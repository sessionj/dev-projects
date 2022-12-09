package egovframework.api.service.mapper;

import java.util.List;

import org.egovframe.rte.psl.dataaccess.mapper.Mapper;

import egovframework.api.entity.FrontApiDefaultEntity;
import egovframework.api.entity.RequestDataEntity;

/**
 * 
 * @FileName  : TraceiMapper.java
 * @Project   : kr.co.ds.api
 * @Date      : 2022. 11. 28.
 * @작성자     : shadow

 * @변경이력 		: 2022.11.28
 * @프로그램 설명 : myBatis Mapper
 */
@Mapper("requestServiceMapper")
public interface RequestServiceMapper {

	/** 화물 운송실적 */
	List<RequestDataEntity> selectPerformanceCar(FrontApiDefaultEntity entity) throws Exception;
	
	/**
	 * 글 총 갯수를 조회한다.
	 * @param searchVO - 조회할 정보가 담긴 VO
	 * @return 글 총 갯수
	 * @exception
	 */
	int selectTraceTotCnt(FrontApiDefaultEntity entity);
	
}
