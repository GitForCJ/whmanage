package com.wlt.webm.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.wlt.webm.pccommon.Constants;

public class ConnectionFactory
{
    /**
     * ����ʵ��
     */
    private static ConnectionFactory factory = new ConnectionFactory();

    /**
     * ���ݿ�����
     */
    private DBConfig config = DBConfig.getInstance();

    /**
     * ˽�й��췽�����������ⲿʵ������
     */
    private ConnectionFactory()
    {
        try
        {
            Class.forName(config.getSmpDriver());
        }
        catch (ClassNotFoundException e)
        {
            throw new RuntimeException("�޷��ҵ����ݿ�������" + config.getSmpDriver());
        }
    }

    /**
     * ������ݿ�����
     * @param dbName ������
     * @return ���ݿ�����
     * @throws SQLException
     */
    public static Connection getConnection(String dbName) throws SQLException
    {
        DBConfig config = factory.config;

        if (dbName.equals(Constants.DBNAME_SMP))
        {
            return DriverManager.getConnection(config.getSmpUrl(), config.getSmpUser(), config.getSmpPassword());
//        	return DBConnection.getConnection(1);
        }
        else if (dbName.equals(Constants.DBNAME_SCP))
        {
            return DriverManager.getConnection(config.getScpUrl(), config.getScpUser(), config.getScpPassword());
//        	return DBConnection.getConnection(0);
        }
        throw new IllegalArgumentException("���ݿ��������ڣ�" + dbName);
    }
}
