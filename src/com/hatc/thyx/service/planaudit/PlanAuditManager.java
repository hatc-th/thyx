/**
 * 
 */
package com.hatc.thyx.service.planaudit;

import com.hatc.base.common.BeanValue;
import com.hatc.base.common.RequestMap;

/**
 * @author Administrator
 *
 */
public interface PlanAuditManager {
	/**
	 * 飞行服务站审批查询列表
	 * @param requestMap
	 * @return
	 * @throws Exception
	 */
	BeanValue searchList(RequestMap map) throws Exception;
	
	BeanValue initPlanInfo(RequestMap map) throws Exception;
	
	BeanValue saveAuditFlow(RequestMap map) throws Exception;
	
	BeanValue flowDetail(RequestMap map) throws Exception;
	
	void updateSuperData(String planId) throws Exception;
	/**
	 * 飞行服务讲解页面初始化
	 * @param requestMap
	 * @return
	 * @throws Exception
	 */
	BeanValue initFlyServiceInfo (RequestMap map) throws Exception;
	
	/**
	 * 飞行服务讲解内容查询
	 * @param requestMap
	 * @return
	 * @throws Exception
	 */
	BeanValue searchFlyServiceInfo( RequestMap requestMap ) throws Exception;
	
	/**
	 * 飞行服务讲解内容保存
	 * @param requestMap
	 * @return
	 * @throws Exception
	 */
	BeanValue saveFlyServiceInfo( RequestMap requestMap ) throws Exception;

}
