// JavaScript Document

//��������
var isIe=(document.all)?true:false;

//͸���� Ĭ��Ϊ50
var endInt =50;

//�Ƿ���Ҫ�ر���ʾ Ĭ�ϲ���ʾ
var isCue = false;

//����Ĭ��͸����
var sDivi = 10;
var sDivg = 0.1;

//����Ƕdivʱ����Ҫ���������div����Ȼ�ڹر�ʱ�����ԭҳ���div��remove��;
var sDivDivTo;

//����Ƕdivʱ���ر�ʱ�Ƿ���Ҫ����ԭҳ���div�ı�־;
// #������ͨ������iframe����
// *������Ƕdiv����
var isCopyDiv = "";

var index = 0;

/**
 * ��װ�����㷽��,ֻ����Ƕiframe��
 * @param sD_width ������Ŀ��
 * @param sD_height ������ĸ߶�
 * @param sDiv_FrameSrc iframe·��
 * @param sDiv_FrameName iframe����
 * @param sDiv_titleContext ��������
 * @param endInt ���͸����
 * @param isCue �ر�ʱ�Ƿ���ʾ
 */
function showIframeDiv(sD_width,sD_height,sDiv_FrameSrc,sDiv_FrameName,sDiv_titleContext,endInt,isCue){
	showDiv(1,'',sD_width,sD_height,sDiv_titleContext,'',sDiv_FrameSrc,sDiv_FrameName,'',endInt,isCue);
}

/**
 * ��װ�����㷽��,ֻ����ʾ����
 * @PARAM sDiv_messageType ������Ϣ�������
 * @param sDiv_Message ��ʾ������
 * @param sDiv_titleContext ��������
 * @param callback �ص�������Ϊ���ܻ�ȡȷ�ϻ���ȡ����ֵ
 * @param sD_width ������Ŀ��
 * @param sD_height ������ĸ߶�
 * @param endInt ���͸����
 * @param isCue �ر�ʱ�Ƿ���ʾ
 */
function showConfirmDiv(sDiv_messageType,sDiv_Message,sDiv_titleContext,callback,sD_width,sD_height,endInt,isCue){
	if(sDiv_messageType==1){
		showDiv(0,sDiv_messageType,sD_width,sD_height,sDiv_titleContext,sDiv_Message,'','','',endInt,isCue,callback);
	}else{
		showDiv(0,sDiv_messageType,sD_width,sD_height,sDiv_titleContext,sDiv_Message,'','','',endInt,isCue);
	}
	
}

/**
 * ��װ�����㷽��,��Ƕ��ǰҳ����div
 * @param sD_width ������Ŀ��
 * @param sD_height ������ĸ߶�
 * @param sDiv_titleContext ��������
 * @param sDiv_DivId ��������
 * @param endInt ���͸����
 * @param isCue �ر�ʱ�Ƿ���ʾ
 */
function showLocalDiv(sD_width,sD_height,sDiv_titleContext,sDiv_DivId,endInt,isCue){
	showDiv(2,'',sD_width,sD_height,sDiv_titleContext,'','','',sDiv_DivId,endInt,isCue);
}

/**
 * ����div��
 * @param sDiv_Type ����������� 0Ϊ��ʾ��1Ϊ��Ƕiframe ,2Ϊ��Ƕ��ǰҳ���ڵ�ĳ��div�е����� Ĭ��Ϊ0
 * @PARAM sDiv_messageType ������Ϣ�������
 *		  0Ϊ��ͨ��ʾ
 *		  1��ʾȷ��
 *		  2��ʾ�����ɹ�
 *		  3��ʾ����ʧ��
 *        4��ʾ����
 * 		  5��ʾ�ò�չʾ��Ϣ
 * @param sD_width ������Ŀ��
 * @param sD_height ������ĸ߶�
 * @param sDiv_titleContext ��������
 * @param sDiv_Message ��ʾ������
 * @param sDiv_FrameSrc iframe·��
 * @param sDiv_FrameName iframe����
 * @param sDiv_DivId ��Ҫ��Ƕ��divId
 * @param endInt ���͸����
 * @param isCue �ر�ʱ�Ƿ���ʾ
 * @param callback �ص�������Ϊ���ܻ�ȡȷ�ϻ���ȡ����ֵ
 */
function showDiv(sDiv_Type,sDiv_messageType,sD_width,sD_height,sDiv_titleContext,sDiv_Message,sDiv_FrameSrc,sDiv_FrameName,sDiv_DivId,endInt,isCue,callback){
	
	var sDivType =  sDiv_Type =='' || sDiv_Type == null ? 0 : sDiv_Type;
	var messageType = sDiv_messageType =='' || sDiv_messageType == null ? 0 : sDiv_messageType;	
	var sDwidth = sD_width =='' || sD_width == null ? 300 : sD_width;
	var sDheight =  sD_height =='' || sD_height == null ? 150 : sD_height;
	var titleContext = sDiv_titleContext =='' || sDiv_titleContext == null ? '������' : sDiv_titleContext;
	var sDivMessage = sDiv_Message =='' || sDiv_Message == null ? '��ʾ' : sDiv_Message;
	var sDivFrameSrc = sDiv_FrameSrc;
	var sDivFrameName = sDiv_FrameName =='' || sDiv_FrameName == null ? 'sDiv_iframe' : sDiv_FrameName;
	var sDivDivId = sDiv_DivId;
	this.endInt = endInt == '' || endInt == null ? this.endInt : endInt;
	
	//����͸����
	this.sDivi=10;
	this.sDivg=0.1;

	//��ȡҳ������������ݣ��õ���ǰ�Ĳ���
	var hiddenIsDivList = document.getElementsByName("_sDivHiddenIsCopyDiv_");
	if(hiddenIsDivList.length==0){
		this.isCopyDiv = "";
	}else{
		this.isCopyDiv = hiddenIsDivList[hiddenIsDivList.length-1].value;
	}
	
	//��ȡҳ������������ݣ��ȵ�index�ĳ�ʼֵ
	var hiddenIndexList = document.getElementsByName("_sDivHiddenIndex_");
	if(hiddenIndexList.length==0){
		this.index = 1 ;
	}else{
		this.index =parseInt(hiddenIndexList[hiddenIndexList.length-1].value) + 1;
	}
	
	//��ȡ��Ļ�߶ȺͿ��
	var bWidth = parseInt(document.documentElement.scrollWidth);
	var bHeight = parseInt(document.documentElement.scrollHeight);
	
	//����������ʾdiv
	var sDiv = document.createElement("div");
	sDiv.className = "sDiv";
	sDiv.style.zIndex = 901 + index;
	sDiv.id = "sDiv" + index;
	
	//���õ���div��ʾ��λ��
	var left = (bWidth-sDwidth)/2;
	var top = (bHeight-sDheight)/2 - 130;
	
	if(top<10){
		top = 10;
	}
	if(left<10){
		left = 10;
	}
	sDiv.style.left = left + "px";
	sDiv.style.top = top + "px";
	
	//���õ���div�ĸ߶ȺͿ��
	sDiv.style.width = sDwidth + "px";
	sDiv.style.height = sDheight + "px";
	
	//��������
	var backDiv = document.createElement("div");
	backDiv.id ="backDiv" + index;
	backDiv.style.zIndex = 900 + index;
	
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
	
	var confirmBut;
	var cancelBut;
	sDivContext.style.height = cDHeight + "px";

	sDiv.appendChild(sDivContext);
	
	//�ж��Ƿ�Ϊ��ͨ��ʾ��
	if(sDivType == 1){
		this.isCopyDiv = this.isCopyDiv + "#" ;
		//����iframe
		var sDivIframe = document.createElement("iframe");
		sDivIframe.name = sDivFrameName;
		sDivIframe.style.width = cDWidth + "px";
		sDivIframe.style.height = cDHeight + "px";
		sDivIframe.style.border = "none";
		sDivIframe.src = sDivFrameSrc;
		sDivContext.appendChild(sDivIframe);
		
	}else if(sDivType == 2){
		var	sDivDiv = document.getElementById(sDivDivId);
		
		if(sDivDiv == null){
			showConfirmDiv('','',3,"û���ҵ���Ӧ��id","��������",50);
			return false;
		}
		
		this.isCopyDiv = this.isCopyDiv + "*" ;
		var sDivCopyDiv = document.createElement("div");
		sDivDiv.style.display = "block";
		sDivCopyDiv = sDivDiv;
		this.sDivDivTo = sDivDiv;
		sDivContext.appendChild(sDivCopyDiv);
	}else{
		this.isCopyDiv = this.isCopyDiv + "#" ;
		
		sDivContext.className="sDivContextMessage";
		
		//����ͼ��
		var messageImg = '<img src="images/';
		
		//�����ײ�������ťdiv��
		var sDivMessageBut = document.createElement("div");
		
		confirmBut = document.createElement("input");
		confirmBut.type="button";
		confirmBut.className="setupLineButton";
		confirmBut.value="ȷ  ��";		
		sDivMessageBut.appendChild(confirmBut);		
		cancelBut = document.createElement("input");
		cancelBut.type="button";
		cancelBut.className="setupLineButton";
		cancelBut.value="ȡ  ��";		

		var messageBut = "<input type='button' class='' onclick='sDivConfirmBut()' value=''>"
		
		//��ͬ����ʾ����ʾ��ͬ��ͼ��
		switch(messageType){
			case 0:	
				messageImg = messageImg + 'info.gif">';
			break;
			case 1:
				messageImg = messageImg + 'ask.gif">';
				//messageBut = messageBut + "&nbsp;<input type='button' class='setupLineButton' onclick='sDivCancelBut()' value='ȡ  ��'>"
				sDivMessageBut.appendChild(cancelBut);
			break;
			case 2:
				messageImg = messageImg + 'right.gif">';
			break;
			case 3:
				messageImg = messageImg + 'err.gif">';
			break;
			case 4:
				messageImg = messageImg + 'warning.gif">';
				//messageBut = messageBut + "&nbsp;<input type='button' class='setupLineButton' onclick='sDivCancelBut()' value='ȡ  ��'>"
			break;
			default:
				messageImg = messageImg + 'info.gif">';
		}
		//fixme ������͵��������
		if(messageType == 5){
			sDivContext.innerHTML = "<div class='sDivAllMessageText'>" + sDivMessage + "</div>";
		}else{
			sDivContext.innerHTML = "<div class='sDivMessageImg'>" + messageImg + "</div><div class='sDivMessageText'>" + sDivMessage + "</div>";
		}
		//sDivMessageBut.innerHTML = messageBut;
		sDivMessageBut.style.width = sDwidth + "px";
		sDivMessageBut.className = "sDivMessageBut";
		sDiv.appendChild(sDivMessageBut);	
	}
	
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

	//��index���浽�������У��Ա�ر�ʱ����رմ���Ĳ�
	var hiddenIndex = document.createElement("input");
	hiddenIndex.type = "hidden";
	hiddenIndex.value = this.index;
	hiddenIndex.name = "_sDivHiddenIndex_";
	sDiv.appendChild(hiddenIndex);
	
	//��isCopyDiv���浽�������У��Ա�ر�ʱ���������ر�ģʽΪ��Ƕdiv�Ĳ�
	var hiddenIsCopyDiv = document.createElement("input");
	hiddenIsCopyDiv.type = "hidden";
	hiddenIsCopyDiv.value = this.isCopyDiv;
	hiddenIsCopyDiv.name = "_sDivHiddenIsCopyDiv_";
	sDiv.appendChild(hiddenIsCopyDiv);
	
	//�ص�����������ȷ�ϻ���ȡ����ֵ
	if(sDivType != 1 && sDivType !=2){
		confirmBut.onclick = function(){
			if(callback) callback("yes");
			closeSDiv();
		}	
		cancelBut.onclick = function(){
			if(callback) callback("no")	;
			closeSDiv();
		}
	}
}

/*
 * ���������䰵
 * @param �䰵�ı���
 * @param ͸����
 */
function showDivBack(obj,endInt){
	obj.className="sbackDiv";
	if(isIe){
		sDivi = sDivi + 10;
		obj.style.filter = "Alpha(Opacity=" + sDivi +")";
		if(sDivi<endInt){
		setTimeout(function(){showDivBack(obj,endInt)},1);
		}
	}else{
		sDivg = sDivg + 0.1;
		obj.style.opacity = sDivg;
		if(sDivg<(endInt/100))	{
			setTimeout(function(){showDivBack(obj,endInt)},1);
		}
	}
}

/**
 * �رյ�����div��
 * @param flag �رձ�־
 *        1 ��ʾ��Ҫ�رո�ҳ��ĵ�����
 *        2 ��ʾ��Ҫ��������div����
 */
function closeSDiv(flag){
	
	var hiddenIsCopyDiv ;	
	var backDiv;
	var sDiv;
	
	//��ȡ���е������ڵ����õ�������
	if(flag==1){
		hiddenIsCopyDiv = parent.document.getElementsByName("_sDivHiddenIsCopyDiv_");
	}else{		
		hiddenIsCopyDiv = document.getElementsByName("_sDivHiddenIsCopyDiv_");
	}

	//ȡ������һ���������index
	var HiddenIndex = hiddenIsCopyDiv.length;

	//ȡ������һ�㴰�����������ݵ����һ���ַ�
	var allWindowType = hiddenIsCopyDiv[HiddenIndex-1].value;
	allWindowType = allWindowType.substring(allWindowType.length,allWindowType.length-1);

	if(flag==1){
		backDiv = parent.document.getElementById("backDiv" + HiddenIndex);
		sDiv = parent.document.getElementById("sDiv" + HiddenIndex);
	}else if(flag==2 || allWindowType == '*'){
		backDiv = document.getElementById("backDiv" + HiddenIndex);
		sDiv = document.getElementById("sDiv" + HiddenIndex);
		sDivDivTo.style.display = "none";
		document.body.appendChild(sDivDivTo);
	}else{
		backDiv = document.getElementById("backDiv" + HiddenIndex);
		sDiv = document.getElementById("sDiv" + HiddenIndex);
	}
	
	//���ָ���������
	if(sDiv!=null)
	{
		sDiv.parentNode.removeChild(sDiv);
	}
	if(backDiv!=null)
	{
		backDiv.parentNode.removeChild(backDiv);
	}
	//����͸����
	this.sDivi=10;
	this.sDivg=0.1;
	//this.index = 0;

	
}

/**
 * �ƶ���
 */
function moveSDiv(){
	addEventHandler(document.body,"mousemove",handleMouseMove);
	addEventHandler(document.body,"mouseUP",handleMouseUp);
}

function handleMouseMove(){
//var oEvent = EventUtil.getEvent();
var ex = event.clientX;
var ey = event.clientY;

var sDiv = document.getElementById("sDiv");
sDiv.style.left=ex;
sDiv.style.top=ey;
}
function handleMouseUp(){
	addEventHandler(document.body,"mousemove",handleMouseMove);
	addEventHandler(document.body,"mouseUP",handleMouseUp);
}

/**
 * ȷ����ť
 */
function sDivConfirmBut(){
	closeSDiv();
	return true;
}

/**
 * ȡ����ť
 */
function sDivCancelBut(){
	closeSDiv();
	return false;
}

function addEventHandler(oTarget,sEventType,fnHandler){
	if(oTarget.addEventListener){
		oTarget.addEventListener(sEventType,fnHandler,false);
	}else if(oTarget.attachEvent){
		oTarget.attachEvent("on" + sEventType,fnHandler);
	}else{
		oTarget["on" + sEventType] = fnHandler;
	}
}

/**function getEvent(){
	if(window.event){
	return this.fo
	}	
}

function formatEvent(){
}*/