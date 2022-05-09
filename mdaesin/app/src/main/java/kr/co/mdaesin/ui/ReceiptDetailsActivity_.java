package kr.co.mdaesin.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

import kr.co.mdaesin.R;
import kr.co.mdaesin.action.request.ReceiptDetailsRequest;
import kr.co.mdaesin.adapter.ReceptDetailsAdapter;
import kr.co.mdaesin.adapter.ReceptDetailsUnsongAdapter;
import kr.co.mdaesin.comm.Label;
import kr.co.mdaesin.models.ReceiptDetailsModelView;
import kr.co.mdaesin.models.ReceptionQuantityModelView;

public class ReceiptDetailsActivity_ extends AppCompatActivity {
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

    private ReceiptUnsongThread receiptUnsongThread;
    private Handler handler;

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


        handler = new Handler(Looper.getMainLooper());

        final Handler responseHandler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {

                /* Processing handleMessage */

                Toast.makeText(ReceiptDetailsActivity_.this, "Runnable completed with result:"+(String)msg.obj, Toast.LENGTH_LONG) .show();
            }
        };


        responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray resultarray = jsonObject.getJSONArray("rows");

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

                        }else{
                            Log.d("=== not found", "");
                            // 없다고 표기
                        }

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {

                }
            }
        };

        responseListenerUnsong = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray resultarray = jsonObject.getJSONArray("rows");

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
                            Log.d(TAG, "onResponse: ======================> 두번째");
                        }else{
                            Log.d("=== not found", "");
                            // 없다고 표기
                        }

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {

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

    /**
     * 자료 가져오기
     */
    public void setReceptDetailsModelView(){

        /*asyncDialog = new ProgressDialog(ReceiptDetailsActivity.this);

        asyncDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        asyncDialog.setMessage("자료 확인중... ");
        asyncDialog.show();
        asyncDialog.setCanceledOnTouchOutside(false);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);*/


        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                try {
                    receiptDetailsModelViewList = new ArrayList<ReceiptDetailsModelView>();

                    // RD 요청
                    receptionQuantityModelView.setSearchMode(Label.DELIVERY_BASE_URL_RECEIPT_DETAILS);
                    ReceiptDetailsRequest receiptListRequest = new ReceiptDetailsRequest(receptionQuantityModelView, responseListener);
                    RequestQueue queue = Volley.newRequestQueue( ReceiptDetailsActivity_.this);
                    queue.add(receiptListRequest);

                }catch (Exception e){
                    e.printStackTrace();
                }finally {

                }
            }
        });
        //asyncDialog.dismiss();
        //getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    private void setReceptDetailUnsongsModelView() {

        asyncDialog = new ProgressDialog(ReceiptDetailsActivity_.this);
        Log.d(TAG, "setReceptDetailUnsongsModelView: --------------------------------------->1");
        asyncDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        asyncDialog.setMessage("자료 확인중... ");
        asyncDialog.show();
        asyncDialog.setCanceledOnTouchOutside(false);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        Log.d(TAG, "setReceptDetailUnsongsModelView: --------------------------------------->2");
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                try {
                    result_ReceptionQuantityModelViewList = new ArrayList<ReceptionQuantityModelView>();

                    // RU 요청
                    receptionQuantityModelView.setSearchMode(Label.DELIVERY_BASE_URL_RECEIPT_DETAILS_UNSONG);
                    ReceiptDetailsRequest receiptListRequests = new ReceiptDetailsRequest(receptionQuantityModelView, responseListenerUnsong);
                    RequestQueue queues = Volley.newRequestQueue( ReceiptDetailsActivity_.this);
                    queues.add(receiptListRequests);

                }catch (Exception e){
                    e.printStackTrace();
                }finally {

                }
            }
        });

    }

    private class CheckTypesTask extends AsyncTask<Void, Void, Void> {

        ProgressDialog asyncDialog = new ProgressDialog(ReceiptDetailsActivity_.this);

        @Override
        protected void onPreExecute() {
            asyncDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            asyncDialog.setMessage("야 쫌만 기다려봐 ~ ");
            // show dialog
            asyncDialog.show();
            asyncDialog.setCanceledOnTouchOutside(false);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            super.onPreExecute();

        }

        @Override
        protected Void doInBackground(Void... arg0) {

            try {
                receiptDetailsModelViewList = new ArrayList<ReceiptDetailsModelView>();
                receptionQuantityModelView.setSearchMode(Label.DELIVERY_BASE_URL_RECEIPT_DETAILS);
                ReceiptDetailsRequest receiptListRequest = new ReceiptDetailsRequest(receptionQuantityModelView, responseListener);
                RequestQueue queue = Volley.newRequestQueue( ReceiptDetailsActivity_.this);
                queue.add(receiptListRequest);

            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {

            if ( asyncDialog != null && asyncDialog.isShowing()){
                asyncDialog.dismiss();
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

                CheckTypesTaskUnsong taskUnsong = new CheckTypesTaskUnsong();
                taskUnsong.execute();
            }
            super.onPostExecute(result);
        }
    }

    private class CheckTypesTaskUnsong extends AsyncTask<Void, Void, Void> {

        ProgressDialog asyncDialogUnsong = new ProgressDialog(ReceiptDetailsActivity_.this);
        ReceiptDetailThread receiptDetailThread;
        ReceiptUnsongThread receiptUnsongThread;
        @Override
        protected void onPreExecute() {
            asyncDialogUnsong.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            asyncDialogUnsong.setMessage("야 쫌만 기다려봐 ~ ");
            // show dialog
            asyncDialogUnsong.show();
            asyncDialogUnsong.setCanceledOnTouchOutside(false);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            super.onPreExecute();

        }

        @Override
        protected Void doInBackground(Void... arg0) {

            try {
                //receiptDetailThread = new ReceiptDetailThread();
                //receiptUnsongThread = new ReceiptUnsongThread();

                //receiptDetailThread.start();
                //receiptUnsongThread.start();

                receiptDetailsModelViewList = new ArrayList<ReceiptDetailsModelView>();
                receptionQuantityModelView.setSearchMode(Label.DELIVERY_BASE_URL_RECEIPT_DETAILS);
                ReceiptDetailsRequest receiptListRequest = new ReceiptDetailsRequest(receptionQuantityModelView, responseListener);
                RequestQueue queue = Volley.newRequestQueue( ReceiptDetailsActivity_.this);
                queue.add(receiptListRequest);

                /*for (int i = 0; i < 1; i++) {
                    asyncDialogUnsong.setProgress(i * 30);
                    Thread.sleep(500);
                }

                result_ReceptionQuantityModelViewList = new ArrayList<ReceptionQuantityModelView>();
                receptionQuantityModelView.setSearchMode(Label.DELIVERY_BASE_URL_RECEIPT_DETAILS_UNSONG);
                ReceiptDetailsRequest receiptListRequests = new ReceiptDetailsRequest(receptionQuantityModelView, responseListenerUnsong);
                RequestQueue queues = Volley.newRequestQueue( ReceiptDetailsActivity.this);
                queues.add(receiptListRequests);*/



            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {

            for (int i = 0; i < 5; i++) {
                asyncDialogUnsong.setProgress(i * 30);
            }
            try {

                Thread.sleep(500);
                Log.d(TAG, "onPostExecute: 끝났을까하고asyncDialogUnsong 종료 ");
                asyncDialogUnsong.dismiss();
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            super.onPostExecute(result);
        }


    }

    private class ReceiptDetailThread extends Thread{
        private static final String TAG = "ReceiptDetailThread";

        public ReceiptDetailThread() {
            super();
        }

        @Override
        public void run() {
            super.run();
            try {
                Log.d(TAG, "=====================ReceiptDetailThread ");
                receiptDetailsModelViewList = new ArrayList<ReceiptDetailsModelView>();
                receptionQuantityModelView.setSearchMode(Label.DELIVERY_BASE_URL_RECEIPT_DETAILS);
                ReceiptDetailsRequest receiptListRequest = new ReceiptDetailsRequest(receptionQuantityModelView, responseListener);
                RequestQueue queue = Volley.newRequestQueue( ReceiptDetailsActivity_.this);
                queue.add(receiptListRequest);

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    private class ReceiptUnsongThread extends Thread{
        private static final String TAG = "ReceiptUnsongThread";

        public ReceiptUnsongThread() {
            super();

        }

        @Override
        public void run() {
            super.run();
            asyncDialogUnsong = new ProgressDialog(ReceiptDetailsActivity_.this);
            asyncDialogUnsong.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            asyncDialogUnsong.setMessage("야 쫌만 기다려봐 ~ ");
            // show dialog
            asyncDialogUnsong.show();
            asyncDialogUnsong.setCanceledOnTouchOutside(false);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);


            try {
                Log.d(TAG, "================= ReceiptUnsongThread ");
                result_ReceptionQuantityModelViewList = new ArrayList<ReceptionQuantityModelView>();
                receptionQuantityModelView.setSearchMode(Label.DELIVERY_BASE_URL_RECEIPT_DETAILS_UNSONG);
                ReceiptDetailsRequest receiptListRequests = new ReceiptDetailsRequest(receptionQuantityModelView, responseListenerUnsong);
                RequestQueue queues = Volley.newRequestQueue( ReceiptDetailsActivity_.this);
                queues.add(receiptListRequests);

                Thread.sleep(1000);

            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                asyncDialogUnsong.dismiss();
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            }
        }
    }

}