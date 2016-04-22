<%@ page contentType="text/html; charset=GBK" language="java"%>
<%@ page import="java.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<jsp:useBean id="userSession" scope="session" class="com.wlt.webm.rights.form.SysUserForm" />
<jsp:useBean id="prod" scope="page" class="com.wlt.webm.business.bean.BiProd"/>
<jsp:useBean id="tpbean" scope="session" class="com.wlt.webm.business.bean.TpBean" />
 <%
	 List areaList = prod.getAreaListMoney();
	 
	 request.setAttribute("arrayList",areaList);
	 
	 List groupsList=tpbean.getGroups();//获取佣金组名称
	  StringBuffer strs = new StringBuffer();
	 strs.append("请选择[]");
	 for(Object tmp : groupsList){
		String[] temp = (String[])tmp;
		strs.append("|"+temp[0]+"["+temp[0]+"]");
	 }
	 request.setAttribute("groups", strs.toString());
 %>
 <html>
<head>
<title>万汇通</title>
<script type="text/javascript" src="../js/jquery-1.8.2.min.js"></script>
<script type="text/javascript" src="../js/xml_cascade.js"></script>
<link href="../css/main_style.css" rel="stylesheet" type="text/css" />
<link href="../css/selectCss.css" rel="stylesheet" type="text/css" /> 
<script src="../js/selectCss.js" type="text/javascript"></script> 
<SCRIPT type=text/javascript>
jQuery(function(){
	if($("#mess").val() != ""){
		alert($("#mess").val());
	}
});
function check(){
  	with(document.forms[0]){
  		if(cm_groups.value=="")
  		{
  			alert("请选择佣金组!");
  			return false;
  		}
  		if(cm_type.value=="")
  		{
	   	 alert("请选择运营商类型 !");
	     return false;
	    }
	    var bool=false;
	    var ids=document.getElementsByName("val");
	    for(var i=0;i<ids.length;i++)
	    {
	    	if(ids[i].value!="")
	    	{
	    		bool=true;
	    		break;
	    	}
	    }
	    if(bool)
	    {
	    	 if(confirm("确认如此分配?")){
			    document.getElementById("checkCode").value="请稍后...";
				document.getElementById("checkCode").disabled="disabled";
				target="_self";
				submit();
			}
			else
			{
				return false;
			}
	    }
	    else
	    {
	    	alert("请配置佣金!");
	    	return false;
	    }
	}
}

</SCRIPT>
</head>
<body>
<div id="popdiv"><span id="sellist"></span><div style="clear:both"></div></div>
<input type="hidden" id="mess" value="${requestScope.mess }"/>
<html:form  method="post" action="/business/prod.do?method=tpaddemployTwo">
<div class="pass_main">
	<div class="pass_title_1">添加佣金</div>
    <div class="pass_list">
    	<ul>
    		<li><strong>佣金组名称 ：</strong><span>
            <div class="sub-input"><a class="sia-2 selhover" id="sl-cm_groups" href="javascript:void(0);" style="width:200px;">请选择</a></div>
			<input style="width:200px;" name="cm_groups" id="cm_groups" defaultsel="${requestScope.groups }" type="hidden" value="" />
			 <li>
			 	<strong>业务类型：</strong>
			 	<span>
		            <div class="sub-input"><a class="sia-2 selhover" id="sl-cm_type" href="javascript:void(0);">请选择</a></div>
					<input name="cm_type" id="cm_type" defaultsel="请选择[]|移动[1]|联通[2]|电信[0]" type="hidden" value="" />
				</span>
			</li>
			<li style="padding-left:100px;">
				佣金配置：
			</li>
			<div style="float:left;width:350px;">
			<c:forEach items="${arrayList}"  var="arr" varStatus="index">
				<li>
				<input style="width:30px;" value="${index.index+1}" disabled />
				<input type="text" value="${arr[1]}" disabled style="width:100px;"/> 
				<input type="hidden" name="sheng" value="${arr[0]}"/> 
				&nbsp;
				<input type="text" value="${arr[3]}~${arr[4]}" disabled style="width:100px;"/>
				<input type="hidden" name="money" value="${arr[2]}"/> 
				&nbsp;
				<input type="text" name="val"  value=""  style="width:50px;" />
				</li>
				<c:if test="${(index.index+1)==132}">
					</div>
					<div style="float:left;width:350px;">
				</c:if>
			</c:forEach>
			</div>
        </ul>
   </div>
   <div class="field-item_button_m">
   <input type="button" value="确认" id="checkCode" onclick="check()" class="field-item_button" />
   <input type="button" value="取消" id="checkCode1" onClick="javascript:history.go(-1)" class="field-item_button" />
   </div>
</div>
</html:form>
</body>
</html>