<%@ page contentType="text/html; charset=GBK" language="java"%>
<%@ page import="java.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%
  String path = request.getContextPath();
%>
<html>
	<head>
		<title>���ͨ</title>
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
		<div id="popdiv">
			<span id="sellist"></span>
			<div style="clear: both"></div>
		</div>
		<input type="hidden" id="mess" value="${requestScope.mess }" />
		<html:form method="post"
			action="/rights/reversal.do?method=jtfklist&flag=1">
			<input name="emtype" value="0" type="hidden">
			<div class="pass_main">
				<div class="pass_title_1">
					��ͨ��������
				</div>
				<div class="pass_list">
					<ul>
						<li>
							<strong>�������ͣ�</strong><span>
 <select name="car" style="width: 100px;">
<option value="1">С������</option>
<option value="2">��С������</option>
 </select>
							</span>
						</li>
						<li>
							<strong>�������ͣ�</strong>
							<span>
 <select name="op" style="width: 100px;">
<option value="1">��ѯ</option>
<option value="2">�µ�</option>
 </select>
							</span>
						</li>
						<li id="aa">
							<strong>�������ͣ�</strong>
							<span>
 <select name="qd" style="width: 100px;">
<option value="0">���</option>
<option value="1">����</option>
<option value="2">���°�</option>
 </select>
							</span>
						</li>
						<li>
							
						</li>
					</ul>
				</div>
				<div class="field-item_button_m">
					<input type="button" value="ȷ��" id="checkCode" onclick="check()"
						class="field-item_button" />
					<input type="button" value="����" id="checkCode1"
						onClick="javascript:window.location.href='<%=path %>/rights/reversal.do?method=jtfklist&flag=0'" class="field-item_button" />
				</div>
			</div>
		</html:form>
	</body>
</html>