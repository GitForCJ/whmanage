package com.wlt.webm.business.bean.zy.entity.ec0014.back;

import java.util.List;
/**
 * 一个单的信息
 * @author fw
 *
 */
public class EC0014BackOrderInfo {
	private String BusinessCode;
    private String Mobile;
    private List<ProductInfo> productInfo;
	public String getBusinessCode() {
		return BusinessCode;
	}
	public void setBusinessCode(String businessCode) {
		BusinessCode = businessCode;
	}
	public String getMobile() {
		return Mobile;
	}
	public void setMobile(String mobile) {
		Mobile = mobile;
	}
	public List<ProductInfo> getProductInfo() {
		return productInfo;
	}
	public void setProductInfo(List<ProductInfo> productInfo) {
		this.productInfo = productInfo;
	}
    
}
