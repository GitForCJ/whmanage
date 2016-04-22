<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@ page import="java.util.*"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<jsp:useBean id="userSession" scope="session" class="com.wlt.webm.rights.form.SysUserForm" />
<%
	String path = request.getContextPath();
%>
<html>
	<head>
		<title>万汇通</title>

		<script type="text/javascript" src="../js/jquery-1.8.2.min.js"></script>
		<script type="text/javascript" src="../js/util.js"></script>
		<script language="javascript" type="text/javascript"
			src="../My97DatePicker/WdatePicker.js"></script>
		<script type="text/javascript">
	window.onload=function(){
		document.getElementById("state").value="${state}";
		if("${inter}"!=""){
			document.getElementById("interID").value="${inter}";
		}else{
			document.getElementById("interID").value=-22;
		}
		
		if("${plateNumberA}"!="")
			document.getElementById("plateNumberA").value="${plateNumberA}";
	}
	//提示框
	function funLoad()
	{
		if(!document.getElementById("load"))
		{
			var body=document.getElementsByTagName('body')[0];
			var left=(document.body.offsetWidth-350)/2;
			var div="<div id='removeload'><div id='load' style='border:1px solid black;background-color:white;position:absolute;top:230px;left:"+left+"px;width:350px;height:90px;z-Index:999;text-align:center;font-weight:bold;'><div style='border-bottom:1px solid #cccccc;height:25px;line-height:25px;text-align:left;padding-left:10px;font-size:13px;'>温馨提示</div><div style='width:100%;height:65px;line-height:65px;font-size:13px;'><img src='<%=path%>/images/load.gif' />&nbsp;数据加载中，请稍后，，，，</div></div></div>";
			body.innerHTML=body.innerHTML+div;
		}
	}
	// 去空格
	String.prototype.Trim = function() 
	{ 
		return this.replace(/(^\s*)|(\s*$)/g, ""); 
	}
	//查询
	function funSelect()
	{	
		document.getElementById("indexID").value="${index}";
		document.getElementById("for").action="<%=path %>/business/mobileCharge.do?method=catMessageList&wheretype=0";
		document.getElementById("for").submit();
		funLoad();
		return ;
	}
	//下一页  跳转
	function funPage(index){
		document.getElementById("indexID").value=index;
		document.getElementById("for").action="<%=path %>/business/mobileCharge.do?method=catMessageList&wheretype=0";
		document.getElementById("for").submit();
		funLoad();
		return ;
	}
	
	//显示指定订单的违章记录
	function showCatMessage(gdID,catId,gdwoState,interfaceId)//interfaceId 区分百事帮 电信  接口
	{
		if($("#"+gdID+"_updown").text()=="展开"){
			//关闭所有 动态添加的tr,
			var ssss=document.getElementsByName("ssss");
			for(var i=0;i<ssss.length;i++){
				$("#"+ssss[i].value+"_table").text("");
				document.getElementById(ssss[i].value+"_tr").style.display="none";
			}
			var showMesageNamss=document.getElementsByName("showMesageNamss");
			for(var i=0;i<showMesageNamss.length;i++){
				showMesageNamss[i].innerHTML="展开";
			}
			//显示 动态添加的 tr
			$("#"+gdID+"_updown").text("收起");
			var str="<tr>"+
					"<td width=55 border=0 height=35></td>"+
   					"<td style='text-align: center; border-bottom: 1px solid #1A7FC3; border-right: 1px solid #1A7FC3;border-left: 1px solid #1A7FC3;width:55px;'>违章ID</td>"+
   					"<td style='text-align: center; border-bottom: 1px solid #1A7FC3; border-right: 1px solid #1A7FC3;width:168px;'>违章地点</td>"+
   					"<td style='text-align: center; border-bottom: 1px solid #1A7FC3; border-right: 1px solid #1A7FC3;width:143px;'>违章时间</td>"+
   					"<td style='text-align: center; border-bottom: 1px solid #1A7FC3; border-right: 1px solid #1A7FC3;width:161px;'>创建时间</td>"+
   					"<td style='text-align: center; border-bottom: 1px solid #1A7FC3; border-right: 1px solid #1A7FC3;width:90px;'>实际/预计<br/>(状态)</td>"+
   					"<td style='text-align: center; border-bottom: 1px solid #1A7FC3;width:150px;'>操作</td>"+
   					"</tr>";
			$.post(
		    	"<%=path %>/business/mobileCharge.do?method=show_breakrules",
		      	{
		      		gdID:gdID
		      	},
		      	function (data){
		      		if(data!=null && data!=""){
		      			for(var i=0;i<data.length;i++){
		      				var ssstate="";
		      				if(1==data[i][17]){
								ssstate="下单成功未支付";
							}else if(2==data[i][17]){
								ssstate="已支付";
							}else if(3==data[i][17]){
								ssstate="办理中";
							}else if(4==data[i][17]){
								ssstate="办理成功";
							}else if(5==data[i][17]){
								ssstate="办理失败";
							}else if(6==data[i][17]){
								ssstate="办理异常";
							}else if(7==data[i][17]){
								ssstate="取消";
							}else if(-99==data[i][17]){
								ssstate="删除代办单";
							}else if(100==data[i][17]){
								ssstate="处理中";
							}else if(101==data[i][17]){
								ssstate="已退费";
							}else{
								ssstate="未知状态";
							}
		      				str=str+"<tr>"+
		      					"<td width=55 border=0 height=60></td>"+
		      					"<td style='text-align: center; border-bottom: 1px solid #1A7FC3; border-right: 1px solid #1A7FC3;border-left: 1px solid #1A7FC3;'>"+data[i][0]+"</td>"+
		      					"<td style='text-align: center; border-bottom: 1px solid #1A7FC3; border-right: 1px solid #1A7FC3;'>"+data[i][4]+"</td>"+
		      					"<td style='text-align: center; border-bottom: 1px solid #1A7FC3; border-right: 1px solid #1A7FC3;'>"+data[i][5]+"</td>"+
		      					"<td style='text-align: center; border-bottom: 1px solid #1A7FC3; border-right: 1px solid #1A7FC3;'>"+data[i][14]+"</td>"+
		      					"<td style='text-align: center; border-bottom: 1px solid #1A7FC3; border-right: 1px solid #1A7FC3;'>"+((parseFloat(data[i][23]))/1000)+"/"+((parseFloat(data[i][24]))/1000)+"元<br/>("+ssstate+")</td>"+
		      					"<td style='text-align: center; border-bottom: 1px solid #1A7FC3;line-height:30px; '>"+
		      					"<a style='cursor:hand;color:red;' onclick='funshowInfo(event,\""+catId+"\",\""+data[i][5]+"\",\""+data[i][4]+"\",\""+data[i][3]+"\","+data[i][7]/1000+","+data[i][8]/1000+","+data[i][9]/1000+","+data[i][6]+","+data[i][10]/1000+",\""+data[i][22]+"\",\""+data[i][23]/1000+"\")'>详细</a>";
		      				
		      				//单条违章状态 不等于 已退费，办理成功 并且  工单状态 不等于已退费，办理成功
	      					if(data[i][17]!=101 && data[i][17]!=4 && gdwoState!=101 && gdwoState!=4){
								str=str+"&nbsp;<a href='javascript:funRefund(22,\""+gdID+"\","+(parseFloat(data[i][23]))+","+data[i][0]+","+interfaceId+")'>退费</a>";		      					
	      					}
	      					if(data[i][17]!=101 && gdwoState!=101)
	      					{
								str=str+"<br/>"+
								"<select style='width:100px;' onchange='funWorkdOrderStateUpdate(1,\""+gdID+"\",this,"+data[i][0]+","+interfaceId+")'>"+
									"<option value='100' "+(data[i][17]==100?"selected":"")+">处理中</option>"+
									"<option value='3' "+(data[i][17]==3?"selected":"")+">办理中</option>"+
									"<option value='2' "+(data[i][17]==2?"selected":"")+">已支付</option>"+
									"<option value='1' "+(data[i][17]==1?"selected":"")+">下单成功未支付</option>"+
									"<option value='4' "+(data[i][17]==4?"selected":"")+">办理成功</option>"+
									"<option value='5' "+(data[i][17]==5?"selected":"")+">办理失败</option>"+
									"<option value='6' "+(data[i][17]==6?"selected":"")+">办理异常</option>"+
									"<option value='7' "+(data[i][17]==7?"selected":"")+">取消</option>"+
									"<option value='101' "+(data[i][17]==101?"selected":"")+">已退费</option>"+
								"</select>";
							}
		      				str=str+"</td></tr>";
		      			}
		      			$("#"+gdID+"_table").html(str);
		      		}
		      	}
			)
			document.getElementById(gdID+"_tr").style.display="";  
		}else if($("#"+gdID+"_updown").text()=="收起"){
			$("#"+gdID+"_updown").text("展开");
			$("#"+gdID+"_table").text("");
			document.getElementById(gdID+"_tr").style.display="none";
		}else{
			alert("未知状态");
			return;
		}
	}
	//提示信息
	function funshowInfo(event,a,b,c,d,aa,bb,cc,dd,ee,tiaoma,zmoney)
	{
		document.getElementById("showInfoMessage").innerHTML="";
		document.getElementById("showInfoMessage").style.display="block";
		document.getElementById("showInfoMessage").style.top=(event.clientY-70)+"px";
		document.getElementById("showInfoMessage").style.left=(event.clientX-25-180)+"px";
		
		var sb0="<table cellpadding=5 cellspacing=0 border=0 style='width:100%;font-size:13px;'> "+
		"<tr style='height:25px;'s><td style='text-align:right;' colspan=2><a href='javascript:funclose()' style='fonts-zie:12px;'>关闭</a>&nbsp;</td></tr> "+
		"<tr style='height:22px;'><td style='width:50px;text-align:right;'>车牌:</td><td style='text-align:left;'>"+a+"</td></tr> "+
		"<tr style='height:22px;'><td style='width:50px;text-align:right;'>时间:</td><td style='text-align:left;'>"+b+"</td></tr> "+
		"<tr style='height:22px;'><td style='width:50px;text-align:right;'>地点:</td><td style='text-align:left;'>"+c+"</td></tr> "+
		"<tr style='height:22px;'><td style='width:50px;text-align:right;'>扣分:</td><td style='text-align:left;'>"+dd+"</td></tr> "+
		"<tr style='height:22px;'><td style='width:50px;text-align:right;'>本金:</td><td style='text-align:left;'>"+aa+"元</td></tr> ";
		sb0=sb0+"<tr style='height:22px;'><td style='width:50px;text-align:right;'>滞纳金:</td><td style='text-align:left;'>"+bb+"元</td></tr> "+
		"<tr style='height:22px;'><td style='width:50px;text-align:right;'>手续费:</td><td style='text-align:left;'>"+cc+"元</td></tr> "+
		"<tr style='height:22px;'><td style='width:50px;text-align:right;'>代办费:</td><td style='text-align:left;'>"+ee+"元</td></tr> ";
		sb0=sb0+"<tr style='height:22px;'><td style='width:50px;text-align:right;'>总费用:</td><td style='text-align:left;'>"+zmoney+"元</td></tr> ";
		//if("${userSession.usersite}"==382){//中石化代理店
		//	sb0=sb0+"<tr style='height:22px;'><td style='width:50px;text-align:right;'>条码:</td><td style='text-align:left;'>39076("+zmoney+"倍)</td></tr> ";
		//}
		sb0=sb0+"<tr style='height:22px;'><td style='width:50px;text-align:right;position:relative;'><div style='font-size:12px;position:absolute;top:7px;right:5px;'>内容:</div></td><td style='text-align:left;'>"+d+"</td></tr></table>";
		sb0=sb0+"<div style='position:absolute;right:-12px;top:60px;width:0;height:0;border-top:10px solid transparent;border-LEFT: 12px solid #FF9E4d;border-bottom:10px solid transparent;'></div>";
		document.getElementById("showInfoMessage").innerHTML=sb0;
	}
	//关闭 显示详细信息
	function funclose()
	{
		document.getElementById("showInfoMessage").style.display="none";
	}
	
	//下单  type 33工单  money下单金额  
	/**
	function funUpDown(type,gdid,money){
		var bool=false;
		if(type==33){
			if(confirm("你确定要为 工单ID: "+gdid+" 订单,交易金额: "+(money/1000)+"元 下单吗!")){
				bool=true;
			}else{
				return ;
			}
		}else{
			alert("未知订单类型!");
			return ;
		}
		$("#"+gdid+"_abcd").text("");
		if(document.getElementById("load"))
		{
			document.getElementById("removeload").innerHTML="";
		}
		funLoad();
		if(bool && (parseInt(money)>1000)){
			$.post(
		    	"<%=path %>/business/mobileCharge.do?method=catStateHandle",
		      	{
		      		typeA:type,
		      		gdid:gdid,
		      		wzid:"",
		      		money:money
		      	},
		      	function (obj){
		      		alert(obj.msg);
		      		funSelect();
		      	}
			)			
		}
		return ;
	}
	*/
	//退费  type 11工单 22违章记录    id   money退费金额  interfaceID接口id
	function funRefund(type,gdid,money,wzid,interfaceID){
		var bool=false;
		if(type==11){
			if(confirm("你确定要为 工单ID: "+gdid+" 退费 "+(parseInt(money/1000))+"元 吗!")){
				bool=true;
			}else{
				return ;
			}
		}else if(type==22){
			if(confirm("你确定要为 工单ID: "+gdid+" , 违章ID: "+wzid+" 退费 "+(parseInt(money/1000))+"元 吗!")){
				bool=true;
			}
		}else{
			alert("未知退费类型!");
			return ;
		}
		if(bool && (parseInt(money)>1000)){
			$.post(
		    	"<%=path %>/business/mobileCharge.do?method=catStateHandle",
		      	{
		      		typeA:type,
		      		gdid:gdid,
		      		wzid:wzid,
		      		money:money,
		      		inter:interfaceID
		      	},
		      	function (obj){
		      		alert(obj.msg);
		      		funSelect();
		      	}
			)			
		}
		return ;
	}
	//工单状态修改  type 0工单 1违章记录  id   this 修改后状态    interfaceID 接口id
	function funWorkdOrderStateUpdate(type,gdid,className,wzid,interfaceID){
		var bool=false;
		var state=-1;
		if(type==0){
			if(confirm("你确定要修改 工单ID: "+gdid+" 状态吗!")){
				bool=true;
				state=className.value;
			}else{
				return ;
			}
		}else if(type==1){
			if(confirm("你确定要修改 工单ID: "+gdid+" , 违章ID: "+wzid+" 状态吗!")){
				bool=true;
				state=className.value;
			}else{
				return ;
			}
		}else{
			alert("未知调用类型!");
			return ;
		}
		if(bool && state!=-1){
			$.post(
		    	"<%=path %>/business/mobileCharge.do?method=catStateHandle",
		      	{
		      		typeA:type,
		      		gdid:gdid,
		      		wzid:wzid,
		      		state:state,
		      		inter:interfaceID
		      	},
		      	function (obj){
		      		alert(obj.msg);
		      		funSelect();
		      	}
			)			
		}
		return ;
	}
	//导出excel
	function funExcel(){
		document.getElementById("for").action="<%=path %>/business/mobileCharge.do?method=catMessageList&wheretype=1";
		document.getElementById("for").submit();
		return;
	}
</script>
	</head>
	<body style="margin: 0px 0px 0px 0px; padding: 0px 0px 0px 0px;padding-top:10px;">
		<form action="<%=path %>/business/mobileCharge.do?method=catMessageList"
			method="post" id="for">
			<ul style="padding-left: 10px; height: 32px; margin-bottom: 0px;">
				<li>
					<input onclick="funSelect()" type="button"
						style="width: 85px; height: 32px; border: 0px; color: #fff; font-weight: bold; background: url(<%=path%>/images/button_out.png) no-repeat bottom; cursor: pointer;"
						value="查询" />
					<c:if test="${(sessionScope.userSession.roleType == 0)}">
						<input onclick="funExcel()" type="button"
							style="width: 85px; height: 32px; border: 0px; color: #fff; font-weight: bold; background: url(<%=path%>/images/button_out.png) no-repeat bottom; cursor: pointer;"
							value="导出" />
					</c:if>
				</li>
			</ul>
			<div
				style="height: 100px; border: 1px solid #cccccc; margin-top: 0px; padding: 0px 0px 0px 0px;padding-top:10px;">
				<ul
					style="margin: 0px 0px 0px 0px; padding: 0px 0px 0px 0px; text-align: center;">
					<li style="list-style-type: none; font-size: 13px;height:40px;line-height:40px;">
						开始日期:
						<input style="width: 120px; height: 25px;" class="Wdate"
							name="inDate" type="text" onClick="WdatePicker()"
							value="${inDate }">
						&nbsp; 结束日期:
						<input style="width: 120px; height: 25px;" class="Wdate"
							name="endDate" type="text" onClick="WdatePicker()"
							value="${endDate }">
						&nbsp; 办理状态:
						<select style="width: 120px; height: 25px;" name="state"
							id="state">
							<option value="">
								-- 请选择 --
							</option>
							<option value="100">
								 处理中
							</option>
							<option value="3">
								办理中
							</option>
							<option value="2">
								已支付
							</option>
							<option value="1">
								下单成功未支付
							</option>
							<option value="4">
								办理成功
							</option>
							<option value="5">
								办理失败
							</option>
							<option value="6">
								办理异常
							</option>
							<option value="7">
								取消
							</option>
							<option value="101">
								已退费
							</option>
						</select>
					</li>
					<li style="list-style-type: none; font-size: 13px;height:40px;line-height:40px;">
						车牌号:
						<select style="height:25px;" id="plateNumberA" name="plateNumberA" >
							<option value="粤">粤</option>
						</select>
						<input style="width: 120px; height: 25px;" value="${plateNumberB}" name="plateNumberB"/>
						&nbsp;
						车主姓名:
						<input style="width: 120px; height: 25px;" value="${catName}" name="catName"/>
						&nbsp;
						<c:if test="${(sessionScope.userSession.roleType == 0)}">
							代理点账号:
							<input style="width: 120px; height: 25px;" value="${userName}" name="userName"/>
							&nbsp;
							接口:
							<select style="height:25px;width:50px;" id="interID" name="inter" >
								<option value="-22">全部</option>
								<!-- 
								<option value="1">电信</option>
								 -->
								<option value="2">百事帮</option>
							</select>
						</c:if>
					</li>
				</ul>
			</div>
			<table border="0" style="width: 100%; font-size: 12px; border: 1px solid #1A7FC3; margin-top: 10px;" cellspacing="0" cellpadding="0">
				<tr style="height: 30px; background-color: #E6EFF6;">
					<th style="text-align: center; border-bottom: 1px solid #1A7FC3; border-right: 1px solid #1A7FC3;"  width=55>
						工单ID
					</th>
					<th style="text-align: center; border-bottom: 1px solid #1A7FC3; border-right: 1px solid #1A7FC3;width:55px;">
						姓名
					</th>
					<th style="text-align: center; border-bottom: 1px solid #1A7FC3; border-right: 1px solid #1A7FC3;width:95px;">
						电话
					</th>
					<th style="text-align: center; border-bottom: 1px solid #1A7FC3; border-right: 1px solid #1A7FC3;width:70px;">
						车牌号
					</th>
					<th style="text-align: center; border-bottom: 1px solid #1A7FC3; border-right: 1px solid #1A7FC3;width:70px;">
						<c:if test="${(sessionScope.userSession.roleType != 0)}">
							实际总额
						</c:if>
						<c:if test="${(sessionScope.userSession.roleType == 0)}">
							实际/预计
						</c:if>
					</th>
					<th style="text-align: center; border-bottom: 1px solid #1A7FC3; border-right: 1px solid #1A7FC3;width:100px;">
						下单日期
					</th>
					<th style="text-align: center; border-bottom: 1px solid #1A7FC3; border-right: 1px solid #1A7FC3;width:70px;">
						办理状态
					</th>
					<th style="text-align: center; border-bottom: 1px solid #1A7FC3; border-right: 1px solid #1A7FC3;width:90px;">
						六位/四位
					</th>
					<th style="text-align: center; border-bottom: 1px solid #1A7FC3; border-right: 1px solid #1A7FC3;width:90px;">
						系统编号
					</th>
					<th style="text-align: center; border-bottom: 1px solid #1A7FC3;width:150px;">
						操作
					</th>
				</tr>
				<c:forEach items="${arrList}" var="arr">
					<tr style="height: 60px;">
						<td  width=55 style="background-color:#8DCDF7;text-align: center; border-bottom: 1px solid #1A7FC3; border-right: 1px solid #1A7FC3;word-wrap:break-word;word-break:break-all;">
							${arr[0]}
						</td>
						<td style="background-color:#8DCDF7;text-align: center; border-bottom: 1px solid #1A7FC3; border-right: 1px solid #1A7FC3;">
							${arr[1]}
						</td>
						<td style="background-color:#8DCDF7;text-align: center; border-bottom: 1px solid #1A7FC3; border-right: 1px solid #1A7FC3;">
							${arr[2]}
						</td>
						<td style="background-color:#8DCDF7;text-align: center; border-bottom: 1px solid #1A7FC3; border-right: 1px solid #1A7FC3;">
							${arr[3]}
						</td>
						<td style="background-color:#8DCDF7;text-align: center; border-bottom: 1px solid #1A7FC3; border-right: 1px solid #1A7FC3;">
							${arr[4]/1000}/${arr[10]/1000}元
						</td>
						<td style="background-color:#8DCDF7;text-align: center; border-bottom: 1px solid #1A7FC3; border-right: 1px solid #1A7FC3;">
							${arr[5]}
						</td>
						<td style="background-color:#8DCDF7;text-align: center; border-bottom: 1px solid #1A7FC3; border-right: 1px solid #1A7FC3;">
							<c:if test="${arr[6]==100}">处理中</c:if>
							<c:if test="${arr[6]==3}">办理中</c:if>
							<c:if test="${arr[6]==2}">已支付</c:if>
							<c:if test="${arr[6]==1}">下单成功未支付</c:if>
							<c:if test="${arr[6]==4}">办理成功</c:if>
							<c:if test="${arr[6]==5}">办理失败</c:if>
							<c:if test="${arr[6]==6}">办理异常</c:if>
							<c:if test="${arr[6]==7}">取消</c:if>
							<c:if test="${arr[6]==101}">已退费</c:if>
							<c:if test="${arr[6]!=100 && arr[6]!=3 && arr[6]!=2 && arr[6]!=1 && arr[6]!=4 && arr[6]!=5 && arr[6]!=6 && arr[6]!=7 && arr[6]!=101}">
							未知状态
							</c:if>
						</td>
						<td style="background-color:#8DCDF7;text-align: center; border-bottom: 1px solid #1A7FC3; border-right: 1px solid #1A7FC3;">
							${arr[7]}/${arr[8]}
						</td>
						<td style="background-color:#8DCDF7;text-align: center; border-bottom: 1px solid #1A7FC3; border-right: 1px solid #1A7FC3;">
							${arr[11]}
						</td>
						<td style="background-color:#8DCDF7;text-align: center; border-bottom: 1px solid #1A7FC3; border-right: 1px solid #1A7FC3; line-height:30px;">
							<a href="javascript:showCatMessage('${arr[0]}','${arr[3]}',${arr[6]},${arr[9]})" id="${arr[0]}_updown" name="showMesageNamss">展开</a>&nbsp;
										<!-- 不是百事帮 接口 可以下单 -->
										<!-- 
										<c:if test="${arr[6]==100}">
											<c:if test="${arr[9]!=2}">
												<a href="javascript:funUpDown(33,'${arr[0]}',${arr[4]})" id="${arr[0]}_abcd">下单</a>&nbsp;
											</c:if>
										</c:if>
										 -->
							 <!-- 工单不是已退费,成功的，，显示 退费 功能 -->
							<c:if test="${arr[6]!=101 && arr[6]!=4}">
								<a href="javascript:funRefund(11,'${arr[0]}',${arr[4]},'',${arr[9]})">退费</a>
							</c:if>
							<br/>
							<select style="width:100px;height:20px;" onchange="funWorkdOrderStateUpdate(0,'${arr[0]}',this,'',${arr[9]})">
								<option value="100" <c:if test='${arr[6]==100}'>selected="selected"</c:if> >处理中</option>
								<option value="3" <c:if test='${arr[6]==3}'>selected="selected"</c:if> >办理中</option>
								<option value="2" <c:if test='${arr[6]==2}'>selected="selected"</c:if> >已支付</option>
								<option value="1" <c:if test='${arr[6]==1}'>selected="selected"</c:if> >下单成功未支付</option>
								<option value="4" <c:if test='${arr[6]==4}'>selected="selected"</c:if> >办理成功</option>
								<option value="5" <c:if test='${arr[6]==5}'>selected="selected"</c:if> >办理失败</option>
								<option value="6" <c:if test='${arr[6]==6}'>selected="selected"</c:if> >办理异常</option>
								<option value="7" <c:if test='${arr[6]==7}'>selected="selected"</c:if> >取消 </option>
								<option value="101" <c:if test='${arr[6]==101}'>selected="selected"</c:if> >已退费</option>
							</select>
						</td>
					</tr>
					<tr style="display:none;" id="${arr[0]}_tr">
						<td colspan=10 >
							<table border="0" style="width: 100%; font-size: 12px; " cellspacing="0" cellpadding="0" id="${arr[0]}_table">
							</table>
						</td>
					</tr>
					<input type="hidden" value="${arr[0]}" name="ssss"/>
				</c:forEach>
				<tfoot>
					<tr height="30">
						<td colspan="10" class="tr_line_top">
							<c:if test="${index!=null}">
								<div style="text-align:right;padding-right:10px;">
									<a href="javascript:funPage(1)">首页</a>
									<a href="javascript:funPage(${index-1})">上一页</a>
									<a href="javascript:funPage(${index+1})">下一页</a>
									<a href="javascript:funPage(${lastIndex})">尾页</a>&nbsp;
									跳转&nbsp;<input id="pageId" type="text" style="width:30px" value="${index}" onblur="funPage(this.value)" onkeyup="value=value.replace(/[^\d]/g,'') "/>&nbsp;页
									&nbsp;
									第 ${index} 页
								</div>
							</c:if>
						</td>
					</tr>
				</tfoot>
			</table>
			<input type="hidden" value="" name="index" id="indexID"/>
			<input type="hidden" value="${whereStr}" name="where"/>
			<input type="hidden" value="${whereInfo}" name="whereInfo"/>
		</form>
		<div style="display:none;border:1px solid #f8f8f8;width:180px;position:absolute;left:0px;top:0px;background-color:#FFFFE5;border:1px solid #FF9E4d;" id="showInfoMessage"></div> 
		<br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/>
	</body>
</html>