
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ page import="java.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<jsp:useBean id="userSession" scope="session" class="com.wlt.webm.rights.form.SysUserForm" />
<jsp:useBean id="notice" scope="page" class="com.wlt.webm.rights.bean.SysNotice"/>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%
  String path = request.getContextPath();
  List list = notice.getSysNoticeListLatest();
  request.setAttribute("noticeList",list);
%>
<html:html>
<head>
<title>万汇通</title>
<link href="/css/member.css" rel="stylesheet" type="text/css">
<link href="../css/chongzhi_mobile.css" rel="stylesheet" type="text/css" />
<script src="../js/jquery-1.8.2.min.js" type="text/javascript"></script> 
<script  type="text/javascript" src="../js/util.js"></script>
<script language='javascript' src='../js/jquery.js'></script>
<link href="../css/main_style.css" rel="stylesheet" type="text/css" />
<link href="../css/style.css" rel="stylesheet" type="text/css" />
<style type="text/css">
.rcontent{
	width:100%;
	border:#b1cae8 1px solid;
    margin-bottom: 10px;
    padding: 1px;
    position: relative;
	background:#eef5fd;
	clear:both;
	padding:5px 10px 5px 10px;
	}
.rcontent table{
    border-left: 1px solid #D7D7D7;
    border-top: 1px solid #D7D7D7;
    margin-bottom: 10px;
    width: 100%;
	border-collapse: collapse;
    border-spacing: 0;
}
.rcontent table th{
	background-image:url(../images/head_btn.png);
	background-position:top;
	border-bottom: 1px solid #D7D7D7;
    border-right: 1px solid #D7D7D7;
	padding: 5px;
    text-align: left;
	text-indent:8px;

}
.rcontent table tr td{
	border-bottom: 1px solid #D7D7D7;
    border-right: 1px solid #D7D7D7;
    text-align: left;
	text-indent:13px;
	padding:15px 15px 25px 15px;
	background-color:#FFF;
	}
.rcontent table tr td .tab1{ height:40px; line-height:40px; _height:20px; _line-height:30px;_ margin-top:10px; margin-top:10px; margin-left:0px; }
.rcontent table tr td input{ border-bottom:1px dotted #999;}
.rcontent table tr td .tab1 span{ height:50px; line-height:50px;  color:#999;  _line-height:15px; }
.rcontent table tr td .notice{ height:40px; margin-top:0px; padding-top:0px; }	
.rcontent table tr td .left_txt{ width:70%; height:40px; float:left;  }
.rcontent table tr td .left_tit{ width:70%; height:20px; line-height:20px; font-size:14px; font-weight:700; color:555px;}
.rcontent table tr td .left_con{ width:70%; height:20px; line-height:20px; font-size:12px; color:#666; }
.rcontent table tr td .date{ height:40px; line-height:40px; color:#999; font-size:14px; float:left; text-align:right; width:30%; }
.rcontent table tr td .tab1 .btn3 {
	WIDTH: 200px; DISPLAY: inline-block;  HEIGHT: 35px; COLOR: #fff; FONT-SIZE: 14px;  CURSOR: pointer; FONT-WEIGHT: bold;  background-color:#06F; border:1px solid #03F; line-height:35px; text-align:center; padding:0px; text-indent:0px; _margin-left:8px;}
</style>
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
	showMessage("${mess}");	
	var str="${mess}";		
	if(str.indexOf("充值成功")!=-1){
			$("#caigou").hide();
			$("#showbar").show();
			$("#barinfo").html("<img src='../imgs/zsh/barcode/barcode${barcode}.png'  height=70></img>");
	}
	
});

		

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
	    	$("#rootPath").val()+"/business/prod.do?method=getCmprod&tradeObject="+a,
	      	{
	      		cm_fee :$("a[class='charge-money-sum on']").attr("data-value"),
	      		phone:$("#js_id_mobile").val()
	      	},
	      	function (data){
	      		var pid,phtype,phoneInfo,phoneArea,barcode;
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
	function getbarcode(a){
		
		$.post(
	    	$("#rootPath").val()+"/business/prod.do?method=getCmprod&tradeObject="+a,
	      	{
	      		cm_fee :$("a[class='charge-money-sum on']").attr("data-value"),
	      		phone:$("#js_id_mobile").val()
	      	},
	      	function (data){
	      		barcode =data[0].barcode;
	      			if(barcode!=""){
	      				$("#showbar").show();
	      				$("#barinfo").html("<img src=../imgs/zsh/barcode/barcode"+barcode+".png height=70></img>");
	      					if(barcode==1){
	      				$("#bianma").text("31461");
	      				}else if(barcode==2){
	      				$("#bianma").text("31460");
	      				}else{
	      				$("#bianma").text("37374");
	      				}
	      			}
	      		return ;
	      	},"json"
		)
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
		var vall=$("#js_id_money_show").text();
			if(vall==""|| typeof(vall) == undefined||vall=="0"){
				alert("请选择充值金额及选择充值金额");
				return;
			}
		if(confirm("确认为号码："+ tradeObject.value+" 缴费："+vall+"元")){
		    Button.disabled=true;
			target = "_self";
				
			document.forms[0].action="../business/zshCharge.do?method=tzFill&money1="+vall;
		    document.getElementById("xx").value="充值中...";
			document.getElementById("xx").disabled="disabled";
			document.getElementById("tabbox").focus();
		    submit(); 
			} 
		}
	}
	function getFocus(){
		document.forms[0].tradeObject.focus();
	}
	function chakan(){
		var ph=$("#js_id_mobile").val();
		var qian=js_id_money_show.innerText;
		if(ph.length<13||qian=="0"){
			$("#moninfo").html("");
			$("#showmoney").hide();
			alert("请输入合法手机号码");
		}else{
			$("#caigou").hide();
			getbarcode(ph);
		}
	}
	function shouqi(){
		$("#caigou").show();
		document.getElementById("showbar").style.display="none";
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
    <ul class="tabs_wzdh">
      <li>手机话费充值</li></ul>
  </div>
</div>
<div style="border:1px solid #cccccc;width:99.5%;">
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
                  <a class="charge-money-sum " data-value='50' href="javascript:void(0);" hidefocus="true" data-money="50" data-lidou='50' data-vip='0' data-grow='50'><span>50元</span></a>
            	  <a class="charge-money-sum" data-value='100' href="javascript:void(0);" hidefocus="true" data-money="100" data-lidou='100' data-vip='0' data-grow='100'><span>100元</span></a>
            	  
            	  <a class="charge-money-sum " data-value='300' href="javascript:void(0);" hidefocus="true" data-money="300" data-lidou='300' data-vip='0' data-grow='300'><span>300元</span></a>
            	  <a class="charge-money-sum" data-value='500' href="javascript:void(0);" hidefocus="true" data-money="500" data-lidou='500' data-vip='0' data-grow='500'><span>500元</span></a>
			 </div>
            </div>
          </div>
		<div id="caigou" >
		<label class="label" for="mobile-num">条形码：</label>
			<span id="dis"><a onclick="chakan();" href="#">点击查看</a></span>
 		</div>
		<div id="showbar" style="display: none;">
			<label class="label" for="mobile-num">条形码：</label>
			<span id="barinfo">
			</span>&nbsp;&nbsp;
<ul style="float:left;">
<li><a onclick="shouqi();" href="#" style="font-size: 15px;">点击收起</a></li>
<li>&nbsp;</li>
<li><span id="bianma" style="font-size: 18px;color: red;"></span></li>
</ul>&nbsp;&nbsp;&nbsp;&nbsp;
		</div>
         <div class="form-line" name="dv4">
			<div class="element"  style="display: none">
              <div class="form-font clearfix"><span name="js_id_money_show" class="highlight font-16" id="js_id_money_show">0</span>元</div>
            </div>
			<div class="field-item_button_m" id="btnChag"><input type="button" name="Button" value="充值" class="field-item_button" onclick="check();" id="xx" /></div>
			</div>
          </div>
        </fieldset>
      </form>
    </div>
    </div>


<div class="rcontent" style="background-color:white;border:0px;margin-top:5px;;width:99.8%;padding-left:0px;">
<table style="width:49.8%;float:left;">
<thead>
<tr>
<th style="position:relative;"><div  style="font-size:15px;font-weight:bold;"><img style="width:20px;height:20px;" alt="消息" src="<%=path %>/images/aaa1.png"/>&nbsp;最新公告</div><a id="A_" style="position:absolute;right:10px;bottom:7px;font-size:13px;" href="javascript:fun('A')"><img style="width:20px;height:20px;" src="<%=path %>/images/add.png"/>更多>></a></th>
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

<table style="width:49.8%;float:right;">
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

</div>


</body>
</html:html>