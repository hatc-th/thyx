package com.hatc.base.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* 
* <b>system��</b>      Эͬ�칫ƽ̨<br/>
* <b>description��</b> ϵͳ�����ֵBean<br/>
* <b>author��</b>      ����<br/>
* <b>copyright��</b>	�� ����������ϿƼ����޹�˾<br/>
* <b>version��</b>     VER1.00 2010-04-06<br/>
*
**/
public class BeanValue {

	/** ����Session�е����� */
	private Map<String, Object> sessionMap = new HashMap<String, Object>();

	/** ����Session�е����� */
	private List<String> removeSessionList = new ArrayList<String>();

	/** ����Request�е����� */
	private Map<String, Object> requestMap = new HashMap<String, Object>();

	/** ���ز���(ͬ������Request��) */
	private Map<String, Object> paramMap = new HashMap<String, Object>();

	/** ��תĿ������ */
	private String forward;

	/**
	 * ��ȡ��תĿ��
	 * @return ��תĿ��
	 */
	public String getForward() {
		return forward;
	}

	/**
	 * ������תĿ��
	 * @param forword����תĿ��
	 */
	public void setForward(String forword) {
		this.forward = forword;
	}

	/**
	 * ���������������
	 * @return����������
	 */
	public Map<String, Object> getRequestMap() {
		return requestMap;
	}

	/**
	 * ���������������
	 * @param requestList ��������
	 */
	public void setRequestMap(Map<String, Object> requestList) {
		this.requestMap = requestList;
	}

	/**
	 * ��ȡ����SESSION�еĲ�������
	 * @return ��������
	 */
	public Map<String, Object> getSessionMap() {
		return sessionMap;
	}

	/**
	 * ��������SESSION�еĲ�������
	 * @param sessionList ��������
	 */
	public void setSessionMap(Map<String, Object> sessionList) {
		this.sessionMap = sessionList;
	}

	/**
	 * ��ȡ����SESSION�еĲ���ֵ
	 * @param key����ֵ
	 * @return��SESSION�еĲ���ֵ
	 */
	public Object getSessionMap(String key) {
		return sessionMap.get(key);
	}

	/**
	 * ��ȡ�����ֵ
	 * @param key����ֵ
	 * @return���������ֵ
	 */
	public Object getRequestMap(String key) {
		return requestMap.get(key);
	}

	/**
	 * ��ȡ����ֵ����
	 * @return ����ֵ����
	 */
	public Map<String, Object> getParamMap() {
		return paramMap;
	}

	/**
	 * ��ȡ���ز���ֵ
	 * @param key����ֵ
	 * @return�����ز���ֵ
	 */
	public Object getParamMap(String key) {
		return paramMap.get(key);
	}

	/**
	 * �������SESSSIONֵ����������SESSION��
	 * @param key ��ֵ
	 * @param value ֵ
	 */
	public void addSessionMap(String key, Object value) {
		sessionMap.put(key, value);
	}

	/**
	 * ������󷵻�ֵ
	 * @param key ��ֵ
	 * @param value ֵ
	 */
	public void addRequestMap(String key, Object value) {
		requestMap.put(key, value);
	}

	/**
	 * ��ӷ��ز���
	 * @param key ��ֵ
	 * @param value ֵ
	 */
	public void addParamMap(String key, Object value) {
		paramMap.put(key, value);
	}

	/**
	 * ��Ӵ��Ƴ�SESSIONֵ�ļ�ֵ
	 * @param key
	 */
	public void removeSession(String key) {
		removeSessionList.add(key);
	}

	/**
	 * ��ȡ��Ҫ�Ƴ���SESSION��ֵ�б�
	 */
	public List<String> getRemoveSessionList() {
		return removeSessionList;
	}
}
