<%@ page contentType="text/html; charset=gb2312" %>
<%@ include file="/common/taglibs.jsp" %>
<c:import url="/common/popSelect.jsp" />

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>流程处理DIV</title>
    
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
	                //deliverMan是本div上的发送人员选择框，所有要显示
	                document.getElementById('deliverMan').style.visibility = "visible";   
	            }
				document.getElementById('deliverdiv').style.display = 'block';
				document.getElementById('deliverdiv').focus();
	               
	         } else {
	            if (selectArray && selectArray.length > 0) {
	                for (var i = 0;i < selectArray.length;i++){
	                    selectArray[i].style.visibility = "visible";
	                }
	            }
	            //点击取消按钮后，恢复div中的初始值
	            document.getElementById('deliverMan').style.visibility = "hidden";   
				document.getElementById('deliverdiv').style.display = 'none';
	        }
			//document.getElementById('changediv').style.display = 'none';
		}
    	
    	function flowSubmit() {
    		document.formDeliver.submit();
    	}
    </script>
	<style type="text/css">
		.flowdiv
		{
			position:absolute; display:none; width:460px; 
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
	<form action="<c:url value="/flowAction.do?method=save"/>" name="formDeliver">
		<!-- 隐含变量，由本页面的包含页面赋值。开始 -->
			<input id="objectType" name="objectType" type="hidden">
			<input id="fileType" name="fileType" type="hidden">
			<input id="objectId" name="objectId" type="hidden">
			<input id="beforeState" name="beforeState" type="hidden">
		<!-- 隐含变量结束 -->
		<div id="deliverdiv"  class="flowdiv" style="">
			<table style="width: 100%; height: 100%; align:center;" class="flowtable">
				<tr>
					<td style="height: 40px;text-align: left;text-align: center;color: #DC1214;" 
					colspan="4">流程处理</td>
				</tr>
				<tr>
					<td style="width: 80px;text-align: left;">&nbsp;处理类别：</td>
					<td style="width: 150px;text-align: left;"><input name="a" type="radio" value="1" checked/>审核(选单人)</td>
					<td style="width: 80px;height: 30px;text-align: left;">&nbsp;处理人员：</td>
					<td style="width: 150px;text-align: left;" rowspan="3"><a href="#" onClick="switchWin(91, 'deliverMan')"><img src="<c:url value="/images/+.gif"/>" width="12" height="12" 
			                    border="0" /></a>&nbsp;<a href="#" onClick="removeDeliverMan()"><img 
			                    src="<c:url value="/images/-.gif"/>" width="12" height="12" border="0" /></a><br/><select id="deliverMan" 
			                    name="deliverMan" class="select" multiple="multiple" 
			                    style="width: 120px;height :63px;"></select></td>
				</tr>
				<tr>
					<td></td>
					<td style="height: 30px;text-align: left;"><input name="a" type="radio" value="2" />会签评审</td>
					<td></td>
				</tr>
				<tr>
					<td></td>
					<td style="height: 30px;text-align: left;"><input name="a" type="radio" value="3" />报批</td>
					<td></td>
				</tr>
				<tr>
					<td style="height: 30px;text-align: left;">&nbsp;附件：</td>
					<td colspan="3" style="text-align: left;"><input id="file" name="file" type="file" 
		                	class="inputText" onChange="javascript:fillname();" size="40"></td>
				</tr>
				<tr>
					<td style="height: 30px;text-align: left;">&nbsp;审阅结果：</td>
					<td colspan="3" style="text-align: left;"><input name="approve" type="radio" value="1" 
					checked/>批准&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input name="approve" type="radio" 
					value="1"/>不批准
					</td>
				</tr>
				<tr>
					<td style="height: 30px;text-align: left;">&nbsp;审阅意见：</td>
					<td colspan="3" style="text-align: left;"><textarea id="note" name="note" 
		                style="width: 360px; height:40px"></textarea></td>
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
