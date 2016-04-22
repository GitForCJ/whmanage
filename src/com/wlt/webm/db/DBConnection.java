package com.wlt.webm.db;

import java.sql.*;

import com.alibaba.druid.pool.DruidPooledConnection;
import com.commsoft.epay.util.logging.Log;

/**
 * ���ݿ�������
 * �������ڣ�2014-06-17
 * Company�����������Ƽ����޹�˾
 * Copyright: Copyright (c) 2014
 * @author caiSJ
 */
public class DBConnection{

	/**
	 * ���һ�����ݿ�����
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
			Log.error("DBConnection��"+type+" ���ݿ����ӳػ�ȡ���ݿ����ӳ���:",sex);
		}
		
		return con;
	}
	
	/**
	 * �ͷ����ݿ�����
	 * @param conn ���ݿ�����
	 */
	public static void close(Connection conn)
	{
		ConnectionProviderSCP.freeConnection(conn);
	}
}
