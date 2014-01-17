<%@ page language="java" pageEncoding="gbk" %>
<%@ include file="/common/taglibs.jsp" %>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE HTML>
<html>
<head>
	<title>常用航线</title>
</head>
		<link href="<%=path %>/fPlan.css" rel="stylesheet" type="text/css">
		<link href="<%=path %>/fPlanContext.css" rel="stylesheet" type="text/css">
		<link href="<%=path %>/otherPage.css" rel="stylesheet" type="text/css">
		<script type="text/javascript" src="<%=path %>/js/jquery-1.9.1.js"></script>
		<script type="text/javascript" src="<%=path %>/js/divWindow.js" ></script>
		<script type="text/javascript">

		        
	// 选择或取消设置航线为本计划航线。
	function reSetRrType(index){
		if(document.getElementById('ids_'+index).checked == true){
			document.getElementById('rrType_'+index).disabled = "";
			document.getElementById('rrType_'+index).options.length=0;
			option=new Option("主航线","1")	;
			document.getElementById('rrType_'+index).add(option);  
			option=new Option("备航线","0")	;
			document.getElementById('rrType_'+index).add(option); 
		}else{
			document.getElementById('rrType_'+index).options.length=0;
			document.getElementById('rrType_'+index).disabled = "disabled";
		}
	}
	
	/*
	 *  提交设置的主备航线信息
	 */
		function ajaxSubmitSetSkyWayForm(){
				var checkedObj = $('#skyWayList :checkbox:checked');
				if(checkedObj.length == 0){
					alert('请选择航线！');
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
							alert('只能设置一条航线为主航线!');
							return;
						}
						if(bakType >1){
							alert('只能设置一条航线为备航线!');
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
		
			// 将获取的航线设置为计划的航线。
		function getSkyData(redata)
		{		
			if (redata.data.returnflag == "true") {
				$('#wiki').show();
				return  redata.data.skyWay;
			}else{
				alert('航线设置失败!');
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
						<td style="width: 5%;" >序号</td>
						<td style="width: 5%;" >选择</td>		               	
		               	<td style="width: 15%;" >航线类型</td>
						<td style="width: 15%;" >航线编号</td>
		               	<td style="width: 20%;" >航线名称</td>
		               	<td style="width: 40%;" >航线节点</td>
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
                   				 	<option value="1">主航线</option>
                   				 	<option value="0">备航线</option>
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
					当前第<font color="red">${rollPage.pageCur + 1 }</font>页,共<font color="red">${rollPage.pageNum }</font>页
					
				<c:if test="${rollPage.pageCur == 0}">
					<input type="button"  value="首页" class="setupLineButton" disabled/>
				</c:if>
				<c:if test="${rollPage.pageCur != 0 }">
					<input type="button"  value="首页" class="setupLineButton" onClick="goToPage('0')"/>
				</c:if>
				<c:if test="${rollPage.pageCur == 0}">
					<input type="button"  value="上一页" class="setupLineButton" disabled/>
				</c:if>
				<c:if test="${rollPage.pageCur != 0}">
					<input type="button"  value="上一页" class="setupLineButton" onClick="goToPage('${rollPage.pageCur - 1 }')"/>
				</c:if>
				<c:if test="${rollPage.pageCur == (rollPage.pageNum-1)}">
					<input type="button"  value="下一页" class="setupLineButton" disabled/>
				</c:if>
				<c:if test="${rollPage.pageCur != (rollPage.pageNum - 1)}">
					<input type="button"  value="下一页" class="setupLineButton" onClick="goToPage('${rollPage.pageCur + 1}')"/>
				</c:if>
				<c:if test="${rollPage.pageCur == (rollPage.pageNum - 1)}">
					<input type="button"  value="尾页" class="setupLineButton" disabled/>
				</c:if>
				<c:if test="${rollPage.pageCur != (rollPage.pageNum - 1)}">
					<input type="button"  value="尾页" class="setupLineButton" onClick="goToPage('${rollPage.pageNum - 1}')"/>
				</c:if>
					</c:when>
					<c:otherwise>
						<font color="red">没有符合要求的记录</font>
					</c:otherwise>
				</c:choose>
				
			</div>	
			
			<div class="otherPageButton">
				<input type="button"  id="saveBtn" value="确 认" class="setupLineButton" onclick="ajaxSubmitSetSkyWayForm();"/>
				&nbsp;&nbsp;&nbsp;
				<input type="button" id="backBtn" value="取  消" class="setupLineButton" onClick="closeSDiv('1')"/>
				&nbsp;&nbsp;&nbsp;
			</div>	
      </div>  
	</form>
</body>
</html>