<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<tr>
	<td>����</td>
	<td>
		<select name="city" onchange="city_district(this.form.district,this.value);area_agent(this.form.agent,this.value,this.form.district.value,true)" >	
			<logic:iterate id="citys" name="cityList">
				<option value="${citys[0]}">${citys[1]}</option>
			</logic:iterate>
		</select>
	</td>
</tr>
<tr>
	<td>Ƭ��</td>
	<td>
		<select name="district" onchange="area_agent(this.form.agent,this.form.city.value,this.value,true)">
			<option value="">����Ƭ��</option>
		</select>
	</td>
</tr>
<tr>
	<td>������</td>
	<td>
		<select name="agent">
			<option value="">���д���</option>
		</select>
	</td>
</tr>