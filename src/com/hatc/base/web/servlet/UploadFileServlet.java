package com.hatc.base.web.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.hatc.base.upload.UploadFileService;

/**
 * <p>
 * Title: 文件上传服务
 * </p>
 * 
 * <p>
 * Description: 主要作用是文件上传或上传状态查询服务。
 * </p>
 * 
 */
public class UploadFileServlet  extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet{

	private static final long serialVersionUID = 9081810398558125990L;

	/**
	 *@see javax.servlet.http.HttpServlet#service(javax.servlet.ServletRequest, javax.servlet.ServletResponse)
	 */
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		UploadFileService serivce = new UploadFileService();
		String keys = request.getRemoteAddr();
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		if (isMultipart) {
			serivce.processFileUpload(request, response);
		} else {
			request.setCharacterEncoding("GB2312");			
			serivce.responseStatusQuery(keys, response);			
		}
	}
}
