package kr.co.delivery_v1.db.delivery;

import static androidx.room.OnConflictStrategy.REPLACE;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.ArrayList;
import java.util.List;

import kr.co.delivery_v1.models.DeliveryModelView;
import kr.co.delivery_v1.models.DeliveryRequestModelView;


@Dao
public interface BasicDeliveryProcessDao {

    // 배달 정보 저장
    @Insert(onConflict = REPLACE)
    void applicationData_insert( DeliveryModelView deliveryModelView);

    // 배달 정보 전체 조회
    @Query("SELECT * FROM tb_delivery")
    List<DeliveryModelView> getDayList();

    // 배달 정보 조회 (생성일자, 코스별)
    @Query("SELECT * FROM tb_delivery  WHERE creatdate = :createDt AND deliverycourse =:deliveryCourse ORDER BY delivery_state ASC" )
    List<DeliveryModelView> getDayList(String createDt, String deliveryCourse);

    // 배달 정보 조회 (운송장 단일정보)
    @Query("SELECT * FROM tb_delivery WHERE  billno = :billNo")
    DeliveryModelView getDayArticle(String billNo);

    // 배달 정보 전체 삭제 (5일)
    @Query("DELETE FROM tb_delivery where creatdate > :sysdate")
    void isDeliveryDel(String sysdate);

    // 배달 정보 변경 (배달 완료 처리)
    @Query("UPDATE tb_delivery SET delivery_state =:deliveryStatus WHERE billno = :billNo ")
    void isDeliveryStatusChange(String billNo, String deliveryStatus);

}


/*@Query("SELECT * FROM tb_delivery  where creatdate = :createDt and deliverycourse = :deliveryCourse" )
    List<DeliveryModelView> getDayList(String createDt, String deliveryCourse);*/


