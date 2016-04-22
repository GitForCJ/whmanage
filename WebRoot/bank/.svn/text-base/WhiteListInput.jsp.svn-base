<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%@ page import="java.util.*"%>
<jsp:useBean id="errors" scope="request" class="java.util.ArrayList" />
<html> 
	<head>
		<title>WhiteListInput</title>
<meta http-equiv="Content-Type" content="text/html; charset=GBK" />	
<script type="text/javascript">

function checkForm(){
	var MerID=document.getElementById("merId");
	var UserName=document.getElementById("userName");
	var CertId=document.getElementById("certId");
	var CardNo=document.getElementById("cardNo");
	var Mobile=document.getElementById("mobile");
	if(Mobile.value.length ==0){
			alert("手机号不能为空！");
			return false;
			Mobile.onblur();
			}
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
	else{
	document.loginForm.submit();
	}
}

</script>	
		
	</head>

	<body>
	<br>
	<br>
	<form method="post" action="./WhiteList" name="WhiteList" onSubmit="return checkForm();"> 
	<center><strong><font size="3">白名单信息：</font></strong></center>
	<br>
	<table border="1" cellspacing="1" cellpadding="1" height="300" bordercolor="#2B91D5" align="center" width="700">                 
				  <!--tr>
                    <td class="th20" ><strong>商户号：</strong></td>
                    <td class="td20left" onmouseout="this.style.backgroundColor=''" onmouseover="this.style.backgroundColor='#CCFFFF'"><input type="text" id="merId" name="MerId" value="808080080988041"/>(15位数字，必填）</td>
                  </tr-->
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
                    <td class="td20left" onmouseout="this.style.backgroundColor=''" onmouseover="this.style.backgroundColor='#CCFFFF'"><input type="text" id="certId" name="CertId" value="120221198606121111"/>（必填）</td>
                  </tr>                 
				  <tr>
                    <td class="th20" ><strong>持卡人姓名：</strong></td>
                    <td class="td20left" onmouseout="this.style.backgroundColor=''" onmouseover="this.style.backgroundColor='#CCFFFF'"><input type="text" id="userName" name="UserName" value="用户名"/>(必填）</td>
                  </tr>
                    <tr>
                    <td class="th20"><strong>卡号/折号：</strong></td>
                    <td class="td20left" onmouseout="this.style.backgroundColor=''" onmouseover="this.style.backgroundColor='#CCFFFF'"><input type="text" id="cardNo" name="CardNo" value="900758403049921833"/>（必填）</td>
                </tr> 
                <tr>
                    <td class="th20"><strong>手机号码：</strong></td>
                    <td class="td20left" onmouseout="this.style.backgroundColor=''" onmouseover="this.style.backgroundColor='#CCFFFF'"><input type="text" id="mobile" name="Mobile" value="13916666665"/>（必填）</td>
                </tr> 
                <tr>
                    <td class="th20"><strong>住址：</strong></td>
                    <td class="td20left" onmouseout="this.style.backgroundColor=''" onmouseover="this.style.backgroundColor='#CCFFFF'"><input type="text" name="Address" value="持卡人地址"/>（选填）</td>
                </tr> 
                <tr>
                    <td class="th20"><strong>邮箱：</strong></td>
                    <td class="td20left" onmouseout="this.style.backgroundColor=''" onmouseover="this.style.backgroundColor='#CCFFFF'"><input type="text" name="Email" value="ABC@ChinaPay.com"/>（选填）</td>
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