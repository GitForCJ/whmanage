<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@ taglib uri="/WEB-INF/wlt-tags.tld" prefix="wlt" %>
<%@ page import="java.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<jsp:useBean id="userSession" scope="session" class="com.wlt.webm.rights.form.SysUserForm" />
<jsp:useBean id="user" scope="page" class="com.wlt.webm.rights.bean.SysUser"/>
<jsp:useBean id="role" scope="page" class="com.wlt.webm.rights.bean.SysRole"/>
<%
  String path = request.getContextPath();
  if(null==userSession||userSession.getUsername()==null){
      response.sendRedirect(path+"/rights/wltlogin.jsp");
	} 
%>
<html:html>
<head>
<title>万汇通</title>
<link href="../css/main_style.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href=../css/common.css>
<style type="text/css">
.tabs_wzdh { width: 100px; float:left;}
.tabs_wzdh li { height:35px; line-height:35px; margin-top:5px; padding-left:20px; font-size:14px; font-weight:bold;}
/*tree_nav*/
.tree_nav { border:1px solid #CCC; background:#f5f5f5; padding:10px; margin-top:20px;}
.field-item_button_m2 { margin:0 auto; text-align:center;}
.field-item_button2 { border:0px;width:136px; height:32px; color:#fff; background:url(../images/button_out2.png) no-repeat; cursor:pointer;margin-right:20px;}
.field-item_button2:hover { border:0px; width:136px; height:32px; color:#fff; background:url(../images/button_on2.png) no-repeat; margin-right:20px;}

 .odd{ background-color:#A3A3A3;}
 .even{background-color:#A3A3A3;}
</style>
<script type="text/javascript" src="../js/util.js"></script>
<SCRIPT src="../js/more_tiaojian.js" type=text/javascript></SCRIPT>
<script language='javascript' src='../js/jquery.js'></script>
<link href="../css/selectCss.css" rel="stylesheet" type="text/css" /> 
<script src="../js/selectCss.js" type="text/javascript"></script> 
<script language="javascript" type="text/javascript" src="../My97DatePicker/WdatePicker.js"></script>
<SCRIPT type=text/javascript>
javascript:window.history.forward(1); 
jQuery(function(){
	//$("#changeCity").click(function(a){$("#allCity").slideDown(0);a.stopPropagation();$(this).blur()});$("#allCity").click(function(a){a.stopPropagation()});$(document).click(function(a){a.button!=2&&$("#allCity").slideUp(0)});$("#foldin").click(function(){$("#allCity").slideUp(0)})
 $("#myTable tr:odd").addClass("odd");//odd为偶数行
 // $("#myTable tr:even").addClass("even")//even为偶数行
});

$(function(){
	if("${flagA}"!=null && "${flagA}"!="null" && "${flagA}"!="")
	{
		if("${flagA}"==-3)
		{
			$("#money").val("${money}");
			document.getElementById("${radioid}").checked="checked";
			alert("一分钟内禁止重复操作!");
			//window.location.href="<%=path%>/AccountInfo/TransferAccounts.do?method=showTransferAccounts";
			return ;
		}
		else
		{
			if("${flagA}"==-2)
			{
				$("#money").val("${money}");
				document.getElementById("${radioid}").checked="checked";
				alert("交易密码错误!");
				//window.location.href="<%=path%>/AccountInfo/TransferAccounts.do?method=showTransferAccounts";
				return ;
			}
			else
			{
				if("${flagA}"==0)
				{
					alert("操作成功!");
					window.location.href="<%=path%>/AccountInfo/TransferAccounts.do?method=showTransferAccounts";
				}
				else
				{
					if("${flagA}"==-4)
					{
						alert("系统禁止当前用户交易!");
						window.location.href="<%=path%>/AccountInfo/TransferAccounts.do?method=showTransferAccounts";
						return ;
					}
					else
					{
						alert("操作失败!");
						window.location.href="<%=path%>/AccountInfo/TransferAccounts.do?method=showTransferAccounts";
					}
				}
			}
		}
	}  
});
//详细
function FunWindowShow(id)
{
	 var obj = new Object();
	 obj.id=id;
	 obj.role=-1;
	 window.showModalDialog("AccountDetails.jsp",obj,"dialogWidth=450px;dialogHeight=550px");
}

function funCheck()
{
var flag=false;
	var arr=document.getElementsByName("radio");
	for(var i=0;i<arr.length;i++)
	{
		if(arr[i].checked)
		{
			flag=true;
			break;
		}
	}
	
	if(!flag)
	{
		alert("请选择账号!");
		return false;
	}
	if($("#money").val().Trim().length<=0)
	{
		alert("请输入转款金额!");
		return false;
	}
	if($("#money").val().Trim()<=0)
	{
		alert("请输入正确的转款金额!");
		return false;
	}
	if(!isInt($("#money").val().Trim()))
	{
		if(!isFloat($("#money").val().Trim()))
		{
			alert("请输入正确的转款金额!");
			return false;
		}
	}
	if($("#money").val().Trim()<1)
	{
		alert("转款金额必须大于一元！");
		return false;
	}

	if($("#password").val().Trim().length<=0)
	{
		alert("请输入交易密码!");
		return false;
	}
	/*
	if(!isAlnum($("#password").val().Trim()))
	{
		alert("只能有26个大小写英文字符和数字组成");
		return false;
	}
	*/
	document.getElementById("zk").style.backgroundColor="#cccccc";
	document.getElementById("zk").style.disabled="disabled";
	document.getElementById("zk").value="转款中";
	return true;
}


// 去空格
String.prototype.Trim = function() 
{ 
	return this.replace(/(^\s*)|(\s*$)/g, ""); 
} 
</SCRIPT>
</head>
<body rightmargin="0" leftmargin="0"  topmargin="0" class="body_no_bg">
<div id="popdiv"><span id="sellist"></span><div style="clear:both"></div></div>
<div id="tabbox">
  <div id="tabs_title">
    <ul class="tabs_wzdh">
      <li>转账</li></ul>
  </div>
</div>
<form id="form1" action="<%=path%>/AccountInfo/TransferAccounts.do?method=saveTransferAccounts" method="post">

<table id="myTable" cellspacing="0" cellpadding="0" class="tab_line" style="width:97%;text-align:left;">
<thead>
<tr height="25" class="tr_line_bottom">
<th>详细信息</th>
<th>开户银行/卡类型</th>
<th>提交日期</th>
<th>状态</th>
<th>权限</th>
<th>请选择</th>
</tr>
</thead>
<tbody id="tab">
<c:forEach items="${arrList}" var="li" >
<tr>
<td class="td_line">
<div style="text-align:left;width:200px;">
<a style="text-decoration:underline;" href="javascript:FunWindowShow(${li[8]})">账户:${li[2]}</br>(${li[1]})${li[3]}:${li[4]}</a>
</div>
</td>
<td class="td_line">${li[9]}</br>${li[5]}</td>
<td class="td_line">${li[6]}</td>
<td class="td_line" >
	<c:if test="${li[7]==0}">
		未验证
	</c:if>
	<c:if test="${li[7]==1}">
		已验证
	</c:if>
</td>
<td  class="td_line" >
	<c:if test="${li[10]==0}">
		正常
	</c:if>
	<c:if test="${li[10]==-1}">
		已禁用
	</c:if>
</td>
<td  class="td_line" >
	<c:if test="${li[10]==-1}">
		<input type="radio" value="${li[8]}" id="${li[8]}" disabled/>
	</c:if>
	<c:if test="${li[10]==0}">
		<c:if test="${li[7]==0}">
			<input type="radio" value="${li[8]}" id="${li[8]}" disabled/>
		</c:if>
		<c:if test="${li[7]==1}">
			<input type="radio" value="${li[8]}"  id="${li[8]}" name="radio"/>
		</c:if>
	</c:if>
</td>
</tr>
</c:forEach>
</table>
<br/>
<div style="text-align:center;font-size: 18px;color:#333333;" />
<span style="height: 30px;vertical-align: middle;">转款金额:</span>
<input style="font-size: 20px;height: 30px;width:180px;color: blue;" id="money" name="order_price" type="text" onkeyup="if(isNaN(value))execCommand('undo')" onafterpaste="if(isNaN(value))execCommand('undo')" onkeydown="keydownEvent()"/>元
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<span style="height: 30px;vertical-align: middle;">交易密码:</span>
<input style="font-size: 20px;height: 30px;width:180px;color: blue;" id="password" name="password" type="password"/>&nbsp;
<input type="submit" id="zk" value="转款" name="OK" onclick="return funCheck();" style="margin-left: 20px;width: 80px;height: 30px;background-color: #1980c3;border: 2;font-size: 16px;color: white;"/>
</div>
</form>
<br/>
<br/>
<div style="padding-left:40px;padding-top:10px;margin-left: 20px;font-size: 13px; color: #FF6600;font-weight:bold;border:1px solid #FF9E4d;background-color: #FFFFE5;">
<ul>
<li style="list-style:none;">1、支持通过银行卡或者存折购买额度。</li>
<li style="list-style:none;">&nbsp;</li>
<li style="list-style:none;">2、交易金额低于2000元/笔扣1元手续费，大于等于2000元/笔免费。</li>
<li style="list-style:none;">&nbsp;</li>
<li style="list-style:none;">3、额度实时到账。</li>
<li style="list-style:none;">&nbsp;</li>
</ul>
</div>
</body>
</html:html>