<%@ page contentType="text/html; charset=gb2312" language="java"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ page import="com.commsoft.epay.pc.util.*" %>
<%
String nowdate = Tools.getNow2();
request.setAttribute("nowdate",nowdate);
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
	            if(startDate.value.substring(0,7) !=  endDate.value.substring(0,7)){
					alert("年份必须相等,开始月份和结束月份必须相等");
					endDate.focus();
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
			<td width="125" background="../images/menu0.on.bg.gif" nowrap> <div align="center" ><a href="affichequery.jsp" class="fontmenu">公告信息查询</a></div></td>
			<td width="11"><img src="../images/menu0.on.end.gif" width="11" height="33"></td>
			<td class="menubg">&nbsp;</td>
		</tr>
		<tr> 
			<td height="10" colspan="4" background="../images/menu.bg.gif"><div align="center"></div></td>
		</tr>
	</table>
	
<form method="post" action="../common/loadcount.jsp">
	<input type="hidden" name="url" value="../common/count.do?beanName=com.commsoft.epay.pc.count.bean.AfficheQuery">
	<input type="hidden" name="pageIndex" value="1">
	<table width="80%" border="0" align="center" cellpadding="0" cellspacing="1" class="tableoutline">
	    <tr class="trcontent">  
	      	<td colspan="4" align="center" class="fontcontitle">&nbsp;公告信息查询&nbsp;</td>
	    </tr>
	    <tr>
	    	<td>
	    		<table width="100%" border="0" align="center" class="tablecontent">
	    			<tr>
						<td width="50%">公告级别</td>
						<td>
							<select name="afficheGradeid">
								<option value="">所有级别</option>
								<option value="1">普通</option>
								<option value="2">紧急</option>
							</select>
						</td>
		  		  	</tr>
		          	<tr>
		            	<td>公告关键字</td>
		            	<td><input type="text" name="keyword" value=""  class="inputtext" size="20"></td>
		          	</tr>
		          	<tr>
			            <td>起始日期(发布日期 例如:2008-04-01)</td>
			            <td><input name="startDate" type="text" value="${nowdate }" size="10"   class="inputtext" onClick="ShowDate.fPopCalendar('startDate','startDate',event);" >
			        </tr>
			        <tr>
			            <td> <div align="left">终止日期(发布日期 例如:2008-05-01)</div></td>
			            <td><input name="endDate" type="text" value="${nowdate }"  size="10"  class="inputtext" onClick="ShowDate.fPopCalendar('endDate','endDate',event);" >
			        </tr>
	    		</table>
	    	</td>
	    </tr>
 		</table>
	<p align="center"> 
		<input type="button" name="button1" value="查询" class="onbutton" onClick="check();">
		<input type="reset" name="submit2" value="重写" class="onbutton">
	</p>
</form>
</body>
</html:html>
