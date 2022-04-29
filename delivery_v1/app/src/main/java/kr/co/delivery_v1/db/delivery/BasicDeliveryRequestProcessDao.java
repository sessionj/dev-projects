package kr.co.delivery_v1.db.delivery;

import static androidx.room.OnConflictStrategy.REPLACE;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import kr.co.delivery_v1.models.DeliveryModelView;
import kr.co.delivery_v1.models.DeliveryRequestModelView;


@Dao
public interface BasicDeliveryRequestProcessDao {

    // 자료 수신 이력 쌓기
    @Insert(onConflict = REPLACE)
    void isDeliveryRequestAdd(DeliveryRequestModelView deliveryRequestModelView);

    // 자료 수신 이력 삭제 (5일)
    @Query("DELETE FROM tb_delivery_request WHERE reqdate > :sysdate ")
    void isDeliveryRequestDel(String sysdate);

    // 자료 수신 이력 조회
    @Query("SELECT * FROM tb_delivery_request")
    List<DeliveryRequestModelView> getDeliveryRequestList();
}


