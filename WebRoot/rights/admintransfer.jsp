<%@ page contentType="text/html; charset=GBK" language="java"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
 <html>
<head>
<title>���ͨ</title>
<script type="text/javascript" src="../js/jquery-1.8.2.min.js"></script>
<link href="../css/main_style.css" rel="stylesheet" type="text/css" />
<script type=text/javascript>
$(function(){
	if($("#mess").val() != ''){
		alert($("#mess").val());
	}
});
function submitForm(){
  	with(document.forms[0]){
  	    if(!/^[0-9]+(\.[0-9]+){0,1}$/.test($("#chargeFee").val())||/^0+[0-9]+(\.[0-9]+){0,1}$/.test($("#chargeFee").val())){
	        alert("��������ȷ�� ���");
	        return false;
            }
//	     if($("#chargeFee").val().split(".")[1].length>2){
    // 	    alert("����С����ֻ������λ��");
	//	    return false;
	 //   } 
  		if($("#username").val() == ""){
  			alert("������ת���˻�");
  			return false;
  		}
  		if($("#username").val() != $("#confirmname").val()){
  			alert("�������벻һ��");
  			return false;
  		}
		submit();
		return true;
	}
}
</script>
</head>
<body>
<input type="hidden" id="mess" value="${requestScope.mess }"/>
<div id="tabbox">
  <div id="tabs_title">
    <ul class="tabs_wzdh">
      <li>�˻�����</li></ul>
  </div>
</div>
<html:form  method="post" action="/rights/sysuser.do?method=adminTransfer">
<div class="pass_main">
    <div class="pass_list">
    	<ul>
            <li><strong>���ֽ�</strong><span><input name="chargeFee" id="chargeFee" type="text"/>Ԫ</span></li>
            <li><strong>�����˻���</strong><span>
			<input type="text" name="username" id="username"><font color="red">(�˺�Ϊ�ʽ��˺�)</font>
			</span></li>
			<li><strong>�ظ��˻���</strong><span>
			<input type="text" name="confirmname" id="confirmname">
			</span></li>
        </ul>
   </div>
   <div class="field-item_button_m">
   <input type="button" value="����" id="btnSubmit" onclick="return submitForm();" class="field-item_button" />
   </div>
    
</div>
  <div style="padding-left:40px;padding-top:10px;margin-left: 20px;font-size: 13px; color: #FF6600;font-weight:bold;border:1px solid #FF9E4d;background-color: #FFFFE5;">
	<ul>
	<li style="list-style:none;">1�����ֹ��ܲ�����,����ϸȷ���˻���Ϣ����������</li>
	<li style="list-style:none;">&nbsp;</li>
	</ul>
	</div>
</html:form>
</body>
</html>