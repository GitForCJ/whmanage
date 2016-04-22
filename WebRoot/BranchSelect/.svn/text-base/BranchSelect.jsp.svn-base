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
jQuery(function(){
	//$("#changeCity").click(function(a){$("#allCity").slideDown(0);a.stopPropagation();$(this).blur()});$("#allCity").click(function(a){a.stopPropagation()});$(document).click(function(a){a.button!=2&&$("#allCity").slideUp(0)});$("#foldin").click(function(){$("#allCity").slideUp(0)})
 $("#myTable tr:odd").addClass("odd");//odd为偶数行
 // $("#myTable tr:even").addClass("even")//even为偶数行
});

function checksubmit()
{
	if($("#accountInput").val().Trim().length<=0 && $("#userstatus").val().Trim().length<=0)
	{
		alert("请输入或选择账户！");
		return false;
	}
	
	if($("#accountInput").val().Trim().length>0 && $("#userstatus").val().Trim().length>0)
	{
		alert("登陆账号和账号选择只能选择一项！");
		return false;
	}
	
	if($("#startDate").val()!="" && $("#endDate").val()!="" )
	{
		if($("#endDate").val()<$("#startDate").val())
		{
			alert("结束日期必须大于开始日期");
			return false;
		}
	}
	
	document.getElementById("form1").action="<%=path%>/branchManage/BranchSelect.do?method=showAgentInfo";
	document.getElementById("form1").submit();
}

// 去空格
String.prototype.Trim = function() 
{ 
	return this.replace(/(^\s*)|(\s*$)/g, ""); 
} 
function funPage(index)
{
	document.getElementById("form1").action="<%=path%>/branchManage/BranchSelect.do?method=showAgentInfo&index="+index;
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

$(function(){
	//funChange();
});

</SCRIPT>
</head>
<body rightmargin="0" leftmargin="0"  topmargin="0" class="body_no_bg">
<div id="popdiv"><span id="sellist"></span><div style="clear:both"></div></div>
<div id="tabbox">
  <div id="tabs_title">
    <ul class="tabs_wzdh">
      <li>网点查询</li></ul>
  </div>
</div>
<form id="form1" name="for" action="<%=path%>/branchManage/BranchSelect.do?method=showAgentInfo" method="post">
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
	<table width="100%" border="0" style="margin-left:10px;" cellspacing="0" cellpadding="0">
	  <tr>
	    <td>
	    	登陆账号：
			<input id="accountInput" name="inputName" value="${inputName}" style="height: 25px;width:130px;" />
	    </td>
	    <td>
	         <span style="float:left;">账号选择：</span>
	    	<div class="sub-input"><a class="sia-2 selhover" id="sl-userstatus" style="width:150px;"  href="javascript:void(0);"></a></div>
  			<input nname="nname" name="selectName" id="userstatus" defaultsel="${requestScope.roleSel}" type="hidden"  value="${selectName}" />
	    </td>
			<td>
				 <span style="float:left;">用户类型：</span>
				<div class="sub-input"><a class="sia-2 selhover" id="sl-sr_type" href="javascript:void(0);"></a></div>
		  		<input nname="nname" name="sr_type" id="sr_type" defaultsel="请选择[]|管理员[1]|代理商[2]|代办点[3]|接口商[4]" type="hidden" value="${sr_type}" />
			</td>
		</tr>
		<tr>
			<td>
		    	开始日期：
				<input class="Wdate" name="startDate" id="startDate" type="text" style="height: 25px;width:130px;" onClick="WdatePicker()" value="${startDate }">
		    </td>
		    <td style="text-align:left;">
		    	结束日期：
				<input class="Wdate" name="endDate" id="endDate" type="text" style="height: 25px;width:130px;" onClick="WdatePicker()" value="${endDate }">
		    </td>
		    <td>
		    	&nbsp;
		    </td>
		</tr>
	</table>
<div class="none"><A id=foldin href="javascript:;" style="cursor: default;">收起</A></div>
</div>
<table id="myTable" cellspacing="0" cellpadding="0" class="tab_line">
<thead>
<tr height="25" class="tr_line_bottom">
<th>序号</th>
<th>登陆账号</th>
<th>用户编号</th>
<th>用户名称</th>
<th>账户号码</th>
<th>开户时间</th>
<th>状态</th>
</tr>
</thead>
<tbody id="tab">
<c:forEach items="${arrList}" var="li" varStatus="index">
<tr>
<td class="td_line">${index.index+1 }</td>
<td class="td_line">${li[0]}</td>
<td class="td_line">${li[1]}</td>
<td class="td_line">${li[2]}</td>
<td class="td_line">${li[4]}</td>
<td class="td_line">${li[5]}</td>
<td class="td_line">${li[3]}</td>
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
</form>
</body>
</html:html>