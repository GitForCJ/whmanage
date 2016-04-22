<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<%@ page language="java" contentType="text/html; charset=GBK"%>
<%
String path = request.getContextPath();
%>
<head>
<title>万汇通</title>
<script type="text/javascript" src="../js/jquery-1.8.2.min.js"></script>
<link href="../css/main_style.css" rel="stylesheet" type="text/css" />
<SCRIPT src="../js/more_tiaojian.js" type=text/javascript></SCRIPT>
<script language='javascript' src='../js/jquery.js'></script>
<script language='javascript' src='../js/select.js'></script>
<script language="javascript" type="text/javascript" src="../My97DatePicker/WdatePicker.js"></script>
<SCRIPT type=text/javascript>
jQuery(function(){
	$("#changeCity").click(function(a){$("#allCity").slideDown(0);a.stopPropagation();$(this).blur()});$("#allCity").click(function(a){a.stopPropagation()});$(document).click(function(a){a.button!=2&&$("#allCity").slideUp(0)});$("#foldin").click(function(){$("#allCity").slideUp(0)})
	var _parentVal = $("input[name='pareacope']").val();
	if('1' != _parentVal){
		$.post(
	    	$("#rootPath").val()+"/rights/sysarea.do?method=getAreaFlag",
	      	{
	      		areacope:_parentVal
	      	},
	      	function (data){
	      		if(data == "0"){
	      			$("#areaFlag").html("是<input type='hidden' name='flag' value='0'/>");
	      		}else{
	      			$("#areaFlag").html("否<input type='hidden' name='flag' value='1'/>");
	      		}
	      	}
		)
	}
});
function check(){
  	with(document.forms[0]){
		/* areacope为区域区号，也即体系层次表中的节点区号 */
		action="sysarea.do?method=add";
		target="_self";
		method="post"
		submit();
	}
}
</SCRIPT>
</head>
<body>
<input type="hidden" id="rootPath" value="<%=path %>"/>
<form  method="post" action="/rights/sysarea.do?method=add">
<div class="pass_main">
	<div class="pass_title_1">添加区域</div>
    <div class="pass_list">
    	<ul>
            <li><strong>区域名称：</strong><span><input name="areaname" type="text" /></span>
            <li><strong>区号：</strong><span><input name="areacode" type="text" /></span></li>
            <li><strong>是否虚拟区域：</strong><span id="areaFlag">
			<select name="flag">
			<option value="0">是</option>
			<option value="1">否</option>
			</select>
			</span></li>
        </ul>
    </div>
   <div class="field-item_button_m">
   <input name="pareacope" type="hidden" value="${areacope}" />
   <input type="button" value="确认" id="checkCode" onclick="check()" class="field-item_button" />
   <input type="button" value="取消" id="checkCode" onClick="javascript:history.go(-1)" class="field-item_button" />
   </div>
</div>
</form>
</body>
</html>