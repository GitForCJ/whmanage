/**
 * 简单的电话号码输入框类
*/

var TelePhone = function(Y,cfg){
    if(!Y || !cfg){
        return;
    }
    this.Y = Y;
    this.cfg = Y.merge({
        selector:"",         //节点
        b_split:true,        //失焦时是否分隔
        emptyText:'',        //输入为空时的字符串
        focusCaller:null,   //获得焦点时的处理
        blurCaller :null,
        emptyCaller:null,    //失焦时为空的句柄
        errorCaller:null,    //失焦时输入不合法的句柄
        correctCaller:null,  //失焦时输入正常的句柄
        keyUpCaller: null,   //键盘弹起的监听事件
        keyDownCaller:null   //键盘按下时的监听事件
    },cfg||{});
    this.initialize();
}
/**
 * 文本框中的初始化
*/
TelePhone.prototype.initialize = function(){
    this.node = this.Y.one(this.cfg.selector);
    if(this.node){
        this.setValue(this.cfg.emptyText);
        this.node.addClass("gray");
        this.node.on("keyup",this.keyupHandler,this);
        this.node.on("focus",this.focusHandler,this);
        this.node.on("blur",this.blurHandler,this);
        this.node.on("keydown",this.keydownHandler,this);
        this.node.on("click",function(){if(this.getValue() == this.cfg.emptyText){this.setValue("");this.node.removeClass("gray");}},this);
    }
}
/**
 * 键盘按下时的监听
*/
TelePhone.prototype.keydownHandler = function(e){
    var length = this.getValue().length;
    var code = e.keyCode; 
    var start_1 = 48,start_2 = 96,end_1 = 57,end_2 = 105;
    if(e.ctrlKey && code==86){
        return;
    }
    if(length==0){
        if(!(code==49 || code==97)){
            e.preventDefault();
            return;
        }
    }
    var aviliable = getFieldSelection(e.target._node).length>0 || length<11;
    if((code>=start_1 && code<=end_1 && aviliable)||(code>=start_2 && code<=end_2 && aviliable)||(code>=37 && code<=40) ||(code==8)||(code==46)){
        this.node.removeClass("gray");
    }else if(code==13){
        e.preventDefault();
        this.validate(true);
    }else{
        e.preventDefault();
    }
    this.cfg.keyDownCaller && this.cfg.keyDownCaller.apply(null,[e.keyCode,this.getValue()]);    
} 
/**
 * 键盘按下弹起时的监听
*/
TelePhone.prototype.keyupHandler = function(e){
    var value = this.getValue();
    this.replaceCharCode(this.node._node);
    if (value.length >= 11) {
        if(this.validate(true)){
            this.cfg.b_split && this.setValue(this.mobileSplit(value));
        }else{
            //this.setValue("");    
            e.preventDefault();
        }
    } else if (value.length < 11) {
        if(!this.isPureNumber()){ 
            this.setValue("");    
            e.preventDefault();
        }              
    }   
    this.cfg.keyUpCaller && this.cfg.keyUpCaller.apply(null,[e.keyCode,this.getValue()]);    
}
/**
 * 输入框获取焦点时的监听
*/
TelePhone.prototype.focusHandler = function(e){
    var target = e.target,
        value = this.getValue();
    this.cfg.focusCaller && this.cfg.focusCaller.call(null,target);
    
    if(value == this.cfg.emptyText){
        value = "";
        this.node.removeClass("gray");
    }
    var pos = this.getCursorPos(target._node);
    value = value.replace(/\s/g,'');
    if(pos > 3 && pos <= 8 ){
        pos = pos -1;
    }else if(pos > 8){
        pos = pos -2;
    }
    this.setValue(value);
    this.moveAtCarAt(target._node,pos);
}
/**
 * 输入框失焦时的监听
*/
TelePhone.prototype.blurHandler = function(e){
    var target = e.target;
    var state = this.cfg.blurCaller ? this.cfg.blurCaller.call(null,this.getValue()) : true;
    if(state){  //控制blur事件是否触发
        this.replaceCharCode(this.node._node);    
        if(this.isEmpty()){  
            this.setValue(this.cfg.emptyText);
            this.node.addClass("gray");
            this.emptyCaller && this.emptyCaller.call(null,target);
        }else{
            var ret = this.validate(true),value = this.getValue();
            if(!ret && !this.isPureNumber()){
                value = "";    
                e.preventDefault();
            }
            this.cfg.b_split && this.setValue(this.mobileSplit(value));
        }
    }    
}
/**
 * 验证文本框中的输入时候合法
 *@param {Boolean} v 是否相应回调
*/
TelePhone.prototype.validate = function(v){
    var re = /^1\d{10}$/,
        value = this.getValue(),
        ret = false;
    
    this.getValue() == this.cfg.emptyText ? this.node.addClass('gray') : this.node.removeClass("gray");
    if (!(re.test(value))) {
        if(v && this.cfg.errorCaller){
            this.cfg.errorCaller.call(null,value);
        }  
    } else {     
        ret = true;     
        if(v && this.cfg.correctCaller){
            this.cfg.correctCaller.call(null,value);
        }    
    }
    return ret;
}
/**
 * 判断是否是纯的数字
*/
TelePhone.prototype.isPureNumber = function(){
    var reg = /^\d+$/,
        v = false;
    if(reg.test(this.getValue())){
        v = true;
    }
    return v;
}
/**
 * 判断文本框中的输入是否为空
*/
TelePhone.prototype.isEmpty = function(){
    var value = this.getValue();
    if(value.replace(/\s*/g,"") == ""){
        return true;
    }
    return false;
}
/**
 * 判断输入框中输入的内容是否正确
*/
TelePhone.prototype.isOK = function(target){
    var re = /^1\d{10}$/,
        value = this.getValue();
    return re.test(value);
}
/**
 * 输入框获得焦点
*/
TelePhone.prototype.getFocus = function(target){
    this.node && this.node.focus();
}
TelePhone.prototype.select = function(){
    this.node && this.node.select();
    this.validate(true);
}
/**
 * 获取输入框中的内容
*/
TelePhone.prototype.getValue = function(){
    var value = this.node.get("value");
    return value.replace(/\s*/g, "");
}
/**
 * 获取输入框中的内容的长度
*/
TelePhone.prototype.getLength = function(){
    return this.node?this.node.get("value").length:0;
}
/**
 * 设置输入框中的内容
*/
TelePhone.prototype.setValue = function(value,isSplit){
    value = isSplit ? this.mobileSplit(value||'') : value;
    this.node && this.node.set("value",value);    
}
/**
 * 将输入框中的内容进行自动分割
*/
TelePhone.prototype.mobileSplit = function(node){
    var re = /(^\s*)|(\s*)|(\s*$)/g,
        str = node.replace(re,"");
    return str.substr(0,3).concat(" ") + str.substr(3,4).concat(" ") + str.substr(7,4);
}
/**
 * 通过选取操作获取输入框中当前光标的位置
*/
TelePhone.prototype.getCursorPos = function(node){
    if (document.selection) {
        var rngSel = document.selection.createRange();
        var rngTxt = node.createTextRange();
        var flag = rngSel.getBookmark();
        rngTxt.collapse();
        rngTxt.moveToBookmark(flag);
        rngTxt.moveStart('character',-node.value.length);
        str = rngTxt.text.replace(/\r\n/g,'');
        return(str.length);
    } else {
        return node.selectionEnd;
    }
}
/**
 * 将输入框中光标移动到指定位置
*/
TelePhone.prototype.moveAtCarAt = function(node,pos){
    if (document.selection) {
        node.focus();
        var rng = document.selection.createRange();
        rng.moveStart("character",pos);
        rng.select();
    } else {
        node.setSelectionRange(pos, pos);
        node.focus();
    }
}
/**
 * 全角半角处理
*/
TelePhone.prototype.replaceCharCode = function(obj){        
    obj.value = obj.value.replace(/[\uFF00-\uFFFF]/g, function(e){
        var charCode = e.charCodeAt(0);
        if(charCode >= 65296 || charCode >= 65305){
            return String.fromCharCode(charCode - 65248);
        }
    });
}
/**
 * 选取复制的处理
*/
function getFieldSelection(field){ 
    var word = "";
    if (document.selection){
         var sel = document.selection.createRange();  
         if (sel.text.length > 0){  
             word = sel.text; 
         }               
     }else if (field.selectionStart || field.selectionStart == '0'){  
        var startP = field.selectionStart;  
        var endP = field.selectionEnd;  
        if (startP != endP){  
            word = select_field.value.substring(startP, endP);     
            field.selectionStart = field.selectionEnd = 0;
        }              
    }  
    return word;
}
