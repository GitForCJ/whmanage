<%@ page contentType="text/html; charset=GBK" language="java"%>
<%@ page import="java.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<jsp:useBean id="sui" scope="page" class="com.wlt.webm.business.bean.SysUserInterface"/>
<%
  String path = request.getContextPath();
  String id = request.getParameter("siid");
  List areaList = sui.listArea();
  StringBuffer sBuffer1 = new StringBuffer();
  sBuffer1.append("请选择[]");
  for(Object tmp : areaList){
	String[] temp = (String[])tmp;
	sBuffer1.append("|"+temp[1]+"["+temp[0]+"]");
  }
  request.setAttribute("areaSel", sBuffer1.toString());
  List typeList = sui.listInterfaceType();
  StringBuffer sBuffer2 = new StringBuffer();
  sBuffer2.append("请选择[]");
  for(Object tmp : typeList){
	String[] temp = (String[])tmp;
	sBuffer2.append("|"+temp[1]+"["+temp[0]+"]");
  }
  request.setAttribute("typeSel", sBuffer2.toString());
  request.setAttribute("suInfo",sui.getUserInterfaceInfo(id)); 
%>
 <html>
<head>
<title>万汇通</title>
<script type="text/javascript" src="../js/jquery-1.8.2.min.js"></script>
<link href="../css/main_style.css" rel="stylesheet" type="text/css" />
<link href="../css/selectCss.css" rel="stylesheet" type="text/css" /> 
<script src="../js/selectCss.js" type="text/javascript"></script> 
<script language="javascript" type="text/javascript" src="../My97DatePicker/WdatePicker.js"></script>
<SCRIPT type=text/javascript>
jQuery(function(){
	$("#changeCity").click(function(a){$("#allCity").slideDown(0);a.stopPropagation();$(this).blur()});$("#allCity").click(function(a){a.stopPropagation()});$(document).click(function(a){a.button!=2&&$("#allCity").slideUp(0)});$("#foldin").click(function(){$("#allCity").slideUp(0)})
});
function check(){
  	with(document.forms[0]){
		/* areacope为区域区号，也即体系层次表中的节点区号 */
		if(province_code.value == ""){
		alert("请选择区域 !");
		return;
		}
		if(province_code.value == ""){
		alert("请选择接口名称 !");
		return;
		}
		target="_self";
		submit();
	}
}
</SCRIPT>
</head>
<body>
<div id="popdiv"><span id="sellist"></span><div style="clear:both"></div></div>
<html:form  method="post" action="/business/sui.do?method=update">
<input type="hidden" name="id" value="<%=id %>"/>
<input type="hidden" name="user_id" value="${suInfo.user_id }"/>
<div class="pass_main">
	<div class="pass_title_1">更新用户接口</div>
    <div class="pass_list">
    	<ul>
            <li><strong>区域：</strong><span>
			<div class="sub-input"><a class="sia-2 selhover" id="sl-province_code" href="javascript:void(0);">请选择</a></div>
			<input nname="nname" name="province_code" id="province_code" defaultsel="${requestScope.areaSel }" type="hidden" value="${suInfo.province_code }" />
			</span></li>
            <li><strong>业务类型：</strong><span>
            <div class="sub-input"><a class="sia-2 selhover" id="sl-charge_type" href="javascript:void(0);">请选择</a></div>
			<input nname="nname" name="charge_type" id="charge_type" defaultsel="请选择[]|所有业务[0]|移动[1]|联通[2]|电信[3]" type="hidden" value="${suInfo.charge_type }" />
			</span></li>
			<li><strong>接口名称：</strong><span>
			<div class="sub-input"><a class="sia-2 selhover" id="sl-in_id" href="javascript:void(0);">请选择</a></div>
			<input nname="nname" name="in_id" id="in_id" defaultsel="${requestScope.typeSel }" type="hidden" value="${suInfo.in_id }" />
			</span></li>
			<li><strong>是否允许冲正：</strong><span>
			<div class="sub-input"><a class="sia-2 selhover" id="sl-is_return" href="javascript:void(0);">请选择</a></div>
			<input nname="nname" name="is_return" id="is_return" defaultsel="请选择[]|是[0]|否[1]" type="hidden" value="${suInfo.is_return }" />
			</span></li>
        </ul>
   </div>
   <div class="field-item_button_m">
   <input type="button" value="确认" id="checkCode" onclick="check()" class="field-item_button" />
   <input type="button" value="取消" id="checkCode" onClick="javascript:history.go(-1)" class="field-item_button" />
   </div>
</div>
</html:form>
</body>
</html>