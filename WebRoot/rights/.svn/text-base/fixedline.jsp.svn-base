<%@ page contentType="text/html; charset=gb2312" language="java"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:useBean id="userSession" scope="session" class="com.wlt.webm.rights.form.SysUserForm" />
<%@ page import="com.wlt.webm.tool.Tools" %>
<%
String path = request.getContextPath();
String flag=Tools.getPwdflag(userSession.getUserno());
request.setAttribute("flag",flag);
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>全国电信固话充值</title>
<link href="../css/chongzhi_mobile.css" rel="stylesheet" type="text/css" />
<script src="../js/jquery-1.8.2.min.js" type="text/javascript"></script> 
<script  type="text/javascript" src="../js/util.js"></script>
<script language='javascript' src='../js/jquery.js'></script>
<link href="../css/main_style.css" rel="stylesheet" type="text/css" />
<link href="../css/style.css" rel="stylesheet" type="text/css" />
<script language="JavaScript">

$(function(){

	$("#js_id_price").find("a").bind("click",function(){
		var _dv = $(this).attr("data-value");
		$("#js_id_price").find("a").attr("class","charge-money-sum");
		$(this).attr("class","charge-money-sum on");
		$("#js_id_money_show").text(_dv);
	});
})


function funblur(className)
{
	if(className.value.length>=3 && $("#prodId").val()=="")
	{
		$.ajax({
			type:"post",
			url:"<%=path%>/AccountInfo/AccountVerify.do?method=getIdName&sa_zone="+className.value,
			dataType:"json",
			success:function(obj)
			{
				if(obj!=null && obj!="")
				{
					$("#prodId").val(obj[0]);
					$("#phinfo").text(" ( "+obj[1]+" ) ");
				}
				else
				{
					$("#prodId").val("");
					$("#phinfo").text("");
				}
			}
		}); 
	}
}
function upup(event)
{
	if(event.target.value.length>=3)
	{
		$.ajax({
			type:"post",
			url:"<%=path%>/AccountInfo/AccountVerify.do?method=getIdName&sa_zone="+event.target.value,
			dataType:"json",
			success:function(obj)
			{
				if(obj!=null && obj!="")
				{
					$("#prodId").val(obj[0]);
					$("#phinfo").text(" ( "+obj[1]+" ) ");
				}
				else
				{
					$("#prodId").val("");
					$("#phinfo").text("");
				}
			}
		}); 
	}
	else
	{
		$("#prodId").val("");
		$("#phinfo").text("");
	}
}	
function upup1(event)
{
	if(event.propertyName.toLowerCase()=="value"){
		//alert("upup1" + event.srcElement.value);
	}
}

function getmon()
{
	var money=$("#js_id_money_show").text();
	var qnumber=$("#qNumber").val();
	var phonenumber=$("#phoneNumber").val();
	if(money=="" || qnumber=="" || phonenumber=="" || money=="0")
	{
		alert("请输入合法号码并选择充值金额!");
		document.getElementById("cai").style.display="none";
		return ;
	}
	$.post(
	    	"<%=path%>/business/prod.do?method=getMoney&tradeObject="+phonenumber+"&money1="+money,
	      	{
	      		prodId :$("#prodId").val(),
	      		telType:$("#telType").val()
	      	},
	      	function (data){
	      		var mmm;
      			mmm = data[0].mon;
      				$("#hh").html(mmm);
					document.getElementById("caigou").style.display="none";
				 	document.getElementById("cai").style.display="block";
	      			return ;
	      	},"json"
		)
}	
 
function shouqi()
{
	document.getElementById("cai").style.display="none";
	document.getElementById("caigou").style.display="block";
}

function check(className){  
	var money=$("#js_id_money_show").text();
	var qnumber=$("#qNumber").val();
	var phonenumber=$("#phoneNumber").val();
	if(qnumber.length<3)
	{
		alert("区号不合法!");
		return false;
	}
	if(phonenumber.length<7)
	{
		alert("固话号码不合法!");
		return ;
	}
	if(money=="" || money=="0")
	{
		alert("请选择充值金额!");
		return ;
	}
	if($("#prodId").val()=="")
	{
		alert("未知区号!");
		return ;
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
	if(confirm("确认为号码："+qnumber+"-"+phonenumber+" 缴费："+money+"元"))
	{
		document.getElementById("tradeObject").value=qnumber+"-"+phonenumber;
		document.getElementById("money1").value=money;
		document.getElementById("form").action="<%=path%>/business/mobileCharge.do?method=tzFillTwo";
		className.value="充值中,,,";
		className.disabled="true";
		document.getElementById("tabbox").focus();
		document.getElementById("form").submit();
		return ;
	}
	else
	{
		document.getElementById("tabbox").focus();
		return ;
	}
	
}
window.onload=function(){
	document.getElementById("tabbox").focus();
		if("${strA}"==null || "${strA}"=="null" || "${strA}"=="" || "${strA}"==0)
		{
			showMessage("${mess}");
		}
		else
		{
			if(window.confirm("${mess},是否打印票据?"))
			{
				window.location.href="<%=path%>/telcom/ReceiptTwo.jsp?gourl=/rights/fixedline.jsp&str=${strA}";
			}
		}
	funListLoad();
}
function funListLoad()
{
$("#tbody").html("<tr><td style='height:275px;' colspan='8' id='errorMessage'><img  src='<%=path%>/images/load.gif'/></td></tr>");
	$.post(
    	"<%=path%>/business/prod.do?method=getUserAccountList&listtype=4",
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
					var strPut=data[i][7]+";"+data[i][4]+";"+data[i][8]+";电信缴费;"+data[i][0]+";"+parseFloat(data[i][1]).toFixed(2);
					//alert(data[i][0]+"   "+data[i][1]+"   "+data[i][2]+"   "+data[i][3]+"   "+data[i][4]+"   "+data[i][5]+"   "+data[i][6]+"   "+data[i][7]+"   "+data[i][8]);
					var state="";//0、成功，1、失败，2、未响应（计费没有响应），3 未处理（未扣款） 4 处理中（已扣款）5 冲正 6异常订单7已退费 
					var puts="";
					if(data[i][5]=="0"){
						state="成功";
						puts="<a href='javascript:funPut(\""+strPut+"\")'>打印票据</a>";
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
					"<td style='border:1px solid #cccccc;height:25px; font-size: 13px'>"+puts+"</td>"+
					"</tr>");
				}
			}	      		
      		return ;
      	},"json"
	)
}

//列表 打印订单信息小票
function funPut(strPuts)
{
	window.location.href="<%=path%>/telcom/ReceiptTwo.jsp?gourl=/rights/fixedline.jsp&str="+strPuts;
}
</script>
</head>
<body  >
<div id="tabbox"  style="border:1px solid #cccccc;border-bottom:0px;">
  <div id="tabs_title">
    <ul class="tabs_wzdh">
      <li>全国电信固话充值</li></ul>
  </div>
</div>
<div style="border:1px solid #cccccc; ">
<input type="hidden" id="rootPath" value="<%=path %>"/>
<div class="mobile-charge" >
<form id="form" action="" method="post" class="form">
<input type="hidden" name="prodId" id="prodId" value=""/>
<input type="hidden" name="telType" id="telType" value="0"/>


        <fieldset>
              <div class="form-line" name="dv1">
            <label class="label" for="mobile-num" style="height:20px;line-height: 45px;">区号-号码：</label>
            <div class="element clearfix" style="width:400px;float:left;">
             <div class="mobile-num">
<input oninput="upup(event);" onblur="funblur(this)" type="text" id="qNumber"  style="font-family:'宋体'; font-size:35px;width:80px;height:45px; font-weight:900;"  onkeyup="value=value.replace(/[^\d]/g,'') " maxlength="4"/>
-
<input  type="text" id="phoneNumber" style="font-family:'宋体'; font-size:35px;width:170px;height:45px; font-weight:900;" onkeyup="value=value.replace(/[^\d]/g,'') " maxlength="8"/>
<span id="showinfo" ><span id="phinfo" style="font-size:15px;color:red;"></span></span>  
</div>
 </div>

          </div>
          <div class="form-line high-text charge-money" name="dv1">
            <label class="label" for="charge-money"  style="height:40px;line-height: 40px;">充值金额：</label>
            <div class="element"  style="width:500px;float:left;">
              <div id="js_id_price">
					 <a class="charge-money-sum" data-value='10' href="javascript:void(0);" hidefocus="true" data-money="10" data-lidou='10' data-vip='0' data-grow='50'><span>10元</span></a>
					 <a class="charge-money-sum" data-value='20' href="javascript:void(0);" hidefocus="true" data-money="20" data-lidou='20' data-vip='0' data-grow='50'><span>20元</span></a>
 				  <a class="charge-money-sum" data-value='30' href="javascript:void(0);" hidefocus="true" data-money="30" data-lidou='30' data-vip='0' data-grow='50'><span>30元</span></a>
                  <a class="charge-money-sum " data-value='50' href="javascript:void(0);" hidefocus="true" data-money="50" data-lidou='50' data-vip='0' data-grow='50'><span>50元</span></a>
                  <a class="charge-money-sum" data-value='100' href="javascript:void(0);" hidefocus="true" data-money="100" data-lidou='100' data-vip='0' data-grow='50'><span>100元</span></a>
                  <a class="charge-money-sum" data-value='300' href="javascript:void(0);" hidefocus="true" data-money="300" data-lidou='300' data-vip='0' data-grow='50'><span>300元</span></a>
                  <a class="charge-money-sum" data-value='500' href="javascript:void(0);" hidefocus="true" data-money="500" data-lidou='500' data-vip='0' data-grow='50'><span>500元</span></a>
              </div>
            </div>
          </div>
<c:if test="${flag==1}">
<div id="mima" style="padding-bottom: 5px;">
<label class="label" for="mobile-num" style="height:45px;line-height: 45px;">交易密码：</label>
<span><input id="psd" type="password" style="font-family:'宋体'; font-size:35px;width:250px;height:35px; font-weight:900;"/></span>
 </div>
<br/>
</c:if>
<div id="caigou" style="font-size:20px;color: #333333;padding-left:12px;">采购信息：
<a onclick="getmon();" href="#">点击查看</a>
 </div>
<div id="cai" style="display: none;font-size:20px;color: #333333;padding-left:52px;">采购信息：
<span id="hh" style="margin-left: 5px;"></span>&nbsp;&nbsp;<a onclick="shouqi();" href="#">点击收起</a>
</div>

          <div class="form-line" name="dv4">
            <div class="element"  style="display: none">
              <div class="form-font clearfix"><span name="price" class="highlight font-16" id="js_id_money_show">0</span>元</div>
            </div>
			<div class="field-item_button_m" id="btnChag" style="margin-top:0px;height:40px;"><input type="button"  name="Button" value="充值" class="field-item_button" onclick="check(this);" id="xx" /></div>
			</div>
          </div>
        </fieldset>

<input type="hidden" value="" name="tradeObject" id="tradeObject"/>
<input type="hidden" value="" name="money1" id="money1"/>

      </form>
    </div>
</div>
<ul>
	<li style="font-size:15px;text-align:left;font-weight:bold;color:red;padding-left:20px;margin-top:5px;width:95%;">
		当月最近十条交易记录 &nbsp;<a href="javascript:funListLoad();" style="float:right;">刷新列表</a>
	</li>
</ul>
<div style="margin-top:10px;border:1px solid #cccccc;">
	<table>
	 	<thead>
			<tr>
				<th style="width:120px;height:25px;border:1px solid #cccccc;" >序号</th>
				<th style="width:120px;height:25px;border:1px solid #cccccc;">充值号码</th>
				<th style="width:120px;height:25px;border:1px solid #cccccc;">充值金额|扣费金额</th>
				<th style="width:120px;height:25px;border:1px solid #cccccc;">客户参考余额</th>
				<th style="width:120px;height:25px;border:1px solid #cccccc;">充值时间</th>
				<th style="width:120px;height:25px;border:1px solid #cccccc;">充值状态</th>
				<th style="width:120px;height:25px;border:1px solid #cccccc;">凭证打印</th>
			</tr>
		</thead>
		<tbody id="tbody" style="text-align:center;width:100%;">
			<tr><td style="height:275px;" colspan="6" id="errorMessage"><img  src="<%=path%>/images/load.gif"/></td></tr>
		</tbody>
	</table>
</div>
<br/>

<div style="padding-left:40px;padding-top:10px;font-size: 13px; color: #FF6600;font-weight:bold;border:1px solid #FF9E4d;background-color: #FFFFE5;">
<ul>
<li style="list-style:none;">1、提供全国电信固话充值服务。</li>
<li style="list-style:none;">&nbsp;</li>
</ul>
</div>

</body>
</html>
