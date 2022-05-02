package kr.co.mdaesin.models;

public class ReceptionQuantityModelView {

    private String linecode;
    private String linename;
    private String carcode;
    private String carname;
    private int cnt;
    private int qty;
    private String chong;
    private String gugan;
    private String senddate;
    private String rgunsu;
    private long rqty;
    private long rfare;
    private long rrate;
    private double weight;
    private String fixcheckingday;

    @Override
    public String toString() {
        return "ReceptionQuantityModelView{" +
                "linecode='" + linecode + '\'' +
                ", linename='" + linename + '\'' +
                ", carcode='" + carcode + '\'' +
                ", carname='" + carname + '\'' +
                ", cnt=" + cnt +
                ", qty=" + qty +
                ", chong='" + chong + '\'' +
                ", gugan='" + gugan + '\'' +
                ", senddate='" + senddate + '\'' +
                ", rgunsu='" + rgunsu + '\'' +
                ", rqty=" + rqty +
                ", rfare=" + rfare +
                ", rrate=" + rrate +
                ", weight=" + weight +
                ", fixcheckingday='" + fixcheckingday + '\'' +
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

    public int getCnt() {
        return cnt;
    }

    public void setCnt(int cnt) {
        this.cnt = cnt;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
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

    public long getRqty() {
        return rqty;
    }

    public void setRqty(long rqty) {
        this.rqty = rqty;
    }

    public long getRfare() {
        return rfare;
    }

    public void setRfare(long rfare) {
        this.rfare = rfare;
    }

    public long getRrate() {
        return rrate;
    }

    public void setRrate(long rrate) {
        this.rrate = rrate;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public String getFixcheckingday() {
        return fixcheckingday;
    }

    public void setFixcheckingday(String fixcheckingday) {
        this.fixcheckingday = fixcheckingday;
    }
}
