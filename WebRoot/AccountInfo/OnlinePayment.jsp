<%@ page contentType="text/html; charset=gb2312" language="java"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:useBean id="spa" scope="page" class="com.wlt.webm.business.MobileChargeService"/>
<jsp:useBean id="dd" scope="page" class="com.wlt.webm.util.Tools"/>
<%
String path = request.getContextPath();
boolean bool=spa.isDateLimit(dd.getNow3());
request.setAttribute("status",bool);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>在线支付</title>
<link href="../css/chongzhi_mobile.css" rel="stylesheet" type="text/css" />
<script src="../js/jquery-1.8.2.min.js" type="text/javascript"></script> 
<script  type="text/javascript" src="../js/util.js"></script>
<script language='javascript' src='../js/jquery.js'></script>
<link href="../css/main_style.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">

	function payFrm()
	{
     	var val=directFrm.order_price.value;
		if (directFrm.order_price.value==""||directFrm.order_price.value==0)
		{
			alert("提醒：请填写转账金额！");
			directFrm.order_price.focus();
			return false;
		} 
        if(parseFloat(val)<10){
			alert("转账金额不能小于10元！");
			return false;
   			}
     	if(!/^[0-9]+(\.[0-9]+){0,1}$/.test(val)||/^0+[0-9]+(\.[0-9]+){0,1}$/.test(val)){
	        alert("请输入正确的数字");
	        return false;
            }
        if(directFrm.order_price.value.indexOf(".")!=-1)
        {
			if(directFrm.order_price.value.split(".")[1].length>2){
	     	    alert("交易金额的小数点只能有两位！");
			    return false;
		    }
	    } 
		if("${status}"=="true")
		{
			if(confirm("周五、周六或者指定日期，通过翼支付网关加款的额度,周日才提到资金账户进行使用，详情请见公告"))
			{
		        document.forms[0].OK.disabled=true;
			    document.forms[0].OK.value="请稍后...";
				return true;
			}
			else
			{
			    return false;
			}
		}
		else
		{
		    document.forms[0].OK.disabled=true;
		    document.forms[0].OK.value="请稍后...";
			return true;
		}
		
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
      <li>银联在线</li></ul>
  </div>
</div>
    <div class="mobile-charge" >
<form action="<%=path%>/business/bank.do?method=OnlinePayment" method="post" name="directFrm" onSubmit="return payFrm();">
        <fieldset>
               <div class="pass_list" style="margin-left: 150">
   	  <ul>
            <li style="font-size: 12px;"><strong style="margin-top:10px;">转款方式：</strong><span><img src="<%=path%>/images/bank.jpg" style="border:1px solid #cccccc;width:170px;height:45px;"/></span></li>
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
<li style="list-style:none;">1、额度实时到账。</li>
<li style="list-style:none;">&nbsp;</li>
<li style="list-style:none;">2、无需开通网银，可直接在线完成支付。</li>
<li style="list-style:none;">&nbsp;</li>
<li style="list-style:none;">3、交易额度最低10元，手续费为转款金额的千分之二。</li><!-- ，手续费为转款金额的0.2%。 -->
<li style="list-style:none;">&nbsp;</li>
<li style="list-style:none;">4、涵盖200多家银行及金融机构的借记卡、信用卡在线支付。</li>
<li style="list-style:none;">&nbsp;</li>
</ul>
</div>
</body>
</html>
