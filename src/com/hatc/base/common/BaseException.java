package com.hatc.base.common;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* 
* <b>system：</b>      协同办公平台<br/>
* <b>description：</b> 系统自定义异常类<br/>
* <b>author：</b>      王洋<br/>
* <b>copyright：</b>	　 北京华安天诚科技有限公司<br/>
* <b>version：</b>     VER1.00 2010-04-06<br/>
*                      支持原始消息: 用于记录到日志文件
* 
*
**/
public class BaseException extends Exception {
	private static final long serialVersionUID = 5404766056023341965L;
	
	private boolean serviceExceptionFlag = false; 

	/** 异常代码 */
	protected String exceptionCode;

	/** 返回参数 */
	protected Map<String, Object> paramMap = new HashMap<String, Object>();

	/** 异常级联(异常链) */
	protected Throwable rootCause = null;

	/** 异常集合（多元化）*/
	private List<BaseException> exceptions = new ArrayList<BaseException>();

	/** 消息key */
	private String messageKey = null;

	/** 消息 */
	private String message = null;
	
	/** 原始消息: 用于记录到日志文件 */
	private String messageOfSystem = null;

	/** 复合式消息 */
	private Object[] messageArgs = null;
	
	/** 消息 */
	private String reason = null;

	/**
	 * 构造函数
	 */
	public BaseException() {
		super();
	}

	/**
	 * 构造函数
	 * 
	 * @param exceptionCode
	 *            错误码
	 */
	public BaseException(String exceptionCode) {
		this.exceptionCode = exceptionCode;
	}
	
	/*
	 * 构造函数
	 * 
	 * @param exceptionCode
	 *            错误码
	 
	public BaseException(FomsException fex) {
		this.exceptionCode = String.valueOf(fex.getFaultcode());
		this.message = fex.getMessage();
	}
*/
	/**
	 * 构造函数
	 * 
	 * @param key
	 *            消息Key
	 * @param info
	 *            消息集
	 */
	public BaseException(String[] info) {
		this.messageArgs = info;
	}

	/**
	 * 构造函数
	 * 
	 * @param rootCause
	 *            异常级联
	 */
	public BaseException(Throwable rootCause) {
		this.rootCause = rootCause;
	}

	/**
	 * 取出异常集合
	 * 
	 * @return List
	 */
	public List getExceptions() {
		return exceptions;
	}

	/**
	 * 添加异常
	 * 
	 * @param baseException
	 *            异常
	 */
	public void setExceptions(BaseException baseException) {
		exceptions.add(baseException);
	}

	/**
	 * 取得消息集
	 * 
	 * @return
	 */
	public Object[] getMessageArgs() {
		return messageArgs;
	}

	/**
	 * 设置消息集
	 * 
	 * @param messageArgs
	 *            消息集
	 */
	public void setMessageArgs(Object[] messageArgs) {
		this.messageArgs = messageArgs;
	}

	/**
	 * 取得消息Key
	 * 
	 * @return
	 */
	public String getMessageKey() {
		return messageKey;
	}

	/**
	 * 设置消息Key
	 * 
	 * @param messageKey
	 *            消息Key
	 * @return
	 */
	public void setMessageKey(String messageKey) {
		this.messageKey = messageKey;
	}

	/**
	 * 取得异常级联
	 * 
	 * @return
	 */
	public Throwable getRootCause() {
		return rootCause;
	}

	/**
	 * 设置异常级联系
	 * 
	 * @param rootCause
	 */
	public void setRootCause(Throwable rootCause) {
		this.rootCause = rootCause;
	}

	/**
	 * 控制台输出（异常）
	 */
	@Override
	public void printStackTrace() {
		printStackTrace(System.err);
	}

	/**
	 * 控制台输出（异常）
	 */
	@Override
	public void printStackTrace(PrintStream out) {
		printStackTrace(new PrintWriter(out));
	}

	/**
	 * 控制台输出（异常）
	 */
	@Override
	public void printStackTrace(PrintWriter writer) {
		super.printStackTrace(writer);
		if (getRootCause() != null) {
			getRootCause().printStackTrace(writer);
		}
		writer.flush();
	}

	/** 
	 * 获取异常信息
	 */
	@Override
	public String getMessage() {
		return message;
	}

	/**
	 * 设置异常信息
	 * @param message　异常信息
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * 设置异常码
	 * 
	 * @return exceptionCode
	 */
	public String getExceptionCode() {
		return exceptionCode;
	}

	/**
	 * 返回异常
	 * 
	 * @param exceptionCode
	 *            要设置的 exceptionCode
	 */
	public void setExceptionCode(String exceptionCode) {
		this.exceptionCode = exceptionCode;
	}

	/**
	 * 异常返回参数表
	 */
	public Map<String, Object> getParamMap() {
		return paramMap;
	}

	/**
	 * 设置异常参数表
	 * @param paramMap 异常返回参数表
	 */
	public void setParamMap(Map<String, Object> paramMap) {
		this.paramMap = paramMap;
	}

	public String getMessageOfSystem() {
		return messageOfSystem;
	}

	public void setMessageOfSystem(String messageOfSystem) {
		this.messageOfSystem = messageOfSystem;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public boolean isServiceExceptionFlag() {
		return serviceExceptionFlag;
	}

	public void setServiceExceptionFlag(boolean serviceExceptionFlag) {
		this.serviceExceptionFlag = serviceExceptionFlag;
	}

}
