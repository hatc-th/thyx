<%@ page language="java" pageEncoding="gbk" import="java.util.*"%>
<%@page import="com.hatc.hibernate.vo.AttachmentVO;"%>
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
<script src="<%=path%>/staticResource/js/jquery.js"></script>
<script type="text/javascript">
			var	zoom = 100;
			function loadPDF(){
				acro.setShowToolbar(false);
				acro.setShowScrollbars(false);
				acro.gotoFirstPage();
				acro.setZoom(100);
			}
			
			function LastPagePDF(){
				acro.gotoLastPage();
			} 			
			
			function FirstPagePDF(){
				acro.gotoFirstPage();
			}
			
			function NextPagePDF(){
				acro.gotoNextPage();
			}
			
			function PreviousPagePDF(){
				acro.gotoPreviousPage();
			}		
			
			function ZoomPage1(){
				zoom = zoom +2;
				acro.setZoom(zoom);
			}
			
			function ZoomPage2(){
				acro.setZoom(100);
			}			
			
			function ZoomPage3(){
				zoom = zoom -2;
				acro.setZoom(zoom);
			}
			
		/**
		 * 关闭弹出的div层
		 * @param flag 关闭标志
		 * 	1 表示需要关闭父页面的弹出层
		 *  2 表示需要将包含的div保留
		 */
		function closeSDiv(flag){
			var sDiv = parent.document.getElementById("sDiv");
			sDiv.parentNode.removeChild(sDiv);
		}
</script>

<body onload="loadPDF()">
		<br><br>
		<c:choose>
			<c:when test="${flag == '1'}">
				<div style="float:left;width:20%;">
					<div style="margin-left:20px;margin-top:30px;">
						<table class="lineTable" style="width:250px;">
							<tr class="lineTableTitle" align="center"><td colspan="2" >
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
					<input type="button" value="&nbsp;&nbsp;第一页" onclick="FirstPagePDF()" class="setupLineButton setupLineButton8" />
					<input type="button" value="最后一页&nbsp;" onclick="LastPagePDF()" class="setupLineButton setupLineButton11" />
					<input type="button" value="&nbsp;&nbsp上一页" onclick="PreviousPagePDF()" class="setupLineButton setupLineButton9"/>
					<input type="button" value="下一页" onclick="NextPagePDF()" class="setupLineButton setupLineButton10"/>
					<input type="button" value="&nbsp;&nbsp;&nbsp;放大2%" onclick="ZoomPage1()" class="setupLineButton setupLineButton12"/>
					<input type="button" value="&nbsp;&nbsp;&nbsp;缩小2%" onclick="ZoomPage3()" class="setupLineButton setupLineButton13"/>
					<input type="button" value="&nbsp;&nbsp;原来大小" onclick="ZoomPage2()" class="setupLineButton setupLineButton14" />
					<input type="button" value="&nbsp;&nbsp;&nbsp;返回" onclick="closeSDiv(1)" class="setupLineButton setupLineButton7"/>
					<br><br>
					<object id="acro" classid="clsid:CA8A9780-280D-11CF-A24D-444553540000" width="95%" height="1000px" border="1">
						<param name="toolbar" value="false">
						<param name="_Version" value="65539">
						<param name="_ExtentX" value="20108">
						<param name="_ExtentY" value="10866">
						<param name="_StockProps" value="0">
						<param name="_page" value="50">
						<param name="src" value="<%=path %>${attachment.attachmentPath }">
					</object>
				</div>


			</c:when>
			<c:otherwise>
			<div style="float:left;width:20%;">
					<div style="margin-left:20px;margin-top:30px;">
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
					<input type="button" value="&nbsp;&nbsp;第一页" onclick="FirstPagePDF()" class="setupLineButton setupLineButton8" />
					<input type="button" value="最后一页&nbsp;" onclick="LastPagePDF()" class="setupLineButton setupLineButton11" />
					<input type="button" value="&nbsp;&nbsp上一页" onclick="PreviousPagePDF()" class="setupLineButton setupLineButton9"/>
					<input type="button" value="下一页" onclick="NextPagePDF()" class="setupLineButton setupLineButton10"/>
					<input type="button" value="&nbsp;&nbsp;&nbsp;放大2%" onclick="ZoomPage1()" class="setupLineButton setupLineButton12"/>
					<input type="button" value="&nbsp;&nbsp;&nbsp;缩小2%" onclick="ZoomPage3()" class="setupLineButton setupLineButton13"/>
					<input type="button" value="&nbsp;&nbsp;原来大小" onclick="ZoomPage2()" class="setupLineButton setupLineButton14" />
					<input type="button" value="&nbsp;&nbsp;&nbsp;返回" onclick="closeSDiv(1)" class="setupLineButton setupLineButton7"/>
					<br><br>
					<object id="acro" classid="clsid:CA8A9780-280D-11CF-A24D-444553540000" width="95%" height="1000px" border="1">
						<param name="toolbar" value="false">
						<param name="_Version" value="65539">
						<param name="_ExtentX" value="20108">
						<param name="_ExtentY" value="10866">
						<param name="_StockProps" value="0">
						<param name="_page" value="50">
						<param name="src" value="<%=path %>/attach/ad/ZUCK-FP.pdf">
					</object>
				</div>
			</c:otherwise>
		</c:choose>


	</body>
</html>