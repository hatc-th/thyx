//global.js //地球环境
//using dojo 
//using google earth api

google.load("earth", "1", {'other_params' : 'sensor=false'});
google.load('maps', '2.x', {'other_params' : 'sensor=false'});

window.global = null;

function runGlobal(){
	if(window.global){ //退出仿真，清理环境
		stopPlane();
		window.global.hide();
		window.ge =null;
		window.global = null;
		window.plane = null;
		gisClear();
	}else{//加载仿真环境
		window.global= new Global();
		window.global.show();
		window.global.init();
		cc.globalInit();
	}
	
}


function Global() {
	var me = this;

	//me.geocoder = new GClientGeocoder();
	me.ge = null;
	me.plane = null;
	me.routePlacemark = null;
	// 路径点
	me.targetPlacemarks = [];
	me.panelMask = null;
	
	me.cameraMode="back" ; //left right back;
	cc.global = me; 
}

Global.prototype.show = function() {
	//创建包含地球的div
//	if (!dojo.byId('map3d_container')) {
//		dojo.create("div", {
//			id : "map3d_container",
//			style : {
//				position : "absolute",
//				width : dojo.byId('fPlanContextText').style.width,
//				height : dojo.byId('fPlanContext').style.height,
//				margin : "0 auto"
//
//			}
//		}, dojo.byId('fPlanContext'));
//	}
//	if (!dojo.byId('map3d')) {
//		dojo.create("div", {
//			id : "map3d",
//			style : {
//				height : "80%",
//				width : "100%"
//			}
//		}, dojo.byId('map3d_container'));
//
//		//显示控制板
//		dojo.place(dojo.byId('map3dControl'), dojo.byId('map3d'), 'after');
//		dojo.setStyle(dojo.byId("map3dControl"), "display", "block");
//		dojo.setStyle(dojo.byId("map3dControl"), "z-index", "999");
//		dojo.setStyle(dojo.byId("map3dControl"), "background-color", "#374068");
//	}
	if (!$('#map3d_container').length>0) {
		$("<div id='map3d_container'></div>").css({
			position : "absolute",
			width : dojo.byId('fPlanContextText').style.width,
			height : dojo.byId('fPlanContext').style.height,
			margin : "0 auto"
		}).appendTo("#fPlanContext");
	}
	if (!$('#map3d').length>0) {
		$("<div id='map3d' ></div>").css({
				height : "80%",
				width : "100%"
			}).appendTo('#map3d_container');

		//显示控制板
		$('#map3d_container').append($('#map3dControl'));
		$('#map3dControl').css({display: "block","z-index": "999","background-color":"#374068" });
	}
};

Global.prototype.hide = function() {
	var me =this;
	if (me.ge != null) {
		$("#globalDiv").append($('#map3dControl'));
		$('#map3dControl').css({display: "none"});
		$("#map3d_container").remove();
		me.ge = null;
		plane = null;
		return;
	}

};

Global.prototype.init = function() {
	var me = this;
	google.earth.createInstance("map3d",
	// initCallback
	function(object) {
		me.ge = object;
		me.ge.getWindow().setVisibility(true);
		// ge.getOptions().setMouseNavigationEnabled(false);
		me.ge.getLayerRoot().enableLayerById(me.ge.LAYER_BUILDINGS, true); // /建筑
		me.ge.getLayerRoot().enableLayerById(me.ge.LAYER_BORDERS, true); // 边界

		me.ge.getNavigationControl().setVisibility(me.ge.VISIBILITY_AUTO);//显示罗盘
		
		//地形精度
		me.ge.getLayerRoot().enableLayerById(me.ge.LAYER_TERRAIN, true);
		me.ge.getOptions().setTerrainExaggeration(1.0);//2.0
		
		me.ge.getOptions().setStatusBarVisibility(true); //显示状态栏 
		me.ge.getOptions().setFlyToSpeed(me.ge.SPEED_TELEPORT);
		me.ge.getOptions().setFlyToSpeed(100);  // don't filter camera motion
		me.plane = new Plane();
		plane= me.plane;
		plane.ge=me.ge;
		// addPanel();
	},
	// failureCallback
	function(err) {

	});
};


//绘制预计航线
Global.prototype.drawRoute = function() {
	var me = this;
	if (me.routePlacemark) {
		me.ge.getFeatures().removeChild(me.routePlacemark);
	}
	// Create the LineString
	var lineString = me.ge.createLineString('');
	// Add LineString points
	for (i = 0; i < linePoints.length; i++) {
		// alert(V3.toString(linePoints[i]));
		lineString.getCoordinates().pushLatLngAlt(
				linePoints[i][0],
				linePoints[i][1],
				i == 0 ? me.ge.getGlobe().getGroundAltitude(linePoints[i][0],
						linePoints[i][1]) : FLIGHTHEIGHT);
	}
	lineString.setTessellate(true);
	lineString.setAltitudeMode(me.ge.ALTITUDE_ABSOLUTE);

	var multiGeometry = me.ge.createMultiGeometry('');
	multiGeometry.getGeometries().appendChild(lineString);

	me.routePlacemark = me.ge.createPlacemark('');
	me.routePlacemark.setGeometry(multiGeometry);

	me.routePlacemark.setStyleSelector(me.ge.createStyle(''));
	var lineStyle = me.routePlacemark.getStyleSelector().getLineStyle();
	lineStyle.setWidth(5);
	lineStyle.getColor().set('99f88fd4');

	me.ge.getFeatures().appendChild(me.routePlacemark);

	me.routePlacemark.setName('航线');
}


var lastPos =null;
var linePlacemarks = new Array(0);
//@param : [lat,lon,alt]
Global.prototype.drawFlyRoute=function( pos ){
	var me =this;
	// Create the LineString
	var lineString = me.ge.createLineString('');
	var lineStringPlacemark = me.ge.createPlacemark('');
	linePlacemarks[linePlacemarks.length] = lineStringPlacemark;
	lineStringPlacemark.setGeometry(lineString);
	me.route = lineString;
	
	if(lastPos ==null) lastPos = [startPos[0], startPos[1],0];
	lineString.getCoordinates().pushLatLngAlt(lastPos[0], lastPos[1], lastPos[2]);
	//lineString.getCoordinates().pushLatLngAlt(pos[0], pos[1], pos[2]);
	
	lineString.setTessellate(true);
	lineString.setAltitudeMode(me.ge.ALTITUDE_ABSOLUTE);

	lineStringPlacemark.setStyleSelector(me.ge.createStyle(''));
	var lineStyle = lineStringPlacemark.getStyleSelector().getLineStyle();
	lineStyle.setWidth(2);
	lineStyle.getColor().set('ffff0000');

	me.ge.getFeatures().appendChild(lineStringPlacemark);
	lastPos  = pos;
}

Global.prototype.drawLine=function( lat1,lon1,lat2,lon2){
	var me =this;
	// Create the LineString
	var lineString = me.ge.createLineString('');
	var lineStringPlacemark = me.ge.createPlacemark('');
	lineStringPlacemark.setGeometry(lineString);
	
	lineString.getCoordinates().pushLatLngAlt(lat1, lon1, 1000);
	lineString.getCoordinates().pushLatLngAlt(lat2, lon2, 1000);
	
	lineString.setTessellate(true);
	lineString.setAltitudeMode(me.ge.ALTITUDE_ABSOLUTE);

	lineStringPlacemark.setStyleSelector(me.ge.createStyle(''));
	var lineStyle = lineStringPlacemark.getStyleSelector().getLineStyle();
	lineStyle.setWidth(2);
	lineStyle.getColor().set('ffffff00');

	me.ge.getFeatures().appendChild(lineStringPlacemark);
}

Global.prototype.cleanLines=function( ){
	var me = this;
	for (var i=0 ;i<linePlacemarks.length ;i++){
		if(linePlacemarks[i]){
			me.ge.getFeatures().removeChild(linePlacemarks[i]);
		}
		
	}
}
Global.prototype.drawTarget = function() {
	var me = this;
	// Create the placemark.
	var placemark = me.ge.createPlacemark('');
	placemark.setName(currentTarget[2]?currentTarget[2]:'');
	// Set the placemark's location.
	var point = me.ge.createPoint('');
	point.setLatitude(currentTarget[0]);
	point.setLongitude(currentTarget[1]);
	point.setAltitude(2000);
	placemark.setGeometry(point);

	me.targetPlacemarks[me.targetPlacemarks.length] = placemark;
	// Add the placemark to Earth.
	me.ge.getFeatures().appendChild(placemark);
};

Global.prototype.removeTargets = function() {
	var me = this;
	if (me.targetPlacemarks.length == 0)
		return;
	for (var i = 0; i < me.targetPlacemarks.length; i++) {
		me.ge.getFeatures().removeChild(me.targetPlacemarks[i]);
	}
};

Global.prototype.drawZone = function(points) {
	//绘制空域
	var me = this;
	points= [[ 31.43458,104.736,3000],[ 31.41,104.83,3000],[ 31.40,104.92,3000],
	         [ 31.42,104.99,3000],[ 31.25,104.978,3000],[ 31.186,104.85,3000]];
	
	// Create the placemark.
	var polygonPlacemark = me.ge.createPlacemark('');

	// Create the polygon.
	var polygon = me.ge.createPolygon('');
	polygon.setExtrude(true);
	polygon.setAltitudeMode(me.ge.ALTITUDE_RELATIVE_TO_GROUND);
	polygonPlacemark.setGeometry(polygon);

	// Add points for the outer shape.
	var outer = me.ge.createLinearRing('');
	outer.setAltitudeMode(me.ge.ALTITUDE_RELATIVE_TO_GROUND);
	for(var i = 0;i < points.length; i++){
		outer.getCoordinates().pushLatLngAlt(points[i][0],points[i][1],points[i][2]);
	}
	polygon.setOuterBoundary(outer);

	//Create a style and set width and color of line
	polygonPlacemark.setStyleSelector(me.ge.createStyle(''));
	var lineStyle = polygonPlacemark.getStyleSelector().getLineStyle();
	lineStyle.setWidth(1);
	lineStyle.getColor().set('99D491DB');
	var polyStyle = polygonPlacemark.getStyleSelector().getPolyStyle();
	polyStyle.getColor().set('99D491DB');
	
	// Add the placemark to Earth.
	me.ge.getFeatures().appendChild(polygonPlacemark);
	
} ;

Global.prototype.addPanel = function() {
	var me = this;
		if(me.panelMask){
			return;
		}
		
		// Create the panelMask.
		me.panelMask = me.ge.createScreenOverlay('');
		// Specify a path to the image and set as the icon.
		if(!me.panelMask){
			return;
		}
		
		var icon = me.ge.createIcon('');
		icon.setHref(basePath + 'earthview/resource/icon_cessna172_.png');
		me.panelMask.setIcon(icon);

		// Important note: due to a bug in the API, screenXY and overlayXY
		// have opposite meanings than their KML counterparts. This means
		// that in the API, screenXY defines the point on the overlay image
		// that is mapped to a point on the screen, defined by overlayXY.

		// This bug will not be fixed until the next major revision of the
		// Earth API, so that applications built upon version 1.x will
		// not break.

		// Set the panelMask's position in the window.
		me.panelMask.getOverlayXY().setXUnits(me.ge.UNITS_PIXELS);
		me.panelMask.getOverlayXY().setYUnits(me.ge.UNITS_PIXELS);
		me.panelMask.getOverlayXY().setX(512);
		me.panelMask.getOverlayXY().setY(0);

		// Specify the point in the image around which to rotate.
		me.panelMask.getRotationXY().setXUnits(me.ge.UNITS_FRACTION);
		me.panelMask.getRotationXY().setYUnits(me.ge.UNITS_FRACTION);
		me.panelMask.getRotationXY().setX(0.5);
		me.panelMask.getRotationXY().setY(0.5);

		// Set the overlay's size in pixels.
		me.panelMask.getSize().setXUnits(me.ge.UNITS_PIXELS);
		me.panelMask.getSize().setYUnits(me.ge.UNITS_PIXELS);
		me.panelMask.getSize().setX(1536);
		me.panelMask.getSize().setY(321);

		// Rotate the overlay.
		me.panelMask.setRotation(0);

		// Add the panelMask to Earth.
		me.ge.getFeatures().appendChild(me.panelMask);
}

Global.prototype.removePanel = function() {
	var me = this;
	if(me.panelMask){
		me.ge.getFeatures().removeChild(me.panelMask);
		me.panelMask=null;
	}
};


Global.prototype.showSun = function () {
	var me=this;
	me.ge.getSun().setVisibility(true);
}


Global.prototype.rotate = function() {
	var me = this;
	//TODO 旋转面板
	
};

//simulate(点集合，高度)
Global.prototype.simulate = function (routePoints,height){
	
	
};

Global.prototype.cameraMove=function(lat,lon,tilt, range ){
	var me = this;
	var la = me.ge.createLookAt('');
	la.set(lat, lon,
		  HEIGHT /* altitude */,
		 me.ge.ALTITUDE_RELATIVE_TO_GROUND,
         0 ,
         tilt?tilt:80, /* tilt */
         range?range:50 /* range */
         );
	me.ge.getView().setAbstractView(la);
};
//舱内视角
Global.prototype.cameraOnboard=function(){
	var me = this;
	var lo = plane.model.getLocation();
	var la = me.ge.createLookAt('');
	la.set(lo.getLatitude(), lo.getLongitude(),
		 lo.getAltitude()+10 /* altitude */,
		 me.ge.ALTITUDE_ABSOLUTE,
		 plane.model.getOrientation().getHeading() ,
         80, /* tilt */
         0 /* range */
         );
	me.ge.getView().setAbstractView(la);
	me.cameraMode = "board";
};
//机尾视角
Global.prototype.cameraBack=function(){
	var me = this;
	plane.cameraCut();
	camHeadingOffset=0;
	me.cameraMode = "back";
};
//左侧视角
Global.prototype.cameraLeft=function(){
	var me = this;
	var lo = plane.model.getLocation();
	var la = me.ge.createLookAt('');
	la.set(lo.getLatitude(), lo.getLongitude(),
		 lo.getAltitude() /* altitude */,
		 me.ge.ALTITUDE_ABSOLUTE,
		 fixAngle( plane.model.getOrientation().getHeading() + 90 ),
         60, /* tilt */
         100 /* range */
         );
	me.ge.getView().setAbstractView(la);
	//camHeadingOffset=90;
	me.cameraMode = "left";
};
//右侧视角
Global.prototype.cameraRight=function(){
	var me = this;
	var lo = plane.model.getLocation();
	var la = me.ge.createLookAt('');
	la.set(lo.getLatitude(), lo.getLongitude(),
		 lo.getAltitude() /* altitude */,
		 me.ge.ALTITUDE_ABSOLUTE,
		 fixAngle(plane.model.getOrientation().getHeading() - 90),
         60, /* tilt */
         100 /* range */
         );
	me.ge.getView().setAbstractView(la);
	//camHeadingOffset=-90;
	me.cameraMode = "right";
};

Global.prototype.addRoutePoint=function( pos ){
	var me =this;
	me.route.getCoordinates().pushLatLngAlt(pos[0], pos[1], pos[2]);
	
};


///---------------------------draw with flot 2.8.3-----------
var ec=null;
function drawElevation(){
	if(!ec)ec= new ElevationChart;
	ec.draw();
}

function ElevationChart (){
	var me= this;
	
	me.elevationDataArray =[]; //地面高程数据系
	me.flightDataArray =[]; 	//航线数据系
	me.pointsDataArray = [] ;	//地面标志点数据系
	me.myPlot=null;
	
	me.img = new Image();
	me.img.src = basePath +'earthview/resource/plane_icon.png';
}


ElevationChart.prototype.draw = function(){
	var me = this;
	me.ptos = [];
	me.elevationDataArray =[]; 
	me.flightDataArray =[]; 	
	me.pointsDataArray = [];
	me.pointsTicks = [];
	current_sample_index=0;
	getLinePoints();
	if(linePoints.length>1){
		me.elevation();
	}
	
	me.virtual_point_distance = -1;
	me.virtual_point_num = -1;
};


//------------------- Google Maps API------------------
var SAMPLE_COUNT = 256; //路径采样密度
//var mLength = 10 ; // 路径宽度采样点密度
//var mPoints = new Array()[mLength+1][SAMPLE_COUNT]; //采样点矩阵
ElevationChart.prototype.elevation=function (){
	var me = this;
	var elevator = new google.maps.ElevationService();
	
	var gPoint = null; //google.maps.LatLng
	var path = [];
	var dist = 0;
	dojo.forEach(linePoints, function(pt,i){
	    gPoint = new google.maps.LatLng(pt[0],pt[1]);
	    path[i]=gPoint ; 
	  });
	
	var pathRequest = {
			'path': path,
			'samples': SAMPLE_COUNT
	};
	// Initiate the path request.
	elevator.getElevationAlongPath(pathRequest, elevationPathCallback);
	
	var locationsRequest = {
			'locations': path
	};
	// Initiate the locations request.
	elevator.getElevationForLocations(locationsRequest,elevationLocationsCallback);
}

function elevationPathCallback (results, status){
	if (status == google.maps.ElevationStatus.OK) {
		var distance = 0;
		var distance_unit = -1;
		ec.virtual_point_distance = -1;
		ec.virtual_point_num = -1;
		var start_ap_ev=results[0].elevation;
		var end_ap_ev=results[results.length-1].elevation;
  	    for (var i=0; i<results.length ; i++) {
  	    	var pto = new Object();
  	    	//pto.distance =  redondear((distance * i)/(1000 * (SAMPLE_COUNT - 1)));
  	    	if(i > 0 ){
  	    		distance+= getDistance(results[i-1].location.lat(), results[i-1].location.lng(), 
  	    				results[i].location.lat(), results[i].location.lng() );
  	    		if(distance_unit < 0){
  	    			distance_unit=distance;
  	    		}
  	    	}
  	    	pto.distance = distance ;
  	    	pto.location = results[i].location;
  	    	pto.elevation = results[i].elevation;
  	    	ec.ptos[i] = pto;      	    	
  	    	
  	    	ec.elevationDataArray[i]=[pto.distance,pto.elevation,results[i].location.lat(),results[i].location.lng()];
  	    	if(i==0 || i==results.length-1){
  	    		ec.flightDataArray[i]=ec.elevationDataArray[i];
  	    	}else if(i + ec.virtual_point_num >= results.length){ //模拟降落 
  	    		ec.flightDataArray[i]=[pto.distance, FLIGHTHEIGHT - (i + ec.virtual_point_num -results.length +1) * ((FLIGHTHEIGHT- end_ap_ev) / (ec.virtual_point_num - 1)), 
  	    		                       results[i].location.lat(),results[i].location.lng()];
  	    	}else if(distance >2000){
  	    		if(ec.virtual_point_num<0){
  	    			ec.virtual_point_num = i;
  	    			ec.virtual_point_distance=distance;
  	    			for(var n =1; n< ec.virtual_point_num;n++){//从起飞到爬升到航线高度，在机场2000米范围内虚拟 virtual_point_num 个点 ， 模拟起飞
  	    				ec.flightDataArray[n]=[n * distance_unit, n * ((FLIGHTHEIGHT- start_ap_ev)  / (ec.virtual_point_num - 1)) + start_ap_ev, 
  	    				                       results[n].location.lat(),results[n].location.lng()];
  	    			}
  	    			
  	    		}
  	    		ec.flightDataArray[i]=[pto.distance,FLIGHTHEIGHT,results[i].location.lat(),results[i].location.lng()];
  	    	}
  	    	
  	    	if(FLIGHTHEIGHT > 0 && FLIGHTHEIGHT - pto.elevation <= 600) { // 当飞行高度低于最高障碍600米时，报警
				addAreaSpaceConflictData("rs_ob",FLIGHTHEIGHT - pto.elevation ,{x:results[i].location.lat() ,y:results[i].location.lng() } );
			}
  	    	
  	    }
  	    
  	    //mPoints [ 0 ] = elevationDataArray;
  	    
  	  //extendPath( ec.elevationDataArray);
  	    
  	 }
	
	ec.doDraw();
}

function extendPath(samples){
	if(global)global.drawRoute();
	var d1,tempdis1,tempdis2,hdvar,sinvar,cosvar,dx,dy;
	for (var k = 0; k < samples.length; k++) {
            
            if(k ==0){
            	d1 = (new Date().getTime());
            	tempdis1 = getDistance(samples[0][2],samples[0][3], samples[0][2],samples[1][3])/1000;//x
			    tempdis2 = getDistance(samples[1][2],samples[1][3], samples[0][2],samples[1][3])/1000;//y
			   
				hdvar = Math.atan2(tempdis2,tempdis1);//获取弧度point(x,y) atan2(y,x)
				if((samples[1][2] > samples[0][2]) && (samples[1][3] < samples[0][3])){
			    	hdvar = Math.PI - hdvar
			    }
			    if((samples[1][2] < samples[0][2]) && (samples[1][3] > samples[0][3])){
			    	hdvar = Math.PI - hdvar
			    }
				sinvar = Math.sin(hdvar);//
				cosvar = Math.cos(hdvar);//
            }else{
			    tempdis1 = getDistance(samples[k-1][2], samples[k-1][3], samples[k-1][2], samples[k][3])/1000;//x
			    tempdis2 = getDistance(samples[k][2], samples[k][3], samples[k-1][2], samples[k][3])/1000;//y

				hdvar = Math.atan2(tempdis2,tempdis1);//获取弧度point(x,y) atan2(y,x)
				if((samples[k][2] > samples[k-1][2]) && (samples[k][3] < samples[k-1][3])){
			    	hdvar = Math.PI - hdvar
			    }
			    if((samples[k][2] < samples[k-1][2]) && (samples[k][3] > samples[k-1][3])){
			    	hdvar = Math.PI - hdvar
			    }
				sinvar = Math.sin(hdvar);//
				cosvar = Math.cos(hdvar);//
            }
			
			//samples[k]
			var pt = new esri.geometry.Point(samples[k][3], samples[k][2], map.spatialReference);
			
			dx = sinvar*10;//经度方向偏移量（单位千米＄1�7
			dy = cosvar*10;//纬度方向偏移量（单位千米＄1�7
			var hd = samples[k][2]*Math.PI/180.0;//角度转换为弧庄1�7
			dx = dx/111.31955*(Math.cos(hd));//经度偏移倄1�7
			dy = dy/111.31955;//纬度偏移倄1�7
			var pt1 = pt.offset(-dx,dy);
			var pt2 = pt.offset(dx,-dy);
			if(global)global.drawLine(pt1.y,pt1.x,pt2.y,pt2.x);
			//var myJsonStr = '{"points":' + points + '}';
			//temppoints = '[['+pt1.x+','+pt1.y+'],['+pt2.x+','+pt2.y+']]';
			//gis_drawSection2(temppoints,planHeight,samples,k,drawChart);
		
	}
}

function elevationLocationsCallback (results, status){
	if (status == google.maps.ElevationStatus.OK) {
		var distance = 0;
  	    for (var i=0; i<results.length ; i++) {
  	    	var pto = new Object();
  	    	//pto.distance =  redondear((distance * i)/(1000 * (SAMPLE_COUNT - 1)));
  	    	if(i > 0 ){
  	    		distance+= getDistance(results[i-1].location.lat(), results[i-1].location.lng(), 
  	    				results[i].location.lat(), results[i].location.lng() );
  	    	}
  	    	pto.distance = distance ;
  	    	pto.location = results[i].location;
  	    	pto.elevation = results[i].elevation;
  	    	
  	    	ec.pointsDataArray[i]=[pto.distance,pto.elevation,results[i].location.lat(),results[i].location.lng()];
  	    	ec.pointsTicks[i]= [pto.distance, linePoints[i][2]];
  	    	
  	    }
  	 }
	ec.doDraw();
}

ElevationChart.prototype.getIndexByDist=function(dist){
	var me = this;
	
	if(me.pointsDataArray.length>0){
		for(var i = 0 ;i<me.pointsDataArray.length;i++){
			if( dist < ec.pointsDataArray[i][0] ){
				return i>1? i: 1;
			}
		}
	}else{
		return 1;
	}
};

ElevationChart.prototype.doDraw =function (){
	var me = this;
	document.getElementById('chartNode').style.display='block';
	me.myPlot = $.plot($('#chartNode'), 
			[ {label : '高程', data: me.elevationDataArray, hoverable: true, lines: { fill: 0.9 }, color:"rgba(240,190,77,0.9)"  }
			  ,{label : '航线', data: me.flightDataArray, hoverable: true  , xaxis: 1,color: "rgba(63,156,233,0.9)"}
			  ,{data:me.pointsDataArray,points:  { show:true , symbol: "diamond" ,radius: 5 },xaxis: 2 ,color : "rgba(235,67,67,0.9)"}
			],
			{	//crosshair: {mode: 'x'},
				grid: {hoverable: true,clickable: true,backgroundColor : {colors : ["rgba(137,203,235,1)", "rgba(182,237,243,1)"]} }
				,
				xaxes : [{tickFormatter: function (val, axis) { return val/1000;},font: {weight: "bold",color: "rgba(255,255,255,1)"},labelHeight: 10},
				         {font: {color: "rgba(255,255,255,1)"}, ticks : me.pointsTicks,labelHeight: 10 }]
				,yaxis :{font: {weight: "bold",color: "rgba(255,255,255,1)"}}
				,legend: { show:false,noColumns : 2}
			});
	$("<div id='tooltipEC'></div>").css({
		position: "absolute",
		display: "none",
		border: "1px solid #fdd",
		padding: "2px",
		"background-color": "#fee",
		"white-space": "nowrap",
		opacity: 0.80
	}).appendTo("body");
	
	
	$("#chartNode").bind("plothover", function (event, pos, item) {
	    // axis coordinates for other axes, if present, are in pos.x2, pos.x3, ...
	    // if you need global screen coordinates, they are pos.pageX, pos.pageY

		if (item) {
			if(item.seriesIndex==2){
				$("#tooltipEC").html(linePoints[item.dataIndex][2]+"</br>" 
						+ "海拔："+ me.pointsDataArray[item.dataIndex][1].toFixed(0) +" m</br>" 
						+ "里程："+ (me.pointsDataArray[item.dataIndex][0]/1000).toFixed(1) +" km</br>")
				.css({top: item.pageY-50, left: item.pageX+15+$("#tooltipEC").width() > $(window).width() ? $(window).width()-$("#tooltipEC").width()- 10 : item.pageX+5})
				.fadeIn(200);
			};
			if(item.seriesIndex==0){
				$("#tooltipEC").html("海拔："+ me.elevationDataArray[item.dataIndex][1].toFixed(0) +" m</br>" 
						+ "里程："+ (me.elevationDataArray[item.dataIndex][0]/1000).toFixed(1) +" km</br>")
				.css({top: item.pageY-36, left: item.pageX+15+$("#tooltipEC").width() > $(window).width() ? $(window).width()-$("#tooltipEC").width()- 10 : item.pageX+5})
				.fadeIn(200);
			};
		} else {
			$("#tooltipEC").hide();
		}
	});
	
	$("#chartNode").bind("plotclick", function (event, pos, item) {
		
	    if (item) {
	    	me.myPlot.unhighlight();
	    	me.myPlot.highlight(item.series, item.datapoint);
	    	current_sample_index= item.dataIndex;
	    	var dataArray = item.series.data;
	        lastPos= [dataArray[item.dataIndex][2], dataArray[item.dataIndex][3],FLIGHTHEIGHT];
	        global.drawFlyRoute(lastPos);
	        cc.jumpTo(dataArray[item.dataIndex][2], dataArray[item.dataIndex][3],dataArray[item.dataIndex][0]);
	    }
	});
	me.redraw();
	me.markPlane(current_sample_index);
	me.drawCloud();
};

ElevationChart.prototype.drawCloud=function (){
	var me=this;
	
	if(me.elevationDataArray.length == 0  ) return;
	var canvas = me.myPlot.getCanvas();
	var ctx = canvas.getContext("2d");
	//start 
	var x = me.myPlot.pointOffset({ x: me.virtual_point_distance,  y: 1500 }).left;
	var y = me.myPlot.pointOffset({ x: me.virtual_point_distance,  y: 1500 }).top;
	var x0 = me.myPlot.pointOffset({x:0,y:0}).left;
	//云
	ctx.fillStyle="#ffffff";
	ctx.fillRect(x0 , y ,x -x0 ,5);

	var start_airport_pos = me.myPlot.pointOffset({x:me.elevationDataArray[0][0],y:me.elevationDataArray[0][1]});
	ctx.fillStyle="rgba(75,247,111,0.8)";
	ctx.beginPath();
	ctx.moveTo(start_airport_pos.left,start_airport_pos.top - 10);
	ctx.lineTo(start_airport_pos.left,start_airport_pos.top - 20);
	ctx.arc(start_airport_pos.left, start_airport_pos.top , 20, Math.PI * 1.5, Math.PI * 2 ,  false);
	ctx.lineTo(start_airport_pos.left + 10 ,start_airport_pos.top );
	ctx.arc(start_airport_pos.left, start_airport_pos.top , 10, 0, Math.PI * 1.5 ,  true );
	ctx.closePath();
	ctx.fill();
	
	//end 
	var xe = me.myPlot.pointOffset({x:ec.elevationDataArray[ec.elevationDataArray.length-1][0],y:0}).left;
	var ye = me.myPlot.pointOffset({x:0,y:1200}).top;
	ctx.fillStyle="#ffffff";
	ctx.fillRect(xe - (x -x0) , ye ,x -x0 ,5);
	
	var end_airport_pos = me.myPlot.pointOffset({x:me.elevationDataArray[ec.elevationDataArray.length-1][0],y:me.elevationDataArray[ec.elevationDataArray.length-1][1]});
	ctx.fillStyle="rgba(251,79,79,0.8)";
	ctx.beginPath();
	ctx.moveTo(end_airport_pos.left,end_airport_pos.top - 10);
	ctx.lineTo(end_airport_pos.left,end_airport_pos.top - 20);
	ctx.arc(end_airport_pos.left, end_airport_pos.top , 20, Math.PI * 1.5, Math.PI  ,  true);
	ctx.lineTo(end_airport_pos.left - 10 ,end_airport_pos.top );
	ctx.arc(end_airport_pos.left, end_airport_pos.top , 10, Math.PI , Math.PI * 1.5 ,  false );
	ctx.closePath();
	ctx.fill();
};

ElevationChart.prototype.redraw=function (){
	var me = this;
	me.myPlot.draw();
	me.drawRightBottomLabel();
	me.drawCloud();
};

ElevationChart.prototype.drawRightBottomLabel= function (){
	return ;
	var me = this;
	var canvas = me.myPlot.getCanvas();
	var ctx = canvas.getContext("2d");
	ctx.globalAlpha=1;
	ctx.globalCompositeOperation="source-over";
	ctx.fillStyle="#ffffff";
	ctx.fillRect(canvas.width-40, canvas.height-16,40,16);
	ctx.fillStyle="#000000";
	ctx.fillText(Math.round(distance/1000,1)+'km', canvas.width-32, canvas.height-4);
};

ElevationChart.prototype.markPlane=function (_i){
	var me = this;
	
	if(me.flightDataArray.length==0)return;
	var canvas = me.myPlot.getCanvas();
	var ctx = canvas.getContext("2d");
	
	var pos = me.myPlot.pointOffset({ x: me.flightDataArray[_i][0], y: me.flightDataArray[_i][1] });
	ctx.drawImage(me.img,pos.left-8,pos.top-8);
	current_sample_index=_i;
};

ElevationChart.prototype.markTarget =  function(_i){
	var me = this;
	me.myPlot.unhighlight();
	me.myPlot.highlight(2, _i);
	
	var pageX = me.myPlot.pointOffset({x:me.pointsDataArray[_i][0],y:me.pointsDataArray[_i][1]}).left+$("#chartNode").offset().left;
	var pageY = me.myPlot.pointOffset({x:me.pointsDataArray[_i][0],y:me.pointsDataArray[_i][1]}).top+$("#chartNode").offset().top;
	$("#tooltipEC").hide();
	$("#tooltipEC").html("目标："+linePoints[_i][2]+"</br>" 
			+ "海拔："+ me.pointsDataArray[_i][1].toFixed(0) +" m</br>" 
			+ "里程："+ (me.pointsDataArray[_i][0]/1000).toFixed(1) +" km</br>")
	.css({top: pageY-50, left: pageX+15+$("#tooltipEC").width() > $(window).width() ? $(window).width()-$("#tooltipEC").width()- 10 : pageX+5})
	.fadeIn(200);
}
//--------------------------ControlCenter ---------------//
//页面控制调度
function ControlCenter(){
	var me = this;
	
}

var cc = new ControlCenter();

ControlCenter.prototype.globalInit=function(){
	drawElevation();
	flight_distance=0;
	current_sample_index=0;
	
	$("#map3dControl input[type='input']").val(0);
};


//跳转航线点  俯视图 ，高程图 -> 三维
ControlCenter.prototype.jumpTo = function (lat ,lon , _distance ){
	if(_distance){
		flight_distance=_distance;
		if(plane){
			if(plane.isMove()){
				stopPlane();
				plane.teleportToRoutePoint(lat,lon,ec.getIndexByDist(_distance));
				startPlane();
			}else{
				plane.teleportToRoutePoint(lat,lon,ec.getIndexByDist(_distance));
			}
		}
		cc.planeMove();
		global.removeTargets();
		global.drawTarget();
		ec.markTarget(currentIndex);
	}else{//没传distance ，不动飞机，移动视角到该点
		if(global)global.cameraMove(lat, lon,10,2000);
	}
	
	
};
ControlCenter.prototype.planeMove = function(){
	showCurrent(plane.model.getLocation().getLongitude(), plane.model.getLocation().getLatitude());
	//global.addRoutePoint([plane.model.getLocation().getLatitude(),plane.model.getLocation().getLongitude(), plane.model.getLocation().getAltitude()]);
	var i=current_sample_index==0? 0 : current_sample_index-1;
	for(;i<ec.flightDataArray.length;i++){
		if(flight_distance <=ec.flightDataArray[i][0]){
			current_sample_index=i;
			ec.redraw();
			ec.markPlane(current_sample_index);
			break;
		}
	}
};

ControlCenter.prototype.targetChange = function(_i){
	global.removeTargets();
	global.drawTarget();
	ec.markTarget(_i);
};

ControlCenter.prototype.planeStart = function(){
	lastPos=null;
	global.cleanLines();
	linePlacemarks = [];
	prepareRoute();
};
