package com.hatc.common.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.hatc.base.common.BaseException;
import com.hatc.base.common.BeanValue;
import com.hatc.base.common.RequestMap;
import com.hatc.base.service.impl.BaseManager;
import com.hatc.base.web.action.ExecuteProcess;
import com.hatc.common.businessdata.RoleFunction;
import com.hatc.common.businessdata.Site;
import com.hatc.common.businessdata.UserRole;
import com.hatc.common.contants.ProjectCode;
import com.hatc.common.contants.ProjectConstants;
import com.hatc.common.hibernate.pojo.TbLocal;
import com.hatc.common.hibernate.pojo.TbOnline;
import com.hatc.common.hibernate.pojo.TbOnlineId;
import com.hatc.common.service.LogService;
import com.hatc.common.service.ProjectManager;
import com.hatc.common.service.model.Menus;
import com.hatc.common.service.security.SecurityService;
import com.hatc.common.servicestub.ReqIdentity;
import com.hatc.common.servicestub.util.FilterCmd;
import com.hatc.common.servicestub.util.SqlCondition;
import com.hatc.common.web.config.UserResourceConfig;

/**
* 
* <b>system：</b>      协同办公平台<br/>
* <b>description：</b> 通用模块Manager接口实现<br/>
* <b>author：</b>      王洋<br/>
* <b>modify：</b>      刘明熹<br/>
* <b>copyright：</b>	　 北京华安天诚科技有限公司<br/>
* <b>version：</b>     VER1.00 2010-04-06<br/>
*
**/
public class ProjectManagerImpl extends BaseManager implements ProjectManager {
	
	
	protected SecurityService secuServ;
	
	public void setSecuServ(SecurityService secuServ) {
		this.secuServ = secuServ;
	}
	
	@Autowired
	protected LogService logServ;
	
	public void setLogServ(LogService logServ) {
		this.logServ = logServ;
	}
	protected final Log logger = LogFactory.getLog(getClass());
	/**
	 * 生成报表ReportDefine名称
	 * 
	 * @param name
	 *            报表名称
	 * @return
	 */
	protected String buildReportDefineName(String name) {
		return name + new Date().getTime();
	}

	/**
	 * 处理内容：获取用户标识
	 * 处理异常: session 失效
	 * 2012-1-1 ningliyu 
	 * @param map
	 *            传入值对象
	 * @return ReqIdentity 用户标识
	 * 
	 * 
	 */
	protected ReqIdentity getReqIdentity(RequestMap map) throws Exception
	{
		
		// 获取调用方式(由于客户端需要调用Web端中的功能，而当客户端调用Web端的时候由于Web端可能没登陆identity是为空的，所以需要在这里进行一次数据的封装)
		// 如果tMode为大写的C则说明是客户端调用的Web端
		String tMode = map.getString("tMode");
		  
		ReqIdentity identity = null;
		if(null != tMode && tMode.equals("C")){
			identity = this.communicationInit(map);
		} else {
			identity = (ReqIdentity) map.getObject(ProjectConstants.SESSION_REQIDENTITY);
//log.debug("into getReqIdentity：" + identity.getFunctionId() + ",sessionId: " + identity.getSessionId() + ",user:" + identity.getUserId() );

		}  
		
		// 这个地方主要为客户端调用Web端fIdentity在过滤器中传入	 
		if(null == identity) 
			identity=(ReqIdentity)map.getObject("fIdentity");  
		
		//TODO ningliyu 2012-1-3
		//由框架来处理异常：会话对象getReqIdentity为空时，抛出异常。不需要每个业务接口处理
		
		if(null == identity) 
		{
			//log.error("无法创建identity 对象...");
			logger.error("user.session.isNull, set 99999.16");
			BaseException bEx = new BaseException(); 
			bEx.setExceptionCode("99999.16");  
			bEx.setMessage(UserResourceConfig.getInstance().getHtmlToDataValue(ProjectCode.SERVICE_NOT_LOGON));
			throw bEx;
		}  

		/**
		if(identity.getLogonType().compareTo("C")!=0)
		{
			  
			if(map.getObject("request")==null)
			{  
				log.error("web 调用：无法获取会话中的用户信息：SESSION_USER");
				BaseException bEx = new BaseException(); 
				bEx.setExceptionCode("99999.16");  
				bEx.setMessage(UserResourceConfig.getInstance().getHtmlToDataValue(ProjectCode.SERVICE_NOT_LOGON));
				throw bEx;
			}   
			
			if(map.getObject(ProjectConstants.SESSION_USER)==null)
			{  
				log.error("web 调用：无法获取会话中的用户信息：SESSION_USER");
				BaseException bEx = new BaseException(); 
				bEx.setExceptionCode("99999.16");  
				bEx.setMessage(UserResourceConfig.getInstance().getHtmlToDataValue(ProjectCode.SERVICE_NOT_LOGON));
				throw bEx;
			}  
	
			 
			if(map.getObject(ProjectConstants.SESSION_USER_ROLE)==null)
			{
				BaseException bEx = new BaseException(); 
				bEx.setExceptionCode(ProjectCode.SERVICE_NOT_LOGON);  
				bEx.setMessage(UserResourceConfig.getInstance().getHtmlToDataValue(ProjectCode.SERVICE_NOT_LOGON));
			//bEx.setMessage(UserResourceConfig.getInstance().getValue(ProjectCode.SERVICE_NOT_LOGON));
				throw bEx;
			}
			 
			 
			if(map.getObject(ProjectConstants.SESSION_REQIDENTITY)==null)
			{
				BaseException bEx = new BaseException(); 
				bEx.setExceptionCode(ProjectCode.SERVICE_NOT_LOGON);  
				bEx.setMessage(UserResourceConfig.getInstance().getHtmlToDataValue(ProjectCode.SERVICE_NOT_LOGON));
				//bEx.setMessage(UserResourceConfig.getInstance().getValue(ProjectCode.SERVICE_NOT_LOGON));
				throw bEx;
			}
		}
		
	    **/
		 
		
		return identity;
		
	}

	/**
	 * 处理内容：处理左侧主菜单
	 * 
	 * @param list
	 *            角色权限集合
	 * @return List<Menus> 菜单集合
	 * 
	 */
	protected List<Menus> function(List<RoleFunction> list) {
		// 找出属于web的主菜单项
		ArrayList<RoleFunction> roleList = new ArrayList<RoleFunction>();
		String id = "";
		for (RoleFunction function : list) {
			id = function.getFunctionId();
			if (id.substring(0, 2).equals("BM")) {
				roleList.add(function);
			}
		}

		// 找出第一层菜单项
		List<Menus> menusList = new ArrayList<Menus>();
		List<RoleFunction> list1 = new ArrayList<RoleFunction>();
		list1.addAll(roleList);
		for (RoleFunction function : roleList) {
			id = function.getOrderId();
			if (id.substring(3, 7).equals("0000")) {
				Menus menu = new Menus();
				menu.setFunction(function);
				menu.setMark(id.substring(2, 3));
				menu.setSid(id.substring(2, 7));
				menusList.add(menu);
				list1.remove(function);
			}
		}
		// 找出第二层菜单项
		List<RoleFunction> list2 = new ArrayList<RoleFunction>();
		list2.addAll(list1);
		// BM80100 BM80200
		List<Menus> listTwo = null;
		Menus mu = null;
		for (Menus menu : menusList) {
			listTwo = new ArrayList<Menus>();
			for (RoleFunction function : list1) {
				id = function.getOrderId();
				if (id.substring(2, 3).equals(menu.getMark()) && !id.subSequence(3, 5).equals("00")
						&& id.subSequence(5, 7).equals("00")) {
					mu = new Menus();
					mu.setFunction(function);
					mu.setMark(id.substring(3, 5));
					mu.setSid(id.substring(2, 7));
					listTwo.add(mu);
					list2.remove(function);
				}
			}
			menu.setList(listTwo);
		}
		// 找出第三层菜单项
		List<Menus> listThere = null;
		for (Menus menu : menusList) {
			if (menu.getList() != null && menu.getList().size() > 0) {
				for (Menus menuThere : menu.getList()) {
				    listThere = new ArrayList<Menus>();
					for (RoleFunction function : list2) {
						id = function.getOrderId();
						if (id.substring(2, 3).equals(menu.getMark()) && id.substring(3, 5).equals(menuThere.getMark())
								&& !id.subSequence(5, 7).equals("00")) {
							mu = new Menus();
							mu.setFunction(function);
							mu.setMark(id.substring(5, 7));
							mu.setSid(id.substring(2, 7));
							listThere.add(mu);
						}
					}
					menuThere.setList(listThere.size() > 0 ? listThere : null);
				}
			}
		}
		return menusList;
	}

	/**
	 * 处理内容：释放当前登录会话的所有对象锁
	 * 
	 * @param identity
	 *            请求的身份标识
	 * @param map
	 *            ID对就的中文名Map
	 * 
	 */
	protected void releaseObjectLock(ReqIdentity identity) throws Exception {

		try {
			secuServ.releaseObjectLockBySession(identity);
		} catch (Exception e) {
			log.error("根据用户会话解锁失败!",e);
			throw e;
			
		}
	}

	/**
	 * 权限检查
	 * @param requestMap 请求值集合
	 * @param functionId 权限ID
	 * @return boolean
	 * @throws Exception
	 */
	protected boolean checkUserAuth(RequestMap requestMap, String functionId) throws Exception {
		String str_auth = requestMap.getString(ProjectConstants.SESSION_USER_ROLE_FUNCTION);
		return str_auth.indexOf(functionId) > -1;
	}

	/**
	 * 记录日志
	 * @param aRequestMap	请求值集合
	 * @param functionId	权限ID
	 * @param logText		日志简述
	 * @throws Exception
	 */
	protected void writeLog(RequestMap aRequestMap, String functionId, String logText) throws Exception {

		logServ.log(logText);
	}
	
	/**
	 * 客户端调用Web端时的信息初始化
	 * @param identity 用户信息
	 * @param map 存放请求信息
	 */
	@SuppressWarnings("unchecked")
	private ReqIdentity communicationInit(RequestMap map) throws Exception
	{
		return null;
	}
	

	/**
	 * 通用文件下载
	 * 
	 * @param RequestMap
	 *            输入参数 Map
	 * @return BeanValue 业务处理完成后的结果集
	 */
	public BeanValue downFiles(RequestMap map) throws Exception
	{
		//TODO ningliyu 2012-1-3
		//修改当会话失败（超时或重启服务器）时，仍然能够下载文件
		
		ReqIdentity aReqIdentity = getReqIdentity(map);
		   
		BeanValue value = new BeanValue();

		String filename = map.getString("DOC_NAME"); // 下载文件名 eg. "椭圆算法"
		String filetype = map.getString("DOC_TYPE"); // 下载文件扩展名 eg. "xls"
		
		String filesname = map.getString("DOC_SNAME"); // 文件保存名
		String filestype = map.getString("DOC_STYPE"); // 文件保存扩展名
		
		String filepath = map.getString("DOC_PATH"); // 下载路径 "f:\\详细设计\\"

		if (filesname == null || filesname.equals("")) { // 默认保存文件名同下载文件名
			filesname = filename;
		}
		
		if (filepath.indexOf("/usr/local/upload") == -1 && filepath.indexOf("/sdb/upload") == -1) {
			filepath = filepath.replaceAll("/", "\\\\\\\\");
		}
		
		if (filetype == null){
			filetype = filestype;
		}
		
		if (filestype == null){
			filestype = filetype;
		}

		String content_type = "";
		if (filetype.equalsIgnoreCase("DOC")) { // Microsoft Word 文档
			content_type = "application/msword";
		} else if (filetype.equalsIgnoreCase("XLS")) { // Excel 电子表格
			content_type = "application/vnd.ms-excel";
		} else if (filetype.equalsIgnoreCase("PPT")) { // PowerPoint 演示文稿
			content_type = "application/vnd.ms-powerpoint";
		} else if (filetype.equalsIgnoreCase("PDF")) { // Acrobat(.pdf) 文件
			content_type = "application/pdf";
		} else if (filetype.equalsIgnoreCase("ZIP")) { // Zip 档案
			content_type = "application/zip";
		} else if (filetype.equalsIgnoreCase(".TAR.GZ")) { // Gzip 档案
			content_type = "application/x-gzip";
		} else if (filetype.equalsIgnoreCase("BMP")) { // X windows 位图图像
			content_type = "image/x-xbitmap";
		} else if (filetype.equalsIgnoreCase("GIF")) { // GIF 图像
			content_type = "image/gif";
		} else if (filetype.equalsIgnoreCase("JPEG")) { // JPEG 图像
			content_type = "image/jpeg";
		} else if (filetype.equalsIgnoreCase("PNG")) { // PNG 图像
			content_type = "image/png";
		} else if (filetype.equalsIgnoreCase("TIFF")) { // TIFF 图像
			content_type = "image/tiff";
		} else if (filetype.equalsIgnoreCase("TXT")) { // TXT 文本
			content_type = "text/plain";
		} else if (filetype.equalsIgnoreCase("XML")) { // XML
			content_type = "text/xml";
		} else { // 未识别
			content_type = "application/octet-stream";
		}

		value.addRequestMap("filename", filename +  "." + filetype);
		value.addRequestMap("filesname", filesname + "." + filestype);
		value.addRequestMap("filetype", content_type);
		value.addRequestMap("filepath", filepath);

		String function_id = map.getString("function_id");

		if (function_id != null && !function_id.equals("")) {
			logServ.sysLog(aReqIdentity.getUserId() + "下载文件：" +filename +  "." + filetype);
		}

		return value;
	}
	/*
	 * 获得最大登录次数
	 * @auth chenzj
	 * @param 登录次数字段名
	 * @return 最大登录次数
	 * **/
	public String getRelogonTimes()throws Exception{
		String sql= "select  t.upper  from TB_PARAMETER_SETUP t where t.para_name ='RELOGON_TIMES' ";
		String[] str = {"upper"};
		List<String[]> param =  dao.getObjects(sql,str);
		String pwdErrNum = param.get(0)[0];
		return pwdErrNum;
	}
	
}
