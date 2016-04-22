<%@ page contentType="text/html; charset=gb2312" language="java"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<jsp:directive.page import="com.commsoft.epay.pc.common.Common"/>
<%
request.setAttribute("serviceList", Common.getServiceList());
%>
<html:html>
<head>
<title></title>
<link REL="stylesheet"  TYPE="text/css"  HREF="../css/common.css">
<script type="text/javascript" src="../js/calendar.js"></script>
<script type="text/javascript" src="../js/util.js"></script>

<script type="text/javascript">
   function check() {
       with(document.forms[0]){
       
	            if(startyear.value !=  endyear.value){
					alert("年份必须相等");
					endyear.focus();
					return false;
				}
				if(startyear.value == endyear.value && startmonth.value != endmonth.value){
				    alert("月份必须相等");
				    endmonth.focus();
					return false;
				}
			  submit();
       }
      
   }
</script>
</head>
  
<body topmargin="0" leftmargin="0" rightmargin="0">
	<table width="100%" border="0" cellspacing="0" cellpadding="0"  class="menu_table">
		<tr> 
			<td width="129"><img src="../images/lgo_0hm_129x36.on.gif" width="129" height="33"></td>
			<td width="125" background="../images/menu0.on.bg.gif" nowrap> <div align="center" ><a href="commisionquery.jsp" class="fontmenu">佣金信息查询</a></div></td>
			<td width="11"><img src="../images/menu0.on.end.gif" width="11" height="33"></td>
			<td class="menubg">&nbsp;</td>
		</tr>
		<tr> 
			<td height="10" colspan="4" background="../images/menu.bg.gif"><div align="center"></div></td>
		</tr>
	</table>
	
<form method="post" action="../common/loadcount.jsp">
	<input type="hidden" name="url" value="../common/count.do?beanName=com.commsoft.epay.pc.count.bean.CommisionQuery">
	<table width="80%" border="0" align="center" cellpadding="0" cellspacing="1" class="tableoutline">
	    <tr class="trcontent">  
	      	<td colspan="4" align="center" class="fontcontitle">&nbsp;佣金信息查询&nbsp;</td>
	    </tr>
	    <tr>
	    	<td>
	    		<table width="100%" border="0" align="center" class="tablecontent">
	    			<tr>
			            <td width="50%">业务类型</td>
			            <td width="50%"><select name="service" class="selectlist">
			              <option value="">所有业务类型</option>
			              <logic:iterate id="services" name="serviceList">
			              <option value="${services[0]}">${services[1]}</option>
			              </logic:iterate>
			              </select>
			            </td>
			        </tr>
		          	<jsp:include flush="true" page="../common/startmonth_endmonth.jsp"></jsp:include>
	    		</table>
	    	</td>
	    </tr>
 		</table>
	<p align="center"> 
		<input type="button" name="button1" value="查询" class="onbutton" onclick="check();">
		<input type="reset" name="submit2" value="重写" class="onbutton">
	</p>
</form>
</body>
</html:html>
