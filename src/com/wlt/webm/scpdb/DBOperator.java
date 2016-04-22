package com.wlt.webm.scpdb;

import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.*;

import com.wlt.webm.scpcommon.Constant;
import com.wlt.webm.scputil.DateParser;
import com.wlt.webm.scputil.Tools;
import com.wlt.webm.scputil.bean.CustErrorOrder;
import com.wlt.webm.scputil.bean.CustOrderLog;
import com.commsoft.epay.util.logging.Log;

/**
 * <p>@Title: ���ڿ����ص��Ӵ���ϵͳҵ���������ģ��</p>
 * <p>@Description: ���ݿ������</p>
 */
public class DBOperator {

	private DBService db = null;

	/**
	 * @param db ���ݿ������
	 * 
	 */
	public DBOperator(DBService db) {
		this.db = db;
	}

	/**
	 * ����ҵ�����ͻ�ȡ���շ�����
	 * @param service ҵ������
	 * @return ���շ�����
	 */
	public String getTrusteeName(String service) {
		StringBuffer sql = new StringBuffer();
		sql.append("select trusteename from ec_trustservice a,ec_trustee b ")
				.append("where a.trustcode=b.trusteeid and a.service='")
				.append(service).append("'");
		String str = null;
		try {
			str = db.getString(sql.toString());
		} catch (Exception ex) {
			Log.error("��ѯҵ�����ͻ�ȡ���շ����Ƴ���:", ex);
		}
		return str;
	}
	/**
	 * �����ֻ�����ǰ7λ��ȡ�ֻ�������
	 */
	public String getPhonecode(String code) {
		StringBuffer sql = new StringBuffer();
		sql.append("select areacode from ec_phonecode a ")
				.append("where a.phone='")
				.append(code).append("'");

		String str = null;
		try {
			str = db.getString(sql.toString());
			if(str==null){str="0000";}
		} catch (Exception ex) {
			Log.error("�����ֻ�����ǰ7λ��ȡ�ֻ������س���:", ex);
		}
		return str;
	}
	/**
	 * 
	 * @param code
	 * @return fee
	 */
	public String getFee(String code) {
		StringBuffer sql = new StringBuffer();
		sql.append("select a.fundacct from ec_facct a,ec_resources b where a.fundacct=b.fundacct and ")
				.append(" b.resourceid='")
				.append(code).append("'");

		String str1 = null;
		String str2 = null;
		try {
			str1 = db.getString(sql.toString());
			sql.delete(0,sql.length());
			sql.append("select usableleft from ec_childfacct where ")
			.append(" childfacct='")
			.append(str1).append("02").append("'");
			str2 = db.getString(sql.toString());
		} catch (Exception ex) {
			Log.error("�����ն˱�Ų����", ex);
		}
		return str2;
	}
	/**
	 * ����ҵ�����ͻ�ȡ���շ�����
	 * @param resouceId 
	 * @param payNo 
	 */
	public void deletePrintMes(String resouceId,String payNo) {
		StringBuffer sql = new StringBuffer();
		sql.append("delete ec_reprint where resourceid='").append(resouceId)
				.append("' and account='")
				.append(payNo).append("'");
		Log.info("deletePrintMes"+sql);
		try {
			db.update(sql.toString());
		} catch (Exception ex) {
			Log.error("��ѯҵ�����ͻ�ȡ���շ����Ƴ���:", ex);
		}
	}

	/**
	 * ��÷�����������
	 * @param tradeSeqNo  ������ˮ��
	 * @return ���ط�������
	 */
	public String[] getYplWriteOffInfo(String tradeSeqNo) {
		String date = DateParser.getNowDate();
		String month = date.substring(2, 6);
		String sql = "select tradeserial,writeoff,tradeobject,fee,tradetime from ec_writeoff_"
				+ month + " where tradeserial='" + tradeSeqNo + "'";
		String[] str = null;
		try {
			str = db.getStringArray(sql);
		} catch (Exception ex) {
			Log.error("��÷����������ݳ���:", ex);
		}
		return str;
	}
	/**
	 * ��÷�����������
	 * @param tradeSeqNo  ������ˮ��
	 * @return ���ط�������
	 */
	public String[] getWriteOffInfo(String tradeSeqNo) {
		String date = DateParser.getNowDate();
		String month = date.substring(2, 6);
		String sql = "select areacode,tradeobject,writeoff from ec_writeoff_"
				+ month + " where tradeserial='" + tradeSeqNo + "'";
		String[] str = null;
		try {
			str = db.getStringArray(sql);
		} catch (Exception ex) {
			Log.error("��÷����������ݳ���:", ex);
		}
		return str;
	}
	/**
	 * ��÷�����������
	 * @param tradeSeqNo  ������ˮ��
	 * @return ���ط�������
	 */
	public String getCMCCWriteOffInfo(String tradeSeqNo) {
		String date = DateParser.getNowDate();
		String month = date.substring(2, 6);
		String sql = "select writeoff from ec_writeoff_"
				+ month + " where tradeserial='" + tradeSeqNo + "'";
		String str = null;
		try {
			str = db.getString(sql);
		} catch (Exception ex) {
			Log.error("��÷����������ݳ���:", ex);
		}
		return str;
	}

	/**
	 * ����ͻ���Ϣ
	 * @param custid �ͻ����
	 * @param cusname �ͻ�����
	 * @param areaCode ����
	 * @param idType ֤������
	 * @param idCard ֤������
	 * @param bank  ������
	 * @param bankCard  ���п���
	 * @param buildtime ����ʱ��
	 * @param buildaddr �����ص�
	 * @param phone ��ϵ�绰
	 * @param address ��ϵ��ַ
	 * @throws Exception ���ݿ��쳣
	 */
	public void insertCustomer(String custid, String cusname, String areaCode,
			String idType, String idCard, String bank, String bankCard,
			String buildtime, String buildaddr, String phone, String address)
			throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select custid from ec_customer where custid=").append(
				custid);

		try {
			boolean check = db.hasData(sql.toString());
			if (check) {
				return;
			}
		} catch (Exception ex) {
			Log.error("����ͻ���Ϣ����:", ex);
			throw ex;
		}
		sql.delete(0, sql.length());
		sql.append("insert into ec_customer values(").append(custid).append(
				",'").append(cusname).append("','").append(areaCode).append(
				"',").append(idType).append(",'").append(idCard).append("','")
				.append(bank).append("','").append(bankCard).append("','")
				.append(buildtime).append("','").append(buildaddr)
				.append("','").append(phone).append("','").append(address)
				.append("')");

		Log.debug("==����ͻ���Ϣsql==:" + sql.toString());
		try {
			db.update(sql.toString());
		} catch (Exception ex) {
			Log.error("����ͻ���Ϣ����:", ex);
			throw ex;
		}

	}

	/**
	 * �޸Ŀͻ���Ϣ
	 * @param custid �ͻ����
	 * @param cusname �ͻ�����
	 * @param areaCode ����
	 * @param idType ֤������
	 * @param idCard ֤������
	 * @param bank  ������
	 * @param bankCard  ���п���
	 * @param buildtime ����ʱ��
	 * @param buildaddr �����ص�
	 * @param phone ��ϵ�绰
	 * @param address ��ϵ��ַ
	 * @throws Exception ���ݿ��쳣
	 */
	public void updateCustomer(String custid, String cusname, String areaCode,
			String idType, String idCard, String bank, String bankCard,
			String buildtime, String buildaddr, String phone, String address)
			throws Exception {
		String[] param = { cusname, areaCode, idType, idCard, bank, bankCard,
				buildtime, buildaddr, phone, address, custid };
		StringBuffer sql = new StringBuffer();
		sql
				.append(
						"update ec_customer set name=?,areacode=?,idtype=?,idcard=?,bank=?,bankcard=?,")
				.append(
						"builddatetime=?,buildaddress=?,phone=?,address=? where custid=?");

		Log.debug("--�޸Ŀͻ�����--:" + sql.toString());

		try {
			db.update(sql.toString(), param);
		} catch (Exception ex) {
			Log.error("�޸Ŀͻ���Ϣ����:", ex);
			throw ex;
		}
	}

	/**
	 * ���ɴ����̱��
	 * @param areaCode  ����
	 * @param agentName ����������
	 * @return ���ش����̱��
	 * @throws Exception
	 */
	public long createAgentId(String areaCode, String agentName)
			throws Exception {
		StringBuffer sql = new StringBuffer();
		String[] params = null;
		int parentid = 0;
		//���������
		sql.append("select areaid from ec_area where areacode='").append(
				areaCode).append("'");
		try {
			parentid = db.getInt(sql.toString());
		} catch (Exception ex) {
			Log.error("��������ų���:", ex);
			throw ex;
		}
		//���ɴ�����
		sql.delete(0, sql.length());
		sql.append("insert into ec_syslevel values(0,?,?,?,2,0)");
		params = new String[] { agentName, String.valueOf(parentid), "1" };
		//�����̱��
		long agentid = 0;

		try {
			agentid = db.getGeneratedKey(sql.toString(), params);
		} catch (Exception ex) {
			Log.error("���ɴ����̱�ų���:", ex);
			throw ex;
		}

		return agentid;
	}

	/**
	 * �����������Ϣ
	 * @param custid  �ͻ����
	 * @param servid  �۷�����ʵ����
	 * @param agentid �����̱��
	 * @param agentName ����������
	 * @throws Exception
	 */
	public void insertAgent(String custid, String servid, long agentid,
			String agentName) throws Exception {
		StringBuffer sql = new StringBuffer();
		String[] params = null;
		sql.append("insert into ec_agent values(?,?,?,1)");
		params = new String[] { String.valueOf(agentid), agentName, custid };

		try {
			db.update(sql.toString(), params);
		} catch (Exception ex) {
			Log.error("��Ӵ�������Ϣ����:", ex);
			throw ex;
		}
		//ƽ̨���۷���ϵ��
		sql.delete(0, sql.length());
		sql.append("insert into ec_sp_agent values(").append(agentid).append(
				",").append(servid).append(")");
		try {
			db.update(sql.toString());
		} catch (Exception ex) {
			Log.error("���ƽ̨���۷������ϵ����:", ex);
			throw ex;
		}
	}

	/**
	 * ����������ʽ��ʻ���ϵ
	 * @param agentid �����̱��
	 * @param fundAcct �ʽ��ʻ�
	 * @throws Exception
	 */
	public void insertAgentFacct(int agentid, String fundAcct) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("insert into ec_agentfacct_grp values(").append(agentid)
				.append(",").append(fundAcct).append(",0)");

		Log.debug("--����������ʽ��ʻ���ϵϢ--:" + sql.toString());

		try {
			db.update(sql.toString());
		} catch (Exception ex) {
			Log.error("��Ӵ����̺��ʽ��ʻ���ϵ����:", ex);
			throw ex;
		}
	}

	/**
	 * �޸Ĵ����̰���Ϣ
	 * @param agentid �����̱��
	 * @param fundAcct �ʽ��ʻ�
	 * @param state ״̬
	 * @throws Exception
	 */
	public void updateAgentFacct(int agentid, String fundAcct, int state)
			throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("update ec_agentfacct_grp set state=").append(state).append(
				" where agentid=").append(agentid).append(" and fundacct='")
				.append(fundAcct).append("'");

		Log.debug("--�޸Ĵ����̰���Ϣ--:" + sql.toString());

		try {
			db.update(sql.toString());
		} catch (Exception ex) {
			Log.error("�޸Ĵ����̰���Ϣ����:", ex);
			throw ex;
		}
	}

	/**
	 * �޸Ĵ�����״̬
	 * @param agentid �����̱��
	 * @param state ״̬
	 * @throws Exception ���ݿ��쳣
	 */
	public void updateAgent(long agentid, int state) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("update ec_agent set state=").append(state).append(
				" where agentid=").append(agentid);

		Log.debug("--�޸Ĵ�����״̬--:" + sql.toString());
		try {
			db.update(sql.toString());
		} catch (Exception ex) {
			Log.error("�޸Ĵ�����״̬����:", ex);
			throw ex;
		}
	}

	/**
	 * ���ݿͻ���Ż�ȡ�����̱��
	 * @param custid �ͻ����
	 * @return ���ش����̱��
	 * @throws Exception
	 */
	public int getAgentId(String custid) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select agentid from ec_agent where custid=").append(custid);
		int agent = 0;
		try {
			agent = db.getInt(sql.toString());
		} catch (Exception ex) {
			Log.error("���ݿͻ���ȡ�����̱�ų���:", ex);
			throw ex;
		}

		return agent;
	}

	/**
	 * �����۷�����ʵ����Ż�ȡ�����̱��
	 * @param servid �ͻ����
	 * @return ���ش����̱��
	 */
	public int getAgentIdOfServId(String servid) {
		StringBuffer sql = new StringBuffer();
		sql.append("select agentid from ec_sp_agent where servid=").append(
				servid);
		int agent = -1;
		try {
			agent = db.getInt(sql.toString());
		} catch (Exception ex) {
			Log.error("�����۷�����ʵ���Ż�ȡ�����̱�ų���:", ex);
		}

		return agent;
	}

	/**
	 * ���������Ƿ����
	 * @param agentid ������
	 * @return	����true��ʾ�ѽ���,��֮δ����
	 */
	public boolean isAgentSett(String agentid) {
		boolean check = false;
		String month = DateParser.getNowMonth();
		String preMonth = DateParser.getPreMonth(month, -1);
		StringBuffer sql = new StringBuffer();
		sql.append("select agentid from ec_agentsett where month in('").append(
				month).append("','").append(preMonth)
				.append("') and agentid='").append(agentid).append("'");
		try {
			check = db.hasData(sql.toString());
		} catch (Exception ex) {
			Log.error("��ѯ�������Ƿ�������:", ex);
		}

		return check;
	}

	/**
	 * ��ȡ������������
	 * @param agentid  ������
	 * @return ������
	 */
	public int getAgentSettFee(String agentid) {
		StringBuffer sql = new StringBuffer();
		sql
				.append(
						"select sum(settfee) from ec_agentsett where state=1 and agentid='")
				.append(agentid).append("'");

		int fee = 0;
		try {
			fee = db.getInt(sql.toString());
		} catch (Exception ex) {
			Log.error("��ȡ���������������:", ex);
		}

		return fee;
	}

	/**
	 * ��ѯ��ͨ�ɷ���ֵ
	 * @param posid �ն˱��
	 * @param services ҵ������
	 * @return ���ؽɷ���ֵ��Ϣ
	 * @throws Exception
	 */
	public List getFillFee(String posid, String services[]) throws Exception {
		StringBuffer sql = new StringBuffer();
		StringBuffer service = new StringBuffer("");
		for(int i = 0;i < services.length-1;i++){
			service.append("'").append(services[i]).append("',");
		}
		service.append("'").append(services[services.length-1]).append("'");
		//�������,���������,����ֵ���,����ֵ
		sql.append("select id,name from ec_fillfee  \n")
			.append("where state=0 and service='01011' \n")
			//.append("and service in(").append(service).append(")")
			.append(" order by id");
		
		List rs = null;
		try {
			rs = db.getList(sql.toString());
		} catch (Exception ex) {
			Log.error("��ѯ�ɷ���ֵ����:", ex);
			throw ex;
		}
		
		return rs;
	}
	/**
	 * ��ѯ�ƶ��ɷ���ֵ
	 * @param posid �ն˱��
	 * @param services ҵ������
	 * @return ���ؽɷ���ֵ��Ϣ
	 * @throws Exception
	 */
	public List getFee(String posid, String services[]) throws Exception {
		StringBuffer sql = new StringBuffer();
		StringBuffer service = new StringBuffer("");
		for(int i = 0;i < services.length-1;i++){
			service.append("'").append(services[i]).append("',");
		}
		service.append("'").append(services[services.length-1]).append("'");
		//�������,���������,����ֵ���,����ֵ
		sql.append("select id,name from ec_fillfee  \n")
			.append("where state=0 and service='01021'  \n")
			//.append("and service in(").append(service).append(")")
			.append(" order by id");
		
		List rs = null;
		try {
			rs = db.getList(sql.toString());
		} catch (Exception ex) {
			Log.error("��ѯ�ɷ���ֵ����:", ex);
			throw ex;
		}
		
		return rs;
	}
	/**
	 * ��ѯ�����ɷ���ֵ
	 * @param posid �ն˱��
	 * @return ���ؽɷ���ֵ��Ϣ
	 * @throws Exception
	 */
	public List getTZFillFee(String posid) throws Exception {
		StringBuffer sql = new StringBuffer();
		StringBuffer service = new StringBuffer("");
		//�������,���������,����ֵ���,����ֵ
		sql.append("select id,name from ec_fillfee  \n")
			.append("where state=0 and service='01012' \n")
			.append(" order by id");
		
		List rs = null;
		try {
			rs = db.getList(sql.toString());
		} catch (Exception ex) {
			Log.error("��ѯ�ɷ���ֵ����:", ex);
			throw ex;
		}
		
		return rs;
	}
	
	/**
	 * �����ն˱�Ų�ѯ���ʽ��˺�
	 * @param resourceid �ն˱��
	 * @return ���ʽ��˺���Ϣ
	 * @throws Exception
	 */
	public String getBindFacct(String resourceid) throws Exception {
		StringBuffer sql = new StringBuffer();
		String fundAcct="";
		sql.append("select fundacct from  wlt_facct   \n")
		.append("where  termID ='").append(resourceid).append("'");
		
		try {
			fundAcct = db.getString(sql.toString());
		} catch (Exception ex) {
			Log.error("��ѯ���컧���а��˺�:", ex);
			throw ex;
		}
		return fundAcct;
	}
	/**
	 * ���ݴ��컧��ѯ��������������
	 * @param termId ���컧���
	 * @return ���ʽ��˺���Ϣ
	 * @throws Exception
	 */
	public String getBindTYNAMEFacct(String termId) throws Exception {
		StringBuffer sql = new StringBuffer();
		String name="";
		sql.append("select a.tybankna from  ec_terminal a,ec_resources b where a.termid=b.termid   \n")
		.append("and  b.posid ='").append(termId).append("'");
		
		try {
			name = db.getString(sql.toString());
		} catch (Exception ex) {
			Log.error("���ݴ��컧��ѯ������������������:", ex);
			throw ex;
		}
		return name;
	}
	/**
	 * ���ݴ��컧��ѯ��������������
	 * @param termId ���컧���
	 * @return ���ʽ��˺���Ϣ
	 * @throws Exception
	 */
	public String getBindTYBANKNAMEFacct(String termId) throws Exception {
		StringBuffer sql = new StringBuffer();
		String name="";
		sql.append("select a.tybankname from ec_terminal a,ec_resources b where a.termid=b.termid   \n")
		.append("and  b.posid ='").append(termId).append("'");
		
		try {
			name = db.getString(sql.toString());
		} catch (Exception ex) {
			Log.error("���ݴ��컧��ѯ�������������ֳ���:", ex);
			throw ex;
		}
		return name;
	}
	/**
	 * ���ݴ��컧��ѯ�������ǩԼID 
	 * @param termId ���컧���
	 * @return ���ʽ��˺���Ϣ
	 * @throws Exception
	 */
	public String getBindTYCid(String termId) throws Exception {
		StringBuffer sql = new StringBuffer();
		String cid="";
		sql.append("select a.cid from ec_terminal a,ec_resources b where a.termid=b.termid   \n")
		.append("and  b.posid ='").append(termId).append("'");
		
		try {
			cid = db.getString(sql.toString());
		} catch (Exception ex) {
			Log.error("���ݴ��컧��ѯ�������ǩԼID����:", ex);
			throw ex;
		}
		return cid;
	}
	
	/**
	 * @param name
	 * @return ������������еı��
	 */
	public String getBindTYBANKNUMFacct(String name){
		String num="00";
		if("��������".equals(name)){
			num="60";
		}
		else if("�й�����".equals(name)){
			num="61";
		}
		else if("��������".equals(name)){
			num="62";
		}
		else if("ũҵ����".equals(name)){
			num="63";
		}
		else if("�㶫��ͨ����".equals(name)){
			num="64";
		}
		else if("��������".equals(name)){
			num="65";
		}
		else if("��������".equals(name)){
			num="66";
		}
		else if("�㷢����".equals(name)){
			num="68";
		}
		else if("��������".equals(name)){
			num="69";
		}
		else if("��������".equals(name)){
			num="70";
		}
		else if("�Ϻ��ַ�����".equals(name)){
			num="71";
		}
		else if("�������".equals(name)){
			num="72";
		}
		else if("�㶫ʡũ����".equals(name)){
			num="73";
		}
		else if("��������".equals(name)){
			num="74";
		}
		else if("��ҵ����".equals(name)){
			num="76";
		}
		else if("����ũ����ҵ����".equals(name)){
			num="77";
		}
		else if("��ݸũ����ҵ����".equals(name)){
			num="78";
		}
		else if("��ݸ����".equals(name)){
			num="79";
		}
		else if("��������".equals(name)){
			num="80";
		}
		return num;
	}
	
	
	/**
	 * ��ѯ���컧���а��˺�
	 * @param posid �ն˱��
	 * @param password ͳһ֧���˻�����
	 * @return ���а��˺���Ϣ
	 * @throws Exception
	 */
	public int validatePassword(String posid,String password) throws Exception {
		StringBuffer sql = new StringBuffer();
		String tradepwd="";
		sql.append("select tradepwd from ec_facct a,ec_resources b  \n")
		.append("where  a.fundacct =b.fundacct   \n")
		.append("and b.posid ='").append(posid).append("'");
		
		try {
			tradepwd = db.getString(sql.toString());
			Log.info("��֤�������룺"+tradepwd+" : "+password);
			if(tradepwd.equals(password)){
				return 0;
			}
		} catch (Exception ex) {
			Log.error("��ѯ���컧���а��˺�:", ex);
			throw ex;
		}
		return -1;
	}
	
	/**
	 * �޸�ͳһ֧���˺Ž�������
	 * @param posid �ն˱��
	 * @param oldPassword ԭ��������
	 * @param newPassword 
	 * @return ���а��˺���Ϣ
	 * @throws Exception
	 */
	public int updateFacctPWD(String posid,String oldPassword,String newPassword) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("update ec_facct set tradepwd='")
			.append(newPassword).append("'")
			.append(" where  fundacct in (")
			.append("select fundacct from ec_resources b where b.posid='")
			.append(posid).append("')")
			.append(" and tradepwd ='").append(oldPassword).append("'");
		int result = 0;
		try {
			result = db.update(sql.toString());
		} catch (Exception ex) {
			Log.error("��ѯ���컧���а��˺�:", ex);
			throw ex;
		}
		return result;
	}
	
	/**
	 * ��ѯ���컧�������а��˺�
	 * @param posid �ն˱��
	 * @return ���а��˺���Ϣ
	 * @throws Exception
	 */
	public String getBankAccount(String posid) throws Exception {
		StringBuffer sql = new StringBuffer();
		String bankAccount="";
		sql.append("select bankaccount from ec_terminal a,ec_resources b  \n")
			.append("where  a.termid =b.termid and a.state=0  \n")
			.append("and b.posid ='").append(posid).append("'");
		
		try {
			bankAccount = db.getString(sql.toString());
		} catch (Exception ex) {
			Log.error("��ѯ���컧�������а��˺�:", ex);
			throw ex;
		}
		
		return bankAccount;
	}
	/**
	 * ��ѯ���컧�������а��˺�
	 * @param posid �ն˱��
	 * @return ���а��˺���Ϣ
	 * @throws Exception
	 */
	public String getTYBankAccount(String posid) throws Exception {
		StringBuffer sql = new StringBuffer();
		String bankAccount="";
		sql.append("select tybankaccount from ec_terminal a,ec_resources b  \n")
			.append("where  a.termid =b.termid and a.state=0  \n")
			.append("and b.posid ='").append(posid).append("'");
		
		try {
			bankAccount = db.getString(sql.toString());
		} catch (Exception ex) {
			Log.error("��ѯ���컧�������а��˺�:", ex);
			throw ex;
		}
		
		return bankAccount;
	}
	/**
	 * ��ѯ�����
	 * @param posid �ն˱��
	 * @param services ҵ������
	 * @return ���ؿ������Ϣ
	 * @throws Exception
	 */
	public List getCardTypes(String posid, String services[]) throws Exception {
		StringBuffer sql = new StringBuffer();
		StringBuffer service = new StringBuffer("");
		for(int i = 0;i < services.length-1;i++){
			service.append("'").append(services[i]).append("',");
		}
		service.append("'").append(services[services.length-1]).append("'");
		//�������,���������,����ֵ���,����ֵ
		sql
				.append(
						"select a.id,a.name,b.id,b.name from ec_cardtype a,ec_cardfee b \n")
				.append("where a.id=b.cardtype and a.state=0 and b.state=0 \n")
				.append("and a.service in(")
				.append(service)
				.append(
						") and b.id in(select cardfee from ec_cardsyslevel where \n")
				.append(
						"district in (select district from ec_terminal where termid in \n")
				.append("(select termid from ec_resources where posid='")
				.append(posid).append("'))) order by a.id,cast(b.name as int)");

		List rs = null;
		try {
			rs = db.getList(sql.toString());
		} catch (Exception ex) {
			Log.error("��ѯ��������:", ex);
			throw ex;
		}

		return rs;
	}
	/**
	 * ���ݿ���ֵ��Ż�ÿ���ֵ
	 * @param cardFeeNo ����ֵ���
	 * @return ���ؿ���ֵ
	 */
	public int getCardFee(String cardFeeNo)
	{
		StringBuffer sql = new StringBuffer();
		sql.append("select name*100 from ec_cardfee where id=").append(cardFeeNo);
		int fee = 0;
		
		try
		{
			fee = db.getInt(sql.toString());
		}catch(Exception ex)
		{
			Log.error("���ݿ���ֵ��Ż�ÿ���ֵ����:",ex);
		}
		
		return fee;
	}
	/**
	 * ���ݿ���ֵ��Ż���ۿۼ�
	 * @param cardFeeNo ����ֵ���
	 * @param cardType ������
	 * @param termNo �ն˺�
	 * @return ���ؿ���ֵ
	 */
	public int getCardFee(String cardFeeNo, String cardType,String termNo) {
		StringBuffer sql = new StringBuffer();
		sql.append("select name*100 from ec_cardfee where id=")
				.append(cardFeeNo);
		int fee = 0;
		int costtype = 1;
		String[] groups = null;
		try {
			fee = db.getInt(sql.toString());
		} catch (Exception ex) {
			Log.error("���ݿ���ֵ��Ż�ÿ���ֵ�����ͳ���:", ex);
		}
		sql.delete(0, sql.length());
		sql.append("select costtype from ec_cardtype where id=").append(
				cardType);
		try {
			costtype = db.getInt(sql.toString());
		} catch (Exception ex) {
			Log.error("���ݿ����ͱ�Ż�ÿۿʽ����:", ex);
		}
		if (costtype == 2) {
			sql.delete(0, sql.length());
			sql
					.append(
							"select a.groupid from ec_termrulebind a,ec_commrulegroup b ")
					.append(
							"where a.groupid = b.id and  a.termid=(select termid from ec_resources c where c.resourceid='")
					.append(termNo).append("');");
			try {
				groups = db.getStringArray(sql.toString());
				for (int n = 0; groups != null&&n < groups.length; n++) {
					String groupid = groups[n];
					int [] rule = null;
					sql.delete(0, sql.length());
					sql.append("select setmode, value ")
					.append("from ec_termcardrule where groupid = ").append(groupid)
					.append("and cardtype=").append(cardType)
					.append("and cardfee=").append(cardFeeNo);
					try {
						rule = db.getIntArray(sql.toString());
					} catch (Exception ex) {
						Log.error("���ݿۿʽ��ù��������:", ex);
					}
					if(rule != null){
						if(rule[0] == 1){
							fee = rule[1]; 
						}
						if(rule[0] == 2){
							fee = fee*rule[1]/10000;
						}
						break;
					}

				}
			} catch (Exception ex) {
				Log.error("���ݿۿʽ��ù��������:", ex);
			}
		}
		return fee;
	}

	/**
	 * ��ô�����Ϣ
	 * @param goodsNo ������Ʒ���
	 * @return ���ش�����Ʒ��Ϣ
	 */
	public String[] getBusiFee(int goodsNo) {
		StringBuffer sql = new StringBuffer();
		sql
				.append(
						"select charge ,name ,goodsinfo,remark from ec_busiagentgoods where code=")
				.append(goodsNo);
		String[] str = null;

		try {
			str = db.getStringArray(sql.toString());
		} catch (Exception ex) {
			Log.error("���ݴ�����Ʒ�ۿ�ֵ����:", ex);
		}

		return str;
	}

	/**
	 * ���ݿ���ֵ��Ż�ÿ���ֵ
	 * @param typeNo �������ͱ��
	 * @return ���ش������ƣ�ҵ������
	 */
	public String[] getBusiType(int typeNo) {
		StringBuffer sql = new StringBuffer();
		sql.append("select service ,name from ec_busiagenttype where code=")
				.append(typeNo);
		String[] str = null;

		try {
			str = db.getStringArray(sql.toString());
		} catch (Exception ex) {
			Log.error("���ݴ�����Ʒ�ۿ�ֵ����:", ex);
		}

		return str;
	}

	/**
	 * ��ȡ�ۿ���Ϣ
	 * @return ����String[],��������,������,����,��Ч��,����ֵ,��־��,��ֵ���ŷ�ʽ,��ֵ����˵��
	 * @param cardType �����ͱ��
	 * @param cardFeeNo ����ֵ���
	 * @throws Exception
	 */
	public synchronized String[] getCardInf(int cardType, int cardFeeNo)
			throws Exception {
		StringBuffer sql = new StringBuffer();
		//		sql.append("select first 1 cardno,cardbatch,password,overtime,c.name,cardsign, \n")
		//		   .append("case filltype when 1 then '��ֵ��ʽ' else '���ŷ�ʽ' end,content \n")
		//		   .append("from ec_card a,ec_cardtype b,ec_cardfee c \n")
		//		   .append("where a.cardtype=b.id and a.cardfee=c.id and a.state=1 \n")
		//		   .append("and a.cardtype=").append(cardType)
		//		   .append(" and a.cardfee=").append(cardFeeNo)
		//		   .append(" and substr(a.overtime,1,8)>='").append(DateParser.getNowDate()).append("'");
		sql
				.append(
						"select  first 1 a.cardno,a.cardbatch,a.password,a.overtime,c.name,")
				.append(
						"b.pwdtype,b.fillphone,b.content,c.present,b.service ,a.cardsign from ec_card a,ec_cardtype b,ec_cardfee c")
				.append(
						" where a.cardtype=b.id and a.cardfee=c.id and a.state=1 ")
				.append("and a.cardtype=").append(cardType).append(
						" and a.cardfee=").append(cardFeeNo).append(
						" and a.overtime>='").append(
						DateParser.getNowDateTime()).append("' order by intime");

		String[] str = null;

		try {
			str = db.getStringArray(sql.toString());

			if (str != null) {
				this.updateCardState(str[0], Constant.CARD_FINAL_SELL);
			}
		} catch (Exception ex) {
			Log.error("��ȡ������Ϣ����:", ex);
			throw ex;
		}

		return str;
	}
	/**
	 * ��ȡ�ۿ���Ϣ
	 * @param cardNo ����
	 * @return ����String[],��������,������,����,��Ч��,����ֵ,��־��,��ֵ���ŷ�ʽ,��ֵ����˵��
	 * @throws Exception
	 */
	public synchronized String[] getCardInf(String cardNo)
			throws Exception {
		StringBuffer sql = new StringBuffer();
		//		sql.append("select first 1 cardno,cardbatch,password,overtime,c.name,cardsign, \n")
		//		   .append("case filltype when 1 then '��ֵ��ʽ' else '���ŷ�ʽ' end,content \n")
		//		   .append("from ec_card a,ec_cardtype b,ec_cardfee c \n")
		//		   .append("where a.cardtype=b.id and a.cardfee=c.id and a.state=1 \n")
		//		   .append("and a.cardtype=").append(cardType)
		//		   .append(" and a.cardfee=").append(cardFeeNo)
		//		   .append(" and substr(a.overtime,1,8)>='").append(DateParser.getNowDate()).append("'");
		sql
				.append(
						"select  first 1 a.overtime,b.name,c.name,")
				.append(
						"b.content,c.present  from ec_card a,ec_cardtype b,ec_cardfee c")
				.append(
						" where a.cardtype=b.id and a.cardfee=c.id ")
				.append("and a.cardno='").append(cardNo).append("'");

		String[] str = null;

		try {
			str = db.getStringArray(sql.toString());

			if (str != null) {
				this.updateCardState(str[0], Constant.CARD_FINAL_SELL);
			}
		} catch (Exception ex) {
			Log.error("��ȡ������Ϣ����:", ex);
			throw ex;
		}

		return str;
	}

	/**
	 * ���¿�״̬
	 * @param cardNo ����
	 * @param state ״̬
	 * @throws Exception
	 */
	public synchronized void updateCardState(String cardNo, int state)
			throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("update ec_card set state=").append(state).append(
				",distime='").append(DateParser.getNowDateTime()).append("'")
				.append(" where cardno='").append(cardNo).append("'");

		try {
			db.update(sql.toString());
		} catch (Exception ex) {
			Log.error("���¿�״̬����:", ex);
			throw ex;
		}
	}

	/**
	 * ��ѯ�ù����Ƿ�Ϊδ������쳣����
	 * @param seqNo ��ˮ��
	 * @param disState ����״̬
	 * @param state ״̬
	 * @return ����true��ʾ��δ������쳣����,��֮��ʾ����
	 * @throws Exception ���ݿ��쳣
	 */
	public boolean isError(String seqNo, int disState, int state)
			throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select seqno from ec_custerror where seqno='")
				.append(seqNo).append("'");

		if (disState >= 0) {
			sql.append(" and disstate=").append(disState);
		}

		if (state >= 0) {
			sql.append(" and state=").append(state);
		}

		boolean check = false;
		try {
			check = db.hasData(sql.toString());
		} catch (Exception ex) {
			Log.error("��ѯ�۷��쳣������־����:", ex);
			throw ex;
		}

		return check;
	}

	/**
	 * @param seqNo ��ˮ��
	 * @return ��������
	 * @throws Exception
	 */
	public int getErrNum(String seqNo) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select disnum from ec_custerror where seqno='").append(
				seqNo).append("'");

		int num = 0;
		try {
			num = db.getInt(sql.toString());
		} catch (Exception ex) {
			Log.error("��ѯ�۷��쳣��������ʧ�ܴ�������:", ex);
			throw ex;
		}
		return num;
	}

	/**
	 * ��ѯ����������־�����Ƿ���ڸñʹ���
	 * @param seqNo ��ˮ��
	 * @return ����true��ʾ��,��֮��ʾû��
	 * @throws Exception
	 */
	private boolean isSuccess(String seqNo) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select seqno from ec_custlog where seqno='").append(seqNo)
				.append("'");

		boolean check = false;
		try {
			check = db.hasData(sql.toString());
		} catch (Exception ex) {
			Log.error("��ѯ�۷�����������־����:", ex);
			throw ex;
		}

		return check;
	}

	/**
	 * �жϸù����Ƿ���Ҫ����
	 * @param seqNo ��ˮ��
	 * @param compdate �������
	 * @param action ִ��ָ���
	 * @param servid ҵ������
	 * @return ����true��ʾ��Ҫ����,��֮��ʾ����Ҫ����
	 * @throws Exception
	 */
	public int isNeedDisOrder(String seqNo, String compdate, int action,
			String servid) throws Exception {
		if (this.isSuccess(seqNo)) {
			this.updateErrOrderDis(seqNo, 2);
			return 1; //ֱ�ӷ��سɹ�
		} else if (this.isError(seqNo, 2, 0)) //�ù����Ѵ���
		{
			return 1; //ֱ�ӷ��سɹ�
		} else if (!this.isError(seqNo, -1, 0)) //������־����쳣��־��û��¼
		{
			return 9; //��Ҫ����
		} else if (this.isIncludeOrder(seqNo, compdate, action, servid, 1) != null) {
			return -1; //ֱ�Ӽ�¼ʧ����־
		}

		return -9;
	}

	//�ж��Ƿ�
	/**
	 * @param seqNo ��ˮ��
	 * @param compdate �������
	 * @param action ִ�ж���
	 * @param servid ҵ������
	 * @param source ����Դ
	 * @return ��ˮ��
	 */
	public String isIncludeOrder(String seqNo, String compdate, int action,
			String servid, int source) {
		StringBuffer sql = new StringBuffer();
		String seqno = null;
		sql.append("select first 1 seqno from ec_custerror where servid=")
				.append(servid).append(" and compdate<'").append(compdate)
				.append("' and seqno!='").append(seqNo).append(
						"' and disstate!=2 and state=0 and source=").append(
						source).append(" order by compdate;");

		try {
			seqno = db.getString(sql.toString());
		} catch (Exception ex) {
			Log.error("�ж��۷������������쳣�����Ƿ����ݳ���:", ex);
		}

		return seqno;
	}

	/**
	 * @param errOrder ������Ϣ
	 * @param type ����
	 * @return �ɹ�״̬
	 * @throws Exception
	 */
	public int insertErrorOrder(CustErrorOrder errOrder, int type)
			throws Exception {
		if (this.isError(errOrder.getSeqNo(), -1, -1)) {
			if (type == 1) //��type=1ʱ,�ۼӴ���ʧ�ܴ���
			{
				this.updateErrorNums(errOrder.getSeqNo());
			}
			return this.getErrNum(errOrder.getSeqNo());
		}
		String sql = "insert into ec_custerror values (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

		try {
			db.update(sql, errOrder.getCustErrorOrder());
		} catch (Exception ex) {
			Log.error("�����۷��쳣������־����:", ex);
			throw ex;
		}

		return this.getErrNum(errOrder.getSeqNo());
	}

	/**
	 * @param seqNo ��ˮ��
	 * @param areaCode ����
	 * @param servId �����
	 * @param custid �ͻ�����
	 * @param custName �ͻ�����
	 * @param action ֮�Զ���
	 * @param prodid proid
	 * @param compDate �������
	 * @param stsDate ��ʼ����
	 * @param disState �������
	 * @param state ״̬
	 * @param errorExp ����˵��
	 * @throws Exception 
	 */
	public void insertErrorOrder(String seqNo, String areaCode, String servId,
			String custid, String custName, int action, int prodid,
			String compDate, String stsDate, int disState, int state,
			String errorExp) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("insert into ec_custerror values ('").append(seqNo).append(
				"','").append(areaCode).append("',").append(servId).append(",")
				.append(custid).append(",'").append(custName).append("',")
				.append(action).append(",").append(prodid).append(",'").append(
						compDate).append("','").append(stsDate).append("',")
				.append(disState).append(",").append(state).append(",'")
				.append(errorExp).append("')");

		try {
			db.update(sql.toString());
		} catch (Exception ex) {
			Log.error("�����۷��쳣������־����:", ex);
			throw ex;
		}
	}

	/**
	 * @param orderLog 
	 * @throws Exception
	 */
	public void insertSuccOrder(CustOrderLog orderLog) throws Exception {
		String sql = "insert into ec_custlog values (?,?,?,?,?,?,?,?)";

		try {
			db.update(sql, orderLog.getCustOrderLog());
		} catch (Exception ex) {
			Log.error("�����۷�����������־����:", ex);
			throw ex;
		}
	}

	/**
	 * @param seqNo
	 * @param areaCode
	 * @param servId
	 * @param custid
	 * @param custName
	 * @param action
	 * @param prodid
	 * @param compDate
	 * @throws Exception
	 */
	public void insertSuccOrder(String seqNo, String areaCode, String servId,
			String custid, String custName, int action, int prodid,
			String compDate) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("insert into ec_custlog values ('").append(seqNo).append(
				"','").append(areaCode).append("',").append(servId).append(",")
				.append(custid).append(",'").append(custName).append("',")
				.append(action).append(",").append(prodid).append(",'").append(
						compDate).append("')");

		try {
			db.update(sql.toString());
		} catch (Exception ex) {
			Log.error("�����۷�����������־����:", ex);
			throw ex;
		}
	}

	public void updateErrorNums(String seqNo) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("update ec_custerror set disnum=disnum+1").append(
				" where seqno='").append(seqNo).append("'");

		try {
			db.update(sql.toString());
		} catch (Exception ex) {
			Log.error("�����۷��쳣���������������:", ex);
			throw ex;
		}
	}

	/**
	 * @param tradeNo
	 * @param posid
	 * @return
	 * @throws Exception
	 */
	public List getAgentTypes(String tradeNo, String posid) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql
				.append(
						"select a.code,a.name,b.code,b.name,b.charge,b.goodsinfo from ec_busiagenttype a,ec_busiagentgoods b \n")
				.append("where a.code=b.type and a.state=0 and b.state=0 \n")
				.append("and a.service='")
				.append(tradeNo)
				.append(
						"' and b.code in(select goodscode from ec_areaagentgoods where \n")
				.append(
						"district in (select district from ec_terminal where termid in \n")
				.append("(select termid from ec_resources where posid='")
				.append(posid).append("'))) order by a.showgrade");
		List rs = null;
		try {
			rs = db.getList(sql.toString());
		} catch (Exception ex) {
			Log.error("��ѯ����ҵ�����:", ex);
			throw ex;
		}

		return rs;
	}

	//�޸��쳣��������״̬
	/**
	 * @param seqNo
	 * @param state
	 * @throws Exception
	 */
	public void updateErrOrderDis(String seqNo, int state) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("update ec_custerror set disnum=0,disstate=").append(state)
				.append(",stsdate='").append(DateParser.getNowDateTime())
				.append("' where seqno='").append(seqNo).append("'");

		try {
			db.update(sql.toString());
		} catch (Exception ex) {
			Log.error("�����۷��쳣��������״̬����:", ex);
			throw ex;
		}
	}

	//�޸��쳣����״̬
	/**
	 * @param seqNo
	 * @param state
	 * @throws Exception
	 */
	public void updateErrOrderSts(String seqNo, int state) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("update ec_custerror set state=").append(state).append(
				",stsdate='").append(DateParser.getNowDateTime()).append(
				"' where seqno='").append(seqNo).append("'");
		Log.debug("--�޸��۷��쳣����״̬--:" + sql.toString());

		try {
			db.update(sql.toString());
		} catch (Exception ex) {
			Log.error("�޸��۷��쳣����״̬����:", ex);
			throw ex;
		}
	}

	/**
	 * ���ݱ������ƴ�SCP����л�ö�Ӧ����������ֵ������0��ǰ�油ȫΪ�̶����ȵ��ַ�����
	 * @param code ��������
	 * @param length ����
	 * @return ��������ֵ
	 * @throws SQLException
	 */
	public static String getCodeSequence(String code, int length)
			throws SQLException {
		int sequence = 0;
		DBService db = null;
		try {
			db = new DBService();
			db.setAutoCommit(false);

			StringBuffer sql = new StringBuffer(100);
			sql
					.append(
							"update ec_code_sequence set sequence=sequence+1 where code='")
					.append(code).append("'");
			db.update(sql.toString());

			sql.delete(0, sql.length());
			sql.append("select sequence from ec_code_sequence where code='")
					.append(code).append("'");
			sequence = db.getInt(sql.toString());

			db.commit();
		} catch (SQLException e) {
			db.rollback();
			throw e;
		} finally {
			db.close();
		}

		DecimalFormat df = new DecimalFormat(Tools.repeat("0", length));
		return df.format(sequence);
	}

	/**
	 * ��ȡ��Ϣ��ˮ��
	 * @param type ��Ϣ������
	 * @return ������Ϣ��ˮ��
	 */
	public String getSeqNo(String type) {
		String msgSeqNo = null;
		String time = DateParser.getNowDateTime().substring(2);
		try {
			String sql = "select ec_takedeposit_id.nextval from dual";
			DecimalFormat df = new DecimalFormat("0000000000");
			String seq = df.format(db.getLong(sql));
			//�����ˮ��
			msgSeqNo = time + type + seq;
		} catch (Exception ex) {
			Log.error("��ȡ������Ϣ��ˮ�ų���", ex);
		}

		return msgSeqNo;
	}

	public List getRePrintService(String areaCode) {
		StringBuffer sql = new StringBuffer();
		List rsList = null;

		try {
			sql.append(
					"select a.code,a.name from ec_service a,ec_reprintset b ")
					.append("where a.code=b.service and b.areacode='").append(
							areaCode).append("'");

			rsList = db.getList(sql.toString());

		} catch (Exception ex) {
			Log.error("��ѯ�ش�ӡҵ�����ͳ���", ex);
		}

		return rsList;
	}

	public String getRePrintInfo(String termId, String service, String payNo,
			String date) {
		StringBuffer sql = new StringBuffer();
		String str = null;

		try {
			sql
					.append("select first 1 printmsg from ec_reprint where ")
					.append(
							"resourceid=(select resourceid from ec_resources where posid='")
					.append(termId).append("') and service='").append(service)
					.append("' and account='").append(payNo).append(
							"' and tradetime like '").append(date).append(
							"%' order by tradetime desc");
			str = db.getString(sql.toString());
		} catch (Exception ex) {
			Log.error("��ѯ�ش�ӡ���ݳ���", ex);
		}

		return str;
	}
	
	/**
	 * ��ȡ�ؽɷ���Ϣ
	 * @return List
	 */
	public List getPayInfo()
	{
		StringBuffer sql = new StringBuffer();
		List rsList = null;
		
		try
		{
			sql.append("select first 10 tradeserial,paydata,serviceseqno ,number from ec_payinfo ")
			   .append("where state=1 and number<'5' ");
			
			rsList = db.getList(sql.toString());
			
		}catch(Exception ex)
		{
			Log.error("��ѯ�ؽɷ���Ϣ����",ex);
		}
		
		return rsList;
	} 
	/**
	 * ��ȡ�ط�����Ϣ
	 * @return List
	 */
	public List getBackInfo()
	{
		StringBuffer sql = new StringBuffer();
		List rsList = null;
		
		try
		{
			sql.append("select first 10 tradeserial,backdata,serviceseqno,area from ec_backinfo ")
			.append("where state=1 and number<5 ");
			rsList = db.getList(sql.toString());
			//Log.info("��ȡ�ط�����Ϣ��¼����"+rsList.size());
		}catch(Exception ex)
		{
			Log.error("��ѯ�ط�����Ϣ����",ex);
		}
		
		return rsList;
	} 
	
	public int getPayState(String seqNo)
	{
		StringBuffer sql = new StringBuffer();
		int state = 0;
		
		try
		{
			sql.append("select state from ec_payinfo ")
			   .append("where tradeserial='")
			   .append(seqNo)
			   .append("';");
			
			state = db.getInt(sql.toString());
			
		}catch(Exception ex)
		{
			Log.error("��ѯ�ش�ӡҵ�����ͳ���",ex);
		}
		
		return state;
	} 
	
	/**
	 * �޸��ؽɷѴ���
	 * @param seqNo ������ˮ��
	 * @param num �ؽɷѴ���
	 */
	public void updatePayInfo(String seqNo,String num)
	{
		StringBuffer sql = new StringBuffer();
		
		try
		{
			sql.append("update ec_payinfo set number='").append(num)
			.append("' where tradeserial='").append(seqNo).append("';");
			db.update(sql.toString());
			
		}catch(Exception ex)
		{
			Log.error("�޸��ؽɷѴ�������",ex);
		}
		
	}
	/**
	 * �޸��ط�������
	 * @param seqNo ������ˮ��
	 */
	public void updateBackInfoNum(String seqNo)
	{
		StringBuffer sql = new StringBuffer();
		
		try
		{
			sql.append("update ec_backinfo set number=number+1 ")
			   .append(" where tradeserial='").append(seqNo).append("';");
			Log.info("�޸��ط�������SQL:"+sql.toString());
			db.update(sql.toString());
			
		}catch(Exception ex)
		{
			Log.error("�޸��ط�����������",ex);
		}
		
	}

	/**
	 * �޸��ؽɷ�״̬
	 * @param seqNo
	 * @param state
	 */
	public void updatePayInfo(String seqNo,int state)
	{
		StringBuffer sql = new StringBuffer();
		
		try
		{
			sql.append("update ec_payinfo set state='").append(state)
			.append("' where tradeserial='").append(seqNo).append("';");
			
			db.update(sql.toString());
			
		}catch(Exception ex)
		{
			Log.error("�޸��ؽɷ�״̬����",ex);
		}
		
	}
	/**
	 * �޸��ط���״̬
	 * @param seqNo
	 * @param state
	 */
	public void updateBackInfoState(String seqNo,int state)
	{
		StringBuffer sql = new StringBuffer();
		
		try
		{
			sql.append("update ec_backinfo set state='").append(state)
			.append("' where serviceseqno='").append(seqNo).append("';");
			Log.info("�޸��ط���SQL:"+sql);
			db.update(sql.toString());
			
		}catch(Exception ex)
		{
			Log.error("�޸��ط�����Ϣ����",ex);
		}
		
	}
	/**
	 * �޸Ķ�������״̬
	 * @param seqNo
	 * @param state
	 */
	public void updateOrderState(String seqNo,int state)
	{
		StringBuffer sql = new StringBuffer();
		String date = DateParser.getNowDate();
		String month = date.substring(2, 6);
		try
		{
			sql.append("update ec_orderForm_"+month+" set state='").append(state)
			.append("' where tradeserial='").append(seqNo).append("';");
			Log.info("�޸Ķ�������SQL:"+sql);
			db.update(sql.toString());
			
		}catch(Exception ex)
		{
			Log.error("�޸Ķ���������Ϣ����",ex);
		}
		
	}
	
	public void addRePrint(String termId, String service, String tradeTime,
			String payNo, String rePrintMsg) throws Exception {
		StringBuffer sql = new StringBuffer();
		/*sql.append("select count(*) from ec_reprint where resourceid='").append(termId)
				.append("' and service='").append(service).append(
						"' and account='").append(payNo).append("'");
		int count=db.getInt(sql.toString());
		sql.delete(0, sql.length());
		if(count>0){
			sql.append("update ec_reprint set tradetime='").append(tradeTime).append("'")
			   .append(",printmsg='").append(rePrintMsg).append("'")
			   .append(" where resourceid='").append(termId)
			   .append("' and service='").append(service)
			   .append("' and account='").append(payNo).append("'");
		}else{*/
			sql.append("insert into ec_reprint values ('").append(termId).append(
			    "','").append(service).append("','").append(tradeTime).append(
			    "','").append(payNo).append("','").append(rePrintMsg).append(
			    "',null)");
		//}

		try {
			db.update(sql.toString());
		} catch (Exception ex) {
			Log.error("����ش�ӡ���ݳ���", ex);
			throw ex;
		}
	}
	
	/**
	 * ����posid��ѯ���ʽ��˺�
	 * @param posid �ն�ID
	 * @return ���ʽ��˺���Ϣ
	 * @throws Exception
	 */
	public String getFacct(String posid) throws Exception {
		StringBuffer sql = new StringBuffer();
		String fundAcct="";
		sql.append("select fundacct from  ec_resources   \n")
		.append("where  posid ='").append(posid).append("'");
		
		try {
			fundAcct = db.getString(sql.toString());
		} catch (Exception ex) {
			Log.error("��ѯ���컧���а��˺�:", ex);
			throw ex;
		}
		return fundAcct;
	}
}
