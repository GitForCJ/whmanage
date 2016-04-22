<%@ page contentType="text/html; charset=gb2312" language="java"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:useBean id="spa" scope="page" class="com.wlt.webm.business.bean.SysPhoneArea"/>
<%
String path = request.getContextPath();
request.setAttribute("areaList",spa.listArea()); 
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>湖北移动充值</title>
<link href="../css/chongzhi_mobile.css" rel="stylesheet" type="text/css" />
<script src="../js/jquery-1.8.2.min.js" type="text/javascript"></script> 
<script  type="text/javascript" src="../js/util.js"></script>
<script language='javascript' src='../js/jquery.js'></script>
<link href="../css/main_style.css" rel="stylesheet" type="text/css" />
<link href="../css/style.css" rel="stylesheet" type="text/css" />
<script language="JavaScript">
var clipNum = "";
$(function(){
	if($("#mess").val() != ""){
		alert($("#mess").val());
	}
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
	$("#btnBack").click(function(){
		$("div[name='dv1']").show();
    	$("div[name='dv2']").hide();
	});
})
	
	function check(){ 
	var array=["139","138","137","136","135","134","147","150","151","152","157","158","159","182","183","187","188"];
		with(document.forms[0]){
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
			      alert("手机号码位数一定要等于11位");
			       return false;
		    }
		}
		var tel=tradeObject.value.substr(0,3);
			var flag="0";
			for(var i=0;i<array.length;i++){
			if(array[i].indexOf(tel)>-1) {
			flag="1";
			}
			}   
			if(flag=="0"){
				alert("电话号码只能为移动号码");
				tradeObject.focus();
				return false;
			}
          getCmprod();
		//	target = "_self";
	    //	submit();  
		} 
	}
	function btnSubmit(){
	with(document.forms[0]){
		if(confirm("确认为号码："+ tradeObject.value+" 缴费："+js_id_money_show.innerText+"元")){
		  Button.disabled=true;
          target="_self";
          $("#btnChag").hide();
          $("#btnBak").hide();
          $("#bfBox").show();
          document.forms[0].action="../xunjie/xjaction.do?method=xjCharge&xjtype=0&money="+js_id_money_show.innerText;
          submit(); 
      }
      }
	}
	function getFocus(){
		document.forms[0].tradeObject.focus();
	}
	function getCmprod(){
		$.post(
	    	$("#rootPath").val()+"/business/prod.do?method=getCmprod",
	      	{
	      		cm_fee :$("a[class='charge-money-sum on']").attr("data-value"),
	      		phone:$("#js_id_mobile").val()
	      	},
	      	function (data){
	      		var prodId,phoneArea,phoneInfo;
      			prodId = data[0].cmProd;
      			phoneArea = data[0].phoneArea;
      			phoneInfo = data[0].phoneInfo;
	      		//if(prodId == "0"){
	      		//	alert("没有该充值产品");
	      		//	return ;
	      		//}else{
	      			$("#prodId").val(data);
	      			if(phoneArea == "0"){
	      				phoneArea = "未知";
	      			}
	      			if(phoneInfo == "0"){
	      				phoneInfo = "未知";
	      			}
	      			$("#pArea").html(phoneArea);
	      			$("#pInfo").html(phoneInfo);
	      			$("#pNum").html($("#js_id_mobile").val());
	      			$("#t_fee").html($("a[class='charge-money-sum on']").attr("data-value"));
	      			$("div[name='dv1']").hide();
	      			$("div[name='dv2']").show();
	      			return ;
	      	//	}
	      	},"json"
		)
	}
</script>
</head>
<!-- 
<div class="tipBox">
    	<div class="tipBoxW">
        	<div class="tipcon">
           	  <div class="tiptitle">消息提醒</div>
                <div class="tipmain">
                	<p><span><img src="<%=path %>/images/tipyes.gif" width="24" height="24" /></span><label>恭喜您，50元充值成功！</label></p>
                    <p><span><img src="<%=path %>/images/tipno.png" width="24" height="24" /></span><label>充值失败</label></p>
                    <div class="tipbut"><input type="submit" value="确认"  /><input type="submit" value="关闭" /></div>
                </div>
            </div>
        </div>
    </div>
  -->
<body>
<div id="tabbox">
  <div id="tabs_title">
    <ul class="tabs_wzdh">
      <li>湖北移动充值</li></ul>
  </div>
</div>
<input type="hidden" id="rootPath" value="<%=path %>"/>
    <div class="mobile-charge">
<form action="" method="post" class="form">
<input type="hidden" name="prodId" id="prodId"/>
        <fieldset>
          <div class="form-line" name="dv1">
            <label class="label" for="mobile-num">手机号码：</label>
            <div class="element clearfix">
             <div class="mobile-num"><input type="text" id="js_id_mobile" name="tradeObject" maxlength="13" style="font-family:'宋体'; font-size:26px; font-weight:bold;"></div>

            </div>
          </div>
          <div class="form-line high-text charge-money" name="dv1">
            <label class="label" for="charge-money">充值金额：</label>
            <div class="element">
              <div id="js_id_price">
                  <a class="charge-money-sum" data-value='10' href="javascript:void(0);" hidefocus="true" data-money="10" data-lidou='10' data-vip='0' data-grow='50'><span>10元</span></a>
                  <a class="charge-money-sum" data-value='20' href="javascript:void(0);" hidefocus="true" data-money="20" data-lidou='20' data-vip='0' data-grow='50'><span>20元</span></a>
                  <a class="charge-money-sum" data-value='30' href="javascript:void(0);" hidefocus="true" data-money="30" data-lidou='30' data-vip='0' data-grow='50'><span>30元</span></a>
                  <a class="charge-money-sum on" data-value='50' href="javascript:void(0);" hidefocus="true" data-money="50" data-lidou='50' data-vip='0' data-grow='50'><span>50元</span></a>
                  <a class="charge-money-sum" data-value='100' href="javascript:void(0);" hidefocus="true" data-money="100" data-lidou='100' data-vip='0' data-grow='50'><span>100元</span></a>
				  <a class="charge-money-sum" data-value='200' href="javascript:void(0);" hidefocus="true" data-money="200" data-lidou='200' data-vip='0' data-grow='50'><span>200元</span></a>
                  <a class="charge-money-sum" data-value='300' href="javascript:void(0);" hidefocus="true" data-money="300" data-lidou='300' data-vip='0' data-grow='50'><span>300元</span></a>
				  <a class="charge-money-sum" data-value='500' href="javascript:void(0);" hidefocus="true" data-money="500" data-lidou='500' data-vip='0' data-grow='50'><span>500元</span></a>
              </div>
            </div>
          </div>

          <div class="form-line" name="dv1">
            <label class="label">充值金额：</label>
            <div class="element">
              <div class="form-font clearfix"><span name="price" class="highlight font-16" id="js_id_money_show">50</span>元</div>

            </div>
          <div class="field-item_button_m"><input type="button" name="Button" value="下一步" class="field-item_button" onClick="check()"></div>
          </div>
          <div>
          
       <div class="qb_main" style="display:none" name="dv2">
    	 <form id="validator-form">
    	 <div class="field-item"><label>充值号码：</label><span id="pNum"></span></div>
	     <div class="field-item"><label>号码归属地：</label><span id="pArea"></span></div>
         <div class="field-item"><label>号码信息：</label><span id="pInfo"></span></div>
          <div class="field-item"><label>应付金额：</label><span style="font-size:26px; color:#f00;" id="t_fee">29.0</span>&nbsp;&nbsp;&nbsp;&nbsp;元</div>	
<div class="field-item_button_m" id="btnChag"><input type="button" name="Button" value="充值" class="field-item_button" onclick="btnSubmit();"></div>
<div class="field-item_button_m" id="btnBak"><input type="button" name="Button" id="btnBack" value="返回" class="field-item_button"></div>
<div class="field-item_button_m" id="bfBox" style="display:none"><div class="bufferBox">
    	<img class="bufferImg" src="<%=path %>/images/loading_16.gif">
        <label class="bufferText">正在充值，请稍等……</label>
    </div></div>
    </div>
</div>        
        </fieldset>
      </form>
    </div>
    <font style="font-family:'宋体'; font-size:17px; " color="red">欢迎使用湖北移动充值，充值后无法进行冲正，请谨慎操作</font>
</body>
<input type="hidden" id="mess" value="${requestScope.mess }"/>
</html>
