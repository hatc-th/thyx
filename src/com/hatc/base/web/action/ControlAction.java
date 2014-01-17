//***************************************************************************
/**
 * @������:ControlAction.java
 * @��������:�ܿ�Action
 * @author WangYang
 * @version 1.0��2007-5-30
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
		// Spring Manager ����
		String managerName = request.getParameter("manager");
		// Manager Method ��������
		String method = request.getParameter("method");
		// ���Managerʵ��
		Object obj = WebApplicationContextUtils
				.getRequiredWebApplicationContext(servlet.getServletContext())
				.getBean(managerName);
		// ��÷���
		Method met = obj.getClass().getMethod(method,
				new Class[] { RequestMap.class });
		// ����Request����
		RequestMap map = new RequestMap();

		map.addParameter("projectPath", servlet.getServletContext()
				.getRealPath("/"));
		// ִ�з�������ִ�н��
		BeanValue value = (BeanValue) met.invoke(obj, new Object[] { map });
		// ������
		disposeRequest(request, value);
		// ��ת����
		return mapping.findForward(value.getForward());
	}

	/**
	 * ������������е���Ϣ disposeRequest(request������BeanValue:��Ϣ����)
	 */
	public static void disposeRequest(HttpServletRequest request,
			BeanValue value) {
		// ���������е���Ϣ
		Map rMap = value.getRequestMap();
		// ����Session�е���Ϣ
		Map sMap = value.getSessionMap();
		// ���������еĴ�����Ϣ
		Map pMap = value.getParamMap();

		// ����Ϣ����������
		Iterator it = rMap.keySet().iterator();
		while (it.hasNext()) {
			String key = (String) it.next();
			request.setAttribute(key, rMap.get(key));
		}
		// ��SESSION��Ϣ����SESSION��
		it = sMap.keySet().iterator();
		while (it.hasNext()) {
			String key = (String) it.next();
			request.getSession().setAttribute(key, sMap.get(key));
		}
		// ��������Ϣ����������
		it = pMap.keySet().iterator();
		while (it.hasNext()) {
			String key = (String) it.next();
			request.setAttribute(key, pMap.get(key));
		}
	}

}
