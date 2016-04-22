<%@ page contentType="text/html; charset=utf-8" language="java"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:useBean id="userSession" scope="session" class="com.wlt.webm.rights.form.SysUserForm" />
<jsp:useBean id="user" scope="page" class="com.wlt.webm.rights.bean.SysUser"/>
<%@ page import="java.util.*" %>
<%
	String path = request.getContextPath();
  String userole=userSession.getUser_role();
  List menuList = user.getMenuList(userole);  //根据角色id获得菜单项 
  List firstMenuList = user.getChildMenuList(menuList,"0");   //一级菜单父节点为"0"
  
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>万汇通管理平台</title>
<link rel="shortcut icon" type="image/x-icon" href="<%=path %>/favicon.ico" />
<script type="text/javascript" src="../js/jquery.js"></script>
<script type="text/javascript" src="../js/side_menu.js"></script>
<script type="text/javascript" src="../js/jquery-1.8.2.min.js"></script>
<link href="../css/style.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">


function checkBS(vv)
{
	if(vv.indexOf('acctbill')>=0||vv.indexOf('order')>=0)
	{
		$("#a_href").val("1");
	}
	else
	{
		$("#a_href").val("0");
	}	
}
$(window).scroll(function () 
{       
	var aa = $("#a_href").val();
	if(aa==1)
	{
	  	var hh = $(document).scrollTop();
		$("#panl").height($(window).height()-180+hh);
	  	var phh = $("#panl").height();
	  	if(phh>1000)
	  	{
	  		$("#panl").height(1000);
	  	}
	}
});
function iframeAutoFit(obj)
{ 
	var aa = $("#a_href").val();
	var hh = $(document).scrollTop();
	//alert(hh);
	if(aa==1)
	{
		$(obj).height($(window).height()-180+hh);
	}
}

//window.setInterval(yueshow(),5000);

</script>

<style type="text/css">
<!--
#nav {
width:175px;
line-height: 24px;
list-style-type: none;
text-align:left;
/*定义整个ul菜单的行高和背景色*/
}

/*==================一级目录===================*/
#nav a {
width: 175px;
display: block;
padding-left:20px;
/*Width(一定要)，否则下面的Li会变形*/
}

#nav li {
border-bottom:#FFF 1px solid; /*下面的一条白边*/
float:left;
background-color: #FECFD6;
color:#3366FF;
font-weight:bold;
font-size: 18px;
background: url(../images/dot_o.gif) no-repeat 8px 8px;
}

#nav li a:hover{
background:#FFFFFF; /*一级目录onMouseOver显示的背景色*/
color:#000000;
}

#nav a:link {
color:#DD1336; text-decoration:none;
}

#nav a:visited {
color:#DD1336;text-decoration:none;
}

#nav a:hover {
color:#FFF;text-decoration:none;font-weight:bold;
}

/*==================二级目录===================*/

#nav li ul {
list-style:none;
text-align:left;
}

#nav li ul li{
font-size: 15px;
background: #FFFFFF; /*二级目录的背景色*/
font-weight:normal;
}

#nav li ul a{
padding-left:35px;
width:175px;
/* padding-left二级目录中文字向右移动，但Width必须重新设置=(总宽度-padding-left)*/

}

/*下面是二级目录的链接样式*/
#nav li ul a:link {
color:#666; text-decoration:none;
}

#nav li ul a:visited {
color:#666;text-decoration:none;
}

#nav li ul a:hover {
color:#000000;
text-decoration:none;
font-weight:normal;
background:#FFFFFF;
/* 二级onmouseover的字体颜色、背景色*/

}

/*==============================*/

#nav li:hover ul {
left: auto;
}

#nav li.sfhover ul {
left: auto;
}

#content {
clear: left;
}

#nav ul.collapsed {
display: none;
}

-->
</style>

    <script type=text/javascript><!--

var LastLeftID = "";

function menuFix() {

var obj = document.getElementById("nav").getElementsByTagName("li");

for (var i=0; i<obj.length; i++) {

   obj[i].onmouseover=function() {

    this.className+=(this.className.length>0? " ": "") + "sfhover";

   }

   obj[i].onMouseDown=function() {

    this.className+=(this.className.length>0? " ": "") + "sfhover";

   }

   obj[i].onMouseUp=function() {

    this.className+=(this.className.length>0? " ": "") + "sfhover";

   }

   obj[i].onmouseout=function() {

    this.className=this.className.replace(new RegExp("( ?|^)sfhover\\b"), "");

   }

}

}

function DoMenu(emid)

{

var obj = document.getElementById(emid);

obj.className = (obj.className.toLowerCase() == "expanded"?"collapsed":"expanded");

if((LastLeftID!="")&&(emid!=LastLeftID)) //关闭上一个Menu

{

   document.getElementById(LastLeftID).className = "collapsed";

}

LastLeftID = emid;

}

function GetMenuID()

{

var MenuID="";

var _paramStr = new String(window.location.href);

var _sharpPos = _paramStr.indexOf("#");

if (_sharpPos >= 0 && _sharpPos < _paramStr.length - 1)

{

   _paramStr = _paramStr.substring(_sharpPos + 1, _paramStr.length);

}

else

{

   _paramStr = "";

}

if (_paramStr.length > 0)

{

   var _paramArr = _paramStr.split("&");

   if (_paramArr.length>0)

   {

    var _paramKeyVal = _paramArr[0].split("=");

    if (_paramKeyVal.length>0)

    {

     MenuID = _paramKeyVal[1];

    }

   }

   /*

   if (_paramArr.length>0)

   {

    var _arr = new Array(_paramArr.length);

   }

   //取所有#后面的，菜单只需用到Menu

   //for (var i = 0; i < _paramArr.length; i++)

   {

    var _paramKeyVal = _paramArr[i].split('=');

  

    if (_paramKeyVal.length>0)

    {

     _arr[_paramKeyVal[0]] = _paramKeyVal[1];

    }

   }

   */

}

if(MenuID!="")

{

   DoMenu(MenuID)

}

}

GetMenuID(); //*这两个function的顺序要注意一下，不然在Firefox里GetMenuID()不起效果

menuFix();

--></script>
</head>

<body>
<!--头部-->
<div id="top_home">
  <div id="top_home_main">
	<div class="top_home_left">
    	<ul>
        	<li class="top_home"><a href="<%=path %>/rights/wltframe.jsp">登陆首页</a></li>
         </ul>
     </div>
    <div class="top_home_right">
    	<ul style="float:left;">
		<span>${userSession.userename }</span>&nbsp;您好!欢迎使用&nbsp;&nbsp;&nbsp;<a href="<%=path %>/rights/sysuser.do?method=logout">退出</a></li>
      </ul>
     </div>
 </div>
</div>
<!--logo&nav-->
<div id="header_main">
  <div id="header">
    <div class="logo"><a href="#"><img src="../images/logo.png" width="303" height="77" /></a></div>
    <div style=" float:left; height:25px; font-size:14px; width:670px; margin:40px auto;">
</div>
  </div>
</div>
<!--主体-->
<div id="content">







   <!--侧导航-->
<div id="sidebar" style="background-color:#F5F5F5; color:#0000CC">
<ul id="nav"><!-- class=tj_submain -->
<% for(int i=0;i<firstMenuList.size();i++){ 
    String menuNum="ChildMenu"+i;
    String[] firstMenu = (String[]) firstMenuList.get(i);%>
  <li id="lst">
  <a id="<%=firstMenu[0]%> href="..<%=firstMenu[2]%>" target="content_right" onClick="DoMenu('<%=menuNum %>')"><img src="../images/onemenu.png" width="15" height="15"><%=firstMenu[1]%>&nbsp;&nbsp;></a> 
      <% if(firstMenu.length>0){%>
       <ul id="<%=menuNum %>" class="collapsed">
       <% 
      List twoMenuList = user.getChildMenuList(menuList,firstMenu[0]);    //二级菜单项
     
      for(int j=0;j<twoMenuList.size();j++){
        String[] twoMenu = (String[]) twoMenuList.get(j);
        System.out.println("twoMenu[0]="+twoMenu[0]);
    	%>
      	<li><a id="<%=twoMenu[0]%>" target="content_right" name="cc" href="..<%=twoMenu[2]%>" onclick="javacript:checkBS(this.href);"><img src="../images/twomenu.png" width="12" height="12"><%=twoMenu[1]%></a></li>
     <%
      }
    %>
      </ul>
       <% }%>
  </li>
  <%} %>
    </ul>
  </div>






  <input type="hidden" id="a_href" value='0'/>
  <input type="hidden" id="userd" value="${userSession.user_id }"/>
  <!--iframe内容-->
  <div id="main_con"><iframe frameborder="0" id="panl" height="100%" width="100%" name="content_right" scrolling="auto" src="<%=path %>/rights/wltwelcome.jsp" onload="javascript:iframeAutoFit(this);">您的浏览器版本不支持iframe，请升级或者跟换其他浏览器访问！</iframe></div>
<!--footer-->
<div id="footer2">万汇通请使用IE 6.0或以上版本  最佳分辨率 1024 * 768<br />统一客服电话：4000-***</div>
</div>
</body>
</html>
