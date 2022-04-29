package kr.co.delivery_v1.action;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import kr.co.delivery_v1.db.delivery.BasicDeliveryDatabase;
import kr.co.delivery_v1.models.DeliveryModelView;
import kr.co.delivery_v1.models.DeliveryRequestModelView;

public class DeliveryDao {

    BasicDeliveryDatabase basicDeliveryDatabase;
    public DeliveryDao(Context context){
        this.basicDeliveryDatabase = BasicDeliveryDatabase.getInstance(context);
    }
    // 배달 정보 조회 (생성일자, 코스별)
    public List<DeliveryModelView> getDeliveryList(DeliveryModelView deliveryModelView){
        Log.d("DeliveryDao ======== 검색 일자 : ", "" + deliveryModelView.getCreatdate().replaceAll("-", "") + ", "+deliveryModelView.getDeliverycourse());
        List<DeliveryModelView> deliveryModelViewArrayList = new ArrayList<DeliveryModelView>();
        deliveryModelViewArrayList = basicDeliveryDatabase.basicDeliveryProcessDao().getDayList(deliveryModelView.getCreatdate().replaceAll("-", ""), deliveryModelView.getDeliverycourse());
        if ( deliveryModelViewArrayList != null && deliveryModelViewArrayList.size() > 0){
            for ( int i=0; i < deliveryModelViewArrayList.size(); i++){
                Log.d("for : " , deliveryModelViewArrayList.get(i).getArrivalman());
                Log.d("for : " , deliveryModelViewArrayList.get(i).getAdress());
            }
        }
        return deliveryModelViewArrayList;
    }
    // 배달 정보 조회 (운송장 단일정보)
    public DeliveryModelView getDeliveryArticle(DeliveryModelView searchModel){
        return basicDeliveryDatabase.basicDeliveryProcessDao().getDayArticle(searchModel.getBillno());
    }
    // 배달 정보 전체 삭제
    public void isDeliveryDel(String sysdate){
        basicDeliveryDatabase.basicDeliveryProcessDao().isDeliveryDel(sysdate);
    }
    // 배달 정보 전체 조회
    public List<DeliveryModelView> getDeliveryList(){
        return  basicDeliveryDatabase.basicDeliveryProcessDao().getDayList();
    }
    // 배달 정보 변경
    public void isDeliveryStatusChange(DeliveryModelView deliveryModelView){
        basicDeliveryDatabase.basicDeliveryProcessDao().isDeliveryStatusChange(deliveryModelView.getBillno(), deliveryModelView.getDelivery_state());
    }
}
