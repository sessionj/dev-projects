package kr.co.mdaesin.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

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

import kr.co.mdaesin.R;
import kr.co.mdaesin.action.request.ReceiptHistoryRequest;
import kr.co.mdaesin.adapter.ReceptHistoryAdapter;
import kr.co.mdaesin.comm.Label;
import kr.co.mdaesin.models.ReceiptHistoryModelView;
import kr.co.mdaesin.models.ReceptionQuantityModelView;

public class HistoryActivity_ extends AppCompatActivity {

    private String TAG = " :: ReceiptDetailsActivity.LOG";
    private ReceiptHistoryModelView receiptHistoryModelView;
    private List<ReceiptHistoryModelView> receiptHistoryModelViewList;
    private ReceptionQuantityModelView receptionQuantityModelView;
    ProgressDialog asyncDialogUnsong;
    TextView history_top_title, history_top_title2, emplist;
    private RecyclerView recyclerView;
    private ReceptHistoryAdapter receptHistoryAdapter;
    private Response.Listener<String> responseListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.title_history);

        Intent intent = getIntent();
        receptionQuantityModelView = (ReceptionQuantityModelView) intent.getSerializableExtra("receptionQuantityModelView");

        history_top_title = (TextView) findViewById(R.id.history_top_title);
        history_top_title.setText(receptionQuantityModelView.getLinename() + " (" + receptionQuantityModelView.getLinecode() + ")");

        history_top_title2 = (TextView) findViewById(R.id.history_top_title2);
        history_top_title2.setText(receptionQuantityModelView.getSearchKeyword_date());

        asyncDialogUnsong = new ProgressDialog(HistoryActivity_.this);
        asyncDialogUnsong.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        asyncDialogUnsong.setMessage("야 쫌만 기다려봐 ~ ");
        // show dialog
        asyncDialogUnsong.show();
        asyncDialogUnsong.setCanceledOnTouchOutside(false);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

        receiptHistoryModelView = new ReceiptHistoryModelView();
        receiptHistoryModelViewList = new ArrayList<ReceiptHistoryModelView>();

        receiptHistoryModelView.setLinecode(receptionQuantityModelView.getLinecode());
        receiptHistoryModelView.setSearchKeyword_date(receptionQuantityModelView.getSearchKeyword_date());

        emplist = (TextView) findViewById(R.id.history_emp_list);

        Handler mHandler = new Handler(Looper.getMainLooper());
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Thread datathread = new Thread() {
                    @Override
                    public void run() {
                        getHistoryList();
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

                    if ( !response.isEmpty() && response != null){
                        JSONObject jsonObject = new JSONObject(response);
                        JSONArray resultarray = jsonObject.getJSONArray("rows");

                        if (resultarray.length() > 0) {
                            emplist.setVisibility(View.GONE);
                            for (int i = 0; i < resultarray.length(); i++) {
                                JSONObject Object = resultarray.getJSONObject(i);

                                receiptHistoryModelView = new ReceiptHistoryModelView();

                                receiptHistoryModelView.setBillNo(Object.getString("billno"));
                                receiptHistoryModelView.setAgencyname(Object.getString("agencyname"));
                                receiptHistoryModelView.setCategory(Object.getString("hangmok"));
                                receiptHistoryModelView.setUpdatetor(Object.getString("updatetor"));
                                receiptHistoryModelView.setUpdatedate(Object.getString("updatedate"));
                                receiptHistoryModelView.setUpdatetime(Object.getString("updatetime"));
                                receiptHistoryModelView.setBefcontent(Object.getString("befcontent"));
                                receiptHistoryModelView.setAftcontent(Object.getString("aftcontent"));
                                receiptHistoryModelView.setInput_date(Object.getString("input_date"));

                                Log.d(TAG, "onResponse: " + i + "번째 자료 수신중");
                                receiptHistoryModelViewList.add(receiptHistoryModelView);

                            }

                            if (receiptHistoryModelViewList != null && receiptHistoryModelViewList.size() > 0) {
                                //receptionQuantityAdapter.notifyDataSetChanged();
                                recyclerView = findViewById(R.id.receipt_details_recyceler_history_view);
                                recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), 1)); // 아이템별 구분선 넣기
                                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

                                receptHistoryAdapter = new ReceptHistoryAdapter(receiptHistoryModelViewList);
                                recyclerView.setAdapter(receptHistoryAdapter);

                                Log.d(TAG, "onResponse: 수신 완료되어 화면에 뿌림 ");

                            }
                        }
                    }else{
                        emplist.setVisibility(View.VISIBLE);
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

    public void getHistoryList(){

        receiptHistoryModelView.setSearchMode(Label.DELIVERY_BASE_URL_RECEIPT_HISTORY);
        ReceiptHistoryRequest receiptListRequest = new ReceiptHistoryRequest(receiptHistoryModelView, responseListener);
        RequestQueue queue = Volley.newRequestQueue( HistoryActivity_.this);
        queue.add(receiptListRequest);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == android.R.id.home ){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}