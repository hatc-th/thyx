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
        <fmt:setLocale value="zh_CN" />
        <fmt:bundle basename="Forward">
            <fmt:message key="${system_forward}" var="forward" scope="page" />
        </fmt:bundle>
    </head>

    <body>
        <table>
            <tr>
                <td>
                    <form name="BaseForm" method="post" action="<c:url value="${forward}"/>" target="<c:out value="${target}"/>">
                        <c:if test="${system_param != null}">
                            <c:forEach var="item" items="${system_param}">
                                <input type="hidden" name="<c:out value="${item.key}"/>" value="<c:out value="${item.value}"/>" />
                            </c:forEach>
                        </c:if>
                        <ul>
                            <li class="fontAlert" style="white-space: normal;">
                                <fmt:setLocale value="zh_CN" />
                                <fmt:bundle basename="${resources}">
                                    <fmt:message key="${resources_key}" />
                                </fmt:bundle>
                                <c:forEach var="item" items="${baseEx.messageArgs}">
                                    <br />
                                    <c:out value="${item}" />
                                </c:forEach>
                                <c:forEach var="item" items="${messageArgs}">
                                    <br />
                                    <c:out value="${item}"/>    
                                </c:forEach>
                            </li>
                            <li>
                                <c:if test="${system_forward != 'close' && system_forward != 'null' && system_forward != 'index'}">
                                    <input type="submit" value="&nbsp;返&nbsp;&nbsp;回&nbsp;" class="inputButtonCtrl" />
                                </c:if>
                                <c:if test="${system_forward == 'close'}">
                                    <input type="button" value="&nbsp;关&nbsp;&nbsp;闭&nbsp;" class="inputButtonCtrl" onclick="javascript:window.close();" />
                                </c:if>
                                <c:if test="${system_forward == 'index'}">
                                    <input type="button" value="&nbsp;确&nbsp;&nbsp;定&nbsp;" class="inputButtonCtrl" onclick="javascript:window.parent.location.href='<c:url value="/loginbg.jsp"/>';" />
                                </c:if>
                            </li>
                        </ul>
                    </form>
                </td>
            </tr>
        </table>
    </body>
</html>
