<%@ page language="java" import="java.util.*" pageEncoding="gb2312"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
		<title>无标题文档</title>
<SCRIPT language="JavaScript">
	var timerIdle = 0; //空闲时间
	var timerBusy = 0; //倒计时开始
	var timerIdle1 = 2; //系统参数定义超时时间
	var timerBusy1 = 14398; //退出时间
	function timerTimeout()
	{
		timerIdle++;
		if (timerIdle > timerIdle1)
		{
			if (timerBusy == 0)
			{
				timerBusy = timerBusy1 + 1;
				//view timerUI
				document.getElementById("timerUI").style.display = "inline";
			}
			timerBusy--;
			document.getElementById("_timerBusy").innerHTML ="系统将在"+ formatSeconds(timerBusy)+"后退出";
			if (timerBusy <= 0)
			{
				timerExit();
				return;
			}
		}
		else
		{
			timerBusy = 0;
		}
		window.setTimeout("timerTimeout()", 1000);

	}

	function timerUser()

	{

		//让div消失

		timerIdle = 0;

		document.getElementById("timerUI").style.display = "none";

	}

	function timerExit()

	{
		document.getElementById("_timerBusy").innerHTML = "请重新登录";

	}

	window.setTimeout("timerTimeout()", 1000);

	function mouseMove(ev)

	{

		ev = ev || window.event;

		timerUser();

		var mousePos = mouseCoords(ev);

	}

	function mouseCoords(ev)

	{

		if (ev.pageX || ev.pageY) {

			return {
				x :ev.pageX,
				y :ev.pageY
			};

		}

		return {

			x :ev.clientX + document.body.scrollLeft - document.body.clientLeft,

			y :ev.clientY + document.body.scrollTop - document.body.clientTop

		};

	}
	
	
///
function formatSeconds(value) {
   var theTime = parseInt(value);// 秒
   var theTime1 = 0;// 分
   var theTime2 = 0;// 小时
  // alert(theTime);
   if(theTime > 60) {
      theTime1 = parseInt(theTime/60);
      theTime = parseInt(theTime%60);
      // alert(theTime1+"-"+theTime);
      if(theTime1 > 60) {
         theTime2 = parseInt(theTime1/60);
         theTime1 = parseInt(theTime1%60);
       }
   }
       var result = ""+parseInt(theTime)+"秒";
       if(theTime1 > 0) {
       result = ""+parseInt(theTime1)+"分"+result;
       }
       if(theTime2 > 0) {
       result = ""+parseInt(theTime2)+"时"+result;
       }
       return result;
   }
///

	document.onmousemove = mouseMove;

	document.onkeydown = mouseMove;
</SCRIPT>

	</head>



	<body>



		<DIV ID="timerUI"
			style="position: absolute; left: 30px; top: 30px; font-size: 20px;">

			<table width="300" border="0" cellspacing="0" cellpadding="0">

				<tr>
					<td nowrap align="right" ID="_timerBusy"
						style="font-size: 15px; font-weight: bold; color: #FF0000;"></td>
				</tr>
			</table>
		</DIV>
	</body>
</html>