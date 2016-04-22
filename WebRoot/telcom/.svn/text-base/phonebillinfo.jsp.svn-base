<%@ page contentType="text/html; charset=GBK" language="java"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String numtype = request.getParameter("numType");
%>
<html>
	<head>
		<title>万汇通</title>
		<META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=GBK">
		<link REL="stylesheet" TYPE="text/css" HREF="../css/common.css">
		<script src="../js/jquery-1.8.2.min.js" type="text/javascript"></script> 
		<script  type="text/javascript" src="../js/util.js"></script>
		<script language='javascript' src='../js/jquery.js'></script>
<style>
	#tabbox {
		width: 100%;
		overflow: hidden;
		margin: 0px auto;
	}
	
	#tabs_title {
		background: #f8f8f8;
		height: 40px;
		border-bottom: 0px solid #e1e2e2;
		width: 100%;
		float: right;
	}
	
	.tabs_wzdh {
		float: left;
		font-family: '宋体';
		font-size: 14px;
	}
</style>
<script language="JavaScript">
	showMessage("${phonePayForm.message}");
	
	function goback(){
	  document.URL="telcomPhonepay.jsp"
	}
	function check(){
		with(document.forms[0]){
			if(payFee.value.trim().length==0){
				alert("交易金额不能为空");
				payFee.focus();
				return false;
			}
			 if(compareFloat(payFee.value.trim(),10)<0){
		    	alert("交易金额不能小于10元!");
				form1.fee.focus();
				return (false);
   			}
			if(compareFloat(payFee.value.trim(),500)>0){
				alert("交易金额必须小于500元!");
				payFee.focus();
				return false;
			}
	
			if(confirm("确认缴费,号码:"+document.getElementById("haoma").value+"，金额为："+payFee.value+"元")){
			  document.getElementById("cz").value="充值中...";
			  document.getElementById("cz").disabled="disabled";
	          target="_self";
	          submit(); 
      		}
		} 
	}
	function funGetAccountInfo(tradeobject,numbertype){
		$("#tbody").html("<tr><td style='height:275px;' colspan='7' id='errorMessage'><img src='<%=path%>/images/load.gif'/></td></tr>");
		$.post("<%=path%>/dianxin/telecom.do?method=queryAccountInfo",
	      	{
		      	tradeobject:tradeobject,
		      	numbertype:numbertype
	      	},
	      	function (data){
	      		if(data==-1 || data=="-1")
	      		{
	      			$("#tbody").html("<tr><td style='height:100px;' colspan='7' id='errorMessage'>暂无数据</td></tr>");
	      		}else{
	      			if(data.length>0){
	      				//document.getElementById("user_name").innerHTML=data[0][6].split(";")[1];
	      				//document.getElementById("user_name_hidden").value=data[0][6].split(";")[1];
	      				$("#user_name").html(data[0][6].split(";")[1]);
	      			}
	      			if(data[0][0]=="abc"){
	      				$("#tbody").html("<tr><td style='height:100px;' colspan='7' id='errorMessage'>暂无数据</td></tr>");
	      				return ;
	      			}
	      			var vs1="";
	      			for(var i=0;i<data.length;i++)
					{
						if(vs1.indexOf(data[i][6].split(";")[0])==-1){
							vs1=vs1+data[i][6].split(";")[0]+"#";
						}	
					}
					vs1=vs1.substring(0,vs1.length-1);
	      			var ars=vs1.split("#");
	      			$("#tbody").html("");
	      			var ss2="";
	      			for(var k=0;k<ars.length;k++){
	      				var bs=ars[k];
	      				var htmlStr="<tr><td style='' colspan='7' align='left'>&nbsp;&nbsp;月份:"+bs+"</td></tr>";
	      				var counts=0;
	      				for(var i=0;i<data.length;i++)
						{
							if(bs==data[i][6].split(";")[0]){
								counts++;
								htmlStr=htmlStr+"<tr>"+
									"<td style='border:1px solid #cccccc;height:25px;'>"+counts+"</td>"+
									"<td style='border:1px solid #cccccc;height:25px;'>"+data[i][0]+"</td>"+
									"<td style='border:1px solid #cccccc;height:25px;'>"+data[i][1]+"</td>"+
									"<td style='border:1px solid #cccccc;height:25px;'>"+data[i][2]+"</td>"+
									"<td style='border:1px solid #cccccc;height:25px;'>"+data[i][3]+"</td>"+
									"<td style='border:1px solid #cccccc;height:25px;'>"+data[i][4]+"</td>"+
									"<td style='border:1px solid #cccccc;height:25px;'>"+data[i][5]+"</td>"+
									"</tr>";
							}
						}
						ss2=ss2+htmlStr;
	      			}
	      			$("#tbody").html(ss2);
				}	      		
	      		return ;
	      	},"json"
		)
	}
	window.onload=function(){
		funGetAccountInfo("${phonePayForm.tradeObject}","${phonePayForm.numType}");
		return ;
	}
</script>
	</head>
	<body rightmargin="0" leftmargin="0" topmargin="0">
		<div id="tabbox">
			<div id="tabs_title">
				<ul class="tabs_wzdh" style="width:100%;height:100%">
					<li style="height:40px;line-height:40px;padding-left:10px;">
						<B>广东电信账单查询</B>
					</li>
				</ul>
			</div>
		</div>
		<br />
		<div style="font-size: 16px;">
			<html:form action="/dianxin/telecom.do?method=payBill" method="post">
				<table width="100%" border="0"  cellpadding="0" cellspacing="0">
					<tr>
						<td>
							<table width="100%" border="0" cellpadding="0" cellspacing="10">
								<tr>
									<td width="200px"  align="right">
										交易类型：
									</td>
									<td  align="left" height="35">
										电信缴费
									</td>
								</tr>
								<tr>
									<td width="200px" align="right">
										客户名称：
									</td>
									<td  align="left" height="35">
										<font id="user_name">${phonePayForm.userName}</font>
										<input type="hidden" name="userName" id="user_name_hidden"
											value="${phonePayForm.userName}">
									</td>
								</tr>
								<tr>
									<td width="200px" align="right">
										客户号码：
									</td>
									<td  align="left" height="35">
										${phonePayForm.tradeObject}
									</td>
								</tr>
								<tr>
									<td width="200px" align="right">
										账户余额：
									</td>
									<td  align="left" height="35">
										<fmt:formatNumber value="${phonePayForm.totalFee/100.0}" pattern="#0.00"/>元
										<input type="hidden" name="totalFee"
											value="${phonePayForm.totalFee}">
									</td>
								</tr>
								<tr>
									<td width="200px" align="right">
										本次充值金额：
									</td>
									<td  align="left" height="25">
										<c:if test="${(phonePayForm.totalFee/100.0)>=0}">
											<input
												style="font-family: '宋体'; font-size: 20px; font-weight: bold; width: 80px;"
												type="text" name=payFee ${readonly}  value="0">元				
										</c:if>
										<c:if test="${(phonePayForm.totalFee/100.0)<0}">
											<input
												style="font-family: '宋体'; font-size: 20px; font-weight: bold; width: 80px;"
												type="text" name=payFee
												${readonly}  value="<fmt:formatNumber value='${fn:substring(phonePayForm.totalFee/100.0,1,100)}' pattern='#0.00'/>">元
										</c:if>
									</td>
								</tr>
								<tr>
									<td  align="right" height="35">
										<input type="button" id="cz" name="Button" value="充值"
											onclick="check()"
											style="background: url(../images/button_out.png); display: block; border: 0px; width: 85px; height: 32px; color: #fff; font-weight: bold;">
									</td>
									<td>
										<input type="reset" name="Button2" value="返回"
											onclick="window.location='<%=path%>/telcom/telcomPhonepay.jsp'"
											style="background: url(../images/button_out.png); display: block; border: 0px; width: 85px; height: 32px; color: #fff; font-weight: bold;">
										<input type="hidden" name="numType" value="<%=numtype%>" />
										<input type="hidden" id="haoma"
											value="${phonePayForm.tradeObject}" />
									</td>
								</tr>
							</table>
						</td>
					</tr>
				</table>
				<div style="margin-top:5px;border:1px solid #cccccc;">
					<c:if test="${bool==true}">
						<ul>
							<li  style="text-align:right;padding-right:20px;padding:5px 5px 5px 5px;">
								<!-- <a href="javascript:funGetAccountInfo('${phonePayForm.tradeObject}','${phonePayForm.numType}')" style="font-size:14px;font-weight:bold;color:#0078B6">账单查询</a> -->
								<font style="font-size:16px;color:red;">显示客户名称及账单详情，请猛戳“账单查询”。</font>
								<input type="button" onclick="funGetAccountInfo('${phonePayForm.tradeObject}','${phonePayForm.numType}')"
									 style="width:85px;height:32px;background-color:#157BBD;color:#FFFFFF;border-radius:5px;" value="账单查询" />
							</li>
						</ul>
						<table width="100%" style="text-align:center;" border=0 cellpadding="0" cellspacing="0">
						 	<thead>
								<tr>
									<th style="height:25px;border:1px solid #cccccc;" >序号</th>
									<th style="height:25px;border:1px solid #cccccc;">发票项代码</th>
									<th style="height:25px;border:1px solid #cccccc;">发票项名称</th>
									<th style="height:25px;border:1px solid #cccccc;">客户号码</th>
									<th style="height:25px;border:1px solid #cccccc;">发票项费用(元)</th>
									<th style="height:25px;border:1px solid #cccccc;">帐期标识</th>
									<th style="height:25px;border:1px solid #cccccc;">运营商代码</th>
								</tr>
							</thead>
							<tbody id="tbody">
								<tr><td style='height:100px;' colspan='7' id='errorMessage'></td></tr>
							</tbody>
						</table>
					</c:if>
				</div>
				<input type="hidden" name="tradeObject"
					value="${phonePayForm.tradeObject}">
				<input type="hidden" name="seqNo" value="${phonePayForm.seqNo}">
			</html:form>
		</div>
	</body>
</html>
