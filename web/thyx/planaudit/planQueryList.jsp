<%@ page language="java" pageEncoding="gbk"%>
<%@ include file="/common/taglibs.jsp" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%-- ************************************
 * System:    通航SAFE系统
 * Function  飞行计划查询列表
 * Author:    安怀梅
 * Copyright: 北京华安天诚科技有限公司
 * Create:    VER1.00 2013.07.10
 * Modify:    
************************************ --%>
<!DOCTYPE html
      PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
      "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="zh" lang="zh">
<head>
	
	<title>华赛集团质检项目信息化管理系统</title>
    <meta http-equiv="content-type" content="text/html; charset=gb2312" />
    <meta http-equiv="content-language" content="zh" />
    <meta http-equiv="content-script-type" content="text/javascript" />
    <meta name="robots" content="none" />
    <meta name="author" content="anhuaimei" />
    <meta name="copyright" content="2013, Beijing HATC Co., LTD" />
    <meta name="description" content="thyx" />
	<link href="css/skin2/thyx/fPlan.css" rel="stylesheet" type="text/css"/>
	<link href="css/skin2/thyx/fPlanContext.css" rel="stylesheet" type="text/css"/>
	<link href="css/skin2/common/showImg.css" rel="stylesheet" type="text/css"/>
	<link href="css/skin2/common/divWindow.css" rel="stylesheet" type="text/css"/>
	<link rel="stylesheet" type="text/css" href="<%=path %>/ad/jqueryPlus/autocomplete/jquery.autocomplete.css"/>
    <script src="js/flyPlan.js" type="text/javascript"></script>
	<script src="js/jquery-1.9.1.js" type="text/javascript"></script>
	<script src="js/divWindow.js" type="text/javascript"></script>
	<script src="js/calendar/syjDT.js" type="text/javascript"></script>
	<script src="js/validata/validata.js" type="text/javascript"></script>
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
		
		var selectArray = new Array(); 
		
		// 弹出选择窗口
		//index 用于区分起飞机场和目的机场，params 关键字查询对应的元素名称，
		function switchPopWin(index, params) {
			params = (params != null && params != "") ? params : "";
            //1 表示查询机场
			params = document.getElementById(params).value;
			showIframeDiv(320,500,'<c:url value="/adAction.do?op=getADInfo&isDep="/>' + index + '&adName=' + params,'iframe','机场查询',60)
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
		/**
	    function showDetail(param){
	
		  newCustWin('<c:url value="/planAuditAction.do?method=initPlanInfo&type=detail&planId="/>'+ param,'','835', '1000', '', '', 'nnnnn');
	    }*/
	  
	    //查询处理
	    function query() {
			startDate = document.getElementById("startDate");
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
       	    document.form1.action='<c:url value="/planAuditAction.do?method=searchList&menuFunctionId=BM10204"/>';
       	    document.form1.submit();
	    }
	
	  	/*
		 * 清空查询条件方法
		 * @return 
		 */
		function clearSelectCondition(){
			document.getElementById("planName").value="";
			document.getElementById("planCode").value="";
			// 起飞机场
			document.getElementById("flyPlanAdep").value="";
			document.getElementById("flyPlanAdepName").value="";
			// 目的机场
			document.getElementById("flyPlanAdes").value="";
			document.getElementById("flyPlanAdesName").value="";
			
			document.getElementById("startDate").value="";
			document.getElementById("endDate").value="";
			document.getElementById("date").value="";
			document.getElementById("flyPlanKind").value="";
			document.getElementById("pilot").value="";
			document.getElementById("planStatus").value="";
		}
		
		//名称被清除时将ID也清空
		function cleanIdValue(names) {
			document.getElementById(names).value = "";
	  	}
	  	
	  	// 飞行计划初始化
		function initFlyPlan() {
		  	var checked = 0;
		  	var status='';  // 获取状态
		  	var planid='';  // 获取操作对象ID
			for (i=0;i<document.form1.elements.length;i++){
			    if (document.form1.elements[i].name=="chkbox"){			    	
			    	if(document.form1.elements[i].checked == true) {
			    		checked ++;
			    		planid=document.form1.elements[i].value.split(",")[0];
			    		status=document.form1.elements[i].value.split(",")[1];
			    		if(status !=''&& status !='31' && status !='32' && status !='72'){  // 飞行状态为已报备、已批复、已确认的计划才能够讲解
							showConfirmDiv(0,'对不起，您所选择的飞行计划不具备讲解服务！','提示信息');
							return ;
			    		}
			    	}
			    }
			}
			if(checked == 0) {
				showConfirmDiv(0,'请至少选择一条飞行计划！','提示信息');
		  		return false;
		  	}else if(checked > 1) {
				showConfirmDiv(0,'仅能选择一条待讲解的飞行计划！','提示信息');
		  		return false;
		  	}
	  		document.form1.action='<c:url value="/planAuditAction.do?method=initFlyServiceInfo&pageBool=${rollPage.pageBool}&operFlag=flyService&planid="/>'+planid ;
			document.form1.submit();
		}	
		
		function showDetail(planid){
			document.form1.action='<c:url value="/planAuditAction.do?method=initFlyServiceInfo&pageBool=${rollPage.pageBool}&operFlag=view&planid="/>'+planid ;
			document.form1.submit();
		}
    </script>
	
	</head>
	<body onload="resetSize();" onresize="resetSize();">
	<div class="globalDiv">
		<div class="navText">
			飞行计划审批&nbsp;》飞行计划查询列表
		</div>
		<form method="post" name="form1">
		<input type="hidden" id="managerType" name="managerType" value="${managerType}"/>
			<div class="fplanListCheckDiv">
				<table class="inputTable fPlanListTable">
					<tr>
						<td class="leftTd">计划类别：</td>
						<td class="rightTd fPlanListTableTd">
							<select id="flyPlanKind" name="flyPlanKind" class="selectText">
	                               <option value="">全部</option>
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
						<td class="leftTd">计划编号：</td>
						<td class="rightTd">
							<input id="planCode" name="planCode" type="text" class="inputText" value="${planCode}"/>
						</td>
						<td class="leftTd">计划名称：</td>
						<td class="rightTd">
							<input id="planName" name="planName" type="text" class="inputText" value="${planName}"/>
						</td>
						<td class="leftTd">飞行员：</td>
						<td class="rightTd">
							 <input id="pilot" name="pilot" type="text" class="inputText" value="${pilot}"/>
						</td>
						<td></td>
					</tr>
					<tr>
						<td class="leftTd">实施日期：</td>
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
						<td class="leftTd">计划状态：</td>
						<td class="rightTd">
							<select id="planStatus" name="planStatus" class="selectText">
									<option value=" ">
										全部
									</option>
								<c:forEach items="${statusList}" var="item">
								<c:if test="${item.code_id != '11'}">
								<c:choose>
		                    		<c:when test="${item.code_id == planStatus}">
		                    			<option value="${item.code_id }" selected="selected">${item.name}</option>
		                    		</c:when>
		                    		<c:otherwise>
		                    			<option value="${item.code_id }">${item.name}</option>
		                    		</c:otherwise>
		                    	</c:choose>
		                    	</c:if>
								</c:forEach>
							</select>
						</td>
						<td class="leftTd">
							<input type="button" value="查 询" onclick="javascript:query();" class="setupLineButton" />&nbsp;&nbsp;<input name="button" type="button" class="setupLineButton" onclick="clearSelectCondition();" value="清&nbsp;空"/>
						</td>
					</tr>
				</table>
			</div>
			<div class="fplanListDivLine"></div>
				<div class="fPlanListDiv" id="">
					<table class="lineTable fPlanListTable">
			           	<tr class="lineTableTitle">
							<td style="width: 3%;"><input id="checkall" type="checkbox" onclick="checkedAll(this.form,'checkall','chkbox');" /></td>
                            <td style="width: 10%;">飞行计划编号</td>
                           <td style="width: 10%;">飞行计划名称</td>
                           <td style="width: 10%;">预计起飞日期</td>
                           <td style="width: 9%;">预计起飞时刻</td> 
                           <td style="width: 9%;">预计降落时刻</td>
                           <td style="width: 9%;">起飞机场</td>
                           <td style="width: 9%;">目的机场</td>
                           <td style="width: 9%;">计划类别</td>
                           <td style="width: 7%;">飞行员</td>
                           <td style="width: 7%;">计划状态</td>
                           <td style="width: 8%;">处理结果</td>  
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
		    		    	<td><input name="chkbox" type="checkbox" class="inputCheckbox" value="${info[0]},${info[11]}" /></td>
							<td>
								<a href="#" onclick="showDetail('${info[0]}');">
		    		    			<c:out value="${info[16]}" />
	    		    			</a>
		    		    	</td>
		    		    	<td>
	    		    			<span class="fontblue" title="<c:out value="${info[1]}" />"><string-truncation:truncation value="${info[1]}" length="12"/></span>
		    		    	</td>
		    		    	<td>
    		    				<c:out value="${info[2]}" />
		    		    	</td>
		    		    	<td>
		    		    		<c:out value="${info[3]}" />
		    		    	</td>
		    		    	<td>
		    		    		<c:out value="${info[4]}" />
		    		    	</td>
		    		    	<td>
								 <span class="fontblue" title="<c:out value="${info[5]}" />"><string-truncation:truncation value="${info[5]}" length="12"/></span>
		    		    	</td>
		    		    	<td>
		    		    		<span class="fontblue" title="<c:out value="${info[6]}" />"><string-truncation:truncation value="${info[6]}" length="12"/></span>
		    		    	</td>
		    		    	<td>
							  	<span class="fontblue" title="<c:out value="${info[7]}" />"><string-truncation:truncation value="${info[7]}" length="10"/></span>
		    		    	</td>
		    		    	<td>
		    		    		<c:out value="${info[9]}" />
		    		    	</td>
		    		    	<td>
		    		    		<c:out value="${info[10]}" />
		    		    	</td>
		    		    	<td>
		    		    	<c:if test="${info[11] eq '32'}">
		    		    	<c:choose>
		    		    	<c:when test="${info[12] eq '32' && info[13] eq '1'}">
		    		    		审批通过
	    		    		</c:when>
	    		    		<c:when test="${info[12] eq '32' && info[13] eq '2'}">
	    		    			审批不通过
	    		    		</c:when>
	    		    		</c:choose></c:if>
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
	                    	<td></td>
	                    </tr>
		    		</c:forEach>
		    		</c:if>
					</table>
				<div class="pageDiv">
					<c:import url="/common/rollpage.jsp"/>
			    </div>
			    <div class="fplanListCheckButton">
					<input name="Bnfp001" class="setupLineButton" type="button" value="飞行讲解" onclick="initFlyPlan();"/>
				</div>
			</div>
   		</form>
   	</div>
	</body>
</html>
