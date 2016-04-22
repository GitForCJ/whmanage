<%@ page language="java" import="java.util.*,java.io.*,com.wlt.webm.rights.form.SysUserForm"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<jsp:useBean id="tpbean" scope="session"
	class="com.wlt.webm.business.bean.TpBean" />
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

SysUserForm userSession = (SysUserForm) request.getSession().getAttribute("userSession");
List<String[]> arrsList=null;
String mess="";
if("1".equals(userSession.getSrdesc())){
	//一代
	String u=request.getParameter("u");
	String s=request.getParameter("s");
	if(u!=null && !"".equals(u.trim()) && s!=null && !"".equals(s.trim()) && ("0".equals(s) || "1".equals(s))){
		boolean bool=tpbean.OperationAgent(u,s);
		if(bool){
			mess="操作成功";
		}else{
			mess="操作失败";
		}
	}
	arrsList=tpbean.getAgent(userSession.getUserno());
}else{
	//不是一代
}
request.setAttribute("mess",mess);
request.setAttribute("arrys",arrsList);
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">
		<title></title>
		<link href="<%=path%>/css/main_style.css" rel="stylesheet"
			type="text/css" />
		<script type="text/javascript" src="<%=path%>/js/jquery-1.8.2.min.js"></script>
		<script type="text/javascript">
			if("${mess}"!=null && "${mess}"!=""){
				alert("${mess}");
			}
			function funOpenClose(userno,s){
				if(s==0){
					//开启
					if(confirm("您确认开启,系统编号:"+userno+",佣金配置功能")){
					
					}else{
						return ;
					}
				}else{
					//关闭
					if(confirm("您确认关闭,系统编号:"+userno+",佣金配置功能")){
					
					}else{
						return ;
					}
				}
				window.location.href="<%=path%>/business/class_a_agent_commission_allocation.jsp?u="+userno+"&s="+s;
				return ;
			}
		</script>
	</head>

	<body>
		<div id="tabbox">
			<div id="tabs_title">
				<ul class="tabs_wzdh">
					<li>
						一代佣金开关
					</li>
				</ul>
			</div>
		</div>
		<div style="width:auto;height:40px;border:1px solid red;line-height:40px;padding-left:30px;display:none;">
			下级代理商:
			<select style="width:200px;height:25px;">
				<option value="all">全部</option>
				<c:forEach items="${arrys}" var="li">
					<option value="${li[0]}">${li[2]}/${li[3]}</option>				
				</c:forEach>
			</select>
			&nbsp;
			&nbsp;
			<input onclick="" type="button" style="width:85px; height:32px; border:0px;color:#fff; font-weight:bold;background:url(<%=path%>/images/button_out.png) no-repeat bottom; cursor:pointer;" value="查询"/>
		</div>
		<table id="myTable" cellspacing="0" cellpadding="0" class="tab_line"
				style="width: 100%">
				<thead>
					<tr height="25" class="tr_line_bottom">
						<th>序号</th>
						<th>
							代理商名称
						</th>
						<th>
							代理商登陆账号
						</th>
						<th>
							代理商系统账号
						</th>
						<th>
							佣金是否可配置
						</th>
						<th>
							操作
						</th>
					</tr>
				</thead>
				<tbody id="tab">
					<c:forEach items="${arrys}" var="li" varStatus="in">
						<tr>
							<td  class="td_line" style="height:30px;">
								${in.index+1}
							</td>
							<td  class="td_line">
								${li[3]}
							</td>
							<td  class="td_line">
								${li[2]}
							</td>
							<td  class="td_line">
								${li[1]}
							</td>
							<td  class="td_line">
								<c:if test="${li[4]==1}">
									不可配置
								</c:if>
								<c:if test="${li[4]!=1}">
									可配置
								</c:if>
							</td>
							<td  class="td_line">
								<c:if test="${li[4]==1}">
									<a href="javascript:funOpenClose('${li[1]}',0)">开启</a>
								</c:if>
								<c:if test="${li[4]!=1}">
									<a href="javascript:funOpenClose('${li[1]}',1)">关闭</a>
								</c:if>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
	</body>
</html>
