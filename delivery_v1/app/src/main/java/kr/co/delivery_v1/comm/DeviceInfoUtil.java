package kr.co.delivery_v1.comm;

import static androidx.core.app.ActivityCompat.requestPermissions;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;

import androidx.core.app.ActivityCompat;

import java.util.ArrayList;
import java.util.List;

import kr.co.delivery_v1.db.AppDatabase;
import kr.co.delivery_v1.models.DeliveryModelView;
import kr.co.delivery_v1.models.LoginModelView;

public class DeviceInfoUtil {

    private static AppDatabase appDatabase;

    /**
     * device id 가져오기
     * @param context
     * @return
     */
    public static String getDeviceId(Context context) {
        return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    /**
     * device 제조사 가져오기
     * @return
     */
    public static String getManufacturer() {
        return Build.MANUFACTURER;
    }

    /**
     * device 브랜드 가져오기
     * @return
     */
    public static String getDeviceBrand() {
        return Build.BRAND;
    }

    /**
     * device 모델명 가져오기
     * @return
     */
    public static String getDeviceModel() {
        return Build.MODEL;
    }

    /**
     * device Android OS 버전 가져오기
     * @return
     */
    public static String getDeviceOs() {
        return Build.VERSION.RELEASE;
    }

    /**
     * device SDK 버전 가져오기
     * @return
     */
    public static int getDeviceSdk() {
        return Build.VERSION.SDK_INT;
    }

    /**
     * device 전화번호 가져오기
     * @param context
     * @return
     */
    /*public static String getPhoneNum(Context context) {
        String phoneNum = "";
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        try{

            Log.d("check1 : ", "" + ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_SMS));
            Log.d("check1 : ", "" + ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_NUMBERS));
            Log.d("check1 : ", "" + ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE));

            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_NUMBERS) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            }else {
                checkPermission();
            }
            String tmpPhoneNumber = telephonyManager.getLine1Number();
            phoneNum = tmpPhoneNumber.replace("+82", "0");
        }catch (Exception e){
            phoneNum = "error";
            e.printStackTrace();
        }
        return phoneNum;
    }*/

    /*private static void checkPermission(){

    }*/
    /**
     * device 전화번호 가져오기
     * @param context
     * @return
     */
    @SuppressLint("MissingPermission")
    public static String getPhoneNum(Context context) {
        String phoneNum = "";
        TelephonyManager telManager = (TelephonyManager) context.getSystemService(context.TELEPHONY_SERVICE);
        phoneNum = telManager.getLine1Number().toString();
        if(phoneNum.startsWith("+82")) {
            phoneNum = phoneNum.replace("+82", "0");
        }
        return phoneNum;
    }

    /**
     * room 정보 (phone) 가져오기
     * @param context
     * @return
     */
    public static String getRoomSelecter(Context context, int type){
        appDatabase = AppDatabase.getInstance(context);
        List<LoginModelView> roomDbArr;
        LoginModelView loginModel;

        String roomDb_selecter = "";
        try{
            roomDbArr = appDatabase.basicProcessDao().getAll();

            if ( roomDbArr != null && roomDbArr.size() > 0){
                for ( int i=0; i < roomDbArr.size(); i ++){
                    loginModel = roomDbArr.get(i);
                    Log.d("tmpArr room db getPhonenumber : ","" + loginModel.getPhonenumber());
                    Log.d("tmpArr room db getAgencycode : ", "" + loginModel.getAgencycode());
                    Log.d("tmpArr room db getDeliverycourse : ", "" + loginModel.getDeliverycourse());
                    switch (type){
                        case 2 : roomDb_selecter = loginModel.getPhonenumber();     break;
                        case 3 : roomDb_selecter = loginModel.getAgencycode();      break;
                        case 4 : roomDb_selecter = loginModel.getDeliverycourse();  break;
                        default:
                            break;
                    }
                }
            }else{
                Log.d(" room DB 목록이 비었습니다.", "");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return roomDb_selecter;
    }

    public static ArrayList<DeliveryModelView> getRoomDeliveryList(Context context, DeliveryModelView deliveryModelView){

        appDatabase = AppDatabase.getInstance(context);

        return null;
    }

}
