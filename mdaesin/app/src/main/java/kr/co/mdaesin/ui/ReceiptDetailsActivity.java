package kr.co.mdaesin.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import kr.co.mdaesin.MainActivity;
import kr.co.mdaesin.R;
import kr.co.mdaesin.action.request.ReceiptDetailsRequest;
import kr.co.mdaesin.adapter.ReceptDetailsAdapter;
import kr.co.mdaesin.adapter.ReceptDetailsUnsongAdapter;
import kr.co.mdaesin.comm.Label;
import kr.co.mdaesin.models.ReceiptDetailsModelView;
import kr.co.mdaesin.models.ReceptionQuantityModelView;

public class ReceiptDetailsActivity extends AppCompatActivity {
    private String TAG = " :: ReceiptDetailsActivity.LOG";

    TextView details_top_title, details_top_title2;

    private ReceptionQuantityModelView receptionQuantityModelView;

    private ReceiptDetailsModelView receiptDetailsModelView;
    private List<ReceiptDetailsModelView> receiptDetailsModelViewList;

    private ReceptionQuantityModelView result_receptionQuantityModelView;
    private List<ReceptionQuantityModelView> result_ReceptionQuantityModelViewList;

    private RecyclerView recyclerView;
    private RecyclerView recyclerView_unsong;
    private ReceptDetailsAdapter receptDetailsAdapter;
    private ReceptDetailsUnsongAdapter receptDetailsUnsongAdapter;

    private boolean isSuccess = false;
    ProgressDialog asyncDialog;
    ProgressDialog asyncDialogUnsong;

    private Response.Listener<String> responseListener;
    private Response.Listener<String> responseListenerUnsong;

    private static int NUMBER_OF_CORES = Runtime.getRuntime().availableProcessors();
    ExecutorService executorService = Executors.newFixedThreadPool(NUMBER_OF_CORES);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipt_details);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.title_receipt);

        Intent intent = getIntent();
        receptionQuantityModelView = (ReceptionQuantityModelView) intent.getSerializableExtra("receptionQuantityModelView");

        Log.d(TAG, "onCreate: " + receptionQuantityModelView.getLinecode());
        Log.d(TAG, "onCreate: " + receptionQuantityModelView.getSearchKeyword_date());

        details_top_title = (TextView) findViewById(R.id.details_top_title);
        details_top_title.setText(receptionQuantityModelView.getLinename() + " (" + receptionQuantityModelView.getLinecode()+")");

        details_top_title2 = (TextView) findViewById(R.id.details_top_title2);
        details_top_title2.setText(receptionQuantityModelView.getSearchKeyword_date());

        asyncDialogUnsong = new ProgressDialog(ReceiptDetailsActivity.this);
        asyncDialogUnsong.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        asyncDialogUnsong.setMessage("야 쫌만 기다려봐 ~ ");
        // show dialog
        asyncDialogUnsong.show();
        asyncDialogUnsong.setCanceledOnTouchOutside(false);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

        receiptDetailsModelViewList = new ArrayList<ReceiptDetailsModelView>();
        result_ReceptionQuantityModelViewList = new ArrayList<ReceptionQuantityModelView>();

        Handler mHandler = new Handler(Looper.getMainLooper());
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Thread datathread = new Thread(){
                    @Override
                    public void run(){
                        getDetailsList();
                    }
                };
                datathread.start();
                try {
                    datathread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    Log.d(TAG, "run: 스레드 종료......");
                }
            }
        }, 100);


        responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray resultarray = jsonObject.getJSONArray("details");

                    if ( resultarray.length() > 0){
                        for ( int i=0; i < resultarray.length(); i++){
                            JSONObject Object = resultarray.getJSONObject(i);

                            receiptDetailsModelView = new ReceiptDetailsModelView();
                            receiptDetailsModelView.setAgencyname(Object.getString("agencyname"));
                            receiptDetailsModelView.setMd(Object.getString("md"));
                            receiptDetailsModelView.setCnt(Object.getString("cnt"));
                            receiptDetailsModelView.setQty(Object.getString("qty"));
                            receiptDetailsModelView.setFare(Object.getString("fare"));
                            receiptDetailsModelView.setStd_departuretime(Object.getString("std_departuretime"));
                            receiptDetailsModelView.setStd_deadlinetime(Object.getString("std_deadlinetime"));
                            receiptDetailsModelView.setAgencytel(Object.getString("agencytel"));
                            Log.d(TAG, "onResponse: " + i +"번째 자료 수신중" );
                            receiptDetailsModelViewList.add(receiptDetailsModelView);

                        }

                        if ( receiptDetailsModelViewList != null && receiptDetailsModelViewList.size() > 0 ){
                            //receptionQuantityAdapter.notifyDataSetChanged();
                            recyclerView = findViewById(R.id.receipt_details_recyceler_view);
                            recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), 1)); // 아이템별 구분선 넣기
                            recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

                            receptDetailsAdapter = new ReceptDetailsAdapter(receiptDetailsModelViewList);
                            recyclerView.setAdapter(receptDetailsAdapter);

                            Log.d(TAG, "onResponse: 수신 완료되어 화면에 뿌림 " );

                        }
                    }


                    JSONArray resultarrayUnsong = jsonObject.getJSONArray("unsong");

                    if ( resultarrayUnsong.length() > 0){
                        for ( int i=0; i < resultarrayUnsong.length(); i++){
                            JSONObject ObjectUnsong = resultarrayUnsong.getJSONObject(i);

                            result_receptionQuantityModelView = new ReceptionQuantityModelView();
                            result_receptionQuantityModelView.setBillNo(ObjectUnsong.getString("billno"));
                            result_receptionQuantityModelView.setSendingagencyname(ObjectUnsong.getString("sendingagencyname"));
                            result_receptionQuantityModelView.setArrivalagencyname(ObjectUnsong.getString("arrivalagencyname"));
                            result_receptionQuantityModelView.setArrivalman(ObjectUnsong.getString("arrivalman"));
                            result_receptionQuantityModelView.setGoods(ObjectUnsong.getString("goods"));
                            result_receptionQuantityModelView.setPojang(ObjectUnsong.getString("pojang"));
                            result_receptionQuantityModelView.setQty(ObjectUnsong.getString("qty"));
                            result_receptionQuantityModelView.setPrefare(ObjectUnsong.getString("prefare"));
                            result_receptionQuantityModelView.setFare(ObjectUnsong.getString("fare"));
                            result_receptionQuantityModelView.setDeliveryfare(ObjectUnsong.getString("deliveryfare"));
                            result_receptionQuantityModelView.setPayway(ObjectUnsong.getString("payway"));
                            Log.d(TAG, "ObjectUnsong: " + i +"번째 자료 수신중" +  ObjectUnsong.getString("billno"));
                            result_ReceptionQuantityModelViewList.add(result_receptionQuantityModelView);

                            asyncDialogUnsong.setProgress(i * 1);

                        }
                        if ( result_ReceptionQuantityModelViewList != null && result_ReceptionQuantityModelViewList.size() > 0 ){
                            //receptionQuantityAdapter.notifyDataSetChanged();
                            recyclerView_unsong = findViewById(R.id.receipt_details_recyceler_unsong_view);
                            recyclerView_unsong.addItemDecoration(new DividerItemDecoration(getApplicationContext(), 1)); // 아이템별 구분선 넣기
                            recyclerView_unsong.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

                            receptDetailsUnsongAdapter = new ReceptDetailsUnsongAdapter(result_ReceptionQuantityModelViewList);
                            recyclerView_unsong.setAdapter(receptDetailsUnsongAdapter);

                        }

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {

                    asyncDialogUnsong.dismiss();
                    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                }
            }
        };

        responseListenerUnsong = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray resultarray = jsonObject.getJSONArray("unsong");

                    if ( resultarray.length() > 0){
                        for ( int i=0; i < resultarray.length(); i++){
                            JSONObject Object = resultarray.getJSONObject(i);

                            result_receptionQuantityModelView = new ReceptionQuantityModelView();
                            result_receptionQuantityModelView.setBillNo(Object.getString("billno"));
                            result_receptionQuantityModelView.setSendingagencyname(Object.getString("sendingagencyname"));
                            result_receptionQuantityModelView.setArrivalagencyname(Object.getString("arrivalagencyname"));
                            result_receptionQuantityModelView.setArrivalman(Object.getString("arrivalman"));
                            result_receptionQuantityModelView.setGoods(Object.getString("goods"));
                            result_receptionQuantityModelView.setPojang(Object.getString("pojang"));
                            result_receptionQuantityModelView.setQty(Object.getString("qty"));
                            result_receptionQuantityModelView.setPrefare(Object.getString("prefare"));
                            result_receptionQuantityModelView.setFare(Object.getString("fare"));
                            result_receptionQuantityModelView.setDeliveryfare(Object.getString("deliveryfare"));
                            result_receptionQuantityModelView.setPayway(Object.getString("payway"));

                            result_ReceptionQuantityModelViewList.add(result_receptionQuantityModelView);

                        }

                        if ( result_ReceptionQuantityModelViewList != null && result_ReceptionQuantityModelViewList.size() > 0 ){
                            //receptionQuantityAdapter.notifyDataSetChanged();
                            recyclerView_unsong = findViewById(R.id.receipt_details_recyceler_unsong_view);
                            recyclerView_unsong.addItemDecoration(new DividerItemDecoration(getApplicationContext(), 1)); // 아이템별 구분선 넣기
                            recyclerView_unsong.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

                            receptDetailsUnsongAdapter = new ReceptDetailsUnsongAdapter(result_ReceptionQuantityModelViewList);
                            recyclerView_unsong.setAdapter(receptDetailsUnsongAdapter);

                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                    asyncDialogUnsong.dismiss();
                    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                }
            }
        };

    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == android.R.id.home ){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void getDetailsList(){

        receptionQuantityModelView.setSearchMode(Label.DELIVERY_BASE_URL_RECEIPT_DETAILS);
        ReceiptDetailsRequest receiptListRequest = new ReceiptDetailsRequest(receptionQuantityModelView, responseListener);
        RequestQueue queue = Volley.newRequestQueue( ReceiptDetailsActivity.this);
        queue.add(receiptListRequest);

    }

    private void getDetailsUnsongList(){

        receptionQuantityModelView.setSearchMode(Label.DELIVERY_BASE_URL_RECEIPT_DETAILS_UNSONG);
        ReceiptDetailsRequest receiptListRequests = new ReceiptDetailsRequest(receptionQuantityModelView, responseListenerUnsong);
        RequestQueue queues = Volley.newRequestQueue( ReceiptDetailsActivity.this);
        queues.add(receiptListRequests);
    }

}