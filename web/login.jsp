<%@ page language="java" pageEncoding="gbk"%>
<%@ include file="common/taglibs.jsp"%>

<!DOCTYPE html>
<html>
<head>
<title>通航登录</title>
<meta http-equiv="Content-Type" content="text/html; charset=gbk" />
<meta http-equiv="content-language" content="zh" />
<meta http-equiv="content-script-type" content="text/javascript" />
<meta name="description" content="login for XTMS" />
<meta name="robots" content="none" />
<meta name="author" content="ZhouYang" />
<meta name="copyright" content="2010, Beijing HATC Technology Co., LTD" />
<link rel="Shortcut Icon" href="images2/logo.ico" type="image/x-icon" >
</head>

<c:set var="imgPath" value="images/login2"></c:set>
<style>

body {
	background:url(${imgPath}/BJ.png)
}
a img {
	border:0px;
}
td {
	padding: 0 0 0 0 ;
}
table {
	background-repeat: no-repeat;
}
.loginInput input {
	border-style:hidden;
	vertical-align:middle;
	height:22px;
	width:172px;
	outline:none;
	background-color:#FFFFFF;
	color:#000000;
	font-size:13px;
	font-family:微软雅黑;
}
.loginInputButton input {
	border:none;
	background:url(${imgPath}/button_bg.png);
	width:60px;
	color:#f0f0f0;
	font-family:微软雅黑;
	font-size:14px;
	font-weight:bold;
	height:25px;
}
.checkCode input {
	background:url(${imgPath}/checkCode_bg.png);
	vertical-align:bottom;
	border:hidden;
	vertical-align:middle;
	height:23px;
	width:74px;
	color:#999999;
	font-size:13px;
	font-family:微软雅黑;
	outline:none;
	
}


</style>

</head>
<script src="<c:url value="/js/platform.js" />"></script>
<script>
      
var cur=2;
function do_login() {
             // 验证[用户名]
	var loginId = document.getElementById('loginId');
	if (isFilled(loginId, '请填写用户名！') === false) {
	    return false;
	}
	// 验证[密码]
	var password = document.getElementById('password');
	if (isFilled(password, '请填写密码！') === false) {
		return false;
	}
	document.getElementById('uiCode').value=cur;
	document.forms[0].submit();
}	

function do_cls() {
	document.forms[0].reset();
}
			
	
function focus_bg( id ){

	var borderObj = document.getElementById(id);
	borderObj.background="${imgPath}/focus_bg.png";
}

function blur_bg(id){
	var borderObj = document.getElementById(id);
	borderObj.background="${imgPath}/login_bg.png";
}

var cur=2;
function changeRole(offset){
	cur+=offset;
	if(cur==4){
		cur=1;
	}else if(cur==0){
		cur=3;
	}
	document.getElementById('imgCt').src="${imgPath}/c"+(cur)+".png"
}
</script>
<body onkeypress="javascript:if (event.keyCode == 13) { do_login(); }">
<form method="post" action="<c:url value="/loginAction.do?method=webLogin" />">
<input type="hidden" id ="uiCode" name ="uiCode"/> 
<div id="mainDiv" align="center" style="margin-top:0px;margin-bottom:0px;">
  <table style="width:1124px;" border="0">
    <tr>
      <td style="width:258px;"><div align="center" ><a href="#" onclick="javascriot:changeRole(-1);" onfocus="blur()"><img src="${imgPath}/l1.png"  /></a> </div></td>
      <td style="width:608px;height:379px;vertical-align:bottom"><div align="center" ><img id="imgCt" src="${imgPath}/c2.png" /> </div></td>
      <td style="width:258px;"><div align="center"><a href="#" onclick="javascriot:changeRole(1);" onfocus="blur()"><img src="${imgPath}/r1.png"  /></a> </div></td>
    </tr>
    <tr>
      
      <td colspan="3" ><table align="center" style="height:80px;width:100%;">
          <tr>
            <td style="vertical-align:middle;text-align:center;"><img src="${imgPath}/t_icon.png"  /><img src="${imgPath}/t_logo.png"  /><img src="${imgPath}/t_sysname.png"  /></td>
          </tr>
        </table></td>
      
    </tr>
    <tr>
      <td style="text-align:center;" colspan="3">
      	<table id="userNameTb" align="center" style="width:218px;" background="${imgPath}/login_bg.png">
          <tr >
            <td ><img src="${imgPath}/usn_icon.png"/></td>
            <td><div align="right" class="loginInput"  >
                <input type="text" id="loginId" name="loginId" onfocus="focus_bg('userNameTb')" onblur="blur_bg('userNameTb')" value="ahm"/>
              </div></td>
          </tr>
        </table>
       </td>
    </tr>
    <tr>
      <td style="text-align:center;" colspan="3">
      	<table id="passwordTb" align="center" style="width:218px;" background="${imgPath}/login_bg.png" >
          <tr>
            <td ><img src="${imgPath}/pwd_icon.png"/></td>
            <td><div align="right"  class="loginInput" >
                <input type="password" id="password" name="password" onfocus="focus_bg('passwordTb')" onblur="blur_bg('passwordTb')" value="1"/>
              </div></td>
          </tr>
        </table></td>
    </tr>
    <tr>
      <td style="height:0px"></td>
      <td style="text-align:center;"><table align="center" border="0" style="height:24px;width:218px;"  >
          <tr>
            <td ><div class="checkCode"><input type="text"  maxlength="4" /></div></td>
            <td style="height:24px;width:70px;"><img src="${imgPath}/checkCode.png"/></td>
            <td style="width:60px;"><div class="loginInputButton" ><input  type="button" value="登  录" onclick="do_login();"/></div></td>
          </tr>
        </table></td>
      <td></td>
    </tr>
    
  </table>
  
</div>
<table align="center" style="height:80px;width:100%;vertical-align:bottom;" border="0">
      <tr>
        <td style="vertical-align:bottom;text-align:center;"><img src="${imgPath}/cprt.png"  /></td>
      </tr>
       
  </table>
</form>
</body>
</html>