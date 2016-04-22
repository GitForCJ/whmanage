package com.wlt.webm.business.bean.zy.entity.ec001;
/**
 * 服务扩展信息列表
 * @author fw
 *
 */
public class EC001USERINFOMAP {
	private String OptType;
	 private String ItemName;
	 private String ItemValue;
	public String getOptType() {
		return OptType;
	}
	/**
	 * 0-订购  2-修改  4-取消

	 * @param optType
	 */
	public void setOptType(String optType) {
		OptType = optType;
	}
	public String getItemName() {
		return ItemName;
	}
	/**
	 * 扩展信息名称
	 * @param itemName
	 */
	public void setItemName(String itemName) {
		ItemName = itemName;
	}
	public String getItemValue() {
		return ItemValue;
	}
	/**
	 * 扩展信息值
	 * @param itemValue
	 */
	public void setItemValue(String itemValue) {
		ItemValue = itemValue;
	}
	 
}
