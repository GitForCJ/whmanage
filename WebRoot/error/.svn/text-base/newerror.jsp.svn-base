<%@ page contentType="text/html; charset=utf-8" language="java"%>
<%
  String path = request.getContextPath();
%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>错误页面</title>
<link href="<%=path %>/css/style_new.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=path %>/js/jquery_new.js"></script>

<script language="javascript">
	$(function(){
    $('.error').css({'position':'absolute','left':($(window).width()-490)/2});
	$(window).resize(function(){  
    $('.error').css({'position':'absolute','left':($(window).width()-490)/2});
    })  
});  
</script> 


</head>


<body style="background:#edf6fa;">
	<div class="place">
    <span>位置：</span>
    <ul class="placeul">
    <li><a href="#">500错误提示</a></li>
    </ul>
    </div>
   <div class="error">
    
    <h2>非常遗憾，您访问的页面不存在！</h2>
    <p>看到这个提示，就自认倒霉吧!</p>
    <div class="reindex"><a href="javascript:window.history.go(-1)" target="_parent">返回上一步</a></div>
    
    </div>
</body>
</html>
