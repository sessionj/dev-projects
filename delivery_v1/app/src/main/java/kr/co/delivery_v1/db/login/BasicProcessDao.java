package kr.co.delivery_v1.db.login;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import kr.co.delivery_v1.models.LoginModelView;
import static androidx.room.OnConflictStrategy.REPLACE;

import java.util.List;

@Dao
public interface BasicProcessDao {

    /**
     * db insert
     * 전화번호, 영업소코드, 배달코스
     * @param loginModelView
     */
    @Insert(onConflict = REPLACE)
    void applicationData_insert(LoginModelView loginModelView);

    /**
     * db select
     * @return
     */
    @Query("SELECT * FROM tb_profile")
    List<LoginModelView> getAll();

    /**
     * 전체 데이터 삭제
     */
    @Query("DELETE FROM tb_profile")
    void applicationData_deleteAll();

}
