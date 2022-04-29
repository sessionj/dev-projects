package kr.co.delivery_v1.models;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import kr.co.delivery_v1.comm.Label;

/**
 * delivery room database 
 */
@Entity(tableName = Label.DELIVERY_BASE_ROOM_DELIVERY_REQ_DATABASE_NAME)
public class DeliveryRequestModelView {
    @NonNull
    @PrimaryKey()
    private String reqdate;

    @ColumnInfo(name = "deliverycourse")        private String deliverycourse;
    @ColumnInfo(name = "deliverycoursenm")      private String deliverycoursenm;
    @ColumnInfo(name = "requestdate")           private String requestdate;
    @ColumnInfo(name = "delivery_count")        private int delivery_count;
    @ColumnInfo(name = "combination_key")        private int combination_key;

    @Override
    public String toString() {
        return "DeliveryRequestModelView{" +
                "reqdate='" + reqdate + '\'' +
                ", deliverycourse='" + deliverycourse + '\'' +
                ", deliverycoursenm='" + deliverycoursenm + '\'' +
                ", requestdate='" + requestdate + '\'' +
                ", delivery_count=" + delivery_count +
                ", combination_key=" + combination_key +
                '}';
    }

    @NonNull
    public String getReqdate() {
        return reqdate;
    }

    public void setReqdate(@NonNull String reqdate) {
        this.reqdate = reqdate;
    }

    public String getDeliverycourse() {
        return deliverycourse;
    }

    public void setDeliverycourse(String deliverycourse) {
        this.deliverycourse = deliverycourse;
    }

    public String getDeliverycoursenm() {
        return deliverycoursenm;
    }

    public void setDeliverycoursenm(String deliverycoursenm) {
        this.deliverycoursenm = deliverycoursenm;
    }

    public String getRequestdate() {
        return requestdate;
    }

    public void setRequestdate(String requestdate) {
        this.requestdate = requestdate;
    }

    public int getDelivery_count() {
        return delivery_count;
    }

    public void setDelivery_count(int delivery_count) {
        this.delivery_count = delivery_count;
    }

    public int getCombination_key() {
        return combination_key;
    }

    public void setCombination_key(int combination_key) {
        this.combination_key = combination_key;
    }
}
