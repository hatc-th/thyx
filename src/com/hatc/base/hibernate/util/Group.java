package com.hatc.base.hibernate.util;

import java.util.ArrayList;
import java.util.List;

/**
* 
* <b>system��</b>      Эͬ�칫ƽ̨<br/>
* <b>description��</b> ���򼯺���<br>
* <b>author��</b>      ����<br/>
* <b>copyright��</b>	�� ����������ϿƼ����޹�˾<br/>
* <b>version��</b>     VER1.00 2010-04-06<br/>
*
**/
public class Group {

	/**
	 * �����ѯ���� count
	 */
	public static final int SQL_GROUP_COUNT = 1;

	/**
	 * �����ѯ���� avg
	 */
	public static final int SQL_GROUP_AVG = 2;

	/**
	 * �����ѯ���� max
	 */
	public static final int SQL_GROUP_MAX = 3;

	/**
	 * �����ѯ���� min
	 */
	public static final int SQL_GROUP_MIN = 4;

	/**
	 * �����ѯ���� row Count
	 */
	public static final int SQL_GROUP_ROWCOUNT = 5;

	/**
	 * �����ѯ���� count Distinct
	 */
	public static final int SQL_GROUP_COUNT_DISTINCT = 6;

	/**
	 * �����ѯ���� sum
	 */
	public static final int SQL_GROUP_SUM = 7;

	// ������
	private List<Column> column = new ArrayList<Column>();

	// ����������
	private List<String> groupColumn = new ArrayList<String>();

	// ����
	/**
	 * �����ѯ�������� And
	 */
	private List<Parameter> parameterAnd = new ArrayList<Parameter>();

	/**
	 * �����ѯ�������� Or
	 */
	private List<Parameter> parameterOr = new ArrayList<Parameter>();

	public Group() {
	}

	/**
	 * ��ȡ������������
	 * @return ���򼯺�
	 */
	public List<String> getGroupColumn() {
		return groupColumn;
	}

	/**
	 * ����������������
	 * @param groupColumn ���򼯺�
	 */
	public void setGroupColumn(List<String> groupColumn) {
		this.groupColumn = groupColumn;
	}

	/**
	 * ��ȡ�����м���
	 * @return�������м���
	 */
	public List<Column> getColumn() {
		return column;
	}

	/**
	 * ���������м���
	 * @param column �����м���
	 */
	public void setColumn(List<Column> column) {
		this.column = column;
	}

	/**
	 * ��ȡ��������ѯ����
	 * @return ��������ѯ����
	 */
	public List<Parameter> getParameterAnd() {
		return parameterAnd;
	}

	/**
	 * ������������ѯ����
	 * @param parameterAnd����������ѯ����
	 */
	public void setParameterAnd(List<Parameter> parameterAnd) {
		this.parameterAnd = parameterAnd;
	}

	/**
	 * ��ȡ��������ѯ����
	 * @return����������ѯ����
	 */
	public List<Parameter> getParameterOr() {
		return parameterOr;
	}

	/**
	 * ���û�������ѯ����
	 * @param parameterOr����������ѯ����
	 */
	public void setParameterOr(List<Parameter> parameterOr) {
		this.parameterOr = parameterOr;
	}

	/**
	 * �����
	 * @param column ����
	 * @param operator��������
	 */
	public void addColumn(String column, int operator) {
		this.column.add(new Column(column, operator));
	}

	/**
	 * �����
	 * @param column������
	 * @param operator��������
	 * @param alias������
	 */
	public void addColumn(String column, int operator, String alias) {
		this.column.add(new Column(column, operator, alias));
	}

	/**
	 * ���������
	 * @param column������
	 */
	public void addGroupColumn(String column) {
		this.groupColumn.add(column);
	}

	/**
	 * ��Ӳ�ѯ����
	 * @param column������
	 * @param operator������
	 * @param value��ֵ����
	 */
	public void addParameterAnd(String column, int operator, Object[] value) {
		this.parameterAnd.add(new Parameter(column, operator, value));
	}

	/**
	 * ��Ӳ�ѯ����
	 * @param column������
	 * @param operator��������
	 * @param value��ֵ����
	 */
	public void addParameterOr(String column, int operator, Object[] value) {
		this.parameterOr.add(new Parameter(column, operator, value));
	}
}
