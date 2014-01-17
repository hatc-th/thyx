package com.hatc.base.web.action;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.apache.struts.config.ControllerConfig;
import org.apache.struts.config.impl.ModuleConfigImpl;
import org.apache.struts.util.MessageResources; 
import com.hatc.base.contants.BaseConstants; 
import com.hatc.base.web.form.BaseForm;
  
import com.hatc.common.web.servlet.WebRunConfig;

/**
* 
* <b>system：</b>      协同办公平台<br/>
* <b>description：</b> Action基础类（对DispatchAction进行重载）<br/>
* <b>author：</b>      王洋<br/>
* <b>copyright：</b>	　 北京华安天诚科技有限公司<br/>
* <b>version：</b>     VER1.00 2010-04-06<br/>
*
**/
public class RootAction extends DispatchAction {
	protected HashMap<String, Method> methodsnew = new HashMap<String, Method>();
	/**
	 * The Class instance of this <code>DispatchAction</code> class.
	 */
	protected Class clazznew = this.getClass();
	
	protected final Log log = LogFactory.getLog(getClass());

	protected Class typesnew[] = { ActionMapping.class, BaseForm.class, HttpServletRequest.class, HttpServletResponse.class };

	protected static MessageResources messages = MessageResources.getMessageResources("org.apache.struts.actions.LocalStrings");

	/**
	 * 获取Spring的业务处理Manager
	 * TODO nly 2011-12-23 
	 * 优化性能.
	 * @param name
	 *            定义的业务名称（xml文件中定义）
	 */
	public Object getBean(String name)
	{
		return WebRunConfig.webac.getBean(name);
	}

	// 重构父类execute方法
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HttpSession session = request.getSession();

		
		String upload_size = (String) request.getSession().getAttribute(BaseConstants.UPLOAD_FILE_MAX_SIZE);
		

		
		if (upload_size == null || upload_size.equals("")) {
			ModuleConfigImpl module = (ModuleConfigImpl) request.getAttribute("org.apache.struts.action.MODULE");
			ControllerConfig controlConfig = module.getControllerConfig();
			request.getSession().setAttribute(BaseConstants.UPLOAD_FILE_MAX_SIZE, controlConfig.getMaxFileSize());
		}

		// 得到参数的值
		String parameter = mapping.getParameter();
		// 如果参数为空，输入日志并转到错误页面
		if (parameter == null) {
			String message = messages.getMessage("dispatch.handler", mapping.getPath());
			log.error(message);
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, message);
			return (null);
		}
		// 获得parameter传递的值,这里是user.event.list
		String name = getMethodName(mapping, form, request, response, parameter);
		if(name==null)
			name="";
		
		/**
		if(DebugConfig.debugFlag==true)
		{
			StringBuffer sb = new StringBuffer();					
			log.debug( this.getClass().getName() + " : " + name );
		}
	    **/
		
		if ("execute".equals(name) || "perform".equals(name)) {
			String message = messages.getMessage("dispatch.recursive", mapping.getPath());
			log.error(message);    
			throw new ServletException(message);
		} else {
			if (name.indexOf(".") > 0)
				return this.dispatchMethod(mapping, form, request, response, name.substring(name.lastIndexOf(".") + 1));
			else
				return this.dispatchMethod(mapping, form, request, response, name);
		}
	}

	/** 
	 * 重写父类dispatchMethod方法(改为用BaseForm) 
	 */
	/* (non-Javadoc)
	 * @see org.apache.struts.actions.DispatchAction#dispatchMethod(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.String)
	 */
	@Override
	protected ActionForward dispatchMethod(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response, String name) throws Exception {
		if (name == null) {
			return unspecified(mapping, form, request, response);
		}
		Method method = null;
		try {
			method = getMethod(name);
		} catch (NoSuchMethodException e) {
			String message = messages.getMessage("dispatch.method", mapping.getPath(), name);
			log.error(message, e);
			throw e;
		}
		ActionForward forward = null;
		try {
			Object args[] = { mapping, (BaseForm) form, request, response };
			forward = (ActionForward) method.invoke(this, args);
		} catch (ClassCastException e) {
			String message = messages.getMessage("dispatch.return", mapping.getPath(), name);
			log.error(message, e);
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, message);
			return (null);
		} catch (IllegalAccessException e) {
			String message = messages.getMessage("dispatch.error", mapping.getPath(), name);
			log.error(message, e);
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, message);
			return (null);
		} catch (InvocationTargetException e) {
			Throwable t = e.getTargetException();
			if (t instanceof Exception) {
				throw ((Exception) t);
			} else {
				String message = messages.getMessage("dispatch.error", mapping.getPath(), name);
				log.error(message, e);
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, message);
				return (null);
			}
		}
		return forward;
	}

	/**
	 * 重写父类getMethod方法
	 */
	/* (non-Javadoc)
	 * @see org.apache.struts.actions.DispatchAction#getMethod(java.lang.String)
	 */
	@Override
	protected Method getMethod(String name) throws NoSuchMethodException {
		
		synchronized (methodsnew) {
			Method method = methodsnew.get(name);
			if (method == null) {
				method = clazznew.getMethod(name, typesnew);
				methodsnew.put(name, method);
			}
			return (method);
		}
	}
}
