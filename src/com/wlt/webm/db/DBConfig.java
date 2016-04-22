package com.wlt.webm.db;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Properties;

import com.wlt.webm.pccommon.ProjectContext;
import com.wlt.webm.util.Tools;

/**
 * 数据库配置文件类。
 * 该类采用单例模式，且提供<code>detect()</code>方法
 * 以检测文件是否被修改并重新读取配置，该类是线程安全的。<br>
 */
public class DBConfig
{
    /**
     * 数据库配置对象实例
     */
    private static DBConfig config = new DBConfig();

    /**
     * 数据库配置文件
     */
    private File file;

    /**
     * 配置文件的最后一次修改时间
     */
    private long lastModified;

    //---------数据库配置文件属性----------

    /**
     * SMP数据库驱动
     */
    private String smpDriver;

    /**
     * SMP数据库URL
     */
    private String smpUrl;

    /**
     * SMP数据库用户名
     */
    private String smpUser;

    /**
     * SMP数据库密码
     */
    private String smpPassword;

    /**
     * SCP数据库驱动
     */
    private String scpDriver;

    /**
     * SCP数据库URL
     */
    private String scpUrl;

    /**
     * SCP数据库用户名
     */
    private String scpUser;
    
    /**
     * 金额最小限制
     */
    private String permitFee;

    /**
     * SCP数据库密码
     */
    private String scpPassword;
    
    /**
     * 缴费上限
     */
    private String upbound;
    /**
     * 卡信息分隔符
     */
    private String compartSign;
    /**
     * 运营公司
     */
    private String companyName;
    /**
     * 缴费金额限制
     */
    private String payState;
    
    /**
     * 是否允许低于最小金额限制时进行银行转账，0表示允许，非0表示不允许。
     */
    private String minLimitState;
    /**
     * 银行转账最小金额限制，单位：元
     */
    private String minTransferFee;
    
    /**
     * 低于最小金额限制时进行银行转账所需手续费，单位：元
     */
    private String handlingCharge;
    /**
     * 银行转账最大金额限制，单位：元
     */
    private String maxTransferFee;

    //财付通转账
    /**
     *  新生转账最大金额限制，单位：元
     */
    private String cftMaxFee;
    /**
     * 新生转账最小金额限制，单位：元
     */
    private String cftMinFee;
    /**
     * 低于最小金额限制时进行新生转账所需手续费，单位：元
     */
    private String cftHandleCharge;
    /**
     * Q币折扣
     */
    private String qbRebate;
    
    
    /**
     * 绑定手机号
     */
    private String SUPHONE;
    /**
     * 绑定IP
     */
    private String IP;


	/**
     * 端口
     */
    private String PORT;
    /**
     * 登陆密码
     */
    private String SIGNPWD;
    /**
     * 缴费密码
     */
    private String PAYPWD;
    /**
     * 初始流水号
     */
    private String BeginSeq;
    /**
     * 广东电信本身佣金比例
     */
    private String gdUserRebate;
    /**
     * 广东电信父节点佣金比例
     */
    private String gdParentRebat;
    /**
     * 其他省份佣金比例
     */
    private String otherRebate;
    
    /**
     * 特殊佣金配置
     */
    private String SpecialCommission;
    /**
     * 揭阳潮汕的移动区号
     */
    private String jcZone;
    /**
     * 揭阳潮汕的直营佣金
     */
    private String jcydzy;
    /**
     * 揭阳潮汕的非直营代办点佣金
     */
    private String jcydfzy_d;
    /**
     * 揭阳潮汕的非直营代理商佣金
     */
    private String jcydfzy_s;
    /**
     * 交通罚代办费
     */
    private HashMap<String, Integer> jtfkfee;
    /**
     * 中石化交通罚款代办费
     */
    private int jtfkzsh;
    /**
     * 交通罚款代办费浮动金额
     */
    private int jtfkfloat;
    /**
     * 交通罚款默认代办费
     */
    private int jtfkdefault;
    /**
     * 广东联通本身佣金比例
     */
    private String gdltUserRebate;
    /**
     * 广东联通父节点佣金比例
     */
    private String gdltParentRebat;
    
    /**
     * 佛山移动 佣金
     */
    private String fosanMoble;
    
    /**
     * 联通流量佣金
     */
    private String liantong_flow;
   
    /**
     * 移动流量佣金
     */
    private String yidong_flow;
    
    /**
     * 电信流量佣金
     */
    private String dianxin_flow;

    /**
     * SCP连接池信息
     */
	private String maxActive = "50";
	private String maxWait = "30000";
	private String maxIdle = "20";
	private String initialSize = "10";
	private String testOnBorrow = "true";
	private String testOnReturn = "true";
	private String timeBetweenEvictionRunsMillis = "360000";
	private String numTestsPerEvictionRun = "5";
	private String minEvictableIdleTimeMillis = "5000";
	private String testWhileIdle = "true";
	private String validationQuery = "";
	private String defaultReadOnly = "false";
	private String defaultAutoCommit = "true";
	private String poolPreparedStatements = "true";
	private String maxOpenPreparedStatements = "-1";
	private String minIdle;
	private String removeAbandonedTimeout;
	private String removeAbandoned;
    /**
     * portal-1连接池信息
     */
	private String maxActiveSMP = "50";
	private String maxWaitSMP = "30000";
	private String maxIdleSMP = "20";
	private String initialSizeSMP = "10";
	private String testOnBorrowSMP = "true";
	private String testOnReturnSMP = "true";
	private String timeBetweenEvictionRunsMillisSMP = "360000";
	private String numTestsPerEvictionRunSMP = "5";
	private String minEvictableIdleTimeMillisSMP = "5000";
	private String testWhileIdleSMP = "true";
	private String validationQuerySMP = "";
	private String defaultReadOnlySMP = "false";
	private String defaultAutoCommitSMP = "true";
	private String poolPreparedStatementsSMP = "true";
	private String maxOpenPreparedStatementsSMP = "-1";
    /**
     * 构造一个数据库配置类
     */
    public DBConfig()
    {
        ProjectContext context = ProjectContext.getInstance();
        file = new File(context.getProjectConfigFile());
        lastModified = file.lastModified();
        readConfig();
    }

    /**
     * 获得数据库配置实例
     * @return 数据库配置实例
     */
    public static DBConfig getInstance()
    {
        return config;
    }

    /**
     * 读取配置文件并设置属性
     */
    @SuppressWarnings("unchecked")
	private void readConfig()
    {
        Properties p = new Properties();
        FileInputStream in = null;

        try
        {
            in = new FileInputStream(file);
            p.load(in);
        }
        catch (FileNotFoundException e)
        {
            throw new RuntimeException("找不到配置文件：" + file.getParent());
        }
        catch (IOException e)
        {
            throw new RuntimeException("无法读取配置文件：" + file.getPath());
        }
        finally
        {
            closeInputStream(in);
        }

        this.smpDriver = p.getProperty("SMPDriver");
        this.smpUrl = p.getProperty("SMPUrl");
        this.smpUser = p.getProperty("SMPUser");
        this.smpPassword = p.getProperty("SMPPassword");

        this.scpDriver = p.getProperty("SCPDriver");
        this.scpUrl = p.getProperty("SCPUrl");
        this.scpUser = p.getProperty("SCPUser");
        this.scpPassword = p.getProperty("SCPPassword");
        this.upbound = p.getProperty("upbound");
        this.permitFee = p.getProperty("permitFee");
        
        this.compartSign = p.getProperty("compart_sign");
        this.companyName = Tools.decode(p.getProperty("companyName"));
        this.payState = p.getProperty("payState");
        //银联转账金额上下限
        this.minLimitState = p.getProperty("minLimitState");
        
        this.minTransferFee = p.getProperty("minTransferFee");
        this.handlingCharge = p.getProperty("handlingCharge");
        this.maxTransferFee = p.getProperty("maxTransferFee");
        
        
        //财付通转账
        this.cftMaxFee=p.getProperty("cftgMaxFee");
        this.cftMinFee=p.getProperty("cftMinFee");
        this.cftHandleCharge=p.getProperty("cftHandleCharge");
        
        //Q币折扣
        this.qbRebate =p.getProperty("qbRebate");
        
        //广东移动
        this.SUPHONE =p.getProperty("SUPHONE");
        this.IP =p.getProperty("IP");
        this.PORT =p.getProperty("PORT");
        this.SIGNPWD =p.getProperty("SIGNPWD");
        this.PAYPWD =p.getProperty("PAYPWD");
        this.BeginSeq =p.getProperty("BeginSeq");
        //佣金折扣率
        this.gdUserRebate=p.getProperty("gdUserRebate");
        this.gdParentRebat=p.getProperty("gdParentRebat");
        this.otherRebate=p.getProperty("otherRebate");
        
        //特殊佣金配置
        this.SpecialCommission=p.getProperty("SpecialCommission");
        
        this.jcZone=p.getProperty("jcZone");
        this.jcydzy=p.getProperty("jcydzy");
        this.jcydfzy_d=p.getProperty("jcydfzy_d");
        this.jcydfzy_s=p.getProperty("jcydfzy_s");
        
        
        //交通罚款手续费配置
        this.jtfkfee=getjtfkFee(p.getProperty("jtfkfee"));
        this.jtfkzsh=Integer.parseInt(p.getProperty("jtfkzsh"));
        this.jtfkfloat=Integer.parseInt(p.getProperty("jtfkfloat"));
        this.jtfkdefault=Integer.parseInt(p.getProperty("jtfkdefault"));
        
        //广东联通佣金配置
        this.gdltUserRebate=p.getProperty("gdltUserRebate");
        this.gdltParentRebat=p.getProperty("gdltParentRebat");
        
        //佛山移动
        this.fosanMoble=p.getProperty("fosanMoble");
        this.liantong_flow=p.getProperty("liantong_flow");
        this.yidong_flow=p.getProperty("yidong_flow");
        this.dianxin_flow=p.getProperty("dianxin_flow");
        //SCP数据库连接池配置
		this.maxActive=p.getProperty("MaxActive");
		this.initialSize=p.getProperty("InitialSize");
		this.maxWait=p.getProperty("MaxWait");
		this.testOnReturn=p.getProperty("TestOnReturn");
		this.testOnBorrow=p.getProperty("TestOnBorrow");
		this.testWhileIdle=p.getProperty("TestWhileIdle");
		this.timeBetweenEvictionRunsMillis=p.getProperty("TimeBetweenEvictionRunsMillis");
		this.numTestsPerEvictionRun=p.getProperty("NumTestsPerEvictionRun");
		this.minEvictableIdleTimeMillis=p.getProperty("MinEvictableIdleTimeMillis");
		this.defaultReadOnly=p.getProperty("DefaultReadOnly");
		this.defaultAutoCommit=p.getProperty("DefaultAutoCommit");
		this.validationQuery=p.getProperty("ValidationQuery");
		this.poolPreparedStatements=p.getProperty("PoolPreparedStatements");
		this.maxOpenPreparedStatements=p.getProperty("MaxOpenPreparedStatements");
		this.maxIdle=p.getProperty("MaxIdle");
		this.minIdle=p.getProperty("minIdle");
		this.removeAbandonedTimeout=p.getProperty("removeAbandonedTimeout");
        this.removeAbandoned=p.getProperty("removeAbandoned");
        //portal-1数据库连接池配置
		this.maxActiveSMP=p.getProperty("MaxActiveSMP");
		this.initialSizeSMP=p.getProperty("InitialSizeSMP");
		this.maxWaitSMP=p.getProperty("MaxWaitSMP");
		this.testOnReturnSMP=p.getProperty("TestOnReturnSMP");
		this.testOnBorrowSMP=p.getProperty("TestOnBorrowSMP");
		this.testWhileIdleSMP=p.getProperty("TestWhileIdleSMP");
		this.timeBetweenEvictionRunsMillisSMP=p.getProperty("TimeBetweenEvictionRunsMillisSMP");
		this.numTestsPerEvictionRunSMP=p.getProperty("NumTestsPerEvictionRunSMP");
		this.minEvictableIdleTimeMillisSMP=p.getProperty("MinEvictableIdleTimeMillisSMP");
		this.defaultReadOnlySMP=p.getProperty("DefaultReadOnlySMP");
		this.defaultAutoCommitSMP=p.getProperty("DefaultAutoCommitSMP");
		this.validationQuerySMP=p.getProperty("ValidationQuerySMP");
		this.poolPreparedStatementsSMP=p.getProperty("PoolPreparedStatementsSMP");
		this.maxOpenPreparedStatementsSMP=p.getProperty("MaxOpenPreparedStatementsSMP");
		this.maxIdleSMP=p.getProperty("MaxIdleSMP");

    }

    /**
     * 关闭输入流并忽略异常
     * @param in 输入流
     */
    private void closeInputStream(InputStream in)
    {
        if (in != null)
        {
            try
            {
                in.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    /**
     * 获得SMP数据库驱动
     * @return SMP数据库驱动
     */
    public String getSmpDriver()
    {
        return smpDriver;
    }

    /**
     * 获得SMP数据库连接
     * @return SMP数据库连接
     */
    public String getSmpUrl()
    {
        return smpUrl;
    }

    /**
     * 获得SMP用户名
     * @return SMP用户名
     */
    public String getSmpUser()
    {
        return smpUser;
    }

    /**
     * 获得SMP密码
     * @return SMP密码
     */
    public String getSmpPassword()
    {
        return smpPassword;
    }

    /**
     * 获得SCP数据库驱动
     * @return SCP数据库驱动
     */
    public String getScpDriver()
    {
        return scpDriver;
    }

    /**
     * 获得SCP数据库连接
     * @return SCP数据库连接
     */
    public String getScpUrl()
    {
        return scpUrl;
    }

    /**
     * 获得SCP用户名
     * @return SCP用户名
     */
    public String getScpUser()
    {
        return scpUser;
    }

    /**
     * 获得SCP密码
     * @return SCP密码
     */
    public String getScpPassword()
    {
        return scpPassword;
    }

    /**
     * 获取卡信息分隔符
     * @return 卡信息分隔符
     */
	public String getCompartSign() {
		return compartSign;
	}

	/**
	 * @return 运营公司 
	 */
	public String getCompanyName() {
		return companyName;
	}
	/**
	 * @return 缴费金额限制  
	 */
	public String getPayState() {
		return payState;
	}
	
	/**
	 * @return 最大限额
	 */
	public String getUpbound() {
		return upbound;
	}

	/**
	 * @return 缴费金额
	 */
	public String getPermitFee() {
		return permitFee;
	}

	/**
	 * @return 银行转账最大金额限制，单位：元
	 */
	public String getMaxTransferFee() {
		return maxTransferFee;
	}

	/**
	 * @return 是否允许低于最小金额限制时进行银行转账
	 */
	public String getMinLimitState() {
		return minLimitState;
	}

	/**
	 * @return 银行转账最小金额限制，单位：元
	 */
	public String getMinTransferFee() {
		return minTransferFee;
	}

	/**
	 * @return 低于最小金额限制时进行银行转账所需手续费
	 */
	public String getHandlingCharge() {
		return handlingCharge;
	}

	/**
	 * @return
	 */
	public String getCftMaxFee() {
		return cftMaxFee;
	}
	/**
	 * @return
	 */
	public String getCftMinFee() {
		return cftMinFee;
	}
	/**
	 * @return
	 */
	public String getCftHandleCharge() {
		return cftHandleCharge;
	}
	/**
	 * @return
	 */
	public String getQbRebate() {
		return qbRebate;
	}
	
	
	
	
    public String getSUPHONE() {
		return SUPHONE;
	}


	public String getIP() {
		return IP;
	}


	public String getPORT() {
		return PORT;
	}


	public String getSIGNPWD() {
		return SIGNPWD;
	}


	public String getPAYPWD() {
		return PAYPWD;
	}


	public String getBeginSeq() {
		return BeginSeq;
	}

	public String getGdUserRebate() {
		return gdUserRebate;
	}

	public void setGdUserRebate(String gdUserRebate) {
		this.gdUserRebate = gdUserRebate;
	}

	public String getGdParentRebat() {
		return gdParentRebat;
	}

	public void setGdParentRebat(String gdParentRebat) {
		this.gdParentRebat = gdParentRebat;
	}

	public String getOtherRebate() {
		return otherRebate;
	}

	public void setOtherRebate(String otherRebate) {
		this.otherRebate = otherRebate;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public long getLastModified() {
		return lastModified;
	}

	public void setLastModified(long lastModified) {
		this.lastModified = lastModified;
	}

	public String getMaxActive() {
		return maxActive;
	}

	public void setMaxActive(String maxActive) {
		this.maxActive = maxActive;
	}

	public String getMaxWait() {
		return maxWait;
	}

	public void setMaxWait(String maxWait) {
		this.maxWait = maxWait;
	}

	public String getMaxIdle() {
		return maxIdle;
	}

	public void setMaxIdle(String maxIdle) {
		this.maxIdle = maxIdle;
	}

	public String getInitialSize() {
		return initialSize;
	}

	public void setInitialSize(String initialSize) {
		this.initialSize = initialSize;
	}

	public String getTestOnBorrow() {
		return testOnBorrow;
	}

	public void setTestOnBorrow(String testOnBorrow) {
		this.testOnBorrow = testOnBorrow;
	}

	public String getTestOnReturn() {
		return testOnReturn;
	}

	public void setTestOnReturn(String testOnReturn) {
		this.testOnReturn = testOnReturn;
	}

	public String getTimeBetweenEvictionRunsMillis() {
		return timeBetweenEvictionRunsMillis;
	}

	public void setTimeBetweenEvictionRunsMillis(
			String timeBetweenEvictionRunsMillis) {
		this.timeBetweenEvictionRunsMillis = timeBetweenEvictionRunsMillis;
	}

	public String getNumTestsPerEvictionRun() {
		return numTestsPerEvictionRun;
	}

	public void setNumTestsPerEvictionRun(String numTestsPerEvictionRun) {
		this.numTestsPerEvictionRun = numTestsPerEvictionRun;
	}

	public String getMinEvictableIdleTimeMillis() {
		return minEvictableIdleTimeMillis;
	}

	public void setMinEvictableIdleTimeMillis(String minEvictableIdleTimeMillis) {
		this.minEvictableIdleTimeMillis = minEvictableIdleTimeMillis;
	}

	public String getTestWhileIdle() {
		return testWhileIdle;
	}

	public void setTestWhileIdle(String testWhileIdle) {
		this.testWhileIdle = testWhileIdle;
	}

	public String getValidationQuery() {
		return validationQuery;
	}

	public void setValidationQuery(String validationQuery) {
		this.validationQuery = validationQuery;
	}

	public String getDefaultReadOnly() {
		return defaultReadOnly;
	}

	public void setDefaultReadOnly(String defaultReadOnly) {
		this.defaultReadOnly = defaultReadOnly;
	}

	public String getDefaultAutoCommit() {
		return defaultAutoCommit;
	}

	public void setDefaultAutoCommit(String defaultAutoCommit) {
		this.defaultAutoCommit = defaultAutoCommit;
	}

	public String getPoolPreparedStatements() {
		return poolPreparedStatements;
	}

	public void setPoolPreparedStatements(String poolPreparedStatements) {
		this.poolPreparedStatements = poolPreparedStatements;
	}

	public String getMaxOpenPreparedStatements() {
		return maxOpenPreparedStatements;
	}

	public void setMaxOpenPreparedStatements(String maxOpenPreparedStatements) {
		this.maxOpenPreparedStatements = maxOpenPreparedStatements;
	}

	public String getMinIdle() {
		return minIdle;
	}

	public void setMinIdle(String minIdle) {
		this.minIdle = minIdle;
	}

	public String getRemoveAbandonedTimeout() {
		return removeAbandonedTimeout;
	}

	public void setRemoveAbandonedTimeout(String removeAbandonedTimeout) {
		this.removeAbandonedTimeout = removeAbandonedTimeout;
	}

	public String getRemoveAbandoned() {
		return removeAbandoned;
	}

	public void setRemoveAbandoned(String removeAbandoned) {
		this.removeAbandoned = removeAbandoned;
	}

	public String getMaxActiveSMP() {
		return maxActiveSMP;
	}

	public void setMaxActiveSMP(String maxActiveSMP) {
		this.maxActiveSMP = maxActiveSMP;
	}

	public String getMaxWaitSMP() {
		return maxWaitSMP;
	}

	public void setMaxWaitSMP(String maxWaitSMP) {
		this.maxWaitSMP = maxWaitSMP;
	}

	public String getMaxIdleSMP() {
		return maxIdleSMP;
	}

	public void setMaxIdleSMP(String maxIdleSMP) {
		this.maxIdleSMP = maxIdleSMP;
	}

	public String getInitialSizeSMP() {
		return initialSizeSMP;
	}

	public void setInitialSizeSMP(String initialSizeSMP) {
		this.initialSizeSMP = initialSizeSMP;
	}

	public String getTestOnBorrowSMP() {
		return testOnBorrowSMP;
	}

	public void setTestOnBorrowSMP(String testOnBorrowSMP) {
		this.testOnBorrowSMP = testOnBorrowSMP;
	}

	public String getTestOnReturnSMP() {
		return testOnReturnSMP;
	}

	public void setTestOnReturnSMP(String testOnReturnSMP) {
		this.testOnReturnSMP = testOnReturnSMP;
	}

	public String getTimeBetweenEvictionRunsMillisSMP() {
		return timeBetweenEvictionRunsMillisSMP;
	}

	public void setTimeBetweenEvictionRunsMillisSMP(
			String timeBetweenEvictionRunsMillisSMP) {
		this.timeBetweenEvictionRunsMillisSMP = timeBetweenEvictionRunsMillisSMP;
	}

	public String getNumTestsPerEvictionRunSMP() {
		return numTestsPerEvictionRunSMP;
	}

	public void setNumTestsPerEvictionRunSMP(String numTestsPerEvictionRunSMP) {
		this.numTestsPerEvictionRunSMP = numTestsPerEvictionRunSMP;
	}

	public String getMinEvictableIdleTimeMillisSMP() {
		return minEvictableIdleTimeMillisSMP;
	}

	public void setMinEvictableIdleTimeMillisSMP(
			String minEvictableIdleTimeMillisSMP) {
		this.minEvictableIdleTimeMillisSMP = minEvictableIdleTimeMillisSMP;
	}

	public String getTestWhileIdleSMP() {
		return testWhileIdleSMP;
	}

	public void setTestWhileIdleSMP(String testWhileIdleSMP) {
		this.testWhileIdleSMP = testWhileIdleSMP;
	}

	public String getValidationQuerySMP() {
		return validationQuerySMP;
	}

	public void setValidationQuerySMP(String validationQuerySMP) {
		this.validationQuerySMP = validationQuerySMP;
	}

	public String getDefaultReadOnlySMP() {
		return defaultReadOnlySMP;
	}

	public void setDefaultReadOnlySMP(String defaultReadOnlySMP) {
		this.defaultReadOnlySMP = defaultReadOnlySMP;
	}

	public String getDefaultAutoCommitSMP() {
		return defaultAutoCommitSMP;
	}

	public void setDefaultAutoCommitSMP(String defaultAutoCommitSMP) {
		this.defaultAutoCommitSMP = defaultAutoCommitSMP;
	}

	public String getPoolPreparedStatementsSMP() {
		return poolPreparedStatementsSMP;
	}

	public void setPoolPreparedStatementsSMP(String poolPreparedStatementsSMP) {
		this.poolPreparedStatementsSMP = poolPreparedStatementsSMP;
	}

	public String getMaxOpenPreparedStatementsSMP() {
		return maxOpenPreparedStatementsSMP;
	}

	public void setMaxOpenPreparedStatementsSMP(String maxOpenPreparedStatementsSMP) {
		this.maxOpenPreparedStatementsSMP = maxOpenPreparedStatementsSMP;
	}

	public void setSmpDriver(String smpDriver) {
		this.smpDriver = smpDriver;
	}

	public void setSmpUrl(String smpUrl) {
		this.smpUrl = smpUrl;
	}

	public void setSmpUser(String smpUser) {
		this.smpUser = smpUser;
	}

	public void setSmpPassword(String smpPassword) {
		this.smpPassword = smpPassword;
	}

	public void setScpDriver(String scpDriver) {
		this.scpDriver = scpDriver;
	}

	public void setScpUrl(String scpUrl) {
		this.scpUrl = scpUrl;
	}

	public void setScpUser(String scpUser) {
		this.scpUser = scpUser;
	}

	public void setPermitFee(String permitFee) {
		this.permitFee = permitFee;
	}

	public void setScpPassword(String scpPassword) {
		this.scpPassword = scpPassword;
	}

	public void setUpbound(String upbound) {
		this.upbound = upbound;
	}

	public void setCompartSign(String compartSign) {
		this.compartSign = compartSign;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public void setPayState(String payState) {
		this.payState = payState;
	}

	public void setMinLimitState(String minLimitState) {
		this.minLimitState = minLimitState;
	}

	public void setMinTransferFee(String minTransferFee) {
		this.minTransferFee = minTransferFee;
	}

	public void setHandlingCharge(String handlingCharge) {
		this.handlingCharge = handlingCharge;
	}

	public void setMaxTransferFee(String maxTransferFee) {
		this.maxTransferFee = maxTransferFee;
	}

	public void setCftMaxFee(String cftMaxFee) {
		this.cftMaxFee = cftMaxFee;
	}

	public void setCftMinFee(String cftMinFee) {
		this.cftMinFee = cftMinFee;
	}

	public void setCftHandleCharge(String cftHandleCharge) {
		this.cftHandleCharge = cftHandleCharge;
	}

	public void setQbRebate(String qbRebate) {
		this.qbRebate = qbRebate;
	}

	public void setSUPHONE(String suphone) {
		SUPHONE = suphone;
	}

	public void setIP(String ip) {
		IP = ip;
	}

	public void setPORT(String port) {
		PORT = port;
	}

	public void setSIGNPWD(String signpwd) {
		SIGNPWD = signpwd;
	}

	public void setPAYPWD(String paypwd) {
		PAYPWD = paypwd;
	}

	public String getSpecialCommission() {
		return SpecialCommission;
	}

	public void setSpecialCommission(String specialCommission) {
		SpecialCommission = specialCommission;
	}

	public String getJcZone() {
		return jcZone;
	}

	public void setJcZone(String jcZone) {
		this.jcZone = jcZone;
	}

	public String getJcydzy() {
		return jcydzy;
	}

	public void setJcydzy(String jcydzy) {
		this.jcydzy = jcydzy;
	}

	public String getJcydfzy_d() {
		return jcydfzy_d;
	}

	public void setJcydfzy_d(String jcydfzy_d) {
		this.jcydfzy_d = jcydfzy_d;
	}

	public String getJcydfzy_s() {
		return jcydfzy_s;
	}

	public void setJcydfzy_s(String jcydfzy_s) {
		this.jcydfzy_s = jcydfzy_s;
	}
	
    public HashMap<String, Integer> getJtfkfee() {
		return jtfkfee;
	}

	public void setJtfkfee(HashMap<String, Integer> jtfkfee) {
		this.jtfkfee = jtfkfee;
	}

	public HashMap getjtfkFee(String fee){
    	HashMap<String, Integer> shMap=new HashMap<String, Integer>();
    	String[] strings=fee.split(",");
    	int n=strings.length;
    	for(int i=0;i<n;){
    		shMap.put(strings[i], Integer.parseInt(strings[i+1]));
    		i+=2;
    	}
		return shMap;
    }

	public int getJtfkzsh() {
		return jtfkzsh;
	}

	public void setJtfkzsh(int jtfkzsh) {
		this.jtfkzsh = jtfkzsh;
	}

	public int getJtfkfloat() {
		return jtfkfloat;
	}

	public void setJtfkfloat(int jtfkfloat) {
		this.jtfkfloat = jtfkfloat;
	}

	public int getJtfkdefault() {
		return jtfkdefault;
	}

	public String getFosanMoble() {
		return fosanMoble;
	}

	public void setFosanMoble(String fosanMoble) {
		this.fosanMoble = fosanMoble;
	}

	public void setJtfkdefault(int jtfkdefault) {
		this.jtfkdefault = jtfkdefault;
	}

	public String getGdltUserRebate() {
		return gdltUserRebate;
	}

	public void setGdltUserRebate(String gdltUserRebate) {
		this.gdltUserRebate = gdltUserRebate;
	}

	public String getGdltParentRebat() {
		return gdltParentRebat;
	}

	public void setGdltParentRebat(String gdltParentRebat) {
		this.gdltParentRebat = gdltParentRebat;
	}

	public String getLiantong_flow() {
		return liantong_flow;
	}

	public void setLiantong_flow(String liantong_flow) {
		this.liantong_flow = liantong_flow;
	}

	public String getYidong_flow() {
		return yidong_flow;
	}

	public void setYidong_flow(String yidong_flow) {
		this.yidong_flow = yidong_flow;
	}

	public String getDianxin_flow() {
		return dianxin_flow;
	}

	public void setDianxin_flow(String dianxin_flow) {
		this.dianxin_flow = dianxin_flow;
	}
}
