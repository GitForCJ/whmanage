package com.wlt.webm.pccommon;

/**
 * ��Ŀ���������Ļ����ࡣ��WEB����������ʱ���������Խ����Զ����á�<br>
 */
public class ProjectContext
{
    /**
     * ����Ψһʵ��
     */
    private static ProjectContext instance = new ProjectContext();

    /**
     * ��Ŀ���̾��Ը�·��
     */
    private String rootPath;

    /**
     * �ֹ�ά�������������ļ���ͳһ���·��
     */
    private String configPath;

    /**
     * ��Ŀ���������ļ�·��
     */
    private String projectConfigFile;

    /**
     * ͳһ֧����������������ļ�·��
     */
    private String epayUtilConfigFile;

    /**
     * ESB�����ļ�·��
     */
    private String epayEsbConfigFile;

    /**
     * ��ӡ�����ļ�·��
     */
    private String invoiceConfigFile;
    
    /**
     * �Ƹ�ͨ�ӿ������ļ�·��
     */
    private String tenpayConfigFile;

    /**
     * ˽�й��������������ⲿʵ������
     */
    private ProjectContext()
    {
        
    }

    /**
     * ��ø���Ψһʵ��
     * @return ����Ψһʵ��
     */
    public static ProjectContext getInstance()
    {
        return instance;
    }

    /**
     * @return ��Ŀ���̾��Ը�·��
     */
    public String getRootPath()
    {
        return rootPath;
    }

    /**
     * @param rootPath ��Ŀ���̾��Ը�·��
     */
    public void setRootPath(String rootPath)
    {
        this.rootPath = rootPath;
    }

    /**
     * @return �ֹ�ά�������������ļ���ͳһ���·��
     */
    public String getConfigPath()
    {
        return configPath;
    }

    /**
     * @param configPath �ֹ�ά�������������ļ���ͳһ���·��
     */
    public void setConfigPath(String configPath)
    {
        this.configPath = configPath;
    }

    /**
     * @return ��Ŀ���������ļ�·��
     */
    public String getProjectConfigFile()
    {
        return projectConfigFile;
    }

    /**
     * @param projectConfigFile ��Ŀ���������ļ�·��
     */
    public void setProjectConfigFile(String projectConfigFile)
    {
        this.projectConfigFile = projectConfigFile;
    }

    /**
     * @return ESB�����ļ�·��
     */
    public String getEpayEsbConfigFile()
    {
        return epayEsbConfigFile;
    }

    /**
     * @param epayEsbConfigFile ESB�����ļ�·��
     */
    public void setEpayEsbConfigFile(String epayEsbConfigFile)
    {
        this.epayEsbConfigFile = epayEsbConfigFile;
    }

    /**
     * @return ͳһ֧����������������ļ�·��
     */
    public String getEpayUtilConfigFile()
    {
        return epayUtilConfigFile;
    }

    /**
     * @param epayUtilConfigFile ͳһ֧����������������ļ�·��
     */
    public void setEpayUtilConfigFile(String epayUtilConfigFile)
    {
        this.epayUtilConfigFile = epayUtilConfigFile;
    }

    /**
     * @return ��ӡ�����ļ�·��
     */
    public String getInvoiceConfigFile()
    {
        return invoiceConfigFile;
    }

    /**
     * @param invoiceConfigFile ��ӡ�����ļ�·��
     */
    public void setInvoiceConfigFile(String invoiceConfigFile)
    {
        this.invoiceConfigFile = invoiceConfigFile;
    }
}
