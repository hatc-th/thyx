<%@ page contentType="text/html; charset=gb2312" %>
<%@ include file="/common/taglibs.jsp" %>
<c:import url="/common/popSelect.jsp" />

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>���̴���DIV</title>
    
    <script type="text/javascript">
    //ѡ������飬DIV���ܸ���ѡ��������Ҫ����ҳ���ϵ�ѡ���
	var selectArray = new Array(); 
	
    	function deliverInfo(disp) {
			//���ð���ҳ��setParameter()�����������ز���
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
	                //deliverMan�Ǳ�div�ϵķ�����Աѡ�������Ҫ��ʾ
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
	            //���ȡ����ť�󣬻ָ�div�еĳ�ʼֵ
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
		<!-- �����������ɱ�ҳ��İ���ҳ�渳ֵ����ʼ -->
			<input id="objectType" name="objectType" type="hidden">
			<input id="fileType" name="fileType" type="hidden">
			<input id="objectId" name="objectId" type="hidden">
			<input id="beforeState" name="beforeState" type="hidden">
		<!-- ������������ -->
		<div id="deliverdiv"  class="flowdiv" style="">
			<table style="width: 100%; height: 100%; align:center;" class="flowtable">
				<tr>
					<td style="height: 40px;text-align: left;text-align: center;color: #DC1214;" 
					colspan="4">���̴���</td>
				</tr>
				<tr>
					<td style="width: 80px;text-align: left;">&nbsp;�������</td>
					<td style="width: 150px;text-align: left;"><input name="a" type="radio" value="1" checked/>���(ѡ����)</td>
					<td style="width: 80px;height: 30px;text-align: left;">&nbsp;������Ա��</td>
					<td style="width: 150px;text-align: left;" rowspan="3"><a href="#" onClick="switchWin(91, 'deliverMan')"><img src="<c:url value="/images/+.gif"/>" width="12" height="12" 
			                    border="0" /></a>&nbsp;<a href="#" onClick="removeDeliverMan()"><img 
			                    src="<c:url value="/images/-.gif"/>" width="12" height="12" border="0" /></a><br/><select id="deliverMan" 
			                    name="deliverMan" class="select" multiple="multiple" 
			                    style="width: 120px;height :63px;"></select></td>
				</tr>
				<tr>
					<td></td>
					<td style="height: 30px;text-align: left;"><input name="a" type="radio" value="2" />��ǩ����</td>
					<td></td>
				</tr>
				<tr>
					<td></td>
					<td style="height: 30px;text-align: left;"><input name="a" type="radio" value="3" />����</td>
					<td></td>
				</tr>
				<tr>
					<td style="height: 30px;text-align: left;">&nbsp;������</td>
					<td colspan="3" style="text-align: left;"><input id="file" name="file" type="file" 
		                	class="inputText" onChange="javascript:fillname();" size="40"></td>
				</tr>
				<tr>
					<td style="height: 30px;text-align: left;">&nbsp;���Ľ����</td>
					<td colspan="3" style="text-align: left;"><input name="approve" type="radio" value="1" 
					checked/>��׼&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input name="approve" type="radio" 
					value="1"/>����׼
					</td>
				</tr>
				<tr>
					<td style="height: 30px;text-align: left;">&nbsp;���������</td>
					<td colspan="3" style="text-align: left;"><textarea id="note" name="note" 
		                style="width: 360px; height:40px"></textarea></td>
				</tr>
				<tr>
					<td style="height: 30px;text-align: center;" colspan="4"><input type="button" value="��  ��" 
					class="inputButtonCtrl" style="width:70px;" 
					onclick="deliverInfo('hidden');"/>&nbsp;&nbsp;&nbsp;<input type="button" 
					value="ȡ  ��" class="inputButtonCtrl" style="width:70px;" onclick="deliverInfo('hidden');"/></td>
				</tr>
			</table>
		</div>
	</form>
	</body>
</html>
