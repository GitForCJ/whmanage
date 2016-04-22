<%@ page contentType="text/html; charset=gb2312" session="false"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%
String path = request.getContextPath();
System.out.println(path);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>首页</title>

<style type="text/css">
 *{margin:0; padding:0;}
body {font-size:15px; color:#222; font-family:Verdana,Arial,Helvetica,sans-serif; background:#B7E3F0;}
.clearfix:after {content: "."; display: block; height: 0; clear: both; visibility: hidden;}
.clearfix {zoom:1;}
ul,li {list-style:none;}
img {border:0;}
h1 {height:120px; line-height:50px; font-size:22px; font-weight:normal; 
	font-family:"Microsoft YaHei",SimHei;background:#B7E3F0; }
.wrapper {width:100%;height:360px;margin: 0 auto; overflow:hidden;float:left;}

/* focus */
#focus {width:700px; height:320px; overflow:hidden; position:relative;margin-left:0px;background:#DEEFFC;float: left;}
#focus ul {height:380px; position:absolute;}
#focus ul li {float:left; width:700px; height:320px; overflow:hidden; position:relative; background:#E09A7E;}
#focus ul li div {position:absolute; overflow:hidden;}
#focus .btnBg {position:absolute; width:700px; height:20px; left:0; bottom:0; background:#E09A7E;}
#focus .btn {position:absolute; width:780px; height:10px; padding:5px 10px; right:0; bottom:0; text-align:right;}
#focus .btn span {display:inline-block; _display:inline; _zoom:1; width:25px; height:10px; _font-size:0; margin-left:5px; cursor:pointer; background:#fff;}
#focus .btn span.on {background:#fff;}
#focus .preNext {width:45px; height:100px; position:absolute; top:90px; background:url(img/sprite.png) repeat x; cursor:pointer;}
#focus .pre {left:0;}
#focus .next {right:0; background-position:right top;}

.bt li{float:left;} 
</style>

<script type="text/javascript" src="js/jquery.min.js"></script>
<script type="text/javascript" src="../js/util.js"></script>
<script type="text/javascript" src="../js/check.js"></script>
<script type="text/javascript">
$(function() {
	var sWidth = $("#focus").width(); //获取焦点图的宽度（显示面积）
	var len = $("#focus ul li").length; //获取焦点图个数
	var index = 0;
	var picTimer;
	
	//以下代码添加数字按钮和按钮后的半透明条，还有上一页、下一页两个按钮
	var btn = "<div class='btnBg'></div><div class='btn'>";
	for(var i=0; i < len; i++) {
		btn += "<span></span>";
	}
	btn += "</div><div class='preNext pre'></div><div class='preNext next'></div>";
	$("#focus").append(btn);
	$("#focus .btnBg").css("opacity",0.5);

	//为小按钮添加鼠标滑入事件，以显示相应的内容
	$("#focus .btn span").css("opacity",0.4).mouseenter(function() {
		index = $("#focus .btn span").index(this);
		showPics(index);
	}).eq(0).trigger("mouseenter");

	//上一页、下一页按钮透明度处理
	$("#focus .preNext").css("opacity",0.2).hover(function() {
		$(this).stop(true,false).animate({"opacity":"0.5"},300);
	},function() {
		$(this).stop(true,false).animate({"opacity":"0.2"},300);
	});

	//上一页按钮
	$("#focus .pre").click(function() {
		index -= 1;
		if(index == -1) {index = len - 1;}
		showPics(index);
	});

	//下一页按钮
	$("#focus .next").click(function() {
		index += 1;
		if(index == len) {index = 0;}
		showPics(index);
	});

	//本例为左右滚动，即所有li元素都是在同一排向左浮动，所以这里需要计算出外围ul元素的宽度
	$("#focus ul").css("width",sWidth * (len));
	
	//鼠标滑上焦点图时停止自动播放，滑出时开始自动播放
	$("#focus").hover(function() {
		clearInterval(picTimer);
	},function() {
		picTimer = setInterval(function() {
			showPics(index);
			index++;
			if(index == len) {index = 0;}
		},2000); //此4000代表自动播放的间隔，单位：毫秒
	}).trigger("mouseleave");
	
	//显示图片函数，根据接收的index值显示相应的内容
	function showPics(index) { //普通切换
		var nowLeft = -index*sWidth; //根据index值计算ul元素的left值
		$("#focus ul").stop(true,false).animate({"left":nowLeft},300); //通过animate()调整ul元素滚动到计算出的position
		//$("#focus .btn span").removeClass("on").eq(index).addClass("on"); //为当前的按钮切换到选中的效果
		$("#focus .btn span").stop(true,false).animate({"opacity":"0.4"},300).eq(index).stop(true,false).animate({"opacity":"1"},300); //为当前的按钮切换到选中的效果
	}
});


//===
	function runB1(form){
		var checkCode = document.getElementById("check").value.toUpperCase();
		if(checkCode.length=="" ){
			alert("请输入验证码");
			return;
		}
		if(checkCode != code){
			alert("验证码不正确");
			return;
		}
		var name = navigator.appName;
		var version = navigator.appVersion;
		if (!check())return;
        var date = new Date((new Date()).getTime() + 30 * 24 * 60 * 60 * 1000);
        document.cookie = "login=" + escape(document.getElementById("username").value) + "; expires=" + date.toGMTString();
		document.forms[0].submit();
		return;
	}
	function check() {
		CT=document.getElementById("username");
		CTV=CT.value;
		if(CTV == ""){
			validatePrompt (CT,"登录名不能为空。");
			return (false);
		}
		CT=document.getElementById("userpassword");
		CTV=CT.value;
		if (CTV ==""){
			validatePrompt (CT,"密码不能为空。");
			return (false);
		}
		if(CTV.indexOf("'") > -1){
			validatePrompt (CT,"密码不能包含无效字符'。");
			return (false);
		}
		return (true);
	}
	function validatePrompt (Ct, PromptStr) {
	    alert(PromptStr);
		Ct.focus();
		return;
	}
	function logFocus(){
		var form = document.forms[0];
		var name = navigator.appName;
		var version = navigator.appVersion;
		if(name != "Microsoft Internet Explorer" ){
			//alert("建议使用ie系列浏览器");
			return;
		}
        var cookies = document.cookie.split(";");
        for (var i = 0; i < cookies.length; i++)
        {
          var cookie = cookies[i].split("=");
          if (cookie[0].trim() == "login") {
            form.login.value = unescape(cookie[1]);
          }
        }
        if (form.username.value.length == 0) {
          form.login.focus();
        } else {
          form.password.focus();
        }
		window.status="软件";
	}
	function onkeyboard(){
		var a = event.keyCode
		if(a == 13){
			runB1(form);
		}
	}
	function clearlogin(txt){
	if(txt.value == "手机号码"){
	txt.value = "";
	}
	}
	function setlogin(txt){
	if(txt.value == ""){
	txt.value = "手机号码";
	}
	}
	function clearpw(txt){
	if(txt.value == "请输入密码"){
	txt.value = "";
	}
	}
	document.onkeydown = onkeyboard;
	showMessage("${mess}");


</script>
</head>

<body onLoad="createCode();logFocus()">
<input type="hidden" id="rootPath" value="<%=path %>"/>
<input type="hidden" id="mess" value="${requestScope.msg }"/>
<html:form  method="post" action="/rights/sysuser.do?method=login" >
<h1><img src="img/headBack.png" width="100%;height:120px;" /></h1>
<div class="wrapper">
	<div id="focus">
		<ul>
			<li><img src="img/01.jpg" /></li>
			<li><img src="img/02.jpg"  /></li>
			<li><img src="img/03.jpg"  /></li>
			<li><img src="img/04.jpg"  /></li>
			<li><img src="img/05.jpg" /></li>
		</ul>
	</div>	
<div id="login" style="background:url(img/login_bg.png) no-repeat;margin-left:700px;width:(100%-700px);height:320px;overflow:hidden; position:relative;">
<table style="margin-top: 90px;margin-left: 10px ;" cellspacing="15px" border="0">
<tr><td align="right" style="font-size: 20px;font-family: serif;"><strong>用户名:</strong></td><td algin="left"><input size="15" name="username" id="username" type="text"  style="height: 30px;font-size: 20px;font-style: oblique;color: blue;" /></td></tr>
<tr><td align="right" style="font-size: 20px;font-family: serif;"><strong>密&nbsp;码&nbsp;:</strong></td><td algin="left"><input name="userpassword" id="userpassword" type="password"  size="15" style="height: 30px;font-size: 20px;font-style: oblique;color: blue;" /></td></tr>
<tr><td align="right" style="font-size: 20px;font-family: serif;"><strong>验证码:</strong></td>
<td algin="left">
	<input type="text" id="check" onpaste="return false" maxLength="8" size="6" style="height: 30px;font-size: 20px;font-style: oblique;color: blue;" />
	<input type="text" size="4" onclick="createCode()" readonly="readonly" id="checkCode" onselectstart="return false" 
	style="background:url(img/yan.png) center no-repeat;height: 30px;font-size: 15px;width: 50px;"/>
	</td></tr>
<tr><td algin="right">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td><td align="left"><img src="img/loginbt.jpg" onclick="runB1(this.form)"/> </td></tr>
</table>
    </div>
</div>
<center>
<a href="xieyi.html" target="_blank">万汇通服务协议</a>||
<a href="#" target="_blank">服务介绍</a>||
<a href="http://t.sina.com.cn/3164340695" target="_blank">官方微博</a>||
<a href="#" target="_blank">万汇通[粤ICP备11104967-5]</a>
</center>
<br/>
<center>
Copyright @ 2013 - 2015 Wanheng.All Rights Reserved.</li>
<br/>
万恒公司 版权所有
</center>
</html:form>
</body>
</html>