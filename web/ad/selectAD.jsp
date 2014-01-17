<%@ page language="java" pageEncoding="gbk"%>
<%@ include file="../common/taglibs.jsp"%>
<%
	String path = request.getContextPath();
%>

<!DOCTYPE HTML>
<html>
	<head>
		<title>选择机场</title>
		<link href="<%=path %>/css/thyx/fPlan.css" rel="stylesheet" type="text/css">
		<link href="<%=path %>/css/thyx/fPlanContext.css" rel="stylesheet" type="text/css">
		<link href="<%=path %>/css/common/otherPage.css" rel="stylesheet" type="text/css">
		<style>
			.bgcolr{
				color:red;
			}	
		</style>
		<script type="text/javascript" src="<%=path %>/js/jquery-1.9.1.js"></script>
		<script type="text/javascript" src="<%=path %>/js/divWindow.js" ></script>
	<script type="text/javascript">
		$(function(){
				$('div[level="3"]').click(function(){
						$(this).toggleClass('bgcolr');
						$('div[level="3"]').not(this).removeClass("bgcolr");
					
					});
					
					var adName = $('#adname').val();
					if($.trim(adName).length > 0){
						searchLeaf(adName);
					}
			});
		function showChildren(obj){
				var parentDiv = $(obj).parent();
				var parentId= parentDiv.attr('id');
				var closed = parentDiv.attr('closed');
				var selector = '#' + parentId + ' > div';
				if(closed == 'y'){
						$(obj).find('img').attr('src','images/nolines_minus.gif');
						parentDiv.attr('closed','n');
						$(selector).show();
					}else{
								$(obj).find('img').attr('src','images/nolines_plus.gif');
								parentDiv.attr('closed','y');
								$(selector).hide();
						}
			}
			
			function expandAll(){
					$('#adName').val('');
					var firstLevelDiv = $('div[level="1"],div[level="2"]');
					var secondLevelDiv = $('div[level="2"]');
					var selector = '';
					firstLevelDiv.each(function(index,entry){
									var id = entry.id;
									$(entry).attr('closed','n');
									$(entry).show();
									$('img',this).attr('src','images/nolines_minus.gif');
									selector = '#' + id + ' > div';
									$(selector).show();
									$(selector).each(function(index,entry){
											$(entry).attr('closed','n');
											$('div:first',this).show();
										});
							
						});
						$('div[level="3"]').show();
				}
				
			function closeAll(){
					$('#adName').val('');
					var firstLevelDiv = $('div[level="1"]');
					var secondLevelDiv = $('div[level="2"]');
					var selector = '';
					firstLevelDiv.each(function(index,entry){
									var id = entry.id;
									$(entry).attr('closed','y');
									$(entry).show();
									$('img',this).attr('src','images/nolines_plus.gif');
									selector = '#' + id + ' > div';
									$(selector).hide();
									$(selector).each(function(index,entry){
											$(entry).attr('closed','y');
											$('div:first',this).hide();
										});
							
						});
						$('div[level="3"]').show();
				}
				
			function searchLeaf(adName){
								var firstLevelArray  = new Array();
								var secondLevelArray = new Array();
								var selector = 'div[level="3"]:contains(' + adName + ')'; 
								var adDiv = $(selector);
								var firstLevelDiv = null;
								var secondLevelDiv = null;
								if(adDiv.length > 0){
										adDiv.each(function(index,entry){
											  firstLevelDiv = $(entry).parent().parent().parent().get(0);
											  secondLevelDiv = $(entry).parent().parent().get(0);
											  if($.inArray(firstLevelDiv,firstLevelArray) == -1){
											  		firstLevelArray.push(firstLevelDiv);
											  	}
											  if($.inArray(secondLevelDiv,secondLevelArray) == -1){
											  		secondLevelArray.push(secondLevelDiv);
											  	}
											});
											expandAll();
											$('div[level="1"]').hide();
											$(firstLevelArray).each(function(index,entry){
												$(entry).show();
												$('#' + $(entry).attr('id') + ' > ' + 'div').each(function(index,entry){
														$(entry).hide();
												});
													
													$.each(secondLevelArray,function(index,entry){
																$(entry).find('div div[level="3"]').hide();
																adDiv.each(function(index,entry){
																		$(entry).show();
																	});
																$(entry).show();		
														});
													
												});
									}else{
												//alert('没有查询到机场！');
												$('div[level="1"]').show();
												closeAll();
										}
				}
				
				function confirmAd(){
					var div = $('div[level="3"].bgcolr');
					var isDep = $('#isDep').val();
					if(div.length > 0){
						var obj = {id:div.attr('id'),name:div.attr('name'),
								code:div.attr('code'),pos:div.attr('pos'),
								type:div.attr('type')
							};
						window.parent.setADInfo(isDep,obj);
						closeSDiv('1');
					}else{
						return null;
					}
				}
	</script>
	</head>

	<body>
		<div class="otherPageButton">
				<input type="button" value="+" class="setupLineButton" onclick="expandAll()"/>
				<input type="button" value="-" class="setupLineButton" onclick="closeAll()"/><br><br>
		</div>
		<div class="ad_body">
		<div class="ad_globalDiv">
			<c:forEach var="cca" items="${ccaList}" varStatus="vs">
				<div class="ad_firstLayer" id="${cca.id }" closed="y" level="1">
					<a href="#" onClick="showChildren(this)"><img src="<%=path %>/images/nolines_plus.gif"></a>${cca.name }
					<c:forEach var="fca" items="${cca.childrenNodeList}">
						<div class="ad_marginHidden" id="${fca.id }" closed="y" level="2">
							<a href="#" onClick="showChildren(this)"><img src="<%=path %>/images/nolines_plus.gif"></a>${fca.name }
								<div class="ad_marginHidden">
									<c:forEach var="ad" items="${fca.childrenNodeList}">
										<div id="${ad.id }" level="3" name="${ad.name }" code="${ad.code }" pos="${ad.pos }" type="${ad.type }">${ad.name }</div>
									</c:forEach>
								</div>
						</div>
					</c:forEach>
				</div>
			</c:forEach>
		</div>		
	</div>
	
	<input type="hidden" id="isDep"  value="${isDep}" title="dep:1,des:0" />
	<input type="hidden" id="adname"  value="${adName}" />
	<div class="otherPageButton">
		<input type="button" class="setupLineButton" value="确  定"  onclick="confirmAd()"/>&nbsp;&nbsp;&nbsp;&nbsp;<input type="button" class="setupLineButton" value="取  消" onClick="closeSDiv('1')" />
	</div>
	</body>
</html>
