package com.hatc.base.upload;

/**
* 
* <b>system：</b>      协同办公平台<br/>
* <b>description：</b> 上传状态控制器<br/>
*                      主要作用是对UploadFileControler进行管理(单例)。<br/>
* <b>author：</b>      王洋<br/>
* <b>copyright：</b>	　 北京华安天诚科技有限公司<br/>
* <b>version：</b>     VER1.00 2010-04-06<br/>
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
	 * 取得相应UploadFileStatus类对象
	 */
	public UploadFileStatus getUploadStatus(String keys) {
		return this.statusMap.get(keys);
	}

	/**
	 * 存储UploadFileStatus类对象
	 */
	public void setUploadStatus(String keys, UploadFileStatus status) {
		status.setKeys(keys);
		this.statusMap.put(keys, status);
	}

	/**
	 * 删除FileUploadStatus类对象
	 */
	public void removeUploadStatus(String strID) {
		this.statusMap.remove(strID);
	}
}
