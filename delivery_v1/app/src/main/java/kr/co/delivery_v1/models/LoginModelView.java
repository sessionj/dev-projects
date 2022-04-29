package kr.co.delivery_v1.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

import kr.co.delivery_v1.comm.Label;

@Entity(tableName = Label.DELIVERY_BASE_ROOM_PROFILE_DATABASE_NAME)
public class LoginModelView implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "phonenumber")
    private String phonenumber;

    @ColumnInfo(name = "agencycode")
    private String agencycode;

    @ColumnInfo(name = "deliverycourse")
    private String deliverycourse;

    @Override
    public String toString() {
        return "LoginEntity{" +
                "id=" + id +
                ", phonenumber='" + phonenumber + '\'' +
                ", agencycode='" + agencycode + '\'' +
                ", deliverycourse='" + deliverycourse + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getAgencycode() {
        return agencycode;
    }

    public void setAgencycode(String agencycode) {
        this.agencycode = agencycode;
    }

    public String getDeliverycourse() {
        return deliverycourse;
    }

    public void setDeliverycourse(String deliverycourse) {
        this.deliverycourse = deliverycourse;
    }
}
