// milktruck.js  -- Copyright Google 2007

// Code for Monster Milktruck demo, using Earth Plugin.

window.plane = null;
window.global = null;

// Pull the Milktruck model from 3D Warehouse.
var MODEL_URL = 'http://127.0.0.1:8080/EarthView/resource/cessna162_0.kmz';
  //var MODEL_URL = 'http://www.barnabu.co.uk/geapi/flightsim/hawk.kmz';
var TICK_MS = 66;

var STEER_ROLL = -1.0;
var ROLL_SPRING = 0.25;
var ROLL_DAMP = -0.16;

var ACCEL =100; 

function Plane(_modelURL) {
  var me = this;

  me.doTick = false;
  
  // We do all our motion relative to a local coordinate frame that is
  // anchored not too far from us.  In this frame, the x axis points
  // east, the y axis points north, and the z axis points straight up
  // towards the sky.
  //
  // We periodically change the anchor point of this frame and
  // recompute the local coordinates.
  me.localAnchorLla = [0, 0, 0];
  me.localAnchorCartesian = V3.latLonAltToCartesian(me.localAnchorLla);
  me.localFrame = M33.identity();

  // Position, in local cartesian coords.
  me.pos = [0, 0, 0];
  
  // Velocity, in local cartesian coords.
  me.vel = [0, 0, 0];

  // Orientation matrix, transforming model-relative coords into local
  // coords.
  me.modelFrame = M33.identity();

  me.roll = 0;
  me.rollSpeed = 0;
  
  me.idleTimer = 0;
  me.fastTimer = 0;
  me.popupTimer = 0;

  ge.getOptions().setFlyToSpeed(100);  // don't filter camera motion

  window.google.earth.fetchKml(ge, _modelURL? _modelURL: MODEL_URL,
              function(obj) { me.finishInit(obj); });
  
}
	  
	  
Plane.prototype.finishInit = function(kml) {
  var me = this;

  // The model zip file is actually a kmz, containing a KmlFolder with
  // a camera KmlPlacemark (we don't care) and a model KmlPlacemark
  // (our milktruck).
  me.placemark = kml.getFeatures().getChildNodes().item(1);
  me.model = me.placemark.getGeometry();
  me.orientation = me.model.getOrientation();
  me.location = me.model.getLocation();

  me.model.setAltitudeMode(ge.ALTITUDE_ABSOLUTE);
  //me.orientation.setHeading(100);
  me.model.setOrientation(me.orientation);

  ge.getFeatures().appendChild(me.placemark);

  me.balloon = ge.createHtmlStringBalloon('');
  me.balloon.setFeature(me.placemark);
  me.balloon.setMaxWidth(200);

  //初始化位置
  prepareRoute();
  moveToStart();
  plane.adjustPosition();
  me.lastMillis = (new Date()).getTime();

  var href = window.location.href;
  var pagePath = href.substring(0, href.lastIndexOf('/')) + '/';

  google.earth.addEventListener(ge, "frameend", afterFrameEnd);
  
  ge.getWindow().blur();
  // If the user clicks on the Earth window, try to restore keyboard
  // focus back to the page.
  /*google.earth.addEventListener(ge.getWindow(), "mouseup", function(event) {
      ge.getWindow().blur();
  	});
  */
}

leftButtonDown = false;
rightButtonDown = false;
gasButtonDown = false;
reverseButtonDown = false;

function keyDown(event) {
  if (!event) {
    event = window.event;
  }
  if (event.keyCode == 37) {  // Left.
    leftButtonDown = true;
    event.returnValue = false;
  } else if (event.keyCode == 39) {  // Right.
    rightButtonDown = true;
    event.returnValue = false;
  } else if (event.keyCode == 38) {  // Up.
    gasButtonDown = true;
    event.returnValue = false;
  } else if (event.keyCode == 40) {  // Down.
    reverseButtonDown = true;
    event.returnValue = false;
  } else {
    return true;
  }
  return false;
}

function keyUp(event) {
  if (!event) {
    event = window.event;
  }
  if (event.keyCode == 37) {  // Left.
    leftButtonDown = false;
    event.returnValue = false;
  } else if (event.keyCode == 39) {  // Right.
    rightButtonDown = false;
    event.returnValue = false;
  } else if (event.keyCode == 38) {  // Up.
    gasButtonDown = false;
    event.returnValue = false;
  } else if (event.keyCode == 40) {  // Down.
    reverseButtonDown = false;
    event.returnValue = false;
  }
  return false;
}

function clamp(val, min, max) {
  if (val < min) {
    return min;
  } else if (val > max) {
    return max;
  }
  return val;
}

Plane.prototype.tick = function() {
  var me = this;
 
  var now = (new Date()).getTime();
  // dt is the delta-time since last tick, in seconds
  var dt = (now - me.lastMillis) / 1000.0;
  if (dt > 0.25) {
    dt = 0.25;
  }
  me.lastMillis = now;
  message("ticking..."+me.lastMillis);
  var c0 = 1;
  var c1 = 0;

  var gpos = V3.add(me.localAnchorCartesian,
                    M33.transform(me.localFrame, me.pos));
  var lla = V3.cartesianToLatLonAlt(gpos);

  if (V3.length([me.pos[0], me.pos[1], 0]) > 100) {
    // Re-anchor our local coordinate frame whenever we've strayed a
    // bit away from it.  This is necessary because the earth is not
    // flat!
    me.adjustAnchor();
  }

  var dir = me.modelFrame[1];
  var up = me.modelFrame[2];

  var absSpeed = V3.length(me.vel);

  var groundAlt = ge.getGlobe().getGroundAltitude(lla[0], lla[1]);
  var airborne = false;
  var steerAngle = 0;
  //辅助转向
  var curPos = [plane.model.getLocation().getLatitude(),  plane.model.getLocation().getLongitude() , 0 ];
  currentHeading=getAngle( curPos, currentTarget); 
  steerAngle = (fixAngle(me.model.getOrientation().getHeading() - currentHeading) ) * Math.PI / 180.0;
  //steerAngle = (getAngle( lastTarget, currentTarget) - currentHeading ) * Math.PI / 180.0;
  //message('');
  
  // Steering.
  if (steerAngle!=0) {
    var TURN_SPEED_MIN = 40.0;  // radians/sec
    var TURN_SPEED_MAX = 60.0;  // radians/sec
 
    var turnSpeed;
    
    // Degrade turning at higher speeds.
    //
    //           angular turn speed vs. vehicle speed
    //    |     -------
    //    |    /       \-------
    //    |   /                 \-------
    //    |--/                           \---------------
    //    |
    //    +-----+-------------------------+-------------- speed
    //    0    SPEED_MAX_TURN           SPEED_MIN_TURN
    var SPEED_MAX_TURN = 200.0;
    var SPEED_MIN_TURN = 450.0;
    if (absSpeed < SPEED_MAX_TURN) {
      turnSpeed = TURN_SPEED_MIN + (TURN_SPEED_MAX - TURN_SPEED_MIN)
                   * (SPEED_MAX_TURN - absSpeed) / SPEED_MAX_TURN;
      turnSpeed *= (absSpeed / SPEED_MAX_TURN);  // Less turn as plane slows
    } else {
      turnSpeed = TURN_SPEED_MAX;
    }
    /*if (leftButtonDown) {   //正值向左 
        steerAngle = turnSpeed/2 * dt * Math.PI / 180.0;
      }
      if (rightButtonDown) {   //负值向右
        steerAngle = -turnSpeed/2 * dt * Math.PI / 180.0;
      }*/
    
    //判断转向方向  主要针对当前朝向和目标朝向在y轴两边的情况
    var direction = 0; // 1向左  -1 向右
    if(Math.abs(steerAngle)>turnSpeed/2 * dt * Math.PI / 180.0){
    	if(steerAngle>0){
    		direction= 1;
    		steerAngle = turnSpeed/2 * dt * Math.PI / 180.0;
    	}else if(steerAngle<0 || steerAngle ){
    		direction= -1;
    		steerAngle = -turnSpeed/2 * dt * Math.PI / 180.0;
    	}
    }
  }
  
  // Turn.
  var newdir = airborne ? dir : V3.rotate(dir, up, steerAngle);
  me.modelFrame = M33.makeOrthonormalFrame(newdir, up);
  dir = me.modelFrame[1];
  up = me.modelFrame[2];

  var forwardSpeed = 0;
  
  if (!airborne) {
    // TODO: if we're slipping, transfer some of the slip
    // velocity into forward velocity.

    // Damp sideways slip.  Ad-hoc frictiony hack.
    //
    // I'm using a damped exponential filter here, like:
    // val = val * c0 + val_new * (1 - c0)
    //
    // For a variable time step:
    //  c0 = exp(-dt / TIME_CONSTANT)
    var right = me.modelFrame[0];
    var slip = V3.dot(me.vel, right);
    c0 = Math.exp(-dt / 0.5);
    me.vel = V3.sub(me.vel, V3.scale(right, slip * (1 - c0)));

    // Apply engine/reverse accelerations.
    //var ACCEL = 280.0;
    var DECEL = 80.0;
    var MAX_REVERSE_SPEED = 100.0;
    forwardSpeed = V3.dot(dir, me.vel);
    //gasButtonDown = true;   //载入后静止 ，手动开始飞行  go()
    if (gasButtonDown) {
      // Accelerate forwards.
      me.checkPoints();
      me.vel = V3.add(me.vel, V3.scale(dir, ACCEL * dt));
    } else if (reverseButtonDown) {
      if (forwardSpeed > -MAX_REVERSE_SPEED)
        me.vel = V3.add(me.vel, V3.scale(dir, -DECEL * dt));
    }
  }

  // Air drag.
  //
  // Fd = 1/2 * rho * v^2 * Cd * A.
  // rho ~= 1.2 (typical conditions)
  // Cd * A = 3 m^2 ("drag area")
  //
  // I'm simplifying to:
  //
  // accel due to drag = 1/Mass * Fd
  // with Milktruck mass ~= 2000 kg
  // so:
  // accel = 0.6 / 2000 * 3 * v^2
  // accel = 0.0009 * v^2
  absSpeed = V3.length(me.vel);
  if (absSpeed > 0.01) {
    var veldir = V3.normalize(me.vel);
    var DRAG_FACTOR =   0.00050;
    var drag = absSpeed * absSpeed * DRAG_FACTOR;

    // Some extra constant drag (rolling resistance etc) to make sure
    // we eventually come to a stop.
    var CONSTANT_DRAG = 2.0;
    drag += CONSTANT_DRAG;

    if (drag > absSpeed) {
      drag = absSpeed;
    }
    
    me.vel = V3.sub(me.vel, V3.scale(veldir, drag * dt));
  }
  
  groundAlt = ge.getGlobe().getGroundAltitude(lla[0], lla[1]);
  //控制爬升和下降的加速度
  // Gravity
  if(gasButtonDown){
	  if(me.pos[2] < FLIGHTHEIGHT){
		  me.vel[2] += 2 * 9.8 * dt;
	  }else{
		  me.vel[2] -= 9.8 * dt;
	  }
  }
  else{
	  me.vel[2] -= 9.8 * dt;
  }
  

  // Move.
  var deltaPos = V3.scale(me.vel, dt);
  me.pos = V3.add(me.pos, deltaPos);
  
  el('vel').value=Math.round(forwardSpeed * 3.6);
  el('height').value=Math.round(me.pos[2]/10)*10;
  
  gpos = V3.add(me.localAnchorCartesian,
                M33.transform(me.localFrame, me.pos));
  lla = V3.cartesianToLatLonAlt(gpos);
  
  // Don't go underground.
  
  if (me.pos[2] < groundAlt) {
    me.pos[2] = groundAlt;
  }

  var normal = estimateGroundNormal(gpos, me.localFrame);
  
  if (airborne) {
    // Cancel velocity into the ground.
    //
    // TODO: would be fun to add a springy suspension here so
    // the plane bobs & bounces a little.
    var speedOutOfGround = V3.dot(normal, me.vel);
    if (speedOutOfGround < 0) {
      me.vel = V3.add(me.vel, V3.scale(normal, -speedOutOfGround));
    }

    // Make our orientation follow the ground.
    c0 = Math.exp(-dt / 0.25);
    c1 = 1 - c0;
    var blendedUp = V3.normalize(V3.add(V3.scale(up, c0),
                                        V3.scale(normal, c1)));
    me.modelFrame = M33.makeOrthonormalFrame(dir, blendedUp);
    
  }
  
  // Propagate our state into Earth.
  gpos = V3.add(me.localAnchorCartesian,
                M33.transform(me.localFrame, me.pos));
  lla = V3.cartesianToLatLonAlt(gpos);
  me.model.getLocation().setLatLngAlt(lla[0], lla[1], lla[2]+HEIGHT);

  var newhtr = M33.localOrientationMatrixToHeadingTiltRoll(me.modelFrame);

  // Compute roll according to steering.
  // TODO: this would be even more cool in 3d.
  var absRoll = newhtr[2];
  me.rollSpeed += steerAngle * forwardSpeed * 4* STEER_ROLL;
  // Spring back to center, with damping.
  me.rollSpeed += (ROLL_SPRING * -me.roll + ROLL_DAMP * me.rollSpeed);
  me.roll += me.rollSpeed * dt;
  me.roll = clamp(me.roll, -85, 85);
  absRoll -= me.roll;

  me.orientation.set(newhtr[0], newhtr[1], absRoll);
  
  var imgR = 0 //舱内图片旋转角度
  
  imgR = absRoll/4 ;
  if(imgR>20){
	  imgR= 20;
  }else if(imgR<-20){
	  imgR = -20
  }
  
  global.rotate(imgR);
  
  //me.tickPopups(dt);
  me.cameraFollow(dt, gpos, me.localFrame);

  // Hack to work around focus bug
  // TODO: fix that bug and remove this hack.
  //ge.getWindow().blur();
};

// TODO: would be nice to have globe.getGroundNormal() in the API.
function estimateGroundNormal(pos, frame) {
  // Take four height samples around the given position, and use it to
  // estimate the ground normal at that position.
  //  (North)
  //     0
  //     *
  //  2* + *3
  //     *
  //     1
  var pos0 = V3.add(pos, frame[0]);
  var pos1 = V3.sub(pos, frame[0]);
  var pos2 = V3.add(pos, frame[1]);
  var pos3 = V3.sub(pos, frame[1]);
  var globe = ge.getGlobe();
  function getAlt(p) {
    var lla = V3.cartesianToLatLonAlt(p);
    return globe.getGroundAltitude(lla[0], lla[1]);
  }
  var dx = getAlt(pos1) - getAlt(pos0);
  var dy = getAlt(pos3) - getAlt(pos2);
  var normal = V3.normalize([dx, dy, 2]);
  return normal;
}


Plane.prototype.scheduleTick = function() {
  var me = this;
  if (me.doTick) {
    setTimeout(function() { me.tick(); }, TICK_MS);
  }
};

// Cut the camera to look at me.
Plane.prototype.cameraCut = function() {
  var me = this;
  var lo = me.model.getLocation();
  var la = ge.createLookAt('');
  la.set(lo.getLatitude(), lo.getLongitude(),
		  HEIGHT /* altitude */,
         ge.ALTITUDE_RELATIVE_TO_GROUND,
         //fixAngle(180 + me.model.getOrientation().getHeading() + 45),
         me.model.getOrientation().getHeading() ,
         80, /* tilt */
         50 /* range */
         );
  ge.getView().setAbstractView(la);
};


Plane.prototype.cameraFollow = function(dt, truckPos, localToGlobalFrame) {
  var me = this;

  var c0 = Math.exp(-dt / 0.5);
  var c1 = 1 - c0;

  var la = ge.getView().copyAsLookAt(ge.ALTITUDE_RELATIVE_TO_GROUND);

  var truckHeading = me.model.getOrientation().getHeading();
  var camHeading = la.getHeading();

  var deltaHeading = fixAngle(truckHeading - camHeading);
  var heading = camHeading + c1 * deltaHeading;
  heading = fixAngle(heading);
  //message("目标角:"+me.model.getOrientation().getHeading());
  var TRAILING_DISTANCE = 25;
  var headingRadians = heading / 180 * Math.PI;
  
  var CAM_HEIGHT = 12;

  var headingDir = V3.rotate(localToGlobalFrame[1], localToGlobalFrame[2],
                             -headingRadians);
  var camPos = V3.add(truckPos, V3.scale(localToGlobalFrame[2], CAM_HEIGHT));
  camPos = V3.add(camPos, V3.scale(headingDir, -TRAILING_DISTANCE));  //**
  var camLla = V3.cartesianToLatLonAlt(camPos);
  var camLat = camLla[0];
  var camLon = camLla[1];
  var camAlt = camLla[2] - ge.getGlobe().getGroundAltitude(camLat, camLon);
  
  la.set(camLat, camLon, camAlt+10 , ge.ALTITUDE_RELATIVE_TO_GROUND, 
        heading, 80 /*tilt*/, 50 /*range*/);
  ge.getView().setAbstractView(la);
};

var camHeadingOffset;
var 

// heading is optional.
Plane.prototype.teleportTo = function(lat, lon, heading) {
  var me = this;
  //添加事件使飞机的位置变化能及时显示，解决了由于在不同的地点取一个点的海拔高度不一致的问题
  google.earth.addEventListener(ge, "frameend", adjustPosHandler);
  me.model.getLocation().setLatitude(lat);
  me.model.getLocation().setLongitude(lon);
  me.tempAlt = HEIGHT + ge.getGlobe().getGroundAltitude(lat, lon);
  var alt = ge.getGlobe().getGroundAltitude(lat, lon);
  me.model.getLocation().setAltitude(HEIGHT + alt);
  
  if (heading == null) {
    heading = 0;
  }
  me.vel = [0, 0, 0];

  me.localAnchorLla = [lat, lon, 0];
  me.localAnchorCartesian = V3.latLonAltToCartesian(me.localAnchorLla);
  me.localFrame = M33.makeLocalToGlobalFrame(me.localAnchorLla);
  me.modelFrame = M33.identity();
  me.modelFrame[0] = V3.rotate(me.modelFrame[0], me.modelFrame[2], -heading);
  me.modelFrame[1] = V3.rotate(me.modelFrame[1], me.modelFrame[2], -heading);
  me.pos = [0, 0, ge.getGlobe().getGroundAltitude(lat, lon)];

  me.cameraCut();
};

// Move our anchor closer to our current position.  Retain our global
// motion state (position, orientation, velocity).
Plane.prototype.adjustAnchor = function() {
  var me = this;
  var oldLocalFrame = me.localFrame;

  var globalPos = V3.add(me.localAnchorCartesian,
                         M33.transform(oldLocalFrame, me.pos));
  var newAnchorLla = V3.cartesianToLatLonAlt(globalPos);
  newAnchorLla[2] = 0;  // For convenience, anchor always has 0 altitude.

  var newAnchorCartesian = V3.latLonAltToCartesian(newAnchorLla);
  var newLocalFrame = M33.makeLocalToGlobalFrame(newAnchorLla);

  var oldFrameToNewFrame = M33.transpose(newLocalFrame);
  oldFrameToNewFrame = M33.multiply(oldFrameToNewFrame, oldLocalFrame);

  var newVelocity = M33.transform(oldFrameToNewFrame, me.vel);
  var newModelFrame = M33.multiply(oldFrameToNewFrame, me.modelFrame);
  var newPosition = M33.transformByTranspose(
      newLocalFrame,
      V3.sub(globalPos, newAnchorCartesian));

  me.localAnchorLla = newAnchorLla;
  me.localAnchorCartesian = newAnchorCartesian;
  me.localFrame = newLocalFrame;
  me.modelFrame = newModelFrame;
  me.pos = newPosition;
  me.vel = newVelocity;
}

// Keep an angle in [-180,180]
function fixAngle(a) {
  while (a < -180) {
    a += 360;
  }
  while (a > 180) {
    a -= 360;
  }
  return a;
}

//---------------------------------extend for flight --------------------//

//求b相对于a的方位角  依赖 flyPlan.js 的 getDistance 方法
function getAngle2(a ,b ){
	var lenLo = getDistance(b[0],a[1],b[0],b[1] ); //经度
	var lenLa = getDistance(a[0],a[1],b[0],a[1] ); //纬度
	var dist =  getDistance(a[0],a[1],b[0],b[1] );
	var ret ;
	//alert (lenLo +"|"+  lenLa+"|"+dist);
	var r = Math.atan(lenLo/lenLa)  * 180 / Math.PI ;//Math.acos( (lenLa*lenLa + dist* dist - lenLa*lenLa) / (2*lenLa* dist)  ) * 180 / Math.PI ;
	//第一象限 
	if( b[1]  >  a[1] && b[0] > a[0]  ){
		ret = r;
	}//第四象限
	else if ( b[1]  >  a[1] && b[0] < a[0]  ){
		ret =  180 - r;
	}
	//第三象限
	else if ( b[1]  <  a[1] && b[0] < a[0]  ){
		ret =  r + 180;
	}
	//第二象限
	else if ( b[1] <  a[1] && b[0] > a[0]  ){
		ret =  360 -  r;
	}
	//message("目标角:"+fixAngle(ret));
	return fixAngle(ret);
 
}


function getAngle(a ,b ){
	
	return getAngle2(a ,b );
	var lenLo = Math.abs(b[1]-a[1]); //经度
	var lenLa = Math.abs(b[0]-a[0]); //纬度
	
	var ret ;
	
	var r = Math.atan(lenLo/lenLa)  * 180 / Math.PI ;
	//第一象限 
	if( b[1]  >  a[1] && b[0] > a[0]  ){
		ret = r;
	}//第四象限
	else if ( b[1]  >  a[1] && b[0] < a[0]  ){
		ret =  180 - r;
	}
	//第三象限
	else if ( b[1]  <  a[1] && b[0] < a[0]  ){
		ret =  r + 180;
	}
	//第二象限
	else if ( b[1] <  a[1] && b[0] > a[0]  ){
		ret =  360 -  r;
	}
	//message("目标角:"+fixAngle(ret));
	return fixAngle(ret);
	
}


Plane.prototype.checkPoints = function (){
	var me = this;
	var curPos = [me.model.getLocation().getLatitude(),  me.model.getLocation().getLongitude() , 0 ];
	
	if(currentTarget==null){
		currentIndex=1;
		lastTarget = chkPoints[0];
		currentTarget = chkPoints[1] ;
		global.drawTarget();
	//}else if( V3.earthDistance(curPos,currentTarget) < 200   ){
	}
	var dist= getDistance(curPos[0],curPos[1],currentTarget[0],currentTarget[1]);
	if( dist < 400   ){	
		currentIndex++;
		if(currentIndex==chkPoints.length ){
			if(dist<=10){
				stop();
				alert("到达目的地 !" );
				return;
			}
			currentIndex--;
		}
		lastTarget=currentTarget;
		currentTarget = chkPoints[currentIndex] ;
		global.drawTarget();
	}
	
	el('target').value=currentIndex;
	el('targetLa').value=currentTarget[0];
	el('targetLo').value=currentTarget[1];
	el('targetR').value=currentHeading;
	el('dis').value=parseInt(dist);
	
}


//----------------------THYX三维模拟控制-------------------------------
var linePoints = [[ 31.43458,104.736,0],[ 31.41,104.83,0],[ 31.40,104.92,0],[ 31.42,104.99,0],[ 31.333,104.9879,0],[ 31.25,104.978,0],
                  [ 31.186,104.85,0],[ 31.08,104.749,0],[ 31.045,104.68,0],[ 31.0256,104.598,0],[ 30.986,104.546,0],[ 30.97,104.415,0],
                  [ 30.95,104.33,0],[ 30.875,104.32,0],[ 30.816,104.376,0],[ 30.8653,104.3859,0],[ 30.87,104.3957,0]];//航线点 array
var chkPoints ; // 路径点 array
var currentTarget=null; // 当前目标点 
var lastTarget =null;
var currentIndex; //当前路径点索引

var startPos , endPos; //起止点

var currentHeading=0;

var FLIGHTHEIGHT=2000;
var HEIGHT=0;
// 等分航线点
function getLinePoints( a ,b, n ){
	if(linePoints){
		startPos=linePoints[0];
		endPos = linePoints[linePoints.length-1];
		return linePoints;
	}
	
	var ret =new Array(n);
	
	var perLo = Math.abs( a[1]-b[1] ) / n ;
	var perLa = Math.abs( a[0]-b[0] ) / n ;
	
	for (var  i=0 ;i<n; i++){
		ret[i] =  [a[0] + (i+1)*perLa , a[1]+(i+1)*perLo , HEIGHT ];
		//alert(V3.toString(ret[i]));
		
	}
	//alert(V3.toString(chkPoints[chkPoints.length-1]));
	return ret ;
}


function prepareRoute(){
	//startPos = [30.57833333 , 103.9466667, 0];
	//startPos = [31.3874166665,104.70006942500001,0];
	//endPos = [31.43	, 104.7397222 ,0];
	
	chkPoints= getLinePoints (startPos,endPos,20 );
	plane.checkPoints();
	global.removeTargets();
	currentHeading=getAngle(startPos, currentTarget);
	plane.orientation.setHeading(currentHeading);
	plane.cameraCut();
}
 
function go(){
	currentTarget=null;
	prepareRoute();
	global.drawRoute();
	moveToStart();
	startPlane();
	
}

Plane.prototype.adjustPosition = function (){
	var me = this; 
	
	var lat = me.model.getLocation().getLatitude();
	var lon = me.model.getLocation().getLongitude();
	//messageFix( (HEIGHT + ge.getGlobe().getGroundAltitude(lat, lon)) + "=? " +me.tempAlt );
	if ( (HEIGHT + ge.getGlobe().getGroundAltitude(lat, lon)) != me.tempAlt ){
		message("移动位置" + me.lastMillis,1);
		me.teleportTo(lat, lon, currentHeading /180 * Math.PI);
		//调用后移除相应的事件，避免事件流过于复杂引起飞机运动缓慢
		google.earth.removeEventListener(ge, "frameend", adjustPosHandler);
	}else{
		message("没动" + me.lastMillis + "\n" +(HEIGHT + ge.getGlobe().getGroundAltitude(lat, lon)) + "=? " +me.tempAlt ,1);
	}
	
	
};

function afterFrameEnd(event){
	if(plane.doTick){
		plane.tick();
	}
}

function adjustPosHandler(event){
	
	plane.adjustPosition();
	
}

function startPlane(){
	google.earth.addEventListener(ge, "frameend", afterFrameEnd);
	gasButtonDown = true;
	plane.doTick = true;
	global.drawTarget();
	//message("目标角:"+plane.model.getOrientation().getHeading());
}

function stopPlane(){
	gasButtonDown = false;
	plane.doTick = false;
	google.earth.removeEventListener(ge, "frameend", afterFrameEnd);
}
function moveToStart(){
	moveTo(startPos);
}
function moveToNext(){
	if(currentIndex<chkPoints.length-1){
		moveTo(chkPoints[currentIndex+1]);
	}else{
		moveTo(endPos);
	}
}
function moveToEnd(){
	moveTo(endPos);
}

function moveTo(point){
	if(!point) return;
	
	plane.teleportTo(point[0]	, point[1]	,currentHeading /180 * Math.PI);
	//google.earth.removeEventListener(ge, "frameend", adjustPosHandler);
}
function message(val,index){
	if(!index)index = 0;
	
	el('inforBox'+index).value=val;
}

function changeSpeed(v){
	if(v>0 && ACCEL< 20){v=1;}
	if(v<0 && ACCEL<= 20){v=-1;}
	
	ACCEL+=v;
	
	if(ACCEL>300) {ACCEL=300;}
	if(ACCEL<0) {ACCEL=1;}
	
	el('speed').value=ACCEL;
}



