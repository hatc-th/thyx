package com.hatc.base.hibernate.util;

import java.util.ArrayList;
import java.util.List;

/**
* 
* <b>system：</b>      协同办公平台<br/>
* <b>description：</b> 查询排序集合对象<br>
* <b>author：</b>      王洋<br/>
* <b>copyright：</b>	　 北京华安天诚科技有限公司<br/>
* <b>version：</b>     VER1.00 2010-04-06<br/>
*
**/
public class Order {

	/**
	 * 正序排列 asc
	 */
	public static final int SQL_ORDER_ASC = 1;

	/**
	 * 倒序排列 desc
	 */
	public static final int SQL_ORDER_DESC = 2;

	/** 排序列集合 */
	private List<Column> column = new ArrayList<Column>();

	/**
	 * 构造函数
	 */
	public Order() {
	}

	/**
	 * 构造函数
	 * @param column 列集合
	 */
	public Order(List<Column> column) {
		this.column = column;
	}

	/**
	 * 构造函数
	 * @param column 列名称
	 * @param type　排序类型
	 */
	public Order(String column, int type) {
		this.column.add(new Column(column, type));
	}

	/**
	 * 获取列集合
	 * @return 列集合
	 */
	public List<Column> getColumn() {
		return column;
	}

	/**
	 * 设置列集合
	 * @param column　列集合
	 */
	public void setColumn(List<Column> column) {
		this.column = column;
	}

	/**
	 * 添加排序条件
	 * @param column　列名称
	 * @param type　排序类型
	 */
	public void addOrder(String column, int type) {
		this.column.add(new Column(column, type));
	}
}
