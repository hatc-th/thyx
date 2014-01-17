package com.hatc.common.businessdata;

import java.io.Serializable;

/**  
*地点类
*/
public class Site implements Serializable {
	/**
     * 
     */
    private static final long serialVersionUID = -5914266014341192286L;

    public Site() {
	}

	public String getAirdrome_name() {
		return airdrome_name;
	}

	public void setAirdrome_name(String airdrome_name) {
		this.airdrome_name = airdrome_name;
	}

	public String getNew_id() {
		return new_id;
	}

	public void setNew_id(String new_id) {
		this.new_id = new_id;
	}

	public String getSite_id() {
		return site_id;
	}

	public void setSite_id(String site_id) {
		this.site_id = site_id;
	}

	public String getSite_name() {
		return site_name;
	}

	public void setSite_name(String site_name) {
		this.site_name = site_name;
	}
	
	public String getDept_id() {
		return dept_id;
	}

	public void setDept_id(String dept_id) {
		this.dept_id = dept_id;
	}

	public String getDept_name() {
		return dept_name;
	}

	public void setDept_name(String dept_name) {
		this.dept_name = dept_name;
	}

	public String getLong_la_titude() {
		return long_la_titude;
	}

	public void setLong_la_titude(String long_la_titude) {
		this.long_la_titude = long_la_titude;
	}
	
	public int getUtc() {
		return utc;
	}

	public void setUtc(int utc) {
		this.utc = utc;
	}
	
	public int getSys_flag() {
		return sys_flag;
	}

	public void setSys_flag(int sys_flag) {
		this.sys_flag = sys_flag;
	}
	
	private String site_id = new String(); ///< 地点编号

	private String site_name = new String(); ///< 地点名称

	private String airdrome_name = new String(); ///< 机场名称

	private int sys_flag = 0; ///< 系统标志 1：不可删除修改 0：可删除修改
	
	private String long_la_titude = new String(); ///< 经纬度

	private String dept_id = new String(); ///< 部门ID
	
	private String dept_name = new String(); ///< 部门名称

	private int utc = 0; ///< 时差
	
	private String new_id = new String(); ///< 地点编号（新），仅修改时用于区别新旧代码，代码没有改变时，值与site_id相同，不能为空
}
