package kr.co.ds.ui;

import android.content.DialogInterface;
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
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import kr.co.ds.MainActivity;
import kr.co.ds.R;
import kr.co.ds.action.ReceiptLoginRequest;
import kr.co.ds.comm.Label;
import kr.co.ds.comm.PatternUtil;
import kr.co.ds.comm.SharedPreferenceConf;
import kr.co.ds.models.TrackingModelView;


public class LoginActivity extends AppCompatActivity {

    Button request_btn;
    Button certification_btn;
    EditText user_phone_number,response_key;
    private boolean isLoginCheck = false;
    private boolean responseStatus;
    private ProgressBar progressBar;
    private TrackingModelView model;
    private String authenticationKey;
    private String TAG = "LoginActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        TextView textView = findViewById(R.id.login_txt);
        String word = Label.RECEIPT_CONSTRUCTOR_FINAL_TRACKING;
        String content = textView.getText().toString();
        int start = content.indexOf(word);
        int end = start + word.length();

        SpannableString spannableString = new SpannableString(content);
        spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#A9E2F3")), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new StyleSpan(Typeface.BOLD), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new RelativeSizeSpan(1.5f), start, end, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.setText(spannableString);

        //this.InitializeView();
        //this.SetListener();
        model = new TrackingModelView();
        request_btn = (Button) findViewById(R.id.request_btn);
        certification_btn = (Button) findViewById(R.id.certification_btn);
        user_phone_number = (EditText) findViewById(R.id.user_phone_number);
        response_key = (EditText) findViewById(R.id.response_key);

        progressBar = findViewById(R.id.receipt_login_progressBar);

        request_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 후대폰 번호가 공백이거나 정규식에 어긋날겨우
                if ( !TextUtils.isEmpty(user_phone_number.getText().toString()) && PatternUtil.isValidCellPhoneNumber(user_phone_number.getText().toString()) ){
                    AlertDialog.Builder alert = new AlertDialog.Builder(LoginActivity.this);
                    alert.setTitle(Label.MESSAGE_TITLE);
                    alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            returnLoginInformation();
                            isLoginCheck = true;
                            dialog.dismiss();
                        }
                    });
                    alert.setMessage(user_phone_number.getText().toString() + Label.MESSAGE_SENDING_KEY);
                    alert.show();
                }else {

                    if (TextUtils.isEmpty(user_phone_number.getText().toString())){
                        setLogicMessage(Label.MESSAGE_INFO, Label.MESSAGE_NOT_PHONE_NUMBER);
                    }else{
                        setLogicMessage(Label.MESSAGE_INFO, Label.MESSAGE_DIFF_PHONE_NUMBER);
                    }
                }
            }
        });

        certification_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // 전화번호와 키값을 던져 확인 (로그인 여부 등록)
                // 인증번호를 받지 않았거나 인증번호 항목이 공란일 경우
                /*if ( !isLoginCheck){
                    setLogicMessage(Label.MESSAGE_INFO, Label.MESSAGE_CERT_PHONE_NUMBER);

                }else if ( isLoginCheck && TextUtils.isEmpty(response_key.getText().toString())){

                    setLogicMessage(Label.MESSAGE_INFO, Label.MESSAGE_NOT_AUTHENTICATIONKEY);
                }else{
                    returnKeyCheck();
                }*/
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void setLogicMessage(String title, String msg){

        AlertDialog.Builder alert = new AlertDialog.Builder(LoginActivity.this);
        alert.setTitle(title);
        alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alert.setMessage(msg);
        alert.show();
    }


    private void returnLoginInformation(){
        progressBar.setVisibility(View.VISIBLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

        model.setSearchMode(Label.DELIVERY_BASE_URL_RECEIPT_LOGIN_CHECK);
        model.setArrivalmantel(user_phone_number.getText().toString());

        ReceiptLoginRequest receiptLoginRequest = new ReceiptLoginRequest(model, successListener(), errorListener());
        RequestQueue queue = Volley.newRequestQueue( LoginActivity.this);
        queue.add(receiptLoginRequest);

    }

    private Response.Listener<String> successListener() {
        return new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    if ( !response.isEmpty() && response != null){
                        JSONObject jsonObject = new JSONObject(response);
                        JSONArray resultarray = jsonObject.getJSONArray("rows");

                        if (resultarray.length() > 0) {

                            for (int i = 0; i < resultarray.length(); i++) {
                                JSONObject Object = resultarray.getJSONObject(i);
                                authenticationKey = Object.getString("authenticationkey");
                            }
                        }
                    }
                } catch(JSONException e) {
                    e.printStackTrace();
                } finally {
                    progressBar.setVisibility(View.GONE);
                    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                    Toast.makeText(getApplicationContext(), authenticationKey, Toast.LENGTH_SHORT).show();
                }
            }
        };
    }

    private void returnKeyCheck(){
        progressBar.setVisibility(View.VISIBLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

        model.setSearchMode(Label.DELIVERY_BASE_URL_RECEIPT_LOGIN_LAST);

        model.setArrivalmantel(user_phone_number.getText().toString());
        model.setAuthenticationkey(response_key.getText().toString());
        ReceiptLoginRequest receiptLoginRequest = new ReceiptLoginRequest(model, checkListener(), errorListener());
        RequestQueue queue = Volley.newRequestQueue( LoginActivity.this);
        queue.add(receiptLoginRequest);

    }

    private Response.Listener<String> checkListener() {
        return new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    if ( !response.isEmpty() && response != null){
                        JSONObject jsonObject = new JSONObject(response);
                        JSONArray resultarray = jsonObject.getJSONArray("rows");

                        if (resultarray.length() > 0) {

                            for (int i = 0; i < resultarray.length(); i++) {
                                JSONObject Object = resultarray.getJSONObject(i);
                                if ( Object.getString("accessright").equals("SUCCESS")){
                                    responseStatus = true;
                                }
                            }
                            if ( responseStatus){
                                // 저장한다.
                                SharedPreferenceConf.setPhoneNumber(getApplicationContext(), model.getArrivalmantel());

                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(intent);
                                finish();	//현재 액티비티 종료
                            }else{
                                setLogicMessage(Label.MESSAGE_ERROR, Label.MESSAGE_ERROR_01);
                            }
                        }
                    }
                } catch(JSONException e) {
                    e.printStackTrace();
                } finally {
                    progressBar.setVisibility(View.GONE);
                    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                }
            }
        };
    }

    private Response.ErrorListener errorListener() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                error.printStackTrace();
            }
        };
    }
}