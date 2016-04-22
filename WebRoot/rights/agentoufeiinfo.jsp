<%@ page contentType="text/html; charset=UTF-8" language="java"%>
<%@page import="java.util.List"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%
	String path = request.getContextPath();
 %>
<html>
	<head>
		<title>万汇通</title>
		<script type="text/javascript" src="../js/util.js"></script>
		<link href="../css/main_style.css" rel="stylesheet" type="text/css" />
		<link href="../css/selectCss.css" rel="stylesheet" type="text/css" />
		<SCRIPT type=text/javascript>
showMessage("${mess}");

function check(a,type){  
  	with(document.forms[0]){
        if(!confirm("确认该操作")){
        return ;
        }
		action="sysuser.do?method=ghUp&flag="+a+"&type="+type;
		target="_self";
		method="post"
		submit();
	}
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
						殴飞供货信息
					</li>
				</ul>
			</div>
		</div>
		<html:form method="post" action="/rights/sysuser.do?method=ghUp">
			<!-- <input type="hidden" value="${infos }" name="hh"/> -->
			<div class="pass_main"  style="width:100%;" >
				<div class="pass_list" style="width:100%;padding-left:0px;">
					<c:forEach items="${infos}" var="in" varStatus="index" >
						<ul>
							<li  style="width:100%;margin-left:0px;<c:if test='${(index.index+1)%2==0 }'>background-color:#cccccc</c:if>" >
								<strong>当前&nbsp;<font style="font-size:16px" color="red">${in[2]}</font> &nbsp;状态：</strong>
								<c:if test="${in[0]==0}">
									<span style="color: red;">已开启</span>
								</c:if>
								<c:if test="${in[0]==1}">
									<span style="color: red;">已关闭</span>
								</c:if>

								<c:if test="${in[0]==1}">
									<input onclick="check('0',${in[1]});" 
										type="button"  
										id="btnSubmit"
										style="width:85px; height:32px; border:0px;color:#fff; font-weight:bold;background:url(<%=path%>/images/button_out.png) no-repeat bottom; cursor:pointer;" 
										value="开启"/>
								</c:if>
								<c:if test="${in[0]==0}">
									<input onclick="check('1',${in[1]});"
										type="button"  
										id="btnSubmit"
										style="width:85px; height:32px; border:0px;color:#fff; font-weight:bold;background:url(<%=path%>/images/button_out.png) no-repeat bottom; cursor:pointer;" 
										value="关闭"/>
								</c:if>
							</li>
						</ul>
					</c:forEach>
				</div>
			</div>
		</html:form>
	</body>
</html>