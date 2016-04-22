<%@ page contentType="text/html; charset=GBK" language="java"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<jsp:useBean id="userSession" scope="session" class="com.wlt.webm.rights.form.SysUserForm" />
<jsp:useBean id="role" scope="page" class="com.wlt.webm.rights.bean.SysRole"/>
 <%
 	 String userrole=userSession.getUser_role();
	 request.setAttribute("rolelist", role.getRole(userrole));
 %>
 <html>
<head>
<title>���ͨ</title>
<script type="text/javascript" src="../js/jquery-1.8.2.min.js"></script>
<script type="text/javascript" src="../js/xml_cascade.js"></script>
<link href="../css/main_style.css" rel="stylesheet" type="text/css" />
<SCRIPT src="../js/more_tiaojian.js" type=text/javascript></SCRIPT>
<script language='javascript' src='../js/jquery.js'></script>
<script language='javascript' src='../js/select.js'></script>
<script language="javascript" type="text/javascript" src="../My97DatePicker/WdatePicker.js"></script>
<SCRIPT type=text/javascript>
jQuery(function(){
	$("#changeCity").click(function(a){$("#allCity").slideDown(0);a.stopPropagation();$(this).blur()});$("#allCity").click(function(a){a.stopPropagation()});$(document).click(function(a){a.button!=2&&$("#allCity").slideUp(0)});$("#foldin").click(function(){$("#allCity").slideUp(0)})
});
function check(){
  	with(document.forms[0]){
		/* areacopeΪ�������ţ�Ҳ����ϵ��α��еĽڵ����� */
		if(sc_traderpercent.value == ""){
		alert("�������ۿ��� !");
		return;
		}
		action="sysrebate.do?method=add";
		target="_self";
		submit();
	}
}
</SCRIPT>
</head>
<body>
<html:form  method="post" action="/business/sysrebate.do?method=add">
<div class="pass_main">
	<div class="pass_title_1">����ۿ���</div>
    <div class="pass_list">
    	<ul>
            <li><strong>��ɫ ��</strong><span>
			<html:select property="roleid">
              <option value="">��ѡ��</option>
              <logic:iterate id="roleinfo" name="rolelist">
              <html:option value="${roleinfo[0]}">${roleinfo[1]}</html:option>
              </logic:iterate>
              </html:select>
			</span></li>
            <li><strong>�������ͣ�</strong><span>
			<select name="sc_tradertype">
				<option value="1">Q��</option>
				<option value="2">��Ϸ��</option>
				<option value="3">��ͨ����</option>
			</select>
			</span></li>
            <li><strong>�ۿ��ʣ�</strong><span><input name="sc_traderpercent" type="text" /></span></li>
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