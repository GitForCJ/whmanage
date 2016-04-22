package com.wlt.webm.xunjie.bean;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.SimpleHttpConnectionManager;
import org.apache.commons.httpclient.methods.PostMethod;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.wlt.webm.ten.service.MD5Util;
import com.wlt.webm.xunjie.form.DetailGoods;
import com.wlt.webm.xunjie.form.Game;
import com.wlt.webm.xunjie.form.GameGoods;
import com.wlt.webm.xunjie.form.Game_account_type;
import com.wlt.webm.xunjie.form.Game_area;
import com.wlt.webm.xunjie.form.Game_charge_type;
import com.wlt.webm.xunjie.form.Game_server;
import com.wlt.webm.xunjie.form.GoodsBrand;

/**
 * 一卡惠各类商品信息同步接口
 * 
 * @author caiSJ
 * 
 */
public class YiKaHuiBean {
	private static String USERNO = "2013120300000094";
	private static String KEY = "7991FA72A1CDF74B7DDE5E82E5F9CF98";
	private static String GOODSBRAND_URL = "http://115.236.95.98:5003/api_game/brandSync?";
	private static String BRAND_URL = "http://115.236.95.98:5003/api_game/goodsSync?";
	private static String GOODSDETAIL_URL = " http://115.236.95.98:5003/api_game/queryGameByGoods?";
	
	public void getGoodsBrand() {
		Map<String, String> map = new HashMap<String, String>();
		GoodsBrand goods=null;
		String url = GOODSBRAND_URL + "&userNo=" + USERNO + "&sign="
				+ MD5Util.MD5Encode(USERNO + KEY, "UTF-8");
		String rs = postXml(url);
		System.out.println(" 商品品牌同步结果:" + rs);
		if (null == rs) {
			System.out.println("商品品牌同步结果空");
			return;
		}
		Document docResult;
		String resMsg=null;
		String failedCode=null;
		String failedReason=null;
		try {
			docResult = DocumentHelper.parseText(rs);
			Element rootResult = docResult.getRootElement();
			List<Element> results = rootResult.elements();
			for (Element item1 : results) {
				if(item1.getName().equals("resMsg")){
					resMsg=item1.getText();
				}else if(item1.getName().equals("failedCode")){
					failedCode=item1.getText();
				}
				else if(item1.getName().equals("failedReason")){
					failedReason=item1.getText();
				}
				if(!"SUCCESS".equals(resMsg)){
					System.out.println(resMsg+"=="+failedCode+"=="+failedReason);
					return ;
				}
				
				if (item1.getName().equals("brand")) {
					List<Element> dataItem = item1.elements();
					for (Element item2 : dataItem) {
						goods=new GoodsBrand();
                     if(item2.getName().equals("brandId")){
                    	 goods.setBrandId(item2.getText());
                    	 System.out.print(" 商品品牌编号:"+item2.getText());
                     }else if(item2.getName().equals("brandName")){
                    	 goods.setBrandName(item2.getText());
                    	 System.out.print(" 商品品牌名称:"+item2.getText());
                     }else if(item2.getName().equals("remark")){
                    	 goods.setRemark(item2.getText());
                    	 System.out.println(" 商品品牌备注:"+item2.getText());
                     }
					}
					System.out.println("---");
				}
			}
		} catch (DocumentException e) {
			System.out.println("商品品牌同步接口异常"+e.toString());
			e.printStackTrace();
		}

	}
	
	public void getDetailGoods(String brandId) {
		String url=BRAND_URL+"&userNo="+USERNO+"&brandId="+brandId+"&sign="+
		MD5Util.MD5Encode(USERNO+brandId+KEY,"UTF-8");
		System.out.println(url);
		String rs = postXml(url);
		System.out.println("具体商品同步结果:" + rs);
		Document docResult;
		String resMsg=null;
		String failedCode=null;
		String failedReason=null;
		try {
			docResult = DocumentHelper.parseText(rs);
			Element rootResult = docResult.getRootElement();
			List<Element> results = rootResult.elements();
			for (Element item1 : results) {
				if(item1.getName().equals("resMsg")){
					resMsg=item1.getText();
				}else if(item1.getName().equals("failedCode")){
					failedCode=item1.getText();
				}
				else if(item1.getName().equals("failedReason")){
					failedReason=item1.getText();
				}
				if(!"SUCCESS".equals(resMsg)){
					System.out.println(resMsg+"=="+failedCode+"=="+failedReason);
					return ;
				}
				DetailGoods goods=null;
				if (item1.getName().equals("goods")) {
					List<Element> dataItem = item1.elements();
					for (Element item2 : dataItem) {
						goods=new DetailGoods();
                     if(item2.getName().equals("goodsId")){
                    	 goods.setGoodsId(item2.getText());
                    	 System.out.print("商品编号:"+item2.getText());
                     }else if(item2.getName().equals("goodsName")){
                    	 goods.setGoodsName(item2.getText());
                    	 System.out.print("商品名称:"+item2.getText());
                     }else if(item2.getName().equals("brandId")){
                    	 goods.setBrandId(item2.getText());
                    	 System.out.print("商品品牌编号:"+item2.getText());
                     } else if(item2.getName().equals("price")){
                    	 goods.setPrice(item2.getText());
                    	 System.out.print("商品价格:"+item2.getText());
                     } else if(item2.getName().equals("parValue")){
                    	 goods.setParValue(item2.getText());
                    	 System.out.print("商品面额:"+item2.getText());
                     }else if(item2.getName().equals("rechargeMode")){
                    	 goods.setRechargeMode(item2.getText());
                    	 System.out.print("充值方式:"+item2.getText());
                     }
                     else if(item2.getName().equals("description")){
                    	 goods.setDescription(item2.getText());
                    	 System.out.print("商品描述:"+item2.getText());
                     }
					}
					System.out.println("====");
				}
			}
		} catch (DocumentException e) {
			System.out.println("商品品牌同步接口异常"+e.toString());
			e.printStackTrace();
		}

	}
	
	
	public void goodsDetailInfo(String goodsId) {
		String url=GOODSDETAIL_URL+"&userNo="+USERNO+"&goodsId="+goodsId+"&sign="+
		MD5Util.MD5Encode(USERNO+goodsId+KEY,"UTF-8");
		String rs = postXml(url);
		System.out.println("商品详细信息同步结果:" + rs);
		Document docResult;
		String rsCode=null;
		String resMsg=null;
		GameGoods goods=null;
		List<Game> gameList =new ArrayList<Game>();
		
		Game game=null;
		List<Game_account_type> gameAccountTypeList=new ArrayList<Game_account_type>();
		List<Game_area> game_AreaList=new ArrayList<Game_area>();
		
		Game_account_type gameAccountType=null;
		List<Game_charge_type> gameChargeTypeList=new ArrayList<Game_charge_type>();
		
		Game_charge_type gameCharType=null;
		
		Game_area gameArea=null;
		List<Game_server>  gameServerList =new ArrayList<Game_server>();
		
		Game_server gameServer=null;
		try {
			docResult = DocumentHelper.parseText(rs);
			Element rootResult = docResult.getRootElement();
			List<Element> results = rootResult.elements();
			for (Element item1 : results) {
				if(item1.getName().equals("resCode")){
					rsCode=item1.getText();
				}
				if(item1.getName().equals("resMsg")){
					resMsg=item1.getText();
				}
				if(!"0000".equals(rsCode)){
					System.out.println(rsCode+"=="+rsCode+"  resMsg:"+resMsg);
					return ;
				}
				if (item1.getName().equals("goods")) {
					goods=new GameGoods();
					List<Element> dataItem = item1.elements();
					for (Element item2 : dataItem) {
                     if(item2.getName().equals("goods_id")){
                    	 goods.setGoods_id(item2.getText());
                    	 System.out.print("商品编号:"+item2.getText());
                     }else if(item2.getName().equals("goods_name")){
                    	 goods.setGoods_name(item2.getText());
                    	 System.out.print("商品名称:"+item2.getText());
                     }else if(item2.getName().equals("game_list")){
                    	 List<Element> dataItem0 = item2.elements();
                    	 for (Element item3 : dataItem0) {
                    		 if(item3.getName().equals("game")){
                    			 game =new Game();
                    			 List<Element> dataItem1 = item3.elements();
                    			 for (Element item4 : dataItem1) {
                    				 if(item4.getName().equals("game_name")){
                    					 game.setGame_name(item4.getText());
                    				 }else if(item4.getName().equals("game_id")){
                    					 game.setGame_id(item4.getText());
                    				 }else if(item4.getName().equals("game_account_type_list")){//===
                    					 List<Element> dataItem2 = item4.elements();
                    					 for (Element item5 : dataItem2) {
                    						 if(item5.getName().equals("game_account_type")){
                    							 List<Element> dataItem3 = item5.elements();
                            					 for (Element item6 : dataItem3) {
                            						 gameAccountType=new Game_account_type();
                            						 if(item6.getName().equals("game_account_type_id")){
                            							 gameAccountType.setGame_account_type_id(item6.getText());
                            						 }else if(item6.getName().equals("game_account_type_name")){
                            							 gameAccountType.setGame_account_type_name(item6.getText());
                            						 }else if(item6.getName().equals("game_charge_type_list")){
                            							 List<Element> dataItem4 = item6.elements();
                            							 for (Element item7 : dataItem4) {
                            								 if(item7.getName().equals("game_charge_type")){
                            									 List<Element> dataItem5 = item7.elements();
                            									 for (Element item8 : dataItem5) {
                            										 gameCharType=new Game_charge_type();
                            										 if(item8.getName().equals("game_charge_type_id")){
                            											 gameCharType.setGame_charge_type_id(item8.getText());
                            										 }else  if(item8.getName().equals("game_charge_type_name")){
                            											 gameCharType.setGame_charge_type_name(item8.getText());
                            										 }else if(item8.getName().equals("buy_number_list")){
                            											 gameCharType.setBuy_number_list(item8.getText());
                            										 }else if(item8.getName().equals("param_validate")){
                            											 gameCharType.setParam_validate(item8.getText());
                            										 }else if(item8.getName().equals("charge_type_info")){
                            											 gameCharType.setCharge_type_info(item8.getText());
                            										 }
                            										 gameChargeTypeList.add(gameCharType);
                            									 }
                            								 }
                            							 }
                            						 } 
                            						 gameAccountTypeList.add(gameAccountType);
                            					 }
                    						 }
                    					 }
                    				 }else if(item4.getName().equals("game_area_list")){
                    					 List<Element> dataItem6=item4.elements();
                    					 for (Element item9 : dataItem6) {
                    						 if(item9.getName().equals("game_area")){
                    							 gameArea=new Game_area();
                    							 List<Element> dataItem7=item9.elements();
                            					 for (Element item10 : dataItem7) {
                            						if(item10.getName().equals("game_area_name")){
                            						 gameArea.setGame_area_name(item10.getText());
                            						}else if(item10.getName().equals("game_area_id")){
                            							gameArea.setGame_area_id(item10.getText());
                            						}else if(item10.getName().equals("game_server_list")){
                            							 List<Element> dataItem8=item10.elements();
                                    					 for (Element item11 : dataItem7) {
                                    						 if(item11.getName().equals("game_server")){
                                    							 List<Element> dataItem9=item11.elements();
                                            					 for (Element item12 : dataItem9) {
                                            						 gameServer =new Game_server();
                                            						 if(item12.getName().equals("game_server_name")){
                                            							 gameServer.setGame_server_name(item11.getText());
                                            						 }else if(item12.getName().equals("game_server_id")){
                                            							 gameServer.setGame_server_id(item11.getText());
                                            						 }
                                            						 gameServerList.add(gameServer);
                                            					 }
                                    						 }
                                    					 }
                            						}
                            					 }
                            					 gameArea.setGameServerList(gameServerList);
                            					 game_AreaList.add(gameArea);
                    						 }
                    						
                    						 
                    					 }
                    				 }
                    			 }
                    			 game.setGameAccountTypeList(gameAccountTypeList);
                    			 game.setGame_AreaList(game_AreaList);
                        		 gameList.add(game);
                    		 }
                    	 }
                    	 }
                    	goods.setGameList(gameList); 
                     }
					}
				}
		} catch (DocumentException e) {
			System.out.println("商品品牌同步接口异常"+e.toString());
			e.printStackTrace();
		}
		System.out.println(goods.getGoods_id()+"  "+goods.getGoods_name()+"  ");
	}

	/**
	 * 获得http请求结果
	 * 
	 * @param url
	 * @return null失败或者异常
	 */
	public String postXml(String url) {
		HttpClient client = new HttpClient();
		PostMethod post = new PostMethod(url);
		String result = null;
		int status;
		try {
			status = client.executeMethod(post);
			if (status == 200) {
				result = convertStreamToString(post.getResponseBodyAsStream());
			}
		} catch (Exception e) {
			e.printStackTrace();
			result = null;
		}
		finally
		{
			 post.releaseConnection();
			((SimpleHttpConnectionManager)client.getHttpConnectionManager()).shutdown(); 
			if(null!=client)
			{
				client=null;
			}
		}
		return result;
	}
	
	  public static String convertStreamToString(InputStream is) throws IOException {
		StringBuilder sf = new StringBuilder();
		String line;
		BufferedReader reader=null;
		try{
		  reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
		 while ((line = reader.readLine()) != null){
		          sf.append(line);
		 }
		}
		finally {
		 is.close();
		 if(null!=reader){
			 reader.close();
		 }
		}
		 return sf.toString();
		}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		YiKaHuiBean bean =new YiKaHuiBean();
//        bean.getGoodsBrand();
		bean.getDetailGoods("100000000");
//		bean.getDetailGoods("100000094");
//		bean.goodsDetailInfo("100001832");//腾讯QQ币
	}

}
