package kr.co.mdaesin.comm;

public class Label {

    public final static String DELIVERY_BASE_URL                            = "http://dev.ds3211.co.kr/";
    public final static String DELIVERY_BASE_URL_LOGIN                      = "DsService_AppInterlockProxyLogin?";
    public final static String DELIVERY_BASE_URL_SUB_1                      = "DsService_AppInterlockProxy?";
    public final static String DELIVERY_BASE_URL_DELIVERY_LIST              = "DL";
    public final static String DELIVERY_BASE_URL_DELIVERY_SUMMARY           = "DA";

    public final static String DELIVERY_BASE_URL_RECEIPT_LIST               = "RL";
    public final static String DELIVERY_BASE_URL_RECEIPT_DETAILS            = "RD";
    public final static String DELIVERY_BASE_URL_RECEIPT_DETAILS_UNSONG     = "RU";

    public final static String DELIVERY_BASE_ROOM_PROFILE_DATABASE_NAME     = "tb_profile";
    public final static String DELIVERY_BASE_ROOM_DELIVERY_DATABASE_NAME    = "tb_delivery";
    public final static String DELIVERY_BASE_ROOM_DELIVERY_REQ_DATABASE_NAME= "tb_delivery_request";

    public final static Double DELIVERY_DEFAULT_POINT_LAT                   = 36.6489337;
    public final static Double DELIVERY_DEFAULT_POINT_LNG                   = 127.4869455;
    public final static String DELIVERY_DELIVERY_STATUS_N                   = "배달중";
    public final static String DELIVERY_DELIVERY_STATUS_Y                   = "배달완료";
    public final static String DELIVERY_STANDARD_DATE_FORMAT                = "yyyy-MM-dd";
    public final static String DELIVERY_STANDARD_DATE_FORMAT_TIME           = "yyyy-MM-dd HH:mm:ss";
    public final static String DELIVERY_DELIVERY_EMPTY_QUESTION             = "검색한 날짜에 자료가 없습니다. 자료수신항목으로 이동하시겠습니까?";
    public final static String DELIVERY_DELIVERY_ACT_MOVETO_REQEUST         = "ACTM_REQUEST";
    public final static String DELIVERY_DELIVERY_ACT_MOVETO_LOGIN           = "ACTM_LOGIN";
    public final static String DELIVERY_DELIVERY_PICTURE_PROVIDER           = ".fileprovider";
    public final static String DELIVERY_DELIVERY_PICTURE_DIR                = "getFilesDir";
    public final static String DELIVERY_DELIVERY_PICTURE_EXTENSION          = ".jpg";
    public final static String DELIVERY_DELIVERY_PICTURE_SAVE_OK            = "사진이 저장되었습니다.";
    public final static String DELIVERY_DELIVERY_PICTURE_SAVE_FAIL          = "사진 저장에 실패했습니다";
    public final static String DELIVERY_DELIVERY_PICTURE_FILE_CREATE_ERROR  = "파일 생성 에러!";

    public final static String RECEIPT_CONSTRUCTOR_FINAL_NAME               = "노선通";
    public final static String RECEIPT_DEFAULT_LINECODE                     = "000000";

}
