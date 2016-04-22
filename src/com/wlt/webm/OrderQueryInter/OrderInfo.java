package com.wlt.webm.OrderQueryInter;

import com.wlt.webm.db.DBService;
import com.commsoft.epay.util.logging.Log;

/**
 * @author adminA
 *
 */
public class OrderInfo 
{
	/**
	 * ��ȡ����״̬
	 * @param tradeserial
	 * @param tableName 
	 * @return int
	 */
	public int getOrderInfo(String tradeserial,String tableName)
	{
		DBService db = null;
		String sql="SELECT state FROM wht_orderForm_"+tableName+" WHERE tradeserial='"+tradeserial+"'";
		try {
			db = new DBService();
			return db.getIntNull(sql,null);
		} catch (Exception ex) {
			Log.error("��ȡ����״̬�ӿ�,,,,,"+ex);
			return -1;
		} finally {
			if(db!=null)
				db.close();
		}
	}
}
