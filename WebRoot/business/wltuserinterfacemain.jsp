<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@ page language="java" contentType="text/html; charset=GBK"%>
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
    <ul class="tabs_wzdh"><li>用户接口</li></ul>
    <ul class="tabs" id="tabs">
        <li><a href="wltinterfaceuserlist.jsp">用户接口管理</a></li>    
    </ul>
  </div>
<!--选择内容-->
	<ul class="tab_conbox" id="tab_conbox">
<!--第1个盒子-->
      <li class="tab_con">
      <div id="search_tiaojian"><iframe frameborder="0" height="100%" width="100%" scrolling="no" src="wltinterfaceuserlist.jsp">您的浏览器版本不支持iframe，请升级或者跟换其他浏览器访问！</iframe></div>
      </li>
	</ul>
</div>
</body>
</html>