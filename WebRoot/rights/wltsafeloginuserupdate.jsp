<%@ page contentType="text/html; charset=GBK" language="java"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@page import="java.util.List"%>
<jsp:useBean id="userSession" scope="session"
	class="com.wlt.webm.rights.form.SysUserForm" />
<jsp:useBean id="user" scope="page"
	class="com.wlt.webm.rights.bean.SysLoginUser" />
<jsp:useBean id="prod" scope="page"
	class="com.wlt.webm.business.bean.BiProd" />
<%
	String path = request.getContextPath();
	String userName = userSession.getUsername();

	String state = prod.getMacState(userSession.getUserno());
	request.setAttribute("state", state);

	String[] MacStr = prod.getMac(userSession.getUserno());
	Object[] obj = null;
	if (MacStr == null || MacStr[0] == null || "".equals(MacStr[0])) {
		//obj=new Object[]{"","","","",""};
	} else {
		obj = MacStr[0].split("#");
	}
	request.setAttribute("googlesign", MacStr[1]);
	request.setAttribute("macList", obj);
	request.setAttribute("cz", MacStr[2]);
%>
<html>
	<head>
		<title>万汇通</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta name="generator" content="editplus" />

		<script type="text/javascript" src="../js/jquery-1.8.2.min.js"></script>
		<script type="text/javascript" src="../js/util.js"></script>
		<link href="../css/main_style.css" rel="stylesheet" type="text/css" />
		<SCRIPT type=text/javascript>
			showMessage("${mess}");
			function check(){
			  	with(document.forms[0]){
			  		if(document.getElementById("mac").value==0)
			  		{
			  			var flag=false;
			  			var arr=document.getElementsByName("macBY");
			  			for(var i=0;i<arr.length;i++)
			  			{
			  				if(arr[i].value.Trim()!="")
			  				{
			  					flag=true;
			  					break;
			  				}
			  			}
			  			if(flag==false)
			  			{
			  				alert("请输入mac地址!");
			  				return ;
			  			}
			  		}
			  		//r sResult=prompt("请在输入交易密码", "");
			  		document.getElementById('light').style.display='block';
			  		document.getElementById('fade').style.display='block';
				}
			}

			function funCC(className)
			{
				if(className.value==0)
				{
					document.getElementById("uldisplay").style.display="block";
				}
				else
				{
				document.getElementById("uldisplay").style.display="none";
				}
			}	

			// 去空格
			String.prototype.Trim = function() 
			{ 
				return this.replace(/(^\s*)|(\s*$)/g, ""); 
			} 
 
			function validatePWD(){
			  var pwd=document.getElementById("pwd").value;
			  if(pwd==""||pwd==null){
			    	alert("交易密码不能为空");
			  	    return false;
			  }
			  document.getElementById("vall").value="查询中...";
			  document.getElementById("vall").disabled="disabled";
			  $.post(
				 	$("#rootPath").val()+"/rights/sysuser.do?method=validatePWD",
				   	{
				   	userpwd:pwd
				   	},
				   	function (data){
				   	document.getElementById("vall").value="输入交易密码点击进行查看";
				   	 document.getElementById("vall").disabled="";
				   		if(data == 0){
				   			document.getElementById("pwd").value="";
				   			document.getElementById("miyao").style.display="block";
				   			document.getElementById("tishi").style.display="none";
				   		}else{
				   			document.getElementById("pwd").value="";
				   			document.getElementById("miyao").style.display="none";
				   			document.getElementById("tishi").style.display="block";
				   		}
				   	}
				)
			}
 
	 function con(a){
		if(a=='0'){
			var flag=document.getElementById('tradepwd').value;
			if(flag==null||flag=="")
			{
				alert("请输入交易密码");
			}else{
			 document.getElementById('tradepassword').value=flag;
			 document.getElementById('light').style.display='none';
			 document.getElementById('fade').style.display='none';
			 document.forms[0].action="sysloginuser.do?method=updateSafe";
			 target="_self";
			 document.forms[0].submit();
		 	}
		 }else{
			 document.getElementById('light').style.display='none';
			 document.getElementById('fade').style.display='none';
		 }
   }
</SCRIPT>

<style type="text/css">
.black_overlay {
	display: none;
	position: absolute;
	top: 0%;
	left: 0%;
	width: 100%;
	height: 100%;
	background-color: black;
	z-index: 1001;
	-moz-opacity: 0.8;
	opacity: .80;
	filter: alpha(opacity =     88);
}
.white_content {
	display: none;
	position: absolute;
	top: 25%;
	left: 30%;
	width: 25%;
	border: 2px solid orange;
	background-color: white;
	z-index: 1002;
	overflow: auto;
}
</style>
	</head>
	<body>
		<div id="tabbox">
			<div id="tabs_title">
				<ul class="tabs_wzdh">
					<li>
						安全登录选择
					</li>
				</ul>
			</div>
		</div>
		<input type="hidden" id="rootPath" value="<%=path%>" />
		<html:form method="post"
			action="/rights/sysloginuser.do?method=updateSafe">
			<input type="hidden" name="userno" value="${userSession.userno}" />
			<input type="hidden" id="rootPath1" name="userid"
				value="${userSession.user_id}" />
			<div class="pass_main">

				<div class="pass_list">
					<ul>
						<li>
							<strong>登陆帐号：</strong><span>${userSession.username}</span>
						</li>
						<li>
							<strong>身份验证手机号：</strong><span>${userSession.username }</span>
						</li>
						<li>
							<strong>手机令牌密钥：</strong><span><input type="password"
									value="" id="pwd" style="height: 25px;" /> <input id="vall"
									onclick="validatePWD();" type="button" value="输入交易密码点击进行查看"
									style="width: 180px; color: blue; height: 25px;" /> </span>
						</li>
						<li>
							<strong>&nbsp;</strong>
							<span id="miyao"
								style="display: none; color: red; font-size: 16px;">${googlesign}</span>
							<span id="tishi"
								style="display: none; color: red; font-size: 16px;">交易密码错误</span>
						</li>
						<li>
							<span style="color: red; margin-left: 100px;"> 开通手机令牌
								(身份证验证器) 需在手机上下载并配置手机令牌软件，说明【<a href="<%=path%>/help/google.jsp">手机令牌见帮助</a>】
							</span>
						</li>
						<li>
							<strong>额度转移验证方式：</strong><span> <select
									name="feeshortflag" id="feeshortflag">
									<option value="0"
										<c:if test="${userSession.feeshortflag == 0 }">selected</c:if>>
										不验证
									</option>
									<option value="1"
										<c:if test="${userSession.feeshortflag == 1 }">selected</c:if>>
										短信验证
									</option>
									<option value="2"
										<c:if test="${userSession.feeshortflag == 2 }">selected</c:if>>
										手机令牌
									</option>
								</select> </span>
						</li>
						<li>
							<strong>安全登录验证方式：</strong><span> <select name="shortflag"
									id="shortflag">
									<option value="0"
										<c:if test="${userSession.shortflag == 0 }">selected</c:if>>
										不验证
									</option>
									<option value="1"
										<c:if test="${userSession.shortflag == 1 }">selected</c:if>>
										短信验证
									</option>
									<option value="2"
										<c:if test="${userSession.shortflag == 2 }">selected</c:if>>
										手机令牌
									</option>
								</select> </span>
						</li>


						<li>
							<strong>充值是否需要交易密码：</strong><span> <select name="czflag"
									id="czflag">
									<option value="0"
										<c:if test="${cz == 0 }">selected</c:if>>
										不需要
									</option>
									<option value="1"
										<c:if test="${cz == 1 }">selected</c:if>>
										需要
									</option>
								</select> </span>
						</li>



						<li>
							<strong>是否绑定MAC地址：</strong><span> <select name="mac"
									id="mac" onchange="funCC(this)">
									<option value="0" <c:if test="${state == 0 }">selected</c:if>>
										绑定
									</option>
									<option value="1" <c:if test="${state != 0 }">selected</c:if>>
										不绑定
									</option>
								</select> </span>
						</li>
						<li>
							<span style="color: red; margin-left: 100px;">
								(若选择mac地址验证,请使用IE8,IE9,IE10,IE11浏览器操作,并设置IE浏览器安全级别为最低,否则下次登陆验证将失败！)
							</span>
						</li>
					</ul>
					<c:if test="${state == 0 }">
						<ul id="uldisplay">
					</c:if>
					<c:if test="${state != 0 }">
						<ul id="uldisplay" style="display: none;">
					</c:if>
					<li>
						<strong>当前系统获取的mac地址：</strong>
						<input id="txtMACAddr" name="txtMACAddr" type="text" value=""
							style="width: 180px;" disabled />
						<div style="color: red; width: 100px; display: inline;">
							&nbsp;(此条不保存入库)
						</div>
					</li>
					<li>
						<strong>备用mac地址一:</strong>
						<input type="text" name="macBY" value="${macList[0] }"
							style="width: 180px;" />
					</li>
					<li>
						<strong>备用mac地址二:</strong>
						<input type="text" name="macBY" value="${macList[1] }"
							style="width: 180px;" />
					</li>
					<li>
						<strong>备用mac地址三:</strong>
						<input type="text" name="macBY" value="${macList[2] }"
							style="width: 180px;" />
					</li>
					<li>
						<strong>备用mac地址四:</strong>
						<input type="text" name="macBY" value="${macList[3] }"
							style="width: 180px;" />
					</li>
					<li>
						<strong>备用mac地址五:</strong>
						<input type="text" name="macBY" value="${macList[4] }"
							style="width: 180px;" />
					</li>
					</ul>
				</div>
				<div class="field-item_button_m">
					<input type="button" value="确认" id="checkCode" onclick="check()"
						class="field-item_button" />
					<input type="button" value="返回" id="btnBack"
						onclick="javascript:history.go(-1);" class="field-item_button" />
				</div>

				<div id="light" class="white_content"
					style="padding: 10px 10px 10px 10px;">
					<ul>
						<li
							style="text-align: left; padding-top: 5px; padding-bottom: 5px;">
							<span style="font-size: 16px; color: #4c89d2">请输入交易密码:</span>
						</li>
						<li
							style="text-align: right; padding-top: 5px; padding-bottom: 5px;">
							<input type="password" id="tradepwd" value="" />
						</li>
						<li
							style="text-align: center; padding-top: 5px; padding-bottom: 5px;">
							<a href="javascript:void(0)" style="font-size: 18px;"
								onclick="con('0')">确定</a>&nbsp;&nbsp;
							<a href="javascript:void(0)" style="font-size: 18px;"
								onclick="con('1')">取消</a>
						</li>
					</ul>
				</div>
				<div id="fade" class="black_overlay"></div>
				<input type="hidden" id="tradepassword" name="tradepassword"
					value="" />
			</div>
		</html:form>
		<div
			style="visibility: hidden; width: 100px; height: 300px; border: 1px solid red;"></div>
	</body>

<SCRIPT language="JavaScript"> 
 function MacInfo(){
       var locator =new ActiveXObject ("WbemScripting.SWbemLocator");
       var service = locator.ConnectServer(".");
       var properties = service.ExecQuery("Select * from Win32_NetworkAdapterConfiguration Where IPEnabled =True");
       var e =new Enumerator (properties);
       {
             var p = e.item();
            var mac = p.MACAddress;
           // alert(mac)
           document.getElementById("txtMACAddr").value=mac;
       }
 }
 MacInfo();
</SCRIPT>
</html>