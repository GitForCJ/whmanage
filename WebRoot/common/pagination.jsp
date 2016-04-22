<%@ page language="java" contentType="text/html; charset=GBK" %>
<%@ page import="java.util.*" %>
<%@ page import="com.ejet.util.Pagination"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<%   
//������ҳ���request�б���������Ϊ"pagination"��Pagination����
Pagination p = (Pagination) request.getAttribute("pagination");//��ȡPagination���pagination����
if(p!=null){
	int pageIndex = p.getPageIndex();//Ҫ��ת��ҳ��
	int pageSize = p.getPageSize();//��ҳ��
	int rowSize = p.getRowSize();//������
	session.setAttribute("sessionPageIndex",pageIndex+"");	
	boolean hasForm = false;
	String form = request.getParameter("submitForm");//��ҳҪ�ύ�ı�����
	String url = null;
	String queryString = null;
	if (form != null && form.length() > 0) {
	  hasForm = true;
	} else {
	  url = request.getRequestURI();//��ȡ��ǰjspҳ���url��ַ
	  url = url.substring(url.lastIndexOf('/') + 1);//��ȡjsp�ļ���
	
	  queryString = request.getQueryString();//��ȡurl��ַ����Ĳ���
	  if (queryString != null) {
	    url += "?" + queryString;
	  }
	}
%>
<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" class="table">
  <tr class="trlist" align="center">
    <td width="20%" bgcolor="#CCCCCC" class="tr1">��<%=pageSize%>ҳ</td>
    <td width="20%" bgcolor="#CCCCCC" class="tr1">��<%=rowSize%>����¼</td>
    <td width="40%" bgcolor="#CCCCCC" class="tr1">
    <% if (pageIndex <= 1) { %>
        ��ҳ ��һҳ
      <% } else { %>
      <a href="javascript:gotoPage(1)">��ҳ</a>
      <a href="javascript:gotoPage(<%=pageIndex - 1%>)">��һҳ</a>
      <% } %>

      <% if (pageIndex >= pageSize) { %>
        ��һҳ ĩҳ
      <% } else { %>
      <a href="javascript:gotoPage(<%=pageIndex + 1%>)">��һҳ</a>
      <a href="javascript:gotoPage(<%=pageSize%>)">ĩҳ</a>
      <% } %>
    </td>
    <td width="20%" bgcolor="#CCCCCC" class="tr1">��ת
      <select name="curPage" onChange="gotoPage(this.value)">
        <% for(int i = 1; i <= pageSize; i++) { %>
        <option value="<%=i%>"<%=i == pageIndex ? " selected" : ""%>><%=i%></option>
        <% } %>
      </select>ҳ
    </td>
  </tr>
</table>
<%-- �� --%>
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
  if (reg.test(url)) { //url �д��� pageIndex ����
    url = url.replace(reg, RegExp.$1 + pageIndex2);//�滻��ԭpageIndex����
  } else { //������ pageIndex �����������ò�����
    url += url.indexOf('?') == -1 ? "?pageIndex=" + pageIndex2 : "&pageIndex=" + pageIndex2;
  }
  window.location = url;
  <% } %>
}
</script>
<%} %>
