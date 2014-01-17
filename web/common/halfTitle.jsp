<%@ page contentType="text/html; charset=GB2312"%>
<%@ include file="/common/taglibs.jsp"%>
<%-- ************************************
 * System:    协同作业平台
 * Function： 页面前半标题, 不独立使用, 用于各种二级以上页面的自动生成部分标题
 * Author:    黄承
 * Copyright: 北京华安天诚科技有限公司
 * Create:    VER1.01 2009.04.13
 * Modify:    VER1.02 2010.03.17 余涛 规范化
 *            VER1.03 2010.04.21 黄承 代码整理
************************************ --%>
<c:choose>
    <c:when test="${menuStyle != null && (menuStyle == '1' || menuStyle == '2')}">
        <%-- 显示一级菜单标题 --%>
        <c:forEach var="menus" items="${menusList}" varStatus="vs">
            <c:choose>
                <c:when test="${vs.index == 0}">
                    <a style="color: #0C1E7C;" href="<c:url value="/loginAction.do?method=menuIcoHref&menuFunctionId=${menus.function.functionId}" />" target="iframe"><c:out value="${menus.function.functionName}" /></a>
                </c:when>
                <c:otherwise>
                    <span>&ndash;<c:choose>
                            <c:when test="${menus != null && (menus.function.functionDescription == '' || menus.function.functionDescription == '#')}">
                                <a style="color: #0C1E7C;" href="<c:url value="/loginAction.do?method=menuIcoHref&menuFunctionId=${menus.function.functionId}" />" target="iframe"><c:out value="${menus.function.functionName}" /></a>
                            </c:when>
                            <c:otherwise>
                                <c:out value="${menus.function.functionName}" />
                            </c:otherwise>
                        </c:choose></span>
                </c:otherwise>
            </c:choose>
        </c:forEach>
    </c:when>
    <c:otherwise>
        <%-- 显示二、三级菜单标题--%>
        <c:forEach var="menus" items="${menusList}" varStatus="vs">
            <c:choose>
                <c:when test="${vs.index == 0}">
                    <c:out value="${menus.function.functionName}" />
                </c:when>
                <c:otherwise>
                    <span>&nbsp;&ndash;&nbsp;<c:out value="${menus.function.functionName}" /></span>
                </c:otherwise>
            </c:choose>
        </c:forEach>
    </c:otherwise>
</c:choose>
