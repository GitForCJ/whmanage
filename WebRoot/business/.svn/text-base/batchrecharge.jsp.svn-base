<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">
		<title>批量充值</title>
		<link href="<%=path%>/css/chongzhi_mobile.css" rel="stylesheet"
			type="text/css" />
		<script src="<%=path%>/js/jquery-1.8.2.min.js" type="text/javascript"></script>
		<script type="text/javascript" src="<%=path%>/js/util.js"></script>
		<script language='javascript' src='<%=path%>/js/jquery.js'></script>
		<link href="<%=path%>/css/main_style.css" rel="stylesheet"
			type="text/css" />
		<link href="<%=path%>/css/style.css" rel="stylesheet" type="text/css" />


		<script type="text/javascript">
if("${mess}"!=null && "${mess}"!=""){
	alert("${mess}");
}

function validate(cla){
	var uploadFile=document.getElementById("uploadFile");
	var tradepass=document.getElementById("tradepass");
   if(uploadFile.value==""){
	   alert("文件不能为空");
	   return;
   }
   if(uploadFile.value.Trim().endWith(".txt")==false){
   	   alert("文件后缀格式 .txt 结尾!");
   	   return ;
   }
   if(tradepass.value==""||tradepass.value.length<5){
	   alert("密码不能为空");
	   return;
   }
   document.form.submit();
   cla.value="充值中,,"
   cla.style.backgroundColor="#cccccc";
   cla.disabled=true;
   return ;
}
// 去空格
String.prototype.Trim = function() 
{ 
	return this.replace(/(^\s*)|(\s*$)/g, ""); 
} 
String.prototype.endWith=function(str){  
	if(str==null||str==""||this.length==0||str.length>this.length)  
	  return false;  
	if(this.substring(this.length-str.length)==str)  
	  return true;  
	else  
	  return false;  
	return true;  
}  
</script>
	</head>
	<body>
		<div id="tabbox"
			style="border: 1px solid #cccccc; border-bottom: 0px;">
			<div id="tabs_title">
				<ul class="tabs_wzdh">
					<li>
						批量充值
					</li>
				</ul>
			</div>
		</div>
		<form action="<%=path%>/batchcharge.do?method=rechage" method="post"
			enctype="multipart/form-data" name="form">
			<div
				style="width: auto; height: 250px; border: 1px solid #cccccc; padding-top: 30px;">
				<ul style="width: auto; padding-left: 100px;">
					<li
						style="width: auto; height: 70px; line-height: 70px; font-size: 20px;">
						批量文件:&nbsp;
						<input type="file" name="batchfile" id="uploadFile" size="50"
							style="width: 385px; height: 40px;  background-color: #ffffff;">
					</li>
					<li
						style="width: auto; height: 70px; line-height: 70px; font-size: 20px;">
						交易密码:&nbsp;
						<input type="password" value="" name="tradepass" id="tradepass"
							style="height: 40px; width: 300px; font-size: 25px;" />
					</li>
					<li
						style="width: auto; height: 70px; padding-top: 20px; padding-left: 90px;">
						<input onclick="validate(this)" type="button"
							style="background-color: #258ED2; font-size: 18px; width: 120px; height: 35px; color: #ffffff; font-weight: bold;"
							value="提交充值" />
					</li>
				</ul>
			</div>
		</form>
		<br/>
		<div
			style="padding-left: 40px; padding-top: 10px; font-size: 13px; color: #FF6600; font-weight: bold; border: 1px solid #FF9E4d; background-color: #FFFFE5;">
			<ul>
				<li style="list-style: none;">
					1、全国三网话费批量充值,文件格式,1.".txt"结尾文件 2.一行一笔充值, 3.充值号码#充值金额(单位元)
				</li>
				<li style="list-style: none;">
					&nbsp;
				</li>
				<li style="list-style: none;">
					2、（遇运营商活动等特殊事件期间暂停）冲正操作不承诺一定成功。
				</li>
				<li style="list-style: none;">
					&nbsp;
				</li>
			</ul>
		</div>
	</body>
</html>
