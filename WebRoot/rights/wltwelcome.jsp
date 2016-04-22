
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ page import="java.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<jsp:useBean id="userSession" scope="session" class="com.wlt.webm.rights.form.SysUserForm" />
<jsp:useBean id="notice" scope="page" class="com.wlt.webm.rights.bean.SysNotice"/>
<%@ page import="com.wlt.webm.tool.Tools" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%
  String path = request.getContextPath();
  List list = notice.getSysNoticeListLatest();
  request.setAttribute("noticeList",list);
  String flag=Tools.getPwdflag(userSession.getUserno());
  request.setAttribute("flag",flag);
%>
<html:html>
<head>
<title>万汇通</title>
<link href="css/member.css" rel="stylesheet" type="text/css">
<link href="../css/chongzhi_mobile.css" rel="stylesheet" type="text/css" />
<script src="../js/jquery-1.8.2.min.js" type="text/javascript"></script> 
<script  type="text/javascript" src="../js/util.js"></script>
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
		$("#showmoney").hidden();
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
		
			if(confirm("确认为号码："+ tradeObject.value+" 缴费："+js_id_money_show.innerText+"元")){
			    Button.disabled=true;
				target = "_self";
				
				document.forms[0].action="../business/mobileCharge.do?method=tzFill&money1="+js_id_money_show.innerText;
			    document.getElementById("xx").value="充值中...";
				document.getElementById("xx").disabled="disabled";
			    submit(); 
			}
			document.getElementById("tabbox").focus();
			return ;
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


function fun(type)
	{
		/**
		var arry=document.getElementsByName(type);
		for(var i=0;i<arry.length;i++)
		{
			arry[i].style.display="block";
		}
		document.getElementById(type+"_").style.display="none";
		*/
		window.location.href="<%=path%>/rights/gg.jsp?type="+type;
	}
</script>
</head>
<body  >
<div id="tabbox">
  <div id="tabs_title" style="border:1px solid #cccccc;width:99.5%;float:left;">
    <ul class="tabs_wzdh" >
      <li>快速充值</li></ul>
  </div>
</div>
<div style="border:1px solid #cccccc;width:99.5%;">
<input type="hidden" id="rootPath" value="<%=path %>"/>
<div class="mobile-charge" >
<form action="" method="post" class="form">
<input type="hidden" name="prodId" id="prodId" value=""/>
<input type="hidden" name="telType" id="telType" value=""/>

        <fieldset>
              <div class="form-line" name="dv1" style="position: relative;height:50px;">
            <label class="label" for="mobile-num" style="height:45px;line-height: 45px;">手机号码：</label>
            <div class="element clearfix">
             <div class="mobile-num" style="position:absolute;top:0px;">
				<input oninput="upup();" onpropertychange="upup1();" type="text" id="js_id_mobile" name="tradeObject" style="font-family:'宋体'; font-size:35px;width:280px;height:45px; font-weight:900;border:1px solid gray;" maxlength="13" />
				<span id="showinfo" style="display: none;"><span id="phinfo" style="font-size:20px;"></span></span>  
			</div>
 		</div>

          </div>
          <div class="form-line high-text charge-money" name="dv1" style="position: relative;">
            <label class="label" for="charge-money"  style="height:65px;line-height: 65px;">充值金额：</label>
            <div class="element" style="position:absolute;top:10px;right:0px;">
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
<br/>

          </div>
<c:if test="${flag==1}">
<div class="form-line" name="dv1" style="position: relative;height:50px;">
<label class="label" for="mobile-num" style="height:45px;line-height: 45px;">交易密码：</label>
<span>
<input type="password" id="psd" style="font-family:'宋体'; font-size:35px;width:280px;height:45px; font-weight:900;border:1px solid gray;" maxlength="13" />
</span>
</div>
</c:if>
<div id="caigou" style="display:none;">
<label class="label" for="mobile-num">采购信息：</label>

<span id="dis"><a onclick="chakan();" href="#">点击查看</a></span>
 </div>
<div id="showmoney" style="display: none;"><label class="label" for="mobile-num">采购信息：</label><span id="moninfo" style="font-size:12px;color: #333333;"></span>&nbsp;&nbsp;<a onclick="shouqi();" href="#">点击收起</a></div>


          <div class="form-line" name="dv4">
            <div class="element"  style="display: none">
              <div class="form-font clearfix"><span name="price" class="highlight font-16" id="js_id_money_show">0</span>元</div>
            </div>
<div class="field-item_button_m" id="btnChag"><input type="button" name="Button" value="充值" class="field-item_button" onclick="check();" id="xx" /></div>
</div>
          </div>
        </fieldset>
      </form>
    </div>
</div>


<div class="rcontent" style="background-color:white;width:99.8%;padding:0px 0px 0px 0px;margin-top:10px;border:0px;">
<table style="width:49.5%;float:left;margin-left:0px;">
<thead>
<tr>
<th style="position:relative;"><div style="font-size:15px;font-weight:bold;"><img style="width:20px;height:20px;" alt="消息" src="<%=path %>/images/aaa1.png"/>&nbsp;最新公告</div><a id="A_" style="position:absolute;right:10px;bottom:7px;font-size:13px;" href="javascript:fun('A')"><img style="width:20px;height:20px;" src="<%=path %>/images/add.png"/>更多>></a></th>
</tr>
</thead>
<tbody>
<tr>
<td>
<%
	int conc=0;
 %>
<logic:iterate id="noticeinfo" name="noticeList" indexId="i">
	<c:if test="${noticeinfo[8]==1}">
	<%
			conc++;
			if(conc>=6)
			{
		 %>
		<div class="notice" name="A" style="border:0px;border-bottom:1px solid #cccccc; height:25px;display:none;">
		<%
			}
			else
			{
		 %>
		 <div class="notice"  style="border:0px;border-bottom:1px solid #cccccc; height:25px;">
		 <%
		 }
		  %>
		<div class="left_txt" style="height:23px;position:relative;width:100%">
				<img style="position:absolute;left:3px;bottom:3px;" src="<%=path %>/images/lb.png"/>
				<c:if test="${noticeinfo[9]==1}">
					<div style="height:20px;position:absolute;left:10px;bottom:0px;"><a style="font-size:14px;color:black;" href="<%=path %>/rights/wltnoticeinfo.jsp?uid=${noticeinfo[0]}">${noticeinfo[1]}</a></div>
				</c:if>
				<c:if test="${noticeinfo[9]==0}">
					<div style="height:20px;position:absolute;left:10px;bottom:0px;"><a style="font-size:14px;color:black;" href="<%=path %>/rights/wltnoticeinfo.jsp?uid=${noticeinfo[0]}">
						${noticeinfo[1]}&nbsp;&nbsp;
						<img style="margin-top:6px;" src="<%=path %>/images/add.gif"/></a></div>
				</c:if>
				<div style="height:20px;position:absolute;right:0px;bottom:0px;"><a style="font-size:14px;color:black;" href="<%=path %>/rights/wltnoticeinfo.jsp?uid=${noticeinfo[0]}">${noticeinfo[3]}</a></div>
			</div>
		</div>
	</c:if>
</logic:iterate>
<%
		if(conc<5)
		{
			for(int dd=conc;dd<5;dd++)
			{
				%>
				<div class="notice" name="A" style="border:0px; height:26px;">&nbsp;</div>
				<%
			}
		}
	 %>
</td>
</tr>
</tbody>
</table>

<table style="width:49.5%;float:right;margin-left:0px;">
<thead>
<tr>
<th style="position:relative;"><div  style="font-size:15px;font-weight:bold;"><img style="width:20px;height:20px;" alt="消息"  src="<%=path %>/images/aaa1.png"/>&nbsp;最新活动</div><a id="B_" style="position:absolute;right:10px;bottom:7px;font-size:13px;" href="javascript:fun('B')"><img style="width:20px;height:20px;" src="<%=path %>/images/add.png"/>更多>></a></th>
</tr>
</thead>
<tbody>
<tr>
<td>
<%
	int con=0;
 %>
<logic:iterate id="noticeinfo" name="noticeList" indexId="i">
	<c:if test="${noticeinfo[8]==2}">
		<%
			con++;
			if(con>=6)
			{
		 %>
		<div class="notice" name="B" style="border:0px;border-bottom:1px solid #cccccc; height:25px;display:none;">
		<%
			}
			else
			{
		 %>
		 <div class="notice"  style="border:0px;border-bottom:1px solid #cccccc; height:25px;">
		 <%
		 }
		  %>
			<div class="left_txt" style="height:23px;position:relative;width:100%">
				<img style="position:absolute;left:3px;bottom:3px;" src="<%=path %>/images/lb.png"/>
				<c:if test="${noticeinfo[9]==1}">
					<div style="height:20px;position:absolute;left:10px;bottom:0px;"><a style="font-size:14px;color:black;" href="<%=path %>/rights/wltnoticeinfo.jsp?uid=${noticeinfo[0]}">${noticeinfo[1]}</a></div>
				</c:if>
				<c:if test="${noticeinfo[9]==0}">
					<div style="height:20px;position:absolute;left:10px;bottom:0px;"><a style="font-size:14px;color:black;" href="<%=path %>/rights/wltnoticeinfo.jsp?uid=${noticeinfo[0]}">
						${noticeinfo[1]}&nbsp;&nbsp;
						<img style="margin-top:6px;" src="<%=path %>/images/add.gif"/></a></div>
				</c:if>
				<div style="height:20px;position:absolute;right:0px;bottom:0px;"><a style="font-size:14px;color:black;" href="<%=path %>/rights/wltnoticeinfo.jsp?uid=${noticeinfo[0]}">${noticeinfo[3]}</a></div>
			</div>
		</div>
	</c:if>
</logic:iterate>
	<%
		if(con<5)
		{
			for(int cc=con;cc<5;cc++)
			{
				%>
				<div class="notice" name="B" style="border:0px;height:26px;">&nbsp;</div>
				<%
			}
		}
	 %>
</td>
</tr>
</tbody>
</table>
</body>
</html:html>