package egovframework.example.cmmn;

import java.io.UnsupportedEncodingException;

/**
 * 
 * @FileName  : EncodingConverter.java
 * @Project   : kr.co.ds.api
 * @Date      : 2022. 11. 2.
 * @작성자     : shadow

 * @변경이력 		:
 * @프로그램 설명 : 대신택배 DB [US7ASCII] Convert.
 */
public class EncodingConverter {

	// US7ASCII > UTF-8
    public  String us7AsciiToUTF8(String str) {
        try {
            if(str != null) {
                return new String(str.getBytes("8859_1"), "EUC_KR");
            }
        } catch(UnsupportedEncodingException e) {
            //throw new RuntimeException(Message.UNSUPPORTED_ENCODING);
        }
        return str;
    }
    // UTF-8 > US7ASCII 
    public  String utf8ToUS7ASCII(String str) {
        try {
            if(str != null) {
                return new String(str.getBytes("EUC_KR"), "8859_1");
            }
        } catch(UnsupportedEncodingException e) {
            //throw new RuntimeException(Message.UNSUPPORTED_ENCODING);
        }
        return str;
    }
}
