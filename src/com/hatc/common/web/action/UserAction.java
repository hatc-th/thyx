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
	 * �޸�����(��ʼ��)
	 */
	public ActionForward setPwd(ActionMapping aActionMapping, BaseForm aBaseForm,
			HttpServletRequest aHttpServletRequest, HttpServletResponse aHttpServletResponse) throws Exception {
		// ����ҵ����
		ActionDispose aActionDispose = new ExecuteProcess();
		// ����Request ����
		RequestMap aRequestMap = new RequestMap();
		BeanValue aBeanValue = new BeanValue();
		try {
			// ʹ������ע�������ö���
			aRequestMap = aActionDispose.initRequest(aHttpServletRequest, servlet);
			UserManager aUserManager = (UserManager) getBean("userManager");
			aBeanValue = aUserManager.setPwd(aRequestMap);
			/* ���÷��� BeanValue */
			aBeanValue.setForward("setPwd");
		} catch (Exception e) {
			processException(aRequestMap, aBeanValue, e, ProjectConstants.FRAME_WINDOW, ProjectCode.USER_INITPASSWORD_FAIL);
		}
		// �������request �����е���Ϣ����value
		//disposeRequest(aHttpServletRequest, aBeanValue);
		disposeRequest(aHttpServletRequest, aBeanValue,aActionMapping);
		 
		if(logger.isDebugEnabled())
			aActionDispose.debugRequest("response",aHttpServletRequest, servlet); 
		// ת��ָ�����
		return aActionMapping.findForward(aBeanValue.getForward());
	}
}
