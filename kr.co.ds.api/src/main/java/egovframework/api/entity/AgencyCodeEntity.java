package egovframework.api.entity;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
/**
 * @FileName  : EgovApiDefaultEntity.java
 * @Project   : kr.co.ds.api
 * @Date      : 2022. 10. 27.
 * @작성자     : shadow

 * @변경이력 		:
 * @프로그램 설명 : 코드관리 Entity [영업소코드]
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AgencyCodeEntity implements Serializable{

	private static final long serialVersionUID = 958838578081269356L;
	
	private String agencycode = "";
	private String agencyname = "";
	private String agencytel1 = "";
	private String agencytel2 = "";
	private String agencytel3 = "";
	private String agencyzipcode1 = "";
	private String agencyzipcode2 = "";
	private String agencyaddress  = "";
	private String location = "";
	private String agencygubun = "";
	private String deliveryagencycode = "";
	private String comyumu = "";
	private String sending_grade = "";
	private String arrival_grade = "";
	private String gibu_grade = "";
	private String com_grade = "";
	private String sendinglink_grade = "";
	private String arrivallink_grade = "";
	private String relay_grade = "";
	private String chulgounitcost = "";
	private String chulgounitcostrate = "";
	private String link_grade = "";
	private String area = "";
	private String openday = "";
	private String closeday = "";
	private String closegubun = "";
	private String arrivalgubun = "";
	private String balgroup = "";
	private String inputdate = "";
	private String seoulcode = "";
	private String gibangcode = "";
	private String terminalcode = "";
	
	public String getAgencycode() {
		return agencycode;
	}

	public void setAgencycode(String agencycode) {
		this.agencycode = agencycode;
	}

	public String getAgencyname() {
		return agencyname;
	}

	public void setAgencyname(String agencyname) {
		this.agencyname = agencyname;
	}

	public String getAgencytel1() {
		return agencytel1;
	}

	public void setAgencytel1(String agencytel1) {
		this.agencytel1 = agencytel1;
	}

	public String getAgencytel2() {
		return agencytel2;
	}

	public void setAgencytel2(String agencytel2) {
		this.agencytel2 = agencytel2;
	}

	public String getAgencytel3() {
		return agencytel3;
	}

	public void setAgencytel3(String agencytel3) {
		this.agencytel3 = agencytel3;
	}

	public String getAgencyzipcode1() {
		return agencyzipcode1;
	}

	public void setAgencyzipcode1(String agencyzipcode1) {
		this.agencyzipcode1 = agencyzipcode1;
	}

	public String getAgencyzipcode2() {
		return agencyzipcode2;
	}

	public void setAgencyzipcode2(String agencyzipcode2) {
		this.agencyzipcode2 = agencyzipcode2;
	}

	public String getAgencyaddress() {
		return agencyaddress;
	}

	public void setAgencyaddress(String agencyaddress) {
		this.agencyaddress = agencyaddress;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getAgencygubun() {
		return agencygubun;
	}

	public void setAgencygubun(String agencygubun) {
		this.agencygubun = agencygubun;
	}

	public String getDeliveryagencycode() {
		return deliveryagencycode;
	}

	public void setDeliveryagencycode(String deliveryagencycode) {
		this.deliveryagencycode = deliveryagencycode;
	}

	public String getComyumu() {
		return comyumu;
	}

	public void setComyumu(String comyumu) {
		this.comyumu = comyumu;
	}

	public String getSending_grade() {
		return sending_grade;
	}

	public void setSending_grade(String sending_grade) {
		this.sending_grade = sending_grade;
	}

	public String getArrival_grade() {
		return arrival_grade;
	}

	public void setArrival_grade(String arrival_grade) {
		this.arrival_grade = arrival_grade;
	}

	public String getGibu_grade() {
		return gibu_grade;
	}

	public void setGibu_grade(String gibu_grade) {
		this.gibu_grade = gibu_grade;
	}

	public String getCom_grade() {
		return com_grade;
	}

	public void setCom_grade(String com_grade) {
		this.com_grade = com_grade;
	}

	public String getSendinglink_grade() {
		return sendinglink_grade;
	}

	public void setSendinglink_grade(String sendinglink_grade) {
		this.sendinglink_grade = sendinglink_grade;
	}

	public String getArrivallink_grade() {
		return arrivallink_grade;
	}

	public void setArrivallink_grade(String arrivallink_grade) {
		this.arrivallink_grade = arrivallink_grade;
	}

	public String getRelay_grade() {
		return relay_grade;
	}

	public void setRelay_grade(String relay_grade) {
		this.relay_grade = relay_grade;
	}

	public String getChulgounitcost() {
		return chulgounitcost;
	}

	public void setChulgounitcost(String chulgounitcost) {
		this.chulgounitcost = chulgounitcost;
	}

	public String getChulgounitcostrate() {
		return chulgounitcostrate;
	}

	public void setChulgounitcostrate(String chulgounitcostrate) {
		this.chulgounitcostrate = chulgounitcostrate;
	}

	public String getLink_grade() {
		return link_grade;
	}

	public void setLink_grade(String link_grade) {
		this.link_grade = link_grade;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getOpenday() {
		return openday;
	}

	public void setOpenday(String openday) {
		this.openday = openday;
	}

	public String getCloseday() {
		return closeday;
	}

	public void setCloseday(String closeday) {
		this.closeday = closeday;
	}

	public String getClosegubun() {
		return closegubun;
	}

	public void setClosegubun(String closegubun) {
		this.closegubun = closegubun;
	}

	public String getArrivalgubun() {
		return arrivalgubun;
	}

	public void setArrivalgubun(String arrivalgubun) {
		this.arrivalgubun = arrivalgubun;
	}

	public String getBalgroup() {
		return balgroup;
	}

	public void setBalgroup(String balgroup) {
		this.balgroup = balgroup;
	}

	public String getInputdate() {
		return inputdate;
	}

	public void setInputdate(String inputdate) {
		this.inputdate = inputdate;
	}

	public String getSeoulcode() {
		return seoulcode;
	}

	public void setSeoulcode(String seoulcode) {
		this.seoulcode = seoulcode;
	}

	public String getGibangcode() {
		return gibangcode;
	}

	public void setGibangcode(String gibangcode) {
		this.gibangcode = gibangcode;
	}

	public String getTerminalcode() {
		return terminalcode;
	}

	public void setTerminalcode(String terminalcode) {
		this.terminalcode = terminalcode;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString();
	}
		
}
