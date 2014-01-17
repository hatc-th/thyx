package com.hatc.thyx.service.planaudit.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.hatc.base.common.BaseException;
import com.hatc.base.common.BeanValue;
import com.hatc.base.common.RequestMap;
import com.hatc.base.common.RollPage;
import com.hatc.base.hibernate.dao.Dao;
import com.hatc.base.utils.BeanUtil;
import com.hatc.base.utils.ConvertLang;
import com.hatc.common.businessdata.Code;
import com.hatc.common.businessdata.Site;
import com.hatc.common.contants.ProjectConstants;
import com.hatc.common.contants.ProjectItemCode;
import com.hatc.common.service.impl.ProjectManagerImpl;
import com.hatc.common.servicestub.ReqIdentity;
import com.hatc.hibernate.pojo.TFpl;
import com.hatc.hibernate.pojo.TFplActivity;
import com.hatc.hibernate.pojo.TFplCycle;
import com.hatc.hibernate.pojo.TFplRs;
import com.hatc.hibernate.pojo.TFplRsP;
import com.hatc.hibernate.pojo.TFsInterpret;
import com.hatc.hibernate.pojo.TbApproveFlowRec;
import com.hatc.hibernate.pojo.TbApproveFlowRecId;
import com.hatc.hibernate.vo.TFplVO;
import com.hatc.thyx.service.planaudit.PlanAuditManager;

public class PlanAuditManagerImpl extends ProjectManagerImpl implements PlanAuditManager {
	

	private Dao dao = null;
	
	private Dao dao1 = null;
	
	private Dao szkydao = null;
	
	public Dao getSzkydao() {
		return szkydao;
	}

	public void setSzkydao(Dao szkydao) {
		this.szkydao = szkydao;
	}
	/**
	 * ���мƻ���˹���ҳ���ѯ
	 */
	public BeanValue searchList(RequestMap map) throws Exception {
		
		BeanValue beanValue = new BeanValue();
		ReqIdentity identity = getReqIdentity(map);
	    /* ҳ���ʼ�� */
		// ��ʼ����ҳ����
		RollPage rollPage = new RollPage();
		
		initRollPage(map, rollPage);
		
		// �������ͣ����managerTypeΪflow��ת����˹����б�query���ѯ,historyΪ��ѯ������ʷ
		String managerType = map.getString("managerType");
		
		// ���Ȩ�޹���
		StringBuffer sql = new StringBuffer("select t.planid,t.f_plan_name,a3.name as commander,cd1.ad_name as adep,cd2.ad_name as ades," +
				"cd4.name as acft,cd3.name as pclass,t.setd as setd,to_char(t.setal,'yyyy-MM-dd') || case when t.seta is not null then ' ' ||t.seta end as seta,cd6.name as planstate,to_char(t.dates,'yyyy-MM-dd') as dates,t.planstate as planstateId, " +
//				"null as approve_state,null as approve_result,null as approve_state_name,null as approve_result_name,t.f_plan_code " +
				"c.approve_state as approve_state,c.approve_result as approve_result,c1.name as approve_state_name,c2.name as approve_result_name,t.f_plan_code  " +
				"from t_fpl t ");
		
		sql.append(" left join t_ad cd1 on cd1.objectid = t.adep");  //��ɻ���
		sql.append(" left join t_ad cd2 on cd2.objectid = t.ades");  //Ŀ�Ļ���		
		sql.append(" left join tb_code cd3 on cd3.class_id = 'TH_FLY_PLAN_KIND' and cd3.code_id = t.pclass");   // ��������ظ��ԣ�һ����
		sql.append(" left join tb_code cd4 on cd4.class_id = 'TH_AIR_CRAFT_TYPE' and cd4.code_id = t.acft");  //�������ͺ�
		
		sql.append(" left join tb_code cd5 on cd5.class_id = 'TH_FLY_RULE' and cd5.code_id = t.rule");  //���й���
		sql.append(" left join tb_code cd6 on cd6.class_id = 'TH_FPL_STATUS' and cd6.code_id = t.planstate");  //���мƻ�״̬
		sql.append(" left join tb_code a3 on a3.class_id='TH_PILOT' and a3.code_id = t.commander ");
		
		// ��ѯ���µ��������̽��
		sql.append(" left join (select a1.object_id,a1.approve_state,a1.approve_result,a1.approve_user_id from tb_approve_flow_rec a1 inner join (select t.object_id,max(t.approve_id) as max_approve_id from tb_approve_flow_rec t group by t.object_id) t1");
		sql.append(" on a1.approve_id = t1.max_approve_id and a1.object_id = t1.object_id) c on c.object_id = t.planid");
			
		sql.append(" left join tb_code c1 on c1.class_id = 'TH_APPROVE_STATUS' and c1.code_id = c.approve_state");
		sql.append(" left join tb_code c2 on c2.class_id='TH_APPROVE_STATE' and c2.code_id = c.approve_result");
		sql.append(" where t.archive_state='0' ");         // ��ѯû�й鵵��
		
		String flag = this.setDepartFlag(map);
		
		if(StringUtils.equals("flow", managerType)) {
			
			// FSS��Ա�ĳ���
			if(StringUtils.equals("0", flag)) {
				// ��ѯ������ģ����ҽ�����Ϊ�յ�
				sql.append(" and ((t.planstate = '12' and c.approve_user_id is null) ");               // ֻ��ѯ���мƻ�״̬�������롢�����еļƻ�
				// ������Ϊ�Լ��Ĳ��Ҵ���״̬Ϊ�ջ�21���������ϼ���������
				sql.append(" or (c.approve_user_id = '").append(identity.getUserId()).append("' and (c.approve_state is null or c.approve_state = '33' or c.approve_state = '21')))");
			} else {
				sql.append(" and t.planstate ='21' ");               // ֻ��ѯ���мƻ�״̬�������еļƻ�
				sql.append(" and c.approve_user_id = '").append(identity.getUserId()).append("' and c.approve_state = '21'");
			}
			
		} else if(StringUtils.equals("query", managerType)){
			sql.append(" and t.planstate != '11' ");              // ��ѯδ��������ļƻ�
			
			// ����״̬
			String planStatus = map.getString("planStatus");
			planStatus = planStatus == null ? "" : planStatus;
			
			if (StringUtils.isNotEmpty(planStatus)) {
				if(StringUtils.equals("40", planStatus)) {
					planStatus = planStatus + "','41','42";
					sql.append( " and t.planstate in ('" );
					sql.append(planStatus);
					sql.append("')");
				} else {
					sql.append( " and t.planstate = '" );
					sql.append(planStatus);
					sql.append("'");
				}
			}
		} else if(StringUtils.equals("history", managerType)) {
			sql.append(" and exists (select * from tb_approve_flow_rec c where t.planid = c.object_id and c.approve_user_id = '").append(identity.getUserId()).append("')");
			
			// ����״̬
			String planStatus = map.getString("planStatus");
			planStatus = planStatus == null ? "" : planStatus;
			
			if (StringUtils.isNotEmpty(planStatus)) {
				if(StringUtils.equals("40", planStatus)) {
					planStatus = planStatus + "','41','42";
					sql.append( " and t.planstate in ('" );
					sql.append(planStatus);
					sql.append("')");
				} else {
					sql.append( " and t.planstate = '" );
					sql.append(planStatus);
					sql.append("'");
				}
			}
		} 
		
		
		// Ԥ�����ʱ��From
		String exceptStartDate = map.getString("startDate");
		exceptStartDate = exceptStartDate == null ? "" : exceptStartDate;

		// ��ѯԤ�����ʱ��
		if (StringUtils.isNotEmpty(exceptStartDate)){
			sql.append(" and to_char(t.dates,'yyyy-MM-dd') >= '");
			sql.append(exceptStartDate);
			sql.append("'");
		}
		
		// Ԥ�����ʱ��To
		String endDate = map.getString("endDate");
		endDate = endDate == null ? "" : endDate;

		// ��ѯԤ�����ʱ��To
		if (StringUtils.isNotEmpty(endDate)){
			sql.append(" and to_char(t.dates,'yyyy-MM-dd') <= '");
			sql.append(endDate);
			sql.append("'");
		}
		
		// ��ɻ���
		String flyPlanAdep = map.getString("flyPlanAdep");
		if (StringUtils.isNotEmpty(flyPlanAdep)) {
			sql.append(" and t.adep = '"+flyPlanAdep.trim()+"'");
		}
		
		
		String flyPlanAdepName = map.getString("flyPlanAdepName");
		if (StringUtils.isNotEmpty(flyPlanAdepName)) {
			sql.append(" and (cd1.ad_name like '%"+flyPlanAdepName.trim()+"%' or cd1.icao_code like '%"+flyPlanAdepName.trim()+"%')");
		}
		
		// Ŀ�Ļ���
		String flyPlanAdes = map.getString("flyPlanAdes");
		if (StringUtils.isNotEmpty(flyPlanAdes)) {
			sql.append(" and t.ades = '"+flyPlanAdes.trim()+"'");
		}
		
		String flyPlanAdesName = map.getString("flyPlanAdesName");
		if (StringUtils.isNotEmpty(flyPlanAdesName)) {
			sql.append(" and (cd2.ad_name like '%"+flyPlanAdesName.trim()+"%' or cd2.icao_code like '%"+flyPlanAdesName.trim()+"%')");
		}
		
		// �ƻ����
		String planCode = map.getString("planCode");
		if (StringUtils.isNotEmpty(planCode)) {
			sql.append( " and t.f_plan_code like '%" );
			sql.append(planCode);
			sql.append("%'");
		}
		
		// �ƻ�����
		String planName = map.getString("planName");
		if (StringUtils.isNotEmpty(planName)) {
			sql.append( " and t.f_plan_name like '%" );
			sql.append(planName);
			sql.append("%'");
		}
		
		// ����Ա
		String pilot = map.getString("pilot");
		if (StringUtils.isNotEmpty(pilot)) {
			sql.append( " and (t.commander like '%" );
			sql.append(pilot);
			sql.append("%'");
			sql.append("or a3.name like '%");
			sql.append(pilot);
			sql.append("%')");
		}
		
		// �ƻ����
		String flyPlanKind = map.getString("flyPlanKind");
		if (StringUtils.isNotEmpty(flyPlanKind)) {
			sql.append( " and t.pclass = '" );
			sql.append(flyPlanKind);
			sql.append("'");
		}
		
		sql.append(" order by t.planid desc ");
//		System.out.println(sql.toString());
		
		List<String[]> dataList = null;
		
		if(StringUtils.equals("0", flag)) {
			dataList = (ArrayList<String[]>) dao.getObjects(rollPage, sql.toString(),
					new String[]{"planid","f_plan_name","dates","setd","seta","adep","ades","pclass","acft","commander","planstate","planstateId","approve_state","approve_result","approve_state_name","approve_result_name","f_plan_code"});
			
		} else if(StringUtils.equals("1", flag)){
			dataList = (ArrayList<String[]>) dao1.getObjects(rollPage, sql.toString(),
					new String[]{"planid","f_plan_name","dates","setd","seta","adep","ades","pclass","acft","commander","planstate","planstateId","approve_state","approve_result","approve_state_name","approve_result_name","f_plan_code"});
		}
		
		// ��ѯÿ�����мƻ��Ĵ�������
		List<String[]> approve = null;
		
		for(int i=0; dataList != null && i < dataList.size(); i++) {
			String tempId = dataList.get(i)[0];
			if(StringUtils.equals("32", dataList.get(i)[11])){
				approve = dao.getObjects("select c.approve_state,c.approve_result,c1.name as approve_state_name,c2.name as approve_result_name from tb_approve_flow_rec c" +
						" left join tb_code c1 on c1.class_id = 'TH_APPROVE_STATUS' and c1.code_id = c.approve_state" +
						" left join tb_code c2 on c2.class_id='TH_APPROVE_STATE' and c2.code_id = c.approve_result" +
						" where c.object_Id = '" + tempId + "' order by c.send_ts desc,c.approve_id desc",new String[]{"approve_state","approve_result","approve_state_name","approve_result_name"}); 
				if(approve != null && approve.size() > 0 ) {
					String[] rec = approve.get(0);
					
					// ���ƻ��Ѿ����޸ģ���������ǿ�б���ֹ���������ϼ���Ϣ����ʱ���ʱ�ͻ�������̣���һ�����ݴ���״̬��40��ֹ����һ�����ݴ���״̬�ǿա�����һ�����ϼ���������
					// ��Ϊ��ʱ����µ�����Ӧ���ǵ��ǿյģ�������������ƻ��ʼ��FSS�����ˣ�����Ҫȡ�ڶ���
					// 20130711 �޸ģ���Ϊ�������ݿⶼҪͬ��������Ϊ����������ת���������������������������ϼ�����������������������ֹ��ʱ����Ϊʱ����ܵ������ǣ���������ת������������������������ֹ���ա����������ϼ���������
					if(approve.size() >1 && "33".equals(rec[0]) && StringUtils.isEmpty(approve.get(2)[0])){
//						rec = approve.get(1);
						rec = approve.get(2);
						// �����ʱ��Ҫ���˵���ֹǰ��״̬
					} else if(approve.size() > 2 && "051".equals(rec[0])) {
						rec = approve.get(2);
					}
					dataList.get(i)[12] = rec[0];
					dataList.get(i)[13] = rec[1];
					dataList.get(i)[14] = rec[2];
					dataList.get(i)[15] = rec[3];
				}
			}
		}
		
		beanValue.addRequestMap("dataList", dataList);

		this.setConditionParam(beanValue, map);
		
		beanValue.addParamMap("rollPage", rollPage);
		
		if ("flow".equals(managerType)) {
			beanValue.setForward("searchList");
		} else if("query".equals(managerType)){
			
			// ���мƻ���ѯҳ��
			beanValue.setForward("queryList");
		} else {
			beanValue.setForward("auditHistory");
		}
		
		this.getCommonList(beanValue);
	    
		return beanValue;
	}
	
	/**
	 * ��˷��мƻ�ҳ���ʼ��
	 */
	public BeanValue initPlanInfo(RequestMap map) throws Exception {
		
		BeanValue beanValue = new BeanValue();
		ReqIdentity identity = getReqIdentity(map);
		
		String planId = "";
		
		String id = map.getString("chkbox");
		if(id!=null){           //����checkbox�Ĳ���
			planId = id.split(",")[0];
			
		} else {
			planId = map.getString("planId");
		}
		beanValue.addParamMap("planId", planId);
		beanValue.addRequestMap("planId", planId);
		
	    /* ҳ���ʼ�� */
		// ��ѯ�ƻ���Ϣ
		StringBuffer sql = new StringBuffer();
		
		sql.append("select t.planid,t.f_plan_name,cd4.name as unit,cd1.name as rulename,cd2.name as pclass,cd3.name as ptype, " +
				"cd5.name as commander, cd6.name as types, cd7.name as acid,cd8.name as acft,cd9.name as equip,cd10.ad_name as adep," +
				"cd11.ad_name as ades,cd12.ad_name as altn1,cd13.ad_name as altn2,cd14.ad_name as altn3,cd15.ad_name as altn4, " +
				"t.alarm,t.chgt,t.cspd,to_char(t.dates,'yyyy-MM-dd') as dates,t.eet,to_char(t.fpl_ctime,'yyyy-MM-dd HH:mm:ss') as fpl_ctime,t.remark,t.ssr,t.task,t.setd,t.seta,t.setal, " + 
				"cd16.name as planstate,t.f_plan_code,t17.name as plan_user,to_char(t18.cy_btime,'yyyy-MM-dd') as cy_btime,to_char(t18.cy_etime,'yyyy-MM-dd') as cy_etime,t18.cy_lengh," +
				"t18.cy_unit,t18.cy_weekday,t.pclass as pclassId,t.planstate as planstateId from t_fpl t ");
		
		sql.append(" left join tb_code cd1 on cd1.class_id = 'TH_FLY_RULE' and cd1.code_id = t.rule");  //���й���
		
		sql.append(" left join tb_code cd2 on cd2.class_id = 'TH_FLY_PLAN_KIND' and cd2.code_id = t.pclass");   // ��������ظ��ԣ�һ����
		
		sql.append(" left join tb_code cd3 on cd3.class_id = 'TH_FLY_PLAN_TYPE' and cd3.code_id = t.ptype");  //�ƻ����� �� ͨ������
		sql.append(" left join tb_code cd4 on cd4.class_id = 'TH_AIRLINE_COMPANY' and cd4.code_id = t.unit");  //���չ�˾
		sql.append(" left join tb_code cd5 on cd5.class_id = 'TH_PILOT' and cd5.code_id = t.commander");  //����Ա
		sql.append(" left join tb_code cd6 on cd6.class_id = 'TH_FLY_TYPE' and cd6.code_id = t.types");  //��������
		sql.append(" left join tb_code cd7 on cd7.class_id = 'TH_AIR_CRAFT_SN' and cd7.code_id = t.acid");  //���������
		sql.append(" left join tb_code cd8 on cd8.class_id = 'TH_AIR_CRAFT_TYPE' and cd8.code_id = t.acft");  //�������ͺ�
		sql.append(" left join tb_code cd9 on cd9.class_id = 'TH_FLY_EQUIPMENT' and cd9.code_id = t.equip");  //�����豸
		sql.append(" left join t_ad cd10 on cd10.objectid = t.adep");  //��ɻ���
		sql.append(" left join t_ad cd11 on cd11.objectid = t.ades");  //Ŀ�Ļ���
		sql.append(" left join t_ad cd12 on cd12.objectid = t.altn1");  //��������1
		sql.append(" left join t_ad cd13 on cd13.objectid = t.altn2");  //��������2
		sql.append(" left join t_ad cd14 on cd14.objectid = t.altn3");  //��������3
		sql.append(" left join t_ad cd15 on cd15.objectid = t.altn4");  //��������4
		sql.append(" left join tb_code cd16 on cd16.class_id = 'TH_FPL_STATUS' and cd16.code_id = t.planstate");  //�ƻ�״̬
		sql.append(" left join tb_user t17 on t17.user_id = t.plan_user ");  // ͨ���û��Ǵ�tb_user����ȡ�õ�ֵ������Ҫ�����˱�
		sql.append(" left join t_fpl_cycle t18 on t18.planid = t.planid "); 
		
		sql.append(" where t.planid = '").append(planId).append("'");

//		System.out.println(sql.toString());
		List<String[]> dataList = null;
		
		String flag = this.setDepartFlag(map);
		
		if(StringUtils.equals("0", flag)) {
			dataList = (ArrayList<String[]>) dao.getObjects(sql.toString(),
				new String[]{"acft","acid","adep","ades","alarm","altn1","altn2","altn3","altn4","chgt","commander",
				"cspd","dates","eet","equip","f_plan_name","fpl_ctime","pclass","planid","ptype",
				"remark","rulename","seta","setal","setd","ssr","task","types","unit","planstate","f_plan_code","plan_user"
				,"cy_btime","cy_etime","cy_lengh","cy_unit","cy_weekday","pclassId","planstateId"});
			
		} else if(StringUtils.equals("1", flag)){
			dataList = (ArrayList<String[]>) dao1.getObjects(sql.toString(),
				new String[]{"acft","acid","adep","ades","alarm","altn1","altn2","altn3","altn4","chgt","commander",
				"cspd","dates","eet","equip","f_plan_name","fpl_ctime","pclass","planid","ptype",
				"remark","rulename","seta","setal","setd","ssr","task","types","unit","planstate","f_plan_code","plan_user"
				,"cy_btime","cy_etime","cy_lengh","cy_unit","cy_weekday","pclassId","planstateId"});
		}
		
		TFplVO fp = new TFplVO();
		
		if (dataList != null && dataList.size() > 0 ) {
			
			String[] info = dataList.get(0);
			fp.setAcft(info[0]);
			fp.setAcid(info[1]);
			fp.setAdep(info[2]);
			fp.setAdes(info[3]);
			fp.setAlarm(info[4]);
			fp.setAltn1(info[5]);
			fp.setAltn2(info[6]);
			fp.setAltn3(info[7]);
			fp.setAltn4(info[8]);
			fp.setChgt(info[9]);
			fp.setCommander(info[10]);
			
//			fp.setConfirm(info[11]);
			fp.setCspd(info[11]);
			fp.setDates(info[12]);
			fp.setEet(info[13]);
			fp.setEquip(info[14]);
			fp.setFPlanName(info[15]);
			fp.setFplCtime(info[16]);
			fp.setPclass(info[17]);
			fp.setPlanid(info[18]);
			fp.setPtype(info[19]);
			
			fp.setRemark(info[20]);
			fp.setRule(info[21]);
			fp.setSeta(info[22]);
			fp.setSetal(info[23]);
			fp.setSetd(info[24]);
			fp.setSsr(info[25]);
			fp.setTask(info[26]);
			fp.setTypes(info[27]);
			fp.setUnit(info[28]);
			fp.setPlanstate(info[29]);
			fp.setFPlanCode(info[30]);
			fp.setPlanUser(info[31]);
			fp.setCyBtime(info[32]);
			fp.setCyEtime(info[33]);
			if(info[34] != null) {
				fp.setCyLengh(Long.parseLong(info[34]));
			}
			fp.setCyUnit(info[35]);
			StringBuffer sf = new StringBuffer();
			String str = "";
			if(info[36] != null ) {
			
				String[] a = info[36].split(":");
				for (String temp : a){
					if(StringUtils.equals("1", temp)) {
						sf.append("��");
					}else if(StringUtils.equals("2", temp)) {
						sf.append("һ");
					}else if(StringUtils.equals("3", temp)) {
						sf.append("��");
					}else if(StringUtils.equals("4", temp)) {
						sf.append("��");
					}else if(StringUtils.equals("5", temp)) {
						sf.append("��");
					}else if(StringUtils.equals("6", temp)) {
						sf.append("��");
					}else if(StringUtils.equals("7", temp)) {
						sf.append("��");
					}
					sf.append("��");
				}
			}
			if(sf.toString().lastIndexOf("��") > 0) {
				str = sf.toString().substring(0, sf.toString().length()-1);
			}
			fp.setCyWeekday(str);
			fp.setPclassId(info[37]);
			
			if(StringUtils.equals("72", info[38]) || StringUtils.equals("73", info[38])) {
				if(StringUtils.equals("0", flag)) {
					this.getActivity(dao,beanValue,planId);
				} else {
					this.getActivity(dao1,beanValue,planId);
				}
			}
			
			// ��ѯ����
			if(StringUtils.equals("0", flag)) {
				this.getPlanRs(dao,beanValue,planId);
			} else {
				this.getPlanRs(dao1,beanValue,planId);
			}
		}
		
		beanValue.addRequestMap("dataInfo", fp);

		if(!StringUtils.equals("detail", map.getString("type"))) {
			
			List<TbApproveFlowRec> approve = null;
			TbApproveFlowRec rec = new TbApproveFlowRec();
			
			// FSS�û��ã�����һ��FSS�û��鿴������ʱ�򣬱�ʾ�÷��мƻ���������
			// ��ʱ��Ҫ����������̵Ľ����˺ʹ���״̬�����мƻ���ķ���״̬�޸�Ϊ21����������
			// ����FSS�û��鿴ʱֻ�ܲ鿴����������������
	
		    // ��ǰ�û���������FSSʱ
			if (StringUtils.equals("0", flag)) {
				
				// ������Ϊ��ʱ����������̱�Ľ����˺ʹ���״̬,���мƻ���ķ���״̬�޸�Ϊ21��������
				approve = dao.getObjects("from TbApproveFlowRec c where c.objectId = '" + planId + "' order by c.sendTs desc,c.id.approveId desc"); 
				if(approve != null && approve.size() > 0 ) {
					rec = approve.get(0);
					
					// ���ƻ��Ѿ����޸ģ���������ǿ�б���ֹ���������ϼ���Ϣ����ʱ���ʱ�ͻ�������̣���һ�����ݴ���״̬��40��ֹ����һ�����ݴ���״̬�ǿա�����һ�����ϼ���������
					// ��Ϊ��ʱ����µ�����Ӧ���ǵ��ǿյģ�������������ƻ��ʼ��FSS�����ˣ�����Ҫȡ�ڶ���
					// 20130711 �޸ģ���Ϊ�������ݿⶼҪͬ��������Ϊ����������ת���������������������������ϼ�����������������������ֹ��ʱ����Ϊʱ����ܵ������ǣ���������ת������������������������ֹ���ա����������ϼ���������
					if("33".equals(approve.get(0).getApproveState()) && StringUtils.isEmpty(approve.get(2).getApproveState())){
						rec = approve.get(2);
					}
					
					// ������Ϊ�գ������Ϊ��ǰ�û����߽�����Ϊ��ǰ�û��������˵��ڵ�½�û�����״̬Ϊ�ձ�ʾ�üƻ����������޸��ˣ�Ҫ����������
					if (rec.getApproveUserId() == null || (rec.getApproveUserId().equals(identity.getUserId()) && rec.getApproveState() == null)) {
						
						//�����˸���Ϊ��ǰ�û�
						rec.setApproveUserId(identity.getUserId());
						rec.setApproveRoleId(identity.getUserRole());
						// ����״̬�޸�Ϊ��21 ������
						rec.setApproveState("21");
						rec.setApproveTs(new Date());
						
						dao.saveOrUpdateObject(rec);
//						
//						// 20130709 ͬ����������
//						dao1.saveOrUpdateObject(rec);
						
						// ���мƻ���ķ���״̬�޸�Ϊ21��������
						TFpl plan = (TFpl) dao.getObject(TFpl.class, Long.parseLong(planId));
						if(plan != null) {
							plan.setPlanstate("21");
							dao.saveOrUpdateObject(plan);
						}
	
						// ���мƻ���ķ���״̬�޸�Ϊ21��������
						TFpl superPlan = (TFpl) dao1.getObject(TFpl.class, Long.parseLong(planId));
						if(superPlan != null) {
							superPlan.setPlanstate("21");
							dao1.saveOrUpdateObject(superPlan);
						}
					} 
				}
			}
			// ��ǰ�û����������񺽵��ϼ�����ʱ,��ѯ���������Լ��Ĳ��Ҵ���״̬�Ǵ�������
			else if(StringUtils.equals("1", flag)) {
				
				approve = dao1.getObjects("from TbApproveFlowRec c where c.objectId = '" + planId + "' order by c.sendTs desc,c.id.approveId desc"); 
				if(approve != null && approve.size() > 0 ) {
					rec = approve.get(0);
					
					if(rec.getApproveUserId().equals(identity.getUserId())) {
						rec.setApproveTs(new Date());
						dao1.saveOrUpdateObject(rec);
	
						// 20130709 ͬ����������
						TbApproveFlowRec newRec = new TbApproveFlowRec();
						BeanUtil.convertObject(newRec, rec);
						dao.saveOrUpdateObject(newRec);
					}
				}
			}
			
			beanValue.addRequestMap("approve", rec);
		}
		
		this.setConditionParam(beanValue, map);
		
		this.getCommonList(beanValue);
	    
		if(!StringUtils.equals("detail", map.getString("type"))) {
			beanValue.setForward("auditInfo");
		} else {
			beanValue.setForward("planDetail");
		}
		
		
		return beanValue;
	}

	/**
	 * ��˴���
	 */
	public BeanValue saveAuditFlow(RequestMap map) throws Exception{
		
		BeanValue beanValue = new BeanValue();
		ReqIdentity identity = getReqIdentity(map);
		
		String planId = map.getString("planId");
		beanValue.addRequestMap("planId", planId);
		beanValue.addParamMap("planId", planId);
		
		this.setConditionParam(beanValue, map);
		
		// ����Ƿ���Ҫ�ϼ����� 0������Ҫ 1����Ҫ
		String isSuperior = map.getString("isSuperior");
		
		Site site = (Site) map.getObject(ProjectConstants.SESSION_LOCAL);
		
		TbApproveFlowRec flowRec = new TbApproveFlowRec();
		
		TbApproveFlowRec newFlowRec = new TbApproveFlowRec();
		
		flowRec.setId(new TbApproveFlowRecId(this.getMaxApproveId(dao)+1,site.getSite_id()));
		
		// ������
		flowRec.setNote(map.getString("note"));
		flowRec.setObjectId(planId);
		flowRec.setSendTs(new Date());
		flowRec.setSendUserId(identity.getUserId());
		flowRec.setSendRoleId(identity.getUserRole());
		// ����ʱ��
//		flowRec.setApproveTs(new Date());
		// ������ 0������� 1�����ͨ��  2����˲�ͨ��
		flowRec.setApproveResult(map.getString("approveResult"));
		
		// ����Ҫ�ϼ�������
		if (StringUtils.equals("0", isSuperior)) {
			
			// ����״̬ 32��������
			flowRec.setApproveState("32");
			
			// flag=0:FSS ; flag=1���ϼ�
			String flag = this.setDepartFlag(map);
			if(StringUtils.equals("0", flag)) {

				// ��һ��������
//				flowRec.setApproveUserId(map.getString("sendUserId"));
				
				dao.saveOrUpdateObject(flowRec);
				
				// ��ѯ�÷��мƻ���һ������ˣ������ǰ������ǵ�һ�������ʱ�����ɣ����·��мƻ����״ֵ̬
				List<String[]> approveUserList = dao.getObjects("select c.approve_user_id from tb_approve_flow_rec c where c.object_id = '" +
						planId + "' order by c.approve_id asc ", new String[]{"approve_user_id"});
				if(approveUserList != null && approveUserList.size() >0 
						&& StringUtils.equals(identity.getUserId(), approveUserList.get(0)[0])){ 
					
					// ���·��мƻ����״̬Ϊ32��������
					TFpl fpl = this.saveTFplData(planId, dao);
					
					// ͬ���ѷɻ��ƻ����µ��ϼ����ݿ��� ��ʱ��sql��ѯʱ���мƻ����������У�����Ҫ����״̬Ϊ������ TODO
					this.saveSuperData(planId,"1");	
					
					// ͬʱ�����ϼ����ݿ��������̱�
					List<TbApproveFlowRec> list = dao.getObjects("from TbApproveFlowRec c where c.objectId = '" + planId + "' order by c.sendTs desc,c.id.approveId desc");
					dao1.saveOrUpdateBatchObject(list);
					
					// ����������Լƻ�������ͨ���Ļ���Ҫ�������ڼƻ����Ӽƻ�
					if(StringUtils.equals("GAR", fpl.getPclass()) && StringUtils.equals("1", map.getString("approveResult"))) {
						
						saveChildPlan(fpl);
					}
				}
				
				
			} else if(StringUtils.equals("1", flag)) {
				
//				// �ϼ���������ʱ������Ҫ���´������Ĵ���ʱ�䣬Ȼ��������
//				List<TbApproveFlowRec> list = dao1.getObjects("from TbApproveFlowRec c where c.objectId = '" + planId + "' and c.approveUserId='"+ identity.getUserId() +"' and c.approveState='21' order by c.sendTs desc,c.id.approveId desc");
//				if(list != null && list.size() >0) {
//					TbApproveFlowRec rec = list.get(0);
//					rec.setApproveTs(new Date());
//					dao1.saveOrUpdateObject(rec);
//					// 20130709 ͬ����������
//					dao.saveOrUpdateObject(rec);
//				}
				
				// ��һ�������ˣ�Ϊ�˷����ѯ�ϼ��Ĵ���״̬������������˸��Լ���
				flowRec.setApproveUserId(identity.getUserId());
				flowRec.setApproveRoleId(identity.getUserRole());
				dao1.saveOrUpdateObject(flowRec);
				
				// 20130709 ͬ����������
				dao.saveOrUpdateObject(flowRec);
				
				// ͬʱ�����¼����ݿ��������̱�
				BeanUtil.convertObject(newFlowRec, flowRec);
				// ����״̬���ϼ�������
				newFlowRec.setApproveState("33");
				newFlowRec.setApproveUserId(map.getString("sendUserId"));
				newFlowRec.setApproveRoleId(map.getString("sendRoleId"));
//				newFlowRec.setApproveTs(null);
				newFlowRec.setId(new TbApproveFlowRecId(this.getMaxApproveId(dao)+1,site.getSite_id()));
				dao.saveOrUpdateObject(newFlowRec);
				
				// 20130709 ͬ����������
				dao1.saveOrUpdateObject(newFlowRec);
			}
			
		// ��Ҫ�ϼ������Ĵ���
		} else if(StringUtils.equals("1", isSuperior)) {
			
			// ����״̬ 22��ת��������
			flowRec.setApproveState("22");
			// ��һ��������
			flowRec.setApproveUserId(map.getString("supeiorMan"));
			flowRec.setApproveRoleId("R1000");
			dao.saveOrUpdateObject(flowRec);
			
//			// ͬʱ�����ϼ����ݿ��������̱�
//			// 20130709 ��������ͬ��
//			dao1.saveOrUpdateObject(flowRec);
			
			BeanUtil.convertObject(newFlowRec, flowRec);
			// ����״̬Ϊ��21 ������
			newFlowRec.setApproveState("21");
			// ����ʱ�����óɿ�
			newFlowRec.setApproveTs(null);
			//�����������Ϊ��
			newFlowRec.setNote(null);
			newFlowRec.setId(new TbApproveFlowRecId(this.getMaxApproveId(dao)+1,site.getSite_id()));
			
			// 20130709 ��������ͬ��
			dao.saveOrUpdateObject(newFlowRec);
			
			// ͬ���ѷɻ��ƻ����µ��ϼ����ݿ���
			this.saveSuperData(planId,"0");	
			
			// ͬʱ�����ϼ����ݿ��������̱�
			List<TbApproveFlowRec> list = dao.getObjects("from TbApproveFlowRec c where c.objectId = '" + planId + "' order by c.sendTs desc,c.id.approveId desc");
			dao1.saveOrUpdateBatchObject(list);
		}
		
		return beanValue;
	}
	
	/**
	 * �鿴��˴�����ϸ
	 */
	public BeanValue flowDetail(RequestMap map) throws Exception{
		
		BeanValue beanValue = new BeanValue();
		
		this.setConditionParam(beanValue, map);
		
		String planId = map.getString("planId");
		
		/* ��ҳ���� */
		// ��ȡҳ��
		String page = map.getString("page");
		// ��ȡ��ҳ��־
		String pageBool = map.getString("pageBool");
		if (pageBool == null || pageBool.equals("")) {
			pageBool = "0";
		}
		// ��ҳ��ת����Ϊ����
		RollPage rollPage = new RollPage(page == null || page.equals("") ? 0 : ConvertLang.convertint(page), pageBool);
		// ���õ�ҳ��¼��
		rollPage.setPagePer(12);
		
		beanValue.addRequestMap("rollPage", rollPage);
		
		List<String[]> list = null;
		String flag = this.setDepartFlag(map);
		
		String sql = "select t5.f_plan_name, t5.dates, t7.ad_name as adep, t8.ad_name as ades, t6.name as planstate, t.send_user_id," +
		"t2.name as unit, case when t1.name is not null then t1.name else t9.name end as sendUsername, " +
		"to_char(t.send_ts,'yyyy-mm-dd hh24:mi:ss') as send_ts, to_char(t.approve_ts,'yyyy-mm-dd hh24:mi:ss') as approve_ts, t3.name as approve_state," +
		"case when approve_state is null then '' else t4.name end as approve_result,t.note,t.approve_state as approve_state_id,t.approve_user_id,t10.name as approveUsername,t11.name as approveUnit " +
		"from t_fpl t5  " +
		"left join tb_approve_flow_rec t on t.object_id = t5.planid " +
		"left join tb_code t1 on t.send_user_id = t1.code_id left join tb_code t2 on t1.class_id = t2.code_id " +
		"left join tb_code t3 on t3.class_id = 'TH_APPROVE_STATUS' and t.approve_state = t3.code_id " +  // ���״̬
		"left join tb_code t4 on t4.class_id = 'TH_APPROVE_STATE' and t.approve_result = t4.code_id " +  // ��˽��
		"left join tb_code t6 on t6.class_id = 'TH_FPL_STATUS' and t5.planstate = t6.code_id " +
		"left join t_ad t7 on t7.objectid = t5.adep " +  //��ɻ���
		"left join t_ad t8 on t8.objectid = t5.ades " +  //Ŀ�Ļ���
		"left join tb_user t9 on t9.user_id = t.send_user_id " +  // ͨ���û��Ǵ�tb_user����ȡ�õ�ֵ������Ҫ�����˱�
		"left join tb_code t10 on t.approve_user_id = t10.code_id left join tb_code t11 on t10.class_id = t11.code_id "+
		"where t5.planid = '" + planId + "' order by t.approve_id desc";
		
		if(StringUtils.equals("0", flag)) {
			list = dao.getObjects(rollPage,sql,
					new String[]{"f_plan_name","dates","adep","ades","planstate",
					"send_user_id","unit","sendUsername","approve_ts","approve_state","approve_result","note","approve_state_id","approve_user_id","approveUsername","approveUnit","send_ts"});
			
		} else if(StringUtils.equals("1", flag)){
			
			list = dao1.getObjects(rollPage,sql,
					new String[]{"f_plan_name","dates","adep","ades","planstate",
					"send_user_id","unit","sendUsername","approve_ts","approve_state","approve_result","note","approve_state_id","approve_user_id","approveUsername","approveUnit","send_ts"});
		}
		 
		
		if(list != null && list.size() > 0){
			beanValue.addRequestMap("dataList", list);
		}
		
		beanValue.setForward("flowDetail");
		
		return beanValue;
		
	}
	
	/**
	 * �жϵ�ǰ�û������ڲ���
	 * 			
	 * @param map
	 * 	      ��ɫR1999����ʾFSS�û�����ɫR1000��ʾ�ϼ������û�
	 * @return  departFlag  
	 * 			0����ʾ��FSS�û���1���ϼ��û�
	 * @throws Exception 
	 */
	private String setDepartFlag(RequestMap map) throws Exception{
		
		// ��õ�ǰ�û��ĵĽ�ɫ
		ReqIdentity identity = getReqIdentity(map);
		
	    String departFlag = "0";
	    if(StringUtils.equals("R1000", identity.getUserRole())){
	    	departFlag = "1";
	    }
		return departFlag;
	}
	/**
	 * ���·��мƻ���ļƻ�״̬
	 * @param planId  �ƻ�ID
	 * @param dao     �������ݿ�
	 * @throws BaseException
	 */
	private TFpl saveTFplData(String planId, Dao dao) throws BaseException {
		
		TFpl fpl = (TFpl) dao.getObject(TFpl.class, Long.parseLong(planId));
		fpl.setPlanstate("32");
		dao.saveOrUpdateObject(fpl);
		return fpl;
	}
	
	private Long getMaxApproveId(Dao dao) throws BaseException {
		
		Long approveId = 0L;
		List<String[]> info = dao.getObjects("select max(approve_id) as approveId from tb_approve_flow_rec", new String[]{"approveId"});
		if(info != null && info.size() > 0) {
			approveId = Long.parseLong(info.get(0)[0]);
		}
		return approveId;
	}
	
	private void getCommonList(BeanValue value) {

	    // ���й���
	    List<Code> flyRuleList = ProjectItemCode.getCodeList("TH_FLY_RULE");
	    value.addRequestMap("flyRuleList", flyRuleList);
	    
	    // ���мƻ�״̬
	    List<Code> flyStatusList = ProjectItemCode.getCodeList("TH_FPL_STATUS");
	    
	    List<Code> tempList = new ArrayList();
	    
	    for(Code c : flyStatusList){
	    	if(!"41".equals(c.getCode_id()) && !"42".equals(c.getCode_id())){
	    		tempList.add(c);
	    	}
	    }
	    value.addRequestMap("statusList", tempList);
	    
	    // ����Ա
	    List<Code> pilotList = ProjectItemCode.getCodeList("TH_PILOT");
	    value.addRequestMap("pilotList", pilotList);
	    
	    // �������ͺ�
	    List<Code> aircraftTypeList = ProjectItemCode.getCodeList("TH_AIR_CRAFT_TYPE");
	    value.addRequestMap("aircraftTypeList", aircraftTypeList);
	    
	    
	    // ���мƻ����
	    List<Code> flyPlanKindList = ProjectItemCode.getCodeList("TH_FLY_PLAN_KIND");
	    value.addRequestMap("flyPlanKindList", flyPlanKindList);
	    
	    // �ϼ�����
	    List<Code> supeiorDepartList = ProjectItemCode.getCodeList("TH_SUPPER_DEPARTMENT");
	    value.addRequestMap("supeiorDepartList", supeiorDepartList);
	    
	    // �ϼ������񺽰칫����Ա
	    List<Code> supeiorManList1 = ProjectItemCode.getCodeList("TH_SUPPER_DEPT_001");
	    value.addRequestMap("supeiorManList1", supeiorManList1);

	    // �ϼ����������ΰ칫����Ա
	    List<Code> supeiorManList2 = ProjectItemCode.getCodeList("TH_SUPPER_DEPT_002");
	    value.addRequestMap("supeiorManList2", supeiorManList2);

	    // �ϼ������񺽻��ز�����Ա
	    List<Code> supeiorManList3 = ProjectItemCode.getCodeList("TH_SUPPER_DEPT_003");
	    value.addRequestMap("supeiorManList3", supeiorManList3);
	    
	    List<Code> supeiorManList = new ArrayList<Code>();
	    supeiorManList.addAll(supeiorManList1);
	    supeiorManList.addAll(supeiorManList2);
	    supeiorManList.addAll(supeiorManList3);
	    value.addRequestMap("supeiorManList", supeiorManList);
	    
	    // �������
	    List<Code> approveResultList = ProjectItemCode.getCodeList("TH_APPROVE_STATE");
	    value.addRequestMap("approveResultList", approveResultList);

		List<Code> autoPeriodList = ProjectItemCode.getCodeList("AUTO_PERIOD");
		value.addRequestMap("autoPeriodList", autoPeriodList);
	}
	
	/**
	 * ��ҳ����
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
		// ��ѯ���ͣ����searchTypeΪ����Ϊ�����б�Ϊlist��Ϊ��ѯ�б�
		String managerType = map.getString("managerType");
		// ����ÿһҳ��ʾ�ļ�¼��
		if ("flow".equals(managerType)) {
			rollPage.setPagePer(16);
		} else {
			rollPage.setPagePer(16);
		}

		// ���÷�ҳ��ѯ����
		String flyPlanKind = map.getString("flyPlanKind");
		rollPage.setCondition("flyPlanKind", flyPlanKind);
		
		String startDate = map.getString("startDate");
		rollPage.setCondition("startDate", startDate);

		String endDate = map.getString("endDate");
		rollPage.setCondition("endDate", endDate);

		// ���ڷ�Χ
		String qDate = map.getString("qDate");
		rollPage.setCondition("qDate", qDate);
		
		String flyPlanAdep = map.getString("flyPlanAdep");
		rollPage.setCondition("flyPlanAdep", flyPlanAdep);

		String flyPlanAdes = map.getString("flyPlanAdes");
		rollPage.setCondition("flyPlanAdes", flyPlanAdes);
		
		String flyPlanAdepName = map.getString("flyPlanAdepName");
		rollPage.setCondition("flyPlanAdepName", flyPlanAdepName);

		String flyPlanAdesName = map.getString("flyPlanAdesName");
		rollPage.setCondition("flyPlanAdesName", flyPlanAdesName);
		
		String planCode = map.getString("planCode");
		rollPage.setCondition("planCode", planCode);

		String planName = map.getString("planName");
		rollPage.setCondition("planName", planName);
		
		String pilot = map.getString("pilot");
		rollPage.setCondition("pilot", pilot);
		
		if(!StringUtils.equals("flow", map.getString("managerType"))){
			
			String planStatus = map.getString("planStatus");
			rollPage.setCondition("planStatus", planStatus);
		}
	}
	

	public void setDao(Dao dao) {
		this.dao = dao;
	}
	
	private void setConditionParam(BeanValue value,RequestMap map) {
		
		// ���ò�ѯ����
		String flyPlanKind = map.getString("flyPlanKind");
		value.addRequestMap("flyPlanKind", flyPlanKind);
		
		String startDate = map.getString("startDate");
		value.addRequestMap("startDate", startDate);
		
		String endDate = map.getString("endDate");
		value.addRequestMap("endDate", endDate);
		
		// ���ڷ�Χ
		String qDate = map.getString("qDate");
		value.addRequestMap("qDate", qDate);
		
		String flyPlanAdep = map.getString("flyPlanAdep");
		value.addParamMap("flyPlanAdep",flyPlanAdep);
		value.addRequestMap("flyPlanAdep",flyPlanAdep);
		
		String flyPlanAdes = map.getString("flyPlanAdes");
		value.addParamMap("flyPlanAdes",flyPlanAdes);
		value.addRequestMap("flyPlanAdes",flyPlanAdes);

		String flyPlanAdepName = map.getString("flyPlanAdepName");
		value.addParamMap("flyPlanAdepName",flyPlanAdepName);
		value.addRequestMap("flyPlanAdepName",flyPlanAdepName);
		
		String flyPlanAdesName = map.getString("flyPlanAdesName");
		value.addParamMap("flyPlanAdesName",flyPlanAdesName);
		value.addRequestMap("flyPlanAdesName",flyPlanAdesName);
		
		String planCode = map.getString("planCode");
		value.addRequestMap("planCode", planCode);
		
		String pilot = map.getString("pilot");
		value.addRequestMap("pilot", pilot);
		
		String planName = map.getString("planName");
		value.addRequestMap("planName", planName);

		String planStatus = map.getString("planStatus");
		value.addRequestMap("planStatus", planStatus);
		
		// �������ͣ����managerTypeΪflow��ת����˹����б�query��ת���ƻ���ֹ/��������б�
		String managerType = map.getString("managerType");
		value.addRequestMap("managerType", managerType);
		value.addParamMap("managerType", managerType);
		
		/* ��ҳ���� */
        // ��ǰҳ��, Ĭ��Ϊ 0
        String page = map.getString("page");
        page = (page == null || page.equals("")) ? "0" : page;
        // ��ѯ��ʶ, 0 ��������ѯ, 1 ��ҳ��ѯ
        String pageBool = map.getString("pageBool");
        // ��ҳ����
        RollPage aRollPage = new RollPage(ConvertLang.convertint(page), pageBool);
        
        /* ���û��˲��� */
		value.addParamMap("page", page);
		value.addParamMap("pageBool", pageBool);
		value.addParamMap("rollPage", aRollPage);
	}

	public void setDao1(Dao dao1) {
		this.dao1 = dao1;
	}
	
	/**
	 * �������ݣ���ȡ��ǰ�ƻ�˳��š� ��1 �� 9999 ��ѭ��ʹ�á�
	 * @return String
	 * @throws Exception
	 */
	private String getPlanCodeSeq()throws Exception {
		String codeSeq=null;
		List<String[]> seqList=dao.getObjects("SELECT SEQ_FPL_PLAN_CODE.Nextval as ns FROM DUAL", new String[]{"ns"});
		if(seqList != null && seqList.size() >0){
			codeSeq=seqList.get(0)[0];
			for(int i=codeSeq.length();i<4;i++){
				codeSeq="0"+codeSeq;
			}
		}
		return codeSeq;
	}

	/**
	 * ͬ�����ϼ����ݿ���
	 * @param planId
	 * @param flag  0:δ������� 1���������
	 * 				1�ĳ���Ҫ���·��мƻ�״̬Ϊ������
	 * @throws BaseException
	 */
	private void saveSuperData(String planId,String flag) throws BaseException {
		
		dao1.updateBySql("delete from thyx1.t_fpl_rs_p tt where tt.rs_id in (select r.rs_id from thyx1.t_fpl_rs r where r.planid=" + planId +")");
		dao1.updateBySql("delete from thyx1.t_fpl_rs tt where tt.planid=" + planId);
		// �����Լƻ�
		dao1.updateBySql("delete from thyx1.t_fpl_cycle tt where tt.planid=" + planId);
		dao1.updateBySql("delete from thyx1.t_fpl tt where tt.planid=" + planId);

		
		// ������мƻ�
		StringBuffer sf = new StringBuffer("insert into thyx1.t_fpl "); 
		sf.append("select * from thyx.t_fpl t where t.planid=").append(planId);
		dao1.updateBySql(sf.toString());
		if("1".equals(flag)) {
			dao1.updateBySql("update thyx1.t_fpl tt set tt.planstate='32' where tt.planid=" + planId);
		}
		
		// �����Լƻ����������Լƻ���
		List<String[]> list = dao.getObjects("select t.pclass from thyx.t_fpl t where t.planid=" + planId, new String[]{"pclass"});
		if(list != null && list.size() > 0) {
			String pclass = list.get(0)[0];
			if(StringUtils.equals("GAR", pclass)) {
				sf = new StringBuffer();
				sf = new StringBuffer("insert into thyx1.t_fpl_cycle "); 
				sf.append("select * from thyx.t_fpl_cycle t where t.planid=").append(planId);
				dao1.updateBySql(sf.toString());
			}
		}
		
		// ���뺽��
		sf = new StringBuffer();
		sf = new StringBuffer("insert into thyx1.t_fpl_rs "); 
		sf.append("select * from thyx.t_fpl_rs t where t.planid=").append(planId);
		dao1.updateBySql(sf.toString());

		// ���뺽�߽ڵ�
		sf = new StringBuffer();
		sf = new StringBuffer("insert into thyx1.t_fpl_rs_p "); 
		sf.append("select * from thyx.t_fpl_rs_p t where t.rs_id in (select r.rs_id from thyx.t_fpl_rs r where r.planid=" + planId +")");
		dao1.updateBySql(sf.toString());
	}
	
	/**
	 * �������ݣ��������мƻ��������¼ƻ���
	 * @param oldPlanId �������ļƻ�
	 * @param newPlanId ���ɵ��¼ƻ���ID 
	 * @param beanValue ���Ʋ���ʱ����Ҫ��������Ϣ����beanValue������ҳ����ʾ
	 * @throws Exception
	 */
	private void savePlanRsByCopy(String oldPlanId,Long newPlanId)throws Exception {

		List<TFplRs> list1=dao.getObjects("from TFplRs trs where trs.planid = "+oldPlanId+" and trs.rsType='1'");
		if(list1 != null && list1.size() >0){
			TFplRs temp=list1.get(0);
			TFplRs trsMain=new TFplRs();
			BeanUtil.convertObject(trsMain, temp);
			trsMain.setRsId(null);
			trsMain.setPlanid(newPlanId);
			trsMain.setCreateDate(new Date());
			dao.saveObject(trsMain);
			List<TFplRsP> list3=dao.getObjects("from TFplRsP trsp where trsp.rsId="+temp.getRsId()+" order by trsp.rsPNum");
			List templist3=new ArrayList();
			if(list3 != null && list3.size() >0){
				for(TFplRsP temp1:list3){
					TFplRsP rsP=new TFplRsP();
					BeanUtil.convertObject(rsP, temp1);
					rsP.setRsPId(null);
					rsP.setRsId(trsMain.getRsId());
					dao.saveObject(rsP);
					templist3.add(rsP);
				}
			}
		}
		List<TFplRs> list2=dao.getObjects("from TFplRs trs where trs.planid="+oldPlanId+" and trs.rsType='0'");
		if(list2 != null && list2.size() >0){
			TFplRs temp=list2.get(0);
			TFplRs trsBak=new TFplRs();
			BeanUtil.convertObject(trsBak, temp);
			trsBak.setRsId(null);
			trsBak.setPlanid(newPlanId);
			trsBak.setCreateDate(new Date());
			dao.saveObject(trsBak);
			List<TFplRsP> list4=dao.getObjects("from TFplRsP trsp where trsp.rsId="+temp.getRsId()+" order by trsp.rsPNum");
			List templist4=new ArrayList();
			if(list4 != null && list4.size() >0){
				for(TFplRsP temp2:list4){
					TFplRsP rsP=new TFplRsP();
					BeanUtil.convertObject(rsP, temp2);
					rsP.setRsPId(null);
					rsP.setRsId(trsBak.getRsId());
					dao.saveObject(rsP);
					templist4.add(rsP);
				}
				
			}
		}
	
		
	}
	
	private void saveChildPlan(TFpl tFpl ) throws Exception {

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		 //  �����Ա����ƻ�
		TFplCycle tFplCycle=null;
		String tempHql="from TFplCycle cycle where cycle.planid="+tFpl.getPlanid();
		List list=dao.getObjects(tempHql);
		
		 // �������ڼƻ��Ӽƻ�
		if(list !=null && list.size() >0){
			
			tFplCycle=(TFplCycle) list.get(0);
			Calendar c1=Calendar.getInstance();
			Date date1=tFplCycle.getCyBtime();
			
			if(tFplCycle.getCyUnit().trim().equals("D")){ 
				// ���ڵ�λ���� 
				for (c1.setTime(date1);c1.getTime().compareTo(tFplCycle.getCyEtime())<=0;c1.add(Calendar.DATE, tFplCycle.getCyLengh().intValue())){
					String codeSeq = this.getPlanCodeSeq();
					TFpl tempFpl =new TFpl();
					BeanUtil.convertObject(tempFpl,tFpl);
					tempFpl.setPlanid(null);
					tempFpl.setDates(c1.getTime());
					tempFpl.setFPlanName(tFpl.getFPlanName()+"-"+dateFormat.format(tempFpl.getDates()));
					tempFpl.setFPlanCode(dateFormat.format(tempFpl.getDates())+codeSeq);
					tempFpl.setPlanstate("31");  // TODO
					tempFpl.setPclass("GA");
					tempFpl.setFplCtime(new Date());
					tempFpl.setFPlanid(tFpl.getPlanid());
					dao.saveOrUpdateObject(tempFpl);
					this.savePlanRsByCopy(tFpl.getPlanid().toString(),tempFpl.getPlanid());
				}
				
			}else{  // ���ڵ�λ����
				
				String[] weekdays=tFplCycle.getCyWeekday().split(":");
				
				if(weekdays != null && weekdays.length>0){
					Date[] dFlag=new Date[weekdays.length];
					c1.setTime(tFplCycle.getCyBtime());
					int m=0;
					for( int k1=0; k1<7;k1++){  // ѭ���ߴΣ���ȡ����һ�������ڵ�ÿһ�죬m ��������ʹ����ֵ����С����
						for(int k=0;k<weekdays.length;k++){
							String week=c1.get(Calendar.DAY_OF_WEEK)+"";
							if(week.trim().equals(weekdays[k])){
								dFlag[m]=c1.getTime();
								m++;
								break;  // ����ҵ���Ӧ�����ڡ�
							}
						}
						c1.add(Calendar.DATE, 1);	
					}
					
				// ����Ϊ���ڵ��ظ��Լƻ������õ�һ��ʵʩ����Ϊ�ƻ��������
					c1.setTime(date1);
					for(int k2=0;c1.getTime().compareTo(tFplCycle.getCyEtime())<=0;k2++){
							
						for(int k=0;k<weekdays.length;k++){ // ����ÿһ�������ڵļƻ�
							c1.setTime(dFlag[k]);
							c1.add(Calendar.DATE, k2*7*tFplCycle.getCyLengh().intValue());	// ���ɸ������ڵµ����ƻ��ļƻ�����
							if(c1.getTime().compareTo(tFplCycle.getCyEtime())>0){
								break;
							}
							
							String codeSeq = this.getPlanCodeSeq();
							TFpl tempFpl =new TFpl();
							BeanUtil.convertObject(tempFpl,tFpl);
							tempFpl.setPlanid(null);										
							tempFpl.setDates(c1.getTime());
							tempFpl.setFPlanName(tFpl.getFPlanName()+"-"+dateFormat.format(tempFpl.getDates()));
							tempFpl.setFPlanCode(dateFormat.format(tempFpl.getDates())+codeSeq);
							tempFpl.setPlanstate("31");
							tempFpl.setPclass("GA");
							tempFpl.setFplCtime(new Date());
							tempFpl.setFPlanid(tFpl.getPlanid());
							dao.saveOrUpdateObject(tempFpl);
							this.savePlanRsByCopy(tFpl.getPlanid().toString(),tempFpl.getPlanid());
							
						}
					}
				}
			}
		}
	}
	
	public void updateSuperData(String planId) throws Exception {
		
		// �����ϼ����ݿ��ظ��ƻ����Ӽƻ�
		dao1.updateBySql("insert into thyx1.t_fpl select * from thyx.t_fpl t where t.f_planid="+planId);
		dao1.updateBySql("insert into thyx1.t_fpl_rs select * from thyx.t_fpl_rs t where exists ( select t2.planid from thyx.t_fpl t2 where t2.f_planid="+planId+" and t.planid=t2.planid)");
		dao1.updateBySql("insert into thyx1.t_fpl_rs_p select * from thyx.t_fpl_rs_p t where exists (select t1.rs_id from thyx.t_fpl_rs t1 where  exists (select t2.planid from thyx.t_fpl t2 where t2.f_planid="+planId+" and t1.planid=t2.planid ) and t.rs_id=t1.rs_id )");
	}
	
	/**
	 * ���ʵ��ʵʩ��Ϣ
	 * @param dao
	 * @param value
	 * @param planId
	 * @throws Exception
	 */
	private void getActivity(Dao dao, BeanValue value, String planId) throws Exception {
		List<TFplActivity> activity = dao.getObjects("from TFplActivity a where a.TFpl.planid=" + planId);
		if(activity != null && activity.size() > 0) {
			value.addRequestMap("activity", activity.get(0));
		}
	}
	
	/**
	 * ��ú�����Ϣ
	 * @param dao
	 * @param value
	 * @param planId
	 * @throws Exception
	 */
	private void getPlanRs(Dao dao, BeanValue value, String planId) throws Exception {
		List<TFplRs> tFplRsList = dao.getObjects("from TFplRs rs where rs.planid=" + planId);
		StringBuffer sf = new StringBuffer();
		if(tFplRsList != null && tFplRsList.size() > 0) {
			for(TFplRs tempRs :tFplRsList) {
				String str = "";
				sf = new StringBuffer();
				List<TFplRsP> tFplRsPList = dao.getObjects("from TFplRsP rs where rs.rsId=" + tempRs.getRsId() + " order by rs.rsPNum");
				
				for(TFplRsP p : tFplRsPList) {
					sf.append(p.getRsPName());
					sf.append("-");
				}
				if(sf.toString().endsWith("-")){
					str = sf.substring(0, sf.length()-1);
				}
				if(StringUtils.equals("1", tempRs.getRsType())) {
					value.addRequestMap("mainRs", str);
					value.addRequestMap("mainRsList", tFplRsPList);
				} else {
					value.addRequestMap("backRs", str);
					value.addRequestMap("backRsList", tFplRsPList);
				}
			}
		}
	}
	
	/**
	 * �������ݣ����з��񽲽�ҳ���ʼ��
	 * @param map
	 * @return value
	 * @throws Exception
	 */
	public BeanValue initFlyServiceInfo(RequestMap requestMap)
			throws Exception {

		
//		// ��֤�û�Ȩ��
//		ReqIdentity identity = getReqIdentity(requestMap);
//		identity.setFunctionId("BM10101");//���мƻ��½�
//		SecurityStub sstub = new SecurityStub();
//		sstub.checkPermission(identity);
		BeanValue beanValue = new BeanValue();
//		
//		// ���ò�ѯ����
		this.setConditionParam(beanValue, requestMap);
//
		String planid = requestMap.getString("planid");
		planid = planid == null ? "" : planid;
		
		String operFlag = requestMap.getString("operFlag");
		beanValue.addRequestMap("operFlag", operFlag);
		
		TFpl tFpl = new TFpl();
//		 ������Ϊ��ִ�����µĲ���
		if (StringUtils.isNotEmpty(planid)) {
			
//			// ��ѯ���мƻ�
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
			
			// ���������ѯ
			SimpleDateFormat sFormat = new SimpleDateFormat("yyyy-MM-dd");
			StringBuffer sf = new StringBuffer();
			sf.append(" select distinct t.wm_desc as notes from t_weather_metar t where t.icao_code ='").append(tFpl.getAdepCode()).append("' and to_date('").append(sFormat.format(tFpl.getDates())+" "+tFpl.getSetd()).append("','yyyy-MM-dd HH24:mi:ss') between t.wm_begin_time and t.wm_end_time");
			sf.append(" union all ");
			sf.append(" select distinct t.wm_desc  as notes from t_weather_metar t where t.icao_code ='").append(tFpl.getAdesCode()).append("' and to_date('").append(sFormat.format(tFpl.getSetal())+" "+tFpl.getSeta()).append("','yyyy-MM-dd HH24:mi:ss') between t.wm_begin_time and t.wm_end_time");
			List<String[]> resultList = dao.getObjects(sf.toString(), new String[]{"notes"});
			StringBuffer notesStr = new StringBuffer();
			for(int i = 0; resultList != null && i < resultList.size(); i++){
				notesStr.append(resultList.get(i)[0]);
			}
			
			beanValue.addRequestMap("notesStr", notesStr.toString());
			
		}else{
			tFpl.setPclass("GA");
			tFpl.setRule("002");
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
		
		
		if(operFlag!= null && operFlag.trim().equals("view")){
			beanValue.setForward("planDetail");
		}else{
			beanValue.setForward("flyServiceInfo");
		}
		
		return beanValue;
	
	}
	
	/**
	 * �������ݣ���ѯ���н�������
	 * @param map
	 * @return value
	 * @throws Exception
	 */
	public BeanValue searchFlyServiceInfo(RequestMap requestMap)
			throws Exception {

		// ��֤�û�Ȩ��
		ReqIdentity identity = getReqIdentity(requestMap);
		BeanValue beanValue = new BeanValue();
		
		String adepCode = requestMap.getString("adepCode");
		String adesCode = requestMap.getString("adesCode");
		
		// Ԥ�ƿ�ʼʱ��
		String dates = requestMap.getString("dates");
		// Ԥ�ƿ�ʼʱ��
		String setd = requestMap.getString("setd");

		// Ԥ�ƽ���ʱ��
		String setal = requestMap.getString("setal");
		// Ԥ�ƿ�ʼʱ��
		String seta = requestMap.getString("seta");
		
		// �������� 1���������� 2���ش����� 3������ͨ��
		String noteType = requestMap.getString("noteType");
		StringBuffer sf = new StringBuffer();
		if(StringUtils.equals("1", noteType)){
			sf.append(" select distinct t.wm_desc as notes from t_weather_metar t where t.icao_code ='").append(adepCode).append("' and to_date('").append(dates).append(" ").append(setd).append("','yyyy-MM-dd HH24:mi:ss') between t.wm_begin_time and t.wm_end_time");
			sf.append(" union all ");
			sf.append(" select distinct t.wm_desc  as notes from t_weather_metar t where t.icao_code ='").append(adesCode).append("' and to_date('").append(setal).append(" ").append(seta).append("','yyyy-MM-dd HH24:mi:ss') between t.wm_begin_time and t.wm_end_time");
		}else if(StringUtils.equals("2", noteType)) {
			sf.append(" select distinct t.ws_desc as notes  from t_weather_sigmet t where t.icao_code ='").append(adepCode).append("' and to_date('").append(dates).append(" ").append(setd).append("','yyyy-MM-dd HH24:mi:ss') between t.ws_begin_time and t.ws_end_time");
			sf.append(" union all ");
			sf.append(" select distinct t.ws_desc as notes  from t_weather_sigmet t where t.icao_code ='").append(adesCode).append("' and to_date('").append(setal).append(" ").append(seta).append("','yyyy-MM-dd HH24:mi:ss') between t.ws_begin_time and t.ws_end_time");
		} else if(StringUtils.equals("3", noteType)) {
			sf.append(" select distinct to_char(t.n_fstime,'yyyy-MM-dd HH24:mi:SS') || case when t.n_fstime is not null and t.n_fetime is not null then '��' || to_char(t.n_fetime,'yyyy-MM-dd HH24:mi:SS') || t.n_content end as notes from t_notam t where t.n_scope ='").append(adepCode).append("' and to_date('").append(dates).append("','yyyy-MM-dd HH24:mi:ss') between t.n_fstime and t.n_fetime");
			sf.append(" union all ");
			sf.append(" select distinct to_char(t.n_fstime,'yyyy-MM-dd HH24:mi:SS') || case when t.n_fstime is not null and t.n_fetime is not null then '��' || to_char(t.n_fetime,'yyyy-MM-dd HH24:mi:SS') || t.n_content end as notes from t_notam t where t.n_scope ='").append(adesCode).append("' and to_date('").append(setal).append("','yyyy-MM-dd HH24:mi:ss') between t.n_fstime and t.n_fetime");
		}
//		System.out.println(sf.toString());
		List<String[]> resultList = dao.getObjects(sf.toString(), new String[]{"notes"});
		StringBuffer notesStr = new StringBuffer();
		for(int i = 0; resultList != null && i < resultList.size(); i++){
			notesStr.append(resultList.get(i)[0]).append(";");
		}
		
		beanValue.addRequestMap("notesStr", notesStr.toString());
		return beanValue;
	
	}
	
	/**
	 * �������ݣ�������н�������
	 * @param map
	 * @return value
	 * @throws Exception
	 */
	public BeanValue saveFlyServiceInfo(RequestMap requestMap)
			throws Exception {

		// ��֤�û�Ȩ��
		ReqIdentity identity = getReqIdentity(requestMap);
		BeanValue beanValue = new BeanValue();
		
		String fiId = requestMap.getString("fiId");
		TFsInterpret interpret = new TFsInterpret();
		if(StringUtils.isNotEmpty(fiId)) {
			interpret.setFiId(Long.parseLong(fiId));
		}
		// ���мƻ�ID
		interpret.setPlanid(Long.parseLong(requestMap.getString("planid")));
		// ��������
		interpret.setFiMetar(requestMap.getString("notes1"));
		// �ش�����
		interpret.setFiSigmet(requestMap.getString("notes2"));
		// ����ͨ��
		interpret.setFiNotam(requestMap.getString("notes3"));
		// ��ȫ��ʾ
		interpret.setFiConflict(requestMap.getString("notes4"));
		// ������
		interpret.setFiInterpreter(identity.getUserId());
		// ����ʱ��
		interpret.setFiTime(new Date());
		// ��ע
		interpret.setRemark(requestMap.getString("remark"));
		
		dao.saveOrUpdateObject(interpret);
		
		beanValue.addRequestMap("fiId", interpret.getFiId());
		return beanValue;
	}
}
