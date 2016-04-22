package com.wlt.webm.pccommon;

import java.util.ArrayList;

import com.commsoft.epay.util.logging.Log;
import com.wlt.webm.business.action.Flows_Public_Method;
import com.wlt.webm.business.action.JdOrderBean;
import com.wlt.webm.business.action.Jd_Flows;
import com.wlt.webm.business.bean.beijingFlow.Flow;
import com.wlt.webm.business.bean.dhst.FlowsService;
import com.wlt.webm.business.bean.leliu.LeliuFlowCharge;
import com.wlt.webm.business.bean.lianlian.LianlianFlows;
import com.wlt.webm.business.bean.unicom3gquery.FillProductDetail;
import com.wlt.webm.business.bean.unicom3gquery.FillProductInfo;
import com.wlt.webm.business.bean.unicom3gquery.HttpFillOP;
import com.wlt.webm.db.DBService;
import com.wlt.webm.message.PortalSend;
import com.wlt.webm.tool.Tools;

public class JDThread extends Thread {
	

	/**
	 * �̶߳��󼯺ϣ���� ��ʹ�õ��̶߳���
	 */
	public static ArrayList thread_list=new ArrayList();
	
	/**
	 * һ���̴߳���������
	 */
	public static final int t_count=20;
	
	/**
	 * ����������������߳���
	 */
	public static final int t_size=50;
	
	/**
	 * ��ѯ��ѯ������ֵ�����ͬһ�ʶ�������ѯ ����
	 */
	public static final int query_max_count=30;
	
	/**
	 * ��ѯ��ѯ������ֵ�����ͬһ�ʶ�����ѯ����С���ʱ��
	 */
	public static final int query_min_time=20*1000;
	
	public void run() {
		while(true){
			//�Ƿ�ѹ��  0��ѹ��,,����ѹ����
			if(Tools.FLOW_SWITCH_FLAG!=0){
				Log.info("������������ѹ����,,,");
				try { Thread.sleep(60*1000);} catch (Exception e) {
					Log.error("����ѹ��ϵͳ�쳣������ex:"+e);
				}
				continue;
			}
			
			if(thread_list.size()>=t_size){//�����ǰ��ʹ�õ��߳������ڻ�����Զ��������߳�������ȴ����룬�����ж��Ƿ��п��߳�
				try { Thread.sleep(5*1000);} catch (Exception e) {}
				continue;
			}
			//�Ӷ�����ʱ����ȡһ������,���������Զ��� ֵ t_count,һ��ȡһ���߳���Ҫ����Ķ�������
			ArrayList<JdOrderBean> arry=JDThread.getOrders_del();
			if(arry==null || arry.size()<=0){//û�о�����Ҫ����Ķ�����Ϣ
				try { Thread.sleep(5*1000);} catch (Exception e) {}
				continue;
			}else{//�о���������Ϣ
				Log.info("=========һ����������:"+arry.size()+",,��ǰ�̼߳�������ʹ���߳�����"+thread_list.size());
				if(arry.size()>0){
					//���������̣߳�����һ����������
					Jd_Thread_Service tt=new Jd_Thread_Service(arry);
					tt.start();
				}
			}
		}
	}
	
	/**
	 * ��ȡһ��������Ϣ��Ϣ
	 * @return ArrayList
	 */
	@SuppressWarnings("unchecked")
	public static ArrayList<JdOrderBean> getOrders_del(){
		DBService db=null;
		try {
			db = new DBService();
			ArrayList<String[]> arrys=(ArrayList<String[]>)db.getList("SELECT fillOrderNo,fillMobile,fillAmount,finTime,areaUsed,notifyUrl," +
					" mutCode,areaCode,costPrice,wh_createtime,wh_mutCode,userno,exp1,wh_order_num,sys_id,exp2 FROM jd_orders ORDER BY wh_createtime asc LIMIT 0,"+JDThread.t_count);
			if(arrys==null || arrys.size()<=0){
				return null;
			}
			Log.info("����������ȡ��ʱ������Ϣ,����:"+arrys.size());
			
			String buf="DELETE FROM jd_orders WHERE sys_id IN (";
			ArrayList<JdOrderBean> list=new ArrayList<JdOrderBean>();
			for(String[] s:arrys){
				JdOrderBean b=new JdOrderBean();
				b.setFillOrderNo(s[0]);
				b.setFillMobile(s[1]);
				b.setFillAmount(s[2]);
				b.setFinTime(s[3]);
				b.setAreaUsed(s[4]);
				b.setNotifyUrl(s[5]);
				b.setMutCode(s[6]);
				b.setAreaCode(s[7]);
				b.setCostPrice(s[8]);
				b.setOrdertime(s[9]);
				b.setWh_mutCode(s[10]);
				b.setUserno(s[11]);
				b.setInterid(s[12]);
				b.setWh_order_num(s[13]);
				b.setCode(s[15]);
				list.add(b);
				buf=buf+"'"+s[14]+"',";
			}
			buf=buf.substring(0,buf.length()-1);
			buf=buf+")";
			
			Log.info("��������ɾ����ȡ�����ʱ����,sql:"+buf);
			
			if(db.update(buf.toString())>0){
				Log.info("��������ɾ����ȡ�����ʱ����,ɾ���ɹ�,����������ѯ��������,sql:"+buf);
				return list;
			}
			return null;
		} catch (Exception e) {
			Log.error("����������ȡ,ɾ����ʱ�����쳣������ex:"+e);
		} finally {
			if (db != null) {
				db.close();
				db = null;
			}
		}
		return null;
	}
	
}

/**
 * �������� ���߳� ҵ����
 * @author 1989
 */
class Jd_Thread_Service extends Thread{
	
	/**
	 * ÿ���̷߳���ģ�һ���������ݼ���
	 */
	private ArrayList<JdOrderBean> arrays=null;
	
	/**
	 * @param arrays
	 */
	@SuppressWarnings("unchecked")
	public Jd_Thread_Service(ArrayList arrays){
		this.arrays=arrays;
	}
	
	@SuppressWarnings("unchecked")
	public void run() {
		//����ǰ�߳� ��ӵ��̼߳�����
		JDThread.thread_list.add(this);
		
		//ѭ������һ������ ��ֵ
		int size=this.arrays.size();
		for(int c=0;c<size;c++){
			//ÿ��ѭ����ȡ��һ�ʶ�����ȡ���Զ�ɾ������
			JdOrderBean bean=this.arrays.remove(0);
			Log.info("��㶩���ţ�"+bean.getWh_order_num()+",,,���������ţ�"+bean.getFillOrderNo()+",,,,��ʼ�����ֵ����,,,");
			
			
			String[] strs = bean.getInterid().split("#");//�ӿ�id#�����˾����
			StringBuffer buf=new StringBuffer();
			String rs=Flows_Public_Method.Request_Flows(bean.getFillAmount(),
					bean.getWh_order_num(), bean.getFillMobile(), bean.getWh_mutCode(), strs[0], strs[1],buf);
			bean.setBeifen_reesult(buf.toString());
			Flows_Public_Method.Result_Processing(rs, strs[0], bean, bean.getCode());
		}
		//��ǰ�߳�һ������������ɣ�ɾ�� �̼߳����� ��ǰ�̶߳��󣬲���¼һ����������ʱ��
		JDThread.thread_list.remove(this);
	}
}