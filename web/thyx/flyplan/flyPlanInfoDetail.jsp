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
<link href="css/skin2/thyx/fPlan.css" rel="stylesheet" type="text/css">
<link href="css/skin2/thyx/fPlanContext.css" rel="stylesheet" type="text/css">
<link href="css/skin2/common/showImg.css" rel="stylesheet" type="text/css">
<link href="css/skin2/common/divWindow.css" rel="stylesheet" type="text/css">
<link href="css/skin2/common/otherPage.css" rel="stylesheet" type="text/css">
<script src="js/jquery-1.9.1.js" type="text/javascript"></script>
<script src="js/validata/validata.js" type="text/javascript"></script>
<script src="js/flyPlan.js" type="text/javascript"></script>
<script src="js/divWindow.js" type="text/javascript"></script>
<script src="js/calendar/syjDT.js" type="text/javascript"></script>
<script src="js/jquery.min.js" type="text/javascript"></script>
<script src="js/changePageSize.js" type="text/javascript"></script>
<script src="js/jquery.nicescroll.min.js" type="text/javascript"></script>
<link rel="stylesheet" type="text/css" href="<%=path %>/ad/jqueryPlus/autocomplete/jquery.autocomplete.css"/>
<script type='text/javascript' src='<%=path %>/ad/jqueryPlus/autocomplete/js/jquery.autocomplete.js'></script><base>
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
	
	showConflict();		
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
		
		if( !checkFlyPlanFormInfo() ){
			return ;
		}
		if( !checkElementFilled('acft','��ѡ�����������!') ){
			return ;
		}
		var removeRow = $('#flyConflictTbl tr[src="db"]');
		$.each(removeRow,function(){
			$(this).removeAttr('icao');
			$(this).removeAttr('src');
			if($(this).index() > 5){
				$(this).remove();
			}else{
				$(this).children('td').html('');
			}
			rowIndex--;
		});
		var tbl = $('#flyConflictTbl');
		var desRow = null;
		var curClass = '';
		
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
				var ICAO = "";
				var depPOS = "";
				var desPOS = "";
				$.each(data,function(index,entry){
						desRow = createConflictRow(rowIndex);
						desRow.attr('id',entry.id);
						desRow.attr('from',entry.from);
						desRow.attr('src','db');//������Դ����ͼ�����ݿ⡿
						if(rowIndex > 5){
							desRow.appendTo(tbl);
						}
						desRow.dblclick(function(){
						ICAO = entry.src;
						switch(ICAO){
							case "depICAO":
							highLightPoint(depICAO,entry.id,entry.from);//���ݻ������ƻ����Ǵ��붨λ
							break;
							
							case "desICAO":
							highLightPoint(desICAO,entry.id,entry.from);
							break;
						}	
						});
	
						desRow.children('td').eq(0).html(rowIndex);
						desRow.children('td').eq(1).html(entry.type);
						desRow.children('td').eq(2).html(entry.description);
						desRow.children('td').eq(3).html(entry.startTime + ' �� ' + entry.endTime);
						rowIndex++;
				});
			},
		});
		$(".divShowScroll")[2].autohidemode = false;
	}
	
	/*
		�Ƴ���ͻ����
		@param conflictType ��ͻ����   rs_as:�����ͻ     rs_ob�����и߶ȳ�ͻ
		@param lineType ��������
	*/
	function removeAreaSpaceConflictData(conflictType,lineType){
		var row;
		if(conflictType == "rs_as"){ 
			row = $('#flyConflictTbl tr[icao="'+lineType+'"]');
		} else if(conflictType == "rs_ob"){
			row = $('#flyConflictTbl tr[src="rs_ob"]');
		}
		$.each(row,function(){
			$(this).removeAttr('icao');
			$(this).removeAttr('src');
			$(this).remove();
			rowIndex--;
		});
		resetLineIndex();
	}
	
	//���ɳ�ͻ������table��
	function createConflictRow(rowIndex){
		var desRow = null;
		if(rowIndex%2  == 1){
			curClass = "oneTdClass";	
		}else{
			curClass = "twoTdClass";
		}
		
		if(rowIndex > 5){
			desRow = $('#flyConflictTbl tr:first').clone();
			desRow.addClass(curClass).removeClass('lineTableTitle');
		}else{
			desRow = $('#flyConflictTbl tr').eq(rowIndex);
		}
		desRow.children('td').eq(2).attr("style","text-align:left");
		return desRow;
	}
	
	/*
		��ӿ����ͻ����
		@param conflictType ��ͻ����
		@param conflictReason ��������
		@param icao  ���룬���ڸ�����ͼ��ĳ��
	*/
	function addAreaSpaceConflictData(conflictType,conflictReason,icao){
		
		var tbl = $('#flyConflictTbl');
		var selector;
		
		if(conflictType == "rs_as"){
			selector = '#flyConflictTbl tr[icao="' + icao + conflictReason + '"]';
		}else{
			removeAreaSpaceConflictData("rs_ob");
			if( $('#bakHeight').val() ==0 || parseInt($('#bakHeight').val(),10) > parseInt(conflictReason,10)){
				$('#bakHeight').val(parseInt(conflictReason,10));
			}
			selector = '#flyConflictTbl tr[src="' + conflictType + '"]';
		}

		if($(selector).length == 0){
			desRow = createConflictRow(rowIndex);
			if(conflictType == "rs_as"){
				desRow.attr('src','rs_as');//��ʾ��ͻ����
				conflictValue="�����ͻ";
				if(conflictReason =='1'){
					reason = "�����ߴ�������"+icao;
				}else{
					reason = "�����ߴ�������"+icao;
				}
				/**
				desRow.dblclick(function(){
					serachThenWink(icao);
				});**/
			}else {
				desRow.attr('src','rs_ob');//��ʾ��ͻ����
				conflictValue="��ȫ�߶ȸ澯";
				reason = "���߸߶ȵ��ڰ�ȫ���ϸ߶ȣ�����������߸߶ȵ�";
				reason = reason + [parseInt($('#chgt').val(),10)+parseInt(600,10)-parseInt($('#bakHeight').val(),10)]+"��";
				
			}
			if(rowIndex > 5){
				desRow.appendTo(tbl);
			}
			desRow.attr('icao',conflictReason);
			desRow.children('td').eq(0).html(rowIndex);
			desRow.children('td').eq(1).html(conflictValue);
			desRow.children('td').eq(2).html(reason);
			desRow.children('td').eq(3).html('');
			
			rowIndex++;
		}
		$(".divShowScroll")[2].autohidemode = false;
	}
	/*
	 * ������ʽ����������4��ʱ���հ���
	 */
	function resetLineIndex(){
		var newRow = $('#flyConflictTbl tr');
		$.each(newRow,function(){
			if($(this).index() > 0 ){
				if($(this).index()%2  == 1){
					curClass = "oneTdClass";	
				}else{
					curClass = "twoTdClass";
				}
				$(this).attr("class",curClass);
				if($(this).index() < rowIndex){
					$(this).children('td').eq(0).html($(this).index());
				}
			}
		});
		
		// �������е���������4��ʱ���հ���
		var len = $('#flyConflictTbl tr').length;  
		for(var m=len-1 ;m<5;m++){  // ��Ϊ��ͷռһ�У�����Ҫ-1
			if(m%2  == 0){
				curClass = "oneTdClass";	
			}else{
				curClass = "twoTdClass";
			}
			desRow = $('#flyConflictTbl tr:first').clone();
			desRow.attr("class",curClass);
			desRow.children('td').html('');
			desRow.appendTo($('#flyConflictTbl'));
		}
	}
	//��ͼ�ϼ�����ʾ
	function highLightPoint(ICAO,id,from){
		// serachThenWink(ICAO);
	}
</script>

</head>
<body onclick=syjDTclick() onkeyup=syjDTkeyup() onload="resetSize1();" onresize="resetSize1();">
<div id="globalDiv" class="globalDiv">
	<div class="navText">
		���мƻ���ѯ&nbsp;�����мƻ��鿴
	</div>
	<form method="post" name="form1" id="form1">
		
		<input id="flyPlanCode" name="flyPlanCode" type="hidden" value="${flyPlanCode}" />
		<input id="flyPlanName" name="flyPlanName" type="hidden" value="${flyPlanName}" />
		<input id="flyPlanAdep" name="flyPlanAdep" type="hidden" value="${flyPlanAdep}" />
		<input id="flyPlanAdes" name="flyPlanAdes" type="hidden" value="${flyPlanAdes}" />
		<input id="flyPlanAdepName" name="flyPlanAdepName" type="hidden" value="${flyPlanAdepName}" />
		<input id="flyPlanAdesName" name="flyPlanAdesName" type="hidden" value="${flyPlanAdesName}" />
		<input id="fplPlanClass" name="fplPlanClass" type="hidden" value="${fplPlanClass}" />
		<input id="fplStatus" name="fplStatus" type="hidden" value="${fplStatus}" />
		<input id="startDate" name="startDate" type="hidden" value="${startDate}" />
		<input id="endDate" name="endDate" type="hidden" value="${endDate}" />
		<input id="qDate" name="qDate" type="hidden" value="${qDate}" />
		<input type="hidden" id="searchType" name="searchType" value="${searchType}"/>
        <input type="hidden" id="page" name="page" value="${page}"/>
		<input type="hidden" id="pageBool" name="pageBool" value="${pageBool}"/>
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
									<input type="text" class="textNoBorder" value="${tFpl.adepName}" readonly>
									<input type="hidden" class="selectText" name="adep" id="adep" value="${tFpl.adep}">
									<input type="hidden" class="selectText" name="adepCode" id="adepCode" value="${tFpl.adepCode}" />
								</td>
								<td class="leftTd">Ŀ�Ļ�����</td>
								<td class="rightTd">
									<input type="text" class="textNoBorder" value="${tFpl.adesName}" readonly>
									<input type="hidden" class="selectText" name="ades" id="ades" value="${tFpl.ades}">
									<input type="hidden" class="selectText" name="adesCode" id="adesCode" value="${tFpl.adesCode}"/>
								</td>
								<td class="leftTd">���û�����</td>
								<td class="rightTd">
									<input type="text" class="textNoBorder" value="${tFpl.altn1Name}" readonly>
	           						<input type="hidden" name="altn1Code" id="altn1Code" value="${tFpl.altn1Code}"/>
								</td>
							</tr>
							<tr>
								<td class="leftTd">���ʱ�䣺</td>
								<td class="rightTd">
									<input type="hidden" class="selectText" name="dates" id="dates" value="<fmt:formatDate value="${tFpl.dates}"  pattern="yyyy-MM-dd"/>">
									<input type="hidden" class="selectText" name="setd" id="setd" value="${tFpl.setd}">
									<input type="text" class="textNoBorder" value="<fmt:formatDate value="${tFpl.dates}"  pattern="yyyy-MM-dd"/> ${tFpl.setd}" readonly>
								</td>
								<td class="leftTd">����ʱ�䣺</td>
								<td class="rightTd">
									<input type="hidden" class="selectText" name="setal" id="setal" value="<fmt:formatDate value="${tFpl.setal}"  pattern="yyyy-MM-dd"/>">
									<input type="hidden" class="selectText" name="seta" id="seta" value="${tFpl.seta}">
									<input type="text" class="textNoBorder" value="<fmt:formatDate value="${tFpl.setal}"  pattern="yyyy-MM-dd"/> ${tFpl.seta}" readonly>
								</td>
								<td class="leftTd">���и߶ȣ�</td>
								<td class="rightTd">
									<input type="text" name="chgt" id="chgt" class="textNoBorder" value="${tFpl.chgt} <c:if test="${not empty tFpl.chgt}">m</c:if>" readonly>
								</td>
							</tr>
							<tr>
								<td class="leftTd">�����ٶȣ�</td>
								<td class="rightTd">
									<input type="text" class="textNoBorder" value="${tFpl.cspd} <c:if test="${not empty tFpl.cspd}">km/h</c:if>" readonly>
								</td>
								<td class="leftTd">���й���</td>
								<td class="rightTd">
									<c:forEach var="item" items="${flyRuleList}">
			                			<c:if test="${tFpl.rule eq item.code_id }"><input type="text" class="textNoBorder" value="${item.name}" readonly></c:if>
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
						<input type="hidden" class="selectText" name="lineFlag" id="lineFlag" value="1"><%-- ����������߱�ʾ���������ߣ�1 �� lineTableDiv1��ʾ�����ߣ�0 �� lineTableDiv2 ��ʾ������ --%>
						<input type="hidden" class="selectText" name="viewTypeFlag" id="viewTypeFlag" value="1"><%-- ��ǵ�ǰ��ʾ�����������ߣ�1 ��ǰ��ʾ���������ߣ�0 ��ǰ��ʾ���Ǳ����� --%>
			           	
						<input type="button" class="LineButton mainLineButton"  name="mainBut" id="mainBut" value="��" onClick="showSelectLineDraw('1');showSelectLineDiv('1');"/><input type="button" class="LineButton prepareLinButton" name="bakBut" id="bakBut" value="��"  onClick="showSelectLineDraw('0');showSelectLineDiv('0');"/>
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
			<!--==========�ı���Ϣ-���мƻ�������Ϣ==========-->
			<div class="fPlanLeftCenter">
				<!--==========��������Բ��start===========-->
				<div class="titleLeftRound">
					<div class="roundLeft leftRound">
						<div class="roundLeftLeft"><img src="images2/left_Context_yj1.png" /></div>
						<div class="topRoundLeftRight"><div class="roundTitle">��ȫ��ʾ</div><div class="roundTitleRight"></div>	</div>
					</div>
					<div class="roundRight"><img src="images2/left_Context_yj2.png" /></div>
				</div>
				<!--==========��������Բ��end===========-->
				<div class="fPlanLeftBottomContext">
					<div class="securityTableDiv divShowScroll" id="conflict_div">
						<table class="lineTable" id="flyConflictTbl">
							<tr class="lineTableTitle">
								<td width="26">���</td>
								<td width="100">��ͻ����</td>
								<td width="180">��ͻԭ��</td>
								<td width="180">��ͻʱ��</td>
							</tr>
							<tr class="twoTdClass">
								<td></td>
								<td></td>
								<td></td>
								<td></td>
							</tr>
							<tr class="oneTdClass">
								<td></td>
								<td></td>
								<td></td>
								<td></td>
							</tr>
							<tr class="twoTdClass">
								<td></td>
								<td></td>
								<td></td>
								<td></td>
							</tr>
							<tr class="oneTdClass">
								<td></td>
								<td></td>
								<td></td>
								<td></td>
							</tr>
							<tr class="twoTdClass">
								<td></td>
								<td></td>
								<td></td>
								<td></td>
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
			
			<!--==========================�ı���Ϣ-��ȫ��ʾ============================-->
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
									<input type="text" class="textNoBorder" value="${tFpl.FPlanName}" readonly>
								</td>
								<td class="leftTd">���б�ţ�</td>
								<td class="rightTd">
									<input type="text" class="textNoBorder" value="${tFpl.FPlanCode}" readonly>
								</td>
								<td class="leftTd">�ƻ����</td>
								<td class="rightTd">
									<input type="text" class="textNoBorder" value="ͨ��һ���Է��мƻ�" readonly>
								</td>
							</tr>
							<tr>
								<td class="leftTd">�������ͣ�</td>
								<td class="rightTd">
									<c:forEach var="item" items="${flyTypeList}">
		                    			<c:if test="${tFpl.types eq item.code_id}"><input type="text" class="textNoBorder" value="${item.name}" readonly></c:if>
		                    		</c:forEach>
								</td>
								<td class="leftTd">����ʱ����</td>
								<td class="rightTd">
									<input type="text" class="textNoBorder" value="${tFpl.eet}" readonly>
								</td>
								<td class="leftTd">���չ�˾��</td>
								<td class="rightTd">
									<c:forEach var="item" items="${airlineCompanyList}">
										<c:if test="${tFpl.unit eq item.code_id}"><input type="text" class="textNoBorder" value="${item.name}" readonly></c:if> 
			                    	</c:forEach>
								</td>
							</tr>
							<tr>
								<td class="leftTd">����Ա��</td>
								<td class="rightTd">
									<c:forEach var="item" items="${pilotList}">
			                    		<c:if test="${tFpl.commander eq item.code_id}"><input type="text" class="textNoBorder" value="${item.name}" readonly></c:if>
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
			                    		<c:if test="${tFpl.acid eq item.code_id}"><input type="text" class="textNoBorder" value="${item.name}" readonly></c:if> 
			                    	</c:forEach>
								</td>
								<td class="leftTd">�����豸��</td>
								<td class="rightTd">
									<c:forEach var="item" items="${flyEquipmentList}">
			                    		<c:if test="${tFpl.equip eq item.code_id}"><input type="text" class="textNoBorder" value="${item.name}" readonly></c:if>
			                    	</c:forEach>
								</td>
								<td class="leftTd">�Ƿ���Ҫ���ѣ�</td>
								<td class="rightTd">
									<c:if test="${tFpl.alarm eq '1'}"><input type="text" class="textNoBorder" value="��" readonly></c:if>
			                    	<c:if test="${tFpl.alarm eq '0'}"><input type="text" class="textNoBorder" value="��" readonly></c:if>
							    </td>
							</tr>
							<tr id="1">
								<td class="leftTd">��ע��</td>
								<td class="rightTd" colspan="5" >
								<textarea  class="fPlanTextareaNoBorder3"  id="remark" name="remark" readonly >${tFpl.remark}</textarea>
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
						<input type="hidden" id="userroleuicode" value="${sessionUserUICode}">
						<div id="map" style="width:100%;height:100%;background:#dff8c3;"></div>
						<!--==========����ͼdiv==========-->
						<div id="contentPane" style="cursor:pointer;text-align:left;position:absolute;left:1512px;top:72px;border:1px solid green;width:120px;height:200px;overflow-y:auto;overflow-x:hidden;background:grey;display:none;">
					    </div>
					</div>
					<div class="showAirdromeImgDiv" id="showAirdromeImgDiv">
						<div class="closeBImg">
							<img src="images/th_xtb_tc.png" id="closeAIDiv"  title="�ص���ͼ" onClick="closeBigImgage('showAirdromeImgDiv','showMapDiv')" width="21" height="21">
							<span id="info" style="position:absolute; right:25px; bottom:5px; color:#000; z-index:50;"></span>
						</div>
						<img id="airdromeImg" src=""/>
					</div>
					<div class="foldImgDiv" id="foldImgDiv">
						<div class="mapCloseBImg">
							<img src="images/qxdj.png"  title="ȡ������" onClick="closeBigImgage('foldImgDiv','showMapDiv')" width="24" height="21">
						</div>
						<img id="foldImg" src=""/>
					</div>
					<div class="blowupDiv" id="blowupDiv" onClick="blowupMapDiv()" title="�Ŵ��ͼ"></div>
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
								<td><img src="images/weather/dbfx_01.jpg" onClick="showBigImgage('bImgDiv','sImg','bImg',this,'','',null,'1')"></td>
								<td><img src="images/weather/ld_01.gif" onClick="showBigImgage('bImgDiv','sImg','bImg',this,'','',null,'2')"></td>
							</tr>
							<tr>
								<td><img src="images/weather/wx_01.jpg" onClick="showBigImgage('bImgDiv','sImg','bImg',this,'','',null,'3')"></td>
								<td><img src="images/weather/rain_01.jpg" onClick="showBigImgage('bImgDiv','sImg','bImg',this,'','',null,'4')"></td>
							</tr>
					  </table>
				  </div>
				  <div id="bImgDiv">
  				  	<div class="imgLeftward" onClick="changeImg('1','bImg')" title="��һ��">&nbsp;</div>
				  	<div class="imgRightward" onClick="changeImg('2','bImg')" title="��һ��">&nbsp;</div>
				  	<div class="closeBImg">
				  		<img src="images/dj.png"  title="����" onClick="showBigImgage('sImg','bImgDiv','foldImg','','','foldImgDiv')" width="24" height="21">
				  		<img src="images/gb.png"  title="�ر�" onClick="closeBigImgage('bImgDiv','sImg')" width="24" height="21">
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
			<input class="buttonArea3" type="button" value="  �� ��" onClick="goBack();"/>
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
		
// 		getPointInfoByCode(dojo.byId('adepCode').value);
// 		getPointInfoByCode(dojo.byId('adesCode').value);
// 		getPointInfoByCode(dojo.byId('altn1Code').value);
		onLoadGisLine();
		
	}
</script>
</html>