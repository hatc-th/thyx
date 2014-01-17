package com.hatc.base.common;

/**
* 
* <b>system：</b>      协同办公平台<br/>
* <b>description：</b> Hiberante异常常量定义<br/>
* <b>author：</b>      王洋<br/>
* <b>copyright：</b>	　 北京华安天诚科技有限公司<br/>
* <b>version：</b>     VER1.00 2010-04-06<br/>
* 
*<b>version：</b>     VER1.00 2011-12-15<br/>
*   ningliyu 此类以后不再使用.暂保留.
* 
**/
public class ExceptionRescoure
{

	/** 登陆错误 */
	public static final String LOGIN_EXCEPTION = "登陆错误";

	/** 查询数据错误 */
	public static final String HIBERNATE_QUERY_EXCEPTION = "查询数据错误";

	/** 保存数据错误 */
	public static final String HIBERNATE_SAVE_EXCEPTION = "保存数据错误";

	/** 更新数据错误 */
	public static final String HIBERNATE_UPDATE_EXCEPTION = "更新数据错误";

	/** 删除数据错误 */
	public static final String HIBERNATE_DELETE_EXCEPTION = "删除数据错误";

	/** 批量保存数据错误 */
	public static final String HIBERNATE_BATCH_SAVE_EXCEPTION = "批量保存数据错误";

	/** 批量更新数据错误 */
	public static final String HIBERNATE_BATCH_UPDATE_EXCEPTION = "批量更新数据错误";

	/** 批量删除数据错误 */
	public static final String HIBERNATE_BATCH_DELETE_EXCEPTION = "批量删除数据错误";

	/** 查询的数据不存在 */
	public static final String HIBERNATE_NOT_FIND = "查询的数据不存在";

	/** 分页查询错误 */
	public static final String HIBERNATE_QUERY_ROLLPAGE_EXCEPTION = "settingRollPage分页查询错误(SQL)！";

	/** 查询序列错误 */
	public static final String HIBERNATE_QUERY_SEQ = "查询序列错误";
	
	/** 执行存储过程错误 */
    public static final String HIBERNATE_PROCEDURES_EXCEPTION = "执行存储过程错误";
    
    /** 执行sql语句错误 */
    public static final String HIBERNATE_SQL_EXCEPTION = "执行存储过程错误";

}
