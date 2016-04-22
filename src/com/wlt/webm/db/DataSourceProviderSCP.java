package com.wlt.webm.db;

import java.util.Properties;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.dbcp.BasicDataSourceFactory;

import com.commsoft.epay.util.config.ConfigParser;
import com.commsoft.epay.util.config.Configuration;
import com.commsoft.epay.util.logging.Log;

/**
 * 数据库连接类
 * 创建日期：2014-06-17
 * Company：深圳市万恒科技有限公司
 * Copyright: Copyright (c) 2014
 * @author caiSJ
 */
public class DataSourceProviderSCP {
	private static DataSourceProviderSCP dataSourceProvider = null;
	private static DBConfig config = DBConfig.getInstance();
	private static BasicDataSource datasource = null;
	
	/**
	 * 私有构造方法
	 */
	private DataSourceProviderSCP() {
		Properties prop = new Properties();
		prop.setProperty("driverClassName", config.getScpDriver().trim());
		prop.setProperty("url", config.getScpUrl().trim());
		prop.setProperty("username", config.getScpUser().trim());
		prop.setProperty("password", config.getScpPassword().trim());
		
		prop.setProperty("maxActive", config.getMaxActive().trim());
		prop.setProperty("initialSize ",config.getInitialSize().trim());
		prop.setProperty("maxWait", config.getMaxWait().trim());
		prop.setProperty("testOnReturn", config.getTestOnReturn().trim());
		prop.setProperty("testOnBorrow", config.getTestOnBorrow().trim());
		prop.setProperty("timeBetweenEvictionRunsMillis", config.getTimeBetweenEvictionRunsMillis().trim());
		prop.setProperty("numTestsPerEvictionRun", config.getNumTestsPerEvictionRun().trim());
		prop.setProperty("minEvictableIdleTimeMillis", config.getMinEvictableIdleTimeMillis().trim());
		prop.setProperty("testWhileIdle", config.getTestWhileIdle().trim());
		prop.setProperty("defaultReadOnly", config.getDefaultReadOnly().trim());
		prop.setProperty("defaultAutoCommit", config.getDefaultAutoCommit().trim());
		prop.setProperty("validationQuery", config.getValidationQuery().trim());
		prop.setProperty("poolPreparedStatements", config.getPoolPreparedStatements().trim());
		prop.setProperty("maxOpenPreparedStatements", config.getMaxOpenPreparedStatements().trim());
		prop.setProperty("minIdle", config.getMinIdle().trim());
		prop.setProperty("removeAbandonedTimeout", config.getRemoveAbandonedTimeout().trim());
		prop.setProperty("removeAbandoned", config.getRemoveAbandoned().trim());
		prop.setProperty("whenExhaustedAction", "2");
		try {
			datasource = (BasicDataSource)BasicDataSourceFactory.createDataSource(prop);
		}
		catch (Exception e) {
			Log.error(DataSourceProviderSCP.class.getName(), e);
		}
	}
	
	/**
	 * 得到全局唯一的数据库连接池类
	 * @return 返回DataSourceProvider的唯一实例
	 */
	public static DataSourceProviderSCP getInstance() {
		if(dataSourceProvider == null) {
			dataSourceProvider = new DataSourceProviderSCP();
		}
		
		return dataSourceProvider;
	}
	
	/**
	 * 返回DataSourceProvider中生成的BasicDataSource
	 * @return 返回BasicDataSource的唯一实例
	 */
	public BasicDataSource getDataSource() {
		System.out.println("scp connect msg->max connect:"+datasource.getMaxActive()+
				"  min idle connect:"+datasource.getNumIdle()+"  init connect:"+datasource.getInitialSize()
				+"   current connect:"+datasource.getNumActive());
		return datasource;
	}
}
