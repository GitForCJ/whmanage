package com.wlt.webm.pccommon;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import whmessgae.TradeMsg;

import com.commsoft.epay.util.logging.Log;
import com.wlt.webm.business.bean.AcctBillBean;
import com.wlt.webm.business.bean.BiProd;
import com.wlt.webm.db.DBService;
import com.wlt.webm.message.PortalSend;
import com.wlt.webm.tool.FloatArith;
import com.wlt.webm.tool.Tools;

/**
 * �򵥵�ѹ������
 * 
 * @author caiSJ
 */
public class PressureTest {

	/**
	 * ��ֵ
	 * 
	 * @param phone
	 *            �绰����
	 * @param payfee
	 *            ���Ԫ
	 * @param userPid
	 *            �û�����ʡ��id
	 * @param parentID
	 *            �û����ڵ�
	 * @param userno
	 *            �û�ϵͳ���
	 * @param login
	 *            �û���¼�˺�
	 * @param areaCode
	 *            �û�����
	 */
	public static void fill(String phone, String payfee, String userPid,
			String parentID, String userno, String login, String areaCode) {
		String seqNo = Tools.getStreamSerial(phone);
		Log.info("������:" + seqNo + "��ʼ��ֵ");
		if (!Tools.validateTime()) {
			System.out.println("����ʱ��β������� :" + seqNo);
		}
		String[] str = PressureTest.getPHpid(phone);
		if (str == null) {
			System.out.println("���� :" + phone + "  �޷���ѯ��������");
		}
		String phonePid = str[0];
		String phoneType = str[1];
		String flag = "0";
		double fee = FloatArith.yun2li(payfee);
		DBService db = null;
		try {
			db = new DBService();
			try {
				if (AcctBillBean.getStatus(parentID)) {// ֱӪ
					flag = "1";
				}
			} catch (Exception e) {
				System.out.println(seqNo + "  �ж��Ƿ�ֱӪ�쳣" + e.toString());
				e.printStackTrace();
			}
			BiProd bp = new BiProd();
			String[] result = bp.getEmploy(db, phoneType, userPid, phonePid,
					userno, flag, Integer.parseInt(payfee), Integer
							.parseInt(parentID),0);
			if (null == result) {
				Log.info(seqNo + " ��ֵʧ��,Ӷ����ϸ������");
			}
			String ip = "192.168.1.190";
			int k = bp.nationFill(parentID, userno, phonePid, userPid,
					phoneType, phone, seqNo, fee, result, areaCode, db, login,
					ip, null, flag);
			Log.info("������:" + seqNo + " ��ֵ���=" + k);
			if (k == 0) {
				Log.info(seqNo + "   ��ֵ�ɹ�");
			} else if (k > 20) {
				Log.info(seqNo + "  ��ֵ�ɹ�,�û��������:" + ((float) k)
						/ 1000);
			} else if (k == 1 || k == -1) {
				Log.info(seqNo + "  ��ֵʧ��");
			} else if (k == 2 || k == -2 || k == 3) {
				Log.info(seqNo + "  ���״�����,����ϵ�ͷ�");
			} else {
				Log.info(seqNo + "  �����쳣����ϵ�ͷ�");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			Log.error(seqNo + "  ϵͳ��æ���Ժ�����   " + e.toString());
		}finally{
			if(null!=db){
				db.close();
			}
		}

	}

	/**
	 * �ֻ�����
	 * 
	 * @param tradeObject
	 * @return
	 */
	public static String[] getPHpid(String tradeObject) {
		String phone = tradeObject.replaceAll(" ", "").substring(0, 7);
		BiProd biProd = new BiProd();
		String[] str = biProd.getPhoneInfo(phone);
		if (null != str && str.length == 3) {
			return str;
		} else {
			return null;
		}
	}

	/**
	 * ��ȡ�����
	 * 
	 * @return ���
	 */
	public static int getRandomDigit() {
		Random r = new Random();
		int n = r.nextInt(100);
		int m;
		if (n < 30) {
			m = 30;
		} else if (n < 50) {
			m = 50;
		} else if (n < 70) {
			m = 100;
		} else if (n < 90) {
			m = 200;
		} else {
			m = 300;
		}
		return m;
	}

	/**
	 * �����ȡ�Ŷ�
	 * 
	 * @return ���
	 */
	public static String getRandomNum() {
		Random r = new Random();
		int n = r.nextInt(100);
		String m;
		if (n < 10) {
			m = "18682033";
		} else if (n < 20) {
			m = "15626509";
		} else if (n < 30) {
			m = "15012880";
		} else if (n < 40) {
			m = "15889731";
		} else if (n < 50) {
			m = "18682022";
		} else if (n < 60) {
			m = "18926512";
		} else if (n < 70) {
			m = "13042801";
		} else if (n < 80) {
			m = "13871682";
		} else if (n < 90) {
			m = "13642361";
		} else {
			m = "13429021";
		}
		return m;
	}

	
	class A extends Thread {
		@Override
		public void run() {
			String phone = null;
			for (int i = 100; i < 1000; i++) {
				phone = getRandomNum() + i;
				fill(phone, getRandomDigit() + "", "35", "221",
						"0000000223", "15212345678", "0755");
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	class B extends Thread {
		@Override
		public void run() {
			String phone = null;
			for (int i = 100; i < 1000; i++) {
				phone = getRandomNum() + i;
				fill(phone, getRandomDigit() + "", "35", "222",
						"0000000224", "15612345678", "0755");
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	class C extends Thread {
		@Override
		public void run() {
			String phone = null;
			for (int i = 100; i < 1000; i++) {
				phone = getRandomNum() + i;
				fill(phone, getRandomDigit() + "", "35", "1",
						"0000000225", "13212345678", "0755");
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	
	class D extends Thread {
		@Override
		public void run() {
			String phone = null;
			for (int i = 100; i < 1000; i++) {
				phone = getRandomNum() + i;
				fill(phone, getRandomDigit() + "", "35", "1",
						"0000000226", "18512345678", "0755");
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	
	class E extends Thread {
		@Override
		public void run() {
			String phone = null;
			for (int i = 100; i < 1000; i++) {
				phone = getRandomNum() + i;
				fill(phone, getRandomDigit() + "", "35", "1",
						"0000000227", "18912345678", "0755");
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	class F extends Thread {
		@Override
		public void run() {
			String phone = null;
			for (int i = 100; i < 1000; i++) {
				phone = getRandomNum() + i;
				fill(phone, getRandomDigit() + "", "35", "221",
						"0000000228", "15112345678", "0755");
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	
	class G extends Thread {
		@Override
		public void run() {
			String phone = null;
			for (int i = 100; i < 1000; i++) {
				phone = getRandomNum() + i;
				fill(phone, getRandomDigit() + "", "35", "222",
						"0000000229", "13412345678", "0755");
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	class TaskFill extends TimerTask{
		
		@Override
		public void run() {
			PressureTest test =new PressureTest();
//			A a=test.new A();
			A a1=test.new A();
//			B b=test.new B();
//			B b1=test.new B();
//			C c=test.new C();
//			D d=test.new D();
//			E e=test.new E();
//			F f=test.new F();
//			G g=test.new G();
//			a.start();
//			b.start();
//			c.start();
//			d.start();
//			e.start();
//			f.start();
//			g.start();
//			try {
//				Thread.sleep(2000);
//			} catch (InterruptedException e1) {
//				e1.printStackTrace();
//			}
			a1.start();
//			b1.start();
		}
		}
		
	
	public static void testfill() {
//		PressureTest test=new PressureTest();
//		new Timer().schedule(test.new TaskFill(),60*1000,60*60*1000);
//		fill("13042801999", getRandomDigit() + "", "35", "222",
//				"0000000229", "13412345678", "0755");
		
		//http://apitest.gm193.com/post/userinfo.asp?username=gm193test&sign=28f0fc425a128019697d22fee890ede4
		TradeMsg tmsg=new TradeMsg();
		tmsg.setSeqNo("2014070112345678912577");
		tmsg.setFlag("0");
		tmsg.setMsgFrom2("0103");
		tmsg.setRsCode("000");
		tmsg.setServNo("00");
		tmsg.setTradeNo("10002");
		Map<String , String> maps=new HashMap<String, String>();
		maps.put("handCharge", "32000.0");
		maps.put("time", "20140704094116");
		maps.put("total_fee", "8000000.0");
		maps.put("userno", "0000000230");
		maps.put("transaction_id", "2014070109393899970");
		tmsg.setContent(maps);
		PortalSend.getInstance().sendmsg(tmsg);
	}
	

}
