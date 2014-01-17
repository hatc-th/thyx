package com.hatc.common.service.other.impl;

import com.hatc.base.common.BeanValue;
import com.hatc.base.common.RequestMap;
import com.hatc.common.contants.ProjectConstants;
import com.hatc.common.contants.ProjectParameters;
import com.hatc.common.hibernate.pojo.TbParameterSetup;
import com.hatc.common.service.impl.ProjectManagerImpl;
import com.hatc.common.service.other.UserManager;
import com.hatc.common.servicestub.ReqIdentity;
import com.hatc.common.web.config.ProjectConfig;

/**
* 
* <b>system：</b>      协同办公平台<br/>
* <b>description：</b> 用户管理、下载、退出系统实现类<br/>
* <b>author：</b>      王洋<br/>
* <b>copyright：</b>	　 北京华安天诚科技有限公司<br/>
* <b>version：</b>     VER1.00 2010-04-06<br/>
*
**/
public class UserManagerImpl extends ProjectManagerImpl implements UserManager {

	//private BeanValue aBeanValue = new BeanValue(); // 业务处理完成后的结果集

	//private ReqIdentity aReqIdentity = new ReqIdentity(); // 用户身份标识

	/**
	 * 修改密码(初始化)
	 */
	public BeanValue setPwd(RequestMap aRequestMap) throws Exception {

		BeanValue aBeanValue = new BeanValue(); 
		// 获取登录用户会话标识
		ReqIdentity aReqIdentity = getReqIdentity(aRequestMap);
		// 释放当前登录会话的所有对象锁
		releaseObjectLock(aReqIdentity);
 		
	    //TODO ningliyu 2011-12-10
	 
		aReqIdentity.setFunctionId("BA00007");
 		
		//名称：名称：PWD_SECRET_LEVEL 内容：密码强度等级（1-5）
		TbParameterSetup par = ProjectParameters.getParameter(ProjectConstants.PWD_SECRET_LEVEL);
		aBeanValue.addRequestMap(ProjectConstants.PWD_SECRET_LEVEL, par.getLower());
		//名称：MODI_PWD_INTERVAL 内容：强制修改密码时间间隔
		par = ProjectParameters.getParameter(ProjectConstants.MODI_PWD_INTERVAL);
		aBeanValue.addRequestMap(ProjectConstants.MODI_PWD_INTERVAL, par.getLower());
		//名称：PASSWORD_LENGTH 内容：密码长度
		par = ProjectParameters.getParameter(ProjectConstants.PASSWORD_LENGTH);
		aBeanValue.addRequestMap(ProjectConstants.PASSWORD_LENGTH, par.getLower());

		// 控制符
		String ctrl = aRequestMap.getString("ctrl");
		aBeanValue.addRequestMap("ctrl", ctrl);
		
		return aBeanValue;
	}

	/**
	 * 修改密码(提交)
	 */
	public BeanValue updatePwd(RequestMap aRequestMap) throws Exception 
	{
	
		BeanValue aBeanValue = new BeanValue(); 
		// 获取登录用户会话标识
		ReqIdentity aReqIdentity = getReqIdentity(aRequestMap);
		aReqIdentity.setFunctionId("BA00007");

		/* WEB 输入参数 */
		String origPwd = aRequestMap.getString("origPwd").trim(); // 原密码
		String newPwd = aRequestMap.getString("newPwd").trim(); // 新密码
		
		//TODO /* 业务逻辑 - 修改密码 */

		return aBeanValue;
	}

	/**
	 * 退出系统
	 */
	public BeanValue logoutSystem(RequestMap aRequestMap) throws Exception {

		
		BeanValue aBeanValue = new BeanValue(); 
		// 获取登录用户会话标识
		ReqIdentity aReqIdentity = getReqIdentity(aRequestMap);

		aReqIdentity.setFunctionId("BA00009");
		// 从 SESSION 得到用户对象 ID
		String userId = aReqIdentity.getUserId();     

		//TODO/* 业务逻辑 - 退出系统 */

		return aBeanValue;
	}

	/**
	 * 下载JAVA JDK
	 * 
	 * @param RequestMap
	 *            输入参数 Map
	 * @return BeanValue 业务处理完成后的结果集
	 */
	public BeanValue downloadJDK(RequestMap map) throws Exception {
   
		BeanValue aBeanValue = new BeanValue(); 
		// 获取登录用户会话标识
		ReqIdentity aReqIdentity = getReqIdentity(map);
		 
		BeanValue value = new BeanValue();
		String appVersion = map.getString("appVersion"); // 得到用户所用操作系统类型

		String filename = null; // 待下载文件名
		String filesname = null; // 文件保存名
		String filetype = null; // 待下载文件类型

		String syspath = ProjectConfig.getProjectConfig("downClient"); // 系统下载客户端配置参数
		String filepath = syspath + "/"; // 待下载文件路径
		
		if (appVersion.equals("windows")) {
			filename = ProjectConfig.getProjectConfig("JDKFileName_windows");
			filetype = ProjectConfig.getProjectConfig("JDKFilePostfix_windows");
		} else if (appVersion.equals("linux")) {
			filename = ProjectConfig.getProjectConfig("JDKFileName_linux");
			filetype = ProjectConfig.getProjectConfig("JDKFilePostfix_linux");
		} else {
			// 待处理
		}

		map.addParameter("DOC_NAME", filename);
		map.addParameter("DOC_SNAME", filesname);
		map.addParameter("DOC_TYPE", filetype);
		map.addParameter("DOC_PATH", filepath);
		value = this.downFiles(map);

		return value;
	}

	public BeanValue saveDefaultRole(RequestMap aRequestMap) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public BeanValue saveDefaultFirstPage(RequestMap aRequestMap)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public BeanValue downloadClient(RequestMap map) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
}
