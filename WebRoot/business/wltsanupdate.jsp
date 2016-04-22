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
   	 request.setAttribute("saninfo", prod.getSanInfo(prid));
   	 request.setAttribute("arealist", prod.getFaceList());
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
<html:form  method="post" action="/business/prod.do?method=sanupdate">
<input type="hidden" name="cm_id" value="<%=prid %>"/>
<div class="pass_main">
	<div class="pass_title_1">折扣率信息</div>
    <div class="pass_list">
    	<ul>
            <li><strong>产品面额：</strong><span>
			<select name="cm_fee">
				<option value="30" <c:if test="${saninfo.cm_fee == '30' }">selected</c:if>>30</option>
				<option value="50" <c:if test="${saninfo.cm_fee == '50' }">selected</c:if>>50</option>
				<option value="100" <c:if test="${saninfo.cm_fee == '100' }">selected</c:if>>100</option>
				<option value="200" <c:if test="${saninfo.cm_fee == '200' }">selected</c:if>>200</option>
				<option value="300" <c:if test="${saninfo.cm_fee == '300' }">selected</c:if>>300</option>
				<option value="500" <c:if test="${saninfo.cm_fee == '500' }">selected</c:if>>500</option>
			</select>
			</span></li>
            <li><strong>接口名称 ：</strong><span>
			<html:select property="cm_face" value="${prodinfo.cm_face }">
              <option value="">请选择</option>
              <logic:iterate id="areainfo" name="arealist">
              <html:option value="${areainfo[0]}">${areainfo[1]}</html:option>
              </logic:iterate>
              </html:select>
			</span></li>
            <li><strong>业务类型：</strong><span>
			<select name="cm_type">
				<option value="0" <c:if test="${saninfo.cm_type == '0' }">selected</c:if>>移动</option>
				<option value="2" <c:if test="${saninfo.cm_type == '2' }">selected</c:if>>电信</option>
				<option value="1" <c:if test="${saninfo.cm_type == '1' }">selected</c:if>>联通</option>
			</select>
			</span></li>
        </ul>
   </div>
   <div class="field-item_button_m">
   <input type="button" value="保存" id="btnSubmit" onclick="submitForm();" class="field-item_button" />
   <input type="button" value="返回" id="checkCode" onclick="location.href='<%=path %>/business/wltsanlist.jsp'" class="field-item_button" />
   </div>
</div>
</html:form>
</body>
</html>