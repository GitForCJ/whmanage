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
public class DataSourceProviderSMP {
	private static DataSourceProviderSMP dataSourceProvider = null;
	private static DBConfig config = DBConfig.getInstance();
	private static BasicDataSource datasource = null;
	
	/**
	 * 私有构造方法
	 */
	private DataSourceProviderSMP() {
		Properties prop = new Properties();
		prop.setProperty("driverClassName", config.getSmpDriver().trim());
		prop.setProperty("url", config.getSmpUrl().trim());
		prop.setProperty("username", config.getSmpUser().trim());
		prop.setProperty("password", config.getSmpPassword().trim());
		
		prop.setProperty("maxActive", config.getMaxActiveSMP().trim());
		prop.setProperty("initialSize ",config.getInitialSizeSMP().trim());
		prop.setProperty("maxWait", config.getMaxWaitSMP().trim());
		prop.setProperty("testOnReturn", config.getTestOnReturnSMP().trim());
		prop.setProperty("testOnBorrow", config.getTestOnBorrowSMP().trim());
		prop.setProperty("timeBetweenEvictionRunsMillis", config.getTimeBetweenEvictionRunsMillisSMP().trim());
		prop.setProperty("numTestsPerEvictionRun", config.getNumTestsPerEvictionRunSMP().trim());
		prop.setProperty("minEvictableIdleTimeMillis", config.getMinEvictableIdleTimeMillisSMP().trim());
		prop.setProperty("testWhileIdle", config.getTestWhileIdleSMP().trim());
		prop.setProperty("defaultReadOnly", config.getDefaultReadOnlySMP().trim());
		prop.setProperty("defaultAutoCommit", config.getDefaultAutoCommitSMP().trim());
		prop.setProperty("validationQuery", config.getValidationQuerySMP().trim());
		prop.setProperty("poolPreparedStatements", config.getPoolPreparedStatementsSMP().trim());
		prop.setProperty("maxOpenPreparedStatements", config.getMaxOpenPreparedStatementsSMP().trim());
		
		try {
			datasource = (BasicDataSource)BasicDataSourceFactory.createDataSource(prop);
		}
		catch (Exception e) {
			Log.error(DataSourceProviderSMP.class.getName(), e);
		}
	}
	
	/**
	 * 得到全局唯一的数据库连接池类
	 * @return 返回DataSourceProvider的唯一实例
	 */
	public static DataSourceProviderSMP getInstance() {
		if(dataSourceProvider == null) {
			dataSourceProvider = new DataSourceProviderSMP();
		}
		
		return dataSourceProvider;
	}
	
	/**
	 * 返回DataSourceProvider中生成的BasicDataSource
	 * @return 返回BasicDataSource的唯一实例
	 */
	public BasicDataSource getDataSource() {
		return datasource;
	}
}
