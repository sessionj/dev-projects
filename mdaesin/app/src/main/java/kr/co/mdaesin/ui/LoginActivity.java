package kr.co.mdaesin.ui;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Pattern;

import kr.co.mdaesin.MainActivity;
import kr.co.mdaesin.R;
import kr.co.mdaesin.comm.Label;
import kr.co.mdaesin.comm.PatternUtil;

public class LoginActivity extends AppCompatActivity {

    Button request_btn;
    Button certification_btn;
    EditText user_phone_number,response_key, user_route;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        TextView textView = findViewById(R.id.login_txt);
        String word = Label.RECEIPT_CONSTRUCTOR_FINAL_NAME;
        String content = textView.getText().toString();
        int start = content.indexOf(word);
        int end = start + word.length();

        SpannableString spannableString = new SpannableString(content);
        spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#FF5E00")), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new StyleSpan(Typeface.BOLD), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new RelativeSizeSpan(1.5f), start, end, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.setText(spannableString);

        //this.InitializeView();
        //this.SetListener();

        request_btn = (Button) findViewById(R.id.request_btn);
        certification_btn = (Button) findViewById(R.id.certification_btn);
        user_phone_number = (EditText) findViewById(R.id.user_phone_number);
        response_key = (EditText) findViewById(R.id.response_key);
        user_route = (EditText) findViewById(R.id.user_route);

        request_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if ( TextUtils.isEmpty(user_route.getText().toString())) {
                   // 노선명이 없는경우
                    AlertDialog.Builder alert = new AlertDialog.Builder(LoginActivity.this);
                    alert.setTitle("메세지 발송");
                    alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    alert.setMessage("노선명을 입력후 다시 시도해주세요");
                    alert.show();
                }else {
                    // 후대폰 번호가 공백이거나 정규식에 어긋날겨우
                    if ( !TextUtils.isEmpty(user_phone_number.getText().toString()) && PatternUtil.isValidCellPhoneNumber(user_phone_number.getText().toString()) ){
                        AlertDialog.Builder alert = new AlertDialog.Builder(LoginActivity.this);
                        alert.setTitle("메세지 발송");
                        alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        alert.setMessage(user_phone_number.getText().toString() + " 인증번호 발송!");
                        alert.show();
                    }else {
                        AlertDialog.Builder alert = new AlertDialog.Builder(LoginActivity.this);
                        alert.setTitle("메세지 발송");
                        alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        if (TextUtils.isEmpty(user_phone_number.getText().toString())){
                            alert.setMessage("전화번호를 입력후 인증해주세요");
                        }else{
                            alert.setMessage("올바른 핸드폰 번호가 아닙니다");
                        }

                        alert.show();
                    }
                }
            }
        });

        certification_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(LoginActivity.this, "메인화면으로 이동합니다.", Toast.LENGTH_SHORT);
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();	//현재 액티비티 종료
            }
        });
    }

    private void setMessage(String msg){

        AlertDialog.Builder alert = new AlertDialog.Builder(LoginActivity.this);
        alert.setTitle("메세지 발송");
        alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alert.setMessage(user_phone_number.getText().toString() + msg);
        alert.show();
    }

}