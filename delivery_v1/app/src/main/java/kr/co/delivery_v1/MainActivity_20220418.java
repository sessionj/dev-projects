package kr.co.delivery_v1;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.util.List;

import kr.co.delivery_v1.db.AppDatabase;
import kr.co.delivery_v1.db.BasicProcessDao;
import kr.co.delivery_v1.login.LoginActivity;
import kr.co.delivery_v1.models.LoginModelView;

public class MainActivity_20220418 extends AppCompatActivity {

    private boolean loginAccess = false;
    private BasicProcessDao roomDao;
    private AppDatabase appDatabase;
    private List<LoginModelView> tmpArr;

    private LoginModelView loginModel;
    private String roomDb_phoneNumber;
    private String device_phoneNumber;
    int nCurrentPermission = 0;
    static final int PERMISSION_REQUEST = 0x0000001;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //OnCheckPermission();
        // 초기화 및 체크 사항 여부 확인
        //init();

    }

    /**
     *READ_PHONE_NUMBERS
     */
    private void OnCheckPermission() {
        String phoneNum = "";
        TelephonyManager telephonyManager = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED
            || ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED
            || ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_NUMBERS) != PackageManager.PERMISSION_GRANTED
            ){
            if ( ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_PHONE_NUMBERS)){
                Toast.makeText(this, "앱 실행을 위해서는 권한을 설정해야 합니다.", Toast.LENGTH_LONG).show();
                //ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},PERMISSION_REQUEST);
                ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.READ_PHONE_NUMBERS, Manifest.permission.READ_PHONE_NUMBERS},PERMISSION_REQUEST);

            }else{
                //ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},PERMISSION_REQUEST);
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_NUMBERS, Manifest.permission.READ_PHONE_NUMBERS},PERMISSION_REQUEST);
            }
        }
        String tmpPhoneNumber = telephonyManager.getLine1Number();
        device_phoneNumber = tmpPhoneNumber.replace("+82", "0");
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode){
            case PERMISSION_REQUEST:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(this, "앱 실행을 위한 권한이 설정 되었습니다.", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(this,"앱 실행을 위한 권한이 취소 되었습니다.", Toast.LENGTH_LONG).show();
                }
                break;
        }
        //super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void init(){

        Intent intent = getIntent();
        appDatabase = AppDatabase.getInstance(this);
        // room 전화번호와 휴대폰 번호가 일치하지 않으면 LoginActivity 로 변경
        //Log.d("roomdb phone number : ", "" + getRoomDBPhoneNum());
        //Log.d("device phone number : ", "" + DeviceInfoUtil.getPhoneNum(this));

        if ( !roomDb_phoneNumber.equals(device_phoneNumber)){
            intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }

        /*if ( !getRoomDBPhoneNum().equals(DeviceInfoUtil.getPhoneNum(this))){
            intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }*/
    }

    /**
     * room db phone Number 가져오기
     * @return
     */
    private void getRoomDBPhoneNum(){
        tmpArr = appDatabase.basicProcessDao().getAll();

        if ( tmpArr != null && tmpArr.size() > 0){
            for ( int i=0; i < tmpArr.size(); i ++){
                loginModel = tmpArr.get(i);
                roomDb_phoneNumber = loginModel.getPhonenumber();
                Log.d("tmpArr room db getPhonenumber : ","" + loginModel.getPhonenumber());
                Log.d("tmpArr room db getAgencycode : ", "" + loginModel.getAgencycode());
                Log.d("tmpArr room db getDeliverycourse : ", "" + loginModel.getDeliverycourse());
                Log.d("tmpArr room db getId : ", "" + loginModel.getId());
            }
        }
    }
}

/**
 * loginAccess = intent.getBooleanExtra("loginAuth", false);
 *         if ( !loginAccess){
 *             intent = new Intent(this, LoginActivity.class);
 *             startActivity(intent);
 *         }
 *
 *         Log.d("========== ", appDatabase.basicProcessDao().getAll().toString());
 */