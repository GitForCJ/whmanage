<%@ page contentType="text/html; charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
 <%
 	 String path = request.getContextPath();
 %>
 <html>
<head>
<title>拉卡拉绑定</title>
<script type="text/javascript" src="../js/jquery-1.8.2.min.js"></script>
<script  type="text/javascript" src="../js/util.js"></script>
<link href="../css/main_style.css" rel="stylesheet" type="text/css" />
<style type="text/css">
	a:hover{
		text-decoration:underline;
	}
</style>

<SCRIPT type=text/javascript>
showMessage("${mess}");

function funCss(className)
{
	className.style.boxShadow="0 0 10px #ccc";
}
function funClearCss(className)
{
	className.style.boxShadow="";
}
// 去空格
String.prototype.Trim = function() 
{ 
	return this.replace(/(^\s*)|(\s*$)/g, ""); 
}
function getUserName(){
	var userLogin=document.getElementById("userLogin").value.Trim();
	if(userLogin=="")
	{
		alert("请输入万汇通平台的登陆账号!");
		return ;
	}
	
		$.post(
	    	"<%=path%>/business/prod.do?method=yanzheng&tradeObject="+userLogin,
	      	{
	      	},
	      	function (data){
	      		var phoneInfo;
      			phoneInfo = "用户名:"+data[0].flag;
	      		document.getElementById("showId").innerHTML=phoneInfo;
	      			return ;
	      	},"json"
		)
	}

function check(){
  	var lakalaNum=document.getElementById("lakalaNum").value.Trim();
  	var userno=document.getElementById("userno").value.Trim();
  	var userfee=document.getElementById("userfee").value.Trim();
  	var userLogin=document.getElementById("userLogin").value.Trim();
  	if(lakalaNum=="")
  	{
  		alert("请输入拉卡拉终端编号!");
  		return ;
  	}
  	if(userno=="")
  	{
  		alert("请输入用户系统编号!");
  		return ;
  	}
  	if(userfee=="")
  	{
  		alert("请输入资金账号!");
  		return ;
  	}
  	if(userLogin=="")
  	{
  		alert("请输入登陆账号!");
  		return ;
  	}
  	document.getElementById("btnSubmit").value="绑定中,,";
	document.getElementById("btnSubmit").disabled=true;
  	document.getElementById("forId").submit();
  	return;
}
</SCRIPT>
</head>
<body style="overflow-y:hidden;">
<div id="popdiv"><span id="sellist"></span><div style="clear:both"></div></div>

<form id="forId" method="post" action="<%=path%>/rights/sysuser.do?method=addLakala">
<div class="pass_main">
    <div class="pass_list">
    	<ul>
			<li style="margin-top:20px;"><strong>拉卡拉终端编号：</strong><span><input name="lakalaNum" id="lakalaNum" onblur="funClearCss(this)" onfocus="funCss(this)" style="width:200px;height:30px;font-size:20px;"  type="text"/></span></li>
			<li style="margin-top:20px;"><strong>万汇通系统编号：</strong><span><input name="userno" id="userno" onblur="funClearCss(this)" onfocus="funCss(this)" style="width:200px;height:30px;font-size:20px;" type="text"/></span></li>
			<li style="margin-top:20px;"><strong>万汇通资金账号：</strong><span><input name="userfee" id="userfee" onblur="funClearCss(this)" onfocus="funCss(this)" style="width:200px;height:30px;font-size:20px;" type="text"/></span></li>
			<li style="margin-top:20px;">
				<strong>万汇通登录账号：</strong>
				<span >
					<input name="userlogin" id="userLogin" onblur="funClearCss(this)" onfocus="funCss(this)" style="width:200px;height:30px;font-size:20px;"  maxlength="11" type="text"/>&nbsp;&nbsp;
					<font color="#FF0000" id="showId"></font>&nbsp;&nbsp;&nbsp;
					<a href="javascript:getUserName()" >验证账户</a>
				</span>
				
			</li>
        </ul>
   </div>
   <div class="field-item_button_m">
   <input type="button" value="绑定" id="btnSubmit" onclick="check();" class="field-item_button" />
   </div>

</div>
</form>
<br /><br />
<div style="padding-left:40px;padding-top:10px;margin-left: 20px;font-size: 13px; color: #FF6600;font-weight:bold;border:1px solid #FF9E4d;background-color: #FFFFE5;">
<ul>
<li style="list-style:none;">1、绑定代办点提供的拉卡拉信息。</li>
<li style="list-style:none;">&nbsp;</li>
</ul>
</div>
</body>
</html>