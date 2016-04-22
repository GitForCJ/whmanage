
var times;
function showMessageInfo()
{
	document.getElementById("messageAlert").style.position="absolute";
	document.getElementById("messageAlert").style.right="10px";
	document.getElementById("messageAlert").style.top=document.documentElement.clientHeight+(document.body.scrollTop || document.documentElement.scrollTop)+"px";
	document.getElementById("messageAlert").style.display="";
	clearTimeout(times);
	funFlushShowMessage(140);
}
function funFlushShowMessage(con)
{
	if(con<=0)
	{
		clearTimeout(times);
		return ;
	}
	else
	{
		con--;
		var height=parseInt(document.getElementById("messageAlert").style.top.replace("px",""))-1;
		document.getElementById("messageAlert").style.top=height+"px";
		times=setTimeout("funFlushShowMessage("+con+")",3);
		return ;
	}
}
var topValue=0;
var interval=null;
window.onscroll=function()
{
	if(document.getElementById("messageAlert").style.display=="")
	{
		//document.getElementById("messageAlert").style.top=document.documentElement.clientHeight+(document.body.scrollTop || document.documentElement.scrollTop)-140+"px";
		if(interval==null)
		{
			interval=setInterval("huadong()",500);//这里就是判定时间，当前是1秒一判定
		}
		topValue=(document.documentElement.scrollTop || document.body.scrollTop);
	}
}
var setTime;
function huadong(){
	// 判断此刻到顶部的距离是否和1秒前的距离相等
	if((document.documentElement.scrollTop || document.body.scrollTop)==topValue)
	{
		clearInterval(interval);
		interval=null;
		clearTimeout(setTime);
		xy(parseInt(document.getElementById("messageAlert").style.top.replace("px","")),document.documentElement.clientHeight+(document.body.scrollTop || document.documentElement.scrollTop)-140,3);
	}
}
function xy(one,two,sd)
{
	if(one==two)
	{
		clearTimeout(setTime);
		return;
	}
	if(one>two)
	{
		one=--one;
		document.getElementById("messageAlert").style.top=one+"px";
	}
	if(one<two)
	{
		one=++one;
		document.getElementById("messageAlert").style.top=one+"px";
	}
	setTime=setTimeout("xy("+one+","+two+","+sd+")",sd);
	return;
}
function funRightMessageAlertNone(){
clearTimeout(times);
clearInterval(interval);
clearTimeout(setTime);
	document.getElementById("messageAlert").style.display="none";
	return ;
}