<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/wlt-tags.tld" prefix="wlt" %>
<%@ page import="java.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<jsp:useBean id="userSession" scope="session" class="com.wlt.webm.rights.form.SysUserForm" />
<jsp:useBean id="user" scope="page" class="com.wlt.webm.business.bean.SysBankLog"/>
<%
  String path = request.getContextPath();
	if(null==userSession||userSession.getUsername()==null){
       response.sendRedirect(path+"/rights/wltlogin.jsp");
}
     List<String[]> lists=user.getUnionCardInfo(userSession.getUserno(),0);
     request.setAttribute("netpayList",lists);
%>
<html:html>
<head>
<title>万汇通</title>
<link href="../css/main_style.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href=../css/common.css>
<script type="text/javascript" src="../js/jquery-1.8.2.min.js"></script>
<script type="text/javascript" src="../js/util.js"></script>
<link href="../css/selectCss.css" rel="stylesheet" type="text/css" /> 
<script src="../js/selectCss.js" type="text/javascript"></script> 
<script language="javascript" type="text/javascript" src="../My97DatePicker/WdatePicker.js"></script>
<SCRIPT type=text/javascript>
	function payFrm()
	{
	    var i;
	    var myObj=document.getElementsByName("jieji");
			for(i=0;i<myObj.length;i++){
			if(myObj[i].checked)break;
			};
			if(i>=myObj.length){
			alert("请选择划账银行卡");
			return false;
			} 
	    
     	var val=directFrm.order_price.value;
     	 //   var minFee =directFrm.minTransferFee.value;
		//	var maxFee = directFrm.maxTransferFee.value;
		//	var hc = directFrm.handlingCharge.value;
					if (directFrm.order_price.value==""||directFrm.order_price.value==0)
		{
			alert("提醒：请填写转账金额！");
			directFrm.order_price.focus();
			return false;
		} 
//			if(parseFloat(val) - 50000>0){
	//			alert("转账金额必须小于50000元！");
	//			return false;
	//		}
        if(parseFloat(val)<1){
				if(hc.value == 1){
					alert("转账金额不能小于1元！");
					return false;
				} 
   			}
     	if(!/^[0-9]+(\.[0-9]+){0,1}$/.test(val)||/^0+[0-9]+(\.[0-9]+){0,1}$/.test(val)){
	        alert("请输入正确的数字");
	        return false;
            }

		if(directFrm.order_price.value.split(".")[1].length>2){
     	    alert("交易金额的小数点只能有两位！");
		    return false;
	    } 


	    document.forms[0].OK.disabled=true;
	    document.forms[0].OK.value="请稍后...";
		return true;
	}
	
	function keydownEvent(){
	if(event.keyCode==13){
	event.returnValue=false;
	}
	}
</SCRIPT>
</head>
<body rightmargin="0" leftmargin="0"  topmargin="0" class="body_no_bg">
<div id="popdiv"><span id="sellist"></span><div style="clear:both"></div></div>
<div id="tabbox">
  <div id="tabs_title">
    <ul class="tabs_wzdh">
      <li>借记/存折卡详情</li></ul>
  </div>
</div>
<form id="form1" name="directFrm" onSubmit="return payFrm();" action="<%=path %>/business/acctbill.do?method=netpayZK" method="post">
<input type="hidden" id="rootPath" value="<%=path %>"/>
<table id="myTable" cellspacing="0" cellpadding="0" class="tab_line">
<thead>
<tr height="25" class="tr_line_bottom">
<th>用户姓名</th>
<th>详细信息</th>
<th>卡类型</th>
<th>提交日期</th>
<th>状态</th>
<th>请选择</th>
</tr>
</thead>
<tbody id="tab">
<logic:iterate id="ordinfo" name="netpayList" indexId="i">
<tr>
<td class="td_line">${ordinfo[1]}</td>
<td class="td_line">${ordinfo[3]}:${ordinfo[4]}<br/>${ordinfo[6]}:${ordinfo[9]}</td>
<td class="td_line">${ordinfo[8]}</td>
<td class="td_line">${ordinfo[11]}</td>
<c:if test="${ordinfo[10]==0}"> 
<td class="td_line">未验证</td>
<td><input name="jieji" type="radio" value="" id="${ordinfo[9]}" style="color: gray;" disabled="disabled"></td>
</c:if>
<c:if test="${ordinfo[10]==0}"> 
<td class="td_line">已验证</td>
<td><input name="jieji" type="radio" value="" ></td>
</c:if>
</tr>
</logic:iterate>
</tbody>
</table>
<br/>
<div style="margin-left: 200px;font-size: 18px;color:#333333;" />
<span style="height: 30px;vertical-align: middle;">请输入转款金额:</span>&nbsp;&nbsp;<input style="font-size: 20px;height: 30px;width:180px;color: blue;" name="order_price" type="text" onkeyup="if(isNaN(value))execCommand('undo')" onafterpaste="if(isNaN(value))execCommand('undo')" onkeydown="keydownEvent()"/>元
<input type="submit" value="转款" name="OK" onclick="check();" style="margin-left: 20px;width: 80px;height: 30px;background-color: #1980c3;border: 2;font-size: 16px;color: white;"/>
</div>
</form>
 <div style="font-size: 13px; color:#333333; red;margin-left: 150px;clear:both;">
<br/>
<br/>
1、支持通过银行卡或者存折购买额度。<br/>
2、交易金额低于2000元/笔扣1元手续费，大于等于2000元/笔免费。<br/>
3、额度实时到账。
</div>
</body>
</html:html>