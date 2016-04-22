<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%
  String path = request.getContextPath();
%>
<head>
<title>万汇通</title>
<script type="text/javascript" src="../js/jquery-1.8.2.min.js"></script>
<script type="text/javascript" src="../js/tab.js"></script>
<link href="../css/main_style.css" rel="stylesheet" type="text/css" />
</head>
<body>
<input type="hidden" id="mark" value="${param.mark }"/>
<div id="tabbox">
<!--位置导航和选择菜单-->
  <div id="tabs_title">
    <ul class="tabs_wzdh"><li>管理员管理</li></ul>
    <ul class="tabs" id="tabs">
        <li><a href="wltuseradd.jsp">添加用户</a></li>   
      	 <li><a href="wltuserlist.jsp">用户信息查询</a></li>    
        <li><a href="wltusercheck.jsp">个人信息管理</a></li>    
    </ul>
  </div>
<!--选择内容-->
	<ul class="tab_conbox" id="tab_conbox">
<!--第1个盒子-->
      <li class="tab_con">
      <div id="search_tiaojian"><iframe frameborder="0" height="93%" width="100%" scrolling="auto" src="wltuseradd.jsp?st=1">您的浏览器版本不支持iframe，请升级或者跟换其他浏览器访问！</iframe></div>
      </li>
            <li class="tab_con">
      <div id="search_tiaojian"><iframe frameborder="0" height="100%" width="100%" scrolling="no" src="<%=path %>/rights/sysuser.do?method=list&st=1">您的浏览器版本不支持iframe，请升级或者跟换其他浏览器访问！</iframe></div>
      </li>
      <li class="tab_con">
      <div id="search_tiaojian"><iframe frameborder="0" height="100%" width="100%" scrolling="no" src="wltuserupdate.jsp?uid=${sessionScope.userSession.user_id }&st=1">您的浏览器版本不支持iframe，请升级或者跟换其他浏览器访问！</iframe></div>
      </li>
	</ul>
</div>
</body>
</html>