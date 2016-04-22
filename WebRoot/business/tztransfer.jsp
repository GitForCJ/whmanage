<%@ page contentType="text/html; charset=gb2312" language="java"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="java.util.*" %>
<html>
<head>
<title>电子代办平台PC系统</title>
<link href="../css/chongzhi_mobile.css" rel="stylesheet" type="text/css" />
<script type='text/javascript' src="../js/chongzhi_app_base.js"></script>
<script  type="text/javascript" src="../js/chongzhi_index.js"></script>
<script  type="text/javascript" src="../js/chongzhi_telephone.js"></script>
<script  type="text/javascript" src="../js/util.js"></script>
<link href="../css/style.css" rel="stylesheet" type="text/css" />
<link href="../css/main_style.css" rel="stylesheet" type="text/css" />
<script language="JavaScript">
	showMessage("${mess}");
	
	function check(){ 
		with(document.forms[0]){
			alert(js_id_money_show.innerText); 
			if(tradeObject.value.length==0){
				alert("电话号码不能为空");
				tradeObject.focus();
				return false;
			}
		//	if(!isDigit(tradeObject.value)){
		//		alert("电话号码格式不对");
				
		//		tradeObject.select();
		//		return false;
		//	}
			if(tradeObject.value.substring(0,1)=='0'){
			    if(tradeObject.value.length < 10){
			       alert("电话号码位数一定要大于或等于10位");
			      return false;
			    }
			}
		//	if(tradeObject.value.substring(0,1)=='1'){
		//	   if(tradeObject.value.length !=11){
			//      alert("手机号码位数一定要等于11位");
		//	       return false;
	//		    }
//			}
		  if(confirm("确认为号码："+ tradeObject.value+" 缴费："+js_id_money_show.innerText+"元")){
		  Button.disabled=true;
          target="_self";
          document.forms[0].action="../operation/tzphonepay.do?method=tzFill&tztype=01012&money="+js_id_money_show.innerText;
          alert( document.forms[0].action);
          submit(); 
      }
		//	target = "_self";
	    //	submit();  
		} 
	}
	function getFocus(){
		document.forms[0].tradeObject.focus();
	}
</script>
</head>
<body>
   <div class="mobile-charge">
<form action="" method="post" class="form">
        <fieldset>
          <div class="form-line">
            <label class="label" for="mobile-num">手机号码：</label>
            <div class="element clearfix">
             <div class="mobile-num"><input type="text" id="js_id_mobile" name="tradeObject" class="text high ime-disabled on gray" value="初始化中..."></div>
               <div class="h-select h-select-operate" id="js_id_history_panel"><a href="#none" class="h-selected @LIFE_V2.MOBILE.INDEX.HISTORYLIST@" hidefocus="true" id="js_id_history"></a>
               </div>

            </div>
          </div>
          <div class="form-line high-text charge-money">
            <label class="label" for="charge-money">充值金额：</label>
            <div class="element">
              <div id="js_id_price">
                  <a class="charge-money-sum" data-value='10' href="#" hidefocus="true" data-money="10" data-lidou='10' data-vip='0' data-grow='50'><span>10元</span></a>
                  <a class="charge-money-sum" data-value='20' href="#" hidefocus="true" data-money="20" data-lidou='20' data-vip='0' data-grow='50'><span>20元</span></a>
                  <a class="charge-money-sum" data-value='30' href="#" hidefocus="true" data-money="30" data-lidou='30' data-vip='0' data-grow='50'><span>30元</span></a>
                  <a class="charge-money-sum on" data-value='50' href="#" hidefocus="true" data-money="50" data-lidou='50' data-vip='0' data-grow='50'><span>50元</span></a>
                  <a class="charge-money-sum" data-value='100' href="#" hidefocus="true" data-money="100" data-lidou='100' data-vip='0' data-grow='50'><span>100元</span></a>
                  <a class="charge-money-sum" data-value='300' href="#" hidefocus="true" data-money="300" data-lidou='300' data-vip='0' data-grow='50'><span>300元</span></a>
              </div>
            </div>
          </div>

          <div class="form-line">
            <label class="label">优惠价：</label>
            <div class="element">
              <div class="form-font clearfix"><span name="price" class="highlight font-16" id="js_id_money_show">50</span>元</div>
              </div>
          </div>
          
          <div class="qb_main">
    	 <form id="validator-form">
	     <div class="field-item"><label>号码归属地：</label><span>湖北ddd</span></div>
         <div class="field-item"><label>号码信息：</label><span>中国移动</span></div>
          <div class="field-item"><label>应付金额：</label><span style="font-size:26px; color:#f00;">29.0</span>&nbsp;&nbsp;&nbsp;&nbsp;元</div>	
    </div>
          <div>
<div class="field-item_button_m"><input type="submit" value="确定" name="OK" class="field-item_button" /></div>
</div>        
        </fieldset>
      </form>
    </div>
</body>
</html>
