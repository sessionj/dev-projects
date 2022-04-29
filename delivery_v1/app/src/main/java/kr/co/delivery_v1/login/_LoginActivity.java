package kr.co.delivery_v1.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import kr.co.delivery_v1.R;

public class _LoginActivity extends AppCompatActivity {

    private EditText phoneNumber, agencyCode, deliveryCourse;
    private Button login_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        // 최초에 전화번호 인증 한번

        // 아이디 값 찾기
        phoneNumber = (EditText) findViewById(R.id.user_phone_number );
        agencyCode = (EditText) findViewById(R.id.user_agencycode);
        deliveryCourse = (EditText) findViewById(R.id.delivery_number);
        login_button = (Button) findViewById(R.id.login_button);

        // 로그인 버튼 클릭
        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String request_phoneNumber = null;
                String request_agencyCode  = null;
                String request_deliveryCourse = null;

                request_phoneNumber = phoneNumber.getText().toString();
                request_agencyCode = agencyCode.getText().toString();
                request_deliveryCourse = deliveryCourse.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");

                            if ( success){
                                String userId = jsonObject.getString("userid");
                                String userPass = jsonObject.getString("passwd");

                                Toast.makeText(getApplicationContext(), "로그인 성공", Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(_LoginActivity.this, kr.co.delivery_v1.MainActivity.class);
                                intent.putExtra("userId", userId);
                                intent.putExtra("passwd", userPass);

                                startActivity(intent);

                            } else {//로그인 실패시
                                Toast.makeText( getApplicationContext(), "로그인 실패", Toast.LENGTH_SHORT ).show();
                                return;
                            }

                        }catch (Exception e){

                        }
                    }
                };
                LoginRequest loginRequest = new LoginRequest(request_phoneNumber, request_agencyCode, request_deliveryCourse, responseListener );
                RequestQueue queue = Volley.newRequestQueue( _LoginActivity.this );
                queue.add( loginRequest );
            }
        });
    }
}