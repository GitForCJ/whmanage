<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page import="java.util.*"%>
<%@ page import="com.wlt.webm.rights.form.SysUserForm"%>
<jsp:useBean id="bi" scope="page" class="com.wlt.webm.business.bean.BiProd" />
<%
  String path = request.getContextPath();
  SysUserForm userSession = (SysUserForm) request.getSession().getAttribute("userSession");
  if(null==userSession||userSession.getUsername()==null){
      response.sendRedirect(path+"/rights/wltlogin.jsp");
      return ;
	} 
  String termId=request.getParameter("termId");
  String atttermId=request.getAttribute("termId")+"";
  String str="";
  if(termId==null || "".equals(termId))
  {
  	if(atttermId==null || "".equals(atttermId))
  	{
  		request.setAttribute("mess", "登录超时,请重新登录");
  		response.sendRedirect(path+"/rights/wltlogin.jsp");
  		return ;
  	}
  	else
  	{
  		str=atttermId;
  	}
  }
  else
  {
  	str=termId;
  }
  
 String indate=request.getParameter("indate")==null?"":request.getParameter("indate");
 if("".equals(indate))
 {
 	indate="null".equals((request.getAttribute("indate")+""))?"":request.getAttribute("indate")+"";
 }
 String endDate=request.getParameter("endDate")==null?"":request.getParameter("endDate");
 if("".equals(endDate))
 {
 	endDate="null".equals((request.getAttribute("endDate")+""))?"":request.getAttribute("endDate")+"";
 }
 List list=bi.getLakalaAccountList(str,indate,endDate);
 request.setAttribute("arryList",list);
%>
<html>
<head>
<title>万汇通</title>
<link href="../css/main_style.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href=../css/common.css>
<style type="text/css">
	a:hover{
		
		color:red;
	}
</style>
<script type="text/javascript" src="../js/util.js"></script>
<script language='javascript' src='../js/jquery.js'></script>
<script language="javascript" type="text/javascript" src="../My97DatePicker/WdatePicker.js"></script>
<SCRIPT type=text/javascript>
showMessage("${mess}");
function funLoad()
{
	if(!document.getElementById("load"))
	{
		var body=document.getElementsByTagName('body')[0];
		var left=(document.body.offsetWidth-350)/2;
		var div="<div id='load' style='border:1px solid black;background-color:white;position:absolute;top:180px;left:"+left+"px;width:350px;height:90px;z-Index:999;text-align:center;font-weight:bold;'><div style='border-bottom:1px solid #cccccc;height:25px;line-height:25px;text-align:left;padding-left:10px;'>温馨提示</div><div style='width:100%;height:65px;line-height:65px;'><img src='<%=path%>/images/load.gif' />&nbsp;资金提现处理中，请稍后，，，，</div></div>";
		body.innerHTML=body.innerHTML+div;
	}
}

function funShow(feeL,usernoL,facctL,refNumberL,idL,stateL,amountL,orderStaL,isbindingL,txstateL)
{
	document.getElementById("feeL").value=feeL;
	document.getElementById("usernoL").value=usernoL;
	document.getElementById("facctL").value=facctL;
	document.getElementById("refNumberL").value=refNumberL;
	document.getElementById("idL").value=idL;
	document.getElementById("stateL").value=stateL;
	document.getElementById("amountL").value=amountL;
	document.getElementById("orderStaL").value=orderStaL;
	document.getElementById("isbindingL").value=isbindingL;
	document.getElementById("txstateL").value=txstateL;
	document.getElementById("cz").innerHTML="处理中，，";
	document.getElementById("form1").submit();
	funLoad();
	return ;
}

function funsubmit()
{
	var indate=document.getElementById("indate").value.Trim();
	var endDate=document.getElementById("endDate").value.Trim();
	if(indate=="")
	{
		alert("请选择开始日期!");
		return ;
	}
	if(endDate=="")
	{
		alert("请选择截止日期!");
		return ;
	}
	if(endDate<indate)
	{
		alert("截止日期必须大于开始日期!");
		return ;
	}
	window.location.href="<%=path%>/AccountInfo/lakalaListTwo.jsp?termId=<%=str %>&indate="+indate+"&endDate="+endDate;
	return ;
}
// 去空格
String.prototype.Trim = function() 
{ 
	return this.replace(/(^\s*)|(\s*$)/g, ""); 
} 
</SCRIPT>
</head>
<body rightmargin="0" leftmargin="0"  topmargin="0" class="body_no_bg">
<div id="popdiv"><span id="sellist"></span><div style="clear:both"></div></div>
<div id="tabbox">
  <div id="tabs_title">
    <ul class="tabs_wzdh">
      <li>拉卡拉转款记录</li></ul>
  </div>
</div>
<form id="form1" action="<%=path %>/AccountInfo/lakala.do?method=AccountInfo" method="post">
<ul style="margin-top:10px;height:50px;;border:1px solid #cccccc;">
<li  style="text-align:center;line-height:50px;">
开始日期： 
<input class="Wdate" id="indate" name="indate" style="width:120px;height:25px;" type="text" onClick="WdatePicker()" value="<%=indate %>">
&nbsp;&nbsp;
截止日期：
<input class="Wdate" id="endDate" name="endDate" style="width:120px;height:25px;" type="text" onClick="WdatePicker()" value="<%=endDate %>">
&nbsp;&nbsp;
<input onclick="funsubmit()" type="button"  style="width:85px; height:32px; border:0px;color:#fff; font-weight:bold;background:url(<%=path%>/images/button_out.png) no-repeat bottom; cursor:pointer;" value="查询"/>
</li>
</ul>
<ul style="margin-top:10px;">
	<li style="font-size:15px;text-align:right;font-weight:bold;color:red;">
		<marquee scrollamount=5 scrolldelay=100 direction= left onMouseOut=this.start(); onMouseOver=this.stop(); align="left" width=100% height=20px>默认只显示近三天的转账记录</marquee>
	</li>
</ul>

<table id="myTable" cellspacing="0" cellpadding="0" class="tab_line" style="width:100%;text-align:left;">
<thead>
<tr height="25" class="tr_line_bottom">
<th style="border:1px solid #cccccc;">系统参考号</th>
<th style="border:1px solid #cccccc;">拉卡拉金额 </th>
<th style="border:1px solid #cccccc;">拉卡拉交易状态</th>
<th style="border:1px solid #cccccc;">交易金额</th>
<th style="border:1px solid #cccccc;">交易时间</th>
<th style="border:1px solid #cccccc;">加款状态 / 方式</th>
<th style="border:1px solid #cccccc;">操作</th>
</tr>
</thead>
<tbody id="tab">
<c:forEach items="${arryList}" var="li" >
<tr>
<td style="border:1px solid #cccccc;width:100px;">${li[0]}</td>
<td style="border:1px solid #cccccc;width:90px;">${li[1]/1000}</td>
<td style="border:1px solid #cccccc;width:90px;">
	<c:if test="${li[2]=='00'}">
		成功
	</c:if>
	<c:if test="${li[2]!='00'}">
		失败
	</c:if>
</td>
<td style="border:1px solid #cccccc;width:90px;">${li[4]/1000}</td>
<td style="border:1px solid #cccccc;width:140px;">${fn:substring(li[5], 0, 4)}-${fn:substring(li[5], 4, 6)}-${fn:substring(li[5], 6, 8)}&nbsp;${fn:substring(li[5], 8,10)}:${fn:substring(li[5], 10,12)}:${fn:substring(li[5], 12,14)}</td>
<td style="border:1px solid #cccccc;width:90px;">
	<c:if test="${li[6]==0}">
		未加款
	</c:if>
	<c:if test="${li[6]==1}">
		已加款
	</c:if>
	<c:if test="${li[6]==2}">
		处理中
	</c:if>
	<c:if test="${li[6]==3}">
		处理中订单
	</c:if>
	<c:if test="${li[6]==4}">
		已撤销
	</c:if>
	<br/>
${li[10]}</td>
<td style="border:1px solid #cccccc;width:90px;" id="cz">
	<c:if test="${li[3]!=0}"> <!-- 判断绑定 -->
		<c:if test="${li[2]=='00'}"><!-- 判断拉卡拉交易状态   -->
			<c:if test="${li[6]==0}">
				<c:if test="${(li[1]/1000)<1}">
					<font style="color:#cccccc;">金额限制</font>
				</c:if>
				<c:if test="${(li[1]/1000)>=1}">
					<a  style="text-decoration:underline;" href="javascript:funShow('${li[4]}','${li[7]}','${li[8]}','${li[0]}','${li[9]}','${li[6]}','${li[1]}','${li[2]}','${li[3]}','${li[5]}')">T+0加款</a>
					 <!--
					<font style="color:#cccccc;">禁止加款</font>
					-->
				</c:if>
			</c:if>
			<c:if test="${li[6]==1}">
				<font style="color:#cccccc;">已加款</font>
			</c:if>
			<c:if test="${li[6]==2}">
				<font style="color:#cccccc;">处理中</font>
			</c:if>
			<c:if test="${li[6]==3}">
				<font style="color:#cccccc;">订单(处理中)</font>
			</c:if>
			<c:if test="${li[6]==4}">
				<font style="color:#cccccc;">已撤销</font>
			</c:if>
		</c:if>
		<c:if test="${li[2]!='00'}">
			<font style="color:#cccccc;">拉卡拉订单失败</font>
		</c:if>
	</c:if>
	<c:if test="${li[3]==0}">
		未绑定
	</c:if>
</td>
</tr>
</c:forEach>
</table>
<input type="hidden" value="" name="totalfee" id="feeL"/>
<input type="hidden" value="" name="userno" id="usernoL"/>
<input type="hidden" value="" name="facct" id="facctL"/>
<input type="hidden" value="" name="refNumber" id="refNumberL"/>
<input type="hidden" value="" name="id" id="idL"/>
<input type="hidden" value="" name="state" id="stateL"/>
<input type="hidden" value="" name="amount" id="amountL"/>
<input type="hidden" value="" name="orderSta" id="orderStaL"/>
<input type="hidden" value="" name="isbinding" id="isbindingL"/>
<input type="hidden" value="" name="tradetime" id="txstateL"/>
<input type="hidden" value="<%=str %>" name="termId" />
<br/>
<br/>
</form>
<div style="padding-left:40px;padding-top:10px;margin-left: 20px;font-size: 13px; color: #FF6600;font-weight:bold;border:1px solid #FF9E4d;background-color: #FFFFE5;">
<ul>
<li style="list-style:none;">1、拉卡拉绑定列表。</li>
<li style="list-style:none;">&nbsp;</li>
</ul>
</div>
</body>
</html>