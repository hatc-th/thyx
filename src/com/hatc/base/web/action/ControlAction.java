//***************************************************************************
/**
 * @类名称:ControlAction.java
 * @处理内容:总控Action
 * @author WangYang
 * @version 1.0，2007-5-30
 */
// ***************************************************************************
package com.hatc.base.web.action;

import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.hatc.base.common.BeanValue;
import com.hatc.base.common.RequestMap;

public class ControlAction extends Action {

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// Spring Manager 名称
		String managerName = request.getParameter("manager");
		// Manager Method 方法名称
		String method = request.getParameter("method");
		// 获得Manager实例
		Object obj = WebApplicationContextUtils
				.getRequiredWebApplicationContext(servlet.getServletContext())
				.getBean(managerName);
		// 获得方法
		Method met = obj.getClass().getMethod(method,
				new Class[] { RequestMap.class });
		// 解析Request请求
		RequestMap map = new RequestMap();

		map.addParameter("projectPath", servlet.getServletContext()
				.getRealPath("/"));
		// 执行方法并获执行结果
		BeanValue value = (BeanValue) met.invoke(obj, new Object[] { map });
		// 处理结果
		disposeRequest(request, value);
		// 跳转动作
		return mapping.findForward(value.getForward());
	}

	/**
	 * 处理放入请求中的信息 disposeRequest(request：请求，BeanValue:信息集合)
	 */
	public static void disposeRequest(HttpServletRequest request,
			BeanValue value) {
		// 放在请求中的信息
		Map rMap = value.getRequestMap();
		// 放在Session中的信息
		Map sMap = value.getSessionMap();
		// 放在请求中的错误信息
		Map pMap = value.getParamMap();

		// 将信息放入请求中
		Iterator it = rMap.keySet().iterator();
		while (it.hasNext()) {
			String key = (String) it.next();
			request.setAttribute(key, rMap.get(key));
		}
		// 将SESSION信息放入SESSION中
		it = sMap.keySet().iterator();
		while (it.hasNext()) {
			String key = (String) it.next();
			request.getSession().setAttribute(key, sMap.get(key));
		}
		// 将错误信息放入请求中
		it = pMap.keySet().iterator();
		while (it.hasNext()) {
			String key = (String) it.next();
			request.setAttribute(key, pMap.get(key));
		}
	}

}
