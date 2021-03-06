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

        // ???????????? ??????
        tracking_view_billno.setText(model.getBillno());
        tracking_view_goods.setText(model.getGoods()+", "+model.getPojang());
        tracking_view_sendingman.setText(model.getSendingman()+"("+model.getSendingmantel()+")");
        tracking_view_arrivalman.setText(model.getArrivalman()+"("+model.getArrivalmantel()+")");

        // ???????????? ????????? ????????? ?????????
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
        String transType = "??????";


        createRowCount = model.getCnt();    // ????????? ?????????

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
            transType = "??????";
        }
        Log.d(TAG, "viewSetting: ======================================= state : " + model.getBillstate());

        if( model.getBillstate() != null && !model.getBillstate().equals("") ) {

            if (model.getBillstate().equals("02")) {
                locate = "???????????????";
                unsongState = "??????????????? ????????????????????????.";

                // getTbanprintyn = 0 (?????????)
            } else if (model.getTbanprintyn().equals("1") && model.getBillstate().equals("42")) {
                if (lastAgencyCode.equals("99")) {

                    if (model.getScaninfo() == null) {
                        locate = "<span style='color:blue;'>????????????</span>";
                        unsongState = "???????????? ?????? ???????????????";
                    } else {
                        locate = "<span style='color:blue;'>???????????????</span>";
                        unsongState = "???????????? ???????????? ???????????????, ????????? ????????? ???????????? ???????????? ??????????????? ????????????";
                    }
                } else {
                    if (courierUnavailable) {
                        unsongState = "???????????? ???????????? ??????????????? ?????????";
                        locate = "???????????????";
                    } else {
                        unsongState = "???????????? ???????????? ??????????????????";
                        locate = "?????????";
                    }
                }
            } else if (model.getBillstate().equals("42")) {
                if (transType.equals("??????")) {
                    if (lastAgencyCode.equals("99")) {
                        if (model.getScaninfo() == null) {
                            locate = "<span style='color:blue;'>????????????</span>";
                            unsongState = "???????????? ?????? ???????????????";
                        } else {
                            locate = "<span style='color:blue;'>???????????????</span>";
                            unsongState = "???????????? ???????????? ???????????????, ????????? ????????? ???????????? ???????????? ??????????????? ????????????";
                        }
                    } else {
                        if (courierUnavailable) {
                            unsongState = "???????????? ???????????? ??????????????? ?????????";
                            locate = "???????????????";
                        } else {
                            unsongState = "???????????? ???????????? ??????????????????";
                            locate = "?????????";
                        }
                    }
                } else {
                    if (lastAgencyCode.equals("99")) {
                        if (model.getScaninfo() == null) {
                            locate = "<span style='color:blue;'>????????????</span>";
                            unsongState = "???????????? ?????? ???????????????";
                        } else {
                            locate = "<span style='color:blue;'>???????????????</span>";
                            unsongState = "???????????? ???????????? ???????????????, ????????? ????????? ???????????? ???????????? ??????????????? ????????????";
                        }
                    } else {
                        locate = "???????????????";
                        unsongState = "???????????? ???????????? ????????????????????????";
                    }
                }
            } else if (model.getBillstate().length() == 2 && model.getBillstate().substring(1, 2).equals("X")) {
                locate = "????????????";
                unsongState = "????????????";
            } else if (model.getBillstate().length() == 2 && model.getBillstate().substring(1, 2).equals("Y")) {
                locate = "??????";
                unsongState = "??????";
            } else if (model.getBillstate().equals("44")) {
                if (transType.equals("??????")) {
                    locate = "????????????";
                    if (model.getChulgoday() != null && model.getChulgoday().length() > 15) {
                        unsongState = "[<span style='color:blue;'> " + (model.getInsuja() == null ? maskingName(model.getArrivalman()) : maskingName(model.getInsuja())) + "</span> ] ??????" + model.getChulgoday().substring(5, 7) + " ??? " + model.getChulgoday().substring(8, 10) + " ??? " + model.getChulgoday().substring(11, 13) + " ??? " + model.getChulgoday().substring(14, 16) + " ?????? ?????? ???????????????.";
                    } else {
                        unsongState = "???????????? ????????? ?????? ???????????????.";
                    }
                } else {
                    locate = "????????????";
                    if (model.getChulgoday() != null && model.getChulgoday().length() > 15) {
                        unsongState = "[<span style='color:blue;'> " + (model.getInsuja() == null ? maskingName(model.getArrivalman()) : maskingName(model.getInsuja())) + "</span> ] ??????" + model.getChulgoday().substring(5, 7) + " ??? " + model.getChulgoday().substring(8, 10) + " ??? " + model.getChulgoday().substring(11, 13) + " ??? " + model.getChulgoday().substring(14, 16) + " ?????? ?????? ???????????????.";
                    } else {
                        unsongState = "???????????? ????????? ?????? ???????????????.";
                    }
                }
            } else {
                locate = "?????????";
                unsongState = "???????????? ??????????????????.";
                //unsongState = "???????????? ??????????????? " + internal.getRelayend4().substring(5, 7) + " ???  " + internal.getRelayend4().substring(8, 10) + " ??? ???????????? ?????????";
            }
        }
        itemView = new TrackingModelView();
        itemView.setItem_gubun("???????????????");
        itemView.setItem_agencyname(model.getSendname());
        itemView.setItem_tel(model.getSendtel());
        itemView.setItem_inputday(model.getJubsuday());
        itemView.setItem_outputday(model.getSendingday());
        itemView.setItem_location(state == 0 ? locate : "");
        itemLists.add(itemView);

        // ?????? 1
        if ( createRowCount >=3 && model.getLand1name() != ""){

            itemView = new TrackingModelView();
            itemView.setItem_gubun("???????????????1");
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
            itemView.setItem_gubun("???????????????2");
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
            itemView.setItem_gubun("???????????????3");
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
        itemView.setItem_gubun("???????????????");
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
            // ????????? ???????????? ???????????? ??????
            if ( isJejuIsland){
                if (model.getBillstate().equals("44") ){
                    // ?????????????????? ?????? ??????????????? ????????? ???????????? ???????????? ?????? ????????? ????????? ?????? ????????????
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

    // ????????? ??????
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