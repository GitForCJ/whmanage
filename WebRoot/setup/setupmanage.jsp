<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@ page language="java" contentType="text/html; charset=gb2312"%>
<%
  String path = request.getContextPath();
%>
<head>
<title>���ͨ</title>
<script type="text/javascript" src="../js/jquery-1.8.2.min.js"></script>
<script type="text/javascript" src="../js/tab.js"></script>
<link href="../css/main_style.css" rel="stylesheet" type="text/css" />
</head>
<body>
<input type="hidden" id="mark" value="${param.mark }"/>
<div id="tabbox">
<!--λ�õ�����ѡ��˵�-->
  <div id="tabs_title">
    <ul class="tabs_wzdh"><li>���ݹ���</li></ul>
    <ul class="tabs" id="tabs">
        <li><a href="<%=path %>/business/prod.do?method=setupGplist&st=0">������</a></li>   
        <li><a href="">�����б�</a></li>  
 		<li><a href="">���ݷ���</a></li>   
    </ul>
  </div>
<!--ѡ������-->
	<ul class="tab_conbox" id="tab_conbox">
<!--��1������-->
      <li class="tab_con">
      <div id="search_tiaojian"><iframe frameborder="0" height="100%" width="100%" scrolling="auto" src="<%=path %>/business/prod.do?method=setupGplist&st=0">����������汾��֧��iframe�����������߸���������������ʣ�</iframe></div>
      </li>
      <li class="tab_con">
      <div id="search_tiaojian"><iframe frameborder="0" height="100%" width="100%" scrolling="no" src="<%=path %>/business/prod.do?method=oneagentList">����������汾��֧��iframe�����������߸���������������ʣ�</iframe></div>
      </li>
 		<li class="tab_con">
     	 <div id="search_tiaojian"><iframe frameborder="0" height="100%" width="100%" scrolling="no" src="<%=path %>/business/prod.do?method=configureList">����������汾��֧��iframe�����������߸���������������ʣ�</iframe></div>
      </li>
	</ul>
</div>
</body>
</html>