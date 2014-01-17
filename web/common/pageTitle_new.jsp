<%@ page language="java" pageEncoding="gbk"%>
<%@ include file="/common/taglibs.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="zh" lang="zh">

    <head>
        <title><c:out value="${webDisplayName}" /></title>
        <meta http-equiv="content-type" content="text/html; charset=GB2312" />
        <meta http-equiv="content-language" content="zh" />
        <meta http-equiv="content-script-type" content="text/javascript" />
        <meta name="robots" content="none" />
        <meta name="author" content="HuangCheng" />
        <meta name="copyright" content="2010, Beijing HATC Co., LTD" />
        <meta name="description" content="rollpage for XTMS" />
        <link rel="shortcut icon" type="image/x-icon" href="<c:url value="/images/default.ico" />" />
        <script type="text/javascript">
        	<c:if test="${backMenu != null}">
            	function icoDo(thefirstPageFunctionId){
               		var tempForm = document.createElement('form')
               		tempForm.id = 'tempForm';
               		tempForm.name = 'tempForm';
                   	tempForm.target = 'iframe';
               		tempForm.action = '<c:url value="/loginAction.do?method=menuIcoHref&menuFunctionId=${backMenu}" />&firstPageFunctionId=' + thefirstPageFunctionId; 
               		tempForm.method = 'post';       		
               		document.body.appendChild(tempForm);
               		tempForm.submit();
            	}
        	</c:if>
    	
        	function setDefaultFirstPage(action) 
        	{
        	    //TODO ningliyu 2012-1-6 
        	    //修改BUG：原来获取 firstPageFunctionId 的算法有问题。
        	    /** 原来代码：
        	       <c:choose>
    	            <c:when test="${empty firstPageFunctionId}">
    	            	firstPageFunctionId = document.getElementById("firstPageFunctionId").value;
    	            </c:when>
    	            <c:otherwise>
    	            	firstPageFunctionId = "<c:out value='${firstPageFunctionId}' />";
    	            </c:otherwise>
                </c:choose>
                **/
                
        		var firstPageFunctionId;
        	    // 首先判断是否为空
        	    var webOjbFirstPageFunctionId = document.getElementById("firstPageFunctionId");
        	      
        	    if(webOjbFirstPageFunctionId != null ){
    	             firstPageFunctionId = webOjbFirstPageFunctionId.value;
   	             }else{
    	             firstPageFunctionId = "<c:out value='${firstPageFunctionId}' />";
   	             }
    	         
    	         
    			var ajaxReq = new Ajax.Request(
                   	'<c:url value="/userAction.do?method=setDefaultFirstPage"/>',{
                       method:'post',
                       postBody: '&webHome=' + firstPageFunctionId + '&action=' + action,
                       onSuccess: function(transport){
                           defaultFirstPageSuccess(transport.responseXML);
                       },
                       onFailure: function(){
                           alert('设置首页过程中出现错误...');
                       }
                   }
                   ); 
    		}
            
            function defaultFirstPageSuccess(response){
            	var data = response.getElementsByTagName("data")[0];
            	var result = data.getElementsByTagName("result")[0].firstChild.data;
            	
            	if (result == '0') {
            		alert('当前首页是已经是用户的默认首页！');
            	} else if (result == '1') {
            		alert('成功将当前页设置为用户默认首页！');
            		var defaultFirstPage = data.getElementsByTagName("defaultFirstPage")[0].firstChild.data;
    	            var hrefStr = '<c:url value="/"/>' + defaultFirstPage;;
    	            if (defaultFirstPage.substr(0,1) == '/') {
    	                hrefStr = '<c:url value="/"/>'.substr(0,'<c:url value="/"/>'.length - 1) + defaultFirstPage;;
    	            }
    	            if(window.opener){
	    	            window.opener.parent.document.getElementById("defaultFirstPage").href = hrefStr;
    	            }else{
   	            		window.parent.document.getElementById("defaultFirstPage").href = hrefStr;
    	            }
                } else if (result == '2') {
            		alert('用户的默认首页已经为空！');
            	} else if (result == '3') {
            		alert('成功将用户默认首页清空！');
            		var defaultFirstPage = data.getElementsByTagName("defaultFirstPage")[0].firstChild.data;
    	            var hrefStr = '<c:url value="/"/>' + defaultFirstPage;;
    	            if (defaultFirstPage.substr(0,1) == '/') {
    	                hrefStr = '<c:url value="/"/>'.substr(0,'<c:url value="/"/>'.length - 1) + defaultFirstPage;
    	            }
    	            if(window.opener){
	    	            window.opener.parent.document.getElementById("defaultFirstPage").href = hrefStr;
    	            }else{
   	            		window.parent.document.getElementById("defaultFirstPage").href = hrefStr;
    	            }
            	} else {
            		alert('设置用户默认首页中出现错误！');
            	}
            }
        </script>
    </head>

    <body>
        <h1>
            <c:choose>
                <c:when test="${menuStyle != null && (menuStyle == '1' || menuStyle == '2')}">
                    <table width="810" border="0" cellpadding="0" cellspacing="0">
                        <tr>
                            <td>
                                <h1>
                                    <c:forEach var="menus" items="${menusList}" varStatus="vs">
                                    	<%-- 计算返回上一级菜单需要的functionId(开始) --%>
                                    	<c:if test="${menusListLength ge 2}">
                                    		<c:if test="${vs.index eq menusListLength - 2}">
                                    			<c:set var="theFirstPageFunctionId" value="${menus.function.functionId}"></c:set>
                                    		</c:if>
                                    	</c:if>
                                    	<%-- 计算返回上一级菜单需要的functionId(结束) --%>
                                        <c:choose>
                                            <c:when test="${vs.index == 0}">
                                                <a href="<c:url value="/loginAction.do?method=menuIcoHref&menuFunctionId=${menus.function.orderId}&firstPageFunctionId=${menus.function.functionId}" />" 
                                                    target="iframe"><c:out value="${menus.function.functionName}" /></a>
                                            </c:when>
                                            <c:otherwise>
                                                <span> - <c:choose>
                                                        <c:when test="${menus != null && menus.function.functionDescription == '' || menus.function.functionDescription == '#'}">
                                                            <a href="<c:url value="/loginAction.do?method=menuIcoHref&menuFunctionId=${menus.function.orderId}&firstPageFunctionId=${menus.function.functionId}" />" 
                                                                target="iframe"><c:out value="${menus.function.functionName}" /></a>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <c:out value="${menus.function.functionName}" />
                                                            <input type="hidden" id="firstPageFunctionId" name="firstPageFunctionId" value="${menus.function.functionId}" />
                                                        </c:otherwise>
                                                    </c:choose></span>
                                            </c:otherwise>
                                        </c:choose>
                                    </c:forEach>
                                </h1>
                            </td>
                            <td style="width: 200px; text-align: right; padding-right: 5px;">
                                <c:if test="${backMenu != null}">
                                    <a href="#" onclick="icoDo('${theFirstPageFunctionId}')"><img style="width: 24px; height: 24px;" src="<c:url value="skin/p1/images/img_divtop_backMenu.gif" />" title="返回上级菜单" /></a>
                                </c:if>
                            	<a href="#" onclick="setDefaultFirstPage('cancel')"><img style="width: 24px; height: 24px;" src="<c:url value="skin/p1/images/img_divtop_cancelFirstPage.gif"/>" title="取消默认首页" /></a>
                                <a href="#" onclick="setDefaultFirstPage('set')"><img style="width: 24px; height: 24px;" src="<c:url value="skin/p1/images/img_divtop_setFirstPage.gif"/>" title="设为默认首页" /></a>
                            </td>
                        </tr>
                    </table>
                </c:when>
                <c:otherwise>
                    <c:forEach var="menus" items="${menusList}" varStatus="vs">
                        <c:choose>
                            <c:when test="${vs.index == 0}">
                                <c:out value="${menus.function.functionName}" />
                                <input type="hidden" id="firstPageFunctionId" name="firstPageFunctionId" value="${menus.function.functionId}" />
                            </c:when>
                            <c:otherwise>
                                <span> - <c:out value="${menus.function.functionName}" /><input type="hidden" id="firstPageFunctionId" name="firstPageFunctionId" value="${menus.function.functionId}" /></span>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                </c:otherwise>
            </c:choose>
        </h1>
    </body>

</html>
