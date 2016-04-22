<%@ page contentType="text/html; charset=GBK" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<html>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<link REL="stylesheet"  TYPE="text/css"  HREF="../css/common.css">
<script type="text/javascript">
function toBack()
{
  if (document.referrer.indexOf("/common/loadcount.jsp") != -1) {
    history.go(-2);
  } else {
    history.go(-1);
  }
}
</script>
</head>

<body>
<table width="100%" height="20%"><tr><td></td></tr></table>

<table width="100%" height="30%" cellpadding="0" cellspacing="1" align="center" class="tableoutline">
  <tr>
    <td align="center" class="fontcontitle">错误信息</td>
  </tr>
  <tr>
    <td align="center" class="tablecontent">${message}</td>
  </tr>
</table>

<table width="100%" height="60">
  <tr>
    <td align="center"><input type="button" name="backButton" value="返回" class="onbutton" onclick="toBack()"></td>
  </tr>
</table>

</body>
</html>
