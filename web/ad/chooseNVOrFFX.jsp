<%@ page language="java" pageEncoding="utf-8" %>
<%@ include file="/common/taglibs.jsp" %>
<%
	String path = request.getContextPath();
%>
<script type="text/javascript">
		
	$(function(){
		getNewNVJSONData();
		
		$("#qName").result(function(event, data, formatted) {
				queryNVOrFFX();
		});
	
	});	
	
	
	function getNewNVJSONData(){
	
		function formatNVItem(row) {
			return " <strong>" + row[1] + "</strong>";
		}
	
		function formatNVResult(row) {
			return row[1].replace(/(<.+?>)/gi, '');
		}
	
		type = '';
		typeRadio=$(":radio",$("#nv_checkDiv"));
		for( i=0 ; i < typeRadio.length ; i++){
			if(typeRadio[i].checked){
				type =typeRadio[i].value ;
			}
		}
		//导航台autocomplete
		$("#qName").autocomplete('<%=path%>/adAction.ax?op=getNVJSONData', {
			minChars: 2,
			max:100,
			extraParams:{timestamp: +new Date(),type:type},
			width: 142,
			formatItem: formatNVItem,
			formatResult: formatNVResult
		});
		
	}
	
		
	function calcelWindow(){
		window.close();
	}
	
	function queryNVOrFFX(){
		goToPage('0');
		/**
				$.ajax({
			url:'<%=path%>/adAction.do?op=getNVOrFFXList',
			type:'post',
			dataType:'html',
			data:{
				qName:$('#qName').val(),type:$(':radio:checked').val(),pageCur:$('#pageCur').val(),
				pagePer:$('#pagePer').val()
			},
			success:function(data){
				$('#jQueryCommonDiv').html(data);
			}
		});
		*/

	}
	
	//分页查询
	function goToPage(pageCur){
		$('#pageCur').val(pageCur);
		$('#jQueryCommonDiv').load('<%=path%>/adAction.ax?op=getNVOrFFXList&' + $("#nvForm").serialize());
	}

	
	//选择导航台
	function chooseNV(){
		var checkedNV = $('#data_tbl :checkbox:checked');
		if(checkedNV.length == 0){
			closeSDiv('2');
			return ;
		}
		
		var arr = new Array();
		checkedNV.each(function(index,entry){
			var td = $(this).parent('td');
			obj = {nvName:td.attr('nvName'),nvCode:td.attr('nvCode'),nvPos:td.attr('nvPos'),nvType:td.attr('nvType')};
			arr.push(obj);
		});
		
		//此处供父窗口调用	
		window.setNVInfo(arr);
		closeSDiv('2');
		
	}
	
	//选择导航台
	function addOneNV(trIndex){
		var arr = new Array();
		addNV = $(":checkbox",$("#data_tbl"));
		addNV.each(function(index,entry){
			if(index == trIndex){
				var td = $(this).parent('td');
				obj = {nvName:td.attr('nvName'),nvCode:td.attr('nvCode'),nvPos:td.attr('nvPos'),nvType:td.attr('nvType')};
				arr.push(obj);
			}
		});
		//此处供父窗口调用	
		window.setNVInfo(arr);
		
	}
	
	
</script>
<div class="nv_globalDiv">
	<form id="nvForm" method="post" action="<%=path%>/adAction.ax?op=getNVOrFFXList">
		<div class="nv_checkDiv" id="nv_checkDiv">
			<input type="radio" name="type" value="nv" <c:if test="${type == null || type == 'nv'}">checked</c:if>  onClick="getNewNVJSONData();" />导航台&nbsp;
			<input type="radio" name="type" value="ffx" <c:if test="${type == 'ffx'}">checked</c:if>   onClick="getNewNVJSONData();"  />地标点&nbsp;&nbsp;&nbsp;&nbsp;
			名称：<input type="text" name="qName" id="qName" class="selectText" value="${qName }"/> 
			<input type="button" value="查询" class="setupLineButton" onclick="queryNVOrFFX()"/> 
		</div>
		
			<input type="hidden" id="pageCur" name="pageCur" value="${rollPage.pageCur}" />
	    	<input type="hidden" id="pageNum" name="pageNum" value="${rollPage.pageNum }" />
	    	<input type="hidden" id="pagePer" name="pagePer" value="10">   
	    	<input type="hidden" id="pageBool" name="pageBool" value="1">
	</form>
		<div class="divLine"></div>
		<div class="skyWayListClass" id="skyWayList">
			<table id="data_tbl" class="lineTable">
				<tr class="lineTableTitle">
					<td style="width: 5%;">序号</td>
					<td style="width: 5%;">选择</td>
					<td style="width: 25%;">名称</td>
					<td style="width: 25%;">代码</td>
					<td style="width: 20%;">标高</td>
					<td style="width: 15%;">状态</td>
					<td style="width: 5%;">添加</td>
				</tr>
				<c:forEach  var="item" items="${resultList}" varStatus="s">
					<c:choose>
						<c:when test="${s.index % 2 == 0}">
							<c:set var="tr_class" value="oneTdClass" scope="page" />
						</c:when>
						<c:otherwise>
							<c:set var="tr_class" value="twoTdClass" scope="page" />
						</c:otherwise>
					</c:choose>
					<tr class="${ tr_class}">
						<td>${s.index + 1 }</td>
						<td  nvName="${item[3]}" nvCode="${item[2]}" nvPos="${item[4]}" nvType="${item[5]}">
							<input name="nv" type="checkbox" value="${item[0]}" />
						</td>
						<td>${item[3]}</td>
						<td>${item[2]}</td>
						<td>${item[6]}</td>
						<td>${item[7]}</td>
						<td><img src="<%=path %>/images/addTo.png" title="将航线点添加到航线" onClick="addOneNV(${s.index});"></td>
					</tr>
				</c:forEach>
				<!--  补齐空行  -->
	    		<c:if test="${10 - fn:length(resultList) > 0}">
		    		<c:forEach begin="${fn:length(resultList)}" end="${9}" step="1" varStatus="status1">
		    			<c:choose>
	                        <c:when test="${(status1.index) % 2 == 0}">
	                            <c:set var="tr_class" value="oneTdClass" scope="page" />
	                        </c:when>
	                        <c:otherwise>
	                            <c:set var="tr_class" value="twoTdClass" scope="page" />
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
	                    </tr>
		    		</c:forEach>
	    		</c:if>
			</table>
			
			<div class="pageDiv">
					<c:choose>
						<c:when test="${rollPage.pageNum != 0}">
						
							<c:if test="${rollPage.pageCur == 0}">
								<!-- <input type="button"  value="首页" class="setupLineButton" disabled='disabled'/> -->
								<img title="首页" src="<c:url value="/images/rollpage/img_rollpage_firstpage.gif" />" />
							</c:if>
							<c:if test="${rollPage.pageCur != 0 }">
								<!-- <input type="button"  value="首页" class="setupLineButton" onClick="goToPage('0')"/> -->
								<a href="#" onClick="goToPage('0');"><img title="首页" src="<c:url value="/images/rollpage/img_rollpage_previouspage.gif" />" /></a>
							</c:if>
							<c:if test="${rollPage.pageCur == 0}">
								<!-- <input type="button"  value="上一页" class="setupLineButton" disabled='disabled'/> -->
								<img title="上一页" src="<c:url value="/images/rollpage/img_rollpage_previouspage.gif" />" />
							</c:if>
							<c:if test="${rollPage.pageCur != 0}">
								<!-- <input type="button"  value="上一页" class="setupLineButton" onClick="goToPage('${rollPage.pageCur - 1 }')"/> -->
								<a href="#" onClick="goToPage('${rollPage.pageCur - 1 }');"><img title="上一页" src="<c:url value="/images/rollpage/img_rollpage_previouspage.gif" />" /></a>
							</c:if>
							<c:if test="${rollPage.pageCur == (rollPage.pageNum-1)}">
								<!-- <input type="button"  value="下一页" class="setupLineButton" disabled='disabled'/> -->
								<img title="下一页" src="<c:url value="/images/rollpage/img_rollpage_nextpage.gif" />" />
							</c:if>
							<c:if test="${rollPage.pageCur != (rollPage.pageNum - 1)}">
								<!-- <input type="button"  value="下一页" class="setupLineButton" onClick="goToPage('${rollPage.pageCur + 1}')"/> -->
								<a href="#" onClick="goToPage('${rollPage.pageCur + 1}');" ><img title="下一页" src="<c:url value="/images/rollpage/img_rollpage_nextpage.gif" />" /></a>
							</c:if>
							<c:if test="${rollPage.pageCur == (rollPage.pageNum - 1)}">
								<!-- <input type="button"  value="尾页" class="setupLineButton" disabled='disabled'/>> -->
								<img title="尾页" src="<c:url value="/images/rollpage/img_rollpage_lastpage.gif" />" />
							</c:if>
							<c:if test="${rollPage.pageCur != (rollPage.pageNum - 1)}">
								<!-- <input type="button"  value="尾页" class="setupLineButton" onClick="goToPage('${rollPage.pageNum - 1}')"/> -->
								<a href="#" onClick="goToPage('${rollPage.pageNum - 1}');"><img title="尾页" src="<c:url value="/images/rollpage/img_rollpage_lastpage.gif" />" /></a>
							</c:if>
							
							当前第<font color="red">${rollPage.pageCur + 1 }</font>页,共<font color="red">${rollPage.pageNum }</font>页
						</c:when>
					<c:otherwise>
						<font color="red">没有符合要求的记录</font>
					</c:otherwise>
				</c:choose>
				
			</div>		
			<div class="otherPageButton">
				<input type="button" id="saveBtn" value="确  认" class="setupLineButton" onclick="chooseNV();"/>&nbsp;&nbsp;&nbsp;
				<input type="button" id="backBtn" value="关  闭" class="setupLineButton" onClick="closeSDiv('2')"/>
			</div>
		</div>
	</div>
