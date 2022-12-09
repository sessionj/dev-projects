package egovframework.common.util.sms;

import javax.annotation.Resource;

import egovframework.api.entity.SmsEntity;
import egovframework.api.service.UserService;

public class SmsWorkProcess extends SmsTemplate{

	/** EgovApiService */
	@Resource(name = "authenticationKeyService")
	private UserService authenticationKeyService;
	
	@Override
	public void jobProcess(SmsEntity entity) throws Exception {
		System.out.println(".. send sms : " + entity.getDest_no() + "("+entity.getCustomernm()+") 님에게 문자 발신을 시작 == ");
		System.out.println("=======> content : " + entity.getMsg_contents());
		System.out.println("=======> dest_no : " + entity.getDest_no());
		//authenticationKeyService.sendingSmsProcess(entity);
		System.out.println(".. send sms : " + entity.getDest_no() + "("+entity.getCustomernm()+") 님에게 문자 발신을 하였습니다.");
	}
	
}
