package com.hatc.base.service;

import com.hatc.base.common.BeanValue;
import com.hatc.base.common.RequestMap;


public interface EntityManager {
	
	/**
	 * ����ʵ�嵼��
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public BeanValue initSave(RequestMap map) throws Exception;
	
	/**
	 * ����ʵ��
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public BeanValue save(RequestMap map) throws Exception;
	
	/**
	 * �޸�ʵ�嵼��
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public BeanValue initUpdate(RequestMap map) throws Exception;
	
	/**
	 * �޸�ʵ��
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public BeanValue update(RequestMap map) throws Exception;
	
	/**
	 * ��ѯʵ���б�
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public BeanValue searchList(RequestMap map) throws Exception;
	
	/**
	 * ��ѯʵ����ϸ��Ϣ
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public BeanValue detail(RequestMap map) throws Exception;
	
	/**
	 * ɾ��ʵ��
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public BeanValue delete(RequestMap map) throws Exception;

}
