package com.hatc.thyx.service.flyplan;

import com.hatc.base.common.BeanValue;
import com.hatc.base.common.RequestMap;

public interface FlyPlanManager {

	/**
	 * 处理内容：通航用户查询飞行计划信息列表
	 * @param map
	 * @return value
	 * @throws Exception
	 */
	public BeanValue searchFlyPlanList( RequestMap requestMap ) throws Exception;
	
	/**
	 * 处理内容：通航用户初始化飞行计划信息
	 * @param map
	 * @return value
	 * @throws Exception
	 */
	public BeanValue initFlyPlanBaseInfo( RequestMap requestMap ) throws Exception;
	
	/**
	 * 处理内容：通航用户查看飞行计划信息
	 * @param map
	 * @return value
	 * @throws Exception
	 */
	public BeanValue initFlyPlanInfo( RequestMap requestMap ) throws Exception;
	
	/**
	 * 处理内容：修改飞行计划状态： 报备计划，激活计划，或撤销计划。
	 * @param map
	 * @return value
	 * @throws Exception
	 */
	public BeanValue updateFlyPlanState( RequestMap requestMap ) throws Exception;
	
	/**
	 * 处理内容：通航用户保存飞行计划信息
	 * @param map
	 * @return value
	 * @throws Exception
	 */
	public BeanValue saveFlyPlanInfo( RequestMap requestMap ) throws Exception;
	
	/**
	 * 处理内容：调用sql写数据库
	 * @param value 
	 * @param dbConType :  	1 表示连接 @192.168.6.44:1521:esri 
	 * 						2 表示连接 @10.90.90.161:1521:H1501
	 * @return 
	 * @throws Exception
	 */
	public void updateFlyPlanBySql(BeanValue value , String dbConType) throws Exception;
	
	
	/**
	 * 处理内容：通航用户保存飞行计划起飞、目的机场信息
	 * @param map
	 * @return value
	 * @throws Exception
	 */
	public BeanValue saveFlyPlanAirPortInfo( RequestMap requestMap ) throws Exception;
	
	/**
	 * 处理内容：通航用户保存常用航线
	 * @param map
	 * @return value
	 * @throws Exception
	 */
	public BeanValue saveGeneralSkyWay( RequestMap requestMap ) throws Exception;
	
	/**
	 * 处理内容：将选择的常用航线设置为计划的主备航线
	 * @param map
	 * @return value
	 * @throws Exception
	 */
	public BeanValue initSkyWayByGaneral( RequestMap requestMap ) throws Exception;
	
	/**
	 * 处理内容：查询用户的常用航线
	 * @param map
	 * @return value
	 * @throws Exception
	 */
	public BeanValue getGeneralSkyWays( RequestMap requestMap ) throws Exception;
	
	/**
	 * 处理内容：通航用户删除飞行计划信息
	 * @param map
	 * @return value
	 * @throws Exception
	 */
	public BeanValue deleteFlyPlan( RequestMap requestMap ) throws Exception;

	/**
	 * 处理内容：获取机场最新METAR气象信息
	 * @param map
	 * @return value
	 * @throws Exception
	 */
	public BeanValue getAdMetarInfo( RequestMap requestMap ) throws Exception;
	
	/**
	 * 获取从机场选中的机场对应的数据库中的ID
	 * @param map
	 * @return value
	 * @throws Exception
	 */
	public BeanValue getAdIdInfo( RequestMap requestMap ) throws Exception;
	
	
}
