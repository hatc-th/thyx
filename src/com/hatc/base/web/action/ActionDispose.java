package com.hatc.base.web.action;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hatc.base.common.BeanValue;
import com.hatc.base.common.RequestMap;

/**
* 
* <b>system��</b>      Эͬ�칫ƽ̨<br/>
* <b>description��</b> ҵ����ת�������ӿڶ���<br/>
* <b>author��</b>      ����<br/>
* <b>copyright��</b>	�� ����������ϿƼ����޹�˾<br/>
* <b>version��</b>     VER1.00 2010-04-06<br/>
*
**/
public interface ActionDispose 
{
	/**
	 * ���������Ϣ
	 * @param request
	 * @param servlet
	 * @throws Exception
	 */
	public void debugRequest(String inPre,HttpServletRequest request, HttpServlet servlet);  
	
	// ***************************************************************************
	/**
	 * �������ݣ�������Ϣ����
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @return ReqeustMap ���������ļ���
	 */
	// ***************************************************************************
	public RequestMap initRequest(HttpServletRequest request,
			HttpServlet servlet) throws Exception;

	// ***************************************************************************
	/**
	 * �������ݣ�ִ��ҵ��
	 * 
	 * @param rMap
	 *            RequestMap
	 * @param value
	 *            BeanValue
	 * @return BeanValue
	 */
	// ***************************************************************************
	public BeanValue dispose(HttpServletRequest request, HttpServlet servlet,
			RequestMap rMap) throws Exception;

	// ***************************************************************************
	/**
	 * �������ݣ�ִ�н������
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @param value
	 *            BeanValue
	 * @return ����ֵ ִ���Ƿ����
	 */
	// ***************************************************************************
	public boolean disposeResult(HttpServletRequest request, BeanValue value);

	// ***************************************************************************
	/**
	 * �������ݣ�ִ�н������
	 * 
	 * @param response
	 *            HttpServletResponse
	 * @param value
	 *            BeanValue
	 * @return ����ֵ ִ���Ƿ����
	 */
	// ***************************************************************************
	public boolean disposeResult(HttpServletResponse response, BeanValue value);

}
