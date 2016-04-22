<%@ page contentType="text/html; charset=gb2312" language="java"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:useBean id="Biprod" scope="page"
	class="com.wlt.webm.business.bean.BiProd" />
<jsp:useBean id="userSession" scope="session"
	class="com.wlt.webm.rights.form.SysUserForm" />
<%@ page import="com.wlt.webm.tool.Tools" %>
<%
	String path = request.getContextPath();
	String[] strs = Biprod.getPlaceMoney("1", userSession.getUserno());
	String flag=Tools.getPwdflag(userSession.getUserno());
	request.setAttribute("flag",flag);
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>Q币充值</title>
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
		with(document.forms[0]){
			if(in_acct.value.length == 0||in_acct.value.length==11) 
		{
			alert("提醒：请填写正确的QQ号码!");
			directFrm.in_acct.focus();
			return false;
		}
		if(in_two.value!=in_acct.value ) {
			alert("提醒：输入QQ号码不一致!");
			in_acct.focus();
			return false;
		}
		
		var temp=document.getElementsByName("qbnum");
		var ok=false;
		var qbNum=order_price.value;
  for (i=0;i<temp.length;i++){
    if(temp[i].checked){
    ok=true;
    qbNum=temp[i].value;
    }
    }
		if((order_price.value == ""
				|| order_price.value == 0)&&ok==false) {
			alert("提醒：请填写Q币数量!");
			directFrm.order_price.focus();
			return false;
		}
				if((order_price.value != ""
				|| order_price.value != 0)&&ok==true) {
			alert("提醒：购买Q币数量只能输入或选择不能同时选择!");
			directFrm.order_price.focus();
			return false;
		}
		
		if (parseFloat(order_price.value) - parseFloat(200) > 0) {
			alert("单次交易数量不能大于200!");
			return false;
		}
		if (!/^[0-9]*$/.test(order_price.value)) {
			alert("请输入正确的数字");
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
		
			if(confirm("确认为号码："+ in_acct.value+" 充："+qbNum+"QB")){
			target = "_self";
			document.forms[0].action="../qb/qbResult.do?method=qbTenpayTransfer&in_acct="+in_acct.value+"&order_price="+order_price.value;
	   	 document.getElementById("xx").value="充值中...";
		 document.getElementById("xx").disabled="disabled";
	    	submit();  
		} }
	}
	function getFocus(){
		document.forms[0].tradeObject.focus();
	}
	
	function panduan() {
	with(document.forms[0]){
	var temp=document.getElementsByName("qbnum");
  for (i=0;i<temp.length;i++){
    if(temp[i].checked){
		if(order_price.value != ""&&order_price.value != 0){
		//temp[i].checked=false;
		//alert("已经输入购买数量,不允许在选择");
		document.getElementById("order_price").value="";
		return ;
		}
		}
		}
		}
	}
	var tempradio= null;
	function checknum(checkedRadio){
	     var num=document.getElementById("order_price").value;
	     if(tempradio== checkedRadio){  
           tempradio.checked=false;  
           tempradio=null;  
          }   
        else{
        tempradio= checkedRadio;    
        }  
	}
	
	function onblue(){
		with(document.forms[0]){
	var temp=document.getElementsByName("qbnum");
  for (i=0;i<temp.length;i++){
    if(temp[i].checked){
		temp[i].checked=false;
		}
	}
	}
	}
	function chakan(){
		var qbnum=$("input[name='qbnum']:checked").val();
		var order_price=document.getElementById("order_price").value;
		var num;
		if(qbnum==null&&(order_price==""||order_price.length==0)){
			alert("请输入或选择Q币个数");
		}else{
			if(qbnum!=null){
				getmoney(qbnum,'0005');
			}else{
				getmoney(order_price,'0005');
			}
		}
	}
	
function getmoney(num,type){//&order_price="+num
		$.post(
	    	$("#rootPath").val()+"/qb/qbResult.do?method=getMoney",
	      	{
	      		price :num,
	      		gametype:type
	      	},
	      	function (data){
	      		var mmm;
      			mmm = data[0].mon;
      			document.getElementById("dis").innerHTML=mmm+",<a onclick='shouqi()' href='#'>点击收起</a>";
	      		return ;
	      	},"json"
		)
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
$("#tbody").html("<tr><td style='height:275px;' colspan='8' id='errorMessage'><img  src='<%=path%>/images/load.gif'/></td></tr>");
$.post(
    	"<%=path%>/business/prod.do?method=getUserAccountList&listtype=3",
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
</script>
	</head>
	<body>
		<div id="tabbox"
			style="border: 1px solid #cccccc; border-bottom: 0px;">
			<div id="tabs_title">
				<ul class="tabs_wzdh">
					<li>
						Q币充值
					</li>
				</ul>
			</div>
		</div>
		<div style="border: 1px solid #cccccc;padding:0px 0px 0px 0px;">
			<input type="hidden" id="rootPath" value="<%=path%>" />
			<div class="mobile-charge" style="margin:0px 0px 0px 0px;margin-top:10px;">
				<form action="" method="post" class="form">
					<input type="hidden" name="prodId" id="prodId" />
					<fieldset>
						<div class="pass_list" style="margin-left: 150;" >
							<ul >
								<li
									style="text-align: center; font-size: 15px; font-weight: bold; color: red;">
									Q币当天限额&nbsp;<%=strs[0]%>元,截止当前时间交易额为&nbsp;<%=strs[1]%>元,今天剩余交易额&nbsp;<%=strs[2]%>元
								</li>
								<li style="font-size: 20px;">
									<strong style="line-height: 45px;">QQ账号：</strong><span><input
											style="font-size: 45px; width: 250px; height: 45px; line-height: 45px;"
											name="in_acct" type="text"
											onkeyup="if(isNaN(value))execCommand('undo')"
											onafterpaste="if(isNaN(value))execCommand('undo')" />
									</span>
								</li>
								<li style="font-size: 20px;">
									<strong style="line-height: 45px;">确认QQ账号：</strong><span><input
											style="font-size: 45px; width: 250px; height: 45px; line-height: 45px;"
											name="in_two" type="text" />
									</span>
								</li>
								<li style="font-size: 20px;">
									<strong style="line-height: 45px;">充值数量：</strong><span><input id="order_price"
											name="order_price" type="text"
											style="font-size: 45px; width: 80px; height: 45px; line-height: 45px;"
											onkeyup="if(isNaN(value))execCommand('undo')"
											onafterpaste="if(isNaN(value))execCommand('undo')"
											onfocus="onblue()" /> 个
								</li>
								<li style="font-size: 20px;">
									<strong style="line-height: 25px;">常用充值数量：</strong>
									<input style="border: 0px; height: 20px; width: 20px;"
										type="radio" name="qbnum" value="5" onclick="panduan();" />
									&nbsp;5个
									<input style="border: 0px; height: 20px; width: 20px;"
										type="radio" name="qbnum" value="10" onclick="panduan();" />
									&nbsp;10个
									<input style="border: 0px; height: 20px; width: 20px;"
										type="radio" name="qbnum" value="20" onclick="panduan();" />
									&nbsp;20个
									<input style="border: 0px; height: 20px; width: 20px;"
										type="radio" name="qbnum" value="30" onclick="panduan();" />
									&nbsp;30个
									<input style="border: 0px; height: 20px; width: 20px;"
										type="radio" name="qbnum" value="50" onclick="panduan();" />
									&nbsp;50个
									<input style="border: 0px; height: 20px; width: 20px;"
										type="radio" name="qbnum" value="100" onclick="panduan();" />
									&nbsp;100个
									</span>
								</li>
<c:if test="${flag==1}">
<li>
<strong style="font-size: 20px;line-height: 45px;">交易密码：</strong>
<span><input id="psd" type="password" style="font-family:'宋体'; font-size:45px;width:250px;height:45px; font-weight:900;"/></span>
</li>
</c:if>
								<li>
									<div id="caigou">
										<strong style="font-size: 20px;line-height: 45px;">采购信息：</strong>
										<span id="dis" style="font-size: 20px;line-height: 45px;"><a onclick="chakan();" href="#" >点击查看</a>
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
							style="clear: left; margin-left: 180px ! importtant; margin-top: 0px; height: 40px;" />
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
					1、支持给QQ号码直接充值，所有操作不能进行冲正。
				</li>
				<li style="list-style: none;">
					&nbsp;
				</li>
				<li style="list-style: none;">
					2、每次充值数量不能大于200，每个QQ号每天累计不超过2000。
				</li>
				<li style="list-style: none;">
					&nbsp;
				</li>
			</ul>
		</div>
	</body>
	<input type="hidden" id="mess" value="${requestScope.mess }" />
</html>
