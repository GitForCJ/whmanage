<%@ page contentType="text/html; charset=gb2312" language="java"%>
<%@page import="java.util.List"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<jsp:useBean id="userSession" scope="session" class="com.wlt.webm.rights.form.SysUserForm" />
<jsp:useBean id="user" scope="page" class="com.wlt.webm.rights.bean.SysUser"/>
<jsp:useBean id="role" scope="page" class="com.wlt.webm.rights.bean.SysRole"/>
<jsp:useBean id="prod" scope="page" class="com.wlt.webm.business.bean.BiProd"/>
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
	 
	 String state=prod.getMacState(userid);
	 request.setAttribute("state",state);
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
<style type="text/css">
.black_overlay {
	display: none;
	position: absolute;
	top: 0%;
	left: 0%;
	width: 100%;
	height: 100%;
	background-color: black;
	z-index: 1001;
	-moz-opacity: 0.8;
	opacity: .80;
	filter: alpha(opacity =     88);
}
.white_content {
	display: none;
	position: absolute;
	top: 25%;
	left: 30%;
	width: 25%;
	border: 2px solid orange;
	background-color: white;
	z-index: 1002;
	overflow: auto;
}
</style>
<SCRIPT type=text/javascript>
jQuery(function(){
	$("#changeCity").click(function(a){$("#allCity").slideDown(0);a.stopPropagation();$(this).blur()});$("#allCity").click(function(a){a.stopPropagation()});$(document).click(function(a){a.button!=2&&$("#allCity").slideUp(0)});$("#foldin").click(function(){$("#allCity").slideUp(0)})
	if($("#mess").val() != ""){
		alert($("#mess").val());
	}
});
function submitForm(){
  	with(document.forms[0]){
  	if(usermail.value==""||phone.value==""){
	  	alert("邮箱和电话信息不能为空");
	  	return false;
  	}
  		document.getElementById('light').style.display='block';
	  	document.getElementById('fade').style.display='block';
	  	return false;
		//submit();
	}
}
function con(a){
	if(a=='0'){
		var flag=document.getElementById('tradepwd').value;
		if(flag==null||flag==""){
			alert("请输入交易密码");
		}else{
		 document.getElementById('tradepassword').value=flag;
		 document.getElementById('light').style.display='none';
		 document.getElementById('fade').style.display='none';
		 document.forms[0].action=$("#rootPath").val()+"/rights/sysuser.do?method=update";
		 target="_self";
		 document.forms[0].submit();
		 return ;
	 	}
	 }else{
		 document.getElementById('light').style.display='none';
		 document.getElementById('fade').style.display='none';
		 return ;
	 }
}

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
<input type="hidden" id="rootPath" value="<%=path%>" />
<html:form  method="post" action="/rights/sysuser.do?method=update">
<input type="hidden" id="mess" value="${requestScope.mess }"/>
<input type="hidden" name="user_id" value="${userinfo.user_id }"/>
<input type="hidden" name="rdurl" value="/rights/wltuserupdate.jsp?uid=${userinfo.user_id }"/>
<input type="hidden" name="userename" value="${requestScope.userinfo.userename }"/>
<input type="hidden" name="userno" value="${userinfo.userno}"/>
<div class="pass_main">
	<div class="pass_title_1">用户信息</div>
    <div class="pass_list">
    	<ul>
    		 <li><strong>上级用户名称：</strong><span id="last"></span></li>
            <li><strong>登陆帐号：</strong><span>${userinfo.username }</span></li>
            <li><strong>用户编号：</strong><span>${userinfo.userno }</span></li>
			<li><strong>身份证：</strong><span>${userinfo.usersf }</span></li>
            <li><strong>用户姓名：</strong><span>${userinfo.userename }</span></li>
            <li><strong>角色类型：</strong><span>${userinfo.role_name }</span></li>
            <li><strong>邮箱：</strong><span><input name="usermail" type="text" value="${userinfo.usermail }"/></span></li>
            <li><strong>电话：</strong><span><input name="phone" type="text" value="${userinfo.phone }"/></span></li>
            <li><strong>状态：</strong><span>
            <div class="sub-input"><a class="sia-2 selhover" id="sl-userstatus" href="javascript:void(0);">正常</a></div>
			<input name="nname" name="userstatus" id="userstatus" defaultsel="正常[0]|注销[1]|暂停[2]" type="hidden" value="${userinfo.userstatus}" />
            <c:if test="${userinfo.userstatus == 2 }">
            <input type="hidden" name="userstatus" value="${userinfo.userstatus }"/>
            <script language="javascript">
            $("#userstatus").attr("defaultsel","正常[0]|注销[1]|暂停[2]");
			$("#sl-userstatus").text("暂停");
            </script>
            </c:if>
            <c:if test="${userinfo.userstatus == 1 }">
            <input type="hidden" name="userstatus" value="${userinfo.userstatus }"/>
            <script language="javascript">
            $("#userstatus").attr("defaultsel","正常[0]|注销[1]|暂停[2]");
			$("#sl-userstatus").text("注销");
            </script>
            </c:if>
            </span></li>
            <li>
            	<strong>mac状态：</strong>
            	<span>
            		<select name="exp3" style="width:145px;height:25px" >
            		<!-- 
            			<option value="0"  <c:if test="${userinfo.exp3 == 0 }">selected</c:if>>已绑定</option>
            			<option value="1" <c:if test="${userinfo.exp3 != 0 }">selected</c:if>>未绑定</option>
            		-->
            			<option value="0" <c:if test="${state == 0 }">selected</c:if>>绑定</option>
						<option value="1" <c:if test="${state != 0 }">selected</c:if>>未绑定</option>
            		</select>
            	</span>
            </li>
<!--            <li><strong>区域：</strong><span>-->
<!--            	<div class="sub-input"><a class="sia-2 selhover" id="sl-usersite" href="javascript:void(0);">请选择</a></div>-->
<!--				<input nname="nname" name="usersite" id="usersite" defaultsel="${requestScope.areaSel }" type="hidden" value="${userinfo.usersite }" />-->
<!--            </span></li>-->
            <li><strong>慧付款号码：</strong><span><input name="hfkphone" type="text" value="<%=ph %>"/></span></li>
<li><strong>慧付款终端ID：</strong><span><input name="hfkID" type="text" value="${userinfo.hfkID}"/></span></li>            
<li><strong>用户地址：</strong><span><input name="address" type="text" value="${userinfo.address }"/></span></li>
            <li><strong>创建时间：</strong><span>${userinfo.usercreatedate }</span></li>
           <li><strong>验证提示：</strong><span><input style="vertical-align:middle;height:18px;width:15px"  type="checkbox" name="shortflag" value="0" <c:if test="${userinfo.shortflag==0 }">checked</c:if>>取消登录短信<input style="vertical-align:middle;height:18px;width:15" type="checkbox" name="feeshortflag" value="0" <c:if test="${userinfo.feeshortflag==0 }">checked</c:if>>取消账户短信</span></li>
                  
          </ul>        
   </div>
   <div class="field-item_button_m">
   <input type="button" value="保存" id="btnSubmit" onclick="submitForm();" class="field-item_button" />
   <input type="button" value="返回" id="checkCode" onclick="javascript:history.go(-1)" class="field-item_button" />
  <!--   <input type="button" value="登陆账户管理" id="checkCode" onclick="location.href='<%=path %>/rights/wltuserloginlist.jsp?uid=${userinfo.user_id }'" class="field-item_button2" />-->
   </div>
</div>

<div id="light" class="white_content" style="padding: 10px 10px 10px 10px;">
		<ul>
			<li
				style="text-align: left; padding-top: 5px; padding-bottom: 5px;">
				<span style="font-size: 16px; color: #4c89d2">请输入交易密码:</span>
			</li>
			<li
				style="text-align: right; padding-top: 5px; padding-bottom: 5px;">
				<input type="password" id="tradepwd" value="" />
			</li>
			<li
				style="text-align: center; padding-top: 5px; padding-bottom: 5px;">
				<a href="javascript:void(0)" style="font-size: 18px;"
					onclick="con('0')">确定</a>&nbsp;&nbsp;
				<a href="javascript:void(0)" style="font-size: 18px;"
					onclick="con('1')">取消</a>
			</li>
		</ul>
	</div>
	<div id="fade" class="black_overlay"></div>
<input type="hidden" id="tradepassword" name="tradepassword" value="" />
</html:form>
</body>
</html>