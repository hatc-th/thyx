package com.hatc.thyx.web.actflyplan;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.hatc.base.common.BeanValue;
import com.hatc.base.common.RequestMap;
import com.hatc.base.web.action.ActionDispose;
import com.hatc.base.web.action.ExecuteProcess;
import com.hatc.base.web.form.BaseForm;
import com.hatc.common.web.action.BaseAction;
import com.hatc.contants.TtimsCode;
import com.hatc.thyx.service.actflyplan.ActFlyPlanManager;

public class ActFlyPlanAction extends  BaseAction{
	
	/**
	 * ʵʩǰȷ�ϲ�ѯ�б�
	 * @param aActionMapping
	 * @param aBaseForm
	 * @param aHttpServletRequest
	 * @param aHttpServletResponse
	 * @return
	 * @throws Exception
	 */
    public ActionForward queryActFlyPlanList(ActionMapping aActionMapping, BaseForm aBaseForm,
            HttpServletRequest aHttpServletRequest, HttpServletResponse aHttpServletResponse) throws Exception {
        // ����ҵ����
        ActionDispose aActionDispose = new ExecuteProcess();
        // ����Request ����
        RequestMap aRequestMap = new RequestMap();
        BeanValue aBeanValue = new BeanValue();
        try {
            // ʹ������ע�������ö���
            aRequestMap = aActionDispose.initRequest(aHttpServletRequest, servlet);
            ActFlyPlanManager actFlyPlanManager = (ActFlyPlanManager)getBean("actFlyPlanManager");
            aBeanValue = actFlyPlanManager.queryAffirmBeforeActPlanList(aRequestMap);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        // �������request �����е���Ϣ����value
        disposeRequest(aHttpServletRequest, aBeanValue);
        // ת��ָ�����
    	return aActionMapping.findForward(aBeanValue.getForward());
    }
    /**
     * ʵʩȷ�ϲ���
     * @param aActionMapping
     * @param aBaseForm
     * @param aHttpServletRequest
     * @param aHttpServletResponse
     * @return
     * @throws Exception
     */
    public ActionForward affirmBeforeAct(ActionMapping aActionMapping, BaseForm aBaseForm,
    		HttpServletRequest aHttpServletRequest, HttpServletResponse aHttpServletResponse) throws Exception {
    	// ����ҵ����
    	ActionDispose aActionDispose = new ExecuteProcess();
    	// ����Request ����
    	RequestMap aRequestMap = new RequestMap();
    	BeanValue aBeanValue = new BeanValue();
    	Map mapinfo = new HashMap();
    	try {
    		// ʹ������ע�������ö���
    		aRequestMap = aActionDispose.initRequest(aHttpServletRequest, servlet);
    		ActFlyPlanManager actFlyPlanManager = (ActFlyPlanManager)getBean("actFlyPlanManager");
    		aBeanValue = actFlyPlanManager.saveAffirmBeforeAct(aRequestMap);
//    		String selectFlag = aRequestMap.getString("selectFlag");
//			processSucceed(aBeanValue, this.getClass().getName() + ".affirmBeforeAct.success" + selectFlag,TtimsCode.AFFIRM_BEFORM_ACT_SUCCESS);
    		mapinfo.put("returnflag","true");
    	} catch (Exception e) {
    		e.printStackTrace();
    		mapinfo.put("returnflag","false");
    	}
//    	// �������request �����е���Ϣ����value
//    	disposeRequest(aHttpServletRequest, aBeanValue);
//    	// ת��ָ�����
//    	return aActionMapping.findForward(aBeanValue.getForward());
    	this.ajaxReturn((Object)mapinfo,aHttpServletResponse);		
		return null;
    }
    
    /**
	 * ��ʼ�����мƻ�������Ϣ
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initFlyPlanBaseInfo(ActionMapping actionMapping, BaseForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		// ����ҵ����
		ActionDispose actionDispose = new ExecuteProcess();
		// ����Request ����
		RequestMap map = new RequestMap();
		BeanValue value = new BeanValue();
		try {
			// ʹ������ע�������ö���
			map = actionDispose.initRequest(request, servlet);
			ActFlyPlanManager actFlyPlanManager = (ActFlyPlanManager)getBean("actFlyPlanManager");
			value = actFlyPlanManager.initFlyPlanBaseInfo(map);
			
		} catch (Exception e) {
			processException(map, value, e, this.getClass().getName() + ".initFlyPlanBaseInfo",TtimsCode.FLYPLAN_INIT_ERROR);
		}	
		
		// �������request �����е���Ϣ����value
		disposeRequest(request, value);
		// ת��ָ�����
		return actionMapping.findForward(value.getForward());
		
	}
}