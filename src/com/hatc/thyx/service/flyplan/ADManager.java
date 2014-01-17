package com.hatc.thyx.service.flyplan;

import com.hatc.base.common.BeanValue;
import com.hatc.base.common.RequestMap;

/**
 * 机场处理manager
 * author：wangdh
 * date：2013-09-17 17:00
 * */
public interface ADManager {
	
	BeanValue getADInfo(RequestMap map)throws Exception;
	
	
	BeanValue getGeneralSkyWays(RequestMap map)throws Exception;
	
	/**
	 * 处理内容：将选择的常用航线设置为计划的主备航线
	 * @param map
	 * @return value
	 * @throws Exception
	 */
	BeanValue initSkyWayByGaneral( RequestMap requestMap ) throws Exception;
	
	BeanValue getNVOrFFXList(RequestMap requestMap)throws Exception;
	
	BeanValue getADListByName(RequestMap requestMap)throws Exception;
	
	BeanValue getNVListByName(RequestMap requestMap)throws Exception;
	
	BeanValue getADAttachment(RequestMap requestMap)throws Exception;
	
}
