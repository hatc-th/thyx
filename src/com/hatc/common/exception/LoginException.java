package com.hatc.common.exception;

public class LoginException extends Exception {
	/**
     * 未知错误
     */
    public static final int UNKNOWN_ERR = -1;
    /**
     * 网络错误
     */
    public static final int NETWORK_ERR = 10;
    /**
     * 用户名密码不匹配
     */
    public static final int BAD_PWD = 11;
    /**
     * 数据库连接错误
     */
    public static final int DB_ERR = 12;
    /**
     * 功能未授权错误
     */
    public static final int AUTH_ERR = 13;           ///< 功能未授权
    public static final int BAD_IP_CONF = 14;        ///< 不匹配的客户端IP
    public static final int KICK_OUT = 15;           ///< 用户从另一处登录
    public static final int NOT_LOGON = 16;          ///< 用户未登录,或从另一处登录,或登录超时
    public static final int LOCK_FAIL = 17;          ///< 对象加锁失败
    public static final int ALREADY_LOGON = 18;      ///< 用户已登录
    public static final int DB_ERR_CREATE = 19;      ///< 数据库插入数据错误
    public static final int DB_ERR_READ = 20;        ///< 数据库查询数据错误
    public static final int DB_ERR_UPDATE = 21;      ///< 数据库修改数据错误
    public static final int DB_ERR_DELETE = 22;      ///< 数据库删除数据错误
    public static final int BAD_LOGIN_MODE = 23;     ///< 未知的登录方式 "C" "W"
    public static final int INVALID_USER = 24;       ///< 无效的用户
    public static final int CONSTRAINT_ERR = 25;     ///< 数据插入主键重复
    public static final int DEPEND_ERR = 26;         ///< 外键存在依赖
    public static final int INCOMPATIBLE_VER = 27;   ///< 不匹配的服务版本
    public static final int PASS_VALID_DATE = 38;    ///< 用户已过期
    public static final int MAX_PWD_ERROR = 51;      ///< 用户名或密码错误次数超过系统最大限制
    public static final int EXCEL_FILE_ERR = 30;     ///< 文件不存在或文件格式不对
    public static final int EXCEL_CONTENT_ERR = 31;  ///< 文件内容不符合要求
    public static final int EXCEL_VERSION_ERR = 32;  ///< 版本不一至
    public static final int NO_TASK_BY_EVERY = 33;  ///< 无对应的任务单
    public static final int TASK_BY_EVERY_USE = 34;  ///< 对应的任务单已被使用
    public static final int TASK_BY_EVERY_CANCLE = 35;  ///< 对应的任务单已被使用
    public static final int BY_DAYFP_SS = 36;  ///< 对应的场次计划已批准或不批准不可撤消
    public static final int BY_DAYFP_END = 37;  ///< 对应的架次计划已完成，不允许编披
    public static final int TASK_RUNNING_ERR  = 40;  ///< 定时任务正在运行或禁止运行	
    public static final int AIRLINE_CHECK_ERR = 45;  ///< 航线检查不符合要求
    public static final int RESEARCH_ID_ERR = 46;	  ///< 课题ID录入错误
    public static final int PLANE_TYPE_ERR  = 47;	  ///< 飞机类型检查错误
    public static final int USER_DEFINE = 99;        ///< 用户自定义
    
    ////////////////////////////////////////////////////////////////////////////////////////
    /// ningliyu add 2011-11-15
    /// 服务响应数据为空
    ///////////////////////////////////////////////////////////////////////////////////////
    
    public static final int RESPONSE_NULL = 80;        ///< 返回为空
    
    
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
