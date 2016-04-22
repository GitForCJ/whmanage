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
<title>手机话费充值</title>
<link href="../css/chongzhi_mobile.css" rel="stylesheet" type="text/css" />
<script src="../js/jquery-1.8.2.min.js" type="text/javascript"></script> 
<script  type="text/javascript" src="../js/util.js"></script>
<script language='javascript' src='../js/jquery.js'></script>
<link href="../css/main_style.css" rel="stylesheet" type="text/css" />
<link href="../css/style.css" rel="stylesheet" type="text/css" />
<script language="JavaScript">

window.onload=function(){
	if("${str}"==null || "${str}"=="null" || "${str}"=="" || "${str}"==0)
	{
		showMessage("${mess}");
	}
	else
	{
		if(window.confirm("${mess},是否打印小票?"))
		{
			window.location.href="<%=path%>/business/Receipt.jsp?str=${str}&gourl=/business/cmccbusiness.jsp";
		}
	}
	funListLoad();
}

//列表 打印订单信息小票
function funPut(strPuts)
{
	window.location.href="<%=path%>/business/Receipt.jsp?gourl=/business/cmccbusiness.jsp&str="+strPuts;
}
//列表 交易充值 
function funAccountReverse(orderid,phonenum,money,datetime)
{
	if(confirm("确认为缴费号码:"+ phonenum+",充值金额:"+money+"元,进行冲正?")){
	   document.getElementById(orderid).innerHTML="冲正中,,";
	   document.getElementById(orderid).style.color="#cccccc";
	   $.post
	   (
	    	"<%=path%>/business/mobileCharge.do?method=userAccountListReverse",
	      	{
	      		orderid:orderid,
	      		datetime:datetime
	      	},
	   		function (data)
	   		{
	   			if(data=="-1"){
	   				alert("系统异常,冲正失败");
	   			}else if(data=="-2"){
	   				alert("当月冲正次数已用完,无法冲正");
	   			}else if(data=="-3"){
	   				alert("不存在该笔交易或者已超过24小时");
	   			}else if(data=="-4"){
	   				alert("该号码不支持冲正");
	   			}else if(data=="-5"){
	   				alert("冲正失败");
	   			}else if(data=="-6"){
	   				alert("冲正处理中");
	   			}else if(data=="0"){
	   				alert("冲正成功");
	   			}else if(data=="-9"){
	   				alert("已发起过一笔冲正,请稍后，，，");
	   			}else if(data=="-10"){
	   				alert("已发起冲正，请稍后查看订单状态，，");
	   			}else if(data=="-11"){
	   				alert("请稍后发起冲正!");
	   			}else{
	   				alert("未知状态");
	   			}
	   			funListLoad();
	   		},"json"
      	)
	} 
	document.getElementById("tabbox").focus();
	return;
}

function funListLoad()
{
	$("#tbody").html("<tr><td style='height:275px;' colspan='8' id='errorMessage'><img src='<%=path%>/images/load.gif'/></td></tr>");
	$.post(
    	$("#rootPath").val()+"/business/prod.do?method=getUserAccountList&listtype=0",
      	{
      	},
      	function (data){
      		if(data==null || data=="" || data=="null")
      		{
      			$("#tbody").html("<tr><td style='height:100px;' colspan='8' id='errorMessage'>暂无数据</td></tr>");
      		}
			else
			{
				$("#tbody").html("");
				for(var i=0;i<data.length;i++)
				{
				var strPut=data[i][7]+";"+data[i][4]+";"+data[i][8]+";话费充值;"+data[i][0]+";"+parseFloat(data[i][1]).toFixed(2);
					//alert(data[i][0]+"   "+data[i][1]+"   "+data[i][2]+"   "+data[i][3]+"   "+data[i][4]+"   "+data[i][5]+"   "+data[i][6]+"   "+data[i][7]+"   "+data[i][8]);
					var state="";//0、成功，1、失败，2、未响应（计费没有响应），3 未处理（未扣款） 4 处理中（已扣款）5 冲正 6异常订单7已退费 
					var puts="";
					if(data[i][5]=="0"){
						state="成功";
						puts="<a href='javascript:funPut(\""+strPut+"\")'>打印小票</a>";
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
					
					var cz="不能冲正";
					// 冲正满足条件，广东省，除 已冲正，失败，已退费的订单都可以冲正
					if(data[i][6]=="35" && data[i][5]!="5" && data[i][5]!="7" && data[i][5]!="1" && (data[i][11]=="9" || data[i][11]=="3" || data[i][11]=="4" || data[i][11]=="13" || data[i][11]=="15"|| data[i][11]=="14"|| data[i][11]=="18"|| data[i][11]=="19"|| data[i][11]=="22" || data[i][11]=="24"|| data[i][11]=="28" )) 
					{
						cz="<a href='javascript:funAccountReverse("+data[i][9]+",\""+data[i][0]+"\","+parseFloat(data[i][1]).toFixed(2)+",\""+data[i][10]+"\")'>交易冲正</a>";
					}
					$("#tbody").html($("#tbody").html()+"<tr>"+
					"<td style='border:1px solid #cccccc;height:25px;'>"+parseInt(i+1)+"</td>"+
					"<td style='border:1px solid #cccccc;height:25px;'>"+data[i][0]+"</td>"+
					"<td style='border:1px solid #cccccc;height:25px;'>"+parseFloat(data[i][1]).toFixed(2)+" | "+parseFloat(data[i][2]).toFixed(2)+"</td>"+
					"<td style='border:1px solid #cccccc;height:25px;'>"+data[i][3]+"</td>"+
					"<td style='border:1px solid #cccccc;height:25px;'>"+data[i][4]+"</td>"+
					"<td style='border:1px solid #cccccc;height:25px;'>"+state+"</td>"+
					"<td style='border:1px solid #cccccc;height:25px;'  id="+data[i][9]+">"+cz+"</td>"+
					"<td style='border:1px solid #cccccc;height:25px;'>"+puts+"</td>"+
					"</tr>");
				}
			}	      		
      		return ;
      	},"json"
	)
}

$(function(){
var clipNum = "";
	$("#js_id_mobile").bind("keyup blur",function(){
		if(clipNum != ""){
			if(clipNum.length > 11){
				$(this).val(clipNum.substr(0,11));
			}else{
				$(this).val(clipNum);
			}
		}else{
			$(this).val($(this).val().replace(/\D/g,''));
		}
		clipNum = "";
		var _old = $("#js_id_mobile").val();
		if(_old.length > 3){
			_old = _old.substr(0,3)+" "+_old.substr(3,_old.length);
		}
		if(_old.length > 8){
			_old = _old.substr(0,8)+" "+_old.substr(8,_old.length);
		}
		$("#js_id_mobile").val(_old);
		
	});

	$("#js_id_price").find("a").bind("click",function(){
		var _dv = $(this).attr("data-value");
		$("#js_id_price").find("a").attr("class","charge-money-sum");
		$(this).attr("class","charge-money-sum on");
		$("#js_id_money_show").text(_dv);
	});
})

function upup(){

with(document.forms[0]){
if(tradeObject.value==""){
	$("#phinfo").html("");
		$("#showinfo").hide();
}
		if(tradeObject.value.replace(/[ ]/g,"").length<7){
		$("#phinfo").html("");
		$("#showinfo").hide();
		}else if(tradeObject.value.replace(/[ ]/g,"").length==7||tradeObject.value.replace(/[ ]/g,"").length==11){
		getCmprod(tradeObject.value);
		}
		}
}
var a11=0;
var a7=0;
function upup1(){
if(event.propertyName == 'value'){
with(document.forms[0]){
if(tradeObject.value==""){
	$("#phinfo").html("");
		$("#showinfo").hide();
}
		if(tradeObject.value.replace(/[ ]/g,"").length<7){
		a11=0;
		a7=0;
		$("#phinfo").html("");
		$("#showinfo").hide();
		}else if(tradeObject.value.length==8&&a7==0){
		a7=1;
		getCmprod(tradeObject.value);
		}else if(tradeObject.value.length==13&&a11==0&&a7!=1){
		a11=2;
		getCmprod(tradeObject.value);
		}
		}
}
}

function check1(){
		with(document.forms[0]){
		if(tradeObject.value.length<11){
		return false;
		}
			if(tradeObject.value.replace(/\D/g,'') == ""){
				alert("电话号码不能为空");
				tradeObject.focus();
				return false;
			}
		if(!isDigit(tradeObject.value.replace(/\D/g,''))){
				alert("电话号码格式不对");
				tradeObject.select();
				return false;
			}
		if(tradeObject.value.replace(/\D/g,'').substring(0,1)=='1'){
		   if(tradeObject.value.replace(/\D/g,'').length !=11){
			      alert("手机号码位数必须11位");
			       return false;
		    }
		}
          //
		} 
	}
//===============
	function getCmprod(a){
		$.post(
	    	$("#rootPath").val()+"/business/prod.do?method=getCmprod&tradeObject="+a+"&rand="+Math.random(),
	      	{
	      		cm_fee :$("a[class='charge-money-sum on']").attr("data-value"),
	      		phone:$("#js_id_mobile").val()
	      	},
	      	function (data){
	      		var pid,phtype,phoneInfo,phoneArea;
      			pid = data[0].cmProd;
      			phtype = data[0].phoneType;
      			phoneInfo = data[0].phoneInfo;
      			phoneArea =data[0].phoneArea;
	      			$("#prodId").val(pid);
	      			$("#telType").val(phtype);
	      			    $("#phinfo").html("");
	      			    $("#phinfo").html("( "+phoneInfo+"  "+phoneArea+" )");
	      				$("#showinfo").show();
	      		
	      			return ;
	      	},"json"
		)
	}
//================

//===============
	function getmoney(a){
		$.post(
	    	$("#rootPath").val()+"/business/prod.do?method=getMoney&tradeObject="+a+"&money1="+js_id_money_show.innerText,
	      	{
	      		prodId :$("#prodId").val(),
	      		telType:$("#telType").val()
	      	},
	      	function (data){
	      		var mmm;
      			mmm = data[0].mon;
      			document.getElementById("dis").innerHTML=mmm+",<a onclick='shouqi()' href='#'>点击收起</a>";
	      			  //  $("#moninfo").html("");
	      			   // $("#moninfo").html(mmm);
	      			//	$("#showmoney").show();
	      			return ;
	      	},"json"
		)
	}
//================
function chakan(){
		var ph=document.getElementById("js_id_mobile").value;//$("#js_id_mobile").val();
		
		var qian=js_id_money_show.innerText;
		if(ph.length<13||qian=="0"){
		$("#moninfo").html("");
		$("#showmoney").hide();
		alert("请输入合法手机号码并选择充值金额");
		}else{
		//document.getElementById("caigou").style.display="none";
		getmoney(ph);
		}
}
//========
	function check(){  
		with(document.forms[0]){
			if(tradeObject.value.length==0){
				alert("电话号码不能为空");
				tradeObject.focus();
				return false;
			}
			if(!isDigit(tradeObject.value.replace(/[ ]/g,""))){
				alert("电话号码格式不对");
				tradeObject.select();
				return false;
			}
			if(tradeObject.value.replace(/[ ]/g,"").length < 11){
			       alert("手机号码位数一定要等于11位");
			      return false;
			}
			if(tradeObject.value.replace(/[ ]/g,"").substring(0,1)=='1'){
			   if(tradeObject.value.replace(/[ ]/g,"").length !=11){
			      alert("手机号码位数一定要等于11位");
			       return false;
			    }
			}
		
		if(!isMobileNO(tradeObject.value.replace(/[ ]/g,""))){
			return;
		}
		var vall=js_id_money_show.innerText;
		if(vall==""|| typeof(vall) == undefined||vall=="0"){
		alert("请选择充值金额");
			return;
		}
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
		if(confirm("确认为号码："+ tradeObject.value+" 缴费："+$("#js_id_money_show").text()+"元")){
			    Button.disabled=true;
				target = "_self";
				document.forms[0].action="../business/mobileCharge.do?method=tzFill&money1="+$("#js_id_money_show").text();
			    document.getElementById("xx").value="充值中...";
				document.getElementById("xx").disabled="disabled";
			    submit(); 
			} 
			document.getElementById("tabbox").focus();
			return;
		}
	}
	function getFocus(){
		document.forms[0].tradeObject.focus();
	}
	
	function shouqi(){
		document.getElementById("dis").innerHTML="<a onclick='chakan()' href='#'>点击查看</a>";
	}
	
	function isMobileNO(objValue){
	if(typeof(objValue)=="undefined")
		return false;
	if(objValue=="" || objValue==null){ 
		return false;
	} 
	if(objValue.length!=11){
		alert('请输入11位手机号码！');
		return false;
	}
	var myreg = /^(1[3-9]{1}[0-9]{1})\d{8}$/;
	if(!myreg.test(objValue)){
		alert('请输入合法的手机号码帐号！');
		obj.value="";
		obj.focus();
		return false;
	}
	return true;
}
</script>
</head>
<body  >
<div id="tabbox" style="border:1px solid #cccccc;border-bottom:0px;">
  <div id="tabs_title">
    <ul class="tabs_wzdh">
      <li>手机话费充值</li></ul>
  </div>
</div>
<div style="border:1px solid #cccccc; ">
<input type="hidden" id="rootPath" value="<%=path %>"/>
<div class="mobile-charge" >
<form action="" method="post" class="form">
<input type="hidden" name="prodId" id="prodId" value=""/>
<input type="hidden" name="telType" id="telType" value=""/>

        <fieldset>
              <div class="form-line" name="dv1">
            <label class="label" for="mobile-num" style="height:45px;line-height: 45px;">手机号码：</label>
            <div class="element clearfix" style="width:580px;float:left;">
             <div class="mobile-num" >
<input oninput="upup();" onpropertychange="upup1();" type="text" id="js_id_mobile" name="tradeObject" style="font-family:'宋体'; font-size:35px;width:280px;height:45px; font-weight:900;" maxlength="13" />
<span id="showinfo" style="display: none;"><span id="phinfo" style="font-size:20px;"></span></span>  
</div>
 </div>

          </div>
          <div class="form-line high-text charge-money" name="dv1">
            <label class="label" for="charge-money"  style="height:45px;line-height: 45px;">充值金额：</label>
            <div class="element" style="width:500px;float:left;">
              <div id="js_id_price" >
					 <a class="charge-money-sum" data-value='10' href="javascript:void(0);" hidefocus="true" data-money="10" data-lidou='10' data-vip='0' data-grow='50'><span>10元</span></a>
					 <a class="charge-money-sum" data-value='20' href="javascript:void(0);" hidefocus="true" data-money="20" data-lidou='20' data-vip='0' data-grow='50'><span>20元</span></a>
 				  <a class="charge-money-sum" data-value='30' href="javascript:void(0);" hidefocus="true" data-money="30" data-lidou='30' data-vip='0' data-grow='50'><span>30元</span></a>
                  <a class="charge-money-sum " data-value='49' href="javascript:void(0);" hidefocus="true" data-money="49" data-lidou='49' data-vip='0' data-grow='49'><span>49元</span></a>
                  <a class="charge-money-sum " data-value='50' href="javascript:void(0);" hidefocus="true" data-money="50" data-lidou='50' data-vip='0' data-grow='50'><span>50元</span></a>
                  <a class="charge-money-sum" data-value='100' href="javascript:void(0);" hidefocus="true" data-money="100" data-lidou='100' data-vip='0' data-grow='50'><span>100元</span></a>
                  <a class="charge-money-sum" data-value='300' href="javascript:void(0);" hidefocus="true" data-money="300" data-lidou='300' data-vip='0' data-grow='50'><span>300元</span></a>
                  <a class="charge-money-sum" data-value='500' href="javascript:void(0);" hidefocus="true" data-money="500" data-lidou='500' data-vip='0' data-grow='50'><span>500元</span></a>
              </div>
			<br/>
			
            </div>
          </div>
<c:if test="${flag==1}">
<div id="mima" style="padding-bottom: 5px;">
<label class="label" for="mobile-num" style="height:35px;line-height: 35px;">交易密码：</label>
<span><input id="psd" type="password" style="font-family:'宋体'; font-size:35px;width:280px;height:35px; font-weight:900;"/></span>
 </div>
</c:if>
<div id="caigou" >
<label class="label" for="mobile-num">采购信息：</label>

<span id="dis"><a onclick="chakan();" href="#">点击查看</a></span>
 </div>
<div id="showmoney" style="display: none;"><label class="label" for="mobile-num">采购信息：</label><span id="moninfo" style="font-size:12px;color: #333333;"></span>&nbsp;&nbsp;<a onclick="shouqi();" href="#">点击收起</a></div>
          <div class="form-line" name="dv4">
            <div class="element"  style="display: none">
              <div class="form-font clearfix"><span name="price" class="highlight font-16" id="js_id_money_show">0</span>元</div>
            </div>
			<input type="button" name="Button" value="充值" class="field-item_button" onclick="check();" id="xx" />
			</div>
          </div>
        </fieldset>
      </form>
    </div>
</div>
<ul>
	<li style="font-size:15px;text-align:left;font-weight:bold;color:red;padding-left:20px;margin-top:5px;width:95%;">
		当月最近十条交易记录 &nbsp;<a href="javascript:funListLoad();" style="float:right;">刷新列表</a>
	</li>
</ul>
<div style="margin-top:5px;border:1px solid #cccccc;">
	<table >
	 	<thead>
			<tr>
				<th style="width:120px;height:25px;border:1px solid #cccccc;" >序号</th>
				<th style="width:120px;height:25px;border:1px solid #cccccc;">充值号码</th>
				<th style="width:120px;height:25px;border:1px solid #cccccc;">充值金额|扣费金额</th>
				<th style="width:120px;height:25px;border:1px solid #cccccc;">客户参考余额</th>
				<th style="width:120px;height:25px;border:1px solid #cccccc;">充值时间</th>
				<th style="width:120px;height:25px;border:1px solid #cccccc;">充值状态</th>
				<th style="width:120px;height:25px;border:1px solid #cccccc;">冲正</th>
				<th style="width:120px;height:25px;border:1px solid #cccccc;">凭证打印</th>
			</tr>
		</thead>
		<tbody id="tbody" style="text-align:center;width:100%;">
			<tr><td style="height:275px;" colspan="8" id="errorMessage"><img  src="<%=path%>/images/load.gif"/></td></tr>
		</tbody>
	</table>
</div>
<br/>
<div style="padding-left:40px;padding-top:10px;font-size: 13px; color: #FF6600;font-weight:bold;border:1px solid #FF9E4d;background-color: #FFFFE5;">
<ul>
<li style="list-style:none;">1、提供全国范围三网手机号充值服务，通过本菜单发起的广东移动、广东联通号码充值支持冲正。</li>
<li style="list-style:none;">&nbsp;</li>
<li style="list-style:none;">2、（遇运营商活动等特殊事件期间暂停）冲正操作不承诺一定成功。</li>
<li style="list-style:none;">&nbsp;</li>
</ul>
</div>
</body>
</html>
