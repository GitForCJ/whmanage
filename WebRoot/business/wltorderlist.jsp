<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@ taglib uri="/WEB-INF/wlt-tags.tld" prefix="wlt" %>
<%@ page import="java.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
  String path = request.getContextPath();
%>
<html:html>
<head>
<title>���ͨ</title>
<link href="../css/main_style.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href=../css/common.css>
<style type="text/css">
.tabs_wzdh { width: 100px; float:left;}
.tabs_wzdh li { height:35px; line-height:35px; margin-top:5px; padding-left:20px; font-size:14px; font-weight:bold;}
/*tree_nav*/
.tree_nav { border:1px solid #CCC; background:#f5f5f5; padding:10px; margin-top:20px;}
.field-item_button_m2 { margin:0 auto; text-align:center;}
.field-item_button2 { border:0px;width:136px; height:32px; color:#fff; background:url(../images/button_out2.png) no-repeat; cursor:pointer;margin-right:20px;}
.field-item_button2:hover { border:0px; width:136px; height:32px; color:#fff; background:url(../images/button_on2.png) no-repeat; margin-right:20px;}

 .odd{ background-color:#A3A3A3;}
 .even{background-color:#A3A3A3;}
</style>
<script type="text/javascript" src="../js/util.js"></script>
<SCRIPT src="../js/more_tiaojian.js" type=text/javascript></SCRIPT>
<script language='javascript' src='../js/jquery.js'></script>
<link href="../css/selectCss.css" rel="stylesheet" type="text/css" /> 
<script src="../js/selectCss.js" type="text/javascript"></script> 
<script language="javascript" type="text/javascript" src="../My97DatePicker/WdatePicker.js"></script>
<SCRIPT type=text/javascript>
jQuery(function(){
	//$("#changeCity").click(function(a){$("#allCity").slideDown(0);a.stopPropagation();$(this).blur()});$("#allCity").click(function(a){a.stopPropagation()});$(document).click(function(a){a.button!=2&&$("#allCity").slideUp(0)});$("#foldin").click(function(){$("#allCity").slideUp(0)})
 $("#myTable tr:odd").addClass("odd");//oddΪż����
 // $("#myTable tr:even").addClass("even")//evenΪż����
});


function checksubmit(){
	$("#form1").first();
	$("#form1").attr("action","<c:url value='/business/order.do?method=list'/>");
	$("#form1").submit();//�ύ
	funLoad();
	
}

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

function excelExport(){

	$("#form1").first();
	$("#form1").attr("action","<c:url value='/business/order.do?method=excelExport'/>");
	$("#form1").submit();//�ύ
	
}
//�����λ�ȡ��ֵ���
function orderQuery(a,b,c,d,e,f){
	  with(document.forms[0]){
            if(confirm("ȷ�ϲ�ѯ?")){
             document.getElementById(e).disabled=true;
		     document.getElementById(e).style.color="gray";
		     document.getElementById(e).value="��ѯ��";
            sendMsg(a,b,c,d,e,f);
	  		}else{
	  		return false;
	  		}
	  }
	}


function sendMsg(a,b,c,d,e,f){
	$.post(
    	$("#rootPath").val()+"/business/mobileCharge.do?method=orderQuery",
      	{
      	orderid:a,
      	buyid:b,
      	phone:c,
      	tradetime:d,
      	money:f
      	},
      	function (data){
      	document.getElementById(e).disabled="";
      	document.getElementById(e).style.color="blue";
      	document.getElementById(e).value="��ѯ";
      		if(data == 0)
      		{//�ɹ�
      		alert("�ñʶ�����ֵ�ɹ�");
      		}
      		else 
      		{
	      		if(data==1)
	      		{//ʧ��
	      		alert("�ñʶ�����ֵʧ��");
	      		}
	      		else
	      		{//���ڴ���
	      		alert("�ñʶ������ڴ�����");
	      		}
      		}
      	}
	)
}
</SCRIPT>
</head>
<body rightmargin="0" leftmargin="0"  topmargin="0" class="body_no_bg">
<div id="popdiv"><span id="sellist"></span><div style="clear:both"></div></div>
<div id="tabbox">
  <div id="tabs_title">
    <ul class="tabs_wzdh">
      <li>������ϸ</li></ul>
  </div>
</div>
<form id="form1" action="<%=path %>/business/order.do?method=list" method="post">
<input type="hidden" id="rootPath" value="<%=path %>"/>
<div class="header">
	<div class="topCtiy clear">
		<ul>
 		<c:if test="${(sessionScope.userSession.roleType == 0) || (sessionScope.userSession.roleType == 1)}">
		  <li>
		  	<c:if test="${sessionScope.userSession.roleType == 0}">
		  		<div class="sub-input"><a class="sia-2 selhover" id="sl-areacode" href="javascript:void(0);">����</a></div>
		 	 	<input nname="nname" name="areacode" id="areacode" defaultsel="${requestScope.areaSel }" type="hidden" value="${order.areacode }" />
		  	</c:if>
		  </li>
           <li>
           <div class="sub-input"><a class="sia-2 selhover" id="sl-buyid" href="javascript:void(0);">�ӿ���</a></div>
		  	<input nname="nname" name="buyid" id="buyid" defaultsel="${requestScope.itypeSel }" type="hidden" value="${order.buyid }" />
           </li>
           </c:if>
           <li>
            <div class="sub-input"><a class="sia-2 selhover" id="sl-state" href="javascript:void(0);">����״̬</a></div>
		  	<input nname="nname" name="state" id="state" defaultsel="����״̬[]|�ɹ�[0]|ʧ��[1]|������[4]|����[5]|�쳣����[6]" type="hidden" value="${order.state }" />
		  </li>
		  <li>
		  <div class="sub-input"><a class="sia-2 selhover" id="sl-service" href="javascript:void(0);">ҵ������</a></div>
		  	<input nname="nname" name="service" id="service" defaultsel="${requestScope.serviceSel }" type="hidden" value="${order.service }" />
		  </li>
		  <li>
		  <div class="sub-input"><a class="sia-2 selhover" id="sl-fee" href="javascript:void(0);">��ֵ���</a></div>
		  	<input nname="nname" name="fee" id="fee" defaultsel="��ֵ���[]|10[10]|20[20]|30[30]|50[50]|100[100]|200[200]|300[300]|500[500]" type="hidden" value="${order.fee }" />
		  </li>
		  <c:if test="${(sessionScope.userSession.roleType == 0) || (sessionScope.userSession.roleType == 1)}">
			<br/>
		</c:if>
		  <li><input type="button" name="Button" value="��ѯ" class="field-item_button" onClick="checksubmit();"></li>
	 	<li><input type="button" name="Button" value="����" class="field-item_button" onClick="excelExport();"></li>
		</ul>
	</div>
</div>
<div class="selCity" id="allCity" style="DISPLAY: block;margin-top: 33px;">
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
 <td>���׺��룺</td>
    <td><input name="tradeobject" type="text" value="${requestScope.order.tradeobject }"/></td>
    <td>��ʼ���ڣ�</td>
    <td><input class="Wdate" name="startDate" type="text" onClick="WdatePicker()" value="${requestScope.order.startDate }"></td>
    <td>�������ڣ�</td>
    <td><input class="Wdate" name="endDate" type="text" onClick="WdatePicker()" value="${requestScope.order.endDate }"></td>
  </tr>
<c:if test="${(sessionScope.userSession.roleType == 0) || (sessionScope.userSession.roleType == 1)}">
  <tr>
 <td>�ͻ�����</td>
    <td><input name="exp1" type="text" value="${requestScope.order.exp1 }"/></td>
<td>�������˺�</td>	
<td><input name="dailishang" type="text" value="${requestScope.order.dailishang }"/></td>      
<td>��½�˺�</td>
    <td><input name="username" type="text" value="${requestScope.order.username }"/></td>
</tr>
</c:if>
</table>
<div class="none"><A id=foldin href="javascript:;" style="cursor: default;">����</A></div>
</div>
<table id="myTable" cellspacing="0" cellpadding="0" class="tab_line">
<thead>
<tr height="25" class="tr_line_bottom">
<%--<th>���</th>--%>
<th>����</th>
<th>����(״̬)</th>
<th><c:if test="${(sessionScope.userSession.roleType == 0) || (sessionScope.userSession.roleType == 1)}">�ӿ���/</c:if>ҵ��</th>
<th>������ֵ</th>
<th>�ۿ���</th>
<th>�ο����</th>
<th>����ʱ��</th>
<th>��ֵ���</th>
</tr>
</thead>
<tbody id="tab">
<logic:iterate id="ordinfo" name="orderList" indexId="i">
<tr>
<%--<td class="td_line_left">${i + 1}</td>--%>
<td class="td_line">${ordinfo[0]}</td>
<td class="td_line">${ordinfo[2]}<br>(<span class="colo_red_sus">${ordinfo[9]}</span>)</td>
<td class="td_line"><c:if test="${(sessionScope.userSession.roleType == 0) || (sessionScope.userSession.roleType == 1)}">${ordinfo[14]}<br/></c:if>${ordinfo[15]}</td>
<td class="td_line">${ordinfo[5]/1000.0}</td>
<td class="td_line">${ordinfo[6]/1000.0}</td>
<td class="td_line">${ordinfo[16]/1000.0}</td>
<td class="td_line">${ordinfo[7]}</td>
<c:if test="${ordinfo[9]=='�ɹ�'||ordinfo[9]=='ʧ��'||ordinfo[9]=='����'}">
<td class="td_line"><input name="update" type="button" value="��ѯ" class="onbutton" style="color: gray;" disabled="disabled" ></td>
</c:if>
<c:if test="${ordinfo[9]=='������'||ordinfo[9]=='�쳣����'}">
	<c:if test="${ordinfo[3]!='1' && ordinfo[3]!='6' && ordinfo[3]!='8' && ordinfo[3]!='9' && ordinfo[3]!='13' && ordinfo[3]!='17'}">
		<td class="td_line"><input name="update" type="button" value="��ѯ" class="onbutton" style="color: gray;" disabled="disabled" ></td>
	</c:if>
	<c:if test="${ordinfo[3]=='1'||ordinfo[3]=='6'||ordinfo[3]=='8'||ordinfo[3]=='9'||ordinfo[3]=='13'||ordinfo[3]=='17'}">
		<td class="td_line"><input name="update" type="button" value="��ѯ" id="${ordinfo[1]}" class="onbutton" onClick="orderQuery('${ordinfo[17]}','${ordinfo[3]}','${ordinfo[2]}','${ordinfo[7]}','${ordinfo[1]}',${ordinfo[5]})"></td>
	</c:if>
</c:if>
</tr>
</logic:iterate>
<tr><td colspan="8">&nbsp;</td></tr>
<tr><td colspan="8" align="left">��ǰҳ,�����ܶ${requestScope.curTotal/1000.0 }&nbsp;&nbsp;��ֵ�ܶ${requestScope.valueTotal/1000.0 }</td></tr>
<tr><td colspan="8" align="left">
	�ܼ�,�����ܶ<fmt:formatNumber value="${requestScope.totalFee[1]/1000.0}" pattern="#0.000"/>&nbsp;&nbsp;
	��ֵ�ܶ<fmt:formatNumber value="${requestScope.totalFee[0]/1000.0}" pattern="#0.000"/>&nbsp;&nbsp;
	���ױ�����${requestScope.totalFee[2]}
	</td>
</tr>
</tbody>
<tfoot>
<tr height="30">
<td colspan="9" class="tr_line_top">
<div class="grayr">
<wlt:pager url="business/order.do?method=list${requestScope.order.paramUrl}" page="${requestScope.page}" style="black" />
</div>
</td>
<!--
<td colspan="8">
<div class="grayr"><span class="disabled"> < </span><span class="current">1</span><a href="#?page=2" _fcksavedurl="#?page=2">2</a><a href="#?page=3" _fcksavedurl="#?page=3">3</a><a href="#?page=4" _fcksavedurl="#?page=4">4</a><a href="#?page=5" _fcksavedurl="#?page=5">5</a><a href="#?page=6" _fcksavedurl="#?page=6">6</a><a href="#?page=7" _fcksavedurl="#?page=7">7</a>...<a href="#?page=199" _fcksavedurl="#?page=199">199</a><a href="#?page=200" _fcksavedurl="#?page=200">200</a><a href="#?page=2" _fcksavedurl="#?page=2"> > </a></div>
<div id="page">
<a href="">�ס�ҳ</a><a href="">��һҳ</a><a href="">��һҳ</a><a href="">ĩ��ҳ</a></div>
</td>
-->
</tr>
</tfoot>
</table>
</form>
</body>
</html:html>