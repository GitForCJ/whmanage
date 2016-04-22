package com.wlt.webm.scpdb;

import com.wlt.webm.scpcommon.Constant;
import com.wlt.webm.scputil.DateParser;
import com.commsoft.epay.util.logging.Log;

/**
 * <p>@Description: 循环获得字符串（该类是线程安全的）</p>
 */
public class UniqueCard {
	/**
	 * 类实例
	 */
	private static UniqueCard uCard = new UniqueCard();

	/**
	 *  数据库操作类
	 */
	private static DBService db = null;

	/**
	 * 重复售卡同步锁
	 */
	private Object sellCard = new Object();


	/**
	 * 私有构造方法，不允许外部实例化。
	 */
	private UniqueCard() {

	}

	/**
	 * 获得类唯一实例
	 * @param dbs 数据库连接
	 * @return 类实例
	 */
	public static UniqueCard getInstance(DBService dbs) {
		db=dbs;
		return uCard;
	}

	/**
	 * 短时间内不能重复交费判断
	 * @param cardType 卡类型编号
	 * @param cardFeeNo 卡面值编号 
	 * @return 返回是否可以对号码缴费
	 * @throws Exception 
	 */
	public String[] getCardInf(int cardType, int cardFeeNo)throws Exception {
		synchronized (sellCard) {
			StringBuffer sql = new StringBuffer();
			sql.append("select  first 1 a.cardno,a.cardbatch,a.password,a.overtime,c.name,")
			   .append("b.pwdtype,b.fillphone,b.content,c.present,b.service ,a.cardsign ")
			   .append(" from ec_card a,ec_cardtype b,ec_cardfee c")
			   .append(" where a.cardtype=b.id and a.cardfee=c.id and a.state=1 ")
			   .append("and a.cardtype=").append(cardType)
			   .append(" and a.cardfee=").append(cardFeeNo)
			   .append(" and a.overtime>='").append(DateParser.getNowDateTime())
			   .append("' order by intime");
			String[] str = null;
			Log.info("售卡的SQL-------------"+sql.toString());
			try {
				str = db.getStringArray(sql.toString());
				if (str != null) {
					sql.delete(0, sql.length());
					Log.info("修改卡状态-----------------------");
					sql.append("update ec_card set state=").append(Constant.CARD_FINAL_SELL)
					   .append(",distime='").append(DateParser.getNowDateTime()).append("'")
					   .append(" where cardno='").append(str[0]).append("'")
					   .append(" and state=1");
					try {
						int num=db.update(sql.toString());
						if(num!=1){
							return null;
						}
					} catch (Exception ex) {
						Log.error("更新卡状态出错:", ex);
						throw ex;
					}
				}
			} catch (Exception e) {
				Log.error("获取卡号信息出错:", e);
				throw e;
			}
			return str;
		}
	}

}

