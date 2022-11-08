package egovframework.trace.service.impl;

import java.util.List;

import org.egovframe.rte.psl.dataaccess.mapper.Mapper;

import egovframework.trace.entity.FrontApiDefaultEntity;
import egovframework.trace.entity.TraceEntity;
import egovframework.trace.entity.UnsongEntity;


@Mapper("traceMapper")
public interface TraceiMapper {

	/**
	 * 운송 목록을 조회한다.
	 * @param searchMap - 조회할 정보가 담긴 Map
	 * @return 운송 목록
	 * @exception Exception
	 */
	List<UnsongEntity> selectTraceList(FrontApiDefaultEntity entity) throws Exception;
	/**
	 * 화물추적 상세정보 조회한다.
	 * @param entity - 조회할 정보가 담긴 EgovApiDefaultEntity
	 * @return 조회한 글
	 * @exception Exception
	 */
	TraceEntity selectTrace(FrontApiDefaultEntity entity) throws Exception;
	/**
	 * 글 총 갯수를 조회한다.
	 * @param searchVO - 조회할 정보가 담긴 VO
	 * @return 글 총 갯수
	 * @exception
	 */
	int selectTraceTotCnt(FrontApiDefaultEntity entity);
	
}
