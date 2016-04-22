<%@ page contentType="text/html; charset=GBK" language="java"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<jsp:useBean id="userSession" scope="session" class="com.wlt.webm.rights.form.SysUserForm" />
<jsp:useBean id="user" scope="page" class="com.wlt.webm.rights.bean.SysUser"/>
 <%
 	 String path = request.getContextPath();
	 String userid=request.getParameter("uid");
   	 request.setAttribute("userinfo", user.getUserAccount(userid));
   	 request.setAttribute("srType", user.getUserRole(userid));
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
});
function submitForm(){
  	with(document.forms[0]){
		submit();
	}
}
</SCRIPT>
</head>
<body>
<div id="popdiv"><span id="sellist"></span><div style="clear:both"></div></div>
<html:form  method="post" action="/rights/sysuser.do?method=updateAccount">
<input type="hidden" name="user_id" value="<%=userid %>"/>
<div class="pass_main">
	<div class="pass_title_1">资金账户</div>
    <div class="pass_list">
    	<ul>
            <li><strong>押金账户：</strong><span>${userinfo.accountAmount/1000 }元</span></li>
            <li><strong>佣金账户：</strong><span>${userinfo.commissionAmount/1000 }元</span></li>
            <li><strong>冻结账户：</strong><span>${userinfo.frozenAmount/1000 }元</span></li>
 <!--             <li><strong>状态：</strong><span>
            <div class="sub-input"><a class="sia-2 selhover" id="sl-state" href="javascript:void(0);">开户</a></div>
			<input nname="nname" name="state" id="state" defaultsel="开户[1]|停机[2]|复机[3]|预拆机[4]|已清算[5]|拆机[6]" type="hidden" value="${userinfo.state}" />
            </span></li>-->
        </ul>
   </div>

   <div class="field-item_button_m">
  <!--   <input type="button" value="保存" id="btnSubmit" onclick="submitForm();" class="field-item_button" />-->

   <input type="button" value="充值" id="btnSubmit" onclick="location.href='<%=path %>/rights/wltaccountCharge.jsp?uid=<%=userid %>'" class="field-item_button" />
   <input type="button" value="提现" id="btnSubmit" onclick="location.href='<%=path %>/rights/wltaccountChargeNew.jsp?uid=<%=userid %>&op=0'" class="field-item_button" />
  <!--   <input type="button" value="转款" id="btnSubmit" onclick="location.href='<%=path %>/rights/wltaccountTrans.jsp?uid=<%=userid %>'" class="field-item_button" />-->
   <input type="button" value="返回" id="checkCode" onclick="javascript:history.go(-1)" class="field-item_button" />
   </div>
</div>
</html:form>
</body>
</html>