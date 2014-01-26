//global.js //地球环境
//using dojo 
//using google earth api

google.load("earth", "1", {'other_params' : 'sensor=false'});
google.load('maps', '2.x', {'other_params' : 'sensor=false'});

window.ge =null;
window.global = null;

function runGlobal(){
	if(window.global){
		window.global.hide();
		window.ge =null;
		window.global = null;
	}else{
		window.global= new Global();
		window.global.show();
		window.global.init();
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

Global.prototype.drawTarget = function() {
	var me = this;
	me.removeTargets();
	// Create the placemark.
	var placemark = me.ge.createPlacemark('');
	placemark.setName("目标点");
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

Global.prototype.addPanel = function() {
	var me = this;

	with (me) {
		// Create the panelMask.
		me.panelMask = me.ge.createpanelMask('');

		// Specify a path to the image and set as the icon.
		var icon = me.ge.createIcon('');
		icon
				.setHref('http://127.0.0.1:8080/EarthView/resource/icon_cessna172.png');
		me.panelMask.setIcon(icon);

		// Important note: due to a bug in the API, screenXY and overlayXY
		// have opposite meanings than their KML counterparts. This means
		// that in the API, screenXY defines the point on the overlay image
		// that is mapped to a point on the screen, defined by overlayXY.

		// This bug will not be fixed until the next major revision of the
		// Earth API, so that applications built upon version 1.x will
		// not break.

		// Set the panelMask's position in the window.
		panelMask.getOverlayXY().setXUnits(ge.UNITS_PIXELS);
		panelMask.getOverlayXY().setYUnits(ge.UNITS_PIXELS);
		panelMask.getOverlayXY().setX(512);
		panelMask.getOverlayXY().setY(288);

		// Specify the point in the image around which to rotate.
		panelMask.getRotationXY().setXUnits(ge.UNITS_FRACTION);
		panelMask.getRotationXY().setYUnits(ge.UNITS_FRACTION);
		panelMask.getRotationXY().setX(0.5);
		panelMask.getRotationXY().setY(0.5);

		// Set the overlay's size in pixels.
		panelMask.getSize().setXUnits(ge.UNITS_PIXELS);
		panelMask.getSize().setYUnits(ge.UNITS_PIXELS);
		panelMask.getSize().setX(1536);
		panelMask.getSize().setY(864);

		// Rotate the overlay.
		panelMask.setRotation(0);

		// Add the panelMask to Earth.
		ge.getFeatures().appendChild(panelMask);
	}
}

Global.prototype.removePanel = function() {
	var me = this;
	me.ge.getFeatures().removeChild(me.panelMask);
};

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
	me.addPanel();
	var lo = me.model.getLocation();
	var la = me.ge.createLookAt('');
	la.set(lo.getLatitude(), lo.getLongitude(),
		 lo.getAaltitude() /* altitude */,
		 me.ALTITUDE_RELATIVE_TO_GROUND,
		 me.model.getOrientation().getHeading() ,
         90, /* tilt */
         0 /* range */
         );
	me.ge.getView().setAbstractView(la);
};
//机尾视角
Global.prototype.cameraBack=function(){
	;
};
//左侧视角
Global.prototype.cameraLeft=function(){
	
};
//右侧视角
Global.prototype.cameraBack=function(){
	
};