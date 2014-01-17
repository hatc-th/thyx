package com.hatc.base.hibernate.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* 
* <b>system��</b>      Эͬ�칫ƽ̨<br/>
* <b>description��</b> ��ѯ����������<br>
* <b>author��</b>      ����<br/>
* <b>copyright��</b>	�� ����������ϿƼ����޹�˾<br/>
* <b>version��</b>     VER1.00 2010-04-06<br/>
*
**/
public class Condition {
	/**
	 * ������������ AND
	 */
	public static final boolean PARAMETER_AND = true;

	/**
	 * ������������ OR
	 */
	public static final boolean PARAMETER_OR = false;

	/**
	 * ���ҵ���
	 */
	private Class objectClass = null;
	
	
	/**
	 * ����Ҫ���Ӷ���
	 */
	private Map<String, String> aliasMap = new HashMap<String,String>();
	
	/**
	 * ��ѯ����
	 */
	private List<Aggregate> parameterList = new ArrayList<Aggregate>();

	/**
	 * ��ѯ�������� And
	 */
	private List<Parameter> parameterAnd = new ArrayList<Parameter>();

	/**
	 * ��ѯ�������� Or
	 */
	private List<Parameter> parameterOr = new ArrayList<Parameter>();

	/**
	 * ����
	 */
	private List<Order> order = new ArrayList<Order>();

	/**
	 * ����
	 */
	private List<Group> group = new ArrayList<Group>();
	
	public Condition() {
	}

	public Condition(Class objectClass) {
		this.objectClass = objectClass;
	}

	@SuppressWarnings("unchecked")
	public Condition(Parameter parameter, boolean operType) {
		if (operType) {
			this.parameterAnd.add(parameter);
		} else {
			this.parameterOr.add(parameter);
		}
	}

	@SuppressWarnings("unchecked")
	public Condition(Class objectClass, Parameter parameter, boolean operType) {
		this.objectClass = objectClass;
		if (operType) {
			this.parameterAnd.add(parameter);
		} else {
			this.parameterOr.add(parameter);
		}
	}

	public Condition(Class objectClass, List<Parameter> parameter, boolean operType) {
		this.objectClass = objectClass;
		if (operType) {
			this.parameterAnd = parameter;
		} else {
			this.parameterOr = parameter;
		}
	}

	public Condition(Class objectClass, List<Parameter> parameterAnd, List<Parameter> parameterOr) {
		this.objectClass = objectClass;
		this.parameterAnd = parameterAnd;
		this.parameterOr = parameterOr;
	}

	public List<Group> getGroup() {
		return group;
	}

	public void setGroup(List<Group> group) {
		this.group = group;
	}

	public List<Parameter> getParameterOr() {
		return parameterOr;
	}

	public void setParameterOr(List<Parameter> parameterOr) {
		this.parameterOr = parameterOr;
	}

	public List<Parameter> getParameterAnd() {
		return parameterAnd;
	}

	public void setParameterAnd(List<Parameter> parameterAnd) {
		this.parameterAnd = parameterAnd;
	}

	public Class getObjectClass() {
		return objectClass;
	}

	public void setObjectClass(Class objectClass) {
		this.objectClass = objectClass;
	}

	public List<Order> getOrder() {
		return order;
	}

	public void setOrder(List<Order> order) {
		this.order = order;
	}

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
	 * ���ò�ѯ���� ����
	 * 
	 * @param order
	 */
	public void addOrder(Order order) {
		this.order.add(order);
	}

	/**
	 * ���ò�ѯ���� ����
	 * 
	 * @param group
	 */
	public void addGroup(Group group) {
		this.group.add(group);
	}

	/**
	 * ��ȡ�롢���ѯ����
	 * @return����ѯ����
	 */
	public List<Aggregate> getParameterList() {
		return parameterList;
	}

	/**
	 * �����롢���ѯ����
	 * @param parameterList ��ѯ����
	 */
	public void setParameterList(List<Aggregate> parameterList) {
		this.parameterList = parameterList;
	}
	
	/**
	 * ���ò�ѯ��������
	 * @param aggregate ��ѯ����
	 */
	public void addAggregate(Aggregate aggregate) {
		this.parameterList.add(aggregate);
	}

	public Map<String, String> getAliasMap() {
		return aliasMap;
	}

	public void setAliasMap(Map<String, String> aliasMap) {
		this.aliasMap = aliasMap;
	}
	
	public String getAlias(String keys) {
		return aliasMap.get(keys);
	}

	public void setAliasMap(String className, String alias) {
		this.aliasMap.put(className, alias);
	}
}
