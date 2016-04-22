    <%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
        <meta http-equiv="Content-Type" content="text/html;charset=UTF-8"/>
<script src="../ueditor.parse.js" type="text/javascript"></script>
<script>
        uParse('.content',{
            'rootPath': '../'
        })
</script>
<%
request.setCharacterEncoding("UTF-8");
response.setCharacterEncoding("UTF-8");
String content = request.getParameter("rich");
System.out.println(content);


response.getWriter().print("==========测试===========");
response.getWriter().print("<div class='content'>"+content+"</div>");

%>