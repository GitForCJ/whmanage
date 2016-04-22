<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<html> 
	<head>
		<title>TransactionInput.jsp</title>
<meta http-equiv="Content-Type" content="text/html; charset=GBK" />
<script type="text/javascript"><!--

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
	if(OrdId.value.length!==0){
			if(OrdId.value.length!==16){
			alert("订单号长度非法！");
			return false;
			OrdId.onblur();
			}
			}
	if(TransAmt.value.length!==12){
			alert("交易金额长度非法！");
			return false;
			TransAmt.onblur();
			}
	if(TransDate.value.length!==0){
	        if(TransDate.value.length!==8){
			alert("日期长度非法！");
			return false;
			TransDate.onblur();
			}
			}		
	else{
	document.loginForm.submit();
	}
}

--></script>	
		
	</head>

	<body>
	<form method="post" action="./Transaction" name="Transaction" onSubmit="return checkForm();"> 
		<center><strong><font size="3" color="blue">交易银行卡信息：</font></strong></center>
	<br>
	<table border="1" cellspacing="1" cellpadding="1" height="200" bordercolor="#2B91D5" align="center" width="700">                 
				  <tr>
                    <td class="th20" ><strong>持卡人姓名：</strong></td>
                    <td class="td20left" onmouseout="this.style.backgroundColor=''" onmouseover="this.style.backgroundColor='#CCFFFF'"><input type="text" id="userName" name="UserName" value="测试"/>(必填）</td>
                  </tr>
                  <tr>
                    <td class="th20"><strong>证件类型：</strong></td>
                    <td width="500"><select size="1" name="CertType">
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
                    <td class="td20left" onmouseout="this.style.backgroundColor=''" onmouseover="this.style.backgroundColor='#CCFFFF'"><input type="text" id="certId" name="CertId" value="120221198606121502"/>（必填）</td>
                  </tr>
                  <tr>
                    <td class="th20"><strong>开户银行：</strong></td>
                    <td width="500"><select size="1" name="OpenBankId">
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
                    <td class="td20left" onmouseout="this.style.backgroundColor=''" onmouseover="this.style.backgroundColor='#CCFFFF'">
                    <input type="radio" name="CardType" value="0" checked="checked">卡
				    <input type="radio" name="CardType" value="1">折</td>
                  </tr>         
                  <tr>
                    <td class="th20"><strong>卡号/折号：</strong></td>
                    <td class="td20left" onmouseout="this.style.backgroundColor=''" onmouseover="this.style.backgroundColor='#CCFFFF'"><input type="text" id="cardNo" name="CardNo" value="900758403049921840"/>（必填）</td>
                </tr>                  
    </table>
    <br>
    <br>
	<center><strong><font size="3" color="blue">交易信息：</font></strong></center>
	<br>
	<table border="1" cellspacing="1" cellpadding="1" height="300" bordercolor="#2B91D5" align="center" width="700">                 
				  <!--tr>
                    <td class="th20" ><strong>商户号：</strong></td>
                    <td class="td20left" onmouseout="this.style.backgroundColor=''" onmouseover="this.style.backgroundColor='#CCFFFF'"><input type="text" id="merId" name="MerId"/>(15位数字）</td>
                  </tr-->
                  <tr>
                    <td class="th20"><strong>交易订单号：</strong></td>
                    <td class="td20left" onmouseout="this.style.backgroundColor=''" onmouseover="this.style.backgroundColor='#CCFFFF'"><input type="text" id="ordNo" name="OrdNo"/>(16位数字，不填由系统自动产生)</td>
                  </tr>
                  <tr>
                    <td class="th20"><strong>交易金额（分）：</strong></td>
                    <td class="td20left" onmouseout="this.style.backgroundColor=''" onmouseover="this.style.backgroundColor='#CCFFFF'"><input type="text" id="transAmt" name="TransAmt" value="000000000001"/>(12位数字,不填默认金额为1分) </td>
                  </tr>
                  <tr>
                    <td class="th20"><strong>币种：</strong></td>
                    <td class="td20left" onmouseout="this.style.backgroundColor=''" onmouseover="this.style.backgroundColor='#CCFFFF'"><input type="text" name="CuryId" value="156" />(3位数字，固定为人民币156）</td>
                  </tr> 
                  <tr>
                    <td class="th20"><strong>交易日期：</strong></td>
                    <td class="td20left" onmouseout="this.style.backgroundColor=''" onmouseover="this.style.backgroundColor='#CCFFFF'"><input type="text" id="transDate" name="TransDate"/>(8位数字，不填系统默认为当前日期)</td>
                  </tr>         
                  <tr>
                    <td class="th20"><strong>交易类型：</strong></td>
                    <td class="td20left" onmouseout="this.style.backgroundColor=''" onmouseover="this.style.backgroundColor='#CCFFFF'"><input type="text" name="TransType" value="0003"/>(4位数字，代扣交易为0003)</td>
                </tr>
                <tr>
                    <td class="th20"><strong>接口版本：</strong></td>
                    <td class="td20left" onmouseout="this.style.backgroundColor=''" onmouseover="this.style.backgroundColor='#CCFFFF'"><input type="text" name="Version" value="20100831"/>(8位数字，代扣版本号为20100831)</td>
                </tr>
                  <tr>
                    <td class="th20"><strong>网关：</strong></td>
                    <td class="td20left" onmouseout="this.style.backgroundColor=''" onmouseover="this.style.backgroundColor='#CCFFFF'"><input type="text" name="GateId" value="7008"/>(4位数字，Chinapay网关号为7008)</td>
                </tr>
                    <tr>
                    <td class="th20"><strong>商户私有域：</strong></td>
                    <td class="td20left" onmouseout="this.style.backgroundColor=''" onmouseover="this.style.backgroundColor='#CCFFFF'"><input type="text" name="Priv1" value="Memo"/>非必须（可为空）</td>
                  </tr>
                  <tr>
                    <td class="th20"><strong>用途：</strong></td>
                    <td class="td20left" onmouseout="this.style.backgroundColor=''" onmouseover="this.style.backgroundColor='#CCFFFF'"><input type="text" name="Purpose" value="Memo"/>非必须（可为空）</td>
                  </tr> 
                  
  </table>
  <br>
  	   <center>
	  	   <input type="submit" name="submitButton" value="提交" class="cmd"/>
		   <input type="reset" name="submitButton2" value="重置" class="cmd"/>
	   </center>
</form>
	
	   
	
</body>
</html>