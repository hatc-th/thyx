/**
 * @file
 * @brief ��ɫ���ܶ����������ļ�
 * @author ֣��Ϊ
 * @version 1.0, 2007-9-19
 * @version 1.0, 2007-9-19, ֣��Ϊ
 */
package com.hatc.common.businessdata;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */

//***************************************************************************
/**
 * ������: UserRole ��������: ��ɫ����
 */
// ***************************************************************************
public class UserRole implements Serializable {

	private static final long serialVersionUID = 546003341751469002L;

	public UserRole() {
	}

	public String getClientPreferredFunctionCode() {
		return clientPreferredFunctionCode;
	}

	public boolean isIsDefaultRole() {
		return isDefaultRole;
	}

	public ArrayList getRoleFunctions() {
		return roleFunctions;
	}

	public String getRoleName() {
		return roleName;
	}

	public String getWebPreferredFunctionCode() {
		return webPreferredFunctionCode;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setClientPreferredFunctionCode(
			String clientPreferredFunctionCode) {
		this.clientPreferredFunctionCode = clientPreferredFunctionCode;
	}

	public void setIsDefaultRole(boolean isDefaultRole) {
		this.isDefaultRole = isDefaultRole;
	}

	public void setRoleFunctions(ArrayList roleFunctions) {
		this.roleFunctions = roleFunctions;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public void setWebPreferredFunctionCode(String webPreferredFunctionCode) {
		this.webPreferredFunctionCode = webPreferredFunctionCode;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	/** ��ɫID */
	private String roleId = new String();

	/**
	 * ��ɫ��
	 */
	private String roleName = new String();

	/**
	 * �Ƿ�Ĭ�Ͻ�ɫ
	 */
	private boolean isDefaultRole;
	
	/** ����ID*/
	private String deptId = new String();
	
	/** ��������*/
	private String deptName = new String();

	/**
	 * Web��ѡ����
	 */
	private String webPreferredFunctionCode = new String();

	/**
	 * Client ��ѡ����
	 */
	private String clientPreferredFunctionCode = new String();
	
	private String secretLevel = new String();		///< �ܼ�
	private String roleType = new String();			///< ��ɫ��� 1ȫ�ֽ�ɫ��2Ϊ���Ž�ɫ��3Ϊ��Ŀ��ɫ

	/**
	 * ��ɫ�����б�<class RoleFunction>
	 * 
	 * @see RoleFunction
	 */
	private ArrayList roleFunctions = new ArrayList();

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public String getRoleType() {
		return roleType;
	}

	public void setRoleType(String roleType) {
		this.roleType = roleType;
	}

	public String getSecretLevel() {
		return secretLevel;
	}

	public void setSecretLevel(String secretLevel) {
		this.secretLevel = secretLevel;
	}
	
	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}	
};
