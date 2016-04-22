<%@ page contentType="text/html; charset=GBK" language="java"%>
<%@ page import="java.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<jsp:useBean id="userSession" scope="session" class="com.wlt.webm.rights.form.SysUserForm" />
<jsp:useBean id="prod" scope="page" class="com.wlt.webm.business.bean.BiProd"/>
 <%
	 List areaList = prod.getAreaList();
	 StringBuffer sBuffer1 = new StringBuffer();
	 sBuffer1.append("请选择[]");
	 for(Object tmp : areaList){
		String[] temp = (String[])tmp;
		sBuffer1.append("|"+temp[1]+"["+temp[0]+"]");
	 }
	 request.setAttribute("areaSel", sBuffer1.toString());
	 
	 List valueList = prod.getValueList();
	 StringBuffer sBuffer2 = new StringBuffer();
	 sBuffer1.append("请选择[]");
	 for(Object tmp : valueList){
		String[] temp = (String[])tmp;
		sBuffer2.append("|"+temp[0]+"["+temp[1]+"]");
	 }
	 request.setAttribute("valueSel", sBuffer2.toString());
	 
	 List arryList=prod.getInterName();
	  StringBuffer strs = new StringBuffer();
	 strs.append("请选择[]");
	 for(Object tmp : arryList){
		String[] temp = (String[])tmp;
		strs.append("|"+temp[2]+"/"+temp[1]+"["+temp[0]+"]");
	 }
	 request.setAttribute("interName", strs.toString());
 %>
 <html>
<head>
<title>万汇通</title>
<script type="text/javascript" src="../js/jquery-1.8.2.min.js"></script>
<script type="text/javascript" src="../js/xml_cascade.js"></script>
<link href="../css/main_style.css" rel="stylesheet" type="text/css" />
<link href="../css/selectCss.css" rel="stylesheet" type="text/css" /> 
<script src="../js/selectCss.js" type="text/javascript"></script> 
<SCRIPT type=text/javascript>
jQuery(function(){
	if($("#mess").val() != ""){
		alert($("#mess").val());
	}
});
function check(){
  	with(document.forms[0]){
  		if(cm_interName.value=="")
  		{
  			alert("请选择要分配的接口商!");
  			return false;
  		}
  		if(cm_area.value==""){
	    alert("请选择省份 !");
	     return false
	    }
	    if(cm_type.value==""){
	    alert("请选择运营商类型 !");
	     return false
	    }
	    if(cm_value.value==""){
	    alert("请选择面额区间 !");
	     return false
	    }
		var val=cm_prod.value;
				if(val == ""){
		alert("请输入佣金百分比 !");
		return false;
		}
     	if(!/^-?(?:\d+|\d{1,3}(?:,\d{3})+)(?:\.\d+)?$/.test(val)){
	        alert("请输入正确的数字");
	        return false;
            }
	if(typeof(val.split(".")[1])!="undefined" && typeof(val.split(".")[1])!=null)
		{
			if(val.split(".")[1].length>2){
	     	    alert("交易金额的小数点只能有两位！");
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
<input type="hidden" id="mess" value="${requestScope.mess }"/>
<html:form  method="post" action="/business/prod.do?method=tpaddemploy">
<div class="pass_main">
	<div class="pass_title_1">添加佣金</div>
    <div class="pass_list">
    	<ul>
    		<li><strong>分配接口商名称 ：</strong><span>
            <div class="sub-input"><a class="sia-2 selhover" id="sl-cm_interName" href="javascript:void(0);" style="width:200px;">请选择</a></div>
			<input style="width:200px;" name="cm_interName" id="cm_interName" defaultsel="${requestScope.interName }" type="hidden" value="" />
			
            <li><strong>省份 ：</strong><span>
            <div class="sub-input"><a class="sia-2 selhover" id="sl-cm_area" href="javascript:void(0);">请选择</a></div>
			<input name="cm_area" id="cm_area" defaultsel="${requestScope.areaSel }" type="hidden" value="" />
			</span></li>
            <li><strong>业务类型：</strong><span>
            <div class="sub-input"><a class="sia-2 selhover" id="sl-cm_type" href="javascript:void(0);">请选择</a></div>
			<input name="cm_type" id="cm_type" defaultsel="请选择[]|移动[1]|联通[2]|电信[0]" type="hidden" value="" />
			</span></li>
            <li><strong>面额区间 ：</strong><span>
            <div class="sub-input"><a class="sia-2 selhover" id="sl-cm_value" href="javascript:void(0);">请选择</a></div>
			<input name="cm_value" id="cm_value" defaultsel="${requestScope.valueSel }" type="hidden" value="" />
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