package kr.co.mdaesin.ui;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.service.autofill.Validator;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.mobsandgeeks.saripaar.annotation.Checked;
import com.mobsandgeeks.saripaar.annotation.ConfirmPassword;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.Length;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Password;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import kr.co.mdaesin.MainActivity;
import kr.co.mdaesin.R;
import kr.co.mdaesin.action.request.ReceiptDetailsRequest;
import kr.co.mdaesin.action.request.ReceiptHistoryRequest;
import kr.co.mdaesin.action.request.SuggestionRequest;
import kr.co.mdaesin.adapter.ReceptDetailsAdapter;
import kr.co.mdaesin.adapter.ReceptDetailsUnsongAdapter;
import kr.co.mdaesin.adapter.ReceptHistoryAdapter;
import kr.co.mdaesin.adapter.SpinnerAdapter;
import kr.co.mdaesin.comm.Label;
import kr.co.mdaesin.comm.SharedPreferenceConf;
import kr.co.mdaesin.models.ReceiptDetailsModelView;
import kr.co.mdaesin.models.ReceiptHistoryModelView;
import kr.co.mdaesin.models.ReceptionQuantityModelView;
import kr.co.mdaesin.models.SuggestionModelView;
import kr.co.mdaesin.ui.popup.HistoryPopupActivity;

public class TendinousActivity extends AppCompatActivity {

    private String TAG = " :: ReceiptDetailsActivity.LOG";

    TextView tendinous_top_title;
    EditText edit_linename, edit_username, edit_phone, edit_content;
    Spinner editSpinner;
    Button edit_save_btn;
    CheckBox edit_secret;
    private SuggestionModelView suggestionModelView;
    private String categorySelect = "";
    private ProgressBar progressBar;
    private boolean responseStatus;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tendinous);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.title_tendinous);
        tendinous_top_title = (TextView) findViewById(R.id.tendinous_top_title);

        editSpinner = (Spinner) findViewById(R.id.edit_spinner);
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(this,R.array.edit_spinner_arr, android.R.layout.simple_spinner_dropdown_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        editSpinner.setAdapter(spinnerAdapter);
        editSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                categorySelect = ""+parent.getItemAtPosition(position);

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        edit_linename = (EditText) findViewById(R.id.edit_linename);
        edit_username = (EditText) findViewById(R.id.edit_username);
        edit_phone = (EditText) findViewById(R.id.edit_phone);
        edit_content = (EditText) findViewById(R.id.edit_content);
        edit_save_btn = (Button) findViewById(R.id.edit_save_btn);
        edit_secret = (CheckBox) findViewById(R.id.edit_secret);
        progressBar = findViewById(R.id.receipt_edit_progressBar);

        edit_phone.setText(SharedPreferenceConf.getPhoneNumber(this).toString());
        edit_linename.setText(SharedPreferenceConf.getLineName(this).toString());
        edit_save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                new Handler().postDelayed(new Runnable(){
                    @Override
                    public void run(){
                        suggestionSave();
                    }
                }, 1000);// 0.6초 정도 딜레이를 준 후 시작
            }
        });
    }

    private void suggestionSave() {

        suggestionModelView = new SuggestionModelView();

        // 카테고리분류
        suggestionModelView.setCategory(categorySelect);
        // 관리자/기사 이름
        suggestionModelView.setUsername(edit_username.getText().toString());
        // 노선명
        suggestionModelView.setLinename(edit_linename.getText().toString());
        // 전화번호
        suggestionModelView.setPhone(edit_phone.getText().toString());
        // 내용
        suggestionModelView.setContent(edit_content.getText().toString());

        // 비밀글 여부
        if ( edit_secret.isChecked()){
            suggestionModelView.setSecert("Y");
        }else{
            suggestionModelView.setSecert("N");
        }

        if ( isValidation(suggestionModelView)){

            pageOpenPopup("등록되었습니다");
            progressBar.setVisibility(View.GONE);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);


            //SuggestionRequest suggestionRequest = new SuggestionRequest(suggestionModelView, successListener(), errorListener());
            //RequestQueue queue = Volley.newRequestQueue( TendinousActivity.this);
            //queue.add(suggestionRequest);
        }
    }

    private Response.Listener<String> successListener() {
        return new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);

                    JSONArray resultarray = jsonObject.getJSONArray("rows");

                    if ( resultarray.length() > 0){
                        for ( int i=0; i < resultarray.length(); i++){
                            JSONObject Object = resultarray.getJSONObject(i);
                            if ( Object.getString("accessright").equals("SUCCESS")){
                                responseStatus = true;
                                pageOpenPopup("등록되었습니다");

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

    private boolean isValidation(SuggestionModelView modelView){
        Log.d(TAG, "isValidation: ----------------------------------->" + modelView.getSecert());
        if (TextUtils.isEmpty(modelView.getCategory()) || modelView.getCategory().trim().equals(Label.SPINNER_SEQUENCE_0.trim())){
            setToastMessage("카테고리가 선택되지 않았습니다");
            return false;
        }
        if (TextUtils.isEmpty(modelView.getUsername())){
            setToastMessage("관리자(기사) 명이 입력되지 않았습니다");
            return false;
        }
        if (TextUtils.isEmpty(modelView.getLinename())){
            setToastMessage("노선명 명이 입력되지 않았습니다");
            return false;
        }
        if (TextUtils.isEmpty(modelView.getPhone())){
            setToastMessage("전화번호가 입력되지 않았습니다");
            return false;
        }
        if (TextUtils.isEmpty(modelView.getContent())){
            setToastMessage("내용이 등록되지 않았습니다");
            return false;
        }

        return true;
    }

    private void setToastMessage(String msg){
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
        progressBar.setVisibility(View.GONE);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    private Response.ErrorListener errorListener() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        };
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home ){
            pageBackPressProcess();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        pageBackPressProcess();
    }

    private void pageBackPressProcess(){

        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("알림!!");
        alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });

        alert.setIcon(R.drawable.ic_baseline_warning_24);
        alert.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        alert.setMessage("작성중이던 글이 삭제 됩니다");
        alert.show();
    }

    // 처음에 한번 띄우기
    private void pageOpenPopup(String msg){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("알림!!");
        alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
                overridePendingTransition(0, 0);
                startActivity(getIntent());
                overridePendingTransition(0, 0);
            }
        });

        alert.setIcon(R.drawable.ic_baseline_info_24);

        alert.setMessage(msg);
        alert.show();
    }
}