package com.hatc.common.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.hatc.common.contants.ProjectConstants;
import com.hatc.common.servicestub.ReqIdentity;

/**
* 
* <b>system��</b>      Эͬ�칫ƽ̨<br/>
* <b>description��</b> �û���ʶ������·��������<br/>
* <b>author��</b>      ����<br/>
* <b>modify��</b>      ������<br/>
* <b>copyright��</b>	�� ����������ϿƼ����޹�˾<br/>
* <b>version��</b>     VER1.00 2010-04-06<br/>
*  
**/
public class ProjectFilter implements Filter 
{
	private Logger logger = Logger.getLogger(ProjectFilter.class);

	protected FilterConfig filterConfig;
	private static ReqIdentity fIdentity;
	private static int callCount = 0;
	
	
	public void init(FilterConfig config) throws ServletException {
		filterConfig = config;
	}

	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)
			throws IOException, ServletException {
		
		
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		
//		int pos =-1;
//		String queryString = request.getQueryString();
//		if (queryString!=null ){
//			pos = queryString.indexOf("jquery=1");	//Action.do �е� jquery��ʽ�ύ
//		}
//		if (pos < 0){
//			request.setCharacterEncoding("gbk");
//			response.setContentType("text/html;charset=gbk");
//		}
		

			
//		// ��ȡsessionId(רΪ�ͻ��˵���Web)
//		String sessionId = request.getParameter("sessionId");
//		// ��ȡuserId(רΪ�ͻ��˵���Web)
//		String userId = request.getParameter("userId");
//		// ��ȡtMode(רΪ�ͻ��˵���Web)
//		String tMode = request.getParameter("tMode");
//		// �û���ʶ
//		//ReqIdentity identity = (ReqIdentity) request.getSession().getAttribute(ProjectConstants.SESSION_REQIDENTITY);
//		
//		// ����·��
//		String accessPahth = request.getRequestURI() + (request.getQueryString() != null && !request.getQueryString().equals("") ? "?" + request.getQueryString() : "");
//
//		// ��·��
//		String rootPath = request.getContextPath(); 
//		
//		//TODO ningliyu 2011-12-08
//		//HttpSession session= request.getSession(false);    
//		HttpSession session= request.getSession();  
//		callCount++;
//		if(callCount==Integer.MAX_VALUE)
//			callCount=0;
//		
//		String currentSessionId = ""; 
//		
//		if( null != sessionId && null != userId && null != tMode) 
//		{
//			fIdentity = new ReqIdentity();
//			fIdentity.setSessionId(sessionId);
//			fIdentity.setUserId(userId);
//			fIdentity.setLogonType(tMode);
//			 
//		}
//		else
//		{
//			if(session != null)
//				if(session.getAttribute(ProjectConstants.SESSION_REQIDENTITY)!=null)
//				{
//					fIdentity = (ReqIdentity) session.getAttribute(ProjectConstants.SESSION_REQIDENTITY);
//					 
//				}
//		}
//		  
//		/**
//		
//		if(null == fIdentity && null != sessionId && null != userId && null != tMode) {
//			fIdentity = new ReqIdentity();
//			fIdentity.setSessionId(sessionId);
//			fIdentity.setUserId(userId);
//			fIdentity.setLogonType(tMode);
//		}
//		**/
//		if(fIdentity!=null)
//		{			
//			fIdentity.setTransID(""+callCount);
//		    request.setAttribute("fIdentity", fIdentity);
//		}
//		
//		// �˴���request�������request�﹩ProjectManagerImpl�л�ȡReqIdentity����ʹ��
//		request.setAttribute("fRequest", request);
//		
//		if(logger.isDebugEnabled())
//		{
//			if(session != null)
//			{
//				currentSessionId = session.getId();
//				if(currentSessionId==null)
//					currentSessionId="";
//			} 
//			if (fIdentity == null)  
//			{
//				logger.debug("   session out: callCount: " + callCount + ", Path: " + accessPahth + ", sessionId: "  + currentSessionId);
//			}
//			else
//				logger.debug("   callCount: " + callCount + ", Path: " + accessPahth + ", sessionId: "  + currentSessionId);
//				
//		}
//			 
//		
//		if (fIdentity == null && checkPath(accessPahth, rootPath)) {
//			
//			chain.doFilter(servletRequest, servletResponse);
//		} else if (fIdentity == null) {
//			//System.out.println("-----------------------------------------------------------------------");
//			//System.out.println("-----------------------------------------------------------------------");
//			response.getWriter().write(
//					"<script language='javascript'>top.location='" + rootPath + "/sessionOut.jsp';</script>");
//		} else {
//			 
//			chain.doFilter(servletRequest, servletResponse);
//		}

		chain.doFilter(servletRequest, servletResponse);
	}

	
//	private boolean checkPath(String accessPahth, String rootPath) {
//		String[] path = new String[14];
//		path[0] = rootPath + "/";
//		path[1] = rootPath + "/index.jsp";
//		path[2] = rootPath + "/login.jsp";
//		path[3] = rootPath + "/loginAction.do?method=webLogin";
//		path[4] = rootPath + "/loginAction.do?method=csLogin";
//		path[5] = rootPath + "/loginAction.do?method=index";
//		
//		
//		path[6] = rootPath + "/csLogin.jsp";
//		path[7] = rootPath + "/sessionOut.jsp";
//		path[8] = rootPath + "/loginWait.jsp";
//		path[9] = rootPath + "/ssoLogin.jsp";
//		path[10] = rootPath + "/ssologinWait.jsp";
//		path[11] = rootPath + "/report_forms/testjdbc.jsp";
//		
//		//TODO ningliyu 2011-12-14
//		//����˳�ʱ����ʱ����
//		path[12] = rootPath + "/userAction.do?method=logoutSystem&logoutMark=1";
//		path[13] = rootPath + "/loginAction.do?method=restLogin";
//		
//		
////		path[12] = rootPath + "/dataImport/csExport.jsp";                   //�ͻ�����־����
////		path[13] = rootPath + "/dataImportAction.do?method=exportSysLog";   //ϵͳ��־
////		path[14] = rootPath + "/dataImportAction.do?method=exportMsgLog";   //������־
////		path[15] = rootPath + "/testTaskSheetAction.do?method=testTaskDetail";   //��������
//
//		for (int i = 0; i < path.length; i++) {
//			if (accessPahth.equalsIgnoreCase(path[i])) {
//				return true;
//			}
//			if (i > 0 && accessPahth.indexOf(path[i]) > -1) {
//				return true;
//			}
//		}
//		return false;
//	}
	
	public void destroy() {
		this.filterConfig = null;
	}
}
