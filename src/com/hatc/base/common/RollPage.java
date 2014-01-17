package com.hatc.base.common;

import java.util.HashMap;
import java.util.Map;

import com.hatc.base.utils.BeanUtil;

public class RollPage {

	private Map<String, String> condition = new HashMap<String, String>();

	private int rowCount = 0; // 记录总数

	private int pageNum = 0;// 页总数

	private int pageCur = 0;// 当前页数

	private int pagePer = 10;// 页显示记录数

	private int currentlyPagePer = 0;// 当前页记录数

	private int pageFirst = 0;

	private String pageBool = "0";// 翻页标志

	public void setPageCur(int pageCur) {
		this.pageCur = pageCur;
	}

	public RollPage() {

	}

	/**
	 * 转入页数
	 */
	public RollPage(int pageCur, String pageBool) {
		this.pageCur = pageCur;
		this.pageBool = pageBool != null ? pageBool : "0";
	}

	/**
	 * 输入总页数后进行初始化
	 */
	public void init() {
		pageCount();
		firstResult();
	}

	/**
	 * 计算出总页数
	 */
	private void pageCount() {
		if (this.rowCount % this.pagePer == 0) {
			this.pageNum = rowCount / this.pagePer;
		} else {
			this.pageNum = (rowCount / this.pagePer) + 1;
		}
	}

	/**
	 * 记录起始位
	 */
	private void firstResult() {
		this.pageFirst = getPageCur() * this.pagePer;
		if (this.pageFirst < 0) {
			this.pageFirst = 0;
		}
	}

	/**
	 * 返回当前页数
	 */
	public int getPageCur() {
		if (pageBool.equals("1")) {
			return pageCur;
		} else {
			return 0;
		}
	}

	/**
	 * 返回页总数
	 */
	public int getPageNum() {
		return pageNum;
	}

	/**
	 * 设置一页显示记录数
	 */
	public void setPagePer(int pagePer) {
		this.pagePer = pagePer;
	}

	/**
	 * 返回一页显示记录数
	 */
	public int getPagePer() {
		return pagePer;
	}

	/**
	 * 设置记录总数
	 */
	public void setRowCount(int rowCount) {
		this.rowCount = rowCount;
	}

	/**
	 * 返回记录总数
	 */
	public int getRowCount() {
		return rowCount;
	}

	/**
	 * 记录开始位置
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
	 *            要设置的 pageBool
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
	 *            要设置的 currentlyPagePer
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
	 *            要设置的 condition
	 */
	public void setCondition(Map<String, String> condition) {
		this.condition = condition;
	}

}
