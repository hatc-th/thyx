// milktruck.js  -- Copyright Google 2007

// Code for Monster Milktruck demo, using Earth Plugin.

window.plane = null;
window.global = null;

// Pull the Milktruck model from 3D Warehouse.
var MODEL_URL =  'earthview/resource/cessna162_0.kmz';
  //var MODEL_URL = 'http://www.barnabu.co.uk/geapi/flightsim/hawk.kmz';
var TICK_MS = 66;

var STEER_ROLL = -1.0;
var ROLL_SPRING = 0.25;
var ROLL_DAMP = -0.16;

var ACCEL = 100 ; //前进的加速度
var vel_limit = 1000; //速度限制
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

  window.google.earth.fetchKml(global.ge, _modelURL? _modelURL: basePath + MODEL_URL,
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

  me.model.setAltitudeMode(me.ge.ALTITUDE_ABSOLUTE);
  //me.orientation.setHeading(100);
  me.model.setOrientation(me.orientation);

  me.ge.getFeatures().appendChild(me.placemark);

  me.balloon = me.ge.createHtmlStringBalloon('');
  me.balloon.setFeature(me.placemark);
  me.balloon.setMaxWidth(200);

  //初始化位置
  linePlacemarks=[];
  prepareRoute();
  moveToStart();
  me.lastMillis = (new Date()).getTime();

  var href = window.location.href;
  var pagePath = href.substring(0, href.lastIndexOf('/')) + '/';

  google.earth.addEventListener(me.ge, "frameend", afterFrameEnd);
  
  me.ge.getWindow().blur();
  // If the user clicks on the Earth window, try to restore keyboard
  // focus back to the pame.ge.
  /*google.earth.addEventListener(me.ge.getWindow(), "mouseup", function(event) {
      me.ge.getWindow().blur();
  	});
  */
}

var leftButtonDown = false;
var rightButtonDown = false;
var gasButtonDown = false;
var reverseButtonDown = false;

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
  //message("ticking...");
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

  var groundAlt = me.ge.getGlobe().getGroundAltitude(lla[0], lla[1]);
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
      me.vel = V3.add(me.vel, V3.scale(dir, ACCEL * dt)); // V = V0 + a * t
      
    } else if (reverseButtonDown) {
      if (forwardSpeed > -MAX_REVERSE_SPEED)
        me.vel = V3.add(me.vel, V3.scale(dir, -DECEL * dt));
    }
  }
  

  // Air drag. 空气阻力
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
  
  if(forwardSpeed * 3.6 > vel_limit ){
	  forwardSpeed = vel_limit/3.6;
	  var upVel= me.vel[2]; //保留竖向速度
	  me.vel = V3.scale(me.modelFrame[1] , forwardSpeed);
	  me.vel[2]=upVel;
  }
  
  groundAlt = me.ge.getGlobe().getGroundAltitude(lla[0], lla[1]);
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
  var old_lla= lla;
  me.pos = V3.add(me.pos, deltaPos);
  
  me.forwardSpeed = forwardSpeed;
  
  gpos = V3.add(me.localAnchorCartesian,
                M33.transform(me.localFrame, me.pos));
  lla = V3.cartesianToLatLonAlt(gpos);
  
  flight_distance += getDistance(old_lla[0], old_lla[1], lla[0], lla[1]);
  //更新飞行状态
  $('#vel').val(Math.round(forwardSpeed * 3.6));
  $('#height').val(Math.round(me.pos[2]/10)*10);
  $('#heading').val(me.model.getOrientation().getHeading());
  $('#distance').val(Math.round(flight_distance));
  
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
  me.roll = clamp(me.roll, -45, 45);
  absRoll -= me.roll;

  me.orientation.set(newhtr[0], newhtr[1], absRoll);
  
  var imgR = 0 ;//舱内图片旋转角度
  
  imgR = absRoll/4 ;
  if(imgR>20){
	  imgR= 20;
  }else if(imgR<-20){
	  imgR = -20;
  }
  
  global.rotate(imgR);
  //添加轨迹
  global.addRoutePoint([plane.model.getLocation().getLatitude(),plane.model.getLocation().getLongitude(), plane.model.getLocation().getAltitude()]);
  //me.tickPopups(dt);
  if(global.cameraMode == "left"){
	  global.cameraLeft();
  }else if (global.cameraMode == "right"){
	  global.cameraRight();
  }else if (global.cameraMode == "board"){
	  global.cameraOnboard();
  }else{
	  me.cameraFollow(dt, gpos, me.localFrame);
  }
  
  // Hack to work around focus bug
  // TODO: fix that bug and remove this hack.
  //me.ge.getWindow().blur();
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
  var globe = global.ge.getGlobe();
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
  var la = me.ge.createLookAt('');
  la.set(lo.getLatitude(), lo.getLongitude(),
		 lo.getAltitude() /* altitude */,
		 me.ge.ALTITUDE_ABSOLUTE,
         //fixAngle(180 + me.model.getOrientation().getHeading() + 45),
         me.model.getOrientation().getHeading() ,
         70, /* tilt */
         50 /* range */
         );
  me.ge.getView().setAbstractView(la);
};

var camHeadingOffset=0; //视角偏移量
Plane.prototype.cameraFollow = function(dt, truckPos, localToGlobalFrame) {
  var me = this;

  var c0 = Math.exp(-dt / 0.5);
  var c1 = 1 - c0;

  var la = me.ge.getView().copyAsLookAt(me.ge.ALTITUDE_RELATIVE_TO_GROUND);

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
  var camAlt = camLla[2] - (me.ge.getGlobe().getGroundAltitude(camLat, camLon)<0?0:me.ge.getGlobe().getGroundAltitude(camLat, camLon));
  //message (camAlt+10);
  la.set(camLat, camLon, camAlt+10 , me.ge.ALTITUDE_RELATIVE_TO_GROUND, 
        fixAngle(heading + camHeadingOffset), 80 /*tilt*/, 50 /*range*/);
  me.ge.getView().setAbstractView(la);
};


//var 

//heading is optional.
Plane.prototype.teleportTo = function(lat, lon, heading , absALT) {
	var me = this;

	me.model.getLocation().setLatitude(lat);
	me.model.getLocation().setLongitude(lon);
	var finalALt = 0;
	var alt = me.ge.getGlobe().getGroundAltitude(lat, lon);
	me.tempAlt = HEIGHT + alt;
	me.vel = [0, 0, 0];
	if(!absALT){
		//添加事件监控因地形载入延迟造成的海拔高度不准的问题
		google.earth.addEventListener(me.ge, "frameend", adjustPosHandler);
		//五秒后自动移除监听器
		setTimeout("google.earth.removeEventListener(global.ge, 'frameend', adjustPosHandler);",5000);
		//console.log("添加监视"+[me.ge,lat, lon,me.tempAlt]);
		finalALt = HEIGHT + alt;
	}else{
		finalALt = absALT;
		
	}
	me.model.getLocation().setAltitude( finalALt );
	
	if (heading == null) {
		heading = 0;
	}

	me.localAnchorLla = [lat, lon, 0];
	me.localAnchorCartesian = V3.latLonAltToCartesian(me.localAnchorLla);
	me.localFrame = M33.makeLocalToGlobalFrame(me.localAnchorLla);
	me.modelFrame = M33.identity();
	me.modelFrame[0] = V3.rotate(me.modelFrame[0], me.modelFrame[2], -heading);
	me.modelFrame[1] = V3.rotate(me.modelFrame[1], me.modelFrame[2], -heading);
	me.pos = [0, 0, finalALt];

	if(absALT){
		me.vel = V3.scale(me.modelFrame[1] , me.forwardSpeed);
	}
	
	me.cameraCut();
};
//自动调整地点的海拔高度
Plane.prototype.adjustPosition = function (){
	var me = this; 
	if(me.isMove()){
		google.earth.removeEventListener(me.ge, "frameend", adjustPosHandler);
		return;
	}
	var lat = me.model.getLocation().getLatitude();
	var lon = me.model.getLocation().getLongitude();
	var nowAlt = (HEIGHT + me.ge.getGlobe().getGroundAltitude(lat, lon) );
	if (  nowAlt != me.tempAlt ){
		me.teleportTo(lat, lon, currentHeading /180 * Math.PI, nowAlt);
		//调用后移除相应的事件，避免事件流过于复杂引起飞机运动缓慢
		//google.earth.removeEventListener(me.ge, "frameend", adjustPosHandler);
		return;
	}
	//message("监听海拔高度变化:"+nowAlt ,1);
};

function adjustPosHandler(event){
	plane.adjustPosition();
}

function afterFrameEnd(event){
	if(plane.doTick){
		plane.tick();
	}
}

Plane.prototype.teleportToRoutePoint =  function(lat, lon , targetIndex ){
	var me = this;
	//防止多次调用
	if(me.lastJumpLat==lat && me.lastJumpLon == lon) return ;
	me.lastJumpLat = lat;
	me.lastJumpLon = lon;
	
	currentIndex = targetIndex;
	currentTarget = linePoints[targetIndex];
	currentHeading = getAngle( [lat,lon,0], currentTarget);
	if(!currentHeading){ //直接跳到航线点的情况
		currentIndex = targetIndex+1;
		currentTarget = linePoints[targetIndex+1];
		currentHeading = getAngle( [lat,lon,0], currentTarget);
	}
	me.model.getOrientation().setHeading(currentHeading) ;
	
	me.teleportTo(lat, lon, currentHeading /180 * Math.PI,FLIGHTHEIGHT);
	
}
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
function getAngle(a ,b ){
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


Plane.prototype.checkPoints = function (){
	var me = this;
	var curPos = [me.model.getLocation().getLatitude(),  me.model.getLocation().getLongitude() , 0 ];
	
	if(currentTarget==null){
		currentIndex=1;
		lastTarget = chkPoints[0];
		currentTarget = chkPoints[1] ;
		cc.targetChange(1);
	}
	var dist= getDistance(curPos[0],curPos[1],currentTarget[0],currentTarget[1]);
	if( dist < 1000   ){	
		currentIndex++;
		if(currentIndex==chkPoints.length ){
			if(dist<=1000){
				stopPlane();
				alert("到达目的地 !" );
				return;
			}
			currentIndex--;
		}
		lastTarget=currentTarget;
		currentTarget = chkPoints[currentIndex] ;
		cc.targetChange(currentIndex);
	}
	
	$('#target').val(currentIndex);
	$('#targetLa').val(currentTarget[0]);
	$('#targetLo').val(currentTarget[1]);
	$('#targetR').val(currentHeading);
	$('#dis').val(parseInt(dist));
	
}


//----------------------THYX三维模拟控制-------------------------------

var linePoints=[];//航线点 array
var chkPoints ; // 路径点 array
var currentTarget=null; // 当前目标点 
var lastTarget =null;
var currentIndex; //当前路径点索引

var startPos , endPos; //起点，终点

var currentHeading=0; //飞机朝向角

var FLIGHTHEIGHT=2000; //飞行高度
var HEIGHT=0;//地面点相对高度
var flight_distance=0; //飞行里程；
var current_sample_index = 0; //当前采样点索引
// 等分航线点
function getLinePoints( a ,b, n ){
	linePoints = coordinateLine;
	if(linePoints.length==0){
		linePoints = [[ 31.43458,104.736,"测试起点"],[ 31.41,104.83,"测试点1"],[ 31.40,104.92,"测试点2"],[ 31.42,104.99,"测试点3"],[ 31.333,104.9879,"测试点4"],
		              [ 31.25,104.978,"测试点5"], [ 31.186,104.85,"测试点6"],[ 31.08,104.749,"测试点7"],[ 31.045,104.68,"测试点8"],[ 31.0256,104.598,"测试点9"],
		              [ 30.986,104.546,"测试点10"],[ 30.97,104.415,"测试点11"],[ 30.95,104.33,"测试点12"],[ 30.875,104.32,"测试点13"],[ 30.816,104.376,"测试点14"],
		              [ 30.8653,104.3859,"测试点15"],[ 30.87,104.3957,"测试终点"]];
	}
	if(linePoints.length>0){
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
	chkPoints =[];
	currentTarget=null;
	currentIndex=-1;
	
	chkPoints= getLinePoints (startPos,endPos,20 );
	plane.checkPoints();
	global.removeTargets();
	currentHeading=getAngle(startPos, currentTarget);
	plane.orientation.setHeading(currentHeading);
	plane.cameraCut();
	global.drawFlyRoute();
	//message(coordinateLine);
}
 
function go(){
	currentTarget=null;
	flight_distance=0;
	current_sample_index=0;
	cc.planeStart();
	moveToStart();
	startPlane();
}

Plane.prototype.isMove = function (){
	var me = this;
	return me.doTick;
}

var reportObj;  //定时器对象，每秒报告位置
function startPlane(){
	google.earth.addEventListener(global.ge, "frameend", afterFrameEnd);
	global.removeTargets();
	global.drawTarget();
	
	gasButtonDown = true;
	plane.doTick = true;
	//global.cameraBack();
	//message("目标角:"+plane.model.getOrientation().getHeading());
	if(reportObj)clearInterval(reportObj);
	reportObj= setInterval("reportPos()",1000);
}

function stopPlane(){
	gasButtonDown = false;
	plane.doTick = false;
	google.earth.removeEventListener(global.ge, "frameend", afterFrameEnd);
	clearInterval(reportObj);
}
function moveToStart(){
	moveTo(startPos);
}
function moveToEnd(){
	moveTo(endPos);
}
function moveTo(point){
	if(!point) return;
	
	plane.teleportTo(point[0]	, point[1]	,currentHeading /180 * Math.PI);
	current_sample_index=0;
	cc.planeMove();
}
function message(val,index){
	if(!index)index = 0;
	if(plane) val= plane.lastMillis + '====\n' + val;
	$('#inforBox'+index).val(val);
}
function reportPos(){
	if(plane){
		message("当前位置："+plane.model.getLocation().getLatitude()+","+plane.model.getLocation().getLongitude(),1);
	}else{
		clearInterval(reportObj);
	}
	cc.planeMove();
}

function changeSpeed(v){
	
	vel_limit+=v;
	
	if(vel_limit>=1500) {vel_limit=1500;}
	if(vel_limit<=100) {vel_limit=100;}
	
}



