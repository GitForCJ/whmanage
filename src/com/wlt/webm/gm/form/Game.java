package com.wlt.webm.gm.form;
/**
 * 
* @ClassName: GuanMingForm 
* @Description: 冠铭游戏API接口 
* @author tanwanlong
* @date 2014-6-5 下午02:37:38
 */
public class Game{
	private String ret;//状态编码 0 为查询成功
	private String ret_msg;//错误提示信息
	private String id;//游戏id
	private String api;//游戏api
	private String corp;//游戏所属公司名称
	private String code;//游戏的值,为空则是通用点数,不需要传送游戏的值,对应gamecode
	private String mprice;//面值(元)
	private String point;//点数与面值的比例
	private String unit;//点数单位
	private String name;//游戏名称
	
	
	
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
