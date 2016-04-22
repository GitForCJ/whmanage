<%@ page contentType="text/html; charset=gb2312" language="java"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
String path = request.getContextPath();
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
showMessage("${mess}");

function upup(){

with(document.forms[0]){
if(tradeObject.value==""){
	$("#phinfo").html("");
		$("#showinfo").hidden();
}
		if(tradeObject.value.replace(/[ ]/g,"").length<7){
		$("#phinfo").html("");
		$("#showinfo").hidden();
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
		$("#showinfo").hidden();
}
		if(tradeObject.value.replace(/[ ]/g,"").length<7){
		a11=0;
		a7=0;
		$("#phinfo").html("");
		$("#showinfo").hidden();
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
	    	$("#rootPath").val()+"/business/prod.do?method=getCmprod&tradeObject="+a,
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
	      			    $("#moninfo").html("");
	      			    $("#moninfo").html(mmm);
	      				$("#showmoney").show();
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
		$("#showmoney").hidden();
		alert("请输入合法手机号码并选择充值金额");
		}else{
		document.getElementById("caigou").style.display="none";
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
		
			if(confirm("确认为号码："+ tradeObject.value+" 缴费："+js_id_money_show.innerText+"元")){
		    Button.disabled=true;
			target = "_self";
			
		document.forms[0].action="../business/mobileCharge.do?method=tzFill&money1="+js_id_money_show.innerText;
	    document.getElementById("xx").value="充值中...";
		document.getElementById("xx").disabled="disabled";
	    submit(); 
		} }
	}
	function getFocus(){
		document.forms[0].tradeObject.focus();
	}
	
	function shouqi(){
	document.getElementById("showmoney").style.display="none";
	document.getElementById("caigou").style.display="block";
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
<div id="tabbox">
  <div id="tabs_title">
    <ul class="tabs_wzdh">
      <li>快速充值</li></ul>
  </div>
</div>
<input type="hidden" id="rootPath" value="<%=path %>"/>
<div class="mobile-charge" >
<form action="" method="post" class="form">
<input type="hidden" name="prodId" id="prodId" value=""/>
<input type="hidden" name="telType" id="telType" value=""/>

        <fieldset>
              <div class="form-line" name="dv1">
            <label class="label" for="mobile-num" style="height:45px;line-height: 45px;">手机号码：</label>
            <div class="element clearfix">
             <div class="mobile-num">
<input oninput="upup();" onpropertychange="upup1();" type="text" id="js_id_mobile" name="tradeObject" style="font-family:'宋体'; font-size:35px;width:280px;height:45px; font-weight:900;" maxlength="13" />
<span id="showinfo" style="display: none;"><span id="phinfo" style="font-size:20px;"></span></span>  
</div>
 </div>

          </div>
          <div class="form-line high-text charge-money" name="dv1">
            <label class="label" for="charge-money"  style="height:45px;line-height: 45px;">充值金额：</label>
            <div class="element">
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
<div id="caigou"  style="display:none;">
	<label class="label" for="mobile-num">采购信息：</label>
	<a onclick="chakan();" href="#">点击查看</a>
</div>
<div id="showmoney" style="display: none;">
	<label class="label" for="mobile-num">采购信息：</label>
	<span id="moninfo" style="font-size:12px;color: #333333;"></span>&nbsp;&nbsp;
	<a onclick="shouqi();" href="#">点击收起</a>
</div>
       <div class="form-line" name="dv4" style="height:40px;">
         <div class="element"  style="display: none">
         	<div class="form-font clearfix"><span name="price" class="highlight font-16" id="js_id_money_show">0</span>元</div>
         </div>
		 <div class="field-item_button_m" id="btnChag" style="margin-top:-10px;"><input type="button" name="Button" value="充值" class="field-item_button" onclick="check();" id="xx" /></div></div>
       </div>
        </fieldset>
      </form>
    </div>
    
    
</body>
</html>
