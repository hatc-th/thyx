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
<html>
<head>
<meta charset="GBK" />
<title>查看飞行计划</title>
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
<script type='text/javascript' src='<%=path %>/ad/jqueryPlus/autocomplete/js/jquery.autocomplete.js'></script><base>
<!-- 地图操作需要引入的文件 -->
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
		$(".divShowScroll")[2].autohidemode = false;
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
		}else{
			desRow = $('#flyConflictTbl tr').eq(rowIndex);
		}
		desRow.children('td').eq(2).attr("style","text-align:left");
		return desRow;
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
	//地图上加亮显示
	function highLightPoint(ICAO,id,from){
		// serachThenWink(ICAO);
	}
	
	// 返回处理
	function goBack() {
		document.form1.action='<c:url value="/planAuditAction.do?method=searchList" />' ;
		document.form1.submit();
	}
</script>

</head>
<body onclick=syjDTclick() onkeyup=syjDTkeyup() onload="resetSize1();" onresize="resetSize1();">
<div id="globalDiv" class="globalDiv">
	<div class="navText">
		飞行计划查询&nbsp;》飞行计划查看
	</div>
	<form method="post" name="form1" id="form1">
		
		<!-- 保存查询页面的查询条件用 -->
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
		<input type="hidden" id="bakHeight" name="bakHeight" value="0"/>
		
	<div class="fPlanContext">
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
									<input type="text" class="textNoBorder" value="${tFpl.adepName}"  readonly="readonly"/>
									<input type="hidden" class="selectText" name="adep" id="adep" value="${tFpl.adep}"/>
									<input type="hidden" class="selectText" name="adepCode" id="adepCode" value="${tFpl.adepCode}" />
								</td>
								<td class="leftTd">目的机场：</td>
								<td class="rightTd">
									<input type="text" class="textNoBorder" value="${tFpl.adesName}"  readonly="readonly"/>
									<input type="hidden" class="selectText" name="ades" id="ades" value="${tFpl.ades}"/>
									<input type="hidden" class="selectText" name="adesCode" id="adesCode" value="${tFpl.adesCode}"/>
								</td>
								<td class="leftTd">备用机场：</td>
								<td class="rightTd">
									<input type="text" class="textNoBorder" value="${tFpl.altn1Name}" readonly="readonly"/>
	           						<input type="hidden" name="altn1Code" id="altn1Code" value="${tFpl.altn1Code}"/>
								</td>
							</tr>
							<tr>
								<td class="leftTd">起飞时间：</td>
								<td class="rightTd">
									<input type="hidden" class="selectText" name="dates" id="dates" value="<fmt:formatDate value="${tFpl.dates}"  pattern="yyyy-MM-dd"/>"/>
									<input type="hidden" class="selectText" name="setd" id="setd" value="${tFpl.setd}"/>
									<input type="text" class="textNoBorder" value="<fmt:formatDate value="${tFpl.dates}"  pattern="yyyy-MM-dd"/> ${tFpl.setd}" readonly/>
								</td>
								<td class="leftTd">降落时间：</td>
								<td class="rightTd">
									<input type="hidden" class="selectText" name="setal" id="setal" value="<fmt:formatDate value="${tFpl.setal}"  pattern="yyyy-MM-dd"/>"/>
									<input type="hidden" class="selectText" name="seta" id="seta" value="${tFpl.seta}"/>
									<input type="text" class="textNoBorder" value="<fmt:formatDate value="${tFpl.setal}"  pattern="yyyy-MM-dd"/> ${tFpl.seta}" readonly/>
								</td>
								<td class="leftTd">飞行高度：</td>
								<td class="rightTd">
									<input type="text" name="chgt" id="chgt" class="textNoBorder" value="${tFpl.chgt} <c:if test="${not empty tFpl.chgt}">m</c:if>" readonly/>
								</td>
							</tr>
							<tr>
								<td class="leftTd">飞行速度：</td>
								<td class="rightTd">
									<input type="text" class="textNoBorder" value="${tFpl.cspd} <c:if test="${not empty tFpl.cspd}">km/h</c:if>" readonly/>
								</td>
								<td class="leftTd">飞行规则：</td>
								<td class="rightTd">
									<c:forEach var="item" items="${flyRuleList}">
			                			<c:if test="${tFpl.rule eq item.code_id }"><input type="text" class="textNoBorder" value="${item.name}" readonly/></c:if>
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
			           	
						<input type="button" class="LineButton mainLineButton"  name="mainBut" id="mainBut" value="主" onClick="showSelectLineDraw('1');showSelectLineDiv('1');"/><input type="button" class="LineButton prepareLinButton" name="bakBut" id="bakBut" value="备"  onClick="showSelectLineDraw('0');showSelectLineDiv('0');"/>
					</div>
					<div id="lineTableDiv1" class="lineTableDiv divShowScroll" >
						<table id="lineTable1" class="lineTable">
							<tr class="lineTableTitle">
								<td width="26">序号</td>
								<td width="200">编&nbsp;&nbsp;&nbsp;&nbsp;号</td>
								<td width="286">名&nbsp;&nbsp;&nbsp;&nbsp;称</td>
								<td width="115">类&nbsp;&nbsp;&nbsp;&nbsp;型</td>
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
								<td width="286">名&nbsp;&nbsp;&nbsp;&nbsp;称</td>
								<td width="115">类&nbsp;&nbsp;&nbsp;&nbsp;型</td>
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
			
			<!--==========================文本信息-安全提示============================-->
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
			
			<!--==========文本信息-飞行计划其他信息==========-->
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
									<input type="text" class="textNoBorder" value="${tFpl.FPlanName}" readonly>
								</td>
								<td class="leftTd">飞行编号：</td>
								<td class="rightTd">
									<input type="text" class="textNoBorder" value="${tFpl.FPlanCode}" readonly>
								</td>
								<td class="leftTd">计划类别：</td>
								<td class="rightTd">
									<input type="text" class="textNoBorder" value="通航一次性飞行计划" readonly>
								</td>
							</tr>
							<tr>
								<td class="leftTd">飞行类型：</td>
								<td class="rightTd">
									<c:forEach var="item" items="${flyTypeList}">
		                    			<c:if test="${tFpl.types eq item.code_id}"><input type="text" class="textNoBorder" value="${item.name}" readonly></c:if>
		                    		</c:forEach>
								</td>
								<td class="leftTd">飞行时长：</td>
								<td class="rightTd">
									<input type="text" class="textNoBorder" value="${tFpl.eet}" readonly>
								</td>
								<td class="leftTd">航空公司：</td>
								<td class="rightTd">
									<c:forEach var="item" items="${airlineCompanyList}">
										<c:if test="${tFpl.unit eq item.code_id}"><input type="text" class="textNoBorder" value="${item.name}" readonly></c:if> 
			                    	</c:forEach>
								</td>
							</tr>
							<tr>
								<td class="leftTd">飞行员：</td>
								<td class="rightTd">
									<c:forEach var="item" items="${pilotList}">
			                    		<c:if test="${tFpl.commander eq item.code_id}"><input type="text" class="textNoBorder" value="${item.name}" readonly></c:if>
			                    	</c:forEach>
								</td>
								<td class="leftTd">航空器型号：</td>
								<td class="rightTd" colspan="3">
									<c:forEach var="item" items="${airCraftTypeList}">
			                    		<c:if test="${tFpl.acft eq item.code_id}">${item.name}</c:if> 
			                    	</c:forEach>
			                    	<input type="hidden" name="acft" id="acft" value="${tFpl.acft}"/>
							    </td>
								
							</tr>
							<tr>
								<td class="leftTd">航空器编号：</td>
								<td class="rightTd">
									<c:forEach var="item" items="${airCraftSnList}">
			                    		<c:if test="${tFpl.acid eq item.code_id}"><input type="text" class="textNoBorder" value="${item.name}" readonly></c:if> 
			                    	</c:forEach>
								</td>
								<td class="leftTd">机载设备：</td>
								<td class="rightTd">
									<c:forEach var="item" items="${flyEquipmentList}">
			                    		<c:if test="${tFpl.equip eq item.code_id}"><input type="text" class="textNoBorder" value="${item.name}" readonly></c:if>
			                    	</c:forEach>
								</td>
								<td class="leftTd">是否需要提醒：</td>
								<td class="rightTd">
									<c:if test="${tFpl.alarm eq '1'}"><input type="text" class="textNoBorder" value="是" readonly></c:if>
			                    	<c:if test="${tFpl.alarm eq '0'}"><input type="text" class="textNoBorder" value="否" readonly></c:if>
							    </td>
							</tr>
							<tr>
								<td class="leftTd">备注：</td>
								<td class="rightTd" colspan="5">
									<textarea  class="fPlanTextareaNoBorder3"  id="remark" name="remark" readonly >${tFpl.remark}</textarea>
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
	<div class="bottomButtonArea">
		<div class="buttonArea">
			<input class="buttonArea3" type="button" value="  返 回" onClick="goBack();"/>
		</div>
	</div>
	</form>
</div>
</body>
<script type="text/javascript">
    // 初始化页面时，地图加载完成将航线在地图显示
	function createToolbar(map) {
		toolbar = new esri.toolbars.Draw(map);
		
		dojo.connect(toolbar, "onDrawEnd", addToMap);
		
		getPointInfoByCode(dojo.byId('adepCode').value);
		getPointInfoByCode(dojo.byId('adesCode').value);
		getPointInfoByCode(dojo.byId('altn1Code').value);
		onLoadGisLine();
		
	}
</script>
</html>