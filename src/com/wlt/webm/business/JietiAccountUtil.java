package com.wlt.webm.business;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

import com.wlt.webm.scpdb.DBService;

/**
 * @author yxl
 * 代理点阶梯佣金统计
 */
public class JietiAccountUtil {
	
	/**
	 * @param dateMMdd 统计日期 格式yyyyMM-201305
	 * @return
	 * @throws SQLException 
	 */
	public int account(String dateyyyyMM) throws SQLException{
		int result = 0;
		DBService servce = new DBService();
		String userId = "";
		String areaId = "";
		String fundAcct = "";
		try {
			//查询所有代理点
			StringBuffer sql1 = new StringBuffer();
			sql1.append("select a.user_id,b.sr_type,a.user_site from sys_user a ") ;
			sql1.append(" left join sys_role b on a.user_role = b.sr_id");
			sql1.append(" where b.sr_type = 3");
			List userList = servce.getList(sql1.toString());
			for(Object user : userList){
//				1:移动2:联通3:电信
				
				String[] temp = (String[])user;
				userId = temp[0];
				areaId = temp[2];
				//获取用户资金账号
				StringBuffer sqlAcct = new StringBuffer();
				sqlAcct.append("select fundacct from wlt_facct where termID =  "+userId);
				fundAcct = servce.getString(sqlAcct.toString());
				//统计移动交易总额
				StringBuffer sql2 = new StringBuffer();
				sql2.append("select sum(fee) from wlt_orderform_"+dateyyyyMM.substring(2,6)+" ");
				sql2.append(" where phone_type = 1 and userid in (select userid from sys_user where user_pt = "+userId+")");
				String yidongFee = servce.getString(sql2.toString());
				//统计联通交易总额
				StringBuffer sql3 = new StringBuffer();
				sql3.append("select sum(fee) from wlt_orderform_"+dateyyyyMM.substring(2,6)+" ");
				sql3.append(" where phone_type = 2 and userid in (select userid from sys_user where user_pt = "+userId+")");
				String liantongFee = servce.getString(sql3.toString());
				//统计电信交易总额
				StringBuffer sql4 = new StringBuffer();
				sql4.append("select sum(fee) from wlt_orderform_"+dateyyyyMM.substring(2,6)+" ");
				sql4.append(" where phone_type = 3 and userid in (select userid from sys_user where user_pt = "+userId+")");
				String dianxinFee = servce.getString(sql4.toString());
				//查询用户阶梯佣金比例(根据用户区域查询佣金)-移动返点
				StringBuffer sql5 = new StringBuffer();
				sql5.append("select a.sc_traderpercent from sys_commission a ");
				sql5.append(" LEFT JOIN sys_commissiongroup b on a.sg_id = b.sg_id");
				sql5.append(" WHERE a.sc_tradertype = 1 AND b.sa_id = "+areaId+" AND b.srt_id = 5 AND a.sc_traderbegin<"+yidongFee+" AND a.sc_traderend > "+yidongFee+"");
				String retPointYiDong = servce.getString(sql5.toString());
				int yidong_fee = 0;
				if(null != retPointYiDong && !"".equals(retPointYiDong) && null != yidongFee && !"".equals(yidongFee)){
					BigDecimal bigDecimal = new BigDecimal(retPointYiDong);
					yidong_fee = bigDecimal.multiply(new BigDecimal(yidongFee)).divide(new BigDecimal(100)).intValue();
				}
				//查询用户阶梯佣金比例(根据用户区域查询佣金)-联通返点
				StringBuffer sql6 = new StringBuffer();
				sql6.append("select a.sc_traderpercent from sys_commission a ");
				sql6.append(" LEFT JOIN sys_commissiongroup b on a.sg_id = b.sg_id");
				sql6.append(" WHERE a.sc_tradertype = 2 AND b.sa_id = "+areaId+" AND b.srt_id = 5 AND a.sc_traderbegin<"+yidongFee+" AND a.sc_traderend > "+yidongFee+"");
				String retPointLianTong = servce.getString(sql6.toString());
				int liantong_fee = 0;
				if(null != retPointLianTong && !"".equals(retPointLianTong) && null != liantongFee && !"".equals(liantongFee)){
					BigDecimal bigDecimal = new BigDecimal(retPointYiDong);
					liantong_fee = bigDecimal.multiply(new BigDecimal(liantongFee)).divide(new BigDecimal(100)).intValue();
				}
				//查询用户阶梯佣金比例(根据用户区域查询佣金)-电信返点
				StringBuffer sql7 = new StringBuffer();
				sql7.append("select a.sc_traderpercent from sys_commission a ");
				sql7.append(" LEFT JOIN sys_commissiongroup b on a.sg_id = b.sg_id");
				sql7.append(" WHERE a.sc_tradertype = 3 AND b.sa_id = "+areaId+" AND b.srt_id = 5 AND a.sc_traderbegin<"+yidongFee+" AND a.sc_traderend > "+yidongFee+"");
				String retPointDianXin = servce.getString(sql7.toString());
				int dianxin_fee = 0;
				if(null != retPointDianXin && !"".equals(retPointDianXin) && null != dianxinFee && !"".equals(dianxinFee)){
					BigDecimal bigDecimal = new BigDecimal(retPointYiDong);
					dianxin_fee = bigDecimal.multiply(new BigDecimal(dianxinFee)).divide(new BigDecimal(100)).intValue();
				}
				//记录统计信息
				StringBuffer sql8 = new StringBuffer();
				sql8.append("insert into total_jieti_account(trade_date,user_id,fundacct,yidong_total,litong_total,dianxing_total,yidong_fee,liantong_fee,dianxin_fee,state) values(" +
						"'"+dateyyyyMM+"',"+userId+",'"+fundAcct+"',"+yidongFee+","+liantongFee+","+dianxinFee+","+yidong_fee+","+liantong_fee+","+dianxin_fee+",0)");
				servce.update(sql8.toString());
			}
		} catch (SQLException e) {
			result = 1;
			//异常处理
			StringBuffer sql8 = new StringBuffer();
			sql8.append("insert into total_jieti_account(trade_date,user_id,fundacct,yidong_total,litong_total,dianxing_total,yidong_fee,liantong_fee,dianxin_fee,state) values(" +
					"'"+dateyyyyMM+"',"+userId+",'"+fundAcct+"',0,0,0,0,0,0,2)");
			servce.update(sql8.toString());
			e.printStackTrace();
		}
		
		return result;
	}

}
