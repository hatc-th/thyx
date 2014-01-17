package com.hatc.thyx.service.flyplan.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.hatc.base.common.BeanValue;
import com.hatc.base.common.RequestMap;
import com.hatc.base.common.RollPage;
import com.hatc.base.utils.ConvertLang;
import com.hatc.common.service.impl.ProjectManagerImpl;
import com.hatc.hibernate.vo.AdTreeVO;
import com.hatc.hibernate.vo.AttachmentVO;
import com.hatc.hibernate.vo.GeneralSkyWayPointVO;
import com.hatc.hibernate.vo.GeneralSkyWayVO;
import com.hatc.thyx.service.flyplan.ADManager;

public class ADManagerImpl extends ProjectManagerImpl implements ADManager {
	
	//查询管制区，管制分区，机场的三级结构
	public BeanValue getADInfo(RequestMap map) throws Exception {
		String ccaSql = " SELECT ca_id id,null pId,ca_name  name FROM t_cca ";//管制区
		String[] column = new String[]{"id","pId","name"};
		String[] adColumn = new String[]{"id","code","name","pos","type"};
		List<String[]> ccaArray = dao.getObjects(ccaSql, column);//管制区查询结果
		List<String[]> fcaArray = null;	//管制分区查询结果
		List<String[]> adArray = null;	//机场查询结果
		List<AdTreeVO> ccaList = new ArrayList<AdTreeVO>();
		AdTreeVO cca = null;
		String pId = "";
		for(String[] strs:ccaArray){
			cca = new AdTreeVO();
			cca.setId(strs[0]);
			cca.setPId(strs[1]);
			cca.setName(strs[2]);
			ccaList.add(cca);
			
			pId = strs[0];
			String fcaSql = " SELECT ca_code id,uca_id pId,ca_name name FROM t_fca WHERE uca_id =  '" + pId + "'"; //管制分区
			fcaArray = dao.getObjects(fcaSql,column);
			AdTreeVO fca = null;
			List<AdTreeVO> fcaList = null;
			if(fcaArray != null && fcaArray.size() > 0){
				fcaList = new ArrayList<AdTreeVO>();
				for(String[] strs2:fcaArray){
					fca = new AdTreeVO();
					fca.setId(strs2[0]);
					fca.setPId(strs2[1]);
					fca.setName(strs2[2]);
					fcaList.add(fca);
					
					pId = fca.getId();
					String adSql = " SELECT objectid id,icao_code code,ad_name name,ad_arp_pos pos,ad_type type FROM t_ad WHERE ca_id =  '" + pId + "'";//机场
					adArray = dao.getObjects(adSql,adColumn);
					List<AdTreeVO> adList = null;
					if(adArray != null && adArray.size() > 0){
						AdTreeVO ad = null;
						adList = new ArrayList<AdTreeVO>();
						for(String[] strs3:adArray){
							ad = new AdTreeVO();
							ad.setId(strs3[0]);
							ad.setCode(strs3[1]);
							ad.setPId(pId);
							ad.setName(strs3[2]);
							ad.setPos(strs3[3]);
							ad.setType(strs3[4]);
							adList.add(ad);
						}
						fca.setChildrenNodeList(adList);
					}
				}
				cca.setChildrenNodeList(fcaList);
			}
		}
		BeanValue beanValue = new BeanValue();
		beanValue.addParamMap("ccaList", ccaList);
		beanValue.addParamMap("isDep", map.getString("isDep"));
		
		String adName = map.getString("adName");
		if(StringUtils.isNotBlank(adName)){
			adName = new String(adName.getBytes("ISO-8859-1"), "GBK");
		}
		beanValue.addRequestMap("adName", adName);
		return beanValue;
	}
	
	
	//查询用户的常用航线
	public BeanValue getGeneralSkyWays(RequestMap requestMap) throws Exception {

		BeanValue beanValue = new BeanValue();
		String userId = "U10001205";
		String adep = requestMap.getString("adep");
		String ades = requestMap.getString("ades");
		
		StringBuffer sqlBuff = new StringBuffer("select t.rr_id,t.rr_code,t.rr_name,t.rr_dep,t.rr_des from T_Regular_Rs t" +
				" where t.rr_user='"+userId+"'");
		
		if(StringUtils.isNotEmpty(adep)) {
			sqlBuff.append(" and t.rr_dep='"+adep.trim()+"'");
		}
		if(StringUtils.isNotEmpty(ades)) {
			sqlBuff.append(" and t.rr_des='"+ades.trim()+"'");
		}
		sqlBuff.append(" order by t.create_date desc ");
		
		RollPage rollPage = initRollPage(requestMap);
		
		List infoList=new ArrayList();
		List<String[]> temp2 =dao.getObjects(rollPage,sqlBuff.toString(), new String[]{"rr_id","rr_code","rr_name","rr_dep","rr_des"});
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
				List<String[]> temp =dao.getObjects("select tf.rs_p_id,tf.rs_p_name,tf.rs_p_num,tf.rs_p_pos,tf.rs_p_type,tf.rs_p_code" +
						" from t_fpl_rs_p tf where tf.rr_id="+skyWayVO.getRrId()+" order by tf.rs_p_num"
						, new String[]{"rs_p_id","rs_p_name","rs_p_pos","rs_p_num","rs_p_type","rs_p_code"});
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
		
		//beanValue.addRequestMap("pageCur", pageCur);
		beanValue.addRequestMap("adep", adep);
		beanValue.addRequestMap("ades", ades);
		beanValue.addRequestMap("rollPage", rollPage);
		beanValue.addRequestMap("infoList", infoList);
		beanValue.setForward("generalRRList");
		return beanValue;
	}

	public BeanValue initSkyWayByGaneral(RequestMap requestMap)
			throws Exception {
		BeanValue beanValue = new BeanValue();
		String  rrIds=requestMap.getString("rrIds");
		List skyWay=new ArrayList();
		if (StringUtils.isNotEmpty(rrIds)) {
			String[] stemp1=rrIds.split(";");
			for(String s: stemp1){
				GeneralSkyWayVO skyWayVO=new GeneralSkyWayVO();
				String[] stemp2=s.split(":");
				StringBuffer sqlBuff = new StringBuffer("select t.rr_id,t.rr_code,t.rr_name,t.rr_dep,t.rr_des,'"+stemp2[0]+"' as rr_type from T_Regular_Rs t" +
						" where t.rr_id="+stemp2[1]);
				
				List<String[]> temp2 =dao.getObjects(sqlBuff.toString(), new String[]{"rr_id","rr_code","rr_name","rr_dep","rr_des","rr_type"});
				if(temp2 != null && temp2.size() > 0){
					
					skyWayVO.setRrId(temp2.get(0)[0]);
					skyWayVO.setRrCode(temp2.get(0)[1]);
					skyWayVO.setRrName(temp2.get(0)[2]);
					skyWayVO.setRrDep(temp2.get(0)[3]);
					skyWayVO.setRrDes(temp2.get(0)[4]);
					skyWayVO.setRrType(temp2.get(0)[5]);
					List temp1=new ArrayList();
					List<String[]> temp =dao.getObjects("select tf.rs_p_id,tf.rs_p_name,tf.rs_p_num,tf.rs_p_pos,tf.rs_p_type,tf.rs_p_code" +
							" from t_fpl_rs_p tf where tf.rr_id="+skyWayVO.getRrId()+" order by tf.rs_p_num"
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
		beanValue.addRequestMap("skyWay", skyWay);
		return beanValue;
	}
	
	//查询导航台或者坐标点
	public BeanValue getNVOrFFXList(RequestMap requestMap) throws Exception {
		String type = requestMap.getString("type");
		String qName = requestMap.getString("qName");
		String method = requestMap.getString("method");  //万恶的GKB编码！！！
		if(StringUtils.isNotBlank(qName) && method.equals("GET")){//GET需要这样处理，否则乱码，POST方式不处理
			qName = new String(qName.getBytes("ISO-8859-1"), "utf-8");
		}
		StringBuffer sql = new StringBuffer();;
		String[] columns = null;
		RollPage rollPage = initRollPage(requestMap);

		if(type == null || (type !=null && type.equals("nv"))){//导航台查询
			columns = new String[]{"objectid","nv_id","nv_code","nv_name","nv_pos","nv_type","nv_elev","status"};
			sql.append(" SELECT objectid,nv_id,nv_code,nv_name,nv_pos,trim('18') nv_type,nv_elev,status FROM t_nv WHERE 1 = 1 ");
			if(StringUtils.isNotBlank(qName)){
				sql.append(" AND nv_name LIKE  '%").append(qName).append("%' ");
			}
		}else{//地标点查询
			columns = new String[]{"objectid","ffx_id","fx_code","fx_name","fx_pos","fx_type","fx_alt","status"};
			sql.append(" SELECT objectid,ffx_id,fx_code,fx_name,fx_pos,trim('20') fx_type,fx_alt,status FROM t_ffx WHERE 1 = 1 ");
			if(StringUtils.isNotBlank(qName)){
				sql.append(" AND fx_name LIKE  '%").append(qName).append("%' ");
			}
		}
		List<String[]> resultList = dao.getObjects(rollPage,sql.toString(), columns);
		
		BeanValue beanValue = new BeanValue();
		beanValue.addParamMap("resultList", resultList);
		beanValue.addParamMap("type", type);
		beanValue.addParamMap("qName", qName==null?"":qName);
		beanValue.setForward("chooseNVOrFFX");
		beanValue.addParamMap("rollPage", rollPage);
		return beanValue;
	}
	
	private RollPage initRollPage(RequestMap requestMap){
		RollPage rollPage = new RollPage();
		String pageCur = requestMap.getString("pageCur");// 获取页数
		String pageBool = requestMap.getString("pageBool");// 获取翻页标志
		String pagePer = requestMap.getString("pagePer");
		// 将页数转换成为数字
		if (pageCur == null || pageCur.equals("")) {
			rollPage.setPageCur(0);
		} else {
			rollPage.setPageCur(ConvertLang.convertint(pageCur));
		}
		if (pageBool == null || pageBool.equals("")) {
			rollPage.setPageBool("0");
		} else {
			rollPage.setPageBool(pageBool);
		}
		
		if(StringUtils.isNotBlank(pagePer)){
			rollPage.setPagePer(Integer.parseInt(pagePer));
		}else{
			rollPage.setPagePer(10);
		}
		return rollPage;
	}

	//根据机场名字或者ICAO码查询机场列表信息
	public BeanValue getADListByName(RequestMap requestMap) throws Exception {
		String param = requestMap.getString("q");  //机场名字或者ICAO_CODE
		param = new String(param.getBytes("ISO-8859-1"),"utf-8");
		String sql = " SELECT objectid,ad_id,icao_code,ad_name,ad_arp_pos FROM t_ad WHERE ad_name LIKE  '%"
					+  param + "%' OR icao_code like '%" + param.toUpperCase() + "%'";
		String[] column = new String[]{"objectid","ad_id","icao_code","ad_name","ad_arp_pos"}; 
		List<String[]> adList = dao.getObjects(sql,column);
		
		BeanValue beanValue = new BeanValue();
		beanValue.addParamMap("adList", adList);
		return beanValue;
	}

	//根据名称查询导航台或地标点信息
	public BeanValue getNVListByName(RequestMap requestMap) throws Exception {
		StringBuffer sql = new StringBuffer();
		String param = requestMap.getString("q");
		param = new String(param.getBytes("ISO-8859-1"),"utf-8");
		
		String type  = requestMap.getString("type");
		if(StringUtils.isBlank(type)){ //查询导航台和地标点
//			type = "nv";
			sql.append(" SELECT objectid id,nv_name name FROM t_nv WHERE nv_name LIKE '%").append(param).append("%' ");
			sql.append("  UNION ALL ");
			sql.append(" SELECT objectid id,fx_name name FROM t_ffx WHERE fx_name LIKE '%").append(param).append("%' ");
		}else{
			if(type.equals("nv")){//导航台
				sql.append(" SELECT objectid id,nv_name name FROM t_nv WHERE nv_name LIKE '%").append(param).append("%' ");
			}
			if(type.equals("ffx")){//地标点
				sql.append(" SELECT objectid id,fx_name name FROM t_ffx WHERE fx_name LIKE '%").append(param).append("%' ");
			}
		}
		String[] column = new String[]{"id","name"};
		List<String[]> nvList = dao.getObjects(sql.toString(),column);
		
		BeanValue beanValue = new BeanValue();
		beanValue.addParamMap("nvList", nvList);
//		beanValue.addParamMap("type", type);
		return beanValue;
	}

	/**
	 * 查询机场（导航台，地标点）的各种相关的图形信息(AP:航拍图，FS:飞行程序图，DS:设计图)
	 * */
	@Override
	public BeanValue getADAttachment(RequestMap requestMap) throws Exception {
		String attachmentType = requestMap.getString("attachmentType");
		String ICAO = requestMap.getString("ICAO");
		String objectType = requestMap.getString("objectType");
		
		String[] column = new String[]{"a_id","a_path","a_content","a_remark"};
		StringBuffer sql = new StringBuffer("");
		sql.append(" SELECT a_id,a_path,a_content,a_remark FROM t_attachment ");
		sql.append(" WHERE a_type =  '").append(attachmentType).append("' AND a_obj_type  = '");
		sql.append(objectType).append("' AND a_state = 1 ").append(" AND a_objid = '").append(ICAO).append("' ");
		
		BeanValue value = new BeanValue();
		value.addParamMap("attachmentType", attachmentType);
		value.addParamMap("objectType", objectType);
		value.addParamMap("objectId", ICAO);
		
		List<String[]> attachments = dao.getObjects(sql.toString(),column);
		if(attachments != null && attachments.size() > 0){
			AttachmentVO attachment = null;
			for(String[] strs:attachments){
				attachment = new AttachmentVO();
				attachment.setAttachmentId(Long.valueOf(strs[0]));
				attachment.setAttachmentPath(strs[1]);
				attachment.setAttachmentContent(strs[2]);
				attachment.setAttachmentRemark(strs[3]);
				break;
			}
			value.addParamMap("attachment", attachment);
			value.addParamMap("flag","1" );
		}else{
			value.addParamMap("flag","0" );
		}
		if(attachmentType.equals("AP")){
			value.setForward("showAirPicture");
		}else{
			value.setForward("showAirPDF");
		}
		
		String[] adBaseInfo = getADBaseInfo(objectType, ICAO);
		value.addParamMap("adBaseInfo", adBaseInfo);
		return value;
	}
	
	/*
	 * 查询机场（导航台，地标点）的基本信息
	 * @param adType[AD,NV,FX]
	 * @param code 编码
	 * throw Exception
	 * @return String[code,name,pos,height];
	 * */
	private String[] getADBaseInfo(String adType,String code)throws Exception{
		String[] column = new String[]{"code","name","pos","height"};
		String sql = "";
		if(adType.equals("AD")){//机场
			sql = " SELECT t.icao_code code,t.ad_name name,t.ad_arp_pos pos,t.ad_elev height FROM t_ad t WHERE t.icao_code = '" + code + "' ";
		}else if(adType.equals("NV")){//导航台
			sql = "  SELECT t.nv_code code,t.nv_name name,t.nv_pos pos,t.nv_elev height FROM t_nv t WHERE t.nv_code = '" + code + "' ";
		}else if(adType.equals("FX")){//地标点
			sql = " SELECT t.fx_code code,t.fx_name name,t.fx_pos pos,t.fx_alt height FROM t_ffx t WHERE t.fx_code ='" + code + "' ";
		}else{
			return null;
		}
		
		List<String[]> list = dao.getObjects(sql, column);
		if(list != null && list.size() > 0){
			return list.get(0);
		}
		return new String[]{"","","",""};
	}
	
}
