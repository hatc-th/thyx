<%@ page language="java" pageEncoding="gbk"%>
<%@ include file="/common/taglibs.jsp"%>
<%-- ************************************
 * System:    �ʼ���Ŀ��Ϣ������ϵͳ
 * Function��  �ȴ�ҳ��(�����ѯ������Ȳ�������ϵͳ�޶�ʱ������Ҫִ�д�ҳ�档��ҳ�����ֶ����ã���ϵͳ�Զ�����)
 * Author:    ������
 * Copyright: ����������ϿƼ����޹�˾
 * Create:    VER1.01 2010.10.01
************************************ --%>

<!DOCTYPE html
      PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
      "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="zh" lang="zh">
  <head>
    <title>�ȴ�ҳ��</title>
	<style type="text/css">
		.lightBoxCSS {
		display: none;
		position: absolute;
		top: 5px;
		left: 5px;
		width: 825px;
		height: 543px;
		background-color: black;
		z-index: 99;
		-moz-opacity: 0.01;
		opacity: .1;
		filter: alpha(opacity = 1);
	}
	</style>
	<script type="text/javascript">
		// ȫ�ֱ���(��ǰҳ��������select����)
		var selectArray;
		/**
		 * �ȴ���ʼ
		 * param displayType ��Ҫ��ʾ��ģʽ(ΪnoIcon����ʾ��̬�ȴ�ͼƬ,�յ�ʱ���෴)
		 */
		function waitingStart(displayType){
			// ��ȡ��ǰҳ���ϵ�����selectѡ���
			if (navigator.appName != "Netscape"){
	            if (document.all) {  
	                selectArray = document.getElementsByTagName("select");
	            } else if (eval("frameMain")){
	                selectArray = contentDocument.getElementsByTagName("select"); 
	            }
	        }
	        // ���ոջ�ȡ��ѡ�������
	        if (null != selectArray && selectArray.length > 0){
	        	for(var i = 0, j = selectArray.length; i < j; i++){
		        	selectArray[i].style.visibility = "hidden";
		        }
	        }
			document.getElementById("bottomDiv").style.display = "block";
			if(displayType != "noIcon"){
				document.getElementById("imageDiv").style.display = "block";
				document.getElementById("imageDiv").focus();
			}
		}
		
		/**
		 * �ȴ�����
		 */
		function waitingStop(){
			// ���ȴ�ʱ���ص�ѡ�����ʾ����
	        if (null != selectArray && selectArray.length > 0){
	        	for(var i = 0, j = selectArray.length; i < j; i++){
		        	selectArray[i].style.visibility = "visible";
		        }
	        }
			document.getElementById("bottomDiv").style.display = "none";
			document.getElementById("imageDiv").style.display = "none";
		}
	</script>
	</head>
  <body>
    <%-- lightBoxDIV��ʼ --%>
	<div id="imageDiv" style="z-index: 100; top: 236px; left: 311px; position: absolute; display: none;">
		<img id="msg" title="" style="margin-left: 0px;" src="<c:url value="/images/loading.gif" />" />
	</div>
	<div id="bottomDiv" class="lightBoxCSS" style="filter: alpha(opacity = 1);"></div>
	<%-- lightBoxDIV���� --%>
  </body>
</html>
