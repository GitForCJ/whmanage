<%@ page language="java" import="java.util.*" pageEncoding="GBK"  contentType="text/html; charset=GBK"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%
request.setCharacterEncoding("GBK");
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<meta http-equiv="Content-Type" content="text/html; charset=GBK" />
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>交通违章</title>
    <link href="<%=path %>/css/chongzhi_mobile.css" rel="stylesheet" type="text/css" />
	<script src="<%=path %>/js/jquery-1.8.2.min.js" type="text/javascript"></script>
	<script type="text/javascript" src="<%=path %>/js/util.js"></script>
	<script language='javascript' src='<%=path %>/js/jquery.js'></script>
    <style type="text/css">
		.black_overlay{ 
		    display: none; 
		    position: absolute; 
		    top: 0%; 
		    left: 0%; 
		    width: 100%; 
		    height: 100%; 
		    background-color: black; 
		    z-index:1001; 
		    -moz-opacity: 0.8; 
		    opacity:.80; 
		    filter: alpha(opacity=88); 
		} 
		.white_content { 
		    display: none; 
		    z-index:1002; 
		    overflow: auto; 
		} 
	</style>
    <script type="text/javascript">
    	// 去空格
		String.prototype.Trim = function() 
		{ 
			return this.replace(/(^\s*)|(\s*$)/g, ""); 
		} 
		function funclose()
		{
			document.getElementById("showInfoMessage").style.display="none";
		}
		function funshowInfo(event,a,b,c,d,aa,bb,cc,dd,ee)
		{
			document.getElementById("showInfoMessage").innerHTML="";
			document.getElementById("showInfoMessage").style.display="block";
			document.getElementById("showInfoMessage").style.top=(event.clientY-70)+"px";
			document.getElementById("showInfoMessage").style.left=(event.clientX-25-180)+"px";
			document.getElementById("showInfoMessage").innerHTML=document.getElementById("showInfoMessage").innerHTML+
			"<table cellpadding=5 cellspacing=0 border=0 style='width:100%;font-size:13px;'> "+
			"<tr style='height:25px;'s><td style='text-align:right;' colspan=2><a href='javascript:funclose()' style='fonts-zie:12px;'>关闭</a>&nbsp;</td></tr> "+
			"<tr style='height:22px;'><td style='width:50px;text-align:right;'>车牌:</td><td style='text-align:left;'>"+a+"</td></tr> "+
			"<tr style='height:22px;'><td style='width:50px;text-align:right;'>时间:</td><td style='text-align:left;'>"+b+"</td></tr> "+
			"<tr style='height:22px;'><td style='width:50px;text-align:right;'>地点:</td><td style='text-align:left;'>"+c+"</td></tr> "+
			"<tr style='height:22px;'><td style='width:50px;text-align:right;'>扣分:</td><td style='text-align:left;'>"+dd+"</td></tr> "+
			"<tr style='height:22px;'><td style='width:50px;text-align:right;'>本金:</td><td style='text-align:left;'>"+aa+"元</td></tr> "+
			"<tr style='height:22px;'><td style='width:50px;text-align:right;'>滞纳金:</td><td style='text-align:left;'>"+bb+"元</td></tr> "+
			"<tr style='height:22px;'><td style='width:50px;text-align:right;'>代办费:</td><td style='text-align:left;'>"+(parseFloat(cc)+parseFloat(ee))+"元</td></tr> "+
			"<tr style='height:22px;'><td style='width:50px;text-align:right;'>总费用:</td><td style='text-align:left;'>"+(parseFloat(aa)+parseFloat(bb)+parseFloat(cc)+parseFloat(ee))+"元</td></tr> "+
			"<tr style='height:22px;'><td style='width:50px;text-align:right;position:relative;'><div style='font-size:12px;position:absolute;top:7px;right:5px;'>内容:</div></td><td style='text-align:left;'>"+d+"</td></tr> "+
			"</table>";
			
			document.getElementById("showInfoMessage").innerHTML=document.getElementById("showInfoMessage").innerHTML+"<div style='position:absolute;right:-12px;top:60px;width:0;height:0;border-top:10px solid transparent;border-LEFT: 12px solid #FF9E4d;border-bottom:10px solid transparent;'></div>";
		}
		
		//全选
		function funClears(className)
		{
			var arrs=document.getElementsByName("ck");
			for(var i=0;i<arrs.length;i++)
			{
				if(className.checked){
					arrs[i].checked=true;
				}else{
					arrs[i].checked=false;
				}
			}
		}
		//单选
		function funaa(className)
		{
			document.getElementById("qck").checked=false;
			var arrs=document.getElementsByName("ck");
			var bool=true;
			if(className.checked)
			{
				for(var i=0;i<arrs.length;i++)
				{
					if(!arrs[i].checked)
					{
						bool=false;
						break;
					}
				}
				if(bool)
				{
					document.getElementById("qck").checked=true;
				}
			}
		}
		
		//下单
		function funget(className)
		{
			if("${interfaceType}"==1) //电信
			{
				var f=document.getElementById("userNameId").value;
	    		var g=document.getElementById("catUserPhoneId").value;
	    		var address=document.getElementById("catUserAddress").value;
	    		if(f.Trim()==""){
	    			alert("请输入车主姓名!");
	    			return ;
	    		}
	    		if(g.Trim()==""){
	    			alert("请输入车主手机号码!");
	    			return ;
	    		}
	    		if(g.Trim().length!=11)
	    		{
	    			alert("请输入正确的手机号码!");
	    			return ;
	    		}
	    		document.getElementById("h_catUserName").value=f;
	    		document.getElementById("h_catUserPhone").value=g;
	    		document.getElementById("h_carAddress").value=address;
    		}
    		
			var bool=false;
			var sumMoney=0;
			var arrs=document.getElementsByName("ck");
			for(var i=0;i<arrs.length;i++){
				var bs=arrs[i].value;
				document.getElementById(bs+"_A").checked=false;
			}
			for(var i=0;i<arrs.length;i++){
				var bs=arrs[i].value;
				if(arrs[i].checked){
					document.getElementById(bs+"_A").checked=true;
					sumMoney=sumMoney+parseFloat(document.getElementById(bs+"_money").value);
					bool=true;
				}
			}
			if(bool){
				document.getElementById("sumMoneyId").value=sumMoney;
				if("${userSession.usersite}"!=382){//普通代理店下单
					if(confirm("下单金额为："+sumMoney+" 元!")){
						className.disabled="disabled";
						className.value="处理中";
						document.getElementById("for").submit();
						funLoad();
					}
					return;
				}
				//中石化代理店下单
				
				$("#showMMImg").html("<img src='<%=path%>/imgs/zsh/barcode/qqbitiaoma.jpg' height=70/>"+39076);
				$("#showMMTow").html("请扫描&nbsp;"+sumMoney+"&nbsp;次条码");
				
				document.getElementById('light').style.position="absolute"; 
		  		document.getElementById('light').style.left=(document.body.clientWidth-500)/2+"px"
		  		document.getElementById('light').style.top="20%";
		  		$("#showMM").text("你确认为车牌号：${catNumbers} ,缴费："+sumMoney+"元");
				document.getElementById('light').style.display='block';
		  		document.getElementById('fade').style.display='block';
				return;
			}else{
				alert("请选择要处理的违章记录!");
				return ;
			}
		}
		
		function funLoad()
		{
			if(!document.getElementById("load"))
			{
				var body=document.getElementsByTagName('body')[0];
				var left=(document.body.offsetWidth-350)/2;
				var div="<div id='load' style='border:1px solid black;background-color:white;position:absolute;top:180px;left:"+left+"px;width:350px;height:90px;z-Index:999;text-align:center;font-weight:bold;'><div style='border-bottom:1px solid #cccccc;height:25px;line-height:25px;text-align:left;padding-left:10px;font-size:13px;'>温馨提示</div><div style='width:100%;height:65px;line-height:65px;font-size:13px;'><img src='<%=path%>/images/load.gif' />&nbsp;下单中，请稍后，，，，</div></div>";
				body.innerHTML=body.innerHTML+div;
			}
		}
		//中石化下单取消
		function funCloseDiv()
		{
			document.getElementById('light').style.display='none';
		  	document.getElementById('fade').style.display='none';
		}
		//中石化下单提交
		function funbuttonSubmit()
		{
			document.getElementById("xiadans").disabled="disabled";
		    document.getElementById("xiadans").value="下单中...";
		    document.getElementById("for").submit();
		   	document.getElementById('light').innerHTML="";
		    document.getElementById("light").style.fontSize="18px";
		    document.getElementById("light").style.textAlign="center";
		    document.getElementById("light").style.height="50px";
		    document.getElementById("light").style.paddingTop="20px";
		    document.getElementById('light').innerHTML="<img src='<%=path%>/images/load.gif'/>&nbsp;下单中，请稍后，，，";
		    return ;
		}
    </script>
  </head>
  
  <body>
	  <form action="<%=path %>/business/mobileCharge.do?method=insertMessage" method="post" id="for">
	  	<div >
	  			<ul style="padding:0px 0px 0px 0px;width:100%;height:30px;margin:0px 0px 0px 0px;">
		  			<li style="text-align:right;font-size:13px;font-weight:bold;color:red;height:30px;line-height:30px;padding-right:10px;border:1px solid #cccccc;border-bottom:0px;">
		  				&nbsp;
		  			</li>
		  		</ul>
	  			<table style="width:100%;font-size:13px;text-align:center;" border=0 cellpadding="0"  cellspacing="0">
		  			<tr style="background:#f8f8f8;font-weight:bold;">
		  				<td style="height:30px;border:1px solid #cccccc;border-right:0px;width:60px;">违章ID</td>
		  				<td style="height:30px;border:1px solid #cccccc;border-right:0px;width:60px;">车牌号</td>
		  				<td style="height:30px;border:1px solid #cccccc;border-right:0px;">违章地点</td>
		  				<td style="height:30px;border:1px solid #cccccc;border-right:0px;width:80px;">违章时间</td>
		  				<td style="height:30px;border:1px solid #cccccc;border-right:0px;width:80px;">本金(元)</td>
		  				<td style="height:30px;border:1px solid #cccccc;border-right:0px;width:80px;">滞纳金(元)</td>
		  				<td style="height:30px;border:1px solid #cccccc;border-right:0px;width:80px;">代办费(元)</td>
		  				<td style="height:30px;border:1px solid #cccccc;border-right:0px;width:80px;">总额(元)</td>
		  				<td style="height:30px;border:1px solid #cccccc;border-right:0px;width:80px;">是否可办</td>
		  				<td style="height:30px;border:1px solid #cccccc;width:150px;" colspan=2>
		  					<input type="checkbox" onclick="funClears(this)" id="qck"/>&nbsp;操作
		  				</td>
		  			</tr>
		  			<c:forEach items="${arrylist}" var="li" varStatus="index">
		  				<tr style="font-size:12px;">
		  					<td style="height:25px;border:1px solid #cccccc;border-top:0px;border-right:0px">${li.id}</td>
			  				<td style="height:25px;border:1px solid #cccccc;border-top:0px;border-right:0px">${li.plateNumber}</td>
			  				<td style="height:25px;border:1px solid #cccccc;border-top:0px;border-right:0px">${li.pecAddr}</td>
			  				<td style="height:25px;border:1px solid #cccccc;border-top:0px;border-right:0px">${li.pecDate }</td>
			  				<td style="height:25px;border:1px solid #cccccc;border-top:0px;border-right:0px" id="${li.id}_corpus">${li.corpus }</td>
			  				<td style="height:25px;border:1px solid #cccccc;border-top:0px;border-right:0px">${li.new_LateFee }</td>
			  				<td style="height:25px;border:1px solid #cccccc;border-top:0px;border-right:0px">${li.myPoundage}</td>
			  				<td style="height:25px;border:1px solid #cccccc;border-top:0px;border-right:0px">
		  						${li.corpus+li.new_LateFee+li.myPoundage }
		  						<input type="hidden" id="${li.id}_money" value="${li.corpus+li.new_LateFee+li.myPoundage}"/>
			  				</td>
			  				<td style="height:25px;border:1px solid #cccccc;border-top:0px;border-right:0px">
			  					<c:if test="${li.isdaibai==1}">
			  						是
			  					</c:if>
			  					<c:if test="${li.isdaibai!=1}">
			  						否
			  					</c:if>
			  				</td>
			  				<td style="height:25px;border:1px solid #cccccc;border-right:0px;border-top:0px;text-align:right;width:55px;">
			  					<c:if test="${li.isdaibai==1}">
			  						<input type="checkbox" id="${li.id}_A" name="infos" style="width:14px;visibility:hidden;" value="${li.id}#${li.pecNum}#${li.plateNumber}#${li.pecCode}#${li.pecDesc}#${li.pecAddr}#${li.pecDate}#${li.pecScore}#${li.corpus}#${li.lateFee}#${li.replaceMoney}#${li.agent}#${li.pecState}#${li.pecChanl}#${li.createDate}#${li.updateDate}#${li.updateWorkerNo}#${li.woState}#${li.areaCode}#${li.illegalType}#${li.myPoundage}#${li.new_LateFee}"/>
			  						<input type="checkbox" value="${li.id}" name="ck" onclick="funaa(this)" style="width:14px;"/>
			  					</c:if>
			  					<c:if test="${li.isdaibai!=1}">
			  						<input type="checkbox" value="" disabled/>
			  					</c:if>
			  				</td>
			  				<td style="height:25px;border:1px solid #cccccc;border-left:0px;border-top:0px">
			  					<a  onclick="funshowInfo(event,'${li.plateNumber}','${li.pecDate }','${li.pecAddr}','${li.pecDesc}',${li.corpus},${li.new_LateFee},0,${li.pecScore},${li.myPoundage})" style="font-size:12px;cursor:hand;color:red;" >详细</a>
			  				</td>
			  			</tr>
		  			</c:forEach>
		  		</table>
		  		<!-- interfaceType=1 表示电信 -->
		  	<c:if test="${interfaceType==1}">
			  	<ul style="padding:0px 0px 0px 0px;">
		  			<li style="text-align:center;">
		  				车主姓名:<input type="text" value="" style="width:190px;height:30px;font-size:20px;" id="userNameId" name=""/>
		  				&nbsp;
		  				联系电话:<input type="text" value="" style="width:190px;height:30px;font-size:20px;" maxlength=13 id="catUserPhoneId" name="" onkeyup="value=value.replace(/[^\d]/g,'') "/>
		  				&nbsp;
		  				联系地址:<input type="text" value="" style="width:190px;height:30px;font-size:20px;" maxlength=100 id="catUserAddress" name="" />
		  			</li>
		  		</ul>
	  		</c:if>
  			<ul style="padding:0px 0px 0px 0px;margin-top:20px;">
	  			<li style="text-align:center;">
	  				<c:if test="${userSession.usersite!=382}">
			  			<input onclick="funget(this)" type="button" style="width:85px; height:32px; border:0px;color:#fff; font-weight:bold;background:url(<%=path%>/images/button_out.png) no-repeat bottom; cursor:pointer;" value="下单" id="xiadans"/>
									&nbsp;
					</c:if>
					<input onclick="javascript:window.location.href='<%=path %>/business/catmanage.jsp'" type="button" style="width:85px; height:32px; border:0px;color:#fff; font-weight:bold;background:url(<%=path%>/images/button_out.png) no-repeat bottom; cursor:pointer;" value="返回"/>
	  			</li>
	  		</ul>
	  	</div>
		<input type="hidden" value="${catmessage}" name="catmessage"/>
		<input type="hidden" value="" name="sumMoney" id="sumMoneyId"/>
		
		<input type="hidden" value="${catUserName}" name="catUserName" id="h_catUserName"/>
		<input type="hidden" value="${catUserPhone}" name="catUserPhone" id="h_catUserPhone"/>
		<input type="hidden" value="${carAddress}" name="carAddress" id="h_carAddress"/>
		<input type="hidden" value="${interfaceType}" name="interfaceType"/>
		<input type="hidden" value="${carNameText}" name="carNameText"/>
	  </form>
	  <!-- 详细 -->
	  <div style="display:none;border:1px solid #f8f8f8;width:180px;position:absolute;left:0px;top:0px;background-color:#FFFFE5;border:1px solid #FF9E4d;" id="showInfoMessage"></div> 
	  
	  <!-- 中石化下单提示 -->
<div  id="light" class="white_content" style="background-color:white;height:250px;width:500px;border-radius:10px;">
<div style="width: auto;height:99%;">
<table  style="height:98%;width:100%;">
	<thead  style="height:30px;">
		<tr style="background-color:#e5e9f3">
			<th style="text-align:left;font-size:14px;padding-left:20px;">
				温馨提示
			</th>
		</tr>
	</thead>
	<tbody >
		<tr>
			<td id="showMM" style="color:red;font-size:18px;font-weight:bold;text-align:center;border-bottom:1px solid #cccccc;height:40px;line-height:40px;"></td>
		</tr>
		<tr>
			<td id="showMMImg" style="color:red;font-size:18px;font-weight:bold;text-align:left;padding-left:15px;border-bottom:1px solid #cccccc;height:80px;line-height:80px;padding-top:10px;">
			</td>
		</tr>
		<tr>
			<td id="showMMTow" style="color:red;font-size:18px;font-weight:bold;text-align:left;border-bottom:1px solid #cccccc;height:40px;line-height:40px;padding-left:20px;"></td>
		</tr>
		<tr>
			<td align="center" >
				<div style="float:right;margin-top:8px;">
					 <input type="button" id="wltfarmebuttonvalue" name="Button" value="&nbsp;确&nbsp;定&nbsp;" style="border:0px; width:85px; height:32px; color:#fff; font-weight:bold; background:url(<%=path %>/images/button_out.png) no-repeat bottom; cursor:pointer;" onclick="funbuttonSubmit()"/>
				&nbsp;
					<input type="button" name="Button" value="&nbsp;取&nbsp;消&nbsp;" style="border:0px; width:85px; height:32px; color:#fff; font-weight:bold; background:url(<%=path %>/images/button_out.png) no-repeat bottom; cursor:pointer;" onclick="funCloseDiv()"/>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				</div>
			</td>
		</tr>
	</tbody>
</table>
</div>
</div>
<div id="fade" class="black_overlay"></div> 
<br/>
	<div style="padding-left:40px;padding-top:10px;font-size: 13px; color: #FF6600;font-weight:bold;border:1px solid #FF9E4d;background-color: #FFFFE5;">
	<ul>
	<li style="list-style:none;font-size:18px;">违章代办手续费建议用户在进价基础上每单加收5-15元。</li>
	<li style="list-style:none;">&nbsp;</li>
	</ul>
	</div>
  </body>
</html>
