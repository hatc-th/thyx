<%@ page contentType="text/html; charset=gb2312" %>
<%@ include file="/common/taglibs.jsp" %>

<!DOCTYPE html
      PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
      "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="zh" lang="zh">

<head>
    <title>Http Status</title>
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
    <meta http-equiv="content-language" content="zh" />
    <meta http-equiv="content-script-type" content="text/javascript" />
    <meta name="robots" content="none" />
    <meta name="author" content="HuangCheng" />
    <meta name="copyright" content="2010, Beijing HATC Co., LTD" />
    <meta name="description" content="httpstatus for XTMS" />
    <link rel="stylesheet" type="text/css" href="<c:url value="/css/platform.css" />" title="default" />
    <link rel="stylesheet" type="text/css" href="<c:url value="/css/platform.css" />" title="default" />
    <link rel="stylesheet" type="text/css" href="<c:url value="/css/status.css" />" title="default" />
</head>
  
<body>
    <div>
        <form id="status" method="post" action="httpsstatus.jsp">
            <ul>
                <li class="font_alert" style="letter-spacing: 3px;">系统提示信息！</li>
                <li>
                    <!-- input type="button" value="确&nbsp;&nbsp;定" onclick="document.forms[0].submit();" class="inputButtonL" / -->
                    <input type="button" value="返&nbsp;&nbsp;回" onclick="document.forms[0].submit();" class="inputButtonL" />
                </li>
            </ul>
        </form>
    </div>
</body>

</html>
