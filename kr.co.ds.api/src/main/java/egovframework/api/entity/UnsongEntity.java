package egovframework.api.entity;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
/**
 * @FileName  : EgovApiDefaultEntity.java
 * @Project   : kr.co.ds.api
 * @Date      : 2022. 10. 27.
 * @작성자     : shadow

 * @변경이력 		:
 * @프로그램 설명 : 운송장
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UnsongEntity implements Serializable{

	private static final long serialVersionUID = 958838578081269359L;
	
	private String billNo = "";
	private String input_date = "";
	private String input_time = "";
	private String chasu = "";
	private String magam_gb = "";
	private String transCode = "";
	private String terminal_sunbun = "";
	private String delivery_yn = "";
	private String sendingAgencycode = "";
	private String arrivalAgencycode = "";
	private String lineSerialno = "";
	private String sendingManTel = "";
	private String sendingMan = "";
	private String arrivalManTel = "";
	private String arrivalMan = "";
	private String zipCode = "";
	private String adress = "";
	private String preFare = "";
	private String fare = "";
	private String deliveryFare = "";
	private String ogideliveryFare = "";
	private String wayfare = "";
	private String enterCharge = "";
	private String workCharge = "";
	private String distance = "";
	private String payway = "";
	private String goods = "";
	private String pojang = "";
	private String qty = "";
	private String weight = "";
	private String memo = "";
	private String ganChasu = "";
	private String hachaChasu = "";
	private String billState = "";
	private String changgo = "";
	private String nearGubun = "";
	private String update_date = "";
	private String creator = "";
	private String updator = "";
	private String dmlGubun = "";
	private String billGubun = "";
	private String deliveryCourse = "";
	private String barcode_yn = "";
	private String creatDate = "";
	private String barcodeNo = "";
	private String createAgency = "";
	
	@Override
	public String toString() {
		return super.toString();
	}
	
	public String getBillNo() {
		return billNo;
	}
	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}
	public String getInput_date() {
		return input_date;
	}
	public void setInput_date(String input_date) {
		this.input_date = input_date;
	}
	public String getInput_time() {
		return input_time;
	}
	public void setInput_time(String input_time) {
		this.input_time = input_time;
	}
	public String getChasu() {
		return chasu;
	}
	public void setChasu(String chasu) {
		this.chasu = chasu;
	}
	public String getMagam_gb() {
		return magam_gb;
	}
	public void setMagam_gb(String magam_gb) {
		this.magam_gb = magam_gb;
	}
	public String getTransCode() {
		return transCode;
	}
	public void setTransCode(String transCode) {
		this.transCode = transCode;
	}
	public String getTerminal_sunbun() {
		return terminal_sunbun;
	}
	public void setTerminal_sunbun(String terminal_sunbun) {
		this.terminal_sunbun = terminal_sunbun;
	}
	public String getDelivery_yn() {
		return delivery_yn;
	}
	public void setDelivery_yn(String delivery_yn) {
		this.delivery_yn = delivery_yn;
	}
	public String getSendingAgencycode() {
		return sendingAgencycode;
	}
	public void setSendingAgencycode(String sendingAgencycode) {
		this.sendingAgencycode = sendingAgencycode;
	}
	public String getArrivalAgencycode() {
		return arrivalAgencycode;
	}
	public void setArrivalAgencycode(String arrivalAgencycode) {
		this.arrivalAgencycode = arrivalAgencycode;
	}
	public String getLineSerialno() {
		return lineSerialno;
	}
	public void setLineSerialno(String lineSerialno) {
		this.lineSerialno = lineSerialno;
	}
	public String getSendingManTel() {
		return sendingManTel;
	}
	public void setSendingManTel(String sendingManTel) {
		this.sendingManTel = sendingManTel;
	}
	public String getSendingMan() {
		return sendingMan;
	}
	public void setSendingMan(String sendingMan) {
		this.sendingMan = sendingMan;
	}
	public String getArrivalManTel() {
		return arrivalManTel;
	}
	public void setArrivalManTel(String arrivalManTel) {
		this.arrivalManTel = arrivalManTel;
	}
	public String getArrivalMan() {
		return arrivalMan;
	}
	public void setArrivalMan(String arrivalMan) {
		this.arrivalMan = arrivalMan;
	}
	public String getZipCode() {
		return zipCode;
	}
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	public String getAdress() {
		return adress;
	}
	public void setAdress(String adress) {
		this.adress = adress;
	}
	public String getPreFare() {
		return preFare;
	}
	public void setPreFare(String preFare) {
		this.preFare = preFare;
	}
	public String getFare() {
		return fare;
	}
	public void setFare(String fare) {
		this.fare = fare;
	}
	public String getDeliveryFare() {
		return deliveryFare;
	}
	public void setDeliveryFare(String deliveryFare) {
		this.deliveryFare = deliveryFare;
	}
	public String getOgideliveryFare() {
		return ogideliveryFare;
	}
	public void setOgideliveryFare(String ogideliveryFare) {
		this.ogideliveryFare = ogideliveryFare;
	}
	public String getWayfare() {
		return wayfare;
	}
	public void setWayfare(String wayfare) {
		this.wayfare = wayfare;
	}
	public String getEnterCharge() {
		return enterCharge;
	}
	public void setEnterCharge(String enterCharge) {
		this.enterCharge = enterCharge;
	}
	public String getWorkCharge() {
		return workCharge;
	}
	public void setWorkCharge(String workCharge) {
		this.workCharge = workCharge;
	}
	public String getDistance() {
		return distance;
	}
	public void setDistance(String distance) {
		this.distance = distance;
	}
	public String getPayway() {
		return payway;
	}
	public void setPayway(String payway) {
		this.payway = payway;
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
	public String getWeight() {
		return weight;
	}
	public void setWeight(String weight) {
		this.weight = weight;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public String getGanChasu() {
		return ganChasu;
	}
	public void setGanChasu(String ganChasu) {
		this.ganChasu = ganChasu;
	}
	public String getHachaChasu() {
		return hachaChasu;
	}
	public void setHachaChasu(String hachaChasu) {
		this.hachaChasu = hachaChasu;
	}
	public String getBillState() {
		return billState;
	}
	public void setBillState(String billState) {
		this.billState = billState;
	}
	public String getChanggo() {
		return changgo;
	}
	public void setChanggo(String changgo) {
		this.changgo = changgo;
	}
	public String getNearGubun() {
		return nearGubun;
	}
	public void setNearGubun(String nearGubun) {
		this.nearGubun = nearGubun;
	}
	public String getUpdate_date() {
		return update_date;
	}
	public void setUpdate_date(String update_date) {
		this.update_date = update_date;
	}
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
	public String getUpdator() {
		return updator;
	}
	public void setUpdator(String updator) {
		this.updator = updator;
	}
	public String getDmlGubun() {
		return dmlGubun;
	}
	public void setDmlGubun(String dmlGubun) {
		this.dmlGubun = dmlGubun;
	}
	public String getBillGubun() {
		return billGubun;
	}
	public void setBillGubun(String billGubun) {
		this.billGubun = billGubun;
	}
	public String getDeliveryCourse() {
		return deliveryCourse;
	}
	public void setDeliveryCourse(String deliveryCourse) {
		this.deliveryCourse = deliveryCourse;
	}
	public String getBarcode_yn() {
		return barcode_yn;
	}
	public void setBarcode_yn(String barcode_yn) {
		this.barcode_yn = barcode_yn;
	}
	public String getCreatDate() {
		return creatDate;
	}
	public void setCreatDate(String creatDate) {
		this.creatDate = creatDate;
	}
	public String getBarcodeNo() {
		return barcodeNo;
	}
	public void setBarcodeNo(String barcodeNo) {
		this.barcodeNo = barcodeNo;
	}
	public String getCreateAgency() {
		return createAgency;
	}
	public void setCreateAgency(String createAgency) {
		this.createAgency = createAgency;
	}
	
}
