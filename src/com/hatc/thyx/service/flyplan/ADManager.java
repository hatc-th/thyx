package com.hatc.thyx.service.flyplan;

import com.hatc.base.common.BeanValue;
import com.hatc.base.common.RequestMap;

/**
 * ��������manager
 * author��wangdh
 * date��2013-09-17 17:00
 * */
public interface ADManager {
	
	BeanValue getADInfo(RequestMap map)throws Exception;
	
	
	BeanValue getGeneralSkyWays(RequestMap map)throws Exception;
	
	/**
	 * �������ݣ���ѡ��ĳ��ú�������Ϊ�ƻ�����������
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
