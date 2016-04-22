<%@ page contentType="text/html; charset=GBK" language="java"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<jsp:useBean id="affichequery" scope="page" class="com.commsoft.epay.pc.count.bean.AfficheQuery"/>
<jsp:useBean id="userSession" scope="session" class="com.commsoft.epay.pc.rights.form.UserForm" />

<html>
<head>
<title>公告信息</title>
<style type="text/css">
<!--
.style1 {font-size: x-large}
-->
</style>
<META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=GBK">
<link REL="stylesheet"  TYPE="text/css"  HREF="../css/common.css">
<script type="text/javascript" src="../js/util.js"></script>
<script type="text/javascript" src="../js/xml_cascade.js"></script>
<script language="JavaScript">

</script>
</head>
<%
request.setAttribute("affichequeryList",affichequery.getBodyData(userSession));

 %>
<body rightmargin="0" leftmargin="0" topmargin="0">
<html:form  method="post" action="/rights/user.do?method=login" >
<table width="90%" border="0" cellspacing="0" cellpadding="0" align="center">

  <tr> 
    <td height="30" align="center"  ><div align="center"></div>公告</td>
  </tr>
   <tr>
      <td>
			<logic:iterate id="afficheInfo" name="affichequeryList">
				${afficheInfo[0] }
			</logic:iterate>
			
	     </td>
	</tr>
</table>
<br>
   <div align="center"><a href="javascript:void(0);window.close();">关闭</a></div>
 </html:form>
</body>
</html>
