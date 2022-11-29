package egovframework.api.service.impl;

import java.util.List;

import org.egovframe.rte.psl.dataaccess.mapper.Mapper;

import egovframework.api.entity.AgencyCodeEntity;
import egovframework.api.entity.FrontApiDefaultEntity;
import egovframework.api.entity.MasterCodeEntity;

/**
 * 
 * @FileName  : TraceiMapper.java
 * @Project   : kr.co.ds.api
 * @Date      : 2022. 11. 28.
 * @작성자     : shadow

 * @변경이력 		: 2022.11.28
 * @프로그램 설명 : myBatis Mapper
 */
@Mapper("codeManagementMapper")
public interface CodeManagementMapper {

	/**
	 * 운송 목록을 조회한다.
	 * @param searchMap - 조회할 정보가 담긴 Map
	 * @return 운송 목록
	 * @exception Exception
	 */
	List<MasterCodeEntity> findMastCodeList(FrontApiDefaultEntity entity) throws Exception;
	
	List<AgencyCodeEntity> findAgencyCodeList(FrontApiDefaultEntity entity) throws Exception;
	/**
	 * 글 총 갯수를 조회한다.
	 * @param searchVO - 조회할 정보가 담긴 VO
	 * @return 글 총 갯수
	 * @exception
	 */
	int selectTraceTotCnt(FrontApiDefaultEntity entity);
	
	
}
