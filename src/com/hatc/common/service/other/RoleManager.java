package com.hatc.common.service.other;

import com.hatc.base.common.BeanValue;
import com.hatc.base.common.RequestMap;
import com.hatc.common.service.ProjectManager;

/**
* 
* <b>system：</b>      协同办公平台<br/>
* <b>description：</b> 用户角色相关业务处理功能定义接口<br/>
* <b>author：</b>      王洋<br/>
* <b>copyright：</b>	　 北京华安天诚科技有限公司<br/>
* <b>version：</b>     VER1.00 2010-04-06<br/>
*
**/
public interface RoleManager extends ProjectManager {

	/**
	 * 处理内容：用户角色切换业务处理
	 * 
	 * @param rMap
	 *            请求参数集合
	 * @return BeanValue 返回的值Bean
	 * @throws Exception
	 * 
	 */
	public BeanValue roleSwitch(RequestMap rMap) throws Exception;
}
