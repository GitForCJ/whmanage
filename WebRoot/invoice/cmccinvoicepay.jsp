<%@ page contentType="text/html; charset=GBK" language="java"%>
<%@ page import="java.util.Vector" %>
<%@ page import="com.commsoft.epay.util.logging.Log"%>
<jsp:directive.page import="com.commsoft.epay.pc.util.Tools"/>
<jsp:directive.page import="com.commsoft.epay.pc.operation.form.PhonePayForm"/>
<jsp:directive.page import="java.util.List"/>
<jsp:directive.page import="java.util.Map"/>
<jsp:directive.page import="java.util.Iterator"/>
<jsp:useBean id="userSession" scope="session" class="com.commsoft.epay.pc.rights.form.UserForm" />
<jsp:useBean id="xmlconfig" scope="page" class="com.commsoft.epay.pc.invoice.Configuration" />
<jsp:useBean id="money" scope="page" class="com.commsoft.epay.pc.util.ConvertMoney" />
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
<link REL="stylesheet"  TYPE="text/css"  HREF="../css/common.css">
<script type="text/javascript" src="../js/util.js"></script>
<script language="JavaScript">
	showMessage("${mess}");
</script>
<script language="JavaScript">
function pcprint(){
	if(document.form1.printtype[0].checked)
	{
	  document.printInvoice.print();
	  document.URL = '../operation/ucphonebillinfo.jsp';
	  return ;
	}else if(document.form1.printtype[2].checked){
      document.printInvoice1.print();
	  document.URL ='../operation/ucphonebillinfo.jsp';
	  return ;
    }else {
	
	document.com.port=document.form1.printport.value;
	
	var message="******PC缴费账单******|";
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
        document.URL = '../operation/ucphonebillinfo.jsp';
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
  document.URL = '../operation/cmccphonebillinfo.jsp';
}
</script>

<%
String itemName = xmlconfig.getItemName();
String itemCoor = xmlconfig.getItemCoor();

PhonePayForm payForm = (PhonePayForm) request.getAttribute("phonePayForm");

String now=Tools.getNow4();
String now1 = Tools.getNow();
String time = now1.substring(10,19);
String year1 = now.substring(0,4); 
String year=now.substring(2,4);
String month = now.substring(4,6);
String date = now.substring(6,8);
String phone = payForm.getTradeObject();
String tradeNo = payForm.getSeqNo();
String payDate ="";
String userName = "空中充值用户";
String totalfee= payForm.getTotalFee();
String service = "移动缴费";
String monthTotal = "本月应收：";
String factMoney = "本月实收：";
String capital = "发票金额（大写）：人民币";
String prepay="其中预付金支付：";
String cardpayment="银行卡支付：";
String date1="";
String smaller="(小写)：";
String prepayValue="0.00";
String cardpaymentValue="0.00";
String smallerValue="";
String te_id=userSession.getResourceId();
String printtype = userSession.getPrintType();
String printport = userSession.getPrintPort();
String termname = userSession.getTermname();
String fee = payForm.getPayFee();//Tools.formatE(Math.abs(Float.parseFloat(request.getParameter("fee"))));
String total = payForm.getTotalFee();
String payFee=fee;
smallerValue=fee;
String capitalfee=money.toChinese(fee);
HttpSession st = request.getSession();

Vector detailName = new Vector();
Vector detailValue = new Vector();

Vector detailPrint = new Vector();
Vector detailPrint1 = new Vector();
List list = (List) payForm.getBillList();  
for (int i = 0; i < list.size(); i++) {
  Map map = (Map) list.get(i);
  for (Iterator it = map.keySet().iterator(); it.hasNext();) {
    String key = (String) it.next();
    String value = (String) map.get(key);  
    if(key.trim().equals("计费周期")){
       date1=key;
       payDate=(String)map.get(key);    
    }else{
    detailPrint.add(key);
    detailPrint.add(value);
    detailName.add(key);
    detailValue.add(value);
    detailPrint1.add(key+":");
    detailPrint1.add(value);
    }
  }
}

String [] detail = new String[detailPrint.size()];	
int detailNumber = detailName.size();

//热敏打印信息
String printmessage = "";
printmessage = "交易类型：空中充值" +"|";
printmessage = printmessage + "交易终端：" + te_id + "|";
printmessage = printmessage + "客户名称：" + userName + "|";
printmessage = printmessage + "客户号码：" + phone + "|";
printmessage = printmessage + "交易时间：" + now1 + "|";
printmessage = printmessage + "流水号：" + tradeNo + "|";
if(date1.trim().length()!=0){
printmessage = printmessage + "计费周期：" + payDate + "|";
}
//明细信息
for(int i=0;i<detailNumber;i++){
    printmessage = printmessage + (String) detailName.get(i)+"："
        + (String)detailValue.get(i) + "|";
}
printmessage = printmessage + "本月应收：" + total + " 元|";
printmessage = printmessage + "本次实收金额：" + fee + " 元|";
Log.info("printmessage:"+printmessage);
%>

<body onload="pcprint()">
<form name="form1" method="post">
<p align="center" class="STYLE2"><u>深圳万恒科技有限公司空中充值缴费专用发票</u></p>
<p align="center">&nbsp;&nbsp;&nbsp;<span class="STYLE2">&nbsp;发&nbsp;&nbsp;&nbsp;&nbsp;票&nbsp;&nbsp;&nbsp;&nbsp;联</span></p>
<table width="643" border="0" align="center">
  <tr>
    <td width="87">客户名称：</td>
    <td width="296">空中充值用户</td>
    <td width="68">流水号:</td>
    <td width="174"><%=tradeNo%></td>              
  </tr>
  <tr>
    <td>客户号码：</td>
    <td><%=phone%></td>
    <td>计费周期:</td>
    <td><%=payDate%></td>
  </tr>
  <tr>
    <td>扣款日期：</td>
    <td></td>
    <td>&nbsp;</td>
    <td><%=year1%>年 <%=month %>月<%=date %>日&nbsp;填开</td>
  </tr>
</table>
<br />

<table width="641" border="1" align="center">
  <tr>
    <td width="180" bordercolor="1">项目</td>
    <td width="50" bordercolor="1">金额</td>
    
    <td width="100" bordercolor="1">项目</td>
    <td width="97" bordercolor="1">金额</td>
    
    <td width="100" bordercolor="1">项目</td>
    <td width="165" bordercolor="1">金额</td>
    
    <td width="100" bordercolor="1">项目</td>
    <td width="165" bordercolor="1">金额</td>
  </tr>
  
   
<%
   		 int size = detailNumber;
   		 int col = size/10;
   		 int ro = size%10;
   		 int big10 = 0;
   		 if( col ==1 )
   		 {
   		 big10 = 10;
   		 }  
   		 int roFlag = 0;
   		 if(col>4)
   		 col = 4;
   		 
   if(  col <= 1  )
   {
   %>
   		  
		   <%
		   for(int h=0; h<big10; h++)
		   {
		   %>
		   <tr>
		    <td><%=detailName.get(h) %></td>
		    <td><%=detailValue.get(h) %></td>
		   
		   <% if( roFlag < ro )
   				{ 
   				roFlag++;
   			%>
   			
          <td><%=detailName.get(9+roFlag) %></td>
          <td><%=detailValue.get(9+roFlag) %></td>
   			<%} %>
		   <% }%>
		   </tr>
		   
		    <%
		   for(int h=0; h<ro&& col==0; h++)
		   {
		   %>
		   <tr>
		    <td><%=detailName.get(h) %></td>
		    <td><%=detailValue.get(h) %></td>  	
		   </tr>
		   
		   <% }%>
		   
		    <%
		   for(int j=0; j<10-ro && col==0; j++)
		   {
		   %>
		   <tr>
		    <td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
		    <td>&nbsp;&nbsp;&nbsp;&nbsp;</td>  	
		   </tr>
		   
		   <% }%>
   
   <% 
   }else
   {		 
   for(int i=0; i<=10; i++){
   %>
   <tr>
   	<%  
   	for(int m=0; m<col; m++)
   	{   	
   	%>
      <td><%=detailName.get(m*10+i) %></td>
      <td><%=detailValue.get(m*10+i) %></td>  	
   	<%} %>
   	<% if( roFlag < ro )
   	{ 
   	%>
    <td><%=detailName.get(col*10+(roFlag++)) %></td>
    <td><%=detailValue.get(col*10+(roFlag++)) %></td>  
   	<%} %>
   	
   </tr>
   <% } %>
   <% } %>
   
   
  <tr>
    <td colspan="8" bordercolor="1"><p><br/>
         &nbsp;&nbsp;&nbsp;&nbsp;缴费总额：<%=fee %>&nbsp;&nbsp;&nbsp; 大写：<%=capitalfee %>  <br/>
        </td>
  </tr>
  
  <tr>
    <td colspan="8" bordercolor="1"><p>备注：<br/>
         &nbsp;&nbsp;&nbsp;&nbsp;打印终端：<%=te_id %>&nbsp;&nbsp;&nbsp; 打印时间：<%=now1 %></p>
        </td>
  </tr>
</table>
<center>
<applet codebase="../" code="com.commsoft.epay.pc.invoice.PrintInvoice" archive="invoice.jar"
name="printInvoice" width="0" height="0" hspace="0" vspace="0" align="middle" id="printInvoice" >
    <param name="userName" value="<%=userName%>"></param>
	<param name="phone" value="<%=phone%>"></param>
	<param name="payFee" value="<%=payFee+"元"%>"></param>  
	<param name="payFeeBig" value="<%=capitalfee%>"></param>
	<param name="contractNo" value="<%=""%>"></param>
	<param name="tradeNo" value="<%=tradeNo%>"></param>
	<param name="payDate" value="<%=payDate%>"></param>
	<param name="terminal" value="<%=te_id%>"></param>
	<param name="itemName" value="<%=itemName%>"></param>
    <param name="itemCoor" value="<%=itemCoor%>"></param>
	<param name="year" value="<%=year%>"></param>
	<param name="month" value="<%=month%>"></param>
	<param name="date" value="<%=date%>"></param>
    <param name="monthTotal" value="<%=monthTotal%>"></param>
    <param name="factMoney" value="<%=factMoney%>"></param>
    <param name="capital" value="<%=capital%>"></param>
    <param name="total" value="<%=total+"元"%>"></param>
    <param name="deductDate" value="<%=year+"年"+month+"月"+date+"日"%>"></param>
	<param name="detailNumber" value="<%=detailNumber*2%>"></param>
	<param name="prepay" value="<%=prepay%>"></param>
	<param name="cardpayment" value="<%=cardpayment%>"></param>
	<param name="smaller" value="<%=smaller%>"></param>
	<param name="prepayValue" value="<%=prepayValue+"（元）"%>"></param>
	<param name="cardpaymentValue" value="<%=cardpaymentValue+"（元）"%>"></param>
	<param name="smallerValue" value="<%="￥"+smallerValue%>"></param>
	<param name="termname" value="<%=termname%>"></param>
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
<applet codebase="../" code="com.commsoft.epay.pc.invoice.PrintInvoice1" archive="invoice.jar"
name="printInvoice1" width="0" height="0" hspace="0" vspace="0" align="middle" id="printInvoice1" >
	<param name="detailNumber" value="<%=detailNumber*2%>"></param>
	<param name="tradetype" value="<%="空中充值"%>"></param>
	<param name="terminal" value="<%=te_id%>"></param>
	<param name="userName" value="<%=userName%>"></param>   
	<param name="phone" value="<%=phone%>"></param>
	<param name="tradetime" value="<%=now1%>"></param>  
	<param name="tradeNo" value="<%=tradeNo%>"></param>
	<%
 	if(date1.trim().length()!=0){%>
 	<param name="payDate" value="<%=payDate%>"></param>
 	<%
 	}
	for(int i=0;i<detailPrint1.size();i++){
	detail[i] = (String)detailPrint1.get(i);
	String j = String.valueOf(i);	

	%>
	<param name=<%=j %> value="<%=detail[i]%>"></param>
	<% 
	}
	%>
	<param name="total" value="<%=total+"元"%>"></param>
	<param name="fee" value="<%=fee+"元"%>"></param>
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
    <input type="radio" name="printtype" value="2" <%=printtype.equals("2") ? "checked" : ""%> />发票
    <br/>
    <input type="radio" name="printtype" value="1" <%=printtype.equals("1") ? "checked" : ""%> />收据(怡丰)
     <br/>
    <input type="radio" name="printtype" value="3" <%=printtype.equals("3") ? "checked" : ""%> />收据(研科数码)
</p>
<input type="button" name="Button2" value="返回" class="onbutton" onClick="goback()">
<input type="button" name="Submit2" value="打印" onClick = "pcprint()" class="onbutton">
</center>
</form>
</body>

</html>
