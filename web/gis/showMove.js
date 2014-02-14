/// <reference path="./jsapi_vsdoc10_v34.js" />

//在二维地图中显示三维模拟轨迹 

//测试用
function startTyphoon(points) {
	//clear();
	_parseTyphoon(points);
	Typhoon.current.moveTally = 0;
	//startMove();
}

//清除显示的轨迹符号
function gisClear() {
	map.graphics.remove(thegraphic);
	/*map.graphics.clear();
	typhoonPathLayer.clear();
	Typhoon.current = {};*/
}

// 测试用
function gisMove() {
	//_convertPathList();
	Typhoon.current.moveRunning = true;
	//_moveCurrent();
	showCurrent(Typhoon.current.pathList[Typhoon.current.moveTally].x, Typhoon.current.pathList[Typhoon.current.moveTally].y);
	Typhoon.current.moveTally++;
	if (Typhoon.current.moveTally == (Typhoon.current.pathList.length)) {
		Typhoon.current.moveTally = 0;
	}
}

// 显示轨迹点，x--经度，例如104.736, y--纬度，例如31.43458
function showCurrent(x, y) {
	map.graphics.remove(thegraphic);
	addPointToMap(new esri.geometry.Point(x, y));
}