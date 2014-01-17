package com.hatc.common.service.model;

import java.io.Serializable;
import java.util.List;

import com.hatc.common.businessdata.RoleFunction;

/**
* 
* <b>system：</b>      协同办公平台<br/>
* <b>description：</b> 自定义菜单模型<br/>
* <b>author：</b>      王洋<br/>
* <b>copyright：</b>	　 北京华安天诚科技有限公司<br/>
* <b>version：</b>     VER1.00 2010-04-06<br/>
*
**/
public class Menus implements Serializable {

	private static final long serialVersionUID = 6208712994463495761L;

	/**
	 * 角色权限对象
	 */
	private RoleFunction function;

	/**
	 * 菜单标识
	 */
	private String mark;

	/**
	 * 缩写ID
	 */
	private String sid;

	/**
	 * 菜单子对象集
	 */
	private List<Menus> list;

	/**
	 * @return function
	 */
	public RoleFunction getFunction() {
		return function;
	}

	/**
	 * @param function
	 *            要设置的 function
	 */
	public void setFunction(RoleFunction function) {
		this.function = function;
	}

	/**
	 * @return list
	 */
	public List<Menus> getList() {
		return list;
	}

	/**
	 * @param list
	 *            要设置的 list
	 */
	public void setList(List<Menus> list) {
		this.list = list;
	}

	/**
	 * @return mark
	 */
	public String getMark() {
		return mark;
	}

	/**
	 * @param mark
	 *            要设置的 mark
	 */
	public void setMark(String mark) {
		this.mark = mark;
	}

	/**
	 * @return sid
	 */
	public String getSid() {
		return sid;
	}

	/**
	 * @param sid
	 *            要设置的 sid
	 */
	public void setSid(String sid) {
		this.sid = sid;
	}

}
