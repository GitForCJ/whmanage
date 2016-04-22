<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<tr>
	<td>代理商</td>
	<td>
		<select name="agent" onchange="agent_city(this.form.city,this.value);city_agent_district(this.form.district,this.value,this.form.agent.value)" >
			<option value="${userSession.syslevelId }">${userSession.syslevelName }</option>
			<logic:iterate id="agents" name="agentList">
				<option value="${agents[0]}">${agents[1]}</option>
			</logic:iterate>
		</select>
	</td>
</tr>
<tr>
	<td >地市</td>
	<td>
		<select name="city" onchange="city_agent_district(this.form.district,this.value,this.form.agent.value)" >
			<option value="">所有地市</option>
		</select>
	</td>
</tr>
<tr>
	<td>片区</td>
	<td>
		<select name="district" >
			<option value="">所有片区</option>
		</select>
	</td>
</tr>