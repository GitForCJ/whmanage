package com.ejet.count;

import java.util.List;
import java.util.Map;

import com.ejet.common.Common;
import com.ejet.common.struts.bean.Counter;
import com.ejet.util.DataUtil;
import com.ejet.util.SQLUtil;
import com.ejet.util.Tools;
import com.ejet.util.format.Format;
import com.ejet.util.format.Formatter;
import com.ibm.wsdl.Constants;
import com.wlt.webm.pccommon.TaskChecker;
import com.wlt.webm.scpdb.DBService;


/**
 * 交易日志代理商统计
 * Company：深圳市###EJET###有限公司
 * Copyright: Copyright (c) 2009
 * version 2.0.0.0
 * @author ###ejet###
 */
public class TradeLogAgentCount extends Counter
{

    /**
     * @return 检测任务日志
     * @throws Exception
     */
    public String getAlertMessage() throws Exception
    {
        return TaskChecker.checkDayTask(TaskChecker.STAT, "em_agenttrade_", form.getStartdate(), form.getEnddate());
    }

    /**
     * @return 获得报表标题
     */
    public String getTitle()
    {
		String title =  form.getStartdate()+"至"+form.getEnddate()+" 代理商交易统计";
        return title;
    }

    /**
     * @return 获得列标题
     */
    public String[][] getColTitles()
    {
        return new String[][] { { "代理商", "业务类型", "交易状态", "应收金额", "实收金额", "交易笔数" } };
    }

    /**
     * @return 报表数据
     * @throws Exception
     */
    public Object getBodyData() throws Exception
    {
    	String startDate = Formatter.format(form.getStartdate(), Formatter.YMD);
        String endDate = Formatter.format(form.getEnddate(), Formatter.YMD);
		List monthList = Tools.getAllMonth(startDate, endDate);
        DBService db = new DBService();
		Map rsMap = null;
		try{
			db.setAutoCommit(false);
	        StringBuffer sql = new StringBuffer();
	        for(int i=0; i<monthList.size(); i++){
	        	String str = (String) monthList.get(i);
	        	 sql.append("select b.agentname,c.name,a.tradeplat,a.fee,a.duefee,a.tradenum")
	             	.append(" from em_agenttrade_").append(str.substring(2, 6)).append(" a,em_agent b,em_service c,em_area d")
	             	.append(" where a.agentid=b.agentid and a.service=c.code")
	             	.append(" and a.areacode=d.areacode");
	
	        	 SQLUtil.addEqual(sql, "a.agentid", form.getAgent());
			     SQLUtil.addEqual(sql, "d.areaid", form.getCity());
			     SQLUtil.addEqual(sql, "a.district", form.getDistrict());
			     SQLUtil.addGreatEqual(sql, "a.day", startDate);
			     SQLUtil.addLessEqual(sql, "a.day", endDate);
			     SQLUtil.addEqual(sql, "a.service", form.getService());
			     SQLUtil.addEqual(sql, "a.paytype", form.getTradetype());
			     SQLUtil.addEqual(sql, "a.tradeplat", form.getState());
			     if(i!=monthList.size()-1){
						sql.append(" union all ");
				 }
	        }
	        sql.append(" into temp aa with no log");
	        
			db.update(sql.toString());
			
			sql.delete(0, sql.length());
			sql.append("select agentname,name,tradeplat,sum(fee),sum(duefee),sum(tradenum) from aa")
			   .append(" group by agentname,name,tradeplat ")
	        	.append(" order by agentname,name,tradeplat");
	
	        List list = db.getList(sql.toString());
	        db.commit();    
	        if(list.size()>0){
	        	Map tradeStateMap = Common.getTradeStateMap();
				for(int i=0; i<list.size(); i++){
					String[] str = (String[]) list.get(i);
				    str[2] = (String)tradeStateMap.get(str[2]); 
				}
	 	        rsMap = DataUtil.toNestedStringsListMap(2, list);
 	        rsMap = DataUtil.sumColToRow(rsMap, 1); 
 	        Format[] params = {null, Formatter.D100F2, Formatter.D100F2, Formatter.INT}; 
 	        rsMap = Formatter.format(rsMap, params);
	        }
		}catch(Exception e){
			db.rollback();
            throw e;
		}finally{
			if(null!=db)
				db.close();
		}
		
        return rsMap;
    }

}
