<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<tr>
	<td>地市</td>
	<td>
		<select name="city" onchange="city_district(this.form.district,this.value);area_agent(this.form.agent,this.value,this.form.district.value,true)" >	
			<logic:iterate id="citys" name="cityList">
				<option value="${citys[0]}">${citys[1]}</option>
			</logic:iterate>
		</select>
	</td>
</tr>
<tr>
	<td>片区</td>
	<td>
		<select name="district" onchange="area_agent(this.form.agent,this.form.city.value,this.value,true)">
			<option value="">所有片区</option>
		</select>
	</td>
</tr>
<tr>
	<td>代理商</td>
	<td>
		<select name="agent">
			<option value="">所有代理</option>
		</select>
	</td>
</tr>