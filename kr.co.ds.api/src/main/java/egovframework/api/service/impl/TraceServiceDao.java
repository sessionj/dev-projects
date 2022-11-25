package egovframework.api.service.impl;

import java.util.List;

import org.egovframe.rte.psl.dataaccess.EgovAbstractDAO;
import org.springframework.stereotype.Repository;

import egovframework.api.entity.FrontApiDefaultEntity;
import egovframework.api.entity.TraceEntity;


@Repository("traceDao")
public class TraceServiceDao extends EgovAbstractDAO{
	
	/**
	 * 운송 목록을 조회한다.
	 * @param searchMap - 조회할 정보가 담긴 Map
	 * @return 운송 목록
	 * @exception Exception
	 */
	public List<?> selectTraceList(FrontApiDefaultEntity entity) throws Exception{
		System.out.println("entity : " + entity.getSearchCondition());
		return list("traceDao.selectTraceList", entity);
	}
	/**
	 * 화물추적 상세정보 조회한다.
	 * @param entity - 조회할 정보가 담긴 EgovApiDefaultEntity
	 * @return 조회한 글
	 * @exception Exception
	 */
	public TraceEntity selectTrace(FrontApiDefaultEntity entity) throws Exception {
		return (TraceEntity) select ("", entity);
	}
	/**
	 * 글 총 갯수를 조회한다.
	 * @param searchMap - 조회할 정보가 담긴 Map
	 * @return 글 총 갯수
	 * @exception
	 */
	public int selectTraceTotCnt(FrontApiDefaultEntity entity) {
		return (Integer) select("", entity);
	}
	
}
