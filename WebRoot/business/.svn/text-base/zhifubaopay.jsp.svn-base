<%@ page contentType="text/html; charset=gb2312" language="java"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="com.wlt.webm.tool.Tools" %>
<jsp:useBean id="Biprod" scope="page"
	class="com.wlt.webm.business.bean.BiProd" />
<jsp:useBean id="userSession" scope="session"
	class="com.wlt.webm.rights.form.SysUserForm" />
<%
	String path = request.getContextPath();
	String[] strs = Biprod.getPlaceMoney("2", userSession.getUserno());
	String flag=Tools.getPwdflag(userSession.getUserno());
	request.setAttribute("flag",flag);
	System.out.println(flag);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>支付宝充值</title>
		<link href="../css/chongzhi_mobile.css" rel="stylesheet"
			type="text/css" />
		<script src="../js/jquery-1.8.2.min.js" type="text/javascript"></script>
		<script type="text/javascript" src="../js/util.js"></script>
		<script language='javascript' src='../js/jquery.js'></script>
		<link href="../css/main_style.css" rel="stylesheet" type="text/css" />
		<link href="../css/style.css" rel="stylesheet" type="text/css" />
		<script language="JavaScript">

showMessage("${mess}");
	function check(){  
			var in_acct=$("#in_acct").val();
			var in_two=$("#in_two").val();
			if(in_acct.length == 0) 
			{
				alert("提醒：支付宝账号不能为空!");
				$("#in_acct").focus();
				return false;
			}
			if(in_acct!=in_two) {
				alert("提醒：两次输入支付宝账号不一致!");
				$("#in_two").focus();
				return false;
			}
			var email=/\w@\w*\.\w/;
			var phone=/^(1[3-9]{1}[0-9]{1})\d{8}$/;
			if(!email.test(in_acct)&&!phone.test(in_acct))
			{
				alert("提醒：请填写正确的支付宝账号!格式为xxx@xx.com或手机号码");
				$("#in_acct").focus();
				return false;
			}
			var order_price=$("#order_price").val();
			if((order_price== ""
					|| order_price== 0)) {
				alert("提醒：请填写金额!");
				$("#order_price").focus();
				return false;
			}
			if (!/^[0-9]*$/.test(order_price)) {
				alert("请输入正确的数字");
				return false;
			}
			if (parseFloat(order_price) - 100 < 0) {
				alert("单次交易金额不能小于100!");
				return false;
			}
			if (parseFloat(order_price) - 1000 > 0) {
				alert("单次交易金额不能大于1000!");
				return false;
			}
			//交易密码
			if(<%=flag%>==1){
		document.getElementById("xx").value="请等待...";
		document.getElementById("xx").disabled="disabled";
		var rs=validateTradePwd($("#psd").val(),<%=flag%>);
		if(rs==1){
			alert("密码错误");
		    document.getElementById("xx").value="充值";
			document.getElementById("xx").disabled=false;;
			return false;
			}
			}
			//交易密码	
			if(confirm("确认为号码："+ in_acct+" 充："+order_price+"元")){
				target = "_self";
				document.forms[0].action="../business/zhiFuBaoCharge.do?method=zhiFuBaoTransfer&in_acct="+in_acct+"&order_price="+order_price;
		   	 	document.getElementById("xx").value="充值中...";
			 	document.getElementById("xx").disabled="disabled";
		    	document.forms[0].submit();  
			} 
	}
	function chakan(){
		var ph=document.getElementById("order_price").value;
		if(ph==""||ph.length==0){
			alert("请输入充值金额");
		}else{
			if (!/^[0-9]*$/.test(ph)) {
				alert("请输入正确的充值金额");
				return;
			}
			var sum=parseInt(ph)+parseFloat(ph*0.01);
			var lirun=parseFloat(ph*0.01);
			document.getElementById("dis").innerHTML=sum+"元&nbsp;&nbsp;<a onclick='shouqi()' href='#'>点击收起</a>";
	
		}
	}
	function shouqi(){
		document.getElementById("dis").innerHTML="<a onclick='chakan()' href='#'>点击查看</a>";
	}
	
	
	window.onload=function()
	{
		funListLoad();
	}
function funListLoad()
{
$("#tbody").html("<tr><td style='height:275px;' colspan='8' id='errorMessage'><img src='<%=path%>/images/load.gif'/></td></tr>");
$.post(
    	"<%=path%>/business/prod.do?method=getUserAccountList&listtype=2",
      	{
      	},
      	function (data){
      		if(data==null || data=="" || data=="null")
      		{
      			$("#tbody").html("<tr><td style='height:100px;' colspan='6' id='errorMessage'>暂无数据</td></tr>");
      		}
			else
			{
				$("#tbody").html("");
				for(var i=0;i<data.length;i++)
				{
					//alert(data[i][0]+"   "+data[i][1]+"   "+data[i][2]+"   "+data[i][3]+"   "+data[i][4]+"   "+data[i][5]+"   "+data[i][6]+"   "+data[i][7]+"   "+data[i][8]);
					var state="";//0、成功，1、失败，2、未响应（计费没有响应），3 未处理（未扣款） 4 处理中（已扣款）5 冲正 6异常订单7已退费 
					if(data[i][5]=="0"){
						state="成功";
					}else if(data[i][5]=="1"){
						state="失败";
					}else if(data[i][5]=="2"){
						state="未响应";
					}else if(data[i][5]=="3"){
						state="未处理";
					}else if(data[i][5]=="4"){
						state="处理中";
					}else if(data[i][5]=="5"){
						state="已冲正";
					}else if(data[i][5]=="6"){
						state="异常订单";
					}else if(data[i][5]=="7"){
						state="已退费";
					}else{
						state="未知状态";
					}
					
					$("#tbody").html($("#tbody").html()+"<tr>"+
					"<td style='border:1px solid #cccccc;height:25px; font-size: 13px'>"+parseInt(i+1)+"</td>"+
					"<td style='border:1px solid #cccccc;height:25px; font-size: 13px'>"+data[i][0]+"</td>"+
					"<td style='border:1px solid #cccccc;height:25px; font-size: 13px'>"+parseFloat(data[i][1]).toFixed(2)+" | "+parseFloat(data[i][2]).toFixed(2)+"</td>"+
					"<td style='border:1px solid #cccccc;height:25px; font-size: 13px'>"+data[i][3]+"</td>"+
					"<td style='border:1px solid #cccccc;height:25px; font-size: 13px'>"+data[i][4]+"</td>"+
					"<td style='border:1px solid #cccccc;height:25px; font-size: 13px'>"+state+"</td>"+
					"</tr>");
				}
			}	      		
      		return ;
      	},"json"
	)
}
function showMess(){
	var order_price=$("#order_price").val();
	if((order_price=="" || order_price== 0)) {
		return ;
	}
	if (!/^[0-9]*$/.test(order_price)) {
		return ;
	}	
	$("#money_show_mess").html("支付宝建议零售价："+parseFloat(order_price*1.03)+"元");
	return;
}
function closeMess(){
	$("#money_show_mess").html("");
}
</script>
	</head>
	<body>
		<div id="tabbox"
			style="border: 1px solid #cccccc; border-bottom: 0px;">
			<div id="tabs_title">
				<ul class="tabs_wzdh">
					<li>
						支付宝充值
					</li>
				</ul>
			</div>
		</div>
		<div style="border: 1px solid #cccccc;">
			<div class="mobile-charge"
				style="margin-top: 20px; margin-left: 0px;">
				<form action="" method="post" class="form">
<input type="hidden" id="rootPath" value="<%=path %>"/>
					<fieldset>
						<div class="pass_list" style="padding-left: 0px;">
							<ul>
								<li
									style="text-align: center; font-size: 15px; font-weight: bold; color: red;">
									支付宝当天限额&nbsp;<%=strs[0]%>元,截止当前时间交易额为&nbsp;<%=strs[1]%>元,今天剩余交易额&nbsp;<%=strs[2]%>元
								</li>
								<li style="font-size: 20px;">
									<strong >支付宝账号：</strong><span><input
											style="font-size: 45px; width: 300px; height: 45px;line-height: 45px;font-weight:900;"
											name="in_acct" id="in_acct" type="text" /> </span>
								</li>
								<li style="font-size: 20px;">
									<strong>确认支付宝账号：</strong><span><input
											style="font-size: 45px; width: 300px; height: 45px;line-height: 45px;font-weight:900;"
											name="in_two" id="in_two" type="text" /> </span>
								</li>
								<li style="font-size: 20px;">
									<strong>充值金额：</strong><span><input id="order_price"
											name="order_price" type="text"
											onkeyup="if(isNaN(value))execCommand('undo')"
											style="font-size: 45px; width: 80px; height: 45px;line-height: 45px;font-weight:900;"
											onafterpaste="if(isNaN(value))execCommand('undo')" onblur="showMess()" onfocus="closeMess()"/> 元 </span>
								</li>
								<li>
									<strong >&nbsp;</strong><span id="money_show_mess" style="font-size: 24px;color:red;"></span>
								</li>
<c:if test="${flag==1}">
<li style="font-size: 20px;">
<strong style="height: 45px;line-height: 45px;">交易密码：</strong>
<span><input id="psd" type="password" style="font-family:'宋体'; font-size:45px;width:280px;height:45px; font-weight:900;"/></span>
</li>
</c:if>

								<li style="font-size: 20px;">
									<div id="caigou">
										<strong>采购信息：</strong>
										<span id="dis"><a onclick="chakan();" href="#">点击查看</a>
										</span>
									</div>
									<div class="element" style="display: none">
										<div class="form-font clearfix">
											<span name="price" class="highlight font-16"
												id="js_id_money_show">0</span>元
										</div>
									</div>

								</li>
							</ul>
						</div>
						<div class="field-item_button_m"
							style="clear: left; margin-left: 180px !   importtant; margin-top: 0px; height: 40px;" />
						<input type="button" name="Button" id="xx" value="充值"
							class="field-item_button" onclick="check();" />
			</div>
			</fieldset>
			</form>
		</div>
		</div>
		<ul>
			<li
				style="font-size: 15px; text-align: left; font-weight: bold; color: red; padding-left: 20px; margin-top: 5px; width: 95%;">
				当月最近十条交易记录 &nbsp;
				<a href="javascript:funListLoad();" style="float: right;">刷新列表</a>
			</li>
		</ul>
		<div style="margin-top: 5px; border: 1px solid #cccccc;">
			<table>
				<thead>
					<tr>
						<th style="width: 150px; height: 25px; border: 1px solid #cccccc;">
							序号
						</th>
						<th style="width: 150px; height: 25px; border: 1px solid #cccccc;">
							充值号码
						</th>
						<th style="width: 150px; height: 25px; border: 1px solid #cccccc;">
							充值金额|扣费金额
						</th>
						<th style="width: 150px; height: 25px; border: 1px solid #cccccc;">
							客户参考余额
						</th>
						<th style="width: 150px; height: 25px; border: 1px solid #cccccc;">
							充值时间
						</th>
						<th style="width: 150px; height: 25px; border: 1px solid #cccccc;">
							充值状态
						</th>
					</tr>
				</thead>
				<tbody id="tbody" style="text-align: center; width: 100%;">
					<tr>
						<td style="height: 275px;" colspan="6" id="errorMessage">
							<img src="<%=path%>/images/load.gif" />
						</td>
					</tr>
				</tbody>
			</table>
		</div>
		<br />
		<div
			style="padding-left: 40px; padding-top: 10px; font-size: 13px; color: #FF6600; font-weight: bold; border: 1px solid #FF9E4d; background-color: #FFFFE5;">
			<ul>
				<li style="list-style: none;">
					1、支持给支付宝直接充值，所有操作不能进行冲正。
				</li>
				<li style="list-style: none;">
					&nbsp;
				</li>
				<li style="list-style: none;">
					2、充值金额最少100元最大1000元。
				</li>
				<li style="list-style: none;">
					&nbsp;
				</li>
				<li style="list-style: none;">
					3、建议网点按照充值金额的1%-2%加收手续费，最低加收1%。
				</li>
				<li style="list-style: none;">
					&nbsp;
				</li>
				<li style="list-style: none;">
					4、所充值支付宝帐号必须通过支付宝实名认证。
				</li>
				<li style="list-style: none;">
					&nbsp;
				</li>
			</ul>
		</div>
	</body>
	<input type="hidden" id="mess" value="${requestScope.mess }" />
</html>
