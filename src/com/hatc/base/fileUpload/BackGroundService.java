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
* <b>system：</b>      协同办公平台<br/>
* <b>description：</b> 文件上下传后台处理（支持文件状态实时查询）<br/>
* <b>author：</b>      王洋<br/>
* <b>copyright：</b>	　 北京华安天诚科技有限公司<br/>
* <b>version：</b>     VER1.00 2010-04-06<br/>
* <b>version：</b>     VER1.01 2012-02-22 chenzj<br/>
**/
public class BackGroundService 
{
	 private Logger logger = Logger.getLogger(BackGroundService.class);

	/**
	 * 从request中取出FileUploadStatus Bean
	 */
	public static FileUploadStatus getStatusBean(HttpServletRequest request) {
		// 上传文件标识
		String index = (String) request.getAttribute(FileUploadConstants.UPLOAD_INDEX);
		BeanControler beanCtrl = BeanControler.getInstance();
		return beanCtrl.getUploadStatus(index);
	}

	/**
	 * 把FileUploadStatus Bean保存到类控制器BeanControler
	 */
	public static void saveStatusBean(HttpServletRequest request, FileUploadStatus statusBean) {
		// 上传文件标识
		String index = (String) request.getAttribute(FileUploadConstants.UPLOAD_INDEX);
		statusBean.setUploadAddr(index);
		BeanControler beanCtrl = BeanControler.getInstance();
		beanCtrl.setUploadStatus(statusBean);
	}

	/**
	 * 处理文件上传
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

		// 上传路径
		String path = (String) request.getAttribute(FileUploadConstants.UPLOAD_PATH);
		DiskFileItemFactory factory = new DiskFileItemFactory();
		// 设置内存缓冲区，超过后写入临时文件
		factory.setSizeThreshold(10240000);
		// 设置临时文件存储位置
		factory.setRepository(new File(System.getProperty("java.io.tmpdir")));
		ServletFileUpload upload = new ServletFileUpload(factory);
		// 设置单个文件的最大上传值
		upload.setFileSizeMax(10240000);
		// 设置整个request的最大值
		upload.setSizeMax(10240000);
		upload.setProgressListener(new FileUploadListener(request));
		// 保存初始化后的FileUploadStatus Bean
		saveStatusBean(request, initStatusBean(request));

		try {
			List items = upload.parseRequest(request);
			// 处理文件上传
			FileItem item = null;
			String fileName = "";
			File uploadedFile = null;
			FileUploadStatus satusBean = null;
			for (int i = 0; i < items.size(); i++) {
				item = (FileItem) items.get(i);
				// 保存文件
				if (!item.isFormField() && item.getName().length() > 0) {
					fileName = takeOutFileName(item.getName());
					uploadedFile = new File(path + File.separator + fileName);
					item.write(uploadedFile);
					// 更新上传文件列表
					satusBean = getStatusBean(request);
					satusBean.getUploadFileUrlList().add(fileName);
					saveStatusBean(request, satusBean);
					Thread.sleep(500);
				}
			}
		} catch (FileUploadException e) {
			e.printStackTrace();
			logger.error(e.getMessage(),e);
			uploadExceptionHandle(request, "上传文件时发生错误:" + e.getMessage());
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(),e);
			uploadExceptionHandle(request, "保存上传文件时发生错误:" + e.getMessage());
			throw e;
		}
	}

	/**
	 * 处理文件上传
	 */
	public List getFileList(HttpServletRequest request) throws ServletException, Exception{
	//public List getFileList(HttpServletRequest request) throws ServletException, IOException {
		DiskFileItemFactory factory = new DiskFileItemFactory();
		// 设置内存缓冲区，超过后写入临时文件
		factory.setSizeThreshold(10240000);
		// 设置临时文件存储位置
		factory.setRepository(new File(System.getProperty("java.io.tmpdir")));
		ServletFileUpload upload = new ServletFileUpload(factory);
		// 设置单个文件的最大上传值
		upload.setFileSizeMax(10240000);
		// 设置整个request的最大值
		upload.setSizeMax(10240000);
		List items = null;
		//增加文件长度限制判断 2012-02-22 chenzj
		int len = request.getContentLength();
		if(len>10240000){
			logger.error("上传文件不能超过10M!");
			Exception ex = new Exception("上传文件不能超过10M!");
			throw ex;
			
		}else{
		try{
		items = upload.parseRequest(request);
		
		} catch (FileUploadException e) {
			e.printStackTrace();
			logger.error(e.getMessage(),e);
			uploadExceptionHandle(request, "上传文件时发生错误:" + e.getMessage());}
		}
		
		return items;
	}

	/**
	 * 保存上传文件
	 * @param items 上传文件集合
	 * @param path 路径
	 * @param nameMap　文件名集合
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
			
			// 处理文件上传
			for (int i = 0; i < items.size(); i++) {
				item = (FileItem) items.get(i);
				// 保存文件
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
	 * 文件拷贝
	 * @param path 文件路径
	 * @param nameMap 文件名称集合
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
	 * 回应上传状态查询
	 */
	public void responseUpload(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/xml");
		response.setHeader("Cache-Control", "no-cache");
		response.setCharacterEncoding("GBK");
		response.getWriter().write("上传成功");
	}

	/**
	 * 回应上传状态查询
	 */
	public void statusQuery(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/xml");
		response.setHeader("Cache-Control", "no-cache");
		response.setCharacterEncoding("GBK");
		FileUploadStatus satusBean = getStatusBean(request);
		response.getWriter().write(satusBean.toJSon());
	}

	/**
	 * 处理取消文件上传
	 */
	public void cancelUpload(HttpServletRequest request, HttpServletResponse response) throws IOException {
		FileUploadStatus satusBean = getStatusBean(request);
		satusBean.setCancel(true);
		saveStatusBean(request, satusBean);
		statusQuery(request, response);
	}

	/**
	 * 从文件路径中取出文件名
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
	 * 删除已经上传的文件
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
	 * 删除已经上传的文件
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
	 * 删除已经上传的文件
	 */
	private void deleteUploadedFile(HttpServletRequest request) {
		// 上传路径
		String path = (String) request.getAttribute(FileUploadConstants.UPLOAD_PATH);
		FileUploadStatus satusBean = getStatusBean(request);
		File uploadedFile = null;
		int size = 0 ;
		//TODO ningliyu  处理空值
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
		satusBean.setStatus("删除已上传的文件");
		saveStatusBean(request, satusBean);
	}

	/**
	 * 上传过程中出错处理
	 */
	private void uploadExceptionHandle(HttpServletRequest request, String errMsg) throws ServletException, IOException {
		// 首先删除已经上传的文件
		deleteUploadedFile(request);
		FileUploadStatus satusBean = getStatusBean(request);
		satusBean.setStatus(errMsg);
		saveStatusBean(request, satusBean);
	}

	/**
	 * 初始化文件上传状态Bean
	 */
	private FileUploadStatus initStatusBean(HttpServletRequest request) {
		// 上传路径
		String path = (String) request.getAttribute(FileUploadConstants.UPLOAD_PATH);
		FileUploadStatus satusBean = new FileUploadStatus();
		satusBean.setStatus("正在准备处理");
		satusBean.setUploadTotalSize(request.getContentLength());
		satusBean.setProcessStartTime(System.currentTimeMillis());
		satusBean.setBaseDir(path);
		return satusBean;
	}
}
