<%@ page contentType="text/html; charset=gb2312" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>电信账单支付</title>
<link href="../css/chongzhi_mobile.css" rel="stylesheet" type="text/css" />
<script src="../js/jquery-1.8.2.min.js" type="text/javascript"></script> 
<script  type="text/javascript" src="../js/util.js"></script>
<script language='javascript' src='../js/jquery.js'></script>
<link href="../css/main_style.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
showMessage("${mess}");

	function check()
	{
		var fee=document.getElementById("moneys").value.Trim();
		if(fee=="")
		{
			alert("请输入账单金额!");
			return ;
		}
		document.getElementsByName("directFrm")[0].submit();
		return;
	}

// 去空格
String.prototype.Trim = function() 
{ 
	return this.replace(/(^\s*)|(\s*$)/g, ""); 
}

function  result()
{
	document.getElementById("moneys").value="";
}
</script>
</head>
<body>
<div id="tabbox">
  <div id="tabs_title">
    <ul class="tabs_wzdh">
      <li>电信账单支付</li></ul>
  </div>
</div>
    <div class="mobile-charge" >
<form action="<%=path%>/business/bank.do?method=Jqhaccountpay" method="post" name="directFrm" >
        <fieldset>
               <div class="pass_list" style="margin-left: 150;">
   	  <ul>
            <li style="font-size: 12px;">
            <strong style="margin-top:10px;">支付类型：</strong><span><img src="<%=path%>/images/accountpay.jpg" style="border:1px solid #cccccc;width:170px;height:45px;"/></span></li>
            <li style="font-size: 12px;">
            	<strong >账单金额：</strong>
            	<span>
            		<input style="font-size: 20px;height: 30px;width:80px;color: blue;" id="moneys" name="fee" type="text" onkeyup="value=value.replace(/[^\d]/g,'')"/> 元
				</span>
			</li>
        </ul>
    </div>
</fieldset>
<div style="margin-left: 20px;" />
<input type="button" value="确定" name="OK" class="field-item_button" onclick="check()" />
<input type="button" value="重写"  style="margin-left: 10px;" class="field-item_button" onclick="result()"/>
</div>
      </form>
    </div>
 <div style="font-size: 13px; color:#333333; red;margin-left: 150px;clear:both;">

</div>
<div style="padding-left:40px;padding-top:10px;margin-left: 20px;font-size: 13px; color: #FF6600;font-weight:bold;border:1px solid #FF9E4d;background-color: #FFFFE5;">
<ul>
<li style="list-style:none;">1、单笔消费最高不能超过50元，最低2元起。</li>
<li style="list-style:none;">&nbsp;</li>
<li style="list-style:none;">2、当天单号码消费不能超过50元</li>
<li style="list-style:none;">&nbsp;</li>
<li style="list-style:none;">3、当月单号码不能超过100元</li>
<li style="list-style:none;">&nbsp;</li>
</ul>
</div>
</body>
</html>
