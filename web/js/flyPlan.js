/***
 * ��ʾ�����ı߿�Ч��
 * @param obj��ǰ�����Ķ���
 * @param flag �����ı����־��Ϊ1ʱ��ʾΪ�����ı���
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
 * ȥ�������ı߿�Ч��
 * @param obj��ǰ�����Ķ���
 * @param flag �����ı����־��Ϊ1ʱ��ʾΪ�����ı���
 */
function hideBorder(obj,flag){
	if(flag == '1'){
		$(obj).addClass('fPlanTextareaNoBorder');
	}else{
		$(obj).addClass('textNoBorder');
	}	
}

/**
 * ������л����鿴��ͼģʽ
 * @param bImgDiv ��Ҫ��ʾ��div
 * @param sImg ��Ҫ�����ص�div
 * @param bImg ��Ҫ��ʾ��img����
 * @param obj ��ǰ����
 * @param imgSrc ��Ҫ��ʾdiv��ͼƬ·��
 * @param otherDivId ��Ҫ��ʾ����div ������
 * @param isShowOverflow �Ƿ���ʾ��������������򲻿���ͼƬ��С 1��ʾ��Ĭ�ϲ���ʾ
 * @param imgFlag ��ǰͼƬ�����
 */
function showBigImgage(bImgDivId,sImgId,bImgId,obj,imgSrc,otherDivId,isShowOverflow,imgFlag){
	document.getElementById(sImgId).style.display="none";
	var bImgDiv = document.getElementById(bImgDivId);
	bImgDiv.style.display="block";
	document.getElementById("imgFlag").value = imgFlag;

	var bImg = document.getElementById(bImgId);
	//�ж��ǵ��Ӳ���������ͨ�ķŴ����
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
 * �رղ鿴��ͼģʽ
 * @param bImgDiv ��ǰ��ʾ��div����Ҫ����
 * @param sImg �����ǵ�div����Ҫ��ʾ
 */
function closeBigImgage(bImgDiv,sImg){
	document.getElementById(bImgDiv).style.display="none";
	document.getElementById(sImg).style.display="block";
	document.getElementById("bImgMessage").style.display="none";
}

/*
 * �л���Ƭ
 * @param flag 1��ʾ��һ��
 *             2��ʾ��һ��
 * @param imgDiv Ҫ������img Id�б�
 */
function changeImg(flag,imgId){
	var imgFlag = document.getElementById("imgFlag").value;
	var imgNamesId = "";
	//��ͬ����ʾ����ʾ��ͬ��ͼƬ
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
		
		//ȡ�õ�ǰ��ʾ��ͼƬ����
		if(srcName == imgNames[i]){
			//�ж�����һ�Ż�����һ��
			if("1"==flag){
				//�ж��Ƿ��ǵ�һ�ţ���һ�Ų�������һ�Ų���
				if(i==0){
					document.getElementById("bImgMessage").style.display="none";
					document.getElementById("bImgMessage").innerHTML="�Ѿ��ǵ�һ��ͼƬ��Ŷ...."
					return;
				}else{
					newImgName = imgNames[i - 1];
				}
			}else{
				//�ж��Ƿ�Ϊ���һ�ţ����һ����Ƭ��������һ�Ų���
				if(i==imgNames.length-1){
					document.getElementById("bImgMessage").style.display="block";
					document.getElementById("bImgMessage").innerHTML="�Ѿ������һ��ͼƬ��Ŷ...."
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

//ȫ�ֶ������ڴ洢��ǰ�������кţ�  �����߽ڵ�ı�ţ��� 1 ��ʼ �� ��ڵ�ǰ�������ֵһ�¡�
var activeRow;

/**
 * ��ʾ�������߸����˵�
 */
function showFLinMeun(objId,trIndex){
	var setupLineDivMeun = document.getElementById(objId);
	if(setupLineDivMeun==null){
		return false;
	}
	
	//��ȡ��ǰ�������е��к�
	// activeRow =document.activeElement.parentNode.parentNode.rowIndex + 1;
	activeRow = trIndex ; // �����߽ڵ�ı�ţ��� 1 ��ʼ �� ��ڵ�ǰ�������ֵһ�¡�
	
	//������ť
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
	
	//��һ��ʱֻ��ʾ�Ӻ�
	if(1 == activeRow){
		setupLineDivMeun.appendChild(add);
		setupLineDivMeun.style.textAlingn = "left";
	}
	/**
	else if(7 == activeRow){
		//���һ�в����κβ���
		return false;
	}
	**/
	else{
				
		setupLineDivMeun.appendChild(add);	
		setupLineDivMeun.appendChild(deduct);
		setupLineDivMeun.appendChild(down);
		setupLineDivMeun.appendChild(up);
	}
	//��ʾ�����˵�div
	setupLineDivMeun.style.display="block";
	
	//�����㶨λ�������˵���
	setupLineDivMeun.focus();
	
	//�����ڽ����ƿ���˵���ʧ�¼�
	setupLineDivMeun.onblur=hiddenFLinMeun;
	
}

/*
 * �����ƿ��󣬸����˵���ʧ�¼�
 */
function hiddenFLinMeun(){
	this.style.display="none";
	//���div�����д���������
	this.innerHTML="";
}

/**
 * ���ߵ���Ӷ������򿪽ڵ��ѯѡ��ҳ��
 */
function addClick(){
	// showIframeDiv(718,427,'nv.html','iframe','��Ӻ��ߵ�',50);
	var jsondata = {
   		//adep:adep.value,
   		//ades:ades.value
    };
    
    $("#jQueryCommonDiv").load('adAction.ax?op=getNVOrFFXList', jsondata, function(){
    	showLocalDiv(718,425,'��Ӻ��ߵ�','jQueryCommonDiv',60,'0');
    });
}

/**
 *  2013-09-29
 *	�����ӵ���̨���ߵر��������
 *  arr : �����ӵĵ���̨�ڵ�����
 */
function setNVInfo(arr){
	
	// ��ǰ��ʾ�ĺ��߼���Ҫ���ӽڵ�ĺ��ߡ�����Ϊ�����߻򱸺��ߡ�
	wayId='lineTable1';	// Ĭ�ϱ�ʾ������ lineTableDiv1��
	wayType ='1'; // Ĭ�ϱ�ʾ������Ϊ�����ߡ�
	if(document.getElementById('lineFlag').value == '1' ){  // ��ʾ��ǰ lineTableDiv1 ���ʾ��������
 		if(document.getElementById('viewTypeFlag').value == '0'){ // ��ǰ��ʾ��Ϊ�����ߣ�����ǰ������Ϊ������
	    	wayId='lineTable2';
	    	wayType ='0';
		}
	}else{	  // ��ʾ��ǰ lineTableDiv2 ���ʾ��������
 		if(document.getElementById('viewTypeFlag').value == '1'){ // ��ǰ��ʾ��Ϊ�����ߣ�����ǰ������Ϊ������
	    	wayId='lineTable2';
		}else{
	    	wayType ='0';
		}
	}
	
	tableObj = document.getElementById(wayId);  //��ȡ����������
	
	linePointLength = 0 ;
	if( wayType == '1'){
    	linePointLength = $('#linePointCount1').val();
    }else{
    	linePointLength = $('#linePointCount2').val();
    }
    
	resetPointRowInfo(wayId,activeRow+1,wayType,arr.length); //  �޸Ĳ�����к�ĸ��кŵ���Ϣ��
	
	for(var i = 0; i < arr.length; i++) {
		
		var classType = "";
		if((activeRow+i) %2 ==0) {
			classType = "twoTdClass";
		}else {
			classType = "oneTdClass";
		}
		
		var indexValue = activeRow + i+1;
		
		newTr = tableObj.insertRow(indexValue);  //��ʾ���뵽�ڼ��У���Ŵ�0��ʼ
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
    
    // ɾ������Ŀհ���
	if(parseInt(linePointLength) + arr.length <6 ){
		deleteLineRow( tableObj.rows.length-1 , 6 ,tableObj);
	}else{
		deleteLineRow( tableObj.rows.length-1 , parseInt(linePointLength) + arr.length  ,tableObj);
	}
	
	// ����ȫ���в����б�����ʹ֮�����˳�������������
	activeRow = activeRow+arr.length ;
	
	// ����һ�κ�����ʾ�л����������������������ʾ�����⡣
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

// ѡ������ص����������÷��صĸ���Ԫ��ֵ
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
 *	�����ͼ�ϵ���Ϊ���ߵ���� ��ӵ���̨���ߵر��������
 *  arr : �����ӵĽڵ�
 */
function gisWriteNVInfo(arr){

	if(!checkElementFilled('adep','��ѡ��������ɻ���!','adepName')){
		return false;
	}
	if(!checkElementFilled('ades','��ѡ������Ŀ�Ļ���!','adesName')){
		return false;
	}
	
	// ��ǰ��ʾ�ĺ��߼���Ҫ���ӽڵ�ĺ��ߡ�����Ϊ�����߻򱸺��ߡ�
	wayType ='1'; // Ĭ�ϱ�ʾ������Ϊ�����ߡ�
	if(document.getElementById('viewTypeFlag').value == '0'){ // ��ǰ��ʾ��Ϊ�����ߣ�����ǰ������Ϊ������
    	wayType ='0';
	}
 	wayDivType='1';
 	if( (wayType =='1' && document.getElementById('lineFlag').value == '0')  // ��ʾ�����ߣ���ǰ lineTableDiv2 ���ʾ��������
 		|| (wayType =='0' && document.getElementById('lineFlag').value == '1') ){	// ���� ��ʾ�����ߣ���ǰ lineTableDiv2 ���ʾ�ı�����
		    wayDivType='2';
	}
 	
 	
	 	
	
	// linePointLength = 0 ;
	
    // ��ӵĵ�����
    addemp=getPointNumInfo(arr[0].nvPos);
    temp1=addemp.indexOf(',');
   	//  ����1
 	lng1 =addemp.substr(1,temp1-1);
  	// γ��1 
    lat1 =addemp.substr(temp1+1,addemp.length - temp1-2 );
    
    // ��ǰ�������ĵ������
   	//  ����2
 	lng2 =0;
  	// γ��2
    lat2 =0;
    
 	// ��һ���������ĵ������
   	//  ����3
 	lng3 =0;
  	// γ��3
    lat3 =0;
    
    // ��ɻ����������
   	//  ����4
 	lng4 =0;
  	// γ��4
    lat4 =0;
    
    // ���Ƚϵ������
    temp='';
    
    // ��λ����㣺 �ݶ����뵽��������Ľڵ�֮��Ŀ�Ļ������������Ӧ����Ŀ�Ļ���ǰ��
    pointIndex=0; // ��¼��˳���
    
    pointBreakIndex=0; // ��¼��ֹͣ�����
    dis1=0;  	// ��ǰ�������
    dis2=0;	 	// ��ǰ�������̾���
    dis3=0;		// ��ǰ����ĵ����ڶ��̵ľ���
    if(wayDivType =='1'){
		for (i=0;i<document.form1.elements.length;i++){
		
		    if (document.form1.elements[i].name=="rs_PPosMain"){ 
		    	pointIndex++ ;
		    	pointBreakIndex++ ;
		    	temp=getPointNumInfo(document.form1.elements[i].value);
		    	temp1=temp.indexOf(',');
			    //  ����2
			 	lng2 =temp.substr(1,temp1-1);
			  	// γ��2 
			    lat2 =temp.substr(temp1+1,temp.length - temp1-2 );
			    
			    if(pointIndex == 1){
				     //  ����4
				 	lng4 =temp.substr(1,temp1-1);
				  	// γ��4 
				    lat4 =temp.substr(temp1+1,temp.length - temp1-2 );
			    }
			    dis1 = getDistance(parseFloat(lat1),parseFloat(lng1),parseFloat(lat2),parseFloat(lng2)); // ���߽ڵ�����ǰ�����Ľڵ�ľ���
			    
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
		    //  ����3
		    lng3 =temp.substr(1,temp1-1);
		  	// γ��3 
		    lat3 =temp.substr(temp1+1,temp.length - temp1-2 );
		   
		}
		
	}else{
		for (i=0;i<document.form1.elements.length;i++){
		    if (document.form1.elements[i].name=="rs_PPosBak"){
		    	pointIndex++ ;
		    	pointBreakIndex++ ;
		    	temp=getPointNumInfo(document.form1.elements[i].value);
		    	 temp1=temp.indexOf(',');
			    //  ����2
			 	lng2 =temp.substr(1,temp1-1);
			  	// γ��2 
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
		     //  ����3
		    lng3 =temp.substr(1,temp1-1);
		  	// γ��3 
		    lat3 =temp.substr(temp1+1,temp.length - temp1-2 );
		}
	
	}
	
    // ����ȫ�ֱ�������ʾ���ӵĽڵ����ĸ�λ�ã�ʹ֮�����˳������������ӣ�Ĭ������Ϊ�ڵ����˳��Ϊ����ɻ�����ӵ�Ŀ�Ļ���
    
    // ��λ����㣺���뵽��������Ľڵ�֮ǰ��֮��Ĭ���ݶ�Ϊ֮��
   	//��������뿪ʼ����֮ǰ�ĵ�����׽ڵ�Զ�������ڵ㣬����뵽��һ���ڵ�֮ǰ��
   	if(getDistance(parseFloat(lat4),parseFloat(lng4),parseFloat(lat1),parseFloat(lng1)) < getDistance(parseFloat(lat4),parseFloat(lng4),parseFloat(lat3),parseFloat(lng3)) ){
   		pointIndex = pointIndex -1;
   	}
    //��Ŀ�Ļ���������������Ŀ�Ļ���ǰ��
    if( wayType == '1' &&��pointIndex��>= parseInt( $('#linePointCount1').val() )��){
    	pointIndex = parseInt( $('#linePointCount1').val() );
    }else if( wayType == '0' &&��pointIndex��>= parseInt( $('#linePointCount2').val() )��){
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
 *	�����ͼ���������֮��ľ���
 */
var R = 6378137.0  // ��λ�� ����뾶
var fl = 1/298.257;
/**
 * lat1 γ��1
 * lng1 ����1
 * lat2 γ��2
 * lng2 ����2
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
//���ݽǶȷ��ػ���
function getRad(d){
	return d*Math.PI/180.0;
}

/*
 * 2013-09-30 
 * ���Ӻ��߽ڵ�ʱ�޸��кŵ���Ϣ
 * wayId �� �����ı���ӦID 
 * startIndex : �������кŵ���ʼ�к� // �� 0 ��ʼ��������ͷ�� ����ӦΪ�ڵ�ı��
 * wayType : 1 ������ 
			 0 ������ 
 * increment : �к����� �� Ĭ��Ϊ�� 1 ��
 */
function resetPointRowInfo(wayId,startIndex,wayType,increment) {

    var tableObj = document.getElementById(wayId);  //��ȡ����������
    
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
	      			updateRowInfo(trNode, '1', (i+increment) ,'1'); 	// ���÷�Ŀ�Ļ����ڵ�	
	       		}else{
	       			updateRowInfo(trNode, '1', (i+increment) ,'0'); 	// ����Ŀ�Ļ����ڵ�	
	       		}
			}else{
			
				// ��� 
	        	tdNodes[0].innerHTML= (i+increment) +'<input type="hidden" name="rsPNumBak" id="rsPNumBak' + (i+increment) + '" value="'+ (i+increment) +'"/>';
	        	
	        	// ����
	      		if(i < linePointLength){
	      			updateRowInfo(trNode, '0', (i+increment) ,'1'); 	// ���÷�Ŀ�Ļ����ڵ�	
	      		}else{
	      			updateRowInfo(trNode, '0', (i+increment) ,'0'); 	// ����Ŀ�Ļ����ڵ�	
	      		}
			}
		}
    }
}

/*
 * 2013-09-30 	ͨ�÷��������������б�ż����ݲ�����Ϣ
 * trNode �� �����õı���ж���
 * divType : 1 ��ʾ�޸ĵ�Ϊ lineTable1
			 0 ��ʾ�޸ĵ�Ϊ lineTable2
 * newTrIndex : ���ú��߽ڵ���
 * nodeType : �ڵ����ͣ�0 ��ʾ ΪĿ�Ļ����ڵ�
					  1 ��ʾ��Ŀ�Ļ����ڵ� ������ɻ����ڵ���м�ڵ㡿
 */
 
function updateRowInfo(trNode,divType,newTrIndex,nodeType) {

	tdNodes = trNode.cells;
    if(divType == '1'){
		// ������� 
       	tdNodes[0].innerHTML= newTrIndex +'<input type="hidden" name="rsPNumMain" id="rsPNumMain' + newTrIndex + '" value="'+ newTrIndex +'"/>'; 
   		// ���ò���
    	if(nodeType == '1'){
   			tdNodes[4].innerHTML='<div class="setupLineDivMeun" id="setupLineDivMeun'+ newTrIndex +'"></div>'
   				+'<div class="setupLineDiv" onClick="showFLinMeun(\'setupLineDivMeun'+ newTrIndex +'\','+ newTrIndex +')"><img src="images/line_setup.png"/></div>';	
    	}
	}else{
		// ������� 
        tdNodes[0].innerHTML= newTrIndex +'<input type="hidden" name="rsPNumBak" id="rsPNumBak' + newTrIndex + '" value="'+ newTrIndex +'"/>';
       	// ���ò���
     	if(nodeType == '1'){
      		tdNodes[4].innerHTML='<div class="setupLineDivMeun" id="setupLineDivMeunBak'+ newTrIndex +'"></div>'
				+'<div class="setupLineDiv" onClick="showFLinMeun(\'setupLineDivMeunBak'+ newTrIndex +'\','+ newTrIndex +')"><img src="images/line_setup.png"/></div>';	
     	}
	}
}

 /***
 * ���ߵ�ɾ������
 * activeRow : ȫ�ֱ�������¼��ǰ���Ĳ����У��������⣬�� 1 ��ʼ��š�
 * wayType �� ��ʾ�����ĺ������ͣ� 1 ��ʾ������
 							   0 ������ 
 * 2013-09-27 
 */
function deductClick(){
	
	// ��ǰ��ʾ�ĺ��߼���Ҫɾ���ĺ��ߡ�����Ϊ�����߻򱸺��ߡ�
	wayId='lineTable1';	// Ĭ�ϱ�ʾ������ lineTableDiv1��
	wayType ='1'; // Ĭ�ϱ�ʾ������Ϊ�����ߡ�
	if(document.getElementById('lineFlag').value == '1' ){  // ��ʾ��ǰ lineTableDiv1 ���ʾ��������
 		if(document.getElementById('viewTypeFlag').value == '0'){ // ��ǰ��ʾ��Ϊ�����ߣ�����ǰ������Ϊ������
	    	wayId='lineTable2';
	    	wayType ='0';
		}
	}else{	  // ��ʾ��ǰ lineTableDiv2 ���ʾ��������
 		if(document.getElementById('viewTypeFlag').value == '1'){ // ��ǰ��ʾ��Ϊ�����ߣ�����ǰ������Ϊ������
	    	wayId='lineTable2';
		}else{
	    	wayType ='0';
		}
	}
	
	// ɾ�����ߵĶ�Ӧ�ڵ㡣
	tableObj = document.getElementById(wayId);  //��ȡ����������
	tableObj.deleteRow(activeRow);	//�б�Ŵ�0��ʼ
	
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
       			updateRowInfo(trNode, '1', i ,'1'); 	// ���÷�Ŀ�Ļ����ڵ�	
       		}else{
       			updateRowInfo(trNode, '1', i ,'0'); 	// ����Ŀ�Ļ����ڵ�	
       		}	
       							
		}else{
		
			if(i<temp-1){
       			updateRowInfo(trNode, '0', i ,'1'); 	// ���÷�Ŀ�Ļ����ڵ�	
       		}else{
       			updateRowInfo(trNode, '0', i ,'0'); 	// ����Ŀ�Ļ����ڵ�	
       		}	
		}
    }
	
	if( wayType =='1' ){
		document.getElementById('linePointCount1').value =temp-1 ;
		if( temp < 7 ){
			addNewLineRow(temp, temp+1 ,tableObj); // ����հ��� 
		}
		
	}else{
		document.getElementById('linePointCount2').value =temp-1 ;
		if( temp < 7 ){
			addNewLineRow(temp, temp+1 ,tableObj); // ����հ��� 
		}
		
	}
	showGraphicPonit(wayType);
	reDrawLineSection(wayType);
	getDateDiff();
}

/**
 * 2013-09-28 ���ߵ������ƶ�����
 * activeRow : ȫ�ֱ�������¼��ǰ���Ĳ����У��������⣬�� 1 ��ʼ��š�
 */
function downClick(){

	lineLength=0 ; // �������ߵĽڵ���
	// ��ǰ��ʾ�ĺ��߼���Ҫɾ���ĺ��ߡ�����Ϊ�����߻򱸺��ߡ�
	if(document.getElementById('lineFlag').value == '1' ){  // ��ʾ��ǰ lineTableDiv1 ���ʾ��������
 		if(document.getElementById('viewTypeFlag').value == '1'){ // ��ǰ��ʾ��Ϊ������
	    	lineLength = parseInt(document.getElementById('linePointCount1').value);
		}else{
	    	lineLength = parseInt(document.getElementById('linePointCount2').value);
		}
	}else{	  // ��ʾ��ǰ lineTableDiv2 ���ʾ��������
 		if(document.getElementById('viewTypeFlag').value == '1'){ // ��ǰ��ʾ��Ϊ������
	    	lineLength = parseInt(document.getElementById('linePointCount1').value);
		}else{
	    	lineLength = parseInt(document.getElementById('linePointCount2').value);
		}
	}
	if(activeRow == lineLength-1 ){
 		showConfirmDiv(4,'�Բ���ĩ�ڵ����Ϊ������������������ƶ�!','������Ϣ');
 		return;
 	}
 	
	activeRow=activeRow+1;	
	upClick('down'); 
	   	
}

/**
 * 2013-09-28 ���ߵ������ƶ�����
 * activeRow : ȫ�ֱ�������¼��ǰ���Ĳ����У��������⣬�� 1 ��ʼ��š�
 * opFlag : down ��ʾִ�е������Ʋ���
 */
function upClick(opFlag){

	if(activeRow ==2 ){
 		showConfirmDiv(4,'�Բ����׽ڵ����Ϊ��ɻ��������������ƶ�!','������Ϣ');
 		return;
 	}
	
	// ��ǰ��ʾ�ĺ��߼���Ҫ���Ƶĺ��ߡ�����Ϊ�����߻򱸺��ߡ�
	wayId='lineTable1';	// Ĭ�ϱ�ʾ������ lineTableDiv1��
	if(document.getElementById('lineFlag').value == '1' &&  document.getElementById('viewTypeFlag').value == '0' 	// ��ʾ��ǰ lineTableDiv1 ���ʾ�������ߣ���ǰ��ʾ��Ϊ�����ߣ�����ǰ������Ϊ������
		|| document.getElementById('lineFlag').value == '0' &&  document.getElementById('viewTypeFlag').value == '1'){  // ��ʾ��ǰ ��ʾ��ǰ lineTableDiv2 ���ʾ�������ߣ���ǰ��ʾ��Ϊ�����ߣ�����ǰ������Ϊ������  
	    	wayId='lineTable2';
	}
	
	tableObj = document.getElementById(wayId);  //��ȡ���ƺ��ߵı���������
	tbRowsObj = tableObj.rows; // ��������
	
	tr1=tableObj.rows[activeRow]; // ִ�������ƶ�����
 	tr2=tableObj.rows[activeRow-1]; // �����Ƶ���
 		
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
 	
 	if(wayId == 'lineTable1'){ // ���� lineTableDiv1 ��
 	
 		// ���Ƶ�����������
       	updateRowInfo(tr1, '1', (activeRow-1) ,'1'); 	// ���÷�Ŀ�Ļ����ڵ�	
       		
 		// ���Ƶ�����������
 		updateRowInfo(tr2, '1', activeRow ,'1'); 	// ���÷�Ŀ�Ļ����ڵ�	
							
	}else{
		// ���Ƶ�����������
		updateRowInfo(tr1, '0', (activeRow-1) ,'1'); 	// ���÷�Ŀ�Ļ����ڵ�	
		
		// ���Ƶ�����������
		updateRowInfo(tr2, '0', activeRow ,'1'); 	// ���÷�Ŀ�Ļ����ڵ�	
		
	}
	
    //tr1.swapNode(tr2);      // �˷�ʽ�Ի���������á�
    cloneTr=tr1.cloneNode(true);  
    tr2.parentNode.replaceChild(cloneTr,tr2);
    tr1.parentNode.replaceChild(tr2,tr1);
    
    showGraphicPonit(document.getElementById('viewTypeFlag').value);
    reDrawLineSection(document.getElementById('viewTypeFlag').value);
	getDateDiff();
}



/***
 * 2013-09-23 ��� ��ʾ���������ߵ���ʾ����л�
 * @param rsType ��ǰ����ʾ�ĺ�������: 1 ��ʾ��Ҫ��ʾ������
								   0 ��ʾ��Ҫ��ʾ������
 */
function showSelectLineDiv(rsType){
 	if(rsType =='1'){ 
 		document.getElementById('viewTypeFlag').value = '1';
 		document.getElementById("isMainLine").innerHTML='������';
 		document.getElementById('mainBut').className="LineButton mainLineButton";
 		document.getElementById('bakBut').className="LineButton prepareLinButton";
 		if(document.getElementById('lineFlag').value == '1'){ // lineTableDiv1 ��ʾ������
	    	document.getElementById('lineTableDiv1').style.display = 'block';
	    	document.getElementById('lineTableDiv2').style.display = 'none';
		}else{
	    	document.getElementById('lineTableDiv1').style.display = 'none';
	    	document.getElementById('lineTableDiv2').style.display = 'block';
		}
	}else{
		document.getElementById('viewTypeFlag').value = '0';
		document.getElementById("isMainLine").innerHTML='������';
		document.getElementById('mainBut').className="LineButton prepareLinButton";
 		document.getElementById('bakBut').className="LineButton mainLineButton";
 		if(document.getElementById('lineFlag').value == '0'){ // lineTableDiv1 ��ʾ������
	    	document.getElementById('lineTableDiv1').style.display = 'block';
	    	document.getElementById('lineTableDiv2').style.display = 'none';
		}else{
	    	document.getElementById('lineTableDiv1').style.display = 'none';
	    	document.getElementById('lineTableDiv2').style.display = 'block';
		}
	}
}
/***
 * 2013-11-18 �����ӣ������ʾ����������ʱ��ˢ������ͼ
 * param wayType ������ʾ������ͼ���� 
 * 				1 ��ʾ����������ͼ
 * 				0 ��ʾʾ����������ͼ
 */	
function showSelectLineDraw(wayType){
	if(document.getElementById('viewTypeFlag').value != wayType){
		reDrawLineSection(wayType);
	}
}

/***
 * 2013-09-23 ���������ߵĺ��߽ڵ����ݵ��л�
 */
 function changeSelectLineType(){
 
 	// �������������ߵĽڵ�����
 	linePointCount1 = document.getElementById('linePointCount1').value;
 	linePointCount2 = document.getElementById('linePointCount2').value;
 	
 	if(parseInt(linePointCount2) == 0){
 		// alert('�Բ��𣬵�ǰδ���ñ����ߣ������л�Ϊ������!');
 		showConfirmDiv(4,'�Բ��𣬵�ǰδ���ñ����ߣ������л�Ϊ������!','������Ϣ');
 		return ;
 	}
 	document.getElementById('linePointCount1').value = linePointCount2;
 	document.getElementById('linePointCount2').value = linePointCount1;
 	
 	if(document.getElementById('lineFlag').value == '1' ){  // ��ʾ��ǰ lineTableDiv1 ���ʾ��������
 		document.getElementById('lineFlag').value = '0';   // �л�Ϊ lineTableDiv2 ���ʾ������
 		if(document.getElementById('viewTypeFlag').value == '1'){ // ��ǰ��Ҫ��ʾ��Ϊ�����ߣ��л�Ϊ��ʾ lineTableDiv2 ��
	    	document.getElementById('lineTableDiv1').style.display = 'none';
	    	document.getElementById('lineTableDiv2').style.display = 'block';
		}else{
	    	document.getElementById('lineTableDiv1').style.display = 'block';
	    	document.getElementById('lineTableDiv2').style.display = 'none';
		}
	}else{
		document.getElementById('lineFlag').value = '1';  // �л�Ϊ lineTableDiv1 ���ʾ������
 		if(document.getElementById('viewTypeFlag').value == '1'){ // ��ǰ��Ҫ��ʾ��Ϊ�����ߣ��л�Ϊ��ʾ lineTableDiv1 ��
	    	document.getElementById('lineTableDiv1').style.display = 'block';
	    	document.getElementById('lineTableDiv2').style.display = 'none';
		}else{
	    	document.getElementById('lineTableDiv1').style.display = 'none';
	    	document.getElementById('lineTableDiv2').style.display = 'block';
		}
	}
	showGraphicPonit('1');
	showGraphicPonit('0');
	// ���û���˳���л�����ͼ
	if(document.getElementById('viewTypeFlag').value == '1'){
		reDrawLineSection('1');
	}else{
		reDrawLineSection('0');
	}
	getDateDiff();
}
 	
/***
 * 2013-09-23 ���浱ǰ����Ϊ���ú���
 */
function ajaxSaveGeneralSkyWay(){
	skyFlag='1';	//lineTableDiv1 �㺽������Ϊ����
	if(document.getElementById('viewTypeFlag').value == '1' ){ 	// ��ǰ��ʾ���������ߣ�����Ҫ����Ϊ���ú��ߵĺ���
		if( parseInt(document.getElementById("linePointCount1").value) == 0){
			showConfirmDiv(4,'��ǰ���߲����ã��޷�����Ϊ���ú���!','������Ϣ');
			return;
		}
		if(document.getElementById('lineFlag').value == '0' ){  // ������ Ϊ lineTableDiv2 ��
			skyFlag='0';				//lineTableDiv2 �㺽������Ϊ����
		}
	}else{			// ��ǰ��ʾ���Ǳ����ߣ�����Ҫ����Ϊ���ú��ߵĺ���
		if( parseInt(document.getElementById("linePointCount2").value) == 0){
			showConfirmDiv(4,'��ǰ���߲����ã��޷�����Ϊ���ú���!','������Ϣ');
			return;
		}
		if(document.getElementById('lineFlag').value == '1' ){  // ������ Ϊ lineTableDiv2 ��
			skyFlag='0';				//lineTableDiv2 �㺽������Ϊ����
		}
	}
	
	showConfirmDiv(1,'��ȷ����Ϊ���ú�����?','��ʾ',function(choose){
		if(choose=="yes"){
			var jsondata = $("#form1").serializeArray();
        	$.post('flyPlanAction.ax?method=saveGeneralSkyWay&jquery=1&skyFlag='+skyFlag, jsondata, function(data){
			if (data.data.returnflag == "true") {
				showConfirmDiv(2,'���溽�����óɹ�!','������ʾ��Ϣ');
				if (data.data.skyFlag == "1") {
					document.getElementById("rsIdGeneralMain").value=data.data.rrId;
				}else{
					document.getElementById("rsIdGeneralBak").value=data.data.rrId;
				}
			}else{
				showConfirmDiv(3,'���溽������ʧ��!','������ʾ��Ϣ');
			}
           },"json");
		}
	});
}

/***
 * 2013-09-23 ���� �� �ύ ��ǰ�ƻ���Ϣ
 */
function ajaxSaveFlyPlan(planstate){

	if( !checkFlyPlanFormInfo() ){
		return ;
	}
	
	var jsondata = $("#form1").serializeArray();
	if(planstate == '12'){
		showConfirmDiv(1,'��ȷ�ϼƻ���Ϣ��д��ϣ��ύ�������ϼ���?','��ʾ',function(choose1){
			if(choose1=="yes"){
	        	 $.post('flyPlanAction.ax?method=saveFlyPlanInfo&planstate=12', jsondata, function(data){
					if (data.data.returnflag == "true") {
						document.getElementById("planid").value = data.data.planid;
						document.getElementById("FPlanCode").value = data.data.FPlanCode;
						document.getElementById("FPlanName").value = data.data.FPlanName;
						
						// showConfirmDiv(2,'���мƻ���Ϣ���沢�ύ�ɹ�!','������ʾ��Ϣ');
						document.getElementById('saveBut').disabled = "disabled";
						document.getElementById('commitBut').disabled = "disabled";
						showConfirmDiv(1,'���мƻ���Ϣ���沢�ύ�ɹ�������ȷ�Ϸ��ع���ҳ!','������ʾ��Ϣ',function(choose2){
							if(choose2=="yes"){
								goBack();
							}
						});
		
					}else{
						showConfirmDiv(3,'���мƻ���Ϣ����ʧ��!','������ʾ��Ϣ');
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
						
				showConfirmDiv(2,'���мƻ���Ϣ����ɹ�!','������ʾ��Ϣ');
			}else{
				showConfirmDiv(3,'���мƻ���Ϣ����ʧ��!','������ʾ��Ϣ');
			}
	   	},"json");
	}
    
}

/***
 * 2013-09-23 У����мƻ�������Ϣ��
 */
function checkFlyPlanFormInfo(){

	if(!checkElementFilled('adep','��ѡ��������ɻ���!','adepName')){
		return false;
	}
	if(!checkElementFilled('ades','��ѡ������Ŀ�Ļ���!','adesName')){
		return false;
	}
	
	var dates = document.getElementById("dates");
	var setal = document.getElementById("setal");
	var setd = document.getElementById("setd");
	var seta = document.getElementById("seta");
	if(!checkElementFilled('dates','�������������!')){
		return false;
	}
	if(!isShortDate(dates, "-", "�������", "2")) {
		return false;
	}
	
	if(!checkElementFilled('setd','���������ʱ��!')){
		return false;
	}
	if(!isTime(setd,'���ʱ��','2')){
		return false;
	}
	
	if(!checkElementFilled('setal','�����ý�������!')){
		return false;
	}
	if(!isShortDate(setal, "-", "��������", "2")) {
		return false;
	}
	
	if(!checkElementFilled('seta','�����ý���ʱ��!')){
		return false;
	}
	if(!isTime(seta,'����ʱ��','2')){
		return false;
	}
	a1 = new Date(Date.parse((dates.value+" "+setd.value+":00").replace(/-/g,'/')));
	b1 = new Date(Date.parse((setal.value+" "+seta.value+":00").replace(/-/g,'/')));
	if(b1.getTime()-a1.getTime() < 1){
		showConfirmDiv(0,'����ʱ��Ӧ�������ʱ��!','��ʾ��Ϣ');
		seta.focus();
        speciallyGoodEffect(seta);
        return false;
	}
	if(!checkElementFilled('chgt','�����÷��и߶�!')){
		return false;
	}
	return true;
}

/***
 * 2013-09-27 �����ӱ�����
 */
function addBakSkyWay() {

	if(!checkElementFilled('adep','��ѡ��������ɻ���!','adepName')){
		return false;
	}
	if(!checkElementFilled('ades','��ѡ������Ŀ�Ļ���!','adesName')){
		return false;
	}
  	if( parseInt(document.getElementById("linePointCount2").value) == 0){
  	
  		wayId='';
		if(document.getElementById('lineFlag').value == '1' ){  // ��ʾ��ǰ lineTableDiv2 ���ʾ�ı�����
		    wayId='lineTable2';
		}else{
		    wayId='lineTable1';
		}
		
		tableObj = document.getElementById(wayId);  //��ȡ����������
		
		// ɾ������
		tbRowsObj = tableObj.rows; // ��������
    	tempLen=tbRowsObj.length;
		deleteLineRow(2,0,tableObj); //�б�Ŵ�0��ʼ
		
		newTr = tableObj.insertRow(1);  //��ʾ���뵽�ڼ��У���Ŵ�0��ʼ
		newTr.className="twoTdClass";
		addAdepOrAdesLineRow('1',wayId,newTr,1);  // �����ɻ�����
		
		newTr = tableObj.insertRow(2);  //��ʾ���뵽�ڼ��У���Ŵ�0��ʼ
		newTr.className="oneTdClass";
		addAdepOrAdesLineRow('2',wayId,newTr,2);  // ���Ŀ�Ļ�����
		
		document.getElementById("linePointCount2").value = '2';
		
		// ����һ�κ�����ʾ�л����������������������ʾ�����⡣
	  	showSelectLineDiv('1');	
	  	showSelectLineDiv('0');	// ��ʾ�����߲㺽��
	  	showGraphicPonit('0');
	  	reDrawLineSection('0');
  	
  	}else{
  	
  		// showConfirmDiv(4,'�Ѵ��ڱ����ߣ����������������ɾ��!','������Ϣ');
  		// deleteSkyWay('0'); // ɾ���Ѵ��ڵı��ú���
  		showConfirmDiv(1,'�Ѵ��ڱ����ߣ���ȷ��ɾ�����������?','ȷ����ʾ',function(choose){
			if(choose=="yes"){
				deleteSkyWay('0'); 	// ɾ���Ѵ��ڵı��ú���
				addBakSkyWay(); 	// �ص��ú���������ӱ����ߡ�
	        	//showSelectLineDiv('0');	// ��ʾ�����߲㺽��
			}else{
				showSelectLineDiv('0');	// ��ʾ�����߲㺽��
			}		
		});
  	}
  	
}
	
/*
 * 2013-09-24 ɾ�� �������� 
 * wayType ��ʾ��ɾ���ĺ��ߵ����ͣ�
 		��ֵʱ 1 ��ʾɾ��������
			  0 ��ʾɾ��������
		������ʾɾ����ǰչʾ���ߡ�����Ϊ�����߻򱸺��ߡ�
 */
function deleteSkyWay(wayType) {
	linePointCount1 = document.getElementById('linePointCount1').value;
	linePointCount2 = document.getElementById('linePointCount2').value;
	
	if(wayType==null || wayType==undefined){
	
		// ��ǰ��ʾ�ĺ��߼���Ҫɾ���ĺ��ߡ�����Ϊ�����߻򱸺��ߡ�
		if(document.getElementById('lineFlag').value == '1' ){  // ��ʾ��ǰ lineTableDiv1 ���ʾ��������
	 		if(document.getElementById('viewTypeFlag').value == '1'){ // ��ǰ��ʾ��Ϊ������
		    	wayId='lineTable1';
		    	wayType ='1';
			}else{
		    	wayId='lineTable2';
		    	wayType ='0';
			}
		}else{	  // ��ʾ��ǰ lineTableDiv2 ���ʾ��������
	 		if(document.getElementById('viewTypeFlag').value == '1'){ // ��ǰ��ʾ��Ϊ������
		    	wayId='lineTable2';
		    	wayType ='1';
			}else{
		    	wayId='lineTable1';
		    	wayType ='0';
			}
		}
		
	}else{
	// �����е���ɾ��ĳ������ ��
		if(wayType =='1'){ // Ҫ��ɾ�������ߡ�
			if(document.getElementById('lineFlag').value == '1' ){  // ��ʾ��ǰ lineTableDiv1 ���ʾ��������
			    	wayId='lineTable1';
			}else{
			    	wayId='lineTable2';
			}
		}else{ // Ҫ��ɾ�������ߡ�
			if(document.getElementById('lineFlag').value == '1' ){  // ��ʾ��ǰ lineTableDiv1 ���ʾ��������
			    	wayId='lineTable2';
			}else{
			    	wayId='lineTable1';
			}
		}
	}
	
    var tableObj = document.getElementById(wayId);  //��ȡ����������
    /*
    if(tableObj == null)
	{
		tableObj = parent.document.getElementById(wayId);
	}*/
    var tbRowsObj = tableObj.rows; // ��������
    tempLen=tbRowsObj.length;
    inserti=1;
    
    if(wayType =='1'){
    
    	deleteLineRow(tempLen-1,1,tableObj); //�б�Ŵ�0��ʼ	
	    
	    adep = document.getElementById("adep").value; // ��ɻ���
		ades = document.getElementById("ades").value; // Ŀ�Ļ���
	
    	if(adep != '' &&��ades��!= ''){
    		
	    	 // ���²���Ŀ�Ļ�����
		    newTr = tableObj.insertRow(2);  //��ʾ���뵽�ڼ��У���Ŵ�0��ʼ	
			newTr.className="oneTdClass";
			
			addAdepOrAdesLineRow('2',wayId,newTr,2);  // ���Ŀ�Ļ�����
			document.getElementById('linePointCount1').value = "2";
		    inserti=3;
    	}else{
    		tableObj.deleteRow(1); // ɾ����ɻ���������
    		document.getElementById('linePointCount1').value = "0";
    	}
	   
	}else{
	
		document.getElementById('linePointCount2').value = "0";
		deleteLineRow(tempLen-1,0,tableObj); //�б�Ŵ�0��ʼ	
	}
	addNewLineRow(inserti,7,tableObj); // ���ӿհ���
	
	
	// ����һ�κ�����ʾ�л����������������������ʾ�����⡣
	if(wayType =='1'){
		showSelectLineDiv('0');	
	  	showSelectLineDiv('1');	// ��ʾ�����߲㺽��
	  	getDateDiff();
	}else{
		showSelectLineDiv('1');	
	  	showSelectLineDiv('0');	// ��ʾ�����߲㺽��
	}
	showGraphicPonit(wayType);
	reDrawLineSection(wayType);  	
}

/***
 * 2013-09-26 ͨ�÷�����ɾ�������������У��Ӻ���ǰɾ, �б�Ŵ� 0 ��ʼ ����ɾ�� (startIndex - endIndex) ����
 * startIndex : �ӵ����ڼ��п�ʼɾ��������ָ����
 * endIndex : ɾ���������ڼ���Ϊֹ ��������ָ���У� ��0 ��ʼ���ڼ��в�ɾ��
 * tableObj ����ɾ�����е�ָ��������
 */
function deleteLineRow(startIndex,endIndex,tableObj) {
	for(i=startIndex; i>endIndex;i--){
    	tableObj.deleteRow(i);
    }
}	

/***
 * 2013-09-26 ͨ�÷�����Ϊ�������ӿհ��У��б�Ŵ� 0 ��ʼ 
 * startIndex : �ӵڼ��п�ʼ�����У�������ָ����
 * endIndex : ���뵽�ڼ���Ϊֹ ��������ָ���к�
 * tableObj ���������е�ָ��������
 */
function addNewLineRow(startIndex,endIndex,tableObj) {
	// ����հ��У����õ��к� 1 Ϊ ��ɫ����Ӧ twoTdClass
    for(i=startIndex; i<endIndex;i++){
    	newTr = tableObj.insertRow(i);  //��ʾ���뵽�ڼ��У���Ŵ�0��ʼ	
    	if(i%2 ==1){
    		newTr.className="twoTdClass";  // ����ռ��һ���к�
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
 * 2013-09-27 ������ɻ�����Ŀ�Ļ����е�����ָ����
 * addType : 1 ��ʾ��ӵ���ɻ����ڵ�
			 2 ��ʾ��ӵ���Ŀ�Ļ����ڵ� 
 * wayId : ����ӵ������ڲ�id
 * newTr : ��ӵ�����ж���
 * indexValue �� ��ӵ��ڼ���  �������߽ڵ��ţ��� 1 ��ʼ
 */
function addAdepOrAdesLineRow(addType,wayId,newTr,indexValue) {
	addId = ''; 
	addName = ''; 
	addCode = ''; 
	addPos = ''; 
	if(addType == '1'){
		addId = document.getElementById("adep").value; // ��ɻ���
		addName = document.getElementById("adepName").value; //  ��ɻ���
		addCode = document.getElementById("adepCode").value; //  ��ɻ���
		addPos = document.getElementById("adepPos").value; //  ��ɻ���
		
	}else{
		addId = document.getElementById("ades").value; // Ŀ�Ļ���
		addName = document.getElementById("adesName").value; // Ŀ�Ļ���
		addCode = document.getElementById("adesCode").value; // Ŀ�Ļ���
		addPos = document.getElementById("adesPos").value; // Ŀ�Ļ���
	}
	
	if(wayId == 'lineTable1'){
		// �������ɻ����������Ӱ�ť
		if(addType == '1'){
			addLinePointRow(newTr,'1', indexValue ,'1' ,addCode ,addName ,addPos ,'9');
		}else{
			addLinePointRow(newTr,'1', indexValue ,'0' ,addCode ,addName ,addPos ,'9');
		}
	}else{
		// �������ɻ����������Ӱ�ť
		if(addType == '1'){
			addLinePointRow(newTr,'0', indexValue ,'1' ,addCode ,addName ,addPos ,'9');
		}else{
			addLinePointRow(newTr,'0', indexValue ,'0' ,addCode ,addName ,addPos ,'9');
		}
	}
}	


/***
 * 2013-09-30 ͨ�÷��������Ӻ��߽ڵ����ݡ� 
 * newTr �� �����ӵı���ж���
 * divType : 1 ��ʾ���ӵ� lineTable1
			 0 ��ʾ���ӵ� lineTable2
 * trIndex : ���ӵĺ��߽ڵ���
 * nodeType : �ڵ����ͣ�1 ��ʾ��ĩ�ڵ�
 					  0 ��ʾ ΪĿ�Ļ����ڵ�
 * rsPCode �� �ڵ�ICAO���
 * rsPName �� �ڵ�����
 * rsPPos : �ڵ��������
 * rsPType : �ڵ�����
 */
 
function addLinePointRow(newTr,divType,trIndex,nodeType,rsPCode,rsPName,rsPPos,rsPType) {

	rsPTypeName='';
	if(rsPType == '9') rsPTypeName='����';
    if(rsPType == '18') rsPTypeName='����̨';
    if(rsPType == '20') rsPTypeName='�ر��';
       		
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
			
			// ����
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
				
       		// ����
       		tdNodes =newTr.insertCell(3);
       		tdNodes.innerHTML= rsPTypeName +'<input type="hidden" name="rsPTypeBak" id="rsPTypeBak' + trIndex + '" value="'+ rsPType + '"/>';
       		
      		// ����
      		tdNodes =newTr.insertCell(4);
      		if(nodeType == '1'){
      			tdNodes.innerHTML='<div class="setupLineDivMeun" id="setupLineDivMeunBak'+ trIndex +'"></div>'
      				+'<div class="setupLineDiv" onClick="showFLinMeun(\'setupLineDivMeunBak'+ trIndex +'\','+ trIndex +')"><img src="images/line_setup.png"/></div>';	
      		}
		}

}	

/***
 * 2013-10-10 ������ɻ�����Ŀ�Ļ���ʱ�������ɻ�����Ŀ�Ļ����ڵ�����������  �� �޸Ļ���ʱ���޸������������еĶ�Ӧ�����ڵ���Ϣ ��
 * addType : 1 ��ʾ��ɻ����ڵ�
			 2 ��ʾĿ�Ļ����ڵ� 
 */
function addAdepOrAdesToLine(addType){

	wayId1='lineTable1';
	wayId2='lineTable2';
	if(document.getElementById('lineFlag').value == '0' ){  // ��ʾ��ǰ lineTableDiv2 ���ʾ��������
	    wayId1='lineTable2';
	    wayId2='lineTable1';
	}
	
	tableObj1 = document.getElementById(wayId1);  //��ȡ�����߱�����
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
	tableObj1.deleteRow(rowIndex1); // ɾ��ԭ�л�����
	trRow1 = tableObj1.insertRow(rowIndex1);  //���²��������
	trRow1.className = classId1;
	addAdepOrAdesLineRow(addType ,wayId1 ,trRow1 ,rowIndex1);
	
	if(rowIndex2 > 0){
		tableObj2 = document.getElementById(wayId2);  //��ȡ�����߱�����
		classId2 =tableObj2.rows[rowIndex2].className;
		tableObj2.deleteRow(rowIndex2); // ɾ��ԭ�л�����
		trRow2 = tableObj2.insertRow(rowIndex2);  //���²��������
		trRow2.className = classId2;
		addAdepOrAdesLineRow(addType ,wayId2 ,trRow2 ,rowIndex2);
	}
	
	if(addType == '2'){
		document.getElementById('linePointCount1').value = rowIndex1;
	}
	showSelectLineDiv('1');	
}

/***
 * 2013-09-24 ���ó��ú��߻��麽��
 * 		flag: ���ú��߻��麽�߱�ʾ 0��ʾ���ú��ߣ�1��ʾ���麽��
 */
function queryGeneralSkyWays(flag) {

	if(!checkElementFilled('adep','��ѡ��������ɻ���!','adepName')){
		return false;
	}
	if(!checkElementFilled('ades','��ѡ������Ŀ�Ļ���!','adesName')){
		return false;
	}
	adep=document.getElementById("adep");
	ades=document.getElementById("ades");
    // showIframeDiv(718,376,'flyPlanAction.do?method=searchGeneralSkyWayList&adep='+adep.value+'&ades='+ades.value,'iframe','ѡ���ú���',60);
    var jsondata = {
   		adep:adep.value,
   		ades:ades.value,
   		flag:flag
    };
    $("#jQueryCommonDiv").load('flyPlanAction.do?method=searchGeneralSkyWayList', jsondata, function(){
    	if(flag == '0') {
    		showLocalDiv(722,340,'ѡ���ú���','jQueryCommonDiv',60,'0');
    	}else {
    		showLocalDiv(722,340,'ѡ���麽��','jQueryCommonDiv',60,'0');
    	}
    });
    
}
/***
 * 2013-11-14 ���÷�������ʱ��Ĭ�����ý�������
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
 * 2013-09-28 �������ʱ��
 * 2014-01-02 ���޸ļ��㣬����ʱ���� �����߾���/�����ٶ� �� ����ʱ�䣺 ���ʱ��+����ʱ��
 */
function getDateDiff(){
	dates = document.getElementById("dates").value.trim();
	setd = document.getElementById("setd").value.trim();
	adep = document.getElementById("adep").value.trim();
	ades = document.getElementById("ades").value.trim();
	cspd = document.getElementById("cspd").value.trim();
	totalDis=0;
	 //  ����1
 	lng1 =0;
  	// γ��1 
    lat1 =0;
     //  ����2
 	lng2 =0;
  	// γ��2 
    lat2 =0;
    // showIframeDiv(718,376,'flyPlanAction.do?method=searchGeneralSkyWayList&adep='+adep.value+'&ades='+ades.value,'iframe','ѡ���ú���',60);
	if(dates !='' && setd !='' && adep !='' && ades !='' && cspd !='' ){
		wayDivType='1';
	 	if( document.getElementById('lineFlag').value == '0') { // ��ǰ lineTableDiv2 ���ʾ��������
			    wayDivType='2';
		}
	 	lagIndex=0;
		if(wayDivType =='1'){
			
			for (i=0;i<document.form1.elements.length;i++){
			    if (document.form1.elements[i].name=="rs_PPosMain"){ 
			    	temp=getPointNumInfo(document.form1.elements[i].value);
			    	temp1=temp.indexOf(',');
			    	if(lagIndex < 1){
			    		//  ����2
					 	lng2 =temp.substr(1,temp1-1);
					  	// γ��2 
					    lat2 =temp.substr(temp1+1,temp.length - temp1-2 );
			    	}else{
			    		 //  ����1
					 	lng1 =lng2;
					  	// γ��1 
					    lat1 =lat2;
					    //  ����2
					 	lng2 =temp.substr(1,temp1-1);
					  	// γ��2 
					    lat2 =temp.substr(temp1+1,temp.length - temp1-2 );
					    totalDis += getDistance(parseFloat(lat1),parseFloat(lng1),parseFloat(lat2),parseFloat(lng2)); // ���߽ڵ�����ǰ�����Ľڵ�ľ���
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
			    		//  ����2
					 	lng2 =temp.substr(1,temp1-1);
					  	// γ��2 
					    lat2 =temp.substr(temp1+1,temp.length - temp1-2 );
			    	}else{
			    		 //  ����1
					 	lng1 =lng2;
					  	// γ��1 
					    lat1 =lat2;
					    //  ����2
					 	lng2 =temp.substr(1,temp1-1);
					  	// γ��2 
					    lat2 =temp.substr(temp1+1,temp.length - temp1-2 );
					    totalDis += getDistance(parseFloat(lat1),parseFloat(lng1),parseFloat(lat2),parseFloat(lng2)); // ���߽ڵ�����ǰ�����Ľڵ�ľ���
			    	}
			    	lagIndex++;
			    }
			}
		}
		var mill=totalDis*3600/(parseFloat(cspd))+59999; //  ����
		hours = Math.floor(mill/(1000*60*60));
//		// �����ȥ����Сʱ�����ʣ��ĺ�����
		leaves = mill%(1000*60*60);
//		// �������ķ�����
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
//		// ������������
//		//var aa = Math.floor((b1.getTime()-a1.getTime())/(1000*60*60*24));
//		//alert(aa);
//		// ��������Сʱ��
//		var hours = Math.floor((b1.getTime()-a1.getTime())/(1000*60*60));
//		// �����ȥ����Сʱ�����ʣ��ĺ�����
//		var leaves = (b1.getTime()-a1.getTime())%(1000*60*60);
//		// �������ķ�����
//		var minutes = Math.floor(leaves/(1000*60));
//		if(minutes < 10) {
//			minutes = "0" + minutes;
//		}
//		document.getElementById("eet").value=hours + ":" + minutes;
//	}
	
	
}

/***
 * 2013-10-11 У���Ƿ���¼����ɻ���
 * param elmId ��ȷ���Ƿ�Ϊ�յı�Ԫ�ص�ID
 * param elmFocusId ��������˸��Ԫ�ص�ID
 * param alertText��ʾ��Ϣ 
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

// ���ش���
function goBack() {
	document.form1.action ='flyPlanAction.do?method=searchFlyPlanList';
	document.form1.submit();
}


var graphic1='';  	// �����ߵ�ͼ��ʾ��Ӧ��ȫ�ֱ���
var graphic2='';	// �����ߵ�ͼ��ʾ��Ӧ��ȫ�ֱ���

/***
 * 2013-10-12 ���ݵõ��ľ�γ����Ϣת���ɵ�ͼ��ʶ�����ֵ��γ����Ϣ
 * param rsPPos ���ݵ�����
 * param formatType ���ص������ʽ�� 1 ��ʾ��������ֵ�о�����ǰ��2 ��ʾ���ص�����ֵ��γ����ǰ�� Ϊ��ʱĬ��Ϊ 1 ������ǰ
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
	if(formatType !=null && formatType == '2'){  // ��άʹ�ø�ʽ��γ����ǰ
		return '['+wd+','+jd+']';
	}
	return '['+jd+','+wd+']'; // ��άʹ�ø�ʽ��������ǰ
} 

/***
 * 2013-10-12 ��ͼ����ʾ���� 
 * param wayType ������ʾ�ĺ������� 
 * 				1 ��ʾ������
 * 				0 ��ʾ������
 */	
function showGraphicPonit(wayType) {
	
	skWay='';
 	wayDivType='1';
 	
 	if( (wayType =='1' && document.getElementById('lineFlag').value == '0')  // ��ʾ�����ߣ���ǰ lineTableDiv2 ���ʾ��������
 		|| (wayType =='0' && document.getElementById('lineFlag').value == '1') ){	// ���� ��ʾ�����ߣ���ǰ lineTableDiv2 ���ʾ�ı�����
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
//2013-11-12 �����ӣ��޸ķ��и߶�ʱ���ػ�����ͼ��
function changeFlyHight(){
	if(document.getElementById('viewTypeFlag').value == '1'){ // ��ǰ��ʾ��Ϊ������
		reDrawLineSection('1');
	}else{
		reDrawLineSection('0');
	}
}

//2013-11-13 ��  ����ˢ�����к���
function reFreshGisLine(){
	showGraphicPonit('1');
	showGraphicPonit('0');
	// ��������ͼ
	if(document.getElementById('viewTypeFlag').value == '1'){
		reDrawLineSection('1');
	}else{
		reDrawLineSection('0');
	}
	// ���ڵ���ɻ�����Ŀ�Ļ���û�б仯 �����ñ��û���ʱ������������ʾ���ߵȵ��л����á�
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


//2013-11-13 ��  ����ˢ�����к���
function onLoadGisLine(){
	showGraphicPonit('1');
	showGraphicPonit('0');
	// ���ڵ���ɻ�����Ŀ�Ļ���û�б仯 �����ñ��û���ʱ������������ʾ���ߵȵ��л����á�
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


// 2014-01-25 ����ӣ�����ȫ�ֱ���������ά����
coordinateLine='';  // ��ά���к��ߵ�����
flyHignt='';  // ��ά���к��߸߶�

/***
 * 2013-11-18 �����ӣ�������ѡ���߽ڵ�ˢ������ͼ
 * param wayType ������ʾ������ͼ���� 
 * 				1 ��ʾ����������ͼ
 * 				0 ��ʾʾ����������ͼ
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
 	if( (wayType =='1' && document.getElementById('lineFlag').value == '0')  // ��ʾ����������ͼ����ǰ lineTableDiv2 ���ʾ��������
 		|| (wayType =='0' && document.getElementById('lineFlag').value == '1') ){	// ���� ��ʾ����������ͼ����ǰ lineTableDiv2 ���ʾ�ı�����
 		
 		if(skWay2 !=''){
			gis_drawSection('['+ skWay2.substr(0,skWay2.length-1) +']', flyHignt,0); // ����skWay2 ��Ӧ�ĸ߶ȱ���ֵ
		}
		
		coordinateLine = coordinateLine1;
		if(skWay1 !=''){
			document.getElementById('chartNode').style.display='block';
			gis_drawSection('['+ skWay1.substr(0,skWay1.length-1) +']', flyHignt,1); // ������ͼ��������skWay1 ��Ӧ�ĸ߶ȱ���ֵ
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
 * 2013-12-20 ���ݻ���ICAO��ȡ����������Metar������Ϣ
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
				showConfirmDiv(5,metarInfo,'����Metar��Ϣ',null,'500','160');
			}else{
				showConfirmDiv(0,'δ�ҵ��û�����Metar������Ϣ!','����Metar��Ϣ');
			}
		}else{
			showConfirmDiv(0,'δ�ҵ��û�����Metar������Ϣ!','����Metar��Ϣ');
		}
   	},"json");
    
}


// ����code�ڵ�ͼ���ҵ�
function getPointInfoByCode(pcode) {
	if( pcode !=''){
		executeAPQuery(pcode);
	}
}

//  ��տؼ�ֵ
function cleanValueById(param) {
	document.getElementById(param).value = "";
	if( param == "altn1" ){
		document.getElementById("altn1Code").value = "";
		reFreshGisLine();
	}
}
