<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>身份验证器</title>

<link href="<%=path %>/css/main_style.css" rel="stylesheet" type="text/css" />

<script type="text/javascript">
	function fun(id)
	{
		if(id=="google")
		{
			document.getElementById("appleTitle").style.backgroundColor="#f8f8f8";
			document.getElementById("googleTitle").style.backgroundColor="white";
			document.getElementById("apple").style.display="none";
			document.getElementById("google").style.display="block";
		}
		else
		{
			document.getElementById("googleTitle").style.backgroundColor="#f8f8f8";
			document.getElementById("appleTitle").style.backgroundColor="white";
			document.getElementById("google").style.display="none";
			document.getElementById("apple").style.display="block";
		}
	}
	
</script>
  </head>
  
  <body>
	 <div id="tabbox">
		<!--位置导航和选择菜单-->
		  <div id="tabs_title">
		    <ul class="tabs_wzdh"><li>身份验证器</li></ul>
		    <ul class="tabs" id="tabs">
		        <li id="googleTitle" style="border:0px;"><a href="javascript:fun('google')">安卓用户</a></li>   
		      	 <li id="appleTitle" style="border:0px;"><a href="javascript:fun('apple')">苹果用户</a></li>    
		      	 <li>&nbsp;</li>   
		    </ul>
		  </div>
		</div>
	<div  style="padding-left:30px;" id="google" style="display:none;">
	  <p align="left">验证器下载：</p>
	  <p align="left">1)方法一：扫描下方二维码信息获得下载链接，下载安装google身份验证器。</p>
	  <p align="left"><img src="images/g1.png" /></p>
	  
	   <p align="left">2)方法二：安装：google身份验证器在应用商店都是可以下载得到的，下面以“应用宝”为例</p>
	   <p align="left">进行下载安装：</p>
	   <p align="left">在“应用宝”中搜索“google身份验证器”</p>
	  <p align="left"><img src="images/g2.jpg" width="80%"/></p>
	  
	  <p align="left">成功安装后打开“身份验证器”。</p>
	  <p align="left">程序配置：</p>
	  <p align="left">1)Google身份验证器安装成功首次运行需要配置一下才可以使用的。点击【开始设置】。</p>
	    <p align="left"><img src="images/g22.png" width="80%"/></p>
	    
	   <p align="left">2)添加账户：（建议用户手动添加账户）。</p>
	    <p align="left"><img src="images/g3.png" width="80%"/></p> 
	    
	    <p align="left">3)选择“输入提供的密钥”跳转到手动输入账户界面。</p>
	    <p align="left">输入账户名称：该账户名称为用户需要绑定的“万汇通”登陆帐号。</p>
	    <p align="left">输入账户密钥：该密钥为“万汇通”所提供，每个用户拥有不同的密钥。</p>
	     <p align="left" style="color:red;">注：密钥在“账户设置”—“用户信息”中的注册验证密钥码。</p>
	    <p align="left"><img src="images/g4.png" width="80%"/></p> 
	    
	   
	     <p align="left">4) 以上信息填写完成后，点击“添加”按钮，完成账户的添加工作。完成绑定后的界面如下图所示：</p>
	     <p align="left"><img src="images/g5.png" width="80%"/></p> 
	 </div>
  
  <div  style="padding-left:30px;" id="apple" style="display:none;">
    <p align="left" style="color:red;">安装：注：苹果未越狱版本无法安装。</p>
    <p align="left">google身份验证器在应用平台上都是可以下载得到的，下面以“PP助手”为例进行下载安装。</p>
	  <p align="left">1)在“PP助手”中搜索“google身份验证器”</p>
	  <p align="left"><img src="images/p1.png"  width="50%"/></p>
	  
	   <p align="left">2)安装成功后点击打开身份验证器</p>
	  <p align="left"><img src="images/p2.png" width="50%"/></p>
	  
	  <p align="left">3)开始设置，添加账户：（建议用户手动输入添加账户）</p>
	    <p align="left"><img src="images/p3.png" width="50%"/></p>
	    <p align="left"><img src="images/p4.png" width="50%"/></p> 
	    
	    <p align="left">选择“手动输入”跳转到手动输入账户界面。</p>
	    <p align="left">输入账户名称：该账户名称为用户需要绑定的“万汇通”登陆帐号。</p>
	     <p align="left">输入账户密钥：该密钥为“万汇通”所提供，每个用户拥有不同的密钥。</p>
	         <p align="left" style="color:red;">注：密钥在“账户设置”—“用户信息”中的注册验证密钥码。</p>
	     <p align="left">默认选择基于时间</p>
	     <p align="left">4)添加成功</p>
	     <p align="left"><img src="images/p5.png" width="50%"/></p> 
	     
  </div>
  </body>
  <script>
  window.onload=fun("google");
  </script>
</html>
