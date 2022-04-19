package kr.co.delivery_v1.action;

import android.content.Context;
import android.text.TextUtils;

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

        List<DeliveryModelView> deliveryModelViewArrayList = new ArrayList<DeliveryModelView>();
        deliveryModelViewArrayList = appDeliveryDatabase.basicDeliveryProcessDao().getDayList(deliveryModelView.getCreatdate());
        return deliveryModelViewArrayList;
    }

    public DeliveryModelView getDeliveryArticle(DeliveryModelView searchModel){

        DeliveryModelView deliveryModelView;
        deliveryModelView = new DeliveryModelView();

        if (TextUtils.isEmpty(searchModel.getBillno())){
            // null 이다
        }

        return deliveryModelView;

    }
}
