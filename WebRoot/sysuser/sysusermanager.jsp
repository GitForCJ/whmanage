<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@ page contentType="text/html; charset=gb2312" language="java"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>���ͨ</title>
<link href="../css/main_style.css" rel="stylesheet" type="text/css" />
<SCRIPT src="../js/more_tiaojian.js" type=text/javascript></SCRIPT>
<script language='javascript' src='../js/jquery.js'></script>
<script language='javascript' src='../js/select.js'></script>
<script language="javascript" type="text/javascript" src="../My97DatePicker/WdatePicker.js"></script>
<link href="../css/selectCss.css" rel="stylesheet" type="text/css" /> 
<script src="../js/jquery-1.8.2.min.js" type="text/javascript"></script> 
<script src="../js/selectCss.js" type="text/javascript"></script> 
<SCRIPT type=text/javascript>
jQuery(function(){
	$("#changeCity").click(function(a){$("#allCity").slideDown(0);a.stopPropagation();$(this).blur()});$("#allCity").click(function(a){a.stopPropagation()});$(document).click(function(a){a.button!=2&&$("#allCity").slideUp(0)});$("#foldin").click(function(){$("#allCity").slideUp(0)})
});
</SCRIPT>
</head>
<body onLoad="changeTableBg();">
<div class="header">
	<div class="topCtiy clear">
		<ul>
			<li>
				<div id="popdiv"><span id="sellist"></span><div style="clear:both"></div></div>
				<div class="sub-input"><a class="sia-2 selhover" id="sl-priceFrom" href="javascript:void(0);">dsadsa</a></div><span class="sfg">-</span>
				<input name="priceFrom" id="priceFrom" defaultsel="dsadsa[]|5[50000]|10[100000]|12[120000]|15[150000]|17[170000]|20[200000]|25[250000]|30[300000]|40[400000]|50[500000]|75[750000]|100[1000000]|150[1500000]|200[2000000]|300[3000000]|400[4000000]|500[5000000]" type="hidden" value="" />
				<div class="sub-input"><a class="sia-2 selhover" id="sl-priceTo" href="javascript:void(0);">dsadsa</a></div><span class="sfg">&nbsp;</span>
				<input name="priceTo" id="priceTo" defaultsel="dsads[]|5[50000]|10[100000]|12[120000]|15[150000]|17[170000]|20[200000]|25[250000]|30[300000]|40[400000]|50[500000]|75[750000]|100[1000000]|150[1500000]|200[2000000]|300[3000000]|400[4000000]|500[5000000]" type="hidden" value="" />			
				<div class="sub-input"><a class="sia-2 selhover" id="sl-priceTo" href="javascript:void(0);">dsadsa</a></div><span class="sfg">&nbsp;</span>
				<input name="priceTo" id="priceTo" defaultsel="dsads[]|5[50000]|10[100000]|12[120000]|15[150000]|17[170000]|20[200000]|25[250000]|30[300000]|40[400000]|50[500000]|75[750000]|100[1000000]|150[1500000]|200[2000000]|300[3000000]|400[4000000]|500[5000000]" type="hidden" value="" />		
			</li>
		   <li class="i2" id="changeCity">��������</li>
		</ul>
	</div>
</div>

<div class="selCity" id="allCity" style="DISPLAY: none">
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td>���ͨ������</td>
    <td><input name="" type="text" /></td>
    <td>��ʼ���ڣ�</td>
    <td><input class="Wdate" type="text" onClick="WdatePicker()"></td>
    <td>�������ڣ�</td>
    <td><input class="Wdate" type="text" onClick="WdatePicker()"></td>
  </tr>
  <tr>
    <td>�̼Ҷ����ţ�</td>
    <td><input name="" type="text" /></td>
    <td>���׶Է���</td>
    <td><input name="" type="text" /></td>
    <td>&nbsp;</td>
    <td><input name="�ύ" type="button" value="�ύ" /></td>
  </tr>
</table>

    <div class="none"><A id=foldin href="javascript:;">����</A></div>
</div>

<!--��ѯ�����-->
<table id="myTable">
<thead>
<tr>
<th><input onclick="if(this.checked==true) { checkAll('test'); } else { clearAll('test'); }" type="checkbox" value="" name="test" title="ȫѡ/ȡ��"/></th>
<th>ƽ̨����</th>
<th>ҵ������/������</th>
<th>��ֵʱ��</th>
<th>����ʱ��</th>
<th>����/������״̬��</th>
<th>����/ʵ�ʽ��</th>
<th>�۷ѽ��/�ۿ���</th>
<th>�ʽ��˻�/���</th>
</tr>
</thead>
<tbody id="tab">
<tr>
<td><input type="checkbox" value="a" name="test"/> </td>
<td>web</td>
<td>���ų�ֵ<br />ʮǿ����</td>
<td>2012-09-28<br />01:05:38.0</td>
<td>2012-09-28<br />01:05:38.0</td>
<td>15098872657<br />T100000177��<span class="colo_red_sus">�ɹ�</span>)</td>
<td>12.00<br />12.00</td>
<td><span class="colo_red_sus">11.83</span><br />1.4</td>
<td>/</td>
</tr>
<tr>
<td><input type="checkbox" value="a" name="test"/> </td>
<td>web</td>
<td>���ų�ֵ<br />ʮǿ����</td>
<td>2012-09-28<br />01:05:38.0</td>
<td>2012-09-28<br />01:05:38.0</td>
<td>15098872657<br />T100000177��<span class="colo_red_sus">�ɹ�</span>)</td>
<td>12.00<br />12.00</td>
<td><span class="colo_red_sus">11.83</span><br />1.4</td>
<td>/</td>
</tr>
<tr>
<td><input type="checkbox" value="a" name="test"/> </td>
<td>web</td>
<td>���ų�ֵ<br />ʮǿ����</td>
<td>2012-09-28<br />01:05:38.0</td>
<td>2012-09-28<br />01:05:38.0</td>
<td>15098872657<br />T100000177��<span class="colo_red_sus">�ɹ�</span>)</td>
<td>12.00<br />12.00</td>
<td><span class="colo_red_sus">11.83</span><br />1.4</td>
<td>10000001<br />176489.00</td>
</tr>
<tr>
<td><input type="checkbox" value="a" name="test"/> </td>
<td>web</td>
<td>���ų�ֵ<br />ʮǿ����</td>
<td>2012-09-28<br />01:05:38.0</td>
<td>2012-09-28<br />01:05:38.0</td>
<td>15098872657<br />T100000177��<span class="colo_red_sus">�ɹ�</span>)</td>
<td>12.00<br />12.00</td>
<td><span class="colo_red_sus">11.83</span><br />1.4</td>
<td>10000001<br />176489.00</td>
</tr>
<tr>
<td><input type="checkbox" value="a" name="test"/> </td>
<td>web</td>
<td>���ų�ֵ<br />ʮǿ����</td>
<td>2012-09-28<br />01:05:38.0</td>
<td>2012-09-28<br />01:05:38.0</td>
<td>15098872657<br />T100000177��<span class="colo_red_sus">�ɹ�</span>)</td>
<td>12.00<br />12.00</td>
<td><span class="colo_red_sus">11.83</span><br />1.4</td>
<td>10000001<br />176489.00</td>
</tr>
<tr>
<td><input type="checkbox" value="a" name="test"/> </td>
<td>web</td>
<td>���ų�ֵ<br />ʮǿ����</td>
<td>2012-09-28<br />01:05:38.0</td>
<td>2012-09-28<br />01:05:38.0</td>
<td>15098872657<br />T100000177��<span class="colo_red_sus">�ɹ�</span>)</td>
<td>12.00<br />12.00</td>
<td><span class="colo_red_sus">11.83</span><br />1.4</td>
<td>/</td>
</tr>
<tr>
<td><input type="checkbox" value="a" name="test"/> </td>
<td>web</td>
<td>���ų�ֵ<br />ʮǿ����</td>
<td>2012-09-28<br />01:05:38.0</td>
<td>2012-09-28<br />01:05:38.0</td>
<td>15098872657<br />T100000177��<span class="colo_red_sus">�ɹ�</span>)</td>
<td>12.00<br />12.00</td>
<td><span class="colo_red_sus">11.83</span><br />1.4</td>
<td>10000001<br />176489.00</td>
</tr>
<tr>
<td><input type="checkbox" value="a" name="test"/> </td>
<td>web</td>
<td>���ų�ֵ<br />ʮǿ����</td>
<td>2012-09-28<br />01:05:38.0</td>
<td>2012-09-28<br />01:05:38.0</td>
<td>15098872657<br />T100000177��<span class="colo_red_sus">�ɹ�</span>)</td>
<td>12.00<br />12.00</td>
<td><span class="colo_red_sus">11.83</span><br />1.4</td>
<td>10000001<br />176489.00</td>
</tr>
<tr>
<td><input type="checkbox" value="a" name="test"/> </td>
<td>web</td>
<td>���ų�ֵ<br />ʮǿ����</td>
<td>2012-09-28<br />01:05:38.0</td>
<td>2012-09-28<br />01:05:38.0</td>
<td>15098872657<br />T100000177��<span class="colo_red_sus">�ɹ�</span>)</td>
<td>12.00<br />12.00</td>
<td><span class="colo_red_sus">11.83</span><br />1.4</td>
<td>10000001<br />176489.00</td>
</tr>
</tbody>
<tfoot>
<tr>
<td></td>
<td colspan="8">
<div class="grayr"><span class="disabled"> < </span><span class="current">1</span><a href="#?page=2" _fcksavedurl="#?page=2">2</a><a href="#?page=3" _fcksavedurl="#?page=3">3</a><a href="#?page=4" _fcksavedurl="#?page=4">4</a><a href="#?page=5" _fcksavedurl="#?page=5">5</a><a href="#?page=6" _fcksavedurl="#?page=6">6</a><a href="#?page=7" _fcksavedurl="#?page=7">7</a>...<a href="#?page=199" _fcksavedurl="#?page=199">199</a><a href="#?page=200" _fcksavedurl="#?page=200">200</a><a href="#?page=2" _fcksavedurl="#?page=2"> > </a></div>
<!--
<div id="page">
<a href="">�ס�ҳ</a><a href="">��һҳ</a><a href="">��һҳ</a><a href="">ĩ��ҳ</a></div>-->
</td>
</tr>
</tfoot>
</table>
<script type="text/javascript">
<!--
function changeTableBg()
{
var changeTr=document.getElementById("myTable").getElementsByTagName("tr");
   for(i=0;i<changeTr.length;i++)
   {
      changeTr[i].className=(i%2>0)?"even":"odd";
   changeTr[i].temp=changeTr[i].className;
   changeTr[i].onmouseover=function(){
              this.className='over';        
   }
   changeTr[i].onmouseout=function(){
              this.className=this.temp;        
   }
   }
}
//-->
</script>

</body>
</html>