<%@ page contentType="text/html; charset=gb2312" language="java"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<jsp:directive.page import="com.commsoft.epay.pc.common.Common"/>
<jsp:directive.page import="com.commsoft.epay.pc.util.Tools"/>
<%
request.setAttribute("serviceList", Common.getServiceList());
String yesterday = Tools.getPreviousDate();
request.setAttribute("endday",yesterday);
request.setAttribute("firstday",yesterday.substring(0,8)+"01");
request.setAttribute("nowday", Tools.getNow2());
request.setAttribute("stateList", Common.getTradeStateList());
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
			if(startDate.value.trim().length==0){
				alert("开始日期不能为空");
				startDate.focus();
				return false;
			}
			if(!isDate(startDate.value.trim())){
				alert("起始日期格式错误");
				startDate.focus();
				return false;
			}
			if(endDate.value.trim().length==0){
				alert("结束日期不能为空");
				endDate.focus();
				return false;
			}
			if(!isDate(endDate.value.trim())){
				alert("起始日期格式错误");
				endDate.focus();
				return false;
			}
			if(endDate.value>=nowDate.value){
				alert("查询历史数据的结束日期必须小于"+nowDate.value);
				endDate.focus();
				return false;
			}
			
			if(startDate.value>endDate.value){
				alert("开始日期不能大于结束日期");
				endDate.focus();
				return false;
			}
			
			if(startDate.value.substring(0,7) !=  endDate.value.substring(0,7)){
				alert("年份必须相等,开始月份和结束月份必须相等");
				endDate.focus();
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
			<td width="125" background="../images/menu0.on.bg.gif" nowrap> <div align="center" ><a href="tradehiscount.jsp" class="fontmenu">历史交易统计</a></div></td>
			<td width="11"><img src="../images/menu0.on.end.gif" width="11" height="33"></td>
			<td class="menubg">&nbsp;</td>
		</tr>
		<tr> 
			<td height="10" colspan="4" background="../images/menu.bg.gif"><div align="center"></div></td>
		</tr>
	</table>
	
<form method="post" action="../common/loadcount.jsp">
	<input type="hidden" name="url" value="../common/count.do?beanName=com.commsoft.epay.pc.count.bean.TradeHisCount">
	<input type="hidden" name="nowDate" value="${nowday }">
	<table width="80%" border="0" align="center" cellpadding="0" cellspacing="1" class="tableoutline">
	    <tr class="trcontent">  
	      	<td colspan="4" align="center" class="fontcontitle">&nbsp;历史交易统计&nbsp;</td>
	    </tr>
	    <tr>
	    	<td>
	    		<table width="100%" border="0" align="center" class="tablecontent">
	    			<tr>
		            	<td>业务类型</td>
		            	<td width="50%">
		            	   <select name="service" class="selectlist">
				               <option value="">所有业务类型</option>
				               <logic:iterate id="services" name="serviceList">
				               <option value="${services[0]}">${services[1]}</option>
				               </logic:iterate>
			               </select>
			            </td>
		          	</tr>
		          	<tr>
		          	<td>交易状态</td>
		            	<td width="50%">
		            	   <select name="state" class="selectlist">
				               <option value="">所有状态</option>
				               <logic:iterate id="stateinfo" name="stateList">
				               <option value="${stateinfo[0]}">${stateinfo[1]}</option>
				               </logic:iterate>
			               </select>
			            </td>
		          	</tr>
		          	<tr>
			            <td>起始日期(交易时间 例如:2008-04-01)</td>
			            <td><input name="startDate" type="text"  size="10"   class="inputtext" value="${firstday }"  onClick="ShowDate.fPopCalendar('startDate','startDate',event);"></td>
			        </tr>
			        <tr>
			            <td> <div align="left">终止日期(交易时间 例如:2008-05-01)</div></td>
			            <td><input name="endDate" type="text"  size="10"   class="inputtext" value="${endday }"  onClick="ShowDate.fPopCalendar('endDate','endDate',event);"></td>
			        </tr>
	    		</table>
	    	</td>
	    </tr>
 		</table>
	<p align="center"> 
		<html:hidden property="operation" value="0"/>
		<input type="button" name="button1" value="查询" class="onbutton" onclick="check()">
		<input type="reset" name="submit2" value="重写" class="onbutton">
	</p>
</form>
</body>
</html:html>
