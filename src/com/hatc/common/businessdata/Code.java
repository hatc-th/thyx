package com.hatc.common.businessdata;

import java.io.Serializable;

/**
 * ���������
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
			exportflag = "0"; //Ϊ��ʱĬ��Ϊȫ�ֽ�ɫ������ʹ��
		return exportflag;
	}

	private String code_id = new String(); // /< ����ID

    private String name = new String(); // /< ��������

    private String class_id = new String(); // /< �������ID

    private String code_detail = new String(); // /< ����˵��

    private String class_name = new String(); // /< �����������

    private int sys_flag = 0; // /< ϵͳ��־ 1������ɾ���޸� 0����ɾ���޸�

    private String newCode = new String(); ///< ����ID(��)�����޸�ʱ���������¾ɴ��룬����û�иı�ʱ��ֵ��ԭcodeId��ͬ������Ϊ�գ�

    private String newClass = new String(); ///< �������ID(��)�����޸�ʱ���������¾ɴ��룬����û�иı�ʱ��ֵ��ԭclassId��ͬ������Ϊ�գ�

    private Integer subDataNum; ///< ��Ӧ��������

    private String superiorId = new String(); ///< 
    
    private String deptId = new String(); ///< code����Ӳ��ţ�����ʹ�ã�JD��ʹ�ã�
    
    private String exportflag;

    
}
