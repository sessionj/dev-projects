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

public class MainActivity_20220421 extends AppCompatActivity {


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

    private TextView date_picker_area_info;
    //private String tmpDate = "";
    //private String tmpDateDay = "";

    private String requestSearchDay = "";
    private String viewSearchDay = "";
    /**
     * ?????????, ??????
     */
    private void init(){

        c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        roomDb_phoneNumber = DeviceInfoUtil.getRoomSelecter(this, 2);
        if ( "".equals(roomDb_phoneNumber)){
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }

        deliveryList = new ArrayList<DeliveryModelView>();
        deliveryModelView = new DeliveryModelView();
        deliveryDao = new DeliveryDao(this);
        //deliveryDao.applicationData_deleteAll();

        /**
         * ????????? ?????? ?????? ????????? room db ??? ??????
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

        datapicker_view = (TextView) findViewById(R.id.date_picker_area);
        // request??? ??????
        requestSearchDay = BasicUtils.getYesterday(Label.DELIVERY_STANDARD_DATE_FORMAT);
        // view ??? ??????
        viewSearchDay = BasicUtils.getDays(Label.DELIVERY_STANDARD_DATE_FORMAT) + " (" + BasicUtils.getDayOfweek(BasicUtils.getDays(Label.DELIVERY_STANDARD_DATE_FORMAT), Label.DELIVERY_STANDARD_DATE_FORMAT) + ")";

        init();
        // ????????? ????????? view ???
        datapicker_view.setText(viewSearchDay);
        CheckTypesTask task = new CheckTypesTask();
        task.execute();
        pageRecyclerListView();

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

                datapicker_view = (TextView) findViewById(R.id.date_picker_area);
                aftSearchDate = datapicker_view.getText().toString();
                Log.d("????????? : ", aftSearchDate);

                if ( !befSearchDate.equals(aftSearchDate)){
                    Log.d("????????? ?????????????????????.",  aftSearchDate);
                    datapicker_view = (TextView) findViewById(R.id.date_picker_area);
                    befSearchDate = datapicker_view.getText().toString();
                    String[] tmpStr = befSearchDate.split(" ");
                    requestSearchDay = tmpStr[0].replace("-","");
                    // ???????????? ?????? ???????????????
                    pageRecyclerListView();
                    CheckTypesTask task = new CheckTypesTask();
                    task.execute();
                }
            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        deliveryViewAdapter.notifyDataSetChanged();
    }

    /**
     *
     */
    private void pageRecyclerListView() {

        arr = new ArrayList<DeliveryModelView>();
        deliveryViewAdapter = new DeliveryViewAdapter(arr);

        // ????????? ??????????????? ???????????? ??????.
        if ( requestSearchDay != null){
            deliveryModelView.setCreatdate(requestSearchDay.replace("-", ""));
        }else{
            deliveryModelView.setCreatdate(BasicUtils.getYesterday(Label.DELIVERY_STANDARD_DATE_FORMAT).replace(" ",""));
        }

        arr = deliveryDao.getDeliveryList(deliveryModelView);
        RecyclerView recyclerView = findViewById(R.id.recyceler_view );
        recyclerView.addItemDecoration(new DividerItemDecoration(this, 1)); // ???????????? ????????? ??????
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        deliveryViewAdapter = new DeliveryViewAdapter(arr);
        recyclerView.setAdapter(deliveryViewAdapter);

        deliveryViewAdapter.setOnitemClickListener(new DeliveryViewAdapter.OnitemClickListener() {
            @Override
            public void onItemClick(View v, int pos) {

                Intent intent = new Intent(getApplicationContext(), DeliveryDetailsActivity.class);
                intent.putExtra("billNo",  Integer.valueOf(arr.get(pos).getBillno()));
                startActivity(intent);
            }
        });
    }

    private class CheckTypesTask extends AsyncTask<Void, Void, Void> {

        ProgressDialog asyncDialog = new ProgressDialog(
                MainActivity_20220421.this);

        @Override
        protected void onPreExecute() {
            asyncDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            asyncDialog.setMessage("??????????????????..");

            // show dialog
            asyncDialog.show();
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            try {
                for (int i = 0; i < 2; i++) {
                    asyncDialog.setProgress(i * 700);
                    Thread.sleep(500);
                }
            } catch (InterruptedException e) {
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    /**
     * activity ?????? [?????? ?????? ???????????? ????????????]
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.main_request_delivery_btn:
                Toast.makeText(getApplicationContext(), "?????? ???????????? ?????? ??????", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, DeliveryRequestActivity.class);
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
                ActivityCompat.requestPermissions(MainActivity_20220421.this, mRequiredPermissions, PERMISSIONS_REQUEST_CODE);
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

