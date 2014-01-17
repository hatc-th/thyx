package com.hatc.common.service.aop;

import java.lang.reflect.Method;

import org.springframework.aop.AfterReturningAdvice;

import com.hatc.base.common.RequestMap;
import com.hatc.common.contants.ProjectConstants;
import com.hatc.common.servicestub.ReqIdentity;

/**
* 
* <b>system��</b>      Эͬ�칫ƽ̨<br/>
* <b>description��</b> �Զ���¼��־<br/>
* <b>author��</b>      ����<br/>
* <b>copyright��</b>	�� ����������ϿƼ����޹�˾<br/>
* <b>version��</b>     VER1.00 2010-12-02<br/>
*
**/
public class LogsAfterAdvice implements AfterReturningAdvice {

	public void afterReturning(Object obj, Method method, Object[] args, Object target) throws Throwable {
	    if(args==null)
	        return;
	    
		RequestMap requestMap = (RequestMap) args[0];
		// ��ȡ�û���¼�Ự��ʶ
		ReqIdentity reqidentity = (ReqIdentity) requestMap.getObject(ProjectConstants.SESSION_REQIDENTITY);
		// ����FunctionId
		String logFunction = requestMap.getString(ProjectConstants.LOG_FUNCTION_ID);
		if (logFunction != null && logFunction.trim().length() > 0) {
			// ����FunctionId
			String logText = requestMap.getString(ProjectConstants.LOG_TEXT);
			//TODO ��¼��־
//			LogStub aLogStub = new LogStub();
//			aLogStub.writeLog(reqidentity.getUserId(), logFunction, "1", logText == null ? "" : logText, reqidentity.getSessionId());
		}
	}
}
