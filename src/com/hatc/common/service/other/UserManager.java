package com.hatc.common.service.other;

import com.hatc.base.common.BeanValue;
import com.hatc.base.common.RequestMap;
import com.hatc.common.service.ProjectManager;

/**
* 
* <b>system：</b>      协同办公平台<br/>
* <b>description：</b> 用户管理、下载、退出系统接口定义<br/>
* <b>author：</b>      王洋<br/>
* <b>copyright：</b>	　 北京华安天诚科技有限公司<br/>
* <b>version：</b>     VER1.00 2010-04-06<br/>
*
**/
public interface UserManager extends ProjectManager {

	/**
	 * 修改密码(初始化)
	 */
	BeanValue setPwd(RequestMap aRequestMap) throws Exception;

	/**
	 * 修改密码(提交)
	 */
	BeanValue updatePwd(RequestMap aRequestMap) throws Exception;

	/**
	 * 退出系统
	 */
	BeanValue logoutSystem(RequestMap aRequestMap) throws Exception;
	
	/**
	 * 设为默认角色
	 */
	BeanValue saveDefaultRole(RequestMap aRequestMap) throws Exception;

	/**
	 * 设为默认首页
	 */
	BeanValue saveDefaultFirstPage(RequestMap aRequestMap) throws Exception;
	
	/**
	 * 通用文件下载
	 * 
	 * @param RequestMap
	 *            输入参数 Map
	 * @return BeanValue 业务处理完成后的结果集
	 */
	public BeanValue downFiles(RequestMap map) throws Exception ;

	/**
	 * 下载客户端
	 * 
	 * @param RequestMap
	 *            输入参数 Map
	 * @return BeanValue 业务处理完成后的结果集
	 */
	public BeanValue downloadClient(RequestMap map) throws Exception ;

	/**
	 * 下载JAVA JDK
	 * 
	 * @param RequestMap
	 *            输入参数 Map
	 * @return BeanValue 业务处理完成后的结果集
	 */
	public BeanValue downloadJDK(RequestMap map) throws Exception ;
}