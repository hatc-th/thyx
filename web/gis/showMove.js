/// <reference path="./jsapi_vsdoc10_v34.js" />

//�ڶ�ά��ͼ����ʾ��άģ��켣 

//������
function startTyphoon(points) {
	//clear();
	_parseTyphoon(points);
	Typhoon.current.moveTally = 0;
	//startMove();
}

//�����ʾ�Ĺ켣����
function gisClear() {
	map.graphics.clear();
	typhoonPathLayer.clear();
	Typhoon.current = {};
}

// ������
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

// ��ʾ�켣�㣬x--���ȣ�����104.736, y--γ�ȣ�����31.43458
function showCurrent(x, y) {
	map.graphics.remove(thegraphic);
	addPointToMap(new esri.geometry.Point(x, y));
}



