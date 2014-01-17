/**
 * @file
 * @brief 角色功能对象数据类文件
 * @author 郑庆为
 * @version 1.0, 2007-9-19
 * @version 1.0, 2007-9-19, 郑庆为
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
 * 类名称: UserRole 处理内容: 角色定义
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

	/** 角色ID */
	private String roleId = new String();

	/**
	 * 角色名
	 */
	private String roleName = new String();

	/**
	 * 是否默认角色
	 */
	private boolean isDefaultRole;
	
	/** 部门ID*/
	private String deptId = new String();
	
	/** 部门名称*/
	private String deptName = new String();

	/**
	 * Web首选功能
	 */
	private String webPreferredFunctionCode = new String();

	/**
	 * Client 首选功能
	 */
	private String clientPreferredFunctionCode = new String();
	
	private String secretLevel = new String();		///< 密级
	private String roleType = new String();			///< 角色类别 1全局角色，2为部门角色，3为项目角色

	/**
	 * 角色功能列表<class RoleFunction>
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
