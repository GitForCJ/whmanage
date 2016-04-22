<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.wlt.webm.db.DBService" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%
String path = request.getContextPath();
DBService db=new DBService();
String st=request.getParameter("st");
String dd=request.getParameter("dd");
String bm=request.getParameter("bm");
String sql ="update "+bm+" set state="+st+" where tradeserial='"+dd+"'";
System.out.println(sql);
int a =db.update(sql);
db.close();
response.sendRedirect(path+"/wlttencent/index0.jsp");
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>腾讯流量产品上下架</title>
  </head>
  
  <body style="margin:0px 0px 0px 0px;padding:0px 0px 0px 0px;">
  </body>
</html>
