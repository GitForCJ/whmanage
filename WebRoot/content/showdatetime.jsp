<%@ page language="java" pageEncoding="GBK"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK" />
<title>��ʾ��ǰ����ʱ��[���ϱ仯]</title>
</head>
<body>
<script language="JavaScript" type="text/javascript">
<!--
calendar = new Date();
day = calendar.getDay();
month = calendar.getMonth();
date = calendar.getDate();
year = calendar.getYear();
if (year< 100) year = 1900 + year;
cent = parseInt(year/100);
g = year % 19;
k = parseInt((cent - 17)/25);
i = (cent - parseInt(cent/4) - parseInt((cent - k)/3) + 19*g + 15) % 30;
i = i - parseInt(i/28)*(1 - parseInt(i/28)*parseInt(29/(i+1))*parseInt((21-g)/11));
j = (year + parseInt(year/4) + i + 2 - cent + parseInt(cent/4)) % 7;
l = i - j;
emonth = 3 + parseInt((l + 40)/44); 
edate = l + 28 - 31*parseInt((emonth/4));
emonth--;
var dayname = new Array ("������", "����һ", "���ڶ�", "������", "������", "������", "������");
var monthname =
new Array ("1��","2��","3��","4��","5��","6��","7��","8��","9��","10��","11��","12��" );
document.write(year +"��");
document.write(monthname[month]);
document.write(date + "��"+" ");

//-->
</script>
<script language="JavaScript" type="text/javascript"> 
<!--
document.write("<span id='clock'></span>"); 
var now,hours,minutes,seconds,timeValue; 
function showtime(){
now = new Date(); 
hours = now.getHours(); 
minutes = now.getMinutes(); 
seconds = now.getSeconds(); 
//timeValue = (hours >= 12) ? " ���� " : " ���� "; 
timeValue = ((hours > 12) ? hours : hours) + ":"; 
timeValue += ((minutes <10)?"0":"") + minutes+":"; 
timeValue += ((seconds <10)?"0":"") + seconds+" "; 
clock.innerHTML = timeValue; 
setTimeout("showtime()",1000); 
} 
showtime(); 
document.write(dayname[day]);
//--> 
</script>
</body>
</html>
