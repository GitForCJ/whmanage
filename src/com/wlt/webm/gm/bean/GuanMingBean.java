package com.wlt.webm.gm.bean;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.PostMethod;
import org.dom4j.DocumentException;

import com.commsoft.epay.util.logging.Log;
import com.wlt.webm.business.bean.BiProd;
import com.wlt.webm.db.DBService;
import com.wlt.webm.gm.form.Game;
import com.wlt.webm.gm.form.GameArea;
import com.wlt.webm.gm.form.GameInterface;
import com.wlt.webm.gm.util.XmlService;
import com.wlt.webm.ten.bean.WhQb;
import com.wlt.webm.ten.service.TenpayXmlPath;
import com.wlt.webm.tool.FloatArith;
import com.wlt.webm.tool.Tools;
import com.wlt.webm.uictool.MD5;
import com.wlt.webm.util.TenpayUtil;

/**
 * 
 * @ClassName: GuanMingBean
 * @Description: ������֧������ֵ�ӿڣ�
 * @author tanwanlong
 * @date 2014-6-5 ����02:04:15
 */
public class GuanMingBean {
//	 ����
//	private final String url = "http://apitest.gm193.com/post/";
//	private final String username = "gm193test";
//	private final String APIkey = "gm193test0123456";

	// ��ʽ
	 private final String url="http://api.gm193.com/post/";
	 private final String username = "szswhkj310";
	 private final String APIkey = "c0k2sk3lagdjk3d7";

	/**
	 * ��ѯ��Ϸ�ӿ��б�
	 * 
	 * @return
	 * @throws IOException
	 * @throws HttpException
	 * @throws DocumentException
	 */
	public List<GameInterface> queryGameInterface() throws HttpException,
			IOException, DocumentException {
		List<GameInterface> list = null;
		HttpClient client = new HttpClient();
		String urlto = url + "gameapi.asp?username=" + username + "&sign="
				+ MD5.encode("username=" + username + "||" + APIkey);
		PostMethod post = new PostMethod(urlto);
		Log.info("��ѯ������Ϸ�ӿ�url:" + urlto);
		int status = client.executeMethod(post);
		if (status == 200) {
			String result = new String((post.getResponseBodyAsString())
					.getBytes("ISO-8859-1"), "GB2312");
//			Log.info("��ѯ������Ϸ�ӿڷ�������:" + result);
			XmlService xml = new XmlService();
			list = xml.getGameInterfaceList(result.trim());
		}
		return list;
	}
	
	/**
	 * Q�ҷַ����ýӿڷ���
	 * @param map
	 * @param userno
	 * @param username
	 * @param parentid
	 * @param sa_zone
	 * @param order
	 * @param ismore
	 * @return int 
	 */
	public int qbInterfaceDistribution(Map<String, String> map,
			String userno, String username, String parentid, String sa_zone,String order,String ismore){
		if(!"0005".equals(map.get("gameapi"))){//����Q��ҵ��
			return interFaceServiceGameOrder(map,userno,username,parentid,sa_zone,order,ismore);
		}
		if("0".equals(ismore)){//��ͨ��
			return interFaceServiceGameOrder(map,userno,username,parentid,sa_zone,order,ismore);
		}
		boolean bool=WhQb.IsMultiChannel();
		if(!bool){//��ͨ���ܿ��أ�δ����
			return interFaceServiceGameOrder(map,userno,username,parentid,sa_zone,order,ismore);
		}
		if("1".equals(map.get("caidan"))){//����
			return interFaceServiceGameOrder(map,userno,username,parentid,sa_zone,order,ismore);
		}
		int num=-1;
		try{
			num=Integer.parseInt(map.get("num"));//��ֵ����
		}catch(Exception e){
			return 1;
		}
		if(num==1 || num==5 || num==10 || num==20 || num==30){//��ǰ���ò�
			return interFaceServiceGameOrder(map,userno,username,parentid,sa_zone,order,ismore);
		}
		//���ò�ҵ������
		return qbOrdersChaiDan(map,userno,username,parentid,sa_zone,order,ismore);
	}
	
	/**qbOrdersChaiDan
	 * �𵥷���
	 * Q�Һ�֧������ֵ�ڲ�ҵ����--�ӿ���ʹ�� 
	 * @param map 
	 * @param userno 
	 * @param username 
	 * @param parentid 
	 * @param sa_zone 
	 * @param order 
	 * @param ismore 
	 * @return 0 �ɹ� 1 ʧ�� 2�����л��쳣 3�ظ����� 4�˺����� 5Ӷ�𲻴���
	 */
	public int qbOrdersChaiDan(Map<String, String> map,
			String userno, String username, String parentid, String sa_zone,String order,String ismore) {
		String num = map.get("num");// ��ֵ����
		String in_acct = map.get("in_acct");// ��ֵ�˺�
		String gameapi = map.get("gameapi");// ��ֵ������Ϸ���
		String ip = map.get("ip");// ip��ַ
		String flag = map.get("flag");// �Ƿ�ֱӪ1ΪֱӪ��0Ϊ��ֱӪ
		String sql0 = "select fundacct from wht_facct where user_no='" + userno + "'";
		DBService dbService = null;
		// ��ǰʱ�� yyyyMMddHHmmss
		TenpayXmlPath tenpayxmlpath = new TenpayXmlPath();
		String currTime = TenpayUtil.getCurrTime();
		String transaction_id = order;//TenpayUtil.getQBChars(currTime);
		String serviceCode = "0005";// �ڲ���Ϸ���
		String serviceTitle = "Q��";
		String code = "6";
		String phone_type="3";
		String gxid=com.wlt.webm.util.Tools.getGuangXinSerialNo();
		int n = 0;
		try {
			dbService = new DBService();
			if (dbService.hasData("select tradeserial from wht_orderform_" + currTime.substring(2, 6) + " where tradeserial='" + transaction_id + "'")) {
				return 3;
			}
			String facct = dbService.getString(sql0);
			String sql = "select accountleft from  wht_childfacct where  childfacct ='" + facct + "01'";
			long money = dbService.getLong(sql);
			int rebate = 1000;// ����
			if (money - Integer.parseInt(num) * 1000 <= 0) {
				return 4;
			}
			BiProd bp = new BiProd();
			String[] employFee = null;
			//Q�ң�ֱӪ�� �Զ���Ӷ��
			if("1".equals(flag)){
				if(employFee==null){
					employFee=new String[1];
				}
				employFee[0]=map.get("qbCommission");
			}else{
				employFee = bp.getZZEmploy(dbService, gameapi, flag,
						userno, Integer.parseInt(parentid));
			}
			if (null == employFee) {
				return 5;
			}
			int facctfee = Integer.parseInt(num) * rebate;
			int parentfee = 0;
			String acct1 = Tools.getAccountSerial(currTime, userno);
			String acct2 = Tools.getAccountSerial(currTime, userno);
			String parentFacct = "";
			// ����Ӷ��
			if ("1".equals(flag)) {
				facctfee = (int) FloatArith.mul(facctfee, 1 - FloatArith
						.div(Double.valueOf(employFee[0]), 100));
			} else {
				double a = FloatArith
						.div(Double.valueOf(employFee[0]), 100);// ��Ӷ���
				double b = FloatArith.mul(facctfee, a);// ��Ӷ��

				double c = FloatArith.mul(b, FloatArith.div(Double
						.valueOf(employFee[1]), 100));// �����Ӧ��Ӷ��

				facctfee = (int) FloatArith.sub(facctfee, c);// Ӧ�۽��
				parentfee = (int) FloatArith.sub(b, c);// �ϼ��ڵ�Ӧ��Ӷ��
			}
			Object[] orderObj = { null, sa_zone, transaction_id, in_acct,
					"16", serviceCode, Integer.parseInt(num) * rebate,
					facctfee, facct + "01", currTime, currTime, 4, gxid,
					transaction_id + "#" + Integer.parseInt(num) * rebate,
					map.get("term_type"), userno, username, phone_type, 381, null };
			Object[] acctObj = { null, facct + "01", acct1, in_acct,
					currTime, Integer.parseInt(num) * rebate, facctfee,
					code, serviceTitle, 0, currTime, money - facctfee,
					transaction_id, facct + "01", 1 };
			boolean rsFlag = false;
			if ("1".equals(flag)) {
				rsFlag = bp.innerDeal(dbService, orderObj, acctObj, null,
						flag, facct, null, currTime, facctfee, 0);
			} else {
				parentFacct = bp.getFacctByUserID(dbService, Integer
						.parseInt(parentid));
				String sqlq = "select accountleft from  wht_childfacct where  childfacct ='"
						+ parentFacct + "02'";
				int moneyq = dbService.getInt(sqlq);
				Object[] acctObj1 = { null, parentFacct + "02", acct2,
						in_acct, currTime, parentfee, parentfee, 15,
						"�¼����׷�Ӷ", 0, currTime, moneyq + parentfee,
						transaction_id, parentFacct + "02", 2 };
				rsFlag = bp.innerDeal(dbService, orderObj, acctObj,
						acctObj1, flag, facct, parentFacct, currTime,
						facctfee, parentfee);
			}
			if (rsFlag == false) {
				return 1;
			}
			//�� ���
			int[] strs=com.wlt.webm.util.Tools.decomposeQB1(Integer.parseInt(num));
			String tableName="wht_qbbreakrecord_"+Tools.getNow3().substring(2,6);
			String orderTable = "wht_orderform_" + currTime.substring(2, 6);
			String acctTable = "wht_acctbill_" + currTime.substring(2, 6);
			
			int oneState=-1;
			int con=0;
			for(int i=0;i<2;i++){
				if(strs[i]==1 || strs[i]==5 || strs[i]==10 || strs[i]==20 || strs[i]==30){
					con++;
					//������transaction_id  ��Ӧ������wht_qbbreakrecord_1503 �����ֶ�oldorderid  
					String order_One=com.wlt.webm.util.Tools.getGuangXinSerialNo();
					//��Ϣ��ˮ��
					String transaction_id_One=TenpayUtil.getQBChars(currTime);
					Object[] qbbreakrecordOne={null,order_One,in_acct,16,"0005",strs[i] * rebate,
							strs[i] * rebate,currTime,Tools.getNow3(),4,order_One+"#"+strs[i] * rebate,userno,transaction_id,"","","",""};
					dbService.logData(17, qbbreakrecordOne, tableName);
					oneState=WhQb.useQB(transaction_id_One, order_One, in_acct, Integer.parseInt(strs[i]+"") * rebate);
					if(i==0){//��һ�ʵ� ����
						if(oneState==0){//�𵥺�ĵ�һ���߹��ţ��ɹ�
							dbService.update("update wht_qbbreakrecord_" + currTime.substring(2, 6)+
									" set chgtime='"+Tools.getNow3()+"',state=0 where oldorderid='"+transaction_id+"' and tradeserial='"+
									order_One+"' and userno='"+userno+"'");
							continue;
						}else if(oneState==-1){//ʧ��
							dbService.update("delete from wht_qbbreakrecord_" + currTime.substring(2, 6)+
									"  where oldorderid='"+transaction_id+"' and tradeserial='"+
									order_One+"' and userno='"+userno+"'");
							break;
						}else{//δ֪״̬,�쳣��������,ת�˹�
							dbService.update("update wht_qbbreakrecord_" + currTime.substring(2, 6)+
									" set chgtime='"+Tools.getNow3()+"',state=6 where oldorderid='"+transaction_id+"' and tradeserial='"+
									order_One+"' and userno='"+userno+"'");
							num=strs[1]+"";
							break;
						}
					}else{//�ڶ��ʵ� ���� ��һ�ʹ��ų�ֵ�ɹ���
						if(oneState==0){//�𵥺�����ʶ��ߣ����ţ��������ʶ��ɹ�,����
							dbService.update("update wht_qbbreakrecord_" + currTime.substring(2, 6)+
									" set chgtime='"+Tools.getNow3()+"',state=0 where oldorderid='"+transaction_id+"'");
							dbService.update("update wht_orderform_" + currTime.substring(2, 6)+
									" set state=0,writecheck='"+transaction_id+ "#" + Integer.parseInt(num) * rebate + "#"+ 
									transaction_id+"' where tradeserial='"+transaction_id+"' and userno='"+userno+"'");
							return 0;
						}else if(oneState==-1){// �ڶ��ʳ�ֵʧ�ܣ��ڶ���Ǯ �߹���
							dbService.update("delete from  wht_qbbreakrecord_" + currTime.substring(2, 6)+
									"  where oldorderid='"+transaction_id+"' and tradeserial='"+
									order_One+"' and userno='"+userno+"'");
							num=strs[1]+"";
							break;
						}else{//δ֪״̬,�쳣��������,ת�˹�
							dbService.update("update wht_qbbreakrecord_" + currTime.substring(2, 6)+
									" set chgtime='"+Tools.getNow3()+"',state=6 where oldorderid='"+transaction_id+"' and tradeserial='"+
									order_One+"' and userno='"+userno+"'");
							return 0;
						}
					}
				}else{//�ڶ��ʵ� ����
					num=strs[1]+"";
					break;
				}
			}
			
			dbService.update("update "+orderTable+" set buyid='10' where tradeserial='"+transaction_id+"'");
			
			Object[] qbbreakrecordOne={null,transaction_id,in_acct,10,"0005",Integer.parseInt(num) * rebate,
					Integer.parseInt(num) * rebate,currTime,"",4,transaction_id+"#"+Integer.parseInt(num) * rebate,userno,transaction_id,"","","",""};
			dbService.logData(17, qbbreakrecordOne, tableName);
			
			Map maps = new HashMap<String, String>();
			maps.put("gameapi", "qqes");
			maps.put("account", in_acct);
			maps.put("buynum", num);
			maps.put("sporderid", transaction_id);
			maps.put("gamecode", null);
			maps.put("gamearea", null);
			maps.put("gameserver", null);
			maps.put("gamerole", null);
			maps.put("clientip", ip);
			String returl="http://www.wanhuipay.com/business/bank.do?method=GuanMingCallBack";
			maps.put("returl", returl);
			String state =fillInterFaceGameOrder(maps) + "";
			//����
			dbService.update("update wht_orderform_" + currTime.substring(2, 6)+" set buyid=10 where tradeserial='"+
					transaction_id+"'");
			Log.info("���������� ���νӿڳ�ֵ���ؽ��:" + state);
			String isState=dbService.getString("select state from "+orderTable+" where tradeserial='" + transaction_id +"' and (state=0 or state=1)");
			if(isState!=null && !"".equals(isState)){
				Log.info("�����ţ�"+transaction_id+",�ö����Ѿ������Ϊ�ս�״̬,�������ٴ��޸�1,state="+isState+",,,select state from "+orderTable+" where tradeserial='" + transaction_id +"' and (state=0 or state=1)");
				return Integer.parseInt(isState);
			}else{
				Log.info("�����ţ�"+transaction_id+",�ö���״̬�ɱ�,�޸�1,state="+isState+",,,select state from "+orderTable+" where tradeserial='" + transaction_id +"' and (state=0 or state=1)");
			}
			if (state.equals("0")) {
//				dbService.update("update wht_qbbreakrecord_" + currTime.substring(2, 6)+
//						" set chgtime='"+Tools.getNow3()+"',state=0 where oldorderid='"+transaction_id+"' and userno='"+userno+"' and tradeserial='"+transaction_id+"'");
//				dbService.update("update wht_orderform_" + currTime.substring(2, 6)+
//						" set state=4,writecheck='"+transaction_id+ "#" + Integer.parseInt(num) * rebate + "#"+ 
//						transaction_id+"' where tradeserial='"+transaction_id+"' and userno='"+userno+"'");
				return 0;
			} else if (state.equals("-1")) {
//				String ss=dbService.getString("select state from "+orderTable+" where tradeserial='" + transaction_id +"' and (state=0 or state=1)");
//				if(ss!=null && !"".equals(ss)){
//					Log.info("�����ţ�"+transaction_id+",���˹���,������");
//					return 1;
//				}
//				
//				dbService.update("update wht_qbbreakrecord_" + currTime.substring(2, 6)+
//						" set chgtime='"+Tools.getNow3()+"',state=1 where oldorderid='"+transaction_id+"' and userno='"+userno+"' and tradeserial='"+transaction_id+"' ");
//				if ("1".equals(flag)) {
//					bp.innerFailDeal(dbService, orderTable, acctTable,
//						transaction_id + "#" + Integer.parseInt(num)
//								* rebate + "#" + transaction_id,
//						transaction_id, userno, acct1, "", facctfee, 0,
//						facct, "", "1");
//				} else {
//					bp.innerFailDeal(dbService, orderTable, acctTable,
//						transaction_id + "#" + Integer.parseInt(num)
//								* rebate + "#" + transaction_id,
//						transaction_id, userno, acct1, acct2, facctfee,
//						parentfee, facct, parentFacct, "0");
//				}
				return 1;
			} else {
//				bp.innerTimeOutDeal(dbService, orderTable, transaction_id,userno);
				return 2;
			}
		} catch (Exception e) {
			Log.error("������ֵ�쳣:" + e.getMessage());
			n = 2;
			Log.error(e.getMessage());
		}finally{
			if(dbService!=null){
				dbService.close();
				dbService=null;
			}
		}
		return n;
	}
	/**
	 * 
	 * Q�Һ�֧������ֵ�ڲ�ҵ����--�ӿ���ʹ��
	 * 
	 * @return 0 �ɹ� 1 ʧ�� 2�����л��쳣 3�ظ����� 4�˺����� 5Ӷ�𲻴���
	 */
	public int interFaceServiceGameOrder(Map<String, String> map,
			String userno, String username, String parentid, String sa_zone,String order,String ismore) {
		String num = map.get("num");// ��ֵ����
		String in_acct = map.get("in_acct");// ��ֵ�˺�
		String gameapi = map.get("gameapi");// ��ֵ������Ϸ���
		String ip = map.get("ip");// ip��ַ
		String flag = map.get("flag");// �Ƿ�ֱӪ1ΪֱӪ��0Ϊ��ֱӪ
		String sql0 = "select fundacct from wht_facct where user_no='" + userno
				+ "'";
		boolean qbFlag=false;
		String interfaceid="2";
		DBService dbService = null;
		// ��ǰʱ�� yyyyMMddHHmmss
		TenpayXmlPath tenpayxmlpath = new TenpayXmlPath();
		String currTime = TenpayUtil.getCurrTime();
		String transaction_id = order;//TenpayUtil.getQBChars(currTime);
		String account = "";
		String serviceCode = "";// �ڲ���Ϸ���
		String serviceTitle = "";
		String code = "";
		String phone_type="";
		String gxid=com.wlt.webm.util.Tools.getGuangXinSerialNo();
		int n = 0;
		if (gameapi.equals("0005")) {
			serviceCode = "0005";
			serviceTitle = "Q��";
			code = "6";
			phone_type="3";
		} else {
			serviceCode = gameapi;
			serviceTitle = "֧����";
			code = "23";
			phone_type="5";
		}
		Log.info("������ֵ����:" + serviceTitle);
		try {
			dbService = new DBService();
			if (dbService.hasData("select tradeserial from wht_orderform_"
					+ currTime.substring(2, 6) + " where tradeserial='"
					+ transaction_id + "'")) {
				return 3;
			}
			String facct = dbService.getString(sql0);
			String sql = "select accountleft from  wht_childfacct where  childfacct ='"
					+ facct + "01'";
			long money = dbService.getLong(sql);
			int rebate = 1000;// ����
			if (money - Integer.parseInt(num) * 1000 <= 0) {
				return 4;
			}
			BiProd bp = new BiProd();
			String[] employFee = null;
			//Q�ң�ֱӪ�� �Զ���Ӷ��
			if(gameapi.equals("0005") && "1".equals(flag)){
				if(employFee==null){
					employFee=new String[1];
				}
				employFee[0]=map.get("qbCommission");
			}else{
				employFee = bp.getZZEmploy(dbService, gameapi, flag,
						userno, Integer.parseInt(parentid));
			}
			if (null == employFee&&gameapi.equals("0005")) {
				return 5;
			}
			int facctfee = Integer.parseInt(num) * rebate;
			int parentfee = 0;
			String acct1 = Tools.getAccountSerial(currTime, userno);
			String acct2 = Tools.getAccountSerial(currTime, userno);
			String parentFacct = "";
			if (serviceCode.equals("0005")) { //Q��
				// ����Ӷ��
				if ("1".equals(flag)) {
					facctfee = (int) FloatArith.mul(facctfee, 1 - FloatArith
							.div(Double.valueOf(employFee[0]), 100));
				} else {
					double a = FloatArith
							.div(Double.valueOf(employFee[0]), 100);// ��Ӷ���
					double b = FloatArith.mul(facctfee, a);// ��Ӷ��

					double c = FloatArith.mul(b, FloatArith.div(Double
							.valueOf(employFee[1]), 100));// �����Ӧ��Ӷ��

					facctfee = (int) FloatArith.sub(facctfee, c);// Ӧ�۽��
					parentfee = (int) FloatArith.sub(b, c);// �ϼ��ڵ�Ӧ��Ӷ��
				}
			}else{//֧����
				if ("1".equals(flag)) {// ֱӪ
					BigDecimal bd1 = new BigDecimal(num);
					BigDecimal bd2 = new BigDecimal(0.01);
					facctfee = (int) ((bd1.multiply(bd2).add(bd1)).doubleValue() * 1000);
				} else {
					BigDecimal bd1 = new BigDecimal(num);
					BigDecimal bd2 = new BigDecimal(0.01);
					BigDecimal bd3 = new BigDecimal(0.002);
					facctfee = (int) ((bd1.multiply(bd2).add(bd1)).doubleValue() * 1000);
					parentfee = (int) ((bd1.multiply(bd3)).doubleValue() * 1000);
				}
			
			}
			qbFlag=WhQb.isUse(dbService, Integer.parseInt(num))&&ismore.equals("1");
			int interfaceid0 = 10;
			if(qbFlag){
				interfaceid0=16;
			}
			Object[] orderObj = { null, sa_zone, transaction_id, in_acct,
					interfaceid0+"", serviceCode, Integer.parseInt(num) * rebate,
					facctfee, facct + "01", currTime, currTime, 4, gxid,
					transaction_id + "#" + Integer.parseInt(num) * rebate,
					map.get("term_type"), userno, username, phone_type, 381, null };
		
			Object[] acctObj = { null, facct + "01", acct1, in_acct,
					currTime, Integer.parseInt(num) * rebate, facctfee,
					code, serviceTitle, 0, currTime, money - facctfee,
					transaction_id, facct + "01", 1 };
			boolean rsFlag = false;
			if ("1".equals(flag)) {
				rsFlag = bp.innerDeal(dbService, orderObj, acctObj, null,
						flag, facct, null, currTime, facctfee, 0);
			} else {
				parentFacct = bp.getFacctByUserID(dbService, Integer
						.parseInt(parentid));
				String sqlq = "select accountleft from  wht_childfacct where  childfacct ='"
						+ parentFacct + "02'";
				int moneyq = dbService.getInt(sqlq);
				Object[] acctObj1 = { null, parentFacct + "02", acct2,
						in_acct, currTime, parentfee, parentfee, 15,
						"�¼����׷�Ӷ", 0, currTime, moneyq + parentfee,
						transaction_id, parentFacct + "02", 2 };
				rsFlag = bp.innerDeal(dbService, orderObj, acctObj,
						acctObj1, flag, facct, parentFacct, currTime,
						facctfee, parentfee);
			}
			if (rsFlag == false) {
				return 1;
			}
			Map maps = new HashMap<String, String>();
			if (gameapi.equals("0005")) {
				gameapi="qqes";
			}else{
				gameapi="epayeszfb";
			}
			maps.put("gameapi", gameapi);
			maps.put("account", in_acct);
			maps.put("buynum", num);
			maps.put("sporderid", transaction_id);
			maps.put("gamecode", null);
			maps.put("gamearea", null);
			maps.put("gameserver", null);
			maps.put("gamerole", null);
			maps.put("clientip", ip);
			String returl="http://www.wanhuipay.com/business/bank.do?method=GuanMingCallBack";
//			String returl = "http://1818y.wicp.net:8090/wh/business/bank.do?method=GuanMingCallBack";
			maps.put("returl", returl);
			String state ="";
			if(gameapi.equals("qqes")){
				if(qbFlag){
					interfaceid="1";
					int k=WhQb.useQB(transaction_id, gxid, in_acct, Integer.parseInt(num) * 1000);
					if(k==0){
						state="0";
					}else if(k==-1){
						interfaceid="2";
						state = fillInterFaceGameOrder(maps) + "";
					}else{
						state="-2";
					}
				}else{
					state = fillInterFaceGameOrder(maps) + "";
				}
			}else{
				state = fillInterFaceGameOrder(maps) + "";
			}
			if(qbFlag&&interfaceid.equals("2")){
				dbService.update("update wht_orderform_" + currTime.substring(2, 6)+" set buyid=10 where tradeserial='"+
						transaction_id+"'");
			}
			Log.info("���������� ���νӿڳ�ֵ���ؽ��:" + state);
			String orderTable = "wht_orderform_" + currTime.substring(2, 6);
			String acctTable = "wht_acctbill_" + currTime.substring(2, 6);
			String isState=dbService.getString("select state from "+orderTable+" where tradeserial='" + transaction_id +"' and (state=0 or state=1)");
			if(isState!=null && !"".equals(isState)){
				Log.info("�����ţ�"+transaction_id+",�ö����Ѿ������Ϊ�ս�״̬,�������ٴ��޸�2,state="+isState+",,,select state from "+orderTable+" where tradeserial='" + transaction_id +"' and (state=0 or state=1)");
				return Integer.parseInt(isState);
			}else{
				Log.info("�����ţ�"+transaction_id+",�ö���״̬�ɱ�,�޸�2,state="+isState+",,,select state from "+orderTable+" where tradeserial='" + transaction_id +"' and (state=0 or state=1)");
			}
			if("2".equals(interfaceid)){ //�����ύ�����������ݽ���޸�״̬
				if("0".equals(state)){
					return 0;
				}else if("-1".endsWith(state)){
					return 1;
				}else{
					return 2;
				}
			}
			if (state.equals("0")) {
				bp.innerQQSuccessDeal(dbService, orderTable, transaction_id
						+ "#" + Integer.parseInt(num) * rebate + "#"
						+ transaction_id, transaction_id, userno,interfaceid);
				return 0;
			} else if (state.equals("-1")) {
				if ("1".equals(flag)) {
					bp.innerFailDeal(dbService, orderTable, acctTable,
							transaction_id + "#" + Integer.parseInt(num)
									* rebate + "#" + transaction_id,
							transaction_id, userno, acct1, "", facctfee, 0,
							facct, "", "1");
				} else {
					bp.innerFailDeal(dbService, orderTable, acctTable,
							transaction_id + "#" + Integer.parseInt(num)
									* rebate + "#" + transaction_id,
							transaction_id, userno, acct1, acct2, facctfee,
							parentfee, facct, parentFacct, "0");
				}
				return 1;
			} else {
				bp.innerTimeOutDeal(dbService, orderTable, transaction_id,
						userno);
				return 2;
			}
		} catch (Exception e) {
			Log.error("������ֵ�쳣:" + e.getMessage());
			n = 2;
			Log.error(e.getMessage());
		}finally{
			if (null != dbService) {
				dbService.close();
				dbService=null;
			}
		}
		return n;
	}

	/**
	 * ��Ϸֱ��ӿ�-�ӿ���ʹ��
	 * 
	 * @param map
	 *            �������ϲ���
	 * @return
	 */
	public int fillInterFaceGameOrder(Map<String, String> map) {
		HttpClient client = new HttpClient();
		String gameapi = map.get("gameapi"); // �ӿڱ���
		String account = map.get("account");// ��ֵ�˺�
		String buynum = map.get("buynum"); // ��ֵ����
		String sporderid = map.get("sporderid"); // �ڲ�������
		String gamecode = map.get("gamecode"); // ��ֵ����,����Ϸ�����,û���򲻴�,��������д
		String gamearea = map.get("gamearea");// ��Ϸ��������,û���򲻴�,��������д
		String gameserver = map.get("gameserver");// ��Ϸ���ڷ�������,û���򲻴�,��������д
		String gamerole = map.get("gamerole"); // ��Ϸ��ɫ,û���򲻴�,��������д
		String clientip = map.get("clientip"); // �������IP
		String returl = map.get("returl"); // �ص���ַ,������ֵ�ɹ��󷵻ص�URL��ַ
		Map<String, String> map2 = null;
		int flag = 0;
		try {
			String urlto = url
					+ "gaorder.asp?username="
					+ username
					+ "&gameapi="
					+ gameapi
					+ "&account="
					+ account
					+ "&buynum="
					+ buynum
					+ "&sporderid="
					+ sporderid
					+ "&gamecode="
					+ gamecode
					+ "&gamearea="
					+ gamearea
					+ "&gameserver="
					+ gameserver
					+ "&gamerole="
					+ gamerole
					+ "&clientip="
					+ clientip
					+ "&returl="
					+ returl
					+ "&sign="
					+ MD5.encode("username=" + username + "&gameapi=" + gameapi
							+ "&sporderid=" + sporderid + "||" + APIkey);
			PostMethod post = new PostMethod(urlto);
			Log.info("������Ϸ��ֵ�ӿ�url:" + urlto);
			int status = client.executeMethod(post);
			if (status == 200) {
				String result = new String((post.getResponseBodyAsString())
						.getBytes("ISO-8859-1"), "GB2312");
				Log.info("��ֵ������Ϸ�ӿڷ�������:" + result);
				XmlService xml = new XmlService();
				map2 = xml.fillGameOrder(result.trim());
				int n = 0;
				String state = map2.get("state");
				if (null != state && state.equals("0")) {
					while (n <= 18) {
						n++;
						int k = GuanMingQuey(gameapi, sporderid);
						if (k == 0) {
							flag = 0;// �ɹ�
							break;
						} else if (k == 1) {
							flag = -1; // ʧ��
							break;
						} else if (k == 4) {
							flag = -2;// ������
							Thread.sleep(10000);
							continue;
						}
					}
				} else {
					flag = -1;
				}
			}
		} catch (Exception e) {
			flag = -4;// ��������
			Log.info("��ֵ������Ϸ�ӿ�ʧ��:" + e.getMessage());
		}
		return flag;
	}

	/**
	 * ��Ϸֱ��ӿ�-�ڲ�ʹ��
	 * 
	 * @param map
	 *            �������ϲ���
	 * @return
	 */
	public Map fillGameOrder(Map<String, String> map) {
		HttpClient client = new HttpClient();
		String gameapi = map.get("gameapi");
		String account = map.get("account");
		String buynum = map.get("buynum");
		String sporderid = map.get("sporderid");
		String gamecode = map.get("gamecode");
		String gamearea = map.get("gamearea");
		String gameserver = map.get("gameserver");
		String gamerole = map.get("gamerole");
		String clientip = map.get("clientip");
		String returl = map.get("returl");
		Map<String, String> map2 = null;
		try {
			String urlto = url
					+ "gaorder.asp?username="
					+ username
					+ "&gameapi="
					+ gameapi
					+ "&account="
					+ account
					+ "&buynum="
					+ buynum
					+ "&sporderid="
					+ sporderid
					+ "&gamecode="
					+ gamecode
					+ "&gamearea="
					+ gamearea
					+ "&gameserver="
					+ gameserver
					+ "&gamerole="
					+ gamerole
					+ "&clientip="
					+ clientip
					+ "&returl="
					+ returl
					+ "&sign="
					+ MD5.encode("username=" + username + "&gameapi=" + gameapi
							+ "&sporderid=" + sporderid + "||" + APIkey);
			PostMethod post = new PostMethod(urlto);
			Log.info("������Ϸ��ֵ�ӿ�url:" + urlto);
			int status = client.executeMethod(post);
			if (status == 200) {
				String result = new String((post.getResponseBodyAsString())
						.getBytes("ISO-8859-1"), "GB2312");
				Log.info("��ֵ������Ϸ�ӿڷ�������:" + result);
				XmlService xml = new XmlService();
				map2 = xml.fillGameOrder(result.trim());
			}
		} catch (HttpException e) {
			Log.info("��ֵ������Ϸ�ӿ�ʧ��:" + e.getMessage());
		} catch (IOException e) {
			Log.info("��ֵ������Ϸ�ӿ�ʧ��:" + e.getMessage());
		} catch (DocumentException e) {
			Log.info("��ֵ������Ϸ�ӿ�ʧ��:" + e.getMessage());
		}
		return map2;
	}

	/**
	 * ����������ѯ
	 * 
	 * @param gameapi
	 *            �ӿ��̱��
	 * @param sporderid
	 *            �������
	 * @return
	 * @throws IOException
	 * @throws HttpException
	 * @throws HttpException
	 * @throws IOException
	 * @throws DocumentException
	 */
	public int GuanMingQuey(String gameapi, String sporderid)
			throws HttpException, IOException, DocumentException {
		HttpClient client = new HttpClient();
		int flag = 0;
		String urlto = url
				+ "gasearch.asp?username="
				+ username
				+ "&gameapi="
				+ gameapi
				+ "&sporderid="
				+ sporderid
				+ "&sign="
				+ MD5.encode("username=" + username + "&gameapi=" + gameapi
						+ "&sporderid=" + sporderid + "||" + APIkey);
		PostMethod post = new PostMethod(urlto);
		Log.info("������Ϸ��ѯ�ӿ�url:" + urlto);
		int status = client.executeMethod(post);
		if (status == 200) {
			String result = new String((post.getResponseBodyAsString())
					.getBytes("ISO-8859-1"), "GB2312");
			Log.info("��ֵ������Ϸ��ѯ�ӿڷ�������:" + result);
			XmlService xml = new XmlService();
			flag = xml.queryGameOrder(result.trim());
		}
		return flag;
	}

	/**
	 * ��ѯ��Ϸ��Ӧ�Ľӿ��б�
	 * 
	 * @param gameapi
	 * @throws IOException
	 * @throws HttpException
	 * @throws DocumentException
	 */
	public List queryGame(String gameapi) throws HttpException, IOException,
			DocumentException {
		List<Game> list = null;
		HttpClient client = new HttpClient();
		String urlto = url + "gamelist.asp?username=" + username + "&byuser=1&gameapi="
				+ gameapi + "&sign="
				+ MD5.encode("username=" + username + "||" + APIkey);
		PostMethod post = new PostMethod(urlto);
		Log.info("��ѯ����������Ϸ�б�ӿ�url:" + urlto);
		int status = client.executeMethod(post);
		if (status == 200) {
			String result = new String((post.getResponseBodyAsString())
					.getBytes("ISO-8859-1"), "GB2312");
//			Log.info("��ѯ������Ϸ�ӿڷ�������:" + result);
			XmlService xml = new XmlService();
			list = xml.getGameList(result);
		}
		return list;
	}

	/**
	 * ��ѯ��Ϸ���༰������ѯ�ӿ�
	 * 
	 * @param gameapi
	 * @throws IOException
	 * @throws HttpException
	 * @throws DocumentException
	 */
	public List queryGameArea(String gameapi, String gameCode)
			throws HttpException, IOException, DocumentException {
		List<GameArea> list = null;
		HttpClient client = new HttpClient();
		String urlto = url
				+ "gamearea.asp?username="
				+ username
				+ "&gameapi="
				+ gameapi
				+ "&gamecode="
				+ gameCode
				+ "&sign="
				+ MD5.encode("username=" + username + "&gameapi=" + gameapi
						+ "||" + APIkey);
		PostMethod post = new PostMethod(urlto);
		Log.info("��ѯ������Ϸ����ӿ�url:" + urlto);
		int status = client.executeMethod(post);
		if (status == 200) {
			String result = new String((post.getResponseBodyAsString())
					.getBytes("ISO-8859-1"), "GB2312");
//			Log.info("��ѯ������Ϸ�ӿڷ�������:" + result);
			XmlService xml = new XmlService();
			list = xml.getGameAreaList(result);
		}
		return list;
	}

	 /**
	 * ��ѯ��Ϸ����������������
	 * @param gameapi
	 * @throws IOException
	 * @throws HttpException
	 * @throws DocumentException
	 */
//	 public List queryGameAreas(String gameapi) throws HttpException,
//	 IOException, DocumentException {
//	 List<GameArea> list = null;
//	 HttpClient client = new HttpClient();
//	 String urlto = url + "gameinfo.asp?username=" + username +
//	 "&gameapi="+gameapi+"&sign="
//	 + MD5.encode("username=" + username + "&gameapi="+gameapi+"||" + APIkey);
//	 PostMethod post = new PostMethod(urlto);
//	 Log.info("��ѯ������Ϸ�ӿ�url:" + urlto);
//	 int status = client.executeMethod(post);
//	 if (status == 200) {
//	 String result = new
//	 String((post.getResponseBodyAsString()).getBytes("ISO-8859-1"),
//	 "GB2312");
//	 Log.info("��ѯ������Ϸ�ӿڷ�������:" + result);
	 //XmlService xml = new XmlService();
	 //list=xml.getGameAreaList(result);
//	 }
//	 return list;
//	 }
	public static void main(String args[]) {
		GuanMingBean gmb = new GuanMingBean();
		// gmb.fillGameOrder("epayeszfb", "tanlong4259@163.com", "100",
		// "201406061048293458", null, null, null, null, "127.0.0.1",
		// "http://shijianqiao321a.xicp.net:8888/wh/business/bank.do?method=GuanMingCallBack"
		// );
		try {
//			gmb.queryGameInterface();
//			 gmb.queryGame("qqes4x");
//			 gmb.queryGameAreas("qqes2dq");
			 List<GameArea> list=gmb.queryGameArea("qqes2dq","21");
			// for(GameArea ga:list){
			// System.out.println(ga.getName());
			// }
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// System.out.println(gmb.GuanMingQuey("epayeszfb",
		// "201406061048293458"));
		catch (DocumentException e) {
			e.printStackTrace();
		}
	}
}
