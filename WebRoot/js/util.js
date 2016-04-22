/*
 * HTML����֤������һЩʵ�ú���
 * company: �����п�����������޹�˾
 * author: ¹��
 * version: 1.0.0
 */

//�Ƿ������ָ�ʽ���ַ���
function isDigit(str)
{
    return /^\d+$/.test(str);
}

//�Ƿ���ֻ��26����СдӢ���ַ����ַ���
function isAlpha(str)
{
    return /^[a-zA-Z]+$/.test(str);
}

//�Ƿ�ֻ���д�дӢ���ַ�
function isUpper(str)
{
    return /^[A-Z]+$/.test(str);
}

//�Ƿ�ֻ����СдӢ���ַ�
function isLower(str)
{
    return /^[a-z]+$/.test(str);
}

//�Ƿ�ֻ����26����СдӢ���ַ��������ַ����ַ���
function isAlnum(str)
{
    return /^[a-zA-Z\d]+$/.test(str);
}

//�Ƿ��ǿ�����ע���û�����������ַ���26����СдӢ���ַ������֡��»��ߡ����ߣ�
function isCode(str)
{
    return /^[a-zA-Z\d_\-]+$/.test(str);
}

//�Ƿ�������
function isInt(str)
{
    return /^[+-]?\d+$/.test(str);
}

//�Ƿ��Ǹ�����
function isFloat(str)
{
   return /^[+-]?(0\.\d+|0|[1-9]\d*(\.\d+)?)$/.test(str);
}

//�Ƿ����ʼ���ַ��ʽ
function isEmail(str)
{
    return /^([a-zA-Z0-9_\-\.])+@([a-zA-Z0-9_-])+\.([a-zA-Z0-9]){2,3}$/.test(str);
    ///^[\w-]+(\.[\w-]+)*@[\w-]+(\.[\w-]+)+$/;
}

//�Ƿ�����
function isLeapYear(str)
{
    if (isNaN(str)) return false;
    var year = parseInt(str, 10);
    return ((year % 4 == 0 && year % 100 != 0) || (year % 100 == 0 && year % 400 == 0))
}

//�Ƿ������ڸ�ʽ��YYYY-MM-DD��
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

//�Ƿ������ڸ�ʽ��YYYYMMDD��
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

//�Ƿ������ڸ�ʽ��YYYY-MM��
function isYearMonth(str)
{
    return /^\d{4}-(0[1-9]|1[0-2])$/.test(str);
}
//�Ƿ������ڸ�ʽ��YYYYMM��
function isYearMonth2(str)
{
    return /^20[0-2]\d((0[1-9])|(1[0-2]))$/.test(str);
}

//�Ƿ���ʱ���ʽ��hh:mm:ss��
function isTime(str)
{
    return /^([01]\d|2[0-3]):[0-5]\d:[0-5]\d$/.test(str);
}

//�Ƿ�������ʱ���ʽ��YYYY-MM-DD hh:mm:ss��
function isDateTime(str)
{
    return (str.length == 19) ? (isDate(str.substring(0, 10)) && isTime(str.substring(11, 19))) : false;
}

//���ĳ���ĳ�����ж����� ����˵ ĳ��ĳ�µ����һ���Ƕ��١�
function getDays(year, month)
{
    switch (parseInt(month, 10))
    {
        case 1: case 3: case 5: case 7: case 8: case 10: case 12: return 31;
        case 4: case 6: case 9: case 11: return 30;
        case 2: return isLeapYear(year) ? 29 : 28;
    }
}

//���������������������sDate1 - sDate2����sDate1��sDate2��"yyyy-mm-dd"��ʽ
function dateDiff(sDate1, sDate2)
{
    var aDate, oDate1, oDate2, iDays
    aDate = sDate1.split("-")
    oDate1 = new Date(aDate[1] + '-' + aDate[2] + '-' + aDate[0])  //ת��Ϊ12-18-2002��ʽ
    aDate = sDate2.split("-")
    oDate2 = new Date(aDate[1] + '-' + aDate[2] + '-' + aDate[0])
    iDays = parseInt((oDate1 - oDate2) / 1000 / 60 / 60 /24)  //�����ĺ�����ת��Ϊ����
    return iDays
}

//����������������������sDate1 - sDate2����sDate1��sDate2��"yyyy-mm-dd"��ʽ
function monthDiff(sDate1, sDate2)
{
    var aDate, oDate1, oDate2, iDays
    aDate = sDate1.split("-")
    oDate1 = new Date(aDate[1] + '-' + aDate[2] + '-' + aDate[0])  //ת��Ϊ12-18-2002��ʽ
    aDate = sDate2.split("-")
    oDate2 = new Date(aDate[1] + '-' + aDate[2] + '-' + aDate[0])
    imonths = parseInt((oDate1 - oDate2) / 1000 / 60 / 60 /24/30)  //�����ĺ�����ת��Ϊ����
    return imonths
}

//�Ƿ���IP��ַ��ʽ���õ�"."�ָ�����������У���һ�����ַ�Χ1-223�������������ַ�Χ0-255��
function isIP(str)
{
    return /^(0?0?[1-9]|0?[1-9]\d|1\d\d|2[01]\d|22[0-3])(\.([01]?\d?\d|2[0-4]\d|25[0-5])){3}$/.test(str);
}

//����IP��ַ�е�ÿ�������������3λ����ǰ�油�㡣���磺"192.168.1.22"�����"192.168.001.022"
function getFullIP(ip)
{
    var nums = ip.split(".");
    var fullIP = "";
    fullIP += repeatString("0", 3 - nums[0].length) + nums[0];
    for (var i = 1; i < nums.length; i++)
        fullIP += "." + repeatString("0", 3 - nums[i].length) + nums[i];
    return fullIP;
}

//��֤�Ƿ���MAC��ַ����Ӣ��ð��":"�ָ��6����λ�ַ��������ַ�ֻ����Ӣ���ַ������֣����磺00:03:0D:61:62:7C��
function isMAC(mac)
{
    return /^[\dA-Za-z]{2}(\:[\dA-Za-z]{2}){5}$/.test(mac);
}

/*
�Ƿ���Ӣ�Ķ���","�ָ������ָ�ʽ�ĺ��봮��
�����жϹ���
1��ÿ��������������ָ�ʽ��
2������֮�������Ӣ�Ķ��š��ո񡢻��з�������Ϸָ���
*/
function isPhoneNumbers(str)
{
    return /^[\s,]*\d+([\s,]+\d+)*[\s,]*$/.test(str);
}

/*
�Ƿ���Ӣ�Ķ���";"�ָ���11λ���ֵ����ظ��ĺ��봮��
����ֵ��1 - �ֻ�����ֻ��Ϊ11λ���ֻ�11λ������Ӣ�ķֺŷָ�
      2 - �ֻ��������ظ�
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
���repeat���ַ���str�����ӽ��
@param str Ҫ�ظ����ַ���
@param repeat �ظ��Ĵ���
*/
function repeatString(str, repeat)
{
    var repeatStr = "";
    for (var i = 0; i < repeat; i++)
        repeatStr += str;
    return repeatStr;
}

//�Ƿ�����Ƿ��ַ�������ļ�������\��/��|��"��?��*��:��<��>��
function containInvalidChar(str)
{
    return /[\\\/\|"\?\*:<>]/.test(str);
}


//����ַ���������ֽ���
function bytes(str)
{
    return str.replace(/[^\x00-\xff]/g,"**").length;
}

//ȥ���ַ������ߵĿո�
String.prototype.trim = function()
{
    return this.replace(/(^\s*)|(\s*$)/g, "");
}

//�������Ƿ������ָ��Ԫ��
Array.prototype.contains = function(obj)
{
    for (var i = 0; i < this.length; i++)
        if (this[i] === obj) return true;

    return false;
}

/*
����ѡ���ť��ѡ��״̬
@param checkBox ѡ���ť������
@param checked trueѡ�����У�falseȡ�����С�
       �˲�����ʡ�ԣ�ʡ��ʱ��ÿһ�ε��ø÷�������ѭ����ѡ�С�ȡ����ѡ��...
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

//���ѡ���ť�鱻ѡ�еĸ���
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
�����ѡ����ѡ��ť���ѡ��״̬
@param field����ѡ���ѡ��ť�������
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
���ָ�������ֶ�ֵ��
������ֶ�Ϊ �ı��� �������
������ֶ�Ϊ ����ѡ��� ����ѡ���һ��
������ֶ�Ϊ��ѡ���ѡ��ť����ȫ��ѡ��״̬
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
��Դ�б����Ŀ���б����Ԫ��
@param fromSelect Դ�б�����
@param toSelect Ŀ���б�����
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
�Ƴ��б���е�Ԫ��
@param select �б�����
*/
function removeSelect(select)
{
    for (var i = select.options.length - 1; i >= 0; i--)
        if (select.options[i].selected)
            select.remove(i);
}

/*
�ƶ�Դ�б����ѡ�е���Ŀ��Ŀ���б���У�������ָ�����������б���е�ֵ��������
@param fromSelect Դ�б�����
@param toSelect Ŀ���б�����
@param compare �Ƚϴ�С��������������Ŀ�ġ�
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
�ƶ�Դ�б�������е���Ŀ��Ŀ���б���У�������ָ�����������б���е�ֵ��������
@param fromSelect Դ�б�����
@param toSelect Ŀ���б�����
@param compare �Ƚϴ�С��������������Ŀ�ġ�
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

/* �������Ƚ����������Ĵ�С���� num1 > num2 �򷵻ش���0��ֵ���� num1 == num2 �򷵻�0�����򷵻�С��0��ֵ */
function compareInt(num1, num2)
{
    return parseInt(num1) - parseInt(num2);
}

/* �Ը������Ƚ����������Ĵ�С���� num1 > num2 �򷵻ش���0��ֵ���� num1 == num2 �򷵻�0�����򷵻�С��0��ֵ */
function compareFloat(num1, num2)
{
    return parseFloat(num1) - parseFloat(num2);
}

/* ���ַ����Ƚ����������Ĵ�С���� num1 > num2 �򷵻�1���� num1 == num2 �򷵻�0�����򷵻�-1��ֵ */
function compareString(str1, str2)
{
    var s1 = new String(str1);
    var s2 = new String(str2);

    if (s1 > s2) return 1;
    else if (s1 == s2) return 0;
    else return -1;
}

/* ����ϵͳ����Ĳ�����ֱ�ӱȽ����������Ĵ�С���� num1 > num2 �򷵻�1���� num1 == num2 �򷵻�0�����򷵻�-1��ֵ */
function compare(a1, a2)
{
    if (a1 > a2) return 1;
    else if (a1 == a2) return 0;
    else return -1;
}

/*
�������ݣ�֧�ֶ����顢����ͻ������͵����ݿ�����
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
ѡ��Ŀ�������б���ֵ�һ��Ԫ��
@param select Ŀ�������б��
@param value Ҫѡ�е�ֵ
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
ѡ�������б���������
@param select �����б��
*/
function selectAll(select)
{
    for (var i = 0; i < select.options.length; i++)
        select.options[i].selected = true;
}

/*
���ĳ��ĳ�µ������ܲ�д��Ŀ�������б��С�
���򣺴ӵ��µĵ�һ������ÿ7��Ϊһ�ܣ����һ����ܲ�Ϊ7�죬Ҳ����һ�ܡ�
Ŀ�������б��е�Ԫ��Ϊ����������
<option value=""></option>
<option value="1">��1��</option>
<option value="2">��2��</option>
......
@param weekSelect Ŀ�������б��
@param year ��
@param month ��
*/
function setWeekSelect(weekSelect, year, month)
{
    var options = weekSelect.options;
    options.length = (options.length > 0 && options[0].value == "") ? 1 : 0;

    if (isNaN(parseInt(year, 10)) || isNaN(parseInt(month, 10)))
        return;

    var weeks = (!isLeapYear(year) && parseInt(month) == 2) ? 4 : 5;
    for (var i = 1; i <= weeks; i++)
        options.add(new Option("��" + i + "��", i));
}

//�÷�������Ѱ�Ҿ���id��parent�����Ե��ı��򣬲���ÿ����ͬ���ֵ������ı������Ͳ������ۼӡ�
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
���ı�������������ۼӵ��������ĸ߲���ı����С�
Ҫ����ı��������name��id��parent���ԡ�
@param text Ҫ�ۼӵ��ı���
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
���� url ��һ���涨����͸߶ȵ��µ������ڣ��´��ڽ�����Ļ�м���΢������ʾ��url�еĲ������Դӱ��л�á�
@param url ���´��ڵ�·��
@param width ���ڿ��
@param height ���ڸ߶�
@param oForm Ҫ��ȡ�����ı�����ʡ�ԡ�
@return ���ش򿪴��ڵĶ���
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

//��url�е�����Ӣ���ַ����б���
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
д��HTML�����Ӣ���ַ��ո�
@param num д��Ŀո�������ʡ�ԣ�ʡ��ʱ��д��Ŀո񳤶����ı����Ĭ�ϳ���(18)��ȡ�
*/
function writeSpaces(num)
{
    var space = "&nbsp;";
    var repeats = num == undefined ? 18 : num;
    document.write(repeatString(space, repeats));
}

/*
���ݴ�����ַ����������д��HTML�����Ӣ�Ŀո���ʹ�ַ����Ϳո�ĳ��ȴ��µ����ı����Ĭ�ϳ��ȡ�
@param str �ַ�������ʡ�ԣ�ʡ��ʱд���ı���Ĭ�ϳ��ȵĿո�
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
���ݴ�����ַ����ж��䳤���Ƿ�Ϊ�㣬���������ʾ�ַ������ݡ�
@param str ��ʾ����Ϣ��
*/
function showMessage(str)
{
   if(str.length!=0){
   		alert(str);
   }
}

/**
��֤�������ϵ���Ƿ�Ϸ�
*/
function checkContact(text){
	var contact = text.value;
	if(contact.length>10){
		alert("��ϵ�˲��ܴ���10��Ӣ���ַ���5�������ַ�");
		text.focus();
		return false;
	}
	return true;
}
/**
��֤�������ϵ��ַ�Ƿ�Ϸ�
*/
function checkAddress(text){
	var addr = text.value;
	if(addr.length>50){
		alert("��ϵ��ַ���ܴ���50��Ӣ���ַ���25�������ַ�");
		text.focus();
		return false;
	}
	return true;
}

/*
��֤�������ϵ�绰��ʽ�������Ƿ�Ϸ�
*/
function checkPhoneNumber(text){
	var phone = text.value;
	if(phone.length>0){
		if(!isDigit(phone)){
			alert("��ϵ�绰����Ϊ���֣�����������");
			text.focus();
			return false;
		}
		if(phone.length>12){
			alert("��ϵ�绰���ܴ���12�������ַ�������������");
			text.focus();
			return false;
		}
	}
	return true;
}

/*
��֤��������п����Ƿ�Ϸ�
*/
function checkbankcard(text){
	var bankcard = text.value;
	if(bankcard.length>0){
		if(!isDigit(bankcard)){
			alert("�����˺ű���Ϊ���֣�����������");
			text.focus();
			return false;
		}
	}
	if(bankcard.length>25){
	  	alert("�����˺Ų��ܴ���25�������ַ�������������");
	  	text.focus();
		return false;
	}
	return true;
}

/*
��֤����Ŀ������еĳ����Ƿ񳬹�25�������ַ�
*/	
function checkbank(text){
	var bank = text.value;
	if(bank.length>25){
	  	alert("�������в��ܴ���50��Ӣ���ַ���25�������ַ�������������");
	  	text.focus();
		return false;
	}
	return true;
}

/*
��֤�����˰���Ƿ�Ϸ�
*/
function checktaxnum(text){
	var taxnum = text.value;
	if(taxnum.length>0){
		if(!isDigit(taxnum)){
			alert("˰�ű���Ϊ���֣�����������");
			text.focus();
			return false;
		}
	}
	if(taxnum.length>25){
	  	alert("˰�Ų��ܴ���25�������ַ�������������");
	  	text.focus();
		return false;
	}
	return true;
}

/*
������֤��ѡ�������Ƿ���ڵ�ǰ����
str1 ��� 
str2 �·�
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
			alert("ѡ������²��ܴ��ڵ�ǰ���£�"+current);
			str2.focus();
		    return false;
		}else{
			return true;
		}
	}
}
/*
 ������֤��ʼ���²��ܴ�����ֹ����
*/
function comparetowmonth(str1,str2,str3,str4)
{
	with (document.forms[0]) {
		var ym1 = str1.value+str2.value;
		var ym2 = str3.value+str4.value;
		if(ym1>ym2){
			alert("��ʼ���²��ܴ�����ֹ���£�");
			str4.focus();
		     return false;
		}else{
			return true;
		}
	}
}

/*
 ������֤��ʼ���²��ܴ�����ֹ����
*/
function comparetowdate(str1,str2)
{
	var date1 = str1.value;
	var date2 = str2.value;
	with (document.forms[0]) {
		if(date1>date2){
			alert("��ʼ���ڲ��ܴ�����ֹ���ڣ�");
			str2.focus();
		    return false;
		}else{
			return true;
		}
	}
}




/**
 * ������������λ���ַ�����֤���޶�ֻ��Ϊ���ֵ������������λ����ֻ���������λ���֣�
 * �㷨������FALSE�������û��˴ΰ��µļ�����Ч
 * ע���÷�����Ҫ������������onkeydown�¼���,�磺onkeydown="return getInput(tmp,e,len);"
 * @param bandno input����
 * @param e �����������¼�����
 * @param len �޶�����Ƕ���λ
 * @return boolean
 * @author leichengjian
 */
function getInput(bandno,e,len){
	var bandno = bandno.value.trim();
	//����ɾ������delete�������������ҹ���
	if(e.keyCode==8||e.keyCode==46||(e.keyCode>=37&&e.keyCode<=40)){
		return true;
	}
	if(bandno.length==0&&e.ctrlKey&&e.keyCode==86){
		//alert("������Ϊ0������ճ��");
		return true;
	}
	//��������Ѿ�Ϊlenλ����Ϊ��Ч
	if(bandno.length==len){
		return false;
	}
	//������µļ����ڴ����0-9��Ҳ����С����0-9������Ϊ��Ч
	if((e.keyCode<48||e.keyCode>57)&&(e.keyCode<96||e.keyCode>105)){
		return false;
	}
}

/**
 * ���Ʊ���������λ�������λ���ﵽlenʱ�����û�������Ч
 * �㷨������FALSE�������û��˴ΰ��µļ�����Ч
 * ע���÷�����Ҫ������������onkeydown�¼���,�磺onkeydown="return limitInputCount(tmp,e,len);"
 * @param tmp input����
 * @param e �����������¼�����
 * @param len �޶�������λ
 * @return boolean
 * @author leichengjian
 */
function limitInputCount(tmp,e,len){
	var tmp_var = tmp.value.trim();
	//����ɾ������delete�������������ҹ���
	if(e.keyCode==8||e.keyCode==46||(e.keyCode>=37&&e.keyCode<=40)){
		return true;
	}
	//����Ѿ��ﵽ���λ��
	if(tmp_var.length==len){
		return false;
	}
}


/**
 * ���ݿ���ţ��ҳ��ÿ�����Ӧ��UID�������õ�input������
 *
 * @param com  Active�ؼ���ͨ�Ŵ���
 * @param obj  CardManager�������ڲ����豸��Active�ؼ�����
 * @param cardsn �����
 * @param new_cardid Ҫ���õ�input����
 * @return boolean	 �����Ƿ�ɹ�
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
	    	alert("��ȡ���ݿ�UIDʧ��!uid�����ڣ���ȷ��!");
	    	return false;
	    	}
		}
	}
	xmlhttp.send(null);
}

/**
 * ��ҳ������Ӧ��deptcode_* ���óɱ�ѡ��״̬
 * ���ݼƴ����ѹ����ţ��ƷѺ��޴����֣��ͼƴ����ѹ������ͣ�ȡֵ��1-�Ʒ�,2-�޴Σ����õ��Ѱ󶨹�ϵ�Ĳ��ű��------�õ��Ĳ��ű������(��ʽ�������ű��|���ű�š� )
 * �����ű�����ݽ��� ��|�� ���룬�����ݹ�����ҳ��ı��е� "deptcode_"+���ű��  ����Ϊ��ѡ��״̬
 *
 * @param frm document ҳ���document����
 * @param code �ƴ����ѹ��򣨼ƷѺ��޴����֣�
 * @param type �ƴ����ѹ������ͣ�ȡֵ��1-�Ʒ�,2-�޴Σ�
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
	    	alert("��ȡ�Ѱ󶨹�ϵ�Ĳ��ű��ʧ�ܣ��������ݿ�Ͽ����������Աȷ��!");
	    	return false;
	    	}
		}
	}
	xmlhttp.send(null);
}

/**
 * ����ѯ���ļƴ�����(�ƷѺ��޴�����)������ϸ��ʾ��ҳ��span��
 *
 * @param rule_display ҳ���span����
 * @param code �ƴ����ѹ��򣨼ƷѺ��޴����֣��ı��
 * @param type �ƴ����ѹ������ͣ�ȡֵ��1-�Ʒ�,2-�޴Σ�
 * @return boolean	 
 * @author leichengjian
 */
function getCountRuleDetailByCode(rule_display,code,type){
	if(code==""){
		alert("��ȡ�Ʒѹ���ʧ�ܣ������޹�������ݿ����ӶϿ����������Աȷ��!");
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
		  rule_display.innerHTML = "��������<font color='red'>"+str[0]+"</font>�����������ͶΣ�<font color='red'>"+str[1]+"</font>���Ͷο�ʼʱ�䣺<font color='red'>"
	  							   +str[2]+"</font>���Ͷν���ʱ�䣺<font color='red'>"+str[3]+"</font>���Ʒ�"+str[5]+"��<font color='red'>"+str[4]+"</font>";
	      return true;
	    } else {
	    	alert("��ȡ����ʧ�ܣ������޹�������ݿ����ӶϿ����������Աȷ��!");
	    	return false;
	    	}
		}
	}
	xmlhttp.send(null);
}



/*
 *�Ƿ��ַ���
 */
function checkInvalidChar(str){
	return /[^a-zA-Z0-9\_\u4e00-\u9fa5]/.test(str);
}

/*
 * �ɷ�ʱ��ͨ��ԭʼ�����뿨���͵õ��ÿ��������Ϣ�ͽɷѿ�Ŀ��Ϣ
 * @param cardid ԭʼ���ţ�UID��
 * @param type ������
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
	    	var str="��Ҫ���ɵķ��ã�";
	    	var count=0;
	    	for(var i=0;i<size;i++){
	    		str += '<br/><input type="checkbox" checked="true" name="deptsubid" onClick="change(this.checked,'
	    			+ Math.round(tt.sublist[i].money/100)+')" value="'+tt.sublist[i].id+'">'+tt.sublist[i].subname+'��'
	    			+ Math.round(tt.sublist[i].money/100)+'Ԫ';
	    		if(i!=size-1){
	    			str += '��';
	    		}
	    		count +=tt.sublist[i].money/100;
	    		
	    	}
//alert("div_subject="+str);
	    	div_subject.innerHTML=str;
	    	span_count.style.display="block";
	    	span_count.innerHTML = "�ϼƷ��ã�"+Math.round(count*100)/100+"Ԫ��";
	    	back.value=count;
	    	return 1;
	    } else {
	    	alert("δ�ҵ��ÿ��������Ϣ��ɷѿ�Ŀ��Ϣ��������ϵ����Ա��");
	    	return 0;
	    	}
		}
	}
	xmlhttp.send(null);
	return flag;
}

//�Ա���������countλ��"0"
function add_0_var(tmp,count){
	var var_tmp = tmp; 
	for(var i=0;i<count - tmp.length;i++){
		var_tmp = "0" + var_tmp;
	}
	return var_tmp;
}


/**
 * ����flag��0-���أ�1-��ʾ�������ػ���ʾsp_model��sp_choose����<span>��ǩ
 * @param flag��־��0-���أ�1-��ʾ��
 * @param sp_model  <span>��ǩ
 * @param sp_choose <span>��ǩ
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
*js����cookie
*/
function setCookie(name,value)//����������һ����cookie�����ӣ�һ����ֵ
{
    var Days = 30; //�� cookie �������� 30 ��
    var exp  = new Date();    //new Date("December 31, 9998");
    exp.setTime(exp.getTime() + Days*24*60*60*1000);
    document.cookie = name + "="+ escape (value) + ";expires=" + exp.toGMTString();
}
function getCookie(name)//ȡcookies����        
{
    var arr = document.cookie.match(new RegExp("(^| )"+name+"=([^;]*)(;|$)"));
     if(arr != null) return unescape(arr[2]); return null;

}

/**
 * ����eigencode(��ҵ������)�����ҵ����������Ϣ�Լ���ҵ������Կ
 * @param eigencode ��ҵ������
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
 * ������ҵ������������ҵ��Կ(�ѻ�����ʹ��)
 * ����0--��Կδ���� -1--��Ƭ���ʹ��� 1--�ɹ�
 * @param eigencode ��ҵ������
 * @param cardtype ��Ƭ���� 02-13.56M 03-2.4G
 * @param companyKeyObj ��ҵ��Կ������
 * @date 2012-05-03
 * @author danzhiping
 */
function setCompanyKey(eigencode,cardtype,companyKeyObj){
   var cardConfig = getCardConfig(eigencode);
   if(cardConfig[0]=='noupt'){
       alert("��ѡ��ҵ��Ҫ������Կ��������ʹ��,�뵽����ҵ����������ܰ��ʾ������");
       return 0;
   }else{
       if(cardtype=='02'){
         set_obj_cardkey(eigencode,companyKeyObj);
         return 1;
       }else if(cardtype=='03'){
         set_obj_rfcardkey(eigencode,companyKeyObj);
         return 1;
       }else{
         alert("��Ƭ���ʹ���"+cardtype);
         return -1;
       }
   }
}

/**
 * Ajaxʵ��ʹ���ѻ����߼�¼������־
 * 0 -- ʧ��  1 -- �ɹ�
 * @param message ������Ϣ
 * @param explain ����ԭ��
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
 * ���ݿ���Ż�ȡ�ͻ���Ӧ���š�ְλ��Ϣ
 * @param cardsn �����
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
 * ��ֵ-�޸�ԭʼ������ˮ(����)
 * @param id ��־���
 * @param tradetime ����ʱ��
 * @param tradetype ��������
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
 * ���ݲ��ű�����ò������Ƶ�ҳ��������
 * @param deptObj ��������������
 * @param deptcode ���ű��
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
	          alert("���ݲ��ű�Ż�ȡ��������ʧ�ܣ������ԣ�");
	       }
	    }
	}
	xmlhttp.send(null);
}

/**
 * ���������ת��ԭʼ�Ҽ�¼(д�뿨Ƭ�ɹ��޸�״̬)
 * @param userid ����
 * @param cardsn �����
 * @param acctleft ������
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
	          alert("���ݲ��ű�Ż�ȡ��������ʧ�ܣ������ԣ�");
	       }
	    }
	}
	xmlhttp.send(null);
}

/**
 * �����ۺ�ͳ�Ʊ������Ա�������Աʵ�տ�
 * @param userid ����
 * @param cardsn �����
 * @param acctleft ������
 * @date 2011-05-22
 * @author danzhiping
 */
function synrealMoney(){
    document.all.btnUpdate.disabled=true;
    var realMoney = document.all.realMoney.value;
    if(realMoney.trim().length==0){
		alert("������ʵ�տ");
		document.all.btnUpdate.disabled=false;
		document.all.realMoney.focus();
		return;
	}
	if(!/^((\d+)|(\d+\.\d{1,2}))$/.test(realMoney)){
    	alert("ʵ�տ����ʽ���ԣ�");
    	document.all.btnUpdate.disabled=false;
		document.all.realMoney.focus();
		return;
  	}
	if(/^[0\.]*$/.test(realMoney)){
    	alert("ʵ�տ����Ϊ�㣡");
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
	          alert("��Ӳ���Աʵ�տ�ɹ���");
	       }else{
	          document.all.btnUpdate.disabled=false;
	          alert("��Ӳ���Աʵ�տ�ʧ�ܣ������ԣ�");
	       }
	    }
	}
	xmlhttp.send(null);
}

/**
 * ����ģʽ�л�
 * @param model ����ģʽ 1-��ͨ 2-ģ��
 * @param checkboxs ����ѡ��
 * @param button ����ѡ�ť
 * @date 2011-06-21
 * @author danzhiping
 */
function changeDisplay(model,checkboxs,checkboxeds,button){
   if(model==2){//���ѡ����
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
 * ����ѡ�����Զ���ѡ��Ӧ��Ŀ
 * ����Ӧ�ó��ϣ���ѡ��Ӧ����ѡ�ж�Ӧ�Ž�������
 * @param box ѡ����
 * @param type ѡ������ 1-���� 2-ְλ 3-����� 4-����
 * @param model ����ģʽ 1-��ͨ 2-ģ��(2��ʱ���ִ�д˷���)
 * @param checkboxs ����checkboxѡ��
 * @param checkboxeds ���б���ѡ��ѡ��
 * @param eigencode ��ҵ������
 * @date 2011-06-21
 * @author danzhiping
 */
function setDoorChecked(box,type,model,checkboxs,checkboxeds,eigencode){
	if(model==2){
		if(checkedSize(checkboxs)>1){
	     	box.checked = false;
		    alert("ģ��ģʽ��ֻ��ѡ��һ����¼");
		    return;
  		}
  		 var doors;
  		//����ѡ������ұ�ѡ��ֵ
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
		          	  alert("��ǰ��ѡ��û�е�����ӦȨ�ޣ���ͨ�����Ž�Ȩ�޹���������ӦȨ��");
		          }
		       }
		    }
		}
		xmlhttp.send(null);
	}
}

/**
 * Ա������
 * @param attedate ��������
 * @param userid1 ����1
 * @param userid2 ����2
 * @date 2011-06-26
 * @author danzhiping
 */
function attmovedetail(attedate,userid1,userid2,form){
    if(!confirm("ȷ�������û����Ű���?")){
        return;
    }
	var xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
    xmlhttp.open("post","../attend/attend.do?method=attmovedetail&attedate="+attedate+"&userid1="+userid1+"&userid2="+userid2,false);
    xmlhttp.onreadystatechange=function(){
		if(xmlhttp.readyState==4&&xmlhttp.status==200){
	       var state = xmlhttp.responseText;
	       if(state==0){
	          alert("����ɹ�");
	          form.action="../common/count.do";
    		  form.submit();
		   }else if(state==-2){
			  alert("ͬһ��Σ��������");
		   }else if (state==-3) {
			  alert("���Ұ�β����ڣ��������");
		   }else if (state==-4) {
			  alert("��ǰ����ʱ�䳬���ϰ࿪ʼˢ��ʱ�䣬���ܵ���");
		   }else if (state==-5) {
			 alert("������ص������ܵ���");
		   }else {
			 alert("ϵͳ�쳣��������");
		   }
	    }
	}
	xmlhttp.send(null);
}

/**
 * �����ʼ���ںͽ��������Ƿ�Ϸ�
 * @param startdate yy-MM-dd
 * @param enddate yy-MM-dd
 * @param nowday yy-MM-dd
 * @param isBigNowday �Ƿ���Ҫ�Ƚϵ�ǰ����(true-��Ҫ;false-����Ҫ);
 * @date 2011-06-29
 * @author danzhiping
 */
function checkStartDateEndDate(startdate,enddate,nowday,isBigNowday){
    if(startdate.value.trim().length==0){
        alert("��ʼ���ڲ���Ϊ��");
		startdate.focus();
		return false;
    }
	if(!isDate(startdate.value.trim())){
		alert("��ʼ���ڸ�ʽ����ȷ");
		startdate.focus();
		return false;
	}
	if(isBigNowday && (startdate.value.trim()>nowday.value)){
		alert("��ʼ���ڲ��ܴ��ڵ�ǰ����");
		startdate.focus();
		return false;
	}
	if(enddate.value.trim().length==0){
        alert("�������ڲ���Ϊ��");
		enddate.focus();
		return false;
    }
	if(!isDate(enddate.value.trim())){
		alert("�������ڸ�ʽ����ȷ");
		enddate.focus();
		return false;
	}
	if(isBigNowday && (enddate.value.trim()>nowday.value)){
		alert("�������ڲ��ܴ��ڵ�ǰ����");
		enddate.focus();
		return false;
	}
	var time1 = startdate.value.trim();
    var time2 = enddate.value.trim();
    if(time1>time2){
        alert("��ʼ���ڲ��ܴ��ڽ�������");
        enddate.focus();
        return false;
    }
    return true;   
}

/**
 * �����ʼ���ںͽ��������Ƿ�Ϸ��Ҵ��ڵ��ڵ�ǰ����
 * @param startdate yy-MM-dd ��ʼ����
 * @param starttime hh:mm:ss ��ʼʱ��
 * @param enddate yy-MM-dd ��������
 * @param endtime hh:mm:ss ����ʱ��
 * @param nowday yy-MM-dd
 * @param isBigNowday �Ƿ���Ҫ�Ƚϵ�ǰ����(true-��Ҫ;false-����Ҫ);
 * @date 2011-07-28
 * @author danzhiping
 */
function checkStartDateEndDateIsBigNowday(startdate,starttime,enddate,endtime,nowday){
    if(startdate.value.trim().length==0){
       	alert("��ʼ���ڲ���Ϊ��");
        startdate.focus();
        return false;
    }
    if(!isDate(startdate.value.trim())){
        alert("��ʼ���ڸ�ʽ����ȷ");
        startdate.focus();
        return false;
    }
    if(startdate.value<nowday.value){
        alert("��ʼ���ڱ�����ڵ��ڵ�ǰ����");
        startdate.focus();
        return false;
    }
	if(starttime.value.trim().length==0){
		alert("��ʼʱ�䲻��Ϊ��");
		starttime.focus();
		return false;
	}
	if(!isTime(starttime.value.trim())){
		alert("��ʼʱ���ʽ����ȷ");
		starttime.focus();
		return false;
	}
	if(enddate.value.trim().length==0){
   		alert("�������ڲ���Ϊ��");
        enddate.focus();
        return false;
   	}
    if(!isDate(enddate.value.trim())){
        alert("�������ڸ�ʽ����ȷ");
        enddate.focus();
        return false;
    }
    if(enddate.value<nowday.value){
        alert("�������ڱ�����ڵ��ڵ�ǰ����");
        enddate.focus();
        return false;
    }
	if(endtime.value.trim().length==0){
		alert("����ʱ�䲻��Ϊ��");
		endtime.focus();
		return false;
	}
	if(!isTime(endtime.value.trim())){
		alert("����ʱ���ʽ����");
		endtime.focus();
		return false;
	}
    var time1 = startdate.value+starttime.value;
    var time2 = enddate.value+endtime.value;
    if(time1>=time2){
        alert("��ʼʱ�䲻�ܴ��ڵ��ڽ���ʱ��");
        enddate.focus();
        return false;
    }
    return true;
}

/**
 * ��ʱ��ת����Ӧҳ��
 * @param timenumObj չʾ��span����
 * @param timenum ��
 * @param url ��תURL
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
 * ����URL�����ߴ�ģʽ����
 * @param url url����
 * @param width ��(Ĭ��800)
 * @param heigh ��(Ĭ��500)
 * @date 2012-07-26
 * @author danzhiping
 */
function openShowModalDialog(url,width,height){
    window.showModalDialog(url+"&date="+new Date(), "", "dialogHeight:"+height+"px;dialogWidth:"+width+"px;dialogLeft:200px;dialogTop:100px;status:no;scroll:yes;help:no;resizable:yes;edge:sunken");
}

/**
 * �ж��Ƿ��пɲ�����¼�����Ѿ�ѡ���˼�¼
 * @param size �ɲ������ݼ�¼����
 * @param ids �ɲ������ݵ�ѡ���ѡ
 * @date 2012-07-31
 * @author danzhiping
 */
function hasAreaAndIsSelectArea(size,ids){
  	if(size<=0){
		alert("û�пɲ�������");
		return false;  
  	}
  	var count = checkedSize(ids);
	if(count == 0){
 		alert("��ѡ���¼");
		return false;
	}
  	return true;
}

/**
 * �ϴ��ļ����󷵻ش��Ŀ¼
 * @param path ·��
 * @param suffix ��׺ �磺txt jpg ..
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
 * ���ݷ��Ͷ��иı�����,������
 * @param ProcessNumObj �����
 * @param QueueObj �����������
 * @param xmlCodeVal ���Ͷ��н����
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
	       	  alert("��ȡ���Ͷ�����Ϣʧ�ܣ���ˢ��ҳ�����ϵ����Ա");
	       }
	    }
	}
	xmlhttp.send(null);
}

/**
 * ���ݷ��Ͷ��иı�����,������
 * @param ProcessNumObj �����
 * @param QueueObj �����������
 * @param xmlCodeVal ���Ͷ��н����
 * @param ByteBuffFlagObj ��������
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
	       	  alert("��ȡ���ն�����Ϣʧ�ܣ���ˢ��ҳ�����ϵ����Ա");
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


