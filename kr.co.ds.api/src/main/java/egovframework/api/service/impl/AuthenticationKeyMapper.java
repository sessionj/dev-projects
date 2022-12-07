package egovframework.api.service.impl;

import org.egovframe.rte.psl.dataaccess.mapper.Mapper;

import egovframework.api.entity.FrontApiDefaultEntity;
import egovframework.api.entity.SmsEntity;

/**
 * 
 * @FileName  : TraceiMapper.java
 * @Project   : kr.co.ds.api
 * @Date      : 2022. 11. 28.
 * @작성자     : shadow

 * @변경이력 		: 2022.11.28
 * @프로그램 설명 : myBatis Mapper
 */
@Mapper("authenticationKeyMapper")
public interface AuthenticationKeyMapper {

	/** 생성된 key, phone-number 저장 */
	void insertKeyGeneration(FrontApiDefaultEntity entity) throws Exception;
	/** 문자 발송 */
	void sendingSmsProcess(SmsEntity entity) throws Exception;
	/**
	 * 글 총 갯수를 조회한다.
	 * @param searchVO - 조회할 정보가 담긴 VO
	 * @return 글 총 갯수
	 * @exception
	 */
	int selectTraceTotCnt(FrontApiDefaultEntity entity);
	
}
