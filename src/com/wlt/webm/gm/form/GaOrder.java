package com.wlt.webm.gm.form;
/**
 * 
* @ClassName: GuanMingFormOrder 
* @Description: ������Ϸֱ��ӿ� 
* @author tanwanlong
* @date 2014-6-5 ����02:37:38
 */
public class GaOrder {
	private String ret;//״̬���� 0 Ϊ��ѯ�ɹ�
	private String ret_msg;//������ʾ��Ϣ
	private String orderid;//�ӿ�ƽ̨������
	private String gameapi;//��Ϸ�ӿڱ���
	private String account;//��Ϸ�ʺ�,ͨ��֤,��QQ�ŵ�
	private String buynum;//��������
	private String cost;//���Ľ��
	private String gamecode;//��ֵ����,����Ϸ�����
	private String gamearea;//��Ϸ��������
	private String gameserver;//��Ϸ���ڷ�������
	private String gamerole;//��Ϸ��ɫ
	private String sporderid;//SP������,�û�ƽ̨�Ķ�����
	private String zt;//0δ����,1�Ѹ���,2������,3�ɹ�,4ʧ��,5���˿�,6δ�����ѳ���
	
	
	
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
