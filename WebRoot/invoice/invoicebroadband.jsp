<%@ page contentType="text/html; charset=GBK" language="java"%>
<%@ page import="java.util.*" %>
<%@ page import="java.io.*" %>
<%@ page import="com.commsoft.epay.util.logging.Log"%>
<jsp:directive.page import="com.commsoft.epay.pc.operation.form.BusiAgentForm"/>
<jsp:directive.page import="com.commsoft.epay.pc.util.Tools"/>
<jsp:useBean id="userSession" scope="session" class="com.commsoft.epay.pc.rights.form.UserForm" />
<jsp:useBean id="money" scope="page" class="com.commsoft.epay.pc.util.ConvertMoney" />
<jsp:useBean id="xmlconfig" scope="page" class="com.commsoft.epay.pc.invoice.Configuration" />
<jsp:useBean id="Formatter" scope="page" class="com.commsoft.epay.pc.util.format.Formatter" />
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
     document.URL = '../operation/broadbandtransact.jsp';
    return ;
  }else if(document.form1.printtype[2].checked){
     document.printInvoice1.print();
	 document.URL ='../operation/broadbandtransact.jsp';
	  return ;
  }
  else
  {
    document.com.port=document.form1.printport.value;
    var message="******PC���������Ϣ******|";
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
 			if (document.com.ErrMsg != '' && document.com.ErrMsg!='��ӡ�ɹ�')
	          {
		        alert(document.com.ErrMsg);
	          }
	          else
	          {
	          document.URL = '../operation/broadbandtransact.jsp';
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
  document.URL = '../operation/broadbandtransact.jsp';
} 

</script>

<%
String payTime = Tools.getNow();
BusiAgentForm busiagentform=(BusiAgentForm)request.getAttribute("busiAgentForm");
String typeCode = busiagentform.getTypeCode();
String typeName = busiagentform.getTypeName();
String tradeNo = busiagentform.getTradeNo();
String goodCode = busiagentform.getGoodsCode();
String goodsName = busiagentform.getGoodsName();
String goodsInfo = busiagentform.getGoodsInfo();
String remart=busiagentform.getRemark();
String linkphone = busiagentform.getLinkphone();
String idcard=busiagentform.getIdcard();
String charge =busiagentform.getCharge();
String capitalcharge=money.toChinese(charge);
String te_id=userSession.getResourceId();
String printport = userSession.getPrintPort();  
String printtype = userSession.getPrintType();

String service = "�������";
     
Vector detailPrint = new Vector ();
detailPrint.add("�������ͣ�");	 
detailPrint.add("�������");

detailPrint.add("������");
detailPrint.add(typeName); 

detailPrint.add("��������");
detailPrint.add(goodsName);

detailPrint.add("��Ʒ��Ϣ��");
detailPrint.add(goodsInfo);

detailPrint.add("����ʱ�䣺");
detailPrint.add(payTime);

detailPrint.add("��ˮ�ţ�");
detailPrint.add(tradeNo);

detailPrint.add("��ϵ�绰��");
detailPrint.add(linkphone);

detailPrint.add("�������֤��");
detailPrint.add(idcard);

detailPrint.add("��ע��");
detailPrint.add(remart);
int detailNumber = detailPrint.size();
String [] detail = new String[detailPrint.size()];	

//������ӡ��Ϣ
String printmessage = "";
printmessage = "�������ͣ��������" +"|";
printmessage = printmessage + "�����նˣ�" + te_id + "|";
printmessage = printmessage + "�ײ����" + typeName + "|";
printmessage = printmessage + "�ײ����ƣ�" + goodsName + "|";
printmessage = printmessage + "�ۿ��" + charge + " Ԫ|"; 
printmessage = printmessage + "����ʱ�䣺" + payTime + "|";
printmessage = printmessage + "��ˮ�ţ�" + tradeNo + "|"; 
printmessage = printmessage + "��Ʒ��Ϣ��" + goodsInfo + "|"; 
printmessage = printmessage + "��ϵ�绰��" + linkphone + "|";
printmessage = printmessage + "�������֤��" + idcard + "|";
printmessage = printmessage + "��ע��" + remart + "|"; 
Log.info("printmessage:"+printmessage);

%>
<body onload="pcprint()">
<form name="form1" method="post">
<p align="center" class="STYLE2"><u>�й����ţ����ţ����޹�˾�㶫ʡ�ֹ�˾ר�÷�Ʊ</u></p>
<p align="center">&nbsp;&nbsp;&nbsp;<span class="STYLE2">&nbsp;��&nbsp;&nbsp;&nbsp;&nbsp;Ʊ&nbsp;&nbsp;&nbsp;&nbsp;��</span></p>
<table width="810" border="1" align="center">
  <tr>
    <td width="82">�ͻ�����</td>
    <td width="441"><%=""%></td>
    <td width="80">�绰����</td>
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
    <td width="320" height="29" align="left">�����ն�</td>
    <td align="left"><%=te_id%></td>
  </tr>
  <tr>
    <td width="320" height="29" align="left">����ʱ��</td>
    <td align="left"><%=payTime%></td>
  </tr>
  <tr>
    <td width="320" height="29" align="left">������ˮ��</td>
    <td align="left"><%=tradeNo%></td>
  </tr>
  <tr>
    <td width="320" height="29" align="left">������</td>
    <td align="left"><%=typeName%></td>
  </tr>
  <tr>
    <td width="320" height="29" align="left">������</td>
    <td align="left"><%=goodsName%></td>
  </tr>
  <tr>
    <td width="320" height="29" align="left">��Ʒ��Ϣ</td>
    <td align="left"><%=goodsInfo%></td>
  </tr>
  <tr>
    <td width="320" height="29" align="left">��ϵ�绰</td>
    <td align="left"><%=linkphone%></td>
  </tr>
    <tr>
    <td width="320" height="29" align="left">�������֤</td>
    <td align="left"><%=idcard%></td>
  </tr>
  <tr>
    <td width="320" height="29" align="left">��ע</td>
    <td align="left"><%=remart%></td>
  </tr>
</table>
<table width="810" height="20" border="1" align="center">
  <tr>
    <td width="80">ʵ�ս��</td>
    <td>����д��<%=capitalcharge %></td>
    <td width="200">��Сд��<%=charge %></td>
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
<applet codebase="../" code="com.commsoft.epay.pc.invoice.PrintInvoice1" archive="invoice.jar"
name="printInvoice1" width="0" height="0" hspace="0" vspace="0" align="middle" id="printInvoice1" >
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
	codeBase="../thermalProj.cab#version=4��0��0��1" height="0" width="0"
	classid="clsid:15F3FD81-74F3-4E9B-9DDD-FB2A7CBC0976" name="com" VIEWASTEXT>
	<PARAM NAME="Visible" VALUE="0">
	<PARAM NAME="AutoScroll" VALUE="0">
	<PARAM NAME="AutoSize" VALUE="0">
	<PARAM NAME="AxBorderStyle" VALUE="1">
	<PARAM NAME="Caption" VALUE="д��">
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
    <input name="printtype" type="radio" value="2" <%=printtype.equals("2") ? "checked" : ""%> />��Ʊ
    <br/>
    <input type="radio" name="printtype" value="1" <%=printtype.equals("1") ? "checked" : ""%> />�վ�(����)
      <br/>
    <input type="radio" name="printtype" value="3" <%=printtype.equals("3") ? "checked" : ""%> />�վ�(�п�����)
    
</p>

<input type="button" name="Button2" value="����" class="onbutton" onClick="goback()">
<input type="button" name="Submit2" value="��ӡ" onClick = "pcprint()" class="onbutton">
</center>
</form>
</body>
</html>
