package kr.co.delivery_v1;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import kr.co.delivery_v1.action.request.DeliveryRequest;
import kr.co.delivery_v1.action.request.DeliveryRequestSummary;
import kr.co.delivery_v1.adapter.DeliverySummaryViewAdapter;
import kr.co.delivery_v1.adapter.DeliveryViewAdapter;
import kr.co.delivery_v1.comm.BasicUtils;
import kr.co.delivery_v1.comm.DeviceInfoUtil;
import kr.co.delivery_v1.comm.Label;
import kr.co.delivery_v1.db.AppDatabase;
import kr.co.delivery_v1.db.delivery.AppDeliveryDatabase;
import kr.co.delivery_v1.login.LoginActivity;
import kr.co.delivery_v1.login.LoginRequest;
import kr.co.delivery_v1.models.DeliveryListViewItem;
import kr.co.delivery_v1.models.DeliveryModelView;
import kr.co.delivery_v1.models.LoginModelView;

/**
 *  # 규칙
 *   1. 자료를 가져오기 전에 room db 에는 5일이전 데이터를 삭제 하고 자료를 받는다.
 *   2. 할게 무쟈게 많구나 화면에서 정의를 좀 해보자
 */
public class DeliveryRequestActivity extends AppCompatActivity {

    final private static String TAG = "DeliveryRequestActivity ";
    private TextView deliveryavt_date_picker_area, deliveryavt_delivery_cource, deliveryavt_agencycode;
    private TextView request_etc_btn;
    private Button request_btn;
    private CheckBox delivery_check;

    private boolean isDeliveryCheck = false;
    private String deliveryCourse = "";
    private String agencyCode = "";
    private DeliveryModelView deliveryModelView;
    private DeliveryListViewItem deliveryListViewItem;
    private Calendar c;
    private int mYear;
    private int mMonth;
    private int mDay;
    private List<DeliveryListViewItem> deliveryListViewItemList;
    private AppDeliveryDatabase appDeliveryDatabase;

    private String etc_btn_check = "";
    private int successCnt = 0;
    private String requestSearchDay = "";

    private RecyclerView recyclerView;
    private DeliverySummaryViewAdapter deliverySummaryViewAdapter;
    private StringBuffer deliveryCourseParam = null;
    private LinearLayoutManager layoutManager;
    /**
     * 초기화 및 셋팅
     */
    private void init(){

        appDeliveryDatabase = AppDeliveryDatabase.getInstance(this);
        c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        deliveryModelView = new DeliveryModelView();

        agencyCode = DeviceInfoUtil.getRoomSelecter(this,3);
        deliveryCourse = DeviceInfoUtil.getRoomSelecter(this,4);

        deliveryavt_agencycode = (TextView) findViewById(R.id.deliveryavt_agencycode);
        deliveryavt_delivery_cource = (TextView) findViewById(R.id.deliveryavt_delivery_cource);
        deliveryavt_date_picker_area = (TextView) findViewById(R.id.deliveryavt_date_picker_area);
        //delivery_check = (CheckBox) findViewById(R.id.checkbox_delivery_check);
        deliveryavt_agencycode.setText(agencyCode);
        deliveryavt_delivery_cource.setText(deliveryCourse);
        deliveryavt_date_picker_area.setText(BasicUtils.getDays("yyyy-MM-dd") + " ("+BasicUtils.getDayOfweek(BasicUtils.getDays("yyyy-MM-dd"), "yyyy-MM-dd")+")");

        //request_etc_btn = (TextView) findViewById(R.id.request_etc_btn);    // 자료 더 가져오기(etc)
        request_btn = (Button) findViewById(R.id.request_btn);              // 클릭된 자료 가져오기

        deliveryModelView.setArrivalagencycode(agencyCode);
        deliveryModelView.setDeliverycourse(deliveryCourse);
        deliveryModelView.setCreatdate(BasicUtils.getDays("yyyy-MM-dd"));

        deliveryCourseParam = new StringBuffer();
    }

    /**
     * getParam (Intent)
     */
    private void getIntentValue(){
        Intent intent = getIntent();
        requestSearchDay = intent.getStringExtra("requestSearchDay") == null ? "" : intent.getStringExtra("requestSearchDay");
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                Intent intent = new Intent(DeliveryRequestActivity.this, MainActivity.class);
                intent.putExtra("requestSearchDay", requestSearchDay);
                intent.putExtra("returnRequest", true);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                finish();
                startActivity(intent);
                return true;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_request);

        /*ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("배달通");
        actionBar.setDisplayHomeAsUpEnabled(true);*/

        init(); // 화면 셋팅
        getIntentValue();   // 파라미터 셋팅
        setRequestStatus();

        // 화면에 표기

        /**
         *
         */
        AlertDialog.Builder builder = new AlertDialog.Builder(this).setIcon(android.R.drawable.ic_menu_save);

        request_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // 버튼 체크 여부 확인
                isDeliveryCheck = delivery_check.isChecked();
                if ( !isDeliveryCheck){
                    AlertDialog.Builder builder = new AlertDialog.Builder(DeliveryRequestActivity.this)
                            .setIcon(android.R.drawable.ic_btn_speak_now);
                    builder.setTitle("경고");
                    builder.setMessage("자료체크 여부를 확인해주세요");
                    builder.setPositiveButton("닫기",null);
                    builder.create().show();
                    return;
                }

                builder.setTitle("알림");
                builder.setMessage(deliveryModelView.getCreatdate() + " 배달자료를 수신하시겠습니까?");
                builder.setPositiveButton("예", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {

                    Toast.makeText(getApplicationContext(), "YES Button Click", Toast.LENGTH_LONG).show();
                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try{
                                roomDbRequest(response);

                            }catch (Exception e){
                                Log.d("log ", e.toString());
                            }finally {
                                AlertDialog.Builder builder = new AlertDialog.Builder(DeliveryRequestActivity.this)
                                        .setIcon(android.R.drawable.ic_btn_speak_now);
                                builder.setTitle("안내");
                                builder.setMessage("자료 수신 (총 "+successCnt+" 건) 완료" );
                                builder.setPositiveButton("닫기",null);
                                builder.create().show();
                                return;
                            }
                        }

                        /**
                         * roomdb insert
                         * @param response
                         */
                        private void roomDbRequest(String response) {

                            ArrayList<DeliveryModelView> resultData = new ArrayList<DeliveryModelView>();
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                JSONArray resultarray = jsonObject.getJSONArray("rows");//배열의 이름

                                for ( int i=0; i < resultarray.length(); i++){
                                    JSONObject Object = resultarray.getJSONObject(i);
                                    deliveryModelView = new DeliveryModelView();
                                    deliveryModelView.setBillno(Object.getString("billno"));
                                    deliveryModelView.setInput_date(Object.getString("input_date"));
                                    deliveryModelView.setInput_time(Object.getString("input_time"));
                                    deliveryModelView.setTranscode(Object.getString("transcode"));
                                    deliveryModelView.setSendingagencycode(Object.getString("sendingagencycode"));
                                    deliveryModelView.setArrivalagencycode(Object.getString("arrivalagencycode"));
                                    deliveryModelView.setSendingmantel(Object.getString("sendingmantel"));
                                    deliveryModelView.setSendingman(Object.getString("sendingman"));
                                    deliveryModelView.setArrivalmantel(Object.getString("arrivalmantel"));
                                    deliveryModelView.setArrivalman(Object.getString("arrivalman"));
                                    deliveryModelView.setZipcode(Object.getString("zipcode"));
                                    deliveryModelView.setAdress(Object.getString("adress"));
                                    deliveryModelView.setPrefare(Object.getString("prefare"));
                                    deliveryModelView.setFare(Object.getString("fare"));
                                    deliveryModelView.setDeliveryfare(Object.getString("deliveryfare"));
                                    deliveryModelView.setOgideliveryfare(Object.getString("ogideliveryfare"));
                                    deliveryModelView.setDistance(Object.getString("distance"));
                                    deliveryModelView.setPayway(Object.getString("payway"));
                                    deliveryModelView.setGoods(Object.getString("goods"));
                                    deliveryModelView.setPojang(Object.getString("pojang"));
                                    deliveryModelView.setQty(Object.getInt("qty"));
                                    deliveryModelView.setWeight(Object.getString("weight"));
                                    deliveryModelView.setMemo(Object.getString("memo"));
                                    deliveryModelView.setBillstate(Object.getString("billstate"));
                                    deliveryModelView.setDeliverycourse(Object.getString("deliverycourse"));
                                    deliveryModelView.setCreatdate(Object.getString("creatdate"));

                                    appDeliveryDatabase.basicDeliveryProcessDao().applicationData_insert(deliveryModelView);
                                    successCnt ++;
                                }
                            } catch(JSONException e){
                                e.printStackTrace();
                            }
                        }
                    };

                    DeliveryRequest deliveryRequest = new DeliveryRequest(deliveryModelView, responseListener );
                    RequestQueue queue = Volley.newRequestQueue( DeliveryRequestActivity.this );
                    queue.add( deliveryRequest );
                    }
                });

                builder.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //"NO" Button Click
                        Toast.makeText(getApplicationContext(), "NO Button Click", Toast.LENGTH_LONG).show();
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });


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
                deliveryavt_date_picker_area.setText( tmpDate + " ("+tmpDateDay +")");
                String[] tmpStr = deliveryavt_date_picker_area.getText().toString().split(" ");
                deliveryModelView.setCreatdate(tmpStr[0].toString() );
            }
        }, mYear, mMonth, mDay);

        deliveryavt_date_picker_area.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (deliveryavt_date_picker_area.isClickable()) {
                    datePickerDialog.show();
                }
            }
        });
        /**
         * 상단 날짜 검색 구간 ---------------------------------------------------------
         */
    }

    /**
     * 화면에 들어오면 화면에 한번 뿌린다.
     * 청주우암 홍길동 10건 (자료받기)
     * 청주우암 김수각 20건 (자료받기)
     * 전체 자료 받기
     * 선택 자료 받기
     */
    private void setRequestStatus() {

        Response.Listener<String> responseViewListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    requestDeliveryView(response);

                }catch (Exception e){
                    Log.d("log ", e.toString());
                }finally {

                }
            }
            /**
             * view
             * @param response
             */
            private void requestDeliveryView(String response) {

                deliveryListViewItemList = new ArrayList<DeliveryListViewItem>();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray resultarray = jsonObject.getJSONArray("rows");//배열의 이름

                    for ( int i=0; i < resultarray.length(); i++){
                        JSONObject Object = resultarray.getJSONObject(i);

                        deliveryListViewItem = new DeliveryListViewItem();
                        deliveryListViewItem.setArrivalagencycode(deliveryModelView.getArrivalagencycode());
                        deliveryListViewItem.setDelivery_course(Object.getString("course"));
                        deliveryListViewItem.setDelivery_course_name(Object.getString("course_name"));
                        deliveryListViewItem.setDelivery_course_cnt(Object.getInt("course_cnt"));
                        deliveryListViewItemList.add(deliveryListViewItem);
                    }

                    //setRequestStatusView(deliveryListViewItemList);
                    deliveryListViewItem.setArrivalagencycode(deliveryModelView.getArrivalagencycode() == null ? "" :deliveryModelView.getArrivalagencycode());
                    deliveryListViewItem.setDeliverycourse(deliveryModelView.getDeliverycourse() == null ? "" :deliveryModelView.getDeliverycourse());
                    deliveryListViewItem.setCreatdate(deliveryModelView.getCreatdate() == null ? "" :deliveryModelView.getCreatdate());

                    /**
                     * recyclerView
                     */
                    recyclerView = findViewById(R.id.request_recyceler_view);
                    layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
                    recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), 1)); // 아이템별 구분선 넣기
                    recyclerView.setLayoutManager(layoutManager);
                    deliverySummaryViewAdapter = new DeliverySummaryViewAdapter(deliveryListViewItemList);

                    recyclerView.setAdapter(deliverySummaryViewAdapter);

                    deliverySummaryViewAdapter.setOnitemButtonClickListener(new DeliverySummaryViewAdapter.OnitemButtonClickListener() {
                        @Override
                        public void onItemButtonClick (View v, int pos) {

                            // pos 들어온 체크박스가 체크인지 아닌지 여부 검사
                            deliveryListViewItemList.get(pos).getArrivalagencycode();
                            deliveryListViewItemList.get(pos).getDelivery_course();
                            String tmpStr = deliveryListViewItemList.get(pos).getArrivalagencycode() + ", " +deliveryListViewItemList.get(pos).getDelivery_course();
                            Toast.makeText(getApplicationContext(), "받아올 자료 선택===========================================여기 . : " + tmpStr, Toast.LENGTH_LONG ).show();
                        }
                    });

                    deliverySummaryViewAdapter.setOnitemClickListener(new DeliverySummaryViewAdapter.OnitemClickListener() {
                        @Override
                        public void onItemClick(View v, int pos) {
                            // 버튼 클릭 시 해당 로우의 영업소 코드와 배달코스를 받아온다
                            deliveryListViewItemList.get(pos).getArrivalagencycode();
                            deliveryListViewItemList.get(pos).getDelivery_course();

                            String tmpStr = deliveryListViewItemList.get(pos).getArrivalagencycode() + ", " +deliveryListViewItemList.get(pos).getDelivery_course();

                            Toast.makeText(getApplicationContext(), "받아올 자료 선택 : " + tmpStr, Toast.LENGTH_LONG ).show();
                        }
                    });

                } catch(JSONException e){
                    e.printStackTrace();
                }
            }
        };
        deliveryListViewItem = new DeliveryListViewItem();
        deliveryListViewItem.setArrivalagencycode(deliveryModelView.getArrivalagencycode() == null ? "" :deliveryModelView.getArrivalagencycode());
        deliveryListViewItem.setDeliverycourse(deliveryModelView.getDeliverycourse() == null ? "" :deliveryModelView.getDeliverycourse());
        deliveryListViewItem.setCreatdate(deliveryModelView.getCreatdate() == null ? "" :deliveryModelView.getCreatdate());

        DeliveryRequestSummary deliveryRequestSummary = new DeliveryRequestSummary(deliveryListViewItem, responseViewListener, deliveryCourseParam); // <-- 파라미터 체크(deliveryCourseParam)
        RequestQueue queue = Volley.newRequestQueue( DeliveryRequestActivity.this );
        queue.add( deliveryRequestSummary );

    }

    /**
     * 가져온 자료 화면에 표기
     */


    public void onClick_setting_costume_save(View view){
        new AlertDialog.Builder(this)
            .setTitle("봉식이셋팅>코스튬")
            .setMessage("저장하시겠습니까?")
            .setIcon(android.R.drawable.ic_menu_save)
            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    // 확인시 처리 로직
                    Toast.makeText(DeliveryRequestActivity.this, "저장을 완료했습니다.", Toast.LENGTH_SHORT).show();
                    finish();
                }})
            .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    // 취소시 처리 로직
                    Toast.makeText(DeliveryRequestActivity.this, "취소하였습니다.", Toast.LENGTH_SHORT).show();
                }})
            .show();
    }

    private class CheckTypesTask extends AsyncTask<Void, Void, Void> {

        ProgressDialog asyncDialog = new ProgressDialog(DeliveryRequestActivity.this);

        @Override
        protected void onPreExecute() {
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
                    for (int i = 0; i < 7; i++) {
                        asyncDialog.setProgress(i * 700);
                        Thread.sleep(500);
                    }
                } catch (InterruptedException e) {
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

    public class CheckableLinerLayout extends LinearLayout implements Checkable{

        public CheckableLinerLayout(Context context, @Nullable AttributeSet attrs) {
            super(context, attrs);
        }

        @Override
        public boolean isChecked() {
            Log.d(TAG, "===================isChecked");
            CheckBox cb = (CheckBox) findViewById(R.id.summary_row_item_check) ;
            return cb.isChecked() ;
            // return mIsChecked ;
        }

        @Override
        public void toggle() {
            Log.d(TAG, "===================toggle");
            CheckBox cb = (CheckBox) findViewById(R.id.summary_row_item_check) ;

            setChecked(cb.isChecked() ? false : true) ;
            // setChecked(mIsChecked ? false : true) ;
        }

        @Override
        public void setChecked(boolean checked) {
            Log.d(TAG, "===================setChecked");
            CheckBox cb = (CheckBox) findViewById(R.id.summary_row_item_check) ;

            if (cb.isChecked() != checked) {
                cb.setChecked(checked) ;
            }

            // CheckBox 가 아닌 View의 상태 변경.
        }
    }

}