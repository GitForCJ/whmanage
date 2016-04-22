package com.wlt.webm.business.bean.zdhuawei.fact;


import java.util.ArrayList;

import com.alibaba.fastjson.JSON;
import com.wlt.webm.business.bean.zdhuawei.CallbackEntityZD;
import com.wlt.webm.business.bean.zdhuawei.OrderEntityZD;
import com.wlt.webm.business.bean.zdhuawei.QueryEntityZD;
import com.wlt.webm.business.bean.zdhuawei.QueryResponeEntityZD;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		//�µ�
		OrderRequest orderrequest=new OrderRequest();
		orderrequest.setTimestamp("2015-11-09 13:52:29");
		orderrequest.setZdws_method("winksi.topUpPhone.orderOnline");
		OrderEntityZD zd=new OrderEntityZD();
		zd.setPhone("18682033916");
		zd.setArea("����");
		zd.setOperator("�ƶ�");
		zd.setCard_type("2");
		zd.setCardid("50M");
		zd.setPrice("3");
		zd.setCallback_url("callback_url");
		zd.setOrderid("123456789");
		zd.setProductid("");
		orderrequest.setZdws_params(zd);
		orderrequest.setZdws_sign(null);
		System.out.println("�µ��ӿ�����: "+JSON.toJSONString(orderrequest));
		//�µ�Ӧ��
		OrderResponse res=new OrderResponse();
		res.setCode("200");
		res.setMsg("�ɹ�");
		res.setOrderid("123456789");
		System.out.println("�µ��ӿ�Ӧ��: "+JSON.toJSONString(res));
		
		//������ѯ����
		QueryRequest query =new QueryRequest();
		query.setTimestamp("2015-11-09 13:52:29");
		query.setZdws_method("winksi.topUpPhone.queryOrder");
		QueryEntityZD zdquery=new QueryEntityZD();
		zdquery.setPhone("18682033916");
		zdquery.setCard_type("1");
		zdquery.setOrderid("12345678");
		zdquery.setStarttime("2015-11-12");
		zdquery.setEndtime("2015-11-13");
		zdquery.setStatus("1023");
		query.setZdws_params(zdquery);
		query.setZdws_sign(null);
		System.out.println("��ѯ����ӿ�:"+JSON.toJSONString(query));
		//��ѯӦ��
		QueryResponse queryres=new QueryResponse();
		queryres.setCode("200");
		queryres.setMsg("�ɹ�");
		ArrayList<QueryResponeEntityZD>  order=new ArrayList<QueryResponeEntityZD>();
		QueryResponeEntityZD qeuryzd=new QueryResponeEntityZD();
		qeuryzd.setArea("����");
		qeuryzd.setPhone("18682033916");
		qeuryzd.setOperator("�ƶ�");
		qeuryzd.setCard_type("1");
		qeuryzd.setCardid("30.00");
		qeuryzd.setPrice("29.00");
		qeuryzd.setFinishTime("2015-11-13 11:50:00");
		qeuryzd.setTime("2015-11-13 10:57:35");
		qeuryzd.setStatus("1020");
		qeuryzd.setCallBackTime("2015-11-13 11:51:00");
		qeuryzd.setCallBackStatus("200");
		qeuryzd.setOrderid("12345678");
		order.add(qeuryzd);
		QueryResponeEntityZD qeuryzd1=new QueryResponeEntityZD();
		qeuryzd1.setArea("����");
		qeuryzd1.setPhone("18682033916");
		qeuryzd1.setOperator("�ƶ�");
		qeuryzd1.setCard_type("1");
		qeuryzd1.setCardid("30.00");
		qeuryzd1.setPrice("29.00");
		qeuryzd1.setFinishTime("2015-11-13 11:50:00");
		qeuryzd1.setTime("2015-11-13 10:57:35");
		qeuryzd1.setStatus("1020");
		qeuryzd1.setCallBackTime("2015-11-13 11:51:00");
		qeuryzd1.setCallBackStatus("200");
		qeuryzd1.setOrderid("12345678");
		order.add(qeuryzd1);
		queryres.setOrder(order);
		System.out.println("��ѯӦ��ӿ�:"+JSON.toJSONString(queryres));
		//�ص�����
		CallbackRequest callback=new CallbackRequest();
		callback.setZdws_method("cpCallBack");
		callback.setTimestamp("2015-11-09 13:52:29");
		CallbackEntityZD callbackzd=new CallbackEntityZD();
		callbackzd.setCard_type("1");
		callbackzd.setOrderid("12345678");
		callbackzd.setPhone("18682033916");
		callbackzd.setPrice("29.00");
		callbackzd.setStatus("200");
		callback.setZdws_params(callbackzd);
		callback.setZdws_sign(null);
        System.out.println("�ص�����ӿ�:"+JSON.toJSONString(callback));
        //�ص�����
        CallbackResponse callbackRes=new CallbackResponse();
        callbackRes.setCode("200");
        callbackRes.setMsg("�ɹ�");
		
	}

}
