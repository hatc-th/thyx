<%@ page language="java" pageEncoding="utf-8" %>
<%@ include file="/common/taglibs.jsp" %>
	<script type="text/javascript">
	/***
	 * 2013-09-24 引用常用航线
	 */
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
			var flag = document.getElementById("flag").value;
			var checkobjs = $(":checkbox",$("#skyWayList"));
			temp ='';
			mainType=0;
			bakType=0;
			for(i=0 ;i<checkobjs.length;i++){
				if(checkobjs[i].checked == true){
					if(document.getElementById('rrType_'+i).value =='0') bakType++;
					if(document.getElementById('rrType_'+i).value =='1') mainType++;
					if(mainType >1){
						showConfirmDiv(4,'只能设置一条航线为主航线!','警告信息');
						return;
					}
					if(bakType >1){
						showConfirmDiv(4,'只能设置一条航线为备航线!','警告信息');
						return;
					}
					temp=temp+document.getElementById('rrType_'+i).value+':'+document.getElementById('ids_'+i).value+";";
				}
			}
			if(temp==''){
				closeSDiv('2');
			}else{
				 var jsondata = {
				   rrIds:temp.substr(0,temp.length - 1),
				   flag:flag
	        	};
		        $.post('<c:url value="/flyPlanAction.do?method=setSkyWayByGaneral&jquery=1"/>', jsondata, getSkyData,"json");
			}
	}
	
	// 将获取的航线设置为计划的航线。
		function getSkyData(redata)
		{
			if (redata.data.returnflag == "true") {
				tempsl=redata.data.skyWay.length;
				tempWayType='';
				for (j = 0; j < tempsl; j++) {
					
                    skyWay = redata.data.skyWay[j];
                    ltemp=skyWay.pointList.length;
                    tableObj = document.getElementById("lineTable1"); 
                    skLine='';
                     
                    if (skyWay.rrType =='1') {
                    
                    	// 判断需添加的主航线所在的层。
	        			// 主航线表单对象默认为 lineTable1
						if(document.getElementById('lineFlag').value == '0'){
							tableObj = document.getElementById("lineTable2"); // 主航线表单对象
						}
						tempLen=tableObj.rows.length;
						deleteLineRow(tempLen-1,0,tableObj); //行编号从0开始	
						
		        		for (i = 0; i < ltemp; i++) {
		        			point = skyWay.pointList[i];
		        			newTr = tableObj.insertRow(i+1);  // 行号-1 ,表示加入到第几行，从0开始
		        			
		        			skLine=skLine+getPointNumInfo(point.rsPPos)+",";    
		        			
		        			if(i%2 ==1){
					    		newTr.className="oneTdClass";
							}else{
								newTr.className="twoTdClass";
							}
							
							if(document.getElementById('lineFlag').value == '1'){   // 表示当前 lineTableDiv1 层表示的主航线
				        		if(i<ltemp-1){
				        			addLinePointRow(newTr,'1', (i+1) ,'1' ,point.rsPCode ,point.rsPName ,point.rsPPos ,point.rsPType);
				        		}else{
					        		addLinePointRow(newTr,'1', (i+1) ,'0' ,point.rsPCode ,point.rsPName ,point.rsPPos ,point.rsPType); // 添加目的机场节点
				        		}
							}else{
				        		if(i<ltemp-1){
				        			addLinePointRow(newTr,'0', (i+1) ,'1' ,point.rsPCode ,point.rsPName ,point.rsPPos ,point.rsPType);
				        		}else{
					        		addLinePointRow(newTr,'0', (i+1) ,'0' ,point.rsPCode ,point.rsPName ,point.rsPPos ,point.rsPType); // 添加目的机场节点
				        		}
							}
                    	}
                    }else{
                    
	        			// 判断需添加的备航线所在的层。
	        			// 备航线表单对象默认为 lineTable1
						if(document.getElementById('lineFlag').value == '1'){
						    tableObj = document.getElementById("lineTable2"); // 备航线表单对象
						}
						tempLen=tableObj.rows.length;
						deleteLineRow(tempLen-1,0,tableObj); //行编号从0开始	
						
		        		for (i = 0; i < ltemp; i++) {
		        		
		        			point = skyWay.pointList[i];
		        			newTr = tableObj.insertRow(i+1);  // 行号-1 ,表示加入到第几行，从0开始	
		        			
		        			skLine=skLine+getPointNumInfo(point.rsPPos)+","; 
		        			
		        			if(i%2 ==1){
					    		newTr.className="oneTdClass";
							}else{
								newTr.className="twoTdClass";
							}
							
							if(document.getElementById('lineFlag').value == '1'){   // 表示当前 lineTableDiv2 层表示的备航线
				        		if(i<ltemp-1){
				        			addLinePointRow(newTr,'0', (i+1) ,'1' ,point.rsPCode ,point.rsPName ,point.rsPPos ,point.rsPType);
				        		}else{
					        		addLinePointRow(newTr,'0', (i+1) ,'0' ,point.rsPCode ,point.rsPName ,point.rsPPos ,point.rsPType); // 添加目的机场节点
				        		}
							}else{	// 表示当前 lineTableDiv1 层表示的备航线
				        		if(i<ltemp-1){
					      			addLinePointRow(newTr,'1', (i+1) ,'1' ,point.rsPCode ,point.rsPName ,point.rsPPos ,point.rsPType);
				        		}else{
					        		addLinePointRow(newTr,'1', (i+1) ,'0' ,point.rsPCode ,point.rsPName ,point.rsPPos ,point.rsPType); // 添加目的机场节点
				        		}
							}
                    	}
                    }
                    
                    addNewLineRow(ltemp+1,7,tableObj); // 补充空白行 
                   
                    if (skyWay.rrType =='1') {
                     	document.getElementById("linePointCount1").value=ltemp; // 设置主航线节点数。
                     	if(graphic1 !=''){
				   			clearGraphic(graphic1);
				   		}
				   		if(skLine !=''){
							graphic1=executeDrawLine('['+ skLine.substr(0,skLine.length-1) +']', 'STYLE_SOLID', '#FF00FF', 3,null,skyWay.rrType);
						} 
                    }else{
                    	document.getElementById("linePointCount2").value=ltemp; // 设置备航线节点数。
                    	if(graphic2 !=''){
				   			clearGraphic(graphic2);
				   		}
				   		if(skLine !=''){
							graphic2=executeDrawLine('['+ skLine.substr(0,skLine.length-1) +']', 'STYLE_SOLID', '#0000FF', 3,null,skyWay.rrType);
						}
                    }
                    
                    // showGraphicPonit(skyWay.rrType); 
                    tempWayType = skyWay.rrType;
                }
                
                showSelectLineDiv(tempWayType);
                reDrawLineSection(tempWayType);
                $(".divShowScroll")[0].autohidemode = false;
				$(".divShowScroll")[1].autohidemode = false;
				// showConfirmDiv(2,'航线设置成功!','操作提示信息');
				closeSDiv('2'); // 关闭层
				getDateDiff();
			}else{
				showConfirmDiv(2,'航线设置失败!','操作提示信息');
			}
        }
		            
		
	</script>
	<form id="selectSkyWayform" >
		<!-- 常用航线或建议航线标示：0表示常用航线，1表示建议航线 -->
		<input type="hidden" name="flag" id="flag" value="${flag}"  />
         <div class="rr_globalDiv">
			<div class="skyWayListClass"  id="skyWayList">
		       	<table class="lineTable">
					<tr class="lineTableTitle">
						<td style="width: 5%;" >序号</td>
						<td style="width: 5%;" >选择</td>		               	
		               	<td style="width: 15%;" >航线类型</td>
		               	<td style="width: 75%;" >航线节点</td>
					</tr>
					
					<c:forEach var="skyWayVO" items="${infoList}" varStatus="s">
                   		<c:choose>
	                       <c:when test="${s.index % 2 == 1}">
	                           <c:set var="tr_class" value="oneTdClass" scope="page" />
	                       </c:when>
	                       <c:otherwise>
	                           <c:set var="tr_class" value="twoTdClass" scope="page" />
	                       </c:otherwise>
	                   	</c:choose>
                   		<tr  class="${tr_class}">
							<td><c:out value="${s.index+1}" /></td>
							<td>
								<input id="ids_${s.index}" name="ids" type="checkbox" class="inputCheckbox" value="${skyWayVO.rrId}" onClick="reSetRrType(${s.index});"/>
							</td>
							<td>
								<select id="rrType_${s.index}" name="rrType"  class="isMainLineSelect" disabled>
		 		    				<option value=""></option>
				             	</select> 
							</td>
							<td  >
								<c:forEach var="point" items="${skyWayVO.pointList}" varStatus="spoint">
									<c:choose>
				                       <c:when test="${spoint.index == 0 || spoint.index == fn:length(skyWayVO.pointList)-1 }">
				                           <span class="airdromeSpan">[${spoint.index+1}.${point.rsPCode}:${point.rsPName}]</span>
				                       </c:when>
				                       <c:otherwise>
				                           [${spoint.index+1}.${point.rsPCode}:${point.rsPName}]
				                       </c:otherwise>
				                   	</c:choose>
	    		    			</c:forEach>
							</td>
	               		</tr>
               		</c:forEach>
				<!--  补齐空行 style="text-align: left;"  -->
	    			<c:if test="${10 - fn:length(infoList) > 0}">
			    		<c:forEach begin="${fn:length(infoList)}" end="${9}" step="1" varStatus="status1">
			    			<c:choose>
		                        <c:when test="${status1.index % 2  == 1}">
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
		                    </tr>
			    		</c:forEach>
		    		</c:if>			  
			  
			  
					
					
		        </table>
	    	</div>
      </div>
			<div class="otherPageButton">
				<input type="button"  id="saveBtn" value="确 认" class="setupLineButton"  onClick="ajaxSubmitSetSkyWayForm();"/>
				&nbsp;&nbsp;&nbsp;
				<input type="button" id="backBtn" value="取  消" class="setupLineButton" onClick="closeSDiv('2')"/>
				&nbsp;&nbsp;&nbsp;
			</div>	  
	</form>
