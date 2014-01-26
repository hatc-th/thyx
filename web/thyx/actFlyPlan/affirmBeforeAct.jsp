<%@ page language="java" pageEncoding="gbk"%>
<%@ include file="/common/taglibs.jsp" %>
<%@ include file="/common/includeCss.jsp"%>
<c:import url="/common/popSelect.jsp" />
<!DOCTYPE html
      PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
      "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="zh" lang="zh">
<head>
	
<title>计划实施管理</title>
<meta http-equiv="content-language" content="zh" />
<meta http-equiv="content-script-type" content="text/javascript" />
<link rel="stylesheet" href= "<c:url value="css/thyx/flyPlan.css" />" type="text/css"></link>
	<script src="<c:url value="/js/widget_calendar.js" />"></script>
<script type="text/javascript">

/*
 * 检查是否有选择单选框，如果有选择，则获取选中的信息
 * @return 选中行的信息
 */
function isSelectd(){
	var radioList = document.getElementsByName("flyPlan");
	var flyPlan = "";
	for(i=0;i<radioList.length;i++){
		if(true == radioList[i].checked){
			flyPlan = radioList[i].value;
		}
	}
	return flyPlan;
}
		// 弹出选择窗口
		//index 用于区分弹出页，winName 弹出窗口名称，params 关键字查询对应的元素名称，
		function switchPopWin(index, winName, params) {
			params = (params != null && params != "") ? params : "";
            switch (index) {
                //1 表示查询机场
				case 1:
				params = document.getElementById(params).value;
				newCustWin('<c:url value="/dicTreeAction.do?method=popAirP&option=radio&code=yes&typeFlag=AD&sqlKeywords=" />'+params,winName, 350, 470, '', '', 'ynnnn');
				break;
	        }
		} 

/*
 * 当选择不是在备降场降落时，备降相关信息禁用
 * @return 
 */
function changeP(){
	var radioList = document.getElementsByName("isMainP");
	for(i=0;i<radioList.length;i++){
		if(true == radioList[i].checked ){
			if(radioList[i].value == '1'){
				//清空计划中的备降场
				document.getElementById("floatAltn1").value="";
				document.getElementById("subAltn1").value="";
				document.getElementById("floatAltn1").disabled=true;
				document.getElementById("reason").disabled=true;
				document.getElementById("showAltn1Model").disabled=true;
				document.getElementById("floatAltn1").style.background = '#DFDFDF';
				document.getElementById("reason").style.background = '#DFDFDF';
				document.getElementById("showAltn1Model").style.background = '#DFDFDF';
			}else{
				document.getElementById("floatAltn1").disabled=false;
				document.getElementById("reason").disabled=false;
				document.getElementById("showAltn1Model").disabled=false;
				document.getElementById("floatAltn1").style.background = '#fff';
				document.getElementById("reason").style.background = '#fff';
				document.getElementById("showAltn1Model").style.background = '#fff';
				//显示计划中的备降场
				var altns = document.getElementById("Altn1").value;
				textArray = altns.split('@_@');
				if(textArray.length>1){
					document.getElementById("floatAltn1").value=textArray[0];
					document.getElementById("subAltn1").value=textArray[1];
				}
			}
		}
	}
}
/*
 * 切换主备航线，并且显示不同的超链接
 * @parma 航线类型
 * @return 
 */
function changeRs(RsType,flag){
	var planRs = document.getElementById("AllRs" + flag).value;
	textArray = planRs.split('@_@');
	if("0"==RsType){
		document.getElementById("floatPlanRs" + flag).innerHTML="<a href='#'>" + textArray[1] + "</a>";
	}else{
		document.getElementById("floatPlanRs" + flag).innerHTML="<a href='#'>" + textArray[0] + "</a>";
	}
}

/*
 * 弹出div窗口，并且将信息传送给div窗口中的元素
 * @parma 标志位，该标志为区分当前窗口处理的任务
 * @return 
 */
function openDivCheck(flag){
	
	//检查有没有选择计划
	var flyplanList = isSelectd();
	var width = (screen.availWidth - 345 - 216)/2;      // 屏幕可用宽度
   	var height = (screen.availHeight - 150 - 345)/2;    // 屏幕可用高度
	//当为实施前确认时
	if("" != flyplanList){
		var textArray = flyplanList.split('@_@');
		var planList = textArray[0];
		var index = textArray[1];
		if("1" == flag){
			//处理计划id
			document.getElementById("flyplanId1").value=planList;
			//处理人员
			var commander = document.getElementById("commander" + index).value;
			textArray = commander.split('@_@');
			
			var list = document.getElementById("tpilot" + flag);
			for(i = 0;i<list.length;i++){
				if(list[i].value == textArray[1]){
					list[i].selected = true;
				}
			}
			//处理航空器编号
			list = document.getElementsByName("floatAcid" + flag);
			var acid = document.getElementById("acid" + index).value
			for(i = 0;i<list.length;i++){
				if(list[0].value == acid){
					list[0].selected = true;
				}
			}
			
			//处理航线
			var planRs = document.getElementById("planRs" + index).value
			document.getElementById("AllRs" + flag).value = planRs;
			textArray = planRs.split('@_@');
			//只有一条航线时，禁用备航线
			if(textArray.length<2){
				document.getElementById("NoMainRs" + flag).disabled="disabled";
			}
			//设置航线超连接
			document.getElementById("floatPlanRs" + flag).innerHTML="<a href='#'>" + textArray[0] + "</a>";
			
			//设置窗口显示 TODO,缺少覆盖下面图层的方法
			document.getElementById("floatDiv" + flag).style.display = "block";
			document.getElementById("floatDiv" + flag).style.height = "270px";
			document.getElementById("floatDiv" + flag).style.left = width + "px";
			document.getElementById("floatDiv" + flag).style.top = height + "px";
		}else if("2" == flag){
			
			//处理计划id
			document.getElementById("flyplanId2").value=planList;
			//处理调整备注
			var adJust = document.getElementById("adJust" + index).value;
			document.getElementById("submitAdjust").value = adJust;
			
			//调整弹出窗口的高度
			document.getElementById("floatDiv" + flag).style.height = "200px";
			document.getElementById("floatDiv" + flag).style.left = width + "px";
			document.getElementById("floatDiv" + flag).style.top = height + "px";
			document.getElementById("floatDiv" + flag).style.display = "block";
		}else if("3" == flag){
			//处理计划id
			document.getElementById("flyplanId3").value=planList;
			//处理人员
			var commander = document.getElementById("commander" + index).value;
			textArray = commander.split('@_@');
			
			var list = document.getElementById("tpilot" + flag);
			for(i = 0;i<list.length;i++){
				if(list[i].value == textArray[1]){
					list[i].selected = true;
				}
			}
			list = document.getElementsByName("floatAcid" + flag);
			var acid = document.getElementById("acid" + index).value
			for(i = 0;i<list.length;i++){
				if(list[0].value == acid){
					list[0].selected = true;
				}
			}
			
			//处理航线
			var planRs = document.getElementById("planRs" + index).value
			document.getElementById("AllRs" + flag).value = planRs;
			textArray = planRs.split('@_@');
			
			//只有一条航线时，禁用备航线
			if(textArray.length<2){
				document.getElementById("NoMainRs" + flag).disabled="disabled";
			}
			//设置航线超连接
			document.getElementById("floatPlanRs" + flag).innerHTML="<a href='#'>" + textArray[0] + "</a>";
			
			var altn = document.getElementById("Altn1" + index).value
			document.getElementById("Altn1").value = altn;
			
			//调整弹出窗口的高度
			document.getElementById("floatDiv" + flag).style.height = "460px";
			var width = (screen.availWidth - 345 - 216)/2;      // 屏幕可用宽度
   			var height = (screen.availHeight - 500 - 345)/2;    // 屏幕可用高度
			document.getElementById("floatDiv" + flag).style.display = "block";
			document.getElementById("floatDiv" + flag).style.left = width + "px";
			document.getElementById("floatDiv" + flag).style.top = height + "px";
		}
	}else{
		alert("没有选择需要确认的计划！");
	}
}
/*
 * 关闭弹出的div窗口
 * @return 
 */
function closeAffirmDiv(flag){
	//清空隐藏div表单中的值
	if(flag!='1' ){
		var isMainRs = document.getElementsByName("isMainRs");
		for(i=0;i<isMainRs.length;i++){
			if(isMainRs[i].value=='0'){
				isMainRs[i].checked=true;
			}
		}
	}
	if(flag=='3'){
		var isMainRs = document.getElementsByName("isMainP");
		for(i=0;i<isMainRs.length;i++){
			if(isMainRs[i].value=='1'){
				isMainRs[i].checked=true;
				document.getElementById("floatAltn1").disabled=true;
				document.getElementById("reason").disabled=true;
				document.getElementById("floatAltn1").style.background = '#DFDFDF';
				document.getElementById("reason").style.background = '#DFDFDF';
			}
		}
	}
	document.getElementById("floatDiv" + flag).style.display = "none";
}

/*
 * 合法性检查方法
 * @param 标志位，该标志为区分当前窗口处理的任务
 */
function checkSubmitCentext(flag){
	
	if(flag=="3"){
	
		//检查实际起飞日期
		var checkValue = document.getElementById("pStartDate").value; 
		if(checkValue == "" || checkValue == null ){
			alert("实际开飞的日期不能为空！");
			return;
		}
		//检查实际起飞小时
		checkValue = document.getElementById("startHour").value; 
		if(checkValue == "" || checkValue == null ){
			alert("实际开飞的小时不能为空！");
			return;
		}
		
		//检查实际起飞小时
		checkValue = document.getElementById("startMinute").value; 
		if(checkValue == "" || checkValue == null ){
			alert("实际开飞的分钟不能为空！");
			return;
		}
		//检查实际降落日期
		checkValue = document.getElementById("pEndDate").value; 
		if(checkValue == "" || checkValue == null ){
			alert("实际降落的日期不能为空！");
			return;
		}
		//检查实际降落小时
		checkValue = document.getElementById("endHour").value; 
		if(checkValue == "" || checkValue == null ){
			alert("实际降落的小时不能为空！");
			return;
		}
		
		//检查实际降落分钟
		checkValue = document.getElementById("endMinute").value; 
		if(checkValue == "" || checkValue == null ){
			alert("实际降落的分钟不能为空！");
			return;
		}
		
		//检查数据合法性
		var startDate = document.getElementById("pStartDate").value;
		var startHour = document.getElementById("startHour").value;
		var startMinute = document.getElementById("startMinute").value; 
		var endDate = document.getElementById("pEndDate").value;
		var endHour = document.getElementById("endHour").value;
		var endMinute = document.getElementById("endMinute").value;
		
		if(startDate<=endDate){
			if(startHour <= endHour){
				if(startHour >-1 && startHour<24){
				}else{
					alert("实际开飞的小时不符合规范");
					return;
				}
				if(endHour >-1 && endHour<24){
					
				}else{
					alert("实际降落的小时不符合规范");
					return;
				}
				if(startMinute<=endMinute){
					if(startMinute >-1 && startMinute<60){
					}else{
						alert("实际开飞的分钟不符合规范");
						return;
					}
					if(endMinute >-1 && endMinute<60){
						
					}else{
						alert("实际降落的分钟不符合规范");
						return;
					}
				}else{
					alert("实际开飞的分钟不能大于实际降落的分钟");
					return;
				}
			}else{
				alert("实际开飞的小时不能大于实际降落的小时");
				return;
			}
		}else{
			alert("实际开飞的日期不能大于实际降落的日期");
			return;
		}
		
		
		document.flyPlanDiv3.submit();
	}
}

/*
 * 提交表单方法 TODO 已过时
 * @parma 标志位，该标志为区分当前窗口处理的任务
 * @return 
 */
function submitPlan(flag){
	document.flyPlanDiv1.action="" + flag;
	document.flyPlanDiv1.submit();
}
/*
 * 清空查询条件方法
 * @return 
 */
function clearSelectContext(){
	document.getElementById("planName").value="";
	document.getElementById("planCode").value="";
	document.getElementById("flyStartDate").value="";
	document.getElementById("flyEndDate").value="";
	document.getElementById("airCraftType").value="";
	document.getElementById("adepName").value="";
	document.getElementById("adesName").value="";
}
onclick="switchPopWin(1, 'adep_adepName_adepPos_adepCode_IsSkyWay', 'adepName');"
/**
 * 跳转到详细信息页面
 * @param 计划唯一标识
 */
function showDetail(param){
	newCustWin('<c:url value="/planAuditAction.do?method=initPlanInfo&type=detail&planId="/>'+ param,'','835', '570', '', '', 'nnnnn');
}
</script>
</head>
<body>
<div id="divtop"  style="width:99.5%">
	<c:import url="/common/pageTitle.jsp" />
</div>
<br/>
<div class="selectFlyPlanDiv">
	<form name="selectFlyPlan" action="ActFlyPlanAction.do?method=queryActFlyPlanList&selectFlag=${selectFlag }" method="post">
		<div id="selectDiv">
			<table>
			 <tr class="textRight" style="height:35px;">
				<td class="selectDivRightTd">计划名称：</td>
				<td class="selectDivLeftTd"><input class="inputTextTh" type="text" name="planName" id="planName" value="${planName}"/></td>
				<td class="selectDivRightTd">计划编号：</td>
				<td class="selectDivLeftTd"><input class="inputTextTh" type="text" name="planCode" id="planCode" value="${planCode }"/></td>
				<td class="selectDivRightTd">计划起始日期：</td>
				<td class="selectDivLeftTd"><input class="inputTextTh" style="width:110px;" type="text" id="flyStartDate" name="flyStartDate" value="${flyStartDate}"/><input 
		                                        type="button" class="inputButtonDate" value="..." style="border:1px #ccc solid;height:22px;background:#FFFFFF;color:#ccc" onclick="return showCalendarObj(this.previousSibling, '%Y-%m-%d');" /></td>
				<td width="92" class="selectDivRightTd">计划结束日期：</td>
				<td width="139" class="selectDivLeftTd"><input class="inputTextTh" style="width:110px;" type="text" id="flyEndDate" name="flyEndDate" value="${flyEndDate}"/><input 
		                                        type="button" class="inputButtonDate" value="..." style="border:1px #ccc solid;height:22px;background:#FFFFFF;color:#ccc" onclick="return showCalendarObj(this.previousSibling, '%Y-%m-%d');" /></td>
				
			  </tr>
			  <tr  class="textRight">
				<td class="selectDivRightTd">航空器类型：</td>
				<td class="selectDivLeftTd"><input class="inputTextTh" type="text" name="airCraftType" id="airCraftType" value="${airCraftType }"/></td>
				<td class="selectDivRightTd">起飞机场：</td>
				<td class="selectDivLeftTd"><input class="inputTextTh" style="width:110px;" type="text" name="adepName" id="adepName" value="${adep }"/><input type="button" class="inputButtonDate" value="..." style="border:1px #ccc solid;height:22px;background:#FFFFFF;color:#ccc" onclick="switchPopWin(1, 'adep_adepName', 'adepName');" />
					<input class="inputTextTh" type="hidden" name="adep" id="adep" value=""/>
				</td>
				<td class="selectDivRightTd">目的机场：</td>
				<td class="selectDivLeftTd"><input class="inputTextTh" style="width:110px;" type="text" name="adesName" id="adesName" value="${ades }"/><input type="button" class="inputButtonDate" value="..." style="border:1px #ccc solid;height:22px;background:#FFFFFF;color:#ccc" onclick="switchPopWin(1, 'ades_adesName', 'adesName');" />
					<input class="inputTextTh" type="hidden" name="ades" id="ades" value=""/></td>
				<td>&nbsp;</td>
				<td class="selectDivRightTd"><input name="button" type="submit" value="查&nbsp;询" class="inputButtonCtrl"/>&nbsp;&nbsp;<input name="button" type="button" class="inputButtonCtrl" onclick="clearSelectContext()"; value="清&nbsp;空"/></td>
			  </tr>
			</table>
		</div>
		<div class="divTable">
			<table class="tableBorder">
			  <tr class="tdbg">
			    <td width="1%" height="25">&nbsp;</td>
			    <td width="5%">序号</td>
			    <td width="14%">飞行计划名称</td>
			    <td width="10%">飞行计划编号</td>
			    <td width="10%">飞行规则</td>
			    <td width="10%">计划起飞时间</td>
			    <td width="10%">飞行时长（分钟）</td>
			    <td width="10%">起飞机场</td>
			    <td width="10%">目的机场</td>
			    <td width="10%">航空器类型</td>
			    <td width="10%">状态</td>
			  </tr>
				  <c:forEach  var="item" items="${planList}" varStatus="statu">
					  <c:choose>
				      	<c:when test="${statu.index % 2 == 0}">
				      		<c:set var="tr_class" value="tr_single" scope="page" />
				      	</c:when>
				      <c:otherwise>
				      	<c:set var="tr_class" value="tr_double" scope="page" />
				      </c:otherwise>
			      </c:choose>
				  <tr class="${tr_class}" style="height:35px;">
						<c:forEach var="it" items="${item}" varStatus="t">
							<c:choose>
								<c:when test="${t.index eq 0}">
									<td><input type="radio" name="flyPlan" value="${it}@_@${statu.index + 1}"/></td>
				  	 				<td>
				  	 					${statu.index + 1}
				  	 				</td>
								</c:when>
								<c:when test="${t.index eq 1}">
									<input type="hidden" name="commander${statu.index + 1}" id="commander${statu.index + 1}" value="${it}"/>
								</c:when>
								<c:when test="${t.index eq 2}">
									<input type="hidden" name="equip${statu.index + 1}" id="equip${statu.index + 1}" value="${it}"/>
								</c:when>
								<c:when test="${t.index eq 3}">
									<input type="hidden" name="acid${statu.index + 1}" id="acid${statu.index + 1}" value="${it}"/>
								</c:when>
								<c:when test="${t.index eq 4}">
									<input type="hidden" name="planRs${statu.index + 1}" id="planRs${statu.index + 1}" value="${it}"/>
								</c:when>
								<c:when test="${t.index eq 5}">
									<input type="hidden" name="adJust${statu.index + 1}" id="adJust${statu.index + 1}" value="${it}"/>
								</c:when>
								<c:when test="${t.index eq 6}">
									<input type="hidden" name="faIaltn${statu.index + 1}" id="faIaltn${statu.index + 1}" value="${it}"/>
								</c:when>
								<c:when test="${t.index eq 7}">
									<input type="hidden" name="Altn1${statu.index + 1}" id="Altn1${statu.index + 1}" value="${it}"/>
								</c:when>
								<c:when test="${t.index eq 8}">
									<td><a href="#" onclick="showDetail('${item[0]}');">${it}</a></td>
								</c:when>
						        <c:otherwise>
							  		<td>${it}</td>
							  	</c:otherwise>
						  	</c:choose>
						  </c:forEach>
						</tr>
					  </c:forEach>
					<c:if test="${rollPage.pagePer - rollPage.currentlyPagePer > 0}">
		    		<c:forEach begin="${rollPage.currentlyPagePer}" end="${rollPage.pagePer-1}" step="1" varStatus="status">
		    			<c:choose>
	                        <c:when test="${(status.index) % 2 == 0}">
	                            <c:set var="tr_class" value="tr_single" scope="page" />
	                        </c:when>
	                        <c:otherwise>
	                            <c:set var="tr_class" value="tr_double" scope="page" />
	                        </c:otherwise>
	                    </c:choose>
		                    <tr class="${tr_class}">
		                    	<td colspan="11"  style="height:35px;"></td>
		                    </tr>
		    		</c:forEach>
		    		</c:if>
			</table>
			<div class="bottomDiv">
				<c:choose>
					<c:when test="${selectFlag eq 1}">
						<input type="button" onclick="openDivCheck('1')" name="" value="确&nbsp;认" class="inputButtonCtrl"/>
					</c:when>
					<c:when test="${selectFlag eq 2}">
						<input type="button" onclick="openDivCheck('2')" name="" value="调&nbsp;整" class="inputButtonCtrl"/>
					</c:when>
					<c:when test="${selectFlag eq 3}">
						<input type="button" onclick="openDivCheck('3')" name="" value="确&nbsp;认" class="inputButtonCtrl"/>
					</c:when>
				</c:choose>
			</div>
		 </div> 
		 <div id="divbot" style="width:99.5%">
			<c:import url="/common/rollpage.jsp"/>
		</div>
	</form>
</div>
<!-- =======================实施前确认弹出窗口=========================== -->
<div id="floatDiv1" class="floatDiv">
	<div id="floatDivTitle" >
		<div id="titleContext">确认计划</div>
		<div id="floatDivClose" onclick="closeAffirmDiv('1')">X</div>
	</div>
	<div id="arrirmPlan">
		<form name="flyPlanDiv1" action="<c:url value="/ActFlyPlanAction.do?method=affirmBeforeAct"/>&selectFlag=1" method="post">
			<input id="flyplanId1" name="flyplanId1" type="hidden" value=""/>
			<div id="ContextName" class="ContextName">
				<ul>
					<li>飞行员：</li>
					<li>航空器编号：</li>
					<li>主备航线：</li>
					<li>航线：</li>
					<li id="remarkLi">备注：</li>
				</ul>
			</div>
			<div id="ContextInput" class="ContextInput">
				<ul>
					<li>
						<select class="floatDivInput" id="tpilot1" name="tpilot">
							<c:forEach  var="item" items="${politList}" varStatus="statu">
								<option value="${item[0] }">${item[1]}</option>
							</c:forEach>					
						</select>
					</li>
					<li>
						<select class="floatDivInput" id="floatAcid1" name="floatAcid">
							<c:forEach  var="item" items="${acidList}" varStatus="statu">
								<option value="${item[0] }">${item[1]}</option>
							</c:forEach>					
						</select>
					</li>
					<li><input id="MainRs" name="isMainRs" type="radio" checked="checked" value="0" onclick="changeRs('1','1')"/>
						主航线&nbsp;&nbsp;&nbsp;<input id="NoMainRs1" type="radio" name="isMainRs" value="1" onclick="changeRs('0','1')"/>备航线
					</li>
					<li>
						<span id="floatPlanRs1"></span>
						<input type="hidden" id="AllRs1" name="AllRs" value=""/>
					</li>
					<li id="remarkTextareaLi"><textarea name="remark" id="remark" class="remarkTextarea"></textarea></li>
					<li>
						<input type="submit" class="button" value=" 确&nbsp;定"/>
					</li>
				</ul>
			</div>
		</form>
	</div>
</div>
<!-- =======================实施调整弹出窗口=========================== -->
<div id="floatDiv2" class="floatDiv">
	<div id="floatDivTitle" >
		<div id="titleContext">调整计划</div>
		<div id="floatDivClose" onclick="closeAffirmDiv('2')">X</div>
	</div>
	<div id="arrirmPlan">
		<form name="flyPlanDiv2" action="<c:url value="/ActFlyPlanAction.do?method=affirmBeforeAct"/>&selectFlag=2" method="post">
			<input id="flyplanId2" name="flyplanId2" type="hidden" value=""/>
				<div class="adContextDiv">
				<div class="adContextName">备注：</div>
				<div class="adContextInput"><textarea name="submitAdjust" id="submitAdjust" class="adjustRemark"></textarea></div>
				</div>
				<div class="adSubmitDiv"><input type="submit" class="button" value=" 确&nbsp;定"/></div>
		</form>
	</div>
</div>
<!-- =======================实施确认弹出窗口=========================== -->
<div id="floatDiv3" class="floatDiv">
	<div id="floatDivTitle" >
		<div id="titleContext">确认计划</div>
		<div id="floatDivClose" onclick="closeAffirmDiv('3')">X</div>
	</div>
	<div id="arrirmPlan">
		<form name="flyPlanDiv3" action="<c:url value="/ActFlyPlanAction.do?method=affirmBeforeAct"/>&selectFlag=3" method="post">
			<input id="flyplanId3" name="flyplanId3" type="hidden" value=""/>
			<div id="ContextName" class="ContextName">
				<ul>
				    <li>飞行员：</li>
					<li>航空器编号：</li>
					<li>主备航线：</li>
					<li>航线：</li>
					<li>实际起飞时刻：</li>
					<li>实际降落时刻：</li>
					<li>是否备降场降落：</li>
					<li>备降场：</li>
					<li id="remarkLi">备降原因：</li>
					<li id="remarkLi">备    注：</li>
				</ul>
			</div>
			<div id="ContextInput" class="ContextInput">
				<ul>
					<li>
						<select class="floatDivInput" id="tpilot3" name="tpilot">
							<c:forEach  var="item" items="${politList}" varStatus="statu">
								<option value="${item[0] }">${item[1]}</option>
							</c:forEach>					
						</select>
					</li>
					<li>
						<select class="floatDivInput" id="floatAcid3" name="floatAcid">
							<c:forEach  var="item" items="${acidList}" varStatus="statu">
								<option value="${item[0] }">${item[1]}</option>
							</c:forEach>					
						</select>
					</li>
					<li><input id="MainRs" name="isMainRs" type="radio" checked="checked" value="0" onclick="changeRs('1','3')"/>
						主航线&nbsp;&nbsp;&nbsp;<input id="NoMainRs3" type="radio" name="isMainRs" value="1" onclick="changeRs('0','3')"/>备航线
					</li>
					<li>
						<span id="floatPlanRs3"></span>
						<input type="hidden" id="AllRs3" name="AllRs" value=""/>
					</li>
					<li>
						<input class="inputTextTh" style="width:70px;" type="text" id="pStartDate" name="pStartDate"/><input 
                                        type="button" class="inputButtonDate" style="border:1px #ccc solid;height:22px;background:#FFFFFF;color:#ccc"  value="..." onclick="return showCalendarObj(this.previousSibling, '%Y-%m-%d');" />
                        &nbsp;<input type="text" id="startHour" name="startHour" class="hmInput"></input>：<input type="text" id="startMinute" name="startMinute" class="hmInput"></input>
					</li>
					<li>
						<input class="inputTextTh" style="width:70px;" type="text" id="pEndDate" name="pEndDate"/><input 
                                        type="button" class="inputButtonDate" style="border:1px #ccc solid;height:22px;background:#FFFFFF;color:#ccc"  value="..." onclick="return showCalendarObj(this.previousSibling, '%Y-%m-%d');" />
                        &nbsp;<input type="text" id="endHour" name="endHour" class="hmInput"></input>：<input type="text" id="endMinute" name="endMinute" class="hmInput"></input>
					</li>
					<li><input name="isMainP" type="radio" value="0" onclick="changeP()"/>
						是&nbsp;&nbsp;&nbsp;<input type="radio" name="isMainP" checked="checked" onclick="changeP()" value="1"/>否
						<input type="hidden" id="Altn1" name="Altn1"/>
					</li>
					<li>
						<input type="text" class="readOnlyStyle floatDivInput" style="width:155px;height:18px;" id="floatAltn1" name="floatAltn1" disabled="disabled"/><input type="button" id="showAltn1Model" class="inputButtonDate" disabled="disabled" value="..." style="border:1px #ccc solid;height:20px;background:#DFDFDF;" onclick="switchPopWin(1, 'subAltn1_floatAltn1', 'floatAltn1');" />
						<input type="hidden" id="subAltn1" name="subAltn1" value=""/>
					</li>
					<li id="remarkTextareaLi"><textarea name="reason" id="reason" class="readOnlyStyle remarkTextarea" disabled="disabled"></textarea></li>
					<li id="remarkTextareaLi"><textarea name="desc" id="desc" class="remarkTextarea"></textarea></li>
					<li>
						<input type="button" onclick="checkSubmitCentext('3')" class="button" value="确&nbsp;定"/>
					</li>
				</ul>
			</div>
		</form>
	</div>
	
	
</body>
</html>