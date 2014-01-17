package com.hatc.thyx.service.flyplan.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import com.hatc.base.common.BeanValue;
import com.hatc.base.common.RequestMap;
import com.hatc.common.service.impl.ProjectManagerImpl;
import com.hatc.hibernate.vo.FlyConflictVO;
import com.hatc.thyx.service.flyplan.FlyConflictManager;

/**
 * ���߳�ͻmanager
 * @author wangdonghua
 * @date 2013-10-10 14:15
 * */
public class FlyConflictManagerImpl extends ProjectManagerImpl implements FlyConflictManager {
	
	/*
	 * ��ѯ���ʱ���Ƿ��б�ķɻ����
	 * @param objId
	 * @param time(pattern:yyyy-MM-dd hh:mi) 
	 * @throws Exception
	 * @return FlyConflictVO
	 * */
	public FlyConflictVO getAdepTimeConflict(String objId,String time)throws Exception{
		String[] strs  = time.split(" ");
		String flyDate = strs[0];
		String flyTime = strs[1];
		StringBuffer sql = new StringBuffer(" ");
		sql.append(" SELECT planid,f_plan_name FROM t_fpl t WHERE t.adep = '").append(objId).append("' ");
		sql.append(" AND to_char(t.dates,'yyyy-MM-dd') = '").append(flyDate).append("' ");
		sql.append(" AND t.setd = '").append(flyTime).append("' ");
		String[] column = new String[]{"planid","f_plan_name"};
		
		List<String[]> rs = dao.getObjects(sql.toString(), column);
		if(rs != null && rs.size() > 0){
				String[] str = rs.get(0);
				FlyConflictVO flyConflict = new FlyConflictVO();
				flyConflict.setId(Long.valueOf(str[0]));
				flyConflict.setStartTime(time);
				flyConflict.setEndTime(time);
				flyConflict.setType("���ʱ���ͻ");
				flyConflict.setFrom("flyPlan");
				return flyConflict;
		}
		return null;
	}
	
	/*
	 * �жϻ����Ƿ�Ϊ�ر�״̬
	 * @param icao ����icao��
	 * @param time ���ʱ��/����ʱ��
	 * @throws Exception
	 * @return FlyConflictVO
	 * */
	
	public FlyConflictVO getADClosedInfo(String icao,String time) throws Exception {
		
		String[] column = new String[]{"n_id","stime","etime"};
		StringBuffer sql = new StringBuffer("");
		sql.append(" SELECT n_id,to_char(n_fstime,'yyyy-MM-dd HH24:mi') stime,to_char(n_fetime,'yyyy-MM-dd HH24:mi') etime ");
		sql.append(" FROM t_notam WHERE to_date('").append(time).append("','yyyy-MM-dd HH24:mi') > n_fstime ");
		sql.append(" AND to_date('").append(time).append("','yyyy-MM-dd HH24:mi') < n_fetime ");
		sql.append(" AND n_state = 1 AND n_kind = 'ad_cls' AND n_scope = '").append(icao).append("'");
		List<String[]> rs = dao.getObjects(sql.toString(), column);
		if(rs != null && rs.size() > 0){
			String[] str = rs.get(0);
			FlyConflictVO flyConflict = new FlyConflictVO();
			flyConflict.setId(Long.valueOf(str[0]));
			flyConflict.setStartTime(str[1]);
			flyConflict.setEndTime(str[2]);
			flyConflict.setType("�����ر�");
			flyConflict.setFrom("note");
			return flyConflict;
		}
		return null;
	}
	
	/*
	 * �ж��ܼ����Ƿ��ͻ
	 * @param icao  ����ICAO��
	 * @param planeType ����������
	 * @param time ���ʱ��/����ʱ��
	 * @throws Exception
	 * @return FlyConflictVO
	 * */
	public FlyConflictVO getVisibilityConflictInfo(String icao,String planeType,String time) throws Exception {
		Map<String,Integer> visibilityMap = this.getWindspeedAndVisibility(planeType);
		int minVivibility = visibilityMap.get("visibility");
		StringBuffer sql = new StringBuffer("");
		sql.append(" SELECT t1.forecastid,to_char(t1.begin_time,'yyyy-MM-dd') begin_time,to_char(t1.end_time,'yyyy-MM-dd') end_time,t2.visibility ");
		sql.append(" FROM tb_weather t1,tb_weather_visibility t2 WHERE t1.forecastid = t2.forecastid ");
		sql.append(" AND to_date('").append(time).append("','yyyy-MM-dd HH24:mi') between t1.begin_time and t1.end_time AND t1.state = '1' ");
		sql.append(" AND to_number(t2.visibility) < ").append(minVivibility).append(" AND t1.scope = '").append(icao).append("'");
		String[] column = new String[]{"forecastid","begin_time","end_time","visibility"};
		List<String[]> rs = dao.getObjects(sql.toString(), column);
		if(rs != null && rs.size() > 0){
			String[] str = rs.get(0);
			FlyConflictVO flyConflict = new FlyConflictVO();
			flyConflict.setId(Long.valueOf(str[0]));
			flyConflict.setStartTime(str[1]);
			flyConflict.setEndTime(str[2]);
			flyConflict.setDescription(str[3]);
			flyConflict.setFrom("weather-visibility");
			flyConflict.setType("�ܼ��ȵ�");
			return flyConflict;
		}
		return null;
	}
	
	/*
	 * ���ٳ�ͻ��Ϣ(Ŀǰ�޷��жϷɻ���ɣ�����ķ�����ʱֻȡ������)
	 * @param icao ����ICAO��
	 * @param planeTyp  ����������
	 * @param time ���/����ʱ��
	 * @throws Exception
	 * @return FlyConflictVO
	 * */
	public FlyConflictVO getWindspeedConflictInfo(String icao,String planeType,String time) throws Exception {
		Map<String,Integer> visibilityMap = this.getWindspeedAndVisibility(planeType);
		int maxWindSpeed = visibilityMap.get("windSpeed");
		StringBuffer sql = new StringBuffer("");
		sql.append(" SELECT t1.forecastid,to_char(t1.begin_time,'yyyy-MM-dd') begin_time,to_char(t1.end_time,'yyyy-MM-dd') end_time,t2.speed "); 
		sql.append(" FROM tb_weather t1,tb_weather_wind t2 WHERE  t1.forecastid = t2.forecastid ");
		sql.append(" AND to_date('").append(time).append("','yyyy-MM-dd HH24:mi') between t1.begin_time and t1.end_time AND t1.state = '1' ");
		sql.append(" AND to_number(t2.speed) > ").append(maxWindSpeed).append(" AND t1.scope = '").append(icao).append("'");
		String[] column = new String[]{"forecastid","begin_time","end_time","speed"};
		List<String[]> strs = dao.getObjects(sql.toString(), column);
		if(strs != null && strs.size() > 0){
			String[] str = strs.get(0);
			FlyConflictVO flyConflict = new FlyConflictVO();
			flyConflict.setId(Long.valueOf(str[0]));
			flyConflict.setDescription(str[3]);
			flyConflict.setStartTime(str[1]);
			flyConflict.setEndTime(str[2]);
			flyConflict.setFrom("weather-speed");
			flyConflict.setType("��糬��");
			return flyConflict;
		}
		return null;
	}

	//�õ����г�ͻ��Ϣ[�����رգ����ٹ��ߣ��ܼ��ȹ���]
	public BeanValue getFlyConflictInfo(RequestMap map) throws Exception {
		String depICAO = map.getString("depICAO");//��ɻ���ICAO��
		String desICAO = map.getString("desICAO");//Ŀ�Ļ���ICAO��
		String depTime = map.getString("depTime");//���ʱ��
		String desTime = map.getString("desTime");//����ʱ��
		String planeType = map.getString("planeType");//����������
		String objId = map.getString("adep");//��ɻ���Id
		
		List<FlyConflictVO> flyConflictList = new ArrayList<FlyConflictVO>();
		//step 1 �����Ƿ�رճ�ͻ����
		FlyConflictVO depClosedConflict = getADClosedInfo(depICAO, depTime);//��ɻ����Ƿ�ر�
		FlyConflictVO desClosedConflict = getADClosedInfo(desICAO, desTime);//Ŀ�Ļ����Ƿ�ر�
		if(depClosedConflict != null){
			depClosedConflict.setSrc("depICAO");
			depClosedConflict.setDescription("��ɻ����ر�");
			flyConflictList.add(depClosedConflict);
		}
		if(desClosedConflict != null){
			desClosedConflict.setSrc("desICAO");
			desClosedConflict.setDescription("Ŀ�Ļ����ر�");
			flyConflictList.add(desClosedConflict);
		}
		
		//step2 �ܼ��ȳ�ͻ����
		FlyConflictVO depVisibilityConflict = getVisibilityConflictInfo(depICAO, planeType, depTime);
		FlyConflictVO desVisibilityConflict = getVisibilityConflictInfo(desICAO, planeType, desTime);
		if(depVisibilityConflict != null){
			depVisibilityConflict.setSrc("depICAO");
			depVisibilityConflict.setDescription("��ɻ����ܼ��ȵ���" + depVisibilityConflict.getDescription() + "m");
			flyConflictList.add(depVisibilityConflict);
		}
		if(desVisibilityConflict != null){
			desVisibilityConflict.setSrc("desICAO");
			desVisibilityConflict.setDescription("Ŀ�Ļ����ܼ��ȵ���" + desVisibilityConflict.getDescription() + "m");
			flyConflictList.add(desVisibilityConflict);
		}
		
		//setp3 ���ٳ�ͻ����
		FlyConflictVO depWindspeedConflict = getWindspeedConflictInfo(depICAO, planeType, depTime);
		FlyConflictVO desWindspeedConflict = getWindspeedConflictInfo(desICAO, planeType, desTime);
		if(depWindspeedConflict != null){
			depWindspeedConflict.setSrc("depICAO");
			depWindspeedConflict.setDescription("��ɻ��������ٴ���" + depWindspeedConflict.getDescription() + "m/s");
			flyConflictList.add(depWindspeedConflict);
		}
		if(desWindspeedConflict != null){
			desWindspeedConflict.setSrc("desICAO");
			desWindspeedConflict.setDescription("Ŀ�Ļ��������ٴ���" + desWindspeedConflict.getDescription() + "m/s");
			flyConflictList.add(desWindspeedConflict);
		}
		
		//step4 ������ɻ�����ͬһʱ���Ƿ��б�ķ��мƻ�
		FlyConflictVO adepTimeConflict = getAdepTimeConflict(objId, depTime);
		if(adepTimeConflict != null){
			adepTimeConflict.setSrc("depICAO");
			adepTimeConflict.setDescription("��ɻ����ڸ�ʱ���Ѿ��б�ķ��мƻ�");
			flyConflictList.add(adepTimeConflict);
		}
		
		BeanValue value = new BeanValue();
		value.addParamMap("flyConflictList", flyConflictList);
		return value;
	}
	
	//���ݷ��������Ͳ�ѯ����ķ��ٺ��ܼ���
	private Map<String,Integer> getWindspeedAndVisibility(String planeType)throws Exception{
		String[] column = new String[]{"visibility","hwind_speed"};
		String sql = " SELECT visibility,hwind_speed FROM tb_type_weath_cond WHERE plane_type = '" + planeType + "'";
		List<String[]> rs = dao.getObjects(sql, column);
		if(rs == null){
			sql = " SELECT visibility,hwind_speed FROM tb_type_weath_cond WHERE plane_type = 'ALL' ";
			rs = dao.getObjects(sql, column);
		}
		
		Map<String,Integer> resultMap = new HashMap<String,Integer>();
		String windSpeed = "";
		String visibility = "";
		String[] strs = null;
		strs = rs.get(0);
		visibility = strs[0];
		windSpeed = strs[1];
		resultMap.put("windSpeed", Integer.parseInt(windSpeed));
		resultMap.put("visibility", Integer.parseInt(visibility));
		return resultMap;
	}
	
}
