<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@ taglib uri="/WEB-INF/wlt-tags.tld" prefix="wlt"%>
<%@ page import="java.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
%>
<html>
	<head>
		<title>���ͨ</title>
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

.odd {
	background-color: #A3A3A3;
}

.even {
	background-color: #A3A3A3;
}
</style>
		<script type="text/javascript" src="../js/jquery-1.8.2.min.js"></script>
		<script type="text/javascript" src="../js/util.js"></script>
		<SCRIPT src="../js/more_tiaojian.js" type=text/javascript></SCRIPT>
		<link href="../css/selectCss.css" rel="stylesheet" type="text/css" />
		<script src="../js/selectCss.js" type="text/javascript"></script>
		<script language="javascript" type="text/javascript"
			src="../My97DatePicker/WdatePicker.js"></script>
		<SCRIPT type=text/javascript>
jQuery(function(){
	//$("#changeCity").click(function(a){$("#allCity").slideDown(0);a.stopPropagation();$(this).blur()});$("#allCity").click(function(a){a.stopPropagation()});$(document).click(function(a){a.button!=2&&$("#allCity").slideUp(0)});$("#foldin").click(function(){$("#allCity").slideUp(0)})
 $("#myTable tr:odd").addClass("odd");//oddΪż����
 // $("#myTable tr:even").addClass("even")//evenΪż����
});

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
function funPage(index)
{
	document.getElementById("form1").action="<%=path%>/business/order.do?method=caidanList&index="+index;
	document.form1.submit();
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
function checksubmit(){
	document.getElementById("form1").action="<%=path%>/business/order.do?method=caidanList&index=${index}";
	document.form1.submit();
	funLoad();
	return ;
}

</SCRIPT>
	</head>
	<body rightmargin="0" leftmargin="0" topmargin="0" class="body_no_bg">
		<div id="popdiv">
			<span id="sellist"></span>
			<div style="clear: both"></div>
		</div>
		<div id="tabbox">
			<div id="tabs_title">
				<ul class="tabs_wzdh">
					<li>
						Q�Ҳ�
					</li>
				</ul>
			</div>
		</div>
		<form id="form1" name="form1"
			action="" method="post">
			<div class="header">
				<div class="topCtiy clear">
					<ul>
						<li style="padding-left: 15px;">
							<input id="chaxun" type="button" name="Button" value="��ѯ"
								class="field-item_button" onClick="checksubmit()">
						</li>
					</ul>
				</div>
			</div>
			<div class="selCity">
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td>
							ϵͳ���:
						</td>
						<td>
							<input name="userno" id="userno" value="${userno}" />
						</td>
						<td>
							��ʼ���ڣ�
						</td>
						<td>
							<input class="Wdate" name="startDate" id="startDate" type="text"
								onClick="WdatePicker()" value="${startDate }">
						</td>
						<td>
							�������ڣ�
						</td>
						<td>
							<input class="Wdate" name="endDate" id="endDate" type="text"
								onClick="WdatePicker()" value="${endDate }">
						</td>
					</tr>
					<tr>
						<td>
							��ֵ����:
						</td>
						<td>
							<input name="tradeobject" id="tradeobject" type="text" value="${tradeobject }" />
						</td>
						<td>
							 ����������: 
						</td>
						<td>
							<input name="oldorderid" id="oldorderid" type="text" value="${oldorderid }" /> 
						</td>
						<td>
							&nbsp;
						</td>
						<td>
							&nbsp;
						</td>
					</tr>
				</table>
			</div>
			<table id="myTable" cellspacing="0" cellpadding="0" class="tab_line">
				<thead>
					<tr height="25" class="tr_line_bottom">
						<th>
							����������
						</th>
						<th>
							������
						</th>
						<th>
							���׺���
						</th>
						<th>
							�ӿ�����
						</th>
						<th>
							ҵ������
						</th>
						<th>
							���׽��(Ԫ)
						</th>
						<th>
							����ʱ��
						</th>
						<th>
							����״̬
						</th>
						<th>
							ϵͳ���
						</th>
					</tr>
				</thead>
				<tbody id="tab">
					<c:forEach items="${arrList}" var="arr">
						<tr>
							<td style="width:120px">${arr[0]}</td>
							<td style="width:120px">${arr[1]}</td>
							<td>${arr[2]}</td>
							<td>${arr[3]}</td>
							<td>${arr[4]}</td>
							<td>${arr[5]}</td>
							<td>${arr[6]}</td>
							<td>${arr[7]}</td>
							<td>${arr[8]}</td>
						</tr>
					</c:forEach>
				</tbody>
				<tfoot>
					<tr height="30">
						<td colspan="9" class="tr_line_top">
							<c:if test="${index!=null}">
								<div class="grayr">
									<a href="javascript:funPage(1)">��ҳ</a>
									<a href="javascript:funPage(${index-1})">��һҳ</a>
									<a href="javascript:funPage(${index+1})">��һҳ</a>
									<a href="javascript:funPage(${lastIndex})">βҳ</a>&nbsp;
									��ת&nbsp;
									<input id="pageId" type="text" style="width: 30px"
										value="${index}" onblur="funPageA(this.value)"
										onkeyup="value=value.replace(/[^\d]/g,'') " />
										&nbsp;
									<font>��${lastIndex}ҳ</font>
								</div>
							</c:if>
						</td>

					</tr>
				</tfoot>
			</table>
		</form>
	</body>
</html>