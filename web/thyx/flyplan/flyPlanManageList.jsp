<%@ page language="java" pageEncoding="gbk"%>
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
	
	<title>ͨ�����й����������ϵͳ</title>
    <meta http-equiv="content-type" content="text/html; charset=gb2312" />
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
    <script src="js/flyPlan.js" type="text/javascript"></script>
    <script src="js/changePageSize.js" type="text/javascript"></script>
	<script src="js/jquery-1.9.1.js" type="text/javascript"></script>
	<script src="js/divWindow.js" type="text/javascript"></script>
	<script src="js/calendar/syjDT.js" type="text/javascript"></script>
	<script src="js/validata/validata.js" type="text/javascript"></script>
	<script type='text/javascript' src='<%=path %>/ad/jqueryPlus/autocomplete/js/jquery.autocomplete.js'></script>
	
	<script type="text/javascript">
	
		$(document).ready(function(){
			//��ɻ������ֻ���ICAO_CODE autocomplete
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
			
			//Ŀ�Ļ������ֻ���ICAO_CODE autocomplete
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
		
		// ����ѡ�񴰿�
		//index ����������ɻ�����Ŀ�Ļ�����params �ؼ��ֲ�ѯ��Ӧ��Ԫ�����ƣ�
		function switchPopWin(index, params) {
			params = (params != null && params != "") ? params : "";
            //1 ��ʾ��ѯ����
			params = document.getElementById(params).value;
			showIframeDiv(320,500,'<c:url value="/adAction.do?op=getADInfo&isDep="/>' + index + '&adName=' + params,'iframe','������ѯ',60)
		} 
		
		// ����ѡ��ص����������÷���ֵ
		function setADInfo(flag,object) {
			if(flag == '1'){
				document.getElementById("flyPlanAdepName").value=object.name;
				document.getElementById("flyPlanAdep").value=object.id;
			}else {
				document.getElementById("flyPlanAdesName").value=object.name;
				document.getElementById("flyPlanAdes").value=object.id;
			}
		}
         
        // �½��Ǽ�
		function  initFlyPlan() {
			document.form1.action = '<c:url value="/flyPlanAction.do?method=initFlyPlanBaseInfo&searchType=manage&operFlag=new"/>';
			document.form1.submit();
		}
		/**
		 // �鿴���мƻ�
		function  viewFlyPlanInfo(planId) {
			// newCustWin('<c:url value="/flyPlanAction.do?method=viewFlyPlanInfo&planId="/>'+planId,'','835', '1000', '', '', 'showInCharge'+planId);
			showIframeDiv(800,600,'<c:url value="/flyPlanAction.do?method=viewFlyPlanInfo&planId="/>'+planId,'iframe','�鿴���мƻ�',60)
		}
	**/
	
	// �鿴���мƻ�
		function  viewFlyPlanInfo(planid){
			// showIframeDiv(1200,835,'flyPlanAction.do?method=initFlyPlanBaseInfo&operFlag=view&planid='+planid,'iframe','�鿴���мƻ�',60);
			document.form1.action = 'flyPlanAction.do?method=initFlyPlanBaseInfo&searchType=manage&operFlag=view&planid='+planid;
			document.form1.submit();
		}
		
		//��ѯ
		function queryFlyPlanList() {
			startDate = document.getElementById("startDate");
			startDate = document.getElementById("startDate");
			if(startDate.value != "") {
				if(!isShortDate(startDate, "-", "��ʼ����", "2")) {
					return false;
				}
			}
			endDate = document.getElementById("endDate");
			if(endDate.value != "") {
				if(!isShortDate(endDate, "-", "��������", "2")) {
					return false;
				}
			}
        	document.form1.action = '<c:url value="/flyPlanAction.do?method=searchFlyPlanList&searchType=manage"/>';
        	document.form1.submit();
		}
		
    
		//���Ʊ����ʱ��IDҲ���
		function cleanIdValue(names) {
			document.getElementById(names).value = "";
	  	}
  	
  	// ɾ���ƻ�
  	function deleteFlyPlan() {
	  	var checked = 0;
		for (i=0;i<document.form1.elements.length;i++){
		    if (document.form1.elements[i].name=="chkbox"){			    	
		    	if(document.form1.elements[i].checked == true) {
		    		checked ++;
		    		testa=document.getElementById(document.form1.elements[i].value+'_delFlag').value;
		    		if(testa !=''&& testa =='0'){
						// alert('�Բ�������ѡ��ĵ�'+checked+'�����мƻ����ύ���ϼ�������ɾ����');
						showConfirmDiv(0,'�Բ�������ѡ��ĵ�'+checked+'�����м�\n�����ύ���ϼ�������ɾ����','��ʾ��Ϣ');
						return ;
		    		}
			    		
		    	// �Ƿ��ɾ���üƻ����ж�
			    //	if(){
			    //	
			    //	}
		    		
		    	}
		    }
		}
		if(checked == 0) {
			showConfirmDiv(0,'������ѡ��һ�����мƻ���','��ʾ��Ϣ');
	  		return false;
	  	}
	  	//if(confirm('��ȷ��Ҫɾ����ѡ�ķ��мƻ���') == true) {
	  	showConfirmDiv(1,'��ȷ��Ҫɾ����ѡ�ķ��мƻ���','��ʾ��Ϣ',function(choose){
		  	if(choose=="yes"){
				document.form1.action='<c:url value="/flyPlanAction.do?method=deleteFlyPlan" />' ;
				document.form1.submit();
			}else{
				return;
			}
		});
	}
	
	
  	// �����ƻ�
  	function cancelFlyPlan() {
	  	var checked = 0;
		for (i=0;i<document.form1.elements.length;i++){
		    if (document.form1.elements[i].name=="chkbox"){			    	
		    	if(document.form1.elements[i].checked == true) {
		    		checked ++;
		    		upsta=document.getElementById(document.form1.elements[i].value+'_upFlag').value;
		    		if(upsta !=''&& upsta =='0'){
						// alert('�Բ�������ѡ��ķ��мƻ�״̬�����������ܳ�����');
						showConfirmDiv(0,'�Բ�������ѡ��ķ��мƻ�״̬�����������ܳ�����','��ʾ��Ϣ');
						return ;
		    		}
			    		
		    	// �Ƿ�ɳ����üƻ����ж�
			    //	if(){
			    //	
			    //	}
		    		
		    	}
		    }
		}
		if(checked == 0) {
			showConfirmDiv(0,'������ѡ��һ�����мƻ���','��ʾ��Ϣ');
	  		return false;
	  	}
		
		showLocalDiv(280,240,'�������мƻ�','cancelDiv',60);
	}
	
	// ȷ���ύ����
  	function confirmCancelPlan() {
  		//if(confirm('������ƻ��������ٸ��ģ���ȷ��Ҫ�����÷��мƻ���')){
  		showConfirmDiv(1,'������ƻ��������ٸ��ģ���ȷ��Ҫ�����÷��мƻ���','��ʾ��Ϣ',function(choose){
		  	if(choose=="yes"){
				document.form1.action='<c:url value="/flyPlanAction.do?method=updateFlyPlanState&operFlag=cancel"/>' ;
				document.form1.submit();
			}else{
				return;
			}
		});
  	}
	// �޸ķ��мƻ�
	function updateFlyPlan() {
	  	var checked = 0;
	  	testa='';  // ��ȡ״̬
		for (i=0;i<document.form1.elements.length;i++){
		    if (document.form1.elements[i].name=="chkbox"){			    	
		    	if(document.form1.elements[i].checked == true) {
		    	// �Ƿ���޸ĸüƻ����ж�
			    //	if(){
			    //	
			    //	}
		    		checked ++;
		    		testa=document.getElementById(document.form1.elements[i].value+'_fstate').value;
		    		upsta=document.getElementById(document.form1.elements[i].value+'_upFlag').value;
		    		if(upsta !=''&& upsta =='0'){
						//alert('�Բ�������ѡ��ķ��мƻ�״̬�������������޸ģ�');
						showConfirmDiv(0,'�Բ�������ѡ��ķ��мƻ�״̬�������������޸ģ�','��ʾ��Ϣ');
						return ;
		    		}
		    	}
		    }
		}
		if(checked == 0) {
			showConfirmDiv(0,'������ѡ��һ�����мƻ���','��ʾ��Ϣ');
	  		return false;
	  	}else if(checked > 1) {
			showConfirmDiv(0,'����ѡ��һ�����޸ķ��мƻ���','��ʾ��Ϣ');
	  		return false;
	  	}
	  	if(checked == 0) {
			showConfirmDiv(0,'������ѡ��һ�����мƻ���','��ʾ��Ϣ');
	  		return false;
	  	}
	  	var confirmValue=false;
	  	
	  	if(testa =='11'){
	  		document.form1.action='<c:url value="/flyPlanAction.do?method=initFlyPlanBaseInfo&searchType=manage&pageBool=${rollPage.pageBool}"/>' ;
			document.form1.submit();
	  	}else {
		  	showConfirmDiv(1,'�޸ĺ��������ύ��������ȷ��Ҫ�޸ĸ÷��мƻ���','��ʾ��Ϣ',function(choose){
			  	if(choose=="yes"){
			  		document.form1.action='<c:url value="/flyPlanAction.do?method=initFlyPlanBaseInfo&searchType=manage&operFlag=update&pageBool=${rollPage.pageBool}"/>' ;
					document.form1.submit();
				}else{
					return;
				}
			});
		}
	}
	
	// ���Ʒ��мƻ�
	function copyFlyPlan() {
	  	var checked = 0;
		for (i=0;i<document.form1.elements.length;i++){
		    if (document.form1.elements[i].name=="chkbox"){			    	
		    	if(document.form1.elements[i].checked == true) {
		    		checked ++;
		    	}
		    }
		}
		if(checked != 1) {
			showConfirmDiv(0,'��ѡ��һ�������Ƶķ��мƻ���','��ʾ��Ϣ');
	  		return false;
	  	}
	  	
	  	// �������Ʒ��мƻ�������Ϣ�����á�
		document.form1.action='<c:url value="/flyPlanAction.do?method=initFlyPlanBaseInfo&copy=copy&searchType=manage"/>' ;
		document.form1.submit();
	}
		
  	/*
	 * ��ղ�ѯ��������
	 * @return 
	 */
	function clearSelectCondition(){
		document.getElementById("flyPlanName").value="";
		document.getElementById("flyPlanCode").value="";
		// ��ɻ���
		document.getElementById("flyPlanAdep").value="";
		document.getElementById("flyPlanAdepName").value="";
		// Ŀ�Ļ���
		document.getElementById("flyPlanAdes").value="";
		document.getElementById("flyPlanAdesName").value="";
		
		document.getElementById("startDate").value="";
		document.getElementById("endDate").value="";
		document.getElementById("date").value="";
		document.getElementById("fplPlanClass").value="";
		document.getElementById("fplStatus").value="";
	}
  	
    </script>
	
	</head>
	<body onload="resetSize();" onresize="resetSize();">
	<div class="globalDiv">
		<div class="navText">
			���мƻ�����&nbsp;�����мƻ������б�
		</div>
		<form method="post" name="form1" id="form1">
			<div class="fplanListCheckDiv">
					<table class="inputTable fPlanListTable">
						<tr>
							<td class="leftTd">�ƻ����:</td>
							<td class="rightTd fPlanListTableTd">
	                    		<input id="flyPlanCode" name="flyPlanCode" type="text" class="selectText" value="${flyPlanCode}"/>
							</td>
							<td class="leftTd">�ƻ�����:</td>
							<td class="rightTd">
								<input id="flyPlanName" name="flyPlanName" type="text" class="selectText" value="${flyPlanName}" />
							</td>
							<td class="leftTd">��ɻ���:</td>
							<td class="rightTd">
								<input type="text" name="flyPlanAdepName" 	id="flyPlanAdepName" class="selectText" onchange="cleanIdValue('flyPlanAdep');"  value="${flyPlanAdepName}"/><input type="button" class="selectButton" value="" onclick="switchPopWin(1, 'flyPlanAdepName');" />
	           					<input type="hidden" name="flyPlanAdep" id="flyPlanAdep" value="${flyPlanAdep}" />
							</td>
							<td class="leftTd">Ŀ�Ļ���:</td>
							<td class="rightTd">
								<input type="text" name="flyPlanAdesName" 	id="flyPlanAdesName" class="selectText" onchange="cleanIdValue('flyPlanAdes');"  value="${flyPlanAdesName}"/><input type="button" class="selectButton" value="" onclick="switchPopWin(0, 'flyPlanAdesName');" />
	           					<input type="hidden" name="flyPlanAdes" id="flyPlanAdes" value="${flyPlanAdes}" />
							</td>
						</tr>
						<tr>
							<td class="leftTd">ʵʩ����:</td>
							<td class="rightTd">
							<input type="text" class="dateSelectText" name="startDate" id="startDate" value="${startDate}" onFocus="syjDate(this)"/>
							��
							<input type="text" class="dateSelectText" name="endDate" id="endDate" value="${endDate}" onFocus="syjDate(this)"/>
								<select id="date" name="qDate" onchange="changePeriod()">
	                                <option value="">
	                                     ȫ��
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
							<td class="leftTd">�ƻ����:</td>
							<td class="rightTd">
								<select name="fplPlanClass" id="fplPlanClass" class="selectText">
									<option value="">ȫ��</option>
			                    	<c:forEach var="item" items="${flyPlanKindList}">
			                    		<option value="${item.code_id}" 
			                    		<c:if test="${fplPlanClass eq item.code_id}">selected="selected" </c:if> >
			                    		${item.name}</option>
			                    	</c:forEach>
		                        </select>
							</td>
							<td class="leftTd">�ƻ�״̬:</td>
							<td class="rightTd">
								<select name="fplStatus" id="fplStatus" class="selectText">
									<option value="">ȫ��</option>
			                    	<c:forEach var="fStatus" items="${fplStatusList}">
			                    		<c:choose>
	                                        <c:when test="${fStatus.code_id == '40' || fStatus.code_id == '41' || fStatus.code_id == '42'}">
	                                        </c:when>
	                                        <c:otherwise>
	                                         	<option value="${fStatus.code_id }" <c:if test="${fplStatus eq fStatus.code_id}">selected="selected"</c:if> >${fStatus.name }</option>
	                                        </c:otherwise>
	                                    </c:choose>
			                    	</c:forEach>
			                    	<option value="4" <c:if test="${fplStatus eq '4'}">selected="selected"</c:if> >����ֹ</option>
		                        </select>
							</td>
							<td class="leftTd" colspan="2">
								<input type="button" value="�� ѯ" onclick="javascript:queryFlyPlanList();" class="setupLineButton" />&nbsp;&nbsp;<input name="button" type="button" class="setupLineButton" onclick="clearSelectCondition();" value="��&nbsp;��"/>
							</td>
						</tr>
					</table>
			</div>
			<div class="fplanListDivLine"></div>
			<div class="fPlanListDiv" id="">
				<table class="lineTable fPlanListTable" id="listTable">
                	<tr class="lineTableTitle">
						<td style="width: 3%;">
							<input id="checkall" type="checkbox" onclick="checkedAll(this.form,'checkall','chkbox');" />
						</td>
		               	<td style="width: 12%;">�ƻ����</td>
		               	<td style="width: 15%;">�ƻ�����</td> 
		               	<td style="width: 10%;">Ԥ�����ʱ��</td>
		               	<td style="width: 8%;">����ʱ��</td>
		               	<td style="width: 12%;">��ɻ���</td>
		               	<td style="width: 12%;">Ŀ�Ļ���</td>
						<td style="width: 12%;">�ƻ����</td>
						<td style="width: 8%;">�ƻ�״̬</td>
						<td style="width: 8%;">������</td>
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
		    		    		<input name="chkbox" type="checkbox" class="inputCheckbox" value="${flyPlan[0]}" />
								<input id="${flyPlan[0]}_fstate" type="hidden" value="${flyPlan[12]}" /> 
								<input id="${flyPlan[0]}_delFlag" type="hidden" value="${flyPlan[17]}" /> 
								<input id="${flyPlan[0]}_upFlag" type="hidden" value="${flyPlan[18]}" /> 
		    		    	</td>
		    		    	<td>
		    		    		<a href="#" onclick="viewFlyPlanInfo('<c:out value="${flyPlan[0]}" />');">
		    		    			<c:out value="${flyPlan[1]}" />
		    		    		</a> 
		    		    	</td>
		    		    	<td>
		    		    		<span title="<c:out value="${flyPlan[2]}" />">
		    		    			<string-truncation:truncation value="${flyPlan[2]}" length="26"/>
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
		    		<!--  �������  -->
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
	                    </tr>
		    		</c:forEach>
		    		</c:if>
				</table>
				<!-- ����ҳ���ҳ�������� -->	
				<div class="pageDiv">
					<c:import url="/common/rollpage.jsp" />
			    </div>
			    <div class="fplanListCheckButton">
					<input name="Bnfp001" class="setupLineButton" type="button" value="�½��ƻ�" onclick="initFlyPlan();"/>
			        <input name="Bnfp002" class="setupLineButton" type="button" value="�޸ļƻ�" onclick="updateFlyPlan();"/>
					<input name="Bnfp003" class="setupLineButton" type="button" value="ɾ���ƻ�" onclick="deleteFlyPlan();"/>
               		<input name="Bnfp006" class="setupLineButton" type="button" value="�����ƻ�" onclick="cancelFlyPlan();"/>
               		<input name="Bnfp007" class="setupLineButton" type="button" value="���Ƽƻ�" onclick="copyFlyPlan();"/>
				</div>
			</div>
			<!-- �����ı�ע��-->
 			<div id="cancelDiv" style="display:none; ">
				<table style="align:center;">
	            	<tr>
						<td style="height: 5px;" colspan="3"></td>
					</tr>
	    		    <tr >
	    		    	<td style="height: 25px;width: 10px;text-align: left;"></td>
	                    <td style="width: 30px;">
	                    	˵��:
	                    </td>
	                    <td>
							<textarea id="remark" name="remark" style="height:150px; width: 200px;"> </textarea>
						</td>
	    		  	</tr>
	    		  	<tr>
						<td style="height: 5px;" colspan="3"></td>
					</tr>
					<tr>
						<td style="text-align: center;" colspan="3">
							&nbsp;&nbsp;&nbsp;<input type="button" id="confirmBtn" value="ȷ  ��" class="setupLineButton" onclick="confirmCancelPlan()"/>&nbsp;&nbsp;&nbsp;<input type="button" value="ȡ  ��" class="setupLineButton" onclick="closeSDiv()"/>
			           </td>
					</tr>
				</table>
			</div>
	     </form>
	</div>
	</body>
</html>
