package oufei;

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
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.commsoft.epay.util.logging.Log;
import com.wlt.webm.tool.Tools;
import com.wlt.webm.util.MD5;

/**
 *
 * @version 2013-10-24
 */
public class OuFeiBean {
	/**
	 * 
	 */
	private String[] str=null;
	/**
	 * 
	 * @param str
	 * @param userno
	 */
	public OuFeiBean(String[] str){
	       this.str=str;	
	}
	
	/**
	 * @throws Exception 
	 * 
	 */
	public void  ouFeiBusiness() throws Exception{ 
		
		List<OuFeiOrderInfo> orderList = getOrder(str);
		if(orderList.size()>0){
				for (int i = 0,len = orderList.size(); i < len; i++) {
					Thread t = new Thread(new OuFeiBusiness(orderList.get(i),str));
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					t.start();
				}
			}
	}
	
	
	/**
	 * 
	 * @return map
	 */
	@SuppressWarnings("unchecked")
	public static List<OuFeiOrderInfo> getOrder(String[] str){
		List<OuFeiOrderInfo> orderList = new ArrayList<OuFeiOrderInfo>();
		Map<String, String> map = new HashMap<String, String>();
		String sign = MD5.encode(str[5]+str[6]+str[7]).toUpperCase();
		String reqid =str[5]+Tools.getNow3().substring(2)+Tools.buildRandom(5);  
		String parameter = "partner="+str[5] +
						"&tplid="+str[6]+
						"&sign="+sign+
						"&reqid="+reqid;
		Log.info(str[3]+"-"+str[5]+" 殴飞取单连接:"+JinhuaConstat.OFGETORDERURL+parameter);
		HttpClient client = new HttpClient();
		PostMethod post = new PostMethod(JinhuaConstat.OFGETORDERURL+parameter);
		client.getHttpConnectionManager().getParams().setConnectionTimeout(30*1000);
		client.getHttpConnectionManager().getParams().setSoTimeout(30*1000);
		int status;
		String result = "" ;
		try {
			status = client.executeMethod(post);
			if(status==200){
				result =convertStreamToString(post.getResponseBodyAsStream());
				Log.info(str[3]+"-"+str[5]+" 取单result:"+result);
				Document docResult=DocumentHelper.parseText(result);
				Element rootResult = docResult.getRootElement();
				List<Element> results=rootResult.elements();
			    for(Element item1:results){
			    	map.put(item1.getName(), item1.getText());
			    	if(item1.getName().equals("data")){
			    		List<Element> data =item1.elements();
			    		for(Element item2:data){
					    	if(item2.getName().equals("dataList")){
					    		List<Element> dataList =item2.elements();
					    		for(Element item3:dataList){
							    	if(item3.getName().equals("item")){
							    		List<Element> items =item3.elements();
							    		for(Element item4:items){
							    			map.put(item4.getName(), item4.getText());
							    		}
							    	}
					    		}
					    	}
			    			
			    		}
			    	}
			    }
			}
		} catch (Exception e) {
			e.printStackTrace();
			Log.error("ouFeibean:"+e.toString());
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
		String state = map.get("status");
		if(state.equals("0000")){
			String orderid = map.get("order_id");
			if(orderid!=null && !orderid.equals("")){
				orderList = getOrder(result); //将所有订单包装到集合list里面
			}
		}
		return  orderList ;
	}
	
	public static List<OuFeiOrderInfo> getOrder(String result ){
		List<OuFeiOrderInfo> orderList = new ArrayList<OuFeiOrderInfo>();
		Document docResult;
		try {
			docResult = DocumentHelper.parseText(result);
			Element rootResult = docResult.getRootElement();
			List<Element> results=rootResult.elements();
		    for(Element item1:results){
		    	if(item1.getName().equals("data")){
		    		List<Element> dataItem =item1.elements();
		    		for(Element item2:dataItem){
		    			if(item2.getName().equals("dataList")){
		    				List<Element> dataListItem =item2.elements();
		    				for(Element item3:dataListItem){
		    					if(item3.getName().equals("item")){
		    						List<Element> ItemItem =item3.elements();
		    						String order_id = "";
	    							String id = "" ;
	    							String product_par_value = "" ;
	    							String recharge_account = "" ;
		    						for(Element item4:ItemItem){
		    							if(item4.getName().equals("order_id") && item4.getName().length()>0){
		    								order_id = item4.getText();
		    							}
		    							if(item4.getName().equals("id") && item4.getName().length()>0){
		    								id = item4.getText();
		    							}
		    							if(item4.getName().equals("product_par_value") && item4.getName().length()>0){
		    								product_par_value = item4.getText();
		    							}
		    							if(item4.getName().equals("recharge_account")&& item4.getName().length()>0){
		    								recharge_account = item4.getText();
		    							}
		    						}
		    						OuFeiOrderInfo order = new OuFeiOrderInfo(order_id,id,product_par_value,recharge_account);
	    							orderList.add(order);
		    					}
		    				}
		    			}
		    		}
		    	}
		    }
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		
		return orderList;
	}
	
	/**
	 * 
	 * @param orderid  
	 * @param id  
	 * @return sum   0不可充值订单 1可充值订单
	 */
	@SuppressWarnings("unchecked")
	public static int checkOrder(String orderid ,String id){
		Map<String, String> map = new HashMap<String, String>();
		int sum = 0 ;
		String parameter = "orderid="+orderid +
						"&id="+id;
		HttpClient client = new HttpClient();
		GetMethod post = new GetMethod(JinhuaConstat.OFCHECKORDERURL+parameter);
		int status;
		try {
			status = client.executeMethod(post);
			if(status==200){
				String result = post.getResponseBodyAsString();
				Log.info("确认欧飞订单是否可充值返回结果："+result);
				Document docResult=DocumentHelper.parseText(result);
			    Element rootResult = docResult.getRootElement();
			    List<Element> results=rootResult.elements();
			    for(Element item1:results){
			    	map.put(item1.getName(), item1.getText());
			    	if(item1.getName().equals("data")){
			    		List<Element> data=item1.elements();
			    		for(Element item2:data){
			    			map.put(item2.getName(), item2.getText());
			    		}
			    	}
			    }
			}
		} catch (Exception e) {
			e.printStackTrace();
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
		if(map!= null && map.get("canRechaege").equals("true")){
			sum = 1 ;
		}
		return sum ;
	}
	/**
	 * 
	 * @param orderid  
	 * @param id  
	 * @param orderstate  
	 * @return  sum  0失锟斤拷  1锟缴癸拷
	 */
	@SuppressWarnings("unchecked")
	public static int returnResult(String orderid ,String id , String orderstate,String[] str){
		Map<String, String> map = new HashMap<String, String>();
		int sum = 0 ;
		String sign = MD5.encode(str[5]+id+orderid+str[7]).toUpperCase();
		String parameter  = "orderid="+orderid+
							"&id="+id+
							"&sign="+sign+
							"&orderstate="+orderstate+
							"&partner="+str[5]+
							"&tplid="+str[6];
		HttpClient client = new HttpClient();
		GetMethod post = new GetMethod(JinhuaConstat.OFRETURNRESULTURL+parameter);
		int status;
		try {
			status = client.executeMethod(post);
			if(status==200){
				String result = post.getResponseBodyAsString();
				Log.info("殴飞结果通知结果 :"+result);
				Document docResult=DocumentHelper.parseText(result);
			    Element rootResult = docResult.getRootElement();
			    List<Element> results=rootResult.elements();
			    for(Element item1:results){
			    	map.put(item1.getName(), item1.getText());
			    }
			}
		} catch (Exception e) {
			e.printStackTrace();
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
		if(map!= null && map.get("status").equals("0000")){
			sum = 1 ;
		}
		Log.info("sum状态 :"+sum);
		return sum ;
	}
	
	/**
	 * 字节流转换成数组
	 * @param is
	 * @return
	 * @throws IOException
	 */
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
	
	public static void main(String[] args) {
	/*	List<OuFeiOrderInfo> orders =  getOrder();
		System.out.println("main 方法启动");
		if(orders.size()>0){
			for(OuFeiOrderInfo order : orders){
				System.out.println("订单号："+order.getOrder_id()+" 编号："+order.getId()+
						" 手机号："+order.getRecharge_account()+" 面额："+order.getProduct_par_value());
			}
		}
		new OuFeiBean().ouFeiBusiness();*/
		
		int sum = checkOrder("13110810582248","158376505");
		Log.info("================"+sum);
	}
}
