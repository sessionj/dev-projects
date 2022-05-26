package kr.co.ds.models;

import java.io.Serializable;

public class LoginModelView implements Serializable {

    private String mode;
    private String phone_number;
    private String certification_key;

    @Override
    public String toString() {
        return "LoginModelView{" +
                "mode='" + mode + '\'' +
                ", phone_number='" + phone_number + '\'' +
                ", certification_key='" + certification_key + '\'' +
                '}';
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getCertification_key() {
        return certification_key;
    }

    public void setCertification_key(String certification_key) {
        this.certification_key = certification_key;
    }
}
