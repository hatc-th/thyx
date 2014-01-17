<%@ page language="java" pageEncoding="gbk"%>
<%@ include file="/common/taglibs.jsp" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%-- ************************************
 * System:    通航信息化管理系统
 * Function  飞行计划审批处理
 * Author:    安怀梅
 * Copyright: 北京华安天诚科技有限公司
 * Create:    VER1.00 2013.11.04
 * Modify:    
************************************ --%>
<!DOCTYPE html
      PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
      "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="zh" lang="zh">
<head>
	
	<title>华赛集团质检项目信息化管理系统</title>
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
	
		//全选/全不选
		function selectall(form,checked){
		  for (i=0;i<form.elements.length;i++){
		    if (form.elements[i].type=="checkbox"){
		    	if(form.elements[i].name=="chkbox"){
		    		form.elements[i].checked = checked;
		    	}
		    }
		  }
		}
		
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
	
	
		//查询处理
		function query() {
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
        	document.form1.action='<c:url value="/planAuditAction.do?method=searchList&menuFunctionId=BM10201&managerType=flow"/>';
        	document.form1.submit();
		}
		
	  // 审批飞行计划	
	  function auditPlan(){
	  
	  	 var checked = 0;
	  	 var statusValue="";	
		  for (i=0;i<document.form1.elements.length;i++){
		    if (document.form1.elements[i].name=="chkbox"){		    	
		    	if(document.form1.elements[i].checked == true) {
		    		checked ++;
		    		statusValue = document.form1.elements[i].value.split(",")[1];
		    	}
		    }
		  }
		  if(checked != 1) {
		 	 
		  	alert('请选择一条记录');
		  	return false;
		  }
		  // 已经批复的计划不能再审批
		  if (statusValue == '32'){
		  	alert("该计划已经批复,请重新选择!");
		  	return false;
		  }
	  		document.form1.action='<c:url value="/planAuditAction.do?method=initPlanInfo"/>';
			document.form1.submit();
	  }
	  
	  function flowDetail(param){
	
		newCustWin('<c:url value="/planAuditAction.do?method=flowDetail&planId="/>'+ param,'','835', '570', '', '', 'nnnnn');
	}
		
	 function showDetail(param){
		document.form1.action='<c:url value="/planAuditAction.do?method=initFlyServiceInfo&pageBool=${rollPage.pageBool}&operFlag=view&planid="/>'+param ;
		document.form1.submit();
	 }
	 
	 //名称被清除时将ID也清空
	 function cleanIdValue(names) {
	 	document.getElementById(names).value = "";
  	 }
	  	
    </script>
	
	</head>
	<body onload="resetSize();" onresize="resetSize();">
		<div class="globalDiv">
			<div class="navText">
				飞行计划审批&nbsp;》飞行计划审批管理
			</div>
			<form method="post" name="form1">
			<input type="hidden" id="managerType" name="managerType" value="${managerType}"/>
				<div class="fplanListCheckDiv">
					<table class="inputTable fPlanListTable">
						<tr>
						<td class="leftTd">
							计划类别：
						</td>
						<td  class="rightTd fPlanListTableTd">
							<select id="flyPlanKind" name="flyPlanKind"  class="selectText">
                                <option value="">
                                    	全部
                                </option>
                                <c:forEach items="${flyPlanKindList}" var="item">
                                    <c:choose>
                                        <c:when test="${flyPlanKind == item.code_id}">
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
						<td class="leftTd">
							计划编号：
						</td>
						<td class="rightTd">
							<input id="planCode" name="planCode" type="text" class="selectText" value="${planCode}"/>
						</td>
						<td class="leftTd">
							计划名称：
						</td>
						<td class="rightTd">
							<input id="planName" name="planName" type="text" class="selectText" value="${planName}"/>
						</td>
						<td class="leftTd">
							飞行员：
						</td>
						<td class="rightTd">
							 <input id="pilot" name="pilot" type="text" class="selectText" value="${pilot}"/>
						</td>
					</tr>
					<tr>
						<td class="leftTd">
							实施日期：
						</td>
						<td class="rightTd">
		                    <input type="text" class="dateSelectText" name="startDate" id="startDate" value="${startDate}" onfocus="syjDate(this)"/>
							到
							<input type="text" class="dateSelectText" name="endDate" id="endDate" value="${endDate}" onfocus="syjDate(this)"/>
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
						<td class="leftTd">起飞机场：</td>
						<td class="rightTd">
							<input type="text" name="flyPlanAdepName" id="flyPlanAdepName" class="selectText" onchange="cleanIdValue('flyPlanAdep');"  value="${flyPlanAdepName}"/><input type="button" class="selectButton" value="" onclick="switchPopWin(1, 'flyPlanAdepName');" />
           					<input type="hidden" name="flyPlanAdep" id="flyPlanAdep" value="${flyPlanAdep}" />
						</td>
						<td class="leftTd">目的机场：</td>
						<td class="rightTd">
							<input type="text" name="flyPlanAdesName" 	id="flyPlanAdesName" class="selectText" onchange="cleanIdValue('flyPlanAdes');"  value="${flyPlanAdesName}"/><input type="button" class="selectButton" value="" onclick="switchPopWin(0, 'flyPlanAdesName');" />
           					<input type="hidden" name="flyPlanAdes" id="flyPlanAdes" value="${flyPlanAdes}" />
						</td>
						<td class="leftTd"  colspan="2">
							<input type="button" value="查 询" onclick="javascript:query();" class="setupLineButton" />
							&nbsp;&nbsp;<input name="button" type="reset" class="setupLineButton" value="清&nbsp;空"/>
						</td>
					</tr>
					</table>
				</div>
				<div class="fplanListDivLine"></div>
				<div class="fPlanListDiv" id="">
			       <table class="lineTable fPlanListTable">
                		<tr class="lineTableTitle">
							<td style="width: 3%; "><input name="checkall" type="checkbox" value="" onclick="selectall(this.form,this.checked) "/></td>
                            <td style="width: 10%; ">
							飞行计划编号
                           </td>
                           <td style="width: 13%; ">
							飞行计划名称
                           </td>
                           <td style="width: 10%; ">
							预计起飞日期
                           </td>
                           <td style="width: 10%; ">
							预计起飞时刻
                           </td> 
                           <td style="width: 10%; ">
							预计降落时刻
                           </td>
                           <td style="width: 9%; ">
							起飞机场
                           </td>
                           <td style="width: 9%; ">
							目的机场
                           </td>
                           <td style="width: 10%; ">
							计划类别
                           </td>
                           <td style="width: 8%; ">
							飞行员
                           </td>
                           <td style="width: 8%; ">
							审批结果
                           </td>   
						</tr>
						<c:forEach var="info" items="${dataList}" varStatus="status">
		    		    <c:choose>
		    		        <c:when test="${status.index % 2 == 0}">
		    		            <c:set var="tr_class" value="twoTdClass" scope="page" />
		    		        </c:when>
		    		        <c:otherwise>
		    		            <c:set var="tr_class" value="oneTdClass" scope="page" />
		    		        </c:otherwise>
		    		    </c:choose>
		    		    <tr class="${tr_class}">
		    		    	<td >
								<input name="chkbox" type="checkbox" class="inputCheckbox" value="${info[0]},${info[11]}" />
							</td>
							<td >
	    		    			<a href="#" onclick="showDetail('${info[0]}');">
		    		    			<c:out value="${info[16]}" />
	    		    			</a>
		    		    	</td>
		    		    	<td >
	    		    			<span title="<c:out value="${info[1]}" />"><string-truncation:truncation value="${info[1]}" length="22"/></span>
		    		    	</td>
		    		    	<td >
    		    				<c:out value="${info[2]}" />
		    		    	</td>
		    		    	<td >
		    		    		<c:out value="${info[3]}" />
		    		    	</td>
		    		    	<td >
		    		    		<c:out value="${info[4]}" />
		    		    	</td>
		    		    	<td >
								 <span class="fontblue" title="<c:out value="${info[5]}" />"><string-truncation:truncation value="${info[5]}" length="16"/></span>
		    		    	</td>
		    		    	<td >
		    		    		<span class="fontblue" title="<c:out value="${info[6]}" />"><string-truncation:truncation value="${info[6]}" length="16"/></span>
		    		    	</td>
		    		    	<td >
		    		    		<c:out value="${info[7]}" />
		    		    	</td>
		    		    	<td >
		    		    		<c:out value="${info[9]}" />
		    		    	</td>
		    		    	<!-- 
		    		    	<td style="text-align: left;"><a href="#" onclick="flowDetail('${info[0]}');">
		    		    	<c:choose>
		    		    	<c:when test="${info[12] eq '32' && info[13] eq '1'}">
		    		    		批复通过
	    		    		</c:when>
	    		    		<c:when test="${info[12] eq '32' && info[13] eq '2'}">
	    		    			批复不通过
	    		    		</c:when>
	    		    		<c:when test="${empty info[12]}">
	    		    			待批复
	    		    		</c:when>
	    		    		<c:otherwise>
	    		    			<c:out value="${info[14]}" />
	    		    		</c:otherwise>
	    		    		</c:choose></a>
		    		    	</td>
		    		    	 -->
		    		    	<td style="text-align: left;"><a href="#" onclick="flowDetail('${info[0]}');">
		    		    	<c:choose>
		    		    	<c:when test="${info[12] eq '33'}">
		    		    		<c:out value="${info[15]}" />
	    		    		</c:when>
	    		    		<c:otherwise>
	    		    		</c:otherwise>
	    		    		</c:choose></a>
		    		    	</td>
		    		    </tr>
		    		</c:forEach>
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
	                    	<td></td>
	                    	<td></td>
	                    	<td></td>
	                    	<td></td>
	                    	<td></td>
	                    	<td></td>
	                    	<td></td>
	                    	<td></td>
	                    	<td></td>
	                    	<td></td>
	                    	<td></td>
	                    </tr>
		    		</c:forEach>
		    		</c:if>
					</table>
			       <!-- 管理页面时显示相关功能区域 -->
		    		<div class="pageDiv">
						<c:import url="/common/rollpage.jsp" />
				    </div>
				    <div class="fplanListCheckButton">
						<input name="Bnap003" class="setupLineButton" type="button" value="审&nbsp;&nbsp;批" onclick="auditPlan();" />
					</div>
				</div>
   	</form>
   	</div>
	</body>
</html>
