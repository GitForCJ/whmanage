<%@ page contentType="text/html; charset=gb2312" language="java"%>
<%
String path = request.getContextPath();
%>

<html>
<head>
<title>��½�ֻ�������֤</title>
<script src="../js/jquery-1.8.2.min.js" type="text/javascript"></script> 
<link href="../css/main_style.css" rel="stylesheet" type="text/css" />
<link href="../css/style.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
  			var i = 90;
        function msgShow() {
            document.getElementById("msg").innerHTML ="<font color=red>"+i+"</font>���δ�յ�����,�����»�ȡ ";
            if (i > 0) {
                i--;
            }
            else {
                document.getElementById("a").href="javascript:sendMsg();";
                document.getElementById("msg").innerHTML ="(�����ȡ������֤��)";
            }
            setTimeout('msgShow()', 1000);
        }

function sendMsg(){
i=90;
msgShow();
	$.post(
    	$("#rootPath").val()+"/business/bank.do?method=sendMsg",
      	{
      	},
      	function (data){
      		if(data == 0){
      		   document.getElementById("a").href="#";
      			alert("���ŷ��ͳɹ�");
      		}else{
      			alert("���ŷ���ʧ��");
      		}
      	}
	)
}
function btnSubmit(){
    var a=$("#msgcode").val();
    if(a.length!=6){
    alert("��������ȷ����֤��");
    return false;
    }
	$("#form1").submit();
}
$(function(){
	if($("#mess").val() != ""){
		alert($("#mess").val());
	}
})
</script>
</head>
<body>
<input type="hidden" id="rootPath" value="<%=path %>"/>
    <div class="mobile-charge">
<form action="<%=path %>/rights/sysuser.do?method=dengluValidata" id="form1" method="post" class="form">
        <fieldset>
          <div class="form-line">
<br>
<br>
<br>
            <label class="label" for="mobile-num"><font size="3" color="red">�û���½�����֤��</font></label>
<hr>         
<br>  
 <div style="margin-left: 200;">
				<b>��֤�룺</b><input height="20px" type="text" name="msgcode" id="msgcode"> <!--<a id="a" href="javascript:sendMsg();"><span id="msg">(�����ȡ������֤��)</span></a>-->
<br>
<br>
��ȡ������֤���ֻ�����Ϊ:<span>${requestScope.phonenum}</span>   
<br>  
<br> 
</div>
          </div>
<div class="field-item_button_m" id="btnChag" style="margin-left: 200;">
<input type="button" name="Button" value="ȷ��" class="field-item_button" onclick="btnSubmit();">
<input type="button" name="Button" value="����" class="field-item_button" onclick="history.go(-1)"></div>
<br>
        </fieldset>
      </form>
    </div>
</body>
<input type="hidden" id="mess" value="${requestScope.mess }"/>
</html>
