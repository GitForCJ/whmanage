<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>交通违章</title>
    <script src="<%=path%>/js/jquery-1.8.2.min.js" type="text/javascript"></script> 
    <script type="text/javascript">
    if("${mess}"!="" && "${mess}"!=null && "${mess}"!="null")
    {
    	alert("${mess}");
    }
    function funLoad()
	{
		if(!document.getElementById("load"))
		{
			var body=document.getElementsByTagName('body')[0];
			var left=(document.body.offsetWidth-350)/2;
			var div="<div id='load' style='border:1px solid black;background-color:white;position:absolute;top:180px;left:"+left+"px;width:350px;height:90px;z-Index:999;text-align:center;font-weight:bold;'><div style='border-bottom:1px solid #cccccc;height:25px;line-height:25px;text-align:left;padding-left:10px;font-size:13px;'>温馨提示</div><div style='width:100%;height:65px;line-height:65px;font-size:13px;'><img src='<%=path%>/images/load.gif' />&nbsp;数据加载中，请稍后，，，，</div></div>";
			body.innerHTML=body.innerHTML+div;
		}
	}
	function funCarType(className){
		document.getElementById("dianxin").style.display="none";
		document.getElementById("baishibang").style.display="none";
		document.getElementById("f_table").style.display="none";
		className.name="plateNumberType";
		document.getElementById("b_name").style.display="none";
		document.getElementById("b_phone").style.display="none";
		document.getElementById("b_address").style.display="none";
		document.getElementById("car_liu").innerHTML="车架号后六位";
		document.getElementById("car_si").innerHTML="发动机号后四位";
		document.getElementById("s_interface").value="";
		if(className.value==-1 || className.value==99){ //非小型车 or  小型车
			var cattype=0;
			if(className.value==99){
				cattype=1;//小型车1
			}else{
				cattype=2;//非小型车2
			}
			$.post(
		    	"<%=path %>/business/mobileCharge.do?method=getCarInterface",
		      	{
		      		cattype:cattype, //非小型车2 or 小型车1
		      		operationType:1 //查询1
		      	},
		      	function (data){
		      		if(data!=null && data!=""){
		      			//小型车1
		      			if(className.value==99){
		      				if(data==1){  //电信1 
		      					document.getElementById("s_interface").value="1";
		      				}
		      				if(data==2){ // 白世邦2
		      					document.getElementById("b_name").style.display="";
								document.getElementById("b_phone").style.display="";
								document.getElementById("b_address").style.display="";
								document.getElementById("car_liu").innerHTML="车架号后六位";
								document.getElementById("car_si").innerHTML="发动机号后四位";
								document.getElementById("s_interface").value="2";
		      				}
		      				return;
		      			}
		      			//非小型车
		      			document.getElementById("f_table").style.display="";
		      			if(data==1){  //电信1 
		      				className.name="";
		      				document.getElementById("dianxin").style.display="";
		      				document.getElementById("s_interface").value="1";
		      			}
		      			if(data==2){ // 白世邦2
		      				className.name="";
		      				document.getElementById("baishibang").style.display="";
		      				document.getElementById("b_name").style.display="";
							document.getElementById("b_phone").style.display="";
							document.getElementById("b_address").style.display="";
							document.getElementById("car_liu").innerHTML="车架号后六位";
							document.getElementById("car_si").innerHTML="发动机号后四位";
							document.getElementById("s_interface").value="2";
		      			}
		      		}else{
		      			alert("未查询到对应接口!");
		      			return ;
		      		}
		      	}
			)
		}
	}
	
    	
    	function funget()
    	{
    		var a=document.getElementById("plateNumberA").value;
    		var b=document.getElementById("plateNumberB").value;
    		var c=document.getElementById("plateNumberType").value;
    		var d=document.getElementById("carFrameNum").value;
    		var e=document.getElementById("engineNumber").value;
    		
    		if(b.Trim()==""){
    			alert("请输入车牌号!");
    			return;
    		}
    		if(c.Trim()==""){
    			alert("请选择车辆类型！");
    			return ;
    		}
    		if(d.Trim()==""){
    			alert("请输入车架号!");
    			return ;
    		}
    		if(d.Trim().length!=6){
    			alert("请输入正确的六位车架号!");
    			return ;
    		}
    		if(e.Trim()==""){
    			alert("请输入发动机号!");
    			return ;
    		}
    		if(e.Trim().length!=4){
    			alert("请输入正确的4位发动机号!");
    			return ;
    		}
    		var inter=document.getElementById("s_interface").value;
    		if(inter==2){
    			var catUserName=document.getElementById("catUserName").value;
    			var catUserPhone=document.getElementById("catUserPhone").value;
    			if(catUserName.Trim()==""){
    				alert("请输入车主姓名!");
    				return ;
    			}
    			if(catUserPhone.Trim()==""){
    				alert("请输入联系电话!");
    				return ;
    			}
    			if(catUserPhone.Trim().length!=11){
    				alert("请输入正确的手机号码!");
    				return ;
    			}
    		}
    		 var selectInput = document.getElementById("plateNumberType")  
    		document.getElementById("s_carNameText").value=selectInput.options[selectInput.selectedIndex].text;
    		
    		document.getElementById("for").action="<%=path %>/business/mobileCharge.do?method=catMessage&plateNumberA="+document.getElementById("plateNumberA").value;
    		document.getElementById("for").submit();
    		funLoad();
    		return ;
    	}
    	// 去空格
		String.prototype.Trim = function() 
		{ 
			return this.replace(/(^\s*)|(\s*$)/g, ""); 
		} 
    </script>
  </head>
  
  <body style="margin-left:0px;margin-right:2px;margin-top:0px;">
	  <form action="<%=path %>/business/mobileCharge.do?method=catMessage" method="post" id="for" style="border:1px solid #cccccc;margin:0px 0px 0px 0px;">
	  	<div style="width:100%;margin:0px 0px 0px 0px;">
	  		<div style="width:100%;padding-top:20px;">
	  			<table style="width:100%;font-size:13px;font-weight:bold;" border=0 cellpadding="8"  cellspacing="0">
					<tr style="height:30px;">
						<td style="width:30%;text-align:right;">车牌号:</td>
						<td style="padding-left:5px;position:relative;">
							<select style="height:30px;" id="plateNumberA" name="plateNumberA">
								<option value="粤">粤</option>
							</select>
							<input type="text" value="" style="width:145px;height:30px;font-size:20px;" id="plateNumberB" name="plateNumberB" onkeyup="value=value.toUpperCase() "/>
						</td>
					</tr>
					<tr style="height:30px;">
						<td style="width:30%;text-align:right;">车辆类型:</td>
						<td style="padding-left:5px;">
							<select style="height:30px;width:190px;" id="plateNumberType" name="plateNumberType" onchange="funCarType(this)">
								<option value="" >-- 请选择 --</option>
								<option value="99">小型车蓝牌车</option>
								<option value="-1">非小型车</option>
							</select>
						</td>
					</tr>
					<tr style="height:30px;display:none;" id="f_table">
						<td style="width:30%;text-align:right;">非小型车类型:</td>
						<td style="padding-left:5px;">
							<select style="height:30px;width:190px;display:none;" id="dianxin" name="plateNumberType">
								<option value="2">大型车黄牌车</option>
								<option value="3">挂车</option>
								<option value="5">外籍车黑牌汽车</option>
							</select>
							<select style="height:30px;width:190px;display:none;" id="baishibang" name="plateNumberType">
								<option value="01">大型汽车</option>
								<option value="03">使馆汽车</option>
								<option value="04">领馆汽车</option>
								<option value="05">境外汽车</option>
								<option value="06">外籍汽车</option>
								<option value="07">两、三轮摩托车</option>
								<option value="08">轻便摩托车</option>
								<option value="09">使馆摩托车</option>
								<option value="10">领馆摩托车</option>
								<option value="11">境外摩托车</option>
								<option value="12">外籍摩托车</option>
								<option value="13">农用运输车</option>
								<option value="14">拖拉机</option>
								<option value="15">挂车</option>
								<option value="16">教练汽车</option>
								<option value="17">教练摩托车</option>
								<option value="18">试验汽车</option>
								<option value="19">试验摩托车</option>
								<option value="20">临时入境汽车 </option>
								<option value="21">临时入境摩托车</option>
								<option value="22">临时行驶车</option>
								<option value="23">警用汽车</option>
								<option value="24">警用摩托车</option>
								<option value="26">香港入出境车</option>
								<option value="27">澳门入出境车</option>
							</select>
						</td>
					</tr>
					<tr style="height:30px;">
						<td style="width:30%;text-align:right;">车架号:</td>
						<td style="padding-left:5px;">
							<input type="text" value="" style="width:190px;height:30px;font-size:20px;" maxlength=6 id="carFrameNum" name="carFrameNum"/>
							<font style="font-size:14px;" color=red id="car_liu">车架号后六位</font>
						</td>
					</tr>
					<tr style="height:30px;">
						<td style="width:30%;text-align:right;">发动机号:</td>
						<td style="padding-left:5px;">
							<input type="text" value="" style="width:190px;height:30px;font-size:20px;" maxlength=4 id="engineNumber" name="engineNumber"/> 
							<font style="font-size:14px;" color=red id="car_si">发动机号后四位</font>
						</td>
					</tr>
					
					<tr style="height:30px;display:none;" id="b_name">
						<td style="width:30%;text-align:right;">车主姓名:</td>
						<td style="padding-left:5px;">
							<input type="text" value="" style="width:190px;height:30px;font-size:20px;"  id="catUserName" name="catUserName"/>
						</td>
					</tr>
					<tr style="height:30px;display:none;" id="b_phone">
						<td style="width:30%;text-align:right;">联系电话:</td>
						<td style="padding-left:5px;">
							<input type="text" value="" style="width:190px;height:30px;font-size:20px;"  id="catUserPhone" maxlength=13 name="catUserPhone" onkeyup="value=value.replace(/[^\d]/g,'') "/>
						</td>
					</tr>
					<tr style="height:30px;display:none;" id="b_address">
						<td style="width:30%;text-align:right;">联系地址:</td>
						<td style="padding-left:5px;">
							<input type="text" value="" style="width:190px;height:30px;font-size:20px;" maxlength=100 id="catUserAddress"  name="carAddress"/>
						</td>
					</tr>
					
					<tr style="height:30px;">
						<td style="width:30%;text-align:right;">&nbsp;</td>
						<td style="padding-left:5px;">
							<input onclick="funget();" type="button" style="width:85px; height:32px; border:0px;color:#fff; font-weight:bold;background:url(<%=path%>/images/button_out.png) no-repeat bottom; cursor:pointer;" value="查询"/>
						</td>
					</tr>
	  			</table>
	  		</div>
	  	</div>
	  	<input type="hidden" value="" id="s_interface" name="interfaceType"/>
	  	<input type="hidden" value="" id="s_carNameText" name="carNameText"/>
	</form>
	<br/>
	<div style="padding-left:40px;padding-top:10px;font-size: 13px; color: #FF6600;font-weight:bold;border:1px solid #FF9E4d;background-color: #FFFFE5;">
	<ul>
	<li style="list-style:none;">1、目前只支持广东省内车辆违章查询及办理。</li>
	<li style="list-style:none;">&nbsp;</li>
	<li style="list-style:none;">2、违章代办手续费建议用户在进价基础上每单加收5-15元。</li>
	<li style="list-style:none;">&nbsp;</li>
	<li style="list-style:none;">3、由于违章处理的时效性，系统将自动同步订单状态，办理结果请以平台状态为准。</li>
	<li style="list-style:none;">&nbsp;</li>
	</ul>
	</div>
  </body>
</html>
