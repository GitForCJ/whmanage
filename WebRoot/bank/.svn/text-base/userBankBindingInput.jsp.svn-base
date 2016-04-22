<%@ page language="java" import="java.util.*" pageEncoding="gb2312"%>
<html> 
	<head>
		<title>银行卡绑定</title>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<script type="text/javascript" src="../js/util.js"></script>
<script type="text/javascript">
showMessage("${mess}");
function checkForm(){
	var MerID=document.getElementById("merId");
	var OrdId=document.getElementById("ordNo");
	var TransAmt=document.getElementById("transAmt");
	var TransDate=document.getElementById("transDate");
	var UserName=document.getElementById("userName");
	var CertId=document.getElementById("certId");
	var CardNo=document.getElementById("cardNo");
	if(UserName.value.length ==0){
			alert("持卡人姓名不能为空！");
			return false;
			UserName.onblur();
			}
	if(CertId.value.length==0){
			alert("证件号码不能为空！");
			return false;
			CertId.onblur();
			}
	if(CardNo.value.length==0){
			alert("卡号或折号不能为空！");
			return false;
			CardNo.onblur();
			}	
	
//	if(MerID.value.length !==15){
//			alert("商户号长度非法！");
//			return false;
//			MerID.onblur();
//			}		
	else{
	document.Transaction.submit();
	}
}
</script>
<style type="text/css">
.th20{text-align: right;}
.td20left input{font-size: 30px;height: 40px;width: 350px;color: blue;}
select{font-size:18px;height:40px;line-height:14px;}
</style>	
	</head>

	<body bgcolor="white">
	<form method="post" action="/business/bank.do?method=netpaySubmit" name="Transaction" onSubmit="return checkForm();"> 
		<center><strong><font size="4" color="blue">银行卡信息：</font></strong></center>
	<br>
	<table border="0" cellspacing="5" cellpadding="5" height="200" bordercolor="#2B91D5" align="center" width="700">                 
				  <tr>
                    <td class="th20"><strong>持卡人姓名：</strong></td>
                    <td class="td20left" onmouseout="this.style.backgroundColor=''" onmouseover="this.style.backgroundColor='#CCFFFF'"><input type="text" id="userName" name="UserName" value="" style="width=200px;"/>(必填）</td>
                  </tr>
                  <tr>
                    <td class="th20"><strong>证件类型：</strong></td>
                    <td class="td20left" width="500"><select size="1" name="CertType" class="a">
                    <option selected value="01">身份证</option>
                    <option value="02">军官证</option>
                    <option value="03">护照</option>
                    <option value="04">户口簿</option>
                    <option value="05">回乡证</option>
                    <option value="06">其他</option>
                    </select></td>
                    </tr>
                  <tr>
                    <td class="th20"><strong>证件号码：</strong></td>
                    <td class="td20left" onmouseout="this.style.backgroundColor=''" onmouseover="this.style.backgroundColor='#CCFFFF'"><input type="text" id="certId" name="CertId" value=""/>（必填）</td>
                  </tr>
                  <tr>
                    <td class="th20"><strong>开户银行：</strong></td>
                    <td width="500"><select size="1" name="OpenBankId" class="a">
                    <option selected value="0102">工商银行</option>
                    <option value="0103">农业银行</option>
                    <option value="0105">建设银行</option>
                    <option value="0308">招商银行</option>
                    <option value="0305">民生银行</option>
                    <option value="0302">中信银行</option>
                    <option value="0306">广东发展银行</option>
                    <option value="0309">兴业银行</option>
                    <option value="0303">中国光大银行</option>
                    <option value="0100">邮储</option>
                    </select></td>
                   </tr> 
                  <tr>
                    <td class="th20"><strong>卡折标志：</strong></td>
                    <td class="td20left1" onmouseout="this.style.backgroundColor=''" onmouseover="this.style.backgroundColor='#CCFFFF'">
                    <input type="radio" name="CardType" value="0" checked="checked">借记卡
				    <input type="radio" name="CardType" value="1">存折
<input type="radio" name="CardType" value="2">信用卡</td>
                  </tr>         
                  <tr>
                    <td class="th20"><strong>卡号/折号：</strong></td>
                    <td class="td20left" onmouseout="this.style.backgroundColor=''" onmouseover="this.style.backgroundColor='#CCFFFF'"><input type="text" id="cardNo" name="CardNo" value=""/>（必填）</td>
                </tr>                  
    </table>
  <br>
  	   <center>
	  	   <input type="submit" name="submitButton" value="提交" style="display:block; border:0px; width:85px; height:32px; color:#fff; font-weight:bold; background:url(../images/button_out.png) no-repeat bottom; cursor:pointer; float:left; margin-left:350px;"/>
	   </center>
</form>
	
	   
	
</body>
</html>