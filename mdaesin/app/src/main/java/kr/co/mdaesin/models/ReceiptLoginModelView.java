package kr.co.mdaesin.models;

public class ReceiptLoginModelView {

    private String id;
    private String user_pos;
    private String user_phone;
    private String authenticationkey;
    private String searchMode;
    private String searchKeyword_date;
    private String linecode;
    private String linename;

    @Override
    public String toString() {
        return "ReceiptLoginModelView{" +
                "id='" + id + '\'' +
                ", user_pos='" + user_pos + '\'' +
                ", user_phone='" + user_phone + '\'' +
                ", authenticationkey='" + authenticationkey + '\'' +
                ", searchMode='" + searchMode + '\'' +
                ", searchKeyword_date='" + searchKeyword_date + '\'' +
                ", linecode='" + linecode + '\'' +
                ", linename='" + linename + '\'' +
                '}';
    }

    public String getLinename() {
        return linename;
    }

    public void setLinename(String linename) {
        this.linename = linename;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser_pos() {
        return user_pos;
    }

    public void setUser_pos(String user_pos) {
        this.user_pos = user_pos;
    }

    public String getUser_phone() {
        return user_phone;
    }

    public void setUser_phone(String user_phone) {
        this.user_phone = user_phone;
    }

    public String getAuthenticationkey() {
        return authenticationkey;
    }

    public void setAuthenticationkey(String authenticationkey) {
        this.authenticationkey = authenticationkey;
    }
}
