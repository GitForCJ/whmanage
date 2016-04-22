<%@ page language="java" contentType="text/html; charset=gb2312"%>
<%@ taglib uri="/WEB-INF/wlt-tags.tld" prefix="wlt"%>
<%@ page import="java.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<jsp:useBean id="userSession" scope="session"
	class="com.wlt.webm.rights.form.SysUserForm" />
<jsp:useBean id="prod" scope="page"
	class="com.wlt.webm.business.bean.BiProd" />
<jsp:useBean id="sit" scope="page"
	class="com.wlt.webm.business.bean.SysUserInterface" />
<jsp:useBean id="ccc" scope="page"
	class="com.wlt.webm.business.action.OrderAction" />
<%
	String path = request.getContextPath();
	List areaList = prod.getInterface();
	request.setAttribute("area", areaList);
	request.setAttribute("areaSel", ccc.getStingSel(sit.listArea(),
			"区域"));
	List yjList = prod.getYjpz();
	request.setAttribute("yjList", yjList);
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
	<link href="../css/selectCss.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript" src="../js/util.js"></script>
	<SCRIPT src="../js/more_tiaojian.js" type=text/javascript></SCRIPT>
	<script src="../js/jquery-1.8.2.min.js" type="text/javascript"></script>
	<script src="../js/selectCss.js" type="text/javascript"></script>
	<SCRIPT type=text/javascript>
showMessage("${mess}");

	function ha_change(){
		with(document.forms[0]){
	  	action="../business/prod.do?method=interList";
	  	target="_self";
	  	submit();
		}
	}
	
	function ha_add(){
	 with(document.forms[0]){
	  action="../employ/whtinterfaceadd.jsp";
	  target="_self";
	  submit();
	  }
	}
	
		function ha_update(a){
		
	  	with(document.forms[0]){
	         var b=document.getElementById(a).value;
	         var moeny=document.getElementById(a+"_A").value;
			
			var tt=-1;
	         var names=document.getElementsByName("type");
	         for(var i=0;i<names.length;i++)
	         {
	         	if(names[i].checked)
	         	{
	         		tt=names[i].value;
	         		break;
	         	}
	         }
	     	 $("#update").val("修改中");
	     	$('#update').attr('disabled',"true");
            if(confirm("确认修改?")){
			  	//action = "../business/prod.do?method=interUpdate&cmid="+a+"&intercm="+b+"&moenyId="+moeny+"&type="+tt;
			  	//target="_self";
			  	//submit();
			  	$.post
			   (
			    	"<%=path%>/business/prod.do?method=interUpdate",
			      	{
			      		cmid:a,
			      		intercm:b,
			      		moenyId:moeny,
			      		type:tt
			      	},
			   		function (data)
			   		{
			   			if(data==0){
			   				alert("修改成功");
			   			}else{
			   				alert("修改失败");
			   			}
			   		},"json"
      			)
			  	 $("#update").val("修改");
			  	$('#update').removeAttr("disabled");
	  		}else{
	  			return false;
	  		}
	  }
	}
	//批量修改
	function BatchUpdate(){
		window.location.href="../employ/BatchUpdateInterface.jsp";
	  	window.location.target="_self";
	  	return ;
	}
</SCRIPT>
</head>
<div id="popdiv">
	<span id="sellist"></span>
	<div style="clear: both"></div>
</div>
<body rightmargin="0" leftmargin="0" topmargin="0" class="body_no_bg">
	<html:form action="/business/prod.do?method=interList&flag=0"
		method="post">
		<table width="100%" cellspacing="0" cellpadding="0">
			<tr>
				<td>
					<strong>区域:</strong>
				</td>
				<td align="left">
					<div class="sub-input">
						<a class="sia-2 selhover" id="sl-cm_one"
							href="javascript:void(0);">区域</a>
					</div>
					<input nname="nname" name="cm_one" id="cm_one"
						defaultsel="${requestScope.areaSel }" type="hidden"
						value="${cm_one}" />
				</td>
				<td>
					<strong>运营商类型:</strong>
					<input name="type" type="radio"
						<c:if test="${type==0}">checked="checked" </c:if> value="0" />
					电信
					<input name="type" type="radio"
						<c:if test="${type==1}">checked="checked" </c:if> value="1" />
					移动
					<input name="type" type="radio"
						<c:if test="${type==2}">checked="checked" </c:if> value="2" />
					联通
					<input name="type" type="radio"
						<c:if test="${type==3}">checked="checked" </c:if> value="3" />
					广东电信
				</td>
				<td style="text-align: left; padding-left: 0px;">
					<input type="button" name="Button" value="查询"
						class="field-item_button" onClick="ha_change()">
				</td>
				<td style="text-align:left;padding:0px 0px 0px 0px;width:80px;">
					<a href="javascript:BatchUpdate();" style="font-size:13px;color:red;font-weight:bold;text-decoration:underline">批量修改</a>
				</td>
			</tr>
		</table>
		<table id="myTable">
			<thead>
				<tr>
					<th>
						序号
					</th>
					<th>
						编号
					</th>
					<th>
						省份
					</th>
					<th>
						运营商
					</th>
					<th>
						面额
					</th>
					<th>
						接口信息
					</th>
					<th>
						操作
					</th>
				</tr>
			</thead>
			<tbody id="tab">
				<logic:iterate id="cominfo" name="comList" indexId="i">
					<tr>
						<td>
							${i + 1}
						</td>
						<td>
							${cominfo[0]}
						</td>
						<td>
							${cominfo[2]}
						</td>
						<c:if test="${cominfo[3]==0}">
							<td>
								电信
							</td>
						</c:if>
						<c:if test="${cominfo[3]==1}">
							<td>
								移动
							</td>
						</c:if>
						<c:if test="${cominfo[3]==2}">
							<td>
								联通
							</td>
						</c:if>

						<td>
							<select id="${cominfo[0]}_A">
								<logic:iterate id="ar" name="yjList" indexId="j">
									<option value="${ar[0]}" <c:if test="${ar[0]==cominfo[6]}">selected="selected" </c:if>>
										${ar[1]}~${ar[2]}
									</option>
								</logic:iterate>
							</select>
						</td>
						<c:if test="${type==3}">
							<c:if test="${i==0}">
								<td align="center" rowspan="8">
									<select id="${cominfo[0]}" style="height:30px;">
										<logic:iterate id="ar" name="area" indexId="j">
											<option value="${ar[0]}" <c:if test="${ar[0]==cominfo[4]}">selected="selected" </c:if>>
												${ar[1] }
											</option>
										</logic:iterate>
									</select>
								</td>
								<td align="center" rowspan="8">
									<input name="update" id="update" type="button" value="修改" class="onbutton" onClick="ha_update('${cominfo[0]}')" style="height:25px;">
								</td>
							</c:if>
						</c:if>
						<c:if test="${type!=3}">
							<td>
								<select id="${cominfo[0]}">
									<logic:iterate id="ar" name="area" indexId="j">
										<option value="${ar[0]}" <c:if test="${ar[0]==cominfo[4]}">selected="selected" </c:if>>
											${ar[1] }
										</option>
									</logic:iterate>
								</select>
							</td>
							<td align="center">
								<input name="update" id="update" type="button" value="修改" class="onbutton" onClick="ha_update('${cominfo[0]}')">
							</td>
						</c:if>
					</tr>
				</logic:iterate>
			</tbody>
			<tfoot>
			</tfoot>
		</table>
		<p align="center">
			<input name="Button" type="button" value="增加" class="onbutton" onClick="ha_add()" <c:if test="${type==3}">style="display: none;" </c:if>>
		</p>
	</html:form>
</body>
</html:html>