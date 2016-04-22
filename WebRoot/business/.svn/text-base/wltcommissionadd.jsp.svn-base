<%@ page contentType="text/html; charset=gb2312" language="java"%>
<%@page import="java.util.List"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<jsp:useBean id="userSession" scope="session" class="com.wlt.webm.rights.form.SysUserForm" />
<jsp:useBean id="role" scope="page" class="com.wlt.webm.rights.bean.SysRole"/>
 <%
   String path = request.getContextPath();
   String userrole=userSession.getUser_role();
   List areaList = role.getRoleArea(userrole);
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
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
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
});
function check(){
	var _scName = $("#sc_name").val();
	if("" == _scName){
		alert("名称不能为空");
		return;
	}
	$.post(
	    	$("#rootPath").val()+"/business/syscommission.do?method=validateName",
	      	{
	      		name :_scName
	      	},
	      	function (data){
	      		if(data == "0"){
	      			alert("名称已存在");
	      			return ;
	      		}else{
	      			with(document.forms[0]){
						action="syscommission.do?method=add";
						target="_self";
						submit();
					}
	      		}
	      	}
		)
}
</SCRIPT>
</head>
<body>
<input type="hidden" id="rootPath" value="<%=path %>"/>
<div id="popdiv"><span id="sellist"></span><div style="clear:both"></div></div>
<html:form  method="post" action="/business/syscommission.do?method=add">
<input type="hidden" id="sc_type" name="sc_type" value="${ param.stype}"/>
<div class="pass_main">
	<div class="pass_title_1">添加佣金组</div>
    <div class="pass_list">
        <ul>
        	<li>
   	 		<tr>
   	 		<strong>佣金组名称：</strong>
   	 		<span>
				<td>
					<input type="text" name="sc_name" id="sc_name"/>
				</td>
			</span>
  		  	</tr>
  		  	</li>
           <li>
   	 		<tr>
   	 		<strong>省份：</strong>
   	 		<span>
   	 			<div class="sub-input"><a class="sia-2 selhover" id="sl-wltarea" href="javascript:void(0);">请选择</a></div>
				<input name="wltarea" id="wltarea" defaultsel="${requestScope.areaSel }" type="hidden" value="" />
			</span>
  		  	</tr>
  		  	</li>
  		  	<li>
   	 		<tr>
   	 		<strong>是否默认：</strong>
   	 		<span>
   	 			<div class="sub-input"><a class="sia-2 selhover" id="sl-sg_defaut" href="javascript:void(0);">请选择</a></div>
				<input name="sg_defaut" id="sg_defaut" defaultsel="请选择[]|默认[1]|非默认[2]" type="hidden" value="" />
			</span>
  		  	</tr>
  		  	</li>
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