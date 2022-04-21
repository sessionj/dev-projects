package kr.co.delivery_v1.comm;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.RequiresApi;

import java.io.IOException;
import java.lang.reflect.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class BasicUtils {

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
            System.out.println(date + "는 " + week[w] +"요일 입니다");
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
        String beforeDate = new java.text.SimpleDateFormat(format).format(day.getTime());
        return beforeDate;
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
            addr = coder.getFromLocationName(address, 5);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }// 몇개 까지의 주소를 원하는지 지정 1~5개 정도가 적당
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

}
