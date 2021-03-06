package kr.co.mdaesin.ui.popup;

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
import kr.co.mdaesin.action.request.ReceiptWaypointRequest;
import kr.co.mdaesin.adapter.ReceptDetailsAdapter;
import kr.co.mdaesin.adapter.ReceptWaypointAdapter;
import kr.co.mdaesin.adapter.ReceptWaypointDetailsAdapter;
import kr.co.mdaesin.comm.Label;
import kr.co.mdaesin.models.ReceiptWayPointModelView;
import kr.co.mdaesin.ui.WayPointActivity;

public class WaypointPopupActivity extends AppCompatActivity {

    private String TAG = "WaypointPopupActivity LOG : ";
    TextView waypoint_top_title, waypoint_top_title2, popup_content3, popup_content4, popup_content5, emplist;

    private ReceiptWayPointModelView receiptWayPointModelView;
    private List<ReceiptWayPointModelView> receiptWayPointModelViewList;
    private Response.Listener<String> responseListener;
    private ReceiptWayPointModelView resultWaypointView;
    private ReceptWaypointDetailsAdapter receptWaypointDetailsAdapter;
    private ProgressBar progressBar;

    ProgressDialog asyncDialog;
    RecyclerView recyclerView;
    TextView waypoint_dt_summery_1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waypoint_popup);

        Intent intent = getIntent();
        receiptWayPointModelView = (ReceiptWayPointModelView) intent.getSerializableExtra("wayPoint");
        receiptWayPointModelView.setSearchMode(Label.DELIVERY_BASE_URL_RECEIPT_WAYPOINT_DETAILS);
        waypoint_top_title = (TextView) findViewById(R.id.waypoint_det_top_title);
        waypoint_top_title.setText(receiptWayPointModelView.getLinename() + " (" + receiptWayPointModelView.getLinecode() + ")");

        waypoint_top_title2 = (TextView) findViewById(R.id.waypoint_det_top_title2);
        waypoint_top_title2.setText(receiptWayPointModelView.getSearchKeyword_date());

        receiptWayPointModelViewList = new ArrayList<ReceiptWayPointModelView>();

        emplist = (TextView) findViewById(R.id.waypoint_emp_list);
        waypoint_dt_summery_1 = (TextView) findViewById(R.id.waypoint_dt_summery_1) ;

        recyclerView = findViewById(R.id.receipt_waypoint_det_recyceler_view);
        recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), 1)); // ???????????? ????????? ??????
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        progressBar = findViewById(R.id.receipt_waypoint_det_progressBar);

        getHistoryList();

    }

    private void getHistoryList(){
        progressBar.setVisibility(View.VISIBLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        ReceiptWaypointRequest receiptWaypointRequest = new ReceiptWaypointRequest(receiptWayPointModelView, successListener(), errorListener());
        RequestQueue queue = Volley.newRequestQueue( WaypointPopupActivity.this);
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
                                resultWaypointView.setDet_agencycode(Object.getString("agencycode"));
                                resultWaypointView.setDet_agencyname(Object.getString("agencyname"));
                                resultWaypointView.setDet_sendagencyname(Object.getString("sendagencyname"));
                                resultWaypointView.setDet_goods(Object.getString("goods"));
                                resultWaypointView.setDet_pojang(Object.getString("pojang"));
                                resultWaypointView.setDet_qty(Object.getString("qty"));
                                resultWaypointView.setDet_fare(Object.getString("fare"));
                                resultWaypointView.setWaypoint(receiptWayPointModelView.getWaypoint());

                                receiptWayPointModelViewList.add(resultWaypointView);

                            }

                            if (receiptWayPointModelViewList != null && receiptWayPointModelViewList.size() > 0) {
                                emplist.setVisibility(View.GONE);
                                //receptionQuantityAdapter.notifyDataSetChanged();


                                receptWaypointDetailsAdapter = new ReceptWaypointDetailsAdapter(receiptWayPointModelViewList);
                                recyclerView.setAdapter(receptWaypointDetailsAdapter);

                                receptWaypointDetailsAdapter.setOnitemClickListener(new ReceptDetailsAdapter.OnitemClickListener() {
                                    @Override
                                    public void onItemClick(View v, int pos) {


                                    }
                                });
                                waypoint_dt_summery_1.setText(receptWaypointDetailsAdapter.standardSum().toString());
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
                }
            }
        };
    }

    // ?????? ????????????
    public void sgetHistoryList(){

        asyncDialog = new ProgressDialog(WaypointPopupActivity.this);

        asyncDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        asyncDialog.setMessage("?????? ?????????... ");
        asyncDialog.show();
        asyncDialog.setCanceledOnTouchOutside(false);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                try {
                    responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            emplist.setVisibility(View.GONE);
                            try {

                                if ( !response.isEmpty() && response != null){
                                    JSONObject jsonObject = new JSONObject(response);
                                    JSONArray resultarray = jsonObject.getJSONArray("rows");
                                    receiptWayPointModelViewList = new ArrayList<ReceiptWayPointModelView>();
                                    if (resultarray.length() > 0) {

                                        for (int i = 0; i < resultarray.length(); i++) {
                                            JSONObject Object = resultarray.getJSONObject(i);
                                            resultWaypointView = new ReceiptWayPointModelView();
                                            resultWaypointView.setDet_agencycode(Object.getString("agencycode"));
                                            resultWaypointView.setDet_agencyname(Object.getString("agencyname"));
                                            resultWaypointView.setDet_sendagencyname(Object.getString("sendagencyname"));
                                            resultWaypointView.setDet_goods(Object.getString("goods"));
                                            resultWaypointView.setDet_pojang(Object.getString("pojang"));
                                            resultWaypointView.setDet_qty(Object.getString("qty"));
                                            resultWaypointView.setDet_fare(Object.getString("fare"));
                                            resultWaypointView.setWaypoint(receiptWayPointModelView.getWaypoint());

                                            receiptWayPointModelViewList.add(resultWaypointView);

                                        }

                                        if (receiptWayPointModelViewList != null && receiptWayPointModelViewList.size() > 0) {
                                            emplist.setVisibility(View.GONE);
                                            //receptionQuantityAdapter.notifyDataSetChanged();
                                            

                                            receptWaypointDetailsAdapter = new ReceptWaypointDetailsAdapter(receiptWayPointModelViewList);
                                            recyclerView.setAdapter(receptWaypointDetailsAdapter);

                                            receptWaypointDetailsAdapter.setOnitemClickListener(new ReceptDetailsAdapter.OnitemClickListener() {
                                                @Override
                                                public void onItemClick(View v, int pos) {


                                                }
                                            });
                                            waypoint_dt_summery_1.setText(receptWaypointDetailsAdapter.standardSum().toString());
                                        }
                                    }
                                }else{
                                    emplist.setVisibility(View.VISIBLE);
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            } finally {

                                asyncDialog.dismiss();
                                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                            }
                        }
                    };

                    ReceiptWaypointRequest receiptWaypointRequest = new ReceiptWaypointRequest(receiptWayPointModelView, responseListener, errorListener());
                    RequestQueue queue = Volley.newRequestQueue( WaypointPopupActivity.this);
                    queue.add(receiptWaypointRequest);

                }catch (Exception e){
                    e.printStackTrace();
                }finally {

                }
            }
        });
    }

    private Response.ErrorListener errorListener() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //progressBar.setVisibility(View.GONE);
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