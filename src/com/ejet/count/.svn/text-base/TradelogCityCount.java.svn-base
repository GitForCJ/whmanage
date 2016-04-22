package com.ejet.count;

import java.awt.geom.Area;
import java.util.List;
import java.util.Map;

import com.ejet.common.Common;
import com.ejet.common.struts.bean.Counter;
import com.ejet.util.DataUtil;
import com.ejet.util.Tools;
import com.ejet.util.format.Format;
import com.ejet.util.format.Formatter;
import com.wlt.webm.pccommon.Constants;
import com.wlt.webm.pccommon.TaskChecker;
import com.wlt.webm.scpdb.DBService;

/**
 * 地市交易统计
 * Company：深圳市###EJET###有限公司
 * Copyright: Copyright (c) 2009
 * @author ejet.shen
 * 修改人：Ejet.Shen
 * 修改日期：2009-10-30
 * version V2.2.2.13
 */
public class TradelogCityCount extends Counter {
	/**
	 * @return 检测汇总日志
	 * @throws Exception
	 */
	public String getAlertMessage() throws Exception {
        return TaskChecker.checkDayTask(TaskChecker.STAT, "em_termtrade_", form.getStartdate(), form.getEnddate());
	}

	/**
	 * @return 获取报表列头标题
	 * @throws Exception
	 */
	public String[][] getColTitles() throws Exception {
		String[][] coltitles = {{"地市","业务类型","交易状态","应收金额(元)","实收金额(元)","交易笔数(笔)"}};
		return coltitles;
	}

	/**
	 * @return 获取报表标题
	 * @throws Exception
	 */
	public String getTitle() throws Exception {
		String title =  form.getStartdate()+"至"+form.getEnddate()+" 地市交易统计";
		return title;
	}
	
	/**
	 * @return 获取报表主体内容
	 * @throws Exception
	 */
	public Object getBodyData() throws Exception {
		String city = form.getCity();
		String service = form.getService();
		String tradetype = form.getTradetype();
		String startdate = Formatter.format(form.getStartdate(), Formatter.YMD);
		String enddate = Formatter.format(form.getEnddate(), Formatter.YMD);

		List monthList = Tools.getAllMonth(startdate, enddate);
		StringBuffer sql = new StringBuffer();
		DBService db = new DBService(Constants.DBNAME_SMP);
		db.setAutoCommit(false);
		Map rsMap = null;
		try{
			for(int i=0; i<monthList.size(); i++){
				String str = (String) monthList.get(i);
				sql.append("select b.areaname, c.name, a.tradeplat, a.fee, a.duefee, a.tradenum")
					.append(" from 	em_termtrade_").append(str.substring(2, 6)).append(" a, em_area b, em_service c")
					.append(" where a.areacode=b.areacode and a.service=c.code")
					.append(" and a.district in(").append(" ").append(")");//Area.getDistrictByLoginSMP(user.getLogin())
				if(city!=null && !city.equals("")){
					sql.append(" and b.areaid=").append(city);
				}
				if(service!=null && !service.equals("")){
					sql.append(" and a.service=").append(service);
				}
				if(tradetype!=null && !tradetype.equals("")){
					sql.append(" and a.paytype=").append(tradetype);
				}
				if(!form.getState().equals("")){
					sql.append(" and a.tradeplat=").append(form.getState());
				}
		        if (form.getStartdate().length() != 0 ) {		            
		            sql.append(" and a.day>='").append(startdate).append("'");
		        }
		        if (form.getEnddate().length() != 0 ) {		         
		            sql.append(" and a.day<='").append(enddate).append("'");
		        }
				if(i!=monthList.size()-1){
					sql.append(" union all ");
				}
			}
			sql.append(" into temp aa with no log");
			db.update(sql.toString());
			
			sql.delete(0, sql.length());
			sql.append("select areaname, name, tradeplat, sum(fee), sum(duefee), sum(tradenum) from aa")
				.append(" group by areaname, name, tradeplat")
				.append(" order by areaname, name, tradeplat");
			List rsList = db.getList(sql.toString());
			db.commit();
			if(rsList.size()>0){
			Map tradeStateMap = Common.getTradeStateMap();
				for(int i=0; i<rsList.size(); i++){
					String[] str = (String[]) rsList.get(i);
				    str[2] = (String)tradeStateMap.get(str[2]); 
				}

				rsMap = DataUtil.toNestedStringsListMap(2, rsList);
				rsMap = DataUtil.sumColToRow(rsMap, 1);
				Format[] format = {null,Formatter.D100F2, Formatter.D100F2,Formatter.INT}; 
				rsMap = Formatter.format(rsMap, format);	
			}
		}catch(Exception e){
			db.rollback();
            throw e;
		}finally{
			if(db!=null)
				db.close();
		}
		

		return rsMap;
	}
}
