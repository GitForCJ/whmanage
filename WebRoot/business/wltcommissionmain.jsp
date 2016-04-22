<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@ page language="java" contentType="text/html; charset=gb2312"%>
<head>
<title>万汇通</title>
<script type="text/javascript" src="../js/jquery-1.8.2.min.js"></script>
<script type="text/javascript" src="../js/tab.js"></script>
<link href="../css/main_style.css" rel="stylesheet" type="text/css" />
</head>
<body>
<div id="tabbox">
<!--位置导航和选择菜单-->
  <div id="tabs_title">
    <ul class="tabs_wzdh"><li>佣金组管理</li></ul>
    <ul class="tabs" id="tabs">
        <li><a href="wltcommissionlist.jsp">本省佣金组</a></li>    
        <li><a href="wltcommissiontclist.jsp">外省佣金组</a></li>
        <li><a href="wltcommissionjtlist.jsp">直营佣金组</a></li>
        <li><a href="wltcommissionslist.jsp">增值佣金组</a></li>
    </ul>
  </div>
<!--选择内容-->
	<ul class="tab_conbox" id="tab_conbox">
<!--第1个盒子-->
      <li class="tab_con">
      <div id="search_tiaojian"><iframe frameborder="0" height="100%" width="100%" scrolling="no" src="wltcommissionlist.jsp">您的浏览器版本不支持iframe，请升级或者跟换其他浏览器访问！</iframe></div>
      </li>
            <li class="tab_con">
            <div id="search_tiaojian"><iframe frameborder="0" height="100%" width="100%" scrolling="no" src="wltcommissiontclist.jsp">您的浏览器版本不支持iframe，请升级或者跟换其他浏览器访问！</iframe></div>
      </li>
       <li class="tab_con">
      <div id="search_tiaojian"><iframe frameborder="0" height="100%" width="100%" scrolling="no" src="wltcommissionjtlist.jsp">您的浏览器版本不支持iframe，请升级或者跟换其他浏览器访问！</iframe></div>
      </li>
        <li class="tab_con">
      <div id="search_tiaojian"><iframe frameborder="0" height="100%" width="100%" scrolling="no" src="wltcommissionslist.jsp">您的浏览器版本不支持iframe，请升级或者跟换其他浏览器访问！</iframe></div>
      </li>
            <li class="tab_con">
      <div id="search_tiaojian"><iframe frameborder="0" height="100%" width="100%" scrolling="no" src="wltcommissionjklist.jsp">您的浏览器版本不支持iframe，请升级或者跟换其他浏览器访问！</iframe></div>
      </li>
      
      
	</ul>
</div>
</body>
</html>