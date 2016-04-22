/*
 * HTML表单验证函数及一些实用函数
 * company: 深圳市康索特软件有限公司
 * author: 鹿振
 * version: 1.0.0
 */

//是否是数字格式的字符串
function isDigit(str)
{
    return /^\d+$/.test(str);
}

//是否是只有26个大小写英文字符的字符串
function isAlpha(str)
{
    return /^[a-zA-Z]+$/.test(str);
}

//是否只含有大写英文字符
function isUpper(str)
{
    return /^[A-Z]+$/.test(str);
}

//是否只含有小写英文字符
function isLower(str)
{
    return /^[a-z]+$/.test(str);
}

//是否只含有26个大小写英文字符和数字字符的字符串
function isAlnum(str)
{
    return /^[a-zA-Z\d]+$/.test(str);
}

//是否是可用于注册用户名和密码的字符（26个大小写英文字符、数字、下划线、横线）
function isCode(str)
{
    return /^[a-zA-Z\d_\-]+$/.test(str);
}

//是否是整数
function isInt(str)
{
    return /^[+-]?\d+$/.test(str);
}

//是否是浮点数
function isFloat(str)
{
   return /^[+-]?(0\.\d+|0|[1-9]\d*(\.\d+)?)$/.test(str);
}

//是否是邮件地址格式
function isEmail(str)
{
    return /^([a-zA-Z0-9_\-\.])+@([a-zA-Z0-9_-])+\.([a-zA-Z0-9]){2,3}$/.test(str);
    ///^[\w-]+(\.[\w-]+)*@[\w-]+(\.[\w-]+)+$/;
}

//是否闰年
function isLeapYear(str)
{
    if (isNaN(str)) return false;
    var year = parseInt(str, 10);
    return ((year % 4 == 0 && year % 100 != 0) || (year % 100 == 0 && year % 400 == 0))
}

//是否是日期格式（YYYY-MM-DD）
function isDate(str)
{
    if (str.length == 10)
    {
        var dateRegExpStr = "\\d{4}-(((01|03|05|07|08|10|12)-(0[1-9]|[12]\\d|3[01]))|((04|06|09|11)-(0[1-9]|[12]\\d|30))|(02-";
        dateRegExpStr += isLeapYear(str.substring(0, 4)) ? "(0[1-9]|[12]\\d)" : "(0[1-9]|1\\d|2[0-8])";
        dateRegExpStr += "))";

        var dateRegExp = new RegExp(dateRegExpStr);

        return dateRegExp.test(str);
    }
    return false;
}

//是否是日期格式（YYYYMMDD）
function isYearMonthDay(str)
{
    if (str.length == 8)
    {
        var dateRegExpStr = "\\d{4}(((01|03|05|07|08|10|12)(0[1-9]|[12]\\d|3[01]))|((04|06|09|11)(0[1-9]|[12]\\d|30))|(02";
        dateRegExpStr += isLeapYear(str.substring(0, 4)) ? "(0[1-9]|[12]\\d)" : "(0[1-9]|1\\d|2[0-8])";
        dateRegExpStr += "))";

        var dateRegExp = new RegExp(dateRegExpStr);

        return dateRegExp.test(str);
    }
    return false;
}

//是否是日期格式（YYYY-MM）
function isYearMonth(str)
{
    return /^\d{4}-(0[1-9]|1[0-2])$/.test(str);
}
//是否是日期格式（YYYYMM）
function isYearMonth2(str)
{
    return /^20[0-2]\d((0[1-9])|(1[0-2]))$/.test(str);
}

//是否是时间格式（hh:mm:ss）
function isTime(str)
{
    return /^([01]\d|2[0-3]):[0-5]\d:[0-5]\d$/.test(str);
}

//是否是日期时间格式（YYYY-MM-DD hh:mm:ss）
function isDateTime(str)
{
    return (str.length == 19) ? (isDate(str.substring(0, 10)) && isTime(str.substring(11, 19))) : false;
}

//获得某年的某个月有多少天 或者说 某年某月的最后一天是多少。
function getDays(year, month)
{
    switch (parseInt(month, 10))
    {
        case 1: case 3: case 5: case 7: case 8: case 10: case 12: return 31;
        case 4: case 6: case 9: case 11: return 30;
        case 2: return isLeapYear(year) ? 29 : 28;
    }
}

//计算两个日期相差天数（sDate1 - sDate2），sDate1和sDate2是"yyyy-mm-dd"格式
function dateDiff(sDate1, sDate2)
{
    var aDate, oDate1, oDate2, iDays
    aDate = sDate1.split("-")
    oDate1 = new Date(aDate[1] + '-' + aDate[2] + '-' + aDate[0])  //转换为12-18-2002格式
    aDate = sDate2.split("-")
    oDate2 = new Date(aDate[1] + '-' + aDate[2] + '-' + aDate[0])
    iDays = parseInt((oDate1 - oDate2) / 1000 / 60 / 60 /24)  //把相差的毫秒数转换为天数
    return iDays
}

//计算两个日期相差的月数（sDate1 - sDate2），sDate1和sDate2是"yyyy-mm-dd"格式
function monthDiff(sDate1, sDate2)
{
    var aDate, oDate1, oDate2, iDays
    aDate = sDate1.split("-")
    oDate1 = new Date(aDate[1] + '-' + aDate[2] + '-' + aDate[0])  //转换为12-18-2002格式
    aDate = sDate2.split("-")
    oDate2 = new Date(aDate[1] + '-' + aDate[2] + '-' + aDate[0])
    imonths = parseInt((oDate1 - oDate2) / 1000 / 60 / 60 /24/30)  //把相差的毫秒数转换为月数
    return imonths
}

//是否是IP地址格式（用点"."分割的四组数字中，第一组数字范围1-223，其他三组数字范围0-255）
function isIP(str)
{
    return /^(0?0?[1-9]|0?[1-9]\d|1\d\d|2[01]\d|22[0-3])(\.([01]?\d?\d|2[0-4]\d|25[0-5])){3}$/.test(str);
}

//对于IP地址中的每组数字如果不足3位，则前面补零。例如："192.168.1.22"，结果"192.168.001.022"
function getFullIP(ip)
{
    var nums = ip.split(".");
    var fullIP = "";
    fullIP += repeatString("0", 3 - nums[0].length) + nums[0];
    for (var i = 1; i < nums.length; i++)
        fullIP += "." + repeatString("0", 3 - nums[i].length) + nums[i];
    return fullIP;
}

//验证是否是MAC地址（用英文冒号":"分割的6组两位字符，其中字符只能是英文字符或数字，例如：00:03:0D:61:62:7C）
function isMAC(mac)
{
    return /^[\dA-Za-z]{2}(\:[\dA-Za-z]{2}){5}$/.test(mac);
}

/*
是否是英文逗号","分隔的数字格式的号码串。
具体判断规则：
1、每个号码必须是数字格式；
2、号码之间可以用英文逗号、空格、换行符或其组合分隔。
*/
function isPhoneNumbers(str)
{
    return /^[\s,]*\d+([\s,]+\d+)*[\s,]*$/.test(str);
}

/*
是否是英文逗号";"分隔的11位数字的无重复的号码串。
返回值：1 - 手机号码只能为11位数字或11位数字与英文分号分隔
      2 - 手机号码有重复
*/
function isNoRepeatPhoneNumbers(str){
    var result;
    if(!/^[1-9]\d{10}(;[1-9]\d{10})*$/.test(str)){
       return 1;
    }
    var strs = str.split(";");
    if(strs.length>1){
      ss:for(var i=0;i<strs.length;i+=1){
          for(var j=i+1;j<strs.length;j+=1){
           if(strs[i]==strs[j]){
             result = 2;
             break ss;
           }
         }
      }
    }
    return result;
}

/*
获得repeat个字符串str的连接结果
@param str 要重复的字符串
@param repeat 重复的次数
*/
function repeatString(str, repeat)
{
    var repeatStr = "";
    for (var i = 0; i < repeat; i++)
        repeatStr += str;
    return repeatStr;
}

//是否包含非法字符，针对文件名。（\、/、|、"、?、*、:、<、>）
function containInvalidChar(str)
{
    return /[\\\/\|"\?\*:<>]/.test(str);
}


//获得字符串对象的字节数
function bytes(str)
{
    return str.replace(/[^\x00-\xff]/g,"**").length;
}

//去除字符串两边的空格
String.prototype.trim = function()
{
    return this.replace(/(^\s*)|(\s*$)/g, "");
}

//数组中是否包含了指定元素
Array.prototype.contains = function(obj)
{
    for (var i = 0; i < this.length; i++)
        if (this[i] === obj) return true;

    return false;
}

/*
设置选择框按钮的选中状态
@param checkBox 选择框按钮的名称
@param checked true选中所有，false取消所有。
       此参数可省略，省略时，每一次调用该方法都会循环的选中、取消、选中...
*/
function setCheckedState(checkBox, checked)
{
    if (!checkBox) return;
    var checked;
    if (typeof(flag) != "undefined")
        checked = flag;
    if (checkBox.length)
    {
        checked = !checkBox[0].checked;
        for (var i = 0; i < checkBox.length; i++)
            checkBox[i].checked = checked;
    }
    else
        checkBox.checked = !checkBox.checked;
}

//获得选择框按钮组被选中的个数
function checkedSize(checkBox)
{
    var size = 0;
    if (typeof(checkBox) != undefined)
    {
        if (checkBox.length)
        {
            for (var i = 0; i < checkBox.length; i++)
                if (checkBox[i].checked) size++;
        }
        else if (checkBox.checked) size++;
    }
    return size;
}

/*
清除单选、多选按钮组的选中状态
@param field：单选或多选按钮组的名称
*/
function clearChecked(field)
{
    if (field)
    {
        if (!field.length)
            field.checked = false;
        else
            for (var i = 0; i < field.length; i++)
                field[i].checked = false;
    }
}

/*
清除指定表单的字段值。
如果表单字段为 文本框 ，则清空
如果表单字段为 下拉选择框 ，则选择第一项
如果表单字段为单选或多选按钮，则全清选中状态
*/
function clearForm(theForm)
{
    var len = theForm.elements.length;
    for (var i = 0; i < len; i++)
    {
        with (theForm.elements[i])
        {
            switch (tagName)
            {
                case "INPUT":
                    var inputType = getAttribute("type");
                    if (inputType == "text")
                        value = "";
                    else if (inputType == "checkbox" || inputType == "radio")
                        clearChecked(theForm.elements[i]);
                    break;
                case "SELECT":
                    selectedIndex = 0;
                    break;
            }
        }
    }
}

/*
从源列表框向目标列表框复制元素
@param fromSelect 源列表框对象
@param toSelect 目标列表框对象
*/
function copySelect(fromSelect, toSelect)
{
    var fOpts = fromSelect.options;
    var tOpts = toSelect.options;

    for (var i = 0; i < fOpts.length; i++)
    {
        if (fOpts[i].selected)
        {
            for (var j = 0; j < tOpts.length; j++)
            {
                var repeat = false;
                if (tOpts[j].value == fOpts[i].value)
                {
                    repeat = true;
                    break;
                }
            }

            if (!repeat) tOpts.add(new Option(fOpts[i].text, fOpts[i].value));
        }
    }
}

/*
移除列表框中的元素
@param select 列表框对象
*/
function removeSelect(select)
{
    for (var i = select.options.length - 1; i >= 0; i--)
        if (select.options[i].selected)
            select.remove(i);
}

/*
移动源列表框中选中的项目到目标列表框中，并按照指定排序函数对列表框中的值进行排序。
@param fromSelect 源列表框对象
@param toSelect 目标列表框对象
@param compare 比较大小函数，用于排序目的。
*/
function moveSelect(fromSelect, toSelect, compare)
{
    outer:
    for (var i = fromSelect.length - 1; i >= 0; i--)
    {
        if (fromSelect[i].selected)
        {
            var opt = new Option(fromSelect[i].text, fromSelect[i].value);
            fromSelect.remove(i);
            if (toSelect.length > 0)
            {
                for (var j = 0; j < toSelect.length; j++)
                {
                    if (compare(opt.value, toSelect[j].value) < 0)
                    {
                        toSelect.add(opt, j);
                        continue outer;
                    }
                }
            }
            toSelect.add(opt);
        }
    }
}

/*
移动源列表框中所有的项目到目标列表框中，并按照指定排序函数对列表框中的值进行排序。
@param fromSelect 源列表框对象
@param toSelect 目标列表框对象
@param compare 比较大小函数，用于排序目的。
*/
function moveAllSelect(fromSelect, toSelect, compare)
{
    outer:
    for (var i = fromSelect.length - 1; i >= 0; i--)
    {
        var opt = new Option(fromSelect[i].text, fromSelect[i].value);
        fromSelect.remove(i);
        if (toSelect.length > 0)
        {
            for (var j = 0; j < toSelect.length; j++)
            {
                if (compare(opt.value, toSelect[j].value) < 0)
                {
                    toSelect.add(opt, j);
                    continue outer;
                }
            }
        }
        toSelect.add(opt);
    }
}

/* 以整数比较两个参数的大小，若 num1 > num2 则返回大于0的值，若 num1 == num2 则返回0，否则返回小于0的值 */
function compareInt(num1, num2)
{
    return parseInt(num1) - parseInt(num2);
}

/* 以浮点数比较两个参数的大小，若 num1 > num2 则返回大于0的值，若 num1 == num2 则返回0，否则返回小于0的值 */
function compareFloat(num1, num2)
{
    return parseFloat(num1) - parseFloat(num2);
}

/* 以字符串比较两个参数的大小，若 num1 > num2 则返回1，若 num1 == num2 则返回0，否则返回-1的值 */
function compareString(str1, str2)
{
    var s1 = new String(str1);
    var s2 = new String(str2);

    if (s1 > s2) return 1;
    else if (s1 == s2) return 0;
    else return -1;
}

/* 根据系统定义的操作符直接比较两个参数的大小，若 num1 > num2 则返回1，若 num1 == num2 则返回0，否则返回-1的值 */
function compare(a1, a2)
{
    if (a1 > a2) return 1;
    else if (a1 == a2) return 0;
    else return -1;
}

/*
拷贝数据，支持对数组、对象和基本类型的数据拷贝。
*/
function clone(obj)
{
    if(typeof(obj) != "object") return obj;
    var obj2;
    if(obj instanceof Array)
    {
        obj2 = [];
        for (var i = 0; i < obj.length; i++)
        {
            obj2[i] = typeof(obj[i]) == "object" ? clone(obj[i]) : obj[i];
        }
    }
    else
    {
        obj2 = {};
        for(i in obj)
        {
            obj2[i] = typeof(obj[i])=="object" ? clone(obj[i]) : obj[i];
        }
    }
    return obj2;
}

/*
选中目标下拉列表框种的一个元素
@param select 目标下拉列表框
@param value 要选中的值
*/
function selectItem(select, value)
{
    var opts = select.options;
    for (var i = 0; i < opts.length; i++)
    {
        if (opts[i].value == value)
        {
            opts[i].selected = true;
            break;
        }
    }
}

/*
选中下拉列表框的所有项
@param select 下拉列表框
*/
function selectAll(select)
{
    for (var i = 0; i < select.options.length; i++)
        select.options[i].selected = true;
}

/*
获得某年某月的所有周并写入目标下拉列表中。
规则：从当月的第一天算起，每7天为一周，最后一天可能不为7天，也算作一周。
目标下拉列表中的元素为（举例）：
<option value=""></option>
<option value="1">第1周</option>
<option value="2">第2周</option>
......
@param weekSelect 目标下拉列表框
@param year 年
@param month 月
*/
function setWeekSelect(weekSelect, year, month)
{
    var options = weekSelect.options;
    options.length = (options.length > 0 && options[0].value == "") ? 1 : 0;

    if (isNaN(parseInt(year, 10)) || isNaN(parseInt(month, 10)))
        return;

    var weeks = (!isLeapYear(year) && parseInt(month) == 2) ? 4 : 5;
    for (var i = 1; i <= weeks; i++)
        options.add(new Option("第" + i + "周", i));
}

//该方法将会寻找具有id和parent的属性的文本框，并对每种相同名字的所有文本框从最低层向上累加。
function initAddUp()
{
    this.hasChild = function(texts, text)
    {
        for (var i = 0; i < texts.length; i++)
            if (texts[i].parent == text.id)
                return true;

        return false;
    }

    this.hasSameParent = function(texts, text)
    {
        for (var i = 0; i < texts.length; i++)
            if (texts[i].parent == text.parent)
                return true;
        return false;
    }

    this.getLastChilds = function(texts)
    {
        var lastChilds = new Array();
        for (var i = 0; i < texts.length; i++)
            if (!hasChild(texts, texts[i]) && !hasSameParent(lastChilds, texts[i]))
                lastChilds.add(texts[i]);

        return lastChilds;
    }

    var allText = document.getElementsByTagName("INPUT");
    var textNames = new Array();
    for (var i = 0; i < allText.length; i++)
    {
        if (allText[i].id && allText[i].parent && !textNames.contains(allText[i].name))
            textNames.add(allText[i].name);
    }

    for (var i = 0; i < textNames.length; i++)
    {
        var texts = document.getElementsByName(textNames[i]);
        var lastTexts = getLastChilds(texts);
        for (var j = 0; j < lastTexts.length; j++)
            addUp(lastTexts[j]);
    }
}

/*
对文本框输入的数据累加到其所属的高层的文本框中。
要求该文本框必须有name、id和parent属性。
@param text 要累加的文本框
*/
function addUp(text)
{
    this.text = text;

    this.texts = document.getElementsByName(text.name);

    this.getParentText = function(text)
    {
        for (var i = 0; i < texts.length; i++)
            if (texts[i].id == text.parent)
                return texts[i];
    }

    this.add = function(text)
    {
        var parentText = getParentText(text);
        if (parentText === undefined)
        {
            text.value = Math.round(text.value * 100) / 100;
            return;
        }

        var sum = 0;
        for (var i = 0; i < texts.length; i++)
            if (texts[i].parent == text.parent && isFloat(texts[i].value))
                sum += parseFloat(texts[i].value);

        text.value = Math.round(text.value * 100) / 100;
        parentText.value = Math.round(sum * 100) / 100;

        add(parentText);
    }

    add(text);
}

/*
根据 url 打开一个规定宽带和高度的新弹出窗口，新窗口将在屏幕中间稍微靠上显示，url中的参数可以从表单中获得。
@param url 打开新窗口的路径
@param width 窗口宽度
@param height 窗口高度
@param oForm 要获取参数的表单，可省略。
@return 返回打开窗口的对象
*/
function openWin(url, width, height, oForm)
{
    if (oForm != undefined)
    {
        var params = "";
        var len = oForm.elements.length;
        for (var i = 0; i < len; i++)
        {
            with (oForm.elements[i])
            {
                switch (tagName)
                {
                    case "SELECT":
                        params += "&" + getAttribute("name") + "=" + encodeString(value);
                        break;
                    case "INPUT" :
                        var type = getAttribute("type");
                        if (type == "text" || type == "hidden")
                            params += "&" + getAttribute("name") + "=" + encodeString(value);
                        break;
                }
            }
        }
        if (url.indexOf("?") == -1)
            url += "?" + params.substr(1);
        else url += params;
    }
    var left = (window.screen.width - width) / 2;
    var top = (window.screen.height - height) / 2;
    window.open(url, "", "width=" + width + ",height=" + height + ",top=" + top + ",left=" + left + ",resizable=yes,scrollbars=yes");
}

//对url中的特殊英文字符进行编码
function encodeString(str)
{
    var s = "";
    for (var i = 0; i < str.length; i++)
    {
        s += str.charCodeAt(i) <= 255 ? encodeURIComponent(str.charAt(i)) : str.charAt(i);
    }
    return s;
}

/*
写入HTML编码的英文字符空格。
@param num 写入的空格数。可省略，省略时，写入的空格长度与文本框的默认长度(18)相等。
*/
function writeSpaces(num)
{
    var space = "&nbsp;";
    var repeats = num == undefined ? 18 : num;
    document.write(repeatString(space, repeats));
}

/*
根据传入的字符串在其后面写入HTML编码的英文空格以使字符串和空格的长度大致等于文本框的默认长度。
@param str 字符串，可省略，省略时写入文本框默认长度的空格。
*/
function fillSpaces(str)
{
    var totalLen = 18;
    var strLen = str == undefined ? 0 : byteSize(str);
    var spaceLen = totalLen - strLen;
    var space = "&nbsp;";
    document.write(str + repeatString(space, spaceLen));
}
/*
根据传入的字符串判断其长度是否为零，如果否，则显示字符串内容。
@param str 提示的信息。
*/
function showMessage(str)
{
   if(str.length!=0){
   		alert(str);
   }
}

/**
验证输入的联系人是否合法
*/
function checkContact(text){
	var contact = text.value;
	if(contact.length>10){
		alert("联系人不能大于10个英文字符或5个中文字符");
		text.focus();
		return false;
	}
	return true;
}
/**
验证输入的联系地址是否合法
*/
function checkAddress(text){
	var addr = text.value;
	if(addr.length>50){
		alert("联系地址不能大于50个英文字符或25个中文字符");
		text.focus();
		return false;
	}
	return true;
}

/*
验证输入的联系电话格式及长度是否合法
*/
function checkPhoneNumber(text){
	var phone = text.value;
	if(phone.length>0){
		if(!isDigit(phone)){
			alert("联系电话必须为数字，请重新输入");
			text.focus();
			return false;
		}
		if(phone.length>12){
			alert("联系电话不能大于12个数字字符，请重新输入");
			text.focus();
			return false;
		}
	}
	return true;
}

/*
验证输入的银行卡号是否合法
*/
function checkbankcard(text){
	var bankcard = text.value;
	if(bankcard.length>0){
		if(!isDigit(bankcard)){
			alert("银行账号必须为数字，请重新输入");
			text.focus();
			return false;
		}
	}
	if(bankcard.length>25){
	  	alert("银行账号不能大于25个数字字符，请重新输入");
	  	text.focus();
		return false;
	}
	return true;
}

/*
验证输入的开户银行的长度是否超过25个中文字符
*/	
function checkbank(text){
	var bank = text.value;
	if(bank.length>25){
	  	alert("开户银行不能大于50个英文字符或25个中文字符，请重新输入");
	  	text.focus();
		return false;
	}
	return true;
}

/*
验证输入的税号是否合法
*/
function checktaxnum(text){
	var taxnum = text.value;
	if(taxnum.length>0){
		if(!isDigit(taxnum)){
			alert("税号必须为数字，请重新输入");
			text.focus();
			return false;
		}
	}
	if(taxnum.length>25){
	  	alert("税号不能大于25个数字字符，请重新输入");
	  	text.focus();
		return false;
	}
	return true;
}

/*
用于验证所选的年月是否大于当前年月
str1 年份 
str2 月份
*/
function isafternow(str1,str2)
{
	with(document.forms[0]){
		var ym = str1.value+str2.value;
		tmpDate = new Date();
		var month = (tmpDate.getMonth()+1)+"";
		if(month.length==1){
			month = "0"+month;
		}
		var year = tmpDate.getYear()+"";
		var current = year+month;
		if(ym>current){
			alert("选择的年月不能大于当前年月！"+current);
			str2.focus();
		    return false;
		}else{
			return true;
		}
	}
}
/*
 用于验证起始年月不能大于终止年月
*/
function comparetowmonth(str1,str2,str3,str4)
{
	with (document.forms[0]) {
		var ym1 = str1.value+str2.value;
		var ym2 = str3.value+str4.value;
		if(ym1>ym2){
			alert("起始年月不能大于终止年月！");
			str4.focus();
		     return false;
		}else{
			return true;
		}
	}
}

/*
 用于验证起始年月不能大于终止年月
*/
function comparetowdate(str1,str2)
{
	var date1 = str1.value;
	var date2 = str2.value;
	with (document.forms[0]) {
		if(date1>date2){
			alert("起始日期不能大于终止日期！");
			str2.focus();
		    return false;
		}else{
			return true;
		}
	}
}




/**
 * 限制数字输入位和字符：验证和限定只能为数字的输入框的输入和位数（只能输入多少位数字）
 * 算法：返回FALSE即可让用户此次按下的键盘无效
 * 注：该方法需要放在输入对象的onkeydown事件中,如：onkeydown="return getInput(tmp,e,len);"
 * @param bandno input对象
 * @param e 动作产生的事件对象
 * @param len 限定最多是多少位
 * @return boolean
 * @author leichengjian
 */
function getInput(bandno,e,len){
	var bandno = bandno.value.trim();
	//如是删除键和delete键或者上下左右光标键
	if(e.keyCode==8||e.keyCode==46||(e.keyCode>=37&&e.keyCode<=40)){
		return true;
	}
	if(bandno.length==0&&e.ctrlKey&&e.keyCode==86){
		//alert("当长度为0，启用粘贴");
		return true;
	}
	//如果长度已经为len位则视为无效
	if(bandno.length==len){
		return false;
	}
	//如果按下的键不在大键盘0-9，也不在小键盘0-9，则视为无效
	if((e.keyCode<48||e.keyCode>57)&&(e.keyCode<96||e.keyCode>105)){
		return false;
	}
}

/**
 * 限制变量的输入位，如果总位数达到len时，让用户输入无效
 * 算法：返回FALSE即可让用户此次按下的键盘无效
 * 注：该方法需要放在输入对象的onkeydown事件中,如：onkeydown="return limitInputCount(tmp,e,len);"
 * @param tmp input对象
 * @param e 动作产生的事件对象
 * @param len 限定最多多少位
 * @return boolean
 * @author leichengjian
 */
function limitInputCount(tmp,e,len){
	var tmp_var = tmp.value.trim();
	//如是删除键和delete键或者上下左右光标键
	if(e.keyCode==8||e.keyCode==46||(e.keyCode>=37&&e.keyCode<=40)){
		return true;
	}
	//如果已经达到最大位数
	if(tmp_var.length==len){
		return false;
	}
}


/**
 * 根据卡序号，找出该卡所对应的UID，并设置到input对象中
 *
 * @param com  Active控件的通信串口
 * @param obj  CardManager对象（用于操作设备的Active控件对象）
 * @param cardsn 卡序号
 * @param new_cardid 要设置的input对象
 * @return boolean	 设置是否成功
 * @author leichengjian
 */
function getUIDByCardsn(com,obj,cardsn,new_cardid){
	var flag;
    var xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
    xmlhttp.open("post","../card/card.do?method=getUIDByCardsn&cardsn="+cardsn,false);
    xmlhttp.onreadystatechange=function(){
		if(xmlhttp.readyState==4&&xmlhttp.status==200){
		  flag = xmlhttp.responseText;
	    if(flag != "failure"){
	      new_cardid.value = flag;
	      return true;
	    } else {
	    	alert("获取数据库UID失败!uid不存在！请确认!");
	    	return false;
	    	}
		}
	}
	xmlhttp.send(null);
}

/**
 * 将页面中相应的deptcode_* 设置成被选择状态
 * 根据计次消费规则编号（计费和限次两种）和计次消费规则类型（取值：1-计费,2-限次），得到已绑定关系的部门编号------得到的部门编号数据(格式：“部门编号|部门编号” )
 * 将部门编号数据进行 “|” 分离，并根据关联将页面的表单中的 "deptcode_"+部门编号  设置为已选择状态
 *
 * @param frm document 页面的document对象
 * @param code 计次消费规则（计费和限次两种）
 * @param type 计次消费规则类型（取值：1-计费,2-限次）
 * @return boolean	 
 * @author leichengjian
 */
function getCountRuleByCode(frm,code,type){
	var flag;
    var xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
    xmlhttp.open("post","../consume/consume.do?method=getCountRuleByCode&code="+code+"&type="+type,false);
    xmlhttp.onreadystatechange=function(){
		if(xmlhttp.readyState==4&&xmlhttp.status==200){
		  flag = xmlhttp.responseText;		  
		  //alert(flag);
	    if(flag != "failure"){
	      var str = flag.split("|");
		  for(var i=0;i<str.length;i++){
	  		 var deptcode_var = document.getElementById("deptcode_"+str[i]);
	  		 if(deptcode_var != null){
	  		 	deptcode_var.checked = true;
	  		 }
		  }
	      return true;
	    } else {
	    	alert("获取已绑定关系的部门编号失败！可能数据库断开，请向管理员确认!");
	    	return false;
	    	}
		}
	}
	xmlhttp.send(null);
}

/**
 * 将查询到的计次消费(计费和限次两种)规则详细显示到页面span中
 *
 * @param rule_display 页面的span对象
 * @param code 计次消费规则（计费和限次两种）的编号
 * @param type 计次消费规则类型（取值：1-计费,2-限次）
 * @return boolean	 
 * @author leichengjian
 */
function getCountRuleDetailByCode(rule_display,code,type){
	if(code==""){
		alert("获取计费规则失败，可能无规则或数据库连接断开，请向管理员确认!");
		return;
	}
	var flag;
    var xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
    xmlhttp.open("post","../consume/consume.do?method=getCountRuleDetailByCode&code="+code+"&type="+type,false);
    xmlhttp.onreadystatechange=function(){
		if(xmlhttp.readyState==4&&xmlhttp.status==200){
		  flag = xmlhttp.responseText;		  
		  //alert(flag);
	    if(flag != "failure"){
	      var str = flag.split("|");
		  rule_display.innerHTML = "规则名：<font color='red'>"+str[0]+"</font>，规则所属餐段：<font color='red'>"+str[1]+"</font>，餐段开始时间：<font color='red'>"
	  							   +str[2]+"</font>，餐段结束时间：<font color='red'>"+str[3]+"</font>，计费"+str[5]+"：<font color='red'>"+str[4]+"</font>";
	      return true;
	    } else {
	    	alert("获取规则失败，可能无规则或数据库连接断开，请向管理员确认!");
	    	return false;
	    	}
		}
	}
	xmlhttp.send(null);
}



/*
 *非法字符：
 */
function checkInvalidChar(str){
	return /[^a-zA-Z0-9\_\u4e00-\u9fa5]/.test(str);
}

/*
 * 缴费时，通过原始卡号与卡类型得到该卡的相关信息和缴费科目信息
 * @param cardid 原始卡号（UID）
 * @param type 卡类型
 * @return null
 * @author leichengjian
 */
function getSubjectMSGbyID(cardid, type,deptcode,deptname,div_subject,span_count,back) {
    var flag=0;
	var keyInfo;
    var xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
    xmlhttp.open("post","../account/fund.do?method=getCustomerMSG&cardid="+cardid+"&type="+type,false);
    xmlhttp.onreadystatechange=function(){
		if(xmlhttp.readyState==4&&xmlhttp.status==200){
	    keyInfo = xmlhttp.responseText;
//alert(keyInfo);
	    if(keyInfo != "failure") {
	    	var tt = eval('('+keyInfo+')');
	    	var size = tt.sublist.length;
	    	deptcode.value = tt.deptcode;
	    	deptname.value = tt.deptname;
	    	var str="需要缴纳的费用：";
	    	var count=0;
	    	for(var i=0;i<size;i++){
	    		str += '<br/><input type="checkbox" checked="true" name="deptsubid" onClick="change(this.checked,'
	    			+ Math.round(tt.sublist[i].money/100)+')" value="'+tt.sublist[i].id+'">'+tt.sublist[i].subname+'：'
	    			+ Math.round(tt.sublist[i].money/100)+'元';
	    		if(i!=size-1){
	    			str += '，';
	    		}
	    		count +=tt.sublist[i].money/100;
	    		
	    	}
//alert("div_subject="+str);
	    	div_subject.innerHTML=str;
	    	span_count.style.display="block";
	    	span_count.innerHTML = "合计费用："+Math.round(count*100)/100+"元。";
	    	back.value=count;
	    	return 1;
	    } else {
	    	alert("未找到该卡的相关信息或缴费科目信息！，请联系管理员！");
	    	return 0;
	    	}
		}
	}
	xmlhttp.send(null);
	return flag;
}

//对变量往左补足count位的"0"
function add_0_var(tmp,count){
	var var_tmp = tmp; 
	for(var i=0;i<count - tmp.length;i++){
		var_tmp = "0" + var_tmp;
	}
	return var_tmp;
}


/**
 * 根据flag（0-隐藏，1-显示），隐藏或显示sp_model、sp_choose两个<span>标签
 * @param flag标志（0-隐藏，1-显示）
 * @param sp_model  <span>标签
 * @param sp_choose <span>标签
 *
 * @date 2011-09-05
 * @author leichengjian
 */
function setTouchModel(flag,sp_model,sp_choose){
	if(flag==0){
		sp_model.style.display = "none";
		sp_choose.style.display = "none";
	}else{
		sp_model.style.display = "inline";
		sp_choose.style.display = "inline";
	}
}
/**
*js设置cookie
*/
function setCookie(name,value)//两个参数，一个是cookie的名子，一个是值
{
    var Days = 30; //此 cookie 将被保存 30 天
    var exp  = new Date();    //new Date("December 31, 9998");
    exp.setTime(exp.getTime() + Days*24*60*60*1000);
    document.cookie = name + "="+ escape (value) + ";expires=" + exp.toGMTString();
}
function getCookie(name)//取cookies函数        
{
    var arr = document.cookie.match(new RegExp("(^| )"+name+"=([^;]*)(;|$)"));
     if(arr != null) return unescape(arr[2]); return null;

}

/**
 * 根据eigencode(企业特征码)获得企业发卡配置信息以及企业明文密钥
 * @param eigencode 企业特征码
 * @date 2011-11-18
 * @author danzhiping
 */
function getCardConfig(eigencode){
    var cardConfig;
    var xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
    xmlhttp.open("post","../setting/company.do?method=getCardConfigByEigencode&eigencode="+eigencode,false);
    xmlhttp.onreadystatechange=function(){
		if(xmlhttp.readyState==4&&xmlhttp.status==200){
	       var str = xmlhttp.responseText;
	       if(str=='noupt'){
	          cardConfig = ["noupt","","","","","",""];
	       }else if(str=='failure'){
	          cardConfig = ["failure","","","","","",""];
	       }else{
	          var vobj_sep = str.split("|");
	          cardConfig = [vobj_sep[0],vobj_sep[1],vobj_sep[2],vobj_sep[3],vobj_sep[4],vobj_sep[5],vobj_sep[6]];
	       }
	    }
	}
	xmlhttp.send(null);
	return cardConfig;
}

/**
 * 根据企业特征码设置企业密钥(脱机工具使用)
 * 返回0--密钥未更新 -1--卡片类型错误 1--成功
 * @param eigencode 企业特征码
 * @param cardtype 卡片类型 02-13.56M 03-2.4G
 * @param companyKeyObj 企业密钥隐藏域
 * @date 2012-05-03
 * @author danzhiping
 */
function setCompanyKey(eigencode,cardtype,companyKeyObj){
   var cardConfig = getCardConfig(eigencode);
   if(cardConfig[0]=='noupt'){
       alert("所选企业需要更新密钥才能正常使用,请到“企业管理”按“温馨提示”操作");
       return 0;
   }else{
       if(cardtype=='02'){
         set_obj_cardkey(eigencode,companyKeyObj);
         return 1;
       }else if(cardtype=='03'){
         set_obj_rfcardkey(eigencode,companyKeyObj);
         return 1;
       }else{
         alert("卡片类型错误："+cardtype);
         return -1;
       }
   }
}

/**
 * Ajax实现使用脱机工具记录操作日志
 * 0 -- 失败  1 -- 成功
 * @param message 操作信息
 * @param explain 操作原因
 * @date 2011-05-03
 * @author danzhiping
 */
function writeOffLineLog(message,explain){
    var state;
    var xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
    xmlhttp.open("post","../log/operatelog.do?method=writeOffLineLog&message="+message+"&explain="+explain,false);
    xmlhttp.onreadystatechange=function(){
		if(xmlhttp.readyState==4&&xmlhttp.status==200){
	       state = xmlhttp.responseText;
	       
	    }
	}
	xmlhttp.send(null);
	return state;
}

/**
 * 根据卡序号获取客户对应部门、职位信息
 * @param cardsn 卡序号
 * @date 2011-05-08
 * @author danzhiping
 */
function getCustInfoByCardsn(cardsn){
    var custinfo;
    var xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
    xmlhttp.open("post","../card/card.do?method=getCustInfoByCardsn&cardsn="+cardsn,false);
    xmlhttp.onreadystatechange=function(){
		if(xmlhttp.readyState==4&&xmlhttp.status==200){
	       var str = xmlhttp.responseText;
	       if(str=='failure'){
	          custinfo = ["failure","",""];
	       }else{
	          var vobj_sep = str.split("|");
	          custinfo = [vobj_sep[0],vobj_sep[1],vobj_sep[2]];
	       }
	    }
	}
	xmlhttp.send(null);
	return custinfo;
}

/**
 * 充值-修改原始交易流水(正常)
 * @param id 日志编号
 * @param tradetime 交易时间
 * @param tradetype 交易类型
 * @date 2011-05-11
 * @author danzhiping
 */
function setFillFee(id,tradetime,tradetype){
    var xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
    xmlhttp.open("post","../account/fee.do?method=setFillFee&id="+id+"&tradetime="+tradetime+"&tradetype="+tradetype,false);
    xmlhttp.onreadystatechange=function(){
		if(xmlhttp.readyState==4&&xmlhttp.status==200){
	       var str = xmlhttp.responseText;
	       //alert(str);
	    }
	}
	xmlhttp.send(null);
}

/**
 * 根据部门编号设置部门名称到页面隐藏域
 * @param deptObj 部门名称隐藏域
 * @param deptcode 部门编号
 * @date 2011-05-18
 * @author danzhiping
 */
function setDeptName(deptObj,deptcode){
    var xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
    xmlhttp.open("post","../setting/dept.do?method=setDeptName&deptcode="+deptcode,false);
    xmlhttp.onreadystatechange=function(){
		if(xmlhttp.readyState==4&&xmlhttp.status==200){
	       var str = xmlhttp.responseText;
	       if(str!='failure'){
	          deptObj.value = str;
	       }else{
	          alert("根据部门编号获取部门名称失败，请重试！");
	       }
	    }
	}
	xmlhttp.send(null);
}

/**
 * 插入结算金额转款原始灰记录(写入卡片成功修改状态)
 * @param userid 工号
 * @param cardsn 卡序号
 * @param acctleft 结算金额
 * @date 2011-05-22
 * @author danzhiping
 */
function writeMoneyTransferLog(userid,cardsn,acctleft){
    var xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
    xmlhttp.open("post","../account/fund.do?method=moneyTransferQryList&deptcode="+deptcode,false);
    xmlhttp.onreadystatechange=function(){
		if(xmlhttp.readyState==4&&xmlhttp.status==200){
	       var str = xmlhttp.responseText;
	       if(str!='failure'){
	          deptObj.value = str;
	       }else{
	          alert("根据部门编号获取部门名称失败，请重试！");
	       }
	    }
	}
	xmlhttp.send(null);
}

/**
 * 插入综合统计报表操作员输入操作员实收款
 * @param userid 工号
 * @param cardsn 卡序号
 * @param acctleft 结算金额
 * @date 2011-05-22
 * @author danzhiping
 */
function synrealMoney(){
    document.all.btnUpdate.disabled=true;
    var realMoney = document.all.realMoney.value;
    if(realMoney.trim().length==0){
		alert("请输入实收款！");
		document.all.btnUpdate.disabled=false;
		document.all.realMoney.focus();
		return;
	}
	if(!/^((\d+)|(\d+\.\d{1,2}))$/.test(realMoney)){
    	alert("实收款金额格式不对！");
    	document.all.btnUpdate.disabled=false;
		document.all.realMoney.focus();
		return;
  	}
	if(/^[0\.]*$/.test(realMoney)){
    	alert("实收款金额不能为零！");
    	document.all.btnUpdate.disabled=false;
		document.all.realMoney.focus();
		return;
  	}
    var realDate = document.all.realDate.value;
    var realPerson = document.all.realPerson.value;
    var shouldMoney = document.all.shouldMoney.value;
    var xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
    xmlhttp.open("post","../log/operatelog.do?method=synrealMoney&realMoney="+realMoney+"&realDate="+realDate+"&realPerson="+realPerson+"&shouldMoney="+shouldMoney,false);
    xmlhttp.onreadystatechange=function(){
		if(xmlhttp.readyState==4&&xmlhttp.status==200){
	       var str = xmlhttp.responseText;
	       if(str!='failure'){
	          document.all.realMoney.style.backgroundColor='#cccccc';
	          document.all.realMoney.readOnly='readOnly';
	          alert("添加操作员实收款成功！");
	       }else{
	          document.all.btnUpdate.disabled=false;
	          alert("添加操作员实收款失败，请重试！");
	       }
	    }
	}
	xmlhttp.send(null);
}

/**
 * 设置模式切换
 * @param model 设置模式 1-普通 2-模板
 * @param checkboxs 操作选项
 * @param button 操作选项按钮
 * @date 2011-06-21
 * @author danzhiping
 */
function changeDisplay(model,checkboxs,checkboxeds,button){
   if(model==2){//清空选中项
      for(var i=0;i<checkboxs.length;i+=1){
          if(checkboxs[i].checked){
              checkboxs[i].checked = false;
          }
      }
      for(var i=0;i<checkboxeds.length;i+=1){
          if(checkboxeds[i].checked){
              checkboxeds[i].checked = false;
          }
      }
      button.disabled=true;
   }else{
      button.disabled=false;
   }
}

/**
 * 根据选择项自动勾选对应项目
 * 现在应用场合：勾选相应部门选中对应门禁控制器
 * @param box 选中项
 * @param type 选中类型 1-部门 2-职位 3-卡类别 4-工号
 * @param model 设置模式 1-普通 2-模板(2的时候才执行此方法)
 * @param checkboxs 所有checkbox选项
 * @param checkboxeds 所有被勾选的选项
 * @param eigencode 企业特征码
 * @date 2011-06-21
 * @author danzhiping
 */
function setDoorChecked(box,type,model,checkboxs,checkboxeds,eigencode){
	if(model==2){
		if(checkedSize(checkboxs)>1){
	     	box.checked = false;
		    alert("模板模式下只能选择一条记录");
		    return;
  		}
  		 var doors;
  		//根据选择项查找被选中值
  		var xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
	    xmlhttp.open("post","../dooraccess/dooraccess.do?method=getRelationByBoxValue&boxValue="+box.value+"&type="+type+"&eigencode="+eigencode,false);
	    xmlhttp.onreadystatechange=function(){
			if(xmlhttp.readyState==4&&xmlhttp.status==200){
		       var str = xmlhttp.responseText;
		       if(str!='failure'){
		          doors = str.split("|");
		          for(var i=0;i<checkboxeds.length;i+=1){
		          	  for(var j=0;j<doors.length;j+=1){
		          	  	  if(checkboxeds[i].value==doors[j]){
		          	  	   	  if(box.checked){
         						  checkboxeds[i].checked = true;
							  }else{
							      checkboxeds[i].checked = false;
							  }
							  break;
		          	  	  }
		          	  }
		          }
		          
		       }else{
		          if(box.checked){
		              box.checked = false;
		          	  alert("当前所选项没有导入相应权限，请通过“门禁权限管理”导入相应权限");
		          }
		       }
		    }
		}
		xmlhttp.send(null);
	}
}

/**
 * 员工调班
 * @param attedate 考勤日期
 * @param userid1 工号1
 * @param userid2 工号2
 * @date 2011-06-26
 * @author danzhiping
 */
function attmovedetail(attedate,userid1,userid2,form){
    if(!confirm("确定调整用户的排班吗?")){
        return;
    }
	var xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
    xmlhttp.open("post","../attend/attend.do?method=attmovedetail&attedate="+attedate+"&userid1="+userid1+"&userid2="+userid2,false);
    xmlhttp.onreadystatechange=function(){
		if(xmlhttp.readyState==4&&xmlhttp.status==200){
	       var state = xmlhttp.responseText;
	       if(state==0){
	          alert("调班成功");
	          form.action="../common/count.do";
    		  form.submit();
		   }else if(state==-2){
			  alert("同一班段，无需调班");
		   }else if (state==-3) {
			  alert("查找班段不存在，无需调班");
		   }else if (state==-4) {
			  alert("当前处理时间超过上班开始刷卡时间，不能调班");
		   }else if (state==-5) {
			 alert("班段有重叠，不能调班");
		   }else {
			 alert("系统异常，请重试");
		   }
	    }
	}
	xmlhttp.send(null);
}

/**
 * 检查起始日期和结束日期是否合法
 * @param startdate yy-MM-dd
 * @param enddate yy-MM-dd
 * @param nowday yy-MM-dd
 * @param isBigNowday 是否需要比较当前日期(true-需要;false-不需要);
 * @date 2011-06-29
 * @author danzhiping
 */
function checkStartDateEndDate(startdate,enddate,nowday,isBigNowday){
    if(startdate.value.trim().length==0){
        alert("起始日期不能为空");
		startdate.focus();
		return false;
    }
	if(!isDate(startdate.value.trim())){
		alert("起始日期格式不正确");
		startdate.focus();
		return false;
	}
	if(isBigNowday && (startdate.value.trim()>nowday.value)){
		alert("起始日期不能大于当前日期");
		startdate.focus();
		return false;
	}
	if(enddate.value.trim().length==0){
        alert("结束日期不能为空");
		enddate.focus();
		return false;
    }
	if(!isDate(enddate.value.trim())){
		alert("结束日期格式不正确");
		enddate.focus();
		return false;
	}
	if(isBigNowday && (enddate.value.trim()>nowday.value)){
		alert("结束日期不能大于当前日期");
		enddate.focus();
		return false;
	}
	var time1 = startdate.value.trim();
    var time2 = enddate.value.trim();
    if(time1>time2){
        alert("起始日期不能大于结束日期");
        enddate.focus();
        return false;
    }
    return true;   
}

/**
 * 检查起始日期和结束日期是否合法且大于等于当前日期
 * @param startdate yy-MM-dd 起始日期
 * @param starttime hh:mm:ss 起始时间
 * @param enddate yy-MM-dd 结束日期
 * @param endtime hh:mm:ss 结束时间
 * @param nowday yy-MM-dd
 * @param isBigNowday 是否需要比较当前日期(true-需要;false-不需要);
 * @date 2011-07-28
 * @author danzhiping
 */
function checkStartDateEndDateIsBigNowday(startdate,starttime,enddate,endtime,nowday){
    if(startdate.value.trim().length==0){
       	alert("起始日期不能为空");
        startdate.focus();
        return false;
    }
    if(!isDate(startdate.value.trim())){
        alert("起始日期格式不正确");
        startdate.focus();
        return false;
    }
    if(startdate.value<nowday.value){
        alert("起始日期必须大于等于当前日期");
        startdate.focus();
        return false;
    }
	if(starttime.value.trim().length==0){
		alert("起始时间不能为空");
		starttime.focus();
		return false;
	}
	if(!isTime(starttime.value.trim())){
		alert("起始时间格式不正确");
		starttime.focus();
		return false;
	}
	if(enddate.value.trim().length==0){
   		alert("结束日期不能为空");
        enddate.focus();
        return false;
   	}
    if(!isDate(enddate.value.trim())){
        alert("结束日期格式不正确");
        enddate.focus();
        return false;
    }
    if(enddate.value<nowday.value){
        alert("结束日期必须大于等于当前日期");
        enddate.focus();
        return false;
    }
	if(endtime.value.trim().length==0){
		alert("结束时间不能为空");
		endtime.focus();
		return false;
	}
	if(!isTime(endtime.value.trim())){
		alert("结束时间格式错误");
		endtime.focus();
		return false;
	}
    var time1 = startdate.value+starttime.value;
    var time2 = enddate.value+endtime.value;
    if(time1>=time2){
        alert("起始时间不能大于等于结束时间");
        enddate.focus();
        return false;
    }
    return true;
}

/**
 * 定时跳转到对应页面
 * @param timenumObj 展示秒span对象
 * @param timenum 秒
 * @param url 跳转URL
 * @date 2012-07-26
 * @author danzhiping
 */
function gotoURL(timenumObj,timenum,url){
   setInterval(function(){
        if(timenum<0) timenum=0;
        timenumObj.innerHTML=timenum;
        timenum=timenum-1;
        if(timenum==0){
           window.location.href=url;
        }
   },1000);
}

/**
 * 根据URL、宽、高打开模式窗口
 * @param url url链接
 * @param width 宽(默认800)
 * @param heigh 高(默认500)
 * @date 2012-07-26
 * @author danzhiping
 */
function openShowModalDialog(url,width,height){
    window.showModalDialog(url+"&date="+new Date(), "", "dialogHeight:"+height+"px;dialogWidth:"+width+"px;dialogLeft:200px;dialogTop:100px;status:no;scroll:yes;help:no;resizable:yes;edge:sunken");
}

/**
 * 判断是否有可操作记录，且已经选择了记录
 * @param size 可操作数据记录条数
 * @param ids 可操作数据单选框或复选
 * @date 2012-07-31
 * @author danzhiping
 */
function hasAreaAndIsSelectArea(size,ids){
  	if(size<=0){
		alert("没有可操作数据");
		return false;  
  	}
  	var count = checkedSize(ids);
	if(count == 0){
 		alert("请选择记录");
		return false;
	}
  	return true;
}

/**
 * 上传文件到后返回存放目录
 * @param path 路径
 * @param suffix 后缀 如：txt jpg ..
 * @date 2012-08-02
 * @author danzhiping
 */
function uploadFile(path,suffix){
    var rootpath = "";
    var xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
    xmlhttp.open("post","../message/message.do?method=uploadFile&path="+path+"&suffix="+suffix,false);
    xmlhttp.onreadystatechange=function(){
		if(xmlhttp.readyState==4&&xmlhttp.status==200){
	       rootpath = xmlhttp.responseText;
	    }
	}
	xmlhttp.send(null);
	return rootpath;
}

/**
 * 根据发送队列改变程序号,队列名
 * @param ProcessNumObj 程序号
 * @param QueueObj 队列名输入框
 * @param xmlCodeVal 发送队列结点编号
 * @date 2012-09-11
 * @author danzhiping
 */
function sendChange(ProcessNumObj,QueueObj,xmlCodeVal,filecodeVal){
	var xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
    xmlhttp.open("post","../monitor/autoUpdate.do?method=getSendQueueByXmlCode&xmlCode="+xmlCodeVal+"&filecode="+filecodeVal,false);
    xmlhttp.onreadystatechange=function(){
		if(xmlhttp.readyState==4&&xmlhttp.status==200){
	       var str = xmlhttp.responseText;
	       if(str!="failure"){
	          var sendMess = str.split("|");
	          ProcessNumObj.value = sendMess[0];
	          QueueObj.value = sendMess[1];
	       }else{
	       	  alert("获取发送队列信息失败，请刷新页面或联系管理员");
	       }
	    }
	}
	xmlhttp.send(null);
}

/**
 * 根据发送队列改变程序号,队列名
 * @param ProcessNumObj 程序号
 * @param QueueObj 队列名输入框
 * @param xmlCodeVal 发送队列结点编号
 * @param ByteBuffFlagObj 传输类型
 * @date 2012-09-11
 * @author danzhiping
 */
function rcvChange(ProcessNumObj,QueueObj,ByteBuffFlagObj,xmlCodeVal,filecodeVal){
	var xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
    xmlhttp.open("post","../monitor/autoUpdate.do?method=getRcvQueueByXmlCode&xmlCode="+xmlCodeVal+"&filecode="+filecodeVal,false);
    xmlhttp.onreadystatechange=function(){
		if(xmlhttp.readyState==4&&xmlhttp.status==200){
	       var str = xmlhttp.responseText;
	       if(str!="failure"){
	          var sendMess = str.split("|");
	          ProcessNumObj.value = sendMess[0];
	          QueueObj.value = sendMess[1];
	          ByteBuffFlagObj.value = sendMess[2];
	       }else{
	       	  alert("获取接收队列信息失败，请刷新页面或联系管理员");
	       }
	    }
	}
	xmlhttp.send(null);
}

function chageMenuDisplay(menuValue){
	var left = parent.window.frames['left'];
	alert(left);
	var menu = left.document.getElementById(menuValue); 
	alert(menu);
}

function validateTradePwd(tradepass,flag){
	var result='0';
	if(flag==1){
		if(tradepass==''){
			result='1';
		}else{
			$.ajax({
				url : $("#rootPath").val()+"/business/bank.do?method=validatetradePwd",
				async : false,
				type : "POST",
				dataType : "text",
				data : {passwd :tradepass},
				success : function(data1) {
					result=data1;
				}
			});
			}
		}
	return result;
}


