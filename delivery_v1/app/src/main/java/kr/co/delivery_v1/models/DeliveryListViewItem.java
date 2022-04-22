package kr.co.delivery_v1.models;

/**
 * 리스트뷰에서도 사용하지만 room 저장용이기도 함
 * 배달 entity
 */
public class DeliveryListViewItem {

    private String billno;
    private String input_date;
    private String input_time;
    private String transcode;
    private String sendingagencycode;
    private String arrivalagencycode;
    private String sendingmantel;
    private String sendingman;
    private String arrivalmantel;
    private String arrivalmante2;
    private String arrivalman;
    private String zipcode;
    private String adress;
    private String prefare;
    private String fare;
    private String deliveryfare;
    private String ogideliveryfare;
    private String distance;
    private String payway;
    private String goods;
    private String pojang;
    private int qty;
    private String weight;
    private String memo;
    private String billstate;
    private String deliverycourse;
    private String creatdate;

    private String delivery_course;
    private String delivery_course_name;
    private int delivery_course_cnt;

    @Override
    public String toString() {
        return "DeliveryListViewItem{" +
                "billno='" + billno + '\'' +
                ", input_date='" + input_date + '\'' +
                ", input_time='" + input_time + '\'' +
                ", transcode='" + transcode + '\'' +
                ", sendingagencycode='" + sendingagencycode + '\'' +
                ", arrivalagencycode='" + arrivalagencycode + '\'' +
                ", sendingmantel='" + sendingmantel + '\'' +
                ", sendingman='" + sendingman + '\'' +
                ", arrivalmantel='" + arrivalmantel + '\'' +
                ", arrivalmante2='" + arrivalmante2 + '\'' +
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
                ", delivery_course='" + delivery_course + '\'' +
                ", delivery_course_name='" + delivery_course_name + '\'' +
                ", delivery_course_cnt=" + delivery_course_cnt +
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
        return deliverycourse;
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

    public String getArrivalmante2() {
        return arrivalmante2;
    }

    public void setArrivalmante2(String arrivalmante2) {
        this.arrivalmante2 = arrivalmante2;
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
}
