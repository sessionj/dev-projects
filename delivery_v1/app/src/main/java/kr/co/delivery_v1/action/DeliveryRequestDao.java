package kr.co.delivery_v1.action;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import kr.co.delivery_v1.db.delivery.BasicDeliveryDatabase;
import kr.co.delivery_v1.db.delivery.BasicDeliveryRequestDatabase;
import kr.co.delivery_v1.models.DeliveryModelView;
import kr.co.delivery_v1.models.DeliveryRequestModelView;

public class DeliveryRequestDao {

    BasicDeliveryRequestDatabase basicDeliveryRequestDatabase;
    public DeliveryRequestDao(Context context){
        this.basicDeliveryRequestDatabase = BasicDeliveryRequestDatabase.getInstance(context);
    }
    // 자료 수신 이력 삭제 (5일)
    public void isDeliveryRequestDel(String sysdate){
        basicDeliveryRequestDatabase.basicDeliveryRequestProcessDao().isDeliveryRequestDel(sysdate);
    }
    // 자료 수신 이력 조회
    public List<DeliveryRequestModelView> getDeliveryRequestList(){
        return basicDeliveryRequestDatabase.basicDeliveryRequestProcessDao().getDeliveryRequestList();
    }
    // 자료 수신 이력 쌓기
    public void isDeliveryRequestAdd(DeliveryRequestModelView deliveryRequestModelView) {
        basicDeliveryRequestDatabase.basicDeliveryRequestProcessDao().isDeliveryRequestAdd(deliveryRequestModelView);
    }
}
