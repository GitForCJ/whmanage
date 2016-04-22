<%@ page contentType="text/html; charset=GBK" language="java"%>
<%@ page import="java.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<jsp:useBean id="userSession" scope="session" class="com.wlt.webm.rights.form.SysUserForm" />
<jsp:useBean id="tpbean" scope="session" class="com.wlt.webm.business.bean.TpBean" />
<%
  String path = request.getContextPath();
  List groupsList=tpbean.getGroups();//获取话费佣金组名称
  
  List jfgroupsList=tpbean.getJTFKGroups();//获取交通罚款佣金组名称
  
  List Flow=tpbean.getFlowGroups();//流量佣金组
  
  String type=request.getParameter("type");
  String str=request.getParameter("str");
  if(str!=null && !"".equals(str))
  	str=new String(str.getBytes("iso-8859-1"),"gbk");
  List arryList=tpbean.getInterList(type);
  String var0="";
  String var1="";
  String var2="";
  String var3="";
  String var4="";
  String var5="";
  String var6="";
  String var8="";
  String var9="";
  String var10="";
  String var11="";
  String var12="";
   String var13="";
  if("1".equals(type))
  {
  	str=str.replaceAll("\"","");
  	var0=str.split(";")[0].toString().trim();
  	var1=str.split(";")[1].toString().trim();
  	var2=str.split(";")[2].toString().trim();
  	var3=str.split(";")[3].toString().trim();
  	var4=str.split(";")[4].toString().trim();
 	 var5=str.split(";")[5].toString().trim();
 	 var6=str.split(";")[6].toString().trim();
 	 var8=str.split(";")[7].toString().trim();
 	 var9=str.split(";")[8].toString().trim();
 	  var10=str.split(";")[9].toString().trim();
 	  var11=str.split(";")[10].toString().trim();
 	   var12=str.split(";")[11].toString().trim();
 	   var13=str.split(";")[12]==null?"":str.split(";")[12].toString().trim();
  }
 %>
 <html>
<head>
<title>万汇通</title>
<script type="text/javascript" src="../js/jquery-1.8.2.min.js"></script>
<link href="../css/main_style.css" rel="stylesheet" type="text/css" />
<link href="../css/selectCss.css" rel="stylesheet" type="text/css" /> 
<script language="javascript" type="text/javascript" src="../js/util.js"></script>
<SCRIPT type=text/javascript>
function check(type){
  	with(document.forms[0]){
  		if($("#user_no").val()==null || $("#user_no").val()=="")
  		{
  			alert("无接口商选择!");
  			return;
  		}
		if(keyvalue.value.Trim() == ""||keyvalue1.value.Trim()==""||ip.value.Trim()=="" || interName.value.Trim()=="" || qbCommission.value.Trim()==""){
			alert("请输入正确数据!");
			return;
		}
		
		if($("#keyvalue").val().Trim().length<12)
		{
			alert("接口商签名秘钥必须大于等于12位！");
			return;
		}
		
		if($("#keyvalue1").val().Trim().length!=8)
		{
			alert("接口商校验码秘钥只能是8位！");
			return;
		}
		if($("#groups").val()==null || $("#groups").val()==""){
			alert("请选择佣金组!");
  			return;
		}
		if(type==0)
		{
			document.getElementById("ff").action="<%=path%>/tpcharge.do?method=agentsignadd";
		}
		else
		{
			document.getElementById("ff").action="<%=path%>/tpcharge.do?method=agentsignupdate";
		}
		submit();
	}
}

// 去空格
String.prototype.Trim = function() 
{ 
	return this.replace(/(^\s*)|(\s*$)/g, ""); 
} 
function funHistory()
{
	window.location.href="<%=path%>/tpcharge.do?method=agentsignlist";
	return;
}

function funs(){
	var vs=document.getElementsByName("qbFlag");
	for(var i=0;i<vs.length;i++){
		if(vs[i].checked==true){
			if(vs[i].value==0){//单通道
				document.getElementById("y").disabled="disabled";
				document.getElementById("n").disabled="";
				document.getElementById("n").checked=true;
			}else{//多
				document.getElementById("y").disabled="";
				document.getElementById("n").disabled="";
			}
		}
	}
}
</SCRIPT>
</head>
<body onload="funs();">
<div id="popdiv"><span id="sellist"></span><div style="clear:both"></div></div>
<form  method="post" id="ff" action="">
<div class="pass_main">
	<div class="pass_title_1">
	<%
		if("0".equals(type))
		{
			out.print("接口商配置添加");
		}
		else
		{
		out.print("接口商配置修改");
		}
		%>
	
	</div>
    <div class="pass_list">
    	<ul>
    	<li><strong>用户编号 ：</strong><span>
    	<select id="user_no"  name="user_no">
    	<%
    		for(int i=0;i<arryList.size();i++)
    		{
    			Object[] obj=(Object[])arryList.get(i);
	    		if("1".equals(type))
	    		{
	    			if(var0.equals(obj[0]))
	    			{
			    		%>
			    			<option value="<%=obj[0] %>" selected><%=obj[1]+"/"+obj[2] %></option>
			    		<%
		    		}
	    		}
	    		else
	    		{
	    			%>
	    				<option value="<%=obj[0] %>" ><%=obj[1]+"/"+obj[2] %></option>
	    			<%
	    		}
    		}
    	%>
    	</select>
    	</span></li>
    	 <li><strong>分配接口商名称 ：</strong><span><input id="interName" name="interName" type="text"  value="<%=var5 %>"/></span></li>
    	<li><strong>&nbsp;</strong><span style="color:red;">(长度12位)</span></li>
            <li><strong>接口商签名秘钥 ：</strong><span><input id="keyvalue" name="keyvalue" type="text"  value="<%=var1 %>" maxlength=12/></span></li>
             
               <li><strong>&nbsp;</strong><span style="color:red;">(长度8位)</span></li>
            <li><strong>接口商校验码秘钥：</strong><span><input id="keyvalue1" name="keyvalue1" type="text"  value="<%=var2 %>" maxlength=8/></span></li>
          
            <li><strong>IP地址：</strong><span><input id="ip" name="ip" type="text"  value="<%=var3 %>" onkeyup="value=value.replace(/[^\d\.]/g,'')"/></span></li>
            <li><strong>Q币佣金：</strong><span><input id="qbCommission" name="qbCommission" type="text"  value="<%=var8 %>" onkeyup="value=value.replace(/[^\d\.]/g,'')"/></span></li>
            <li>
	            <strong>话费佣金组：</strong>
	            <span>
	            	<select style="width:145px;" name="groups" id="groups">
	            		<%
	            			for(int i=0;i<groupsList.size();i++)
	            			{
	            			String[] sss=(String[])groupsList.get(i);
	            		 %>
	            			<option  value="<%=sss[0]%>" <%=sss[0].equals(var10)?"selected":""%>><%=sss[0]%></option>
	            		<%
	            			}
	            		 %>
	            	</select>
	            </span>
            </li>
            <li>
	            <strong>交罚佣金组：</strong>
	            <span>
	            	<select style="width:145px;" name="jfgroups" id="jfgroups">
	            		<%
	            			for(int i=0;i<jfgroupsList.size();i++)
	            			{
	            			String[] sa=(String[])jfgroupsList.get(i);
	            		 %>
	            			<option  value="<%=sa[0]%>" <%=sa[0].equals(var11)?"selected":""%>><%=sa[0]%></option>
	            		<%
	            			}
	            		 %>
	            	</select>
	            </span>
            </li>
            <li>
	            <strong>流量佣金组：</strong>
	            <span>
	            	<select style="width:145px;" name="flowgroups" id="flowgroups">
	            		<%
	            			for(int i=0;i<Flow.size();i++)
	            			{
	            			String[] s1=(String[])Flow.get(i);
	            		 %>
	            			<option  value="<%=s1[0]%>" <%=s1[0].equals(var12)?"selected":""%>><%=s1[0]%></option>
	            		<%
	            			}
	            		 %>
	            	</select>
	            </span>
            </li>
            <li><strong>流量回调地址：</strong><span><input id="returnurl" name="returnurl" type="text"  value="<%=var13 %>"/></span></li>
            <li>
            	<strong>Q币状态：</strong>
             	<input type="radio" name="qbFlag" value="0" onchange="funs()" style="height:15px;width:25px;" <%=!"1".equals(var6)?"checked":"" %>/>单通道&nbsp;
             	<input type="radio" name="qbFlag" value="1" onchange="funs()" style="height:15px;width:25px;"  <%="1".equals(var6)?"checked":"" %>/>多通道
             </li>
             <li>
            	<strong>拆单状态：</strong>
            	<input type="radio" name="caidan" id="n" value="1" style="height:15px;width:25px;"  <%="1".equals(var9)?"checked":"" %>/>不拆单&nbsp;
             	<input type="radio" name="caidan" id="y" value="0" style="height:15px;width:25px;" <%=!"1".equals(var9)?"checked":"" %>/>拆单
             </li>
            <li>
            	<strong>接口商状态：</strong>
            	<input type="radio" name="state"  value="1" style="height:15px;width:25px;"  <%="1".equals(var4)?"checked":"" %>/>不可用&nbsp;
             	<input type="radio" name="state"  value="0" style="height:15px;width:25px;" <%=!"1".equals(var4)?"checked":"" %>/>可用
             </li>
        </ul>
   </div>
   <div class="field-item_button_m">
   <input type="button" value="确认" id="checkCode" onclick="check(<%=type %>)" class="field-item_button" />
   <input type="button" value="取消" id="checkCode" onClick="javascript:funHistory()" class="field-item_button" />
   </div>
</div>
</form>
</body>
</html>