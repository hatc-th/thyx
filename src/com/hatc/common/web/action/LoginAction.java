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
 * <b>system：</b> 协同办公平台<br/> 
 * <b>description：</b> 用户登录跳转控制<br/> 
 * <b>author：</b> 王洋<br/> 
 * <b>copyright：</b> 北京华安天诚科技有限公司<br/> 
 * <b>version：</b> VER1.00 2010-04-06<br/>
 *    获取系统密码验证最大次数 chenzj 2012.4.6
 * 
 */
public class LoginAction extends BaseAction 
{
	
	private Logger logger = Logger.getLogger(LoginAction.class);  
	
	/**
	 * 处理内容:首页处理
	 * 
	 * @param ActionMapping
	 *            Action Map地图
	 * @param BaseForm
	 *            自定义Form
	 * @param HttpServletRequest
	 *            request 请求
	 * @param HttpServletResponse
	 *            response 响应
	 * @return ActionForward 跳传
	 * @throws Exception
	 *             异常
	 */
	public ActionForward index(ActionMapping mapping, BaseForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 业务流程定义
		ActionDispose disponse = new ExecuteProcess();
		// 解析Request请求
		RequestMap map = new RequestMap();
		BeanValue value = new BeanValue();
		try {
			map = disponse.initRequest(request, servlet);
			LoginManager manager = (LoginManager) getBean("loginManager");
			value = manager.index(map);
			//控制跳转
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
	 * 处理内容：用户从网站登录
	 */
	public ActionForward webLogin(ActionMapping mapping, BaseForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// 业务流程定义
		ActionDispose disponse = new ExecuteProcess();
		 
		// 解析Request请求
		RequestMap map = new RequestMap();
		BeanValue value = new BeanValue();
		try {
			
			map = disponse.initRequest(request, servlet);
			LoginManager manager = (LoginManager) getBean("loginManager");
			value = manager.webLogin(map);
			// 设置目标页面
			String ctrl = (String)value.getRequestMap("ctrl");
			value.setForward(ctrl.equals("homepage") ? "homepage" : "loginPwd");
			 
			    
		} catch (Exception e) {
			if (e instanceof LoginException) {
				if (((LoginException) e).getFaultCode() == LoginException.BAD_PWD){
					// 登录名
					String loginId = map.getString("loginId");
					// 登录密码
					String password = map.getString("password");
					// 密码错误次数
					String errorPwdNumber = map.getString("errorPwdNumber");
					int intEPN = errorPwdNumber != null && !errorPwdNumber.equals("") ? Integer.parseInt(errorPwdNumber) : 0;

					value.addParamMap("loginId", loginId);// 登录名
					value.addParamMap("password", password);// 登录密码
					value.addParamMap("errorPwdNumber", intEPN + 1);// 密码错误次数
					// 获取系统密码验证最大次数
					String pwdErrNum = "";
					if("true".equals(ProjectConfig.getProjectConfig("max_logon_num_fromdb"))){
						//从数据库中取出 获取系统密码验证最大次数 chenzj 2012.4.6
						//开始
						ProjectManager pm = (ProjectManager)getBean("projectManager");
						pwdErrNum = pm.getRelogonTimes();
						//结束
					}else{
						pwdErrNum = ProjectConfig.getProjectConfig("pwd_error_number");
					}
						
					
					value.addRequestMap("pwd_error_number", pwdErrNum);
					if (intEPN + 1 == Integer.parseInt(pwdErrNum)) {
						value.addParamMap("errorInfoKey", ProjectCode.USER_LOGIN_NUMBER_ERROR);// 密码错误次数
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
		
		// 处理放入request 请求中的信息集合value
		disposeRequest(request, value,mapping);
		 
		if(logger.isDebugEnabled())
				disponse.debugRequest("response",request, servlet); 
		
		//设置界面类型代码
		String uiCode = map.getString("uiCode");
		request.getSession().setAttribute(ProjectConstants.SESSION_USER_UI_CODE, uiCode);
		
		return mapping.findForward(value.getForward());
	}
	 
	
	/**
	 * 处理内容：外部应用系统调用接口查看信息时的访问系统。
	 * 罗凤梅
	 * 2012-03-14
	 */
	public ActionForward wsLogin(ActionMapping mapping, BaseForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// 业务流程定义
		ActionDispose disponse = new ExecuteProcess();
		 
		// 解析Request请求
		RequestMap map = new RequestMap();
		BeanValue value = new BeanValue();
		try {
			
			map = disponse.initRequest(request, servlet);
			LoginManager manager = (LoginManager) getBean("loginManager");
			value = manager.wsLogin(map);
			// 设置目标页面
			String ctrl = (String)value.getRequestMap("ctrl");
			value.setForward(ctrl.equals("homepage") ? "homepage" : "loginPwd");

//			ctrl :  homepage
			
			
		} catch (Exception e) {
			if (e instanceof LoginException) {
				if (((LoginException) e).getFaultCode() == LoginException.BAD_PWD){
					// 登录名
					String loginId = map.getString("loginId");
					// 登录密码
					String password = map.getString("password");
					// 密码错误次数
					String errorPwdNumber = map.getString("errorPwdNumber");
					int intEPN = errorPwdNumber != null && !errorPwdNumber.equals("") ? Integer.parseInt(errorPwdNumber) : 0;

					value.addParamMap("loginId", loginId);// 登录名
					value.addParamMap("password", password);// 登录密码
					value.addParamMap("errorPwdNumber", intEPN + 1);// 密码错误次数
					// 获取系统密码验证最大次数
					String pwdErrNum = ProjectConfig.getProjectConfig("pwd_error_number");
					value.addRequestMap("pwd_error_number", pwdErrNum);
					if (intEPN + 1 == Integer.parseInt(pwdErrNum)) {
						value.addParamMap("errorInfoKey", ProjectCode.USER_LOGIN_NUMBER_ERROR);// 密码错误次数
					}
				}
			}
			  
			processException(map, value, e, this.getClass().getName() + ".webLogin", ProjectCode.USER_LOGIN_ERROR);
		}
		 
		// 处理放入request 请求中的信息集合value
		disposeRequest(request, value,mapping);
		 
		if(logger.isDebugEnabled())
				disponse.debugRequest("response",request, servlet); 
		return mapping.findForward(value.getForward());
	}
 
	/**
	 * 处理内容:CS客户端登陆
	 */
	public ActionForward csLogin(ActionMapping mapping, BaseForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// 业务流程定义
		ActionDispose disponse = new ExecuteProcess();
		// 解析Request请求
		RequestMap map = new RequestMap();
		BeanValue value = new BeanValue();
		try {
			map = disponse.initRequest(request, servlet);
			LoginManager manager = (LoginManager) getBean("loginManager");
			value = manager.csLogin(map);
			// 设置目标页面
			String ctrl = (String)value.getRequestMap("ctrl");
			value.setForward(ctrl.equals("homepage") ? "homepage" : "loginPwd");
		} catch (Exception e) {
			// 异常处理
			processException(map, value, e, ProjectConstants.POP_UP_WINDOW, ProjectCode.CS_LOGIN_ERROR);
		}
		//disposeRequest(request, value);
		disposeRequest(request, value,mapping);
		 
		if(logger.isDebugEnabled())
				disponse.debugRequest("response",request, servlet); 
		return mapping.findForward(value.getForward());
	}

	/**
	 * 处理内容:重新登陆
	 */
	public ActionForward restLogin(ActionMapping mapping, BaseForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// 业务流程定义
		ActionDispose disponse = new ExecuteProcess();
		// 解析Request请求
		RequestMap map = disponse.initRequest(request, servlet);

		BeanValue value = new BeanValue();
		try {
			LoginManager manager = (LoginManager) getBean("loginManager");
			value = manager.restLogin(map);
		} catch (Exception e) {
			// 异常处理
			processException(map, value, e, ProjectConstants.POP_UP_WINDOW, ProjectCode.SWICTH_FIRSTPAGE_ERROR);
		}
		disposeRequest(request, value);
		 
		if(logger.isDebugEnabled())
				disponse.debugRequest("response",request, servlet); 
		//disposeRequest(request, value,mapping);
		return mapping.findForward(value.getForward());
	}

	/**
	 * 处理内容：图标菜单
	 */
	public ActionForward menuIcoHref(ActionMapping mapping, BaseForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// 业务流程定义
		ActionDispose disponse = new ExecuteProcess();
		// 解析Request请求
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
	 * 处理内容:首页
	 * 
	 * @param ActionMapping
	 *            Action Map地图
	 * @param BaseForm
	 *            自定义Form
	 * @param HttpServletRequest
	 *            request 请求
	 * @param HttpServletResponse
	 *            response 响应
	 * @return ActionForward 跳传
	 * @throws Exception
	 *             异常
	 */
	public ActionForward firstPage(ActionMapping mapping, BaseForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// 业务流程定义
		ActionDispose disponse = new ExecuteProcess();
		// 解析Request请求
		RequestMap map = new RequestMap();
		BeanValue value = new BeanValue();
		try {
			map = disponse.initRequest(request, servlet);
			LoginManager manager = (LoginManager) getBean("loginManager");
			value = manager.firstPage(map);
			// 报表查询
		} catch (Exception e) {
			// 异常处理
			processException(map, value, e, this.getClass().getName() + ".firstPage", ProjectCode.USER_LOGIN_ERROR);
		}
		
		disposeRequest(request, value,mapping);
		 
		if(logger.isDebugEnabled())
				disponse.debugRequest("response",request, servlet); 
		
		return mapping.findForward(value.getForward());
	}
}
