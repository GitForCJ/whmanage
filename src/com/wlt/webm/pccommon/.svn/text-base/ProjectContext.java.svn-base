package com.wlt.webm.pccommon;

/**
 * 项目工程上下文环境类。当WEB服务器启动时，该类属性将被自动设置。<br>
 */
public class ProjectContext
{
    /**
     * 本例唯一实例
     */
    private static ProjectContext instance = new ProjectContext();

    /**
     * 项目工程绝对根路径
     */
    private String rootPath;

    /**
     * 手工维护的所有配置文件的统一存放路径
     */
    private String configPath;

    /**
     * 项目工程配置文件路径
     */
    private String projectConfigFile;

    /**
     * 统一支付公共组件的配置文件路径
     */
    private String epayUtilConfigFile;

    /**
     * ESB配置文件路径
     */
    private String epayEsbConfigFile;

    /**
     * 打印配置文件路径
     */
    private String invoiceConfigFile;
    
    /**
     * 财付通接口配置文件路径
     */
    private String tenpayConfigFile;

    /**
     * 私有构造器，不允许外部实例化。
     */
    private ProjectContext()
    {
        
    }

    /**
     * 获得该类唯一实例
     * @return 该类唯一实例
     */
    public static ProjectContext getInstance()
    {
        return instance;
    }

    /**
     * @return 项目工程绝对根路径
     */
    public String getRootPath()
    {
        return rootPath;
    }

    /**
     * @param rootPath 项目工程绝对根路径
     */
    public void setRootPath(String rootPath)
    {
        this.rootPath = rootPath;
    }

    /**
     * @return 手工维护的所有配置文件的统一存放路径
     */
    public String getConfigPath()
    {
        return configPath;
    }

    /**
     * @param configPath 手工维护的所有配置文件的统一存放路径
     */
    public void setConfigPath(String configPath)
    {
        this.configPath = configPath;
    }

    /**
     * @return 项目工程配置文件路径
     */
    public String getProjectConfigFile()
    {
        return projectConfigFile;
    }

    /**
     * @param projectConfigFile 项目工程配置文件路径
     */
    public void setProjectConfigFile(String projectConfigFile)
    {
        this.projectConfigFile = projectConfigFile;
    }

    /**
     * @return ESB配置文件路径
     */
    public String getEpayEsbConfigFile()
    {
        return epayEsbConfigFile;
    }

    /**
     * @param epayEsbConfigFile ESB配置文件路径
     */
    public void setEpayEsbConfigFile(String epayEsbConfigFile)
    {
        this.epayEsbConfigFile = epayEsbConfigFile;
    }

    /**
     * @return 统一支付公共组件的配置文件路径
     */
    public String getEpayUtilConfigFile()
    {
        return epayUtilConfigFile;
    }

    /**
     * @param epayUtilConfigFile 统一支付公共组件的配置文件路径
     */
    public void setEpayUtilConfigFile(String epayUtilConfigFile)
    {
        this.epayUtilConfigFile = epayUtilConfigFile;
    }

    /**
     * @return 打印配置文件路径
     */
    public String getInvoiceConfigFile()
    {
        return invoiceConfigFile;
    }

    /**
     * @param invoiceConfigFile 打印配置文件路径
     */
    public void setInvoiceConfigFile(String invoiceConfigFile)
    {
        this.invoiceConfigFile = invoiceConfigFile;
    }
}
