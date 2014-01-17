<%@ page contentType="text/html; charset=gb2312"%>
<%@ include file="/common/taglibs.jsp"%>

<!DOCTYPE html
      PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
      "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="zh" lang="zh">

    <head>
        <title>系统信息</title>
        <meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
        <meta http-equiv="content-language" content="zh" />
        <meta http-equiv="content-script-type" content="text/javascript" />
        <meta name="robots" content="none" />
        <meta name="author" content="WangYang" />
        <meta name="copyright" content="2010, Beijing HATC Co., LTD" />
        <meta name="description" content="httpstatus for XTMS" />
        <link rel="stylesheet" type="text/css" href="<c:url value="/css/platform.css" />" title="default" />
        <link rel="stylesheet" type="text/css" href="<c:url value="/css/platform.css" />" title="default" />
        <link rel="stylesheet" type="text/css" href="<c:url value="/css/status.css" />" title="default" />
    </head>

    <body>
        <table>
            <tr>
                <td>
                    <form name="BaseForm" method="post" action="<c:url value="/index.jsp"/>" class="" >
                        <ul>
                            <li class="fontAlert" style="text-align: left;">
	                            <table>
						            <tr>
						            	<td style="width: 100px;">&nbsp;</td>
						                <td>
						                	由于长时间没有响应或服务器重启!<br/>
			                            	用户会话已过期!<br/>
			                            	请您重新登录!
								        </td>
								        <td style="width: 100px;">&nbsp;</td>
						            </tr>
						        </table>                            	
                            </li>
                            <li>
                            	<input type="submit" value="&nbsp;确&nbsp;&nbsp;定&nbsp;" class="inputButtonCtrl"/>
                            </li>
                        </ul>
                    </form>
                </td>
            </tr>
        </table>
    </body>
</html>