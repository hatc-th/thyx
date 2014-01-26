package com.hatc.thyx.service.actflyplan;

import com.hatc.base.common.BeanValue;
import com.hatc.base.common.RequestMap;
import com.hatc.common.service.ProjectManager;

/**
 * 飞行计划实施管理接口
 * @author zhulin 2013-07-01
 *
 */
public interface ActFlyPlanManager  extends ProjectManager {

	/**
	 * 实施前确认列表查询
	 * @param aRequestMap
	 * @return 
	 * @throws Exception
	 */
	public BeanValue queryAffirmBeforeActPlanList(RequestMap aRequestMap) throws Exception;
	
	/**
	 * 实施确认
	 * @param aRequestMap
	 * @return
	 * @throws Exception
	 */
	public BeanValue saveAffirmBeforeAct(RequestMap aRequestMap) throws Exception;

	/**
	 * 处理内容：通航用户查看飞行计划信息
	 * @param map
	 * @return value
	 * @throws Exception
	 */
	public BeanValue initFlyPlanBaseInfo( RequestMap requestMap ) throws Exception;
}