//global.js //地球环境
//using dojo 
//using google earth api

google.load("earth", "1", {'other_params' : 'sensor=false'});
google.load('maps', '2.x', {'other_params' : 'sensor=false'});

window.ge =null;
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
		draw();
	}
	
}


function Global() {
	var me = this;

	me.geocoder = new GClientGeocoder();
	me.ge = null;
	me.plane = null;
	me.routePlacemark = null;
	// 路径点
	me.targetPlacemarks = [];
	me.panelMask = null;
	
	cc.global = me; 
}

Global.prototype.show = function() {
	//创建包含地球的div
	if (!dojo.byId('map3d_container')) {
		dojo.create("div", {
			id : "map3d_container",
			style : {
				position : "absolute",
				width : dojo.byId('fPlanContextText').style.width,
				height : dojo.byId('fPlanContext').style.height,
				margin : "0 auto"

			}
		}, dojo.byId('fPlanContext'));
	}
	if (!dojo.byId('map3d')) {
		dojo.create("div", {
			id : "map3d",
			style : {
				height : "80%",
				width : "100%"
			}
		}, dojo.byId('map3d_container'));

		//显示控制板
		dojo.place(dojo.byId('map3dControl'), dojo.byId('map3d'), 'after');
		dojo.setStyle(dojo.byId("map3dControl"), "display", "block");
		dojo.setStyle(dojo.byId("map3dControl"), "z-index", "999");
		dojo.setStyle(dojo.byId("map3dControl"), "background-color", "#374068");
	}

};

Global.prototype.hide = function() {
	if (ge != null) {
		dojo.place(dojo.byId('map3dControl'), dojo.byId('globalDiv'), 'after');
		dojo.setStyle(dojo.byId("map3dControl"), "display", "none");
		dojo.destroy("map3d_container");
		ge = null;
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
		window.ge = object;
		me.ge.getWindow().setVisibility(true);
		// ge.getOptions().setMouseNavigationEnabled(false);
		me.ge.getLayerRoot().enableLayerById(ge.LAYER_BUILDINGS, true); // /建筑
		me.ge.getLayerRoot().enableLayerById(ge.LAYER_BORDERS, true); // 边界

		me.ge.getNavigationControl().setVisibility(ge.VISIBILITY_AUTO);//显示罗盘
		
		//地形精度
		ge.getLayerRoot().enableLayerById(ge.LAYER_TERRAIN, true);
		ge.getOptions().setTerrainExaggeration(1.0);//2.0
		
		me.ge.getOptions().setStatusBarVisibility(true); //显示状态栏 
		me.ge.getOptions().setFlyToSpeed(ge.SPEED_TELEPORT);
		me.plane = new Plane();
		plane= me.plane;
		// addPanel();
	},
	// failureCallback
	function(err) {

	});
};

function el(e) {
	return document.getElementById(e);
}

function submitLocation() {
	doGeocode(el('address').value);
}

function doGeocode(address) {
	geocoder.getLatLng(address, function(point) {
		if (point) {
			if (ge != null && plane != null) {
				plane.teleportTo(point.y, point.x);
			}
		}
	});
}

function updateOptions() {
	var options = ge.getOptions();
	var form = el('options');

	options.setStatusBarVisibility(form.statusbar.checked);
	if (form.nav.checked) {
		ge.getNavigationControl().setVisibility(ge.VISIBILITY_SHOW);
	} else {
		ge.getNavigationControl().setVisibility(ge.VISIBILITY_HIDE);
	}
}

Global.prototype.drawRoute = function() {
	var me = this;
	me.drawRoute2();
	return;
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
	lineStyle.setWidth(2);
	lineStyle.getColor().set('ffff0000');

	me.ge.getFeatures().appendChild(me.routePlacemark);

	me.routePlacemark.setName('航线');
}


var lastPos =null;
var linePlacemark = [];
//@param : [lat,lon,alt]
Global.prototype.drawRoute2=function( pos ){
	var me =this;
	// Create the LineString
	var lineString = me.ge.createLineString('');
	var lineStringPlacemark = me.ge.createPlacemark('');
	linePlacemark[linePlacemark.length] = lineStringPlacemark;
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

Global.prototype.cameraMove=function(lat,lon){
	var me = this;
	var la = me.ge.createLookAt('');
	la.set(lat, lon,
		  HEIGHT /* altitude */,
		 me.ALTITUDE_RELATIVE_TO_GROUND,
         0 ,
         80, /* tilt */
         50 /* range */
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
		 ge.ALTITUDE_ABSOLUTE,
		 plane.model.getOrientation().getHeading() ,
         80, /* tilt */
         0 /* range */
         );
	me.ge.getView().setAbstractView(la);
	cameraMode = "board";
};
var cameraMode ; //left right back;
//机尾视角
Global.prototype.cameraBack=function(){
	var me = this;
	plane.cameraCut();
	camHeadingOffset=0;
	cameraMode = "back";
};
//左侧视角
Global.prototype.cameraLeft=function(){
	var me = this;
	var lo = plane.model.getLocation();
	var la = me.ge.createLookAt('');
	la.set(lo.getLatitude(), lo.getLongitude(),
		 lo.getAltitude() /* altitude */,
		 ge.ALTITUDE_ABSOLUTE,
		 fixAngle( plane.model.getOrientation().getHeading() + 90 ),
         60, /* tilt */
         100 /* range */
         );
	me.ge.getView().setAbstractView(la);
	//camHeadingOffset=90;
	cameraMode = "left";
};
//右侧视角
Global.prototype.cameraRight=function(){
	var me = this;
	var lo = plane.model.getLocation();
	var la = me.ge.createLookAt('');
	la.set(lo.getLatitude(), lo.getLongitude(),
		 lo.getAltitude() /* altitude */,
		 ge.ALTITUDE_ABSOLUTE,
		 fixAngle(plane.model.getOrientation().getHeading() - 90),
         60, /* tilt */
         100 /* range */
         );
	me.ge.getView().setAbstractView(la);
	//camHeadingOffset=-90;
	cameraMode = "right";
};

Global.prototype.addRoutePoint=function( pos ){
	var me =this;
	me.route.getCoordinates().pushLatLngAlt(pos[0], pos[1], pos[2]);
	
};


///---------------------------draw with flot 2.8.3-----------

var elevationDataArray =[]; //地面高程数据系
var flightDataArray =[]; 	//航线数据系
var pointsDataArray = [] ;	//地面标志点数据系
var myPlot ;
function draw(){
	ptos = [];
	pointsDataArray = [];
	getLinePoints();
	elevation();
	
//	$("#elevation").bind( "plothover", function ( evt, position, item ) {
//		if ( item ) {
//			// Lock the crosshair to the data point being hovered
//			myFlot.lockCrosshair({
//				x: item.datapoint[ 0 ],
//				y: item.datapoint[ 1 ]
//			});
//			
//			ctx.beginPath();
//	        ctx.fillStyle = '#222222'
//	        ctx.arc(item.pageX - $("#elevation").offset().left , item.pageY - $("#elevation").offset().top, 5, 0, Math.PI*2, true); 
//	        ctx.closePath();
//	        ctx.fill();
//	        
//		} else {
//			// Return normal crosshair operation
//			myFlot.unlockCrosshair();
//		}
//	});
	
	
}


//------------------- Google Maps API------------------
var SAMPLE_COUNT = 256; 
function elevation(){
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
		distance = 0;
  	    for (var i=0; i<results.length ; i++) {
  	    	var pto = new Object();
  	    	//pto.distance =  redondear((distance * i)/(1000 * (SAMPLE_COUNT - 1)));
  	    	if(i > 0 ){
  	    		distance+= getDistance(results[i-1].location.d, results[i-1].location.e, 
  	    				results[i].location.d, results[i].location.e );
  	    	}
  	    	pto.distance = distance ;
  	    	pto.location = results[i].location;
  	    	pto.elevation = results[i].elevation;
  	    	ptos[i] = pto;      	    	
  	    	
  	    	elevationDataArray[i]=[pto.distance,pto.elevation,results[i].location.d,results[i].location.e];
  	    	if(i==0){
  	    		flightDataArray[i]=elevationDataArray[i];
  	    	}else{
  	    		flightDataArray[i]=[pto.distance,FLIGHTHEIGHT,results[i].location.d,results[i].location.e];
  	    	}
  	    	
  	    }
  	 }
	
	drawElevation();
}

function elevationLocationsCallback (results, status){
	if (status == google.maps.ElevationStatus.OK) {
		distance = 0;
  	    for (var i=0; i<results.length ; i++) {
  	    	var pto = new Object();
  	    	//pto.distance =  redondear((distance * i)/(1000 * (SAMPLE_COUNT - 1)));
  	    	if(i > 0 ){
  	    		distance+= getDistance(results[i-1].location.d, results[i-1].location.e, 
  	    				results[i].location.d, results[i].location.e );
  	    	}
  	    	pto.distance = distance ;
  	    	pto.location = results[i].location;
  	    	pto.elevation = results[i].elevation;
  	    	ptos[i] = pto;      	    	
  	    	
  	    	pointsDataArray[i]=[pto.distance,pto.elevation,results[i].location.d,results[i].location.e];
  	    	
  	    }
  	 }
	drawElevation();
}

function drawElevation(){
	myPlot = $.plot($('#elevation'), 
			[ {label : '高程', data: elevationDataArray, hoverable: true }
			  ,{label : '航线', data: flightDataArray, hoverable: true ,highlightColor: "rgba(0, 255, 0, 0.8)" }
			  ,{data:pointsDataArray,points: { show: true }}
			],
			{	crosshair: {mode: 'x'}
				,grid: {hoverable: true,clickable: true }
				,xaxis :{tickFormatter: function (val, axis) {   
					return val/1000;}}
				,yaxis :{max: 3000}
			});
	
	$("#elevation").bind("plotclick", function (event, pos, item) {
	    // axis coordinates for other axes, if present, are in pos.x2, pos.x3, ...
	    // if you need global screen coordinates, they are pos.pageX, pos.pageY

	    if (item) {
	    	myPlot.unhighlight();
	    	myPlot.highlight(item.series, item.datapoint);
	    	index_hightlight_point= item.dataIndex;
	    	var dataArray = item.series.data;
	        lastPos= [dataArray[item.dataIndex][2], dataArray[item.dataIndex][3],FLIGHTHEIGHT];
	        global.drawRoute2(lastPos);
	        cc.jumpTo(dataArray[item.dataIndex][2], dataArray[item.dataIndex][3],dataArray[item.dataIndex][0]);
	    }
	});
	drawRightBottomLabel();
}

function redraw(){
	myPlot.draw();
	drawRightBottomLabel();
}

function drawRightBottomLabel(){
	
	var canvas = myPlot.getCanvas();
	var ctx = canvas.getContext("2d");
	ctx.globalAlpha=1;
	ctx.globalCompositeOperation="source-over";
	ctx.fillStyle="#ffffff";
	ctx.fillRect(canvas.width-40, canvas.height-16,40,16);
	ctx.fillStyle="#000000";
	//ctx.fillText(Math.round(distance/1000,1)+'km', canvas.width-32, canvas.height-4);
}

//页面控制调度
function ControlCenter(){
	var me = this;
	
	
}

//跳转航线点  俯视图 ，高程图 -> 三维
ControlCenter.prototype.jumpTo = function (lat ,lon , _distance ){
	if(_distance){
		flight_distance=_distance;
	}
	
	if(plane){
		if(plane.isMove()){
			stopPlane();
			plane.teleportToRoutePoint(lat,lon);
			startPlane();
		}else{
			plane.teleportToRoutePoint(lat,lon);
		}
	}
};
var index_hightlight_point = 0;
ControlCenter.prototype.planeMove = function(){
	showCurrent(plane.model.getLocation().getLongitude(), plane.model.getLocation().getLatitude());
	//global.addRoutePoint([plane.model.getLocation().getLatitude(),plane.model.getLocation().getLongitude(), plane.model.getLocation().getAltitude()]);
	var i=index_hightlight_point ;
	for(;i<flightDataArray.length;i++){
		if(flight_distance < flightDataArray[i][0]){
			myPlot.unhighlight();
			//myPlot.highlight(1, i-1);
			var canvas = myPlot.getCanvas();
			var ctx = canvas.getContext("2d");
			
			var img = new Image();
			img.src = basePath +'earthview/resource/plane_icon.png';
			
			var pos = myPlot.pointOffset({ x: flightDataArray[i][0], y: flightDataArray[i][1] });
			redraw();
			ctx.drawImage(img,pos.left-8,pos.top-8);
			index_hightlight_point=i;
			break;
		}
	}
	myPlot.unhighlight();
	myPlot.highlight(2, currentIndex);
};

ControlCenter.prototype.targetChange = function(_i){
	global.removeTargets();
	global.drawTarget();
	myPlot.unhighlight();
	myPlot.highlight(2, _i);
};

ControlCenter.prototype.planeStart = function(){
	lastPos=null;
	for (i=0 ;i<linePlacemark.length ;i++){
		global.ge.getFeatures().removeChild(linePlacemark[i]);
	}
	
};
