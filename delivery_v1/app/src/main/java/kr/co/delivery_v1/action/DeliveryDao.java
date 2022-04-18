package kr.co.delivery_v1.action;

import java.util.ArrayList;

import kr.co.delivery_v1.models.DeliveryModelView;

public class DeliveryDao {

    ArrayList<DeliveryModelView> deliveryModelViewArrayList;

    /**
     * room db에 저장된 목록 가져오기
     * 일자별,
     * @return
     */
    public ArrayList<DeliveryModelView> getDeliveryList(){

        deliveryModelViewArrayList = new ArrayList<DeliveryModelView>();

        return deliveryModelViewArrayList;

    }
}
