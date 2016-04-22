<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page import="java.util.*"%>
<%@ page import="com.wlt.webm.rights.form.SysUserForm"%>
<jsp:useBean id="bi" scope="page"
	class="com.wlt.webm.business.bean.BiProd" />
<%
	String path = request.getContextPath();
	SysUserForm userSession = (SysUserForm) request.getSession()
			.getAttribute("userSession");
	if (null == userSession || userSession.getUsername() == null) {
		response.sendRedirect(path + "/rights/wltlogin.jsp");
		return;
	}
	String termId = request.getParameter("termId");
	String atttermId = request.getAttribute("termId") + "";
	String str = "";
	if (termId == null || "".equals(termId)) {
		if (atttermId == null || "".equals(atttermId)) {
			request.setAttribute("mess", "登录超时,请重新登录");
			response.sendRedirect(path + "/rights/wltlogin.jsp");
			return;
		} else {
			str = atttermId;
		}
	} else {
		str = termId;
	}

	String indate = request.getParameter("indate") == null ? ""
			: request.getParameter("indate");
	if ("".equals(indate)) {
		indate = "null".equals((request.getAttribute("indate") + "")) ? ""
				: request.getAttribute("indate") + "";
	}
	String endDate = request.getParameter("endDate") == null ? ""
			: request.getParameter("endDate");
	if ("".equals(endDate)) {
		endDate = "null".equals((request.getAttribute("endDate") + "")) ? ""
				: request.getAttribute("endDate") + "";
	}
	List list = bi.getHuiFuKuanList(str, indate, endDate);
	request.setAttribute("arryList", list);
%>
<html>
	<head>
		<title>万汇通</title>
		<link href="../css/main_style.css" rel="stylesheet" type="text/css" />
		<link rel="stylesheet" type="text/css" href=../css/common.css>
		<style type="text/css">
a:hover {
	color: red;
}
</style>
		<script type="text/javascript" src="../js/util.js"></script>
		<script language='javascript' src='../js/jquery-1.8.2.min.js'></script>
		<script language="javascript" type="text/javascript"
			src="../My97DatePicker/WdatePicker.js"></script>
		<SCRIPT type=text/javascript>
showMessage("${mess}");
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
	window.location.href="<%=path%>/business/wlthfklistTwo.jsp?termId=<%=str%>&indate="+indate+"&endDate="+endDate;
	return ;
}
// 去空格
String.prototype.Trim = function() 
{ 
	return this.replace(/(^\s*)|(\s*$)/g, ""); 
}

function addMoney(id){

    if(id==""||null==id){
    alert("加款失败");
   	 return ;
    }
    document.getElementById("tj"+id).value="处理中";
    document.getElementById("tj"+id).disabled=true;
	$.post(
    	$("#rootPath").val()+"/business/prod.do?method=hfkAddMoney",
      	{
      	"id":id
      	},
      	function (data){
      		if(data == 0){
      			alert("加款成功");
      			document.getElementById("td"+id).innerHTML="已加款/T+0";
      			document.getElementById("tj"+id).value="T+0加款";
      		}else{
      		    document.getElementById("tj"+id).value="T+0加款";
    			document.getElementById("tj"+id).disabled=false;
      			alert("加款失败,错误码"+data);
      		}
      	}
	)
}
</SCRIPT>
	</head>
	<body rightmargin="0" leftmargin="0" topmargin="0" class="body_no_bg">
<input type="hidden" id="rootPath" value="<%=path %>"/>
		<div id="popdiv">
			<span id="sellist"></span>
			<div style="clear: both"></div>
		</div>
		<div id="tabbox">
			<div id="tabs_title">
				<ul class="tabs_wzdh">
					<li>
						慧付款转款记录
					</li>
				</ul>
			</div>
		</div>
		<form id="form1" action="" method="post">
			<ul
				style="margin-top: 10px; height: 50px;; border: 1px solid #cccccc;">
				<li style="text-align: center; line-height: 50px;">
					开始日期：
					<input class="Wdate" id="indate" name="indate"
						style="width: 120px; height: 25px;" type="text"
						onClick="WdatePicker()" value="<%=indate%>">
					&nbsp;&nbsp; 截止日期：
					<input class="Wdate" id="endDate" name="endDate"
						style="width: 120px; height: 25px;" type="text"
						onClick="WdatePicker()" value="<%=endDate%>">
					&nbsp;&nbsp;
					<input onclick="funsubmit()" type="button"
						style="width: 85px; height: 32px; border: 0px; color: #fff; font-weight: bold; background: url(<%=path%>/images/button_out.png) no-repeat bottom; cursor: pointer;"
						value="查询" />
				</li>
			</ul>
			<ul style="margin-top: 10px;">
				<li
					style="font-size: 15px; text-align: right; font-weight: bold; color: red;">
					<marquee scrollamount=5 scrolldelay=100 direction=left
						onMouseOut=this.start(); onMouseOver=this.stop(); align="left"
						width=100% height=20px>
						默认只显示近三天的转账记录
					</marquee>
				</li>
			</ul>

			<table id="myTable" cellspacing="0" cellpadding="0" class="tab_line"
				style="width: 100%; text-align: left;">
				<thead>
					<tr height="25" class="tr_line_bottom">
						<th style="border: 1px solid #cccccc;">
							刷卡面值
						</th>
						<th style="border: 1px solid #cccccc;">
							实际面值
						</th>
						<th style="border: 1px solid #cccccc;">
							慧付款时间
						</th>
						<th style="border: 1px solid #cccccc;">
							万汇通加款时间
						</th>
						<th style="border: 1px solid #cccccc;">
							卡类型
						</th>
						<th style="border: 1px solid #cccccc;">
							加款状态 / 方式
						</th>
						<th style="border: 1px solid #cccccc;">
							操作
						</th>
					</tr>
				</thead>
				<tbody id="tab">
					<c:forEach items="${arryList}" var="li" >
						<tr>
							<td style="border:1px solid #cccccc;width:100px;">${li[1]/1000}元</td>
							<td style="border:1px solid #cccccc;width:100px;">${li[2]/1000}元</td>
							<td style="border:1px solid #cccccc;width:100px;">${li[3]}</td>
							<td style="border:1px solid #cccccc;width:120px;">${li[4]}</td>
							<td style="border:1px solid #cccccc;width:100px;">
								<c:if test="${li[5]==0}">
									信用卡
								</c:if>
								<c:if test="${li[5]==1}">
									借记卡
								</c:if>
								<c:if test="${li[5]!=0 && li[5]!=1}">
									未知
								</c:if>
							</td>
							<td style="border:1px solid #cccccc;width:100px;" id="td${li[8]}">
								<c:if test="${li[6]==0}">
									未加款
								</c:if>
								<c:if test="${li[6]==1}">
									已加款
								</c:if>
								<c:if test="${li[6]!=0 && li[6]!=1}">
									未知
								</c:if>
								/${li[7]}
							</td>
<td style="border:1px solid #cccccc;width:100px;">
								<c:if test="${li[6]==0}">
<input type="button" value="T+0加款" onclick="addMoney('${li[8]}')" id="tj${li[8]}"/>
								</c:if>
								<c:if test="${li[6]!=0}">
<input type="button" value="T+0加款"  disabled="disabled"/>
								</c:if>
</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</form>
		<div
			style="padding-left: 40px; padding-top: 10px;font-size: 13px; color: #FF6600; font-weight: bold; border: 1px solid #FF9E4d; background-color: #FFFFE5;">
			<ul>
				<li style="list-style: none;">
					1、账户加款精确到小数点后三位。 
				</li>
				<li style="list-style:none;">&nbsp;</li>
				<li style="list-style: none;">
					2、T+0/结算时收取2元/笔手续费。 
				</li>
				<li style="list-style:none;">&nbsp;</li>
				<li style="list-style: none;">
					3、自动结算时间为：T+1/06:00自动结算。
				</li>
				<li style="list-style:none;">&nbsp;</li>
			</ul>
		</div>
	</body>
</html>