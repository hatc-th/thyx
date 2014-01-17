/**
 * <b>system��</b> Эͬ�칫ƽ̨<br/> 
 * <b>description��</b> �û���¼ҵ���ܶ���ӿ�ʵ��<br/> 
 * <b>author��</b> ����<br/> 
 * <b>copyright��</b> ����������ϿƼ����޹�˾<br/> 
 * <b>version��</b> VER1.00 2010-04-06<br/>
 *                 VER1.01 2010-09-27 �Ƴ� ���ӻ�ȡϵͳ����<br/>
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
		// ������ϵͳ�汾
		String systemSecretLevel = ProjectConfig.getProjectConfig("system_secret_level");
		value.addSessionMap("systemSecretLevel", systemSecretLevel);
		
		// ��¼��
		String loginId = map.getString("loginId");
		// ����������
		String errorPwdNumber = map.getString("errorPwdNumber");
		// ��������������������ϢKEY
		String errorInfoKey = map.getString("errorInfoKey");
		// ��ȡϵͳ������֤������
		String pwdErrNum = ProjectConfig.getProjectConfig("pwd_error_number");
		
		value.addRequestMap("pwd_error_number", pwdErrNum);// ϵͳ������֤������
		value.addRequestMap("loginId", loginId);// ��¼��
		value.addRequestMap("errorPwdNumber", errorPwdNumber);// ����������
		value.addRequestMap("errorInfoKey", errorInfoKey);// ��������������������ϢKEY
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
	 * �ⲿϵͳ��¼
	 * �޷�÷ 
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
		// ���������
		   
		//log.debug("User-Agent: " + rMap.getString(RequestMap.USER_AGENT));
		
		String user_agent = rMap.getString(RequestMap.USER_AGENT);
		if(user_agent==null)
			user_agent=""; 
		// ��������� session
		value.addSessionMap(ProjectConstants.SESSION_WINDOW_VER, user_agent);
		// �ж��Ƿ���IE�����
		boolean bool_agent = (user_agent.indexOf("MSIE") != -1);
		// bool_agent = true;
		  
		
		// ��¼��
		String loginId = rMap.getString("loginId");
		// ��¼����
		String password = rMap.getString("password");
		ReqIdentity identity = new ReqIdentity();
		value.addParamMap("password", password);
		String loginType = rMap.getString("loginType");
		identity.setFunctionId("BA00001");
		// ������ϵͳ�汾
		String version = ProjectConfig.getProjectConfig("SubVersion");
		identity.setSrvVersion(version);
		// WEBϵͳ�汾
		String webVersion = ProjectConfig.getProjectConfig("WebVersion");
		value.addRequestMap("webVersion", webVersion);
		// WEBϵͳ��������
		String webReleaseDate = ProjectConfig.getProjectConfig("WebReleaseDate");
		value.addRequestMap("webReleaseDate", webReleaseDate);
		
		// ���÷���IP
		identity.setIpAddress(rMap.getString(RequestMap.REQUEST_IP));
		
		/**
		if (!bool_agent) {
			
			// �ж�ͬһ��IP���Ƿ�����FireFox��¼���û�
			Condition con = new Condition(TbUser.class);
			con.addParameterAnd("loginId", Operator.SQL_EQ, new String[] { loginId });
			List userList = dao.getObjects(con);
			// ������¼ID�����û���Ϣ
			if (userList == null || userList.size() != 1) {
				throw new BaseException(ProjectCode.SERVICE_INVALID_USER);
			} else {
				// �ж�ͬIP����FIREFOX��¼���û��Ƿ���ǰһδ�����˳����û�(�������,�������ܵ�¼)
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
					// ���ϵ�ǰ�û�USERID����FIREFOX��¼��δ�����˳�
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
		
		// �û���¼�������
		userLogin(value, loginId, identity);
		        
		//�����¼�,������ʱ�����ȡ��������
		//SysDataLoadCommander.getInstance().sendCommand();  
		
		// 
		SysDataLoadManager manager = (SysDataLoadManager) WebRunConfig.webac.getBean("sysDataLoadManager");
		
		manager.process();  
		
		/**
		// ��ʼ���ֵ��
		ProjectItemCode.init(identity);
		
		// ��ʼϵͳ������
		ProjectParameters.init(dao);
		**/  
		
		// �ؼ���
		String ctrl = "homepage";
		// �޸�����ԭ��
		String reason = "";
		
		//�ж��û������Ƿ���Ҫǿ���޸�(pwdFirstΪ��һ�ε�¼,pwdOutOfDateΪ���뵽��)
		if(identity.getVariant() != null){
			if(identity.getVariant().equals("pwdOutOfDate")){
				reason = "pwdOutOfDate";
				ctrl = "loginPwd";
			} else if(identity.getVariant().equals("pwdFirst")){
				reason = "pwdFirst";
				ctrl = "loginPwd";
			}
		}
		
		// ���Ʒ�
		value.addRequestMap("ctrl", ctrl);
		// �޸�����ԭ��
		value.addRequestMap("reason", reason);
		return value;
	}

	/**
	 * �������ݣ��û���¼�������
	 * 
	 * @param value
	 *            ����ֵ����
	 * @param loginId
	 *            ��¼ID
	 * @param identity
	 *            �û���ʶ
	 * @throws FomsException
	 *             ��̨�쳣
	 * @throws BaseException
	 *             WEB�쳣
	 * 
	 */
	@SuppressWarnings("unchecked")
	private void userLogin(BeanValue value, String loginId, ReqIdentity identity) throws Exception {
        
        // ��ȡϵͳ��ǰ����
        Date aDate = new Date();
        // ��ʽ������ "2010��03��25�� ������"
        DateFormat aDateFormat = new SimpleDateFormat("yyyy��MM��dd�� E", Locale.CHINA);
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
		// ��������ݱ�ʶ������session��
		value.addSessionMap(ProjectConstants.SESSION_REQIDENTITY, identity);
		// ��½�û���Ϣ
		value.addSessionMap(ProjectConstants.SESSION_USER, user);
		// ���û���ɫ��Ϣ������Session��
		if(identity.getFunctionId().equals("S999999")){
			//return ;
		}
		// �û���ɫ�б�
		ArrayList<UserRole> roleList = secuServ.getUserRoles(user);
		
		
		if (roleList == null  ) {
			BaseException bEx = new BaseException();
	        //���û�û�н�ɫ��Ȩ! = msg.common.user.role.norole=\u8be5\u7528\u6237\u6ca1\u6709\u89d2\u8272\u6388\u6743!
			//chenzj 2012.3.7
			bEx.setExceptionCode(ExceptionCode.USER_ROLE_ERROR);  
  
			throw bEx;   			

		}
		else
		{
			if(roleList.size()==0)
			{
				BaseException bEx = new BaseException();
			 //���û�û�н�ɫ��Ȩ! = msg.common.user.role.norole
			//chenzj 2012.3.7 
				bEx.setExceptionCode(ExceptionCode.USER_ROLE_ERROR); 
				/*
				 * update by jicheng.
				 * �����û�û�н�ɫʱ,���˳����û�.��web�˽����˳�����. */
				BeanValue aBeanValue = new BeanValue(); 
				// ��ȡ��¼�û��Ự��ʶ
				identity.setFunctionId("BA00009");
				// �� SESSION �õ��û����� ID
				String userId = identity.getUserId();     

				/* ҵ���߼� - �˳�ϵͳ */
				secuServ.logout(userId);
				throw bEx;   	
			}
		}

		int roleNameL = 0;
		if (roleList != null && roleList.size() > 0) {
			// �û�Ĭ�Ͻ�ɫ(ȡ�б��һ��)
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
			// �û���ɫ�б�
//			uStub.getRolesOrFunctions(identity, identity.getUserId(), identity.getUserRole(), rolesOrFunctions);
//			if (rolesOrFunctions != null && rolesOrFunctions.size() > 0) {
//				role = rolesOrFunctions.get(0);
//			}
			// �û���ѡ����
			String firstWindow = "";
			// �û�Ĭ����ҳ
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
			// ��ɫĬ����ҳ
			if (firstWindow.trim().length() == 0 && role != null
					&& (role.getWebPreferredFunctionCode() != null && !role.getWebPreferredFunctionCode().equals(""))) {
				// �������ӻ�ȡfunction
				Condition condition = new Condition(TbFunction.class);
				condition.addParameterAnd("link", Operator.SQL_EQ, new String[]{role.getWebPreferredFunctionCode()});
				TbFunction functionForFirstPage = (TbFunction)(dao.getObjects(condition).get(0));
				//firstWindow = role.getWebPreferredFunctionCode() + "&firstPageFunctionId=" + functionForFirstPage.getFunctionId();
				///2012-4-11 wrb�޸ģ�����menuFunctionId�����Ա��״ε�¼ʱ���ܰ�ť��ʾ��ȫ�����⣬����2694��bug
				firstWindow = role.getWebPreferredFunctionCode() + "&menuFunctionId="+functionForFirstPage.getOrderId()+"&firstPageFunctionId=" + functionForFirstPage.getFunctionId();
				value.addRequestMap("firstPageFunctionId", functionForFirstPage.getFunctionId());
			}
			
			// ϵͳĬ����ҳ
			if (firstWindow.trim().length() == 0) {
				firstWindow = ProjectConfig.getProjectConfig("firstWindow").replace(",", "&");
				value.addRequestMap("firstPageFunctionId", firstWindow.substring(firstWindow.lastIndexOf("=") + 1));
			}
			
			
//			�޷�÷��������ѡҳΪĬ����ݵ�URL�������á�
			if(identity.getLoginType()!=null && identity.getLoginType().trim().equals("WS")){
				if(identity.getLoginUrl()!=null&& !identity.getLoginUrl().trim().equals("")){
					firstWindow=identity.getLoginUrl();
				}
				
				
			}
			value.addRequestMap("firstWindow", firstWindow);
			value.addSessionMap(ProjectConstants.SESSION_USER_ROLE, role);
			// ����Ĭ�ϵ�½��ɫ�� identity �У����ں����ȡ�û���ɫ����ݽ�ɫ������ҳ�ȡ�
			identity.setUserRole(role.getRoleId());
			
			List roleFunctionTemp = secuServ.getRoleFunctions(role) ;
			
			StringBuffer roleFunction = new StringBuffer();
			List tmprole = new ArrayList();
			RoleFunction temp = null;
			// �ҳ�����BA��ͷ�Ĺ���ID
			for (Object obj : roleFunctionTemp) {
				 temp = (RoleFunction) obj;
				if (temp.getFunctionId().substring(0, 2).equals("BA")) {
					roleFunction.append(temp.getFunctionId());
					tmprole.add(temp.getFunctionId());
				}
			}
			value.addSessionMap(ProjectConstants.SESSION_USER_ROLE_FUNCTION, roleFunction.toString());
			//modify by lilj add web�����б�list
			value.addSessionMap(ProjectConstants.SESSION_USER_ROLE_FUNCTIONLIST, tmprole);
			// �˵���ʾ��ʽ
			String style = ProjectConfig.getProjectConfig("menuStyle");
			value.addSessionMap("menuStyle", style);
			// �û���ɫȨ���б�(�˵�)
			// �Բ˵����й���
			List<Menus> functionList = function(roleFunctionTemp);
			// ���������Ľ�ɫ����
			value.addSessionMap("functions", functionList);
			// �����ɫList
			value.addSessionMap(ProjectConstants.SESSION_USER_ROLE_LIST, roleList);
			
			secuServ.login(user,identity);
			// �����ص�
			//List localList = dao.getObjects(TbLocal.class);
			// �ص��������ֻ������һ�����ü�¼
			//TbLocal local = localList != null && localList.size() > 0 ? (TbLocal) localList.get(0) : null;
		}
	}

	/**
	 * @see com.hatc.common.service.other.LoginManager#restLogin(com.hatc.base.common.RequestMap)
	 * ���µ�½
	 * 2011-12-23 �����ֵ�����.
	 */
	public BeanValue restLogin(RequestMap map) throws Exception {
		// ��ȡ�û���¼�Ự��ʶ
		ReqIdentity identity = getReqIdentity(map);
		if(identity!=null)
		{
		
			identity.setFunctionId("BA00005");
			// ����û������Ķ���
			releaseObjectLock(identity);

			// ���������־
			/* ��Session �õ��û����� */
			User user = (User) (map.getObject(ProjectConstants.SESSION_USER));
			
			//TODO ningliyu 2012-1-7
			//��������,������ֵ�����: ���ʷ������δ֪����.
			if(user==null)
			{   
				BaseException bEx = new BaseException(); 
				bEx.setExceptionCode("99999.16");  
				bEx.setMessage(UserResourceConfig.getInstance().getHtmlToDataValue(ProjectCode.SERVICE_NOT_LOGON));
				throw bEx;
			}  			
			
			secuServ.logout(user.getLoginId());
			
		}
		
		// ����ע�͵�����Ϊ���µ�¼��ʱ����ú�̨���񣬺�̨�����Ѿ���¼����־��
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
 
		 
		// ��ȡ�û���¼�Ự��ʶ
		ReqIdentity identity = getReqIdentity(map);
		//identity.setFunctionId("BA00005"); 
		// ����û������Ķ���
		releaseObjectLock(identity);
		BeanValue value = new BeanValue();
		// �˵�����ID
		String id = map.getString("menuFunctionId");
		value.addRequestMap("DefaultFirstPageId", id);
		// �û���ҳ����ID
		String firstPageFunctionId = map.getString("firstPageFunctionId");
		value.addRequestMap("firstPageFunctionId", firstPageFunctionId);
		// ����˵�ID
		String topMenuId = id.substring(0, 3) + "0000";
		// �Ӳ˵�ID
		String childMenuId = id.substring(0, 5) + "00";
		// ��ǰ��ɫ����Ȩ�޼�
		List functionList = (List) map.getObject("functions");
		int functionListSize = 0;
		if(functionList!=null)
			functionListSize=functionList.size();
		
		Menus temp = null;
		// ����
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
			{// ��һ��˵�
				menusList.add(menu);
				if (!id.equals(topMenuId)) 
				{
					for (Menus temp_1 : menu.getList()) 
					{
						if (temp_1.getFunction().getOrderId().equals(childMenuId)) 
						{// �ڶ���˵�
							menusList.add(temp_1);
							// �ݹ�
							temp = recursionMenus(temp_1, menusList);
							value.addRequestMap("menu", temp);
							Menus tempMenu = new Menus();
							RoleFunction rf = new RoleFunction();
							rf.setFunctionId(menu.getFunction().getFunctionId());
							rf.setFunctionName("������һ��");
							tempMenu.setFunction(rf);
							value.addRequestMap("backMenu", tempMenu);
							break;
						}
					}
				} else {
					// �ݹ�
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

		// ͼ����ʾ��ʽ
		String icoStyle = ProjectConfig.getProjectConfig("icoStyle");
		value.addRequestMap("icoStyle", icoStyle);
		// ��ʾ����ʾ����
		String shortcutStyle = ProjectConfig.getProjectConfig("shortcutStyle");
		value.addRequestMap("shortcutStyle", shortcutStyle);

		if (shortcutStyle.equals("1")) {  
			//MessageQueryBPO bpo = new MessageQueryBPO();
			//bpo.getHomePageMessage(identity,dao, map, value);
			
			setShortcutData(map, value);
		}

		// �˵����
		int icoTableW = shortcutStyle.equals("0") ? 823 : 610;
		value.addRequestMap("icoTableW", icoTableW);
		// ��ʾ����
		int shortcutW = shortcutStyle.equals("0") ? 0 : 200;
		value.addRequestMap("shortcutW", shortcutW);

		// �˵���ʾ������
		int icoSize = shortcutStyle.equals("0") ? 4 : 2;
		// �˵�TD�߶�
		int icoTableH = 480;
		String menuRowNum = ProjectConfig.getProjectConfig("menuRowNum");
		String menuRepeat = ProjectConfig.getProjectConfig("menuRepeat");
		value.addRequestMap("menuRowNum", menuRowNum); // ��������� Arielly ����100505
		value.addRequestMap("menuRepeat", menuRepeat); // �˵���ʾ��ʽ repeat_x ����repeat_y ����
		int rowSize = Integer.parseInt(menuRowNum == null ? "0" : menuRowNum);
		// �в��ܴ���10
		rowSize = rowSize > 10 ? 10 : rowSize;
		if (temp != null && temp.getList() != null && temp.getList().size() > 0) {
			int size = temp.getList().size();
			value.addRequestMap("listSize", size);
			int colSize = size / rowSize + (size % rowSize > 0 ? 1 : 0);
			// �в��ܴ���4
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

	// �ݹ飨�˵���
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
		
		 
		
		// ��ȡ�û���¼�Ự��ʶ
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
