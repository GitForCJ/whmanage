<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";

	String[] strs = request.getParameter("ids").split("#");
	request.setAttribute("str", strs);
	
	String groups=request.getParameter("groups");
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>万汇通</title>
		<script type="text/javascript" src="../js/jquery-1.8.2.min.js"></script>
		<link href="../css/main_style.css" rel="stylesheet" type="text/css" />
		<SCRIPT type=text/javascript>
function check(){
	var val=document.forms[0].cm_prod.value;
	if(val == ""){
		alert("请输入佣金百分比 !");
		return ;
	}
	if(val.substring(0,1)=="-")
	{
		var strtostring=val.substring(1,val.length);
		if(!/^[0-9]+(\.[0-9]+){0,1}$/.test(strtostring)||/^0+[0-9]+(\.[0-9]+){0,1}$/.test(strtostring))
		{
       	 alert("请输入正确的数字");
       	 return false;
        }
	}
	else
	{
		if(!/^[0-9]+(\.[0-9]+){0,1}$/.test(val)||/^0+[0-9]+(\.[0-9]+){0,1}$/.test(val))
		{
       	 alert("请输入正确的数字");
       	 return false;
        }
	}

	if(val.split(".").length>1){
		if(val.split(".")[1].length>2){
    	    alert("交易金额的小数点只能有两位！");
	    	return ;
	    }
    } 
    if(confirm("确认如此分配?")){
	    document.getElementById("checkCode").value="请稍后...";
		document.getElementById("checkCode").disabled="disabled";
		document.forms[0].target="_self";
		document.forms[0].submit();
		return ;
	}else{
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
		<form method="post" action="../business/prod.do?method=FlowUpdate">
			<div class="pass_main">
				<div class="pass_title_1">
					接口商流量佣金修改
				</div>
				<div class="pass_list">
					<ul>
						<li>
							<strong>佣金组名称 ：</strong><span> <input value="${str[3]}" readonly="readonly" disabled />
								<li>
									<strong>运营商 ：</strong><span> <input value="${str[0]}" readonly="readonly" disabled /> </span>
								</li>
								<li>
									<strong>类型：</strong><span> <input value="${str[1]}" readonly="readonly" disabled /> </span>
								</li>

								<li>
									<strong>佣金比：</strong><span><input name="cm_prod" type="text" value="${str[2]}" /><font color="red" size="3">%最多两位小数</font>
									</span>
								</li>
					</ul>
				</div>
				<div class="field-item_button_m">
					<input type="button" value="确认" id="checkCode" onclick="check()"
						class="field-item_button" />
					<input type="button" value="取消" id="checkCode1"
						onClick="javascript:history.go(-1)" class="field-item_button" />
				</div>
			</div>
			<input type="hidden" value="${str[4]}" name="id"/>
			<input type="hidden" value="<%=groups %>" name="groups"/>
			<input type="hidden" name="flag" value="update"/>
		</form>
	</body>
</html>
