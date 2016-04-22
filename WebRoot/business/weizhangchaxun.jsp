<%@ page contentType="text/html; charset=GBK" language="java"%>
<%@page import="java.util.List"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<jsp:useBean id="jtfk" scope="page" class="com.wlt.webm.business.bean.JtfkBean"/>
 <%
 	 String path = request.getContextPath();
 	 List vehicleTypeList = jtfk.getVehicleTypeList();
	 StringBuffer sBuffer = new StringBuffer();
	 sBuffer.append("��ѡ��[]");
	 for(Object tmp : vehicleTypeList){
		String[] temp = (String[])tmp;
		sBuffer.append("|"+temp[1]+"["+temp[0]+"]");
	 }
 	 request.setAttribute("vehicleSel", sBuffer.toString());
 %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>���ͨ-��ർ��</title>
<script type="text/javascript" src="../js/jquery-1.8.2.min.js"></script>
<link href="../css/main_style.css" rel="stylesheet" type="text/css" />
<link href="../css/selectCss.css" rel="stylesheet" type="text/css" /> 
<script src="../js/selectCss.js" type="text/javascript"></script> 
<script language="javascript" type="text/javascript" src="../My97DatePicker/WdatePicker.js"></script>
<SCRIPT type=text/javascript>
jQuery(function(){
	$("#changeCity").click(function(a){$("#allCity").slideDown(0);a.stopPropagation();$(this).blur()});$("#allCity").click(function(a){a.stopPropagation()});$(document).click(function(a){a.button!=2&&$("#allCity").slideUp(0)});$("#foldin").click(function(){$("#allCity").slideUp(0)})
});
</SCRIPT>
<script type="text/javascript">
function selectAll()
{
if(this.checked==true) { 
checkAll('test'); 
} 
else { 
clearAll('test'); 
}
}
function checkAll(name)
{
var el = document.getElementsByTagName('input');
var len = el.length;
for(var i=0; i<len; i++)
{
if((el[i].type=="checkbox") && (el[i].name==name))
{
el[i].checked = true;
}
}
}
function clearAll(name)
{
var el = document.getElementsByTagName('input');
var len = el.length;
for(var i=0; i<len; i++)
{
if((el[i].type=="checkbox") && (el[i].name==name))
{
el[i].checked = false;
}
}
}
function submitForm(){
	if($("#vehicleSel").val() == ""){
		alert("��ѡ������");
		return;
	}
	$("#process").show();
	$("#tdButton").hide();
	$("#form1").submit();
}
</script>
<style type="text/css">
/*Υ�²�ѯ*/
.weiz_tab { width:600px; margin:50px auto;}
.subMenu_weiz{ float:left;padding-top:2px;width:100%;}
.subMenu_weiz ul { display:block; border-bottom:1px solid #b0c4d8; height:22px;}
.subMenu_weiz li{float:left;}
.subMenu_weiz li a{display:block;float:left;color:#06669b;font-size:15px; background:url(images/weizhangbg_tab.png) no-repeat 0 0; height:22px; line-height:25px; margin-right:6px; width:75px; text-align:center;}
.subMenu_weiz .thisStyle a {display:block;float:left;color:#000;font-size:15px; background:url(images/weizhangbg_tab.png) no-repeat 0 -23px; height:23px; line-height:25px; margin-right:6px; width:75px; text-align:center; z-index:1000;}
.subContent{clear:both;border:1px solid #b0c4d8;border-top:none;background:#fff;}
.subContent ul{display:none;padding:15px;}
#myTable th,td{ padding:5px 0px;}
.weizhang_table { font-family:"΢���ź�";}
.tab_tab {  border-collapse:collapse; text-align:center;}
.tab_tab td { padding:5px 10px;}
.tab_tab thead {font-family:"΢���ź�"; border-bottom:1px dotted #ccc;}
.tab_tab tbody td { border-bottom:1px dotted #ccc; }
</style>
<script type="text/javascript" language="JavaScript">
$(function(){
	if($("#mess").val() != ''){
		alert($("#mess").val());
	}
});
</script>
</head>

<body>
<div id="popdiv"><span id="sellist"></span><div style="clear:both"></div></div>
<input type="hidden" id="mess" value="${requestScope.mess }"/>
<div id="tabbox">
  <div id="tabs_title">
    <ul class="tabs_wzdh">
      <li>��ͨΥ��</li></ul>
  </div>
</div>
<div class="weiz_tab">
	<div class="subMenu_weiz">
           <ul>
                <li class="thisStyle"><a href="javascript:void(0)">Υ�²�ѯ</a></li>
                <li><a href="javascript:void(0)">�ҵ�Υ��</a></li>
            </ul>
     </div>
     <form name="form1" id="form1" action="<%=path %>/business/jtfk.do?method=query" method="post">
      <div class="subContent">
        	<ul style="display:block;">
            <table width="100%" border="0" cellspacing="0" cellpadding="0" class="weizhang_table">
              <tr>
                <td width="70">�� �� �� ��</td>
                <td style="text-align:left">
                <div class="sub-input"><a class="sia-2 selhover" id="sl-vehicleSel" href="javascript:void(0);">��ѡ��</a></div>
				<input name="vehicleSel" id="vehicleSel" defaultsel="��ѡ��[]|��[��]|��[��]|��[��]|��[��]" type="hidden" value="" />
               <input name="vehicle" id="vehicle" type="text" value="" style="width:150px;height:20px"/>
               </td>
              </tr>
              <tr>
                <td width="70">�� �� �� ��</td>
                <td style="text-align:left"><input name="frameNo" type="text" style="width:140px;height:20px"/></td>
              </tr>
              <tr>
                <td width="70">�������� ��</td>
                <td style="text-align:left">
                <div class="sub-input"><a class="sia-2 selhover" id="sl-vehicleType" href="javascript:void(0);">С������</a></div>
				<input name="vehicleType" id="vehicleType" defaultsel="${requestScope.vehicleSel }" type="hidden" value="02" />
                </td>
              </tr>
              <tr>
                <td width="70">&nbsp;</td>
                <td height="50" valign="bottom" id="tdButton">
                <input type="button" value="Υ�²�ѯ" id="checkCode" onClick="submitForm();" class="field-item_button" />
                </td>
                <td style="display:none" id="process"><img class="bufferImg" src="<%=path %>/images/loading_16.gif" />
		        <label class="bufferText">���ڽ����У����Եȡ���</label>
		        </td>
              </tr>
            </table>

          </ul>
            <ul>
                  <table class="tab_tab" width="570" align="center">
                      <thead>
                          <tr>
                              <th width="">���ƺ���</th>
                              <th>��ѯ����</th>
                              <th>��Υ��</th>
                              <th>���쵥</th>
                              <th>����</th>
                          </tr>
                      </thead>
                      <tbody>
                      <tr>
                              <td>���ƺ���</td>
                              <td>��ѯ������</td>
                              <td>��Υ������</td>
                              <td>������</td>
                              <td>����</td>
                          </tr>
                      </tbody>
                      <tfoot>
                          <tr><td colspan="1"><a href="#">��������</a></td><td colspan="4" ></td></tr>
                      </tfoot>
                  </table>
          </ul>
  </div>
  </form>
  </div>
<script>
function $_class(name){
	var elements = document.getElementsByTagName("*");
	for(s=0;s<elements.length;s++){
		if(elements[s].className==name){
			return 	elements[s];
		}	
	}
}
//tab effects
var tabList = $_class("subMenu_weiz").getElementsByTagName("li")
	tabCon = $_class("subContent").getElementsByTagName("ul");
for(i=0;i<tabList.length;i++){
	(function(){
		var t = i;	  
		tabList[t].onclick = function(){
			for(o=0;o<tabCon.length;o++){
				tabCon[o].style.display = "none";
				tabList[o].className = "";
				if(t==o){
					this.className = "thisStyle";
					tabCon[o].style.display = "block";	
				}
			}
		}	
	})()	
}
</script>
<div style="margin-left: 200;font-size: 17px; color: red">
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;ֻ�ܲ�ѯ������㶫ʡ����������ʡ�ݳ����ڹ㶫ʡ��Χ�����Ľ�ͨΥ��(�۷�Υ�³���)
</div>
</body>
</html>
