package kr.co.mdaesin.models;

import java.io.Serializable;

public class ReceiptWayPointModelView implements Serializable {
    private String stagencycode;
    private String edagencycode;
    private String stagencyname;
    private String edagencyname;
    private String sendfare;
    private String arrivefare;
    private String gubun;
    private String linecode;
    private String searchKeyword_date;
    private String searchMode;

    private String det_agencycode;
    private String det_agencyname;
    private String det_sendagencyname;
    private String det_goods;
    private String det_pojang;
    private String det_qty;
    private String det_fare;

    private String linename;

    @Override
    public String toString() {
        return "ReceiptWayPointModelView{" +
                "stagencycode='" + stagencycode + '\'' +
                ", edagencycode='" + edagencycode + '\'' +
                ", stagencyname='" + stagencyname + '\'' +
                ", edagencyname='" + edagencyname + '\'' +
                ", sendfare='" + sendfare + '\'' +
                ", arrivefare='" + arrivefare + '\'' +
                ", gubun='" + gubun + '\'' +
                ", linecode='" + linecode + '\'' +
                ", searchKeyword_date='" + searchKeyword_date + '\'' +
                ", searchMode='" + searchMode + '\'' +
                ", det_agencycode='" + det_agencycode + '\'' +
                ", det_agencyname='" + det_agencyname + '\'' +
                ", det_sendagencyname='" + det_sendagencyname + '\'' +
                ", det_goods='" + det_goods + '\'' +
                ", det_pojang='" + det_pojang + '\'' +
                ", det_qty='" + det_qty + '\'' +
                ", det_fare='" + det_fare + '\'' +
                ", linename='" + linename + '\'' +
                '}';
    }

    public String getSearchMode() {
        return searchMode;
    }

    public void setSearchMode(String searchMode) {
        this.searchMode = searchMode;
    }

    public String getStagencycode() {
        return stagencycode;
    }

    public void setStagencycode(String stagencycode) {
        this.stagencycode = stagencycode;
    }

    public String getEdagencycode() {
        return edagencycode;
    }

    public void setEdagencycode(String edagencycode) {
        this.edagencycode = edagencycode;
    }

    public String getStagencyname() {
        return stagencyname;
    }

    public void setStagencyname(String stagencyname) {
        this.stagencyname = stagencyname;
    }

    public String getEdagencyname() {
        return edagencyname;
    }

    public void setEdagencyname(String edagencyname) {
        this.edagencyname = edagencyname;
    }

    public String getSendfare() {
        return sendfare;
    }

    public void setSendfare(String sendfare) {
        this.sendfare = sendfare;
    }

    public String getArrivefare() {
        return arrivefare;
    }

    public void setArrivefare(String arrivefare) {
        this.arrivefare = arrivefare;
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

    public String getSearchKeyword_date() {
        return searchKeyword_date;
    }

    public void setSearchKeyword_date(String searchKeyword_date) {
        this.searchKeyword_date = searchKeyword_date;
    }

    public String getDet_agencycode() {
        return det_agencycode;
    }

    public void setDet_agencycode(String det_agencycode) {
        this.det_agencycode = det_agencycode;
    }

    public String getDet_agencyname() {
        return det_agencyname;
    }

    public void setDet_agencyname(String det_agencyname) {
        this.det_agencyname = det_agencyname;
    }

    public String getDet_sendagencyname() {
        return det_sendagencyname;
    }

    public void setDet_sendagencyname(String det_sendagencyname) {
        this.det_sendagencyname = det_sendagencyname;
    }

    public String getDet_goods() {
        return det_goods;
    }

    public void setDet_goods(String det_goods) {
        this.det_goods = det_goods;
    }

    public String getDet_pojang() {
        return det_pojang;
    }

    public void setDet_pojang(String det_pojang) {
        this.det_pojang = det_pojang;
    }

    public String getDet_qty() {
        return det_qty;
    }

    public void setDet_qty(String det_qty) {
        this.det_qty = det_qty;
    }

    public String getDet_fare() {
        return det_fare;
    }

    public void setDet_fare(String det_fare) {
        this.det_fare = det_fare;
    }

    public String getLinename() {
        return linename;
    }

    public void setLinename(String linename) {
        this.linename = linename;
    }
}
