<%@ page contentType="text/html; charset=gb2312" language="java"%>
<%
String path = request.getContextPath();
%>

<html>
<head>
<title>�˻�����</title>
<script src="../js/jquery-1.8.2.min.js" type="text/javascript"></script> 
<link href="../css/main_style.css" rel="stylesheet" type="text/css" />
<link href="../css/style.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
  			var i = 90;
        function msgShow() {
        document.getElementById("a").href="javascript:return false;";
            document.getElementById("msg").innerHTML ="�������ڷ��ٷ�����...<font color=red>"+i+"</font>���δ�յ�����,�����»�ȡ ";
            if (i > 0) {
                i--;
            }
            else {
                document.getElementById("a").href="javascript:sendMsg();";
                document.getElementById("msg").innerHTML ="(�����ȡ������֤��)";
            }
            setTimeout('msgShow()', 1000);
        }

function sendMsg(a){
    var a2=$("#newpwd").val();
    var a3=$("#newpwd1").val();
    if(a2==""||a3==""){
    alert("��������������");
   	 return ;
    }
    if(a2.length<8){
	    alert("�����벻�ܵ���8λ");
	    return ;
    }
    if(a2!=a3){
	    alert("�����������벻һ��");
	    return ;
    }
    document.getElementById("newpwd").readOnly=true;
    document.getElementById("newpwd1").readOnly=true;
	i=90;
	msgShow();
	$.post(
    	$("#rootPath").val()+"/business/bank.do?method=phsendMsg",
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
	    var b=document.getElementById("qr");
	    if(!b.checked){
	    alert("��δͬ�����Э��,��������");
	    return false;
    }
    
    var a2=$("#newpwd").val();
    var a3=$("#newpwd1").val();
    if(a2==""||a3==""){
    alert("��������������");
   	 return false;
    }
    if(a2.length<8){
	    alert("�����벻�ܵ���8λ");
	    return false;
    }
    if(a2!=a3){
	    alert("�����������벻һ��");
	    return false;
    }
    
    if(a.length!=6){
	    alert("��������ȷ�Ķ�����֤��");
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

<style type="text/css">
.inputText4 {
	font-family:Arial,Helvetica,sans-serif;
	background:none repeat scroll 0 0 #F5F7FD;
	border:1px solid #B8BFE9;
	padding:5px 7px;
	width:200px;
	vertical-align:middle;
	height:30px;
	font-size:12px;
	margin:0;
	list-style:none outside none;
</style>
</head>
<body>
<input type="hidden" id="rootPath" value="<%=path %>"/>
    <div class="mobile-charge">
<form action="<%=path %>/rights/sysuser.do?method=phoneValidata" id="form1" method="post" class="form">
        <fieldset>
          <div class="form-line">
<br>
<br>
<br>
            <label class="label" for="mobile-num"><font size="3" color="red">��һ�ε�½�����޸����ĵ�¼����,���ж�����֤,���������˻���</font></label>
<hr>         
<br>  
 <div style="margin-left: 200;">
		<!--  		<b style="font-size: 16px;">ԭʼ��¼���룺</b><input class="inputText4" type="text" name="oldpwd" id="oldpwd" /><font color="red" size=2>&nbsp;&nbsp;*</font>-->
<br>
<br>
				<b style="font-size: 16px;">�µĵ�¼���룺</b><input class="inputText4" type="password" name="newpwd" id="newpwd" /><font color="red" size=2>&nbsp;&nbsp;*���Ȳ��ܵ���8λ</font>
<br>
<br>
				<b style="font-size: 16px;">&nbsp;&nbsp;&nbsp;ȷ�������룺</b><input class="inputText4" type="password" name="newpwd1" id="newpwd1" /><font color="red" size=2>&nbsp;&nbsp;*</font>
<br>
<br>
				<b style="font-size: 16px;">&nbsp;&nbsp;&nbsp;�ֻ���֤�룺</b><input class="inputText4" type="text" name="msgcode" id="msgcode" /><a id="a" href="javascript:sendMsg();"><span id="msg">(�����ȡ������֤��)</span></a>
<br>
<br>
<span style="font-size: 15px;">���ն��ŵ��ֻ�����Ϊ:${requestScope.phonenum}</span>   
<br>  
<br> 
<input type="checkbox" checked="checked" id="qr"/><span style="font-size: 15px;">�����Ķ���ͬ����ط������� <a href="../rights/xieyi.html" target="view_window">����Э��</a></span>    
</div>
          </div>
<div class="field-item_button_m" id="btnChag" style="margin-left: 150;">
<input type="button" name="Button" value="ȷ��" class="field-item_button" onclick="btnSubmit();">
<input type="button" name="Button" value="����" class="field-item_button" onclick="history.go(-1)"></div>
<br>
        </fieldset>
      </form>
    </div>
</body>
<input type="hidden" id="mess" value="${requestScope.mess }"/>
</html>
