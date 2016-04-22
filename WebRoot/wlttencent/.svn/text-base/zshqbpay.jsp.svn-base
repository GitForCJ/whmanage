<%@ page contentType="text/html; charset=gb2312" language="java"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:useBean id="Biprod" scope="page"
	class="com.wlt.webm.business.bean.BiProd" />
<jsp:useBean id="userSession" scope="session"
	class="com.wlt.webm.rights.form.SysUserForm" />
<%
	String path = request.getContextPath();
	String[] strs = Biprod.getPlaceMoney("1", userSession.getUserno());
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
		<style type="text/css">
			.black_overlay{ 
			    display: none; 
			    position: absolute; 
			    top: 0%; 
			    left: 0%; 
			    width: 100%; 
			    height: 100%; 
			    background-color: black; 
			    z-index:1001; 
			    -moz-opacity: 0.8; 
			    opacity:.80; 
			    filter: alpha(opacity=88); 
			} 
			.white_content { 
			    display: none; 
			    z-index:1002; 
			    overflow: auto; 
			} 
		</style>
		<script language="JavaScript">
	$(function(){
		showMessage("${mess}");	
	});
	function check(){  
		var in_acct=document.getElementById("in_acct");
		var in_two=document.getElementById("in_two");
		var qb_InNum=document.getElementById("qb_InNum");
		
		if(in_acct.value.length == 0) 
		{
			alert("提醒：请填写正确的QQ号码!");
			directFrm.in_acct.focus();
			return ;
		}
		if(in_two.value!=in_acct.value ) {
			alert("提醒：输入QQ号码不一致!");
			in_acct.focus();
			return ;
		}
		var con=0;
		if(qb_InNum.value==""){
			var temp=document.getElementsByName("qbnum");
			var bool=false;
			 for (i=0;i<temp.length;i++){
		    	if(temp[i].checked){
					bool=true;
					con=temp[i].value;
					break;
			    }
		 	 }
		 	 if(!bool){
		 	 	alert("请输入或选中Q币充值数量!");
		 	 	qb_InNum.focus();
		 	 	return ;
		 	 }
		}else{
			con=qb_InNum.value;
		}
		if(con<=0){
			alert("请输入正确的充值数量!");
			return ;
		}
		if(con>200){
			alert("单次交易数量不能大于200!");
			return ;
		}
		 document.getElementById("qbNumHidden").value=con;
		 
		if (parseFloat(con) - parseFloat(200) > 0) {
			alert("单次交易数量不能大于200!");
			return ;
		}
		
		$("#showMMImg").html("<img src='<%=path%>/imgs/zsh/barcode/qqbitiaoma.jpg' height=70/>"+39076);
		$("#showMMTow").html("请扫描&nbsp;"+con+"&nbsp;次条码");
		
		document.getElementById('light').style.position="absolute"; 
  		document.getElementById('light').style.left=(document.body.clientWidth-500)/2+"px"
  		document.getElementById('light').style.top="20%";
  		$("#showMM").text("你确认为号码："+ in_acct.value +" 充值："+con+"元");
		document.getElementById('light').style.display='block';
  		document.getElementById('fade').style.display='block';
		return;
		
	}
	
function funClose()
{
	document.getElementById('light').style.display='none';
  	document.getElementById('fade').style.display='none';
}

function funbuttonSubmit()
{
	document.forms[0].target = "_self";
	document.forms[0].action="<%=path%>/business/zshCharge.do?method=qbZhiFuBaoTransfer&in_acct="+document.getElementById("in_acct").value+"&order_price="+document.getElementById("qbNumHidden").value;
	document.getElementById("xx").disabled="disabled";
    document.getElementById("xx").value="充值中...";
    document.forms[0].submit(); 
   	$("#light").text("");
   	document.getElementById("light").style.fontSize="18px";
   	document.getElementById("light").style.textAlign="center";
   	document.getElementById("light").style.height="50px";
   	document.getElementById("light").style.paddingTop="20px";
   	$("#light").html("<img src='<%=path%>/images/load.gif'/>&nbsp;充值中，请稍后，，，");
   	return ;
}


//数量文本框 离开焦点
function funBlur(className){
	if(className.value!=""){
		var temp=document.getElementsByName("qbnum");
		 for (i=0;i<temp.length;i++){
		    if(temp[i].checked){
				temp[i].checked=false;
			}
		 }
	}
}
//单选 选中
function funUp(className){
	if(className.checked){
		document.getElementById("qb_InNum").value="";
	}
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
	<div style="border: 1px solid #cccccc;">
		<div class="mobile-charge" style="margin-left:0px;margin-top:10px;">
			<form action="" method="post" class="form" name="directFrm">
				<input type="hidden" name="prodId" id="prodId" />
				<fieldset>
					<div class="pass_list" style="margin-left: 150;">
						<ul>
							<li style="text-align: center; font-size: 15px; font-weight: bold; color: red;">
								Q币当天限额&nbsp;<%=strs[0]%>元,截止当前时间交易额为&nbsp;<%=strs[1]%>元,今天剩余交易额&nbsp;<%=strs[2]%>元
							</li>
							<li style="font-size: 12px;">
								<strong>QQ账号：</strong><span><input
										style="font-size: 30px; width: 250px; height: 30px;"
										id="in_acct" name="in_acct" type="text"
										onkeyup="if(isNaN(value))execCommand('undo')"
										onafterpaste="if(isNaN(value))execCommand('undo')" />
								</span>
							</li>
							<li style="font-size: 12px;">
								<strong>确认QQ账号：</strong><span>
									<input
										style="font-size: 30px; width: 250px; height: 30px;"
										id="in_two" name="in_two" type="text" />
								</span>
							</li>
							<li style="font-size: 12px;">
								<strong>充值数量：</strong><span>
								<input id="qb_InNum"
										name="qb_InNum" type="text"
										style="font-size: 30px; width: 80px; height: 30px; line-height: 30px;"
										 onkeyup="value=value.replace(/[^\d]/g,'') "
										 onblur="funBlur(this);"/> 个
							</li>
							<li style="font-size: 12px;">
								<strong>常用充值数量：</strong>
								<span>
									<input style="border: 0px; height: 20px; width: 20px;"
										type="radio" name="qbnum" value="5" onclick="funUp(this)" />
									5个&nbsp;
									<input style="border: 0px; height: 20px; width: 20px;"
										type="radio" name="qbnum" value="10" onclick="funUp(this)"  />
									10个&nbsp;
									<input style="border: 0px; height: 20px; width: 20px;"
										type="radio" name="qbnum" value="20" onclick="funUp(this)"  />
									20个&nbsp;
									<input style="border: 0px; height: 20px; width: 20px;"
										type="radio" name="qbnum" value="30" onclick="funUp(this)"  />
									30个&nbsp;
									<input style="border: 0px; height: 20px; width: 20px;"
										type="radio" name="qbnum" value="50" onclick="funUp(this)"  />
									50个&nbsp;
									<input style="border: 0px; height: 20px; width: 20px;"
										type="radio" name="qbnum" value="100" onclick="funUp(this)"  />
									100个&nbsp;
									<input style="border: 0px; height: 20px; width: 20px;"
										type="radio" name="qbnum" value="200" onclick="funUp(this)"  />
									200个&nbsp;
								</span>
							</li>
							<li style="font-size: 12px;">
								<strong>&nbsp;</strong><span>
								<input onclick="check()" type="button" id="xx"  style="width:85px; height:32px; border:0px;color:#fff; font-weight:bold;background:url(<%=path%>/images/button_out.png) no-repeat bottom; cursor:pointer;" value="充值"/>
							</li>
						</ul>
					</div>
					<br/><br/>
					<input type="hidden" value="" id="qbNumHidden"/>
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
<div  id="light" class="white_content" style="background-color:white;height:250px;width:500px;border-radius:10px;">
<div style="width: auto;height:99%;">
<table  style="height:98%;width:100%;">
	<thead  style="height:30px;">
		<tr >
			<th style="text-align:left;font-size:14px;padding-left:20px;">
				温馨提示
			</th>
		</tr>
	</thead>
	<tbody >
		<tr>
			<td id="showMM" style="color:red;font-size:18px;font-weight:bold;text-align:center;border-bottom:1px solid #cccccc;height:40px;line-height:40px;"></td>
		</tr>
		<tr>
			<td id="showMMImg" style="color:red;font-size:18px;font-weight:bold;text-align:left;padding-left:15px;border-bottom:1px solid #cccccc;height:80px;line-height:80px;padding-top:10px;">
			</td>
		</tr>
		<tr>
			<td id="showMMTow" style="color:red;font-size:18px;font-weight:bold;text-align:left;border-bottom:1px solid #cccccc;height:40px;line-height:40px;"></td>
		</tr>
		<tr>
			<td align="center" >
				<div style="float:right;margin-top:8px;">
					 <input type="button" id="wltfarmebuttonvalue" name="Button" value="&nbsp;确&nbsp;定&nbsp;" style="border:0px; width:85px; height:32px; color:#fff; font-weight:bold; background:url(../images/button_out.png) no-repeat bottom; cursor:pointer;" onclick="funbuttonSubmit()"/>
				&nbsp;
					<input type="button" name="Button" value="&nbsp;取&nbsp;消&nbsp;" style="border:0px; width:85px; height:32px; color:#fff; font-weight:bold; background:url(../images/button_out.png) no-repeat bottom; cursor:pointer;" onclick="funClose()"/>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				</div>
			</td>
		</tr>
	</tbody>
</table>
</div>
</div>
<div id="fade" class="black_overlay"></div> 
<input type="hidden" id="mess" value="${requestScope.mess }" />
</body>
</html>
