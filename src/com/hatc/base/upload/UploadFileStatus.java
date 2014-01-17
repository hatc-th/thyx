package com.hatc.base.upload;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
* 
* <b>system��</b>      Эͬ�칫ƽ̨<br/>
* <b>description��</b> �ļ��ϴ�״̬<br/>
* <b>author��</b>      ����<br/>
* <b>copyright��</b>	�� ����������ϿƼ����޹�˾<br/>
* <b>version��</b>     VER1.00 2010-04-06<br/>
*
**/
public class UploadFileStatus implements Serializable {

	private static final long serialVersionUID = -6103121705310677801L;

	// �ϴ���ʶ
	private String keys;

	// �ϴ�����
	private long uploadTotalSize = 0;

	// ��ȡ�ϴ�����
	private long readTotalSize = 0;

	// ��ǰ�ϴ��ļ���
	private int currentUploadFileNum = 0;

	// �ɹ���ȡ�ϴ��ļ���
	private int successUploadFileCount = 0;

	// ������ʼʱ��
	private long processStartTime = 0l;

	// ������ֹʱ��
	private long processEndTime = 0l;

	// ����ִ��ʱ��
	private long processRunningTime = 0l;

	// ״̬
	private String status = "";

	// �ϴ��ļ�URL�б�
	private List<String> uploadFileUrlList = new ArrayList<String>();

	/**
	 * @return currentUploadFileNum
	 */
	public int getCurrentUploadFileNum() {
		return currentUploadFileNum;
	}

	/**
	 * @param currentUploadFileNum
	 *            Ҫ���õ� currentUploadFileNum
	 */
	public void setCurrentUploadFileNum(int currentUploadFileNum) {
		this.currentUploadFileNum = currentUploadFileNum;
	}

	/**
	 * @return processEndTime
	 */
	public long getProcessEndTime() {
		return processEndTime;
	}

	/**
	 * @param processEndTime
	 *            Ҫ���õ� processEndTime
	 */
	public void setProcessEndTime(long processEndTime) {
		this.processEndTime = processEndTime;
	}

	/**
	 * @return processRunningTime
	 */
	public long getProcessRunningTime() {
		return processRunningTime;
	}

	/**
	 * @param processRunningTime
	 *            Ҫ���õ� processRunningTime
	 */
	public void setProcessRunningTime(long processRunningTime) {
		this.processRunningTime = processRunningTime;
	}

	/**
	 * @return processStartTime
	 */
	public long getProcessStartTime() {
		return processStartTime;
	}

	/**
	 * @param processStartTime
	 *            Ҫ���õ� processStartTime
	 */
	public void setProcessStartTime(long processStartTime) {
		this.processStartTime = processStartTime;
	}

	/**
	 * @return readTotalSize
	 */
	public long getReadTotalSize() {
		return readTotalSize;
	}

	/**
	 * @param readTotalSize
	 *            Ҫ���õ� readTotalSize
	 */
	public void setReadTotalSize(long readTotalSize) {
		this.readTotalSize = readTotalSize;
	}

	/**
	 * @return status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            Ҫ���õ� status
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return successUploadFileCount
	 */
	public int getSuccessUploadFileCount() {
		return successUploadFileCount;
	}

	/**
	 * @param successUploadFileCount
	 *            Ҫ���õ� successUploadFileCount
	 */
	public void setSuccessUploadFileCount(int successUploadFileCount) {
		this.successUploadFileCount = successUploadFileCount;
	}

	/**
	 * @return uploadTotalSize
	 */
	public long getUploadTotalSize() {
		return uploadTotalSize;
	}

	/**
	 * @param uploadTotalSize
	 *            Ҫ���õ� uploadTotalSize
	 */
	public void setUploadTotalSize(long uploadTotalSize) {
		this.uploadTotalSize = uploadTotalSize;
	}

	/**
	 * @return uploadFileUrlList
	 */
	public List<String> getUploadFileUrlList() {
		return uploadFileUrlList;
	}

	/**
	 * @param uploadFileUrlList
	 *            Ҫ���õ� uploadFileUrlList
	 */
	public void setUploadFileUrlList(List<String> uploadFileUrlList) {
		this.uploadFileUrlList = uploadFileUrlList;
	}

	/**
	 * @return keys
	 */
	public String getKeys() {
		return keys;
	}

	/**
	 * @param keys
	 *            Ҫ���õ� keys
	 */
	public void setKeys(String keys) {
		this.keys = keys;
	}

	public String toXML() {
		StringBuffer buff = new StringBuffer();
		buff.append("<bean>");
		buff.append("<keys>");
		buff.append(keys);
		buff.append("</keys>");
		buff.append("<uploadTotalSize>");
		buff.append(uploadTotalSize);
		buff.append("</uploadTotalSize>");
		buff.append("<readTotalSize>");
		buff.append(readTotalSize);
		buff.append("</readTotalSize>");
		buff.append("<currentUploadFileNum>");
		buff.append(currentUploadFileNum);
		buff.append("</currentUploadFileNum>");
		buff.append("<successUploadFileCount>");
		buff.append(successUploadFileCount);
		buff.append("</successUploadFileCount>");
		buff.append("<processStartTime>");
		buff.append(processStartTime);
		buff.append("</processStartTime>");
		buff.append("<processEndTime>");
		buff.append(processEndTime);
		buff.append("</processEndTime>");
		buff.append("<processRunningTime>");
		buff.append(processRunningTime);
		buff.append("</processRunningTime>");
		buff.append("<status>");
		buff.append(status);
		buff.append("</status>");
		buff.append("</bean>");
		return buff.toString();
	}
	
	public String toJSon() {
		StringBuffer strJSon = new StringBuffer();
		strJSon.append("{UploadTotalSize:");
		strJSon.append(getUploadTotalSize());
		strJSon.append(",");
		strJSon.append("ReadTotalSize:");
		strJSon.append(getReadTotalSize());
		strJSon.append(",");
		strJSon.append("CurrentUploadFileNum:");
		strJSon.append(getCurrentUploadFileNum());
		strJSon.append(",");
		strJSon.append("SuccessUploadFileCount:");
		strJSon.append(getSuccessUploadFileCount());
		strJSon.append(",");
		strJSon.append("Status:'");
		strJSon.append(getStatus());
		strJSon.append("',");
		strJSon.append("ProcessStartTime:");
		strJSon.append(getProcessStartTime());
		strJSon.append(",");
		strJSon.append("ProcessEndTime:");
		strJSon.append(getProcessEndTime());
		strJSon.append(",");
		strJSon.append("ProcessRunningTime:");
		strJSon.append(getProcessRunningTime());
		return strJSon.toString();

	}
}
