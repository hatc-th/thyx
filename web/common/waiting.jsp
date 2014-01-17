<%@ page language="java" pageEncoding="gbk"%>
<%@ include file="/common/taglibs.jsp"%>
<%-- ************************************
 * System:    质检项目信息化管理系统
 * Function：  等待页面(如果查询、计算等操作超过系统限定时间则需要执行此页面。此页面是手动调用，非系统自动调用)
 * Author:    刘明熹
 * Copyright: 北京华安天诚科技有限公司
 * Create:    VER1.01 2010.10.01
************************************ --%>

<!DOCTYPE html
      PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
      "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="zh" lang="zh">
  <head>
    <title>等待页面</title>
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
		// 全局变量(当前页面上所有select集合)
		var selectArray;
		/**
		 * 等待开始
		 * param displayType 需要显示的模式(为noIcon不显示动态等待图片,空的时候相反)
		 */
		function waitingStart(displayType){
			// 获取当前页面上的所有select选择框
			if (navigator.appName != "Netscape"){
	            if (document.all) {  
	                selectArray = document.getElementsByTagName("select");
	            } else if (eval("frameMain")){
	                selectArray = contentDocument.getElementsByTagName("select"); 
	            }
	        }
	        // 将刚刚获取的选择框隐藏
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
		 * 等待结束
		 */
		function waitingStop(){
			// 将等待时隐藏的选择框显示出来
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
    <%-- lightBoxDIV开始 --%>
	<div id="imageDiv" style="z-index: 100; top: 236px; left: 311px; position: absolute; display: none;">
		<img id="msg" title="" style="margin-left: 0px;" src="<c:url value="/images/loading.gif" />" />
	</div>
	<div id="bottomDiv" class="lightBoxCSS" style="filter: alpha(opacity = 1);"></div>
	<%-- lightBoxDIV结束 --%>
  </body>
</html>
