package com.hatc.common.service.other.impl;

import com.hatc.base.common.BeanValue;
import com.hatc.base.common.RequestMap;
import com.hatc.common.service.impl.ProjectManagerImpl;
import com.hatc.common.service.other.RoleManager;
import com.hatc.common.servicestub.ReqIdentity;

/**
* 
* <b>system��</b>      Эͬ�칫ƽ̨<br/>
* <b>description��</b> �û���ɫ��ع��ܴ���ʵ����<br/>
* <b>author��</b>      ����<br/>
* <b>copyright��</b>	�� ����������ϿƼ����޹�˾<br/>
* <b>version��</b>     VER1.00 2010-04-06<br/>
*
**/
public class RoleManagerImpl extends ProjectManagerImpl implements RoleManager {

	@SuppressWarnings("unchecked")
	public BeanValue roleSwitch(RequestMap map) throws Exception {
		// ��ȡ�û���¼�Ự��ʶ
		ReqIdentity identity = getReqIdentity(map);
		// ����û������Ķ���
		releaseObjectLock(identity);

		BeanValue value = new BeanValue();
		
		return value;
	}
}
