package com.wlt.webm.pccommon;

import java.util.ArrayList;
import java.util.List;

import com.commsoft.epay.util.logging.Log;
import com.wlt.webm.business.action.Flow_GrabBean;
import com.wlt.webm.business.action.Flows_Public_Method;
import com.wlt.webm.business.action.JdOrderBean;
import com.wlt.webm.business.action.TencentOrdersBean;
import com.wlt.webm.business.bean.unicom3gquery.HttpFillOP;
import com.wlt.webm.db.DBService;
import com.wlt.webm.message.PortalSend;
import com.wlt.webm.tool.Tools;

public class Grab_Thread extends Thread {
	/**
	 * �̶߳��󼯺ϣ���� ��ʹ�õ��̶߳���
	 */
	public static ArrayList thread_list=new ArrayList();
	
	/**
	 * һ���̴߳���������
	 */
	public static final int t_count=10;
	
	/**
	 * ����߳���
	 */
	public static final int t_size=2;
	
	public void run() {
		while(true){
			if(thread_list.size()>=t_size){//�����ǰ��ʹ�õ��߳������ڻ�����Զ��������߳�������ȴ����룬�����ж��Ƿ��п��߳�
				try { Thread.sleep(5*1000);} catch (Exception e) {}
				continue;
			}
			List<Flow_GrabBean> arrys=Grab_Thread.getGrab_Handle_Orders();
			if(arrys==null || arrys.size()<=0){
				try { Thread.sleep(10*1000);} catch (Exception e) {}
				continue;
			}
			new GrabService_Thread(arrys).start();
		}
	}
	
	/**ץ�� ��һ��ץ��10�ʶ���
	 * @return List<Flow_GrabBean>
	 */
	public static List<Flow_GrabBean> getGrab_Handle_Orders(){
		StringBuffer buffer=null;
		DBService db=null;
		try {
			db=new DBService();
			List datas=db.getList("SELECT r_dis,o_tradeserial,o_tradeobject,o_buyid,o_service,o_fee,o_writeoff," +
					" o_writecheck,o_userno,o_phone_type,o_city,o_notityUrl,o_type FROM wht_flow_Reissue " +
					" WHERE r_dis=1 AND handle_state=0 AND o_state=4 ORDER BY o_tradetime ASC LIMIT 0,"+t_count);
			if(datas==null || datas.size()<=0){
				return null;
			}
			buffer=new StringBuffer();
			List<Flow_GrabBean> arrysList=new ArrayList<Flow_GrabBean>();
			for(int i=0;i<datas.size();i++){
				Flow_GrabBean b=new Flow_GrabBean();
				
				String[] a=(String[])datas.get(i);
				b.setR_dis(a[0]);
				b.setO_tradeserial(a[1]);
				b.setO_tradeobject(a[2]);
				b.setO_buyid(a[3]);
				b.setO_service(a[4]);
				b.setO_fee(a[5]);
				b.setO_writeoff(a[6]);
				b.setO_writecheck(a[7]);
				b.setO_userno(a[8]);
				b.setO_phone_type(a[9]);
				b.setO_city(a[10]);
				b.setO_notityUrl(a[11]);
				b.setO_type(a[12]);
				
				arrysList.add(b);
				
				buffer.append("'"+a[1]+"',");
			}
			Log.info("������ץ��,,�޸Ĳ�������״̬Ϊ,��ץ��״̬,,����������:"+buffer.toString());
			boolean bool=db.update("UPDATE wht_flow_Reissue SET handle_state=1 WHERE r_dis=1 AND handle_state=0 AND o_state=4 AND o_tradeserial IN ("+buffer.substring(0,buffer.length()-1).toString()+")")>0?true:false;
			if(bool){
				return arrysList;
			}else{
				return null;
			}
		} catch (Exception e) {
			Log.error("������ץ��,,һ���ζ�����:"+buffer.toString()+",,,ϵͳ�쳣,,ex:"+e);
		} finally {
			if (db != null) {
				db.close();
				db = null;
			}
		}
		return null;
	}
	
}

class GrabService_Thread extends Thread{
	private List<Flow_GrabBean> arrysList;
	
	public GrabService_Thread(List<Flow_GrabBean> arry){
		this.arrysList=arry;
	}
	
	public void run() {
		//��ǰ�߳���ӵ� �̼߳�����
		Grab_Thread.thread_list.add(this);
		
		//����ѭ�� ���䶩��
		int size=this.arrysList.size();
		for(int i=0;i<size;i++){
			Flow_GrabBean bean=this.arrysList.get(i);
			
			String mz=bean.getO_writeoff().replace("mb","").replace("MB","").trim();

			StringBuffer buf=new StringBuffer();
			String rs=Flows_Public_Method.Request_Flows(mz,
					bean.getO_tradeserial(), bean.getO_tradeobject(),bean.getO_phone_type(),bean.getO_buyid(),bean.getO_city(),buf);
			
			if(HttpFillOP.tencent_code.equals(bean.getO_type())){
				//��Ѷ
				TencentOrdersBean tBean=new TencentOrdersBean();
				tBean.setSeqNo1(bean.getO_tradeserial());
				tBean.setPaipai_dealid(bean.getO_writecheck());
				tBean.setCode(bean.getO_type());
				tBean.setBeifen_reesult(buf.toString());
				
				Flows_Public_Method.Result_Processing(rs, bean.getO_buyid(),tBean,bean.getO_type());
			}
		}
		
		//�̼߳����� ɾ����ǰ�߳�
		Grab_Thread.thread_list.remove(this);
	}
}