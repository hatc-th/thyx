package com.hatc.base.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* 
* <b>system：</b>      协同办公平台<br/>
* <b>description：</b> 系统处理后值Bean<br/>
* <b>author：</b>      王洋<br/>
* <b>copyright：</b>	　 北京华安天诚科技有限公司<br/>
* <b>version：</b>     VER1.00 2010-04-06<br/>
*
**/
public class BeanValue {

	/** 放在Session中的数据 */
	private Map<String, Object> sessionMap = new HashMap<String, Object>();

	/** 放在Session中的数据 */
	private List<String> removeSessionList = new ArrayList<String>();

	/** 放在Request中的数据 */
	private Map<String, Object> requestMap = new HashMap<String, Object>();

	/** 返回参数(同样放在Request中) */
	private Map<String, Object> paramMap = new HashMap<String, Object>();

	/** 跳转目标名称 */
	private String forward;

	/**
	 * 获取跳转目标
	 * @return 跳转目标
	 */
	public String getForward() {
		return forward;
	}

	/**
	 * 设置跳转目标
	 * @param forword　跳转目标
	 */
	public void setForward(String forword) {
		this.forward = forword;
	}

	/**
	 * 设置请求参数集合
	 * @return　参数集合
	 */
	public Map<String, Object> getRequestMap() {
		return requestMap;
	}

	/**
	 * 设置请求参数集合
	 * @param requestList 参数集合
	 */
	public void setRequestMap(Map<String, Object> requestList) {
		this.requestMap = requestList;
	}

	/**
	 * 获取请求SESSION中的参数集合
	 * @return 参数集合
	 */
	public Map<String, Object> getSessionMap() {
		return sessionMap;
	}

	/**
	 * 设置请求SESSION中的参数集合
	 * @param sessionList 参数集合
	 */
	public void setSessionMap(Map<String, Object> sessionList) {
		this.sessionMap = sessionList;
	}

	/**
	 * 获取请求SESSION中的参数值
	 * @param key　键值
	 * @return　SESSION中的参数值
	 */
	public Object getSessionMap(String key) {
		return sessionMap.get(key);
	}

	/**
	 * 获取请参数值
	 * @param key　键值
	 * @return　请求参数值
	 */
	public Object getRequestMap(String key) {
		return requestMap.get(key);
	}

	/**
	 * 获取返回值集合
	 * @return 返回值集合
	 */
	public Map<String, Object> getParamMap() {
		return paramMap;
	}

	/**
	 * 获取返回参数值
	 * @param key　键值
	 * @return　返回参数值
	 */
	public Object getParamMap(String key) {
		return paramMap.get(key);
	}

	/**
	 * 添加请求SESSSION值，存入请求SESSION中
	 * @param key 键值
	 * @param value 值
	 */
	public void addSessionMap(String key, Object value) {
		sessionMap.put(key, value);
	}

	/**
	 * 添加请求返回值
	 * @param key 键值
	 * @param value 值
	 */
	public void addRequestMap(String key, Object value) {
		requestMap.put(key, value);
	}

	/**
	 * 添加返回参数
	 * @param key 键值
	 * @param value 值
	 */
	public void addParamMap(String key, Object value) {
		paramMap.put(key, value);
	}

	/**
	 * 添加待移除SESSION值的键值
	 * @param key
	 */
	public void removeSession(String key) {
		removeSessionList.add(key);
	}

	/**
	 * 获取需要移除的SESSION的值列表
	 */
	public List<String> getRemoveSessionList() {
		return removeSessionList;
	}
}
