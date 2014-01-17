package com.hatc.common.service.other.impl;

import com.hatc.base.common.BeanValue;
import com.hatc.base.common.RequestMap;
import com.hatc.common.service.impl.ProjectManagerImpl;
import com.hatc.common.service.other.RoleManager;
import com.hatc.common.servicestub.ReqIdentity;

/**
* 
* <b>system：</b>      协同办公平台<br/>
* <b>description：</b> 用户角色相关功能处理实现类<br/>
* <b>author：</b>      王洋<br/>
* <b>copyright：</b>	　 北京华安天诚科技有限公司<br/>
* <b>version：</b>     VER1.00 2010-04-06<br/>
*
**/
public class RoleManagerImpl extends ProjectManagerImpl implements RoleManager {

	@SuppressWarnings("unchecked")
	public BeanValue roleSwitch(RequestMap map) throws Exception {
		// 获取用户登录会话标识
		ReqIdentity identity = getReqIdentity(map);
		// 解除用户锁定的对象
		releaseObjectLock(identity);

		BeanValue value = new BeanValue();
		
		return value;
	}
}
