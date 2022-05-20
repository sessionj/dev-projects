package kr.co.ds.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import kr.co.ds.R;
import kr.co.ds.adapter.TrackingViewAdapter;
import kr.co.ds.comm.Label;
import kr.co.ds.models.TrackingModelView;

public class TrackingViewActivity extends AppCompatActivity {

    private static final String TAG = "TrackingViewActivity";
    private long backPressedTime = 0;
    private final long FINISH_INTERVAL_TIME = 2000;
    private TrackingModelView model;
    private List<TrackingModelView> itemLists;
    private TrackingViewAdapter adapter;
    private TrackingModelView itemView;
    TextView tracking_view_billno, tracking_view_goods, tracking_view_sendingman, tracking_view_arrivalman;
    ProgressBar progressBar;
    RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracking_view);
        progressBar = (ProgressBar) findViewById(R.id.tracking_view_progressBar);
        setProgressBar(1);
        checkTitle();
        Intent intent = getIntent();
        model = (TrackingModelView) intent.getSerializableExtra("viewMode");
        viewSetting();


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

    private void viewSetting() {

        tracking_view_billno = (TextView) findViewById(R.id.tracking_view_billno);
        tracking_view_goods = (TextView) findViewById(R.id.tracking_view_goods);
        tracking_view_sendingman = (TextView) findViewById(R.id.tracking_view_sendingman);
        tracking_view_arrivalman = (TextView) findViewById(R.id.tracking_view_arrivalman);
        recyclerView = findViewById(R.id.tracking_view_recyclerView);

        // 파라미터 셋팅
        tracking_view_billno.setText(model.getBillno());
        tracking_view_goods.setText(model.getGoods()+", "+model.getPojang());
        tracking_view_sendingman.setText(model.getSendingman()+"("+model.getSendingmantel()+")");
        tracking_view_arrivalman.setText(model.getArrivalman()+"("+model.getArrivalmantel()+")");

        // 아답터에 들어갈 리스트 만들기
        itemLists = new ArrayList<TrackingModelView>();

        int createRowCount = 0;
        createRowCount = model.getCnt();    // 생성될 라인수



        int state = 0;
        String relayStart1 = "";
        String relayStart2 = "";
        String relayStart3 = "";
        String relayEnd4 = "";

        state = model.getBillstate() == null ? 0 : Integer.parseInt(model.getBillstate().substring(0,1));
        relayStart1 = model.getRelaystart2();
        relayStart2 = model.getRelaystart3();
        relayStart3 = model.getRelaystart4();

        if (createRowCount == 2){
            relayEnd4 = model.getRelayend1();
        }else if ( createRowCount == 3){
            relayEnd4 = model.getRelayend2();
        }else if ( createRowCount == 4){
            relayEnd4 = model.getRelayend3();
        }

        itemView = new TrackingModelView();
        itemView.setItem_gubun("발송취급점");
        itemView.setItem_agencyname(model.getSendname());
        itemView.setItem_tel(model.getSendtel());
        itemView.setItem_inputday(model.getJubsuday());
        itemView.setItem_outputday(model.getSendingday());
        itemView.setItem_location("집하완료");
        itemLists.add(itemView);

        // 경유 1
        if ( createRowCount >=3 && model.getLand1name() != ""){

            itemView = new TrackingModelView();
            itemView.setItem_gubun("경유취급점1");
            itemView.setItem_agencyname(model.getLand1name());
            itemView.setItem_tel(model.getLand1tel());

            if (!TextUtils.isEmpty(model.getSt2())){
                if (model.getSt2().equals("YY") || model.getSt2().equals("NY")){
                    itemView.setItem_inputday(model.getRelayend1() );
                    itemView.setItem_outputday(relayStart1);
                }
            }
            if ( !TextUtils.isEmpty(model.getSt2()) && model.getSt2().equals("NY") ){
                itemView.setItem_location(model.getLand1name()+"도착");
            }
            if ( !TextUtils.isEmpty(model.getSt2()) && model.getSt2().equals("YY") ){
                itemView.setItem_location(model.getLand1name()+"출발");
            }
            itemLists.add(itemView);
        }

        if ( createRowCount >=4 && model.getLand2name() != ""){

            itemView = new TrackingModelView();
            itemView.setItem_gubun("경유취급점2");
            itemView.setItem_agencyname(model.getLand2name());
            itemView.setItem_tel(model.getLand2tel());

            if (!TextUtils.isEmpty(model.getSt3())){
                if (model.getSt2().equals("YY") || model.getSt3().equals("NY")){
                    itemView.setItem_inputday(model.getRelayend2() );
                    itemView.setItem_outputday(relayStart2);
                }
            }
            if ( !TextUtils.isEmpty(model.getSt3()) && model.getSt3().equals("NY") ){
                itemView.setItem_location(model.getLand2name()+"도착");
            }
            if ( !TextUtils.isEmpty(model.getSt3()) && model.getSt3().equals("YY") ){
                itemView.setItem_location(model.getLand2name()+"출발");
            }
            itemLists.add(itemView);
        }

        if ( createRowCount >=5 && model.getLand3name() != ""){

            itemView = new TrackingModelView();
            itemView.setItem_gubun("경유취급점3");
            itemView.setItem_agencyname(model.getLand3name());
            itemView.setItem_tel(model.getLand3tel());

            if (!TextUtils.isEmpty(model.getSt3())){
                if (model.getSt2().equals("YY") || model.getSt4().equals("NY")){
                    itemView.setItem_inputday(model.getRelayend3() );
                    itemView.setItem_outputday(relayStart3);
                }
            }
            if ( !TextUtils.isEmpty(model.getSt4()) && model.getSt4().equals("NY") ){
                itemView.setItem_location(model.getLand3name()+"도착");
            }
            if ( !TextUtils.isEmpty(model.getSt4()) && model.getSt4().equals("YY") ){
                itemView.setItem_location(model.getLand3name()+"출발");
            }
            itemLists.add(itemView);
        }

        itemView = new TrackingModelView();
        itemView.setItem_gubun("도착취급점");
        itemView.setItem_agencyname(model.getArrivalname());
        itemView.setItem_tel(model.getArrivaltel());

        if (model.getBillstate().equals("42") || model.getBillstate().equals("42")){
            itemView.setItem_inputday(relayEnd4);
            itemView.setItem_outputday(model.getBillstate() == "44" ? model.getChulgoday() : "");
        }

        itemView.setItem_location(state == 4 ? "아놔" : "");

        itemLists.add(itemView);

        if ( itemLists != null && itemLists.size() > 0 ){
            recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            adapter = new TrackingViewAdapter(itemLists);
            recyclerView.setAdapter(adapter);
        }
        // 도착 취급점 남음


        setProgressBar(2);
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

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home ){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        finish();
    }

}