<%@ page contentType="text/html; charset=GBK" language="java"%>
<%
String path = request.getContextPath();
%>
<%@ page import="java.util.*" %>
<%@ page import="com.wlt.webm.util.*"%>
<%@ page import="com.commsoft.epay.util.logging.Log"%>
<%@ page import="com.wlt.webm.ten.service.*" %>
<%
	TenpayXmlPath test = new TenpayXmlPath();
	String total_fee=request.getParameter("order_price"); 
	float   sum   =   Float.parseFloat(total_fee)*100;     //转换成float
	int   i   =   (int)sum;
	total_fee=String.valueOf(i);
	String bargainor_id = TenpayConfigParser.getConfig().getBargainor_id();
	String key = TenpayConfigParser.getConfig().getKey();
	String return_url = TenpayConfigParser.getConfig().getReturn_url();
     String currTime = TenpayUtil.getCurrTime();
	String transaction_id =TenpayUtil.get28Chars(currTime);
	PayRequestHandler reqHandler = new PayRequestHandler(request,response);
	reqHandler.setKey(key);
	reqHandler.init();
	reqHandler.setParameter("bargainor_id", bargainor_id); //商户号
	reqHandler.setParameter("transaction_id", transaction_id); //财付通交易单号
	reqHandler.setParameter("return_url", return_url); //支付通知url
	reqHandler.setParameter("desc", new String("深圳市万恒科技有限公司额度充值".getBytes(),"GBK")); //商品名称
	reqHandler.setParameter("total_fee", total_fee); //商品金额,以分为单位
	reqHandler.setParameter("spbill_create_ip", request.getRemoteAddr());
	reqHandler.logTransferToDB(bargainor_id,transaction_id, request.getRemoteAddr(),total_fee,currTime);
	String requestUrl = reqHandler.getRequestURL();
	String debuginfo = reqHandler.getDebugInfo();
	Log.info("财付通转账---requestUrl:" + requestUrl);
	Log.info("财付通转账---debuginfo:" + debuginfo);
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gbk">
<title>Insert title here</title>
<title></title>
<link href="../rights/css/member.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="../js/jquery-1.8.2.min.js"></script>
<script type="text/javascript">
	function fun(requestUrl)
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
		window.parent.document.getElementById("responseUrl").value="<%=path%>/wlttencent/tepay.jsp";
	}
</script>
</head>
<body style="font-size: 16px;background-color: #E4F4FC;">
<div class="navigation">
<div class="crumbs ">
<a href="#">账户管理</a>
<a href="#">转账付款</a>
</div>
</div>
  
<div class="rcontent">
<table>
<thead>
<tr>
<th>确定转帐信息&nbsp;<a href="tepay.jsp">返回上一步</a></th>
</tr>
</thead>
<tbody>
<tr>
<td  style="padding-left:40px;"><div class="tab1">产品类型：深圳市万恒科技额度购买</div>
<div class="tab1">转账方式：财付通</div>
<div class="tab1">转账金额：<%= request.getParameter("order_price")%> 元</div>
<div class="tab1"><span class="btn3"><a href="<%=requestUrl%>" onclick="fun('<%=requestUrl%>')" target="_blank">点击确定使用财付通转账</a></span></div>
</td>

</tr>
</tbody>
</table>


</div>  

</body>
</html>