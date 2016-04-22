<%@ page contentType="text/html; charset=GBK" language="java"%>
<%
String path = request.getContextPath();
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gbk">
<title>Insert title here</title>
<title></title>
<link href="../rights/css/member.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="../js/jquery-1.8.2.min.js"></script>
<script type="text/javascript">
	function funA(requestUrl)
	{
		window.parent.document.body.style.overflowX="hidden";
		window.parent.document.body.style.overflowY="hidden";
		
		var div=window.parent.document.getElementById("zzc");
		
		var sTop=window.parent.document.body.scrollTop+window.parent.document.documentElement.scrollTop;
		div.style.top=sTop+"px";
		div.style.display="block";
		
		var divTwo=window.parent.document.getElementById("zzcTwo");
		divTwo.style.left=(window.parent.document.body.clientWidth-500)/2+"px";
		divTwo.style.top=(window.screen.height-200)/2+sTop-50+"px";
		divTwo.style.display="block";
		
		// 关闭弹出层后的 ifream 页面路径
		window.parent.document.getElementById("responseUrl").value="<%=path%>/AccountInfo/TyPortPay.jsp";
	}
</script>
</head>
<body style="font-size: 16px;background-color: #E4F4FC;">
<div class="navigation"style="width:99%">
<div class="crumbs ">
<a href="#">账户管理</a>
<a href="#">转账付款</a>
</div>
</div>
  
<div class="rcontent" style="width:97%">
<table border="1">
<thead>
<tr>
<th>确定转帐信息&nbsp;<a href="<%=path%>/AccountInfo/TyPortPay.jsp">返回上一步</a></th>
</tr>
</thead>
<tbody>
<tr>
<td  style="padding-left:40px;"><div class="tab1">产品类型：深圳市万恒科技额度购买</div>
<div class="tab1">转账方式：翼支付转账</div>
<div class="tab1">转账金额：${moeny} 元</div>
<div class="tab1"><span class="btn3"><a href="${requestUrl}" onclick="funA('${requestUrl}')" target="_blank">点击确定使用翼支付转账</a></span></div>
</td>

</tr>
</tbody>
</table>


</div>  

</body>
</html>