package egovframework.api.entity;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
/**
 * @FileName  : EgovApiDefaultEntity.java
 * @Project   : kr.co.ds.api
 * @Date      : 2022. 10. 27.
 * @작성자     : shadow

 * @변경이력 		:
 * @프로그램 설명 : 운송 스캔
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ScanEntity implements Serializable{

	private static final long serialVersionUID = 958838578081269359L;
	
	private String billNo = "";
	private String subbillNo = "";
	private String addqty = "";
	private String workFlag = "";
	private String worker = "";
	private String scanDay = "";
	private String scanTime = "";
	private String scanStat = "";
	private String scanChasu = "";
	private String scanEvent = "";
	private String agencyCode = "";
	private String send_yn = "";
	private String rslt_yn = "";
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString();
	}
	public String getBillNo() {
		return billNo;
	}
	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}
	public String getSubbillNo() {
		return subbillNo;
	}
	public void setSubbillNo(String subbillNo) {
		this.subbillNo = subbillNo;
	}
	public String getAddqty() {
		return addqty;
	}
	public void setAddqty(String addqty) {
		this.addqty = addqty;
	}
	public String getWorkFlag() {
		return workFlag;
	}
	public void setWorkFlag(String workFlag) {
		this.workFlag = workFlag;
	}
	public String getWorker() {
		return worker;
	}
	public void setWorker(String worker) {
		this.worker = worker;
	}
	public String getScanDay() {
		return scanDay;
	}
	public void setScanDay(String scanDay) {
		this.scanDay = scanDay;
	}
	public String getScanTime() {
		return scanTime;
	}
	public void setScanTime(String scanTime) {
		this.scanTime = scanTime;
	}
	public String getScanStat() {
		return scanStat;
	}
	public void setScanStat(String scanStat) {
		this.scanStat = scanStat;
	}
	public String getScanChasu() {
		return scanChasu;
	}
	public void setScanChasu(String scanChasu) {
		this.scanChasu = scanChasu;
	}
	public String getScanEvent() {
		return scanEvent;
	}
	public void setScanEvent(String scanEvent) {
		this.scanEvent = scanEvent;
	}
	public String getAgencyCode() {
		return agencyCode;
	}
	public void setAgencyCode(String agencyCode) {
		this.agencyCode = agencyCode;
	}
	public String getSend_yn() {
		return send_yn;
	}
	public void setSend_yn(String send_yn) {
		this.send_yn = send_yn;
	}
	public String getRslt_yn() {
		return rslt_yn;
	}
	public void setRslt_yn(String rslt_yn) {
		this.rslt_yn = rslt_yn;
	}

	
}
