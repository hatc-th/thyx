<%@ page language="java" pageEncoding="gbk"%>
<%@ include file="/common/taglibs.jsp" %>
<%@ include file="/common/includeCss.jsp"%>
<c:import url="/common/popSelect.jsp" />
<!DOCTYPE html
      PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
      "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="zh" lang="zh">
<head>
	
<title>�ƻ�ʵʩ����</title>
<meta http-equiv="content-language" content="zh" />
<meta http-equiv="content-script-type" content="text/javascript" />
<link rel="stylesheet" href= "<c:url value="css/thyx/flyPlan.css" />" type="text/css"></link>
	<script src="<c:url value="/js/widget_calendar.js" />"></script>
<script type="text/javascript">

/*
 * ����Ƿ���ѡ��ѡ�������ѡ�����ȡѡ�е���Ϣ
 * @return ѡ���е���Ϣ
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
		// ����ѡ�񴰿�
		//index �������ֵ���ҳ��winName �����������ƣ�params �ؼ��ֲ�ѯ��Ӧ��Ԫ�����ƣ�
		function switchPopWin(index, winName, params) {
			params = (params != null && params != "") ? params : "";
            switch (index) {
                //1 ��ʾ��ѯ����
				case 1:
				params = document.getElementById(params).value;
				newCustWin('<c:url value="/dicTreeAction.do?method=popAirP&option=radio&code=yes&typeFlag=AD&sqlKeywords=" />'+params,winName, 350, 470, '', '', 'ynnnn');
				break;
	        }
		} 

/*
 * ��ѡ�����ڱ���������ʱ�����������Ϣ����
 * @return 
 */
function changeP(){
	var radioList = document.getElementsByName("isMainP");
	for(i=0;i<radioList.length;i++){
		if(true == radioList[i].checked ){
			if(radioList[i].value == '1'){
				//��ռƻ��еı�����
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
				//��ʾ�ƻ��еı�����
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
 * �л��������ߣ�������ʾ��ͬ�ĳ�����
 * @parma ��������
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
 * ����div���ڣ����ҽ���Ϣ���͸�div�����е�Ԫ��
 * @parma ��־λ���ñ�־Ϊ���ֵ�ǰ���ڴ��������
 * @return 
 */
function openDivCheck(flag){
	
	//�����û��ѡ��ƻ�
	var flyplanList = isSelectd();
	var width = (screen.availWidth - 345 - 216)/2;      // ��Ļ���ÿ��
   	var height = (screen.availHeight - 150 - 345)/2;    // ��Ļ���ø߶�
	//��Ϊʵʩǰȷ��ʱ
	if("" != flyplanList){
		var textArray = flyplanList.split('@_@');
		var planList = textArray[0];
		var index = textArray[1];
		if("1" == flag){
			//����ƻ�id
			document.getElementById("flyplanId1").value=planList;
			//������Ա
			var commander = document.getElementById("commander" + index).value;
			textArray = commander.split('@_@');
			
			var list = document.getElementById("tpilot" + flag);
			for(i = 0;i<list.length;i++){
				if(list[i].value == textArray[1]){
					list[i].selected = true;
				}
			}
			//�����������
			list = document.getElementsByName("floatAcid" + flag);
			var acid = document.getElementById("acid" + index).value
			for(i = 0;i<list.length;i++){
				if(list[0].value == acid){
					list[0].selected = true;
				}
			}
			
			//������
			var planRs = document.getElementById("planRs" + index).value
			document.getElementById("AllRs" + flag).value = planRs;
			textArray = planRs.split('@_@');
			//ֻ��һ������ʱ�����ñ�����
			if(textArray.length<2){
				document.getElementById("NoMainRs" + flag).disabled="disabled";
			}
			//���ú��߳�����
			document.getElementById("floatPlanRs" + flag).innerHTML="<a href='#'>" + textArray[0] + "</a>";
			
			//���ô�����ʾ TODO,ȱ�ٸ�������ͼ��ķ���
			document.getElementById("floatDiv" + flag).style.display = "block";
			document.getElementById("floatDiv" + flag).style.height = "270px";
			document.getElementById("floatDiv" + flag).style.left = width + "px";
			document.getElementById("floatDiv" + flag).style.top = height + "px";
		}else if("2" == flag){
			
			//����ƻ�id
			document.getElementById("flyplanId2").value=planList;
			//���������ע
			var adJust = document.getElementById("adJust" + index).value;
			document.getElementById("submitAdjust").value = adJust;
			
			//�����������ڵĸ߶�
			document.getElementById("floatDiv" + flag).style.height = "200px";
			document.getElementById("floatDiv" + flag).style.left = width + "px";
			document.getElementById("floatDiv" + flag).style.top = height + "px";
			document.getElementById("floatDiv" + flag).style.display = "block";
		}else if("3" == flag){
			//����ƻ�id
			document.getElementById("flyplanId3").value=planList;
			//������Ա
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
			
			//������
			var planRs = document.getElementById("planRs" + index).value
			document.getElementById("AllRs" + flag).value = planRs;
			textArray = planRs.split('@_@');
			
			//ֻ��һ������ʱ�����ñ�����
			if(textArray.length<2){
				document.getElementById("NoMainRs" + flag).disabled="disabled";
			}
			//���ú��߳�����
			document.getElementById("floatPlanRs" + flag).innerHTML="<a href='#'>" + textArray[0] + "</a>";
			
			var altn = document.getElementById("Altn1" + index).value
			document.getElementById("Altn1").value = altn;
			
			//�����������ڵĸ߶�
			document.getElementById("floatDiv" + flag).style.height = "460px";
			var width = (screen.availWidth - 345 - 216)/2;      // ��Ļ���ÿ��
   			var height = (screen.availHeight - 500 - 345)/2;    // ��Ļ���ø߶�
			document.getElementById("floatDiv" + flag).style.display = "block";
			document.getElementById("floatDiv" + flag).style.left = width + "px";
			document.getElementById("floatDiv" + flag).style.top = height + "px";
		}
	}else{
		alert("û��ѡ����Ҫȷ�ϵļƻ���");
	}
}
/*
 * �رյ�����div����
 * @return 
 */
function closeAffirmDiv(flag){
	//�������div���е�ֵ
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
 * �Ϸ��Լ�鷽��
 * @param ��־λ���ñ�־Ϊ���ֵ�ǰ���ڴ��������
 */
function checkSubmitCentext(flag){
	
	if(flag=="3"){
	
		//���ʵ���������
		var checkValue = document.getElementById("pStartDate").value; 
		if(checkValue == "" || checkValue == null ){
			alert("ʵ�ʿ��ɵ����ڲ���Ϊ�գ�");
			return;
		}
		//���ʵ�����Сʱ
		checkValue = document.getElementById("startHour").value; 
		if(checkValue == "" || checkValue == null ){
			alert("ʵ�ʿ��ɵ�Сʱ����Ϊ�գ�");
			return;
		}
		
		//���ʵ�����Сʱ
		checkValue = document.getElementById("startMinute").value; 
		if(checkValue == "" || checkValue == null ){
			alert("ʵ�ʿ��ɵķ��Ӳ���Ϊ�գ�");
			return;
		}
		//���ʵ�ʽ�������
		checkValue = document.getElementById("pEndDate").value; 
		if(checkValue == "" || checkValue == null ){
			alert("ʵ�ʽ�������ڲ���Ϊ�գ�");
			return;
		}
		//���ʵ�ʽ���Сʱ
		checkValue = document.getElementById("endHour").value; 
		if(checkValue == "" || checkValue == null ){
			alert("ʵ�ʽ����Сʱ����Ϊ�գ�");
			return;
		}
		
		//���ʵ�ʽ������
		checkValue = document.getElementById("endMinute").value; 
		if(checkValue == "" || checkValue == null ){
			alert("ʵ�ʽ���ķ��Ӳ���Ϊ�գ�");
			return;
		}
		
		//������ݺϷ���
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
					alert("ʵ�ʿ��ɵ�Сʱ�����Ϲ淶");
					return;
				}
				if(endHour >-1 && endHour<24){
					
				}else{
					alert("ʵ�ʽ����Сʱ�����Ϲ淶");
					return;
				}
				if(startMinute<=endMinute){
					if(startMinute >-1 && startMinute<60){
					}else{
						alert("ʵ�ʿ��ɵķ��Ӳ����Ϲ淶");
						return;
					}
					if(endMinute >-1 && endMinute<60){
						
					}else{
						alert("ʵ�ʽ���ķ��Ӳ����Ϲ淶");
						return;
					}
				}else{
					alert("ʵ�ʿ��ɵķ��Ӳ��ܴ���ʵ�ʽ���ķ���");
					return;
				}
			}else{
				alert("ʵ�ʿ��ɵ�Сʱ���ܴ���ʵ�ʽ����Сʱ");
				return;
			}
		}else{
			alert("ʵ�ʿ��ɵ����ڲ��ܴ���ʵ�ʽ��������");
			return;
		}
		
		
		document.flyPlanDiv3.submit();
	}
}

/*
 * �ύ������ TODO �ѹ�ʱ
 * @parma ��־λ���ñ�־Ϊ���ֵ�ǰ���ڴ��������
 * @return 
 */
function submitPlan(flag){
	document.flyPlanDiv1.action="" + flag;
	document.flyPlanDiv1.submit();
}
/*
 * ��ղ�ѯ��������
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
 * ��ת����ϸ��Ϣҳ��
 * @param �ƻ�Ψһ��ʶ
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
				<td class="selectDivRightTd">�ƻ����ƣ�</td>
				<td class="selectDivLeftTd"><input class="inputTextTh" type="text" name="planName" id="planName" value="${planName}"/></td>
				<td class="selectDivRightTd">�ƻ���ţ�</td>
				<td class="selectDivLeftTd"><input class="inputTextTh" type="text" name="planCode" id="planCode" value="${planCode }"/></td>
				<td class="selectDivRightTd">�ƻ���ʼ���ڣ�</td>
				<td class="selectDivLeftTd"><input class="inputTextTh" style="width:110px;" type="text" id="flyStartDate" name="flyStartDate" value="${flyStartDate}"/><input 
		                                        type="button" class="inputButtonDate" value="..." style="border:1px #ccc solid;height:22px;background:#FFFFFF;color:#ccc" onclick="return showCalendarObj(this.previousSibling, '%Y-%m-%d');" /></td>
				<td width="92" class="selectDivRightTd">�ƻ��������ڣ�</td>
				<td width="139" class="selectDivLeftTd"><input class="inputTextTh" style="width:110px;" type="text" id="flyEndDate" name="flyEndDate" value="${flyEndDate}"/><input 
		                                        type="button" class="inputButtonDate" value="..." style="border:1px #ccc solid;height:22px;background:#FFFFFF;color:#ccc" onclick="return showCalendarObj(this.previousSibling, '%Y-%m-%d');" /></td>
				
			  </tr>
			  <tr  class="textRight">
				<td class="selectDivRightTd">���������ͣ�</td>
				<td class="selectDivLeftTd"><input class="inputTextTh" type="text" name="airCraftType" id="airCraftType" value="${airCraftType }"/></td>
				<td class="selectDivRightTd">��ɻ�����</td>
				<td class="selectDivLeftTd"><input class="inputTextTh" style="width:110px;" type="text" name="adepName" id="adepName" value="${adep }"/><input type="button" class="inputButtonDate" value="..." style="border:1px #ccc solid;height:22px;background:#FFFFFF;color:#ccc" onclick="switchPopWin(1, 'adep_adepName', 'adepName');" />
					<input class="inputTextTh" type="hidden" name="adep" id="adep" value=""/>
				</td>
				<td class="selectDivRightTd">Ŀ�Ļ�����</td>
				<td class="selectDivLeftTd"><input class="inputTextTh" style="width:110px;" type="text" name="adesName" id="adesName" value="${ades }"/><input type="button" class="inputButtonDate" value="..." style="border:1px #ccc solid;height:22px;background:#FFFFFF;color:#ccc" onclick="switchPopWin(1, 'ades_adesName', 'adesName');" />
					<input class="inputTextTh" type="hidden" name="ades" id="ades" value=""/></td>
				<td>&nbsp;</td>
				<td class="selectDivRightTd"><input name="button" type="submit" value="��&nbsp;ѯ" class="inputButtonCtrl"/>&nbsp;&nbsp;<input name="button" type="button" class="inputButtonCtrl" onclick="clearSelectContext()"; value="��&nbsp;��"/></td>
			  </tr>
			</table>
		</div>
		<div class="divTable">
			<table class="tableBorder">
			  <tr class="tdbg">
			    <td width="1%" height="25">&nbsp;</td>
			    <td width="5%">���</td>
			    <td width="14%">���мƻ�����</td>
			    <td width="10%">���мƻ����</td>
			    <td width="10%">���й���</td>
			    <td width="10%">�ƻ����ʱ��</td>
			    <td width="10%">����ʱ�������ӣ�</td>
			    <td width="10%">��ɻ���</td>
			    <td width="10%">Ŀ�Ļ���</td>
			    <td width="10%">����������</td>
			    <td width="10%">״̬</td>
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
						<input type="button" onclick="openDivCheck('1')" name="" value="ȷ&nbsp;��" class="inputButtonCtrl"/>
					</c:when>
					<c:when test="${selectFlag eq 2}">
						<input type="button" onclick="openDivCheck('2')" name="" value="��&nbsp;��" class="inputButtonCtrl"/>
					</c:when>
					<c:when test="${selectFlag eq 3}">
						<input type="button" onclick="openDivCheck('3')" name="" value="ȷ&nbsp;��" class="inputButtonCtrl"/>
					</c:when>
				</c:choose>
			</div>
		 </div> 
		 <div id="divbot" style="width:99.5%">
			<c:import url="/common/rollpage.jsp"/>
		</div>
	</form>
</div>
<!-- =======================ʵʩǰȷ�ϵ�������=========================== -->
<div id="floatDiv1" class="floatDiv">
	<div id="floatDivTitle" >
		<div id="titleContext">ȷ�ϼƻ�</div>
		<div id="floatDivClose" onclick="closeAffirmDiv('1')">X</div>
	</div>
	<div id="arrirmPlan">
		<form name="flyPlanDiv1" action="<c:url value="/ActFlyPlanAction.do?method=affirmBeforeAct"/>&selectFlag=1" method="post">
			<input id="flyplanId1" name="flyplanId1" type="hidden" value=""/>
			<div id="ContextName" class="ContextName">
				<ul>
					<li>����Ա��</li>
					<li>��������ţ�</li>
					<li>�������ߣ�</li>
					<li>���ߣ�</li>
					<li id="remarkLi">��ע��</li>
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
						������&nbsp;&nbsp;&nbsp;<input id="NoMainRs1" type="radio" name="isMainRs" value="1" onclick="changeRs('0','1')"/>������
					</li>
					<li>
						<span id="floatPlanRs1"></span>
						<input type="hidden" id="AllRs1" name="AllRs" value=""/>
					</li>
					<li id="remarkTextareaLi"><textarea name="remark" id="remark" class="remarkTextarea"></textarea></li>
					<li>
						<input type="submit" class="button" value=" ȷ&nbsp;��"/>
					</li>
				</ul>
			</div>
		</form>
	</div>
</div>
<!-- =======================ʵʩ������������=========================== -->
<div id="floatDiv2" class="floatDiv">
	<div id="floatDivTitle" >
		<div id="titleContext">�����ƻ�</div>
		<div id="floatDivClose" onclick="closeAffirmDiv('2')">X</div>
	</div>
	<div id="arrirmPlan">
		<form name="flyPlanDiv2" action="<c:url value="/ActFlyPlanAction.do?method=affirmBeforeAct"/>&selectFlag=2" method="post">
			<input id="flyplanId2" name="flyplanId2" type="hidden" value=""/>
				<div class="adContextDiv">
				<div class="adContextName">��ע��</div>
				<div class="adContextInput"><textarea name="submitAdjust" id="submitAdjust" class="adjustRemark"></textarea></div>
				</div>
				<div class="adSubmitDiv"><input type="submit" class="button" value=" ȷ&nbsp;��"/></div>
		</form>
	</div>
</div>
<!-- =======================ʵʩȷ�ϵ�������=========================== -->
<div id="floatDiv3" class="floatDiv">
	<div id="floatDivTitle" >
		<div id="titleContext">ȷ�ϼƻ�</div>
		<div id="floatDivClose" onclick="closeAffirmDiv('3')">X</div>
	</div>
	<div id="arrirmPlan">
		<form name="flyPlanDiv3" action="<c:url value="/ActFlyPlanAction.do?method=affirmBeforeAct"/>&selectFlag=3" method="post">
			<input id="flyplanId3" name="flyplanId3" type="hidden" value=""/>
			<div id="ContextName" class="ContextName">
				<ul>
				    <li>����Ա��</li>
					<li>��������ţ�</li>
					<li>�������ߣ�</li>
					<li>���ߣ�</li>
					<li>ʵ�����ʱ�̣�</li>
					<li>ʵ�ʽ���ʱ�̣�</li>
					<li>�Ƿ񱸽������䣺</li>
					<li>��������</li>
					<li id="remarkLi">����ԭ��</li>
					<li id="remarkLi">��    ע��</li>
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
						������&nbsp;&nbsp;&nbsp;<input id="NoMainRs3" type="radio" name="isMainRs" value="1" onclick="changeRs('0','3')"/>������
					</li>
					<li>
						<span id="floatPlanRs3"></span>
						<input type="hidden" id="AllRs3" name="AllRs" value=""/>
					</li>
					<li>
						<input class="inputTextTh" style="width:70px;" type="text" id="pStartDate" name="pStartDate"/><input 
                                        type="button" class="inputButtonDate" style="border:1px #ccc solid;height:22px;background:#FFFFFF;color:#ccc"  value="..." onclick="return showCalendarObj(this.previousSibling, '%Y-%m-%d');" />
                        &nbsp;<input type="text" id="startHour" name="startHour" class="hmInput"></input>��<input type="text" id="startMinute" name="startMinute" class="hmInput"></input>
					</li>
					<li>
						<input class="inputTextTh" style="width:70px;" type="text" id="pEndDate" name="pEndDate"/><input 
                                        type="button" class="inputButtonDate" style="border:1px #ccc solid;height:22px;background:#FFFFFF;color:#ccc"  value="..." onclick="return showCalendarObj(this.previousSibling, '%Y-%m-%d');" />
                        &nbsp;<input type="text" id="endHour" name="endHour" class="hmInput"></input>��<input type="text" id="endMinute" name="endMinute" class="hmInput"></input>
					</li>
					<li><input name="isMainP" type="radio" value="0" onclick="changeP()"/>
						��&nbsp;&nbsp;&nbsp;<input type="radio" name="isMainP" checked="checked" onclick="changeP()" value="1"/>��
						<input type="hidden" id="Altn1" name="Altn1"/>
					</li>
					<li>
						<input type="text" class="readOnlyStyle floatDivInput" style="width:155px;height:18px;" id="floatAltn1" name="floatAltn1" disabled="disabled"/><input type="button" id="showAltn1Model" class="inputButtonDate" disabled="disabled" value="..." style="border:1px #ccc solid;height:20px;background:#DFDFDF;" onclick="switchPopWin(1, 'subAltn1_floatAltn1', 'floatAltn1');" />
						<input type="hidden" id="subAltn1" name="subAltn1" value=""/>
					</li>
					<li id="remarkTextareaLi"><textarea name="reason" id="reason" class="readOnlyStyle remarkTextarea" disabled="disabled"></textarea></li>
					<li id="remarkTextareaLi"><textarea name="desc" id="desc" class="remarkTextarea"></textarea></li>
					<li>
						<input type="button" onclick="checkSubmitCentext('3')" class="button" value="ȷ&nbsp;��"/>
					</li>
				</ul>
			</div>
		</form>
	</div>
	
	
</body>
</html>