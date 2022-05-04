package kr.co.mdaesin.models;

public class ReceiptDetailsModelView {
    private String agencyname;
    private String md;
    private String cnt;
    private String qty;
    private String fare;
    private String std_departuretime;
    private String std_deadlinetime;
    private String agencytel;

    @Override
    public String toString() {
        return "ReceiptDetailsModelView{" +
                "agencyname='" + agencyname + '\'' +
                ", md='" + md + '\'' +
                ", cnt=" + cnt +
                ", qty=" + qty +
                ", fare=" + fare +
                ", std_departuretime='" + std_departuretime + '\'' +
                ", std_deadlinetime='" + std_deadlinetime + '\'' +
                ", agencycode='" + agencytel + '\'' +
                '}';
    }

    public String getAgencyname() {
        return agencyname;
    }

    public void setAgencyname(String agencyname) {
        this.agencyname = agencyname;
    }

    public String getMd() {
        return md;
    }

    public void setMd(String md) {
        this.md = md;
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

    public String getFare() {
        return fare;
    }

    public void setFare(String fare) {
        this.fare = fare;
    }

    public String getStd_departuretime() {
        return std_departuretime;
    }

    public void setStd_departuretime(String std_departuretime) {
        this.std_departuretime = std_departuretime;
    }

    public String getStd_deadlinetime() {
        return std_deadlinetime;
    }

    public void setStd_deadlinetime(String std_deadlinetime) {
        this.std_deadlinetime = std_deadlinetime;
    }

    public String getAgencytel() {
        return agencytel;
    }

    public void setAgencytel(String agencytel) {
        this.agencytel = agencytel;
    }
}
