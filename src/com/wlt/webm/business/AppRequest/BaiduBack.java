package com.wlt.webm.business.AppRequest;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.commsoft.epay.util.logging.Log;
import com.wlt.webm.business.AppRequest.baidu.PartnerConfig;
import com.wlt.webm.business.bean.BiProd;
import com.wlt.webm.business.bean.beijingFlow.BeanFlow;
import com.wlt.webm.business.bean.unicom3gquery.HttpFillOP;
import com.wlt.webm.business.form.TPForm;
import com.wlt.webm.db.DBService;
import com.wlt.webm.gm.bean.GuanMingBean;
import com.wlt.webm.message.MemcacheConfig;

public class BaiduBack extends DispatchAction {

	/**
	 * @throws  
	 * 
	 */
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		/**
		 * 1�����ñ���
		 */
		request.setCharacterEncoding("gbk");
		response.setContentType("text/html;charset=gbk");
		response.setCharacterEncoding("gbk");
		
		String sp_no=request.getParameter("sp_no");
		String order_no=request.getParameter("order_no");
		String bfb_order_no=request.getParameter("bfb_order_no");
		String bfb_order_create_time=request.getParameter("bfb_order_create_time");
		String pay_time=request.getParameter("pay_time");
		String pay_type=request.getParameter("pay_type");
		String total_amount=request.getParameter("total_amount");
		String fee_amount=request.getParameter("fee_amount");
		String currency=request.getParameter("currency");
		String buyer_sp_username=request.getParameter("buyer_sp_username");
		String pay_result=request.getParameter("pay_result");
		String input_charset=request.getParameter("input_charset");
		String version=request.getParameter("version");
		String sign=request.getParameter("sign");
		String sign_method=request.getParameter("sign_method");
		
		String extra=request.getParameter("extra");
		String transport_amount=request.getParameter("transport_amount");
		String unit_amount=request.getParameter("unit_amount");
		String unit_count=request.getParameter("unit_count");
		String bank_no=request.getParameter("bank_no");
		
		Log.info("�ٶ�Ǯ���ص���Ϣ,,,������:"+order_no+",,֧�����:"+pay_result+",,,֧�����(��):"+total_amount);
		
		String local_sign=com.wlt.webm.business.AppRequest.baidu.MD5.toMD5("bank_no="+bank_no+"&bfb_order_create_time="+bfb_order_create_time+"&bfb_order_no="+bfb_order_no+"&buyer_sp_username="+buyer_sp_username+"&currency="+currency+"&extra="+extra+"&fee_amount="+fee_amount+"&input_charset="+input_charset+"&order_no="+order_no+"&pay_result="+pay_result+"&pay_time="+pay_time+"&pay_type="+pay_type+"&sign_method="+sign_method+"&sp_no="+sp_no+"&total_amount="+total_amount+"&transport_amount="+transport_amount+"&unit_amount="+unit_amount+"&unit_count="+unit_count+"&version="+version+"&key="+PartnerConfig.MD5_PRIVATE);
		if("1".equals(pay_result.trim())){
			if(sign.trim().equalsIgnoreCase(local_sign.trim())){
				Log.info("�ٶ�Ǯ���ص���Ϣ,,,������:"+order_no+",,֧�����:"+pay_result+",֧���ɹ�,,,֧�����(��):"+total_amount);
				
				if(order_no==null || "".equals(order_no.trim()) ||
						total_amount==null || "".equals(total_amount.trim())){
					Log.info("�ٶ�Ǯ���ص���Ϣ,,,������:"+order_no+",,֧�����:"+pay_result+",֧���ɹ�,,�����Ż�֧�����δ��,return null,,,֧�����(��):"+total_amount);
					return null;
				}
				
				StringBuffer buffer=new StringBuffer();
				buffer.append("<HTML><head>");
				buffer.append("<meta name=\"VIP_BFB_PAYMENT\" content=\"BAIFUBAO\">");
				buffer.append("</head>");
				buffer.append("<body>");
				buffer.append("֧���ɹ�����ǩͨ��" + "�����ţ�" + order_no + "<br/>");
				buffer.append("�ٸ������ص�ǩ���� :" + sign + "<br/>");
				buffer.append("�������ɵ�ǩ����     :" + local_sign + "<br/>");
				buffer.append("</body></html>");
				
				PrintWriter out = response.getWriter();
				out.println(buffer.toString());
				
				Log.info("�ٶ�Ǯ���ص���Ϣ,,,������:"+order_no+",,֧�����:"+pay_result+",֧���ɹ�,,,֧�����(��):"+total_amount+",,��Ӧhtml:"+buffer.toString());
				
				//֧���ɹ� ҵ����
				services_oper(order_no);
				
				out.flush();
				out.close();
			}else{
				Log.info("�ٶ�Ǯ���ص���Ϣ,,,������:"+order_no+",,֧�����:"+pay_result+",֧���ɹ�,ǩ������,,,֧�����(��):"+total_amount);
			}
		}else{
			Log.info("�ٶ�Ǯ���ص���Ϣ,,,������:"+order_no+",,֧�����:"+pay_result+",֧��ʧ��,,,֧�����(��):"+total_amount);
		}
		return null;
	}
	
	/**
	 * �ٶ�֧���ɹ�ҵ����
	 * @param order_no app_RechargeOrder_1509 orderNum
	 * @param pay_result 1֧���ɹ�
	 * @param total_amount ֧����� ��
	 */
	@SuppressWarnings("static-access")
	public static void services_oper(String order_no){
		Log.info("�ص��ɹ�,֧���ɹ�,����ҵ������,,,������:"+order_no);
		
		String tableName="app_RechargeOrder_"+order_no.substring(2,6);
		
		DBService db=null;
		try {
			db=new DBService();
			String[] strs=db.getStringArray("SELECT ap.id,ap.userno,ap.productId,ap.productName,ap.productBody,ap.amount,ap.price,ap.realPrice,ap.phoneNum,ap.payStatus,ap.rechargeStatus,ap.createOrderTime,ap.phoneType,ap.areaId FROM "+tableName+" ap WHERE ap.orderNum='"+order_no.trim()+"'");
			if(strs==null || strs.length<=0){
				Log.info("�ص��ɹ�,֧���ɹ�,,,������:"+order_no+",,����������,reutrn null");
				return ;
			}
			if(!"1004".equals(strs[9])){
				Log.info("�ص��ɹ�,֧���ɹ�,,,������:"+order_no+",,�ö����Ѵ����,�ظ��ص�,reutrn null");
				return;
			}
			TPForm tp = MemcacheConfig.getInstance().getTP(strs[1]);
			if(tp==null){
				Log.info("�ص��ɹ�,֧���ɹ�,,,������:"+order_no+",,����ϵͳ����,�޷���ȡ���ӿ�����Ϣuserno:"+strs[1]+",reutrn null");
				return ;	
			}
			//�޸�֧�����Ϊ�ɹ�������
			db.update("UPDATE "+tableName+" SET payStatus=1000 WHERE orderNum='"+order_no.trim()+"'");
			
			String rechargeStatus="";
			
			if("10000001".equals(strs[3])){
				Log.info("�ص��ɹ�,֧���ɹ�,,,������:"+order_no+",,Q��ҵ��,��װQ��������Ϣ,����Q�ҳ�ֵ,,,");
				//Q��ҵ��
				// ֧����
				GuanMingBean gmbean = new GuanMingBean();
				HashMap<String, String> map = new HashMap<String, String>();
				map.put("num", strs[5]);
				map.put("in_acct", strs[8]);
				map.put("gameapi","0005");
				map.put("ip", tp.getIp());
				map.put("flag", "1");// 1ΪֱӪ��0Ϊ��ֱӪ
				map.put("qbCommission",tp.getQbCommission());//ÿ���ӿ��̶�Ӧ��Ӷ��
				map.put("caidan",tp.getCaidan());//ÿ���ӿ��̶�Ӧ�Ĳ� ����   -- 0��  1����
				map.put("term_type","0");
				
				// 0 �ɹ� 1 ʧ�� 2�����л��쳣 3�ظ����� 4�˺����� 5Ӷ�𲻴���
				int state = gmbean.qbInterfaceDistribution(map, tp.getUser_no(),tp.getUser_login(), "1", tp.getSa_zone(), order_no, tp.getQbFlag());
				if(state==0){
					rechargeStatus="1000";//�ɹ�
				}else if(state==1 || state==3 || state==4 || state==5){
					rechargeStatus="1002";//ʧ��
				}else {
					rechargeStatus="1003";//������
				}
				db.update("UPDATE "+tableName+" SET rechargeStatus="+rechargeStatus+" WHERE orderNum='"+order_no.trim()+"'");
				Log.info("�ص��ɹ�,֧���ɹ�,,,������:"+order_no+",,Q��ҵ��,��ֵ�������,��ֵ���:"+state+",,�������(0 �ɹ� 1 ʧ�� 2�����л��쳣 3�ظ����� 4�˺����� 5Ӷ�𲻴���)");
				return ;
			}else if("10000000".equals(strs[3])){
				Log.info("�ص��ɹ�,֧���ɹ�,,,������:"+order_no+",,���ѳ�ֵҵ��,��װ���ѳ�ֵ������Ϣ,���𻰷ѳ�ֵ,,,");
				//���ѳ�ֵҵ��
				BiProd bp = new BiProd();
				String[] result = bp.getEmploy(db, strs[12], tp.getUser_site()
						+ "", strs[13], tp.getUser_no(),"2", Integer.parseInt(strs[6]) / 1000, 1, Integer.parseInt(tp.getGroups()));
				if (null == result) {
					rechargeStatus="1002";
					Log.info("�ص��ɹ�,֧���ɹ�,,,������:"+order_no+",,���ѳ�ֵҵ��,����Ӷ���ȡʧ��,");
				}else{
					int k = bp.nationFill(1 + "", tp.getUser_no(), strs[13], tp.getUser_site()+ "", strs[12], strs[8], order_no, Double.parseDouble(strs[6]), result, tp.getSa_zone(), db, tp.getUser_login(), tp.getIp(),"OF", "1");
					if (k == 0 || k >= 20) {
						rechargeStatus="1000";//�ɹ�
					}else if (k == 1 || k == -1) {
						rechargeStatus="1002";//"��ֵʧ��"
					} else if(k==-5){
						rechargeStatus="1002";//"�˻�����"
					}else if(k==6){
						rechargeStatus="1002";//"��ˮ���ظ�����ʧ��"
					}else {
						rechargeStatus="1003";//������
					}
				}
				db.update("UPDATE "+tableName+" SET rechargeStatus="+rechargeStatus+" WHERE orderNum='"+order_no.trim()+"'");
				Log.info("�ص��ɹ�,֧���ɹ�,,,������:"+order_no+",,���ѳ�ֵҵ��,���ѳ�ֵ�������,");
			}else if("10000002".equals(strs[3])){
				String productId=strs[2];
				String phoneType=strs[12];
				String name=strs[4].substring(0,strs[4].indexOf("mb"));
				String userno=strs[1];
				String phone=strs[8];
				String orderid=order_no;
				String areaId=strs[13];
				String realPrice=strs[6];
				//������ֵҵ��
				String interId=BiProd.getFlowInterface(phoneType,areaId)+"";//BiProd.getFlowInterface(Integer.parseInt(phoneType),Integer.parseInt(name))+"".trim();
				HashMap<String,String> map=null;
				if(HttpFillOP.FLOw1.equals(interId)){//ʡ����
					map= new HttpFillOP().fillFlow(orderid, productId, phone,
						Integer.parseInt(realPrice), HttpFillOP.ACTIVITYID, userno, tp.getUser_login(),tp.getSa_zone(),
						areaId, tp.getUser_site()+"",
						tp.getUserPt(),tp.getFlowgroups(),interId,"1",name);
				}else if(HttpFillOP.FLOw2.equals(interId)){//������ͨ����
					map=BeanFlow.fillFlow(orderid, productId, phone,
							Integer.parseInt(realPrice),HttpFillOP.ZDY, userno, tp.getUser_login(),tp.getSa_zone(),
							areaId, tp.getUser_site()+"", 
							tp.getUserPt(),tp.getFlowgroups(),interId,"1",name);
				}else if(HttpFillOP.FLOW3.equals(interId)){//˼���ƶ�����
					map=BeanFlow.sikong_flow(orderid, productId, phone,
							Integer.parseInt(realPrice), HttpFillOP.ZDY, userno, tp.getUser_login(),tp.getSa_zone(),
							areaId, tp.getUser_site()+"", 
							tp.getUserPt(),tp.getFlowgroups(),interId,"1",name,"1");//�ƶ�
				}else if(HttpFillOP.FLOW4.equals(interId)){//˼����ͨ����
					map=BeanFlow.sikong_flow(orderid, productId, phone,
							Integer.parseInt(realPrice),HttpFillOP.ZDY, userno, tp.getUser_login(),tp.getSa_zone(),
							areaId, tp.getUser_site()+"", 
							tp.getUserPt(),tp.getFlowgroups(),interId,"1",name,"2");//��ͨ
				}else if(HttpFillOP.FLOW5.equals(interId)){//˼�յ�������
					map=BeanFlow.sikong_flow(orderid, productId, phone,
							Integer.parseInt(realPrice), HttpFillOP.ZDY, userno, tp.getUser_login(),tp.getSa_zone(),
							areaId, tp.getUser_site()+"",
							tp.getUserPt(),tp.getFlowgroups(),interId,"1",name,"0");//����
				}else if(HttpFillOP.FLOW6.equals(interId)){//�����ƶ�����
					map=BeanFlow.WebServices_flow(orderid, productId, phone,
							Integer.parseInt(realPrice),HttpFillOP.ZDY, userno, tp.getUser_login(),tp.getSa_zone(),
							areaId, tp.getUser_site()+"", 
							tp.getUserPt(),tp.getFlowgroups(),interId,"1",name);
				}else if(HttpFillOP.FLOW8.equals(interId)){//����ͨ�ƶ�����
					map=BeanFlow.Dhst_flow(orderid, productId, phone,
							Integer.parseInt(realPrice), HttpFillOP.ZDY, userno, tp.getUser_login(),tp.getSa_zone(),
							areaId, tp.getUser_site()+"", 
							tp.getUserPt(),tp.getFlowgroups(),interId,"1",name,"1");//�ƶ�
				}else if(HttpFillOP.FLOW9.equals(interId)){//����ͨ��ͨ����
					map=BeanFlow.Dhst_flow(orderid, productId, phone,
							Integer.parseInt(realPrice),HttpFillOP.ZDY, userno, tp.getUser_login(),tp.getSa_zone(),
							areaId, tp.getUser_site()+"", 
							tp.getUserPt(),tp.getFlowgroups(),interId,"1",name,"2");//��ͨ
				}else if(HttpFillOP.FLOW7.equals(interId)){//����ͨ��������
					map=BeanFlow.Dhst_flow(orderid, productId, phone,
							Integer.parseInt(realPrice), HttpFillOP.ZDY, userno, tp.getUser_login(),tp.getSa_zone(),
							areaId, tp.getUser_site()+"",
							tp.getUserPt(),tp.getFlowgroups(),interId,"1",name,"0");//����
				}else if(HttpFillOP.FLOW11.equals(interId)){//�����ƶ�����
					map=BeanFlow.LeLiu_flow(orderid, productId, phone,
							Integer.parseInt(realPrice), HttpFillOP.ZDY, userno, tp.getUser_login(),tp.getSa_zone(),
							areaId, tp.getUser_site()+"", 
							tp.getUserPt(),tp.getFlowgroups(),interId,"1",name,"1");//�ƶ�
				}else if(HttpFillOP.FLOW12.equals(interId)){//������ͨ����
					map=BeanFlow.LeLiu_flow(orderid, productId, phone,
							Integer.parseInt(realPrice),HttpFillOP.ZDY, userno, tp.getUser_login(),tp.getSa_zone(),
							areaId, tp.getUser_site()+"", 
							tp.getUserPt(),tp.getFlowgroups(),interId,"1",name,"2");//��ͨ
				}else if(HttpFillOP.FLOW10.equals(interId)){//������������
					map=BeanFlow.LeLiu_flow(orderid, productId, phone,
							Integer.parseInt(realPrice), HttpFillOP.ZDY, userno, tp.getUser_login(),tp.getSa_zone(),
							areaId, tp.getUser_site()+"",
							tp.getUserPt(),tp.getFlowgroups(),interId,"1",name,"0");//����
				}else if(HttpFillOP.FLOW14.equals(interId)){//�����ƶ�����
					map=BeanFlow.Lianlian_flow(orderid, productId, phone,
							Integer.parseInt(realPrice), HttpFillOP.ZDY, userno, tp.getUser_login(),tp.getSa_zone(),
							areaId, tp.getUser_site()+"", 
							tp.getUserPt(),tp.getFlowgroups(),interId,"1",name,"1");//�ƶ�
				}else if(HttpFillOP.FLOW15.equals(interId)){//������ͨ����
					map=BeanFlow.Lianlian_flow(orderid, productId, phone,
							Integer.parseInt(realPrice),HttpFillOP.ZDY, userno, tp.getUser_login(),tp.getSa_zone(),
							areaId, tp.getUser_site()+"", 
							tp.getUserPt(),tp.getFlowgroups(),interId,"1",name,"2");//��ͨ
				}else if(HttpFillOP.FLOW13.equals(interId)){//������������
					map=BeanFlow.Lianlian_flow(orderid, productId, phone,
							Integer.parseInt(realPrice), HttpFillOP.ZDY, userno, tp.getUser_login(),tp.getSa_zone(),
							areaId, tp.getUser_site()+"",
							tp.getUserPt(),tp.getFlowgroups(),interId,"1",name,"0");//����
				}else if(HttpFillOP.flow16.equals(interId)){//�����ƶ�����
					map=BeanFlow.zb_flow(orderid, productId, phone,
							Integer.parseInt(realPrice),HttpFillOP.ZDY, userno, tp.getUser_login(),tp.getSa_zone(),
							areaId, tp.getUser_site()+"", 
							tp.getUserPt(),tp.getFlowgroups(),interId,"1",name);
				}else if(HttpFillOP.flow17.equals(interId)){//�����ƶ�����
					map=BeanFlow.zy_flow(orderid, productId, phone,
							Integer.parseInt(realPrice),HttpFillOP.ZDY, userno, tp.getUser_login(),tp.getSa_zone(),
							areaId, tp.getUser_site()+"", 
							tp.getUserPt(),tp.getFlowgroups(),interId,"1",name);
				}else if(HttpFillOP.flow18.equals(interId)){//������ ��ͨ
					map=BeanFlow.LiuLiangFan(orderid, productId, phone,
							Integer.parseInt(realPrice),HttpFillOP.ZDY, userno, tp.getUser_login(),tp.getSa_zone(),
							areaId, tp.getUser_site()+"", 
							tp.getUserPt(),tp.getFlowgroups(),interId,"1",name);
				}else{
					rechargeStatus="1002";//ʧ��
					Log.info("�ص��ɹ�,֧���ɹ�,,,������:"+order_no+",,������ֵҵ��,����δ���ýӿ�,��ֵʧ��,");
					db.update("UPDATE "+tableName+" SET rechargeStatus="+rechargeStatus+" WHERE orderNum='"+order_no.trim()+"'");
					return ;
				}
				if(null!=map){
					String code =map.get("code")+"".trim();
					if("0".equals(code)){
						rechargeStatus="1000";//�ɹ�
					}else if("-5".equals(code) ||"1".equals(code) ||"-1".equals(code) ||"8".equals(code) ||"6".equals(code)){
						rechargeStatus="1002";//ʧ��
					}else{
						rechargeStatus="1003";//������
					}
				}else{
					rechargeStatus="1003";//������
				}
				db.update("UPDATE "+tableName+" SET rechargeStatus="+rechargeStatus+" WHERE orderNum='"+order_no.trim()+"'");
				Log.info("�ص��ɹ�,֧���ɹ�,,,������:"+order_no+",,������ֵҵ��,������ֵ�������,");
				
			}else{
				Log.info("�ص��ɹ�,֧���ɹ�,,,������:"+order_no+",,����ϵͳ����,δ֪ҵ������,reutrn null");
				return ;
			}
		} catch (Exception e) {
			Log.error("�ص��ɹ�,֧���ɹ�,����ҵ������,,,������:"+order_no+",,ϵͳ�쳣,ex:"+e);
		}finally{
			if(null!=db){
				db.close();
				db=null;
			}
		}
		
	}
	public static String getRandomDigit() {
		Random r = new Random();
		int n = r.nextInt(100);
		String m;
		if (n < 70) {
			m = "0";
			return m;
		} else if (n < 90) {
			m = "-1";
			return m;
		}else {
			m = "-2";
			return m;
		}
		
	}
}
