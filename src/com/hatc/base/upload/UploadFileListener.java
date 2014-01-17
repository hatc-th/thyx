package com.hatc.base.upload;

import org.apache.commons.fileupload.ProgressListener;

/**
* 
* <b>system：</b>      协同办公平台<br/>
* <b>description：</b> 上传状态监听器<br/>
* <b>author：</b>      王洋<br/>
* <b>copyright：</b>	　 北京华安天诚科技有限公司<br/>
* <b>version：</b>     VER1.00 2010-04-06<br/>
*
**/
public class UploadFileListener implements ProgressListener {
	private String keys = null;

	public UploadFileListener(String keys ) {
		this.keys = keys;
	}

	/**
	 * 更新状态
	 */
	public void update(long pBytesRead, long pContentLength, int pItems) {
		UploadFileService service = new UploadFileService();
		UploadFileStatus statusBean = service.getStatusBean(keys);
		statusBean.setUploadTotalSize(pContentLength);
		// 读取完成
		if (pContentLength == -1) {
			statusBean.setStatus("完成对" + pItems + "个文件的读取:读取了 " + pBytesRead + " bytes.");
			statusBean.setReadTotalSize(pBytesRead);
			statusBean.setSuccessUploadFileCount(pItems);
			statusBean.setProcessEndTime(System.currentTimeMillis());
			statusBean.setProcessRunningTime(statusBean.getProcessEndTime());
			// 读取中
		} else {
			statusBean.setStatus("当前正在处理第" + pItems + "个文件:已经读取了 " + pBytesRead + " / " + pContentLength + " bytes.");
			statusBean.setReadTotalSize(pBytesRead);
			statusBean.setCurrentUploadFileNum(pItems);
			statusBean.setProcessRunningTime(System.currentTimeMillis());
		}
		service.saveStatusBean(keys, statusBean);
	}
}
