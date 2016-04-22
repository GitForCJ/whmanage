package com.wlt.webm.scputil;

import java.sql.*;

import com.commsoft.epay.util.dbcp.ConnectionProvider;
import com.commsoft.epay.util.logging.Log;

/**
 * ���ݿ�������
 */
public class DBConnection {

	/**
	 * ���һ�����ݿ�����
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
			Log.error("�����ݿ����ӳػ�ȡ���ݿ����ӳ���:",sex);
			throw sex;
		}
		
		return con;
	}
	
	/**
	 * �ͷ����ݿ�����
	 * @param conn ���ݿ�����
	 */
	public static void close(Connection conn)
	{
		ConnectionProvider.freeConnection(conn);
	}
}
