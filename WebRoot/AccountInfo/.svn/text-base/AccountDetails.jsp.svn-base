<%@ page contentType="text/html; charset=gbk" language="java"%>
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
	 String userRole=userSession.getUser_role();
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
	var objA = window.dialogArguments;
    //alert("您传递的参数为：" + obj.id)
    $(function(){
    	var root=-1;
    	$.ajax({
			type:"post",
			url:"<%=path%>/AccountInfo/AccountVerify.do?method=showDetails&id="+objA.id,
			dataType:"json",
			success:function(obj){
				$("#RegionalCodeName").html(obj[0]);
				$("#accname").html(obj[1]);
				if(objA.role!=1)
				{
					$("#ankacct").html(StrCharAt(obj[2]));
					$("#certno").html(StrCharAt(obj[7]));
					$("#contactphone").html(StrCharAt(obj[8]));
				}
				else
				{
					$("#ankacct").html(obj[2]);
					$("#certno").html(obj[7]);
					$("#contactphone").html(obj[8]);
				}
				
				$("#bankinfo").html(obj[3]);
				$("#BankCodeName").html(obj[4]);
				$("#BandCardTypeName").html(obj[5]);
				$("#Papers_TypeName").html(obj[6]);
				$("#contactaddr").html(obj[9]);
				$("#recvcorp").html(obj[10]);
				if(obj[11]=="0")
					$("#status").html("未验证");
				else
					$("#status").html("已验证");
			}
		}); 
    });
    
   function StrCharAt(str)
   {
   var displayStr="";
   	for(var i=0;i<str.length;i++)
   	{
   		if(i>3 && i<(str.length-4))
   		{
   			displayStr=displayStr+"*";
   			continue;
   		}
   		displayStr=displayStr+str.charAt(i);
   	}
  	return displayStr;
   }
</SCRIPT>
</head>
<body>
<div id="popdiv"><span id="sellist"></span><div style="clear:both"></div></div>
<div id="tabbox">
  <div id="tabs_title">
    <ul class="tabs_wzdh">
      <li>账户详细信息</li></ul>
  </div>
</div>
<input type="hidden" name="rdurl" value="/rights/wltusermodify.jsp"/>
<div class="pass_main">
    <div class="pass_list" style="width:400px;">
    	<ul>
            <li><strong>银行卡归属地区：</strong><span id="RegionalCodeName">abc</span></li>
            <li><strong>开户银行：</strong><span id="BankCodeName">abc</span></li>
            <li><strong>开户行名称：</strong><span id="bankinfo">abc</span></li>
            <li><strong>银行帐户姓名：</strong><span id="accname">abc</span></li>
            <li><strong>银行账号：</strong><span id="ankacct">abc</span></li>
            <li><strong>卡折标识：</strong><span id="BandCardTypeName">abc</span></li>
            <li><strong>证件类型：</strong><span id="Papers_TypeName">abc</span></li>
            <li><strong>证件号码：</strong><span id="certno">abc</span></li>
            <li><strong>联系电话：</strong><span id="contactphone">abc</span></li>
            <li style="display:none;"><strong>联系地址：</strong><span id="contactaddr">abc</span></li>
        	<li><strong>账户状态：</strong><span id="status">abc</span></li>
        	<li style="text-align:center;"><input type="submit" value="返回"  onclick="window.close();" style="margin-left: 20px;width: 80px;height: 30px;background-color: #1980c3;border: 2;font-size: 16px;color: white;"/></li>
        </ul>
   </div>
</div>
</body>
</html>
	
  