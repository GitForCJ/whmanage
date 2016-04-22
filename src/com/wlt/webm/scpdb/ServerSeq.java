package com.wlt.webm.scpdb;

import com.wlt.webm.scputil.Tools;
import com.commsoft.epay.util.logging.Log;

public class ServerSeq {
	/**
	 * ��ʵ��
	 */
	private static ServerSeq sysSeq = new ServerSeq();

	/**
	 *  ���ݿ������
	 */
	private static DBService db = null;

	/**
	 * �ظ��ۿ�ͬ����
	 */
	private Object getSeq = new Object();


	/**
	 * ˽�й��췽�����������ⲿʵ������
	 */
	private ServerSeq() {

	}

	/**
	 * �����Ψһʵ��
	 * @param dbs ���ݿ�����
	 * @return ��ʵ��
	 */
	public static ServerSeq getInstance() {
		return sysSeq;
	}

	/**
	 * ��ʱ���ڲ����ظ���ȡ���к�
	 * @return �����Ƿ���ԶԺ���ɷ�
	 * @throws Exception 
	 */
	public String getNoSix_bank()throws Exception {
		synchronized (getSeq) {
			db = new DBService();
			String sequence="";
			try {
				StringBuffer sql = new StringBuffer();
	            sql.append("update ec_code_sequence set sequence=sequence+1 where code='ec_serverSeq_id'");
	            db.update(sql.toString());
	            sql.delete(0, sql.length());
	            sql.append("select sequence from ec_code_sequence where code='ec_serverSeq_id'");
	            sequence = Tools.formatSn(db.getInt(sql.toString()),7);
	            sql.delete(0, sql.length());
	            if(sequence.equals("9999999")){
	            	sql.append("update ec_code_sequence set sequence=0 where code='ec_serverSeq_id'");
	                db.update(sql.toString());
	            }
			} catch (Exception e) {
				Log.error("��ѯ���ɵ��ƶ���ˮ�ų���",e);
			}
			finally
			{
				if(db!=null)
				db.close();
			}
			return sequence;
		}
	}

}

