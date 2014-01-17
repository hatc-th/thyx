<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%@ include file="/common/taglibs.jsp" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%-- ************************************
 * System:    	通航运行管理与服务保障系统
 * Function：  	通航运行管理与服务保障系统主页面
 * Author:    	罗凤梅
 * Copyright: 	北京华安天诚科技有限公司
 * Create:    	VER1.00 2013.11.04
 * Modify:    
************************************ --%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>通航运行管理系统</title>
	<meta charset="GBK" />
	<link href="css/skin2/common/main.css" type="text/css" rel="stylesheet"/>
	<link rel="Shortcut Icon" href="images2/logo.ico" type="image/x-icon" />
	<link href="css/skin2/common/context.css" type="text/css" rel="stylesheet" />
	<script src="js/jquery-1.9.1.js" type="text/javascript"></script>

	<script type="text/javascript">
		$(document).ready(function(){
			resetSize2();
		});
		
		function resetHeight(){
			var winHeight = document.documentElement.clientHeight;
			var winWidth = document.documentElement.clientWidth;

			if(winHeight > 943) {
				$(".centerContext").css({height:winHeight-100});
				$(".iframeCenter").css({height:winHeight-100});
			}else {
				$(".centerContext").css({height:836});
				$(".iframeCenter").css({height:836});
			}
			if(winWidth > 1663) {
				$(".centerContext").css({width:winWidth-16});
				$(".iframeCenter").css({width:winWidth-16});
			} else{
				$(".centerContext").css({width:1663});
				$(".iframeCenter").css({width:1663});
			}
			resizeTimer2 = null;
		}
		
		var resizeTimer2 = null;

		function resetSize2(){
			if(resizeTimer2 == null){
				resizeTimer2 = setTimeout("resetHeight()",100);
			}
		}
  
  	</script>	

</head>

<body onresize="resetSize2();" >
<div class="globalDiv" >
<!--==================================================================== TOP START =============================================================-->
  <div class="top">
		<!--<div class="topTop">
			<div class="topRight">
				<ul>
					<li class="liBorder"><a href="index.html" target="iframe"><img src="images/th_xtb_xg.png" align="middle"/>修改密码</a></li>
					<li class="liBorder"><a href="index.html" target="iframe"><img src="images/th_xtb_ys.png" align="middle"/>重新登录</a></li>
					<li class="liBorder"><a href="index.html" target="iframe"><img src="images/th_xtb_wh.png" align="middle"/>帮&nbsp;&nbsp;助</a></li>
					<li><a href="index.html" target="iframe"><img src="images/th_xtb_tc.png" align="middle" />退&nbsp;&nbsp;出</a></li>
				</ul>
			</div>
		</div>-->
		<div class="topCenter">
			<div class="topCenterLeft">
				<div class="titleImgLeft"></div>
				<div class="titleImgRight"></div>
			</div>
			<div class="topCenterRight">
				<ul>
					<li class="liBorder"><a href="index.html" target="iframe"><img src="images2/th_xtb_xg.png" align="middle"/>修改密码</a></li>
					<li class="liBorder"><a href="index.html" target="iframe"><img src="images2/th_xtb_ys.png" align="middle"/>重新登录</a></li>
					<li class="liBorder"><a href="index.html" target="iframe"><img src="images2/th_xtb_wh.png" align="middle"/>帮&nbsp;&nbsp;助</a></li>
					<li class="liBorderExit"><a href="index.html" target="iframe"><img src="images2/th_xtb_tc.png" align="middle" />退&nbsp;&nbsp;出</a></li>
				</ul>
			</div>
		</div>
		<div class="topBottom">
			<div class="topBottomLeft">
				<div class="ShowleftRoundCorner"></div>
				<div class="meunContext">
					<ul class="meunUl">
						<li class="homeImg"><img src="images2/th_31.png"/></li>
						<li class="ulLiNoImg ulLiNoImgBr">
							<div class="ulLiNoImgBrDiv"><a href="index.html" target="iframe"><span>飞行计划</span></a></div>
							<div class="ulLiNoImgBrJgDiv"></div>
								<ul class="ulliNoImgUl">
									<c:if test="${session_reqidentity.userRole == 'R0000' || session_reqidentity.userRole == 'R9999' }">
										<li class="showDd"><a href="index.html" target="iframe">飞行计划申请</a>
											<div class="dlDdMuen">
												<dl>
													<dd class="" style="padding:0px; background:none;height:13px;">
														<div class="smrcLeftTo">
															<div class="smrcLeftLeftTo"></div>
															<div class="smrcLeftRightTo"></div>
														</div>
														<div class="smrcRight"><img src="images/A1_10.png" /></div>
													</dd>
													<dd><a href="flyPlanAction.do?method=initFlyPlanBaseInfo&operFlag=edit&menuFunctionId=BM10101" target="iframe">飞行计划新建</a></dd>
													<dd><a href="flyPlanAction.do?method=searchFlyPlanList&searchType=manage&menuFunctionId=BM10102" target="iframe">飞行计划管理</a></dd>
													<dd><a href="flyPlanAction.do?method=searchFlyPlanList&searchType=activateQ&menuFunctionId=BM10103" target="iframe">飞行计划激活</a></dd>
													<dd><a href="flyPlanAction.do?method=searchFlyPlanList&searchType=query&menuFunctionId=BM10102" target="iframe">飞行计划查询</a></dd>
													<dd class="" style="padding:0px; background:none;">
														<div class="smrcLeftTo">
															<div class="smrcLeftLeft"><img src="images/A1_09.png" /></div>
															<div class="smrcLeftRightTo"></div>
														</div>
														<div class="smrcRight"><img src="images/A1_11.png" /></div>
													</dd>
												</dl>
											</div>									
										</li>
									</c:if>
									<c:if test="${session_reqidentity.userRole != 'R9999' }">
										<li class="showDd"><a href="index.html" target="iframe">飞行计划审批</a>
											<div class="dlDdMuen">
												<dl>
													<dd class="" style="padding:0px; background:none;height:13px;">
														<div class="smrcLeftTo">
															<div class="smrcLeftLeftTo"></div>
															<div class="smrcLeftRightTo"></div>
														</div>
														<div class="smrcRight"><img src="images/A1_10.png" /></div>
													</dd>
													<dd><a href="planAuditAction.do?method=searchList&menuFunctionId=BM10201&managerType=flow" target="iframe">飞行计划审批管理</a></dd>
													<dd><a href="planSuspendAction.do?method=searchList&menuFunctionId=BM10202&managerType=suspend" target="iframe">飞行计划中止/激活</a></dd>
													<dd><a href="planSuspendAction.do?method=searchList&menuFunctionId=BM10203&managerType=invalid" target="iframe">飞行计划失效</a></dd>
													<dd><a href="planAuditAction.do?method=searchList&menuFunctionId=BM10204&managerType=query" target="iframe">飞行计划查询</a></dd>
													<dd><a href="planAuditAction.do?method=searchList&menuFunctionId=BM10205&managerType=history" target="iframe">审批记录查询</a></dd>
													<dd class="" style="padding:0px; background:none;">
														<div class="smrcLeftTo">
															<div class="smrcLeftLeft"><img src="images/A1_09.png" /></div>
															<div class="smrcLeftRightTo"></div>
														</div>
														<div class="smrcRight"><img src="images/A1_11.png" /></div>
													</dd>
												</dl>
											</div>
										</li>
									</c:if>
									<li class="showDd"><a href="index.html" target="iframe">飞行计划实施管理</a>
										<div class="dlDdMuen">
											<dl>
												<dd class="" style="padding:0px; background:none;height:13px;">
													<div class="smrcLeftTo">
														<div class="smrcLeftLeftTo"></div>
														<div class="smrcLeftRightTo"></div>
													</div>
													<div class="smrcRight"><img src="images/A1_10.png" /></div>
												</dd>
												<dd><a href="ActFlyPlanAction.do?method=queryActFlyPlanList&selectFlag=1" target="iframe">飞行计划实施确认</a></dd>
												<dd><a href="ActFlyPlanAction.do?method=queryActFlyPlanList&selectFlag=2" target="iframe">飞行计划实施调整</a></dd>
												<dd><a href="ActFlyPlanAction.do?method=queryActFlyPlanList&selectFlag=3" target="iframe">飞行计划完成确认</a></dd>
												<dd class="" style="padding:0px; background:none;">
													<div class="smrcLeftTo">
														<div class="smrcLeftLeft"><img src="images/A1_09.png" /></div>
														<div class="smrcLeftRightTo"></div>
													</div>
													<div class="smrcRight"><img src="images/A1_11.png" /></div>
												</dd>
											</dl>
										</div>
									</li>
									<li><a href="planPigeonholeAction.do?method=searchList" target="iframe">飞行计划归档查询</a></li>
									<!-- ================================设置圆角========================================= -->
									<li class="showMeunRoundCorner" style="margin:0px;padding:0px; background:none;">
										<div class="smrcLeft">
											<div class="smrcLeftLeft"><img src="images/A1_06.png" /></div>
											<div class="smrcLeftRight"></div>
										</div>
										<div class="smrcRight"><img src="images/A1_08.png" /></div>
									</li>
						  		</ul>
						</li>
						<li class="ulLiNoImg">
						<c:if test="${session_reqidentity.userRole != 'R9999' }">
							<div class="ulLiNoImgBrDiv"><a href="index.html" target="iframe"><span>低空空域飞行监控</span></a></div>
						</c:if>
						<div class="ulLiNoImgBrJgDiv"></div>
							<ul class="ulliNoImgUl">
									<li><a href="index.html" target="iframe">飞行实时监控</a></li>
									<li><a href="index.html" target="iframe">空域使用监控</a></li>
									<!-- ================================设置圆角========================================= -->
									<li class="showMeunRoundCorner" style="margin:0px;padding:0px; background:none;">
										<div class="smrcLeft">
											<div class="smrcLeftLeft"><img src="images/A1_06.png" /></div>
											<div class="smrcLeftRight"></div>
										</div>
										<div class="smrcRight"><img src="images/A1_08.png" /></div>
									</li>
						  		</ul>
						</li>
						<li class="ulLiNoImg">
						<c:if test="${session_reqidentity.userRole != 'R9999' }">
							<div class="ulLiNoImgBrDiv"><a href="index.html" target="iframe"><span>飞行服务管理</span></a></div>
						</c:if>
						<div class="ulLiNoImgBrJgDiv"></div>
							<ul class="ulliNoImgUl">
       							<li><a href="航行情报查询/主页.html" target="iframe">航行情报管理</a></li>
								<li class="showDd"><a href="index.html" target="iframe">气象情报</a>
									<div class="dlDdMuen">
											<dl>
												<dd class="" style="padding:0px; background:none;height:13px;">
													<div class="smrcLeftTo">
														<div class="smrcLeftLeftTo"></div>
														<div class="smrcLeftRightTo"></div>
													</div>
													<div class="smrcRight"><img src="images/A1_10.png" /></div>
												</dd>
												<dd><a href="metinf/crTextualMeteo.html" target="iframe">文本气象情报新建</a></dd>
												<dd><a href="metinf/crChartMeteo.html" target="iframe">图像气象情报新建</a></dd>
												<dd><a href="metinf/textualMeteo.html" target="iframe">文本气象情报管理</a></dd>
												<dd><a href="metinf/chartMeteo.html" target="iframe">图像气象情报管理</a></dd>
												<dd><a href="metinf/meteoInfoSch.html" target="iframe">气象情报查询</a></dd>
												<dd class="" style="padding:0px; background:none;">
													<div class="smrcLeftTo">
														<div class="smrcLeftLeft"><img src="images/A1_09.png" /></div>
														<div class="smrcLeftRightTo"></div>
													</div>
													<div class="smrcRight"><img src="images/A1_11.png" /></div>
												</dd>
											</dl>
										</div>								
								</li>
								<li><a href="飞行情报查询/主页.html" target="iframe">飞行情报管理</a></li>
								<li><a href="metinf/infoSch4Pi.html" target="iframe">情报综合查询</a></li>
								<li><a href="搜索救援/搜索救援.html" target="iframe">搜索与救援管理</a></li>
								<!-- ================================设置圆角========================================= -->
								<li class="showMeunRoundCorner" style="margin:0px;padding:0px; background:none;">
									<div class="smrcLeft">
										<div class="smrcLeftLeft"><img src="images/A1_06.png" /></div>
										<div class="smrcLeftRight"></div>
									</div>
									<div class="smrcRight"><img src="images/A1_08.png" /></div>
								</li>
						  	</ul>
						</li>
						<li class="ulLiNoImg">
						<div class="ulLiNoImgBrDiv"><a href="index.html" target="iframe"><span>系统管理</span></a></div>
						<div class="ulLiNoImgBrJgDiv"></div>
							<ul class="ulliNoImgUl">
								<li><a href="index.html" target="iframe">自动提醒设置</a></li>
								<li><a href="index.html" target="iframe">电子地图管理</a></li>
								<li><a href="index.html" target="iframe">用户管理</a></li>
								<c:if test="${session_reqidentity.userRole == 'R1000' }">
									<li><a href="index.html" target="iframe">系统角色管理</a></li>
								</c:if>
								<li class="showMeunRoundCorner" style="margin:0px;padding:0px; background:none;">
									<div class="smrcLeft">
										<div class="smrcLeftLeft"><img src="images/A1_06.png" /></div>
										<div class="smrcLeftRight"></div>
									</div>
									<div class="smrcRight"><img src="images/A1_08.png" /></div>
								</li>
					  		</ul>
						</li>
					</ul>
		  </div>
	</div>
			<div class="topBottomright">
				<div class="messageContext">
					<img src="images/th_38.png" class="homeImgTo" align="middle"; />
					<span>欢迎您：${session_user.name}&nbsp;&nbsp;华安天诚&nbsp;&nbsp;${hpDate}</span>
				</div>
				<div class="ShowRightRoundCorner"></div>
			</div>
  </div>
</div>
<!--==================================================================== TOP END ================================================================-->
<!--==================================================================== CENTER START ===========================================================-->
	<div class="centerContext">
		<!--<div class="contextTitle">标题导航》标题导航》标题导航》标题导航</div>-->
		<div class="iframeCenter">
		
			<c:choose>
  		        <c:when test="${session_reqidentity.userRole == 'R0000' || session_reqidentity.userRole == 'R9999' }">
  		            <iframe id="downiframe" name="iframe" src="flyPlanAction.do?method=initFlyPlanBaseInfo&operFlag=edit&menuFunctionId=BM10101" frameborder="0" style="width:100%; height: 100%"></iframe>
  		        </c:when>
  		        <c:when test="${session_reqidentity.userRole == 'R1999'}">
  		            <iframe id="downiframe" name="iframe" src="planAuditAction.do?method=searchList&menuFunctionId=BM10201&managerType=flow" frameborder="0" style="width:100%"></iframe>
  		        </c:when>
  		        <c:when test="${session_reqidentity.userRole == 'R1000'}">
  		            <iframe id="downiframe" name="iframe" src="planAuditAction.do?method=searchList&menuFunctionId=BM10201&managerType=flow" frameborder="0" style="width:100%"></iframe>
  		        </c:when>
  		        <c:otherwise>
  		        	<iframe id="downiframe" name="iframe" src="index.html" frameborder="0"></iframe>
  		        </c:otherwise>
  		    </c:choose>	
		</div>
	</div>	
	
	
<!--==================================================================== CENTER END ============================================================-->
	
	
<!--==================================================================== BOTTOM END ============================================================-->
	<div>	
	</div>
<!--==================================================================== BOTTOM END ============================================================-->
</div>
</body>
</html>