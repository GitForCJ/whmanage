<%@ page language="java" import="java.util.*,java.net.*"  contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

String excelName="代理统计.xls";
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
<style type="text/css">
#tables td{
	padding:0px 0px 0px 0px;
	border:0.1px solid blue;
	border-right:0px;
	border-bottom:0px;
	height:30px;
}
</style>
  </head>
      <body>
   <div style="margin-top:10px;">
	<table  cellspacing="0" cellpadding="0" style="font-size:13px;width:900px;" border="1">
		<tr>
			<td colspan="21" style="text-align:center;height:30px;font-size:18px;">代理统计</td>
		</tr>
		<tr>
			<td colspan="21" style="text-align:right;">
				查询日期：${indate } - ${enddate }
			</td>
		</tr>
		<tr style="font-weight:bold;">
			<td style="text-align:center;" width=150 rowspan=3></td>
			
			<td style="text-align:center;" colspan="4">移动充值</td>
			<td style="text-align:center;" colspan="4">联通充值</td>
			<td style="text-align:center;" colspan="4">电信充值</td>
			
			<td style="text-align:center;" colspan="2">Q币充值</td>
			<td style="text-align:center;" colspan="2">游戏充值</td>
			<td style="text-align:center;" colspan="2">支付宝充值</td>
			<td style="text-align:center;" colspan="2">汇总</td>
		</tr>
		<tr style="font-weight:bold;">
			<td style="text-align:center;" colspan="2">本省</td>
			<td style="text-align:center;" colspan="2">外省</td>
			
			<td style="text-align:center;" colspan="2">本省</td>
			<td style="text-align:center;" colspan="2">外省</td>
			
			<td style="text-align:center;" colspan="2">本省</td>
			<td style="text-align:center;" colspan="2">外省</td>
			
			<td style="text-align:center;" rowspan=2>笔<br/>数</td>
			<td style="text-align:center;" rowspan=2>金额<br/>(元)</td>
			<td style="text-align:center;" rowspan=2>笔<br/>数</td>
			<td style="text-align:center;" rowspan=2>金额<br/>(元)</td>
			<td style="text-align:center;" rowspan=2>笔<br/>数</td>
			<td style="text-align:center;" rowspan=2>金额<br/>(元)</td>
			<td style="text-align:center;" rowspan=2>笔<br/>数</td>
			<td style="text-align:center;" rowspan=2>金额<br/>(元)</td>
		</tr>
		<tr style="font-weight:bold;">
			<td style="text-align:center;">笔<br/>数</td>
			<td style="text-align:center;">金额<br/>(元)</td>
			
			<td style="text-align:center;">笔<br/>数</td>
			<td style="text-align:center;">金额<br/>(元)</td>
			
			<td style="text-align:center;">笔<br/>数</td>
			<td style="text-align:center;">金额<br/>(元)</td>
			
			<td style="text-align:center;">笔<br/>数</td>
			<td style="text-align:center;">金额<br/>(元)</td>
			
			<td style="text-align:center;">笔<br/>数</td>
			<td style="text-align:center;">金额<br/>(元)</td>
			
			<td style="text-align:center;">笔<br/>数</td>
			<td style="text-align:center;">金额<br/>(元)</td>
		</tr>
		<c:forEach items="${accountList}" var="arrList" varStatus="index">
			<c:if test="${(index.index+1)%2==0}">
				<tr style="color:red;">
			</c:if>
			<c:if test="${(index.index+1)%2!=0}">
				<tr style="color:red;">
			</c:if>
				<td style="color:black;text-align:center;">${arrList[0]}&nbsp;${arrList[1]}</td>
				<td style="text-align:center;" >${arrList[2]}&nbsp;</td>
				<td style="text-align:center;" ><fmt:formatNumber value="${arrList[3]}" pattern="#0.0"/>&nbsp;</td>
				<td style="text-align:center;">${arrList[4]}&nbsp;</td>
				<td style="text-align:center;"><fmt:formatNumber value="${arrList[5]}" pattern="#0.0"/>&nbsp;</td>
				
				<td style="text-align:center;">${arrList[6]}&nbsp;</td>
				<td style="text-align:center;" ><fmt:formatNumber value="${arrList[7]}" pattern="#0.0"/>&nbsp;</td>
				<td style="text-align:center;">${arrList[8]}&nbsp;</td>
				<td style="text-align:center;"><fmt:formatNumber value="${arrList[9]}" pattern="#0.0"/>&nbsp;</td>
				
				<td style="text-align:center;" >${arrList[10]}&nbsp;</td>
				<td style="text-align:center;" ><fmt:formatNumber value="${arrList[11]}" pattern="#0.0"/>&nbsp;</td>
				<td style="text-align:center;">${arrList[12]}&nbsp;</td>
				<td style="text-align:center;"><fmt:formatNumber value="${arrList[13]}" pattern="#0.0"/>&nbsp;</td>
				
				<td style="text-align:center;" >${arrList[14]}&nbsp;</td>
				<td style="text-align:center;" ><fmt:formatNumber value="${arrList[15]}" pattern="#0.0"/>&nbsp;</td>
				<td style="text-align:center;" >${arrList[16]}&nbsp;</td>
				<td style="text-align:center;" ><fmt:formatNumber value="${arrList[17]}" pattern="#0.0"/>&nbsp;</td>
				<td style="text-align:center;" >${arrList[18]}&nbsp;</td>
				<td style="text-align:center;" ><fmt:formatNumber value="${arrList[19]}" pattern="#0.0"/>&nbsp;</td>
				<td style="text-align:center;" >${arrList[20]}&nbsp;</td>
				<td style="text-align:center;" ><fmt:formatNumber value="${arrList[21]}" pattern="#0.0"/>&nbsp;</td>
			</tr>
		</c:forEach>
		<tr style="color:red;font-weight:bold;">
				<td style="text-align:center;">${strsList[1]}</td>
				<td style="text-align:center;"><fmt:formatNumber value="${strsList[2]}" pattern="#0"/></td>
				<td style="text-align:center;"><fmt:formatNumber value="${strsList[3]}" pattern="#0.0"/></td>
				<td style="text-align:center;"><fmt:formatNumber value="${strsList[4]}" pattern="#0"/></td>
				<td style="text-align:center;"><fmt:formatNumber value="${strsList[5]}" pattern="#0.0"/></td>
				
				<td style="text-align:center;"><fmt:formatNumber value="${strsList[6]}" pattern="#0"/></td>
				<td style="text-align:center;"><fmt:formatNumber value="${strsList[7]}" pattern="#0.0"/></td>
				<td style="text-align:center;"><fmt:formatNumber value="${strsList[8]}" pattern="#0"/></td>
				<td style="text-align:center;"><fmt:formatNumber value="${strsList[9]}" pattern="#0.0"/></td>
				
				<td style="text-align:center;"><fmt:formatNumber value="${strsList[10]}" pattern="#0"/></td>
				<td style="text-align:center;"><fmt:formatNumber value="${strsList[11]}" pattern="#0.0"/></td>
				<td style="text-align:center;"><fmt:formatNumber value="${strsList[12]}" pattern="#0"/></td>
				<td style="text-align:center;"><fmt:formatNumber value="${strsList[13]}" pattern="#0.0"/></td>
				
				<td style="text-align:center;"><fmt:formatNumber value="${strsList[14]}" pattern="#0"/></td>
				<td style="text-align:center;"><fmt:formatNumber value="${strsList[15]}" pattern="#0.0"/></td>
				
				<td style="text-align:center;"><fmt:formatNumber value="${strsList[16]}" pattern="#0"/></td>
				<td style="text-align:center;"><fmt:formatNumber value="${strsList[17]}" pattern="#0.0"/></td>
				<td style="text-align:center;"><fmt:formatNumber value="${strsList[18]}" pattern="#0"/></td>
				<td style="text-align:center;"><fmt:formatNumber value="${strsList[19]}" pattern="#0.0"/></td>
				<td style="text-align:center;"><fmt:formatNumber value="${strsList[20]}" pattern="#0"/></td>
				<td style="text-align:center;"><fmt:formatNumber value="${strsList[21]}" pattern="#0.0"/></td>
			</tr>
	</table>
</div>
     </body>
</html>