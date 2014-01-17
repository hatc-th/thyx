package com.hatc.base.upload;

import org.apache.commons.fileupload.ProgressListener;

/**
* 
* <b>system��</b>      Эͬ�칫ƽ̨<br/>
* <b>description��</b> �ϴ�״̬������<br/>
* <b>author��</b>      ����<br/>
* <b>copyright��</b>	�� ����������ϿƼ����޹�˾<br/>
* <b>version��</b>     VER1.00 2010-04-06<br/>
*
**/
public class UploadFileListener implements ProgressListener {
	private String keys = null;

	public UploadFileListener(String keys ) {
		this.keys = keys;
	}

	/**
	 * ����״̬
	 */
	public void update(long pBytesRead, long pContentLength, int pItems) {
		UploadFileService service = new UploadFileService();
		UploadFileStatus statusBean = service.getStatusBean(keys);
		statusBean.setUploadTotalSize(pContentLength);
		// ��ȡ���
		if (pContentLength == -1) {
			statusBean.setStatus("��ɶ�" + pItems + "���ļ��Ķ�ȡ:��ȡ�� " + pBytesRead + " bytes.");
			statusBean.setReadTotalSize(pBytesRead);
			statusBean.setSuccessUploadFileCount(pItems);
			statusBean.setProcessEndTime(System.currentTimeMillis());
			statusBean.setProcessRunningTime(statusBean.getProcessEndTime());
			// ��ȡ��
		} else {
			statusBean.setStatus("��ǰ���ڴ����" + pItems + "���ļ�:�Ѿ���ȡ�� " + pBytesRead + " / " + pContentLength + " bytes.");
			statusBean.setReadTotalSize(pBytesRead);
			statusBean.setCurrentUploadFileNum(pItems);
			statusBean.setProcessRunningTime(System.currentTimeMillis());
		}
		service.saveStatusBean(keys, statusBean);
	}
}
