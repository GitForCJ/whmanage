<%@ page contentType="text/html; charset=GBK" language="java"%>
<!-- 
<jsp:directive.page import="com.wlt.webm.db.DBConfig"/>
 -->
<%
//String minTransferFee = DBConfig.getInstance().getCftMinFee().trim();
//String handlingCharge = DBConfig.getInstance().getCftHandleCharge().trim();
//String maxTransferFee = DBConfig.getInstance().getCftMaxFee().trim();

//request.setAttribute("minTransferFee",minTransferFee);
//request.setAttribute("handlingCharge",handlingCharge);
//request.setAttribute("maxTransferFee",maxTransferFee);
%>
<html>
<head>
<script type="text/javascript">window.onerror = function(){return true;}</script>
<meta http-equiv="Content-Type" content="text/html; charset=GBK" />
<title></title>
<script type="text/javascript" src="../js/jquery-1.8.2.min.js"></script>
<link href="../css/main_style.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">

	function payFrm()
	{
     	var val=directFrm.order_price.value;
     	 //   var minFee =directFrm.minTransferFee.value;
		//	var maxFee = directFrm.maxTransferFee.value;
		//	var hc = directFrm.handlingCharge.value;
					if (directFrm.order_price.value==""||directFrm.order_price.value==0)
		{
			alert("提醒：请填写转账金额！");
			directFrm.order_price.focus();
			return false;
		} 
//			if(parseFloat(val) - 50000>0){
	//			alert("转账金额必须小于50000元！");
	//			return false;
	//		}
        if(parseFloat(val)<1){
				if(hc.value == 1){
					alert("转账金额不能小于1元！");
					return false;
				} 
   			}
     	if(!/^[0-9]+(\.[0-9]+){0,1}$/.test(val)||/^0+[0-9]+(\.[0-9]+){0,1}$/.test(val)){
	        alert("请输入正确的数字");
	        return false;
            }

		if(directFrm.order_price.value.split(".")[1].length>2){
     	    alert("交易金额的小数点只能有两位！");
		    return false;
	    } 


	    document.forms[0].OK.disabled=true;
	    document.forms[0].OK.value="请稍后...";
		return true;
	}
	
	function keydownEvent(){
	if(event.keyCode==13){
	event.returnValue=false;
	}
	}
</script>

</head>
<body>

<form action="tenpayconfirm.jsp" method="post" name="directFrm" onSubmit="return payFrm();">
<div class="navigation">
<div class="crumbs ">
<a href="#">账户管理</a>
<a href="#">财付通转账</a>
</div>
</div>
  
<div class="rcontent">
<table>
<thead>
<tr>
<th>财付通转账</th>
</tr>
</thead>
<tbody>
<tr>
<td  style="padding-left:10%;"><div class="tab1">转账方式：财付通转账</div>
<div class="tab1">转账金额：<input style="font-size: 20px;height: 30px;width:70px;color: blue;" name="order_price" type="text" onkeyup="if(isNaN(value))execCommand('undo')" onafterpaste="if(isNaN(value))execCommand('undo')" onkeydown="keydownEvent()"/> 元</div>
<div class="tab1"><span class="btn_m">
<INPUT  class=btn2  type="submit" value="确定" name="OK"></span><span class="btn_m">
<INPUT  class=btn2  type="reset" value="重写" id="checkCode" onClick="validate();"></span></div>
</td>

</tr>
</tbody>
</table>
</div>  

</div>
</form>
<div style="margin-left: 100;font-size: 13px; color:#333333;overflow: hidden;float:left;">
1、支持通过银行网银或财付通账户购买额度。
<br>
2、交易金额低于2000元/笔扣1元手续费，大于等于2000元/笔免费。
<br>
3、额度实时到账。
</div>
</body>
</html>

