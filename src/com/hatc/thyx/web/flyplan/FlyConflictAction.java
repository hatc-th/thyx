package com.hatc.thyx.web.flyplan;

import java.util.List;

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
import com.hatc.hibernate.vo.FlyConflictVO;
import com.hatc.thyx.service.flyplan.FlyConflictManager;

/*
 * 飞行冲突action
 * @author wangdonghua
 * @date 2013-10-08 14:30
 * @version 1.0
 * */
public class FlyConflictAction extends BaseAction {
	
	/*
	 * 查询飞行冲突数据（机场，风速，能见度）
	 */
	public ActionForward getFlyConflictInfo(ActionMapping mapping, BaseForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionDispose  dispose = new ExecuteProcess();
		BeanValue beanValue = null;
		RequestMap map = null;
		
		try {
			map = dispose.initRequest(request, servlet);
			FlyConflictManager flyConflictManager = (FlyConflictManager)getBean("flyConflictManager");
			beanValue = flyConflictManager.getFlyConflictInfo(map);
			List<FlyConflictVO> flyConflictList = (List<FlyConflictVO>)beanValue.getParamMap("flyConflictList");
			this.ajaxReturn(flyConflictList, response);
		} catch (RuntimeException e) {
			processException(map, beanValue, e, ProjectConstants.FRAME_WINDOW,TtimsCode.AIRPORTANDPOINT_SEARCH_ERROR);
		}
		return null;
	}
}
