<%@ page contentType="text/html; charset=gb2312" language="java"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>财付通</title>
<link href="../css/chongzhi_mobile.css" rel="stylesheet" type="text/css" />
<script src="../js/jquery-1.8.2.min.js" type="text/javascript"></script> 
<script  type="text/javascript" src="../js/util.js"></script>
<script language='javascript' src='../js/jquery.js'></script>
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
				//if(hc.value == 1){
					alert("转账金额不能小于1元！");
					return false;
				//} 
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
<div id="tabbox">
  <div id="tabs_title">
    <ul class="tabs_wzdh">
      <li>财付通转账</li></ul>
  </div>
</div>
    <div class="mobile-charge" >
<form action="tenpayconfirm.jsp" method="post" name="directFrm" onSubmit="return payFrm();">
        <fieldset>
               <div class="pass_list" style="margin-left: 150">
   	  <ul>
            <li style="font-size: 12px;"><strong  style="margin-top:10px;">转款方式：</strong><span><img src="<%=path%>/images/caifutong.jpg" style="border:1px solid #cccccc;width:170px;height:45px;"/></span></li>
            <li style="font-size: 12px;"><strong>转款金额：</strong><span><input style="font-size: 20px;height: 30px;width:80px;color: blue;" name="order_price" type="text" onkeyup="if(isNaN(value))execCommand('undo')" onafterpaste="if(isNaN(value))execCommand('undo')" onkeydown="keydownEvent()"/> 元
</span></li>
        </ul>
    </div>
</fieldset>
<div style="margin-left: 20px;" />
<input type="submit" value="确定" name="OK" class="field-item_button" onclick="check();" />
<input type="reset" value="重写" id="checkCode" onClick="validate();" style="margin-left: 10px;" class="field-item_button" onclick="check();" />
</div>
        
      </form>
    </div>
 <div style="font-size: 13px; color:#333333; red;margin-left: 150px;clear:both;">

</div>
<div style="padding-left:40px;padding-top:10px;margin-left: 20px;font-size: 13px; color: #FF6600;font-weight:bold;border:1px solid #FF9E4d;background-color: #FFFFE5;">
<ul>
<li style="list-style:none;">1、支持通过银行网银或财付通账户购买额度。</li>
<li style="list-style:none;">&nbsp;</li>
<li style="list-style:none;">2、交易金额低于2000元/笔扣1元手续费，大于等于2000元/笔免费。</li>
<li style="list-style:none;">&nbsp;</li>
<li style="list-style:none;">3、额度实时到账。</li>
<li style="list-style:none;">&nbsp;</li>
<li style="list-style:none;">4、信用卡支持：中国工商银行、招商银行、中国建设银行、中国银行、浦发银行、广发银行、中国民生银行、中国光大银行、兴业银行、交通银行、东亚银行,信用卡支付单笔金额大于等于2000元，手续费全免。</li>
<li style="list-style:none;">&nbsp;</li>
<li style="list-style:none;">5、信用卡支付额度与银行政策及个人支付权限有关。</li>
<li style="list-style:none;">&nbsp;</li>
<li style="list-style:none;">6、企业银行支持：中国工商银行、招商银行、中国建设银行、中国农业银行、浦发银行、光大银行。</li>
<li style="list-style:none;">&nbsp;</li>
</ul>
</div>
</body>
</html>
