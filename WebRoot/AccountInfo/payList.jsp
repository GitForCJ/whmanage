<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@ page import="java.util.*"%>
<%@ page import="com.wlt.webm.rights.form.SysUserForm"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
SysUserForm userSession = (SysUserForm) request.getSession().getAttribute("userSession");
  String path = request.getContextPath();
%>
<html>
<head>
<title>万汇通</title>
<link href="../css/main_style.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="../js/jquery-1.8.2.min.js"></script>
<script type="text/javascript" src="../js/util.js"></script>
<script language="javascript" type="text/javascript" src="../My97DatePicker/WdatePicker.js"></script>
<style type="text/css">
	#tables td{
		padding:0px 0px 0px 0px;
		border:1px solid #cccccc;
		border-right:0px;
		border-bottom:0px;
		height:30px;
	}
</style>
<script type="text/javascript">
showMessage("${mess}");
	//提示框
	function funLoad()
	{
		if(!document.getElementById("load"))
		{
			var body=document.getElementsByTagName('body')[0];
			var left=(document.body.offsetWidth-350)/2;
			var div="<div id='load' style='border:1px solid black;background-color:white;position:absolute;top:280px;left:"+left+"px;width:350px;height:90px;z-Index:999;text-align:center;font-weight:bold;'><div style='border-bottom:1px solid #cccccc;height:25px;line-height:25px;text-align:left;padding-left:10px;'>温馨提示</div><div style='width:100%;height:65px;line-height:65px;'><img src='<%=path%>/images/load.gif' />&nbsp;数据加载中，请稍后，，，，</div></div>";
			body.innerHTML=body.innerHTML+div;
		}
	}
	// 去空格
	String.prototype.Trim = function() 
	{ 
		return this.replace(/(^\s*)|(\s*$)/g, ""); 
	} 
	function checksubmit()
	{
		
		document.getElementById("form1").action="<%=path %>/business/acctbill.do?method=OneOrderList&subtype=0";
		document.getElementById("form1").submit();
		funLoad()
		return ;
	}
	
	function excelExport()
	{
		var ahidden=document.getElementById("ahidden");
		if(ahidden.value.Trim()=="")
		{
			alert("请先查询列表!");
			return ;
		}
	//	alert(ahidden.value.Trim());
		var arrlist=ahidden.value.Trim().split(";");
		document.getElementById("accountName").value=arrlist[0];
		document.getElementById("indate").value=arrlist[1];
		document.getElementById("enddate").value=arrlist[2];
		document.getElementById("inmoney").value=arrlist[3];
		document.getElementById("endmoney").value=arrlist[4];
		document.getElementById("OrderStateType").value=arrlist[5];
		document.getElementById("form1").action="<%=path %>/business/acctbill.do?method=OneOrderList&subtype=1";
		document.getElementById("form1").submit();
		return;
	}
	
	window.onload=function()
	{
		if("${OrderStateType}"=="")
		{
			document.getElementById("OrderStateType").value="-100";
		}
		else
		{
			document.getElementById("OrderStateType").value="${OrderStateType}";
		}
		if("${accountName}"!="" && "${accountName}"!=null && "${accountName}"!="null")
			document.getElementById("ahidden").value="${accountName};${indate};${enddate};${inmoney};${endmoney};${OrderStateType}";
	}
</script>

</head>
<body>
<div id="tabbox">
  <div id="tabs_title">
    <ul class="tabs_wzdh">
      <li>代理统计</li></ul>
  </div>
</div>
<form id="form1" action="" method="post">
<div class="header">
	<div class="topCtiy clear" style="padding-left:15px;">
		<li><input id="chaxun" type="button" name="Button" value="查询" class="field-item_button" onClick="checksubmit()"></li>
		<li><input id="daochu" type="button" name="Button" value="导出" class="field-item_button" onClick="excelExport();"></li>
	</div>
</div>
<div class="selCity" id="allCity" >
    <table  border="0" cellspacing="0" cellpadding="0">
		<tr>
			<%
				if("3".equals(userSession.getRoleType()) || "4".equals(userSession.getRoleType()))
				{
				%>
					<input type="hidden" value="<%=userSession.getUsername() %>" id="accountName" name="accountName" />
				<%
				}
				else
				{
					%>
					<td >
						代理账号:<input type="text" value="${accountName}" id="accountName" name="accountName" style="width:120px;height:25px;" maxlength="11" />
					</td>
					<%
				}
			 %>
			
		    <td >
		    	统计日期:<input class="Wdate" id="indate" name="indate" type="text" style="width:120px;height:25px;" onClick="WdatePicker()" value="${indate}">
		    		至
		   			<input class="Wdate" id="enddate" name="enddate" type="text" style="width:120px;height:25px;" onClick="WdatePicker()" value="${enddate}">
		    </td>
		    <td>
		    	金额:<input type="text" style="width:50px;height:25px;" value="${inmoney}" name="inmoney" maxlength="5" id="inmoney" onkeyup="value=value.replace(/[^\d]/g,'') "/>
		    	至
		    	<input type="text"  style="width:50px;height:25px;" value="${endmoney}" name="endmoney" maxlength="5" id="endmoney" onkeyup="value=value.replace(/[^\d]/g,'') "/>
		    </td>
		</tr>
		<tr>
			<td style="text-align:left;">
		    	交易状态:<select style="width:120px;height:25px;" name="OrderStateType" id="OrderStateType" >
		    		<option value="-100" >全部交易</option>
		    		<option value="0" >交易成功</option>
		    		<option value="1">交易失败</option>
		    		<option value="2">交易未响应</option>
		    		<option value="3">交易未处理</option>
		    		<option value="4">交易处理中</option>
		    		<option value="5">交易冲正</option>
		    		<option value="6">异常订单</option>
		    		<option value="7">交易已退费</option>
		    	</select>
		    </td>
		    <td></td>
		    <td></td>
		</tr>
	</table>
</div>
<div style="width:100%;height:30px;text-align:right;font-weight:bold;line-height:30px;font-size:15px;color:red;">
	不支持跨月统计数据&nbsp;&nbsp;
</div>
<div id="tables" style="width:100%;overflow-x:auto;">
	<table  cellspacing="0" cellpadding="0" width="100%"  style="font-size:13px;border-bottom:1px solid #cccccc;border-right:1px solid #cccccc" >
		<tr style="font-weight:bold;background-color:#E6EFF6;">
			<td rowspan=3 width=50>&nbsp;</td>
			
			<td colspan="4">移动充值</td>
			<td colspan="4">联通充值</td>
			<td colspan="4">电信充值</td>
			
			<td colspan="2">Q币充值</td>
			<td colspan="2">游戏充值</td>
			<td colspan="2">支付宝充值</td>
			<td colspan="2">汇总</td>
		</tr>
		<tr style="font-weight:bold;background-color:#E6EFF6;">
			<td style="" colspan="2">本省</td>
			<td style="" colspan="2">外省</td>
			
			<td style="" colspan="2">本省</td>
			<td style="" colspan="2">外省</td>
			
			<td style="" colspan="2">本省</td>
			<td style="" colspan="2">外省</td>
			
			<td style="" rowspan=2>笔<br/>数</td>
			<td style="" rowspan=2>金额<br/>(元)</td>
			<td style="" rowspan=2>笔<br/>数</td>
			<td style="" rowspan=2>金额<br/>(元)</td>
			<td style="" rowspan=2>笔<br/>数</td>
			<td style="" rowspan=2>金额<br/>(元)</td>
			<td style="" rowspan=2>笔<br/>数</td>
			<td style="" rowspan=2>金额<br/>(元)</td>
		</tr>
		<tr style="background-color:#E6EFF6; font-weight:bold;">
			<td style="">笔<br/>数</td>
			<td style="">金额<br/>(元)</td>
			
			<td style="">笔<br/>数</td>
			<td style="">金额<br/>(元)</td>
			
			<td style="">笔<br/>数</td>
			<td style="">金额<br/>(元)</td>
			
			<td style="">笔<br/>数</td>
			<td style="">金额<br/>(元)</td>
			
			<td style="">笔<br/>数</td>
			<td style="">金额<br/>(元)</td>
			
			<td style="">笔<br/>数</td>
			<td style="">金额<br/>(元)</td>
		</tr>
		<c:forEach items="${accountList}" var="arrList" varStatus="index">
			<c:if test="${(index.index+1)%2==0}">
				<tr style="color:red;">
			</c:if>
			<c:if test="${(index.index+1)%2!=0}">
				<tr style="color:red;border:1px solid #cccccc">
			</c:if>
				<td style="color:black;word-break:break-all;">${arrList[0]}<br/>${arrList[1]}</td>
				<td  style="">${arrList[2]}&nbsp;</td>
				<td  style=""><fmt:formatNumber value="${arrList[3]}" pattern="#0.0"/>&nbsp;</td>
				<td style="">${arrList[4]}&nbsp;</td>
				<td style=""><fmt:formatNumber value="${arrList[5]}" pattern="#0.0"/>&nbsp;</td>
				
				<td style="">${arrList[6]}&nbsp;</td>
				<td style="" ><fmt:formatNumber value="${arrList[7]}" pattern="#0.0"/>&nbsp;</td>
				<td style="">${arrList[8]}&nbsp;</td>
				<td style=""><fmt:formatNumber value="${arrList[9]}" pattern="#0.0"/>&nbsp;</td>
				
				<td  style="">${arrList[10]}&nbsp;</td>
				<td style="" ><fmt:formatNumber value="${arrList[11]}" pattern="#0.0"/>&nbsp;</td>
				<td style="">${arrList[12]}&nbsp;</td>
				<td style=""><fmt:formatNumber value="${arrList[13]}" pattern="#0.0"/>&nbsp;</td>
				
				<td style="" >${arrList[14]}&nbsp;</td>
				<td  style=""><fmt:formatNumber value="${arrList[15]}" pattern="#0.0"/>&nbsp;</td>
				<td  style="">${arrList[16]}&nbsp;</td>
				<td  style=""><fmt:formatNumber value="${arrList[17]}" pattern="#0.0"/>&nbsp;</td>
				<td  style="">${arrList[18]}&nbsp;</td>
				<td style="" ><fmt:formatNumber value="${arrList[19]}" pattern="#0.0"/>&nbsp;</td>
				<td style="" >${arrList[20]}&nbsp;</td>
				<td  style=""><fmt:formatNumber value="${arrList[21]}" pattern="#0.0"/>&nbsp;</td>
			</tr>
		</c:forEach>
		<tr style="background-color:yellow;color:red;font-weight:bold;border:1px solid #cccccc">
				<td  style="word-break:break-all;">${strsList[1]}</td>
				<td  style=""><fmt:formatNumber value="${strsList[2]}" pattern="#0"/></td>
				<td  style=""><fmt:formatNumber value="${strsList[3]}" pattern="#0.0"/></td>
				<td  style=""><fmt:formatNumber value="${strsList[4]}" pattern="#0"/></td>
				<td  style=""><fmt:formatNumber value="${strsList[5]}" pattern="#0.0"/></td>
				
				<td  style=""><fmt:formatNumber value="${strsList[6]}" pattern="#0"/></td>
				<td  style=""><fmt:formatNumber value="${strsList[7]}" pattern="#0.0"/></td>
				<td  style=""><fmt:formatNumber value="${strsList[8]}" pattern="#0"/></td>
				<td  style=""><fmt:formatNumber value="${strsList[9]}" pattern="#0.0"/></td>
				
				<td  style=""><fmt:formatNumber value="${strsList[10]}" pattern="#0"/></td>
				<td  style=""><fmt:formatNumber value="${strsList[11]}" pattern="#0.0"/></td>
				<td  style=""><fmt:formatNumber value="${strsList[12]}" pattern="#0"/></td>
				<td  style=""><fmt:formatNumber value="${strsList[13]}" pattern="#0.0"/></td>
				
				<td  style=""><fmt:formatNumber value="${strsList[14]}" pattern="#0"/></td>
				<td  style=""><fmt:formatNumber value="${strsList[15]}" pattern="#0.0"/></td>
				
				<td  style=""><fmt:formatNumber value="${strsList[16]}" pattern="#0"/></td>
				<td  style=""><fmt:formatNumber value="${strsList[17]}" pattern="#0.0"/></td>
				<td  style=""><fmt:formatNumber value="${strsList[18]}" pattern="#0"/></td>
				<td  style=""><fmt:formatNumber value="${strsList[19]}" pattern="#0.0"/></td>
				<td  style=""><fmt:formatNumber value="${strsList[20]}" pattern="#0"/></td>
				<td  style=""><fmt:formatNumber value="${strsList[21]}" pattern="#0.0"/></td>
				
			</tr>
	</table>
</div>
</form>
<input type="hidden" id="ahidden" value=""/>
</body>
</html>