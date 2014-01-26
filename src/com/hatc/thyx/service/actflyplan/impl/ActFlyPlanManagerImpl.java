package com.hatc.thyx.service.actflyplan.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.hatc.base.common.BaseException;
import com.hatc.base.common.BeanValue;
import com.hatc.base.common.RequestMap;
import com.hatc.base.common.RollPage;
import com.hatc.base.utils.ConvertLang;
import com.hatc.common.businessdata.Code;
import com.hatc.common.contants.ProjectItemCode;
import com.hatc.common.service.impl.ProjectManagerImpl;
import com.hatc.hibernate.pojo.TFpl;
import com.hatc.hibernate.pojo.TFplActivity;
import com.hatc.hibernate.pojo.TFplCycle;
import com.hatc.hibernate.pojo.TFplRs;
import com.hatc.hibernate.pojo.TFplRsP;
import com.hatc.thyx.service.actflyplan.ActFlyPlanManager;

/**
 * ���мƻ�ʵʩ����
 * @author zhulin 2013-07-01
 *
 */
public class ActFlyPlanManagerImpl extends ProjectManagerImpl implements ActFlyPlanManager{

	/**
	 * ���мƻ�ʵʩȷ�ϳ�ʼ����ѯ
	 * @param aRequestMap
	 * @return BeanValue
	 */
	@SuppressWarnings("unchecked")
	public BeanValue queryAffirmBeforeActPlanList(RequestMap aRequestMap) throws Exception {
		BeanValue aBeanValue = new BeanValue();
		List<String[]> planList = new ArrayList<String[]>();
		//��ѯ��־
		// 1 ��ʾʵʩǰȷ��
		// 2 ��ʾʵʩ�е���
		// 3 ��ʾʵʩ��ȷ��
		String selectFlag =  aRequestMap.getString("selectFlag");
		selectFlag = selectFlag == null ? "1" : selectFlag;
		
		//ȡ�üƻ�����
		String planName = aRequestMap.getString("planName");
		planName = planName == null ? "" : planName;
		
		//ȡ�üƻ����
		String planCode = aRequestMap.getString("planCode");
		planCode = planCode == null ? "" : planCode;
		
		//ȡ�üƻ���ʼʱ��
		String flyStartDate = aRequestMap.getString("startDate");
		flyStartDate = flyStartDate == null ? "" : flyStartDate;
		
		//ȡ�üƻ�����ʱ��
		String flyEndDate = aRequestMap.getString("endDate");
		flyEndDate = flyEndDate == null ? "" : flyEndDate;
		
		//ȡ�ú������ͺ�
		String airCraftType = aRequestMap.getString("airCraftType");
		airCraftType = airCraftType == null ? "" : airCraftType;
		
		//ȡ����ɻ���
		String adep = aRequestMap.getString("adepName");
		adep = adep == null ? "" : adep;
		
		//ȡ��Ŀ�Ļ���
		String ades = aRequestMap.getString("adesName");
		ades = ades == null ? "" : ades;
		
		/* ҳ���ʼ�� */
		// ��ʼ����ҳ����
		RollPage rollPage = new RollPage();
		this.initRollPage(aRequestMap, rollPage);
		this.getParamMapForList(aRequestMap, aBeanValue);
//		dao.openConnection();
		StringBuffer sb = new StringBuffer();
		sb.append("select tfp.planid");
		sb.append("       ,tc6.name || '@_@' || tc6.code_id AS COMMANDER");
		sb.append("       ,tc7.name || '@_@' || tc7.code_id AS EQUIP");
		sb.append("       ,nvl(tpa.acid,tfp.ACID) as acid");
		sb.append("       ,'' as planRs");
		sb.append("       ,tpa.fa_adjust as fa_adjust");
		sb.append("       ,nvl(tpa.Fa_Ialtn,0) as faIaltn");
		sb.append("       ,AD3.AD_NAME  || '@_@' ||  AD3.OBJECTID  as Altn1");
		sb.append("       ,tfp.f_plan_name");
		sb.append("       ,tfp.F_PLAN_CODE");
		sb.append("       ,tc5.name as RULE");
		sb.append("       ,to_char(tfp.DATES,'yyyy-MM-dd') || ' ' || tfp.SETD as flyDate");
		sb.append("       ,tfp.eet as eet");
		sb.append("       ,AD1.AD_NAME || '(' || AD1.ICAO_CODE || ')' as adep");
		sb.append("       ,AD2.AD_NAME || '(' || AD2.ICAO_CODE || ')' as ades");
		sb.append("       ,tc2.name as acft");
		sb.append("       ,tc1.name as planstate");
		sb.append("  from T_FPL tfp");
		sb.append("  left join (select t.object_id, max(t.approve_ts),t.approve_result");
		sb.append("  from tb_approve_flow_rec t");
		sb.append("  where t.approve_state = '32'");
		sb.append("  and t.approve_ts is not null");
		sb.append("  group by t.object_id,t.approve_result) app");
		sb.append("  on app.object_id = tfp.planid");
		sb.append("  left join t_fpl_activity tpa");
		sb.append("  on tpa.planid = tfp.planid");
		sb.append(" left join T_AD AD1");
		sb.append(" ON TFP.ADEP = AD1.OBJECTID");
		sb.append(" LEFT JOIN T_AD AD2");
		sb.append(" ON TFP.ADES = AD2.OBJECTID");
		sb.append(" LEFT JOIN T_AD AD3 ");
		sb.append(" ON TFP.Altn1 = AD3.OBJECTID");
		sb.append("  left join tb_code tc1 ");
		sb.append("  on tc1.code_id = tfp.planstate");
		sb.append("  and tc1.class_id = 'TH_FPL_STATUS'");
		sb.append("  left join tb_code tc2 ");
		sb.append("  on tc2.code_id = tfp.acft");
		sb.append("  and tc2.class_id = 'TH_AIR_CRAFT_TYPE'");
		sb.append("  left join tb_code tc5 ");
		sb.append("  on tc5.code_id = tfp.RULE");
		sb.append("  and tc5.class_id = 'TH_FLY_RULE'");
		sb.append("  left join tb_code tc6 ");
		sb.append("  on tc6.code_id = nvl(tpa.fa_pilot,TFP.COMMANDER)");
		sb.append("  and tc6.class_id = 'TH_PILOT'");
		sb.append("  left join tb_code tc7 ");
		sb.append("  on tc7.code_id = tfp.EQUIP");
		sb.append("  and tc7.class_id = 'TH_FLY_EQUIPMENT'");
		sb.append("  where '1' = '1'");
		sb.append("  and  tfp.PCLASS = 'GA'");
		sb.append("  and (app.approve_result = '1' or app.approve_result is null) ");
		//
		if("1".equals(selectFlag)){
			sb.append("  and tfp.planstate in ('31','32')");
		}else if("2".equals(selectFlag)){
			sb.append("  and tfp.planstate in ('31','32','72')");
		}else if("3".equals(selectFlag)){
			sb.append("  and tfp.planstate in ('31','32','72')");
		}
		// �������Ʋ�ѯ����
		if(!"".equals(planName)){
			sb.append(" and tfp.f_plan_name like '%" + planName + "%'");
		}
		// ���Ӽƻ���Ų�ѯ����
		if(!"".equals(planCode)){
			sb.append(" and tfp.F_PLAN_CODE like '%" + planCode + "%'");
		}
		//�������ڲ�ѯ
		if(!"".equals(flyStartDate) && !"".equals(flyEndDate)){
			sb.append("  and tfp.DATES between to_date('" + flyStartDate + "','yyyy-MM-dd') and to_date('" + flyEndDate + "','yyyy-MM-dd')");
		}else{
			if(!"".equals(flyStartDate)){
				sb.append(" and tfp.DATES >= to_date('" + flyStartDate + "','yyyy-MM-dd')");
			}
			if(!"".equals(flyEndDate)){
				sb.append(" and tfp.DATES <= to_date('" + flyEndDate + "','yyyy-MM-dd')");
			}
		}
		//���Ӻ������ͺŲ�ѯ
		if(!"".equals(airCraftType)){
			sb.append(" and tfp.acft = '" + airCraftType + "'");
		}
		//������ɻ�����ѯ
		if(!"".equals(adep)){
			sb.append(" and AD1.AD_NAME like '%" + adep + "%'");
		}
		//����Ŀ�Ļ�����ѯ
		if(!"".equals(ades)){
			sb.append(" and AD1.AD_NAME like '%" + ades + "%'");
		}
		StringBuffer rs = new StringBuffer();
//		System.out.println(sb.toString());
		//���мƻ����� ���й��� �ƻ����ʱ�� ����ʱ�������ӣ� ��ɻ��� Ŀ�Ļ��� ���������� ״̬ 
		planList = (List<String[]>)dao.getObjects(rollPage,sb.toString(),new String[]{"planid","COMMANDER","EQUIP","ACID","planRs","fa_adjust","faIaltn","Altn1","f_plan_name","F_PLAN_CODE","RULE","flyDate","eet","adep","ades","acft","planstate"});
		List<String[]> rsList = new ArrayList<String[]>();
		if(planList != null && planList.size()!=0){
			for (int j = 0; j < planList.size(); j++) {
				sb = new StringBuffer();
//				sb.append("select tfr.RS_NAME from T_FPL_RS tfr");
//				sb.append(" where tfr.planid = '" + planList.get(j)[0] +"'");
//				rsList = (List<String[]>)dao.getObjects(sb.toString(), new String[]{"RS_NAME"});
				
				sb.append("select tf.rs_id,tf.rs_p_num,tf.rs_p_pos,tf.rs_p_name,tf.rs_p_code from  t_fpl_rs_p tf left join  t_fpl_rs rs on tf.rs_id = rs.rs_id " +
						"where rs.planid = '" + planList.get(j)[0] +"' order by tf.rs_id,tf.rs_p_num");
				rsList = (List<String[]>)dao.getObjects(sb.toString(), new String[]{"rs_id","rs_p_num","rs_p_pos","rs_p_name","rs_p_code"});
//				System.out.println(sb.toString());
				rs = new StringBuffer();
				//ȡ�����к���
				if(rsList!=null && rsList.size()>0 ){
					for(int k = 0;k < rsList.size();k++){
						if(k == 0 || rsList.get(k)[0].equals(rsList.get(k-1)[0])){
							rs.append(rsList.get(k)[1]).append(".").append(rsList.get(k)[4]).append(":").append(rsList.get(k)[3]);
						} else{
							rs.append("@_@");
							rs.append(rsList.get(k)[1]).append(".").append(rsList.get(k)[4]).append(":").append(rsList.get(k)[3]);
						}
					}
				}
				planList.get(j)[4] = rs.toString();
			}
		}
		
		//ȡ����Ա�б�
		List<String[]> politList = this.getCode("TH_PILOT");
		
		//ȡ�ú��������
		List<String[]> acidList = this.getCode("TH_AIR_CRAFT_SN");
		
		aBeanValue.addParamMap("planName", planName);
		aBeanValue.addParamMap("planCode", planCode);
		aBeanValue.addParamMap("flyStartDate", flyStartDate);
		aBeanValue.addParamMap("flyEndDate", flyEndDate);
		aBeanValue.addParamMap("airCraftType", airCraftType);
		aBeanValue.addParamMap("planList", planList);
		aBeanValue.addParamMap("selectFlag", selectFlag);
		aBeanValue.addParamMap("adep", adep);
		aBeanValue.addParamMap("ades", ades);
		aBeanValue.setForward("affirmBeforeAct");
		aBeanValue.addParamMap("rollPage", rollPage);
		aBeanValue.addParamMap("politList", politList);
		aBeanValue.addParamMap("acidList", acidList);
		
		// ȡ�����ڷ�Χ
		List<Code> autoPeriodList = ProjectItemCode.getCodeList("AUTO_PERIOD");
		aBeanValue.addRequestMap("autoPeriodList", autoPeriodList);
		
		//ȡ�ú������ͺ�
//		List<Code> airCraftTypeList = ProjectItemCode.getCodeList("TH_AIR_CRAFT_TYPE");
//		aBeanValue.addRequestMap("airCraftTypeList", airCraftTypeList);
		
		return aBeanValue;
	}
	
	
	/**
	 * ����class_Idȡ�ô���ֵ
	 * @param classId 
	 * @return classId��Ӧ�Ĵ���ֵ
	 */
	public List<String[]> getCode(String classId){
		List<String[]> codeList = new ArrayList<String[]>();
		String sql = "SELECT T.CODE_ID,T.NAME FROM TB_CODE T WHERE T.CLASS_ID = '" + classId + "' ";
		try {
			codeList = dao.getObjects(sql,new String[]{"CODE_ID","NAME"});
		} catch (BaseException e) {
			
		}
		
		return codeList;
	} 
	
	/**
	 * ʵʩȷ��
	 * @param aRequestMap
	 * @return  BeanValue
	 */
	@SuppressWarnings("unchecked")
	public BeanValue saveAffirmBeforeAct(RequestMap aRequestMap) throws Exception{
		BeanValue aBeanValue = new BeanValue();
//		dao.openConnection();
		//��ѯ��־
		// 1 ��ʾʵʩǰȷ��
		// 2 ��ʾʵʩ�е���
		// 3 ��ʾʵʩ��ȷ��
		String selectFlag =  aRequestMap.getString("selectFlag");
		selectFlag = selectFlag == null ? "1" : selectFlag;
		
		//ʵʩǰȷ�ϼƻ�id
		String flyplanId1 = aRequestMap.getString("flyplanId1");
		flyplanId1 = flyplanId1 == null ? "" : flyplanId1;
		flyplanId1 = flyplanId1.replace(",", "");
		
		//ʵʩ�е����ƻ�id
		String flyplanId2 = aRequestMap.getString("flyplanId2");
		flyplanId2 = flyplanId2 == null ? "" : flyplanId2;
		flyplanId2 = flyplanId2.replace(",", "");
		
		//ʵʩ��ȷ�ϼƻ�id
		String flyplanId3 = aRequestMap.getString("flyplanId3");
		flyplanId3 = flyplanId3 == null ? "" : flyplanId3;
		flyplanId3 = flyplanId3.replace(",", "");
		
		//��Աid
		String tpilot = aRequestMap.getString("tpilot");
		tpilot = tpilot == null ? "" : tpilot;
		
		//���������
		String acid = aRequestMap.getString("floatAcid");
		acid = acid == null ? "" : acid;
		
		//��������
		String isMainRs = aRequestMap.getString("isMainRs");
		isMainRs = isMainRs == null ? "" : isMainRs;
		
		//ʵʩǰȷ�ϱ�ע
		String remark = aRequestMap.getString("remark");
		remark = remark == null ? "" : remark;
		
		//������ע
		String adjust = aRequestMap.getString("submitAdjust");
		adjust = adjust == null ? "" : adjust;
		
		//ʵ���������
		String pStartDate = aRequestMap.getString("pStartDate");
		pStartDate = pStartDate == null ? "" : pStartDate;
		
		//ʵ�ʽ�������
		String pEndDate = aRequestMap.getString("pEndDate");
		pEndDate = pEndDate == null ? "" : pEndDate;
        
		//�Ƿ񱸽�������
		String isMainP = aRequestMap.getString("isMainP");
		isMainP = isMainP == null ? "" : isMainP;
		
		//������
		String altn = aRequestMap.getString("subAltn1");
		altn = altn == null ? "" : altn;
		
		//����ԭ��
		String reason = aRequestMap.getString("reason");
		reason = reason == null ? "" : reason;
		
		//ʵ�����Сʱ
		String startHour = aRequestMap.getString("startHour");
		startHour = startHour == null ? "" : startHour;
		
		//ʵ����ɷ���
		String startMinute = aRequestMap.getString("startMinute");
		startMinute = startMinute == null ? "" : startMinute;
		
		//ʵ�ʽ���Сʱ
		String endHour = aRequestMap.getString("endHour");
		endHour = endHour == null ? "" : endHour;
		
		//ʵ�ʽ������
		String endMinute = aRequestMap.getString("endMinute");
		endMinute = endMinute == null ? "" : endMinute;
		
		//ʵʩ��ȷ�ϱ�ע
		String desc = aRequestMap.getString("desc");
		desc = desc == null ? "" : desc;
		
		List<String[]> s = new ArrayList<String[]>();
		TFpl tp = new TFpl();
		TFplActivity tpa = new TFplActivity();
		TFplActivity tpaTo = new TFplActivity();
		List<TFplActivity> tpaList = new ArrayList<TFplActivity>();
		if(!"".equals(flyplanId1) || !"".equals(flyplanId2) || !"".equals(flyplanId3)){
			
			if("1".equals(selectFlag)){
				//ȡ�üƻ���Ӧ����Ϣ
				tp = (TFpl)dao.getObject(TFpl.class, Long.valueOf(flyplanId1));
				//���¶�Ӧ��״̬
				tp.setPlanstate("72");
				dao.updateObject(tp);
				
				//����Ӧ��ʵʩ��Ϣ�Ƿ����
				String hql = "from TFplActivity tfa where tfa.TFpl.planid = '" + flyplanId1 + "'";
				tpaList = dao.getObjects(hql);
				//���ľ�в鵽��¼��ʾû��ʵʩǰ��ȷ�����¼�һ����¼
				if(tpaList == null || tpaList.size()==0){
					//����
					s = dao.getObjects("select seq_t_fpl_activity.nextval from dual ", new String[]{"nextval"});
					tpa.setFaId(Long.valueOf(s.get(0)[0]));
				}else{
					tpa = tpaList.get(0);
				}
				
				tpa.setTFpl(tp);
				tpa.setFaPilot(tpilot);
				tpa.setAcid(Long.valueOf(acid));
				tpa.setFaRemark(remark);
				tpa.setFaRsId(Long.valueOf(isMainRs));
				dao.saveOrUpdateObject(tpa);
			}else if("2".equals(selectFlag)){
				//����Ӧ��ʵʩ��Ϣ�Ƿ����
				String hql = "from TFplActivity tfa where tfa.TFpl.planid = '" + flyplanId2 + "'";
				tpaList = dao.getObjects(hql);
				//���ľ�в鵽��¼��ʾû��ʵʩǰ��ȷ�����¼�һ����¼
				if(tpaList == null || tpaList.size()==0){
					tp = (TFpl)dao.getObject(TFpl.class, Long.valueOf(flyplanId2));
					tpaTo = new TFplActivity();
					tpaTo.setTFpl(tp);
					//����
					s = dao.getObjects("select seq_t_fpl_activity.nextval from dual ", new String[]{"nextval"});
					tpaTo.setFaId(Long.valueOf(s.get(0)[0]));
					tpaTo.setFaAdjust(adjust);
					dao.saveObject(tpaTo);
				}else{
					tpaList.get(0).setFaAdjust(adjust);
					dao.updateObject(tpaList.get(0));
				}
			}else if("3".equals(selectFlag)){
				Date startDateTime = new Date();
				Date endDateTime = new Date();
				//����ʵ�����ʱ���ʵ�ʽ���ʱ��
				String allDdateTime = pStartDate + " " + startHour + ":" + startMinute;
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
				startDateTime = sdf.parse(allDdateTime);
				
				allDdateTime = pEndDate + " " + endHour + ":" + endMinute;
				endDateTime = sdf.parse(allDdateTime);
	            String strDiff = getDiffTime(startDateTime,endDateTime);
	            
				//����Ӧ��ʵʩ��Ϣ�Ƿ����
				String hql = "from TFplActivity tfa where tfa.TFpl.planid = '" + flyplanId3 + "'";
				tpaList = dao.getObjects(hql);
				tp = (TFpl)dao.getObject(TFpl.class, Long.valueOf(flyplanId3));
				//���ľ�в鵽��¼��ʾû��ʵʩǰ��ȷ�����¼�һ����¼
				if(tpaList == null || tpaList.size()==0){
					//����
					s = dao.getObjects("select seq_t_fpl_activity.nextval from dual ", new String[]{"nextval"});
					
					tpaTo = new TFplActivity();
					tpaTo.setTFpl(tp);
					tpaTo.setFaId(Long.valueOf(s.get(0)[0]));
					tpaTo.setAcid(Long.valueOf(acid));
					tpaTo.setFaPilot(tpilot);
					tpaTo.setFaRsId(Long.valueOf(0));
					tpaTo.setAtd(startDateTime);
					tpaTo.setAta(endDateTime);
					tpaTo.setEet(strDiff);
					tpaTo.setFaIaltn(Long.valueOf(isMainP));
					tpaTo.setFaActad(altn);
					tpaTo.setFaReason(reason);
					tpaTo.setFaDesc(desc);
					dao.saveObject(tpaTo);
				}else{
					tpaList.get(0).setAtd(startDateTime);
					tpaList.get(0).setFaPilot(tpilot);
					tpaList.get(0).setFaRsId(Long.valueOf(0));
					tpaList.get(0).setAcid(Long.valueOf(acid));
					tpaList.get(0).setAta(endDateTime);
					tpaList.get(0).setEet(strDiff);
					tpaList.get(0).setFaDesc(desc);
					tpaList.get(0).setFaIaltn(Long.valueOf(isMainP));
					tpaList.get(0).setFaActad(altn);
					tpaList.get(0).setFaReason(reason);
					dao.updateObject(tpaList.get(0));
				}
				//���¶�Ӧ��״̬
				tp.setPlanstate("73");
				dao.updateObject(tp);
			}
	}
	aBeanValue.addParamMap("selectFlag", selectFlag);
	aBeanValue.setForward("affirmBeforeAct");
	return aBeanValue;
	}
	
	/**
	 * ȷ������ʱ���ʱ���
	 * @param startDateTime ��ʼʱ��
	 * @param endDateTime ����ʱ��
	 * @return ʱ���
	 */
	public String getDiffTime(Date startDateTime, Date endDateTime) {
		
		String strDiff = "";
		Long diff;
		//Long second;
		Long minute;
		Long hour;
		Long day;
		diff = endDateTime.getTime() - startDateTime.getTime();
		//second = diff / 1000;
		minute = diff / (1000 * 60);
		hour = diff / (1000 * 60 * 60);
		day = diff / (1000 * 60 * 60 * 24);
//		System.out.println("���룺" + diff);
//		System.out.println("�룺" + second);
//		System.out.println("�֣�" + minute);
//		System.out.println("Сʱ��" + hour);
//		System.out.println("�죺" + day);
		hour = (diff - (day * 24 * 60 * 60 * 1000)) / (1000 * 60 * 60);
		minute = (diff - (day * 24 * 60 * 60 * 1000) - (hour * 60 * 60 * 1000))
				/ (1000 * 60);
		//second = (diff - (day * 24 * 60 * 60 * 1000) - (hour * 60 * 60 * 1000) - (minute * 60 * 1000)) / (1000);
//		System.out.println("ȥ�����ں�ʣ�µ�Сʱ" + hour + "Сʱ");
//		System.out.println("ȥ�����ڡ�Сʱ��ʣ�µķ���" + minute + "����");
//		System.out.println("ȥ�����ڡ�Сʱ�����Ӻ�ʣ�µ���" + second + "��");
//		System.out.println("�����ߵ�ʱ���Ϊ��" + day + "��" + hour + "Сʱ" + minute
//				+ "����" + second + "��");
		strDiff = hour + ":" + minute;
		return strDiff;
	}
	
	/**
	 * ���Է���
	 * @param args
	 */
	public static void main(String[] args){
		String strStartDateTime = "2013-07-13 11:30:15";
		String strEndDateTime = "2013-07-14 12:20:6";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date startDateTime;
		Date endDateTime;
		Long diff;
		Long second;
		Long minute;
		Long hour;
		Long day;
		try {
			startDateTime = sdf.parse(strStartDateTime);
			endDateTime = sdf.parse(strEndDateTime);
			diff = endDateTime.getTime() - startDateTime.getTime();
			second = diff/1000;
			minute = diff/(1000*60);
			hour = diff/(1000*60*60);
			day = diff/(1000*60*60*24);
			System.out.println("���룺" + diff);
			System.out.println("�룺" + second);
			System.out.println("�֣�" + minute);
			System.out.println("Сʱ��" + hour);
			System.out.println("�죺" + day);
			hour = (diff - (day * 24 * 60 * 60 * 1000))/(1000*60*60);
			minute = (diff - (day * 24 * 60 * 60 * 1000) - (hour * 60 * 60 * 1000))/(1000*60);
			second = (diff - (day * 24 * 60 * 60 * 1000) - (hour * 60 * 60 * 1000) - (minute * 60 * 1000))/(1000);
			System.out.println("ȥ�����ں�ʣ�µ�Сʱ" + hour + "Сʱ");
			System.out.println("ȥ�����ڡ�Сʱ��ʣ�µķ���" + minute + "����");
			System.out.println("ȥ�����ڡ�Сʱ�����Ӻ�ʣ�µ���" + second + "��");
			System.out.println("�����ߵ�ʱ���Ϊ��" + day + "��" + hour + "Сʱ" + minute + "����" + second + "��");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * ��ʼ����ҳ����
	 * @param map
	 * @param rollPage
	 */
	private void initRollPage(RequestMap map, RollPage rollPage) {

		/* * * * * * * ��ҳ���� * * * * * * */
		// ��ȡҳ��
		String page = map.getString("page");
		// ��ȡ��ҳ��־
		String pageBool = map.getString("pageBool");

		// ��ҳ��ת����Ϊ����
		if (page == null || page.equals("")) {
			rollPage.setPageCur(0);
		} else {
			rollPage.setPageCur(ConvertLang.convertint(page));
		}
		if (pageBool == null || pageBool.equals("")) {
			rollPage.setPageBool("0");
		} else {
			rollPage.setPageBool(pageBool);
		}
		// ����ÿһҳ��ʾ�ļ�¼��
		rollPage.setPagePer(16);
	}
	
	 /**
     * ���� List ��ѯ���˲���
     */
    @SuppressWarnings("unused")
	private void getParamMapForList(RequestMap requestMap, BeanValue aBeanValue) throws Exception {
		
		// ��ʾ���ͣ����selectFlagΪ�յ�����ʽ��ʾ����Ϊ�գ�ҳ����ת
    	String selectFlag =  requestMap.getString("selectFlag");
		selectFlag = selectFlag == null ? "1" : selectFlag;
		
		//ȡ�üƻ�����
		String planName = requestMap.getString("planName");
		planName = planName == null ? "" : planName;
		
		//ȡ�üƻ����
		String planCode = requestMap.getString("planCode");
		planCode = planCode == null ? "" : planCode;
		
		//ȡ�üƻ���ʼʱ��
		String flyStartDate = requestMap.getString("startDate");
		flyStartDate = flyStartDate == null ? "" : flyStartDate;
		
		//ȡ�üƻ�����ʱ��
		String flyEndDate = requestMap.getString("endDate");
		flyEndDate = flyEndDate == null ? "" : flyEndDate;
		
		//ȡ�ú������ͺ�
		String airCraftType = requestMap.getString("airCraftType");
		airCraftType = airCraftType == null ? "" : airCraftType;
		
		//ȡ����ɻ���
		String adep = requestMap.getString("adepName");
		adep = adep == null ? "" : adep;
		
		//ȡ��Ŀ�Ļ���
		String ades = requestMap.getString("adesName");
		ades = ades == null ? "" : ades;
    	aBeanValue.addParamMap("planName", planName);
		aBeanValue.addParamMap("planCode", planCode);
		aBeanValue.addParamMap("flyStartDate", flyStartDate);
		aBeanValue.addParamMap("flyEndDate", flyEndDate);
		aBeanValue.addParamMap("airCraftType", airCraftType);
		aBeanValue.addParamMap("selectFlag", selectFlag);
		aBeanValue.addParamMap("adep", adep);
		aBeanValue.addParamMap("ades", ades);
		aBeanValue.setForward("affirmBeforeAct");
		
		aBeanValue.addRequestMap("selectFlag", selectFlag);
		aBeanValue.addParamMap("selectFlag",selectFlag);
		
        /* ��ҳ���� */
        // ��ǰҳ��, Ĭ��Ϊ 0
        String page = requestMap.getString("page");
        page = (page == null || page.equals("")) ? "0" : page;
        // ��ѯ��ʶ, 0 ��������ѯ, 1 ��ҳ��ѯ
        String pageBool = requestMap.getString("pageBool");
        // ��ҳ����
        RollPage aRollPage = new RollPage(ConvertLang.convertint(page), pageBool);
     // ����ÿһҳ��ʾ�ļ�¼��
		aRollPage.setPagePer(16);

        aBeanValue.addParamMap("page", page);
        aBeanValue.addParamMap("pageBool", pageBool);
        aBeanValue.addParamMap("rollPage", aRollPage);
    }
    
    /**
	 * �������ݣ�ͨ���û���ʼ�����мƻ���Ϣ
	 * @param map
	 * @return value
	 * @throws Exception
	 */
	public BeanValue initFlyPlanBaseInfo(RequestMap requestMap)
			throws Exception {

		
//		// ��֤�û�Ȩ��
//		ReqIdentity identity = getReqIdentity(requestMap);
//		identity.setFunctionId("BM10101");//���мƻ��½�
//		SecurityStub sstub = new SecurityStub();
//		sstub.checkPermission(identity);
		BeanValue beanValue = new BeanValue();
//		
//		// ���ò�ѯ����
		this.getParamMapForList(requestMap, beanValue);
//
		String planid = requestMap.getString("planid");
		planid = planid == null ? "" : planid;
		
		String operFlag = requestMap.getString("operFlag");
		beanValue.addRequestMap("operFlag", operFlag);
		
		TFpl tFpl = new TFpl();
//		 ������Ϊ��ִ�����µĲ���
		if (StringUtils.isNotEmpty(planid)) {
			
//			// ��ѯ����������������Ϣ������ʵʩ������������¼��
			tFpl = (TFpl) dao.getObject(TFpl.class, new Long(planid.trim()));
			TFplCycle tFplCycle=null;
			String hql="from TFplCycle cycle where cycle.planid="+planid.trim();
			List list=dao.getObjects(hql);
			if(list !=null && list.size() >0){
				tFplCycle=(TFplCycle) list.get(0);
			}
			
			beanValue.addRequestMap("tFplCycle", tFplCycle);
			
			// ��ѯ��صĺ���
			TFplRs trsMain=null;
			TFplRs trsBak=null;
			List<TFplRs> list1=dao.getObjects("from TFplRs trs where trs.planid = "+tFpl.getPlanid()+" and trs.rsType='1'");
			if(list1 != null && list1.size() >0){
				trsMain=list1.get(0);
				beanValue.addRequestMap("trsMain", trsMain);
				
				List<TFplRsP> list3=dao.getObjects("from TFplRsP trsp where trsp.rsId="+trsMain.getRsId()+" order by trsp.rsPNum");
				if(list3 != null && list3.size() >0){
					beanValue.addRequestMap("trsMainTRsPList", list3);
				}
			}
			List<TFplRs> list2=dao.getObjects("from TFplRs trs where trs.planid="+tFpl.getPlanid()+" and trs.rsType='0'");
			if(list2 != null && list2.size() >0){
				trsBak=list2.get(0);
				beanValue.addRequestMap("trsBak", trsBak);
				List<TFplRsP> list4=dao.getObjects("from TFplRsP trsp where trsp.rsId="+trsBak.getRsId()+" order by trsp.rsPNum");
				if(list4 != null && list4.size() >0){
					beanValue.addRequestMap("trsBakTRsPList", list4);
				}
			}
			
			
		}else{
			tFpl.setPclass("GA");
			tFpl.setRule("002");
			tFpl.setChgt("1000");
			tFpl.setCspd("200");
		}
		
		beanValue.addRequestMap("tFpl", tFpl);
		List<Code> airlineCompanyList = ProjectItemCode.getCodeList("TH_AIRLINE_COMPANY");
		beanValue.addRequestMap("airlineCompanyList", airlineCompanyList);
		List<Code> pilotList = ProjectItemCode.getCodeList("TH_PILOT");
		beanValue.addRequestMap("pilotList", pilotList);
		List<Code> flyPlanKindList = ProjectItemCode.getCodeList("TH_FLY_PLAN_KIND");
		beanValue.addRequestMap("flyPlanKindList", flyPlanKindList);
		List<Code> flyPlanTypeList = ProjectItemCode.getCodeList("TH_FLY_PLAN_TYPE");
		beanValue.addRequestMap("flyPlanTypeList", flyPlanTypeList);
		List<Code> flyRuleList = ProjectItemCode.getCodeList("TH_FLY_RULE");
		beanValue.addRequestMap("flyRuleList", flyRuleList);
		List<Code> airCraftTypeList = ProjectItemCode.getCodeList("TH_AIR_CRAFT_TYPE");
		beanValue.addRequestMap("airCraftTypeList", airCraftTypeList);
		List<Code> airCraftSnList = ProjectItemCode.getCodeList("TH_AIR_CRAFT_SN");
		beanValue.addRequestMap("airCraftSnList", airCraftSnList);
		List<Code> flyEquipmentList = ProjectItemCode.getCodeList("TH_FLY_EQUIPMENT");
		beanValue.addRequestMap("flyEquipmentList", flyEquipmentList);
		List<Code> flyTypeList = ProjectItemCode.getCodeList("TH_FLY_TYPE");
		beanValue.addRequestMap("flyTypeList", flyTypeList);
		List<Code> cycleType = ProjectItemCode.getCodeList("TH_FPL_CYCLE_TYPE");
		beanValue.addRequestMap("cycleType", cycleType);
		
		
		// ʵʩҳ��ĵ�������
		beanValue.setForward("flyPlanInfoDetail");
		
		return beanValue;
	
	}
}