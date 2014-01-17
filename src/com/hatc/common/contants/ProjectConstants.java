package com.hatc.common.contants;

/**
* 
* <b>system：</b>      协同办公平台<br/>
* <b>description：</b> 通用模块常量定义<br/>
* <b>author：</b>      王洋<br/>
* <b>copyright：</b>	　 北京华安天诚科技有限公司<br/>
* <b>version：</b>     VER1.00 2010-04-06<br/>
* <b>version：</b>     VER1.00 2011-12-10<br/>
*        修改了: ProjectConstants.FRAME_WINDOW 值为:noButton.  作用是不显示返回按钮.
	     如:
	     processException(map, value, ex, ProjectConstants.FRAME_WINDOW, TtimsCode.AIRSTATE_SELECT_ERROR);	
**/
public class ProjectConstants {

	/**
	 * 信息页面
	 */
	public static final String SYSTEM_RESULT = "result";

	/**
	 * 登录类型(Web登录)
	 */
	public static final String WEB_LOGIN_TYPE = "W";

	/**
	 * 单点登录类型(Web登录)
	 */
	public static final String SSO_LOGIN_TYPE = "SS0";

	/**
	 * 登录人员信息保存在Session中的标识
	 */
	public static final String SESSION_REQIDENTITY = "session_reqidentity";

	/**
	 * 登录人员当前角色保存在Session中的标识
	 */
	public static final String SESSION_USER_ROLE = "session_user_role";

	/**
	 * 浏览器类型保存在Session中的标识
	 */
	public static final String SESSION_WINDOW_VER = "session_window_ver";

	/**
	 * 登录人员当前角色的权限注留Session中的标识
	 */
	public static final String SESSION_USER_ROLE_FUNCTION = "session_user_role_function";
	public static final String SESSION_USER_ROLE_FUNCTIONLIST = "session_user_role_functionlist";

	
	/**
	 * 登录人员当前角色列表保存在Session中的标识
	 */
	public static final String SESSION_USER_ROLE_LIST = "session_user_role_list";
	
	 
	/**
	 * 登录人员信息保存在Session中的标识
	 */
	public static final String SESSION_USER = "session_user";

	/**
	 * 地点信息保存在Session中的标识
	 */
	public static final String SESSION_LOCAL = "session_local";

	/**
	 * 登录人员部门在Session中的标识
	 */
	public static final String SESSION_USER_DEPT = "session_user_dept";

	/**
	 * 用户声音设置在Session中的标识
	 */
	public static final String SESSION_SOUND = "session_sound";

	/**
	 * 弹出页面标识
	 */
	public static final String POP_UP_WINDOW = "close";

	/**
	 * 框架中页面标识
	 * TODO ningliyu 2011-12-10
	 */	
	public static final String FRAME_WINDOW = "noButton";
	//public static final String FRAME_WINDOW = "null";

	/*---------------------------系统参数---------------------------------*/
	/**
	 * 名称：MODI_PWD_INTERVAL         类型：I  单位：天  内容：强制修改密码时间间隔
	 */
	public static final String MODI_PWD_INTERVAL = "MODI_PWD_INTERVAL";
	
	/**
	 * 名称：PASSWORD_LENGTH           类型：I  单位：位  内容：密码长度
	 */
	public static final String PASSWORD_LENGTH = "PASSWORD_LENGTH";
	
	/**
	 * 名称：名称：PWD_SECRET_LEVEL     类型：O  单位：级  内容：密码强度等级（1-5）
	 */
	public static final String PWD_SECRET_LEVEL = "PWD_SECRET_LEVEL";
	
	/**
	 * 名称：RELOGON_TIMES             类型：I  单位：次  内容：允许最大登陆失败次数
	 */
	public static final String RELOGON_TIMES = "RELOGON_TIMES";
	
	/*--------------------------权限验证--------------------------------*/
	/**
	 * 名称：LOG_FUNCTION_ID         类型：S  内容：记录日志的功能ID
	 */
	public static final String LOG_FUNCTION_ID = "LOG_FUNCTION_ID";
	
	/**
	 * 名称：LOG_TEXT         类型：S  内容：日志备注内容
	 */
	public static final String LOG_TEXT = "LOG_TEXT";
	
	/**
	 * 名称：OPERATE_FUNCTION        类型：S  内容：操作类型
	 */
	public static final String OPERATE_FUNCTION = "OPERATE_FUNCTION";
	
	/**
	 * 名称：OPERATE_QUERY        类型：S  内容：查询操作
	 */
	public static final String OPERATE_QUERY = "query";
	
	/**
	 * 名称：OPERATE_NEW        类型：S  内容：新建操作
	 */
	public static final String OPERATE_NEW = "new";
	
	/**
	 * 名称：OPERATE_EDIT        类型：S  内容：编辑操作
	 */
	public static final String OPERATE_EDIT = "edit";
	
	/**
	 * 名称：OPERATE_DELETE        类型：S  内容：删除操作
	 */
	public static final String OPERATE_DELETE = "delete";
	
	/**
	 * 名称：OPERATE_SAVE        类型：S  内容：保存操作
	 */
	public static final String OPERATE_SAVE = "save";
	
	/**
     * 名称：OPERATE_COPY        类型：S  内容：复制操作
     */
    public static final String OPERATE_COPY = "copy";
    
    /**
     * 名称：SESSION_USER_UI_CODE        类型：S  内容：1 服务站  2 飞行员  3 空军
     */
    public static final String SESSION_USER_UI_CODE = "sessionUserUICode";
    
}
