<%@ page language="java" contentType="text/html; charset=gb2312"%>
<%
  String path = request.getContextPath();
%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>�������ͳ��</title>
<style>
*{ padding:0; margin:0;}

.wrapper{
	width:820px;
	overflow:hidden;
	margin:20px auto;
}

.wrapper a{
	float:left;
	width:260px;
	height:60px;
	text-align:center;
	line-height:60px;
	border:1px #7cb5df solid;
	font-size:24px;
	margin:0 11px 12px 0;
	color:#005493;
	background: -webkit-gradient(linear, left top, left bottom, from(#edf7ff), to(#abdfff));
           -webkit-background-origin: padding; 
		   -webkit-background-clip: content;
	text-decoration:none;
	text-shadow:1px 1px 0 #fff;
}

</style>
</head>


<body>

	<div class="wrapper">
    	<a href="<%=path%>/rights/flowproportioncount.jsp">�����̡�ʡ������</a>
        <a href="<%=path%>/rights/flowusertatecount.jsp">�ӿ��̽���ͳ��</a>
        <a href="<%=path%>/rights/flowtradetimerange.jsp">����ʱ��ͳ��</a>
        <a href="<%=path%>/business/order.do?method=flow2list">����������ϸ</a>
    </div>

</body>
</html>
