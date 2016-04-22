<%@ page language="java" contentType="text/html; charset=gb2312"%>
<%@ page import="java.util.*"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>

<jsp:useBean id="sit" scope="page"
	class="com.wlt.webm.business.bean.SysUserInterface" />
<jsp:useBean id="ccc" scope="page"
	class="com.wlt.webm.business.action.OrderAction" />
<jsp:useBean id="tpbean" scope="session"
	class="com.wlt.webm.business.bean.TpBean" />
<%
	String path = request.getContextPath();

	request.setAttribute("groupsList", ccc.getStingSel(tpbean
			.getJTFKGroups(), "交罚佣金组"));//获取佣金组名称

	request.setAttribute("carList", ccc.getStingSel(
			tpbean.getCarList(), "车辆类型"));

	request.setAttribute("areaSel", ccc.getStingSel(sit.listArea2(),
			"地市"));
%>
<html>
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
showMessage("${mess}");

	function checksubmit(bool){
		document.getElementById("for").action="<%=path%>/business/prod.do?method=InterJFlist&index=${index}&bool="+bool;
		document.getElementById("for").submit();
		if(bool!=-11)
			funLoad();
		return ;
	}
	function funLoad()
	{
		if(!document.getElementById("load"))
		{
			var body=document.getElementsByTagName('body')[0];
			var left=(document.body.offsetWidth-350)/2;
			var div="<div id='load' style='border:1px solid black;background-color:white;position:absolute;top:280px;left:"+left+"px;width:350px;height:90px;z-Index:999;text-align:center;font-weight:bold;'><div style='border-bottom:1px solid #cccccc;height:25px;line-height:25px;text-align:left;padding-left:10px;'>温馨提示</div><div style='width:100%;height:65px;line-height:65px;'><img src='<%=path%>/images/load.gif' />&nbsp;数据加载中，请稍后，，，，</div></div>";
			body.innerHTML=body.innerHTML+div;
		}
	}
	String.prototype.Trim = function() 
	{ 
		return this.replace(/(^\s*)|(\s*$)/g, ""); 
	} 
	function funPage(index)
	{
		document.getElementById("for").action="<%=path%>/business/prod.do?method=InterJFlist&index="+index;
		document.getElementById("for").submit();
		funLoad();
		return ;
	}
	function funPageA(index)
	{
		if(index.Trim().length<=0)
		{
			return ;
		}
		if(isDigit(index)==false)
		{
			index=1;
		}
		funPage(index);
	}
	function ha_add(){
	 document.getElementById("for").action="<%=path%>/employ/interJFadd.jsp";
	 document.getElementById("for").target="_self";
	 document.getElementById("for").submit();
	 return;
	}
	function update(abc,bool){
		$("#updateid").html("处理中");
		document.getElementById("for").action="<%=path%>/business/prod.do?method=InterJFlist&index=${index}&bool="+bool+"&abc="+abc;
		document.getElementById("for").submit();
		funLoad();
		return ;
	}
	
</SCRIPT>
	</head>
	<div id="popdiv">
		<span id="sellist"></span>
		<div style="clear: both"></div>
	</div>
	<body rightmargin="0" leftmargin="0" topmargin="0" class="body_no_bg">
		<form id="for" action="" method="post">
			<div class="header">
				<div class="topCtiy clear">
					<ul>
						<li>
							<div class="sub-input">
								<a class="sia-2 selhover" id="sl-areacode"
									href="javascript:void(0);">地市</a>
							</div>
							<input nname="nname" name="cm_one" id="areacode"
								defaultsel="${requestScope.areaSel }" type="hidden"
								value="${cm_one}" />
						</li>
						<li>
							<div class="sub-input">
								<a class="sia-2 selhover" id="sl-carType"
									href="javascript:void(0);">车辆类型</a>
							</div>
							<input nname="nname" name="carType" id="carType"
								defaultsel="${requestScope.carList }" type="hidden"
								value="${carType}" />
						</li>
						<li>
							<div class="sub-input">
								<a class="sia-2 selhover" id="sl-groups"
									href="javascript:void(0);" style="width: 170px;">佣金组</a>
							</div>
							<input nname="nname" name="groups" id="groups"
								defaultsel="${requestScope.groupsList }" type="hidden"
								value="${groups}" />
						</li>
						<li>
							<input type="button" name="Button" value="查询"
								class="field-item_button" onClick="checksubmit(0);">
						</li>
						<c:if test="${groups!='' && groups!=null}">
							<li>
								<input type="button" name="Button" value="导出"
									class="field-item_button" onClick="checksubmit(-11);"
									id="excelID">
							</li>
						</c:if>
					</ul>
				</div>
			</div>
			<br />
			<div id="" style="width: 98%; border: 1px solid #cccccc;">
				<table id="" style="width: 100%;" border=0 cellspacing=0
					cellpadding=0>
					<thead>
						<tr>
							<th style="border: 1px solid #cccccc; height: 30px;">
								序号
							</th>
							<th style="border: 1px solid #cccccc; height: 30px;">
								地市
							</th>
							<th style="border: 1px solid #cccccc; height: 30px;">
								车辆类型
							</th>
							<th style="border: 1px solid #cccccc; height: 30px;">
								代办费(元)
							</th>
							<th style="border: 1px solid #cccccc; height: 30px;">
								佣金组名称
							</th>
							<th style="border: 1px solid #cccccc; height: 30px;">
								操作
							</th>
						</tr>
					</thead>
					<tbody id="">
						<c:forEach items="${arrList}" var="userinfo" varStatus="index">
							<tr>
								<td style='border: 1px solid #cccccc; height: 25px;'>
									${index.index + 1}
								</td>
								<td style='border: 1px solid #cccccc; height: 25px;'>
									${userinfo[0]}
								</td>
								<td style='border: 1px solid #cccccc; height: 25px;'>
									${userinfo[1]}
								</td>
								<td style='border: 1px solid #cccccc; height: 25px;'>
									${userinfo[2]}
								</td>
								<td style='border: 1px solid #cccccc; height: 25px;'>
									${userinfo[3]}
								</td>
								<td align="center"
									style='border: 1px solid #cccccc; height: 25px;' id='updateid'>
									<a href="javascript:update('${userinfo[4]}',-12)" >修改</a>
								</td> 
							</tr>
						</c:forEach>
					</tbody>
					<tfoot>
						<tr height="30">
							<td colspan="6" class="tr_line_top">
								<c:if test="${index!=null}">
									<div class="grayr">
										<a href="javascript:funPage(1)">首页</a>
										<a href="javascript:funPage(${index-1})">上一页</a>
										<a href="javascript:funPage(${index+1})">下一页</a>
										<a href="javascript:funPage(${lastIndex})">尾页</a>&nbsp;
										跳转&nbsp;
										<input id="pageId" type="text" style="width: 30px"
											value="${index}" onblur="funPageA(this.value)"
											onkeyup="value=value.replace(/[^\d]/g,'') " />
										&nbsp;
										<font>共${lastIndex}页</font>
									</div>
								</c:if>
							</td>

						</tr>
					</tfoot>
				</table>
			</div>
			<p align="center">
				<input name="Button" type="button" value="增加" class="onbutton"
					onClick="ha_add()">
			</p>
		</form>
	</body>
</html>