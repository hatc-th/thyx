package com.hatc.common.service.aop;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.springframework.aop.AfterReturningAdvice;

import com.hatc.base.common.BeanValue;
import com.hatc.base.common.RequestMap;
import com.hatc.common.service.model.Menus;

/**
* 
* <b>system：</b>      协同办公平台<br/>
* <b>description：</b> 自动读取菜单标题AOP<br/>
* <b>author：</b>      王洋<br/>
* <b>copyright：</b>	　 北京华安天诚科技有限公司<br/>
* <b>version：</b>     VER1.00 2010-04-06<br/>
*
**/
public class ProjectAfterAdvisor implements AfterReturningAdvice {
	public void afterReturning(Object returnValue, Method m, Object[] args, Object target) throws Throwable {
		
		//TODO ningliyu 2011-12-23
		if(args==null)
			return;
		//2012-11-28 zhangaimin 添加业务dao层后自动调用该方法，由于类型不符会报错，故只对输入适合的类型
		if(!(returnValue instanceof BeanValue))
			return;
		BeanValue value = (BeanValue) returnValue;
		
		RequestMap map = (RequestMap) args[0];
		String id = map.getString("menuFunctionId");
		String firstPageFunctionId = map.getString("firstPageFunctionId");
		String isDisplayButton = map.getString("isDisplayButton");
		if(id == null){
			id = map.getString("SessionMenuFunctionId");
		}
		value.addRequestMap("DefaultFirstPageId", id);
		value.addRequestMap("firstPageFunctionId", firstPageFunctionId);
		
		if (id != null && !id.equals("")) {
			// 顶层菜单ID
			String topMenuId = id.substring(0, 3) + "0000";
			// 子菜单ID
			String childMenuId = id.substring(0, 5) + "00";
			// 第一层菜单没有返回按钮
			if (!id.substring(3, 7).equals("0000")) {
				value.addRequestMap("backMenu", id.substring(5, 7).equals("00") ?  topMenuId : childMenuId);
			}
			// 当前角色功能权限集
			List functionList = (List) map.getObject("functions");
			// 标题
			List<Menus> menusList = new ArrayList<Menus>();
			if (null != functionList) {
				for (Object fun : functionList) {
					Menus menu = (Menus) fun;
					if (menu.getFunction().getOrderId().equals(topMenuId)) {
						menusList.add(menu);
						if (!id.equals(topMenuId)) {
							if (menu.getList() != null) {
								for (Menus temp_1 : menu.getList()) {
									if (temp_1.getFunction().getOrderId().equals(childMenuId)) {
										menusList.add(temp_1);
										if (temp_1.getList() != null) {
											for (Menus temp_2 : temp_1.getList()) {
												if (temp_2.getFunction().getOrderId().equals(id)) {
													menusList.add(temp_2);
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}
			value.addSessionMap("SessionMenuFunctionId", id);
			value.addRequestMap("isDisplayButton", isDisplayButton);
			value.addRequestMap("menusList", menusList);
			value.addRequestMap("menusListLength", menusList.size());
		}
	}
}
