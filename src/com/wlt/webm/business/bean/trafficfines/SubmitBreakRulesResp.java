package com.wlt.webm.business.bean.trafficfines;

/**
 * ��Υ�´��쵥���ض���
 * @author 1989
 *
 */
public class SubmitBreakRulesResp {
private int code;
private String desc;
private PeccancyWOModel order;
public int getCode() {
	return code;
}
public void setCode(int code) {
	this.code = code;
}
public String getDesc() {
	return desc;
}
public void setDesc(String desc) {
	this.desc = desc;
}
public PeccancyWOModel getOrder() {
	return order;
}
public void setOrder(PeccancyWOModel order) {
	this.order = order;
}

}
