package com.wlt.webm.MonthAwardRule;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.wlt.webm.AccountInfo.bean.RegionalCode;
import com.wlt.webm.MonthAwardRule.bean.MonthAwardRule;
import com.wlt.webm.db.DBService;

/**
 * @author adminA
 *
 */
public class MonthAwardRuleServices {

	/**
	 * �����½���������Ϣ
	 * @return List<Object[]>
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<Object[]> showAwards() throws Exception
{
	  DBService db = new DBService();
        @SuppressWarnings("unused")
		List<RegionalCode> arrList=new ArrayList<RegionalCode>();
        try {
            String sql=" SELECT u.user_login,m.minmoney,m.maxmoney,m.rate,m.faceValue,m.awardId FROM wlt_MonthAwardRule m LEFT JOIN sys_user u ON m.userno=u.user_no ";
            return db.getList(sql,null);
        } catch (Exception ex) {
            throw ex;
        } finally {
            db.close();
        }
	}

	/**
	 * �����û�����������
	 * @return List<Object[]>
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List addMonthAwardRule() throws Exception
	{
	  DBService db = new DBService();
        try {
            String sql="SELECT u.user_no,u.user_login,u.user_id FROM sys_roletype ty "+
				" JOIN sys_role r ON ty.sr_type=r.sr_type  "+
				" JOIN sys_user u ON r.sr_id=u.user_role WHERE ty.sr_type=2 AND u.user_site=382 ";
            return db.getList(sql,null);
        } catch (Exception ex) {
            throw ex;
        } finally {
            db.close();
        }
	}

	/**
	 * ɾ��
	 * @param id
	 * @return boolean
	 * @throws Exception
	 */
	public int delMonthAwardRule(int id) throws Exception
	{
	  DBService db = new DBService();
        try {
            String sql="DELETE FROM wlt_MonthAwardRule WHERE awardId=?";
            return db.update(sql,new Object[]{id});
        } catch (Exception ex) {
            throw ex;
        } finally {
            db.close();
        }
	}
	
	/** ���
	 * @param bean
	 * @return int
	 * @throws Exception 
	 */
	public int saveMonthAwardRule(MonthAwardRule bean) throws Exception
	{
		 DBService db = new DBService();
	        try {
	           StringBuffer leftStr=new StringBuffer("INSERT INTO wlt_MonthAwardRule( ");
	           StringBuffer rightStr=new StringBuffer("VALUES( ");
	           
	           //�û�id,�û����
	           if(null!=bean.getUserno() && !"".equals(bean.getUserno()))
	           {
	        	   leftStr.append("userid");
	        	   rightStr.append(bean.getUserno().split(";")[0]);
	        	   
	        	   leftStr.append(",userno");
	        	   rightStr.append(",'"+bean.getUserno().split(";")[1]+"'");
	           }
	           
	           if(!"".equals(bean.getMinmoney()))
	           {
	        	   leftStr.append(",minmoney");
	        	   rightStr.append(","+bean.getMinmoney()*1000);
	           }
	           
	           if(!"".equals(bean.getMaxmoney()))
	           {
	        	   leftStr.append(",maxmoney");
	        	   rightStr.append(","+bean.getMaxmoney()*1000);
	           }
	           
	           if(!"".equals(bean.getRate()))
	           {
	        	   leftStr.append(",rate");
	        	   rightStr.append(","+bean.getRate());
	           }
	           
	           if(!"".equals(bean.getFacevalue()))
	           {
	        	   leftStr.append(",faceValue");
	        	   rightStr.append(","+bean.getFacevalue());
	           }
	           
	           leftStr.append(",intime)");
	           rightStr.append(",'"+getTime()+"')");
	           
	           String inserSql=leftStr.append(rightStr.toString()).toString();
	          return db.update(inserSql);
	        } catch (Exception ex) {
	            throw ex;
	        } finally {
	            db.close();
	        }
	}
	
	/**
	 * ������ʱ����
	 * @return String  
	 */
	public  static String getTime()
	{
		 Calendar cal=Calendar.getInstance();//ʹ��������
		  int year=cal.get(Calendar.YEAR);//�õ���
		  int month=cal.get(Calendar.MONTH)+1;//�õ��£���Ϊ��0��ʼ�ģ�����Ҫ��1
		  int day=cal.get(Calendar.DAY_OF_MONTH);//�õ���
		  int hour=cal.get(Calendar.HOUR);//�õ�Сʱ
		  int minute=cal.get(Calendar.MINUTE);//�õ�����
		  int second=cal.get(Calendar.SECOND);//�õ���
		  String datetime=(year<10?"0"+year:year+"")+""+(month<10?"0"+month:month+"")+(day<10?"0"+day:day+"")+(hour<10?"0"+hour:hour+"")+(minute<10?"0"+minute:minute+"")+(second<10?"0"+second:second+"");

		  return datetime;
	}
}
