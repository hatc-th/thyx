package com.hatc.common.web.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.hatc.base.common.BeanValue;
import com.hatc.base.common.RequestMap;
import com.hatc.base.web.action.ActionDispose;
import com.hatc.base.web.action.ExecuteProcess;
import com.hatc.base.web.form.BaseForm;
import com.hatc.common.contants.ProjectCode;
import com.hatc.common.contants.ProjectConstants;
import com.hatc.common.exception.LoginException;
import com.hatc.common.service.ProjectManager;
import com.hatc.common.service.other.LoginManager;
import com.hatc.common.web.config.ProjectConfig;

/**
 * 
 * <b>system��</b> Эͬ�칫ƽ̨<br/> 
 * <b>description��</b> �û���¼��ת����<br/> 
 * <b>author��</b> ����<br/> 
 * <b>copyright��</b> ����������ϿƼ����޹�˾<br/> 
 * <b>version��</b> VER1.00 2010-04-06<br/>
 *    ��ȡϵͳ������֤������ chenzj 2012.4.6
 * 
 */
public class LoginAction extends BaseAction 
{
	
	private Logger logger = Logger.getLogger(LoginAction.class);  
	
	/**
	 * ��������:��ҳ����
	 * 
	 * @param ActionMapping
	 *            Action Map��ͼ
	 * @param BaseForm
	 *            �Զ���Form
	 * @param HttpServletRequest
	 *            request ����
	 * @param HttpServletResponse
	 *            response ��Ӧ
	 * @return ActionForward ����
	 * @throws Exception
	 *             �쳣
	 */
	public ActionForward index(ActionMapping mapping, BaseForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		// ҵ�����̶���
		ActionDispose disponse = new ExecuteProcess();
		// ����Request����
		RequestMap map = new RequestMap();
		BeanValue value = new BeanValue();
		try {
			map = disponse.initRequest(request, servlet);
			LoginManager manager = (LoginManager) getBean("loginManager");
			value = manager.index(map);
			//������ת
			value.setForward("login");
		} catch (Exception e) {
			//e.printStackTrace();
			processException(map, value, e, this.getClass().getName() + ".index", ProjectCode.USER_LOGIN_ERROR);
		}
		
		//disposeRequest(request, value);
		
		disposeRequest(request, value,mapping);
		
		if(logger.isDebugEnabled())
			disponse.debugRequest("response",request, servlet); 
		
		return mapping.findForward(value.getForward());
	}
	
	/**
	 * �������ݣ��û�����վ��¼
	 */
	public ActionForward webLogin(ActionMapping mapping, BaseForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// ҵ�����̶���
		ActionDispose disponse = new ExecuteProcess();
		 
		// ����Request����
		RequestMap map = new RequestMap();
		BeanValue value = new BeanValue();
		try {
			
			map = disponse.initRequest(request, servlet);
			LoginManager manager = (LoginManager) getBean("loginManager");
			value = manager.webLogin(map);
			// ����Ŀ��ҳ��
			String ctrl = (String)value.getRequestMap("ctrl");
			value.setForward(ctrl.equals("homepage") ? "homepage" : "loginPwd");
			 
			    
		} catch (Exception e) {
			if (e instanceof LoginException) {
				if (((LoginException) e).getFaultCode() == LoginException.BAD_PWD){
					// ��¼��
					String loginId = map.getString("loginId");
					// ��¼����
					String password = map.getString("password");
					// ����������
					String errorPwdNumber = map.getString("errorPwdNumber");
					int intEPN = errorPwdNumber != null && !errorPwdNumber.equals("") ? Integer.parseInt(errorPwdNumber) : 0;

					value.addParamMap("loginId", loginId);// ��¼��
					value.addParamMap("password", password);// ��¼����
					value.addParamMap("errorPwdNumber", intEPN + 1);// ����������
					// ��ȡϵͳ������֤������
					String pwdErrNum = "";
					if("true".equals(ProjectConfig.getProjectConfig("max_logon_num_fromdb"))){
						//�����ݿ���ȡ�� ��ȡϵͳ������֤������ chenzj 2012.4.6
						//��ʼ
						ProjectManager pm = (ProjectManager)getBean("projectManager");
						pwdErrNum = pm.getRelogonTimes();
						//����
					}else{
						pwdErrNum = ProjectConfig.getProjectConfig("pwd_error_number");
					}
						
					
					value.addRequestMap("pwd_error_number", pwdErrNum);
					if (intEPN + 1 == Integer.parseInt(pwdErrNum)) {
						value.addParamMap("errorInfoKey", ProjectCode.USER_LOGIN_NUMBER_ERROR);// ����������
					}
					processException(map, value, e, this.getClass().getName() + ".webLogin", ProjectCode.USER_LOGIN_PASSWORD_ERROR);
				}
			}
			  
			processException(map, value, e, this.getClass().getName() + ".webLogin", ProjectCode.USER_LOGIN_ERROR);
		}
		 
		/**request.getSession().removeAttribute(ProjectConstants.SESSION_REQIDENTITY);
		request.getSession().removeAttribute(ProjectConstants.SESSION_USER);
		request.getSession().removeAttribute(ProjectConstants.SESSION_USER_ROLE);
		request.getSession().removeAttribute(ProjectConstants.SESSION_USER_ROLE_LIST);
		request.getSession().removeAttribute(ProjectConstants.SESSION_WINDOW_VER);
		**/ 
		
		// �������request �����е���Ϣ����value
		disposeRequest(request, value,mapping);
		 
		if(logger.isDebugEnabled())
				disponse.debugRequest("response",request, servlet); 
		
		//���ý������ʹ���
		String uiCode = map.getString("uiCode");
		request.getSession().setAttribute(ProjectConstants.SESSION_USER_UI_CODE, uiCode);
		
		return mapping.findForward(value.getForward());
	}
	 
	
	/**
	 * �������ݣ��ⲿӦ��ϵͳ���ýӿڲ鿴��Ϣʱ�ķ���ϵͳ��
	 * �޷�÷
	 * 2012-03-14
	 */
	public ActionForward wsLogin(ActionMapping mapping, BaseForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// ҵ�����̶���
		ActionDispose disponse = new ExecuteProcess();
		 
		// ����Request����
		RequestMap map = new RequestMap();
		BeanValue value = new BeanValue();
		try {
			
			map = disponse.initRequest(request, servlet);
			LoginManager manager = (LoginManager) getBean("loginManager");
			value = manager.wsLogin(map);
			// ����Ŀ��ҳ��
			String ctrl = (String)value.getRequestMap("ctrl");
			value.setForward(ctrl.equals("homepage") ? "homepage" : "loginPwd");

//			ctrl :  homepage
			
			
		} catch (Exception e) {
			if (e instanceof LoginException) {
				if (((LoginException) e).getFaultCode() == LoginException.BAD_PWD){
					// ��¼��
					String loginId = map.getString("loginId");
					// ��¼����
					String password = map.getString("password");
					// ����������
					String errorPwdNumber = map.getString("errorPwdNumber");
					int intEPN = errorPwdNumber != null && !errorPwdNumber.equals("") ? Integer.parseInt(errorPwdNumber) : 0;

					value.addParamMap("loginId", loginId);// ��¼��
					value.addParamMap("password", password);// ��¼����
					value.addParamMap("errorPwdNumber", intEPN + 1);// ����������
					// ��ȡϵͳ������֤������
					String pwdErrNum = ProjectConfig.getProjectConfig("pwd_error_number");
					value.addRequestMap("pwd_error_number", pwdErrNum);
					if (intEPN + 1 == Integer.parseInt(pwdErrNum)) {
						value.addParamMap("errorInfoKey", ProjectCode.USER_LOGIN_NUMBER_ERROR);// ����������
					}
				}
			}
			  
			processException(map, value, e, this.getClass().getName() + ".webLogin", ProjectCode.USER_LOGIN_ERROR);
		}
		 
		// �������request �����е���Ϣ����value
		disposeRequest(request, value,mapping);
		 
		if(logger.isDebugEnabled())
				disponse.debugRequest("response",request, servlet); 
		return mapping.findForward(value.getForward());
	}
 
	/**
	 * ��������:CS�ͻ��˵�½
	 */
	public ActionForward csLogin(ActionMapping mapping, BaseForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// ҵ�����̶���
		ActionDispose disponse = new ExecuteProcess();
		// ����Request����
		RequestMap map = new RequestMap();
		BeanValue value = new BeanValue();
		try {
			map = disponse.initRequest(request, servlet);
			LoginManager manager = (LoginManager) getBean("loginManager");
			value = manager.csLogin(map);
			// ����Ŀ��ҳ��
			String ctrl = (String)value.getRequestMap("ctrl");
			value.setForward(ctrl.equals("homepage") ? "homepage" : "loginPwd");
		} catch (Exception e) {
			// �쳣����
			processException(map, value, e, ProjectConstants.POP_UP_WINDOW, ProjectCode.CS_LOGIN_ERROR);
		}
		//disposeRequest(request, value);
		disposeRequest(request, value,mapping);
		 
		if(logger.isDebugEnabled())
				disponse.debugRequest("response",request, servlet); 
		return mapping.findForward(value.getForward());
	}

	/**
	 * ��������:���µ�½
	 */
	public ActionForward restLogin(ActionMapping mapping, BaseForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// ҵ�����̶���
		ActionDispose disponse = new ExecuteProcess();
		// ����Request����
		RequestMap map = disponse.initRequest(request, servlet);

		BeanValue value = new BeanValue();
		try {
			LoginManager manager = (LoginManager) getBean("loginManager");
			value = manager.restLogin(map);
		} catch (Exception e) {
			// �쳣����
			processException(map, value, e, ProjectConstants.POP_UP_WINDOW, ProjectCode.SWICTH_FIRSTPAGE_ERROR);
		}
		disposeRequest(request, value);
		 
		if(logger.isDebugEnabled())
				disponse.debugRequest("response",request, servlet); 
		//disposeRequest(request, value,mapping);
		return mapping.findForward(value.getForward());
	}

	/**
	 * �������ݣ�ͼ��˵�
	 */
	public ActionForward menuIcoHref(ActionMapping mapping, BaseForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// ҵ�����̶���
		ActionDispose disponse = new ExecuteProcess();
		// ����Request����
		RequestMap map = new RequestMap();
		BeanValue value = new BeanValue();
		try {
			map = disponse.initRequest(request, servlet);
			LoginManager manager = (LoginManager) getBean("loginManager");
			value = manager.menuIcoHref(map);
		}  catch (Exception e) {			 
//			processException(map, value, e, ProjectConstants.FRAME_WINDOW, ProjectCode.USER_LOGIN_ERROR);
			processException(map, value, e, this.getClass().getName() + ".webLogin", ProjectCode.USER_LOGIN_ERROR);
		}
		
		disposeRequest(request, value,mapping);
		 
		if(logger.isDebugEnabled())
				disponse.debugRequest("response",request, servlet); 
		
		String size = (String) value.getParamMap("size");
		return size != null && size.equals("1") ? new ActionForward(value.getForward().equals("") ? "/404.jsp" : value.getForward()) : mapping.findForward(value
				.getForward());
	}

	/**
	 * ��������:��ҳ
	 * 
	 * @param ActionMapping
	 *            Action Map��ͼ
	 * @param BaseForm
	 *            �Զ���Form
	 * @param HttpServletRequest
	 *            request ����
	 * @param HttpServletResponse
	 *            response ��Ӧ
	 * @return ActionForward ����
	 * @throws Exception
	 *             �쳣
	 */
	public ActionForward firstPage(ActionMapping mapping, BaseForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// ҵ�����̶���
		ActionDispose disponse = new ExecuteProcess();
		// ����Request����
		RequestMap map = new RequestMap();
		BeanValue value = new BeanValue();
		try {
			map = disponse.initRequest(request, servlet);
			LoginManager manager = (LoginManager) getBean("loginManager");
			value = manager.firstPage(map);
			// �����ѯ
		} catch (Exception e) {
			// �쳣����
			processException(map, value, e, this.getClass().getName() + ".firstPage", ProjectCode.USER_LOGIN_ERROR);
		}
		
		disposeRequest(request, value,mapping);
		 
		if(logger.isDebugEnabled())
				disponse.debugRequest("response",request, servlet); 
		
		return mapping.findForward(value.getForward());
	}
}
