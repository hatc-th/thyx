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
* <b>system��</b>      Эͬ�칫ƽ̨<br/>
* <b>description��</b> �Զ���ȡ�˵�����AOP<br/>
* <b>author��</b>      ����<br/>
* <b>copyright��</b>	�� ����������ϿƼ����޹�˾<br/>
* <b>version��</b>     VER1.00 2010-04-06<br/>
*
**/
public class ProjectAfterAdvisor implements AfterReturningAdvice {
	public void afterReturning(Object returnValue, Method m, Object[] args, Object target) throws Throwable {
		
		//TODO ningliyu 2011-12-23
		if(args==null)
			return;
		//2012-11-28 zhangaimin ���ҵ��dao����Զ����ø÷������������Ͳ����ᱨ����ֻ�������ʺϵ�����
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
			// ����˵�ID
			String topMenuId = id.substring(0, 3) + "0000";
			// �Ӳ˵�ID
			String childMenuId = id.substring(0, 5) + "00";
			// ��һ��˵�û�з��ذ�ť
			if (!id.substring(3, 7).equals("0000")) {
				value.addRequestMap("backMenu", id.substring(5, 7).equals("00") ?  topMenuId : childMenuId);
			}
			// ��ǰ��ɫ����Ȩ�޼�
			List functionList = (List) map.getObject("functions");
			// ����
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
