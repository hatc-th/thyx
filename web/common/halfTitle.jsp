<%@ page contentType="text/html; charset=GB2312"%>
<%@ include file="/common/taglibs.jsp"%>
<%-- ************************************
 * System:    Эͬ��ҵƽ̨
 * Function�� ҳ��ǰ�����, ������ʹ��, ���ڸ��ֶ�������ҳ����Զ����ɲ��ֱ���
 * Author:    �Ƴ�
 * Copyright: ����������ϿƼ����޹�˾
 * Create:    VER1.01 2009.04.13
 * Modify:    VER1.02 2010.03.17 ���� �淶��
 *            VER1.03 2010.04.21 �Ƴ� ��������
************************************ --%>
<c:choose>
    <c:when test="${menuStyle != null && (menuStyle == '1' || menuStyle == '2')}">
        <%-- ��ʾһ���˵����� --%>
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
        <%-- ��ʾ���������˵�����--%>
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
