package egovframework.common.util;

import egovframework.api.entity.SmsEntity;
import egovframework.common.util.sms.SmsWorkProcess;

public class TestJavaSource {

	public static void main(String[] args) throws Exception {

		SmsEntity entity = null;
		entity = new SmsEntity();
		entity.setDest_no("01045627914");
		entity.setCustomernm("홍길동");
		SmsWorkProcess smsWorkProcess = new SmsWorkProcess();
		smsWorkProcess.buildTemplate(entity);
		
	}
}


