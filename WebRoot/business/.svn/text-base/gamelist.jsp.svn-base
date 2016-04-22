<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@ page import="java.util.*,com.wlt.webm.gm.form.GameInterface"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%
String path = request.getContextPath();
List arryList=(List)request.getAttribute("list");
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>游戏充值列表</title>
<script src="../js/jquery-1.8.2.min.js" type="text/javascript"></script> 
<script  type="text/javascript" src="../js/util.js"></script>
<script language='javascript' src='../js/jquery.js'></script>
<style type="text/css">
	a { text-decoration:none;}
	a:hover{text-decoration:underline;} 
	.field-item_button_m { margin:20px 0 20px 140px; float:left;}
	.field-item_button { display:block; border:0px; width:85px; height:32px; color:#fff; font-weight:bold; background:url(../images/button_out.png) no-repeat bottom; cursor:pointer; float:left; margin-right:10px;}0
.field-item_button:hover { display:block; border:0px; width:85px; height:32px; color:#fff; font-weight:bold; background:url(../images/button_on.png) no-repeat bottom; margin-right:10px;}
.label { font-size:12px; }
</style>
<script language="JavaScript">
var strList="";
// 去空格
String.prototype.Trim = function() 
{ 
	return this.replace(/(^\s*)|(\s*$)/g, ""); 
} 

function funLoad()
{
	document.getElementById("showTable").style.display="block";
	document.getElementById("showdiv").style.display="none";
	document.body.removeChild(document.getElementById("load"));
}
function funClearValue()
{
strList="";
	document.getElementById("xx").value="充值";
	document.getElementById("xx").disabled="";
	document.getElementById("account").value="";
	document.getElementById("accountNum").value=10;
	var obj=document.getElementById("accountNum").options.length=0; 
	document.getElementById("dis").style.display="none";
	document.getElementById("services").options.length=0; 
	
	document.getElementById("disTwo").style.display="none";
	document.getElementById("servicesTwo").options.length=0; 
	
	document.getElementById("czslabc").innerHTML="充值面额:";
	document.getElementById("bzts").innerHTML="";
	document.getElementById("djckhref").innerHTML="<a href='#' onclick='funClick(1)' style='font-size:13px;font-weight:bold'>点击查看</a>";
	
	document.getElementById("gamecodeType").style.display="none";
	document.getElementById("gamecodeTypeValue").value=1;

}

function funHref(api,corp,code,mprice,point,unit,name)
{
	//切换界面
	funClearValue();
	//填充充值界面
	document.getElementById("showTable").style.display="none";
	document.getElementById("showdiv").style.display="block";
	document.getElementById("corp").innerHTML=corp;
	document.getElementById("name").innerHTML=name;
	document.getElementById("mprice").innerHTML=mprice;
	document.getElementById("point").innerHTML=point;
	document.getElementById("unit").innerHTML=unit;
	document.getElementById("api").value=api;
	document.getElementById("gameType").value=code;
	
	var abc="元";
	if(api=="qqes4x")
	{
		document.getElementById("czslabc").innerHTML="充值月数:";
		document.getElementById("bzts").innerHTML="(10元1个月)";
		abc="月";
	}
	
	
	//加载购买数量下拉框，根据不同的游戏加载不懂得列表值
	var obj=document.getElementById("accountNum"); 
	<%
		for(int i=0;i<arryList.size();i++)
		{
		%>
			if(api=="<%=((GameInterface)arryList.get(i)).getGameapi()%>")
			{
				if("<%=((GameInterface)arryList.get(i)).getContbuy()%>"==null || "<%=((GameInterface)arryList.get(i)).getContbuy()%>"=="0" || "<%=((GameInterface)arryList.get(i)).getContbuy()%>"=="")
				{
					var start=<%=((GameInterface)arryList.get(i)).getStartbuy()%>;
					var end=<%=((GameInterface)arryList.get(i)).getEndbuy()%>;
					for(var u=start;u<=end;u++)
					{
						if(end>=500)
						{
							if(u%20==0)
							{
								obj.options.add(new Option(u+abc,u)); //这个兼容IE与firefox
							}
						}
						else
						{
							obj.options.add(new Option(u+abc,u)); //这个兼容IE与firefox
						}
					}
				}
				else
				{
					var content="<%=((GameInterface)arryList.get(i)).getContbuy()%>";
					if(content.indexOf(",")!=-1)
					{
						var strs= new Array(); 
					    strs=content.split(",");
						for(var k=0;k<strs.length;k++)
						{
							obj.options.add(new Option(strs[k]+abc,strs[k])); //这个兼容IE与firefox 
						}
					}
					else
					{
						obj.options.add(new Option(content+abc,content)); //这个兼容IE与firefox 
					}
				}
			}
		<%
		}
	%>
	if(api!=null && api!="" && code!="" && code!=null)
	{
		$.post(
		    	"<%=path%>/business/zhiFuBaoCharge.do?method=queryGameArea",
		      	{
		      		gameapi:api,
		            gameCode:code
		      	},
		      	function (data){
		           	if(data!=null && data!="")
		           	{
		           		if(data[0].mess==undefined)
		           		{
		           			var object=document.getElementById("services");
			           		for(var i=0;i<data.length;i++)
			           		{
			           			object.options.add(new Option(data[i].name,data[i].id));
			           			var arryList=data[i].litGameServer;
			           			for(var n=0;n<arryList.length;n++)
			           			{
			           				strList=strList+data[i].id+";"+arryList[n].id+";"+arryList[n].name+"#";
			           			}
			           			if(i==0 && strList.Trim()!="")
			           			{
			           				funQyLoad(data[i].id);
			           			}
			           		}
			           		document.getElementById("dis").style.display="";
		           		}
		           		else
		           		{
		           			alert(data[0].mess);
		           			window.location.reload();
		           		}
		           		
		           	}
		      	},"json"
			)
	}
	if(api=="qqes4x")
	{
		document.getElementById("moneyJE").innerHTML=parseInt(document.getElementById("accountNum").value)*parseInt(mprice)+"元";
	}
	else
	{
		document.getElementById("moneyJE").innerHTML=document.getElementById("accountNum").value+"元";
	}
	if(api=="163es")
	{
		document.getElementById("gamecodeType").style.display="";
		document.getElementById("gameType").value=document.getElementById("gamecodeTypeValue").value;
	}
}

function funupdateType()
{
	document.getElementById("gameType").value=document.getElementById("gamecodeTypeValue").value;
}


function funSelect(className)
{
	if(document.getElementById("api").value=="qqes4x")
	{
		document.getElementById("moneyJE").innerHTML=parseInt(className.value)*parseInt(document.getElementById("mprice").innerHTML)+"元";
	}
	else
	{
		document.getElementById("moneyJE").innerHTML=className.value+"元";
	}
}

function check()
{
	var gameapi=document.getElementById("api").value;
	var in_acct=document.getElementById("account").value;
	var qbnum=document.getElementById("accountNum").value;
	var numberId=document.getElementById("services").value;
	var gameType=document.getElementById("gameType").value;
	
	var servicesTwo=document.getElementById("servicesTwo").value;
	
	if(in_acct.Trim().length<=0)
	{
		//window.parent.showAlert(200,"请输入游戏账号!");
		alert("请输入游戏账号!");
		return ;
	}
	
	document.getElementById("xx").value="充值中,,,";
	document.getElementById("xx").disabled="disabled";
	functionLoad();
	$.post(
	    	"<%=path%>/business/zhiFuBaoCharge.do?method=qbTransfer",
	      	{
	      		in_acct:in_acct,
	            qbnum:qbnum,
	            gameapi:gameapi,
	            gameId:numberId,
	            gamecode:gameType,
	            gameaddress:servicesTwo
	      	},
	      	function (data){
	           	document.getElementById("xx").value="充值";
				document.getElementById("xx").disabled="";
				//删除加载提示框
				document.body.removeChild(document.getElementById("load"));
				alert(data[0].mess);
	      		return ;
	      	},"json"
		)
}

//添加加载提示框
function functionLoad()
{
	if(!document.getElementById("load"))
	{
		var body=document.getElementsByTagName('body')[0];
		var left=(document.body.offsetWidth-350)/2;
		var div="<div id='load' style='border:1px solid black;background-color:white;position:absolute;top:200px;left:"+left+"px;width:350px;height:90px;z-Index:999;text-align:center;font-weight:bold;'><div style='border-bottom:1px solid #cccccc;height:25px;line-height:25px;text-align:left;padding-left:10px;'>温馨提示</div><div style='width:100%;height:65px;line-height:65px;'><img src='<%=path%>/images/load.gif' />&nbsp;充值中，请稍后，，，，</div></div>";
		body.innerHTML=body.innerHTML+div;
	}
}

//采购信息 
function funClick(className)
{
	if(className=="1")
	{
		//display:block;
		var gameapi=document.getElementById("api").value;
		var in_acct=document.getElementById("account").value;
		var qbnum=document.getElementById("accountNum").value;
		if(in_acct=="")
		{
			alert("请输入账号!");
			reutrn ;
		}
		$.post(
	    	"<%=path%>/qb/qbResult.do?method=getMoney",
	      	{
	      		price :qbnum,
	      		gametype:gameapi
	      	},
	      	function (data){
	      		var mmm;
      			mmm = data[0].mon;
      			document.getElementById("djckhref").innerHTML=mmm+",<a onclick='funClick(2)' href='#' style='font-size:13px;font-weight:bold'>点击收起</a>";
	      		return ;
	      	},"json"
	     )
	}
	else
	{
		//display:none;
		document.getElementById("djckhref").innerHTML="<a href='#' onclick='funClick(1)' style='font-size:13px;font-weight:bold'>点击查看</a>";
	}
}

function funOnchangeQy(className)
{
	document.getElementById("disTwo").style.display="none";
	document.getElementById("servicesTwo").options.length=0;
	funQyLoad(className.value);
}

function funQyLoad(id)
{
	if(strList.Trim()!="")
	{
		var object=document.getElementById("servicesTwo");
		var arryLists=strList.split("#");
		var bool=false;
		for(var n=0;n<arryLists.length;n++)
		{
			if(arryLists[n].indexOf(id+";")!=-1)
			{
				object.options.add(new Option(arryLists[n].split(";")[2],arryLists[n].split(";")[1]));
				bool=true;
			}
		}
		if(bool)
		{
			document.getElementById("disTwo").style.display="";
		}
	}
}
</script>
</head>
<body style="overflow-y:auto;">
<div id="tabbox" style="overflow:hidden; margin:0px auto;width: auto;" >
  <div id="tabs_title" style="background:#f8f8f8; height:40px; border-bottom:1px solid #e1e2e2; width:100%; float:right;">
    <ul class="tabs_wzdh" style=" width:200px; float:left;">
      <li style="list-style:none; padding:0px; margin-left:-20px; font-size:14px; font-weight:bold;">游戏充值</li>
      </ul>
  </div>
</div>
<br />
<table id="showTable"  style="border:1px solid #00D5FF;border-bottom:0px;margin-top:-10px;" width="100%" cellspacing="0">
	<c:forEach items="${map}" var="mm" >
		<tr>
			<td style="text-align:left;font-size:12px; font-weight:bold;text-align:center;border-bottom:1px solid #00D5FF;" width=120>
				${fn:split(mm.key,',')[1]}
			</td>
			<td  style="border-bottom:1px solid #00D5FF;border-left:1px solid #00D5FF;">
				<table border=0 cellpadding="0" cellspacing="0" style="width:100%">
					<tr>
						<c:forEach items="${mm.value}" var="arr" varStatus="index" >
							<td style="width:150px;height:25px;font-size:12px;text-align:left;">
								<a href="javascript:window.parent.scrollBy(0,-1000);" onclick="funHref('${arr.api}','${arr.corp}','${arr.code}','${arr.mprice}','${arr.point}','${arr.unit}','${arr.name}')" style="margin-left:15px;">${arr.name}</a>
							</td>
							<c:if test="${(index.index+1)%5==0}">
								</tr>
								<tr>
							</c:if>
						</c:forEach>
					
					</tr>
				</table>
			</td>
		</tr>
	</c:forEach>
</table>
<br/>
<div  id="showdiv" style=" margin: 0 auto;text-align: center;margin-top:50px;width:450px;display:none;box-shadow: 0 0 3px #000;">
	<table style="border:1px solid #cccccc;width:450px;height:340px;">
		<tr><td style="height:2px;width:150px;">&nbsp;</td></tr>
		<tr>
			<td style="text-align:right;font-weight:bold;font-size:13px;">
				游戏所属公司:
			</td>
			<td style="text-align:left;font-weight:bold">
				<label id="corp" style="font-size:13px;"></label>
			</td>
		</tr>
		<tr>
			<td style="text-align:right;font-weight:bold;font-size:13px;">
				游戏名称:
			</td>
			<td style="text-align:left;font-weight:bold">
				<label id="name" style="font-size:13px;"></label>
			</td>
		</tr>
		<tr>
			<td style="text-align:right;font-weight:bold;font-size:13px;">
				充值账号:
			</td>
			<td style="text-align:left;font-weight:bold">
				<input id="account" type="text" value="" style="width:150px;"/>
			</td>
		</tr>
		<tr id="gamecodeType"  style="display:none;">
			<td style="text-align:right;font-weight:bold;font-size:13px;" >
				游戏类型：
			</td>
			<td style="text-align:left;font-weight:bold;">
				<select style="width:150px;" id="gamecodeTypeValue" onchange="funupdateType()">
					<option value="1">充值游戏点</option>
					<option value="2">交易游戏货币</option>
				</select>
			</td>
		</tr>
		<tr  id="dis">
			<td style="text-align:right;font-weight:bold;font-size:13px;" >
				计费区域:
			</td>
			<td style="text-align:left;font-weight:bold;">
				<select style="width:150px;" id="services" onchange="funOnchangeQy(this)"></select>
			</td>
		</tr>
		<tr  id="disTwo">
			<td style="text-align:right;font-weight:bold;font-size:13px;" >
				选择区域:
			</td>
			<td style="text-align:left;font-weight:bold;">
				<select style="width:150px;" id="servicesTwo" ></select>
			</td>
		</tr>
		<tr>
			<td style="text-align:right;font-weight:bold;font-size:13px;" id="czslabc">
				充值面额:
			</td>
			<td style="text-align:left;font-weight:bold">
				<select style="width:150px;" id="accountNum" onclick="funSelect(this)"></select>
			</td>
		</tr>
		<tr>
			<td style="text-align:right;font-weight:bold;font-size:13px;" >
				订单金额:
			</td>
			<td style="text-align:left;font-weight:bold">
				<label id="moneyJE" style="font-size:13px;color:red;"></label>
			</td>
		</tr>
		<tr>
			<td style="text-align:right;font-weight:bold;font-size:13px;">
				兑换比例:
			</td>
			<td style="text-align:left;font-weight:bold">
				<label id="mprice" style="font-size:13px;"></label>:
				<label style="font-size:13px;" id="point"></label>
				 <label style="font-size:13px;" id="unit"></label>
				<label style="font-size:13px;color:red;font-weight:bold" id="bzts"></label>
			</td>
		</tr>
		<tr>
			<td style="text-align:right;font-weight:bold;font-size:13px;">
				采购信息:
			</td>
			<td style="text-align:left;font-weight:bold;font-size:13px;" id="djckhref">
				<a href="#" onclick="funClick(1)" style="font-size:13px;font-weight:bold">点击查看</a>
			</td>
		</tr>
		<tr>
			<td colspan="2">
				<input type="hidden" id="api" value=""/>
				<input id="gameType" type="hidden" value=""/>
			</td>
		</tr>
		<tr>
			<td >&nbsp;</td>
			<td colspan="1" >
				<div class="field-item_button_m" id="btnChag" style="width:150px;margin:0px 0 0px 10px;position:relative;">
					<input type="button"   name="Button" value="充值" class="field-item_button" onclick="check()" id="xx" />
					&nbsp;&nbsp;
					<a href="#" onclick="funLoad()" style="font-size:13px;font-weight:bold;position:absolute;bottom:0px;right:0px;"><<返回</a>
				</div>
			</td>
			
		</tr>
	</table>
</div>
</body>
</html>
