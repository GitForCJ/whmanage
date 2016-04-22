<%@ page contentType="text/html; charset=UTF-8" language="java"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page import="com.wlt.webm.db.DBConfig" %>
<%@page import="java.util.HashMap" %>
<jsp:useBean id="biprod" scope="session" class="com.wlt.webm.business.bean.BiProd" />
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
HashMap<String,String> maps=biprod.getInfo(2);
String flag=maps.get("jk");
String jiage=maps.get("price");
String ep=DBConfig.getInstance().getLiantong_flow();
request.setAttribute("flag",flag);
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>联通流量充值</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<script src="<%=path %>/js/jquery-1.8.2.min.js" type="text/javascript"></script> 
<link href="<%=path %>/css/main_style.css" rel="stylesheet" type="text/css" />
<style>
*{ margin:0; padding:0;}
.wrapper{
	width:800px;
	overflow:hidden;
	margin-top:20px auto;
	margin-left:30px;
	font-family:"微软雅黑";
	font-size:16px;
	color:#000;
}

.sjhm_box{
	width:600px;
	height:40px;
	overflow:hidden;
	margin:20px 0;
	line-height:40px;
		
}

.sj_txt{
	background:url(<%=path %>/images/01.png) no-repeat 0 0;
	width:250px;
	height:40px;
	display:inline-block;
}

.sj_txt input{
	width:250px;
	height:40px;
	background:none;
	border:none 0;
	margin-left:0px;
}

.ll_box{
	width:500px;
	overflow:hidden;	
}

.ll_box span,.ll_content{ float:left;}

.ll_content{
	width:400px;
	overflow:hidden;	
}

.ll_content span{
	width:60px;
	height:40px;
	text-align:center;
	line-height:40px;
	overflow:hidden;
	margin:4px;
	font-size:14px;
	background:url(<%=path %>/images/02.png) no-repeat 0 0;	
}

.ll_content .cur{background:url(<%=path %>/images/03.png) no-repeat 0 0; font-weight:bold;}

.ll_content span:hover{ background:url(<%=path %>/images/03.png) no-repeat 0 0; font-weight:bold;}

.sj_box{
	width:400px;
	height:40px;
	line-height:40px;
	overflow:hidden;
	margin:20px 0;
}

.jg_txt{
	width:300px;
	font-size:24px;
	color:#ff6600;
	line-height:40px;	
}

.cz_box{
	width:400px;
	overflow:hidden;
	margin:20px 0;	
}

.cz_box a{
	display:inline-block;
	background:url(<%=path %>/images/04.png) no-repeat 0 0;
	width:116px;
	height:38px;
	margin-left:100px;
}

</style>
<script type="text/javascript">
if("${mess}"!=null&&"${mess}"!=""){
alert("${mess}");
}
//$(function()

	function event1(obj){
	$(".ll_content span").click(function(){
		var index=$(this).index();
		$(".ll_content span").removeClass("cur");
		$(this).addClass("cur");
		//$("#jg").text($(this).attr('id'));
		//var a=$(this).attr('id');
		//var b=a.split('#');
	})
	    $(obj).addClass("cur");
		var a=obj.id;
		var b=a.split('#');
		
		//找出具体价格
		var fact='0';
		 var array="<%=jiage%>".split(',')
		 for(var i=0;i<array.length;i++){
		       var array1=array[i].split('|');
		       if((array1[0]+'MB')==obj.innerHTML){
		       fact=array1[1];
		       break;
		       }
		 }
		
		$("#jg").text((parseFloat(fact)*parseFloat("<%=ep%>")).toFixed(2));
		$("#price").val(fact);
		$("#ls").text(fact);
		$("#productID").val(b[0]);
		$("#liu").val(obj.innerHTML);
}

function upup(){
with(document.forms[0]){
if(tradeObject.value==""){
	$("#phinfo").html("");
		$("#showinfo").hide();
		}
		if(tradeObject.value.replace(/[ ]/g,"").length<7){
		$("#phinfo").html("");
		$("#showinfo").hide();
		}else if(tradeObject.value.replace(/[ ]/g,"").length==11){
		getCmprod(tradeObject.value);
		if("<%=flag%>"=='21'){
		getUnicom();
		}
		}
		}
		}
	

	function getCmprod(a){
		$.post(
	    	$("#rootPath").val()+"/business/prod.do?method=getCmprod&tradeObject="+a+"&rand="+Math.random(),
	      	{
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

	//---
		function getUnicom(){
		if($("#js_id_mobile").val()==""||$("#js_id_mobile").val().length!=11){
		return;
		}
		$.post(
	    	$("#rootPath").val()+"/business/prod.do?method=getUnicomFlow&rand="+Math.random(),
	      	{
	      		 tradeObject:$("#js_id_mobile").val(),
	      		 activityId:'2808',
	      		 areaId:$("#prodId").val()
	      	},
	      	function (da){
                    if(da.status=='0'){
	      		    var a=da.data.list;
	      		    var result="";
	      		    $("#unicom").html("");
	      		    for(var i=0;i<a.length;i++){
	      		    $(".ll_content").append("<span onclick='event1(this);' id='"+a[i].PRODUCT_ID+"#"+a[i].PRICE_NUM+"'>"+a[i].PRODUCT_NAME+"</span>");
	      		    }
	      		    //$("#unicom").html(result);
	      		    }else{
	      		    $("#unicom").html("<font color='red'>暂无对应产品信息,错误码("+da.status+")</font>");
	      		    }
	      			return ;
	      	},"json"
		)
	}
//---
var count=0;
	function check(){ 
	if(count==1){
	alert("正在充值请不要重复提交");
	return ;
	} 
		with(document.forms[0]){
		    var ph1=tradeObject.value.substr(0,3);
		    if(!(ph1=='186'||ph1=='130'||ph1=='131'||ph1=='132'||ph1=='145'||ph1=='155'
		    ||ph1=='156'||ph1=='176'||ph1=='185')){
		    alert("请输入联通手机号");
		    return;
		    }
		    if(interfaceid.value=='0'){
		      alert("暂无对应接口信息,请稍候再试");
		      return;
		    }
			if(tradeObject.value.length==0){
				alert("电话号码不能为空");
				return ;
			}
			if(!isDigit(tradeObject.value.replace(/[ ]/g,""))){
				alert("电话号码格式不对");
				return ;
			}
			if(tradeObject.value.replace(" ","").length < 11){
			      alert("手机号码位数一定要等于11位");
			      return ;
			}
			var vall=$("#jg").text();
			if(vall==""|| typeof(vall) == undefined||vall=="0"){
				alert("请选择充值产品额");
				return ;
			}
		
			if(confirm("确认为号码："+ tradeObject.value+"充"+$("#liu").val()+"流量")){
				target = "_self";
				document.getElementById("xx").disabled="disabled";
				count=1;
			    submit(); 
			} 
			return;
		}
	}
//是否是数字格式的字符串
function isDigit(str)
{
    return /^\d+$/.test(str);
}
//点击显示
function djw(){
if($("#dj").text()=='展开'){
$("#dj").text("隐藏");
$("#jg").show();
}else{
$("#dj").text("展开");
$("#jg").hide();
}
}
</script>
  </head>
  
  <body>&nbsp; 
<input type="hidden" id="rootPath" value="<%=path %>"/>
<form action="<%=path %>/business/prod.do?method=fillUnicomFlow" method="post" class="form">
<input type="hidden" name="prodId" id="prodId" value=""/>
<input type="hidden" name="telType" id="telType" value=""/>
<input type="hidden" name="productID" id="productID" value=""/>
<input type="hidden" name="liu" id="liu" value=""/>
<input type="hidden" name="price" id="price" value=""/>
<input type="hidden" name="interfaceid" id="interfaceid" value="<%=flag %>"/>
<div id="tabbox" style="border:1px solid #cccccc;border-bottom:0px;">
  <div id="tabs_title">
    <ul class="tabs_wzdh">
      <li>联通流量充值</li></ul>
  </div>
</div>
<div class="wrapper">
<c:if test="${flag==21}">
	<div class="sjhm_box">
    	<span style="text-align:right; width:90px; display:inline-block;font-size:13px;">手机号：</span>
        <span class="sj_txt"><input oninput="upup();" onpropertychange="upup();"  type="text" maxlength="11" value="" style="font-family:'宋体'; font-size:35px;font-weight:900;" id="js_id_mobile" name="tradeObject"></span>
<span id="showinfo" style="display: none;"><span id="phinfo" style="font-size:20px;"></span></span>  

    </div>
    
    <div class="ll_box">
    	<span style="width:90px; text-align:right; display:inline-block;font-size:12px;">流量产品：</span>
        <div class="ll_content" id="unicom">
            <span >20MB</span>
            <span >50MB</span>
               <span >20MB</span>
            <span >50MB</span>
               <span >20MB</span>
            <span >50MB</span>
        </div>
    </div>
    </c:if>

<c:if test="${flag==23}">
	<div class="sjhm_box">
    	<span style="text-align:right; width:90px; display:inline-block;line-height:50px;font-size:13px;">手机号:</span>
        <span class="sj_txt"><input oninput="upup();" onpropertychange="upup();" type="text" maxlength="11" value="" style="font-family:'宋体'; font-size:35px;font-weight:900;" id="js_id_mobile" name="tradeObject"></span>
		<span id="showinfo" style="display: none;"><span id="phinfo" style="font-size:20px;"></span></span>  
    </div>
    <div class="ll_box">
    	<span style="width:90px; text-align:right; display:inline-block;line-height:50px;font-size:13px;">流量产品:</span>
        <div class="ll_content">
            <span id="G00020#1" onclick='event1(this);'>20MB</span>
            <span id="G00050#2" onclick='event1(this);'>50MB</span>
            <span id="G00100#3" onclick='event1(this);'>100MB</span>
            <span id="G00200#4" onclick='event1(this);'>200MB</span>
            <span id="G00500#5" onclick='event1(this);'>500MB</span>
        </div>
    </div>
    </c:if>
<c:if test="${flag!=0}">
    <div class="sj_box">
    	<span style="width:90px; text-align:right; display:inline-block; line-height:40px;font-size:13px;">零售价:</span>
        <span class="jg_txt">&nbsp;<span id="ls"></span></span>
    </div>
    <div class="sj_box">
    	<span style="width:90px; text-align:right; display:inline-block; line-height:40px;font-size:13px;">采购价:</span>
        <span class="jg_txt">&nbsp;<span id="jg" style="display:none;"></span>&nbsp;&nbsp;<span id="dj" style="color:gray;font-size:15px;" onclick="djw()">展开</span></span>
    </div>
     <div class="cz_box"><a id="xx" href="javascript:check();"  title="立即充值"></a></div>
</c:if>
<c:if test="${flag==0}">
  <div class="sj_box">
    	<span style="width:90px; text-align:right; display:inline-block; line-height:40px;">提示信息:</span>
        <span class="jg_txt">暂无对应接口,请联系客服<span id="jg"></span></span>
    </div>
</c:if>
</div>
</form>
<br/>
<div style="padding-left:40px;padding-top:10px;font-size:16px; color: #FF6600;font-weight:bold;border:1px solid #FF9E4d;background-color: #FFFFE5;">
<ul>
<li style="list-style:none;">1、联通流量充值支持全国漫游。</li>
<li>&nbsp;</li>
<li style="list-style:none;">2、联通优先使用套餐流量，再使用充值流量。</li>
<li>&nbsp;</li>
<li style="list-style:none;">3、同号码同套餐当月只能叠加充值5次；充值时起立即生效，当月月底失效，不自动续订。</li>
<li>&nbsp;</li>
<li style="list-style:none;">4、该业务不支持冲正，号码停机、欠费用户不能充值，流量一旦充值成功不支持退款。</li>
<li>&nbsp;</li>
</ul>
</div>
  </body>
</html>
