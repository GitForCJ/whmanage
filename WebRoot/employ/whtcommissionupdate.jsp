<%@ page contentType="text/html; charset=GBK" language="java"%>
<%@ page import="java.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<jsp:useBean id="userSession" scope="session" class="com.wlt.webm.rights.form.SysUserForm" />
<jsp:useBean id="prod" scope="page" class="com.wlt.webm.business.bean.BiProd"/>
 <%
	// request.setAttribute("arealist", prod.getAreaList());
	   String st=(String)request.getAttribute("st");
	     String id=(String)request.getAttribute("id");
	//  System.out.println("==="+st);
	 //   System.out.println("==="+id);
 %>
 <html>
<head>
<title>���ͨ</title>
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
				if(val == ""){
		alert("������Ӷ��ٷֱ� !");
		return false;
		}
		if(val.substring(0,1)=="-")
		{
			var strtostring=val.substring(1,val.length);
			if(!/^[0-9]+(\.[0-9]+){0,1}$/.test(strtostring)||/^0+[0-9]+(\.[0-9]+){0,1}$/.test(strtostring))
			{
	       	 alert("��������ȷ������");
	       	 return false;
            }
		}
		else
		{
			if(!/^[0-9]+(\.[0-9]+){0,1}$/.test(val)||/^0+[0-9]+(\.[0-9]+){0,1}$/.test(val))
			{
	       	 alert("��������ȷ������");
	       	 return false;
            }
		}
		
		if(val.split(".")[1].length>2){
     	    alert("���׽���С����ֻ������λ��");
		    return false;
	    } 
	    if(confirm("ȷ����˷���?")){
	    document.getElementById("checkCode").value="���Ժ�...";
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
<html:form  method="post" action="/business/prod.do?method=delEmploy&k=1&curPage=${curPage}&cm_one=${userForm.cm_one}&yunyingshang=${userForm.yunyingshang}&miane=${userForm.miane}">
<input type="hidden" name="st"  value="<%=st %>" />
<input type="hidden" name="id"  value="<%=id %>" />
<input name="emtype" value="0" type="hidden">
<div class="pass_main">
	<div class="pass_title_1">�޸�Ӷ�����</div>
    <div class="pass_list">
    	<ul>
            <li><strong>ʡ�� ��</strong><span>
			<input name="cm_area" id="cm_area" value="${str[1]}" readonly="readonly"/>
			</span></li>
            <li><strong>ҵ�����ͣ�</strong><span>
			<input name="cm_type" id="cm_type" value="${str[2] }" readonly="readonly"/>
			</span></li>
            <li><strong>������� ��</strong><span>
			<input name="cm_value" id="cm_value" value="${str[3] }" readonly="readonly"/>
			</span></li>

           <li><strong>Ӷ��ȣ�</strong><span><input name="cm_prod" type="text" value="${str[4]}" /><font color="red" size="3">%�����λС��</font></span></li>
        </ul>
   </div>
   <div class="field-item_button_m">
   <input type="button" value="ȷ��" id="checkCode" onclick="check()" class="field-item_button" />
   <input type="button" value="ȡ��" id="checkCode1" onClick="javascript:history.go(-1)" class="field-item_button" />
   </div>
</div>
</html:form>
</body>
</html>