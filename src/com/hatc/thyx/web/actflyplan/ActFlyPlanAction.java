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
	 * 实施前确认查询列表
	 * @param aActionMapping
	 * @param aBaseForm
	 * @param aHttpServletRequest
	 * @param aHttpServletResponse
	 * @return
	 * @throws Exception
	 */
    public ActionForward queryActFlyPlanList(ActionMapping aActionMapping, BaseForm aBaseForm,
            HttpServletRequest aHttpServletRequest, HttpServletResponse aHttpServletResponse) throws Exception {
        // 定义业务流
        ActionDispose aActionDispose = new ExecuteProcess();
        // 解析Request 请求
        RequestMap aRequestMap = new RequestMap();
        BeanValue aBeanValue = new BeanValue();
        try {
            // 使用依赖注入来配置对象
            aRequestMap = aActionDispose.initRequest(aHttpServletRequest, servlet);
            ActFlyPlanManager actFlyPlanManager = (ActFlyPlanManager)getBean("actFlyPlanManager");
            aBeanValue = actFlyPlanManager.queryAffirmBeforeActPlanList(aRequestMap);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 处理放入request 请求中的信息集合value
        disposeRequest(aHttpServletRequest, aBeanValue);
        // 转至指定层次
    	return aActionMapping.findForward(aBeanValue.getForward());
    }
    /**
     * 实施确认操作
     * @param aActionMapping
     * @param aBaseForm
     * @param aHttpServletRequest
     * @param aHttpServletResponse
     * @return
     * @throws Exception
     */
    public ActionForward affirmBeforeAct(ActionMapping aActionMapping, BaseForm aBaseForm,
    		HttpServletRequest aHttpServletRequest, HttpServletResponse aHttpServletResponse) throws Exception {
    	// 定义业务流
    	ActionDispose aActionDispose = new ExecuteProcess();
    	// 解析Request 请求
    	RequestMap aRequestMap = new RequestMap();
    	BeanValue aBeanValue = new BeanValue();
    	Map mapinfo = new HashMap();
    	try {
    		// 使用依赖注入来配置对象
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
//    	// 处理放入request 请求中的信息集合value
//    	disposeRequest(aHttpServletRequest, aBeanValue);
//    	// 转至指定层次
//    	return aActionMapping.findForward(aBeanValue.getForward());
    	this.ajaxReturn((Object)mapinfo,aHttpServletResponse);		
		return null;
    }
    
    /**
	 * 初始化飞行计划基本信息
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initFlyPlanBaseInfo(ActionMapping actionMapping, BaseForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 定义业务流
		ActionDispose actionDispose = new ExecuteProcess();
		// 解析Request 请求
		RequestMap map = new RequestMap();
		BeanValue value = new BeanValue();
		try {
			// 使用依赖注入来配置对象
			map = actionDispose.initRequest(request, servlet);
			ActFlyPlanManager actFlyPlanManager = (ActFlyPlanManager)getBean("actFlyPlanManager");
			value = actFlyPlanManager.initFlyPlanBaseInfo(map);
			
		} catch (Exception e) {
			processException(map, value, e, this.getClass().getName() + ".initFlyPlanBaseInfo",TtimsCode.FLYPLAN_INIT_ERROR);
		}	
		
		// 处理放入request 请求中的信息集合value
		disposeRequest(request, value);
		// 转至指定层次
		return actionMapping.findForward(value.getForward());
		
	}
}