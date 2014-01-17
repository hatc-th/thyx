package com.hatc.base.hibernate.util;

/**
* 
* <b>system��</b>      Эͬ�칫ƽ̨<br/>
* <b>description��</b> ��ѯ��������<br>
* <b>author��</b>      ����<br/>
* <b>copyright��</b>	�� ����������ϿƼ����޹�˾<br/>
* <b>version��</b>     VER1.00 2010-04-06<br/>
*
**/
public class Parameter {

	/** ������ */
	private String column;

	/** ��ѯ������ */
	private int operator;

	/** ��ѯ����ֵ���� */
	private Object[] value;

	/**
	 * ���캯��
	 */
	public Parameter() {
	}

	/**
	 * ���캯��
	 * @param column��������
	 * @param operator��������
	 * @param value������ֵ
	 */
	public Parameter(String column, int operator, Object[] value) {
		this.column = column;
		this.operator = operator;
		this.value = value;
	}
	
	/**
	 * ��ȡ������
	 * @return��������
	 */
	public String getColumn() {
		return column;
	}

	/**
	 * ����������
	 * @param column��������
	 */
	public void setColumn(String column) {
		this.column = column;
	}

	/**
	 * ��ȡ������
	 * @return��������
	 */
	public int getOperator() {
		return operator;
	}

	/**
	 * ���ò�����
	 * @param operator��������
	 */
	public void setOperator(int operator) {
		this.operator = operator;
	}

	/**
	 * ��ȡ��ѯ����ֵ����
	 * @return����ѯ����ֵ����
	 */
	public Object[] getValue() {
		return value;
	}

	/**
	 * ���ò�ѯ����ֵ����
	 * @param value����ѯ����ֵ����
	 */
	public void setValue(Object[] value) {
		this.value = value;
	}

}
