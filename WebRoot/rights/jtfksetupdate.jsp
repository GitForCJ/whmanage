<%@ page contentType="text/html; charset=GBK" language="java"%>
<%@ page import="java.util.*"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
	<%
  String path = request.getContextPath();
  String[] urlp=request.getParameter("ids").split("#");
  System.out.println(urlp[0]+"  "+urlp[1]+"   "+urlp[2]+"   "+urlp[3]);
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
			action="/rights/reversal.do?method=jtfklist&flag=2">
			<input name="emtype" value="0" type="hidden">
			<div class="pass_main">
				<div class="pass_title_1">
					��ͨ��������
				</div>
				<div class="pass_list">
					<ul>
						<li>
							<strong>�������ͣ�</strong><span>
 <select name="car" style="width: 100px;" disabled>
<option value="1" <c:if test="${urlp[1]==1}">selected="selected"></c:if> >С�ͳ�</option>
<option value="2" <c:if test="${urlp[1]==2}">selected="selected"></c:if>> ��С������</option>
 </select>
							</span>
						</li>
						<li>
							<strong>�������ͣ�</strong>
							<span>
 <select name="op" style="width: 100px;" disabled>
<option value="1" <c:if test="${urlp[1]==1}">selected="selected"></c:if>>��ѯ</option>
<option value="2" <c:if test="${urlp[1]==2}">selected="selected"></c:if>>�µ�</option>
 </select>
							</span>
						</li>
						<li id="aa">
							<strong>�������ͣ�</strong>
							<span>
 <select name="qd" style="width: 100px;">
<option value="0" <c:if test="${urlp[1]==0}">selected="selected"></c:if>>���</option>
<option value="1" <c:if test="${urlp[1]==1}">selected="selected"></c:if>>����</option>
<option value="2" <c:if test="${urlp[1]==2}">selected="selected"></c:if>>���°�</option>
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