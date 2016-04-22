package com.wlt.webm.gm.form;
/**
 * 
* @ClassName: GuanMingFormOrder 
* @Description: 冠铭游戏直冲接口 
* @author tanwanlong
* @date 2014-6-5 下午02:37:38
 */
public class GaOrder {
	private String ret;//状态编码 0 为查询成功
	private String ret_msg;//错误提示信息
	private String orderid;//接口平台订单号
	private String gameapi;//游戏接口编码
	private String account;//游戏帐号,通行证,或QQ号等
	private String buynum;//购买数量
	private String cost;//消耗金额
	private String gamecode;//充值类型,或游戏分类等
	private String gamearea;//游戏所在区域
	private String gameserver;//游戏所在服务器组
	private String gamerole;//游戏角色
	private String sporderid;//SP订单号,用户平台的订单号
	private String zt;//0未付款,1已付款,2处理中,3成功,4失败,5已退款,6未付款已撤销
	
	
	
	public GaOrder() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public GaOrder(String account, String buynum, String cost, String gameapi,
			String gamearea, String gamecode, String gamerole,
			String gameserver, String orderid, String ret, String ret_msg,
			String sporderid, String zt) {
		super();
		this.account = account;
		this.buynum = buynum;
		this.cost = cost;
		this.gameapi = gameapi;
		this.gamearea = gamearea;
		this.gamecode = gamecode;
		this.gamerole = gamerole;
		this.gameserver = gameserver;
		this.orderid = orderid;
		this.ret = ret;
		this.ret_msg = ret_msg;
		this.sporderid = sporderid;
		this.zt = zt;
	}

	public String getRet() {
		return ret;
	}

	public void setRet(String ret) {
		this.ret = ret;
	}

	public String getRet_msg() {
		return ret_msg;
	}

	public void setRet_msg(String ret_msg) {
		this.ret_msg = ret_msg;
	}

	public String getOrderid() {
		return orderid;
	}
	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}
	public String getGameapi() {
		return gameapi;
	}
	public void setGameapi(String gameapi) {
		this.gameapi = gameapi;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getBuynum() {
		return buynum;
	}
	public void setBuynum(String buynum) {
		this.buynum = buynum;
	}
	public String getCost() {
		return cost;
	}
	public void setCost(String cost) {
		this.cost = cost;
	}
	public String getGamecode() {
		return gamecode;
	}
	public void setGamecode(String gamecode) {
		this.gamecode = gamecode;
	}
	public String getGamearea() {
		return gamearea;
	}
	public void setGamearea(String gamearea) {
		this.gamearea = gamearea;
	}
	public String getGameserver() {
		return gameserver;
	}
	public void setGameserver(String gameserver) {
		this.gameserver = gameserver;
	}
	public String getGamerole() {
		return gamerole;
	}
	public void setGamerole(String gamerole) {
		this.gamerole = gamerole;
	}
	public String getSporderid() {
		return sporderid;
	}
	public void setSporderid(String sporderid) {
		this.sporderid = sporderid;
	}
	public String getZt() {
		return zt;
	}
	public void setZt(String zt) {
		this.zt = zt;
	}
	
	
	
}
