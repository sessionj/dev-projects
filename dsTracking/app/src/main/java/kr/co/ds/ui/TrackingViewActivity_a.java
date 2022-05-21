package kr.co.ds.ui;

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

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import kr.co.ds.R;
import kr.co.ds.adapter.TrackingViewAdapter;
import kr.co.ds.comm.Label;
import kr.co.ds.models.TrackingModelView;

public class TrackingViewActivity_a extends AppCompatActivity {

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
        tracking_view_billno.setText(model.getBillno());
        tracking_view_goods.setText(model.getGoods()+", "+model.getPojang());
        tracking_view_sendingman.setText(model.getSendingman()+"("+model.getSendingmantel()+")");
        tracking_view_arrivalman.setText(model.getArrivalman()+"("+model.getArrivalmantel()+")");

        // 아답터에 들어갈 리스트 만들기
        itemLists = new ArrayList<TrackingModelView>();

        int createRowCount = 0;


        boolean isJejuIsland = false;
        boolean courierUnavailable = false;
        int state = 0;
        String relayStart1 = "";
        String relayStart2 = "";
        String relayStart3 = "";
        String relayEnd4 = "";
        String lastAgencyCode = "";
        String locate = "";
        String unsongState = null;
        String transType = "정기";


        createRowCount = model.getCnt();    // 생성될 라인수

        switch (createRowCount){
            case 2 :
                lastAgencyCode = model.getAgency2().substring(0,2);
                break;
            case 3:
                lastAgencyCode = model.getAgency3().substring(0,2);
                break;
            case 4:
                lastAgencyCode = model.getAgency4().substring(0,2);
                break;
            case 5:
                lastAgencyCode = model.getAgency5().substring(0,2);
                break;
        }
        if ( !TextUtils.isEmpty(model.getCourierunavailable()))	{
            if ( !model.getCourierunavailable().equals("0000")){
                courierUnavailable = true;
            }
        }
        if (!TextUtils.isEmpty(model.getArea()) && model.getArea().equals("1")){
            isJejuIsland = true;
        }
        state = model.getBillstate() == null ? 0 : Integer.parseInt(model.getBillstate().substring(0,1));

        model.setRelaystart1( model.getRelaystart2());
        model.setRelaystart2( model.getRelaystart3());
        model.setRelaystart3( model.getRelaystart4());

        if (createRowCount == 2){
            model.setRelayend4(model.getRelayend1());
        }else if ( createRowCount == 3){
            model.setRelayend4(model.getRelayend2());
        }else if ( createRowCount == 4){
            model.setRelayend4(model.getRelayend3());
        }
        if ( model.getTranscode().equals("002") || model.getTranscode().equals("004") || model.getTranscode().equals("009")){
            transType = "택배";
        }
        Log.d(TAG, "viewSetting: ======================================= state : " + model.getBillstate());

        if( model.getBillstate() != null && !model.getBillstate().equals("") ) {

            if (model.getBillstate().equals("02")) {
                locate = "발송대기중";
                unsongState = "발송지에서 발송대기중입니다.";

                // getTbanprintyn = 0 (무조건)
            } else if (model.getTbanprintyn().equals("1") && model.getBillstate().equals("42")) {
                if (lastAgencyCode.equals("99")) {

                    if (model.getScaninfo() == null) {
                        locate = "<span style='color:blue;'>도착예정</span>";
                        unsongState = "목적지에 도착 예정입니다";
                    } else {
                        locate = "<span style='color:blue;'>배달준비중</span>";
                        unsongState = "목적지에 도착되어 배달중이나, 정확한 내용은 영업소와 통화하여 확인하시기 바랍니다";
                    }
                } else {
                    if (courierUnavailable) {
                        unsongState = "목적지에 도착되어 배달준비중 입니다";
                        locate = "배달준비중";
                    } else {
                        unsongState = "목적지에 도착되어 배달중입니다";
                        locate = "배달중";
                    }
                }
            } else if (model.getBillstate().equals("42")) {
                if (transType.equals("택배")) {
                    if (lastAgencyCode.equals("99")) {
                        if (model.getScaninfo() == null) {
                            locate = "<span style='color:blue;'>도착예정</span>";
                            unsongState = "목적지에 도착 예정입니다";
                        } else {
                            locate = "<span style='color:blue;'>배달준비중</span>";
                            unsongState = "목적지에 도착되어 배달중이나, 정확한 내용은 영업소와 통화하여 확인하시기 바랍니다";
                        }
                    } else {
                        if (courierUnavailable) {
                            unsongState = "목적지에 도착되어 배달준비중 입니다";
                            locate = "배달준비중";
                        } else {
                            unsongState = "목적지에 도착되어 배달중입니다";
                            locate = "배달중";
                        }
                    }
                } else {
                    if (lastAgencyCode.equals("99")) {
                        if (model.getScaninfo() == null) {
                            locate = "<span style='color:blue;'>도착예정</span>";
                            unsongState = "목적지에 도착 예정입니다";
                        } else {
                            locate = "<span style='color:blue;'>배달준비중</span>";
                            unsongState = "목적지에 도착되어 배달중이나, 정확한 내용은 영업소와 통화하여 확인하시기 바랍니다";
                        }
                    } else {
                        locate = "인도대기중";
                        unsongState = "목적지에 도착되어 인도대기중입니다";
                    }
                }
            } else if (model.getBillstate().length() == 2 && model.getBillstate().substring(1, 2).equals("X")) {
                locate = "전량미착";
                unsongState = "전량미착";
            } else if (model.getBillstate().length() == 2 && model.getBillstate().substring(1, 2).equals("Y")) {
                locate = "과착";
                unsongState = "과착";
            } else if (model.getBillstate().equals("44")) {
                if (transType.equals("택배")) {
                    locate = "배송완료";
                    if (model.getChulgoday() != null && model.getChulgoday().length() > 15) {
                        unsongState = "[<span style='color:blue;'> " + (model.getInsuja() == null ? maskingName(model.getArrivalman()) : maskingName(model.getInsuja())) + "</span> ] 님께" + model.getChulgoday().substring(5, 7) + " 월 " + model.getChulgoday().substring(8, 10) + " 일 " + model.getChulgoday().substring(11, 13) + " 시 " + model.getChulgoday().substring(14, 16) + " 분에 인도 되었습니다.";
                    } else {
                        unsongState = "고객께서 화물을 인수 하였습니다.";
                    }
                } else {
                    locate = "배송완료";
                    if (model.getChulgoday() != null && model.getChulgoday().length() > 15) {
                        unsongState = "[<span style='color:blue;'> " + (model.getInsuja() == null ? maskingName(model.getArrivalman()) : maskingName(model.getInsuja())) + "</span> ] 님께" + model.getChulgoday().substring(5, 7) + " 월 " + model.getChulgoday().substring(8, 10) + " 일 " + model.getChulgoday().substring(11, 13) + " 시 " + model.getChulgoday().substring(14, 16) + " 분에 인수 하셨습니다.";
                    } else {
                        unsongState = "고객께서 화물을 인수 하였습니다.";
                    }
                }
            } else {
                locate = "운송중";
                unsongState = "목적지로 이동중입니다.";
                //unsongState = "목적지로 이동중이며 " + internal.getRelayend4().substring(5, 7) + " 월  " + internal.getRelayend4().substring(8, 10) + " 일 도착예정 입니다";
            }
        }
        itemView = new TrackingModelView();
        itemView.setItem_gubun("발송취급점");
        itemView.setItem_agencyname(model.getSendname());
        itemView.setItem_tel(model.getSendtel());
        itemView.setItem_inputday(model.getJubsuday());
        itemView.setItem_outputday(model.getSendingday());
        itemView.setItem_location(state == 0 ? locate : "");
        itemLists.add(itemView);

        // 경유 1
        if ( createRowCount >=3 && model.getLand1name() != ""){

            itemView = new TrackingModelView();
            itemView.setItem_gubun("경유취급점1");
            itemView.setItem_agencyname(model.getLand1name());
            itemView.setItem_tel(model.getLand1tel());

            if(model.getCnt() > 2){
                itemView.setItem_tel(model.getInternettel2());
            }else if (model.getCnt() == 2){
                itemView.setItem_tel(model.getInternettel3());
            }

            if (!TextUtils.isEmpty(model.getSt2())){
                if( model.getSt2().equals("YY") || model.getSt2().equals("NY") ){

                    itemView.setItem_inputday(model.getRelayend1());
                    itemView.setItem_outputday(model.getRelaystart1());
                }
            }
            Log.d(TAG, "viewSetting: ===================> location : " + locate);
            itemView.setItem_location(state == 1 ? locate : "");

            itemLists.add(itemView);
        }

        if ( createRowCount >=4 && model.getLand2name() != ""){

            itemView = new TrackingModelView();
            itemView.setItem_gubun("경유취급점2");
            itemView.setItem_agencyname(model.getLand2name());
            itemView.setItem_tel(model.getLand2tel());

            if(model.getCnt() > 3){
                itemView.setItem_tel(model.getInternettel3());
            }else if (model.getCnt() == 3){
                itemView.setItem_tel(model.getInternettel4());
            }

            if (!TextUtils.isEmpty(model.getSt3())){
                if( model.getSt3().equals("YY") || model.getSt3().equals("NY") ){
                    itemView.setItem_inputday(model.getRelayend2());
                    itemView.setItem_outputday(model.getRelaystart2());
                }
            }
            itemView.setItem_location(state == 1 ? locate : "");

            itemLists.add(itemView);
        }

        if ( createRowCount >=5 && model.getLand3name() != ""){

            itemView = new TrackingModelView();
            itemView.setItem_gubun("경유취급점3");
            itemView.setItem_agencyname(model.getLand3name());
            itemView.setItem_tel(model.getLand3tel());

            if(model.getCnt() > 4){
                itemView.setItem_tel(model.getInternettel4());
            }else if (model.getCnt() == 4){
                itemView.setItem_tel(model.getInternettel5());
            }

            if (!TextUtils.isEmpty(model.getSt4())){
                if( model.getSt4().equals("YY") || model.getSt4().equals("NY") ){
                    itemView.setItem_inputday(model.getRelayend3());
                    itemView.setItem_outputday(model.getRelaystart3());
                }
            }
            itemView.setItem_location(state == 1 ? locate : "");

            itemLists.add(itemView);
        }

        itemView = new TrackingModelView();
        itemView.setItem_gubun("도착취급점");
        itemView.setItem_agencyname(model.getArrivalname());
        itemView.setItem_tel(model.getArrivaltel());

        if ( model.getCnt() == 3) {
            itemView.setItem_tel(model.getInternettel3());
        }else if ( model.getCnt() == 4){
            itemView.setItem_tel(model.getInternettel4());
        }else if ( model.getCnt() == 5){
            itemView.setItem_tel(model.getInternettel5());
        }

        if( model.getBillstate().equals("42") || model.getBillstate().equals("44") ){
            // 제주일 경우에는 도착일자 변경
            if ( isJejuIsland){
                if (model.getBillstate().equals("44") ){
                    // 배송완료인데 혹시 스캔자료가 없다면 도착통신 시간으로 스캔 시간이 있으면 스캔 시가능로
                    if  ( model.getScaninfo() == null){
                        itemView.setItem_inputday(model.getRelayend4());
                    }else{
                        itemView.setItem_inputday(model.getScaninfo());
                    }
                }else{
                    itemView.setItem_inputday(model.getScaninfo());
                }
            }else{
                itemView.setItem_inputday(model.getRelayend4());
            }
            itemView.setItem_outputday(model.getBillstate().equals("44") ? model.getChulgoday() : "");
        }
        itemView.setItem_location(state == 4 ? locate : "");

        itemLists.add(itemView);

        if ( itemLists != null && itemLists.size() > 0 ){
            recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            adapter = new TrackingViewAdapter(itemLists);
            recyclerView.setAdapter(adapter);
        }
        tracking_view_billstatus.setText(locate.toString());
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