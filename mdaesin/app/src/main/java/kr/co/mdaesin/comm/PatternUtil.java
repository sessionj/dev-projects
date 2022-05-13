package kr.co.mdaesin.comm;

import android.util.Log;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PatternUtil {
    // 숫자 검사기
    public static boolean isNumeric(String str) {
        return Pattern.matches("^[0-9]*$", str);
    }

    // 영어 검사기
    public static boolean isAlpha(String str) {
        return Pattern.matches("^[a-zA-Z]*$", str);
    }

    // 한국어 혹은 영어 검사기
    public static boolean isAlphaNumeric(String str) {
        return Pattern.matches("[a-zA-Z0-9]*$", str);
    }

    // 한국어 검사기
    public static boolean isKorean(String str) {
        return Pattern.matches("[가-힣]*$", str);
    }

    // 대문자 검사기
    public static boolean isUpper(String str) {
        return Pattern.matches("^[A-Z]*$", str);
    }

    // 소문자 검사기
    public static boolean isDowner(String str) {
        return Pattern.matches("^[a-z]*$", str);
    }

    // 이메일 검사기
    public static boolean isEmail(String str) {
        return Pattern.matches("^[a-z0-9A-Z._-]*@[a-z0-9A-Z]*.[a-zA-Z.]*$", str);
    }

    // URL 검사기 (Http, Https 제외)
    public static boolean isURL(String str) {
        return Pattern.matches("^[^((http(s?))\\:\\/\\/)]([0-9a-zA-Z\\-]+\\.)+[a-zA-Z]{2,6}(\\:[0-9]+)?(\\/\\S*)?$",
                str);
    }



    // phone number checker
// xxx-xxx-xxxx (형식만 비교)
// - 은 없어야 함.
    public static boolean isMob(String str) {
        return Pattern.matches("^\\d{2,3}\\d{3,4}\\d{4}$", str);
    }

    // 일반 전화 검사기
    public static boolean isPhone(String str) {
        return Pattern.matches("^\\d{2,3}\\d{3,4}\\d{4}$", str);
    }

    // IP 검사기
    public static boolean isIp(String str) {
        return Pattern.matches("([0-9]{1,3})\\.([0-9]{1,3})\\.([0-9]{1,3})\\.([0-9]{1,3})", str);
    }

    // 우편번호 검사기
    public static boolean isPost(String str) {
        return Pattern.matches("^\\d{3}\\d{2}$", str);
    }

    // 주민등록번호 검사기
    public static boolean isPersonalID(String str) {
        return Pattern.matches("^(?:[0-9]{2}(?:0[1-9]|1[0-2])(?:0[1-9]|[1,2][0-9]|3[0,1]))-[1-4][0-9]{6}$", str);
    }

    public static boolean isValidCellPhoneNumber(String cellphoneNumber) {

        boolean returnValue = false;
        Log.i("cell", cellphoneNumber);
        String regex = "^\\s*(010|011|012|013|014|015|016|017|018|019)(-|\\)|\\s)*(\\d{3,4})(-|\\s)*(\\d{4})\\s*$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(cellphoneNumber);
        if (m.matches()) {
            returnValue = true;
        }
        return returnValue;

    }

}
