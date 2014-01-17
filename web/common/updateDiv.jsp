<%@ page contentType="text/html; charset=gb2312" %>
<%@ include file="/common/taglibs.jsp" %>
<c:import url="/common/popSelect.jsp" />

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>变更处理DIV</title>
    
    <script type="text/javascript">
    //选择框数组，DIV不能覆盖选择框，因此需要隐藏页面上的选择框
	var selectArray = new Array(); 
	
    	function deliverInfo(disp) {
			//调用包含页的setParameter()函数设置隐藏参数
    		setParameter();
    		
	        if (navigator.appName != "Netscape") {
	            if (document.all) {  
	                iframeSelect = document.getElementsByTagName("select");
	            } else if (eval("frameMain")) {
	                iframeSelect = contentDocument.getElementsByTagName("select"); 
	            }
	        }  
	        if (disp == "show") {
	            
	            if (iframeSelect && iframeSelect.length > 0) {
	                for (var i = 0;i < iframeSelect.length;i++) {
	                    if (iframeSelect[i].style.visibility != "hidden") {
	                        iframeSelect[i].style.visibility = "hidden";
	                        selectArray[selectArray.length] = iframeSelect[i];
	                    }               
	                }
	            }
				document.getElementById('deliverdiv').style.display = 'block';
				document.getElementById('deliverdiv').focus();
	               
	         } else {
	            if (selectArray && selectArray.length > 0) {
	                for (var i = 0;i < selectArray.length;i++){
	                    selectArray[i].style.visibility = "visible";
	                }
	            }
				document.getElementById('deliverdiv').style.display = 'none';
	        }
		}
    	
    	function flowSubmit() {
    		document.formDeliver.submit();
    	}
    </script>
	<style type="text/css">
		.flowdiv
		{
			position:absolute; display:block; width:460px; 
			border:1px inset #003399; top: 139px; left: 181px;
			visibility: visible; background: #E4E6E4;
		}
		.flowtable
		{
			 border: 1px; background-color: #E9F2F7;
		}
	</style>
  </head>
  
	<body>
	<form action="<c:url value="/updateAction.do?method=save"/>" name="formDeliver">
		<!-- 隐含变量，由本页面的包含页面赋值。开始 -->
			<input id="updateType" name="updateType" type="hidden" value="">
			<input id="updateObjectId" name="updateObjectId" type="hidden" value="">
			<input id="backUrl" name="backUrl" type="hidden" value=""/>
		<!-- 隐含变量结束 -->
		<div id="deliverdiv"  class="flowdiv" style="">
			<table style="width: 100%; height: 100%; align:center;" class="flowtable">
		<tr>
			<td style="height: 40px;text-align: left;text-align: center;color: #DC1214;" 
			colspan="4">变更履历添加</td>
		</tr>
		<tr>
			<td style="height: 30px;text-align: center;">&nbsp;变更原因：</td>
			<td style="text-align: left;" colspan="3"><input id="reason" name="reason" type="text" 
                    	class="inputText" style="width: 340px;" value="" /></td>
		</tr>
		<tr>
			<td style="height: 70px;text-align: center;">&nbsp;变更内容：</td>
			<td style="text-align: left;" colspan="3"><textarea id="description" name="description" 
		                	style="width: 340px; height:60px"></textarea></td>
		</tr>
		<tr>
			<td style="height: 30px;text-align: center;">&nbsp;变更文档：</td>
			<td style="text-align: left;" colspan="3"><input id="file" name="file" type="file" 
                	class="inputText" onChange="javascript:fillname();" size="35"></td>
		</tr>
		<tr>
			<td style="width: 120px;height: 30px;text-align: center;">&nbsp;审批人员：</td>
			<td style="width: 160px; text-align: left;"><input id="approveUserName" name="approveUserName" type="text" 
                    	class="inputText" style="width: 100px;" value="" /><input id="approveUserId" name="approveUserId"
                    	 type="hidden" value="" /><input type="button" 
                    	class="inputButtonDate" value="..." onclick="switchWin(39, 'approveUserId_approveUserName');" /></td>
			<td style="width: 120px;height: 30px;text-align: center;">变更人员：</td>
			<td style="width: 160px; text-align: left;">${session_user.name}<input id="updateUserId" name="updateUserId"
                    	 type="hidden" value="${session_user.userId}" /></td>
		</tr>
				<tr>
					<td style="height: 30px;text-align: center;" colspan="4"><input type="button" value="提  交" 
					class="inputButtonCtrl" style="width:70px;" 
					onclick="deliverInfo('hidden');"/>&nbsp;&nbsp;&nbsp;<input type="button" 
					value="取  消" class="inputButtonCtrl" style="width:70px;" onclick="deliverInfo('hidden');"/></td>
				</tr>
			</table>
		</div>
	</form>
	</body>
</html>
