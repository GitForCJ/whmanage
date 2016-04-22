<%@ page contentType="text/html; charset=GBK" language="java"%>
<%@ page import="java.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<jsp:useBean id="userSession" scope="session" class="com.wlt.webm.rights.form.SysUserForm" />
<jsp:useBean id="prod" scope="page" class="com.wlt.webm.business.bean.BiProd"/>
 <%
	     String id=(String)request.getAttribute("id");
 %>
 <html>
<head>
<title>万汇通</title>
<script type="text/javascript" src="../js/jquery-1.8.2.min.js"></script>
<link href="../css/main_style.css" rel="stylesheet" type="text/css" />
<SCRIPT type=text/javascript>
jQuery(function(){
	$("#changeCity").click(function(a){$("#allCity").slideDown(0);a.stopPropagation();$(this).blur()});$("#allCity").click(function(a){a.stopPropagation()});$(document).click(function(a){a.button!=2&&$("#allCity").slideUp(0)});$("#foldin").click(function(){$("#allCity").slideUp(0)})
	if($("#mess").val() != ""){
		alert($("#mess").val());
	}
});
function check(){
  	with(document.forms[0]){
		var val=cm_prod.value;
		var val1=cm_prod1.value;
				if(val == ""||val1==""){
		alert("请输入金额 !");
		return false;
		}
     	if(!/^[0-9]+(\.[0-9]+){0,1}$/.test(val)||/^0+[0-9]+(\.[0-9]+){0,1}$/.test(val)){
	        alert("请输入正确的数字");
	        return false;
            }

//		if(val.split(".")[1].length>0){
//     	    alert("金额只能为小数！");
//		    return false;
//  } 
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
<input type="hidden" id="mess" value="${requestScope.mess }"/>
<html:form  method="post" action="/business/prod.do?method=delminEmploy&k=1&curPage=${curPage}">
<input type="hidden" name="id"  value="<%=id %>" />
<input name="emtype" value="0" type="hidden">
<div class="pass_main">
	<div class="pass_title_1">修改佣金比例</div>
    <div class="pass_list">
    	<ul>
            <li><strong>省份 ：</strong><span>
			<input name="cm_area" id="cm_area" value="${str[1]}" readonly="readonly"/>
			</span></li>
            <li><strong>业务类型：</strong><span>
			<input name="cm_type" id="cm_type" value="${str[2] }" readonly="readonly"/>
			</span></li>
            <li><strong>面额区间 ：</strong><span>
			<input name="cm_value" id="cm_value" value="${str[3] }" readonly="readonly"/>
			</span></li>

           <li><strong>交易金额：</strong><span><input name="cm_prod" type="text" value="${str[4]}" /><font color="red" size="3">*</font></span></li>
           <li><strong>返佣金额：</strong><span><input name="cm_prod1" type="text" value="${str[5]}" /><font color="red" size="3">*</font></span></li>       
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