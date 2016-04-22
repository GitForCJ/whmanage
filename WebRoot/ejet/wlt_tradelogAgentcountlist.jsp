<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ page import="java.util.*"%>
<%@page import="java.text.DecimalFormat"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<jsp:useBean id="userSession" scope="session"
	class="com.wlt.webm.rights.form.SysUserForm" />
<jsp:useBean id="user" scope="page"
	class="com.wlt.webm.rights.bean.SysUser" />
<%
	String path = request.getContextPath();
	String srType = request.getParameter("st");
	List list = user.getSysUserList(srType, request.getSession() );
	request.setAttribute("userList", list);
%>
<html:html>
<jsp:useBean id="area" scope="page"
	class="com.wlt.webm.rights.bean.SysArea" />
<head>
	<title>万汇通</title>
	<link href="../css/main_style.css" rel="stylesheet" type="text/css" />
	<link rel="stylesheet" type="text/css" href=../css/common.css>
	<style type="text/css">
.tabs_wzdh {
	width: 100px;
	float: left;
}

.tabs_wzdh li {
	height: 35px;
	line-height: 35px;
	margin-top: 5px;
	padding-left: 20px;
	font-size: 14px;
	font-weight: bold;
}

/*tree_nav*/
.tree_nav {
	border: 1px solid #CCC;
	background: #f5f5f5;
	padding: 10px;
	margin-top: 20px;
}

.field-item_button_m2 {
	margin: 0 auto;
	text-align: center;
}

.field-item_button2 {
	border: 0px;
	width: 136px;
	height: 32px;
	color: #fff;
	background: url(../images/button_out2.png) no-repeat;
	cursor: pointer;
	margin-right: 20px;
}

.field-item_button2:hover {
	border: 0px;
	width: 136px;
	height: 32px;
	color: #fff;
	background: url(../images/button_on2.png) no-repeat;
	margin-right: 20px;
}
</style>
	<script type="text/javascript" src="../js/util.js"></script>
	<SCRIPT src="../js/more_tiaojian.js" type=text/javascript></SCRIPT>
	<script language='javascript' src='../js/jquery.js'></script>
	<script language="javascript" type="text/javascript"
		src="../My97DatePicker/WdatePicker.js"></script>
	<link href="../css/selectCss.css" rel="stylesheet" type="text/css" />
	<script src="../js/jquery-1.8.2.min.js" type="text/javascript"></script>
	<script src="../js/selectCss.js" type="text/javascript"></script>

</head>
<script language="JavaScript">
	
</script>
<body rightmargin="0" leftmargin="0" topmargin="0" class="body_no_bg">
	<table id="myTable" style="border: 1px ridge solid;">
		<thead>
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
				<td class="td_line" rowspan="<%=arr.get(i)[7]%>" style="border: 1px ridge solid;"><%=arr.get(i)[0]%></td>
				<%
					} else {
								if (isPrint && !arr.get(i)[0].equals(arr.get(i - 1)[0])
										&& !"2".equals(arr.get(i - 1)[6])) {
									isPrint = false;
				%>
				<td class="td_line" rowspan="<%=arr.get(i)[7]%>" style="border: 1px ridge solid;"><%=arr.get(i)[0]%></td>
				<%
					} else {
									bol = true;
								}
							}

							if ((i > 0 && !arr.get(i)[0].equals(arr.get(i - 1)[0])))//判断是否输出合计 
							{
								isPrint = true;
				%>

				<td class="td_line" style="border: 1px ridge solid;" colspan="3">
					【 合计 】
				</td>

				<td class="td_line" style="border: 1px ridge solid;">
					<%=new DecimalFormat("##,###.##")
										.format(sumFeeTemp)%></td>
				<td class="td_line" style="border: 1px ridge solid;">
					<%=new DecimalFormat("##,###.##")
										.format(sumFeeTemp)%></td>
				<td class="td_line" style="border: 1px ridge solid;">
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
				<td class="td_line" rowspan="<%=arr.get(i)[7]%>" style="border: 1px ridge solid;"><%=arr.get(i)[0]%></td>
				<%
					}
							
				%>
				<td class="td_line" style="border: 1px ridge solid;">
					<%=arr.get(i)[1]%></td>
				<td class="td_line" style="border: 1px ridge solid;"><%=arr.get(i)[3]%></td>
				<td class="td_line" style="border: 1px ridge solid;"><%=arr.get(i)[2]%></td>
				<td class="td_line" style="border: 1px ridge solid;"><%=new DecimalFormat("##,###.##")
										.format(Double.parseDouble(arr.get(i)[4])/100)%></td>
				<td class="td_line" style="border: 1px ridge solid;"><%=new DecimalFormat("##,###.##")
										.format(Double.parseDouble(arr.get(i)[4])/100)%></td>
				<td class="td_line" style="border: 1px ridge solid;"><%=arr.get(i)[5]%></td>
			</tr>
			<%
				if (i == arr.size() - 1)//判断是否输出合计 
						{
			%>

			<td class="td_line" style="border: 1px ridge solid;" colspan="3">
				【 合计 】
			</td>

			<td class="td_line" style="border: 1px ridge solid;">
				<%=new DecimalFormat("##,###.##")
										.format(sumFeeTemp)%></td>
			<td class="td_line" style="border: 1px ridge solid;">
				<%=new DecimalFormat("##,###.##")
										.format(sumFeeTemp)%></td>
			<td class="td_line" style="border: 1px ridge solid;">
				<%=countTemp%></td>
			</tr>
			<tr>
				<%
					}
						}
				%>
			
			<tr>
				<td class="td_line" style="border: 1px ridge solid;" colspan="4">
					【 总计 】
				</td>

				<td class="td_line" style="border: 1px ridge solid;">
					<%=new DecimalFormat("##,###.##").format(sumFee)%></td>
				<td class="td_line" style="border: 1px ridge solid;">
					<%=new DecimalFormat("##,###.##").format(sumFee)%></td>
				<td class="td_line" style="border: 1px ridge solid;">
					<%=count%></td>
			</tr>
		</tbody>

	</table>
</body>
</html:html>