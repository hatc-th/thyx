package com.hatc.base.contants;


public class ExceptionCode {

	/* ------------------------------异常码:服务端错误------------------------------- */
	/** 未知错误 */
	public static final String SERVICE_UNKNOWN_ERR = "00000.00000";

	/** 网络错误 */
	public static final String SERVICE_NETWORK_ERR = "00000.00010";

	/** 数据库连接错误 */
	public static final String SERVICE_DB_ERR = "00000.00012";

	/** 功能未授权错误 */
	public static final String SERVICE_AUTH_ERR = "00000.00013";

	/** 不匹配的客户端IP */
	public static final String SERVICE_BAD_IP_CONF = "00000.00014";

	/** 用户从另一处登录 */
	public static final String SERVICE_KICK_OUT = "00000.00015";

	/** 用户未登录,或从另一处登录,或登录超时 */
	public static final String SERVICE_NOT_LOGON = "00000.00016";

	/** 对象加锁失败 */
	public static final String SERVICE_LOCK_FAIL = "00000.00017";

	/** 用户已登录 */
	public static final String SERVICE_ALREADY_LOGON = "00000.00018";

	/** 错误误的登录模式 */
	public static final String BAD_LOGIN_MODE = "00000.00023";

	/** 无效用户 */
	public static final String SERVICE_INVALID_USER = "00000.00024";

	/** 不配本的服务器版本 */
	public static final String INCOMPATIBLE_VER = "00000.00027";

	/*--------------------------------异常码:WEB异常码-----------------------------*/
	/* 未知错误 */
	public static final String WEB_UNKNOWN_ERR = "00001.00000";

	/* 用户登录 */
	/** 用户登录_用户名或密码错误 */
	public static final String USER_LOGIN_U_P_ERROR = "00001.00001";

	/** 用户登录_查询用户出错误 */
	public static final String USER_LOGIN_QUERY_ERROR = "00001.00002";

	/** 用户登录失败次数超过限定次数 */
	public static final String USER_LOGIN_COUNT_ERROR = "00001.00003";

	/** 用户登录过程中出现错误 */
	public static final String USER_LOGIN_ERROR = "00001.00004";

	/** 首页 - 修改密码初始化异常 * */
	public static final String USER_INITPWD_FAIL = "00001.00010";

	/** 首页 - 修改密码失败 * */
	public static final String USER_EDITPWD_FAIL = "00001.00011";

	/** 首页 - 退出系统失败 */
	public static final String SYS_LOGOUT_FAIL = "00001.00099";

	/** 用户角色切换出现错误 * */
	public static final String USER_SWICTH_ROLE_ERROR = "00001.00012";

	/** 切换到首页出现错误 * */
	public static final String SWICTH_FIRSTPAGE_ERROR = "00001.00013";

	/** 园区网登录出现错误 * */
	public static final String SSO_LOGIN_ERROR = "00001.00014";
	
	/** FireFox 浏览器只允许登录一个用户 * */
	public static final String NO_IE_LOGIN_ERROR = "00001.00015";
	
	/**  要打印的计划不存在 */
	public static final String PRINT_FP_FILE_ERROR = "00001.00100";
	
	/**  打印场次计划出错 */
	public static final String PRINT_FP_ERROR = "00001.00101";

	/* 计划管理 - 年度计划 */
	/** 计划管理 - 年度计划 - 编辑年度计划列表失败 */
	public static final String YEARPROJECT_EDITLIST_FAIL = "10100.00001";
	
	/** 计划管理 - 年度计划 - 查询年度计划列表失败 */
	public static final String YEARPROJECT_GETLIST_FAIL = "10100.00002";
	
	/** 计划管理 - 年度计划 - 编辑/新建年度计划失败 */
	public static final String YEARPROJECT_SET_FAIL = "10100.00003";

	/* 计划管理 - 科研试飞月计划 */
	/** 科研试飞月计划编辑 - 删除科研试飞月计划失败 */
	
	/* 计划管理.日场次计划 */
	/** 查询日场次计划列表信息异常 */
	public static final String DAY_PLAN_LIST_QUERY_ERROR = "10500.00001";

	/** 查询日场次计划信息异常 */
	public static final String DAY_PLAN_INFO_QUERY_ERROR = "10500.00002";

	/** 生成日场次计划图形信息异常 */
	public static final String DAY_PLAN_REPORT_ERROR = "10500.00003";

	/** 查询架次计划信息异常 */
	public static final String QUERY_EVERY_PLAN_ERROR = "10500.00004";

	/* 资源管理.飞机资源.飞机资源管理 */
	/** 查询飞机列表信息异常 */
	public static final String PLANE_LIST_QUERY_ERROR = "30101.00001";

	/** 查询飞机信息异常 */
	public static final String PLANE_INFO_QUERY_ERROR = "30101.00002";

	/** 保存飞机信息异常 */
	public static final String PLANE_SAVE_ERROR = "30101.00003";

	/** 更新飞机信息异常 */
	public static final String PLANE_UPDATE_ERROR = "30101.00004";

	/** 删除飞机信息异常 */
	public static final String PLANE_DELETE_ERROR = "30101.00005";

	/** 新建飞机信息初始化异常 */
	public static final String PLANE_CREATE_INIT_ERROR = "30101.00006";

	/** 飞机号重复异常 */
	public static final String PLANE_CODE_ERROR = "30101.00007";

	/** 删除飞机信息异常 -- 飞机已经被使用 */
	public static final String PLANE_DELETE_Fk_ERROR = "30101.00008";

	/* 资源管理.飞机资源.飞机相关资源查询 */
	/** 查询飞机有寿件信息异常 */
	public static final String PLANE_PART_QUERY_ERROR = "30102.00001";

	/** 查询飞机故障信息异常 */
	public static final String PLANE_EFFECT_QUERY_ERROR = "30102.00002";

	/* 资源管理 - 测试设备 - 机载设备及频点管理 */
	/** 机载设备及频点管理 - 查询列表异常 * */
	public static final String AeroEquipList_QRY_FAIL = "30201.00001";

	/** 机载设备及频点管理 - 查询详情异常 * */
	public static final String AeroEquipInfo_QRY_FAIL = "30201.00002";

	/** 机载设备及频点管理 - 更新详情异常 * */
	public static final String AeroEquipInfo_SET_FAIL = "30201.00003";

	/* 资源管理 - 人力资源 */
	/** 人力资源 - 飞行人员名单导入 - 初始化异常 * */
	public static final String PILOT_UPLOAD_FAIL_INIT = "30401.00001";

	/** 人力资源 - 飞行人员名单导入 - 导入异常 * */
	public static final String PILOT_UPLOAD_FAIL = "30401.00002";

	/** 人力资源 - 飞行人员查询列表异常 * */
	public static final String PILOTLIST_QRY_FAIL = "30402.00001";

	/** 人力资源 - 飞行人员查询详情异常 * */
	public static final String PILOTINFO_QRY_FAIL = "30402.00002";

	/** 人力资源 - 飞行人员删除异常 * */
	public static final String PILOTLIST_DEL_FAIL = "30402.00003";

	/** 人力资源 - 飞行人员新建 - 初始化异常 * */
	public static final String PILOT_SET_FAIL_INIT = "30402.00004";

	/** 人力资源 - 飞行人员新建异常 * */
	public static final String PILOT_SET_FAIL = "30402.00005";

	/** 人力资源 - 飞行人员编辑保存异常 * */
	public static final String PILOT_EDIT_FAIL = "30402.00006";

	/** 人力资源 - 飞行人员删除异常 - 所选飞行人员已被使用，不允许删除！ */
	public static final String PILOTLIST_DEL_FAIL_XML = "30402.00007";

	/** 人力资源 - 飞行人员新建异常 - 飞行人员姓名重复 * */
	public static final String PILOT_SET_FAIL_UC = "30402.00008";
	
	/** 人力资源 - 飞行人员新建异常 - 飞行人员能力更新 * */
	public static final String PILOT_UPDATE_ABILITY_FAIL = "30402.00009";

	/* 质量管理(问题报告) */
	/** 查询问题报告列表异常 */
	public static final String DEFECT_LIST_QUERY_ERROR = "40100.00001";

	/** 查询问题报告信息异常 */
	public static final String DEFECT_INFO_QUERY_ERROR = "40100.00002";

	/** 保存问题报告异常 */
	public static final String DEFECT_INFO_SAVE_ERROR = "40100.00003";

	/** 更新问题报告异常 */
	public static final String DEFECT_INFO_UPDATE_ERROR = "40100.00004";

	/** 提交问题报告异常 */
	public static final String DEFECT_INFO_SUBMIT_ERROR = "40100.00005";

	/** 保存处理问题报告异常 */
	public static final String DEFECT_INFO_DISPOSE_ERROR = "40100.00006";

	/** 关闭问题报告异常 */
	public static final String DEFECT_INFO_DONE_ERROR = "40100.00007";

	/** 跳传至新建问题报告异常 */
	public static final String FROWARD_NEW_DEFECT_ERROR = "40100.00008";

	/** 跳传至更新问题报告异常 */
	public static final String FROWARD_UPDATE_DEFECT_ERROR = "40100.00009";

	/** 跳传至处理保存问题报告异常 */
	public static final String FROWARD_DISPOSE_DEFECT_ERROR = "40100.00010";

	/** 问题报告被锁定 */
	public static final String FROWARD_DISPOSE_DEFECT_LOCK = "40100.00011";

	/** 删除问题报告 */
	public static final String DEFECT_INFO_DELETE_ERROR = "40100.00012";

	/* 信息维护 */
	/** 信息维护 - 科研项目课题期初进度列表查询异常 * */
	public static final String RESEARCH_QRY_FAIL_LIST = "60300.00001";

	/** 信息维护 - 科研项目课题期初进度详情查询异常 * */
	public static final String RESEARCH_QRY_FAIL_INFO = "60300.00002";

	/** 信息维护 - 科研项目课题期初进度更新异常 * */
	public static final String RESEARCH_SET_FAIL = "60300.00003";
	
	/** 信息维护 - 指挥引导系统信息导入异常 */
	public static final String INFO_UPLOADDOC_FAIL = "60500.00001";

	/* 通知管理 */
	/** 未知异常 */
	public static final String UNKNOW_EX = "70100.00000";

	/** 新建消息 - 初始化异常 */
	public static final String MSG_SET_INIT_FAIL = "70100.00001";

	/** 通知消息 - 通知消息发送异常 */
	public static final String MSG_INSERT_FAIL = "70100.00002";

	/** 飞行通知 - 飞行通知发送异常 */
	public static final String MSG_INSERT_FAIL_FLY = "70100.00003";

	/** 航管批复 - 航管批复下达异常 */
	public static final String MSG_INSERT_FAIL_EX = "70100.00004";

	/** 通知消息 - 通知消息保存异常 */
	public static final String MSG_BCKUP_FAIL = "70100.00005";

	/** 飞行通知 - 飞行通知保存异常 */
	public static final String MSG_BCKUP_FAIL_FLY = "70100.00006";

	/** 航管批复 - 航管批复保存异常 */
	public static final String MSG_BCKUP_FAIL_EX = "70100.00007";

	/** 查询列表异常 */
	public static final String MSG_GETLIST_FAIL = "70100.00010";

	/** 查询详情异常 */
	public static final String MSG_GETINFO_FAIL = "70100.00011";

	/** 删除消息异常 */
	public static final String MSG_DEL_FAIL = "70100.00012";

	/** 待发通知消息 - 编辑待发消息异常 */
	public static final String MSG_UPDATE_FAIL = "70100.00013";

	/** 回复消息 - 初始化异常 */
	public static final String MSG_REMSG_INIT_FAIL = "70100.00020";

	/** 回复消息 - 回复异常 */
	public static final String MSG_INSERT_FAIL_RE = "70100.00021";

	/** 用户组设定 - 初始化异常 */
	public static final String MSG_GROUP_INIT_FAIL = "70500.00001";

	/** 用户组设定 - 新建用户组异常 */
	public static final String MSG_GROUP_INSERT_FAIL = "70500.00002";

	/** 用户组设定 - 删除用户组异常 */
	public static final String MSG_GROUP_DELETE_FAIL = "70500.00003";

	/** 用户组设定 - 更新用户组异常 */
	public static final String MSG_GROUP_UPDATE_FAIL = "70500.00004";

	/* 计划管理.计划编批 */
	/** 查询编批列表信息异常 */
	public static final String POST_EDIT_LIST_QUERY_ERROR = "10700.00001";

	/** 保存计划信息异常 */
	public static final String POST_PLAN_SAVE_ERROR = "10700.00002";

	/** 报批计划信息异常 */
	public static final String POST_PLAN_SUBMIT_ERROR = "10700.00003";

	/** 查询编批列表信息异常 */
	public static final String POST_PLAN_LIST_QUERY_ERROR = "10700.00004";

	/** 记录完成后的计划不能报批 */
	public static final String POST_PLAN_ERROR = "10700.00005";

	/* 试飞任务单 */
	/** 取消试飞任务单异常 */
	public static final String TASK_ARILINE_ERROR = "10400.00000";
	/** 取消试飞任务单异常 */
	public static final String TASK_CANCEL_ERROR = "10400.00001";

	/** 确认试飞任务单异常 */
	public static final String TASK_CONFIRM_ERROR = "10400.00002";

	/** 创建试飞任务单异常 */
	public static final String TASK_CREATE_ERROR = "10400.00003";

	/** 删除试飞任务单文档异常 */
	public static final String TASK_DELDOC_ERROR = "10400.00004";

	/** 删除试飞任务单异常 */
	public static final String TASK_DELTASK_ERROR = "10400.00005";

	/** 初始化上载文档异常 */
	public static final String TASK_INIT_UPLOADDOC_ERROR = "10400.00008";

	/** 修改试飞任务单异常 */
	public static final String TASK_MODIFY_ERROR = "10400.00009";

	/** 查询试飞任务单异常 */
	public static final String TASK_QUERY_ERROR = "10400.00010";

	/** 初始化修改试飞任务单异常 */
	public static final String TASK_INIT_MODIFY_ERROR = "10400.00011";

	/** 查询试飞任务单消息内容异常 */
	public static final String TASK_SEARCH_DETAIL_ERROR = "10400.00012";

	/** 上传试飞任务单相关文档异常 */
	public static final String TASK_UPLOAD_DOC_ERROR = "10400.00013";

	/** 上传试飞任务单相关文档大于5M */
	public static final String TASK_UPLOAD_DOC_2BIG = "10400.00014";

	/** 增加上机试飞工程师登记查询列表 */
	public static final String TASK_ENGINEER_LIST = "10400.00015";

	/** 上机试飞工程师登记 */
	public static final String TASK_ENGINEER_QUERY = "10400.00016";

	/** 上机试飞工程师登记 */
	public static final String TASK_ENGINEER_CREATE = "10400.00017";

	/* 运行控制.日场次计划实施 */
	/** 查询日场次实施计划列表信息异常 */
	public static final String ACT_DAY_PLAN_LIST_QUERY_ERROR = "20100.00001";

	/** 查询日场次实施计划信息异常 */
	public static final String ACT_DAY_PLAN_INFO_QUERY_ERROR = "20100.00002";

	/* 运行控制.科研课题有效性 */
	/** 查询科研课题有效性列表 */
	public static final String TASK_VALIDITY_LIST_QUERY_ERROR = "20300.00001";

	/** 科研有效性(有效) */
	public static final String TASK_VALIDITY_YES_ERROR = "20300.00002";

	/** 科研有效性(无效) */
	public static final String TASK_VALIDITY_NO_ERROR = "20300.00003";

	/* 自然照明时刻表 */
	/** 查询自然照明时刻表文档异常 */
	public static final String DNTIME_SEARCH_ERROR = "60600.00001";

	/** 上载自然照明时刻表文档异常 */
	public static final String DNTIME_UPLOAD_ERROR = "60600.00002";

	/* 弹出选择框 */
	/** 信息查询异常 */
	public static final String INFO_QUERY_ERROR = "00011.00099";

	/** 查询部门异常 */
	public static final String DEPARTMENT_QUERY_ERROR = "00011.00001";

	/** 查询项目/科题异常 */
	public static final String RESEARCH_QUERY_ERROR = "00011.00002";

	/** 查询飞行单位异常 */
	public static final String FUNIT_QUERY_ERROR = "00011.00002";

	/* 科研项目课题 */
	/** 获取科研项目课题列表异常 */
	public static final String RESEARCH_LIST_SEARCH_ERROR = "62000.00001";

	/** 删除科研项目课题异常 */
	public static final String RESEARCH_DEL_ERROR = "62000.00002";

	/** 删除科研项目课题文档异常 */
	public static final String RESEARCH_DOC_DEL_ERROR = "62000.00003";

	/** 初始化科目完成情况页面异常 */
	public static final String RESEARCH_EDIT_INIT_ERROR = "62000.00004";

	/** 初始化新建科研科目页面异常 */
	public static final String RESEARCH_CREATE_INIT_ERROR = "62000.00005";

	/** 初始化修改科研科目页面异常 */
	public static final String RESEARCH_MODIFY_INIT_ERROR = "62000.00006";

	/** 上载科研科目文档初始化异常 */
	public static final String RESEARCH_DOC_UPLOAD_INIT_ERROR = "62000.00007";

	/** 科研科目文档列表初始化异常 */
	public static final String RESEARCH_DOC_LIST_INIT_ERROR = "62000.00008";

	/** 查询科研科目详细内容异常 */
	public static final String RESEARCH_SEARCH_DETAIL_ERROR = "62000.00009";

	/** 修改科研科目异常 */
	public static final String RESEARCH_MODIFY_ERROR = "62000.000010";

	/** 上载科研科目文档异常 */
	public static final String RESEARCH_DOC_UPLOAD_ERROR = "62000.000011";

	/** 维护科研项目课题列表异常 */
	public static final String RESEARCH_EDIT_LIST_ERROR = "62000.000012";

	/** 创建科研项目课题异常 */
	public static final String RESEARCH_CREATE_ERROR = "62000.000013";

	/** 修改科研科目状态异常 */
	public static final String RESEARCH_MODIFY_STATE_ERROR = "62000.000014";

	/* 报表异常 */
	/** 初始化查询条件异常 */
	public static final String REPORT_PARAM_ERROR = "53000.00001";

	public static final String REPORT_BUILD_ERROR = "53000.00002";

	/* 气象预报 */
	/** 查询气象预报草稿列表异常 */
	public static final String WEATHER_QUERY_DRAFT_ERROR = "35000.00001";

	/** 构建新气象预报条件列表异常 */
	public static final String WEATHER_CREATE_INIT_ERROR = "35000.00002";

	/* 统计分析 */
	/** 查询阶段飞行统计表异常 */
	public static final String PERIOD_REPORT_ERROR = "51200.00001";

	/* 天气预报 */
	/* 查询编辑列表出错(草稿) */
	public static final String WEATHER_QUERY_EDIT_LIST = "35100.00001";

	/* 进入新建页面出错 */
	public static final String WEATHER_CREATE_ERROR = "35100.00002";

	/* 进入编辑页面出错 */
	public static final String WEATHER_CREATE_UPDATE_ERROR = "35100.00003";

	/* 保存气象预报出错 */
	public static final String WEATHER_SAVE_ERROR = "35100.00004";

	/* 修改气象预报出错 */
	public static final String WEATHER_UPDATE_ERROR = "35100.00005";

	/* 提交气象预报出错 */
	public static final String WEATHER_SUBMIT_ERROR = "35100.00006";

	/* 删除气象预报出错 */
	public static final String WEATHER_DELETE_ERROR = "35100.00007";

	/* 查询列表出错 */
	public static final String WEATHER_QUERY_LIST_ERROR = "35100.00008";

	/* 查询气象信息详细信息出错 */
	public static final String WEATHER_QUERY_INFO_ERROR = "35100.00009";

	/* 查询云状出错 */
	public static final String QUERY_CLOUD_SHPAE_ERROR = "35100.00099";

	/* 转场飞行计划 */
	/** 运输机转场飞行计划创建异常 */
	public static final String TRANSFER_FARGO_CREATE_ERROR = "16000.00001";

	/** 战斗机转场飞行计划创建异常 */
	public static final String TRANSFER_FIGHT_CREATE_ERROR = "16000.00002";

	/** 运输机转场飞行计划创建异常 */
	public static final String TRANSFER_FARGO_MODIFY_ERROR = "16000.00003";

	/** 战斗机转场飞行计划创建异常 */
	public static final String TRANSFER_FIGHT_MODIFY_ERROR = "16000.00004";

	/** 查询转场飞行计划创建异常 */
	public static final String TRANSFER_FIGHT_QUERY_ERROR = "16000.00005";

	/** 删除转场飞行计划创建异常 */
	public static final String TRANSFER_PLAN_DELETE_ERROR = "16000.00006";

	/** 运输机转场飞行计划创建异常 */
	public static final String TRANSFER_FARGO_HANDLE_ERROR = "16000.00007";

	/** 战斗机转场飞行计划创建异常 */
	public static final String TRANSFER_FIGHT_HANDLE_ERROR = "16000.00008";

	/** 战斗机转场飞行计划创建异常 */
	public static final String TRANSFER_IMPL_REPORT_ERROR = "16000.00009";
	
	

	/* 文件上载 */
	/** 文件大小过大 */
	public static final String FILE_SIZE_ERROR = "00001.00070";

	/** 文件类型错误 */
	public static final String FILE_EXT_ERROR = "00001.00071";

	/** 文件目录不存在 */
	public static final String FILE_DISP_ERROR = "00001.00072";

	/** 文件不存在 */
	public static final String FILE_NULL_ERROR = "00001.00073";
	
	/** 文件上载失败 */
	public static final String FILE_UPLOAD_ERROR = "00001.00074";
	
	/** 文件导入失败 */
	public static final String FILE_IMPORT_ERROR = "00001.00075";
	
	/* 型号系列 */
	/** 型号系列删除异常 */
	public static final String SERIES_DELETE_ERROR = "60100.00001";

	/** 型号系列创建异常 */
	public static final String SERIES_CREATE_ERROR = "60100.00002";

	/** 型号系列修改异常 */
	public static final String SERIES_MODIFY_ERROR = "60100.00003";
	
	/* 航管文档 */
	/** 航管文档查询异常 */
	public static final String HGDOC_SEARCH_ERROR = "60700.00001";
	/** 航管文档上载异常 */
	public static final String HGDOC_UPLOAD_ERROR = "60700.00002";
	/** 航管文档删除异常 */
	public static final String HGDOC_DELETE_ERROR = "60700.00003";
	/** 航管文档修改异常 */
	public static final String HGDOC_MODIFY_ERROR = "60700.00004";
	
	/* 地面测试设备&保障资源 */
	/** 地面测试设备查询异常 */
	public static final String GRESOURCE_QUERY_ERROR = "30300.00001";
	/** 保障资源查询异常 */
	public static final String RESOURCE_QUERY_ERROR = "30300.00002";
	
	/** 地面测试设备管理异常 */
	public static final String GRESOURCE_SEARCH_ERROR = "30300.00003";
	/** 保障资源管理异常 */
	public static final String RESOURCE_SEARCH_ERROR = "30300.00004";
	
	/** 地面测试设备创建异常 */
	public static final String RESOURCE_CREATE_ERROR = "30300.00005";
	/** 保障资源创建异常 */
	public static final String GRESOURCE_CREATE_ERROR = "30300.00006";
	
	/** 地面测试设备删除异常 */
	public static final String GRESOURCE_DELETE_ERROR = "30300.00007";
	/** 保障资源删除异常 */
	public static final String RESOURCE_DELETE_ERROR = "30300.00008";
	
	/** 地面测试设备修改异常 */
	public static final String GRESOURCE_MODIFY_ERROR = "30300.00009";
	/** 保障资源修改异常 */
	public static final String RESOURCE_MODIFY_ERROR = "30300.00010";
	
	/** 地面测试设备创建初始化异常 */
	public static final String GRESOURCE_INITCREATE_ERROR = "30300.00011";
	/** 保障资源创建初始化异常 */
	public static final String RESOURCE_INITCREATE_ERROR = "30300.00012";
	
	/** 地面测试设备修改初始化异常 */
	public static final String GRESOURCE_INITMODIFY_ERROR = "30300.00013";
	/** 保障资源修改初始化异常 */
	public static final String RESOURCE_INITMODIFY_ERROR = "30300.00014";
	//该用户没有角色授权!
	public static final String USER_ROLE_ERROR = "99999.28";
	

	
}
