package kr.co.mdaesin.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import kr.co.mdaesin.MainActivity;
import kr.co.mdaesin.R;
import kr.co.mdaesin.action.request.ReceiptDetailsRequest;
import kr.co.mdaesin.adapter.ReceptDetailsAdapter;
import kr.co.mdaesin.models.ReceiptDetailsModelView;
import kr.co.mdaesin.models.ReceptionQuantityModelView;

public class ReceiptDetailsActivity extends AppCompatActivity {
    private String TAG = " :: ReceiptDetailsActivity.LOG";

    TextView details_top_title, details_top_title2;

    private ReceptionQuantityModelView receptionQuantityModelView;
    private ReceiptDetailsModelView receiptDetailsModelView;
    private List<ReceiptDetailsModelView> receiptDetailsModelViewList;
    private RecyclerView recyclerView;
    private ReceptDetailsAdapter receptDetailsAdapter;
    private SwipeRefreshLayout mysrl;

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

        CheckTypesTask task = new CheckTypesTask();
        task.execute();

        mysrl = findViewById(R.id.content_srl);
        mysrl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                CheckTypesTask task2 = new CheckTypesTask();
                task2.execute();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == android.R.id.home ){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private class CheckTypesTask extends AsyncTask<Void, Void, Void> {

        ProgressDialog asyncDialog = new ProgressDialog(ReceiptDetailsActivity.this);

        @Override
        protected void onPreExecute() {
            asyncDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            asyncDialog.setMessage("야 쫌만 기다려봐 ~ ");
            // show dialog
            asyncDialog.show();
            asyncDialog.setCanceledOnTouchOutside(false);
            super.onPreExecute();

            getWindow().addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        }

        @Override
        protected Void doInBackground(Void... arg0) {

            try {
                setRceptDetailsModelView();
                for (int i = 0; i < 3; i++) {
                    asyncDialog.setProgress(i * 30);
                    Thread.sleep(500);
                }

            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            // 백그라운드 스레드가 완료되면 Layout 출력 시작
            loadRceptDetailsModelView();
            mysrl.setRefreshing(false);
            asyncDialog.dismiss();
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            super.onPostExecute(result);
        }
    }

    /**
     * 자료 가져오기
     */
    public void setRceptDetailsModelView(){

        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                try {
                    receiptDetailsModelViewList = new ArrayList<ReceiptDetailsModelView>();
                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                JSONArray resultarray = jsonObject.getJSONArray("rows");

                                if ( resultarray.length() > 0){
                                    for ( int i=0; i < resultarray.length(); i++){
                                        JSONObject Object = resultarray.getJSONObject(i);
                                        /**
                                         *  private String agencyname;
                                         *     private String md;
                                         *     private int cnt;
                                         *     private int qty;
                                         *     private double fare;
                                         *     private String std_departuretime;
                                         *     private String std_deadlinetime;
                                         *     private String agencytel;
                                         */
                                        receiptDetailsModelView = new ReceiptDetailsModelView();
                                        receiptDetailsModelView.setAgencyname(Object.getString("agencyname"));
                                        receiptDetailsModelView.setMd(Object.getString("md"));
                                        receiptDetailsModelView.setCnt(Object.getString("cnt"));
                                        receiptDetailsModelView.setQty(Object.getString("qty"));
                                        receiptDetailsModelView.setFare(Object.getString("fare"));
                                        receiptDetailsModelView.setStd_departuretime(Object.getString("std_departuretime"));
                                        receiptDetailsModelView.setStd_deadlinetime(Object.getString("std_deadlinetime"));
                                        receiptDetailsModelView.setAgencytel(Object.getString("agencytel"));

                                        receiptDetailsModelViewList.add(receiptDetailsModelView);
                                    }
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    };

                    ReceiptDetailsRequest receiptListRequest = new ReceiptDetailsRequest(receptionQuantityModelView, responseListener);
                    RequestQueue queue = Volley.newRequestQueue( ReceiptDetailsActivity.this);
                    queue.add(receiptListRequest);

                }catch (Exception e){
                    e.printStackTrace();
                }finally {

                }
            }
        });
    }

    /**
     * 상세 정보 표기
     */
    public void loadRceptDetailsModelView(){

        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                try {
                    //settingRceptionQuantityModelView();

                    if ( receiptDetailsModelViewList != null && receiptDetailsModelViewList.size() > 0 ){
                        //receptionQuantityAdapter.notifyDataSetChanged();
                        recyclerView = findViewById(R.id.receipt_details_recyceler_view);
                        recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), 1)); // 아이템별 구분선 넣기
                        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

                        receptDetailsAdapter = new ReceptDetailsAdapter(receiptDetailsModelViewList);
                        recyclerView.setAdapter(receptDetailsAdapter);

                    }else{
                        Log.d("=== not found", "");
                    }

                }catch (Exception e){
                    e.printStackTrace();
                }finally {
                    
                }
            }
        });
    }

}