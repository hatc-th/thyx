package com.hatc.base.common;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* 
* <b>system��</b>      Эͬ�칫ƽ̨<br/>
* <b>description��</b> ϵͳ�Զ����쳣��<br/>
* <b>author��</b>      ����<br/>
* <b>copyright��</b>	�� ����������ϿƼ����޹�˾<br/>
* <b>version��</b>     VER1.00 2010-04-06<br/>
*                      ֧��ԭʼ��Ϣ: ���ڼ�¼����־�ļ�
* 
*
**/
public class BaseException extends Exception {
	private static final long serialVersionUID = 5404766056023341965L;
	
	private boolean serviceExceptionFlag = false; 

	/** �쳣���� */
	protected String exceptionCode;

	/** ���ز��� */
	protected Map<String, Object> paramMap = new HashMap<String, Object>();

	/** �쳣����(�쳣��) */
	protected Throwable rootCause = null;

	/** �쳣���ϣ���Ԫ����*/
	private List<BaseException> exceptions = new ArrayList<BaseException>();

	/** ��Ϣkey */
	private String messageKey = null;

	/** ��Ϣ */
	private String message = null;
	
	/** ԭʼ��Ϣ: ���ڼ�¼����־�ļ� */
	private String messageOfSystem = null;

	/** ����ʽ��Ϣ */
	private Object[] messageArgs = null;
	
	/** ��Ϣ */
	private String reason = null;

	/**
	 * ���캯��
	 */
	public BaseException() {
		super();
	}

	/**
	 * ���캯��
	 * 
	 * @param exceptionCode
	 *            ������
	 */
	public BaseException(String exceptionCode) {
		this.exceptionCode = exceptionCode;
	}
	
	/*
	 * ���캯��
	 * 
	 * @param exceptionCode
	 *            ������
	 
	public BaseException(FomsException fex) {
		this.exceptionCode = String.valueOf(fex.getFaultcode());
		this.message = fex.getMessage();
	}
*/
	/**
	 * ���캯��
	 * 
	 * @param key
	 *            ��ϢKey
	 * @param info
	 *            ��Ϣ��
	 */
	public BaseException(String[] info) {
		this.messageArgs = info;
	}

	/**
	 * ���캯��
	 * 
	 * @param rootCause
	 *            �쳣����
	 */
	public BaseException(Throwable rootCause) {
		this.rootCause = rootCause;
	}

	/**
	 * ȡ���쳣����
	 * 
	 * @return List
	 */
	public List getExceptions() {
		return exceptions;
	}

	/**
	 * ����쳣
	 * 
	 * @param baseException
	 *            �쳣
	 */
	public void setExceptions(BaseException baseException) {
		exceptions.add(baseException);
	}

	/**
	 * ȡ����Ϣ��
	 * 
	 * @return
	 */
	public Object[] getMessageArgs() {
		return messageArgs;
	}

	/**
	 * ������Ϣ��
	 * 
	 * @param messageArgs
	 *            ��Ϣ��
	 */
	public void setMessageArgs(Object[] messageArgs) {
		this.messageArgs = messageArgs;
	}

	/**
	 * ȡ����ϢKey
	 * 
	 * @return
	 */
	public String getMessageKey() {
		return messageKey;
	}

	/**
	 * ������ϢKey
	 * 
	 * @param messageKey
	 *            ��ϢKey
	 * @return
	 */
	public void setMessageKey(String messageKey) {
		this.messageKey = messageKey;
	}

	/**
	 * ȡ���쳣����
	 * 
	 * @return
	 */
	public Throwable getRootCause() {
		return rootCause;
	}

	/**
	 * �����쳣����ϵ
	 * 
	 * @param rootCause
	 */
	public void setRootCause(Throwable rootCause) {
		this.rootCause = rootCause;
	}

	/**
	 * ����̨������쳣��
	 */
	@Override
	public void printStackTrace() {
		printStackTrace(System.err);
	}

	/**
	 * ����̨������쳣��
	 */
	@Override
	public void printStackTrace(PrintStream out) {
		printStackTrace(new PrintWriter(out));
	}

	/**
	 * ����̨������쳣��
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
	 * ��ȡ�쳣��Ϣ
	 */
	@Override
	public String getMessage() {
		return message;
	}

	/**
	 * �����쳣��Ϣ
	 * @param message���쳣��Ϣ
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * �����쳣��
	 * 
	 * @return exceptionCode
	 */
	public String getExceptionCode() {
		return exceptionCode;
	}

	/**
	 * �����쳣
	 * 
	 * @param exceptionCode
	 *            Ҫ���õ� exceptionCode
	 */
	public void setExceptionCode(String exceptionCode) {
		this.exceptionCode = exceptionCode;
	}

	/**
	 * �쳣���ز�����
	 */
	public Map<String, Object> getParamMap() {
		return paramMap;
	}

	/**
	 * �����쳣������
	 * @param paramMap �쳣���ز�����
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
