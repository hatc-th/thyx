package com.hatc.thyx.web.flyplan;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
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
import com.hatc.common.contants.ProjectConstants;
import com.hatc.common.web.action.BaseAction;
import com.hatc.contants.TtimsCode;
import com.hatc.thyx.service.flyplan.ADManager;

/**
 * ��������action
 * author:wangdh
 * date: 2013-09-17 16:45
 * */
public class ADAction extends BaseAction {
	private String className = this.getClass().getName();
	
	/**
	 * ��ѯ���������µĹ��Ʒ����µĻ�����Ϣ
	 * */
	public ActionForward getADInfo(ActionMapping mapping, BaseForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionDispose  dispose = new ExecuteProcess();
		BeanValue beanValue = null;
		RequestMap map = null;
		
		try {
			map = dispose.initRequest(request, servlet);
			ADManager adManager = (ADManager) getBean("adManager");
			beanValue = adManager.getADInfo(map);
		} catch (RuntimeException e) {
			processException(map, beanValue, e, ProjectConstants.FRAME_WINDOW,TtimsCode.AIRPORTANDPOINT_SEARCH_ERROR);
		}
		disposeRequest(request, beanValue);
		return mapping.findForward("adInfo");
	}
	
	//���ú��߲�ѯ
	public ActionForward getRegularRR(ActionMapping actionMapping, BaseForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		ActionDispose actionDispose = new ExecuteProcess();
		RequestMap map = new RequestMap();
		BeanValue value = new BeanValue();
		try {
			map = actionDispose.initRequest(request, servlet);
			ADManager adManager = (ADManager) getBean("adManager");
			value = adManager.getGeneralSkyWays(map);
		} catch (Exception e) {
				processException(map, value, e, ProjectConstants.FRAME_WINDOW,TtimsCode.GENERALSKYWAY_SEARCH_ERROR);
	    }
		
		disposeRequest(request, value);
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
		ActionDispose actionDispose = new ExecuteProcess();
		RequestMap map = new RequestMap();
		Map mapinfo = new HashMap();
		BeanValue value = new BeanValue();
		try {
			map = actionDispose.initRequest(request, servlet);
			ADManager adManager = (ADManager) getBean("adManager");
			value=adManager.initSkyWayByGaneral(map);
			mapinfo.put("returnflag","true");
			mapinfo.put("skyWay",value.getRequestMap("skyWay"));
			
		} catch (Exception e) {
			mapinfo.put("returnflag","false");
		}	
		this.ajaxReturn((Object)mapinfo,response);		
		return null;
    }
	
	//��ѯ����̨���ߵر��
	public ActionForward getNVOrFFXList(ActionMapping actionMapping, BaseForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionDispose actionDispose = new ExecuteProcess();
		RequestMap map = new RequestMap();
		BeanValue value = new BeanValue();
		try {
			map = actionDispose.initRequest(request, servlet);
			map.addParameter("method",  request.getMethod());
			ADManager adManager = (ADManager) getBean("adManager");
			value=adManager.getNVOrFFXList(map);
		} catch (Exception e) {
			processException(map, value, e, ProjectConstants.FRAME_WINDOW,TtimsCode.AIRPORTANDPOINT_SEARCH_ERROR);
		}	
		disposeRequest(request, value);
		return actionMapping.findForward(value.getForward());
    }
	
	
	public ActionForward autoCompleteTest(ActionMapping actionMapping, BaseForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String q = request.getParameter("q");
		PrintWriter writer = response.getWriter();
		for(int i = 0;i < 2000;i++){
			writer.println("key" + i + "|" + "value" + i);
		}
		
		return null;
    }
	
	//���ݻ������ֲ�ѯ������Ϣ����ǰ̨autocompleteʹ��
	public ActionForward getADJSONData(ActionMapping actionMapping, BaseForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionDispose actionDispose = new ExecuteProcess();
		RequestMap reqMap = null;
		BeanValue value = null;
		
		try {
			reqMap = actionDispose.initRequest(request, servlet);
			ADManager adManager = (ADManager) getBean("adManager");
			value = adManager.getADListByName(reqMap);
			
			List<String[]> adList = (List<String[]>)value.getParamMap("adList");
			if(adList != null && adList.size() > 0){
				PrintWriter writer = response.getWriter();
				for(String[] strs:adList){
					writer.println(strs[0] + "|" + strs[3] + "|" + strs[1] + "|" + strs[2] + "|" + strs[4]);
				}
			}
		} catch (Exception e) {
			processException(reqMap, value, e, ProjectConstants.FRAME_WINDOW,TtimsCode.AIRPORTANDPOINT_SEARCH_ERROR);
		}
		return null;
    }
	
	//���ݵ���̨���ֲ�ѯ������Ϣ����ǰ̨autocompleteʹ��
	public ActionForward getNVJSONData(ActionMapping actionMapping, BaseForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionDispose actionDispose = new ExecuteProcess();
		RequestMap map = null;
		BeanValue value = null;
		
		try {
			map  = actionDispose.initRequest(request, servlet);
			ADManager adManager = (ADManager) getBean("adManager");
			value = adManager.getNVListByName(map);
			List<String[]> nvList = (List<String[]>)value.getParamMap("nvList");

			if(nvList != null && nvList.size() > 0){
				PrintWriter writer = response.getWriter();
				for(String[] strs:nvList){
					writer.println(strs[0] + "|" + strs[1]);
				}
			}
		} catch (Exception e) {
			processException(map, value, e, ProjectConstants.FRAME_WINDOW,TtimsCode.AIRPORTANDPOINT_SEARCH_ERROR);
			e.printStackTrace();
		}
		return null;
    }
	
	//��ѯ����(����̨���ر��)�ĸ��������ͼ�����г���ͼ������ͼ������ͼ��
	public ActionForward getADAttachment(ActionMapping actionMapping, BaseForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{
		ActionDispose actionDispose = new ExecuteProcess();
		RequestMap map = null;
		BeanValue value = null;
		
		try {
			map = actionDispose.initRequest(request, servlet);
			ADManager adManager = (ADManager) getBean("adManager");
			value = adManager.getADAttachment(map);
		} catch (RuntimeException e) {
			processException(map, value, e, ProjectConstants.FRAME_WINDOW,TtimsCode.AIRPORTANDPOINT_SEARCH_ERROR);
			e.printStackTrace();
		}
		
		disposeRequest(request, value);
		return actionMapping.findForward(value.getForward());
	}
	
}
