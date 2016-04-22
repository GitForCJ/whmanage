<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="f"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<link REL="stylesheet"  TYPE="text/css"  HREF="../css/common.css">
<script type="text/javascript" src="../js/util.js"></script>
<script type="text/javascript" src="../js/color_change.js"></script>
<title>${title}</title>
<script type="text/javascript">
function toExcel(form)
{
  form.action = "../common/excel.do";
  form.submit();
}

function toBack()
{
  if (document.referrer.indexOf("/common/loadcount.jsp") != -1) {
    history.go(-2);
  } else {
    history.go(-1);
  }
}
</script>
</head>
<body class="minmargin">
<table width="100%" border="0" cellspacing="0" cellpadding="0" class="menu_table">
 <tr> 
    <td width="129"><img src="../images/lgo_0hm_129x36.on.gif" width="129" height="33"></td>
    <td width="120" background="../images/menu0.on.bg.gif" nowrap> <div align="center" ><a href="#" class="fontmenu">查询统计报表</a></div></td>
    <td width="11"><img src="../images/menu0.on.end.gif" width="11" height="33"></td>
    <td class="menubg">&nbsp;</td>
  </tr>
  <tr> 
    <td height="10" colspan="4" background="../images/menu.bg.gif"><div align="center"></div></td>
  </tr>
  <tr> 
    <td height="10" colspan="4" background="../images/menu.bg.gif"><div align="center"></div></td>
  </tr>
</table>
&nbsp;
<table width="100%" border="0" align="center" cellpadding="0" cellspacing="1" class="tablelist">
<%-- 报表标题 --%>
<tr align="center" class="trlist">
  <td class="fontlisttitle" colspan="${f:length(colTitles[0])}">${title}</td>
</tr>

<%-- 列标题 --%>
<logic:iterate id="row" name="colTitles" indexId="i">
<tr align="center" class="fontcoltitle">
  <logic:iterate id="col" name="row" indexId="j">
  <c:if test="${col != null}"><th nowrap rowspan="${colTitlesSpans[i][j][0]}" colspan="${colTitlesSpans[i][j][1]}">${col}</th></c:if>
  </logic:iterate>
</tr>
</logic:iterate>

<c:choose>
<%-- 报表数据 - 列表（List） --%>
<c:when test="${nestedCount == 0}">
<logic:iterate id="row" name="bodyData">
<tr align="center" class="tr1" onmouseover="overRow(this)" onmouseout="outRow(this)" onclick="clickRow(this)">
  <logic:iterate id="col" name="row">
  <td nowrap>${col}</td>
  </logic:iterate>
</tr>
</logic:iterate>
</c:when>
<%-- 报表数据 - 一列合并（Map(List)） --%>
<c:when test="${nestedCount == 1}">
<logic:iterate id="data" name="bodyData">
<bean:define id="key" name="data" property="key"></bean:define>
<bean:define id="list" name="data" property="value"></bean:define>
  <logic:iterate id="row" name="list" indexId="i">
  <tr align="center" class="tr1" onmouseover="overRow(this)" onmouseout="outRow(this)" onclick="clickRow(this)">
    <c:if test="${i == 0}"><td nowrap rowspan="${f:length(list)}">${key}</td></c:if>
    <logic:iterate id="col" name="row">
    <td nowrap>${col}</td>
    </logic:iterate>
  </tr>
  </logic:iterate>
</logic:iterate>
</c:when>
<%-- 报表数据 - 二列合并（Map(Map(List))） --%>
<c:when test="${nestedCount == 2}">
<logic:iterate id="entry1" name="bodyData" indexId="i">
<bean:define id="key1" name="entry1" property="key"></bean:define>
<bean:define id="map2" name="entry1" property="value"></bean:define>
  <logic:iterate id="entry2" name="map2" indexId="j">
  <bean:define id="key2" name="entry2" property="key"></bean:define>
  <bean:define id="list" name="entry2" property="value"></bean:define>
    <logic:iterate id="row" name="list" indexId="k">
    <tr align="center" class="tr1" onmouseover="overRow(this)" onmouseout="outRow(this)" onclick="clickRow(this)">
      <c:if test="${j == 0 && k == 0}"><td nowrap rowspan="${bodyDataRowSpans[i]}">${key1}</td></c:if>
      <c:if test="${k == 0}"><td nowrap rowspan="${f:length(list)}">${key2}</td></c:if>
      <logic:iterate id="col" name="row">
      <td nowrap>${col}</td>
      </logic:iterate>
    </tr>
    </logic:iterate>
  </logic:iterate>
</logic:iterate>
</c:when>
</c:choose>
</table>

<%-- 分页 --%>
<jsp:include flush="true" page="pagination.jsp">
  <jsp:param name="submitForm" value="forms[0]"/>
</jsp:include>

<%-- 表单 --%>
<form action="../common/excel.do" method="post">
<logic:iterate id="entry" name="params">
<bean:define id="name" name="entry" property="key"></bean:define>
<bean:define id="values" name="entry" property="value"></bean:define>
<logic:iterate id="value" name="values">
<input type="hidden" name="${name}" value="${value}">
</logic:iterate>
</logic:iterate>
<p align="center">
  <input type="button" name="excelButton" value="导出EXCEL" class="onbutton" onclick="toExcel(this.form)">
  <input type="button" name="backButton" value="返回" class="onbutton" onclick="toBack()">
</p>
</form>

<%-- 报表备注 --%>
<c:if test="${!empty remarks}">
<table align="center" style="color: red">
  <logic:iterate id="remark" name="remarks">
    <tr><td>${remark}</td></tr>
  </logic:iterate>
</table>
</c:if>

</body>
</html>
