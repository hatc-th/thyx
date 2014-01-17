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
        	// ����ʱ��ʱ��
        	var index_local_Second = 15;
        	// ��ȡcookie���isAlert������ֵΪno���򲻽��е���ʱ�رմ���
        	// TODO TH����Ҫ
        	//var isAlert = getCookie("isAlert"); 
        	var isAlert = "no";
        	function closeLocalWindow(){
        		window.close();
        	}
        	
        	// ����ʱ����
        	function countDown(){
        		if (index_local_Second <= 0) {
        			closeLocalWindow();
        		}
		        document.getElementById("second").value = index_local_Second--;
        		// ����ʱ
            	window.setTimeout("countDown();", 1000);
        	}
        	
        	function openLoginWindow() {
        		var width = screen.availWidth;      // ��Ļ���ÿ��
   				var height = screen.availHeight;    // ��Ļ���ø߶�
        		newCustWin('<c:url value="/loginAction.do?method=index"/>', newWinName,width,height,'','','yyyyyy');
        		// ��ȡҳ���������ĸ߶�
        		var clientHeight = document.body.clientHeight;
        		// ��ȡҳ���������Ŀ��
        		var clientWidth = document.body.clientWidth;
        		// ��ʾ�����table�ĸ߶�
        		var tableHeight = 250;
        		// ��ʾ�����table�Ŀ��
        		var tableWidth = clientWidth;
        		document.getElementById("alertTbl").style.width = tableWidth;
        		document.getElementById("alertTbl").style.height = tableHeight;
        		document.getElementById("alertTbl").style.marginTop = (clientHeight - 300)/2;
        		// �Ե�ǰʱ��ĺ�������Ϊ�´�����
	            var newWinName = new Date().getTime();
	            
	            // WEB ������ļ����ƣ�IE Ϊ Microsoft Internet Explorer, ���������(eg. Firefox) Ϊ Netscape
	            var browerName = navigator.appName;
	            // ���ιرմ��ڵ�ϵͳ��ʾ
	            if (browerName.indexOf("Microsoft") != -1 ) {
	                // ���ø�������
	                window.opener = "openerIndex";
	            } else {
	            	//window.location = '<c:url value="/index_info.html" />';
	            }
	            
	           
        	}
        	
        	// ҳ���ʼ��
        	function initPage(){
        		// ��ǰ�����Ƿ���ر�
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
        	
        	// ���� ����ʾ�رո����� chenzj 2013-01-04
        	function initPageNew(){
                openLoginWindow();
                window.opener = null;
	            window.open('','_self');
	            window.close();
        
        	}
        	
        	/****** �û��ر��������ʱ��д��cookie ******/
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
					������ǵ�һ��ʹ�ã����������ߡ�-->������������ֹ����-->���رյ���������ֹ���򡿡�<br />
					���������ղء�-->����ӵ��ղؼС�����ϵͳ��ӵ��ղؼС�<br />
					<input type="text" id="second" style="border: none; width: 23px; font-family: Arial, Fixedsys; font-size: 19px; text-align: center;" value="" />����Զ��رձ�ҳ�档<br />
	    		</td>
	    	</tr>
	    	<tr>
	    		<td style="text-align: center; font-family: Arial, Fixedsys; font-size: 19px; height: 50px;">
					<input type="checkbox" id="isDisplay"     value="0" />������ʾ��ҳ
					<input type="button" class="inputButtonCtrl" value="��&nbsp;&nbsp;��" onclick="closeLocalWindow();" />
	    		</td>
	    	</tr>
		</table>
    </body>
</html>
