package kr.co.delivery_v1.models;

import android.graphics.Bitmap;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import kr.co.delivery_v1.comm.Label;

/**
 * delivery room database 
 */
@Entity(tableName = Label.DELIVERY_BASE_ROOM_DELIVERY_DATABASE_NAME)
public class DeliveryModelView {
    @NonNull
    @PrimaryKey()
    private String billno;

    @ColumnInfo(name = "input_date")            private String input_date;
    @ColumnInfo(name = "input_time")            private String input_time;
    @ColumnInfo(name = "transcode")             private String transcode;
    @ColumnInfo(name = "sendingagencycode")     private String sendingagencycode;
    @ColumnInfo(name = "arrivalagencycode")     private String arrivalagencycode;
    @ColumnInfo(name = "sendingmantel")         private String sendingmantel;
    @ColumnInfo(name = "sendingman")            private String sendingman;
    @ColumnInfo(name = "arrivalmantel")         private String arrivalmantel;
    @ColumnInfo(name = "arrivalmantel2")        private String arrivalmantel2;
    @ColumnInfo(name = "arrivalman")            private String arrivalman;
    @ColumnInfo(name = "zipcode")               private String zipcode;
    @ColumnInfo(name = "adress")                private String adress;
    @ColumnInfo(name = "prefare")               private String prefare;
    @ColumnInfo(name = "fare")                  private String fare;
    @ColumnInfo(name = "deliveryfare")          private String deliveryfare;
    @ColumnInfo(name = "ogideliveryfare")       private String ogideliveryfare;
    @ColumnInfo(name = "distance")              private String distance;
    @ColumnInfo(name = "payway")                private String payway;
    @ColumnInfo(name = "goods")                 private String goods;
    @ColumnInfo(name = "pojang")                private String pojang;
    @ColumnInfo(name = "qty")                   private int qty;
    @ColumnInfo(name = "weight")                private String weight;
    @ColumnInfo(name = "memo")                  private String memo;
    @ColumnInfo(name = "billstate")             private String billstate;
    @ColumnInfo(name = "deliverycourse")        private String deliverycourse;
    @ColumnInfo(name = "creatdate")             private String creatdate;
    @ColumnInfo(name = "delivery_state")        private String delivery_state;
    @ColumnInfo(name = "delivery_picture_path") private String delivery_picture_path;
    @ColumnInfo(name = "deliverycoursenm")      private String deliverycoursenm;

    private String delivery_course;
    private String delivery_course_name;
    private int delivery_course_cnt;
    private String combination_key;
    private boolean isSelected;

    @Override
    public String toString() {
        return "DeliveryModelView{" +
                "billno='" + billno + '\'' +
                ", input_date='" + input_date + '\'' +
                ", input_time='" + input_time + '\'' +
                ", transcode='" + transcode + '\'' +
                ", sendingagencycode='" + sendingagencycode + '\'' +
                ", arrivalagencycode='" + arrivalagencycode + '\'' +
                ", sendingmantel='" + sendingmantel + '\'' +
                ", sendingman='" + sendingman + '\'' +
                ", arrivalmantel='" + arrivalmantel + '\'' +
                ", arrivalmantel2='" + arrivalmantel2 + '\'' +
                ", arrivalman='" + arrivalman + '\'' +
                ", zipcode='" + zipcode + '\'' +
                ", adress='" + adress + '\'' +
                ", prefare='" + prefare + '\'' +
                ", fare='" + fare + '\'' +
                ", deliveryfare='" + deliveryfare + '\'' +
                ", ogideliveryfare='" + ogideliveryfare + '\'' +
                ", distance='" + distance + '\'' +
                ", payway='" + payway + '\'' +
                ", goods='" + goods + '\'' +
                ", pojang='" + pojang + '\'' +
                ", qty=" + qty +
                ", weight='" + weight + '\'' +
                ", memo='" + memo + '\'' +
                ", billstate='" + billstate + '\'' +
                ", deliverycourse='" + deliverycourse + '\'' +
                ", creatdate='" + creatdate + '\'' +
                ", delivery_state='" + delivery_state + '\'' +
                ", delivery_picture_path='" + delivery_picture_path + '\'' +
                ", deliverycoursenm='" + deliverycoursenm + '\'' +
                ", delivery_course='" + delivery_course + '\'' +
                ", delivery_course_name='" + delivery_course_name + '\'' +
                ", delivery_course_cnt=" + delivery_course_cnt +
                ", combination_key='" + combination_key + '\'' +
                ", isSelected=" + isSelected +
                '}';
    }

    public String getBillno() {
        return billno;
    }

    public void setBillno(String billno) {
        this.billno = billno;
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

    public String getTranscode() {
        return transcode;
    }

    public void setTranscode(String transcode) {
        this.transcode = transcode;
    }

    public String getSendingagencycode() {
        return sendingagencycode;
    }

    public void setSendingagencycode(String sendingagencycode) {
        this.sendingagencycode = sendingagencycode;
    }

    public String getArrivalagencycode() {
        return arrivalagencycode;
    }

    public void setArrivalagencycode(String arrivalagencycode) {
        this.arrivalagencycode = arrivalagencycode;
    }

    public String getSendingmantel() {
        return sendingmantel;
    }

    public void setSendingmantel(String sendingmantel) {
        this.sendingmantel = sendingmantel;
    }

    public String getSendingman() {
        return sendingman;
    }

    public void setSendingman(String sendingman) {
        this.sendingman = sendingman;
    }

    public String getArrivalmantel() {
        return arrivalmantel;
    }

    public void setArrivalmantel(String arrivalmantel) {
        this.arrivalmantel = arrivalmantel;
    }

    public String getArrivalman() {
        return arrivalman;
    }

    public void setArrivalman(String arrivalman) {
        this.arrivalman = arrivalman;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
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

    public String getOgideliveryfare() {
        return ogideliveryfare;
    }

    public void setOgideliveryfare(String ogideliveryfare) {
        this.ogideliveryfare = ogideliveryfare;
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

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty < 0 ? 0 : qty;
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

    public String getBillstate() {
        return billstate;
    }

    public void setBillstate(String billstate) {
        this.billstate = billstate;
    }

    public String getDeliverycourse() {
        return deliverycourse == null ? "000" : deliverycourse;
    }

    public void setDeliverycourse(String deliverycourse) {
        this.deliverycourse = deliverycourse;
    }

    public String getCreatdate() {
        return creatdate;
    }

    public void setCreatdate(String creatdate) {
        this.creatdate = creatdate;
    }

    public String getDelivery_state() {
        return delivery_state;
    }

    public void setDelivery_state(String delivery_state) {

        if ( delivery_state == null || delivery_state.equals("")){
            this.delivery_state = "N";
        }else{
            this.delivery_state = delivery_state;
        }
    }

    public String getArrivalmantel2() {
        return arrivalmantel2;
    }

    public void setArrivalmantel2(String arrivalmantel2) {
        this.arrivalmantel2 = arrivalmantel2;
    }

    public String getDelivery_picture_path() {
        return delivery_picture_path;
    }

    public void setDelivery_picture_path(String delivery_picture_path) {
        this.delivery_picture_path = delivery_picture_path;
    }

    public String getDeliverycoursenm() {
        return deliverycoursenm;
    }

    public void setDeliverycoursenm(String deliverycoursenm) {
        this.deliverycoursenm = deliverycoursenm;
    }

    public String getDelivery_course() {
        return delivery_course;
    }

    public void setDelivery_course(String delivery_course) {
        this.delivery_course = delivery_course;
    }

    public String getDelivery_course_name() {
        return delivery_course_name;
    }

    public void setDelivery_course_name(String delivery_course_name) {
        this.delivery_course_name = delivery_course_name;
    }

    public int getDelivery_course_cnt() {
        return delivery_course_cnt;
    }

    public void setDelivery_course_cnt(int delivery_course_cnt) {
        this.delivery_course_cnt = delivery_course_cnt;
    }

    public String getCombination_key() {
        return combination_key;
    }

    public void setCombination_key(String combination_key) {
        this.combination_key = combination_key;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
