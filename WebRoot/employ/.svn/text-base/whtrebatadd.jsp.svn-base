<%@ page contentType="text/html; charset=GBK" language="java"%>
<%@ page import="java.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<jsp:useBean id="userSession" scope="session" class="com.wlt.webm.rights.form.SysUserForm" />
<jsp:useBean id="prod" scope="page" class="com.wlt.webm.business.bean.BiProd"/>
 <%
	 List valueList = prod.gettype();
	 StringBuffer sBuffer2 = new StringBuffer();
	 sBuffer2.append("|请选择[]");
	 for(Object tmp : valueList){
		String[] temp = (String[])tmp;
		sBuffer2.append("|"+temp[1]+"["+temp[0]+"]");
	 }
	 request.setAttribute("valueSel", sBuffer2.toString());
 %>
 <html>
<head>
<title>万汇通</title>
<script type="text/javascript" src="../js/util.js"></script>
<script type="text/javascript" src="../js/jquery-1.8.2.min.js"></script>
<script type="text/javascript" src="../js/xml_cascade.js"></script>
<link href="../css/main_style.css" rel="stylesheet" type="text/css" />
<link href="../css/selectCss.css" rel="stylesheet" type="text/css" /> 
<script src="../js/selectCss.js" type="text/javascript"></script> 
<script language="javascript" type="text/javascript" src="../My97DatePicker/WdatePicker.js"></script>
<SCRIPT type=text/javascript>
showMessage("${mess}");
jQuery(function(){
	$("#changeCity").click(function(a){$("#allCity").slideDown(0);a.stopPropagation();$(this).blur()});$("#allCity").click(function(a){a.stopPropagation()});$(document).click(function(a){a.button!=2&&$("#allCity").slideUp(0)});$("#foldin").click(function(){$("#allCity").slideUp(0)})
});
function check(){

  	with(document.forms[0]){
	    if(cm_type.value==""){
	    alert("请选择佣金方式 !");
	     return false
	    }
	    if(cm_value.value==""){
	    alert("请选择业务类型!");
	     return false
	    }
		var val=cm_prod.value;
				if(val == ""){
		alert("请输入佣金百分比 !");
		return false;
		}
		  if(!/^[0-9]+(\.[0-9]+){0,1}$/.test(val)||/^0+[0-9]+(\.[0-9]+){0,1}$/.test(val)){
	        alert("请输入正确的佣金");
	        return false;
            }
		if(val.indexOf(".")!=-1)
		{
			if(val.split(".")[1].length>2){
	     	    alert("佣金小数点只能有两位！");
			    return false;
		    }
	    }
	         if(confirm("确认如此分配?")){
	    document.getElementById("checkCode").value="请稍后...";
		document.getElementById("checkCode").disabled="disabled";
		target="_self";
		submit();
		}else{
		return false;
		}
	}
}
</SCRIPT>
</head>
<body>
<div id="popdiv"><span id="sellist"></span><div style="clear:both"></div></div>
<html:form  method="post" action="/business/prod.do?method=addRebat">
<input name="emtype" value="0" type="hidden">
<div class="pass_main">
	<div class="pass_title_1">添加增值业务佣金</div>
    <div class="pass_list">
    	<ul>
            <li><strong>业务类型 ：</strong><span>
            <div class="sub-input"><a class="sia-2 selhover" id="sl-cm_value" href="javascript:void(0);">请选择</a></div>
			<input name="cm_value" id="cm_value" defaultsel="${requestScope.valueSel }" type="hidden" value="" />
			</span></li>
            <li><strong>佣金方式：</strong><span>
            <div class="sub-input"><a class="sia-2 selhover" id="sl-cm_type" href="javascript:void(0);">请选择</a></div>
			<input name="cm_type" id="cm_type" defaultsel="请选择[]|非直营[0]|直营[1]|" type="hidden" value="" />
			</span></li>
           <li><strong>佣金比：</strong><span><input name="cm_prod" type="text" /><font color="red" size="3">%最多两位小数</font></span></li>
        </ul>
   </div>
   <div class="field-item_button_m">
   <input type="button" value="确认" id="checkCode" onclick="check()" class="field-item_button" />
   <input type="button" value="取消" id="checkCode1" onClick="javascript:history.go(-1)" class="field-item_button" />
   </div>
</div>
</html:form>
</body>
</html>