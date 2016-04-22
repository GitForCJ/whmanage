<%@ page contentType="text/html; charset=utf-8" language="java"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<jsp:useBean id="userSession" scope="session" class="com.wlt.webm.rights.form.SysUserForm" />
<jsp:useBean id="user" scope="page" class="com.wlt.webm.rights.bean.SysUser"/>
<%@ page import="java.util.*" %>

<%
	String path = request.getContextPath();
	if(null==userSession||userSession.getUsername()==null){
       response.sendRedirect(path+"/rights/wltlogin.jsp");
}
  double yu=Double.valueOf(user.getUseLeft(userSession.getUserno()));
  String userole=userSession.getUser_role();
  String userlogin1=userSession.getExp1();
  List menuList = user.getMenuList(userole);  //根据角色id获得菜单项 
  if(null!=userlogin1&&user.getUser(userlogin1)){
  for(int i=0;i<menuList.size();i++){
  String[] str=(String[])menuList.get(i);
  if(str[0].equals("0101")){
  menuList.remove(i);
  }
  }
  }
  List firstMenuList = user.getChildMenuList(menuList,"0");   //一级菜单父节点为"0"
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>万汇通欢迎您</title>
<link rel="shortcut icon" type="image/x-icon" href="<%=path %>/favicon.ico" />
<link href="css/member.css" rel="stylesheet" type="text/css"></link>
<style type="text/css">
	li img:hover{box-shadow: 0 0 3px #000;}
</style>
<script type="text/javascript" src="js/jquery.min.js"></script>
<script type="text/javascript" src="<%=path %>/js/rightMessageAlert.js"></script>
<script type="text/javascript"> 
history.go(1);

var preClassName = "man_nav_a";
function list_sub_nav(Id,sortname){

           $(".menu li").each(function() {
                    if($(this).attr("id")==Id){
                    $(this).addClass("on");
                    }else{
                    $(this).removeClass("on");
                    }
            });  
			 if(Id=="man_nav_a")
			 {
			      var a=document.getElementById("usertype").value;
			      if(a==3)
			      {
			      	if("${sessionScope.userSession.usersite}"==382)
			      	{
			      		document.getElementById("myframe").src="wltwelcome3.jsp";
			      	}
			      	else
			      	{
             	     document.getElementById("myframe").src="wltwelcome.jsp";
             	    }
                  }else
                  {
                  	if("${sessionScope.userSession.usersite}"==382)
			      	{
			      		document.getElementById("myframe").src="wltwelcome3.jsp";
			      	}
			      	else
			      	{
               	  	 	document.getElementById("myframe").src="wltwelcome1.jsp";
               	   	}
                  }
            }
     // getObject(Id).className="on";
}

function getObject(objectId) {
    if(document.getElementById && document.getElementById(objectId)) {
	// W3C DOM
	return document.getElementById(objectId);
    } else if (document.all && document.all(objectId)) {
	// MSIE 4 DOM
	return document.all(objectId);
    } else if (document.layers && document.layers[objectId]) {
	// NN 4 DOM.. note: this won't find nested layers
	return document.layers[objectId];
    } else {
	return false;
    }
}

  function show(id) {
              var divs = $("div[class='menu-bd']");
              for (var i = 0; i < divs.length; i++) {
                  divs[i].style.display = divs[i].id == id ? 'block' : 'none';
                  }
              var arr=document.getElementById(id+"_ul").getElementsByTagName("a");
               document.getElementById("myframe").src=arr[0].href;
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
	}else {
	iframeDocument = iframe.contentWindow.document.body;
	} 
	if (!!iframeDocument) {
		iframe.width=iframeDocument.documentElement.scrollWidth+"px";
		iframe.height=iframeDocument.documentElement.scrollHeight+"px";		
	} else {
		alert("this browser doesn't seem to support the iframe document object");
	} 

} 
//-------------------------------------
function dyniframesize(down) { 
var pTar = null; 
if (document.getElementById){ 
pTar = document.getElementById(down); 
} 
else{ 
eval('pTar = ' + down + ';'); 
} 
if (pTar && !window.opera){ 
//begin resizing iframe
pTar.style.display="block" 
if (pTar.contentDocument && pTar.contentDocument.body.offsetHeight){ 
//ns6 syntax 
pTar.height = pTar.contentDocument.body.offsetHeight +20; 
pTar.width = pTar.contentDocument.body.scrollWidth; 
} 
else if (pTar.Document && pTar.Document.body.scrollHeight){ 
//ie5+ syntax 
pTar.height = pTar.Document.body.scrollHeight; 
pTar.width = pTar.Document.body.scrollWidth; 
} 
} 
	
}
//=====
function iFrameHeight() { 
	var ifm= document.getElementById("myframe"); 
	    	if($.browser.msie) { 
	    		var userAgent = window.navigator.userAgent.toLowerCase();
				$.browser.msie10 = $.browser.msie && /msie 10\.0/i.test(userAgent);
				$.browser.msie9 = $.browser.msie && /msie 9\.0/i.test(userAgent); 
				$.browser.msie8 = $.browser.msie && /msie 8\.0/i.test(userAgent);
				$.browser.msie7 = $.browser.msie && /msie 7\.0/i.test(userAgent);
				$.browser.msie6 = !$.browser.msie8 && !$.browser.msie7 && $.browser.msie
				if($.browser.msie10){
					//ifm.style.minHeight=600px;
					var subWeb = document.frames ? document.frames["myframe"].document : ifm.contentDocument; 
					if(ifm != null && subWeb != null) { 
						ifm.height = subWeb.body.scrollHeight;
					}
					if(ifm.height<600){
							ifm.height =600;
					}
				}else{
					var subWeb = document.frames ? document.frames["myframe"].document : ifm.contentDocument; 
					if(ifm != null && subWeb != null) { 
						ifm.height = subWeb.body.scrollHeight;
					}
					if(ifm.height<600){
							ifm.height =600;
					}
				}
		
			} 
			else if($.browser.safari) 
			{ 
				$("#myframe").height(100);
				var height=ifm.contentDocument.body.offsetHeight;
				$("#myframe").height(height);
				if($("#myframe").height()<600){
						$("#myframe").height(600);
					}
			} 
			else if($.browser.mozilla) 
			{ 
				ifm.height = ifm.contentDocument.body.offsetHeight ;
				if(ifm.height<600){
						ifm.height =600;
				}
			} 
			else if($.browser.opera) { 
				$("#myframe").height(100);
				var height=ifm.contentDocument.body.offsetHeight;
				$("#myframe").height(height);
				if($("#myframe").height()<600){
						$("#myframe").height(600);
					}
			} 
			else if ($.browser.mozilla && $.browser.version == '11.0')
			{
				$("#myframe").height(100);
           		var bHeight = ifm.contentWindow.document.body.scrollHeight;
          		$("#myframe").height(bHeight);
	            if($("#myframe").height()<600)
	            {
					$("#myframe").height(600);
				}
           
			}
			else { 
				alert("不能识别的浏览器"); 
				if($("#myframe").height()<600){
						$("#myframe").height(600);
					}
			}
			
		
}
//=====
function chayue(){
var username =document.getElementById("userno").value;
$.post(
	    	$("#rootPath").val()+"/util/MoneyServlet",
	      	{
	      		userID : username
	      	},
	      	function (data){
	      		$("#ed").html("");
	      		$("#ed").html(data);
	      	}
		);
}

function ajax_Money(){
$("#moneys_liantong").html("<img src='<%=path%>/images/loading_16.gif'/>");
	$.post(
	   	$("#rootPath").val()+"/dianxin/telecom.do?method=Query_Money",
	     	{ },
	     	function (data){
	     		$("#moneys_liantong").html("");
	     		$("#moneys_liantong").html(data);
	     	}
	);
}

// 点击关闭遮罩层 事件 
function funClose()
{
	document.getElementById("zzc").style.display="none";
	document.getElementById("zzcTwo").style.display="none";
	document.body.style.overflowX="visible";
	document.body.style.overflowY="visible";
	document.getElementById('myframe').src=document.getElementById('responseUrl').value;//重定向 ifream url
}

window.onload=function()
{
	$.post(
    	"<%=path%>/AccountInfo/showAccountInfo.do?method=showMessage",
      	{
      	},
      	function (data){
      		if(data==1){
      			return ;
      		}
      		
				///alert(data[i][0]);
			
      		var _str="<div id='messageAlert' style='border:1px solid #cccccc;width:270px;height:130px;display:none;box-shadow: 0 0 8px #000;border-radius:10px;background-color:white;'>";
			_str=_str+"<ul style='margin:0px 0px 0px 0px;padding:0px 0px 0px 0px;'>";
			_str=_str+"<li style='box-shadow: 0 0 0px #000;border-top-left-radius:10px;border-top-right-radius:10px;background-color:#e5e9f3;width:100%;height:30px;margin:0px 0px 0px 0px;padding:0px 0px 0px 0px;line-height:30px;text-align:right;'><div style='padding-left:10px;font-weight:bold;font-size:13px;width:180px;text-align:left;height:30px;float:left;'>最新公告</div><img  onclick='funRightMessageAlertNone()' src='../images/tipno.png' style='height:20px;width:20px;float:right;margin-top:5px;margin-right:10px;cursor:pointer'/></li>";
			for(var i=0;i<data.length;i++)
			{
				_str=_str+"<li style='border-bottom:1px dashed red;width:250px;height:30px;margin:0px 0px 0px 0px;margin-left:5px;padding:0px 0px 0px 0px;line-height:30px;padding-left:10px;'>";
				_str=_str+"<a  href='#' onclick='tagit("+data[i][0]+")' style='float:left;'>";
				_str=_str+"<img  src='<%=path %>/images/lb.png'/>&nbsp;";
				_str=_str+data[i][1];
				_str=_str+"&nbsp;<img  src='<%=path %>/images/add.gif'/>";
				_str=_str+"</a>";
				_str=_str+"<div style='float:right;'><a  href='#' onclick='tagit("+data[i][0]+")'>"+data[i][2]+"</a></div>";
				_str=_str+"</li>";
			}
			_str=_str+"</ul>";
			_str=_str+"</div>";
			document.body.innerHTML=document.body.innerHTML+_str;
			showMessageInfo();
			return ;
      	},"json"
	)
	if("${sessionScope.userSession.roleType}"=="0"){
		ajax_Money();
	}
}
function tagit(id){
	document.getElementsByName("myframe")[0].height="500px";
	document.getElementsByName("myframe")[0].src="<%=path %>/rights/wltnoticeinfo.jsp?uid="+id;
	return;
}

function show_moneys_openation(th){
	if(th.innerHTML=="显示余额"){
		document.getElementById("hidden_money").style.display="";
		document.getElementById("hidden_money_openation").style.display="";
		th.innerHTML="隐藏余额";
	}else{
		chayue();
		document.getElementById("hidden_money").style.display="none";
		document.getElementById("hidden_money_openation").style.display="none";
		th.innerHTML="显示余额";
	}
}
</script>
</head>

<body style="z-index:1;">
<input type="text" style="display:none" value="" id="responseUrl"/>
<div id="zzc" style="display:none;position:absolute;left:0px;Z-INDEX:9;width:100%;height:1000px;background:#000;opacity:0.5;">
</div>
<div  id="zzcTwo" style="display:none;width:500px;height:200px;background-color:white;position:absolute;Z-INDEX:9999;opacity:1;">
	<div class="rcontent" style="width: auto;height:99%">
<table  style="height:98%">
<thead  style="height:25px;">
<tr >
<th>温馨提示</th>
</tr>
</thead>
<tbody >
<tr>
<td align="center" >
<br/>
<img src="../images/show.png" style="width:81;height:64px;"/>&nbsp;<span style="font-size: 13px; color: #FF6600;font-weight:bold;" id="wltfarmeshowText">请在新打开的页面完成支付。</span>
<br />
<br />
	<div style="float:right;">
	 <input type="button" id="wltfarmebuttonvalue" name="Button" value="已完成支付" style="border:0px; width:85px; height:32px; color:#fff; font-weight:bold; background:url(../images/button_out.png) no-repeat bottom; cursor:pointer;" onclick="funClose()"/>
&nbsp;
	<input type="button" name="Button" value="&nbsp;关&nbsp;&nbsp;闭&nbsp;" style="border:0px; width:85px; height:32px; color:#fff; font-weight:bold; background:url(../images/button_out.png) no-repeat bottom; cursor:pointer;" onclick="funClose()"/>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
</div>
</td>
</tr>
</tbody>
</table>
</div>
</div>

<input type="hidden" id="rootPath" value="<%=path %>"/>
<input type="hidden" id="userno" value="${userSession.userno }"/>
<input type="hidden" id="usertype" value="${sessionScope.userSession.roleType }"/>
<div class="member_head">
  <div class="w990">
    <div class="welcome_txt" style="color: #0072b8;"><a href="#"></a> 欢迎您,${userSession.userename}&nbsp;&nbsp;[${fn:substring(userSession.username, 0, 3)}****${fn:substring(userSession.username, 7, 11)}]</div>
    <div class="head_link">
      <ul>
      	<c:if test="${sessionScope.userSession.roleType == 0}">
	      	<li>(省联通余额：<span id="moneys_liantong" style="color:#0072b8">0.0元</span></li>
	        <li class="topnav-pipe">|</li>
	        <li><a href="#" onclick="ajax_Money()">刷新余额)</a></li>
	        <li class="topnav-pipe">|</li>
        </c:if>
        <li id="hidden_money" style="display:none;">(余额：<span id="ed" style="color:#0072b8"><%=yu %>元</span></li>
        <li class="topnav-pipe">|</li>
        <li  id="hidden_money_openation" style="display:none;"><a href="#" onclick="chayue();">刷新余额)</a></li>
        <li class="topnav-pipe">|</li>
         <li><a href="#" onclick="show_moneys_openation(this);">显示余额</a></li>
         <li class="topnav-pipe">|</li>
        <li><a href="<%=path %>/rights/sysuser.do?method=logout">安全退出</a></li>
      </ul>
    </div>
  </div>
</div>
<div class="header">
  <div class="head-wrap">
    <div class="logo">
      <h1><a title="万汇通服务平台" href="#"></a></h1>
    </div>
    <div class="m_ad">
    <a href="tdyh.html" target="_blank"><img src="images/m_ad1.jpg" width="716" height="51" /></a></div>
  </div>
  
  <div id="header">
<div class="blank9 clearFloat"></div>
<div class="navbar">
<div class="barbg1">
<div class="barbg2">
<UL class="menu">
  <LI class="on" id="man_nav_a" onclick="list_sub_nav(id,'首页')" >
  <H3><A id="shouye"  href="javascript:show('shouye')" >首&nbsp;页</A></H3></LI>
<% for(int i=0;i<firstMenuList.size();i++){
    String[] firstMenu = (String[]) firstMenuList.get(i);%>

  <LI id="man_nav_<%=i %>" onclick="list_sub_nav(id,'<%=firstMenu[1]%>')">
  <H3>
<a id="<%=firstMenu[0]%>"  href="javascript:show('<%=firstMenu[0]%>')" ><%=firstMenu[1]%></a>
</H3>
</LI>
 <% } %>
</UL>

</div></div></div></div>
</div>

<div class="page">
  <div class="menu_left">
  
  
  

<div class="menu-bd" id="shouye">
<div class="menu-box">
<!--  
<h3  class="myli-bar" >产品服务</h3> 

  <ul class="group"  >
  <li ><a href="<%=path %>/business/cmccbusiness.jsp" target="myframe">全国充值</a></li>
  <li ><a href="<%=path %>/telcom/telcomPhonepay.jsp" target="myframe">广东电信</a></li>
  <li ><a href="<%=path %>/wlttencent/qbpay.jsp" target="myframe">Q币充值</a></li>
  </ul>
-->
  </div>
</div>

<% for(int i=0;i<firstMenuList.size();i++){ 
    String[] firstMenu = (String[]) firstMenuList.get(i);
     %>
<div class="menu-bd" id="<%=firstMenu[0]%>" style="display: none;">
<div class="menu-box">
<h3  class="myli-bar" ><%=firstMenu[1]%></h3> 

  <ul class="group" id="<%=firstMenu[0]%>_ul" >
  <% 
      List twoMenuList = user.getChildMenuList(menuList,firstMenu[0]);    //二级菜单项
      for(int j=0;j<twoMenuList.size();j++){
        String[] twoMenu = (String[]) twoMenuList.get(j);
    	%>
  <li><a href="..<%=twoMenu[2]%>" target="myframe"><%=twoMenu[1]%></a></li>
<%} %>
  </ul>
  </div>
</div>
<%} %>







<!--  
  <div class="menu-bd1" >
  <div class="menu-box">
  <h3  class="myli-bar" >帮助中心</h3> 
  <ul class="group"  >
  <li ><a href="#">1.如何话费充值</a></li>
  <li ><a href="#">2.充值全国话费多少</a></li>
  <li ><a href="#">3.游戏充值100元</a></li>
  </ul></div>
 </div> 
-->


   <div class="menu-bd1" >
  <div class="menu-box">
  <h3  class="myli-bar" >客服中心</h3> 
  <ul class="group1">
	<li style="height:130px;">
<img  style="CURSOR: pointer;border:1px solid #cccccc;width:120px;height:120px;" onclick="javascript:window.open('http://b.qq.com/webc.htm?new=0&sid=4006220216&eid=218808P8z8p8p8R808Q8z&o=www.wanhuipay.com&q=7&ref='+document.location, '_blank', 'height=544, width=644,toolbar=no,scrollbars=no,menubar=no,status=no');"  border="0" src="<%=path %>/rights/img/qq.jpg" />
</li>
	
 <li ><font size="2.5"><b>客服热线:</b></font></li>

 <li><b>400-9923-988</b></li>
  </ul></div>
 </div> 
 
 </div>
 
  
<div class="content_right">
<c:if test="${sessionScope.userSession.roleType == 3 && sessionScope.userSession.usersite!=382}">
<iframe class="okiframe" src="<%=path %>/rights/wltwelcome.jsp" frameborder="0" marginheight="0" marginwidth="0" frameborder="0" scrolling="no" id="myframe" name="myframe" onload="iFrameHeight();" width="100%" height="100%" > 
</iframe> 
</c:if>

<c:if test="${sessionScope.userSession.roleType == 3 && sessionScope.userSession.usersite==382}">
<iframe class="okiframe" src="<%=path %>/rights/wltwelcome3.jsp" frameborder="0" marginheight="0" marginwidth="0" frameborder="0" scrolling="no" id="myframe" name="myframe" onload="iFrameHeight();" width="100%" height="100%" > 
</iframe> 
</c:if>

<c:if test="${sessionScope.userSession.roleType != 3 && sessionScope.userSession.usersite!=382}">
<iframe class="okiframe" src="<%=path %>/rights/wltwelcome1.jsp" frameborder="0" marginheight="0" marginwidth="0" frameborder="0" scrolling="no" id="myframe" name="myframe"  width="100%" onload="iFrameHeight();" height="100%" > 
</iframe>
</c:if>

<c:if test="${sessionScope.userSession.roleType != 3 && sessionScope.userSession.usersite==382}">
<iframe class="okiframe" src="<%=path %>/rights/wltwelcome3.jsp" frameborder="0" marginheight="0" marginwidth="0" frameborder="0" scrolling="no" id="myframe" name="myframe" onload="iFrameHeight();" width="100%" height="100%" > 
</iframe> 
</c:if>

</div>
<div  class="footer">
<p>招商加盟:400-9923-988&nbsp;&nbsp;|&nbsp;&nbsp;<a href="xieyi.html" target="_blank">服务协议</a>&nbsp;&nbsp;|&nbsp;&nbsp;<a href="http://www.wanhengtech.com" target="_blank">关于我们</a>&nbsp;&nbsp;|&nbsp;&nbsp;<a href="http://www.miitbeian.gov.cn/" target="_blank">粤ICP备11104967-5</a> </p>
<p>Copyright© 2013-2015 wanhengtech, All Rights Reserved</p>
<p>深圳市万恒科技有限公司&nbsp;&nbsp;版权所有</p>
</div>
</body>
</html>

