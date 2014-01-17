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
	  * �������ݣ���ѯ���мƻ���˹����б�
	  * 
	  * @param 
	  * @return 
	  * 
	  */
	public ActionForward searchList(ActionMapping actionMapping, BaseForm baseForm, HttpServletRequest request,HttpServletResponse
			response){		
		// ����ҵ����
		ActionDispose actionDispose = new ExecuteProcess();
		// ����Request ����
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
		// �������request �����е���Ϣ����value
		disposeRequest(request, value);
		// ת��ָ�����
		return actionMapping.findForward(value.getForward());

	} 
	
	/**
	  * �������ݣ���ѯ���мƻ���Ϣ
	  * 
	  * @param 
	  * @return 
	  * 
	  */
	public ActionForward initPlanInfo(ActionMapping actionMapping, BaseForm baseForm, HttpServletRequest request,HttpServletResponse
			response){		
		// ����ҵ����
		ActionDispose actionDispose = new ExecuteProcess();
		// ����Request ����
		RequestMap map = new RequestMap();
		BeanValue value = new BeanValue();
		try {
			map = actionDispose.initRequest(request, servlet);
			PlanAuditManager planAuditManager = (PlanAuditManager) getBean("planAuditManager");
			value = planAuditManager.initPlanInfo(map);
			String type= map.getString("type");
			if(StringUtils.isNotEmpty(type)) {
				value.addRequestMap("moduleTitle", "���мƻ���ϸ��Ϣ");
			} else {
				value.addRequestMap("moduleTitle", "���мƻ������Ϣ");
			}
		} catch (Exception e) {
			String managerType = map.getString("managerType");
			value.addParamMap("managerType", managerType);
			
			String planId = map.getString("planId");
			value.addParamMap("planId", planId);
			processException(map, value, e, className + ".initPlanInfo.error",TtimsCode.INIT_PLANINFO_ERROR);
			
	    }
		// �������request �����е���Ϣ����value
		disposeRequest(request, value);
		// ת��ָ�����
		return actionMapping.findForward(value.getForward());

	} 
	
	/**
	  * �������ݣ���˱��洦��
	  * 
	  * @param 
	  * @return 
	  * 
	  */
	public ActionForward saveAuditFlow(ActionMapping actionMapping, BaseForm baseForm, HttpServletRequest request,HttpServletResponse
			response){		
		// ����ҵ����
		ActionDispose actionDispose = new ExecuteProcess();
		// ����Request ����
		RequestMap map = new RequestMap();
		BeanValue value = new BeanValue();
		try {
			map = actionDispose.initRequest(request, servlet);
			PlanAuditManager planAuditManager = (PlanAuditManager) getBean("planAuditManager");
			value = planAuditManager.saveAuditFlow(map);
			
			// �����ϼ����ݿ��ظ��ƻ����Ӽƻ�
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
		// �������request �����е���Ϣ����value
		disposeRequest(request, value);
		// ת��ָ�����
		return actionMapping.findForward(value.getForward());

	} 
	
	/**
	  * �������ݣ��鿴������ϸ��Ϣ
	  * 
	  * @param 
	  * @return 
	  * 
	  */
	public ActionForward flowDetail(ActionMapping actionMapping, BaseForm baseForm, HttpServletRequest request,HttpServletResponse
			response){		
		// ����ҵ����
		ActionDispose actionDispose = new ExecuteProcess();
		// ����Request ����
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
		// �������request �����е���Ϣ����value
		disposeRequest(request, value);
		// ת��ָ�����
		return actionMapping.findForward(value.getForward());

	}
	
	/**
	 * ���з��񽲽�ҳ���ʼ��
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initFlyServiceInfo(ActionMapping actionMapping, BaseForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		// ����ҵ����
		ActionDispose actionDispose = new ExecuteProcess();
		// ����Request ����
		RequestMap map = new RequestMap();
		BeanValue value = new BeanValue();
		try {
			// ʹ������ע�������ö���
			map = actionDispose.initRequest(request, servlet);
			PlanAuditManager planAuditManager = (PlanAuditManager) getBean("planAuditManager");
			value = planAuditManager.initFlyServiceInfo(map);
			
		} catch (Exception e) {
			processException(map, value, e, className + ".initFlyPlanBaseInfo", TtimsCode.FLYPLAN_INIT_ERROR);
		}	
		
		// �������request �����е���Ϣ����value
		disposeRequest(request, value);
		// ת��ָ�����
		return actionMapping.findForward(value.getForward());
		
	}
	
	/**
	 * ���з��񽲽����ݲ�ѯ
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward searchFlyServiceInfo(ActionMapping actionMapping, BaseForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		// ����ҵ����
		ActionDispose actionDispose = new ExecuteProcess();
		// ����Request ����
		RequestMap map = new RequestMap();
		BeanValue value = new BeanValue();
		Map mapinfo = new HashMap();
		try{
			// ʹ������ע�������ö���
			map = actionDispose.initRequest(request, servlet);
			PlanAuditManager planAuditManager = (PlanAuditManager) getBean("planAuditManager");
			value = planAuditManager.searchFlyServiceInfo(map);
			
			mapinfo.put("returnflag","true");
			// ���ؽ�������
			mapinfo.put("notesStr",(String) value.getRequestMap("notesStr"));
		} catch(Exception e) {
			mapinfo.put("returnflag","false");
            log.error(e);
        }
		
		this.ajaxReturn((Object)mapinfo,response);
		// ת��ָ�����
		return null;
		
	}
	
	/**
	 * ���з��񽲽Ᵽ�洦��
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward saveFlyServiceInfo(ActionMapping actionMapping, BaseForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		// ����ҵ����
		ActionDispose actionDispose = new ExecuteProcess();
		// ����Request ����
		RequestMap map = new RequestMap();
		BeanValue value = new BeanValue();
		Map mapinfo = new HashMap();
		try {
			// ʹ������ע�������ö���
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
		// ת��ָ�����
		return null;
	}
}
