<%@ page contentType="text/html; charset=GBK" language="java"%>
<%@page import="java.util.List"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<jsp:useBean id="userSession" scope="session" class="com.wlt.webm.rights.form.SysUserForm" />
<jsp:useBean id="role" scope="page" class="com.wlt.webm.rights.bean.SysRole"/>
 <%
   String path = request.getContextPath();
   String userrole=userSession.getUser_role();
   request.setAttribute("prole", userrole);
   List areaList = role.getAreaList();
	 StringBuffer sBuffer = new StringBuffer();
	 sBuffer.append("��ѡ��[]");
	 for(Object tmp : areaList){
		String[] temp = (String[])tmp;
		sBuffer.append("|"+temp[1]+"["+temp[0]+"]");
	 }
	 
	 List rolelist = role.getRoleType(null);
	 StringBuffer sBuffer1 = new StringBuffer();
	 sBuffer1.append("��ѡ��[]");
	 for(Object tmp : rolelist){
		String[] temp = (String[])tmp;
		sBuffer1.append("|"+temp[1]+"["+temp[0]+"]");
	 }
	 request.setAttribute("areaSel", sBuffer.toString());
 	 request.setAttribute("roleSel", sBuffer1.toString());
 %>
 <html>
<head>
<title>���ͨ</title>
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
function check(){
  	with(document.forms[0]){
		/* areacopeΪ�������ţ�Ҳ����ϵ��α��еĽڵ����� */
		if(rolename.value == ""){
		alert("�������ɫ����!");
		return;
		}
		if(sg_default.value == 0){
			if(user_site_city.value != ''){
				alert("��ɫĬ��ֻ�ܹ���ʡ��");
				return;
			}
		}
		action="sysrole.do?method=add";
		target="_self";
		submit();
	}
}

</SCRIPT>
</head>
<body>
<div id="popdiv"><span id="sellist"></span><div style="clear:both"></div></div>
<input type="hidden" id="rootPath" value="<%=path %>"/>
<input type="hidden" id="mess" value="${requestScope.mess }"/>
<html:form  method="post" action="/rights/sysrole.do?method=add">
<div class="pass_main">
	<div class="pass_title_1">��ӽ�ɫ</div>
    <div class="pass_list">
    	<ul>
            <li><strong>��ɫ���ƣ�</strong><span><input name="rolename" type="text" /></span></li>
        </ul>
           <ul>
           <li>
   	 		<tr>
   	 		<strong>�û����ͣ�</strong>
   	 		<span>
   	 		<div class="sub-input"><a class="sia-2 selhover" id="sl-roleType" href="javascript:void(0);">��ѡ��</a></div>
			<input name="roleType" id="roleType" defaultsel="${requestScope.roleSel }" type="hidden" value="" />
            </span>
  		  	</tr>
  		  	</li>
  		  </ul>
        <ul>
           <li>
   	 		<tr>
   	 		<strong>ʡ����</strong>
   	 		<span>
				<td>
				<div class="sub-input"><a class="sia-2 selhover" id="sl-wltarea_role" href="javascript:void(0);">��ѡ��</a></div>
				<input name="wltarea" id="wltarea_role" defaultsel="${requestScope.areaSel }" type="hidden" value="" />
				</td>
			</span>
  		  	</tr>
  		  	</li>
  		  </ul>
  		          <ul>
           <li>
  		  	<tr>
				<strong>�м���</strong>
				<span>
				<td>
					<div class="sub-input"><a class="sia-2 selhover" id="sl-user_site_city" href="javascript:void(0);">��ѡ��</a></div>
					<input name="user_site_city" id="user_site_city" defaultsel="" type="hidden" value="" />
				</td>
			</span>
  		  	</tr>
  		  </li>
  		  <li>
				<strong>Ĭ�ϣ�</strong>
				<span>
				<div class="sub-input"><a class="sia-2 selhover" id="sl-sg_default" href="javascript:void(0);">��Ĭ��</a></div>
				<input name="sg_default" id="sg_default" defaultsel="Ĭ��[0]|��Ĭ��[1]" type="hidden" value="1" />
			</span>
  		  </li>
				<li id="orAgent" >
					<strong>�Ƿ�һ������</strong>
					<span>
						<input type="radio" value="" name="oneAgents"  checked="checked" style="width:20px;height:14px;"/>��
						&nbsp;
						<input type="radio" value="1" name="oneAgents" style="width:20px;height:14px;"/>��
					</span>
					
				</li>
			 <li  >
			 <strong>&nbsp;</strong>
				<font style="color:red">һ����������ڹ���Ա��ɫ</font>
			</li>
  		  </ul>
   </div>
   <div class="field-item_button_m">
   <input name="prole" type="hidden" value="${prole}" />
   <input type="button" value="ȷ��" id="checkCode" onclick="check()" class="field-item_button" />
   <input type="button" value="ȡ��" id="checkCode" onClick="javascript:history.go(-1)" class="field-item_button" />
   </div>
</div>
</html:form>
</body>
</html>