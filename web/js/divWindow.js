// JavaScript Document

//检测浏览器
var isIe=(document.all)?true:false;

//透明度 默认为50
var endInt =50;

//是否需要关闭提示 默认不提示
var isCue = false;

//设置默认透明度
var sDivi = 10;
var sDivg = 0.1;

//当内嵌div时，需要保留引入的div，不然在关闭时，会把原页面的div给remove掉;
var sDivDivTo;

//当内嵌div时，关闭时是否需要保留原页面的div的标志;
// #代表普通弹出和iframe弹出
// *代表内嵌div弹出
var isCopyDiv = "";

var index = 0;

/**
 * 封装弹出层方法,只给内嵌iframe用
 * @param sD_width 弹出框的宽度
 * @param sD_height 弹出框的高度
 * @param sDiv_FrameSrc iframe路径
 * @param sDiv_FrameName iframe名字
 * @param sDiv_titleContext 标题内容
 * @param endInt 最大透明度
 * @param isCue 关闭时是否提示
 */
function showIframeDiv(sD_width,sD_height,sDiv_FrameSrc,sDiv_FrameName,sDiv_titleContext,endInt,isCue){
	showDiv(1,'',sD_width,sD_height,sDiv_titleContext,'',sDiv_FrameSrc,sDiv_FrameName,'',endInt,isCue);
}

/**
 * 封装弹出层方法,只给提示框用
 * @PARAM sDiv_messageType 弹出消息框的类型
 * @param sDiv_Message 提示框内容
 * @param sDiv_titleContext 标题内容
 * @param callback 回调函数，为了能获取确认或者取消的值
 * @param sD_width 弹出框的宽度
 * @param sD_height 弹出框的高度
 * @param endInt 最大透明度
 * @param isCue 关闭时是否提示
 */
function showConfirmDiv(sDiv_messageType,sDiv_Message,sDiv_titleContext,callback,sD_width,sD_height,endInt,isCue){
	if(sDiv_messageType==1){
		showDiv(0,sDiv_messageType,sD_width,sD_height,sDiv_titleContext,sDiv_Message,'','','',endInt,isCue,callback);
	}else{
		showDiv(0,sDiv_messageType,sD_width,sD_height,sDiv_titleContext,sDiv_Message,'','','',endInt,isCue);
	}
	
}

/**
 * 封装弹出层方法,内嵌当前页面内div
 * @param sD_width 弹出框的宽度
 * @param sD_height 弹出框的高度
 * @param sDiv_titleContext 标题内容
 * @param sDiv_DivId 标题内容
 * @param endInt 最大透明度
 * @param isCue 关闭时是否提示
 */
function showLocalDiv(sD_width,sD_height,sDiv_titleContext,sDiv_DivId,endInt,isCue){
	showDiv(2,'',sD_width,sD_height,sDiv_titleContext,'','','',sDiv_DivId,endInt,isCue);
}

/**
 * 弹出div层
 * @param sDiv_Type 弹出框的类型 0为提示框，1为内嵌iframe ,2为内嵌当前页面内的某个div中的内容 默认为0
 * @PARAM sDiv_messageType 弹出消息框的类型
 *		  0为普通提示
 *		  1表示确认
 *		  2表示操作成功
 *		  3表示操作失败
 *        4表示警告
 * 		  5表示用层展示信息
 * @param sD_width 弹出框的宽度
 * @param sD_height 弹出框的高度
 * @param sDiv_titleContext 标题内容
 * @param sDiv_Message 提示框内容
 * @param sDiv_FrameSrc iframe路径
 * @param sDiv_FrameName iframe名字
 * @param sDiv_DivId 需要内嵌的divId
 * @param endInt 最大透明度
 * @param isCue 关闭时是否提示
 * @param callback 回调函数，为了能获取确认或者取消的值
 */
function showDiv(sDiv_Type,sDiv_messageType,sD_width,sD_height,sDiv_titleContext,sDiv_Message,sDiv_FrameSrc,sDiv_FrameName,sDiv_DivId,endInt,isCue,callback){
	
	var sDivType =  sDiv_Type =='' || sDiv_Type == null ? 0 : sDiv_Type;
	var messageType = sDiv_messageType =='' || sDiv_messageType == null ? 0 : sDiv_messageType;	
	var sDwidth = sD_width =='' || sD_width == null ? 300 : sD_width;
	var sDheight =  sD_height =='' || sD_height == null ? 150 : sD_height;
	var titleContext = sDiv_titleContext =='' || sDiv_titleContext == null ? '弹出框' : sDiv_titleContext;
	var sDivMessage = sDiv_Message =='' || sDiv_Message == null ? '提示' : sDiv_Message;
	var sDivFrameSrc = sDiv_FrameSrc;
	var sDivFrameName = sDiv_FrameName =='' || sDiv_FrameName == null ? 'sDiv_iframe' : sDiv_FrameName;
	var sDivDivId = sDiv_DivId;
	this.endInt = endInt == '' || endInt == null ? this.endInt : endInt;
	
	//重置透明度
	this.sDivi=10;
	this.sDivg=0.1;

	//获取页面隐藏域的内容，得到当前的层数
	var hiddenIsDivList = document.getElementsByName("_sDivHiddenIsCopyDiv_");
	if(hiddenIsDivList.length==0){
		this.isCopyDiv = "";
	}else{
		this.isCopyDiv = hiddenIsDivList[hiddenIsDivList.length-1].value;
	}
	
	//获取页面隐藏域的内容，等到index的初始值
	var hiddenIndexList = document.getElementsByName("_sDivHiddenIndex_");
	if(hiddenIndexList.length==0){
		this.index = 1 ;
	}else{
		this.index =parseInt(hiddenIndexList[hiddenIndexList.length-1].value) + 1;
	}
	
	//获取屏幕高度和宽度
	var bWidth = parseInt(document.documentElement.scrollWidth);
	var bHeight = parseInt(document.documentElement.scrollHeight);
	
	//创建内容显示div
	var sDiv = document.createElement("div");
	sDiv.className = "sDiv";
	sDiv.style.zIndex = 901 + index;
	sDiv.id = "sDiv" + index;
	
	//设置弹出div显示的位置
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
	
	//设置弹出div的高度和宽度
	sDiv.style.width = sDwidth + "px";
	sDiv.style.height = sDheight + "px";
	
	//创建背景
	var backDiv = document.createElement("div");
	backDiv.id ="backDiv" + index;
	backDiv.style.zIndex = 900 + index;
	
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
	
	var confirmBut;
	var cancelBut;
	sDivContext.style.height = cDHeight + "px";

	sDiv.appendChild(sDivContext);
	
	//判断是否为普通提示框
	if(sDivType == 1){
		this.isCopyDiv = this.isCopyDiv + "#" ;
		//创建iframe
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
			showConfirmDiv('','',3,"没有找到对应的id","操作错误",50);
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
		
		//创建图标
		var messageImg = '<img src="images/';
		
		//创建底部操作按钮div层
		var sDivMessageBut = document.createElement("div");
		
		confirmBut = document.createElement("input");
		confirmBut.type="button";
		confirmBut.className="setupLineButton";
		confirmBut.value="确  定";		
		sDivMessageBut.appendChild(confirmBut);		
		cancelBut = document.createElement("input");
		cancelBut.type="button";
		cancelBut.className="setupLineButton";
		cancelBut.value="取  消";		

		var messageBut = "<input type='button' class='' onclick='sDivConfirmBut()' value=''>"
		
		//不同的提示框，显示不同的图标
		switch(messageType){
			case 0:	
				messageImg = messageImg + 'info.gif">';
			break;
			case 1:
				messageImg = messageImg + 'ask.gif">';
				//messageBut = messageBut + "&nbsp;<input type='button' class='setupLineButton' onclick='sDivCancelBut()' value='取  消'>"
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
				//messageBut = messageBut + "&nbsp;<input type='button' class='setupLineButton' onclick='sDivCancelBut()' value='取  消'>"
			break;
			default:
				messageImg = messageImg + 'info.gif">';
		}
		//fixme 这里有偷懒的嫌疑
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

	//将index保存到隐藏域中，以便关闭时不会关闭错误的层
	var hiddenIndex = document.createElement("input");
	hiddenIndex.type = "hidden";
	hiddenIndex.value = this.index;
	hiddenIndex.name = "_sDivHiddenIndex_";
	sDiv.appendChild(hiddenIndex);
	
	//将isCopyDiv保存到隐藏域中，以便关闭时，能正常关闭模式为内嵌div的层
	var hiddenIsCopyDiv = document.createElement("input");
	hiddenIsCopyDiv.type = "hidden";
	hiddenIsCopyDiv.value = this.isCopyDiv;
	hiddenIsCopyDiv.name = "_sDivHiddenIsCopyDiv_";
	sDiv.appendChild(hiddenIsCopyDiv);
	
	//回调函数，返回确认或者取消的值
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
 * 背景渐渐变暗
 * @param 变暗的背景
 * @param 透明度
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
 * 关闭弹出的div层
 * @param flag 关闭标志
 *        1 表示需要关闭父页面的弹出层
 *        2 表示需要将包含的div保留
 */
function closeSDiv(flag){
	
	var hiddenIsCopyDiv ;	
	var backDiv;
	var sDiv;
	
	//获取所有弹出窗口的内置的隐藏域
	if(flag==1){
		hiddenIsCopyDiv = parent.document.getElementsByName("_sDivHiddenIsCopyDiv_");
	}else{		
		hiddenIsCopyDiv = document.getElementsByName("_sDivHiddenIsCopyDiv_");
	}

	//取得最上一层隐藏域的index
	var HiddenIndex = hiddenIsCopyDiv.length;

	//取得最上一层窗口隐藏域内容的最后一个字符
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
	
	//清楚指定层的内容
	if(sDiv!=null)
	{
		sDiv.parentNode.removeChild(sDiv);
	}
	if(backDiv!=null)
	{
		backDiv.parentNode.removeChild(backDiv);
	}
	//重置透明度
	this.sDivi=10;
	this.sDivg=0.1;
	//this.index = 0;

	
}

/**
 * 移动层
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
 * 确定按钮
 */
function sDivConfirmBut(){
	closeSDiv();
	return true;
}

/**
 * 取消按钮
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