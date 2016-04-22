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
			.td_line{
				width:90px;
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
			function funPage(index) {
				document.getElementById("form1").action="<%=path%>/rights/sysuser.do?method=Account_operation&index="+index;
				document.getElementById("form1").submit();
				funLoad();
				return ;
			}
			function funPageA(index){
				if(index.Trim().length<=0){
					return ;
				}
				if(isDigit(index)==false){
					index=1;
				}
				funPage(index);
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

			function funs(ids,ty){
				if(ty==0){
					if(confirm("操作通过,是否确认?")){
						document.getElementById("form1").action="<%=path%>/rights/sysuser.do?method=Audit_operation&index=${index}&ids="+ids+"&op="+ty;
						document.getElementById("form1").submit();
						funLoad();
						return ;
					}
				}else{
					if(confirm("操作撤销,是否确认?")){
						document.getElementById("form1").action="<%=path%>/rights/sysuser.do?method=Audit_operation&index=${index}&ids="+ids+"&op="+ty;
						document.getElementById("form1").submit();
						funLoad();
						return ;
					}
				}
			}
		</script>
	</head>

	<body>
		<div id="tabbox">
			<div id="tabs_title">
				<ul class="tabs_wzdh">
					<li>
						加款,提现审批
					</li>
				</ul>
			</div>
		</div>

		<form action="" id="form1" method="post">
			<div class="header">
				<div class="topCtiy clear" style="padding-left: 15px;">
					<li>
						<input id="chaxun" type="button" name="Button" value="查询"
							class="field-item_button" onClick="funPage(1)">
					</li>
				</div>
			</div>
			<div class="selCity" id="allCity">
				<table border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td>
							操作人系统账号:
							<input type="text" value="${openation_userno}"
								id="openation_userno" name="openation_userno"
								style="width: 120px; height: 25px;" />
						</td>
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
						<td style="text-align: left;">
							审核人系统账号:
							<input type="text" value="${audit_person_userno}"
								id="audit_person_userno" name="audit_person_userno"
								style="width: 120px; height: 25px;" />
						</td>
					</tr>
				</table>
			</div>

			<table id="myTable" cellspacing="0" cellpadding="0" class="tab_line"
				style="width: 100%">
				<thead>
					<tr height="25" class="tr_line_bottom">
						<th>序号</th>
						<th>
							操作人<br/>系统编号
						</th>
						<th>
							操作类型
						</th>
						<th>
							操作金额
						</th>
						<th>
							操作时间
						</th>
						<th>
							状态
						</th>
						<th>
							被操作人<br/>系统编号
						</th>
						<th>
							审批人<br/>系统编号
						</th>
						<th>
							审批时间
						</th>
						<th>
							操作
						</th>
					</tr>
				</thead>
				<tbody id="tab">
					<c:forEach items="${arrList}" var="li"  varStatus="in">
						<tr>
							<td  class="td_line">
								${in.index+1}
							</td>
							<td class="td_line">
								${li[0]}<br/>${li[1]}
							</td>
							<td class="td_line">
								${li[2]}
							</td>
							<td class="td_line">
								${li[3]}
							</td>
							<td class="td_line">
								${li[4]}
							</td>
							<td class="td_line">
								${li[5]}
							</td>
							<td class="td_line">
								${li[6]}<br/>${li[7]}
							</td>
							<td class="td_line">
								${li[8]}<br/>${li[9]}
							</td>
							<td class="td_line">
								${li[10]}
							</td>
							<td class="td_line" style="padding-top:5px;padding-bottom:5px;">
								<c:if test="${li[12]==-1}">
									<a href="javascript:funs(${li[11]},0)">通过</a>
									<hr/>
									<a href="javascript:funs(${li[11]},-1)">撤销</a>
								</c:if>
							</td>
						</tr>
					</c:forEach>
				</tbody>
				<tfoot>
					<tr height="30">
						<td colspan="10" style="padding-left:20px;">
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
									<font>共${count}条/${lastIndex}页</font>
								</div>
							</c:if>
						</td>
					</tr>
				</tfoot>
			</table>
		</form>
	</body>
</html>
