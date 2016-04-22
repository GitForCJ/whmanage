<%@ page contentType="text/html; charset=GBK" language="java"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<jsp:useBean id="userSession" scope="session" class="com.wlt.webm.rights.form.SysUserForm" />
<jsp:useBean id="prod" scope="page" class="com.wlt.webm.business.bean.BiProd"/>
 <%
 	 String path = request.getContextPath();
	 String prid=request.getParameter("prid");
   	 request.setAttribute("prodinfo", prod.getProdInfo(prid));
   	 request.setAttribute("arealist", prod.getAreaList());
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
<html:form  method="post" action="/business/prod.do?method=update">
<input type="hidden" name="cm_id" value="<%=prid %>"/>
<div class="pass_main">
	<div class="pass_title_1">折扣率信息</div>
    <div class="pass_list">
    	<ul>
            <li><strong>产品编号：</strong><span><input name="cm_prod" type="text" value="${prodinfo.cm_prod }"/></span></li>
            <li><strong>产品面额：</strong><span><input name="cm_fee" type="text" value="${prodinfo.cm_fee }"/></span></li>
            <li><strong>区域 ：</strong><span>
			<html:select property="cm_area" value="${prodinfo.cm_area }">
              <option value="">请选择</option>
              <logic:iterate id="areainfo" name="arealist">
              <html:option value="${areainfo[0]}">${areainfo[1]}</html:option>
              </logic:iterate>
              </html:select>
			</span></li>
            <li><strong>业务类型：</strong><span>
			<select name="cm_type">
				<option value="M" <c:if test="${prodinfo.cm_type == 'M' }">selected</c:if>>移动</option>
				<option value="T" <c:if test="${prodinfo.cm_type == 'T' }">selected</c:if>>电信</option>
				<option value="U" <c:if test="${prodinfo.cm_type == 'U' }">selected</c:if>>联通</option>
			</select>
			</span></li>
        </ul>
   </div>
   <div class="field-item_button_m">
   <input type="button" value="保存" id="btnSubmit" onclick="submitForm();" class="field-item_button" />
   <input type="button" value="返回" id="checkCode" onclick="location.href='<%=path %>/business/wltprodlist.jsp'" class="field-item_button" />
   </div>
</div>
</html:form>
</body>
</html>