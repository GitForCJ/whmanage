package com.wlt.webm.scpdb;

import com.wlt.webm.scputil.Tools;
import com.commsoft.epay.util.logging.Log;

/**
 * @Description: 循环获得字符串（该类是线程安全的）
 */
public class SystemSeq {
	/**
	 * 类实例
	 */
	private static SystemSeq sysSeq = new SystemSeq();

	/**
	 * 数据库操作类
	 */
	private static DBService db = null;

	/**
	 * 重复售卡同步锁
	 */
	private Object getSeq = new Object();

	/**
	 * 私有构造方法，不允许外部实例化。
	 */
	private SystemSeq() {

	}

	/**
	 * 获得类唯一实例
	 * 
	 * @param dbs
	 *            数据库连接
	 * @return 类实例
	 */
	public static SystemSeq getInstance() {
		return sysSeq;
	}

	/**
	 * 短时间内不能重复获取序列号
	 * 
	 * @return 返回是否可以对号码缴费
	 * @throws Exception
	 */
	public String getNoSix_bank()throws Exception {
		synchronized (getSeq) {
			db = new DBService();
			String sequence="";
			try {
				StringBuffer sql = new StringBuffer();
	            sql.append("update ec_code_sequence set sequence=sequence+1 where code='ec_sysseq_id'");
	            db.update(sql.toString());

	            sql.delete(0, sql.length());
	            sql.append("select sequence from ec_code_sequence where code='ec_sysseq_id'");
	            sequence = Tools.formatSn(db.getInt(sql.toString()),6);
	            sql.delete(0, sql.length());
	            if(sequence.equals("999999")){
	            	sql.append("update ec_code_sequence set sequence=0 where code='ec_sysseq_id'");
	                db.update(sql.toString());
	            }
			} catch (Exception e) {
				Log.error("查询出错",e);
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
