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
* <b>system��</b>      Эͬ�칫ƽ̨<br/>
* <b>description��</b> �Զ���֤FUNCTION<br/>
* <b>author��</b>      ����<br/>
* <b>copyright��</b>	�� ����������ϿƼ����޹�˾<br/>
* <b>version��</b>     VER1.00 2010-12-02<br/>
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
		
		// ִ�з������ȫ·��
		String methodRealPath = target.getClass().getName() + "." + method.getName();
		// ������Ӧ��Ȩ
		String functionId = operateFunctionId == null || operateFunctionId.trim().length() == 0 ? ProjectFunctionConfig
				.getFunctionProperties(methodRealPath) : ProjectFunctionConfig.getFunctionProperties(methodRealPath + "." + operateFunctionId);
		/*
		if (functionId == null || functionId.trim().length() == 0) {
			// ����δ��Ȩ����
			throw new BaseException(ProjectCode.SERVICE_AUTH_ERR);
		}
		 */
		if (functionId != null && functionId.trim().length() > 0) {
			// ����û���ʶ
			ReqIdentity reqidentity = (ReqIdentity) requestMap.getObject(ProjectConstants.SESSION_REQIDENTITY);
			// ����FunctionId
			//���� chenzj 2012-07-02 session ��ʧ��ʧЧʱ���жϴ���
			if(reqidentity != null){
				reqidentity.setFunctionId(functionId);
			//TODO ��ȫ����
//			SecurityStub aSecurityStub = new SecurityStub();
//			// ��֤Ȩ��
//			aSecurityStub.checkPermission(reqidentity);
			// ����Ȩ��ID
			requestMap.addParameter(ProjectConstants.LOG_FUNCTION_ID, functionId);
			}else{
				//�׳� �û��ѹ��� �쳣
				throw new BaseException(ProjectCode.USER_INVALID_RELOGON);
			}
			
			// System.out.println("before.......................end......");
		}
	}

}
