package com.wlt.webm.scpdb;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.commsoft.epay.util.logging.Log;
import com.wlt.webm.scpcommon.Constant;
import com.wlt.webm.scpcommon.RespCode;
import com.wlt.webm.scputil.DateParser;
import com.wlt.webm.scputil.Tools;

/**
 * �������ڣ�2013-11-26
 * <p>@Title: ���ڿ����ص��Ӵ���ϵͳҵ���������ģ��</p>
 * <p>@Description: �ʽ��ʻ�������ݿ����</p>
 * <p>Copyright: Copyright (c) 2013</p>
 * <p>Company: �����б���Ƽ����޹�˾ </p> 
 * <p>@author caiSJ</p>
 * <p>@version 1.0.0</p>
 */
public class FundAcctDao {
	
	/**
	 * ���ݿ����Ӷ���
	 */
	private DBService db = null;
	
	/**
	 * ���췽�� 
	 * @param db ���Ӷ���
	 */
	public FundAcctDao(DBService db)
	{
		this.db = db;
	}
	
	/**
	 * �ʽ��ʻ�ת��
	 * @param outFacct	ת���ʽ��ʻ�
	 * @param inFacct	ת���ʽ��ʻ�
	 * @param fee	���׽��
	 * @return	����true��ʾת�ʳɹ�,��֮ʧ��
	 * @throws Exception ���ݿ�����쳣
	 */
	public boolean updateFacctToFacct(String outFacct,String inFacct,int fee) throws Exception
	{
		int num = this.updateChildLeft(outFacct,-fee,Constant.FACCT_TO_FACCT_OUT) + this.updateChildLeft(inFacct,fee,Constant.FACCT_TO_FACCT_OUT);
		if(num < 4)
		{
			return false;
		}
		return true;
	}
	
	/**
	 * ����Ӷ���˻������
	 * @param childfacct Ӷ���ʻ�
	 * @param fee ���
	 * @return	���ش�����
	 * @throws Exception ���ݿ�����쳣
	 */
	public  int updateEngageLeft(String childfacct,int fee) throws Exception{
		long startTime=System.currentTimeMillis();
		StringBuffer sql = new StringBuffer();
		String[] str = new String[1];
		sql.append("update wht_childfacct set accountleft=accountleft-").append(fee);
		sql.append(" where childfacct='").append(childfacct).append("'");
		str[0] = sql.toString();
		int num = -1;
		try
		{
			num = db.update(str);
		}catch(Exception ex)
		{
			Log.error("����Ӷ���ʻ�������:",ex);
			throw ex;
		}
		long endTime=System.currentTimeMillis();
		Log.info("����Ӷ���˻�ʱ��"+(endTime-startTime));
		return num;
	}
	
	
	/**
	 * �����ʽ��ʻ����
	 * @param childfacct ���ʽ��ʻ�
	 * @param fee ���
	 * @param tradeType ��������
	 * @return	���ش�����
	 * @throws Exception ���ݿ�����쳣  -1ʧ��
	 */
	public  int  updateChildLeft(String childfacct,int fee,String tradeType) throws Exception
	{
		long startTime=System.currentTimeMillis();
		StringBuffer sql = new StringBuffer();
		String[] str = new String[2];
		sql.append("update wht_childfacct set accountleft=accountleft+").append(fee);
		//ֻ�г�ֵʱ����
		if(tradeType!=null && tradeType.trim().equals(Constant.FACCT_TRADE_FILL))
		{
			sql.append(",awardleft=awardleft+").append(fee);
		}
		sql.append(" where childfacct='").append(childfacct).append("'");
		str[0] = sql.toString();
		
		sql.delete(0,sql.length());
		sql.append("update wht_facct set accountleft=accountleft+").append(fee);
		
		if(tradeType!=null && tradeType.trim().equals(Constant.FACCT_TRADE_FILL))
		{
			sql.append(",awardleft=awardleft+").append(fee);
		}
		/*sql.append(" where fundacct in (select fundacct from ec_childfacct where childfacct='")
		   .append(childfacct).append("')");*/
		
		sql.append(" where fundacct='").append(childfacct.substring(0, childfacct.length()-2)).append("'");
		
		str[1] = sql.toString();
		
		int num = -1;
		num = db.update(str);
		return num;
	}
	

	
	/**
	 * �޸��ʽ��ʻ���־״̬
	 * @param date ����
	 * @param seqNo ��ˮ��
	 * @param state ״̬
	 * @return �����޸ĵļ�¼����
	 * @throws Exception ���ݿ�����쳣
	 */
	public int updateFacctBillSts(String date,String seqNo,int state) throws Exception
	{
		String yearMonth = date.substring(2,6);
		String tableName = "wht_acctbill_"+yearMonth;
		String sql = "update "+tableName+" set state="+state+","+
		"distime='"+DateParser.getNowDateTime()+"' where dealserial='"+seqNo+"'";
		
		return db.update(sql);
	}
	
	/**
	 * ����ʽ��ʻ��Ƿ�����
	 * @param fundAcct �ʽ��ʻ�
	 * @return	����true��ʾ������
	 */
	public boolean isDrop(String fundAcct)
	{
		boolean check = false;
		StringBuffer sql = new StringBuffer();
		sql.append("select fundacct from wht_facct where fundacct='")
		   .append(fundAcct).append("' and state=1");
		try
		{
			check = db.hasData(sql.toString());
		}catch(Exception ex)
		{
			Log.error("��ѯ�ʽ��ʻ��Ƿ���������:",ex);
		}
		
		return check;
	}
	
	/**
	 * �ж��ʽ��ʻ��Ƿ��
	 * @param fundAcct �ʽ��ʺ�
	 * @return ����true��ʾ����,��֮��ʾ������
	 */
	public boolean isFacctExist(String fundAcct)
	{
		boolean check = false;
		String sql = "select fundacct from wht_facct where fundacct='"+fundAcct+"'";

		try
		{
			check = db.hasData(sql);
		}catch(Exception ex)
		{
			Log.error("��ѯ�ʽ��ʻ��Ƿ���ڳ���:",ex);
		}
		
		return check;
	}
	
	/**
	 * �ʽ��ʻ��Ϸ�����֤
	 * @param fundAcct �ʽ��ʻ�
	 * @param childFacct ���ʽ��ʻ�
	 * @param payFee ���׽��
	 * @return ����true��ʾ��֤�ɹ�,��֮��ʾʧ��
	 */
	public String checkFacct(String fundAcct,String childFacct,double payFee)
	{
		//����ʽ��ʻ�״̬����
		if(this.isDrop(fundAcct))
		{
			long left = this.getChildFacctLeft(childFacct);
			Log.debug("�˻���"+left+" <> "+"�ɷѽ�"+payFee);
			//����
			if(left < payFee||payFee<0)
			{
				Log.debug("�ʽ��˻�����");
				return RespCode.RC_FACCT_NOLEFT;
			}
			return ""+left;
		}else
		{
			Log.debug("�ʽ��˻��Ѿ�����");
		}
		
		return RespCode.RC_FACCT_DROP;
	}
	
	/**
	 * �ж�����Ƿ�С��Ԥ��ֵ
	 * @param childFacct ���ʽ��ʻ�
	 * @return С��,����ʧ��.��֮���سɹ�
	 */
	public String checkWarnFee(String childFacct)
	{
		long left = this.getChildFacctLeft(childFacct);
		if(left<0){
			Log.debug("��ѯ�˻�����쳣");
			return RespCode.RC_FACCT_LESSWARN;
		}
		///��ȡ�ʽ��ʻ����������Ϣ
		Map limitInf = this.getAcctLimit(childFacct);
		if(limitInf == null || limitInf.size() < 1)
		{
			Log.debug("�ʽ��˻����������ϢΪ��");
			//return RespCode.RC_FACCT_NOLIMIT;
			return RespCode.RC_SUCCESS;
			
		}
		//Ԥ��ֵ
		int warnFee = Integer.parseInt((String)limitInf.get(Constant.FACCT_LIMIT_WARN_TYPE+""));
		if(left < warnFee)
		{
			Log.debug("�˻����������Ԥ��ֵ");
			return RespCode.RC_FACCT_LESSWARN;
		}
		
		return RespCode.RC_SUCCESS;
	}
	
	/**
	 * ����ʽ��ʻ����׼�¼�Ƿ����
	 * @param childFacct �ʽ��ʻ�
	 * @param seqNo ������ˮ��
	 * @param date ����
	 * @param type ��������
	 * @return ����true��ʾ����,��֮��ʾ������
	 * @throws Exception ���ݿ��쳣
	 */
	public boolean checkBill(String childFacct,String seqNo,String date,int type) throws Exception
	{
		StringBuffer sql = new StringBuffer();
		String tableName = "ec_acctbill_"+date.substring(2,6);
		sql.append("select id from ").append(tableName)
		   .append(" where childfacct='").append(childFacct)
		   .append("' and dealserial='").append(seqNo)
		   .append("' and state=0 and tradetype=").append(type);
		
		boolean check = false;
		
		try
		{
			check = db.hasData(sql.toString());
		}catch(Exception ex)
		{
			Log.error("��ѯ�ʽ��ʻ����׼�¼�Ƿ���ڳ���:",ex);
			throw ex;
		}
		
		return check;
	}
	
	/**
	 * ��ѯ�ʽ��ʻ�������ˮ���Ƿ����
	 * @param seqNo ��ˮ��
	 * @return ����true��ʾ�Ѵ���,��֮��ʾ������
	 */
	public boolean checkSeq(String seqNo)
	{
		StringBuffer sql = new StringBuffer();
		String tableName = "wht_orderform_"+Tools.getNow().substring(2,6);
		sql.append("select id from ").append(tableName)
		   .append(" where tradeserial='").append(seqNo)
		   .append("'");
		
		boolean check = false;
		
		try
		{
			check = db.hasData(sql.toString());
		}catch(Exception ex)
		{
			Log.error("��ѯ�ʽ��ʻ�������ˮ���Ƿ���ڳ���:",ex);
		}
		
		return check;
	}
	
	/**
	 * ����������ʽ��ʻ�
	 * @return �����ʽ��ʻ�����
	 */
	public List getDropFacctList()
	{
		List rsList = null;
		StringBuffer sql = new StringBuffer();
		sql.append("select fundacct,areacode,state from ec_facct where state=")
		   .append(Constant.FACCT_REMOVE_STATE).append(" or state=")
		   .append(Constant.FACCT_RECKONING_STATE);
		
		try
		{
			rsList = db.getList(sql.toString());
		}catch(Exception ex)
		{
			Log.error("����������ʽ��ʻ���Ϣ",ex);
		}
		return rsList;
	}
	
	/**
	 * ��ȡ�ʽ��ʻ���������
	 * @param fundacct �ʽ��ʻ�
	 * @return ��������
	 */
	public String getFacctArea(String fundacct)
	{
		StringBuffer sql = new StringBuffer();
		sql.append("select areacode from ec_facct where fundacct='")
		   .append(fundacct).append("'");
		String str = null;
		try
		{
			str = db.getString(sql.toString());
		}catch(Exception ex)
		{
			Log.error("��ѯ�ʽ��ʻ����ų���:",ex);
		}
		
		return str;
	}
	
	/**
	 * �����ʽ��ʻ������󶨵��ն��û�
	 * @param fundacct �ʽ��ʻ�
	 * @return �����ն��û��б�
	 */
	public List getTermUser(String fundacct)
	{
		List rsList = null;
		try
		{
			String sql = "select termid from ec_terminal where " +
					"agent="+Constant.BASE_AGENT+" and fundacct='"+fundacct+"'";
			rsList = db.getList(sql);
		}catch(Exception ex)
		{
			Log.error("�����ʽ��ʻ������󶨵��ն��û�����",ex);
		}
		return rsList;
	}
	

	/**
	 * �����ʽ��ʻ�������ʽ��ʺ�
	 * @param fundAcct �ʽ��ʻ�
	 * @param type �ʻ�����
	 * @return ����string���͵����ʽ��ʺ�
	 */
	public String getChildFacct(String fundAcct,String type)
	{
		StringBuffer sql = new StringBuffer();
		sql.append("select childfacct from ec_childfacct where fundacct='")
		   .append(fundAcct).append("' and accttypeid='").append(type).append("'");
		
		//Log.info("�������˻�---------->" + sql.toString());
		
		String str = null;
		try
		{
			str = db.getString(sql.toString());
		}catch(Exception ex)
		{
			Log.error("��ѯ���ʽ��ʺų���:"	+fundAcct,ex);
		}
		
		return str;
	}
	
	/**
	 * �������ʽ��ʻ��������������Ϣ
	 * @param childfacct ���ʽ��ʻ�
	 * @return ����Map; keyΪ�����������,valueΪ���ֵ
	 */
	public Map getAcctLimit(String childfacct)
	{
		StringBuffer sql = new StringBuffer();
		sql.append("select type,personnum from ec_acctlimit where childfacct='")
		   .append(childfacct).append("' and state=0 order by type");
		//Log.info("--------------------------->>>" + sql);
		Map rsMap = null;
		
		try
		{
			rsMap = Tools.getStringMap(db.getList(sql.toString()));
		}catch(Exception ex)
		{
			Log.error("��ѯ�ʽ��ʻ����������Ϣ����:",ex);
		}
		
		return rsMap;
	}
	
	/**
	 * ����ʽ��ʻ����
	 * @param fundAcct �ʽ��ʻ�
	 * @return �����ʽ��ʻ����
	 */
	public long getFacctLeft(String fundAcct)
	{
		StringBuffer sql = new StringBuffer();
		sql.append("select accountleft from wht_facct where fundacct='")
		   .append(fundAcct).append("'");

		long fee = 0;
		try
		{
			fee = db.getLong(sql.toString());
		}catch(Exception ex)
		{
			Log.error("��ѯ�ʽ��ʺ�������:",ex);
		}
		return fee;
	}
	
	/**
	 * ������ʽ��ʻ����
	 * @param childFacct �ʽ����ʻ�
	 * @return �����ʽ����ʻ����
	 */
	public long getChildFacctLeft(String childFacct)
	{
		StringBuffer sql = new StringBuffer();
		sql.append("select accountleft from wht_childfacct where childfacct='")
		   .append(childFacct).append("'");

		long fee = 0;
		try
		{
			fee = db.getLong(sql.toString());
		}catch(Exception ex)
		{
			Log.error("��ѯ���ʽ��ʺ�������:",ex);
			return -1;
		}
		return fee;
	}
	
	
	/**
	 * ����ʽ��ʻ����
	 * @param fundAcct �ʽ��ʻ�
	 * @param acctType �˻�����
	 * @return �������
	 * @throws Exception
	 */
	public int getChildFacctsLeft(String fundAcct,String acctType) throws Exception
	{
	    
		String sql ="select accountleft from wht_childfacct where fundacct='"+
		fundAcct+"' and type='"+acctType+"'";
		try
		{
			return db.getInt(sql);
		}catch(Exception ex)
		{
			throw ex;
		}
	}
	
	/**
	 * �����ʽ��ʻ������󶨵Ĵ�����
	 * @param fundacct �ʽ��ʻ�
	 * @return ���ش������б�
	 */
	public List getAgentList(String fundacct)
	{
		List rsList = null;
		try
		{
			rsList = db.getList("select agentid from ec_agentfacct_grp where state=0 and fundacct='"+fundacct+"'");
		}catch(Exception ex)
		{
			Log.error("�����ʽ��ʻ������󶨵Ĵ����̳���",ex);
		}
		return rsList;
	}
	/**
	 * �����ʽ��ʻ���Ϣ
	 * @param fundAcct �ʽ��ʺ�
	 * @param pwd  ����
	 * @param servid ��������
	 * @param areaCode ����
	 * @param custid  �ͻ����
	 * @param buildTime  ����ʱ��
	 * @param state ״̬
	 * @param disTime ����ʱ��
	 * @param isDeposit �Ƿ񴴽�Ѻ���ʻ�
	 * @throws Exception
	 */
	public void insertFacct(String fundAcct,String pwd,String servid,String areaCode,String custid,
			String buildTime,int state,String disTime,int isDeposit) throws Exception
	{
		StringBuffer sql = new StringBuffer();
		String[] str = new String[3];;
		//��Ҫ����Ѻ���ʻ�
		if(isDeposit == 1)
		{
			str = new String[4];
		}
		//�����ʽ��ʻ�
		sql.append("insert into ec_facct values('")
		   .append(fundAcct).append("','")
		   .append(pwd).append("',")
		   .append(custid).append(",'")
		   .append(buildTime).append("','")
		   .append(areaCode).append("',0,0,0,'")
		   .append(buildTime.substring(0,8)).append("','00000000','")
		   .append(buildTime).append("',")
		   .append(state).append(",'")
		   .append(disTime).append("')");
		str[0] = sql.toString();
		
		Log.debug("--�����ʽ��ʻ�--:"+sql.toString());
		//���ʽ��ʻ�
		String childFacct = fundAcct+Constant.FACCT_CASH_TYPE;
		
		//�����ֽ��ʽ��ʻ�
		sql.delete(0,sql.length());
		sql.append("insert into ec_childfacct values('")
		   .append(childFacct).append("','").append(fundAcct)
		   .append("','").append(Constant.FACCT_CASH_TYPE)
		   .append("',0,0,0,").append(state).append(",'")
		   .append(DateParser.getNowDateTime()).append("')");
		str[1] = sql.toString();
		
		//ƽ̨���۷���ϵ��
		sql.delete(0,sql.length());
		sql.append("insert into ec_sp_facct values('")
		   .append(fundAcct).append("',").append(servid).append(")");
		str[2] = sql.toString();
		
		//����Ѻ���ʻ�
		if(isDeposit == 1)
		{
			childFacct = fundAcct+Constant.FACCT_DEPOSIT_TYPE;
			sql.delete(0,sql.length());
			sql.append("insert into ec_childfacct values('")
			   .append(childFacct).append("','").append(fundAcct)
			   .append("','").append(Constant.FACCT_DEPOSIT_TYPE)
			   .append("',0,0,0,").append(state).append(",'")
			   .append(DateParser.getNowDateTime()).append("')");
			str[3] = sql.toString();
		}
		
		try
		{
			db.update(str);
		}catch(Exception ex)
		{
			Log.error("�����ʽ��ʻ�����:",ex);
			throw ex;
		}
	}
	
	/**
	 * ���ݿͻ���Ż�ȡ�ʽ��ʺ�
	 * @param custid �ͻ����
	 * @return �����ʽ��ʺ�
	 * @throws Exception
	 */
	public String getFacct(String custid) throws Exception
	{
		StringBuffer sql = new StringBuffer();
		sql.append("select fundacct from ec_facct where custid=").append(custid);
		String str = null;
		try
		{
			str = db.getString(sql.toString());
		}catch(Exception ex)
		{
			Log.error("���ݿͻ���ȡ�ʽ��ʺų���:",ex);
			throw ex;
		}
		
		return str;
	}
	/**
	 * �����۷�����ʵ����Ż�ȡ�ʽ��ʺ�
	 * @param servid �۷�ʵ����
	 * @return �����ʽ��ʺ�
	 * @throws Exception  
	 */
	public String getFacctOfServId(String servid) throws Exception
	{
		StringBuffer sql = new StringBuffer();
		sql.append("select fundacct from ec_sp_facct where servid=").append(servid);
		String str = null;
		try
		{
			str = db.getString(sql.toString());
		}catch(Exception ex)
		{
			Log.error("���ݷ���ʵ���Ż�ȡ�ʽ��ʺų���:",ex);
			throw ex;
		}
		
		return str;
	}
	
	/**
	 * ͳ���ʽ��ʻ��ս��ױ����ͽ��
	 * @param type ��������
	 * @param date ����
	 * @return ����String[],�ֱ�Ϊ���ױ����ͽ��׽��
	 * @throws Exception ���ݿ��쳣
	 */
	public String[] getDayBill(int type,String date) throws Exception
	{
		StringBuffer sql = new StringBuffer();
		String tableName = "ec_acctbill_"+date.substring(2,6);
		sql.append("select count(*),sum(tradefee) from ").append(tableName)
		   .append(" where substr(tradetime,1,8)='").append(date)
		   .append("' and state=0 and tradetype=").append(type);
		
		Log.debug("ͳ���ʽ��ʻ��ս��ױ����ͽ��: "+sql.toString());
		String[] str =  null;
		try
		{
			str = db.getStringArray(sql.toString());
		}catch(Exception ex)
		{
			Log.error("ͳ���ʽ��ʻ��ս��ױ����ͽ�����:",ex);
			throw ex;
		}
		
		return str;
	}
	/**
	 * �޸�ת��scp��״̬
	 * @param ʱ��
	 * @param ���׵���
	 * @throws Exception ���ݿ��쳣
	 */
	public void updateScpState(String t,String l) throws Exception
	{
		String sql="update ec_tenpaytransferlog set scpflag='0',scpstatechg='"+t+"' where transaction_id='"+l+"'";
		try
		{
			db.update(sql);
		}catch(Exception ex)
		{
			Log.error("�޸��ʽ��ʻ��������:",ex);
			throw ex;
		}
	}
	
	/**
	 * �޸�ת��QB scp��״̬
	 * @param ʱ��
	 * @param ���׵���
	 * @throws Exception ���ݿ��쳣
	 */
	public void updateQBState(String t,String l) throws Exception
	{
		String sql="update ec_QBtransferlog set scpFlag='0',scpstatechg='"+t+"' where tran_seq='"+l+"'";
		try
		{
			db.update(sql);
		}catch(Exception ex)
		{
			Log.error("�޸��ʽ��ʻ��������:",ex);
			throw ex;
		}
	}
	
	/**
	 * ��ȡӶ�����
	 * @param phonetype
	 * @param userPid
	 * @param phonePid
	 * @param userno
	 * @param flag
	 * @param payfee
	 * @param parentid
	 * @return
	 */
	public String[] getEmploy(String phonetype,String userPid,String phonePid,String userno,String flag,int payfee,int parentid){
		String cm_id;
		try {
			cm_id = db.getString("select cm_id from sys_valueRange where min<="+payfee+
					" and max>"+payfee);
		} catch (SQLException e1) {
			e1.printStackTrace();
			return null;
		}
		String sql=null;
		if("1".equals(flag)){//ֱӪ
			sql="select value from sys_employ2 where pid="+phonePid+
			" and type="+phonetype+" and cm_id='"+cm_id+"'";
		}else {//��ֱӪ
		if(!userPid.equals(phonePid)){
			sql="select value from sys_employ1 where pid="+phonePid+
			" and type="+phonetype+" and cm_id='"+cm_id+"'";
		}else{
			sql="select value0,value1 from wht_agentAnduser where userno='"+userno+
			"' and parentid="+parentid+" and type="+phonetype+" and cmid='"+cm_id+"'";
		}
		}
		try {
			return db.getStringArray(sql);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * �����û�ϵͳ��Ų�ѯ �˺�
	 * @param userno
	 * @return
	 */
	public String getFacctByUserno(String userno)
	{
		String sql="select fundacct from wht_facct where user_no='"+userno+"'";
		try
		{
		  return db.getString(sql);
		}catch(Exception ex)
		{
			Log.error("�����û�ϵͳ��Ż�ȡ�˻�ʧ��:",ex);
			return null;
		}
	}
	
	 /**
	  * �����û�id��ѯ �˺�
	  * @param userid
	  * @return  �û�ƽ̨�˻�
	  */
	public String getFacctByUserID(int userid)
	{
		String sql="select fundacct from wht_facct where user_no=(select user_no from sys_user where user_id="+userid+")";
		try
		{
		  return db.getString(sql);
		}catch(Exception ex)
		{
			Log.error("�����û�ϵͳ��Ż�ȡ�˻�ʧ��:",ex);
			return null;
		}
	}
	
	
}
