package com.wlt.webm.pccommon;

import java.sql.SQLException;
import java.util.List;

import com.wlt.webm.db.DBToolSCP;
import com.wlt.webm.scpdb.DBService;


/**
 * XML工具类<br>
 */
public class XMLBean {
	
    /**
     * 获取返销业务类型
     * @param dbName 数据库
     * @param operation 1 返销 2 退款 3 冲正
     * @return 返销业务类型列表
     * @throws Exception
     */
    public List getTradeBackServiceList(String dbName, String operation) throws Exception
    {
    	DBService db = null;
    	List list = null;
    	try{
    		
    		String sql = "";
        	if(dbName.equals(Constants.DBNAME_SCP)){
        		db = new DBService(Constants.DBNAME_SCP);
        		if(operation.equals("1")){
        			sql = "select a.code,a.name from ec_service a,ec_returnservice b where a.code=b.service  and b.type=1";	
        		}else if(operation.equals("2")){
        			sql = "select a.code,a.name from ec_service a,ec_returnservice b where a.code=b.service  and b.type=2";	
        		}else if(operation.equals("3")){
        			sql = "select a.code,a.name from ec_service a,ec_returnservice b where a.code=b.service  and b.type=3";	
        		}else{
        			sql = "select code,name from ec_service";	
        		}
        		
        	}else {
        		db = new DBService(Constants.DBNAME_SMP);
        		if(operation.equals("1")){
        			sql = "select a.code,a.name from em_service a,em_returnservice b where a.code=b.service  and b.type=1";	
        		}else if(operation.equals("2")){
        			sql = "select a.code,a.name from em_service a,em_returnservice b where a.code=b.service  and b.type=2";	
        		}else if(operation.equals("3")){
        			sql = "select a.code,a.name from em_service a,em_returnservice b where a.code=b.service  and b.type=3";	
        		}else{
        			sql = "select code,name from em_service";	
        		}
        	}	
        	list = db.getList(sql);
    	}catch(Exception e){
    		throw e;
    	}finally{
    		db.close();
    	}
    	return list;  
    }
    
    /**
     * 获得业务类型信息列表
     * @param num 1 获取所有的业务类型
     * 			   2 获取固话和G网缴费业务类型
     * 			   3 获取固话、G网缴费、电子售卡业务类型
     *             4 获取固话、G网缴费、电子售卡、固网订单支付业务类型
     *             5 获取固话、G网缴费、宽带缴费业务类型
     * @return List(String[])业务类型编号和名称列表
     * @throws SQLException
     * 01001 电信缴费
		03002 电信售卡
		03004 售游戏卡
		05001 宽带办理
		05002 移动业务办理

     */
    public List getServiceList(int num) throws SQLException
    {
    	if(num==1){
    		return DBToolSCP.getList("select code,name from ec_service order by code");
    	}else if(num==2){
    		return DBToolSCP.getList("select code,name from ec_service where code in('01001','03002') order by code");
    	}else if(num==3){
    		return DBToolSCP.getList("select code,name from ec_service where code in('01001','03002','03004') order by code");
    	}else if(num == 5){
    		return DBToolSCP.getList("select code,name from ec_service where code in('01001','05001','05002') order by code");
    	}else if(num == 10){
    		return DBToolSCP.getList("select code,name from ec_service where code like '010%' order by code");
    	}else if(num == 30){
    		return DBToolSCP.getList("select code,name from ec_service where code like '030%' order by code");
    	}else if(num == 50){
    		return DBToolSCP.getList("select code,name from ec_service where code like '050%' order by code");
    	}else {
    		return DBToolSCP.getList("select code,name from ec_service where code in('01001','03002','03004','05001','05002') order by code");
    	}
    }
    
    /**
     * 查询交易方式列表
     * @return List(String[])交易方式列表
     * @throws SQLException
     */
    public List tradeTypeList() throws SQLException
    {
        String sql = "select id,name from ec_tradetype order by id";
        return DBToolSCP.getList(sql.toString());
    }
    /**
     * 获得配置的退费返销的业务类型列表
     * @return List(String[])配置的退费返销的业务类型列表
     * @throws SQLException
     */
    public List getReturnBackServiceList() throws SQLException
    {
        return DBToolSCP.getList("select code,name from ec_service where code in(select service from ec_returnservice)");
    }
}
