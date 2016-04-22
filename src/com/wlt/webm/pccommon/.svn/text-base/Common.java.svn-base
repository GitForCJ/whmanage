package com.wlt.webm.pccommon;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.wlt.webm.util.DataUtil;
import com.wlt.webm.util.Tools;

/**
 * 公共信息类<br>
 */
public class Common {
	
	/**
     * 对账状态列表
     */
    private static List checkStateList = new ArrayList();

    /**
     * 对账状态Map
     */
    private static Map checkStateMap = new LinkedHashMap();
    
    static {
    	checkStateList.add(new String[] { "0", "对账成功" });
    	checkStateList.add(new String[] { "1", "省代记录少" });
    	checkStateList.add(new String[] { "2", "省代记录多"});
    	checkStateList.add(new String[] { "3", "数据不对" });
    	checkStateList.add(new String[] { "4", "已处理" });
    	checkStateList.add(new String[] { "5", "未对账" });
    	checkStateMap = DataUtil.toStringMap(checkStateList);
    	
    }
    /**
	 * 获得交易流水号
	 * @param userNo 
	 * @return 交易流水号
	 */
	public static String getSeqNo(String userNo) {
		return Tools.getNow3().substring(2)+userNo+UniqueNo.getInstance().getNo();
	}
    /**
	 * 获得对账状态Map
	 * @return 交易流水号
	 */
	public static Map getCheckStateMap() {
		return checkStateMap;
	}
	
	/**
	 * 获取业务类型
	 * @return 业务类型列表
	 * @throws Exception
	 */
	public static List getServiceList() throws Exception {
		List serviceList = new ArrayList();
		serviceList.add(new String[] {"01001","电信缴费"});
		serviceList.add(new String[] {"01011","联通缴费"});
		serviceList.add(new String[] {"01021","空中充值"});
		serviceList.add(new String[] {"03002","电信售卡"});
		serviceList.add(new String[] {"03004","售游戏卡"});
		serviceList.add(new String[] {"05001","宽带办理"});
		serviceList.add(new String[] {"05002","移动业务办理"});
		serviceList.add(new String[] {"01012","天作缴费"});
		serviceList.add(new String[] {"01013","易票联缴费"});
		serviceList.add(new String[] {"10004","Q币充值"});

		return serviceList;
	}
	
	/**
	 * 获取交易状态列表
	 * @return 交易状态列表
	 * @throws Exception
	 */
	public static List getTradeStateList() throws Exception {
		List stateList = new ArrayList();
		stateList.add(new String[] {"0","正常"});
		stateList.add(new String[] {"1","返销"});
		stateList.add(new String[] {"2","退费"});
		stateList.add(new String[] {"3","正处理"});
		return stateList;
	}
	
	/**
	 * 获取交易状态MAP
	 * @return 交易状态Map
	 * @throws Exception
	 */
	public static Map getTradeStateMap() throws Exception {
		List stateList = getTradeStateList();
		Map stateMap = DataUtil.toStringMap(stateList);
		return stateMap;
	}
	
	/**
	 * 获取押金明细交易类型列表
	 * @return 交易类型列表
	 * @throws Exception
	 */
	public static List getTradeTypeList0() throws Exception{
		List typeList = new ArrayList();
		typeList.add(new String[] {"1","现金充值"});
		typeList.add(new String[] {"14","银行转帐"});
		typeList.add(new String[] {"28","财付通转帐"});
		typeList.add(new String[] {"33","新生支付转帐"});
		return typeList;
	}
	/**
	 * 获取佣金明细交易类型列表
	 * @return 交易类型列表
	 * @throws Exception
	 */
	public static List getTradeTypeList1() throws Exception{
		List typeList = new ArrayList();
		typeList.add(new String[] {"1","佣金现金充值"});
		typeList.add(new String[] {"22","佣金返还"});
		typeList.add(new String[] {"29","佣金转帐"});
		return typeList;
	}
	
	/**
	 * 获取押金明细交易类型MAP
	 * @return 交易类型Map
	 * @throws Exception
	 */
	public static Map getTradeTypeMap0() throws Exception {
		List typeList = getTradeTypeList0();
		Map typeMap = DataUtil.toStringMap(typeList);
		return typeMap;
	}
	/**
	 * 获取佣金明细交易类型MAP
	 * @return 交易类型Map
	 * @throws Exception
	 */
	public static Map getTradeTypeMap1() throws Exception {
		List typeList = getTradeTypeList1();
		Map typeMap = DataUtil.toStringMap(typeList);
		return typeMap;
	}
}
