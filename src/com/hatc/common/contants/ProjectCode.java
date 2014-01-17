package com.hatc.common.contants;

/**
* 
* <b>system：</b>      协同办公平台<br/>
* <b>description：</b> 通用模块异常定义<br/>
* <b>author：</b>      王洋<br/>
* <b>copyright：</b>	　 北京华安天诚科技有限公司<br/>
* <b>version：</b>     VER1.00 2010-04-06<br/>
*
**/
public class ProjectCode {
	/* ------------------------------异常码:服务端错误------------------------------- */
	/** 未知错误 */
	public static final String SERVICE_UNKNOWN_ERR = "99999.-1";

	/** 网络错误 */
	public static final String SERVICE_NETWORK_ERR = "99999.10";

	/** 用户名或密码错误 */
	public static final String SERVICE_BAD_PWD = "99999.11";

	/** 数据库连接错误 */
	public static final String SERVICE_DB_ERR = "99999.12";

	/** 功能未授权错误 */
	public static final String SERVICE_AUTH_ERR = "99999.13";

	/** 不匹配的客户端IP */
	public static final String SERVICE_BAD_IP_CONF = "99999.14";

	/** 用户从另一处登录 */
	public static final String SERVICE_KICK_OUT = "99999.15";

	/** 用户未登录,或从另一处登录,或登录超时 */
	public static final String SERVICE_NOT_LOGON = "99999.16";

	/** 对象加锁失败 */
	public static final String SERVICE_LOCK_FAIL = "99999.17";

	/** 用户已登录 */
	public static final String SERVICE_ALREADY_LOGON = "99999.18";

	/** 错误的登录模式 */
	public static final String BAD_LOGIN_MODE = "99999.23";

	/** 无效用户 */
	public static final String SERVICE_INVALID_USER = "99999.24";
	
	/** 不配本的服务器版本 */
	public static final String INCOMPATIBLE_VER = "99999.27";
	
	/** 用户已失效 请重新登录 */
	public static final String USER_INVALID_RELOGON = "99999.30";

	/** 用户已过期 */
	public static final String USER_INVALID = "99999.38";
	
	/** 无效单位的用户 */
	public static final String SERVICE_INVALID_DEPARTMENT = "99999.39";

	/*--------------------------------异常码:WEB异常码-----------------------------*/
	/* 未知错误 */
	public static final String UNKNOWN_ERROR = "00000.00000";

	/* 处理类未找到 */
	public static final String CLASS_NOT_FOUND_ERROR = "00000.00001";

	/* 输入输出错误 */
	public static final String IO_ERROR = "00000.00002";

	/* 文件未找到 */
	public static final String FILE_NOT_FOUND_ERROR = "00000.00003";

	/* 用户登录 */
	/** 用户登录_用户名错误 */
	public static final String USER_LOGIN_NAME_ERROR = "00001.00001";

	/** 用户登录_密码错误 */
	public static final String USER_LOGIN_PASSWORD_ERROR = "00001.00002";

	/** 用户登录失败次数超过限定次数 */
	public static final String USER_LOGIN_NUMBER_ERROR = "00001.00003";

	/** 用户登录过程中出现错误 */
	public static final String USER_LOGIN_ERROR = "00001.00004";

	/** 用户角色切换出现错误 * */
	public static final String USER_SWICTH_ROLE_ERROR = "00001.00012";

	/** 切换到首页出现错误 * */
	public static final String SWICTH_FIRSTPAGE_ERROR = "00001.00013";

	/** 园区网登录出现错误 * */
	public static final String SSO_LOGIN_ERROR = "00001.00014";

	/** FireFox 浏览器只允许登录一个用户 * */
	public static final String NO_IE_LOGIN_ERROR = "00001.00015";

	/** 用户从客户端登录失败 * */
	public static final String CS_LOGIN_ERROR = "00001.00016";


	/** 首页 */
	/* 首页 - 修改密码 - 操作成功 */
	public static final String USER_PASSWORD_SUCCEED = "00000.00101";

	/* 首页 - 退出 - 操作成功 */
	public static final String USER_LOGOUT_SUCCEED = "00000.00201";

	/* 首页 - 修改密码 - 操作失败 */
	public static final String USER_PASSWORD_FAIL = "00000.10101";

	/* 首页 - 修改密码 - 初始化失败 */
	public static final String USER_INITPASSWORD_FAIL = "00000.10102";

	/* 首页 - 退出 - 操作失败 */
	public static final String USER_LOGOUT_FAIL = "00000.10201";

	/* 文件下载异常 */
	public static final String FILE_DOWN_ERROR = "00000.90001";

	
	/** 通知管理 */
	/* 新建通知消息 - 保存成功 */
	public static final String MSG_NEWSAVE_SUCCEED = "Z0000.00001";

	/* 新建通知消息 - 发送成功 */
	public static final String MSG_NEWINSERT_SUCCEED = "Z0000.00002";

	/* 待发通知消息 - 编辑待发 - 保存成功 */
	public static final String MSG_EDITSAVE_SUCCEED = "Z0000.00101";

	/* 待发通知消息 - 编辑待发 - 发送成功 */
	public static final String MSG_EDITUPDATE_SUCCEED = "Z0000.00102";

	/* 待发通知消息 - 删除成功 */
	public static final String MSG_DELETESAVE_SUCCEED = "Z0000.00103";

	/* 新建回复消息 - 发送成功 */
	public static final String MSG_REPLYINSERT_SUCCEED = "Z0000.00201";

	/* 已收通知消息 - 删除成功 */
	public static final String MSG_DELETEGOT_SUCCEED = "Z0000.00202";

	/* 已收通知消息 - 条件删除成功 */
	public static final String MSG_DELETEGOT_QUERY_SUCCEED = "Z0000.00203";

	/* 已收回复消息 - 删除成功 */
	public static final String MSG_DELETERE_SUCCEED = "Z0000.00204";

	/* 已收回复消息 - 条件删除成功 */
	public static final String MSG_DELETERE_QUERY_SUCCEED = "Z0000.00205";

	/* 已发通知消息 - 删除成功 */
	public static final String MSG_DELETESENT_SUCCEED = "Z0000.00301";

	/* 已发通知消息 - 条件删除成功 */
	public static final String MSG_DELETESENT_QUERY_SUCCEED = "Z0000.00302";

	/* 原始消息 - 删除成功 */
	public static final String MSG_DELETEORIG_SUCCEED = "Z0000.00401";

	/* 消息转发 - 保存成功 */
	public static final String MSG_TRANSSAVE_SUCCEED = "Z0000.00501";

	/* 消息转发 - 发送成功 */
	public static final String MSG_TRANSINSERT_SUCCEED = "Z0000.00502";

	/* 消息复制 - 保存成功 */
	public static final String MSG_COPYSAVE_SUCCEED = "Z0000.00503";

	/* 消息复制 - 发送成功 */
	public static final String MSG_COPYINSERT_SUCCEED = "Z0000.00504";

	/* 用户组设定 - 新建用户组成功 */
	public static final String MSGGROUP_INSERT_SUCCEED = "Z0000.00601";

	/* 用户组设定 - 编辑用户组成功 */
	public static final String MSGGROUP_UPDATE_SUCCEED = "Z0000.00602";

	/* 用户组设定 - 删除用户组成功 */
	public static final String MSGGROUP_DELETE_SUCCEED = "Z0000.00603";

	/* ****** ****** ****** ****** ****** ****** */
	
	/* 新建通知消息 - 保存失败 */
	public static final String MSG_NEWSAVE_FAIL = "Z0000.10001";

	/* 新建通知消息 - 发送失败 */
	public static final String MSG_NEWINSERT_FAIL = "Z0000.10002";

	/* 待发通知消息 - 编辑待发 - 保存失败 */
	public static final String MSG_EDITSAVE_FAIL = "Z0000.10101";

	/* 待发通知消息 - 编辑待发 - 发送失败 */
	public static final String MSG_EDITUPDATE_FAIL = "Z0000.10102";

	/* 待发通知消息 - 删除失败 */
	public static final String MSG_DELETESAVE_FAIL = "Z0000.10103";
	
	/* 待发通知消息 - 列表 - 查询失败 */
	public static final String MSG_BCKLIST_FAIL = "Z0000.10711";

	/* 待发通知消息 - 编辑待发 - 初始化失败 */
	public static final String MSG_INITEDITSAVE_FAIL = "Z0000.10712";
	
	/* 新建回复消息 - 发送失败 */
	public static final String MSG_REPLYINSERT_FAIL = "Z0000.10201";


	/* 原始消息 - 删除失败 */
	public static final String MSG_DELETEORIG_FAIL = "Z0000.10401";

	/* 消息转发 - 保存失败 */
	public static final String MSG_TRANSSAVE_FAIL = "Z0000.10501";

	/* 消息转发 - 发送失败 */
	public static final String MSG_TRANSINSERT_FAIL = "Z0000.10502";

	/* 消息复制 - 保存失败 */
	public static final String MSG_COPYSAVE_FAIL = "Z0000.10503";

	/* 消息复制 - 发送失败 */
	public static final String MSG_COPYINSERT_FAIL = "Z0000.10504";

	/* 用户组设定 - 新建用户组失败 */
	public static final String MSGGROUP_INSERT_FAIL = "Z0000.10601";

	/* 用户组设定 - 编辑用户组失败 */
	public static final String MSGGROUP_UPDATE_FAIL = "Z0000.10602";

	/* 用户组设定 - 删除用户组失败 */
	public static final String MSGGROUP_DELETE_FAIL = "Z0000.10603";

	/* 新建通知消息 - 初始化失败 */
	public static final String MSG_INITNEW_FAIL = "Z0000.10701";
	

	/* 已收通知消息 - 删除失败 */
	public static final String MSG_DELETEGOT_FAIL = "Z0000.10202";

	/* 已收通知消息 - 条件删除失败 */
	public static final String MSG_DELETEGOT_QUERY_FAIL = "Z0000.10203";

	/* 已收通知消息 - 列表 - 查询失败 */
	public static final String MSG_GOTLIST_FAIL = "Z0000.10721";

	/* 已收通知消息 - 查看初始化失败 */
	public static final String MSG_INITGOT_FAIL = "Z0000.10722";

	/* 已收通知消息 - 回复初始化失败 */
	public static final String MSG_INITREPLY_FAIL = "Z0000.10723";
	
	/* 已发通知消息 - 删除失败 */
	public static final String MSG_DELETESENT_FAIL = "Z0000.10301";

	/* 已发通知消息 - 条件删除失败 */
	public static final String MSG_DELETESENT_QUERY_FAIL = "Z0000.10302";
	

	/* 已收回复消息 - 列表 - 查询失败 */
	public static final String MSG_RELIST_FAIL = "Z0000.10724";

	/* 已收回复消息 - 查看初始化失败 */
	public static final String MSG_INITRE_FAIL = "Z0000.10725";
	
	/* 已收回复消息 - 删除失败 */
	public static final String MSG_DELETERE_FAIL = "Z0000.10204";

	/* 已收回复消息 - 条件删除失败 */
	public static final String MSG_DELETERE_QUERY_FAIL = "Z0000.10205";
	

	/* 已发通知消息 - 列表 - 查询失败 */
	public static final String MSG_SENTLIST_FAIL = "Z0000.10731";

	/* 已发通知消息 - 查看初始化失败 */
	public static final String MSG_INITSENT_FAIL = "Z0000.10732";
	

	/* 原始消息 - 查看初始化失败 */
	public static final String MSG_INITORIG_FAIL = "Z0000.10741";

	/* 消息转发 - 初始化失败 */
	public static final String MSG_INITTRANS_FAIL = "Z0000.10751";

	/* 消息复制 - 初始化失败 */
	public static final String MSG_INITCOPY_FAIL = "Z0000.10752";

	/* 用户组设定 - 初始化失败 */
	public static final String MSGGROUP_INIT_FAIL = "Z0000.10761";
	
	
	/*------------------------用户自定义报表----------------------*/
	/** 用户自定义报表保存成功 */
	public static final String USER_REPORT_SAVE_SUCCEED = "Z0001.00001";
	
	/** 用户自定义报表发布成功 */
	public static final String USER_REPORT_PUBLISH_SUCCEED = "Z0001.00003";
	
	/** 用户自定义报表保存失败 */
	public static final String USER_REPORT_SAVE_FAIL = "Z0001.10001";
	
	/** 用户自定义报表删除成功 */
	public static final String USER_REPORT_DELETE_SUCCEED = "Z0001.00002";
	
	/** 用户自定义报表删除失败 */
	public static final String USER_REPORT_DELETE_FAIL = "Z0001.10002";
	
	/** 用户自定义报表列表查询失败 */
	public static final String USER_REPORT_LIST_FAIL = "Z0001.10003";
	
	/** 用户自定义报表生成预览失败 */
	public static final String USER_REPORT_PREVIEW_FAIL = "Z0001.10004";
	
	/** 用户自定义报表向导初始化失败 */
	public static final String USER_REPORT_INIT_FAIL = "Z0001.10005";
	
	/*------------------------用户自定义报表----------------------*/
	
	/* 项目变更管理 - 新建更新成功 */
	public static final String UPDATE_SAVE_SUCCEED = "30000.00001";
	
	/* 项目变更管理 - 新建更新失败 */
	public static final String UPDATE_SAVE_ERROR = "30000.10001";
	
	/* 项目变更管理 - 查询更新失败 */
	public static final String UPDATE_SEARCH_ERROR = "30000.10002";
	
	/* 项目变更管理 - 查询更新列表失败 */
	public static final String UPDATE_SEARCHLIST_ERROR = "30000.10003";

	
	/* 流程审批管理 - 新建流程处理成功 */
	public static final String FLOW_SAVE_SUCCEED = "30000.00011";
	
	/* 流程审批管理 - 新建流程处理失败 */
	public static final String FLOW_SAVE_ERROR = "30000.10011";
	
	/* 项目流程审批管理 - 查询流程审批失败 */
	public static final String FLOW_SEARCH_ERROR = "30000.10012";
	
	/* 项目流程审批管理 - 查询流程审批列表失败 */
	public static final String FLOW_SEARCHLIST_ERROR = "30000.10013";

	
	/* 文档管理 - 新建文档成功 */
	public static final String DOCUMENT_SAVE_SUCCEED = "30000.00021";
	
	/* 文档管理 - 新建文档失败 */
	public static final String DOCUMENT_SAVE_ERROR = "30000.10021";
	
	/* 文档管理 - 查询文档失败 */
	public static final String DOCUMENT_SEARCH_ERROR = "30000.10022";
	
	/* 文档管理 - 查询文档列表失败 */
	public static final String DOCUMENT_SEARCHLIST_ERROR = "30000.10023";
	
	/* 文档管理 - 删除文档成功 */
	public static final String DOCUMENT_DELETE_SUCCEED = "30000.00024";
	
	/* 文档管理 - 删除文档失败 */
	public static final String DOCUMENT_DELETE_ERROR = "30000.10024";
	/* 文档管理 - 更新文档成功 */
	public static final String DOCUMENT_UPDATE_SUCCEED = "30000.00025";

	
}
