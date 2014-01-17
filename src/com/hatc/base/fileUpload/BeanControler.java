package com.hatc.base.fileUpload;

/**
* 
* <b>system��</b>      Эͬ�칫ƽ̨<br/>
* <b>description��</b> �ļ����´�������<br>
*    ��Ҫ�����Ƕ�FileUploadStatus���й���Ϊ�ͻ����ṩ��Ӧ��<br>
*    FileUploadStatus���������һ�������ࡣ<br/>
* <b>author��</b>      ����<br/>
* <b>copyright��</b>	�� ����������ϿƼ����޹�˾<br/>
* <b>version��</b>     VER1.00 2010-04-06<br/>
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
     * ȡ����ӦFileUploadStatus�����Ĵ洢λ��
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
     * ȡ����ӦFileUploadStatus�����
     */
    public FileUploadStatus getUploadStatus(String strID) {
    	if(indexOf(strID)>=0 )
            return vector.elementAt(indexOf(strID));
    	else
    		return null;
    }
    /**
     * �洢FileUploadStatus�����
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
     * ɾ��FileUploadStatus�����
     */
    public void removeUploadStatus(String strID){
        int nIndex = indexOf(strID);
        if(-1!=nIndex)
            vector.removeElementAt(nIndex);
    }
}
