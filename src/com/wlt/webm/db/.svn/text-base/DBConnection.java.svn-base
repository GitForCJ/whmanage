package com.wlt.webm.db;

import java.sql.*;

import com.alibaba.druid.pool.DruidPooledConnection;
import com.commsoft.epay.util.logging.Log;

/**
 * 数据库连接类
 * 创建日期：2014-06-17
 * Company：深圳市万恒科技有限公司
 * Copyright: Copyright (c) 2014
 * @author caiSJ
 */
public class DBConnection{

	/**
	 * 获得一个数据库连接
	 * @return Connection
	 * @throws Exception 
	 */
	public static Connection getConnection(int type){
		DruidPooledConnection con = null;
		try
		{
			if(type==0){
				//con = ConnectionProviderSCP.getConnection();
				 DbPoolConnection dbp = DbPoolConnection.getInstance();  
				 con = dbp.getConnection();  
			}else if(type==1){
				//con = ConnectionProviderSMP.getConnection();
				DbPoolConnection dbp = DbPoolConnection.getInstance();  
				 con = dbp.getConnection(); 
			}

		}catch(Exception sex)
		{
			Log.error("DBConnection从"+type+" 数据库连接池获取数据库连接出错:",sex);
		}
		
		return con;
	}
	
	/**
	 * 释放数据库连接
	 * @param conn 数据库连接
	 */
	public static void close(Connection conn)
	{
		ConnectionProviderSCP.freeConnection(conn);
	}
}
