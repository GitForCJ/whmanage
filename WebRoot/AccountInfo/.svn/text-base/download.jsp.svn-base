<%@ page language="java" import="java.util.*,java.net.*"  contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String HeaderName="";
if(request.getAttribute("HeaderName")!=null)
	HeaderName=request.getAttribute("HeaderName").toString();
String excelName="abc.xls";
if(request.getAttribute("excelName")!=null)
	excelName=request.getAttribute("excelName")+".xls";
List HeaderList=(List)request.getAttribute("HeaderList");
List DataList=(List)request.getAttribute("DataList");

String count=request.getAttribute("count")+"";

excelName=excelName.replace(" ","");
excelName=URLEncoder.encode(excelName, "UTF-8"); 
excelName=new String(excelName.getBytes("ISO8859-1"),"utf-8");

   response.reset(); 
   response.setContentType("application/vnd.ms-excel;charset=UTF-8");
   response.setContentType("application/msexcel; charset=UTF-8");
   response.setCharacterEncoding("utf-8");
   response.setHeader("Content-disposition", "inline;filename="+excelName);
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html  xmlns:x="urn:schemas-microsoft-com:office:excel">
  <head>
    <base href="<%=basePath%>">  
    <title></title>   
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
	
<!--  显示网格线 -->
<!--[if gte mso 9]><xml>
           <x:ExcelWorkbook>
               <x:ExcelWorksheets>
                   <x:ExcelWorksheet>
                       <x:Name>工作表标题</x:Name>
                       <x:WorksheetOptions>
                           <x:Print>
                               <x:ValidPrinterInfo />
                           </x:Print>
                       </x:WorksheetOptions>
                   </x:ExcelWorksheet>
               </x:ExcelWorksheets>
           </x:ExcelWorkbook>
       </xml>
 <![endif]-->
	
  </head>
      <body>
    <form name="fm" method="post" >
      <table class="common1" border="1" cellpadding="5" cellspacing="1" align="center" >
        <tr>
          <td class=formtitle colspan="<%=count %>"><CENTER style="font-weight:bold;"><%=HeaderName %></CENTER> </td>
        </tr>    
        <!-- 标头体 -->
        <%
        	for(int i=0;i<HeaderList.size();i++)
        	{
         %>
        <tr>
        	<%
        		Object[] obj=(Object[])HeaderList.get(i);
        		for(int k=0;k<obj.length;k++)
        		{
	        	 %>
	     		   <td align="center" nowrap <%=obj[k].toString().split(";")[1] %>  <%=obj[k].toString().split(";")[2] %>><%=obj[k].toString().split(";")[0] %></td>
	             <%
           	    }
             %>
        </tr>    
        <%
        }
        %> 
        
        <!-- 列表体 -->
        <%
        	for(int i=0;i<DataList.size();i++)
        	{
         %>
        <tr>
        	<%
        		Object[] obj=(Object[])DataList.get(i);
        		for(int k=0;k<obj.length;k++)
        		{
	        	 %>
	     		   <td align="center" nowrap ><%=obj[k] %></td>
	             <%
           	    }
             %>
        </tr>    
        <%
        }
        %>                
   </table>
        </form>
     </body>
</html>