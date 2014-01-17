package com.hatc.base.hibernate.util;

/**
* 
* <b>system：</b>      协同办公平台<br/>
* <b>description：</b> 查询操作符参数集合对象<br>
* <b>author：</b>      王洋<br/>
* <b>copyright：</b>	　 北京华安天诚科技有限公司<br/>
* <b>version：</b>     VER1.00 2010-04-06<br/>
*
**/
public class Operator {

	/**
	 * 查询条件等于的数据 =
	 */
	public static final int SQL_EQ = 1;

	/**
	 * 查询条件不等于的数据 <>
	 */
	public static final int SQL_NE = 2;
	
	/**
	 * 查询条件大于的数据 >
	 */
	public static final int SQL_GT = 3;

	/**
	 * 查询条件小于的数据 <
	 */
	public static final int SQL_LT = 4;

	/**
	 * 查询条件大于等于的数据 >=
	 */
	public static final int SQL_GE = 5;

	/**
	 * 查询条件小于等于的数据 <=
	 */
	public static final int SQL_LE = 6;

	/**
	 * 模糊查询 like
	 */
	public static final int SQL_LIKE = 7;

	/**
	 * 模糊查询 like，不区分大小写
	 */
	public static final int SQL_I_LIKE = 12;

	/**
	 * 查询范围内的数据 butween
	 */
	public static final int SQL_BETWEEN = 8;

	/**
	 * 查询不在范围内的数据 not butween
	 */
	public static final int SQL_NOT_BETWEEN = 9;

	/**
	 * 查询不为空的数据 isNotNull
	 */
	public static final int SQL_NOT_NULL = 10;

	/**
	 * 查询不空的数据 isNull
	 */
	public static final int SQL_NULL = 11;
}
