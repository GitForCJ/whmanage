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
 * ���ݿ������ļ��ࡣ
 * ������õ���ģʽ�����ṩ<code>detect()</code>����
 * �Լ���ļ��Ƿ��޸Ĳ����¶�ȡ���ã��������̰߳�ȫ�ġ�<br>
 */
public class DBConfig
{
    /**
     * ���ݿ����ö���ʵ��
     */
    private static DBConfig config = new DBConfig();

    /**
     * ���ݿ������ļ�
     */
    private File file;

    /**
     * �����ļ������һ���޸�ʱ��
     */
    private long lastModified;

    //---------���ݿ������ļ�����----------

    /**
     * SMP���ݿ�����
     */
    private String smpDriver;

    /**
     * SMP���ݿ�URL
     */
    private String smpUrl;

    /**
     * SMP���ݿ��û���
     */
    private String smpUser;

    /**
     * SMP���ݿ�����
     */
    private String smpPassword;

    /**
     * SCP���ݿ�����
     */
    private String scpDriver;

    /**
     * SCP���ݿ�URL
     */
    private String scpUrl;

    /**
     * SCP���ݿ��û���
     */
    private String scpUser;
    
    /**
     * �����С����
     */
    private String permitFee;

    /**
     * SCP���ݿ�����
     */
    private String scpPassword;
    
    /**
     * �ɷ�����
     */
    private String upbound;
    /**
     * ����Ϣ�ָ���
     */
    private String compartSign;
    /**
     * ��Ӫ��˾
     */
    private String companyName;
    /**
     * �ɷѽ������
     */
    private String payState;
    
    /**
     * �Ƿ����������С�������ʱ��������ת�ˣ�0��ʾ������0��ʾ������
     */
    private String minLimitState;
    /**
     * ����ת����С������ƣ���λ��Ԫ
     */
    private String minTransferFee;
    
    /**
     * ������С�������ʱ��������ת�����������ѣ���λ��Ԫ
     */
    private String handlingCharge;
    /**
     * ����ת����������ƣ���λ��Ԫ
     */
    private String maxTransferFee;

    //�Ƹ�ͨת��
    /**
     *  ����ת����������ƣ���λ��Ԫ
     */
    private String cftMaxFee;
    /**
     * ����ת����С������ƣ���λ��Ԫ
     */
    private String cftMinFee;
    /**
     * ������С�������ʱ��������ת�����������ѣ���λ��Ԫ
     */
    private String cftHandleCharge;
    /**
     * Q���ۿ�
     */
    private String qbRebate;
    
    
    /**
     * ���ֻ���
     */
    private String SUPHONE;
    /**
     * ��IP
     */
    private String IP;


	/**
     * �˿�
     */
    private String PORT;
    /**
     * ��½����
     */
    private String SIGNPWD;
    /**
     * �ɷ�����
     */
    private String PAYPWD;
    /**
     * ��ʼ��ˮ��
     */
    private String BeginSeq;
    /**
     * �㶫���ű���Ӷ�����
     */
    private String gdUserRebate;
    /**
     * �㶫���Ÿ��ڵ�Ӷ�����
     */
    private String gdParentRebat;
    /**
     * ����ʡ��Ӷ�����
     */
    private String otherRebate;
    
    /**
     * ����Ӷ������
     */
    private String SpecialCommission;
    /**
     * �������ǵ��ƶ�����
     */
    private String jcZone;
    /**
     * �������ǵ�ֱӪӶ��
     */
    private String jcydzy;
    /**
     * �������ǵķ�ֱӪ�����Ӷ��
     */
    private String jcydfzy_d;
    /**
     * �������ǵķ�ֱӪ������Ӷ��
     */
    private String jcydfzy_s;
    /**
     * ��ͨ�������
     */
    private HashMap<String, Integer> jtfkfee;
    /**
     * ��ʯ����ͨ��������
     */
    private int jtfkzsh;
    /**
     * ��ͨ�������Ѹ������
     */
    private int jtfkfloat;
    /**
     * ��ͨ����Ĭ�ϴ����
     */
    private int jtfkdefault;
    /**
     * �㶫��ͨ����Ӷ�����
     */
    private String gdltUserRebate;
    /**
     * �㶫��ͨ���ڵ�Ӷ�����
     */
    private String gdltParentRebat;
    
    /**
     * ��ɽ�ƶ� Ӷ��
     */
    private String fosanMoble;
    
    /**
     * ��ͨ����Ӷ��
     */
    private String liantong_flow;
   
    /**
     * �ƶ�����Ӷ��
     */
    private String yidong_flow;
    
    /**
     * ��������Ӷ��
     */
    private String dianxin_flow;

    /**
     * SCP���ӳ���Ϣ
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
     * portal-1���ӳ���Ϣ
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
     * ����һ�����ݿ�������
     */
    public DBConfig()
    {
        ProjectContext context = ProjectContext.getInstance();
        file = new File(context.getProjectConfigFile());
        lastModified = file.lastModified();
        readConfig();
    }

    /**
     * ������ݿ�����ʵ��
     * @return ���ݿ�����ʵ��
     */
    public static DBConfig getInstance()
    {
        return config;
    }

    /**
     * ��ȡ�����ļ�����������
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
            throw new RuntimeException("�Ҳ��������ļ���" + file.getParent());
        }
        catch (IOException e)
        {
            throw new RuntimeException("�޷���ȡ�����ļ���" + file.getPath());
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
        //����ת�˽��������
        this.minLimitState = p.getProperty("minLimitState");
        
        this.minTransferFee = p.getProperty("minTransferFee");
        this.handlingCharge = p.getProperty("handlingCharge");
        this.maxTransferFee = p.getProperty("maxTransferFee");
        
        
        //�Ƹ�ͨת��
        this.cftMaxFee=p.getProperty("cftgMaxFee");
        this.cftMinFee=p.getProperty("cftMinFee");
        this.cftHandleCharge=p.getProperty("cftHandleCharge");
        
        //Q���ۿ�
        this.qbRebate =p.getProperty("qbRebate");
        
        //�㶫�ƶ�
        this.SUPHONE =p.getProperty("SUPHONE");
        this.IP =p.getProperty("IP");
        this.PORT =p.getProperty("PORT");
        this.SIGNPWD =p.getProperty("SIGNPWD");
        this.PAYPWD =p.getProperty("PAYPWD");
        this.BeginSeq =p.getProperty("BeginSeq");
        //Ӷ���ۿ���
        this.gdUserRebate=p.getProperty("gdUserRebate");
        this.gdParentRebat=p.getProperty("gdParentRebat");
        this.otherRebate=p.getProperty("otherRebate");
        
        //����Ӷ������
        this.SpecialCommission=p.getProperty("SpecialCommission");
        
        this.jcZone=p.getProperty("jcZone");
        this.jcydzy=p.getProperty("jcydzy");
        this.jcydfzy_d=p.getProperty("jcydfzy_d");
        this.jcydfzy_s=p.getProperty("jcydfzy_s");
        
        
        //��ͨ��������������
        this.jtfkfee=getjtfkFee(p.getProperty("jtfkfee"));
        this.jtfkzsh=Integer.parseInt(p.getProperty("jtfkzsh"));
        this.jtfkfloat=Integer.parseInt(p.getProperty("jtfkfloat"));
        this.jtfkdefault=Integer.parseInt(p.getProperty("jtfkdefault"));
        
        //�㶫��ͨӶ������
        this.gdltUserRebate=p.getProperty("gdltUserRebate");
        this.gdltParentRebat=p.getProperty("gdltParentRebat");
        
        //��ɽ�ƶ�
        this.fosanMoble=p.getProperty("fosanMoble");
        this.liantong_flow=p.getProperty("liantong_flow");
        this.yidong_flow=p.getProperty("yidong_flow");
        this.dianxin_flow=p.getProperty("dianxin_flow");
        //SCP���ݿ����ӳ�����
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
        //portal-1���ݿ����ӳ�����
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
     * �ر��������������쳣
     * @param in ������
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
     * ���SMP���ݿ�����
     * @return SMP���ݿ�����
     */
    public String getSmpDriver()
    {
        return smpDriver;
    }

    /**
     * ���SMP���ݿ�����
     * @return SMP���ݿ�����
     */
    public String getSmpUrl()
    {
        return smpUrl;
    }

    /**
     * ���SMP�û���
     * @return SMP�û���
     */
    public String getSmpUser()
    {
        return smpUser;
    }

    /**
     * ���SMP����
     * @return SMP����
     */
    public String getSmpPassword()
    {
        return smpPassword;
    }

    /**
     * ���SCP���ݿ�����
     * @return SCP���ݿ�����
     */
    public String getScpDriver()
    {
        return scpDriver;
    }

    /**
     * ���SCP���ݿ�����
     * @return SCP���ݿ�����
     */
    public String getScpUrl()
    {
        return scpUrl;
    }

    /**
     * ���SCP�û���
     * @return SCP�û���
     */
    public String getScpUser()
    {
        return scpUser;
    }

    /**
     * ���SCP����
     * @return SCP����
     */
    public String getScpPassword()
    {
        return scpPassword;
    }

    /**
     * ��ȡ����Ϣ�ָ���
     * @return ����Ϣ�ָ���
     */
	public String getCompartSign() {
		return compartSign;
	}

	/**
	 * @return ��Ӫ��˾ 
	 */
	public String getCompanyName() {
		return companyName;
	}
	/**
	 * @return �ɷѽ������  
	 */
	public String getPayState() {
		return payState;
	}
	
	/**
	 * @return ����޶�
	 */
	public String getUpbound() {
		return upbound;
	}

	/**
	 * @return �ɷѽ��
	 */
	public String getPermitFee() {
		return permitFee;
	}

	/**
	 * @return ����ת����������ƣ���λ��Ԫ
	 */
	public String getMaxTransferFee() {
		return maxTransferFee;
	}

	/**
	 * @return �Ƿ����������С�������ʱ��������ת��
	 */
	public String getMinLimitState() {
		return minLimitState;
	}

	/**
	 * @return ����ת����С������ƣ���λ��Ԫ
	 */
	public String getMinTransferFee() {
		return minTransferFee;
	}

	/**
	 * @return ������С�������ʱ��������ת������������
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
