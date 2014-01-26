package com.hatc.thyx.web.flyplan;

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
import com.hatc.common.contants.ProjectConstants;
import com.hatc.common.web.action.BaseAction;
import com.hatc.contants.TtimsCode;
import com.hatc.thyx.service.flyplan.FlyPlanManager;

public class FlyPlanAction extends BaseAction {
	
	private String className = this.getClass().getName();
	
	/**
	  * �������ݣ���ѯ���мƻ��б�
	  * 
	  * @param 
	  * @return 
	  * 
	  */
	public ActionForward searchFlyPlanList(ActionMapping actionMapping, BaseForm baseForm, HttpServletRequest request,HttpServletResponse
			response){		
		// ����ҵ����
		ActionDispose actionDispose = new ExecuteProcess();
		// ����Request ����
		RequestMap map = new RequestMap();
		BeanValue value = new BeanValue();
		try {
			map = actionDispose.initRequest(request, servlet);
			FlyPlanManager flyPlanManager = (FlyPlanManager) getBean("flyPlanManager");
			value = flyPlanManager.searchFlyPlanList(map);
		} catch (Exception e) {
			if ("query".equals(request.getParameter("searchType"))) {
				// ��ѯ�б�
				processException(map, value, e, className + ".searchFlyPlanList",TtimsCode.FLYPLAN_SEARCH_ERROR);
			}else if ("activateQ".equals(request.getParameter("searchType"))) {
				// �����б�
				processException(map, value, e, className + ".activateFlyPlanList",TtimsCode.FLYPLAN_SEARCH_ERROR);
			}else{// �����б�
				processException(map, value, e, className + ".queryFlyPlanList",TtimsCode.FLYPLAN_SEARCH_ERROR);
			}
			
	    }
		// �������request �����е���Ϣ����value
		disposeRequest(request, value);
		// ת��ָ�����
		return actionMapping.findForward(value.getForward());

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
			FlyPlanManager flyPlanManager = (FlyPlanManager) getBean("flyPlanManager");
			value = flyPlanManager.initFlyPlanBaseInfo(map);
			
			// �ɹ���ʾҳ
//			processSucceed(value, this.getClass().getName() + ".save.success",TtimsCode.TASKSHEEET_ADD_SUCCESS);
		} catch (Exception e) {
			processException(map, value, e, className + ".initFlyPlanBaseInfo", TtimsCode.FLYPLAN_INIT_ERROR);
		}	
		
		// �������request �����е���Ϣ����value
		disposeRequest(request, value);
//		disposeRequest(request, value,actionMapping);
		// ת��ָ�����
		return actionMapping.findForward(value.getForward());
		
	}
		
	/**
	 * �޸ķ��мƻ�״̬�� ����ƻ��������ƻ���
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward updateFlyPlanState(ActionMapping actionMapping, BaseForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		// ����ҵ����
		ActionDispose actionDispose = new ExecuteProcess();
		// ����Request ����
		RequestMap map = new RequestMap();
		BeanValue value = new BeanValue();
		String operFlag= request.getParameter("operFlag");
		try {
			// ʹ������ע�������ö���
			map = actionDispose.initRequest(request, servlet);
			
			FlyPlanManager flyPlanManager = (FlyPlanManager) getBean("flyPlanManager");
			value = flyPlanManager.updateFlyPlanState(map);
//			flyPlanManager.updateFlyPlanBySql(value); 
			// �ɹ���ʾҳ
			if ("cancel".equals(operFlag)) {
				// �����б�
				processSucceed(value, className+ ".searchFlyPlanList",TtimsCode.FLYPLAN_CANCEL_SUCCESS);
			}else{// �����б�
				processSucceed(value, className+ ".activateFlyPlanList",TtimsCode.FLYPLAN_ACTIVATE_SUCCESS);
			}
			
		} catch (Exception e) {
			if ("cancel".equals(operFlag)) {
				// �����б�
				processException(map, value, e, className + ".searchFlyPlanList",TtimsCode.FLYPLAN_CANCEL_ERROR);
			}else{
				// �����б�
				processException(map, value, e, className + ".activateFlyPlanList",TtimsCode.FLYPLAN_ACTIVATE_ERROR);
			}
		}	
		
		// �������request �����е���Ϣ����value
		disposeRequest(request, value);
		// ת��ָ�����
		return actionMapping.findForward(value.getForward());
		
	}
	
	/**
	 * ������мƻ���ɡ�Ŀ�Ļ���
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward saveFlyPlanAirPortInfo(ActionMapping actionMapping, BaseForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
         // ����ҵ����
        ActionDispose actionDispose = new ExecuteProcess();
        // ����Request ����
        RequestMap requestMap = new RequestMap();
        BeanValue value = new BeanValue();
        try {
            // ʹ������ע�������ö���
        	requestMap = actionDispose.initRequest(request, servlet);
        	FlyPlanManager flyPlanManager = (FlyPlanManager) getBean("flyPlanManager");
			value = flyPlanManager.saveFlyPlanAirPortInfo(requestMap);
            //������Ӧ����ͷ�ļ���ʽ
			response.setContentType("text/xml; charset=UTF-8");
			response.setHeader("Cache-Control", "no-cache");
            // �������������
            PrintWriter out = response.getWriter();
            try{
                String boolstr = (String) value.getRequestMap("boolstr");
                out.println(boolstr);
            }catch(Exception e) {
            	out.println("<response><reValue>false</reValue></response>");
                e.printStackTrace();
                log.error(e);
            }finally{
                out.flush();
                out.close();
            }
        } catch (Exception e) {
            // processException(aBeanValue, e, className, OmssCode.*);
            log.error(e);
        }
        // �������request �����е���Ϣ����value
        disposeRequest(request, value);
        // ת��ָ�����
        return null;
    }
	
	
	/**
	 * ������мƻ�������Ϣ
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward saveFlyPlanInfo(ActionMapping actionMapping, BaseForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		// ����ҵ����
		ActionDispose actionDispose = new ExecuteProcess();
		// ����Request ����
		RequestMap map = new RequestMap();
		BeanValue value = new BeanValue();
		Map mapinfo = new HashMap();
		
			try {
				// ʹ������ע�������ö���
				map = actionDispose.initRequest(request, servlet);
				FlyPlanManager flyPlanManager = (FlyPlanManager) getBean("flyPlanManager");
				
				value=flyPlanManager.saveFlyPlanInfo(map);
				flyPlanManager.updateFlyPlanBySql(value , "1");  	// д 6.44 ,�����������ϼ����ݿ�
				flyPlanManager.updateFlyPlanBySql(value , "3");   	// д 90.161 ,�����������
				
				mapinfo.put("returnflag","true");
				// ���ط��мƻ������� �� �ƻ���� �� �ƻ�����
				mapinfo.put("planid",(String) value.getRequestMap("planid"));
				mapinfo.put("FPlanCode",value.getRequestMap("FPlanCode"));
				mapinfo.put("FPlanName",value.getRequestMap("FPlanName"));
				mapinfo.put("tRsMainId",value.getRequestMap("tRsMainId"));
				mapinfo.put("tRsBakId",value.getRequestMap("tRsBakId"));
				
				
				// ���ط��мƻ���״̬
				mapinfo.put("planstate",request.getParameter("planstate").trim());  // 12 �ύ��11 ����
				
			} catch (Exception e) {
				e.printStackTrace();
				mapinfo.put("returnflag","false");
			}	
			this.ajaxReturn((Object)mapinfo,response);		
			return null;

			
//			try {
//				// ʹ������ע�������ö���
//				map = actionDispose.initRequest(request, servlet);
//				FlyPlanManager flyPlanManager = (FlyPlanManager) getBean("flyPlanManager");
//				value = flyPlanManager.saveFlyPlanInfo(map);
//				flyPlanManager.updateFlyPlanBySql(value); 
//				// �ɹ���ʾҳ
//				processSucceed(value, className + ".searchFlyPlanList",TtimsCode.FLYPLAN_SAVE_SUCCESS);
//			} catch (Exception e) {
//				processException(map, value, e, className + ".saveFlyPlanSkyway.error", TtimsCode.FLYPLAN_SAVE_ERROR);
//			}	
//			// �������request �����е���Ϣ����value
//			disposeRequest(request, value);
//			// ת��ָ�����
//			return actionMapping.findForward(value.getForward());
		
		
	}
	
	/**
	 * ���泣�ú���
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward saveGeneralSkyWay(ActionMapping actionMapping, BaseForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		// ����ҵ����
		ActionDispose actionDispose = new ExecuteProcess();
		// ����Request ����
		RequestMap map = new RequestMap();
		Map mapinfo = new HashMap();
		BeanValue value = new BeanValue();
		try {
			// ʹ������ע�������ö���
			map = actionDispose.initRequest(request, servlet);
			FlyPlanManager flyPlanManager = (FlyPlanManager) getBean("flyPlanManager");
			value=flyPlanManager.saveGeneralSkyWay(map);
			mapinfo.put("returnflag","true");
			// �������������Key
			mapinfo.put("skyFlag",(String) value.getRequestMap("skyFlag"));
			mapinfo.put("rrId",(String) value.getRequestMap("rrId"));
			
			// �ɹ���ʾҳ
		} catch (Exception e) {
			mapinfo.put("returnflag","false");
		}	
		this.ajaxReturn((Object)mapinfo,response);		
		return null;
    }
	
	
	/**
	  * �������ݣ���ѯ�û��ĳ��ú���
	  * 
	  * @param 
	  * @return 
	  * 
	  */
	public ActionForward searchGeneralSkyWayList(ActionMapping actionMapping, BaseForm baseForm, HttpServletRequest request,HttpServletResponse
			response){		
		// ����ҵ����
		ActionDispose actionDispose = new ExecuteProcess();
		// ����Request ����
		RequestMap map = new RequestMap();
		BeanValue value = new BeanValue();
		try {
			map = actionDispose.initRequest(request, servlet);
			FlyPlanManager flyPlanManager = (FlyPlanManager) getBean("flyPlanManager");
			value=flyPlanManager.getGeneralSkyWays(map);
		} catch (Exception e) {
				processException(map, value, e, ProjectConstants.FRAME_WINDOW,TtimsCode.GENERALSKYWAY_SEARCH_ERROR);
	    }
		
		// �������request �����е���Ϣ����value
		disposeRequest(request, value);
		// ת��ָ�����
		return actionMapping.findForward(value.getForward());

	}
	
	
	/**
	 * ��ѡ��ĳ��ú�������Ϊ�ƻ�����������
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward setSkyWayByGaneral(ActionMapping actionMapping, BaseForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		// ����ҵ����
		ActionDispose actionDispose = new ExecuteProcess();
		// ����Request ����
		RequestMap map = new RequestMap();
		Map mapinfo = new HashMap();
		BeanValue value = new BeanValue();
		try {
			// ʹ������ע�������ö���
			map = actionDispose.initRequest(request, servlet);
			FlyPlanManager flyPlanManager = (FlyPlanManager) getBean("flyPlanManager");
			value=flyPlanManager.initSkyWayByGaneral(map);
			mapinfo.put("returnflag","true");
			// �������������Key
			mapinfo.put("skyWay",value.getRequestMap("skyWay"));
			
			// �ɹ���ʾҳ
		} catch (Exception e) {
			mapinfo.put("returnflag","false");
		}	
		this.ajaxReturn((Object)mapinfo,response);		
		return null;
    }
	
	
	
	/**
	 * ɾ������ʵʩ����(����)
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward deleteFlyPlan(ActionMapping actionMapping, BaseForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		// ����ҵ����
		ActionDispose actionDispose = new ExecuteProcess();
		// ����Request ����
		RequestMap requestMap = new RequestMap();
		BeanValue value = new BeanValue();
		try {
			// ʹ������ע�������ö���
			requestMap = actionDispose.initRequest(request, servlet);
			FlyPlanManager flyPlanManager = (FlyPlanManager) getBean("flyPlanManager");
			value = flyPlanManager.deleteFlyPlan(requestMap);
			// �ɹ���ʾҳ
			processSucceed(value, className+ ".searchFlyPlanList",TtimsCode.FLYPLAN_DELETE_SUCCESS);
		} catch (Exception e) {
			processException(requestMap, value, e, className + ".searchFlyPlanList", TtimsCode.FLYPLAN_DELETE_ERROR);
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
	public ActionForward viewFlyPlanInfo(ActionMapping actionMapping, BaseForm baseForm, HttpServletRequest request,HttpServletResponse
			response){		
		// ����ҵ����
		ActionDispose actionDispose = new ExecuteProcess();
		// ����Request ����
		RequestMap map = new RequestMap();
		BeanValue value = new BeanValue();
		try {
			map = actionDispose.initRequest(request, servlet);
			FlyPlanManager flyPlanManager = (FlyPlanManager) getBean("flyPlanManager");
			value = flyPlanManager.initFlyPlanInfo(map);
		} catch (Exception e) {
			processException(map, value, e, className + ".viewPlanInfo.error",TtimsCode.FLYPLAN_INIT_ERROR);
			
	    }
		// �������request �����е���Ϣ����value
		
		disposeRequest(request, value);
		// ת��ָ�����
		return actionMapping.findForward(value.getForward());

	} 
	
	
	/**
	 * ��ȡ��������METAR������Ϣ
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getAdMetarInfo(ActionMapping actionMapping, BaseForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		// ����ҵ����
		ActionDispose actionDispose = new ExecuteProcess();
		// ����Request ����
		RequestMap map = new RequestMap();
		Map mapinfo = new HashMap();
		BeanValue value = new BeanValue();
		try {
			// ʹ������ע�������ö���
			map = actionDispose.initRequest(request, servlet);
			FlyPlanManager flyPlanManager = (FlyPlanManager) getBean("flyPlanManager");
			value=flyPlanManager.getAdMetarInfo(map);
			mapinfo.put("returnflag","true");
			// �������������Key
			mapinfo.put("wmDesc",value.getRequestMap("wmDesc"));
			mapinfo.put("wmDecode",value.getRequestMap("wmDecode"));
			
			// �ɹ���ʾҳ
		} catch (Exception e) {
			mapinfo.put("returnflag","false");
		}	
		this.ajaxReturn((Object)mapinfo,response);		
		return null;
    }
	
	/**
	 * ��ȡ�ӻ���ѡ�еĻ�����Ӧ�����ݿ��е�ID
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getAdIdInfo(ActionMapping actionMapping, BaseForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		// ����ҵ����
		ActionDispose actionDispose = new ExecuteProcess();
		// ����Request ����
		RequestMap map = new RequestMap();
		Map mapinfo = new HashMap();
		BeanValue value = new BeanValue();
		try {
			// ʹ������ע�������ö���
			map = actionDispose.initRequest(request, servlet);
			FlyPlanManager flyPlanManager = (FlyPlanManager) getBean("flyPlanManager");
			value=flyPlanManager.getAdIdInfo(map);
			mapinfo.put("returnflag","true");
			// �������������Key
			mapinfo.put("adId",value.getRequestMap("adId"));
			
			// �ɹ���ʾҳ
		} catch (Exception e) {
			mapinfo.put("returnflag","false");
		}	
		this.ajaxReturn((Object)mapinfo,response);		
		return null;
    }
	
}
