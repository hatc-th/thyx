package com.hatc.common.contants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.hatc.common.businessdata.Code;
import com.hatc.common.code.CodeCondition;
import com.hatc.common.service.CodeService;
import com.hatc.common.servicestub.ReqIdentity;
import com.hatc.common.servicestub.util.SqlCondition;
import com.opensymphony.xwork2.inject.Inject;

/**
* 
* <b>system：</b>      协同办公平台<br/>
* <b>description：</b> 字典表实现类<br/>
* <b>author：</b>      王洋<br/>
* <b>copyright：</b>	　 北京华安天诚科技有限公司<br/>
* <b>version：</b>     VER1.00 2010-04-06<br/>
*
**/
public class ProjectItemCode implements java.io.Serializable {

	private static Log logger = LogFactory.getLog(ProjectItemCode.class);
	
	private static final long serialVersionUID = -105038452255205305L;

	public static Map<String, List<Code>> codeList = new HashMap<String, List<Code>>();

	public static Map<String, Map<String, Code>> codeMap = new HashMap<String, Map<String, Code>>();
	
	public static Map<String, List<Code>> codeList2 = new HashMap<String, List<Code>>();

	public static Map<String, Map<String, Code>> codeMap2 = new HashMap<String, Map<String, Code>>();
	
	public static int useCacheCount = 0;
	
	private CodeService codeService;
	
	public void setCodeService(CodeService codeService ){
		this.codeService=codeService;
	}
/**
 * 防止用户同时登陆时,造成数据重复.synchronized==加锁.
 * 2011-12-23 去掉synchronized, 通过定时线程来获取代码数据.
 * */
	public synchronized void init(ReqIdentity identity) throws Exception 
	{
	   //public synchronized static void init(ReqIdentity identity) throws Exception {


		// 查询字典表
		
		ArrayList<Code> codes = new ArrayList<Code>();
		
		SqlCondition condition = CodeCondition.getInstance().getSqlCondition();
		/**
		
		SqlCondition condition = new SqlCondition();
		ArrayList<SortCmd> orders = new ArrayList<SortCmd>();
		SortCmd order = new SortCmd();
		order.fieldName = "CLASS_ID";
		order.order = "ASC";
		orders.add(order);
		condition.setOrders(orders);
		order = new SortCmd();
		order.fieldName = "ORDER_ID";
		order.order = "ASC";
		orders.add(order);
		condition.setOrders(orders);  
		 **/
		identity.setFunctionId("S999999");
		
		int codesNum = 0;
		
		//if(DebugConfig.debugFlag==true)
		//    System.out.println("codeStub.getCodes............");
		
		codeService.getCodes(identity, condition, codes);
		//if(codes!=null)
		//	logger.debug("codes.size()............" + codes.size());
		 
		
		codeList2 = codeList;
		codeMap2 = codeMap;
		
		//if(DebugConfig.debugFlag==true)  
		//    System.out.println("codes.size()............" + codes.size());
		codeList.clear();
		codeMap.clear();

		
		List<Code> codesList = null;
		Map<String, Code> codesMap = null;
		
		for (Code code : codes) {
			// 条件List
			codesList = codeList.get(code.getClass_id());
			//if(DebugConfig.debugFlag==true)
			//    System.out.println("" + (codesNum++) +" : " +  code.getClass_id() + " : " + code.getClass_name() + " : "+ code.getName());
			
			if (codesList == null) {
				codesList = new ArrayList<Code>();
				codeList.put(code.getClass_id(), codesList);
			}
			codesList.add(code);
			// 转换Map
			codesMap = codeMap.get(code.getClass_id());
			if (codesMap == null) {
				codesMap = new HashMap<String, Code>();
				codeMap.put(code.getClass_id(), codesMap);
				
			}
			codesMap.put(code.getCode_id(), code);
		}
	}

	public static List<Code> getCodeList(String key) {
		return codeList.get(key);
	}

	public static Map<String, Code> getCodeMap(String key) {
		return codeMap.get(key);
	}

	public static Code getCode(String class_key, String key) {
		 
		 if(codeMap.get(class_key)==null)
		 {
			 //System.out.println("load cache.................................................");
			 if(codeMap2.get(class_key) !=null)
			 {
				 useCacheCount++;
				 return codeMap2.get(class_key).get(key);
			 }
			 else
				 return null;
		 }
		 else
		    return codeMap.get(class_key).get(key);
	}

	public static String getCodeName(String class_key, String key) {
		Code code = getCode(class_key, key);
		return null == code ? "" : code.getName();
	}
}
