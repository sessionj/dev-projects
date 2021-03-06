package kr.co.ds.comm;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.text.TextUtils;
import android.util.Log;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

public class BasicUtils {

    private static final String TAG = "BasicUtils ";

    /**
     * format 변경
     * @param various
     * @return
     */
    public static String getFormatDate(int various){
        if ( various > 0 && various < 10){
            return "0"+String.valueOf(various);
        }else{
            return String.valueOf(various);
        }
    }

    /**
     * 일자별 요일 가져오기
     * @param date
     * @param dateType
     * @return
     */
    public static String getDateDay(String date, String dateType) throws ParseException {
        String day = "" ;
        SimpleDateFormat dateFormat = new SimpleDateFormat(dateType) ;
        Date nDate = dateFormat.parse(date) ;
        Calendar cal = Calendar.getInstance() ;
        cal.setTime(nDate);
        int dayNum = cal.get(Calendar.DAY_OF_WEEK) ;
        switch(dayNum){
            case 1: day = "일"; break ;
            case 2: day = "월"; break ;
            case 3: day = "화"; break ;
            case 4: day = "수"; break ;
            case 5: day = "목"; break ;
            case 6: day = "금"; break ;
            case 7: day = "토"; break ;
        }
        return day ;
    }

    /**
     * 요일 가져오기2
     * @param date
     * @param format
     */
    public static String getDayOfweek(String date, String format){

        Log.d("data :", date);
        Log.d("format :", format);
        SimpleDateFormat formats = new SimpleDateFormat(format);
        String[] week = {"일","월","화","수","목","금","토"};
        Calendar cal = Calendar.getInstance();
        Date getDate;
        String result = "";
        try {
            getDate = formats.parse(date);
            cal.setTime(getDate);
            int w = cal.get(Calendar.DAY_OF_WEEK)-1;
            result = week[w];
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     *
     * @param format
     * @return
     */
    public static String getDays(String format){
        long now = System.currentTimeMillis();
        Date date =new Date(now);
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        String getTime = sdf.format(date);
        return getTime;
    }

    /**
     * 어제
     * @param format
     * @return
     */
    public static String getYesterday(String format){
        Calendar day = Calendar.getInstance();
        day.add(Calendar.DATE , -1);
        String beforeDate = new SimpleDateFormat(format).format(day.getTime());
        return beforeDate;
    }

    public static String getToDayDays(){
        String resultDay = "";
        try {
            resultDay = getDays(Label.DELIVERY_STANDARD_DATE_FORMAT)+" ["+getDayOfweek( getDays(Label.DELIVERY_STANDARD_DATE_FORMAT), Label.DELIVERY_STANDARD_DATE_FORMAT  )+"]";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultDay;
    }



    /**
     *
     * @param viewDate
     * @param format
     * @return
     */
    public static String getDataFormatConvert(String viewDate, String format){
        String result = "";
        String tmpWeek = "";

        if (TextUtils.isEmpty(viewDate)){
            return "";
        }else{
            if ( viewDate.length() == 8){
                tmpWeek =  getDayOfweek(viewDate.substring(0,4) + "-" + viewDate.substring(4,6) + "-" + viewDate.substring(6,8), Label.DELIVERY_STANDARD_DATE_FORMAT);
                result = viewDate.substring(0,4) + "년 " + viewDate.substring(4,6) + "월 " + viewDate.substring(6,8)+ "일 ("+tmpWeek+")";
            }
        }
        return result;
    }

    /**
     * 기준일 어제일자
     * @param
     * @return
     */
    public static String getDateControl(String strDate, int year, int month, int day) {

        SimpleDateFormat dtFormat = new SimpleDateFormat(Label.DELIVERY_STANDARD_DATE_FORMAT);
        Calendar cal = Calendar.getInstance();
        try {
            Date dt = dtFormat.parse(strDate);
            cal.setTime(dt); cal.add(Calendar.YEAR, year);
            cal.add(Calendar.MONTH, month);
            cal.add(Calendar.DATE, day);
        }catch (Exception e){
            e.printStackTrace();
        }
        return dtFormat.format(cal.getTime());
    }

    /**
     *
     * @param objnum
     * @return
     */
    public static String getDataFormatConvert(String objnum){
        String result = "";
        String tmpWeek = "";

        if (TextUtils.isEmpty(objnum)){
            return "";
        }else{
            if ( objnum.length() == 13){
                result =  objnum.substring(0,4) + "-" + objnum.substring(4,7) + "-" + objnum.substring(7,13);
            }
        }
        return result;
    }

    /**
     * 주소로 위도, 경도 구하기
     * @param mcontext
     * @param address
     * @return
     */
    public static ArrayList<Double> findGeoPoint(Context mcontext, String address) {

        if ( TextUtils.isEmpty(address)){
            return null;
        }

        Geocoder coder = new Geocoder(mcontext);
        List<Address> addr = null;// 한좌표에 대해 두개이상의 이름이 존재할수있기에 주소배열을 리턴받기 위해 설정
        ArrayList<Double> result = null;
        double lat;
        double lon;
        try {
            // 몇개 까지의 주소를 원하는지 지정 1~5개 정도가 적당
            addr = coder.getFromLocationName(address, 5);
            if (addr != null) {
                if ( addr.get(0) == null){
                    return null;
                }
                Address lating = addr.get(0);
                lat = lating.getLatitude(); // 위도가져오기
                lon = lating.getLongitude(); // 경도가져오기
                result = new ArrayList<Double>();
                result.add(lat);
                result.add(lon);
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return result;
    }

    /**
     *
     * @param mcontext
     * @param address
     * @return
     */
    public static Location findGeoPoint_(Context mcontext, String address) {
        Location loc = new Location("");
        Geocoder coder = new Geocoder(mcontext);
        List<Address> addr = null;// 한좌표에 대해 두개이상의 이름이 존재할수있기에 주소배열을 리턴받기 위해 설정

        try {
            addr = coder.getFromLocationName(address, 5);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }// 몇개 까지의 주소를 원하는지 지정 1~5개 정도가 적당
        if (addr != null) {
            for (int i = 0; i < addr.size(); i++) {
                Address lating = addr.get(i);
                double lat = lating.getLatitude(); // 위도가져오기
                double lon = lating.getLongitude(); // 경도가져오기
                loc.setLatitude(lat);
                loc.setLongitude(lon);
                Log.d("위도 : ", lat + "");
                Log.d("위도 : " , lon + "");
            }
        }
        return loc;
    }

    /**
     *
     * @param object
     * @return
     */
    public static boolean isNullCheck(Object object){
        if ( object == null){
            return true;
        }
        else if (object.equals("")){
            return true;
        }
        else if(object.toString().length()==0){
            return true;
        }
        return false;
    }

    /**
     *
     * @param targetStr
     * @param maxByte
     * @param endCut
     * @return
     */
    public static String getStrCut(String targetStr, int maxByte, boolean endCut){

        if ( !TextUtils.isEmpty(targetStr)){

            targetStr = strReverse(targetStr);
            Log.d("1 strReverse : ", targetStr);
            int buffer = 0;
            int idx = 0;
            while (true){
                int unicode = targetStr.charAt(idx);
                Log.d("2 unicode : ",""+ unicode);
                buffer += unicode > 127 ? 2 : 1;
                if (buffer > maxByte) break;
                idx ++;
            }
            if (endCut){
                return strReverse( targetStr.substring(0,idx));
            }else{
                return strReverse(targetStr.substring(targetStr.length()-idx));
            }
        }
        return "";
    }

    private static String strReverse(String targetStr){
        String reverse = "";
        for(int i=0; i < targetStr.length(); i++){
            reverse = targetStr.split("")[i]+reverse;
        }
        return reverse;
    }

    public static String addComma(Double str){
        String tmpStr = "";
        if ( !TextUtils.isEmpty(String.valueOf(str) )){
            tmpStr = String.valueOf(str);
            return tmpStr.replaceAll("\\B(?=(\\d{3})+(?!\\d))", ",");
        }
        return "";
    }
    public static String addComma(long str){
        String tmpStr = "";
        if ( !TextUtils.isEmpty(String.valueOf(str) )){
            tmpStr = String.valueOf(str);
            return tmpStr.replaceAll("\\B(?=(\\d{3})+(?!\\d))", ",");
        }
        return "";
    }
    public static String addComma(int str){
        String tmpStr = "";
        if ( !TextUtils.isEmpty(String.valueOf(str) )){
            tmpStr = String.valueOf(str);
            return tmpStr.replaceAll("\\B(?=(\\d{3})+(?!\\d))", ",");
        }
        return "";
    }

    public static String addStringComma(String str){
        if ( !TextUtils.isEmpty(str)){
            return str.replaceAll("\\B(?=(\\d{3})+(?!\\d))", ",");
        }
        return "";
    }

    /********************************************************************************
     * 운송장 상태 체크
     */

    public static String billStatus(String statusCode, String transCode){
        if ( !TextUtils.isEmpty(statusCode)){
            if ( statusCode.equals("42")){
                if ( statusCode.equals("001")){
                    return "출고대기(도착)";
                }else{
                    return "배송준비중(도착)";
                }
            }else if( statusCode.equals("44")){
                if ( statusCode.equals("001")){
                    return "출고완료";
                }else{
                    return "배송완료";
                }

            }else{
                return "운송중";
            }
        }
        return "";
    }

    /**
     * 운송장번호, 전화번호 분리,하이픈 넣기 등
     * @param targetStr
     * @param mode
     * @return
     */
    public static String stringSplit(String targetStr, int mode){

        String result = "";
        switch (mode){
            // 운송장 번호
            case 1:
                result =  targetStr.substring(0, 4) +"-"+targetStr.substring(4, 7)+"-"+targetStr.substring(7, 13);
                break;
            case 2:
                result = convertTelNo(targetStr);
                break;
            default:
                result = "";
                break;
        }
        return result;
    }

    /**
     * 전화번호 하이픈
     * @param src
     * @return
     */
    public static String convertTelNo(String src) {

        String mobTelNo = src;
        if (src.length() < 10){
            return src;
        }
        if (mobTelNo != null) {
            // 일단 기존 - 전부 제거
            mobTelNo = mobTelNo.replaceAll(Pattern.quote("-"), "");
            if (mobTelNo.length() == 11) {
                // 010-1234-1234
                mobTelNo = mobTelNo.substring(0, 3) + "-" + mobTelNo.substring(3, 7) + "-" + mobTelNo.substring(7);
            } else if (mobTelNo.length() == 8) {
                // 1588-1234
                mobTelNo = mobTelNo.substring(0, 4) + "-" + mobTelNo.substring(4);
            } else {
                if (mobTelNo.startsWith("02")) { // 서울은 02-123-1234
                    mobTelNo = mobTelNo.substring(0, 2) + "-" + mobTelNo.substring(2, 5) + "-" + mobTelNo.substring(5);
                } else { // 그외는 012-123-1345
                    mobTelNo = mobTelNo.substring(0, 3) + "-" + mobTelNo.substring(3, 6) + "-" + mobTelNo.substring(6);
                }
            }
        }
        return mobTelNo;
    }
}