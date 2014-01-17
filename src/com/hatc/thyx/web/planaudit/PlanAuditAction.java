package com.hatc.thyx.web.planaudit;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.hatc.base.common.BeanValue;
import com.hatc.base.common.RequestMap;
import com.hatc.base.web.action.ActionDispose;
import com.hatc.base.web.action.ExecuteProcess;
import com.hatc.base.web.form.BaseForm;
import com.hatc.common.web.action.BaseAction;
import com.hatc.contants.TtimsCode;
import com.hatc.thyx.service.flyplan.FlyPlanManager;
import com.hatc.thyx.service.planaudit.PlanAuditManager;

public class PlanAuditAction extends BaseAction {
	
	private String className = this.getClass().getName();
	
	/**
	  * 处理内容：查询飞行计划审核管理单列表
	  * 
	  * @param 
	  * @return 
	  * 
	  */
	public ActionForward searchList(ActionMapping actionMapping, BaseForm baseForm, HttpServletRequest request,HttpServletResponse
			response){		
		// 定义业务流
		ActionDispose actionDispose = new ExecuteProcess();
		// 解析Request 请求
		RequestMap map = new RequestMap();
		BeanValue value = new BeanValue();
		try {
			map = actionDispose.initRequest(request, servlet);
			PlanAuditManager planAuditManager = (PlanAuditManager) getBean("planAuditManager");
			value = planAuditManager.searchList(map);
		} catch (Exception e) {
			String managerType = map.getString("managerType");
			value.addParamMap("managerType", managerType);
			processException(map, value, e, className + ".searchList.error",TtimsCode.SEARCH_PLANAUDIT_ERROR);
			
	    }
		// 处理放入request 请求中的信息集合value
		disposeRequest(request, value);
		// 转至指定层次
		return actionMapping.findForward(value.getForward());

	} 
	
	/**
	  * 处理内容：查询飞行计划信息
	  * 
	  * @param 
	  * @return 
	  * 
	  */
	public ActionForward initPlanInfo(ActionMapping actionMapping, BaseForm baseForm, HttpServletRequest request,HttpServletResponse
			response){		
		// 定义业务流
		ActionDispose actionDispose = new ExecuteProcess();
		// 解析Request 请求
		RequestMap map = new RequestMap();
		BeanValue value = new BeanValue();
		try {
			map = actionDispose.initRequest(request, servlet);
			PlanAuditManager planAuditManager = (PlanAuditManager) getBean("planAuditManager");
			value = planAuditManager.initPlanInfo(map);
			String type= map.getString("type");
			if(StringUtils.isNotEmpty(type)) {
				value.addRequestMap("moduleTitle", "飞行计划详细信息");
			} else {
				value.addRequestMap("moduleTitle", "飞行计划审核信息");
			}
		} catch (Exception e) {
			String managerType = map.getString("managerType");
			value.addParamMap("managerType", managerType);
			
			String planId = map.getString("planId");
			value.addParamMap("planId", planId);
			processException(map, value, e, className + ".initPlanInfo.error",TtimsCode.INIT_PLANINFO_ERROR);
			
	    }
		// 处理放入request 请求中的信息集合value
		disposeRequest(request, value);
		// 转至指定层次
		return actionMapping.findForward(value.getForward());

	} 
	
	/**
	  * 处理内容：审核保存处理
	  * 
	  * @param 
	  * @return 
	  * 
	  */
	public ActionForward saveAuditFlow(ActionMapping actionMapping, BaseForm baseForm, HttpServletRequest request,HttpServletResponse
			response){		
		// 定义业务流
		ActionDispose actionDispose = new ExecuteProcess();
		// 解析Request 请求
		RequestMap map = new RequestMap();
		BeanValue value = new BeanValue();
		try {
			map = actionDispose.initRequest(request, servlet);
			PlanAuditManager planAuditManager = (PlanAuditManager) getBean("planAuditManager");
			value = planAuditManager.saveAuditFlow(map);
			
			// 插入上级数据库重复计划的子计划
			String planId = map.getString("planId");
			planAuditManager.updateSuperData(planId);
			processSucceed(value, className + ".saveAuditFlow.success", TtimsCode.SAVE_FLOW_SUCCESS);
		} catch (Exception e) {
			String managerType = map.getString("managerType");
			value.addParamMap("managerType", managerType);
			
			String planId = map.getString("planId");
			value.addParamMap("planId", planId);
			processException(map, value, e, className + ".saveAuditFlow.error",TtimsCode.SAVE_FLOW_ERROR);
			
	    }
		// 处理放入request 请求中的信息集合value
		disposeRequest(request, value);
		// 转至指定层次
		return actionMapping.findForward(value.getForward());

	} 
	
	/**
	  * 处理内容：查看审批详细信息
	  * 
	  * @param 
	  * @return 
	  * 
	  */
	public ActionForward flowDetail(ActionMapping actionMapping, BaseForm baseForm, HttpServletRequest request,HttpServletResponse
			response){		
		// 定义业务流
		ActionDispose actionDispose = new ExecuteProcess();
		// 解析Request 请求
		RequestMap map = new RequestMap();
		BeanValue value = new BeanValue();
		try {
			map = actionDispose.initRequest(request, servlet);
			PlanAuditManager planAuditManager = (PlanAuditManager) getBean("planAuditManager");
			value = planAuditManager.flowDetail(map);
			
		} catch (Exception e) {
			String managerType = map.getString("managerType");
			value.addParamMap("managerType", managerType);
			
			String planId = map.getString("planId");
			value.addParamMap("planId", planId);
			processException(map, value, e, className + ".flowDetail.error",TtimsCode.SEARCH_FLOW_ERROR);
			
	    }
		// 处理放入request 请求中的信息集合value
		disposeRequest(request, value);
		// 转至指定层次
		return actionMapping.findForward(value.getForward());

	}
	
	/**
	 * 飞行服务讲解页面初始化
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initFlyServiceInfo(ActionMapping actionMapping, BaseForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 定义业务流
		ActionDispose actionDispose = new ExecuteProcess();
		// 解析Request 请求
		RequestMap map = new RequestMap();
		BeanValue value = new BeanValue();
		try {
			// 使用依赖注入来配置对象
			map = actionDispose.initRequest(request, servlet);
			PlanAuditManager planAuditManager = (PlanAuditManager) getBean("planAuditManager");
			value = planAuditManager.initFlyServiceInfo(map);
			
		} catch (Exception e) {
			processException(map, value, e, className + ".initFlyPlanBaseInfo", TtimsCode.FLYPLAN_INIT_ERROR);
		}	
		
		// 处理放入request 请求中的信息集合value
		disposeRequest(request, value);
		// 转至指定层次
		return actionMapping.findForward(value.getForward());
		
	}
	
	/**
	 * 飞行服务讲解内容查询
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward searchFlyServiceInfo(ActionMapping actionMapping, BaseForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 定义业务流
		ActionDispose actionDispose = new ExecuteProcess();
		// 解析Request 请求
		RequestMap map = new RequestMap();
		BeanValue value = new BeanValue();
		Map mapinfo = new HashMap();
		try{
			// 使用依赖注入来配置对象
			map = actionDispose.initRequest(request, servlet);
			PlanAuditManager planAuditManager = (PlanAuditManager) getBean("planAuditManager");
			value = planAuditManager.searchFlyServiceInfo(map);
			
			mapinfo.put("returnflag","true");
			// 返回讲解内容
			mapinfo.put("notesStr",(String) value.getRequestMap("notesStr"));
		} catch(Exception e) {
			mapinfo.put("returnflag","false");
            log.error(e);
        }
		
		this.ajaxReturn((Object)mapinfo,response);
		// 转至指定层次
		return null;
		
	}
	
	/**
	 * 飞行服务讲解保存处理
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward saveFlyServiceInfo(ActionMapping actionMapping, BaseForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 定义业务流
		ActionDispose actionDispose = new ExecuteProcess();
		// 解析Request 请求
		RequestMap map = new RequestMap();
		BeanValue value = new BeanValue();
		Map mapinfo = new HashMap();
		try {
			// 使用依赖注入来配置对象
			map = actionDispose.initRequest(request, servlet);
			PlanAuditManager planAuditManager = (PlanAuditManager) getBean("planAuditManager");
			value = planAuditManager.saveFlyServiceInfo(map);
			mapinfo.put("returnflag","true");
			mapinfo.put("fiId", value.getRequestMap("fiId"));
		} catch (Exception e) {
			mapinfo.put("returnflag","false");
			log.error(e);
		}	
		
		this.ajaxReturn((Object)mapinfo,response);
		// 转至指定层次
		return null;
	}
}
