<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>流量订单</title>
    <script type="text/javascript">
    	function fun(){
    		var proName=document.getElementById("dd").value;
    		if(proName==""|| proName.length<=0){
    			alert("请输入订单号");
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
    	<form action="<%=path %>/wlttencent/index1.jsp" method="post" id="forms">
    		<table style="width:100%;height:300px;border:1px solid #ccc;">
    			<tr>
    				<td colspan=2 style="font-size:30px;color:#000;text-align:center;">修改状态</td>
    			</tr>
    			<tr>
    				<td style="text-align:right;font-size:25px;width:200px;">订单号:</td>
    				<td>
    					<input type="text" value="" name="dd" id="dd"  style="width:500px;height:40px;font-size:25px;"/>
    				</td>
    			</tr>
 			<tr>
    			<tr>
    				<td style="text-align:right;font-size:25px;width:200px;">表名:</td>
    				<td>
    					<input type="text" value="wht_orderform_1509" name="bm" id="bm" maxlength=20  style="width:300px;height:40px;font-size:25px;"/>
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
