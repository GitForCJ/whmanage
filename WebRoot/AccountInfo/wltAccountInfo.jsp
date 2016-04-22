<%@ page contentType="text/html; charset=gb2312" language="java"%>
<%@page import="java.util.List"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<jsp:useBean id="userSession" scope="session" class="com.wlt.webm.rights.form.SysUserForm" />
<jsp:useBean id="user" scope="page" class="com.wlt.webm.rights.bean.SysUser"/>
<jsp:useBean id="role" scope="page" class="com.wlt.webm.rights.bean.SysRole"/>
 <%
 	 String path = request.getContextPath();
 	 if(null==userSession||userSession.getUsername()==null){
       response.sendRedirect(path+"/rights/wltlogin.jsp");
	}
 	 
	 String userid=userSession.getUserno();
   	 request.setAttribute("userinfo", user.getUserInfo(userid));
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
<script type="text/javascript" src="../js/util.js"></script>
<SCRIPT src="../js/more_tiaojian.js" type=text/javascript></SCRIPT>
<script language='javascript' src='../js/jquery.js'></script>
<link href="../css/main_style.css" rel="stylesheet" type="text/css" />
<link href="../css/selectCss.css" rel="stylesheet" type="text/css" /> 
<script language="javascript" type="text/javascript" src="../My97DatePicker/WdatePicker.js"></script>
<SCRIPT type=text/javascript>
	 function submitForm()
	 {
	 	if($("#bankarea").val()==-1)
	 	{
	 		alert("请选择区域？");
	 		$("#bankarea").focus();
	 		return ;
	 	}
	 	if($("#bankcode").val()==-1)
	 	{
	 		alert("请选择开户银行?");
	 		$("#bankcode").focus();
	 		return ;
	 	}
	 	
	 	if($("#bankinfo").val().Trim().length<=0)
	 	{
	 		alert("请输入开户行名称?");
	 		$("#bankinfo").focus();
	 		return ;
	 	}
	 	
	 	if($("#accname").val().Trim().length<=0)
	 	{
	 		alert("请输入银行账户名称?");
	 		$("#accname").focus();
	 		return ;
	 	}
	 	
	 	//checkbankcard($("#ankacct").val().Trim());//验证银行账户
	 	if($("#ankacct").val().Trim().length<=0)
	 	{
	 		alert("请输入银行账户?");
	 		$("#ankacct").focus();
	 		return ;
	 	}

		if($("#certno").val().Trim().length<=0)
		{
			alert("请输入证件号?");
	 		$("#certno").focus();
	 		return ;
		}
		
		//checkPhoneNumber($("#contactphone").val().Trim());//验证 电话
		if($("#contactphone").val().Trim().length<=0)
		{
			alert("请输入电话号?");
	 		$("#contactphone").focus();
	 		return ;
		}
	 	
	 	//checkAddress($("#contactaddr").val().Trim()); //验证 联系人地址 
	 	if($("#contactaddr").val().Trim().length<=0)
	 	{
	 		alert("请输入联系地址?");
	 		$("#contactaddr").focus();
	 		return ;
	 	}
	 	
	 	document.getElementById("btnSubmit").value="录入中,,,";
	 	document.getElementById("btnSubmit").disabled=true;
	 	document.forms[0].submit();
	 	//return true;
	 }
	 
// 去空格
String.prototype.Trim = function() 
{ 
	return this.replace(/(^\s*)|(\s*$)/g, ""); 
} 
 
 /**
if("${ts}"=="0")
{
	alert("操作成功,审核通过！");
	window.location.href="<%=path%>/AccountInfo/showAccountInfo.do?method=showAccountInfo";
}
if("${ts}"=="1" || "${ts}"=="2" || "${ts}"=="3")
{
	alert("账户信息录入成功,验证审核失败！");
	window.location.href="<%=path%>/AccountInfo/showAccountInfo.do?method=showAccountInfo";
}
*/
if("${ts}"=="4")
{
	alert("账户信息录入成功,未审核！");
	window.location.href="<%=path%>/AccountInfo/showAccountInfo.do?method=showAccountInfo";
}
if("${ts}"=="-2")
{
	alert("账户信息录入失败！");
	window.location.href="<%=path%>/AccountInfo/showAccountInfo.do?method=showAccountInfo";
}

//账号按下键时
function keyup(obj)
{
	var lastchar=obj.value.substring(obj.value.length-1,obj.value.length);
	
	if(!isDigit(lastchar))
	{
		obj.value=obj.value.substring(0,obj.value.length-1);
	}

	if(obj.value.length==4 || obj.value.length==9 || obj.value.length==14 || obj.value.length==19)
	{
		obj.value=obj.value+" ";
	}
}


/*
//账号 按下键释放
function keypress()
{
	if(event.keyCode>=48 && event.keyCode<=57)
	{
		
	}
	else
	{
		alert($("#ankacct").val());
		//$("#ankacct").val("");
	}
}
*/
</SCRIPT>
</head>
<body>
<div id="popdiv"><span id="sellist"></span><div style="clear:both"></div></div>
<div id="tabbox">
  <div id="tabs_title">
    <ul class="tabs_wzdh">
      <li>账户信息录入</li></ul>
  </div>
</div>
<html:form  method="post" action="/AccountInfo/showAccountInfo.do?method=saveAccountInfo">
<input type="hidden" name="rdurl" value="/rights/wltusermodify.jsp"/>
<div class="pass_main">
    <div class="pass_list">
    	<ul>
            <li>
            	<strong>银行卡归属地区：</strong>
            	<select  style="font-size: 20px;height: 30px;width:260px;color: blue;"  id="bankarea" name="bankarea">
            		<option value="-1">-- 请选择 --</option>
            		<c:forEach items="${regionalcode}" var="regionalcode">
            			<option value="${regionalcode.regionalcoeid}">${regionalcode.regionoalcodename}</option>
            		</c:forEach>
            	</select>
            </li>
            <li>
            	<strong>开户银行：</strong>
            	<select   style="font-size: 20px;height: 30px;width:260px;color: blue;" id="bankcode" name="bankcode">
            		<option value="-1">-- 请选择 --</option>
            		<c:forEach items="${bankInfo}" var="bank">
            			<option value="${bank.bankcodeid}">${bank.bankcodename}</option>
            		</c:forEach>
            	</select>
		  	</li>
		  	 <li><strong>开户行名称：</strong><input  style="font-size: 20px;height: 30px;width:260px;color: blue;" id="bankinfo" name="bankinfo" value="" /></li>
		  	 <li><strong>&nbsp;</strong><span style="color:red;">(例如：工商银行深圳市南头支行)</span></li>
            <li><strong>银行帐户姓名：</strong><input  style="font-size: 20px;height: 30px;width:260px;color: blue;" id="accname" name="accname" value="" /></li>
            <li><strong>银行账号：</strong><input onkeyup="keyup(this);"   style="font-size: 20px;height: 30px;width:260px;color: blue;" id="ankacct" name="ankacct" value="" /></li>
           
            
            <li>
            	<strong>卡折标识：</strong>
            	<select   style="font-size: 20px;height: 30px;width:260px;color: blue;" name="cardflag">
            		<c:forEach items="${bankCardType}" var="bankCard">
            			<option value="${bankCard.bandcardtypeid}">${bankCard.bandcardtypename}</option>
            		</c:forEach>
            	</select>	
			</li>
            <li>
            	<strong>证件类型：</strong>
            	<select   style="font-size: 20px;height: 30px;width:260px;color: blue;" name="certcode" >
            		<c:forEach items="${papersTypeInfo}" var="papersTypeInfo" >
            			<c:if test="${papersTypeInfo.paperstypename=='身份证'}">
            				<option value="${papersTypeInfo.paperstypeid}">${papersTypeInfo.paperstypename}</option>
            			</c:if>
            		</c:forEach>
            	</select>	
			</li>
            <li><strong>证件号码：</strong><input  style="font-size: 20px;height: 30px;width:260px;color: blue;" id="certno" name="certno" value=""  onkeyup="value=value.replace(/[^\d\w]/g,'') " /><font color="red">*身份证有字母注意X大写</font></li>
            <li><strong>联系电话：</strong><input  style="font-size: 20px;height: 30px;width:260px;color: blue;" id="contactphone" name="contactphone" value="" onkeyup="value=value.replace(/[^\d]/g,'') "	/></li>
            <li><strong>联系地址：</strong><input  style="font-size: 20px;height: 30px;width:260px;color: blue;" id="contactaddr" name="contactaddr" value="" /></li>
        
        </ul>
   </div>
   <div class="field-item_button_m">
    <input type="button" style="visibility:hidden" value="返回" id="checkCode" onClick="javascript:history.go(-1);" class="field-item_button" />  
   <input type="button" value="确认绑定" id="btnSubmit" onclick="submitForm();" class="field-item_button" />
   <!-- 
  <input type="button" style="visibility:hidden" value="返回" id="checkCode" onClick="javascript:history.go(-1);" class="field-item_button" />  
   --> 
</div>
</div>
</html:form>
</body>
</html>