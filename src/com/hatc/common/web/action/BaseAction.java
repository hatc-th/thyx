package com.hatc.common.web.action;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.hatc.base.common.BaseException;
import com.hatc.base.common.BeanValue;
import com.hatc.base.common.RequestMap;
import com.hatc.base.contants.BaseConstants;
import com.hatc.base.contants.SystemConfig;
import com.hatc.base.web.action.RootAction;
import com.hatc.common.contants.ProjectCode;
import com.hatc.common.contants.ProjectConstants;
import com.hatc.common.servicestub.ReqIdentity;
/**
 * 
 * <b>system：</b> 协同办公平台<br/>
 * <b>description：</b> 系统基础Action<br/>
 * <b>author：</b> 王洋<br/>
 * <b>copyright：</b>
 * 北京华安天诚科技有限公司<br/>
 * <b>version：</b> VER1.00 2010-04-06<br/>
 * 
 * <b>version：</b> VER1.01 2011-12-10<br/>
 *    processException 方法: 增加了如下:
 *           //TODO ningliyu 2011-12-10
			value.addRequestMap("messageArgs", e.getMessage());
			
			使得在action层抛出异常时, 界面能够显示异常信息.
	
	  修改了: ProjectConstants.FRAME_WINDOW 值为:noButton.  作用是不显示返回按钮.
	     如:
	     processException(map, value, ex, ProjectConstants.FRAME_WINDOW, TtimsCode.AIRSTATE_SELECT_ERROR);	
 * 
 * 
 */
public class BaseAction extends RootAction 
{
	protected final Log logger = LogFactory.getLog(getClass());
	private String requestURL = "";

	private boolean FomsExceptionFlag  = false;
	
	/**
	 * 获取上传文件大小设置
	 * 
	 * @param request
	 *            请求
	 * @return 大小
	 */
	protected String getUploadMaxSize(HttpServletRequest request) {
		return (String) request.getSession().getAttribute(BaseConstants.UPLOAD_FILE_MAX_SIZE);
	}
	
	protected void disposeRequest(HttpServletRequest request, BeanValue value)
	{
		disposeRequest(request,value,null);
	}
	
	/**
	 * 处理放入请求中的信息 disposeRequest(request：请求，BeanValue:信息集合)
	 */
	protected void disposeRequest(HttpServletRequest request, BeanValue value,ActionMapping aActionMapping) {
	 
		
		// 请求路径
		StringBuffer url = new StringBuffer();
		url.append(request.getRequestURI());
		url.append("?" + request.getQueryString());
		requestURL = url.toString();
		
		// 是否是客户端调用 
		String tMode = request.getParameter("tMode");
		 
		
		// 如果是客户端调用则初始跳转页面为forward页面
		if(null != tMode && tMode.equals("C")) {
		
			//requestUrl
			String newRequestUrl = requestURL.replace("&tMode=C", "");
	
			//TODO ningliyu 2012-1-1     
			value.setForward("clientToWebForward");
			// 去掉tMode参数否则参数中一直有tMode参数则将不断进入forward页面
			value.addRequestMap("requestUrl", newRequestUrl );
			
			if(logger.isDebugEnabled())
			{
				StringBuffer sb = new StringBuffer();
				sb.append("    client url:");
				sb.append(requestURL);
				sb.append(SystemConfig.CHAR_LINE);
				sb.append("       newUrl:");
				sb.append(newRequestUrl);
				logger.debug(sb.toString());
			}
			
			//value.addRequestMap("requestUrl", requestURL.replace("&tMode=C", ""));
			//value.addRequestMap("requestUrl", url.toString().replace("&tMode=C", ""));
		}   
		
		// 放在请求中的信息
		Map rMap = value.getRequestMap();
		// 放在Session中的信息
		Map sMap = value.getSessionMap();
		// 放在请求中的错误信息
		Map pMap = value.getParamMap();
		// 将信息放入请求中
		Iterator it = rMap.keySet().iterator();
		String key = "";
		while (it.hasNext()) {
			key = (String) it.next();
			request.setAttribute(key, rMap.get(key));
		}
		// 将SESSION信息放入SESSION中
		it = sMap.keySet().iterator();
		while (it.hasNext()) {
			key = (String) it.next();
			request.getSession().setAttribute(key, sMap.get(key));
		}
		// 将返回参数放入请求中
		it = pMap.keySet().iterator();
		while (it.hasNext()) {
			key = (String) it.next();
			request.setAttribute(key, pMap.get(key));
		}
		      
		
		try{ 
		 
		if(logger.isDebugEnabled()==true )
		{ 
			ReqIdentity identity = null;
			String transID = "";
			if(request.getSession()!=null)
			{
				if(request.getSession().getAttribute(ProjectConstants.SESSION_REQIDENTITY)!=null)
				{
					identity = (ReqIdentity) request.getSession().getAttribute(ProjectConstants.SESSION_REQIDENTITY);
					transID = identity.getTransID();
				}
			}
			
			StringBuffer sb = new StringBuffer();
			sb.append("    response: transID:"+ transID );
			sb.append(" ,class: "+ this.getClass().getName() );
			 
			if(aActionMapping!=null)
			{    
				if(value.getForward()!=null)
				{
				    ActionForward forward = aActionMapping.findForward(value.getForward());
					sb.append(SystemConfig.CHAR_LINE);
					//TODO ningliyu 2012-1-1
					//处理空值
					if(forward!=null)
					{ 
						if(forward.getPath()!=null)
							sb.append("    path: " + forward.getPath());
						if(value.getForward()!=null)
							sb.append("   ,forward: " + value.getForward() ); 
					}
				}
			}
			
			sb.append(SystemConfig.CHAR_LINE);
			sb.append("============================================================");
			
			logger.debug(sb.toString());
	  	 
		}
		   }catch(Exception e){
			   e.printStackTrace();
			   logger.error(e.getMessage(),e);
		   } 
	}
	
	 
	/**
	 * 处理内容：下载文件
	 * 
	 */
	protected void downFile(HttpServletResponse response, BeanValue value) throws IOException {
		// 获取参数
		String content_type = (String) value.getRequestMap("content_type");
		String postfix = (String) value.getRequestMap("postfix");
		byte[] out = (byte[]) value.getRequestMap("content");
		String file_name = new String(((String) value.getRequestMap("fileTitle")).getBytes("GBK"), "ISO-8859-1");
		// 非常重要
		response.reset();
		// 纯下载方式
		response.setContentType(content_type);
		response.setHeader("Content-Disposition", "attachment;filename=\"" + file_name + "." + postfix + "\"");
		// 设置下载内容大小
		response.setContentLength(out.length);
		BufferedOutputStream output = null;
		try {
			output = new BufferedOutputStream(response.getOutputStream());
			output.write(out, 0, out.length);
			response.flushBuffer();
		} catch (Exception e) {
		}
		// 用户可能取消了下载
		finally {
			try{
				if (output != null) 
				{				
					output.close();
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}

	/**
	 * 处理内容：出现异常后,对象参数处理
	 * 
	 * @param map
	 *            前台传入的值对象地图
	 * @param value
	 *            值对象
	 * @param cas
	 *            类
	 * 
	 */
	protected void initParam(RequestMap map, BeanValue value, Class cas) {
		Field[] fields = cas.getDeclaredFields();
		String param = "";
		for (Field field : fields) {
			param = map.getString(field.getName());
			if (param != null && !param.equals("")) {
				value.addParamMap(field.getName(), param);
			}
		}
		value.addRequestMap(BaseConstants.SYSTEM_PARAM, value.getParamMap());
	}

	/**
	 * 处理内容：操作成功
	 * 
	 * @param BeanValue
	 *            value 传入的值对象
	 * @param String
	 *            类及方法名称
	 * @param String
	 *            forwardCode
	 * 
	 */
	protected void processSucceed(BeanValue value, String succeed_forward, String code, String forward) {
		// 资源文件Key
		value.addRequestMap(BaseConstants.RESOURCES_KEY, code);
		// 资源文件Key
		value.addRequestMap(BaseConstants.SYSTEM_FORWARD, succeed_forward);
		// 资源文件定位
		value.addRequestMap(BaseConstants.RESOURCES, "Resources");
		// 返回参数
		value.addRequestMap(BaseConstants.SYSTEM_PARAM, value.getParamMap());
		// 目标页面
		value.setForward(forward);
	}
              
	/**
	 * 处理内容：操作成功
	 * 
	 * @param BeanValue
	 *            value 传入的值对象
	 * @param String
	 *            类及方法名称
	 * @param String
	 *            forwardCode
	 * 
	 */
	protected void processSucceed(BeanValue value, String succeed_forward, String code) {
		processSucceed(value, succeed_forward, code, ProjectConstants.SYSTEM_RESULT);
	}

	private String printDebugExceptionInfo(RequestMap map,BeanValue value, BaseException e, String system_forward, String resource,
			String forward)
	{
		 
		String message = "";
		String messageOfSystem = "";
		
		//TODO ningliyu 2012-12-10 
		//统一打印提示信息......
 
		String LINE = "\n";
		ReqIdentity identity = null;		
		if(map.getObject(ProjectConstants.SESSION_REQIDENTITY)!=null)
			identity = (ReqIdentity) map.getObject(ProjectConstants.SESSION_REQIDENTITY);
		
		String transID = "";
 
		String ipAddress = "";
 
		if(identity!=null)
		{
		   transID = identity.getTransID(); 
		   
	       ipAddress = identity.getIpAddress();
	       if(ipAddress==null)
	        	ipAddress=""; 
		} 
		
		////////////////////////////////////////////////////////////////////////////////////////////////
		String exceptionCode = e.getExceptionCode();
		if(exceptionCode==null)
			exceptionCode=""; 

		if(e.getMessage() != null)
			message = e.getMessage();
		else
			message = e.getExceptionCode();
		
		messageOfSystem = e.getMessageOfSystem();
		if(messageOfSystem==null)
			messageOfSystem = ""; 
		
		StringBuffer sb = new StringBuffer();
		
		value.addRequestMap("logoutMark", "1");

						
        String target = "";
        target= map.getString(BaseConstants.SYSTEM_BACK_TARGET);
        if(target==null)
        	target="";

        String logoutMark = "";
        logoutMark= map.getString("logoutMark");
        if(logoutMark==null)
        	logoutMark="";
       
 
		sb.append( "    exception:" );	 
		if(identity!=null)
		    sb.append(getUserReqIdentityInfo(identity));
	     
		//sb.append(identity.toString());
		sb.append( LINE );
		sb.append("    ecode: "+ exceptionCode );	
		sb.append(",msg: "+ message );	
		sb.append(",mos: "+ messageOfSystem );
		sb.append( LINE );
		sb.append("    to: "+ forward );		 
		sb.append(",sfd: "+ system_forward );
		sb.append( LINE );	
		
		/**sb.append("    resource: "+ resource );
		sb.append( LINE );
		sb.append("    target: "+ target );
		sb.append( LINE );
		sb.append("    logoutMark: "+ logoutMark );
		sb.append( LINE );**/
	/**
		sb.append( "3: sinfo:" );
		sb.append( LINE );
		
		if(FomsExceptionFlag==true)
		{
			sb.append("   FomsExcep: ");
			sb.append( ef.getFaultcode());
			sb.append("  ");
			sb.append(ef.getReason());
			sb.append( LINE );
		} 
		**/
		//sb.append(ExceptionUtil.getExceptionMessage("",ef));
		//else
		//	sb.append(ExceptionUtil.getExceptionMessage("",e));  
 
		log.error(sb.toString());
 
		return sb.toString(); 
		 
	}
	/**
	 * 处理内容：异常信息处理
	 * 
	 * @param BeanValue
	 *            value 传入的值对象
	 * @param e
	 *            异常类
	 * @param info
	 *            人工加入的信息
	 * @param key
	 *            异常编码
	 * @param forwardCode
	 *            返回页编码
	 * @param resource
	 *            资源文件编码
	 * @param forward
	 *            目标页面编码
	 * 
	 */
	
	public String getUserReqIdentityInfo(ReqIdentity inReqIdentity) {
	    String line = "\r\n";
		StringBuffer sb = new StringBuffer();
		sb.append("   transID:" + inReqIdentity.getTransID());
		sb.append(",user:" + inReqIdentity.getUserId());
		sb.append(",role:" + inReqIdentity.getUserRole());
		sb.append(",ip:" + inReqIdentity.getIpAddress());				
		sb.append(",funcid:" + inReqIdentity.getFunctionId());
		sb.append(",logonType:" + inReqIdentity.getLogonType());		
		sb.append("session:" + inReqIdentity.getSessionId());	 
		return sb.toString();
	}

	protected void processException(RequestMap map,BeanValue value, BaseException e, String system_forward, String resource,
			String forward) {
		
		String messageOfSystem = "";

 		
		value.addRequestMap(BaseConstants.RESOURCES_KEY, e.getExceptionCode());
	 	
		value.addRequestMap("messageOfSystem", messageOfSystem);    
		  
		value.addRequestMap(BaseConstants.SYSTEM_FORWARD, system_forward);
		// 资源文件定位
		value.addRequestMap(BaseConstants.RESOURCES, resource);
		// 返回参数
		value.addRequestMap(BaseConstants.SYSTEM_PARAM, e.getParamMap());
		// 异常
		value.addRequestMap("baseEx", e);
		// 目标页面
		value.setForward(forward);
		
		try{
			messageOfSystem = printDebugExceptionInfo(map,value,e,system_forward,resource,forward);
		}catch(Exception ex){
			//logger.error(ex.getMessage(),ex);
			
		}
		
	}

	/**
	 * 处理内容：异常信息处理
	 * 
	 * @param BeanValue
	 *            value 传入的值对象
	 * @param e
	 *            异常类
	 * @param key
	 *            异常编码
	 * @param forwardCode
	 *            返回页编码
	 * 
	 */
	protected void processException(RequestMap map,BeanValue value, BaseException e, String system_forward) {
		 
		processException(map,value, e, system_forward, "Resources", ProjectConstants.SYSTEM_RESULT);
	}

	/**
	 * 处理内容：异常处理
	 * 
	 * @param map
	 *            TODO
	 * @param value
	 *            传入值对象
	 * @param e
	 *            异常类
	 * @param className
	 *            类名称
	 * @param eCode
	 *            异常码
	 * 
	 */
	protected BaseException processException(RequestMap map, BeanValue value, Exception e, String className, String eCode) {
		BaseException ex = new BaseException();
		FomsExceptionFlag =false;
		
		//System.out.println("processException....................");
//		System.out.println("class: "+e.getClass().getName());
		
		  
		/*if (e instanceof FomsException) {
			
			//System.out.println("FomsException....................");
			ef = (FomsException) e; 
			
			FomsExceptionFlag = true;  
			
			//TODO ningliyu 2012-1-8
			//支持99 返回代码: 直接获取错误信息显示!
			
			if(ef.getFaultcode()==99)
			{
				 
				ex.setExceptionCode("msg.99999.99");
				ex.setMessageArgs( new String[] { ef.getReason() });
			}
			else
			    ex.setExceptionCode("99999." + String.valueOf(ef.getFaultcode()));
			
			
			ex.setMessage(ef.getReason());
			ex.setReason(ef.getReason());
			
			log.error("FomsException: " + ef.getFaultcode() + "  : " +  ef.getReason());
     		
			//ex.setMessage(ef.getMessage());
			//ex.setReason(ef.getReason());
			 
			String target = "";
			switch (ef.getFaultcode()) {
			case FomsErrorCode.NOT_LOGON:
				// 设置系统退出标志(有此标志页面在退出的时候将不弹出提示对话框)
				value.addRequestMap("logoutMark", "1");
				target = "_parent";
				className = "system.index";
				break;
			case FomsErrorCode.KICK_OUT:
				// 设置系统退出标志(有此标志页面在退出的时候将不弹出提示对话框)
				value.addRequestMap("logoutMark", "1");
				target = "_parent";
				className = "system.index";
				break;
			case FomsErrorCode.NETWORK_ERR:
				// 设置系统退出标志(有此标志页面在退出的时候将不弹出提示对话框)
				value.addRequestMap("logoutMark", "1");
				target = "_parent";
				className = "system.index";
				break;
			case FomsErrorCode.AUTH_ERR:
				target = "_parent";
				className = "noButton";
				break;
			case FomsErrorCode.BAD_PWD:
				break;
			case FomsErrorCode.DB_ERR:
				break;
			case FomsErrorCode.BAD_IP_CONF:
				break;
			case FomsErrorCode.LOCK_FAIL:
				break;
			case FomsErrorCode.ALREADY_LOGON:
				break;
			case FomsErrorCode.DB_ERR_CREATE:
				break;
			case FomsErrorCode.DB_ERR_READ:
				break;
			case FomsErrorCode.DB_ERR_UPDATE:
				break;
			case FomsErrorCode.DB_ERR_DELETE:
				break;
			case FomsErrorCode.BAD_LOGIN_MODE:
				break;
			case FomsErrorCode.INVALID_USER:
				break;
			case FomsErrorCode.CONSTRAINT_ERR:
				break;
			case FomsErrorCode.DEPEND_ERR:
				break;
			case FomsErrorCode.INCOMPATIBLE_VER:
				break;
			case FomsErrorCode.EXCEL_FILE_ERR:
				break;
			case FomsErrorCode.EXCEL_CONTENT_ERR:
				break;
			case FomsErrorCode.EXCEL_VERSION_ERR:
				break;
			case FomsErrorCode.PASS_VALID_DATE:
				break;
			case FomsErrorCode.MAX_PWD_ERROR:
				break;
			case 99:
				break;
			default:
				target = "_self";
				ex.setExceptionCode("99999.-1");        
			}
			value.addRequestMap(BaseConstants.SYSTEM_BACK_TARGET, target);
		} else*/
		if (e instanceof BaseException) {
			//System.out.println("BaseException....................");
			ex = (BaseException) e;
			if (ex.getExceptionCode() == null || ex.getExceptionCode().equals("")) {
				ex.setExceptionCode(eCode);
			}
		} else {
			//System.out.println("Exception...................." + eCode );
			ex = new BaseException();
			ex.setRootCause(e.getCause());
			ex.setStackTrace(e.getStackTrace());
			
			ex.setExceptionCode(eCode);
			
			String message = e.getMessage();
		
			String clientMessage=message;
			if(message==null)
				message="";
			ex.setMessageOfSystem(message);
			
			int index1=message.indexOf("ORA-");
			int index2=message.indexOf("Could not execute");
			int index3=message.indexOf("Could not open");
			int index4=message.indexOf("Hibernate");
			
			
			if(index1>=0 || index2 >=0 || index3 >=0 || index4 >=0)  
			{
				//System.out.println(" set exception 99999.12 ");
				//	System.out.println( e.getMessage());
				
				e.printStackTrace(); 
				clientMessage = "";
				ex.setExceptionCode("99999.12");
			} 	 			
			
			else if(e.getClass().getName().compareTo("java.lang.NumberFormatException")==0)
			{
				//System.out.println(" set exception NumberFormatException ");
				clientMessage = "";
				ex.setExceptionCode("msg.java.lang.NumberFormatException");
			}
			
			ex.setMessage( clientMessage );
			   
		 
			value.addRequestMap("messageArgs", clientMessage);
			
			// 用户登录过程中出现未知错误
			
			if(null != eCode && eCode.equals(ProjectCode.USER_LOGIN_ERROR)) {
				// 设置系统退出标志(有此标志页面在退出的时候将不弹出提示对话框)
				value.addRequestMap("logoutMark", "1");
				value.addRequestMap(BaseConstants.SYSTEM_BACK_TARGET, "_parent");
			}
			//TODO ningliyu 2011-1209 add
			else
			{
				value.addRequestMap(BaseConstants.SYSTEM_BACK_TARGET, "_self");
			}
		}

		Map<String, Object> paramMap = new HashMap<String, Object>();
		String str = "";
		Class oClass = (Class) value.getRequestMap(BaseConstants.SYSTEM_BACK_CLASS);
		if (oClass != null) {
			// 得到所有的属性
			Field[] fields = oClass.getDeclaredFields();
			for (Field field : fields) {
				str = map.getString(field.getName());
				paramMap.put(field.getName(), str);
			}
		}

		String[] paramName = (String[]) value.getRequestMap(BaseConstants.SYSTEM_BACK_PARAM_NAME);
		if (paramName != null) {
			for (String name : paramName) {
				str = map.getString(name);
				paramMap.put(name, str);
			}
		}

		String[][] param = (String[][]) value.getRequestMap(BaseConstants.SYSTEM_BACK_PARAM);
		if (param != null) {
			for (String[] par : param) {
				paramMap.put(par[0], par[1]);
			}
		}

		ex.getParamMap().putAll(paramMap);

		ex.getParamMap().putAll(value.getParamMap());

		// 异常处理(返回的值对象,异常,自定义异常显示信息,返回路径名称)
		processException(map,value, ex, className);
		
		return ex;
	}
	
	/**
	 * ajax返回数据处理
	 * @param object 返回的对象
	 * @param response 
	 */
	protected void ajaxReturn(Object object,HttpServletResponse response){

		String outString="";
		if (object instanceof List){
			JSONArray jsonArray = JSONArray.fromObject(object);
			outString = jsonArray.toString();
		}else{
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("data",object);
			JSONObject jsonObject = JSONObject.fromObject(map);
			outString = jsonObject.toString();
		}
			
		PrintWriter write = null;
		try {
			response.setContentType("text/xml; charset=UTF-8");
			response.setHeader("Cache-Control", "no-cache");			
			write =response.getWriter(); 
			write.write(outString);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if (write!=null)
				write.close();
		}		
		
	}
}
