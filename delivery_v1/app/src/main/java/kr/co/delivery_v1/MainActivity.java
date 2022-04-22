package kr.co.delivery_v1;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

//import com.gun0912.tedpermission.PermissionListener;
//import com.gun0912.tedpermission.TedPermission;


import java.sql.Array;
import java.text.ParseException;
import java.time.Year;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import kr.co.delivery_v1.R;
import kr.co.delivery_v1.action.DeliveryDao;
import kr.co.delivery_v1.adapter.DeliveryViewAdapter;
import kr.co.delivery_v1.comm.BasicUtils;
import kr.co.delivery_v1.comm.DeviceInfoUtil;
import kr.co.delivery_v1.comm.Label;
import kr.co.delivery_v1.db.AppDatabase;
import kr.co.delivery_v1.db.BasicProcessDao;
import kr.co.delivery_v1.db.delivery.AppDeliveryDatabase;
import kr.co.delivery_v1.login.LoginActivity;
import kr.co.delivery_v1.models.DeliveryModelView;
import kr.co.delivery_v1.models.LoginModelView;

public class MainActivity extends AppCompatActivity {


    int nCurrentPermission = 0;
    static final int PERMISSION_REQUEST = 0x0000001;
    private static final int PERMISSIONS_REQUEST_CODE = 22;
    private boolean loginAccess = false;

    private DeliveryModelView deliveryModelView;
    private DeliveryDao deliveryDao;
    private String roomDb_phoneNumber = "";
    private String device_phoneNumber = "";
    private List<DeliveryModelView> deliveryList;

    TextView datapicker_view;
    private String befSearchDate;
    private String aftSearchDate;

    private Calendar c;
    private int mYear;
    private int mMonth;
    private int mDay;
    private List<DeliveryModelView>  arr;
    private DeliveryViewAdapter deliveryViewAdapter;

    private TextView list_count;

    private String requestSearchDay = "";
    private String viewSearchDay = "";

    private RecyclerView recyclerView;
    private CheckTypesTask taskAsync;

    private boolean changeDate = false;

    /**
     * 초기화, 셋팅
     */
    private void init(){

        c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        deliveryList = new ArrayList<DeliveryModelView>();
        deliveryModelView = new DeliveryModelView();
        deliveryDao = new DeliveryDao(this);
        //deliveryDao.applicationData_deleteAll();
        datapicker_view = (TextView) findViewById(R.id.date_picker_area);
        list_count = (TextView) findViewById(R.id.list_count);
        roomDb_phoneNumber = DeviceInfoUtil.getRoomSelecter(this, 2);
        if ( "".equals(roomDb_phoneNumber)){
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
        deliveryModelView.setDeliverycourse(DeviceInfoUtil.getRoomSelecter(this, 4));

    }

    /**
     * getParam (Intent)
     */
    private void getIntentValue(){

        Intent intent = getIntent();
        if (!TextUtils.isEmpty(intent.getStringExtra("requestSearchDay") )){
            requestSearchDay = intent.getStringExtra("requestSearchDay");
            viewSearchDay =  BasicUtils.getDateControl(intent.getStringExtra("requestSearchDay"), 0, 0, 1) + " (" + BasicUtils.getDayOfweek(requestSearchDay, Label.DELIVERY_STANDARD_DATE_FORMAT)+")" ;
        }else{
            requestSearchDay = BasicUtils.getYesterday(Label.DELIVERY_STANDARD_DATE_FORMAT);
            viewSearchDay = BasicUtils.getDays(Label.DELIVERY_STANDARD_DATE_FORMAT) + " (" + BasicUtils.getDayOfweek(requestSearchDay, Label.DELIVERY_STANDARD_DATE_FORMAT)+")";
        }
        // 달력 일자 셋팅팅
        datapicker_view.setText(viewSearchDay);

        // request 화면에서 받아온건지 체크
        //changeDate = intent.getExtras().getBoolean("returnRequest");

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        getIntentValue();
        taskAsync = new CheckTypesTask();
        taskAsync.equals("");
        //task.execute();
        pageRecyclerListView();

        /*try{
            new CheckTypesTask().execute();
        }catch (Exception e){
            e.printStackTrace();
        }*/
        /**
         * 상단 날짜 검색 구간 ---------------------------------------------------------
         */
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                // 날짜가 변경되면 목록 교체

                String tmpDate = year + "-" + BasicUtils.getFormatDate( (month+1) ) + "-" + BasicUtils.getFormatDate( dayOfMonth );
                String tmpDateDay = BasicUtils.getDayOfweek(tmpDate, Label.DELIVERY_STANDARD_DATE_FORMAT);
                datapicker_view.setText( tmpDate + " ("+tmpDateDay +")");
            }
        }, mYear, mMonth, mDay);

        datapicker_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (datapicker_view.isClickable()) {
                    datePickerDialog.show();
                }
            }
        });
        /**
         * 상단 날짜 검색 구간 ---------------------------------------------------------
         */

        datapicker_view.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // 변경 전 묹자열.
                datapicker_view = (TextView) findViewById(R.id.date_picker_area);
                befSearchDate = datapicker_view.getText().toString();
                Log.d("변경전 : ", befSearchDate);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //editText에 포커스가 되어있고 텍스트 입력 시 동작
            }

            @Override
            public void afterTextChanged(Editable s) {
                // 텍스트가 변경된 이후 호출됨
                changeDate = true;
                datapicker_view = (TextView) findViewById(R.id.date_picker_area);
                aftSearchDate = datapicker_view.getText().toString();
                Log.d("변경후 : ", aftSearchDate);

                if ( !befSearchDate.equals(aftSearchDate)){
                    Log.d("날짜가 변경되었습니다.",  aftSearchDate);
                    datapicker_view = (TextView) findViewById(R.id.date_picker_area);
                    befSearchDate = datapicker_view.getText().toString();
                    if ( befSearchDate != null && befSearchDate.length() > 0){
                        requestSearchDay = BasicUtils.getDateControl(befSearchDate, 0,0,-1);
                        pageRecyclerListView();
                        //taskAsync.equals("");
                    }
                }
            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        deliveryViewAdapter.notifyDataSetChanged();
    }

    private class CheckTypesTask extends AsyncTask<Void, Void, Void> {

        ProgressDialog asyncDialog = new ProgressDialog(MainActivity.this);

        @Override
        protected void onPreExecute() {
            Log.d("================ onPreExecute() ", "실행중");
            asyncDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            asyncDialog.setMessage("로딩중입니다..");

            // show dialog
            asyncDialog.show();
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            try {
                try {
                    //pageRecyclerListView();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            asyncDialog.dismiss();
            super.onPostExecute(result);
        }
    }

    /**
     * 화면 목록 셋팅
     */
    private void pageRecyclerListView() {

        arr = new ArrayList<DeliveryModelView>();

        if ( requestSearchDay != null){
            deliveryModelView.setCreatdate(requestSearchDay.replace("-", ""));
        }else{
            deliveryModelView.setCreatdate(BasicUtils.getYesterday(Label.DELIVERY_STANDARD_DATE_FORMAT).replace(" ",""));
        }
        Toast.makeText(getApplicationContext(), "자료 가져오기 버튼 클릭" + deliveryModelView.getCreatdate(), Toast.LENGTH_SHORT).show();
        arr = deliveryDao.getDeliveryList(deliveryModelView);

        /**
         * 배달 자료가 없으면 받는 화면으로 이동
         * 검색이 이루어진 이후에는 자료가 없어도 이동하지 않는다.
         */
        if ( (arr == null || arr.size() == 0 ) && !changeDate ){
            Log.d("", "====================================================>");
            Intent intent = new Intent(this, DeliveryRequestActivity.class);
            intent.putExtra("requestSearchDay", requestSearchDay);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }

        recyclerView = findViewById(R.id.recyceler_view );
        recyclerView.addItemDecoration(new DividerItemDecoration(this, 1)); // 아이템별 구분선 넣기
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        list_count.setText("총 " + arr.size() + "건");
        deliveryViewAdapter = new DeliveryViewAdapter(arr);
        recyclerView.setAdapter(deliveryViewAdapter);
        //dialog.show();
        deliveryViewAdapter.setOnitemClickListener(new DeliveryViewAdapter.OnitemClickListener() {
            @Override
            public void onItemClick(View v, int pos) {

                Intent intent = new Intent(getApplicationContext(), DeliveryDetailsActivity.class);
                intent.putExtra("billNo", arr.get(pos).getBillno().toString());
                intent.putExtra("requestSearchDay", requestSearchDay);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    /**
     * activity 이동 [배달 자료 받아오는 화면으로]
     * 버튼이 있으면 ID를 가져와서 체크해서 이벤트 발생하면 된다.
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.main_request_delivery_btn:
                Toast.makeText(getApplicationContext(), "자료 가져오기 버튼 클릭", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(this, DeliveryRequestActivity.class);
                intent.putExtra("requestSearchDay", requestSearchDay);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public boolean chkPermission() {
        // 위험 권한을 모두 승인했는지 여부
        boolean mPermissionsGranted = false;
        // 승인 받기 위한 권한 목록
        String[] mRequiredPermissions = new String[]{
                Manifest.permission.CAMERA,
                Manifest.permission.RECORD_AUDIO
        };

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // 필수 권한을 가지고 있는지 확인한다.
            mPermissionsGranted = hasPermissions(mRequiredPermissions);

            // 필수 권한 중에 한 개라도 없는 경우
            if (!mPermissionsGranted) {
                // 권한을 요청한다.
                ActivityCompat.requestPermissions(MainActivity.this, mRequiredPermissions, PERMISSIONS_REQUEST_CODE);
            }
        } else {
            mPermissionsGranted = true;
        }

        return mPermissionsGranted;
    }


    public boolean hasPermissions(String[] permissions) {
        // 필수 권한을 가지고 있는지 확인한다.
        for (String permission : permissions) {
            if (checkCallingOrSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSIONS_REQUEST_CODE) {
            // 권한을 모두 승인했는지 여부
            boolean chkFlag = false;
            // 승인한 권한은 0 값, 승인 안한 권한은 -1을 값으로 가진다.
            for (int g : grantResults) {
                if (g == -1) {
                    chkFlag = true;
                    break;
                }
            }

            // 권한 중 한 개라도 승인 안 한 경우
            if (chkFlag){
                chkPermission();
            }
        }
    }




}

