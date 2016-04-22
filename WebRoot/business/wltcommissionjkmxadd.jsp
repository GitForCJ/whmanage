<%@ page contentType="text/html; charset=GBK" language="java"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<jsp:useBean id="userSession" scope="session" class="com.wlt.webm.rights.form.SysUserForm" />
<jsp:useBean id="role" scope="page" class="com.wlt.webm.rights.bean.SysRole"/>
 <%
   String userrole=userSession.getUser_role();
   request.setAttribute("list", role.getRoleArea(userrole));
   String sg_id = request.getParameter("sg_id");
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
	$("input[name='sc_traderbegin'],input[name='sc_traderend']").keyup(function(){
		$(this).val($(this).val().replace(/\D/g,''));
	})
});
function check(){	
	var _startFee = $("input[name='sc_traderbegin']").val();
	var _endFee = $("input[name='sc_traderend']").val();
	if(parseInt(_startFee) > parseInt(_endFee)){
		alert("起始面额不能大于截止面额");
		return;
	}
  	with(document.forms[0]){
		action="syscommission.do?method=addjkmx";
		target="_self";
		submit();
	}
}
</SCRIPT>
</head>
<body>
<div id="popdiv"><span id="sellist"></span><div style="clear:both"></div></div>
<html:form  method="post" action="/business/syscommission.do?method=add">
<div class="pass_main">
	<div class="pass_title_1">添加佣金组明细</div>
    <div class="pass_list">
        <ul>
  		  	<li>
   	 		<tr>
   	 		<strong>业务类型：</strong>
   	 		<span>
				<div class="sub-input"><a class="sia-2 selhover" id="sl-sc_tradertype" href="javascript:void(0);">请选择</a></div>
				<input name="sc_tradertype" id="sc_tradertype" defaultsel="请选择[]|移动[1]|电信[3]|联通[2]|所有业务[0]" type="hidden" value="" />
			</span>
  		  	</tr>
  		  	</li>
  		  	<li><strong>面额：</strong><span><input name="sc_traderbegin" type="text" />-<input name="sc_traderend" type="text" />&nbsp;(单位:元)</span></li>
            <li><strong>佣金比例：</strong><span><input name="sc_traderpercent" type="text" /></span></li>
  		  </ul>
   </div>
   <div class="field-item_button_m">
   <input type="hidden" name="sg_id" value="<%=sg_id%>"> 
   <input type="button" value="确认" id="checkCode" onclick="check()" class="field-item_button" />
   <input type="button" value="取消" id="checkCode" onClick="javascript:history.go(-1)" class="field-item_button" />
   </div>
</div>
</html:form>
</body>
</html>