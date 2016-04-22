<%@ page language="java" contentType="application/vnd.ms-excel;charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="java.text.DecimalFormat"%>
 
<%@page import="java.net.URLEncoder"%>
<%
   response.setHeader("Content-Disposition","inline;filename="+URLEncoder.encode("代理商交易统计报表","UTF-8")+".xls"); 
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
              <x:Name>代理商交易统计报表</x:Name>
            </x:ExcelWorksheet>
         </x:ExcelWorksheets>
       </x:ExcelWorkbook>
   </xml>
</head>
 
<body rightmargin="0" leftmargin="0" topmargin="0" class="body_no_bg">
	<table id="myTable" style="border: 1px ridge solid;">
		<thead>
		<tr colspan="7">
		   <th  colspan="7"><h3>代理商交易统计报表</h3> </th>
           </tr>
			<tr>
				<th style="border: 1px ridge solid; font-weight: bold;">
					代理商
				</th>
				<th style="border: 1px ridge solid; font-weight: bold;">
					业务类型
				</th>
				<th style="border: 1px ridge solid; font-weight: bold;">
					交易状态
				</th>
				<th style="border: 1px ridge solid; font-weight: bold;">
					交易终端
				</th>
				<th style="border: 1px ridge solid; font-weight: bold;">
					应收金额(元)
				</th>
				<th style="border: 1px ridge solid; font-weight: bold;">
					实收金额(元)
				</th>
				<th style="border: 1px ridge solid; font-weight: bold;">
					交易笔数(笔)
				</th>
			</tr>
		</thead>
		<tbody id="tab" style="cursor: pointer;">
			<%
				    double sumFee = 0;
					int count = 0;

					double sumFeeTemp = 0;
					int countTemp = 0;
					boolean bol = false;
					boolean isPrint = false;
					List<String[]> arr = (List<String[]>) request
							.getAttribute("cityCount");
					for (int i = 0; arr != null && i < arr.size(); i++) {
						bol = false;

						sumFee += Double.parseDouble(arr.get(i)[4] + "");
						count += Double.parseDouble(arr.get(i)[5] + "");
			%>
			<tr>
				<%
					if (i == 0) {
				%>
				<td rowspan="<%=arr.get(i)[7]%>" style="border: 1px ridge solid;text-align: center;"><%=arr.get(i)[0]%></td>
				<%
					} else {
								if (isPrint && !arr.get(i)[0].equals(arr.get(i - 1)[0])
										&& !"2".equals(arr.get(i - 1)[6])) {
									isPrint = false;
				%>
				<td rowspan="<%=arr.get(i)[7]%>" style="border: 1px ridge solid;text-align: center;"><%=arr.get(i)[0]%></td>
				<%
					} else {
									bol = true;
								}
							}

							if ((i > 0 && !arr.get(i)[0].equals(arr.get(i - 1)[0])))//判断是否输出合计 
							{
								isPrint = true;
				%>

				<td style="border: 1px ridge solid;text-align: center;" colspan="3">
					【 合计 】
				</td>

				<td style="border: 1px ridge solid;text-align: center;">
					<%=new DecimalFormat("##,###.##")
										.format(sumFeeTemp)%></td>
				<td style="border: 1px ridge solid;text-align: center;">
					<%=new DecimalFormat("##,###.##")
										.format(sumFeeTemp)%></td>
				<td style="border: 1px ridge solid;text-align: center;">
					<%=countTemp%></td>
			</tr>
			<tr>
				<%
					sumFeeTemp = 0;
								countTemp = 0;
							}
							if (i == 0 || arr.get(i)[0].equals(arr.get(i - 1)[0])|| isPrint) {
								sumFeeTemp += Double.parseDouble(arr.get(i)[4] + "");
								countTemp += Double.parseDouble(arr.get(i)[5] + "");
							}
							if (bol && !arr.get(i)[0].equals(arr.get(i - 1)[0])) {
								isPrint = false;
				%>
				<td rowspan="<%=arr.get(i)[7]%>" style="border: 1px ridge solid;text-align: center;"><%=arr.get(i)[0]%></td>
				<%
					}
							
				%>
				<td style="border: 1px ridge solid;text-align: center;">
					<%=arr.get(i)[1]%></td>
								<td style="border: 1px ridge solid;text-align: center;"><%=arr.get(i)[3]%></td>
				<td style="border: 1px ridge solid;text-align: center;"><%=arr.get(i)[2]%>
				</td>

				<td style="border: 1px ridge solid;text-align: center;"><%=arr.get(i)[4]%></td>
				<td style="border: 1px ridge solid;text-align: center;"><%=arr.get(i)[4]%></td>
				<td style="border: 1px ridge solid;text-align: center;"><%=arr.get(i)[5]%></td>
			</tr>
			<%
				if (i == arr.size() - 1)//判断是否输出合计 
						{
			%>

			<td style="border: 1px ridge solid;text-align: center;" colspan="3">
				【 合计 】
			</td>

			<td style="border: 1px ridge solid;text-align: center;">
				<%=new DecimalFormat("##,###.##")
										.format(sumFeeTemp)%></td>
			<td style="border: 1px ridge solid;text-align: center;">
				<%=new DecimalFormat("##,###.##")
										.format(sumFeeTemp)%></td>
			<td style="border: 1px ridge solid;text-align: center;">
				<%=countTemp%></td>
			</tr>
			<tr>
				<%
					}
						}
				%>
			
			<tr>
				<td style="border: 1px ridge solid;text-align: center;" colspan="4">
					【 总计 】
				</td>

				<td style="border: 1px ridge solid;text-align: center;">
					<%=new DecimalFormat("##,###.##").format(sumFee)%></td>
				<td style="border: 1px ridge solid;text-align: center;">
					<%=new DecimalFormat("##,###.##").format(sumFee)%></td>
				<td style="border: 1px ridge solid;text-align: center;">
					<%=count%></td>
			</tr>
		</tbody>

	</table>
</body> 
</html>