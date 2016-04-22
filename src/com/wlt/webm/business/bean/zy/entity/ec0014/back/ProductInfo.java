package com.wlt.webm.business.bean.zy.entity.ec0014.back;
/**
 * 产品信息
 * @author fw
 *
 */
public class ProductInfo {
	private String ProductCode;
    private String ProductName;
    private String OrderNum;
    private String FlowValue;
    private String DealState;
    private String CrmApplyCode;
	public String getProductCode() {
		return ProductCode;
	}
	public void setProductCode(String productCode) {
		ProductCode = productCode;
	}
	public String getProductName() {
		return ProductName;
	}
	public void setProductName(String productName) {
		ProductName = productName;
	}
	public String getOrderNum() {
		return OrderNum;
	}
	public void setOrderNum(String orderNum) {
		OrderNum = orderNum;
	}
	public String getFlowValue() {
		return FlowValue;
	}
	public void setFlowValue(String flowValue) {
		FlowValue = flowValue;
	}
	public String getDealState() {
		return DealState;
	}
	public void setDealState(String dealState) {
		DealState = dealState;
	}
	public String getCrmApplyCode() {
		return CrmApplyCode;
	}
	public void setCrmApplyCode(String crmApplyCode) {
		CrmApplyCode = crmApplyCode;
	}
    
}
