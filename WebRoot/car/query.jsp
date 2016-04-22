<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">
		<title>Υ�²�ѯ</title>
		<script src="<%=path%>/js/jquery-1.8.2.min.js" type="text/javascript"></script>
		<script type="text/javascript" src="<%=path%>/js/util.js"></script>
		<style>
* {
	margin: 0;
	padding: 0;
	font-size: 14px;
	font-family: "΢���ź�";
}

.wrapper {
	width: 830px;
	height: auto;
	overflow: hidden;
	margin: 0 auto;
}

.h_box {
	height: 40px;
	overflow: hidden;
	border-bottom: 1px #9e9e9e solid;
}

.h_box h4 {
	float: left;
	line-height: 40px;
	font-weight: normal;
	font-size: 24px;
}

.tab_a {
	float: right;
}

.tab_a a {
	width: 98px;
	height: 32px;
	display: inline-block;
	margin-left: 10px;
}

.content {
	margin-top: 20px;
	position: relative;
}

#tab_01 {
	position: relative;
	background: url(<%=path%>/car/images/01.jpg) no-repeat 0 0;
	width: 830px;
	height: 600px;
}

#tab_01 .span1 {
	display: inline-block;
	width: 50px;
	height: 28px;
	background: url(<%=path%>/car/images/span1_bg.png) no-repeat 0 0;
}

#tab_01 .span2 {
	display: inline-block;
	width: 133px;
	height: 28px;
	background: url(<%=path%>/car/images/span2_bg.png) no-repeat 0 0;
	margin-left: 15px;
}

#tab_01 .span3 {
	display: inline-block;
	width: 201px;
	height: 28px;
	background: url(<%=path%>/car/images/span_bg.png) no-repeat 0 0;
}

#tab_01 tr {
	display: block;
	margin: 10px 0;
}

.tj {
	display: block;
	margin: 10px 0 0 150px;
	width: 98px;
	height: 32px;
	background: url(<%=path%>/car/images/nkscx_kscxan.gif) no-repeat 0 0;
}

#tab_01 lable {
	text-align: right;
}

.a1 {
	background: url(<%=path%>/car/images/a01_1.png) no-repeat 0 0;
}

.a2 {
	background: url(<%=path%>/car/images/a02_2.png) no-repeat 0 0;
}

.cur1,.a1:hover {
	background: url(<%=path%>/car/images/a01.png) no-repeat 0 0;
}

.cur2,.a2:hover {
	background: url(<%=path%>/car/images/a02.png) no-repeat 0 0;
}
</style>
<script type="text/javascript">
showMessage("${mess}");
function showImg(){
	var Car_code=$("#code_aa");
	var X = Car_code.position().top;
	var Y = Car_code.position().left;
	document.getElementById("tc_img").style.position="absolute";
	document.getElementById("tc_img").style.left=Y+201+"px";
	document.getElementById("tc_img").style.top=X+"px";
	document.getElementById("tc_img").style.zIndex=1;
	document.getElementById("tc_img").style.display="block";
}
function dis(){
	document.getElementById("tc_img").style.display="none";
}
$(function(){
	$("#tab_02").hide();
	$(".a1").addClass("cur1");
	$(".a1").show();
	
	$(".tab_a a").click(function(){
		var i=$(this).index()+1;
		$(".tab_a a").removeClass("cur1 cur2");
		$(this).addClass("cur"+i);
		$("#tab_01").hide();
		$("#tab_02").hide();
		if(i==1){
			//��ѯ
			$("#tab_01").show();
		}else{
			//����
			$("#tab_02").show();
			funListLoad(1);
		}
	});
})

//ajax ��ҳ
function funPage(dis){
	var index=document.getElementById("index_hidden").value;
	if(dis=="begin"){
		index=1;
	}else if(dis=="up"){
		index=parseInt(index)-1;
	}else if(dis=="down"){
		index=parseInt(index)+1;
	}else if(dis=="end"){
		index=document.getElementById("lastindex_hidden").value;
	}else if(dis=="href"){
		index=document.getElementById("pageId").value;
	}
	funListLoad(index);
}

//�����б�
function funListLoad(index)
{
	$("#tbody").html("<tr><td style='height:275px;' colspan='8' id='errorMessage'><img src='<%=path%>/images/load.gif'/></td></tr>");
	$.post("<%=path%>/cars.do?method=CarList",
      	{
      		requestType:"1",
      		index:index
      	},
      	function (data){
			document.getElementById("index_hidden").value=data[0].index;
			document.getElementById("pageId").value=data[0].index;
			document.getElementById("lastindex_hidden").value=data[0].lastindex;
			
			data=data[0].arry;
      		if(data==null || data=="" || data=="null"){
      			$("#tbody").html("<tr><td style='height:100px;' colspan='8' id='errorMessage'>��������</td></tr>");
      		}else{
				$("#tbody").html("");
				for(var i=0;i<data.length;i++){
					var st="";
      				if(1==data[i][5]){
						st="�µ��ɹ�δ֧��";
					}else if(2==data[i][5]){
						st="��֧��";
					}else if(3==data[i][5]){
						st="������";
					}else if(4==data[i][5]){
						st="����ɹ�";
					}else if(5==data[i][5]){
						st="����ʧ��";
					}else if(6==data[i][5]){
						st="�����쳣";
					}else if(7==data[i][5]){
						st="ȡ��";
					}else if(-99==data[i][5]){
						st="ɾ�����쵥";
					}else if(100==data[i][5]){
						st="������";
					}else if(101==data[i][5]){
						st="���˷�";
					}else{
						st="δ֪״̬";
					}
					var css="";
					if((i+1)%2==0){
						css="#F9F2E3";
					}else{
						css="#FBEEC5";
					}
					var strs="<tr onclick=funList('"+data[i][0]+"') style='background-color:"+css+"' onmouseover=this.style.cursor='hand' title='�������Υ����ϸ' >"+
					"<td style='height:40px;'>"+data[i][0]+"</td>"+
					"<td style='height:40px;'>"+data[i][1]+"</td>"+
					"<td style='height:40px;'>"+data[i][2]+"</td>"+
					"<td style='height:40px;'>"+data[i][3]+"</td>"+
					"<td style='height:40px;'>"+parseFloat(data[i][4]/1000).toFixed(2)+"</td>"+
					"<td style='height:40px;'>"+st+"</td>"+
					"<td style='height:40px;'>"+data[i][6]+"</td>"+
					"</tr>"+
					"<tr><td colspan=7 >"+
					"<table  style='width: 100%; font-size: 12px; border:0;' cellspacing='0' cellpadding='0' id='"+data[i][0]+"' align='center'></table>"+
					"</td></tr>";
					$("#tbody").html($("#tbody").html()+strs);
				}
			}	      		
      		return ;
      	},"json"
	)
}
function funList(idList){
	if($("#"+idList).html()!=""){
		$("#"+idList).html("");
		return ;
	}

	$("#"+idList).html("<tr><td style='height:50px;' colspan='10' align='center'><img src='<%=path%>/images/load.gif'/></td></tr>");
	$.post("<%=path%>/cars.do?method=CarList",{
    		requestType:"2",
    		wzid:idList
    	},
      	function (data){
      		if(data==null || data=="" || data=="null"){
      			$("#"+idList).html("<tr><td style='height:100px;' colspan='10' id='errorMessage'>ϵͳ�쳣</td></tr>");
      		}else{
				$("#"+idList).html("");
				var strs="<tr >"+
					"<td style='height:50px;text-align:center;width:50px;'>&nbsp;</td>"+
					"<td style='height:50px;text-align:center;width:110px;'>Υ��ʱ��</td>"+
					"<td style='height:50px;text-align:center;width:110px;'>Υ�µص�</td>"+
					"<td style='height:50px;text-align:center;width:80px;'>����</td>"+
					"<td style='height:50px;text-align:center;width:80px;'>���ɽ�</td>"+
					"<td style='height:50px;text-align:center;width:80px;'>�����</td>"+
					"<td style='height:50px;text-align:center;width:80px;'>�۷�</td>"+
					"<td style='height:50px;text-align:center;width:80px;'>״̬</td>"+
					"<td style='height:50px;text-align:center;width:80px;'>С��</td>"+
					"</tr>";
				for(var i=0;i<data.length;i++){
					var st="";
      				if(1==data[i][5]){
						st="�µ��ɹ�δ֧��";
					}else if(2==data[i][8]){
						st="��֧��";
					}else if(3==data[i][8]){
						st="������";
					}else if(4==data[i][8]){
						st="����ɹ�";
					}else if(5==data[i][8]){
						st="����ʧ��";
					}else if(6==data[i][8]){
						st="�����쳣";
					}else if(7==data[i][8]){
						st="ȡ��";
					}else if(-99==data[i][8]){
						st="ɾ�����쵥";
					}else if(100==data[i][8]){
						st="������";
					}else if(101==data[i][8]){
						st="���˷�";
					}else{
						st="δ֪״̬";
					}
					strs=strs+"<tr >"+
					"<td style='height:40px;text-align:center;'>&nbsp;</td>"+
					"<td style='height:40px;text-align:center;'>"+data[i][0]+"</td>"+
					"<td style='height:40px;text-align:center;'>"+data[i][1]+"</td>"+
					"<td style='height:40px;text-align:center;'>"+parseFloat(data[i][3]/1000).toFixed(2)+"</td>"+
					"<td style='height:40px;text-align:center;'>"+parseFloat(data[i][4]/1000).toFixed(2)+"</td>"+
					"<td style='height:40px;text-align:center;'>"+parseFloat((parseFloat(data[i][5])+parseFloat(data[i][6]))/1000).toFixed(2)+"</td>"+
					"<td style='height:40px;text-align:center;'>"+data[i][7]+"</td>"+
					"<td style='height:40px;text-align:center;'>"+st+"</td>"+
					"<td style='height:40px;text-align:center;'>"+parseFloat(data[i][9]/1000).toFixed(2)+"</td>"+
					"</tr>";
				}
				$("#"+idList).html(strs);
			}	      		
      		return ;
      	},"json"
	)
}

//���ٲ�ѯ ��֤
function Verification(){
	var car_number=$('input[name="car_number"]');         
	var car_type=$('select[name="car_type"]');
	var car_code=$('input[name="car_code"]');
	var car_engine=$('input[name="car_engine"]');
	var car_username=$('input[name="car_username"]');
	var car_userphone=$('input[name="car_userphone"]');
	if($.trim(car_number.val())==""){
		alert("�����복�ƺ�!");
		car_number.focus();
		return ;
	}
	if(car_number.val().length<4){
		alert("��������ȷ�ĳ��ƺ�!");
		car_number.focus();
		return ;
	}
	
	if($.trim(car_type.val())==""){
		alert("��ѡ��������!");
		car_type.focus();
		return ;
	}
	
	if($.trim(car_code.val())=="" || car_code.val().length!=6){
		alert("��������λ����ʶ�����!");
		car_code.focus();
		return ;
	}
	
	if($.trim(car_engine.val())=="" || car_engine.val().length!=4){
		alert("��������λ��������!");
		car_engine.focus();
		return ;
	}
	if($.trim(car_username.val())==""){
		alert("��������ϵ��!");
		car_username.focus();
		return ;
	}
	if($.trim(car_userphone.val())=="" || car_userphone.val().length<7){
		alert("��������ȷ����ϵ�绰!");
		car_userphone.focus();
		return ;
	}
	var form=$('form[name="for"]');
	form.submit();
	
	$(".tj").css('display','none'); 
	funLoad();
	return ;
}
function funLoad()
{
	if(!document.getElementById("load"))
	{
		var body=document.getElementsByTagName('body')[0];
		var left=(document.body.offsetWidth-350)/2;
		var div="<div id='load' style='border:1px solid black;background-color:white;position:absolute;top:280px;left:"+left+"px;width:350px;height:90px;z-Index:999;text-align:center;font-weight:bold;'><div style='border-bottom:1px solid #cccccc;height:25px;line-height:25px;text-align:left;padding-left:10px;'>��ܰ��ʾ</div><div style='width:100%;height:65px;line-height:65px;'><img src='<%=path%>/images/load.gif' />&nbsp;���ݼ����У����Ժ󣬣�����</div></div>";
		body.innerHTML=body.innerHTML+div;
	}
}
</script>
	</head>
	<body>
		<div class="wrapper">
			<div class="h_box">
				<h4>
					��ͨ����
				</h4>
				<div class="tab_a">
					<a href="javascript:;" class="a1"></a>
					<a href="javascript:;" class="a2"></a>
				</div>
			</div>
			<form action="<%=path%>/cars.do?method=CarQuery" method="post"
				name="for">
				<input type="hidden" value="0" name="requestType" />
				<div class="content">
					<div id="tab_01" style="display: ;" class="tab_box">
						<div style="width: 500px; margin: 0 auto">
							<table>
								<tr>
									<td align="right" style="text-align: right; width: 120px;height:40px;">
										���ƺ��� ��
									</td>
									<td>
										<span class="span1"> 
											<select name="province_name"
													style="border: none 0; background: none; width: 50px; height: 26px;">
													<option value="��">
														��
													</option>
											</select> 
										</span>
										<span class="span2"> 
											<input type="text" value=""
												name="car_number" maxlength=6 
												style="width: 133px; height: 28px; background: none; border: 0 none;"
												onkeyup="value=value.toUpperCase() " /> 
										</span>
										<font style="color:red;padding-left:6px;">�����복�ƺ���</font>
									</td>
								</tr>

								<tr>
									<td align="right" style="text-align: right; width: 120px;height:40px;">
										�������� ��
									</td>
									<td>
										<span class="span3"> <select name="car_type"
												style="width: 201px; height: 28px; border: 0 none; background: none;">
												<option value="">
													��ѡ��������
												</option>
												<option value="02">
													С������
												</option>
												<option value="01">
													��������
												</option>
												<option value="03">
													ʹ������
												</option>
												<option value="04">
													�������
												</option>
												<option value="05">
													��������
												</option>
												<option value="06">
													�⼮����
												</option>
												<option value="07">
													��������Ħ�г�
												</option>
												<option value="08">
													���Ħ�г�
												</option>
												<option value="09">
													ʹ��Ħ�г�
												</option>
												<option value="10">
													���Ħ�г�
												</option>
												<option value="11">
													����Ħ�г�
												</option>
												<option value="12">
													�⼮Ħ�г�
												</option>
												<option value="13">
													ũ�����䳵
												</option>
												<option value="14">
													������
												</option>
												<option value="15">
													�ҳ�
												</option>
												<option value="16">
													��������
												</option>
												<option value="17">
													����Ħ�г�
												</option>
												<option value="18">
													��������
												</option>
												<option value="19">
													����Ħ�г�
												</option>
												<option value="20">
													��ʱ�뾳����
												</option>
												<option value="21">
													��ʱ�뾳Ħ�г�
												</option>
												<option value="22">
													��ʱ��ʻ��
												</option>
												<option value="23">
													��������
												</option>
												<option value="24">
													����Ħ�г�
												</option>
												<option value="26">
													����������
												</option>
												<option value="27">
													�����������
												</option>
											</select> </span>
									</td>
								</tr>

								<tr>
									<td align="right" style="text-align: right; width: 120px;height:40px;">
										����ʶ����� ��
									</td>
									<td>
										<span class="span3"> 
											<input type="text" name="car_code" id="code_aa"
												 maxlength="6"
												style="width: 201px; height: 28px; background: none; border: 0 none;"
												onFocus="showImg()" onblur="dis()"> 
										</span>
										<font style="color:red;padding-left:6px;">�������6λ</font>
									</td>
								</tr>

								<tr>
									<td align="right" style="text-align: right; width: 120px;height:40px;">
										�������� ��
									</td>
									<td>
										<span class="span3"> <input type="text"
												name="car_engine"  maxlength="4"
												style="width: 201px; height: 28px; background: none; border: 0 none;" />
										</span>
										<font style="color:red;padding-left:6px;">�������ź�4λ</font>
									</td>
								</tr>

								<tr>
									<td align="right" style="text-align: right; width: 120px;height:40px;">
										��ϵ�� ��
									</td>
									<td>
										<span class="span3"> <input type="text"
												name="car_username" maxlength=5 
												style="width: 201px; height: 28px; background: none; border: 0 none;" />
										</span>
										<font style="color:red;padding-left:6px;">����������</font>
									</td>
								</tr>

								<tr>
									<td align="right" style="text-align: right; width: 120px;height:40px;">
										�ֻ����� ��
									</td>
									<td>
										<span class="span3"> <input type="text"
												name="car_userphone"  maxlength="11"
												style="width: 201px; height: 28px; background: none; border: 0 none;"
												onkeyup="value=value.replace(/[^\d]/g,'') " /> 
										</span>
										<font style="color:red;padding-left:6px;">�������ֻ�����</font>
									</td>
								</tr>

							</table>
							<a href="javascript:Verification()" class="tj" title="���ٲ�ѯ"></a>
						</div>

						<!--������-->
						<div style="display: none;" class="tc_img" id="tc_img">
							<img src="<%=path%>/car/images/n_cjhts_03.jpg" alt="">
						</div>
					</div>


					<!--tab02-->
					<div id="tab_02" style="position: relative; width: 100%">
						<table style="width: 100%; text-align: center;border-radius:10px;border:1px solid #cccccc;"  cellpadding="0" 
							cellspacing="0">
							<tr >
								<td style=" height: 50px;">
									����ID
								</td>
								<td style="">
									���ƺ�
								</td>
								<td style="">
									�û�����
								</td>
								<td style="">
									�û��绰
								</td>
								<td style="">
									�����ܶ�(Ԫ)
								</td>
								<td style="">
									����״̬
								</td>
								<td style="">
									�µ�ʱ��
								</td>
							</tr>
							<tbody id="tbody" style="text-align: center; width: 100%;"></tbody>
							<tr>
								<td style="text-align:right;" colspan="7" height=30>
									<div style="width:95%;">
										<a href="javascript:funPage('begin')">��ҳ</a>
										<a href="javascript:funPage('up')">��һҳ</a>
										<a href="javascript:funPage('down')">��һҳ</a>
										<a href="javascript:funPage('end')">βҳ</a>&nbsp;
										��ת&nbsp;<input id="pageId" type="text" style="width:30px"  value="" onblur="funPage('href')" onkeyup="value=value.replace(/[^\d]/g,'') "/>
									</div>
								</td>
							</tr>
						</table>
					</div>
				</div>
			</form>
		</div>
		<input type="hidden" value="1" id="index_hidden"/>
		<input type="hidden" value="1" id="lastindex_hidden"/>
	</body>
</html>
