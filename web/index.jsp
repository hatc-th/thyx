<%@ page language="java" pageEncoding="gbk"%>
<%@ include file="common/taglibs.jsp"%>

<!DOCTYPE html
      PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
      "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="zh" lang="zh">

    <head>
        <fmt:setLocale value="zh_CN" />
        <fmt:bundle basename="Resources">
            <fmt:message key="000000" var="webDisplayName" scope="application" />
        </fmt:bundle>
        <title><c:out value="${webDisplayName}"/></title>
        <meta http-equiv="Content-Type" content="text/html; charset=gbk" />
        <meta http-equiv="content-language" content="zh" />
        <meta http-equiv="content-script-type" content="text/javascript" />
        <meta name="robots" content="none" />
        <meta name="author" content="HuangCheng" />
        <meta name="copyright" content="2010, Beijing HATC Technology Co., LTD" />
        <meta name="description" content="index for TTIMS" />
        <link rel="icon" type="image/x-icon" href="<c:url value="/images/default.ico" />" />
        <link rel="shortcut icon" type="image/x-icon" href="<c:url value="/images/default.ico" />" />
        <link type="text/css" rel="stylesheet" href="<c:url value="/skin/p1/css/platform.css" />" title="default" />
        <script src="<c:url value="/js/platform.js" />"></script>
        <script src="<c:url value="/js/popWindows.js" />"></script>
        <script>
        	// 倒计时的时间
        	var index_local_Second = 15;
        	// 读取cookie如果isAlert参数的值为no，则不进行倒计时关闭窗口
        	// TODO TH不需要
        	//var isAlert = getCookie("isAlert"); 
        	var isAlert = "no";
        	function closeLocalWindow(){
        		window.close();
        	}
        	
        	// 倒计时函数
        	function countDown(){
        		if (index_local_Second <= 0) {
        			closeLocalWindow();
        		}
		        document.getElementById("second").value = index_local_Second--;
        		// 倒计时
            	window.setTimeout("countDown();", 1000);
        	}
        	
        	function openLoginWindow() {
        		var width = screen.availWidth;      // 屏幕可用宽度
   				var height = screen.availHeight;    // 屏幕可用高度
        		newCustWin('<c:url value="/loginAction.do?method=index"/>', newWinName,width,height,'','','yyyyyy');
        		// 获取页面可视区域的高度
        		var clientHeight = document.body.clientHeight;
        		// 获取页面可视区域的宽度
        		var clientWidth = document.body.clientWidth;
        		// 显示区域的table的高度
        		var tableHeight = 250;
        		// 显示区域的table的宽度
        		var tableWidth = clientWidth;
        		document.getElementById("alertTbl").style.width = tableWidth;
        		document.getElementById("alertTbl").style.height = tableHeight;
        		document.getElementById("alertTbl").style.marginTop = (clientHeight - 300)/2;
        		// 以当前时间的毫秒数作为新窗口名
	            var newWinName = new Date().getTime();
	            
	            // WEB 浏览器的简单名称：IE 为 Microsoft Internet Explorer, 其他浏览器(eg. Firefox) 为 Netscape
	            var browerName = navigator.appName;
	            // 屏蔽关闭窗口的系统提示
	            if (browerName.indexOf("Microsoft") != -1 ) {
	                // 设置父窗口名
	                window.opener = "openerIndex";
	            } else {
	            	//window.location = '<c:url value="/index_info.html" />';
	            }
	            
	           
        	}
        	
        	// 页面初始化
        	function initPage(){
        		// 当前窗口是否需关闭
        		var isClose = "${requestScope.isClose}";
        		if(isClose == "close"){
        			window.close();
        		}else{
        		
        			if(isAlert != "no") {
        				document.getElementById("alertTbl").style.display = "block";
        				countDown();
        				openLoginWindow();
        			} else {
        				setCookie("isAlert", "no", 315360000);
        				openLoginWindow();
        				closeLocalWindow();
        			}
        		}
        	}
        	
        	// 增加 不提示关闭父窗口 chenzj 2013-01-04
        	function initPageNew(){
                openLoginWindow();
                window.opener = null;
	            window.open('','_self');
	            window.close();
        
        	}
        	
        	/****** 用户关闭浏览器的时候写入cookie ******/
        	window.onbeforeunload = function() {
            	var cboIsDisplay = document.getElementById("isDisplay");
        		if(cboIsDisplay.checked){
        			setCookie("isAlert", "no", 315360000);
        		}
			}
        </script>
    </head>

    <body onload="initPageNew();" style="background-color: #E9EDF0;">
	    <table id="alertTbl" style="display: none; height: auto; margin: auto; width: 1026px; vertical-align: middle;">
	    	<tr>	
	    		<td style="height: 150px; text-align: center;">
	    			<img style="width: 1016px; height: 96px;" src="<c:url value='skin/p1/images/homepage/homepage_bgtop.png' />" />
	    		</td>
	    	</tr>
	    	<tr>
	    		<td style="text-align: center; font-family: Arial, Fixedsys; font-size: 19px; height: 50px;">
					如果您是第一次使用，请点击【工具】-->【弹出窗口阻止程序】-->【关闭弹出窗口阻止程序】。<br />
					建议点击【收藏】-->【添加到收藏夹】将本系统添加到收藏夹。<br />
					<input type="text" id="second" style="border: none; width: 23px; font-family: Arial, Fixedsys; font-size: 19px; text-align: center;" value="" />秒后将自动关闭本页面。<br />
	    		</td>
	    	</tr>
	    	<tr>
	    		<td style="text-align: center; font-family: Arial, Fixedsys; font-size: 19px; height: 50px;">
					<input type="checkbox" id="isDisplay"     value="0" />不再显示本页
					<input type="button" class="inputButtonCtrl" value="关&nbsp;&nbsp;闭" onclick="closeLocalWindow();" />
	    		</td>
	    	</tr>
		</table>
    </body>
</html>
