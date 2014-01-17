<%@ page language="java" import="java.util.*" pageEncoding="gbk"%>
<%@ include file="/common/taglibs.jsp" %>
<%
	String path = request.getContextPath();
%>

<!DOCTYPE HTML>
<html>
	<head>
		<title>导航页面</title>
	</head>
	<meta http-equiv="content-type" content="text/html; charset=gbk" />
    <meta http-equiv="content-language" content="zh" />
    <meta http-equiv="content-script-type" content="text/javascript" />
	<link href="<%=path%>/css/thyx/fPlan.css" rel="stylesheet" type="text/css">
	<link href="<%=path%>/css/thyx/fPlanContext.css" rel="stylesheet" type="text/css">
	<link rel="stylesheet" type="text/css" href="<%=path %>/ad/jqueryPlus/autocomplete/jquery.autocomplete.css"/>
	<script src="<%=path%>/js/jquery-1.9.1.js"></script>
	<script type='text/javascript' src='<%=path %>/ad/jqueryPlus/autocomplete/js/jquery.autocomplete.js'></script>
	<script type='text/javascript' src='<%=path %>/js/divWindow.js'></script>
	<script type="text/javascript">
		$.ajaxSetup({ contentType: "application/x-www-form-urlencoded; charset=UTF-8"}); 
		
		//需要传递[机场类型，附件类型，ICAO码]
		function showAirPicture(adType,attachType,ICAO){
			var width = parseInt(document.documentElement.clientWidth);
			var height = parseInt(document.documentElement.clientHeight) - 20;
			var params = "objectType=" + adType + "&attachmentType=" + attachType + "&ICAO=" + ICAO;
			showDivPDF(1,1,width,height,"","","<%=request.getContextPath()%>/adAction.do?op=getADAttachment&" + params,"frameNaME","sDiv",0.5,false)
		}
		
	function showDivPDF(sDiv_Type,sDiv_messageType,sD_width,sD_height,sDiv_titleContext,sDiv_Message,sDiv_FrameSrc,sDiv_FrameName,sDiv_DivId,endInt,isCue){
		var sDivType =  sDiv_Type =='' || sDiv_Type == null ? 0 : sDiv_Type;
		var messageType = sDiv_messageType =='' || sDiv_messageType == null ? 0 : sDiv_messageType;	
		var sDwidth = sD_width =='' || sD_width == null ? 300 : sD_width;
		var sDheight =  sD_height =='' || sD_height == null ? 150 : sD_height;
		var titleContext = sDiv_titleContext =='' || sDiv_titleContext == null ? '' : sDiv_titleContext;
		var sDivMessage = sDiv_Message =='' || sDiv_Message == null ? '提示' : sDiv_Message;
		var sDivFrameSrc = sDiv_FrameSrc;
		var sDivFrameName = sDiv_FrameName =='' || sDiv_FrameName == null ? 'sDiv_iframe' : sDiv_FrameName;
		var sDivDivId = sDiv_DivId;
		this.endInt = endInt == '' || endInt == null ? this.endInt : endInt;
		
		//重置透明度
		this.sDivi=10;
		this.sDivg=0.1;
		
		//获取屏幕高度和宽度
		var bWidth = parseInt(document.documentElement.scrollWidth);
		var bHeight = parseInt(document.documentElement.scrollHeight);
		
		//创建内容显示div
		var sDiv = document.createElement("div");
		sDiv.className = "sDiv";
		sDiv.id="sDiv";
		
		//设置弹出div显示的位置
		var left = (bWidth-sDwidth)/2;
		var top = (bHeight-sDheight)/2 - 130;
		left = 3;
		top = 3;
		sDiv.style.left = left + "px";
		sDiv.style.top = top + "px";
		sDiv.style.position = "absolute";
		
		//设置弹出div的高度和宽度
		sDiv.style.width = sDwidth + "px";
		sDiv.style.height = sDheight + "px";
		
		//创建背景
		var backDiv = document.createElement("div");
		backDiv.id="backDiv";
		
		//设置弹出div层的titile
		var sDivTitle = document.createElement("div");
		sDivTitle.className="sDivTitle";
		sDiv.appendChild(sDivTitle);
		//sDiv.onmousedown = moveSDiv;
	
	    //标题内容
		var sDivTitleContext = document.createElement("div");
		sDivTitleContext.className="sDivTitleContext";
		sDivTitleContext.innerHTML = titleContext;
		sDivTitle.appendChild(sDivTitleContext);
	
		//正式内容
		var sDivContext = document.createElement("div");
		var cDWidth = sDwidth;
		var cDHeight = sDheight - 25;
		
		sDivContext.style.width = cDWidth + "px";
		
		if(sDivType != 1){
			//需要留出下边按钮的位置
			cDHeight = (sDheight - 25 - 40 );
		}
	
		sDivContext.style.height = cDHeight + "px";
		sDiv.appendChild(sDivContext);
		
			//创建iframe
			var sDivIframe = document.createElement("iframe");
			sDivIframe.name = sDivFrameName;
			sDivIframe.style.width = cDWidth + "px";
			sDivIframe.style.height = cDHeight + "px";
			sDivIframe.style.border = "none";
			sDivIframe.src = sDivFrameSrc;
			sDivContext.appendChild(sDivIframe);
			
		
			//关闭按钮
			var sDivCloseBtn = document.createElement("div");
			sDivCloseBtn.className="sDivCloseBtn";
			sDivCloseBtn.onclick=closeSDiv;
			sDivTitle.appendChild(sDivCloseBtn);
		
			//将项目放入到body中
			document.body.appendChild(backDiv);
			document.body.appendChild(sDiv);
			showDivBack(backDiv,endInt);
			sDiv.focus();
	}
	
	//显示冲突信息
	function showConflict(){
		var depDate = $('#dates').val();
		var depTime = $('#setd').val();
		var desDate = $('#setal').val();
		var desTime = $('#seta').val();
		var depICAO = $('#adepCode').val();
		var desICAO = $('#adesCode').val();
		var planeType = $('#acft').val();
		
		if(depICAO == ''){
			alert("请选择起飞机场！");
			return false;		
		}
		if(desICAO == ''){
			alert("请选择目的机场！");
			return false;		
		}
		if(depDate == '' || depTime == ''){
			alert("请选择起飞时间！");
			return false;		
		}
		if(desDate == '' || desTime == ''){
			alert("请选择降落时间！");
			return false;		
		}
		if(planeType == ''){
			alert("请选择飞行器类型!！");
			return false;		
		}
		
		var rowIndex = 1;
		var row = null;
		var conflict = false;
		var areaSpaceConflict = null;//空域冲突
		var fraiseConflict = null;//障碍物冲突
		
		if(areaSpaceConflict){
			conflict = true;
			row = $('#flyConflictTbl tr').eq(rowIndex);
			//添加双击函数
			row.find('td').eq(0).html(rowIndex);
			row.find('td').eq(1).html('安全距离告警');
			row.find('td').eq(2).html('地面障碍物过高');
			row.dblclick(function(){
				highLightPoint();
			});
			createRow++;
		}
		
		if(fraiseConflict){
			conflict = true;
			row = $('#flyConflictTbl tr').eq(rowIndex);
			//添加双击函数
			row.find('td').eq(0).html(rowIndex);
			row.find('td').eq(1).html('空域管制');
			row.find('td').eq(2).html('航线经过危险区、禁区、限制区');
			row.dblclick(function(){
				highLightPoint();
			});
			createRow++;
		}
		
		$.ajax({
			url:'<%=path%>/flyConflictAction.do?op=getFlyConflictInfo',
			data:{
				depICAO:depICAO,
				desICAO:desICAO,
				depTime:depDate + ' ' + depTime,
				desTime:desDate + ' ' + desTeime,
				planeType:planeType
			},
			type:'post',
			dataType:'json',
			success:function(data){
				var ICAO = "";
				var depPOS = "";
				var desPOS = "";
				$.each(data,function(index,entry){
					conflict = true;
					row = $('#flyConflictTbl tr').eq(rowIndex);
					row.dblclick(function(){
						ICAO = entry.other;
						switch(ICAO){
						case "depICAO":
						highLightPoint(depPOS);//根据机场名称或者是代码定位
						break;
						
						case "desICAO":
						highLight(desPOS);
						break;
						
						case "BOTH":
						highLight(depPOS);
						highLight(desPOS);
						break;
					}				
					});

					row.find('td').eq(0).html(rowIndex);
					row.find('td').eq(1).html(entry.type);
					row.find('td').eq(2).html(entry.description);
					createRow++;
				});
			}
		});
		if(!conflict){
			alert('没有查询到冲突信息！');
		}
	}
	
	//地图上加亮显示
	function highLightPoint(adNameOrICAO){
		
	}
	</script>
	<body>
		<div style="margin-left:100px;margin-top:60px;">
				<input type="button" value="机场图(PDF)" class="setupLineButton" onclick="showAirPicture()" /><br><br>
				<input type="button" value="机场图(GIF)" class="setupLineButton" onclick="showAirPicture2()" /><br><br>
				<input type="button" value="冲突查询" class="setupLineButton" onclick="showConflict()" /><br><br>
		</div>
		
		<div style="margin-left:60px;">
			路径说明：<br><br>
			<table border="1">
				<thead>
					<tr >
					<th style="width:120px;">功能名称</th>
					<th style="width:320px;">URL</th>
					<th style="width:220px;">参数说明</th>
					<th style="width:420px;">返回数据格式</th>
				</tr>
				</thead>

				<tr class="oneTdClass">
					<td>常用航线</td>
					<td>/adAction.do?op=getRegularRR&adep=123&ades=456</td>
					<td>adep:起飞机场objectid(必填)；ades：目的机场objectid(必填)</td>
					<td>航线及其航线节点的json数据格式，最多2两条航线，主航线和备航线，每条航线有若干节点</td>
				</tr>
				<tr class="twoTdClass">
					<td>导航台</td>
					<td>/adAction.do?op=getNVOrFFXList&qName=北京</td>
					<td>qName:可选</td>
					<td>导航台数组，eg：[{nvName:'',nvCode:'',nvType:'',nvPos:''},{{nvName:'',nvCode:'',nvType:'',nvPos:''}}...]</td>
				</tr>
				<tr class="oneTdClass">
					<td>机场选择</td>
					<td>/adAction.do?op=getADInfo&adName=北京</td>
					<td>adName：可选</td>
					<td>{id:'',name:''}</td>
				</tr>
				<tr class="twoTdClass">
					<td>显示机场图</td>
					<td>/adAction.do?op=getADAttachment&attachmentType=DS&ICAO=ZUCK&objectType=AD</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
				</tr>
				<tr class="oneTdClass">
					<td>冲突分析</td>
					<td>/flyConflictAction.do?op=getFlyConflictInfo&depICAO=XXX&desICAO=YYY&depTime=aaa&desTime=bbb&planeType=ccc</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
				</tr>
			</table>
		</div>
	</body>
</html>
