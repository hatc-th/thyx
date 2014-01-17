package com.hatc.thyx.service.flyplan;

import com.hatc.base.common.BeanValue;
import com.hatc.base.common.RequestMap;
import com.hatc.hibernate.vo.FlyConflictVO;

/**
 * ���߳�ͻmanager
 * @author wangdonghua
 * @date 2013-10-10 14:00
 * @version 1.0
 * */
public interface FlyConflictManager {
	
	//�����ر�
	FlyConflictVO getADClosedInfo(String icao,String time)throws Exception;
	
	//���ٳ�ͻ��Ϣ
	FlyConflictVO getWindspeedConflictInfo(String icao,String planeType,String time)throws Exception;
	
	//�ܼ��ȳ�ͻ��Ϣ
	FlyConflictVO getVisibilityConflictInfo(String icao,String planeType,String time)throws Exception;
	
	//�õ����г�ͻ��Ϣ
	BeanValue getFlyConflictInfo(RequestMap map)throws Exception;
	
	//���ʱ���ͻ
	public FlyConflictVO getAdepTimeConflict(String objId,String time)throws Exception;
	
}
