package kr.co.ds.comm;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SharedPreferenceConf {

    static final String PREF_KEY_PHONE = "phone";

    static SharedPreferences getSharedPreferences(Context ctx){
        return PreferenceManager.getDefaultSharedPreferences(ctx);
    }

    // 계정 정보 저장
    public static void setPhoneNumber(Context ctx, String phoneNumber) {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_KEY_PHONE, phoneNumber);
        editor.commit();
    }
    // 저장된 정보 가져오기 (전화번호)
    public static String getPhoneNumber(Context ctx) {
        return getSharedPreferences(ctx).getString(PREF_KEY_PHONE, "");
    }


    // 로그아웃
    public static void clearUserName(Context ctx) {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.clear();
        editor.commit();
    }
}
