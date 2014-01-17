package com.hatc.base.web.action;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.hatc.base.common.BeanValue;
import com.hatc.base.common.RequestMap;
import com.hatc.base.fileUpload.BackGroundService;
import com.hatc.base.utils.IpUtil;
import com.hatc.base.contants.DebugConfig;
import com.hatc.common.contants.ProjectConstants;
import com.hatc.base.contants.SystemConfig;

import com.hatc.common.servicestub.ReqIdentity;

/**
* 
* <b>system：</b>      协同办公平台<br/>
* <b>description：</b> 业务跳转控制器接口实现<br/>
* <b>author：</b>      王洋<br/>
* <b>copyright：</b>	　 北京华安天诚科技有限公司<br/>
* <b>version：</b>     VER1.00 2010-04-06<br/>
*
**/
public class ExecuteProcess2 implements ActionDispose2 
{

	protected final Log logger = LogFactory.getLog(getClass());

	private String transID = "";
	private String requestURL = "";
	
	
	/* (non-Javadoc)
	 * @see com.hatc.base.web.action.ActionDispose#disposeResult(javax.servlet.http.HttpServletResponse, com.hatc.base.common.BeanValue)
	 */
	public boolean disposeResult(HttpServletResponse response, BeanValue value) {
		if (response != null && value != null) {
			return true;
		} else {
			return false;
		}
	}

	/* (non-Javadoc)
	 * @see com.hatc.base.web.action.ActionDispose#disposeResult(javax.servlet.http.HttpServletRequest, com.hatc.base.common.BeanValue)
	 */
	public boolean disposeResult(HttpServletRequest request, BeanValue value) {
		if (request != null && value != null) {
			// 放在请求中的信息
			Map<String, Object> rMap = value.getRequestMap();
			// 放在Session中的信息
			Map<String, Object> sMap = value.getSessionMap();
			// 放在请求中的返回参数信息
			Map<String, Object> eMap = value.getParamMap();
			// 移除Session中的信息
			List<String> sList = value.getRemoveSessionList();
			Iterator<String> it;
			// 将信息放入请求中
			if (rMap != null && rMap.size() > 0) {
				it = rMap.keySet().iterator();
				while (it.hasNext()) {
					String key = it.next();
					request.setAttribute(key, rMap.get(key));
				}
			}
			// 将SESSION信息放入SESSION中
			if (sMap != null && sMap.size() > 0) {
				it = sMap.keySet().iterator();
				while (it.hasNext()) {
					String key = it.next();
					request.getSession().setAttribute(key, sMap.get(key));
				}
			}
			// 将错误信息放入请求中
			if (eMap != null && eMap.size() > 0) {
				it = eMap.keySet().iterator();
				while (it.hasNext()) {
					String key = it.next();
					request.setAttribute(key, eMap.get(key));
				}
			}
			// 移除Session中的信息
			if (sList != null && sList.size() > 0) {
				it = sList.iterator();
				while (it.hasNext()) {
					String key = it.next();
					request.getSession().removeAttribute(key);
				}
			}
			return true;
		} else {
			return false;
		}
	}

	/* (non-Javadoc)
	 * @see com.hatc.base.web.action.ActionDispose#initRequest(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServlet)
	 */
	@SuppressWarnings("unchecked")
	public RequestMap initRequest(HttpServletRequest request, ServletContext servletContext) throws Exception
	{
		RequestMap rMap = new RequestMap();
		// 获得请求中的参数
		rMap.putAll(request.getParameterMap());
		// 请求路径
		StringBuffer url = new StringBuffer();
		url.append(request.getRequestURI());
		url.append("?" + request.getQueryString());
		requestURL= url.toString();
		
		rMap.addParameter(RequestMap.REQUEST_URL, requestURL);
		// 工程绝对路径
		rMap.addParameter(RequestMap.PROJECT_URL, servletContext.getRealPath("/"));
		// 请求的IP地址
		rMap.addParameter(RequestMap.REQUEST_IP, IpUtil.getIpAddr(request));
		// 本机IP
		rMap.addParameter(RequestMap.LOCAL_IP, request.getServerName());
		// 本机服务端口
		rMap.addParameter(RequestMap.LOCAL_PORT, String.valueOf(request.getServerPort()));
		// User-Agent
		rMap.addParameter(RequestMap.USER_AGENT, request.getHeader(RequestMap.USER_AGENT));

		Enumeration enu_a = request.getAttributeNames();
		String name = "";
		while (enu_a.hasMoreElements()) {
			name = (String) enu_a.nextElement();
			rMap.addParameter(name, request.getAttribute(name));
		}

		// 将session中的值放入map中
		Enumeration enu_s = request.getSession().getAttributeNames();
		while (enu_s.hasMoreElements()) {
			name = (String) enu_s.nextElement();
			rMap.addParameter(name, request.getSession().getAttribute(name));
		}

		// 请求类型
		String reqType = request.getContentType();
		// 文件上传时参数处理
		if (reqType != null && reqType.indexOf("multipart/form-data") > -1) {
			try {
				Map<String, ArrayList<String>> params = new HashMap<String, ArrayList<String>>();
				BackGroundService uploadService = new BackGroundService();
				List<FileItem> uploadFileList = new ArrayList<FileItem>();
				List fileList = uploadService.getFileList(request);
				FileItem fileItem = null;
				String field = "";
				for (Iterator i = fileList.iterator(); i.hasNext();) {
					fileItem = (FileItem) i.next();
					field = fileItem.getFieldName();
					if (fileItem.isFormField()) {
						ArrayList<String> tempList = params.get(field);
						if (tempList == null){
							tempList = new ArrayList<String>();
						}
						tempList.add(new String(fileItem.getString().getBytes("ISO-8859-1"), "GBK"));
						params.put(field, tempList);
						//rMap.addParameter(field, new String(fileItem.getString().getBytes("ISO-8859-1"), "GBK"));
					} else if (!fileItem.isFormField() && fileItem.getName().length() > 0) {
						uploadFileList.add(fileItem);
					}
				}
				
				for (Iterator iterator = params.keySet().iterator(); iterator.hasNext();) {
					String key = (String) iterator.next();
					ArrayList<String> tempList = params.get(key);
					if (tempList.size() > 1) {
						String[] strArray = new String[tempList.size()];
						for (int i = 0; i < tempList.size(); i++) {
							strArray[i] = tempList.get(i);
						}
						rMap.addParameter(key, strArray);
					} else {
						rMap.addParameter(key, tempList.get(0));
					}
				}
				
				rMap.addParameter(RequestMap.UPLOAD_FILE_LIST, uploadFileList);
			} catch (ServletException e) {
				e.printStackTrace();
				logger.error(e.getMessage(),e);
				throw e;
			} catch (IOException e) {
				e.printStackTrace();
				logger.error(e.getMessage(),e);
				throw e;
			}catch(Exception e){
				e.printStackTrace();
				logger.error(e.getMessage(),e);
				throw e;
			}
		}
		
		////////////////////////////////////////////////////////////////////////////////
		 
		try
		{
		//TODO ningliyu 2012-12-02
  
			StringBuffer sb = new StringBuffer();
			ReqIdentity identity = null;
			
		if(logger.isDebugEnabled()==true)
		{ 
		 	if(request.getSession()!=null)
			{
				if(request.getSession().getAttribute(ProjectConstants.SESSION_REQIDENTITY)!=null)
				{
					identity = (ReqIdentity) request.getSession().getAttribute(ProjectConstants.SESSION_REQIDENTITY);
					transID = identity.getTransID();
				}
			} 
			sb.append("------------------------------------------------------------");
			sb.append(SystemConfig.CHAR_LINE);
			
			sb.append("    request: transID:"+ transID + ",url: "+ requestURL);
			 
			if(identity!=null)
			{
				sb.append("        identity info:");
				sb.append("  userId:");
				sb.append(identity.getUserId());
				sb.append(", roleId:");
				sb.append(identity.getUserRole());
				sb.append(", sessionId:");
				sb.append(identity.getSessionId());
				sb.append(", fid:");
				sb.append(identity.getFunctionId());
				sb.append(", ip:");
				sb.append(identity.getIpAddress());				
				sb.append(", LogonType:");
				sb.append(identity.getLogonType());
				
			} 
		 
			logger.debug(sb.toString()); 
	
			debugRequest(request,servletContext);
		}
			 
		}catch(Exception e){
			logger.error(e.getMessage(),e);
		} 
		
		return rMap;
	}

	public void debugRequest(HttpServletRequest request, ServletContext servlet)  
	{
		debugRequest("request",request,servlet);
	}
	
	public void debugRequest(String inPre,HttpServletRequest request, ServletContext servlet)  
	{ 
		 try{
			 
		if(logger.isDebugEnabled()==false)
			return;
		 	
		if(DebugConfig.debugClientRequestFlag == false)
			return;
			 
		// 请求路径
		String LINE = "\r\n";
		StringBuffer sb = new StringBuffer();
		sb.append("    debug ");
		sb.append(inPre );
		sb.append("  begin......");
		//sb.append("debugRequest begin......"); 
		sb.append(LINE);
		sb.append("        "); 
		sb.append(request.getRequestURI());
		sb.append("?" + request.getQueryString());
		sb.append(LINE);
		
		sb.append("        PROJECT_URL: " + servlet.getRealPath("/"));
		sb.append(LINE);
		
		sb.append("        REQUEST_IP: " + IpUtil.getIpAddr(request));
		sb.append(LINE);
		
		sb.append("        LOCAL_IP: " + request.getServerName());
		sb.append(LINE);
		
		sb.append("        LOCAL_PORT: " + String.valueOf(request.getServerPort()));
		sb.append(LINE);
		
		sb.append("        USER_AGENT: " + request.getHeader(RequestMap.USER_AGENT));
		sb.append(LINE);
		sb.append("        transID: " + transID);
		sb.append(LINE);
		
		sb.append("    request.getAttribute  begin..."); 
		sb.append(LINE);

		Enumeration enu_a = request.getAttributeNames();
		String name = "";
		int count = 0;
		while (enu_a.hasMoreElements()) {
			name = (String) enu_a.nextElement();			
			sb.append("             " + (count++) + " : " + name + " : "+  request.getAttribute(name)); 
			sb.append(LINE);
		}
		sb.append("    request.getAttribute  end..."); 
		sb.append(LINE);
		
		sb.append("    session.getAttribute  begin..."); 
		sb.append(LINE);
		
		// 将session中的值放入map中
		Enumeration enu_s = request.getSession().getAttributeNames();
		count = 0;
		while (enu_s.hasMoreElements()) {
			name = (String) enu_s.nextElement();			 
			sb.append("       " + (count++) + " : " + name + " : "+   request.getSession().getAttribute(name));
			sb.append(LINE);			
		}
		sb.append("    session.getAttribute  end..."); 
		sb.append(LINE);

		// 请求类型
		String reqType = request.getContentType();
		// 文件上传时参数处理
		if (reqType != null && reqType.indexOf("multipart/form-data") > -1) {
			sb.append("multipart/form-data......"); 
			sb.append(LINE);
		}
		sb.append("    debug ");
		sb.append(inPre );
		sb.append("  end......");
		sb.append(LINE); 
		
		logger.debug(sb.toString());
		 }catch(Exception e){
			 e.printStackTrace();
			 logger.error(e);
		 }
	}
	
	/* (non-Javadoc)
	 * @see com.hatc.base.web.action.ActionDispose#dispose(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServlet, com.hatc.base.common.RequestMap)
	 */
	public BeanValue dispose(HttpServletRequest request, HttpServlet servlet, RequestMap rMap) throws Exception {
		// Spring Manager 名称
		String managerName = request.getParameter("manager");
		// Manager Method 方法名称
		String method = request.getParameter("function");
		// 获得Manager实例
		Object obj = WebApplicationContextUtils.getRequiredWebApplicationContext(servlet.getServletContext()).getBean(
				managerName);
		// 获得方法
		Method met = obj.getClass().getMethod(method, new Class[] { RequestMap.class });
		// 执行方法并获执行结果
		BeanValue value = (BeanValue) met.invoke(obj, new Object[] { rMap });
		return value;
	}
}
