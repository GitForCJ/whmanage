package com.wlt.webm.business.tencent;

import com.commsoft.epay.util.logging.Log;
import com.wlt.webm.scpcommon.Constant;
import com.wlt.webm.scpdb.DBOperator;
import com.wlt.webm.scpdb.DBService;
import com.wlt.webm.scpdb.FundAcctDao;
import com.wlt.webm.scputil.DateParser;
import com.wlt.webm.scputil.Tools;

/**
 * <p>@Description: ͨ����Ѷ�Ƹ�ͨ��ƽ̨�˻�����</p>
 * <p>@version 3.0.3.0</p>
 */ 
public class TransferAccounts  {
	

	/**
	 * Ĭ��
	 */
	public TransferAccounts(){
		
	}
	
    /**
     * ���˴�����
     */
	public  static String  deal(String transaction_id,String time,String total_fee,String HandCharge,
			String resourceid,String serialNumber){
		String totalfee1 ="";
		DBService db = null;
		String flag="000";
		try {
			db = new DBService();
			db.setAutoCommit(false);
			// �ж���ˮ���Ƿ����, �����ظ���Ϣ
			String yearMonth = DateParser.getNowDate().substring(2, 6);
			String tableName = "wlt_acctbill_" + yearMonth;
			String sql = "select childfacct from " + tableName
					+ " where dealserial='" + serialNumber+"'";
			if(db.hasData(sql))
			{
				Log.info("�Ƹ�ͨת��ҵ��:�յ��ظ���Ϣ");
				flag= "001";
			} else {
				String remark = "�Ƹ�ͨ�ӿ�ת��";
				// ���ݿ����
				DBOperator dbOper = new DBOperator(db);
				String facct = dbOper.getBindFacct(resourceid);
				// ��ֵ��Ѻ���˻�
				String childFacct = facct + Constant.FACCT_DEPOSIT_TYPE;


				FundAcctDao facctDB = new FundAcctDao(db);
				// �ж��Ƿ���Ҫ������
				if (HandCharge != null && !HandCharge.equals("0")) {
					// ʵ��ת�˽��
					totalfee1 = ""+ (Integer.parseInt(total_fee) - Integer.parseInt(HandCharge));
				}
				totalfee1=""+Integer.parseInt(total_fee);
				//��¼�˻���ϸ��
	            StringBuffer sql2 = new StringBuffer();	
	            sql2.append("insert into wlt_acctbill_"+yearMonth+"(childfacct,dealserial,tradeaccount,tradetime,tradefee,tradetype,`explain`,state,distime,accountleft,tradeserial,other_childfacct,pay_type)" +
	            		" values(");
	            sql2.append("'"+childFacct+"',");
	            sql2.append("'"+serialNumber+"',");
	            sql2.append("'"+childFacct+"',");
	            sql2.append("'"+time+"',");
	            sql2.append("'"+totalfee1+"',");
	            sql2.append("33,");
	            sql2.append("'�Ƹ�ͨת��',");
	            sql2.append("0,");
	            sql2.append("'"+DateParser.getNowDateTime()+"',");
	            String sqlFuncLeft = "select usableleft from wlt_childfacct where childfacct = "+childFacct;
	            sql2.append("'"+db.getString(sqlFuncLeft)+"',");
	            sql2.append("'"+transaction_id+"',");
	            sql2.append("'"+childFacct+"',");
	            sql2.append("2");
	            sql2.append(")");
				db.update(sql2.toString());
				sql2=null;
				//�����ʽ��ʻ����
				facctDB.updateChildLeft(childFacct, Integer.parseInt(totalfee1),
						null);
				String current=Tools.getNow();
				facctDB.updateScpState(current, transaction_id);
			} 
			db.commit();
		}catch (Exception ex) {
			flag="001";
			db.rollback();
			Log.info("�Ƹ�ͨת��ҵ���ڲ����������"+ex.toString());
		}
		finally
		{
			if(db!=null)
			db.close();
		}

		Log.info("����Ƹ�ͨת��ҵ���ڲ��������");
		return flag;
	}
	
}
