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
* <b>system：</b>      协同办公平台<br/>
* <b>description：</b> 文件上传服务<br/>
* <b>author：</b>      王洋<br/>
* <b>copyright：</b>	　 北京华安天诚科技有限公司<br/>
* <b>version：</b>     VER1.00 2010-04-06<br/>
*
**/
public class UploadFileService {

	private static final long serialVersionUID = -2931009545662528900L;

	/**
	 * 从文件路径中取出文件名
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
	 * 取出文件上传状态 Bean
	 */
	public UploadFileStatus getStatusBean(String keys) {
		UploadFileControler beanCtrl = UploadFileControler.getInstance();
		return beanCtrl.getUploadStatus(keys);
	}

	/**
	 * 文件上传状态 Bean保存
	 */
	public void saveStatusBean(String keys, UploadFileStatus statusBean) {
		UploadFileControler beanCtrl = UploadFileControler.getInstance();
		beanCtrl.setUploadStatus(keys, statusBean);
	}

	/**
	 * 初始化文件上传状态Bean
	 */
	public UploadFileStatus initStatusBean(long contentLength) {
		UploadFileStatus satusBean = new UploadFileStatus();
		satusBean.setStatus("正在准备处理");
		satusBean.setUploadTotalSize(contentLength);
		satusBean.setProcessStartTime(System.currentTimeMillis());
		return satusBean;
	}
	
	/**
	 * 删除已经上传的文件
	 */
	private void deleteUploadedFile(String keys) {
		UploadFileStatus satusBean = getStatusBean(keys);
		for (int i = 0; i < satusBean.getUploadFileUrlList().size(); i++) {
			File uploadedFile = new File(satusBean.getUploadFileUrlList().get(i));
			uploadedFile.delete();
		}
		satusBean.getUploadFileUrlList().clear();
		satusBean.setStatus("删除已上传的文件");
		saveStatusBean(keys, satusBean);
	}

	/**
	 * 上传过程中出错处理
	 */
	private void uploadExceptionHandle(String keys, String errMsg) throws ServletException, IOException {
		// 首先删除已经上传的文件
		deleteUploadedFile(keys);
		UploadFileStatus satusBean = getStatusBean(keys);
		satusBean.setStatus(errMsg);
		saveStatusBean(keys, satusBean);
	}
	

	/**
	 * 处理文件上传
	 */
	@SuppressWarnings("deprecation")
    public void processFileUpload(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String keys = request.getRemoteAddr();

		DiskFileItemFactory factory = new DiskFileItemFactory();
		// 设置内存缓冲区，超过后写入临时文件
		factory.setSizeThreshold(10240000);
		// 设置临时文件存储位置
		factory.setRepository(new File(request.getRealPath("/upload/temp")));
		ServletFileUpload upload = new ServletFileUpload(factory);
		// 设置单个文件的最大上传值
		upload.setFileSizeMax(102400000);
		// 设置整个request的最大值
		upload.setSizeMax(102400000);
		upload.setProgressListener(new UploadFileListener(keys));
		// 保存初始化后的FileUploadStatus Bean
		saveStatusBean(keys, initStatusBean(request.getContentLength()));

		try {
			List items = upload.parseRequest(request);
			// 处理文件上传
			for (int i = 0; i < items.size(); i++) {
				FileItem item = (FileItem) items.get(i);
				// 保存文件
				if (!item.isFormField() && item.getName().length() > 0) {
					String fileName = takeOutFileName(item.getName());
					File uploadedFile = new File(request.getRealPath("/upload") + File.separator + fileName);
					item.write(uploadedFile);
					// 更新上传文件列表
					UploadFileStatus statusBean = getStatusBean(keys);
					saveStatusBean(keys, statusBean);
					Thread.sleep(500);
				}
			}

		} catch (FileUploadException e) {
			uploadExceptionHandle(keys, "上传文件时发生错误:" + e.getMessage());
		} catch (Exception e) {
			uploadExceptionHandle(keys, "保存上传文件时发生错误:" + e.getMessage());
		}
	}

	/**
	 * 回应上传状态查询
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
