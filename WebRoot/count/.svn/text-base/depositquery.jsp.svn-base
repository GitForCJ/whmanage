<%@ page contentType="text/html; charset=gb2312" language="java"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<jsp:useBean id="depositQuery" scope="session" class="com.commsoft.epay.pc.count.bean.DepositQuery" />
<jsp:useBean id="userSession" scope="session" class="com.commsoft.epay.pc.rights.form.UserForm" />
<%
	request.setAttribute("fundList",depositQuery.getFundAcct(userSession));
	request.setAttribute("childList",depositQuery.getChildFacctQuery(userSession));
	request.setAttribute("ruleMap",depositQuery.getFundRule(userSession));
 %>
<html:html>
<head>
<title></title>
<link REL="stylesheet"  TYPE="text/css"  HREF="../css/common.css">
<script type="text/javascript" src="../js/calendar.js"></script>
<script type="text/javascript" src="../js/util.js"></script>
<script type="text/javascript" src="../js/color_change.js"></script>
</head>
  
<body topmargin="0" leftmargin="0" rightmargin="0">
	<table width="100%" border="0" cellspacing="0" cellpadding="0"  class="menu_table">
		<tr> 
			<td width="129"><img src="../images/lgo_0hm_129x36.on.gif" width="129" height="33"></td>
			<td width="125" background="../images/menu0.on.bg.gif" nowrap> <div align="center" ><a href="dipositquery.jsp" class="fontmenu">押金信息</a></div></td>
			<td width="11"><img src="../images/menu0.on.end.gif" width="11" height="33"></td>
			<td class="menubg">&nbsp;</td>
		</tr>
		<tr> 
			<td height="10" colspan="4" background="../images/menu.bg.gif"><div align="center"></div></td>
		</tr>
	</table>
	<br>
	<table width="60%" border="0" align="center" cellpadding="0" cellspacing="1" class="tableoutline">
	    <tr class="trlist">  
	      <td align="center" colspan="3"  class="fontlisttitle">&nbsp;账户信息&nbsp;</td>
	    </tr>
		<tr>
			<td  colspan="3" >
				<logic:iterate id="fundinfo" name="fundList" >
					<table width="100%" border="0" align="center" class="tablecontent">
					
			          <tr> 
						<td width="40%">资金账号</td>
			            <td width="60%"><input name="account" type="text" class="inputtext" size="20" value="${fundinfo[0] }" style="background-color: cccccc;"  readonly></td>
			          </tr>
			          <tr> 
						<td>客户姓名</td>
			            <td><input name="name" type="text" class="inputtext" size="20" value="${fundinfo[1] }" style="background-color: cccccc;"  readonly></td>
			          </tr>
			          <tr> 
						<td>地市</td>
			            <td><input name="name" type="text" class="inputtext" size="20" value="${fundinfo[2] }" style="background-color: cccccc;"  readonly></td>
			          </tr>
			          <tr> 
						<td>开户时间</td>
			            <td><input name="name" type="text" class="inputtext" size="20" value="${fundinfo[3] }" style="background-color: cccccc;"  readonly></td>
			          </tr>
			          <tr> 
						<td></td>
			            <td><input name="accountleft" type="hidden" class="inputtext" size="20" value="${fundinfo[4] }" style="background-color: cccccc;"  readonly></td>
			          </tr>
			          <tr> 
						<td></td>
			            <td><input name="accounttotal" type="hidden" class="inputtext" size="20"  value="${fundinfo[5] }" style="background-color: cccccc;"  readonly></td>
			          </tr>
			          <tr> 
						<td>账户状态</td>
			            <td><input name="state" type="text" class="inputtext" size="20" value="${fundinfo[6] }" style="background-color: cccccc;"  readonly></td>
			          </tr>
			        </table>
		  		</logic:iterate>
		 	</td>
		 </tr>
		 <logic:iterate id="childinfo" name="childList" >
			<tr class="trlist">  
			    <td align="center" colspan="3"  class="fontlisttitle">&nbsp;${childinfo[0] }信息&nbsp;
			    	<input name="childfacct" type="hidden" value="${childinfo[1] }">
			    </td>
		    </tr>
			<tr>
				<td  colspan="3" >
					<table width="100%" border="0" align="center" class="tablecontent">
			          <tr> 
						<td width="40%">账户余额(元)</td>
			            <td width="60%"><input name="usableleft" type="text" class="inputtext" size="20" value="${childinfo[2] }" style="background-color: cccccc;"  readonly="true"></td>
			          </tr>
			        </table>
		 		</td>
		 	</tr>
		 </logic:iterate>
	     <logic:iterate id="rule" name="ruleMap" >
	   		<bean:define id="key" name="rule" property="key"></bean:define>
			<bean:define id="list" name="rule" property="value"></bean:define>
	   		<tr class="trlist">  
			    <td align="center" colspan="3" class="fontlisttitle">&nbsp;${key }使用规则&nbsp;</td>
    		</tr>
    		<tr class="tr1">
		   		 <td nowrap width="33%">规则名称</td>
		         <td nowrap width="33%">系统值</td>
		         <td nowrap width="33%">个人值</td>
			</tr>
	   		<logic:iterate id="row" name="list">
	   		<tr>
				<td  colspan="3" >
					<table width="100%" border="0" align="left" class="tablecontent">
				         <tr align="left" class="tr1" onmouseover="overRow(this)" onmouseout="outRow(this)" onclick="clickRow(this)">
					     <logic:iterate id="col" name="row">
					    	<td nowrap width="33%">${col}</td>
					     </logic:iterate>
					  	 </tr>
			        </table>
		 		</td>
		 	</tr>
	  		</logic:iterate>
		  </logic:iterate>
	</table>
	<br>
		  <br>
		  <br>
</body>
</html:html>
