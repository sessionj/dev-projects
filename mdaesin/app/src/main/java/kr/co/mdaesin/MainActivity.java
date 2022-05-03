package kr.co.mdaesin;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.ProgressBar;
import android.widget.SearchView;
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

import kr.co.mdaesin.action.request.ReceiptListRequest;
import kr.co.mdaesin.adapter.ReceptionQuantityAdapter;
import kr.co.mdaesin.comm.BasicUtils;
import kr.co.mdaesin.comm.Label;
import kr.co.mdaesin.models.ReceptionQuantityModelView;
import kr.co.mdaesin.ui.CarControlActivity;
import kr.co.mdaesin.ui.HistoryActivity;
import kr.co.mdaesin.ui.ReceiptDetailsActivity;
import kr.co.mdaesin.ui.WayPointActivity;

public class MainActivity extends AppCompatActivity {

    private ReceptionQuantityModelView receptionQuantityModelView;
    private List<ReceptionQuantityModelView> receptionQuantityModelViewList;
    private ReceptionQuantityAdapter receptionQuantityAdapter;
    private RecyclerView recyclerView;

    private Calendar c;
    private int mYear, mMonth, mDay;
    private SwipeRefreshLayout mysrl;
    SearchView searchView;
    TextView textview_v1;

    private String searchKeyward = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 1.셋팅
        textview_v1 = (TextView) findViewById(R.id.textview_v1);
        textview_v1.setText(BasicUtils.getToDayDays().toString()) ;
        textview_v1.setOnClickListener(onClickListener);

        c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        CheckTypesTask task = new CheckTypesTask();
        task.execute();
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        mysrl = findViewById(R.id.content_srl);

        mysrl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {

                CheckTypesTask task3 = new CheckTypesTask();
                task3.execute();
            }
        });

        /**
         * 날짜 검색
         */
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                // 날짜가 변경되면 목록 교체

                String tmpDate = year + "-" + BasicUtils.getFormatDate( (month+1) ) + "-" + BasicUtils.getFormatDate( dayOfMonth );
                String tmpDateDay = BasicUtils.getDayOfweek(tmpDate, Label.DELIVERY_STANDARD_DATE_FORMAT);
                textview_v1.setText( tmpDate + " ("+tmpDateDay +")");

                CheckTypesTask task2 = new CheckTypesTask();
                task2.execute();
            }
        }, mYear, mMonth, mDay);

        textview_v1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (textview_v1.isClickable()) {
                    datePickerDialog.show();
                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);

        MenuItem menuItem = menu.findItem(R.id.menu_main_search);
        searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint(getResources().getString(R.string.query_hint));
        searchView.setOnQueryTextListener(queryTextListener);

        return super.onCreateOptionsMenu(menu);
    }

    private SearchView.OnQueryTextListener queryTextListener = new SearchView.OnQueryTextListener() {
        @Override
        public boolean onQueryTextSubmit(String query) {
            // 텍스트 입력 후 검색 버튼이 눌렸을 때의 이벤트
            searchKeyward = query;
            CheckTypesTask task4 = new CheckTypesTask();
            task4.execute();
            Toast.makeText(MainActivity.this, "query : " + query, Toast.LENGTH_SHORT).show();
            return false;
        }

        @Override
        public boolean onQueryTextChange(String newText) {
            // 검색 글 한자 한자 눌렸을 때의 이벤트
            Toast.makeText(MainActivity.this, "newText : " + newText, Toast.LENGTH_SHORT).show();
            return false;
        }
    };

    // 화면에 표기된  클릭 이벤트 처리
    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.textview_v1:
                    Toast.makeText(MainActivity.this, Label.RECEIPT_CONSTRUCTOR_FINAL_NAME, Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    public void loadReceptionQuantityModelView(){

        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                try {
                    //settingRceptionQuantityModelView();

                    if ( receptionQuantityModelViewList != null && receptionQuantityModelViewList.size() > 0 ){
                        //receptionQuantityAdapter.notifyDataSetChanged();
                        recyclerView = findViewById(R.id.receipt_recyceler_view);
                        recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), 1)); // 아이템별 구분선 넣기
                        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

                        receptionQuantityAdapter = new ReceptionQuantityAdapter(receptionQuantityModelViewList);
                        recyclerView.setAdapter(receptionQuantityAdapter);

                        // 접수내역 상세 정보
                        receptionQuantityAdapter.setReceiptDetailsClickListener(new ReceptionQuantityAdapter.OnReceiptDetailsListener() {
                            @Override
                            public void onReceiptDetails(View v, int pos) {
                                Toast.makeText(getApplicationContext(), "접수 상세("+receptionQuantityModelViewList.get(pos).getLinecode()+")", Toast.LENGTH_SHORT ).show();
                                Intent intent = new Intent(getApplicationContext(), ReceiptDetailsActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                            }
                        });

                        // 경유지별 내역
                        receptionQuantityAdapter.setWayPointClickListener(new ReceptionQuantityAdapter.OnWayPointListenerlickListener() {
                            @Override
                            public void onWayPoint(View v, int pos) {
                                Toast.makeText(getApplicationContext(), "경유지별 내역("+receptionQuantityModelViewList.get(pos).getLinecode()+")", Toast.LENGTH_SHORT ).show();
                                Intent intent = new Intent(getApplicationContext(), WayPointActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                            }
                        });

                        // 수정 내역
                        receptionQuantityAdapter.setHistoryClickListener(new ReceptionQuantityAdapter.OnhistoryClickListener() {
                            @Override
                            public void onhistory(View v, int pos) {
                                Toast.makeText(getApplicationContext(), "수정 내역("+receptionQuantityModelViewList.get(pos).getLinecode()+")", Toast.LENGTH_SHORT ).show();
                                Intent intent = new Intent(getApplicationContext(), HistoryActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                            }
                        });

                        // 차량관제
                        receptionQuantityAdapter.setCarControlClickListener(new ReceptionQuantityAdapter.OncarControlClickListener() {
                            @Override
                            public void onCarControl(View v, int pos) {
                                Toast.makeText(getApplicationContext(), "차량관제 내역("+receptionQuantityModelViewList.get(pos).getLinecode()+")", Toast.LENGTH_SHORT ).show();
                                Intent intent = new Intent(getApplicationContext(), CarControlActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                            }
                        });

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


    public void settingRceptionQuantityModelView(){

        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                try {
                    receptionQuantityModelView = new ReceptionQuantityModelView();
                    receptionQuantityModelView.setSearchKeyword_date(textview_v1.getText().toString().split(" ")[0]);

                    if ( !TextUtils.isEmpty(searchKeyward)){
                        receptionQuantityModelView.setLinecode(searchKeyward);
                    }else{
                        receptionQuantityModelView.setLinecode(Label.RECEIPT_DEFAULT_LINECODE);
                    }
                    receptionQuantityModelViewList = new ArrayList<ReceptionQuantityModelView>();
                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                JSONArray resultarray = jsonObject.getJSONArray("rows");

                                if ( resultarray.length() > 0){
                                    for ( int i=0; i < resultarray.length(); i++){
                                        JSONObject Object = resultarray.getJSONObject(i);
                                        receptionQuantityModelView = new ReceptionQuantityModelView();
                                        receptionQuantityModelView.setLinecode(Object.getString("linecode"));
                                        receptionQuantityModelView.setLinename(Object.getString("linename"));
                                        receptionQuantityModelView.setCarcode(Object.getString("carcode"));
                                        receptionQuantityModelView.setCarname(Object.getString("carname"));
                                        receptionQuantityModelView.setCnt(Object.getString("cnt"));
                                        receptionQuantityModelView.setQty(Object.getString("qty"));
                                        receptionQuantityModelView.setChong(Object.getString("chong"));
                                        receptionQuantityModelView.setGugan(Object.getString("gugan"));
                                        receptionQuantityModelView.setSenddate(Object.getString("senddate"));
                                        receptionQuantityModelView.setRgunsu(Object.getString("rgunsu"));
                                        receptionQuantityModelView.setRqty(Object.getString("rqty"));
                                        receptionQuantityModelView.setRfare(Object.getString("rfare"));
                                        receptionQuantityModelView.setRrate(Object.getString("rrate"));
                                        receptionQuantityModelView.setWeight(Object.getString("weight"));

                                        receptionQuantityModelViewList.add(receptionQuantityModelView);
                                    }
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    };

                    ReceiptListRequest receiptListRequest = new ReceiptListRequest(receptionQuantityModelView, responseListener);
                    RequestQueue queue = Volley.newRequestQueue( MainActivity.this);
                    queue.add(receiptListRequest);

                }catch (Exception e){
                    e.printStackTrace();
                }finally {

                }
            }
        });
    }

    private class CheckTypesTask extends AsyncTask<Void, Void, Void> {

        ProgressDialog asyncDialog = new ProgressDialog(MainActivity.this);

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
                //loadReceptionQuantityModelView();
                settingRceptionQuantityModelView();
                for (int i = 0; i < 10; i++) {
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
            //
            loadReceptionQuantityModelView();
            asyncDialog.dismiss();
            mysrl.setRefreshing(false);

            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            super.onPostExecute(result);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}