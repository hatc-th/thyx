<%@ page language="java" pageEncoding="gbk"%>
<%@ include file="/common/taglibs.jsp"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE HTML>
<html>
	<head>
		<title>ѡ��ڵ�</title>
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
									<c:if test="${objectType == 'AD'}">����������Ϣ</c:if>
									<c:if test="${objectType == 'NV'}">����̨������Ϣ</c:if>
									<c:if test="${objectType == 'FX'}">�ر�������Ϣ</c:if>
									</td>
							</tr>
							<tr class="oneTdClass">
								<td width="50">����</td>
								<td width="100">
									<c:if test="${objectType == 'AD'}">����</c:if>
									<c:if test="${objectType == 'NV'}">����̨</c:if>
									<c:if test="${objectType == 'FX'}">�ر��</c:if>
								</td>
							</tr>
							<tr class="twoTdClass">
								<td width="50">����</td>
								<td width="80">${adBaseInfo[1] }</td>
							</tr>
							<tr class="oneTdClass">
								<td width="50">����</td>
								<td width="80">${adBaseInfo[0] }</td>
							</tr>
							<tr class="twoTdClass">
								<td width="50">λ��</td>
								<td width="80">${adBaseInfo[2] }</td>
							</tr>
							<tr class="oneTdClass">
								<td width="50">���</td>
								<td width="80">${adBaseInfo[3] }</td>
							</tr>
						</table>
					</div>

				</div>
				
				<div style="float:right;width:80%;">
					<div  style="margin-top:20px;text-align:center;">
						<input type="button" class="setupLineButton setupLineButton7" value="&nbsp;&nbsp;&nbsp;��  ��" onclick="closeSDiv(1)"/>
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
									<c:if test="${objectType == 'AD'}">����������Ϣ</c:if>
									<c:if test="${objectType == 'NV'}">����̨������Ϣ</c:if>
									<c:if test="${objectType == 'FX'}">�ر�������Ϣ</c:if>
								</td>
							</tr>
							<tr class="oneTdClass">
								<td width="50">����</td>
								<td width="100">
									<c:if test="${objectType == 'AD'}">����</c:if>
									<c:if test="${objectType == 'NV'}">����̨</c:if>
									<c:if test="${objectType == 'FX'}">�ر��</c:if>
								</td>
							</tr>
							<tr class="twoTdClass">
								<td width="50">����</td>
								<td width="80">${adBaseInfo[1] }</td>
							</tr>
							<tr class="oneTdClass">
								<td width="50">����</td>
								<td width="80">${adBaseInfo[0] }</td>
							</tr>
							<tr class="twoTdClass">
								<td width="50">λ��</td>
								<td width="80">${adBaseInfo[2] }</td>
							</tr>
							<tr class="oneTdClass">
								<td width="50">���</td>
								<td width="80">${adBaseInfo[3] }</td>
							</tr>
						</table>
					</div>
 
				</div>
				
				<div style="float:right;width:80%;">
					<div  style="margin-top:20px;text-align:center;">
						<input type="button" class="setupLineButton setupLineButton7" value="&nbsp;&nbsp;&nbsp;��  ��" onclick="closeSDiv(1)"/>
					</div>
					<br><br>
					<div align="center">
						<ul>
							<li>
								<a><img src="<%=path %>/attach/ad/ZUCK-AP.jpg" alt="���콭����������ͼ" /></a>
							</li>
						</ul>
					</div>
				</div>
			</c:otherwise>
		</c:choose>
		
	</body>
</html>