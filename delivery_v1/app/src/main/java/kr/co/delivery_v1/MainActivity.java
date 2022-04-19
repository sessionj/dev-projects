package kr.co.delivery_v1;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
    /**
     * 초기화, 셋팅
     */
    private void init(){

        datapicker_view = (TextView) findViewById(R.id.date_picker_area);
        datapicker_view.setText(BasicUtils.getDays("yyyy-MM-dd") + " (" +BasicUtils.getDayOfweek(BasicUtils.getDays("yyyy-MM-dd"), "yyyy-MM-dd")+")");

        c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        /**
         * tmp
         */

        roomDb_phoneNumber = DeviceInfoUtil.getRoomSelecter(this, 2);
        if ( "".equals(roomDb_phoneNumber)){
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }

        deliveryList = new ArrayList<DeliveryModelView>();
        deliveryModelView = new DeliveryModelView();
        deliveryDao = new DeliveryDao(this);

        /**
         * 로그인 화면 보고 싶을때 room db 를 제거
         */
        //appDatabase = AppDatabase.getInstance(this);
        //appDatabase.basicProcessDao().applicationData_deleteAll();

        /*roomDb_phoneNumber = DeviceInfoUtil.getRoomSelecter(this, 1);
        device_phoneNumber = DeviceInfoUtil.getPhoneNum(this);

        Log.d("roomdb phone_number : ", "" + roomDb_phoneNumber);
        Log.d("device phone_number : ", "" + device_phoneNumber);

        if ( !roomDb_phoneNumber.equals(device_phoneNumber) ||  "".equals(roomDb_phoneNumber)){
            intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }*/
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 권한 체크에 대한 내용은 마지막에 하자
        // 2022-04-16 : 메인 화면에는 자료가 없을테니

        //if (chkPermission()){
        //   Toast.makeText(this, "위험 권한 승인함", Toast.LENGTH_SHORT).show();
        //}

        init();
        ArrayList<DeliveryModelView> arr = new ArrayList<DeliveryModelView>();
        DeliveryModelView deliveryModelView;
        for ( int i=0; i < 10; i++){
            deliveryModelView = new DeliveryModelView();
            deliveryModelView.setAdress("충북 청주시 흥덕구 문암동 체육생활센터 100번지" + i + " 메롱 ");
            deliveryModelView.setArrivalman("(주)안드로메다");
            deliveryModelView.setArrivalmantel("010-0000-2222");
            deliveryModelView.setPojang("박스");
            deliveryModelView.setGoods("운동화");
            deliveryModelView.setBillno("4001251000001");
            deliveryModelView.setQty(i+1);
            arr.add(deliveryModelView);
        }
        deliveryModelView = new DeliveryModelView();
        /**
         * 검색 조건 넣기
         */

        RecyclerView recyclerView = findViewById(R.id.recyceler_view );
        recyclerView.addItemDecoration(new DividerItemDecoration(this, 1)); // 아이템별 구분선 넣기
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        deliveryModelView.setCreatdate(BasicUtils.getYesterday(Label.DELIVERY_STANDARD_DATE_FORMAT));

       // deliveryList = deliveryDao.getDeliveryList(deliveryModelView);

        DeliveryViewAdapter deliveryViewAdapter = new DeliveryViewAdapter(arr);
        recyclerView.setAdapter(deliveryViewAdapter);

        deliveryViewAdapter.setOnitemClickListener(new DeliveryViewAdapter.OnitemClickListener() {
                @Override
                public void onItemClick(View v, int pos) {
                    Intent intent = new Intent(getApplicationContext(), DeliveryDetailsActivity.class);
                    intent.putExtra("billNo", arr.get(pos).getBillno().toString());
                    startActivity(intent);
                }
            }
        );



        /**
         * 상단 날짜 검색 구간 ---------------------------------------------------------
        */
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                // 날짜가 변경되면 목록 교체
                String tmpDate = "";
                String tmpDateDay = "";
                tmpDate = year + "-" + BasicUtils.getFormatDate( (month+1) ) + "-" + BasicUtils.getFormatDate( dayOfMonth );
                tmpDateDay = BasicUtils.getDayOfweek(tmpDate, Label.DELIVERY_STANDARD_DATE_FORMAT);
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
                Log.d("변경전 ..",  befSearchDate);

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //editText에 포커스가 되어있고 텍스트가 하나라도 입력되어 있을때 동작하기 위해서 추가.
            }

            @Override
            public void afterTextChanged(Editable s) {
                // 텍스트가 변경된 이후 호출됨

                datapicker_view = (TextView) findViewById(R.id.date_picker_area);
                aftSearchDate = datapicker_view.getText().toString();


                if ( !befSearchDate.equals(aftSearchDate)){
                    Log.d("날짜가 변경되었습니다.",  aftSearchDate);
                }

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
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.main_request_delivery_btn:
                Toast.makeText(getApplicationContext(), "자료 가져오기 버튼 클릭", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, DeliveryRequestActivity.class);
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

