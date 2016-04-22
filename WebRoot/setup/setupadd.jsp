<%@ page contentType="text/html; charset=GBK" language="java"%>
<%@ page import="java.util.*"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<jsp:useBean id="biProd" scope="page" class="com.wlt.webm.business.bean.BiProd"/>
 <%
  String path = request.getContextPath();
 List arrylist=biProd.getgroupname();
 request.setAttribute("arryList",arrylist);
 
 String zName=request.getParameter("zName");
 String curPage=request.getParameter("curPage");
 %>
 <html>
<head>
<title>万汇通</title>
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
	var a3=document.getElementById("a3").value;
	var a4=document.getElementById("a4").value;
	var a5=document.getElementById("a5").value;
	var a6=document.getElementById("a6").value;
	if(a2.Trim()=="")
	{
		alert("请选择交易类型!");
		return ;
	}
	if(a3.Trim()=="")
	{
		alert("请选择组名称!");
		return ;
	}
	if(a4.Trim()=="")
	{
		alert("请输入最小值!");
		return ;
	}
	if(a5.Trim()=="")
	{
		alert("请输入最大值!");
		return ;
	}
	if(a6.Trim()=="")
	{
		alert("请输入奖励比!");
		return ;
	}
	document.getElementById("checkCode").disabled="disabled";
	document.getElementById("for").submit();
	return ;
}
// 去空格
String.prototype.Trim = function() 
{ 
	return this.replace(/(^\s*)|(\s*$)/g, ""); 
} 
</SCRIPT>
</head>
<body>
<form id="for"  method="post" action="<%=path %>/business/prod.do?method=insertOneAgent">
<div class="pass_main">
	<div class="pass_title_1">添加阶梯组</div>
    <div class="pass_list">
    	<ul>
			<li>
				<strong>交易类型：</strong>
				<span>
					<select  style="width:145px;height:20px;" name="a2" id="a2">
						<option  value="">请选择</option>
						<option  value="1">本省移动</option>
						<option  value="2">外省移动</option>
						<option  value="3">本省联通</option>
						<option  value="4">外省联通</option>
						<option  value="5">本省电信</option>
						<option  value="6">外省电信</option>
						<option  value="7">qq币</option>
					</select>
				</span>
			</li>
			<li>
				<strong>组名称：</strong>
				<span>
					<select  style="width:145px;height:20px;" id="a3" name="a3">
						<option  value="">请选择</option>
						<c:forEach items="${arryList}" var="arr">
		  					<option value="${arr[0]}">${arr[1]}</option>
		  				</c:forEach>
					</select>
				</span>
			</li>
           <li><strong>最小值：</strong><span><input id="a4" name="a4" type="text" /><font color="red" size="3">元</font></span></li>
           <li><strong>最大值：</strong><span><input id="a5" name="a5" type="text" /><font color="red" size="3">元</font></span></li>
           <li><strong>奖励比：</strong><span><input id="a6" name="a6" type="text" /><font color="red" size="3">%最多两位小数</font></span></li>
        </ul>
   </div>
   <div class="field-item_button_m">
   <input type="button" value="确认" id="checkCode" onclick="check()" class="field-item_button" />
   <input type="button" value="取消" id="checkCode1" onClick="javascript:window.location.href='<%=path %>/business/prod.do?method=oneagentList&zName=<%=zName%>&curPage=<%=curPage%>'" class="field-item_button" />
   </div>
</div>
</form>
</body>
</html>