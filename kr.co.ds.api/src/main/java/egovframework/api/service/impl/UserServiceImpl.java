package egovframework.api.service.impl;

import javax.annotation.Resource;

import org.egovframe.rte.fdl.cmmn.EgovAbstractServiceImpl;
import org.egovframe.rte.fdl.idgnr.EgovIdGnrService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import egovframework.api.entity.FrontApiDefaultEntity;
import egovframework.api.entity.SmsEntity;
import egovframework.api.service.UserService;
import egovframework.api.service.mapper.UserServiceMapper;
import egovframework.common.util.AuthenticationKeyGeneration;
import egovframework.common.util.StringCommonLibray;


@Service("userService")
public class UserServiceImpl extends EgovAbstractServiceImpl implements UserService{

	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
	
	// TODO mybatis 사용
	@Resource(name ="userServiceMapper")
	private UserServiceMapper userServiceMapper;
	
	/** ID Generation */
	@Resource(name = "egovIdGnrService")
	private EgovIdGnrService egovIdGnrService;


	/** KEY Generation */
	/**
	 *  전화번호를 입력받아 인증키 return
	 */
	@Override
	public Integer authenticationKeyGeneration(FrontApiDefaultEntity entity) throws Exception {
		
		// 휴대폰에 인증번호 전송
		// url 요청한 인증휴대폰에도 마찬가지로 인증번호 return
		// 사용자는 인증번호를 입력후 인증 시도 ==> 던져준 번호와 맞는지 체크 맞으면
		// 전화번호 DB 저장
		int key = AuthenticationKeyGeneration.generateAuthNo3();
		entity.setSearchCondition(String.valueOf(key));
		
		// 문자 발신
		SmsEntity smsEntity = null;
		smsEntity = new SmsEntity();
		smsEntity.setMsg_contents(StringCommonLibray.CODE5.getMsg()+key);
		smsEntity.setDest_no(entity.getSearchKeyword());
		userServiceMapper.sendingSmsProcess(smsEntity);
		
		return key;
	}

	@Override
	public int selectTraceTocCnt(FrontApiDefaultEntity entity) {
		// TODO Auto-generated method stub
		return 0;
	}
	
}
