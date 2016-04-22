<%@ page contentType="text/html; charset=gb2312" language="java"%>
<%
String path = request.getContextPath();
%>

<html>
<head>
<title>账户激活</title>
<script src="../js/jquery-1.8.2.min.js" type="text/javascript"></script> 
<link href="../css/main_style.css" rel="stylesheet" type="text/css" />
<link href="../css/style.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
  			var i = 90;
        function msgShow() {
        document.getElementById("a").href="javascript:return false;";
            document.getElementById("msg").innerHTML ="短信正在飞速发送中...<font color=red>"+i+"</font>秒后未收到短信,请重新获取 ";
            if (i > 0) {
                i--;
            }
            else {
                document.getElementById("a").href="javascript:sendMsg();";
                document.getElementById("msg").innerHTML ="(点击获取短信验证码)";
            }
            setTimeout('msgShow()', 1000);
        }

function sendMsg(a){
    var a2=$("#newpwd").val();
    var a3=$("#newpwd1").val();
    if(a2==""||a3==""){
    alert("请填入完整数据");
   	 return ;
    }
    if(a2.length<8){
	    alert("新密码不能低于8位");
	    return ;
    }
    if(a2!=a3){
	    alert("两次输入密码不一致");
	    return ;
    }
    document.getElementById("newpwd").readOnly=true;
    document.getElementById("newpwd1").readOnly=true;
	i=90;
	msgShow();
	$.post(
    	$("#rootPath").val()+"/business/bank.do?method=phsendMsg",
      	{
      	},
      	function (data){
      		if(data == 0){
      		   document.getElementById("a").href="#";
      			alert("短信发送成功");
      		}else{
      			alert("短信发送失败");
      		}
      	}
	)
}
function btnSubmit(){
    var a=$("#msgcode").val();
	    var b=document.getElementById("qr");
	    if(!b.checked){
	    alert("尚未同意服务协议,不允许激活");
	    return false;
    }
    
    var a2=$("#newpwd").val();
    var a3=$("#newpwd1").val();
    if(a2==""||a3==""){
    alert("请填入完整数据");
   	 return false;
    }
    if(a2.length<8){
	    alert("新密码不能低于8位");
	    return false;
    }
    if(a2!=a3){
	    alert("两次输入密码不一致");
	    return false;
    }
    
    if(a.length!=6){
	    alert("请输入正确的短信验证码");
	    return false;
    }
	$("#form1").submit();
}
$(function(){
	if($("#mess").val() != ""){
		alert($("#mess").val());
	}
})
</script>

<style type="text/css">
.inputText4 {
	font-family:Arial,Helvetica,sans-serif;
	background:none repeat scroll 0 0 #F5F7FD;
	border:1px solid #B8BFE9;
	padding:5px 7px;
	width:200px;
	vertical-align:middle;
	height:30px;
	font-size:12px;
	margin:0;
	list-style:none outside none;
</style>
</head>
<body>
<input type="hidden" id="rootPath" value="<%=path %>"/>
    <div class="mobile-charge">
<form action="<%=path %>/rights/sysuser.do?method=phoneValidata" id="form1" method="post" class="form">
        <fieldset>
          <div class="form-line">
<br>
<br>
<br>
            <label class="label" for="mobile-num"><font size="3" color="red">第一次登陆请先修改您的登录密码,进行短信验证,激活您的账户：</font></label>
<hr>         
<br>  
 <div style="margin-left: 200;">
		<!--  		<b style="font-size: 16px;">原始登录密码：</b><input class="inputText4" type="text" name="oldpwd" id="oldpwd" /><font color="red" size=2>&nbsp;&nbsp;*</font>-->
<br>
<br>
				<b style="font-size: 16px;">新的登录密码：</b><input class="inputText4" type="password" name="newpwd" id="newpwd" /><font color="red" size=2>&nbsp;&nbsp;*长度不能低于8位</font>
<br>
<br>
				<b style="font-size: 16px;">&nbsp;&nbsp;&nbsp;确认新密码：</b><input class="inputText4" type="password" name="newpwd1" id="newpwd1" /><font color="red" size=2>&nbsp;&nbsp;*</font>
<br>
<br>
				<b style="font-size: 16px;">&nbsp;&nbsp;&nbsp;手机验证码：</b><input class="inputText4" type="text" name="msgcode" id="msgcode" /><a id="a" href="javascript:sendMsg();"><span id="msg">(点击获取短信验证码)</span></a>
<br>
<br>
<span style="font-size: 15px;">接收短信的手机号码为:${requestScope.phonenum}</span>   
<br>  
<br> 
<input type="checkbox" checked="checked" id="qr"/><span style="font-size: 15px;">我已阅读并同意相关服务条款 <a href="../rights/xieyi.html" target="view_window">服务协议</a></span>    
</div>
          </div>
<div class="field-item_button_m" id="btnChag" style="margin-left: 150;">
<input type="button" name="Button" value="确认" class="field-item_button" onclick="btnSubmit();">
<input type="button" name="Button" value="返回" class="field-item_button" onclick="history.go(-1)"></div>
<br>
        </fieldset>
      </form>
    </div>
</body>
<input type="hidden" id="mess" value="${requestScope.mess }"/>
</html>
