package kr.co.mdaesin.ui.popup;

import static android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import kr.co.mdaesin.R;
import kr.co.mdaesin.comm.Label;
import kr.co.mdaesin.models.ReceiptHistoryModelView;
import kr.co.mdaesin.models.ReceptionQuantityModelView;

public class HistoryPopupActivity extends AppCompatActivity {
    TextView popup_content1, popup_content2, popup_content3, popup_content4, popup_content5;
    private ReceiptHistoryModelView receiptHistoryModelView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, FLAG_FULLSCREEN);
        setContentView(R.layout.activity_history_popup);

        receiptHistoryModelView = new ReceiptHistoryModelView();
        Intent intent = getIntent();
        receiptHistoryModelView = (ReceiptHistoryModelView) intent.getSerializableExtra("histModel");

        //UI 객체생성
        popup_content1 = (TextView)findViewById(R.id.popup_content1);
        popup_content2 = (TextView)findViewById(R.id.popup_content2);
        popup_content3 = (TextView)findViewById(R.id.popup_content3);
        popup_content4 = (TextView)findViewById(R.id.popup_content4);
        popup_content5 = (TextView)findViewById(R.id.popup_content5);

        popup_content1.setText(Label.RECEIPT_HIST_BILLNO + getStringSplit(receiptHistoryModelView.getBillNo()));
        popup_content2.setText(Label.RECEIPT_HIST_AGENCY + receiptHistoryModelView.getAgencyname());
        popup_content3.setText(Label.RECEIPT_HIST_DATE  + getDateSplit(receiptHistoryModelView.getUpdatedate())+" "+getTimeSplit(receiptHistoryModelView.getUpdatetime()));
        popup_content4.setText(Label.RECEIPT_HIST_CATEGORY +"" + receiptHistoryModelView.getCategory());
        popup_content5.setText(Label.RECEIPT_HIST_CONTENT  + receiptHistoryModelView.getBefcontent().toString() + "=>" + receiptHistoryModelView.getAftcontent());

        popup_content1.setTextColor(Color.rgb(255,153,51));

    }
    // 운송장번호 분리
    private String getStringSplit(String billNo){
        if (TextUtils.isEmpty(billNo) || billNo.length() != 13){
            return "";
        }
        return billNo.substring(0,4) + "-" + billNo.substring(4,7) + "-" + billNo.substring(7,13);
    }
    // 시간 분리
    private String getTimeSplit(String time){
        if (TextUtils.isEmpty(time) || time.length() != 4){
            return "";
        }
        return time.substring(0,2) + ":" + time.substring(2,4) ;
    }
    // 날짜 분리
    private String getDateSplit(String date){
        if (TextUtils.isEmpty(date) || date.length() != 8){
            return "";
        }
        return date.substring(0,4) + "-" + date.substring(4,6) + "-" + date.substring(6,8) ;
    }

    //확인 버튼 클릭
    public void mOnClose(View v){
        finish();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //바깥레이어 클릭시 안닫히게
        if(event.getAction()==MotionEvent.ACTION_OUTSIDE){
            return false;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        return;
    }
}