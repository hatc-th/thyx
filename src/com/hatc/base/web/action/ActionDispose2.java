package com.hatc.base.web.action;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hatc.base.common.BeanValue;
import com.hatc.base.common.RequestMap;

/**
* 
* <b>system：</b>      协同办公平台<br/>
* <b>description：</b> 业务跳转控制器接口定义<br/>
* <b>author：</b>      王洋<br/>
* <b>copyright：</b>	　 北京华安天诚科技有限公司<br/>
* <b>version：</b>     VER1.00 2010-04-06<br/>
*
**/
public interface ActionDispose2 
{
	/**
	 * 输出调试信息
	 * @param request
	 * @param servlet
	 * @throws Exception
	 */
	public void debugRequest(String inPre,HttpServletRequest request, ServletContext servlet);  
	
	// ***************************************************************************
	/**
	 * 处理内容：请求信息处理
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @return ReqeustMap 请求解析后的集合
	 */
	// ***************************************************************************
	public RequestMap initRequest(HttpServletRequest request,
			ServletContext servletContext) throws Exception;

	// ***************************************************************************
	/**
	 * 处理内容：执行业务
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
	 * 处理内容：执行结果处理
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @param value
	 *            BeanValue
	 * @return 布尔值 执行是否进行
	 */
	// ***************************************************************************
	public boolean disposeResult(HttpServletRequest request, BeanValue value);

	// ***************************************************************************
	/**
	 * 处理内容：执行结果处理
	 * 
	 * @param response
	 *            HttpServletResponse
	 * @param value
	 *            BeanValue
	 * @return 布尔值 执行是否进行
	 */
	// ***************************************************************************
	public boolean disposeResult(HttpServletResponse response, BeanValue value);

}
