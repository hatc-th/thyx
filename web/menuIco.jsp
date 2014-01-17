<%@ page language="java" pageEncoding="gbk"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/includeCss.jsp"%>
<!DOCTYPE html
      PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
      "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="zh" lang="zh">

    <head>
        <title><c:out value="${webDisplayName}" /></title>
        <meta http-equiv="content-type" content="text/html; charset=gbk" />
        <meta http-equiv="content-language" content="zh" />
        <meta http-equiv="content-script-type" content="text/javascript" />
        <meta name="robots" content="none" />
        <meta name="author" content="HuangCheng" />
        <meta name="copyright" content="2010, Beijing HATC Technology Co., LTD" />
        <meta name="description" content="menuIco for XTMS" />
        <script src="<c:url value="/js/prototype.js"/>"></script>
        <script src="<c:url value="/js/popWindows.js"/>"></script>
        <script>
        	function displayItem(url,id){
            	newWin(url + '&msgId=' + id, 'msg' + id);
            }
            
            function displayMore(url){
            	var date = new Date();
            	newWin(url, 'msg' + date.getTime());
            }
            function resizeHeightAndWidth(){
            	var winheight = document.body.clientHeight;
				var winwidth = document.body.clientWidth;
				document.getElementById("divcase").style.height=winheight-45;
				document.getElementById("divleft").style.height=winheight-48;
				document.getElementById("divright").style.height=winheight-48;
				document.getElementById("divleft").style.width=winwidth-220;		
            }
        </script>
    </head>

    <body onload="resizeHeightAndWidth();" onresize="resizeHeightAndWidth();">
        <div id="divtop">
            <c:import url="/common/pageTitle.jsp" />
        </div>
        <c:choose>
            <%-- 无右侧区域 shortcutStyle == 0 --%>
            <c:when test="${shortcutStyle == 0}">
                <div id="divunder"></div>
            </c:when>
            <%-- 有右侧区域 shortcutStyle == 1 --%>
            <c:otherwise>
                <div id="divcase">
                    <div id="divleft" style="overflow-y: auto;">
                        <table>
                            <tr>
                                <c:choose>
                                    <%-- 单排 --%>
                                    <c:when test="${listSize < menuRowNum}">
                                        <c:set var="td_style" value="overflow-y: auto; padding: 50px 168px 50px 169px;text-align: center;" scope="page" />
                                        <c:set var="div_menu_style" value="width: 271px;" scope="page" />
                                    </c:when>
                                    <%-- 多排 --%>
                                    <c:otherwise>
                                        <c:set var="td_style" value="overflow-y: auto; padding: 50px 33px 50px 33px;text-align: center;" scope="page" />
                                        <c:set var="div_menu_style" value="width: 542px;" scope="page" />
                                    </c:otherwise>
                                </c:choose>
                                <td style="${td_style}">
                                    <c:choose>
                                        <%-- 多排纵向排列 --%>
                                        <c:when test="${listSize >= menuRowNum && menuRepeat == 'repeat_y'}">
                                            <div id="tbreport" style="${div_menu_style}">
                                                <div style="float: left; width: 50%;">
                                                    <c:forEach var="item" items="${menu.list}" varStatus="it">
                                                        <c:if test="${(it.index * 2) < listSize}">
                                                            <c:choose>
                                                                <c:when test="${item != null && item.function.functionDescription == '' || item.function.functionDescription == '#' }">
                                                                    <li class="menuLi">
                                                                        <a href="<c:url value="/loginAction.do?method=menuIcoHref&menuFunctionId=${item.function.orderId}" />&firstPageFunctionId=${item.function.functionId}" target="iframe"> <img
                                                                                src="<c:url value="images/menuAppMinFolder.gif"/>" /> <c:out value="${item.function.functionName }" /> </a>
                                                                    </li>
                                                                </c:when>
                                                                <c:otherwise>
                                                                    <li class="menuLi">
                                                                        <a href="<c:url value="${item.function.functionDescription }" />" target="iframe"> <img src="<c:url value="images/menuAppMin.gif"/>" /> <c:out
                                                                                value="${item.function.functionName }" /> </a>
                                                                    </li>
                                                                </c:otherwise>
                                                            </c:choose>
                                                        </c:if>
                                                    </c:forEach>
                                                </div>
                                                <div style="float: right; width: 50%;">
                                                    <c:forEach var="item" items="${menu.list}" varStatus="it">
                                                        <c:if test="${(it.index * 2) >= listSize}">
                                                            <c:choose>
                                                                <c:when test="${item != null && item.function.functionDescription == '' || item.function.functionDescription == '#' }">
                                                                    <li class="menuLi">
                                                                        <a href="<c:url value="/loginAction.do?method=menuIcoHref&menuFunctionId=${item.function.orderId}" />&firstPageFunctionId=${item.function.functionId}" target="iframe"> <img
                                                                                src="<c:url value="images/menuAppMinFolder.gif"/>" /> <c:out value="${item.function.functionName }" /> </a>
                                                                    </li>
                                                                </c:when>
                                                                <c:otherwise>
                                                                    <li class="menuLi">
                                                                        <a href="<c:url value="${item.function.functionDescription }" />" target="iframe"> <img src="<c:url value="images/menuAppMin.gif"/>" /> <c:out
                                                                                value="${item.function.functionName }" /> </a>
                                                                    </li>
                                                                </c:otherwise>
                                                            </c:choose>
                                                        </c:if>
                                                    </c:forEach>
                                                </div>
                                            </div>
                                        </c:when>
                                        <%-- 单排或多排横向排列 --%>
                                        <c:otherwise>
                                            <div id="tbreport" style="${div_menu_style}">
                                                <c:forEach var="item" items="${menu.list}">
                                                    <c:choose>
                                                        <c:when test="${item != null && item.function.functionDescription == '' || item.function.functionDescription == '#' }">
                                                            <li class="menuLiSplitX"></li>
                                                            <li class="menuLi" style="float: left;">
                                                                <a href="<c:url value="/loginAction.do?method=menuIcoHref&menuFunctionId=${item.function.orderId}" />&firstPageFunctionId=${item.function.functionId}" target="iframe"> <img
                                                                        src="<c:url value="images/menuAppMinFolder.gif"/>" /> <c:out value="${item.function.functionName }" /> </a>
                                                            </li>
                                                            <li class="menuLiSplitX"></li>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <li class="menuLiSplitX"></li>
                                                            <li class="menuLi" style="float: left;">
                                                                <a href="<c:url value="${item.function.functionDescription }" />" target="iframe"> <img src="<c:url value="images/menuAppMin.gif"/>" /> <c:out
                                                                        value="${item.function.functionName }" /> </a>
                                                            </li>
                                                            <li class="menuLiSplitX"></li>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </c:forEach>
                                            </div>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                            </tr>
                        </table>
                    </div>

                    <div id="divright">
                        <div class="rightCap">
                            <li class="liLeft">
                                <img src="<c:url value="/images/email_flow.gif"/>" />
                                待处理事项 
                        		<%-- <fmt:message key="lbl.projectScheduleManage.testTaskFinishRecord.profile.unsolvedItems" />--%>
                            </li>
                            <li class="liRight">
                                <c:if test="${flowListSize > 4}">
                                    <a href="#" onclick="displayMore('<c:url value="/flowAction.do?method=searchList" />');">更多 
                                    <%-- <fmt:message key="lbl.statisticReport.testTaskSummary.more" /><fmt:message key="lbl.common.dot" /><fmt:message key="lbl.common.dot" /><fmt:message key="lbl.common.dot" /></a>--%>
                                </c:if>
                            </li>
                        </div>
                        <div class="rightList">
                            <c:forEach var="it" items="${flowList}">
                                <div class="rightText">
                                    <li class="liLeft">
                                        <%--<a href="#" onclick="displayItem('<c:url value="${it.process_url}"/>&approve_id=${it.approve_id }&approve_state=${it.approve_state }&menuFunctionId=BMA0100','${it.approve_id }')"> <string-truncation:truncation
                                                value="${it.object_id}" length="29" />
                                        <c:choose>
                                            <c:when test="${fn:length(it.object_id) > 10}">${fn:substring(it.object_id, 0, 10)}...</c:when>
                                            <c:otherwise>${it.object_id}</c:otherwise>
                                        </c:choose>
                                        </a>--%>
                                        <a href="<c:url value="${it.process_url}"/>&approve_id=${it.approve_id }&approve_state=${it.approve_state }&approve_type=${it.approve_type }&sid=${it.send_user_id }&aid=${it.approve_user_id}" title="${it.object_name}">
	                                    	<string-truncation:truncation value="${it.object_name}" length="29" />
	                                    </a>
                                    </li>
                                </div>
                            </c:forEach>
                        </div>
                        <div class="rightCap">
                            <li class="liLeft">
                                <img src="<c:url value="/images/email.gif"/>" />
                                未读信息 
                                <%--fmt:message key="lbl.cooperateManage.common.needReadMessage" /--%>
                            </li>
                            <li class="liRight">
                                <c:if test="${gotMsgListNullSize > 4}">
                                    <a href="#" onclick="displayMore('<c:url value="/msgListAction.do?method=gotMsgList&sqlIsRead=NULL&menuFunctionId=BM20900&isDisplayButton=no" />');">更多...</a>
                                </c:if>
                            </li>
                        </div>
                        <div class="rightList">
                            <c:forEach var="gotMsgListNull" items="${gotMsgListNull}">
                                <div class="rightText">
                                    <li class="liLeft">
                                        <a href="#" onclick="displayItem('<c:url value="/msgListAction.do?method=getGotMsg" />', '<c:out value="${gotMsgListNull.message_id}" />');">
                                        <%--
                                        <string-truncation:truncation value="${gotMsgListNull.subject}" length="29" />
                                        --%> 
                                        <c:choose>
                                            <c:when test="${fn:length(gotMsgListNull.subject) > 10}">${fn:substring(gotMsgListNull.subject, 0, 10)}...</c:when>
                                            <c:otherwise>${gotMsgListNull.subject}</c:otherwise>
                                        </c:choose>
                                        </a>
                                    </li>
                                    <li class="liRight">
                                        <c:if test="${gotMsgListNull.msgAtcRcvSize > 0}">
                                            <img src="<c:url value="/images/email_attach.gif"/>" />(<c:out value="${gotMsgListNull.msgAtcRcvSize}" />)
                                    </c:if>
                                    </li>
                                </div>
                            </c:forEach>
                        </div>
                        <div class="rightCap">
                            <li class="liLeft">
                                <img src="<c:url value="/images/email_open.gif"/>" />
                                已读信息
                                <%-- <fmt:message key="lbl.cooperateManage.common.readMessage" /> --%>
                            </li>
                            <li class="liRight">
                                <c:if test="${gotMsgListNotNullSize > 4}">
                                    <a href="#" onclick="displayMore('<c:url value="/msgListAction.do?method=gotMsgList&sqlIsRead=NOT NULL&menuFunctionId=BM20900&isDisplayButton=no" />');">更多...</a>
                                </c:if>
                            </li>
                        </div>
                        <div class="rightList">
                            <c:forEach var="gotMsgListNotNull" items="${gotMsgListNotNull}">
                                <div class="rightText">
                                    <li class="liLeft">
                                        <a href="#" onclick="displayItem('<c:url value="/msgListAction.do?method=getGotMsg" />', '<c:out value="${gotMsgListNotNull.message_id}" />');">
                                        <string-truncation:truncation value="${gotMsgListNotNull.subject}" length="29" /> <%--
                                        <c:choose>
                                            <c:when test="${fn:length(gotMsgListNotNull.subject) > 10}">${fn:substring(gotMsgListNotNull.subject, 0, 10)}...</c:when>
                                            <c:otherwise>${gotMsgListNotNull.subject}</c:otherwise>
                                        </c:choose>
                                        --%> </a>
                                    </li>
                                    <li class="liRight">
                                        <c:if test="${gotMsgListNotNull.msgAtcRcvSize > 0}">
                                            <img src="<c:url value="/images/email_attach.gif"/>" />(<c:out value="${gotMsgListNotNull.msgAtcRcvSize}" />)
                                    </c:if>
                                    </li>
                                </div>
                            </c:forEach>
                        </div>
                        <div class="rightCap">
                            <li class="liLeft">
                                <img src="<c:url value="/images/email_go.gif" />" />
                                已发信息 
                                <%-- <fmt:message key="lbl.cooperateManage.common.sendedMessage" />--%>
                            </li>
                            <li class="liRight">
                                <c:if test="${sentMsgListSize > 4}">
                                    <a href="#" onclick="displayMore('<c:url value="/msgListAction.do?method=sentMsgList&menuFunctionId=BM21000&isDisplayButton=no" />');">更多...</a>
                                </c:if>
                            </li>
                        </div>
                        <div class="rightList">
                            <c:forEach var="item" items="${sentMsgList}">
                                <div class="rightText">
                                    <li class="liLeft">
                                        <a href="#" onclick="displayItem('<c:url value="/msgListAction.do?method=getSentMsg" />', '<c:out value="${item.message_id}" />');">
                                        <string-truncation:truncation value="${item.subject}" length="29" /></a>
                                    </li>
                                    <li class="liRight">
                                        <c:if test="${item.msgAtcRcvSize > 0}">
                                            <img src="<c:url value="/images/email_attach.gif" />" />(<c:out value="${item.msgAtcRcvSize}" />)
                                    </c:if>
                                    </li>
                                </div>
                            </c:forEach>
                        </div>
                        <div class="rightCap" style="background: transparent url(images/bgimg_mi_rightcap2.gif) repeat-x scroll 50%;">
                            <li class="liLeft">
                                <img src="<c:url value="/images/application_cascade.gif" />" />
                                常用功能 
                                <%-- <fmt:message key="lbl.projectScheduleManage.testTaskFinishRecord.profile.usualFunction" />--%>
                            </li>
                            <li class="liRight"></li>
                        </div>
                        <div class="rightList" style="overflow-y: auto;">
                            <c:forEach var="item" items="${requestScope.commonFunctionList}">
                                <div class="rightText">
                                    <li>
                                        <a href="<c:url value="${item.functionDescription}" />"> <img src="<c:url value="/images/menuAppMin.gif" />" />&nbsp;<c:out value="${item.functionName}"></c:out> </a>
                                    </li>
                                </div>
                            </c:forEach>
                        </div>
                    </div>

                </div>
            </c:otherwise>
        </c:choose>
    </body>

</html>
