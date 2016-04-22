<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">
		<title>违章查询</title>
		<script src="<%=path%>/js/jquery-1.8.2.min.js" type="text/javascript"></script>
		<script type="text/javascript" src="<%=path%>/js/util.js"></script>
		<style>
* {
	margin: 0;
	padding: 0;
	font-size: 14px;
	font-family: "微软雅黑";
}

.wrapper {
	width: 830px;
	height: auto;
	overflow: hidden;
	margin: 0 auto;
}

.h_box {
	height: 40px;
	overflow: hidden;
	border-bottom: 1px #9e9e9e solid;
}

.h_box h4 {
	float: left;
	line-height: 40px;
	font-weight: normal;
	font-size: 24px;
}

.tab_a {
	float: right;
}

.tab_a a {
	width: 98px;
	height: 32px;
	display: inline-block;
	margin-left: 10px;
}

.content {
	margin-top: 20px;
	position: relative;
}

#tab_01 {
	position: relative;
	background: url(<%=path%>/car/images/01.jpg) no-repeat 0 0;
	width: 830px;
	height: 600px;
}

#tab_01 .span1 {
	display: inline-block;
	width: 50px;
	height: 28px;
	background: url(<%=path%>/car/images/span1_bg.png) no-repeat 0 0;
}

#tab_01 .span2 {
	display: inline-block;
	width: 133px;
	height: 28px;
	background: url(<%=path%>/car/images/span2_bg.png) no-repeat 0 0;
	margin-left: 15px;
}

#tab_01 .span3 {
	display: inline-block;
	width: 201px;
	height: 28px;
	background: url(<%=path%>/car/images/span_bg.png) no-repeat 0 0;
}

#tab_01 tr {
	display: block;
	margin: 10px 0;
}

.tj {
	display: block;
	margin: 10px 0 0 150px;
	width: 98px;
	height: 32px;
	background: url(<%=path%>/car/images/nkscx_kscxan.gif) no-repeat 0 0;
}

#tab_01 lable {
	text-align: right;
}

.a1 {
	background: url(<%=path%>/car/images/a01_1.png) no-repeat 0 0;
}

.a2 {
	background: url(<%=path%>/car/images/a02_2.png) no-repeat 0 0;
}

.cur1,.a1:hover {
	background: url(<%=path%>/car/images/a01.png) no-repeat 0 0;
}

.cur2,.a2:hover {
	background: url(<%=path%>/car/images/a02.png) no-repeat 0 0;
}
</style>
<script type="text/javascript">
showMessage("${mess}");
function showImg(){
	var Car_code=$("#code_aa");
	var X = Car_code.position().top;
	var Y = Car_code.position().left;
	document.getElementById("tc_img").style.position="absolute";
	document.getElementById("tc_img").style.left=Y+201+"px";
	document.getElementById("tc_img").style.top=X+"px";
	document.getElementById("tc_img").style.zIndex=1;
	document.getElementById("tc_img").style.display="block";
}
function dis(){
	document.getElementById("tc_img").style.display="none";
}
$(function(){
	$("#tab_02").hide();
	$(".a1").addClass("cur1");
	$(".a1").show();
	
	$(".tab_a a").click(function(){
		var i=$(this).index()+1;
		$(".tab_a a").removeClass("cur1 cur2");
		$(this).addClass("cur"+i);
		$("#tab_01").hide();
		$("#tab_02").hide();
		if(i==1){
			//查询
			$("#tab_01").show();
		}else{
			//处理
			$("#tab_02").show();
			funListLoad(1);
		}
	});
})

//ajax 分页
function funPage(dis){
	var index=document.getElementById("index_hidden").value;
	if(dis=="begin"){
		index=1;
	}else if(dis=="up"){
		index=parseInt(index)-1;
	}else if(dis=="down"){
		index=parseInt(index)+1;
	}else if(dis=="end"){
		index=document.getElementById("lastindex_hidden").value;
	}else if(dis=="href"){
		index=document.getElementById("pageId").value;
	}
	funListLoad(index);
}

//处理列表
function funListLoad(index)
{
	$("#tbody").html("<tr><td style='height:275px;' colspan='8' id='errorMessage'><img src='<%=path%>/images/load.gif'/></td></tr>");
	$.post("<%=path%>/cars.do?method=CarList",
      	{
      		requestType:"1",
      		index:index
      	},
      	function (data){
			document.getElementById("index_hidden").value=data[0].index;
			document.getElementById("pageId").value=data[0].index;
			document.getElementById("lastindex_hidden").value=data[0].lastindex;
			
			data=data[0].arry;
      		if(data==null || data=="" || data=="null"){
      			$("#tbody").html("<tr><td style='height:100px;' colspan='8' id='errorMessage'>暂无数据</td></tr>");
      		}else{
				$("#tbody").html("");
				for(var i=0;i<data.length;i++){
					var st="";
      				if(1==data[i][5]){
						st="下单成功未支付";
					}else if(2==data[i][5]){
						st="已支付";
					}else if(3==data[i][5]){
						st="办理中";
					}else if(4==data[i][5]){
						st="办理成功";
					}else if(5==data[i][5]){
						st="办理失败";
					}else if(6==data[i][5]){
						st="办理异常";
					}else if(7==data[i][5]){
						st="取消";
					}else if(-99==data[i][5]){
						st="删除代办单";
					}else if(100==data[i][5]){
						st="处理中";
					}else if(101==data[i][5]){
						st="已退费";
					}else{
						st="未知状态";
					}
					var css="";
					if((i+1)%2==0){
						css="#F9F2E3";
					}else{
						css="#FBEEC5";
					}
					var strs="<tr onclick=funList('"+data[i][0]+"') style='background-color:"+css+"' onmouseover=this.style.cursor='hand' title='点击加载违章明细' >"+
					"<td style='height:40px;'>"+data[i][0]+"</td>"+
					"<td style='height:40px;'>"+data[i][1]+"</td>"+
					"<td style='height:40px;'>"+data[i][2]+"</td>"+
					"<td style='height:40px;'>"+data[i][3]+"</td>"+
					"<td style='height:40px;'>"+parseFloat(data[i][4]/1000).toFixed(2)+"</td>"+
					"<td style='height:40px;'>"+st+"</td>"+
					"<td style='height:40px;'>"+data[i][6]+"</td>"+
					"</tr>"+
					"<tr><td colspan=7 >"+
					"<table  style='width: 100%; font-size: 12px; border:0;' cellspacing='0' cellpadding='0' id='"+data[i][0]+"' align='center'></table>"+
					"</td></tr>";
					$("#tbody").html($("#tbody").html()+strs);
				}
			}	      		
      		return ;
      	},"json"
	)
}
function funList(idList){
	if($("#"+idList).html()!=""){
		$("#"+idList).html("");
		return ;
	}

	$("#"+idList).html("<tr><td style='height:50px;' colspan='10' align='center'><img src='<%=path%>/images/load.gif'/></td></tr>");
	$.post("<%=path%>/cars.do?method=CarList",{
    		requestType:"2",
    		wzid:idList
    	},
      	function (data){
      		if(data==null || data=="" || data=="null"){
      			$("#"+idList).html("<tr><td style='height:100px;' colspan='10' id='errorMessage'>系统异常</td></tr>");
      		}else{
				$("#"+idList).html("");
				var strs="<tr >"+
					"<td style='height:50px;text-align:center;width:50px;'>&nbsp;</td>"+
					"<td style='height:50px;text-align:center;width:110px;'>违章时间</td>"+
					"<td style='height:50px;text-align:center;width:110px;'>违章地点</td>"+
					"<td style='height:50px;text-align:center;width:80px;'>本金</td>"+
					"<td style='height:50px;text-align:center;width:80px;'>滞纳金</td>"+
					"<td style='height:50px;text-align:center;width:80px;'>代办费</td>"+
					"<td style='height:50px;text-align:center;width:80px;'>扣分</td>"+
					"<td style='height:50px;text-align:center;width:80px;'>状态</td>"+
					"<td style='height:50px;text-align:center;width:80px;'>小计</td>"+
					"</tr>";
				for(var i=0;i<data.length;i++){
					var st="";
      				if(1==data[i][5]){
						st="下单成功未支付";
					}else if(2==data[i][8]){
						st="已支付";
					}else if(3==data[i][8]){
						st="办理中";
					}else if(4==data[i][8]){
						st="办理成功";
					}else if(5==data[i][8]){
						st="办理失败";
					}else if(6==data[i][8]){
						st="办理异常";
					}else if(7==data[i][8]){
						st="取消";
					}else if(-99==data[i][8]){
						st="删除代办单";
					}else if(100==data[i][8]){
						st="处理中";
					}else if(101==data[i][8]){
						st="已退费";
					}else{
						st="未知状态";
					}
					strs=strs+"<tr >"+
					"<td style='height:40px;text-align:center;'>&nbsp;</td>"+
					"<td style='height:40px;text-align:center;'>"+data[i][0]+"</td>"+
					"<td style='height:40px;text-align:center;'>"+data[i][1]+"</td>"+
					"<td style='height:40px;text-align:center;'>"+parseFloat(data[i][3]/1000).toFixed(2)+"</td>"+
					"<td style='height:40px;text-align:center;'>"+parseFloat(data[i][4]/1000).toFixed(2)+"</td>"+
					"<td style='height:40px;text-align:center;'>"+parseFloat((parseFloat(data[i][5])+parseFloat(data[i][6]))/1000).toFixed(2)+"</td>"+
					"<td style='height:40px;text-align:center;'>"+data[i][7]+"</td>"+
					"<td style='height:40px;text-align:center;'>"+st+"</td>"+
					"<td style='height:40px;text-align:center;'>"+parseFloat(data[i][9]/1000).toFixed(2)+"</td>"+
					"</tr>";
				}
				$("#"+idList).html(strs);
			}	      		
      		return ;
      	},"json"
	)
}

//快速查询 验证
function Verification(){
	var car_number=$('input[name="car_number"]');         
	var car_type=$('select[name="car_type"]');
	var car_code=$('input[name="car_code"]');
	var car_engine=$('input[name="car_engine"]');
	var car_username=$('input[name="car_username"]');
	var car_userphone=$('input[name="car_userphone"]');
	if($.trim(car_number.val())==""){
		alert("请输入车牌号!");
		car_number.focus();
		return ;
	}
	if(car_number.val().length<4){
		alert("请输入正确的车牌号!");
		car_number.focus();
		return ;
	}
	
	if($.trim(car_type.val())==""){
		alert("请选择车辆类型!");
		car_type.focus();
		return ;
	}
	
	if($.trim(car_code.val())=="" || car_code.val().length!=6){
		alert("请输入六位车辆识别代号!");
		car_code.focus();
		return ;
	}
	
	if($.trim(car_engine.val())=="" || car_engine.val().length!=4){
		alert("请输入四位发动机号!");
		car_engine.focus();
		return ;
	}
	if($.trim(car_username.val())==""){
		alert("请输入联系人!");
		car_username.focus();
		return ;
	}
	if($.trim(car_userphone.val())=="" || car_userphone.val().length<7){
		alert("请输入正确的联系电话!");
		car_userphone.focus();
		return ;
	}
	var form=$('form[name="for"]');
	form.submit();
	
	$(".tj").css('display','none'); 
	funLoad();
	return ;
}
function funLoad()
{
	if(!document.getElementById("load"))
	{
		var body=document.getElementsByTagName('body')[0];
		var left=(document.body.offsetWidth-350)/2;
		var div="<div id='load' style='border:1px solid black;background-color:white;position:absolute;top:280px;left:"+left+"px;width:350px;height:90px;z-Index:999;text-align:center;font-weight:bold;'><div style='border-bottom:1px solid #cccccc;height:25px;line-height:25px;text-align:left;padding-left:10px;'>温馨提示</div><div style='width:100%;height:65px;line-height:65px;'><img src='<%=path%>/images/load.gif' />&nbsp;数据加载中，请稍后，，，，</div></div>";
		body.innerHTML=body.innerHTML+div;
	}
}
</script>
	</head>
	<body>
		<div class="wrapper">
			<div class="h_box">
				<h4>
					交通罚款
				</h4>
				<div class="tab_a">
					<a href="javascript:;" class="a1"></a>
					<a href="javascript:;" class="a2"></a>
				</div>
			</div>
			<form action="<%=path%>/cars.do?method=CarQuery" method="post"
				name="for">
				<input type="hidden" value="0" name="requestType" />
				<div class="content">
					<div id="tab_01" style="display: ;" class="tab_box">
						<div style="width: 500px; margin: 0 auto">
							<table>
								<tr>
									<td align="right" style="text-align: right; width: 120px;height:40px;">
										车牌号码 ：
									</td>
									<td>
										<span class="span1"> 
											<select name="province_name"
													style="border: none 0; background: none; width: 50px; height: 26px;">
													<option value="粤">
														粤
													</option>
											</select> 
										</span>
										<span class="span2"> 
											<input type="text" value=""
												name="car_number" maxlength=6 
												style="width: 133px; height: 28px; background: none; border: 0 none;"
												onkeyup="value=value.toUpperCase() " /> 
										</span>
										<font style="color:red;padding-left:6px;">请输入车牌号码</font>
									</td>
								</tr>

								<tr>
									<td align="right" style="text-align: right; width: 120px;height:40px;">
										车辆类型 ：
									</td>
									<td>
										<span class="span3"> <select name="car_type"
												style="width: 201px; height: 28px; border: 0 none; background: none;">
												<option value="">
													请选择车牌类型
												</option>
												<option value="02">
													小型汽车
												</option>
												<option value="01">
													大型汽车
												</option>
												<option value="03">
													使馆汽车
												</option>
												<option value="04">
													领馆汽车
												</option>
												<option value="05">
													境外汽车
												</option>
												<option value="06">
													外籍汽车
												</option>
												<option value="07">
													两、三轮摩托车
												</option>
												<option value="08">
													轻便摩托车
												</option>
												<option value="09">
													使馆摩托车
												</option>
												<option value="10">
													领馆摩托车
												</option>
												<option value="11">
													境外摩托车
												</option>
												<option value="12">
													外籍摩托车
												</option>
												<option value="13">
													农用运输车
												</option>
												<option value="14">
													拖拉机
												</option>
												<option value="15">
													挂车
												</option>
												<option value="16">
													教练汽车
												</option>
												<option value="17">
													教练摩托车
												</option>
												<option value="18">
													试验汽车
												</option>
												<option value="19">
													试验摩托车
												</option>
												<option value="20">
													临时入境汽车
												</option>
												<option value="21">
													临时入境摩托车
												</option>
												<option value="22">
													临时行驶车
												</option>
												<option value="23">
													警用汽车
												</option>
												<option value="24">
													警用摩托车
												</option>
												<option value="26">
													香港入出境车
												</option>
												<option value="27">
													澳门入出境车
												</option>
											</select> </span>
									</td>
								</tr>

								<tr>
									<td align="right" style="text-align: right; width: 120px;height:40px;">
										车辆识别代号 ：
									</td>
									<td>
										<span class="span3"> 
											<input type="text" name="car_code" id="code_aa"
												 maxlength="6"
												style="width: 201px; height: 28px; background: none; border: 0 none;"
												onFocus="showImg()" onblur="dis()"> 
										</span>
										<font style="color:red;padding-left:6px;">请输入后6位</font>
									</td>
								</tr>

								<tr>
									<td align="right" style="text-align: right; width: 120px;height:40px;">
										发动机号 ：
									</td>
									<td>
										<span class="span3"> <input type="text"
												name="car_engine"  maxlength="4"
												style="width: 201px; height: 28px; background: none; border: 0 none;" />
										</span>
										<font style="color:red;padding-left:6px;">发动机号后4位</font>
									</td>
								</tr>

								<tr>
									<td align="right" style="text-align: right; width: 120px;height:40px;">
										联系人 ：
									</td>
									<td>
										<span class="span3"> <input type="text"
												name="car_username" maxlength=5 
												style="width: 201px; height: 28px; background: none; border: 0 none;" />
										</span>
										<font style="color:red;padding-left:6px;">请输入姓名</font>
									</td>
								</tr>

								<tr>
									<td align="right" style="text-align: right; width: 120px;height:40px;">
										手机号码 ：
									</td>
									<td>
										<span class="span3"> <input type="text"
												name="car_userphone"  maxlength="11"
												style="width: 201px; height: 28px; background: none; border: 0 none;"
												onkeyup="value=value.replace(/[^\d]/g,'') " /> 
										</span>
										<font style="color:red;padding-left:6px;">请输入手机号码</font>
									</td>
								</tr>

							</table>
							<a href="javascript:Verification()" class="tj" title="快速查询"></a>
						</div>

						<!--弹出层-->
						<div style="display: none;" class="tc_img" id="tc_img">
							<img src="<%=path%>/car/images/n_cjhts_03.jpg" alt="">
						</div>
					</div>


					<!--tab02-->
					<div id="tab_02" style="position: relative; width: 100%">
						<table style="width: 100%; text-align: center;border-radius:10px;border:1px solid #cccccc;"  cellpadding="0" 
							cellspacing="0">
							<tr >
								<td style=" height: 50px;">
									工单ID
								</td>
								<td style="">
									车牌号
								</td>
								<td style="">
									用户姓名
								</td>
								<td style="">
									用户电话
								</td>
								<td style="">
									工单总额(元)
								</td>
								<td style="">
									工单状态
								</td>
								<td style="">
									下单时间
								</td>
							</tr>
							<tbody id="tbody" style="text-align: center; width: 100%;"></tbody>
							<tr>
								<td style="text-align:right;" colspan="7" height=30>
									<div style="width:95%;">
										<a href="javascript:funPage('begin')">首页</a>
										<a href="javascript:funPage('up')">上一页</a>
										<a href="javascript:funPage('down')">下一页</a>
										<a href="javascript:funPage('end')">尾页</a>&nbsp;
										跳转&nbsp;<input id="pageId" type="text" style="width:30px"  value="" onblur="funPage('href')" onkeyup="value=value.replace(/[^\d]/g,'') "/>
									</div>
								</td>
							</tr>
						</table>
					</div>
				</div>
			</form>
		</div>
		<input type="hidden" value="1" id="index_hidden"/>
		<input type="hidden" value="1" id="lastindex_hidden"/>
	</body>
</html>
