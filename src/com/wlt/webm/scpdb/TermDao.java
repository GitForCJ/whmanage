/**
 * 
 */
package com.wlt.webm.scpdb;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.wlt.webm.scpcommon.Constant;
import com.wlt.webm.scpcommon.RespCode;
import com.commsoft.epay.util.logging.Log;

/**
 * <p>@Description: ҵ����촦����</p>
 */ 
public class TermDao {
	
	/**
	 * ���ݿ����Ӷ���
	 */
	private DBService db = null;
	
	/**
	 * ���췽�� 
	 * @param db ���Ӷ���
	 */
	public TermDao(DBService db)
	{
		this.db = db;
	}
	
	/**
	 * �����ն�ID�����ն˱��
	 * @param posid �ն�ID
	 * @return	����String���͵��ն˱��
	 * @throws Exception 
	 */
	public String getTermAcct(String posid) throws Exception
	{
		String str = "";
		try
		{
			String sql = "select resourceid from ec_resources where posid='"+posid+"'";
			str = db.getString(sql);
		}catch(Exception ex)
		{
			Log.error("��ѯ�ն��ʺų���:",ex);
			throw ex;
		}
		
		return str;
	}
	/**
	 * �����ն�ID�����ն˰��ʽ��ʻ�
	 * @param posid �ն�ID
	 * @return ����String���͵��ʽ��ʻ�
	 * @throws Exception 
	 */
	public String getFundAcct(String posid) throws Exception
	{
		String str = "";
		try
		{
			StringBuffer sql = new StringBuffer();
//			sql.append("select fundacct from ec_terminal where termid=")
//			   .append("(select termid from ec_resources where posid='")
//			   .append(posid).append("')");
			sql.append("select fundacct from ec_resources where posid='")
			   .append(posid).append("'");
			str = db.getString(sql.toString());
			
			//Log.info("�����˻�---------->" + sql.toString());
			
		}catch(Exception ex)
		{
			Log.error("��ѯ�ն��ʺų���:",ex);
			throw ex;
		}
		
		return str;
	}
	
	/**����ͳһ֧���˺Ż�ȡ�������
	 * @param fundacct ͳһ֧���˺�
	 * @return �������
	 * @throws Exception
	 */
	public int getAccountleftByFundAcc(String fundacct) throws Exception
	{
		int accountleft = 0;
		try
		{
			StringBuffer sql = new StringBuffer();
			sql.append("select accountleft from ec_facct where fundacct='")
			   .append(fundacct).append("'");
			accountleft = db.getInt(sql.toString());
		}catch(Exception ex)
		{
			Log.error("��ѯ�ն��ʺų���:",ex);
			throw ex;
		}
		
		return accountleft;
	}
	
	/**
	 * ����ն���Ϣ
	 * @param resourceID  �ն�id
	 * @return �����ն���Ϣ
	 */
	public String[] getPosMes(String resourceID) {
		String sql = "select b.termid,b.termname,c.agentid,c.agentname " +
				"from ec_resources a,ec_terminal b,ec_agent c " +
				"where a.termid=b.termid and b.agent=c.agentid and a.resourceid='"
				+ resourceID  + "'";
		String[] str = null;
		try {
			str = db.getStringArray(sql);
		} catch (Exception ex) {
			Log.error("��÷����������ݳ���:", ex);
		}
		return str;
	}
	/**
	 * ��ý�����Ϣ
	 * @param
	 * @return
	 */
	public String[] getPosQryMes(String termAcct,String phone,String service) {
		
        SimpleDateFormat sdf=new SimpleDateFormat("yyyyMM");
        String date = sdf.format(new Date());
		String yearMonth = date.substring(2,6);
		String tableName = "ec_tradelog_"+yearMonth;
		
		String sql = "select tradetime,duefee from " + tableName + 
		"where resourceid='" + termAcct + "'" + 
		" and tradeobject='" + phone + "'" + 
		" and service='" + service + "'";
		Log.info("sql===sx22======"+sql.toString());
		String[] str = null;
		try {
			str = db.getStringArray(sql);
		} catch (Exception ex) {
			Log.error("��ý��׼�¼���ݳ���:", ex);
		}
		return str;
	}
	/**
	 * �����ն�ID�����ն�������������
	 * @param posid �ն�ID
	 * @return ����String���͵ĵ�������
	 * @throws Exception 
	 */
	public String getAreaCode(String posid) throws Exception
	{
		String str = "";
		try
		{
			StringBuffer sql = new StringBuffer();
			sql.append("select areacode from ec_terminal where termid=")
			   .append("(select termid from ec_resources where posid='")
			   .append(posid).append("')");
			str = db.getString(sql.toString());
		}catch(Exception ex)
		{
			Log.error("��ѯ�ն������������ų���:",ex);
			throw ex;
		}
		
		return str;
	}
	
	/**
	 * ���������Դ�Ƿ�����
	 * @param posid posID
	 * @return	����true��ʾ����,��֮������
	 * @throws Exception 
	 */
	public boolean isPosDrop(String posid) throws Exception
	{
		boolean check = false;
		try
		{
			check = db.hasData("select posid from ec_resources where posid='"+posid+"' and state in(1,3)");
			
			Log.debug("------select posid from ec_resources where posid='"+posid+"' and state in(1,3)");
			
		}catch(Exception ex)
		{
			Log.error("��ѯ������Դ�Ƿ�ͣ������������:",ex);
			throw ex;
		}
		
		return check;
	}
	
	/**
	 * ���������Դ��Ӧ���ն��û��Ƿ�����
	 * @param posid posID
	 * @return	����true��ʾ����,��֮������
	 * @throws Exception 
	 */
	public boolean isTermDrop(String posid) throws Exception
	{
		boolean check = false;
		StringBuffer sql = new StringBuffer();
		//
		sql.append("select termid from ec_terminal where termid=")
		   .append("(select termid from ec_resources where posid='")
		   .append(posid).append("' and state=1) and state=").append(Constant.TERM_NOMAL);
//		   .append(Constant.TERM_NEW_STATE).append(",")
//		   .append(Constant.TERM_REOPEN_STATE).append(")");
		
		try
		{
			check = db.hasData(sql.toString());
			
			Log.debug("------"+sql.toString());
			
		}catch(Exception ex)
		{
			Log.error("��ѯ������Դ��Ӧ���ն��û��Ƿ���������:" + posid,ex);
			throw ex;
		}
		
		return check;
	}
	
	/**
	 * @param termid
	 * @return �Ƿ�Ϊ�Ϸ��Ĵ��컧
	 * @throws Exception
	 */
	public boolean isTerminal(String termid) throws Exception
	{
		boolean check = false;
		StringBuffer sql = new StringBuffer();
		sql.append("select termid from ec_terminal where termid='")
			.append(termid).append("'");
		
		try
		{
			check = db.hasData(sql.toString());
			
			Log.debug("------"+sql.toString());
			
		}catch(Exception ex)
		{
			Log.error("��ѯ���컧�Ƿ�Ϸ�����:" + termid,ex);
			throw ex;
		}
		
		return check;
	}
	
	
	/**
	 * �ն��Ƿ�󶨵绰����
	 * @param posid	�ն�ID
	 * @param accnbr	�绰����
	 * @param type �Ƿ��
	 * @return	����true��ʾ�Ѱ󶨣���֮δ��
	 * @throws Exception 
	 */
	public boolean isBindAccnbr(String posid,String accnbr,int type) throws Exception
	{
		StringBuffer sql = new StringBuffer();
		boolean check = false;
		boolean hasPhone = true;
		try
		{
//			sql.append("select posid from ec_resourcesbind where posid='").append(posid)
//				.append("' and bindnbr='").append(accnbr).append("' ");
			sql.append("select a.resourceid from ec_resources a,ec_resourcesbind b where a.resourceid=b.resourceid and a.posid='")
			   .append(posid).append("' and b.bindnbr='").append(accnbr).append("' ");
			
			if(type >=0)
			{
				sql.append("and b.iscall=").append(type);
			}
			check = db.hasData(sql.toString());
			
			Log.debug("------" + sql.toString());
			
			sql = new StringBuffer();
			sql.append("select b.bindnbr from ec_resources a,ec_resourcesbind b where a.resourceid=b.resourceid and a.posid='")
			   .append(posid).append("' ");
			hasPhone = db.hasData(sql.toString());
			
			Log.debug("------" + sql.toString());
			
		}catch(Exception ex)
		{
			Log.error("��ѯ�ն˰󶨺������:",ex);
			throw ex;
		}
		return check || !hasPhone;
	}
//	public static void main(String[] args) {
//		LoadConfig.getConfig();
//		DBService db = null;
//		db = new DBService();
//		TermDao term = new TermDao(db);
//		try {
//			System.out.println(term.isBindAccnbr("080808080809","5583636958",-1));
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//	
	/**
	 * ��ѯ������Դ�Ƿ����
	 * @param posid  ��ԴID
	 * @return ����true��ʾ���ڣ���֮������
	 * @throws Exception
	 */
	public boolean isResources(String posid) throws Exception
	{
		StringBuffer sql = new StringBuffer();
		boolean check = false;
		try
		{
			sql.append("select posid from ec_resources where posid='")
			   .append(posid).append("'");

			check = db.hasData(sql.toString());
		}catch(Exception ex)
		{
			Log.error("��ѯ������Դ�Ƿ���ڳ���:",ex);
			throw ex;
		}
		return check;
	}
	
	/**
	 * �ն��Ƿ��psam��
	 * @param posid	�ն�ID
	 * @param psamCard	psam����
	 * @return	����true��ʾ�Ѱ󶨣���֮δ��
	 * @throws Exception 
	 */
	public boolean isBindPsamNo(String posid,String psamCard) throws Exception
	{
		StringBuffer sql = new StringBuffer();
		boolean check = false;
		//psam����ΪĬ�Ϻ�,ʾ�Ѱ�
		if(psamCard.equals("0000000000000000"))
		{
			return true;
		}
		try
		{
			sql.append("select termid from ec_resources where posid='").append(posid)
				.append("' and (psamcard1='").append(psamCard)
				.append("' or psamcard2='").append(psamCard).append("')");

			check = db.hasData(sql.toString());
			
			Log.debug("------" + sql.toString());
			
		}catch(Exception ex)
		{
			Log.error("��ѯ�ն˰�psam������:",ex);
			throw ex;
		}
		return check;
	}
	
	/**
	 * �����ն�ID�����ն�ʹ������
	 * @param posid �ն�ID
	 * @return ����String���͵��ն�ʹ������
	 * @throws Exception 
	 */
	public int getUseType(String posid) throws Exception
	{
		int type = 0;
		try
		{
			StringBuffer sql = new StringBuffer();
			sql.append("select usetype from ec_terminal where termid=")
			   .append("(select termid from ec_resources where posid='")
			   .append(posid).append("')");
			type = db.getInt(sql.toString());
			
			Log.debug("------" + sql.toString());
			
		}catch(Exception ex)
		{
			Log.error("��ѯ�ն�ʹ�����ͳ���:",ex);
			throw ex;
		}
		
		return type;
	}
	
	/**
	 * �����ն��û���Ϣ
 	 * @param termId  �ն˱��
	 * @param servid ҵ������
	 * @param areaCode ����
	 * @param custid  �ͻ����
	 * @param usertype ʹ������
	 * @param fundAcct  ���ʽ��ʻ�
	 * @param buildTime ����ʱ��
	 * @param state ״̬
	 * @throws Exception
	 */
	public void insertTerminal(String termId,String servid,String areaCode,
			String custid,String usertype,String fundAcct,String buildTime,int state) throws Exception
	{
		StringBuffer sql = new StringBuffer();
		String[] str = new String[2];
		String nu=null;
		//�����ն��û���Ϣ
		sql.append("insert into ec_terminal values('")
		   .append(termId).append("',null,'") //����Ƭ��,��0
		   .append(areaCode).append("',")
		   .append(custid).append(",null,") //����������,��0
		   .append(usertype).append(",'")
		   .append(fundAcct).append("',null,'") //��������,��0
		   .append(buildTime).append("','00000000','00000000',")
		   .append(state).append(",'")
		   .append(buildTime).append("',' ')");  //����ʱ,״̬ʱ��Ϳ���ʱ����ͬ
		str[0] = sql.toString();
		Log.debug("--�����ն��û���Ϣ1-:"+sql.toString());
		
		//����ƽ̨���۷��ն��û���ϵ
		sql.delete(0,sql.length());
		sql.append("insert into ec_sp_terminal values('")
		   .append(termId).append("',").append(servid).append(")");
		str[1] = sql.toString();
		Log.debug("--�����ն��û���Ϣ2-:"+sql.toString());
		
		
		try
		{
			db.update(str);
		}catch(Exception ex)
		{
			Log.error("�����ն��û���Ϣ����:",ex);
			throw ex;
		}
	}
	
	/**
	 * ����������Դ��Ϣ
	 * @param posid  ��ԴID
	 * @param termId  �ն�ID
	 * @param posType ��Դ����
	 * @param addr   ��װ��ַ
	 * @param posModel  ��Դ�ͺ�
	 * @param psam  psam����1
	 * @param psamR psam����2
	 * @param isPrint �Ƿ����ô�ӡ��
	 * @param state  ״̬
	 * @param disTime ���ʱ��
	 * @throws Exception
	 */
	public void insertResources(String posid,String termId,int posType,
			String addr,String posModel,String psam,String psamR,int isPrint,int state,String disTime) throws Exception
	{
		StringBuffer sql = new StringBuffer();
		sql.append("insert into ec_resources values('")
		   .append(posid).append("','")
		   .append(termId).append("',")
		   .append(posType).append(",'")
		   .append(addr).append("','")
		   .append(posModel).append("','")
		   .append(psam).append("','")
		   .append(psamR).append("',")
		   .append(isPrint).append(",0,")
		   .append(state).append(",'")
		   .append(disTime).append("')");
		Log.debug("--����������Դ��Ϣ-:"+sql.toString());
		try
		{
			db.update(sql.toString());
		}catch(Exception ex)
		{
			Log.error("����������Դ��Ϣ����:",ex);
			throw ex;
		}
	}
	
	/**
	 * ����������Դ����Ϣ
	 * @param posid  ��ԴID
	 * @param bindnbr  �󶨺���
	 * @throws Exception
	 */
	public void insertResourceBind(String posid,String bindnbr) throws Exception
	{
		StringBuffer sql = new StringBuffer();
		sql.append("insert into ec_resourcesbind values(")
		   .append("ec_resourcesbind_id.nextval,'").append(posid)
		   .append("','").append(bindnbr).append("',1)");
		
		Log.debug("--����������Դ����Ϣ-:"+sql.toString());
		
		try
		{
			db.update(sql.toString());
		}catch(Exception ex)
		{
			Log.error("����������Դ����Ϣ����:",ex);
			throw ex;
		}
	}
	
	/**
	 * �����ն��û����ʽ��ʻ�
	 * @param fundAcct �ʽ��ʻ�
	 * @param termId  �ն˱��
	 * @throws Exception
	 */
	public void updateTermFacct(String fundAcct,String termId) throws Exception
	{
		StringBuffer sql = new StringBuffer();
		sql.append("update ec_terminal set fundacct='")
		   .append(fundAcct).append("' where termid='")
		   .append(termId).append("'");
		
		Log.debug("--�����ն��û����ʽ��ʻ�--:"+sql.toString());
		
		try
		{
			db.update(sql.toString());
		}catch(Exception ex)
		{
			Log.error("�����ն��û����ʽ��ʻ�����:",ex);
			throw ex;
		}
	}
	
	/**
	 * �����ն��û�״̬
	 * @param state  �û�״̬
	 * @param termId �ն˱��
	 * @param agentId �����̱��
	 * @param stsTime ����ʱ��
	 * @throws Exception
	 */
	public void updateTermState(int state,String termId,long agentId,String stsTime) throws Exception
	{
		StringBuffer sql = new StringBuffer();
		sql.append("update ec_terminal set state=")
		   .append(state).append(",distime='").append(stsTime)
		   .append("' where 1=1");
		
		if(termId != null && !termId.trim().equals(""))
		{
			sql.append(" and termid='").append(termId).append("'");
		}
		
		if(agentId > 0)
		{
			sql.append(" and agent=").append(agentId);
		}
		
		Log.debug("--�����ն��û�״̬--:"+sql.toString());
		try
		{
			db.update(sql.toString());
		}catch(Exception ex)
		{
			Log.error("�����ն��û�״̬����:",ex);
			throw ex;
		}
	}
	
	/**
	 * �޸�������Դ��Ϣ
	 * @param posid  ��ԴID
	 * @param termId  �ն�ID
	 * @param posType ��Դ����
	 * @param addr   ��װ��ַ
	 * @param posModel  ��Դ�ͺ�
	 * @param psam  psam����1
	 * @param psamR psam����2
	 * @param isPrint �Ƿ����ô�ӡ��
	 * @param state  ״̬
	 * @throws Exception
	 */
	public void updateResources(String posid, String termId, int posType,
			String addr, String posModel, String psam, String psamR,
			int isPrint, int state) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("update ec_resources set termid='").append(termId)
			.append("',type=").append(posType).append(",address='")
				.append(addr).append("',model='").append(posModel).append("',psamcard1='")
				.append(psam).append("',psamcard2='").append(psamR).append("',isprint=").append(
						isPrint).append(",state=").append(state)
						.append(" where posid='").append(posid).append("'");
		
		Log.debug("--�޸�������Դ��Ϣ--:"+sql.toString());
		
		try {
			db.update(sql.toString());
		} catch (Exception ex) {
			Log.error("�޸�������Դ��Ϣ����:", ex);
			throw ex;
		}
	}
	
	/**
	 * ����������Դ״̬
	 * @param state  �û�״̬
	 * @param posid  pos���
	 * @param termId  �ն˱��
	 * @param stsTime ����ʱ��
	 * @throws Exception
	 */
	public void updateResourcesState(int state,String posid,String termId,String stsTime) throws Exception
	{
		StringBuffer sql = new StringBuffer();
		sql.append("update ec_resources set state=")
		   .append(state).append(",distime='").append(stsTime)
		   .append("' where 1=1");
		
		if(posid != null && !posid.trim().equals(""))
		{
			sql.append("and posid='").append(posid).append("' ");
		}
		
		if(termId != null && !termId.trim().equals(""))
		{
			sql.append("and termid='").append(termId).append("'");
		}
		
		Log.debug("--����������Դ״̬--:"+sql.toString());
		try
		{
			db.update(sql.toString());
		}catch(Exception ex)
		{
			Log.error("����������Դ״̬����:",ex);
			throw ex;
		}
	}
	
	/**
	 * �޸�������Դ����Ϣ
	 * @param posid  ��ԴID
	 * @param bindnbr  �󶨺���
	 * @throws Exception
	 */
	public void updateResourceBind(String posid,String bindnbr) throws Exception
	{
		StringBuffer sql = new StringBuffer();
		sql.append("update ec_resourcesbind set ")
		   .append("bindnbr='").append(bindnbr)
		   .append("' where posid='").append(posid).append("'");
		
		Log.debug("--�޸�������Դ����Ϣ--:"+sql.toString());
		
		try
		{
			db.update(sql.toString());
		}catch(Exception ex)
		{
			Log.error("�޸�������Դ����Ϣ����:",ex);
			throw ex;
		}
	}
	
	/**
	 * ���ݴ������޸�������Դ״̬
	 * @param state  �û�״̬
	 * @param agentId  �����̱��
	 * @param stsTime ����ʱ��
	 * @throws Exception
	 */
	public void updateResOfAgent(int state,long agentId,String stsTime) throws Exception
	{
		StringBuffer sql = new StringBuffer();
		sql.append("update ec_resources set state=")
		   .append(state).append(",distime='").append(stsTime)
		   .append("' where termid in(")
		   .append("select termid from ec_terminal where agent=")
		   .append(agentId).append(")");
		
		try
		{
			db.update(sql.toString());
		}catch(Exception ex)
		{
			Log.error("���ݴ������޸�������Դ״̬����:",ex);
			throw ex;
		}
	}
	
	
	/**
	 * ��ѯӶ��
	 * @param posid �ն�ID
	 * @param month �·�
	 * @return ����Ӷ��
	 * @throws Exception
	 */
	public int getCommision(String posid) throws Exception
	{
		String  comm = "";
		int fee =0;
		try
		{
			StringBuffer sql = new StringBuffer();
			sql.append("select fundacct from ec_resources where posid ='")
			   .append(posid).append("'");
			Log.info("000000000000000012"+sql.toString());
			comm = db.getString(sql.toString())+"03";
			sql.delete(0, sql.length());
			sql.append("select usableleft from ec_childfacct where childfacct ='")
			   .append(comm).append("'");
			fee=db.getInt(sql.toString());
			
		}catch(Exception ex)
		{
			Log.error("��ѯӶ�����:",ex);
			throw ex;
		}
		
		return fee;
	}
	
	/**
	 * ��ѯ���ŷ�����ˮ��
	 * @throws Exception
	 */
	public String getDxRollBack(String termAcct,String phone,String service) throws Exception
	{
		String str = "";
        SimpleDateFormat sdf=new SimpleDateFormat("yyyyMM");
        String date = sdf.format(new Date());
		String yearMonth = date.substring(2,6);
		String tableName = "ec_tradelog_"+yearMonth;
		try
		{
			StringBuffer sql = new StringBuffer();
			sql.append("select  first 1 tradeserial  from ").append(tableName)
			.append(" where tradeobject='").append(phone)
			.append("' and resourceid='").append(termAcct)
			.append("' and service='").append(service)
			.append("' order by tradetime  desc ");
			str = db.getString(sql.toString());
			Log.info("sql===sx======"+sql.toString());
		}catch(Exception ex)
		{
			Log.error("��ѯ���ŷ�����ˮ�ų���:",ex);
			throw ex;
		}
		
		return str;
	}
	
	/**
	 * ��ѯ�����̶�Ӧ���ն��û�
	 * @param agentId �����̱��
	 * @return ���ز�ѯ�����̶�Ӧ���ն��û�
	 * @throws Exception
	 */
	public List getTermIdList(int agentId) throws Exception
	{
		StringBuffer sql = new StringBuffer();
		sql.append("select termid from ec_terminal where agent=").append(agentId);
		List rsList = null;
		
		try
		{
			rsList = db.getList(sql.toString());
		}catch(Exception ex)
		{
			Log.error("��ѯ�����̶�Ӧ���ն��û�����:",ex);
			throw ex;
		}
		
		return rsList;
	}
	
	/**
	 * ��ѯ�������Ƿ����ն��û�
	 * @param agentId
	 * @return �Ǳ�ʾ���ն� ���ʾû��
	 * @throws Exception
	 */
	public boolean checkAgent(long agentId) throws Exception
	{
		StringBuffer sql = new StringBuffer();
		sql.append("select termid from ec_terminal where state<=")
		   .append(Constant.TERM_REOPEN_STATE).append(" and agent=").append(agentId);
		boolean check = false;
		
		try
		{
			check = db.hasData(sql.toString());
		}catch(Exception ex)
		{
			Log.error("��ѯ�������Ƿ����ն��û�����:",ex);
			throw ex;
		}
		
		return check;
	}
	/**
	 * �����۷�����ʵ����Ż�ȡ�ն��û����
	 * @param servid �ͻ����
	 * @return �����ն��û����
	 * @throws Exception
	 */
	public String getTermIdOfServId(String servid) throws Exception
	{
		StringBuffer sql = new StringBuffer();
		sql.append("select termid from ec_sp_terminal where servid=").append(servid);
		String str = null;
		try
		{
			str = db.getString(sql.toString());
		}catch(Exception ex)
		{
			Log.error("�����۷�����ʵ���Ż�ȡ�ն��û���ų���:",ex);
		}
		
		return str;
	}
	
	/**
	 * ����ն��û��Ƿ�����
	 * @param termid �ն��û�
	 * @return	����true��ʾ������,��֮δ����
	 */
	public boolean isTermSett(String termid)
	{
		boolean check = false;
		StringBuffer sql = new StringBuffer();
		sql.append("select termid from ec_termsett where state=1 and termid='")
		  .append(termid).append("'");
		  
		try
		{
			check = db.hasData(sql.toString());
		}catch(Exception ex)
		{
			Log.error("��ѯ�ն��û��Ƿ��������:",ex);
		}
		
		return check;
	}
	
	/**
	 * ��ȡ�ն��û�������
	 * @param termid  �ն��˺�
	 * @return ������
	 * @throws Exception
	 */
	public int getTermSettFee(String termid)throws Exception
	{
		StringBuffer sql = new StringBuffer();
		sql.append("select sum(settfee) from ec_termsett where state=1 and termid='")
		   .append(termid).append("'");
		
		int fee = 0;
		try
		{
			fee = db.getInt(sql.toString());
		}catch(Exception ex)
		{
			Log.error("��ȡ�ն��û����������:",ex);
		}
		
		return fee;
	}
	
	/**
	 * @return ��ȡ����ת�˹��ܿ���
	 */
	public String getUpswitch() {
		String upswitch = null;
		StringBuffer sql = new StringBuffer();
		sql.append("select upswitch from ec_switch where cftswitch=0");
		try {
			upswitch = db.getString(sql.toString());
		} catch (Exception ex) {
			Log.error("��ȡ����ת�˹��ܿ��س���:",ex);
		}
		return upswitch;
	}
	
	/**
	 * @return ��ȡ����ת�˹��ܿ���
	 */
	public String getTYswitch() {
		String upswitch = null;
		StringBuffer sql = new StringBuffer();
		sql.append("select upswitch from ec_switch where cftswitch=6");
		try {
			upswitch = db.getString(sql.toString());
		} catch (Exception ex) {
			Log.error("��ȡ����ת�˹��ܿ��س���:",ex);
		}
		return upswitch;
	}
	
	/**
	 * �����ն˱�ŵõ��绰����
	 * @param resourceid �ն˱��
	 * @param flag   ģ���ʶ
	 * @return	����String���͵ĵ绰����
	 * @throws Exception 
	 */
	public String getPhone(String resourceid,String flag) throws Exception
	{
		String str = "";
		String sql ="select a.phone from ec_customer a,ec_terminal b,ec_resources c " +
				"where a.custid=b.custid and b.termid=c.termid and c.resourceid='"+resourceid+"'";
//		if(flag.equals(Constant.IPC_FLAG)){
//			sql = "select bindnbr from ec_resourcesbind where resourceid='"+resourceid+"'";
//		}else if(flag.equals(Constant.PC_FLAG)||flag.equals(Constant.FEP_FLAG)){
//			sql = "select bindinfo from ec_pcip where type=3 and resourceid='"+resourceid+"'";
//		}else{
//			sql = "select posid from ec_resources where type=3 and posid[1,8]!='11111111' and resourceid='"+resourceid+"'";
//		}
		try
		{
			str = db.getString(sql);
		}catch(Exception ex)
		{
			Log.error("��ѯ�ն˵绰�������:",ex);
			throw ex;
		}
		
		return str;
	}
	
	
	
    public static void main(String[] args) {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyyMM");
        String date = sdf.format(new Date());
        System.out.println(date.substring(2, 6));
        }
}
