package kr.co.ds.comm;

public class Label {

    public final static String DELIVERY_BASE_URL                            = "http://dev.ds3211.co.kr/";
    public final static String DELIVERY_BASE_URL_LOGIN                      = "DsService_AppLoginProxy?";
    public final static String DELIVERY_BASE_URL_SUB_1                      = "DsService_AppInterlockProxy?";
    public final static String DELIVERY_BASE_URL_SUB_TRACKING               = "DsService_TrackingProxy?";


    public final static String DELIVERY_BASE_URL_DELIVERY_LIST              = "DL";
    public final static String DELIVERY_BASE_URL_DELIVERY_SUMMARY           = "DA";


    public final static String DELIVERY_BASE_URL_RECEIPT_LOGIN_CHECK        = "LOC";
    public final static String DELIVERY_BASE_URL_RECEIPT_LOGIN_LAST         = "LOR";

    public final static String DELIVERY_BASE_URL_MOBILE_LOGIN_GETKEY        = "MAK";
    public final static String DELIVERY_BASE_URL_MOBILE_LOGIN_CERTIFICATION  = "MAC";

    public final static String DELIVERY_BASE_URL_TRACKING_LIST              = "TRACKING";

    public final static String DELIVERY_BASE_URL_RECEIPT_LIST               = "RL";
    public final static String DELIVERY_BASE_URL_RECEIPT_DETAILS            = "RD";
    public final static String DELIVERY_BASE_URL_RECEIPT_DETAILS_UNSONG     = "RU";
    public final static String DELIVERY_BASE_URL_RECEIPT_HISTORY            = "RH";
    public final static String DELIVERY_BASE_URL_RECEIPT_WAYPOINT           = "RW";
    public final static String DELIVERY_BASE_URL_RECEIPT_WAYPOINT_DETAILS   = "RWD";

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
    public final static String RECEIPT_CONSTRUCTOR_FINAL_TRACKING           = "배송추적";
    public final static String RECEIPT_CONSTRUCTOR_FINAL_DAESIN             = "대신택배";

    public final static String RECEIPT_CONSTRUCTOR_BILL_STATUS1             = "운송중";
    public final static String RECEIPT_CONSTRUCTOR_BILL_STATUS2             = "배송중(도착)";
    public final static String RECEIPT_CONSTRUCTOR_BILL_STATUS3             = "배송완료";

    public final static String RECEIPT_CONSTRUCTOR_FINAL_MAINNAME           = "通";
    public final static String RECEIPT_DEFAULT_LINECODE                     = "000000";

    public final static String RECEIPT_HIST_BILLNO                          = "운송장번호 : ";
    public final static String RECEIPT_HIST_AGENCY                          = "수정영업소 : ";
    public final static String RECEIPT_HIST_DATE                            = "변경일자 : ";
    public final static String RECEIPT_HIST_CATEGORY                        = "변동항목 : ";
    public final static String RECEIPT_HIST_CONTENT                         = "변동내역 : ";

    public final static String RECEIPT_CAR_API_CALL                         = "https://s1.u-vis.com/uvisc/SSOAction.do?method=GetAccessKeyWithValues&SerialKey=S1610-3393-F997--545&VIEW_TYPE=1&CAR_NUMBER=";
    public final static String RECEIPT_CAR_MONITOR                          = "https://s1.u-vis.com/uvisc/SSOAction.do?method=viewGroupMap&AccessKey=";

    public final static String RECEIPT_CUSTOM_CALL                          = "http://custom.ds3211.co.kr/vcSvl?apiKey=58d01815eb9b10a79ce08e6d08a6a63f&carNumber=";


    public final static String MESSAGE_ERROR                                = "오류";
    public final static String MESSAGE_ERROR_01                             = "인증번호 오류! 다시시도해주세요";

    public final static String MESSAGE_TITLE                                = "메세지 발송";


    public final static String MESSAGE_01                                   = "노선명을 입력후 다시 시도해주세요";
    public final static String MESSAGE_INFO                                 = "안내";

    public final static String MESSAGE_SENDING_KEY                          = "인증번호 발송!";


    public final static String MESSAGE_CERT_PHONE_NUMBER                    = "전화번호가 인증되지 않았습니다";
    public final static String MESSAGE_NOT_PHONE_NUMBER                     = "전화번호를 입력후 시도해주세요";
    public final static String MESSAGE_DIFF_PHONE_NUMBER                    = "올바른 핸드폰 번호가 아닙니다";


    public final static String MESSAGE_NOT_AUTHENTICATIONKEY                = "인증키를 입력후 시도해주세요";
    public final static String SPINNER_SEQUENCE_0                           = "건의함 유형을 선택하세요";






}
