////////////////////////////////////////////	Dates	//////////////////////////////////////////
////////////////////////////////////////////	Dates	//////////////////////////////////////////
var bMoveable=false;
var _VersionInfo="";
var strFrame;

document.writeln('<iframe id=meizzDateLayer Author=wayx frameborder=0 style="position: absolute; width: 144px; height: 200px; z-index: 9998; display: none"></iframe>');
strFrame='<style>';

//  底部操作按钮
strFrame+='INPUT.button{BACKGROUND-COLOR: #99AFBC;';
strFrame+='border:1px #ccc solid;font-family:宋体;color:#3E3E3E;font-size:12px;}';
strFrame+='TD{FONT-SIZE: 9pt;font-family:宋体;}';
strFrame+='</style>';

strFrame+='<scr' + 'ipt>';
strFrame+='var datelayerx,datelayery;';
strFrame+='var bDrag;';
strFrame+='function document.onmousemove()';
strFrame+='{if(bDrag && window.event.button==1)';
strFrame+='{var DateLayer=parent.document.all.meizzDateLayer.style;';
strFrame+='DateLayer.posLeft += window.event.clientX-datelayerx;';
strFrame+='DateLayer.posTop += window.event.clientY-datelayery;}}';
strFrame+='function DragStart()';
strFrame+='{var DateLayer=parent.document.all.meizzDateLayer.style;';
strFrame+='datelayerx=window.event.clientX;';
strFrame+='datelayery=window.event.clientY;';
strFrame+='bDrag=true;}';
strFrame+='function DragEnd(){';
strFrame+='bDrag=false;}';
strFrame+='</scr' + 'ipt>';
//外边框
strFrame+='<div style="z-index:9999;position: absolute; left:0; top:0;" onselectstart="return false"><span id=tmpSelectYearLayer Author=wayx style="z-index: 9999;position: absolute;top: 3; left: 19;display: none"></span>';
strFrame+='<span id=tmpSelectMonthLayer Author=wayx style="z-index: 9999;position: absolute;top: 3; left: 78;display: none"></span>';

//####  上面中间
strFrame+='<table border=1 cellspacing=0 cellpadding=0 width=142 height=160 bordercolor=#2C3A44 bgcolor=#C6D8E2 Author="wayx">';
strFrame+='<tr Author="wayx"><td width=142 height=23 Author="wayx" bgcolor=#F0F0F0><table border=0 cellspacing=1 cellpadding=0 width=140 Author="wayx" height=23>';

//####  上面左箭头
strFrame+='<tr align=center Author="wayx"><td width=16 align=center bgcolor=#99AFBC style="font-size:12px;cursor: hand;color: #000" ';
strFrame+=' onclick="parent.meizzPrevM()" title="上 月" Author=meizz><b Author=meizz>&lt;</b>';

//####  中间鼠标移动事件
strFrame+='</td><td width=60 align=center style="font-size:12px;color:#3E3E3E;font-weight:bold;cursor:default" Author=meizz ';
strFrame+=' onmouseover="style.backgroundColor=\'#C6D8E2\'" onmouseout="style.backgroundColor=\'#F0F0F0\'" ';
strFrame+=' onclick="parent.tmpSelectYearInnerHTML(this.innerText.substring(0,4))" title="点击选择年度"><span Author=meizz id=meizzYearHead></span></td>';
strFrame+='<td width=48 align=center style="font-size:12px;color:#3E3E3E;font-weight:bold;cursor:default" Author=meizz onmouseover="style.backgroundColor=\'#C6D8E2\'" ';
strFrame+=' onmouseout="style.backgroundColor=\'#F0F0F0\'" onclick="parent.tmpSelectMonthInnerHTML(this.innerText.length==3?this.innerText.substring(0,1):this.innerText.substring(0,2))"';
strFrame+=' title="点击选择月份"><span id=meizzMonthHead Author=meizz></span></td>';

//####  上面右箭头
strFrame+='<td width=16 bgcolor=#99AFBC align=center style="font-size:12px;cursor: hand;color: #000" ';
strFrame+=' onclick="parent.meizzNextM()" title="下 月" Author=meizz><b Author=meizz>&gt;</b></td></tr>';
strFrame+='</table></td></tr>';


// 测试到这里了 一行行测试
//星期  #B7CA97背景色
strFrame+='<tr Author="wayx"><td width=142 height=18 Author="wayx">';
strFrame+='<table cellspacing=0 cellpadding=0 bgcolor=#738996' + (bMoveable? 'onmousedown="DragStart()" onmouseup="DragEnd()"':'');
strFrame+=' width=140 height=15 Author="wayx" style="cursor:' + (bMoveable ? 'move':'default') + ';border:0px #ccc solid;">';
strFrame+='<tr Author="wayx" align=center valign=bottom><td style="font-size:12px;color:#FFFFFF;border-right:1px #ccc solid;" Author=meizz>Su</td>';
strFrame+='<td style="font-size:12px;color:#FFFFFF;border-right:1px #ccc solid;" Author=meizz>Mo</td><td style="font-size:12px;color:#FFFFFF;border-right:1px #ccc solid;" Author=meizz>Tu</td>';
strFrame+='<td style="font-size:12px;color:#FFFFFF;border-right:1px #ccc solid;" Author=meizz>We</td><td style="font-size:12px;color:#FFFFFF;border-right:1px #ccc solid;" Author=meizz>Th</td>';
strFrame+='<td style="font-size:12px;color:#FFFFFF;border-right:1px #ccc solid;" Author=meizz>Fr</td><td style="font-size:12px;color:#FFFFFF;border-right:1px #ccc solid;" Author=meizz>Sa</td></tr>';
strFrame+='</table></td></tr>';
strFrame+='<tr Author="wayx"><td width=142 height=120 Author="wayx">';

//中间日期框   背景色#D8EBD8  边框颜色
strFrame+='<table border=0 cellspacing=1 cellpadding=0 bgcolor=#C6D8E2 width=140 height=120 Author="wayx">';
var n=0;for(j=0;j<5;j++){strFrame+='<tr align=center Author="wayx">';
for (i=0;i<7;i++)
{
	strFrame+='<td width=20 height=20 id=meizzDay'+n+' style="font-size:12px" Author=meizz onclick=parent.meizzDayClick(this.innerText,0)></td>';n++;}
	strFrame+='</tr>';
}
strFrame+='<tr align=center Author="wayx">';
for(i=35;i<39;i++)
	strFrame+='<td width=20 height=20 id=meizzDay'+i+' style="font-size:12px" Author=wayx onclick="parent.meizzDayClick(this.innerText,0)"></td>';
strFrame+='<td colspan=3 align=right Author=meizz style="text-decoration:none;text-align:center;color:#3E3E3E;background:#99AFBC"><span onclick=parent.closedate() style="font-size:12px;cursor: hand;"';
strFrame+=' Author=meizz title="' + _VersionInfo + '">&nbsp;关   闭</span>&nbsp;</td></tr>';
strFrame+='</table></td></tr><tr Author="wayx"><td Author="wayx">';
strFrame+='<table border=0 cellspacing=1 cellpadding=0 width=100% Author="wayx" bgcolor=#F0F0F0>';
strFrame+='<tr Author="wayx"><td Author=meizz align=left><input Author=meizz type=button class=button value="<<" title="上一年" onclick="parent.meizzPrevY()" ';
strFrame+=' onfocus="this.blur()" style="font-size: 12px; width: 25px; height: 20px"><input Author=meizz class=button title="上 月" type=button ';
strFrame+=' value="<" onclick="parent.meizzPrevM()" onfocus="this.blur()" style="font-size: 12px; width: 16px; height: 20px"></td><td ';
strFrame+=' Author=meizz align=center><input Author=meizz type=button class=button value="今 天" onclick="parent.meizzToday()" ';
strFrame+=' onfocus="this.blur()" title="今 天" style="font-size: 12px; height: 20px; cursor:hand"></td><td ';
strFrame+=' Author=meizz align=right><input Author=meizz type=button class=button value=">" onclick="parent.meizzNextM()" ';
strFrame+=' onfocus="this.blur()" title="下 月" class=button style="font-size: 12px; width: 16px; height: 20px"><input ';
strFrame+=' Author=meizz type=button class=button value=">>" title="下一年" onclick="parent.meizzNextY()"';
strFrame+=' onfocus="this.blur()" style="font-size: 12px; width: 25px; height: 20px"></td>';
strFrame+='</tr></table></td></tr></table></div>';

window.frames.meizzDateLayer.document.writeln(strFrame);
window.frames.meizzDateLayer.document.close();
//
//Date
var outObject;
var outButton;
var outformat='yyyy-mm-dd';

var outDate="";
var odatelayer=window.frames.meizzDateLayer.document.all;

function syjDate(tt)
{
	if (arguments.length>1){alert("Sorry!More Paras");return;}
	if (arguments.length==0){alert("Sorry!Can not zero Paras");return;}
	var dads  = document.all.meizzDateLayer.style;
	var th = tt;
	var ttop  = tt.offsetTop;
	var thei  = tt.clientHeight;
	var tleft = tt.offsetLeft;
	var ttyp  = tt.type;
	while (tt = tt.offsetParent){ttop+=tt.offsetTop; tleft+=tt.offsetLeft;}
	dads.top  = ((ttyp=="image")?ttop+thei:ttop+thei+6)+'px';
	dads.left = tleft+'px';
	outObject = th;
	//outformat = oformat;
	var reg = /^(\d+)-(\d{1,2})-(\d{1,2})$/; 
	//var r = outObject.value.match(reg); 
	var tempstr=outObject.value.substring(0,4)+"-"+outObject.value.substring(5,7)+"-"+outObject.value.substring(8,10);
	var r = tempstr.match(reg);
	if(r!=null)
	{
		r[2]=r[2]-1; 
		var d= new Date(r[1], r[2],r[3]); 
		if(d.getFullYear()==r[1]&&d.getMonth()==r[2]&&d.getDate()==r[3])
		{
			outDate=d;
		}
		else outDate="";
		meizzSetDay(r[1],r[2]+1);
	}
	else{
		outDate="";
		meizzSetDay(new Date().getFullYear(),new Date().getMonth()+1);
	}
	dads.display = '';
	event.returnValue=false;
}
var MonHead=new Array(12);
	MonHead[0]=31; MonHead[1]=28; MonHead[2]=31; MonHead[3]=30; MonHead[4] =31; MonHead[5] =30;
	MonHead[6]=31; MonHead[7]=31; MonHead[8]=30; MonHead[9]=31; MonHead[10]=30; MonHead[11]=31;

var meizzTheYear=new Date().getFullYear();
var meizzTheMonth=new Date().getMonth()+1;
var meizzWDay=new Array(39);

function meizzSetDay(yy,mm)
{
	meizzWriteHead(yy,mm);
	meizzTheYear=yy;
	meizzTheMonth=mm;

	for(var i=0;i<39;i++){meizzWDay[i]="";}
	var day1=1,day2=1,firstday=new Date(yy,mm-1,1).getDay();
	for(i=0;i<firstday;i++)meizzWDay[i]=GetMonthCount(mm==1?yy-1:yy,mm==1?12:mm-1)-firstday+i+1;
	for(i=firstday;day1<GetMonthCount(yy,mm)+1;i++){meizzWDay[i]=day1;day1++;}
	for(i=firstday+GetMonthCount(yy,mm);i<39;i++){meizzWDay[i]=day2;day2++;}
	for(i=0;i<39;i++)
	{
		var da=eval("odatelayer.meizzDay"+i);
		if(meizzWDay[i]!="")
		{
			da.borderColorLight="#B7CA97";
			da.borderColorDark="#FFFFFF";
			if(i<firstday)
			{
				da.innerHTML="<b><font color=gray>"+meizzWDay[i]+"</font></b>";
				da.title=(mm==1?12:mm-1)+"M"+meizzWDay[i]+"D";
				da.onclick=Function("meizzDayClick(this.innerText,-1)");
				if(!outDate)
					da.style.backgroundColor=((mm==1?yy-1:yy)==new Date().getFullYear()&&(mm==1?12:mm-1)==new Date().getMonth()+1&&meizzWDay[i]==new Date().getDate())?"#31424A":"#F0F0F0";
				else
				{
					da.style.backgroundColor=((mm==1?yy-1:yy)==outDate.getFullYear()&&(mm==1?12:mm-1)==outDate.getMonth()+1&&meizzWDay[i]==outDate.getDate())? "#00ffff":(((mm==1?yy-1:yy)==new Date().getFullYear()&&(mm==1?12:mm-1)==new Date().getMonth()+1&&meizzWDay[i]==new Date().getDate()) ?"#31424A":"#B2ABA3");
					if((mm==1?yy-1:yy)==outDate.getFullYear()&&(mm==1?12:mm-1)==outDate.getMonth()+1&&meizzWDay[i]==outDate.getDate())
					{
						da.borderColorLight="#FFFFFF";
						da.borderColorDark="#B7CA97";
					}
				}
			}
			else if(i>=firstday+GetMonthCount(yy,mm))
			{
				da.innerHTML="<b><font color=gray>"+meizzWDay[i]+"</font></b>";
				da.title=(mm==12?1:mm+1) +"M"+meizzWDay[i]+"D";
				da.onclick=Function("meizzDayClick(this.innerText,1)");
				if(!outDate)
					da.style.backgroundColor=((mm==12?yy+1:yy)==new Date().getFullYear()&&(mm==12?1:mm+1)==new Date().getMonth()+1&&meizzWDay[i]==new Date().getDate())?"#31424A":"#F0F0F0";
				else
				{
					da.style.backgroundColor=((mm==12?yy+1:yy)==outDate.getFullYear()&&(mm==12?1:mm+1)==outDate.getMonth()+1&&meizzWDay[i]==outDate.getDate())? "#00ffff":(((mm==12?yy+1:yy)==new Date().getFullYear()&&(mm==12?1:mm+1)==new Date().getMonth()+1&&meizzWDay[i]==new Date().getDate()) ?"#31424A":"#B2ABA3");
					if((mm==12?yy+1:yy)==outDate.getFullYear()&&(mm==12?1:mm+1)==outDate.getMonth()+1&&meizzWDay[i]==outDate.getDate())
					{
						da.borderColorLight="#FFFFFF";
						da.borderColorDark="#B7CA97";
					}
				}
			}
			else
			{
				da.innerHTML="<b>"+meizzWDay[i]+"</b>";
				da.title=mm +"M"+meizzWDay[i]+"D";
				da.onclick=Function("meizzDayClick(this.innerText,0)");
				if(!outDate){
					da.style.backgroundColor=(yy==new Date().getFullYear()&&mm==new Date().getMonth()+1&&meizzWDay[i]==new Date().getDate())?"#31424A":"#99AFBC";
				}else{
					da.style.backgroundColor=(yy==outDate.getFullYear()&&mm==outDate.getMonth()+1&&meizzWDay[i]==outDate.getDate())?"#00ffff":((yy==new Date().getFullYear()&&mm==new Date().getMonth()+1&&meizzWDay[i]==new Date().getDate())?"#31424A":"#738996");
					if(yy==outDate.getFullYear()&&mm==outDate.getMonth()+1&&meizzWDay[i]==outDate.getDate())
					{
						da.borderColorLight="#FFFFFF";
						da.borderColorDark="#B7CA97";
					}
				}
			}
			da.style.cursor="hand";
			da.style.border="0px #31424A solid";
		}
		else{da.innerHTML="";da.style.backgroundColor="";da.style.cursor="default";}
	}
}
function meizzWriteHead(yy,mm)
{
	odatelayer.meizzYearHead.innerText=yy+" 年";
	odatelayer.meizzMonthHead.innerText=mm+" 月";
}
function GetMonthCount(year,month)
{
	var c=MonHead[month-1];
	if((month==2)&&IsPinYear(year))
		c++;
	return c;
}
function tmpSelectYearInnerHTML(strYear)
{
	if(strYear.match(/\D/)!=null){alert("Year Must Numbers");return;}
	var m=(strYear)?strYear:new Date().getFullYear();
	if(m<1000||m>9999){alert("Year must between 1000 and 9999");return;}
	var n=m-10;
	if(n<1000) n=1000;
	if(n+26>9999) n=9974;
	var s="<select Author=meizz name=tmpSelectYear style='font-size: 12px'";
		s+="onblur='document.all.tmpSelectYearLayer.style.display=\"none\"'";
		s+="onchange='document.all.tmpSelectYearLayer.style.display=\"none\";";
		s+="parent.meizzTheYear=this.value;parent.meizzSetDay(parent.meizzTheYear,parent.meizzTheMonth)'>\r\n";
	var selectInnerHTML=s;
	for(var i=n;i<n+26;i++)
	{
		if(i==m)
			{selectInnerHTML+="<option Author=wayx value='"+i+"' selected>"+i+"年"+"</option>\r\n";}
		else
			{selectInnerHTML+="<option Author=wayx value='"+i+"'>"+i+"年"+"</option>\r\n";}
	}
	selectInnerHTML+="</select>";
	odatelayer.tmpSelectYearLayer.style.display="";
	odatelayer.tmpSelectYearLayer.innerHTML=selectInnerHTML;
	odatelayer.tmpSelectYear.focus();
}
function tmpSelectMonthInnerHTML(strMonth)
{
	if(strMonth.match(/\D/)!=null){alert("Months must Numbers");return;}
	var m=(strMonth)?strMonth:new Date().getMonth()+1;
	var s="<select Author=meizz name=tmpSelectMonth style='font-size: 12px'";
		s+="onblur='document.all.tmpSelectMonthLayer.style.display=\"none\"'";
		s+="onchange='document.all.tmpSelectMonthLayer.style.display=\"none\";";
		s+="parent.meizzTheMonth=this.value;parent.meizzSetDay(parent.meizzTheYear,parent.meizzTheMonth)'>\r\n";
	var selectInnerHTML=s;
	for(var i=1;i<13;i++)
	{
		if(i==m)
			{selectInnerHTML+="<option Author=wayx value='"+i+"' selected>"+i+"月"+"</option>\r\n";}
		else
			{selectInnerHTML+="<option Author=wayx value='"+i+"'>"+i+"月"+"</option>\r\n";}
	}
	selectInnerHTML+="</select>";
	odatelayer.tmpSelectMonthLayer.style.display="";
	odatelayer.tmpSelectMonthLayer.innerHTML=selectInnerHTML;
	odatelayer.tmpSelectMonth.focus();
}
function closedate()
{
	document.all.meizzDateLayer.style.display="none";
}
function closeLayer()
{
	document.all.meizzDateLayer.style.display="none";
	try
	{
		setformat(outObject,outformat);
	}
	catch(exceptionObj){}
	try
	{
		setetcell(0);
	}
	catch(exceptionObj){}
}
function IsPinYear(year)
{
	if(0==year%4&&((year%100!=0)||(year%400==0)))
		return true;
	else
		return false;
}
function GetDOW(day,month,year)
{
	var dt=new Date(year,month-1,day).getDay()/7;
	return dt;
}
function meizzPrevY()
{
	if(meizzTheYear>999&&meizzTheYear<10000)
		{meizzTheYear--;}
	else
		{alert("Year must between 1000 and 9999");}
	meizzSetDay(meizzTheYear,meizzTheMonth);
}
function meizzNextY()
{
	if(meizzTheYear>999&&meizzTheYear<10000)
		{meizzTheYear++;}
	else
		{alert("Year must between 1000 and 9999");}
	meizzSetDay(meizzTheYear,meizzTheMonth);
}
function meizzToday()
{
	var today;
	meizzTheYear=new Date().getFullYear();
	meizzTheMonth=new Date().getMonth()+1;
	today=new Date().getDate();
	//meizzSetDay(meizzTheYear,meizzTheMonth);
	if(outObject)
	{
		if(meizzTheMonth<10)
			{meizzTheMonth="0"+meizzTheMonth;}
		if(today<10)
			{today="0"+today;}
		//outObject.value=meizzTheYear+"-"+meizzTheMonth+"-"+today;
		outObject.value=meizzTheYear+""+meizzTheMonth+""+today;
	}
	closeLayer();
}
function meizzPrevM()
{
	if(meizzTheMonth>1)
		{meizzTheMonth--;}
	else
		{meizzTheYear--;meizzTheMonth=12;}
	meizzSetDay(meizzTheYear,meizzTheMonth);
}
function meizzNextM()
{
	if(meizzTheMonth==12)
		{meizzTheYear++;meizzTheMonth=1}
	else
		{meizzTheMonth++}
	meizzSetDay(meizzTheYear,meizzTheMonth);
}
function meizzDayClick(n,ex)
{
	var yy=meizzTheYear;
	var mm=parseInt(meizzTheMonth)+ex;
	if(mm<1)
	{
		yy--;
		mm=12+mm;
	}
	else if(mm>12)
	{
		yy++;
		mm=mm-12;
	}
	if(mm<10)
		{mm="0"+mm;}
	if(outObject)
	{
		if(!n)
		{
			//outObject.value="";
			return;
		}
		if( n<10)
			{n="0"+n;}
		outObject.value=yy+""+mm+""+n;
		closeLayer();
	}
	else
		{closeLayer();alert("Your Output Controls not Exsit!");}
}
function setformat(outObj,outformat)
{
	outObj.value=outObj.value.substring(0,4)+"-"+outObj.value.substring(4,6)+"-"+outObj.value.substring(6,8);
}
function syjDclick()
{
	with(window.event)
	{
		if(window.event.srcElement.getAttribute("Author")==null&&srcElement!=outObject&&srcElement!=outButton)
			closedate();
	}
}
function syjDkeyup()
{
	if(window.event.keyCode==27)
	{
		if(outObject)
			outObject.blur();
		closedate();
	}
	else if(document.activeElement)
	{
		if(document.activeElement.getAttribute("Author")==null&&document.activeElement!=outObject&&document.activeElement!=outButton)
		{
			closedate();
		}
	}
}
////////////////////////////////////////////	Times	//////////////////////////////////////////
////////////////////////////////////////////	Times	//////////////////////////////////////////
var outobj;
function syjTime(tt)
{
	if(arguments.length>1||arguments.length<=0)
		return;
	if(outobj=='undefined')
		return;
	var dads=document.all.syjif1.style;
	var th=tt;
	var ttop=tt.offsetTop;
	var thei=tt.clientHeight;
	var tleft=tt.offsetLeft;
	var ttyp=tt.type;
	while (tt=tt.offsetParent){ttop+=tt.offsetTop;tleft+=tt.offsetLeft;}
	dads.top=((ttyp=="image")?ttop+thei:ttop+thei+6)+'px';
	dads.left=tleft+'px';
	
	outobj=th;
	syjSetT();
	dads.display='';
	event.returnValue=false;
}
function syjSetT()
{
	if(outobj=='undefined'||outobj.value=='')
		return;
	try{
		window.frames.syjif1.selh.value=outobj.value.substring(0,2);
		window.frames.syjif1.selm.value=outobj.value.substring(3,5);
		window.frames.syjif1.sels.value=outobj.value.substring(6,8);
		if(window.frames.syjif1.selh.value=='')window.frames.syjif1.selh.value='00';
		if(window.frames.syjif1.selm.value=='')window.frames.syjif1.selm.value='00';
		//if(window.frames.syjif1.sels.value=='')window.frames.syjif1.sels.value='00';
	}catch(exceptionObj){}
}
function syj2S(i0)
{
	if(i0<10)
		return ('0'+i0);
	else
		return i0;
}
function syjOK()
{
	if(outobj=='undefined')
		return;
	var tf=window.frames.syjif1;
	outobj.value=tf.selh.value+':'+tf.selm.value;//+':'+tf.sels.value;
	syjClose();
}
function syjNOW()
{
	if(outobj=='undefined')
		return;
	var t1=new Date();
	outobj.value=syj2S(t1.getHours())+':'+syj2S(t1.getMinutes());//+':'+syj2S(t1.getSeconds());
	syjClose();
}
function syjClose()
{
	document.all.syjif1.style.display="none";
}
function syjTclick()
{
	if(window.event.srcElement!=outobj)
		syjClose();
}
function syjTkeyup()
{
	if(window.event.keyCode==27)
	{
		if(outobj)
			outobj.blur();
		syjClose();
	}
	else if(document.activeElement)
	{
		if(document.activeElement!=outobj)
		{
			syjClose();
		}
	}
}

var str0='';
document.writeln('<iframe id=syjif1 frameborder=0 style="position: absolute; width: 91px; height: 164px; z-index: 9995; display: none"></iframe>');
str0+='<!DOCTYPE html><html><head><title> </title><style>';
str0+='.button{WIDTH: 60px;border:1px #31424A solid;background:#99AFBC;font-family:宋体;color:#3E3E3E;}';
str0+='.fc{FONT-SIZE: 10pt;font-family:宋体;color:#3E3E3E;}';
str0+='.select{border:1px #31424A solid;width:50px;background: #99AFBC;color:#000;font-weight:bold;}';
str0+='</style>';
str0+='<scr'+'ipt>';
str0+='function abc(){var v1=new Date();dt1.value=parent.syj2S(v1.getHours())+\' : \'+parent.syj2S(v1.getMinutes())+\' : \'+parent.syj2S(v1.getSeconds());}window.setInterval("abc()",1000);';
str0+='</scr'+'ipt>';
str0+='</head><body>';
str0+='<DIV style="position: absolute; text-align:center; left:0; top:0;border:2px #2C3A44 solid;height:160px;">';
str0+='<table id="Table1" cellSpacing="0" cellPadding="0" border="0" width="85" height="160" bgColor="">';
str0+='<tr>';
str0+='<td style="background: #F0F0F0;text-align:center;"><input id=dt1 name=dt1 style="font-size:12px; font-weight:bold; background: #F0F0F0;border:none;color:#3E3E3E; width:85px; text-align:center;"  type="text" value=""></td></tr>';
str0+='<tr>';
str0+='<TD style="background: #F0F0F0;text-align:center;"><select id=selh class="select" type="select-one">';
for(i=0;i<24;i++)
	str0+='<option value='+syj2S(i)+'>'+syj2S(i)+'</option>';
str0+='</SELECT><font class=fc>小时</font></TD></tr><tr>';
str0+='<TD style="background: #F0F0F0;text-align:center;"><select id=selm class="select" type="select-one">';
for(i=0;i<60;i++)
	str0+='<option value='+syj2S(i)+'>'+syj2S(i)+'</option>';
str0+='</SELECT><font class=fc>分钟</font></TD></tr><tr>';
/**str0+='<TD><SELECT id=sels style="WIDTH: 40px; BORDER-TOP-STYLE: none; BORDER-RIGHT-STYLE: none; BORDER-LEFT-STYLE: none; BACKGROUND-COLOR: #b7ca97; BORDER-BOTTOM-STYLE: none" type="select-one">';
for(i=0;i<60;i++)
	str0+='<option value='+syj2S(i)+'>'+syj2S(i)+'</option>';
str0+='</SELECT><font class=fc>S</font></TD>';*/
str0+='</TR><tr>';
str0+='<TD  style="background: #F0F0F0; text-align:center;" colSpan="2">';
//str0+='<NOBR>';
str0+='<INPUT class="button" type="button" value="确 定" onclick="parent.syjOK();"></td></tr><tr><td style="background: #F0F0F0; text-align:center;">';
str0+='<INPUT class="button" type="button" value="当 前" onclick="parent.syjNOW();"></td></tr><tr><td style="background: #F0F0F0; text-align:center;">';
str0+='<INPUT class="button" type="button" value="关 闭" onclick="parent.syjClose();"></td></tr><tr><td style="background: #F0F0F0; text-align:center;">';
//str0+='</NOBR>';
str0+='</TD>';
str0+='</TR>';
str0+='</table>';
str0+='</DIV>';
str0+='</body>';

window.frames.syjif1.document.write(str0);
window.frames.syjif1.document.close();

////////////////////////////////////////////	close	//////////////////////////////////////////
////////////////////////////////////////////	close	//////////////////////////////////////////
function syjDTclick()
{
	syjDclick();
	syjTclick();
}
function syjDTkeyup()
{
	syjDkeyup();
	syjTkeyup();
}