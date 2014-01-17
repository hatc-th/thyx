/**
 * @file
 * @brief 服务请求身份标识
 * @author 郑庆为
 * @version 1.0, 2007-9-19
 * @version 1.0, 2007-9-19, 郑庆为
 */
package com.hatc.common.servicestub;

 

/**
 * <p>Title:类名称: ReqIdentity 服务请求身份标识，标识调用服务功能的相关客户信息 </p>
 *
 * <p>Description:  </p>
 *
 * <p>Copyright:  </p>
 *
 * <p>Company: </p>
 *
 * @author  
 * @version 1.0
 * @version 1.1 增加事务ID ningliyu
 */

 
public class ReqIdentity {
	public ReqIdentity() {

	}

	public String getFunctionId() {
		return functionId;
	}

	public void setFunctionId(String functionId) {
		this.functionId = functionId;
	}

	public String getLogonType() {
		return logonType;
	}

	public void setLogonType(String logonType) {
		this.logonType = logonType;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserRole() {
		return userRole;
	}

	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}

	private String sessionId = new String(); ///< 会话ID

	private String userId  = new String(); ///< 用户ID

	private String userRole  = new String(); ///< 当前登录角色

	private String logonType  = new String(); ///< 登录方式

    private String functionId  = new String(); ///< 请求功能ID
    private String ipAddress  = new String(); ///< 登录IP
    private String srvVersion  = new String(); ///< 服务版本
    private String variant = new String();  ///< 临时作用域变量

    private String deptid = new String();
    private String comUseScope;
    private String transID = "";   // 事务ID 
    private String mainFunctionID = "";    
    private String mainFunctionName = "";   
    
//    罗凤梅2012-03-16 日增加登录入口： 
    private String loginType="";
    private String loginUrl="";
     
    
   
    public String getLoginUrl() {
		return loginUrl;
	}

	public void setLoginUrl(String loginUrl) {
		this.loginUrl = loginUrl;
	}

	public String getLoginType() {
		return loginType;
	}

	public void setLoginType(String loginType) {
		this.loginType = loginType;
	}

	public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getSrvVersion() {
        return srvVersion;
    }

    public void setSrvVersion(String srvVersion) {
        this.srvVersion = srvVersion;
    }

	public String getVariant() {
		return variant;
	}

	public void setVariant(String variant) {
		this.variant = variant;
	}
	
	/////////////////////////////////////////////////////////
	/// ningliyu add 2011-11-16
	/// 目标:方便调试
	/////////////////////////////////////////////////////////
	@Override
	public String toString() {
	    String line = "\r\n";
		StringBuffer sb = new StringBuffer();
		sb.append("    userId:" + userId);
		sb.append(line);
		sb.append("    userRole:" + userRole);
		sb.append(line);sb.append("    sessionId:" + sessionId);
		sb.append(line);sb.append("    logonType:" + logonType);
		sb.append(line);sb.append("    functionId:" + functionId);
		sb.append(line);sb.append("    ipAddress:" + ipAddress);
		sb.append(line);sb.append("    srvVersion:" + srvVersion);
		sb.append(line);sb.append("    variant:" + variant);
		sb.append(line);sb.append("    transID:" + transID);
		
		sb.append(line);
		return sb.toString();
	}

	public String getTransID() {
		return transID;
	}

	public void setTransID(String transID) {
		this.transID = transID;
	}

	public String getMainFunctionID() {
		return mainFunctionID;
	}

	public void setMainFunctionID(String mainFunctionID) {
		this.mainFunctionID = mainFunctionID;
	}

	public String getMainFunctionName() {
		return mainFunctionName;
	}

	public void setMainFunctionName(String mainFunctionName) {
		this.mainFunctionName = mainFunctionName;
	}

	public void setDeptid(String deptid) {

		//根据人员部门ID，判断人员是一区还是二区用户，从而过滤数据
		//modify by lilj 2013.01.30 
		//暂时这样判断，不太好
		if ("000".equals(deptid) || "000/001".equals(deptid.substring(0,7)))
			this.setComUseScope("0");
		else if ("000/002".equals(deptid.substring(0,7)))
			this.setComUseScope("1");
		else if ("000/003".equals(deptid.substring(0,7)))
			this.setComUseScope("2");
		this.deptid = deptid;
	}

	public String getDeptid() {
		return deptid;
	}

	public void setComUseScope(String comUseScope) {
		this.comUseScope = comUseScope;
	}

	public String getComUseScope() {
		if (comUseScope == null)
			this.comUseScope = "0"; //为空时，默认为集团用户
		return comUseScope;
	}

	
}
