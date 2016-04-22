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
	 sBuffer.append("请选择[]");
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
<title>万汇通-左侧导航</title>
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
		alert("请选择区域");
		return;
	}
	$("#process").show();
	$("#tdButton").hide();
	$("#form1").submit();
}
</script>
<style type="text/css">
/*违章查询*/
.weiz_tab { width:600px; margin:50px auto;}
.subMenu_weiz{ float:left;padding-top:2px;width:100%;}
.subMenu_weiz ul { display:block; border-bottom:1px solid #b0c4d8; height:22px;}
.subMenu_weiz li{float:left;}
.subMenu_weiz li a{display:block;float:left;color:#06669b;font-size:15px; background:url(images/weizhangbg_tab.png) no-repeat 0 0; height:22px; line-height:25px; margin-right:6px; width:75px; text-align:center;}
.subMenu_weiz .thisStyle a {display:block;float:left;color:#000;font-size:15px; background:url(images/weizhangbg_tab.png) no-repeat 0 -23px; height:23px; line-height:25px; margin-right:6px; width:75px; text-align:center; z-index:1000;}
.subContent{clear:both;border:1px solid #b0c4d8;border-top:none;background:#fff;}
.subContent ul{display:none;padding:15px;}
#myTable th,td{ padding:5px 0px;}
.weizhang_table { font-family:"微软雅黑";}
.tab_tab {  border-collapse:collapse; text-align:center;}
.tab_tab td { padding:5px 10px;}
.tab_tab thead {font-family:"微软雅黑"; border-bottom:1px dotted #ccc;}
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
      <li>交通违章</li></ul>
  </div>
</div>
<div class="weiz_tab">
	<div class="subMenu_weiz">
           <ul>
                <li class="thisStyle"><a href="javascript:void(0)">违章查询</a></li>
                <li><a href="javascript:void(0)">我的违章</a></li>
            </ul>
     </div>
     <form name="form1" id="form1" action="<%=path %>/business/jtfk.do?method=query" method="post">
      <div class="subContent">
        	<ul style="display:block;">
            <table width="100%" border="0" cellspacing="0" cellpadding="0" class="weizhang_table">
              <tr>
                <td width="70">车 牌 号 ：</td>
                <td style="text-align:left">
                <div class="sub-input"><a class="sia-2 selhover" id="sl-vehicleSel" href="javascript:void(0);">请选择</a></div>
				<input name="vehicleSel" id="vehicleSel" defaultsel="请选择[]|鄂[鄂]|湘[湘]|闽[闽]|粤[粤]" type="hidden" value="" />
               <input name="vehicle" id="vehicle" type="text" value="" style="width:150px;height:20px"/>
               </td>
              </tr>
              <tr>
                <td width="70">车 架 号 ：</td>
                <td style="text-align:left"><input name="frameNo" type="text" style="width:140px;height:20px"/></td>
              </tr>
              <tr>
                <td width="70">车辆类型 ：</td>
                <td style="text-align:left">
                <div class="sub-input"><a class="sia-2 selhover" id="sl-vehicleType" href="javascript:void(0);">小型汽车</a></div>
				<input name="vehicleType" id="vehicleType" defaultsel="${requestScope.vehicleSel }" type="hidden" value="02" />
                </td>
              </tr>
              <tr>
                <td width="70">&nbsp;</td>
                <td height="50" valign="bottom" id="tdButton">
                <input type="button" value="违章查询" id="checkCode" onClick="submitForm();" class="field-item_button" />
                </td>
                <td style="display:none" id="process"><img class="bufferImg" src="<%=path %>/images/loading_16.gif" />
		        <label class="bufferText">正在进行中，请稍等……</label>
		        </td>
              </tr>
            </table>

          </ul>
            <ul>
                  <table class="tab_tab" width="570" align="center">
                      <thead>
                          <tr>
                              <th width="">车牌号码</th>
                              <th>查询区域</th>
                              <th>新违章</th>
                              <th>代办单</th>
                              <th>操作</th>
                          </tr>
                      </thead>
                      <tbody>
                      <tr>
                              <td>车牌号码</td>
                              <td>查询区区域</td>
                              <td>新违区域章</td>
                              <td>代区域单</td>
                              <td>操作</td>
                          </tr>
                      </tbody>
                      <tfoot>
                          <tr><td colspan="1"><a href="#">新增车辆</a></td><td colspan="4" ></td></tr>
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
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;只能查询、受理广东省车辆和其他省份车辆在广东省范围产生的交通违章(扣分违章除外)
</div>
</body>
</html>
