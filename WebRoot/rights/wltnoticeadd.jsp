<%@ page contentType="text/html; charset=gb2312" language="java"%>
<%@page import="java.util.List"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<jsp:useBean id="userSession" scope="session"
	class="com.wlt.webm.rights.form.SysUserForm" />
<jsp:useBean id="role" scope="page"
	class="com.wlt.webm.rights.bean.SysRole" />
<jsp:useBean id="notice" scope="page"
	class="com.wlt.webm.rights.bean.SysNotice" />
<%
	String path = request.getContextPath();
	List areaList = role.getAreaList();
	StringBuffer sBuffer = new StringBuffer();
	sBuffer.append("����[0]");
	for (Object tmp : areaList) {
		String[] temp = (String[]) tmp;
		sBuffer.append("|" + temp[1] + "[" + temp[0] + "]");
	}

	request.setAttribute("areaSel", sBuffer.toString());
%>
<html>
	<head>
		<title>���ͨ</title>
		<META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=gb2312">
		<script type="text/javascript" src="../js/jquery-1.8.2.min.js"></script>
		<link href="../css/main_style.css" rel="stylesheet" type="text/css" />
		<link href="../css/selectCss.css" rel="stylesheet" type="text/css" />
		<script src="../js/selectCss.js" type="text/javascript"></script>
		<script language="javascript" type="text/javascript"
			src="../My97DatePicker/WdatePicker.js"></script>
		<SCRIPT type=text/javascript>
jQuery(function(){
	$("#changeCity").click(function(a){$("#allCity").slideDown(0);a.stopPropagation();$(this).blur()});$("#allCity").click(function(a){a.stopPropagation()});$(document).click(function(a){a.button!=2&&$("#allCity").slideUp(0)});$("#foldin").click(function(){$("#allCity").slideUp(0)})
});
function check(){
  	with(document.forms[0]){
		/* areacopeΪ�������ţ�Ҳ����ϵ��α��еĽڵ����� */
		if(an_title.value == ""){
		alert("�����빫�����!");
		return;
		}
		if(an_faceid.value == ""){
			alert("��ѡ���������");
			return;
		}
		if(contentType.value=="")
		{
			alert("��ѡ�񹫸�����");
			return;
		}
		if(an_activedate.value == ""){
			alert("��ѡ����Чʱ��");
			return;
		}
		if(an_deaddate.value == ""){
			alert("��ѡ���ֹʱ��");
			return;
		}
		if(UE.getEditor('myeditor').getContent()==""){
			alert("�����빫������!");
			return ;
		}
		an_content.value=UE.getEditor('myeditor').getContent();
		submit();
		return ;
	}
}
</SCRIPT>
	</head>
	<body>
		<div id="popdiv">
			<span id="sellist"></span>
			<div style="clear: both"></div>
		</div>
		<input type="hidden" id="rootPath" value="<%=path%>" />
		<form method="post" action="<%=path%>/rights/notice.do?method=add">
			<input type="hidden" name="an_content" id="an_content" value="" />
			<div class="pass_main">
				<div class="pass_title_1">
					��ӹ���
				</div>
				<div class="pass_list">
					<ul>
						<li>
							<strong>������⣺</strong><span><input name="an_title"
									type="text" /> </span>
						</li>
					</ul>
					<ul>
						<li>
							<tr>
								<strong>������У�</strong>
								<span>
									<td>
										<div class="sub-input">
											<a class="sia-2 selhover" id="sl-wltarea_role"
												href="javascript:void(0);">����</a>
										</div>
										<input name="an_faceid" id="wltarea_role"
											defaultsel="${requestScope.areaSel }" type="hidden" value="0" />
									</td> </span>
							</tr>
						</li>
					</ul>

					<ul>
						<li>
							<tr>
								<strong>�������ͣ�</strong>
								<span>
									<td>
										<div class="sub-input">
											<a class="sia-2 selhover" id="sl-cm_type"
												href="javascript:void(0);">��ѡ��</a>
										</div>
										<input name="contentType" id="cm_type"
											defaultsel="��ѡ��[]|���¹���[1]|���»[2]" type="hidden" value="" />
									</td> </span>
							</tr>
						</li>
					</ul>

					<ul>
						<li>
							<strong>������Чʱ�䣺</strong><span><input name="an_activedate"
									type="text" class="Wdate" onClick="WdatePicker()" /> </span>
						</li>
					</ul>
					<ul>
						<li>
							<strong>�����ֹʱ�䣺</strong><span><input name="an_deaddate"
									type="text" class="Wdate" onClick="WdatePicker()" /> </span>
						</li>
					</ul>
					<ul>
						<!--  <li><strong>�������ݣ�</strong><span><textarea rows="15" cols="60" name="an_content" type="text" ></textarea></span></li>  -->
						<li  style="width:100%;height:400px;overflow:scroll;overflow-x:hidden">
							<script id="myeditor" type="text/plain"
								style="width:100%;height:400px;"></script>
						</li>
					</ul>
				</div>
				<div class="field-item_button_m">
					<input type="button" value="ȷ��" id="checkCode" onclick="check()"
						class="field-item_button" />
					<input type="button" value="ȡ��" id="checkCode"
						onClick="javascript:history.go(-1)" class="field-item_button" />
				</div>
			</div>
		</form>
	</body>
	<script type="text/javascript" charset="utf-8"
		src="<%=path%>/richtext/ueditor.config.js"></script>
	<script type="text/javascript" charset="utf-8"
		src="<%=path%>/richtext/ueditor.all.min.js"> </script>
	<script type="text/javascript" charset="utf-8"
		src="<%=path%>/richtext/lang/zh-cn/zh-cn.js"></script>
	<script type="text/javascript">
UE.getEditor('myeditor');
</script>
</html>