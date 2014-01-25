//global.js //地球环境

google.load("earth", "1", {'other_params': 'sensor=false' });
google.load('maps', '2.x', {'other_params': 'sensor=false' });

var ge = null;
var geocoder;
var truck;


function toggle3dDiv(){
	if(ge != null){
		dojo.place(dojo.byId('map3dControl'),dojo.byId('globalDiv'),'after');
		dojo.destroy("map3d_container");
		ge = null;
		truck = null;
		return;
	}
	
	if(!dojo.byId('map3d_container')){
		dojo.create("div", {  
	        id: "map3d_container",
	        style: { 
	        	position : "absolute" ,
	            width: dojo.byId('fPlanContextText').style.width,
	            height: dojo.byId('fPlanContext').style.height ,
	            margin: "0 auto"
	           
	        }  
	    }, dojo.byId('fPlanContext')); 
	}
	if(!dojo.byId('map3d')){
		dojo.create("div", {  
			id: "map3d",
	        style: {  
	            height: "80%" ,
	            width:  "100%"
	        }  
	    }, dojo.byId('map3d_container')); 
		
		/*dojo.create("div", {  
			innerHTML : "这里是控制板" ,
			id: "map3dControl",
	        style: {  
	            height: "20%" ,
	            width:  "100%"
	        }  
	    }, dojo.byId('map3d_container'));*/
		
		dojo.place(dojo.byId('map3dControl'),dojo.byId('map3d'),'after');
		dojo.setStyle(dojo.byId("map3dControl"), "display" ,"block");
		dojo.setStyle(dojo.byId("map3dControl"), "z-index" ,"999");
		dojo.setStyle(dojo.byId("map3dControl"), "background-color" ,"#374068");
	}
        init3D();
}

function el(e) { return document.getElementById(e); }

function Sample(description, url) {
  this.description = description;
  this.url = url;
  return this;
}

var samples = [];

function initCallback(object) {
  ge = object;
  ge.getWindow().setVisibility(true);
  // ge.getOptions().setMouseNavigationEnabled(false);
  ge.getLayerRoot().enableLayerById(ge.LAYER_BUILDINGS, true); ///建筑
  ge.getLayerRoot().enableLayerById(ge.LAYER_BORDERS, true);	//边界
  
  ge.getOptions().setFlyToSpeed(ge.SPEED_TELEPORT);

  truck = new Truck();
  //addPanel();
}

function failureCallback(err) {
}

function init3D() {
	geocoder = new GClientGeocoder();
	google.earth.createInstance("map3d", initCallback, failureCallback);
}

function submitLocation() {
  doGeocode(el('address').value);
}

function doGeocode(address) {
  geocoder.getLatLng(address, function(point) {
    if (point) {
      if (ge != null && truck != null) {
        truck.teleportTo(point.y, point.x);
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

//航线
var multGeoPlacemark ;

function drawLine(){
	if(multGeoPlacemark){
		ge.getFeatures().removeChild(multGeoPlacemark);
	}
	
	// Create the LineString
	var lineString = ge.createLineString('');
	// Add LineString points
	for(i=0;i<linePoints.length;i++){
		//alert(V3.toString(linePoints[i]));
		lineString.getCoordinates().pushLatLngAlt(linePoints[i][0],linePoints[i][1],i==0?ge.getGlobe().getGroundAltitude(linePoints[i][0],linePoints[i][1]):FLIGHTHEIGHT);
	}
	lineString.setTessellate(true);
	lineString.setAltitudeMode(ge.ALTITUDE_ABSOLUTE);
	
	var multiGeometry = ge.createMultiGeometry('');
	multiGeometry.getGeometries().appendChild(lineString);

	multGeoPlacemark = ge.createPlacemark('');
	multGeoPlacemark.setGeometry(multiGeometry);

	multGeoPlacemark.setStyleSelector(ge.createStyle(''));
	var lineStyle = multGeoPlacemark.getStyleSelector().getLineStyle();
	lineStyle.setWidth(2);
	lineStyle.getColor().set('ffff0000');
	
	ge.getFeatures().appendChild(multGeoPlacemark);

	multGeoPlacemark.setName('航线');
}

//路径点
var targetPlaceMarks = [];

function drawTarget(){
	removeTargets();
	//Create the placemark.
	var placemark = ge.createPlacemark('');
	placemark.setName("目标点");
	// Set the placemark's location.  
	var point = ge.createPoint('');
	point.setLatitude(currentTarget[0]);
	point.setLongitude(currentTarget[1]);
	point.setAltitude(2000);
	placemark.setGeometry(point);

	targetPlaceMarks[targetPlaceMarks.length] = placemark ;
	// Add the placemark to Earth.
	ge.getFeatures().appendChild(placemark);
}

function removeTargets(){
	if(targetPlaceMarks.length==0)return;	
	for (var  i=0 ;i< targetPlaceMarks.length ; i++){
		ge.getFeatures().removeChild(targetPlaceMarks[i]);
	}
}

//舱内视角的舱内面板
var screenOverlay;

function addPanel(){
	// Create the ScreenOverlay.
    screenOverlay = ge.createScreenOverlay('');

    // Specify a path to the image and set as the icon.
    var icon = ge.createIcon('');
    icon.setHref('http://127.0.0.1:8080/EarthView/resource/icon_cessna172.png');
    screenOverlay.setIcon(icon);

    // Important note: due to a bug in the API, screenXY and overlayXY
    // have opposite meanings than their KML counterparts. This means
    // that in the API, screenXY defines the point on the overlay image
    // that is mapped to a point on the screen, defined by overlayXY.

    // This bug will not be fixed until the next major revision of the
    // Earth API, so that applications built upon version 1.x will
    // not break.

		// Set the ScreenOverlay's position in the window.
    screenOverlay.getOverlayXY().setXUnits(ge.UNITS_PIXELS);
    screenOverlay.getOverlayXY().setYUnits(ge.UNITS_PIXELS);
    screenOverlay.getOverlayXY().setX(512);
    screenOverlay.getOverlayXY().setY(288);

    // Specify the point in the image around which to rotate.
    screenOverlay.getRotationXY().setXUnits(ge.UNITS_FRACTION);
    screenOverlay.getRotationXY().setYUnits(ge.UNITS_FRACTION);
    screenOverlay.getRotationXY().setX(0.5);
    screenOverlay.getRotationXY().setY(0.5);

	// Set the overlay's size in pixels.
    screenOverlay.getSize().setXUnits(ge.UNITS_PIXELS);
    screenOverlay.getSize().setYUnits(ge.UNITS_PIXELS);
    screenOverlay.getSize().setX(1536);
    screenOverlay.getSize().setY(864);

    // Rotate the overlay.
    screenOverlay.setRotation(0);

    // Add the ScreenOverlay to Earth.
    ge.getFeatures().appendChild(screenOverlay);

}

function removePanel(){
	ge.getFeatures().removeChild(screenOverlay);
}