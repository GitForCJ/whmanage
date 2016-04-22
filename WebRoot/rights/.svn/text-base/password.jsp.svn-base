<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String conString = request.getHeader("REFERER");
//System.out.println("password:"+conString);

if(conString==null || "".equals(conString))
{
  response.setHeader("Cache-Control","no-cache");
  String jumpto="<script language='JavaScript'>"+
				"  top.window.location='"+path+"/index.jsp';" +
                "</script>";
  out.print(jumpto);
  return;
}
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <title>万汇通安全中心</title>
<style type="text/css">
	a:hover {text-decoration:underline;color:red;}
</style>
<script type="text/javascript">
function showMessage(str)
{
   if(str.length!=0){
   		alert(str);
   }
}
showMessage("${mess}");
 function refresh()
{
document.getElementById("authImg").src="<%=path%>/authImg?now="+new Date();
}

function funblur()
{
	var userName=document.getElementById("userNameA").value;
	if(userName.Trim().length<=0)
	{
		document.getElementById("accid").innerHTML="请输入账户名!";
		document.getElementById("accid").style.color="red";
		document.getElementById("accid").style.fontWeight="bold";
		return ;
	}
		document.getElementById("accid").innerHTML="";
		document.getElementById("accid").style.color="red";
		document.getElementById("accid").style.fontWeight="bold";
		return ;
}
function funsubmit()
{
	var userName=document.getElementById("userNameA").value;
	var yanzhengma=document.getElementById("yanzhengma").value;
	if(userName.Trim().length<=0)
	{
		alert("请输入账户名!");
		return ;
	}

	if(yanzhengma.Trim().length<=0)
	{
		alert("请输入验证码!");
		return 
	}
	document.forms[0].submit();
	return;
}

String.prototype.Trim = function() 
{ 
	return this.replace(/(^\s*)|(\s*$)/g, ""); 
} 
</script>
</head>
  
  <body style="padding:0px 0px 0px 0px;margin:0px 0px 0px 0px;">
  <form id="for1" action="<%=path%>/AccountInfo/showAccountInfo.do?method=mimahuoqu" method="post">
    <div style="width:100%;height:70px;margin-top:20px;line-height:90px;padding-left:200px;">
    	<label style="font-size:35px;color:red;font-weight:bold;">万汇通</label>&nbsp;<label style="font-size:25px;font-weight:bold">安全中心</label>
    </div>
    <hr>
    <div style="width:100%;height:30px;line-height:30px;padding-left:200px;margin-top:10px;">
    	<label style="font-size:20px;font-weight:bold">找回密码</label>
    </div>
    <div style="width:100%;height:80px;padding-left:220px;">
    <table>
    	<tr>
    	<td>
    	<div style="float:left;line-height:43px;text-align:center;font-size:20px;color:white;font-weight:bold;width:45px;height:43px;margin-top:18px;background-image:url(<%=path %>/rights/images/lingxing1.jpg)">
    		1
    	</div>
    	</td>
    	<td>
    	<hr style="border:2px solid #cccccc;width:250px;float:left;margin-top:39px;">
    	</td>
    	<td>
    	<div style="float:left;line-height:43px;text-align:center;font-size:20px;color:white;font-weight:bold;width:45px;height:43px;margin-top:18px;background-image:url(<%=path %>/rights/images/lingxing2.jpg)">
    		2
    	</div>
    	</td>
    	<td>
    	<hr style="border:2px solid #cccccc;width:250px;float:left;margin-top:39px;">
    	</td>
    	<td>
    	<div style="float:left;line-height:43px;text-align:center;font-size:20px;color:white;font-weight:bold;width:45px;height:43px;margin-top:18px;background-image:url(<%=path %>/rights/images/lingxing2.jpg)">
    		3
    	</div>
    	</td>
    	</tr>
    </table>
    </div>
    <div style="width:100%;height:20px;margin-top:-13px;padding-left:205px;">
    	<label style="color:red;font-weight:bold;margin-right:220px;">输入账户名</label>
    	<label style="color:#cccccc;font-weight:bold;margin-right:240px;">重置密码</label>
    	<label style="color:#cccccc;font-weight:bold">完成</label>
    </div>
    <div style="width:100%;margin-top:20px;padding-left:300px;">
    	<label style="color:#cccccc;font-weight:bold">账户名</label>
    	<input name="userName" id="userNameA" type="text" value="" style="width:170px;height:30px;"  onblur="funblur()"/>
    	<label style="font-size:13px;" id="accid" ></label>
    	<br/>
    	<br/>
    	<label style="color:#cccccc;font-weight:bold">验证码</label>
    	<input name="rand" id="yanzhengma" type="text" value="" style="width:50px;height:30px;"/>
    	<img alt="" src="<%=path%>/authImg" onclick="refresh()" id="authImg" style="" align="absmiddle"/>
    	<a href="javascript:refresh()" style="font-size:14px;font-weight:bold;margin-top:20px;text-decoration:none;">换一张</a>
    	<br/>
    	<br/>
    	<input onclick="funsubmit()" type="button" style="width:85px; height:32px; border:0px;color:#fff; font-weight:bold;background:url(<%=path%>/images/button_out.png) no-repeat bottom; cursor:pointer;" value="下一步"/>
    	&nbsp;
    	<input onclick="javascript:window.location.href='<%=path %>/index.jsp'" type="button" style="width:85px; height:32px; border:0px;color:#fff; font-weight:bold;background:url(<%=path%>/images/button_out.png) no-repeat bottom; cursor:pointer;" value="回到首页"/>
    </div>
    </form>
  </body>
</html>
