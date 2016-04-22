<%@ page contentType="text/html; charset=GBK" language="java"%>
<%@ page import="java.util.*"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<jsp:useBean id="userSession" scope="session"
	class="com.wlt.webm.rights.form.SysUserForm" />
<jsp:useBean id="prod" scope="page"
	class="com.wlt.webm.business.bean.BiProd" />
<%
	String path = request.getContextPath();
	List valueList = prod.getProvince();
	request.setAttribute("proList", valueList);
	List interList = prod.getInterface();
	request.setAttribute("interface", interList);
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
jQuery(function(){
	$("#changeCity").click(function(a){$("#allCity").slideDown(0);a.stopPropagation();$(this).blur()});$("#allCity").click(function(a){a.stopPropagation()});$(document).click(function(a){a.button!=2&&$("#allCity").slideUp(0)});$("#foldin").click(function(){$("#allCity").slideUp(0)})
});

function check_all(obj,cName)
{
    var checkboxs = document.getElementsByName(cName);
    for(var i=0;i<checkboxs.length;i++){
    checkboxs[i].checked = obj.checked;
    }
}

function check(){
  	with(document.forms[0]){
    var checkboxs = document.getElementsByName("province");
    var flag=false;
    for(var i=0;i<checkboxs.length;i++){
    if(checkboxs[i].checked){
    flag=true;
    }
    }
    if(flag==false){
    alert("请选择省份");
    return false;
    }
   	if(confirm("请核对好信息,否则后果很严重!")){
    	document.getElementById("checkCode").value="请稍后...";
		document.getElementById("checkCode").disabled="disabled";
		target="_self";
		submit();
   	}else{
   		return false;
   	}
	}
}
window.onload=function(){
	if("${arrsy}"!=null && "${arrsy}"!=""){
		document.getElementById("div_arrsy").style.display="";
		document.getElementById("iscon").style.display="none";
	} 
}
function funCloseZzc(){
	document.getElementById("div_arrsy").style.display="none";iscon
	document.getElementById("iscon").style.display="";
}

</SCRIPT>
	</head>
	<body>
		<div id="popdiv">
			<span id="sellist"></span>
			<div style="clear: both"></div>
		</div>
		<html:form method="post" action="/business/prod.do?method=BatchUpdateFlowInterface">
			<input name="emtype" value="0" type="hidden">
			<div class="pass_main" id="iscon">
				<div class="pass_title_1">
					批量修改接口配置
				</div>
				<ul>
					<li style="margin-left: 50px; font-size: 16px;">
						<strong>运营商类型 ：</strong>
						<input name="type" type="radio" checked="checked" value="0" />
						电信
						<input name="type" type="radio" value="1" />
						移动
						<input name="type" type="radio" value="2" />
						联通
					</li>
					<li style="color: #397DF3;">
						<hr>
					</li>
					<li style="margin-left: 50px; font-size: 16px;">
						<strong>省份 ：</strong>
						<input type="checkbox" name="all"
							onclick="check_all(this,'province')" />
						全选
					</li>
					<li style="margin-left: 50px; color: #083D82; font-size: 14px;">
						<table border=0 width="90%" cellspacing=0 cellpadding=0>
							<tr style="text-align: left;">
								<logic:iterate id="proinfo" name="proList" indexId="i">
									<td style="text-align: left;">
<c:if test="${proinfo[1]!='全国'&&proinfo[1]!='中石化'}">
										<input name="province" type="checkbox" value="${proinfo[0] }" />
										${proinfo[1]}
										</c:if>
									</td>
									<c:if test="${(i+1)%6==0}">
							</tr>
							<tr style="text-align: left;">
								</c:if>
								</logic:iterate>
							</tr>
						</table>
					</li>
					<li style="color: #397DF3;">
						<hr>
					</li>
					<li style="margin-left: 50px; font-size: 16px;">
						<strong>接口信息 ：</strong>
					</li>
					<li style="margin-left: 50px; font-size: 16px;">
						<logic:iterate id="face" name="interface" indexId="j">
							<c:if test="${(j+1)%6==0}">
								<br />
							</c:if>
							<input name="face0" type="radio" value="${face[0] }"
								<c:if test="${j==0 }">checked="checked"</c:if> />${face[1]}
						</logic:iterate>
					</li>
				</ul>
				<div class="field-item_button_m">
					<input type="button" value="确认" id="checkCode" onclick="check()"
						class="field-item_button" />
					<input type="button" value="取消" id="checkCode" onClick="fun()"
						class="field-item_button" />
				</div>
			</div>
		</html:form>
	
	</body>
	<script type="text/javascript">
	function fun()
	{
		window.location.href ="<%=path%>/business/prod.do?method=flowinterList&type=0";
	}
</script>
</html>