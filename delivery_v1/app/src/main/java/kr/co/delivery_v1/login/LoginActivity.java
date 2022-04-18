package kr.co.delivery_v1.login;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;
import java.util.List;
import kr.co.delivery_v1.R;
import kr.co.delivery_v1.db.AppDatabase;
import kr.co.delivery_v1.db.BasicProcessDao;

import kr.co.delivery_v1.models.LoginModelView;

public class LoginActivity extends AppCompatActivity {

    private EditText phoneNumber, agencyCode, deliveryCourse;
    private Button login_button;
    private LoginModelView loginModel;
    private BasicProcessDao basicProcessDao;
    private AppDatabase appDatabase;

    private List<LoginModelView> tmpArray ;
    private LoginModelView tmpEntity ;

    /**
     * 액션바 제거
     * room DB 연결
     * room DB 데이터 제거
     */
    private void init(){
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        appDatabase = AppDatabase.getInstance(this);
        appDatabase.basicProcessDao().applicationData_deleteAll();
        Log.d("========== ", appDatabase.basicProcessDao().getAll().toString());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        init();
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

                loginModel = new LoginModelView();

                String request_phoneNumber = null;
                String request_agencyCode  = null;
                String request_deliveryCourse = null;

                request_phoneNumber = phoneNumber.getText().toString();
                request_agencyCode = agencyCode.getText().toString();
                request_deliveryCourse = deliveryCourse.getText().toString();

                loginModel.setPhonenumber(request_phoneNumber);
                loginModel.setAgencycode(request_agencyCode);
                loginModel.setDeliverycourse(request_deliveryCourse);

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("", "===================> start" );
                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");
                            Log.d("", "===================> 요기는 ? ?? ? ?? ? " );
                            if ( success){
                                //String userId = jsonObject.getString("userid");
                                //String userPass = jsonObject.getString("passwd");
                                // room db에 저장한다.
                                // 전화번호, 현재 시간, 영업소코드, 배달코스
                                roomDbRequest(loginModel, response);

                                Toast.makeText(getApplicationContext(), "로그인 성공", Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(LoginActivity.this, kr.co.delivery_v1.MainActivity.class);
                                intent.putExtra("loginAuth", success);
                                //intent.putExtra("passwd", userPass);

                                startActivity(intent);

                            } else {//로그인 실패시
                                Toast.makeText( getApplicationContext(), "로그인 실패", Toast.LENGTH_SHORT ).show();
                                return;
                            }

                        }catch (Exception e){
                            Log.d("log ", e.toString());
                        }
                    }

                    /**
                     * roomdb insert
                     * @param loginEntity
                     * @param response
                     */
                    private void roomDbRequest(LoginModelView loginEntity, String response) {

                        appDatabase.basicProcessDao().applicationData_insert(loginEntity);

                        //tmpArray = database.basicProcessDao().getAll();

                        //Log.d("arr", tmpArray.get(0).getId() + "" );
                        //Log.d("arr", tmpArray.get(0).getPhonenumber());
                        //Log.d("arr", tmpArray.get(0).getAgencycode());
                        //Log.d("arr", tmpArray.get(0).getDeliverycourse());
                        /*roomDao = new MainDao() {
                            @Override
                            public void insert(LoginEntity loginEntity) {
                                database.mainDao().insert(loginEntity);

                            }

                            @Override
                            public ArrayList<LoginEntity> getAll() {

                                tmpEntity = new LoginEntity();
                                tmpArray = new ArrayList<LoginEntity>();
                                tmpArray = database.mainDao().getAll();

                                Log.d("arr", tmpArray.get(0).getId() + "" );
                                Log.d("arr", tmpArray.get(0).getPhonenumber());
                                Log.d("arr", tmpArray.get(0).getAgencycode());
                                Log.d("arr", tmpArray.get(0).getDeliverycourse());

                                  return null;
                            }
                        };*/
                    }
                };
                LoginRequest loginRequest = new LoginRequest(request_phoneNumber, request_agencyCode, request_deliveryCourse, responseListener );
                RequestQueue queue = Volley.newRequestQueue( LoginActivity.this );
                queue.add( loginRequest );
            }

        });
    }
}