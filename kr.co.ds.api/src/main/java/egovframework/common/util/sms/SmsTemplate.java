package egovframework.common.util.sms;

import egovframework.api.entity.SmsEntity;

/**
 * 
 * @FileName  : SmsTemplate.java
 * @Project   : kr.co.ds.api
 * @Date      : 2022. 12. 7.
 * @작성자     : shadow

 * @변경이력 		:
 * @프로그램 설명 : 문자 발송 시스템 (디자인 템플릿)
 */
public abstract class SmsTemplate {

	public final void buildTemplate(SmsEntity entity) throws Exception {
		sendingSMS();
		jobProcess(entity);
	}
	
	private void sendingSMS() {
		System.out.println(".. SEND SMS //");
	}
	
	public abstract void jobProcess(SmsEntity entity) throws Exception;
	
}
