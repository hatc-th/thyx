package com.hatc.common.service.aop;

import java.lang.reflect.Method;

import org.springframework.aop.MethodBeforeAdvice;

import com.hatc.base.common.BaseException;
import com.hatc.base.common.RequestMap;
import com.hatc.common.contants.ProjectCode;
import com.hatc.common.contants.ProjectConstants;
import com.hatc.common.servicestub.ReqIdentity;
import com.hatc.common.web.config.ProjectFunctionConfig;

/**
* 
* <b>system：</b>      协同办公平台<br/>
* <b>description：</b> 自动验证FUNCTION<br/>
* <b>author：</b>      王洋<br/>
* <b>copyright：</b>	　 北京华安天诚科技有限公司<br/>
* <b>version：</b>     VER1.00 2010-12-02<br/>
*
**/
public class FunctionCheckBeforeAdvice implements MethodBeforeAdvice {

	public void before(Method method, Object[] args, Object target) throws Throwable 
	{
		//System.out.println("before...............................................");
	    
	    if(args == null )
            return ;
	    
		if(args.length == 0 )
		    return ;
		RequestMap requestMap = (RequestMap) args[0];
		String operateFunctionId = requestMap.getString(ProjectConstants.OPERATE_FUNCTION);
		
		// 执行方法类的全路径
		String methodRealPath = target.getClass().getName() + "." + method.getName();
		// 方法对应授权
		String functionId = operateFunctionId == null || operateFunctionId.trim().length() == 0 ? ProjectFunctionConfig
				.getFunctionProperties(methodRealPath) : ProjectFunctionConfig.getFunctionProperties(methodRealPath + "." + operateFunctionId);
		/*
		if (functionId == null || functionId.trim().length() == 0) {
			// 返回未授权错误
			throw new BaseException(ProjectCode.SERVICE_AUTH_ERR);
		}
		 */
		if (functionId != null && functionId.trim().length() > 0) {
			// 获得用户标识
			ReqIdentity reqidentity = (ReqIdentity) requestMap.getObject(ProjectConstants.SESSION_REQIDENTITY);
			// 设置FunctionId
			//增加 chenzj 2012-07-02 session 丢失或失效时，判断处理。
			if(reqidentity != null){
				reqidentity.setFunctionId(functionId);
			//TODO 安全服务
//			SecurityStub aSecurityStub = new SecurityStub();
//			// 验证权限
//			aSecurityStub.checkPermission(reqidentity);
			// 设置权限ID
			requestMap.addParameter(ProjectConstants.LOG_FUNCTION_ID, functionId);
			}else{
				//抛出 用户已过期 异常
				throw new BaseException(ProjectCode.USER_INVALID_RELOGON);
			}
			
			// System.out.println("before.......................end......");
		}
	}

}
