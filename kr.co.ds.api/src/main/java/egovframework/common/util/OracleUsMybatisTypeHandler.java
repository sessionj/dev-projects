package egovframework.common.util;

import java.io.UnsupportedEncodingException;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @FileName  : OracleUsMybatisTypeHandler.java
 * @Project   : kr.co.ds.api
 * @Date      : 2022. 11. 8.
 * @작성자     : shadow

 * @변경이력 		: 현재 사용중.2022.12.07
 * @프로그램 설명 : Oracle US7ASCII 설정 [MyBatis Type]
 */
@MappedJdbcTypes(JdbcType.VARCHAR)
public class OracleUsMybatisTypeHandler extends BaseTypeHandler<String> {
	
	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public void setNonNullParameter(PreparedStatement ps, int i, String parameter, JdbcType jdbcType)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getNullableResult(ResultSet rs, String columnName) throws SQLException {
		// TODO Auto-generated method stub
		String str = null;
		try {
			str = new String(rs.getString(columnName).getBytes("iso-8859-1"), "euc-kr");
		} catch (UnsupportedEncodingException e) {
			this.logger.debug("UnsupportedEncodingException : " + e.getMessage());
			str = rs.getString(columnName).toString();
		} catch (Exception localException) {
		}
		return str;
	}

	@Override
	public String getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
