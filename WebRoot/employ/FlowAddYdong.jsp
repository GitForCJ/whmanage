<%@ page language="java" contentType="text/html; charset=gb2312"%>
<%@ page import="java.util.*"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<jsp:useBean id="tpbean" scope="session"
	class="com.wlt.webm.business.bean.TpBean" />
<jsp:useBean id="sit" scope="page"
	class="com.wlt.webm.business.bean.SysUserInterface" />
<%
String path = request.getContextPath();
	List groupsList = tpbean.getFlowGroups();//获取流量佣金组名称
	request.setAttribute("areas",sit.listArea());
	StringBuffer strs = new StringBuffer();
	strs.append("请选择[]");
	for (Object tmp : groupsList) {
		String[] temp = (String[]) tmp;
		strs.append("|" + temp[0] + "[" + temp[0] + "]");
	}
	request.setAttribute("groups", strs.toString());
	String ListGreoups=request.getParameter("groups");
%>
<html>
	<head>
		<title>万汇通</title>
		<script type="text/javascript" src="../js/jquery-1.8.2.min.js"></script>
		<script type="text/javascript" src="../js/xml_cascade.js"></script>
		<link href="../css/main_style.css" rel="stylesheet" type="text/css" />
		<link href="../css/selectCss.css" rel="stylesheet" type="text/css" />
		<script src="../js/selectCss.js" type="text/javascript"></script>
<script  type="text/javascript" src="../js/util.js"></script>
<SCRIPT type=text/javascript>

showMessage("${mess}");

	function check(){
		var cm_groups=document.getElementById("cm_groups").value;
		if(cm_groups==""){
			alert("请选择佣金组名称");
			return ;
		}
		var value=document.getElementsByName("ids");
		var strs="";
		var bool=false;
		for(var i=0;i<value.length;i++){
		var a=value[i].value;
		var province=document.getElementById("pd"+a).value;
		var price =document.getElementById("price"+a).value;
		if(null==price||""==price){
		bool=true;
		break;
		}
		strs=strs+province+"|"+price+"#";
		}
		if(bool){
			alert("数据不完整!");
			return ;
		}
		document.getElementById("checkCode").value="请稍后...";
		document.getElementById("checkCode").disabled="disabled";
		document.getElementById("strs").value=strs;
		document.forms[0].target="_self";
		document.forms[0].submit();
		return ;
	}
</SCRIPT>
	</head>
	<body>
		<div id="popdiv">
			<span id="sellist"></span>
			<div style="clear: both"></div>
		</div>
		<form method="post" action="<%=path %>/business/prod.do?method=Ydflowadd">
			<div class="pass_main">
				<div class="pass_title_1">
					添加移动流量省份佣金
				</div>
				<div class="pass_list">
					<ul>
						<li>
							<strong>佣金组名称:</strong>
							<span>
								<div class="sub-input">
									<a class="sia-2 selhover" id="sl-cm_groups"
										href="javascript:void(0);" style="width: 200px;">请选择</a>
								</div> <input style="width: 200px;" name="cm_groups" id="cm_groups"
									defaultsel="${requestScope.groups }" type="hidden" value="" />
							</span>
							<li >
								<strong>佣金配置:</strong>
								<span>&nbsp;</span>
							</li>
<logic:iterate id="area" name="areas" indexId="i">
<c:if test="${area[0]!='381'&&area[0]!='382'}">
										<li>
											<strong>&nbsp;</strong>
											<span>
												<input type="text" value="${area[1]}" disabled style="width: 150px;" />
												<input type="hidden"  value="${area[0]}" id="pd${area[0]}"/>
												&nbsp;
												<input type="text"  value=""  id="price${area[0]}" style="width: 80px;"/>&nbsp;<label><font color="red">最多两位小数</font></label>
												&nbsp;
												<input style="display: none;" name="ids" type="checkbox" checked="checked"  value="${area[0]}">
											</span>
										</li>
</c:if>
</logic:iterate>
					</ul>
				</div>
				<div class="field-item_button_m">
					<input type="button" value="添加" id="checkCode"  onclick="check()"
						class="field-item_button" />
					<input type="button" value="取消" id="checkCode1"
						onClick="javascript:history.go(-1)" class="field-item_button" />
				</div>
			</div>
			<input type="hidden" name="strs" id="strs" value=""/>
		</form>
	</body>
</html>