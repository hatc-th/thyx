package com.hatc.common.exception;

public class LoginException extends Exception {
	/**
     * δ֪����
     */
    public static final int UNKNOWN_ERR = -1;
    /**
     * �������
     */
    public static final int NETWORK_ERR = 10;
    /**
     * �û������벻ƥ��
     */
    public static final int BAD_PWD = 11;
    /**
     * ���ݿ����Ӵ���
     */
    public static final int DB_ERR = 12;
    /**
     * ����δ��Ȩ����
     */
    public static final int AUTH_ERR = 13;           ///< ����δ��Ȩ
    public static final int BAD_IP_CONF = 14;        ///< ��ƥ��Ŀͻ���IP
    public static final int KICK_OUT = 15;           ///< �û�����һ����¼
    public static final int NOT_LOGON = 16;          ///< �û�δ��¼,�����һ����¼,���¼��ʱ
    public static final int LOCK_FAIL = 17;          ///< �������ʧ��
    public static final int ALREADY_LOGON = 18;      ///< �û��ѵ�¼
    public static final int DB_ERR_CREATE = 19;      ///< ���ݿ�������ݴ���
    public static final int DB_ERR_READ = 20;        ///< ���ݿ��ѯ���ݴ���
    public static final int DB_ERR_UPDATE = 21;      ///< ���ݿ��޸����ݴ���
    public static final int DB_ERR_DELETE = 22;      ///< ���ݿ�ɾ�����ݴ���
    public static final int BAD_LOGIN_MODE = 23;     ///< δ֪�ĵ�¼��ʽ "C" "W"
    public static final int INVALID_USER = 24;       ///< ��Ч���û�
    public static final int CONSTRAINT_ERR = 25;     ///< ���ݲ��������ظ�
    public static final int DEPEND_ERR = 26;         ///< �����������
    public static final int INCOMPATIBLE_VER = 27;   ///< ��ƥ��ķ���汾
    public static final int PASS_VALID_DATE = 38;    ///< �û��ѹ���
    public static final int MAX_PWD_ERROR = 51;      ///< �û�������������������ϵͳ�������
    public static final int EXCEL_FILE_ERR = 30;     ///< �ļ������ڻ��ļ���ʽ����
    public static final int EXCEL_CONTENT_ERR = 31;  ///< �ļ����ݲ�����Ҫ��
    public static final int EXCEL_VERSION_ERR = 32;  ///< �汾��һ��
    public static final int NO_TASK_BY_EVERY = 33;  ///< �޶�Ӧ������
    public static final int TASK_BY_EVERY_USE = 34;  ///< ��Ӧ�������ѱ�ʹ��
    public static final int TASK_BY_EVERY_CANCLE = 35;  ///< ��Ӧ�������ѱ�ʹ��
    public static final int BY_DAYFP_SS = 36;  ///< ��Ӧ�ĳ��μƻ�����׼����׼���ɳ���
    public static final int BY_DAYFP_END = 37;  ///< ��Ӧ�ļܴμƻ�����ɣ����������
    public static final int TASK_RUNNING_ERR  = 40;  ///< ��ʱ�����������л��ֹ����	
    public static final int AIRLINE_CHECK_ERR = 45;  ///< ���߼�鲻����Ҫ��
    public static final int RESEARCH_ID_ERR = 46;	  ///< ����ID¼�����
    public static final int PLANE_TYPE_ERR  = 47;	  ///< �ɻ����ͼ�����
    public static final int USER_DEFINE = 99;        ///< �û��Զ���
    
    ////////////////////////////////////////////////////////////////////////////////////////
    /// ningliyu add 2011-11-15
    /// ������Ӧ����Ϊ��
    ///////////////////////////////////////////////////////////////////////////////////////
    
    public static final int RESPONSE_NULL = 80;        ///< ����Ϊ��
    
    
    private int faultCode ;


	public int getFaultCode() {
		return faultCode;
	}


	public void setFaultCode(int faultCode) {
		this.faultCode = faultCode;
	}
    
    public LoginException(int faultCode){
    	setFaultCode(faultCode);
    }
}
