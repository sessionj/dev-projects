package kr.co.delivery_v1.comm;

import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

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

}
