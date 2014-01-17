package com.hatc.thyx.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.hatc.base.common.BeanValue;
import com.hatc.base.common.RequestMap;
import com.hatc.base.service.EntityManager;
import com.hatc.base.web.action.ActionDispose;
import com.hatc.base.web.action.ExecuteProcess;
import com.hatc.base.web.form.BaseForm;
import com.hatc.common.web.action.BaseAction;
import com.hatc.contants.TtimsCode;

public class ExampleAction extends BaseAction {
	
	/**
	  * 处理内容：查询飞行计划审核管理单列表
	  * 
	  * @param 
	  * @return 
	  * 
	  */
	public ActionForward searchList(ActionMapping actionMapping, BaseForm baseForm, HttpServletRequest request,HttpServletResponse
			response){		
		// 定义业务流
		ActionDispose actionDispose = new ExecuteProcess();
		// 解析Request 请求
		RequestMap map = new RequestMap();
		BeanValue value = new BeanValue();
		try {
			map = actionDispose.initRequest(request, servlet);
			EntityManager planAuditManager = (EntityManager) getBean("planAuditManager");
			value = planAuditManager.searchList(map);
		} catch (Exception e) {
			String managerType = map.getString("managerType");
			value.addParamMap("managerType", managerType);
			processException(map, value, e, this.getClass().getName() + ".searchList.error",TtimsCode.SEARCH_PLANAUDIT_ERROR);
			
	    }
		// 处理放入request 请求中的信息集合value
		disposeRequest(request, value);
		// 转至指定层次
		return actionMapping.findForward(value.getForward());

	} 

	
	
}
