package com.hatc.base.hibernate.util;

import java.util.ArrayList;
import java.util.List;

/**
* 
* <b>system��</b>      Эͬ�칫ƽ̨<br/>
* <b>description��</b> ��ѯ���򼯺϶���<br>
* <b>author��</b>      ����<br/>
* <b>copyright��</b>	�� ����������ϿƼ����޹�˾<br/>
* <b>version��</b>     VER1.00 2010-04-06<br/>
*
**/
public class Order {

	/**
	 * �������� asc
	 */
	public static final int SQL_ORDER_ASC = 1;

	/**
	 * �������� desc
	 */
	public static final int SQL_ORDER_DESC = 2;

	/** �����м��� */
	private List<Column> column = new ArrayList<Column>();

	/**
	 * ���캯��
	 */
	public Order() {
	}

	/**
	 * ���캯��
	 * @param column �м���
	 */
	public Order(List<Column> column) {
		this.column = column;
	}

	/**
	 * ���캯��
	 * @param column ������
	 * @param type����������
	 */
	public Order(String column, int type) {
		this.column.add(new Column(column, type));
	}

	/**
	 * ��ȡ�м���
	 * @return �м���
	 */
	public List<Column> getColumn() {
		return column;
	}

	/**
	 * �����м���
	 * @param column���м���
	 */
	public void setColumn(List<Column> column) {
		this.column = column;
	}

	/**
	 * �����������
	 * @param column��������
	 * @param type����������
	 */
	public void addOrder(String column, int type) {
		this.column.add(new Column(column, type));
	}
}
