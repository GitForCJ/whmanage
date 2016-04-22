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
showMessage("${mess}");
jQuery(function(){
	//$("#changeCity").click(function(a){$("#allCity").slideDown(0);a.stopPropagation();$(this).blur()});$("#allCity").click(function(a){a.stopPropagation()});$(document).click(function(a){a.button!=2&&$("#allCity").slideUp(0)});$("#foldin").click(function(){$("#allCity").slideUp(0)})
 $("#myTable tr:odd").addClass("odd");//odd为偶数行
 // $("#myTable tr:even").addClass("even")//even为偶数行
});

function checksubmit()
{
	if(isDate($("#startDate").val().Trim())==false)
	{
		alert("请正确选择开始日期!");
		return false;
	}
	
	if(isDate($("#endDate").val().Trim())==false)
	{
		alert("请正确选择结束日期!");
		return false;
	}
	if(dateDiff($("#endDate").val().Trim(),$("#startDate").val().Trim())<0)
	{
		alert("结束日期必须大于开始日期!");
		return false;
	}
	var arr=document.getElementsByName("status");
	if(arr[0].checked)
	{
		$("#stat").val(arr[0].value);
	}
	else
	{
		$("#stat").val(arr[1].value);
	}
	$("#intime").val($("#startDate").val());
	$("#endTime").val($("#endDate").val());
	document.getElementById("form1").action="<%=path%>/AccountInfo/AccountVerify.do?method=showAccountVerify";
	document.getElementById("form1").submit();
}

// 去空格
String.prototype.Trim = function() 
{ 
	return this.replace(/(^\s*)|(\s*$)/g, ""); 
} 
function funPage(index)
{
	document.getElementById("form1").action="<%=path%>/AccountInfo/AccountVerify.do?method=showAccountVerify&index="+index;
	document.getElementById("form1").submit();
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

function funSend(id)
{
$("#"+id+"_statusFlag").html("发送请求中");
	$.ajax({
		type:"post",
		url:"<%=path%>/AccountInfo/AccountVerify.do?method=sendVerify&id="+id,
		dataType:"json",
		success:function(obj){
			if(obj==-1)
			{
				$("#"+id+"_statusFlag").html("<a href='javascript:funSend("+id+")'  style='text-decoration:underline;'>发送验证</a>");
				alert("更新数据状态失败!");
				return;
			}
			if(obj==0)
			{
				//成功
				$("#"+id+"_statusFlag").html("已验证");
				alert("验证成功!");
				return;
			}
			if(obj==1)
			{
			$("#"+id+"_statusFlag").html("<a href='javascript:funSend("+id+")'  style='text-decoration:underline;'>发送验证</a>");
				alert("发送消息失败,充值失败！");
				return;
			}
			if(obj==2)
			{
				$("#"+id+"_statusFlag").html("<a href='javascript:funSend("+id+")'  style='text-decoration:underline;'>发送验证</a>");
				alert("请求处理中!");
				return ;
			}
			if(obj==3)
			{
			$("#"+id+"_statusFlag").html("<a href='javascript:funSend("+id+")'  style='text-decoration:underline;'>发送验证</a>");
				alert("失败!");
				return;
			}
		}
	}); 
}

//详细
function FunWindowShow(id)
{
	 var obj = new Object();
	 obj.id=id;
	 obj.role=1;
	 window.showModalDialog("AccountDetails.jsp",obj,"dialogWidth=450px;dialogHeight=550px");
}

// 删除
function funDel(id)
{
	if(confirm("你确定删除吗?"))
	{
			$.ajax({
			type:"post",
			url:"<%=path%>/AccountInfo/AccountVerify.do?method=DelAccountVerify&delID="+id,
			dataType:"json",
			success:function(obj){
				if(obj)
				{
					alert("操作成功!");
					document.getElementById("form1").action="<%=path%>/AccountInfo/AccountVerify.do?method=showAccountVerify&index=${index}";
					document.getElementById("form1").submit();
				}
				else
				{
					alert("操作失败!");
					return ;
				}
			}
		}); 
	}
}

function funHidden()
{
	var userLogins=document.getElementById("userLogins").value;
	if(userLogins.Trim()=="")
	{
		alert("请输入登陆账号!");
		return ;
	}
	if(confirm("你确定要禁用或启用 "+userLogins+" 用户吗!"))
	{
		document.getElementById("form1").action="<%=path%>/AccountInfo/AccountVerify.do?method=showAccountVerify&hidden=1";
		document.getElementById("form1").submit();
		return ;
	}
}
window.onload=function()
{
document.getElementById("hiddenTypeId").value="${hiddenType}";
}
</SCRIPT>
</head>
<body rightmargin="0" leftmargin="0"  topmargin="0" class="body_no_bg">
<div id="popdiv"><span id="sellist"></span><div style="clear:both"></div></div>
<div id="tabbox">
  <div id="tabs_title">
    <ul class="tabs_wzdh">
      <li>账户验证</li></ul>
  </div>
</div>
<form id="form1" name="for" action="<%=path%>/AccountInfo/AccountVerify.do?method=showAccountVerify" method="post">
<input type="text" value="${stat}" id="stat" name="stat" style="display:none"/>
<input type="text" value="${intime}" id="intime" name="intime" style="display:none"/>
<input type="text" value="${endTime}" id="endTime" name="endTime" style="display:none"/>
<div class="header">
	<div class="topCtiy clear">
	<nobr>
		<li>&nbsp;</li>
		<li><input type="button" name="Button" value="查询" class="field-item_button" onClick="return checksubmit()"></li>
		<!-- 
		<li><input type="button" name="Button" value="导出" class="field-item_button" onClick="excelExport();"></li>
		 -->
		</nobr>
	</div>
</div>
<div class="selCity" id="allCity" style="DISPLAY: block;">
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
	  <tr>
	  	<td>
	  		<input type="radio" style="height:10px;" name="status" value="1" ${stat==1?'checked':''} checked/>已验证 &nbsp;&nbsp;
	  		<input type="radio" style="height:10px;" name="status" value="0" ${stat==0?'checked':''}/>未验证
	  	</td>
	    <td>开始日期：<input class="Wdate" id="startDate"  type="text" onClick="WdatePicker()" value="${intime}"></td>
	    <td>结束日期：<input class="Wdate" id="endDate"  type="text" onClick="WdatePicker()" value="${endTime}"></td>
	  </tr>
	  <tr  style="border:1px solid #cccccc;">
	  	<td>
	  		是否禁用：
	  			<select id="hiddenTypeId" style="width:120px;height:20px;" name="hiddenType">
	  				<option value="">--请选择--</option>
	  				<option value="-1">已禁用</option>
	  				<option value="2">未禁用</option>
	  			</select>
	  	</td>
	  	<td>&nbsp;</td>
	  	<td>&nbsp;</td>
	  </tr>
	</table>
<div class="none"><A id=foldin href="javascript:;" style="cursor: default;">收起</A></div>
</div>
<table id="myTable" cellspacing="0" cellpadding="0" class="tab_line" style="width:100%">
<thead>
<tr height="25" class="tr_line_bottom">
<th>登陆账号</th>
<th>详细信息</th>
<th>开户银行/卡类型</th>
<th>绑定类型</th>
<th>提交日期</th>
<th>状态</th>
</tr>
</thead>
<tbody id="tab">
<c:forEach items="${arrList}" var="li" >
<tr>
<td class="td_line">${li[0]}</td>
<td class="td_line" >
<div style="text-align:left;width:200px;">
<a style="text-decoration:underline;" href="javascript:FunWindowShow(${li[8]})">
账户:${li[2]}<br>(${li[1]})${li[3]}:${li[4]}
</a>
</div>
</td>
<td class="td_line">${li[10]}<br/>${li[5]}</td>
<td  class="td_line">
	<c:if test="${li[9]==0}">
		翼支付
	</c:if>
	<c:if test="${li[9]!=0}">
		其他
	</c:if>
</td>
<td class="td_line">${li[6]}</td>
<td class="td_line" >
	<span id="${li[8]}_statusFlag">
	<c:if test="${li[7]==0}">
		<a href='javascript:funSend(${li[8]})'  style='text-decoration:underline;'>验证</a>
		<a href="javascript:funDel(${li[8]})"   style='text-decoration:underline;'>删除</a>
	</c:if>
	<c:if test="${li[7]==1}">
		已验证
	</c:if>
	<br/>
	<c:if test="${li[11]==-1}">
		已禁用
	</c:if>
	</span>
</td>
</tr>
</c:forEach>
</tbody>
<tfoot>
<tr height="30">
<td colspan="9" class="tr_line_top">
<c:if test="${index!=null}">
<div class="grayr">
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
<ul style="border:1px solid #cccccc;padding:10px 10px 10px 10px;">
	<li style="text-align:center;">
		登陆账号：<input type="text" value="" style="width:135px;height:28px;font-size:17px;font-weight:bold;" id="userLogins" name="userLogins"/>
		&nbsp;&nbsp;
		<input type="radio" style="height:15px;" name="hh" value="1" checked/>禁用 &nbsp;&nbsp;
	  	<input type="radio" style="height:15px;" name="hh" value="2" />启用
	  	&nbsp;
	  	&nbsp;
    	<input onclick="funHidden()" type="button" style="width:85px; height:32px; border:0px;color:#fff; font-weight:bold;background:url(<%=path%>/images/button_out.png) no-repeat bottom; cursor:pointer;" value="提交"/>
	</li>
</ul>
</form>
</body>
</html:html>