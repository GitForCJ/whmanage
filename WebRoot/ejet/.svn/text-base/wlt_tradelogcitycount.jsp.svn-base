<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@ taglib uri="/WEB-INF/wlt-tags.tld" prefix="wlt" %>
<%@ page import="java.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%
  String path = request.getContextPath();
%>
<html:html>
<head>
<title>万汇通</title>
<link href="../css/main_style.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href=../css/common.css>
<style type="text/css">
.tabs_wzdh { width: 100px; float:left;}
.tabs_wzdh li { height:35px; line-height:35px; margin-top:5px; padding-left:20px; font-size:14px; font-weight:bold;}
/*tree_nav*/
.tree_nav { border:1px solid #CCC; background:#f5f5f5; padding:10px; margin-top:20px;}
.field-item_button_m2 { margin:0 auto; text-align:center;}
.field-item_button2 { border:0px;width:70px; height:25px; color:#fff; background:url(../images/button_out2.png) no-repeat; cursor:pointer;}
.field-item_button2:hover { border:0px; width:86px; height:32px; color:#fff; background:url(../images/button_on2.png) no-repeat; cursor:pointer;}
</style>
<script type="text/javascript" src="../js/jquery-1.8.2.min.js"></script>
<script type="text/javascript" src="../js/util.js"></script>
<SCRIPT src="../js/more_tiaojian.js" type=text/javascript></SCRIPT>
<link href="../css/selectCss.css" rel="stylesheet" type="text/css" /> 
<script src="../js/selectCss.js" type="text/javascript"></script> 
<script language="javascript" type="text/javascript" src="../My97DatePicker/WdatePicker.js"></script>
<SCRIPT type=text/javascript>
  
  function query()
  {
     jQuery("#queryFrame").attr("src","<%=request.getContextPath()%>/report/tradeLogCount.do?method=query&"+jQuery("#queryform").serialize());
  }
  
  function exportXls()
  {
   jQuery("#queryFrame").attr("src","<%=request.getContextPath()%>/report/tradeLogCount.do?method=exportReport&"+jQuery("#queryform").serialize());
  }
</SCRIPT>
</head>
<body rightmargin="0" leftmargin="0"  topmargin="0" class="body_no_bg">
<div id="tabbox">

  <div id="tabs_title">
    <ul class="tabs_wzdh">
      <li style="width: 150%">地市交易统计</li></ul>
  </div>
</div>
<div id="popdiv"><span id="sellist"></span><div style="clear:both"></div></div>
<form id="queryform">
<input type="hidden" id="rootPath" value="<%=request.getContextPath() %>"/>
<div class="header">
	<div class="topCtiy clear">
		<ul>
		  <li>
		  <div class="sub-input"><a class="sia-2 selhover" id="sl-areacode" href="javascript:void(0);">请选择省份</a></div>
		  <input nname="nname" name="areacode" id="areacode"   type="hidden"   defaultsel="${requestScope.areaSel }"   
		  />
		  </li>
		   <li>
		  <div class="sub-input"><a class="sia-2 selhover" id="sl-ucity" href="javascript:void(0);">请选择地市</a></div>
		  <input nname="nname" name="user_site_city" id="ucity"  type="hidden" defaultsel=""  />
		  </li>
		  
		   <li>
		   <td>交易状态</td>
            <td><select name="state" class="sia-2 selhover">
              <option value="">所有状态</option>
              <option value="0">正常</option>
              <option value="1">返销</option>
              </select>
            </td>
		  </li>
		  
		   <li>
		    交易日期： 
          <input class="Wdate" name="startDate" style="width: 90px;" onClick="new WdatePicker({dateFmt:'yyyyMMdd'})" > 
                          至
         <input class="Wdate" name="endDate" style="width: 90px;" onClick="new WdatePicker({dateFmt:'yyyyMMdd'})" > 
		   </li>
		   <li>
		   <div>
		   <input type="button" value="统计"  onclick="query()" class="field-item_button2" /> 
		       <input type="button" value="导出"   onclick="exportXls()"  class="field-item_button2" />
		   </div>
		   </li> 
		</ul>
		 
		 
	</div>
</div>
<iframe style="width: 96%;height: 90%" src="<%=request.getContextPath()%>/report/tradeLogCount.do?method=query" scrolling="auto" frameborder="0px" id="queryFrame">
 
</iframe>
</form>
</body>
</html:html>