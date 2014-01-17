package com.hatc.base.hibernate.util;

/**
* 
* <b>system：</b>      协同办公平台<br/>
* <b>description：</b> 查询列对象<br>
* <b>author：</b>      王洋<br/>
* <b>copyright：</b>	　 北京华安天诚科技有限公司<br/>
* <b>version：</b>     VER1.00 2010-04-06<br/>
*
**/
public class Column {

	/** 列名称 */
	private String column;
	
	/** 别名 */
	private String alias;

	/** 查询符号 */
	private int operator;

	/**
	 * 列名对象构造函数
	 */
	public Column() {
	}
	
	/**
	 * 构建列名对象
	 * @param column　列名
	 * @param operator　操作符
	 */
	public Column(String column, int operator) {
		this.column = column;
		this.operator = operator;		
	}
	
	/**
	 * 构建列名对象
	 * @param column　列名
	 * @param operator　操作符
	 * @param alias　别名
	 */
	public Column(String column, int operator,String alias) {
		this.column = column;
		this.operator = operator;
		this.alias = alias;
	}

	/**
	 * 获取列别名
	 * @return 列别名
	 */
	public String getAlias() {
		return alias;
	}
	
	/**
	 * 设置列别名
	 * @param alias 列别名
	 */
	public void setAlias(String alias) {
		this.alias = alias;
	}

	/**
	 * 获取列名
	 * @return 列名
	 */
	public String getColumn() {
		return column;
	}

	/**
	 * 设置列名
	 * @param column 列名
	 */
	public void setColumn(String column) {
		this.column = column;
	}

	/**
	 * 获取列操作符
	 * @return 操作符
	 */
	public int getOperator() {
		return operator;
	}

	/**
	 * 设置列操作符
	 * @param operator 操作符
	 */
	public void setOperator(int operator) {
		this.operator = operator;
	}

}
