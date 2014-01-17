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
 * <b>system��</b> Эͬ�칫ƽ̨<br/>
 * <b>description��</b> ϵͳ����Action<br/>
 * <b>author��</b> ����<br/>
 * <b>copyright��</b>
 * ����������ϿƼ����޹�˾<br/>
 * <b>version��</b> VER1.00 2010-04-06<br/>
 * 
 * <b>version��</b> VER1.01 2011-12-10<br/>
 *    processException ����: ����������:
 *           //TODO ningliyu 2011-12-10
			value.addRequestMap("messageArgs", e.getMessage());
			
			ʹ����action���׳��쳣ʱ, �����ܹ���ʾ�쳣��Ϣ.
	
	  �޸���: ProjectConstants.FRAME_WINDOW ֵΪ:noButton.  �����ǲ���ʾ���ذ�ť.
	     ��:
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
	 * ��ȡ�ϴ��ļ���С����
	 * 
	 * @param request
	 *            ����
	 * @return ��С
	 */
	protected String getUploadMaxSize(HttpServletRequest request) {
		return (String) request.getSession().getAttribute(BaseConstants.UPLOAD_FILE_MAX_SIZE);
	}
	
	protected void disposeRequest(HttpServletRequest request, BeanValue value)
	{
		disposeRequest(request,value,null);
	}
	
	/**
	 * ������������е���Ϣ disposeRequest(request������BeanValue:��Ϣ����)
	 */
	protected void disposeRequest(HttpServletRequest request, BeanValue value,ActionMapping aActionMapping) {
	 
		
		// ����·��
		StringBuffer url = new StringBuffer();
		url.append(request.getRequestURI());
		url.append("?" + request.getQueryString());
		requestURL = url.toString();
		
		// �Ƿ��ǿͻ��˵��� 
		String tMode = request.getParameter("tMode");
		 
		
		// ����ǿͻ��˵������ʼ��תҳ��Ϊforwardҳ��
		if(null != tMode && tMode.equals("C")) {
		
			//requestUrl
			String newRequestUrl = requestURL.replace("&tMode=C", "");
	
			//TODO ningliyu 2012-1-1     
			value.setForward("clientToWebForward");
			// ȥ��tMode�������������һֱ��tMode�����򽫲��Ͻ���forwardҳ��
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
		
		// ���������е���Ϣ
		Map rMap = value.getRequestMap();
		// ����Session�е���Ϣ
		Map sMap = value.getSessionMap();
		// ���������еĴ�����Ϣ
		Map pMap = value.getParamMap();
		// ����Ϣ����������
		Iterator it = rMap.keySet().iterator();
		String key = "";
		while (it.hasNext()) {
			key = (String) it.next();
			request.setAttribute(key, rMap.get(key));
		}
		// ��SESSION��Ϣ����SESSION��
		it = sMap.keySet().iterator();
		while (it.hasNext()) {
			key = (String) it.next();
			request.getSession().setAttribute(key, sMap.get(key));
		}
		// �����ز�������������
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
					//�����ֵ
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
	 * �������ݣ������ļ�
	 * 
	 */
	protected void downFile(HttpServletResponse response, BeanValue value) throws IOException {
		// ��ȡ����
		String content_type = (String) value.getRequestMap("content_type");
		String postfix = (String) value.getRequestMap("postfix");
		byte[] out = (byte[]) value.getRequestMap("content");
		String file_name = new String(((String) value.getRequestMap("fileTitle")).getBytes("GBK"), "ISO-8859-1");
		// �ǳ���Ҫ
		response.reset();
		// �����ط�ʽ
		response.setContentType(content_type);
		response.setHeader("Content-Disposition", "attachment;filename=\"" + file_name + "." + postfix + "\"");
		// �����������ݴ�С
		response.setContentLength(out.length);
		BufferedOutputStream output = null;
		try {
			output = new BufferedOutputStream(response.getOutputStream());
			output.write(out, 0, out.length);
			response.flushBuffer();
		} catch (Exception e) {
		}
		// �û�����ȡ��������
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
	 * �������ݣ������쳣��,�����������
	 * 
	 * @param map
	 *            ǰ̨�����ֵ�����ͼ
	 * @param value
	 *            ֵ����
	 * @param cas
	 *            ��
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
	 * �������ݣ������ɹ�
	 * 
	 * @param BeanValue
	 *            value �����ֵ����
	 * @param String
	 *            �༰��������
	 * @param String
	 *            forwardCode
	 * 
	 */
	protected void processSucceed(BeanValue value, String succeed_forward, String code, String forward) {
		// ��Դ�ļ�Key
		value.addRequestMap(BaseConstants.RESOURCES_KEY, code);
		// ��Դ�ļ�Key
		value.addRequestMap(BaseConstants.SYSTEM_FORWARD, succeed_forward);
		// ��Դ�ļ���λ
		value.addRequestMap(BaseConstants.RESOURCES, "Resources");
		// ���ز���
		value.addRequestMap(BaseConstants.SYSTEM_PARAM, value.getParamMap());
		// Ŀ��ҳ��
		value.setForward(forward);
	}
              
	/**
	 * �������ݣ������ɹ�
	 * 
	 * @param BeanValue
	 *            value �����ֵ����
	 * @param String
	 *            �༰��������
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
		//ͳһ��ӡ��ʾ��Ϣ......
 
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
	 * �������ݣ��쳣��Ϣ����
	 * 
	 * @param BeanValue
	 *            value �����ֵ����
	 * @param e
	 *            �쳣��
	 * @param info
	 *            �˹��������Ϣ
	 * @param key
	 *            �쳣����
	 * @param forwardCode
	 *            ����ҳ����
	 * @param resource
	 *            ��Դ�ļ�����
	 * @param forward
	 *            Ŀ��ҳ�����
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
		// ��Դ�ļ���λ
		value.addRequestMap(BaseConstants.RESOURCES, resource);
		// ���ز���
		value.addRequestMap(BaseConstants.SYSTEM_PARAM, e.getParamMap());
		// �쳣
		value.addRequestMap("baseEx", e);
		// Ŀ��ҳ��
		value.setForward(forward);
		
		try{
			messageOfSystem = printDebugExceptionInfo(map,value,e,system_forward,resource,forward);
		}catch(Exception ex){
			//logger.error(ex.getMessage(),ex);
			
		}
		
	}

	/**
	 * �������ݣ��쳣��Ϣ����
	 * 
	 * @param BeanValue
	 *            value �����ֵ����
	 * @param e
	 *            �쳣��
	 * @param key
	 *            �쳣����
	 * @param forwardCode
	 *            ����ҳ����
	 * 
	 */
	protected void processException(RequestMap map,BeanValue value, BaseException e, String system_forward) {
		 
		processException(map,value, e, system_forward, "Resources", ProjectConstants.SYSTEM_RESULT);
	}

	/**
	 * �������ݣ��쳣����
	 * 
	 * @param map
	 *            TODO
	 * @param value
	 *            ����ֵ����
	 * @param e
	 *            �쳣��
	 * @param className
	 *            ������
	 * @param eCode
	 *            �쳣��
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
			//֧��99 ���ش���: ֱ�ӻ�ȡ������Ϣ��ʾ!
			
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
				// ����ϵͳ�˳���־(�д˱�־ҳ�����˳���ʱ�򽫲�������ʾ�Ի���)
				value.addRequestMap("logoutMark", "1");
				target = "_parent";
				className = "system.index";
				break;
			case FomsErrorCode.KICK_OUT:
				// ����ϵͳ�˳���־(�д˱�־ҳ�����˳���ʱ�򽫲�������ʾ�Ի���)
				value.addRequestMap("logoutMark", "1");
				target = "_parent";
				className = "system.index";
				break;
			case FomsErrorCode.NETWORK_ERR:
				// ����ϵͳ�˳���־(�д˱�־ҳ�����˳���ʱ�򽫲�������ʾ�Ի���)
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
			
			// �û���¼�����г���δ֪����
			
			if(null != eCode && eCode.equals(ProjectCode.USER_LOGIN_ERROR)) {
				// ����ϵͳ�˳���־(�д˱�־ҳ�����˳���ʱ�򽫲�������ʾ�Ի���)
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
			// �õ����е�����
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

		// �쳣����(���ص�ֵ����,�쳣,�Զ����쳣��ʾ��Ϣ,����·������)
		processException(map,value, ex, className);
		
		return ex;
	}
	
	/**
	 * ajax�������ݴ���
	 * @param object ���صĶ���
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
