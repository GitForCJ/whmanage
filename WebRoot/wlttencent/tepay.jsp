<%@ page contentType="text/html; charset=gb2312" language="java"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>�Ƹ�ͨ</title>
<link href="../css/chongzhi_mobile.css" rel="stylesheet" type="text/css" />
<script src="../js/jquery-1.8.2.min.js" type="text/javascript"></script> 
<script  type="text/javascript" src="../js/util.js"></script>
<script language='javascript' src='../js/jquery.js'></script>
<link href="../css/main_style.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">

	function payFrm()
	{
     	var val=directFrm.order_price.value;
     	 //   var minFee =directFrm.minTransferFee.value;
		//	var maxFee = directFrm.maxTransferFee.value;
		//	var hc = directFrm.handlingCharge.value;
					if (directFrm.order_price.value==""||directFrm.order_price.value==0)
		{
			alert("���ѣ�����дת�˽�");
			directFrm.order_price.focus();
			return false;
		} 
//			if(parseFloat(val) - 50000>0){
	//			alert("ת�˽�����С��50000Ԫ��");
	//			return false;
	//		}
        if(parseFloat(val)<1){
				//if(hc.value == 1){
					alert("ת�˽���С��1Ԫ��");
					return false;
				//} 
   			}
     	if(!/^[0-9]+(\.[0-9]+){0,1}$/.test(val)||/^0+[0-9]+(\.[0-9]+){0,1}$/.test(val)){
	        alert("��������ȷ������");
	        return false;
            }
		if(directFrm.order_price.value.split(".")[1].length>2){
     	    alert("���׽���С����ֻ������λ��");
		    return false;
	    } 


	    document.forms[0].OK.disabled=true;
	    document.forms[0].OK.value="���Ժ�...";
		return true;
	}
	
	function keydownEvent(){
	if(event.keyCode==13){
	event.returnValue=false;
	}
	}
</script>
</head>
<body>
<div id="tabbox">
  <div id="tabs_title">
    <ul class="tabs_wzdh">
      <li>�Ƹ�ͨת��</li></ul>
  </div>
</div>
    <div class="mobile-charge" >
<form action="tenpayconfirm.jsp" method="post" name="directFrm" onSubmit="return payFrm();">
        <fieldset>
               <div class="pass_list" style="margin-left: 150">
   	  <ul>
            <li style="font-size: 12px;"><strong  style="margin-top:10px;">ת�ʽ��</strong><span><img src="<%=path%>/images/caifutong.jpg" style="border:1px solid #cccccc;width:170px;height:45px;"/></span></li>
            <li style="font-size: 12px;"><strong>ת���</strong><span><input style="font-size: 20px;height: 30px;width:80px;color: blue;" name="order_price" type="text" onkeyup="if(isNaN(value))execCommand('undo')" onafterpaste="if(isNaN(value))execCommand('undo')" onkeydown="keydownEvent()"/> Ԫ
</span></li>
        </ul>
    </div>
</fieldset>
<div style="margin-left: 20px;" />
<input type="submit" value="ȷ��" name="OK" class="field-item_button" onclick="check();" />
<input type="reset" value="��д" id="checkCode" onClick="validate();" style="margin-left: 10px;" class="field-item_button" onclick="check();" />
</div>
        
      </form>
    </div>
 <div style="font-size: 13px; color:#333333; red;margin-left: 150px;clear:both;">

</div>
<div style="padding-left:40px;padding-top:10px;margin-left: 20px;font-size: 13px; color: #FF6600;font-weight:bold;border:1px solid #FF9E4d;background-color: #FFFFE5;">
<ul>
<li style="list-style:none;">1��֧��ͨ������������Ƹ�ͨ�˻������ȡ�</li>
<li style="list-style:none;">&nbsp;</li>
<li style="list-style:none;">2�����׽�����2000Ԫ/�ʿ�1Ԫ�����ѣ����ڵ���2000Ԫ/����ѡ�</li>
<li style="list-style:none;">&nbsp;</li>
<li style="list-style:none;">3�����ʵʱ���ˡ�</li>
<li style="list-style:none;">&nbsp;</li>
<li style="list-style:none;">4�����ÿ�֧�֣��й��������С��������С��й��������С��й����С��ַ����С��㷢���С��й��������С��й�������С���ҵ���С���ͨ���С���������,���ÿ�֧�����ʽ����ڵ���2000Ԫ��������ȫ�⡣</li>
<li style="list-style:none;">&nbsp;</li>
<li style="list-style:none;">5�����ÿ�֧��������������߼�����֧��Ȩ���йء�</li>
<li style="list-style:none;">&nbsp;</li>
<li style="list-style:none;">6����ҵ����֧�֣��й��������С��������С��й��������С��й�ũҵ���С��ַ����С�������С�</li>
<li style="list-style:none;">&nbsp;</li>
</ul>
</div>
</body>
</html>
