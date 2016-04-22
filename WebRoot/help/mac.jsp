<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>mac 地址绑定</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
  <body style="padding-left:30px;">
    <div class="getcer_title">
		<p align="center" class="find_ps clearfix" style="font-weight:bold;font-size:20px;">
			<span class="icon arrow fixIE6"></span>
			<span>mac地址绑定</span>
		</p>
	  </div>
	  <p align="left">系统用户查看本地MAC地址：</p>
	  <p align="left">1)打卡开始程序中的“运行”选项；</p>
	   <p align="left"><img src="images/a11.jpg" width="80%"/></p>
	    <p align="left">2)在“运行”框中输入 cmd；</p>
	    <p align="left"><img src="images/a22.jpg" width="80%"/></p>
	    <p align="left">3)在运行对话框中，输入 ipconfig /all ;</p>
	    <p align="left"><img src="images/ipconfig.jpg" width="80%"/></p>
	    
	     <p align="left">4)然后回车；标红区域为本机有线MAC地址和无线MAC地址。</p>
	    <p align="left"><img src="images/macaddress.jpg"  width="80%"/></p>
	    <br/>
	     <p align="left">MAC地址绑定：</p>
	     <p align="left">1)IE浏览器设置：在IE浏览器“工具”中，选择“Internet选项”，选择“自定义级别”。</p>
	    <p align="left"><img src="images/i1.jpg"  width="80%"/></p>
	    
	    <p align="left">2)在“自定义级别”中，找到“对未标记为可安全执行脚本的ActiveX控件初始化并执行”选择“启用”状态。</p>
	    <p align="left" style="color:red">注：必须选择为启用，否则无法完成MAC地址的获取及绑定。</p>
	    <p align="left"><img src="images/i2.jpg"  width="80%"/></p>
	    
	    <p align="left">3)标记为启用后，点击确定。登录：www.wanhuipay.com登录，在“账户设置”—“安全设置”中，找到MAC地址绑定，选择“验证”。</p>
	    <p align="left"><img src="images/i3.jpg"  width="80%"/></p>
	    <p align="left"  style="color:red">4)将“当前系统获取的MAC地址”复制粘贴到“备用MAC地址一”中，点击确认完成MAC地址。</p>
	 <p align="left"  style="color:red">注：当系统开启ActiveX控件时，IE浏览器会提示有风险，若操作修复则系统无法获取MAC地址；若用户已完成MAC地址绑定操作，则用户下次无法登录系统。IE绑定MAC地址后，无法使用其他浏览器登录。
	    </p>
  </body>
</html>
