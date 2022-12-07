package egovframework.api.service;

import egovframework.api.entity.FrontApiDefaultEntity;


/**
 * @FileName  : UserAuthenticationService.java
 * @Project   : kr.co.ds.api
 * @Date      : 2022. 12. 5.
 * @작성자     : shadow

 * @변경이력 		:
 * @프로그램 설명 : 사용자 인증, 로그인 등 관리 서비스
 */
public interface UserAuthenticationService {
	
	/** 인증키 생성 -
	 *  요청된 전화번호로 문자 전송
	 *  local에서 인증키 체크 - 로그인처리
	 */
	Integer authenticationKeyGeneration(FrontApiDefaultEntity entity) throws Exception;
	
	/**
	 * 글 총 갯수를 조회한다.
	 * @param searchVO - 조회할 정보가 담긴 VO
	 * @return 글 총 갯수
	 * @exception
	 */
	int selectTraceTocCnt(FrontApiDefaultEntity entity);
}
