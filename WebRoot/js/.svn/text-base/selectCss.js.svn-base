jQuery(function()
{
$(".selhover").each(function(){
	var _top=$(this);
	_top.click(function(){
		var _self=$(this);
		var _sellist=$("#sellist");
		var _popdiv=$("#popdiv");
		var _jiaw=24;
		
		var id=_self.attr("id").replace("sl-","");
		var selval=$("#"+id).attr("defaultsel");
		var sva =selval.split("|");
		var optionm="",onetxt;
		for (loop = 0; loop <sva.length; loop++){
			var toname=sva[loop].substring(sva[loop].lastIndexOf("[") + 1,sva[loop].lastIndexOf("]"));
			var tovalue=sva[loop].replace("["+toname+"]","");
			var typeif=sva[loop].indexOf("}");
			if(typeif==-1){
				optionm=optionm+'<a rel="'+toname+'" title="'+tovalue+'" href="javascript:void(0);">'+tovalue+'</a>';
			}else{
				tovalue=tovalue.replace("{","").replace("}","");
				optionm=optionm+'<strong>'+tovalue+'</strong>';
			}
		}
		_sellist.html(optionm);
		
		$("body").append('<span id="tmpsel">'+optionm+"</span>");
		var max1 = 0;
		$("#tmpsel a").each(function() {var th = parseInt($(this).width());
		if (th > max1 && th<450) { max1 = th;}});
		$("#tmpsel").html("").remove();
		_sellist.css({display:"inline"});
		
		_sellist.css({height:"auto"});//add by yindan
		_sellist.css("max-height","200px");//add by yindan
		
		if(_popdiv.height()>200)
		{
			_sellist.css({display:"block"});
			_sellist.css("height","200px");//add by yindan
			_jiaw=34;
		}
		if($.browser.msie)
		{
			_popdiv.width(_self.width()+23);
			if(id.indexOf('vehicle')>=0)
			{
				_popdiv.width(_self.width()+11);
			}
		}//add by yindan
		else
		{
			_popdiv.width(_self.width()+11);
		}//add by yindan;
		var dtop = document.body.scrollTop;//add by yindan
		var Y = _self.offset().top+25;
		if($.browser.msie){Y = Y + dtop;}//add by yindan
		var X = _self.offset().left-4;
		if(selval!=""){
			if(_popdiv.attr("rel")!=id){_popdiv.hide(0);}
			_popdiv.css({left:X,top:Y}).slideToggle("fast");
		}
		
		_popdiv.find("a").click(function(){
			jQuery("#popdiv").slideUp("fast");
			$(_self).html($(this).html());
			
			$("#"+id).val($(this).attr("rel"));
			if(id == 'rolecheck'){
				$("#wltarea").val("");
				$("#sl-wltarea").html("«Î—°‘Ò");
				$("#sl-user_site_city").html("«Î—°‘Ò");
				$("#user_role").attr("defaultsel","");
				$("#user_role").val("");
				$("#sl-user_role").text("«Î—°‘Ò");
				$("#areaid").attr("defaultsel","");
				$("#areaid").val("");
				$("#sl-areaid").text("«Î—°‘Ò");
				var _rcVal = $("#rolecheck").val();
				//if(_rcVal == '1'){
				//	$("#province,#user_siteCity").hide();
				//}else{
				//	$("#province,#user_siteCity").show();
				//}
				if('' == _rcVal){
					$("#user_role").attr("defaultsel","");
					$("#user_role").val("");
					$("#sl-user_role").text("«Î—°‘Ò");
					return ;
				}
				$.post(
			    	$("#rootPath").val()+"/rights/sysuser.do?method=getUserRoleByType",
			      	{
			      		rtype:_rcVal,
			      		provice:$("#wltarea").val()
			      	},
			      	function (data){
			      		$("#user_role").attr("defaultsel",data);
			      	}
				)
			}else if(id == 'wltarea'){
				var _sa = $("#wltarea").val();
				if(_sa == ''){
					$("#user_role").attr("defaultsel","");
					$("#user_role").val("");
					$("#sl-user_role").text("«Î—°‘Ò");
					$("#areaid").attr("defaultsel","");
					$("#areaid").val("");
					$("#sl-areaid").text("«Î—°‘Ò");
					return ;
				}
				if($("#rolecheck").val() != ''){
					$.post(
				    	$("#rootPath").val()+"/rights/sysuser.do?method=getUserRoleByArea",
				      	{
				      		areaid:_sa,
				      		sType:$("#rolecheck").val()
				      	},
				      	function (data){
				      		//$("#user_role").val(data);
				      		$("#user_role").attr("defaultsel",data);
				      	}
					)
				}
				$.post(
			    	$("#rootPath").val()+"/rights/sysuser.do?method=getCity",
			      	{
			      		areaid:_sa
			      	},
			      	function (data){
			      		$("#areaid").attr("defaultsel",data);
			      		$("#sl-user_site_city").html("«Î—°‘Ò");
			      		$("#user_site_city").attr("defaultsel",data);
			      	}
				)
			}else if(id == 'wltarea1'){
				var _sa = $("#wltarea1").val();
				if(_sa == ''){
					$("#user_role").attr("defaultsel","");
					$("#user_role").val("");
					$("#sl-user_role").text("«Î—°‘Ò");
					$("#areaid").attr("defaultsel","");
					$("#areaid").val("");
					$("#sl-areaid").text("«Î—°‘Ò");
					return ;
				}
				if($("#rolecheck").val() != ''){
					$.post(
				    	$("#rootPath").val()+"/rights/sysuser.do?method=getUserRoleDefaultByArea",
				      	{
				      		areaid:_sa,
				      		sType:$("#rolecheck").val()
				      	},
				      	function (data){
				      		$("#user_role").val(data);
				      	}
					)
				}
				$.post(
			    	$("#rootPath").val()+"/rights/sysuser.do?method=getCity",
			      	{
			      		areaid:_sa
			      	},
			      	function (data){
			      		$("#areaid").attr("defaultsel",data);
			      		$("#user_site_city").attr("defaultsel",data);
			      	}
				)
			}else if(id == 'wltarea_regist'){
				var _sa = $("#wltarea_regist").val();
				if(_sa == ''){
					$("#user_role").attr("defaultsel","");
					$("#user_role").val("");
					$("#sl-user_role").text("«Î—°‘Ò");
					$("#areaid_regist").attr("defaultsel","");
					$("#areaid_regist").val("");
					$("#sl-areaid").text("«Î—°‘Ò");
					return ;
				}
				if($("#rolecheck").val() != ''){
					$.post(
				    	$("#rootPath").val()+"/rights/sysuser.do?method=getUserRoleByAreaRegist",
				      	{
				      		areaid:_sa,
				      		sType:$("#rolecheck").val()
				      	},
				      	function (data){
				      		if(data != ""){
				      			var toname=data.substring(data.lastIndexOf("[") + 1,data.lastIndexOf("]"));
								var tovalue=data.replace("["+toname+"]","");
					      		$("#sl-user_role").text(tovalue);
					      		$("#user_role").val(toname);
				      		}else{
				      			$("#sl-user_role").text("«Î—°‘Ò");
					      		$("#user_role").val("");
				      		}
				      		$("#user_role").attr("defaultsel",data);
				      	}
					)
				}
				$.post(
			    	$("#rootPath").val()+"/rights/sysuser.do?method=getCity",
			      	{
			      		areaid:_sa
			      	},
			      	function (data){
			      		$("#areaid_regist").attr("defaultsel",data);
			      		$("#user_site_city").attr("defaultsel",data);
			      	}
				)
			}else if(id == 'areaid_regist'){
				var _sa = $("#areaid_regist").val();
				if(_sa == ''){
					$("#user_role").attr("defaultsel","");
					$("#user_role").val("");
					$("#sl-user_role").text("«Î—°‘Ò");
					return ;
				}
				$.post(
			    	$("#rootPath").val()+"/rights/sysuser.do?method=getUserRoleByAreaRegist",
			      	{
			      		areaid:_sa,
			      		sType:$("#rolecheck").val()
			      	},
			      	function (data){
			      		if(data != ""){
			      			var toname=data.substring(data.lastIndexOf("[") + 1,data.lastIndexOf("]"));
							var tovalue=data.replace("["+toname+"]","");
				      		$("#sl-user_role").text(tovalue);
				      		$("#user_role").val(toname);
			      		}else{
			      			$("#sl-user_role").text("«Î—°‘Ò");
				      		$("#user_role").val("");
			      		}
			      		$("#user_role").attr("defaultsel",data);
			      	}
				)
			}else if(id == 'areaid000'){
				var _sa = $("#areaid").val();
				if(_sa == ''){
					$("#user_role").attr("defaultsel","");
					$("#user_role").val("");
					$("#sl-user_role").text("«Î—°‘Ò");
					return ;
				}
				$.post(
			    	$("#rootPath").val()+"/rights/sysuser.do?method=getUserRoleByArea",
			      	{
			      		areaid:_sa,
			      		sType:$("#rolecheck").val()
			      	},
			      	function (data){
			      		$("#user_role").attr("defaultsel",data);
			      	}
				)
			}else if(id == 'macflag'){
				var _mf = $("#macflag").val();
				if(_mf == '1'){
					$("#mac").val("");
					$("#macAddr").hide();
				}else{
					$("#macAddr").show();
				}
			}else if(id == 'wltarea_role'){
				var _sa = $("#wltarea_role").val();
				$.post(
			    	$("#rootPath").val()+"/rights/sysuser.do?method=getCity",
			      	{
			      		areaid:_sa
			      	},
			      	function (data){
			      		$("#user_site_city").attr("defaultsel",data);
			      	}
				)
			}else if(id == 'areacode'){
				var _sa = $("#areacode").val();
				$.post(
			    	$("#rootPath").val()+"/rights/sysuser.do?method=getCity",
			      	{
			      		areaid:_sa
			      	},
			      	function (data){
			      		$("#ucity").attr("defaultsel",data);
			      	}
				)
			}
		});
		_popdiv.attr("rel",id);
		//event.stopPropagation();//◊Ë÷π√∞≈›
		event.cancelBubble=true; 
	});
});
	
	$(document).click(function(){
	jQuery("#popdiv").slideUp("fast");
	});
	
	

	
	$("input[nname='nname']").each(function(){
		var _th = $(this);
		var _val = _th.val();
		var _def = _th.attr("defaultsel");
		if(_def != "" && null != _def){
			var sva =_def.split("|");
			for (loop = 0; loop <sva.length; loop++){
				var toname=sva[loop].substring(sva[loop].lastIndexOf("[") + 1,sva[loop].lastIndexOf("]"));
				var tovalue=sva[loop].replace("["+toname+"]","");
				if(_val != "" && null != _val && toname == _val){
					$("#sl-"+_th.attr("id")).text(tovalue);
				}
			}
		}
		
	});
});