<%@ page contentType="text/html; charset=gb2312" language="java"%>
<%@page import="java.util.List"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<jsp:useBean id="userSession" scope="session"
	class="com.wlt.webm.rights.form.SysUserForm" />
<jsp:useBean id="user" scope="page"
	class="com.wlt.webm.rights.bean.SysUser" />
<jsp:useBean id="role" scope="page"
	class="com.wlt.webm.rights.bean.SysRole" />
<%
	String path = request.getContextPath();
	String userid = userSession.getUserno();
	request.setAttribute("userinfo", user.getUserInfo(userid));

	List arryList = role.getMac(userid);
	Object[] obj = (Object[]) arryList.get(0);
	if (obj[0] != null && obj[0].toString().indexOf("#") != -1) {
		obj[0] = obj[0].toString().replace("#", "  ");
	}
	request.setAttribute("macSign", obj);
	//	 List areaList = role.getAreaList();
	// StringBuffer sBuffer = new StringBuffer();
	// sBuffer.append("请选择[]");
	// for(Object tmp : areaList){
	//	String[] temp = (String[])tmp;
	//	sBuffer.append("|"+temp[1]+"["+temp[0]+"]");
	// }
	// request.setAttribute("areaSel", sBuffer.toString());
%>
<html>
	<head>
		<title>万汇通</title>
		<META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=gb2312">
		<script type="text/javascript" src="../js/jquery-1.8.2.min.js"></script>
		<link href="../css/main_style.css" rel="stylesheet" type="text/css" />
		<link href="../css/selectCss.css" rel="stylesheet" type="text/css" />
		<script language="javascript" type="text/javascript"
			src="../My97DatePicker/WdatePicker.js"></script>
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
<SCRIPT type=text/javascript>
jQuery(function(){
	if($("#mess").val() != ""){
		alert($("#mess").val());
	}
});
function submitForm(){
  	with(document.forms[0]){
  	    if(userename.value==""){
	  	    alert("用户姓名不能为空");
	  	    return false;
  	    }
  	    if(usermail.value==""){
	  	    alert("邮箱不能为空");
	  	    return false;
  	    }
  	    if(phone.value==""){
	  	    alert("联系电话不能为空");
	  	    return false;
  	    }
	   	document.getElementById('light').style.display='block';
	  	document.getElementById('fade').style.display='block';
	  	return false;
	}
}

function con(a){
	if(a=='0'){
		var flag=document.getElementById('tradepwd').value;
		if(flag==null||flag==""){
			alert("请输入交易密码");
		}else{
		 document.getElementById('tradepassword').value=flag;
		 document.getElementById('light').style.display='none';
		 document.getElementById('fade').style.display='none';
		 document.forms[0].action=$("#rootPath").val()+"/rights/sysuser.do?method=update";
		 target="_self";
		 document.forms[0].submit();
		 return ;
	 	}
	 }else{
		 document.getElementById('light').style.display='none';
		 document.getElementById('fade').style.display='none';
		 return ;
	 }
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
</SCRIPT>
	</head>
	<body>
		<div id="popdiv">
			<span id="sellist"></span>
			<div style="clear: both"></div>
		</div>
		<div id="tabbox">
			<div id="tabs_title">
				<ul class="tabs_wzdh">
					<li>
						用户信息
					</li>
				</ul>
			</div>
		</div>
		<html:form method="post" action="/rights/sysuser.do?method=update">
			<input type="hidden" name="userno" value="${userinfo.userno }" />
			<input type="hidden" id="mess" value="${requestScope.mess }" />
			<input type="hidden" id="rootPath" value="<%=path%>" />
			<input type="hidden" name="rdurl" value="/rights/wltusermodify.jsp" />
			<div class="pass_main">
				<div class="pass_list">
					<ul>
						<li>
							<strong>登陆帐号：</strong><span>${userinfo.username }</span>
						</li>
						<li>
							<strong>系统编号：</strong><span>${userinfo.userno }</span>
						</li>
						<li>
							<strong>资金账号：</strong><span>${userinfo.fundacct }</span>
						</li>
						<li>
							<strong>账户状态：</strong><span> <c:if
									test="${userinfo.userstatus == 0 }">
           正常
            </c:if> <c:if test="${userinfo.userstatus == 1 }">
           销户
            </c:if> <c:if test="${userinfo.userstatus == 2 }">
           暂停服务
            </c:if> </span>
						</li>
						<!--            <li><strong>区域：</strong><span>-->
						<!--            	<div class="sub-input"><a class="sia-2 selhover" id="sl-usersite" href="javascript:void(0);">请选择</a></div>-->
						<!--				<input nname="nname" name="usersite" id="usersite" defaultsel="${requestScope.areaSel }" type="hidden" value="${userinfo.usersite }" />-->
						<!--            </span></li>-->
						<li>
							<strong>用户姓名：</strong>
							<input name="userename" value="${userinfo.userename }" />
						</li>
						<li style="display: none">
							<strong>创建时间：</strong><span>${userinfo.usercreatedate }</span>
						</li>
						<li>
							<strong>电子邮箱：</strong>
							<input name="usermail" value="${userinfo.usermail }" />
						</li>
						<li>
							<strong>联系电话：</strong>
							<input name="phone" value="${userinfo.phone }" />
							</span>
						</li>
						<li>
							<strong>身份证：</strong>
							<input readonly="readonly" name="usersf"
								value="${userinfo.usersf }" />
							</span>
						</li>
						<li>
							<strong>mac绑定地址：</strong>
							<span> <c:if test="${macSign[2]==0}">
             			${macSign[0]}
             		</c:if> <c:if test="${macSign[2]!=0}">
             			未绑定
             		</c:if> </span>
						</li>
						<li>
							<strong>手机令牌密钥：</strong><span><input type="password"
									value="" id="pwd" style="height: 25px;" /> <input id="vall"
									onclick="validatePWD();" type="button" value="输入交易密码点击进行查看"
									style="width: 180px; color: blue; height: 25px;" />
							</span>
						</li>
						<li>
							<strong>&nbsp;</strong>
							<span id="miyao"
								style="display: none; color: blue; font-size: 16px;">${macSign[1]}</span>
							<span id="tishi"
								style="display: none; color: blue; font-size: 16px;">交易密码错误</span>
						</li>
						<!-- 
             <li>
             	<strong>注册验证图片：</strong>
             	<span>
	             	<c:if test="${macSign[3]==1}">
	             		<img alt="" src="<%=path%>/AccountInfo/showAccountInfo.do?method=getImg" style=""/>
	             	</c:if>
	             	<c:if test="${macSign[3]==0}">
	             		无验证图片
	             	</c:if>
             	</span>
             </li>
              -->
					</ul>
				</div>
				<div class="field-item_button_m">
					<input type="button" value="修改" id="btnSubmit"
						onclick="submitForm();" class="field-item_button" />
					<input type="button" value="取消" id="checkCode"
						onClick="javascript:history.go(-1)" class="field-item_button" />
				</div>
			</div>
			
			<div id="light" class="white_content" style="padding: 10px 10px 10px 10px;">
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
			<input type="hidden" id="tradepassword" name="tradepassword" value="" />
		</html:form>
	</body>
</html>