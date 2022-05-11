package kr.co.mdaesin.models;

import java.io.Serializable;

public class ReceiptHistoryModelView implements Serializable {

    /**
     * "billno": "1801202096557",
     *             "agencyname": "구로중앙",
     *             "hangmok": "발송제비용",
     *             "updatetor": "ds180102",
     *             "updatedate": "20220505",
     *             "updatetime": "0848",
     *             "statenayuk": "12,500",
     *             "nayuk": "2,500",
     *             "input_date": "20220505"
     */

    private String billNo;
    private String agencyname;
    private String category;
    private String updatetor;
    private String updatedate;
    private String updatetime;
    private String befcontent;
    private String aftcontent;
    private String input_date;
    private String searchMode;
    private String searchKeyword_date;
    private String linecode;

    @Override
    public String toString() {
        return "ReceiptHistoryModelView{" +
                "billNo='" + billNo + '\'' +
                ", agencyname='" + agencyname + '\'' +
                ", category='" + category + '\'' +
                ", updatetor='" + updatetor + '\'' +
                ", updatedate='" + updatedate + '\'' +
                ", updatetime='" + updatetime + '\'' +
                ", befcontent='" + befcontent + '\'' +
                ", aftcontent='" + aftcontent + '\'' +
                ", input_date='" + input_date + '\'' +
                ", searchMode='" + searchMode + '\'' +
                ", searchKeyword_date='" + searchKeyword_date + '\'' +
                ", linecode='" + linecode + '\'' +
                '}';
    }

    public String getBillNo() {
        return billNo;
    }

    public void setBillNo(String billNo) {
        this.billNo = billNo;
    }

    public String getAgencyname() {
        return agencyname;
    }

    public void setAgencyname(String agencyname) {
        this.agencyname = agencyname;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getUpdatetor() {
        return updatetor;
    }

    public void setUpdatetor(String updatetor) {
        this.updatetor = updatetor;
    }

    public String getUpdatedate() {
        return updatedate;
    }

    public void setUpdatedate(String updatedate) {
        this.updatedate = updatedate;
    }

    public String getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(String updatetime) {
        this.updatetime = updatetime;
    }

    public String getBefcontent() {
        return befcontent;
    }

    public void setBefcontent(String befcontent) {
        this.befcontent = befcontent;
    }

    public String getAftcontent() {
        return aftcontent;
    }

    public void setAftcontent(String aftcontent) {
        this.aftcontent = aftcontent;
    }

    public String getInput_date() {
        return input_date;
    }

    public void setInput_date(String input_date) {
        this.input_date = input_date;
    }

    public String getSearchMode() {
        return searchMode;
    }

    public void setSearchMode(String searchMode) {
        this.searchMode = searchMode;
    }

    public String getSearchKeyword_date() {
        return searchKeyword_date;
    }

    public void setSearchKeyword_date(String searchKeyword_date) {
        this.searchKeyword_date = searchKeyword_date;
    }

    public String getLinecode() {
        return linecode;
    }

    public void setLinecode(String linecode) {
        this.linecode = linecode;
    }
}
