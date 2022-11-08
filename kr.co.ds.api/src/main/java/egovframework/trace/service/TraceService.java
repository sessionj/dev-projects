package egovframework.trace.service;

import java.util.List;

import egovframework.trace.entity.FrontApiDefaultEntity;
import egovframework.trace.entity.TraceEntity;
import egovframework.trace.entity.UnsongEntity;


/**
 * @FileName  : EgovApiService.java
 * @Project   : kr.co.ds.api
 * @Date      : 2022. 10. 27.
 * @작성자     : shadow

 * @변경이력 		:
 * @프로그램 설명 : 
 */
public interface TraceService {
	
	/**
	 * 화물 추적 대상 목록을 조회한다 | 운송 목록
	 * @param FrontApiDefaultEntity - 조회할 정보가 담긴 VO
	 * @return 화물추적 목록
	 * @exception Exception
	 */
	List<UnsongEntity> selectHistoryList(FrontApiDefaultEntity entity) throws Exception;
	/**
	 * 화물 추적 상세 정보를 조회한다 | 상세 정보
	 * @param FrontApiDefaultEntity - 조회할 정보가 담긴 VO
	 * @return 화물추적 목록
	 * @exception Exception
	 */
	TraceEntity selectTrace(FrontApiDefaultEntity entity) throws Exception;
	/**
	 * 글 총 갯수를 조회한다.
	 * @param searchVO - 조회할 정보가 담긴 VO
	 * @return 글 총 갯수
	 * @exception
	 */
	int selectTraceTocCnt(FrontApiDefaultEntity entity);
	

	/**
	 * 화물 추적 대상 목록을 조회한다 | 운송 목록
	 * @param FrontApiDefaultEntity - 조회할 정보가 담긴 VO
	 * @return 화물추적 목록
	 * @exception Exception
	 */
	List<UnsongEntity> selectTraceList(FrontApiDefaultEntity entity) throws Exception;
	
}
