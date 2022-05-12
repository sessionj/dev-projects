package kr.co.mdaesin.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;

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
import kr.co.mdaesin.action.request.ReceiptWaypointRequest;
import kr.co.mdaesin.adapter.ReceptDetailsAdapter;
import kr.co.mdaesin.adapter.ReceptWaypointAdapter;
import kr.co.mdaesin.comm.Label;
import kr.co.mdaesin.models.ReceiptWayPointModelView;
import kr.co.mdaesin.models.ReceptionQuantityModelView;
import kr.co.mdaesin.ui.popup.WaypointPopupActivity;

public class WayPointActivity extends AppCompatActivity {

    private String TAG = "WayPointActivity";
    private ReceptionQuantityModelView receptionQuantityModelView;
    private ReceiptWayPointModelView receiptWayPointModelView;
    private List<ReceiptWayPointModelView> receiptWayPointModelViewList;
    private Response.Listener<String> responseListener;

    private ReceiptWayPointModelView resultWaypointView;
    private ReceptWaypointAdapter receptWaypointAdapter;
    private ProgressBar progressBar;

    TextView waypoint_top_title, waypoint_top_title2, emplist, waypoint_sum_list_3, waypoint_sum_list_4;
    ProgressDialog asyncDialog;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_way_point);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.title_waypoint);

        Intent intent = getIntent();
        receptionQuantityModelView = (ReceptionQuantityModelView) intent.getSerializableExtra("receptionQuantityModelView");

        waypoint_top_title = (TextView) findViewById(R.id.waypoint_top_title);
        waypoint_top_title.setText(receptionQuantityModelView.getLinename() + " (" + receptionQuantityModelView.getLinecode() + ")");

        waypoint_top_title2 = (TextView) findViewById(R.id.waypoint_top_title2);
        waypoint_top_title2.setText(receptionQuantityModelView.getSearchKeyword_date());

        receiptWayPointModelView = new ReceiptWayPointModelView();
        receiptWayPointModelViewList = new ArrayList<ReceiptWayPointModelView>();

        receiptWayPointModelView.setLinecode(receptionQuantityModelView.getLinecode());
        receiptWayPointModelView.setSearchKeyword_date(receptionQuantityModelView.getSearchKeyword_date());
        receiptWayPointModelView.setLinename(receptionQuantityModelView.getLinename());

        receiptWayPointModelView.setSearchMode(Label.DELIVERY_BASE_URL_RECEIPT_WAYPOINT);
        emplist = (TextView) findViewById(R.id.waypoint_emp_list);

        waypoint_sum_list_3 = (TextView) findViewById(R.id.waypoint_sum_3);
        waypoint_sum_list_4 = (TextView) findViewById(R.id.waypoint_sum_4);
        progressBar = findViewById(R.id.receipt_waypoint_progressBar);

        getHistoryList();
    }

    private void getHistoryList(){
        progressBar.setVisibility(View.VISIBLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        ReceiptWaypointRequest receiptWaypointRequest = new ReceiptWaypointRequest(receiptWayPointModelView, successListener(), errorListener());
        RequestQueue queue = Volley.newRequestQueue( WayPointActivity.this);
        queue.add(receiptWaypointRequest);

    }

    private Response.Listener<String> successListener() {
        return new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    if ( !response.isEmpty() && response != null){
                        JSONObject jsonObject = new JSONObject(response);
                        JSONArray resultarray = jsonObject.getJSONArray("rows");
                        receiptWayPointModelViewList = new ArrayList<ReceiptWayPointModelView>();
                        if (resultarray.length() > 0) {

                            for (int i = 0; i < resultarray.length(); i++) {
                                JSONObject Object = resultarray.getJSONObject(i);

                                resultWaypointView = new ReceiptWayPointModelView();
                                resultWaypointView.setStagencycode(Object.getString("stagencycode"));
                                resultWaypointView.setEdagencycode(Object.getString("edagencycode"));
                                resultWaypointView.setStagencyname(Object.getString("stagencyname"));
                                resultWaypointView.setEdagencyname(Object.getString("edagencyname"));
                                resultWaypointView.setSendfare(Object.getString("sendfare"));
                                resultWaypointView.setArrivefare(Object.getString("arrivefare"));
                                resultWaypointView.setGubun(Object.getString("gubun"));

                                receiptWayPointModelViewList.add(resultWaypointView);

                            }

                            if (receiptWayPointModelViewList != null && receiptWayPointModelViewList.size() > 0) {
                                emplist.setVisibility(View.GONE);
                                //receptionQuantityAdapter.notifyDataSetChanged();
                                recyclerView = findViewById(R.id.receipt_waypoint_recyceler_view);
                                recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), 1)); // 아이템별 구분선 넣기
                                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

                                receptWaypointAdapter = new ReceptWaypointAdapter(receiptWayPointModelViewList);
                                recyclerView.setAdapter(receptWaypointAdapter);

                                receptWaypointAdapter.setOnitemClickListener(new ReceptDetailsAdapter.OnitemClickListener() {
                                    @Override
                                    public void onItemClick(View v, int pos) {

                                        ReceiptWayPointModelView wayPoint = new ReceiptWayPointModelView();
                                        wayPoint = receiptWayPointModelViewList.get(pos);
                                        Log.d(TAG, "onItemClick: ================== agencycode : " + wayPoint.getStagencycode());
                                        wayPoint.setLinecode(receptionQuantityModelView.getLinecode());
                                        wayPoint.setSearchKeyword_date(receptionQuantityModelView.getSearchKeyword_date());
                                        wayPoint.setLinename(receptionQuantityModelView.getLinename());
                                        if (!TextUtils.isEmpty(wayPoint.getStagencycode())){
                                            wayPoint.setWaypoint("1");
                                        }else{
                                            wayPoint.setWaypoint("2");
                                        }

                                        Intent intent = new Intent(getApplicationContext(), WaypointPopupActivity.class);
                                        intent.putExtra("wayPoint", wayPoint);
                                        //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(intent);
                                    }
                                });
                            }
                        }
                    }else{
                        emplist.setVisibility(View.VISIBLE);
                        waypoint_top_title.setVisibility(View.GONE);
                        waypoint_top_title2.setVisibility(View.GONE);
                        waypoint_sum_list_3.setVisibility(View.GONE);
                        waypoint_sum_list_4.setVisibility(View.GONE);

                    }
                } catch(JSONException e) {
                    e.printStackTrace();
                } finally {

                    if ( receiptWayPointModelViewList != null && receiptWayPointModelViewList.size() > 0){
                        waypoint_sum_list_3.setText(receptWaypointAdapter.standardSum(1).toString());
                        waypoint_sum_list_4.setText(receptWaypointAdapter.standardSum(2).toString());
                    }

                    progressBar.setVisibility(View.GONE);
                    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
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
