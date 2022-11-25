package egovframework.api.entity;

import java.io.UnsupportedEncodingException;

/**
 * @FileName  : LanguageConversionUtility.java
 * @Project   : kr.co.ds.api
 * @Date      : 2022. 11. 7.
 * @작성자     : shadow

 * @변경이력 		:
 * @프로그램 설명 : 전자정부프레임워크 오라클 US7ASCII 한글 깨짐 대처 유틸 (사용 안함)
 */
public class EncodingConverter {

	public static String getLanguageConvert(String str) throws UnsupportedEncodingException {
		
		if ( str == null || str.equals("")) {
			return null;
		}
		return new String(str.getBytes("iso-8859-1"), "euc-kr");
	}
	
}

/**
 * 
 System.out.println("iso-8859-1 -> euc-kr        : " + new String(word.getBytes("iso-8859-1"), "euc-kr"));
 System.out.println("iso-8859-1 -> ksc5601       : " + new String(word.getBytes("iso-8859-1"), "ksc5601"));
 System.out.println("iso-8859-1 -> x-windows-949 : " + new String(word.getBytes("iso-8859-1"), "x-windows-949"));
 */
