/******************************************************
 * Copyright (c) 2011, TENPAY.COM All rights reserved *
 ******************************************************/
/**
 * @ author chauvetxiao
 * @ encode utf-8
 * @ version 1.0
 * @ date 2012-07-09
 * @ 手机充值基础类
*/

/**
 *YUI全局对象
*/
try {document.domain="tenpay.com";}catch(e){}var G_pageLoadTime=(new Date()).getTime(),G_speedPointTime={},G_userInfo=null;G_needSuperLogin=false;var _d=window.name;_a=_d.split('[stamp]');if(_a.length>1){G_speedPointTime['PAGE_JUMP']={'ID':7,'DATA':[_a[0],(new Date()).getTime()]};};window.onbeforeunload=function(){var d=window.name;a=d.split('[stamp]');if(a.length>1){d=a[1];}};function $(id){return document.getElementById(id);};function getQQList(h){if(window.ActiveXObject){try{var l=[];var e=new ActiveXObject("SSOAxCtrlForPTLogin.SSOForPTLogin2");var d=e.CreateTXSSOData();e.InitSSOFPTCtrl(0,d);var j=e.CreateTXSSOData();var a=e.DoOperation(2,j);if(a!=null){var b=a.GetArray("PTALIST");var i=b.GetSize();var g="PVAList::";for(var c=0;c<i;c++){var n=b.GetData(c);var m=n.GetArray("SSO_Account_AccountValueList");var k=m.GetStr(0);if(h){if(h==-1){l.push(k)}else{if(c==h){l=k;break}}}else{l=k;break}}return l}else{return""}}catch(f){return""}}else{return""}};
function CL_CallBack(o) {var C_userType = 'cft',retcode = parseInt(o.retcode, 10);if (retcode == 0) {var status = o['status'];var uid = '',truename = '',uin = o['uin'] || '',attid = '',type = C_userType; var vip = o["vip_flag"]||"";if ('2' == status) {type = 'qq';} else if (/^[1|4]$/.test(status)) {attid = o['att_id'];switch (attid) {case '30':type = 's_qq';break;case '31':type = 's_paipai';break;case '32':type = 's_email';break;default:uid = o['uid'];truename = o['user_true_name'];}}G_userInfo = {'uid': uid,'uin': uin,'truename': truename,'attid': attid,'type': type,'logtype': status,"vip_flag":vip};G_needSuperLogin = ('4' == G_userInfo['logtype']);} else {G_userInfo = {'uid': '','uin': '','truename': '','attid': '','type': '','logtype': '','vip_flag':''};}}

var type = {YUI:"YUI"};
/**
 * 活动框架的相关配置
*/
var Libs = {
    "YUI":{name:type.YUI,version:3.2,require:{base:{url:"https://www.tenpay.com/app/v1.0/mergefile?/js/global/speed-min.js&/js/global/tenpayctrl-min.js&v=2.38&/js/yui/build/yui/yui-min.js",charset:"utf-8"},
                                     extend:[{url:'https://www.tenpay.com/app/v1.0/query_login_status.cgi?JsonObj=CL_CallBack',charset:"utf-8"}]},plug:["node","login","stat",'substitute',"tbox","cookie"]}
};

var Y = null;

/**
 * 调用
*/
var AppBase = function(){
    var _frmw_res = {},
        _plugs = null,
        _callBack = null,
        _o = null;
    
    var _queue_cfg = {_isReady:false};   //资源队列加载相关参数
    /**
     * 初始化接口
     * @param {cfg} object 配置对象
    */
    var _init = function(){
        var args = arguments,
            length = args.length;
                    
        _frmw_res = Libs["YUI"];
        _plugs = [].slice.call(args,0,length-1);
        _callBack = args[length-1];
        if(!(_callBack instanceof Function)){_callBack = function(){}}
        _getLoad();
    };    
    /**
     * 开始加载脚本-基础库
    */
    var _getLoad = function(){
        try{
            _loadScript({url:_frmw_res.require.base.url,handler:_loadPlugScript,charset:_frmw_res.require.base.charset});
        }catch(e){}
    };
    /**
     * 加载其他脚本
    */
    var _loadPlugScript = function(){
        var cfg = _frmw_res["require"]["extend"][0];
        _makeQueue();
        for(var i=0,l=_queue_cfg.queue.length;i<l;i++){
            _loadScript({url:_queue_cfg.queue[i].url,handler:_doCheck,charset:_queue_cfg.queue[i].charset});
        }
        try{ 
            var context = YUI({"combine":true});
            var callBack = function(Y){ 
                var timer = null;
                (function(){
                    if(_queue_cfg._isReady){    //执行回调必须在主脚本和其他脚本加载完毕以后
                        timer && window.clearInterval(timer);
                        _o = Y;
                        _doInitialize(Y);
                        _callBack(Y);
                    }else{
                        if(!timer){
                            timer = window.setInterval(arguments.callee,100);
                        }
                    }
                })();
            }
            _plugs = _plugs.concat(_frmw_res.plug);
            _plugs = _uniq.call(_plugs);
            _plugs.push(callBack);
            context.use.apply(context,_plugs);
        }catch(e){}
    };
    /**
     * 将需要加载的资源依次入队
    */
    var _makeQueue = function(){
        var plug = _frmw_res.plug;
        _queue_cfg.queue = _frmw_res.require.extend;  
        if(!(plug instanceof Array)){
            for(var i =0,l = _plugs.length,temp = null;i < l;i++){
                temp = plug[_plugs[i]];
                if(temp){
                    _queue_cfg.queue.push(temp);
                }
            }
        }
    };
    /**
     * 请求用户信息完毕
    */
    var _doCheck = function(pop){
        !pop && _queue_cfg.queue.pop();
        if(_queue_cfg.queue.length == 0){
            _queue_cfg._isReady = true;
        }
    };
    /**
     * 进行公共组件的初始化
    */
    var _doInitialize = function(o){
        var o = o||_o;
        window["Y"] = o;
        o["Request"] = AppBase.Request;
        o["Tool"] = AppBase.Tool;
        o["User"] = AppBase.User;
    };
    /**
     * 动态加载脚本 cfg{url:资源地址 handler:加载的回调 args:回调参数 isDestory:是否销毁对象 charset:编码,context:上下文对象}
    */
    var _loadScript = function(cfg){ 
        var cfg = {url:(cfg = cfg||{}).url||"",handler:cfg.handler,args:cfg.args||[],context:cfg.context||window,charset:cfg.charset||"utf-8",isDestory:(typeof(cfg.isDestory) == "boolean" ? cfg.isDestory : true)},
            head = document.getElementsByTagName("head")[0],
            script = document.createElement("script"),
            id = "dynamic_script_" + ((new Date()).getTime()),
            eventType = (undefined !== script.onreadystatechange && undefined !== script.readyState) ? "readystatechange" : "load";
            
            script.charset = cfg.charset;
            script.type="text/javascript";
            script.src = cfg.url;
            script.id = cfg.id;
                
        var stateChange = function(){
            var state = script.readyState || "loaded";
            if("loaded" == state || "complete" == state){
                if(typeof(cfg.handler) == "string"){
                    setTimeout(function(){try{var hdl = eval(cfg.handler);hdl.apply(cfg.context, args);}catch(e){}}, 50);
                }else if(typeof(cfg.handler) == "function"){
                    setTimeout(function(){try{cfg.handler.apply(cfg.context, cfg.args);}catch(e){}}, 50);
                }
                if(cfg.isDestory){
                    head.removeChild(script);
                }
                head = null;
                script = null;                                           
            }
        }
        if(script.attachEvent){
            script.attachEvent("on" + eventType,stateChange);
        }else{
            script.addEventListener(eventType,stateChange,true);
        }
        head.appendChild(script);
        return id;
    };
    /**
     * 数组的去重
    */
    var _uniq = function() {  
        var temp = {}, len = this.length;

        for(var i=0; i < len; i++)  {  
            if(typeof temp[this[i]] == "undefined") {
                temp[this[i]] = 1;
            }  
        }  
        this.length = 0;
        len = 0;
        for(var i in temp) {  
            this[len++] = i;
        }  
        return this;  
    };
    
    return {
        run:_init,
        loadScript:_loadScript
    }
}(); 

/**
 * 为AppBase添加其他属性
*/
(function(o){     
    /**
     * 工具对象
    */
    var Tool = o.Tool = {      
        /**
         * 判断当前场景如否处于钱包
        */
        isWallet: function(){
            return G_APP_TYPE === 'm';
        },
        /**
         * 判断当前场景如否处于大版life充值
        */
        isLife: function(){
            return G_APP_TYPE === 'b';
        },
        /**
         * 获取点击流统计的名称
        */
        getStatName: function(end){
            var p = Tool.isWallet() ? "WALLET" : "LIFE_V2";
            return p + "." + end;
        },
        /**
         * 获取环境对象中配置的参数
        */
        getEnvPara: function(env){
            try{
                var str = "G_MOBILE." + env;
                var data = eval(str);
                if(data.m){
                    return data[G_APP_TYPE];
                }else{
                    return data;
                }
            }catch(e){return ""}
        },
        /**
         * 获取ajax请求的路径参数
        */
        getEnvUrl: function(name){
            var data = this.getEnvPara("s.io." + name);
            return (data ? window.location.protocol + data.url : "");
        },
        /**
         * 获取tbox消息框的尺寸
        */
        getBoxSize: function(){
            return this.getEnvPara("boxsize");
        },
        /**
         * 发起支付
         *@param {String} queryStr 查询的字符串
         *@param {String} data 预查询返回的JSON数据
        */
        doPayAction: function(queryStr,data){
            var caller = this[this.getEnvPara("pay")];          
            if(caller){
                caller.apply(this,arguments);
            }
        },
        /**
         * 发起钱包版的充值
        */
        doWalletPay: function(queryStr,data){            
            var url = "https://www.tenpay.com/cgi-bin/v1.0/pay_gate.cgi?" + data.para;
            
            var payUrl = Y.Tool.appendParameter(url, this.getSceneString());
                payUrl = Y.Tool.appendParameter(payUrl, this.getPayScene());

                var _url = this.getEnvPara("pay_center") + "?url=" + encodeURIComponent(payUrl);
                Y.until.getTop().location.replace(_url);
        },
        /**
         * 根据场景获得支付字符串串
        */
        getPayScene: function(){
            /** SCENE的值
                CLIENT: 0, //QQ客户端
                WEB   : 1,  //web 版
                WEBQQ : 2,  //web qq调用
                QZONE : 3,  //qzone 调用
                SOSO  : 4,  //soso 调用
                PENGYOU : 5, //朋友调用
                TENPAY : 6, //主站调用
                TENCITY : 7, //tencity调用
                APPBOX : 8, //应用盒子
                QPLUS  : 9, //Q+
                PAIPAI_CHONG_WEB : 11,//paipai主站充值
                PAIPAI_CHONG_CLIENT : 12, //paipai便民充值
             */
            var o = {"0":1,"1":1,"2":1,"3":1,"4":1,"5":1,"6":1,"7":1,"8":1,"9":1,"11":1,"12":1};
            var paras = this.getPara();
            return ("pay_scene=1");
        },
        /**
         * 获取场景串
        */
        getSceneString: function(){
            var paras = this.getPara();
            return ("SCENE=" + (paras.SCENE||0) + "&LOADER=" + (paras.LOADER||0));
        },
        /**
         *在大版中跳转支付中心
        */
        doLifePay: function(str){            
            Y.PayLayer.pay({
                url     : this.getEnvPara("pay_center"),
                method  : "POST",
                formName: "form" + (+new Date),
                target  : Y.UA.chrome > 0? "_top" : "_blank",
                params  : this.splitStr(str)
            },{
                msgTitle  : '请点击前往支付中心，完成手机充值',
                msgContent: '<div class="tips-line"><a href="http://help.tenpay.com/cgi-bin/helpcenter/help_center.cgi?id=66000&type=0" target="_blank">充值遇到问题？</a></div>',
                btns      : [{text:'继续充值',caller:function(e){e && e.preventDefault(); var win = Y.until.getTop();win.location.reload();},context:this,args:[]}]
            },{
                caller  : function(){         
                    this.showMsgBox({
                        title      : '提示',
                        ico        : 'info',
                        msgTitle   : '请在新开的页面完成手机充值',
                        msgContent : '<div class="tips-line"><a href="http://help.tenpay.com/cgi-bin/helpcenter/help_center.cgi?id=66000&type=0" target="_blank">充值遇到问题？</a></div>',
                        btns       :  [['已完成充值', function(e){e && e.preventDefault();var box = Y.until.getTop().TBox;box.hideMsgBox();}], ['继续充值', function (e) {e && e.preventDefault();Y.until.getTop().location.reload();}]]
                    });
                },
                context : this
            });
        },        
        /**
         * 获取ajax请求的路径参数
        */
        getReqPara: function(name){
            var data = this.getEnvPara("s.io." + name);
            return data['param'];
        },
        /**
         * 去除空格
         * @param {String}  str  需要去除空格的字符串
        */
        trim: function(str){
            str = str||"";
            return str.replace(/(^\s*)|(\s*$)/g, "");
        },
        /**
         * 字符串转浮点数
        */
        strToFloat: function(value,len){
            var t =  parseFloat(value);
            if(!isNaN(t)){
                return (t/100).toFixed(len);
            }
            return value;
        },
        /**
         * 过滤脚本等串
        */
        filterScript: function(str){
            str = str || "";
            str = "" + str;
            str = str.replace(/<.*>/g, ""); //过滤标签注入
            str = str.replace(/(java|vb|action)script/gi, ""); //过滤脚本注入
            str = str.replace(/[\"\'][\s ]*([^=\"\'\s ]+[\s ]*=[\s ]*[\"\']?[^\"\']+[\"\']?)+/gi, ""); //过滤HTML属性注入		
            return str;
        },
        /**
         * 由于xml转json时如果子节点只有一个，则不会自动转为数组形式
        */
        formatRecord: function(records){          
            if(!Y.Lang.isArray(records)){
               return (records ? (Y.Lang.isArray(records.record) ? records.record : [records.record]) : []);
            }
            return records;
        },
        /**
         * 格式化请求的参数字符串
        */
        formatQueryStr: function(query , para){
            var chargeWay = Y.one("#js_id_chargeWay");
            var data = {
                'ptm'      : +new Date(),
                'state'    : User.getUserState(),
                'channel'  : this.getEnvPara("channel"),
                'chargeWay': (chargeWay ? chargeWay.get("value") : 1)
            };
            para = Y.merge(data,para);
            if(para.uin != undefined && User.isLogin()){
                para.uin = Y.Cookie.get("qluin");
            }
            return Y.substitute(query,para);
        },
        /**
         * 格式化数据
         * @param {tpl} string 格式化的模板
         * @param {records} Array 数据数组
         * @param {rules} object 不同字段的格式化方法
        */
        format: function(tpl,records,rules){
            var arr = [],rules = rules||{},tpl = tpl||"";
            Y.each(records||[],function(v,k){
                if(v){
                    Y.each(v,function(m,c){
                        if(rules[c]){
                            v[c] = rules[c].call(v,m,c,k);  /**格式化时的四个参数：作用域当前对象、当前属性的值、标记位、当前行在整个数据对象中的位置*/
                        }
                    })
                    arr.push(Y.substitute(tpl,v));
                }
            });
            return arr.join("").replace(/{\w*}/g,"");
        },
        /**
         * 创建一个下拉选项
         * @param {element} target 需要添加选项的select节点
         * @param {String}  txt 选项的文本节点
         * @param {*}       val 节点的值
         * @param {Boolean} selected 选项是否选中
        */
        addOption: function(target,txt,val,selected){          
            var op = document.createElement("option");
                op.text = txt;
                op.value = val;
                op.selected  = selected;
                Y.one(target) && Y.one(target)._node.options.add(op);
        },
         /**
         * 重定向，钱包带登录态跳转到财付通主站
         * @param String url 目标地址
         * @param Boolean inWallet 是否钱包内跳转
         * @param Boolean isFlush 是否刷新cookie
         * @param Boolean isUpdate 是否更新session
         */
        redirectTo:function(url, inWallet, isFlush, isUpdate){
            var s = User.getSession(),
                flush = (isFlush ? 1 : 0),
                jumper = "https://www.tenpay.com/cgi-bin/v1.0/wallet_loginto_cft.cgi",
                desUrl = jumper + "?channel=wallet&tourl=" + encodeURIComponent(url) + "&flush_cookie=" + flush + "&qlsign=" + (s["qlsign"]||""),
                jump_page = "https://www.tenpay.com/v2.0/wallet/loading.html";

            if(true === isFlush){
                desUrl += "&flush_cookie=1";
            }
            if(inWallet){
                location.href = desUrl;
            }else{
                Tool.doForm(jump_page, "_blank", "get", {
                    "j":1,
                    "qluin"       : s.qluin,
                    "qlskey"      : s.qlskey,
                    "tourl"       : url,
                    "flush_cookie": flush,
                    "channel"     : "wallet",
                    "qlsign"      : s["qlsign"]||""
                });
            }
        },
        /**
         * 拆分字符处为对象
        */
        splitStr: function(params){
            var arr = params.split('&') , temp;
            params = {};
            for(var i = 0,l = arr.length; i < l ; i ++){
                temp = arr[i].split("=");
                if(temp.length == 2){
                    params[temp[0]] = temp[1];
                }
            }
            return params;
        },       
        /**
         * 动态表单
         * @param String url 表单的action
         * @param String target 表单提交的目标窗口名
         * @param String method 表单提交方法（GET/POST）
         * @param Object params 表单元素集
         * @param Boolean isAutoSubmit 是否自动提交，默认为true
         * @return String 表单的ID
         */
        doForm : function(url, target, method, params, isAutoSubmit){
            var f = document.createElement("form");
            var sInput = "";
            var idStr = "dynamic_form_" + (new Date().getTime());
            f.method = method;
            f.target = target;
            f.action = url;
            f.id = idStr;
            f.name = idStr;
            isAutoSubmit = typeof(isAutoSubmit) == "boolean" ? isAutoSubmit : true;
            if(typeof(params) == 'string'){
               params = this.splitStr(params);
            }
            for(var key in params){
                if(params.hasOwnProperty(key)){
                    sInput += '<input type="hidden" name="'+key+'" value="'+params[key]+'" />';
                }
            }
            f.innerHTML = sInput;
            document.body.appendChild(f);
            if(isAutoSubmit){
                f.submit();
                document.body.removeChild(f);
            }
            f = null;
            return idStr;
        },
        /**
         * 设置状态
         * @param {element} target 需要设置状态的节点
         * @param {Number} status 需要设置的状态位
         * @param {Number} retcode 状态码
         * @param {String} retmsg 错误信息
        */
        setStatus: function(target,status,retcode,retmsg){
            if(target){
                var allStatus = ["","show-abnormal-loading","show-abnormal-sysbusy","none-data"];
                
                target = Y.one(target);
                if(target){
                    for(var i = 0;i < allStatus.length;i++){                        
                        i == status?target.addClass(allStatus[i]):target.removeClass(allStatus[i]);
                    }
                }
                if(status == 2 && Y.one("#js_id_error_msg")){
                    Y.one("#js_id_error_msg").set("innerHTML","[" + retcode + "]" + (retmsg||"系统繁忙，请稍候再试！"));
                }
            }
        },
        /** 
         * 获取url的参数值
        */
        getPara: function(){
            var obj = {},
            str = window.location.search.replace("?","");
            
            var arr = str.split("&");
            if(arr.length > 0){
                for(var i = 0,l=arr.length ;i<l;i++){
                    try{
                        var t = arr[i].split("=");
                        obj[t[0]] = Y.until.htmlEncode(t[1]);
                    }catch(e){}
                }
            }
            return obj;
        },
        /**
         * 给URL添加参数
         * @param String url  url
         * @param String parmas 需要添加的参数
         * @return String 添加参数后的URL
         */
        appendParameter : function(url, params){
            if(params){
                url = url + (url.indexOf('?') === -1 ? '?' : '&') + params;
            }
            return url;
        },
        /**
         * 复制
        */
        copyText: function(v){
            var v = Y.until.copyText(v)
            this.showMsgBox({
                'ico'       : (v ? 'ok' : 'warn'),
                'msgTitle'  : (v ? '复制到剪贴板成功。' : '复制到剪贴板失败！' )
            });           
        },
        /**
         * 显示消息提示框
        */
        showMsgBox: function(cfg){
             var size = Y.Tool.getBoxSize();
             var box = Y.until.getTop().TBox||window.TBox;
             box.showMsgBox(Y.merge({
                'title'     : '提示',
                'ico'       : 'warn',
                'width'     : size.width,
                'height'    : size.height,
                'resizeable': true,
                'msgContent': ''
             },cfg));
        },
        /**
         * 隐藏消息对话框
        */
        hideMsgBox: function(){
            var box = Y.until.getTop().TBox||window.TBox;
            box.hideMsgBox();
        },
        /**
         * 过滤掉字符串中的空白和非法字符
        */
        filter: function(str){
            str = ("" + (str || "")).replace(/\s/g,"");
            str = str.replace(/<.*>/g, ""); //过滤标签注入
            str = str.replace(/(java|vb|action)script/gi, ""); //过滤脚本注入
            str = str.replace(/[\"\'][\s ]*([^=\"\'\s ]+[\s ]*=[\s ]*[\"\']?[^\"\']+[\"\']?)+/gi, ""); //过滤HTML属性注入
            return str;
        },
        /**
         *@ 修复IE6不支持的hover效果
         *@param {String|Array} classes 按类选择器进行处理
         *@param {object|Array} cfgs 相关的配置
        */
        FixIE6Hover:function(classes,cfgs){
            var filter = [],cfg = [],reg = /^.\S+/;
            if(!(classes instanceof Array)){
                filter.push(classes);
            }else{
                filter = classes;
            }
            if(!(cfgs instanceof Array)){
                cfg.push(cfgs);
            }else{
                cfg = cfgs;
            }            
            Y.each(filter,function(v,k){
                if(!reg.test(v)){
                    v = "."+v;
                }
                var apps = Y.all(v);
                var that = this;            
                apps.on("mouseover",function(e){
                    that.Tool._IE6Fixed(e.currentTarget,e.currentTarget.addClass,cfg[k]);
                });
                apps.on("mouseout",function(e){
                    that.Tool._IE6Fixed(e.currentTarget,e.currentTarget.removeClass,cfg[k]);
                })
            });
        },
        /**
         *@ IE6的mouseover处理
         *@param {object}   target 处理的对象
         *@param {Function} method 处理方法
         *@param {object}   o      相关属性
        */
        _IE6Fixed : function(target,method,o){        
            if(!target.hasClass(o.checkClass||"add-app")){
                method.call(target,o.addClass||"hoverIE6");
            }else{ 
                method.call(target,o.unaddClass||"add-app-hoverIE6");
            }
        }      
    };
    /**
     * 用户相关的类
    */
    var User = o.User = function(){
        /**
         * 获取用户的登录状态
        */
        var _getUserState = function(){
           var state = 0;           
           if(_isLogin()){
                var qluin = Y.Cookie.get("qluin");
                if(/^[1-9][0-9]{4,9}$/.test(qluin)){
                    state = 2;
                }else if(/^[_a-zA-Z0-9-]+(\.[_a-zA-Z0-9-]+)*@[a-zA-Z0-9-]+(\.[a-zA-Z0-9-]+)+$/.test(qluin)){
                    state = 3;
                }else if(/^1[0-9]{10}$/.test(qluin)){
                    state = 1;
                }
           }
           return state;
        };
        /**
         * 判断使用是否登录
        */
        var _isLogin = function(){
            return _getUserInfo()["isLogin"]||false;
        };
        /**
         * 获取用户的登录态相关cookie信息
        */
        var _getSession = function(){
            var skey = Y.Cookie.get("skey")||null,
                uin = Y.Cookie.get("uin")||null,
                qluin = Y.Cookie.get("qluin"),
                qlskey = Y.Cookie.get("qlskey"),
                qq_logtype = Y.Cookie.get("qq_logtype"),
                qqnick = Y.Cookie.get("qq_nickname"),
                qlsign = Y.Cookie.get("qlsign");

            return {
                "skey" : skey,
                "uin" : uin,
                "qluin" : qluin,
                "qlskey" : qlskey,
                "logtype" : qq_logtype,
                "qqnick" : qqnick,
                "qlsign" : qlsign
            };
        };
        /**
         * 获取用户信息 
        */
        var _getUserInfo  = function(){
            var info = window["G_userInfo"]||null,value = false;
            _userInfo = {isLogin:false, name:"",account:"",type:-1,vip_flag:''};
            if(info){
                _userInfo = {name:info.truename,account:info.uin,type:(info.type||-1),vip_flag:info.vip_flag};
                _userInfo.isLogin = !!(info.type && info.uin);
            }
            return _userInfo;
        };
        /**
         * 默认的登录处理
        */
        var _defaultLogin = function(){
            try{
                var handler = window["G_MOBILE"]["unlogin"][G_APP_TYPE];
                if(Y.Lang.isString(handler)){
                    eval(handler);
                }else{
                    handler();
                }
            }catch(e){Tool.log("调用场景的未登录处理方法失败。")}
        };
        return {
            isLogin     : _isLogin,
            getSession  : _getSession,
            getUserInfo : _getUserInfo,
            getUserState: _getUserState,
            defaultLogin: _defaultLogin
        };
    }();
    
    /**
     * 将xml转为json的对象
    */
    var Xml2Json = o.XML = function(){        
        /**
         * 对文档做转换
        */
        var _doConvert = function(args){
            var data = {};
            if(args){
                var isIE = Y.UA.ie > 0;
                var res = _getXMLInstance(isIE ? args[1].responseXML.xml : args[1].responseText);
                var fields = _getRootChildrenList(res);               
                Y.each(fields,function(v,k){
                    data[k] = _getNodeX2J(v);
                })
            }
            return data;
        };
        
         /**
        * 将j字符串转为document文档对象
        * @param {String} data 需要进行转换的字符串         
        * @return element 文档对象
        */
        var _getXMLInstance = function(data){
            if(typeof(data) == "string"){
                var xml;
                data = data.replace(/[\r\n\t]/gm,'');
                data = data.replace(/\&lt\;/g,"<").replace(/\&gt\;/g,">");
                try{
                    xml = new ActiveXObject("Microsoft.XMLDOM");
                    xml.async = false;
                    xml.loadXML(data);                   
                }catch(e){
                    xml = new DOMParser().parseFromString(data, "text/xml");
                }
                return xml;
            }
            return data;
        };   
        
        /**
        * 获得一个文档节点的子节点的名称的集合
        * 在进行非filter模式的筛选时读取根节点的所有子节点
        * @param {element} doc 获取根节点的dom对象
        */
        var _getRootChildrenList = function(doc){
            var obj ={};
            /**
            * 简单的节点过滤器
            */
            var filter = function(node){
                var arr =[];
                for(var i=0,l= node.childNodes.length;i<l;i++){
                    if(node.childNodes[i].nodeType != 3){
                        arr.push(node.childNodes[i]);
                    }
                }
                return arr;
            };
            try{
                var root = doc.documentElement||doc,tagName,temp;
                var childs = filter(root);
                Y.each(childs,function(v,k){
                    tagName = v.tagName;
                    temp = obj[tagName];
                    if(temp){
                        if({}.toString.call(temp) == "[object Array]"){
                            temp.push(v);
                        }else{
                            temp = [temp,v];
                        }
                    }else{
                        temp = v;
                    }
                    obj[tagName] = temp;
                });
                obj = childs.length ? obj : null;
            }catch(e){Tool.log("获取xml文档所有子节点发生错误");}
            return obj;
        };
        /**
         * 转换xml节点
        */
        var _getNodeX2J = function(target){
            var arr = [];
            if({}.toString.call(target) == "[object Array]"){
                for(var i = 0,l = target.length;i < l; i ++){
                    arr.push(_domNodeReader(target[i]));
                }
            }else{
                arr = _domNodeReader(target);
            }
            return arr;
        };
        
        /**
        * 解析一个节点的结构
        * @param {element} node dom节点
        */
        var _domNodeReader = function(node){
            var childs = _getRootChildrenList(node),
                nodeValue = {};

            if(childs){
                Y.each(childs,function(v,k){
                    nodeValue[k] = _getNodeX2J(v);
                });
            }else{
                nodeValue = _getNodeValue(node);
            }
            return nodeValue;
        };
        
        /**
         * 获取没有子节点的节点的值以及属性
         * @param {element} node 获取属性值的节点
        */
        var _getNodeValue = function(node){
            var getValue = function(node){  //获取text值或者属性字段
                return node.textContent||node.value||(node.nodeType == 9 ?"":node.text);
            };
            var attrs = node.attributes,value = {};
            if(attrs && attrs.length){
                value["text"] = getValue(node);
                Y.each(attrs,function(v,k){
                    value[v["name"]||v["nodeName"]] = getValue(v)||"";
                });
            }else{
                value = getValue(node);
            }
            return value;
        };
        
        return {
            convertX2J: _doConvert
        };
    }();
    
    /**
     * 网络请求类
     * @param {extraCfg} object 网络请求的配置 比如类型和需要取的字段
     * @param {requireCfg} object ajax或者jsonp方式的配置  如果是ajax，参数与Y.io一致
     * @param {func} function 执行完毕后的回调
    */
    var Request = o.Request = function(extraCfg,requireCfg,func){
        this.json = {};       //最终存放数据的对象
        this.extraCfg = this.fixExtraPara(extraCfg);
        this.target = this.extraCfg.target||"";
        this.isFilter = !(this.extraCfg.data.join(",").indexOf("*") > -1);
        this.dataType = this.extraCfg.dataType;
        this.requireCfg = this.fixReqPara(requireCfg);
        this.callBack = (func?func:function(){});
        this.doBefore();
        this.doRequest();
    };
    /**
     * 扩展相关的方法
    */
    Request.prototype = {                      
        /**
         * 修正网络请求的类型和回调对数据处理的参数
         * @param {para} object 初始化传递的参数 
        */
        fixExtraPara: function(para){
            var default_ = {dataType:"json",data:[],debugMode:false,emptyCaller:"",errorCode:/[^0]/g,checkLogin:User.defaultLogin};  //errorCode为正则表达式
            if(typeof(para.data) == "string"){
                para.data = [para.data];
            }
            return Y.merge(default_,para);
        },
        /**
         * 转义网络请求参数 
         * @param {para} object 初始化传递的参数 
         * @desc 如果方式是xml||json，则为ajax的请求，将参数从YUI格式转义为jquery格式 ，如果是jsonp方式则单独处理
        */
        fixReqPara: function(para){
            var type = this.dataType;
            var obj = {};
            if(type == "jsonp"){
                obj = Y.merge({url:"",prefix:"JsonObj"},para||{});
            }else{
                obj = Y.merge({method:"GET",async:true,data:"",url:"",context:this,on:{success:this.wrapSuccess},arguments:{}},para||{});
            }
            return obj;
        },
        /**
         * 发起请求
        */
        doRequest: function(){
            var checkLogin = this.extraCfg.checkLogin,
                chanel = window["G_MOBILE"]["channel"][G_APP_TYPE],
                url = this.requireCfg.url;
                
            var sendReq = function(){
                if(this.dataType == "json" || this.dataType == "xml"){
                    Y.io(url , this.requireCfg);
                }else if(this.dataType == "jsonp"){
                    var id_ = this.extraCfg.debugMode?"JSON" : "JSONP" + (+new Date),
                        url_ = this.requireCfg.url + (this.requireCfg.url.indexOf("?") > 0 ? "&" : "?") + this.requireCfg.prefix + "=" + id_;
                                                            
                    window[id_] = this.wrapSuccess;
                    window[id_]["context"] = this;
                    window[id_]["callBack"] = function(res){
                        this.context.wrapSuccess(res);
                    }
                    AppBase.loadScript({url:url_,handler:function(){
                        if(!(window[id_]["context"])){
                            this.wrapSuccess(window[id_]);
                        }
                    },context:this});
                }
            };
            if(checkLogin){
                 if(User.isLogin()){
                    sendReq.call(this);
                 }else{
                    if(checkLogin instanceof Function){
                        checkLogin();
                    }else{
                        User.defaultLogin();
                    }
                 }
            }else{
                sendReq.call(this);
            }
        },
        /**
         * 统一不同类型的请求的回调方法
        */
        wrapSuccess: function(){
            var that = (this.alert?arguments.callee.context:this);   
            var res = {};
            if(that.dataType == "jsonp"){
                res = arguments[0];
            }else if(that.dataType == "json"){
                res = arguments[1];
            }else if(that.dataType == "xml"){
                res = Xml2Json.convertX2J(arguments);
            }         
            that.jsonReader(res);
            that.doCheck();
            that.callBack.call(that.json,that.json);
        },   
        /**
         * json解析器
        */
        jsonReader: function(json){
            var fields = this.extraCfg.data;
            if(typeof(json) == "string"){
                eval("(json=" + json +")");
            }
            if(!this.isFilter){
                this.json = json;
            }else{
                Y.each(fields,function(v,k){
                    this.json[v] = json[v];
                },this);
            }
        },
        /**
         * 在执行请求前需要做的预处理操作
        */
        doBefore: function(){
            var obj = this.extraCfg.before;
            if(obj){
                var args = obj.args||[];
                args = args.push?args:[args];
                obj.fn && obj.fn.call(obj.context,args);
            }
            this.setStatus(1);
        },
        /**
         * 检测返回的数据和状态，先检查发生异常再检查是否为空
        */
        doCheck: function(){
            this.target = Y.one(this.target);
            if(this.target){
                var retcode = +this.json["retcode"],
                    records = this.json["records"],
                    caller = this.extraCfg.emptyCaller||this.setStatus;
                    
                if(2020001 == retcode){  /**在所有的网络回调前进行服务器端用户状态判断 废弃*/
                    User.defaultLogin();
                }
                if(this.extraCfg.errorCode.test(retcode)){
                    this.setStatus(2,retcode,this.json.retmsg);
                    return;
                }
                if(!records || records.length == 0){
                    if(caller instanceof Function){
                        caller.call(this,3);
                        return;
                    }
                }
                this.setStatus(0);
            }
        },
        /**
         * 设置目标节点的状态
         * 0:正常 1:加载 2:系统异常 3:没有数据
        */
        setStatus: function(status,code,msg){
            Tool.setStatus(this.target,status,code,msg);
        }
    }
})(AppBase);

/**
 *@ 输入文本框
 *@ useage 
 *  var text = new TextField(Y,{
 *      node:"id"||Object,
 *      emptyText:"默认显示字符串",
 *      value: ''
 *  });
 *text.getValue();
*/
var TextField = function(Y,setting){
    setting = setting||{};
    this.Y = Y;
    this._node = typeof(setting.node)=="string" ? Y.one("#"+setting.node):setting.node;
    if(!this._node){
        return;
    }
    this._emptyText = setting.emptyText||"";
    if(setting.value && this._node){
        this._node.set("value",setting.value);
        this._node.removeClass("gray");
    }else{
        this._node.set("value",this._emptyText);
        this._node.addClass("gray");
    }
    this._bind(setting);
} 
/**
 *@ 绑定文本框的事件
*/
TextField.prototype._bind = function(setting){
    if(this._node){
        this._node.on("blur",function(e){
            var target = e.target;
            var v = target.get("value").replace(/\s+$/,"");
            if(v == ""){
                this.setEmpty();
            }
            if(setting.blur){
                setting.blur.callBack.call(setting.blur.context,e);
            }
        },this);
        this._node.on("focus",function(e){
            if(this.hasClass("gray")){
                this.set("value","");
                this.removeClass("gray");
            }
        });
        if(setting.keydown){
           this._node.on("keydown",function(e){
               var v = e.target.get("value");
               setting.keydown.callBack.call(setting.keydown.context,e);                
           }); 
           this._node.on("keyup",function(e){
               var v = e.target.get("value");
               v == "" ? this.addClass("gray") : this.removeClass('gray');
           });
        }
    }
}
/**
 *@ 将文本框置为空
*/
TextField.prototype.setEmpty = function(){
    this._node.addClass("gray");
    this._node.set("value",this._emptyText);
}
/**
 *@ 获取输入文本框的值
*/
TextField.prototype.getValue = function(){
    var value = "";
    if(this._node){
        value = this._node.get("value");
        if(value == this._emptyText){
            value = "";
        }
    }
    return value;
}
/**
 *@ 验证文本框的输入
 *@ params {validater} Object 验证的条件
 *@ return Object 是否通过验证
*/
TextField.prototype.validate = function(validater){
    var b_validate = true,
        reason = "";
        value = this.Y.Tool.filterScript(this.getValue());
        
    if(validater){
        this.Y.each(validater,function(v,k){
            switch(k){
                case "EMPTY":if(value == "" || this._node.hasClass("gray")){
                                b_validate = false;
                                reason = k;
                            }
                            break;
                case "MAXLENGTH":if(value.length>v){
                                b_validate = false;
                                reason = k;
                            }
                            break;
                case "MINLENGTH":if(value.length<v && !this._node.hasClass("gray")){
                                b_validate = false;
                                reason = k;
                            }
                            break;
            }
        },this);
    }
    return {b_validate:b_validate,reason:reason};
}
/**
 *@ 文本输入框获取焦点
*/
TextField.prototype.focus = function(){
    if(this._node){
        this._node.focus();
    }
}
