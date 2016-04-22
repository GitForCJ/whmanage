<%@ page language="java" import="java.util.*,java.io.*"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<jsp:useBean id="prod" scope="page"
	class="com.wlt.webm.business.bean.BiProd" />
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	String inter = request.getParameter("inter");
	String flowId = request.getParameter("flowId");
	String val=request.getParameter("val");
	String sertypes=request.getParameter("sertypes");
	String flag=request.getParameter("flag");
	if (inter != null && !"".equals(inter) && val!=null && !"".equals(val) && sertypes!=null && !"".equals(sertypes) && flag!=null && !"".equals(flag)) {
		PrintWriter p = response.getWriter();
		p.println("<script language='javascript'>");
		boolean bool = prod.update_Flow(inter, flowId,val,sertypes,flag);
		if (bool) {
			p.println("alert('操作成功!');");
		} else {
			p.println("alert('数据重复,操作失败!');");
		}
		p.print("</script>");
	}
	//流量配置列表
	List<String[]> arryList = prod.flow_config();
	request.setAttribute("arryList", arryList);

	//接口列表
	List areaList = prod.getInterface();
	request.setAttribute("areaList", areaList);

	//流量接口对应的面额 数据
	List<String[]> arrys = prod.flow_cinfig_interList();
	request.setAttribute("arrys", arrys);
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">
		<title>Flow Config</title>
		<link href="<%=path%>/css/main_style.css" rel="stylesheet"
			type="text/css" />
		<script type="text/javascript" src="<%=path%>/js/jquery-1.8.2.min.js"></script>
		<style type="text/css">
			#myTable td.c0{
				background:#FBEEC5;
			}
			#myTable td.c1{
				background:#cccccc;
			}
			#myTable td.c2{
				background:#E5E9F3;
			}
		</style>
		<script type="text/javascript">
			function fun(id,selectId,moenyVal,serviceType)
			{
				document.getElementById("flow_id").value=id;
				document.getElementById("flow_flag").value="1";
				document.getElementById("interId").value=selectId;
				document.getElementById("sertyps").value=serviceType;
				
				var divTwo=document.getElementById("showDialog");
				divTwo.style.left=(document.body.clientWidth-400)/2+"px";
				divTwo.style.top=window.screen.height/2-250+"px";
				divTwo.style.display="block";
				
				//加载接口对应的面额
				funMoneyLoad(selectId,moenyVal);
			}
			
			//增加
			function funAdd(){
				document.getElementById("flow_flag").value="0";
				$('#interId')[0].selectedIndex = 0;
				$('#sertyps')[0].selectedIndex = 0;
				
				var divTwo=document.getElementById("showDialog");
				divTwo.style.left=(document.body.clientWidth-400)/2+"px";
				divTwo.style.top=window.screen.height/2-250+"px";
				divTwo.style.display="block";
				
				//加载接口对应的面额
				funMoneyLoad('','');
			}
			
			
			function funMoneyLoad(interId,moenyVal){
				var optstr="";
				 <%for(int i=0; i <arrys.size(); i++){%> 
			       if(interId==<%=((String[]) arrys.get(i))[0]%>){
					 optstr=optstr+"<option value='<%=((String[]) arrys.get(i))[1]%>'><%=((String[]) arrys.get(i))[1]%></option>";
			       }
				 <%}%>
				 $("#moneyList").html(optstr);
				 if(moenyVal!=""){
				 	document.getElementById("moneyList").value=moenyVal;
				 }
			}
			
			function funClose(){
				document.getElementById("showDialog").style.display="none";
				return ;
			}
			function funsub(){
				var inter=document.getElementById("interId").value;
				var flow_id=document.getElementById("flow_id").value;
				var sertypes=document.getElementById("sertyps").value;
				var flag=document.getElementById("flow_flag").value;
				var money_val=$("#moneyList").val();
				if(money_val=="" || money_val==null ){
					alert("请选择流量接口!");
					return ;
				}
				window.location.href="<%=path%>/rights/flowconfig.jsp?inter="+inter+"&flowId="+flow_id+"&val="+money_val+"&sertypes="+sertypes+"&flag="+flag;
				return ;
			}
			
			
			jQuery(document).ready( 
				function () {
					$('#li_td').mousedown(
						function (event) {
							var isMove = true; 
							var abs_x = event.pageX - $('#showDialog').offset().left; 
							var abs_y = event.pageY - $('#showDialog').offset().top; 
							$(document).mousemove(
								function (event) {
									if (isMove)
									{
										var obj = $('#showDialog'); 
										obj.css({'left':event.pageX - abs_x, 'top':event.pageY - abs_y}); 
									}
								}
								).mouseup(
									function () {
									isMove = false; 
								}
							); 
						} 
					); 
				} 
			); 
		</script>
	</head>

	<body>
		<div id="tabbox">
			<div id="tabs_title">
				<ul class="tabs_wzdh">
					<li>
						流量接口配置
					</li>
				</ul>
			</div>
		</div>
		<div>
			<table id="myTable" cellspacing="0" cellpadding="0" class="tab_line"
				style="width: 100%">
				<thead>
					<tr height="35" class="tr_line_bottom">
						<th>
							序号
						</th>
						<th>
							运营商类型
						</th>
						<th>
							流量接口
						</th>
						<th>
							面额
						</th>
						<th>
							操作
						</th>
					</tr>
					<c:forEach items="${arryList}" var="arrs" varStatus="index">
						<tr height=25>
							<td class="c${arrs[5]}">
								${index.index+1}
							</td>
							<td class="c${arrs[5]}">
								${arrs[1]}
							</td>
							<td class="c${arrs[5]}">
								${arrs[2]}
							</td>
							<td class="c${arrs[5]}">
								${arrs[4]}
							</td>
							<td class="c${arrs[5]}">
								<a href="javascript:fun(${arrs[0]},${arrs[3]},'${arrs[4]}','${arrs[5]}')">修改</a>
							</td>
						</tr>
					</c:forEach>
					<tbody>
						<tr>
							<td colspan="5" style="border: 1px solid #FBEEC5;text-align:center;padding-top:10px;">
								<input onclick="funAdd()" type="button"
									style="width: 85px; height: 32px;font-size:15px;border-radius:6px;background-color:#cccccc" value="增加" />
							</td>
						</tr>
					</tbody>
			</table>
		</div>


		<div id="showDialog"
			style="box-shadow: 0 0 20px #ccc; display: none; width: 400px; height: 230px; border-radius: 10px; border: 10px solid #FBEEC5; position: absolute; opacity: 1; padding: 0px;">
			<ul style="height: 230px; background-color: #F9F2E3;">
				<li id="li_td"
					style="cursor: pointer; height: 50px; line-height: 50px; font-weight: bold; font-size: 16px; background-color: #F8F8F8; padding-left: 20px;">
					流量接口切换
				</li>
				<li
					style="font-weight: bold; font-size: 14px; height: 40px; line-height: 50px; padding-left: 50px;">
					<font>运营类型：</font> &nbsp;
					<font> <select
							style="width: 150px; height: 25px; background-color: #E5E9F3" id="sertyps" >
							<option value="0">电信流量</option>
							<option value="1">移动流量</option>
							<option value="2">联通流量</option>
						</select><input type="hidden" value="" id="flow_flag" /></font>
				</li>
				<li
					style="font-weight: bold; font-size: 14px; height: 40px; line-height: 50px; padding-left: 50px;">
					<font>流量接口：</font> &nbsp;
					<font> <select
							style="width: 150px; height: 25px; background-color: #E5E9F3" onChange="funMoneyLoad(this.value,'')"
							id="interId">
							<c:forEach items="${areaList}" var="arr">
								<option value="${arr[0]}">
									${arr[1]}
								</option>
							</c:forEach>
						</select> <input type="hidden" value="" id="flow_id" /> </font>
				</li>
				<li
					style="font-weight: bold; font-size: 14px; height: 40px; line-height: 50px; padding-left: 50px;">
					<font>面额选择：</font> &nbsp;
					<font> <select
							style="width: 150px; height: 25px; background-color: #E5E9F3"
							id="moneyList">

						</select> </font>
				</li>
				<li
					style="height: 45px; line-height: 45px; text-align: right; padding-right: 20px;padding-top:10px;">
					<input onclick="funClose()" type="button"
						style="width: 85px; height: 32px; border: 0px; color: #fff; font-weight: bold; background: url(<%=path%>/images/button_out.png) no-repeat bottom; cursor: pointer;"
						value="返回" />
					&nbsp;
					<input onclick="funsub()" type="button"
						style="width: 85px; height: 32px; border: 0px; color: #fff; font-weight: bold; background: url(<%=path%>/images/button_out.png) no-repeat bottom; cursor: pointer;"
						value="提交" />
				</li>
			</ul>
		</div>
	</body>
</html>
