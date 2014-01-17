package com.hatc.base.fileUpload;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;

/**
* 
* <b>system��</b>      Эͬ�칫ƽ̨<br/>
* <b>description��</b> �ļ����´���̨����֧���ļ�״̬ʵʱ��ѯ��<br/>
* <b>author��</b>      ����<br/>
* <b>copyright��</b>	�� ����������ϿƼ����޹�˾<br/>
* <b>version��</b>     VER1.00 2010-04-06<br/>
* <b>version��</b>     VER1.01 2012-02-22 chenzj<br/>
**/
public class BackGroundService 
{
	 private Logger logger = Logger.getLogger(BackGroundService.class);

	/**
	 * ��request��ȡ��FileUploadStatus Bean
	 */
	public static FileUploadStatus getStatusBean(HttpServletRequest request) {
		// �ϴ��ļ���ʶ
		String index = (String) request.getAttribute(FileUploadConstants.UPLOAD_INDEX);
		BeanControler beanCtrl = BeanControler.getInstance();
		return beanCtrl.getUploadStatus(index);
	}

	/**
	 * ��FileUploadStatus Bean���浽�������BeanControler
	 */
	public static void saveStatusBean(HttpServletRequest request, FileUploadStatus statusBean) {
		// �ϴ��ļ���ʶ
		String index = (String) request.getAttribute(FileUploadConstants.UPLOAD_INDEX);
		statusBean.setUploadAddr(index);
		BeanControler beanCtrl = BeanControler.getInstance();
		beanCtrl.setUploadStatus(statusBean);
	}

	/**
	 * �����ļ��ϴ�
	 * TODO nly 2011-12-19
	 */
	//public void upload(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	public void upload(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// System.out.println(aHttpServletRequest.getSession().getAttribute(BaseConstants.UPLOAD_FILE_MAX_SIZE));
		// Employee user = (Employee) aHttpServletRequest.getSession().getAttribute(Constants.SESSION_USER);
		// String uploadIndex = user.getUserId();
		// aHttpServletRequest.setAttribute(FileUploadConstants.UPLOAD_INDEX,uploadIndex);
		// String savePath = TtimsConfig.getFtpmsConfig("fileSavePath");
		// String pathMsg = TtimsConfig.getFtpmsConfig("path_msg");
		// aHttpServletRequest.setAttribute(FileUploadConstants.UPLOAD_PATH,savePath + pathMsg);

		// �ϴ�·��
		String path = (String) request.getAttribute(FileUploadConstants.UPLOAD_PATH);
		DiskFileItemFactory factory = new DiskFileItemFactory();
		// �����ڴ滺������������д����ʱ�ļ�
		factory.setSizeThreshold(10240000);
		// ������ʱ�ļ��洢λ��
		factory.setRepository(new File(System.getProperty("java.io.tmpdir")));
		ServletFileUpload upload = new ServletFileUpload(factory);
		// ���õ����ļ�������ϴ�ֵ
		upload.setFileSizeMax(10240000);
		// ��������request�����ֵ
		upload.setSizeMax(10240000);
		upload.setProgressListener(new FileUploadListener(request));
		// �����ʼ�����FileUploadStatus Bean
		saveStatusBean(request, initStatusBean(request));

		try {
			List items = upload.parseRequest(request);
			// �����ļ��ϴ�
			FileItem item = null;
			String fileName = "";
			File uploadedFile = null;
			FileUploadStatus satusBean = null;
			for (int i = 0; i < items.size(); i++) {
				item = (FileItem) items.get(i);
				// �����ļ�
				if (!item.isFormField() && item.getName().length() > 0) {
					fileName = takeOutFileName(item.getName());
					uploadedFile = new File(path + File.separator + fileName);
					item.write(uploadedFile);
					// �����ϴ��ļ��б�
					satusBean = getStatusBean(request);
					satusBean.getUploadFileUrlList().add(fileName);
					saveStatusBean(request, satusBean);
					Thread.sleep(500);
				}
			}
		} catch (FileUploadException e) {
			e.printStackTrace();
			logger.error(e.getMessage(),e);
			uploadExceptionHandle(request, "�ϴ��ļ�ʱ��������:" + e.getMessage());
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(),e);
			uploadExceptionHandle(request, "�����ϴ��ļ�ʱ��������:" + e.getMessage());
			throw e;
		}
	}

	/**
	 * �����ļ��ϴ�
	 */
	public List getFileList(HttpServletRequest request) throws ServletException, Exception{
	//public List getFileList(HttpServletRequest request) throws ServletException, IOException {
		DiskFileItemFactory factory = new DiskFileItemFactory();
		// �����ڴ滺������������д����ʱ�ļ�
		factory.setSizeThreshold(10240000);
		// ������ʱ�ļ��洢λ��
		factory.setRepository(new File(System.getProperty("java.io.tmpdir")));
		ServletFileUpload upload = new ServletFileUpload(factory);
		// ���õ����ļ�������ϴ�ֵ
		upload.setFileSizeMax(10240000);
		// ��������request�����ֵ
		upload.setSizeMax(10240000);
		List items = null;
		//�����ļ����������ж� 2012-02-22 chenzj
		int len = request.getContentLength();
		if(len>10240000){
			logger.error("�ϴ��ļ����ܳ���10M!");
			Exception ex = new Exception("�ϴ��ļ����ܳ���10M!");
			throw ex;
			
		}else{
		try{
		items = upload.parseRequest(request);
		
		} catch (FileUploadException e) {
			e.printStackTrace();
			logger.error(e.getMessage(),e);
			uploadExceptionHandle(request, "�ϴ��ļ�ʱ��������:" + e.getMessage());}
		}
		
		return items;
	}

	/**
	 * �����ϴ��ļ�
	 * @param items �ϴ��ļ�����
	 * @param path ·��
	 * @param nameMap���ļ�������
	 * @throws ServletException
	 * @throws IOException
	 */
	public void saveFile(List items, String path, Map<String, String> nameMap) throws ServletException, IOException {
		File uploadedFile = null;
		try {
			logger.info("saveFile:" + path);
			FileItem item = null;
			String fileName = null;
			String asName = "";
			
			// �����ļ��ϴ�
			for (int i = 0; i < items.size(); i++) {
				item = (FileItem) items.get(i);
				// �����ļ�
				if (!item.isFormField() && item.getName().length() > 0) {
					fileName = takeOutFileName(item.getName());
					if (nameMap != null) {
						asName = nameMap.get(fileName);
						fileName = asName != null ? asName : fileName;
					}
					uploadedFile = new File(path + File.separator + fileName);
					item.write(uploadedFile);
				}
			}
		} catch (FileUploadException e) {
			e.printStackTrace();
			 
		} catch (Exception e) {
			e.printStackTrace();
			 
		}
	}

	/**
	 * �ļ�����
	 * @param path �ļ�·��
	 * @param nameMap �ļ����Ƽ���
	 * @throws ServletException
	 * @throws IOException
	 */
	public void saveFile(String path, Map<String, String> nameMap) throws ServletException, IOException{
		try {

			for (Iterator<String> it = nameMap.keySet().iterator(); it.hasNext();) {
				String keys = it.next();
				String fileId_temp = nameMap.get(keys);
				FileInputStream in = new FileInputStream(path + File.separator + fileId_temp);
				File file = new File(path + File.separator + keys);
				if (!file.exists()) {
					file.createNewFile();
				}
				FileOutputStream out = new FileOutputStream(file);
				int c;
				byte buffer[] = new byte[1024];
				while ((c = in.read(buffer)) != -1) {
					for (int i = 0; i < c; i++)
						out.write(buffer[i]);
				}
				in.close();
				out.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
			 
		}
	}

	/**
	 * ��Ӧ�ϴ�״̬��ѯ
	 */
	public void responseUpload(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/xml");
		response.setHeader("Cache-Control", "no-cache");
		response.setCharacterEncoding("GBK");
		response.getWriter().write("�ϴ��ɹ�");
	}

	/**
	 * ��Ӧ�ϴ�״̬��ѯ
	 */
	public void statusQuery(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/xml");
		response.setHeader("Cache-Control", "no-cache");
		response.setCharacterEncoding("GBK");
		FileUploadStatus satusBean = getStatusBean(request);
		response.getWriter().write(satusBean.toJSon());
	}

	/**
	 * ����ȡ���ļ��ϴ�
	 */
	public void cancelUpload(HttpServletRequest request, HttpServletResponse response) throws IOException {
		FileUploadStatus satusBean = getStatusBean(request);
		satusBean.setCancel(true);
		saveStatusBean(request, satusBean);
		statusQuery(request, response);
	}

	/**
	 * ���ļ�·����ȡ���ļ���
	 */
	private String takeOutFileName(String filePath) {
		int pos = filePath.lastIndexOf("\\");
		if(pos < 0){
			pos = filePath.lastIndexOf("/");
		}
		if (pos > 0) {
			return filePath.substring(pos + 1);
		} else {
			return filePath;
		}
	}

	/**
	 * ɾ���Ѿ��ϴ����ļ�
	 */
	public void deleteUploadedFile(String path, String[] nameArray) {
		for (String fileName : nameArray) {
			if (fileName != null && !fileName.equals("")) {
				File file = new File(path + File.separator + fileName);
				if (file.exists()) {
					file.delete();
				}
			}
		}
	}

	/**
	 * ɾ���Ѿ��ϴ����ļ�
	 */
	public void deleteUploadedFile(String path, String fileName) {
		if (fileName != null && !fileName.equals("")) {
			File file = new File(path + File.separator + fileName);
			if (file.exists()) {
				file.delete();
			}
		}
	}

	/**
	 * ɾ���Ѿ��ϴ����ļ�
	 */
	private void deleteUploadedFile(HttpServletRequest request) {
		// �ϴ�·��
		String path = (String) request.getAttribute(FileUploadConstants.UPLOAD_PATH);
		FileUploadStatus satusBean = getStatusBean(request);
		File uploadedFile = null;
		int size = 0 ;
		//TODO ningliyu  �����ֵ
	 if(satusBean!=null)
		 if(satusBean.getUploadFileUrlList()!=null)
			size = satusBean.getUploadFileUrlList().size();
	 if(size>0)
	 {
	 for (int i = 0; i < size; i++) {
		//for (int i = 0; i < satusBean.getUploadFileUrlList().size(); i++) {
			uploadedFile = new File(path + File.separator + satusBean.getUploadFileUrlList().get(i));
			uploadedFile.delete();
		}
		satusBean.getUploadFileUrlList().clear();
	 }
		satusBean.setStatus("ɾ�����ϴ����ļ�");
		saveStatusBean(request, satusBean);
	}

	/**
	 * �ϴ������г�����
	 */
	private void uploadExceptionHandle(HttpServletRequest request, String errMsg) throws ServletException, IOException {
		// ����ɾ���Ѿ��ϴ����ļ�
		deleteUploadedFile(request);
		FileUploadStatus satusBean = getStatusBean(request);
		satusBean.setStatus(errMsg);
		saveStatusBean(request, satusBean);
	}

	/**
	 * ��ʼ���ļ��ϴ�״̬Bean
	 */
	private FileUploadStatus initStatusBean(HttpServletRequest request) {
		// �ϴ�·��
		String path = (String) request.getAttribute(FileUploadConstants.UPLOAD_PATH);
		FileUploadStatus satusBean = new FileUploadStatus();
		satusBean.setStatus("����׼������");
		satusBean.setUploadTotalSize(request.getContentLength());
		satusBean.setProcessStartTime(System.currentTimeMillis());
		satusBean.setBaseDir(path);
		return satusBean;
	}
}
