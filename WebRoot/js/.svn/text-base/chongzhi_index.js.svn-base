
 /*----------------------------------------常量结束----------------------------------------------*/
 
if(typeof(AppBase) != "undefined"){
    AppBase.run('tbox',"login","node", "io","until","node-event-simulate",'paylayer',"event","cookie","stat",function(Y){        
        var _telephone = null,       //手机号码的文本输入框对象            
            _history_list = null,    //充值记录对象
            _box = Y.until.getTop().TBox||window.TBox;        
              
        /**
         * 初始化文本框为电话号码输入对象
        */
        var _initElement = function(){
            var tips = Y.one("#js_id_mobile_tips");
            var correctCaller = function(value){interaction.getPhoneAddress(value);};
            var errorCaller   = function(value){tips && tips.addClass("show-num-err").removeClass("show-num-info");};
            var focusCaller   = function(){tips && tips.removeClass("show-num-err")};
            var keydownCaller = function(){tips && tips.removeClass("show-num-err");};
            var keyUpCaller   = function(){if(_telephone.getValue() == ""){tips.removeClass("show-num-info")}};
            
            _telephone = new TelePhone(Y,{
                'selector'      : '#js_id_mobile',
                'emptyText'     : '',
                'correctCaller' :  correctCaller,
                'errorCaller'   :  errorCaller,
                'focusCaller'   :  focusCaller,
                'keyDownCaller' :  keydownCaller,
                'keyUpCaller'   :  keyUpCaller
            });
        };    
        /**
         * 请求用户最近一次的充值记录
        */
        var _initData = function(){
            if(Y.User.isLogin()){
                request.doRequest("LATEST_QUERY",null,function(json){
                    _history_list = Y.Tool.formatRecord(json.records);
                    if(_history_list.length){
                        var mobile = _telephone.mobileSplit(_history_list[0].chgmobile);
                        var para = Y.Tool.getPara();
                        mobile = para.mobile ? para.mobile : mobile;
                        
                        _telephone.setValue(mobile);     //显示最近一次的充值记录或者从url中传递的值 
                        _telephone.validate(true);
                    }
                });            
            }            
        };                  
        
        var initEnv = function(){            
            var _init = function(){
                _initElement();              //初始化页面的相关元素
                //_initData();                 //初始化页面在加载完毕后需要加载的数据  
                var para = Y.Tool.getPara();            
                
                if(para.mobile){
                    _telephone.setValue(para.mobile);     //显示最近一次的充值记录或者从url中传递的值 
                    _telephone.validate(true);
                }    
                
                if(para.fee){
                    var node = Y.one("#js_id_price a[data-value='" + para.fee + "']");
                    if(node){
                        Y.all("#js_id_price a").removeClass("on");
                        node.addClass("on");
                    }
                }
            };      
            
            return {
                init: _init
            };
        }();          
        /*----------------------------------------初始化结束-----------------------------------------*/
        
        var interaction = function(){
            /**
             * 初始化页面的事件
            */
            var _initEvent = function(){
                Y.one("#js_id_history").on("click",_showHistoryList);
                Y.one("html").on("click",_onDocClick);
                Y.one("#js_id_price").delegate("click",_onPrizeChange,">a");     
                Y.one("#js_id_submit").delegate("click",_onSubmit, ">a");
            };
            
            /**
             * 显示历史充值记录
            */
            var _showHistoryList = function(e){               
                var content = CONSTANT.HIS_UNLOIGIN;
                var node = Y.one("#js_id_history_panel");
                var formatHis = function(list){     //格式化充值记录
                    var str = list.length ? "" : CONSTANT.HIS_EMPTY;   
                    Y.each(list,function(v,k){
                        str += Y.substitute(CONSTANT.HIS_ITEM,{
                            'number' : v.chgmobile||"",
                            'name'   : unescape((v.attach||"").substring(0,30))
                        });
                    });
                    return str;
                };
                
                e.preventDefault();
                e.stopPropagation();
                if(Y.User.isLogin() && !node.hasClass('h-select-on')){              
                    content = CONSTANT.HIS_LOADING;
                    request.doRequest("HISTORY_QUERY", null,function(json){
                        _history_list = Y.Tool.formatRecord(json.records);
                        if(_history_list.length && _history_list.length > 5 && Y.Tool.isWallet()){
                            Y.one("#js_id_history_list").addClass("num-limit");
                        }
                        Y.one("#js_id_history_list").set("innerHTML" , formatHis(_history_list));
                    });                    
                }
                node.toggleClass("h-select-on");
                Y.one("#js_id_history_list").set("innerHTML" , content);
            };
            /**
             * 响应页面的点击事件
            */
            var _onDocClick = function(e){
                var target = e.target;    
                if(target){
                    var type = target.getAttribute("data-type");
                    var config = {
                        'his_edit'  : _OnUpdateAttach,
                        'his_delete': _deleteRecord,
                        'his_save'  : _saveAttach,
                        'do_login'  : function(){Y.Stat.clickStat(null,Y.Tool.getStatName("MOBILE.INDEX.LOGIN"),'life.tenpay.com');Y.User.defaultLogin();},
                        'his_number': _selectNumber,
                        'his_attach': function(){}
                    };                    
                    var caller = config[type];
                    
                    if(caller){
                        caller(target);
                        e.preventDefault();
                    }else{
                        Y.one("#js_id_history_panel").removeClass("h-select-on");
                    }                    
                }
            };
            /**
             * 响应备注名称的按钮事件
            */
            var _OnUpdateAttach = function(target){
                if(target){
                    Y.one("#js_id_history_list").all('li').removeClass('show-edit');
                    var parent = target.ancestor();                       
                    var phone = parent.one("a").get("innerHTML");
                    var node = parent.one("input");                                
                    parent.addClass("show-edit");
                    node.focus();
                    
                    if(!target.getAttribute("data-bind")){
                        target.setAttribute("data-bind","1");
                                                 
                        new TextField(Y,{    //初始化备注名称文本框输入对象
                            'node'   : node,
                            'value'  : target.getAttribute("data-value")||"",
                            'keydown':{
                                'callBack' : function(e){                                          
                                    if(e.keyCode === 13){     
                                        node.blur();                                        
                                        _updateAttach(parent,phone,node.get("value"),node.getAttribute('data-value'),node);                                                                                                                         
                                    }
                                }                                  
                            }
                        });
                    }     
                }
            };
            
            /**
             * 用户点击保存备注名称操作
            */
            var _saveAttach = function(target){
                if(target){
                    var pre = target.ancestor().previous();
                    var phone = target.getAttribute("data-number");                  
                    _updateAttach(Y.one("ul>li[data-value=" + phone +"]"),phone,pre.get("value"),pre.getAttribute('data-value'),pre);                    
                }
            };
            
            /**
             * 更新电话号码的备注名称
            */
            var _updateAttach = function(node,phone,attach,oldValue,crtNode){                
                var len = 0 ,index = 0;
                attach = Y.Tool.filterScript(attach);
                for(var i = 0;i < attach.length; i++){
                    var the = attach.charAt(i);
                    if(/.*[\u4e00-\u9fa5]+.*$/.test(the)) {
                        len = len + 2;
                    }else{
                        len++;
                    }                    
                    if(len > 10){
                        attach = attach.substring(0,i);
                        break;
                    }
                }         
                
                node.removeClass("show-edit");
                node.one("a[class='cnt-note-block']").set("innerHTML",attach);
                if(attach.replace(/\s/g,"") == "" || attach == oldValue) return;
                request.doRequest("MODIFY_NAME" , {
                                                    'uin'   :Y.User.isLogin() ? Y.Cookie.get("qluin") : phone,
                                                    'phone' :phone,
                                                    'attach':escape(attach)
                                                  },function(json){crtNode.setAttribute('data-value',attach);Y.log("update attach state:" + json.retcode )});
            };
            
            /**
             * 获取号码的归属地
             *@param {String} phone 需要查询归属地的电话号码
            */
            var _getPhoneAddress = function(phone){
                request.doRequest("PHONE_ADDRESS",{'mobile' : phone} , function(json){                    
                    var p = (json.province||"");
                    var c = (json.city||"");
                    var data = {
                        'sp'  : json.supplier||"",
                        'area': (p  == c ? p : p + c)
                    };                    
                    var tpl = data.area ? CONSTANT.ADDRES_KOWN : CONSTANT.ADDRES_UNKOWN;
                    Y.one("#js_id_mobile_tips").removeClass("show-num-err").addClass("show-num-info");
                    Y.one("#js_id_mobile_info").set("innerHTML" , Y.substitute(tpl,data));
                    Y.one("#js_id_sp").set('value',(data.area + data.sp));
                });
            };
            
            /**
             * 选择历史记录中的电话号码
            */
            var _selectNumber = function(target){                             
               Y.one("#js_id_history_panel").removeClass("h-select-on");
               _telephone.setValue(target.getAttribute("data-value")||target.get("innerHTML"),true);
               _telephone.validate(true);
            };
            
            /**
             * 删除用户的充值记录
            */
            var _deleteRecord = function(target){
                if(target){                    
                    var number = target.getAttribute("data-value");                    
                    var deleteHander = function(){                                                                
                        request.doRequest("DELETE_RECORD",{"phone": number},function(json){                               
                            Y.one("ul>li[data-value=" + number +"]").remove();                        
                            var child = Y.one("#js_id_history_list").all("li");
                            if(child.size() == 0){
                                Y.one("#js_id_history_list").set("innerHTML",CONSTANT.HIS_EMPTY);
                            }
                        });
                    };                    
                    
                    Y.Tool.showMsgBox({
                        title : '提示',                        
                        msgTitle : '您确定要删除该记录吗？',                                              
                        btns : [['确定',function(){_box.hideMsgBox();deleteHander.call();}],['取消', function () {_box.hideMsgBox();}]]
                    });
                }
            };
            
            /**
             * 价格发生变化时的代理事件
            */
            var _onPrizeChange = function(e){
                e.preventDefault();
                this.ancestor().all("a").removeClass("on");
                this.addClass("on");
                Y.one("#js_id_money_show").set('innerHTML',this.getAttribute("data-money"));     
                var  that = this;               
                Y.each(["js_id_lidou","js_id_vip","js_id_grow"],function(v,k){
                    var node = Y.one("#" + v);
                    if(node){
                        var value = that.getAttribute("data-" + v.substring(6));
                        node.set('innerHTML',value);
                        if(value == 0){
                            node.ancestor().ancestor().addClass('hide');
                        }else{
                            node.ancestor().ancestor().removeClass('hide');
                        }
                    }                    
                });
            };
            
            /**
             * 提交表单
            */
            var _onSubmit = function(e){
                e.preventDefault();     
                var resSuc = function(data){  //预支付CGI成功后POST到支付网关
                    var para = [
                        'amount=' + data.amount,
                        'spname=' + data.spname||'',
                        'chargeway=' + data.chargeway,
                        'add=' + Y.one("#js_id_sp").get('value')
                    ];
                    var str = para.join('&') + "&" + data.para;               
                    Y.Tool.doPayAction(str,data);    
                    Y.one("#js_id_submit").removeClass('loading');                    
                };
                
                if(_telephone.validate(true)){
                    this.ancestor().addClass("loading");
                    var links = Y.one("#js_id_price").all('a'),value = '';
                    Y.each(links,function(v,k){
                        if(v.hasClass("on")){value = v.getAttribute('data-value');}
                    })                    
                    request.doRequest("DO_CHARGE",{
                                                        'anonyuser' : Y.User.getUserState() > 0 ? "" : 1,
                                                        'phone'     : _telephone.getValue(),
                                                        'uin'       : _telephone.getValue(),
                                                        'amount'    : parseInt(value) * 100
                                                  },resSuc,ERROR_MAP);
                }else{
                    _telephone.select();
                }
            };
            
            return {
                init           : _initEvent,
                getPhoneAddress: _getPhoneAddress
            }
        }();
                   
        /*------------------------------------- 交互区结束--------------------------------------*/    
       
        var request = function(){            
            /**
             * 统一网络请求发送方法 只有请求成功才进入回调  非成功的请求在此处统一处理
             *@param {String} name  请求名称标示
            */
            var _doRequest = function(name , para , callBack , errorMap){
                var _url = Y.Tool.getEnvUrl(name);
                errorMap = errorMap||{};
                
                if(_url){
                    var _data = Y.Tool.formatQueryStr(Y.Tool.getReqPara(name),para);
                    new Y.Request({'dataType' : 'xml','data' : ["*"],'checkLogin' : false},{'url': _url,'data': _data},function(json){
                        var retcode = parseInt(json.retcode,10);
                        switch(retcode){
                            case 0: callBack.call(Y,json);
                                    break;
                            case 2020001:
                                    Y.User.defaultLogin();
                                    break;
                            default:
                                    if("PHONE_ADDRESS" != name){
                                        Y.Tool.showMsgBox({                                    
                                            msgTitle  : errorMap[retcode]||("[" + retcode + "]" + json.retmsg||"系统繁忙")                                                                                                                    
                                        });
                                        Y.one("#js_id_submit").removeClass('loading');
                                    }                                    
                                    break;
                        }
                    });
                }
            };
            
            return {
                doRequest: _doRequest
            }
        }();        
        /*-------------------------统一的网络请求结束-------------------------*/  
        initEnv.init();
        interaction.init();    
    });
}
