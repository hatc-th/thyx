<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%@ include file="/common/taglibs.jsp" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%-- ************************************
 * System:    通航运行管理与服务保障系统
 * Function： 飞行计划管理页面
 * Author:    罗凤梅
 * Copyright: 北京华安天诚科技有限公司
 * Create:    VER1.00 2013.09.22
 * Modify:    
************************************ --%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="zh" lang="zh">
<head>
<meta charset="GBK" />
<title>新建飞行计划</title>
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
<script src="js/changePageSize.js" type="text/javascript"></script>
<script src="js/jquery.nicescroll.min.js" type="text/javascript"></script>
<link rel="stylesheet" type="text/css" href="<%=path %>/ad/jqueryPlus/autocomplete/jquery.autocomplete.css"/>
<script type='text/javascript' src='<%=path %>/ad/jqueryPlus/autocomplete/js/jquery.autocomplete.js'></script><base>
<!-- 地图操作需要引入的文件 -->
<%@include file = "/gis/gis.inc"%>
<!-- 三维模拟需要引入的文件 -->
<%@include file = "/earthview/glb.inc"%>

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
			autohidemode:false,
		}
	); 

	//起飞机场名字或者ICAO_CODE autocomplete
	$("#adepName").autocomplete('<%=path%>/adAction.do?op=getADJSONData', {
		minChars: 2,
		max:100,
		width: 142,
		formatItem: function(row){
						return " <strong>" + row[1] + "</strong>";
					},
		formatResult: function(row){
						return row[1].replace(/(<.+?>)/gi, '');
					}
	});
	
	// 设置起飞机场节点
	$("#adepName").result(function(event, data, formatted) {
		//0:objectid  1:ad_name,2:ad_id,3:icao_code,4:ad_arp_pos
		if(document.getElementById("adep").value != data[0] ){
			document.getElementById("adepName").value = data[1];
			document.getElementById("adep").value = data[0];
			document.getElementById("adepCode").value = data[3];
			document.getElementById("adepPos").value = data[4];
			addAdepOrAdesToLine('1');
			reFreshGisLine();
			getDateDiff();
		}
	});
	
	
	//目的机场名字或者ICAO_CODE autocomplete
	$("#adesName").autocomplete('<%=path%>/adAction.do?op=getADJSONData', {
		minChars: 2,
		max:100,
		width: 142,
		formatItem: function(row){
						return " <strong>" + row[1] + "</strong>";
					},
		formatResult: function(row){
						return row[1].replace(/(<.+?>)/gi, '');
					}
	});
	
	// 设置目的机场节点
	$("#adesName").result(function(event, data, formatted) {
		//0:objectid  1:ad_name,2:ad_id,3:icao_code,4:ad_arp_pos
		if(document.getElementById("ades").value != data[0]){
			document.getElementById("adesName").value = data[1];
			document.getElementById("ades").value = data[0];
			document.getElementById("adesCode").value = data[3];
			document.getElementById("adesPos").value = data[4];
			addAdepOrAdesToLine('2');
			reFreshGisLine();
			getDateDiff();
		}
	});
	
	//备降机场名字或者ICAO_CODE autocomplete
	$("#altn1Name").autocomplete('<%=path%>/adAction.do?op=getADJSONData', {
		minChars: 1,
		max:100,
		width: 142,
		formatItem: function(row){
						return " <strong>" + row[1] + "</strong>";
					},
		formatResult: function(row){
						return row[1].replace(/(<.+?>)/gi, '');
					}
	});
	// 设置备降机场位置，用于地图显示
	$("#altn1Name").result(function(event, data, formatted) {
		//0:objectid  1:ad_name,2:ad_id,3:icao_code,4:ad_arp_pos
		if(document.getElementById("altn1").value != data[0]){
			document.getElementById("altn1Name").value = data[1];
			document.getElementById("altn1").value = data[0];
			document.getElementById("altn1Code").value = data[3];
			reFreshGisLine();
		}
	});
			
});

// 弹出选择窗口
//index 用于区分弹出页，params 关键字查询对应的元素名称，
function switchPopWin(index, params) {
	params = (params != null && params != "") ? params : "";
	params = document.getElementById(params).value;
    switch (index) {
        //1 表示查询起飞机场
		case 1:
		//2 表示查询目的机场
		case 2:
		//3 表示查询备降机场
		case 3:
			if(index == 2 && document.getElementById("adep").value == ''){
				 showConfirmDiv(0,'请先设置起飞机场!','提示信息');
				 break;
			}
			showIframeDiv(320,500,'<c:url value="/adAction.do?op=getADInfo&isDep="/>' + index + '&adName=' + params,'iframe','机场查询',60);
		break;
  	}
} 

// 选择机场回调方法，设置返回的各表单元素值
function setADInfo(flag,object) {
	if(flag == '1'){
		if(document.getElementById("adep").value != object.id ){
			document.getElementById("adepName").value = object.name;
			document.getElementById("adep").value = object.id;
			document.getElementById("adepCode").value = object.code;
			document.getElementById("adepPos").value = object.pos;
			addAdepOrAdesToLine('1');
			reFreshGisLine();
			getDateDiff();
		}
		
	}else if(flag == '2'){
		if(document.getElementById("ades").value != object.id){
			document.getElementById("adesName").value = object.name;
			document.getElementById("ades").value = object.id;
			document.getElementById("adesCode").value = object.code;
			document.getElementById("adesPos").value = object.pos;
			addAdepOrAdesToLine('2');
			reFreshGisLine();
			getDateDiff();
		}
		
	}else {
		document.getElementById("altn1Name").value = object.name;
		document.getElementById("altn1").value = object.id;
		document.getElementById("altn1Code").value = object.code;
		reFreshGisLine();
	}
}

// 修改机场时清除已有内容
function clearGraphicInfoByCode() {
	clearDraw();
	getPointInfoByCode(dojo.byId('adepCode').value);
	getPointInfoByCode(dojo.byId('adesCode').value);
	getPointInfoByCode(dojo.byId('altn1Code').value);
	// reFreshGisLine();
}
	
	//显示冲突信息
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
		if( !checkElementFilled('acft','请选择飞行器类型!') ){
			return ;
		}
		var removeRow = $('#flyConflictTbl tr[src="db"]');
		$.each(removeRow,function(){
			$(this).removeAttr('icao');
			$(this).removeAttr('src');
			$(this).remove();
			rowIndex--;
		});
		resetLineIndex();
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
						desRow.attr('src','db');//数据来源【地图，数据库】
						if(rowIndex > 5){
							desRow.appendTo(tbl);
						}
						desRow.dblclick(function(){
						ICAO = entry.src;
						switch(ICAO){
							case "depICAO":
							highLightPoint(depICAO,entry.id,entry.from);//根据机场名称或者是代码定位
							break;
							
							case "desICAO":
							highLightPoint(desICAO,entry.id,entry.from);
							break;
						}	
						});
	
						desRow.children('td').eq(0).html(rowIndex);
						desRow.children('td').eq(1).html(entry.type);
						desRow.children('td').eq(2).html(entry.description);
						desRow.children('td').eq(3).html(entry.startTime + ' 到 ' + entry.endTime);
						rowIndex++;
				});
			},
		});
		
		if(rowIndex >5 ){
			$(".divShowScroll")[2].autohidemode = false;
		}
	}
	
	//生成冲突分析的table行
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
			desRow.children('td').eq(2).attr("style","text-align:left");
		}else{
			desRow = $('#flyConflictTbl tr').eq(rowIndex);
			desRow.children('td').eq(2).attr("style","text-align:left");
		}
		return desRow;
	}
	
	/*
		移除冲突数据
		@param conflictType 冲突类型   rs_as:空域冲突     rs_ob：飞行高度冲突
		@param lineType 航线类型
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
	
	/*
	 * 重置样式和行数不足4行时补空白行
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
		
		// 如果表格中的行数不足4行时补空白行
		var len = $('#flyConflictTbl tr').length;  
		for(var m=len-1 ;m<5;m++){  // 因为表头占一行，所以要-1
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
	
	/*
		添加空域冲突数据
		@param conflictType 冲突类型
		@param conflictReason 航线类型
		@param icao  编码，用于高亮地图中某点
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
				desRow.attr('src','rs_as');//标示冲突类型
				conflictValue="空域冲突";
				if(conflictReason =='1'){
					reason = "主航线穿过禁区"+icao;
				}else{
					reason = "备航线穿过禁区"+icao;
				}
				/**
				desRow.dblclick(function(){
					serachThenWink(icao);
				});**/
			}else {
				desRow.attr('src','rs_ob');//标示冲突类型
				conflictValue="安全高度告警";
				reason = "航线高度低于安全超障高度，建议调整航线高度到";

				reason = reason + [parseInt($('#chgt').val(),10)+parseInt(600,10)-parseInt($('#bakHeight').val(),10)]+"米";
				
				desRow.dblclick(function(){
					showConfirmDiv(1,'是否更新当前的飞行高度到建议高度？','提示信息',function(choose){
						  	if(choose=="yes"){
								$('#chgt').val(parseInt($('#chgt').val(),10)+parseInt(600,10)-parseInt($('#bakHeight').val(),10));
								reDrawLineSection($('#viewTypeFlag').val());
							}else{
								return;
							}
						});
					});
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
		if(rowIndex >5 ){
			$(".divShowScroll")[2].autohidemode = false;
		}
	}
	
	//地图上加亮显示
	function highLightPoint(ICAO,id,from){
		// serachThenWink(ICAO);
	}
	
	function changerTime1(thes1,thes2){
		alert(13);
		thes2.value= thes1+'1';
	}
	function changerTime2(thes1,thes2){
		alert(12);
		thes2.value= thes1+'2';
	}
		
</script>

</head>
<body onclick=syjDTclick() onkeyup=syjDTkeyup() onload="resetSize1();" onresize="resetSize1();" onkeydown="return keyDown(event);" onkeyup="return keyUp(event);">
<div id="globalDiv" class="globalDiv">
	<div class="navText">
		飞行计划管理&nbsp;》飞行计划新建
	</div>
	<form method="post" name="form1" id="form1" >
	
	
		<input id="planid" name="planid" type="hidden" value="<c:if test="${ empty copy }">${tFpl.planid}</c:if>" />
		<input id="copy" name="copy" type="hidden" value="${copy}" />
		<input id="cyId" name="cyId" type="hidden" value="<c:if test="${ empty copy }">${tFplCycle.cyId}</c:if>" />	
		
		<!-- ahm 增加 保存查询页面的查询条件用 -->
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
		<input type="hidden" id="bakHeight" name="bakHeight" value="0"/>
		
		<input type="hidden" id="pageBool" name="pageBool" value="${pageBool}"/>
	<div class="fPlanContext" id="fPlanContext">
		<!--==========================================================文本信息=========================================================================-->
		<div id="fPlanContextText" class="fPlanContextText">
			<!--==========文本信息-飞行计划==========-->
			<div class="fPlanLeftTop">
				<!--==========设置上面圆角start===========-->
				<div class="titleLeftRound">
					<div class="roundLeft leftRound">
						<div class="roundLeftLeft"><img src="images2/left_Context_yj1.png" /></div>
						<div class="topRoundLeftRight"><div class="roundTitle">飞行计划</div><div class="roundTitleRight"></div></div>
				 	</div>
					<div class="roundRight"><img src="images2/left_Context_yj2.png" /></div>
				</div>
				<!--==========设置上面圆角end===========-->
				
				<div class="fPlanLeftTopContext">
					<div class="inputTableDiv">
						<table class="inputTable">
							<tr>
								<td class="leftTd">起飞机场：</td>
								<td class="rightTd">
									<input type="hidden" class="selectText" name="adep" id="adep" value="${tFpl.adep}"/>
									<input type="text" class="selectText" name="adepName" id="adepName" value="${tFpl.adepName}" onChange="cleanValueById('adep');"/><input type="button" class="selectButton" value="" onClick="switchPopWin(1, 'adepName');"/>
									<input type="hidden" class="selectText" name="adepCode" id="adepCode" value="${tFpl.adepCode}" onpropertychange="clearGraphicInfoByCode();" />
	           						<input type="hidden" class="selectText" name="adepPos" id="adepPos" value="${tFpl.adepPos}" />
								</td>
								<td class="leftTd">目的机场：</td>
								<td class="rightTd">
									<input type="hidden" class="selectText" name="ades" id="ades" value="${tFpl.ades}">
									<input type="text" class="selectText" name="adesName" id="adesName" value="${tFpl.adesName}" onChange="cleanValueById('ades');" onFocus="checkElementFilled('adep','请先选择设置起飞机场!');"/><input type="button" class="selectButton" value="" onClick="switchPopWin(2, 'adesName');"/>
									<input type="hidden" class="selectText" name="adesCode" id="adesCode" value="${tFpl.adesCode}" onpropertychange="clearGraphicInfoByCode();" />
	           						<input type="hidden" class="selectText" name="adesPos" id="adesPos" value="${tFpl.adesPos}" />
								</td>
								<td class="leftTd">备用机场：</td>
								<td class="rightTd">
									<input type="hidden" class="selectText" name="altn1" id="altn1" value="${tFpl.altn1}">
									<input type="text" class="selectText" name="altn1Name" id="altn1Name" value="${tFpl.altn1Name}" onChange="cleanValueById('altn1');"/><input type="button" class="selectButton" value="" onClick="switchPopWin(3, 'altn1Name');"/>
	           						<input type="hidden" name="altn1Code" id="altn1Code" value="${tFpl.altn1Code}" onpropertychange="clearGraphicInfoByCode();" />
								</td>
							</tr>
							<tr>
								<td class="leftTd">起飞时间：</td>
								<td class="rightTd">
									<input type="text" class="dateSelectText" name="dates" id="dates" value='<fmt:formatDate value="${tFpl.dates}"  pattern="yyyy-MM-dd"/>' onFocus="syjDate(this)" onpropertychange="getDateDiff()"/>：<input type="text" class="timeSelectText" name="setd" id="setd" value="${tFpl.setd}" onFocus="syjTime(this)" onpropertychange="getDateDiff()"/>
								</td>
								<td class="leftTd">飞行速度：</td>
								<td class="rightTd">
									<input type="text" class="selectTextTo" name="cspd" id="cspd" value="${tFpl.cspd}" onpropertychange="getDateDiff()" ><span>km/h</span>
								</td>
								<td class="leftTd">飞行高度：</td>
								<td class="rightTd">
									<input type="text" class="selectTextTo" name="chgt" id="chgt" value="${tFpl.chgt}" onChange="changeFlyHight();"> <span class="meterSpan">m</span>
								</td>
							</tr>
							<tr>
								<td class="leftTd">降落时间：</td>
								<td class="rightTd">
									<input type="text" class="dateSelectText" name="setal" id="setal" value='<fmt:formatDate value="${tFpl.setal}"  pattern="yyyy-MM-dd"/>' readonly />：<input type="text" class="timeSelectText" name="seta" id="seta" value="${tFpl.seta}" readonly />
								</td>
								
								<td class="leftTd">飞行规则：</td>
								<td class="rightTd">
									<c:forEach var="item" items="${flyRuleList}">
			                			<input class="radioClass"  type="radio"  value="${item.code_id}" id="rule" name="rule" <c:if test="${tFpl.rule eq item.code_id }">checked="checked"</c:if> />${item.name}&nbsp;&nbsp;&nbsp;&nbsp;
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
						<span id="isMainLine">主航线</span>
						<input type="hidden" class="selectText" name="lineFlag" id="lineFlag" value="1"><%-- 标记哪条航线表示的是主航线：1 则 lineTableDiv1表示主航线，0 则 lineTableDiv2 表示主航线 --%>
						<input type="hidden" class="selectText" name="viewTypeFlag" id="viewTypeFlag" value="1"><%-- 标记当前显示的是哪条航线：1 则当前显示的是主航线，0 则当前显示的是备航线 --%>
						<input type="hidden" name="linePointCount1" id="linePointCount1" value="${fn:length(trsMainTRsPList)}"/><%-- 记录主航线有多少个节点 --%>
	           			<input type="hidden" name="linePointCount2" id="linePointCount2" value="${fn:length(trsBakTRsPList)}"/> <%-- 记录备航线有多少个节点 --%>
	           			<input type="hidden" name="rsIdMain" id="rsIdMain" value="<c:if test="${ empty copy }">${trsMain.rsId}</c:if>"/>
			           	<input type="hidden" name="rsIdGeneralMain" id="rsIdGeneralMain" value=""/>
			           	<input type="hidden" name="rsIdBak" id="rsIdBak" value="<c:if test="${ empty copy }">${trsBak.rsId}</c:if>"/>
			           	<input type="hidden" name="rsIdGeneralBak" id="rsIdGeneralBak" value=""/>
			           	
						<input type="button" class="LineButton mainLineButton"  name="mainBut" id="mainBut" value="主" onClick="showSelectLineDraw('1');showSelectLineDiv('1');"/><input type="button" class="LineButton prepareLinButton" name="bakBut" id="bakBut" value="备"  onClick="showSelectLineDraw('0');showSelectLineDiv('0');"/><input type="button" class="changeLineButton" value=" "   onClick="changeSelectLineType();">
						<input type="button" class="setupLineButton setupLineButton1" value="&nbsp;&nbsp;&nbsp;建议航线" onClick="queryGeneralSkyWays('1');"/><input type="button" class="setupLineButton setupLineButton2" value="&nbsp;&nbsp;&nbsp;常用航线" onClick="queryGeneralSkyWays('0');"/><input type="button" class="setupLineButton setupLineButton3" value="&nbsp;&nbsp;&nbsp;设为常用" onClick="ajaxSaveGeneralSkyWay();">
						<input type="button" class="setupLineButton setupLineButton4" value="&nbsp;&nbsp;&nbsp;添加备航线" onClick="addBakSkyWay();"><input type="button" class="setupLineButton setupLineButton5" value="&nbsp;&nbsp;&nbsp;删除航线" onClick="deleteSkyWay();" >
					</div>
					<div id="lineTableDiv1" class="lineTableDiv divShowScroll" >
						<table id="lineTable1" class="lineTable">
							<tr class="lineTableTitle">
								<td width="26">序号</td>
								<td width="200">编&nbsp;&nbsp;&nbsp;&nbsp;号</td>
								<td width="260">名&nbsp;&nbsp;&nbsp;&nbsp;称</td>
								<td width="115">类&nbsp;&nbsp;&nbsp;&nbsp;型</td>
								<td width="26">操作</td>
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
										<c:if test="${trsMainTRs.rsPType eq '9'}">机场</c:if><c:if test="${trsMainTRs.rsPType eq '18'}">导航台</c:if>
										<c:if test="${trsMainTRs.rsPType eq '20'}">地标点</c:if>
					    		    	<input type="hidden" name="rsPTypeMain" id="rsPTypeMain${trsMainTRs.rsPNum}" value="${trsMainTRs.rsPType}"/>
					    		    </td>
									<td>
										<c:if test="${status.index < fn:length(trsMainTRsPList)-1}">
											<div class="setupLineDivMeun" id="setupLineDivMeun${trsMainTRs.rsPNum}"></div>
											<div class="setupLineDiv" onClick="showFLinMeun('setupLineDivMeun${trsMainTRs.rsPNum}',${trsMainTRs.rsPNum})"><img src="images/line_setup.png"/></div>
										</c:if>
									</td>
								</tr>
			    		    </c:forEach>
			    		    <!--  补齐空行  -->
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
										<td></td>	
				                    </tr>
					    		</c:forEach>
				    		</c:if>
						</table>
					</div>
					<div id="lineTableDiv2" class="lineTableDiv divShowScroll" style="display: none;">
						<table id="lineTable2" class="lineTable">
							<tr class="lineTableTitle">
								<td width="26">序号</td>
								<td width="200">编&nbsp;&nbsp;&nbsp;&nbsp;号</td>
								<td width="260">名&nbsp;&nbsp;&nbsp;&nbsp;称</td>
								<td width="115">类&nbsp;&nbsp;&nbsp;&nbsp;型</td>
								<td width="26">操作</td>
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
										<c:if test="${trsBakTRs.rsPType eq '9'}">机场</c:if><c:if test="${trsBakTRs.rsPType eq '18'}">导航台</c:if>
										<c:if test="${trsBakTRs.rsPType eq '20'}">地标点</c:if>
					    		    	<input type="hidden" name="rsPTypeBak" id="rsPTypeBak${trsBakTRs.rsPNum}" value="${trsBakTRs.rsPType}"/>
					    		    </td>
									<td>
										<c:if test="${status1.index < fn:length(trsBakTRsPList)-1}">
											<div class="setupLineDivMeun" id="setupLineDivMeunBak${trsBakTRs.rsPNum}"></div>
											<div class="setupLineDiv" onClick="showFLinMeun('setupLineDivMeunBak${trsBakTRs.rsPNum}',${trsBakTRs.rsPNum})"><img src="images/line_setup.png"/></div>
										</c:if>
									</td>
								</tr>
				    		    
			    		    </c:forEach>
			    		    
			    		    <!--  补齐空行  -->
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
										<td></td>	
				                    </tr>
					    		</c:forEach>
				    		</c:if>
						</table>
					</div>
				</div>
				<!--==========设置下面圆角start===========-->
				<div class="round">
					<div class="roundLeft leftRound">
						<div class="roundLeftLeft"><img src="images2/Context_yj4.png" /></div>
						<div class="roundLeftRight"></div>
				 	</div>
					<div class="roundRight"><img src="images2/Context_yj3.png" /></div>
				</div>
				<!--==========设置下面圆角end===========-->
			</div>
			<!--==========文本信息-飞行计划其他信息==========-->
			<div class="fPlanLeftCenter">
				<!--==========设置上面圆角start===========-->
				<div class="titleLeftRound">
					<div class="roundLeft leftRound">
						<div class="roundLeftLeft"><img src="images2/left_Context_yj1.png" /></div>
						<div class="topRoundLeftRight"><div class="roundTitle">安全提示</div><div class="roundTitleRight"></div>
					</div>
				</div>
				<div class="roundRight"><img src="images2/left_Context_yj2.png" /></div>
				</div>
				<!--==========设置上面圆角end===========-->
				<div class="fPlanLeftBottomContext">
					<div class="securityButtonDiv">
						<input type="button" class="setupLineButton setupLineButton6" value="&nbsp;&nbsp;&nbsp;查询冲突" onclick="showConflict()" />
						<!-- input type="button" class="setupLineButton" value="清空冲突信息" onclick="removeAreaSpaceConflictData()" /-->
					</div>
					<div class="securityTableDiv divShowScroll" id="conflict_div">
						<table class="lineTable" id="flyConflictTbl">
							<tr class="lineTableTitle">
								<td width="26">序号</td>
								<td width="100">冲突类型</td>
								<td width="180">冲突原因</td>
								<td width="180">冲突时间</td>
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
				<!--==========设置下面圆角start===========-->
				<div class="round">
					<div class="roundLeft leftRound">
						<div class="roundLeftLeft"><img src="images2/Context_yj4.png" /></div>
						<div class="roundLeftRight"></div>
				  	</div>
					<div class="roundRight"><img src="images2/Context_yj3.png" /></div>
				</div>
				<!--==========设置下面圆角end===========-->
			</div>
			<!--==========================文本信息-安全提示============================-->
			<div class="fPlanLeftBottom">
				<!--==========设置上面圆角start===========-->
				<div class="titleLeftRound">
					<div class="roundLeft leftRound">
						<div class="roundLeftLeft"><img src="images2/left_Context_yj1.png" /></div>
						<div class="topRoundLeftRight"><div class="roundTitle">其他信息</div><div class="roundTitleRight"></div></div>
				  	</div>
					<div class="roundRight"><img src="images2/left_Context_yj2.png" /></div>
				</div>
				<!--==========设置上面圆角end===========-->
				
				<div class="fPlanLeftCenterContext">
					<div class="inputTableDiv">
						<table class="inputTableTo">
							<tr>
								<td class="leftTd">计划名称：</td>
								<td class="rightTd">
									<input type="text" class="textNoBorder" name="FPlanName" id="FPlanName" value="${tFpl.FPlanName}" onFocus="showBorder(this)" onBlur="hideBorder(this)"/>
								</td>
								<td class="leftTd">飞行编号：</td>
								<td class="rightTd">
									<input type="text" class="textNoBorder" name="FPlanCode" id="FPlanCode" value="<c:if test="${ empty copy }">${tFpl.FPlanCode}</c:if>" onBlur="hideBorder(this)" readonly>
								</td>
								<td class="leftTd">计划类别：</td>
								<td class="rightTd">
									<input type="hidden" class="selectText" name="pclass" id="pclass" value="${tFpl.pclass}">
									<input type="text" class="textNoBorder" name="" id="" value="通航一次性飞行计划" onBlur="hideBorder(this)" readonly>
								</td>
							</tr>
							<tr>
								<td class="leftTd">飞行类型：</td>
								<td class="rightTd">
									<select class="textNoBorder" name="types" id="types" onFocus="showBorder(this)" onBlur="hideBorder(this)">
										<c:forEach var="item" items="${flyTypeList}">
			                    			<option value="${item.code_id}" <c:if test="${tFpl.types eq item.code_id}">selected="selected"</c:if> >${item.name}</option>
			                    		</c:forEach>
									</select>
								</td>
								<td class="leftTd">飞行时长：</td>
								<td class="rightTd">
									<input type="text" class="textNoBorder" name="eet" id="eet" value="${tFpl.eet}" onBlur="hideBorder(this)" readonly>
								</td>
								<td class="leftTd">航空公司：</td>
								<td class="rightTd">
									<select class="textNoBorder" name="unit" id="unit" onFocus="showBorder(this)" onBlur="hideBorder(this)">
										<c:forEach var="item" items="${airlineCompanyList}">
				                    		<option value="${item.code_id}" <c:if test="${tFpl.unit eq item.code_id}">selected="selected"</c:if> >${item.name}</option>
				                    	</c:forEach>
									</select>
								</td>
							</tr>
							<tr>
								<td class="leftTd">飞行员：</td>
								<td class="rightTd">
									<select class="textNoBorder" name="commander" id="commander" onFocus="showBorder(this)" onBlur="hideBorder(this)">
										<c:forEach var="item" items="${pilotList}">
				                    		<option value="${item.code_id}" <c:if test="${tFpl.commander eq item.code_id}">selected="selected"</c:if> >${item.name}</option>
				                    	</c:forEach>
									</select>
								</td>
								<td class="leftTd">航空器型号：</td>
								<td class="rightTd">
									<select class="textNoBorder" name="acft" id="acft" onFocus="showBorder(this)" onBlur="hideBorder(this)">
										<c:forEach var="item" items="${airCraftTypeList}">
				                    		<option title="${item.name}" value="${item.code_id}" <c:if test="${tFpl.acft eq item.code_id}">selected="selected"</c:if> >${item.name}</option>
				                    	</c:forEach>
									</select>
							    </td>
								<td class="leftTd">航空器编号：</td>
								<td class="rightTd">
									<select class="textNoBorder" name="acid" id="acid" onFocus="showBorder(this)" onBlur="hideBorder(this)">
										<c:forEach var="item" items="${airCraftSnList}">
				                    		<option value="${item.code_id}" <c:if test="${tFpl.acid eq item.code_id}">selected="selected"</c:if> >${item.name}</option>
				                    	</c:forEach>
									</select>
								</td>
							</tr>
							<tr>
								<td class="leftTd">机载设备：</td>
								<td class="rightTd">
									<select class="textNoBorder" name="equip" id="equip" onFocus="showBorder(this)" onBlur="hideBorder(this)">
										<c:forEach var="item" items="${flyEquipmentList}">
				                    		<option value="${item.code_id}" <c:if test="${tFpl.equip eq item.code_id}">selected="selected"</c:if> >${item.name}</option>
				                    	</c:forEach>
									</select>
								</td>
								<td class="leftTd">是否需要提醒：</td>
								<td class="rightTd">
									<select class="textNoBorder" name="alarm" id="alarm" onFocus="showBorder(this)" onBlur="hideBorder(this)">
										<option value="1" <c:if test="${tFpl.alarm eq '1'}">selected="selected"</c:if> >是</option>
			                    		<option value="0" <c:if test="${tFpl.alarm eq '0'}">selected="selected"</c:if> >否</option>
									</select>	
							    </td>
								<td class="leftTd">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
								<td class="rightTd">
									<input type="hidden" class="selectText" name="createImitate" id="createImitate" value="1">
								</td>
							</tr>
							<tr>
								<td class="leftTd">备注：</td>
								<td class="rightTd" colspan="5">
									<textarea  class="fPlanTextareaNoBorder"  id="remark" name="remark" onFocus="showBorder(this,'1')" onBlur="hideBorder(this,'1')">${tFpl.remark}</textarea>
								</td>
							</tr>
						</table>
					</div>
				</div>				
				<!--==========设置下面圆角start===========-->
				<div class="round">
					<div class="roundLeft leftRound">
						<div class="roundLeftLeft"><img src="images2/Context_yj4.png" /></div>
						<div class="roundLeftRight"></div>
				  	</div>
					<div class="roundRight"><img src="images2/Context_yj3.png" /></div>
				</div>
				<!--==========设置下面圆角end===========-->
			</div>		
		</div>
		
		<!--==========================================================图片信息=========================================================================-->
		<div id="fPlanContextImg" class="fPlanContextImg">
			<!--==========图片信息-地图==========-->
			<div id="fPlanRightTop" class="fPlanRightTop">
				<!--==========设置上面圆角start===========-->
				<div id="mapRound1" class="mapRound">
					<div id="mapLeftRound1"  class="mapLeftRound">
						<div class="mapRoundLeftLeft"><img src="images2/map_context_yj1.png" /></div>
						<div id="mapRoundLeftRight1" class="mapRoundLeftRight"></div>
				    </div>
					<div class="mapRoundRight"><img src="images2/map_context_yj2.png" /></div>
				</div>
				<!--==========设置上面圆角end===========-->				
				
				<div id="fPlanRightTopContext" class="fPlanRightTopContext">
					<div id="controlPane" style="cursor:pointer;text-align:center;padding-top:5px;position:absolute;left:1512px;top:52px;z-index: 10000;border:1px solid green;width:120px;height:15px;overflow-y:auto;overflow-x:hidden;background:grey;" onclick="shPane()">图层控制</div>
					<div class="showMapDiv" id="showMapDiv">
						<input type="hidden" id="userroleuicode" value="${sessionUserUICode}">
						<div id="map" style="width:100%;height:100%;background:#dff8c3;"></div>
						<!--==========剖面图div==========-->
						<div id="contentPane" style="cursor:pointer;text-align:left;position:absolute;left:1512px;top:72px;border:1px solid green;width:120px;height:200px;overflow-y:auto;overflow-x:hidden;background:grey;display:none;">
					    	
					    </div>
					</div>
					<div class="showAirdromeImgDiv" id="showAirdromeImgDiv">
						<div class="closeBImg">
							<img src="images/th_xtb_tc.png" id="closeAIDiv"  title="回到地图" onClick="closeBigImgage('showAirdromeImgDiv','showMapDiv')" width="21" height="21">
							<span id="info" style="position:absolute; right:25px; bottom:5px; color:#000; z-index:50;"></span>
						</div>
						<img id="airdromeImg" src=""/>
					</div>
					<div class="foldImgDiv" id="foldImgDiv">
						<div class="mapCloseBImg">
							<img src="images/qxdj.png"  title="取消叠加" onClick="closeBigImgage('foldImgDiv','showMapDiv')" width="24" height="21">
						</div>
						<img id="foldImg" src=""/>
					</div>
					<div class="blowupDiv" id="blowupDiv" onClick="blowupMapDiv()" title="放大地图"></div>
				</div>
				
				<!--==========设置下面圆角start===========-->
				<div id="mapRound2" class="mapRound">
					<div id="mapLeftRound2"  class="mapLeftRound mapLeftRoundTo">
						<div class="mapRoundLeftLeft"><img src="images2/map_context_yj4.png" /></div>
						<div id="mapRoundLeftRight2" class="mapRoundLeftRight bMapRoundLeftRight"></div>
				    </div>
					<div class="mapRoundRight mapRoundRightTo"><img src="images2/map_context_yj3.png" /></div>
				</div>
				<!--==========设置下面圆角end===========-->
			
			</div>
			<!--==========图片信息-气象图==========-->
			<div id="fPlanRightBottom" class="fPlanRightBottom">
				<!--==========设置上面圆角start===========-->
				<div id="mapRound3" class="mapRound">
					<div id="mapLeftRound3" class="mapLeftRound">
						<div class="mapRoundLeftLeft"><img src="images2/map_context_yj1.png" /></div>
						<div id="mapRoundLeftRight3" class="mapRoundLeftRight"></div>
					</div>
					<div class="mapRoundRight"><img src="images2/map_context_yj2.png" /></div>
				</div>
				<!--==========设置上面圆角end===========-->
				
				<div id="fPlanRightBottomContext" class="fPlanRightBottomContext">
					
					<div id="chartNode" data-dojo-type="dojox.charting.widget.Chart2D"  style="width:840px;height:150px;margin-left:5px;"></div>
				</div>	
				
				<!--==========设置下面圆角start===========-->
				<div  id="mapRound4" class="mapRound">
					<div id="mapLeftRound4" class="mapLeftRound mapLeftRoundTo">
						<div class="mapRoundLeftLeft"><img src="images2/map_context_yj4.png" /></div>
						<div id="mapRoundLeftRight4" class="mapRoundLeftRight bMapRoundLeftRight"></div>
					</div>
					<div class="mapRoundRight mapRoundRightTo"><img src="images2/map_context_yj3.png" /></div>
				</div>
				<!--==========设置下面圆角end===========-->
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
  				  	<div class="imgLeftward" onClick="changeImg('1','bImg')" title="上一张">&nbsp;</div>
				  	<div class="imgRightward" onClick="changeImg('2','bImg')" title="下一张">&nbsp;</div>
				  	<div class="closeBImg">
				  		<img src="images/dj.png"  title="叠加" onClick="showBigImgage('sImg','bImgDiv','foldImg','','','foldImgDiv')" width="24" height="21">
				  		<img src="images/gb.png"  title="关闭" onClick="closeBigImgage('bImgDiv','sImg')" width="24" height="21">
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
	<!--==========================================================浮动菜单，版权信息=========================================================================-->
	<div class="bottomButtonArea"  >
		<div class="buttonArea">
			<input class="buttonArea1" id="saveBut" type="button" value="  保 存" onClick="ajaxSaveFlyPlan('11');"/>&nbsp;&nbsp;
			<input class="buttonArea2" id="commitBut"  type="button" value="  提 交" onClick="ajaxSaveFlyPlan('12');" />&nbsp;&nbsp;
			<input class="buttonArea3" type="button" value="  返 回" onClick="goBack();"/>
			
			<input class="buttonArea3" type="button" value="  仿真" onClick="runGlobal();"/>
			<input class="buttonArea3" type="button" value="  高程" onclick="drawElevation();" />
		</div>
	</div>
	</form>
	<div id="jQueryCommonDiv"></div>
</div>

<div id='map3dControl' class="bottomButtonArea" style="height: 20% ;width:100% ;float: left ;display:none;text-align: left;" >
		
		<span style="color: #FFFFFF" >速度</span><input type="input" style="width:60px;" value="0" id="vel"  readonly /><span style="color: #FFFFFF" >km/h</span>
		<input type="button" value="加速" onclick="changeSpeed(100);" />
		<input type="button" value="减速" onclick="changeSpeed(-100);" />
		<input type="button" value="舱内视角" onclick="global.addPanel();global.cameraOnboard();" />
		<input type="button" value="舱外视角" onclick="global.removePanel();global.cameraBack();" />
		<input type="button" value="右侧视角" onclick="global.removePanel();global.cameraRight();" />
		<input type="button" value="左侧视角" onclick="global.removePanel();global.cameraLeft();" />
		<br/>
		
		<span style="color: #FFFFFF" >航程</span><input type="input" style="width:50px;" value="0" id="distance"  readonly />
		<span style="color: #FFFFFF" >高度</span><input type="input" style="width:50px;" value="0" id="height"  readonly />
		
		<span style="color: #FFFFFF" >目标点：</span><input type="input" style="width:50px;" value="0" id="target"  readonly />
		<span style="color: #FFFFFF" >纬度</span><input type="input" style="width:50px;" value="0" id="targetLa"  readonly />
		<span style="color: #FFFFFF" >经度</span><input type="input" style="width:50px;" value="0" id="targetLo"  readonly />
		
		<span style="color: #FFFFFF" >距离</span><input type="input" style="width:50px;" value="0" id="dis"  readonly />
		<span style="color: #FFFFFF" >目标朝向</span><input type="input" style="width:50px;" value="0" id="targetR"  readonly />
		<span style="color: #FFFFFF" >当前朝向</span><input type="input" style="width:50px;" value="0" id="heading"  readonly /><br/>
		<input type="button" value="开始" onclick="go()" /> 
		<input type="button" value="计算航线" onclick="prepareRoute();" />
		<input type="button" value="转到起点" onclick="moveToStart();" />
		<input type="button" value="转到终点" onclick="moveToEnd();" />
		<input type="button" value="启动" onclick="startPlane()" /> 
		<input type="button" value="停止" onclick="stopPlane()" /><br/>
		测试功能
		<input type="button" value="空域" onclick="global.drawZone();" />
		<input type="button" value="日照" onclick="global.showSun();" />
		<input type="button" value="参考航线" onclick="global.drawRoute();" />
		<input type="button" value="改变尺寸" onclick="$('embed').css('height',$('embed').height()!=200?200:'100%');$('embed').parent().children();" />
		<br/>
		<textarea type="input" rows="3" style="width: 45%;" id="inforBox0" ></textarea>
		<textarea type="input" rows="3" style="width: 45%;" id="inforBox1" ></textarea>
	</div>
</body>
<script type="text/javascript">
    // 初始化页面时，地图加载完成将航线在地图显示
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