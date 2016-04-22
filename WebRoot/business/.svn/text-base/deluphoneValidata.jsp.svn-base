<%@ page contentType="text/html; charset=gb2312" language="java"%>
<%
String path = request.getContextPath();
%>

<html>
<head>
<title>登陆手机令牌验证</title>
<script src="../js/jquery-1.8.2.min.js" type="text/javascript"></script> 
<link href="../css/main_style.css" rel="stylesheet" type="text/css" />
<link href="../css/style.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
  			var i = 90;
        function msgShow() {
            document.getElementById("msg").innerHTML ="<font color=red>"+i+"</font>秒后未收到短信,请重新获取 ";
            if (i > 0) {
                i--;
            }
            else {
                document.getElementById("a").href="javascript:sendMsg();";
                document.getElementById("msg").innerHTML ="(点击获取短信验证码)";
            }
            setTimeout('msgShow()', 1000);
        }

function sendMsg(){
i=90;
msgShow();
	$.post(
    	$("#rootPath").val()+"/business/bank.do?method=sendMsg",
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
    if(a.length!=6){
    alert("请输入正确的验证码");
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
</head>
<body>
<input type="hidden" id="rootPath" value="<%=path %>"/>
    <div class="mobile-charge">
<form action="<%=path %>/rights/sysuser.do?method=dengluValidata" id="form1" method="post" class="form">
        <fieldset>
          <div class="form-line">
<br>
<br>
<br>
            <label class="label" for="mobile-num"><font size="3" color="red">用户登陆身份验证：</font></label>
<hr>         
<br>  
 <div style="margin-left: 200;">
				<b>验证码：</b><input height="20px" type="text" name="msgcode" id="msgcode"> <!--<a id="a" href="javascript:sendMsg();"><span id="msg">(点击获取短信验证码)</span></a>-->
<br>
<br>
获取令牌验证码手机号码为:<span>${requestScope.phonenum}</span>   
<br>  
<br> 
</div>
          </div>
<div class="field-item_button_m" id="btnChag" style="margin-left: 200;">
<input type="button" name="Button" value="确认" class="field-item_button" onclick="btnSubmit();">
<input type="button" name="Button" value="返回" class="field-item_button" onclick="history.go(-1)"></div>
<br>
        </fieldset>
      </form>
    </div>
</body>
<input type="hidden" id="mess" value="${requestScope.mess }"/>
</html>
