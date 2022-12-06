package egovframework.api.entity;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
/**
 * @FileName  : CarCodeEntity.java
 * @Project   : kr.co.ds.api
 * @Date      : 2022. 10. 27.
 * @작성자     : shadow

 * @변경이력 		:
 * @프로그램 설명 : 코드관리 Entity [차량코드]
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CarCodeEntity implements Serializable{

	private static final long serialVersionUID = 958838578081269355L;
	
	private String carno = "";
	private String agencycode = "";
	private String cargubun = "";
	private String carnumber = "";
	private String fixcheckingday = "";
	private String weight = "";
	private String startpoint = "";
	private String endpoint = "";
	private String cargroup = "";
	private String orderno = "";
	private String driver = "";
	private String drivertel = "";
	private String admin = "";
	private String admintel = "";
	private String inputdate = "";
	private String chuk = "";
	private String jaejosa = "";
	private String jukjaeham = "";
	private String wesutak = "";
	private String gubun = "";
	private String linecode = "";
	
	public String getCarno() {
		return carno;
	}
	public void setCarno(String carno) {
		this.carno = carno;
	}
	public String getAgencycode() {
		return agencycode;
	}
	public void setAgencycode(String agencycode) {
		this.agencycode = agencycode;
	}
	public String getCargubun() {
		return cargubun;
	}
	public void setCargubun(String cargubun) {
		this.cargubun = cargubun;
	}
	public String getCarnumber() {
		return carnumber;
	}
	public void setCarnumber(String carnumber) {
		this.carnumber = carnumber;
	}
	public String getFixcheckingday() {
		return fixcheckingday;
	}
	public void setFixcheckingday(String fixcheckingday) {
		this.fixcheckingday = fixcheckingday;
	}
	public String getWeight() {
		return weight;
	}
	public void setWeight(String weight) {
		this.weight = weight;
	}
	public String getStartpoint() {
		return startpoint;
	}
	public void setStartpoint(String startpoint) {
		this.startpoint = startpoint;
	}
	public String getEndpoint() {
		return endpoint;
	}
	public void setEndpoint(String endpoint) {
		this.endpoint = endpoint;
	}
	public String getCargroup() {
		return cargroup;
	}
	public void setCargroup(String cargroup) {
		this.cargroup = cargroup;
	}
	public String getOrderno() {
		return orderno;
	}
	public void setOrderno(String orderno) {
		this.orderno = orderno;
	}
	public String getDriver() {
		return driver;
	}
	public void setDriver(String driver) {
		this.driver = driver;
	}
	public String getDrivertel() {
		return drivertel;
	}
	public void setDrivertel(String drivertel) {
		this.drivertel = drivertel;
	}
	public String getAdmin() {
		return admin;
	}
	public void setAdmin(String admin) {
		this.admin = admin;
	}
	public String getAdmintel() {
		return admintel;
	}
	public void setAdmintel(String admintel) {
		this.admintel = admintel;
	}
	public String getInputdate() {
		return inputdate;
	}
	public void setInputdate(String inputdate) {
		this.inputdate = inputdate;
	}
	public String getChuk() {
		return chuk;
	}
	public void setChuk(String chuk) {
		this.chuk = chuk;
	}
	public String getJaejosa() {
		return jaejosa;
	}
	public void setJaejosa(String jaejosa) {
		this.jaejosa = jaejosa;
	}
	public String getJukjaeham() {
		return jukjaeham;
	}
	public void setJukjaeham(String jukjaeham) {
		this.jukjaeham = jukjaeham;
	}
	public String getWesutak() {
		return wesutak;
	}
	public void setWesutak(String wesutak) {
		this.wesutak = wesutak;
	}
	public String getGubun() {
		return gubun;
	}
	public void setGubun(String gubun) {
		this.gubun = gubun;
	}
	public String getLinecode() {
		return linecode;
	}
	public void setLinecode(String linecode) {
		this.linecode = linecode;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString();
	}
}
