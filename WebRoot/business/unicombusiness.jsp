<%@ page contentType="text/html; charset=gb2312" language="java"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:useBean id="spa" scope="page" class="com.wlt.webm.business.bean.SysPhoneArea"/>
<%
String path = request.getContextPath();
request.setAttribute("areaList",spa.listArea()); 
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>�㶫��ͨ��ֵ</title>
<link href="../css/chongzhi_mobile.css" rel="stylesheet" type="text/css" />
<script src="../js/jquery-1.8.2.min.js" type="text/javascript"></script> 
<script  type="text/javascript" src="../js/util.js"></script>
<script language='javascript' src='../js/jquery.js'></script>
<link href="../css/main_style.css" rel="stylesheet" type="text/css" />
<link href="../css/style.css" rel="stylesheet" type="text/css" />
<script language="JavaScript">
$(function(){
var clipNum = "";
	$("#js_id_mobile").bind("keyup blur",function(){
		if(clipNum != ""){
			if(clipNum.length > 11){
				$(this).val(clipNum.substr(0,11));
			}else{
				$(this).val(clipNum);
			}
		}else{
			$(this).val($(this).val().replace(/\D/g,''));
		}
		clipNum = "";
		var _old = $("#js_id_mobile").val();
		if(_old.length > 3){
			_old = _old.substr(0,3)+" "+_old.substr(3,_old.length);
		}
		if(_old.length > 8){
			_old = _old.substr(0,8)+" "+_old.substr(8,_old.length);
		}
		$("#js_id_mobile").val(_old);
		
	});
	
	$("#js_id_price").find("a").bind("click",function(){
		var _dv = $(this).attr("data-value");
		$("#js_id_price").find("a").attr("class","charge-money-sum");
		$(this).attr("class","charge-money-sum on");
		$("#js_id_money_show").text(_dv);
	});
})
showMessage("${mess}");
	function check(){  
		with(document.forms[0]){
			if(tradeObject.value.length==0){
				alert("�绰���벻��Ϊ��");
				tradeObject.focus();
				return false;
			}
			if(!isDigit(tradeObject.value.replace(/[ ]/g,""))){
				alert("�绰�����ʽ����");
				tradeObject.select();
				return false;
			}
			if(tradeObject.value.replace(/[ ]/g,"").length < 11){
			       alert("�ֻ�����λ��һ��Ҫ����11λ");
			      return false;
			}
			if(tradeObject.value.replace(/[ ]/g,"").substring(0,1)=='1'){
			   if(tradeObject.value.replace(/[ ]/g,"").length !=11){
			      alert("�ֻ�����λ��һ��Ҫ����11λ");
			       return false;
			    }
			}
			 if(confirm("ȷ��Ϊ���룺"+ tradeObject.value+" �ɷѣ�"+js_id_money_show.innerText+"Ԫ")){
		    Button.disabled=true;
			target = "_self";
			 $("#btnChag").hide();
			 $("#btnBak").hide();
             $("#bfBox").show();
			document.forms[0].action="../unicom/UnicomAction.do?method=unicomCharge&money="+js_id_money_show.innerText;
	    	submit();  
		} }
	}
	function getFocus(){
		document.forms[0].tradeObject.focus();
	}
</script>
</head>
<body text-align:center>
<div id="tabbox">
  <div id="tabs_title">
    <ul class="tabs_wzdh">
      <li>�㶫��ͨ��ֵ</li></ul>
  </div>
</div>
<input type="hidden" id="rootPath" value="<%=path %>"/>
    <div class="mobile-charge" >
<form action="" method="post" class="form">
<input type="hidden" name="prodId" id="prodId"/>
        <fieldset>
              <div class="form-line" name="dv1">
            <label class="label" for="mobile-num">�ֻ����룺</label>
            <div class="element clearfix">
             <div class="mobile-num"><input type="text" id="js_id_mobile" name="tradeObject" style="font-family: '����'; font-size: 35px; width: 280px; height: 45px; font-weight: 900;"" maxlength="13"></div>

            </div>
          </div>
          <div class="form-line high-text charge-money" name="dv1">
            <label class="label" for="charge-money">��ֵ��</label>
            <div class="element">
              <div id="js_id_price">
 				  <a class="charge-money-sum" data-value='30' href="javascript:void(0);" hidefocus="true" data-money="30" data-lidou='30' data-vip='0' data-grow='50'><span>30Ԫ</span></a>
                  <a class="charge-money-sum" data-value='50' href="javascript:void(0);" hidefocus="true" data-money="50" data-lidou='50' data-vip='0' data-grow='50'><span>50Ԫ</span></a>
                  <a class="charge-money-sum on" data-value='100' href="javascript:void(0);" hidefocus="true" data-money="100" data-lidou='100' data-vip='0' data-grow='50'><span>100Ԫ</span></a>
                  <a class="charge-money-sum" data-value='300' href="javascript:void(0);" hidefocus="true" data-money="300" data-lidou='300' data-vip='0' data-grow='50'><span>300Ԫ</span></a>
                  <a class="charge-money-sum" data-value='500' href="javascript:void(0);" hidefocus="true" data-money="500" data-lidou='500' data-vip='0' data-grow='50'><span>500Ԫ</span></a>
              </div>
            </div>
          </div>

          <div class="form-line" name="dv1">
            <div class="element"  style="display: none">
              <div class="form-font clearfix"><span name="price" class="highlight font-16" id="js_id_money_show">100</span>Ԫ</div>

            </div>
<div class="field-item_button_m" id="btnChag"><input type="button" name="Button" value="��ֵ" class="field-item_button" onclick="check();"></div>
<div class="field-item_button_m" id="btnBak"><input type="reset" name="Submit2" value="��д" class="field-item_button"></div>
<div class="field-item_button_m" id="bfBox" style="display:none"><div class="bufferBox">
    	<img class="bufferImg" src="<%=path %>/images/loading_16.gif">
        <label class="bufferText">���ڳ�ֵ�����Եȡ���</label>`
    </div></div>
          </div>

        </fieldset>
      </form>
    </div><br /><br /><br />
 <font style="font-family:'����'; font-size:17px; " color="red">��ӭʹ�ù㶫��ͨ��ֵҵ��ֻ��Թ㶫��ͨ�ֻ��û��������������ֻ���ֵ</font>
</body>
<input type="hidden" id="mess" value="${requestScope.mess }"/>
</html>
