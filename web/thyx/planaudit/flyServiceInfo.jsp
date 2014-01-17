<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%@ include file="/common/taglibs.jsp" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%-- ************************************
 * System:    ͨ�����й����������ϵͳ
 * Function�� ���мƻ�����ҳ��
 * Author:    �޷�÷
 * Copyright: ����������ϿƼ����޹�˾
 * Create:    VER1.00 2013.09.22
 * Modify:    
************************************ --%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta charset="GBK" />
<title>�鿴���мƻ�</title>
<link href="css/skin2/thyx/fPlan.css" rel="stylesheet" type="text/css"/>
<link href="css/skin2/thyx/fPlanContext.css" rel="stylesheet" type="text/css"/>
<link href="css/skin2/common/showImg.css" rel="stylesheet" type="text/css"/>
<link href="css/skin2/common/divWindow.css" rel="stylesheet" type="text/css"/>
<link href="css/skin2/common/otherPage.css" rel="stylesheet" type="text/css"/>
<script src="js/jquery-1.9.1.js" type="text/javascript"></script>
<script src="js/validata/validata.js" type="text/javascript"></script>
<script src="js/flyPlan.js" type="text/javascript"></script>
<script src="js/divWindow.js" type="text/javascript"></script>
<script src="js/calendar/syjDT.js" type="text/javascript"></script>
<script src="js/jquery.min.js" type="text/javascript"></script>
<script src="js/changePageSize.js" type="text/javascript"></script>
<script src="js/jquery.nicescroll.min.js" type="text/javascript"></script>
<link rel="stylesheet" type="text/css" href="<%=path %>/ad/jqueryPlus/autocomplete/jquery.autocomplete.css"/>
<script type='text/javascript' src='<%=path %>/ad/jqueryPlus/autocomplete/js/jquery.autocomplete.js'></script>
<!-- ��ͼ������Ҫ������ļ� -->
<%@include file = "/gis/gis.inc"%>

<script type="text/javascript">

	$(document).ready(function() {

		$(".divShowScroll").niceScroll(
			{
				touchbehavior:false,
				cursorcolor:"#ccc",
				cursoropacitymax:0.7,
				cursorwidth:5,
				cursorborder:"1px solid #ccc",
				cursorborderradius:"8px",
				background:"#143347",
				autohidemode:false
			}
		); 
	});

	//��ʾ��ͻ��Ϣ
	var rowIndex = 1;
	function showConflict(){
		var adep = $('#adep').val();
		var depDate = $('#dates').val();
		var depTime = $('#setd').val();
		var desDate = $('#setal').val();
		var desTime = $('#seta').val();
		var depICAO = $('#adepCode').val();
		var desICAO = $('#adesCode').val();
		var planeType = $('#acft').val();

		$.ajax({
			url:'<%=path%>/flyConflictAction.do?op=getFlyConflictInfo',
			data:{
				depICAO:depICAO,
				desICAO:desICAO,
				depTime:depDate + ' ' + depTime,
				desTime:desDate + ' ' + desTime,
				planeType:planeType,
				adep:adep
			},
			type:'post',
			dataType:'json',
			async:false,
			success:function(data){
			
	    		document.getElementById("conflict").style.display="block";
	    		document.getElementById("conflictBut").className="TabSecurityButton mainLineButton";
	    		
				document.getElementById("metar").style.display="none";
				document.getElementById("metarBut").className="TabSecurityButton prepareLinButton";
				document.getElementById("sigmet").style.display="none";
				document.getElementById("sigmetBut").className="TabSecurityButton prepareLinButton";
				document.getElementById("notam").style.display="none";
				document.getElementById("notamBut").className="TabSecurityButton prepareLinButton";
				
				var ICAO = "";
				var depPOS = "";
				var desPOS = "";
				// �����ر�
				var depClosedConflict="";
				// �ܼ���
				var depVisibilityConflict="";
				// ���ٳ�ͻ����
				var depWindspeedConflict="";
				// ������ɻ�����ͬһʱ���Ƿ��б�ķ��мƻ�
				var adepTimeConflict="";
				$.each(data,function(index,entry){
						ICAO = entry.src;

						if(entry.type=="�����ر�"){
							if(depClosedConflict.indexOf(entry.type) != 0) {
								depClosedConflict=depClosedConflict + entry.type + ":";
							}
							depClosedConflict = depClosedConflict + entry.startTime + '��' + entry.endTime + entry.description + ';';
						}
						if(entry.type=="�ܼ��ȵ�"){
							if(depVisibilityConflict.indexOf(entry.type) != 0) {
								depVisibilityConflict=depVisibilityConflict + entry.type + ":";
							}
							depVisibilityConflict = depVisibilityConflict + entry.startTime + '��' + entry.endTime + entry.description + ';';
						}
						if(entry.type=="��糬��"){
							if(depWindspeedConflict.indexOf(entry.type) != 0) {
								depWindspeedConflict=depWindspeedConflict + entry.type + ":";
							}
							depWindspeedConflict = depWindspeedConflict + entry.startTime + '��' + entry.endTime + entry.description + ';';
						}
						if(entry.type=="���ʱ���ͻ"){
							if(adepTimeConflict.indexOf(entry.type) != 0) {
								adepTimeConflict=adepTimeConflict + entry.type + ":";
							}
							adepTimeConflict = adepTimeConflict + entry.startTime + '��' + entry.endTime + entry.description + ';';
						}
				});
				var result = "";
				if(depClosedConflict != ""){
					result = result + depClosedConflict+'\r\n';
				}
				if(depVisibilityConflict != ""){
					result = result + depVisibilityConflict+'\r\n';
				}
				if(depWindspeedConflict != ""){
					result = result + depWindspeedConflict+'\r\n';
				}
				if(adepTimeConflict != ""){
					result = result + adepTimeConflict+'\r\n';
				}
				if(document.getElementById("areaSpace").value != ""){
					result = result + document.getElementById("areaSpace").value+'\r\n';
				}
				if(document.getElementById("heightConfilct").value != ""){
					result = result + document.getElementById("heightConfilct").value+';\r\n';
				}
				/*$('#flyConflictTbl tr[id="conflict"]').children('td').eq(1).html('<textarea  class="fPlanTextareaNoBorder2"  id="notes4" name="notes4" readonly="readonly">'
					+result.substr(0,result.lastIndexOf('\r\n'))+'</textarea>');*/
				document.getElementById("notes4").value = result.substr(0,result.lastIndexOf('\r\n'));
			},
			error:function(){
				showConfirmDiv(2,'��ȫ��ʾ��ѯʧ��!','������ʾ��Ϣ');
			},
		});
	}
	
	/**
		��ӿ����ͻ����
		@param conflictType ��ͻ����
		@param conflictReason ��ͻԭ��
		@param icao  ���룬���ڸ�����ͼ��ĳ��
	*/
	function addAreaSpaceConflictData(conflictType,conflictReason,icao){
	
		if(conflictType == 'rs_as'){
			if(conflictReason=='1'){
				conflictReason = "�����ߴ�������"+icao;
			}else{
				conflictReason = "�����ߴ�������"+icao;
			}
			if(document.getElementById("areaSpace").value != ''){
				document.getElementById("areaSpace").value=document.getElementById("areaSpace").value +conflictReason+";";
			}else {
				document.getElementById("areaSpace").value="�����ͻ:" + conflictReason +";";
			}
		}else{
			if( $('#bakHeight').val() ==0 || parseInt($('#bakHeight').val(),10) > parseInt(conflictReason,10)){
				$('#bakHeight').val(parseInt(conflictReason,10));
			}
			var reason = "���߸߶ȵ��ڰ�ȫ���ϸ߶ȣ�����������߸߶ȵ�"+ [parseInt($('#chgt').val())+parseInt(600,10)-parseInt($('#bakHeight').val(),10)]+"��";
			document.getElementById("heightConfilct").value="��ȫ�߶ȸ澯:" + reason;
		}
	}
	
	/*
		�Ƴ���ͻ����
		@param conflictType ��ͻ����   rs_as:�����ͻ     rs_ob�����и߶ȳ�ͻ
		@param lineType ��������
	*/
	function removeAreaSpaceConflictData(conflictType,lineType){
		if(conflictType == 'rs_as'){
			document.getElementById("areaSpace").value="";
		}else{
			document.getElementById("heightConfilct").value="";
		}
	}
	// ���ش���
	function goBack() {
	    showConfirmDiv(1,'��ȷ��Ҫ�˳���','��ʾ��Ϣ',function(choose){
		  	if(choose=="yes"){
				document.form1.action='<c:url value="/planAuditAction.do?method=searchList" />' ;
				document.form1.submit();
			}else{
				return;
			}
		});
	}
	
	//��ͼ�ϼ�����ʾ
	function highLightPoint(ICAO,id,from){
		serachThenWink(ICAO);
	}
</script>

</head>
<body onclick=syjDTclick() onkeyup=syjDTkeyup() onload="resetSize1();" onresize="resetSize1();">
<div id="globalDiv" class="globalDiv">
	<div class="navText">
		���мƻ���ѯ&nbsp;�����з��񽲽�
	</div>
	<form method="post" name="form1" id="form1">
		
		<!-- �����ѯҳ��Ĳ�ѯ������ -->
		<input id="flyPlanKind" name="flyPlanKind" type="hidden" value="${flyPlanKind}"/>
		<input id="planCode" name="planCode" type="hidden" value="${planCode}" />
		<input id="planName" name="planName" type="hidden" value="${planName}" />
		<input id="pilot" name="pilot" type="hidden" value="${pilot}"/>
		<input id="flyPlanAdep" name="flyPlanAdep" type="hidden" value="${flyPlanAdep}" />
		<input id="flyPlanAdes" name="flyPlanAdes" type="hidden" value="${flyPlanAdes}" />
		<input id="flyPlanAdepName" name="flyPlanAdepName" type="hidden" value="${flyPlanAdepName}" />
		<input id="flyPlanAdesName" name="flyPlanAdesName" type="hidden" value="${flyPlanAdesName}" />
		<input id="planStatus" name="planStatus" type="hidden" value="${planStatus}" />
		<input id="startDate" name="startDate" type="hidden" value="${startDate}" />
		<input id="endDate" name="endDate" type="hidden" value="${endDate}" />
		<input id="qDate" name="qDate" type="hidden" value="${qDate}" />
		<input type="hidden" id="managerType" name="managerType" value="${managerType}"/>
        <input type="hidden" id="page" name="page" value="${page}"/>
		<input type="hidden" id="pageBool" name="pageBool" value="${pageBool}"/>
		
		<input type="hidden" id="planid" name="planid" value="${tFpl.planid}"/>
		<!-- ���ڱ�������ͻ -->
		<input type="hidden" id="areaSpace" name="areaSpace" value=""/>
		<input type="hidden" id="heightConfilct" name="heightConfilct" value=""/>
		<input type="hidden" id="bakHeight" name="bakHeight" value="0"/>
	<div class="fPlanContext">
		<!--==========================================================�ı���Ϣ=========================================================================-->
		<div id="fPlanContextText" class="fPlanContextText">
			<!--==========�ı���Ϣ-���мƻ�==========-->
			<div class="fPlanLeftTop">
				<!--==========��������Բ��start===========-->
				<div class="titleLeftRound">
					<div class="roundLeft leftRound">
							<div class="roundLeftLeft"><img src="images2/left_Context_yj1.png" /></div>
							<div class="topRoundLeftRight"><div class="roundTitle">���мƻ�</div><div class="roundTitleRight"></div></div>
				  </div>
						<div class="roundRight"><img src="images2/left_Context_yj2.png" /></div>
				</div>
				<!--==========��������Բ��end===========-->
				
				<div class="fPlanLeftTopContext">
					<div class="inputTableDiv">
						<table class="inputTable">
							<tr>
								<td class="leftTd">��ɻ�����</td>
								<td class="rightTd">
									<input type="text" class="textNoBorder" value="${tFpl.adepName}" readonly="readonly"/>
									<input type="hidden" class="selectText" name="adep" id="adep" value="${tFpl.adep}"/>
									<input type="hidden" class="selectText" name="adepCode" id="adepCode" value="${tFpl.adepCode}" />
								</td>
								<td class="leftTd">Ŀ�Ļ�����</td>
								<td class="rightTd">
									<input type="text" class="textNoBorder" value="${tFpl.adesName}" readonly="readonly"/>
									<input type="hidden" class="selectText" name="ades" id="ades" value="${tFpl.ades}"/>
									<input type="hidden" class="selectText" name="adesCode" id="adesCode" value="${tFpl.adesCode}"/>
								</td>
								<td class="leftTd">���û�����</td>
								<td class="rightTd">
									<input type="text" class="textNoBorder" value="${tFpl.altn1Name}" readonly="readonly"/>
	           						<input type="hidden" name="altn1Code" id="altn1Code" value="${tFpl.altn1Code}"/>
								</td>
							</tr>
							<tr>
								<td class="leftTd">���ʱ�䣺</td>
								<td class="rightTd">
									<input type="hidden" class="selectText" name="dates" id="dates" value="<fmt:formatDate value="${tFpl.dates}"  pattern="yyyy-MM-dd"/>"/>
									<input type="hidden" class="selectText" name="setd" id="setd" value="${tFpl.setd}"/>
									<input type="text" class="textNoBorder" value="<fmt:formatDate value="${tFpl.dates}"  pattern="yyyy-MM-dd"/> ${tFpl.setd}" readonly="readonly"/>
								</td>
								<td class="leftTd">����ʱ�䣺</td>
								<td class="rightTd">
									<input type="hidden" class="selectText" name="setal" id="setal" value="<fmt:formatDate value="${tFpl.setal}"  pattern="yyyy-MM-dd"/>"/>
									<input type="hidden" class="selectText" name="seta" id="seta" value="${tFpl.seta}"/>
									<input type="text" class="textNoBorder" value="<fmt:formatDate value="${tFpl.setal}"  pattern="yyyy-MM-dd"/> ${tFpl.seta}" readonly="readonly"/>
								</td>
								<td class="leftTd">���и߶ȣ�</td>
								<td class="rightTd">
									<input type="text" name="chgt" id="chgt" class="textNoBorder" value="${tFpl.chgt} <c:if test="${not empty tFpl.chgt}">m</c:if>" readonly="readonly"/>
								</td>
							</tr>
							<tr>
								<td class="leftTd">�����ٶȣ�</td>
								<td class="rightTd">
									<input type="text" class="textNoBorder" value="${tFpl.cspd} <c:if test="${not empty tFpl.cspd}">km/h</c:if>" readonly="readonly"/>
								</td>
								<td class="leftTd">���й���</td>
								<td class="rightTd">
									<c:forEach var="item" items="${flyRuleList}">
			                			<c:if test="${tFpl.rule eq item.code_id }"><input type="text" class="textNoBorder" value="${item.name}" readonly="readonly"/></c:if>
			                    	</c:forEach>
							    </td>
								<td></td>
								<td>									
							  </td>
							</tr>	
						</table>
					</div>
					<div class="divLine"></div>
					<div class="lineButtonDiv">
						<span id="isMainLine">������</span>
						<input type="hidden" class="selectText" name="lineFlag" id="lineFlag" value="1"/><%-- ����������߱�ʾ���������ߣ�1 �� lineTableDiv1��ʾ�����ߣ�0 �� lineTableDiv2 ��ʾ������ --%>
						<input type="hidden" class="selectText" name="viewTypeFlag" id="viewTypeFlag" value="1"/><%-- ��ǵ�ǰ��ʾ�����������ߣ�1 ��ǰ��ʾ���������ߣ�0 ��ǰ��ʾ���Ǳ����� --%>
			           	
						<input type="button" class="LineButton mainLineButton"  name="mainBut" id="mainBut" value="��" onclick="showSelectLineDraw('1');showSelectLineDiv('1');"/><input type="button" class="LineButton prepareLinButton" name="bakBut" id="bakBut" value="��"  onclick="showSelectLineDraw('0');showSelectLineDiv('0');"/>
					</div>
					<div id="lineTableDiv1" class="lineTableDiv divShowScroll" >
						<table id="lineTable1" class="lineTable">
							<tr class="lineTableTitle">
								<td width="26">���</td>
								<td width="200">��&nbsp;&nbsp;&nbsp;&nbsp;��</td>
								<td width="286">��&nbsp;&nbsp;&nbsp;&nbsp;��</td>
								<td width="115">��&nbsp;&nbsp;&nbsp;&nbsp;��</td>
							</tr>
							<c:forEach var="trsMainTRs" items="${trsMainTRsPList}" varStatus="status">
			    		    	<c:choose>
				    		        <c:when test="${status.index % 2 == 0}">
				    		            <c:set var="tr_class" value="twoTdClass" scope="page" />
				    		        </c:when>
				    		        <c:otherwise>
				    		            <c:set var="tr_class" value="oneTdClass" scope="page" />
				    		        </c:otherwise>
				    		    </c:choose>
				    		    <tr class="${tr_class}">
									<td>${trsMainTRs.rsPNum}
										<input type="hidden" name="rsPNumMain" id="rsPNumMain${trsMainTRs.rsPNum}" value="${trsMainTRs.rsPNum}"/>
									</td>
									<td>${trsMainTRs.rsPCode}
										<input type="hidden" name="rsPCodeMain" id="rsPCodeMain${trsMainTRs.rsPNum}" value="${trsMainTRs.rsPCode}"/></td>
									<td>${trsMainTRs.rsPName}
										<input type="hidden" name="rsPIdMain" id="rsPIdMain${trsMainTRs.rsPNum}" value="<c:if test="${ empty copy }">${trsMainTRs.rsPId}</c:if>"/>
   		    							<input type="hidden" name="rsPNameMain" id="rsPNameMain${trsMainTRs.rsPNum}" value="${trsMainTRs.rsPName}"/>
   		    							<input type="hidden" name="rs_PPosMain" id="rs_PPosMain${trsMainTRs.rsPNum}" value="${trsMainTRs.rsPPos}"/></td>
									<td>
										<c:if test="${trsMainTRs.rsPType eq '9'}">����</c:if><c:if test="${trsMainTRs.rsPType eq '18'}">����̨</c:if>
										<c:if test="${trsMainTRs.rsPType eq '20'}">�ر��</c:if>
					    		    	<input type="hidden" name="rsPTypeMain" id="rsPTypeMain${trsMainTRs.rsPNum}" value="${trsMainTRs.rsPType}"/>
					    		    </td>
								</tr>
			    		    </c:forEach>
			    		    <!--  �������  -->
				    		<c:if test="${6 - fn:length(trsMainTRsPList) > 0}">
					    		<c:forEach begin="${fn:length(trsMainTRsPList)}" end="${5}" step="1" varStatus="status">
					    			<c:choose>
				                        <c:when test="${(status.index) % 2 == 0}">
				                            <c:set var="tr_class" value="twoTdClass" scope="page" />
				                        </c:when>
				                        <c:otherwise>
				                            <c:set var="tr_class" value="oneTdClass" scope="page" />
				                        </c:otherwise>
				                    </c:choose>
				                    <tr class="${tr_class}">
				                    	<td></td>
										<td></td>
										<td></td>
										<td></td>
				                    </tr>
					    		</c:forEach>
				    		</c:if>
						</table>
					</div>
					<div id="lineTableDiv2" class="lineTableDiv divShowScroll" style="display: none;">
						<table id="lineTable2" class="lineTable">
							<tr class="lineTableTitle">
								<td width="26">���</td>
								<td width="200">��&nbsp;&nbsp;&nbsp;&nbsp;��</td>
								<td width="286">��&nbsp;&nbsp;&nbsp;&nbsp;��</td>
								<td width="115">��&nbsp;&nbsp;&nbsp;&nbsp;��</td>
							</tr>
							
							<c:forEach var="trsBakTRs" items="${trsBakTRsPList}" varStatus="status1">
			    		    	<c:choose>
				    		        <c:when test="${status1.index % 2 == 0}">
				    		            <c:set var="tr_class" value="twoTdClass" scope="page" />
				    		        </c:when>
				    		        <c:otherwise>
				    		            <c:set var="tr_class" value="oneTdClass" scope="page" />
				    		        </c:otherwise>
				    		    </c:choose>
				    		    <tr class="${tr_class}">
									<td>${trsBakTRs.rsPNum}
										<input type="hidden" name="rsPNumBak" id="rsPNumBak${trsBakTRs.rsPNum}" value="${trsBakTRs.rsPNum}"/>
									</td>
									<td>${trsBakTRs.rsPCode}
										<input type="hidden" name="rsPCodeBak" id="rsPCodeBak${trsBakTRs.rsPNum}" value="${trsBakTRs.rsPCode}"/></td>
									<td>${trsBakTRs.rsPName}
										<input type="hidden" name="rsPIdBak" id="rsPIdBak${trsBakTRs.rsPNum}" value="<c:if test="${ empty copy }">${trsBakTRs.rsPId}</c:if>"/>
   		    							<input type="hidden" name="rsPNameBak" id="rsPNameBak${trsBakTRs.rsPNum}" value="${trsBakTRs.rsPName}"/>
   		    							<input type="hidden" name="rs_PPosBak" id="rs_PPosBak${trsBakTRs.rsPNum}" value="${trsBakTRs.rsPPos}"/></td>
									<td>
										<c:if test="${trsBakTRs.rsPType eq '9'}">����</c:if><c:if test="${trsBakTRs.rsPType eq '18'}">����̨</c:if>
										<c:if test="${trsBakTRs.rsPType eq '20'}">�ر��</c:if>
					    		    	<input type="hidden" name="rsPTypeBak" id="rsPTypeBak${trsBakTRs.rsPNum}" value="${trsBakTRs.rsPType}"/>
					    		    </td>
								</tr>
				    		    
			    		    </c:forEach>
			    		    
			    		    <!--  �������  -->
				    		<c:if test="${6 - fn:length(trsBakTRsPList) > 0}">
					    		<c:forEach begin="${fn:length(trsBakTRsPList)}" end="${5}" step="1" varStatus="status1">
					    			<c:choose>
				                        <c:when test="${(status1.index) % 2 == 0}">
				                            <c:set var="tr_class" value="twoTdClass" scope="page" />
				                        </c:when>
				                        <c:otherwise>
				                            <c:set var="tr_class" value="oneTdClass" scope="page" />
				                        </c:otherwise>
				                    </c:choose>
				                    <tr class="${tr_class}">
				                    	<td></td>
										<td></td>
										<td></td>
										<td></td>
				                    </tr>
					    		</c:forEach>
				    		</c:if>
						</table>
					</div>
				</div>
				<!--==========��������Բ��start===========-->
				<div class="round">
					<div class="roundLeft leftRound">
							<div class="roundLeftLeft"><img src="images2/Context_yj4.png" /></div>
							<div class="roundLeftRight"></div>
				  </div>
						<div class="roundRight"><img src="images2/Context_yj3.png" /></div>
				</div>
				<!--==========��������Բ��end===========-->
			</div>
			
			<!--==========================�ı���Ϣ-��ȫ��ʾ============================-->
			<div class="fPlanLeftCenter">
				<!--==========��������Բ��start===========-->
				<div class="titleLeftRound">
					<div class="roundLeft leftRound">
						<div class="roundLeftLeft"><img src="images2/left_Context_yj1.png" /></div>
						<div class="topRoundLeftRight">
						&nbsp;<input type="button" class="TabSecurityButton mainLineButton"  name="metarBut" id="metarBut" value="METAR" onclick="searchNotes('1');"/><input type="button" class="TabSecurityButton prepareLinButton" name="sigmetBut" id="sigmetBut" value="SIGMET" onclick="searchNotes('2');"/><input type="button" class="TabSecurityButton prepareLinButton" name="notamBut" id="notamBut" value="NOTAM" onclick="searchNotes('3');"/><input type="button" class="TabSecurityButton prepareLinButton" name="conflictBut" id="conflictBut" value="��ȫ��ʾ" onclick="searchNotes('4');"/>
							<div class="tabSecurityTitleRight"></div><input type="hidden" id="noteType" name="noteType"/><input type="hidden" id="fiId" name="fiId"/>
						</div>
					</div>
				<div class="roundRight"><img src="images2/left_Context_yj2.png" /></div>
				</div>
				<!--==========��������Բ��end===========-->
				<div class="fPlanLeftBottomContext">
					<div class="securityTableDiv divShowScroll" id="conflict_div">
						<table class="inputTableTo" id="flyConflictTbl">
							<tr id="metar">
								<td style="width:80px;" class="leftTd">METAR��</td>
								<td class="rightTd"><textarea  class="fPlanTextareaNoBorder2" id="notes1" name="notes1" readonly="readonly">${notesStr}</textarea></td>
							</tr>
							<tr id="sigmet" style="display:none;">
								<td style="width:80px;" class="leftTd">SIGMET��</td>
								<td class="rightTd"><textarea  class="fPlanTextareaNoBorder2" id="notes2" name="notes2" readonly="readonly"></textarea></td>
							</tr>
							<tr id="notam" style="display:none;">
								<td style="width:80px;" class="leftTd">NOTAM��</td>
								<td class="rightTd"><textarea  class="fPlanTextareaNoBorder2" id="notes3" name="notes3" readonly="readonly"></textarea></td>
							</tr>
							<tr id="conflict" style="display:none;">
								<td style="width:80px;" class="leftTd">��ȫ��ʾ��</td>
								<td class="rightTd"><textarea  class="fPlanTextareaNoBorder2" id="notes4" name="notes4" readonly="readonly"></textarea></td>
							</tr>
							<tr>
								<td style="width:80px;" class="leftTd">��ע��</td>
								<td class="rightTd"><textarea  class="fPlanTextareaNoBorder" id="remark" name="remark" onfocus="showBorder(this,'1')" onblur="hideBorder(this,'1')"></textarea></td>
							</tr>
						</table>
					</div>
				</div>				
				<!--==========��������Բ��start===========-->
				<div class="round">
					<div class="roundLeft leftRound">
							<div class="roundLeftLeft"><img src="images2/Context_yj4.png" /></div>
							<div class="roundLeftRight"></div>
				  </div>
						<div class="roundRight"><img src="images2/Context_yj3.png" /></div>
				</div>
				<!--==========��������Բ��end===========-->
			</div>
			
			<!--==========�ı���Ϣ-���мƻ�������Ϣ==========-->
			<div class="fPlanLeftBottom">
				<!--==========��������Բ��start===========-->
				<div class="titleLeftRound">
					<div class="roundLeft leftRound">
							<div class="roundLeftLeft"><img src="images2/left_Context_yj1.png" /></div>
							<div class="topRoundLeftRight"><div class="roundTitle">������Ϣ</div><div class="roundTitleRight"></div></div>
				  </div>
						<div class="roundRight"><img src="images2/left_Context_yj2.png" /></div>
				</div>
				<!--==========��������Բ��end===========-->
				
				<div class="fPlanLeftCenterContext">
					<div class="inputTableDiv">
						<table class="inputTableTo">
							<tr>
								<td class="leftTd">�ƻ����ƣ�</td>
								<td class="rightTd">
									<input type="text" class="textNoBorder" value="${tFpl.FPlanName}" readonly="readonly"/>
								</td>
								<td class="leftTd">���б�ţ�</td>
								<td class="rightTd">
									<input type="text" class="textNoBorder" value="${tFpl.FPlanCode}" readonly="readonly"/>
								</td>
								<td class="leftTd">�ƻ����</td>
								<td class="rightTd">
									<input type="text" class="textNoBorder" value="ͨ��һ���Է��мƻ�" readonly="readonly"/>
								</td>
							</tr>
							<tr>
								<td class="leftTd">�������ͣ�</td>
								<td class="rightTd">
									<c:forEach var="item" items="${flyTypeList}">
		                    			<c:if test="${tFpl.types eq item.code_id}"><input type="text" class="textNoBorder" value="${item.name}" readonly="readonly"/></c:if>
		                    		</c:forEach>
								</td>
								<td class="leftTd">����ʱ����</td>
								<td class="rightTd">
									<input type="text" class="textNoBorder" value="${tFpl.eet}" readonly="readonly"/>
								</td>
								<td class="leftTd">���չ�˾��</td>
								<td class="rightTd">
									<c:forEach var="item" items="${airlineCompanyList}">
										<c:if test="${tFpl.unit eq item.code_id}"><input type="text" class="textNoBorder" value="${item.name}" readonly="readonly"/></c:if> 
			                    	</c:forEach>
								</td>
							</tr>
							<tr>
								<td class="leftTd">����Ա��</td>
								<td class="rightTd">
									<c:forEach var="item" items="${pilotList}">
			                    		<c:if test="${tFpl.commander eq item.code_id}"><input type="text" class="textNoBorder" value="${item.name}" readonly="readonly"/></c:if>
			                    	</c:forEach>
								</td>
								<td class="leftTd">�������ͺţ�</td>
								<td class="rightTd" colspan="3">
									<c:forEach var="item" items="${airCraftTypeList}">
			                    		<c:if test="${tFpl.acft eq item.code_id}">${item.name}</c:if> 
			                    	</c:forEach>
			                    	<input type="hidden" name="acft" id="acft" value="${tFpl.acft}"/>
							    </td>
								
							</tr>
							<tr>
								<td class="leftTd">��������ţ�</td>
								<td class="rightTd">
									<c:forEach var="item" items="${airCraftSnList}">
			                    		<c:if test="${tFpl.acid eq item.code_id}"><input type="text" class="textNoBorder" value="${item.name}" readonly="readonly"/></c:if> 
			                    	</c:forEach>
								</td>
								<td class="leftTd">�����豸��</td>
								<td class="rightTd">
									<c:forEach var="item" items="${flyEquipmentList}">
			                    		<c:if test="${tFpl.equip eq item.code_id}"><input type="text" class="textNoBorder" value="${item.name}" readonly="readonly"/></c:if>
			                    	</c:forEach>
								</td>
								<td class="leftTd">�Ƿ���Ҫ���ѣ�</td>
								<td class="rightTd">
									<c:if test="${tFpl.alarm eq '1'}"><input type="text" class="textNoBorder" value="��" readonly="readonly"/></c:if>
			                    	<c:if test="${tFpl.alarm eq '0'}"><input type="text" class="textNoBorder" value="��" readonly="readonly"/></c:if>
							    </td>
							</tr>
							<tr>
								<td class="leftTd">��ע��</td>
								<td class="rightTd" colspan="5">
									<textarea  class="fPlanTextareaNoBorder3"  id="remark" name="remark" readonly="readonly" >${tFpl.remark}</textarea>
								</td>
							</tr>
						</table>
					</div>
				</div>				
				<!--==========��������Բ��start===========-->
				<div class="round">
					<div class="roundLeft leftRound">
							<div class="roundLeftLeft"><img src="images2/Context_yj4.png" /></div>
							<div class="roundLeftRight"></div>
				  </div>
						<div class="roundRight"><img src="images2/Context_yj3.png" /></div>
				</div>
				<!--==========��������Բ��end===========-->
			</div>
		</div>
		
		<!--==========================================================ͼƬ��Ϣ=========================================================================-->
		<div id="fPlanContextImg" class="fPlanContextImg">
			<!--==========ͼƬ��Ϣ-��ͼ==========-->
			<div id="fPlanRightTop" class="fPlanRightTop">
				<!--==========��������Բ��start===========-->
				<div id="mapRound1" class="mapRound">
					<div id="mapLeftRound1"  class="mapLeftRound">
							<div class="mapRoundLeftLeft"><img src="images2/map_context_yj1.png" /></div>
							<div id="mapRoundLeftRight1" class="mapRoundLeftRight"></div>
				    </div>
					<div class="mapRoundRight"><img src="images2/map_context_yj2.png" /></div>
				</div>
				<!--==========��������Բ��end===========-->				
				
				<div id="fPlanRightTopContext" class="fPlanRightTopContext">
					<div id="controlPane" style="cursor:pointer;text-align:center;padding-top:5px;position:absolute;left:1512px;top:52px;z-index: 10000;border:1px solid green;width:120px;height:15px;overflow-y:auto;overflow-x:hidden;background:grey;" onclick="shPane()">ͼ�����</div>
					<div class="showMapDiv" id="showMapDiv">
						<input type="hidden" id="userroleuicode" value="${sessionUserUICode}"/>
						<div id="map" style="width:100%;height:100%;background:#dff8c3;"></div>
						<!--==========����ͼdiv==========-->
						<div id="contentPane" style="cursor:pointer;text-align:left;position:absolute;left:1512px;top:72px;border:1px solid green;width:120px;height:200px;overflow-y:auto;overflow-x:hidden;background:grey;display:none;">
					    </div>
					</div>
					<div class="showAirdromeImgDiv" id="showAirdromeImgDiv">
						<div class="closeBImg">
							<img src="images/th_xtb_tc.png" id="closeAIDiv"  title="�ص���ͼ" onclick="closeBigImgage('showAirdromeImgDiv','showMapDiv')" width="21" height="21"/>
							<span id="info" style="position:absolute; right:25px; bottom:5px; color:#000; z-index:50;"></span>
						</div>
						<img id="airdromeImg" src=""/>
					</div>
					<div class="foldImgDiv" id="foldImgDiv">
						<div class="mapCloseBImg">
							<img src="images/qxdj.png"  title="ȡ������" onclick="closeBigImgage('foldImgDiv','showMapDiv')" width="24" height="21"/>
						</div>
						<img id="foldImg" src=""/>
					</div>
					<div class="blowupDiv" id="blowupDiv" onclick="blowupMapDiv()" title="�Ŵ��ͼ"></div>
				</div>
				
				<!--==========��������Բ��start===========-->
				<div id="mapRound2" class="mapRound">
					<div id="mapLeftRound2"  class="mapLeftRound mapLeftRoundTo">
							<div class="mapRoundLeftLeft"><img src="images2/map_context_yj4.png" /></div>
							<div id="mapRoundLeftRight2" class="mapRoundLeftRight bMapRoundLeftRight"></div>
				    </div>
					<div class="mapRoundRight mapRoundRightTo"><img src="images2/map_context_yj3.png" /></div>
				</div>
				<!--==========��������Բ��end===========-->
			
			</div>
			<!--==========ͼƬ��Ϣ-����ͼ==========-->
			<div id="fPlanRightBottom" class="fPlanRightBottom">
				<!--==========��������Բ��start===========-->
					<div id="mapRound3" class="mapRound">
						<div id="mapLeftRound3" class="mapLeftRound">
								<div class="mapRoundLeftLeft"><img src="images2/map_context_yj1.png" /></div>
								<div id="mapRoundLeftRight3" class="mapRoundLeftRight"></div>
						</div>
						<div class="mapRoundRight"><img src="images2/map_context_yj2.png" /></div>
					</div>
					<!--==========��������Բ��end===========-->
					
					
				<div id="fPlanRightBottomContext" class="fPlanRightBottomContext">
					<div id="chartNode" data-dojo-type="dojox.charting.widget.Chart2D"  style="width:840px;height:150px;margin-left:5px;"></div>
				</div>	
				
				
					<!--==========��������Բ��start===========-->
					<div  id="mapRound4" class="mapRound">
						<div id="mapLeftRound4" class="mapLeftRound mapLeftRoundTo">
								<div class="mapRoundLeftLeft"><img src="images2/map_context_yj4.png" /></div>
								<div id="mapRoundLeftRight4" class="mapRoundLeftRight bMapRoundLeftRight"></div>
						</div>
						<div class="mapRoundRight mapRoundRightTo"><img src="images2/map_context_yj3.png" /></div>
					</div>
					<!--==========��������Բ��end===========-->
			</div>
			<!--  
			<div class="fPlanRightBottom">
				<div class="fPlanRightBottomContext">
					<div id="sImg">
						<table cellpadding="0" cellspacing="0" class="fourWeatherImgTable">
							<tr>
								<td><img src="images/weather/dbfx_01.jpg" onclick="showBigImgage('bImgDiv','sImg','bImg',this,'','',null,'1')"></td>
								<td><img src="images/weather/ld_01.gif" onclick="showBigImgage('bImgDiv','sImg','bImg',this,'','',null,'2')"></td>
							</tr>
							<tr>
								<td><img src="images/weather/wx_01.jpg" onclick="showBigImgage('bImgDiv','sImg','bImg',this,'','',null,'3')"></td>
								<td><img src="images/weather/rain_01.jpg" onclick="showBigImgage('bImgDiv','sImg','bImg',this,'','',null,'4')"></td>
							</tr>
					  </table>
				  </div>
				  <div id="bImgDiv">
  				  	<div class="imgLeftward" onclick="changeImg('1','bImg')" title="��һ��">&nbsp;</div>
				  	<div class="imgRightward" onclick="changeImg('2','bImg')" title="��һ��">&nbsp;</div>
				  	<div class="closeBImg">
				  		<img src="images/dj.png"  title="����" onclick="showBigImgage('sImg','bImgDiv','foldImg','','','foldImgDiv')" width="24" height="21">
				  		<img src="images/gb.png"  title="�ر�" onclick="closeBigImgage('bImgDiv','sImg')" width="24" height="21">
				  	</div>
				  	<div class="bImgMessage" id="bImgMessage"></div>
				  	<input type="hidden" value="dbfx_01.jpg_#_dbfx_02.jpg" id="imgNames1"/>
				  	<input type="hidden" value="ld_01.gif_#_ld_02.gif" id="imgNames2"/>
				  	<input type="hidden" value="wx_01.jpg_#_wx_02.jpg" id="imgNames3"/>
				  	<input type="hidden" value="rain_01.jpg_#_rain_02.jpg" id="imgNames4"/>
				  	<input type="hidden" value="" id="imgFlag"/>
					<input type="hidden" value="" id="curImgName">
				  	<img id="bImg" src=""/>
				  </div>
				</div>			
			</div>-->
		 </div>
	</div>		
	<!--==========================================================�����˵�����Ȩ��Ϣ=========================================================================-->
	<div class="bottomButtonArea">
		<div class="buttonArea">
			<input class="buttonArea1" type="button" value="  �������" onclick="saveExplain();"/> <input class="buttonArea3" type="button" value="  �� ��" onclick="goBack();"/>
		</div>
	</div>
	</form>
</div>
</body>
<script type="text/javascript">
    // ��ʼ��ҳ��ʱ����ͼ������ɽ������ڵ�ͼ��ʾ
	function createToolbar(map) {
		toolbar = new esri.toolbars.Draw(map);
		
		dojo.connect(toolbar, "onDrawEnd", addToMap);
		
		getPointInfoByCode(dojo.byId('adepCode').value);
		getPointInfoByCode(dojo.byId('adesCode').value);
		getPointInfoByCode(dojo.byId('altn1Code').value);
		onLoadGisLine();
		
	}
	
	// ����index��ѯ��ͬ�Ľ������� 1���������� 2���ش����� 3������ͨ�� 4����ȫ��ʾ
	function searchNotes(index){
		document.getElementById("noteType").value=index;
					
		if(index == "4") {
			showConflict();
		} else {
			var jsondata = $("#form1").serialize();
			$.post('<c:url value="/planAuditAction.ax?method=searchFlyServiceInfo"/>', jsondata, function(data){
		    	if(data.data.returnflag == 'true') {
		    		var noteType = document.getElementById("noteType").value;
			    	if(noteType == '1'){
				    	document.getElementById("metar").style.display="block";
	    				document.getElementById("metarBut").className="TabSecurityButton mainLineButton";
			    		document.getElementById("notes1").value=data.data.notesStr;
					}else{
						document.getElementById("metar").style.display="none";
						document.getElementById("metarBut").className="TabSecurityButton prepareLinButton";
						document.getElementById("conflict").style.display="none";
						document.getElementById("conflictBut").className="TabSecurityButton prepareLinButton";
					}
					if(noteType == '2'){
			    		document.getElementById("sigmet").style.display="block";
			    		document.getElementById("sigmetBut").className="TabSecurityButton mainLineButton";
			    		document.getElementById("notes2").value=data.data.notesStr;
					}else{
						document.getElementById("sigmet").style.display="none";
						document.getElementById("sigmetBut").className="TabSecurityButton prepareLinButton";
						document.getElementById("conflict").style.display="none";
						document.getElementById("conflictBut").className="TabSecurityButton prepareLinButton";
					}
					if(noteType == '3'){
			    		document.getElementById("notam").style.display="block";
			    		document.getElementById("notamBut").className="TabSecurityButton mainLineButton";
			    		document.getElementById("notes3").value=data.data.notesStr;
					}else{
						document.getElementById("notam").style.display="none";
						document.getElementById("notamBut").className="TabSecurityButton prepareLinButton";
						document.getElementById("conflict").style.display="none";
						document.getElementById("conflictBut").className="TabSecurityButton prepareLinButton";
					}
		    		
		    	}else {
		    		if(noteType == '1'){
		    			showConfirmDiv(2,'���������ѯʧ��!','������ʾ��Ϣ');
		    		} else if(noteType == '2'){
		    			showConfirmDiv(2,'�ش������ѯʧ��!','������ʾ��Ϣ');
		    		} else if(noteType == '3'){
		    			showConfirmDiv(2,'����ͨ���ѯʧ��!','������ʾ��Ϣ');
		    		}
	    		}
	   		},"json");
		}
	}
	
	function saveExplain(){
		var jsondata = $("#form1").serialize();
		$.post('<c:url value="/planAuditAction.ax?method=saveFlyServiceInfo"/>', jsondata, function(data){
	    	if(data.data.returnflag == 'true') {
	    		document.getElementById("fiId").value=data.data.fiId;
		    	showConfirmDiv(2,'�������ݱ���ɹ�!','������ʾ��Ϣ');
	    	}else {
	    		showConfirmDiv(2,'�������ݲ�ѯʧ��!','������ʾ��Ϣ');
	    	}
	    },"json");
	}
</script>
</html>