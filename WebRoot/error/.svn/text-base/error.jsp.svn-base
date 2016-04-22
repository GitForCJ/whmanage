<%@ page contentType="text/html; charset=GBK" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<html>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<link REL="stylesheet"  TYPE="text/css"  HREF="../css/common.css">
<script type="text/javascript">
var flag = 0;
function showInfo() {
	if (flag == 0) {
		document.all.detail.innerText = "隐藏详细信息";
		document.all.info.style.display = "inline";
		flag = 1;
	} else {
		document.all.detail.innerText = "显示详细信息";
		document.all.info.style.display = "none";
		flag = 0;
	}
}
function goback(){
    if(document.referrer.indexOf("/common/load.jsp")!=-1 || document.referrer.indexOf("/common/loadcount.jsp")!=-1){
    	history.go(-2);
    }else{
    	history.go(-1);
    }
}
</script>
</head>

<body>
<table width="100%" height="25%"><tr><td></td></tr></table>
<div align="center"><font color="#FF0000" size="+3">${message}&nbsp;&nbsp;&nbsp;&nbsp;<a id="detail" href="javascript: showInfo()">显示详细信息</a></font>
&nbsp;&nbsp;&nbsp;&nbsp;<a  href="javascript: goback()">关闭</a>
</div>
<table id="info" style="display: none;" width="70%"  cellpadding="0" cellspacing="1" align="center" class="tablelist">
<tr class="trcontent"><td  class="fontcontitle" align="center">详细信息
</td></tr>
<tr><td><table width="100%" border="0"  class="tablecontent" align="left" >
<tr><td>
<br>
${errtitle}<br>
<logic:iterate id="err" name="errstacktrace">
${err}<br>
</logic:iterate>
</td></tr>
</table>
</td></tr>
</table>

</body>
</html>
