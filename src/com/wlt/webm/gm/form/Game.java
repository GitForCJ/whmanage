package com.wlt.webm.gm.form;
/**
 * 
* @ClassName: GuanMingForm 
* @Description: ������ϷAPI�ӿ� 
* @author tanwanlong
* @date 2014-6-5 ����02:37:38
 */
public class Game{
	private String ret;//״̬���� 0 Ϊ��ѯ�ɹ�
	private String ret_msg;//������ʾ��Ϣ
	private String id;//��Ϸid
	private String api;//��Ϸapi
	private String corp;//��Ϸ������˾����
	private String code;//��Ϸ��ֵ,Ϊ������ͨ�õ���,����Ҫ������Ϸ��ֵ,��Ӧgamecode
	private String mprice;//��ֵ(Ԫ)
	private String point;//��������ֵ�ı���
	private String unit;//������λ
	private String name;//��Ϸ����
	
	
	
	public Game() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Game(String api, String code, String corp, String id, String mprice,
			String name, String point, String ret, String ret_msg, String unit) {
		super();
		this.api = api;
		this.code = code;
		this.corp = corp;
		this.id = id;
		this.mprice = mprice;
		this.name = name;
		this.point = point;
		this.ret = ret;
		this.ret_msg = ret_msg;
		this.unit = unit;
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
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getApi() {
		return api;
	}
	public void setApi(String api) {
		this.api = api;
	}
	public String getCorp() {
		return corp;
	}
	public void setCorp(String corp) {
		this.corp = corp;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMprice() {
		return mprice;
	}
	public void setMprice(String mprice) {
		this.mprice = mprice;
	}
	public String getPoint() {
		return point;
	}
	public void setPoint(String point) {
		this.point = point;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	

}
