<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<tr>
	<td>地市</td>
	<td>
		<select name="city" onchange="city_district(this.form.district,this.value)" >
		<logic:iterate id="citys" name="cityList">
			<option value="${citys[0]}">${citys[1]}</option>
		</logic:iterate>
		</select>
	</td>
</tr>
<tr>
	<td>片区</td>
	<td>
		<select name="district"">
			<option value="">所有片区</option>
		</select>
	</td>
</tr>