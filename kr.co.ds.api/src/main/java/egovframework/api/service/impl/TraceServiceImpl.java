package egovframework.api.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.egovframe.rte.fdl.cmmn.EgovAbstractServiceImpl;
import org.egovframe.rte.fdl.idgnr.EgovIdGnrService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import egovframework.api.entity.FrontApiDefaultEntity;
import egovframework.api.entity.TraceEntity;
import egovframework.api.entity.UnsongEntity;
import egovframework.api.service.TraceService;
import egovframework.api.service.mapper.TraceiServiceMapper;


@Service("traceService")
public class TraceServiceImpl extends EgovAbstractServiceImpl implements TraceService{

	private static final Logger logger = LoggerFactory.getLogger(TraceServiceImpl.class);
	
	/** apiDAO */
	// TODO ibatis 사용
	/*
	 * @Resource(name = "traceDao") private TraceServiceDao traceDao;
	 */
	
	// TODO mybatis 사용
	@Resource(name ="traceiServiceMapper")
	private TraceiServiceMapper traceiServiceMapper;
	
	/** ID Generation */
	@Resource(name = "egovIdGnrService")
	private EgovIdGnrService egovIdGnrService;
	
	/**
	 * 운송정보 목록을 조회한다 | 목록
	 * @param FrontApiDefaultEntity - 조회할 정보가 담긴 VO
	 * @return 화물추적 목록
	 * @exception Exception
	 */
	@Override
	public List<UnsongEntity> findUnsongList(FrontApiDefaultEntity entity) throws Exception {
		logger.debug(null, this.getClass(), entity);
		return traceiServiceMapper.findUnsongList(entity);
	}
	
	/**
	 * 운송정보 1건 조회 | 상세 정보 - 운송정보, 운송경로
	 * @param FrontApiDefaultEntity - 조회할 정보가 담긴 VO
	 * @return 운송정보 [화물추적시 사용]
	 * @exception Exception
	 */
	@Override
	public Map<String, Object> findSynthesis(FrontApiDefaultEntity entity) throws Exception {
		// TODO Auto-generated method stub
		Map<String, Object> map = null;
		map = new HashMap<String, Object>();
		map.put("unsong", traceiServiceMapper.findUnsongArticle(entity));
		map.put("trace", traceiServiceMapper.findTraceArticle(entity));
		return map;
	}
	
	/**
	 * 운송정보 1건 조회 | 상세 정보
	 * @param FrontApiDefaultEntity - 조회할 정보가 담긴 VO
	 * @return 운송정보
	 * @exception Exception
	 */
	@Override
	public UnsongEntity findUnsongArticle(FrontApiDefaultEntity entity) throws Exception {
		// TODO Auto-generated method stub
		logger.debug(null, this.getClass(), entity);
		return traceiServiceMapper.findUnsongArticle(entity);
	}
	/**
	 * 화물추적 1건 조회 | 상세 정보
	 * @param FrontApiDefaultEntity - 조회할 정보가 담긴 VO
	 * @return 화물추적 정보
	 * @exception Exception
	 */
	@Override
	public TraceEntity findTraceArticle(FrontApiDefaultEntity entity) throws Exception {
		// TODO Auto-generated method stub
		return traceiServiceMapper.findTraceArticle(entity);
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
		
		//return (List<UnsongEntity>) traceDao.selectTraceList(entity);
		return null;
	}
	
}
