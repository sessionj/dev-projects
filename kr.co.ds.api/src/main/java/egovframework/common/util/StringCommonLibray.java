package egovframework.common.util;

public enum StringCommonLibray {

	CODE1("01", "마스터코드"),	// mastcode
	CODE2("02", "영업소코드"),	// agencyCode
	CODE3("03", "차량코드"),		// carlineinfo
	CODE4("04", "노선코드"),		// globallinecode
	CODE5("05", "Certification service : ");		// sms 문자 발신
	
	private String code;
	private String msg;
	
	private StringCommonLibray(String code, String msg) {
		this.code = code;
		this.msg = msg;
	}
	
	public String getMsg() {
		return this.msg;
	}
	public String getMsg(String column) {
		return column + this.msg;
	}
	public String getCode() {
		return this.code;
	}
	public String getCode(String column) {
		return this.code;
	}
	public String getErrorMsg(String code) {
		this.code = code;
		return getMsg();
	}
	
}
