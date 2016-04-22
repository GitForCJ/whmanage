<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<html> 
	<head>
		<title>QueryInput</title>
<meta http-equiv="Content-Type" content="text/html; charset=GBK" />	
<script type="text/javascript">

function checkForm(){
	var MerID=document.getElementById("merId");
	var OrdId=document.getElementById("ordId");
	var TransDate=document.getElementById("transDate");
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

</script>	
		
	</head>
	<body>
	<br>
	<br>
	<form method="post" action="./QuerySend" name="QuerySend" onSubmit="return checkForm();"> 
	<center><strong><font size="3">单笔查询测试：</font></strong></center>
	<br>
	<table border="1" cellspacing="1" cellpadding="1" height="300" bordercolor="#2B91D5" align="center" width="700">                 
				  <!--tr>
                    <td class="th20" ><strong>商户号：</strong></td>
                    <td class="td20left" onmouseout="this.style.backgroundColor=''" onmouseover="this.style.backgroundColor='#CCFFFF'"><input type="text" id="merId" name="MerId" value="808080080988041"/>(15位数字，必填）</td>
                  </tr-->
                  <tr>
                    <td class="th20"><strong>交易订单号：</strong></td>
                    <td class="td20left" onmouseout="this.style.backgroundColor=''" onmouseover="this.style.backgroundColor='#CCFFFF'"><input type="text" id="ordId" name="OrdId" value="0020110802134723"/>(16位数字，原始交易订单号)</td>
                  </tr>
                  <tr>
                    <td class="th20"><strong>交易日期：</strong></td>
                    <td class="td20left" onmouseout="this.style.backgroundColor=''" onmouseover="this.style.backgroundColor='#CCFFFF'"><input type="text" id="transDate" name="TransDate" value="20110802"/>(8位数字，原始交易订单日期)</td>
                  </tr>         
                  <tr>
                    <td class="th20"><strong>交易类型：</strong></td>
                    <td class="td20left" onmouseout="this.style.backgroundColor=''" onmouseover="this.style.backgroundColor='#CCFFFF'"><input type="text" name="TransType" value="0003"/>(4位数字，代扣交易为0003)</td>
                </tr>
                <tr>
                    <td class="th20"><strong>单笔查询接口版本：</strong></td>
                    <td class="td20left" onmouseout="this.style.backgroundColor=''" onmouseover="this.style.backgroundColor='#CCFFFF'"><input type="text" name="Version" value="20100831"/>(8位数字，查询接口版本号) </td>
                  </tr>
                 <tr>
                    <td class="th20"><strong>商户保留域：</strong></td>
                    <td class="td20left" onmouseout="this.style.backgroundColor=''" onmouseover="this.style.backgroundColor='#CCFFFF'"><input type="text" name="Priv1" value="Memo"/>（可为空）</td>
                  </tr> 
                  
  </table>
  <br>
  	   <center>
	  	   <input type="submit" name="submitButton" value="提交" class="cmd"/>
		   <input type="reset" name="submitButton2" value="重置" class="cmd"/>
	   </center>
</form>
<p><font color="blue">
注： <br>&nbsp;&nbsp;&nbsp;&nbsp;
1.目前查询功能仅支持对代扣成功订单的查询，其他类型的查询暂不支持。<br>&nbsp;&nbsp;&nbsp;&nbsp;
2.使用查询功能前，需要提前将商户用于查询的服务器IP，在ChinaPay进行配置后才可使用。<br>&nbsp;&nbsp;&nbsp;&nbsp;
3.查询功能每个IP的默认查询间隔为30s。
</font></p>	
	   
	
</body>
</html>