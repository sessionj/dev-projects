package kr.co.delivery_v1;

import android.Manifest;
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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import kr.co.delivery_v1.action.DeliveryDao;
import kr.co.delivery_v1.adapter.DeliveryViewAdapter;
import kr.co.delivery_v1.comm.BasicUtils;
import kr.co.delivery_v1.comm.DeviceInfoUtil;
import kr.co.delivery_v1.comm.Label;
import kr.co.delivery_v1.login.LoginActivity;
import kr.co.delivery_v1.models.DeliveryModelView;

public class MainActivity_20220425 extends AppCompatActivity {


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
     * ?????????, ??????
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
        // ?????? ?????? ?????????
        datapicker_view.setText(viewSearchDay);

        // request ???????????? ??????????????? ??????
        //changeDate = intent.getExtras().getBoolean("returnRequest");

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        Log.d("============ phoneNumber : ", roomDb_phoneNumber);
        if ( "".equals(roomDb_phoneNumber) || roomDb_phoneNumber == null){
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }

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
         * ?????? ?????? ?????? ?????? ---------------------------------------------------------
         */
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                // ????????? ???????????? ?????? ??????

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
         * ?????? ?????? ?????? ?????? ---------------------------------------------------------
         */

        datapicker_view.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // ?????? ??? ?????????.
                datapicker_view = (TextView) findViewById(R.id.date_picker_area);
                befSearchDate = datapicker_view.getText().toString();
                Log.d("????????? : ", befSearchDate);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //editText??? ???????????? ???????????? ????????? ?????? ??? ??????
            }

            @Override
            public void afterTextChanged(Editable s) {
                // ???????????? ????????? ?????? ?????????
                changeDate = true;
                datapicker_view = (TextView) findViewById(R.id.date_picker_area);
                aftSearchDate = datapicker_view.getText().toString();
                Log.d("????????? : ", aftSearchDate);

                if ( !befSearchDate.equals(aftSearchDate)){
                    Log.d("????????? ?????????????????????.",  aftSearchDate);
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

        ProgressDialog asyncDialog = new ProgressDialog(MainActivity_20220425.this);

        @Override
        protected void onPreExecute() {
            Log.d("================ onPreExecute() ", "?????????");
            asyncDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            asyncDialog.setMessage("??????????????????..");

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
     * ?????? ?????? ??????
     */
    private void pageRecyclerListView() {

        arr = new ArrayList<DeliveryModelView>();

        if ( requestSearchDay != null){
            deliveryModelView.setCreatdate(requestSearchDay.replace("-", ""));
        }else{
            deliveryModelView.setCreatdate(BasicUtils.getYesterday(Label.DELIVERY_STANDARD_DATE_FORMAT).replace(" ",""));
        }
        Toast.makeText(getApplicationContext(), "?????? ???????????? ?????? ??????" + deliveryModelView.getCreatdate(), Toast.LENGTH_SHORT).show();
        arr = deliveryDao.getDeliveryList(deliveryModelView);

        /**
         * ?????? ????????? ????????? ?????? ???????????? ??????
         * ????????? ???????????? ???????????? ????????? ????????? ???????????? ?????????.
         */
        if ( (arr == null || arr.size() == 0 ) && !changeDate ){
            Log.d("", "====================================================>");
            Intent intent = new Intent(this, DeliveryRequestActivity.class);
            intent.putExtra("requestSearchDay", requestSearchDay);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }

        recyclerView = findViewById(R.id.recyceler_view );
        recyclerView.addItemDecoration(new DividerItemDecoration(this, 1)); // ???????????? ????????? ??????
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        list_count.setText("??? " + arr.size() + "???");
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
     * activity ?????? [?????? ?????? ???????????? ????????????]
     * ????????? ????????? ID??? ???????????? ???????????? ????????? ???????????? ??????.
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.main_request_delivery_btn:
                Toast.makeText(getApplicationContext(), "?????? ???????????? ?????? ??????", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(this, DeliveryRequestActivity.class);
                intent.putExtra("requestSearchDay", requestSearchDay);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public boolean chkPermission() {
        // ?????? ????????? ?????? ??????????????? ??????
        boolean mPermissionsGranted = false;
        // ?????? ?????? ?????? ?????? ??????
        String[] mRequiredPermissions = new String[]{
                Manifest.permission.CAMERA,
                Manifest.permission.RECORD_AUDIO
        };

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // ?????? ????????? ????????? ????????? ????????????.
            mPermissionsGranted = hasPermissions(mRequiredPermissions);

            // ?????? ?????? ?????? ??? ????????? ?????? ??????
            if (!mPermissionsGranted) {
                // ????????? ????????????.
                ActivityCompat.requestPermissions(MainActivity_20220425.this, mRequiredPermissions, PERMISSIONS_REQUEST_CODE);
            }
        } else {
            mPermissionsGranted = true;
        }

        return mPermissionsGranted;
    }


    public boolean hasPermissions(String[] permissions) {
        // ?????? ????????? ????????? ????????? ????????????.
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
            // ????????? ?????? ??????????????? ??????
            boolean chkFlag = false;
            // ????????? ????????? 0 ???, ?????? ?????? ????????? -1??? ????????? ?????????.
            for (int g : grantResults) {
                if (g == -1) {
                    chkFlag = true;
                    break;
                }
            }

            // ?????? ??? ??? ????????? ?????? ??? ??? ??????
            if (chkFlag){
                chkPermission();
            }
        }
    }




}

