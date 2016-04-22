<%@ page language="java" contentType="text/html; charset=gb2312"%>
<%@ page import="java.util.*"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<jsp:useBean id="tpbean" scope="session"
	class="com.wlt.webm.business.bean.TpBean" />
<%
	List groupsList = tpbean.getFlowGroups();//��ȡ����Ӷ��������
	StringBuffer strs = new StringBuffer();
	strs.append("��ѡ��[]");
	for (Object tmp : groupsList) {
		String[] temp = (String[]) tmp;
		strs.append("|" + temp[0] + "[" + temp[0] + "]");
	}
	request.setAttribute("groups", strs.toString());
	
	String[] a=new String[]{"����#0","��ͨ#2"};
	String[] b=new String[]{"ȫ��#0","ʡ��#1"};
	
	String ListGreoups=request.getParameter("groups");
%>
<html>
	<head>
		<title>���ͨ</title>
		<script type="text/javascript" src="../js/jquery-1.8.2.min.js"></script>
		<script type="text/javascript" src="../js/xml_cascade.js"></script>
		<link href="../css/main_style.css" rel="stylesheet" type="text/css" />
		<link href="../css/selectCss.css" rel="stylesheet" type="text/css" />
		<script src="../js/selectCss.js" type="text/javascript"></script>
<SCRIPT type=text/javascript>
	function check(){
		var cm_groups=document.getElementById("cm_groups").value;
		if(cm_groups==""){
			alert("��ѡ��Ӷ��������");
			return ;
		}
		var value=document.getElementsByName("value");
		var strs="";
		var bool=false;
		for(var i=0;i<value.length;i++){
			if(value[i].value!=""){
				if(!/^[0-9]+(\.[0-9]+){0,1}$/.test(value[i].value)||/^0+[0-9]+(\.[0-9]+){0,1}$/.test(value[i].value)){
			       	 alert("��������ȷ������");
			       	 value[i].focus(); 
			       	 return ;
		        }else if(value[i].value.split(".").length>1 && value[i].value.split(".")[1].length>2){
		    	    alert("���׽���С����ֻ������λ��");
		    	    value[i].focus(); 
			    	return ;
			    } else{
			    	strs=strs+document.getElementById("t_"+value[i].id).value+"@";
			    	strs=strs+document.getElementById("c_"+value[i].id).value+"@";
			    	strs=strs+value[i].value+"#";
		        	bool=true;
		        }
			}
		}
		if(bool==false){
			alert("������Ӷ��!");
			return ;
		}
		document.getElementById("checkCode").value="���Ժ�...";
		document.getElementById("checkCode").disabled="disabled";
		document.getElementById("strs").value=strs;
		document.forms[0].target="_self";
		document.forms[0].submit();
		return ;
	}
</SCRIPT>
	</head>
	<body>
		<div id="popdiv">
			<span id="sellist"></span>
			<div style="clear: both"></div>
		</div>
		<form method="post" action="../business/prod.do?method=FlowUpdate">
			<div class="pass_main">
				<div class="pass_title_1">
					�������Ӷ��
				</div>
				<div class="pass_list">
					<ul>
						<li>
							<strong>Ӷ��������:</strong>
							<span>
								<div class="sub-input">
									<a class="sia-2 selhover" id="sl-cm_groups"
										href="javascript:void(0);" style="width: 200px;">��ѡ��</a>
								</div> <input style="width: 200px;" name="cm_groups" id="cm_groups"
									defaultsel="${requestScope.groups }" type="hidden" value="" />
							</span>
							<li >
								<strong>Ӷ������:</strong>
								<span>&nbsp;</span>
							</li>
							<%
								int con=0;
								for(int i=0;i<a.length;i++){
									for(int k=0;k<b.length;k++){
									con++;
									%>
										<li>
											<strong>&nbsp;</strong>
											<span>
												<input type="text" value="<%=a[i].split("#")[0]%>" disabled style="width: 100px;" />
												<input type="hidden"  value="<%=a[i].split("#")[1]%>" id="t_<%=con %>"/>
												&nbsp;
												<input type="text" value="<%=b[k].split("#")[0]%>" disabled style="width: 100px;" />
												<input type="hidden"  value="<%=b[k].split("#")[1]%>"  id="c_<%=con %>"/>
												&nbsp;
												<input type="text" name="value" value="" style="width: 50px;"  id="<%=con %>"/>
											</span>
										</li>
									<%
									}
								}
							 %>
						
					</ul>
				</div>
				<div class="field-item_button_m">
					<input type="button" value="ȷ��" id="checkCode"  onclick="check()"
						class="field-item_button" />
					<input type="button" value="ȡ��" id="checkCode1"
						onClick="javascript:history.go(-1)" class="field-item_button" />
				</div>
			</div>
			<input type="hidden" name="groups" value="<%=ListGreoups %>"/>
			<input type="hidden" name="flag" value="add"/>
			<input type="hidden" name="strs" id="strs" value=""/>
		</form>
	</body>
</html>