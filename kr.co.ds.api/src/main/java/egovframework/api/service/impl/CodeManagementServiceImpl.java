package egovframework.api.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.egovframe.rte.fdl.cmmn.EgovAbstractServiceImpl;
import org.egovframe.rte.fdl.idgnr.EgovIdGnrService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import egovframework.api.entity.AgencyCodeEntity;
import egovframework.api.entity.FrontApiDefaultEntity;
import egovframework.api.entity.MasterCodeEntity;
import egovframework.api.service.CodeManagementService;


@Service("codeManagementService")
public class CodeManagementServiceImpl extends EgovAbstractServiceImpl implements CodeManagementService{

	private static final Logger logger = LoggerFactory.getLogger(CodeManagementServiceImpl.class);
	
	// TODO mybatis 사용
	@Resource(name ="codeManagementMapper")
	private CodeManagementMapper codeManagementMapper;
	
	/** ID Generation */
	@Resource(name = "egovIdGnrService")
	private EgovIdGnrService egovIdGnrService;
	
	@Override
	public List<MasterCodeEntity> findMastCodeList(FrontApiDefaultEntity entity) throws Exception {
		// TODO Auto-generated method stub
		return codeManagementMapper.findMastCodeList(entity);
	}

	@Override
	public List<AgencyCodeEntity> findAgencyCodeList(FrontApiDefaultEntity entity) throws Exception {
		// TODO Auto-generated method stub
		return codeManagementMapper.findAgencyCodeList(entity);
	}

	@Override
	public boolean isCodeInsert(AgencyCodeEntity entity) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean isCodeInsert(MasterCodeEntity entity) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean isCodeDelete(AgencyCodeEntity entity) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean isCodeDelete(MasterCodeEntity entity) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean isCodeModify(AgencyCodeEntity entity) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean isCodeModify(MasterCodeEntity entity) throws Exception {
		// TODO Auto-generated method stub
		return false;
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
	
}
