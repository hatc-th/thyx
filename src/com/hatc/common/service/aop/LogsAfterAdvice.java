package com.hatc.common.service.aop;

import java.lang.reflect.Method;

import org.springframework.aop.AfterReturningAdvice;

import com.hatc.base.common.RequestMap;
import com.hatc.common.contants.ProjectConstants;
import com.hatc.common.servicestub.ReqIdentity;

/**
* 
* <b>system：</b>      协同办公平台<br/>
* <b>description：</b> 自动记录日志<br/>
* <b>author：</b>      王洋<br/>
* <b>copyright：</b>	　 北京华安天诚科技有限公司<br/>
* <b>version：</b>     VER1.00 2010-12-02<br/>
*
**/
public class LogsAfterAdvice implements AfterReturningAdvice {

	public void afterReturning(Object obj, Method method, Object[] args, Object target) throws Throwable {
	    if(args==null)
	        return;
	    
		RequestMap requestMap = (RequestMap) args[0];
		// 获取用户登录会话标识
		ReqIdentity reqidentity = (ReqIdentity) requestMap.getObject(ProjectConstants.SESSION_REQIDENTITY);
		// 方法FunctionId
		String logFunction = requestMap.getString(ProjectConstants.LOG_FUNCTION_ID);
		if (logFunction != null && logFunction.trim().length() > 0) {
			// 方法FunctionId
			String logText = requestMap.getString(ProjectConstants.LOG_TEXT);
			//TODO 记录日志
//			LogStub aLogStub = new LogStub();
//			aLogStub.writeLog(reqidentity.getUserId(), logFunction, "1", logText == null ? "" : logText, reqidentity.getSessionId());
		}
	}
}
