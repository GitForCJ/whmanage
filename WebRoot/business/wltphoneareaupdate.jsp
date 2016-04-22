<%@ page contentType="text/html; charset=GBK" language="java"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<jsp:useBean id="spa" scope="page" class="com.wlt.webm.business.bean.SysPhoneArea"/>
<%
  String path = request.getContextPath();
  String paid=request.getParameter("paid");
  request.setAttribute("painfo", spa.getPhoneAreaInfo(paid));
  request.setAttribute("areaList",spa.listArea()); 
%>
 <html>
<head>
<title>万汇通</title>
<script type="text/javascript" src="../js/jquery-1.8.2.min.js"></script>
<script type="text/javascript" src="../js/xml_cascade.js"></script>
<link href="../css/main_style.css" rel="stylesheet" type="text/css" />
<SCRIPT src="../js/more_tiaojian.js" type=text/javascript></SCRIPT>
<script language='javascript' src='../js/jquery.js'></script>
<script language='javascript' src='../js/select.js'></script>
<script language="javascript" type="text/javascript" src="../My97DatePicker/WdatePicker.js"></script>
<SCRIPT type=text/javascript>
jQuery(function(){
	$("#changeCity").click(function(a){$("#allCity").slideDown(0);a.stopPropagation();$(this).blur()});$("#allCity").click(function(a){a.stopPropagation()});$(document).click(function(a){a.button!=2&&$("#allCity").slideUp(0)});$("#foldin").click(function(){$("#allCity").slideUp(0)})
	$("#province_code").val($("#s1").val());
	$("#phone_type").val($("#s2").val());
});
function check(){
  	with(document.forms[0]){
		/* areacope为区域区号，也即体系层次表中的节点区号 */
		if(phone_num.value == ""){
		alert("请输入手机号码 !");
		return;
		}
		if(province_code.value == ""){
		alert("请选择省份名称 !");
		return;
		}
		target="_self";
		submit();
	}
}
</SCRIPT>
</head>
<body>
<html:form  method="post" action="/business/spa.do?method=update">
<input type="hidden" name="id" value="<%=paid %>"/>
<input type="hidden" id="s1" value="${painfo.province_code }"/>
<input type="hidden" id="s2" value="${painfo.phone_type }"/>
<div class="pass_main">
	<div class="pass_title_1">更新号码区域</div>
    <div class="pass_list">
    	<ul>
            <li><strong>手机号码（前7位）：</strong><span><input name="phone_num" id="phone_num" type="text" value="${painfo.phone_num }"/></span></li>
            <li><strong>省份名称：</strong><span>
				<select name="province_code" id="province_code">
					<option value="">请选择</option>
					<logic:iterate id="area" name="areaList">
						<option value="${area[0]}">${area[1]}</option>
					</logic:iterate>
				</select>
			</span></li>
            <li><strong>城市名称：</strong><span><input name="city_name" type="text" value="${painfo.city_name }"/></span></li>
            <li><strong>卡型：</strong><span><input name="cart_type" type="text" value="${painfo.cart_type }"/></span></li>
            <li><strong>区号：</strong><span><input name="area_code" type="text" value="${painfo.area_code }"/></span></li>
            <li><strong>手机号码类型：</strong><span>
				<select name="phone_type" id="phone_type">
					<option value="1">移动</option>
					<option value="2">联通</option>
					<option value="3">电信</option>
				</select>
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