package kr.co.delivery_v1;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
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
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

//import com.gun0912.tedpermission.PermissionListener;
//import com.gun0912.tedpermission.TedPermission;


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

public class MainActivity extends AppCompatActivity {

    private final long FINISH_INTERVAL_TIME = 2000;
    private long backPressedTime = 0;

    private boolean loginAccess = false;

    private DeliveryViewAdapter deliveryViewAdapter;
    private List<DeliveryModelView> deliveryList;
    private DeliveryModelView deliveryModelView;
    private List<DeliveryModelView>  arr;
    private DeliveryDao deliveryDao;
    private RecyclerView recyclerView;
    private int deliverySuccessCnt = 0;
    private TextView datapicker_view, list_count;
    private String befSearchDate, aftSearchDate;
    private Calendar c;
    private int mYear, mMonth, mDay;
    private String roomDb_phoneNumber = "";
    private String device_phoneNumber = "";
    private String requestSearchDay = "";
    private String viewSearchDay = "";
    private boolean changeDate = false;
    private CheckTypesTask taskAsync;
    private TextView main_list_empty ;

    @Override
    public void onBackPressed() {
        long tempTime = System.currentTimeMillis();
        long intervalTime = tempTime - backPressedTime;

        if (0 <= intervalTime && FINISH_INTERVAL_TIME >= intervalTime)
        {
            finish();
        }
        else
        {
            backPressedTime = tempTime;
            Toast.makeText(getApplicationContext(), "한번 더 누르면 종료됩니다.", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 매뉴 활성화 (상단 우측 "자료가져오기" 버튼 생성)
     * @param menu
     * @return
     */
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
        switch (item.getItemId()) {
            case R.id.main_request_delivery_btn:
                Intent intent = new Intent(this, DeliveryRequestActivity.class);
                intent.putExtra("requestSearchDay", BasicUtils.getDateControl(requestSearchDay,0,0,1));
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    /**
     * 초기화, 셋팅
     */
    private void initialization(){

        c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        deliveryDao = new DeliveryDao(this);
        deliveryModelView = new DeliveryModelView();
        deliveryList = new ArrayList<DeliveryModelView>();

        list_count = (TextView) findViewById(R.id.list_count);
        datapicker_view = (TextView) findViewById(R.id.date_picker_area);
        roomDb_phoneNumber = DeviceInfoUtil.getRoomSelecter(this, 2);
        deliveryModelView.setDeliverycourse(DeviceInfoUtil.getRoomSelecter(this, 4));
        main_list_empty = (TextView) findViewById(R.id.main_list_empty);
        // 데이터 삭제시
        //deliveryDao.applicationData_deleteAll();

        List<DeliveryModelView> tmpArr = new ArrayList<DeliveryModelView>();
        tmpArr = deliveryDao.getDeliveryList();
        Log.d("tmp : ",""+ tmpArr);
    }

    /**
     * getParam (Intent)
     */
    private void initializationSetIntentValue(){

        Intent intent = getIntent();
        if (!TextUtils.isEmpty(intent.getStringExtra("requestSearchDay") )){
            /**
             * 파라미터 셋팅
             * 날짜가 넘어오면 검색 데이터는 -1
             * 보여주는 데이터는 넘어온 데이터 그대로
             */
            requestSearchDay = BasicUtils.getDateControl(intent.getStringExtra("requestSearchDay"), 0,0,-1) ;
            viewSearchDay =  intent.getStringExtra("requestSearchDay")  ;
        }else{

            /**
             * 파라미터가 없을경우
             */
            requestSearchDay = BasicUtils.getYesterday(Label.DELIVERY_STANDARD_DATE_FORMAT);
            viewSearchDay = BasicUtils.getDays(Label.DELIVERY_STANDARD_DATE_FORMAT);
        }
        // 달력 일자 셋팅팅


        // request 화면에서 받아온건지 체크
        //changeDate = intent.getExtras().getBoolean("returnRequest");

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*if ( DeviceInfoUtil.getRoomSelecter(this, 2) == null || DeviceInfoUtil.getRoomSelecter(this, 2).length() == 0) {
            moveActivity("DDL");
        }*/

        initialization();
        initializationSetIntentValue();
        datapicker_view.setText(viewSearchDay + " ("+BasicUtils.getDayOfweek(viewSearchDay, Label.DELIVERY_STANDARD_DATE_FORMAT)+")");
        /*if ( "".equals(roomDb_phoneNumber) || roomDb_phoneNumber == null){
            moveActivity("DDL");
            return ;
        }*/
        taskAsync = new CheckTypesTask();
        taskAsync.execute();

        //task.execute();
        //pageRecyclerListView();

        /*try{
            new CheckTypesTask().execute();
        }catch (Exception e){
            e.printStackTrace();
        }*/

        /**
         * 상단 날짜 검색 구간 ########################################################################################################
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
         * 상단 날짜 검색 구간 ########################################################################################################
         */

        /**
         * 상단 날짜 변경 검색 ########################################################################################################
         */
        datapicker_view.addTextChangedListener(new TextWatcher() {
            @Override   // 변경 전 묹자열
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                datapicker_view = (TextView) findViewById(R.id.date_picker_area);
                befSearchDate = datapicker_view.getText().toString();
            }

            @Override   // editText에 포커스, 텍스트 입력시 동작함
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override   // 텍스트가 변경된 이후 호출됨
            public void afterTextChanged(Editable s) {

                changeDate = true;
                datapicker_view = (TextView) findViewById(R.id.date_picker_area);
                aftSearchDate = datapicker_view.getText().toString();

                if ( !befSearchDate.equals(aftSearchDate)){
                    datapicker_view = (TextView) findViewById(R.id.date_picker_area);
                    befSearchDate = datapicker_view.getText().toString();
                    if ( befSearchDate != null && befSearchDate.length() > 0){
                        requestSearchDay = BasicUtils.getDateControl(befSearchDate, 0,0,-1);
                        searchpageRecyclerListView();
                    }
                }
            }
        });
        /**
         * 상단 날짜 변경 검색 ########################################################################################################
         */
    }

    public void searchpageRecyclerListView(){
        taskAsync = new CheckTypesTask();
        taskAsync.execute();

        //pageRecyclerListView();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        deliveryViewAdapter.notifyDataSetChanged();
    }

    /**
     * Async (비동기 처리시)
     */
    private class CheckTypesTask extends AsyncTask<Void, Void, Void> {

        ProgressDialog asyncDialog = new ProgressDialog(MainActivity.this);

        @Override
        protected void onPreExecute() {
            asyncDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            asyncDialog.setMessage("로딩중입니다..");

            // show dialog
            asyncDialog.show();
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            try {
                try {
                    pageRecyclerListView();
                    Thread.sleep(200 * 5);
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

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                arr = new ArrayList<DeliveryModelView>();
                deliveryModelView.setCreatdate(requestSearchDay);
                arr = deliveryDao.getDeliveryList(deliveryModelView);

                if ((arr == null || arr.size() == 0) ) {
                    isEmptyList();
                    // 오늘 날짜면 이동 그렇지 않으면 물어본다
                    if ( deliveryModelView.getCreatdate() != (BasicUtils.getDays(Label.DELIVERY_STANDARD_DATE_FORMAT))){
                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this).setIcon(android.R.drawable.ic_btn_speak_now);

                        builder.setTitle("알림");
                        builder.setMessage( BasicUtils.getDateControl(deliveryModelView.getCreatdate(),0,0,1)  + Label.DELIVERY_DELIVERY_EMPTY_QUESTION);
                        builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                moveActivity(Label.DELIVERY_DELIVERY_ACT_MOVETO_REQEUST);
                            }
                        });

                        builder.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                        AlertDialog alert = builder.create();
                        alert.show();
                    }else{
                        moveActivity(Label.DELIVERY_DELIVERY_ACT_MOVETO_REQEUST);
                        return;
                    }
                }
                searchProcessHistory(arr);
                recyclerView = findViewById(R.id.recyceler_view);
                recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), 1)); // 아이템별 구분선 넣기
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                list_count.setText("["+deliverySuccessCnt+"/"+arr.size()+"]건");
                deliveryViewAdapter = new DeliveryViewAdapter(arr);
                recyclerView.setAdapter(deliveryViewAdapter);
                //dialog.show();
                deliveryViewAdapter.setOnitemClickListener(new DeliveryViewAdapter.OnitemClickListener() {
                    @Override
                    public void onItemClick(View v, int pos) {

                        Intent intent = new Intent(getApplicationContext(), DeliveryDetailsActivity.class);
                        intent.putExtra("billNo", arr.get(pos).getBillno().toString());
                        intent.putExtra("requestSearchDay", BasicUtils.getDateControl(requestSearchDay,0,0,1) );
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }
                });
            }
        });
    }

    /**
     * 엑티비티 이동 (순서 체크)
     * @param route
     */
    public void moveActivity(String route){

        Log.d("route : ", route);

        if ( !TextUtils.isEmpty(route)){
            if ( route.equals(Label.DELIVERY_DELIVERY_ACT_MOVETO_LOGIN)){

                Intent intent = new Intent(this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                return;
            }else if(route.equals(Label.DELIVERY_DELIVERY_ACT_MOVETO_REQEUST)){
                Intent intent = new Intent(this, DeliveryRequestActivity.class);
                intent.putExtra("requestSearchDay", BasicUtils.getDateControl(requestSearchDay,0,0,1)  );
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                return;
            }
        }
    }

    /**
     * 검색시 자료가 없을시에 표기된다.
     */
    public void isEmptyList(){
        // 객체 호출
        main_list_empty.setVisibility(View.VISIBLE);

    }

    /**
     * 상단
     * @param arr
     */
    private void searchProcessHistory(List<DeliveryModelView> arr){

        DeliveryModelView entity = null;
        if ( arr != null && arr.size() > 0){
            entity = new DeliveryModelView();
            for ( int i=0; i <arr.size(); i++){
                entity = arr.get(i);
                if ( entity.getDelivery_state().equals("Y")){
                    deliverySuccessCnt ++;
                }
            }
        }
    }
}

