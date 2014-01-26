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
	  * 处理内容：查询飞行计划列表
	  * 
	  * @param 
	  * @return 
	  * 
	  */
	public ActionForward searchFlyPlanList(ActionMapping actionMapping, BaseForm baseForm, HttpServletRequest request,HttpServletResponse
			response){		
		// 定义业务流
		ActionDispose actionDispose = new ExecuteProcess();
		// 解析Request 请求
		RequestMap map = new RequestMap();
		BeanValue value = new BeanValue();
		try {
			map = actionDispose.initRequest(request, servlet);
			FlyPlanManager flyPlanManager = (FlyPlanManager) getBean("flyPlanManager");
			value = flyPlanManager.searchFlyPlanList(map);
		} catch (Exception e) {
			if ("query".equals(request.getParameter("searchType"))) {
				// 查询列表
				processException(map, value, e, className + ".searchFlyPlanList",TtimsCode.FLYPLAN_SEARCH_ERROR);
			}else if ("activateQ".equals(request.getParameter("searchType"))) {
				// 激活列表
				processException(map, value, e, className + ".activateFlyPlanList",TtimsCode.FLYPLAN_SEARCH_ERROR);
			}else{// 管理列表
				processException(map, value, e, className + ".queryFlyPlanList",TtimsCode.FLYPLAN_SEARCH_ERROR);
			}
			
	    }
		// 处理放入request 请求中的信息集合value
		disposeRequest(request, value);
		// 转至指定层次
		return actionMapping.findForward(value.getForward());

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
			FlyPlanManager flyPlanManager = (FlyPlanManager) getBean("flyPlanManager");
			value = flyPlanManager.initFlyPlanBaseInfo(map);
			
			// 成功提示页
//			processSucceed(value, this.getClass().getName() + ".save.success",TtimsCode.TASKSHEEET_ADD_SUCCESS);
		} catch (Exception e) {
			processException(map, value, e, className + ".initFlyPlanBaseInfo", TtimsCode.FLYPLAN_INIT_ERROR);
		}	
		
		// 处理放入request 请求中的信息集合value
		disposeRequest(request, value);
//		disposeRequest(request, value,actionMapping);
		// 转至指定层次
		return actionMapping.findForward(value.getForward());
		
	}
		
	/**
	 * 修改飞行计划状态： 激活计划，或撤销计划。
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward updateFlyPlanState(ActionMapping actionMapping, BaseForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 定义业务流
		ActionDispose actionDispose = new ExecuteProcess();
		// 解析Request 请求
		RequestMap map = new RequestMap();
		BeanValue value = new BeanValue();
		String operFlag= request.getParameter("operFlag");
		try {
			// 使用依赖注入来配置对象
			map = actionDispose.initRequest(request, servlet);
			
			FlyPlanManager flyPlanManager = (FlyPlanManager) getBean("flyPlanManager");
			value = flyPlanManager.updateFlyPlanState(map);
//			flyPlanManager.updateFlyPlanBySql(value); 
			// 成功提示页
			if ("cancel".equals(operFlag)) {
				// 管理列表
				processSucceed(value, className+ ".searchFlyPlanList",TtimsCode.FLYPLAN_CANCEL_SUCCESS);
			}else{// 激活列表
				processSucceed(value, className+ ".activateFlyPlanList",TtimsCode.FLYPLAN_ACTIVATE_SUCCESS);
			}
			
		} catch (Exception e) {
			if ("cancel".equals(operFlag)) {
				// 管理列表
				processException(map, value, e, className + ".searchFlyPlanList",TtimsCode.FLYPLAN_CANCEL_ERROR);
			}else{
				// 激活列表
				processException(map, value, e, className + ".activateFlyPlanList",TtimsCode.FLYPLAN_ACTIVATE_ERROR);
			}
		}	
		
		// 处理放入request 请求中的信息集合value
		disposeRequest(request, value);
		// 转至指定层次
		return actionMapping.findForward(value.getForward());
		
	}
	
	/**
	 * 保存飞行计划起飞、目的机场
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward saveFlyPlanAirPortInfo(ActionMapping actionMapping, BaseForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
         // 定义业务流
        ActionDispose actionDispose = new ExecuteProcess();
        // 解析Request 请求
        RequestMap requestMap = new RequestMap();
        BeanValue value = new BeanValue();
        try {
            // 使用依赖注入来配置对象
        	requestMap = actionDispose.initRequest(request, servlet);
        	FlyPlanManager flyPlanManager = (FlyPlanManager) getBean("flyPlanManager");
			value = flyPlanManager.saveFlyPlanAirPortInfo(requestMap);
            //设置响应数据头文件格式
			response.setContentType("text/xml; charset=UTF-8");
			response.setHeader("Cache-Control", "no-cache");
            // 创建流输出对象
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
        // 处理放入request 请求中的信息集合value
        disposeRequest(request, value);
        // 转至指定层次
        return null;
    }
	
	
	/**
	 * 保存飞行计划基本信息
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward saveFlyPlanInfo(ActionMapping actionMapping, BaseForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 定义业务流
		ActionDispose actionDispose = new ExecuteProcess();
		// 解析Request 请求
		RequestMap map = new RequestMap();
		BeanValue value = new BeanValue();
		Map mapinfo = new HashMap();
		
			try {
				// 使用依赖注入来配置对象
				map = actionDispose.initRequest(request, servlet);
				FlyPlanManager flyPlanManager = (FlyPlanManager) getBean("flyPlanManager");
				
				value=flyPlanManager.saveFlyPlanInfo(map);
				flyPlanManager.updateFlyPlanBySql(value , "1");  	// 写 6.44 ,保存数据至上级数据库
				flyPlanManager.updateFlyPlanBySql(value , "3");   	// 写 90.161 ,保存仿真数据
				
				mapinfo.put("returnflag","true");
				// 返回飞行计划的主键 、 计划编号 、 计划名称
				mapinfo.put("planid",(String) value.getRequestMap("planid"));
				mapinfo.put("FPlanCode",value.getRequestMap("FPlanCode"));
				mapinfo.put("FPlanName",value.getRequestMap("FPlanName"));
				mapinfo.put("tRsMainId",value.getRequestMap("tRsMainId"));
				mapinfo.put("tRsBakId",value.getRequestMap("tRsBakId"));
				
				
				// 返回飞行计划的状态
				mapinfo.put("planstate",request.getParameter("planstate").trim());  // 12 提交，11 保存
				
			} catch (Exception e) {
				e.printStackTrace();
				mapinfo.put("returnflag","false");
			}	
			this.ajaxReturn((Object)mapinfo,response);		
			return null;

			
//			try {
//				// 使用依赖注入来配置对象
//				map = actionDispose.initRequest(request, servlet);
//				FlyPlanManager flyPlanManager = (FlyPlanManager) getBean("flyPlanManager");
//				value = flyPlanManager.saveFlyPlanInfo(map);
//				flyPlanManager.updateFlyPlanBySql(value); 
//				// 成功提示页
//				processSucceed(value, className + ".searchFlyPlanList",TtimsCode.FLYPLAN_SAVE_SUCCESS);
//			} catch (Exception e) {
//				processException(map, value, e, className + ".saveFlyPlanSkyway.error", TtimsCode.FLYPLAN_SAVE_ERROR);
//			}	
//			// 处理放入request 请求中的信息集合value
//			disposeRequest(request, value);
//			// 转至指定层次
//			return actionMapping.findForward(value.getForward());
		
		
	}
	
	/**
	 * 保存常用航线
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward saveGeneralSkyWay(ActionMapping actionMapping, BaseForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 定义业务流
		ActionDispose actionDispose = new ExecuteProcess();
		// 解析Request 请求
		RequestMap map = new RequestMap();
		Map mapinfo = new HashMap();
		BeanValue value = new BeanValue();
		try {
			// 使用依赖注入来配置对象
			map = actionDispose.initRequest(request, servlet);
			FlyPlanManager flyPlanManager = (FlyPlanManager) getBean("flyPlanManager");
			value=flyPlanManager.saveGeneralSkyWay(map);
			mapinfo.put("returnflag","true");
			// 操作对象的主键Key
			mapinfo.put("skyFlag",(String) value.getRequestMap("skyFlag"));
			mapinfo.put("rrId",(String) value.getRequestMap("rrId"));
			
			// 成功提示页
		} catch (Exception e) {
			mapinfo.put("returnflag","false");
		}	
		this.ajaxReturn((Object)mapinfo,response);		
		return null;
    }
	
	
	/**
	  * 处理内容：查询用户的常用航线
	  * 
	  * @param 
	  * @return 
	  * 
	  */
	public ActionForward searchGeneralSkyWayList(ActionMapping actionMapping, BaseForm baseForm, HttpServletRequest request,HttpServletResponse
			response){		
		// 定义业务流
		ActionDispose actionDispose = new ExecuteProcess();
		// 解析Request 请求
		RequestMap map = new RequestMap();
		BeanValue value = new BeanValue();
		try {
			map = actionDispose.initRequest(request, servlet);
			FlyPlanManager flyPlanManager = (FlyPlanManager) getBean("flyPlanManager");
			value=flyPlanManager.getGeneralSkyWays(map);
		} catch (Exception e) {
				processException(map, value, e, ProjectConstants.FRAME_WINDOW,TtimsCode.GENERALSKYWAY_SEARCH_ERROR);
	    }
		
		// 处理放入request 请求中的信息集合value
		disposeRequest(request, value);
		// 转至指定层次
		return actionMapping.findForward(value.getForward());

	}
	
	
	/**
	 * 将选择的常用航线设置为计划的主备航线
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward setSkyWayByGaneral(ActionMapping actionMapping, BaseForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 定义业务流
		ActionDispose actionDispose = new ExecuteProcess();
		// 解析Request 请求
		RequestMap map = new RequestMap();
		Map mapinfo = new HashMap();
		BeanValue value = new BeanValue();
		try {
			// 使用依赖注入来配置对象
			map = actionDispose.initRequest(request, servlet);
			FlyPlanManager flyPlanManager = (FlyPlanManager) getBean("flyPlanManager");
			value=flyPlanManager.initSkyWayByGaneral(map);
			mapinfo.put("returnflag","true");
			// 操作对象的主键Key
			mapinfo.put("skyWay",value.getRequestMap("skyWay"));
			
			// 成功提示页
		} catch (Exception e) {
			mapinfo.put("returnflag","false");
		}	
		this.ajaxReturn((Object)mapinfo,response);		
		return null;
    }
	
	
	
	/**
	 * 删除试验实施方案(二队)
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward deleteFlyPlan(ActionMapping actionMapping, BaseForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 定义业务流
		ActionDispose actionDispose = new ExecuteProcess();
		// 解析Request 请求
		RequestMap requestMap = new RequestMap();
		BeanValue value = new BeanValue();
		try {
			// 使用依赖注入来配置对象
			requestMap = actionDispose.initRequest(request, servlet);
			FlyPlanManager flyPlanManager = (FlyPlanManager) getBean("flyPlanManager");
			value = flyPlanManager.deleteFlyPlan(requestMap);
			// 成功提示页
			processSucceed(value, className+ ".searchFlyPlanList",TtimsCode.FLYPLAN_DELETE_SUCCESS);
		} catch (Exception e) {
			processException(requestMap, value, e, className + ".searchFlyPlanList", TtimsCode.FLYPLAN_DELETE_ERROR);
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
	public ActionForward viewFlyPlanInfo(ActionMapping actionMapping, BaseForm baseForm, HttpServletRequest request,HttpServletResponse
			response){		
		// 定义业务流
		ActionDispose actionDispose = new ExecuteProcess();
		// 解析Request 请求
		RequestMap map = new RequestMap();
		BeanValue value = new BeanValue();
		try {
			map = actionDispose.initRequest(request, servlet);
			FlyPlanManager flyPlanManager = (FlyPlanManager) getBean("flyPlanManager");
			value = flyPlanManager.initFlyPlanInfo(map);
		} catch (Exception e) {
			processException(map, value, e, className + ".viewPlanInfo.error",TtimsCode.FLYPLAN_INIT_ERROR);
			
	    }
		// 处理放入request 请求中的信息集合value
		
		disposeRequest(request, value);
		// 转至指定层次
		return actionMapping.findForward(value.getForward());

	} 
	
	
	/**
	 * 获取机场最新METAR气象信息
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getAdMetarInfo(ActionMapping actionMapping, BaseForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 定义业务流
		ActionDispose actionDispose = new ExecuteProcess();
		// 解析Request 请求
		RequestMap map = new RequestMap();
		Map mapinfo = new HashMap();
		BeanValue value = new BeanValue();
		try {
			// 使用依赖注入来配置对象
			map = actionDispose.initRequest(request, servlet);
			FlyPlanManager flyPlanManager = (FlyPlanManager) getBean("flyPlanManager");
			value=flyPlanManager.getAdMetarInfo(map);
			mapinfo.put("returnflag","true");
			// 操作对象的主键Key
			mapinfo.put("wmDesc",value.getRequestMap("wmDesc"));
			mapinfo.put("wmDecode",value.getRequestMap("wmDecode"));
			
			// 成功提示页
		} catch (Exception e) {
			mapinfo.put("returnflag","false");
		}	
		this.ajaxReturn((Object)mapinfo,response);		
		return null;
    }
	
	/**
	 * 获取从机场选中的机场对应的数据库中的ID
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getAdIdInfo(ActionMapping actionMapping, BaseForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 定义业务流
		ActionDispose actionDispose = new ExecuteProcess();
		// 解析Request 请求
		RequestMap map = new RequestMap();
		Map mapinfo = new HashMap();
		BeanValue value = new BeanValue();
		try {
			// 使用依赖注入来配置对象
			map = actionDispose.initRequest(request, servlet);
			FlyPlanManager flyPlanManager = (FlyPlanManager) getBean("flyPlanManager");
			value=flyPlanManager.getAdIdInfo(map);
			mapinfo.put("returnflag","true");
			// 操作对象的主键Key
			mapinfo.put("adId",value.getRequestMap("adId"));
			
			// 成功提示页
		} catch (Exception e) {
			mapinfo.put("returnflag","false");
		}	
		this.ajaxReturn((Object)mapinfo,response);		
		return null;
    }
	
}
