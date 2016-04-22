package com.wlt.webm.AccountInfo.action;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import whmessgae.TradeMsg;

import com.commsoft.epay.util.logging.Log;
import com.wlt.webm.business.bean.TyPortPayBean;
import com.wlt.webm.db.DBService;
import com.wlt.webm.message.MemcacheConfig;
import com.wlt.webm.message.PortalSend;
import com.wlt.webm.util.MD5;
import com.wlt.webm.util.TenpayUtil;

/**
 * @author adminA
 *
 */
public class TyPortInfo extends DispatchAction {

	/**
	 * ������֧�� ����ƽ̨�ӿ�  ��Ϣ
	 * @param mapping 
	 * @param form 
	 * @param request 
	 * @param response 
	 * @return null
	 * @throws Exception 
	 */
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setCharacterEncoding("utf-8");
		String UPTRANSEQ=request.getParameter("UPTRANSEQ");//������ˮ��
		String TRANDATE=request.getParameter("TRANDATE");//��������
		String RETNCODE=request.getParameter("RETNCODE");//������״̬ 
		String RETNINFO=request.getParameter("RETNINFO");//������������
		//String ORDERREQTRANSEQ=request.getParameter("ORDERREQTRANSEQ");//������������ˮ��
		String ORDERSEQ=request.getParameter("ORDERSEQ");//������
		String ORDERAMOUNT=request.getParameter("ORDERAMOUNT");//�����ܽ��
		//String PRODUCTAMOUNT=request.getParameter("PRODUCTAMOUNT");//��Ʒ���
		//String ATTACHAMOUNT=request.getParameter("ATTACHAMOUNT");//���ӽ��
		//String CURTYPE=request.getParameter("CURTYPE");//����
		//String ENCODETYPE=request.getParameter("ENCODETYPE");//���ܷ�ʽ
		//String BANKID=request.getParameter("BANKID");//���б���
		String SIGN=request.getParameter("SIGN");//����ǩ��
		if(UPTRANSEQ==null || TRANDATE==null || RETNCODE==null || RETNINFO==null || ORDERSEQ==null || ORDERAMOUNT==null || SIGN==null)
		{
			Log.info("�ص��������㣬����");
			return null;
		}
		
		TyPortPayBean ty=new TyPortPayBean();
		PrintWriter out = response.getWriter();
		
		String signStr="UPTRANSEQ="+UPTRANSEQ+"&MERCHANTID="+ty.getMERCHANTID()+"&ORDERID="+ORDERSEQ+"&PAYMENT="+ORDERAMOUNT+"&RETNCODE="+RETNCODE+"&RETNINFO="+RETNINFO+"&PAYDATE="+TRANDATE+"&KEY="+ty.getKEY();
		String mm=(MD5.encode(signStr)).toUpperCase();
		if(mm.equals(SIGN))//ǩ����ȷ
		{
			Log.info("ǩ����ȷ������");
			if(!"0000".equals(RETNCODE))
			{
				//����ʧ��
				Log.info("����ʧ�ܣ�����������ˮ��="+UPTRANSEQ+"  ,������="+ORDERSEQ+"   ,������״̬ ="+RETNCODE);
				DBService db = null;
		        try {
		        	db=new DBService();
		        	 String sql="UPDATE wht_transactionRecord SET recordStatus=2 WHERE orderNumber='"+ORDERSEQ+"'";
		        	 if(db.update(sql)>0)
		        	 {
		        		 Log.info("����ʧ�ܣ����� �޸Ķ���״̬Ϊʧ����ɣ���");
		        	 }
		        	 else
		        	 {
		        		 Log.info("����ʧ�ܣ���������������");
		        	 }
		        } 
		        catch (Exception ex) 
		        {
		        	 Log.error("����ʧ�ܣ����� �޸Ķ���״̬�쳣����"+ex);
		        } 
		        finally 
		        {
		        	if(db!=null)
		        		db.close();
		        }
				out.println("UPTRANSEQ_"+UPTRANSEQ);
				out.flush();
				out.close();
			}
			else
			{
				 Log.info("��֧�����׳ɹ�,,���ض���״̬�Ѹ�Ϊ�����У���������ˮ��="+UPTRANSEQ+"  ,������="+ORDERSEQ+"   ,������״̬ ="+RETNCODE);
				DBService db = null;
		        try {
		        	db=new DBService();
		        	 String sql="SELECT recordStatus,userNumber FROM wht_transactionRecord WHERE orderNumber='"+ORDERSEQ+"'";
		        	 List arrList=db.getList(sql);
		        	 if(arrList.size()>0)
		        	 {
		        		 Object[] obj=(Object[])arrList.get(0);
		        		 if("0".equals(obj[0]))
		        		 {
			        		 db.update("UPDATE wht_transactionRecord SET recordStatus=3 WHERE orderNumber='"+ORDERSEQ+"'");
			 				 new TyPortThread(ORDERSEQ,ORDERAMOUNT,obj[1].toString()).start();
		        		 }
		        		 else
		        		 {
		        			 Log.info("��֧�����׳ɹ�������״̬���޸Ĺ����˴λص�Ϊ�ظ��ص�");
		        		 }
		        	 }
		        	 else
		        	 {
		        		 Log.info("��֧�����׳ɹ����������ض���������");
		        	 }
		        } 
		        catch (Exception ex) 
		        {
		        	 Log.error("��֧�����׳ɹ����������ض���������"+ex);
		        } 
		        finally 
		        {
		        	if(db!=null)
		        		db.close();
		        }
				out.println("UPTRANSEQ_"+UPTRANSEQ);
				out.flush();
				out.close();
			}
		}
		else
		{
			//ǩ���쳣����Ϣ���۸�
			Log.info("ǩ���쳣����Ϣ���۸ģ�����������ˮ��="+UPTRANSEQ+"  ,������="+ORDERSEQ+"   ,������״̬ ="+RETNCODE);
		}
		return null;
	}
	
	
	/**
	 * �ڲ��߳���
	 */
	class TyPortThread extends Thread{
		private String ORDERSEQ;
		private String ORDERAMOUNT;
		private String userno;
		/**
		 * @param transaction_id
		 * @param request
		 */
		public TyPortThread(String ORDERSEQ,String ORDERAMOUNT,String userno) {
			this.ORDERSEQ=ORDERSEQ;
			this.ORDERAMOUNT=ORDERAMOUNT;
			this.userno=userno;
		}
		/* 
		 * �߳�����
		 */
		public void run() {
			//���׳ɹ�
			String tradeNo="10002"; //  ǩԼ��������
			String seqNo=ORDERSEQ;//�ö����ţ�������ˮ�� Ψһ
			TradeMsg msg=new TradeMsg();
	   		msg.setSeqNo(seqNo); 
	   		msg.setFlag("0");//������
	   		msg.setMsgFrom2("0103");
	   		msg.setServNo("00");
	   		msg.setTradeNo(tradeNo);
	   		
	   		Map<String, String> content =new HashMap<String, String>();
	   		content.put("transaction_id",ORDERSEQ);
	   		content.put("time",TenpayUtil.getCurrTime());
	   		content.put("total_fee",Float.parseFloat(ORDERAMOUNT)*10+"");
	   		content.put("handCharge","0");//������  (Float.parseFloat(ORDERAMOUNT)*10)*0.002+""
	   		content.put("userno",this.userno);
	           
	   		Log.info("�������趩����:"+seqNo);
	   		msg.setContent(content);
	
	   		if(!PortalSend.getInstance().sendmsg(msg)){
	   			//return 1;//������Ϣʧ��,��ֵʧ��
	   			Log.info("������Ϣʧ��,��ֵʧ�ܣ�����");
	   			return ;
	   		}
	   		int k=0;
	   		TradeMsg rsMsg=null;
	   		while(k<180){
	   			k++;
	   			try {
	   				Thread.sleep(1000);
	   			}catch (InterruptedException e) {
	   				e.printStackTrace();
	   			}
	   			rsMsg=MemcacheConfig.getInstance().get(seqNo);
	   			if(null==rsMsg){
	   				continue;
	   			}else{
	   				MemcacheConfig.getInstance().delete(seqNo);
	   				break;
	   			}
	   		}
	   		if(null==rsMsg&&k>=150)
	   		{
	   			//return 2;//��ʱ
	   			Log.info("����ʱ,��ֵʧ�ܣ�����");
	   			return ;
	   		}
	   		String code1=rsMsg.getRsCode();
	   		if("000".equals(code1))
	   		{
	   			//return 0;//�ɹ�
	   			Log.info("�����ɹ�������");
	   			return;
	   		}
	   		else
	   		{
	   		    //return 3;//ʧ��
	   			Log.info("����ʱ,��ֵʧ�ܣ�����");
	   			return ;
	   		}
		}
	}
	
}
