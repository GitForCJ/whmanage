package com.wlt.webm.scputil;

import java.sql.*;

import com.commsoft.epay.util.dbcp.ConnectionProvider;
import com.commsoft.epay.util.logging.Log;

/**
 * 数据库连接类
 */
public class DBConnection {

	/**
	 * 获得一个数据库连接
	 * @return Connection
	 * @throws Exception 
	 */
	public static Connection getConnection() throws Exception{
		Connection con = null;
		
		try
		{
			con = ConnectionProvider.getConnection();
		}catch(SQLException sex)
		{
			Log.error("从数据库连接池获取数据库连接出错:",sex);
			throw sex;
		}
		
		return con;
	}
	
	/**
	 * 释放数据库连接
	 * @param conn 数据库连接
	 */
	public static void close(Connection conn)
	{
		ConnectionProvider.freeConnection(conn);
	}
}
