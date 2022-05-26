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
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import kr.co.ds.R;
import kr.co.ds.adapter.TrackingViewAdapter;
import kr.co.ds.comm.BasicUtils;
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
    TextView tracking_view_billno, tracking_view_goods, tracking_view_sendingman, tracking_view_arrivalman, tracking_view_billstatus;
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
        tracking_view_billstatus = (TextView) findViewById(R.id.tracking_view_billstatus);
        recyclerView = findViewById(R.id.tracking_view_recyclerView);

        // 파라미터 셋팅
        tracking_view_billno.setText(BasicUtils.stringSplit(model.getBillno(),1).toString());
        tracking_view_goods.setText(model.getGoods()+", "+model.getPojang());
        tracking_view_sendingman.setText(model.getSendingman()+"("+BasicUtils.stringSplit(model.getSendingmantel(),2)+")");
        tracking_view_arrivalman.setText(model.getArrivalman()+"("+BasicUtils.stringSplit(model.getArrivalmantel(),2)+")");

        // 아답터에 들어갈 리스트 만들기
        itemLists = new ArrayList<TrackingModelView>();

        int createRowCount = 0;
        // getRelayend1 1번째 경유지 도착
        // getRelaystart1 1번째 경유지에서 발송

        // 2022-04-02 10:09 물품이 발송되었습니다.
        itemView = new TrackingModelView();
        itemView.setItem_outputday(model.getSendingday());
        itemView.setItem_gubun("물품이 ("+model.getSendname() +") 에서 발송되었습니다");
        itemLists.add(itemView);

        if ( !TextUtils.isEmpty(model.getSt2()) && model.getCnt() >= 3 ){
            if ( model.getSt2().equals("YN")){
                itemView = new TrackingModelView();
                itemView.setItem_outputday(model.getRelayend1());
                itemView.setItem_gubun("경유지("+model.getLand1name()+") 에 도착되었습니다");
                itemLists.add(itemView);
            }
            if ( model.getSt2().equals("YY")){

                itemView = new TrackingModelView();
                itemView.setItem_outputday(model.getRelayend1());
                itemView.setItem_gubun("경유지("+model.getLand1name()+") 에서 도착되었습니다");
                itemLists.add(itemView);

                itemView = new TrackingModelView();
                itemView.setItem_outputday(model.getRelaystart1());
                itemView.setItem_gubun("경유지("+model.getLand1name()+") 에서 출발하였습니다");
                itemLists.add(itemView);
            }
        }

        if ( !TextUtils.isEmpty(model.getSt3()) && model.getCnt() >= 4){
            if ( model.getSt3().equals("YN")){
                itemView = new TrackingModelView();
                itemView.setItem_outputday(model.getRelayend2());
                itemView.setItem_gubun("경유지("+model.getLand2name()+") 에 도착되었습니다");
                itemLists.add(itemView);
            }
            if ( model.getSt3().equals("YY")){

                itemView = new TrackingModelView();
                itemView.setItem_outputday(model.getRelayend2());
                itemView.setItem_gubun("경유지("+model.getLand2name()+") 에 도착되었습니다");
                itemLists.add(itemView);

                itemView = new TrackingModelView();
                itemView.setItem_outputday(model.getRelaystart2());
                itemView.setItem_gubun("경유지("+model.getLand2name()+") 에서 출발했습니다.");

                itemLists.add(itemView);
            }
        }

        if ( !TextUtils.isEmpty(model.getSt4()) && model.getCnt() >= 5){
            if ( model.getSt4().equals("YN")){
                itemView = new TrackingModelView();
                itemView.setItem_outputday(model.getRelayend3());
                itemView.setItem_gubun("경유지("+model.getLand3name()+") 에 도착되었습니다");
                itemLists.add(itemView);
            }
            if ( model.getSt4().equals("YY")){

                itemView = new TrackingModelView();
                itemView.setItem_outputday(model.getRelayend3());
                itemView.setItem_gubun("경유지("+model.getLand3name()+") 에 도착되었습니다");
                itemLists.add(itemView);

                itemView = new TrackingModelView();
                itemView.setItem_outputday(model.getRelaystart3());
                itemView.setItem_gubun("경유지("+model.getLand3name()+") 에서 출발하였습니다");
                itemLists.add(itemView);
            }
        }

        if ( model.getBillstate().equals("42") || model.getBillstate().equals("44")){
            if ( model.getBillstate().equals("42")){
                itemView = new TrackingModelView();

                if( model.getCnt() == 2)
                    itemView.setItem_outputday(model.getRelayend1());
                else if( model.getCnt() == 3 )
                    itemView.setItem_outputday(model.getRelayend2());
                else if( model.getCnt() == 4 )
                    itemView.setItem_outputday(model.getRelayend3());

                itemView.setItem_gubun("도착지("+model.getArrivalname()+")에 도착되었습니다");

                if ( model.getBillstate().equals("001")){
                    tracking_view_billstatus.setText("출고대기");
                }else{
                    tracking_view_billstatus.setText("배송준비중");
                }

                itemLists.add(itemView);
            }else{

                itemView = new TrackingModelView();

                if( model.getCnt() == 2)
                    itemView.setItem_outputday(model.getRelayend1());
                else if( model.getCnt() == 3 )
                    itemView.setItem_outputday(model.getRelayend2());
                else if( model.getCnt() == 4 )
                    itemView.setItem_outputday(model.getRelayend3());

                itemView.setItem_gubun("배달 도착지("+model.getArrivalname()+")에 도착되었습니다");
                itemLists.add(itemView);

                itemView = new TrackingModelView();
                itemView.setItem_outputday(model.getChulgoday());
                if (model.getTranscode().equals("002")){
                    itemView.setItem_gubun("물품 배달이 완료되었습니다");
                }else{
                    itemView.setItem_gubun("물품 출고가 완되었습니다");
                }

                itemLists.add(itemView);
                if ( !TextUtils.isEmpty(model.getBillstate()) && model.getBillstate().equals("44")){
                    if ( model.getBillstate().equals("001")){
                        tracking_view_billstatus.setText("출고완료");
                    }else{
                        tracking_view_billstatus.setText("배송완료");
                    }
                }
            }
        }

        if ( TextUtils.isEmpty(tracking_view_billstatus.getText())){
            tracking_view_billstatus.setText("운송중");
        }

        if ( itemLists != null && itemLists.size() > 0 ){
            recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            adapter = new TrackingViewAdapter(itemLists);
            recyclerView.setAdapter(adapter);
        }

        setProgressBar(2);
    }

    // 로딩바 정의
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

    public String maskingName(String str) {
        String replaceString = str;

        String pattern = "";
        if(str.length() == 2) {
            pattern = "^(.)(.+)$";
        } else {
            pattern = "^(.)(.+)(.)$";
        }

        Matcher matcher = Pattern.compile(pattern).matcher(str);

        if(matcher.matches()) {
            replaceString = "";

            for(int i=1;i<=matcher.groupCount();i++) {
                String replaceTarget = matcher.group(i);
                if(i == 2) {
                    char[] c = new char[replaceTarget.length()];
                    Arrays.fill(c, '*');

                    replaceString = replaceString + String.valueOf(c);
                } else {
                    replaceString = replaceString + replaceTarget;
                }

            }
        }
        return replaceString;
    }
}