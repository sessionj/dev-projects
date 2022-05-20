package kr.co.ds.models;

import java.io.Serializable;

public class TrackingModelView implements Serializable {

    private String sendname;
    private String billno;
    private String sendingman;
    private String arrivalman;
    private String sendingday;
    private String relaystart1;
    private String relaystart2;
    private String relaystart3;
    private String relaystart4;
    private String relayend1;
    private String relayend2;
    private String relayend3;
    private String relayend4;
    private String land1name;
    private String land2name;
    private String land3name;
    private String arrivalname;
    private String sendingmantel;
    private String sendtel;
    private String land1tel;
    private String land2tel;
    private String land3tel;
    private String arrivaltel;
    private String arrivalmantel;
    private String statename;
    private String billstate;
    private int cnt;
    private String tbanprintyn;
    private String chulgoday;
    private String transcode;
    private String st1;
    private String st2;
    private String st3;
    private String st4;
    private String st5;
    private String jubsuday;
    private String insuja;
    private String goods;
    private String pojang;
    private String qty;

    private String searchMode;
    private String authenticationkey;

    private String item_gubun;
    private String item_agencyname;
    private String item_tel;
    private String item_inputday;
    private String item_outputday;
    private String item_location;

    @Override
    public String toString() {
        return "TrackingModelView{" +
                "sendname='" + sendname + '\'' +
                ", billno='" + billno + '\'' +
                ", sendingman='" + sendingman + '\'' +
                ", arrivalman='" + arrivalman + '\'' +
                ", sendingday='" + sendingday + '\'' +
                ", relaystart1='" + relaystart1 + '\'' +
                ", relaystart2='" + relaystart2 + '\'' +
                ", relaystart3='" + relaystart3 + '\'' +
                ", relaystart4='" + relaystart4 + '\'' +
                ", relayend1='" + relayend1 + '\'' +
                ", relayend2='" + relayend2 + '\'' +
                ", relayend3='" + relayend3 + '\'' +
                ", relayend4='" + relayend4 + '\'' +
                ", land1name='" + land1name + '\'' +
                ", land2name='" + land2name + '\'' +
                ", land3name='" + land3name + '\'' +
                ", arrivalname='" + arrivalname + '\'' +
                ", sendingmantel='" + sendingmantel + '\'' +
                ", sendtel='" + sendtel + '\'' +
                ", land1tel='" + land1tel + '\'' +
                ", land2tel='" + land2tel + '\'' +
                ", land3tel='" + land3tel + '\'' +
                ", arrivaltel='" + arrivaltel + '\'' +
                ", arrivalmantel='" + arrivalmantel + '\'' +
                ", statename='" + statename + '\'' +
                ", billstate='" + billstate + '\'' +
                ", cnt=" + cnt +
                ", tbanprintyn='" + tbanprintyn + '\'' +
                ", chulgoday='" + chulgoday + '\'' +
                ", transcode='" + transcode + '\'' +
                ", st1='" + st1 + '\'' +
                ", st2='" + st2 + '\'' +
                ", st3='" + st3 + '\'' +
                ", st4='" + st4 + '\'' +
                ", st5='" + st5 + '\'' +
                ", jubsuday='" + jubsuday + '\'' +
                ", insuja='" + insuja + '\'' +
                ", goods='" + goods + '\'' +
                ", pojang='" + pojang + '\'' +
                ", qty='" + qty + '\'' +
                ", searchMode='" + searchMode + '\'' +
                ", authenticationkey='" + authenticationkey + '\'' +
                ", item_gubun='" + item_gubun + '\'' +
                ", item_agencyname='" + item_agencyname + '\'' +
                ", item_tel='" + item_tel + '\'' +
                ", item_inputday='" + item_inputday + '\'' +
                ", item_outputday='" + item_outputday + '\'' +
                ", item_location='" + item_location + '\'' +
                '}';
    }

    public String getSendname() {
        return sendname;
    }

    public void setSendname(String sendname) {
        this.sendname = sendname;
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

    public int getCnt() {
        return cnt;
    }

    public void setCnt(int cnt) {
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

    public String getSearchMode() {
        return searchMode;
    }

    public void setSearchMode(String searchMode) {
        this.searchMode = searchMode;
    }

    public String getAuthenticationkey() {
        return authenticationkey;
    }

    public void setAuthenticationkey(String authenticationkey) {
        this.authenticationkey = authenticationkey;
    }

    public String getItem_gubun() {
        return item_gubun;
    }

    public void setItem_gubun(String item_gubun) {
        this.item_gubun = item_gubun;
    }

    public String getItem_agencyname() {
        return item_agencyname;
    }

    public void setItem_agencyname(String item_agencyname) {
        this.item_agencyname = item_agencyname;
    }

    public String getItem_tel() {
        return item_tel;
    }

    public void setItem_tel(String item_tel) {
        this.item_tel = item_tel;
    }

    public String getItem_inputday() {
        return item_inputday;
    }

    public void setItem_inputday(String item_inputday) {
        this.item_inputday = item_inputday;
    }

    public String getItem_outputday() {
        return item_outputday;
    }

    public void setItem_outputday(String item_outputday) {
        this.item_outputday = item_outputday;
    }

    public String getItem_location() {
        return item_location;
    }

    public void setItem_location(String item_location) {
        this.item_location = item_location;
    }
}
