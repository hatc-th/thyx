<%@ page language="java" pageEncoding="gbk" %>
<%@ include file="/common/taglibs.jsp" %>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE HTML>
<html>
<head>
	<title>���ú���</title>
</head>
		<link href="<%=path %>/fPlan.css" rel="stylesheet" type="text/css">
		<link href="<%=path %>/fPlanContext.css" rel="stylesheet" type="text/css">
		<link href="<%=path %>/otherPage.css" rel="stylesheet" type="text/css">
		<script type="text/javascript" src="<%=path %>/js/jquery-1.9.1.js"></script>
		<script type="text/javascript" src="<%=path %>/js/divWindow.js" ></script>
		<script type="text/javascript">

		        
	// ѡ���ȡ�����ú���Ϊ���ƻ����ߡ�
	function reSetRrType(index){
		if(document.getElementById('ids_'+index).checked == true){
			document.getElementById('rrType_'+index).disabled = "";
			document.getElementById('rrType_'+index).options.length=0;
			option=new Option("������","1")	;
			document.getElementById('rrType_'+index).add(option);  
			option=new Option("������","0")	;
			document.getElementById('rrType_'+index).add(option); 
		}else{
			document.getElementById('rrType_'+index).options.length=0;
			document.getElementById('rrType_'+index).disabled = "disabled";
		}
	}
	
	/*
	 *  �ύ���õ�����������Ϣ
	 */
		function ajaxSubmitSetSkyWayForm(){
				var checkedObj = $('#skyWayList :checkbox:checked');
				if(checkedObj.length == 0){
					alert('��ѡ���ߣ�');
					return false;
				}
				var checkobjs = $(":checkbox",$("#skyWayList"));
				temp ='';
				mainType=0;
				bakType=0;
				for(i=0 ;i<checkobjs.length;i++){
					if(checkobjs[i].checked == true){
						if(document.getElementById('rrType_'+i).value =='0') bakType++;
						if(document.getElementById('rrType_'+i).value =='1') mainType++;
						if(mainType >1){
							alert('ֻ������һ������Ϊ������!');
							return;
						}
						if(bakType >1){
							alert('ֻ������һ������Ϊ������!');
							return;
						}
						temp=temp+document.getElementById('rrType_'+i).value+':'+document.getElementById('ids_'+i).value+";";
					}
				}
				if(temp !=''){
					 var jsondata = {
					   rrIds:temp.substr(0,temp.length - 1)
		        	};
			        $.post('<c:url value="/adAction.do?op=setSkyWayByGaneral&jquery=1"/>', jsondata, getSkyData,"json");
				}
				
		}
		
			// ����ȡ�ĺ�������Ϊ�ƻ��ĺ��ߡ�
		function getSkyData(redata)
		{		
			if (redata.data.returnflag == "true") {
				$('#wiki').show();
				return  redata.data.skyWay;
			}else{
				alert('��������ʧ��!');
				return null;
			}
        }
		
		function calcelWindow(){
			window.close();
		}
		
		function goToPage(pageCur){
			$('#pageCur').val(pageCur);
			$('#generalSkyWaysDiv').load('<%=path%>/adAction.do?op=getRegularRR&' + $("#selectSkyWayform").serialize());
		}
		
		//generalSkyWaysDiv
</script>
<body>
<form id="selectSkyWayform" method="post" action="<%=path %>/adAction.do?op=getRegularRR">
         <div class="rr_globalDiv">
           <input type="hidden" id="ecmTypes" name="ecmTypes" value=""/>
			<div class="skyWayListClass">
		       	<table class="lineTable" id="skyWayList">
					<tr class="lineTableTitle">
						<td style="width: 5%;" >���</td>
						<td style="width: 5%;" >ѡ��</td>		               	
		               	<td style="width: 15%;" >��������</td>
						<td style="width: 15%;" >���߱��</td>
		               	<td style="width: 20%;" >��������</td>
		               	<td style="width: 40%;" >���߽ڵ�</td>
					</tr>
					<c:forEach var="skyWayVO" items="${infoList}" varStatus="s">
						<c:choose>
                       		<c:when test="${s.index % 2 == 0}">
                           		<c:set var="tr_class" value="oneTdClass" scope="page" />
                       		</c:when>
                       		<c:otherwise>
                           		<c:set var="tr_class" value="twoTdClass" scope="page" />
                       		</c:otherwise>
                   		</c:choose>
                   		<tr class="${ tr_class}">
                   			<td>${s.index+1 }</td>
                   			<td>
                   				<input id="ids_${s.index}" name="ids" type="checkbox" class="inputCheckbox" value="${skyWayVO.rrId}"/>
                   			</td>
                   			
                   			<td>
                   				 <select id="rrType_${s.index}" name="rrType" class="isMainLineSelect">
                   				 	<option value="1">������</option>
                   				 	<option value="0">������</option>
		             			 </select> 
                   			</td>
                   			<td>${skyWayVO.rrCode}</td>
                   			<td>${skyWayVO.rrName}</td>
                   			<td>
                   				<c:forEach var="point" items="${skyWayVO.pointList}" varStatus="spoint">
    		    					<span <c:if test="${spoint.index %2 == 0}">class="airdromeSpan"</c:if>>[${spoint.index+1}.${point.rsPCode}:${point.rsPName}]</span>
    		    				</c:forEach>
                   			</td>
                   		</tr>
               </c:forEach>
		        </table>
	    	</div>
	    	
	 	    <input type="hidden" id="adep" name="adep" value="${adep} " />
	    	<input type="hidden" id="ades" name="ades" value="${ades}" />   	
	    	<input type="hidden" id="pageCur" name="pageCur" value="${rollPage.pageCur}" />
	    	<input type="hidden" id="pageNum" name="pageNum" value="${rollPage.pageNum }" />
	    	<input type="hidden" id="pagePer" name="pagePer" value="10">   
	    	<input type="hidden" id="pageBool" name="pageBool" value="1">
	    	
			<div class="pageDiv">
				<c:choose>
					<c:when test="${rollPage.pageNum != 0}">
					��ǰ��<font color="red">${rollPage.pageCur + 1 }</font>ҳ,��<font color="red">${rollPage.pageNum }</font>ҳ
					
				<c:if test="${rollPage.pageCur == 0}">
					<input type="button"  value="��ҳ" class="setupLineButton" disabled/>
				</c:if>
				<c:if test="${rollPage.pageCur != 0 }">
					<input type="button"  value="��ҳ" class="setupLineButton" onClick="goToPage('0')"/>
				</c:if>
				<c:if test="${rollPage.pageCur == 0}">
					<input type="button"  value="��һҳ" class="setupLineButton" disabled/>
				</c:if>
				<c:if test="${rollPage.pageCur != 0}">
					<input type="button"  value="��һҳ" class="setupLineButton" onClick="goToPage('${rollPage.pageCur - 1 }')"/>
				</c:if>
				<c:if test="${rollPage.pageCur == (rollPage.pageNum-1)}">
					<input type="button"  value="��һҳ" class="setupLineButton" disabled/>
				</c:if>
				<c:if test="${rollPage.pageCur != (rollPage.pageNum - 1)}">
					<input type="button"  value="��һҳ" class="setupLineButton" onClick="goToPage('${rollPage.pageCur + 1}')"/>
				</c:if>
				<c:if test="${rollPage.pageCur == (rollPage.pageNum - 1)}">
					<input type="button"  value="βҳ" class="setupLineButton" disabled/>
				</c:if>
				<c:if test="${rollPage.pageCur != (rollPage.pageNum - 1)}">
					<input type="button"  value="βҳ" class="setupLineButton" onClick="goToPage('${rollPage.pageNum - 1}')"/>
				</c:if>
					</c:when>
					<c:otherwise>
						<font color="red">û�з���Ҫ��ļ�¼</font>
					</c:otherwise>
				</c:choose>
				
			</div>	
			
			<div class="otherPageButton">
				<input type="button"  id="saveBtn" value="ȷ ��" class="setupLineButton" onclick="ajaxSubmitSetSkyWayForm();"/>
				&nbsp;&nbsp;&nbsp;
				<input type="button" id="backBtn" value="ȡ  ��" class="setupLineButton" onClick="closeSDiv('1')"/>
				&nbsp;&nbsp;&nbsp;
			</div>	
      </div>  
	</form>
</body>
</html>