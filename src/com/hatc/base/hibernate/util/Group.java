package com.hatc.base.hibernate.util;

import java.util.ArrayList;
import java.util.List;

/**
* 
* <b>system：</b>      协同办公平台<br/>
* <b>description：</b> 排序集合类<br>
* <b>author：</b>      王洋<br/>
* <b>copyright：</b>	　 北京华安天诚科技有限公司<br/>
* <b>version：</b>     VER1.00 2010-04-06<br/>
*
**/
public class Group {

	/**
	 * 分组查询条件 count
	 */
	public static final int SQL_GROUP_COUNT = 1;

	/**
	 * 分组查询条件 avg
	 */
	public static final int SQL_GROUP_AVG = 2;

	/**
	 * 分组查询条件 max
	 */
	public static final int SQL_GROUP_MAX = 3;

	/**
	 * 分组查询条件 min
	 */
	public static final int SQL_GROUP_MIN = 4;

	/**
	 * 分组查询条件 row Count
	 */
	public static final int SQL_GROUP_ROWCOUNT = 5;

	/**
	 * 分组查询条件 count Distinct
	 */
	public static final int SQL_GROUP_COUNT_DISTINCT = 6;

	/**
	 * 分组查询条件 sum
	 */
	public static final int SQL_GROUP_SUM = 7;

	// 列名称
	private List<Column> column = new ArrayList<Column>();

	// 分组列名称
	private List<String> groupColumn = new ArrayList<String>();

	// 条件
	/**
	 * 分组查询条件集合 And
	 */
	private List<Parameter> parameterAnd = new ArrayList<Parameter>();

	/**
	 * 分组查询条件集合 Or
	 */
	private List<Parameter> parameterOr = new ArrayList<Parameter>();

	public Group() {
	}

	/**
	 * 获取排序条件集合
	 * @return 排序集合
	 */
	public List<String> getGroupColumn() {
		return groupColumn;
	}

	/**
	 * 设置排序条件集合
	 * @param groupColumn 排序集合
	 */
	public void setGroupColumn(List<String> groupColumn) {
		this.groupColumn = groupColumn;
	}

	/**
	 * 获取排序列集合
	 * @return　排序列集合
	 */
	public List<Column> getColumn() {
		return column;
	}

	/**
	 * 设置排序列集合
	 * @param column 排序列集合
	 */
	public void setColumn(List<Column> column) {
		this.column = column;
	}

	/**
	 * 获取与条件查询集合
	 * @return 与条件查询集合
	 */
	public List<Parameter> getParameterAnd() {
		return parameterAnd;
	}

	/**
	 * 设置与条件查询集合
	 * @param parameterAnd　与条件查询集合
	 */
	public void setParameterAnd(List<Parameter> parameterAnd) {
		this.parameterAnd = parameterAnd;
	}

	/**
	 * 获取或条件查询集合
	 * @return　或条件查询集合
	 */
	public List<Parameter> getParameterOr() {
		return parameterOr;
	}

	/**
	 * 设置或条件查询集合
	 * @param parameterOr　或条件查询集合
	 */
	public void setParameterOr(List<Parameter> parameterOr) {
		this.parameterOr = parameterOr;
	}

	/**
	 * 添加列
	 * @param column 列名
	 * @param operator　操作符
	 */
	public void addColumn(String column, int operator) {
		this.column.add(new Column(column, operator));
	}

	/**
	 * 添加列
	 * @param column　列名
	 * @param operator　操作符
	 * @param alias　别名
	 */
	public void addColumn(String column, int operator, String alias) {
		this.column.add(new Column(column, operator, alias));
	}

	/**
	 * 添加排序列
	 * @param column　列名
	 */
	public void addGroupColumn(String column) {
		this.groupColumn.add(column);
	}

	/**
	 * 添加查询条件
	 * @param column　列名
	 * @param operator　操作
	 * @param value　值集合
	 */
	public void addParameterAnd(String column, int operator, Object[] value) {
		this.parameterAnd.add(new Parameter(column, operator, value));
	}

	/**
	 * 添加查询条件
	 * @param column　列名
	 * @param operator　操作符
	 * @param value　值集合
	 */
	public void addParameterOr(String column, int operator, Object[] value) {
		this.parameterOr.add(new Parameter(column, operator, value));
	}
}
