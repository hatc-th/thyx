package com.hatc.base.common;

import java.util.HashMap;
import java.util.Map;

import com.hatc.base.utils.BeanUtil;

public class RollPage {

	private Map<String, String> condition = new HashMap<String, String>();

	private int rowCount = 0; // ��¼����

	private int pageNum = 0;// ҳ����

	private int pageCur = 0;// ��ǰҳ��

	private int pagePer = 10;// ҳ��ʾ��¼��

	private int currentlyPagePer = 0;// ��ǰҳ��¼��

	private int pageFirst = 0;

	private String pageBool = "0";// ��ҳ��־

	public void setPageCur(int pageCur) {
		this.pageCur = pageCur;
	}

	public RollPage() {

	}

	/**
	 * ת��ҳ��
	 */
	public RollPage(int pageCur, String pageBool) {
		this.pageCur = pageCur;
		this.pageBool = pageBool != null ? pageBool : "0";
	}

	/**
	 * ������ҳ������г�ʼ��
	 */
	public void init() {
		pageCount();
		firstResult();
	}

	/**
	 * �������ҳ��
	 */
	private void pageCount() {
		if (this.rowCount % this.pagePer == 0) {
			this.pageNum = rowCount / this.pagePer;
		} else {
			this.pageNum = (rowCount / this.pagePer) + 1;
		}
	}

	/**
	 * ��¼��ʼλ
	 */
	private void firstResult() {
		this.pageFirst = getPageCur() * this.pagePer;
		if (this.pageFirst < 0) {
			this.pageFirst = 0;
		}
	}

	/**
	 * ���ص�ǰҳ��
	 */
	public int getPageCur() {
		if (pageBool.equals("1")) {
			return pageCur;
		} else {
			return 0;
		}
	}

	/**
	 * ����ҳ����
	 */
	public int getPageNum() {
		return pageNum;
	}

	/**
	 * ����һҳ��ʾ��¼��
	 */
	public void setPagePer(int pagePer) {
		this.pagePer = pagePer;
	}

	/**
	 * ����һҳ��ʾ��¼��
	 */
	public int getPagePer() {
		return pagePer;
	}

	/**
	 * ���ü�¼����
	 */
	public void setRowCount(int rowCount) {
		this.rowCount = rowCount;
	}

	/**
	 * ���ؼ�¼����
	 */
	public int getRowCount() {
		return rowCount;
	}

	/**
	 * ��¼��ʼλ��
	 */
	public int getPageFirst() {
		return pageFirst;
	}

	/**
	 * @return pageBool
	 */
	public String getPageBool() {
		return pageBool;
	}

	/**
	 * @param pageBool
	 *            Ҫ���õ� pageBool
	 */
	public void setPageBool(String pageBool) {
		this.pageBool = pageBool;
	}

	/**
	 * @return currentlyPagePer
	 */
	public int getCurrentlyPagePer() {
		return currentlyPagePer;
	}

	/**
	 * @param currentlyPagePer
	 *            Ҫ���õ� currentlyPagePer
	 */
	public void setCurrentlyPagePer(int currentlyPagePer) {
		this.currentlyPagePer = currentlyPagePer;
	}

	public void setCondition(String key, String value) {
		if (value != null && !value.equals("")) {
			this.condition.put(key, value);
		}
	}

	public void setCondition(Object object, boolean bool) {
		this.condition.putAll(BeanUtil.convertParmMap(object, bool));
	}

	public String getCondition(String key, String value) {
		return this.condition.get(key);
	}

	/**
	 * @return condition
	 */
	public Map<String, String> getCondition() {
		return condition;
	}

	/**
	 * @param condition
	 *            Ҫ���õ� condition
	 */
	public void setCondition(Map<String, String> condition) {
		this.condition = condition;
	}

}
