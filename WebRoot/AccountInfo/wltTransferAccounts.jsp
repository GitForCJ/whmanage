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
<title>���ͨ</title>
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
 $("#myTable tr:odd").addClass("odd");//oddΪż����
 // $("#myTable tr:even").addClass("even")//evenΪż����
});

$(function(){
	if("${flagA}"!=null && "${flagA}"!="null" && "${flagA}"!="")
	{
		if("${flagA}"==-3)
		{
			$("#money").val("${money}");
			document.getElementById("${radioid}").checked="checked";
			alert("һ�����ڽ�ֹ�ظ�����!");
			//window.location.href="<%=path%>/AccountInfo/TransferAccounts.do?method=showTransferAccounts";
			return ;
		}
		else
		{
			if("${flagA}"==-2)
			{
				$("#money").val("${money}");
				document.getElementById("${radioid}").checked="checked";
				alert("�����������!");
				//window.location.href="<%=path%>/AccountInfo/TransferAccounts.do?method=showTransferAccounts";
				return ;
			}
			else
			{
				if("${flagA}"==0)
				{
					alert("�����ɹ�!");
					window.location.href="<%=path%>/AccountInfo/TransferAccounts.do?method=showTransferAccounts";
				}
				else
				{
					if("${flagA}"==-4)
					{
						alert("ϵͳ��ֹ��ǰ�û�����!");
						window.location.href="<%=path%>/AccountInfo/TransferAccounts.do?method=showTransferAccounts";
						return ;
					}
					else
					{
						alert("����ʧ��!");
						window.location.href="<%=path%>/AccountInfo/TransferAccounts.do?method=showTransferAccounts";
					}
				}
			}
		}
	}  
});
//��ϸ
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
		alert("��ѡ���˺�!");
		return false;
	}
	if($("#money").val().Trim().length<=0)
	{
		alert("������ת����!");
		return false;
	}
	if($("#money").val().Trim()<=0)
	{
		alert("��������ȷ��ת����!");
		return false;
	}
	if(!isInt($("#money").val().Trim()))
	{
		if(!isFloat($("#money").val().Trim()))
		{
			alert("��������ȷ��ת����!");
			return false;
		}
	}
	if($("#money").val().Trim()<1)
	{
		alert("ת����������һԪ��");
		return false;
	}

	if($("#password").val().Trim().length<=0)
	{
		alert("�����뽻������!");
		return false;
	}
	/*
	if(!isAlnum($("#password").val().Trim()))
	{
		alert("ֻ����26����СдӢ���ַ����������");
		return false;
	}
	*/
	document.getElementById("zk").style.backgroundColor="#cccccc";
	document.getElementById("zk").style.disabled="disabled";
	document.getElementById("zk").value="ת����";
	return true;
}


// ȥ�ո�
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
      <li>ת��</li></ul>
  </div>
</div>
<form id="form1" action="<%=path%>/AccountInfo/TransferAccounts.do?method=saveTransferAccounts" method="post">

<table id="myTable" cellspacing="0" cellpadding="0" class="tab_line" style="width:97%;text-align:left;">
<thead>
<tr height="25" class="tr_line_bottom">
<th>��ϸ��Ϣ</th>
<th>��������/������</th>
<th>�ύ����</th>
<th>״̬</th>
<th>Ȩ��</th>
<th>��ѡ��</th>
</tr>
</thead>
<tbody id="tab">
<c:forEach items="${arrList}" var="li" >
<tr>
<td class="td_line">
<div style="text-align:left;width:200px;">
<a style="text-decoration:underline;" href="javascript:FunWindowShow(${li[8]})">�˻�:${li[2]}</br>(${li[1]})${li[3]}:${li[4]}</a>
</div>
</td>
<td class="td_line">${li[9]}</br>${li[5]}</td>
<td class="td_line">${li[6]}</td>
<td class="td_line" >
	<c:if test="${li[7]==0}">
		δ��֤
	</c:if>
	<c:if test="${li[7]==1}">
		����֤
	</c:if>
</td>
<td  class="td_line" >
	<c:if test="${li[10]==0}">
		����
	</c:if>
	<c:if test="${li[10]==-1}">
		�ѽ���
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
<span style="height: 30px;vertical-align: middle;">ת����:</span>
<input style="font-size: 20px;height: 30px;width:180px;color: blue;" id="money" name="order_price" type="text" onkeyup="if(isNaN(value))execCommand('undo')" onafterpaste="if(isNaN(value))execCommand('undo')" onkeydown="keydownEvent()"/>Ԫ
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<span style="height: 30px;vertical-align: middle;">��������:</span>
<input style="font-size: 20px;height: 30px;width:180px;color: blue;" id="password" name="password" type="password"/>&nbsp;
<input type="submit" id="zk" value="ת��" name="OK" onclick="return funCheck();" style="margin-left: 20px;width: 80px;height: 30px;background-color: #1980c3;border: 2;font-size: 16px;color: white;"/>
</div>
</form>
<br/>
<br/>
<div style="padding-left:40px;padding-top:10px;margin-left: 20px;font-size: 13px; color: #FF6600;font-weight:bold;border:1px solid #FF9E4d;background-color: #FFFFE5;">
<ul>
<li style="list-style:none;">1��֧��ͨ�����п����ߴ��۹����ȡ�</li>
<li style="list-style:none;">&nbsp;</li>
<li style="list-style:none;">2�����׽�����2000Ԫ/�ʿ�1Ԫ�����ѣ����ڵ���2000Ԫ/����ѡ�</li>
<li style="list-style:none;">&nbsp;</li>
<li style="list-style:none;">3�����ʵʱ���ˡ�</li>
<li style="list-style:none;">&nbsp;</li>
</ul>
</div>
</body>
</html:html>