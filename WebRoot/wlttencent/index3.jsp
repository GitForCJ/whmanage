<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.wlt.webm.business.action.Flows_Public_Method"%>
<%@page import="com.wlt.webm.business.action.TencentOrdersBean;"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%
String path = request.getContextPath();
String st=request.getParameter("st");
String t_order=request.getParameter("t_order");
String w_order=request.getParameter("w_order");

TencentOrdersBean obj=new TencentOrdersBean();
obj.setPaipai_dealid(t_order);
obj.setSeqNo1(w_order);

Flows_Public_Method.Is_Back(null,st,"1000",obj,null);
response.sendRedirect(path+"/wlttencent/index2.jsp");
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  </head>
  <body style="margin:0px 0px 0px 0px;padding:0px 0px 0px 0px;">
  </body>
</html>
