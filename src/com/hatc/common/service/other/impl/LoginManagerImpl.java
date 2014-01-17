/**
 * <b>system：</b> 协同办公平台<br/> 
 * <b>description：</b> 用户登录业务功能定义接口实现<br/> 
 * <b>author：</b> 王洋<br/> 
 * <b>copyright：</b> 北京华安天诚科技有限公司<br/> 
 * <b>version：</b> VER1.00 2010-04-06<br/>
 *                 VER1.01 2010-09-27 黄承 增加获取系统日期<br/>
 */

package com.hatc.common.service.other.impl;


import java.security.MessageDigest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.hatc.base.common.BaseException;
import com.hatc.base.common.BeanValue;
import com.hatc.base.common.RequestMap;
import com.hatc.base.contants.ExceptionCode;
import com.hatc.base.hibernate.util.Condition;
import com.hatc.base.hibernate.util.Operator;
import com.hatc.base.utils.Security;
import com.hatc.common.businessdata.RoleFunction;
import com.hatc.common.businessdata.User;
import com.hatc.common.businessdata.UserRole;
import com.hatc.common.contants.ProjectCode;
import com.hatc.common.contants.ProjectConstants;
import com.hatc.common.exception.LoginException;
import com.hatc.common.hibernate.pojo.TbFunction;
import com.hatc.common.hibernate.pojo.TbUser;
import com.hatc.common.manager.SysDataLoadManager;
import com.hatc.common.service.impl.ProjectManagerImpl;
import com.hatc.common.service.model.Menus;
import com.hatc.common.service.other.LoginManager;
import com.hatc.common.servicestub.ReqIdentity;
import com.hatc.common.web.config.ProjectConfig;
import com.hatc.common.web.config.UserResourceConfig;
import com.hatc.common.web.servlet.WebRunConfig;


public class LoginManagerImpl extends ProjectManagerImpl implements LoginManager {
	
	/*
	 * @see com.hatc.foms.service.LoginManager#index(com.hatc.base.common.RequestMap)
	 */
	public BeanValue index(RequestMap map) throws Exception
	{
		
		BeanValue value = new BeanValue();
		// 服务子系统版本
		String systemSecretLevel = ProjectConfig.getProjectConfig("system_secret_level");
		value.addSessionMap("systemSecretLevel", systemSecretLevel);
		
		// 登录名
		String loginId = map.getString("loginId");
		// 密码错误次数
		String errorPwdNumber = map.getString("errorPwdNumber");
		// 密码错误次数超出限制信息KEY
		String errorInfoKey = map.getString("errorInfoKey");
		// 获取系统密码验证最大次数
		String pwdErrNum = ProjectConfig.getProjectConfig("pwd_error_number");
		
		value.addRequestMap("pwd_error_number", pwdErrNum);// 系统密码验证最大次数
		value.addRequestMap("loginId", loginId);// 登录名
		value.addRequestMap("errorPwdNumber", errorPwdNumber);// 密码错误次数
		value.addRequestMap("errorInfoKey", errorInfoKey);// 密码错误次数超出限制信息KEY
		return value;
	}

	/**
	 * @see com.hatc.common.service.other.LoginManager#csLogin(com.hatc.base.common.RequestMap)
	 */
	public BeanValue csLogin(RequestMap rMap) throws Exception {
		rMap.addParameter("loginType", "CS");
		BeanValue value = webLogin(rMap);
		return value;
	}
	
	/**
	 * @see com.hatc.common.service.other.LoginManager#wsLogin(com.hatc.base.common.RequestMap)
	 * 外部系统登录
	 * 罗凤梅 
	 * 2012-03-15
	 */
	public BeanValue wsLogin(RequestMap rMap) throws Exception {
		rMap.addParameter("loginType", "WS");
		BeanValue value = webLogin(rMap);
		return value;
	}
	/**
	 * @see com.hatc.common.service.other.LoginManager#webLogin(com.hatc.base.common.RequestMap)
	 */
	@SuppressWarnings("unchecked")
	public BeanValue webLogin(RequestMap rMap) throws Exception 
	{
		  
		BeanValue value = new BeanValue();
		// 浏览器类型
		   
		//log.debug("User-Agent: " + rMap.getString(RequestMap.USER_AGENT));
		
		String user_agent = rMap.getString(RequestMap.USER_AGENT);
		if(user_agent==null)
			user_agent=""; 
		// 浏览器类型 session
		value.addSessionMap(ProjectConstants.SESSION_WINDOW_VER, user_agent);
		// 判断是否是IE浏览器
		boolean bool_agent = (user_agent.indexOf("MSIE") != -1);
		// bool_agent = true;
		  
		
		// 登录名
		String loginId = rMap.getString("loginId");
		// 登录密码
		String password = rMap.getString("password");
		ReqIdentity identity = new ReqIdentity();
		value.addParamMap("password", password);
		String loginType = rMap.getString("loginType");
		identity.setFunctionId("BA00001");
		// 服务子系统版本
		String version = ProjectConfig.getProjectConfig("SubVersion");
		identity.setSrvVersion(version);
		// WEB系统版本
		String webVersion = ProjectConfig.getProjectConfig("WebVersion");
		value.addRequestMap("webVersion", webVersion);
		// WEB系统发布日期
		String webReleaseDate = ProjectConfig.getProjectConfig("WebReleaseDate");
		value.addRequestMap("webReleaseDate", webReleaseDate);
		
		// 设置访问IP
		identity.setIpAddress(rMap.getString(RequestMap.REQUEST_IP));
		
		/**
		if (!bool_agent) {
			
			// 判断同一个IP下是否有用FireFox登录的用户
			Condition con = new Condition(TbUser.class);
			con.addParameterAnd("loginId", Operator.SQL_EQ, new String[] { loginId });
			List userList = dao.getObjects(con);
			// 根所登录ID查找用户信息
			if (userList == null || userList.size() != 1) {
				throw new BaseException(ProjectCode.SERVICE_INVALID_USER);
			} else {
				// 判断同IP下用FIREFOX登录的用户是否是前一未正常退出的用户(是则放行,不是则不能登录)
				TbUser tbUser = (TbUser) userList.get(0);
				Condition conCode = new Condition(TbOnline.class);
				conCode.addParameterAnd("loginLocation", Operator.SQL_EQ, new String[] { identity.getIpAddress() });
				//TODO ningliyu 2011-12-08
				////////////////////////////////////////////////////////////////////////
				conCode.addParameterAnd("loginMode", Operator.SQL_EQ, new String[] { ProjectConstants.WEB_LOGIN_TYPE
						+ "I" });
				List onLineList = dao.getObjects(conCode);
				if(onLineList==null)
				{
				conCode = new Condition(TbOnline.class);
				conCode.addParameterAnd("loginLocation", Operator.SQL_EQ, new String[] { identity.getIpAddress() });
				conCode.addParameterAnd("loginMode", Operator.SQL_EQ, new String[] { ProjectConstants.WEB_LOGIN_TYPE
						+ "F" });
				onLineList = dao.getObjects(conCode);
				}
				
				//ningliyu 2011-11-28
				TbOnline online = null;
 
				if (onLineList != null && onLineList.size() > 0) {
					// 决断当前用户USERID是用FIREFOX登录并未正常退出
					for (Object object : onLineList) {
						online = (TbOnline) object;
						//System.out.println(" " +online.getId().getUserId() );
						//System.out.println("tbUser.getUserId() " +tbUser.getUserId() );
						
						if (!online.getId().getUserId().equals(tbUser.getUserId())) {
							BaseException ex = new BaseException();
							ex.setExceptionCode(ProjectCode.NO_IE_LOGIN_ERROR);
							throw ex;
						}
					}
				}
			}
		}
		**/
		
		// 用户登录处理过程
		userLogin(value, loginId, identity);
		        
		//发送事件,触发定时服务获取代码数据
		//SysDataLoadCommander.getInstance().sendCommand();  
		
		// 
		SysDataLoadManager manager = (SysDataLoadManager) WebRunConfig.webac.getBean("sysDataLoadManager");
		
		manager.process();  
		
		/**
		// 初始化字典表
		ProjectItemCode.init(identity);
		
		// 初始系统参数表
		ProjectParameters.init(dao);
		**/  
		
		// 控件符
		String ctrl = "homepage";
		// 修改密码原因
		String reason = "";
		
		//判断用户密码是否需要强制修改(pwdFirst为第一次登录,pwdOutOfDate为密码到期)
		if(identity.getVariant() != null){
			if(identity.getVariant().equals("pwdOutOfDate")){
				reason = "pwdOutOfDate";
				ctrl = "loginPwd";
			} else if(identity.getVariant().equals("pwdFirst")){
				reason = "pwdFirst";
				ctrl = "loginPwd";
			}
		}
		
		// 控制符
		value.addRequestMap("ctrl", ctrl);
		// 修改密码原因
		value.addRequestMap("reason", reason);
		return value;
	}

	/**
	 * 处理内容：用户登录处理过程
	 * 
	 * @param value
	 *            传入值对象
	 * @param loginId
	 *            登录ID
	 * @param identity
	 *            用户标识
	 * @throws FomsException
	 *             后台异常
	 * @throws BaseException
	 *             WEB异常
	 * 
	 */
	@SuppressWarnings("unchecked")
	private void userLogin(BeanValue value, String loginId, ReqIdentity identity) throws Exception {
        
        // 获取系统当前日期
        Date aDate = new Date();
        // 格式化日期 "2010年03月25日 星期四"
        DateFormat aDateFormat = new SimpleDateFormat("yyyy年MM月dd日 E", Locale.CHINA);
        String hpDate = aDateFormat.format(aDate).toString();
        value.addParamMap("hpDate", hpDate);
     
        
		User user = secuServ.getUserInfo(loginId);
		identity.setFunctionId("S999999");
		
		if(user == null){
			throw new LoginException(LoginException.INVALID_USER);
		}
		String password  = (String)value.getParamMap("password");
		String md5_password = new String(Security.MD5DecodeString(password));
		if(!user.getPassword().equals(md5_password)){
			throw new LoginException(LoginException.BAD_PWD);
		}
		
		identity.setUserId(user.getUserId());
		// 将请求身份标识存入在session中
		value.addSessionMap(ProjectConstants.SESSION_REQIDENTITY, identity);
		// 登陆用户信息
		value.addSessionMap(ProjectConstants.SESSION_USER, user);
		// 将用户角色信息保存在Session中
		if(identity.getFunctionId().equals("S999999")){
			//return ;
		}
		// 用户角色列表
		ArrayList<UserRole> roleList = secuServ.getUserRoles(user);
		
		
		if (roleList == null  ) {
			BaseException bEx = new BaseException();
	        //该用户没有角色授权! = msg.common.user.role.norole=\u8be5\u7528\u6237\u6ca1\u6709\u89d2\u8272\u6388\u6743!
			//chenzj 2012.3.7
			bEx.setExceptionCode(ExceptionCode.USER_ROLE_ERROR);  
  
			throw bEx;   			

		}
		else
		{
			if(roleList.size()==0)
			{
				BaseException bEx = new BaseException();
			 //该用户没有角色授权! = msg.common.user.role.norole
			//chenzj 2012.3.7 
				bEx.setExceptionCode(ExceptionCode.USER_ROLE_ERROR); 
				/*
				 * update by jicheng.
				 * 若该用户没有角色时,则退出该用户.在web端进行退出操作. */
				BeanValue aBeanValue = new BeanValue(); 
				// 获取登录用户会话标识
				identity.setFunctionId("BA00009");
				// 从 SESSION 得到用户对象 ID
				String userId = identity.getUserId();     

				/* 业务逻辑 - 退出系统 */
				secuServ.logout(userId);
				throw bEx;   	
			}
		}

		int roleNameL = 0;
		if (roleList != null && roleList.size() > 0) {
			// 用户默认角色(取列表第一个)
			UserRole role = null;
			int i_index = 0;
			for (UserRole role2 : roleList) {
				if (role2.isIsDefaultRole()) {
					role = role2;
				}
				if (role2.getRoleName().getBytes().length > roleNameL) {
					roleNameL = role2.getRoleName().getBytes().length;
				}
				i_index++;
			}
			value.addRequestMap("roleWidth", (roleNameL + 2) * 7 < 120 ? 140 : (roleNameL + 2) * 7);
			if (i_index > 30) {
				value.addRequestMap("roleHeight", "600");
			}

			ArrayList<UserRole> rolesOrFunctions = new ArrayList<UserRole>();
			// 用户角色列表
//			uStub.getRolesOrFunctions(identity, identity.getUserId(), identity.getUserRole(), rolesOrFunctions);
//			if (rolesOrFunctions != null && rolesOrFunctions.size() > 0) {
//				role = rolesOrFunctions.get(0);
//			}
			// 用户首选功能
			String firstWindow = "";
			// 用户默认首页
			TbUser tbUser = (TbUser) dao.getObject(TbUser.class, identity.getUserId());
			if (tbUser != null && tbUser.getWebHome() != null && tbUser.getWebHome().trim().length() > 0) {
				TbFunction function = (TbFunction) dao.getObject(TbFunction.class, tbUser.getWebHome());
				if (function != null) {
					if (function.getLink() != null && !function.getLink().trim().equals("")
							&& !function.getLink().trim().equals("#")) {
						firstWindow = function.getLink() + "&firstPageFunctionId=" + function.getFunctionId();
					} else {
						firstWindow = "/loginAction.do?method=menuIcoHref&menuFunctionId=" + function.getOrderId() + "&firstPageFunctionId=" + function.getFunctionId();
					}
					value.addRequestMap("firstPageFunctionId", function.getFunctionId());
				}
			}
			// 角色默认首页
			if (firstWindow.trim().length() == 0 && role != null
					&& (role.getWebPreferredFunctionCode() != null && !role.getWebPreferredFunctionCode().equals(""))) {
				// 根据链接获取function
				Condition condition = new Condition(TbFunction.class);
				condition.addParameterAnd("link", Operator.SQL_EQ, new String[]{role.getWebPreferredFunctionCode()});
				TbFunction functionForFirstPage = (TbFunction)(dao.getObjects(condition).get(0));
				//firstWindow = role.getWebPreferredFunctionCode() + "&firstPageFunctionId=" + functionForFirstPage.getFunctionId();
				///2012-4-11 wrb修改，增加menuFunctionId参数以便首次登录时功能按钮显示不全的问题，修正2694的bug
				firstWindow = role.getWebPreferredFunctionCode() + "&menuFunctionId="+functionForFirstPage.getOrderId()+"&firstPageFunctionId=" + functionForFirstPage.getFunctionId();
				value.addRequestMap("firstPageFunctionId", functionForFirstPage.getFunctionId());
			}
			
			// 系统默认首页
			if (firstWindow.trim().length() == 0) {
				firstWindow = ProjectConfig.getProjectConfig("firstWindow").replace(",", "&");
				value.addRequestMap("firstPageFunctionId", firstWindow.substring(firstWindow.lastIndexOf("=") + 1));
			}
			
			
//			罗凤梅，设置首选页为默认项传递的URL进行设置。
			if(identity.getLoginType()!=null && identity.getLoginType().trim().equals("WS")){
				if(identity.getLoginUrl()!=null&& !identity.getLoginUrl().trim().equals("")){
					firstWindow=identity.getLoginUrl();
				}
				
				
			}
			value.addRequestMap("firstWindow", firstWindow);
			value.addSessionMap(ProjectConstants.SESSION_USER_ROLE, role);
			// 设置默认登陆角色至 identity 中，用于后面获取用户角色或根据角色设置首页等。
			identity.setUserRole(role.getRoleId());
			
			List roleFunctionTemp = secuServ.getRoleFunctions(role) ;
			
			StringBuffer roleFunction = new StringBuffer();
			List tmprole = new ArrayList();
			RoleFunction temp = null;
			// 找出所有BA开头的功能ID
			for (Object obj : roleFunctionTemp) {
				 temp = (RoleFunction) obj;
				if (temp.getFunctionId().substring(0, 2).equals("BA")) {
					roleFunction.append(temp.getFunctionId());
					tmprole.add(temp.getFunctionId());
				}
			}
			value.addSessionMap(ProjectConstants.SESSION_USER_ROLE_FUNCTION, roleFunction.toString());
			//modify by lilj add web功能列表list
			value.addSessionMap(ProjectConstants.SESSION_USER_ROLE_FUNCTIONLIST, tmprole);
			// 菜单显示方式
			String style = ProjectConfig.getProjectConfig("menuStyle");
			value.addSessionMap("menuStyle", style);
			// 用户角色权限列表(菜单)
			// 对菜单进行过滤
			List<Menus> functionList = function(roleFunctionTemp);
			// 保存排序后的角色功能
			value.addSessionMap("functions", functionList);
			// 保存角色List
			value.addSessionMap(ProjectConstants.SESSION_USER_ROLE_LIST, roleList);
			
			secuServ.login(user,identity);
			// 本场地点
			//List localList = dao.getObjects(TbLocal.class);
			// 地点参数表中只可能有一条可用记录
			//TbLocal local = localList != null && localList.size() > 0 ? (TbLocal) localList.get(0) : null;
		}
	}

	/**
	 * @see com.hatc.common.service.other.LoginManager#restLogin(com.hatc.base.common.RequestMap)
	 * 重新登陆
	 * 2011-12-23 处理空值的情况.
	 */
	public BeanValue restLogin(RequestMap map) throws Exception {
		// 获取用户登录会话标识
		ReqIdentity identity = getReqIdentity(map);
		if(identity!=null)
		{
		
			identity.setFunctionId("BA00005");
			// 解除用户锁定的对象
			releaseObjectLock(identity);

			// 插入操作日志
			/* 从Session 得到用户对象 */
			User user = (User) (map.getObject(ProjectConstants.SESSION_USER));
			
			//TODO ningliyu 2012-1-7
			//解决重起后,界面出现的问题: 访问服务出现未知错误.
			if(user==null)
			{   
				BaseException bEx = new BaseException(); 
				bEx.setExceptionCode("99999.16");  
				bEx.setMessage(UserResourceConfig.getInstance().getHtmlToDataValue(ProjectCode.SERVICE_NOT_LOGON));
				throw bEx;
			}  			
			
			secuServ.logout(user.getLoginId());
			
		}
		
		// 这里注释掉是因为重新登录的时候调用后台服务，后台服务已经记录了日志。
//		try {
//			LogStub lStub = new LogStub();
//			lStub.writeLog(user.getUserId(), "BA00005", "1", "", identity.getSessionId());
//		} catch (Exception e) {
//			log.error(e);
//		}

		BeanValue value = new BeanValue();
		value.removeSession(ProjectConstants.SESSION_REQIDENTITY);
		value.removeSession(ProjectConstants.SESSION_USER);
		value.removeSession(ProjectConstants.SESSION_USER_ROLE);
		value.removeSession(ProjectConstants.SESSION_USER_ROLE_LIST);
		value.removeSession(ProjectConstants.SESSION_WINDOW_VER);
 

		value.setForward("index");
		return value;
	}

	/*
	 * @see com.hatc.ttims.service.other.LoginManager#menuIcoHref(com.hatc.base.common.RequestMap)
	 */
	@SuppressWarnings("unchecked")
	public BeanValue menuIcoHref(RequestMap map) throws Exception {
 
		 
		// 获取用户登录会话标识
		ReqIdentity identity = getReqIdentity(map);
		//identity.setFunctionId("BA00005"); 
		// 解除用户锁定的对象
		releaseObjectLock(identity);
		BeanValue value = new BeanValue();
		// 菜单功能ID
		String id = map.getString("menuFunctionId");
		value.addRequestMap("DefaultFirstPageId", id);
		// 用户首页功能ID
		String firstPageFunctionId = map.getString("firstPageFunctionId");
		value.addRequestMap("firstPageFunctionId", firstPageFunctionId);
		// 顶层菜单ID
		String topMenuId = id.substring(0, 3) + "0000";
		// 子菜单ID
		String childMenuId = id.substring(0, 5) + "00";
		// 当前角色功能权限集
		List functionList = (List) map.getObject("functions");
		int functionListSize = 0;
		if(functionList!=null)
			functionListSize=functionList.size();
		
		Menus temp = null;
		// 标题
		List<Menus> menusList = new ArrayList<Menus>();
		
		if(functionListSize==0)
		{
			log.error("menuIcoHref: functionList is null!...");
		}
		else
		{ 
	
		for (Object fun : functionList)
		{
			Menus menu = (Menus) fun;
			if (menu.getFunction().getOrderId().equals(topMenuId))
			{// 第一层菜单
				menusList.add(menu);
				if (!id.equals(topMenuId)) 
				{
					for (Menus temp_1 : menu.getList()) 
					{
						if (temp_1.getFunction().getOrderId().equals(childMenuId)) 
						{// 第二层菜单
							menusList.add(temp_1);
							// 递归
							temp = recursionMenus(temp_1, menusList);
							value.addRequestMap("menu", temp);
							Menus tempMenu = new Menus();
							RoleFunction rf = new RoleFunction();
							rf.setFunctionId(menu.getFunction().getFunctionId());
							rf.setFunctionName("返回上一级");
							tempMenu.setFunction(rf);
							value.addRequestMap("backMenu", tempMenu);
							break;
						}
					}
				} else {
					// 递归
					temp = recursionMenus(menu, menusList);
					value.addRequestMap("menu", temp);
					break;
				}
			}
		}
		}

		value.setForward("menuIco");

		if (temp != null && temp.getList() != null && temp.getList().size() == 1) {
			Menus temp_1 = temp.getList().get(0);
			if (temp_1.getList() == null) {
				value.addParamMap("size", "1");
				value.setForward(temp_1.getFunction().getFunctionDescription());
			} else {
				if (temp_1.getList().size() == 1) {
					value.addParamMap("size", "1");
					value.setForward(temp_1.getList().get(0).getFunction().getFunctionDescription());
				} else {
					temp = temp_1;
					value.addRequestMap("menu", temp);
				}
			}
		}

		// 图标显示方式
		String icoStyle = ProjectConfig.getProjectConfig("icoStyle");
		value.addRequestMap("icoStyle", icoStyle);
		// 提示层显示参数
		String shortcutStyle = ProjectConfig.getProjectConfig("shortcutStyle");
		value.addRequestMap("shortcutStyle", shortcutStyle);

		if (shortcutStyle.equals("1")) {  
			//MessageQueryBPO bpo = new MessageQueryBPO();
			//bpo.getHomePageMessage(identity,dao, map, value);
			
			setShortcutData(map, value);
		}

		// 菜单宽度
		int icoTableW = shortcutStyle.equals("0") ? 823 : 610;
		value.addRequestMap("icoTableW", icoTableW);
		// 提示框宽度
		int shortcutW = shortcutStyle.equals("0") ? 0 : 200;
		value.addRequestMap("shortcutW", shortcutW);

		// 菜单显示最大个数
		int icoSize = shortcutStyle.equals("0") ? 4 : 2;
		// 菜单TD高度
		int icoTableH = 480;
		String menuRowNum = ProjectConfig.getProjectConfig("menuRowNum");
		String menuRepeat = ProjectConfig.getProjectConfig("menuRepeat");
		value.addRequestMap("menuRowNum", menuRowNum); // 最大折行数 Arielly 新增100505
		value.addRequestMap("menuRepeat", menuRepeat); // 菜单显示方式 repeat_x 横向，repeat_y 纵向
		int rowSize = Integer.parseInt(menuRowNum == null ? "0" : menuRowNum);
		// 行不能大于10
		rowSize = rowSize > 10 ? 10 : rowSize;
		if (temp != null && temp.getList() != null && temp.getList().size() > 0) {
			int size = temp.getList().size();
			value.addRequestMap("listSize", size);
			int colSize = size / rowSize + (size % rowSize > 0 ? 1 : 0);
			// 列不能大于4
			if (colSize > icoSize) {
				rowSize = size / colSize + (size % colSize > 0 ? 1 : 0);
				colSize = icoSize;
			}
			value.addRequestMap("colSize", colSize);
			int colWidth = 200;
			value.addRequestMap("colWidth", colWidth);
			value.addRequestMap("tableWidth", colWidth * colSize);

			int iheight = icoTableH / size;
			value.addRequestMap("iheight", iheight);
		}
		value.addRequestMap("menusList", menusList);
		value.addRequestMap("menusListLength", menusList.size());

		return value;
	}

	// 递归（菜单）
	private Menus recursionMenus(Menus menu, List<Menus> titleList) {
		Menus ret = new Menus();
		if (menu.getList() != null && menu.getList().size() == 1) {
			Menus temp = menu.getList().get(0);
			if (temp.getList() != null && temp.getList().size() == 1) {
				ret = recursionMenus(temp, titleList);
			} else {
				ret = menu;
			}
		} else {
			ret = menu;
		}
		return ret;
	}

	@SuppressWarnings("unchecked")
	private void setShortcutData(RequestMap reqMap, BeanValue value) throws Exception {
		
		 
		
		// 获取用户登录会话标识
		ReqIdentity identity = getReqIdentity(reqMap);
		//identity.setFunctionId(null == reqMap.getString("firstPageFunctionId") ? "BM80900" : reqMap.getString("firstPageFunctionId"));
		
		//String functionID = "BA73000";
		//identity.setFunctionId(functionID);
		
		
	}

	/*
	 * @see com.hatc.ttims.service.other.LoginManager#firstPage(com.hatc.base.common.RequestMap)
	 */
	public BeanValue firstPage(RequestMap map) throws Exception {
		BeanValue value = new BeanValue();
		value.setForward("info");
		return value;
	}

}
