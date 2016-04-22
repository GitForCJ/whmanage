var url ;
var tradeObject;
var info;
var payFee;
var dxtype1;
var dxtype2;
var dxtype3;
var ydfee;
var chzhi;
var tzfee;
var urltemp;
var number = 0;
var max = 30;
var padde;
var query;
$(document).ready(function(){
	 dxtype1 = $("#dxtype01");//  综合缴费    单一号码缴费       单一宽带缴费
	 dxtype2 = $("#dxtype02");
	 dxtype3 = $("#dxtype03");
	 ydfee = $("#ydfee");
	 chzhi  =  $("#chzhi");
	 tzfee  = $("#tzfee");
    tradeObject = $("#tradeObject");
     info = $("#info");
     padde = $("#padde");
     query = $("#query");
     padde.hide();
     dxtype1.hide();
     dxtype2.hide();
     dxtype3.hide();
     ydfee.hide();
     chzhi.hide();
     tzfee.hide();
	info.hide();
	query.hide();
	 info =info.css("border","1px solid black").width("200px").height("30px")
	.css("position","absolute").css("z-index","99").css("background","#e0ffff").css("color","blue").css("font-size","x-large");
	tradeObject.keypress(function(event){
		var mayevent = event || window.event;
		var keycode = mayevent.keyCode;
		info.show();
		//数字键盘为数字的字符，按键为数字的字符，以及del,space键,adsl键码值
		if((keycode >=48 && keycode <= 57) || (keycode >= 96 && keycode <= 105) ||  keycode == 8 || keycode == 46 || keycode == 65 || keycode == 68 || keycode == 83 || keycode == 76 ){
	         setTimeout(showcharginfo,500);
		}
		
	});
	tradeObject.keyup(function(event){
		var mayevent = event || window.event;
		var keycode = mayevent.keyCode;
		if(keycode == 13){
			objectvalue = $(this).val();
			if(objectvalue.length==0){
				alert("电话号码不能为空");
				objectvalue.focus();
				return false;
			}else if(!(/^[0-9]+$/.test(objectvalue) || /^(adsl){1}[0-9]+$/.test(objectvalue.toLowerCase()))){
				alert("手机号码格式有误，请重新输入");
				return false;
			}
			
			tradeObject.blur();
			info.hide();
		}
	});
	
	});

function check(){  
	objectvalue = tradeObject.val();
	if(objectvalue.length==0){
		alert("电话号码不能为空");
		objectvalue.focus();
		return false;
	}else if(!(/^[0-9]+$/.test(objectvalue) || /^(adsl)[0-9]+$/.test(objectvalue.toLowerCase()))){
		alert("手机号码格式有误，请重新输入");
		return false;
	}	
	if(type == "yd"){
		payFee=$("#ydfee select").val();
	}else if(type == "um"){
		payFee=$("#tzfee select").val();
	}else{
			inputtemp = $("input[name='numType']:checked");
			if(inputtemp.attr("checked") != undefined){
				var numType = inputtemp.val();
				if(url == null || url == ''){
					url = urltemp;
				}
				url = url +"&&numType="+numType;
		}
	}
	if(type == "yd" || type == "um"){
		  if(confirm("确认为号码："+ objectvalue+" 缴费："+payFee+"元")){
			  urlt = url+"&&payFee="+payFee;
			     dxtype1.hide();
			     dxtype2.hide();
			     dxtype3.hide();
			     query.hide();
			     ydfee.hide();
			     chzhi.hide();
			     tzfee.hide();
				 info.hide();
				 padde.show();
				 $("#tradeinfo").hide();
				 start();
			  window.location.href=urlt;
	      }
	}else{
		 if(confirm("确认为号码："+ objectvalue)){
		     dxtype1.hide();
		     dxtype2.hide();
		     dxtype3.hide();
		     query.hide();
		     ydfee.hide();
		     chzhi.hide();
		     tzfee.hide();
			 info.hide();
			 padde.show();
			 $("#tradeinfo").hide();
			 start();
		  window.location.href=url;
		 }
	}

	  url = '';
}
function infomes(){
	var offset = tradeObject.offset();
	info.html(tradeObject.val());
	info.css("left",offset.left+"px").css("top",offset.top-tradeObject.height()-12+"px");
}
function showcharginfo(){
	$.get("../operation/phonepay.do?method=querytype&&tradeObject="+tradeObject.val(),"null",function(data){
		type = data;
		if(data=="yd"){
		     dxtype1.hide();
		     dxtype2.hide();
		     dxtype3.hide();
		     query.hide();
		     tzfee.hide();
		     ydfee.show();
		     chzhi.show();
		     url="../operation/phonepay.do?method=cmccPayBill&&tradeObject="+tradeObject.val();
		}else if(data=="um"){
			 dxtype1.hide();
		     dxtype2.hide();
		     dxtype3.hide();
		     ydfee.hide();
		     query.hide();
		     chzhi.show();
		     tzfee.show();
		     url="../operation/phonepay.do?method=ucPayBillQc&&tradeObject="+tradeObject.val();
		}else if(data=="dx03"){
			 dxtype1.show();
		     dxtype2.show();
		     dxtype3.show();
		     query.show();
		     ydfee.hide();
		     chzhi.hide();
		     tzfee.hide();
		    dxtype1.children("input").eq(0).attr("disabled","disabled");
		    dxtype2.children("input").eq(0).attr("disabled","disabled");
		    dxtype3.children("input").eq(0).removeAttr("disabled");
		    dxtype3.children("input").eq(0).attr("checked","checked");
		     url="../operation/phonepay.do?method=queryBill&&tradeObject="+tradeObject.val()+"&&dxqc=1";
		}else{
			 dxtype1.show();
		     dxtype2.show();
		     dxtype3.show();
		     query.show();
		     ydfee.hide();
		     tzfee.hide();
		     chzhi.hide();
		     dxtype1.children("input").eq(0).removeAttr("disabled");
		     dxtype2.children("input").eq(0).removeAttr("disabled");
		     dxtype1.children("input").eq(0).attr("checked","checked");
			 dxtype2.children("input").eq(0).attr("checked","checked");
			 dxtype3.children("input").eq(0).attr("disabled","disabled");
		     url="../operation/phonepay.do?method=queryBill&&tradeObject="+tradeObject.val()+"&&dxqc=1";;
		}
		urltemp = url ;
		infomes();
		setInterval(infomes,100);
	});
}

function start()
{
  number++;

  if (number > max)
  {
    for (var i = 1; i <= max; i++)
    {
      document.getElementById("span" + i).style.backgroundColor = "transparent";
    }
    number = 0;
  }
  else
  {
    document.getElementById("span" + number).style.backgroundColor = "green";
  }

  setTimeout("start()", 500);
}

function reset(){
	tradeObject.val("");
	info.hide();
}

