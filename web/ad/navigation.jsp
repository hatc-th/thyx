<%@ page language="java" import="java.util.*" pageEncoding="gbk"%>
<%@ include file="/common/taglibs.jsp" %>
<%
	String path = request.getContextPath();
%>

<!DOCTYPE HTML>
<html>
	<head>
		<title>����ҳ��</title>
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
		
		//��Ҫ����[�������ͣ��������ͣ�ICAO��]
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
		var sDivMessage = sDiv_Message =='' || sDiv_Message == null ? '��ʾ' : sDiv_Message;
		var sDivFrameSrc = sDiv_FrameSrc;
		var sDivFrameName = sDiv_FrameName =='' || sDiv_FrameName == null ? 'sDiv_iframe' : sDiv_FrameName;
		var sDivDivId = sDiv_DivId;
		this.endInt = endInt == '' || endInt == null ? this.endInt : endInt;
		
		//����͸����
		this.sDivi=10;
		this.sDivg=0.1;
		
		//��ȡ��Ļ�߶ȺͿ��
		var bWidth = parseInt(document.documentElement.scrollWidth);
		var bHeight = parseInt(document.documentElement.scrollHeight);
		
		//����������ʾdiv
		var sDiv = document.createElement("div");
		sDiv.className = "sDiv";
		sDiv.id="sDiv";
		
		//���õ���div��ʾ��λ��
		var left = (bWidth-sDwidth)/2;
		var top = (bHeight-sDheight)/2 - 130;
		left = 3;
		top = 3;
		sDiv.style.left = left + "px";
		sDiv.style.top = top + "px";
		sDiv.style.position = "absolute";
		
		//���õ���div�ĸ߶ȺͿ��
		sDiv.style.width = sDwidth + "px";
		sDiv.style.height = sDheight + "px";
		
		//��������
		var backDiv = document.createElement("div");
		backDiv.id="backDiv";
		
		//���õ���div���titile
		var sDivTitle = document.createElement("div");
		sDivTitle.className="sDivTitle";
		sDiv.appendChild(sDivTitle);
		//sDiv.onmousedown = moveSDiv;
	
	    //��������
		var sDivTitleContext = document.createElement("div");
		sDivTitleContext.className="sDivTitleContext";
		sDivTitleContext.innerHTML = titleContext;
		sDivTitle.appendChild(sDivTitleContext);
	
		//��ʽ����
		var sDivContext = document.createElement("div");
		var cDWidth = sDwidth;
		var cDHeight = sDheight - 25;
		
		sDivContext.style.width = cDWidth + "px";
		
		if(sDivType != 1){
			//��Ҫ�����±߰�ť��λ��
			cDHeight = (sDheight - 25 - 40 );
		}
	
		sDivContext.style.height = cDHeight + "px";
		sDiv.appendChild(sDivContext);
		
			//����iframe
			var sDivIframe = document.createElement("iframe");
			sDivIframe.name = sDivFrameName;
			sDivIframe.style.width = cDWidth + "px";
			sDivIframe.style.height = cDHeight + "px";
			sDivIframe.style.border = "none";
			sDivIframe.src = sDivFrameSrc;
			sDivContext.appendChild(sDivIframe);
			
		
			//�رհ�ť
			var sDivCloseBtn = document.createElement("div");
			sDivCloseBtn.className="sDivCloseBtn";
			sDivCloseBtn.onclick=closeSDiv;
			sDivTitle.appendChild(sDivCloseBtn);
		
			//����Ŀ���뵽body��
			document.body.appendChild(backDiv);
			document.body.appendChild(sDiv);
			showDivBack(backDiv,endInt);
			sDiv.focus();
	}
	
	//��ʾ��ͻ��Ϣ
	function showConflict(){
		var depDate = $('#dates').val();
		var depTime = $('#setd').val();
		var desDate = $('#setal').val();
		var desTime = $('#seta').val();
		var depICAO = $('#adepCode').val();
		var desICAO = $('#adesCode').val();
		var planeType = $('#acft').val();
		
		if(depICAO == ''){
			alert("��ѡ����ɻ�����");
			return false;		
		}
		if(desICAO == ''){
			alert("��ѡ��Ŀ�Ļ�����");
			return false;		
		}
		if(depDate == '' || depTime == ''){
			alert("��ѡ�����ʱ�䣡");
			return false;		
		}
		if(desDate == '' || desTime == ''){
			alert("��ѡ����ʱ�䣡");
			return false;		
		}
		if(planeType == ''){
			alert("��ѡ�����������!��");
			return false;		
		}
		
		var rowIndex = 1;
		var row = null;
		var conflict = false;
		var areaSpaceConflict = null;//�����ͻ
		var fraiseConflict = null;//�ϰ����ͻ
		
		if(areaSpaceConflict){
			conflict = true;
			row = $('#flyConflictTbl tr').eq(rowIndex);
			//���˫������
			row.find('td').eq(0).html(rowIndex);
			row.find('td').eq(1).html('��ȫ����澯');
			row.find('td').eq(2).html('�����ϰ������');
			row.dblclick(function(){
				highLightPoint();
			});
			createRow++;
		}
		
		if(fraiseConflict){
			conflict = true;
			row = $('#flyConflictTbl tr').eq(rowIndex);
			//���˫������
			row.find('td').eq(0).html(rowIndex);
			row.find('td').eq(1).html('�������');
			row.find('td').eq(2).html('���߾���Σ������������������');
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
						highLightPoint(depPOS);//���ݻ������ƻ����Ǵ��붨λ
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
			alert('û�в�ѯ����ͻ��Ϣ��');
		}
	}
	
	//��ͼ�ϼ�����ʾ
	function highLightPoint(adNameOrICAO){
		
	}
	</script>
	<body>
		<div style="margin-left:100px;margin-top:60px;">
				<input type="button" value="����ͼ(PDF)" class="setupLineButton" onclick="showAirPicture()" /><br><br>
				<input type="button" value="����ͼ(GIF)" class="setupLineButton" onclick="showAirPicture2()" /><br><br>
				<input type="button" value="��ͻ��ѯ" class="setupLineButton" onclick="showConflict()" /><br><br>
		</div>
		
		<div style="margin-left:60px;">
			·��˵����<br><br>
			<table border="1">
				<thead>
					<tr >
					<th style="width:120px;">��������</th>
					<th style="width:320px;">URL</th>
					<th style="width:220px;">����˵��</th>
					<th style="width:420px;">�������ݸ�ʽ</th>
				</tr>
				</thead>

				<tr class="oneTdClass">
					<td>���ú���</td>
					<td>/adAction.do?op=getRegularRR&adep=123&ades=456</td>
					<td>adep:��ɻ���objectid(����)��ades��Ŀ�Ļ���objectid(����)</td>
					<td>���߼��亽�߽ڵ��json���ݸ�ʽ�����2�������ߣ������ߺͱ����ߣ�ÿ�����������ɽڵ�</td>
				</tr>
				<tr class="twoTdClass">
					<td>����̨</td>
					<td>/adAction.do?op=getNVOrFFXList&qName=����</td>
					<td>qName:��ѡ</td>
					<td>����̨���飬eg��[{nvName:'',nvCode:'',nvType:'',nvPos:''},{{nvName:'',nvCode:'',nvType:'',nvPos:''}}...]</td>
				</tr>
				<tr class="oneTdClass">
					<td>����ѡ��</td>
					<td>/adAction.do?op=getADInfo&adName=����</td>
					<td>adName����ѡ</td>
					<td>{id:'',name:''}</td>
				</tr>
				<tr class="twoTdClass">
					<td>��ʾ����ͼ</td>
					<td>/adAction.do?op=getADAttachment&attachmentType=DS&ICAO=ZUCK&objectType=AD</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
				</tr>
				<tr class="oneTdClass">
					<td>��ͻ����</td>
					<td>/flyConflictAction.do?op=getFlyConflictInfo&depICAO=XXX&desICAO=YYY&depTime=aaa&desTime=bbb&planeType=ccc</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
				</tr>
			</table>
		</div>
	</body>
</html>
