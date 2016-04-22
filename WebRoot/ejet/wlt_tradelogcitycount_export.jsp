<%@ page language="java" contentType="application/vnd.ms-excel;charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="java.text.DecimalFormat"%>
 
<%@page import="java.net.URLEncoder"%>
<%
   response.setHeader("Content-Disposition","inline;filename="+URLEncoder.encode("地市交易统计报表","UTF-8")+".xls"); 
   out.clear();         
%>
 
<html xmlns:o="urn:schemas-microsoft-com:office:office"
      xmlns:x="urn:schemas-microsoft-com:office:excel"
      xmlns="http://www.w3.org/TR/REC-html40"
>
<head>
   <xml>
      <x:ExcelWorkbook>
         <x:ExcelWorksheets>
            <x:ExcelWorksheet>
              <x:Name>地市交易统计报表</x:Name>
            </x:ExcelWorksheet>
         </x:ExcelWorksheets>
       </x:ExcelWorkbook>
   </xml>
</head>
<body rightmargin="0" leftmargin="0"  topmargin="0" class="body_no_bg">
<table id="myTable" style="border:1px ridge solid; text-align: center; ">
<thead>
<tr> 
<th  colspan="8"><h3>地市交易统计报表</h3> </th> 
</tr>
<tr> 
<th style="border:1px ridge solid;font-weight: bold;  ">省份 </th>
<th style="border:1px ridge solid;font-weight: bold;   ">地市</th>
<th style="border:1px ridge solid;font-weight: bold;   ">业务类型</th>
<th style="border:1px ridge solid;font-weight: bold;   ">交易状态</th>
<th style="border:1px ridge solid;font-weight: bold;   ">交易终端</th>
<th style="border:1px ridge solid;font-weight: bold;  ">应收金额(元)</th>
<th style="border:1px ridge solid;font-weight: bold;   ">实收金额(元)</th>
<th style="border:1px ridge solid;font-weight: bold;  ">交易笔数(笔)</th> 
</tr>
</thead>
<tbody id="tab" style="cursor: pointer;">
 <%
 double sumFee= 0;
 int count =0 ;
 
 double sumFeeTemp= 0;
 int countTemp =0 ;
 
   List<String[]> arr = ( List<String[]>) request.getAttribute("cityCount");
   for(int i=0;arr!=null && i<arr.size();i++ )
   {
   sumFee+= Double.parseDouble(arr.get(i)[5]+"");
   count+= Double.parseDouble(arr.get(i)[6]+"");

  %>
<tr id="<%=i %>"> 

<%
  if(i==0 )
  {
 %>
  <td rowspan="<%= arr.get(i)[8] %>" style="border:1px ridge solid;  "><%= arr.get(i)[1] %></td>
<%
  }
  if((i>0 && !arr.get(i)[1].equals(arr.get(i-1)[1])))//判断是否输出合计 
  {%>

<td style="border:1px ridge solid;  " colspan="3">【 合计 】</td> 
 
<td style="border:1px ridge solid;  "> <%=new DecimalFormat("##,###.##").format(sumFeeTemp) %></td>
<td style="border:1px ridge solid;  "> <%=new DecimalFormat("##,###.##").format(sumFeeTemp) %></td>
<td style="border:1px ridge solid;  "> <%=countTemp %></td>
</tr>
  <tr >  
  <%
  if(i>0 || !arr.get(i)[1].equals(arr.get(i-1)[1]) )
  {
  if("".equals(arr.get(i)[0]) || null == arr.get(i)[0]){
  
 %>
  <td rowspan="<%= arr.get(i)[8] %>" style="border:1px ridge solid;  "><%= arr.get(i)[1] %></td>
  <td style="border:1px ridge solid;  " rowspan="<%=arr.get(i)[8] %>"> <%= arr.get(i)[0] %></td>
<%
}else {
  
  	
  
  %>
  <td rowspan="<%= arr.get(i)[8] %>" style="border:1px ridge solid;  "><%= arr.get(i)[0] %></td>
  <td style="border:1px ridge solid;  " rowspan="<%=arr.get(i)[8] %>"> <%= arr.get(i)[1] %></td>
<% 
}
}
sumFeeTemp =0;
countTemp=0;
  }
     if(i==0 || arr.get(i)[0].equals(arr.get(i-1)[0]))
   {
   sumFeeTemp+= Double.parseDouble(arr.get(i)[5]+"");
   countTemp+= Double.parseDouble(arr.get(i)[6]+"");
   }
   if(i==0 )
  {
 %>
  <td style="border:1px ridge solid;  " rowspan="<%=arr.get(i)[8] %>"> <%= arr.get(i)[0] %></td>
<%
  }  
 %>

<td style="border:1px ridge solid;  "><%= arr.get(i)[2] %> </td> 
<td style="border:1px ridge solid;  "><%= arr.get(i)[4] %></td>
<td style="border:1px ridge solid;  "><%= arr.get(i)[3] %></td>
<td style="border:1px ridge solid;  "><%= arr.get(i)[5] %></td>
<td style="border:1px ridge solid;  "><%= arr.get(i)[5] %></td>
<td style="border:1px ridge solid;  "><%= arr.get(i)[6] %></td>
</tr>
 <%
    if(i==arr.size()-1)//判断是否输出合计 
  {%>

<td style="border:1px ridge solid;  " colspan="3">【 合计 】</td> 
 
<td style="border:1px ridge solid;  "> <%=new DecimalFormat("##,###.##").format(sumFeeTemp) %></td>
<td style="border:1px ridge solid;  "> <%=new DecimalFormat("##,###.##").format(sumFeeTemp) %></td>
<td style="border:1px ridge solid;  "> <%=countTemp %></td>
</tr>
  <tr >  
<% 

  }
   }
  
  %>
<tr >  
<td style="border:1px ridge solid;  " colspan="5">【 总计 】</td> 
 
<td style="border:1px ridge solid;  "> <%=new DecimalFormat("##,###.##").format(sumFee) %></td>
<td style="border:1px ridge solid;  "> <%=new DecimalFormat("##,###.##").format(sumFee) %></td>
<td style="border:1px ridge solid;  "> <%=count %></td>
</tr>
</tbody>

</table>
</body>
</html>