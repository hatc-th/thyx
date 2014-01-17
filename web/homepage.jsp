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
            var curLeaf; // ��ʷ�˵������ƶ����¼��ʶ
            var curLeafBgcolor; // ��ʷ�˵������ƶ��󱳾�ɫ
            var selectArray = new Array(); // �ݴ浱ǰҳ�����б����ص� select Ԫ��
            var switchRoleFlag = "0"; // ��ʶ�Ƿ���Ҫ��յ�ǰ��¼�û���seeesionId
			var msecs=100; //�ı�ʱ��õ���ͬ����˸�����
			var counter=0;
            
            /****** �û��ر��������ʱ���˳�ϵͳ ******/
            window.onbeforeunload = function() {
            	// ��ȡ�쳣�˳���־
            	var logoutMark = document.getElementById("logoutMark").value;
            	if(switchRoleFlag == "1"){
            		if(alreadyLoggedout == true)
            			return;
            		window.location = "<c:url value='/userAction.do?method=logoutSystem' />";
            		if(logoutMark != 1) {
            		    //ningliyu 2011-11-23
            			//alert("�˳�ϵͳ�����ɹ���");
            		}
            	}
            }
					
            /****** ���µ�¼ ******/
            function reLogin() {
                if (confirm('��ȷ��Ҫ���µ�¼��') == true) {
                	// �����쳣�˳���־
                	document.getElementById("logoutMark").value = "1";
                    window.location = '<c:url value="/loginAction.do?method=restLogin" />';
                }
                return false;
            }

            /****** ��ɫ�л� ******/
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
                	// �����쳣�˳���־
                	document.getElementById("logoutMark").value = "1";
                    document.location = url;
                }
            }
            
            /****** �޸����� ******/
            
            /****** ���� ******/
            function help(){
            	var url = "<c:url value='/help/___index.html' />";
            	var winName = "helpDoc";
            	var width = screen.availWidth;      // ��Ļ���ÿ��
   				var height = screen.availHeight;    // ��Ļ���ø߶�
            	var screenX = "";
            	var screenY = "";
   				var ctrlStr = "ynnny";
            	// ���Ʋ�������
			    function getCtrlValue(ctrlStr, n) {
			        var ctrlValue = ctrlStr.charAt(n);
			        var pattern = /[1yY]/;
			        if (ctrlValue == '' || !pattern.test(ctrlValue)) {
			            ctrlValue = 'no';    // Ĭ�ϲ���ʾ
			        } else {
			            ctrlValue = 'yes';
			        }
			        return ctrlValue;
			    }
			
			    // Ĭ�ϴ��ڿ��
			    if (width == null || width == '') {
			        width = 240;
			    }
			
			    // Ĭ�ϴ��ڸ߶�
			    if (height == null || height == '') {
			        height = 320;
			    }
			
			    var aw = screen.availWidth - 30;     // ��Ļ���ÿ��
			    var ah = screen.availHeight - 50;    // ��Ļ���ø߶�
			
			    // Ĭ�ϴ���ˮƽ����
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
			
			    // Ĭ�ϴ��ڴ�ֱ����
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
			
			    // Ĭ�ϴ��ڳߴ粻�ɵ����޹�����/�˵���/������/״̬��
			    if (ctrlStr == null || ctrlStr == '') {
			        ctrlStr = 'nnnnn';
			    }
			
			    var resize = getCtrlValue(ctrlStr, 0);        // ���ڳߴ�ɵ�����
			    var scrollbars = getCtrlValue(ctrlStr, 1);    // ����������
			    var menubar = getCtrlValue(ctrlStr, 2);       // �˵�������
			    var toolbar = getCtrlValue(ctrlStr, 3);       // ����������
			    var status = getCtrlValue(ctrlStr, 4);        // ״̬������
			    var features = 'width=' + width
			            + ',height=' + height
			            + ',left=' + screenX             // ����X���� for IE
			            + ',top=' + screenY              // ����Y���� for IE
			            + ',screenX=' + screenX          // ����X���� for Netscape
			            + ',screenY=' + screenY          // ����Y���� for Netscape
			            + ',resizable=' + resize         // ���ڳߴ�ɵ�����
			            + ',scrollbars=' + scrollbars    // ����������
			            + ',menubar=' + menubar          // �˵�������
			            + ',toolbar=' + toolbar          // ����������
			            + ',status=' + status            // ״̬������
			            + '';
			    winName = winName === undefined || winName === null || winName == '' ? Date.parse(new Date()) : winName;
			    var win = window.open(url, winName, features);
            	win.focus();
            }
            
            /****** �˳� ******/
            function logout() {
                if (confirm('��ȷ��Ҫ�˳�<c:out value="${webDisplayName}" />��') == true) {
                	// �����쳣�˳���־
                	alreadyLoggedout = true;
                	document.getElementById("logoutMark").value = "1";
                    window.location = '<c:url value="/userAction.do?method=logoutSystem&logoutMark=1" />';
                    //���� �˳���ֱ�ӹرմ��� chenzj 2012-12-28
                    window.close();
                }
            }
            
            function logoutAlert() {
                var logoutMark = document.getElementById("logoutMark").value;
                if (logoutMark == 0) {
                    alert('���ѳɹ��˳�<c:out value="${webDisplayName}" />��');
                }
                document.getElementById("logoutMark").value = 0;
            }
            
            /****** ��Ϣ���� ******/
            <%--function setMsgAlert(isMuted) {
                if (isMuted == 'mute') {
                    // ����
                    sendRequest('<c:url value="/msgListAction.do?method=setMsgAlert&isMuted=1" />');                   
                } else if (isMuted == 'unmute') {
                    // ȡ������
                    sendRequest('<c:url value="/msgListAction.do?method=setMsgAlert&isMuted=0" />');            
                }
            }
            
            function doAjax() {
                var rrt = req.responseText;
                if (rrt == 1) {
                    document.getElementById("setMsgAlert").innerHTML = '<a href="#" onclick="setMsgAlert(\'unmute\');"><img id="alerticon" name="alerticon" src="<c:url value='/skin/p1/images/homepage/homepage_soundClose.gif' />" title="���������" /></a>'
                } else if (rrt == 0) {
                    document.getElementById("setMsgAlert").innerHTML = '<a href="#" onclick="setMsgAlert(\'mute\');"><img id="alerticon" name="alerticon" src="<c:url value='/skin/p1/images/homepage/homepage_soundOpen.gif' />" title="����ر�����" /></a>'
                }
            }
            
            function chgMsg() {
                document.msgicon.src = '<c:url value="/images/msgempty.gif" />';
            }
            --%>
            /****** ������� ******/
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
            
            /****** ����Ĭ���û���ɫ ******/
            function setDefaultRole() {
                var ajaxReq = new Ajax.Request(
                    '<c:url value="/userAction.do?method=setDefaultRole"/>',
                    {
                        method:'get',
                        onSuccess: function(transport) {
                            defaultRoleSuccess(transport.responseXML);
                        },
                        onFailure: function() {
                            alert('��ΪĬ�Ͻ�ɫ�����г��ִ���...');
                        }
                    }
                );
            }
            
            function defaultRoleSuccess(response) {
                var result = response.getElementsByTagName("result")[0].firstChild.data;
                if (result == '0') {
                    alert('��ǰ��ɫ���Ѿ����û���Ĭ�Ͻ�ɫ��');
                } else if (result == '1') {
                    alert('�ɹ�����ǰ��ɫ����Ϊ�û�Ĭ�Ͻ�ɫ��');
                } else {
                    alert('�����û�Ĭ�Ͻ�ɫ�г��ִ���');
                }
            }
            
            /****** �˵������Ʋ��� ******/
            /* һ���˵�����ʾ���� */
            function ctrlPrim(curObj, id, bool) {
                var obj = document.getElementById('primNode' + id);
                if (bool) {
	                if (obj.style.display != 'block') {
	                    // ��ʾ�Ӳ˵�
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
            
            /* �����˵�����ʾ���� */
            function ctrlSec(curObj, id) {
                var obj = document.getElementById('secNode' + id);
                if (obj.style.display != 'block') {
                    // ��ʾ�Ӳ˵�
                    obj.style.display = 'block';
                    curObj.style.backgroundImage = 'url(<c:url value="/skin/p1/images/menu/secNodeB.gif" />)';
                } else {
                    obj.style.display = 'none';
                    curObj.style.backgroundImage = 'url(<c:url value="/skin/p1/images/menu/secNodeA.gif" />)';
                }
            }
            
            /* ��ǰ�˵�ѡ�������ʶ */
            function ctrlLeaf(curObj) {
                /* ��ʷ�����˵�������ɫ�ָ� */
                if (curLeaf != null) {
                    curLeaf.style.backgroundColor = curLeafBgcolor;
                }
                curLeaf = curObj;
                curLeafBgcolor = curObj.style.backgroundColor;
                if (curObj.style.backgroundColor != '#3997ce') {
                    /* ��ǰ�˵�������ɫ���� */
                    curObj.style.backgroundColor = '#3997ce';
                }
            }
            /** ������Ϣ  ͨ������Ҫ
            function sendWeatheActionS() {
               var weatherInfo = document.getElementById('weatherInfo');
               weatherInfo.src = '<c:url value="/weatherAction.do?method=ajaxStationsWeather"/>';
            }*/
            
            /**
             * ���¼��㴰�ڴ�С
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
             * ҳ���ʼ��
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
			
			
			//����Ӧҳ�涯̬���� div ���
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
    	<%-- ϵͳ�˳���־(��ʼ) --%>
    	<input type="hidden" id="logoutMark" name="logoutMark" value="0" />
    	<%-- ϵͳ�˳���־(����) --%>
        <div id="divHomepage"><%-- �ϲ���� --%>
            <div id="divHptop">
            <div id="divtopleft" style = "font-size:30px;color:#fff; font-weight:bold;">
            	<br/>&nbsp;&nbsp;ͨ�ú������й���ͷ�����ϵͳ
	    	</div>
	    	<div id="divtoprepeat">
	    	</div>
	    	<div id="divtopright">
	    	</div>
	    	<div id="divtool">
                <%-- �ϲ���� - 1/3 ����ģ�� --%>
                <ul id="hpTool">
                    <li id="homepage">
                        <a id="defaultFirstPage" title="��ҳ" href="welcome.jsp"  target="iframe"></a>
                    </li>
                    <li id="reLogin">
                        <a href="#" title="���µ�¼" onclick="reLogin();" ></a>
                    </li>
                    <li id="chgPwd">
                        <a href="#" title="�޸�����" onclick="newHpmainWin('<c:url value="/userAction.do?method=setPwd" />', 'updatePwd');"></a>
                    </li>
                    <!-- <li id="chgRole" title="�л���ɫ" onclick="chgRole('show');" onmouseover="chgRole('show');" onmouseout="chgRole('hide');">
                        <a href="#"></a>
                    </li> -->
                    <li id="helpInfo">
                        <a href="#" title="�鿴�����ĵ�" onclick="help();"></a>
                    </li>
                    <li id="logout">
                        <a href="#" title="�˳�ϵͳ" onclick="logout();"></a>
                    </li>
                </ul>
               </div>
                <%-- �ϲ���� - 2/3 ��ҳ��Ϣ --%>
                <div id="divstatus">
                <ul id="hpMsg">
                    <li style="width: 2%;"></li>

                    <li style="width: 3%;">
                        <a href="#" id="role">
                        </a>
                    </li>
                    <li style="width: 22%; color: #E5E8ED;">
                        ��ӭ����
                        <span style="color:#FF9000"><c:out value="${session_user.name}" /></span>
                        &nbsp;
                        <c:out value="${session_user.dept_name}" />
                        &nbsp;
                        <c:out value="${session_user_role.roleName}" />
                    </li>
                    <li id="setMsgAlert" style="width: 5%;">
                        <%--<a href="#" onclick="setMsgAlert('mute');">
                        	<img id="alerticon" name="alerticon" src="<c:url value="/skin/p1/images/homepage/homepage_soundOpen.gif" />" title="����ر�����" />
                        </a>--%>
                    </li>
                    <li style="width: 5%;">
                        <%-- <iframe id="msgAlert" name="msgAlert" frameborder="0" scrolling="no" style="margin: 0px; width: 24px; height: 20px;" src="<c:url value="/message/msgWav.jsp" />"></iframe>--%>
                    </li>
                    <li style="width: 3%;">
                        <%-- ������  <a href="#" onclick="sendWeatheActionS();" id="alertweather" title="������Ϣ"></a>--%>
                    </li>
                    <li style="width: 37%;">
                        <%-- ����Ԥ��Ԥ��   ������ --%>
                        <%-- <iframe id="weatherInfo" name="weatherInfo" frameborder="0" style="margin: 0px; width: 95%; height: 20px;" scrolling="no" src="<c:url value="/weatherAction.do?method=ajaxStationsWeather"/>"></iframe> --%>
                    </li>
                    <li style="width: 3%;">
                    </li>
                    <li style="width: 3%;" >
                        <a href="#" id="alertdate"  title="����">
                        </a>
                    </li>
                    <li id="hpDate" style="width: 15%; color: #E5E8ED;">
                        <c:out value="${hpDate}" />
                    </li>
                </ul>
                </div>
            </div>
            <%-- �󲿿�� --%>
            <div id="divHpnav">
                <%-- �󲿿�� - Ŀ¼�б� --%>
                <div id="divMenu">
                    <c:choose>
                        <%-- ͼ�귽ʽ��ʾĿ¼�� --%>
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
                        <%-- ���� + ͼ�귽ʽ��ʾĿ¼�� --%>
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
                    <%-- �󲿿�� - �����б� --%>
<%--                <div id="divInfo">
                    <ul>
                        <li>
        					 <img id="downClient" name="downClient" src="<c:url value='skin/p1/images/homepage/homepage_downClient.gif' />" style="width: 91px; height: 91px;" />
                        </li>
                        <c:if test="${fn:indexOf(session_user_role_function, 'BA00010') > -1}">
                            <li>
                                <a href="#" onclick="isDownClient(2);">���ض�ά�ͻ������</a>
                            </li>
                        </c:if>
                        <c:if test="${fn:indexOf(session_user_role_function, 'BA00034') > -1}">
                            <li>
                                <a href="#" onclick="isDownClient(3);">������ά�ͻ������</a>
                            </li>
                        </c:if>
                        <li>
                            <a style="font-weight: normal; font-size: 12px;" href="#" onclick="isDownJDK();">���ر����ӡ֧�����</a>
                        </li>
                        <li>
                            �汾<c:out value="${webVersion}" />[<c:out value="${webReleaseDate}" />]
                        </li>
                        <li>                            
                            ����汾:<c:out value="${StubServerVersion}" />
                        </li>
                    </ul>
                </div> --%>
                </div>
                
            </div>
            <%-- �Ҳ���� --%>
            <div id="divHpmain">
                <iframe id="iframe" name="iframe" frameborder="0" src="welcome.jsp" style="width: 100%;height: 100%"></iframe>
            </div>
            <%-- �ϲ���� - 3/3 ��ɫ�л� --%>
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
