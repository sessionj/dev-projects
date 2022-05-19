package kr.co.mdaesin.models;

public class SuggestionModelView {

    private String title;
    private String content;
    private String phone;
    private String linename;
    private String username;
    private String category;
    private String secert;
    private String mode = "I";

    @Override
    public String toString() {
        return "SuggestionModelView{" +
                "title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", phone='" + phone + '\'' +
                ", linename='" + linename + '\'' +
                ", username='" + username + '\'' +
                ", category='" + category + '\'' +
                ", secert='" + secert + '\'' +
                ", mode='" + mode + '\'' +
                '}';
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getLinename() {
        return linename;
    }

    public void setLinename(String linename) {
        this.linename = linename;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSecert() {
        return secert;
    }

    public void setSecert(String secert) {
        this.secert = secert;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }
}
