<%@ page language="java" import="java.util.*" pageEncoding="gb2312"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String str=request.getParameter("str")+"";
if(str!=null && !"".equals(str))
	str=new String(str.getBytes("iso-8859-1"),"gb2312");
String[] strs=new String[str.split(";").length];
strs[0]=str.split(";")[0];
strs[1]=str.split(";")[1];
strs[2]=str.split(";")[2];
strs[3]=str.split(";")[3];
strs[4]=str.split(";")[4];
strs[5]=str.split(";")[5];

String gourl=request.getParameter("gourl");
gourl=path+gourl;
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title></title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<script  type="text/javascript" src="<%=path %>/js/util.js"></script>
<script type="text/javascript" language="javascript"> 
history.go(1);

function  printPage(){ 

  var titleHTML=document.getElementById("doctitle").innerHTML; 
  var bodyinnerHTML=document.body.innerHTML;
document.body.innerHTML=titleHTML;
  window.print();
  document.body.innerHTML=bodyinnerHTML;
}

function ChinaCost(numberValue){
var numberValue=new String(Math.round(numberValue*100)); // 数字金额
var chineseValue=""; // 转换后的汉字金额
var String1 = "零壹贰叁肆伍陆柒捌玖"; // 汉字数字
var String2 = "万仟佰拾亿仟佰拾万仟佰拾元角分"; // 对应单位
var len=numberValue.length; // numberValue 的字符串长度
var Ch1; // 数字的汉语读法
var Ch2; // 数字位的汉字读法
var nZero=0; // 用来计算连续的零值的个数
var String3; // 指定位置的数值
if(len>15){
alert("超出计算范围");
return "";
}
if (numberValue==0){
chineseValue = "零元整";
return chineseValue;
}

String2 = String2.substr(String2.length-len, len); // 取出对应位数的STRING2的值
for(var i=0; i<len; i++){
String3 = parseInt(numberValue.substr(i, 1),10); // 取出需转换的某一位的值
if ( i != (len - 3) && i != (len - 7) && i != (len - 11) && i !=(len - 15) ){
if ( String3 == 0 ){
Ch1 = "";
Ch2 = "";
nZero = nZero + 1;
}
else if ( String3 != 0 && nZero != 0 ){
Ch1 = "零" + String1.substr(String3, 1);
Ch2 = String2.substr(i, 1);
nZero = 0;
}
else{
Ch1 = String1.substr(String3, 1);
Ch2 = String2.substr(i, 1);
nZero = 0;
}
}
else{ // 该位是万亿，亿，万，元位等关键位
if( String3 != 0 && nZero != 0 ){
Ch1 = "零" + String1.substr(String3, 1);
Ch2 = String2.substr(i, 1);
nZero = 0;
}
else if ( String3 != 0 && nZero == 0 ){
Ch1 = String1.substr(String3, 1);
Ch2 = String2.substr(i, 1);
nZero = 0;
}
else if( String3 == 0 && nZero >= 3 ){
Ch1 = "";
Ch2 = "";
nZero = nZero + 1;
}
else{
Ch1 = "";
Ch2 = String2.substr(i, 1);
nZero = nZero + 1;
}
if( i == (len - 11) || i == (len - 3)){ // 如果该位是亿位或元位，则必须写上
Ch2 = String2.substr(i, 1);
}
}
chineseValue = chineseValue + Ch1 + Ch2;
}

if ( String3 == 0 ){ // 最后一位（分）为0时，加上“整”
chineseValue = chineseValue + "整";
}

return chineseValue;
}
//订单号  交易时间  代办户账户  商品名称  充值号码  付款金额   
</script>
  </head>
  
  <body>
    <div id="doctitle" style="margin:0px 0px 0px 0px;padding:0px 0px 0px 0px;">
	<table cellspacing=10>
		<tr>
			<td style="font-size:15px;font-weight:bold;text-align:center;">客户缴费回执单</td>
		</tr>
<!-- 
	<tr>
		<td style="font-size:13px; ">深圳市万恒科技有限公司</td>
	</tr>
	<tr>
		<td style="font-size:13px; ">全国客服: 400-9923-988</td>
	</tr>
 -->
		<tr>
			<td style="font-size:13px; ">交易订单: <%=strs[0] %></td>
		</tr>
		<tr>
			<td style="font-size:13px; ">交易时间: <%=strs[1] %></td>
		</tr>
		<tr>
			<td style="font-size:13px; ">商品名称: <%=strs[3] %></td>
		</tr>
		<tr>
			<td style="font-size:13px; ">客户名称: ***</td>
		</tr>
		<tr>
			<td style="font-size:13px; ">充值号码: <%=strs[4] %></td>
		</tr>
		<tr>
			<td style="font-size:13px; ">充值金额: <%=strs[5] %>元(<label id="lab"></label>)</td>
		</tr>
		
	</table>
</div>
<input onclick="funDY();" type="button" style="width:85px; height:32px; border:0px;color:#fff; font-weight:bold;background:url(<%=path%>/images/button_out.png) no-repeat bottom; cursor:pointer;" value="打印票据"/>
&nbsp;
<input onclick="javascript:window.location.href='<%=gourl%>'" type="button" style="width:85px; height:32px; border:0px;color:#fff; font-weight:bold;background:url(<%=path%>/images/button_out.png) no-repeat bottom; cursor:pointer;" value="返回充值"/>
 </body>
  <script type="text/javascript">
  	window.onload=function(){
			document.getElementById("lab").innerHTML=ChinaCost("<%=strs[5] %>");
			//printPage();
			//window.location.href="<%=path %>/business/cmccbusiness.jsp";
			return;
		}
	function funDY()
	{
		printPage();
		return;
	}
  </script>
</html>
