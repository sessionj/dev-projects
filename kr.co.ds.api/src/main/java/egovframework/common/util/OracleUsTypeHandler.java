package egovframework.common.util;

import com.ibatis.sqlmap.client.extensions.ParameterSetter;
import com.ibatis.sqlmap.client.extensions.ResultGetter;
import com.ibatis.sqlmap.client.extensions.TypeHandlerCallback;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @FileName  : OracleUsTypeHandler.java
 * @Project   : kr.co.ds.api
 * @Date      : 2022. 11. 8.
 * @작성자     : shadow

 * @변경이력 		:
 * @프로그램 설명 : Oracle US7ASCII 설정 [iBatis Type]
 */
public class OracleUsTypeHandler implements TypeHandlerCallback{
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	public Object getResult(ResultGetter getter) throws SQLException {
		String str = null;
		try {
			str = new String(getter.getString().getBytes("8859_1"), "x-windows-949");
		} catch (UnsupportedEncodingException e) {
			this.logger.debug("UnsupportedEncodingException : " + e.getMessage());
			str = getter.getString();
		} catch (Exception localException) {
		}
		return str;
	}

	public void setParameter(ParameterSetter setter, Object parameter) throws SQLException {
		
		String str = null;
		try {
			str = new String(((String) parameter).getBytes("EUC_KR"), "8859_1");
		} catch (UnsupportedEncodingException e) {
			this.logger.debug("UnsupportedEncodingException : " + e.getMessage());
			str = (String) parameter;
		} catch (Exception localException) {
		}
		setter.setString(str);
	}

	public Object valueOf(String s) {
		return s;
	}
}
