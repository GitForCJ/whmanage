<%@ page contentType="text/html; charset=GBK" language="java"%>
<%@page import="java.util.List"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<jsp:useBean id="user" scope="page" class="com.wlt.webm.rights.bean.SysUser"/>
<jsp:useBean id="role" scope="page" class="com.wlt.webm.rights.bean.SysRole"/>
 <%
 	 String path = request.getContextPath();
	 String userid=request.getParameter("uid");
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
function submitForm(){
  	with(document.forms[0]){
  		if(userename.value == ""){
		alert("请输入用户姓名!");
		return;
		}
		submit();
	}
}
</SCRIPT>
</head>
<body>
<div id="popdiv"><span id="sellist"></span><div style="clear:both"></div></div>
<html:form  method="post" action="/rights/sysuser.do?method=update">
<input type="hidden" id="mess" value="${requestScope.msg }"/>
<input type="hidden" name="user_id" value="${userinfo.user_id }"/>
<input type="hidden" name="rdurl" value="/rights/wltuserupdate_1.jsp?uid=${userinfo.user_id }"/>
<div class="pass_main">
	<div class="pass_title_1">用户信息</div>
    <div class="pass_list">
    	<ul>
            <li><strong>帐号：</strong><span>${userinfo.username }</span></li>
            <li><strong>用户编号：</strong><span>${userinfo.userno }</span></li>
            <li><strong>用户姓名：</strong><span><input name="userename" type="text" value="${userinfo.userename }"/><font color="red">*</font></span></li>
            <li><strong>角色：</strong><span><input name="user_role" type="hidden" value="${userinfo.user_role }"/>
			${userinfo.role_name }
			</span></li>
            <li><strong>邮箱：</strong><span><input name="usermail" type="text" value="${userinfo.usermail }"/><font color="red">*</font></span></li>
            <li><strong>电话：</strong><span><input name="usertel" type="text" value="${userinfo.usertel }"/><font color="red">*</font></span></li>
            <li><strong>状态：</strong><span>
            <div class="sub-input"><a class="sia-2 selhover" id="sl-userstatus" href="javascript:void(0);">正常</a></div>
			<input nname="nname" name="userstatus" id="userstatus" defaultsel="正常[0]|禁用[1]|销户[2]" type="hidden" value="${userinfo.userstatus}" />
            <c:if test="${userinfo.userstatus == 2 }">
            <input type="hidden" name="userstatus" value="${userinfo.userstatus }"/>
            <script language="javascript">
            $("#userstatus").attr("defaultsel","销户[2]");
			$("#sl-userstatus").text("销户");
            </script>
            </c:if>
            </span></li>
<!--            <li><strong>区域：</strong><span>-->
<!--            	<div class="sub-input"><a class="sia-2 selhover" id="sl-usersite" href="javascript:void(0);">请选择</a></div>-->
<!--				<input nname="nname" name="usersite" id="usersite" defaultsel="${requestScope.areaSel }" type="hidden" value="${userinfo.usersite }" />-->
<!--            </span></li>-->
            <li><strong>描述：</strong><span><input name="userdesc" type="text" value="${userinfo.userdesc }"/></span></li>
            <li><strong>创建时间：</strong><span>${userinfo.usercreatedate }</span></li>
        </ul>
   </div>
   <div class="field-item_button_m">
   <input type="button" value="保存" id="btnSubmit" onclick="submitForm();" class="field-item_button" />
   <input type="button" value="返回" id="checkCode" onclick="location.href='<%=path %>/rights/wltusermain.jsp'" class="field-item_button" />
   </div>
</div>
</html:form>
</body>
</html>