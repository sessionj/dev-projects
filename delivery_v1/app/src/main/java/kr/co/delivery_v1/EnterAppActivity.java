package kr.co.delivery_v1;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.widget.TextView;

import kr.co.delivery_v1.action.DeliveryDao;
import kr.co.delivery_v1.action.DeliveryRequestDao;
import kr.co.delivery_v1.comm.BasicUtils;
import kr.co.delivery_v1.comm.DeviceInfoUtil;
import kr.co.delivery_v1.comm.Label;
import kr.co.delivery_v1.login.LoginActivity;
import kr.co.delivery_v1.models.DeliveryRequestModelView;

public class EnterAppActivity extends AppCompatActivity {

    private DeliveryRequestDao deliveryRequestDao;
    private DeliveryDao deliveryDao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_app);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        moveMain(2);

        TextView textView = findViewById(R.id.enter_txt);
        String word = "배달通";
        String content = textView.getText().toString();
        int start = content.indexOf(word);
        int end = start + word.length();

        SpannableString spannableString = new SpannableString(content);
        spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#FF5E00")), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new StyleSpan(Typeface.BOLD), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new RelativeSizeSpan(1.3f), start, end, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.setText(spannableString);
    }

    // 자료 삭제, Activity 이동
    private void moveMain(int sec) {
        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                deleteProcess();
                // 로그인 여부 체크 후 선택하여 처리 (room DB)
                if (DeviceInfoUtil.getRoomSelecter(getApplicationContext(), 2) == null
                        || DeviceInfoUtil.getRoomSelecter(getApplicationContext(), 2).equals("")
                        || TextUtils.isEmpty(DeviceInfoUtil.getRoomSelecter(getApplicationContext(), 2))){
                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(intent);
                }else{
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                }
                finish();	//현재 액티비티 종료
            }
        }, 1000 * sec); // sec초 정도 딜레이를 준 후 시작
    }

    // 자료 삭제
    private void deleteProcess() {
        deliveryRequestDao = new DeliveryRequestDao(this);
        deliveryDao = new DeliveryDao(this);
        // test data
        /*DeliveryRequestModelView deliveryRequestModelView = null;
        deliveryRequestModelView = new DeliveryRequestModelView();
        deliveryRequestModelView.setReqdate("20220429");
        deliveryRequestModelView.setDelivery_count(10);
        deliveryRequestModelView.setDeliverycourse("100");
        deliveryRequestModelView.setRequestdate("20220429");
        deliveryRequestModelView.setDeliverycoursenm("코스명");
        deliveryRequestDao.isDeliveryRequestAdd(deliveryRequestModelView);*/

        deliveryRequestDao.isDeliveryRequestDel(BasicUtils.getDays(Label.DELIVERY_STANDARD_DATE_FORMAT).replace("-", ""));
        deliveryDao.isDeliveryDel(BasicUtils.getDays(Label.DELIVERY_STANDARD_DATE_FORMAT).replace("-", ""));
    }
}