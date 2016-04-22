<%@ page contentType="text/html; charset=gb2312" language="java"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:useBean id="userSession" scope="session" class="com.wlt.webm.rights.form.SysUserForm" />
<jsp:useBean id="user" scope="page" class="com.wlt.webm.rights.bean.SysUser"/>
<%@ page import="java.util.*" %>
<%
	String path = request.getContextPath();
	if(null==userSession||userSession.getUsername()==null){
response.sendRedirect(path+"/rights/wltlogin.jsp");
}
  String userole=userSession.getUser_role();
  List menuList = user.getMenuList(userole);  //根据角色id获得菜单项 
  List firstMenuList = user.getChildMenuList(menuList,"0");   //一级菜单父节点为"0"
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>万汇通</title>
<script type="text/javascript" src="js/jquery.min.js"></script>
<link rel="stylesheet" type="text/css" href="css/css.css" />
<script type="text/javascript"> 
$(document).ready(function(){

	$("ul.sidenav li").hover(function() {
		$(this).find("div").stop()
		.animate({left: "210", opacity:1}, "fast")
		.css("display","block")

	}, function() {
		$(this).find("div").stop()
		.animate({left: "0", opacity: 0}, "fast")
	});
	
	
});
            function show(id) {
              var divs = $("div[class='container']");
              for (var i = 0; i < divs.length; i++) 
                  divs[i].style.display = divs[i].id == id ? 'block' : 'none';
          }
          
function IFrameResize() {
	var iframe = document.getElementById("myframe"); 
	var iframeDocument = null;
	//safari和chrome都是webkit内核的浏览器，但是webkit可以,chrome不可以
	if (iframe.contentDocument)
	{ 
		//ie 8,ff,opera,safari
		iframeDocument = iframe.contentDocument;
	} 
	else if (iframe.contentWindow) 
	{ 
		// for IE, 6 and 7:
		iframeDocument = iframe.contentWindow.document;
	} 
	if (!!iframeDocument) {
		iframe.width=iframeDocument.documentElement.scrollWidth+"px";
		iframe.height=iframeDocument.documentElement.scrollHeight+"px";		
	} else {
		alert("this browser doesn't seem to support the iframe document object");
	} 

} 

</script>
<style type="text/css">
body {
	background: white;
	font: 12px Verdana, Arial, Helvetica, sans-serif;
	margin: 0;
	padding: 0;
	color: black;
}
a:focus {
	outline: none;
}
* {margin: 0; padding: 0;}
h1 {
	font: 4.7em normal Georgia, 'Times New Roman', Times, serif;
	color: #fff;
	margin-bottom: 20px;
}
h1 small{
	font: 0.2em normal Verdana, Arial, Helvetica, sans-serif;
	text-transform:uppercase;
	letter-spacing: 1.5em;
	display: block;
	color: #33CC33;
}
h2 {font: 2em normal Georgia, 'Times New Roman', Times, serif;}

/*=========================================================================*/

.main{
	margin: 0 auto;
	width: 100%;
	color: white;
	height: 100%;
	overflow:hidden;
}

.one {
   position:relative;
	margin: 0 auto;
	margin-left:0px;
	width: 200px;
	background: #FFFFFF;
	overflow: hidden;
    float:left;
	border-right: 1px solid #0000CC;
	height: 100%;
}

.mainrt{
   position:relative;
	margin: 0 auto;
	margin-left:202px;
	width:auto;
	background-color: white;
	height: 100%;
}

.container0 {
	margin: 0 auto;
	width: 150px;
	background: #FFFFFF;
	overflow: hidden;
    float:left;
	/*padding: 20px;*/
}

.container {
	margin: 0 auto;
	width: 150px;
	background: #FFFFFF;
	overflow: hidden;
    float:left;
	/*padding: 20px;*/
}
.container1 {
	margin: 0 auto;
	width: 150px;
	background: #FFFFFF;
	overflow: hidden;
    float:left;
	/*padding: 20px;*/
}

.content {
	width: 639px;
	padding: 20px;
	margin-left: 20px;
	color: #black;
	font-size: 1.2em;
	float: left;
	border-left: 1px dashed #003867;
}
.content p {
	line-height: 1.6em;
	margin: 5px 0;
	padding: 5px 0;
}
ul.sidenav {
	float: left;
	margin: 10px 0 0;
	padding: 0;
	width: 200px;
	list-style: none;
	border-bottom: 1px solid #FFFFFF;
	border-top: 1px solid #FFFFFF;
	font-size: 1.2em;
}
ul.sidenav li {
	position: relative;
	float: left;
	margin: 0;
	padding: 0;
    border-bottom: 1px dashed #00CCFF;
}
ul.sidenav li a{
	border-top: 1px solid #FFFFFF;
	border-bottom: 1px solid #FFFFFF;
	padding: 10px 10px 10px 25px;
	display: block;
	color: #000000;
	text-decoration: none;
	width: 165px;
	background: #FFFFFF url(image/sidenav_li_a.gif) no-repeat 5px 10px;
	position: relative;
	z-index: 2;
}
ul.sidenav li a:hover {
	background-color: #FFFFCC;
	border-bottom: 1px solid #1a4c76;
}
ul.sidenav li div {
	display: none;
	position: absolute;
	top: 2px;
	left: 0;
	width: 225px;
	font-size: 0.9em;
	/*background: url(image/bubble_top.gif) no-repeat right top;*/
}
ul.sidenav li div p {
	margin: 7px 0;
	line-height: 1.6em;
	padding: 0 5px 10px 30px;
	/*background: url(image/bubble_btm.gif) no-repeat right bottom;*/
}
ul.sidenav li a.treetop{
  padding-top:13px;
   background: #3300CC url(image/1111.png) ;width:165px;height:30px ;
    color: white;
    height: 25px;
    font-size: 1.6em;
    font-family:Georgia;
    vertical-align: middle;
    border:none;
}

ul.sidenav li a.treebottom{
  padding-top:13px;
   background: #3300CC url(image/1111.png) ;width:165px;height:30px ;
    color: white;
    height: 25px;
    font-family:Georgia;
    font-size: 1.6em;
    border-bottom:1px solid #00CCFF;
}
</style>
</head>
<body>
<div style="background-color: #0078B6;height:30px;color:white;font-size: 18px;padding-top: 10px;">
<table width="100%">
<tr>
<td align="left" width="50%">${userSession.username}您好,欢迎使用万汇通</td>
<td align="right" width="50%"><a href="<%=path %>/rights/sysuser.do?method=logout" style="color:white;text-decoration: none;">安全退出</a></td>
</tr>
</table>
</div>
<div id="vdividermenu">
<ul>
<li><a href="../business/cmccbusiness.jsp" target="myframe" title="首页">首页</a></li>
<% for(int i=0;i<firstMenuList.size();i++){
    String[] firstMenu = (String[]) firstMenuList.get(i);%>
<li>
<a id="<%=firstMenu[0]%>"  href="javascript:show('<%=firstMenu[0]%>')" ><%=firstMenu[1]%></a>
</li>
<% } %>
</ul>
</div>


<div class="main">




<div class="one">





<% for(int i=0;i<firstMenuList.size();i++){ 
    String[] firstMenu = (String[]) firstMenuList.get(i);
     if(i==0){ %>
<div class="container" id="<%=firstMenu[0]%>" >
<%}else { %>
<div class="container" id="<%=firstMenu[0]%>" style="display: none" >
<%} %>
  <ul class="sidenav">
    <li> <a href="#" class="treetop"><%=firstMenu[1]%></a>
    </li>
       <% 
      List twoMenuList = user.getChildMenuList(menuList,firstMenu[0]);    //二级菜单项
      for(int j=0;j<twoMenuList.size();j++){
        String[] twoMenu = (String[]) twoMenuList.get(j);
    	%>
    <li> <a href="..<%=twoMenu[2]%>" target="myframe"><%=twoMenu[1]%></a></li>
<%} %>
  </ul>
</div>
<%} %>







<div class="container1">
  <ul class="sidenav">
    <li> <a href="#" class="treebottom">安全中心</a>
      <div>
        <p></p>
      </div>
    </li>
        <li> <a href="<%=path %>/rights/sysuser.do?method=logout">安全退出</a>
    </li>
    <li> <a href="#">安全说明</a>
    </li>
  </ul>
</div>

</div>



<div class="mainrt" >
<iframe src="<%=path %>/rights/wltwelcome.jsp" frameborder="0" marginheight="0" marginwidth="0" frameborder="0" scrolling="no" id="myframe" name="myframe" onload="IFrameResize();" width="100%" height="100%" > 
</iframe> 
</div>

</div>



<div style="background-color: white;text-align: center;font-size: 15px;margin-top: 100px;color: gray; ">
<hr/>
<ul>
<li>版权所有：深圳市万恒科技有限公司 ICP证:粤ICP备11104967-5</li>
<li>如有疑问请联系:4006-220-216</li>
</ul>
</div>


</body>
</html>
