<%@ page language="java" import="java.util.*" pageEncoding="gbk"%>
<%
String path = request.getContextPath();
%>

<!DOCTYPE HTML>
<html>
  <head>
    
    <title>auto complete</title>
<link rel="stylesheet" href="<%=path %>/ad/test/autoSuggest/css/autosuggest_inquisitor.css" type="text/css" media="screen" charset="utf-8" />
<script type="text/javascript" src="<%=path %>/ad/test/autoSuggest/js/bsn.AutoSuggest_c_2.0.js"></script>
<style type="text/css">
	body {
		font-family: Lucida Grande, Arial, sans-serif;
		font-size: 10px;
		text-align: center;
		margin: 0;
		padding: 0;
	}
	table
	{
		border: 1px;
		background-color: #999;
		font-size: 10px;
	}
	tr
	{
		vertical-align: top;
	}
	th
	{
		text-align: left;
		background-color: #ccc;
	}
	th,
	td
	{
		padding: 2px;
		font-family: Lucida Grande, Arial, sans-serif;
		font-size: 1.2em;
	}
	td
	{
		background-color: #fff;
	}
	a {
		font-weight: bold;
		text-decoration: none;
		color: #f30;
	}
	a:hover {
		color: #fff;
		background-color: #f30; 
	}
	#wrapper {
		width: 600px;
		margin: 10px auto;
		text-align: left;
	}
	#content {
		font-size: 1.2em;
		line-height: 1.8em;
	}
	#content h1 {
		font-size: 1.6em;
		border-bottom: 1px solid #ccc;
		padding: 5px 0 5px 0;
	}
	#content h2 {
		font-size: 1.2em;
		margin-top: 3em;
	}
	label
	{
		font-weight: bold;
	}
</style>
  </head>
  
  <body>
  	<br><br>
    <input type="text" id="testinput" name="testinput" />
    <input type="text" id="rs" />
    <input type="text" id="rs2" /><br><br>
    
    <div align="center">
    <h3>OPTIONS</h3>
    <table>
	<tr>
		<th>Property</th>
		<th>Type</th>
		<th>Default</th>
		<th>Description</th>
	</tr>
	<tr>
		<td><strong>script</strong></td>
		<td>String</td>
		<td>-</td>
		<td><strong>REQUIRED!</strong> The path to the script that returns the results in XML format.</td>
	</tr>
	<tr>
		<td><strong>varname</strong></td>
		<td>String</td>
		<td>"input"</td>
		<td>Name of variable passed to script holding current input.</td>
	</tr>
	<tr>
		<td><strong>minchars</strong></td>
		<td>Integer</td>
		<td>1</td>
		<td>Length of input required before AutoSuggest is triggered.</td>
	</tr>
	<tr>
		<td><strong>className</strong></td>
		<td>String</td>
		<td>"autosuggest"</td>
		<td>Value of the class name attribute added to the generated <code>ul</code>.</td>
	</tr>
	<tr>
		<td><strong>delay</strong></td>
		<td>Integer</td>
		<td>500</td>
		<td>Number of milliseconds before an AutoSuggest AJAX request is fired.</td>
	</tr>
	<tr>
		<td><strong>timeout</strong></td>
		<td>Integer</td>
		<td>2500</td>
		<td>Number of milliseconds before an AutoSuggest list closes itself.</td>
	</tr>
	<tr>
		<td><strong>cache</strong></td>
		<td>Boolean</td>
		<td>true</td>
		<td>Whether or not a results list should be cached during typing.</td>
	</tr>
	<tr>
		<td><strong>offsety</strong></td>
		<td>Integer</td>
		<td>-5</td>
		<td>Vertical pixel offset from the text field.</td>
	</tr>
	<tr>
		<td><strong>shownoresults</strong></td>
		<td>Boolean</td>
		<td>true</td>
		<td>Whether to display a message when no results are returned.</td>
	</tr>
	<tr>
		<td><strong>noresults</strong></td>
		<td>String</td>
		<td>No results!</td>
		<td>No results message.</td>
	</tr>
	<tr>
		<td><strong>callback</strong></td>
		<td>Function</td>
		<td>&nbsp;</td>
		<td>
			A function taking one argument: an object
			<br />
			<br />
			<pre><code>{id:"1", value:"Foobar", info:"Cheshire"}</code></pre>
		</td>
	</tr>
	<tr>
		<td><strong>json</strong></td>
		<td>Boolean</td>
		<td>false</td>
		<td>Whether or not a results are returned in JSON format. If not, script assumes results are in XML.</td>
	</tr>
</table>
    	
    </div>
  </body>
  <script type="text/javascript">
	var options = {
		script:"<%=path%>/adAction.do?op=test&",
		varname:"testinput",
		json:true,
		minchars:2,
		noresults:'没有数据',
		callback: function (obj) { 
		document.getElementById('rs').value = obj.info; 
		document.getElementById('rs2').value = obj.id;
		}
	};
	var as_json = new AutoSuggest('testinput', options);
</script>
</html>
