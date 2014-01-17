package com.hatc.base.hibernate.util;

import java.util.ArrayList;
import java.util.List;

/**
* 
* <b>system��</b>      Эͬ�칫ƽ̨<br/>
* <b>description��</b> �����롢�򼯺���<br>
* <b>author��</b>      ����<br/>
* <b>copyright��</b>	�� ����������ϿƼ����޹�˾<br/>
* <b>version��</b>     VER1.00 2010-04-06<br/>
*
**/
public class Aggregate {
	/**
	 * ��ѯ�������� And
	 */
	private List<Parameter> parameterAnd = new ArrayList<Parameter>();

	/**
	 * ��ѯ�������� Or
	 */
	private List<Parameter> parameterOr = new ArrayList<Parameter>();
	
	/**
	 * ���ò�ѯ���� And
	 * 
	 * @param par
	 */
	@SuppressWarnings("unchecked")
	public void addParameterAnd(Parameter par) {
		this.parameterAnd.add(par);
	}

	/**
	 * ���ò�ѯ���� And
	 * 
	 * @param par
	 */
	@SuppressWarnings("unchecked")
	public void addParameterAnd(String column, int operator, Object[] value) {
		this.parameterAnd.add(new Parameter(column, operator, value));
	}

	/**
	 * ���ò�ѯ���� Or
	 * 
	 * @param par
	 */
	@SuppressWarnings("unchecked")
	public void addParameterOr(Parameter par) {
		this.parameterOr.add(par);
	}

	/**
	 * ���ò�ѯ���� Or
	 * 
	 * @param par
	 */
	@SuppressWarnings("unchecked")
	public void addParameterOr(String column, int operator, Object[] value) {
		this.parameterOr.add(new Parameter(column, operator, value));
	}
	
	/**
	 * ��ȡ�����򼯺�
	 * @return �����򼯺�
	 */
	public List<Parameter> getParameterOr() {
		return parameterOr;
	}

	/**
	 * ���������򼯺�
	 * @param parameterOr �����򼯺�
	 */
	public void setParameterOr(List<Parameter> parameterOr) {
		this.parameterOr = parameterOr;
	}

	/**
	 * ��ȡ�����뼯��
	 * @return�������뼯��
	 */
	public List<Parameter> getParameterAnd() {
		return parameterAnd;
	}

	/**
	 * ���������뼯��
	 * @param parameterAnd�������뼯��
	 */
	public void setParameterAnd(List<Parameter> parameterAnd) {
		this.parameterAnd = parameterAnd;
	}
}
