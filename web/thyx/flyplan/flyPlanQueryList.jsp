<%@ page language="java" pageEncoding="gbk"%>
<%@ include file="/common/taglibs.jsp" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%-- ************************************
 * System:   	通航运行管理与服务保障系统
 * Function： 	试验实施方案添加
 * Author:    	罗凤梅
 * Copyright: 	北京华安天诚科技有限公司
 * Create:    	VER1.00 2013.10.14
 * Modify:    
************************************ --%>
<!DOCTYPE html
      PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
      "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="zh" lang="zh">
<head>
	
	<title>飞行计划查询</title>
    <meta http-equiv="content-type" content="text/html; charset=gbk" />
    <meta http-equiv="content-language" content="zh" />
    <meta http-equiv="content-script-type" content="text/javascript" />
    <meta name="robots" content="none" />
    <meta name="author" content="luofengmei" />
    <meta name="copyright" content="2013, Beijing HATC Co., LTD" />
    <meta name="description" content="thyx" />
   	<link href="css/skin2/thyx/fPlan.css" rel="stylesheet" type="text/css"/>
	<link href="css/skin2/thyx/fPlanContext.css" rel="stylesheet" type="text/css"/>
	<link href="css/skin2/common/showImg.css" rel="stylesheet" type="text/css"/>
	<link href="css/skin2/common/divWindow.css" rel="stylesheet" type="text/css"/>
	<link rel="stylesheet" type="text/css" href="<%=path %>/ad/jqueryPlus/autocomplete/jquery.autocomplete.css"/>
	<script src="js/divWindow.js" type="text/javascript"></script>
	<script src="js/calendar/syjDT.js" type="text/javascript"></script>
	<script src="js/validata/validata.js" type="text/javascript"></script>
	<script src="js/jquery-1.9.1.js" type="text/javascript"></script>
	<script type='text/javascript' src='<%=path %>/ad/jqueryPlus/autocomplete/js/jquery.autocomplete.js'></script>
	<script src="js/changePageSize.js" type="text/javascript"></script>
	<script type="text/javascript">
		$(document).ready(function(){
			//起飞机场名字或者ICAO_CODE autocomplete
			$("#flyPlanAdepName").autocomplete('<%=path%>/adAction.do?op=getADJSONData', {
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
			
			//目的机场名字或者ICAO_CODE autocomplete
			$("#flyPlanAdesName").autocomplete('<%=path%>/adAction.do?op=getADJSONData', {
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
					showIframeDiv(320,500,'<c:url value="/adAction.do?op=getADInfo&isDep="/>' + index + '&adName=' + params,'iframe','机场查询',60);
				break;
		  	}
		} 
		// 机场选择回调方法，设置返回值
		function setADInfo(flag,object) {
			if(flag == '1'){
				document.getElementById("flyPlanAdepName").value=object.name;
				document.getElementById("flyPlanAdep").value=object.id;
			}else {
				document.getElementById("flyPlanAdesName").value=object.name;
				document.getElementById("flyPlanAdes").value=object.id;
			}
		}
	
	//查询
		function queryFlyPlanList() {
			startDate = document.getElementById("startDate");
			if(startDate.value != "") {
				if(!isShortDate(startDate, "-", "开始日期", "2")) {
					return false;
				}
			}
			endDate = document.getElementById("endDate");
			if(endDate.value != "") {
				if(!isShortDate(endDate, "-", "结束日期", "2")) {
					return false;
				}
			}
        	document.form1.action = '<c:url value="/flyPlanAction.do?method=searchFlyPlanList&searchType=query"/>';
        	document.form1.submit();
		}
		
	  // 查看飞行计划
		function  viewFlyPlanInfo(planid){
			// showIframeDiv(1200,835,'flyPlanAction.do?method=initFlyPlanBaseInfo&operFlag=view&planid='+planid,'iframe','查看飞行计划',60);
			document.form1.action = 'flyPlanAction.do?method=initFlyPlanBaseInfo&searchType=query&operFlag=view&planid='+planid;
			document.form1.submit();
		}
    
		//名称被清除时将ID也清空
		function cleanIdValue(names) {
			document.getElementById(names).value = "";
	  	}
  	
    </script>
	
	</head>
	<body onclick=syjDTclick() onkeyup=syjDTkeyup() onload="resetSize();" onresize="resetSize();">
	<div class="globalDiv">
		<div class="navText">
			飞行计划激活&nbsp;》飞行计划激活列表
		</div>
		<form method="post" name="form1" >
			<div class="fplanListCheckDiv">
				<table class="inputTable fPlanListTable">
					<tr>
						<td  class="leftTd">计划编号:</td>
						<td class="rightTd fPlanListTableTd">
                    		<input id="flyPlanCode" name="flyPlanCode" type="text" class="selectText" value="${flyPlanCode}"/>
						</td>
						<td  class="leftTd">计划名称:</td>
						<td class="rightTd">
							<input id="flyPlanName" name="flyPlanName" type="text" class="selectText" value="${flyPlanName}"/>
						</td>
						<td  class="leftTd">起飞机场:</td>
						<td class="rightTd">
							<input type="text" name="flyPlanAdepName" id="flyPlanAdepName" class="selectText" onchange="cleanIdValue('flyPlanAdep');"  value="${flyPlanAdepName}"/><input type="button" class="selectButton" value="" onclick="switchPopWin(1, 'flyPlanAdepName');" />
           					<input type="hidden" name="flyPlanAdep" id="flyPlanAdep" value="${flyPlanAdep}" />
						</td>
						<td  class="leftTd">目的机场:</td>
						<td class="rightTd">
							<input type="text" name="flyPlanAdesName" id="flyPlanAdesName" class="selectText" onchange="cleanIdValue('flyPlanAdes');"  value="${flyPlanAdesName}"/><input type="button" class="selectButton" value="" onclick="switchPopWin(0, 'flyPlanAdesName');" />
           					<input type="hidden" name="flyPlanAdes" id="flyPlanAdes" value="${flyPlanAdes}" />
						</td>
						<td></td>
					</tr>
					<tr>
						<td class="leftTd">实施日期:</td>
						<td class="rightTd">
							<input type="text" class="dateSelectText" name="startDate" id="startDate" value="${startDate}" onFocus="syjDate(this)"/>
							到
							<input type="text" class="dateSelectText" name="endDate" id="endDate" value="${endDate}" onFocus="syjDate(this)"/>
							<select id="date" name="qDate" onchange="changePeriod()">
                                <option value="">
                                     全部
                                </option>
                                <c:forEach items="${autoPeriodList}" var="item">
                                    <c:choose>
                                        <c:when test="${qDate == item.code_id}">
                                            <option value="<c:out value="${item.code_id}"/>" selected="selected">
                                                <c:out value="${item.name}" />
                                            </option>
                                        </c:when>
                                        <c:otherwise>
                                          <option value="<c:out value="${item.code_id}"/>">
	                                             <c:out value="${item.name}" />
    	                                     </option>
                                        </c:otherwise>
                                    </c:choose>
                                </c:forEach>
                            </select>
                       	</td>
						<td  class="leftTd">计划类别:</td>
						<td class="rightTd">
							<select name="fplPlanClass" id="fplPlanClass" class="selectText">
								<option value="">全部</option>
		                    	<c:forEach var="item" items="${flyPlanKindList}">
		                    		<option value="${item.code_id}" 
		                    		<c:if test="${fplPlanClass eq item.code_id}">selected="selected" </c:if> >
		                    		${item.name}</option>
		                    	</c:forEach>
	                        </select>
						</td>
						<td  class="leftTd">计划状态:</td>
						<td class="rightTd">
							<select name="fplStatus" id="fplStatus" class="selectText">
								<option value="">全部</option>
		                    	<c:forEach var="fStatus" items="${fplStatusList}">
		                    		<c:choose>
                                        <c:when test="${fStatus.code_id == '40' || fStatus.code_id == '41' || fStatus.code_id == '42'}">
                                        </c:when>
                                        <c:otherwise>
                                         	<option value="${fStatus.code_id }" <c:if test="${fplStatus eq fStatus.code_id}">selected="selected"</c:if> >${fStatus.name }</option>
                                        </c:otherwise>
                                    </c:choose>
		                    	</c:forEach>
		                    	<option value="4" <c:if test="${fplStatus eq '4'}">selected="selected"</c:if> >已中止</option>
	                        </select>
						</td>
						<td></td>
						<td class="leftTd">
							<input type="button" value="查 询" onclick="javascript:queryFlyPlanList();"  class="setupLineButton" />
							&nbsp;&nbsp;<input name="button" type="reset" class="setupLineButton" value="清&nbsp;空"/>
						</td>
					</tr>
				</table>
			</div>
			<div class="fplanListDivLine"></div>
			<div class="fPlanListDiv" id="">
		       	<table class="lineTable fPlanListTable">
		           	<tr class="lineTableTitle">
		               	<td style="width: 12%;">计划编号</td>
		               	<td style="width: 16%;">计划名称</td> 
		               	<td style="width: 10%;">预计起飞时刻</td>
		               	<td style="width: 8%;">飞行时长</td>
		               	<td style="width: 13%;">起飞机场</td>
		               	<td style="width: 13%;">目的机场</td>
						<td style="width: 12%;">计划类别</td>
						<td style="width: 8%;">计划状态</td>
						<td style="width: 8%;">处理结果</td>
					</tr>
                   	<c:forEach var="flyPlan" items="${flyPlanList}" varStatus="status">
		    		    <c:choose>
		    		        <c:when test="${status.index % 2 == 0}">
		    		            <c:set var="tr_class" value="twoTdClass" scope="page" />
		    		        </c:when>
		    		        <c:otherwise>
		    		            <c:set var="tr_class" value="oneTdClass" scope="page" />
		    		        </c:otherwise>
		    		    </c:choose>
	    		    	<tr class="${tr_class}">
		    		    	<td>
		    		    		<a href="#" onclick="viewFlyPlanInfo('<c:out value="${flyPlan[0]}" />');">
		    		    			<c:out value="${flyPlan[1]}" />
		    		    		</a>
		    		    	</td>
		    		    	<td>
		    		    		<span title="<c:out value="${flyPlan[2]}" />">
		    		    			<string-truncation:truncation value="${flyPlan[2]}" length="30"/>
		    		    		</span>
		    		    	</td>
		    		    	<td>
		    		    		<c:out value="${flyPlan[3]}" />
			    		    	<c:if test="${!empty impPlan[3] && !empty impPlan[4] }">:</c:if>
			    		    	<c:out value="${flyPlan[4]}" />
		    		    	</td>
		    		    	<td>
    		    				<c:out value="${flyPlan[5]}" />
		    		    	</td>
		    		    	<td>
	    		    			<c:out value="${flyPlan[7]}" />
		    		    	</td>
		    		    	<td>
		    		    		<c:out value="${flyPlan[9]}" />
		    		    	</td>
		    		    	<td>
		    		    		<c:out value="${flyPlan[20]}" />
		    		    	</td>
		    		    	<td>
		    		    		<c:out value="${flyPlan[13]}" />
		    		    	</td>
		    		    	<td>
		    		    		<c:out value="${flyPlan[15]}" />
		    		    	</td>
	    		    	</tr>
	    			</c:forEach>
		    		<!--  补齐空行  -->
		    		<c:if test="${rollPage.pagePer - rollPage.currentlyPagePer > 0}">
			    		<c:forEach begin="${rollPage.currentlyPagePer}" end="${rollPage.pagePer-1}" step="1" varStatus="status">
			    			<c:choose>
		                        <c:when test="${(status.index) % 2 == 0}">
		                            <c:set var="tr_class" value="twoTdClass" scope="page" />
		                        </c:when>
		                        <c:otherwise>
		                            <c:set var="tr_class" value="oneTdClass" scope="page" />
		                        </c:otherwise>
		                    </c:choose>
		                     <tr class="${tr_class}">
	                    	<td></td><td></td><td></td><td></td>
	                    	<td></td><td></td><td></td><td></td>
	                    	<td></td>
	                    </tr>
			    		</c:forEach>
			    	</c:if>
				</table>
				<div class="pageDiv">
					<c:import url="/common/rollpage.jsp"/>
			    </div>
		    </div>
	     </form>
	</body>
</html>
