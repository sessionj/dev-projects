package kr.co.mdaesin.models;

import java.io.Serializable;

public class ReceptionQuantityModelView implements Serializable {

    private String linecode;
    private String linename;
    private String carcode;
    private String carname;
    private String cnt;
    private String qty;
    private String chong;
    private String gugan;
    private String senddate;
    private String rgunsu;
    private String rqty;
    private String rfare;
    private String rrate;
    private String weight;
    private String fixcheckingday;

    private String searchKeyword_date;
    private String searchMode;

    private String billNo;
    private String sendingagencyname;
    private String arrivalagencyname;
    private String arrivalman;
    private String goods;
    private String pojang;
    private String prefare;
    private String fare;
    private String deliveryfare;
    private String payway;

    @Override
    public String toString() {
        return "ReceptionQuantityModelView{" +
                "linecode='" + linecode + '\'' +
                ", linename='" + linename + '\'' +
                ", carcode='" + carcode + '\'' +
                ", carname='" + carname + '\'' +
                ", cnt='" + cnt + '\'' +
                ", qty='" + qty + '\'' +
                ", chong='" + chong + '\'' +
                ", gugan='" + gugan + '\'' +
                ", senddate='" + senddate + '\'' +
                ", rgunsu='" + rgunsu + '\'' +
                ", rqty='" + rqty + '\'' +
                ", rfare='" + rfare + '\'' +
                ", rrate='" + rrate + '\'' +
                ", weight='" + weight + '\'' +
                ", fixcheckingday='" + fixcheckingday + '\'' +
                ", searchKeyword_date='" + searchKeyword_date + '\'' +
                ", searchMode='" + searchMode + '\'' +
                ", billNo='" + billNo + '\'' +
                ", sendingagencyname='" + sendingagencyname + '\'' +
                ", arrivalagencyname='" + arrivalagencyname + '\'' +
                ", arrivalman='" + arrivalman + '\'' +
                ", goods='" + goods + '\'' +
                ", pojang='" + pojang + '\'' +
                ", prefare='" + prefare + '\'' +
                ", fare='" + fare + '\'' +
                ", deliveryfare='" + deliveryfare + '\'' +
                ", payway='" + payway + '\'' +
                '}';
    }

    public String getLinecode() {
        return linecode;
    }

    public void setLinecode(String linecode) {
        this.linecode = linecode;
    }

    public String getLinename() {
        return linename;
    }

    public void setLinename(String linename) {
        this.linename = linename;
    }

    public String getCarcode() {
        return carcode;
    }

    public void setCarcode(String carcode) {
        this.carcode = carcode;
    }

    public String getCarname() {
        return carname;
    }

    public void setCarname(String carname) {
        this.carname = carname;
    }

    public String getCnt() {
        return cnt;
    }

    public void setCnt(String cnt) {
        this.cnt = cnt;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getChong() {
        return chong;
    }

    public void setChong(String chong) {
        this.chong = chong;
    }

    public String getGugan() {
        return gugan;
    }

    public void setGugan(String gugan) {
        this.gugan = gugan;
    }

    public String getSenddate() {
        return senddate;
    }

    public void setSenddate(String senddate) {
        this.senddate = senddate;
    }

    public String getRgunsu() {
        return rgunsu;
    }

    public void setRgunsu(String rgunsu) {
        this.rgunsu = rgunsu;
    }

    public String getRqty() {
        return rqty;
    }

    public void setRqty(String rqty) {
        this.rqty = rqty;
    }

    public String getRfare() {
        return rfare;
    }

    public void setRfare(String rfare) {
        this.rfare = rfare;
    }

    public String getRrate() {
        return rrate;
    }

    public void setRrate(String rrate) {
        this.rrate = rrate;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getFixcheckingday() {
        return fixcheckingday;
    }

    public void setFixcheckingday(String fixcheckingday) {
        this.fixcheckingday = fixcheckingday;
    }

    public String getSearchKeyword_date() {
        return searchKeyword_date;
    }

    public void setSearchKeyword_date(String searchKeyword_date) {
        this.searchKeyword_date = searchKeyword_date;
    }

    public String getSearchMode() {
        return searchMode;
    }

    public void setSearchMode(String searchMode) {
        this.searchMode = searchMode;
    }

    public String getBillNo() {
        return billNo;
    }

    public void setBillNo(String billNo) {
        this.billNo = billNo;
    }

    public String getSendingagencyname() {
        return sendingagencyname;
    }

    public void setSendingagencyname(String sendingagencyname) {
        this.sendingagencyname = sendingagencyname;
    }

    public String getArrivalman() {
        return arrivalman;
    }

    public void setArrivalman(String arrivalman) {
        this.arrivalman = arrivalman;
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

    public String getPrefare() {
        return prefare;
    }

    public void setPrefare(String prefare) {
        this.prefare = prefare;
    }

    public String getFare() {
        return fare;
    }

    public void setFare(String fare) {
        this.fare = fare;
    }

    public String getDeliveryfare() {
        return deliveryfare;
    }

    public void setDeliveryfare(String deliveryfare) {
        this.deliveryfare = deliveryfare;
    }

    public String getPayway() {
        return payway;
    }

    public void setPayway(String payway) {
        this.payway = payway;
    }

    public String getArrivalagencyname() {
        return arrivalagencyname;
    }

    public void setArrivalagencyname(String arrivalagencyname) {
        this.arrivalagencyname = arrivalagencyname;
    }
}
