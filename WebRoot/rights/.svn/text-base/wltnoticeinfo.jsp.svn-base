<%@ page contentType="text/html; charset=UTF-8" language="java"%>
<%@page import="java.util.List"%>
<%@page import="com.wlt.webm.rights.form.SysNoticeForm"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<jsp:useBean id="userSession" scope="session"
	class="com.wlt.webm.rights.form.SysUserForm" />
<jsp:useBean id="role" scope="page"
	class="com.wlt.webm.rights.bean.SysRole" />
<jsp:useBean id="sysnotice" scope="page"
	class="com.wlt.webm.rights.bean.SysNotice" />
<%
	String path = request.getContextPath();
	String id = request.getParameter("uid");

	String type = request.getParameter("type");
	SysNoticeForm snForm = sysnotice.getNoticeInfo(id);
	if (snForm != null && !"".equals(snForm.getAn_faceid())) {
		if ("0".equals(snForm.getAn_faceid())) {
			snForm.setAn_faceid("所有");
		} else {
			List areaList = role.getAreaList();
			for (Object tmp : areaList) {
				String[] temp = (String[]) tmp;
				if (temp[0].equals(snForm.getAn_faceid())) {
					snForm.setAn_faceid(temp[1]);
				}
			}
		}
		if ("0".equals(snForm.getIf_active())) {
			snForm.setIf_active("无效");
		} else if ("1".equals(snForm.getIf_active())) {
			snForm.setIf_active("有效");
		}
	}

	request.setAttribute("notice", snForm);
%>
<html>
	<head>
		<title>万汇通</title>
		<script type="text/javascript" src="../js/jquery-1.8.2.min.js"></script>
		<link href="../css/main_style.css" rel="stylesheet" type="text/css" />
		<style type="text/css">
a:hover {
	color: #ff4400;
	text-decoration: underline
}

textarea {
	border: 0 none white;
	overflow: hidden;
	padding: 0;
	outline: none;
	width: 100%;
	resize: none;
}
</style>
		<script type="text/javascript">
var observe;
if (window.attachEvent) {
    observe = function (element, event, handler) {
        element.attachEvent('on'+event, handler);
    };
}
else {
    observe = function (element, event, handler) {
        element.addEventListener(event, handler, false);
    };
}
function init () {
    var text = document.getElementById('text');
    function resize () {
        text.style.height = 'auto';
        text.style.height = text.scrollHeight+'px';
        document.getElementById("body").style.height=(text.scrollHeight+300)+"px";
        document.getElementById("dd").style.height=document.getElementById("dd").style.height+(text.scrollHeight+200)+"px";
        if((text.scrollHeight+200)<500)
        {
        	 document.getElementById("dd").style.height="500px";
        }
    }
    /* 0-timeout to get the already changed text */
    function delayedResize () {
        window.setTimeout(resize, 0);
    }
    observe(text, 'change',  resize);
    observe(text, 'cut',     delayedResize);
    observe(text, 'paste',   delayedResize);
    observe(text, 'drop',    delayedResize);
    observe(text, 'keydown', delayedResize);
    resize();
}
</script>
	</head>
	<body onload="init();" id="body">
		<div id="popdiv">
			<span id="sellist"></span>
			<div style="clear: both"></div>
		</div>
		<input type="hidden" id="rootPath" value="<%=path%>" />
		<input type="hidden" name="an_id" value="<%=id%>" />
		<div class="pass_main">
			<%
				if (!"update".equals(type)) {
			%>
			<DIV class="pass_title_1"
				style="background-color: #f8f8f8; position: relative;">
				万汇通首页>>公告>>公告正文
				<a id="B_"
					style="position: absolute; right: 10px; bottom: 7px; font-size: 13px; width: 50px;"
					href="javascript:history.go(-1)">返回>></a>
			</DIV>
			<%
				}
			%>
			<div class="pass_list"
				style="padding-left: 30px; padding-right: 30px;height:120px;" id="dd">
				<ul style="margin-left: 0px;">
					<li style="width: 100%; margin-left: 0px; margin-right: 0px;">
						&nbsp;
					</li>
				</ul>
				<ul>
					<li
						style="font-size: 25px; text-align: center; width: 100%; margin-left: 0px; margin-right: 0px;">
						${requestScope.notice.an_title }
					</li>
				</ul>
				<ul>
					<li
						style="font-size: 15px; text-align: center; width: 100%; margin-left: 0px; margin-right: 0px;">
						【 发布日期： ${requestScope.notice.an_activedate } 】
					</li>
				</ul>

				<ul>
					<li>
							<script id="myeditor" type="text/plain"
								style="width:100%;height:100%;"></script>
						</li>
				</ul>
			</div>
		</div>
	</body>
	<script type="text/javascript" charset="utf-8"
		src="<%=path%>/richtext/ueditor.config.js"></script>
	<script type="text/javascript" charset="utf-8"
		src="<%=path%>/richtext/ueditor.all.min.js"> </script>
	<script type="text/javascript" charset="utf-8"
		src="<%=path%>/richtext/lang/zh-cn/zh-cn.js"></script>
	<script type="text/javascript">
		var editor = UE.getEditor('myeditor', {
		    toolbars: [
		        ['wordCountMsg']
		    ],
		    autoHeightEnabled: true,
		    autoFloatEnabled: true
		});
		editor.addListener("ready", function () {
	        // editor准备好之后才可以使用
	        editor.setContent('${requestScope.notice.an_content}');
	        editor.setDisabled();
		});
	</script>
</html>