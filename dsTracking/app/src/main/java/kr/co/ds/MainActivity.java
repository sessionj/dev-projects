package kr.co.ds;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.List;

import kr.co.ds.action.TrackingListRequest;
import kr.co.ds.adapter.TrackingListAdapter;
import kr.co.ds.comm.Label;
import kr.co.ds.comm.SharedPreferenceConf;
import kr.co.ds.models.TrackingModelView;
import kr.co.ds.ui.TrackingViewActivity;

public class MainActivity extends AppCompatActivity {

    // 총 주문건수 : 10건, 완료 10건
    TextView tracking_row_in_1, emplist;
    ProgressBar progressBar;
    RecyclerView recyclerView;
    Button main_send_btn, main_req_btn;
    private static final String TAG = "MainActivity";
    private long backPressedTime = 0;
    private final long FINISH_INTERVAL_TIME = 2000;
    private TrackingModelView model;
    private TrackingModelView resultModel;
    private List<TrackingModelView> resultModelList;
    private TrackingListAdapter adapter;
    private SwipeRefreshLayout mysrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkTitle();
        tracking_row_in_1 = (TextView) findViewById(R.id.tracking_row_in_1);
        progressBar = (ProgressBar) findViewById(R.id.tracking_progressBar);
        recyclerView = findViewById(R.id.tracking_recyclerView);
        emplist = (TextView) findViewById(R.id.tracking_list_empty);
        main_send_btn = (Button) findViewById(R.id.main_send_btn);
        main_req_btn = (Button) findViewById(R.id.main_req_btn);
        modelSetting();

        /*mysrl = findViewById(R.id.content_srl);
        mysrl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                modelSetting();
            }
        });*/

        main_send_btn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                main_send_btn.setBackgroundColor(Color.parseColor("#FF9800"));
                main_send_btn.setTextColor(Color.WHITE);



                main_req_btn.setBackgroundColor(Color.WHITE);
                main_req_btn.setTextColor(Color.parseColor("#FF9800"));
                //main_req_btn.setBackground(getResources().getDrawable(R.drawable.down_icon) );

            }
        });

        main_req_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                main_req_btn.setBackgroundColor(Color.parseColor("#FF9800"));
                main_req_btn.setTextColor(Color.WHITE);
                //main_req_btn.setBackground(getResources().getDrawable(R.drawable.down_icon) );

                main_send_btn.setBackgroundColor(Color.WHITE);
                main_send_btn.setTextColor(Color.parseColor("#FF9800"));
                //main_send_btn.setBackground(getResources().getDrawable(R.drawable.up_icon) );
            }
        });

    }

    protected void modelSetting(){
        setProgressBar(1);
        model = new TrackingModelView();
        model.setSearchMode(Label.DELIVERY_BASE_URL_TRACKING_LIST);
        model.setArrivalmantel(SharedPreferenceConf.getPhoneNumber(this)); // 임시임

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run(){
                getTrackingList();
            }
        }, 1000);
    }

    protected void getTrackingList() {

        TrackingListRequest listRequest = new TrackingListRequest(model, successListener(), errorListener());
        RequestQueue queue = Volley.newRequestQueue( MainActivity.this);
        queue.add(listRequest);
    }

    private Response.Listener<String> successListener() {
        return new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                //recyclerView.setVisibility(View.VISIBLE);
                //emplist.setVisibility(View.GONE);
                try {
                    if ( !response.isEmpty() && response != null){
                        JSONObject jsonObject = new JSONObject(response);
                        JSONArray resultarray = jsonObject.getJSONArray("rows");

                        if (resultarray.length() > 0) {
                            resultModelList = new ArrayList<TrackingModelView>();

                            for (int i = 0; i < resultarray.length(); i++) {
                                JSONObject Object = resultarray.getJSONObject(i);

                                resultModel = new TrackingModelView();
                                resultModel.setBillno(Object.getString("billno"));
                                resultModel.setSendname(Object.getString("sendname"));
                                resultModel.setSendingman(Object.getString("sendingman"));
                                resultModel.setArrivalman(Object.getString("arrivalman"));
                                resultModel.setSendingday(Object.getString("sendingday"));
                                resultModel.setRelaystart1(Object.getString("relaystart1"));
                                resultModel.setRelaystart2(Object.getString("relaystart2"));
                                resultModel.setRelaystart3(Object.getString("relaystart3"));
                                resultModel.setRelaystart4(Object.getString("relaystart4"));
                                resultModel.setRelayend1(Object.getString("relayend1"));
                                resultModel.setRelayend2(Object.getString("relayend2"));
                                resultModel.setRelayend3(Object.getString("relayend3"));
                                resultModel.setRelayend4(Object.getString("relayend4"));
                                resultModel.setLand1name(Object.getString("land1name"));
                                resultModel.setLand2name(Object.getString("land2name"));
                                resultModel.setLand3name(Object.getString("land3name"));
                                resultModel.setArrivalname(Object.getString("arrivalname"));
                                resultModel.setSendingmantel(Object.getString("sendingmantel"));
                                resultModel.setSendtel(Object.getString("sendtel"));
                                resultModel.setLand1tel(Object.getString("land1tel"));
                                resultModel.setLand2tel(Object.getString("land2tel"));
                                resultModel.setLand3name(Object.getString("land3tel"));
                                resultModel.setArrivaltel(Object.getString("arrivaltel"));
                                resultModel.setArrivalmantel(Object.getString("arrivalmantel"));
                                resultModel.setStatename(Object.getString("statename"));
                                resultModel.setBillstate(Object.getString("billstate"));
                                resultModel.setCnt(Object.getInt("cnt"));
                                resultModel.setTbanprintyn(Object.getString("tbanprintyn"));
                                resultModel.setChulgoday(Object.getString("chulgoday"));
                                resultModel.setTranscode(Object.getString("transcode"));
                                resultModel.setSt1(Object.getString("st1"));
                                resultModel.setSt2(Object.getString("st2"));
                                resultModel.setSt3(Object.getString("st3"));
                                resultModel.setSt4(Object.getString("st4"));
                                resultModel.setSt5(Object.getString("st5"));
                                resultModel.setJubsuday(Object.getString("jubsuday"));
                                resultModel.setInsuja(Object.getString("insuja"));
                                resultModel.setGoods(Object.getString("goods"));
                                resultModel.setPojang(Object.getString("pojang"));
                                resultModel.setQty(Object.getString("qty"));
                                resultModel.setAgency1(Object.getString("agency1"));
                                resultModel.setAgency2(Object.getString("agency2"));
                                resultModel.setAgency3(Object.getString("agency3"));
                                resultModel.setAgency4(Object.getString("agency4"));
                                resultModel.setAgency5(Object.getString("agency5"));
                                resultModel.setInternettel1(Object.getString("internettel1"));
                                resultModel.setInternettel2(Object.getString("internettel2"));
                                resultModel.setInternettel3(Object.getString("internettel3"));
                                resultModel.setInternettel4(Object.getString("internettel4"));
                                resultModel.setInternettel5(Object.getString("internettel5"));
                                resultModel.setCourierunavailable(Object.getString("courierunavailable"));
                                resultModel.setArrivalagencycode(Object.getString("arrivalagencycode"));
                                resultModel.setScaninfo(Object.getString("scaninfo"));
                                resultModel.setArea(Object.getString("area"));
                                ;
                                resultModelList.add(resultModel);
                            }

                            if (resultModelList != null && resultModelList.size() > 0) {

                                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                                adapter = new TrackingListAdapter(resultModelList);
                                recyclerView.setAdapter(adapter);

                                adapter.setOnitemClickListener(new TrackingListAdapter.OnitemClickListener() {
                                    @Override
                                    public void onItemClick(View v, int seq) {
                                        Toast.makeText(getApplicationContext(), resultModelList.get(seq).getBillno(), Toast.LENGTH_SHORT).show();

                                        TrackingModelView putModel = new TrackingModelView();
                                        putModel = resultModelList.get(seq);
                                        Intent intent = new Intent(getApplicationContext(), TrackingViewActivity.class);
                                        intent.putExtra("viewMode", putModel);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(intent);

                                    }
                                });
                            }
                        }
                    }else{
                        //recyclerView.setVisibility(View.GONE);
                        //emplist.setVisibility(View.VISIBLE);
                    }
                } catch(JSONException e) {
                    e.printStackTrace();
                } finally {
                    if ( resultModelList != null && resultModelList.size() > 0){
                        tracking_row_in_1.setText(adapter.totalInformation().toString());
                    }
                   setProgressBar(2);
                }
            }
        };
    }

    private Response.ErrorListener errorListener() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                setProgressBar(2);
                error.printStackTrace();
            }
        };
    }

    protected void setProgressBar(int mode){
        if ( mode == 1){
            progressBar.setVisibility(View.VISIBLE);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        }else{
            progressBar.setVisibility(View.GONE);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        }
    }

    private void checkTitle(){

        ActionBar ac = getSupportActionBar();
        String word = Label.RECEIPT_CONSTRUCTOR_FINAL_DAESIN;
        String content = ac.getTitle().toString();
        int start = content.indexOf(word);
        int end = start + word.length();

        SpannableString spannableString = new SpannableString(content);
        spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#A9E2F3")), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new StyleSpan(Typeface.NORMAL), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new RelativeSizeSpan(1.1f), start, end, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE);

        ac.setTitle(spannableString);
    }

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
}
