package com.hatc.common.service.other;

import com.hatc.base.common.BeanValue;
import com.hatc.base.common.RequestMap;
import com.hatc.common.service.ProjectManager;

/**
* 
* <b>system��</b>      Эͬ�칫ƽ̨<br/>
* <b>description��</b> �û���¼ҵ���ܶ���ӿ�<br/>
* <b>author��</b>      ����<br/>
* <b>copyright��</b>	�� ����������ϿƼ����޹�˾<br/>
* <b>version��</b>     VER1.00 2010-04-06<br/>
*
**/
public interface LoginManager extends ProjectManager {
	
	/**
	 * �������ݣ�ϵͳ��ҳ����
	 * 
	 * @param map
	 *            ǰ̨��ȡ�Ĳ���Map
	 * @return BeanValue ҵ������ֵ����
	 */
	public BeanValue index(RequestMap map) throws Exception;

	/**
	 * �������ݣ��û�����վ��ҳ��¼ҵ����
	 * 
	 * @param rMap
	 *            �����������
	 * @return BeanValue ���ص�ֵBean
	 * @throws Exception
	 * 
	 */
	public BeanValue webLogin(RequestMap rMap) throws Exception;
	
	/**
	 * �������ݣ��û��ӿͻ��˵�¼ҵ����
	 * 
	 * @param rMap
	 *            �����������
	 * @return BeanValue ���ص�ֵBean
	 * @throws Exception
	 * 
	 */
	public BeanValue csLogin(RequestMap rMap) throws Exception;
	/**
	 * �������ݣ��û����ⲿϵͳ��¼ҵ����
	 * �޷�÷
	 * 2012-03-15
	 * @param rMap
	 *            �����������
	 * @return BeanValue ���ص�ֵBean
	 * @throws Exception
	 * 
	 */
	public BeanValue wsLogin(RequestMap rMap) throws Exception;
	
	/**
	 * �������ݣ����µ�½
	 * 
	 * @param map
	 *            ǰ̨��ȡ�Ĳ���Map
	 * @return BeanValue ҵ������ֵ����
	 */
	public BeanValue restLogin(RequestMap map) throws Exception;
	
	/**
	 * �������ݣ�ͼ��˵�
	 * 
	 * @param map
	 *            ǰ̨��ȡ�Ĳ���Map
	 * @return BeanValue ҵ������ֵ����
	 */
	public BeanValue menuIcoHref(RequestMap map) throws Exception;
	
	/**
	 * �������ݣ���ҳ
	 * 
	 * @param map
	 *            ǰ̨��ȡ�Ĳ���Map
	 * @return BeanValue ҵ������ֵ����
	 */
	public BeanValue firstPage(RequestMap map) throws Exception;
}
