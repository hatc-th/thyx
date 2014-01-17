package com.hatc.thyx.service.flyplan;

import com.hatc.base.common.BeanValue;
import com.hatc.base.common.RequestMap;

public interface FlyPlanManager {

	/**
	 * �������ݣ�ͨ���û���ѯ���мƻ���Ϣ�б�
	 * @param map
	 * @return value
	 * @throws Exception
	 */
	public BeanValue searchFlyPlanList( RequestMap requestMap ) throws Exception;
	
	/**
	 * �������ݣ�ͨ���û���ʼ�����мƻ���Ϣ
	 * @param map
	 * @return value
	 * @throws Exception
	 */
	public BeanValue initFlyPlanBaseInfo( RequestMap requestMap ) throws Exception;
	
	/**
	 * �������ݣ�ͨ���û��鿴���мƻ���Ϣ
	 * @param map
	 * @return value
	 * @throws Exception
	 */
	public BeanValue initFlyPlanInfo( RequestMap requestMap ) throws Exception;
	
	/**
	 * �������ݣ��޸ķ��мƻ�״̬�� �����ƻ�������ƻ��������ƻ���
	 * @param map
	 * @return value
	 * @throws Exception
	 */
	public BeanValue updateFlyPlanState( RequestMap requestMap ) throws Exception;
	
	/**
	 * �������ݣ�ͨ���û�������мƻ���Ϣ
	 * @param map
	 * @return value
	 * @throws Exception
	 */
	public BeanValue saveFlyPlanInfo( RequestMap requestMap ) throws Exception;
	
	/**
	 * �������ݣ�����sqlд���ݿ�
	 * @param value 
	 * @param dbConType :  	1 ��ʾ���� @192.168.6.44:1521:esri 
	 * 						2 ��ʾ���� @10.90.90.161:1521:H1501
	 * @return 
	 * @throws Exception
	 */
	public void updateFlyPlanBySql(BeanValue value , String dbConType) throws Exception;
	
	
	/**
	 * �������ݣ�ͨ���û�������мƻ���ɡ�Ŀ�Ļ�����Ϣ
	 * @param map
	 * @return value
	 * @throws Exception
	 */
	public BeanValue saveFlyPlanAirPortInfo( RequestMap requestMap ) throws Exception;
	
	/**
	 * �������ݣ�ͨ���û����泣�ú���
	 * @param map
	 * @return value
	 * @throws Exception
	 */
	public BeanValue saveGeneralSkyWay( RequestMap requestMap ) throws Exception;
	
	/**
	 * �������ݣ���ѡ��ĳ��ú�������Ϊ�ƻ�����������
	 * @param map
	 * @return value
	 * @throws Exception
	 */
	public BeanValue initSkyWayByGaneral( RequestMap requestMap ) throws Exception;
	
	/**
	 * �������ݣ���ѯ�û��ĳ��ú���
	 * @param map
	 * @return value
	 * @throws Exception
	 */
	public BeanValue getGeneralSkyWays( RequestMap requestMap ) throws Exception;
	
	/**
	 * �������ݣ�ͨ���û�ɾ�����мƻ���Ϣ
	 * @param map
	 * @return value
	 * @throws Exception
	 */
	public BeanValue deleteFlyPlan( RequestMap requestMap ) throws Exception;

	/**
	 * �������ݣ���ȡ��������METAR������Ϣ
	 * @param map
	 * @return value
	 * @throws Exception
	 */
	public BeanValue getAdMetarInfo( RequestMap requestMap ) throws Exception;
	
	
}
