<%@ page contentType="text/html; charset=gb2312" language="java"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:useBean id="Biprod" scope="page"
	class="com.wlt.webm.business.bean.BiProd" />
<jsp:useBean id="userSession" scope="session"
	class="com.wlt.webm.rights.form.SysUserForm" />
<%
	String path = request.getContextPath();
	String[] strs = Biprod.getPlaceMoney("1", userSession.getUserno());
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>Q�ҳ�ֵ</title>
		<link href="../css/chongzhi_mobile.css" rel="stylesheet"
			type="text/css" />
		<script src="../js/jquery-1.8.2.min.js" type="text/javascript"></script>
		<script type="text/javascript" src="../js/util.js"></script>
		<script language='javascript' src='../js/jquery.js'></script>
		<link href="../css/main_style.css" rel="stylesheet" type="text/css" />
		<link href="../css/style.css" rel="stylesheet" type="text/css" />
		<style type="text/css">
			.black_overlay{ 
			    display: none; 
			    position: absolute; 
			    top: 0%; 
			    left: 0%; 
			    width: 100%; 
			    height: 100%; 
			    background-color: black; 
			    z-index:1001; 
			    -moz-opacity: 0.8; 
			    opacity:.80; 
			    filter: alpha(opacity=88); 
			} 
			.white_content { 
			    display: none; 
			    z-index:1002; 
			    overflow: auto; 
			} 
		</style>
		<script language="JavaScript">
	$(function(){
		showMessage("${mess}");	
	});
	function check(){  
		var in_acct=document.getElementById("in_acct");
		var in_two=document.getElementById("in_two");
		var qb_InNum=document.getElementById("qb_InNum");
		
		if(in_acct.value.length == 0) 
		{
			alert("���ѣ�����д��ȷ��QQ����!");
			directFrm.in_acct.focus();
			return ;
		}
		if(in_two.value!=in_acct.value ) {
			alert("���ѣ�����QQ���벻һ��!");
			in_acct.focus();
			return ;
		}
		var con=0;
		if(qb_InNum.value==""){
			var temp=document.getElementsByName("qbnum");
			var bool=false;
			 for (i=0;i<temp.length;i++){
		    	if(temp[i].checked){
					bool=true;
					con=temp[i].value;
					break;
			    }
		 	 }
		 	 if(!bool){
		 	 	alert("�������ѡ��Q�ҳ�ֵ����!");
		 	 	qb_InNum.focus();
		 	 	return ;
		 	 }
		}else{
			con=qb_InNum.value;
		}
		if(con<=0){
			alert("��������ȷ�ĳ�ֵ����!");
			return ;
		}
		if(con>200){
			alert("���ν����������ܴ���200!");
			return ;
		}
		 document.getElementById("qbNumHidden").value=con;
		 
		if (parseFloat(con) - parseFloat(200) > 0) {
			alert("���ν����������ܴ���200!");
			return ;
		}
		
		$("#showMMImg").html("<img src='<%=path%>/imgs/zsh/barcode/qqbitiaoma.jpg' height=70/>"+39076);
		$("#showMMTow").html("��ɨ��&nbsp;"+con+"&nbsp;������");
		
		document.getElementById('light').style.position="absolute"; 
  		document.getElementById('light').style.left=(document.body.clientWidth-500)/2+"px"
  		document.getElementById('light').style.top="20%";
  		$("#showMM").text("��ȷ��Ϊ���룺"+ in_acct.value +" ��ֵ��"+con+"Ԫ");
		document.getElementById('light').style.display='block';
  		document.getElementById('fade').style.display='block';
		return;
		
	}
	
function funClose()
{
	document.getElementById('light').style.display='none';
  	document.getElementById('fade').style.display='none';
}

function funbuttonSubmit()
{
	document.forms[0].target = "_self";
	document.forms[0].action="<%=path%>/business/zshCharge.do?method=qbZhiFuBaoTransfer&in_acct="+document.getElementById("in_acct").value+"&order_price="+document.getElementById("qbNumHidden").value;
	document.getElementById("xx").disabled="disabled";
    document.getElementById("xx").value="��ֵ��...";
    document.forms[0].submit(); 
   	$("#light").text("");
   	document.getElementById("light").style.fontSize="18px";
   	document.getElementById("light").style.textAlign="center";
   	document.getElementById("light").style.height="50px";
   	document.getElementById("light").style.paddingTop="20px";
   	$("#light").html("<img src='<%=path%>/images/load.gif'/>&nbsp;��ֵ�У����Ժ󣬣���");
   	return ;
}


//�����ı��� �뿪����
function funBlur(className){
	if(className.value!=""){
		var temp=document.getElementsByName("qbnum");
		 for (i=0;i<temp.length;i++){
		    if(temp[i].checked){
				temp[i].checked=false;
			}
		 }
	}
}
//��ѡ ѡ��
function funUp(className){
	if(className.checked){
		document.getElementById("qb_InNum").value="";
	}
}
	
window.onload=function()
{
	funListLoad();
}
function funListLoad()
{
$("#tbody").html("<tr><td style='height:275px;' colspan='8' id='errorMessage'><img  src='<%=path%>/images/load.gif'/></td></tr>");
$.post(
    	"<%=path%>/business/prod.do?method=getUserAccountList&listtype=3",
      	{
      	},
      	function (data){
      		if(data==null || data=="" || data=="null")
      		{
      			$("#tbody").html("<tr><td style='height:100px;' colspan='6' id='errorMessage'>��������</td></tr>");
      		}
			else
			{
				$("#tbody").html("");
				for(var i=0;i<data.length;i++)
				{
					//alert(data[i][0]+"   "+data[i][1]+"   "+data[i][2]+"   "+data[i][3]+"   "+data[i][4]+"   "+data[i][5]+"   "+data[i][6]+"   "+data[i][7]+"   "+data[i][8]);
					var state="";//0���ɹ���1��ʧ�ܣ�2��δ��Ӧ���Ʒ�û����Ӧ����3 δ����δ�ۿ 4 �����У��ѿۿ5 ���� 6�쳣����7���˷� 
					if(data[i][5]=="0"){
						state="�ɹ�";
					}else if(data[i][5]=="1"){
						state="ʧ��";
					}else if(data[i][5]=="2"){
						state="δ��Ӧ";
					}else if(data[i][5]=="3"){
						state="δ����";
					}else if(data[i][5]=="4"){
						state="������";
					}else if(data[i][5]=="5"){
						state="�ѳ���";
					}else if(data[i][5]=="6"){
						state="�쳣����";
					}else if(data[i][5]=="7"){
						state="���˷�";
					}else{
						state="δ֪״̬";
					}
					
					$("#tbody").html($("#tbody").html()+"<tr>"+
					"<td style='border:1px solid #cccccc;height:25px; font-size: 13px'>"+parseInt(i+1)+"</td>"+
					"<td style='border:1px solid #cccccc;height:25px; font-size: 13px'>"+data[i][0]+"</td>"+
					"<td style='border:1px solid #cccccc;height:25px; font-size: 13px'>"+parseFloat(data[i][1]).toFixed(2)+" | "+parseFloat(data[i][2]).toFixed(2)+"</td>"+
					"<td style='border:1px solid #cccccc;height:25px; font-size: 13px'>"+data[i][3]+"</td>"+
					"<td style='border:1px solid #cccccc;height:25px; font-size: 13px'>"+data[i][4]+"</td>"+
					"<td style='border:1px solid #cccccc;height:25px; font-size: 13px'>"+state+"</td>"+
					"</tr>");
				}
			}	      		
      		return ;
      	},"json"
	)
}
</script>
	</head>
<body>
	<div id="tabbox"
		style="border: 1px solid #cccccc; border-bottom: 0px;">
		<div id="tabs_title">
			<ul class="tabs_wzdh">
				<li>
					Q�ҳ�ֵ
				</li>
			</ul>
		</div>
	</div>
	<div style="border: 1px solid #cccccc;">
		<div class="mobile-charge" style="margin-left:0px;margin-top:10px;">
			<form action="" method="post" class="form" name="directFrm">
				<input type="hidden" name="prodId" id="prodId" />
				<fieldset>
					<div class="pass_list" style="margin-left: 150;">
						<ul>
							<li style="text-align: center; font-size: 15px; font-weight: bold; color: red;">
								Q�ҵ����޶�&nbsp;<%=strs[0]%>Ԫ,��ֹ��ǰʱ�佻�׶�Ϊ&nbsp;<%=strs[1]%>Ԫ,����ʣ�ཻ�׶�&nbsp;<%=strs[2]%>Ԫ
							</li>
							<li style="font-size: 12px;">
								<strong>QQ�˺ţ�</strong><span><input
										style="font-size: 30px; width: 250px; height: 30px;"
										id="in_acct" name="in_acct" type="text"
										onkeyup="if(isNaN(value))execCommand('undo')"
										onafterpaste="if(isNaN(value))execCommand('undo')" />
								</span>
							</li>
							<li style="font-size: 12px;">
								<strong>ȷ��QQ�˺ţ�</strong><span>
									<input
										style="font-size: 30px; width: 250px; height: 30px;"
										id="in_two" name="in_two" type="text" />
								</span>
							</li>
							<li style="font-size: 12px;">
								<strong>��ֵ������</strong><span>
								<input id="qb_InNum"
										name="qb_InNum" type="text"
										style="font-size: 30px; width: 80px; height: 30px; line-height: 30px;"
										 onkeyup="value=value.replace(/[^\d]/g,'') "
										 onblur="funBlur(this);"/> ��
							</li>
							<li style="font-size: 12px;">
								<strong>���ó�ֵ������</strong>
								<span>
									<input style="border: 0px; height: 20px; width: 20px;"
										type="radio" name="qbnum" value="5" onclick="funUp(this)" />
									5��&nbsp;
									<input style="border: 0px; height: 20px; width: 20px;"
										type="radio" name="qbnum" value="10" onclick="funUp(this)"  />
									10��&nbsp;
									<input style="border: 0px; height: 20px; width: 20px;"
										type="radio" name="qbnum" value="20" onclick="funUp(this)"  />
									20��&nbsp;
									<input style="border: 0px; height: 20px; width: 20px;"
										type="radio" name="qbnum" value="30" onclick="funUp(this)"  />
									30��&nbsp;
									<input style="border: 0px; height: 20px; width: 20px;"
										type="radio" name="qbnum" value="50" onclick="funUp(this)"  />
									50��&nbsp;
									<input style="border: 0px; height: 20px; width: 20px;"
										type="radio" name="qbnum" value="100" onclick="funUp(this)"  />
									100��&nbsp;
									<input style="border: 0px; height: 20px; width: 20px;"
										type="radio" name="qbnum" value="200" onclick="funUp(this)"  />
									200��&nbsp;
								</span>
							</li>
							<li style="font-size: 12px;">
								<strong>&nbsp;</strong><span>
								<input onclick="check()" type="button" id="xx"  style="width:85px; height:32px; border:0px;color:#fff; font-weight:bold;background:url(<%=path%>/images/button_out.png) no-repeat bottom; cursor:pointer;" value="��ֵ"/>
							</li>
						</ul>
					</div>
					<br/><br/>
					<input type="hidden" value="" id="qbNumHidden"/>
				</fieldset>
			</form>
		</div>

	</div>

	<ul>
		<li
			style="font-size: 15px; text-align: left; font-weight: bold; color: red; padding-left: 20px; margin-top: 5px; width: 95%;">
			�������ʮ�����׼�¼ &nbsp;
			<a href="javascript:funListLoad();" style="float: right;">ˢ���б�</a>
		</li>
	</ul>
	<div style="margin-top: 5px; border: 1px solid #cccccc;">
		<table>
			<thead>
				<tr>
					<th style="width: 150px; height: 25px; border: 1px solid #cccccc;">
						���
					</th>
					<th style="width: 150px; height: 25px; border: 1px solid #cccccc;">
						��ֵ����
					</th>
					<th style="width: 150px; height: 25px; border: 1px solid #cccccc;">
						��ֵ���|�۷ѽ��
					</th>
					<th style="width: 150px; height: 25px; border: 1px solid #cccccc;">
						�ͻ��ο����
					</th>
					<th style="width: 150px; height: 25px; border: 1px solid #cccccc;">
						��ֵʱ��
					</th>
					<th style="width: 150px; height: 25px; border: 1px solid #cccccc;">
						��ֵ״̬
					</th>
				</tr>
			</thead>
			<tbody id="tbody" style="text-align: center; width: 100%;">
				<tr>
					<td style="height: 275px;" colspan="6" id="errorMessage">
						<img src="<%=path%>/images/load.gif" />
					</td>
				</tr>
			</tbody>
		</table>
	</div>
	<br />
	<div
		style="padding-left: 40px; padding-top: 10px; font-size: 13px; color: #FF6600; font-weight: bold; border: 1px solid #FF9E4d; background-color: #FFFFE5;">
		<ul>
			<li style="list-style: none;">
				1��֧�ָ�QQ����ֱ�ӳ�ֵ�����в������ܽ��г�����
			</li>
			<li style="list-style: none;">
				&nbsp;
			</li>
			<li style="list-style: none;">
				2��ÿ�γ�ֵ�������ܴ���200��ÿ��QQ��ÿ���ۼƲ�����2000��
			</li>
			<li style="list-style: none;">
				&nbsp;
			</li>
		</ul>
	</div>
<div  id="light" class="white_content" style="background-color:white;height:250px;width:500px;border-radius:10px;">
<div style="width: auto;height:99%;">
<table  style="height:98%;width:100%;">
	<thead  style="height:30px;">
		<tr >
			<th style="text-align:left;font-size:14px;padding-left:20px;">
				��ܰ��ʾ
			</th>
		</tr>
	</thead>
	<tbody >
		<tr>
			<td id="showMM" style="color:red;font-size:18px;font-weight:bold;text-align:center;border-bottom:1px solid #cccccc;height:40px;line-height:40px;"></td>
		</tr>
		<tr>
			<td id="showMMImg" style="color:red;font-size:18px;font-weight:bold;text-align:left;padding-left:15px;border-bottom:1px solid #cccccc;height:80px;line-height:80px;padding-top:10px;">
			</td>
		</tr>
		<tr>
			<td id="showMMTow" style="color:red;font-size:18px;font-weight:bold;text-align:left;border-bottom:1px solid #cccccc;height:40px;line-height:40px;"></td>
		</tr>
		<tr>
			<td align="center" >
				<div style="float:right;margin-top:8px;">
					 <input type="button" id="wltfarmebuttonvalue" name="Button" value="&nbsp;ȷ&nbsp;��&nbsp;" style="border:0px; width:85px; height:32px; color:#fff; font-weight:bold; background:url(../images/button_out.png) no-repeat bottom; cursor:pointer;" onclick="funbuttonSubmit()"/>
				&nbsp;
					<input type="button" name="Button" value="&nbsp;ȡ&nbsp;��&nbsp;" style="border:0px; width:85px; height:32px; color:#fff; font-weight:bold; background:url(../images/button_out.png) no-repeat bottom; cursor:pointer;" onclick="funClose()"/>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				</div>
			</td>
		</tr>
	</tbody>
</table>
</div>
</div>
<div id="fade" class="black_overlay"></div> 
<input type="hidden" id="mess" value="${requestScope.mess }" />
</body>
</html>
