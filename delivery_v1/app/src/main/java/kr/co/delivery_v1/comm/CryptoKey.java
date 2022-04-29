package kr.co.delivery_v1.comm;
import java.text.SimpleDateFormat;
import java.util.Date;
public class CryptoKey {

    public static String createCryptoKey(String src) {
        long li_key = 20000000;
        String ls_yyyymmdd = null;
        String ls_temp = null;
        String ls_src = null;
        String ls_from = null;

        ls_src = src;

        if( isNumber(ls_src) ){
            ls_yyyymmdd = getCurrentDate("yyyyMMdd");
            ls_temp = Long.toString( Long.valueOf(ls_yyyymmdd).longValue() - li_key - Long.valueOf(ls_src).longValue() );
            ls_from = ls_yyyymmdd.substring(4, 8) + ls_temp + ls_yyyymmdd;
        }

        return ls_from;
    }

    public static boolean isNumber(String str){
        try{
            Integer.parseInt(str);
            return true;
        }catch(Exception e){
            return false;
        }

    }

    public static String getCurrentDate(String format) {
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(d);
    }

    /**
     *
     * @param key
     * @param serialNumber
     * @return
     */
    public static boolean cryptoKey(String key, String serialNumber) {

        long li_key = 20000000;
        String ls_yyyymmdd = null;
        String ls_temp = null;
        String ls_src = null;
        String ls_from = null;

        ls_src = serialNumber == null ? "0" : serialNumber;

        if( isNumber(ls_src) ){
            ls_yyyymmdd = getCurrentDate("yyyyMMdd");
            ls_temp = Long.toString( Long.valueOf(ls_yyyymmdd).longValue() - li_key - Long.valueOf(ls_src).longValue() );
            ls_from = ls_yyyymmdd.substring(4, 8) + ls_temp + ls_yyyymmdd;

            if( ls_from != null && !"".equals(ls_from)){
                if( ls_from.equals(key)){
                    return true;
                }
            }
        }

        return false;
    }
}
