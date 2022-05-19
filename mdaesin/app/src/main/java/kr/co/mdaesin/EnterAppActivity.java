package kr.co.mdaesin;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.widget.TextView;

import kr.co.mdaesin.comm.Label;
import kr.co.mdaesin.comm.SharedPreferenceConf;
import kr.co.mdaesin.ui.LoginActivity;

public class EnterAppActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_app);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        moveMain(2);

        TextView textView = findViewById(R.id.enter_txt);
        String word = Label.RECEIPT_CONSTRUCTOR_FINAL_NAME;
        String content = textView.getText().toString();
        int start = content.indexOf(word);
        int end = start + word.length();

        SpannableString spannableString = new SpannableString(content);
        spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#FF5E00")), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new StyleSpan(Typeface.BOLD), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new RelativeSizeSpan(1.5f), start, end, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.setText(spannableString);

        //SharedPreferenceConf.clearUserName(EnterAppActivity.this);

    }

    // 자료 삭제, Activity 이동
    private void moveMain(int sec) {
        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run()
            {

                if (SharedPreferenceConf.getPhoneNumber(EnterAppActivity.this).length() == 0){
                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(intent);
                    finish();
                }else{
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        }, 1000 * sec); // sec초 정도 딜레이를 준 후 시작
    }
}