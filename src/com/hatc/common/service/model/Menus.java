package com.hatc.common.service.model;

import java.io.Serializable;
import java.util.List;

import com.hatc.common.businessdata.RoleFunction;

/**
* 
* <b>system��</b>      Эͬ�칫ƽ̨<br/>
* <b>description��</b> �Զ���˵�ģ��<br/>
* <b>author��</b>      ����<br/>
* <b>copyright��</b>	�� ����������ϿƼ����޹�˾<br/>
* <b>version��</b>     VER1.00 2010-04-06<br/>
*
**/
public class Menus implements Serializable {

	private static final long serialVersionUID = 6208712994463495761L;

	/**
	 * ��ɫȨ�޶���
	 */
	private RoleFunction function;

	/**
	 * �˵���ʶ
	 */
	private String mark;

	/**
	 * ��дID
	 */
	private String sid;

	/**
	 * �˵��Ӷ���
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
	 *            Ҫ���õ� function
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
	 *            Ҫ���õ� list
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
	 *            Ҫ���õ� mark
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
	 *            Ҫ���õ� sid
	 */
	public void setSid(String sid) {
		this.sid = sid;
	}

}
