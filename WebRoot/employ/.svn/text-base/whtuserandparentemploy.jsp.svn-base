<%@ page contentType="text/html; charset=GBK" language="java"%>
<%@ page import="java.util.*"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<jsp:useBean id="userSession" scope="session" class="com.wlt.webm.rights.form.SysUserForm" />
<jsp:useBean id="prod" scope="page" class="com.wlt.webm.business.bean.BiProd"/>
 <%
     String pid=userSession.getUsersite();
	 List interList=prod.getEmploy0Detail(pid);
	 request.setAttribute("interface", interList);
	 List zonghe=prod.getEmploy3Detail();
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
    alert(a);
  // var strs= new Array();
	//strs=a.split("#"); 
	var b=document.getElementById(a).value;   
	var c=document.getElementById("td"+a).value; 
	alert(b);
	alert(c);
	if(b==""){alert("代办户佣金不能为空"); return false;}
       if(parseFloat(b)>=parseFloat(c)){
       flag=false;
       break;
       }
    }
    if(flag==false){
    alert("代办户佣金比不能大于总佣金比");
    return false;
    }
    
    alert("===");
    var checkboxs1 = document.getElementsByName("elids");
    var flag1=true;

    for(var j=0;j<checkboxs1.length;j++){
        alert(checkboxs1.length);
    var a1=checkboxs1[j].value;
    alert(a1);
  // var strs= new Array();
	//strs=a.split("#"); 
	var b1=document.getElementById(a1).value;   
	var c1=document.getElementById("zh"+a1).value; 
	alert(b1);
	alert(c1);
	if(b1==""){alert("代办户佣金不能为空"); return false;}
       if(parseFloat(b1)>=parseFloat(c1)){
       flag1=false;
       break;
       }
    }
    if(flag1==false){
    alert("代办户佣金比不能大于总佣金比");
    return false;
    }
    
    
     if(confirm("确认如此分配?")){
	    document.getElementById("checkCode").value="请稍后...";
		document.getElementById("checkCode").disabled="disabled";
		target="_self";
		submit();
		}else{
		return false;
		}
	}
}
</SCRIPT>
</head>
<body>
<div id="popdiv"><span id="sellist"></span><div style="clear:both"></div></div>
<html:form  method="post" action="/business/prod.do?method=addEmployAvg">
<input name="userno" value="${requestScope.downnum}" type="hidden">
<input name="parentid" value="${requestScope.parentid}" type="hidden">
<div class="pass_main">
    	<ul>
            <li style="margin-left: 50px;font-size: 14px;"><strong>话费充值佣金 ：</strong></li>
<table style="margin-left: 100px;font-size: 14px;">
<thead>
<tr>
<th>序号</th>
<th>运营商</th>
<th>面额区间</th>
<th>总佣金比</th>
<th>代办户佣金</th>
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
<td><input size="5" readonly="readonly" id="td${face[0]}#${face[1]}#${face[2]}" name="td${face[0]}#${face[1]}#${face[2]}" value="${face[2]}"/>%</td>
<td><input size="5" id="${face[0]}#${face[1]}#${face[2]}" name="${face[0]}#${face[1]}#${face[2]}" value=""/></td>
<td align="center" style="display: none;"> 
<input name="ids" type="checkbox" checked="checked" readonly="readonly" value="${face[0]}#${face[1]}#${face[2]}">
</td>
</tr>
</logic:iterate>
</tbody>
</table>

<li style="margin-left: 50px;font-size: 14px;"><strong>增值业务佣金 ：</strong></li>
<logic:iterate id="zg" name="zonghe" indexId="i">
<li  style="margin-left: 100px;font-size: 14px;">
<label><font color="blue">${zg[2]}:</font></label><font color="blue">总佣金:</font>><input size="5" readonly="readonly" id="zh${zg[0]}#${zg[1]}" name="zh${zg[0]}#${zg[1]}" value="${zg[1]}"/>%
&nbsp;&nbsp;&nbsp;<font color="blue">代办户佣金:</font><input size="5"  id="${zg[0]}#${zg[1]}" name="${zg[0]}#${zg[1]}" value=""/>
<input style="display: none;" name="elids" type="checkbox" checked="checked" readonly="readonly" value="${zg[0]}#${zg[1]}">
</li>
</logic:iterate>
</ul>
   <div class="field-item_button_m">
   <input type="button" value="确认" id="checkCode" onclick="check()" class="field-item_button" />
   </div>
</div>
</html:form>
</body>
</html>