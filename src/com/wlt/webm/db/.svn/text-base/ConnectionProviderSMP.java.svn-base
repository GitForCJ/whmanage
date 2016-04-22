package com.wlt.webm.db;


import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.dbcp.BasicDataSource;

import com.commsoft.epay.util.logging.Log;

/**
 * 数据库连接类
 * 创建日期：2014-06-17
 * Company：深圳市万恒科技有限公司
 * Copyright: Copyright (c) 2014
 * @author caiSJ
 */
public class ConnectionProviderSMP {
	private static BasicDataSource datasource = DataSourceProviderSMP.getInstance().getDataSource();
	
	/**
	 * 从连接池中获取一个数据库连接
	 * @return Connection 数据库连接
	 * @throws 如果连接都已经在使用,此处会返回一个SQLException,
	 * 应用应该捕获这个异常,以便控制流程
	 */
	public static Connection getConnection() throws SQLException {
		Connection conn = null;
		
		conn = datasource.getConnection();
		
		return conn;	
	}
	
	/**
	 * 释放一个连接还给数据库连接池
	 * @param conn 需要释放的数据库连接
	 */
	public static void freeConnection(Connection conn) {
		if(conn != null) {
			try {
				conn.close();
			}
			catch (SQLException e) {
				Log.error(ConnectionProviderSMP.class.getName(), e);
			}
		}
	}
}
