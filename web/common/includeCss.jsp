<%@ page language="java" pageEncoding="gbk"%>
<%@ include file="/common/taglibs.jsp" %>

<!DOCTYPE html
      PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
      "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="zh" lang="zh">
<head>
	<% 
	String skin = (String) request.getSession().getAttribute("skin");
	
	if ( skin == null || "".equals(skin)) {
		skin = request.getContextPath() + "/skin/p1";
	}
	%>
	<link type="text/css" rel="stylesheet" href="<%=skin %>/css/platform.css"  title="default" />
	<link type="text/css" rel="stylesheet" href="<%=skin %>/css/rollpage.css"  title="default" />
	<link type="text/css" rel="stylesheet" href="<%=skin %>/css/widget_calendar.css" title="winter" media="all" />
	
	<link type="text/css" rel="stylesheet" href="<%=skin %>/css/popChoose.css"  title="default" />
	<link type="text/css" rel="stylesheet" href="<%=skin %>/css/widget_dtree.css"  title="default" />
	<link type="text/css" rel="stylesheet" href="<%=skin %>/css/weather.css"  title="default" />
	<link type="text/css" rel="stylesheet" href="<%=skin %>/css/menuIco.css" title="default" />
	
	<%-- 协同作业模块的样式 --%>  
	<link type="text/css" rel="stylesheet" href="<%=skin %>/css/message.css" title="default" /> 
	<!-- 
		<link type="text/css" rel="stylesheet" href="<%=skin %>/css/homepage.css"  title="default" />
	<link type="text/css" rel="stylesheet" href="<%=skin %>/css/login.css"  title="default" />
	<link type="text/css" rel="stylesheet" href="<%=skin %>/css/status.css"  title="default" />
	 -->
	
	<script src="<c:url value="/js/ui/SetDivStyle.js" />"></script>
</head>
</html>
