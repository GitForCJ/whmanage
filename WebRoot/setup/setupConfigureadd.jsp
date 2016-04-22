<%@ page contentType="text/html; charset=GBK" language="java"%>
<%@ page import="java.util.*"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<jsp:useBean id="biProd" scope="page" class="com.wlt.webm.business.bean.BiProd"/>
 <%
  String path = request.getContextPath();
 List arrylist=biProd.getgroupname();
 request.setAttribute("arryList",arrylist);
 
 List userList=biProd.getuserList();
 request.setAttribute("userList",userList);
 
 String zName=request.getParameter("zName");
 String curPage=request.getParameter("curPage");
 %>
 <html>
<head>
<title>���ͨ</title>
<script type="text/javascript" src="../js/jquery-1.8.2.min.js"></script>
<script type="text/javascript" src="../js/xml_cascade.js"></script>
<link href="../css/main_style.css" rel="stylesheet" type="text/css" />
<link href="../css/selectCss.css" rel="stylesheet" type="text/css" /> 
<script src="../js/selectCss.js" type="text/javascript"></script> 
<script type="text/javascript" src="../js/util.js"></script>
<SCRIPT type=text/javascript>
showMessage("${mess}");
function check()
{
	var a2=document.getElementById("a2").value;
	var a1=document.getElementById("a1").value;
	if(a1.Trim()=="")
	{
		alert("��ѡ��������!");
		return ;
	}
	if(a2.Trim()=="")
	{
		alert("��ѡ��ӳ���û�!");
		return ;
	}
	document.getElementById("checkCode").disabled="disabled";
	document.getElementById("for").submit();
	return ;
}
// ȥ�ո�
String.prototype.Trim = function() 
{ 
	return this.replace(/(^\s*)|(\s*$)/g, ""); 
} 
</SCRIPT>
</head>
<body>
<form id="for"  method="post" action="<%=path %>/business/prod.do?method=insertConfigure">
<div class="pass_main">
	<div class="pass_title_1">��ӽ�����ӳ��</div>
    <div class="pass_list">
    	<ul>
			<li>
				<strong>�����ƣ�</strong>
				<span>
					<select  style="width:145px;height:25px;" id="a1" name="a1">
						<option  value="">��ѡ��</option>
						<c:forEach items="${arryList}" var="arr">
		  					<option value="${arr[0]}">${arr[1]}</option>
		  				</c:forEach>
					</select>
				</span>
			</li>
			<li>
				<strong>һ�������û���</strong>
				<span>
					<select  style="width:145px;height:25px;" id="a2" name="a2">
						<option  value="">��ѡ��</option>
						<c:forEach items="${userList}" var="arr">
		  					<option value="${arr[0]}">${arr[1]}</option>
		  				</c:forEach>
					</select>
				</span>
			</li>
        </ul>
   </div>
   <div class="field-item_button_m">
   <input type="button" value="ȷ��" id="checkCode" onclick="check()" class="field-item_button" />
   <input type="button" value="ȡ��" id="checkCode1" onClick="javascript:window.location.href='<%=path %>/business/prod.do?method=configureList&zName=<%=zName%>&curPage=<%=curPage%>'" class="field-item_button" />
   </div>
</div>
</form>
</body>
</html>