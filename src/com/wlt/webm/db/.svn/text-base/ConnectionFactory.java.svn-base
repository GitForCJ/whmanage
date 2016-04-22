package com.wlt.webm.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.wlt.webm.pccommon.Constants;

public class ConnectionFactory
{
    /**
     * 工厂实例
     */
    private static ConnectionFactory factory = new ConnectionFactory();

    /**
     * 数据库配置
     */
    private DBConfig config = DBConfig.getInstance();

    /**
     * 私有构造方法，不允许外部实例化。
     */
    private ConnectionFactory()
    {
        try
        {
            Class.forName(config.getSmpDriver());
        }
        catch (ClassNotFoundException e)
        {
            throw new RuntimeException("无法找到数据库驱动：" + config.getSmpDriver());
        }
    }

    /**
     * 获得数据库连接
     * @param dbName 数据名
     * @return 数据库连接
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
        throw new IllegalArgumentException("数据库名不存在：" + dbName);
    }
}
