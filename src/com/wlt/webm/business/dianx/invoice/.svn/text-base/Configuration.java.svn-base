package com.wlt.webm.business.dianx.invoice;

/**
 * 获取xml解析的信息<br>
 * company 深圳市万恒科技有限公司<br>
 * copyright Copyright (c) 2008<br>
 * version 3.0.0.0
 * @author 胡俊
 */
public class Configuration
{
    /**
     * 构造函数，防止对象重复创建
     * @param p
     */
    public Configuration(int p)
    {

    }

    /**
     * 默认构造函数
     */
    public Configuration()
    {
        if (XmlParse.getConfig() == null)
        {
            XmlParse xml = new XmlParse();
            xml.xmlParse();
        }
    }

    /**
     * 打印名称
     */
    private static String itemName = "";

    /**
     * 打印坐标
     */
    public static String itemCoor = "";

    /**
     * 设置打印信息，同时打印名称项，及对应的坐标之间用"#"字符分隔
     * @param itemName 打印名称
     * @param itemXcoor X坐标
     * @param itemYcoor Y坐标
     */
    public void setItem(String itemName, String itemXcoor, String itemYcoor)
    {
        this.itemName = this.itemName + itemName;
        this.itemName = this.itemName + "#";
        this.itemCoor = this.itemCoor + itemXcoor + "@" + itemYcoor;
        this.itemCoor = this.itemCoor + "#";
    }

    /**
     * 获得打印名称
     * @return 打印名称
     */
    public String getItemName()
    {
        return this.itemName;
    }

    /**
     * 获得打印坐标
     * @return 打印坐标
     */
    public String getItemCoor()
    {
        return this.itemCoor;
    }

}
