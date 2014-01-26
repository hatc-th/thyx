package com.hatc.thyx.service.flyplan.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.hatc.base.common.BeanValue;
import com.hatc.base.common.RequestMap;
import com.hatc.base.common.RollPage;
import com.hatc.base.hibernate.dao.Dao;
import com.hatc.base.utils.BeanUtil;
import com.hatc.base.utils.ConvertLang;
import com.hatc.common.businessdata.Code;
import com.hatc.common.businessdata.Site;
import com.hatc.common.businessdata.UserRole;
import com.hatc.common.contants.ProjectConstants;
import com.hatc.common.contants.ProjectItemCode;
import com.hatc.common.service.impl.ProjectManagerImpl;
import com.hatc.common.servicestub.ReqIdentity;
import com.hatc.hibernate.pojo.TFpl;
import com.hatc.hibernate.pojo.TFplActivity;
import com.hatc.hibernate.pojo.TFplCycle;
import com.hatc.hibernate.pojo.TFplRs;
import com.hatc.hibernate.pojo.TFplRsP;
import com.hatc.hibernate.pojo.TRegularRs;
import com.hatc.hibernate.pojo.TbApproveFlowRec;
import com.hatc.hibernate.pojo.TbApproveFlowRecId;
import com.hatc.hibernate.vo.GeneralSkyWayPointVO;
import com.hatc.hibernate.vo.GeneralSkyWayVO;
import com.hatc.hibernate.vo.TFplVO;
import com.hatc.thyx.service.flyplan.FlyPlanManager;

public class FlyPlanManagerImpl extends ProjectManagerImpl implements FlyPlanManager {


	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
	private Dao dao1 = null;
	private Dao szkydao = null;
	
	public Dao getSzkydao() {
		return szkydao;
	}

	public void setSzkydao(Dao szkydao) {
		this.szkydao = szkydao;
	}

	public Dao getDao1() {
		return dao1;
	}

	public void setDao1(Dao dao1) {
		this.dao1 = dao1;
	}

	/**
	 * 处理内容：通航用户删除飞行计划信息
	 * @param map
	 * @return value
	 * @throws Exception
	 */
	public BeanValue deleteFlyPlan(RequestMap requestMap) throws Exception {
		// TODO Auto-generated method stub
		
		// 验证用户权限
//		ReqIdentity identity = getReqIdentity(requestMap);
//		identity.setFunctionId("BM10101");//飞行计划新建
//		SecurityStub sstub = new SecurityStub();
//		sstub.checkPermission(identity);
		BeanValue beanValue = new BeanValue();
		
		// 设置查询条件
		this.getParamMapForList(requestMap, beanValue);
        
        // 删除飞行计划	
		String[] planIds = requestMap.getStringArray("chkbox");
		if (planIds != null && planIds.length > 0) {
			for (String planid : planIds) {
				
				dao.updateBySql("delete from t_fpl_rs_p tRsp where tRsp.rs_Id in (select rs_Id from t_fpl_rs where planid="+planid+" )");
				dao.updateBySql("delete from t_fpl_rs where planid="+planid);
				dao.updateBySql("delete from t_fpl_cycle where planid="+planid);
				dao.updateBySql("delete from t_fpl where planid="+planid);
				
//				String hql="from TFplRsP tRsp where tRsp.rsId in (select tRs.rsId from TFplRs tRs where tRs.planid="+planid+" )";
//				List tempList = dao.getObjects(hql);
//				if(tempList !=null)
//				{
//					dao.removeUpdateBatchObject(TFplRsP.class, tempList);
//				}
//				tempList = dao.getObjects("from TFplRs tRs where tRs.planid="+planid);
//				if(tempList !=null)
//				{
//					dao.removeUpdateBatchObject(TFplRs.class, tempList);
//				}
//				
//	    		tempList= dao.getObjects(" from TFplCycle cycle where cycle.planid= "+ planid);
//	    		if(tempList !=null && tempList.size()>0){
//	    			dao.removeUpdateBatchObject(TFplCycle.class,tempList);
//	    		}
//	    		dao.removeObject(TFpl.class, new Long(planid));
			}
		}
    		
//            /** 记录日志 */ 
        requestMap.addParameter(ProjectConstants.LOG_TEXT, "删除飞行计划");
        // 目标页面（更新页面）
		/* 页面初始化 */
		beanValue.setForward("searchFlyPlanList");
		return beanValue;
//
	}

	/**
	 * 处理内容：修改飞行计划状态： 报备计划，激活计划，或撤销计划。
	 * @param map
	 * @return value
	 * @throws Exception
	 */
	public BeanValue updateFlyPlanState(RequestMap requestMap) throws Exception {
		// TODO Auto-generated method stub
		
		// 验证用户权限
//		ReqIdentity identity = getReqIdentity(requestMap);
//		identity.setFunctionId("BM10101");//飞行计划新建
//		SecurityStub sstub = new SecurityStub();
//		sstub.checkPermission(identity);
		BeanValue beanValue = new BeanValue();
		
		// 设置查询条件
		this.getParamMapForList(requestMap, beanValue);
//		Employee employee = (Employee)requestMap.getObject(ProjectConstants.SESSION_USER);
		Site site = (Site) requestMap.getObject(ProjectConstants.SESSION_LOCAL);
        
		 
		String[] planIds = requestMap.getStringArray("chkbox");
		String operFlag= requestMap.getString("operFlag");
		if (planIds != null && planIds.length > 0) {
//			List<String> sqlList =new ArrayList();
			
			String remark= requestMap.getString("remark");
			
			for (String planid : planIds) {
				
				String state1="";
				String state2="";
				if(operFlag != null && operFlag.trim().equals("cancel")){  // 撤销飞行计划	
					state1="35";
					state2="035"; // 撤销
				}
				if(operFlag != null && operFlag.trim().equals("activate")){  // 激活飞行计划	
					List<String[]> stateList=dao.getObjects("select t.planstate from t_fpl t where t.planid="+planid, new String[]{"planstate"});
					if(stateList != null && stateList.size() >0){
						String tempState=stateList.get(0)[0];
						if(tempState.trim().equals("41")){
							state1="31";
						}else{
							state1="32";
						}
					}
					state2="051";  // 激活
				}
				

				dao.updateBySql("update t_fpl set planstate='"+state1+"' where planid="+planid);
				
				TbApproveFlowRec flowRec=new TbApproveFlowRec();
				TbApproveFlowRecId  flowRecId=new TbApproveFlowRecId();
				flowRecId.setSiteId("S0000");
				List<String[]> seqList=dao.getObjects("select nvl(max(t.approve_id),0)+1 as approve_id from tb_approve_flow_rec t", new String[]{"approve_id"});
				if(seqList != null && seqList.size() >0){
					flowRecId.setApproveId(new Long(seqList.get(0)[0]));
				}
				flowRec.setId(flowRecId);
				flowRec.setObjectId(planid.toString());
				flowRec.setSendTs(new Date());
				flowRec.setSendUserId("U12345678");
				flowRec.setSendRoleId("R1999");
				flowRec.setApproveState(state2);
				flowRec.setNote(remark);
				dao.saveOrUpdateObject(flowRec);
				dao1.saveOrUpdateObject(flowRec);
				
//				// 查询飞行计划的是否已提交上级。
//				List temp1=dao1.getObjects("select planid from t_fpl tf where tf.planid="+planid, new String[]{"planid"});
//				if(temp1 != null && temp1.size() > 0 ) { // 0 ，表示上级数据库中有该计划数据
//					TbApproveFlowRec flowRec1=new TbApproveFlowRec();
//					TbApproveFlowRecId  flowRecId1=new TbApproveFlowRecId();
//					flowRecId1.setSiteId(site.getSite_id());
//					List<String[]> seqList1=dao1.getObjects("select nvl(max(t.approve_id),0)+1 as approve_id from tb_approve_flow_rec t", new String[]{"approve_id"});
//					if(seqList1 != null && seqList1.size() >0){
//						flowRecId1.setApproveId(new Long(seqList1.get(0)[0]));
//					}
//					flowRec1.setId(flowRecId1);
//					flowRec1.setObjectId(planid.toString());
//					flowRec1.setSendTs(new Date());
//					flowRec1.setSendUserId(identity.getUserId());
//					flowRec1.setSendRoleId(identity.getUserRole());
//					flowRec1.setApproveState(state2);
//					dao1.saveOrUpdateObject(flowRec1);
//					
//				}
				dao1.updateBySql("update thyx1.t_fpl t set t.planstate='"+state1+"' where t.planid="+planid);
			
			}
			
//			beanValue.addRequestMap("upList", sqlList);
		}
    		
//            /** 记录日志 */ 
        requestMap.addParameter(ProjectConstants.LOG_TEXT, "撤销飞行计划");
        // 目标页面（更新页面）
		/* 页面初始化 */
		beanValue.setForward("searchFlyPlanList");
		return beanValue;
//
	}

	
	/**
	 * 处理内容：通航用户初始化飞行计划信息
	 * @param map
	 * @return value
	 * @throws Exception
	 */
	public BeanValue initFlyPlanBaseInfo(RequestMap requestMap)
			throws Exception {

		
//		// 验证用户权限
//		ReqIdentity identity = getReqIdentity(requestMap);
//		identity.setFunctionId("BM10101");//飞行计划新建
//		SecurityStub sstub = new SecurityStub();
//		sstub.checkPermission(identity);
		BeanValue beanValue = new BeanValue();
//		
//		// 设置查询条件
		this.getParamMapForList(requestMap, beanValue);
//
		String planid = requestMap.getString("planid");
		planid = planid == null ? "" : planid;
		
		String operFlag = requestMap.getString("operFlag");
		beanValue.addRequestMap("operFlag", operFlag);
		
		String[] planIds = requestMap.getStringArray("chkbox");
		
		if (StringUtils.isEmpty(planid)) {
			if (planIds != null && planIds.length > 0 && !StringUtils.equals(operFlag, "new")) {
				planid=planIds[0];
			}
		}
		
		String copy = requestMap.getString("copy");
		if(copy!= null && copy.trim().equals("copy")){
			beanValue.addRequestMap("copy", copy);
		}
		
		TFpl tFpl = new TFpl();
//		 主键不为空执行以下的操作
		if (StringUtils.isNotEmpty(planid)) {
			
//			// 查询试验完成情况公共信息【试验实施方案完成情况记录】
			tFpl = (TFpl) dao.getObject(TFpl.class, new Long(planid.trim()));
			TFplCycle tFplCycle=null;
			String hql="from TFplCycle cycle where cycle.planid="+planid.trim();
			List list=dao.getObjects(hql);
			if(list !=null && list.size() >0){
				tFplCycle=(TFplCycle) list.get(0);
			}
			
			beanValue.addRequestMap("tFplCycle", tFplCycle);
			
			// 查询相关的航线
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
		
		
		if(operFlag!= null && operFlag.trim().equals("view")){
			beanValue.setForward("viewFlyPlanInfo");
		}else{
			beanValue.setForward("editFlyPlanInfo");
		}
		
		return beanValue;
	
	}

	
	/**
	 * 处理内容：获取当前计划顺序号。 从1 至 9999 ，循环使用。
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
	 * 处理内容：拷贝主周期计划的航线至子计划
	 * @param hashMap 待拷贝的计划航线
	 * @param newPlanId 生成的新计划的ID 
	 * @throws Exception
	 */
	private void savePlanRsByNewSub(HashMap hashMap,Long planid)throws Exception {
		
		TFplRs fplRs=(TFplRs) hashMap.get("subtMainRs");
		if(fplRs !=null){
			TFplRs fplRs1=new TFplRs();
			BeanUtil.convertObject(fplRs1, fplRs);
			fplRs1.setPlanid(planid);
			dao.saveObject(fplRs1);
			List<TFplRsP> list=(List<TFplRsP>) hashMap.get("subtMainRsP");
			if(list !=null && list.size()>0){
				for(TFplRsP rsP:list){
					TFplRsP rsP1=new TFplRsP();
					BeanUtil.convertObject(rsP1, rsP);
					rsP1.setRsId(fplRs1.getRsId());
					dao.saveObject(rsP1);
				}
			}
		}
		
		TFplRs fplBskRs=(TFplRs) hashMap.get("subtBakRs");
		if(fplBskRs !=null){
			TFplRs fplRs2=new TFplRs();
			BeanUtil.convertObject(fplRs2, fplBskRs);
			fplRs2.setPlanid(planid);
			dao.saveObject(fplRs2);
			List<TFplRsP> listBak=(List<TFplRsP>) hashMap.get("subtBakRsP");
			if(listBak !=null && listBak.size()>0){
				for(TFplRsP rsPBak:listBak){
					TFplRsP rsP2=new TFplRsP();
					BeanUtil.convertObject(rsP2, rsPBak);
					rsP2.setRsId(fplRs2.getRsId());
					dao.saveObject(rsP2);
				}
			}
		}
	}
	

	/**
	 * 处理内容：通航用户保存飞行计划起飞、目的机场
	 * @param map
	 * @return value
	 * @throws Exception
	 */
	public BeanValue saveFlyPlanAirPortInfo(RequestMap requestMap)
			throws Exception {

		// 验证用户权限
		ReqIdentity identity = getReqIdentity(requestMap);
		identity.setFunctionId("BM10101");//飞行计划新建
//		SecurityStub sstub = new SecurityStub();
//		sstub.checkPermission(identity);
		BeanValue beanValue = new BeanValue();
		
		
		TFpl tFpl =(TFpl) dao.getObject(TFpl.class, new Long(requestMap.getString("planid")));	
//		BeanUtil.convertObject(tFpl, requestMap);  // 从数据库中查询到的该计划后调用该方法不能清空数据项。。。。
		tFpl.setAltn1(requestMap.getString("altn1"));
		tFpl.setAltn2(requestMap.getString("altn2"));
		tFpl.setAltn3(requestMap.getString("altn3"));
		tFpl.setAdep(requestMap.getString("adep"));
		tFpl.setAdes(requestMap.getString("ades"));
		
//		TFpl tFpl = (TFpl) BeanUtil.convertObject(TFpl.class, requestMap);
		
		tFpl.setPlanstate("11");
		tFpl.setArchiveState("0");
		tFpl.setActivate(new Long("0"));
		
//			// 保存飞行计划起飞、目的机场信息
		dao.updateObject(tFpl)	;
		
		
//		如果起飞、目的机场发生变化，则删除计划下的已有航线信息避免不一致
		String hql="from TFplRsP tRsp where tRsp.rsId in (select tRs.rsId from TFplRs tRs where tRs.planid="+tFpl.getPlanid()+" and (tRs.rsDep !="+tFpl.getAdep()+" or tRs.rsDes !="+tFpl.getAdes()+") )";
		List tempList = dao.getObjects(hql);
		if(tempList !=null)
		{
			dao.removeUpdateBatchObject(TFplRsP.class, tempList);
		}
		tempList = dao.getObjects("from TFplRs tRs where tRs.planid="+tFpl.getPlanid()+" and (tRs.rsDep !="+tFpl.getAdep()+" or tRs.rsDes !="+tFpl.getAdes()+")");
		if(tempList !=null)
		{
			dao.removeUpdateBatchObject(TFplRs.class, tempList);
		}
	
		  /** 记录日志 */ 
		requestMap.addParameter(ProjectConstants.LOG_TEXT, "保存飞行计划起飞、目的机场信息");
      
		
        // 设置回退参数
        beanValue.addRequestMap("boolstr", "<response><reValue>true</reValue></response>");
        
		return beanValue;
	
	}

	/**
	 * 处理内容：通航用户保存飞行航线等信息
	 * @param map
	 * @return value
	 * @throws Exception
	 */
	public BeanValue saveFlyPlanInfo(RequestMap requestMap)
			throws Exception {
		
		// 验证用户权限
		ReqIdentity identity = getReqIdentity(requestMap);
		
		BeanValue beanValue = new BeanValue();
		
		this.getParamMapForList(requestMap, beanValue);
		
		TFpl tFpl = (TFpl) BeanUtil.convertObject(TFpl.class, requestMap);
		
		String copy = requestMap.getString("copy");
		if(StringUtils.isEmpty(requestMap.getString("planid")) || ( copy != null && copy.trim().equals("copy") ) ){ // 新建计划、复制计划
			
			tFpl.setFplCtime(new Date());
			
			tFpl.setPlanUser("U10001205");
//			
			// 根据序列生成问题报告编号
			if( (tFpl.getFPlanCode()==null || tFpl.getFPlanCode().trim().equals("")) && tFpl.getDates() != null){  // 单次飞行计划生成计划编号
				String codeSeq = this.getPlanCodeSeq();
				tFpl.setFPlanCode(dateFormat.format(tFpl.getDates())+codeSeq);	// 计划编号
			}
			tFpl.setPlanid(null);
			
		}
		
		tFpl.setArchiveState("0"); // 归档状态
		tFpl.setActivate(new Long("0")); // 是否可激活
		
//		计划名称
		if(StringUtils.isEmpty(tFpl.getFPlanName())){
			tFpl.setFPlanName(tFpl.getAdepName()+"_"+tFpl.getAdesName()+"_"+dateFormat.format(tFpl.getDates()));
		}
//			
//		保存飞行计划的基本信息
		dao.saveOrUpdateObject(tFpl);
		
		
//		保存周期性计划的周期信息
//		if(requestMap.getString("cyLengh") !=null && !requestMap.getString("cyLengh").trim().equals("")){
//			TFplCycle tFplCycle=null;
//			if(requestMap.getString("cyId") ==null || requestMap.getString("cyId").trim().equals("") || ( copy != null && copy.trim().equals("copy"))){
//				tFplCycle=new TFplCycle();
//				tFplCycle.setFplCtime(new Date());
//			}else{
//				tFplCycle=(TFplCycle) dao.getObject(TFplCycle.class, new Long(requestMap.getString("cyId")));	
//			}
//			
//			BeanUtil.convertObject(tFplCycle, requestMap);
////			复制周期计划，清空ID
//			if( copy != null && copy.trim().equals("copy")){
//				tFplCycle.setCyId(null);
//			}
//			String[] weekdays=requestMap.getStringArray("weekdays");
//			
//			if(tFpl.getFPlanCode()==null || tFpl.getFPlanCode().trim().equals("")){
//				
//				// 根据序列生成问题报告编号
//				String codeSeq = this.getPlanCodeSeq();
//				if(tFplCycle.getCyUnit().trim().equals("D")){  // 周期单位是天 
//					tFpl.setFPlanCode(dateFormat.format(tFplCycle.getCyBtime())+codeSeq);	// 计划编号
//				}else{  // 以周为周期的重复性计划。
//					Calendar c1=Calendar.getInstance();
//					if(weekdays != null && weekdays.length>0){
//						// 以周为周期的重复性计划，设置第一个实施日期为计划编号日期
//						for( c1.setTime(tFplCycle.getCyBtime());tFpl.getFPlanCode()==null || tFpl.getFPlanCode().trim().equals("");c1.add(Calendar.DATE, 1)){
//							String week=c1.get(Calendar.DAY_OF_WEEK)+"";
//							for(int i=0;i<weekdays.length;i++){
//								if(week.trim().equals(weekdays[i])){
//									tFpl.setFPlanCode(dateFormat.format(c1.getTime())+codeSeq);	// 计划编号
//									dao.updateObject(tFpl);
//									break;
//								}
//							}
//						}
//					}
//					
//				}
//			}
//			
//			StringBuffer weekBuffer=new StringBuffer();
//			// 以周为周期的重复性计划，设置一周中的哪几天
//			if(weekdays != null && weekdays.length>0){
//				for(int i=0;i<weekdays.length;i++){
//					weekBuffer.append(weekdays[i]+":");
//				}
//				tFplCycle.setCyWeekday(weekBuffer.substring(0, weekBuffer.length()-1));
//			}else{
//				tFplCycle.setCyWeekday("");
//			}
//			
//			
//			tFplCycle.setPlanid(tFpl.getPlanid());
//			dao.saveOrUpdateObject(tFplCycle);
//		}
		
//		保存飞行计划的航线信息
		List tempList =null;
		StringBuffer buffer=new StringBuffer();
		String[] rsPIdMain = requestMap.getStringArray("rsPIdMain");
		String[] rsPIdBak = requestMap.getStringArray("rsPIdBak");
		if(rsPIdMain !=null && rsPIdMain.length>0){
			for (int i=0;i<rsPIdMain.length;i++){
				if(rsPIdMain[i]!=null && !rsPIdMain[i].trim().equals("")){
					buffer.append(rsPIdMain[i]+",");
				}
			}
		}
		if(rsPIdBak !=null && rsPIdBak.length>0){
			for (int i=0;i<rsPIdBak.length;i++){
				if(rsPIdBak[i]!=null && !rsPIdBak[i].trim().equals("")){
					buffer.append(rsPIdBak[i]+",");
				}
			}
		}
		
		String rsIdMain =requestMap.getString("rsIdMain");
		String rsIdBak =requestMap.getString("rsIdBak");
		String hql="from TFplRsP tRsp where tRsp.rsId in (select tRs.rsId from TFplRs tRs where tRs.planid="+tFpl.getPlanid()+")";
		if(buffer !=null && buffer.length()>0){
			hql=hql+" and tRsp.rsPId not in("+buffer.substring(0, buffer.length()-1)+")"; // 对保留的已有节点不重新生成ID
		}
		tempList = dao.getObjects(hql);
		if(tempList !=null)
		{
			dao.removeUpdateBatchObject(TFplRsP.class, tempList);
		}
		
		String[] rsPNums = requestMap.getStringArray("rsPNumMain");
		String[] rsPNumBak = requestMap.getStringArray("rsPNumBak");
		String hql1="";
		if(StringUtils.isNotEmpty(rsIdMain) && rsPNums != null && rsPNums.length > 0){
			hql1=" and tRs.rsId !="+rsIdMain;
		}
		if(StringUtils.isNotEmpty(rsIdBak) && rsPNumBak != null && rsPNumBak.length > 0){
			hql1= hql1+" and tRs.rsId !="+rsIdBak;
		}
		tempList = dao.getObjects("from TFplRs tRs where tRs.planid="+tFpl.getPlanid()+hql1);   // 对已有航线不重新生成ID
		if(tempList !=null)
		{
			dao.removeUpdateBatchObject(TFplRs.class, tempList);
		}
		
		String lineFlag = requestMap.getString("lineFlag");
		
		
		String[] rsPNameMain = requestMap.getStringArray("rsPNameMain");
		String[] rs_PPosMain = requestMap.getStringArray("rs_PPosMain");
		String[] rsPTypeMain = requestMap.getStringArray("rsPTypeMain");
		String[] rsPCodeMain = requestMap.getStringArray("rsPCodeMain");
		
		
		HashMap hashMap=new HashMap(); 
		
		if (rsPNums != null && rsPNums.length > 0) {
			
			
			TFplRs tRs=null;
			if(rsIdMain == null || rsIdMain.trim().equals("")){
				tRs=new TFplRs();
				tRs.setCreateDate(new Date());
				tRs.setPlanid(tFpl.getPlanid());
			}else{
				tRs= (TFplRs) dao.getObject(TFplRs.class, new Long(rsIdMain));
				tRs.setUpdateDate(new Date());
			}
			
			if(lineFlag.trim().equals("1")){
				tRs.setRsType("1");
			}else{
				tRs.setRsType("0");
			}
			
//			设置主备航线标记
			
			tRs.setRsDep(requestMap.getString("adep"));
			tRs.setRsDes(requestMap.getString("ades"));
			
//			if(tFpl.getPlanstate()!=null && tFpl.getPlanstate().trim().equals("12") && tFpl.getPclass().trim().equals("GAR")){ // 如果是提交周期性计划
//				TFplRs subtMainRs=new TFplRs();
//				BeanUtil.convertObject(subtMainRs,tRs);
//				hashMap.put("subtMainRs", subtMainRs);
//			}
			
			dao.saveOrUpdateObject(tRs);
			beanValue.addRequestMap("tRsMainId", tRs.getRsId().toString());
			List subtMainRsP=new ArrayList();
			for (int i=0;i<rsPNums.length;i++){
				
				TFplRsP tRsP=null;
				if(rsPIdMain[i] != null && !rsPIdMain[i].trim().equals("")){
					tRsP=(TFplRsP) dao.getObject(TFplRsP.class, new Long(rsPIdMain[i]));
				}else{
					tRsP=new TFplRsP();
				}
				
				tRsP.setRsId(tRs.getRsId());
				tRsP.setRsPName(rsPNameMain[i]);
				tRsP.setRsPNum(new Long(rsPNums[i]));
				tRsP.setRsPPos(rs_PPosMain[i]);
				tRsP.setRsPType(new Long(rsPTypeMain[i]));
				tRsP.setRsPCode(rsPCodeMain[i]);
				
//				if(tFpl.getPlanstate()!=null && tFpl.getPlanstate().trim().equals("12") && tFpl.getPclass().trim().equals("GAR")){ // 如果是提交周期性计划
//					TFplRsP subtRsP=new TFplRsP();
//					BeanUtil.convertObject(subtRsP,tRsP);
//					subtMainRsP.add(subtRsP);
//				}
				
				dao.saveOrUpdateObject(tRsP);
			}
			
//			if(tFpl.getPlanstate()!=null && tFpl.getPlanstate().trim().equals("12") && tFpl.getPclass().trim().equals("GAR")){ 
//				// 如果是提交周期性计划
//				hashMap.put("subtMainRsP", subtMainRsP);
//			}
			
			
		}
		
		
		
		String[] rsPNameBak = requestMap.getStringArray("rsPNameBak");
		String[] rs_PPosBak = requestMap.getStringArray("rs_PPosBak");
		String[] rsPCodeBak = requestMap.getStringArray("rsPCodeBak");
		String[] rsPTypeBak = requestMap.getStringArray("rsPTypeBak");
		if (rsPNumBak != null && rsPNumBak.length > 0) {
			
			
			TFplRs tRs1=null;
			if(rsIdBak == null || rsIdBak.trim().equals("")){
				tRs1=new TFplRs();
				tRs1.setCreateDate(new Date());
				tRs1.setPlanid(tFpl.getPlanid());
			}else{
				tRs1= (TFplRs) dao.getObject(TFplRs.class, new Long(rsIdBak));
				tRs1.setUpdateDate(new Date());
			}
			
			
//			tRs1.setRsCode(requestMap.getString("rsCodeBak"));
//			tRs1.setRsName(requestMap.getString("rsNameBak"));
			
			if(lineFlag.trim().equals("1")){
				tRs1.setRsType("0");
			}else{
				tRs1.setRsType("1");
			}
			
			tRs1.setRsDep(requestMap.getString("adep"));
			tRs1.setRsDes(requestMap.getString("ades"));
			
//			if(tFpl.getPlanstate()!=null && tFpl.getPlanstate().trim().equals("12") && tFpl.getPclass().trim().equals("GAR")){ // 如果是提交周期性计划
//				TFplRs subtBakRs=new TFplRs();
//				BeanUtil.convertObject(subtBakRs,tRs1);
//				hashMap.put("subtBakRs", subtBakRs);
//			}
			
			dao.saveOrUpdateObject(tRs1);
			beanValue.addRequestMap("tRsBakId", tRs1.getRsId().toString());
			List subtBakRsP=new ArrayList();
			for (int i=0;i<rsPNumBak.length;i++){
				
				TFplRsP tRsP1=null;
				if(rsPIdBak[i] != null && !rsPIdBak[i].trim().equals("")){
					tRsP1=(TFplRsP) dao.getObject(TFplRsP.class, new Long(rsPIdBak[i]));
				}else{
					tRsP1=new TFplRsP();
				}
				
				tRsP1.setRsId(tRs1.getRsId());
				tRsP1.setRsPName(rsPNameBak[i]);
				tRsP1.setRsPNum(new Long(rsPNumBak[i]));
				tRsP1.setRsPPos(rs_PPosBak[i]);
				tRsP1.setRsPCode(rsPCodeBak[i]);
				tRsP1.setRsPType(new Long(rsPTypeBak[i]));
				
//				if(tFpl.getPlanstate()!=null && tFpl.getPlanstate().trim().equals("12") && tFpl.getPclass().trim().equals("GAR")){ // 如果是提交周期性计划
//					TFplRsP subtRsP=new TFplRsP();
//					BeanUtil.convertObject(subtRsP,tRsP1);
//					subtBakRsP.add(subtRsP);
//				}
				
				dao.saveOrUpdateObject(tRsP1);
			}
			
//			if(tFpl.getPlanstate()!=null && tFpl.getPlanstate().trim().equals("12") && tFpl.getPclass().trim().equals("GAR")){
//				// 如果是提交周期性计划
//				hashMap.put("subtBakRsP", subtBakRsP);
//			}
		}
		List<String> sqlList =new ArrayList();
		
//		  修改计划时，插入对审批流程的处理 。
		if( StringUtils.isEmpty(copy) && StringUtils.isNotEmpty(requestMap.getString("planid")) ){
//			修改计划时，将上级数据库状态设置为未申请状态。
			
			sqlList.add("update thyx1.t_fpl t set t.planstate='11' where t.planid="+tFpl.getPlanid());
//			Site site = (Site) requestMap.getObject(ProjectConstants.SESSION_LOCAL);
			this.savePlanReAuidt(tFpl.getPlanid(), null, "S0000"); // 中止流程。
//			dao.updateObject(tFpl);
		}
		
		if(tFpl.getPlanstate()!=null && tFpl.getPlanstate().trim().equals("12")){
			
//			TODO 提交计划，此处调用接口函数确认是否为报备计划。暂定为偶数编号为报备计划，奇数计划为待审批计划
			int mi=1;
			if(StringUtils.isNotEmpty(tFpl.getFPlanCode())){
				mi=Integer.parseInt(tFpl.getFPlanCode().substring(8, 12))%2;
			}
			if(mi==0){ // 报备计划
				tFpl.setPlanstate("31");
				dao.updateObject(tFpl);
				
//				 插入上级数据库已报备计划：包括提交的单次报备计划或重复报备计划的主计划
				sqlList.add("delete from thyx1.t_fpl_rs_p t where exists (select t1.RS_ID from thyx1.t_fpl_rs t1 where t1.planid="+tFpl.getPlanid()+" and t.RS_ID=t1.rs_id)");
				sqlList.add("delete from thyx1.t_fpl_rs where planid="+tFpl.getPlanid());
				sqlList.add("delete from thyx1.t_fpl_cycle where planid="+tFpl.getPlanid());
				sqlList.add("delete from thyx1.t_fpl where planid="+tFpl.getPlanid());
				sqlList.add("insert into thyx1.t_fpl select * from thyx.t_fpl t where t.planid="+tFpl.getPlanid());
				sqlList.add("insert into thyx1.t_fpl_cycle select * from thyx.t_fpl_cycle t where t.planid="+tFpl.getPlanid());
				sqlList.add("insert into thyx1.t_fpl_rs select * from thyx.t_fpl_rs t where t.planid="+tFpl.getPlanid());
				sqlList.add("insert into thyx1.t_fpl_rs_p select * from thyx.t_fpl_rs_p t where exists (select t1.RS_ID from thyx.t_fpl_rs t1 where t1.planid="+tFpl.getPlanid()+" and t.RS_ID=t1.rs_id )");

				
//				if(tFpl.getPclass().trim().equals("GAR")){ //  周期性报备计划
//					
//					TFplCycle tFplCycle=null;
//					String tempHql="from TFplCycle cycle where cycle.planid="+tFpl.getPlanid();
//					List list=dao.getObjects(tempHql);
//					
//					 // 生成周期计划子计划
//					if(list !=null && list.size() >0){
//						tFplCycle=(TFplCycle) list.get(0);
//						Calendar c1=Calendar.getInstance();
//						Date date1=tFplCycle.getCyBtime();
//						
//						if(tFplCycle.getCyUnit().trim().equals("D")){ 
//							// 周期单位是天 
//							for (c1.setTime(date1);c1.getTime().compareTo(tFplCycle.getCyEtime())<=0;c1.add(Calendar.DATE, tFplCycle.getCyLengh().intValue())){
//								String codeSeq = this.getPlanCodeSeq();
//								TFpl tempFpl =new TFpl();
//								BeanUtil.convertObject(tempFpl,tFpl);
//								tempFpl.setPlanid(null);
//								tempFpl.setDates(c1.getTime());
//								tempFpl.setFPlanName(tFpl.getFPlanName()+"-"+dateFormat.format(tempFpl.getDates()));
//								tempFpl.setFPlanCode(dateFormat.format(tempFpl.getDates())+codeSeq);
//								tempFpl.setPlanstate("31");
//								tempFpl.setPclass("GA");
//								tempFpl.setFplCtime(new Date());
//								tempFpl.setFPlanid(tFpl.getPlanid());
//								dao.saveOrUpdateObject(tempFpl);
//								this.savePlanRsByNewSub(hashMap,tempFpl.getPlanid());
//								
//							}
//							
//						}else{  // 周期单位是周
//							
//							String[] weekdays=tFplCycle.getCyWeekday().split(":");
//							
//							if(weekdays != null && weekdays.length>0){
//								Date[] dFlag=new Date[weekdays.length];
//								c1.setTime(tFplCycle.getCyBtime());
//								int m=0;
//								for( int k1=0; k1<7;k1++){  // 循环七次，获取到第一个周期内的每一天，m 计数排序，使数组值日期小到大。
//									for(int k=0;k<weekdays.length;k++){
//										String week=c1.get(Calendar.DAY_OF_WEEK)+"";
//										if(week.trim().equals(weekdays[k])){
//											dFlag[m]=c1.getTime();
//											m++;
//											break;  // 如果找到对应的日期。
//										}
//									}
//									c1.add(Calendar.DATE, 1);	
//								}
//								
//							// 以周为周期的重复性计划，设置第一个实施日期为计划编号日期
//								c1.setTime(date1);
//								for(int k2=0;c1.getTime().compareTo(tFplCycle.getCyEtime())<=0;k2++){
//	//									
//									for(int k=0;k<weekdays.length;k++){ // 生成每一个周期内的计划
//										c1.setTime(dFlag[k]);
//										c1.add(Calendar.DATE, k2*7*tFplCycle.getCyLengh().intValue());	// 生成该周期内德单个计划的计划日期
//										if(c1.getTime().compareTo(tFplCycle.getCyEtime())>0){
//											break;
//										}
//										
//										String codeSeq = this.getPlanCodeSeq();
//										TFpl tempFpl =new TFpl();
//										BeanUtil.convertObject(tempFpl,tFpl);
//										tempFpl.setPlanid(null);										
//										tempFpl.setDates(c1.getTime());
//										tempFpl.setFPlanName(tFpl.getFPlanName()+"-"+dateFormat.format(tempFpl.getDates()));
//										tempFpl.setFPlanCode(dateFormat.format(tempFpl.getDates())+codeSeq);
//										tempFpl.setPlanstate("31");
//										tempFpl.setPclass("GA");
//										tempFpl.setFplCtime(new Date());
//										tempFpl.setFPlanid(tFpl.getPlanid());
//										dao.saveOrUpdateObject(tempFpl);
//										this.savePlanRsByNewSub(hashMap,tempFpl.getPlanid());
//										
//									}
//								}
//							}
//						}
//					}
//					
////					 插入上级数据库重复报备计划的子计划
//					sqlList.add("insert into thyx1.t_fpl select * from thyx.t_fpl t where t.f_planid="+tFpl.getPlanid());
//					sqlList.add("insert into thyx1.t_fpl_rs select * from thyx.t_fpl_rs t where exists ( select t2.planid from thyx.t_fpl t2 where t2.f_planid="+tFpl.getPlanid()+" and t.planid=t2.planid)");
//					sqlList.add("insert into thyx1.t_fpl_rs_p select * from thyx.t_fpl_rs_p t where exists (select t1.rs_id from thyx.t_fpl_rs t1 where  exists (select t2.planid from thyx.t_fpl t2 where t2.f_planid="+tFpl.getPlanid()+" and t1.planid=t2.planid ) and t.rs_id=t1.rs_id )");
//
//					
//				}
				
				 
			}else{  // 非报备计划的提交

				String tempUser=null;
				String tempRole=null;
				//			  插入对审批流程的处理 。
				TbApproveFlowRec temp=this.savePlanReAuidt(tFpl.getPlanid(), null, "S0000"); // 启动新流程。
				if(temp != null){
					tempUser=temp.getApproveUserId();
					tempRole=temp.getApproveRoleId();
				}
				
				TbApproveFlowRec flowRec=new TbApproveFlowRec();
				TbApproveFlowRecId  flowRecId=new TbApproveFlowRecId();
				flowRecId.setSiteId("S0000");
				List<String[]> seqList=dao.getObjects("select nvl(max(t.approve_id),0)+1 as approve_id from tb_approve_flow_rec t", new String[]{"approve_id"});
				if(seqList != null && seqList.size() >0){
					flowRecId.setApproveId(new Long(seqList.get(0)[0]));
				}
				flowRec.setId(flowRecId);
				flowRec.setObjectId(tFpl.getPlanid().toString());
				flowRec.setSendTs(new Date());
				
				
				flowRec.setSendUserId(identity.getUserId());
				flowRec.setSendRoleId(identity.getUserRole());
				
				flowRec.setApproveResult("0");
				flowRec.setApproveUserId(tempUser);
				flowRec.setApproveRoleId(tempRole);
				dao.saveOrUpdateObject(flowRec);
				
			}

			beanValue.addRequestMap("upList", sqlList);
		}
		
//		如果设置生成仿真数据，则需调用保存仿真数据。
		if( tFpl.getCreateImitate() != null && tFpl.getCreateImitate().toString().equals("1")){ // 保存仿真数据，并且为非周期性计划。
			this.saveSzkyFlyPlan(requestMap , tFpl ,beanValue);
		}
			
		  /** 记录日志 */ 
		requestMap.addParameter(ProjectConstants.LOG_TEXT, "保存飞行计划信息");
		beanValue.addRequestMap("tFpl", tFpl);
		beanValue.addRequestMap("planid", tFpl.getPlanid().toString());
		beanValue.addRequestMap("FPlanCode", tFpl.getFPlanCode());
		beanValue.addRequestMap("FPlanName", tFpl.getFPlanName());
		
		return beanValue;
	
	}
	
//	在修改或提交飞行计划时，进行插入中止计划流程的数据。
	private TbApproveFlowRec savePlanReAuidt(Long planid,ReqIdentity identity,String site_id) throws Exception {
		
//		  插入对审批流程的处理 。
		
		String hql="from TbApproveFlowRec flow where flow.objectId ='"+planid+"' order by flow.sendTs,flow.id.approveId";
		
		List tempList = dao.getObjects(hql);
		if(tempList !=null && tempList.size() >0){
			
			TbApproveFlowRec temp=(TbApproveFlowRec) tempList.get(tempList.size()-1);
			
//			TODO
			if( StringUtils.isEmpty(temp.getApproveState()) ){ // 尚未审批 ，删除已插入的审批数据。
				
				dao.removeObject(TbApproveFlowRec.class, temp.getId());
				return null;
				
			}else if(temp.getApproveState() != null && !temp.getApproveState().trim().equals("32") ){ // 有审批流程并且审批流程未结束，则插入流程中止数据。
				
				
				TbApproveFlowRec temp1=(TbApproveFlowRec) tempList.get(0);
				
				if(!temp.getApproveState().trim().equals("40") && !temp.getApproveState().trim().equals("051") ){ // 插入一条终止流程数据
					TbApproveFlowRec flowRec=new TbApproveFlowRec();
					TbApproveFlowRecId  flowRecId=new TbApproveFlowRecId();
					flowRecId.setSiteId(site_id);
					List<String[]> seqList=dao.getObjects("select nvl(max(t.approve_id),0)+1 as approve_id from tb_approve_flow_rec t", new String[]{"approve_id"});
					if(seqList != null && seqList.size() >0){
						flowRecId.setApproveId(new Long(seqList.get(0)[0]));
					}
					flowRec.setId(flowRecId);
					
					flowRec.setObjectId(planid.toString());
					flowRec.setSendTs(new Date());
					flowRec.setSendUserId(identity.getUserId());
					flowRec.setSendRoleId(identity.getUserRole());
					flowRec.setApproveState("40");
					
					dao.saveOrUpdateObject(flowRec);
					dao1.saveOrUpdateObject(flowRec);
				}
				
				return temp1;
				
				
				
			}
		}
		return null;
		
	}
	
	/**
	 * 处理内容：通航用户保存常用航线
	 * @param map
	 * @return value
	 * @throws Exception
	 */
	public BeanValue saveGeneralSkyWay(RequestMap requestMap)
			throws Exception {
//
		// 验证用户权限
		ReqIdentity identity = getReqIdentity(requestMap);
////		identity.setFunctionId("BM10101");//飞行计划新建
////		SecurityStub sstub = new SecurityStub();
////		sstub.checkPermission(identity);
		
		BeanValue beanValue = new BeanValue();
//		
		String skyFlag =requestMap.getString("skyFlag");
		String rrId="";
		
		
		List tempList =null;
		
		if(skyFlag !=null && skyFlag.trim().equals("1")){ // 对应的默认的主航线层航线为常用航线
			String rrIdMain =requestMap.getString("rsIdGeneralMain");
			
			if (StringUtils.isNotEmpty(rrIdMain)) {
				String hql="from TFplRsP tRsp where tRsp.rrId ="+rrIdMain.trim();
				tempList = dao.getObjects(hql);
				
			}
			if(tempList !=null)
			{
				dao.removeUpdateBatchObject(TFplRsP.class, tempList);
			}
			
			
			String[] rsPNums = requestMap.getStringArray("rsPNumMain");
			String[] rsPNameMain = requestMap.getStringArray("rsPNameMain");
			String[] rs_PPosMain = requestMap.getStringArray("rs_PPosMain");
//			String[] rs_PPos2Main = requestMap.getStringArray("rs_PPos2Main");
			String[] rsPTypeMain = requestMap.getStringArray("rsPTypeMain");
			String[] rsPCodeMain = requestMap.getStringArray("rsPCodeMain");
			if (rsPNums != null && rsPNums.length > 0) {
				
				TRegularRs tRs=null;
				if(rrIdMain == null || rrIdMain.trim().equals("") ){
					tRs=new TRegularRs();
					tRs.setCreateDate(new Date());
				}else{
					tRs= (TRegularRs) dao.getObject(TRegularRs.class, new Long(rrIdMain));
					tRs.setUpdateDate(new Date());
				}
				
				tRs.setRrCode(requestMap.getString("rsCodeMain"));
				tRs.setRrName(requestMap.getString("rsNameMain"));
				tRs.setRrUser(identity.getUserId());
				tRs.setRrDep(requestMap.getString("adep"));
				tRs.setRrDes(requestMap.getString("ades"));
				
				dao.saveOrUpdateObject(tRs);
				for (int i=0;i<rsPNums.length;i++){
					
					TFplRsP tRsP=new TFplRsP();
					tRsP.setRrId(tRs.getRrId());
					tRsP.setRsPName(rsPNameMain[i]);
					tRsP.setRsPNum(new Long(rsPNums[i]));
					tRsP.setRsPPos(rs_PPosMain[i]);
					tRsP.setRsPCode(rsPCodeMain[i]);
					tRsP.setRsPType(new Long(rsPTypeMain[i]));
					
					dao.saveOrUpdateObject(tRsP);
				}
				rrId=tRs.getRrId()+"";
				
			}
			
		}else{
			 // 对应的默认的备航线层航线为常用航线
			String rrIdBak =requestMap.getString("rsIdGeneralBak");
			if (StringUtils.isNotEmpty(rrIdBak)) {
				String hql="from TFplRsP tRsp where tRsp.rrId ="+rrIdBak.trim();
				tempList = dao.getObjects(hql);
			}
			if(tempList !=null)
			{
				dao.removeUpdateBatchObject(TFplRsP.class, tempList);
			}
			
			String[] rsPNumBak = requestMap.getStringArray("rsPNumBak");
			String[] rsPNameBak = requestMap.getStringArray("rsPNameBak");
			String[] rs_PPosBak = requestMap.getStringArray("rs_PPosBak");
//			String[] rs_PPos2Bak = requestMap.getStringArray("rs_PPos2Bak");
			String[] rsPTypeBak = requestMap.getStringArray("rsPTypeBak");
			String[] rsPCodeBak = requestMap.getStringArray("rsPCodeBak");
			
			
			if (rsPNumBak != null && rsPNumBak.length > 0) {
				
				TRegularRs tRs=null;
				if(rrIdBak == null || rrIdBak.trim().equals("") ){
					tRs=new TRegularRs();
					tRs.setCreateDate(new Date());
				}else{
					tRs= (TRegularRs) dao.getObject(TRegularRs.class, new Long(rrIdBak));
					tRs.setUpdateDate(new Date());
				}
				
				tRs.setRrCode(requestMap.getString("rsCodeBak"));
				tRs.setRrName(requestMap.getString("rsNameBak"));
				tRs.setRrDep(requestMap.getString("adep"));
				tRs.setRrDes(requestMap.getString("ades"));
				tRs.setRrUser(identity.getUserId());
				
				dao.saveOrUpdateObject(tRs);
				for (int i=0;i<rsPNumBak.length;i++){
					
					TFplRsP tRsP=new TFplRsP();
					tRsP.setRrId(tRs.getRrId());
					tRsP.setRsPName(rsPNameBak[i]);
					tRsP.setRsPNum(new Long(rsPNumBak[i]));
					tRsP.setRsPPos(rs_PPosBak[i]);
					tRsP.setRsPCode(rsPCodeBak[i]);
					tRsP.setRsPType(new Long(rsPTypeBak[i]));
					
					dao.saveOrUpdateObject(tRsP);
				}
				rrId=tRs.getRrId()+"";
			}
		
		}
//			
		  /** 记录日志 */ 
		requestMap.addParameter(ProjectConstants.LOG_TEXT, "保存常规航线信息");
		
		 // 设置回退参数
        beanValue.addRequestMap("skyFlag", skyFlag);
        beanValue.addRequestMap("rrId", rrId);
//        
		return beanValue;
//	
	}
	
	
	/**
	 * 处理内容：将选择的常用航线设置为计划的主备航线
	 * @param map
	 * @return value
	 * @throws Exception
	 */
	public BeanValue initSkyWayByGaneral(RequestMap requestMap)
			throws Exception {
//
////		// 验证用户权限
//		ReqIdentity identity = getReqIdentity(requestMap);
		
		
		BeanValue beanValue = new BeanValue();
		String  rrIds=requestMap.getString("rrIds");
		List skyWay=new ArrayList();
		if (StringUtils.isNotEmpty(rrIds)) {
			String[] stemp1=rrIds.split(";");
			for(String s: stemp1){
				GeneralSkyWayVO skyWayVO=new GeneralSkyWayVO();
				String[] stemp2=s.split(":");
				String flag = requestMap.getString("flag");
				StringBuffer sqlBuff = new StringBuffer();
				if(StringUtils.equals("0", flag)) {
					sqlBuff = new StringBuffer("select t.rr_id,t.rr_code,t.rr_name,t.rr_dep,t.rr_des,'"+stemp2[0]+"' as rr_type from T_Regular_Rs t" +
						" where t.rr_id="+stemp2[1]);
				}else{
					sqlBuff = new StringBuffer("select t.rer_id as rr_id,t.rer_code as rr_code,t.rer_name as rr_name,t.rer_dep as rr_dep,t.rer_des as rr_des ,'"+stemp2[0]+"' as rr_type from " +
							" T_RECOMMEND_RS t where t.rer_id="+stemp2[1]);
				}
				
//				System.out.println(sqlBuff+"");
				List<String[]> temp2 =dao.getObjects(sqlBuff.toString(), new String[]{"rr_id","rr_code","rr_name","rr_dep","rr_des","rr_type"});
				if(temp2 != null && temp2.size() > 0){
					
					skyWayVO.setRrId(temp2.get(0)[0]);
					skyWayVO.setRrCode(temp2.get(0)[1]);
					skyWayVO.setRrName(temp2.get(0)[2]);
					skyWayVO.setRrDep(temp2.get(0)[3]);
					skyWayVO.setRrDes(temp2.get(0)[4]);
					skyWayVO.setRrType(temp2.get(0)[5]);
					List temp1=new ArrayList();
					
					StringBuffer sqlBuff2 = new StringBuffer("select tf.rs_p_id,tf.rs_p_name,tf.rs_p_num,tf.rs_p_pos,tf.rs_p_type,tf.rs_p_code" +
							" from t_fpl_rs_p tf ");
					if(StringUtils.equals("0", flag)) {
						sqlBuff2.append("where tf.rr_id="+skyWayVO.getRrId());
					}else{
						sqlBuff2.append("where tf.rer_id="+skyWayVO.getRrId());
					}
					sqlBuff2.append(" order by tf.rs_p_num ");
					List<String[]> temp =dao.getObjects(sqlBuff2.toString()
							, new String[]{"rs_p_id","rs_p_name","rs_p_pos","rs_p_num","rs_p_type","rs_p_code"});
					if(temp != null && temp.size() > 0){
						for(int j=0 ;j<temp.size() ; j++){
							GeneralSkyWayPointVO pointVO=new GeneralSkyWayPointVO();
							pointVO.setRsPName(temp.get(j)[1]);
							pointVO.setRsPPos(temp.get(j)[2]);
							pointVO.setRsPNum(new Long(temp.get(j)[3]));
							pointVO.setRsPType(new Long(temp.get(j)[4]));
							pointVO.setRsPCode(temp.get(j)[5]);
							temp1.add(pointVO);
						}
						skyWayVO.setPointList(temp1);
					}
				}
				skyWay.add(skyWayVO);
			}
		}
//		
		
//		System.out.println(sqlBuff+"");
		
		// 获得设备信息
		
		beanValue.addRequestMap("skyWay", skyWay);
		
//		  /** 记录日志 */ 
//		requestMap.addParameter(ProjectConstants.LOG_TEXT, "常规航线信息");
		
		return beanValue;
//	
	}
	
	
	
	/**
	 * 处理内容：查询用户设置的常用航线列表
	 * @param map
	 * @return value
	 * @throws Exception
	 */
	public BeanValue getGeneralSkyWays(RequestMap requestMap)
			throws Exception {

		BeanValue beanValue = new BeanValue();
		
//		// 验证用户权限
		ReqIdentity identity = getReqIdentity(requestMap);
//		identity.("BM10101");//飞行计划新建
//		SecurityStub sstub = new SecurityStub();
//		sstub.checkPermission(identity);
//		BeanValue beansetFunctionIdValue = new BeanValue();
		
		
		// 起飞机场
		String adep = requestMap.getString("adep");
		// 目的机场
		String ades = requestMap.getString("ades");
		
		String flag = requestMap.getString("flag");
		StringBuffer sqlBuff = new StringBuffer();
		if(StringUtils.equals("0", flag)) {
//			TODO 查询条件中设置登录人过滤 。。
			sqlBuff = new StringBuffer("select t.rr_id,t.rr_code,t.rr_name,t.rr_dep,t.rr_des from T_Regular_Rs t" +
					" where t.rr_user='"+identity.getUserId()+"'");
			
			if(StringUtils.isNotEmpty(adep)) {
				sqlBuff.append(" and t.rr_dep='"+adep.trim()+"'");
			}
			if(StringUtils.isNotEmpty(ades)) {
				sqlBuff.append(" and t.rr_des='"+ades.trim()+"'");
			}
			
		} else {
			sqlBuff = new StringBuffer("select t.rer_id as rr_id,t.rer_code as rr_code,t.rer_name as rr_name,t.rer_dep as rr_dep,t.rer_des as rr_des from T_RECOMMEND_RS t");
			
			if(StringUtils.isNotEmpty(adep)) {
				sqlBuff.append(" where t.rer_dep='"+adep.trim()+"'");
			}
			if(StringUtils.isNotEmpty(ades)) {
				sqlBuff.append(" and t.rer_des='"+ades.trim()+"'");
			}
			
		}
		sqlBuff.append(" order by t.create_date desc ");
		
//		System.out.println(sqlBuff+"");
		
		
		List infoList=new ArrayList();
		List<String[]> temp2 =dao.getObjects(sqlBuff.toString(), new String[]{"rr_id","rr_code","rr_name","rr_dep","rr_des"});
		beanValue.addRequestMap("infoList", infoList);
		if(temp2 != null && temp2.size() > 0){
			
			for(int i=0 ;i<temp2.size() ; i++){
				GeneralSkyWayVO skyWayVO=new GeneralSkyWayVO();
				
				skyWayVO.setRrId(temp2.get(i)[0]);
				skyWayVO.setRrCode(temp2.get(i)[1]);
				skyWayVO.setRrName(temp2.get(i)[2]);
				skyWayVO.setRrDep(temp2.get(i)[3]);
				skyWayVO.setRrDes(temp2.get(i)[4]);
				List temp1=new ArrayList();
				
				StringBuffer sqlBuff2 = new StringBuffer("select tf.rs_p_id,tf.rs_p_name,tf.rs_p_num,tf.rs_p_pos,tf.rs_p_type,tf.rs_p_code from t_fpl_rs_p tf ");
				
				if(StringUtils.equals("0", flag)) {
					sqlBuff2.append(" where tf.rr_id="+skyWayVO.getRrId());
				}else{
					sqlBuff2.append(" where tf.rer_id="+skyWayVO.getRrId());
				}
				sqlBuff2.append(" order by tf.rs_p_num");
				List<String[]> temp =dao.getObjects(sqlBuff2.toString(), new String[]{"rs_p_id","rs_p_name","rs_p_pos","rs_p_num","rs_p_type","rs_p_code"});
				if(temp != null && temp.size() > 0){
					for(int j=0 ;j<temp.size() ; j++){
						GeneralSkyWayPointVO pointVO=new GeneralSkyWayPointVO();
						pointVO.setRsPName(temp.get(j)[1]);
						pointVO.setRsPPos(temp.get(j)[2]);
						pointVO.setRsPCode(temp.get(j)[5]);
						temp1.add(pointVO);
					}
					skyWayVO.setPointList(temp1);
				}
				
				infoList.add(skyWayVO);
			}
			
		}
		beanValue.addRequestMap("flag", flag);
		beanValue.addRequestMap("infoList", infoList);
		beanValue.setForward("generalSkyWayList");
		return beanValue;
	
	}
	
	/**
	 * 审核飞行计划页面初始化
	 */
	public BeanValue initFlyPlanInfo(RequestMap map) throws Exception {
		
		BeanValue beanValue = new BeanValue();
		String planId = map.getString("planId");
		
		beanValue.addParamMap("planId", planId);
		beanValue.addRequestMap("planId", planId);
		
		this.getParamMapForList(map, beanValue);
		
//		Employee employee = (Employee)map.getObject(ProjectConstants.SESSION_USER);
//		
//		// 获得当前用户的的角色
//		UserRole role = (UserRole) map.getObject(ProjectConstants.SESSION_USER_ROLE);
		
	    /* 页面初始化 */
		// 查询计划信息
		StringBuffer sql = new StringBuffer();
		
		sql.append("select t.planid,t.f_plan_name,cd4.name as unit,cd1.name as rulename,cd2.name as pclass,cd3.name as ptype, " +
				"cd5.name as commander, cd6.name as types, cd7.name as acid,cd8.name as acft,cd9.name as equip,cd10.ad_name as adep," +
				"cd11.ad_name as ades,cd12.ad_name as altn1,cd13.ad_name as altn2,cd14.ad_name as altn3,cd15.ad_name as altn4, " +
				"t.alarm,t.chgt,t.cspd,to_char(t.dates,'yyyy-MM-dd') as dates,t.eet,to_char(t.fpl_ctime,'yyyy-MM-dd HH:mm:ss') as fpl_ctime,t.remark,t.ssr,t.task,t.setd,t.seta,t.setal, " + 
				"cd16.name as planstate,t.f_plan_code,t17.name as plan_user,to_char(t18.cy_btime,'yyyy-MM-dd') as cy_btime,to_char(t18.cy_etime,'yyyy-MM-dd') as cy_etime,t18.cy_lengh," +
				"t18.cy_unit,t18.cy_weekday,t.pclass as pclassId,t.planstate as planstateId from t_fpl t ");
		
		sql.append(" left join tb_code cd1 on cd1.class_id = 'TH_FLY_RULE' and cd1.code_id = t.rule");  //飞行规则
		
		sql.append(" left join tb_code cd2 on cd2.class_id = 'TH_FLY_PLAN_KIND' and cd2.code_id = t.pclass");   // 飞行类别：重复性，一次性
		
		sql.append(" left join tb_code cd3 on cd3.class_id = 'TH_FLY_PLAN_TYPE' and cd3.code_id = t.ptype");  //计划类型 ： 通航，民航
		sql.append(" left join tb_code cd4 on cd4.class_id = 'TH_AIRLINE_COMPANY' and cd4.code_id = t.unit");  //航空公司
		sql.append(" left join tb_code cd5 on cd5.class_id = 'TH_PILOT' and cd5.code_id = t.commander");  //飞行员
		sql.append(" left join tb_code cd6 on cd6.class_id = 'TH_FLY_TYPE' and cd6.code_id = t.types");  //飞行类型
		sql.append(" left join tb_code cd7 on cd7.class_id = 'TH_AIR_CRAFT_SN' and cd7.code_id = t.acid");  //航空器编号
		sql.append(" left join tb_code cd8 on cd8.class_id = 'TH_AIR_CRAFT_TYPE' and cd8.code_id = t.acft");  //航空器型号
		sql.append(" left join tb_code cd9 on cd9.class_id = 'TH_FLY_EQUIPMENT' and cd9.code_id = t.equip");  //机载设备
		sql.append(" left join t_ad cd10 on cd10.objectid = t.adep");  //起飞机场
		sql.append(" left join t_ad cd11 on cd11.objectid = t.ades");  //目的机场
		sql.append(" left join t_ad cd12 on cd12.objectid = t.altn1");  //备降机场1
		sql.append(" left join t_ad cd13 on cd13.objectid = t.altn2");  //备降机场2
		sql.append(" left join t_ad cd14 on cd14.objectid = t.altn3");  //备降机场3
		sql.append(" left join t_ad cd15 on cd15.objectid = t.altn4");  //备降机场4
		sql.append(" left join tb_code cd16 on cd16.class_id = 'TH_FPL_STATUS' and cd16.code_id = t.planstate");  //计划状态
		sql.append(" left join tb_user t17 on t17.user_id = t.plan_user ");  // 通航用户是从tb_user表中取得的值，所以要关联此表
		sql.append(" left join t_fpl_cycle t18 on t18.planid = t.planid "); 
		
		sql.append(" where t.planid = '").append(planId).append("'");

//		System.out.println(sql.toString());
		List<String[]> dataList = null;
		
		
		dataList = (ArrayList<String[]>) dao.getObjects(sql.toString(),
			new String[]{"acft","acid","adep","ades","alarm","altn1","altn2","altn3","altn4","chgt","commander",
			"cspd","dates","eet","equip","f_plan_name","fpl_ctime","pclass","planid","ptype",
			"remark","rulename","seta","setal","setd","ssr","task","types","unit","planstate","f_plan_code","plan_user"
			,"cy_btime","cy_etime","cy_lengh","cy_unit","cy_weekday","pclassId","planstateId"});
			
		
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
						sf.append("日");
					}else if(StringUtils.equals("2", temp)) {
						sf.append("一");
					}else if(StringUtils.equals("3", temp)) {
						sf.append("二");
					}else if(StringUtils.equals("4", temp)) {
						sf.append("三");
					}else if(StringUtils.equals("5", temp)) {
						sf.append("四");
					}else if(StringUtils.equals("6", temp)) {
						sf.append("五");
					}else if(StringUtils.equals("7", temp)) {
						sf.append("六");
					}
					sf.append("、");
				}
			}
			if(sf.toString().lastIndexOf("、") > 0) {
				str = sf.toString().substring(0, sf.toString().length()-1);
			}
			fp.setCyWeekday(str);
			fp.setPclassId(info[37]);
			
//			查询计划实施信息。 
			if(StringUtils.equals("72", info[38]) || StringUtils.equals("73", info[38])) {
				this.getActivity(dao,beanValue,planId);
			}
			
			// 查询航线
			this.getPlanRs(dao,beanValue,planId);
		}
		
		beanValue.addRequestMap("dataInfo", fp);

		beanValue.addRequestMap("moduleTitle", "查看飞行计划");
//		
		  /** 记录日志 */ 
		map.addParameter(ProjectConstants.LOG_TEXT, "查看飞行计划");
		beanValue.setForward("viewFlyPlanInfo");
		
		return beanValue;
	}
	
	/**
	 * 获得航线信息
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
	 * 获得实际实施信息
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
	 * 处理内容：通航用户查询飞行计划信息列表
	 * @param map
	 * @return value
	 * @throws Exception
	 */
	public BeanValue searchFlyPlanList(RequestMap requestMap) throws Exception {
		
		
		//测控实施方案查询(二队)
		BeanValue beanValue = new BeanValue();
		//默认为管理列表 
		String functionId = "BM10102";
		// 查询类型，如果searchType为manage则转到管理列表，为query则转到查询列表
		String searchType = requestMap.getString("searchType");
		if ("query".equals(searchType)) {
			// 查询列表
			functionId = "BM10104";
			
		}else if ("activateQ".equals(searchType)) {
			// 激活查询列表
			functionId = "BM10103";
			
		}
		
		// 用于返回按钮判断返回到管理页面还是查询页面
		beanValue.addRequestMap("searchType", searchType);
		
		//如果为空，则默认为管理列表。
		// 获取用户登录会话标识
//		ReqIdentity identity = getReqIdentity(requestMap);
//		// 解除用户锁定的对象
//		releaseObjectLock(identity);
//		//权限验证
//		identity.setFunctionId(functionId);
//		SecurityStub sstub = new SecurityStub();
//		sstub.checkPermission(identity);
		
		
		/* 页面初始化 */
		// 初始化分页条件
		RollPage rollPage = new RollPage();
		this.initRollPage(requestMap, rollPage);
		
//		this.getParamMapForList(requestMap, beanValue);
		List<Code> flyPlanKindList = ProjectItemCode.getCodeList("TH_FLY_PLAN_KIND");
		beanValue.addRequestMap("flyPlanKindList", flyPlanKindList);
		
		//日期段 TODO : 日期查询条件的设置，多个或导致看不出来效果，质检项目查询
		List<Code> autoPeriodList = ProjectItemCode.getCodeList("AUTO_PERIOD");
		beanValue.addRequestMap("autoPeriodList", autoPeriodList);
		
		
		//从前台获取查询条件
//		UserRole userRole = (UserRole) map
//				.getObject(ProjectConstants.SESSION_USER_ROLE); 
	    
		
		StringBuffer sqlBuff = new StringBuffer("select tf.planid,tf.f_plan_code,tf.f_plan_name,tf.dates,tf.setd,tf.eet,tf.adep,tf.ades" +
				" ,tf.planstate,tad1.ad_name adepName,tad2.ad_name adesName,tc2.name acft,tc3.name ruleName,tc1.name stateName,'' auditR" +
				" ,'' auditRName,tf.pclass,'1' delFlag,'1' upFlag,tf.activate,tc4.name className " +
				" from t_fpl tf left join t_ad tad1 on tf.adep = trim(tad1.objectid)" +
				" left join t_ad tad2 on tf.ades = trim(tad2.objectid) left join tb_code tc1 on tc1.class_id = 'TH_FPL_STATUS' and" +
				" tf.planstate = tc1.code_id left join tb_code tc2 on tc2.class_id = 'TH_AIR_CRAFT_TYPE' and tf.acft = tc2.code_id" +
				" left join tb_code tc3 on tc3.class_id = 'TH_FLY_RULE' and tf.rule = tc3.code_id" +
				" left join tb_code tc4 on tc4.class_id = 'TH_FLY_PLAN_KIND' and tf.pclass = tc4.code_id" +
				" where tf.archive_state='0' ");
		
		
		String flyPlanCode = requestMap.getString("flyPlanCode");
		if(StringUtils.isNotEmpty(flyPlanCode)) {
			sqlBuff.append(" and tf.f_plan_code like '%"+flyPlanCode.trim()+"%'");
		}
		
		String flyPlanName = requestMap.getString("flyPlanName");
		if(StringUtils.isNotEmpty(flyPlanName)) {
			sqlBuff.append(" and tf.f_plan_name like '%"+flyPlanName.trim()+"%'");
		}
		
		String startDate = requestMap.getString("startDate");
		if (StringUtils.isNotEmpty(startDate)) { 
			sqlBuff.append(" and tf.dates >= to_date('"+startDate.trim()+"','yyyy-mm-dd')");
		}
		String endDate = requestMap.getString("endDate");
		if (StringUtils.isNotEmpty(endDate)) {
			sqlBuff.append(" and tf.dates <= to_date('"+endDate.trim()+"','yyyy-mm-dd')");
		}
		
		String flyPlanAdep = requestMap.getString("flyPlanAdep");
		if (StringUtils.isNotEmpty(flyPlanAdep)) {
			sqlBuff.append(" and tf.adep = '"+flyPlanAdep.trim()+"'");
		}
		
		String flyPlanAdes = requestMap.getString("flyPlanAdes");
		if (StringUtils.isNotEmpty(flyPlanAdes)) {
			sqlBuff.append(" and tf.ades = '"+flyPlanAdes.trim()+"'");
		}
		
		String fplPlanClass = requestMap.getString("fplPlanClass");
		if (StringUtils.isNotEmpty(fplPlanClass)) {
			sqlBuff.append(" and tf.pclass = '"+fplPlanClass.trim()+"'");
		}

		String flyPlanAdepName = requestMap.getString("flyPlanAdepName");
		if (StringUtils.isNotEmpty(flyPlanAdepName)) {
			sqlBuff.append(" and (tad1.ad_name like '%"+flyPlanAdepName.trim()+"%' or tad1.icao_code like '%"+flyPlanAdepName.trim()+"%')");
		}
		
		String flyPlanAdesName = requestMap.getString("flyPlanAdesName");
		if (StringUtils.isNotEmpty(flyPlanAdesName)) {
			sqlBuff.append(" and (tad2.ad_name like '%"+flyPlanAdesName.trim()+"%' or tad2.icao_code like '%"+flyPlanAdepName.trim()+"%')");
		}
		
		String fplStatus = requestMap.getString("fplStatus");
		if (StringUtils.isNotEmpty(fplStatus)) {
			if(fplStatus.trim().equals("4") ){ // 表示中止计划，对应状态40,41,42
				sqlBuff.append(" and tf.planstate in ('41','42','40')");
			}else{
				sqlBuff.append(" and tf.planstate = '"+fplStatus.trim()+"'");
			}
			
		}
		
		if ("activateQ".equals(searchType)) {
			// 激活中止可激活的列表
			sqlBuff.append(" and tf.planstate in ('41','42') and tf.activate ='1'");
			
		}
		
//		sqlBuff.append(" order by tf.dates desc,tf.f_plan_code ");
		sqlBuff.append(" order by tf.f_plan_code ");
		
//		System.out.println(""+sqlBuff);
		
		List<String[]> infoList = dao.getObjects(rollPage, sqlBuff.toString(), new String[]{"planid","f_plan_code","f_plan_name","dates"
			,"setd","eet","adep","adepName","ades","adesName","acft","ruleName","planstate","stateName","auditR","auditRName","pclass"
			,"delFlag","upFlag","activate","className"});
		
		if(infoList != null && infoList.size() > 0 ) {
			for(int i=0; i < infoList.size(); i++) {
				
				// 查询每个飞行计划的处理流程
				List<String[]> approve = null;
				
				approve = dao.getObjects("select c.approve_state,c.approve_result,c2.name as approve_result_name from tb_approve_flow_rec c" +
						" left join tb_code c2 on c2.class_id='TH_APPROVE_STATE' and c2.code_id = c.approve_result" +
						" where c.object_Id = '" + infoList.get(i)[0] + "' order by c.send_ts desc,c.approve_id desc",new String[]{"approve_state","approve_result","approve_result_name"}); 
				if(approve != null && approve.size() > 0 ) {
					
					if(infoList.get(i)[12] != null && infoList.get(i)[12].trim().equals("32")){ // 已批复，需查询批复结果
						String[] rec = approve.get(0);
						if("051".equals(rec[0])) {
							rec = approve.get(2);
						}
						
						infoList.get(i)[14] = rec[1];
						infoList.get(i)[15] = rec[2];
						
						if(infoList.get(i)[14] != null && infoList.get(i)[14].trim().equals("1")){ //审批通过计划
							if(infoList.get(i)[16] != null && infoList.get(i)[16].trim().equals("GAR")){ //周期计划
								infoList.get(i)[18] = "0"; // 0 ，表示不可以修改，撤销，1 表示可以修改，撤销
							}
						}
					}
					
					infoList.get(i)[17] = "0"; // 0 ，表示不可以删除，1 表示可以删除
					
				}else{
					
					// 无审批流程，查询飞行计划的是否已提交上级。
					List temp1=dao1.getObjects("select planid from t_fpl tf where tf.planid="+infoList.get(i)[0], new String[]{"planid"});
					if(temp1 != null && temp1.size() > 0 ) {
						infoList.get(i)[17] = "0"; // 0 ，表示不可以删除，1 表示可以删除
					}
					
					if(infoList.get(i)[12] != null && infoList.get(i)[12].trim().equals("31")){ //报备计划
						if(infoList.get(i)[16] != null && infoList.get(i)[16].trim().equals("GAR")){ //周期报备计划
							infoList.get(i)[18] = "0"; // 0 ，表示不可以修改，撤销，1 表示可以修改，撤销
						}
					}
				}
				
				if(infoList.get(i)[12].trim().equals("40")|| infoList.get(i)[12].trim().equals("41") || infoList.get(i)[12].trim().equals("42")
						|| infoList.get(i)[12].trim().equals("36") || infoList.get(i)[12].trim().equals("35")
						|| infoList.get(i)[12].trim().equals("72")|| infoList.get(i)[12].trim().equals("73")){ // 中止计划、失效、撤销计划、已确认或实施的计划不可修改
					infoList.get(i)[18] = "0"; // 0 ，表示不可以修改，撤销，1 表示可以修改，撤销
				}
			}
		}
		
		beanValue.addRequestMap("flyPlanList", infoList);
		
//		System.out.println(""+infoList.size());
		
//		beanValue.addRequestMap("keyWord", keyWord);
		beanValue.addRequestMap("rollPage", rollPage);
		
		 // 放到paramMap中的值为了返回按钮使用
//	    beanValue.addParamMap("pageBool", rollPage.getPageBool());
		
	    
//		List<Code> airCraftTypeList = ProjectItemCode.getCodeList("TH_AIR_CRAFT_TYPE");
//		beanValue.addRequestMap("airCraftTypeList", airCraftTypeList);
	    
	    
	 // 本模块内的查询条件的回传，页面返回所需参数串

		
		beanValue.addRequestMap("flyPlanCode",flyPlanCode);
		beanValue.addRequestMap("flyPlanName",flyPlanName);
		beanValue.addRequestMap("flyPlanAdep",flyPlanAdep);
		beanValue.addRequestMap("flyPlanAdes",flyPlanAdes);
		beanValue.addRequestMap("flyPlanAdepName",flyPlanAdepName);
		beanValue.addRequestMap("flyPlanAdesName",flyPlanAdesName);
		beanValue.addRequestMap("fplPlanClass",fplPlanClass);
		beanValue.addRequestMap("fplStatus",fplStatus);
		beanValue.addRequestMap("startDate",startDate);
		beanValue.addRequestMap("endDate",endDate);
		
		// 日期
		String qDate = requestMap.getString("qDate");
		beanValue.addRequestMap("qDate", qDate);
		
		List<Code> fplStatusList = ProjectItemCode.getCodeList("TH_FPL_STATUS");
		beanValue.addRequestMap("fplStatusList", fplStatusList);
	    
	    if ("query".equals(searchType)) {
	    	beanValue.setForward("queryFlyPlanList");
		}else if ("activateQ".equals(searchType)) {
			beanValue.setForward("activateFlyPlanList");
		}else {
			beanValue.setForward("searchFlyPlanList");
		}
		return beanValue;
	}
	
	
	/**
	 * 处理内容：调用sql写数据库
	 * @param value 
	 * @param dbConType :  	1 表示连接 @192.168.6.44:1521:esri ，使用 dao 执行更新。
	 * 						2 表示连接 @192.168.6.44:1521:esri ，使用 dao1 执行更新，暂未使用。
	 * 						3 表示连接 @10.90.90.161:1521:H1501 ，使用 szkydao 执行更新。
	 * @return 
	 * @throws Exception
	 */
	public void updateFlyPlanBySql(BeanValue value , String dbConType) throws Exception{
		if(dbConType != null && dbConType.trim().equals("1")){ // dao 执行语句， 写 6.44 数据库
			List<String> list=(List) value.getRequestMap("upList");
			if(list != null && list.size()>0){
				for(String sql:list){
					dao.updateBySql(sql);
				}
			}
		}else if(dbConType != null && dbConType.trim().equals("3")){ // szkydao 执行语句， 写 90.161 数据库
			List<String> list=(List) value.getRequestMap("szkyUpList");
			if(list != null && list.size()>0){
				for(String sql:list){
					szkydao.updateBySql(sql);
				}
			}
		}
		
		
	}

	
	/**
	 * 初始化翻页操作
	 * @param map
	 * @param rollPage
	 */
	private void initRollPage(RequestMap map, RollPage rollPage) {

		/* * * * * * * 分页设置 * * * * * * */
		// 获取页数
		String page = map.getString("page");
		// 获取翻页标志
		String pageBool = map.getString("pageBool");

		// 将页数转换成为数字
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
		// 查询类型，如果searchType为manage空则为管理列表，为query空则为查询列表
		String searchType = map.getString("searchType");
		// 设置每一页显示的记录数
		if (searchType != null && searchType.trim().equals("query")) {
			rollPage.setPagePer(16);
		} else {
			rollPage.setPagePer(16);
		}

		// 设置翻页查询条件
		
		String flyPlanCode = map.getString("flyPlanCode");
		rollPage.setCondition("flyPlanCode", flyPlanCode);
		
		String flyPlanName = map.getString("flyPlanName");
		rollPage.setCondition("flyPlanName", flyPlanName);
		
		String startDate = map.getString("startDate");
		rollPage.setCondition("startDate", startDate);
		
		String endDate = map.getString("endDate");
		rollPage.setCondition("endDate", endDate);
		
		String flyPlanAdep = map.getString("flyPlanAdep");
		rollPage.setCondition("flyPlanAdep", flyPlanAdep);
		
		String flyPlanAdes = map.getString("flyPlanAdes");
		rollPage.setCondition("flyPlanAdes", flyPlanAdes);

		String flyPlanAdepName = map.getString("flyPlanAdepName");
		rollPage.setCondition("flyPlanAdepName", flyPlanAdepName);
		
		String flyPlanAdesName = map.getString("flyPlanAdesName");
		rollPage.setCondition("flyPlanAdesName", flyPlanAdesName);
		
		String fplPlanClass = map.getString("fplPlanClass");
		rollPage.setCondition("fplPlanClass", fplPlanClass);

		
		String fplStatus = map.getString("fplStatus");
		rollPage.setCondition("fplStatus", fplStatus);
		
		
		
		

	}
	
	 /**
     * 保存 List 查询回退参数
     */
    private void getParamMapForList(RequestMap requestMap, BeanValue aBeanValue) throws Exception {
    	
    	// 页面返回所需参数串
//		String parameters = aRequestMap.getString("tipparams");  // 该参数主要用于对公用模块的传值，在本模块直接采用隐藏域获取。
//		aBeanValue.addParamMap("tipparams", aRequestMap.getString("tipparams"));
//		aBeanValue.addParamMap("tip", aRequestMap.getString("tip"));
//		if (parameters != null) {
//			String[] paramArr = parameters.split("##");
//			for (int i = 0; i < paramArr.length; i++) {
//				String[] keyValue = paramArr[i].split("%%");
//				if (keyValue.length > 1) {
//					aBeanValue.addParamMap(keyValue[0], keyValue[1]);
//					aBeanValue.addRequestMap(keyValue[0], keyValue[1]);
//				}
//			}
//		}
		
    	// 本模块内的查询条件的回传，页面返回所需参数串
		String flyPlanCode = requestMap.getString("flyPlanCode");
		aBeanValue.addParamMap("flyPlanCode",flyPlanCode);
		aBeanValue.addRequestMap("flyPlanCode",flyPlanCode);
		
		String flyPlanName = requestMap.getString("flyPlanName");
		aBeanValue.addParamMap("flyPlanName",flyPlanName);
		aBeanValue.addRequestMap("flyPlanName",flyPlanName);
		
		String startDate = requestMap.getString("startDate");
		aBeanValue.addParamMap("startDate",startDate);
		aBeanValue.addRequestMap("startDate",startDate);
		
		String endDate = requestMap.getString("endDate");
		aBeanValue.addParamMap("endDate",endDate);
		aBeanValue.addRequestMap("endDate",endDate);
		
		String flyPlanAdep = requestMap.getString("flyPlanAdep");
		aBeanValue.addParamMap("flyPlanAdep",flyPlanAdep);
		aBeanValue.addRequestMap("flyPlanAdep",flyPlanAdep);
		
		String flyPlanAdes = requestMap.getString("flyPlanAdes");
		aBeanValue.addParamMap("flyPlanAdes",flyPlanAdes);
		aBeanValue.addRequestMap("flyPlanAdes",flyPlanAdes);

		String flyPlanAdepName = requestMap.getString("flyPlanAdepName");
		aBeanValue.addParamMap("flyPlanAdepName",flyPlanAdepName);
		aBeanValue.addRequestMap("flyPlanAdepName",flyPlanAdepName);
		
		String flyPlanAdesName = requestMap.getString("flyPlanAdesName");
		aBeanValue.addParamMap("flyPlanAdesName",flyPlanAdesName);
		aBeanValue.addRequestMap("flyPlanAdesName",flyPlanAdesName);
		
		String fplPlanClass = requestMap.getString("fplPlanClass");
		aBeanValue.addParamMap("fplPlanClass",fplPlanClass);
		aBeanValue.addRequestMap("fplPlanClass",fplPlanClass);

		
		String fplStatus = requestMap.getString("fplStatus");
		aBeanValue.addParamMap("fplStatus",fplStatus);
		aBeanValue.addRequestMap("fplStatus",fplStatus);

		String qDate = requestMap.getString("qDate");
		aBeanValue.addParamMap("qDate",qDate);
		aBeanValue.addRequestMap("qDate",qDate);

		
//		// viewType为view表示查看详细信息
//		String viewType = requestMap.getString("viewType");
//		aBeanValue.addParamMap("viewType",viewType);
//		aBeanValue.addRequestMap("viewType",viewType);

		
		// 查询类型，如果searchType为list则转到管理列表，为query则转到查询列表
		String searchType = requestMap.getString("searchType");
		aBeanValue.addRequestMap("searchType", searchType);
		aBeanValue.addParamMap("searchType",searchType);
		
		
        /* 翻页参数 */
        // 当前页码, 默认为 0
        String page = requestMap.getString("page");
        page = (page == null || page.equals("")) ? "0" : page;
        // 查询标识, 0 新条件查询, 1 翻页查询
        String pageBool = requestMap.getString("pageBool");
        // 翻页对象
        RollPage aRollPage = new RollPage(ConvertLang.convertint(page), pageBool);
     // 设置每一页显示的记录数
		if (searchType != null && searchType.trim().equals("query")) {
			aRollPage.setPagePer(15);
		} else {
			aRollPage.setPagePer(14);
		}
        
//        /* 设置回退参数 */
        
        aBeanValue.addParamMap("page", page);
        aBeanValue.addParamMap("pageBool", pageBool);
        aBeanValue.addParamMap("rollPage", aRollPage);
        
        
    }

    
	/*
	 * 增加数字空域仿真演示数据.
	 * 1.在3维数据库中增加演示计划.
	 * 在数字化空域中生成三个相关表数据：飞行计划（T_FPL）、飞行计划航路点表（T_FPL_P）、飞行计划使用空域表（T_TP_AS）。
	 * 2.将本地数据库中的飞行计划表的仿真字段设置为1,表示已经进行了设置仿真数据.
	 * 
	 * 当已经存在仿真数据时,相应数据的变更.
	 * ?此时是否需要判断,仿真数据的相关信息是否发生了变更.?
	 * 此时考虑两种情况的消耗比较,
	 * (1)查询出相关的数据,然后删除
	 * (2)问询是否发生了变更.
	 * 
	 * 考虑是否和保存使用相同的逻辑.
	 */
    /**
	 * 处理内容：数字化空域应保存的仿真计划信息.
	 * @param map
	 * @return value
	 * @throws Exception
	 */
	public void saveSzkyFlyPlan(RequestMap requestMap , TFpl tFpl , BeanValue beanValue) throws Exception {
		
		//计划标识.
		//根据计划标识在本地数据库中查询出三维需要的数据.
		//实施日期,计划类别,计划类型,计划状态(任意状态均可),时刻表预计起飞时刻,时刻表预计降落时刻,飞行总时间,机型/架次,飞行规则,巡航速度,巡航高度(1000),任务,
		//需要在页面中获取的数据.起飞机场、目的机场、备降场1、备降场2、备降场3,航线(空域) -- 起飞机场  航线上的各地标点导航台  降落机场
		//需要增加的默认值: 空管基本数据版本号 (0.0.0).
//		String[] planReturnTemp = {"PLANID","DATES","PCLASS","PTYPE","PLANSTATE","SETD","SETA","EET","ACFT","RULE","CSPD","CHGT","TASK","CREATE_IMITATE"};
//		StringBuffer executeSerchSql = new StringBuffer();
//		executeSerchSql.append("SELECT FPL.PLANID ");/*计划标识*/
//		executeSerchSql.append(",FPL.DATES ");/*实施日期*/
//		executeSerchSql.append(",FPL.PCLASS ");/*计划类别*/
//		executeSerchSql.append(",FPL.PTYPE ");/*计划类型*/
//		executeSerchSql.append(",FPL.PLANSTATE ");/*计划状态*/
//		executeSerchSql.append(",FPL.SETD ");/*预计起飞时刻*/
//		executeSerchSql.append(",FPL.SETA ");/*预计降落时刻*/
//		executeSerchSql.append(",FPL.EET ");/*飞行总时间*/
//		executeSerchSql.append(",FPL.ACFT ");/*机型*/
//		executeSerchSql.append(",FPL.RULE ");/*飞行规则*/
//		executeSerchSql.append(",FPL.CSPD ");/*巡航速度*/
//		executeSerchSql.append(",FPL.CHGT ");/*巡航高度*/
//		executeSerchSql.append(",FPL.TASK ");/*任务*/
//		executeSerchSql.append(",FPL.CREATE_IMITATE ");
//		executeSerchSql.append(" FROM T_FPL FPL ");
//		executeSerchSql.append("WHERE FPL.PLANID = '");
//		executeSerchSql.append(planid);
//		executeSerchSql.append("'");
//		//由于根据planid查询的数据唯一,因此操作此数据的时候,只需要获取list中的第一个数据即可.
//		List<String[]> list = dao.getObjects(executeSerchSql.toString(), planReturnTemp);
//		if(list != null){
		
			//当查询到相应信息时,说明需要的数据相应内容已经初具模型,此时再获取页面中的相应航线信息.
			//起飞降落以及备降场.
			String altn1 = requestMap.getString("altn1Code");
//			String altn2 = requestMap.getString("altn2Code");
//			String altn3 = requestMap.getString("altn3Code");
			String adep = requestMap.getString("adepCode");
			String ades = requestMap.getString("adesCode");
			
			String[] rsPNums = requestMap.getStringArray("rsPNumMain");
			String[] rs_PPosMain = requestMap.getStringArray("rs_PPosMain");
			String[] rsPTypeMain = requestMap.getStringArray("rsPTypeMain");
			String[] rsPCodeMain = requestMap.getStringArray("rsPCodeMain");
			StringBuffer rspcodeStr = new StringBuffer("");
			String[] returnOneColumn = {"nextNum"};
			if(rsPCodeMain!=null && rsPCodeMain.length > 0){
				for(String s : rsPCodeMain){
					rspcodeStr.append(s).append(" ");
				}
			}
			
			List<String> szkyUpList =new ArrayList<String>();
			
			szkyUpList.add("delete from t_fpl fpl where fpl.planmid = '"+tFpl.getPlanid()+"'");
			szkyUpList.add("delete from t_fpl_p tp where tp.planmid = '"+tFpl.getPlanid()+"'");
			szkyUpList.add("delete from t_tp_as tpas where tpas.planmid = '"+tFpl.getPlanid()+"'");
			
			SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
			
			StringBuffer insertStr = new StringBuffer("");
			insertStr.append("");
			insertStr.append("INSERT INTO T_FPL (PLANMID, DATES, PCLASS, PTYPE, ACID, PLANSTATE,ADEP, ADES, ALTN1, ALTN2, ALTN3, ALTN4, SETD, METD, SETA, META, ATD, ATA, EET,");
			insertStr.append("ACFT, TOTAL, TURB, RULE, TYPES, EQUIP, SSR, CSPD, CHGT, ROUTES,UNIT, TASK, TRANSNO, CALLSIGN, COMM, METEO, SUBJECT, COMMANDER, DUTY, AIRCREW, DATAVERSION, REMARK, INSERT_DATE, HATC_TIMESTAMP, UPDATE_NUM)");
			insertStr.append("values (");
			insertStr.append("'").append(tFpl.getPlanid()).append("'").append(",");	//PLANMID
			insertStr.append("'").append(dateFormat1.format(tFpl.getDates())).append("'").append(",");		//DATES
			insertStr.append("'").append(tFpl.getPclass()).append("'").append(",");		//PCLASS
//			insertStr.append("'").append(tFpl.getPtype()).append("'").append(",");		//PTYPE  // 20131105 新设计中没有保存此字段
			insertStr.append("'FPG'").append(",");	//PTYPE  // 20131105 新设计中没有保存此字段
			insertStr.append("null").append(",");	//ACID
			insertStr.append("'").append(tFpl.getPlanstate()).append("'").append(",");		//PLANSTATE
			insertStr.append("'").append(adep).append("'").append(",");		//ADEP
			insertStr.append("'").append(ades).append("'").append(",");		//ADES
			insertStr.append( StringUtils.isNotEmpty(altn1) ? "'"+altn1+"'" :"null" ).append(",");	//ALTN1
			insertStr.append("null").append(",");	//ALTN2
			insertStr.append("null").append(",");	//ALTN3
			insertStr.append("null").append(",");	//ALTN4
			insertStr.append("'").append(tFpl.getSetd()).append("'").append(",");	//SETD
			insertStr.append("null").append(",");	//METD
			insertStr.append("'").append(tFpl.getSeta()).append("'").append(",");//SETA
			insertStr.append("null").append(",");	//META
			insertStr.append("'").append(tFpl.getSetd()).append("'").append(",");	//ATD
			insertStr.append("'").append(tFpl.getSeta()).append("'").append(",");	//ATA
			insertStr.append("'").append(tFpl.getEet()).append("'").append(",");	//EET
			insertStr.append("'").append( StringUtils.isNotEmpty(tFpl.getAcft()) ? tFpl.getAcft() :"319" ).append("'").append(",");	//ACFT
			insertStr.append("null").append(",");	//TOTAL
			insertStr.append("null").append(",");	//TURB
			if("IFR".equals(tFpl.getRule())){	//RULE
				insertStr.append("'").append("I").append("'").append(",");
			}else if("VFR".equals(tFpl.getRule())){
				insertStr.append("'").append("V").append("'").append(",");
			}else{
				insertStr.append("null").append(",");
			}
			insertStr.append("null").append(",");	//TYPES, EQUIP, SSR
			insertStr.append("null").append(",");	//TYPES, EQUIP, SSR
			insertStr.append("null").append(",");	//TYPES, EQUIP, SSR
			insertStr.append( StringUtils.isNotEmpty(tFpl.getCspd()) ? "'"+tFpl.getCspd()+"'" :"null" ).append(",");	//CSPD
			insertStr.append("'").append(tFpl.getChgt()!=null?tFpl.getChgt():1000).append("'").append(",");	//CHGT
			insertStr.append("'").append(rspcodeStr.toString()).append("'").append(",");//航线拼接字符串.
			insertStr.append("null").append(",");	//UNIT
			insertStr.append( StringUtils.isNotEmpty(tFpl.getTask()) ? "'"+tFpl.getTask()+"'" :"null" ).append(",");	//TASK
			insertStr.append("null").append(",");	//TRANSNO, 
			insertStr.append("null").append(",");	//CALLSIGN, 
			insertStr.append("null").append(",");	//COMM, 
			insertStr.append("null").append(",");	//METEO, 
			insertStr.append("null").append(",");	//SUBJECT,
			insertStr.append("null").append(",");	// COMMANDER,
			insertStr.append("null").append(",");	//DUTY, 
			insertStr.append("null").append(",");	//AIRCREW,
			insertStr.append("'0.0.0'").append(",");	//DATAVERSION
			//REMARK, INSERT_DATE, HATC_TIMESTAMP, UPDATE_NUM
			insertStr.append("'本数据为通航项目插入数据.'").append(",");
			insertStr.append("null").append(",");
			insertStr.append("null").append(",");
			insertStr.append("null").append(")");
			
			szkyUpList.add(insertStr.toString());
			
			if(rsPNums != null && rsPNums.length > 0){
				String insertTitle = "INSERT INTO T_FPL_P (PLANMID, PCODE, PTYPE, OVERHGT, OVERSPD, OVERTIME, PNUM, FORCAST_TIME) VALUES(";
				StringBuffer tpAsInsert = new StringBuffer("INSERT INTO T_TP_AS (FUA_ID, PLANMID, USAGE_SEQ, AS_UNIT_CODE, AS_UNIT_CODE_S, AS_UNIT_VERSION_NUM, AS_UNIT_TYPE, PARENT_ROUTER_CODE, ENTRANCE_POINT_CODE, ENTRANCE_POINT_TYPE, ENTRANCE_POINT_COORD, EXIT_POINT_CODE, EXIT_POINT_TYPE, EXIT_POINT_COORD, USAGE_HGT, USAGE_SPD, ENTRANCE_POINT_OVERTIME, EXIT_POINT_OVERTIME, PARENT_ROUTER_TYPE) VALUES(");
				StringBuffer sb ;
				for(int i = 0 ; i < rsPNums.length ; i++ ){
					sb = new StringBuffer();
					sb.append(insertTitle);
					sb.append("'").append(tFpl.getPlanid()).append("'").append(",");
					sb.append("'").append(rsPCodeMain[i]).append("'").append(",");
					sb.append("'").append(rsPTypeMain[i]).append("'").append(",");
					sb.append("null").append(",");
					sb.append("null").append(",");
					sb.append("null").append(",");
					sb.append("'").append(i).append("'").append(",");
					sb.append("null").append(")");
					
					szkyUpList.add(sb.toString());
					
					if(i != 0){
						//当存在航线点时,根据航线点数据.
						sb = new StringBuffer();
						sb.append(tpAsInsert.toString());
						
						List<String[]> returnList = szkydao.getObjects("select t_as_tp_seq.nextval as nextNum from dual", returnOneColumn);
						sb.append("'").append(returnList.get(0)[0]).append("',");
						sb.append("'").append(tFpl.getPlanid()).append("',");
						sb.append( i - 1 ).append(",");
						sb.append("'").append(rsPCodeMain[i-1]).append("-").append(rsPCodeMain[i]).append("',");
						sb.append("'").append(rsPCodeMain[i]).append("-").append(rsPCodeMain[i-1]).append("',");
						sb.append("'").append("0.0.0").append("',");
						sb.append(23).append(",");
						sb.append("'").append(rsPCodeMain[i-1]).append("-").append(rsPCodeMain[i]).append("',");
						sb.append("'").append(rsPCodeMain[i-1]).append("',");
						if(rsPTypeMain[i - 1].equals("9")){//为9的时候是机场,其他情况则为非机场的情况.输入数字.
							sb.append("'").append("RPD_AIRDROME").append("',");
						}else{
							sb.append("'").append("17").append("',");
						}
						sb.append("'").append(rs_PPosMain[i - 1]).append("',");
						sb.append("'").append(rsPCodeMain[i]).append("',");
						if(rsPTypeMain[i].equals("9")){//为9的时候是机场,其他情况则为非机场的情况.输入数字.
							sb.append("'").append("RPD_AIRDROME").append("',");
						}else{
							sb.append("'").append("17").append("',");
						}
						sb.append("'").append(rs_PPosMain[i]).append("',");
						sb.append("700").append(",");
						sb.append("100").append(",");
						sb.append("null").append(",");
						sb.append("null").append(",");
						if(i == 1){
							sb.append("'").append("13").append("')");
						}else if(i == rsPNums.length - 1){
							sb.append("'").append("12").append("')");
						}else{
							sb.append("'").append("300").append("')");
						}
						
						szkyUpList.add(sb.toString());
					}
				}
			}
			beanValue.addRequestMap("szkyUpList", szkyUpList);
			
//		}
	}
	
	/**
	 * 处理内容：飞行讲解内容查询
	 * @param map
	 * @return value
	 * @throws Exception
	 */
	public BeanValue searchServiceNoteList( RequestMap requestMap ) throws Exception {
		
		BeanValue beanValue = new BeanValue();
		
		// 起飞机场 ICAO_CODE
		String adepCode = requestMap.getString("adepCode");
		// 目的机场 ICAO_CODE
		String adesCode = requestMap.getString("adesCode");
		
		// TODO 建议航线算法
		
		List infoList=new ArrayList();
			
		beanValue.setForward("generalSkyWayList");
		
		return beanValue;
	}

	@Override
	public BeanValue getAdMetarInfo(RequestMap requestMap) throws Exception {
			
		BeanValue beanValue = new BeanValue();
		String  icaoCode=requestMap.getString("icaoCode");
		String wmDesc="";
		String wmDecode="";
		if (StringUtils.isNotEmpty(icaoCode)) {
			List<String[]> temp =dao.getObjects("select t.wm_desc,t.wm_decode from t_weather_metar t where t.icao_code='"+icaoCode+"' order by t.wm_id desc", new String[]{"wm_desc","wm_decode"});
			if(temp != null && temp.size() > 0){
				wmDesc = temp.get(0)[0];
				wmDecode = temp.get(0)[1];
			}
		}
		
		beanValue.addRequestMap("wmDesc", wmDesc);
		beanValue.addRequestMap("wmDecode", wmDecode);
		
	//	  /** 记录日志 */ 
	//	requestMap.addParameter(ProjectConstants.LOG_TEXT, "常规航线信息");
		
		return beanValue;
	}
	
	
	@Override
	public BeanValue getAdIdInfo(RequestMap requestMap) throws Exception {
			
		BeanValue beanValue = new BeanValue();
		String  icaoCode=requestMap.getString("icaoCode");
		String adId="";
		if (StringUtils.isNotEmpty(icaoCode)) {
			List<String[]> temp =dao.getObjects("select t.objectid from t_ad t where t.icao_code ='"+icaoCode.trim()+"'", new String[]{"objectid"});
			if(temp != null && temp.size() > 0){
				adId = temp.get(0)[0];
			}
		}
		beanValue.addRequestMap("adId", adId);
		
	//	  /** 记录日志 */ 
	//	requestMap.addParameter(ProjectConstants.LOG_TEXT, "常规航线信息");
		
		return beanValue;
	}
}
