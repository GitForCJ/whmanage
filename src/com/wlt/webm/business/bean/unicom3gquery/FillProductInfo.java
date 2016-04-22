package com.wlt.webm.business.bean.unicom3gquery;

import java.util.List;

import com.alibaba.fastjson.JSON;

/**
 * 向后产品过滤列表
 * 
 * @author 1989
 * 
 */
public class FillProductInfo {

	public String desc;
	public String status;
	public ProList data;

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public ProList getData() {
		return data;
	}

	public void setData(ProList data) {
		this.data = data;
	}

	public static void main(String[] args) {
		String str = "{\"desc\":\"数据读取成功\",\"status\":\"0\",\"data\":{\"list\":[{\"product_id\":\"51_002001\",\"PRODUCT_NAME\":\"200MB\",\"PRODUCT_PRICE\":\"15元\",\"price_num\":15,\"PRO_NUM\":5}]}}";
		FillProductInfo info = JSON.parseObject(str, FillProductInfo.class);
		List<FillProductDetail> list = info.getData().getList();
		for (FillProductDetail po : list) {
			String proid = po.getProduct_id();
			int num = po.getPrice_num();
			System.out.println(proid+"   "+num);
		}

	}

}
