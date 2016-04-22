<%@ page contentType="text/html; charset=gb2312" language="java"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<jsp:directive.page import="com.commsoft.epay.pc.common.Common"/>
<jsp:directive.page import="com.commsoft.epay.pc.util.Tools"/>
<%
String nowday = Tools.getNow2();
request.setAttribute("endday",nowday);
request.setAttribute("firstday",nowday.substring(0,8)+"01");
request.setAttribute("nowday", Tools.getNow2());
 %>
<html:html>
<head>
<title></title>
<link REL="stylesheet"  TYPE="text/css"  HREF="../css/common.css">
<script type="text/javascript" src="../js/calendar.js"></script>
<script type="text/javascript" src="../js/util.js"></script>
<script type="text/javascript">
	function check(){
		with(document.forms[0]){
			if(startdate.value.trim().length==0){
				alert("开始日期不能为空");
				startdate.focus();
				return false;
			}
			if(!isDate(startdate.value.trim())){
				alert("起始日期格式错误");
				startdate.focus();
				return false;
			}
			if(enddate.value.trim().length==0){
				alert("结束日期不能为空");
				enddate.focus();
				return false;
			}
			if(!isDate(enddate.value.trim())){
				alert("起始日期格式错误");
				enddate.focus();
				return false;
			}
			if(startdate.value>enddate.value){
				alert("开始日期不能大于结束日期");
				enddate.focus();
				return false;
			}
			if(startdate.value.substring(0,7) !=  enddate.value.substring(0,7)){
				alert("年份必须相等,开始月份和结束月份必须相等");
				enddate.focus();
				return false;
			}
			target = "_self";
			submit();
		}
	}
</script>
</head>
  
<body topmargin="0" leftmargin="0" rightmargin="0">
	<table width="100%" border="0" cellspacing="0" cellpadding="0"  class="menu_table">
		<tr> 
			<td width="129"><img src="../images/lgo_0hm_129x36.on.gif" width="129" height="33"></td>
			<td width="125" background="../images/menu0.on.bg.gif" nowrap> <div align="center" ><a href="QBtenpaytransfer.jsp" class="fontmenu">Q币交易查询</a></div></td>
			<td width="11"><img src="../images/menu0.on.end.gif" width="11" height="33"></td>
			<td class="menubg">&nbsp;</td>
		</tr>
		<tr> 
			<td height="10" colspan="4" background="../images/menu.bg.gif"><div align="center"></div></td>
		</tr>
	</table>
	
<form method="post" action="../common/loadcount.jsp">
	<input type="hidden" name="url" value="../common/count.do?beanName=com.commsoft.epay.pc.count.bean.QbtenpaytransferQuery">
	<input type="hidden" name="dbName" value="SCP">
	<input type="hidden" name="pageIndex" value="1">
	<table width="80%" border="0" align="center" cellpadding="0" cellspacing="1" class="tableoutline">
	    <tr class="trcontent">  
	      	<td colspan="4" align="center" class="fontcontitle">&nbsp;Q币交易查询&nbsp;</td>
	    </tr>
	    <tr>
	    	<td>
	    		<table width="100%" border="0" align="center" class="tablecontent">
	    			<tr>
	    			<tr>
		          	<td>资金子账户</td>
		            	<td width="50%">
		            	   <input type="text" name="childfacct" size="15" class="inputdistinct">
			            </td>
		          	</tr>
		          	<tr>
			         	<td>订单号</td>
			         	<td><input type="text" name="tran_seq" class="inputdistinct"></td>
		        	</tr>
		        	<tr>
			         	<td>流水号</td>
			         	<td><input type="text" name="tradeNum" class="inputdistinct"></td>
		        	</tr>
		        	
		        	<tr> 
		            	<td>交易类型</td>
		            	<td>
				            <select name="tradeType" class="selectlist">
				            <option value="" >所有状态</option>
				            <option value="0">Q币充值</option>
				            </select>
			            </td>
		        	</tr>
		        	<tr> 
		            	<td>腾讯状态</td>
		            	<td>
				            <select name="tencentFlag" class="selectlist">
				            <option value="" >所有状态</option>
				            <option value="0">成功</option>
				            <option value="1">失败</option>
				            <option value="2">未响应</option>
				            </select>
			            </td>
		        	</tr>
		        	<tr> 
		            	<td>平台状态</td>
		            	<td>
				            <select name="scpFlag" class="selectlist">
				            <option value="" >所有状态</option>
				            <option value="0">成功</option>
				            <option value="1">失败</option>
				            <option value="2">未响应</option>
				            </select>
			            </td>
		        	</tr>
		          	<tr>
			            <td>起始日期(状态变更时间 例如:2008-04-01)</td>
			            <td><input name="startdate" type="text"  size="10"   class="inputtext" value="${firstday }"  onClick="ShowDate.fPopCalendar('startdate','startdate',event);">
			            	<input name="startTime" type="text"  size="8" class="inputtext" value="00:00:00" >
			            </td>
			        </tr>
			        <tr>
			            <td> <div align="left">终止日期(状态变更时间 例如:2008-05-01)</div></td>
			            <td><input name="enddate" type="text"  size="10"   class="inputtext" value="${endday }"  onClick="ShowDate.fPopCalendar('enddate','enddate',event);">
			            <input name="endTime" type="text"  size="8" class="inputtext" value="23:59:59" value="00:00:00" ></td>
			        </tr>
	    		</table>
	    	</td>
	    </tr>
 		</table>
	<p align="center"> 
		<input type="button" name="button1" value="查询" class="onbutton" onclick="check()">
		<input type="reset" name="submit2" value="重写" class="onbutton">
	</p>
</form>
</body>
</html:html>
