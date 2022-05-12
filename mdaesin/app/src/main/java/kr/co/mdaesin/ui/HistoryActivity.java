package kr.co.mdaesin.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
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

import java.util.ArrayList;
import java.util.List;

import kr.co.mdaesin.R;
import kr.co.mdaesin.action.request.ReceiptDetailsRequest;
import kr.co.mdaesin.action.request.ReceiptHistoryRequest;
import kr.co.mdaesin.adapter.ReceptDetailsAdapter;
import kr.co.mdaesin.adapter.ReceptDetailsUnsongAdapter;
import kr.co.mdaesin.adapter.ReceptHistoryAdapter;
import kr.co.mdaesin.adapter.ReceptListAdapter;
import kr.co.mdaesin.comm.Label;
import kr.co.mdaesin.models.ReceiptDetailsModelView;
import kr.co.mdaesin.models.ReceiptHistoryModelView;
import kr.co.mdaesin.models.ReceptionQuantityModelView;
import kr.co.mdaesin.ui.popup.HistoryPopupActivity;

public class HistoryActivity extends AppCompatActivity {

    private String TAG = " :: ReceiptDetailsActivity.LOG";
    private ReceiptHistoryModelView receiptHistoryModelView;
    private List<ReceiptHistoryModelView> receiptHistoryModelViewList;
    private ReceptionQuantityModelView receptionQuantityModelView;
    ProgressDialog asyncDialogUnsong;
    TextView history_top_title, history_top_title2, emplist;
    private RecyclerView recyclerView;
    private ReceptHistoryAdapter receptHistoryAdapter;
    private Response.Listener<String> responseListener;
    ProgressDialog asyncDialog;
    private ProgressBar progressBar;
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

        receiptHistoryModelView = new ReceiptHistoryModelView();
        receiptHistoryModelViewList = new ArrayList<ReceiptHistoryModelView>();

        receiptHistoryModelView.setLinecode(receptionQuantityModelView.getLinecode());
        receiptHistoryModelView.setSearchKeyword_date(receptionQuantityModelView.getSearchKeyword_date());

        emplist = (TextView) findViewById(R.id.history_emp_list);
        progressBar = findViewById(R.id.receipt_history_progressBar);


        getHistoryList();

    }



    private void getHistoryList(){
        progressBar.setVisibility(View.VISIBLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

        receiptHistoryModelView.setSearchMode(Label.DELIVERY_BASE_URL_RECEIPT_HISTORY);
        ReceiptHistoryRequest receiptListRequest = new ReceiptHistoryRequest(receiptHistoryModelView, successListener(), errorListener());
        RequestQueue queue = Volley.newRequestQueue( HistoryActivity.this);
        queue.add(receiptListRequest);

    }

    private Response.Listener<String> successListener() {
        return new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    if ( !response.isEmpty() && response != null){
                        JSONObject jsonObject = new JSONObject(response);
                        JSONArray resultarray = jsonObject.getJSONArray("rows");

                        if (resultarray.length() > 0) {

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
                                emplist.setVisibility(View.GONE);
                                //receptionQuantityAdapter.notifyDataSetChanged();
                                recyclerView = findViewById(R.id.receipt_details_recyceler_history_view);
                                recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), 1)); // 아이템별 구분선 넣기
                                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

                                receptHistoryAdapter = new ReceptHistoryAdapter(receiptHistoryModelViewList);
                                recyclerView.setAdapter(receptHistoryAdapter);

                                receptHistoryAdapter.setOnitemClickListener(new ReceptDetailsAdapter.OnitemClickListener() {
                                    @Override
                                    public void onItemClick(View v, int pos) {

                                        ReceiptHistoryModelView histModel = new ReceiptHistoryModelView();
                                        histModel = receiptHistoryModelViewList.get(pos);

                                        Intent intent = new Intent(getApplicationContext(), HistoryPopupActivity.class);
                                        intent.putExtra("histModel", histModel);
                                        startActivity(intent);
                                    }
                                });
                            }
                        }
                    }else{
                        emplist.setVisibility(View.VISIBLE);
                    }
                } catch(JSONException e) {
                    e.printStackTrace();
                } finally {
                    progressBar.setVisibility(View.GONE);
                    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                    /*asyncDialogUnsong.dismiss();
                    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);*/
                }
            }
        };
    }

    private Response.ErrorListener errorListener() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                //asyncDialogUnsong.dismiss();
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                error.printStackTrace();
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
}