<%@ page contentType="text/html; charset=GBK" language="java"%>
<%@ page import="java.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<jsp:useBean id="userSession" scope="session" class="com.wlt.webm.rights.form.SysUserForm" />
<jsp:useBean id="prod" scope="page" class="com.wlt.webm.business.bean.BiProd"/>
 <%
	 request.setAttribute("arealist", prod.getAreaList());
	 List areaList = prod.getFaceList();
	 StringBuffer sBuffer1 = new StringBuffer();
	 sBuffer1.append("请选择[]");
	 for(Object tmp : areaList){
		String[] temp = (String[])tmp;
		sBuffer1.append("|"+temp[1]+"["+temp[0]+"]");
	 }
	 request.setAttribute("areaSel", sBuffer1.toString());
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
	if($("#mess").val() != ""){
		alert($("#mess").val());
	}
});
function check(){
  	with(document.forms[0]){
		/* areacope为区域区号，也即体系层次表中的节点区号 
		if(cm_fee.value == ""){
		alert("请输入 !");
		return;
		}*/
		action="prod.do?method=sanadd";
		target="_self";
		submit();
	}
}
</SCRIPT>
</head>
<body>
<div id="popdiv"><span id="sellist"></span><div style="clear:both"></div></div>
<input type="hidden" id="mess" value="${requestScope.mess }"/>
<html:form  method="post" action="/business/prod.do?method=sanadd">
<div class="pass_main">
	<div class="pass_title_1">添加第三方接口信息</div>
    <div class="pass_list">
    	<ul>
            <li><strong>产品面额：</strong><span>
            <div class="sub-input"><a class="sia-2 selhover" id="sl-cm_fee" href="javascript:void(0);">请选择</a></div>
			<input name="cm_fee" id="cm_fee" defaultsel="请选择[30]|30[30]|50[50]|100[100]|200[200]|300[300]|500[500]" type="hidden" value="" />
			</span></li>
            <li><strong>接口名称 ：</strong><span>
            <div class="sub-input"><a class="sia-2 selhover" id="sl-cm_face" href="javascript:void(0);">请选择</a></div>
			<input name="cm_face" id="cm_face" defaultsel="${requestScope.areaSel }" type="hidden" value="" />
			</span></li>
            <li><strong>业务类型：</strong><span>
            <div class="sub-input"><a class="sia-2 selhover" id="sl-cm_type" href="javascript:void(0);">请选择</a></div>
			<input name="cm_type" id="cm_type" defaultsel="请选择[0]|移动[0]|联通[1]|电信[2]" type="hidden" value="" />
			</span></li>
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