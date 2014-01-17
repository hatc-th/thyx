package com.hatc.base.hibernate.util;

/**
* 
* <b>system��</b>      Эͬ�칫ƽ̨<br/>
* <b>description��</b> ��ѯ�������������϶���<br>
* <b>author��</b>      ����<br/>
* <b>copyright��</b>	�� ����������ϿƼ����޹�˾<br/>
* <b>version��</b>     VER1.00 2010-04-06<br/>
*
**/
public class Operator {

	/**
	 * ��ѯ�������ڵ����� =
	 */
	public static final int SQL_EQ = 1;

	/**
	 * ��ѯ���������ڵ����� <>
	 */
	public static final int SQL_NE = 2;
	
	/**
	 * ��ѯ�������ڵ����� >
	 */
	public static final int SQL_GT = 3;

	/**
	 * ��ѯ����С�ڵ����� <
	 */
	public static final int SQL_LT = 4;

	/**
	 * ��ѯ�������ڵ��ڵ����� >=
	 */
	public static final int SQL_GE = 5;

	/**
	 * ��ѯ����С�ڵ��ڵ����� <=
	 */
	public static final int SQL_LE = 6;

	/**
	 * ģ����ѯ like
	 */
	public static final int SQL_LIKE = 7;

	/**
	 * ģ����ѯ like�������ִ�Сд
	 */
	public static final int SQL_I_LIKE = 12;

	/**
	 * ��ѯ��Χ�ڵ����� butween
	 */
	public static final int SQL_BETWEEN = 8;

	/**
	 * ��ѯ���ڷ�Χ�ڵ����� not butween
	 */
	public static final int SQL_NOT_BETWEEN = 9;

	/**
	 * ��ѯ��Ϊ�յ����� isNotNull
	 */
	public static final int SQL_NOT_NULL = 10;

	/**
	 * ��ѯ���յ����� isNull
	 */
	public static final int SQL_NULL = 11;
}
