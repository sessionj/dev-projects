package egovframework.api.service;

import java.util.List;
import java.util.Map;

import egovframework.api.entity.FrontApiDefaultEntity;
import egovframework.api.entity.TraceEntity;
import egovframework.api.entity.UnsongEntity;


/**
 * @FileName  : TraceService.java
 * @Project   : kr.co.ds.api
 * @Date      : 2022. 10. 27.
 * @작성자     : shadow

 * @변경이력 		:
 * @프로그램 설명 : 
 */
public interface TraceService {
	
	/**
	 * 운송 대상 목록을 조회한다 | 운송 목록
	 * @param FrontApiDefaultEntity - 조회할 정보가 담긴 VO
	 * @return 화물추적 목록
	 * @exception Exception
	 */
	List<UnsongEntity> findUnsongList(FrontApiDefaultEntity entity) throws Exception;
	
	/**
	 * 화물 추적 대상 목록을 조회한다 | 운송 목록 list
	 * @param FrontApiDefaultEntity - 조회할 정보가 담긴 VO
	 * @return 화물추적 목록
	 * @exception Exception
	 */
	List<UnsongEntity> selectTraceList(FrontApiDefaultEntity entity) throws Exception;
	
	/**
	 * 운송정보 1건 조회 | 상세 정보 - 운송정보, 운송경로
	 * @param FrontApiDefaultEntity - 조회할 정보가 담긴 VO
	 * @return 운송정보 [화물추적시 사용]
	 * @exception Exception
	 */
	Map<String, Object> findSynthesis(FrontApiDefaultEntity entity) throws Exception; 
	
	/**
	 * 화물 추적 상세 정보를 조회한다 | 상세 정보 - 운송정보 1건
	 * @param FrontApiDefaultEntity - 조회할 정보가 담긴 VO
	 * @return 화물추적 목록
	 * @exception Exception
	 */
	UnsongEntity findUnsongArticle(FrontApiDefaultEntity entity) throws Exception;
	/**
	 * 화물 추적 상세 정보를 조회한다 | 상세 정보 - 화물추적 1건
	 * @param FrontApiDefaultEntity - 조회할 정보가 담긴 VO
	 * @return 화물추적 목록
	 * @exception Exception
	 */
	TraceEntity findTraceArticle(FrontApiDefaultEntity entity) throws Exception;
	
	
	
	/**
	 * 글 총 갯수를 조회한다.
	 * @param searchVO - 조회할 정보가 담긴 VO
	 * @return 글 총 갯수
	 * @exception
	 */
	int selectTraceTocCnt(FrontApiDefaultEntity entity);
}
