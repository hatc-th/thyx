package com.hatc.base.service;

import com.hatc.base.common.BeanValue;
import com.hatc.base.common.RequestMap;


public interface EntityManager {
	
	/**
	 * 保存实体导入
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public BeanValue initSave(RequestMap map) throws Exception;
	
	/**
	 * 保存实体
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public BeanValue save(RequestMap map) throws Exception;
	
	/**
	 * 修改实体导入
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public BeanValue initUpdate(RequestMap map) throws Exception;
	
	/**
	 * 修改实体
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public BeanValue update(RequestMap map) throws Exception;
	
	/**
	 * 查询实体列表
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public BeanValue searchList(RequestMap map) throws Exception;
	
	/**
	 * 查询实体详细信息
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public BeanValue detail(RequestMap map) throws Exception;
	
	/**
	 * 删除实体
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public BeanValue delete(RequestMap map) throws Exception;

}
