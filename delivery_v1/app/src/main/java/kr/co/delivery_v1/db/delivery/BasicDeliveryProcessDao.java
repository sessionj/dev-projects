package kr.co.delivery_v1.db.delivery;

import static androidx.room.OnConflictStrategy.REPLACE;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.ArrayList;
import java.util.List;

import kr.co.delivery_v1.models.DeliveryModelView;


@Dao
public interface BasicDeliveryProcessDao {

    // 배달 정보 저장
    @Insert(onConflict = REPLACE)
    void applicationData_insert( DeliveryModelView deliveryModelView);

    // 배달 정보 전체 조회
    @Query("SELECT * FROM tb_delivery")
    List<DeliveryModelView> getDayList();

    // 배달 정보 조회 (생성일자, 코스별)
    @Query("SELECT * FROM tb_delivery  WHERE creatdate = :createDt AND deliverycourse =:deliveryCourse ORDER BY deliverycourse DESC" )
    List<DeliveryModelView> getDayList(String createDt, String deliveryCourse);


    // 배달 정보 조회 (운송장 단일정보)
    @Query("SELECT * FROM tb_delivery WHERE  billno = :billNo")
    DeliveryModelView getDayArticle(String billNo);

    // 배달 정보 전체 삭제
    @Query("DELETE FROM tb_delivery")
    void applicationData_deleteAll();

    // 배달 정보 변경
    @Query("UPDATE tb_delivery "
         + "SET delivery_state =:deliveryStatus "
         + "WHERE billno = :billNo ")
    void isUpdateDelivery(String billNo, String deliveryStatus);
}


/*@Query("SELECT * FROM tb_delivery  where creatdate = :createDt and deliverycourse = :deliveryCourse" )
    List<DeliveryModelView> getDayList(String createDt, String deliveryCourse);*/


