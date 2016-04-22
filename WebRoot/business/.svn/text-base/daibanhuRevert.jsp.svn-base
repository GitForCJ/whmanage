<%@ page contentType="text/html; charset=gb2312" language="java"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:useBean id="userSession" scope="session" class="com.wlt.webm.rights.form.SysUserForm" />
<jsp:useBean id="biProd" scope="page" class="com.wlt.webm.business.bean.BiProd"/>
<%
  String path = request.getContextPath();
  String str="系统繁忙"; 
  int rs = biProd.getRevertCount(userSession.getUserno()); 
  if(rs>-1){
  	str=rs+"次";
  }
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>交易冲正</title>
<link href="../css/chongzhi_mobile.css" rel="stylesheet" type="text/css" />
<script src="../js/jquery-1.8.2.min.js" type="text/javascript"></script> 
<script  type="text/javascript" src="../js/util.js"></script>
<link href="../css/main_style.css" rel="stylesheet" type="text/css" />
<link href="../css/style.css" rel="stylesheet" type="text/css" />
<script language="JavaScript">
$(function(){
var clipNum = "";
/*
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
	*/

	$("#js_id_price").find("a").bind("click",function(){
		var _dv = $(this).attr("data-value");
		$("#js_id_price").find("a").attr("class","charge-money-sum");
		$(this).attr("class","charge-money-sum on");
		$("#js_id_money_show").text(_dv);
	});
})

showMessage("${mess}");

	function check(flag){
	var a=document.getElementById("ci").value
	if(a<1){
	alert("当月冲正量已用完,不能进行冲正");
	return false;
	}  
		with(document.forms[0]){
			if(tradeObject.value.length==0){
				alert("电话号码不能为空");
				tradeObject.focus();
				return false;
			}
			/*
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
			*/
		if(!isMobileNO(tradeObject.value.replace(/[ ]/g,""))){
			return;
		}
		if(flag==1){
		if(isSelectRole()){
	  			var count = checkedSize(ids);
				if(count>1){
				 	alert("只能选择一条数据");
					return;
				}
			  	}else{
			  	return false;
			  	}
		
			if(confirm("确认为号码："+ tradeObject.value+"进行冲正?")){
		    Button.disabled=true;
			target = "_self";
			
		document.forms[0].action="../business/mobileCharge.do?method=userRevert";
	    document.getElementById("xx").value="冲正中...";
		document.getElementById("xx").disabled="disabled";
	    submit(); 
		} 
		}else{
		document.forms[0].action="../business/mobileCharge.do?method=getReverts";
	    document.getElementById("bt").value="查询中..";
		document.getElementById("bt").disabled="disabled";
			    submit();
		}
		}
	}
	function getFocus(){
		document.forms[0].tradeObject.focus();
	}
	
	
	function isMobileNO(objValue){
	if(typeof(objValue)=="undefined")
		return false;
	if(objValue=="" || objValue==null){ 
		return false;
	} 
	/*
	if(objValue.length!=11){
		alert('请输入11位手机号码！');
		return false;
	}
	*/
	/*
	var myreg = /^(1[3-9]{1}[0-9]{1})\d{8}$/;
	if(!myreg.test(objValue)){
		alert('请输入合法的手机号码帐号！');
		obj.value="";
		obj.focus();
		return false;
	}
	*/
	return true;
}

	function isSelectRole(){
		  with(document.forms[0]){
		var count = checkedSize(ids);
	  	if(count == 0){
	    	alert("请选择一条数据");
			return false;
	  	}
	  	return true;
	  	 }
	}
</script>
</head>
<body  >
<div id="tabbox">
  <div id="tabs_title">
    <ul class="tabs_wzdh">
      <li>交易冲正</li></ul>
  </div>
</div>
<input type="hidden" id="rootPath" value="<%=path %>"/>
<input type="hidden" id="ci" value="<%=rs %>"/>
<div class="mobile-charge" >
<form action="" method="post" class="form">
<input type="hidden" name="prodId" id="prodId" value=""/>
<input type="hidden" name="telType" id="telType" value=""/>

        <fieldset>
              <div class="form-line" name="dv1">
            <label class="label" for="mobile-num">充值号码：</label>
            <div class="element clearfix">
             <div class="mobile-num">
<input type="text" id="js_id_mobile" name="tradeObject" style="font-family:'宋体'; font-size:26px;width:220px; font-weight:bold;"  value="${ph }"/>
<input type="button" onclick="check(0);" name="bt" value="查询" id="bt" style="background:url(../images/button_out.png) no-repeat bottom;color: white;height: 30px;width: 70px;border: none;"/>
</div>
 </div>

          </div>
          <div class="form-line high-text charge-money" name="dv1">
            <label class="label" for="charge-money">当月剩余：</label>
<span id="moninfo" style="font-size:20px;color: red;"><%=str %></span>
            </div>

<c:if test="${!empty revertList}">
<div class="form-line high-text charge-money">
<table id="myTable" cellspacing="0" cellpadding="0">
<thead>
<tr height="25px">
<th>交易号码</th>
<th>交易金额</th>
<th>交易时间</th>
<th>状态</th>
<th>操作</th>
</tr>
</thead>
<tbody id="tab">
<logic:iterate id="rvinfo" name="revertList" indexId="i">
<tr height="25">
<td>${rvinfo[0]}</td>
<td>${rvinfo[1]/1000.0}</td>
<td>${rvinfo[2]}</td>
<td>${rvinfo[3]}</td>
<td><input name="ids" type="checkbox" value="${rvinfo[4]}#${rvinfo[5]}#${rvinfo[1]}" /></td>
</tr>
</logic:iterate>
</tbody>
</table>

</div>
</c:if>

<c:if test="${!empty revertList}">
<div class="field-item_button_m" style="margin-left: 100px;" id="btnChag"><input type="button" name="Button" value="冲正" class="field-item_button" onclick="check(1);" id="xx" /></div>
</c:if>

 </fieldset>
</form>
</div>
<br/>
 
<div style="padding-left:40px;padding-top:10px;margin-left: 20px;font-size: 13px; color: #FF6600;font-weight:bold;border:1px solid #FF9E4d;background-color: #FFFFE5;">
<ul>
<li style="list-style:none;">1、仅支持"全国充值"菜单发起的广东移动、广东联通和"广东电信"菜单发起的充值进行冲正。</li>
<li style="list-style:none;">&nbsp;</li>
<li style="list-style:none;">2、广东电信固话、宽带冲正时，请加区号。</li>
<li style="list-style:none;">&nbsp;</li>
<li style="list-style:none;">3、广东电信预付费号码24小时内冲正有效，后付费号码6小时有效，广东移动和联通号码24小时内有效。</li>
<li style="list-style:none;">&nbsp;</li>
<li style="list-style:none;">&nbsp;</li>
<li style="list-style:none;">4、冲正操作受多种条件限制，不承诺一定成功。</li>
<li style="list-style:none;">&nbsp;</li>
</ul>
</div>
</body>
</html>
