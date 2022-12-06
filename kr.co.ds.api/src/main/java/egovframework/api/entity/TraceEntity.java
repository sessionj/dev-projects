package egovframework.api.entity;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonInclude;
/**
 * @FileName  : TraceEntity.java
 * @Project   : kr.co.ds.api
 * @Date      : 2022. 10. 27.
 * @작성자     : shadow

 * @변경이력 		:
 * @프로그램 설명 : 화물추적 [tracking]
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TraceEntity implements Serializable{

	private static final long serialVersionUID = 158838578081269359L;
	
	private String billno = "";
	private String sendagencyname = "";
	private String sendingman = "";
	private String arrivalman = "";
	private String sendingday = "";
	private String relaystart1 = "";
	private String relaystart2 = "";
	private String relaystart3 = "";
	private String relaystart4 = "";
	private String relayend1 = "";
	private String relayend2 = "";
	private String relayend3 = "";
	private String relayend4 = "";
	private String land1name = "";
	private String land2name = "";
	private String land3name = "";
	private String arrivalname = "";
	private String sendingmantel = "";
	private String sendtel = "";
	private String land1tel = "";
	private String land2tel = "";
	private String land3tel = "";
	private String arrivaltel = "";
	private String arrivalmantel = "";
	private String statename = "";
	private String billstate = "";
	private String cnt = "";
	private String tbanprintyn = "";
	private String chulgoday = "";
	private String transcode = "";
	private String st1 = "";
	private String st2 = "";
	private String st3 = "";
	private String st4 = "";
	private String st5 = "";
	private String jubsuday = "";
	private String insuja = "";
	private String goods = "";
	private String pojang = "";
	private String qty = "";
	private String agency1 = "";
	private String agency2 = "";
	private String agency3 = "";
	private String agency4 = "";
	private String agency5 = "";
	private String internettel1 = "";
	private String internettel2 = "";
	private String internettel3 = "";
	private String internettel4 = "";
	private String internettel5 = "";
	private String courierunavailable = "";
	private String arrivalagencycode = "";
	private String scaninfo = "";
	private String area = "";
	
	public String getSendagencyname() {
		return sendagencyname;
	}

	public void setSendagencyname(String sendagencyname) {
		this.sendagencyname = sendagencyname;
	}

	public String getBillno() {
		return billno;
	}

	public void setBillno(String billno) {
		this.billno = billno;
	}

	public String getSendingman() {
		return sendingman;
	}

	public void setSendingman(String sendingman) {
		this.sendingman = sendingman;
	}

	public String getArrivalman() {
		return arrivalman;
	}

	public void setArrivalman(String arrivalman) {
		this.arrivalman = arrivalman;
	}

	public String getSendingday() {
		return sendingday;
	}

	public void setSendingday(String sendingday) {
		this.sendingday = sendingday;
	}

	public String getRelaystart1() {
		return relaystart1;
	}

	public void setRelaystart1(String relaystart1) {
		this.relaystart1 = relaystart1;
	}

	public String getRelaystart2() {
		return relaystart2;
	}

	public void setRelaystart2(String relaystart2) {
		this.relaystart2 = relaystart2;
	}

	public String getRelaystart3() {
		return relaystart3;
	}

	public void setRelaystart3(String relaystart3) {
		this.relaystart3 = relaystart3;
	}

	public String getRelaystart4() {
		return relaystart4;
	}

	public void setRelaystart4(String relaystart4) {
		this.relaystart4 = relaystart4;
	}

	public String getRelayend1() {
		return relayend1;
	}

	public void setRelayend1(String relayend1) {
		this.relayend1 = relayend1;
	}

	public String getRelayend2() {
		return relayend2;
	}

	public void setRelayend2(String relayend2) {
		this.relayend2 = relayend2;
	}

	public String getRelayend3() {
		return relayend3;
	}

	public void setRelayend3(String relayend3) {
		this.relayend3 = relayend3;
	}

	public String getRelayend4() {
		return relayend4;
	}

	public void setRelayend4(String relayend4) {
		this.relayend4 = relayend4;
	}

	public String getLand1name() {
		return land1name;
	}

	public void setLand1name(String land1name) {
		this.land1name = land1name;
	}

	public String getLand2name() {
		return land2name;
	}

	public void setLand2name(String land2name) {
		this.land2name = land2name;
	}

	public String getLand3name() {
		return land3name;
	}

	public void setLand3name(String land3name) {
		this.land3name = land3name;
	}

	public String getArrivalname() {
		return arrivalname;
	}

	public void setArrivalname(String arrivalname) {
		this.arrivalname = arrivalname;
	}

	public String getSendingmantel() {
		return sendingmantel;
	}

	public void setSendingmantel(String sendingmantel) {
		this.sendingmantel = sendingmantel;
	}

	public String getSendtel() {
		return sendtel;
	}

	public void setSendtel(String sendtel) {
		this.sendtel = sendtel;
	}

	public String getLand1tel() {
		return land1tel;
	}

	public void setLand1tel(String land1tel) {
		this.land1tel = land1tel;
	}

	public String getLand2tel() {
		return land2tel;
	}

	public void setLand2tel(String land2tel) {
		this.land2tel = land2tel;
	}

	public String getLand3tel() {
		return land3tel;
	}

	public void setLand3tel(String land3tel) {
		this.land3tel = land3tel;
	}

	public String getArrivaltel() {
		return arrivaltel;
	}

	public void setArrivaltel(String arrivaltel) {
		this.arrivaltel = arrivaltel;
	}

	public String getArrivalmantel() {
		return arrivalmantel;
	}

	public void setArrivalmantel(String arrivalmantel) {
		this.arrivalmantel = arrivalmantel;
	}

	public String getStatename() {
		return statename;
	}

	public void setStatename(String statename) {
		this.statename = statename;
	}

	public String getBillstate() {
		return billstate;
	}

	public void setBillstate(String billstate) {
		this.billstate = billstate;
	}

	public String getCnt() {
		return cnt;
	}

	public void setCnt(String cnt) {
		this.cnt = cnt;
	}

	public String getTbanprintyn() {
		return tbanprintyn;
	}

	public void setTbanprintyn(String tbanprintyn) {
		this.tbanprintyn = tbanprintyn;
	}

	public String getChulgoday() {
		return chulgoday;
	}

	public void setChulgoday(String chulgoday) {
		this.chulgoday = chulgoday;
	}

	public String getTranscode() {
		return transcode;
	}

	public void setTranscode(String transcode) {
		this.transcode = transcode;
	}

	public String getSt1() {
		return st1;
	}

	public void setSt1(String st1) {
		this.st1 = st1;
	}

	public String getSt2() {
		return st2;
	}

	public void setSt2(String st2) {
		this.st2 = st2;
	}

	public String getSt3() {
		return st3;
	}

	public void setSt3(String st3) {
		this.st3 = st3;
	}

	public String getSt4() {
		return st4;
	}

	public void setSt4(String st4) {
		this.st4 = st4;
	}

	public String getSt5() {
		return st5;
	}

	public void setSt5(String st5) {
		this.st5 = st5;
	}

	public String getJubsuday() {
		return jubsuday;
	}

	public void setJubsuday(String jubsuday) {
		this.jubsuday = jubsuday;
	}

	public String getInsuja() {
		return insuja;
	}

	public void setInsuja(String insuja) {
		this.insuja = insuja;
	}

	public String getGoods() {
		return goods;
	}

	public void setGoods(String goods) {
		this.goods = goods;
	}

	public String getPojang() {
		return pojang;
	}

	public void setPojang(String pojang) {
		this.pojang = pojang;
	}

	public String getQty() {
		return qty;
	}

	public void setQty(String qty) {
		this.qty = qty;
	}

	public String getAgency1() {
		return agency1;
	}

	public void setAgency1(String agency1) {
		this.agency1 = agency1;
	}

	public String getAgency2() {
		return agency2;
	}

	public void setAgency2(String agency2) {
		this.agency2 = agency2;
	}

	public String getAgency3() {
		return agency3;
	}

	public void setAgency3(String agency3) {
		this.agency3 = agency3;
	}

	public String getAgency4() {
		return agency4;
	}

	public void setAgency4(String agency4) {
		this.agency4 = agency4;
	}

	public String getAgency5() {
		return agency5;
	}

	public void setAgency5(String agency5) {
		this.agency5 = agency5;
	}

	public String getInternettel1() {
		return internettel1;
	}

	public void setInternettel1(String internettel1) {
		this.internettel1 = internettel1;
	}

	public String getInternettel2() {
		return internettel2;
	}

	public void setInternettel2(String internettel2) {
		this.internettel2 = internettel2;
	}

	public String getInternettel3() {
		return internettel3;
	}

	public void setInternettel3(String internettel3) {
		this.internettel3 = internettel3;
	}

	public String getInternettel4() {
		return internettel4;
	}

	public void setInternettel4(String internettel4) {
		this.internettel4 = internettel4;
	}

	public String getInternettel5() {
		return internettel5;
	}

	public void setInternettel5(String internettel5) {
		this.internettel5 = internettel5;
	}

	public String getCourierunavailable() {
		return courierunavailable;
	}

	public void setCourierunavailable(String courierunavailable) {
		this.courierunavailable = courierunavailable;
	}

	public String getArrivalagencycode() {
		return arrivalagencycode;
	}

	public void setArrivalagencycode(String arrivalagencycode) {
		this.arrivalagencycode = arrivalagencycode;
	}

	public String getScaninfo() {
		return scaninfo;
	}

	public void setScaninfo(String scaninfo) {
		this.scaninfo = scaninfo;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
