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
	 * ��ȡ������Ϣ
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
	 * ��ȡ������Ϣ
	 * @return ������Ϣ
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
	 * ��ȡ��������
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
	 * ��ȡ֤������
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
	 * ¼���˻���Ϣ
	 * @param bean 
	 * @return int
	 * @throws Exception 
	 */
	public int saveAccountInfo(AccountInfoBean bean) throws Exception
	{
		StringBuffer strbuf=new StringBuffer(" insert into wht_AccountInfo( ");
		StringBuffer valuebuf=new StringBuffer(" values(");
//		//������
//		if(null != bean.getOrderseq() && !"".equals(bean.getOrderseq()))
//		{
//			strbuf.append("orderseq");
//			valuebuf.append("'"+bean.getOrderseq()+"'");
//		}
		//���ʱ��
		strbuf.append("inTime");
		valuebuf.append("NOW()");
		
		//������
		if(!"".equals(bean.getBankarea()))
		{
			strbuf.append(",bankarea");
			valuebuf.append(","+bean.getBankarea());
		}
		//�����˻�����
		if(null != bean.getAccname() && !"".equals(bean.getAccname()))
		{
			strbuf.append(",accname");
			valuebuf.append(",'"+bean.getAccname()+"'");
		}
		//�����˺�
		if(null != bean.getAnkacct() && !"".equals(bean.getAnkacct()))
		{
			strbuf.append(",ankacct");
			valuebuf.append(",'"+bean.getAnkacct().replace(" ","")+"'");
		}
		//����������
		if(null != bean.getBankinfo() && !"".equals(bean.getBankinfo()))
		{
			strbuf.append(",bankinfo");
			valuebuf.append(",'"+bean.getBankinfo()+"'");
		}
		//��������
		if(!"".equals(bean.getBankcode()))
		{
			strbuf.append(",bankcode");
			valuebuf.append(","+bean.getBankcode());
		}
		//���۱�ʶ
		if(!"".equals(bean.getCardflag()))
		{
			strbuf.append(",cardflag");
			valuebuf.append(","+bean.getCardflag());
		}
		//֤������
		if(!"".equals(bean.getCertcode()))
		{
			strbuf.append(",certcode");
			valuebuf.append(","+bean.getCertcode());
		}
		//֤������
		if(null != bean.getCertno() && !"".equals(bean.getCertno()))
		{
			strbuf.append(",certno");
			valuebuf.append(",'"+bean.getCertno()+"'");
		}
		//��ϵ�绰
		if(null != bean.getContactphone() && !"".equals(bean.getContactphone()))
		{
			strbuf.append(",contactphone");
			valuebuf.append(",'"+bean.getContactphone()+"'");
		}
		//��ϵ��ַ
		if(null != bean.getContactaddr() && !"".equals(bean.getContactaddr()))
		{
			strbuf.append(",contactaddr");
			valuebuf.append(",'"+bean.getContactaddr()+"'");
		}
	
		//��֤״̬
		if(!"".equals(bean.getStatus()))
		{
			strbuf.append(",status");
			valuebuf.append(","+bean.getStatus());
		}
		//���û�id
		if(!"".equals(bean.getUserno()))
		{
			strbuf.append(",userno");
			valuebuf.append(",'"+bean.getUserno()+"'");
		}
		// ֧������
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
			//��֧���Զ���֤
			//��֤���۵� ����  ���֤��  ���û����������֤�� �Ƿ�һ��
//			if(bean.getAccname().equals(bean.getUsername()) && bean.getCertno().equals(bean.getUsercat()))
//			{
//				int no=(int)prearykey;//������¼id
//				AccountVerifyServies acc=new AccountVerifyServies();
//				int status=acc.senVerify(no); //0�ɹ� 1������Ϣʧ��,��ֵʧ�� 2��ʱ  3ʧ��
//				return status;//�˻���Ϣ¼��ɹ���������֤
//			}
			return 4;//�˻���Ϣ¼��ɹ���δ������֤(�û��� �� ���֤id��һ��)
		}
		else
		{
			return -2; //�˻���Ϣ¼��ʧ��
		}
	}

}
