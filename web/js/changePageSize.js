// JavaScript Document
function mainPage(){

}


function blowupMapDiv(){
	var fPlanContextText = document.getElementById("fPlanContextText");
	var blowupDiv = document.getElementById("blowupDiv");	
	var flag = fPlanContextText.style.display;
	if(flag != "none"){
		fPlanContextText.style.display = "none";
		blowupDiv.title = "地图还原";

		document.getElementById('lineTableDiv1').style.display = 'none';
		document.getElementById('lineTableDiv2').style.display = 'none';
		document.getElementById('conflict_div').style.display = 'none';
	}else{
		
		fPlanContextText.style.display = "block";
		blowupDiv.title = "放大地图";
		document.getElementById('conflict_div').style.display = 'block';
		showSelectLineDiv(document.getElementById('viewTypeFlag').value);
	}
	resetSize();
	// reDrawLineSection(document.getElementById('viewTypeFlag').value);
	map.resize(); 
	map.reposition();
}



var resizeTimer = null;

function resetSize1(){
	if(resizeTimer == null){
		resizeTimer = setTimeout("resetSize()",100);
	}
}

/**
 * 页面自适应计算
 */
function resetSize(){
	var frameheight = document.documentElement.clientHeight;
	var framewidth = document.documentElement.clientWidth;

	var parentHeight = window.parent.document.documentElement.clientHeight;
	var parentWidth = window.parent.document.documentElement.clientWidth;
	
	var flag = $(".fPlanContextText").css("display");

	if(flag != undefined ){

		if(framewidth <= 1663) {
			$(".globalDiv").css({width:(1595)});
			$(".fPlanContext").css({width:1595});
		}else{
			$(".globalDiv").css({width:(framewidth-58)});
			$(".fPlanContext").css({width:(framewidth-58)});
		}
		if(frameheight <= 836) {
			$(".globalDiv").css({height:770});
			$(".fPlanContext").css({height:725});
		}else{
			
			$(".globalDiv").css({height:frameheight-40});
			$(".fPlanContext").css({height:frameheight-40-45});
		}
		if(flag == "none"){
			// 放大地图
			if(framewidth <= 1663) {
				$(".fPlanContextImg").css({width:1595,styleFloat: "right",left:48});
			}else{
				$(".fPlanContextImg").css({width:(framewidth-58)*1,styleFloat: "right",left:48});
			}
	
		}else{
			var fPlanContextImgWidth = (framewidth-58-15)*0.55 > 865 ? (framewidth-58-15)*0.55 : 865;
			var fPlanContextTextWidth = (framewidth-58-15)*0.45 > 715 ? (framewidth-58-15)*0.45 : 715;
	
			$(".fPlanContextText").css({width:fPlanContextTextWidth,height:$(".fPlanContext").height()});
			$(".fPlanContextImg").css({width:fPlanContextImgWidth,position: "absolute",left:$(".fPlanContextText").width()+63,height:$(".fPlanContext").height()});
			$(".fPlanLeftTop").css("width","100%");
			$(".fPlanLeftCenter").css("width","100%");
			$(".fPlanLeftBottom").css("width","100%");
			$(".fPlanLeftTopContext").css("width",$(".fPlanContextText").width()-6+"px");
			$(".fPlanLeftCenterContext").css("width",$(".fPlanContextText").width()-6+"px");
			$(".fPlanLeftBottomContext").css("width",$(".fPlanContextText").width()-6+"px");
			
			// 高度设置
			var textheight = $(".fPlanContextText").height();
			$(".fPlanLeftTop").css("height",textheight*0.47);
			$(".fPlanLeftCenter").css("height",textheight*0.29);
			$(".fPlanLeftBottom").css("height",textheight*0.24);
			
			$(".fPlanLeftTopContext").css("height",$(".fPlanLeftTop").height()-39);
			$(".fPlanLeftCenterContext").css("height",$(".fPlanLeftBottom").height()-54);
			$(".fPlanLeftBottomContext").css("height",$(".fPlanLeftCenter").height()-48);

			//alert(frameheight+","+$(".fPlanContext").width()+","+$(".fPlanContext").height()+","+$(".centerContext").height());
			
			$(".inputTableDiv").css("width",$(".fPlanLeftTopContext").width()-15+"px");
			$(".inputTable").css("width",$(".inputTableDiv").width()-5+"px");

			// 一根线的长度设置
			$(".divLine").css("width",$(".inputTable").width()-15+"px");
			// 航线列表设置
			$(".lineButtonDiv").css("width",$(".fPlanLeftTopContext").width()-15+"px");
			$(".lineTableDiv").css("width",$(".fPlanLeftTopContext").width()-19+"px");
			$(".lineTable").css("width",$(".fPlanLeftTopContext").width()-27+"px");
			
			// 冲突列表设置
			$(".securityTableDiv").css("width",$(".fPlanLeftBottomContext").width()-19+"px");
			$(".securityButtonDiv").css("width",$(".fPlanLeftBottomContext").width()-5+"px");
			
			// 飞行计划其他信息
			$(".inputTableTo").css("width",$(".fPlanLeftCenterContext").width()-19+"px");
			
			//设置圆角的长度
			var titleLeftRoundWidth = $(".titleLeftRound").width();
			$(".roundLeft").css("width",titleLeftRoundWidth-10+"px");
			$(".topRoundLeftRight").css("width",titleLeftRoundWidth-20+"px");
			$(".roundRight").css("margin-left",titleLeftRoundWidth-10+"px");
			
			$(".roundTitleRight").css("width",titleLeftRoundWidth-120+"px");
			$(".roundLeftRight").css("width",titleLeftRoundWidth-20+"px");
			$(".fPlanTextareaShowBorder").css("width",$(".inputTableTo").width()-125);
			$(".fPlanTextareaNoBorder").css("width",$(".inputTableTo").width()-125);
			$(".fPlanTextareaNoBorder2").css("width",$(".inputTableTo").width()-125);
			$(".fPlanTextareaNoBorder3").css("width",$(".inputTableTo").width()-155);
			$(".tabSecurityTitleRight").css("width",titleLeftRoundWidth-320+"px");
		}
			
		// 设置右半部分的长度
		$(".fPlanRightTop").css("width","100%");
		$(".fPlanRightBottom").css("width","100%");
		$(".fPlanRightTopContext").css("width",$(".fPlanRightTop").width() - 10 +"px");
		$(".fPlanRightBottomContext").css("width",$(".fPlanRightBottom").width()-10 +"px");
		
		$(".fPlanRightTop").css("height",($(".fPlanContextImg").height()-10)*0.73);
		$(".fPlanRightBottom").css("height",($(".fPlanContextImg").height()-10)*0.27-22);
		$(".fPlanRightTopContext").css("height",$(".fPlanRightTop").height()-20);
		$(".fPlanRightBottomContext").css("height",$(".fPlanRightBottom").height()-20);
			
		$(".showMapDiv").css({width:$(".fPlanRightTop").width() - 20,height:$(".fPlanRightTop").height()-20} );
		$(".mapLeftRound").css("width",$(".fPlanRightTop").width() - 10 +"px");
		$(".mapRoundLeftRight").css("width",$(".fPlanRightTop").width() - 20 +"px");
		$(".mapRoundRight").css("margin-left",$(".fPlanContextImg").width()-10+"px");
		// 放大缩小图片的定位
		$("#blowupDiv").css({left:10,top:$(".fPlanRightTop").height()-23});
		// 图层控制定位
		$("#controlPane").css({left:$(".showMapDiv").width()-112,top:20});
		$("#contentPane").css({left:$(".showMapDiv").width()-112,top:20});
		
		//$(".chartNode").css({width:$(".fPlanRightBottomContext").width()-25,height:$(".fPlanRightBottomContext").height()} );
		
		reDrawLineSection(document.getElementById('viewTypeFlag').value);
	} else {
		var textWidth=0;
		var textHeight=0;
		if(framewidth <= 1663) {
			$(".globalDiv").css({width:(1595)});
			textWidth = 1595;
		}else{
			$(".globalDiv").css({width:(framewidth-58)});
			textWidth = framewidth-58;
		}
		if(frameheight <= 836) {
			$(".globalDiv").css({height:770});
			textHeight = 690;
		}else{
			$(".globalDiv").css({height:frameheight-40});
			textHeight = frameheight-120;
		}
		
		$(".fPlanListDiv").css({width:textWidth,height:textHeight});
		$(".lineTable").css("height",textHeight-80);
		$(".fPlanListTable").css("width",textWidth);
		$(".fplanListDivLine").css("width",textWidth);
	}
	
	resizeTimer = null;
}