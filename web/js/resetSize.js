function resetSize(){
	var frameheight = document.body.clientHeight;
	var framewidth = document.body.clientWidth;
	var flag = fPlanContextText.style.display;
	
	if(framewidth < 1663) {
		$(".fPlanContext").css({width:1595});
	}else{
		$(".fPlanContext").css("width",(framewidth-58));
	}
	if(flag == "none"){
		// �Ŵ��ͼ
		if(framewidth < 1663) {
			$(".fPlanContextImg").css({width:1595,styleFloat: "right",left:48});
		}else{
			$(".fPlanContextImg").css({width:(framewidth-58)*1,styleFloat: "right",left:48});
		}

	}else{
		
		var fPlanContextImgWidth = (framewidth-58-15)*0.55 > 865 ? (framewidth-58-15)*0.55 : 865;
		var fPlanContextTextWidth = (framewidth-58-15)*0.45 > 715 ? (framewidth-58-15)*0.45 : 715;

		$(".fPlanContextText").css("width",fPlanContextTextWidth);
		$(".fPlanContextImg").css({width:fPlanContextImgWidth,position: "absolute",left:$(".fPlanContextText").width()+63});
		$(".fPlanLeftTop").css("width","100%");
		$(".fPlanLeftCenter").css("width","100%");
		$(".fPlanLeftBottom").css("width","100%");
		$(".fPlanLeftTopContext").css("width",$(".fPlanContextText").width()-6+"px");
		$(".fPlanLeftCenterContext").css("width",$(".fPlanContextText").width()-6+"px");
		$(".fPlanLeftBottomContext").css("width",$(".fPlanContextText").width()-6+"px");
		
		$(".inputTableDiv").css("width",$(".fPlanLeftTopContext").width()-15+"px");
		$(".inputTable").css("width",$(".inputTableDiv").width()-5+"px");
		
		// һ���ߵĳ�������
		$(".divLine").css("width",$(".inputTable").width()-15+"px");
		// �����б�����
		$(".lineButtonDiv").css("width",$(".fPlanLeftTopContext").width()-15+"px");
		$(".lineTableDiv").css("width",$(".fPlanLeftTopContext").width()-25+"px");
		$(".lineTable").css("width",$(".fPlanLeftTopContext").width()-33+"px");
		$("#lineTable1 tr:first").children('td').eq(0).css("width","5%");
		$("#lineTable1 tr:first").children('td').eq(1).css("width","28%");
		$("#lineTable1 tr:first").children('td').eq(2).css("width","42%");
		$("#lineTable1 tr:first").children('td').eq(3).css("width","20%");
		$("#lineTable1 tr:first").children('td').eq(4).css("width","5%");
		
		// ��ͻ�б�����
		$(".securityTableDiv").css("width",$(".fPlanLeftBottomContext").width()-15+"px");
		$(".securityButtonDiv").css("width",$(".fPlanLeftBottomContext").width()-5+"px");
		$(".flyConflictTbl").css("width",$(".fPlanLeftBottomContext").width()-33+"px");
		$("#flyConflictTbl tr:first").children('td').eq(0).css("width","5%");
		$("#flyConflictTbl tr:first").children('td').eq(1).css("width","25%");
		$("#flyConflictTbl tr:first").children('td').eq(2).css("width","45%");
		$("#flyConflictTbl tr:first").children('td').eq(3).css("width","25%");
		
		// ���мƻ�������Ϣ
		$(".inputTableTo").css("width",$(".fPlanLeftCenterContext").width()-20+"px");
		
		//����Բ�ǵĳ���
		var titleLeftRoundWidth = $(".titleLeftRound").width();
		$(".roundLeft").css("width",titleLeftRoundWidth-10+"px");
		$(".topRoundLeftRight").css("width",titleLeftRoundWidth-20+"px");
		$(".roundRight").css("margin-left",titleLeftRoundWidth-10+"px");
		
		$(".roundTitleRight").css("width",titleLeftRoundWidth-120+"px");
		$(".roundLeftRight").css("width",titleLeftRoundWidth-20+"px");
		$(".fPlanTextareaShowBorder").css("width",$(".inputTableTo").width()-150);
		$(".fPlanTextareaNoBorder").css("width",$(".inputTableTo").width()-150);
		$(".fPlanTextareaNoBorder2").css("width",$(".inputTableTo").width()-150);
	}
		
	// �����Ұ벿�ֵĳ���
	$(".fPlanRightTop").css("width","100%");
	$(".fPlanRightBottom").css("width","100%");
	$(".fPlanRightTopContext").css("width",$(".fPlanRightTop").width() - 10 +"px");
	$(".fPlanRightBottomContext").css("width",$(".fPlanRightBottom").width() - 10 +"px");
	$(".showMapDiv").css("width",$(".fPlanRightBottom").width() - 20 +"px");
	$(".mapLeftRound").css("width",$(".fPlanRightTop").width() - 10 +"px");
	$(".mapRoundLeftRight").css("width",$(".fPlanRightTop").width() - 20 +"px");
	$(".mapRoundRight").css("margin-left",$(".fPlanContextImg").width()-10+"px");
	// �Ŵ���СͼƬ�Ķ�λ
	$("#blowupDiv").css({left:10,top:506});
	// ͼ����ƶ�λ
	$("#controlPane").css({left:$(".showMapDiv").width()-112,top:18});
	$("#contentPane").css({left:$(".showMapDiv").width()-112,top:18});
	reDrawLineSection(document.getElementById('viewTypeFlag').value);
}