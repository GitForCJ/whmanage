<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

String div=request.getParameter("div");
int aa=Integer.parseInt(div);
String name="";
switch(aa)
{
	case 1:
		name="平台登录问题";
		break;
	case 2:
		name="充值业务问题";
		break;
	case 3:
		name="订单查询问题";
		break;
	case 4:
		name="账户充值转账问题";
		break;
	case 5:
		name="账户安全问题";
		break;
}
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <base href="<%=basePath%>">
    
    <title></title>

<link href="<%=path %>/css/main_style.css" rel="stylesheet" type="text/css" />
<style type="text/css">
	a:hover{text-decoration:underline;color:red} 
</style>
<script type="text/javascript">
	
</script>
  </head>
  
  <body>
	 <div id="tabbox">
		<!--位置导航和选择菜单-->
		  <div id="tabs_title">
		    <ul class="tabs_wzdh"><li><%=name %></li></ul>
		
		  </div>
		</div>
		<%
			if("1".equals(div))
			{
				%>
				<div style="width:99%;margin-top:10px;">
					<ul style="border-bottom:1.5px dashed #ccc;height:40px;">
						<li style="line-height:40px;margin-left:10px;position: relative">
							<a href="<%=path %>/help/loginandregist.html" style="padding-left:28px;"><img src="<%=path %>/images/aa123.png" style="width:25px;height:25px;position: absolute;left:0px;top:5px;"/>&nbsp;如何在万汇通平台登录（登录注册）?</a>
						</li>
					</ul>
				</div>
				<%
			}
		 %>
		 
		 <%
			if("2".equals(div))
			{
				%>
					<div style="width:99%;margin-top:10px;">
						<ul style="border-bottom:1.5px dashed #ccc;height:40px;">
							<li style="line-height:40px;margin-left:10px;position: relative">
								<a href="<%=path %>/help/sjczwt.html" style="padding-left:28px;"><img src="<%=path %>/images/aa123.png" style="width:25px;height:25px;position: absolute;left:0px;top:5px;"/>&nbsp;关于手机充值问题（手机充值问题）?</a>
							</li>
						</ul>
						<ul style="border-bottom:1.5px dashed #ccc;height:40px;">
							<li style="line-height:40px;margin-left:10px;position: relative">
								<a href="<%=path %>/help/trade.html" style="padding-left:28px;"><img src="<%=path %>/images/aa123.png" style="width:25px;height:25px;position: absolute;left:0px;top:5px;"/>&nbsp;关于固话充值问题（固话充值问题）?</a>
							</li>
						</ul>
					</div>
				<%
			}
		 %>
		 
		 <%
			if("3".equals(div))
			{
				%>
					<div style="width:99%;margin-top:10px;">
						<ul style="border-bottom:1.5px dashed #ccc;height:40px;">
							<li style="line-height:40px;margin-left:10px;position: relative">
								<a href="<%=path %>/help/jymx.html" style="padding-left:28px;"><img src="<%=path %>/images/aa123.png" style="width:25px;height:25px;position: absolute;left:0px;top:5px;"/>&nbsp;如何查询您的交易记录（交易明细）?</a>
							</li>
						</ul>
						<ul style="border-bottom:1.5px dashed #ccc;height:40px;">
							<li style="line-height:40px;margin-left:10px;position: relative">
								<a href="<%=path %>/help/zwcx.html" style="padding-left:28px;"><img src="<%=path %>/images/aa123.png" style="width:25px;height:25px;position: absolute;left:0px;top:5px;"/>&nbsp;如何查询您的资金流水（账务查询）?</a>
							</li>
						</ul>
						<ul style="border-bottom:1.5px dashed #ccc;height:40px;">
							<li style="line-height:40px;margin-left:10px;position: relative">
								<a href="<%=path %>/help/jycz.html" style="padding-left:28px;"><img src="<%=path %>/images/aa123.png" style="width:25px;height:25px;position: absolute;left:0px;top:5px;"/>&nbsp;如何进行交易冲正（交易冲正）?</a>
							</li>
						</ul>
					</div>
				<%
			}
		 %>
		 
		 
		 <%
			if("4".equals(div))
			{
				%>
					<div style="width:99%;margin-top:10px;">
						<ul style="border-bottom:1.5px dashed #ccc;height:40px;">
							<li style="line-height:40px;margin-left:10px;position: relative">
								<a href="<%=path %>/help/cftcz.html" style="padding-left:28px;"><img src="<%=path %>/images/aa123.png" style="width:25px;height:25px;position: absolute;left:0px;top:5px;"/>&nbsp;如何使用财付通为账户充值（账户充值问题）?</a>
							</li>
						</ul>
						<ul style="border-bottom:1.5px dashed #ccc;height:40px;">
							<li style="line-height:40px;margin-left:10px;position: relative">
								<a href="<%=path %>/help/yzfzz.html" style="padding-left:28px;"><img src="<%=path %>/images/aa123.png" style="width:25px;height:25px;position: absolute;left:0px;top:5px;"/>&nbsp;如何使用翼支付为账户充值（翼支付为账户充值）?</a>
							</li>
						</ul>
						<ul style="border-bottom:1.5px dashed #ccc;height:40px;">
							<li style="line-height:40px;margin-left:10px;position: relative">
								<a href="<%=path %>/help/zhcz.html" style="padding-left:28px;"><img src="<%=path %>/images/aa123.png" style="width:25px;height:25px;position: absolute;left:0px;top:5px;"/>&nbsp;如何使用银行代扣为账户充值（银行代扣）?</a>
							</li>
						</ul>
						<ul style="border-bottom:1.5px dashed #ccc;height:40px;">
							<li style="line-height:40px;margin-left:10px;position: relative">
								<a href="<%=path %>/help/edzy.html" style="padding-left:28px;"><img src="<%=path %>/images/aa123.png" style="width:25px;height:25px;position: absolute;left:0px;top:5px;"/>&nbsp;如何使用账户转账（额度转移）?</a>
							</li>
						</ul>
					</div>
				<%
			}
		 %>
		 
		 <%
			if("5".equals(div))
			{
				%>
					<div style="width:99%;margin-top:10px;">
						<ul style="border-bottom:1.5px dashed #ccc;height:40px;">
							<li style="line-height:40px;margin-left:10px;position: relative">
								<a href="<%=path %>/help/mac.html" style="padding-left:28px;"><img src="<%=path %>/images/aa123.png" style="width:25px;height:25px;position: absolute;left:0px;top:5px;"/>&nbsp;如何绑定MAC地址（MAC地址绑定）?</a>
							</li>
						</ul>
						<ul style="border-bottom:1.5px dashed #ccc;height:40px;">
							<li style="line-height:40px;margin-left:10px;position: relative">
								<a href="<%=path %>/help/google.jsp" style="padding-left:28px;"><img src="<%=path %>/images/aa123.png" style="width:25px;height:25px;position: absolute;left:0px;top:5px;"/>&nbsp;如何绑定google身份验证器（google身份验证器）?</a>
							</li>
						</ul>
						<ul style="border-bottom:1.5px dashed #ccc;height:40px;">
							<li style="line-height:40px;margin-left:10px;position: relative">
								<a href="<%=path %>/help/xgmm.html" style="padding-left:28px;"><img src="<%=path %>/images/aa123.png" style="width:25px;height:25px;position: absolute;left:0px;top:5px;"/>&nbsp;如何修改密码（修改密码）?</a>
							</li>
						</ul>
						<ul style="border-bottom:1.5px dashed #ccc;height:40px;display:none;">
							<li style="line-height:40px;margin-left:10px;position: relative">
								<a href="" style="padding-left:28px;"><img src="<%=path %>/images/aa123.png" style="width:25px;height:25px;position: absolute;left:0px;top:5px;"/>&nbsp;如何使用短信验证（账户安全）?</a>
							</li>
						</ul>
					</div>
				<%
			}
		 %>
		
  </body>
  
</html>
