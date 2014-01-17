package com.hatc.common.businessdata;

import java.io.Serializable;

/**
 * 分类代码类
 */
public class Code implements Serializable {

    private static final long serialVersionUID = -6256268658773500567L;

    public Code() {
    }

    public String getClass_id() {
        return class_id;
    }

    public void setClass_id(String class_id) {
        this.class_id = class_id;
    }

    public String getClass_name() {
        return class_name;
    }

    public void setClass_name(String class_name) {
        this.class_name = class_name;
    }

    public String getCode_detail() {
        return code_detail;
    }

    public void setCode_detail(String code_detail) {
        this.code_detail = code_detail;
    }

    public String getCode_id() {
        return code_id;
    }

    public void setCode_id(String code_id) {
        this.code_id = code_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNewClass() {
        return newClass;
    }

    public void setNewClass(String newClass) {
        this.newClass = newClass;
    }

    public String getNewCode() {
        return newCode;
    }

    public void setNewCode(String newCode) {
        this.newCode = newCode;
    }

    public int getSys_flag() {
        return sys_flag;
    }

    public void setSys_flag(int sys_flag) {
        this.sys_flag = sys_flag;
    }

    public Integer getSubDataNum() {
    	return subDataNum;
    }
    
    public void setSubDataNum(Integer subDataNum) {
    	this.subDataNum = subDataNum;
    }
    
    public String getSuperiorId() {
		return superiorId;
	}

	public void setSuperiorId(String superiorId) {
		this.superiorId = superiorId;
	}

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public void setExportflag(String exportflag) {
		this.exportflag = exportflag;
	}

	public String getExportflag() {
		if (null==exportflag)
			exportflag = "0"; //为空时默认为全局角色，都能使用
		return exportflag;
	}

	private String code_id = new String(); // /< 代码ID

    private String name = new String(); // /< 代码名称

    private String class_id = new String(); // /< 代码分类ID

    private String code_detail = new String(); // /< 代码说明

    private String class_name = new String(); // /< 代码分类名称

    private int sys_flag = 0; // /< 系统标志 1：不可删除修改 0：可删除修改

    private String newCode = new String(); ///< 代码ID(新)（仅修改时用于区别新旧代码，代码没有改变时，值与原codeId相同，不能为空）

    private String newClass = new String(); ///< 代码分类ID(新)（仅修改时用于区别新旧代码，代码没有改变时，值与原classId相同，不能为空）

    private Integer subDataNum; ///< 对应数据条数

    private String superiorId = new String(); ///< 
    
    private String deptId = new String(); ///< code的添加部门（海航使用，JD不使用）
    
    private String exportflag;

    
}
