<%@ page contentType="text/html; charset=GB2312"%>
<%@ include file="common/taglibs.jsp"%>

<!DOCTYPE html
      PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
      "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="zh" lang="zh">

    <head>
        <title>${webDisplayName}&nbsp;<c:out value="${systemSecretLevel}"/></title>
        <meta http-equiv="Content-Type" content="text/html; charset=GB2312" />
        <meta http-equiv="content-language" content="zh" />
        <meta http-equiv="content-script-type" content="text/javascript" />
        <meta name="robots" content="none" />
        <meta name="author" content="HuangCheng" />
        <meta name="copyright" content="2010, Beijing HATC Technology Co., LTD" />
        <meta name="description" content="homepage for TTIMS" />
        <link type="text/css" rel="stylesheet" href="<c:url value="/css/platform.css" />" title="default" />
        <link type="text/css" rel="stylesheet" href="<c:url value="/css/homepage.css" />" title="default" />
        <script src="<c:url value="/js/popWindows.js" />"></script>
        <script src="<c:url value="/js/ajaxCommon.js" />"></script>
        <script src="<c:url value="/js/prototype.js"/>"></script>
        <script>
        	var today = new Date();
    		var day = today.getDay();
    		var month = today.getMonth() + 1;
    		var year = today.getFullYear();
    		var weekday = "";
    		var date = today.getDate();
            var selectArray = new Array();
    		switch(day) {
    			case 0:
    				weekday = "������";
    				break;
    			case 1:
    				weekday = "����һ";
    				break;
    			case 2:
    				weekday = "���ڶ�";
    				break;
    			case 3:
    				weekday = "������";
    				break;
    			case 4:
    				weekday = "������";
    				break;
    			case 5:
    				weekday = "������";
    				break;
    			case 6:
    				weekday = "������";
    				break;
    			default:
    		}

            
            function logoutSystem() {
                if (confirm("��ȷ��Ҫ�˳�<c:out value="${webDisplayName}"/>��")) {
                    window.location = '<c:url value="/userAction.do?method=logoutSystem" />';
                }
            }            
        </script>
    </head>

    <body >
        <div id="divFrame">
            <div id="divHptop">
                <ul id="hpTool">
                    <li>
                        &nbsp;
                    </li>
                    <li>
                        &nbsp;
                    </li>
                    <li>
                        &nbsp;
                    </li>
                    <li>
                        &nbsp;
                    </li>
                    <li>
                        &nbsp;
                    </li>
                    <li>
                        <a href="#" onclick="logoutSystem();"><img src="<c:url value="/images/img_hp_logout.gif" />" /></a>
                    </li>
                </ul>
                <ul id="hpMsg">
                    <li>
                        &nbsp;
                    </li>
                    <li>
                        &nbsp;
                    </li>
                    <li>
                       	��ӭ����&nbsp;&nbsp;<c:out value="${session_user.name}" />&nbsp;&nbsp;<c:out value="${session_user.dept_name}" />&nbsp;&nbsp;<c:out value="${session_user_role.roleName}" />
                    </li>
                    <li>&nbsp;</li>
                    <li>&nbsp;</li>
                    <li id="hpDate">
                        <script>
                            document.write("" + year + "��" + month + "��" + date + "��&nbsp;&nbsp;" + weekday);
                        </script>
                    </li>
                    <li>&nbsp;</li>
                	<li>&nbsp;</li>
                </ul>
            </div>
            <div id="divHpnav">
                <div id="divMenu">&nbsp;</div>
                <div id="divInfo">
                    <ul>
                        <li style="height: 80px;">
                        	&nbsp;				
                        </li>
                        <li>
                            &nbsp;
                        </li>
                        <li>
                           &nbsp;
                        </li>                        
                    </ul>
                </div>
            </div>
            <div id="divHpmain">
                <iframe id="iframe" name="iframe" frameborder="0" src="<c:url value="/userAction.do?method=setPwd" />"></iframe>
            </div>
            <div id="divHpbot">
                <span class="spanOthers">&nbsp;</span>
                <span class="spanCorp">����������ϿƼ����޹�˾</span>
            </div>           
        </div>
    </body>

</html>
