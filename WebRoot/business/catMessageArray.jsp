<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@ page import="java.util.*"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<jsp:useBean id="userSession" scope="session" class="com.wlt.webm.rights.form.SysUserForm" />
<%
	String path = request.getContextPath();
%>
<html>
	<head>
		<title>���ͨ</title>

		<script type="text/javascript" src="../js/jquery-1.8.2.min.js"></script>
		<script type="text/javascript" src="../js/util.js"></script>
		<script language="javascript" type="text/javascript"
			src="../My97DatePicker/WdatePicker.js"></script>
		<script type="text/javascript">
	window.onload=function(){
		document.getElementById("state").value="${state}";
		if("${inter}"!=""){
			document.getElementById("interID").value="${inter}";
		}else{
			document.getElementById("interID").value=-22;
		}
		
		if("${plateNumberA}"!="")
			document.getElementById("plateNumberA").value="${plateNumberA}";
	}
	//��ʾ��
	function funLoad()
	{
		if(!document.getElementById("load"))
		{
			var body=document.getElementsByTagName('body')[0];
			var left=(document.body.offsetWidth-350)/2;
			var div="<div id='removeload'><div id='load' style='border:1px solid black;background-color:white;position:absolute;top:230px;left:"+left+"px;width:350px;height:90px;z-Index:999;text-align:center;font-weight:bold;'><div style='border-bottom:1px solid #cccccc;height:25px;line-height:25px;text-align:left;padding-left:10px;font-size:13px;'>��ܰ��ʾ</div><div style='width:100%;height:65px;line-height:65px;font-size:13px;'><img src='<%=path%>/images/load.gif' />&nbsp;���ݼ����У����Ժ󣬣�����</div></div></div>";
			body.innerHTML=body.innerHTML+div;
		}
	}
	// ȥ�ո�
	String.prototype.Trim = function() 
	{ 
		return this.replace(/(^\s*)|(\s*$)/g, ""); 
	}
	//��ѯ
	function funSelect()
	{	
		document.getElementById("indexID").value="${index}";
		document.getElementById("for").action="<%=path %>/business/mobileCharge.do?method=catMessageList&wheretype=0";
		document.getElementById("for").submit();
		funLoad();
		return ;
	}
	//��һҳ  ��ת
	function funPage(index){
		document.getElementById("indexID").value=index;
		document.getElementById("for").action="<%=path %>/business/mobileCharge.do?method=catMessageList&wheretype=0";
		document.getElementById("for").submit();
		funLoad();
		return ;
	}
	
	//��ʾָ��������Υ�¼�¼
	function showCatMessage(gdID,catId,gdwoState,interfaceId)//interfaceId ���ְ��°� ����  �ӿ�
	{
		if($("#"+gdID+"_updown").text()=="չ��"){
			//�ر����� ��̬��ӵ�tr,
			var ssss=document.getElementsByName("ssss");
			for(var i=0;i<ssss.length;i++){
				$("#"+ssss[i].value+"_table").text("");
				document.getElementById(ssss[i].value+"_tr").style.display="none";
			}
			var showMesageNamss=document.getElementsByName("showMesageNamss");
			for(var i=0;i<showMesageNamss.length;i++){
				showMesageNamss[i].innerHTML="չ��";
			}
			//��ʾ ��̬��ӵ� tr
			$("#"+gdID+"_updown").text("����");
			var str="<tr>"+
					"<td width=55 border=0 height=35></td>"+
   					"<td style='text-align: center; border-bottom: 1px solid #1A7FC3; border-right: 1px solid #1A7FC3;border-left: 1px solid #1A7FC3;width:55px;'>Υ��ID</td>"+
   					"<td style='text-align: center; border-bottom: 1px solid #1A7FC3; border-right: 1px solid #1A7FC3;width:168px;'>Υ�µص�</td>"+
   					"<td style='text-align: center; border-bottom: 1px solid #1A7FC3; border-right: 1px solid #1A7FC3;width:143px;'>Υ��ʱ��</td>"+
   					"<td style='text-align: center; border-bottom: 1px solid #1A7FC3; border-right: 1px solid #1A7FC3;width:161px;'>����ʱ��</td>"+
   					"<td style='text-align: center; border-bottom: 1px solid #1A7FC3; border-right: 1px solid #1A7FC3;width:90px;'>ʵ��/Ԥ��<br/>(״̬)</td>"+
   					"<td style='text-align: center; border-bottom: 1px solid #1A7FC3;width:150px;'>����</td>"+
   					"</tr>";
			$.post(
		    	"<%=path %>/business/mobileCharge.do?method=show_breakrules",
		      	{
		      		gdID:gdID
		      	},
		      	function (data){
		      		if(data!=null && data!=""){
		      			for(var i=0;i<data.length;i++){
		      				var ssstate="";
		      				if(1==data[i][17]){
								ssstate="�µ��ɹ�δ֧��";
							}else if(2==data[i][17]){
								ssstate="��֧��";
							}else if(3==data[i][17]){
								ssstate="������";
							}else if(4==data[i][17]){
								ssstate="����ɹ�";
							}else if(5==data[i][17]){
								ssstate="����ʧ��";
							}else if(6==data[i][17]){
								ssstate="�����쳣";
							}else if(7==data[i][17]){
								ssstate="ȡ��";
							}else if(-99==data[i][17]){
								ssstate="ɾ�����쵥";
							}else if(100==data[i][17]){
								ssstate="������";
							}else if(101==data[i][17]){
								ssstate="���˷�";
							}else{
								ssstate="δ֪״̬";
							}
		      				str=str+"<tr>"+
		      					"<td width=55 border=0 height=60></td>"+
		      					"<td style='text-align: center; border-bottom: 1px solid #1A7FC3; border-right: 1px solid #1A7FC3;border-left: 1px solid #1A7FC3;'>"+data[i][0]+"</td>"+
		      					"<td style='text-align: center; border-bottom: 1px solid #1A7FC3; border-right: 1px solid #1A7FC3;'>"+data[i][4]+"</td>"+
		      					"<td style='text-align: center; border-bottom: 1px solid #1A7FC3; border-right: 1px solid #1A7FC3;'>"+data[i][5]+"</td>"+
		      					"<td style='text-align: center; border-bottom: 1px solid #1A7FC3; border-right: 1px solid #1A7FC3;'>"+data[i][14]+"</td>"+
		      					"<td style='text-align: center; border-bottom: 1px solid #1A7FC3; border-right: 1px solid #1A7FC3;'>"+((parseFloat(data[i][23]))/1000)+"/"+((parseFloat(data[i][24]))/1000)+"Ԫ<br/>("+ssstate+")</td>"+
		      					"<td style='text-align: center; border-bottom: 1px solid #1A7FC3;line-height:30px; '>"+
		      					"<a style='cursor:hand;color:red;' onclick='funshowInfo(event,\""+catId+"\",\""+data[i][5]+"\",\""+data[i][4]+"\",\""+data[i][3]+"\","+data[i][7]/1000+","+data[i][8]/1000+","+data[i][9]/1000+","+data[i][6]+","+data[i][10]/1000+",\""+data[i][22]+"\",\""+data[i][23]/1000+"\")'>��ϸ</a>";
		      				
		      				//����Υ��״̬ ������ ���˷ѣ�����ɹ� ����  ����״̬ ���������˷ѣ�����ɹ�
	      					if(data[i][17]!=101 && data[i][17]!=4 && gdwoState!=101 && gdwoState!=4){
								str=str+"&nbsp;<a href='javascript:funRefund(22,\""+gdID+"\","+(parseFloat(data[i][23]))+","+data[i][0]+","+interfaceId+")'>�˷�</a>";		      					
	      					}
	      					if(data[i][17]!=101 && gdwoState!=101)
	      					{
								str=str+"<br/>"+
								"<select style='width:100px;' onchange='funWorkdOrderStateUpdate(1,\""+gdID+"\",this,"+data[i][0]+","+interfaceId+")'>"+
									"<option value='100' "+(data[i][17]==100?"selected":"")+">������</option>"+
									"<option value='3' "+(data[i][17]==3?"selected":"")+">������</option>"+
									"<option value='2' "+(data[i][17]==2?"selected":"")+">��֧��</option>"+
									"<option value='1' "+(data[i][17]==1?"selected":"")+">�µ��ɹ�δ֧��</option>"+
									"<option value='4' "+(data[i][17]==4?"selected":"")+">����ɹ�</option>"+
									"<option value='5' "+(data[i][17]==5?"selected":"")+">����ʧ��</option>"+
									"<option value='6' "+(data[i][17]==6?"selected":"")+">�����쳣</option>"+
									"<option value='7' "+(data[i][17]==7?"selected":"")+">ȡ��</option>"+
									"<option value='101' "+(data[i][17]==101?"selected":"")+">���˷�</option>"+
								"</select>";
							}
		      				str=str+"</td></tr>";
		      			}
		      			$("#"+gdID+"_table").html(str);
		      		}
		      	}
			)
			document.getElementById(gdID+"_tr").style.display="";  
		}else if($("#"+gdID+"_updown").text()=="����"){
			$("#"+gdID+"_updown").text("չ��");
			$("#"+gdID+"_table").text("");
			document.getElementById(gdID+"_tr").style.display="none";
		}else{
			alert("δ֪״̬");
			return;
		}
	}
	//��ʾ��Ϣ
	function funshowInfo(event,a,b,c,d,aa,bb,cc,dd,ee,tiaoma,zmoney)
	{
		document.getElementById("showInfoMessage").innerHTML="";
		document.getElementById("showInfoMessage").style.display="block";
		document.getElementById("showInfoMessage").style.top=(event.clientY-70)+"px";
		document.getElementById("showInfoMessage").style.left=(event.clientX-25-180)+"px";
		
		var sb0="<table cellpadding=5 cellspacing=0 border=0 style='width:100%;font-size:13px;'> "+
		"<tr style='height:25px;'s><td style='text-align:right;' colspan=2><a href='javascript:funclose()' style='fonts-zie:12px;'>�ر�</a>&nbsp;</td></tr> "+
		"<tr style='height:22px;'><td style='width:50px;text-align:right;'>����:</td><td style='text-align:left;'>"+a+"</td></tr> "+
		"<tr style='height:22px;'><td style='width:50px;text-align:right;'>ʱ��:</td><td style='text-align:left;'>"+b+"</td></tr> "+
		"<tr style='height:22px;'><td style='width:50px;text-align:right;'>�ص�:</td><td style='text-align:left;'>"+c+"</td></tr> "+
		"<tr style='height:22px;'><td style='width:50px;text-align:right;'>�۷�:</td><td style='text-align:left;'>"+dd+"</td></tr> "+
		"<tr style='height:22px;'><td style='width:50px;text-align:right;'>����:</td><td style='text-align:left;'>"+aa+"Ԫ</td></tr> ";
		sb0=sb0+"<tr style='height:22px;'><td style='width:50px;text-align:right;'>���ɽ�:</td><td style='text-align:left;'>"+bb+"Ԫ</td></tr> "+
		"<tr style='height:22px;'><td style='width:50px;text-align:right;'>������:</td><td style='text-align:left;'>"+cc+"Ԫ</td></tr> "+
		"<tr style='height:22px;'><td style='width:50px;text-align:right;'>�����:</td><td style='text-align:left;'>"+ee+"Ԫ</td></tr> ";
		sb0=sb0+"<tr style='height:22px;'><td style='width:50px;text-align:right;'>�ܷ���:</td><td style='text-align:left;'>"+zmoney+"Ԫ</td></tr> ";
		//if("${userSession.usersite}"==382){//��ʯ�������
		//	sb0=sb0+"<tr style='height:22px;'><td style='width:50px;text-align:right;'>����:</td><td style='text-align:left;'>39076("+zmoney+"��)</td></tr> ";
		//}
		sb0=sb0+"<tr style='height:22px;'><td style='width:50px;text-align:right;position:relative;'><div style='font-size:12px;position:absolute;top:7px;right:5px;'>����:</div></td><td style='text-align:left;'>"+d+"</td></tr></table>";
		sb0=sb0+"<div style='position:absolute;right:-12px;top:60px;width:0;height:0;border-top:10px solid transparent;border-LEFT: 12px solid #FF9E4d;border-bottom:10px solid transparent;'></div>";
		document.getElementById("showInfoMessage").innerHTML=sb0;
	}
	//�ر� ��ʾ��ϸ��Ϣ
	function funclose()
	{
		document.getElementById("showInfoMessage").style.display="none";
	}
	
	//�µ�  type 33����  money�µ����  
	/**
	function funUpDown(type,gdid,money){
		var bool=false;
		if(type==33){
			if(confirm("��ȷ��ҪΪ ����ID: "+gdid+" ����,���׽��: "+(money/1000)+"Ԫ �µ���!")){
				bool=true;
			}else{
				return ;
			}
		}else{
			alert("δ֪��������!");
			return ;
		}
		$("#"+gdid+"_abcd").text("");
		if(document.getElementById("load"))
		{
			document.getElementById("removeload").innerHTML="";
		}
		funLoad();
		if(bool && (parseInt(money)>1000)){
			$.post(
		    	"<%=path %>/business/mobileCharge.do?method=catStateHandle",
		      	{
		      		typeA:type,
		      		gdid:gdid,
		      		wzid:"",
		      		money:money
		      	},
		      	function (obj){
		      		alert(obj.msg);
		      		funSelect();
		      	}
			)			
		}
		return ;
	}
	*/
	//�˷�  type 11���� 22Υ�¼�¼    id   money�˷ѽ��  interfaceID�ӿ�id
	function funRefund(type,gdid,money,wzid,interfaceID){
		var bool=false;
		if(type==11){
			if(confirm("��ȷ��ҪΪ ����ID: "+gdid+" �˷� "+(parseInt(money/1000))+"Ԫ ��!")){
				bool=true;
			}else{
				return ;
			}
		}else if(type==22){
			if(confirm("��ȷ��ҪΪ ����ID: "+gdid+" , Υ��ID: "+wzid+" �˷� "+(parseInt(money/1000))+"Ԫ ��!")){
				bool=true;
			}
		}else{
			alert("δ֪�˷�����!");
			return ;
		}
		if(bool && (parseInt(money)>1000)){
			$.post(
		    	"<%=path %>/business/mobileCharge.do?method=catStateHandle",
		      	{
		      		typeA:type,
		      		gdid:gdid,
		      		wzid:wzid,
		      		money:money,
		      		inter:interfaceID
		      	},
		      	function (obj){
		      		alert(obj.msg);
		      		funSelect();
		      	}
			)			
		}
		return ;
	}
	//����״̬�޸�  type 0���� 1Υ�¼�¼  id   this �޸ĺ�״̬    interfaceID �ӿ�id
	function funWorkdOrderStateUpdate(type,gdid,className,wzid,interfaceID){
		var bool=false;
		var state=-1;
		if(type==0){
			if(confirm("��ȷ��Ҫ�޸� ����ID: "+gdid+" ״̬��!")){
				bool=true;
				state=className.value;
			}else{
				return ;
			}
		}else if(type==1){
			if(confirm("��ȷ��Ҫ�޸� ����ID: "+gdid+" , Υ��ID: "+wzid+" ״̬��!")){
				bool=true;
				state=className.value;
			}else{
				return ;
			}
		}else{
			alert("δ֪��������!");
			return ;
		}
		if(bool && state!=-1){
			$.post(
		    	"<%=path %>/business/mobileCharge.do?method=catStateHandle",
		      	{
		      		typeA:type,
		      		gdid:gdid,
		      		wzid:wzid,
		      		state:state,
		      		inter:interfaceID
		      	},
		      	function (obj){
		      		alert(obj.msg);
		      		funSelect();
		      	}
			)			
		}
		return ;
	}
	//����excel
	function funExcel(){
		document.getElementById("for").action="<%=path %>/business/mobileCharge.do?method=catMessageList&wheretype=1";
		document.getElementById("for").submit();
		return;
	}
</script>
	</head>
	<body style="margin: 0px 0px 0px 0px; padding: 0px 0px 0px 0px;padding-top:10px;">
		<form action="<%=path %>/business/mobileCharge.do?method=catMessageList"
			method="post" id="for">
			<ul style="padding-left: 10px; height: 32px; margin-bottom: 0px;">
				<li>
					<input onclick="funSelect()" type="button"
						style="width: 85px; height: 32px; border: 0px; color: #fff; font-weight: bold; background: url(<%=path%>/images/button_out.png) no-repeat bottom; cursor: pointer;"
						value="��ѯ" />
					<c:if test="${(sessionScope.userSession.roleType == 0)}">
						<input onclick="funExcel()" type="button"
							style="width: 85px; height: 32px; border: 0px; color: #fff; font-weight: bold; background: url(<%=path%>/images/button_out.png) no-repeat bottom; cursor: pointer;"
							value="����" />
					</c:if>
				</li>
			</ul>
			<div
				style="height: 100px; border: 1px solid #cccccc; margin-top: 0px; padding: 0px 0px 0px 0px;padding-top:10px;">
				<ul
					style="margin: 0px 0px 0px 0px; padding: 0px 0px 0px 0px; text-align: center;">
					<li style="list-style-type: none; font-size: 13px;height:40px;line-height:40px;">
						��ʼ����:
						<input style="width: 120px; height: 25px;" class="Wdate"
							name="inDate" type="text" onClick="WdatePicker()"
							value="${inDate }">
						&nbsp; ��������:
						<input style="width: 120px; height: 25px;" class="Wdate"
							name="endDate" type="text" onClick="WdatePicker()"
							value="${endDate }">
						&nbsp; ����״̬:
						<select style="width: 120px; height: 25px;" name="state"
							id="state">
							<option value="">
								-- ��ѡ�� --
							</option>
							<option value="100">
								 ������
							</option>
							<option value="3">
								������
							</option>
							<option value="2">
								��֧��
							</option>
							<option value="1">
								�µ��ɹ�δ֧��
							</option>
							<option value="4">
								����ɹ�
							</option>
							<option value="5">
								����ʧ��
							</option>
							<option value="6">
								�����쳣
							</option>
							<option value="7">
								ȡ��
							</option>
							<option value="101">
								���˷�
							</option>
						</select>
					</li>
					<li style="list-style-type: none; font-size: 13px;height:40px;line-height:40px;">
						���ƺ�:
						<select style="height:25px;" id="plateNumberA" name="plateNumberA" >
							<option value="��">��</option>
						</select>
						<input style="width: 120px; height: 25px;" value="${plateNumberB}" name="plateNumberB"/>
						&nbsp;
						��������:
						<input style="width: 120px; height: 25px;" value="${catName}" name="catName"/>
						&nbsp;
						<c:if test="${(sessionScope.userSession.roleType == 0)}">
							������˺�:
							<input style="width: 120px; height: 25px;" value="${userName}" name="userName"/>
							&nbsp;
							�ӿ�:
							<select style="height:25px;width:50px;" id="interID" name="inter" >
								<option value="-22">ȫ��</option>
								<!-- 
								<option value="1">����</option>
								 -->
								<option value="2">���°�</option>
							</select>
						</c:if>
					</li>
				</ul>
			</div>
			<table border="0" style="width: 100%; font-size: 12px; border: 1px solid #1A7FC3; margin-top: 10px;" cellspacing="0" cellpadding="0">
				<tr style="height: 30px; background-color: #E6EFF6;">
					<th style="text-align: center; border-bottom: 1px solid #1A7FC3; border-right: 1px solid #1A7FC3;"  width=55>
						����ID
					</th>
					<th style="text-align: center; border-bottom: 1px solid #1A7FC3; border-right: 1px solid #1A7FC3;width:55px;">
						����
					</th>
					<th style="text-align: center; border-bottom: 1px solid #1A7FC3; border-right: 1px solid #1A7FC3;width:95px;">
						�绰
					</th>
					<th style="text-align: center; border-bottom: 1px solid #1A7FC3; border-right: 1px solid #1A7FC3;width:70px;">
						���ƺ�
					</th>
					<th style="text-align: center; border-bottom: 1px solid #1A7FC3; border-right: 1px solid #1A7FC3;width:70px;">
						<c:if test="${(sessionScope.userSession.roleType != 0)}">
							ʵ���ܶ�
						</c:if>
						<c:if test="${(sessionScope.userSession.roleType == 0)}">
							ʵ��/Ԥ��
						</c:if>
					</th>
					<th style="text-align: center; border-bottom: 1px solid #1A7FC3; border-right: 1px solid #1A7FC3;width:100px;">
						�µ�����
					</th>
					<th style="text-align: center; border-bottom: 1px solid #1A7FC3; border-right: 1px solid #1A7FC3;width:70px;">
						����״̬
					</th>
					<th style="text-align: center; border-bottom: 1px solid #1A7FC3; border-right: 1px solid #1A7FC3;width:90px;">
						��λ/��λ
					</th>
					<th style="text-align: center; border-bottom: 1px solid #1A7FC3; border-right: 1px solid #1A7FC3;width:90px;">
						ϵͳ���
					</th>
					<th style="text-align: center; border-bottom: 1px solid #1A7FC3;width:150px;">
						����
					</th>
				</tr>
				<c:forEach items="${arrList}" var="arr">
					<tr style="height: 60px;">
						<td  width=55 style="background-color:#8DCDF7;text-align: center; border-bottom: 1px solid #1A7FC3; border-right: 1px solid #1A7FC3;word-wrap:break-word;word-break:break-all;">
							${arr[0]}
						</td>
						<td style="background-color:#8DCDF7;text-align: center; border-bottom: 1px solid #1A7FC3; border-right: 1px solid #1A7FC3;">
							${arr[1]}
						</td>
						<td style="background-color:#8DCDF7;text-align: center; border-bottom: 1px solid #1A7FC3; border-right: 1px solid #1A7FC3;">
							${arr[2]}
						</td>
						<td style="background-color:#8DCDF7;text-align: center; border-bottom: 1px solid #1A7FC3; border-right: 1px solid #1A7FC3;">
							${arr[3]}
						</td>
						<td style="background-color:#8DCDF7;text-align: center; border-bottom: 1px solid #1A7FC3; border-right: 1px solid #1A7FC3;">
							${arr[4]/1000}/${arr[10]/1000}Ԫ
						</td>
						<td style="background-color:#8DCDF7;text-align: center; border-bottom: 1px solid #1A7FC3; border-right: 1px solid #1A7FC3;">
							${arr[5]}
						</td>
						<td style="background-color:#8DCDF7;text-align: center; border-bottom: 1px solid #1A7FC3; border-right: 1px solid #1A7FC3;">
							<c:if test="${arr[6]==100}">������</c:if>
							<c:if test="${arr[6]==3}">������</c:if>
							<c:if test="${arr[6]==2}">��֧��</c:if>
							<c:if test="${arr[6]==1}">�µ��ɹ�δ֧��</c:if>
							<c:if test="${arr[6]==4}">����ɹ�</c:if>
							<c:if test="${arr[6]==5}">����ʧ��</c:if>
							<c:if test="${arr[6]==6}">�����쳣</c:if>
							<c:if test="${arr[6]==7}">ȡ��</c:if>
							<c:if test="${arr[6]==101}">���˷�</c:if>
							<c:if test="${arr[6]!=100 && arr[6]!=3 && arr[6]!=2 && arr[6]!=1 && arr[6]!=4 && arr[6]!=5 && arr[6]!=6 && arr[6]!=7 && arr[6]!=101}">
							δ֪״̬
							</c:if>
						</td>
						<td style="background-color:#8DCDF7;text-align: center; border-bottom: 1px solid #1A7FC3; border-right: 1px solid #1A7FC3;">
							${arr[7]}/${arr[8]}
						</td>
						<td style="background-color:#8DCDF7;text-align: center; border-bottom: 1px solid #1A7FC3; border-right: 1px solid #1A7FC3;">
							${arr[11]}
						</td>
						<td style="background-color:#8DCDF7;text-align: center; border-bottom: 1px solid #1A7FC3; border-right: 1px solid #1A7FC3; line-height:30px;">
							<a href="javascript:showCatMessage('${arr[0]}','${arr[3]}',${arr[6]},${arr[9]})" id="${arr[0]}_updown" name="showMesageNamss">չ��</a>&nbsp;
										<!-- ���ǰ��°� �ӿ� �����µ� -->
										<!-- 
										<c:if test="${arr[6]==100}">
											<c:if test="${arr[9]!=2}">
												<a href="javascript:funUpDown(33,'${arr[0]}',${arr[4]})" id="${arr[0]}_abcd">�µ�</a>&nbsp;
											</c:if>
										</c:if>
										 -->
							 <!-- �����������˷�,�ɹ��ģ�����ʾ �˷� ���� -->
							<c:if test="${arr[6]!=101 && arr[6]!=4}">
								<a href="javascript:funRefund(11,'${arr[0]}',${arr[4]},'',${arr[9]})">�˷�</a>
							</c:if>
							<br/>
							<select style="width:100px;height:20px;" onchange="funWorkdOrderStateUpdate(0,'${arr[0]}',this,'',${arr[9]})">
								<option value="100" <c:if test='${arr[6]==100}'>selected="selected"</c:if> >������</option>
								<option value="3" <c:if test='${arr[6]==3}'>selected="selected"</c:if> >������</option>
								<option value="2" <c:if test='${arr[6]==2}'>selected="selected"</c:if> >��֧��</option>
								<option value="1" <c:if test='${arr[6]==1}'>selected="selected"</c:if> >�µ��ɹ�δ֧��</option>
								<option value="4" <c:if test='${arr[6]==4}'>selected="selected"</c:if> >����ɹ�</option>
								<option value="5" <c:if test='${arr[6]==5}'>selected="selected"</c:if> >����ʧ��</option>
								<option value="6" <c:if test='${arr[6]==6}'>selected="selected"</c:if> >�����쳣</option>
								<option value="7" <c:if test='${arr[6]==7}'>selected="selected"</c:if> >ȡ�� </option>
								<option value="101" <c:if test='${arr[6]==101}'>selected="selected"</c:if> >���˷�</option>
							</select>
						</td>
					</tr>
					<tr style="display:none;" id="${arr[0]}_tr">
						<td colspan=10 >
							<table border="0" style="width: 100%; font-size: 12px; " cellspacing="0" cellpadding="0" id="${arr[0]}_table">
							</table>
						</td>
					</tr>
					<input type="hidden" value="${arr[0]}" name="ssss"/>
				</c:forEach>
				<tfoot>
					<tr height="30">
						<td colspan="10" class="tr_line_top">
							<c:if test="${index!=null}">
								<div style="text-align:right;padding-right:10px;">
									<a href="javascript:funPage(1)">��ҳ</a>
									<a href="javascript:funPage(${index-1})">��һҳ</a>
									<a href="javascript:funPage(${index+1})">��һҳ</a>
									<a href="javascript:funPage(${lastIndex})">βҳ</a>&nbsp;
									��ת&nbsp;<input id="pageId" type="text" style="width:30px" value="${index}" onblur="funPage(this.value)" onkeyup="value=value.replace(/[^\d]/g,'') "/>&nbsp;ҳ
									&nbsp;
									�� ${index} ҳ
								</div>
							</c:if>
						</td>
					</tr>
				</tfoot>
			</table>
			<input type="hidden" value="" name="index" id="indexID"/>
			<input type="hidden" value="${whereStr}" name="where"/>
			<input type="hidden" value="${whereInfo}" name="whereInfo"/>
		</form>
		<div style="display:none;border:1px solid #f8f8f8;width:180px;position:absolute;left:0px;top:0px;background-color:#FFFFE5;border:1px solid #FF9E4d;" id="showInfoMessage"></div> 
		<br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/>
	</body>
</html>