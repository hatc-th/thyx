package com.hatc.base.hibernate.util;

/**
* 
* <b>system：</b>      协同办公平台<br/>
* <b>description：</b> 查询条件对象<br>
* <b>author：</b>      王洋<br/>
* <b>copyright：</b>	　 北京华安天诚科技有限公司<br/>
* <b>version：</b>     VER1.00 2010-04-06<br/>
*
**/
public class Parameter {

	/** 列名称 */
	private String column;

	/** 查询操作符 */
	private int operator;

	/** 查询条件值数组 */
	private Object[] value;

	/**
	 * 构造函数
	 */
	public Parameter() {
	}

	/**
	 * 构造函数
	 * @param column　列名称
	 * @param operator　操作符
	 * @param value　条件值
	 */
	public Parameter(String column, int operator, Object[] value) {
		this.column = column;
		this.operator = operator;
		this.value = value;
	}
	
	/**
	 * 获取列名称
	 * @return　列名称
	 */
	public String getColumn() {
		return column;
	}

	/**
	 * 设置列名称
	 * @param column　列名称
	 */
	public void setColumn(String column) {
		this.column = column;
	}

	/**
	 * 获取操作符
	 * @return　操作符
	 */
	public int getOperator() {
		return operator;
	}

	/**
	 * 设置操作符
	 * @param operator　操作符
	 */
	public void setOperator(int operator) {
		this.operator = operator;
	}

	/**
	 * 获取查询条件值数组
	 * @return　查询条件值数组
	 */
	public Object[] getValue() {
		return value;
	}

	/**
	 * 设置查询条件值数组
	 * @param value　查询条件值数组
	 */
	public void setValue(Object[] value) {
		this.value = value;
	}

}
