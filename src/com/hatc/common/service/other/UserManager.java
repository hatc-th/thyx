package com.hatc.common.service.other;

import com.hatc.base.common.BeanValue;
import com.hatc.base.common.RequestMap;
import com.hatc.common.service.ProjectManager;

/**
* 
* <b>system��</b>      Эͬ�칫ƽ̨<br/>
* <b>description��</b> �û��������ء��˳�ϵͳ�ӿڶ���<br/>
* <b>author��</b>      ����<br/>
* <b>copyright��</b>	�� ����������ϿƼ����޹�˾<br/>
* <b>version��</b>     VER1.00 2010-04-06<br/>
*
**/
public interface UserManager extends ProjectManager {

	/**
	 * �޸�����(��ʼ��)
	 */
	BeanValue setPwd(RequestMap aRequestMap) throws Exception;

	/**
	 * �޸�����(�ύ)
	 */
	BeanValue updatePwd(RequestMap aRequestMap) throws Exception;

	/**
	 * �˳�ϵͳ
	 */
	BeanValue logoutSystem(RequestMap aRequestMap) throws Exception;
	
	/**
	 * ��ΪĬ�Ͻ�ɫ
	 */
	BeanValue saveDefaultRole(RequestMap aRequestMap) throws Exception;

	/**
	 * ��ΪĬ����ҳ
	 */
	BeanValue saveDefaultFirstPage(RequestMap aRequestMap) throws Exception;
	
	/**
	 * ͨ���ļ�����
	 * 
	 * @param RequestMap
	 *            ������� Map
	 * @return BeanValue ҵ������ɺ�Ľ����
	 */
	public BeanValue downFiles(RequestMap map) throws Exception ;

	/**
	 * ���ؿͻ���
	 * 
	 * @param RequestMap
	 *            ������� Map
	 * @return BeanValue ҵ������ɺ�Ľ����
	 */
	public BeanValue downloadClient(RequestMap map) throws Exception ;

	/**
	 * ����JAVA JDK
	 * 
	 * @param RequestMap
	 *            ������� Map
	 * @return BeanValue ҵ������ɺ�Ľ����
	 */
	public BeanValue downloadJDK(RequestMap map) throws Exception ;
}