package com.hatc.base.upload;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
* 
* <b>system��</b>      Эͬ�칫ƽ̨<br/>
* <b>description��</b> �ļ��ϴ�����<br/>
* <b>author��</b>      ����<br/>
* <b>copyright��</b>	�� ����������ϿƼ����޹�˾<br/>
* <b>version��</b>     VER1.00 2010-04-06<br/>
*
**/
public class UploadFileService {

	private static final long serialVersionUID = -2931009545662528900L;

	/**
	 * ���ļ�·����ȡ���ļ���
	 */
	public String takeOutFileName(String filePath) {
		int pos = filePath.lastIndexOf(File.separator);
		if (pos > 0) {
			return filePath.substring(pos + 1);
		} else {
			return filePath;
		}
	}

	/**
	 * ȡ���ļ��ϴ�״̬ Bean
	 */
	public UploadFileStatus getStatusBean(String keys) {
		UploadFileControler beanCtrl = UploadFileControler.getInstance();
		return beanCtrl.getUploadStatus(keys);
	}

	/**
	 * �ļ��ϴ�״̬ Bean����
	 */
	public void saveStatusBean(String keys, UploadFileStatus statusBean) {
		UploadFileControler beanCtrl = UploadFileControler.getInstance();
		beanCtrl.setUploadStatus(keys, statusBean);
	}

	/**
	 * ��ʼ���ļ��ϴ�״̬Bean
	 */
	public UploadFileStatus initStatusBean(long contentLength) {
		UploadFileStatus satusBean = new UploadFileStatus();
		satusBean.setStatus("����׼������");
		satusBean.setUploadTotalSize(contentLength);
		satusBean.setProcessStartTime(System.currentTimeMillis());
		return satusBean;
	}
	
	/**
	 * ɾ���Ѿ��ϴ����ļ�
	 */
	private void deleteUploadedFile(String keys) {
		UploadFileStatus satusBean = getStatusBean(keys);
		for (int i = 0; i < satusBean.getUploadFileUrlList().size(); i++) {
			File uploadedFile = new File(satusBean.getUploadFileUrlList().get(i));
			uploadedFile.delete();
		}
		satusBean.getUploadFileUrlList().clear();
		satusBean.setStatus("ɾ�����ϴ����ļ�");
		saveStatusBean(keys, satusBean);
	}

	/**
	 * �ϴ������г�����
	 */
	private void uploadExceptionHandle(String keys, String errMsg) throws ServletException, IOException {
		// ����ɾ���Ѿ��ϴ����ļ�
		deleteUploadedFile(keys);
		UploadFileStatus satusBean = getStatusBean(keys);
		satusBean.setStatus(errMsg);
		saveStatusBean(keys, satusBean);
	}
	

	/**
	 * �����ļ��ϴ�
	 */
	@SuppressWarnings("deprecation")
    public void processFileUpload(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String keys = request.getRemoteAddr();

		DiskFileItemFactory factory = new DiskFileItemFactory();
		// �����ڴ滺������������д����ʱ�ļ�
		factory.setSizeThreshold(10240000);
		// ������ʱ�ļ��洢λ��
		factory.setRepository(new File(request.getRealPath("/upload/temp")));
		ServletFileUpload upload = new ServletFileUpload(factory);
		// ���õ����ļ�������ϴ�ֵ
		upload.setFileSizeMax(102400000);
		// ��������request�����ֵ
		upload.setSizeMax(102400000);
		upload.setProgressListener(new UploadFileListener(keys));
		// �����ʼ�����FileUploadStatus Bean
		saveStatusBean(keys, initStatusBean(request.getContentLength()));

		try {
			List items = upload.parseRequest(request);
			// �����ļ��ϴ�
			for (int i = 0; i < items.size(); i++) {
				FileItem item = (FileItem) items.get(i);
				// �����ļ�
				if (!item.isFormField() && item.getName().length() > 0) {
					String fileName = takeOutFileName(item.getName());
					File uploadedFile = new File(request.getRealPath("/upload") + File.separator + fileName);
					item.write(uploadedFile);
					// �����ϴ��ļ��б�
					UploadFileStatus statusBean = getStatusBean(keys);
					saveStatusBean(keys, statusBean);
					Thread.sleep(500);
				}
			}

		} catch (FileUploadException e) {
			uploadExceptionHandle(keys, "�ϴ��ļ�ʱ��������:" + e.getMessage());
		} catch (Exception e) {
			uploadExceptionHandle(keys, "�����ϴ��ļ�ʱ��������:" + e.getMessage());
		}
	}

	/**
	 * ��Ӧ�ϴ�״̬��ѯ
	 */
	public void responseStatusQuery(String keys, HttpServletResponse response) throws IOException {
		UploadFileService service = new UploadFileService();
		response.setContentType("text/xml");
		response.setHeader("Cache-Control", "no-cache");
		UploadFileStatus satusBean = service.getStatusBean(keys);
		System.out.println(satusBean.toXML());
		response.getWriter().write(satusBean.toXML());
		
	}
}
