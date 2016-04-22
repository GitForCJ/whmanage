<%@ page contentType="text/html; charset=gb2312" language="java"%>
<%@ page import="java.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<jsp:useBean id="spa" scope="page" class="com.wlt.webm.business.bean.SysPhoneArea"/>
<%
  String path = request.getContextPath();
  List areaList = spa.listArea();
  StringBuffer sBuffer1 = new StringBuffer();
  sBuffer1.append("请选择[]");
  for(Object tmp : areaList){
	String[] temp = (String[])tmp;
	sBuffer1.append("|"+temp[1]+"["+temp[0]+"]");
  }
  request.setAttribute("areaSel", sBuffer1.toString());
%>
 <html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>万汇通</title>
<script type="text/javascript" src="../js/jquery-1.8.2.min.js"></script>
<script type="text/javascript" src="../js/xml_cascade.js"></script>
<link href="../css/main_style.css" rel="stylesheet" type="text/css" />
<SCRIPT src="../js/more_tiaojian.js" type=text/javascript></SCRIPT>
<link href="../css/selectCss.css" rel="stylesheet" type="text/css" /> 
<script src="../js/selectCss.js" type="text/javascript"></script> 
<script language="javascript" type="text/javascript" src="../My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="../js/util.js"></script>
<SCRIPT type=text/javascript>
showMessage("${mess}");
jQuery(function(){
	$("#changeCity").click(function(a){$("#allCity").slideDown(0);a.stopPropagation();$(this).blur()});$("#allCity").click(function(a){a.stopPropagation()});$(document).click(function(a){a.button!=2&&$("#allCity").slideUp(0)});$("#foldin").click(function(){$("#allCity").slideUp(0)})
});
function check(){
  	with(document.forms[0]){
		/* areacope为区域区号，也即体系层次表中的节点区号 */
		if(phone_num.value == ""||phone_num.value.length!=7){
		alert("请输入手机7位号段!");
		return;
		}
		if(province_code.value == ""){
		alert("请选择省份名称 !");
		return;
		}
		if(city_name.value == ""){
		alert("请输入城市名称 !");
		return;
		}
		if(phone_type.value == ""){
		alert("请选择手机号码类型 !");
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
<html:form  method="post" action="/business/spa.do?method=add">
<div class="pass_main">
	<div class="pass_title_1">添加号码区域</div>
    <div class="pass_list">
    	<ul>
            <li><strong>手机号码（前7位）：</strong><span><input name="phone_num" id="phone_num" type="text" /></span></li>
            <li><strong>省份名称：</strong><span>
            <div class="sub-input"><a class="sia-2 selhover" id="sl-province_code" href="javascript:void(0);">请选择</a></div>
			<input name="province_code" id="province_code" defaultsel="${requestScope.areaSel }" type="hidden" value="" />
			</span></li>
          <!--    <li><strong>城市名称：</strong><span><input name="city_name" type="text" /></span></li>
            <li><strong>卡型：</strong><span><input name="cart_type" type="text" /></span></li>
            <li><strong>区号：</strong><span><input name="area_code" type="text" /></span></li>-->
<li><strong>城市名称：</strong><span><input name="city_name" id="city_name" type="text" /></span></li>           
 <li><strong>手机号码类型：</strong><span>
            <div class="sub-input"><a class="sia-2 selhover" id="sl-phone_type" href="javascript:void(0);">请选择</a></div>
			<input name="phone_type" id="phone_type" defaultsel="请选择[]|移动[1]|联通[2]|电信[0]" type="hidden" value="" />
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