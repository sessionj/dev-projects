package kr.co.mdaesin;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import kr.co.mdaesin.action.request.ReceiptListRequest;
import kr.co.mdaesin.adapter.ReceiptLoadAdapter;
import kr.co.mdaesin.adapter.ReceptListAdapter;
import kr.co.mdaesin.comm.BasicUtils;
import kr.co.mdaesin.comm.Label;
import kr.co.mdaesin.models.ReceptionQuantityModelView;
import kr.co.mdaesin.ui.CarControlActivity;
import kr.co.mdaesin.ui.HistoryActivity;
import kr.co.mdaesin.ui.ReceiptDetailsActivity;
import kr.co.mdaesin.ui.TendinousActivity;
import kr.co.mdaesin.ui.WayPointActivity;

public class MainActivity extends AppCompatActivity {

    private String TAG = "MainActivity ";
    private ReceptionQuantityModelView receptionQuantityModelView;
    private ReceptionQuantityModelView setReceptionQuantityModelView;
    private List<ReceptionQuantityModelView> receptionQuantityModelViewList;
    private ReceiptLoadAdapter receptListAdapter;
    private RecyclerView recyclerView;
    private Calendar c;
    private int mYear, mMonth, mDay;
    private SwipeRefreshLayout mysrl;
    private long backPressedTime = 0;
    private final long FINISH_INTERVAL_TIME = 2000;
    private String searchKeyward = "";
    private boolean isLoading = false;
    private boolean isPageLoad = true;
    private final int STARTPAGE = 1;
    private final int ENDPAGE = 10;
    private final int CONSTANTNUMBER = 10;
    private int setCurrent = 1;
    private boolean pageTextSearch = false;
    Toolbar mainToolbar;

    MenuItem mSearchItem;
    SearchView searchView;
    TextView textview_v1, row_in_1, row_in_2, row_in_3, row_in_4;
    ProgressDialog asyncDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar ac = getSupportActionBar();

        String word = Label.RECEIPT_CONSTRUCTOR_FINAL_MAINNAME;
        String content = ac.getTitle().toString();
        int start = content.indexOf(word);
        int end = start + word.length();

        SpannableString spannableString = new SpannableString(content);
        spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#FF5E00")), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new StyleSpan(Typeface.NORMAL), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new RelativeSizeSpan(1.1f), start, end, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE);

        ac.setTitle(spannableString);

        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_dehaze_black_24dp);

        // 1.셋팅
        textview_v1 = (TextView) findViewById(R.id.textview_v1);
        textview_v1.setText(BasicUtils.getToDayDays().toString()) ;
        textview_v1.setOnClickListener(onClickListener);

        row_in_1 = (TextView) findViewById(R.id.row_in_1);
        row_in_2 = (TextView) findViewById(R.id.row_in_2);
        row_in_3 = (TextView) findViewById(R.id.row_in_3);
        row_in_4 = (TextView) findViewById(R.id.row_in_4);

        c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        setReceptionQuantityModelView = new ReceptionQuantityModelView();
        recyclerView = findViewById(R.id.receipt_recyceler_view);
        populateData();
        //initAdapter();
        initScrollListener();


        mysrl = findViewById(R.id.content_srl);
        mysrl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //CheckTypesTask task3 = new CheckTypesTask();
                //task3.execute();
                pageHandler(true, false, 1);
                settingRceptionQuantityModelView();
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
                pageHandler(true, false, 1);
                settingRceptionQuantityModelView();
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
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        Log.d(TAG, "onOptionsItemSelected: " + item.getItemId());

        switch (item.getItemId()){
            case R.id.main_id001:

                Intent intent = new Intent(getApplicationContext(), TendinousActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

                break;
            case R.id.main_id002:
                Toast.makeText(getApplicationContext(), "menu2 준비중", Toast.LENGTH_SHORT).show();
                break;
            case R.id.main_id003:
                Toast.makeText(getApplicationContext(), "menu3 준비중", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void populateData() {
        pageHandler(true, false, 1);
        settingRceptionQuantityModelView();
    }

    private void initScrollListener() {
        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();

                if (!isLoading) {

                    if (layoutManager != null && layoutManager.findLastCompletelyVisibleItemPosition() == receptionQuantityModelViewList.size() - 1) {
                        //리스트 마지막
                        if ( !pageTextSearch){
                            loadMore();
                            isLoading = true;
                        }
                    }
                }
            }
        });
    }

    private void loadMore() {

        recyclerView.post(new Runnable() {
            public void run() {
                receptListAdapter.notifyDataSetChanged();

                receptionQuantityModelViewList.add(null);
                receptListAdapter.notifyItemInserted(receptionQuantityModelViewList.size() - 1);

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        receptionQuantityModelViewList.remove(receptionQuantityModelViewList.size() - 1);
                        int scrollPosition = receptionQuantityModelViewList.size();
                        receptListAdapter.notifyItemRemoved(scrollPosition);
                        int currentSize = scrollPosition;
                        int nextLimit = currentSize + 10;

                        //isPageLoad = false;
                        pageHandler(false, false, 0);
                        settingRceptionQuantityModelView();
                    }
                }, 2000);
            }
        });
    }

    protected void pageHandler(boolean _isPageLoad, boolean _pageTextSearch, int reset){

        isPageLoad = _isPageLoad;
        pageTextSearch = _pageTextSearch;

        if ( reset == 1){
            receptionQuantityModelViewList = new ArrayList<ReceptionQuantityModelView>();
        }

        // 처음이면
        if(isPageLoad){
            setReceptionQuantityModelView.setStartPage(STARTPAGE);
            setReceptionQuantityModelView.setEndPage(ENDPAGE);
            setCurrent = 1;
        }else{

            setCurrent ++;
            setReceptionQuantityModelView.setStartPage(CONSTANTNUMBER*setCurrent+1);
            setReceptionQuantityModelView.setEndPage(CONSTANTNUMBER*setCurrent+CONSTANTNUMBER);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        mSearchItem = menu.getItem(0);
        MenuItem menuItem = menu.findItem(R.id.menu_main_search);
        searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint(getResources().getString(R.string.query_hint));
        searchView.setOnQueryTextListener(queryTextListener);

        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {

                Toast.makeText(getApplicationContext(), "검색닫기", Toast.LENGTH_SHORT).show();
                // 닫기를 누르면 내용 삭제 화면 다시 로드
                searchKeyward = "";
                pageHandler(true, false, 1);
                settingRceptionQuantityModelView();
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    private SearchView.OnQueryTextListener queryTextListener = new SearchView.OnQueryTextListener() {
        @Override
        public boolean onQueryTextSubmit(String query) {
            // 텍스트 입력 후 검색 버튼이 눌렸을 때의 이벤트

            searchKeyward = query;
            pageHandler(true, true, 1);
            settingRceptionQuantityModelView();
            //mSearchItem.collapseActionView();
            return false;
        }

        @Override
        public boolean onQueryTextChange(String newText) {
            // 검색 글 한자 한자 눌렸을 때의 이벤트

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

    public void settingRceptionQuantityModelView(){

        if ( isPageLoad){
            asyncDialog = new ProgressDialog(MainActivity.this);

            asyncDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            asyncDialog.setMessage("자료 확인중... ");
            asyncDialog.show();
            asyncDialog.setCanceledOnTouchOutside(false);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        }


        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                try {

                    setReceptionQuantityModelView.setSearchKeyword_date(textview_v1.getText().toString().split(" ")[0]);

                    if ( !TextUtils.isEmpty(searchKeyward)){
                        setReceptionQuantityModelView.setLinecode(searchKeyward);
                    }else{
                        setReceptionQuantityModelView.setLinecode(Label.RECEIPT_DEFAULT_LINECODE);
                    }

                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                JSONArray resultarray = jsonObject.getJSONArray("details");

                                if ( resultarray.length() > 0){
                                    for ( int i=0; i < resultarray.length(); i++){
                                        JSONObject Object = resultarray.getJSONObject(i);

                                        ReceptionQuantityModelView result = new ReceptionQuantityModelView();

                                        result.setLinecode(Object.getString("linecode"));
                                        result.setLinename(Object.getString("linename"));
                                        result.setCarcode(Object.getString("carcode"));
                                        result.setCarname(Object.getString("carname"));
                                        result.setCnt(Object.getString("cnt"));
                                        result.setQty(Object.getString("qty"));
                                        result.setChong(Object.getString("chong"));
                                        result.setGugan(Object.getString("gugan"));
                                        result.setSenddate(Object.getString("senddate"));
                                        result.setRgunsu(Object.getString("rgunsu"));
                                        result.setRqty(Object.getString("rqty"));
                                        result.setRfare(Object.getString("rfare"));
                                        result.setRrate(Object.getString("rrate"));
                                        result.setWeight(Object.getString("weight"));

                                        receptionQuantityModelViewList.add(result);
                                    }

                                        if ( isPageLoad){
                                            if ( receptionQuantityModelViewList != null && receptionQuantityModelViewList.size() > 0 ){

                                                recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), 1)); // 아이템별 구분선 넣기
                                                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                                                receptListAdapter = new ReceiptLoadAdapter(receptionQuantityModelViewList);

                                                recyclerView.setAdapter(receptListAdapter);

                                                // 접수내역 상세 정보
                                                receptListAdapter.setReceiptDetailsClickListener(new ReceptListAdapter.OnReceiptDetailsListener() {
                                                    @Override
                                                    public void onReceiptDetails(View v, int pos) {
                                                        //Toast.makeText(getApplicationContext(), "접수 상세("+receptionQuantityModelViewList.get(pos).getLinecode()+")", Toast.LENGTH_SHORT ).show();
                                                        ReceptionQuantityModelView intentParam = new ReceptionQuantityModelView();
                                                        intentParam = receptionQuantityModelViewList.get(pos);
                                                        intentParam.setSearchKeyword_date(textview_v1.getText().toString());
                                                        Intent intent = new Intent(getApplicationContext(), ReceiptDetailsActivity.class);
                                                        intent.putExtra("receptionQuantityModelView", intentParam);
                                                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                        startActivity(intent);
                                                    }
                                                });

                                                // 경유지별 내역
                                                receptListAdapter.setWayPointClickListener(new ReceptListAdapter.OnWayPointListenerlickListener() {
                                                    @Override
                                                    public void onWayPoint(View v, int pos) {
                                                        //Toast.makeText(getApplicationContext(), "경유지별 내역("+receptionQuantityModelViewList.get(pos).getLinecode()+")", Toast.LENGTH_SHORT ).show();
                                                        ReceptionQuantityModelView intentParam = new ReceptionQuantityModelView();
                                                        intentParam = receptionQuantityModelViewList.get(pos);
                                                        intentParam.setSearchKeyword_date(textview_v1.getText().toString());
                                                        Intent intent = new Intent(getApplicationContext(), WayPointActivity.class);
                                                        intent.putExtra("receptionQuantityModelView", intentParam);
                                                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                        startActivity(intent);
                                                    }
                                                });

                                                // 수정 내역
                                                receptListAdapter.setHistoryClickListener(new ReceptListAdapter.OnhistoryClickListener() {
                                                    @Override
                                                    public void onhistory(View v, int pos) {
                                                        //Toast.makeText(getApplicationContext(), "수정 내역("+receptionQuantityModelViewList.get(pos).getLinecode()+")", Toast.LENGTH_SHORT ).show();
                                                        ReceptionQuantityModelView intentParam = new ReceptionQuantityModelView();
                                                        intentParam = receptionQuantityModelViewList.get(pos);
                                                        intentParam.setSearchKeyword_date(textview_v1.getText().toString());
                                                        Intent intent = new Intent(getApplicationContext(), HistoryActivity.class);
                                                        intent.putExtra("receptionQuantityModelView", intentParam);
                                                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                        startActivity(intent);
                                                    }
                                                });

                                                // 차량관제
                                                receptListAdapter.setCarControlClickListener(new ReceptListAdapter.OncarControlClickListener() {
                                                    @Override
                                                    public void onCarControl(View v, int pos) {
                                                        ReceptionQuantityModelView intentParam = new ReceptionQuantityModelView();
                                                        intentParam = receptionQuantityModelViewList.get(pos);
                                                        intentParam.setSearchKeyword_date(textview_v1.getText().toString());
                                                        Intent intent = new Intent(getApplicationContext(), CarControlActivity.class);
                                                        intent.putExtra("receptionQuantityModelView", intentParam);
                                                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                        startActivity(intent);
                                                    }
                                                });

                                                receptListAdapter.notifyDataSetChanged();
                                            }
                                        }else{
                                            receptListAdapter.notifyDataSetChanged();
                                            isLoading = false;
                                        }
                                    }

                                // 두번째꺼

                                if ( isPageLoad){
                                    JSONArray resultarraySummary = jsonObject.getJSONArray("summary");
                                    if ( resultarraySummary != null && resultarraySummary.length() > 0 ){
                                        for ( int i=0; i < resultarraySummary.length(); i++) {
                                            JSONObject ObjectSummary = resultarraySummary.getJSONObject(i);
                                            row_in_1.setText(BasicUtils.addStringComma(ObjectSummary.getString("summary_cnt")) +"건".toString());
                                            row_in_2.setText(BasicUtils.addStringComma(ObjectSummary.getString("summary_qty"))  +"건".toString());
                                            row_in_3.setText("￦"+BasicUtils.addStringComma(ObjectSummary.getString("summary_chong")).toString());
                                            row_in_4.setText("￦"+BasicUtils.addStringComma(ObjectSummary.getString("summary_gugan")).toString());
                                        }
                                    }
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            } finally {
                                //row_in_1.setText(receptListAdapter.standardSum(1)+"건".toString());
                                //row_in_2.setText(receptListAdapter.standardSum(2)+"개".toString());
                                //row_in_3.setText("￦" + receptListAdapter.standardSum(3).toString());
                                //row_in_4.setText("￦" + receptListAdapter.standardSum(4).toString());
                                if ( isPageLoad){
                                    asyncDialog.dismiss();
                                    hideKeyboard();
                                    mysrl.setRefreshing(false);
                                    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                                }
                            }
                        }
                    };

                    ReceiptListRequest receiptListRequest = new ReceiptListRequest(setReceptionQuantityModelView, responseListener);
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

            } catch (InterruptedException  ex) {
                ex.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            // 백그라운드 스레드가 완료되면 Layout 출력 시작
            //
            //loadReceptionQuantityModelView();
            asyncDialog.dismiss();
            hideKeyboard();
            mysrl.setRefreshing(false);

            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            super.onPostExecute(result);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
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
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        hideKeyboard();
        return super.dispatchTouchEvent(ev);
    }
    /**
     * Hiding keyboard after every button press
     */
    private void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(this);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}