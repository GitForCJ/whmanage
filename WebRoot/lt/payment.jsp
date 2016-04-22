<%@ page contentType="text/html; charset=gb2312" language="java"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:useBean id="userSession" scope="session"
	class="com.wlt.webm.rights.form.SysUserForm" />
<jsp:useBean id="DBConfig" scope="page" class="com.wlt.webm.db.DBConfig" />
<%@ page import="com.wlt.webm.tool.Tools" %>
<%
	String path = request.getContextPath();
	if (null == userSession || userSession.getUsername() == null) {
		response.sendRedirect(path + "/rights/wltlogin.jsp");
	}
	float f = Float.parseFloat(DBConfig.getInstance().getGdltUserRebate())/100;//获取特殊佣金 1.5
String flag=Tools.getPwdflag(userSession.getUserno());
request.setAttribute("flag",flag);
%>
<html>
	<head>
		<title>万汇通</title>
		<META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=gb2312">

		<link href="<%=path%>/css/chongzhi_mobile.css" rel="stylesheet"
			type="text/css" />
		<script src="<%=path%>/js/jquery-1.8.2.min.js" type="text/javascript"></script>
		<script type="text/javascript" src="<%=path%>/js/util.js"></script>
		<script language='javascript' src='<%=path%>/js/jquery.js'></script>
		<link href="<%=path%>/css/main_style.css" rel="stylesheet"
			type="text/css" />
		<link href="<%=path%>/css/style.css" rel="stylesheet" type="text/css" />
	<style TYPE="text/css">
		a {
			TEXT-decoration: NONE;
			color: #0078b6;
			font-size: 13px;
		}
		
		.ab {
			padding-LEFT: 30px;
			background: #f8f8f8;
			padding-top: 10px;
			height: 30px;
			border-bottom: 1px solid #e1e2e2;
			font-family: '微软雅黑';
			color: #151111;
			font-size: 14px;
		}
		
		.element a.on {
			background: url(../images/mobile_v2_png24.png) no-repeat;
			background-position: right -19px;
			border: none;
			color: #FF6600;
			padding-right: 11px;
			height: 39px;
			line-height: 39px;
		}
		
		.element a.on span {
			background: url(../images/mobile_v2_png24.png) no-repeat;
			background-position: -181px -19px;
			height: 39px;
			line-height: 39px;
			padding-left: 11px;
			color: #FF6600;
		}
	</style>
	<script language="JavaScript">
	$(function(){
		$("#js_id_price").find("a").bind("click",function(){
			var _dv = $(this).attr("data-value");
			$("#js_id_price").find("a").attr("class","charge-money-sum");
			$(this).attr("class","charge-money-sum on");
			$("#payFee").val(_dv);
		});
	})
	
		function check(str){  
			with(document.forms[0]){
				if(tradeObject.value.length==0){
					alert("电话号码不能为空");
					tradeObject.focus();
					return false;
				}
				if(tradeObject.value.length<8){
					alert("号码格式不对");
					tradeObject.focus();
					return false;
				}
				if(tradeObject.value.substring(0,1)==1){
					alert("此菜单只提供固话和宽带缴费!");
					tradeObject.focus();
					return false;
				}
				target = "_self";
				if(str==0){
					action="../dianxin/telecom.do?method=queryUserMoney";
					//document.getElementById("query").value="查询中...";
				   // document.getElementById("query").disabled="disabled";
				    document.getElementById("xx").disabled="disabled";
				}else{
					if($("#payFee").val()==""){
						alert("请选择金额!");
						return false;
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
					if(confirm("确认为缴费号码:"+ tradeObject.value+",充值金额:"+$("#payFee").val()+"元")){
					
					}else{
						tradeObject.focus();
						return ;
					}
					 action="../dianxin/telecom.do?method=UserPayMent";
					 document.getElementById("xx").value="充值中...";
				     document.getElementById("xx").disabled="disabled";
				     //document.getElementById("query").disabled="disabled";
				}
		    	submit();
			} 
		}
		function getFocus(){
			document.forms[0].tradeObject.focus();
			if("${strA}"==null || "${strA}"=="null" || "${strA}"=="" || "${strA}"==0){
				showMessage("${mess}");
			}else{
				if(window.confirm("${mess},是否打印票据?")){
					window.location.href="<%=path%>/business/Receipt.jsp?str=${strA}&gourl=/lt/payment.jsp";
				}
			}
			funListLoad();
		}
		
	function getmon(){
	 var a=$("#payFee").val();
	 var cc=document.getElementById("txtid").value;
	 if(a=="" || cc==""){
		document.getElementById("cai").style.display="none";
	 	return ;
	 }
	 
	var b=parseFloat(a)*parseFloat(<%=f%>);
	 var c=parseFloat(a)-parseFloat(b);
	   $("#hh").html(c.toFixed(2)+" 元 ,利润:"+b.toFixed(2)+" 元");
	   document.getElementById("caigou").style.display="none";
	   document.getElementById("cai").style.display="block";
	 }	
	function shouqi(){
	 document.getElementById("cai").style.display="none";
	 document.getElementById("caigou").style.display="block";
	}
		
	//列表 打印订单信息小票
	function funPut(strPuts){
		window.location.href="<%=path%>/business/Receipt.jsp?gourl=/lt/payment.jsp&str="+strPuts;
	}
	//列表 交易充值 
	function funAccountReverse(orderid,phonenum,money,datetime)
	{
		if(confirm("确认为缴费号码:"+ phonenum+",充值金额:"+money+"元,进行冲正?")){
		   document.getElementById(orderid).innerHTML="冲正中,,";
		   document.getElementById(orderid).style.color="#cccccc";
		   $.post
		   (
		    	"<%=path%>/business/mobileCharge.do?method=userAccountListReverse",{
		      		orderid:orderid,
		      		datetime:datetime
		      	},
		   		function (data){
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
	    	"<%=path%>/business/prod.do?method=getUserAccountList&listtype=6",{
	      	},
	      	function (data){
	      		if(data==null || data=="" || data=="null"){
	      			$("#tbody").html("<tr><td style='height:100px;font-size:13px;' colspan='8' id='errorMessage'>暂无数据</td></tr>");
	      		}else{
					$("#tbody").html("");
					for(var i=0;i<data.length;i++){
						var strPut=data[i][7]+";"+data[i][4]+";"+data[i][8]+";联通缴费;"+data[i][0]+";"+parseFloat(data[i][1]).toFixed(2);
						//alert(data[i][0]+"   "+data[i][1]+"   "+data[i][2]+"   "+data[i][3]+"   "+data[i][4]+"   "+data[i][5]+"   "+data[i][6]+"   "+data[i][7]+"   "+data[i][8]);
						var state="";//0、成功，1、失败，2、未响应（计费没有响应），3 未处理（未扣款） 4 处理中（已扣款）5 冲正 6异常订单7已退费 
						var puts="";
						if(data[i][5]=="0"){
							state="成功";
							puts="<a href='javascript:funPut(\""+strPut+"\")'>打印票据</a>";
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
						if(data[i][6]=="35" && data[i][5]!="5" && data[i][5]!="7" && data[i][5]!="1"  && data[i][11]=="15"){
							cz="<a href='javascript:funAccountReverse("+data[i][9]+",\""+data[i][0]+"\","+parseFloat(data[i][1]).toFixed(2)+",\""+data[i][10]+"\")'>交易冲正</a>";
						}
						$("#tbody").html($("#tbody").html()+"<tr>"+
						"<td style='border:1px solid #cccccc;height:25px;font-size:13px;'>"+parseInt(i+1)+"</td>"+
						"<td style='border:1px solid #cccccc;height:25px;font-size:13px;'>"+data[i][0]+"</td>"+
						"<td style='border:1px solid #cccccc;height:25px;font-size:13px;'>"+parseFloat(data[i][1]).toFixed(2)+" | "+parseFloat(data[i][2]).toFixed(2)+"</td>"+
						"<td style='border:1px solid #cccccc;height:25px;font-size:13px;'>"+data[i][3]+"</td>"+
						"<td style='border:1px solid #cccccc;height:25px;font-size:13px;'>"+data[i][4]+"</td>"+
						"<td style='border:1px solid #cccccc;height:25px;font-size:13px;'>"+state+"</td>"+
						"<td style='border:1px solid #cccccc;height:25px;font-size:13px;'  id="+data[i][9]+">"+cz+"</td>"+
						"<td style='border:1px solid #cccccc;height:25px;font-size:13px;'>"+puts+"</td>"+
						"</tr>");
					}
				}	      		
	      		return ;
	      	},"json"
		)
	}
	
	</script>
</head>
	<body rightmargin="0" leftmargin="0" topmargin="0" onload="getFocus();">
		<div class="ab" style="border: 1px solid #cccccc; border-bottom: 0px;">
			<b>广东联通充值</b>
		</div>
		<html:form action="/dianxin/telecom.do?method=UserPayMent"
			method="post">
<input type="hidden" id="rootPath" value="<%=path %>"/>
			<div style="border: 1px solid #cccccc; padding-top: 40px;">
				<table border="0" style="margin-left: 110px;">
					<tr>
						<td
							style="font-size: 20px; color: #333333; text-align: right; padding: 0px 0px 0px 0px;">
							充值号码：
						</td>
						<td style="text-align: left; padding-left: 5px;">
							<input type="text" id="txtid" height="28px" name="tradeObject"
								size="18"
								style="font-family: '宋体'; font-size: 35px; width: 280px; height: 45px; font-weight: 900;" />
							<font color="#FF0000"></font>
						</td>
					</tr>
					<tr height=50>
						<td
							style="font-size: 20px; text-align: right; padding: 0px 0px 0px 0px;">
							充值类型：
						</td>
						<td
							style="font-size: 20px; color: #333333; text-align: left; padding-left: 5px;">
							<!-- 
							<input type="radio" name="numType" value="1" checked="checked" />
							移动缴费
							 -->
							<input type="radio" name="numType" value="2" checked="checked"/>
							固话缴费
							<input type="radio" name="numType" value="3" />
							宽带缴费
							<!-- 
							<input
								style="position: absolute; border: none; font-weight: bold; color: #fff; background: url(../images/button_out.png); width: 65px;"
								type="button" onclick="check(0)" id="query" value="账单查询"
								name="Submit3" />
							 -->
						</td>
					</tr>
					<tr>
						<td
							style="font-size: 20px; color: #333333; text-align: right; padding: 0px 0px 0px 0px;">
							充值金额：
						</td>
						<td style="text-align: left; padding-left: 5px;">
							<div class="element" style="width: 500px; float: left;">
								<div id="js_id_price">
									<a class="charge-money-sum" data-value='20'
										href="javascript:void(0);" hidefocus="true" data-money="20"
										data-lidou='20' data-vip='0' data-grow='50'><span>20元</span>
									</a>
									<a class="charge-money-sum" data-value='30'
										href="javascript:void(0);" hidefocus="true" data-money="30"
										data-lidou='30' data-vip='0' data-grow='50'><span>30元</span>
									</a>
									<a class="charge-money-sum " data-value='50'
										href="javascript:void(0);" hidefocus="true" data-money="50"
										data-lidou='50' data-vip='0' data-grow='50'><span>50元</span>
									</a>
									<a class="charge-money-sum" data-value='100'
										href="javascript:void(0);" hidefocus="true" data-money="100"
										data-lidou='100' data-vip='0' data-grow='50'><span>100元</span>
									</a>
									<a class="charge-money-sum" data-value='300'
										href="javascript:void(0);" hidefocus="true" data-money="300"
										data-lidou='300' data-vip='0' data-grow='50'><span>300元</span>
									</a>
									<a class="charge-money-sum" data-value='500'
										href="javascript:void(0);" hidefocus="true" data-money="500"
										data-lidou='500' data-vip='0' data-grow='50'><span>500元</span>
									</a>
								</div>
							</div>
							<input type="hidden" value="" id="payFee" name="payFee" />
						</td>
					</tr>
				</table>
				<br>
<c:if test="${flag==1}">
<div id="mima" style="padding-bottom: 5px;margin-left: 112px;">
<label class="label" for="mobile-num" style="height:45px;line-height: 45px;">交易密码：</label>
<span><input id="psd" type="password" style="font-family:'宋体'; font-size:35px;width:250px;height:35px; font-weight:900;"/></span>
 </div>
</c:if>
				<div id="caigou"
					style="font-size: 20px; color: #333333; margin-left: 112px;">
					采购信息:&nbsp;
					<a onclick="getmon();" href="#" style="font-size: 20px;">点击查看</a>
				</div>
				<div id="cai"
					style="display: none; font-size: 20px; color: #333333; margin-left: 112px;">
					采购信息:
					<span id="hh" style="margin-left: 5px;"></span>&nbsp;&nbsp;
					<a onclick="shouqi();" href="#">点击收起</a>
				</div>
				<br>
				<input type="button" id="xx" name="Button" value="充值"
					class="onbutton" onClick="check(1)"
					style="background: url(../images/button_out.png); display: block; border: 0px; width: 85px; height: 32px; color: #fff; font-weight: bold; margin-left: 200px;">
				<br>
			</div>
			<ul>
				<li
					style="font-size: 15px; text-align: left; font-weight: bold; color: red; padding-left: 20px; margin-top: 5px; width: 95%;">
					当月最近十条交易记录 &nbsp;
					<a href="javascript:funListLoad();" style="float: right;">刷新列表</a>
				</li>
			</ul>
			<div style="margin-top: 5px; border: 1px solid #cccccc;">
				<table>
					<thead>
						<tr>
							<th
								style="width: 120px; height: 25px; border: 1px solid #cccccc;">
								序号
							</th>
							<th
								style="width: 120px; height: 25px; border: 1px solid #cccccc;">
								充值号码
							</th>
							<th
								style="width: 150px; height: 25px; border: 1px solid #cccccc;">
								充值金额|扣费金额
							</th>
							<th
								style="width: 120px; height: 25px; border: 1px solid #cccccc;">
								客户参考余额
							</th>
							<th
								style="width: 120px; height: 25px; border: 1px solid #cccccc;">
								充值时间
							</th>
							<th
								style="width: 120px; height: 25px; border: 1px solid #cccccc;">
								充值状态
							</th>
							<th
								style="width: 120px; height: 25px; border: 1px solid #cccccc;">
								冲正
							</th>
							<th
								style="width: 120px; height: 25px; border: 1px solid #cccccc;">
								凭证打印
							</th>
						</tr>
					</thead>
					<tbody id="tbody" style="text-align: center; width: 100%;">
						<tr>
							<td style="height: 25px; height: 275px;" colspan="8"
								id="errorMessage">
								<img src="<%=path%>/images/load.gif" />
							</td>
						</tr>
					</tbody>
				</table>
			</div>
			<br />
			<div
				style="padding-left: 40px; padding-top: 10px; font-size: 13px; color: #FF6600; font-weight: bold; border: solid 1px #FF9E4d; background-color: #FFFFE5;">
				<ul>
					<li style="list-style: none;">
						1、提供广东联通手机、固话、宽带任意金额充值，单笔金额不大于500元，预付费号码最低30元，后付费号码最低10元。支持冲正操作，但不承诺一定成功。
					</li>
					<li style="list-style: none;">
						&nbsp;
					</li>
					<li style="list-style: none;">
						2、"综合缴费"是指给该号码对应的合同内所有账号充值，“单一缴费”是指仅给充值号码自身充值，“宽带缴费”是指给宽带账号充值。
					</li>
					<li style="list-style: none;">
						&nbsp;
					</li>
					<li style="list-style: none;">
						3、"账单查询"是指后付费号码的账单，如果账单内容过多，只显示部分账单内容。
					</li>
					<li style="list-style: none;">
						&nbsp;
					</li>
					<li style="list-style: none;">
						4、如果缴费号码是座机或宽带号码，需填写缴费号码所属区域的区号。
					</li>
					<li style="list-style: none;">
						&nbsp;
					</li>
				</ul>
			</div>
		</html:form>
	<body>
</html>
