<%@ page contentType="text/html; charset=gb2312" language="java"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:useBean id="Biprod" scope="page"
	class="com.wlt.webm.business.bean.BiProd" />
<jsp:useBean id="userSession" scope="session"
	class="com.wlt.webm.rights.form.SysUserForm" />
<%@ page import="com.wlt.webm.tool.Tools" %>
<%
	String path = request.getContextPath();
	String[] strs = Biprod.getPlaceMoney("1", userSession.getUserno());
	String flag=Tools.getPwdflag(userSession.getUserno());
	request.setAttribute("flag",flag);
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
		<script language="JavaScript">

showMessage("${mess}");
	function check(){  
		with(document.forms[0]){
			if(in_acct.value.length == 0||in_acct.value.length==11) 
		{
			alert("���ѣ�����д��ȷ��QQ����!");
			directFrm.in_acct.focus();
			return false;
		}
		if(in_two.value!=in_acct.value ) {
			alert("���ѣ�����QQ���벻һ��!");
			in_acct.focus();
			return false;
		}
		
		var temp=document.getElementsByName("qbnum");
		var ok=false;
		var qbNum=order_price.value;
  for (i=0;i<temp.length;i++){
    if(temp[i].checked){
    ok=true;
    qbNum=temp[i].value;
    }
    }
		if((order_price.value == ""
				|| order_price.value == 0)&&ok==false) {
			alert("���ѣ�����дQ������!");
			directFrm.order_price.focus();
			return false;
		}
				if((order_price.value != ""
				|| order_price.value != 0)&&ok==true) {
			alert("���ѣ�����Q������ֻ�������ѡ����ͬʱѡ��!");
			directFrm.order_price.focus();
			return false;
		}
		
		if (parseFloat(order_price.value) - parseFloat(200) > 0) {
			alert("���ν����������ܴ���200!");
			return false;
		}
		if (!/^[0-9]*$/.test(order_price.value)) {
			alert("��������ȷ������");
			return false;
		}
		//��������
		if(<%=flag%>==1){
		document.getElementById("xx").value="��ȴ�...";
		document.getElementById("xx").disabled="disabled";
		var rs=validateTradePwd($("#psd").val(),<%=flag%>);
		if(rs==1){
			alert("�������");
		    document.getElementById("xx").value="��ֵ";
			document.getElementById("xx").disabled=false;;
			return false;
			}
			}
			//��������
		
			if(confirm("ȷ��Ϊ���룺"+ in_acct.value+" �䣺"+qbNum+"QB")){
			target = "_self";
			document.forms[0].action="../qb/qbResult.do?method=qbTenpayTransfer&in_acct="+in_acct.value+"&order_price="+order_price.value;
	   	 document.getElementById("xx").value="��ֵ��...";
		 document.getElementById("xx").disabled="disabled";
	    	submit();  
		} }
	}
	function getFocus(){
		document.forms[0].tradeObject.focus();
	}
	
	function panduan() {
	with(document.forms[0]){
	var temp=document.getElementsByName("qbnum");
  for (i=0;i<temp.length;i++){
    if(temp[i].checked){
		if(order_price.value != ""&&order_price.value != 0){
		//temp[i].checked=false;
		//alert("�Ѿ����빺������,��������ѡ��");
		document.getElementById("order_price").value="";
		return ;
		}
		}
		}
		}
	}
	var tempradio= null;
	function checknum(checkedRadio){
	     var num=document.getElementById("order_price").value;
	     if(tempradio== checkedRadio){  
           tempradio.checked=false;  
           tempradio=null;  
          }   
        else{
        tempradio= checkedRadio;    
        }  
	}
	
	function onblue(){
		with(document.forms[0]){
	var temp=document.getElementsByName("qbnum");
  for (i=0;i<temp.length;i++){
    if(temp[i].checked){
		temp[i].checked=false;
		}
	}
	}
	}
	function chakan(){
		var qbnum=$("input[name='qbnum']:checked").val();
		var order_price=document.getElementById("order_price").value;
		var num;
		if(qbnum==null&&(order_price==""||order_price.length==0)){
			alert("�������ѡ��Q�Ҹ���");
		}else{
			if(qbnum!=null){
				getmoney(qbnum,'0005');
			}else{
				getmoney(order_price,'0005');
			}
		}
	}
	
function getmoney(num,type){//&order_price="+num
		$.post(
	    	$("#rootPath").val()+"/qb/qbResult.do?method=getMoney",
	      	{
	      		price :num,
	      		gametype:type
	      	},
	      	function (data){
	      		var mmm;
      			mmm = data[0].mon;
      			document.getElementById("dis").innerHTML=mmm+",<a onclick='shouqi()' href='#'>�������</a>";
	      		return ;
	      	},"json"
		)
	}
	function shouqi(){
		document.getElementById("dis").innerHTML="<a onclick='chakan()' href='#'>����鿴</a>";
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
		<div style="border: 1px solid #cccccc;padding:0px 0px 0px 0px;">
			<input type="hidden" id="rootPath" value="<%=path%>" />
			<div class="mobile-charge" style="margin:0px 0px 0px 0px;margin-top:10px;">
				<form action="" method="post" class="form">
					<input type="hidden" name="prodId" id="prodId" />
					<fieldset>
						<div class="pass_list" style="margin-left: 150;" >
							<ul >
								<li
									style="text-align: center; font-size: 15px; font-weight: bold; color: red;">
									Q�ҵ����޶�&nbsp;<%=strs[0]%>Ԫ,��ֹ��ǰʱ�佻�׶�Ϊ&nbsp;<%=strs[1]%>Ԫ,����ʣ�ཻ�׶�&nbsp;<%=strs[2]%>Ԫ
								</li>
								<li style="font-size: 20px;">
									<strong style="line-height: 45px;">QQ�˺ţ�</strong><span><input
											style="font-size: 45px; width: 250px; height: 45px; line-height: 45px;"
											name="in_acct" type="text"
											onkeyup="if(isNaN(value))execCommand('undo')"
											onafterpaste="if(isNaN(value))execCommand('undo')" />
									</span>
								</li>
								<li style="font-size: 20px;">
									<strong style="line-height: 45px;">ȷ��QQ�˺ţ�</strong><span><input
											style="font-size: 45px; width: 250px; height: 45px; line-height: 45px;"
											name="in_two" type="text" />
									</span>
								</li>
								<li style="font-size: 20px;">
									<strong style="line-height: 45px;">��ֵ������</strong><span><input id="order_price"
											name="order_price" type="text"
											style="font-size: 45px; width: 80px; height: 45px; line-height: 45px;"
											onkeyup="if(isNaN(value))execCommand('undo')"
											onafterpaste="if(isNaN(value))execCommand('undo')"
											onfocus="onblue()" /> ��
								</li>
								<li style="font-size: 20px;">
									<strong style="line-height: 25px;">���ó�ֵ������</strong>
									<input style="border: 0px; height: 20px; width: 20px;"
										type="radio" name="qbnum" value="5" onclick="panduan();" />
									&nbsp;5��
									<input style="border: 0px; height: 20px; width: 20px;"
										type="radio" name="qbnum" value="10" onclick="panduan();" />
									&nbsp;10��
									<input style="border: 0px; height: 20px; width: 20px;"
										type="radio" name="qbnum" value="20" onclick="panduan();" />
									&nbsp;20��
									<input style="border: 0px; height: 20px; width: 20px;"
										type="radio" name="qbnum" value="30" onclick="panduan();" />
									&nbsp;30��
									<input style="border: 0px; height: 20px; width: 20px;"
										type="radio" name="qbnum" value="50" onclick="panduan();" />
									&nbsp;50��
									<input style="border: 0px; height: 20px; width: 20px;"
										type="radio" name="qbnum" value="100" onclick="panduan();" />
									&nbsp;100��
									</span>
								</li>
<c:if test="${flag==1}">
<li>
<strong style="font-size: 20px;line-height: 45px;">�������룺</strong>
<span><input id="psd" type="password" style="font-family:'����'; font-size:45px;width:250px;height:45px; font-weight:900;"/></span>
</li>
</c:if>
								<li>
									<div id="caigou">
										<strong style="font-size: 20px;line-height: 45px;">�ɹ���Ϣ��</strong>
										<span id="dis" style="font-size: 20px;line-height: 45px;"><a onclick="chakan();" href="#" >����鿴</a>
										</span>
									</div>
									<div class="element" style="display: none">
										<div class="form-font clearfix">
											<span name="price" class="highlight font-16"
												id="js_id_money_show">0</span>Ԫ
										</div>
									</div>
								</li>
							</ul>
						</div>
						<div class="field-item_button_m"
							style="clear: left; margin-left: 180px ! importtant; margin-top: 0px; height: 40px;" />
							<input type="button" name="Button" id="xx" value="��ֵ"
								class="field-item_button" onclick="check();" />
						</div>
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
	</body>
	<input type="hidden" id="mess" value="${requestScope.mess }" />
</html>
