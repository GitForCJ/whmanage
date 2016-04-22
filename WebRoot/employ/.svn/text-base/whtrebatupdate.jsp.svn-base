<%@ page contentType="text/html; charset=GBK" language="java"%>
<%@ page import="java.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<jsp:useBean id="userSession" scope="session" class="com.wlt.webm.rights.form.SysUserForm" />
 <html>
<head>
<title>万汇通</title>
<script type="text/javascript" src="../js/util.js"></script>
<link href="../css/main_style.css" rel="stylesheet" type="text/css" />
<SCRIPT type=text/javascript>
showMessage("${mess}");
function check(){
  	with(document.forms[0]){
		var val=cm_prod.value;
				if(val == ""){
		alert("请输入佣金百分比 !");
		return false;
		}
		  if(!/^-?(?:\d+|\d{1,3}(?:,\d{3})+)(?:\.\d+)?$/.test(val)){
	        alert("请输入正确的佣金");
	        return false;
            }

		if(val.split(".")[1].length>2){
     	    alert("佣金小数点只能有两位！");
		    return false;
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
<html:form  method="post" action="/business/prod.do?method=delRebat&flag=1">
<input name="id" value="${str[0]}" type="hidden">
<div class="pass_main">
	<div class="pass_title_1">添加增值业务佣金</div>
    <div class="pass_list">
    	<ul>
            <li><strong>业务类型 ：</strong><span>
			<input name="cm_value" id="cm_value"  value="${str[1]}" readonly="readonly"/>
			</span></li>
            <li><strong>佣金方式：</strong><span>
			<input name="cm_type" id="cm_type"  value="${str[3]}" readonly="readonly"/>
			</span></li>
           <li><strong>佣金比：</strong><span><input name="cm_prod" type="text" value="${str[2]}" /><font color="red" size="3">%最多两位小数</font></span></li>
        </ul>
   </div>
   <div class="field-item_button_m">
   <input type="button" value="修改" id="checkCode" onclick="check()" class="field-item_button" />
   <input type="button" value="取消" id="checkCode1" onClick="javascript:history.go(-1)" class="field-item_button" />
   </div>
</div>
</html:form>
</body>
</html>