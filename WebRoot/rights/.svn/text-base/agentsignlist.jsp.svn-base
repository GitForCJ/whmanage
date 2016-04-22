<br><%@ page language="java" contentType="text/html; charset=GBK"%>
<%@ taglib uri="/WEB-INF/wlt-tags.tld" prefix="wlt" %>
<%@ page import="java.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<jsp:useBean id="userSession" scope="session" class="com.wlt.webm.rights.form.SysUserForm" />
<jsp:useBean id="biProd" scope="page" class="com.wlt.webm.business.bean.BiProd"/>
<%
  String path = request.getContextPath();
%>
<html:html>
<head>
<title>万汇通</title>
<link rel="stylesheet" type="text/css" href=<%=path%>/css/common.css>
<style type="text/css">
.tabs_wzdh { width: 100px; float:left;}
.tabs_wzdh li { height:35px; line-height:35px; margin-top:5px; padding-left:20px; font-size:14px; font-weight:bold;}
/*tree_nav*/
.tree_nav { border:1px solid #CCC; background:#f5f5f5; padding:10px; margin-top:20px;}
.field-item_button_m2 { margin:0 auto; text-align:center;}
.field-item_button2 { border:0px;width:136px; height:32px; color:#fff; background:url(../images/button_out2.png) no-repeat; cursor:pointer;margin-right:20px;}
.field-item_button2:hover { border:0px; width:136px; height:32px; color:#fff; background:url(../images/button_on2.png) no-repeat; margin-right:20px;}
td{border:1px inset #000;}
</style>
<script type="text/javascript" src="<%=path%>/js/util.js"></script>
</head>
<script language="JavaScript">
showMessage("${mess}");
//var url=window.location.href;

//if(url.indexOf("showAwardsAction")<0)
//{
//	window.location.href="/MonthAwardRule/showMonthAwardRule.do?method=showAwardsAction";
//}

	function ha_add(){
		window.location.href="<%=path%>/rights/agentsignAddUpdate.jsp?type=0";
		return ;
	}
	function ha_update(var0,var1,var2,var3,var4,var5,var6,var8,var9,var10,var11,var12,var13)
	{
		if(var13==""){
			var13="------";
		}
		var str=var0+";"+var1+";"+var2+";"+var3+";"+var4+";"+var5+";"+var6+";"+var8+";"+var9+";"+var10+";"+var11+";"+var12+";"+var13;
		window.location.href="<%=path%>/rights/agentsignAddUpdate.jsp?type=1&str="+str;
		return ;
	}
	
</script>
<body rightmargin="0" leftmargin="0"  topmargin="0" class="body_no_bg">
<html:form method="post" action="/business/prod.do?method=del">	
		<tr class="trcontent">
		<td colspan="4" align="center" class="fontcontitle">&nbsp;<B>接口商配置</B>&nbsp;</td>
		</tr>
<table width="100%" border="0" align="center" cellpadding="2" cellspacing="1" border="1" bordercolor="#000000">
  <tr class="trlist">
    <td colspan="14"><div align="center" class="fontlisttitle">接口商配置</div></td>
  </tr>
  <tr class="fontcoltitle">
    <td nowrap="nowrap"><div align="center" >编号</div></td>
    <td nowrap="nowrap"><div align="center" >登陆账户</div></td>
      <td nowrap="nowrap"><div align="center" >名称</div></td>
    <td nowrap="nowrap"><div align="center" >签名</div></td>
    <td nowrap="nowrap"><div align="center" >校验码</div></td>
    <td nowrap="nowrap"><div align="center" >IP地址</div></td>
     <td nowrap="nowrap"><div align="center" >Q币佣金</div></td>
     <td nowrap="nowrap"><div align="center" >话费组</div></td>
     <td nowrap="nowrap"><div align="center" >交罚组</div></td>
     <td nowrap="nowrap"><div align="center" >流量组</div></td>
    <td nowrap="nowrap"><div align="center" >Q币通道</div></td>
    <td nowrap="nowrap"><div align="center" >Q币拆单</div></td>
     <td nowrap="nowrap"><div align="center" >接口商</div></td>
      <td nowrap="nowrap"><div align="center" >操作</div></td>
  </tr>
  <c:forEach items="${tplist}" var="arr" varStatus="index">
  	<tr>
  		<td align="center">${arr[0]}</td>
  		<td align="center">${arr[6]}</td>
  		<td align="center">${arr[5]}</td>
  		<td align="center">${arr[1]}</td>
  		<td align="center">${arr[2]}</td>
  		<td align="center">${arr[3]}</td>
  		<td align="center">${arr[8]}</td>
  		<td align="center">${arr[10]}</td>
  		<td align="center">${arr[11]}</td>
  		<td align="center">${arr[12]}</td>
  		<td align="center">${arr[7]==0?"单通道":"多通道"}</td>
  		<td align="center">${arr[9]==0?"拆单":"不拆单"}</td>
  		<td align="center">${arr[4]==0?"可用":"不可用"}</td>
  		<td  align="center"><input name="Button" type="button" value="修改" class="onbutton" onClick=ha_update('"${arr[0]}"','"${arr[1]}"','"${arr[2]}"','"${arr[3]}"',"${arr[4]}",'"${arr[5]}"','"${arr[7]}"','"${arr[8]}"','"${arr[9]}"','"${arr[10]}"','"${arr[11]}"','"${arr[12]}"','${arr[13]}')></td>
  	</tr>
</c:forEach>
<tfoot>
	<tr height="30" >
		<td colspan="14" class="tr_line_top">
			<div class="grayr">
				<wlt:pager url="../tpcharge.do?method=agentsignlist"
					page="${requestScope.page}" style="black" />
			</div>
		</td>
	</tr>
</tfoot>
</table>
<p align="center">
  <input name="Button" type="button" value="增加" class="onbutton" onClick="ha_add()">
</p>
</html:form>
</body>
</html:html>