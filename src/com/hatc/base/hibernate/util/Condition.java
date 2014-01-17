package com.hatc.base.hibernate.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* 
* <b>system：</b>      协同办公平台<br/>
* <b>description：</b> 查询条件集合类<br>
* <b>author：</b>      王洋<br/>
* <b>copyright：</b>	　 北京华安天诚科技有限公司<br/>
* <b>version：</b>     VER1.00 2010-04-06<br/>
*
**/
public class Condition {
	/**
	 * 参数操作类型 AND
	 */
	public static final boolean PARAMETER_AND = true;

	/**
	 * 参数操作类型 OR
	 */
	public static final boolean PARAMETER_OR = false;

	/**
	 * 查找的类
	 */
	private Class objectClass = null;
	
	
	/**
	 * 所需要联接对象
	 */
	private Map<String, String> aliasMap = new HashMap<String,String>();
	
	/**
	 * 查询集合
	 */
	private List<Aggregate> parameterList = new ArrayList<Aggregate>();

	/**
	 * 查询条件集合 And
	 */
	private List<Parameter> parameterAnd = new ArrayList<Parameter>();

	/**
	 * 查询条件集合 Or
	 */
	private List<Parameter> parameterOr = new ArrayList<Parameter>();

	/**
	 * 排序
	 */
	private List<Order> order = new ArrayList<Order>();

	/**
	 * 分组
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
	 * 设置查询条件 And
	 * 
	 * @param par
	 */
	@SuppressWarnings("unchecked")
	public void addParameterAnd(Parameter par) {
		this.parameterAnd.add(par);
	}

	/**
	 * 设置查询条件 And
	 * 
	 * @param par
	 */
	@SuppressWarnings("unchecked")
	public void addParameterAnd(String column, int operator, Object[] value) {
		this.parameterAnd.add(new Parameter(column, operator, value));
	}

	/**
	 * 设置查询条件 Or
	 * 
	 * @param par
	 */
	@SuppressWarnings("unchecked")
	public void addParameterOr(Parameter par) {
		this.parameterOr.add(par);
	}

	/**
	 * 设置查询条件 Or
	 * 
	 * @param par
	 */
	@SuppressWarnings("unchecked")
	public void addParameterOr(String column, int operator, Object[] value) {
		this.parameterOr.add(new Parameter(column, operator, value));
	}

	/**
	 * 设置查询条件 排序
	 * 
	 * @param order
	 */
	public void addOrder(Order order) {
		this.order.add(order);
	}

	/**
	 * 设置查询条件 排序
	 * 
	 * @param group
	 */
	public void addGroup(Group group) {
		this.group.add(group);
	}

	/**
	 * 获取与、或查询集合
	 * @return　查询集合
	 */
	public List<Aggregate> getParameterList() {
		return parameterList;
	}

	/**
	 * 设置与、或查询集合
	 * @param parameterList 查询集合
	 */
	public void setParameterList(List<Aggregate> parameterList) {
		this.parameterList = parameterList;
	}
	
	/**
	 * 设置查询集合条件
	 * @param aggregate 查询条件
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
