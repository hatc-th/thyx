<%@ page language="java" pageEncoding="gbk"%>
<%@ include file="/common/taglibs.jsp" %>
<%@ include file="/common/includeCss.jsp"%>
<!DOCTYPE html
      PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
      "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="zh" lang="zh">
<head>
	
	<title>归档计划查询</title>
    <meta http-equiv="content-language" content="zh" />
    <meta http-equiv="content-script-type" content="text/javascript" />
    <link type="text/css" rel="stylesheet" href="<%=skin %>/css/tip.css" title="default" />
   	<link type="text/css" rel="stylesheet" href="<c:url value='/skin/p1/css/tabstyle.css'/>"/>
	<link type="text/css" rel="stylesheet" href="<c:url value='/skin/core/tabcore.css'/>"/>
	<script src="<c:url value="/js/platform.js" />"></script>
	<script src="<c:url value="/js/widget_calendar.js" />"></script>
	<script type="text/javascript">
		//查询处理
		function query() {
			
        	document.form1.action='<c:url value="/planPigeonholeAction.do?method=searchList"/>';
        	document.form1.submit();
		}
	  
	</script>
	</head>
	<body onload="setdivMidHeight('divform_21');" onresize="setdivMidHeight('divform_21');">
		 <form method="post" name="form1">
			<div id="divtop">
			 	<c:import url="/common/pageTitle.jsp" />
			</div>
			<div id="divmid" >
				<div id="divctrl_20">
					<table class="tbctrl">
						<tr>
						<td class="text_class_b" style="width: 10%;">
							计划名称：
						</td>
						<td style="width: 15%;">
							<input id="planName" name="planName" type="text" class="inputText" value="${planName}"/>
						</td>
						<td class="text_class_b" style="width: 10%;">
							飞行员：
						</td>
						<td style="width: 15%;">
							 <input id="pilot" name="pilot" type="text" class="inputText" value="${pilot}"/>
						</td>
						<td class="text_class_b" style="width: 10%;">
							起飞机场：
						</td>
						<td style="width: 15%;">
							<input id="ariportFrom" name="ariportFrom" type="text" class="inputText" value="${ariportFrom}"/>
						</td>
						<td class="text_class_b" style="width: 10%;">
							目的机场：
						</td>
						<td style="width: 15%;">
							<input id="ariportTo" name="ariportTo" type="text" class="inputText" value="${ariportTo}"/>
						</td>
						<td></td>
					</tr>
					<tr>
						<td class="text_class_b" style="width: 10%;">
							飞行规则：
						</td>
						<td style="width: 5%;">
							<select id="flyRule" name="flyRule" style="width: 130px;">
                                <option value="">
                                    	全部
                                </option>
                                <c:forEach items="${flyRuleList}" var="item">
                                    <c:choose>
                                        <c:when test="${flyRule == item.code_id}">
                                            <option value="<c:out value="${item.code_id}"/>" selected="selected">
                                                <c:out value="${item.name}" />
                                            </option>
                                        </c:when>
                                        <c:otherwise>
                                          <option value="<c:out value="${item.code_id}"/>">
	                                             <c:out value="${item.name}" />
    	                                     </option>
                                        </c:otherwise>
                                    </c:choose>
                                </c:forEach>
                            </select>
						</td>
						<td class="text_class_b" style="width: 10%;">
							预计起飞日期：
						</td>
						<td style="width: 5%;">
							<input id="exceptStartDate" name="exceptStartDate" type="text" class="inputText" value="${exceptStartDate}"/><input type="button" value="..." 
		                    onclick="return showCalendar('exceptStartDate', '%Y-%m-%d');" class="inputButtonDate" />
						</td>
						<td class="text_class_b" style="width: 10%;">
							航空器型号：
						</td>
						<td style="width: 5%;">
							<select id="aircraftType" name="aircraftType" style="width: 130px;">
									<option value=" ">
										全部
									</option>
								<c:forEach items="${aircraftTypeList}" var="item">
								<c:choose>
		                    		<c:when test="${item.code_id == aircraftType}">
		                    			<option value="${item.code_id }" selected>${item.name}</option>
		                    		</c:when>
		                    		<c:otherwise>
		                    			<option value="${item.code_id }">${item.name}</option>
		                    		</c:otherwise>
		                    	</c:choose>
								</c:forEach>
							</select>
						</td>
						<td class="text_class_b" style="width: 10%;">
							计划类别：
						</td>
						<td style="width: 5%;">
							<select id="flyPlanKind" name="flyPlanKind" style="width: 130px;">
                                <option value="">
                                    	全部
                                </option>
                                <c:forEach items="${flyPlanKindList}" var="item">
                                    <c:choose>
                                        <c:when test="${flyPlanKind == item.code_id}">
                                            <option value="<c:out value="${item.code_id}"/>" selected="selected">
                                                <c:out value="${item.name}" />
                                            </option>
                                        </c:when>
                                        <c:otherwise>
                                          <option value="<c:out value="${item.code_id}"/>">
	                                             <c:out value="${item.name}" />
    	                                     </option>
                                        </c:otherwise>
                                    </c:choose>
                                </c:forEach>
                            </select>
						</td>
						<td class="tdbutton" style="padding-right: 50px; text-align: right;">
							<input type="button" value="查 询" onclick="javascript:query();" class="inputButtonSearch" />
						</td>
					</tr>
					</table>
				</div>
				<div id="divform_21">
			      <div class="divform_caption" id = "divform_caption">
			       	<table style="width: 100%; height: 10px; align:center;border:1px;">
			           	<tr>
							<td style="width: 5%; text-align: center;"></td>
                           <td style="width: 10%; text-align: left;">
							飞行计划名称
                           </td>
                           <td style="width: 10%; text-align: left;">
							预计起飞日期
                           </td>
                           <td style="width: 9%; text-align: left;">
							预计起飞时刻
                           </td> 
                           <td style="width: 9%; text-align: left;">
							预计降落时刻
                           </td>
                           <td style="width: 9%; text-align: left;">
							起飞机场
                           </td>
                           <td style="width: 9%; text-align: left;">
							目的机场
                           </td>
                           <td style="width: 17%; text-align: left;">
							计划类别
                           </td>
                           <td style="width: 8%; text-align: left;">
							航空器型号
                           </td>
                           <td style="width: 7%; text-align: left;">
							飞行员
                           </td>
                           <td style="width: 7%; text-align: left;">
							计划类型
                           </td>
						</tr>
			        </table>
			    </div>
		        <div class="divform_info" style="overflow: hidden; " id="divform_info">
					<table style = "height:100%">
						<c:forEach var="tfpl" items="${tfplList}" varStatus="status">
						<c:choose>
		    		        <c:when test="${status.index % 2 == 0}">
		    		            <c:set var="tr_class" value="tr_single" scope="page" />
		    		        </c:when>
		    		        <c:otherwise>
		    		            <c:set var="tr_class" value="tr_double" scope="page" />
		    		        </c:otherwise>
		    		    </c:choose>
		    		    <tr class="${tr_class}">
		    		    	<td style="text-align: center;width: 5%;">
							</td>
		    		    	<td style="text-align: left;width: 10%">
	    		    			<span class="fontblue" title="<c:out value="${tfpl[0]}" />"><string-truncation:truncation value="${tfpl[0]}" length="12"/></span>
		    		    	</td>
		    		    	<td style="text-align: left;width: 10%">
    		    				<c:out value="${tfpl[1]}" />
		    		    	</td>
		    		    	<td style="text-align: left;width: 9%">
		    		    		<c:out value="${tfpl[2]}" />
		    		    	</td>
		    		    	<td style="text-align: left;width: 9%">
		    		    		<c:out value="${tfpl[3] } ~ ${tfpl[4] }" />
		    		    	</td>
		    		    	<td style="text-align: left;width: 9%">
								 <c:out value="${tfpl[5]}" />
		    		    	</td>
		    		    	<td style="text-align: left;width: 9%">
		    		    		<c:out value="${tfpl[6]}" />
		    		    	</td>
		    		    	<td style="text-align: left;width: 17%">
								 <c:out value="${tfpl[7]}" />
		    		    	</td>
		    		    	<td style="text-align: left;width: 8%">
								 <c:out value="${tfpl[8]}" />
		    		    	</td>
		    		    	<td style="text-align: left;width: 7%">
		    		    		<c:out value="${tfpl[9]}" />
		    		    	</td>
		    		    	<td style="text-align: left;width: 7%">
		    		    		<c:out value="${tfpl[11]}" />
		    		    	</td>
		    		    </tr>
		    		</c:forEach>
		    		<c:if test="${rollPage.pagePer - rollPage.currentlyPagePer > 0}">
		    		<c:forEach begin="${rollPage.currentlyPagePer}" end="${rollPage.pagePer-1}" step="1" varStatus="status">
		    			<c:choose>
	                        <c:when test="${(status.index) % 2 == 0}">
	                            <c:set var="tr_class" value="tr_single" scope="page" />
	                        </c:when>
	                        <c:otherwise>
	                            <c:set var="tr_class" value="tr_double" scope="page" />
	                        </c:otherwise>
	                    </c:choose>
		    		</c:forEach>
		    		</c:if>
					</table>
				</div>
			</div>
	    </div>
		<div id="divbot"><c:import url="/common/rollpage.jsp" />
	    </div>
   	</form>
	</body>
</html>
