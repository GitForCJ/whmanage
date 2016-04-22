<%@ page contentType="text/html; charset=GBK" language="java"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<jsp:useBean id="userSession" scope="session" class="com.wlt.webm.rights.form.SysUserForm" />
<jsp:useBean id="rebate" scope="page" class="com.wlt.webm.business.bean.SysRebate"/>
<jsp:useBean id="role" scope="page" class="com.wlt.webm.rights.bean.SysRole"/>
 <%
 	 String path = request.getContextPath();
	 String srid=request.getParameter("srid");
   	 request.setAttribute("rebateinfo", rebate.getRebateInfo(srid));
   	 String userrole=userSession.getUser_role();
  	 request.setAttribute("rolelist", role.getRole(userrole));
 %>
 <html>
<head>
<title>万汇通</title>
<script type="text/javascript" src="../js/jquery-1.8.2.min.js"></script>
<script type="text/javascript" src="../js/xml_cascade.js"></script>
<link href="../css/main_style.css" rel="stylesheet" type="text/css" />
<SCRIPT src="../js/more_tiaojian.js" type=text/javascript></SCRIPT>
<script language='javascript' src='../js/jquery.js'></script>
<script language='javascript' src='../js/select.js'></script>
<script language="javascript" type="text/javascript" src="../My97DatePicker/WdatePicker.js"></script>
<SCRIPT type=text/javascript>
jQuery(function(){
	$("#changeCity").click(function(a){$("#allCity").slideDown(0);a.stopPropagation();$(this).blur()});$("#allCity").click(function(a){a.stopPropagation()});$(document).click(function(a){a.button!=2&&$("#allCity").slideUp(0)});$("#foldin").click(function(){$("#allCity").slideUp(0)})
});
function submitForm(){
  	with(document.forms[0]){
		submit();
	}
}
</SCRIPT>
</head>
<body>
<html:form  method="post" action="/business/sysrebate.do?method=update">
<input type="hidden" name="srid" value="${rebateinfo.srid }"/>
<div class="pass_main">
	<div class="pass_title_1">折扣率信息</div>
    <div class="pass_list">
    	<ul>
            <li><strong>角色 ：</strong><span>
			<html:select property="roleid" value="${rebateinfo.roleid }">
              <option value="">请选择</option>
              <logic:iterate id="roleinfo" name="rolelist">
              <html:option value="${roleinfo[0]}">${roleinfo[1]}</html:option>
              </logic:iterate>
              </html:select>
			</span></li>
            <li><strong>交易类型：</strong><span>
			<select name="sc_tradertype">
				<option value="1" <c:if test="${rebateinfo.sc_tradertype == 1 }">selected</c:if>>Q币</option>
				<option value="2" <c:if test="${rebateinfo.sc_tradertype == 2 }">selected</c:if>>游戏币</option>
				<option value="3" <c:if test="${rebateinfo.sc_tradertype == 3 }">selected</c:if>>交通罚款</option>
			</select>
			</span></li>
            <li><strong>折扣率：</strong><span><input name="sc_traderpercent" type="text" value="${rebateinfo.sc_traderpercent }"/></span></li>
        </ul>
   </div>
   <div class="field-item_button_m">
   <input type="button" value="保存" id="btnSubmit" onclick="submitForm();" class="field-item_button" />
   <input type="button" value="返回" id="checkCode" onclick="location.href='<%=path %>/business/wltrebatelist.jsp'" class="field-item_button" />
   </div>
</div>
</html:form>
</body>
</html>