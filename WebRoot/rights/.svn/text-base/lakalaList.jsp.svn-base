<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<jsp:useBean id="userSession" scope="session" class="com.wlt.webm.rights.form.SysUserForm" />
<%
  String path = request.getContextPath();
  if(null==userSession||userSession.getUsername()==null){
      response.sendRedirect(path+"/rights/wltlogin.jsp");
	}
%>
<html>
<head>
<title>万汇通</title>
<link href="../css/main_style.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="../js/util.js"></script>
<SCRIPT type=text/javascript>

function funCss(className)
{
	className.style.boxShadow="0 0 10px #ccc";
}
function funClearCss(className)
{
	className.style.boxShadow="";
}

function funPage(index)
{
	document.getElementById("form1").action="<%=path %>/AccountInfo/lakala.do?method=AccountInfoList&index="+index;
	document.getElementById("form1").submit();
	return;
}
function funPageA(index)
{
	if(index.Trim().length<=0)
	{
		return ;
	}
	if(isDigit(index)==false)
	{
		index=1;
	}
	funPage(index);
}
// 去空格
String.prototype.Trim = function() 
{ 
	return this.replace(/(^\s*)|(\s*$)/g, ""); 
} 
function checksubmit()
{
	document.getElementById("userlogin").value=document.getElementById("userloginA").value;
	document.getElementById("userno").value=document.getElementById("usernoA").value;
	document.getElementById("form1").submit();
	return;
}
</SCRIPT>
</head>
<body rightmargin="0" leftmargin="0"  topmargin="0" class="body_no_bg">
<form id="form1" name="for" action="<%=path %>/AccountInfo/lakala.do?method=AccountInfoList" method="post">
<input type="text" value="${userlogin}" id="userlogin" name="userlogin" style="display:none"/>
<input type="text" value="${userno}" id="userno" name="userno" style="display:none"/>
<div class="header">
	<div class="topCtiy clear">
	<nobr>
		<li>&nbsp;</li>
		<li>
			<input type="button" name="Button" value="查询" class="field-item_button" onClick="checksubmit()">
		</li>
		</nobr>
	</div>
</div>
<div style="border:1px solid #cccccc;height:40px;margin-bottom:10px;width:98%">
	<table width="100%" border="0" cellspacing="0" cellpadding="0" style="height:30px;margin-top:5px;">
	  <tr>
	   <td>&nbsp;</td>
	    <td style="width:25%;font-size:13px;">登陆账号:&nbsp;<input name="userloginA" value="${userlogin }" id="userloginA" onblur="funClearCss(this)" onfocus="funCss(this)" style="width:150px;height:25px;font-size:18px;"  type="text"/></td>
	    <td style="width:25%;font-size:13px;">系统编号:&nbsp;<input name="usernoA" value="${userno }" id="usernoA" onblur="funClearCss(this)" onfocus="funCss(this)" style="width:150px;height:25px;font-size:18px;"  type="text"/></td>
	    <td>&nbsp;</td>
	  </tr>
	</table>
</div>
<table border=0  cellspacing="0" cellpadding="0" class="tab_line" style="width:98%">
<thead>
<tr height="30" >
<th >拉卡拉终端编号</th>
<th >万汇通系统编号</th>
<th >万汇通资金账号</th>
<th >万汇通登录账号</th>
<th >绑定日期</th>
</tr>
</thead>
<tbody>
<c:forEach items="${arrList}" var="li" varStatus="index">
<c:if test="${index.index%2==0}">
	<tr  height="30"  style="background:#FDEBFE">
</c:if>
<c:if test="${index.index%2!=0}">
	<tr  height="30" >
</c:if>
<td class="td_line" style="font-size:14px;">${li[0]}</td>
<td class="td_line" style="font-size:14px;">${li[1]}</td>
<td class="td_line" style="font-size:14px;">${li[2]}</td>
<td class="td_line" style="font-size:14px;">${li[3]}</td>
<td class="td_line" style="font-size:14px;">
	${fn:substring(li[4], 0, 4)}/${fn:substring(li[4], 4, 6)}/${fn:substring(li[4], 6, 8)}&nbsp;${fn:substring(li[4], 8,10)}:${fn:substring(li[4], 10,12)}:${fn:substring(li[4], 12,14)}
</td>
</tr>
</c:forEach>
</tbody>
<tfoot>
<tr height="30" style="background:#FDEBFE">
<td colspan="9" class="tr_line_top">
<c:if test="${index!=null}">
<div style="text-align:right;font-size:14px;">
	<a href="javascript:funPage(1)">首页</a>
	<a href="javascript:funPage(${index-1})">上一页</a>
	<a href="javascript:funPage(${index+1})">下一页</a>
	<a href="javascript:funPage(${lastIndex})">尾页</a>&nbsp;
	跳转&nbsp;<input id="pageId" type="text" style="width:30px" value="${index}" onblur="funPageA(this.value)" onkeyup="value=value.replace(/[^\d]/g,'') "/>
</div>
</c:if>
</td>

</tr>
</tfoot>
</table>
</form>
</body>
</html>