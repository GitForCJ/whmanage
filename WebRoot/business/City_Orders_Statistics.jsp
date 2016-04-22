<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">
		<link href="<%=path%>/css/main_style.css" rel="stylesheet"
			type="text/css" />
		<style type="text/css">
.td_line {
	width: 90px;
}
</style>
		<script language="javascript" type="text/javascript"
			src="<%=path%>/My97DatePicker/WdatePicker.js"></script>
		<script type="text/javascript" src="<%=path%>/js/util.js"></script>

		<script type="text/javascript">
			if("${mess}"!=null && "${mess}"!=""){
				alert("${mess}");
			}
		
			// 去空格
			String.prototype.Trim = function(){ 
				return this.replace(/(^\s*)|(\s*$)/g, ""); 
			} 
			function funPage(ins) {
				var indate=document.getElementById("indate").value;
				var enddate=document.getElementById("enddate").value;
				if(indate.Trim()==""){
					alert("请选择开始日期!");
					return ;
				}
				if(enddate.Trim()==""){
					alert("请选择结束日期!");
					return ;
				}
				document.getElementById("form1").action="<%=path%>/rights/sysuser.do?method=Statistics_info&opType="+ins;
				document.getElementById("form1").submit();
				if(ins==0){
					funLoad();
				}
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
		</script>
	</head>

	<body>
		<div id="tabbox">
			<div id="tabs_title">
				<ul class="tabs_wzdh">
					<li>
						地市交易统计
					</li>
				</ul>
			</div>
		</div>

		<form action="" id="form1" method="post">
			<input type="hidden" value="2" name="reType"/>
			<div class="header">
				<div class="topCtiy clear" style="padding-left: 15px;">
					<li>
						<input id="chaxun" type="button" name="Button" value="查询"
							class="field-item_button" onClick="funPage(0)">
					</li>
					<li>
						<input id="chaxun" type="button" name="Button" value="导出"
							class="field-item_button" onClick="funPage(1)">
					</li>
				</div>
			</div>
			<div class="selCity" id="allCity">
				<table border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td>
							统计日期:
							<input class="Wdate" id="indate" name="indate" type="text"
								style="width: 120px; height: 25px;" onClick="WdatePicker()"
								value="${indate}">
							至
							<input class="Wdate" id="enddate" name="enddate" type="text"
								style="width: 120px; height: 25px;" onClick="WdatePicker()"
								value="${enddate}">
						</td>
					</tr>
				</table>
			</div>

			<table id="myTable" cellspacing="0" cellpadding="0" class="tab_line"
				style="width: 100%">
				<thead>
					<tr height="25" class="tr_line_bottom">
						<th>
							地市名称
						</th>
						<th>
							交易笔数
						</th>
						<th>
							交易总额
						</th>
					</tr>
				</thead>
				<tbody id="tab">
					<c:forEach items="${arrList}" var="li" varStatus="in">
						<tr>
							<td class="td_line">
								${li[0]}
							</td>
							<td class="td_line">
								${li[1]}
							</td>
							<td class="td_line">
								${li[2]}
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</form>
	</body>
</html>
