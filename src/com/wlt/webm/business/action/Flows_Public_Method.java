package com.wlt.webm.business.action;

import java.util.ArrayList;
import java.util.Random;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.methods.GetMethod;

import com.alibaba.fastjson.JSON;
import com.bonc.wo_key.WoMd5;
import com.commsoft.epay.util.logging.Log;
import com.wlt.webm.business.bean.beijingFlow.Flow;
import com.wlt.webm.business.bean.liuliangfan.LlFan;
import com.wlt.webm.business.bean.unicom3gquery.FillProductDetail;
import com.wlt.webm.business.bean.unicom3gquery.FillProductInfo;
import com.wlt.webm.business.bean.unicom3gquery.HttpFillOP;
import com.wlt.webm.db.DBService;
import com.wlt.webm.message.PortalSend;
import com.wlt.webm.util.Tools;

/**
 * @author ʩ����
 */
public class Flows_Public_Method {
	static MultiThreadedHttpConnectionManager connectionManager = new MultiThreadedHttpConnectionManager();
	static HttpClient client = new HttpClient(connectionManager);
	static{
		client.getHttpConnectionManager().getParams().setConnectionTimeout(20*1000);
		client.getHttpConnectionManager().getParams().setSoTimeout(20*1000);
	}
	
	/**
	 * �����˾������ֵ����Ʒ��Ȩ
	 * @param phone ��ֵ����
	 * @param productid ��Ʒid
	 * @return boolean true��Ȩ�ɹ�,���Գ�ֵ,  false��Ȩʧ�ܻ�����ʧ�ܣ������Գ�ֵ
	 */
	public static boolean Flows_Authentication(String phone,String productid){
		String str=HttpFillOP.CHECKURL+"productId="+productid+"&activityId="+HttpFillOP.ACTIVITYID+"&phoneNum="+
			phone+"&token="+WoMd5.encode(HttpFillOP.SIGNKEY+phone+productid+HttpFillOP.ACTIVITYID);
		Log.info("�����˾������Ʒ��Ȩ,,,����:"+phone+",��ƷID:"+productid+",,����url:"+str);
		GetMethod method = new GetMethod(str);
		try {
			int st = client.executeMethod(method);
			if (st == 200) {
				String rs = method.getResponseBodyAsString();
				Log.info("�����˾������Ʒ��Ȩ,,,����:"+phone+",��ƷID:"+productid+",,http��Ӧ����:"+rs);
				if(rs!=null && rs.indexOf("status")>0){
					rs=rs.replace("\"","").replace(":","").replace("{","").replace("}","").replace(" ","").trim();
					rs=rs.substring(rs.indexOf("status")+"status".length(),rs.indexOf("status")+"status".length()+1);
					if("0".equals(rs)){
						return true;
					}
				}
			}
		} catch (Exception e) {
			Log.error("�����˾������Ʒ��Ȩ,,,����:"+phone+",��ƷID:"+productid+",,return false,,ϵͳ�쳣,,ex��"+e);
		} finally {
			method.releaseConnection();
		}
		return false;
	}
	
	/**
	 * �����˾ ������ѯ�ӿ�
	 * @param wh_OrderNum ��㶩���� 
	 * @return ����״״̬  0�ɹ�  -1ʧ��  ����������
	 */
	public static String getQueryOrderFlows(String wh_OrderNum){
		String url=HttpFillOP.RESULTURL+"orderNo="+wh_OrderNum+"&activityId="+HttpFillOP.ACTIVITYID;
		Log.info("�����˾����������ѯ,��㶩����:"+wh_OrderNum+",,��ѯ����url:"+url);
		GetMethod get = new GetMethod(url);
		try {
			int status = client.executeMethod(get);
			if (200 == status) {
				String result = HttpFillOP.convertStreamToString(get.getResponseBodyAsStream());
				Log.info("�����˾����������ѯ,��㶩����:"+wh_OrderNum+",,http��Ӧ����:"+result);
				if (null == result || "".equals(result.trim())) {
					Log.info("�����˾����������ѯ,��㶩����:"+wh_OrderNum+",,return 2 ������");
					return "2";
				}
				FillProductInfo info = JSON.parseObject(result.toLowerCase(),FillProductInfo.class);
				if(info==null){
					Log.info("�����˾����������ѯ,��㶩����:"+wh_OrderNum+",,return 2 ������");
					return "2";
				}
				if("0".equals(info.getStatus().trim())){
					Log.info("�����˾����������ѯ,��㶩����:"+wh_OrderNum+",,return 0 �ɹ�");
					return "0";
				}
				if("1".equals(info.getStatus().trim())){
					Log.info("�����˾����������ѯ,��㶩����:"+wh_OrderNum+",,return -1 ʧ��");
					return "-1";
				}
			}
		}catch (Exception e) {
			Log.error("�����˾����������ѯ,��㶩����:"+wh_OrderNum+",,ϵͳ�쳣,return 2 ������,,ex:"+e);
			return "2";
		}finally{
			get.releaseConnection();
		}
		Log.info("�����˾����������ѯ,��㶩����:"+wh_OrderNum+",,return 2 ������");
		return "2";
	}
	
	
	/**
	 * ��Ѷ ���� ������ֵ�ӿڹ��÷���
	 * @param mz ��ֵ��ֵ 50 
	 * @param wh_OrderNum ��㶩����
	 * @param phone ��ֵ����
	 * @param phone_type ��Ӫ������ 0���� 1�ƶ� 2��ͨ
	 * @param interId ��ֵ�ӿڽӿ�
	 * @param ds �����˾����
	 * @param buf ����ֵ���������� ����������ֵ�ӿڣ����صĶ�����
	 * @return 0�ɹ� -1ʧ�� ���������� �쳣
	 */
	public static String Request_Flows(String mz,String wh_OrderNum,String phone,String phone_type,String interId,String ds,StringBuffer buf){
		mz=mz.replace("mb","").replace("MB","");
		String rs = "";
		
		//���� �ŶΣ����� ��ѯ
		if(HttpFillOP.FLOW9.equals(interId) || HttpFillOP.FLOW8.equals(interId) || HttpFillOP.FLOW7.equals(interId)){//����ͨ
			rs =getRandomDigit(); 
//			rs=FlowsService.CZ_Orders(phone, mz, wh_OrderNum);
		}else if(HttpFillOP.FLOW12.equals(interId) || HttpFillOP.FLOW11.equals(interId) || HttpFillOP.FLOW10.equals(interId)){//����
			rs =getRandomDigit(); 
//			rs=""+LeliuFlowCharge.leliuFill(wh_OrderNum, phone, mz,phone_type);
		}else if(HttpFillOP.FLOW15.equals(interId) || HttpFillOP.FLOW14.equals(interId) || HttpFillOP.FLOW13.equals(interId)){//����
			rs =getRandomDigit(); 
//			rs=LianlianFlows.CZ_Orders(phone, mz,wh_OrderNum);
		}else if(HttpFillOP.FLOW3.equals(interId) || HttpFillOP.FLOW4.equals(interId) || HttpFillOP.FLOW5.equals(interId)){//˼��
			rs =getRandomDigit(); 
//			rs=Flow_SiKong.Recharge(wh_OrderNum, phone,mz+"M");
		}else if(HttpFillOP.FLOW6.equals(interId)){//�����ƶ�
			rs =getRandomDigit(); 
//			String wString=Webservices_Flow.flow_Result(wh_OrderNum,phone,mz);
//			rs=wString.split("#")[0];
//			Log.info("�����ƶ�������ֵ�ӿ�,,��㶩����:"+wh_OrderNum+",,,��ֵ��Ӧ�����ţ�"+wString.split("#")[1]);
////			if("0".equals(rs)){
////				db.update("update wht_orderform_" + time.substring(2, 6)+" set writeoff='"+wString.split("#")[1]+"' where tradeserial='"+seqNo1+"'");
////			}
		}else if(HttpFillOP.flow16.equals(interId)){//����
			rs =getRandomDigit(); 
//			rs=Mobile_Zb_Inter.cz_flow(phone,mz, wh_OrderNum);
		}else if(HttpFillOP.flow17.equals(interId)){//�����ƶ�����
			rs =getRandomDigit(); 
//			String checkAccount=GDFreeTrafficCharge.getTransIDO();
//			rs=GDFreeTrafficCharge.testEC001(phone,checkAccount,mz)+"";
//			buf.delete(0,buf.length());
//			buf.append(checkAccount);
		}else if(HttpFillOP.flow18.equals(interId)){//������  ��ͨ
			rs =getRandomDigit(); 
//			rs=LlFan.llFanFill(wh_OrderNum,phone,mz,"2","")+"";
		}else if(HttpFillOP.FLOw2.equals(interId)){//������ͨ
			rs =getRandomDigit(); 
			rs=Flow.BeiJin_Flow(phone,mz);
//			rs=rs.split("#")[0];
//			buf.delete(0,buf.length());
//			buf.append((rs.split("#").length>1)?rs.split("#")[1]:"");
		}else if(HttpFillOP.FLOw1.equals(interId)){//�����˾
			String cString="{'data':{'list':[{'price_num':10,'pro_num':5,'product_id':'51_001000','product_name':'100mb','product_price':'10.00'},{'price_num':15,'pro_num':5,'product_id':'51_002001','product_name':'200mb','product_price':'15.00'},{'price_num':6,'pro_num':5,'product_id':'51_000501','product_name':'50mb','product_price':'6.00'},{'price_num':3,'pro_num':5,'product_id':'51_002000','product_name':'20mb','product_price':'3.00'},{'price_num':30,'pro_num':5,'product_id':'51_005000','product_name':'500mb','product_price':'30.00'}]},'desc':'���ݶ�ȡ�ɹ�','status':'0'}";
			FillProductInfo infos=JSON.parseObject(cString,FillProductInfo.class);
			String realPrice = "";
//			FillProductInfo infos = HttpFillOP.filterProduct(phone,
//					HttpFillOP.phoneAreas1.get(ds),
//					HttpFillOP.ACTIVITYID);
			if (null == infos || !infos.status.equals("0")) {
				Log.info(",ͬ����Ʒ������Ϣ����,����ʧ��֪ͨ�ӿ�,,,��㶩����:"+wh_OrderNum+",,,return -1,ʧ��");
				return "-1";
			}
			boolean flag = false;
			@SuppressWarnings("unused")
			String productId = "";
			ArrayList<FillProductDetail> list = (ArrayList<FillProductDetail>) infos.getData().getList();
			for (FillProductDetail detail : list) {
				if ((mz + "MB").equalsIgnoreCase(detail.getProduct_name())) {
					flag = true;
					productId = detail.getProduct_id();
					realPrice = detail.getPrice_num() + "";
					break;
				}
			}
			if (!flag) {
				Log.info("��ֵ������ֵδ��ƥ��ͬ����Ʒ��Ϣ,����ʧ��֪ͨ�ӿ�,,,��㶩����:"+wh_OrderNum+",,,return -1,ʧ��");
				return "-1";
			}
			rs =getRandomDigit(); 
//			rs = HttpFillOP.fillProduct(wh_OrderNum, productId, phone, Long.parseLong(realPrice), HttpFillOP.ACTIVITYID) + "";
		}else{
			Log.info("ϵͳ���ô���,���ýӿڴ���,����ʧ��֪ͨ�ӿ�,,,��㶩����:"+wh_OrderNum+",,,return -1,ʧ��");
			return "-1";
		}
		Log.info("����������ֵ����,�����ţ�"+wh_OrderNum+",,��ֵ����:"+phone+",,,���ýӿ�:"+interId+",,,��ֵ���ύ���:"+rs+",(0�ɹ� -1ʧ�� ���������� �쳣),,,,��Ӧ�����ţ�"+buf.toString());
		return rs;
	}
	
	
	/**
	 * ��ֵ�������
	 * @param rs ��ֵ���  0�ɹ� -1ʧ��  ����������
	 * @param inter ��ֵ�ӿ�
	 * @param bean ������Ϣbean
	 * @param call ������  tencent || jd
	 */
	public static void Result_Processing(String rs,String inter,Object bean,String call){
		if(call!=null && HttpFillOP.tencent_code.equals(call.trim())){
			//��Ѷ
			TencentOrdersBean tBean=(TencentOrdersBean)bean;
			Log.info("��Ѷ������ֵ���ҵ������,��㶩���ţ�"+tBean.getSeqNo1()+",,��Ѷ������:"+tBean.getPaipai_dealid()+",,��ֵ���:"+rs+",(0�ɹ� -1ʧ��  ����������),,��ֵ���ýӿ�:"+inter);
			if ("0".equals(rs)) {
				if(HttpFillOP.FLOw1.equals(inter) || HttpFillOP.FLOw2.equals(inter)){
					Flows_Public_Method.Is_Back(tBean.getSeqNo1(), "0", call, null,tBean.getBeifen_reesult());
					return ;
				}
			} else if ("-1".equals(rs)) {
				Flows_Public_Method.Is_Back(tBean.getSeqNo1(), "1", call, null,tBean.getBeifen_reesult());
				return ;
			} else {
				if(HttpFillOP.FLOw1.equals(inter)){//�����˾
					tBean.setLast_time_query(System.currentTimeMillis());
					tBean.setInterid(HttpFillOP.FLOw1);
					PortalSend.getInstance().Send_LianTong_Query_Orders(tBean);
					return ;
				}else if(HttpFillOP.FLOw2.equals(inter)){//������ͨ����
					tBean.setLast_time_query(System.currentTimeMillis());
					tBean.setInterid(HttpFillOP.FLOw2);
					PortalSend.getInstance().Send_LianTong_Query_Orders(tBean);
				}
			}
		}else if(call!=null && HttpFillOP.jd_code.equals(call.trim())){
			//����
			JdOrderBean jBean=(JdOrderBean)bean;
			Log.info("����������ֵ���ҵ������,��㶩���ţ�"+jBean.getWh_order_num()+",,����������:"+jBean.getFillOrderNo()+",,��ֵ���:"+rs+",(0�ɹ� -1ʧ��  ����������),,��ֵ���ýӿ�:"+inter);
			if ("0".equals(rs)) {
				if(HttpFillOP.FLOw1.equals(inter) || HttpFillOP.FLOw2.equals(inter)){
					Flows_Public_Method.Is_Back(jBean.getWh_order_num(), "0", call, null,jBean.getBeifen_reesult());
					return ;
				}
			} else if ("-1".equals(rs)) {
				Flows_Public_Method.Is_Back(jBean.getWh_order_num(), "1", call, null,jBean.getBeifen_reesult());
				return ;
			} else {
				if(HttpFillOP.FLOw1.equals(inter)){//�����˾
					jBean.setLast_time_query(System.currentTimeMillis());
					jBean.setInterid(HttpFillOP.FLOw1);
					PortalSend.getInstance().Send_JD_Query_Message(jBean);
					return ;
				}else if(HttpFillOP.FLOw2.equals(inter)){//������ͨ����
					jBean.setLast_time_query(System.currentTimeMillis());
					jBean.setInterid(HttpFillOP.FLOw2);
					PortalSend.getInstance().Send_JD_Query_Message(jBean);
				}
			}
		}
	}
	
	/**
	 * �Ƿ�ص� 
	 * @param order_num ��ǰ���׶�����
	 * @param r_type 0�ɹ� ����ʧ��
	 * @param type 1000��Ѷ  ��1001 ����
	 * @param obj ��Ѷ�򾩶� ������Ϣbean ,(��������û�����,��֤ʧ��ʱ,�˲������� ������Ϣbean) or (��Ѷ�ظ�����,����Ҫ�������ݿ�,ֱ�ӻص�) 
	 * @param beifen_orderNum ����������ֵ�ӿڣ�������
	 * @return boolean
	 */
	public static boolean Is_Back(String order_num,String r_type,String type,Object obj,String beifen_orderNum){
		//����Ҫ�������ݿ�,ֻ��Ҫ�ص��ӿ�
		if(obj!=null){
			try{
				if(HttpFillOP.tencent_code.equals(type)){
					TencentOrdersBean bean=(TencentOrdersBean)obj;
					if("0".equals(r_type)){
						//��Ѷ֪ͨ��ֵ�ɹ�
						Log.info("��Ѷ�ص�֪ͨ,����״̬�ɹ�,��㶩����:"+bean.getSeqNo1()+",,��Ѷ������:"+bean.getPaipai_dealid());
						Tencent_Flows.Success_Back(bean.getPaipai_dealid(),bean.getSeqNo1(),Tencent_Flows.refundFee);
						return true;
					}else{
						//��Ѷ֪ͨ��ֵʧ��
						Log.info("��Ѷ�ص�֪ͨ,����״̬ʧ��,��㶩����:"+bean.getSeqNo1()+",,��Ѷ������:"+bean.getPaipai_dealid());
						Tencent_Flows.Failure_Back(bean.getPaipai_dealid(),bean.getSeqNo1(),Tencent_Flows.refundFee);
						return true;
					}
				}else if(HttpFillOP.jd_code.equals(type)){
					JdOrderBean jbean=(JdOrderBean)obj;
					if("0".equals(r_type)){
						//����֪ͨ��ֵ�ɹ�
						Log.info("�����ص�֪ͨ,����״̬�ɹ�,��㶩����:"+jbean.getWh_order_num()+",,����������:"+jbean.getFillOrderNo());
						Jd_Flows.Rs_break(jbean.getFillOrderNo(),jbean.getWh_order_num(),jbean.getFillAmount(),Tools.getNow3(),jbean.getNotifyUrl(), "1");
						return true;
					}else{
						//����֪ͨ��ֵʧ��
						Log.info("�����ص�֪ͨ,����״̬ʧ��,��㶩����:"+jbean.getWh_order_num()+",,����������:"+jbean.getFillOrderNo());
						Jd_Flows.Rs_break(jbean.getFillOrderNo(),jbean.getWh_order_num(),jbean.getFillAmount(),Tools.getNow3(),jbean.getNotifyUrl(), "2");
						return true;
					}
				}
			}catch (Exception e) {
				Log.error("�ص���Ѷ�򾩶�,���������ݿ�,ϵͳ�쳣,,,ex��"+e);
			}
			return false;
		}
		
		//��Ҫ�������ݿ�
		String tableName="wht_orderform_"+Tools.getNow3().substring(2,6);
		DBService db=null;
		try {
			db=new DBService();
			String update_time=Tools.getNow3();
			String[] isData=db.getStringArray("SELECT fl.o_tradeserial,fl.o_writecheck,fl.o_writeoff,fl.o_notityUrl FROM wht_flow_Reissue fl WHERE fl.r_dis=0 and fl.o_writecheck=(SELECT r.o_writecheck FROM wht_flow_Reissue r WHERE r.o_tradeserial='"+order_num+"' AND r.r_dis=1) LIMIT 0,1");
			if(isData==null || isData.length<=0){
				//û�в����
				String[] strs=db.getStringArray("SELECT tradeserial,writecheck,writeoff,term_type,buyid,tradeobject FROM "+tableName+" where tradeserial='"+order_num+"'");
				if("0".equals(r_type)){
					Log.info("��һ�γ�ֵ,������ֵ�ɹ�,�޸ĵ�ǰ����״̬Ϊ�ɹ�,�ص�֪ͨ,��㶩����:"+strs[0]+",,��Ѷ�򾩶�������:"+strs[1]);
					//��һ�ζ����ɹ�
					if(beifen_orderNum==null || "".equals(beifen_orderNum.trim())){
						db.update("update "+tableName+" set state=0,chgtime='"+update_time+"' where tradeserial='"+order_num+"'");
					}else{ 
						db.update("update "+tableName+" set state=0,chgtime='"+update_time+"',fundacct='"+beifen_orderNum+"' where tradeserial='"+order_num+"'");
					}
					if(HttpFillOP.tencent_code.equals(type)){
						//��Ѷ֪ͨ��ֵ�ɹ�
						Tencent_Flows.Success_Back(strs[1],strs[0],Tencent_Flows.refundFee);
						return true;
					}else if(HttpFillOP.jd_code.equals(type)){
						//����֪ͨ��ֵ�ɹ�
						Jd_Flows.Rs_break(strs[1], strs[0], (strs[2].indexOf("mb")>0)?strs[2].replace("mb",""):strs[2], update_time, strs[3], "1");
						return true;
					}
				}else{
					Log.info("��һ�γ�ֵ,������ֵʧ��,�޸ĵ�ǰ����״̬Ϊʧ��,д�벹�䶩��,��㶩����:"+strs[0]+",,��Ѷ�򾩶�������:"+strs[1]);
					String tradeserial=Tools.getNow3()+"as"+((int)(Math.random()*1000)+1000)+((char)(new Random().nextInt(26) + (int)'a'))+((char)(new Random().nextInt(26) + (int)'A'));
					//��һ�ζ���ʧ��
					if(beifen_orderNum==null || "".equals(beifen_orderNum.trim())){
						db.update("update "+tableName+" set state=1,chgtime='"+update_time+"' where tradeserial='"+order_num+"'");
					}else{
						db.update("update "+tableName+" set state=1,chgtime='"+update_time+"',fundacct='"+beifen_orderNum+"' where tradeserial='"+order_num+"'");
					}
					//����û�в��书��
					if(HttpFillOP.jd_code.equals(type)){
						Jd_Flows.Rs_break(isData[1], isData[0], (isData[2].indexOf("mb")>0)?isData[2].replace("mb",""):isData[2], update_time, isData[3], "2");
						return true;
					}
					String create_inter=getInter(strs[4],strs[5],strs[2],tradeserial);
					db.update("INSERT INTO wht_flow_Reissue(r_dis,o_tradeserial,o_tradeobject,o_buyid,o_service,o_fee,o_tradetime,o_chgtime,o_state,o_writeoff,o_writecheck,o_userno,o_phone_type,o_city,o_notityUrl,handle_state,o_type,a1) "+
					 "SELECT '0',tradeserial,tradeobject,buyid,service,fee,tradetime,chgtime,state,writeoff,writecheck,userno,phone_type,phone_pid,term_type,'',phoneleft,fundacct FROM "+tableName+"  WHERE tradeserial='"+order_num+"' "+
					 "UNION "+
					 "SELECT '1','"+tradeserial+"',tradeobject,'"+create_inter+"',service,fee,'"+update_time+"','','4',writeoff,writecheck,userno,phone_type,phone_pid,term_type,'0',phoneleft,'' FROM "+tableName+"  WHERE tradeserial='"+order_num+"' ");
					Log.info("��һ�γ�ֵ,������ֵʧ��,д�벹�䶩���ɹ�,�ȴ�����,��㶩����:"+strs[0]+",,��Ѷ�򾩶�������:"+strs[1]);
					return true;
				}
			}else{
				//�Ѿ������
				if("0".equals(r_type)){
					Log.info("�ڶ��γ�ֵ,���䶩��,������ֵ�ɹ�,�޸ĵ�ǰ����״̬Ϊ,�ɹ�,�ص���Ѷ�򾩶�,������㶩����:"+order_num+",,ԭʼ��㶩����:"+isData[0]+",,��Ѷ�򾩶�������:"+isData[1]);
					if(beifen_orderNum==null || "".equals(beifen_orderNum.trim())){
						db.update("update wht_flow_Reissue set o_state=0,o_chgtime='"+update_time+"',handle_state='2' where o_tradeserial='"+order_num+"' and r_dis=1");
					}else{
						db.update("update wht_flow_Reissue set o_state=0,o_chgtime='"+update_time+"',handle_state='2',a1='"+beifen_orderNum+"' where o_tradeserial='"+order_num+"' and r_dis=1");
					}
					if(HttpFillOP.tencent_code.equals(type)){
						//��Ѷ֪ͨ��ֵ�ɹ�
						Tencent_Flows.Success_Back(isData[1],isData[0],Tencent_Flows.refundFee);
						return true;
					}else if(HttpFillOP.jd_code.equals(type)){
						//����֪ͨ��ֵ�ɹ�
						Jd_Flows.Rs_break(isData[1], isData[0], (isData[2].indexOf("mb")>0)?isData[2].replace("mb",""):isData[2], update_time, isData[3], "1");
						return true;
					}
				}else{
					Log.info("�ڶ��γ�ֵ,���䶩��,������ֵʧ��,�޸ĵ�ǰ����״̬Ϊ,ʧ��,�ص���Ѷ�򾩶�,������㶩����:"+order_num+",,ԭʼ��㶩����:"+isData[0]+",,��Ѷ�򾩶�������:"+isData[1]);
					if(beifen_orderNum==null || "".equals(beifen_orderNum.trim())){
						db.update("update wht_flow_Reissue set o_state=1,o_chgtime='"+update_time+"',handle_state='2' where o_tradeserial='"+order_num+"' and r_dis=1");
					}else{
						db.update("update wht_flow_Reissue set o_state=1,o_chgtime='"+update_time+"',handle_state='2',a1='"+beifen_orderNum+"' where o_tradeserial='"+order_num+"' and r_dis=1");
					}
					if(HttpFillOP.tencent_code.equals(type)){
						//��Ѷ֪ͨ��ֵʧ��
						Tencent_Flows.Failure_Back(isData[1],isData[0],Tencent_Flows.refundFee);
						return true;
					}else if(HttpFillOP.jd_code.equals(type)){
						//����֪ͨ��ֵʧ��
						Jd_Flows.Rs_break(isData[1], isData[0], (isData[2].indexOf("mb")>0)?isData[2].replace("mb",""):isData[2], update_time, isData[3], "2");
						return true;
					}
				}
			}
		} catch (Exception e) {
			Log.error("����,������:"+order_num+",,��ֵ���:"+r_type+",,ϵͳ�쳣,,,ex:"+e);
		}finally{
			if(db!=null){
				db.close();
				db=null;
			}
		}
		return false;
	}
	
	/**
	 * ��ȡ��Ҫ�л��Ľӿ�id
	 * @param isInter ��ǰ�ӿ� id
	 * @param tradeobject �������
	 * @param mz ������ֵ
	 * @param tradeserial ���䶩���� 
	 * @return String �ӿ�id
	 */
	public static String getInter(String isInter,String tradeobject,String mz,String tradeserial){ 
		if(HttpFillOP.FLOw1.equals(isInter)){ //�����ǰ�ӿ��ǿ����˾���򷵻� ���ֽӿ�
			return HttpFillOP.FLOw2;
		}
		if(HttpFillOP.FLOw2.equals(isInter)){//�����ǰ�ӿ�ʱ���ֽӿڣ��򷵻� �����˾
			return HttpFillOP.FLOw1;
		}
		
		mz=mz.replace("mb","").replace("MB","").trim();
		
		Log.info("��㶩����:"+tradeserial+",,��Ȩ����:"+tradeobject+",,��Ȩ��ֵ:"+mz+",,���ÿ����˾��Ȩ�ӿ�");
		boolean bool=false;
		//���ü�Ȩ�ӿ� ���Գ�ֵ(�����˾)�������Գ�ֵ�ߣ����֣�
		if(HttpFillOP.phone_check.containsKey(mz)){
			bool=Flows_Public_Method.Flows_Authentication(tradeobject,HttpFillOP.phone_check.get(mz));
		}
		if(bool){
			Log.info("��㶩����:"+tradeserial+",,��Ȩ����:"+tradeobject+",,��Ȩ��ֵ:"+mz+",,��Ȩ�ɹ�,���� �����˾�ӿڱ��");
			return HttpFillOP.FLOw1;//ʡ��˾����
		}else{
			Log.info("��㶩����:"+tradeserial+",,��Ȩ����:"+tradeobject+",,��Ȩ��ֵ:"+mz+",,��Ȩʧ��,���� ���������ӿڱ��");
			return HttpFillOP.FLOw2;//��������
		}
	}
	
	public static String getRandomDigit() {
		Random r = new Random();
		int n = r.nextInt(100);
		String m;
		if (n < 35) {
			m = "0";
			return m;
		} else if (n < 70) {
			m = "-1";
			return m;
		}else {
			m = "2";
			return m;
		}
		
	}
}
