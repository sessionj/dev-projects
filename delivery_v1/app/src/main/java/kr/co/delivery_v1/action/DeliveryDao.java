package kr.co.delivery_v1.action;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import kr.co.delivery_v1.db.delivery.AppDeliveryDatabase;
import kr.co.delivery_v1.models.DeliveryModelView;

public class DeliveryDao {

    AppDeliveryDatabase appDeliveryDatabase;
    public DeliveryDao(Context context){
        this.appDeliveryDatabase = AppDeliveryDatabase.getInstance(context);
    }
    // 배달 정보 조회 (생성일자, 코스별)
    public List<DeliveryModelView> getDeliveryList(DeliveryModelView deliveryModelView){
        Log.d("DeliveryDao ======== 검색 일자 : ", "" + deliveryModelView.getCreatdate().replaceAll("-", "") + ", "+deliveryModelView.getDeliverycourse());
        List<DeliveryModelView> deliveryModelViewArrayList = new ArrayList<DeliveryModelView>();
        deliveryModelViewArrayList = appDeliveryDatabase.basicDeliveryProcessDao().getDayList(deliveryModelView.getCreatdate().replaceAll("-", ""), deliveryModelView.getDeliverycourse());
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
        return appDeliveryDatabase.basicDeliveryProcessDao().getDayArticle(searchModel.getBillno());
    }
    // 배달 정보 전체 삭제
    public void applicationData_deleteAll(){
        appDeliveryDatabase.basicDeliveryProcessDao().applicationData_deleteAll(); ;
    }
    // 배달 정보 전체 조회
    public List<DeliveryModelView> getDeliveryList(){
        return  appDeliveryDatabase.basicDeliveryProcessDao().getDayList();
    }
    // 배달 정보 변경
    public void isDeliveryStatusChange(DeliveryModelView deliveryModelView){
        appDeliveryDatabase.basicDeliveryProcessDao().isDeliveryStatusChange(deliveryModelView.getBillno(), deliveryModelView.getDelivery_state());
    }
}
