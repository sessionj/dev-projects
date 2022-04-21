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

    /**
     * db insert
     * 전화번호, 영업소코드, 배달코스
     * @param deliveryModelView
     */
    @Insert(onConflict = REPLACE)
    void applicationData_insert( DeliveryModelView deliveryModelView);

    /**
     * db select
     * @return
     */
    @Query("SELECT * FROM tb_delivery")
    List<DeliveryModelView> getDayList();

    /**
     * db select
     * @return
     */

    /*@Query("SELECT * FROM tb_delivery  where creatdate = :createDt and deliverycourse = :deliveryCourse" )
    List<DeliveryModelView> getDayList(String createDt, String deliveryCourse);*/

    //임시
    @Query("SELECT * FROM tb_delivery  where creatdate = :createDt order by creatdate desc" )
    List<DeliveryModelView> getDayList(String createDt);

    /**
     * db select
     * @return
     */
    @Query("SELECT * FROM tb_delivery where billno = :billNo")
    DeliveryModelView getDayArticle(String billNo);

    /**
     * 전체 데이터 삭제
     */
    @Query("DELETE FROM tb_delivery")
    void applicationData_deleteAll();

}
