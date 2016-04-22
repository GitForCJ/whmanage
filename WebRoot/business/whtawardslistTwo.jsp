<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@ page import="java.util.*"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%
  String path = request.getContextPath();
%>
<html>
<head>
<title>���ͨ</title>

<script type="text/javascript" src="../js/jquery-1.8.2.min.js"></script>
<script type="text/javascript" src="../js/util.js"></script>
<script language="javascript" type="text/javascript" src="../My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript">
	window.onload=function()
	{
		document.getElementById("state").value=${acctbill.state};
	}
	
	//��ʾ��
	function funLoad()
	{
		if(!document.getElementById("load"))
		{
			var body=document.getElementsByTagName('body')[0];
			var left=(document.body.offsetWidth-350)/2;
			var div="<div id='load' style='border:1px solid black;background-color:white;position:absolute;top:280px;left:"+left+"px;width:350px;height:90px;z-Index:999;text-align:center;font-weight:bold;'><div style='border-bottom:1px solid #cccccc;height:25px;line-height:25px;text-align:left;padding-left:10px;font-size:13px;'>��ܰ��ʾ</div><div style='width:100%;height:65px;line-height:65px;font-size:13px;'><img src='<%=path%>/images/load.gif' />&nbsp;���ݼ����У����Ժ󣬣�����</div></div>";
			body.innerHTML=body.innerHTML+div;
		}
	}
	// ȥ�ո�
	String.prototype.Trim = function() 
	{ 
		return this.replace(/(^\s*)|(\s*$)/g, ""); 
	}
	//��ѯ
	function funSelect()
	{
		document.getElementById("for").submit();
		funLoad();
		return ;
	}
	//����
	function funCheck(login,yearMoneth,facct,bs,className)
	{
		className.disabled="disabled";
		className.value="������";
		var money=0;
		var strs=document.getElementsByName("money_"+login+"_"+yearMoneth+"_"+bs);
		for(var i=0;i<strs.length;i++)
		{
			var sa=strs[i].innerHTML;
			sa=sa.replace(",","");
			money= parseFloat(money)+ parseFloat(sa==""?0:sa);
		}
		if(money<=0)
		{
			alert("�������С��0Ԫ�����ܽ�����");
			className.disabled="";
			className.value="����";
			return;
		}
		if(confirm("ȷ�϶�"+login+"�˺Ž��н���?������"+money.toFixed(3)+" Ԫ,�����·�:"+yearMoneth))
		{
			document.getElementById("for").action="<%=path %>/business/acctbill.do?method=awardsListTwo&login="+login+"&facct="+facct+"&money="+money.toFixed(3)+"&yearMoneth="+yearMoneth+"&bs="+bs;
			document.getElementById("for").submit();
			return ;
		}
		else
		{
			className.disabled="";
			className.value="����";
		}
	}
</script>
</head>
<body style="margin:0px 0px 0px 0px;padding:0px 0px 0px 0px;">
	<form action="<%=path %>/business/acctbill.do?method=awardsListTwo" method="post" id="for">
	    <ul style="padding-left:0px;height:40px;border:1px solid #e1e2e2;margin-top:0px;background:#f8f8f8;">
	      <li style="line-height:40px;padding-left:20px;list-style-type:none;font-size:14px;font-weight:bold;">��������(һ��������)
	      </li>
	    </ul>
	    <ul style="padding-left:10px;height:32px;margin-bottom:0px;">
	    	<li>
	    		<input onclick="funSelect()" type="button"  style="width:85px; height:32px; border:0px;color:#fff; font-weight:bold;background:url(<%=path%>/images/button_out.png) no-repeat bottom; cursor:pointer;" value="��ѯ"/>
	    	</li>
	    </ul>
	    <div style="height:80px;border:1px solid #cccccc;margin-top:0px;padding:0px 0px 0px 0px;">
	    	<ul style="margin:0px 0px 0px 0px;padding:0px 0px 0px 0px;text-align:center;">
		     	<li style="list-style-type:none;height:80px;line-height:80px;font-size:14px;">
		     		��ʼ����:<input style="width:120px;height:25px;" class="Wdate" name="startDate" type="text" onClick="WdatePicker()" value="${requestScope.acctbill.startDate }">
					&nbsp;
					��������:<input style="width:120px;height:25px;" class="Wdate" name="endDate" type="text" onClick="WdatePicker()" value="${requestScope.acctbill.endDate }">
					&nbsp;
					����״̬:
					<select style="width:120px;height:25px;" name="state" id="state">
						<option value="-1">-- ��ѡ�� --</option>
						<option value="0">�ѽ���</option>
						<option value="1">δ����</option>
					</select>
				</li>
		    </ul>
	    </div>
		<table border="0" style="width:100%;font-size:12px;border:1px solid #cccccc;margin-top:10px;" cellspacing="0" cellpadding="0">
	  		<tr style="height:30px;background-color:#E6EFF6;">
				<th style="text-align:center;border-bottom:1px solid #cccccc;border-right:1px solid #cccccc;">�û��˺�</th>
				<th style="text-align:center;border-bottom:1px solid #cccccc;border-right:1px solid #cccccc;">ҵ������</th>
				<th style="text-align:center;border-bottom:1px solid #cccccc;border-right:1px solid #cccccc;">�ܶ�(Ԫ)</th>
				<th style="text-align:center;border-bottom:1px solid #cccccc;border-right:1px solid #cccccc;">�������(Ԫ)</th>
				<th style="text-align:center;border-bottom:1px solid #cccccc;border-right:1px solid #cccccc;">��������</th>
				<th style="text-align:center;border-bottom:1px solid #cccccc;border-right:1px solid #cccccc;">������</th>
				<th style="text-align:center;border-bottom:1px solid #cccccc;border-right:1px solid #cccccc;">�����·�</th>
				<th style="text-align:center;border-bottom:1px solid #cccccc;border-right:1px solid #cccccc;">״̬</th>
				<th style="text-align:center;border-bottom:1px solid #cccccc;">����</th>
			</tr>
			<c:forEach items="${arryList}" var="list" varStatus="index">
				<c:if test="${index.index%2==0}">
					<tr style="background:#f8f8f8;">
				</c:if>
				<c:if test="${index.index%2!=0}">
					<tr>
				</c:if>
					<c:if test="${index.index%7==0}">
						<td rowspan=7  style="text-align:center;height:30px;border-bottom:1px solid #cccccc;border-right:1px solid #cccccc;font-size:14px;font-weight:bold;">${list[0]}<br/><br/>(${list[2]})</td>
					</c:if>
					<td  style="text-align:center;height:30px;border-bottom:1px solid #cccccc;border-right:1px solid #cccccc;">${list[3]}</td>
					<td  style="text-align:center;height:30px;border-bottom:1px solid #cccccc;border-right:1px solid #cccccc;">${list[4]/1000}</td>
					<td  style="text-align:center;height:30px;border-bottom:1px solid #cccccc;border-right:1px solid #cccccc;" name="money_${list[0]}_${list[8]}_${list[11]}">${list[5]}</td>
					<td  style="text-align:center;height:30px;border-bottom:1px solid #cccccc;border-right:1px solid #cccccc;">${list[6]}</td>
					<td  style="text-align:center;height:30px;border-bottom:1px solid #cccccc;border-right:1px solid #cccccc;">${list[7]}</td>
					<c:if test="${index.index%7==0}">
						<td rowspan=7  style="text-align:center;height:30px;border-bottom:1px solid #cccccc;border-right:1px solid #cccccc;font-size:14px;font-weight:bold;">${list[8]}</td>
					</c:if>
					<c:if test="${index.index%7==0}">
						<td rowspan=7  style="text-align:center;height:30px;border-bottom:1px solid #cccccc;border-right:1px solid #cccccc;font-size:14px;font-weight:bold;color:red">
							<c:if test="${list[9]==1}">
								δ����
							</c:if>
							<c:if test="${list[9]==0}">
								�ѽ���<br/><br/>(${list[12]}Ԫ)
							</c:if>
						</td>
					</c:if>
					<c:if test="${index.index%7==0}">
						<td rowspan=7  style="text-align:center;height:30px;border-bottom:1px solid #cccccc;font-size:14px;font-weight:bold;color:red">
							<c:if test="${list[9]==1}">
								<input type="button" style="width:60px;height:25px;" value="����" onclick="funCheck('${list[0]}','${list[8]}','${list[10]}','${list[11]}',this)"/>
							</c:if>
						</td>
					</c:if>
				</tr>
			</c:forEach>
		</table>
	</form>
</body>
</html>