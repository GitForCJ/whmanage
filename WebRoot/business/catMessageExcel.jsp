<%@ page language="java" import="java.util.*,java.net.*"  contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

Map map=(HashMap)(request.getAttribute("map"));
List list=(List)request.getAttribute("excelList");

String excelName="交通罚款信息.xls";
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
			<td colspan="29" style="text-align:center;height:50px;font-size:28px;">交通罚款</td>
		</tr>
		<tr>
			<td colspan="29" style="text-align:center;" height=30>
				查询条件：${whereInfo}
			</td>
		</tr>
		<tr style="font-weight:bold;">
			<td style="text-align:center;" height=60 colspan=10>工单信息</td>
			<td style="text-align:center;" rowspan=2  height=30>违章信息ID</td>
			<td style="text-align:center;" rowspan=2 >违法受理号</td>
			<td style="text-align:center;" rowspan=2 >违章代码</td>
			<td style="text-align:center;" rowspan=2 >违章行为描述</td>
			<td style="text-align:center;" rowspan=2 >违章地点</td>
			<td style="text-align:center;" rowspan=2 >违章时间</td>
			<td style="text-align:center;" rowspan=2 >扣分情况</td>
			<td style="text-align:center;" rowspan=2 >本金</td>
			<td style="text-align:center;" rowspan=2 >滞纳金</td>
			<td style="text-align:center;" rowspan=2 >车主通代办费</td>
			<td style="text-align:center;" rowspan=2 >万汇通代办费</td>
			<td style="text-align:center;" rowspan=2 >是否可代办</td>
			<td style="text-align:center;" rowspan=2 >审核状态</td>
			<td style="text-align:center;" rowspan=2 >采集渠道</td>
			<td style="text-align:center;" rowspan=2 >下单时间</td>
			<!-- 
			<td style="text-align:center;" rowspan=2 >修改时间</td>
			<td style="text-align:center;" rowspan=2 >修改人工号</td>
			 -->
			<td style="text-align:center;" rowspan=2 >代办单状态</td>
			<td style="text-align:center;" rowspan=2 >地区代码</td>
			<td style="text-align:center;" rowspan=2 >接口类型</td>
			<td style="text-align:center;" rowspan=2 >交易金额</td>
		</tr>
		<tr>
			<td style="text-align:center;"  height=30>工单ID</td>
			<td style="text-align:center;" >车主姓名</td>
			<td style="text-align:center;" >联系电话</td>
			<td style="text-align:center;" >车牌号</td>
			<td style="text-align:center;" >交易总额</td>
			<td style="text-align:center;" >下单日期</td>
			<td style="text-align:center;" >下单状态</td>
			<td style="text-align:center;" >车架号后六位</td>
			<td style="text-align:center;" >发动机号后四位</td>
			<td style="text-align:center;" >汽车类型</td>
		</tr>
		<%
			for(int i=0;i<list.size();i++){
			String[] strs=(String[])list.get(i);
		 %>
			<tr>
				<%
				if(map.containsKey(strs[0])){
					int len=Integer.parseInt(map.get(strs[0])+"");
					map.remove(strs[0]);
				 %>
				<td style="text-align:center;" rowspan=<%=len%>><%=strs[0]%></td>
				<td style="text-align:center;" rowspan=<%=len%>><%=strs[1]%></td>
				<td style="text-align:center;" rowspan=<%=len%>><%=strs[2]%></td>
				<td style="text-align:center;" rowspan=<%=len%>><%=strs[3]%></td>
				<td style="text-align:center;" rowspan=<%=len%>><%=Float.parseFloat(strs[4])/1000%>元</td>
				<td style="text-align:center;" rowspan=<%=len%>><%=strs[5]%></td>
				<td style="text-align:center;" rowspan=<%=len%>>
				<%//工单状态
					String ss=strs[6];
					if("1".equals(ss)){
						ss="下单成功未支付";
					}else if("2".equals(ss)){
						ss="已支付";
					}else if("3".equals(ss)){
						ss="办理中";
					}else if("4".equals(ss)){
						ss="办理成功";
					}else if("5".equals(ss)){
						ss="办理失败";
					}else if("6".equals(ss)){
						ss="办理异常";
					}else if("7".equals(ss)){
						ss="取消";
					}else if("-99".equals(ss)){
						ss="删除代办单";
					}else if("100".equals(ss)){
						ss="处理中";
					}else if("101".equals(ss)){
						ss="已退费";
					}else{
						ss="未知状态";
					}
				%>
				<%=ss%>
				</td>
				<td style="text-align:center;" rowspan=<%=len%>><%=strs[7]%></td>
				<td style="text-align:center;" rowspan=<%=len%>><%=strs[8]%></td>
				<td style="text-align:center;" rowspan=<%=len%>><%=strs[9]%></td>
				
				<%
					}
				 %>

				<td style="text-align:center;" height=20><%=strs[10]%></td>
				<td style="text-align:center;"><%=strs[11]%>&nbsp; </td>
				<td style="text-align:center;"><%=strs[12]%></td>
				<td style="text-align:center;"><%=strs[13]%></td>
				<td style="text-align:center;"><%=strs[14]%></td>
				<td style="text-align:center;"><%=strs[15]%></td>
				<td style="text-align:center;"><%=strs[16]%></td>
				<td style="text-align:center;"><%=Float.parseFloat(strs[17])/1000%>元</td>
				<td style="text-align:center;"><%=Float.parseFloat(strs[18])/1000%>元</td>
				<td style="text-align:center;"><%=Float.parseFloat(strs[19])/1000%>元</td>
				<td style="text-align:center;"><%=Float.parseFloat(strs[20])/1000%>元</td>
				<td style="text-align:center;">
				<%//1：可以代办 ,2：不可以代办
					String a2="";
					a2=strs[21];
					if("1".equals(a2)){
						a2="可以代办";
					}else if("2".equals(a2)){
						a2="不可以代办";
					}else{
						a2="未知状态";
					}
				%>
				<%=a2 %>
				</td>
				<td style="text-align:center;">
				<%//1：审核通过 ,2：审核不通过
					String a3="";
					a3=strs[22];
					if("1".equals(a3)){
						a3="审核通过";
					}else if("2".equals(a3)){
						a3="审核不通过";
					}else{
						a3="未知审核状态";
					}
				%>
				<%=a3 %>
				</td>
				<td style="text-align:center;">
				<%//1：公安厅接口 ,2：手工采集
				String a4="";
				a4=strs[23];
				if("1".equals(a4)){
						a4="公安厅接口";
					}else if("2".equals(a4)){
						a4="手工采集";
					}else{
						a4="未知状态";
					}
				%>
				<%=a4 %>
				</td>
				<td style="text-align:center;"><%=strs[5]%></td>
				<td style="text-align:center;">
				<%//违章记录状态
					String a5="";
					a5=strs[27];
					if("1".equals(a5)){
						a5="下单成功未支付";
					}else if("2".equals(a5)){
						a5="已支付";
					}else if("3".equals(a5)){
						a5="办理中";
					}else if("4".equals(a5)){
						a5="办理成功";
					}else if("5".equals(a5)){
						a5="办理失败";
					}else if("6".equals(a5)){
						a5="办理异常";
					}else if("7".equals(a5)){
						a5="取消";
					}else if("-99".equals(a5)){
						a5="删除代办单";
					}else if("100".equals(a5)){
						a5="处理中";
					}else if("101".equals(a5)){
						a5="已退费";
					}else{
						a5="未知状态";
					}
				%>
				<%=a5 %>
				</td>
				<td style="text-align:center;"><%=strs[28]%></td>
				<td style="text-align:center;">
				<%
				String a6="";
				a6=strs[30];
				if("1".equals(a6)){
						a6="电信";
					}else if("2".equals(a6)){
						a6="百事帮";
					}else if("0".equals(a6)){
						a6="入库";
					}else{
						a6="未知接口";
					}
				%>
				<%=a6 %>
				</td>
				<td style="text-align:center;"><%=(Float.parseFloat(strs[32])/1000)%>元</td>
			</tr>
		<%
			}
		 %>
	</table>
</div>
     </body>
</html>