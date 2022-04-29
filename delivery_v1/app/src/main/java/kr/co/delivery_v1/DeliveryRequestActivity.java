package kr.co.delivery_v1;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.DatePicker;
import android.widget.ImageView;
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

import kr.co.delivery_v1.action.DeliveryDao;
import kr.co.delivery_v1.action.request.DeliveryRequest;
import kr.co.delivery_v1.action.request.DeliveryRequestSummary;
import kr.co.delivery_v1.adapter.DeliverySummaryViewAdapter;
import kr.co.delivery_v1.adapter.QuanitityListener;
import kr.co.delivery_v1.comm.BasicUtils;
import kr.co.delivery_v1.comm.DeviceInfoUtil;
import kr.co.delivery_v1.comm.Label;
import kr.co.delivery_v1.db.delivery.BasicDeliveryDatabase;
import kr.co.delivery_v1.db.delivery.BasicDeliveryRequestDatabase;
import kr.co.delivery_v1.models.DeliveryListViewItem;
import kr.co.delivery_v1.models.DeliveryModelView;
import kr.co.delivery_v1.models.DeliveryRequestModelView;

/**
 *  # 규칙
 *   1. 자료를 가져오기 전에 room db 에는 5일이전 데이터를 삭제 하고 자료를 받는다.
 *   2. 할게 무쟈게 많구나 화면에서 정의를 좀 해보자
 *   3. git test
 */
public class DeliveryRequestActivity extends AppCompatActivity implements QuanitityListener{

    final private static String TAG = "DeliveryRequestActivity ";
    private TextView deliveryavt_date_picker_area, deliveryavt_delivery_cource, deliveryavt_agencycode, reqeust_false_txt;
    private LinearLayout request_all_pull_area, reqeust_false_replay;
    private Button request_btn;
    private CheckBox request_all_btn;

    private ProgressDialog progressDialog;
    private ImageView request_flase_replay_set_1;
    private TextView request_flase_replay_set_2;

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
    private BasicDeliveryDatabase basicDeliveryDatabase;
    private BasicDeliveryRequestDatabase basicDeliveryRequestDatabase;
    private List<DeliveryRequestModelView> deliveryRequestModelViewList;
    private String etc_btn_check = "";
    private int successCnt = 0;
    private String requestSearchDay = "";
    private String viewRequestSearchDay = "";

    //private RecyclerView recyclerView;
    private DeliverySummaryViewAdapter deliverySummaryViewAdapter;
    private StringBuffer deliveryCourseParam = null;
    private LinearLayoutManager layoutManager;
    private boolean isResponse = true;
    private StringBuffer privateParam;
    private DeliveryRequestModelView deliveryRequestModelView;
    private String deliveryCourseName = "";
    private int combination_key;
    private ArrayList<DeliveryRequestModelView> deliveryRequestModelViewArrayList;
    /**
     * 초기화 및 셋팅
     */
    private void init(){

        basicDeliveryDatabase = BasicDeliveryDatabase.getInstance(this);
        basicDeliveryRequestDatabase = BasicDeliveryRequestDatabase.getInstance(this);
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

        request_btn = (Button) findViewById(R.id.request_btn);              // 클릭된 자료 가져오기
        request_all_pull_area = (LinearLayout) findViewById(R.id.request_all_pull_area);
        request_all_btn = (CheckBox) findViewById(R.id.request_all_btn);
        request_flase_replay_set_1 = (ImageView) findViewById(R.id.request_flase_replay_set_1);
        request_flase_replay_set_2 = (TextView) findViewById(R.id.request_flase_replay_set_2);

        reqeust_false_txt = (TextView) findViewById(R.id.reqeust_false_txt);
        reqeust_false_replay = (LinearLayout) findViewById(R.id.reqeust_false_replay);

        deliveryModelView.setArrivalagencycode(agencyCode);
        deliveryModelView.setDeliverycourse(deliveryCourse);
        //deliveryModelView.setCreatdate(BasicUtils.getDays(Label.DELIVERY_STANDARD_DATE_FORMAT));
        deliveryCourseParam = new StringBuffer();


    }

    /**
     * getParam (Intent)
     */
    private void getIntentValue(){
        /*Intent intent = getIntent();
        requestSearchDay = intent.getStringExtra("requestSearchDay") == null ? "" : intent.getStringExtra("requestSearchDay");
        Toast.makeText(getApplicationContext(), "금일 자료가 없기에 화면 이동됨", Toast.LENGTH_SHORT).show();
        Log.d("==========> request : ", requestSearchDay);*/

        Intent intent = getIntent();
        requestSearchDay = intent.getStringExtra("requestSearchDay") == null ? BasicUtils.getYesterday(Label.DELIVERY_STANDARD_DATE_FORMAT) : intent.getStringExtra("requestSearchDay");
        viewRequestSearchDay = requestSearchDay + " ("+BasicUtils.getDayOfweek(requestSearchDay, Label.DELIVERY_STANDARD_DATE_FORMAT)+")";

        if ( !TextUtils.isEmpty(requestSearchDay)){
            deliveryavt_date_picker_area.setText(viewRequestSearchDay);
        }else{
            deliveryavt_date_picker_area.setText(BasicUtils.getDays(Label.DELIVERY_STANDARD_DATE_FORMAT) + " ("+BasicUtils.getDayOfweek( BasicUtils.getDays(Label.DELIVERY_STANDARD_DATE_FORMAT), Label.DELIVERY_STANDARD_DATE_FORMAT  )+")");
        }

        deliveryModelView.setCreatdate(requestSearchDay);
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
        //setRequestStatus();
        CheckTypesTask task = new CheckTypesTask();
        task.execute();

        // 화면에 표기

        /**
         *
         */
        AlertDialog.Builder builder = new AlertDialog.Builder(this).setIcon(android.R.drawable.ic_menu_save);

        request_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // 버튼 체크 여부 확인
                /*isDeliveryCheck = delivery_check.isChecked();
                if ( !isDeliveryCheck){
                    AlertDialog.Builder builder = new AlertDialog.Builder(DeliveryRequestActivity.this)
                            .setIcon(android.R.drawable.ic_btn_speak_now);
                    builder.setTitle("경고");
                    builder.setMessage("자료체크 여부를 확인해주세요");
                    builder.setPositiveButton("닫기",null);
                    builder.create().show();
                    return;
                }*/
                if ( deliverySummaryViewAdapter.getBtnCheckList() != null && deliverySummaryViewAdapter.getBtnCheckList().size() == 0 ){

                    AlertDialog.Builder builder = new AlertDialog.Builder(DeliveryRequestActivity.this).setIcon(android.R.drawable.ic_btn_speak_now);
                    builder.setTitle("경고");
                    builder.setMessage("수신할 항목에 체크박스를 선택하세요");
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
                        getDeliveryList(null, 0);
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

                CheckTypesTask dateChange = new CheckTypesTask();
                dateChange.execute();
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

        /**
         * 다시 시도 이미지 클릭시
         */
        request_flase_replay_set_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setReplayEvent();
            }
        });

        /**
         * 다시시도 텍스트 클릭시
         */
        request_flase_replay_set_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setReplayEvent();
            }
        });

        /**
         * 전체 자료 받기 클릭스
         */
        request_all_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if ( request_all_btn.isChecked()){

                    Toast.makeText(getApplicationContext(), "전체 선택", Toast.LENGTH_SHORT).show();

                    for ( DeliveryListViewItem model : deliveryListViewItemList){
                        model.setSelected(true);
                    }
                }else{
                    Toast.makeText(getApplicationContext(), "전체 선택을 해제", Toast.LENGTH_SHORT).show();
                    for ( DeliveryListViewItem model : deliveryListViewItemList){
                        model.setSelected(false);
                    }
                }
                deliverySummaryViewAdapter.allCheck();
                // 화면에 반영
            }
        });


        // 임시
        List<DeliveryRequestModelView> listTmp = new ArrayList<DeliveryRequestModelView>();
        listTmp = basicDeliveryRequestDatabase.basicDeliveryRequestProcessDao().getDeliveryRequestList();

        Log.d(TAG, " list : " + listTmp);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:

                String[] tmpStr = deliveryavt_date_picker_area.getText().toString().split(" ");
                requestSearchDay = tmpStr[0].toString();

                Intent intent = new Intent(DeliveryRequestActivity.this, MainActivity.class);
                intent.putExtra("requestSearchDay", requestSearchDay);
                Log.d("보낸다 main 으로 ", requestSearchDay);
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
    /**
     *
     */
    public void getDeliveryList(StringBuffer param, int _combination_key){

        if ( param == null || param.length() == 0){
            return;
        }

        combination_key = _combination_key;
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    roomDbRequest(response);

                }catch (Exception e){
                    Log.d("log ", e.toString());
                }finally {

                    deliveryRequestModelView = new DeliveryRequestModelView();
                    deliveryRequestModelView.setDelivery_count(successCnt);
                    deliveryRequestModelView.setDeliverycourse(param.toString());
                    deliveryRequestModelView.setReqdate(BasicUtils.getDays(Label.DELIVERY_STANDARD_DATE_FORMAT));
                    deliveryRequestModelView.setRequestdate(BasicUtils.getDays(Label.DELIVERY_STANDARD_DATE_FORMAT_TIME));
                    deliveryRequestModelView.setDeliverycoursenm(deliveryCourseName);
                    deliveryRequestModelView.setCombination_key(combination_key);
                    basicDeliveryRequestDatabase.basicDeliveryRequestProcessDao().isDeliveryRequestAdd(deliveryRequestModelView);
                    checkDeliveryDataCheck();

                    AlertDialog.Builder builder = new AlertDialog.Builder(DeliveryRequestActivity.this).setIcon(android.R.drawable.ic_btn_speak_now);
                    builder.setTitle("안내");
                    builder.setMessage("자료 수신 (총 "+successCnt+" 건) 완료" );
                    builder.setPositiveButton("확인",null);
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
                    successCnt = 0;
                    DeliveryModelView deliveryModelViewResult = null;
                    for ( int i=0; i < resultarray.length(); i++){
                        JSONObject Object = resultarray.getJSONObject(i);
                        deliveryModelViewResult = new DeliveryModelView();
                        deliveryModelViewResult.setBillno(Object.getString("billno"));
                        deliveryModelViewResult.setInput_date(Object.getString("input_date"));
                        deliveryModelViewResult.setInput_time(Object.getString("input_time"));
                        deliveryModelViewResult.setTranscode(Object.getString("transcode"));
                        deliveryModelViewResult.setSendingagencycode(Object.getString("sendingagencycode"));
                        deliveryModelViewResult.setArrivalagencycode(Object.getString("arrivalagencycode"));
                        deliveryModelViewResult.setSendingmantel(Object.getString("sendingmantel"));
                        deliveryModelViewResult.setSendingman(Object.getString("sendingman"));
                        deliveryModelViewResult.setArrivalmantel(Object.getString("arrivalmantel"));
                        deliveryModelViewResult.setArrivalman(Object.getString("arrivalman"));
                        deliveryModelViewResult.setZipcode(Object.getString("zipcode"));
                        deliveryModelViewResult.setAdress(Object.getString("adress"));
                        deliveryModelViewResult.setPrefare(Object.getString("prefare"));
                        deliveryModelViewResult.setFare(Object.getString("fare"));
                        deliveryModelViewResult.setDeliveryfare(Object.getString("deliveryfare"));
                        deliveryModelViewResult.setOgideliveryfare(Object.getString("ogideliveryfare"));
                        deliveryModelViewResult.setDistance(Object.getString("distance"));
                        deliveryModelViewResult.setPayway(Object.getString("payway"));
                        deliveryModelViewResult.setGoods(Object.getString("goods"));
                        deliveryModelViewResult.setPojang(Object.getString("pojang"));
                        deliveryModelViewResult.setQty(Object.getInt("qty"));
                        deliveryModelViewResult.setWeight(Object.getString("weight"));
                        deliveryModelViewResult.setMemo(Object.getString("memo"));
                        deliveryModelViewResult.setBillstate(Object.getString("billstate"));
                        // deliveryCourse 는 받는 순간 100번으로 변경
                        //deliveryModelView.setDeliverycourse(Object.getString("deliverycourse"));
                        deliveryModelViewResult.setDeliverycourse(deliveryCourse);
                        deliveryModelViewResult.setCreatdate(Object.getString("creatdate"));
                        deliveryModelViewResult.setDeliverycoursenm(Object.getString("deliverycoursenm"));

                        basicDeliveryDatabase.basicDeliveryProcessDao().applicationData_insert(deliveryModelViewResult);
                        successCnt ++;
                        deliveryCourseName = deliveryModelViewResult.getDeliverycoursenm();
                    }


                } catch(JSONException e){
                    e.printStackTrace();
                } finally {
                    roomDbCheck();
                }
            }
        };

        String[] tmpStr = deliveryavt_date_picker_area.getText().toString().split(" ");
        deliveryModelView.setCreatdate(tmpStr[0].toString() );
        DeliveryRequest deliveryRequest = new DeliveryRequest(deliveryModelView, responseListener, param  );
        RequestQueue queue = Volley.newRequestQueue( DeliveryRequestActivity.this );
        queue.add( deliveryRequest );
    }

    /**
     * 재처리
     * 메세지, 다시시도 Liner 숨기기
     */
    private void setReplayEvent(){
        // 메세지, 다시시도 화면 숨기기
        reqeust_false_txt.setVisibility(View.GONE);
        reqeust_false_replay.setVisibility(View.GONE);
        //setRequestStatus();

        CheckTypesTask tasking = new CheckTypesTask();
        tasking.execute();
    }

    /**
     * 화면에 들어오면 화면에 한번 뿌린다.
     * 청주우암 홍길동 10건 (자료받기)
     * 청주우암 김수각 20건 (자료받기)
     * 전체 자료 받기
     * 선택 자료 받기
     */
    private void setRequestStatus() {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Response.Listener<String> responseViewListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        requestDeliveryView(response);
                    }
                    /**
                     * view
                     * @param response
                     */
                    private void requestDeliveryView(String response) {

                        deliveryRequestModelViewList = basicDeliveryRequestDatabase.basicDeliveryRequestProcessDao().getDeliveryRequestList();
                        isResponse = true;

                        if ( response == null && response.length() == 0){
                            isResponse = false;
                        }else{
                            deliveryListViewItemList = new ArrayList<DeliveryListViewItem>();
                            RecyclerView recyclerView = findViewById(R.id.request_recyceler_view);

                            try {
                                if ( response != null && response.length() > 0){

                                    JSONObject jsonObject = new JSONObject(response);
                                    JSONArray resultarray = jsonObject.getJSONArray("rows");//배열의 이름

                                    for ( int i=0; i < resultarray.length(); i++){
                                        JSONObject Object = resultarray.getJSONObject(i);

                                        deliveryListViewItem = new DeliveryListViewItem();
                                        deliveryListViewItem.setArrivalagencycode(deliveryModelView.getArrivalagencycode());
                                        deliveryListViewItem.setDelivery_course(Object.getString("course"));
                                        deliveryListViewItem.setDelivery_course_name(Object.getString("course_name"));
                                        deliveryListViewItem.setDelivery_course_cnt(Object.getInt("course_cnt"));
                                        deliveryListViewItem.setCombination_key(Object.getInt("combination_key"));
                                        deliveryListViewItemList.add(deliveryListViewItem);
                                    }

                                    //setRequestStatusView(deliveryListViewItemList);
                                    deliveryListViewItem.setArrivalagencycode(deliveryModelView.getArrivalagencycode() == null ? "" :deliveryModelView.getArrivalagencycode());
                                    deliveryListViewItem.setDeliverycourse(deliveryModelView.getDeliverycourse() == null ? "" :deliveryModelView.getDeliverycourse());
                                    deliveryListViewItem.setCreatdate(deliveryModelView.getCreatdate() == null ? "" :deliveryModelView.getCreatdate());

                                    layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
                                    recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), 1)); // 아이템별 구분선 넣기
                                    recyclerView.setLayoutManager(layoutManager);
                                    deliverySummaryViewAdapter = new DeliverySummaryViewAdapter(getApplicationContext(), deliveryListViewItemList, DeliveryRequestActivity.this::onQuanitityChange, deliveryRequestModelViewList);
                                    recyclerView.setAdapter(deliverySummaryViewAdapter);

                                    if ( deliveryListViewItemList != null && deliveryListViewItemList.size() == 0){
                                        isResponse = false;
                                    }

                                }else{
                                    isResponse = false;
                                    Log.d("abcd1234 ",  "싹 해 야지요");
                                }
                            } catch(JSONException e){
                                e.printStackTrace();
                            } finally {
                                if ( !isResponse ){
                                    reqeust_false_txt.setVisibility(View.VISIBLE);
                                    reqeust_false_replay.setVisibility(View.VISIBLE);

                                    //request_all_pull_area.setVisibility(View.GONE);
                                    //request_btn.setVisibility(View.GONE);
                                }else{
                                    reqeust_false_txt.setVisibility(View.GONE);
                                    reqeust_false_replay.setVisibility(View.GONE);

                                    //request_all_pull_area.setVisibility(View.VISIBLE);
                                    //request_btn.setVisibility(View.VISIBLE);

                                }
                                layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
                                recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), 1)); // 아이템별 구분선 넣기
                                recyclerView.setLayoutManager(layoutManager);
                                deliverySummaryViewAdapter = new DeliverySummaryViewAdapter(getApplicationContext(), deliveryListViewItemList, DeliveryRequestActivity.this::onQuanitityChange, deliveryRequestModelViewList);
                                recyclerView.setAdapter(deliverySummaryViewAdapter);

                                deliverySummaryViewAdapter.setOnitemButtonClickListener(new DeliverySummaryViewAdapter.OnitemButtonClickListener() {
                                    @Override
                                    public void onItemButtonClick (View v, int pos) {

                                        // pos 들어온 체크박스가 체크인지 아닌지 여부 검사
                                        deliveryListViewItemList.get(pos).getArrivalagencycode();
                                        deliveryListViewItemList.get(pos).getDelivery_course();

                                        String tmpStr = deliveryListViewItemList.get(pos).getArrivalagencycode() + ", " +deliveryListViewItemList.get(pos).getDelivery_course();
                                        //Toast.makeText(getApplicationContext(), "받아올 자료 선택===========================================여기 . : " + tmpStr, Toast.LENGTH_LONG ).show();

                                    }
                                });

                                deliverySummaryViewAdapter.setOnitemClickListener(new DeliverySummaryViewAdapter.OnitemClickListener() {
                                    @Override
                                    public void onItemClick(View v, int pos) {
                                        // 버튼 클릭 시 해당 로우의 영업소 코드와 배달코스를 받아온다
                                        deliveryListViewItemList.get(pos).getArrivalagencycode();
                                        deliveryListViewItemList.get(pos).getDelivery_course();
                                        String tmpStr = deliveryListViewItemList.get(pos).getArrivalagencycode() + ", " +deliveryListViewItemList.get(pos).getDelivery_course();
                                        //Toast.makeText(getApplicationContext(), "받아올 자료 선택 : " + tmpStr, Toast.LENGTH_LONG ).show();
                                        privateParam = new StringBuffer();
                                        privateParam.append(deliveryListViewItemList.get(pos).getDelivery_course());
                                        getListTypesTask getListTypesTask = new getListTypesTask(privateParam, deliveryListViewItemList.get(pos).getCombination_key());
                                        getListTypesTask.execute();
                                    }
                                });
                            }
                        }
                    }
                };
                /**
                 * param setting
                 */
                deliveryListViewItem = new DeliveryListViewItem();
                deliveryListViewItem.setArrivalagencycode(agencyCode == null ? "" : agencyCode);
                deliveryListViewItem.setDeliverycourse(deliveryCourse == null ? "" : deliveryCourse);
                deliveryListViewItem.setCreatdate(deliveryModelView.getCreatdate() == null ? "" :deliveryModelView.getCreatdate());

                Log.d("===> ", ""+ TextUtils.isEmpty(deliveryListViewItem.getArrivalagencycode()));
                Log.d("===> ", ""+  TextUtils.isEmpty(deliveryListViewItem.getDelivery_course()));
                Log.d("===> ", ""+  TextUtils.isEmpty(requestSearchDay));
                if ( TextUtils.isEmpty(deliveryListViewItem.getArrivalagencycode()) || TextUtils.isEmpty(deliveryListViewItem.getDelivery_course()) || TextUtils.isEmpty(deliveryListViewItem.getCreatdate())){
                    //return;
                }

                DeliveryRequestSummary deliveryRequestSummary = new DeliveryRequestSummary(deliveryListViewItem, responseViewListener); // <-- 파라미터 체크(deliveryCourseParam)
                RequestQueue queue = Volley.newRequestQueue( DeliveryRequestActivity.this );
                queue.add( deliveryRequestSummary );
            }
        });
    }

    @Override
    public void onQuanitityChange(ArrayList<String> arrayList) {
        Toast.makeText(this, arrayList.toString(), Toast.LENGTH_LONG).show();
    }

    /**
     * 가져온 자료 화면에 표기
     */
    @SuppressLint("StaticFieldLeak")
    private class CheckTypesTask extends AsyncTask<Void, Void, Void> {

        ProgressDialog asyncDialog = new ProgressDialog(DeliveryRequestActivity.this);

        @Override
        protected void onPreExecute() {
            asyncDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            asyncDialog.setMessage("자료를 확인중입니다..");

            // show dialog
            asyncDialog.show();
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            try {

                setRequestStatus();
                Thread.sleep(200 * 5);
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
     *
     */
    private class getListTypesTask extends AsyncTask<Void, Void, Void> {

        ProgressDialog asyncDialog = new ProgressDialog(DeliveryRequestActivity.this);
        private StringBuffer sbr = new StringBuffer();
        private int combination_key;
        public getListTypesTask(StringBuffer privateParam, int _combination_key) {
            sbr = privateParam;
            combination_key = _combination_key;
        }

        @Override
        protected void onPreExecute() {
            asyncDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            asyncDialog.setMessage("자료를 확인중입니다..");

            // show dialog
            asyncDialog.show();
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            try {
                getDeliveryList(sbr, combination_key);
                Thread.sleep(300 * 5);
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
     *
     */
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

    private void roomDbCheck(){
        DeliveryDao deliveryDao = new DeliveryDao(getApplicationContext());
        List<DeliveryModelView> asr = new ArrayList<DeliveryModelView>();
        asr = deliveryDao.getDeliveryList();
        if ( asr != null ){
            DeliveryModelView tmpEntity = new DeliveryModelView();
            for ( int i =0; i < asr.size();i++){
                tmpEntity = asr.get(i);
                Log.d(">> ", tmpEntity.toString() );
            }
        }
    }

    private void checkDeliveryDataCheck(){
        List<DeliveryRequestModelView> arr = new ArrayList<DeliveryRequestModelView>();
        DeliveryRequestModelView entity = null;
        entity = new DeliveryRequestModelView();
        arr = basicDeliveryRequestDatabase.basicDeliveryRequestProcessDao().getDeliveryRequestList();
        if ( arr != null && arr.size() > 0){
            Log.d(TAG + "============ checkDeliveryDataCheck",arr + "");
        }else{
            Log.d(TAG , "no data");
        }
    }
}