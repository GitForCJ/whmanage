<br><%@ page language="java" contentType="text/html; charset=GBK"%>
<%@ page import="java.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<jsp:useBean id="userSession" scope="session" class="com.wlt.webm.rights.form.SysUserForm" />
<jsp:useBean id="biProd" scope="page" class="com.wlt.webm.business.bean.BiProd"/>
<%
  String path = request.getContextPath();
  List list = biProd.listAwardsRules(); 
  request.setAttribute("awards",list); 
%>
<html:html>
<head>
<title>���ͨ</title>
<link rel="stylesheet" type="text/css" href=../css/common.css>
<style type="text/css">
.tabs_wzdh { width: 100px; float:left;}
.tabs_wzdh li { height:35px; line-height:35px; margin-top:5px; padding-left:20px; font-size:14px; font-weight:bold;}
/*tree_nav*/
.tree_nav { border:1px solid #CCC; background:#f5f5f5; padding:10px; margin-top:20px;}
.field-item_button_m2 { margin:0 auto; text-align:center;}
.field-item_button2 { border:0px;width:136px; height:32px; color:#fff; background:url(../images/button_out2.png) no-repeat; cursor:pointer;margin-right:20px;}
.field-item_button2:hover { border:0px; width:136px; height:32px; color:#fff; background:url(../images/button_on2.png) no-repeat; margin-right:20px;}
</style>
<script type="text/javascript" src="../js/util.js"></script>
</head>
<script language="JavaScript">
showMessage("${mess}");
var url=window.location.href;

if(url.indexOf("showAwardsAction")<0)
{
	window.location.href="<%=path%>/MonthAwardRule/showMonthAwardRule.do?method=showAwardsAction";
}

	function ha_add(){
		window.location.href="<%=path%>/MonthAwardRule/showMonthAwardRule.do?method=addMonthAwardRule";
		return ;
	}
	
	function isSelectRole()
	{
	  with(document.forms[0])
	  {
		var count = checkedSize(ids);
	  	if(count == 0)
	  	{
	    	alert("��ѡ��Ҫɾ���ļ�¼!");
			return false;
	  	}
	  	return true;
  	 }
	}
	
	function ha_del()
	{
	  with(document.forms[0])
	  {
  		if(isSelectRole())
  		{
  			var count = checkedSize(ids);
			if(count>1)
			{
			 	alert("ֻ��ѡ��һ����¼��������ѡ��");
				return;
			}
	  		var tmp = confirm("��ȷ��Ҫɾ����ѡ�ļ�¼��");
		  	if(tmp == false)
		  	{
		    	return;
		  	}
		  	var arr=document.getElementsByName("ids");
		  	var id=-1;
		  	for(var i=0;i<arr.length;i++)
		  	{
		  		if(arr[i].checked)
		  		{
		  			id=arr[i].value;
		  		}
		  	}
		  	window.location.href="<%=path%>/MonthAwardRule/showMonthAwardRule.do?method=delMonthAwardRule&id="+id;
			return ;
  		}
	  }
	}
	
</script>
<body rightmargin="0" leftmargin="0"  topmargin="0" class="body_no_bg">
<html:form method="post" action="/business/prod.do?method=del">
		<tr class="trcontent">
		<td colspan="4" align="center" class="fontcontitle">&nbsp;<B>����Ӷ�����</B>&nbsp;</td>
		</tr>
<table width="100%" border="0" align="center" cellpadding="0" cellspacing="1" class="tablelist">
  <tr class="trlist">
    <td colspan="9"><div align="center" class="fontlisttitle">����Ӷ�����</div></td>
  </tr>
  <tr class="fontcoltitle">
    <td nowrap="nowrap"><div align="center" >���</div></td>
    <td nowrap="nowrap"><div align="center" >��½�˺�</div></td>
    <td nowrap="nowrap"><div align="center" >��Сֵ</div></td>
    <td nowrap="nowrap"><div align="center" >���ֵ</div></td>
    <td nowrap="nowrap"><div align="center" >��������</div></td>
    <td nowrap="nowrap"><div align="center" >��������</div></td>
    <td ><div align="center"><input name="button1" type="button" onClick="setCheckedState(this.form.ids)" value="ȫѡ" class="onbutton">
        <input type="hidden" name="check" value="0">
      </div></td>
  </tr>
  <c:forEach items="${arryList}" var="arr" varStatus="index">
  <tr class="tr1">
    <td align="center">${index.index+1}</td>
     <td align="center">${arr[0]}</td>
      <td align="center">${arr[1]/1000}Ԫ</td>
       <td align="center">${arr[2]/1000}Ԫ</td>
        <td align="center">${arr[3]}%</td>
         <td align="center">
         	<c:if test="${arr[4]==0}">
         		������
         	</c:if>
         	<c:if test="${arr[4]==1}">
         		����һ
         	</c:if>
         	<c:if test="${arr[4]==2}">
         		�����	
         	</c:if>
         	<c:if test="${arr[4]==3}">
         		������
         	</c:if>
         	<c:if test="${arr[4]==4}">
         		������
         	</c:if>
         </td>
    <td align="center"> <input name="ids" type="checkbox" value="${arr[5]}"></td>
  </tr>
</c:forEach>
</table>
<p align="center">
  <input name="Button" type="button" value="����" class="onbutton" onClick="ha_add()">
  <input type="button" name="Submit2" value="ɾ��" class="onbutton" onClick="ha_del()">
</p>
</html:form>
</body>
</html:html>