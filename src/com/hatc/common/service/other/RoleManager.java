package com.hatc.common.service.other;

import com.hatc.base.common.BeanValue;
import com.hatc.base.common.RequestMap;
import com.hatc.common.service.ProjectManager;

/**
* 
* <b>system��</b>      Эͬ�칫ƽ̨<br/>
* <b>description��</b> �û���ɫ���ҵ�����ܶ���ӿ�<br/>
* <b>author��</b>      ����<br/>
* <b>copyright��</b>	�� ����������ϿƼ����޹�˾<br/>
* <b>version��</b>     VER1.00 2010-04-06<br/>
*
**/
public interface RoleManager extends ProjectManager {

	/**
	 * �������ݣ��û���ɫ�л�ҵ����
	 * 
	 * @param rMap
	 *            �����������
	 * @return BeanValue ���ص�ֵBean
	 * @throws Exception
	 * 
	 */
	public BeanValue roleSwitch(RequestMap rMap) throws Exception;
}
