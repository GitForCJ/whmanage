<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="/WEB-INF/wlt-tags.tld" prefix="wlt" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<jsp:useBean id="biProd" scope="page" class="com.wlt.webm.business.bean.BiProd"/>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
List arry=biProd.getOneAngent();

request.setAttribute("arryList",arry);
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <title></title>
    <style type="text/css">
    	a:hover{
    		COLOR: #ff0000; TEXT-DECORATION: none;
    	}
    </style>
    <script type="text/javascript">
    	function check()
    	{
    		document.getElementById("for").submit();
    		return ;
    	}
    	// 去空格
		String.prototype.Trim = function() 
		{ 
			return this.replace(/(^\s*)|(\s*$)/g, ""); 
		} 
		
		window.onload=function()
		{
			if("${userForm.zName}"!="" && "${userForm.zName}"!=null){
				document.getElementById("aa").value="${userForm.zName}";
			}
			if("${mess}"!=null && "${mess}"!="" && "${mess}"!="null")
			{
				alert("${mess}");
			}
		}
		
		function funDel(id)
		{
			if(confirm("你确定删除吗?"))
			{
				window.location.href="<%=path %>/business/prod.do?method=oneagentList&zName=${userForm.zName}&curPage=${curPage}&type=-100&setid="+id;
			}
		}
    </script>
  </head>
  <body>
  <form action="<%=path%>/business/prod.do?method=oneagentList" id="for" method="post">
  	<ul style="list-style-type:none;font-size:14px;height:30px;line-height:30px;font-weight:bold;">
  		<li>
  			阶梯组:
  			<select style="width:150px;height:25px;" id="aa" name="zName">
  				<option value="">-- 全部 --</option>
  				<c:forEach items="${arryList}" var="arr">
  					<option value="${arr[0]}">${arr[1]}</option>
  				</c:forEach>
  			</select>
  			&nbsp;
  			&nbsp;
  			<input onclick="check()" type="button" style="width:85px; height:32px; border:0px;color:#fff; font-weight:bold;background:url(<%=path%>/images/button_out.png) no-repeat bottom; cursor:pointer;" value="查询"/>
  		</li>
  	</ul>
  	<table border="0" style="width:100%;font-size:12px;border:1px solid #cccccc;" cellspacing="0" cellpadding="0">
  		<tr style="height:30px;background-color:#E6EFF6;">
  			<th style="text-align:center;border-bottom:1px solid #cccccc;border-right:1px solid #cccccc;">序号</th>
  			<th style="text-align:center;border-bottom:1px solid #cccccc;border-right:1px solid #cccccc;">阶梯组名称</th>
  			<th  style="text-align:center;border-bottom:1px solid #cccccc;border-right:1px solid #cccccc;">交易类型</th>
  			<th style="text-align:center;border-bottom:1px solid #cccccc;border-right:1px solid #cccccc;">面额区间</th>
  			<th style="text-align:center;border-bottom:1px solid #cccccc;border-right:1px solid #cccccc;">佣金比例</th>
  			<th style="text-align:center;border-bottom:1px solid #cccccc;border-right:1px solid #cccccc;">操作</th>
  		</tr>
  		<c:forEach items="${userList}" var="list" varStatus="index">
  			<tr>
  				<td style="text-align:center;height:30px;border-bottom:1px solid #cccccc;border-right:1px solid #cccccc;">${index.index+1 }</td>
  				<td style="text-align:center;height:30px;border-bottom:1px solid #cccccc;border-right:1px solid #cccccc;">${list[1]}</td>
  				<td style="text-align:center;height:30px;border-bottom:1px solid #cccccc;border-right:1px solid #cccccc;">
  					<c:if test="${list[2]==1}">
						本省移动  						
  					</c:if>
  					<c:if test="${list[2]==2}">
						外省移动  						
  					</c:if>
  					<c:if test="${list[2]==3}">
						本省联通  						
  					</c:if>
  					<c:if test="${list[2]==4}">
						外省联通 						
  					</c:if>
  					<c:if test="${list[2]==5}">
						本省电信			
  					</c:if>
  					<c:if test="${list[2]==6}">
						外省电信  						
  					</c:if>
  					<c:if test="${list[2]==7}">
						qq币				
  					</c:if>
  				</td>
  				<td style="text-align:center;height:30px;border-bottom:1px solid #cccccc;border-right:1px solid #cccccc;">${list[3]}</td>
  				<td style="text-align:center;height:30px;border-bottom:1px solid #cccccc;border-right:1px solid #cccccc;">${list[4]}%</td>
  				<td style="text-align:center;height:30px;border-bottom:1px solid #cccccc;border-right:1px solid #cccccc;">
  					<a href="javascript:funDel(${list[5]})" style="font-size:13px;">删除</a>
  				</td>
  			</tr>
  		</c:forEach>
  		
  	</table>
<tfoot>
			<tr >
				<td colspan="7" >
					<div class="grayr" style="text-align:right;height:35px;line-height:35px;font-size:12px;">
					 <wlt:pager url="business/prod.do?method=oneagentList&zName=${userForm.zName}" page="${requestScope.page}" style="black" />
					</div>
				</td>
			</tr>
		</tfoot>

<p align="center">
	<input onClick="javascript:window.location.href='<%=path %>/setup/setupadd.jsp?zName=${userForm.zName}&curPage=${curPage}'" type="button" style="width:85px; height:32px; border:0px;color:#fff; font-weight:bold;background:url(<%=path%>/images/button_out.png) no-repeat bottom; cursor:pointer;" value="增加"/>
</p>
</form>
  </body>
</html>
