package com.hatc.common.service.other;

import com.hatc.base.common.BeanValue;
import com.hatc.base.common.RequestMap;
import com.hatc.common.service.ProjectManager;

/**
* 
* <b>system：</b>      协同办公平台<br/>
* <b>description：</b> 用户登录业务功能定义接口<br/>
* <b>author：</b>      王洋<br/>
* <b>copyright：</b>	　 北京华安天诚科技有限公司<br/>
* <b>version：</b>     VER1.00 2010-04-06<br/>
*
**/
public interface LoginManager extends ProjectManager {
	
	/**
	 * 处理内容：系统首页流程
	 * 
	 * @param map
	 *            前台获取的参数Map
	 * @return BeanValue 业务处理后的值对象
	 */
	public BeanValue index(RequestMap map) throws Exception;

	/**
	 * 处理内容：用户从网站首页登录业务处理
	 * 
	 * @param rMap
	 *            请求参数集合
	 * @return BeanValue 返回的值Bean
	 * @throws Exception
	 * 
	 */
	public BeanValue webLogin(RequestMap rMap) throws Exception;
	
	/**
	 * 处理内容：用户从客户端登录业务处理
	 * 
	 * @param rMap
	 *            请求参数集合
	 * @return BeanValue 返回的值Bean
	 * @throws Exception
	 * 
	 */
	public BeanValue csLogin(RequestMap rMap) throws Exception;
	/**
	 * 处理内容：用户从外部系统登录业务处理
	 * 罗凤梅
	 * 2012-03-15
	 * @param rMap
	 *            请求参数集合
	 * @return BeanValue 返回的值Bean
	 * @throws Exception
	 * 
	 */
	public BeanValue wsLogin(RequestMap rMap) throws Exception;
	
	/**
	 * 处理内容：重新登陆
	 * 
	 * @param map
	 *            前台获取的参数Map
	 * @return BeanValue 业务处理后的值对象
	 */
	public BeanValue restLogin(RequestMap map) throws Exception;
	
	/**
	 * 处理内容：图标菜单
	 * 
	 * @param map
	 *            前台获取的参数Map
	 * @return BeanValue 业务处理后的值对象
	 */
	public BeanValue menuIcoHref(RequestMap map) throws Exception;
	
	/**
	 * 处理内容：首页
	 * 
	 * @param map
	 *            前台获取的参数Map
	 * @return BeanValue 业务处理后的值对象
	 */
	public BeanValue firstPage(RequestMap map) throws Exception;
}
