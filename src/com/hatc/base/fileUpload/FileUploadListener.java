package com.hatc.base.fileUpload;

import org.apache.commons.fileupload.ProgressListener;
import javax.servlet.http.HttpServletRequest;

/**
* 
* <b>system��</b>      Эͬ�칫ƽ̨<br/>
* <b>description��</b> �ļ��ϴ������еĽ�����ʾ<br>
* <b>author��</b>      ����<br/>
* <b>copyright��</b>	�� ����������ϿƼ����޹�˾<br/>
* <b>version��</b>     VER1.00 2010-04-06<br/>
*
**/
public class FileUploadListener implements ProgressListener{
	private HttpServletRequest request=null;

	public FileUploadListener(HttpServletRequest request){
		this.request=request;
	}

	/**
	 * ����״̬
	 */
	public void update(long pBytesRead, long pContentLength, int pItems){
		FileUploadStatus statusBean= BackGroundService.getStatusBean(request);
		statusBean.setUploadTotalSize(pContentLength);
		//��ȡ���
	    if (pContentLength == -1) {
	       statusBean.setStatus("��ɶ�" + pItems +"���ļ��Ķ�ȡ:��ȡ�� " + pBytesRead + " bytes.");
	       statusBean.setReadTotalSize(pBytesRead);
	       statusBean.setSuccessUploadFileCount(pItems);
	       statusBean.setProcessEndTime(System.currentTimeMillis());
	       statusBean.setProcessRunningTime(statusBean.getProcessEndTime());
	    //��ȡ��
	    } else {
	       statusBean.setStatus("��ǰ���ڴ����" + pItems +"���ļ�:�Ѿ���ȡ�� " + pBytesRead + " / " + pContentLength+ " bytes.");
	       statusBean.setReadTotalSize(pBytesRead);
	       statusBean.setCurrentUploadFileNum(pItems);
	       statusBean.setProcessRunningTime(System.currentTimeMillis());
	    }
            BackGroundService.saveStatusBean(request,statusBean);
	}
}
