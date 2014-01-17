package com.hatc.common.web.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.hatc.base.common.BeanValue;
import com.hatc.base.common.RequestMap;
import com.hatc.base.web.action.ActionDispose;
import com.hatc.base.web.action.ExecuteProcess;
import com.hatc.base.web.form.BaseForm;
import com.hatc.common.contants.ProjectCode;
import com.hatc.common.contants.ProjectConstants;
import com.hatc.common.service.other.UserManager;

public class UserAction extends BaseAction {
	/**
	 * 修改密码(初始化)
	 */
	public ActionForward setPwd(ActionMapping aActionMapping, BaseForm aBaseForm,
			HttpServletRequest aHttpServletRequest, HttpServletResponse aHttpServletResponse) throws Exception {
		// 定义业务流
		ActionDispose aActionDispose = new ExecuteProcess();
		// 解析Request 请求
		RequestMap aRequestMap = new RequestMap();
		BeanValue aBeanValue = new BeanValue();
		try {
			// 使用依赖注入来配置对象
			aRequestMap = aActionDispose.initRequest(aHttpServletRequest, servlet);
			UserManager aUserManager = (UserManager) getBean("userManager");
			aBeanValue = aUserManager.setPwd(aRequestMap);
			/* 设置返回 BeanValue */
			aBeanValue.setForward("setPwd");
		} catch (Exception e) {
			processException(aRequestMap, aBeanValue, e, ProjectConstants.FRAME_WINDOW, ProjectCode.USER_INITPASSWORD_FAIL);
		}
		// 处理放入request 请求中的信息集合value
		//disposeRequest(aHttpServletRequest, aBeanValue);
		disposeRequest(aHttpServletRequest, aBeanValue,aActionMapping);
		 
		if(logger.isDebugEnabled())
			aActionDispose.debugRequest("response",aHttpServletRequest, servlet); 
		// 转至指定层次
		return aActionMapping.findForward(aBeanValue.getForward());
	}
}
