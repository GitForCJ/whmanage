<!-- 在界面中显示年月，显示占4个字段情况 -->
<%@ page contentType="text/html; charset=gb2312" language="java"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ page import="com.commsoft.epay.pc.util.*" %>
<%
	request.setAttribute("years", Tools.getToCurYears());   
	request.setAttribute("months", Tools.getMonths()); 
	String nowMonth = Tools.getNow3().substring(0,6);
	String yearMonth = Tools.getPreMonth(nowMonth); 
	request.setAttribute("curYear", yearMonth.substring(0, 4));
	request.setAttribute("curMonth", yearMonth.substring(4, 6));
	request.setAttribute("firstMonth","01");
%>
 <tr>
<td>起始年月</td>
<td>
	<select name="startyear">
		<logic:iterate id="year" name="years">
			<option value="${year}" ${year==curYear ? " selected" : ""}>
				${year}
			</option>
		</logic:iterate>
	</select>
	年
	<select name="startmonth">
		<logic:iterate id="month" name="months">
			<option value="${month[0]}" ${month[0] ==firstMonth ? " selected" : ""}>
				${month[1]}
			</option>
		</logic:iterate>
	</select>
	月
</td>
</tr>
 <tr>
<td>
	终止年月
</td>
<td>
	<select name="endyear">
		<logic:iterate id="year" name="years">
			<option value="${year}" ${year==curYear ? " selected" : ""}>
				${year}
			</option>
		</logic:iterate>
	</select>
	年
	<select name="endmonth">
		<logic:iterate id="month" name="months">
			<option value="${month[0]}" ${month[0] ==curMonth ? " selected" : ""}>
				${month[1]}
			</option>
		</logic:iterate>
	</select>
	月
</td>
</tr>
