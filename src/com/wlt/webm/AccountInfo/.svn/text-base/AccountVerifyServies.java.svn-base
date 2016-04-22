package com.wlt.webm.AccountInfo;

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
public class AccountVerifyServies 
{
	
	/**
	 * 获取账户验证信息
	 * @param status 
	 * @param intime 
	 * @param endTime 
	 * @param index 
	 * @param pagesize 
	 * @return List
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<Object[]> showAccountVerify(int status,String intime,String endTime,String hiddenType,int index,int pagesize) throws Exception {
        DBService db = new DBService();
        List<Object[]> arrList=null;
        try {
            String sql="SELECT sysuser.user_login, "+
			" acc.accname,acc.ankacct,paper.Papers_TypeName,acc.certno,cardtype.BandCardTypeName,acc.inTime,acc.status,acc.AccountInfoId,acc.payType,bankcode.BankCodeName,CASE WHEN hi.userno IS NULL THEN 0 ELSE -1 END   "+ 
			"    FROM wht_AccountInfo acc LEFT JOIN sys_user sysuser ON  acc.userno=sysuser.user_no "+
			"  LEFT JOIN wht_Papers_Type paper ON acc.certcode=paper.Papers_TypeId "+
			"  LEFT JOIN wht_BankCard_Type cardtype ON acc.cardflag=cardtype.BandCardTypeId " +
			" LEFT JOIN wht_BankCode bankcode ON bankcode.BankCodeId=acc.bankcode " +
			" LEFT JOIN wht_AccountInfo_hidden hi ON acc.userno=hi.userno " +
			" WHERE  acc.STATUS=? "+ 
			" 	   AND acc.inTime>='"+intime+"' " +
            		"AND acc.inTime <='"+endTime+"'";
            if("-1".equals(hiddenType))//禁用
            {
            	sql=sql+" AND acc.userno IN (SELECT userno FROM wht_AccountInfo_hidden)";
            }
            if("2".equals(hiddenType))//未禁用
            {
            	sql=sql+" AND acc.userno NOT IN (SELECT userno FROM wht_AccountInfo_hidden)";
            }
            sql=sql+"  order by acc.inTime asc limit "+(index-1)*pagesize+" , "+pagesize;
            arrList=db.getList(sql,new Object[]{status});
            return arrList;
        } catch (Exception ex) {
            throw ex;
        } finally {
            db.close();
        }
    }
	
	/**
	 * 获取账户验证信息 总行数
	 * @param status 
	 * @param intime 
	 * @param endTime 
	 * @return int
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int getAccountVerifyCount(int status,String intime,String endTime,String hiddenType) throws Exception {
        DBService db = new DBService();
        try {
            String sql="SELECT count(*) con  " +
            		" FROM wht_AccountInfo acc LEFT JOIN wht_RegionalCode reg " +
            		"ON acc.bankarea=reg.RegionalCodeId WHERE acc.STATUS=? AND acc.inTime>='"+intime+"' " +
            		"AND acc.inTime <='"+endTime+"'";
            if("-1".equals(hiddenType))//禁用
            {
            	sql=sql+" AND acc.userno IN (SELECT userno FROM wht_AccountInfo_hidden)";
            }
            if("2".equals(hiddenType))//未禁用
            {
            	sql=sql+" AND acc.userno NOT IN (SELECT userno FROM wht_AccountInfo_hidden)";
            }
            int count=db.getInt(sql,new Object[]{status});
            return count;
        } catch (Exception ex) {
            throw ex;
        } finally {
            db.close();
        }
    }
	
	
	/**
	 * 根据id 取对应的一条记录，然后发起验证
	 * @param id
	 * @return int 
	 * @throws Exception 
	 */
	public int senVerify(int id) throws Exception
	{
		DBService db = new DBService();
        try {
        	String updateSql="UPDATE wht_AccountInfo SET orderseq='"+getTime()+"' WHERE AccountInfoId=?";
        	if(db.update(updateSql,new Object[]{id})<=0)
        	{
        		return -1;
        	}
        	
            String sql="SELECT reg.RegionalCodeNumber,acc.accname,acc.ankacct,acc.bankinfo,bank.bankCodeNumber,banktype.BandCardTypeNumber, "+
				" paper.Papers_TypeNumber,acc.certno,acc.contactphone,acc.contactaddr,acc.orderseq,acc.userno FROM wht_AccountInfo acc  "+
				" LEFT JOIN wht_RegionalCode reg ON acc.bankarea=reg.RegionalCodeId "+
				" LEFT JOIN wht_BankCode bank ON acc.bankcode=bank.BankCodeId "+
				" LEFT JOIN wht_BankCard_Type banktype ON acc.cardflag=banktype.BandCardTypeId "+
				" LEFT JOIN wht_Papers_Type paper ON acc.certcode=paper.Papers_TypeId "+
				" WHERE acc.AccountInfoId=? AND STATUS =0"; 
            List arryList=db.getStringList(sql,new Object[]{id});
            
            String tradeNo="09010"; //  签约交易类型
            String seqNo=arryList.get(10).toString();//用订单号，来做流水号 唯一
            TradeMsg msg=new TradeMsg();
    		msg.setSeqNo(seqNo); 
    		msg.setFlag("0");//有内容
    		msg.setMsgFrom2("0103");
    		msg.setServNo("00");
    		msg.setTradeNo(tradeNo);
    		
    		Map<String, String> content =new HashMap<String, String>();
    		content.put("BANKAREA",arryList.get(0).toString());
    		content.put("ACCNAME",arryList.get(1).toString());
    		content.put("BANKACCT",arryList.get(2).toString());
    		content.put("BANKINFO",arryList.get(3).toString());
    		content.put("BANKCODE",arryList.get(4).toString());
    		content.put("CARDFLAG",arryList.get(5).toString());
    		content.put("CERTCODE",arryList.get(6).toString());
    		content.put("CERTNO",arryList.get(7).toString());
    		content.put("CONTACTPHONE",arryList.get(8).toString());
    		content.put("CONTACTADDR",arryList.get(9).toString());
    		content.put("ORDERSEQ",arryList.get(10).toString());
    		content.put("USERNO",arryList.get(11).toString());
            
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
//    			String updateSql="UPDATE wht_AccountInfo SET STATUS=1,verifyTime=NOW() WHERE AccountInfoId=?";
//    			if(db.update(updateSql,new Object[]{id})>0)
//    			{
//    				return 0;//
//    			}
//    			else
//    			{
//    				return -1;//更新数据状态失败
//    			}
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
	 * 显示账户详细信息
	 * @param id
	 * @return List 
	 * @throws Exception
	 */
	public List showDetails(int id) throws Exception
	{
		DBService db = new DBService();
        try {
            String sql="SELECT "+
				" reg.RegionalCodeName,acc.accname,acc.ankacct,acc.bankinfo,bank.BankCodeName,banktype.BandCardTypeName, "+
				" paper.Papers_TypeName,acc.certno,acc.contactphone,acc.contactaddr,acc.recvcorp,acc.status  "+
				" FROM wht_AccountInfo acc  "+
				" LEFT JOIN wht_RegionalCode reg ON acc.bankarea=reg.RegionalCodeId "+
				" LEFT JOIN wht_BankCode bank ON acc.bankcode=bank.BankCodeId "+
				" LEFT JOIN wht_BankCard_Type banktype ON acc.cardflag=banktype.BandCardTypeId "+
				" LEFT JOIN wht_Papers_Type paper ON acc.certcode=paper.Papers_TypeId "+
				" WHERE acc.AccountInfoId=?"; 
            List arryList=db.getStringList(sql,new Object[]{id});
           return arryList;
        } catch (Exception ex) {
            throw ex;
        } finally {
            db.close();
        }
	}
	
	/**
	 * 生成 订单号，年月日时分秒+六位随机数  组成
	 * @return String  订单编号
	 */
	public static String getTime()
	{
		return Tools.getNow3()+((char)(new Random().nextInt(26) + (int)'A'))+((int)(Math.random()*10000)+10000);
	}

	/**
	 * 根据id 删除未验证的id
	 * @param id
	 * @return int 
	 * @throws Exception 
	 */
	public int DelAccountVerify(int id) throws Exception
	{
		DBService db = new DBService();
        try {
        	String delSql=" DELETE FROM  wht_AccountInfo WHERE AccountInfoId=? AND STATUS=0 ";
        	return db.update(delSql,new Object[]{id});
        } catch (Exception ex) {
            throw ex;
        } finally {
            db.close();
        }
	}

	/**
	 * 获取用户 当前类型
	 * @param roleId
	 * @return int
	 * @throws Exception
	 */
	public int getUserRole(int roleId) throws Exception
	{
		DBService db = new DBService();
        try {
        	String getSql=" SELECT sys.sr_type FROM sys_role r JOIN sys_roletype sys ON r.sr_type=sys.sr_type WHERE r.sr_id=? ";
        	return db.getInt(getSql,new Object[]{roleId});
        } catch (Exception ex) {
            throw ex;
        } finally {
            db.close();
        }
	}
}
