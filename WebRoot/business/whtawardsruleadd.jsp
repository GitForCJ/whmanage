<%@ page contentType="text/html; charset=GBK" language="java"%>
<%@ page import="java.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<jsp:useBean id="userSession" scope="session" class="com.wlt.webm.rights.form.SysUserForm" />
 <html>
<head>
<title>���ͨ</title>
<script type="text/javascript" src="../js/jquery-1.8.2.min.js"></script>
<link href="../css/main_style.css" rel="stylesheet" type="text/css" />
<link href="../css/selectCss.css" rel="stylesheet" type="text/css" /> 
<script language="javascript" type="text/javascript" src="../js/util.js"></script>
<SCRIPT type=text/javascript>
showMessage("${mess}");
function check(){
  	with(document.forms[0]){
		/* areacopeΪ�������ţ�Ҳ����ϵ��α��еĽڵ����� */
		if(cm_fee.value == ""||cm_fee1.value==""||cm_fee2.value==""){
		alert("��������ȷ����!");
		return;
		}
		var myreg = /^[1-9]*[0-9]*$/;
		if(!myreg.test(cm_fee.value)||!myreg.test(cm_fee1.value)){
		alert("��������ȷ������ !");
		return;
		}
		if(parseFloat(cm_fee.value)>=parseFloat(cm_fee1.value)){
		alert("��С���ܴ��ڻ���������!");
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
	<div class="pass_title_1">����¶Ƚ�������</div>
    <div class="pass_list">
    	<ul>
            <li><strong>�û����ͣ�</strong><span>
            <select name="usertype">
            <option value="2" selected="selected">������</option>
			<option value="4">�ӿ���</option>
            </select>
            </span></li>
            <li><strong>�����Сֵ��</strong><span><input name="cm_fee" type="text" /></span></li>
            <li><strong>������ֵ��</strong><span><input name="cm_fee1" type="text" /></span></li>
            <li><strong>�����ٷֱȣ�</strong><span><input name="cm_fee2" type="text" /></span></li>
        </ul>
   </div>
   <div class="field-item_button_m">
   <input type="button" value="ȷ��" id="checkCode" onclick="check()" class="field-item_button" />
   <input type="button" value="ȡ��" id="checkCode" onClick="javascript:history.go(-1)" class="field-item_button" />
   </div>
</div>
</html:form>
</body>
</html>