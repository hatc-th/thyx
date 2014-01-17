package com.hatc.base.fileUpload;

/**
* 
* <b>system：</b>      协同办公平台<br/>
* <b>description：</b> 文件上下传控制器<br>
*    主要作用是对FileUploadStatus进行管理，为客户端提供相应的<br>
*    FileUploadStatus类对象。这是一个单例类。<br/>
* <b>author：</b>      王洋<br/>
* <b>copyright：</b>	　 北京华安天诚科技有限公司<br/>
* <b>version：</b>     VER1.00 2010-04-06<br/>
*
**/
import java.util.Vector;

public class BeanControler {
    private static BeanControler beanControler = new BeanControler();
    private Vector<FileUploadStatus> vector = new Vector<FileUploadStatus>();
    private BeanControler() {
    }

    public static BeanControler getInstance() {
        return beanControler;
    }

    /**
     * 取得相应FileUploadStatus类对象的存储位置
     */
    private int indexOf(String strID) {
        int nReturn = -1;
        for (int i = 0; i < vector.size(); i++) {
            FileUploadStatus status = vector.elementAt(i);
            if (status.getUploadAddr().equals(strID)) {
                nReturn = i;
                break;
            }
        }
        return nReturn;
    }
    /**
     * 取得相应FileUploadStatus类对象
     */
    public FileUploadStatus getUploadStatus(String strID) {
    	if(indexOf(strID)>=0 )
            return vector.elementAt(indexOf(strID));
    	else
    		return null;
    }
    /**
     * 存储FileUploadStatus类对象
     */
    public void setUploadStatus(FileUploadStatus status) {
        int nIndex = indexOf(status.getUploadAddr());
        if ( -1 == nIndex) {
            vector.add(status);
        } else {
            vector.insertElementAt(status, nIndex);
            vector.removeElementAt(nIndex + 1);
        }
    }
    /**
     * 删除FileUploadStatus类对象
     */
    public void removeUploadStatus(String strID){
        int nIndex = indexOf(strID);
        if(-1!=nIndex)
            vector.removeElementAt(nIndex);
    }
}
