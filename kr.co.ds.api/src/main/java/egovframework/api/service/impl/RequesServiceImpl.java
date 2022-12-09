package egovframework.api.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.egovframe.rte.fdl.cmmn.EgovAbstractServiceImpl;
import org.egovframe.rte.fdl.idgnr.EgovIdGnrService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import egovframework.api.entity.FrontApiDefaultEntity;
import egovframework.api.entity.RequestDataEntity;
import egovframework.api.service.RequestService;
import egovframework.api.service.mapper.RequestServiceMapper;


@Service("requestService")
public class RequesServiceImpl extends EgovAbstractServiceImpl implements RequestService{

	private static final Logger logger = LoggerFactory.getLogger(RequesServiceImpl.class);
	
	// TODO mybatis 사용
	@Resource(name ="requestServiceMapper")
	private RequestServiceMapper requestServiceMapper;
	
	/** ID Generation */
	@Resource(name = "egovIdGnrService")
	private EgovIdGnrService egovIdGnrService;


	@Override
	public List<RequestDataEntity> findCodeList(FrontApiDefaultEntity entity) throws Exception {
		// TODO Auto-generated method stub
		return requestServiceMapper.selectPerformanceCar(entity);
	}



	@Override
	public int selectTraceTocCnt(FrontApiDefaultEntity entity) {
		// TODO Auto-generated method stub
		return 0;
	}
	
}
