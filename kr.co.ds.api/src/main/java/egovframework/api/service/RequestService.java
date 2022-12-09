package egovframework.api.service;

import java.util.List;

import egovframework.api.entity.FrontApiDefaultEntity;
import egovframework.api.entity.RequestDataEntity;


/**
 * @FileName  : CodeManagementService.java
 * @Project   : kr.co.ds.api
 * @Date      : 2022. 10. 27.
 * @작성자     : shadow

 * @변경이력 		:
 * @프로그램 설명 : 요구 사항 처리 질의 응답 
 * 				[1.화물운송실적 자료, 2.영업소별 발송내역 등.. ]
 */
public interface RequestService {
	
	/**
	 * @param FrontApiDefaultEntity
	 * @return 코드 목록 [Master, Agency, Car]
	 * @exception Exception
	 */
	
	List<RequestDataEntity> findCodeList(FrontApiDefaultEntity entity) throws Exception;
	
	/**
	 * 글 총 갯수를 조회한다.
	 * @param searchVO - 조회할 정보가 담긴 VO
	 * @return 글 총 갯수
	 * @exception
	 */
	int selectTraceTocCnt(FrontApiDefaultEntity entity);
}
