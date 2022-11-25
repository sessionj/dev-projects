package egovframework.api.service.impl;

import java.util.List;

import org.egovframe.rte.psl.dataaccess.mapper.Mapper;

import egovframework.api.entity.FrontApiDefaultEntity;
import egovframework.api.entity.TraceEntity;
import egovframework.api.entity.UnsongEntity;


@Mapper("traceMapper")
public interface TraceiMapper {

	/**
	 * 운송 목록을 조회한다.
	 * @param searchMap - 조회할 정보가 담긴 Map
	 * @return 운송 목록
	 * @exception Exception
	 */
	List<UnsongEntity> findUnsongList(FrontApiDefaultEntity entity) throws Exception;
	/**
	 * 화물추적 상세정보 조회한다.(운송)
	 * @param entity - 조회할 정보가 담긴 EgovApiDefaultEntity
	 * @return 조회한 글
	 * @exception Exception
	 */
	UnsongEntity findUnsongArticle(FrontApiDefaultEntity entity) throws Exception;
	
	/**
	 * 화물추적 상세정보 조회한다.(화물 추적)
	 * @param entity - 조회할 정보가 담긴 EgovApiDefaultEntity
	 * @return 조회한 글
	 * @exception Exception
	 */
	TraceEntity findTraceArticle(FrontApiDefaultEntity entity) throws Exception;
	
	/**
	 * 글 총 갯수를 조회한다.
	 * @param searchVO - 조회할 정보가 담긴 VO
	 * @return 글 총 갯수
	 * @exception
	 */
	int selectTraceTotCnt(FrontApiDefaultEntity entity);
	
	
}
