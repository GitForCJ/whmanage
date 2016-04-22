<%@ page contentType="text/html; charset=GBK" language="java"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
 <%
 	 String path = request.getContextPath();
 	 String userId = request.getParameter("uid");
 %>
 <html>
<head>
<title>万汇通</title>
<script type="text/javascript" src="../js/jquery-1.8.2.min.js"></script>
<script type="text/javascript" src="../js/xml_cascade.js"></script>
<link href="../css/main_style.css" rel="stylesheet" type="text/css" />
<SCRIPT src="../js/more_tiaojian.js" type=text/javascript></SCRIPT>
<link href="../css/selectCss.css" rel="stylesheet" type="text/css" /> 
<script src="../js/selectCss.js" type="text/javascript"></script> 
<script language="javascript" type="text/javascript" src="../My97DatePicker/WdatePicker.js"></script>
<SCRIPT type=text/javascript>
jQuery(function(){
	$("#changeCity").click(function(a){$("#allCity").slideDown(0);a.stopPropagation();$(this).blur()});$("#allCity").click(function(a){a.stopPropagation()});$(document).click(function(a){a.button!=2&&$("#allCity").slideUp(0)});$("#foldin").click(function(){$("#allCity").slideUp(0)})
	$("#macflag").change(function(){
		var _mf = $(this).val();
		if(_mf == '1'){
			$("#mac").val("");
			$("#macAddr").hide();
		}else{
			$("#macAddr").show();
		}
	});
});
function check(){
  	with(document.forms[0]){
		/* areacope为区域区号，也即体系层次表中的节点区号 */
		if(username.value == ""){
		alert("请输入用户名称!");
		return;
		}
		if(userpassword.value == ""){
		alert("请输入用户密码!");
		return;
		}
		action="sysloginuser.do?method=addSafe";
		target="_self";
		submit();
	}
}
</SCRIPT>
</head>
<body>
<div id="tabbox">
  <div id="tabs_title">
    <ul class="tabs_wzdh">
      <li>安全登录选择</li></ul>
  </div>
</div>
<div id="popdiv"><span id="sellist"></span><div style="clear:both"></div></div>
<input type="hidden" id="rootPath" value="<%=path %>"/>
<html:form  method="post" action="/rights/sysloginuser.do?method=addSafe">
<input type="hidden" name="user_id" value="<%=userId %>"/>
<div class="pass_main">
	<div class="pass_title_1">添加用户</div>
    <div class="pass_list">
    	<ul>
            <li><strong>帐号：</strong><span><input name="username" type="text" /></span></li>
            <li><strong>密码：</strong><span><input name="userpassword" type="text" /></span></li>
  		  	<li><strong>交易密码：</strong><span><input name="dealpass" type="text" /></span></li>
			<li><strong>转款密码：</strong><span><input name="feepass" type="text" /></span></li>
			<li><strong>短信验证手机号：</strong><span><input name="phone" type="text" /></span></li>
			<li><strong>资金账户转款短信是否验证：</strong><span>
				<div class="sub-input"><a class="sia-2 selhover" id="sl-feeshortflag" href="javascript:void(0);">请选择</a></div>
				<input name="feeshortflag" id="feeshortflag" defaultsel="请选择[]|验证[0]|不验证[1]" type="hidden" value="" />
			</span></li>
			<li><strong>Mac地址是否验证：</strong><span>
				<div class="sub-input"><a class="sia-2 selhover" id="sl-macflag" href="javascript:void(0);">请选择</a></div>
				<input name="macflag" id="macflag" defaultsel="请选择[]|验证[0]|不验证[1]" type="hidden" value="" />
			</span></li>
			<li id="macAddr"><strong>Mac地址：</strong><span><input name="mac" id="mac" type="text" /></span></li>
			 <li><strong>登陆密码是否验证：</strong><span>
				<div class="sub-input"><a class="sia-2 selhover" id="sl-passflag" href="javascript:void(0);">请选择</a></div>
				<input name="passflag" id="passflag" defaultsel="请选择[]|验证[0]|不验证[1]" type="hidden" value="" />
			</span></li>
			<li><strong>登录短信是否验证：</strong><span>
				<div class="sub-input"><a class="sia-2 selhover" id="sl-shortflag" href="javascript:void(0);">请选择</a></div>
				<input name="shortflag" id="shortflag" defaultsel="请选择[]|验证[0]|不验证[1]" type="hidden" value="" />
			</span></li>
        </ul>
           <ul>
           
  		  </ul>
   </div>
   <div class="field-item_button_m">
   <input type="button" value="确认" id="checkCode" onclick="check()" class="field-item_button" />
   <input type="button" value="返回" id="checkCode" onclick="javascript:history.go(-1);" class="field-item_button" />
   </div>
</div>
</html:form>
</body>
</html>