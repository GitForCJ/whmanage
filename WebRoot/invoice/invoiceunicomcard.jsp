<%@ page contentType="text/html; charset=GBK" language="java"%>
<%@ page import="java.util.*" %>
<%@ page import="java.io.*" %>
<%@ page import="com.commsoft.epay.util.logging.Log"%>
<jsp:directive.page import="com.commsoft.epay.pc.operation.form.SellCardForm"/>
<jsp:directive.page import="com.commsoft.epay.pc.db.DBConfig"/>
<jsp:directive.page import="com.commsoft.epay.pc.util.Tools"/>
<jsp:useBean id="userSession" scope="session" class="com.commsoft.epay.pc.rights.form.UserForm" />
<jsp:useBean id="money" scope="page" class="com.commsoft.epay.pc.util.ConvertMoney" />
<jsp:useBean id="xmlconfig" scope="page" class="com.commsoft.epay.pc.invoice.Configuration" />
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title></title>
</head>
<script language="vbs">
		function chinese(instr)
        Dim InputStr 
        InputStr = instr
        chinese = hex(Asc(InputStr))
        END function   
</script>
<script language="JavaScript">
  javascript:window.history.forward(1); 
</script> 
<script language="JavaScript">
function pcprint(){
  if(document.form1.printtype[0].checked)
  {
    document.printInvoice.print();
     document.URL = '../operation/sellunicomcard.jsp';
    return ;
  }
  else
  {
    document.com.port=document.form1.printport.value;
    var message="******PC售卡账单******|";
    message = message + document.form1.printmessage.value;
    var printmsg="";
		   var i=0;		      
		   for(i=0;i<message.length;i++)	
		   {	  
		     var num;
		     num = parseInt(message.charCodeAt(i));
		     if(num>255)
		        printmsg = printmsg+ chinese(message.charAt(i));
		     else
		      	if(num==124 || num==10)
		      	   printmsg = printmsg +"0A";
		      	else if (num>27)
		           printmsg = printmsg+ num.toString(16);	
			}
 			document.com.Print_Auto(printmsg);
 			if (document.com.ErrMsg != '' && document.com.ErrMsg!='打印成功')
	          {
		        alert(document.com.ErrMsg);
	          }
	          else
	          {
	          document.URL = '../operation/sellunicomcard.jsp';
	          }
    }
}

function onkeyboard(){
	var a = event.keyCode;
	if(a == 13){
		return pcprint();
	}
}
document.onkeydown = onkeyboard;
function goback()
{ 
  document.URL = '../operation/sellunicomcard.jsp';
} 

</script>

<%
String payState = DBConfig.getInstance().getPayState();
String payTime = Tools.getNow();
SellCardForm cardform=(SellCardForm)request.getAttribute("sellCardForm");
String cardNo = cardform.getCardNo() == null ? "" : cardform.getCardNo();
String password = cardform.getCardPwd()==null?"" :cardform.getCardPwd();
String tradeNo = cardform.getTradeNo();
String cardSerialno = cardform.getSerialNum() == null ? "" : cardform.getSerialNum();
String cardName = cardform.getCardTypeName();
String fee = cardform.getCardFee();
String capitalfee=money.toChinese(fee);
String serPhone = cardform.getFillPhone();
String presentMoney = cardform.getPresent();
String overTime = cardform.getEffectDate();
String cardDescription=cardform.getServerInfo();

String te_id=userSession.getTermId();
String printport = userSession.getPrintPort();  
String printtype = userSession.getPrintType();


	
String service = "售卡";
     
Vector detailPrint = new Vector ();
detailPrint.add("交易类型：");	 
detailPrint.add("电子售卡");

detailPrint.add("交易终端：");
detailPrint.add(te_id); 

detailPrint.add("卡类型：");
detailPrint.add(cardName); 


detailPrint.add("卡面值：");
detailPrint.add(fee+"元");

detailPrint.add("交易时间：");
detailPrint.add(payTime);

detailPrint.add("交易流水号：");
detailPrint.add(tradeNo);

detailPrint.add("卡序列号：");
detailPrint.add(cardSerialno);

detailPrint.add("卡号：");
detailPrint.add(cardNo);

detailPrint.add("密码：");
detailPrint.add(password);

detailPrint.add("赠送金额：");
detailPrint.add(presentMoney+"元");

detailPrint.add("有效时间：");
detailPrint.add(overTime);

if(serPhone!=null&&!serPhone.trim().equals("")){
detailPrint.add("充值电话：");
detailPrint.add(serPhone);
}
	if(cardDescription.length()!=0){
	String[] tt = cardDescription.split("\\|");
	String compart_sign1=":";
	Log.info("compart_sign1&"+compart_sign1);
	if(tt.length>1){
	for(int i=0;i<tt.length;i++){
   			 String [] str=tt[i].split("\\"+compart_sign1);
	       if(str.length>1){
	       	detailPrint.add(str[0]+":");
	   	    detailPrint.add(str[1]);
	   	    }else{
	   	    detailPrint.add(str[0]);
	   	    detailPrint.add(" ");
	   	    }
	}
	}else{
		detailPrint.add("业务说明：");
		detailPrint.add(cardDescription);
	}
   }
//detailPrint.add("");
//detailPrint.add(cardDescription);
int detailNumber = detailPrint.size();
String [] detail = new String[detailPrint.size()];	

//热敏打印信息
String printmessage = "";
printmessage = "交易类型：电子售卡" +"|";
printmessage = printmessage + "交易终端：" + te_id + "|";
printmessage = printmessage + "卡类型：" + cardName + "|";
printmessage = printmessage + "卡面值：" + fee + " 元|";
printmessage = printmessage + "交易时间：" + payTime + "|";
printmessage = printmessage + "流水号：" + tradeNo + "|"; 
printmessage = printmessage + "卡序列号：" + cardSerialno + "|"; 
printmessage = printmessage + "卡  号：" + cardNo + "|";
if(password.trim().length()!=0){
printmessage = printmessage + "密  码：" + password + "|"; 
}
printmessage = printmessage + "赠送金额：" + presentMoney + " 元|"; 
printmessage = printmessage + "有效时间：" + overTime + "|"; 
if(serPhone!=null&&!serPhone.trim().equals("")){
printmessage = printmessage + "充值电话：" + serPhone + "|"; 
}

 	if(cardDescription.length()!=0){
		String[] tt = cardDescription.split("\\|");
		if(tt.length>1){
		  printmessage = printmessage + "" + cardDescription + "|";
		}else{
			printmessage = printmessage + "业务说明：" + cardDescription + "|";
		}
	}
Log.info("printmessage:"+printmessage);

%>
<body onload="pcprint()">
<form name="form1" method="post">
<p align="center" class="STYLE2"><u></u></p>
<p align="center">&nbsp;&nbsp;&nbsp;<span class="STYLE2">&nbsp;发&nbsp;&nbsp;&nbsp;&nbsp;票&nbsp;&nbsp;&nbsp;&nbsp;联</span></p>
<table width="810" border="1" align="center">
  <tr>
    <td width="82">客户名称</td>
    <td width="441"><%=""%></td>
    <td width="80">电话号码</td>
    <td width="199"><%=""%></td>
  </tr>
</table>
<br />

<table width="810" border="0" align="center">
  <tr>
    <td width="180" bordercolor="0"></td>
    <td width="50" bordercolor="0"></td>
    
    <td width="100" bordercolor="0"></td>
    <td width="97" bordercolor="0"></td>
    
    <td width="100" bordercolor="0"></td>
    <td width="165" bordercolor="0"></td>
    
    <td width="100" bordercolor="0"></td>
    <td width="165" bordercolor="0"></td>
  </tr>
  <tr >
    <td width="320" height="29" align="left">售卡终端</td>
    <td align="left"><%=te_id%></td>
  </tr>
  <tr>
    <td width="320" height="29" align="left">售卡时间</td>
    <td align="left"><%=payTime%></td>
  </tr>
  <tr>
    <td width="320" height="29" align="left">卡类别</td>
    <td align="left"><%=cardName%></td>
  </tr>
  <tr>
    <td width="320" height="29" align="left">卡面值（元）</td>
    <td align="left"><%=fee%></td>
  </tr>
  <tr>
    <td width="320" height="29" align="left">卡号</td>
    <td align="left"><b><%=cardNo%></b></td>
  </tr>
  <tr>
    <td width="320" height="29" align="left">密码</td>
    <td align="left"><b><%=password%></b></td>
  </tr>
   <tr>
    <td width="320" height="29" align="left">卡序列号</td>
    <td align="left"><%=cardSerialno%></td>
  </tr>
  <tr>
    <td width="320" height="29" align="left">交易流水号</td>
    <td align="left"><%=tradeNo%></td>
  </tr>
    <tr>
    <td width="320" height="29" align="left">赠送金额（元）</td>
    <td align="left"><%=presentMoney%></td>
  </tr>
   <tr>
    <td width="320" height="29" align="left">有效期</td>
    <td align="left"><%=overTime%></td>
  </tr>
  <tr>
    <td width="320" height="29" align="left">充值电话</td>
    <td align="left"><%=serPhone%></td>
  </tr>

</table>
<table width="810" height="20" border="1" align="center">
  <tr>
    <td width="80">实收金额</td>
    <td>（大写）<%=capitalfee %></td>
    <td width="200">（小写）<%=fee %></td>
  </tr>
</table>

<center>
<applet codebase="../" code="com.commsoft.epay.pc.invoice.PrintInvoice" archive="invoice.jar"
name="printInvoice" width="0" height="0" hspace="0" vspace="0" align="middle" id="printInvoice" >
<param name="detailNumber" value="<%=detailNumber%>"></param>
<%
	 for(int i=0;i<detailPrint.size();i++){
	 detail[i] = (String)detailPrint.get(i);
	 String j = String.valueOf(i);
%>
	 <param name=<%=j %> value="<%=detail[i]%>"></param>
<%
	 }
%>
</applet>

<OBJECT id="com" style="Z-INDEX: 109; LEFT: 80px; WIDTH: 8px; POSITION: absolute; TOP: 416px; HEIGHT: 8px"
	codeBase="../thermalProj.cab#version=4，0，0，1" height="0" width="0"
	classid="clsid:15F3FD81-74F3-4E9B-9DDD-FB2A7CBC0976" name="com" VIEWASTEXT>
	<PARAM NAME="Visible" VALUE="0">
	<PARAM NAME="AutoScroll" VALUE="0">
	<PARAM NAME="AutoSize" VALUE="0">
	<PARAM NAME="AxBorderStyle" VALUE="1">
	<PARAM NAME="Caption" VALUE="写卡">
	<PARAM NAME="Color" VALUE="2147483663">
	<PARAM NAME="Font" VALUE="MS Sans Serif">
	<PARAM NAME="KeyPreview" VALUE="0">
	<PARAM NAME="PixelsPerInch" VALUE="96">
	<PARAM NAME="PrintScale" VALUE="1">
	<PARAM NAME="Scaled" VALUE="-1">
	<PARAM NAME="DropTarget" VALUE="0">
	<PARAM NAME="HelpFile" VALUE="">
	<PARAM NAME="DoubleBuffered" VALUE="0">
	<PARAM NAME="Enabled" VALUE="-1">
	<PARAM NAME="Cursor" VALUE="0">
	<PARAM NAME="Port" VALUE="1">
	<PARAM NAME="ErrMsg" VALUE="">
	<PARAM NAME="Logo" VALUE="">
	<PARAM NAME="BaudRate" VALUE="0">
	<PARAM NAME="PrintFont" VALUE="">
	<PARAM NAME="CheckBlackSign" VALUE="0">
	<PARAM NAME="LogoPositing" VALUE="0">
</OBJECT>

<input name="printmessage" type="hidden" value="<%=printmessage %>" />
<input name="printport" type="hidden" value="<%=printport %>" />


<p align="center">
    <input name="printtype" type="radio" value="2" <%=printtype.equals("2") ? "checked" : ""%> />发票
    <br/>
    <input type="radio" name="printtype" value="1" <%=printtype.equals("1") ? "checked" : ""%> />收据
</p>

<input type="button" name="Button2" value="返回" class="onbutton" onClick="goback()">
<input type="button" name="Submit2" value="打印" onClick = "pcprint()" class="onbutton">
</center>
</form>
</body>
</html>
