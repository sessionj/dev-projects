package egovframework.api.service;

import java.util.List;

import egovframework.api.entity.AgencyCodeEntity;
import egovframework.api.entity.FrontApiDefaultEntity;
import egovframework.api.entity.MasterCodeEntity;


/**
 * @FileName  : EgovApiService.java
 * @Project   : kr.co.ds.api
 * @Date      : 2022. 10. 27.
 * @작성자     : shadow

 * @변경이력 		:
 * @프로그램 설명 : 대신 코드 관리 서비스
 */
public interface CodeManagementService {
	
	/**
	 * @param FrontApiDefaultEntity
	 * @return 코드 목록 [Master, Agency, Car]
	 * @exception Exception
	 */
	List<MasterCodeEntity> findCodeList(FrontApiDefaultEntity entity) throws Exception;
	
	/**
	 * @param FrontApiDefaultEntity
	 * @return 코드 상세정보[Master, Agency, Car]
	 * @exception Exception
	 */
	MasterCodeEntity findCodeArticle(FrontApiDefaultEntity entity) throws Exception;
	
	/**
	 * @param FrontApiDefaultEntity
	 * @return 코드 목록[영업소코드]
	 * @exception Exception
	 */
	List<AgencyCodeEntity> findAgencyCodeList(FrontApiDefaultEntity entity) throws Exception;
	/**
	 * @param FrontApiDefaultEntity
	 * @return 코드 등록[영업소 코드]
	 * @exception Exception
	 */
	boolean isCodeInsert(AgencyCodeEntity entity) throws Exception;
	/**
	 * @param FrontApiDefaultEntity
	 * @return 코드 등록[마스터코드]
	 * @exception Exception
	 */
	boolean isCodeInsert(MasterCodeEntity entity) throws Exception;
	/**
	 * @param FrontApiDefaultEntity
	 * @return 코드 삭제[영업소코드]
	 * @exception Exception
	 */
	boolean isCodeDelete(AgencyCodeEntity entity) throws Exception;
	/**
	 * @param FrontApiDefaultEntity
	 * @return 코드 삭제[마스터코드]
	 * @exception Exception
	 */
	boolean isCodeDelete(MasterCodeEntity entity) throws Exception;
	/**
	 * @param FrontApiDefaultEntity
	 * @return 코드 수정[영업소코드]
	 * @exception Exception
	 */
	boolean isCodeModify(AgencyCodeEntity entity) throws Exception;
	/**
	 * @param FrontApiDefaultEntity
	 * @return 코드 수정[마스터코드]
	 * @exception Exception
	 */
	boolean isCodeModify(MasterCodeEntity entity) throws Exception;
	
	/**
	 * 글 총 갯수를 조회한다.
	 * @param searchVO - 조회할 정보가 담긴 VO
	 * @return 글 총 갯수
	 * @exception
	 */
	int selectTraceTocCnt(FrontApiDefaultEntity entity);
}
