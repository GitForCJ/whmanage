<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page import="com.wlt.webm.db.DBConfig" %>
<%@page import="java.util.HashMap" %>
<jsp:useBean id="biprod" scope="session" class="com.wlt.webm.business.bean.BiProd" />
<jsp:useBean id="userSession" scope="session" class="com.wlt.webm.rights.form.SysUserForm" />
<%@ page import="com.wlt.webm.tool.Tools" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

String jiage=biprod.getInfo();
String ep2=DBConfig.getInstance().getLiantong_flow();
String ep1=DBConfig.getInstance().getYidong_flow();
String ep0=DBConfig.getInstance().getDianxin_flow();

String flag=Tools.getPwdflag(userSession.getUserno());
request.setAttribute("flag",flag);
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>流量充值</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
<script src="<%=path %>/js/jquery-1.8.2.min.js" type="text/javascript"></script> 
<link href="<%=path%>/css/main_style.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="<%=path%>/js/util.js"></script>
<style type="text/css">
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
//点击事件获取交易金额和接口
	function event1(obj){
	$(".ll_content span").click(function(){
		var index=$(this).index();
		$(".ll_content span").removeClass("cur");
		$(this).addClass("cur");
	})
	    $(obj).addClass("cur");
		var a=obj.id;
		var b=a.split('#');
		
		//找出具体价格
		var fact='0';
		 var array="<%=jiage%>".split(',')
		 for(var i=0;i<array.length;i++){
		       var array1=array[i].split('|');
		       if((array1[0]+'MB')==obj.innerHTML&&array1[2]==$("#telType").val()){
		        fact=array1[1];
				break;
		       }
		 }
		if($("#telType").val()==0){
		$("#jg").text((parseFloat(fact)*parseFloat("<%=ep0%>")).toFixed(2));
		}else if($("#telType").val()==1){
		$("#jg").text((parseFloat(fact)*parseFloat("<%=ep1%>")).toFixed(2));
		}else{
		$("#jg").text((parseFloat(fact)*parseFloat("<%=ep2%>")).toFixed(2));
		}
		$("#price").val(fact);
		$("#ls").text(fact);
		$("#liu").val(obj.innerHTML);
		$("#interfaceid").val(b[0]);
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
		}
		}
		}
	
//获取号码归属地信息
	function getCmprod(a){
		$.post(
	    	$("#rootPath").val()+"/business/prod.do?method=getCmprod&tradeObject="+a,
	      	{
	      		phone:$("#jsmobile").val()
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
	      				getPrice(phtype);
	      			return ;
	      	},"json"
		)
	}
        //根据运营商获取面额和对应接口
	    function getPrice(type){
	    	$.post(
	    	    	$("#rootPath").val()+"/business/prod.do?method=getFaceBytype&rand="+Math.random(),
	    	      	{
	    	      		 phtype:type,
	    	      	},
	    	      	function (da){
	                        if(da!=""&&da!=null){
	    	      		    var result="";
	    	      		    $("#unicom").html("");
	    	      		    for(var i=0;i<da.length;i++){
                            var spanid=da[i][0]+"#"+da[i][1];
	    	      		    $(".ll_content").append("<span onclick='event1(this);' id="+spanid+">"+da[i][1]+"MB</span>");
	    	      		    }
	    	      		    }else{
	    	      		    $("#unicom").html("<font color='red'>暂无对应产品信息</font>");
	    	      		    }
	    	      			return ;
	    	      	},"json"
	    		)
		}

//--确认提交
var count=0;
	function check(){ 
	if(count==1){
	alert("正在充值请不要重复提交");
	return ;
	} 
		with(document.forms[0]){
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
			if(interfaceid.value=='0'){
		      alert("暂无对应接口信息,请稍候再试");
		      return;
		    }
		//交易密码
		if(<%=flag%>==1){
		var rs=validateTradePwd($("#psd").val(),<%=flag%>);
		if(rs==1){
			alert("密码错误");
			return ;
			}
			}
		//交易密码
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
  
<body>
<input type="hidden" id="rootPath" value="<%=path %>"/>
<form action="<%=path %>/business/prod.do?method=fillUnicomFlow" method="post" class="form">
<input type="hidden" name="prodId" id="prodId" value=""/>
<input type="hidden" name="telType" id="telType" value=""/>
<input type="hidden" name="liu" id="liu" value=""/>
<input type="hidden" name="price" id="price" value=""/>
<input type="hidden" name="interfaceid" id="interfaceid" value="0"/>

<div id="tabbox" style="border:1px solid #cccccc;border-bottom:0px;">
  <div id="tabs_title">
    <ul class="tabs_wzdh">
      <li>流量充值</li></ul>
  </div>
</div>
<div class="wrapper">
	<div class="sjhm_box">
    	<span style="text-align:right; width:90px; display:inline-block;font-size:20px;">手机号：</span>
        <span class="sj_txt"><input oninput="upup();" onpropertychange="upup();"   type="text" maxlength="11" value="" style="font-size:35px;width:250px;height:45px;font-weight:900;" id="jsmobile" name="tradeObject" /></span>
<span id="showinfo" style="display: none;"><span id="phinfo" style="font-size:20px;"></span></span>  
    </div>
    
    <div class="ll_box">
    	<span style="width:90px; text-align:right; display:inline-block;font-size:20px;">产品：</span>
        <div class="ll_content" id="unicom">
            <span >20MB</span>
            <span >50MB</span>
            <span >100MB</span>
            <span >200MB</span>
            <span >500MB</span>
        </div>
    </div>
<c:if test="${flag==1}">
<br/>
<div id="mima" class="ll_box">
<span style="text-align:right; width:100px; display:inline-block;font-size:20px;">交易密码：</span>
<span class="sj_txt"><input id="psd" type="password" style="font-family:'宋体'; font-size:45px;width:250px;height:45px; font-weight:900;"/></span>
 </div>
</c:if>

    <div class="sj_box">
    	<span style="width:90px; text-align:right; display:inline-block; line-height:40px;font-size:20px;">零售价:</span>
        <span class="jg_txt">&nbsp;<span id="ls"></span></span>
    </div>
    <div class="sj_box">
    	<span style="width:90px; text-align:right; display:inline-block; line-height:40px;font-size:20px;">采购价:</span>
        <span class="jg_txt">&nbsp;<span id="jg" style="display:none;"></span>&nbsp;&nbsp;<span id="dj" style="color:gray;font-size:20px;" onclick="djw()">展开</span></span>
    </div>
     <div class="cz_box"><a id="xx" href="javascript:check();"  title="立即充值"></a></div>


  <div class="sj_box" style="display:none;">
    	<span style="width:90px; text-align:right; display:inline-block; line-height:40px;">提示信息:</span>
        <span class="jg_txt">暂无对应接口,请联系客服<span id="jg"></span></span>
    </div>

</div>
</form>

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
