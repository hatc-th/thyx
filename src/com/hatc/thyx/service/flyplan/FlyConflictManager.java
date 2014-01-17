package com.hatc.thyx.service.flyplan;

import com.hatc.base.common.BeanValue;
import com.hatc.base.common.RequestMap;
import com.hatc.hibernate.vo.FlyConflictVO;

/**
 * 航线冲突manager
 * @author wangdonghua
 * @date 2013-10-10 14:00
 * @version 1.0
 * */
public interface FlyConflictManager {
	
	//机场关闭
	FlyConflictVO getADClosedInfo(String icao,String time)throws Exception;
	
	//风速冲突信息
	FlyConflictVO getWindspeedConflictInfo(String icao,String planeType,String time)throws Exception;
	
	//能见度冲突信息
	FlyConflictVO getVisibilityConflictInfo(String icao,String planeType,String time)throws Exception;
	
	//得到飞行冲突信息
	BeanValue getFlyConflictInfo(RequestMap map)throws Exception;
	
	//起飞时间冲突
	public FlyConflictVO getAdepTimeConflict(String objId,String time)throws Exception;
	
}
