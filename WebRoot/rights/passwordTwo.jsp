<%@ page language="java" import="java.util.*" pageEncoding="gbk"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String conString = request.getHeader("REFERER");
//System.out.println("passwordTwo:"+conString);
if(conString==null || "".equals(conString))
{
  response.setHeader("Cache-Control","no-cache");
  String jumpto="<script language='JavaScript'>"+
				"  top.window.location='"+path+"/index.jsp';" +
                "</script>";
  out.print(jumpto);
  return;
}
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <title>���ͨ��ȫ����</title>
<style type="text/css">
	a:hover {text-decoration:underline;color:red;}
</style>
<script src="<%=path%>/js/jquery-1.8.2.min.js" type="text/javascript"></script> 
<script type="text/javascript">
function showMessage(str)
{
   if(str.length!=0){
   		alert(str);
   }
}
showMessage("${mess}");
String.prototype.Trim = function() 
{ 
	return this.replace(/(^\s*)|(\s*$)/g, ""); 
} 
var time;
function funyanzhengma(className)
{
	className.disabled=true;
	$.post(
    	"<%=path%>/AccountInfo/showAccountInfo.do?method=sendMsg",
      	{
      		phone:"${usertel}"
      	},
      	function (data){
      		if(data == 0){
      			document.getElementById("stats").innerHTML="���ŷ��ͳɹ�!";
      		}else{
      			document.getElementById("stats").innerHTML="���ŷ���ʧ��!";
      		}
      	}
	)
	fun(60)
}
function fun(aa)
{
	if(parseInt(aa)<=0)
	{
		clearTimeout(time);
		document.getElementById("btn").value="��ȡ��֤��";
		document.getElementById("btn").disabled=false;
		return ;
	}
	document.getElementById("btn").value=aa+"��";
	aa--;
	time=setTimeout("fun("+aa+")", 1000);
}

//�ύ
function funsubmit()
{
	var catName=document.getElementById("catName").value;
	var catId=document.getElementById("catId").value;
	var oldmiyi=document.getElementById("oldmiyi").value;
	var newmiyi=document.getElementById("newmiyi").value;
	var oldmier=document.getElementById("oldmier").value;
	var newmier=document.getElementById("newmier").value;
	var yanzhengma=document.getElementById("yanzhengma").value;
	if(catName.Trim().length<=0)
	{
		alert("�������û�����!");
		return ;
	}
	if(catId.Trim().length<=0)
	{
		alert("���������֤����!");
		return ;
	}
	if(oldmiyi.Trim().length<=6)
	{
		alert("��������λ���ϵĵ�¼����!");
		return ;
	}
	
	if(oldmiyi.Trim()!=newmiyi.Trim())
	{
		alert("��¼���벻һ��!");
		return ;
	}
	if(oldmier.Trim().length<=6)
	{
		alert("��������λ���ϵĽ�������!");
		return ;
	}
	if(oldmier.Trim()!=newmier.Trim())
	{
		alert("�������벻һ��!");
		return ;
	}
	if(yanzhengma.Trim().length<=0)
	{
		alert("��������֤��!");
		return ;
	}
	if(window.confirm("��ȷ������������"))
	{
		document.forms[0].submit();
		return;
	}
	else
	{
		return ;
	}
}


window.onload=function()
{
	if("${type}"=="1")
	{
		document.getElementById("lingxing1").style.backgroundImage="url(<%=path %>/rights/images/lingxing2.jpg)";
		document.getElementById("lingxing2").style.backgroundImage="url(<%=path %>/rights/images/lingxing1.jpg)";
		
		document.getElementById("lingxingA").style.color="#cccccc";
		document.getElementById("lingxingB").style.color="red";
		
		document.getElementById("lingxingC").style.display="none";
		document.getElementById("lingxingD").style.display="block";
		
		funMiao(5);
	}	
}
var t;
function funMiao(miaoA)
{
	if(parseInt(miaoA)<=0)
	{
		clearTimeout(t);
		window.location.href="<%=path%>";
		return ;
	}
	document.getElementById("miaoA").innerHTML=miaoA;
	miaoA--;
	t=setTimeout("funMiao("+miaoA+")", 1000);
}
</script>
</head>
  
  <body style="padding:0px 0px 0px 0px;margin:0px 0px 0px 0px;" >
  <form action="<%=path%>/AccountInfo/showAccountInfo.do?method=submitMIMA" method="post">
    <div style="width:100%;height:70px;margin-top:20px;line-height:90px;padding-left:200px;">
    	<label style="font-size:35px;color:red;font-weight:bold;">���ͨ</label>&nbsp;<label style="font-size:25px;font-weight:bold">��ȫ����</label>
    </div>
    <hr>
    <div style="width:100%;height:30px;line-height:30px;padding-left:200px;margin-top:10px;">
    	<label style="font-size:20px;font-weight:bold">�һ�����</label>
    </div>
    <div style="width:100%;height:80px;padding-left:220px">
    <table>
    <tr>
    	<td>
    	<div style="float:left;line-height:43px;text-align:center;font-size:20px;color:white;font-weight:bold;width:45px;height:43px;margin-top:18px;background-image:url(<%=path %>/rights/images/lingxing2.jpg)">
    		1
    	</div>
    	</td>
    	<td>
    	<hr style="border:2px solid #cccccc;width:250px;float:left;margin-top:39px;" >
    	</td>
    	<td>
    	<div style="float:left;line-height:43px;text-align:center;font-size:20px;color:white;font-weight:bold;width:45px;height:43px;margin-top:18px;background-image:url(<%=path %>/rights/images/lingxing1.jpg)" id="lingxing1">
    		2
    	</div>
    	</td>
    	<td>
    	<hr style="border:2px solid #cccccc;width:250px;float:left;margin-top:39px;">
    	</td>
    	<td>
    	<div style="float:left;line-height:43px;text-align:center;font-size:20px;color:white;font-weight:bold;width:45px;height:43px;margin-top:18px;background-image:url(<%=path %>/rights/images/lingxing2.jpg)" id="lingxing2">
    		3
    	</div>
    	</td>
    	</tr>
    </table>
    </div>
    <div style="width:100%;height:20px;margin-top:-13px;padding-left:205px;">
    	<label style="color:#cccccc;font-weight:bold;margin-right:220px;">�����˻���</label>
    	<label style="color:red;font-weight:bold;margin-right:240px;" id="lingxingA">��������</label>
    	<label style="color:#cccccc;font-weight:bold"  id="lingxingB">���</label>
    </div>
    <div style="width:100%;margin-top:20px;padding-left:300px;" id="lingxingC" >
    	<table border=0 cellspacing=10 >
    	<tr>
    	<td style="text-align:right;width:150px;">
    	<label style="color:#cccccc;font-weight:bold;width:150px;" >�û�����:</label>
    	</td>
    	<td style="width:500px;">
    	<input type="text" value="" id="catName" name="catName" style="width:180px;height:30px;"/><font color="red">*ע��ʱ������û�����</font>
    	</td>
    	</tr>
    	
    	<tr>
    	<td style="text-align:right;width:150px;">
    	<label style="color:#cccccc;font-weight:bold;width:150px;" >���֤����:</label>
    	</td>
    	<td style="width:500px;">
    	<input type="text" value="" id="catId" name="cat" style="width:180px;height:30px;"/>
<font color="red">*ע��ʱ��������֤��</font>    	
</td>
    	</tr>
    	
    	<tr>
    	<td style="text-align:right;width:150px;">
    	<label style="color:#cccccc;font-weight:bold;width:150px;">�µ�¼����:</label>
</td>
    	<td style="width:500px;">
    	<input type="password" value="" name="dlmima" id="oldmiyi" style="width:180px;height:30px;"/>
<font color="red">*</font>    	
</td>
    	</tr>
		
		<tr>
		<td style="text-align:right;width:150px;">
    	<label style="color:#cccccc;font-weight:bold;width:150px;">ȷ�ϵ�¼����:</label>
    	</td>
    	<td style="width:500px;">
    	<input type="password" value="" id="newmiyi" style="width:180px;height:30px;"/>
<font color="red">*</font>    	
</td>
    	</tr>
		
		<tr>
		<td style="text-align:right;width:150px;">
    	<label style="color:#cccccc;font-weight:bold;width:150px;">�½�������:</label>
    	</td>
    	<td style="width:500px;">
    	<input type="password" value="" name="jymima" id="oldmier" style="width:180px;height:30px;"/>
<font color="red">*</font>    	
</td>
    	</tr>

		<tr>
		<td style="text-align:right;width:150px;">
    	<label style="color:#cccccc;font-weight:bold;width:150px;">ȷ�Ͻ�������:</label>
    	</td>
    	<td  style="width:500px;">
    	<input type="password" value="" id="newmier" style="width:180px;height:30px;"/>
<font color="red">*</font>    	
</td>
    	</tr>

		<tr>
		<td style="text-align:right;width:150px;">
    	<label style="color:#cccccc;font-weight:bold;">���ֻ���:</label>
    	</td>
    	<td style="width:500px;">
    	<label style="font-weight:bold" >${usertel}</label>
    	<input onclick="funyanzhengma(this)" type="button" id="btn" style="width:85px; height:32px; border:0px;color:#fff; font-weight:bold;background:url(<%=path%>/images/button_out.png) repeat-x bottom; cursor:pointer;" value="��ȡ��֤��"/>
    	</td>
    	</tr>
    	
    	<tr>
    	<td style="text-align:right;width:150px;">
    	<label style="color:#cccccc;font-weight:bold;width:150px;">������֤��:</label>
    	</td>
    	<td style="width:500px;">
    	<input type="text" value="" name="yanzhengma" id="yanzhengma" style="width:100px;height:30px;"/>
    	<label style="color:red;font-weight:bold;font-size:13px;" id="stats"></label>
    	</td>
    	</tr>
    	
    	<tr>
    	<td style="text-align:right;width:150px;">
    	<input onclick="funsubmit()" type="button"  style="width:85px; height:32px; border:0px;color:#fff; font-weight:bold;background:url(<%=path%>/images/button_out.png) no-repeat bottom; cursor:pointer;" value="�ύ"/>
    	</td>
    	<td style="width:500px;padding-left:10px;">
    	<input onclick="javascript:window.location.href='<%=path %>/rights/password.jsp'" type="button" style="width:85px; height:32px; border:0px;color:#fff; font-weight:bold;background:url(<%=path%>/images/button_out.png) no-repeat bottom; cursor:pointer;" value="����"/>
    	</td>
    	</tr>
    	</table>
    </div>
    <div style="width:100%;margin-top:20px;padding-left:300px;height:200px;line-height:200px;font-size:30px;font-weight:bold;display:none;" id="lingxingD" >
    �����޸ĳɹ�,<span id="miaoA"></span>���ص���ҳ!
    </div>
    <input type="hidden" value="${userName }" name="accountPhone"/>
    </form>
  </body>
</html>
