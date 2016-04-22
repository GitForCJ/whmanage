<%@ page contentType="text/html; charset=GBK" language="java"%>
<%@ page import="java.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<jsp:useBean id="userSession" scope="session" class="com.wlt.webm.rights.form.SysUserForm" />
<%
  String path = request.getContextPath();
 %>
 <html>
<head>
<title>���ͨ</title>
<script type="text/javascript" src="../js/jquery-1.8.2.min.js"></script>
<link href="../css/main_style.css" rel="stylesheet" type="text/css" />
<link href="../css/selectCss.css" rel="stylesheet" type="text/css" /> 
<script language="javascript" type="text/javascript" src="../js/util.js"></script>
<SCRIPT type=text/javascript>
showMessage("${mess}");

function check(){
  	with(document.forms[0]){
		/* areacopeΪ�������ţ�Ҳ����ϵ��α��еĽڵ����� */
		if(minmoney.value == ""||maxmoney.value==""||rate.value==""){
		alert("��������ȷ����!");
		return;
		}
		var myreg = /^[1-9]*[0-9]*$/;
		if(!myreg.test(minmoney.value)||!myreg.test(maxmoney.value)){
		alert("��������ȷ������ !");
		return;
		}
		
		if(!isInt(minmoney.value) || !isInt(maxmoney.value))
		{
			alert("������������!");
			return ;
		}
		
		if(parseFloat(minmoney.value)>=parseFloat(maxmoney.value)){
		alert("��С���ܴ��ڻ���������!");
		return;
		}
		if(!isFloat($("#bl").val()) && !isInt($("#bl").val()))
		{
			alert("ֻ������������С��!");
			return;
		}
		$.ajax({
			type:"post",
			url:"<%=path%>/MonthAwardRule/showMonthAwardRule.do?method=getVerifyMoney&userno="+$("#userno").val()+"&min="+minmoney.value+"&max="+maxmoney.value+"&faceValue="+$('input[name="facevalue"]:checked').val(),
			dataType:"json",
			success:function(obj){
				if(!obj)
				{
					alert("Ӷ�����������ظ�!");
					return ;
				}
				else
				{
					submit();
				}
			}
		}); 
	}
}

function funHistory()
{
	window.location.href="<%=path%>/MonthAwardRule/showMonthAwardRule.do?method=showAwardsAction";
	return;
}
</SCRIPT>
</head>
<body>
<div id="popdiv"><span id="sellist"></span><div style="clear:both"></div></div>
<input type="hidden" id="mess" value="${requestScope.mess }"/>
<form  method="post" action="<%=path%>/MonthAwardRule/showMonthAwardRule.do?method=saveMonthAwardRule">
<div class="pass_main">
	<div class="pass_title_1">��������Ӷ�����</div>
    <div class="pass_list">
    	<ul>
            <li><strong>��½�˺ţ�</strong><span>
            <select name="userno" id="userno" style="width:145px;height:23px;">
	          <c:forEach items="${arryList}" var="arr">
				<option value="${arr[2]};${arr[0]}">${arr[1]}</option>         	
	          </c:forEach>
            </select>
            </span></li>
            <li><strong>�����Сֵ��</strong><span><input name="minmoney" type="text" /></span></li>
            <li><strong>������ֵ��</strong><span><input name="maxmoney" type="text" /></span></li>
            <li><strong>�����ٷֱȣ�</strong><span><input id="bl" name="rate" type="text" /></span></li>
             <li>
             	<strong>����ѡ��</strong>
             	<input type="radio" name="facevalue" value="0" style="height:15px;width:25px;" checked/>��&nbsp;
             	<input type="radio" name="facevalue" value="1" style="height:15px;width:25px;"/>����һ&nbsp;
             	<input type="radio" name="facevalue" value="2" style="height:15px;width:25px;"/>�����&nbsp;
             	<input type="radio" name="facevalue" value="3" style="height:15px;width:25px;"/>������&nbsp;
             	<input type="radio" name="facevalue" value="4" style="height:15px;width:25px;"/>������
             </li>
        </ul>
   </div>
   <div class="field-item_button_m">
   <input type="button" value="ȷ��" id="checkCode" onclick="check()" class="field-item_button" />
   <input type="button" value="ȡ��" id="checkCode" onClick="javascript:funHistory()" class="field-item_button" />
   </div>
</div>
</form>
</body>
</html>