package com.hatc.base.upload;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
* 
* <b>system：</b>      协同办公平台<br/>
* <b>description：</b> 文件上传状态<br/>
* <b>author：</b>      王洋<br/>
* <b>copyright：</b>	　 北京华安天诚科技有限公司<br/>
* <b>version：</b>     VER1.00 2010-04-06<br/>
*
**/
public class UploadFileStatus implements Serializable {

	private static final long serialVersionUID = -6103121705310677801L;

	// 上传标识
	private String keys;

	// 上传总量
	private long uploadTotalSize = 0;

	// 读取上传总量
	private long readTotalSize = 0;

	// 当前上传文件号
	private int currentUploadFileNum = 0;

	// 成功读取上传文件数
	private int successUploadFileCount = 0;

	// 处理起始时间
	private long processStartTime = 0l;

	// 处理终止时间
	private long processEndTime = 0l;

	// 处理执行时间
	private long processRunningTime = 0l;

	// 状态
	private String status = "";

	// 上传文件URL列表
	private List<String> uploadFileUrlList = new ArrayList<String>();

	/**
	 * @return currentUploadFileNum
	 */
	public int getCurrentUploadFileNum() {
		return currentUploadFileNum;
	}

	/**
	 * @param currentUploadFileNum
	 *            要设置的 currentUploadFileNum
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
	 *            要设置的 processEndTime
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
	 *            要设置的 processRunningTime
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
	 *            要设置的 processStartTime
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
	 *            要设置的 readTotalSize
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
	 *            要设置的 status
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
	 *            要设置的 successUploadFileCount
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
	 *            要设置的 uploadTotalSize
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
	 *            要设置的 uploadFileUrlList
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
	 *            要设置的 keys
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
