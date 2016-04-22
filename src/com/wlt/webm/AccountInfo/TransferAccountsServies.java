package com.wlt.webm.AccountInfo;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import whmessgae.TradeMsg;

import com.commsoft.epay.util.logging.Log;
import com.wlt.webm.db.DBService;
import com.wlt.webm.message.MemcacheConfig;
import com.wlt.webm.message.PortalSend;
import com.wlt.webm.util.Tools;

/**
 * @author adminA
 *
 */
public class TransferAccountsServies {

	/**
	 * 显示转账列表
	 * @param userno
	 * @return list<Object[]>
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<Object[]> showTransferAccounts(String userno) throws Exception
	{
		 DBService db = new DBService();
	        List<Object[]> arrList=null;
	        try {
	            String sql="SELECT sysuser.user_login, "+
	            "  acc.accname,acc.ankacct,paper.Papers_TypeName,acc.certno,cardtype.BandCardTypeName,acc.inTime,acc.status,acc.AccountInfoId,bankcode.BankCodeName,CASE WHEN hi.userno IS NULL THEN 0 ELSE -1 END  "+
				  " FROM wht_AccountInfo acc LEFT JOIN sys_user sysuser ON  acc.userno=sysuser.user_no "+
				  "  LEFT JOIN wht_Papers_Type paper ON acc.certcode=paper.Papers_TypeId "+
				  "  LEFT JOIN wht_BankCard_Type cardtype ON acc.cardflag=cardtype.BandCardTypeId " +
				  " LEFT JOIN wht_BankCode bankcode ON bankcode.BankCodeId=acc.bankcode " +
				  " LEFT JOIN wht_AccountInfo_hidden hi ON hi.userno=acc.userno " +
				  " WHERE acc.userno='"+userno+"'";
	            arrList=db.getList(sql,null);
	            return arrList;
	        } catch (Exception ex) {
	            throw ex;
	        } finally {
	            db.close();
	        }
	}

	/**
	 * 转账
	 * @param id
	 * @param money
	 * @param username 
	 * @param userno 
	 * @param type 
	 * @return int 
	 * @throws Exception
	 */
	public int saveTransferAccounts(int id,String money,String username,String userno,int type)throws Exception
	{
		money=TransferAccountsServies.changeY2F(money); //元转成分
		
		String orderNumber=getOrderNumber(); //生成转账订单号
		 DBService db = new DBService();
	        try {
	            String sql="SELECT acc.sign_Id,acc.ankacct FROM "+
					" wht_AccountInfo acc WHERE acc.status=1  "+  //AND  acc.sign_Id IS NOT NULL
					"	 AND acc.AccountInfoId=?";
	            List arryList=db.getStringList(sql,new Object[]{id});

	            String insertSql="INSERT INTO wht_transactionRecord(userLogin,userNumber,orderNumber,recordMoney,bankacct,payType,recordStatus,recordTime) " +
	            		" VALUES('"+username+"','"+userno+"','"+orderNumber+"','"+money+"','"+arryList.get(1).toString()+"',"+type+",0,'"+getTime()+"')";
	            if(db.update(insertSql)<=0)
	            {
	            	return -1;
	            }
	            
	            String tradeNo="09011"; //  转款的交易类型
	            String seqNo=orderNumber;//流水号
	            TradeMsg msg=new TradeMsg();
	    		msg.setSeqNo(seqNo); 
	    		msg.setFlag("0");//有内容
	    		msg.setMsgFrom2("0103");
	    		msg.setServNo("00");
	    		msg.setTradeNo(tradeNo);
	    		
	    		Map<String, String> content =new HashMap<String, String>();
	    		content.put("BUSITYPE","BT001");
	    		
	    		content.put("TRANSCONTRACTID",arryList.get(0).toString());
	    		
	    		content.put("BANKACCT",arryList.get(1).toString());
	    		
	    		content.put("TXNAMOUNT",money);
	    		content.put("ORDERSEQ",orderNumber);
	    		
	    		content.put("USERNO",userno);
	            
	    		Log.info("返销所需订单号:"+seqNo);
	    		msg.setContent(content);

	    		if(!PortalSend.getInstance().sendmsg(msg)){
	    			return 1;//发送消息失败,充值失败
	    		}
	    		int k=0;
	    		TradeMsg rsMsg=null;
	    		while(k<180){
	    			k++;
	    			try {
	    				Thread.sleep(1000);
	    			}catch (InterruptedException e) {
	    				e.printStackTrace();
	    			}
	    			rsMsg=MemcacheConfig.getInstance().get(seqNo);
	    			if(null==rsMsg){
	    				continue;
	    			}else{
	    				MemcacheConfig.getInstance().delete(seqNo);
	    				break;
	    			}
	    		}
	    		if(null==rsMsg&&k>=150)
	    		{
	    			return 2;//超时
	    		}
	    		String code1=rsMsg.getRsCode();
	    		if("000".equals(code1))
	    		{
	    			return 0;//成功
	    		}
	    		else
	    		{
	    		    return 3;//失败
	    		}
	        } catch (Exception ex) {
	            throw ex;
	        } finally {
	            db.close();
	        }
	}

	/**
	 * 生成年月日时分秒
	 * @return String
	 */
	public  static String getTime()
	{
		 Calendar cal=Calendar.getInstance();//使用日历类
		  int year=cal.get(Calendar.YEAR);//得到年
		  int month=cal.get(Calendar.MONTH)+1;//得到月，因为从0开始的，所以要加1
		  int day=cal.get(Calendar.DAY_OF_MONTH);//得到天
		  int hour=cal.get(Calendar.HOUR_OF_DAY);//得到小时
		  int minute=cal.get(Calendar.MINUTE);//得到分钟
		  int second=cal.get(Calendar.SECOND);//得到秒
		  String datetime=(year<10?"0"+year:year+"")+""+(month<10?"0"+month:month+"")+(day<10?"0"+day:day+"")+(hour<10?"0"+hour:hour+"")+(minute<10?"0"+minute:minute+"")+(second<10?"0"+second:second+"");
		  return datetime;
	}
	
	/**
	 * 生成 订单号，年月日时分秒+六位随机数  组成
	 * @return String  订单编号
	 */
	public static String getOrderNumber()
	{
		return Tools.getNow3()+((char)(new Random().nextInt(26) + (int)'A'))+((int)(Math.random()*10000)+10000);
	}

	    /**   
	     * 将元为单位的转换为分 （乘100）  
	     *   
	     * @param amount  
	     * @return  String
	     */    
	    public static String changeY2F(Long amount){    
	        return BigDecimal.valueOf(amount).multiply(new BigDecimal(100)).toString();    
	    }    
	        
	    /**   
	     * 将元为单位的转换为分 替换小数点，支持以逗号区分的金额  
	     *   
	     * @param amount  
	     * @return  String
	     */    
	    public static String changeY2F(String amount){    
	       String currency =  amount.replaceAll("\\$|\\￥|\\,", "");  //处理包含, ￥ 或者$的金额    
	        int index = currency.indexOf(".");    
	        int length = currency.length();    
	        Long amLong = 0l;    
	        if(index == -1){    
	            amLong = Long.valueOf(currency+"00");    
	        }else if(length - index >= 3){    
	            amLong = Long.valueOf((currency.substring(0, index+3)).replace(".", ""));    
	        }else if(length - index == 2){    
	            amLong = Long.valueOf((currency.substring(0, index+2)).replace(".", "")+0);    
	        }else{    
	            amLong = Long.valueOf((currency.substring(0, index+1)).replace(".", "")+"00");    
	        }    
	        return amLong.toString();    
	    }    

	    /**转账交易密码验证
	     * @param userno
	     * @param password
	     * @return int
	     * @throws Exception
	     */
		@SuppressWarnings("unchecked")
		public int getPassword(String userno,String password) throws Exception
		{
			 DBService db = new DBService();
		        try {
		            String sql="SELECT COUNT(*) con FROM sys_user WHERE user_no='"+userno+"' AND trade_pass='"+password+"'";
		            return db.getInt(sql);
		        } catch (Exception ex) {
		            throw ex;
		        } finally {
		            db.close();
		        }
		}
		
		 /**转账时间控制
		 * @param userNumber 
	     * @return int
	     * @throws Exception
	     */
		@SuppressWarnings("unchecked")
		public int TimeControl(String userNumber) throws Exception
		{
			 DBService db = new DBService();
		        try {
		            String sql="SELECT COUNT(*) con FROM wht_transactionRecord WHERE TIMESTAMPDIFF(SECOND ,recordTime,'"+getTime()+"')<60 AND userNumber='"+userNumber+"'";
		            return db.getInt(sql,null);
		        } catch (Exception ex) {
		            throw ex;
		        } finally {
		            db.close();
		        }
		}
}
