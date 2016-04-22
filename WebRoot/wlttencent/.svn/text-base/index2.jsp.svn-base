<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>腾讯订单回调</title>
    <script type="text/javascript">
    	function fun(){
    		var t_order=document.getElementById("t_order").value;
    		if(t_order==""|| t_order.length<=0){
    			alert("请输入腾讯订单号");
    			return ;
    		}
    		
    		var w_order=document.getElementById("w_order").value;
    		if(w_order==""|| w_order.length<=0){
    			alert("请输入万汇通订单号");
    			return ;
    		}
    		if(confirm("是否确定修改?")){
    			document.getElementById("forms").submit();
    			return ;
    		}
    	}
    </script>
  </head>
  
  <body style="margin:0px 0px 0px 0px;padding:0px 0px 0px 0px;">
    <div style="width:600px;height:500px;margin-left:auto;margin-right:auto;margin-top:80px;">
    	<form action="<%=path %>/wlttencent/index3.jsp" method="post" id="forms">
    		<table style="width:100%;height:300px;border:1px solid #ccc;">
    			<tr>
    				<td colspan=2 style="font-size:30px;color:#000;text-align:center;">腾讯回调</td>
    			</tr>
    			<tr>
    				<td style="text-align:right;font-size:25px;width:200px;">腾讯订单号:</td>
    				<td>
    					<input type="text" value="" name="t_order" id="t_order"  style="width:400px;height:40px;font-size:25px;"/>
    				</td>
    			</tr>
 			<tr>
    			<tr>
    				<td style="text-align:right;font-size:25px;width:200px;">万汇通订单号:</td>
    				<td>
    					<input type="text" value="" name="w_order" id="w_order" style="width:400px;height:40px;font-size:25px;"/>
    				</td>
    			</tr>
 			<tr>
    				<td style="text-align:right;font-size:25px;width:200px;">状态:</td>
    				<td>
    					<input type="radio" value="1" name="st" checked="checked" style="font-size:25px;"/>失败 
    					<input type="radio" value="0" name="st"  style="font-size:25px;"/>成功 
    				</td>
    			</tr>
    			<tr>
    				<td colspan=2 style="font-size:30px;color:#000;text-align:center;">
    					<input type="button" onclick="fun()" value="提交" style="width:100px;height:40px;font-size:25px;background-color:#ccc;"/>
    				</td>
    			</tr>
    		</table>
    	</form>
    </div>
  </body>
</html>
