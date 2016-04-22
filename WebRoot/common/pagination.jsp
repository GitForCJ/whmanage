<%@ page language="java" contentType="text/html; charset=GBK" %>
<%@ page import="java.util.*" %>
<%@ page import="com.ejet.util.Pagination"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<%   
//包含此页面的request中必须有名称为"pagination"的Pagination对象
Pagination p = (Pagination) request.getAttribute("pagination");//获取Pagination类的pagination对象
if(p!=null){
	int pageIndex = p.getPageIndex();//要跳转的页面
	int pageSize = p.getPageSize();//总页数
	int rowSize = p.getRowSize();//总行数
	session.setAttribute("sessionPageIndex",pageIndex+"");	
	boolean hasForm = false;
	String form = request.getParameter("submitForm");//分页要提交的表单名称
	String url = null;
	String queryString = null;
	if (form != null && form.length() > 0) {
	  hasForm = true;
	} else {
	  url = request.getRequestURI();//获取当前jsp页面的url地址
	  url = url.substring(url.lastIndexOf('/') + 1);//获取jsp文件名
	
	  queryString = request.getQueryString();//获取url地址后面的参数
	  if (queryString != null) {
	    url += "?" + queryString;
	  }
	}
%>
<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" class="table">
  <tr class="trlist" align="center">
    <td width="20%" bgcolor="#CCCCCC" class="tr1">共<%=pageSize%>页</td>
    <td width="20%" bgcolor="#CCCCCC" class="tr1">共<%=rowSize%>条记录</td>
    <td width="40%" bgcolor="#CCCCCC" class="tr1">
    <% if (pageIndex <= 1) { %>
        首页 上一页
      <% } else { %>
      <a href="javascript:gotoPage(1)">首页</a>
      <a href="javascript:gotoPage(<%=pageIndex - 1%>)">上一页</a>
      <% } %>

      <% if (pageIndex >= pageSize) { %>
        下一页 末页
      <% } else { %>
      <a href="javascript:gotoPage(<%=pageIndex + 1%>)">下一页</a>
      <a href="javascript:gotoPage(<%=pageSize%>)">末页</a>
      <% } %>
    </td>
    <td width="20%" bgcolor="#CCCCCC" class="tr1">跳转
      <select name="curPage" onChange="gotoPage(this.value)">
        <% for(int i = 1; i <= pageSize; i++) { %>
        <option value="<%=i%>"<%=i == pageIndex ? " selected" : ""%>><%=i%></option>
        <% } %>
      </select>页
    </td>
  </tr>
</table>
<%-- 表单 --%>
<script type="text/javascript">
function gotoPage(pageIndex2) {
  <% if (hasForm) { %>
  with (document.<%=form%>) {
    if (location.href.indexOf("/common/count.do") != -1) {
      action = "../common/count.do";
    }
    target = "_self";
    if (!document.<%=form%>.pageIndex) {
      var pageIndexInput = document.createElement('<input type="hidden" name="pageIndex" value="${param.pageIndex}">');
      appendChild(pageIndexInput);
    }
    pageIndex.value = pageIndex2;
    submit();
  }
  <% } else { %>
  var url = "<%=url%>";
  var reg = /([\?&]pageIndex=)[^&]*(?:&|$)/;
  if (reg.test(url)) { //url 中存在 pageIndex 参数
    url = url.replace(reg, RegExp.$1 + pageIndex2);//替换掉原pageIndex参数
  } else { //不存在 pageIndex 参数，则加入该参数。
    url += url.indexOf('?') == -1 ? "?pageIndex=" + pageIndex2 : "&pageIndex=" + pageIndex2;
  }
  window.location = url;
  <% } %>
}
</script>
<%} %>
