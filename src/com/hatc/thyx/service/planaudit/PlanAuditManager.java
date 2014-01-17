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
	 * ���з���վ������ѯ�б�
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
	 * ���з��񽲽�ҳ���ʼ��
	 * @param requestMap
	 * @return
	 * @throws Exception
	 */
	BeanValue initFlyServiceInfo (RequestMap map) throws Exception;
	
	/**
	 * ���з��񽲽����ݲ�ѯ
	 * @param requestMap
	 * @return
	 * @throws Exception
	 */
	BeanValue searchFlyServiceInfo( RequestMap requestMap ) throws Exception;
	
	/**
	 * ���з��񽲽����ݱ���
	 * @param requestMap
	 * @return
	 * @throws Exception
	 */
	BeanValue saveFlyServiceInfo( RequestMap requestMap ) throws Exception;

}
