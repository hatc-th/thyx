package com.hatc.base.upload;

/**
* 
* <b>system��</b>      Эͬ�칫ƽ̨<br/>
* <b>description��</b> �ϴ�״̬������<br/>
*                      ��Ҫ�����Ƕ�UploadFileControler���й���(����)��<br/>
* <b>author��</b>      ����<br/>
* <b>copyright��</b>	�� ����������ϿƼ����޹�˾<br/>
* <b>version��</b>     VER1.00 2010-04-06<br/>
*
**/
import java.util.HashMap;
import java.util.Map;

public class UploadFileControler {
	
	private static UploadFileControler beanControler = new UploadFileControler();

	private Map<String, UploadFileStatus> statusMap = new HashMap<String, UploadFileStatus>();

	private UploadFileControler() {
	}

	public static UploadFileControler getInstance() {
		return beanControler;
	}

	/**
	 * ȡ����ӦUploadFileStatus�����
	 */
	public UploadFileStatus getUploadStatus(String keys) {
		return this.statusMap.get(keys);
	}

	/**
	 * �洢UploadFileStatus�����
	 */
	public void setUploadStatus(String keys, UploadFileStatus status) {
		status.setKeys(keys);
		this.statusMap.put(keys, status);
	}

	/**
	 * ɾ��FileUploadStatus�����
	 */
	public void removeUploadStatus(String strID) {
		this.statusMap.remove(strID);
	}
}
