   <%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title></title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
   <meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
    <script type="text/javascript" charset="utf-8" src="ueditor.config.js"></script>
    <script type="text/javascript" charset="utf-8" src="ueditor.all.min.js"> </script>
    <script type="text/javascript" charset="utf-8" src="lang/zh-cn/zh-cn.js"></script>

    <style type="text/css">
        .clear {
            clear: both;
        }
    </style>
<script type="text/javascript">
UE.getEditor('myeditor');

function sub(){
alert(UE.getEditor('myeditor').getContent());
with(document.forms[0]){
        rich.value=UE.getEditor('myeditor').getContent();
        alert(rich.value);
	  	target="_self";
	  	submit();
	  	}
}
</script>
  </head>
  
  <body>
<form id="form1" action="jsp/getContent.jsp" method="post">
<div>
<script id="myeditor" type="text/plain" style="width:100%;height:300px;"></script>
</div>
<input type="button" value="测试" onclick="sub()"/>
<input type="hidden" name="rich" value="" id="rich"/>
</form>

</body>
</html>
