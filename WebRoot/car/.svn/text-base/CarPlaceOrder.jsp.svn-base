<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">

		<title>违章列表</title>
		<link href="<%=path%>/css/main_style.css" rel="stylesheet"
			type="text/css" />
		<script src="<%=path%>/js/jquery-1.8.2.min.js" type="text/javascript"></script>
		<script  type="text/javascript" src="<%=path%>/js/util.js"></script>
<style>
* {
	margin: 0;
	padding: 0;
	font-family: "微软雅黑";
	font-size: 14px;
	font-weight: normal;
}

.wrapper {
	width: 830px;
	height: auto;
	overflow: hidden;
	margin: 0 auto;
	margin-top: 10px;
}

.content tr {
	height: 60px;
}

.content td { /*width:118px;*/
	text-align: center;
	padding: 0 10px;
}

.td_color span {
	color: #f00;
}
</style>
<script type="text/javascript">   
showMessage("${mess}");

  function Checkeds(className,isbool){
 	if(isbool==2){
 		className.checked=false;
 		alert("此条违章不可代办!");
 		return ;
 	}
 	var mm=0;
 	var flag=false;
 	var lists=document.getElementsByName("ids");
 	for(var i=0;i<lists.length;i++){
 		if(lists[i].checked==true){
 			mm=mm+parseFloat(lists[i].value.split("#")[0]);
 			flag=true;
 		}
 	}
 	if(flag){
 		document.getElementById("zMoney").innerHTML=parseFloat(mm/1000).toFixed(2);
 		document.getElementById("tss").style.display="";
 	}else{
 		document.getElementById("tss").style.display="none";
 	}
 }
function funLoad()
{
	if(!document.getElementById("load"))
	{
		var body=document.getElementsByTagName('body')[0];
		var left=(document.body.offsetWidth-350)/2;
		var div="<div id='load' style='border:1px solid black;background-color:white;position:absolute;top:280px;left:"+left+"px;width:350px;height:90px;z-Index:999;text-align:center;font-weight:bold;'><div style='border-bottom:1px solid #cccccc;height:25px;line-height:25px;text-align:left;padding-left:10px;'>温馨提示</div><div style='width:100%;height:65px;line-height:65px;'><img src='<%=path%>/images/load.gif' />&nbsp;订单处理中，请稍后，，，，</div></div>";
		body.innerHTML=body.innerHTML+div;
	}
}
 function funDown(){
 	var mm=0;
 	var flag=false;
 	var lists=document.getElementsByName("ids");
 	for(var i=0;i<lists.length;i++){
 		if(lists[i].checked==true){
 			mm=mm+parseFloat(lists[i].value.split("#")[0]);
 			flag=true;
 		}
 	}
 	if(flag){
 		if(confirm("下单金额为: "+parseFloat(mm/1000).toFixed(2)+" 元!")){
 			document.getElementById("downId").style.display="none";
 			document.forms[0].submit();
 			funLoad();
 			return;
 		}else{
 			return ;
 		}
 	}else{
 		alert("请选择需要处理的违章记录!");
 		return ;
 	}
 }
</script>
	</head>

	<body>
		<div id="tabbox">
			<div id="tabs_title">
				<ul class="tabs_wzdh">
					<li>
						违章处理
					</li>
				</ul>
			</div>
		</div>
		<div class="wrapper">
			<form action="<%=path %>/cars.do?method=carOrderAction" method="post">
			<input type="hidden" value="0" name="requestType"/>
			<input type="hidden" value="${carInfo.car_userphone}" name="car_userphone"/>
			<input type="hidden" value="${carInfo.car_username}" name="car_username" />
			<input type="hidden" value="${carInfo.car_engine}" name="car_engine"/>
			<input type="hidden" value="${carInfo.car_code}" name="car_code"/>
			<input type="hidden" value="${carInfo.car_type}" name="car_type"/>
			<input type="hidden" value="${carInfo.car_number}"  name="car_number" />
			<input type="hidden" value="${carInfo.province_name}"  name="province_name"  />
				<div class="content">
					<div>
						<table style="border: 1px solid #cccccc" cellpadding="0"
							cellspacing="0" width="100%">
							<tr height="50px" style="background-color: #e2f2ff;">
								<td style="border: 1px solid #cccccc; width: 20px">
									&nbsp;
								</td>
								<td style="border: 1px solid #cccccc;">
									违章信息
								</td>
								<td style="border: 1px solid #cccccc; width: 80px">
									罚款金额
									<br />
									(元)
								</td>
								<td style="border: 1px solid #cccccc; width: 80px">
									滞纳金额
									<br />
									(元)
								</td>
								<td style="border: 1px solid #cccccc; width: 80px">
									服务费用
									<br />
									(元)
								</td>
								<td style="border: 1px solid #cccccc; width: 80px">
									小计
									<br />
									(元)
								</td>
								<td style="border: 1px solid #cccccc; width: 80px">
									违章扣分
									<br />
									(供参考)
								</td>
								<td style="border: 1px solid #cccccc; width: 80px">
									是否可下单
								</td>
							</tr>
							<tr>
								<td colspan="8" style="text-align: left; padding-left: 5px;">
									车牌号:&nbsp;
									<span style="color: #09F; font-size: 18px;">${carInfo.province_name}${carInfo.car_number}</span>
								</td>
							</tr>

							<c:forEach items="${arry}" var="li">
								<tr style="background-color: #e2f2ff;">
									<td style="border: 1px solid #cccccc; width: 30px">
										<c:if test="${li[7]==1 && li[10]==102}">
											<input type="checkbox" name="ids" onclick="Checkeds(this,1)"
												value="${li[4]+li[5]+li[6]+li[13]}#${li[0]}" />
										</c:if>
										<c:if test="${li[7]!=1 || li[10]!=102}">
											<input type="checkbox" name="list" onclick="Checkeds(this,2)"
												disabled="disabled" value="" />
										</c:if>
									</td>
									<td
										style="border: 1px solid #cccccc; padding: 0px 0px 0px 0px;">
										<table border="0" cellpadding="0" cellspacing="0"
											width="260px" style="margin: 0px 0px 0px 0px">
											<tr style="height: 25px;">
												<td style="width: 80px; text-align: right;">
													违章时间:
												</td>
												<td
													style="width: 180px; text-align: left; height: 25px; word-wrap: break-word; word-break: break-all">
													${li[1]}
												</td>
											</tr>
											<tr style="height: 25px;">
												<td style="width: 80px; text-align: right;">
													违章地点:
												</td>
												<td
													style="width: 180px; text-align: left; word-wrap: break-word; word-break: break-all">
													${li[2]}
												</td>
											</tr>
											<tr style="height: 25px;">
												<td style="width: 80px; text-align: right;">
													违章行为:
												</td>
												<td
													style="width: 180px; text-align: left; word-wrap: break-word; word-break: break-all">
													${li[3]}
												</td>
											</tr>
										</table>
									</td>
									<td style="border: 1px solid #cccccc; width: 80px">
										${li[4]/1000}
									</td>
									<td style="border: 1px solid #cccccc; width: 80px">
										${li[5]/1000}
									</td>
									<td style="color: #F00; border: 1px solid #cccccc; width: 80px">
										${(li[6]+li[13])/1000}
									</td>
									<td style="color: #f00; border: 1px solid #cccccc; width: 80px">
										${(li[4]+li[5]+li[6]+li[13])/1000}
									</td>
									<td style="border: 1px solid #cccccc; width: 80px">
										${li[9]}
									</td>
									<td style="border: 1px solid #cccccc; width: 80px">
										<c:if test="${li[7]==1 && li[10]==102}">
											是
										</c:if>
										<c:if test="${li[7]!=1 || li[10]!=102}">
											否
										</c:if>
									</td>
								</tr>
							</c:forEach>
						</table>
					</div>

					<div style="border: 1px #09F solid; margin-top: 20px;">
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
							<tr>
								<td class="td_color">
									&nbsp;
									<span id="tss"
										style="display: none; text-align: center; font-size: 18px;">合计：<font
										id="zMoney"> 0 </font> 元</span>
								</td>
								<td style="width: 180px;">
									<a href="javascript:funDown()" id="downId"> <img
											src="<%=path%>/car/images/3.png"> </a>
								</td>
							</tr>
						</table>

					</div>
			</form>
		</div>
	</body>
</html>
