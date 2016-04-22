<%@ page contentType="text/html; charset=UTF-8" language="java"%>
<%@page import="com.wlt.webm.rights.form.SysUserBankForm"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<jsp:useBean id="userSession" scope="session" class="com.wlt.webm.rights.form.SysUserForm" />
<jsp:useBean id="userBank" scope="page" class="com.wlt.webm.rights.bean.SysUserBank"/>
 <%
 	 String path = request.getContextPath();
	 String userid=request.getParameter("uid");
	 SysUserBankForm bank = userBank.getUserBankInfo(userid);
	 String isNullBank = "false";
	 if(null == bank.getUser_id()){
		 isNullBank = "true";
	 }
   	 request.setAttribute("bankinfo", bank);
 %>
 <!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
 <html>
<head>
<title>万汇通</title>
<script type="text/javascript" src="../js/jquery-1.8.2.min.js"></script>
<script type="text/javascript" src="../js/xml_cascade.js"></script>
<link href="../css/main_style.css" rel="stylesheet" type="text/css" />
<script src="../js/more_tiaojian.js" type=text/javascript></script>
<script language='javascript' src='../js/jquery.js'></script>
<script language='javascript' src='../js/select.js'></script>
<script language="javascript" type="text/javascript" src="../My97DatePicker/WdatePicker.js"></script>
<script type=text/javascript>
jQuery(function(){
	$("#changeCity").click(function(a){$("#allCity").slideDown(0);a.stopPropagation();$(this).blur()});$("#allCity").click(function(a){a.stopPropagation()});$(document).click(function(a){a.button!=2&&$("#allCity").slideUp(0)});$("#foldin").click(function(){$("#allCity").slideUp(0)})
});
function submitForm(){
  	with(document.forms[0]){
		submit();
	}
}
</script>
</head>
<body>
<html:form  method="post" action="/rights/sysuserbank.do?method=bankBound">
<input type="hidden" name="user_id" value="<%=userid %>"/>
<input type="hidden" name="isNullBank" value="<%=isNullBank %>"/>
<div class="pass_main">
	<div class="pass_title_1">用户信息</div>
    <div class="pass_list">
    	<ul>
            <li><strong>用户账号：</strong><span>${param.uname }</span></li>
            <li><strong>银行卡号：</strong><span>${bankinfo.user_bankcard }</span></li>
            <li><strong>用户姓名：</strong><span>${bankinfo.user_name }</span></li>
            <li><strong>身份证类型：</strong><span>
				<select name="user_icard_type" id="user_icard_type" class="jj" name="slct"  style="width:120px">
	              <option value="01" <c:if test="${bankinfo.user_icard_type == '01' }">selected</c:if>>身份证</option>
	              <option value="02" <c:if test="${bankinfo.user_icard_type == '02' }">selected</c:if>>军官证</option>
	              <option value="03" <c:if test="${bankinfo.user_icard_type == '03' }">selected</c:if>>护照</option>
	              <option value="04" <c:if test="${bankinfo.user_icard_type == '04' }">selected</c:if>>回乡证</option>
	              <option value="05" <c:if test="${bankinfo.user_icard_type == '05' }">selected</c:if>>台胞证</option>
	              <option value="06" <c:if test="${bankinfo.user_icard_type == '06' }">selected</c:if>>警官证</option>
	              <option value="07" <c:if test="${bankinfo.user_icard_type == '07' }">selected</c:if>>士兵证</option>
	              <option value="99" <c:if test="${bankinfo.user_icard_type == '99' }">selected</c:if>>其它证件</option>
	            </select>
			</span></li>
            <li><strong>用户证件号：</strong><span>${bankinfo.user_icard }</span></li>
        </ul>
   </div>
   <div class="field-item_button_m">
   <input type="button" value="返回" id="checkCode" onclick="location.href='<%=path %>/rights/wltuserbanklist.jsp'" class="field-item_button" />
   </div>
</div>
</html:form>
</body>
</html>