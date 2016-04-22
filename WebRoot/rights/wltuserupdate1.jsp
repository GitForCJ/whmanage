<%@ page contentType="text/html; charset=gbk" language="java"%>
<%@page import="java.util.List"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<jsp:useBean id="userSession" scope="session" class="com.wlt.webm.rights.form.SysUserForm" />
<jsp:useBean id="user" scope="page" class="com.wlt.webm.rights.bean.SysUser"/>
<jsp:useBean id="role" scope="page" class="com.wlt.webm.rights.bean.SysRole"/>
 <%
 	 String path = request.getContextPath();
	 String userid=request.getParameter("uid");
	 String ph=request.getParameter("ph");
   	 request.setAttribute("userinfo", user.getUserInfo(userid));
  	 List areaList = role.getAreaList();
	 StringBuffer sBuffer = new StringBuffer();
	 sBuffer.append("请选择[]");
	 for(Object tmp : areaList){
		String[] temp = (String[])tmp;
		sBuffer.append("|"+temp[1]+"["+temp[0]+"]");
	 }
	 request.setAttribute("areaSel", sBuffer.toString());
 %>
 <html>
<head>
<title>万汇通</title>
<script type="text/javascript" src="../js/jquery-1.8.2.min.js"></script>
<link href="../css/main_style.css" rel="stylesheet" type="text/css" />
<SCRIPT src="../js/more_tiaojian.js" type=text/javascript></SCRIPT>
<link href="../css/selectCss.css" rel="stylesheet" type="text/css" /> 
<script src="../js/selectCss.js" type="text/javascript"></script> 
<script language="javascript" type="text/javascript" src="../My97DatePicker/WdatePicker.js"></script>
<SCRIPT type=text/javascript>
jQuery(function(){
	$("#changeCity").click(function(a){$("#allCity").slideDown(0);a.stopPropagation();$(this).blur()});$("#allCity").click(function(a){a.stopPropagation()});$(document).click(function(a){a.button!=2&&$("#allCity").slideUp(0)});$("#foldin").click(function(){$("#allCity").slideUp(0)})
	if($("#mess").val() != ""){
		alert($("#mess").val());
	}
});

$(function(){
	$.ajax({
		type:"post",
		url:"<%=path%>/branchManage/userInfoA.do?method=getFatherUser&userno=<%=userid%>",
		dataType:"json",
		success:function(obj)
		{
			if(obj.username==null)
				$("#last").html("无");
			else
				$("#last").html(obj.username);
		}
	}); 
});
</SCRIPT>
</head>
<body>
<div id="popdiv"><span id="sellist"></span><div style="clear:both"></div></div>
<html:form  method="post" action="/rights/sysuser.do?method=update">
<input type="hidden" id="mess" value="${requestScope.mess }"/>
<input type="hidden" name="user_id" value="${userinfo.user_id }"/>
<input type="hidden" name="rdurl" value="/rights/wltuserupdate1.jsp?uid=${userinfo.user_id }"/>
<input type="hidden" name="userename" value="${requestScope.userinfo.userename }"/>
<input type="hidden" name="userno" value="${userinfo.userno}"/>
<div class="pass_main">
	<div class="pass_title_1">用户信息</div>
    <div class="pass_list">
    	<ul>
    		 <li><strong>上级用户名称：</strong><span id="last"></span></li>
            <li><strong>登陆帐号：</strong><span>${userinfo.username }</span></li>
            <li><strong>用户编号：</strong><span>${userinfo.userno }</span></li>
 			<li><strong>用户余额：</strong><span>${userinfo.left }</span></li>
            <li><strong>用户姓名：</strong><span>${userinfo.userename }</span></li>
            <li><strong>身份证号：</strong><span>${userinfo.usersf }</span></li>
            <li><strong>角色类型：</strong><span>${userinfo.role_name }</span></li>
            <li><strong>电子邮箱：</strong><span><input name="usermail" type="text" value="${userinfo.usermail }" readonly="readonly"/></span></li>
            <li><strong>联系电话：</strong><span><input name="phone" type="text" value="${userinfo.phone }" readonly="readonly"/></span></li>
            <li><strong>当前状态：</strong><span>
            <div class="sub-input"><a class="sia-2 selhover" id="sl-userstatus" href="javascript:void(0);">正常</a></div>
			<input nname="nname" name="userstatus" id="userstatus" defaultsel="正常[0]|注销[1]|暂停[2]" type="hidden" value="${userinfo.userstatus}" />
            <c:if test="${userinfo.userstatus == 2 }">
            <input readonly="readonly" type="hidden" name="userstatus" value="${userinfo.userstatus }"/>
            <script language="javascript">
            $("#userstatus").attr("defaultsel","注销[1]");
			$("#sl-userstatus").text("注销");
            </script>
            </c:if>
            </span></li>
<!--            <li><strong>区域：</strong><span>-->
<!--            	<div class="sub-input"><a class="sia-2 selhover" id="sl-usersite" href="javascript:void(0);">请选择</a></div>-->
<!--				<input nname="nname" name="usersite" id="usersite" defaultsel="${requestScope.areaSel }" type="hidden" value="${userinfo.usersite }" />-->
<!--            </span></li>-->
            <li><strong>慧付款号码：</strong><span><input name="hfkphone" type="text" value="<%=ph %>" readonly="readonly"/></span></li>
<li><strong>慧付款终端ID：</strong><span><input name="hfkID" type="text" value="${userinfo.hfkID}" readonly="readonly"/></span></li>            
<li><strong>用户地址：</strong><span>${userinfo.address }</span></li>
            <li><strong>创建时间：</strong><span>${userinfo.usercreatedate }</span></li>
        </ul>
   </div>
   <div class="field-item_button_m">
   <input type="button" value="返回" id="checkCode" onclick="javascript:history.go(-1)" class="field-item_button" />
  <!--   <input type="button" value="登陆账户管理" id="checkCode" onclick="location.href='<%=path %>/rights/wltuserloginlist.jsp?uid=${userinfo.user_id }'" class="field-item_button2" />-->
   </div>
</div>
</html:form>
</body>
</html>