<%@ page language="java" import="java.util.*,java.net.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";

	String excelName = "接口商佣金.xls";
	excelName = excelName.replace(" ", "");
	excelName = URLEncoder.encode(excelName, "UTF-8");
	excelName = new String(excelName.getBytes("ISO8859-1"), "utf-8");

	response.reset();
	response.setContentType("application/vnd.ms-excel;charset=UTF-8");
	response.setContentType("application/msexcel; charset=UTF-8");
	response.setCharacterEncoding("utf-8");
	response.setHeader("Content-disposition", "inline;filename="
			+ excelName);
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html xmlns:x="urn:schemas-microsoft-com:office:excel">
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
#tables td {
	padding: 0px 0px 0px 0px;
	border: 0.1px solid blue;
	border-right: 0px;
	border-bottom: 0px;
	height: 30px;
}
</style>
	</head>
	<body>
		<div style="margin-top: 10px;">
			<table cellspacing="0" cellpadding="0"
				style="font-size: 13px; width: 900px;" border="1">
				<tr>
					<td colspan="5"
						style="text-align: center; height: 30px; font-size: 18px;">
						接口商佣金
					</td>
				</tr>
				<tr>
					<td colspan="4" style="text-align: right;">
						佣金组名称: ${userForm.groups}
					</td>
					<td>&nbsp;</td>
				</tr>
				<tr style="font-weight: bold;">
					<td style="text-align: center;">
						序号
					</td>
					<td style="text-align: center;">
						省份
					</td>
					<td style="text-align: center;">
						运营商
					</td>
					<td style="text-align: center;">
						面额区间
					</td>
					<td style="text-align: center;">
						佣金百分比
					</td>
				</tr>
				<c:forEach items="${userList}" var="arrList" varStatus="index">
					<tr>
					<td style="color: black; text-align: center;">
						${index.index+1 }
					</td>
					<td style="text-align: center;">
						${arrList[1]}&nbsp;
					</td>
					<td style="text-align: center;">
						${arrList[2]}&nbsp;
					</td>
					<td style="text-align: center;">
						${arrList[3]}&nbsp;
					</td>
					<td style="text-align: center;">
						<fmt:formatNumber value="${arrList[4]}" pattern="#0.00" />
						&nbsp;
					</td>
					</tr>
				</c:forEach>
			</table>
		</div>
	</body>
</html>