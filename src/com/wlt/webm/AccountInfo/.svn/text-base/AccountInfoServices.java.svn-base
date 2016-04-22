package com.wlt.webm.AccountInfo;

import java.util.ArrayList;
import java.util.List;

import com.wlt.webm.AccountInfo.bean.AccountInfoBean;
import com.wlt.webm.AccountInfo.bean.BankCardInfo;
import com.wlt.webm.AccountInfo.bean.BankInfo;
import com.wlt.webm.AccountInfo.bean.PapersType;
import com.wlt.webm.AccountInfo.bean.RegionalCode;
import com.wlt.webm.db.DBService;

/**
 * @author adminA
 *
 */
public class AccountInfoServices {
	
	/**
	 * 获取区域信息
	 * @return List
	 * @throws Exception
	 */
	public List<RegionalCode> getRegionalCodeInfo() throws Exception {
        DBService db = new DBService();
        List<RegionalCode> arrList=new ArrayList<RegionalCode>();
        try {
            String sql="SELECT regionalcodeid,regionalcodenumber,regionalcodename FROM wht_RegionalCode";
            List arr=db.getList(sql,null);
            for(int i=0;i<arr.size();i++)
            {
            	RegionalCode bankform = new RegionalCode();
            	 Object[] obj=(Object[])arr.get(i);
            	 bankform.setRegionalcoeid(obj[0]!=null?Integer.parseInt(obj[0].toString()):0);
            	 bankform.setRegionalcodenumber(obj[1]!=null?obj[1].toString():"");
            	 bankform.setRegionoalcodename(obj[2]!=null?obj[2].toString():"");
            	 arrList.add(bankform);
            }
            
            return arrList;
        } catch (Exception ex) {
            throw ex;
        } finally {
        	if(db!=null)
        		db.close();
        }
    }
	
	/**
	 * 获取银行信息
	 * @return 银行信息
	 * @throws Exception
	 */
	public List<BankInfo> getBankInfo() throws Exception {
        DBService db = new DBService();
        List<BankInfo> arrList=new ArrayList<BankInfo>();
        try {
            String sql="SELECT BankCodeId,BankCodeName FROM wht_BankCode";
            List arr=db.getList(sql,null);
            for(int i=0;i<arr.size();i++)
            {
            	 BankInfo bankform = new BankInfo();
            	 Object[] obj=(Object[])arr.get(i);
            	 bankform.setBankcodeid(obj[0]!=null?Integer.parseInt(obj[0].toString()):0);
            	 bankform.setBankcodename(obj[1]!=null?obj[1].toString():"");
            	 arrList.add(bankform);
            }
            
            return arrList;
        } catch (Exception ex) {
            throw ex;
        } finally {
        	if(db!=null)
        		db.close();
        }
    }
	
	/**
	 * 获取银行类型
	 * @return List
	 * @throws Exception
	 */
	public List<BankCardInfo> getBankCardInfo() throws Exception 
	{
		DBService db = new DBService();
		List<BankCardInfo> arrList=new ArrayList<BankCardInfo>();
        try {
            String sql="SELECT BandCardTypeId,BandCardTypeName FROM wht_BankCard_Type";
            List arr=db.getList(sql,null);
            for(int i=0;i<arr.size();i++)
            {
            	BankCardInfo bankform = new BankCardInfo();
            	 Object[] obj=(Object[])arr.get(i);
            	 bankform.setBandcardtypeid(obj[0]!=null?Integer.parseInt(obj[0].toString()):0);
            	 bankform.setBandcardtypename(obj[1]!=null?obj[1].toString():"");
            	 arrList.add(bankform);
            }
            return arrList;
        } catch (Exception ex) {
            throw ex;
        } finally {
        	if(db!=null)
        		db.close();
        }
	}
	
	/**
	 * 获取证件类型
	 * @return  List
	 * @throws Exception
	 */
	public List<PapersType> getPapersTypeInfo() throws Exception
	{
		DBService db = new DBService();
		List<PapersType> arrList=new ArrayList<PapersType>();
        try {
            String sql="SELECT Papers_TypeId,Papers_TypeName FROM wht_Papers_Type";
            List arr=db.getList(sql,null);
            for(int i=0;i<arr.size();i++)
            {
            	PapersType bankform = new PapersType();
            	 Object[] obj=(Object[])arr.get(i);
            	 bankform.setPaperstypeid(obj[0]!=null?Integer.parseInt(obj[0].toString()):0);
            	 bankform.setPaperstypename(obj[1]!=null?obj[1].toString():"");
            	 arrList.add(bankform);
            }
            return arrList;
        } catch (Exception ex) {
            throw ex;
        } finally {
        	if(db!=null)
        		db.close();
        }
	}

	/**
	 * 录入账户信息
	 * @param bean 
	 * @return int
	 * @throws Exception 
	 */
	public int saveAccountInfo(AccountInfoBean bean) throws Exception
	{
		StringBuffer strbuf=new StringBuffer(" insert into wht_AccountInfo( ");
		StringBuffer valuebuf=new StringBuffer(" values(");
//		//订单号
//		if(null != bean.getOrderseq() && !"".equals(bean.getOrderseq()))
//		{
//			strbuf.append("orderseq");
//			valuebuf.append("'"+bean.getOrderseq()+"'");
//		}
		//添加时间
		strbuf.append("inTime");
		valuebuf.append("NOW()");
		
		//区域编号
		if(!"".equals(bean.getBankarea()))
		{
			strbuf.append(",bankarea");
			valuebuf.append(","+bean.getBankarea());
		}
		//银行账户名称
		if(null != bean.getAccname() && !"".equals(bean.getAccname()))
		{
			strbuf.append(",accname");
			valuebuf.append(",'"+bean.getAccname()+"'");
		}
		//银行账号
		if(null != bean.getAnkacct() && !"".equals(bean.getAnkacct()))
		{
			strbuf.append(",ankacct");
			valuebuf.append(",'"+bean.getAnkacct().replace(" ","")+"'");
		}
		//开户行名称
		if(null != bean.getBankinfo() && !"".equals(bean.getBankinfo()))
		{
			strbuf.append(",bankinfo");
			valuebuf.append(",'"+bean.getBankinfo()+"'");
		}
		//开户银行
		if(!"".equals(bean.getBankcode()))
		{
			strbuf.append(",bankcode");
			valuebuf.append(","+bean.getBankcode());
		}
		//卡折标识
		if(!"".equals(bean.getCardflag()))
		{
			strbuf.append(",cardflag");
			valuebuf.append(","+bean.getCardflag());
		}
		//证件类型
		if(!"".equals(bean.getCertcode()))
		{
			strbuf.append(",certcode");
			valuebuf.append(","+bean.getCertcode());
		}
		//证件号码
		if(null != bean.getCertno() && !"".equals(bean.getCertno()))
		{
			strbuf.append(",certno");
			valuebuf.append(",'"+bean.getCertno()+"'");
		}
		//联系电话
		if(null != bean.getContactphone() && !"".equals(bean.getContactphone()))
		{
			strbuf.append(",contactphone");
			valuebuf.append(",'"+bean.getContactphone()+"'");
		}
		//联系地址
		if(null != bean.getContactaddr() && !"".equals(bean.getContactaddr()))
		{
			strbuf.append(",contactaddr");
			valuebuf.append(",'"+bean.getContactaddr()+"'");
		}
	
		//验证状态
		if(!"".equals(bean.getStatus()))
		{
			strbuf.append(",status");
			valuebuf.append(","+bean.getStatus());
		}
		//绑定用户id
		if(!"".equals(bean.getUserno()))
		{
			strbuf.append(",userno");
			valuebuf.append(",'"+bean.getUserno()+"'");
		}
		// 支付类型
		if(!"".equals(bean.getPayType()))
		{
			strbuf.append(",payType");
			valuebuf.append(","+bean.getPayType());
		}
		
		String insertSql=strbuf.append(")  ").append(valuebuf.append(")")).toString();
		DBService db = new DBService();
		long prearykey=db.getGeneratedKey(insertSql);
		if(db!=null)
    		db.close();
		if(prearykey>0)
		{
			//翼支付自动验证
			//验证代扣的 姓名  身份证号  和用户的姓名身份证号 是否一致
//			if(bean.getAccname().equals(bean.getUsername()) && bean.getCertno().equals(bean.getUsercat()))
//			{
//				int no=(int)prearykey;//订单记录id
//				AccountVerifyServies acc=new AccountVerifyServies();
//				int status=acc.senVerify(no); //0成功 1发送消息失败,充值失败 2超时  3失败
//				return status;//账户信息录入成功，发起验证
//			}
			return 4;//账户信息录入成功，未发起验证(用户名 或 身份证id不一致)
		}
		else
		{
			return -2; //账户信息录入失败
		}
	}

}
