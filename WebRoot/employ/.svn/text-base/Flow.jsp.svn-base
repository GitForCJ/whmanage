<%@ page language="java" contentType="text/html; charset=gb2312"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>

<jsp:useBean id="ccc" scope="page"
	class="com.wlt.webm.business.action.OrderAction" />
<jsp:useBean id="tpbean" scope="session"
	class="com.wlt.webm.business.bean.TpBean" />
<%
	String path = request.getContextPath();

	request.setAttribute("groupsList", ccc.getStingSel(tpbean
			.getFlowGroups(), "佣金组"));//获取佣金组名称
%>
<html:html>
<head>
	<title>万汇通</title>
	<META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=gb2312">
	<link href="../css/main_style.css" rel="stylesheet" type="text/css" />
	<link rel="stylesheet" type="text/css" href=../css/common.css>
	<style type="text/css">
.tabs_wzdh {
	width: 100px;
	float: left;
}

.tabs_wzdh li {
	height: 35px;
	line-height: 35px;
	margin-top: 5px;
	padding-left: 20px;
	font-size: 14px;
	font-weight: bold;
}

/*tree_nav*/
.tree_nav {
	border: 1px solid #CCC;
	background: #f5f5f5;
	padding: 10px;
	margin-top: 20px;
}

.field-item_button_m2 {
	margin: 0 auto;
	text-align: center;
}

.field-item_button2 {
	border: 0px;
	width: 136px;
	height: 32px;
	color: #fff;
	background: url(../images/button_out2.png) no-repeat;
	cursor: pointer;
	margin-right: 20px;
}

.field-item_button2:hover {
	border: 0px;
	width: 136px;
	height: 32px;
	color: #fff;
	background: url(../images/button_on2.png) no-repeat;
	margin-right: 20px;
}
</style>
	<script type="text/javascript" src="../js/util.js"></script>
	<script language='javascript' src='../js/jquery.js'></script>
	<link href="../css/selectCss.css" rel="stylesheet" type="text/css" />
	<script src="../js/jquery-1.8.2.min.js" type="text/javascript"></script>
	<script src="../js/selectCss.js" type="text/javascript"></script>
	<SCRIPT type=text/javascript>
	jQuery(function(){
		if("${mess}"!=""){
			alert("${mess}");
		}
	});
	
	function ha_add(){
	 with(document.forms[0]){
		  action="../employ/FlowAdd.jsp";
		  target="_self";
		  submit();
		  return;
	  }
	}
	
	function ha_update(){
	  with(document.forms[0]){
	  	var strs=document.getElementsByName("ids");
	  	var con=0;
	  	for(var i=0;i<strs.length;i++){
	  		if(strs[i].checked==true){
	  			con++;
	  			break;
	  		}
	  	}
	  	if(con<1){
	  		alert("请选择要修改的记录");
			return;
	  	}
	  	action = "../employ/FlowUpdaet.jsp";
	  	target="_self";
	  	submit();
	  	return ;
	  }
	}
	
	function checksubmit(bool)
	{
		$("#bool").val(bool);
		with(document.forms[0]){
			submit();
		}
	}
	
</SCRIPT>
</head>
<div id="popdiv">
	<span id="sellist"></span>
	<div style="clear: both"></div>
</div>
<body rightmargin="0" leftmargin="0" topmargin="0" class="body_no_bg">
	<html:form action="/business/prod.do?method=Flowlist" method="post">
		<input type="hidden" id="bool" name="bool" value="" />
		<div class="header">
			<div class="topCtiy clear">
				<ul>
					<li>
						<div class="sub-input">
							<a class="sia-2 selhover" id="sl-groups"
								href="javascript:void(0);" style="width: 170px;">佣金组</a>
						</div>
						<input nname="nname" name="groups" id="groups"
							defaultsel="${requestScope.groupsList }" type="hidden"
							value="${userForm.groups}" />
					</li>
					<li>
						<input type="button" name="Button" value="查询"
							class="field-item_button" onClick="checksubmit(0);">
					</li>
					<c:if test="${userForm.groups!='' && userForm.groups!=null}">
						<li>
							<input type="button" name="Button" value="导出"
								class="field-item_button" onClick="checksubmit(-11);"
								id="excelID">
						</li>
					</c:if>
				</ul>
			</div>
		</div>

		<div id="div_scroll">
			<table id="myTable">
				<thead>
					<tr>
						<th>
							序号
						</th>
						<th>
							运营商
						</th>
						<th>
							类型
						</th>
						<th>
							佣金百分比
						</th>
						<th>
							佣金组名称
						</th>
						<th>
							选择
						</th>
					</tr>
				</thead>
				<tbody id="tab">
					<logic:iterate id="userinfo" name="userList" indexId="i">
						<tr>
							<td>
								${i + 1}
							</td>
							<td>
								${userinfo[0]}
							</td>
							<td>
								${userinfo[1]}
							</td>
							<td>
								${userinfo[2]}
							</td>
							<td>
								${userinfo[3]}
							</td>
							<td align="center">
								<input name="ids" type="radio"
									value="${userinfo[0]}#${userinfo[1]}#${userinfo[2]}#${userinfo[3]}#${userinfo[4]}"/>
							</td>
						</tr>
					</logic:iterate>
				</tbody>
			</table>
			<p align="center">
				<input name="Button" type="button" value="增加" class="onbutton"
					onClick="ha_add()">
				<input type="button" name="Submit2" value="修改" class="onbutton"
					onClick="ha_update()">
			</p>
		</div>
	</html:form>
</body>
</html:html>