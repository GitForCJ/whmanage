package com.wlt.webm.xunjie.bean;

import com.wlt.webm.util.MD5;

/**
 * 迅捷配置文件
 * @author caiSJ
 *
 */
public class XunJConstant {
	
	public static String  AGENT_ID="13417169406";
	public static String  PWD="hrd82025590com";//88570370
	public static String  CMCCPHONE_TYPE="0";
	public static String  UNICOMPHONE_TYPE="1";
	public static String  PHONE_TYPE="2";
	public static String  KEY="HRD20130724KJ";
	public static String  FILL_URL="http://183.129.178.137:9999/Interface/PaySubmit.ashx?";
    public static String  QUERY_URL="http://183.129.178.137:9999/Interface/QueryOrder2010.ashx?";
    
    /**
     * 手拉手业务类型
     */
	public static final String HANGZHOUSLS="";
	/**
	 * 手拉手密钥
	 */
	public static final String SLSKEY="jefkj9652IIEWNJ964";
	/**
	 * 手拉手交易密码
	 */
	public static final String SLSPWD=MD5.encode("888888");
	/**
	 * 手拉手商户号
	 */
	public static final String SLSID="84042";
	/**
	 * 手拉手回调
	 */
	public static final String SLSRETURNURL="http://caisj359.oicp.net:8090/jf//business/bank.do?method=slsReturn";
	/**
	 * 手拉手充值请求url
	 */
	public static final String SLSFILLURL="http://interface.slswd.cn:8088/Interface/PaySubmit.ashx?";
	/**
	 * 手拉手查询请求url
	 */
	public static final String SLSQUERYURL="http://interface.slswd.cn:8088/Interface/QueryOrder.ashx?";
	
	
	
}
