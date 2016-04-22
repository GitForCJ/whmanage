<%@ page language="java" pageEncoding="GBK"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK" />
<title>显示当前日期时间[不断变化]</title>
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
var dayname = new Array ("星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六");
var monthname =
new Array ("1月","2月","3月","4月","5月","6月","7月","8月","9月","10月","11月","12月" );
document.write(year +"年");
document.write(monthname[month]);
document.write(date + "日"+" ");

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
//timeValue = (hours >= 12) ? " 下午 " : " 上午 "; 
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
