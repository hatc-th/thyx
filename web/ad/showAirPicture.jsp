<%@ page language="java" pageEncoding="gbk"%>
<%@ include file="/common/taglibs.jsp"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE HTML>
<html>
	<head>
		<title>选择节点</title>
	</head>
	<link href="<%=path%>/css/thyx/fPlan.css" rel="stylesheet" type="text/css">
	<link href="<%=path%>/css/thyx/fPlanContext.css" rel="stylesheet" type="text/css">
	<script type="text/javascript">
		function closeSDiv(flag){
			var sDiv = parent.document.getElementById("sDiv");
			sDiv.parentNode.removeChild(sDiv);			
		}
</script>
	<body>
		<c:choose>
			<c:when test="${flag == '1'}">
				<div style="float:left;width:20%;">
					<div style="margin-left:20px;margin-top:65px;">
						<table class="lineTable" style="width:250px;">
							<tr class="lineTableTitle">
								<td colspan="2" align="center">	
									<c:if test="${objectType == 'AD'}">机场基本信息</c:if>
									<c:if test="${objectType == 'NV'}">导航台基本信息</c:if>
									<c:if test="${objectType == 'FX'}">地标点基本信息</c:if>
									</td>
							</tr>
							<tr class="oneTdClass">
								<td width="50">类型</td>
								<td width="100">
									<c:if test="${objectType == 'AD'}">机场</c:if>
									<c:if test="${objectType == 'NV'}">导航台</c:if>
									<c:if test="${objectType == 'FX'}">地标点</c:if>
								</td>
							</tr>
							<tr class="twoTdClass">
								<td width="50">名称</td>
								<td width="80">${adBaseInfo[1] }</td>
							</tr>
							<tr class="oneTdClass">
								<td width="50">编码</td>
								<td width="80">${adBaseInfo[0] }</td>
							</tr>
							<tr class="twoTdClass">
								<td width="50">位置</td>
								<td width="80">${adBaseInfo[2] }</td>
							</tr>
							<tr class="oneTdClass">
								<td width="50">标高</td>
								<td width="80">${adBaseInfo[3] }</td>
							</tr>
						</table>
					</div>

				</div>
				
				<div style="float:right;width:80%;">
					<div  style="margin-top:20px;text-align:center;">
						<input type="button" class="setupLineButton setupLineButton7" value="&nbsp;&nbsp;&nbsp;返  回" onclick="closeSDiv(1)"/>
					</div>
					<br><br>
					<div align="center">
						<ul>
							<li>
								<a><img src="<%=path%>${attachment.attachmentPath }" alt="${attachment.attachmentRemark }" /></a>
							</li>
						</ul>
					</div>
				</div>
			</c:when>
			<c:otherwise>
				<div style="float:left;width:20%;">
					<div style="margin-left:20px;margin-top:65px;">
						<table class="lineTable" style="width:250px;">
							<tr class="lineTableTitle">
								<td colspan="2" align="center">
									<c:if test="${objectType == 'AD'}">机场基本信息</c:if>
									<c:if test="${objectType == 'NV'}">导航台基本信息</c:if>
									<c:if test="${objectType == 'FX'}">地标点基本信息</c:if>
								</td>
							</tr>
							<tr class="oneTdClass">
								<td width="50">类型</td>
								<td width="100">
									<c:if test="${objectType == 'AD'}">机场</c:if>
									<c:if test="${objectType == 'NV'}">导航台</c:if>
									<c:if test="${objectType == 'FX'}">地标点</c:if>
								</td>
							</tr>
							<tr class="twoTdClass">
								<td width="50">名称</td>
								<td width="80">${adBaseInfo[1] }</td>
							</tr>
							<tr class="oneTdClass">
								<td width="50">编码</td>
								<td width="80">${adBaseInfo[0] }</td>
							</tr>
							<tr class="twoTdClass">
								<td width="50">位置</td>
								<td width="80">${adBaseInfo[2] }</td>
							</tr>
							<tr class="oneTdClass">
								<td width="50">标高</td>
								<td width="80">${adBaseInfo[3] }</td>
							</tr>
						</table>
					</div>
 
				</div>
				
				<div style="float:right;width:80%;">
					<div  style="margin-top:20px;text-align:center;">
						<input type="button" class="setupLineButton setupLineButton7" value="&nbsp;&nbsp;&nbsp;返  回" onclick="closeSDiv(1)"/>
					</div>
					<br><br>
					<div align="center">
						<ul>
							<li>
								<a><img src="<%=path %>/attach/ad/ZUCK-AP.jpg" alt="重庆江北机场航拍图" /></a>
							</li>
						</ul>
					</div>
				</div>
			</c:otherwise>
		</c:choose>
		
	</body>
</html>