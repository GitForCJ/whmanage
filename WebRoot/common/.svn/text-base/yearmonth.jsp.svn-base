<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@ page import="com.commsoft.epay.pc.util.*" %>
<%
request.setAttribute("years", Tools.getToCurYears()); 
request.setAttribute("months", Tools.getMonths());
String nowMonth = Tools.getNow3().substring(0,6);
String yearMonth = Tools.getPreMonth(nowMonth);
request.setAttribute("curYear", yearMonth.substring(0, 4));
request.setAttribute("curMonth", yearMonth.substring(4, 6));
%>
<tr>
<td>年月</td>
<td><select name="year">
    <logic:iterate id="year" name="years">
    <option value="${year}"${year == curYear ? " selected" : ""}>${year}</option>
    </logic:iterate>
  </select>年
  <select name="month">
    <logic:iterate id="month" name="months">
    <option value="${month[0]}"${month[0] == curMonth ? " selected" : ""}>${month[1]}</option>
    </logic:iterate>
  </select>月</td>
 </tr>