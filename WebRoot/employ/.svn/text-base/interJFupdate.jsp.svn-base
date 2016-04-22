<%@ page contentType="text/html; charset=GBK" language="java"%>
<%@ page import="java.util.*"%>
<%
	String path = request.getContextPath();
%>
<html>
	<head>
		<title>万汇通</title>
		<script type="text/javascript" src="../js/jquery-1.8.2.min.js"></script>
		<link href="../css/main_style.css" rel="stylesheet" type="text/css" />
		<SCRIPT type=text/javascript>
function check(id){
	var val=document.getElementById("val").value;
	if(val == ""){
		alert("请输入佣金百分比 !");
		return;
	}
	if(val.split(".")[1].length>2){
   	    alert("交易金额的小数点只能有两位！");
	    return;
	}
	if(confirm("确认如此分配?")){
	    document.getElementById("checkCode").value="请稍后...";
		document.getElementById("checkCode").disabled="disabled";
		document.getElementById("for").action="<%=path%>/business/prod.do?method=InterJFlist&bool=-13&id="+id;
		document.getElementById("for").target="_self";
		document.getElementById("for").submit(); 
		return ;
	}
}
</SCRIPT>
	</head>
	<body>
		<div id="popdiv">
			<span id="sellist"></span>
			<div style="clear: both"></div>
		</div>
		<input type="hidden" id="mess" value="${requestScope.mess }" />
		<form method="post" id="for" action="">
			<div class="pass_main">
				<div class="pass_title_1">
					修改接口商交通罚款佣金比例
				</div>
				<div class="pass_list">
					<ul>
						<li>
							<strong>佣金组名称 ：</strong>
							<span> 
								<input value="${arryList[0][5]}" readonly="readonly" name="groupid"/>
								
							</span>
						<li>
							<strong>地市 ：</strong>
							<span> 
								<input  id="cm_area" value="${arryList[0][0]}" readonly="readonly" /> 
							</span>
						</li>
						<li>
							<strong>汽车类型：</strong>
							<span> 
								<input  value="${arryList[0][2] }" readonly="readonly" /> 
							</span>
						</li>
						<li>
							<strong>佣金比：</strong>
							<span>
								<input name="val" id="val" type="text" value="${arryList[0][4]}" />&nbsp;<font color="red" size="3">%最多两位小数</font>
							</span>
						</li>
					</ul>
				</div>
				<div class="field-item_button_m">
					<input type="button" value="确认" id="checkCode" onclick="check(${arryList[0][6]})"
						class="field-item_button" />
					<input type="button" value="取消" id="checkCode1"
						onClick="javascript:history.go(-1)" class="field-item_button" />
				</div>
			</div>
			<input type="hidden" value="${cm_one}" name="cm_one" />
			<input type="hidden" value="${groups}" name="groups" />
			<input type="hidden" value="${carType}" name="carType" />
			<input type="hidden" value="${index}" name="index" />
		</form>
	</body>
</html>