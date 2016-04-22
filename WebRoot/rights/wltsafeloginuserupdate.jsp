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
		<title>���ͨ</title>
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
			  				alert("������mac��ַ!");
			  				return ;
			  			}
			  		}
			  		//r sResult=prompt("�������뽻������", "");
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

			// ȥ�ո�
			String.prototype.Trim = function() 
			{ 
				return this.replace(/(^\s*)|(\s*$)/g, ""); 
			} 
 
			function validatePWD(){
			  var pwd=document.getElementById("pwd").value;
			  if(pwd==""||pwd==null){
			    	alert("�������벻��Ϊ��");
			  	    return false;
			  }
			  document.getElementById("vall").value="��ѯ��...";
			  document.getElementById("vall").disabled="disabled";
			  $.post(
				 	$("#rootPath").val()+"/rights/sysuser.do?method=validatePWD",
				   	{
				   	userpwd:pwd
				   	},
				   	function (data){
				   	document.getElementById("vall").value="���뽻�����������в鿴";
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
				alert("�����뽻������");
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
						��ȫ��¼ѡ��
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
							<strong>��½�ʺţ�</strong><span>${userSession.username}</span>
						</li>
						<li>
							<strong>�����֤�ֻ��ţ�</strong><span>${userSession.username }</span>
						</li>
						<li>
							<strong>�ֻ�������Կ��</strong><span><input type="password"
									value="" id="pwd" style="height: 25px;" /> <input id="vall"
									onclick="validatePWD();" type="button" value="���뽻�����������в鿴"
									style="width: 180px; color: blue; height: 25px;" /> </span>
						</li>
						<li>
							<strong>&nbsp;</strong>
							<span id="miyao"
								style="display: none; color: red; font-size: 16px;">${googlesign}</span>
							<span id="tishi"
								style="display: none; color: red; font-size: 16px;">�����������</span>
						</li>
						<li>
							<span style="color: red; margin-left: 100px;"> ��ͨ�ֻ�����
								(���֤��֤��) �����ֻ������ز������ֻ����������˵����<a href="<%=path%>/help/google.jsp">�ֻ����Ƽ�����</a>��
							</span>
						</li>
						<li>
							<strong>���ת����֤��ʽ��</strong><span> <select
									name="feeshortflag" id="feeshortflag">
									<option value="0"
										<c:if test="${userSession.feeshortflag == 0 }">selected</c:if>>
										����֤
									</option>
									<option value="1"
										<c:if test="${userSession.feeshortflag == 1 }">selected</c:if>>
										������֤
									</option>
									<option value="2"
										<c:if test="${userSession.feeshortflag == 2 }">selected</c:if>>
										�ֻ�����
									</option>
								</select> </span>
						</li>
						<li>
							<strong>��ȫ��¼��֤��ʽ��</strong><span> <select name="shortflag"
									id="shortflag">
									<option value="0"
										<c:if test="${userSession.shortflag == 0 }">selected</c:if>>
										����֤
									</option>
									<option value="1"
										<c:if test="${userSession.shortflag == 1 }">selected</c:if>>
										������֤
									</option>
									<option value="2"
										<c:if test="${userSession.shortflag == 2 }">selected</c:if>>
										�ֻ�����
									</option>
								</select> </span>
						</li>


						<li>
							<strong>��ֵ�Ƿ���Ҫ�������룺</strong><span> <select name="czflag"
									id="czflag">
									<option value="0"
										<c:if test="${cz == 0 }">selected</c:if>>
										����Ҫ
									</option>
									<option value="1"
										<c:if test="${cz == 1 }">selected</c:if>>
										��Ҫ
									</option>
								</select> </span>
						</li>



						<li>
							<strong>�Ƿ��MAC��ַ��</strong><span> <select name="mac"
									id="mac" onchange="funCC(this)">
									<option value="0" <c:if test="${state == 0 }">selected</c:if>>
										��
									</option>
									<option value="1" <c:if test="${state != 0 }">selected</c:if>>
										����
									</option>
								</select> </span>
						</li>
						<li>
							<span style="color: red; margin-left: 100px;">
								(��ѡ��mac��ַ��֤,��ʹ��IE8,IE9,IE10,IE11���������,������IE�������ȫ����Ϊ���,�����´ε�½��֤��ʧ�ܣ�)
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
						<strong>��ǰϵͳ��ȡ��mac��ַ��</strong>
						<input id="txtMACAddr" name="txtMACAddr" type="text" value=""
							style="width: 180px;" disabled />
						<div style="color: red; width: 100px; display: inline;">
							&nbsp;(�������������)
						</div>
					</li>
					<li>
						<strong>����mac��ַһ:</strong>
						<input type="text" name="macBY" value="${macList[0] }"
							style="width: 180px;" />
					</li>
					<li>
						<strong>����mac��ַ��:</strong>
						<input type="text" name="macBY" value="${macList[1] }"
							style="width: 180px;" />
					</li>
					<li>
						<strong>����mac��ַ��:</strong>
						<input type="text" name="macBY" value="${macList[2] }"
							style="width: 180px;" />
					</li>
					<li>
						<strong>����mac��ַ��:</strong>
						<input type="text" name="macBY" value="${macList[3] }"
							style="width: 180px;" />
					</li>
					<li>
						<strong>����mac��ַ��:</strong>
						<input type="text" name="macBY" value="${macList[4] }"
							style="width: 180px;" />
					</li>
					</ul>
				</div>
				<div class="field-item_button_m">
					<input type="button" value="ȷ��" id="checkCode" onclick="check()"
						class="field-item_button" />
					<input type="button" value="����" id="btnBack"
						onclick="javascript:history.go(-1);" class="field-item_button" />
				</div>

				<div id="light" class="white_content"
					style="padding: 10px 10px 10px 10px;">
					<ul>
						<li
							style="text-align: left; padding-top: 5px; padding-bottom: 5px;">
							<span style="font-size: 16px; color: #4c89d2">�����뽻������:</span>
						</li>
						<li
							style="text-align: right; padding-top: 5px; padding-bottom: 5px;">
							<input type="password" id="tradepwd" value="" />
						</li>
						<li
							style="text-align: center; padding-top: 5px; padding-bottom: 5px;">
							<a href="javascript:void(0)" style="font-size: 18px;"
								onclick="con('0')">ȷ��</a>&nbsp;&nbsp;
							<a href="javascript:void(0)" style="font-size: 18px;"
								onclick="con('1')">ȡ��</a>
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