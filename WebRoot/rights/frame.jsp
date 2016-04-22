<%@ page contentType="text/html; charset=gb2312" language="java"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ page import="java.io.PrintWriter" %>
<html>
<head>
<html:base/>
<title>万汇通管理平台</title>
<META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=gb2312">
<link REL="stylesheet"  TYPE="text/css"  HREF="../css/common.css">
</head>
<frameset  border='0'  frameBorder='0'  frameSpacing='0' marginborder='0'  rows='70,*,40'>
  <frame  marginwidth='0' noresize="noresize"  marginheight='0' scrolling='no' name='top' src='../content/top.jsp'>
  <frameset  border='1' frameBorder='1' scrolling='yes' frameSpacing='2' marginborder='0'  cols='230,*'>
   	<frame  marginwidth='0' marginheight='0'  scrolling='yes' name='left' src='../content/content.jsp'>
  	<frame name='right' src='../rights/welcome.jsp'>
  </frameset>
  <!-- <frame  marginwidth='0' noresize="noresize"  marginheight='0' scrolling='no' name='buttom' src='../rights/buttom.jsp'>  -->
</frameset>
</html>
