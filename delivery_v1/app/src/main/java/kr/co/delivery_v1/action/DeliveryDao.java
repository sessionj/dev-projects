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
    /**
     * room db에 저장된 목록 가져오기
     * 일자별,
     * @return
     */
    public List<DeliveryModelView> getDeliveryList(DeliveryModelView deliveryModelView){

        Log.d("DeliveryDao ======== 검색 일자 : ", "" + deliveryModelView.getCreatdate().replaceAll("-", "") + ", "+deliveryModelView.getDeliverycourse());
        List<DeliveryModelView> deliveryModelViewArrayList = new ArrayList<DeliveryModelView>();
        //deliveryModelViewArrayList = appDeliveryDatabase.basicDeliveryProcessDao().getDayList(deliveryModelView.getCreatdate(), deliveryModelView.getDeliverycourse());
        deliveryModelViewArrayList = appDeliveryDatabase.basicDeliveryProcessDao().getDayList(deliveryModelView.getCreatdate().replaceAll("-", ""), deliveryModelView.getDeliverycourse());
        if ( deliveryModelViewArrayList != null && deliveryModelViewArrayList.size() > 0){
            for ( int i=0; i < deliveryModelViewArrayList.size(); i++){
                Log.d("for : " , deliveryModelViewArrayList.get(i).getArrivalman());
                Log.d("for : " , deliveryModelViewArrayList.get(i).getAdress());
            }
        }
        return deliveryModelViewArrayList;
    }

    /**
     * 운송장 단일검색(상세 페이지)
     * @param searchModel
     * @return
     */
    public DeliveryModelView getDeliveryArticle(DeliveryModelView searchModel){
        return appDeliveryDatabase.basicDeliveryProcessDao().getDayArticle(searchModel.getBillno());

    }

    /**
     *
     */
    public void applicationData_deleteAll(){
        appDeliveryDatabase.basicDeliveryProcessDao().applicationData_deleteAll(); ;

    }

    public List<DeliveryModelView> getDeliveryList(){

        return  appDeliveryDatabase.basicDeliveryProcessDao().getDayList();
    }
}
