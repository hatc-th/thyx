package com.hatc.thyx.service.actflyplan;

import com.hatc.base.common.BeanValue;
import com.hatc.base.common.RequestMap;
import com.hatc.common.service.ProjectManager;

/**
 * ���мƻ�ʵʩ����ӿ�
 * @author zhulin 2013-07-01
 *
 */
public interface ActFlyPlanManager  extends ProjectManager {

	/**
	 * ʵʩǰȷ���б��ѯ
	 * @param aRequestMap
	 * @return 
	 * @throws Exception
	 */
	public BeanValue queryAffirmBeforeActPlanList(RequestMap aRequestMap) throws Exception;
	
	/**
	 * ʵʩȷ��
	 * @param aRequestMap
	 * @return
	 * @throws Exception
	 */
	public BeanValue saveAffirmBeforeAct(RequestMap aRequestMap) throws Exception;

	/**
	 * �������ݣ�ͨ���û��鿴���мƻ���Ϣ
	 * @param map
	 * @return value
	 * @throws Exception
	 */
	public BeanValue initFlyPlanBaseInfo( RequestMap requestMap ) throws Exception;
}