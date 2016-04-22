// JavaScript Document
$(document).ready(function() {
var mark = 0;
var locaUrl = window.location.href;
var redUrl = locaUrl.substring(locaUrl.lastIndexOf("/")+1);
if(redUrl.indexOf("mark") != -1){
	mark = redUrl.split("?")[1].split("=")[1];
}
var _resutlUrl = redUrl.split("jsp")[0]+"jsp";
	jQuery.jqtab = function(tabtit,tab_conbox,shijian) {
		$(tab_conbox).find("li").hide();
		$(tabtit).find("li:first").addClass("thistab").show(); 
		$(tab_conbox).find("li:first").show();
		$(tabtit).children().eq(mark).addClass("thistab").siblings("li").removeClass("thistab"); 
		$(tab_conbox).children().eq(mark).show().siblings().hide();
	
		$(tabtit).find("li").bind(shijian,function(){
		var activeindex = $(tabtit).find("li").index(this);
		location.href=_resutlUrl+"?mark="+activeindex;
		  //$(this).addClass("thistab").siblings("li").removeClass("thistab"); 
			//var activeindex = $(tabtit).find("li").index(this);
			//$(tab_conbox).children().eq(activeindex).show().siblings().hide();
			return false;
		});
	
	};
	/*调用方法如下：*/
	$.jqtab("#tabs","#tab_conbox","click");
	
	$.jqtab("#tabs2","#tab_conbox2","mouseenter");
	
});