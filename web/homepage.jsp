<%@ page language="java" pageEncoding="gbk"%>
<%@ include file="common/taglibs.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" 
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="zh" lang="zh">

    <head>
        <title><c:out value="${webDisplayName}" />&nbsp;<c:out value="${systemSecretLevel}" /></title>
        <meta http-equiv="Content-Type" content="text/html; charset=gbk" />
        <meta http-equiv="content-language" content="zh" />
        <meta http-equiv="content-script-type" content="text/javascript" />
        <meta name="description" content="homepage for XTMS" />
        <meta name="robots" content="none" />
        <meta name="author" content="HuangCheng" />
        <meta name="copyright" content="2010, Beijing HATC Co., LTD" />
        <link type="text/css" rel="stylesheet" href="<c:url value="/skin/p1/css/platform.css" />" title="default" />
        <link type="text/css" rel="stylesheet" href="<c:url value="/skin/p1/css/homepage.css" />" title="default" />
        <link rel="shortcut icon" type="image/x-icon" href="<c:url value="/images/default.ico" />" />
        <script src="<c:url value="/js/popWindows.js" />"></script>
        <script src="<c:url value="/js/ajaxCommon.js" />"></script>
        <script src="<c:url value="/js/prototype.js"/>"></script>
        <script>
        var alreadyLoggedout = false;
            var curLeaf; // 历史菜单树控制对象记录标识
            var curLeafBgcolor; // 历史菜单树控制对象背景色
            var selectArray = new Array(); // 暂存当前页面所有被隐藏的 select 元素
            var switchRoleFlag = "0"; // 标识是否需要清空当前登录用户的seeesionId
			var msecs=100; //改变时间得到不同的闪烁间隔；
			var counter=0;
            
            /****** 用户关闭浏览器的时候退出系统 ******/
            window.onbeforeunload = function() {
            	// 获取异常退出标志
            	var logoutMark = document.getElementById("logoutMark").value;
            	if(switchRoleFlag == "1"){
            		if(alreadyLoggedout == true)
            			return;
            		window.location = "<c:url value='/userAction.do?method=logoutSystem' />";
            		if(logoutMark != 1) {
            		    //ningliyu 2011-11-23
            			//alert("退出系统操作成功！");
            		}
            	}
            }
					
            /****** 重新登录 ******/
            function reLogin() {
                if (confirm('您确认要重新登录？') == true) {
                	// 设置异常退出标志
                	document.getElementById("logoutMark").value = "1";
                    window.location = '<c:url value="/loginAction.do?method=restLogin" />';
                }
                return false;
            }

            /****** 角色切换 ******/
            function chgRole(disp) {
	            if(disp=="show")
	            {
	            	counter = 1;
	            }
	            else
	            {
	            	counter = 2;
	            }
			}
            
            function gotoRole(url) {
                if (url != "#") {
                	switchRoleFlag = "0";
                	// 设置异常退出标志
                	document.getElementById("logoutMark").value = "1";
                    document.location = url;
                }
            }
            
            /****** 修改密码 ******/
            
            /****** 帮助 ******/
            function help(){
            	var url = "<c:url value='/help/___index.html' />";
            	var winName = "helpDoc";
            	var width = screen.availWidth;      // 屏幕可用宽度
   				var height = screen.availHeight;    // 屏幕可用高度
            	var screenX = "";
            	var screenY = "";
   				var ctrlStr = "ynnny";
            	// 控制参数处理
			    function getCtrlValue(ctrlStr, n) {
			        var ctrlValue = ctrlStr.charAt(n);
			        var pattern = /[1yY]/;
			        if (ctrlValue == '' || !pattern.test(ctrlValue)) {
			            ctrlValue = 'no';    // 默认不显示
			        } else {
			            ctrlValue = 'yes';
			        }
			        return ctrlValue;
			    }
			
			    // 默认窗口宽度
			    if (width == null || width == '') {
			        width = 240;
			    }
			
			    // 默认窗口高度
			    if (height == null || height == '') {
			        height = 320;
			    }
			
			    var aw = screen.availWidth - 30;     // 屏幕可用宽度
			    var ah = screen.availHeight - 50;    // 屏幕可用高度
			
			    // 默认窗口水平居中
			    if (screenX == null || screenX == '') {
			        if (aw > width) {
			            screenX = (aw - width)/2;
			        } else {
			            screenX = 0;
			        }
			    } else {
			        if (aw <= width) {
			            screenX = 0;
			        }
			    }
			
			    // 默认窗口垂直居中
			    if (screenY == null || screenY == '') {
			        if (ah > height) {
			            screenY = (ah - height)/2;
			        } else {
			            screenY = 0;
			        }
			    } else {
			        if (ah <= height) {
			            screenY = 0;
			        }
			    }
			
			    // 默认窗口尺寸不可调，无滚动条/菜单栏/工具栏/状态栏
			    if (ctrlStr == null || ctrlStr == '') {
			        ctrlStr = 'nnnnn';
			    }
			
			    var resize = getCtrlValue(ctrlStr, 0);        // 窗口尺寸可调参数
			    var scrollbars = getCtrlValue(ctrlStr, 1);    // 滚动条参数
			    var menubar = getCtrlValue(ctrlStr, 2);       // 菜单栏参数
			    var toolbar = getCtrlValue(ctrlStr, 3);       // 工具栏参数
			    var status = getCtrlValue(ctrlStr, 4);        // 状态栏参数
			    var features = 'width=' + width
			            + ',height=' + height
			            + ',left=' + screenX             // 窗口X坐标 for IE
			            + ',top=' + screenY              // 窗口Y坐标 for IE
			            + ',screenX=' + screenX          // 窗口X坐标 for Netscape
			            + ',screenY=' + screenY          // 窗口Y坐标 for Netscape
			            + ',resizable=' + resize         // 窗口尺寸可调参数
			            + ',scrollbars=' + scrollbars    // 滚动条参数
			            + ',menubar=' + menubar          // 菜单栏参数
			            + ',toolbar=' + toolbar          // 工具栏参数
			            + ',status=' + status            // 状态栏参数
			            + '';
			    winName = winName === undefined || winName === null || winName == '' ? Date.parse(new Date()) : winName;
			    var win = window.open(url, winName, features);
            	win.focus();
            }
            
            /****** 退出 ******/
            function logout() {
                if (confirm('您确认要退出<c:out value="${webDisplayName}" />？') == true) {
                	// 设置异常退出标志
                	alreadyLoggedout = true;
                	document.getElementById("logoutMark").value = "1";
                    window.location = '<c:url value="/userAction.do?method=logoutSystem&logoutMark=1" />';
                    //增加 退出后直接关闭窗口 chenzj 2012-12-28
                    window.close();
                }
            }
            
            function logoutAlert() {
                var logoutMark = document.getElementById("logoutMark").value;
                if (logoutMark == 0) {
                    alert('您已成功退出<c:out value="${webDisplayName}" />！');
                }
                document.getElementById("logoutMark").value = 0;
            }
            
            /****** 消息提醒 ******/
            <%--function setMsgAlert(isMuted) {
                if (isMuted == 'mute') {
                    // 静音
                    sendRequest('<c:url value="/msgListAction.do?method=setMsgAlert&isMuted=1" />');                   
                } else if (isMuted == 'unmute') {
                    // 取消静音
                    sendRequest('<c:url value="/msgListAction.do?method=setMsgAlert&isMuted=0" />');            
                }
            }
            
            function doAjax() {
                var rrt = req.responseText;
                if (rrt == 1) {
                    document.getElementById("setMsgAlert").innerHTML = '<a href="#" onclick="setMsgAlert(\'unmute\');"><img id="alerticon" name="alerticon" src="<c:url value='/skin/p1/images/homepage/homepage_soundClose.gif' />" title="点击打开声音" /></a>'
                } else if (rrt == 0) {
                    document.getElementById("setMsgAlert").innerHTML = '<a href="#" onclick="setMsgAlert(\'mute\');"><img id="alerticon" name="alerticon" src="<c:url value='/skin/p1/images/homepage/homepage_soundOpen.gif' />" title="点击关闭声音" /></a>'
                }
            }
            
            function chgMsg() {
                document.msgicon.src = '<c:url value="/images/msgempty.gif" />';
            }
            --%>
            /****** 软件下载 ******/
            function isDownClient(type) {
                var tempiframe = document.getElementById('downiframe')
                if (navigator.platform == 'Win32') {
                    tempiframe.src = '<c:url value="/userAction.do?method=downloadClient&appVersion=windows&type=" />' + type;
                } else {
                    tempiframe.src = '<c:url value="/userAction.do?method=downloadClient&appVersion=linux&type=" />' + type;
                }
            }
            
            function isDownJDK() {
                var tempiframe = document.getElementById('downiframe')
                if (navigator.platform == 'Win32') {
                    tempiframe.src = '<c:url value="/userAction.do?method=downloadJDK&appVersion=windows" />';
                } else {
                    tempiframe.src = '<c:url value="/userAction.do?method=downloadJDK&appVersion=linux" />';
                }
            }
            
            /****** 设置默认用户角色 ******/
            function setDefaultRole() {
                var ajaxReq = new Ajax.Request(
                    '<c:url value="/userAction.do?method=setDefaultRole"/>',
                    {
                        method:'get',
                        onSuccess: function(transport) {
                            defaultRoleSuccess(transport.responseXML);
                        },
                        onFailure: function() {
                            alert('设为默认角色过程中出现错误...');
                        }
                    }
                );
            }
            
            function defaultRoleSuccess(response) {
                var result = response.getElementsByTagName("result")[0].firstChild.data;
                if (result == '0') {
                    alert('当前角色是已经是用户的默认角色！');
                } else if (result == '1') {
                    alert('成功将当前角色设置为用户默认角色！');
                } else {
                    alert('设置用户默认角色中出现错误！');
                }
            }
            
            /****** 菜单树控制操作 ******/
            /* 一级菜单树显示控制 */
            function ctrlPrim(curObj, id, bool) {
                var obj = document.getElementById('primNode' + id);
                if (bool) {
	                if (obj.style.display != 'block') {
	                    // 显示子菜单
	                    obj.style.display = 'block';
	                } else {
	                    obj.style.display = 'none';
	                }
                }
                if (!bool) {
	                var dtArray = document.getElementsByTagName('dt');
	                for (var i=0;i<dtArray.length;i++) {
	                	if (dtArray[i].style.backgroundImage != 'url(<c:url value="/skin/p1/images/menu/primNodeA.gif" />)') {
	                		dtArray[i].style.backgroundImage = 'url(<c:url value="/skin/p1/images/menu/primNodeA.gif" />)';
	                	}
	                }
                }
                if (curObj.style.backgroundImage == 'url(<c:url value="/skin/p1/images/menu/primNodeB.gif" />)') {
                	curObj.style.backgroundImage = 'url(<c:url value="/skin/p1/images/menu/primNodeA.gif" />)';
                } else {
               		curObj.style.backgroundImage = 'url(<c:url value="/skin/p1/images/menu/primNodeB.gif" />)';
                }
            }
            
            /* 二级菜单树显示控制 */
            function ctrlSec(curObj, id) {
                var obj = document.getElementById('secNode' + id);
                if (obj.style.display != 'block') {
                    // 显示子菜单
                    obj.style.display = 'block';
                    curObj.style.backgroundImage = 'url(<c:url value="/skin/p1/images/menu/secNodeB.gif" />)';
                } else {
                    obj.style.display = 'none';
                    curObj.style.backgroundImage = 'url(<c:url value="/skin/p1/images/menu/secNodeA.gif" />)';
                }
            }
            
            /* 当前菜单选择高亮标识 */
            function ctrlLeaf(curObj) {
                /* 历史高亮菜单条背景色恢复 */
                if (curLeaf != null) {
                    curLeaf.style.backgroundColor = curLeafBgcolor;
                }
                curLeaf = curObj;
                curLeafBgcolor = curObj.style.backgroundColor;
                if (curObj.style.backgroundColor != '#3997ce') {
                    /* 当前菜单条背景色高亮 */
                    curObj.style.backgroundColor = '#3997ce';
                }
            }
            /** 气象信息  通航不需要
            function sendWeatheActionS() {
               var weatherInfo = document.getElementById('weatherInfo');
               weatherInfo.src = '<c:url value="/weatherAction.do?method=ajaxStationsWeather"/>';
            }*/
            
            /**
             * 重新计算窗口大小
             */
             function resizeWindow(){
             	var divHomepage = document.getElementById("divHomepage");
	                if (document.body.clientWidth < 1016) {
	                    divHomepage.style.width = document.body.clientWidth + "px";
	                } else {
	                    divHomepage.style.width = "1016px";
	                }
				if (document.body.clientHeight < 660) {
	                divHomepage.style.height = document.body.clientHeight + "px";
	            } else {
	                divHomepage.style.height = "660px";
	            }
             }
            
            /**
             * 页面初始化
             */
             function pageInitialize(){
             	switchRoleFlag = "1";
             	resizeWindow();
             }
             
             window.onresize = function() {
	            resizeWindow();
			}
			
			function soccerOnload(){
				setTimeout("blink()", msecs)
			}
			function blink(){
				var disp;
				if (counter == 1 || counter == 2)
				{
					if(counter == 1)
					{
						disp = "show";
					}
	                var obj = document.getElementById('chgRoleUL');
	                var iframeSelect = null;
	                if (navigator.appName != 'Netscape') {
	                    if (document.all) {
	                        iframeSelect = document.frames['iframe'].document.getElementsByTagName('select');
	                    } else if (eval('frameMain')) {
	                        iframeSelect = eval('iframe').contentDocument.getElementsByTagName('select');
	                    }
	                }
	
	                if (disp == 'show') {
	                    if (iframeSelect && iframeSelect.length > 0) {
	                        for (var i = 0;i < iframeSelect.length;i++) {
	                            if (iframeSelect[i].style.visibility != 'hidden') {
	                                iframeSelect[i].style.visibility = 'hidden';
	                                selectArray[selectArray.length] = iframeSelect[i];
	                            }
	                        }
	                    }
	                    obj.style.display = 'block';
	                    obj.focus();
	                } 
	               else {
	                    if (selectArray && selectArray.length > 0) {
	                        for (var i = 0;i < selectArray.length;i++) {                        
	                            if (iframeSelect[i].style.visibility != 'visible') {
	                                iframeSelect[i].style.visibility = 'visible';
	                                }
	                            selectArray[i].style.visibility = 'visible';
	                        }
	                    }
	                    selectArray = new Array();
	                    obj.style.display = 'none';
	                }
					counter=0;
				}
				setTimeout("blink()", msecs)
			}
			soccerOnload()  
			
			
			//自适应页面动态设置 div 宽高
			function setCss(){
				var winheight = document.body.clientHeight;
				var winwidth = document.body.clientWidth;
				document.getElementById("divHpmain").style.width=winwidth-223;
				document.getElementById("divHpmain").style.height=winheight-131;
				document.getElementById("divHpnav").style.height=winheight-131;
				document.getElementById("divMenu").style.height=winheight-138;
				
			}
        </script>
    </head>

    <%-- onunload="logoutAlert();" --%>
    <body onclick="chgRole('hide');" onload="setCss();" onresize="setCss()">
    	<%-- 系统退出标志(开始) --%>
    	<input type="hidden" id="logoutMark" name="logoutMark" value="0" />
    	<%-- 系统退出标志(结束) --%>
        <div id="divHomepage"><%-- 上部框架 --%>
            <div id="divHptop">
            <div id="divtopleft" style = "font-size:30px;color:#fff; font-weight:bold;">
            	<br/>&nbsp;&nbsp;通用航空运行管理和服务保障系统
	    	</div>
	    	<div id="divtoprepeat">
	    	</div>
	    	<div id="divtopright">
	    	</div>
	    	<div id="divtool">
                <%-- 上部框架 - 1/3 功能模块 --%>
                <ul id="hpTool">
                    <li id="homepage">
                        <a id="defaultFirstPage" title="首页" href="welcome.jsp"  target="iframe"></a>
                    </li>
                    <li id="reLogin">
                        <a href="#" title="重新登录" onclick="reLogin();" ></a>
                    </li>
                    <li id="chgPwd">
                        <a href="#" title="修改密码" onclick="newHpmainWin('<c:url value="/userAction.do?method=setPwd" />', 'updatePwd');"></a>
                    </li>
                    <!-- <li id="chgRole" title="切换角色" onclick="chgRole('show');" onmouseover="chgRole('show');" onmouseout="chgRole('hide');">
                        <a href="#"></a>
                    </li> -->
                    <li id="helpInfo">
                        <a href="#" title="查看帮助文档" onclick="help();"></a>
                    </li>
                    <li id="logout">
                        <a href="#" title="退出系统" onclick="logout();"></a>
                    </li>
                </ul>
               </div>
                <%-- 上部框架 - 2/3 首页信息 --%>
                <div id="divstatus">
                <ul id="hpMsg">
                    <li style="width: 2%;"></li>

                    <li style="width: 3%;">
                        <a href="#" id="role">
                        </a>
                    </li>
                    <li style="width: 22%; color: #E5E8ED;">
                        欢迎您：
                        <span style="color:#FF9000"><c:out value="${session_user.name}" /></span>
                        &nbsp;
                        <c:out value="${session_user.dept_name}" />
                        &nbsp;
                        <c:out value="${session_user_role.roleName}" />
                    </li>
                    <li id="setMsgAlert" style="width: 5%;">
                        <%--<a href="#" onclick="setMsgAlert('mute');">
                        	<img id="alerticon" name="alerticon" src="<c:url value="/skin/p1/images/homepage/homepage_soundOpen.gif" />" title="点击关闭声音" />
                        </a>--%>
                    </li>
                    <li style="width: 5%;">
                        <%-- <iframe id="msgAlert" name="msgAlert" frameborder="0" scrolling="no" style="margin: 0px; width: 24px; height: 20px;" src="<c:url value="/message/msgWav.jsp" />"></iframe>--%>
                    </li>
                    <li style="width: 3%;">
                        <%-- 不保留  <a href="#" onclick="sendWeatheActionS();" id="alertweather" title="气象信息"></a>--%>
                    </li>
                    <li style="width: 37%;">
                        <%-- 天气预报预留   不保留 --%>
                        <%-- <iframe id="weatherInfo" name="weatherInfo" frameborder="0" style="margin: 0px; width: 95%; height: 20px;" scrolling="no" src="<c:url value="/weatherAction.do?method=ajaxStationsWeather"/>"></iframe> --%>
                    </li>
                    <li style="width: 3%;">
                    </li>
                    <li style="width: 3%;" >
                        <a href="#" id="alertdate"  title="日期">
                        </a>
                    </li>
                    <li id="hpDate" style="width: 15%; color: #E5E8ED;">
                        <c:out value="${hpDate}" />
                    </li>
                </ul>
                </div>
            </div>
            <%-- 左部框架 --%>
            <div id="divHpnav">
                <%-- 左部框架 - 目录列表 --%>
                <div id="divMenu">
                    <c:choose>
                        <%-- 图标方式显示目录树 --%>
                        <c:when test="${menuStyle == '1'}">
                            <dl>
                                <c:forEach var="menus" items="${functions}">
                                    <dt onclick="ctrlPrim(this, '<c:out value="${menus.sid}"/>', false);">
                                        <a href="<c:url value="/loginAction.do?method=menuIcoHref&menuFunctionId=${menus.function.orderId}" />" target="iframe">
                                            <c:out value="${menus.function.functionName}" />
                                        </a>
                                    </dt>
                                </c:forEach>
                            </dl>
                        </c:when>
                        <%-- 树型 + 图标方式显示目录树 --%>
                        <c:when test="${menuStyle == '2'}">
                            <dl>
                                <c:forEach var="menus" items="${functions}">
                                    <dt onclick="ctrlPrim(this, '<c:out value="${menus.sid}"/>', true);">
                                        <a href="<c:url value="/loginAction.do?method=menuIcoHref&menuFunctionId=${menus.function.orderId}&firstPageFunctionId=${menus.function.functionId}" />" target="iframe">
                                            <c:out value="${menus.function.functionName}" />
                                        </a>
                                    </dt>
                                    <dd id="primNode<c:out value='${menus.sid}' />">
                                        <dl>
                                            <c:forEach var="menusOne" items="${menus.list}">
                                                <c:choose>
                                                    <c:when test="${menusOne.list != null}">
                                                        <dt class="secNodes" onclick="ctrlSec(this, '<c:out value="${menusOne.sid}" />');">
                                                            <a href="<c:url value="/loginAction.do?method=menuIcoHref&menuFunctionId=${menusOne.function.orderId}&firstPageFunctionId=${menusOne.function.functionId}" />" target="iframe">
                                                                <c:out value="${menusOne.function.functionName}" />
                                                            </a>
                                                        </dt>
                                                        <dd id="secNode<c:out value='${menusOne.sid}' />">
                                                            <dl>
                                                                <c:forEach var="menusTwo" items="${menusOne.list}">
                                                                    <dt onclick="ctrlLeaf(this);">
                                                                    	<c:choose>
                                                                    		<c:when test="${(not empty menusTwo.function.functionDescription) and (menusTwo.function.functionDescription ne '#')}">
                                                                    			<a href="<c:url value="${menusTwo.function.functionDescription}&menuFunctionId=${menusTwo.function.orderId}&firstPageFunctionId=${menusTwo.function.functionId}" />" target="iframe">
		                                                                            <c:out value="${menusTwo.function.functionName}" />
		                                                                        </a>
                                                                    		</c:when>
                                                                    		<c:otherwise>
                                                                    			<a href="<c:url value="/loginAction.do?method=menuIcoHref&menuFunctionId=${menusTwo.function.orderId}&firstPageFunctionId=${menusTwo.function.functionId}" />" target="iframe">
		                                                                            <c:out value="${menusTwo.function.functionName}" />
		                                                                        </a>
                                                                    		</c:otherwise>
                                                                    	</c:choose>
                                                                    </dt>
                                                                </c:forEach>
                                                            </dl>
                                                        </dd>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <dt class="secNode" onclick="ctrlLeaf(this);">
                                                        	<c:choose>
                                                        		<c:when test="${(not empty menusOne.function.functionDescription) and (menusOne.function.functionDescription ne '#')}">
                                                        			<a href="<c:url value="${menusOne.function.functionDescription}&menuFunctionId=${menusOne.function.orderId}&firstPageFunctionId=${menusOne.function.functionId}" />" target="iframe">
		                                                           		<c:out value="${menusOne.function.functionName}" />
		                                                            </a>
                                                        		</c:when>
                                                        		<c:otherwise>
                                                        			<a href="<c:url value="/loginAction.do?method=menuIcoHref&menuFunctionId=${menusOne.function.orderId}&firstPageFunctionId=${menusOne.function.functionId}" />" target="iframe">
		                                                           		<c:out value="${menusOne.function.functionName}" />
		                                                            </a>
                                                        		</c:otherwise>
                                                        	</c:choose>
                                                        </dt>
                                                    </c:otherwise>
                                                </c:choose>
                                            </c:forEach>
                                        </dl>
                                    </dd>
                                </c:forEach>
                            </dl>
                        </c:when>
                        <c:otherwise>
                            <dl>
                                <c:forEach var="menus" items="${functions}">
                                    <dt onclick="ctrlPrim(this, '<c:out value="${menus.sid}"/>', true)">
                                        <a href="#">
                                            <c:out value="${menus.function.functionName}" />
                                        </a>
                                    </dt>
                                    <dd id="primNode<c:out value='${menus.sid}' />">
                                        <dl>
                                            <c:forEach var="menusOne" items="${menus.list}">
                                                <c:choose>
                                                    <c:when test="${menusOne.list != null}">
                                                        <dt class="secNodes" onclick="ctrlSec(this, '<c:out value="${menusOne.sid}"/>');">
                                                            <a href="#">
                                                                <c:out value="${menusOne.function.functionName}" />
                                                            </a>
                                                        </dt>
                                                        <dd id="secNode<c:out value="${menusOne.sid}"/>">
                                                            <dl>
                                                                <c:forEach var="menusTwo" items="${menusOne.list}">
                                                                    <dt onclick="ctrlLeaf(this);">
                                                                        <a href="<c:url value="${menusTwo.function.functionDescription}&menuFunctionId=${menusTwo.function.orderId}&firstPageFunctionId=${menusTwo.function.functionId}" />" target="iframe">
                                                                            <c:out value="${menusTwo.function.functionName}" />
                                                                        </a>
                                                                    </dt>
                                                                </c:forEach>
                                                            </dl>
                                                        </dd>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <dt class="secNode" onclick="ctrlLeaf(this);">
                                                            <a href="<c:url value="${menusOne.function.functionDescription}&menuFunctionId=${menusOne.function.orderId}&firstPageFunctionId=${menusOne.function.functionId}" />" target="iframe">
                                                                <c:out value="${menusOne.function.functionName}" />
                                                            </a>
                                                        </dt>
                                                    </c:otherwise>
                                                </c:choose>
                                            </c:forEach>
                                        </dl>
                                    </dd>
                                </c:forEach>
                            </dl>
                        </c:otherwise>
                    </c:choose>
                    <%-- 左部框架 - 下载列表 --%>
<%--                <div id="divInfo">
                    <ul>
                        <li>
        					 <img id="downClient" name="downClient" src="<c:url value='skin/p1/images/homepage/homepage_downClient.gif' />" style="width: 91px; height: 91px;" />
                        </li>
                        <c:if test="${fn:indexOf(session_user_role_function, 'BA00010') > -1}">
                            <li>
                                <a href="#" onclick="isDownClient(2);">下载二维客户端软件</a>
                            </li>
                        </c:if>
                        <c:if test="${fn:indexOf(session_user_role_function, 'BA00034') > -1}">
                            <li>
                                <a href="#" onclick="isDownClient(3);">下载三维客户端软件</a>
                            </li>
                        </c:if>
                        <li>
                            <a style="font-weight: normal; font-size: 12px;" href="#" onclick="isDownJDK();">下载报表打印支持软件</a>
                        </li>
                        <li>
                            版本<c:out value="${webVersion}" />[<c:out value="${webReleaseDate}" />]
                        </li>
                        <li>                            
                            服务版本:<c:out value="${StubServerVersion}" />
                        </li>
                    </ul>
                </div> --%>
                </div>
                
            </div>
            <%-- 右部框架 --%>
            <div id="divHpmain">
                <iframe id="iframe" name="iframe" frameborder="0" src="welcome.jsp" style="width: 100%;height: 100%"></iframe>
            </div>
            <%-- 上部框架 - 3/3 角色切换 --%>
            <div id="chgRoleUL" onmouseover="chgRole('show');" onmouseout="chgRole('hide');">
					<ul>
                    <c:forEach var="userRole" items="${session_user_role_list}">
                        <c:choose>
                            <c:when test="${userRole.roleId == session_user_role.roleId}">
                                <li >
                                    <a href="#" class="aonfocus">
                                        <c:out value="${userRole.roleName}"/>
                                    </a>
                                </li>
                            </c:when>
                            <c:otherwise>
                                <li  >
                                    <a href="#" onclick="gotoRole('<c:url value="/roleAction.do?method=roleSwitch&roleId=${userRole.roleId}&roleName=${userRole.roleName}&width=${roleWidth}&height=${roleHeight}"/>');">
                                        <c:out value="${userRole.roleName}" />
                                    </a>
                                </li>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
				</ul>
            </div>
            <iframe id="downiframe" name="downiframe" frameborder="0" style="display: none;"  onmouseover="chgRole('show');" onmouseout="chgRole('hide');"></iframe>
        </div>
    </body>
	
</html>
