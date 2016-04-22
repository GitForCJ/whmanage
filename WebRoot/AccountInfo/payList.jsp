<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@ page import="java.util.*"%>
<%@ page import="com.wlt.webm.rights.form.SysUserForm"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
SysUserForm userSession = (SysUserForm) request.getSession().getAttribute("userSession");
  String path = request.getContextPath();
%>
<html>
<head>
<title>���ͨ</title>
<link href="../css/main_style.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="../js/jquery-1.8.2.min.js"></script>
<script type="text/javascript" src="../js/util.js"></script>
<script language="javascript" type="text/javascript" src="../My97DatePicker/WdatePicker.js"></script>
<style type="text/css">
	#tables td{
		padding:0px 0px 0px 0px;
		border:1px solid #cccccc;
		border-right:0px;
		border-bottom:0px;
		height:30px;
	}
</style>
<script type="text/javascript">
showMessage("${mess}");
	//��ʾ��
	function funLoad()
	{
		if(!document.getElementById("load"))
		{
			var body=document.getElementsByTagName('body')[0];
			var left=(document.body.offsetWidth-350)/2;
			var div="<div id='load' style='border:1px solid black;background-color:white;position:absolute;top:280px;left:"+left+"px;width:350px;height:90px;z-Index:999;text-align:center;font-weight:bold;'><div style='border-bottom:1px solid #cccccc;height:25px;line-height:25px;text-align:left;padding-left:10px;'>��ܰ��ʾ</div><div style='width:100%;height:65px;line-height:65px;'><img src='<%=path%>/images/load.gif' />&nbsp;���ݼ����У����Ժ󣬣�����</div></div>";
			body.innerHTML=body.innerHTML+div;
		}
	}
	// ȥ�ո�
	String.prototype.Trim = function() 
	{ 
		return this.replace(/(^\s*)|(\s*$)/g, ""); 
	} 
	function checksubmit()
	{
		
		document.getElementById("form1").action="<%=path %>/business/acctbill.do?method=OneOrderList&subtype=0";
		document.getElementById("form1").submit();
		funLoad()
		return ;
	}
	
	function excelExport()
	{
		var ahidden=document.getElementById("ahidden");
		if(ahidden.value.Trim()=="")
		{
			alert("���Ȳ�ѯ�б�!");
			return ;
		}
	//	alert(ahidden.value.Trim());
		var arrlist=ahidden.value.Trim().split(";");
		document.getElementById("accountName").value=arrlist[0];
		document.getElementById("indate").value=arrlist[1];
		document.getElementById("enddate").value=arrlist[2];
		document.getElementById("inmoney").value=arrlist[3];
		document.getElementById("endmoney").value=arrlist[4];
		document.getElementById("OrderStateType").value=arrlist[5];
		document.getElementById("form1").action="<%=path %>/business/acctbill.do?method=OneOrderList&subtype=1";
		document.getElementById("form1").submit();
		return;
	}
	
	window.onload=function()
	{
		if("${OrderStateType}"=="")
		{
			document.getElementById("OrderStateType").value="-100";
		}
		else
		{
			document.getElementById("OrderStateType").value="${OrderStateType}";
		}
		if("${accountName}"!="" && "${accountName}"!=null && "${accountName}"!="null")
			document.getElementById("ahidden").value="${accountName};${indate};${enddate};${inmoney};${endmoney};${OrderStateType}";
	}
</script>

</head>
<body>
<div id="tabbox">
  <div id="tabs_title">
    <ul class="tabs_wzdh">
      <li>����ͳ��</li></ul>
  </div>
</div>
<form id="form1" action="" method="post">
<div class="header">
	<div class="topCtiy clear" style="padding-left:15px;">
		<li><input id="chaxun" type="button" name="Button" value="��ѯ" class="field-item_button" onClick="checksubmit()"></li>
		<li><input id="daochu" type="button" name="Button" value="����" class="field-item_button" onClick="excelExport();"></li>
	</div>
</div>
<div class="selCity" id="allCity" >
    <table  border="0" cellspacing="0" cellpadding="0">
		<tr>
			<%
				if("3".equals(userSession.getRoleType()) || "4".equals(userSession.getRoleType()))
				{
				%>
					<input type="hidden" value="<%=userSession.getUsername() %>" id="accountName" name="accountName" />
				<%
				}
				else
				{
					%>
					<td >
						�����˺�:<input type="text" value="${accountName}" id="accountName" name="accountName" style="width:120px;height:25px;" maxlength="11" />
					</td>
					<%
				}
			 %>
			
		    <td >
		    	ͳ������:<input class="Wdate" id="indate" name="indate" type="text" style="width:120px;height:25px;" onClick="WdatePicker()" value="${indate}">
		    		��
		   			<input class="Wdate" id="enddate" name="enddate" type="text" style="width:120px;height:25px;" onClick="WdatePicker()" value="${enddate}">
		    </td>
		    <td>
		    	���:<input type="text" style="width:50px;height:25px;" value="${inmoney}" name="inmoney" maxlength="5" id="inmoney" onkeyup="value=value.replace(/[^\d]/g,'') "/>
		    	��
		    	<input type="text"  style="width:50px;height:25px;" value="${endmoney}" name="endmoney" maxlength="5" id="endmoney" onkeyup="value=value.replace(/[^\d]/g,'') "/>
		    </td>
		</tr>
		<tr>
			<td style="text-align:left;">
		    	����״̬:<select style="width:120px;height:25px;" name="OrderStateType" id="OrderStateType" >
		    		<option value="-100" >ȫ������</option>
		    		<option value="0" >���׳ɹ�</option>
		    		<option value="1">����ʧ��</option>
		    		<option value="2">����δ��Ӧ</option>
		    		<option value="3">����δ����</option>
		    		<option value="4">���״�����</option>
		    		<option value="5">���׳���</option>
		    		<option value="6">�쳣����</option>
		    		<option value="7">�������˷�</option>
		    	</select>
		    </td>
		    <td></td>
		    <td></td>
		</tr>
	</table>
</div>
<div style="width:100%;height:30px;text-align:right;font-weight:bold;line-height:30px;font-size:15px;color:red;">
	��֧�ֿ���ͳ������&nbsp;&nbsp;
</div>
<div id="tables" style="width:100%;overflow-x:auto;">
	<table  cellspacing="0" cellpadding="0" width="100%"  style="font-size:13px;border-bottom:1px solid #cccccc;border-right:1px solid #cccccc" >
		<tr style="font-weight:bold;background-color:#E6EFF6;">
			<td rowspan=3 width=50>&nbsp;</td>
			
			<td colspan="4">�ƶ���ֵ</td>
			<td colspan="4">��ͨ��ֵ</td>
			<td colspan="4">���ų�ֵ</td>
			
			<td colspan="2">Q�ҳ�ֵ</td>
			<td colspan="2">��Ϸ��ֵ</td>
			<td colspan="2">֧������ֵ</td>
			<td colspan="2">����</td>
		</tr>
		<tr style="font-weight:bold;background-color:#E6EFF6;">
			<td style="" colspan="2">��ʡ</td>
			<td style="" colspan="2">��ʡ</td>
			
			<td style="" colspan="2">��ʡ</td>
			<td style="" colspan="2">��ʡ</td>
			
			<td style="" colspan="2">��ʡ</td>
			<td style="" colspan="2">��ʡ</td>
			
			<td style="" rowspan=2>��<br/>��</td>
			<td style="" rowspan=2>���<br/>(Ԫ)</td>
			<td style="" rowspan=2>��<br/>��</td>
			<td style="" rowspan=2>���<br/>(Ԫ)</td>
			<td style="" rowspan=2>��<br/>��</td>
			<td style="" rowspan=2>���<br/>(Ԫ)</td>
			<td style="" rowspan=2>��<br/>��</td>
			<td style="" rowspan=2>���<br/>(Ԫ)</td>
		</tr>
		<tr style="background-color:#E6EFF6; font-weight:bold;">
			<td style="">��<br/>��</td>
			<td style="">���<br/>(Ԫ)</td>
			
			<td style="">��<br/>��</td>
			<td style="">���<br/>(Ԫ)</td>
			
			<td style="">��<br/>��</td>
			<td style="">���<br/>(Ԫ)</td>
			
			<td style="">��<br/>��</td>
			<td style="">���<br/>(Ԫ)</td>
			
			<td style="">��<br/>��</td>
			<td style="">���<br/>(Ԫ)</td>
			
			<td style="">��<br/>��</td>
			<td style="">���<br/>(Ԫ)</td>
		</tr>
		<c:forEach items="${accountList}" var="arrList" varStatus="index">
			<c:if test="${(index.index+1)%2==0}">
				<tr style="color:red;">
			</c:if>
			<c:if test="${(index.index+1)%2!=0}">
				<tr style="color:red;border:1px solid #cccccc">
			</c:if>
				<td style="color:black;word-break:break-all;">${arrList[0]}<br/>${arrList[1]}</td>
				<td  style="">${arrList[2]}&nbsp;</td>
				<td  style=""><fmt:formatNumber value="${arrList[3]}" pattern="#0.0"/>&nbsp;</td>
				<td style="">${arrList[4]}&nbsp;</td>
				<td style=""><fmt:formatNumber value="${arrList[5]}" pattern="#0.0"/>&nbsp;</td>
				
				<td style="">${arrList[6]}&nbsp;</td>
				<td style="" ><fmt:formatNumber value="${arrList[7]}" pattern="#0.0"/>&nbsp;</td>
				<td style="">${arrList[8]}&nbsp;</td>
				<td style=""><fmt:formatNumber value="${arrList[9]}" pattern="#0.0"/>&nbsp;</td>
				
				<td  style="">${arrList[10]}&nbsp;</td>
				<td style="" ><fmt:formatNumber value="${arrList[11]}" pattern="#0.0"/>&nbsp;</td>
				<td style="">${arrList[12]}&nbsp;</td>
				<td style=""><fmt:formatNumber value="${arrList[13]}" pattern="#0.0"/>&nbsp;</td>
				
				<td style="" >${arrList[14]}&nbsp;</td>
				<td  style=""><fmt:formatNumber value="${arrList[15]}" pattern="#0.0"/>&nbsp;</td>
				<td  style="">${arrList[16]}&nbsp;</td>
				<td  style=""><fmt:formatNumber value="${arrList[17]}" pattern="#0.0"/>&nbsp;</td>
				<td  style="">${arrList[18]}&nbsp;</td>
				<td style="" ><fmt:formatNumber value="${arrList[19]}" pattern="#0.0"/>&nbsp;</td>
				<td style="" >${arrList[20]}&nbsp;</td>
				<td  style=""><fmt:formatNumber value="${arrList[21]}" pattern="#0.0"/>&nbsp;</td>
			</tr>
		</c:forEach>
		<tr style="background-color:yellow;color:red;font-weight:bold;border:1px solid #cccccc">
				<td  style="word-break:break-all;">${strsList[1]}</td>
				<td  style=""><fmt:formatNumber value="${strsList[2]}" pattern="#0"/></td>
				<td  style=""><fmt:formatNumber value="${strsList[3]}" pattern="#0.0"/></td>
				<td  style=""><fmt:formatNumber value="${strsList[4]}" pattern="#0"/></td>
				<td  style=""><fmt:formatNumber value="${strsList[5]}" pattern="#0.0"/></td>
				
				<td  style=""><fmt:formatNumber value="${strsList[6]}" pattern="#0"/></td>
				<td  style=""><fmt:formatNumber value="${strsList[7]}" pattern="#0.0"/></td>
				<td  style=""><fmt:formatNumber value="${strsList[8]}" pattern="#0"/></td>
				<td  style=""><fmt:formatNumber value="${strsList[9]}" pattern="#0.0"/></td>
				
				<td  style=""><fmt:formatNumber value="${strsList[10]}" pattern="#0"/></td>
				<td  style=""><fmt:formatNumber value="${strsList[11]}" pattern="#0.0"/></td>
				<td  style=""><fmt:formatNumber value="${strsList[12]}" pattern="#0"/></td>
				<td  style=""><fmt:formatNumber value="${strsList[13]}" pattern="#0.0"/></td>
				
				<td  style=""><fmt:formatNumber value="${strsList[14]}" pattern="#0"/></td>
				<td  style=""><fmt:formatNumber value="${strsList[15]}" pattern="#0.0"/></td>
				
				<td  style=""><fmt:formatNumber value="${strsList[16]}" pattern="#0"/></td>
				<td  style=""><fmt:formatNumber value="${strsList[17]}" pattern="#0.0"/></td>
				<td  style=""><fmt:formatNumber value="${strsList[18]}" pattern="#0"/></td>
				<td  style=""><fmt:formatNumber value="${strsList[19]}" pattern="#0.0"/></td>
				<td  style=""><fmt:formatNumber value="${strsList[20]}" pattern="#0"/></td>
				<td  style=""><fmt:formatNumber value="${strsList[21]}" pattern="#0.0"/></td>
				
			</tr>
	</table>
</div>
</form>
<input type="hidden" id="ahidden" value=""/>
</body>
</html>