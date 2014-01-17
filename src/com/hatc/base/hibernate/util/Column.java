package com.hatc.base.hibernate.util;

/**
* 
* <b>system��</b>      Эͬ�칫ƽ̨<br/>
* <b>description��</b> ��ѯ�ж���<br>
* <b>author��</b>      ����<br/>
* <b>copyright��</b>	�� ����������ϿƼ����޹�˾<br/>
* <b>version��</b>     VER1.00 2010-04-06<br/>
*
**/
public class Column {

	/** ������ */
	private String column;
	
	/** ���� */
	private String alias;

	/** ��ѯ���� */
	private int operator;

	/**
	 * ���������캯��
	 */
	public Column() {
	}
	
	/**
	 * ������������
	 * @param column������
	 * @param operator��������
	 */
	public Column(String column, int operator) {
		this.column = column;
		this.operator = operator;		
	}
	
	/**
	 * ������������
	 * @param column������
	 * @param operator��������
	 * @param alias������
	 */
	public Column(String column, int operator,String alias) {
		this.column = column;
		this.operator = operator;
		this.alias = alias;
	}

	/**
	 * ��ȡ�б���
	 * @return �б���
	 */
	public String getAlias() {
		return alias;
	}
	
	/**
	 * �����б���
	 * @param alias �б���
	 */
	public void setAlias(String alias) {
		this.alias = alias;
	}

	/**
	 * ��ȡ����
	 * @return ����
	 */
	public String getColumn() {
		return column;
	}

	/**
	 * ��������
	 * @param column ����
	 */
	public void setColumn(String column) {
		this.column = column;
	}

	/**
	 * ��ȡ�в�����
	 * @return ������
	 */
	public int getOperator() {
		return operator;
	}

	/**
	 * �����в�����
	 * @param operator ������
	 */
	public void setOperator(int operator) {
		this.operator = operator;
	}

}
