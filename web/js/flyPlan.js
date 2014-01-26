/***
 * 显示输入框的边框效果
 * @param obj当前操作的对象
 * @param flag 多行文本框标志，为1时表示为多行文本框
 */
function showBorder(obj,flag){
	if(flag == '1'){
		$(obj).removeClass('fPlanTextareaNoBorder');
		$(obj).addClass('fPlanTextareaShowBorder');
	}else{
		$(obj).removeClass('textNoBorder');
		$(obj).addClass('textShowBorder');
	}
}
/***
 * 去除输入框的边框效果
 * @param obj当前操作的对象
 * @param flag 多行文本框标志，为1时表示为多行文本框
 */
function hideBorder(obj,flag){
	if(flag == '1'){
		$(obj).addClass('fPlanTextareaNoBorder');
	}else{
		$(obj).addClass('textNoBorder');
	}	
}

/**
 * 点击后切换到查看大图模式
 * @param bImgDiv 需要显示的div
 * @param sImg 需要被隐藏的div
 * @param bImg 需要显示的img对象
 * @param obj 当前对象
 * @param imgSrc 需要显示div的图片路径
 * @param otherDivId 需要显示其他div 叠加用
 * @param isShowOverflow 是否显示滚动条，如果是则不控制图片大小 1显示，默认不显示
 * @param imgFlag 当前图片的序号
 */
function showBigImgage(bImgDivId,sImgId,bImgId,obj,imgSrc,otherDivId,isShowOverflow,imgFlag){
	document.getElementById(sImgId).style.display="none";
	var bImgDiv = document.getElementById(bImgDivId);
	bImgDiv.style.display="block";
	document.getElementById("imgFlag").value = imgFlag;

	var bImg = document.getElementById(bImgId);
	//判断是叠加操作还是普通的放大操作
    if(''==otherDivId || null == otherDivId){
		if(obj=="" || obj == null){
			bImg.src = imgSrc;
		}else{
			bImg.src = obj.src;
		}
		
		
		if(null==isShowOverflow){
			bImg.style.width ="855px";
			bImg.style.height ="355px";
		}
		document.getElementById("curImgName").value = bImg.src;
	}else{
		var otherDiv = document.getElementById(otherDivId);
		otherDiv.style.display="block";
		bImg.src = document.getElementById("curImgName").value;
		document.getElementById("curImgName").value = "";
		if(null==isShowOverflow){
			bImg.style.width ="855px";
			bImg.style.height ="355px";
		}
	}
}

/*
 * 关闭查看大图模式
 * @param bImgDiv 当前显示的div，需要隐藏
 * @param sImg 被覆盖的div，需要显示
 */
function closeBigImgage(bImgDiv,sImg){
	document.getElementById(bImgDiv).style.display="none";
	document.getElementById(sImg).style.display="block";
	document.getElementById("bImgMessage").style.display="none";
}

/*
 * 切换照片
 * @param flag 1表示上一张
 *             2表示下一张
 * @param imgDiv 要操作的img Id列表
 */
function changeImg(flag,imgId){
	var imgFlag = document.getElementById("imgFlag").value;
	var imgNamesId = "";
	//不同的提示框，显示不同的图片
	switch(parseInt(imgFlag)){
		case 1:	
			imgNamesId = "imgNames1";
		break;
		case 2:
			imgNamesId = "imgNames2";
		break;
		case 3:
			imgNamesId = "imgNames3";
		break;
		case 4:
			imgNamesId = "imgNames4";
		break;
		default:imgNamesId = "imgNames1";
	}	
	document.getElementById("bImgMessage").style.display="none";
	var bImg = document.getElementById(imgId);
	var srcUrl = bImg.src.substring(0,bImg.src.lastIndexOf("/")+1);
	var srcName = bImg.src.substring(bImg.src.lastIndexOf("/")+1);
	var newImgName;
	//TODO
	var imgNames = document.getElementById(imgNamesId).value.split("_#_");

	for(i=0;i<imgNames.length;i++){
		
		//取得当前显示的图片名称
		if(srcName == imgNames[i]){
			//判断是上一张还是下一张
			if("1"==flag){
				//判断是否是第一张，第一张不允许上一张操作
				if(i==0){
					document.getElementById("bImgMessage").style.display="none";
					document.getElementById("bImgMessage").innerHTML="已经是第一张图片了哦...."
					return;
				}else{
					newImgName = imgNames[i - 1];
				}
			}else{
				//判断是否为最后一张，最后一张照片不允许下一张操作
				if(i==imgNames.length-1){
					document.getElementById("bImgMessage").style.display="block";
					document.getElementById("bImgMessage").innerHTML="已经是最后一张图片了哦...."
					return;
				}else{				
					newImgName = imgNames[i + 1];
				}
			}
		}
	}
	bImg.src = srcUrl + newImgName;
	document.getElementById("curImgName").value = bImg.src;
}

//全局对象，用于存储当前操作的行号：  按航线节点的编号，从 1 开始 即 与节点前的序号列值一致。
var activeRow;

/**
 * 显示操作航线浮动菜单
 */
function showFLinMeun(objId,trIndex){
	var setupLineDivMeun = document.getElementById(objId);
	if(setupLineDivMeun==null){
		return false;
	}
	
	//获取当前操作的行的行号
	// activeRow =document.activeElement.parentNode.parentNode.rowIndex + 1;
	activeRow = trIndex ; // 按航线节点的编号，从 1 开始 即 与节点前的序号列值一致。
	
	//创建按钮
	var add = document.createElement("img");
	add.src="images/add.png";
	add.onclick = addClick;	
	
	var deduct = document.createElement("img");
	deduct.src="images/deduct.png";	
	deduct.onclick = deductClick;	
	
	var up = document.createElement("img");
	up.src="images/up.png";	
	up.onclick = upClick;
	
	var down = document.createElement("img");
	down.src="images/down.png";	
	down.onclick = downClick;	
	
	//第一行时只显示加号
	if(1 == activeRow){
		setupLineDivMeun.appendChild(add);
		setupLineDivMeun.style.textAlingn = "left";
	}
	/**
	else if(7 == activeRow){
		//最后一行不做任何操作
		return false;
	}
	**/
	else{
				
		setupLineDivMeun.appendChild(add);	
		setupLineDivMeun.appendChild(deduct);
		setupLineDivMeun.appendChild(down);
		setupLineDivMeun.appendChild(up);
	}
	//显示浮动菜单div
	setupLineDivMeun.style.display="block";
	
	//将焦点定位到浮动菜单上
	setupLineDivMeun.focus();
	
	//设置在焦点移开后菜单消失事件
	setupLineDivMeun.onblur=hiddenFLinMeun;
	
}

/*
 * 焦点移开后，浮动菜单消失事件
 */
function hiddenFLinMeun(){
	this.style.display="none";
	//清空div中所有创建的内容
	this.innerHTML="";
}

/**
 * 航线点添加动作，打开节点查询选择页面
 */
function addClick(){
	// showIframeDiv(718,427,'nv.html','iframe','添加航线点',50);
	var jsondata = {
   		//adep:adep.value,
   		//ades:ades.value
    };
    
    $("#jQueryCommonDiv").load('adAction.ax?op=getNVOrFFXList', jsondata, function(){
    	showLocalDiv(718,425,'添加航线点','jQueryCommonDiv',60,'0');
    });
}

/**
 *  2013-09-29
 *	点击添加导航台或者地标点至航线
 *  arr : 需增加的导航台节点数组
 */
function setNVInfo(arr){
	
	// 当前显示的航线即需要增加节点的航线【可能为主航线或备航线】
	wayId='lineTable1';	// 默认表示操作层 lineTableDiv1。
	wayType ='1'; // 默认表示操作的为主航线。
	if(document.getElementById('lineFlag').value == '1' ){  // 表示当前 lineTableDiv1 层表示的主航线
 		if(document.getElementById('viewTypeFlag').value == '0'){ // 当前显示的为备航线，即当前操作的为备航线
	    	wayId='lineTable2';
	    	wayType ='0';
		}
	}else{	  // 表示当前 lineTableDiv2 层表示的主航线
 		if(document.getElementById('viewTypeFlag').value == '1'){ // 当前显示的为主航线，即当前操作的为主航线
	    	wayId='lineTable2';
		}else{
	    	wayType ='0';
		}
	}
	
	tableObj = document.getElementById(wayId);  //获取表格相关属性
	
	linePointLength = 0 ;
	if( wayType == '1'){
    	linePointLength = $('#linePointCount1').val();
    }else{
    	linePointLength = $('#linePointCount2').val();
    }
    
	resetPointRowInfo(wayId,activeRow+1,wayType,arr.length); //  修改插入的行后的各行号等信息。
	
	for(var i = 0; i < arr.length; i++) {
		
		var classType = "";
		if((activeRow+i) %2 ==0) {
			classType = "twoTdClass";
		}else {
			classType = "oneTdClass";
		}
		
		var indexValue = activeRow + i+1;
		
		newTr = tableObj.insertRow(indexValue);  //表示加入到第几行，编号从0开始
		newTr.className=classType;
		
		if( wayId == 'lineTable1'){
			addLinePointRow(newTr,'1', indexValue ,'1' ,arr[i].nvCode ,arr[i].nvName ,arr[i].nvPos ,arr[i].nvType);
		}else{
			addLinePointRow(newTr,'0', indexValue ,'1' ,arr[i].nvCode ,arr[i].nvName ,arr[i].nvPos ,arr[i].nvType);
		}
	}
	
	if( wayType == '1'){
		document.getElementById("linePointCount1").value = parseInt(linePointLength) + arr.length;
    }else{
    	document.getElementById("linePointCount2").value = parseInt(linePointLength) + arr.length; 
    }
    
    // 删除多余的空白行
	if(parseInt(linePointLength) + arr.length <6 ){
		deleteLineRow( tableObj.rows.length-1 , 6 ,tableObj);
	}else{
		deleteLineRow( tableObj.rows.length-1 , parseInt(linePointLength) + arr.length  ,tableObj);
	}
	
	// 重置全局行操作行变量，使之按点击顺序依次往下添加
	activeRow = activeRow+arr.length ;
	
	// 进行一次航线显示切换，避免滚动条不能正常显示的问题。
	if( wayType == '1'){
	    showSelectLineDiv('0');  
	    showSelectLineDiv('1');  
    }else{
	    showSelectLineDiv('1');  
	    showSelectLineDiv('0');  
    }
    
    showGraphicPonit(wayType);
    reDrawLineSection(wayType);
    getDateDiff();
	
}

// 选择机场回调方法，设置返回的各表单元素值
function setAdNodeInfo(flag,value,str,pos) {
	object = {id:'',nvName:str,nvCode:value,nvPos:pos};
	var jsondata = {
   		//adep:adep.value,
   		icaoCode:value
    };
	$.post('flyPlanAction.ax?method=getAdIdInfo', jsondata, function(data){
		if (data.data.returnflag == "true") {
			if (data.data.adId != "" && data.data.adId !=document.getElementById("adep").value)  {
				if(flag == '1'){
					document.getElementById("adepName").value = object.nvName;
					document.getElementById("adep").value = data.data.adId;
					document.getElementById("adepCode").value = object.nvCode;
					document.getElementById("adepPos").value = object.nvPos;
					addAdepOrAdesToLine('1');
					reFreshGisLine();
					getDateDiff();
					
				}else if(flag == '2' && data.data.adId !=document.getElementById("ades").value){
					document.getElementById("adesName").value = object.nvName;
					document.getElementById("ades").value = data.data.adId;
					document.getElementById("adesCode").value = object.nvCode;
					document.getElementById("adesPos").value = object.nvPos;
					addAdepOrAdesToLine('2');
					reFreshGisLine();
					getDateDiff();
					
				}else if(data.data.adId !=document.getElementById("altn1").value){
					document.getElementById("altn1Name").value = object.nvName;
					document.getElementById("altn1").value = data.data.adId;
					document.getElementById("altn1Code").value = object.nvCode;
				}
			}
		}
   	},"json");
}

/**
 *  2013-10-17
 *	点击地图上的设为航线点操作 添加导航台或者地标点至航线
 *  arr : 需增加的节点
 */
function gisWriteNVInfo(arr){

	if(!checkElementFilled('adep','请选择设置起飞机场!','adepName')){
		return false;
	}
	if(!checkElementFilled('ades','请选择设置目的机场!','adesName')){
		return false;
	}
	
	// 当前显示的航线即需要增加节点的航线【可能为主航线或备航线】
	wayType ='1'; // 默认表示操作的为主航线。
	if(document.getElementById('viewTypeFlag').value == '0'){ // 当前显示的为备航线，即当前操作的为备航线
    	wayType ='0';
	}
 	wayDivType='1';
 	if( (wayType =='1' && document.getElementById('lineFlag').value == '0')  // 显示主航线，当前 lineTableDiv2 层表示的主航线
 		|| (wayType =='0' && document.getElementById('lineFlag').value == '1') ){	// 或者 显示备航线，当前 lineTableDiv2 层表示的备航线
		    wayDivType='2';
	}
 	
 	
	 	
	
	// linePointLength = 0 ;
	
    // 添加的点坐标
    addemp=getPointNumInfo(arr[0].nvPos);
    temp1=addemp.indexOf(',');
   	//  经度1
 	lng1 =addemp.substr(1,temp1-1);
  	// 纬度1 
    lat1 =addemp.substr(temp1+1,addemp.length - temp1-2 );
    
    // 当前参与计算的点的坐标
   	//  经度2
 	lng2 =0;
  	// 纬度2
    lat2 =0;
    
 	// 上一个参与计算的点的坐标
   	//  经度3
 	lng3 =0;
  	// 纬度3
    lat3 =0;
    
    // 起飞机场点的坐标
   	//  经度4
 	lng4 =0;
  	// 纬度4
    lat4 =0;
    
    // 被比较点的坐标
    temp='';
    
    // 定位插入点： 暂定插入到距离最近的节点之后【目的机场距离最近则应插入目的机场前】
    pointIndex=0; // 记录点顺序号
    
    pointBreakIndex=0; // 记录需停止计算的
    dis1=0;  	// 当前计算距离
    dis2=0;	 	// 当前计算的最短距离
    dis3=0;		// 当前计算的倒数第二短的距离
    if(wayDivType =='1'){
		for (i=0;i<document.form1.elements.length;i++){
		
		    if (document.form1.elements[i].name=="rs_PPosMain"){ 
		    	pointIndex++ ;
		    	pointBreakIndex++ ;
		    	temp=getPointNumInfo(document.form1.elements[i].value);
		    	temp1=temp.indexOf(',');
			    //  经度2
			 	lng2 =temp.substr(1,temp1-1);
			  	// 纬度2 
			    lat2 =temp.substr(temp1+1,temp.length - temp1-2 );
			    
			    if(pointIndex == 1){
				     //  经度4
				 	lng4 =temp.substr(1,temp1-1);
				  	// 纬度4 
				    lat4 =temp.substr(temp1+1,temp.length - temp1-2 );
			    }
			    dis1 = getDistance(parseFloat(lat1),parseFloat(lng1),parseFloat(lat2),parseFloat(lng2)); // 航线节点至当前需插入的节点的距离
			    
			    if(dis2 == 0 ||  dis1 < dis2){
			    	dis3 = dis2;
			    	dis2 = dis1;
			    }
			    if( dis3 == 0){
			    	dis3 = dis2;
			    }
			    
		    }
		    if( dis1 > dis2 ){
		    	break;
		    }
		    //  经度3
		    lng3 =temp.substr(1,temp1-1);
		  	// 纬度3 
		    lat3 =temp.substr(temp1+1,temp.length - temp1-2 );
		   
		}
		
	}else{
		for (i=0;i<document.form1.elements.length;i++){
		    if (document.form1.elements[i].name=="rs_PPosBak"){
		    	pointIndex++ ;
		    	pointBreakIndex++ ;
		    	temp=getPointNumInfo(document.form1.elements[i].value);
		    	 temp1=temp.indexOf(',');
			    //  经度2
			 	lng2 =temp.substr(1,temp1-1);
			  	// 纬度2 
			    lat2 =temp.substr(temp1+1,temp.length - temp1-2 );
			    dis1 = getDistance(parseFloat(lat1),parseFloat(lng1),parseFloat(lat2),parseFloat(lng2));
			    if(dis2 == 0 ||  dis1 < dis2){
			    	dis3 = dis2;
			    	dis2 = dis1;
			    }
			    if( dis3 == 0){
			    	dis3 = dis2;
			    }
		    }
		    if( dis1 > dis2 ){
		    	break;
		    }
		     //  经度3
		    lng3 =temp.substr(1,temp1-1);
		  	// 纬度3 
		    lat3 =temp.substr(temp1+1,temp.length - temp1-2 );
		}
	
	}
	
    // 设置全局变量，表示增加的节点在哪个位置，使之按点击顺序依次往下添加，默认设置为节点添加顺序为从起飞机场添加到目的机场
    
    // 定位插入点：插入到距离最近的节点之前或之后，默认暂定为之后
   	//【如果距离开始增大之前的点距离首节点远于需插入节点，则插入到上一个节点之前】
   	if(getDistance(parseFloat(lat4),parseFloat(lng4),parseFloat(lat1),parseFloat(lng1)) < getDistance(parseFloat(lat4),parseFloat(lng4),parseFloat(lat3),parseFloat(lng3)) ){
   		pointIndex = pointIndex -1;
   	}
    //【目的机场距离最近则插入目的机场前】
    if( wayType == '1' &&　pointIndex　>= parseInt( $('#linePointCount1').val() )　){
    	pointIndex = parseInt( $('#linePointCount1').val() );
    }else if( wayType == '0' &&　pointIndex　>= parseInt( $('#linePointCount2').val() )　){
    	pointIndex = parseInt( $('#linePointCount2').val() );
    }
	activeRow = pointIndex - 1 ;
	if(activeRow < 1) {
		activeRow = 1;
	}
    setNVInfo(arr);
}
	
/**
 *  2013-10-17
 *	计算地图两个坐标点之间的距离
 */
var R = 6378137.0  // 单位米 地球半径
var fl = 1/298.257;
/**
 * lat1 纬度1
 * lng1 经度1
 * lat2 纬度2
 * lng2 经度2
*/
function getDistance(lat1,lng1,lat2,lng2){
	var f = getRad((lat1+lat2)/2);
	var g = getRad((lat1-lat2)/2);
	var l = getRad((lng1-lng2)/2);
	var sg = Math.pow(Math.sin(g),2);
	var sl = Math.pow(Math.sin(l),2);
	var sf = Math.pow(Math.sin(f),2);

	var s = sg*(1-sl)+sl*(1-sf);
	var c = (1-sg)*(1-sl)+sf*sl;
	
	//alert("s="+s+"; c=" + c);
	
	var w = Math.atan(Math.sqrt(s/c));
	var r = Math.sqrt(s*c)/w;
	
	// alert("w="+w+"; r=" + r);
	
	var d = 2*R*w;
	var h1 = (3*r-1)/2/c;
	var h2 = (3*r+1)/2/s;

	return d*(1+fl*(h1*sf*(1-sg)-h2*(1-sf)*sg));
}
//根据角度返回弧度
function getRad(d){
	return d*Math.PI/180.0;
}

/*
 * 2013-09-30 
 * 增加航线节点时修改行号等信息
 * wayId ： 操作的表单对应ID 
 * startIndex : 需增加行号的起始行号 // 从 0 开始，包括表头行 ，对应为节点的编号
 * wayType : 1 主航线 
			 0 备航线 
 * increment : 行号增量 ， 默认为加 1 。
 */
function resetPointRowInfo(wayId,startIndex,wayType,increment) {

    var tableObj = document.getElementById(wayId);  //获取表格相关属性
    
    linePointLength = 0 ;
    if( wayType == '1'){
    	linePointLength = $('#linePointCount1').val();
    }else{
    	linePointLength = $('#linePointCount2').val();
    }
    
    for(var i=startIndex ; i < tableObj.rows.length ; i++ ){
    
    	trNode = tableObj.rows[i];
    	
    	if((i+increment) %2 == 1) {
			trNode.className = "twoTdClass";
		}else {
			trNode.className = "oneTdClass";
		}
		
		if(i <= linePointLength){
			tdNodes = trNode.cells;
	       	if(wayId =='lineTable1'){
	       	
	       		if(i < linePointLength){
	      			updateRowInfo(trNode, '1', (i+increment) ,'1'); 	// 重置非目的机场节点	
	       		}else{
	       			updateRowInfo(trNode, '1', (i+increment) ,'0'); 	// 重置目的机场节点	
	       		}
			}else{
			
				// 序号 
	        	tdNodes[0].innerHTML= (i+increment) +'<input type="hidden" name="rsPNumBak" id="rsPNumBak' + (i+increment) + '" value="'+ (i+increment) +'"/>';
	        	
	        	// 操作
	      		if(i < linePointLength){
	      			updateRowInfo(trNode, '0', (i+increment) ,'1'); 	// 重置非目的机场节点	
	      		}else{
	      			updateRowInfo(trNode, '0', (i+increment) ,'0'); 	// 重置目的机场节点	
	      		}
			}
		}
    }
}

/*
 * 2013-09-30 	通用方法，重新设置行编号及传递参数信息
 * trNode ： 需重置的表格行对象
 * divType : 1 表示修改的为 lineTable1
			 0 表示修改的为 lineTable2
 * newTrIndex : 重置航线节点编号
 * nodeType : 节点类型，0 表示 为目的机场节点
					  1 表示非目的机场节点 【即起飞机场节点或中间节点】
 */
 
function updateRowInfo(trNode,divType,newTrIndex,nodeType) {

	tdNodes = trNode.cells;
    if(divType == '1'){
		// 重置序号 
       	tdNodes[0].innerHTML= newTrIndex +'<input type="hidden" name="rsPNumMain" id="rsPNumMain' + newTrIndex + '" value="'+ newTrIndex +'"/>'; 
   		// 重置操作
    	if(nodeType == '1'){
   			tdNodes[4].innerHTML='<div class="setupLineDivMeun" id="setupLineDivMeun'+ newTrIndex +'"></div>'
   				+'<div class="setupLineDiv" onClick="showFLinMeun(\'setupLineDivMeun'+ newTrIndex +'\','+ newTrIndex +')"><img src="images/line_setup.png"/></div>';	
    	}
	}else{
		// 重置序号 
        tdNodes[0].innerHTML= newTrIndex +'<input type="hidden" name="rsPNumBak" id="rsPNumBak' + newTrIndex + '" value="'+ newTrIndex +'"/>';
       	// 重置操作
     	if(nodeType == '1'){
      		tdNodes[4].innerHTML='<div class="setupLineDivMeun" id="setupLineDivMeunBak'+ newTrIndex +'"></div>'
				+'<div class="setupLineDiv" onClick="showFLinMeun(\'setupLineDivMeunBak'+ newTrIndex +'\','+ newTrIndex +')"><img src="images/line_setup.png"/></div>';	
     	}
	}
}

 /***
 * 航线点删除动作
 * activeRow : 全局变量，记录当前表单的操作行，包括标题，从 1 开始编号。
 * wayType ： 表示操作的航线类型， 1 表示主航线
 							   0 表备航线 
 * 2013-09-27 
 */
function deductClick(){
	
	// 当前显示的航线即需要删除的航线【可能为主航线或备航线】
	wayId='lineTable1';	// 默认表示操作层 lineTableDiv1。
	wayType ='1'; // 默认表示操作的为主航线。
	if(document.getElementById('lineFlag').value == '1' ){  // 表示当前 lineTableDiv1 层表示的主航线
 		if(document.getElementById('viewTypeFlag').value == '0'){ // 当前显示的为备航线，即当前操作的为备航线
	    	wayId='lineTable2';
	    	wayType ='0';
		}
	}else{	  // 表示当前 lineTableDiv2 层表示的主航线
 		if(document.getElementById('viewTypeFlag').value == '1'){ // 当前显示的为主航线，即当前操作的为主航线
	    	wayId='lineTable2';
		}else{
	    	wayType ='0';
		}
	}
	
	// 删除航线的对应节点。
	tableObj = document.getElementById(wayId);  //获取表格相关属性
	tableObj.deleteRow(activeRow);	//行编号从0开始
	
	temp=0 ;
	if(wayType == '1'){
		temp = parseInt(document.getElementById('linePointCount1').value);
	}else{
		temp = parseInt(document.getElementById('linePointCount2').value);
	}
	
 	for(var i=activeRow ; i < temp; i++){
 	
    	trNode = tableObj.rows[i];
    	if(i%2 ==1){
    		trNode.className="twoTdClass";  
		}else{
			trNode.className="oneTdClass";
		}
       	tdNodes = trNode.cells;
       	if(wayId == 'lineTable1'){
       	
       		if(i<temp-1){
       			updateRowInfo(trNode, '1', i ,'1'); 	// 重置非目的机场节点	
       		}else{
       			updateRowInfo(trNode, '1', i ,'0'); 	// 重置目的机场节点	
       		}	
       							
		}else{
		
			if(i<temp-1){
       			updateRowInfo(trNode, '0', i ,'1'); 	// 重置非目的机场节点	
       		}else{
       			updateRowInfo(trNode, '0', i ,'0'); 	// 重置目的机场节点	
       		}	
		}
    }
	
	if( wayType =='1' ){
		document.getElementById('linePointCount1').value =temp-1 ;
		if( temp < 7 ){
			addNewLineRow(temp, temp+1 ,tableObj); // 补充空白行 
		}
		
	}else{
		document.getElementById('linePointCount2').value =temp-1 ;
		if( temp < 7 ){
			addNewLineRow(temp, temp+1 ,tableObj); // 补充空白行 
		}
		
	}
	showGraphicPonit(wayType);
	reDrawLineSection(wayType);
	getDateDiff();
}

/**
 * 2013-09-28 航线点向下移动操作
 * activeRow : 全局变量，记录当前表单的操作行，包括标题，从 1 开始编号。
 */
function downClick(){

	lineLength=0 ; // 操作航线的节点数
	// 当前显示的航线即需要删除的航线【可能为主航线或备航线】
	if(document.getElementById('lineFlag').value == '1' ){  // 表示当前 lineTableDiv1 层表示的主航线
 		if(document.getElementById('viewTypeFlag').value == '1'){ // 当前显示的为主航线
	    	lineLength = parseInt(document.getElementById('linePointCount1').value);
		}else{
	    	lineLength = parseInt(document.getElementById('linePointCount2').value);
		}
	}else{	  // 表示当前 lineTableDiv2 层表示的主航线
 		if(document.getElementById('viewTypeFlag').value == '1'){ // 当前显示的为主航线
	    	lineLength = parseInt(document.getElementById('linePointCount1').value);
		}else{
	    	lineLength = parseInt(document.getElementById('linePointCount2').value);
		}
	}
	if(activeRow == lineLength-1 ){
 		showConfirmDiv(4,'对不起，末节点必须为降落机场，不可向下移动!','警告信息');
 		return;
 	}
 	
	activeRow=activeRow+1;	
	upClick('down'); 
	   	
}

/**
 * 2013-09-28 航线点向上移动操作
 * activeRow : 全局变量，记录当前表单的操作行，包括标题，从 1 开始编号。
 * opFlag : down 表示执行的是下移操作
 */
function upClick(opFlag){

	if(activeRow ==2 ){
 		showConfirmDiv(4,'对不起，首节点必须为起飞机场，不可向上移动!','警告信息');
 		return;
 	}
	
	// 当前显示的航线即需要上移的航线【可能为主航线或备航线】
	wayId='lineTable1';	// 默认表示操作层 lineTableDiv1。
	if(document.getElementById('lineFlag').value == '1' &&  document.getElementById('viewTypeFlag').value == '0' 	// 表示当前 lineTableDiv1 层表示的主航线，当前显示的为备航线，即当前操作的为备航线
		|| document.getElementById('lineFlag').value == '0' &&  document.getElementById('viewTypeFlag').value == '1'){  // 表示当前 表示当前 lineTableDiv2 层表示的主航线，当前显示的为主航线，即当前操作的为主航线  
	    	wayId='lineTable2';
	}
	
	tableObj = document.getElementById(wayId);  //获取上移航线的表格相关属性
	tbRowsObj = tableObj.rows; // 表单所有行
	
	tr1=tableObj.rows[activeRow]; // 执行向上移动的行
 	tr2=tableObj.rows[activeRow-1]; // 需下移的行
 		
 	tdNodes1 = tr1.cells;	
 	tdNodes2 = tr2.cells;	
 	
 	if(opFlag == 'down'){
 		tr2.className="selectTdClass";
 		if( activeRow %2 == 1){
	 		tr1.className="oneTdClass";
	   		
		}else{
			tr1.className="twoTdClass";
		}
 	}else{
 		tr1.className="selectTdClass";
 		if( activeRow %2 == 1){
	 		//tr1.className="oneTdClass";
	   		tr2.className="twoTdClass";
	   		
		}else{
			//tr1.className="twoTdClass";
			tr2.className="oneTdClass";
		}
 	}
 	
 	if(wayId == 'lineTable1'){ // 操作 lineTableDiv1 层
 	
 		// 上移的行重新设置
       	updateRowInfo(tr1, '1', (activeRow-1) ,'1'); 	// 重置非目的机场节点	
       		
 		// 下移的行重新设置
 		updateRowInfo(tr2, '1', activeRow ,'1'); 	// 重置非目的机场节点	
							
	}else{
		// 上移的行重新设置
		updateRowInfo(tr1, '0', (activeRow-1) ,'1'); 	// 重置非目的机场节点	
		
		// 下移的行重新设置
		updateRowInfo(tr2, '0', activeRow ,'1'); 	// 重置非目的机场节点	
		
	}
	
    //tr1.swapNode(tr2);      // 此方式对火狐不起作用。
    cloneTr=tr1.cloneNode(true);  
    tr2.parentNode.replaceChild(cloneTr,tr2);
    tr1.parentNode.replaceChild(tr2,tr1);
    
    showGraphicPonit(document.getElementById('viewTypeFlag').value);
    reDrawLineSection(document.getElementById('viewTypeFlag').value);
	getDateDiff();
}



/***
 * 2013-09-23 点击 显示主、备航线的显示层的切换
 * @param rsType 当前需显示的航线类型: 1 表示需要显示主航线
								   0 表示需要显示备航线
 */
function showSelectLineDiv(rsType){
 	if(rsType =='1'){ 
 		document.getElementById('viewTypeFlag').value = '1';
 		document.getElementById("isMainLine").innerHTML='主航线';
 		document.getElementById('mainBut').className="LineButton mainLineButton";
 		document.getElementById('bakBut').className="LineButton prepareLinButton";
 		if(document.getElementById('lineFlag').value == '1'){ // lineTableDiv1 表示主航线
	    	document.getElementById('lineTableDiv1').style.display = 'block';
	    	document.getElementById('lineTableDiv2').style.display = 'none';
		}else{
	    	document.getElementById('lineTableDiv1').style.display = 'none';
	    	document.getElementById('lineTableDiv2').style.display = 'block';
		}
	}else{
		document.getElementById('viewTypeFlag').value = '0';
		document.getElementById("isMainLine").innerHTML='备航线';
		document.getElementById('mainBut').className="LineButton prepareLinButton";
 		document.getElementById('bakBut').className="LineButton mainLineButton";
 		if(document.getElementById('lineFlag').value == '0'){ // lineTableDiv1 表示备航线
	    	document.getElementById('lineTableDiv1').style.display = 'block';
	    	document.getElementById('lineTableDiv2').style.display = 'none';
		}else{
	    	document.getElementById('lineTableDiv1').style.display = 'none';
	    	document.getElementById('lineTableDiv2').style.display = 'block';
		}
	}
}
/***
 * 2013-11-18 日增加，点击显示主、备航线时，刷新刨面图
 * param wayType 需在显示的刨面图类型 
 * 				1 显示主航线刨面图
 * 				0 显示示备航线刨面图
 */	
function showSelectLineDraw(wayType){
	if(document.getElementById('viewTypeFlag').value != wayType){
		reDrawLineSection(wayType);
	}
}

/***
 * 2013-09-23 主、备航线的航线节点内容的切换
 */
 function changeSelectLineType(){
 
 	// 交换主、备航线的节点数量
 	linePointCount1 = document.getElementById('linePointCount1').value;
 	linePointCount2 = document.getElementById('linePointCount2').value;
 	
 	if(parseInt(linePointCount2) == 0){
 		// alert('对不起，当前未设置备航线，不能切换为主航线!');
 		showConfirmDiv(4,'对不起，当前未设置备航线，不能切换为主航线!','警告信息');
 		return ;
 	}
 	document.getElementById('linePointCount1').value = linePointCount2;
 	document.getElementById('linePointCount2').value = linePointCount1;
 	
 	if(document.getElementById('lineFlag').value == '1' ){  // 表示当前 lineTableDiv1 层表示的主航线
 		document.getElementById('lineFlag').value = '0';   // 切换为 lineTableDiv2 层表示主航线
 		if(document.getElementById('viewTypeFlag').value == '1'){ // 当前需要显示的为主航线，切换为显示 lineTableDiv2 层
	    	document.getElementById('lineTableDiv1').style.display = 'none';
	    	document.getElementById('lineTableDiv2').style.display = 'block';
		}else{
	    	document.getElementById('lineTableDiv1').style.display = 'block';
	    	document.getElementById('lineTableDiv2').style.display = 'none';
		}
	}else{
		document.getElementById('lineFlag').value = '1';  // 切换为 lineTableDiv1 层表示主航线
 		if(document.getElementById('viewTypeFlag').value == '1'){ // 当前需要显示的为主航线，切换为显示 lineTableDiv1 层
	    	document.getElementById('lineTableDiv1').style.display = 'block';
	    	document.getElementById('lineTableDiv2').style.display = 'none';
		}else{
	    	document.getElementById('lineTableDiv1').style.display = 'none';
	    	document.getElementById('lineTableDiv2').style.display = 'block';
		}
	}
	showGraphicPonit('1');
	showGraphicPonit('0');
	// 设置画线顺序，切换刨面图
	if(document.getElementById('viewTypeFlag').value == '1'){
		reDrawLineSection('1');
	}else{
		reDrawLineSection('0');
	}
	getDateDiff();
}
 	
/***
 * 2013-09-23 保存当前航线为常用航线
 */
function ajaxSaveGeneralSkyWay(){
	skyFlag='1';	//lineTableDiv1 层航线设置为常用
	if(document.getElementById('viewTypeFlag').value == '1' ){ 	// 当前显示的是主航线，即需要设置为常用航线的航线
		if( parseInt(document.getElementById("linePointCount1").value) == 0){
			showConfirmDiv(4,'当前航线不可用，无法设置为常用航线!','警告信息');
			return;
		}
		if(document.getElementById('lineFlag').value == '0' ){  // 主航线 为 lineTableDiv2 层
			skyFlag='0';				//lineTableDiv2 层航线设置为常用
		}
	}else{			// 当前显示的是备航线，即需要设置为常用航线的航线
		if( parseInt(document.getElementById("linePointCount2").value) == 0){
			showConfirmDiv(4,'当前航线不可用，无法设置为常用航线!','警告信息');
			return;
		}
		if(document.getElementById('lineFlag').value == '1' ){  // 备航线 为 lineTableDiv2 层
			skyFlag='0';				//lineTableDiv2 层航线设置为常用
		}
	}
	
	showConfirmDiv(1,'您确认设为常用航线吗?','提示',function(choose){
		if(choose=="yes"){
			var jsondata = $("#form1").serializeArray();
        	$.post('flyPlanAction.ax?method=saveGeneralSkyWay&jquery=1&skyFlag='+skyFlag, jsondata, function(data){
			if (data.data.returnflag == "true") {
				showConfirmDiv(2,'常规航线设置成功!','操作提示信息');
				if (data.data.skyFlag == "1") {
					document.getElementById("rsIdGeneralMain").value=data.data.rrId;
				}else{
					document.getElementById("rsIdGeneralBak").value=data.data.rrId;
				}
			}else{
				showConfirmDiv(3,'常规航线设置失败!','操作提示信息');
			}
           },"json");
		}
	});
}

/***
 * 2013-09-23 保存 或 提交 当前计划信息
 */
function ajaxSaveFlyPlan(planstate){

	if( !checkFlyPlanFormInfo() ){
		return ;
	}
	
	var jsondata = $("#form1").serializeArray();
	if(planstate == '12'){
		showConfirmDiv(1,'您确认计划信息填写完毕，提交申请至上级吗?','提示',function(choose1){
			if(choose1=="yes"){
	        	 $.post('flyPlanAction.ax?method=saveFlyPlanInfo&planstate=12', jsondata, function(data){
					if (data.data.returnflag == "true") {
						document.getElementById("planid").value = data.data.planid;
						document.getElementById("FPlanCode").value = data.data.FPlanCode;
						document.getElementById("FPlanName").value = data.data.FPlanName;
						
						// showConfirmDiv(2,'飞行计划信息保存并提交成功!','操作提示信息');
						document.getElementById('saveBut').disabled = "disabled";
						document.getElementById('commitBut').disabled = "disabled";
						showConfirmDiv(1,'飞行计划信息保存并提交成功，请点击确认返回管理页!','操作提示信息',function(choose2){
							if(choose2=="yes"){
								goBack();
							}
						});
		
					}else{
						showConfirmDiv(3,'飞行计划信息保存失败!','操作提示信息');
					}
			   	},"json");
			}
		});
	}else{
		$.post('flyPlanAction.ax?method=saveFlyPlanInfo&planstate=11', jsondata, function(data){
			if (data.data.returnflag == "true") {
				document.getElementById("planid").value = data.data.planid;
				document.getElementById("FPlanCode").value = data.data.FPlanCode;
				document.getElementById("FPlanName").value = data.data.FPlanName;
				if(data.data.tRsMainId != null){
					document.getElementById("rsIdMain").value = data.data.tRsMainId;
				}
				if(data.data.tRsBakId != null){
					document.getElementById("rsIdBak").value = data.data.tRsBakId;
				}
						
				showConfirmDiv(2,'飞行计划信息保存成功!','操作提示信息');
			}else{
				showConfirmDiv(3,'飞行计划信息保存失败!','操作提示信息');
			}
	   	},"json");
	}
    
}

/***
 * 2013-09-23 校验飞行计划必填信息项
 */
function checkFlyPlanFormInfo(){

	if(!checkElementFilled('adep','请选择设置起飞机场!','adepName')){
		return false;
	}
	if(!checkElementFilled('ades','请选择设置目的机场!','adesName')){
		return false;
	}
	
	var dates = document.getElementById("dates");
	var setal = document.getElementById("setal");
	var setd = document.getElementById("setd");
	var seta = document.getElementById("seta");
	if(!checkElementFilled('dates','请设置起飞日期!')){
		return false;
	}
	if(!isShortDate(dates, "-", "起飞日期", "2")) {
		return false;
	}
	
	if(!checkElementFilled('setd','请设置起飞时刻!')){
		return false;
	}
	if(!isTime(setd,'起飞时刻','2')){
		return false;
	}
	
	if(!checkElementFilled('setal','请设置降落日期!')){
		return false;
	}
	if(!isShortDate(setal, "-", "降落日期", "2")) {
		return false;
	}
	
	if(!checkElementFilled('seta','请设置降落时刻!')){
		return false;
	}
	if(!isTime(seta,'降落时刻','2')){
		return false;
	}
	a1 = new Date(Date.parse((dates.value+" "+setd.value+":00").replace(/-/g,'/')));
	b1 = new Date(Date.parse((setal.value+" "+seta.value+":00").replace(/-/g,'/')));
	if(b1.getTime()-a1.getTime() < 1){
		showConfirmDiv(0,'降落时间应晚于起飞时间!','提示信息');
		seta.focus();
        speciallyGoodEffect(seta);
        return false;
	}
	if(!checkElementFilled('chgt','请设置飞行高度!')){
		return false;
	}
	return true;
}

/***
 * 2013-09-27 点击添加备航线
 */
function addBakSkyWay() {

	if(!checkElementFilled('adep','请选择设置起飞机场!','adepName')){
		return false;
	}
	if(!checkElementFilled('ades','请选择设置目的机场!','adesName')){
		return false;
	}
  	if( parseInt(document.getElementById("linePointCount2").value) == 0){
  	
  		wayId='';
		if(document.getElementById('lineFlag').value == '1' ){  // 表示当前 lineTableDiv2 层表示的备航线
		    wayId='lineTable2';
		}else{
		    wayId='lineTable1';
		}
		
		tableObj = document.getElementById(wayId);  //获取表格相关属性
		
		// 删除两行
		tbRowsObj = tableObj.rows; // 表单所有行
    	tempLen=tbRowsObj.length;
		deleteLineRow(2,0,tableObj); //行编号从0开始
		
		newTr = tableObj.insertRow(1);  //表示加入到第几行，编号从0开始
		newTr.className="twoTdClass";
		addAdepOrAdesLineRow('1',wayId,newTr,1);  // 添加起飞机场行
		
		newTr = tableObj.insertRow(2);  //表示加入到第几行，编号从0开始
		newTr.className="oneTdClass";
		addAdepOrAdesLineRow('2',wayId,newTr,2);  // 添加目的机场行
		
		document.getElementById("linePointCount2").value = '2';
		
		// 进行一次航线显示切换，避免滚动条不能正常显示的问题。
	  	showSelectLineDiv('1');	
	  	showSelectLineDiv('0');	// 显示备航线层航线
	  	showGraphicPonit('0');
	  	reDrawLineSection('0');
  	
  	}else{
  	
  		// showConfirmDiv(4,'已存在备航线，如需重新添加请先删除!','警告信息');
  		// deleteSkyWay('0'); // 删除已存在的备用航线
  		showConfirmDiv(1,'已存在备航线，您确认删除重新添加吗?','确认提示',function(choose){
			if(choose=="yes"){
				deleteSkyWay('0'); 	// 删除已存在的备用航线
				addBakSkyWay(); 	// 回调该函数重新添加备航线。
	        	//showSelectLineDiv('0');	// 显示备航线层航线
			}else{
				showSelectLineDiv('0');	// 显示备航线层航线
			}		
		});
  	}
  	
}
	
/*
 * 2013-09-24 删除 整条航线 
 * wayType 表示需删除的航线的类型，
 		传值时 1 表示删除主航线
			  0 表示删除备航线
		不传表示删除当前展示航线【可能为主航线或备航线】
 */
function deleteSkyWay(wayType) {
	linePointCount1 = document.getElementById('linePointCount1').value;
	linePointCount2 = document.getElementById('linePointCount2').value;
	
	if(wayType==null || wayType==undefined){
	
		// 当前显示的航线即需要删除的航线【可能为主航线或备航线】
		if(document.getElementById('lineFlag').value == '1' ){  // 表示当前 lineTableDiv1 层表示的主航线
	 		if(document.getElementById('viewTypeFlag').value == '1'){ // 当前显示的为主航线
		    	wayId='lineTable1';
		    	wayType ='1';
			}else{
		    	wayId='lineTable2';
		    	wayType ='0';
			}
		}else{	  // 表示当前 lineTableDiv2 层表示的主航线
	 		if(document.getElementById('viewTypeFlag').value == '1'){ // 当前显示的为主航线
		    	wayId='lineTable2';
		    	wayType ='1';
			}else{
		    	wayId='lineTable1';
		    	wayType ='0';
			}
		}
		
	}else{
	// 方法中调用删除某条航线 。
		if(wayType =='1'){ // 要求删除主航线。
			if(document.getElementById('lineFlag').value == '1' ){  // 表示当前 lineTableDiv1 层表示的主航线
			    	wayId='lineTable1';
			}else{
			    	wayId='lineTable2';
			}
		}else{ // 要求删除备航线。
			if(document.getElementById('lineFlag').value == '1' ){  // 表示当前 lineTableDiv1 层表示的主航线
			    	wayId='lineTable2';
			}else{
			    	wayId='lineTable1';
			}
		}
	}
	
    var tableObj = document.getElementById(wayId);  //获取表格相关属性
    /*
    if(tableObj == null)
	{
		tableObj = parent.document.getElementById(wayId);
	}*/
    var tbRowsObj = tableObj.rows; // 表单所有行
    tempLen=tbRowsObj.length;
    inserti=1;
    
    if(wayType =='1'){
    
    	deleteLineRow(tempLen-1,1,tableObj); //行编号从0开始	
	    
	    adep = document.getElementById("adep").value; // 起飞机场
		ades = document.getElementById("ades").value; // 目的机场
	
    	if(adep != '' &&　ades　!= ''){
    		
	    	 // 重新插入目的机场行
		    newTr = tableObj.insertRow(2);  //表示加入到第几行，编号从0开始	
			newTr.className="oneTdClass";
			
			addAdepOrAdesLineRow('2',wayId,newTr,2);  // 添加目的机场行
			document.getElementById('linePointCount1').value = "2";
		    inserti=3;
    	}else{
    		tableObj.deleteRow(1); // 删除起飞机场所在行
    		document.getElementById('linePointCount1').value = "0";
    	}
	   
	}else{
	
		document.getElementById('linePointCount2').value = "0";
		deleteLineRow(tempLen-1,0,tableObj); //行编号从0开始	
	}
	addNewLineRow(inserti,7,tableObj); // 增加空白行
	
	
	// 进行一次航线显示切换，避免滚动条不能正常显示的问题。
	if(wayType =='1'){
		showSelectLineDiv('0');	
	  	showSelectLineDiv('1');	// 显示主航线层航线
	  	getDateDiff();
	}else{
		showSelectLineDiv('1');	
	  	showSelectLineDiv('0');	// 显示备航线层航线
	}
	showGraphicPonit(wayType);
	reDrawLineSection(wayType);  	
}

/***
 * 2013-09-26 通用方法，删除航线中连续行，从后往前删, 行编号从 0 开始 ，供删除 (startIndex - endIndex) 个行
 * startIndex : 从倒数第几行开始删除，包括指定行
 * endIndex : 删除到倒数第几行为止 ，不包括指定行， 从0 开始到第几行不删除
 * tableObj ：需删除的行的指定表格对象
 */
function deleteLineRow(startIndex,endIndex,tableObj) {
	for(i=startIndex; i>endIndex;i--){
    	tableObj.deleteRow(i);
    }
}	

/***
 * 2013-09-26 通用方法，为航线增加空白行，行编号从 0 开始 
 * startIndex : 从第几行开始增加行，包括该指定行
 * endIndex : 插入到第几行为止 ，不包括指定行号
 * tableObj ：需增加行的指定表格对象
 */
function addNewLineRow(startIndex,endIndex,tableObj) {
	// 插入空白行，设置的行号 1 为 白色，对应 twoTdClass
    for(i=startIndex; i<endIndex;i++){
    	newTr = tableObj.insertRow(i);  //表示加入到第几行，编号从0开始	
    	if(i%2 ==1){
    		newTr.className="twoTdClass";  // 标题占用一个行号
		}else{
			newTr.className="oneTdClass";
		}
		newTr.insertCell(0);
		newTr.insertCell(1);
		newTr.insertCell(2);
		newTr.insertCell(3);
		newTr.insertCell(4);
    }	
}
/***
 * 2013-09-27 增加起飞机场、目的机场行到航线指定行
 * addType : 1 表示添加的起飞机场节点
			 2 表示添加的是目的机场节点 
 * wayId : 需添加的行所在层id
 * newTr : 添加到层的行对象
 * indexValue ： 添加到第几行  ，即航线节点编号，从 1 开始
 */
function addAdepOrAdesLineRow(addType,wayId,newTr,indexValue) {
	addId = ''; 
	addName = ''; 
	addCode = ''; 
	addPos = ''; 
	if(addType == '1'){
		addId = document.getElementById("adep").value; // 起飞机场
		addName = document.getElementById("adepName").value; //  起飞机场
		addCode = document.getElementById("adepCode").value; //  起飞机场
		addPos = document.getElementById("adepPos").value; //  起飞机场
		
	}else{
		addId = document.getElementById("ades").value; // 目的机场
		addName = document.getElementById("adesName").value; // 目的机场
		addCode = document.getElementById("adesCode").value; // 目的机场
		addPos = document.getElementById("adesPos").value; // 目的机场
	}
	
	if(wayId == 'lineTable1'){
		// 如果是起飞机场，需增加按钮
		if(addType == '1'){
			addLinePointRow(newTr,'1', indexValue ,'1' ,addCode ,addName ,addPos ,'9');
		}else{
			addLinePointRow(newTr,'1', indexValue ,'0' ,addCode ,addName ,addPos ,'9');
		}
	}else{
		// 如果是起飞机场，需增加按钮
		if(addType == '1'){
			addLinePointRow(newTr,'0', indexValue ,'1' ,addCode ,addName ,addPos ,'9');
		}else{
			addLinePointRow(newTr,'0', indexValue ,'0' ,addCode ,addName ,addPos ,'9');
		}
	}
}	


/***
 * 2013-09-30 通用方法，增加航线节点内容。 
 * newTr ： 新增加的表格行对象
 * divType : 1 表示增加到 lineTable1
			 0 表示增加到 lineTable2
 * trIndex : 增加的航线节点编号
 * nodeType : 节点类型，1 表示非末节点
 					  0 表示 为目的机场节点
 * rsPCode ： 节点ICAO编号
 * rsPName ： 节点名称
 * rsPPos : 节点地理坐标
 * rsPType : 节点类型
 */
 
function addLinePointRow(newTr,divType,trIndex,nodeType,rsPCode,rsPName,rsPPos,rsPType) {

	rsPTypeName='';
	if(rsPType == '9') rsPTypeName='机场';
    if(rsPType == '18') rsPTypeName='导航台';
    if(rsPType == '20') rsPTypeName='地标点';
       		
	if(divType == '1'){
			tdNodes = newTr.insertCell(0);
			tdNodes.innerHTML= trIndex + '<input type="hidden" name="rsPNumMain" id="rsPNumMain' + trIndex +'" value="' + trIndex +'"/>';
			
			tdNodes = newTr.insertCell(1);
			tdNodes.innerHTML= rsPCode + '<input type="hidden" name="rsPCodeMain" id="rsPCodeMain' + trIndex +'" value="'+ rsPCode +'"/>';
			
			tdNodes = newTr.insertCell(2);
			tdNodes.innerHTML= rsPName +'<input type="hidden" name="rsPIdMain" id="rsPIdMain' + trIndex +'" value="" />'
					+'<input type="hidden" name="rsPNameMain" id="rsPNameMain' + trIndex +'" value="'+ rsPName +'"/>'
					+'<input type="hidden" name="rs_PPosMain" id="rs_PPosMain' + trIndex +'" value="'+ rsPPos +'"/>';
					
			tdNodes = newTr.insertCell(3);
			tdNodes.innerHTML= rsPTypeName +'<input type="hidden" name="rsPTypeMain" id="rsPTypeMain' + trIndex + '" value="' + rsPType +'"/>';
			
			// 操作
			tdNodes = newTr.insertCell(4);
			if(nodeType == '1'){
				tdNodes.innerHTML= '<div class="setupLineDivMeun" id="setupLineDivMeun' + trIndex + '"></div>'
					+'<div class="setupLineDiv" onClick="showFLinMeun(\'setupLineDivMeun' + trIndex +'\','+ trIndex +')"><img src="images/line_setup.png"/></div>';
			}
			
		}else{
		
			tdNodes = newTr.insertCell(0);
       		tdNodes.innerHTML= trIndex+'<input type="hidden" name="rsPNumBak" id="rsPNumBak' + trIndex + '" value="'+ trIndex +'"/>';
       		
       		tdNodes =newTr.insertCell(1);
      		tdNodes.innerHTML= rsPCode + '<input type="hidden" name="rsPCodeBak" id="rsPCodeBak' + trIndex + '" value="'+ rsPCode +'"/>';
	
       		tdNodes =newTr.insertCell(2);
       		tdNodes.innerHTML= rsPName +'<input type="hidden" name="rsPIdBak" id="rsPIdBak' + trIndex+ '" value="" />'
				+'<input type="hidden" name="rsPNameBak" id="rsPNameBak' + trIndex + '" value="'+ rsPName +'"/>'
				+'<input type="hidden" name="rs_PPosBak" id="rs_PPosBak' + trIndex + '" value="'+ rsPPos +'"/>';
				
       		// 类型
       		tdNodes =newTr.insertCell(3);
       		tdNodes.innerHTML= rsPTypeName +'<input type="hidden" name="rsPTypeBak" id="rsPTypeBak' + trIndex + '" value="'+ rsPType + '"/>';
       		
      		// 操作
      		tdNodes =newTr.insertCell(4);
      		if(nodeType == '1'){
      			tdNodes.innerHTML='<div class="setupLineDivMeun" id="setupLineDivMeunBak'+ trIndex +'"></div>'
      				+'<div class="setupLineDiv" onClick="showFLinMeun(\'setupLineDivMeunBak'+ trIndex +'\','+ trIndex +')"><img src="images/line_setup.png"/></div>';	
      		}
		}

}	

/***
 * 2013-10-10 设置起飞机场，目的机场时，添加起飞机场，目的机场节点行至主航线  ； 修改机场时，修改主、备航线中的对应机场节点信息 。
 * addType : 1 表示起飞机场节点
			 2 表示目的机场节点 
 */
function addAdepOrAdesToLine(addType){

	wayId1='lineTable1';
	wayId2='lineTable2';
	if(document.getElementById('lineFlag').value == '0' ){  // 表示当前 lineTableDiv2 层表示的主航线
	    wayId1='lineTable2';
	    wayId2='lineTable1';
	}
	
	tableObj1 = document.getElementById(wayId1);  //获取主航线表格对象
	rowIndex1 =1;
	if(addType == '2'){
		linePointCount1 = document.getElementById('linePointCount1').value;
		rowIndex1 = parseInt(linePointCount1) < 1 ? 2 : parseInt(linePointCount1);
		
	}
	
	linePointCount2 = document.getElementById('linePointCount2').value;
	rowIndex2 =1;
	if(parseInt(linePointCount2) < 1){
		rowIndex2 =0;
	}
	if(addType == '2'){
		rowIndex2 = parseInt(linePointCount2);
	}
	
	classId1 =tableObj1.rows[rowIndex1].className;
	tableObj1.deleteRow(rowIndex1); // 删除原有机场行
	trRow1 = tableObj1.insertRow(rowIndex1);  //重新插入机场行
	trRow1.className = classId1;
	addAdepOrAdesLineRow(addType ,wayId1 ,trRow1 ,rowIndex1);
	
	if(rowIndex2 > 0){
		tableObj2 = document.getElementById(wayId2);  //获取备航线表格对象
		classId2 =tableObj2.rows[rowIndex2].className;
		tableObj2.deleteRow(rowIndex2); // 删除原有机场行
		trRow2 = tableObj2.insertRow(rowIndex2);  //重新插入机场行
		trRow2.className = classId2;
		addAdepOrAdesLineRow(addType ,wayId2 ,trRow2 ,rowIndex2);
	}
	
	if(addType == '2'){
		document.getElementById('linePointCount1').value = rowIndex1;
	}
	showSelectLineDiv('1');	
}

/***
 * 2013-09-24 引用常用航线或建议航线
 * 		flag: 常用航线或建议航线标示 0表示常用航线，1表示建议航线
 */
function queryGeneralSkyWays(flag) {

	if(!checkElementFilled('adep','请选择设置起飞机场!','adepName')){
		return false;
	}
	if(!checkElementFilled('ades','请选择设置目的机场!','adesName')){
		return false;
	}
	adep=document.getElementById("adep");
	ades=document.getElementById("ades");
    // showIframeDiv(718,376,'flyPlanAction.do?method=searchGeneralSkyWayList&adep='+adep.value+'&ades='+ades.value,'iframe','选择常用航线',60);
    var jsondata = {
   		adep:adep.value,
   		ades:ades.value,
   		flag:flag
    };
    $("#jQueryCommonDiv").load('flyPlanAction.do?method=searchGeneralSkyWayList', jsondata, function(){
    	if(flag == '0') {
    		showLocalDiv(722,340,'选择常用航线','jQueryCommonDiv',60,'0');
    	}else {
    		showLocalDiv(722,340,'选择建议航线','jQueryCommonDiv',60,'0');
    	}
    });
    
}
/***
 * 2013-11-14 设置飞行日期时，默认设置降落日期
 */
//function setEmpSetal(){
//	dates = document.getElementById("dates");
//	setal = document.getElementById("setal");
//	
//	if(setal.value ==''){
//		setal.value = dates.value;
//		setformat(setal,outformat);
//	}
//}


/***
 * 2013-09-28 计算飞行时长
 * 2014-01-02 日修改计算，飞行时长： 主航线距离/飞行速度 ， 降落时间： 起飞时间+飞行时长
 */
function getDateDiff(){
	dates = document.getElementById("dates").value.trim();
	setd = document.getElementById("setd").value.trim();
	adep = document.getElementById("adep").value.trim();
	ades = document.getElementById("ades").value.trim();
	cspd = document.getElementById("cspd").value.trim();
	totalDis=0;
	 //  经度1
 	lng1 =0;
  	// 纬度1 
    lat1 =0;
     //  经度2
 	lng2 =0;
  	// 纬度2 
    lat2 =0;
    // showIframeDiv(718,376,'flyPlanAction.do?method=searchGeneralSkyWayList&adep='+adep.value+'&ades='+ades.value,'iframe','选择常用航线',60);
	if(dates !='' && setd !='' && adep !='' && ades !='' && cspd !='' ){
		wayDivType='1';
	 	if( document.getElementById('lineFlag').value == '0') { // 当前 lineTableDiv2 层表示的主航线
			    wayDivType='2';
		}
	 	lagIndex=0;
		if(wayDivType =='1'){
			
			for (i=0;i<document.form1.elements.length;i++){
			    if (document.form1.elements[i].name=="rs_PPosMain"){ 
			    	temp=getPointNumInfo(document.form1.elements[i].value);
			    	temp1=temp.indexOf(',');
			    	if(lagIndex < 1){
			    		//  经度2
					 	lng2 =temp.substr(1,temp1-1);
					  	// 纬度2 
					    lat2 =temp.substr(temp1+1,temp.length - temp1-2 );
			    	}else{
			    		 //  经度1
					 	lng1 =lng2;
					  	// 纬度1 
					    lat1 =lat2;
					    //  经度2
					 	lng2 =temp.substr(1,temp1-1);
					  	// 纬度2 
					    lat2 =temp.substr(temp1+1,temp.length - temp1-2 );
					    totalDis += getDistance(parseFloat(lat1),parseFloat(lng1),parseFloat(lat2),parseFloat(lng2)); // 航线节点至当前需插入的节点的距离
			    	}
			    	lagIndex++;
			    }
			}
		}else{
			for (i=0;i<document.form1.elements.length;i++){
			    if (document.form1.elements[i].name=="rs_PPosMain"){ 
			    	temp=getPointNumInfo(document.form1.elements[i].value);
			    	temp1=temp.indexOf(',');
			    	if(lagIndex < 1){
			    		//  经度2
					 	lng2 =temp.substr(1,temp1-1);
					  	// 纬度2 
					    lat2 =temp.substr(temp1+1,temp.length - temp1-2 );
			    	}else{
			    		 //  经度1
					 	lng1 =lng2;
					  	// 纬度1 
					    lat1 =lat2;
					    //  经度2
					 	lng2 =temp.substr(1,temp1-1);
					  	// 纬度2 
					    lat2 =temp.substr(temp1+1,temp.length - temp1-2 );
					    totalDis += getDistance(parseFloat(lat1),parseFloat(lng1),parseFloat(lat2),parseFloat(lng2)); // 航线节点至当前需插入的节点的距离
			    	}
			    	lagIndex++;
			    }
			}
		}
		var mill=totalDis*3600/(parseFloat(cspd))+59999; //  毫秒
		hours = Math.floor(mill/(1000*60*60));
//		// 计算除去相差的小时数后的剩余的毫秒数
		leaves = mill%(1000*60*60);
//		// 计算相差的分钟数
		minutes = Math.floor(leaves/(1000*60));
		if(minutes < 10) {
			minutes = "0" + minutes;
		}
		document.getElementById("eet").value=hours + ":" + minutes;
		var a1 = new Date(Date.parse((dates+" "+setd+":00").replace(/-/g,'/')));
		var b1 = new Date(a1.getTime()+mill);
		document.getElementById("setal").value=b1.getFullYear()+"-"+((b1.getMonth()+1) < 10 ? "0"+(b1.getMonth()+1):(b1.getMonth()+1) )+"-"+(b1.getDate() < 10 ? "0"+b1.getDate() : b1.getDate() );
		document.getElementById("seta").value= (b1.getHours() < 10 ? "0"+b1.getHours() : b1.getHours() ) +":"+(b1.getMinutes() < 10 ? "0"+b1.getMinutes() : b1.getMinutes() );
		
	}
	

	
//	var setd = document.getElementById("setd").value;
//	var seta = document.getElementById("seta").value;
//	document.getElementById("eet").value ="";
//	if(dates != null && dates !="" && setal != null && setal !=""
//		&& setd != null && setd !="" && seta != null && seta !="") {
//		
//		var a1 = new Date(Date.parse((dates+" "+setd+":00").replace(/-/g,'/')));
//		var b1 = new Date(Date.parse((setal+" "+seta+":00").replace(/-/g,'/')));
//		
//		// 计算相差的天数
//		//var aa = Math.floor((b1.getTime()-a1.getTime())/(1000*60*60*24));
//		//alert(aa);
//		// 计算相差的小时数
//		var hours = Math.floor((b1.getTime()-a1.getTime())/(1000*60*60));
//		// 计算除去相差的小时数后的剩余的毫秒数
//		var leaves = (b1.getTime()-a1.getTime())%(1000*60*60);
//		// 计算相差的分钟数
//		var minutes = Math.floor(leaves/(1000*60));
//		if(minutes < 10) {
//			minutes = "0" + minutes;
//		}
//		document.getElementById("eet").value=hours + ":" + minutes;
//	}
	
	
}

/***
 * 2013-10-11 校验是否已录入起飞机场
 * param elmId 待确认是否为空的表单元素的ID
 * param elmFocusId 待进行闪烁表单元素的ID
 * param alertText提示信息 
 */
function checkElementFilled(elmId ,alertText ,elmFocusId){
	elm=document.getElementById(elmId);
	if(elmFocusId==null || elmFocusId==undefined){
		return isFilled(elm, alertText);
	}else{
		if(!isFilled(elm, alertText)){
			elmFocus=document.getElementById(elmFocusId);
			elmFocus.focus();
            speciallyGoodEffect(elmFocus);
            return false;
		}
		return true ;
	}
}

// 返回处理
function goBack() {
	document.form1.action ='flyPlanAction.do?method=searchFlyPlanList';
	document.form1.submit();
}


var graphic1='';  	// 主航线地图显示对应的全局变量
var graphic2='';	// 备航线地图显示对应的全局变量

/***
 * 2013-10-12 根据得到的经纬度信息转换成地图可识别的数值经纬度信息
 * param rsPPos 传递的坐标
 * param formatType 返回的坐标格式， 1 表示返回坐标值中经度在前，2 表示返回的坐标值中纬度在前。 为空时默认为 1 经度在前
 */
function getPointNumInfo(rsPPos,formatType){
	pPos=rsPPos.split('E');
	temp1=parseFloat(pPos[0].substr(1,2));
	temp2=parseFloat(pPos[0].substr(3,2))/60;
	temp3=parseFloat(pPos[0].substr(5,2))/(60*60);
	temp4=parseFloat(pPos[0].substr(8,3))/(60*60*60);
	
	temp5=parseFloat(pPos[1].substr(0,3));
	temp6=parseFloat(pPos[1].substr(3,2))/60;
	temp7=parseFloat(pPos[1].substr(5,2))/(60*60);
	temp8=parseFloat(pPos[1].substr(8,3))/(60*60*60);
	
	wd=temp1+temp2+temp3+temp4;
	jd=temp5+temp6+temp7+temp8;
	if(formatType !=null && formatType == '2'){  // 三维使用格式，纬度在前
		return '['+wd+','+jd+']';
	}
	return '['+jd+','+wd+']'; // 二维使用格式，经度在前
} 

/***
 * 2013-10-12 地图上显示航线 
 * param wayType 需在显示的航线类型 
 * 				1 显示主航线
 * 				0 显示备航线
 */	
function showGraphicPonit(wayType) {
	
	skWay='';
 	wayDivType='1';
 	
 	if( (wayType =='1' && document.getElementById('lineFlag').value == '0')  // 显示主航线，当前 lineTableDiv2 层表示的主航线
 		|| (wayType =='0' && document.getElementById('lineFlag').value == '1') ){	// 或者 显示备航线，当前 lineTableDiv2 层表示的备航线
		    wayDivType='2';
	}
	if(wayDivType =='1'){
		for (i=0;i<document.form1.elements.length;i++){
		    if (document.form1.elements[i].name=="rs_PPosMain"){ 
		    		skWay=skWay+getPointNumInfo(document.form1.elements[i].value)+",";	
		    }
		}
	}else{
		for (i=0;i<document.form1.elements.length;i++){
		    if (document.form1.elements[i].name=="rs_PPosBak"){ 
		    		skWay=skWay+getPointNumInfo(document.form1.elements[i].value)+",";    	
		    }
		}
	
	}
	if(wayType =='1'){
		if(graphic1 !=''){
   			clearGraphic(graphic1);
   			graphic1 ='';
   		}
   		if(skWay !=''){
			graphic1=executeDrawLine('['+ skWay.substr(0,skWay.length-1) +']', 'STYLE_SOLID', '#FF00FF', 3,null,wayType);
		}
	}else{
		if(graphic2 !=''){
   			clearGraphic(graphic2);
   			graphic2 ='';
   		}
   		if(skWay !=''){
			graphic2=executeDrawLine('['+ skWay.substr(0,skWay.length-1) +']', 'STYLE_SOLID', '#0000FF', 3,null,wayType);
		}
	}
		
    
}	
//2013-11-12 日增加，修改飞行高度时，重画刨面图。
function changeFlyHight(){
	if(document.getElementById('viewTypeFlag').value == '1'){ // 当前显示的为主航线
		reDrawLineSection('1');
	}else{
		reDrawLineSection('0');
	}
}

//2013-11-13 日  重新刷新所有航线
function reFreshGisLine(){
	showGraphicPonit('1');
	showGraphicPonit('0');
	// 设置刨面图
	if(document.getElementById('viewTypeFlag').value == '1'){
		reDrawLineSection('1');
	}else{
		reDrawLineSection('0');
	}
	// 用于当起飞机场、目的机场没有变化 或设置备用机场时，不能正常显示航线等的切换设置。
	if($(".showMapDiv").width()== $(".fPlanRightBottom").width() - 20){
		//document.getElementById("showMapDiv").style.width = "844px";
		$(".showMapDiv").css("width",$(".fPlanRightBottom").width() - 21 +"px");
	}else{
		//document.getElementById("showMapDiv").style.width = "845px";
		$(".showMapDiv").css("width",$(".fPlanRightBottom").width() - 20 +"px");
	}
	map.resize(); 
	map.reposition();
}


//2013-11-13 日  重新刷新所有航线
function onLoadGisLine(){
	showGraphicPonit('1');
	showGraphicPonit('0');
	// 用于当起飞机场、目的机场没有变化 或设置备用机场时，不能正常显示航线等的切换设置。
	if($(".showMapDiv").width()== $(".fPlanRightBottom").width() - 20){
		//document.getElementById("showMapDiv").style.width = "844px";
		$(".showMapDiv").css("width",$(".fPlanRightBottom").width() - 21 +"px");
	}else{
		//document.getElementById("showMapDiv").style.width = "845px";
		$(".showMapDiv").css("width",$(".fPlanRightBottom").width() - 20 +"px");
	}
	map.resize(); 
	map.reposition();
}


// 2014-01-25 日添加，定义全局变量用于三维飞行
coordinateLine='';  // 三维飞行航线点坐标
flyHignt='';  // 三维飞行航线高度

/***
 * 2013-11-18 日增加，根据所选航线节点刷新刨面图
 * param wayType 需在显示的刨面图类型 
 * 				1 显示主航线刨面图
 * 				0 显示示备航线刨面图
 */	
function reDrawLineSection(wayType){
	removeAreaSpaceConflictData("rs_ob");
	flyHignt = parseFloat(document.getElementById('chgt').value);
	document.getElementById('bakHeight').value = '0';
	if(!flyHignt >0){
		flyHignt =1000;
	}
	skWay1='';
 	skWay2='';
 	coordinateLine1='';
 	coordinateLine2='';
 	var fPlanRightBottomContext = document.getElementById("fPlanRightBottomContext");
 	document.getElementById('chartNode').style.width = fPlanRightBottomContext.offsetWidth-25+"px";
 	document.getElementById('chartNode').style.height = fPlanRightBottomContext.offsetHeight +"px";
 	for (i=0;i<document.form1.elements.length;i++){
	    if (document.form1.elements[i].name=="rs_PPosBak"){ 
	    		skWay1=skWay1+getPointNumInfo(document.form1.elements[i].value,'1')+",";   
	    		coordinateLine1 = coordinateLine1 + getPointNumInfo(document.form1.elements[i].value,'2')+",";  
	    }
	    if (document.form1.elements[i].name=="rs_PPosMain"){
	    		skWay2=skWay2+getPointNumInfo(document.form1.elements[i].value,'1')+",";	
	    		coordinateLine2 =coordinateLine2 + getPointNumInfo(document.form1.elements[i].value,'2')+",";	
	    }
	}
 	if( (wayType =='1' && document.getElementById('lineFlag').value == '0')  // 显示主航线刨面图，当前 lineTableDiv2 层表示的主航线
 		|| (wayType =='0' && document.getElementById('lineFlag').value == '1') ){	// 或者 显示备航线刨面图，当前 lineTableDiv2 层表示的备航线
 		
 		if(skWay2 !=''){
			gis_drawSection('['+ skWay2.substr(0,skWay2.length-1) +']', flyHignt,0); // 计算skWay2 对应的高度报警值
		}
		
		coordinateLine = coordinateLine1;
		if(skWay1 !=''){
			document.getElementById('chartNode').style.display='block';
			gis_drawSection('['+ skWay1.substr(0,skWay1.length-1) +']', flyHignt,1); // 画刨面图，并计算skWay1 对应的高度报警值
		}else{
			document.getElementById('chartNode').style.display='none';
		}
		
	}else{
		if(skWay1 !=''){
			gis_drawSection('['+ skWay1.substr(0,skWay1.length-1) +']', flyHignt,0);
		}
		
		coordinateLine = coordinateLine2;
		if(skWay2 !=''){
			document.getElementById('chartNode').style.display='block';
			gis_drawSection('['+ skWay2.substr(0,skWay2.length-1) +']', flyHignt,1);
		}else{
			document.getElementById('chartNode').style.display='none';
		}
	}
}	


/***
 * 2013-12-20 根据机场ICAO获取机场的最新Metar气象信息
 */
function showWeatherInfo(icaoCode){

	var jsondata = {
   		//adep:adep.value,
   		icaoCode:icaoCode
    };
	$.post('flyPlanAction.ax?method=getAdMetarInfo', jsondata, function(data){
		if (data.data.returnflag == "true") {
			if (data.data.wmDesc != "") {
				metarInfo=data.data.wmDesc;
				if (data.data.wmDecode != "null" ) {
					metarInfo +="<br/>"+data.data.wmDecode;
				}
				showConfirmDiv(5,metarInfo,'机场Metar信息',null,'500','160');
			}else{
				showConfirmDiv(0,'未找到该机场的Metar气象信息!','机场Metar信息');
			}
		}else{
			showConfirmDiv(0,'未找到该机场的Metar气象信息!','机场Metar信息');
		}
   	},"json");
    
}


// 根据code在地图查找点
function getPointInfoByCode(pcode) {
	if( pcode !=''){
		executeAPQuery(pcode);
	}
}

//  清空控件值
function cleanValueById(param) {
	document.getElementById(param).value = "";
	if( param == "altn1" ){
		document.getElementById("altn1Code").value = "";
		reFreshGisLine();
	}
}
