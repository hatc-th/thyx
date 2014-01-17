package com.hatc.base.hibernate.util;

import java.util.ArrayList;
import java.util.List;

/**
* 
* <b>system：</b>      协同办公平台<br/>
* <b>description：</b> 复杂与、或集合类<br>
* <b>author：</b>      王洋<br/>
* <b>copyright：</b>	　 北京华安天诚科技有限公司<br/>
* <b>version：</b>     VER1.00 2010-04-06<br/>
*
**/
public class Aggregate {
	/**
	 * 查询条件集合 And
	 */
	private List<Parameter> parameterAnd = new ArrayList<Parameter>();

	/**
	 * 查询条件集合 Or
	 */
	private List<Parameter> parameterOr = new ArrayList<Parameter>();
	
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
	 * 获取条件或集合
	 * @return 条件或集合
	 */
	public List<Parameter> getParameterOr() {
		return parameterOr;
	}

	/**
	 * 设置条件或集合
	 * @param parameterOr 条件或集合
	 */
	public void setParameterOr(List<Parameter> parameterOr) {
		this.parameterOr = parameterOr;
	}

	/**
	 * 获取条件与集合
	 * @return　条件与集合
	 */
	public List<Parameter> getParameterAnd() {
		return parameterAnd;
	}

	/**
	 * 设置条件与集合
	 * @param parameterAnd　条件与集合
	 */
	public void setParameterAnd(List<Parameter> parameterAnd) {
		this.parameterAnd = parameterAnd;
	}
}
