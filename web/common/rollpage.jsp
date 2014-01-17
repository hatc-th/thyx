<%@ page contentType="text/html; charset=GB2312" %>
<%@ include file="/common/taglibs.jsp" %>
<!DOCTYPE html
      PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
      "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="zh" lang="zh">

    <head>
        <title>RollPage</title>
        <meta http-equiv="content-type" content="text/html; charset=GB2312" />
        <meta http-equiv="content-language" content="zh" />
        <meta http-equiv="content-script-type" content="text/javascript" />
        <meta name="robots" content="none" />
        <meta name="author" content="HuangCheng" />
        <meta name="copyright" content="2010, Beijing HATC Technology Co., LTD" />
        <meta name="description" content="rollpage for XTMS" />
        <c:if test="${rollPage != null}">
            <script>
                function formSubmit(num) {
                	var pageForm = document.forms[0];
                	
                	var hid_pageBool = document.forms[0].pageBool;
                	if (hid_pageBool == null) {
        	        	hid_pageBool = document.createElement('input')
        	        	hid_pageBool.type = 'hidden';
        	        	hid_pageBool.name = 'pageBool';
        	        	hid_pageBool.value = '1';
        	        	pageForm.appendChild(hid_pageBool);
                	} else {
                		hid_pageBool.value = '1';
                	}
                    if (num == -1) {
                        var pageNum = document.getElementById('selectPage').value;		
        				var numCount = <c:out value="${rollPage.pageNum}" />;
        				if (pageNum > numCount || pageNum < 1) {
        				    alert('页码不能小于1或大于<c:out value="${rollPage.pageNum}" />!');
        				} else {					
        				    document.getElementById('page').value = pageNum - 1;
        				    document.forms[0].submit();
        				}
        			} else {
        			    document.getElementById('page').value = num;
        				document.forms[0].submit();
        			}
        		}
        	</script>
    	</c:if>
    </head>
    
    <body>
        <div id="div_rollpage" onkeypress="if (event.keyCode == 13) { formSubmit(-1); }" >
            <p style="text-align:center;">
                <input type="hidden" id="page" name="page" value="<c:out value="${rollPage.pageCur}" />" />
    			<c:if test="${rollPage.pageCur > 0}">
                    <a href="javascript:formSubmit(0);"><img title="首页" src="<c:url value="/images/rollpage/img_rollpage_firstpage.gif" />" /></a>
                </c:if>
                <c:if test="${rollPage.pageCur < 1}">
                    <img title="首页" src="<c:url value="/images/rollpage/img_rollpage_firstpage.gif" />" />
                </c:if>
                <c:if test="${rollPage.pageCur > 0}">
                    <a href='javascript:formSubmit(<c:out value="${rollPage.pageCur - 1}" />);'><img title="上一页" src="<c:url value="/images/rollpage/img_rollpage_previouspage.gif" />" /></a>
                </c:if>
                <c:if test="${rollPage.pageCur < 1}">
                    <img title="上一页" src="<c:url value="/images/rollpage/img_rollpage_previouspage.gif" />" />
                </c:if>
                <c:if test="${rollPage.pageCur + 1 < rollPage.pageNum}">
                    <a href='javascript:formSubmit(<c:out value="${rollPage.pageCur + 1}" />);'><img title="下一页" src="<c:url value="/images/rollpage/img_rollpage_nextpage.gif" />" /></a>
                </c:if>
                <c:if test="${rollPage.pageCur + 1 >= rollPage.pageNum}">
                    <img title="下一页" src="<c:url value="/images/rollpage/img_rollpage_nextpage.gif" />" />
                </c:if>
                <c:if test="${rollPage.pageCur + 1 < rollPage.pageNum}">
                    <a href='javascript:formSubmit(<c:out value="${rollPage.pageNum - 1}" />);'><img title="尾页" src="<c:url value="/images/rollpage/img_rollpage_lastpage.gif" />" /></a>
                </c:if>
                <c:if test="${rollPage.pageCur + 1 >= rollPage.pageNum}">
                    <img title="尾页" src="<c:url value="/images/rollpage/img_rollpage_lastpage.gif" />" />
                </c:if>
                    &nbsp;&nbsp;到&nbsp;<input id="selectPage" name="selectPage" type="text" class="selectText" style="width: 20px; height: 15px; line-height: 20px;" size="2" value="<c:out value="${rollPage.pageCur+1}" />" onkeyup="value=value.replace(/[^\d]/g,'');" />&nbsp;页
                    <a href="javascript:formSubmit(-1);"><img src="<c:url value="/images/rollpage/img_rollpage_forward.gif" />" /></a>
                <c:choose>
                    <c:when test="${rollPage == null}">
                        &nbsp;&nbsp;第<span>0</span>页/共<span>0</span>页/共<span>0</span>条&nbsp;&nbsp;
    			    </c:when>
                    <c:otherwise>
                        &nbsp;&nbsp;第<span><c:out value="${rollPage.pageCur + 1}" /></span>页/共<span><c:out value="${rollPage.pageNum}" /></span>页/共<span><c:out value="${rollPage.rowCount}" /></span>条&nbsp;&nbsp;
    			    </c:otherwise>
                </c:choose> 
            </p>
        </div>
    </body>

</html>