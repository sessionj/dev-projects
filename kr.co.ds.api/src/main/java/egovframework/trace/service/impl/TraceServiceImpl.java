package egovframework.trace.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.egovframe.rte.fdl.cmmn.EgovAbstractServiceImpl;
import org.egovframe.rte.fdl.idgnr.EgovIdGnrService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import egovframework.trace.entity.FrontApiDefaultEntity;
import egovframework.trace.entity.TraceEntity;
import egovframework.trace.entity.UnsongEntity;
import egovframework.trace.service.TraceService;


@Service("traceService")
public class TraceServiceImpl extends EgovAbstractServiceImpl implements TraceService{

	private static final Logger logger = LoggerFactory.getLogger(TraceServiceImpl.class);
	
	/** apiDAO */
	// TODO ibatis 사용
	@Resource(name = "traceDao")
	private TraceServiceDao traceDao;
	
	// TODO mybatis 사용
	@Resource(name ="traceMapper")
	private TraceiMapper traceMapper;
	
	/** ID Generation */
	@Resource(name = "egovIdGnrService")
	private EgovIdGnrService egovIdGnrService;
	
	/**
	 * 화물 추적 대상 목록을 조회한다 | 운송 목록
	 * @param FrontApiDefaultEntity - 조회할 정보가 담긴 VO
	 * @return 화물추적 목록
	 * @exception Exception
	 */
	@Override
	public List<UnsongEntity> selectHistoryList(FrontApiDefaultEntity entity) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("EgovApiServiceImpl =======> selectTraceList");
		//return (List<TraceEntity>) traceDao.selectTraceList(entity);
		return traceMapper.selectTraceList(entity);
	}
	/**
	 * 화물 추적 상세 정보를 조회한다 | 상세 정보
	 * @param FrontApiDefaultEntity - 조회할 정보가 담긴 VO
	 * @return 화물추적 목록
	 * @exception Exception
	 */
	@Override
	public TraceEntity selectTrace(FrontApiDefaultEntity entity) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	/**
	 * 글 총 갯수를 조회한다.
	 * @param searchMap - 조회할 정보가 담긴 Map
	 * @return 글 총 갯수
	 * @exception
	 */
	@Override
	public int selectTraceTocCnt(FrontApiDefaultEntity entity) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public List<UnsongEntity> selectTraceList(FrontApiDefaultEntity entity) throws Exception {
		
		return (List<UnsongEntity>) traceDao.selectTraceList(entity);
	}
	
}
