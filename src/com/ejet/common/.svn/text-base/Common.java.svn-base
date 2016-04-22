package com.ejet.common;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.ejet.util.DataUtil;
import com.wlt.webm.db.DBToolSCP;


/**
 * 常用数据的常量类
 * Company：深圳市###EJET###有限公司
 * Copyright: Copyright (c) 2009
 * version 2.0.0.0
 * @author ejet.shen
 */
public class Common {

	/**
     * 终端状态列表
     */
    private static List terminalStateList = new ArrayList();
    /**
     * 终端状态Map
     */
    private static Map terminalStateMap = new LinkedHashMap();
    /**
     * 终端用户类型列表
     */
    private static List terminalUserTypeList = new ArrayList();
    /**
     * 终端用户类型Map
     */
    private static Map terminalUserTypeMap = new LinkedHashMap();
   /**
    * 代理商状态列表
    */
    private static List agentStateList = new ArrayList();
    /**
     * 代理商状态Map
     */
    private static Map agentStateMap = new LinkedHashMap();

    
    /**
     * 支付平台列表
     */
    private static List tradePlatList = new ArrayList();
    /**
     * 支付平台Map
     */
    private static Map tradePlatMap = new LinkedHashMap();
    
    /**
     * 交易状态列表
     */
    private static List tradeStateList = new ArrayList();

    /**
     * 交易状态Map
     */
    private static Map tradeStateMap = new LinkedHashMap();

    static {
        terminalStateList.add(new String[] { "0", "正常" });
    	terminalStateMap = DataUtil.toStringMap(terminalStateList);

    	terminalUserTypeList.add(new String[] {"1","代理点"});
    	terminalUserTypeList.add(new String[] {"2","家庭用户"});
    	terminalUserTypeList.add(new String[] {"3","商户"});
    	terminalUserTypeMap = DataUtil.toStringMap(terminalUserTypeList);
    	
    	agentStateList.add(new String[] {"0","正常"});
    	agentStateMap = DataUtil.toStringMap(agentStateList);
    	
    	tradePlatList.add(new String[] {"1","Portal"});
    	tradePlatList.add(new String[] {"2","Pos"});
    	tradePlatList.add(new String[] {"3","Pc"});
    	tradePlatList.add(new String[] {"8","Ivar"});
    	
//    	tradePlatList.add(new String[] {"4","计费系统"});
//    	tradePlatList.add(new String[] {"5","168系统"});
//    	tradePlatList.add(new String[] {"6","媒景"});
//    	tradePlatList.add(new String[] {"7","SMPClient"});
    	tradePlatMap = DataUtil.toStringMap(tradePlatList);
    	
    	tradeStateList.add(new String[] { "0", "正常" });
    	tradeStateList.add(new String[] { "1", "返销" });
    	tradeStateList.add(new String[] { "2", "退费"});
    	tradeStateList.add(new String[] { "3", "正处理" });
    	tradeStateMap = DataUtil.toStringMap(tradeStateList);
    }

    /**
     * @return List(String[]) 返回终端状态列表
     */
    public static List getTerminalStateList(){
        return terminalStateList;
    }
    
    /**
     * @return 返回待终端状态Map
     */
    public static Map getTerminalStateMap(){
    	return terminalStateMap;
    }
    
    /**
     * @return 终端用户类型列表
     */
    public static List getTerminalUserTypeList(){
    	return terminalUserTypeList;
    }
    
    /**
     * @return 终端用户类型Map
     */
    public static Map getTerminalUserTypeMap(){
    	return terminalUserTypeMap;
    }
    
    /**
     * @return 返回代理商状态列表
     */
    public static List getAgentStateList(){
    	return agentStateList;
    }
    
    /**
     * 
     * @return 返回代理商状态Map
     */
    public static Map getAgentStateMap(){
    	return agentStateMap;
    }
    
    /**
     * @return 交易平台列表
     */
    public static List getTradePlatList(){
    	return tradePlatList;
    }
    
    /**
     * @return 交易平台Map
     */
    public static Map getTradePlatMap(){
    	return tradePlatMap;
    }
    
    /**
     * @return List(String[]) 返回交易状态列表
     */
    public static List getTradeState()
    {
        return tradeStateList;
    }
    
    /**
     * @return 交易状态Map
     */
    public static Map getTradeStateMap(){
    	return tradeStateMap;
    }

    
    /**
     * 获取交易方式信息列表
     * @param num 1 所有的交易方式
     * 			   2 包括现金、银行卡、统一支付账户交易方式
     * @return 交易方式信息列表 
     * @throws Exception
     */
    public static List getTradeTypeList(int num) throws Exception {
    	if(num==1){
    		return DBToolSCP.getList("select id,name from ec_tradetype order by id");
    	}else{
    		return DBToolSCP.getList("select id,name from ec_tradetype where id in(1,2,3) order by id");
    	}
    }
    /**
     * 获取交易方式信息Map形式
     * @param num 1 所有的交易方式
     * 			   2 包括现金、银行卡、统一支付账户交易方式
     * @return  交易方式Map形式
     * @throws Exception
     */
    public static Map getTradeTypeMap(int num) throws Exception{
    	List tradeTypeList = getTradeTypeList(num);
    	return DataUtil.toStringMap(tradeTypeList);
    }
    
    /**
	 * 获得交易流水号
	 * @param userNo 
	 * @return 交易流水号
	 */
	public static String getSeqNo(String userNo) {
		//return Tools.getNow3().substring(2)+userNo+UniqueNo.getInstance().getNo();
		return "";
	}

}
