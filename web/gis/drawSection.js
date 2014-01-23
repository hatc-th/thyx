//dojo.require("esri.map");
//dojo.require("esri.toolbars.draw");
dojo.require("dijit.layout.BorderContainer");
dojo.require("dijit.layout.ContentPane");
dojo.require("dojox.charting.widget.Chart2D");
dojo.require("dojox.charting.themes.Claro");
//var map, toolbar, symbol;
//var imageservicelayer;
//var pointcountexp, pointcountact;
//var anchorgeometry, thegraphic;
//var op = "", theCon;
var thegraphic;
var samplegeometryType = "esriGeometryPolyline";

var layerUrl = "http://192.168.1.20:6080/arcgis/rest/services/hatc/GC02/ImageServer";

/*对外接口,根据点集合画剖面图.
 *参数points: 剖面图的线所使用的点集合
 *参数planHeight：计划中的飞行高度.
 *参数drawChart：0-不画刨面图，仅高度报警；else-画刨面图并高度报警
 */
function gis_drawSection(points, planHeight, drawChart) {
	//rz-----------------
//	var pnts = points;
//	var s = "sdfsdfs".toString();
//	while (pnts.toString().search("[") != -1) {
//        pnts = pnts.toString().replace("[", "");
//        }
//	while (pnts.toString().search("]") != -1) {
//        pnts = pnts.toString().replace("]", "");
//        }
//	alert(pnts);
	//rz-----------------
//	ad = [];
//	ad[0]='23';
//	ad[3]='12';
//	alert(ad.length);
//	alert(ad[1]==undefined);
	var myJsonStr = '{"points":' + points + '}';
	mpJson = JSON.parse(myJsonStr);
	var multipoint = new esri.geometry.Multipoint(mpJson);
	polyline = new esri.geometry.Polyline(map.spatialReference);
	polyline.addPath(multipoint.points);
	doGetSamples(polyline, planHeight, drawChart);
}

function addPointToMap(geometry) {
	//toolbar.deactivate();
	//map.showZoomSlider();
	switch (geometry.type) {
		case "point":
			//samplegeometryType = "esriGeometryPoint";
			var symbol = new esri.symbol.SimpleMarkerSymbol(esri.symbol.SimpleMarkerSymbol.STYLE_CIRCLE, 10, new esri.symbol.SimpleLineSymbol(esri.symbol.SimpleLineSymbol.STYLE_SOLID, new dojo.Color([0, 0, 255]), 1), new dojo.Color([0, 255, 0, 1]));
			break;
		case "polyline":
			samplegeometryType = "esriGeometryPolyline";
			var symbol = new esri.symbol.SimpleLineSymbol(esri.symbol.SimpleLineSymbol.STYLE_SOLID, new dojo.Color([255, 0, 0]), 5);
			break;
		case "polygon":
			samplegeometryType = "esriGeometryPolygon";
			var symbol = new esri.symbol.SimpleFillSymbol(esri.symbol.SimpleFillSymbol.STYLE_SOLID, new esri.symbol.SimpleLineSymbol(esri.symbol.SimpleLineSymbol.STYLE_DASHDOT, new dojo.Color([255, 0, 0]), 2), new dojo.Color([255, 255, 0, 0.25]));
			break;
		case "extent":
			samplegeometryType = "esriGeometryEnvelope";
			var symbol = new esri.symbol.SimpleFillSymbol(esri.symbol.SimpleFillSymbol.STYLE_SOLID, new esri.symbol.SimpleLineSymbol(esri.symbol.SimpleLineSymbol.STYLE_DASHDOT, new dojo.Color([255, 0, 0]), 2), new dojo.Color([255, 255, 0, 0.25]));
			break;
		case "multipoint":
			samplegeometryType = "esriGeometryMultipoint";
			var symbol = new esri.symbol.SimpleMarkerSymbol(esri.symbol.SimpleMarkerSymbol.STYLE_DIAMOND, 20, new esri.symbol.SimpleLineSymbol(esri.symbol.SimpleLineSymbol.STYLE_SOLID, new dojo.Color([0, 0, 0]), 1), new dojo.Color([255, 255, 0, 0.5]));
			break;
		default:
			var symbol = new esri.symbol.SimpleMarkerSymbol(esri.symbol.SimpleMarkerSymbol.STYLE_CIRCLE, 30, new esri.symbol.SimpleLineSymbol(esri.symbol.SimpleLineSymbol.STYLE_SOLID, new dojo.Color([0, 0, 255]), 1), new dojo.Color([0, 255, 0, 1]));
			break;
	}
	var graphic = new esri.Graphic(geometry, symbol);
	if (geometry.type == null || geometry.type == "point") {
		thegraphic = graphic;
	}
	map.graphics.add(graphic);
/*
	if (geometry.type == "polyline" || geometry.type == "multipoint") {
		anchorgeometry = geometry;
		doGetSamples(anchorgeometry);
	}
*/
}

function doGetSamples(pline, planHeight, drawChart) {
	if (planHeight == null) planHeight = 0;
//	if(!(drawChart == 0)) {
//		dojo.empty("chartNode");
//		map.infoWindow.hide();
//	}
	var callbackfunc = {
		chartit : function(response) {
			if (response.samples && response.samples.length > 0)
				showChart(response.samples);
		}
	};
	var errorbackfunc = {
		chartit : function(error) {
			console.log(error.message);
		}
	};
	var layersRequest = esri.request({
		url : layerUrl + "/getSamples",
		content : {
			geometry : JSON.stringify(pline.toJson())
			,geometryType : samplegeometryType
			,sampleCount : 100
			//,returnFirstValueOnly : true
			,f : "json"
		},
		handleAs : "json",
		callbackParamName : "callback"
	});
	layersRequest.then(dojo.hitch(callbackfunc, "chartit"), dojo.hitch(errorbackfunc, "chartit"));

	function showChart(samples) {
		var chartData1 = [], chartData2 = [];
		chartX = [];
		chartPoints = [];
		minpixelvalue = 9999;
		maxpixelvalue = -9999;
		var tempval = 0;
		var tempdist = 0, maxK;
		//rz---------------
		var tempdis1 = 0;
		var tempdis2 = 0;
		var sinvar = 0;
		var cosvar = 0;
		var hdvar = 0;
		var dx =0;
		var dy =0;
		var temppoints = '';
		var temppoints2 = '';
		var d1 = 0;
		var d2 = 0;

		for (var k = 0; k < samples.length; k++) {
			if(samples.length > 1 ){
                
                if(k ==0){
                	d1 = (new Date().getTime());
//                	tempdis1 = getDistance(samples[0].location.x, samples[0].location.y, samples[1].location.x, samples[0].location.y)/1000;//x
//				    tempdis2 = getDistance(samples[1].location.x, samples[1].location.y, samples[1].location.x, samples[0].location.y)/1000;//y
                	tempdis1 = getDistance(samples[0].location.y,samples[0].location.x, samples[0].location.y,samples[1].location.x)/1000;//x
				    tempdis2 = getDistance(samples[1].location.y,samples[1].location.x, samples[0].location.y,samples[1].location.x)/1000;//y
				   
					hdvar = Math.atan2(tempdis2,tempdis1);//获取弧度point(x,y) atan2(y,x)
					if((samples[1].location.y > samples[0].location.y) && (samples[1].location.x < samples[0].location.x)){
				    	hdvar = Math.PI - hdvar
				    }
				    if((samples[1].location.y < samples[0].location.y) && (samples[1].location.x > samples[0].location.x)){
				    	hdvar = Math.PI - hdvar
				    }
					sinvar = Math.sin(hdvar);//
					cosvar = Math.cos(hdvar);//
                }else{
//                	tempdis1 = getDistance(samples[k-1].location.x, samples[k-1].location.y, samples[k].location.x, samples[k-1].location.y)/1000;//x
//				    tempdis2 = getDistance(samples[k].location.x, samples[k].location.y, samples[k].location.x, samples[k-1].location.y)/1000;//y
				    tempdis1 = getDistance(samples[k-1].location.y, samples[k-1].location.x, samples[k-1].location.y, samples[k].location.x)/1000;//x
				    tempdis2 = getDistance(samples[k].location.y, samples[k].location.x, samples[k-1].location.y, samples[k].location.x)/1000;//y

					hdvar = Math.atan2(tempdis2,tempdis1);//获取弧度point(x,y) atan2(y,x)
					if((samples[k].location.y > samples[k-1].location.y) && (samples[k].location.x < samples[k-1].location.x)){
				    	hdvar = Math.PI - hdvar
				    }
				    if((samples[k].location.y < samples[k-1].location.y) && (samples[k].location.x > samples[k-1].location.x)){
				    	hdvar = Math.PI - hdvar
				    }
					sinvar = Math.sin(hdvar);//
					cosvar = Math.cos(hdvar);//
                }
				
				//samples[k]
				var pt = new esri.geometry.Point(samples[k].location.x, samples[k].location.y, map.spatialReference);
				
				dx = sinvar*10;//经度方向偏移量（单位千米）
				dy = cosvar*10;//纬度方向偏移量（单位千米）
				var hd = samples[k].location.y*Math.PI/180.0;//角度转换为弧度
				dx = dx/111.31955*(Math.cos(hd));//经度偏移值
				dy = dy/111.31955;//纬度偏移值
				var pt1 = pt.offset(-dx,dy);
				var pt2 = pt.offset(dx,-dy);
				//var myJsonStr = '{"points":' + points + '}';
				temppoints = '[['+pt1.x+','+pt1.y+'],['+pt2.x+','+pt2.y+']]';
				temppoints2 += temppoints + ';';
				gis_drawSection2(temppoints,planHeight,samples,k,drawChart);
			
				
			}
		}
		console.log('****');console.log(d1);
        d2 = (new Date().getTime());
        console.log(d2);
        console.log('haoshi='+(d2-d1));
	}

}

