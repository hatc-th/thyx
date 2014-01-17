/**
 * @file
 * @brief ����������ݱ�ʶ
 * @author ֣��Ϊ
 * @version 1.0, 2007-9-19
 * @version 1.0, 2007-9-19, ֣��Ϊ
 */
package com.hatc.common.servicestub;

 

/**
 * <p>Title:������: ReqIdentity ����������ݱ�ʶ����ʶ���÷����ܵ���ؿͻ���Ϣ </p>
 *
 * <p>Description:  </p>
 *
 * <p>Copyright:  </p>
 *
 * <p>Company: </p>
 *
 * @author  
 * @version 1.0
 * @version 1.1 ��������ID ningliyu
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

	private String sessionId = new String(); ///< �ỰID

	private String userId  = new String(); ///< �û�ID

	private String userRole  = new String(); ///< ��ǰ��¼��ɫ

	private String logonType  = new String(); ///< ��¼��ʽ

    private String functionId  = new String(); ///< ������ID
    private String ipAddress  = new String(); ///< ��¼IP
    private String srvVersion  = new String(); ///< ����汾
    private String variant = new String();  ///< ��ʱ���������

    private String deptid = new String();
    private String comUseScope;
    private String transID = "";   // ����ID 
    private String mainFunctionID = "";    
    private String mainFunctionName = "";   
    
//    �޷�÷2012-03-16 �����ӵ�¼��ڣ� 
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
	/// Ŀ��:�������
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

		//������Ա����ID���ж���Ա��һ�����Ƕ����û����Ӷ���������
		//modify by lilj 2013.01.30 
		//��ʱ�����жϣ���̫��
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
			this.comUseScope = "0"; //Ϊ��ʱ��Ĭ��Ϊ�����û�
		return comUseScope;
	}

	
}
