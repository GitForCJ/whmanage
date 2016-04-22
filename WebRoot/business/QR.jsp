<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<jsp:useBean id="bipord" scope="page" class="com.wlt.webm.business.bean.BiProd"/>
<jsp:useBean id="des" scope="page" class="com.wlt.webm.tool.DES"/>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
des.setKey("wh#@!a59");
String id=request.getParameter("id");
System.out.println(id);
String[] strs=bipord.AppUserInfos(Integer.parseInt(id.trim()));

%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <META http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta id="viewport" name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0"/>
<meta content="telephone=no" name="format-detection" />
    <title>用户信息</title>
<style>
*{ margin:0; padding:0;}
html{_background-image:url(about:blank);_background-attachment:fixed;}
html, body{width:100%; height:100%;background-color:#4eadfd;}

.content p{
	width:90%;
	color:#fff;
	height:40px;
	line-height:40px;
	margin:20px auto;
	font-size:16px;
}

.content .span1{text-align:right;width:35%; display:inline-block;}

.content .span2{ border-bottom:1px #fff solid; display:inline-block; width:64%;}

.content p a{
	width:50%;
	height:100%;
	display:inline-block;
	color:#fff;
	text-align:center;
	line-height:40px;
	background-color:#279cff;
	text-decoration:none;
	font-size:18px;
	margin-left:25%;
	border-top:1px #fff solid;
	border-left:1px #fff solid;
	border-bottom:1px #006bc5 solid;
	border-right:1px #006bc5 solid;
}

</style>
</head>

<body>
<div class="content">
		<p><span class="span1">姓名：</span><span class="span2"><%=strs[0]%></span></p>
	    <p><span class="span1">公司：</span><span class="span2"><%=strs[1]%></span></p>
	    <p><span class="span1">职位：</span><span class="span2"><%=strs[2]%></span></p>
	    <p><span class="span1">扣扣：</span><span class="span2"><%=strs[6]%></span></p>
	    <p><span class="span1">邮箱：</span><span class="span2"><%=strs[8]%></span></p>
	    <p><span class="span1">手机：</span><span class="span2"><%=strs[3]%>&nbsp;</span></p>
	    <p><span class="span1">座机：</span><span class="span2"><%=strs[4]%></span></p>
	    <p><span class="span1">备注：</span><span class="span2"><%=strs[10]%></span></p>
	    <p><span class="span1">公司地址：</span><span class="span2"><%=strs[5]%></span></p>
	    <p><a href="<%=path %>/business/QR1.jsp?id=<%=id %>">保存到手机</a></p>
	    <p><span class="span1">&nbsp;</span><span class="span2" style="border:none;">&nbsp;</span></p>
</div>
</body>
</html>
