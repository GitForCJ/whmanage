<%@ page contentType="text/html; charset=GBK" language="java"%>
<%@ page import="java.util.*"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<jsp:useBean id="userSession" scope="session" class="com.wlt.webm.rights.form.SysUserForm" />
<jsp:useBean id="prod" scope="page" class="com.wlt.webm.business.bean.BiProd"/>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
 <%
     String parentid=userSession.getUser_id();
     String pid=userSession.getUsersite();
     String downnum= request.getParameter("downnum");
    // System.out.println("downnum"+downnum+"  parentid "+parentid);
	 List interList=prod.getAgentEmploy(pid,Integer.parseInt(userSession.getUser_id()),downnum);
	 request.setAttribute("interface", interList);
	 List zonghe=prod.getAgentEmploy3Detail(Integer.parseInt(userSession.getUser_id()),downnum);
	 request.setAttribute("zonghe", zonghe);
 %>
 <html>
<head>
<title>万汇通</title>
<script type="text/javascript" src="../js/util.js"></script>
<script type="text/javascript" src="../js/jquery-1.8.2.min.js"></script>
<link href="../css/main_style.css" rel="stylesheet" type="text/css" />
<link href="../css/selectCss.css" rel="stylesheet" type="text/css" /> 
<SCRIPT type=text/javascript>
showMessage("${mess}");

function check_all(obj,cName)
{
    var checkboxs = document.getElementsByName(cName);
    for(var i=0;i<checkboxs.length;i++){
    checkboxs[i].checked = obj.checked;
    }
}

function check(){
  	with(document.forms[0]){
    var checkboxs = document.getElementsByName("ids");
    var flag=true;
    for(var i=0;i<checkboxs.length;i++){
    var a=checkboxs[i].value;
  // var strs= new Array();
	//strs=a.split("#"); 
	var b=document.getElementById(a).value;   
	var c=document.getElementById("td"+a).value; 
	if(b==""){
	alert("代办户佣金不能为空");
	 return false;
	 }
       if(parseFloat(b)>100||parseFloat(b)<0){
       flag=false;
       break;
       }
    }
    if(flag==false){
    alert("代办户佣金比不能大于100%,并且不能低于0%");
    return false;
    }
    
    var checkboxs1 = document.getElementsByName("elids");
    var flag1=true;

    for(var j=0;j<checkboxs1.length;j++){
    var a1=checkboxs1[j].value;
  // var strs= new Array();
	//strs=a.split("#"); 
	var b1=document.getElementById(a1).value;   
	var c1=document.getElementById("zh"+a1).value; 
//	alert(b1);
//	alert(c1);
	if(b1==""){alert("代办户佣金不能为空"); return false;}
       if(parseFloat(b1)>100||parseFloat(b1)<0){
       flag1=false;
       break;
       }
    }
    if(flag1==false){
    alert("代办户佣金比不能大于100%");
    return false;
    }
    
    
     if(confirm("您确认如此分配?")){
	    document.getElementById("checkCode").value="请稍后...";
		document.getElementById("checkCode").disabled="disabled";
		target="_self";
		submit();
		}else{
		return false;
		}
	}
}

function fun(className,b,index)
{
	var c=className.value/100*b;
	document.getElementById("valueA_"+index).innerHTML=c.toFixed(3);
}
</SCRIPT>
</head>
<body>
<div id="popdiv"><span id="sellist"></span><div style="clear:both"></div></div>
<html:form  method="post" action="/business/prod.do?method=addEmployAvg">
<input name="userno" value="<%=downnum %>" type="hidden">
<input name="parentid" value="<%=parentid %>" type="hidden">
<div class="pass_main">
    	<ul>
            <li style="margin-left: 50px;font-size: 14px;"><strong>话费充值佣金 ：</strong></li>
<table style="margin-left: 100px;font-size: 14px;" >
<thead>
<tr>
<th>序号</th>
<th>运营商</th>
<th>面额区间</th>
<th>总佣金比</th>
<th>代办户佣金</th>
<th>用户佣金值</th>
</tr>
</thead>
<tbody id="tab">
<logic:iterate id="face" name="interface" indexId="i">
<tr>
<td>${i + 1}</td>
<c:if test="${face[0]==0}">
<td>电信</td>
</c:if>
<c:if test="${face[0]==1}">
<td>移动</td>
</c:if>
<c:if test="${face[0]==2}">
<td>联通</td>
</c:if>
<td>${face[3]}</td>
<td><input size="8" readonly="readonly" style="border:none;" id="td${face[0]}#${face[1]}#${face[2]}#${face[5]}" name="td${face[0]}#${face[1]}#${face[2]}#${face[5]}" value="${face[2]}" />%</td>
<td><input size="8" id="${face[0]}#${face[1]}#${face[2]}#${face[5]}" name="${face[0]}#${face[1]}#${face[2]}#${face[5]}" onblur="fun(this,${face[2]},${i})" value="${face[4]}" />%</td>
<td id="valueA_${i}">
<fmt:formatNumber value="${face[4]/100*face[2]}"  pattern="#0.000"></fmt:formatNumber>
</td>
<td align="center" style="display: none;">
<input name="ids" type="checkbox" checked="checked" readonly="readonly" value="${face[0]}#${face[1]}#${face[2]}#${face[5]}">
</td>
</tr>
</logic:iterate>
</tbody>
</table>

<li style="margin-left: 50px;font-size: 14px;"><strong>增值业务佣金 ：</strong></li>
<table border=0 style="margin-left: 50px;">
<logic:iterate id="zg" name="zonghe" indexId="i">
<tr>
<td style="text-align:left;">
<label>
	<font color="blue">${zg[2]}:</font>
</label>
<font color="blue">总佣金:</font>
<input size="5" readonly="readonly" id="zh${zg[0]}#${zg[1]}#${zg[4]}" name="zh${zg[0]}#${zg[1]}#${zg[4]}" value="${zg[1]}"/>%
</td>
<td style="text-align:left;">
<font color="blue">代办点佣金:</font>
<input size="5"  id="${zg[0]}#${zg[1]}#${zg[4]}" name="${zg[0]}#${zg[1]}#${zg[4]}" value="${zg[3] }" onblur="fun(this,${zg[1]},${i+1001})"/>%
</td>
<td style="text-align:left;">
<font color="blue">佣金值:</font>
<font id="valueA_${i+1001}">
	<fmt:formatNumber value="${zg[3]/100*zg[1]}"  pattern="#0.000"></fmt:formatNumber>
</font>
<input style="display: none;" name="elids" type="checkbox" checked="checked" readonly="readonly" value="${zg[0]}#${zg[1]}#${zg[4]}">
</td>
</tr>
</logic:iterate>
</table>
</ul>
   <div class="field-item_button_m">
   <input type="button" value="修改" id="checkCode" onclick="check()" class="field-item_button" />
   <input type="button" value="取消" id="checkCode1" onClick="javascript:history.go(-1)" class="field-item_button" />
   </div>
</div>
<br>
</html:form>
<div style="padding-left:40px;padding-top:10px;margin-left: 20px;font-size: 13px; color: #FF6600;font-weight:bold;border:1px solid #FF9E4d;background-color: #FFFFE5;">
<ul>
<li style="list-style:none;">1、"面额区间"参数的跨度是指大于等于下限数值，小于上限数值。</li>
<li style="list-style:none;">&nbsp;</li>
<li style="list-style:none;">2、"总佣金比"是指系统给到代理商（含下级网点）的最高佣金比例。</li>
<li style="list-style:none;">&nbsp;</li>
<li style="list-style:none;">3、"下级网点佣金"是代理商用来调整下级网点本省话费充值和增值业务佣金范围的，比例差额是代理商的提成佣金比，建议采用系统默认比例，不要随意调整。</li>
<li style="list-style:none;">&nbsp;</li>
</ul>
</div>

</body>
</html>