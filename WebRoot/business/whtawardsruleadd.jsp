<%@ page contentType="text/html; charset=GBK" language="java"%>
<%@ page import="java.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<jsp:useBean id="userSession" scope="session" class="com.wlt.webm.rights.form.SysUserForm" />
 <html>
<head>
<title>万汇通</title>
<script type="text/javascript" src="../js/jquery-1.8.2.min.js"></script>
<link href="../css/main_style.css" rel="stylesheet" type="text/css" />
<link href="../css/selectCss.css" rel="stylesheet" type="text/css" /> 
<script language="javascript" type="text/javascript" src="../js/util.js"></script>
<SCRIPT type=text/javascript>
showMessage("${mess}");
function check(){
  	with(document.forms[0]){
		/* areacope为区域区号，也即体系层次表中的节点区号 */
		if(cm_fee.value == ""||cm_fee1.value==""||cm_fee2.value==""){
		alert("请输入正确数据!");
		return;
		}
		var myreg = /^[1-9]*[0-9]*$/;
		if(!myreg.test(cm_fee.value)||!myreg.test(cm_fee1.value)){
		alert("请输入正确的数据 !");
		return;
		}
		if(parseFloat(cm_fee.value)>=parseFloat(cm_fee1.value)){
		alert("最小金额不能大于或等于最大金额!");
		return;
		}
		
		action="prod.do?method=awardsadd";
		target="_self";
		submit();
	}
}
</SCRIPT>
</head>
<body>
<div id="popdiv"><span id="sellist"></span><div style="clear:both"></div></div>
<input type="hidden" id="mess" value="${requestScope.mess }"/>
<html:form  method="post" action="/business/prod.do?method=awardsadd">
<div class="pass_main">
	<div class="pass_title_1">添加月度奖励规则</div>
    <div class="pass_list">
    	<ul>
            <li><strong>用户类型：</strong><span>
            <select name="usertype">
            <option value="2" selected="selected">代理商</option>
			<option value="4">接口商</option>
            </select>
            </span></li>
            <li><strong>金额最小值：</strong><span><input name="cm_fee" type="text" /></span></li>
            <li><strong>金额最大值：</strong><span><input name="cm_fee1" type="text" /></span></li>
            <li><strong>奖励百分比：</strong><span><input name="cm_fee2" type="text" /></span></li>
        </ul>
   </div>
   <div class="field-item_button_m">
   <input type="button" value="确认" id="checkCode" onclick="check()" class="field-item_button" />
   <input type="button" value="取消" id="checkCode" onClick="javascript:history.go(-1)" class="field-item_button" />
   </div>
</div>
</html:form>
</body>
</html>