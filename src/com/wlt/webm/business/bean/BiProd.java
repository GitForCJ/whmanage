package com.wlt.webm.business.bean;

import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import whmessgae.TradeMsg;

import com.commsoft.epay.util.logging.Log;
import com.wlt.webm.AccountInfo.HeBao;
import com.wlt.webm.AccountInfo.JingFengInter;
import com.wlt.webm.AccountInfo.NineteenRecharge;
import com.wlt.webm.AccountInfo.scdx;
import com.wlt.webm.business.bean.beijingFlow.Flow;
import com.wlt.webm.business.bean.dhst.FlowsService;
import com.wlt.webm.business.bean.dingxin.dingxFlow;
import com.wlt.webm.business.bean.lechong.MobileRecharge;
import com.wlt.webm.business.bean.leliu.LeliuFlowCharge;
import com.wlt.webm.business.bean.lianlian.LianlianFlows;
import com.wlt.webm.business.bean.liansheng.Mobile_Interface;
import com.wlt.webm.business.bean.liuliangfan.LlFan;
import com.wlt.webm.business.bean.sikong.Flow_SiKong;
import com.wlt.webm.business.bean.unicom3gquery.HttpFillOP;
import com.wlt.webm.business.bean.webserviceclient.Webservices_Flow;
import com.wlt.webm.business.bean.zb.Mobile_Zb_Inter;
import com.wlt.webm.business.bean.zhixin.ZhiXin;
import com.wlt.webm.business.bean.zy.GDFreeTrafficCharge;
import com.wlt.webm.business.form.BiProdForm;
import com.wlt.webm.business.form.BiSanForm;
import com.wlt.webm.db.DBConfig;
import com.wlt.webm.db.DBService;
import com.wlt.webm.db.DBToolSCP;
import com.wlt.webm.message.MemcacheConfig;
import com.wlt.webm.message.PortalSend;
import com.wlt.webm.pccommon.Constants;
import com.wlt.webm.scpcommon.RespCode;
import com.wlt.webm.scpdb.FundAcctDao;
import com.wlt.webm.ten.service.MD5Util;
import com.wlt.webm.ten.service.TenpayXmlPath;
import com.wlt.webm.tool.FloatArith;
import com.wlt.webm.uictool.Constant;
import com.wlt.webm.util.Tools;
import com.wlt.webm.xunjie.bean.YiKahuiTrade;
import com.wlt.webm.yx.bean.YxBean;

/**
 * ������
 */
public class BiProd {
	/** ͬ���� **/
	private static Object lock = new Object();
//	private static Object lock1 = new Object();

	/**
	 * ��ȡ�����Ϣ�б�
	 * 
	 * @return �����Ϣ�б�
	 * @throws Exception
	 */
	public List listProd() throws Exception {
		DBService dbService = new DBService();
		try {
			StringBuffer sql = new StringBuffer();
			sql
					.append(
							" select a.cm_id,a.cm_type,a.cm_prod,a.cm_fee,b.sa_name ")
					.append(
							" from bi_cmprod a left join sys_area b on a.cm_area = b.sa_id")
					.append(" order by a.cm_prod");
			List list = dbService.getList(sql.toString());
			for (Object tmp : list) {
				String[] temp = (String[]) tmp;
				if (null != temp[1] && !"".equals(temp[1])) {
					if ("M".equals(temp[1])) {
						temp[1] = "�ƶ�";
					} else if ("T".equals(temp[1])) {
						temp[1] = "����";
					} else if ("U".equals(temp[1])) {
						temp[1] = "��ͨ";
					}
				}
			}
			return list;
		} catch (Exception ex) {
			throw ex;
		} finally {
			if(dbService!=null){
				dbService.close();
			}
		}
	}

	/**
	 * ��ȡ���������Ϣ�б�
	 * 
	 * @return ���������Ϣ�б�
	 * @throws Exception
	 */
	public List listvalueRange() throws Exception {
		DBService dbService = new DBService();
		try {
			String sql = "SELECT * FROM sys_valueRange ORDER BY MIN";
			List list = dbService.getList(sql.toString());
			return list;
		} catch (Exception ex) {
			throw ex;
		} finally {
			if(dbService!=null){
				dbService.close();
			}
		}
	}
	/**
	 * ��ȡ�����󶨵��������б���Ϣ
	 */
	public List getLaklaList(String userno)
	{
		DBService dbService =null;
		try {
			dbService= new DBService();
			String sql = "SELECT termid,userno,userlogin,fundacct,TIME FROM sys_lkal WHERE userno='"+userno+"'";
			List list = dbService.getList(sql.toString(),null);
			return list;
		} catch (Exception ex) {
			Log.error("��ȡ �������������Ϣ�б��쳣������"+ex);
		} finally {
			if(dbService!=null){
				dbService.close();
			}
		}
		return null;
	}
	/**
	 * ��ȡָ�� ������ ˢ����¼
	 */
	public List getLakalaAccountList(String tid,String indate,String enddate)
	{
		String str="";
		if(indate!=null && !"".equals(indate) && enddate!=null && !"".equals(enddate))
		{
			indate=indate.replace("-","")+"000000";
			enddate=enddate.replace("-","")+"235959";
			str=" AND tradetime>='"+indate+"' AND tradetime<='"+enddate+"' ";
		}
		else
		{
			str=" AND DATEDIFF(SYSDATE(),tradetime)<=3 ";
		}
		//�ж�ʱ��
		
		
		DBService dbService =null;
		try {
			dbService= new DBService();
			String sql = "SELECT refNumber,amount,orderSta,isbinding,fee,tradetime, "+
				" state, userno,fundacct,id,typeT "+
				" FROM wht_lakal_record "+
				" WHERE termId='"+tid+"' " +str+" ORDER BY tradetime DESC";
			List list = dbService.getList(sql.toString(),null);
			return list;
		} catch (Exception ex) {
			Log.error("��ȡָ�� ������ ˢ����¼������"+ex);
		} finally {
			if(dbService!=null){
				dbService.close();
			}
		}
		return null;
	}
	
	/**
	 * ��ȡָ�� �۸��� ˢ����¼
	 */
	public List getHuiFuKuanList(String tid,String indate,String enddate)
	{
		String str="";
		if(indate!=null && !"".equals(indate) && enddate!=null && !"".equals(enddate))
		{
			indate=indate.replace("-","")+"000000";
			enddate=enddate.replace("-","")+"235959";
			str=" AND tradetime>='"+indate+"' AND tradetime<='"+enddate+"' ";
		}
		else
		{
			str=" AND DATEDIFF(SYSDATE(),tradetime)<=3 ";
		}
		//�ж�ʱ��
		DBService dbService =null;
		try {
			dbService= new DBService();
			String sql = "SELECT phone,tradefee,fee,DATE_FORMAT(tradefirst,'%Y-%m-%d'),DATE_FORMAT(feetime,'%Y-%m-%d %k:%i:%s'),cardtype,state,descript,id FROM sys_hfk_record " +
					" WHERE phone='"+tid+"' " +str+" ORDER BY tradetime DESC";
			List list = dbService.getList(sql.toString(),null);
			return list;
		} catch (Exception ex) {
			Log.error("��ȡָ�� �۸��� ˢ����¼������"+ex);
		} finally {
			if(dbService!=null){
				dbService.close();
			}
		}
		return null;
	}
	/**
	 * ��ȡ���������Ϣ�б�
	 * 
	 * @return ���������Ϣ�б�
	 * @throws Exception
	 */
	public List listvalueRangeTwo() throws Exception {
		DBService dbService = new DBService();
		try {
			String sql = "SELECT cm_id,CONCAT(MIN,'~',MAX) FROM sys_valueRange ORDER BY MIN";
			List list = dbService.getList(sql.toString());
			return list;
		} catch (Exception ex) {
			throw ex;
		} finally {
			if(dbService!=null){
				dbService.close();
			}
		}
	}

	/**
	 * ��ȡ�ӿ�������
	 * @return null 
	 * @throws Exception
	 */
	public List listagentsign() throws Exception {
		DBService dbService = new DBService();
		try {
			String sql = "SELECT id,CONCAT(interName,'/',userno) FROM sys_agentsign";
			List list = dbService.getList(sql.toString());
			return list;
		} catch (Exception ex) {
			throw ex;
		} finally {
			if(dbService!=null){
				dbService.close();
			}
		}
	}
	/**
	 * ��ȡ�¶Ƚ���������Ϣ�б�
	 * 
	 * @return
	 * @throws Exception
	 */
	public List listAwardsRules() throws Exception {
		DBService dbService = new DBService();
		try {
			String sql = "SELECT * FROM wht_awardsrule ORDER BY usertype,minmoney";
			List list = dbService.getList(sql.toString());
			return list;
		} catch (Exception ex) {
			throw ex;
		}finally {
			if(dbService!=null){
				dbService.close();
			}
		}
	}

	/**
	 * ��ȡʣ���������
	 * 
	 * @return ���������Ϣ�б�
	 * @throws Exception
	 */
	public int getRevertCount(String userno) {
		int sum = 0;
		int count = 0;
		String selectStr = "select count(*) from wlt_revertlimit where userno='"
				+ userno + "'  and date=" + Tools.getMonth2();
		String sql = "select reversalcount from sys_reversalcount where tradetype=0 and user_no="
				+ userno;
		int cnt = 0;
		try {
			cnt = DBToolSCP.getInt(sql);
			count = DBToolSCP.getInt(selectStr);
			if (cnt > 0) {
				sum = cnt - count;
			} else {
				sum = 10 - count;
			}

		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}
		return sum;
	}

	/**
	 * ��������Ϣ
	 * 
	 * @param bpForm
	 * @return ��¼��
	 * @throws Exception
	 */
	public int add(int min, int max) throws Exception {
		DBService dbService = new DBService();
		try {
			String pid = "wh" + min + max;
			String sql0 = "select * from sys_valueRange where cm_id='" + pid
					+ "' or " + "min=" + min + " or max=" + max;
			if (dbService.hasData(sql0)) {
				return 1;
			}
			String sql = "insert into sys_valueRange values('" + pid + "',"
					+ min + "," + max + ")";
			dbService.update(sql);
			return 0;
		} catch (Exception ex) {
			ex.printStackTrace();
			return 1;
		} finally {
			if(dbService!=null){
				dbService.close();
			}
		}
	}

	/**
	 * ��ӽ�����Ϣ
	 * 
	 * @param type
	 * @param min
	 * @param max
	 * @param awards
	 * @return
	 * @throws Exception
	 */
	public int addawards(int type, int min, int max, float awards)
			throws Exception {
		DBService dbService = new DBService();
		try {
			String sql0 = "select * from wht_awardsrule where usertype=" + type
					+ " and minmoney=" + min + " and maxmoney=" + max;
			if (dbService.hasData(sql0)) {
				return 1;
			}
			String sql = "insert into wht_awardsrule values(NULL," + type + ","
					+ min + "," + max + "," + awards + ")";
			dbService.update(sql);
			return 0;
		} catch (Exception ex) {
			ex.printStackTrace();
			return 1;
		} finally {
			if(dbService!=null){
				dbService.close();
			}
		}
	}

	/**
	 * ɾ�������Ϣ
	 * 
	 * @param id
	 * @return bool
	 * @throws Exception
	 */
	public boolean del(String id) {
		DBService dbService = null;
		try {
			dbService = new DBService();
			StringBuffer sql1 = new StringBuffer();
			sql1.append("delete from sys_valueRange where cm_id='" + id + "';");
			dbService.update(sql1.toString());
			return true;
		} catch (Exception ex) {
			System.out.println(ex.toString());
			return false;
		} finally {
			if(dbService!=null){
				dbService.close();
			}
		}
	}

	/**
	 * ɾ���¶Ƚ�������
	 * 
	 * @param id
	 * @return bool
	 * @throws Exception
	 */
	public boolean delawards(String id) {
		DBService dbService = null;
		try {
			dbService = new DBService();
			StringBuffer sql1 = new StringBuffer();
			sql1.append("delete from wht_awardsrule where id=" + id);
			dbService.update(sql1.toString());
			return true;
		} catch (Exception ex) {
			Log.error(ex.toString());
			return false;
		}finally {
			if(dbService!=null){
				dbService.close();
			}
		}
	}

	/**
	 * ��ȡ�����Ϣ(����)
	 * 
	 * @return �����Ϣ
	 * @throws Exception
	 */
	public BiProdForm getProdInfo(String pid) throws Exception {
		DBService db = new DBService(Constants.DBNAME_SCP);
		BiProdForm prodForm = new BiProdForm();
		try {
			StringBuffer sql = new StringBuffer();
			sql.append("select a.cm_prod, a.cm_fee,a.cm_area,a.cm_type")
					.append(" from bi_cmprod a ").append(" where a.cm_id=? ");

			String[] params = { pid };
			String[] fields = { "cm_prod", "cm_fee", "cm_area", "cm_type" };

			db.populate(prodForm, fields, sql.toString(), params);

			return prodForm;
		} catch (Exception ex) {
			throw ex;
		} finally {
			if(db!=null){
				db.close();
			}
		}
	}

	/**
	 * �������
	 * 
	 * @param prodForm
	 *            �����Ϣ
	 * @return int
	 * @throws SQLException
	 */
	public int update(BiProdForm prodForm) throws SQLException {
		StringBuffer sql = new StringBuffer();
		sql.append("update bi_cmprod set");
		sql.append(" cm_prod='" + prodForm.getCm_prod() + "'");
		sql.append(" ,cm_fee='" + prodForm.getCm_fee() + "'");
		sql.append(" ,cm_area='" + prodForm.getCm_area() + "'");
		sql.append(" ,cm_type='" + prodForm.getCm_type() + "'");
		sql.append(" where");
		sql.append(" cm_id='" + prodForm.getCm_id() + "'");
		return DBToolSCP.update(sql.toString());
	}

	/**
	 * ��ȡ�����б�
	 * 
	 * @return �����б�
	 * @throws Exception
	 */
	public List getAreaList() throws Exception {
		DBService dbService = new DBService();
		try {
			StringBuffer sql = new StringBuffer();
			sql.append(" select sa_id,sa_name ").append(" from sys_area")
					.append(" where sa_pid = 1");
			List list = dbService.getList(sql.toString());
			return list;
		} catch (Exception ex) {
			throw ex;
		} finally {
			if(dbService!=null){
				dbService.close();
			}
		}
	}
	/**
	 * ��ȡ�����б����
	 * 
	 * @return 
	 * @throws Exception
	 */
	public List getAreaListMoney() throws Exception {
		DBService dbService = new DBService();
		try {
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT a.sa_id,a.sa_name,b.cm_id,b.min,b.max FROM sys_area a,sys_valueRange b WHERE a.sa_pid = 1 ORDER BY a.sa_id,b.min ASC");
			List list = dbService.getList(sql.toString());
			return list;
		} catch (Exception ex) {
			throw ex;
		} finally {
			if(dbService!=null){
				dbService.close();
			}
		}
	}
	public List getAreaListMoney2() throws Exception {
		DBService dbService = new DBService();
		try {
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT ar.sa_id,ar.sa_name,car.id,car.carName FROM sys_area ar , sys_car_type car WHERE ar.sa_pid=35 OR sa_id=381 ORDER BY ar.sa_id,car.id ASC");
			List list = dbService.getList(sql.toString());
			return list;
		} catch (Exception ex) {
			throw ex;
		} finally {
			if(dbService!=null){
				dbService.close();
			}
		}
	}
	/**
	 * ��ȡ����ӿ�������
	 * @return list
	 * @throws Exception
	 */
	public List getInterName() throws Exception {
		DBService dbService = new DBService();
		try {
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT id,userno,interName FROM sys_agentsign");
			List list = dbService.getList(sql.toString());
			return list;
		} catch (Exception ex) {
			throw ex;
		} finally {
			if(dbService!=null){
				dbService.close();
			}
		}
	}

	/**
	 * ��ȡ�����Ϣ(����)
	 * 
	 * @return �����Ϣ
	 * @throws Exception
	 */
	public BiProdForm getCmprod(BiProdForm pdForm) throws Exception {
		DBService db = new DBService(Constants.DBNAME_SCP);
		BiProdForm prodForm = new BiProdForm();
		try {
			StringBuffer sql = new StringBuffer();
			sql.append("select a.cm_prod").append(" from bi_cmprod a ").append(
					" where a.cm_fee=? and a.cm_area=? and a.cm_type=?");

			String[] params = { String.valueOf(pdForm.getCm_fee()),
					pdForm.getCm_area(), pdForm.getCm_type() };
			String[] fields = { "cm_prod" };

			db.populate(prodForm, fields, sql.toString(), params);

			return prodForm;
		} catch (Exception ex) {
			throw ex;
		} finally {
			if(db!=null){
				db.close();
			}
		}
	}

	/**
	 * ��ȡ�������ӿڰ���Ϣ�б�
	 * 
	 * @return ��Ϣ�б�
	 * @throws Exception
	 */
	public List listsan() throws Exception {
		DBService dbService = new DBService();
		try {
			StringBuffer sql = new StringBuffer();
			sql.append(" select id,cm_type,cm_fee,cm_one ").append(
					" from wlt_three order by id");
			List list = dbService.getList(sql.toString());
			for (Object tmp : list) {
				String[] temp = (String[]) tmp;
				if (null != temp[1] && !"".equals(temp[1])) {
					if ("0".equals(temp[1])) {
						temp[1] = "�ƶ�";
					} else if ("1".equals(temp[1])) {
						temp[1] = "��ͨ";
					} else if ("2".equals(temp[1])) {
						temp[1] = "����";
					}
				}
			}
			return list;
		} catch (Exception ex) {
			throw ex;
		} finally {
			if(dbService!=null){
				dbService.close();
			}
		}
	}

	/**
	 * ɾ������������Ϣ
	 * 
	 * @param
	 * @return bool
	 * @throws Exception
	 */
	public boolean sandel(BiSanForm bpForm) throws Exception {
		DBService dbService = new DBService();
		try {
			StringBuffer sql1 = new StringBuffer();
			sql1.append("delete from wlt_three where id='" + bpForm.getIds()[0]
					+ "';");
			dbService.update(sql1.toString());
			return true;
		} catch (Exception ex) {
			throw ex;
		} finally {
			if(dbService!=null){
				dbService.close();
			}
		}
	}

	/**
	 * ���µ���������Ϣ
	 * 
	 * @param
	 * @return int
	 * @throws SQLException
	 */
	public int sanupdate(BiSanForm prodForm) throws SQLException {
		StringBuffer sql = new StringBuffer();
		sql.append("update wlt_three set");
		sql.append(" ,cm_fee='" + prodForm.getCm_fee() + "'");
		sql.append(" ,cm_face='" + prodForm.getCm_face() + "'");
		sql.append(" ,cm_type='" + prodForm.getCm_type() + "'");
		sql.append(" where");
		sql.append(" id='" + prodForm.getId() + "'");
		return DBToolSCP.update(sql.toString());
	}

	/**
	 * ��ȡ�ӿ��б�
	 * 
	 * @return �����б�
	 * @throws Exception
	 */
	public List getFaceList() throws Exception {
		DBService dbService = new DBService();
		try {
			StringBuffer sql = new StringBuffer();
			sql.append(" select in_id,in_explain ").append(
					" from sys_interface_type");
			List list = dbService.getList(sql.toString());
			return list;
		} catch (Exception ex) {
			throw ex;
		} finally {
			if(dbService!=null){
				dbService.close();
			}
		}
	}
	
	/**
	 * ��ȡ��ǰ�û�mac��֤״̬
	 * @param userno 
	 * @return int
	 * @throws Exception
	 */
	public String getMacState(String userno) throws Exception {
		DBService dbService =null;
		try {
			dbService= new DBService();
			String sql="SELECT exp3 FROM sys_user WHERE user_no='"+userno+"'";
			return dbService.getString(sql);
		} catch (Exception ex) {
			throw ex;
		} finally {
			if(dbService!=null)
				dbService.close();
		}
	}
	/**
	 * ��ȡ��ǰ�û�mac��֤״̬
	 * @param userno 
	 * @return int
	 * @throws Exception
	 */
	public String[] getMac(String userno) throws Exception {
		DBService dbService =null;
		try {
			dbService= new DBService();
			String sql="SELECT macaddress,googlesign,ext3 FROM mac WHERE userno='"+userno+"'";
			return dbService.getStringArray(sql);
		} catch (Exception ex) {
			throw ex;
		} finally {
			if(dbService!=null)
				dbService.close();
		}
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public BiProdForm getSanInfo(String pid) throws Exception {
		DBService db = new DBService(Constants.DBNAME_SCP);
		BiProdForm prodForm = new BiProdForm();
		try {
			StringBuffer sql = new StringBuffer();
			sql.append("select cm_fee,cm_face,cm_type").append(
					" from wlt_three where a.cm_id=?  ");
			String[] params = { pid };
			String[] fields = { "cm_fee", "cm_face", "cm_type" };

			db.populate(prodForm, fields, sql.toString(), params);

			return prodForm;
		} catch (Exception ex) {
			throw ex;
		} finally {
			if(db!=null){
				db.close();
			}
		}
	}

	/**
	 * ��ӵ������ӿڰ���Ϣ
	 * 
	 * @param
	 * @return
	 * @throws Exception
	 */
	public int sanadd(BiSanForm bpForm) throws Exception {
		DBService db = new DBService();
		StringBuffer sql3 = new StringBuffer();
		try {
			sql3
					.append("select 1 from wlt_three where cm_fee=? and cm_face=? and cm_type=?");
			if (db.hasData(sql3.toString(), new String[] { bpForm.getCm_fee(),
					bpForm.getCm_face(), bpForm.getCm_type() })) {
				return -1;
			}
			sql3.delete(0, sql3.length());

			sql3.append("select in_name from sys_interface_type where in_id='"
					+ bpForm.getCm_face() + "'");
			String name = db.getString(sql3.toString());
			sql3.delete(0, sql3.length());
			sql3
					.append("insert into wlt_three(cm_fee,cm_face,cm_type,cm_one) values('"
							+ bpForm.getCm_fee()
							+ "','"
							+ bpForm.getCm_face()
							+ "','" + bpForm.getCm_type() + "','" + name + "')");
			db.update(sql3.toString());
			return 0;
		} catch (Exception ex) {
			throw ex;
		}finally {
			if(db!=null){
				db.close();
			}
		}
	}

	/**
	 * ��ȡӶ��
	 * 
	 * @return �����Ϣ�б�
	 * @throws Exception
	 */
	public List listbsEmploy(int n) {
		DBService dbService = null;
		String tablename = "sys_employ" + n;
		try {
			dbService = new DBService();
			String sql = "select a.id, b.sa_name,a.type,a.min,a.max,a.value from "
					+ tablename + " a,sys_area b where a.pid=b.sa_id";
			List list = dbService.getList(sql.toString());
			for (Object tmp : list) {
				String[] temp = (String[]) tmp;
				if (null != temp[2] && !"".equals(temp[2])) {
					if ("2".equals(temp[2])) {
						temp[2] = "�ƶ�";
					} else if ("0".equals(temp[2])) {
						temp[2] = "����";
					} else if ("1".equals(temp[2])) {
						temp[2] = "��ͨ";
					}
				}
			}
			return list;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}finally {
			if(dbService!=null){
				dbService.close();
			}
		}
	}

	/**
	 * �޸� Ӷ����Ϣ
	 * 
	 * @param id
	 * @param n
	 * @return
	 */
	public boolean delEmploy(String cm_prod, int id, String n) {
		DBService dbService = null;
		String tablename = "sys_employ" + n;
		try {
			dbService = new DBService();
			String sql = "update " + tablename + " set value="
					+ Float.valueOf(cm_prod) + " where id=" + id;
			dbService.update(sql.toString());
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}finally {
			if(dbService!=null){
				dbService.close();
			}
		}
	}

	/**
	 * �޸� Ӷ����Ϣ
	 * 
	 * @param cm_prod
	 * @param id
	 * @return
	 */
	public boolean tpdelEmploy(String cm_prod, int id) {
		DBService dbService = null;
		String tablename = "sys_tpemploy";
		try {
			dbService = new DBService();
			String sql = "update " + tablename + " set value="
					+ Float.valueOf(cm_prod) + " where id=" + id;
			dbService.update(sql.toString());
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}finally {
			if(dbService!=null){
				dbService.close();
			}
		}
	}

	/**
	 * �޸� С��Ӷ����Ϣ
	 * 
	 * @param cm_prod
	 * @param id
	 * @param cm_prod1
	 * @return
	 */
	public boolean delEmploy(int cm_prod, int id, int cm_prod1) {
		DBService dbService = null;
		String tablename = "sys_employ5";
		try {
			dbService = new DBService();
			String sql = "update " + tablename + " set value=" + cm_prod
					+ ",value1=" + cm_prod1 + " where id=" + id;
			dbService.update(sql.toString());
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}finally {
			if(dbService!=null){
				dbService.close();
			}
		}
	}

	/**
	 * ��ȡ���
	 * 
	 * @return �����б�
	 * @throws Exception
	 */
	public List getValueList() throws Exception {
		DBService dbService = new DBService();
		try {
			StringBuffer sql = new StringBuffer();
			sql.append(" select CONCAT(min,'--',max) AS b ,cm_id ").append(
					" from sys_valueRange").append(" where 1 = 1 ORDER BY min");
			List list = dbService.getList(sql.toString());
			return list;
		} catch (Exception ex) {
			throw ex;
		} finally {
			if(dbService!=null){
				dbService.close();
			}
		}
	}

	/**
	 * ���Ӷ����ϸ
	 * 
	 * @param bpForm
	 * @return ��¼��
	 * @throws Exception
	 */
	public int addEmploy(String st, int pid, String type, String cm_id,
			float value) throws Exception {
		DBService dbService = new DBService();
		try {
			String table = "sys_employ" + st;
			String sql0 = "select * from " + table + " where pid=" + pid
					+ " and " + "cm_id='" + cm_id + "' and type='" + type + "'";
			if (dbService.hasData(sql0)) {
				return 1;
			}
			Object[] params = { null, pid, type, cm_id, value };
			dbService.logData(5, params, table);
			return 0;
		} catch (Exception ex) {
			ex.printStackTrace();
			return 1;
		} finally {
			if(dbService!=null){
				dbService.close();
			}
		}
	}

	/**
	 * ��ӽӿ���Ӷ����ϸ
	 * 
	 * @param bpForm
	 * @return ��¼��
	 * @throws Exception
	 */
	public int tpddEmploy(int pid, String type, String cm_id, float value,int interName)
			throws Exception {
		DBService dbService = null;
		try {
			dbService = new DBService();
			String table = "sys_tpemploy";
			String sql0 = "select * from " + table + " where pid=" + pid
					+ " and " + "cm_id='" + cm_id + "' and type='" + type + "' and groups="+interName;
			if (dbService.hasData(sql0)) {
				return 1;
			}
			Object[] params = { null, pid, type, cm_id, value,interName };
			dbService.logData(6, params, table);
			return 0;
		} catch (Exception ex) {
			ex.printStackTrace();
			return 1;
		} finally {
			if(dbService!=null){
				dbService.close();
			}
		}
	}

	/**
	 * ���С��Ӷ����ϸ
	 * 
	 * @param value1
	 * @param pid
	 * @param type
	 * @param cm_id
	 * @param value
	 * @return
	 * @throws Exception
	 */
	public int addminEmploy(float value1, int pid, String type, String cm_id,
			float value) throws Exception {
		DBService dbService = new DBService();
		try {
			String table = "sys_employ5";
			String sql0 = "select * from " + table + " where pid=" + pid
					+ " and " + "cm_id='" + cm_id + "' and type='" + type + "'";
			if (dbService.hasData(sql0)) {
				return 1;
			}
			Object[] params = { null, pid, type, cm_id, value, value1, 0 };
			dbService.logData(7, params, table);
			return 0;
		} catch (Exception ex) {
			ex.printStackTrace();
			return 1;
		}finally {
			if(dbService!=null){
				dbService.close();
			}
		}
	}

	/**
	 * ��ȡҵ������
	 * 
	 * @return
	 * @throws Exception
	 */
	public List gettype() throws Exception {
		DBService dbService = null;
		try {
			dbService = new DBService();
			String sql = "select code,name from wht_service where flag=1";
			List list = dbService.getList(sql);
			return list;
		} catch (Exception ex) {
			throw ex;
		} finally {
			if(dbService!=null){
				dbService.close();
			}
		}
	}

	/**
	 * ɾ��Ӷ����Ϣ
	 * 
	 * @param id
	 * @return
	 */
	public boolean delRebat(int id, float a) {
		DBService dbService = null;
		try {
			dbService = new DBService();
			String sql = "update sys_employ3 set value=" + a + " where id="
					+ id;
			dbService.update(sql.toString());
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		} finally {
			if(dbService!=null){
				dbService.close();
			}
		}
	}

	/**
	 * ����ۺ�ҵ��
	 * 
	 * @param bpForm
	 * @return ��¼��
	 * @throws Exception
	 */
	public int addRebat(int flag, String code, float value) throws Exception {
		DBService dbService = null;
		try {
			dbService = new DBService();
			String sql0 = "select * from sys_employ3 where code='" + code
					+ "' and flag=" + flag + "";
			if (dbService.hasData(sql0)) {
				return 1;
			}
			Object[] params = { null, code, value, flag };
			dbService.logData(4, params, "sys_employ3");
			return 0;
		} catch (Exception ex) {
			ex.printStackTrace();
			return 1;
		} finally {
			if(dbService!=null){
				dbService.close();
			}
		}
	}

	/**
	 * ��ȡҵ������
	 * 
	 * @return
	 * @throws Exception
	 */
	public List getProvince() {
		DBService dbService = null;
		try {
			dbService = new DBService();
			String sql = "select sa_id,sa_name FROM sys_area where sa_pid=1";
			List list = dbService.getList(sql);
			return list;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		} finally {
			if(dbService!=null){
				dbService.close();
			}
		}
	}

	/**
	 * ��ȡ��ֵ���
	 * 
	 * @return list
	 */
	public List getYjpz() {
		DBService dbService = null;
		try {
			dbService = new DBService();
			String sql = " SELECT cm_id,MIN,MAX FROM sys_valueRange order by MIN asc";
			List list = dbService.getList(sql);
			return list;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}finally {
			if(dbService!=null){
				dbService.close();
			}
		}
	}
	/**
	 * ��ȡ�ӿڱ�
	 * 
	 * @return
	 * @throws Exception
	 */
	public List getInterface() {
		DBService dbService = null;
		try {
			dbService = new DBService();
			String sql = "select * from sys_interface";
			List list = dbService.getList(sql);
			return list;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		} finally {
			if(dbService!=null){
				dbService.close();
			}
		}
	}

	/**
	 * ��ӽӿڶ�Ӧ��
	 * @param pid 
	 * @param moneyValues 
	 * @return ��¼��
	 */
	public int addInterMaping(String[] pid, int type, int interid,String[] moneyValues) {
		DBService dbService = null;
		try {
			dbService = new DBService();
			dbService.setAutoCommit(false);
			for(int k=0;k<moneyValues.length;k++)
			{
				for (int i = 0; i < pid.length; i++) {
					String sql0 = "select * from sys_interfaceMaping"
							+ " where pid=" + pid[i] + " and type=" + type +" and cmid='"+moneyValues[k]+"'";
					if (dbService.hasData(sql0)) {
						return -1;
					}
					Object[] params = { null, pid[i], type, interid,moneyValues[k],null};
					dbService.logData(6, params, "sys_interfaceMaping");
				}
			}
			dbService.commit();
			return 0;
		} catch (Exception ex) {
			Log.error("��ӽӿڶ�Ӧ�� �쳣��"+ex);
			return 1;
		} finally {
			if(dbService!=null)
				dbService.close();	
		}
	}

	/**
	 * ��ȡ�ӿ�����
	 * 
	 * @param type
	 * @return ��� ʡ�ݱ�� ʡ������ ��Ӫ������ �ӿ�id �ӿ�����
	 */
	public List getInterfaceMap(int type) {
		DBService dbService = null;
		try {
			dbService = new DBService();
			String sql = "select a.id,a.pid,sa_name,a.type,a.interid,c.name,a.cmid "
					+ " from sys_interfaceMaping a left join sys_area b on"
					+ " a.pid=b.sa_id left join sys_interface c on a.interid=c.id   "
					+ " where a.type=" + type+" order by sa_name asc";
			List list = dbService.getList(sql);
			return list;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		} finally {
			if(dbService!=null){
				dbService.close();
			}
		}
	}

	/**
	 * ��ȡ�ӿ�����
	 * 
	 * @param type
	 * @param cm_one 
	 * @return ��� ʡ�ݱ�� ʡ������ ��Ӫ������ �ӿ�id �ӿ�����
	 */
	public List getInterfaceMap(int type, String cm_one) {
		DBService dbService = null;
		try {
			dbService = new DBService();
			StringBuffer sql=null;
			if(type!=3){
			 sql = new StringBuffer("select a.id,a.pid,sa_name,a.type,a.interid,c.name,a.cmid "
					+ " from sys_interfaceMaping a left join sys_area b on"
					+ " a.pid=b.sa_id left join sys_interface c on a.interid=c.id   "
					+ " where a.type=" + type);
			if(cm_one!=null&&cm_one.trim().length()>0){
				sql.append(" and b.sa_id="+cm_one); 
			}
				sql.append(" order by sa_name asc");
			}else{
				 sql = new StringBuffer("select a.id,a.pid,sa_name,a.type,a.interid,c.name,a.cmid "
						+ " from sys_gddxinterfacemaping a left join sys_area b on"
						+ " a.pid=b.sa_id left join sys_interface c on a.interid=c.id   "
						+ " where a.type=0");
				if(cm_one!=null&&cm_one.trim().length()>0){
					sql.append(" and b.sa_id="+cm_one); 
				}
					sql.append(" order by sa_name asc");				
			}
			List list = dbService.getList(sql.toString());
			return list;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		} finally {
			if(dbService!=null){
				dbService.close();
			}
		}
	}

	/**
	 * �޸Ľӿڶ�Ӧ��ϵ��
	 * 
	 * @param type
	 */
	public int updateInterfaceMap(int type, int cm, int in,String moneyId) {
		DBService dbService = null;
		try {
			dbService = new DBService();
			String tablename=null;
			if(type!=3){
				tablename="sys_interfaceMaping";
			}else{
				tablename="sys_gddxinterfacemaping";
				type=0;
				String sql = "update sys_gddxinterfacemaping set interid=" + in;
				dbService.update(sql);
				return 0;
			}
			String s="SELECT COUNT(*) con FROM "+tablename+" WHERE TYPE="+type+" AND cmid='"+moneyId+"' AND pid=(SELECT pid FROM "+tablename+" WHERE id="+cm+")";
			if(dbService.getInt(s)>0)
			{
				String ss="SELECT cmid,interid con FROM "+tablename+" WHERE id="+cm;
				List arry=dbService.getList(ss);
				Object[] obj=(Object[])arry.get(0);
				if(moneyId.equals(obj[0]) && obj[1].equals(in+""))
				{
					return 0;
				}
				else
				{
					if(moneyId.equals(obj[0])&& !obj[1].equals(in+""))
					{
						String sql = "update "+tablename+" set interid=" + in+" where id=" + cm;
						dbService.update(sql);
						return 0;
					}
					else
					{
						return -1;
					}
				}
			}
			else
			{
				String sql = "update "+tablename+" set interid=" + in+" ,cmid='"+moneyId+"' "
						+ " where id=" + cm;
				dbService.update(sql);
				return 0;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			return 1;
		} finally {
			if(dbService!=null){
				dbService.close();
			}
		}
	}

	/**
	 * ��ȡ��ʡӶ������
	 * 
	 * @param pid
	 * @return
	 */
	public List getEmploy0Detail(String pid) {
		DBService dbService = null;
		try {
			dbService = new DBService();
			String sql = "select a.type,a.cm_id,a.value,concat(b.min,'--',b.max) as k"
					+ " from sys_employ0 a,sys_valueRange b"
					+ " where a.pid="
					+ pid + " and a.cm_id=b.cm_id order by a.type";
			List list = dbService.getList(sql);
			return list;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}finally {
			if(dbService!=null){
				dbService.close();
			}
		}

	}

	/**
	 * ��ȡ��ֱӪ�ۺ�ҵ��Ӷ������
	 * 
	 * @param pid
	 * @return
	 */
	public List getEmploy3Detail() {
		DBService dbService = null;
		try {
			dbService = new DBService();
			String sql = "select a.code,a.value,b.name from sys_employ3 a,wht_service b where a.code=b.code and a.flag=0";
			List list = dbService.getList(sql);
			return list;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		} finally {
			if(dbService!=null){
				dbService.close();
			}
		}

	}

	/**
	 * ����Ӷ��
	 * 
	 * @param lists
	 * @param down
	 * @param parentid
	 * @param lists1
	 * @return
	 */
	public int agentAndUserEmploy(List<Object[]> lists, String down,
			String parentid, List<Object[]> lists1) {
		DBService dbService = null;
		try {
			dbService = new DBService();
			dbService.setAutoCommit(false);
			for (Object[] object : lists) {
				dbService.update("update wht_agentAnduser set value='"
						+ object[1] + "' where userno='" + down
						+ "' and parentid=" + parentid + " and employ0id="
						+ object[0]);
			}
			for (Object[] object : lists1) {
				String bool=dbService.getString("select 1 from wht_agentAnduser  where userno='" + down+ "' and parentid=" + parentid + " and employ3id="+object[0]);
				if(bool==null || "".equals(bool))
				{
					dbService.update("INSERT INTO wht_agentAnduser(userno,parentid,employ3id,VALUE,flag) VALUES('"+down+"',"+parentid+","+object[0]+","+object[1]+",1)");
				}
				else
				{
					dbService.update("update wht_agentAnduser set value='"
						+ object[1] + "' where userno='" + down
						+ "' and parentid=" + parentid + " and employ3id="
						+ object[0]);
				}
			}
			dbService.commit();
			return 0;
		} catch (Exception ex) {
			dbService.rollback();
			ex.printStackTrace();
			return 1;
		} finally {
			if(dbService!=null){
				dbService.close();
			}
		}
	}

	/**
	 * �����̲鿴Ӷ����ϸ
	 * 
	 * @param pid
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getAgentEmploy(String pid, int parentid, String useno) {
		DBService dbService = null;
		try {
			dbService = new DBService();
			String sql = "select a.type,a.cm_id,a.value,concat(b.min,'--',b.max) as k,a.id"
					+ " from sys_employ0 a,sys_valueRange b"
					+ " where a.value>0 and a.pid="
					+ pid
					+ " and a.cm_id=b.cm_id order by a.type";
			List list = dbService.getList(sql);
			List list1 = new ArrayList();
			for (Object object : list) {
				String[] str = (String[]) object;
				float l = dbService
						.getFloat("select value from wht_agentAnduser where userno='"
								+ useno
								+ "' and parentid="
								+ parentid
								+ " and flag=0 and employ0id='" + str[4] + "'");
				if (l == 0) {
					list1.add(new String[] { str[0], str[1], str[2], str[3],
							"", str[4] });
				} else {
					list1.add(new String[] { str[0], str[1], str[2], str[3],
							String.valueOf(l), str[4] });
				}
			}
			list = null;
			return list1;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		} finally {
			if(dbService!=null){
				dbService.close();
			}
		}

	}

	/**
	 * �����̻�ȡ��ֱӪ�ۺ�ҵ��Ӷ������
	 * 
	 * @param pid
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getAgentEmploy3Detail(int parentid, String useno) {
		DBService dbService = null;
		try {
			dbService = new DBService();
			String sql = "select a.code,a.value,b.name,a.id from sys_employ3 a,wht_service b where a.code=b.code and a.flag=0 AND b.flag=1";
			List list = dbService.getList(sql);
			List list1 = new ArrayList();
			for (Object object : list) {
				String[] str = (String[]) object;
				float l = dbService
						.getFloat("select value from wht_agentAnduser where userno='"
								+ useno
								+ "' and parentid="
								+ parentid
								+ " and employ3id=" + str[3]);
				if (l == 0) {
					list1
							.add(new String[] { str[0], str[1], str[2], "",
									str[3] });
				} else {
					list1.add(new String[] { str[0], str[1], str[2],
							String.valueOf(l), str[3] });
				}
			}
			list = null;
			return list1;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		} finally {
			if(dbService!=null){
				dbService.close();
			}
		}

	}

	/**
	 * ���ݺŶλ�ȡ����ʡ����Ӫ������
	 * 
	 * @param num
	 * @return ʡ�ݱ�� ��Ӫ������ ��������
	 */
	public String[] getPhoneInfo(String num) {
		DBService dbService = null;
		try {
			dbService = new DBService();
			String sql = "select distinct a.province_code,a.phone_type,concat(b.sa_name,' ',a.cart_type) from sys_phone_area a,sys_area b"
					+ " where a.phone_num='"
					+ num
					+ "' and a.province_code=b.sa_id";
			return dbService.getStringArray(sql);
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		} finally {
			if(dbService!=null)
				dbService.close();
		}
	}

	/**
	 * ���ݺ����ȡ�û�����
	 * 
	 * @param num
	 * @return
	 */
	public String getuserName(String num) {
		DBService dbService = null;
		try {
			dbService = new DBService();
			String sql = "select user_ename from sys_user where user_login='"
					+ num + "'";
			return dbService.getString(sql);
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		} finally {
			if(dbService!=null){
				dbService.close();
			}
		}
	}

	/**
	 * ���ݺŶλ�ȡ����ʡ����Ӫ������
	 * 
	 * @param num
	 * @param dbService
	 * @return ʡ�ݱ�� ��Ӫ������
	 */
	public String[] getPhoneInfo(String num, DBService dbService) {
		try {
			String sql = "select distinct a.province_code,a.phone_type from sys_phone_area a "
					+ " where a.phone_num='" + num + "'";
			return dbService.getStringArray(sql);
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	/**
	 * Ź�ɹ�����ֵ
	 * 
	 * @param phone
	 *            �绰����
	 * @param orderID
	 *            ������
	 * @param userno
	 *            �����ϵͳ���
	 * @param userPid
	 *            �û�����ʡ�ݱ��
	 * @param fee
	 *            ���׽��
	 * @param areacode
	 *            ����
	 * @param login
	 *            ������½�˺�
	 * @param parentid
	 *            ���ڵ�id
	 * @return ��ֵ״̬
	 */
	public int ghFill(String phone, String orderID, String userno,
			String userPid, String fee, String areacode, String login,
			int parentid) {
		DBService dbService = null;
		try {
			dbService = new DBService();
			double mon = FloatArith.yun2li(fee);
			String[] str = getPhoneInfo(phone.substring(0, 7), dbService);
			if (null == str) {// ��������ʡ�ݱ��� ��Ӫ������
				return -1;
			}
			String[] result = getEmploy(dbService, str[1], userPid, str[0],
					userno, "0", (int) (mon / 1000.0), parentid,0);
			if (null == result) {
				return -1;
			}
			String flag = "0";// ���ǹ����Ǵ������¼�
								// AcctBillBean.getStatus(parentid+"")==true?
								// "1":"0";
			int k = nationFill(parentid + "", userno, str[0], userPid, str[1],
					phone, orderID, (int) mon, result, areacode, dbService,
					login, "220.231.192.42", "OF", flag);
			return k;
		} catch (Exception e) {
			return -1;
		} finally {
			if (null != dbService) {
				dbService.close();
			}
		}
	}

	/**
	 * ����ʡ����Ӫ�̻�ȡ��Ӧ�ӿ�
	 * 
	 * @param pid
	 * @param phonetype
	 * @param dbService
	 * @return �ӿ�id
	 * @throws SQLException
	 */
	public int getPhoneInfo(String pid, String phonetype, DBService dbService,double fee)
			throws SQLException {
		String strFee=fee+"";
		 String str = strFee.substring(0, strFee.indexOf("."));
		  int intgeo = Integer.parseInt(str);
		String sql = "SELECT interid FROM sys_interfaceMaping a,sys_valueRange b"+
			" WHERE a.cmid=b.cm_id AND a.pid="+ pid + " AND TYPE=" + phonetype +" and "+intgeo/1000+">=min and "+intgeo/1000+"<max";
		return dbService.getInt(sql);
	}

	/**
	 * ��ȡӶ�����
	 * 
	 * @param db
	 * @param phonetype
	 *            ��������
	 * @param userPid
	 *            �û�����ʡ��
	 * @param phonePid
	 *            ��������ʡ��
	 * @param userno
	 *            �û�ϵͳ���
	 * @param flag
	 *            1 ֱӪ 0��ֱӪ
	 * @param payfee
	 *            ��� Ԫ
	 * @param parentid
	 *            ���ڵ�id
	 * @param groups ����ӿ�Ӷ����             
	 * @return
	 */
	public String[] getEmploy(DBService db, String phonetype, String userPid,
			String phonePid, String userno, String flag, int payfee,
			int parentid,int groups) {
		String cm_id;
		try {
			cm_id = db.getString("select cm_id from sys_valueRange where min<="
					+ payfee + " and max>" + payfee);

			if (payfee > 0 && payfee <= 20) {
				String sql = "select value,value1 from sys_employ5 where pid="
						+ phonePid + " and type=" + phonetype + " and cm_id='"
						+ cm_id + "'";
				return db.getStringArray(sql);
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
			System.out.println(e1.toString());
			return null;
		}
		try {
			String sql = null;
			if ("1".equals(flag)) {// ֱӪ
				sql = "select value from sys_employ2 where pid=" + phonePid
						+ " and type=" + phonetype + " and cm_id='" + cm_id
						+ "'";
			} else if ("2".equals(flag)) {// �ӿ���
				sql = "select value from sys_tpemploy where pid=" + phonePid
						+ " and type=" + phonetype + " and cm_id='" + cm_id
						+ "' and groups="+groups;
			} else {// ��ֱӪ
				if (!userPid.equals(phonePid)) {
					sql = "select value from sys_employ1 where pid=" + phonePid
							+ " and type=" + phonetype + " and cm_id='" + cm_id
							+ "'";
				} else {// ��ʡ
					sql = "select a.value,b.value from sys_employ0 a,wht_agentAnduser b where a.id=b.employ0id and a.pid="
							+ phonePid
							+ " and a.type="
							+ phonetype
							+ " and b.flag=0 and a.cm_id='"
							+ cm_id
							+ "'"
							+ " and b.userno='" + userno + "'";
				}
			}
			return db.getStringArray(sql);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * ��ʯ����װ��Ϣ����business���е���ҵ����
	 * 
	 * @param parentID
	 *            ���ڵ�id
	 * @param userno
	 *            �û�ϵͳ���
	 * @param phonePid
	 *            �������ʡ��
	 * @param userPid
	 *            �û�����ʡ��
	 * @param phonetype
	 *            ��������
	 * @param phone
	 *            ����
	 * @param seqNo
	 *            ��ˮ��
	 * @param fee
	 *            ���׽��
	 * @param str
	 *            Ӷ������
	 * @param areaCode
	 *            ����
	 * @return 0 �ɹ� 1 ��ֵ��Ϣ����ʧ�� -1 ϵͳ��æ -2 �޶�Ӧ��ֵ�ӿ� 2 ������ 3ʧ��
	 * @throws Exception
	 */
	public int zshDxFill(String parentID, String userno, String phonePid,
			String userPid, String phonetype, String phone, String seqNo,
			double fee, String areaCode, String login, String numType,
			String serialNo, DBService db) throws Exception {
		//һ�����ڲ������ظ�����
		synchronized (lock) {
			if(MemcacheConfig.getInstance().get1(phone+fee+"repeat")){
				Log.info(phone+"-"+fee+" һ�����ڴ��ڸý���");
				return 10;
			}else{
				MemcacheConfig.getInstance().add(phone+fee+"repeat", phone);
			}
		}
		String tradeNo = "";
		int interfaceID = -1;
		DBService dbService =null;
		//�������㶫ʡ�����ţ���ȡ�ӿ�id
		try {
			dbService=new DBService();
			String sql="SELECT interid FROM sys_gddxinterfacemaping ORDER BY id ASC LIMIT 0,1";
			interfaceID = dbService.getInt(sql);
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;//ϵͳ��æ
		}finally{
			if(dbService!=null){
				dbService.close();
				dbService=null;
			}
		}
		if (interfaceID == -1) {
			return -1;
		}
		
		switch (interfaceID) {
		case 9:
			tradeNo = "09015"; // �㶫ѸԴ����
			break;
		case 19:
			tradeNo = "09028";// �㶫��������
			serialNo = Tools.getNow3()+((char)(new Random().nextInt(26) + (int)'A'))+((int)(Math.random()*10000)+10000);
			break;
		}
		
		int facctfee = 0;
		int parentfee = 0;
		String userRebat = DBConfig.getInstance().getGdUserRebate();
		String parentRebat = DBConfig.getInstance().getGdParentRebat();
		TradeMsg msg = new TradeMsg();
		msg.setSeqNo(seqNo);
		msg.setFlag("0");// ������
		msg.setMsgFrom2("0103");
		msg.setServNo("00");
		msg.setTradeNo(tradeNo);
		Map<String, String> content = new HashMap<String, String>();
		content.put("phone", phone);
		content.put("phoneNum", phone);
		content.put("userNo", userno);
		content.put("parentID", parentID);
		content.put("isline", "1");// �Ƿ�ֱӪ��ʾ 0��ʾ��ֱӪ 1��ʾֱӪ
		content.put("userPid", userPid);
		content.put("phonePid", phonePid);
		content.put("areaCode", areaCode);
		content.put("phoneType", phonetype);
		content.put("tradeFee", (int) fee + "");
		content.put("facctFee", (int) fee + "");
		content.put("parentFee", 0 + "");
		content.put("interfaceID", interfaceID+"");
		content.put("login", login);
		content.put("numType", numType);
		content.put("time", Tools.getNow3());
		content.put("serialNo", serialNo);
		content.put("termType", "0");
		msg.setContent(content);
		if (!PortalSend.getInstance().sendmsg(msg)) {
			return 1;// ������Ϣʧ��,��ֵʧ��
		}
		saveBarcodeAndOrder(1, seqNo, db); // �������뼰����
		int k = 0;
		TradeMsg rsMsg = null;
		while (k < 180) {
			k++;
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			rsMsg = MemcacheConfig.getInstance().get(seqNo);
			if (null == rsMsg) {
				continue;
			} else {
				MemcacheConfig.getInstance().delete(seqNo);
				break;
			}
		}
		if (null == rsMsg && k >= 90) {
			return 2;// ������
		}
		String code = rsMsg.getRsCode();
		if ("103".equals(code) || "002".equals(code)
				|| "045".equals(code)) {
			// ɾ����������
			delBarcodeAndOrder(seqNo, db);
		}
		if ("000".equals(code)) {
			return 0;// �ɹ�
		}else if("102".equals(code)){
			return -5;//����
		} else if("101".equals(code)){
			return 6;//�����ظ�
		}else if("002".equals(code) || "001".equals(code)){// ʧ��
			return 1;//ʧ��
		}else {
			return 2;//������ 103ϵͳ��æ 003��ʱ��008���� 009�쳣  105�쳣 
		}
	}

	/**
	 * ��װ��Ϣ����business����ҵ����
	 * 
	 * @param parentID
	 *            ���ڵ�id
	 * @param userno
	 *            �û�ϵͳ���
	 * @param phonePid
	 *            �������ʡ��
	 * @param userPid
	 *            �û�����ʡ��
	 * @param phonetype
	 *            ��������
	 * @param phone
	 *            ����
	 * @param seqNo
	 *            ��ˮ��
	 * @param fee
	 *            ���׽��
	 * @param str
	 *            Ӷ������
	 * @param areaCode
	 *            ����
	 * @return 0 �ɹ� 1 ��ֵ��Ϣ����ʧ�� -1 ϵͳ��æ -2 �޶�Ӧ��ֵ�ӿ� 2 ������ 3ʧ��
	 * @throws Exception
	 */
	public int dxFill(String parentID, String userno, String phonePid,
			String userPid, String phonetype, String phone, String seqNo,
			double fee, String areaCode, String login, String numType,
			String serialNo) throws Exception {
		String tradeNo = "01001";
		int facctfee = 0;
		int parentfee = 0;
		String userRebat = DBConfig.getInstance().getGdUserRebate();
		String parentRebat = DBConfig.getInstance().getGdParentRebat();
		String flag = AcctBillBean.getStatus(parentID + "") == true ? "1" : "0";
		if ("1".equals(flag)) {// ֱӪ
			facctfee = (int) FloatArith.mul(fee, 1 - FloatArith.div(Double
					.valueOf(userRebat), 100));
		} else {// ��ֱӪ
			facctfee = (int) FloatArith.mul(fee, 1 - FloatArith.div(Double
					.valueOf(userRebat), 100));
			parentfee = (int) FloatArith.mul(fee, FloatArith.div(Double
					.valueOf(parentRebat), 100));
		}
		TradeMsg msg = new TradeMsg();
		msg.setSeqNo(seqNo);
		msg.setFlag("0");// ������
		msg.setMsgFrom2("0103");
		msg.setServNo("00");
		msg.setTradeNo(tradeNo);
		Map<String, String> content = new HashMap<String, String>();
		content.put("phone", phone);
		content.put("userNo", userno);
		content.put("parentID", parentID);
		content.put("isline", flag);// �Ƿ�ֱӪ��ʾ 0��ʾ��ֱӪ 1��ʾֱӪ
		content.put("userPid", userPid);
		content.put("phonePid", phonePid);
		content.put("areaCode", areaCode);
		content.put("phoneType", phonetype);
		content.put("tradeFee", (int) fee + "");
		content.put("facctFee", facctfee + "");
		content.put("parentFee", parentfee + "");
		content.put("interfaceID", "5");
		content.put("login", login);
		content.put("numType", numType);
		content.put("time", Tools.getNow3());
		content.put("serialNo", serialNo);
		content.put("termType", "0");
		msg.setContent(content);
		if (!PortalSend.getInstance().sendmsg(msg)) {
			return 1;// ������Ϣʧ��,��ֵʧ��
		}
		int k = 0;
		TradeMsg rsMsg = null;
		while (k < 90) {
			k++;
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			rsMsg = MemcacheConfig.getInstance().get(seqNo);
			if (null == rsMsg) {
				continue;
			} else {
				MemcacheConfig.getInstance().delete(seqNo);
				break;
			}
		}
		if (null == rsMsg && k >= 90) {
			return 2;// ������
		}
		String code = rsMsg.getRsCode();
		if ("000".equals(code)) {
			return 0;// �ɹ�
		} else {
			return 3;// ʧ��
		}
	}
//	/**�ƶ���ֵ
//	 * @param parentID
//	 * @param userno
//	 * @param phonePid
//	 * @param userPid
//	 * @param phonetype
//	 * @param phone
//	 * @param seqNo
//	 * @param fee
//	 * @param areaCode
//	 * @param login
//	 * @param numType
//	 * @param serialNo
//	 * @return int
//	 * @throws Exception
//	 */
//	public int MobileFill(String parentID, String userno, String phonePid,
//			String userPid, String phonetype, String phone, String seqNo,
//			double fee, String areaCode, String login, String numType) throws Exception {
//		String userRebat= DBConfig.getInstance().getGdUserRebate();//Ӷ������;
//		String parentRebat = DBConfig.getInstance().getGdParentRebat();//���ڵ�Ӷ��
//		
//		String tradeNo = "09014";
//		int facctfee = 0;
//		int parentfee = 0;
//		
//		String flag = AcctBillBean.getStatus(parentID + "") == true ? "1" : "0";
//		if ("1".equals(flag)) {// ֱӪ
//			facctfee = (int) FloatArith.mul(fee, 1 - FloatArith.div(Double
//					.valueOf(userRebat), 100));
//		} else {// ��ֱӪ
//			facctfee = (int) FloatArith.mul(fee, 1 - FloatArith.div(Double
//					.valueOf(userRebat), 100));
//			parentfee = (int) FloatArith.mul(fee, FloatArith.div(Double
//					.valueOf(parentRebat), 100));
//		}
////		if ("09014".equals(tradeNo)) {// ��Ѹ��ֵ
////			String orderID = com.wlt.webm.tool.Tools.getStreamDate()
////			+ com.wlt.webm.tool.Tools.buildRandom(2);
////			return yiKaHuiService(msg, ip, orderID,tradeNo,seqNo);
//		return -1;
//	}
	/** ʡ��ͨ tcp ��ֵ
	 * @param parentID
	 * @param userno
	 * @param phonePid
	 * @param userPid
	 * @param phonetype
	 * @param phone
	 * @param seqNo
	 * @param fee
	 * @param areaCode
	 * @param login
	 * @param numType
	 * @param serialNo
	 * @return
	 * @throws Exception
	 */
	public int LianTongFill(String parentID, String userno, String phonePid,
			String userPid, String phonetype, String phone, String seqNo,
			double fee, String areaCode, String login, String numType,
			String serialNo) throws Exception {
		String userRebat= DBConfig.getInstance().getGdltUserRebate();//Ӷ������;
		String parentRebat = DBConfig.getInstance().getGdltParentRebat();//���ڵ�Ӷ��
		
		int facctfee = 0;
		int parentfee = 0;
		
		String flag = AcctBillBean.getStatus(parentID + "") == true ? "1" : "0";
		if ("1".equals(flag)) {// ֱӪ
			facctfee = (int) FloatArith.mul(fee, 1 - FloatArith.div(Double
					.valueOf(userRebat), 100));
		} else {// ��ֱӪ
			facctfee = (int) FloatArith.mul(fee, 1 - FloatArith.div(Double
					.valueOf(userRebat), 100));
			parentfee = (int) FloatArith.mul(fee, FloatArith.div(Double
					.valueOf(parentRebat), 100));
		}
		TradeMsg msg = new TradeMsg();
		msg.setSeqNo(seqNo);
		msg.setFlag("0");// ������
		msg.setMsgFrom2("0103");
		msg.setServNo("00");
		msg.setTradeNo("09022");
		Map<String, String> content = new HashMap<String, String>();
		content.put("phoneNum", phone);
		content.put("userNo", userno);
		content.put("parentID", parentID);
		content.put("isline", flag);// �Ƿ�ֱӪ��ʾ 0��ʾ��ֱӪ 1��ʾֱӪ
		content.put("userPid", userPid);
		content.put("phonePid", phonePid);
		content.put("areaCode", areaCode);
		content.put("phoneType", phonetype);
		content.put("tradeFee", (int) fee + "");
		content.put("facctFee", facctfee + "");
		content.put("parentFee", parentfee + "");
		content.put("login", login);
		content.put("numType", numType);
		content.put("time", Tools.getNow3());
		content.put("serialNo", serialNo);
		content.put("interfaceID", "15");
		content.put("termType", "0");
		msg.setContent(content);
		if (!PortalSend.getInstance().sendmsg(msg)) {
			return 1;// ������Ϣʧ��,��ֵʧ��
		}
		int k = 0;
		TradeMsg rsMsg = null;
		while (k < 90) {
			k++;
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			rsMsg = MemcacheConfig.getInstance().get(seqNo);
			if (null == rsMsg) {
				continue;
			} else {
				MemcacheConfig.getInstance().delete(seqNo);
				break;
			}
		}
		if (null == rsMsg && k >= 90) {
			return 2;// ������
		}
		String code = rsMsg.getRsCode();
		if ("000".equals(code)) {
			return 0;// �ɹ�
		}else if("102".equals(code)){
			return 5;//����
		}else if("001".equals(code) || "002".equals(code) || "101".equals(code)) {
			return 1;// ʧ��
		}else if("103".equals(code) || "009".equals(code) || "105".equals(code)){
			return 3;//�쳣
		}else if("045".equals(code)){
			return 4;//�������ظ�
		}else{ //008
			return 2;//������
		}
	}
	/**
	 * ��װ��Ϣ����business����ҵ����
	 * 
	 * @param parentID
	 *            ���ڵ�id
	 * @param userno
	 *            �û�ϵͳ���
	 * @param phonePid
	 *            �������ʡ��
	 * @param userPid
	 *            �û�����ʡ��
	 * @param phonetype
	 *            ��������
	 * @param phone
	 *            ����
	 * @param seqNo
	 *            ��ˮ��
	 * @param fee
	 *            ���׽��
	 * @param str
	 *            Ӷ������
	 * @param areaCode
	 *            ����
	 * @param login 
	 * @param login 
	 * @param serialNo 
	 * @return 0 �ɹ� 1 ��ֵ��Ϣ����ʧ�� -1 ϵͳ��æ -2 �޶�Ӧ��ֵ�ӿ� 2 ������ 3ʧ��
	 * @throws Exception
	 */
	public int XYdxFill(String parentID, String userno, String phonePid,
			String userPid, String phonetype, String phone, String seqNo,
			double fee, String areaCode, String login, String numType,
			String serialNo,String gh) throws Exception {
		if("01".equals(numType))//��һ
		{
			numType="000001";
		}
		if("02".equals(numType))//���
		{
			numType="000003";
		}
		if("03".equals(numType)){//�ۺ�
			numType="000004";
		}
		
		String tradeNo = "";
		int facctfee = 0;
		int parentfee = 0;
		int interfaceID = -1;
		DBService dbService =null;
		String[] employFee =null;
		String flag = AcctBillBean.getStatus(parentID + "") == true ? "1" : "0";
		//�������㶫ʡ�����ţ���ȡ�ӿ�id
		try {
			dbService=new DBService();
			String sql="SELECT interid FROM sys_gddxinterfacemaping ORDER BY id ASC LIMIT 0,1";
			interfaceID = dbService.getInt(sql);
			employFee=getZZEmploy(dbService, "xyjb01", flag,
					userno, Integer.parseInt(parentID));
			if (null == employFee) {
				return 10;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;//ϵͳ��æ
		}finally{
			if(dbService!=null){
				dbService.close();
				dbService=null;
			}
		}
		if (interfaceID == -1) {
			return -2;
		}
		switch (interfaceID) {
		case 9:
			tradeNo = "09015"; // �㶫ѸԴ����
			break;
		case 19:
			tradeNo = "09028";// �㶫��������
			serialNo = Tools.getNow3()+((char)(new Random().nextInt(26) + (int)'A'))+((int)(Math.random()*10000)+10000);
			break;
		}
		
		/**��ǰ���������ļ�����Ӷ�� 
		String userRebat= DBConfig.getInstance().getGdUserRebate();//Ӷ������;
		String parentRebat = DBConfig.getInstance().getGdParentRebat();//���ڵ�Ӷ��
		if ("1".equals(flag)) {// ֱӪ
			facctfee = (int) FloatArith.mul(fee, 1 - FloatArith.div(Double
					.valueOf(userRebat), 100));
		} else {// ��ֱӪ
			facctfee = (int) FloatArith.mul(fee, 1 - FloatArith.div(Double
					.valueOf(userRebat), 100));
			parentfee = (int) FloatArith.mul(fee, FloatArith.div(Double
					.valueOf(parentRebat), 100));
		}
		*/
		//�µļ���Ӷ�𷽷�
		if ("1".equals(flag)) {
			facctfee = (int) FloatArith.mul(fee, 1 - FloatArith.div(
					Double.valueOf(employFee[0]), 100));
		} else {
			double a = FloatArith.div(Double.valueOf(employFee[0]), 100);// ��Ӷ���
			double b = FloatArith.mul(fee, a);// ��Ӷ��
			double c = FloatArith.mul(b, FloatArith.div(Double
					.valueOf(employFee[1]), 100));// �����Ӧ��Ӷ��
			facctfee = (int) FloatArith.sub(fee, c);// Ӧ�۽��
			parentfee = (int) FloatArith.sub(b, c);// �ϼ��ڵ�Ӧ��Ӷ��
		}
		//�µĻ�ȡӶ�𷽷�����
		TradeMsg msg = new TradeMsg();
		msg.setSeqNo(seqNo);
		msg.setFlag("0");// ������
		msg.setMsgFrom2("0103");
		msg.setServNo("00");
		msg.setTradeNo(tradeNo);
		Map<String, String> content = new HashMap<String, String>();
		content.put("phoneNum", phone);
		content.put("userNo", userno);
		content.put("parentID", parentID);
		content.put("isline", flag);// �Ƿ�ֱӪ��ʾ 0��ʾ��ֱӪ 1��ʾֱӪ
		content.put("userPid", userPid);
		content.put("phonePid", phonePid);
		content.put("areaCode", areaCode);
		content.put("phoneType", phonetype);
		content.put("tradeFee", (int) fee + "");
		content.put("facctFee", facctfee + "");
		content.put("parentFee", parentfee + "");
		content.put("login", login);
		content.put("numType", numType);
		content.put("time", Tools.getNow3());
		content.put("serialNo", serialNo);
		content.put("interfaceID", interfaceID+"");
		content.put("termType", "0");
		content.put("gh", gh);
		msg.setContent(content);
		if (!PortalSend.getInstance().sendmsg(msg)) {
			return 1;// ������Ϣʧ��,��ֵʧ��
		}
		int k = 0;
		TradeMsg rsMsg = null;
		while (k < 90) {
			k++;
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			rsMsg = MemcacheConfig.getInstance().get(seqNo);
			if (null == rsMsg) {
				continue;
			} else {
				MemcacheConfig.getInstance().delete(seqNo);
				break;
			}
		}
		if (null == rsMsg && k >= 90) {
			return 2;// ������
		}
		String code = rsMsg.getRsCode();
		if ("000".equals(code)) {
			return 0;// �ɹ�
		}else if("102".equals(code)){
			return -5;//����
		} else if("101".equals(code)){
			return 6;//�����ظ�
		}else if("002".equals(code) || "001".equals(code)){// ʧ��
			return 3;//ʧ��
		}else {
			return 2;//������ 103 ϵͳ��æ
		}
	}


	/**
	 * �ӿ��� ���� ���÷���
	 * @param parentID
	 * @param userno
	 * @param phonePid
	 * @param userPid
	 * @param phonetype
	 * @param phone
	 * @param seqNo
	 * @param fee
	 * @param areaCode
	 * @param login
	 * @param numType
	 * @param serialNo
	 * @param gh
	 * @return
	 * @throws Exception
	 */
	 
	public int XYdxFill_Interface(String parentID, String userno, String phonePid,
			String userPid, String phonetype, String phone, String seqNo,
			double fee, String areaCode, String login, String numType,
			String serialNo,String gh) throws Exception {
		if("01".equals(numType))//��һ
		{
			numType="000001";
		}
		if("02".equals(numType))//���
		{
			numType="000003";
		}
		if("03".equals(numType)){//�ۺ�
			numType="000004";
		}
		
		String userRebat= DBConfig.getInstance().getGdUserRebate();//Ӷ������;
		String parentRebat = DBConfig.getInstance().getGdParentRebat();//���ڵ�Ӷ��
		String tradeNo = "";
		int facctfee = 0;
		int parentfee = 0;
		
		int interfaceID = -1;
		DBService dbService =null;
		//�������㶫ʡ�����ţ���ȡ�ӿ�id
		try {
			dbService=new DBService();
//			String strFee=fee+"";
//			 String str = strFee.substring(0, strFee.indexOf("."));
//			  int intgeo = Integer.parseInt(str);
//			String sql = "SELECT interid FROM sys_gddxinterfacemaping a,sys_valueRange b"+
//				" WHERE a.cmid=b.cm_id AND a.pid="+ phonePid + " AND TYPE=0 AND TYPE=" + phonetype +" and "+intgeo/1000+">=min and "+intgeo/1000+"<max";
			String sql="SELECT interid FROM sys_gddxinterfacemaping ORDER BY id ASC LIMIT 0,1";
			interfaceID = dbService.getInt(sql);
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;//ϵͳ��æ
		}finally{
			if(dbService!=null){
				dbService.close();
				dbService=null;
			}
		}
		if (interfaceID == -1) {
			return -2;
		}
		
		switch (interfaceID) {
		case 9:
			tradeNo = "09015"; // �㶫ѸԴ����
			break;
		case 19:
			tradeNo = "09028";// �㶫��������
			serialNo = Tools.getNow3()+((char)(new Random().nextInt(26) + (int)'A'))+((int)(Math.random()*10000)+10000);
			break;
		}
		
		String flag = AcctBillBean.getStatus(parentID + "") == true ? "1" : "0";
		if ("1".equals(flag)) {// ֱӪ
			facctfee = (int) FloatArith.mul(fee, 1 - FloatArith.div(Double
					.valueOf(userRebat), 100));
		} else {// ��ֱӪ
			facctfee = (int) FloatArith.mul(fee, 1 - FloatArith.div(Double
					.valueOf(userRebat), 100));
			parentfee = (int) FloatArith.mul(fee, FloatArith.div(Double
					.valueOf(parentRebat), 100));
		}
		TradeMsg msg = new TradeMsg();
		msg.setSeqNo(seqNo);
		msg.setFlag("0");// ������
		msg.setMsgFrom2("0103");
		msg.setServNo("00");
		msg.setTradeNo(tradeNo);
		Map<String, String> content = new HashMap<String, String>();
		content.put("phoneNum", phone);
		content.put("userNo", userno);
		content.put("parentID", parentID);
		content.put("isline", flag);// �Ƿ�ֱӪ��ʾ 0��ʾ��ֱӪ 1��ʾֱӪ
		content.put("userPid", userPid);
		content.put("phonePid", phonePid);
		content.put("areaCode", areaCode);
		content.put("phoneType", phonetype);
		content.put("tradeFee", (int) fee + "");
		content.put("facctFee", facctfee + "");
		content.put("parentFee", parentfee + "");
		content.put("login", login);
		content.put("numType", numType);
		content.put("time", Tools.getNow3());
		content.put("serialNo", serialNo);
		content.put("interfaceID", interfaceID+"");
		content.put("termType", "0");
		content.put("gh", gh);
		msg.setContent(content);
		if (!PortalSend.getInstance().sendmsg(msg)) {
			return 1;// ������Ϣʧ��,��ֵʧ��
		}
		int k = 0;
		TradeMsg rsMsg = null;
		while (k < 90) {
			k++;
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			rsMsg = MemcacheConfig.getInstance().get(seqNo);
			if (null == rsMsg) {
				continue;
			} else {
				MemcacheConfig.getInstance().delete(seqNo);
				break;
			}
		}
		if (null == rsMsg && k >= 90) {
			return 2;// ������
		}
		String code = rsMsg.getRsCode();
		if ("000".equals(code)) {
			return 0;// �ɹ�
		}else if("102".equals(code)){
			return -5;//����
		} else if("101".equals(code)){
			return 6;//�����ظ�
		}else if("002".equals(code) || "001".equals(code)){// ʧ��
			return 3;//ʧ��
		}else {
			return 2;//������ 103 ϵͳ��æ
		}
	}
	
	/**
	 * ȫ�����Ź̻���ֵ
	 * @param parentID
	 * @param userno
	 * @param phonePid
	 * @param userPid
	 * @param phonetype
	 * @param phone
	 * @param seqNo
	 * @param fee
	 * @param str
	 * @param areaCode
	 * @param dbService
	 * @param login
	 * @param ip
	 * @param gh
	 * @param flag
	 * @return
	 */
	public int nationFillGH(String parentID, String userno, String phonePid,
			String userPid, String phonetype, String phone, String seqNo,
			double fee, String[] str, String areaCode, DBService dbService,
			String login, String ip, String gh, String flag) {
		//һ�����ڲ������ظ�����
		synchronized (lock) {
			if(MemcacheConfig.getInstance().get1(phone+fee+"repeat")){
				Log.info(phone+"-"+fee+" һ�����ڴ��ڸý���");
				return 10;
			}else{
				MemcacheConfig.getInstance().add(phone+fee+"repeat", phone);
			}
		}
		int interfaceID = 6;
		String tradeNo = "09013";// ������ͨ �Ĵ�
		int facctfee = 0;
		int parentfee = 0;
		String serialNo = seqNo;
		
		if (fee > 0 && fee < 30000) {
			if ("1".equals(flag)) {// ֱӪ
				facctfee = Integer.parseInt(str[0]);
			} else {
				facctfee = Integer.parseInt(str[0]);
				parentfee = Integer.parseInt(str[1]);
			}
		} else {
			if ("1".equals(flag)) {// ֱӪ
				facctfee = (int) FloatArith.mul(fee, 1 - FloatArith.div(Double
						.valueOf(str[0]), 100));
			} else if (!userPid.equals(phonePid)) {// ��ֱӪ �Ǳ�ʡ
				TenpayXmlPath tenpayxmlpath = new TenpayXmlPath();
				facctfee = (int) FloatArith.mul(fee, 1 - FloatArith.div(Double
						.valueOf(str[0]), 100));
				parentfee = (int) FloatArith
						.mul(fee, FloatArith.div(Double.valueOf(DBConfig
								.getInstance().getOtherRebate()), 100));
			} else {// ��ֱӪ ��ʡ
				double a = FloatArith.div(Double.valueOf(str[0]), 100);// ��Ӷ���
				double b = FloatArith.mul(fee, a);// ��Ӷ��
				double c = FloatArith.mul(b, FloatArith.div(Double
						.valueOf(str[1]), 100));// �����Ӧ��Ӷ��
				facctfee = (int) FloatArith.sub(fee, c);// Ӧ�۽��
				parentfee = (int) FloatArith.sub(b, c);// �ϼ��ڵ�Ӧ��Ӷ��
			}
		}
		TradeMsg msg = new TradeMsg();
		msg.setSeqNo(seqNo);
		msg.setFlag("0");// ������
		msg.setMsgFrom2("0103");
		msg.setServNo("00");
		msg.setTradeNo(tradeNo);
		Map<String, String> content = new HashMap<String, String>();
		content.put("phoneNum", phone);
		content.put("userNo", userno);
		content.put("parentID", parentID);
		content.put("isline", flag);// �Ƿ�ֱӪ��ʾ 0��ʾ��ֱӪ 1��ʾֱӪ
		content.put("userPid", userPid);
		content.put("phonePid", phonePid);
		content.put("areaCode", areaCode);
		content.put("phoneType", phonetype);
		content.put("tradeFee", (int) fee + "");
		content.put("facctFee", facctfee + "");
		content.put("parentFee", parentfee + "");
		content.put("interfaceID", interfaceID + "");
		content.put("login", login);
		content.put("time", Tools.getNow3());
		content.put("numType","01");
		
		Log.info("ȫ�����Ź̻��������趩����:" + serialNo);
		content.put("serialNo", serialNo);
		content.put("termType", "0");
		msg.setContent(content);
		
		// ������ͨ �Ĵ���ֵ
		String orderID = com.wlt.webm.tool.Tools.getStreamDate()
		+ com.wlt.webm.tool.Tools.getCMPayNO()
		+ com.wlt.webm.tool.Tools.buildRandom(2);
		return qgdxghcz(msg, ip, orderID,tradeNo);
		
	}
	
	/**
	 * ��װ��Ϣ����business����ҵ����
	 * 
	 * @param parentID
	 *            ���ڵ�id
	 * @param userno
	 *            �û�ϵͳ���
	 * @param phonePid
	 *            �������ʡ��
	 * @param userPid
	 *            �û�����ʡ��
	 * @param phonetype
	 *            ��������
	 * @param phone
	 *            ����
	 * @param seqNo
	 *            ��ˮ��
	 * @param fee
	 *            ���׽��
	 * @param str
	 *            Ӷ������
	 * @param areaCode
	 *            ����
	 * @param dbService
	 *            ���ݿ����
	 * @param login
	 *            ��½�˺�
	 * @param ip
	 * @param gh
	 * @param flag
	 *            �Ƿ�ֱӪ
	 * @return 0 �ɹ� 1 ��ֵ��Ϣ����ʧ�� -1 ϵͳ��æ -2 �޶�Ӧ��ֵ�ӿ� 2 ������ 3ʧ��
	 */
	public int nationFill(String parentID, String userno, String phonePid,
			String userPid, String phonetype, String phone, String seqNo,
			double fee, String[] str, String areaCode, DBService dbService,
			String login, String ip, String gh, String flag) {
		//һ�����ڲ������ظ�����
		if(!"OF".equals(gh) && gh==null)
		{
			synchronized (lock) {
				if(MemcacheConfig.getInstance().get1(phone+fee+"repeat")){
					Log.info(phone+"-"+fee+" һ�����ڴ��ڸý���");
					return 10;
				}else{
					MemcacheConfig.getInstance().add(phone+fee+"repeat", phone);
				}
			}
		}
		//
		int interfaceID = -1;
		String tradeNo = "";
		int facctfee = 0;
		int parentfee = 0;
		String serialNo = seqNo;
		try {
			interfaceID = getPhoneInfo(phonePid, phonetype, dbService, fee);
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}
		if (interfaceID == -1) {
			return -2;
		}
		switch (interfaceID) {
		case 3:
			tradeNo = "09001"; // �㶫�ƶ�
			serialNo = "XY0315012880537" + Tools.getNow3().substring(2)
					+ Tools.buildRandom(2) + Tools.buildRandom(3);
			break;
		case 4:
			tradeNo = "01013";// �㶫��ͨ
			serialNo = "XY0418682022602" + Tools.getNow3().substring(2)
					+ Tools.buildRandom(2) + Tools.buildRandom(3); // �㶫��ͨ
			break;
		case 5:
			tradeNo = "01001"; // ��������
			break;
		case 1:
			tradeNo = "01000";// ȫ��
			break;
		case 6:
			tradeNo = "09013";// ������ͨ �Ĵ�
			break;
		case 7:
			tradeNo="09012";//����
			serialNo = "wh09012" + Tools.getNow3().substring(6)
			+ Tools.buildRandom(2) + Tools.buildRandom(3); //�����ƶ�
			break;
		case 8:
			tradeNo = "09014";// ������Ѹ
			break;
		case 11:
			tradeNo="09016";//����
			serialNo = "zh09016" + Tools.getNow3().substring(6)
			+ Tools.buildRandom(2) + Tools.buildRandom(3); //�㽭�����ƶ�
			break;
		case 12:
			tradeNo="09018";//�װ���
//			serialNo = "yb09018" + Tools.getNow3().substring(6)
//			+ Tools.buildRandom(2) + Tools.buildRandom(3); //�װ����ƶ�
			break;
		case 13:
			tradeNo = "09020";// �ڿ�
			break;
		case 14://��ͨ tcp  �ƶ�
			tradeNo="09021";
			serialNo = ((int)(Math.random()*1000)+1000)+""+((char)(new Random().nextInt(26) + (int)'A'))+""+((int)(Math.random()*100)+100);
			break;
		case 15://ʡ��ͨ  tcp 
			tradeNo="09022";
			serialNo = Tools.getNow3()+((char)(new Random().nextInt(26) + (int)'A'))+((int)(Math.random()*10000)+10000);
			break;
		case 17://����ӿ�  http
			tradeNo = "09025";
			break;
		case 18://�����ƶ� tcp
			tradeNo="09026";
			serialNo = Tools.getNow3()+((char)(new Random().nextInt(26) + (int)'A'))+((int)(Math.random()*10000)+10000);
			break;
		case 22://��ʢ �ƶ�
			tradeNo="09032";
			serialNo = "0000"+((char)(new Random().nextInt(26) + (int)'a'))+""+((int)(Math.random()*1000)+1000)+""+((char)(new Random().nextInt(26) + (int)'A'))+""+((int)(Math.random()*10000)+10000)+((char)(new Random().nextInt(26) + (int)'A'));
			break;
		case 24://�ֳ��ƶ�
			tradeNo = "09034";
			break;
		case 28://��ʤ�ӿ�
			tradeNo="09036";
			break;
		case 30://�Ͱ��ƶ��ӿ�
			tradeNo="09037";
			break;
		case 40://�����ֵ�ӿ�
			tradeNo="09038";
			break;
		default:
			Log.info("��ֵ����:"+phone+",δ��ƥ�䵽�ӿ�,,,,");
			return 1; // ʧ��
		}
		if (fee > 0 && fee < 30000) {
			if ("1".equals(flag)) {// ֱӪ
				facctfee = Integer.parseInt(str[0]);
			} else {
				facctfee = Integer.parseInt(str[0]);
				parentfee = Integer.parseInt(str[1]);
			}
		} else {
			if ("1".equals(flag)) {// ֱӪ
				facctfee = (int) FloatArith.mul(fee, 1 - FloatArith.div(Double
						.valueOf(str[0]), 100));
			} else if (!userPid.equals(phonePid)) {// ��ֱӪ �Ǳ�ʡ
				TenpayXmlPath tenpayxmlpath = new TenpayXmlPath();
				facctfee = (int) FloatArith.mul(fee, 1 - FloatArith.div(Double
						.valueOf(str[0]), 100));
				parentfee = (int) FloatArith
						.mul(fee, FloatArith.div(Double.valueOf(DBConfig
								.getInstance().getOtherRebate()), 100));
			} else {// ��ֱӪ ��ʡ
				double a = FloatArith.div(Double.valueOf(str[0]), 100);// ��Ӷ���
				double b = FloatArith.mul(fee, a);// ��Ӷ��
				double c = FloatArith.mul(b, FloatArith.div(Double
						.valueOf(str[1]), 100));// �����Ӧ��Ӷ��
				facctfee = (int) FloatArith.sub(fee, c);// Ӧ�۽��
				parentfee = (int) FloatArith.sub(b, c);// �ϼ��ڵ�Ӧ��Ӷ��
			}
		}
		TradeMsg msg = new TradeMsg();
		msg.setSeqNo(seqNo);
		msg.setFlag("0");// ������
		msg.setMsgFrom2("0103");
		msg.setServNo("00");
		msg.setTradeNo(tradeNo);
		Map<String, String> content = new HashMap<String, String>();
		content.put("phoneNum", phone);
		content.put("userNo", userno);
		content.put("parentID", parentID);
		content.put("isline", flag);// �Ƿ�ֱӪ��ʾ 0��ʾ��ֱӪ 1��ʾֱӪ
		content.put("userPid", userPid);
		content.put("phonePid", phonePid);
		content.put("areaCode", areaCode);
		content.put("phoneType", phonetype);
		content.put("tradeFee", (int) fee + "");
		content.put("facctFee", facctfee + "");
		content.put("parentFee", parentfee + "");
		content.put("interfaceID", interfaceID + "");
		content.put("login", login);
		content.put("time", Tools.getNow3());
		content.put("numType","01");
		if("09022".equals(tradeNo)){
			content.put("numType","1");//ʡ��ͨ
		}
		if (null != gh && "OF".equals(gh)) {
			content.put("gh", gh);
		}
		Log.info("�������趩����:" + seqNo);
		if(tradeNo.equals("09018")){
			content.put("serialNo", seqNo);
		}else{
			content.put("serialNo", serialNo);
		}
		content.put("termType", "0");
		msg.setContent(content);
		if ("01000".equals(tradeNo)) {// һ���ݳ�ֵ
			String orderID = com.wlt.webm.tool.Tools.getStreamDate()
					+ com.wlt.webm.tool.Tools.getCMPayNO()
					+ com.wlt.webm.tool.Tools.buildRandom(2);
			return yiKaHuiService(msg, ip, orderID,tradeNo,seqNo);
		}else if ("09014".equals(tradeNo)) {// ��Ѹ��ֵ
			String orderID = com.wlt.webm.tool.Tools.getStreamDate()
			+ com.wlt.webm.tool.Tools.buildRandom(2);
			return yiKaHuiService(msg, ip, orderID,tradeNo,seqNo);
		} else if ("09013".equals(tradeNo)) {// ������ͨ �Ĵ���ֵ
			String orderID = com.wlt.webm.tool.Tools.getStreamDate()
			+ com.wlt.webm.tool.Tools.getCMPayNO()
			+ com.wlt.webm.tool.Tools.buildRandom(2);
			return yiKaHuiService(msg, ip, orderID,tradeNo,seqNo);
		} else if ("09020".equals(tradeNo)) {//�ڿ�
			String orderID = "yikuai"+com.wlt.webm.tool.Tools.getNow3()+com.wlt.webm.tool.Tools.buildRandom(6);
			return yiKaHuiService(msg, ip, orderID,tradeNo,seqNo);
		} else if ("09025".equals(tradeNo)) {//����
			String orderID ="09025"+Tools.getNow3()+((int)(Math.random()*1000)+1000)+""+((char)(new Random().nextInt(26) + (int)'A'))+""+((int)(Math.random()*100)+100);
			return yiKaHuiService(msg, ip, orderID,tradeNo,seqNo);
		} else if ("09034".equals(tradeNo)) {//�ֳ�
			return yiKaHuiService(msg, ip,"",tradeNo,seqNo);
		} else if ("09036".equals(tradeNo)) {//��ʤ�ӿ�
			return yiKaHuiService(msg, ip,"",tradeNo,seqNo);
		} else if ("09037".equals(tradeNo)) {//�Ͱ��ƶ��ӿ�
			String orderID = Tools.getNow3()+((int)(Math.random()*1000)+1000)+((char)(new Random().nextInt(26) + (int)'a'))+((char)(new Random().nextInt(26) + (int)'A'));
			return yiKaHuiService(msg, ip,orderID,tradeNo,seqNo);
		} else if ("09038".equals(tradeNo)) {//�����ֵ�ӿ�
			String orderID = Tools.getNow3()+((int)(Math.random()*1000)+1000)+((char)(new Random().nextInt(26) + (int)'a'))+((char)(new Random().nextInt(26) + (int)'A'))+"zb";
			return yiKaHuiService(msg, ip,orderID,tradeNo,seqNo);
		}else {
			if (!PortalSend.getInstance().sendmsg(msg)) {
				return 1;// ������Ϣʧ��,��ֵʧ��
			}
			int k = 0;
			TradeMsg rsMsg = null;
			while (k < 180) {
				k++;
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				rsMsg = MemcacheConfig.getInstance().get(seqNo);
				if (null == rsMsg) {
					continue;
				} else {
					MemcacheConfig.getInstance().delete(seqNo);
					break;
				}
			}
			if (null == rsMsg && k >= 90) {
				return 2;// ������
			}
			String code1 = rsMsg.getRsCode();
			if ("000".equals(code1)) {
				if (interfaceID == 3 || interfaceID == 14 || interfaceID==18) {
					return Integer.parseInt(rsMsg.getContent().get("mon"));
				} else {
					return 0;// �ɹ�
				}
			} else if ("102".equals(code1)){
				return -5;//����
			}else if ("002".equals(code1)|| "045".equals(code1) || "001".equals(code1)) {
				return 1; // ʧ��
			} else if ("008".equals(code1)) {
				return 3;// ������
			}else if("101".equals(code1)){
				return 6;//�������ظ�
			} else {
				return 5;// �쳣
			}
		}
	}
	
	/**
	 *  ��ɽ�ƶ� �ӿ�
	 * @param parentID
	 * @param userno
	 * @param phonePid
	 * @param userPid
	 * @param phonetype
	 * @param phone
	 * @param seqNo
	 * @param fee
	 * @param str
	 * @param areaCode
	 * @param dbService
	 * @param login
	 * @param ip
	 * @param orderid
	 * @param flag
	 * @return
	 */
	public int MobleInter(String parentID, String userno, String phonePid,
			String userPid, String phonetype, String phone, String seqNo,
			double fee, String[] str, String areaCode, DBService dbService,
			String login, String ip, String orderid, String flag) {
		int interfaceID = 20;
		String tradeNo = "09031";
		int facctfee = 0;
		int parentfee = 0;
		String serialNo = orderid;
		
		facctfee = (int) FloatArith.mul(fee, 1 - FloatArith.div(Double.valueOf(str[0]), 100));
		
		TradeMsg msg = new TradeMsg();
		msg.setSeqNo(seqNo);
		msg.setFlag("0");// ������
		msg.setMsgFrom2("0103");
		msg.setServNo("00");
		msg.setTradeNo(tradeNo);
		Map<String, String> content = new HashMap<String, String>();
		content.put("phoneNum", phone);
		content.put("userNo", userno);
		content.put("parentID", parentID);
		content.put("isline", flag);// �Ƿ�ֱӪ��ʾ 0��ʾ��ֱӪ 1��ʾֱӪ
		content.put("userPid", userPid);
		content.put("phonePid", phonePid);
		content.put("areaCode", areaCode);
		content.put("phoneType", phonetype);
		content.put("tradeFee", (int) fee + "");
		content.put("facctFee", facctfee + "");
		content.put("parentFee", parentfee + "");
		content.put("interfaceID", interfaceID + "");
		content.put("login", login);
		content.put("time", Tools.getNow3());
		content.put("numType","01");
		content.put("termType", "0");
		content.put("serialNo", serialNo);
		msg.setContent(content);
		
		if (!PortalSend.getInstance().sendmsg(msg)) {
			return 1;// ������Ϣʧ��,��ֵʧ��
		}
		int k = 0;
		TradeMsg rsMsg = null;
		while (k < 180) {
			k++;
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			rsMsg = MemcacheConfig.getInstance().get(seqNo);
			if (null == rsMsg) {
				continue;
			} else {
				MemcacheConfig.getInstance().delete(seqNo);
				break;
			}
		}
		if (null == rsMsg && k >= 90) {
			return 2;// ������
		}
		String code1 = rsMsg.getRsCode();
		if ("000".equals(code1)) {
			return 0;// �ɹ�
		} else if ("102".equals(code1)){
			return 4;//�ʽ��˻�������������
		}else if ("002".equals(code1) || "001".equals(code1)) {
			return 1; // ʧ��
		} else if ("008".equals(code1)) {
			return 2;// ������
		}else if("101".equals(code1)){
			return 3;//�������ظ�
		} else {
			return 100;// �쳣
		}
	}

	/**
	 * ��װ��ʯ����Ϣ����business����ҵ����
	 * 
	 * @param parentID
	 *            ���ڵ�id
	 * @param userno
	 *            �û�ϵͳ���
	 * @param phonePid
	 *            �������ʡ��
	 * @param userPid
	 *            �û�����ʡ��
	 * @param phonetype
	 *            ��������
	 * @param phone
	 *            ����
	 * @param seqNo
	 *            ��ˮ��
	 * @param fee
	 *            ���׽��
	 * @param areaCode
	 *            ����
	 * @param dbService
	 *            ���ݿ����
	 * @param login
	 *            ��½�˺�
	 * @param ip
	 * @param gh
	 * @return ������ ����0 �ɹ� 1 ��ֵ��Ϣ����ʧ�� -1 ϵͳ��æ -2 �޶�Ӧ��ֵ�ӿ� 2 ������ 3ʧ��
	 */
	public Map<String, Integer> zshNationFill(String parentID, String userno,
			String phonePid, String userPid, String phonetype, String phone,
			String seqNo, double fee, String areaCode, DBService dbService,
			String login, String ip, String gh) {
		int interfaceID = -1;
		String tradeNo = "";
		String serialNo = seqNo;
		String barSeqNo = seqNo;
		Map map = new HashMap<String, Integer>();
		//һ�����ڲ������ظ�����
		synchronized (lock) {
			if(MemcacheConfig.getInstance().get1(phone+fee+"repeat")){
				Log.info(phone+"-"+fee+" һ�����ڴ��ڸý���");
				map.put("state", 10);
				map.put("barcode",null);
				return map;
			}else{
				MemcacheConfig.getInstance().add(phone+fee+"repeat", phone);
			}
		}
	
		// �㶫ʡ��ͨ���ƶ���ȫ������
		if (Integer.parseInt(phonePid) == 35
				|| Integer.parseInt(phonetype) == 0) {
			map.put("barcode", 1);
		}
		// ȫ���ƶ�
		else if (Integer.parseInt(phonePid) != 35
				&& Integer.parseInt(phonetype) == 1) {
			map.put("barcode", 2);
		}
		// ȫ����ͨ
		else if (Integer.parseInt(phonePid) != 35
				&& Integer.parseInt(phonetype) == 2) {
			map.put("barcode", 3);
		}
		// QQ��ֵ
		else {
			map.put("barcode", 4);
		}
		try {
//			if(Tools.isJC(phone)){//���� ����������ӿ�
//				interfaceID=12;
//			}else{
				 if (Integer.parseInt(phonePid) == 35
						&& Integer.parseInt(phonetype) == 0) {
					interfaceID = 9;
				} else {
					interfaceID = getPhoneInfo(phonePid, phonetype, dbService,fee);
				}
//			}
		} catch (SQLException e) {
			e.printStackTrace();
			map.put("barcode", null);
			map.put("state", -1);
			return map;
		}
		if (interfaceID == -1) {
			map.put("barcode", null);
			map.put("state", -1);
			return map;
		}

		switch (interfaceID) {
		case 3:
			tradeNo = "09001"; // �㶫�ƶ�
			serialNo = "XY0315012880537" + Tools.getNow3().substring(2)
					+ Tools.buildRandom(2) + Tools.buildRandom(3);
			break;
		case 4:
			tradeNo = "01013";// �㶫��ͨ
			serialNo = "XY0418682022602" + Tools.getNow3().substring(2)
					+ Tools.buildRandom(2) + Tools.buildRandom(3); // �㶫��ͨ
			break;
		case 5:
			tradeNo = "01001"; // �㶫����
			break;
		case 6:
			tradeNo = "09013";// ������ͨ �Ĵ�
			break;
		case 1:
			tradeNo = "01000";// ȫ��
			break;
		case 7:
			tradeNo="09012";//����
			serialNo = "wh09012" + Tools.getNow3().substring(6)
			+ Tools.buildRandom(2) + Tools.buildRandom(3); //�����ƶ�
			break;
		case 8:
			tradeNo = "09014";// ������Ѹ
			break;
//		case 9://ѶԴ����
//			tradeNo="09015";
//			serialNo = "wh09015" + Tools.getNow3().substring(6)
//			+ Tools.buildRandom(2) + Tools.buildRandom(3); 
//			break;
		case 11:
			tradeNo="09016";//����
			serialNo = "zh09016" + Tools.getNow3().substring(6)
			+ Tools.buildRandom(2) + Tools.buildRandom(3); //�㽭�����ƶ�
			break;
		case 12:
			tradeNo="09018";//�װ���
//			serialNo = "yb09018" + Tools.getNow3().substring(6)
//			+ Tools.buildRandom(2) + Tools.buildRandom(3); //�㽭�����ƶ�
			break;
		case 13:
			tradeNo = "09020";// �ڿ�
			break;
		case 14://��ͨ tcp  �ƶ�
			tradeNo="09021";
			serialNo = ((int)(Math.random()*1000)+1000)+""+((char)(new Random().nextInt(26) + (int)'A'))+""+((int)(Math.random()*100)+100);
			break;
		case 15://ʡ��ͨ  tcp 
			tradeNo="09022";
			serialNo = Tools.getNow3()+((char)(new Random().nextInt(26) + (int)'A'))+((int)(Math.random()*10000)+10000);
			break;
		case 17://����ӿ�  http
			tradeNo = "09025";
			break;
//		case 18://�����ƶ� tcp
//			tradeNo="09026";
//			serialNo = Tools.getNow3()+((char)(new Random().nextInt(26) + (int)'A'))+((int)(Math.random()*10000)+10000);
//			break;
		default:
			Log.info("zsh��ֵ����:"+phone+",δ��ƥ�䵽�ӿ�,,,,");
			map.put("state", 1);//ʧ��
		}
		TradeMsg msg = new TradeMsg();
		msg.setSeqNo(seqNo);
		msg.setFlag("0");// ������
		msg.setMsgFrom2("0103");
		msg.setServNo("00");
		msg.setTradeNo(tradeNo);
		Map<String, String> content = new HashMap<String, String>();
		content.put("phoneNum", phone);
		content.put("userNo", userno);
		content.put("parentID", parentID);
		content.put("isline", "1");
		content.put("userPid", userPid);
		content.put("phonePid", phonePid);
		content.put("areaCode", areaCode);
		content.put("phoneType", phonetype);
		content.put("tradeFee", (int) fee + "");
		content.put("facctFee",(int)fee + "");
		content.put("parentFee", 0 + "");
		content.put("interfaceID", interfaceID + "");
		content.put("login", login);
		content.put("time", Tools.getNow3());
		if (null != gh && "OF".equals(gh)) {
			content.put("gh", gh);
		}
		if("09022".equals(tradeNo)){
			content.put("numType","1");//ʡ��ͨ
		}
		Log.info("�������趩����:" + seqNo);
		if(tradeNo.equals("09018")){
			content.put("serialNo", seqNo);
		}else{
			content.put("serialNo", serialNo);
		}
		content.put("termType", "0");
		msg.setContent(content);
		if ("01000".equals(tradeNo)) {// һ���ݳ�ֵ
			String orderID = com.wlt.webm.tool.Tools.getStreamDate()
			+ com.wlt.webm.tool.Tools.getCMPayNO()
			+ com.wlt.webm.tool.Tools.buildRandom(2);
			map.put("state", yiKaHuiService(msg, ip, orderID,tradeNo,seqNo));
			barSeqNo = orderID;
			saveBarcodeAndOrder(Integer.parseInt(map.get("barcode") + ""),
					barSeqNo, dbService); // �������뼰����
			return map;
		}else if ("09014".equals(tradeNo)) {// ��Ѹ��ֵ
			String orderID = com.wlt.webm.tool.Tools.getStreamDate()
			+ com.wlt.webm.tool.Tools.buildRandom(2)
			+com.wlt.webm.tool.Tools.buildRandom(2);
			map.put("state", yiKaHuiService(msg, ip, orderID,tradeNo,seqNo));
			barSeqNo = orderID;
			saveBarcodeAndOrder(Integer.parseInt(map.get("barcode") + ""),
					barSeqNo, dbService); // �������뼰����
			return map;
		} else if ("09013".equals(tradeNo)) {// ������ͨ �Ĵ���ֵ
			String orderID = com.wlt.webm.tool.Tools.getStreamDate()
			+ com.wlt.webm.tool.Tools.getCMPayNO()
			+ com.wlt.webm.tool.Tools.buildRandom(2);
			map.put("state", yiKaHuiService(msg, ip, orderID,tradeNo,seqNo));
			barSeqNo = orderID;
			saveBarcodeAndOrder(Integer.parseInt(map.get("barcode") + ""),
					barSeqNo, dbService); // �������뼰����
			return map;
		} else if ("09020".equals(tradeNo)) {// �ڿ� 
			String orderID = com.wlt.webm.tool.Tools.getStreamDate()
			+ com.wlt.webm.tool.Tools.getCMPayNO()
			+ com.wlt.webm.tool.Tools.buildRandom(2);
			map.put("state", yiKaHuiService(msg, ip, orderID,tradeNo,seqNo));
			barSeqNo = orderID;
			saveBarcodeAndOrder(Integer.parseInt(map.get("barcode") + ""),
					barSeqNo, dbService); // �������뼰����
			return map;
		} else if ("09025".equals(tradeNo)) {// ���� ȫ������
			String orderID = "09025"+Tools.getNow3()+((int)(Math.random()*1000)+1000)+""+((char)(new Random().nextInt(26) + (int)'A'))+""+((int)(Math.random()*100)+100);
			map.put("state", yiKaHuiService(msg, ip, orderID,tradeNo,seqNo));
			barSeqNo = orderID;
			saveBarcodeAndOrder(Integer.parseInt(map.get("barcode") + ""),
					barSeqNo, dbService); // �������뼰����
			return map;
		}else {
			barSeqNo = seqNo;
			saveBarcodeAndOrder(Integer.parseInt(map.get("barcode") + ""),
					barSeqNo, dbService); // �������뼰����
			if (!PortalSend.getInstance().sendmsg(msg)) {
				map.put("state", 1);// ������Ϣʧ��,��ֵʧ��
				return map;
			}
			int k = 0;
			TradeMsg rsMsg = null;
			while (k < 180) {
				k++;
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				rsMsg = MemcacheConfig.getInstance().get(seqNo);
				if (null == rsMsg) {
					continue;
				} else {
					MemcacheConfig.getInstance().delete(seqNo);
					break;
				}
			}
			if (null == rsMsg && k >= 90) {
				map.put("state", 2);// ������
				return map;
			}
			String code1 = rsMsg.getRsCode();
			if ("101".equals(code1) || "102".equals(code1)
					|| "103".equals(code1) || "002".equals(code1)
					|| "045".equals(code1)) {
				// ɾ����������
				delBarcodeAndOrder(barSeqNo, dbService);
			}
			if ("000".equals(code1)) {
				if (interfaceID == 3) {
					map.put("state", Integer.parseInt(rsMsg.getContent().get(
							"mon")));// ������
				} else {
					map.put("state", 0);// �ɹ�
				}
			} else if ("102".equals(code1) || "002".equals(code1)
					|| "045".equals(code1) || "001".equals(code1)) {
				map.put("state", 1);
				 // ʧ��
			} else if ("008".equals(code1)) {
				map.put("state", 3);// ������
			} else {
				map.put("state", 5);// �쳣
			}
			return map;
		}
	}

	/**
	 * ɾ��������
	 * 
	 * @param barSeqNo
	 * @param dbService
	 */
	public  void delBarcodeAndOrder(String barSeqNo, DBService db) {
		try {
			String sql = "delete from wht_barcode_order where orderId='"
					+ barSeqNo + "'";
			db.update(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			db.close();
		}
	}

	/**
	 * ���涩����ż�������
	 * 
	 * @param object
	 * @param tradeNo
	 * @param db
	 */
	public void saveBarcodeAndOrder(Integer barcode, String tradeNo,
			DBService db) {
		try {
			String sql = "insert into wht_barcode_order(barcodeId,orderId) values("
					+ barcode + ",'" + tradeNo + "')";
			db.update(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * ��ȡ��ֵҵ��Ӷ�����
	 * 
	 * @param db
	 * @param code
	 * @param flag
	 * @param userno
	 * @param parentid
	 * @return
	 */
	public String[] getZZEmploy(DBService db, String code, String flag,
			String userno, int parentid) {
		String sql = null;
		if ("1".equals(flag)) {// ֱӪ
			sql = "select value from sys_employ3 where code='" + code + "'"
					+ " and flag=1";
		} else {// ��ֱӪ
			sql = "select a.value,b.value from sys_employ3 a,wht_agentAnduser b where a.id=b.employ3id and a.code='"
					+ code
					+ "' and a.flag=0 and b.flag=1 and b.userno='"
					+ userno + "' and b.parentid=" + parentid;
		}
		try {
			return db.getStringArray(sql);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * �ڲ�ҵ����
	 * 
	 * @param db
	 * @param code
	 * @param flag
	 * @param userno
	 * @param parentid
	 * @return
	 */
	public boolean innerDeal(DBService db, Object[] orderObj, Object[] acctObj,
			Object[] acctObj1, String flag, String userFacct,
			String parentFacct, String time, int tradeFee, int parentFee) {
		try {
			db.setAutoCommit(false);
			db.update("update wht_childfacct set accountleft=accountleft-"
					+ tradeFee + " where childfacct='" + userFacct + "01'");
			String tableName = "wht_acctbill_" + time.substring(2, 6);
			db.logData(20, orderObj, "wht_orderform_" + time.substring(2, 6));
			db.logData(15, acctObj, tableName);
			if (!"1".equals(flag)) {
				db.update("update wht_childfacct set accountleft=accountleft+"
						+ parentFee + " where childfacct='" + parentFacct
						+ "02'");
				db.logData(15, acctObj1, tableName);
				db.commit();
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			db.rollback();
			return false;
		} finally {
			try {
				db.setAutoCommit(true);
			} catch (SQLException e) {
				e.printStackTrace();
				return false;
			}
		}
		return true;
	}

	/**
	 * �ڲ�ҵ�����д���
	 * 
	 * @param db
	 * @param tableName
	 * @param check
	 * @param seqNo
	 * @param userno
	 */
	public void innerTimeOutDeal(DBService db, String tableName, String seqNo,
			String userno) {
		try {
			db.update("update " + tableName
					+ " set state=4 where tradeserial='" + seqNo
					+ "' and userno='" + userno + "'");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * �ڲ�ҵ��ɹ�����
	 * 
	 * @param db
	 * @param tableName
	 * @param check
	 * @param seqNo
	 * @param userno
	 */
	public void innerSuccessDeal(DBService db, String tableName, String check,
			String seqNo, String userno) {
		try {
			db.update("update " + tableName + " set state=0,writecheck='"
					+ check + "' where tradeserial='" + seqNo
					+ "' and userno='" + userno + "'");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * �ڲ�ҵ��ʧ�ܴ���
	 * 
	 * @param db
	 * @param orderTableName
	 * @param acctTbaleName
	 * @param check
	 * @param seqNo
	 * @param userno
	 * @param acct1
	 * @param acct2
	 * @param facctFee
	 * @param parentFee
	 * @param userfacct
	 * @param parentfacct
	 * @param flag
	 */
	public void innerFailDeal(DBService db, String orderTableName,
			String acctTbaleName, String check, String seqNo, String userno,
			String acct1, String acct2, int facctFee, int parentFee,
			String userfacct, String parentfacct, String flag) {
		try {
			db.setAutoCommit(false);
			/**
			 * ��֧δ�ֿ��� db.update("update " + orderTableName +
			 * " set state=1,writecheck='" + check + "' where tradeserial='" +
			 * seqNo + "' and userno='" + userno + "'"); db.update("update " +
			 * acctTbaleName + " set state=1 where dealserial='" + acct1 +
			 * "' and tradeserial='" + seqNo + "'");
			 * db.update("update wht_childfacct set accountleft=accountleft+" +
			 * facctFee + " where childfacct='" + userfacct + "01'"); if
			 * (!"1".equals(flag)) { db.update("update " + acctTbaleName +
			 * " set state=1 where dealserial='" + acct2 + "' and tradeserial='"
			 * + seqNo + "'");
			 * db.update("update wht_childfacct set accountleft=accountleft-" +
			 * parentFee + " where childfacct='" + parentfacct + "02'"); }
			 */
			// ��֧�ֿ���
			String newtime = Tools.getNow3();
			String tableName = "wht_acctbill_" + newtime.substring(2, 6);
			String sql1 = "select tradeobject,fee  from " + orderTableName
					+ "  where tradeserial='" + seqNo + "' and userno='"
					+ userno + "'";
			String[] str1 = db.getStringArray(sql1);
			String userleft0 = "select accountleft from wht_childfacct where childfacct='"
					+ userfacct + "01'";
			int userleft = db.getInt(userleft0);
			String fialAcct1 = Tools
					.getAccountSerial(newtime, "QBDF" + str1[0]);

			db.update("update " + orderTableName + " set state=1,writecheck='"
					+ check + "' where tradeserial='" + seqNo
					+ "' and userno='" + userno + "'");

			Object[] acctObj1 = { null, userfacct + "01", fialAcct1, str1[0],
					newtime, Integer.parseInt(str1[1]), facctFee, 6,
					"Q�ҳ�ֵ(ʧ���˷�)", 0, newtime, userleft + facctFee, seqNo,
					userfacct + "01", 2 };
			db.logData(15, acctObj1, tableName);
			db.update("update wht_childfacct set accountleft=accountleft+"
					+ facctFee + " where childfacct='" + userfacct + "01'");
			if (!"1".equals(flag)) {
				String fialAcct2 = Tools.getAccountSerial(newtime, "QBSF"
						+ str1[0]);
				String userleft2 = "select accountleft from wht_childfacct where childfacct='"
						+ parentfacct + "02'";
				int userleft3 = db.getInt(userleft2);

				Object[] acctObj2 = { null, parentfacct + "02", fialAcct2,
						str1[0], newtime, parentFee, parentFee, 15,
						"Q�ҳ�ֵ(���׳���)", 0, newtime, userleft3 - parentFee, seqNo,
						parentfacct + "02", 1 };
				db.logData(15, acctObj2, tableName);
				db.update("update wht_childfacct set accountleft=accountleft-"
						+ parentFee + " where childfacct='" + parentfacct
						+ "02'");
			}

			//
			db.commit();
		} catch (SQLException e) {
			db.rollback();
			e.printStackTrace();
		}
	}

	/**
	 * �����û�id��ѯ �˺�
	 * 
	 * @param userid
	 * @return �û�ƽ̨�˻�
	 */
	public String getFacctByUserID(DBService db, int userid) {
		String sql = "select fundacct from wht_facct where user_no=(select user_no from sys_user where user_id="
				+ userid + ")";
		try {
			return db.getString(sql);
		} catch (Exception ex) {
			Log.error("�����û�ϵͳ��Ż�ȡ�˻�ʧ��:", ex);
			return null;
		}
	}

	/**
	 * ȫ�����Ź̻���ֵ
	 * @param reveMess
	 * @param ip
	 * @param orderID
	 * @param tradeNo
	 * @return
	 */
	public int qgdxghcz(TradeMsg reveMess, String ip, String orderID,String tradeNo) {
		int n = 0;
		String seqNo = null;
		String areaCode = null;
		String phoneType = null;
		String phone = null;
		String time = null;
		String facctFee = null;
		String parentFee = null;
		String userNo = null;
		String parentID = null;
		String userPid = null;
		String phonePid = null;
		String serialNo = null;
		String interfaceID = null;
		String tradeFee = null;
		String termType = null;
		String login = null;
		String tradetype = null;
		int acctType;
		Log.info("ȫ�����Ź̻���ֵ...");
		seqNo = reveMess.getSeqNo();
		Map content = reveMess.getContent();
		areaCode = (String) content.get("areaCode");
		phoneType = (String) content.get("phoneType");
		phone = (String) content.get("phoneNum");
		time = (String) content.get("time");
		facctFee = (String) content.get("facctFee");
		parentFee = (String) content.get("parentFee");
		userNo = (String) content.get("userNo");
		parentID = (String) content.get("parentID");
		String isline = (String) content.get("isline");
		userPid = (String) content.get("userPid");
		phonePid = (String) content.get("phonePid");
		serialNo = (String) content.get("serialNo");
		interfaceID = (String) content.get("interfaceID");
		tradeFee = (String) content.get("tradeFee");
		termType = (String) content.get("termType");
		login = (String) content.get("login");
		
		String writeoff = serialNo + "#" + userNo + "#" + phone + "#"
				+ tradeFee;
		String checkAccount = seqNo + "#" + serialNo + "#" + userNo + "#"
				+ phone + "#" + tradeFee;
		// �ж����˻��Ϸ�
		com.wlt.webm.scpdb.DBService db = null;
		try {
			db = new com.wlt.webm.scpdb.DBService();
			FundAcctDao fd = new FundAcctDao(db);
			if (fd.checkSeq(orderID)) {
				return 1;// ��ˮ���ظ�
			}
			if (phoneType.equals("0")) {
				tradetype = "0001";
				acctType = 3;
			} else if (phoneType.equals("1")) {
				tradetype = "0002";
				acctType = 4;
			} else {
				tradetype = "0003";
				acctType = 5;
			}
			String userFacct = fd.getFacctByUserno(userNo.trim()).trim();
			String parentFacct = "";
			int parentLeft = 0;
			if (!"1".equals(isline)) {
				parentFacct = fd.getFacctByUserID(Integer.parseInt(parentID));
				parentLeft = fd.getChildFacctsLeft(parentFacct, "02");
			}
			String code = fd.checkFacct(userFacct, userFacct + "01", Double.parseDouble(facctFee));
			if (RespCode.RC_FACCT_NOLEFT.equals(code)
					|| RespCode.RC_FACCT_DROP.equals(code)) {
				return -5;// �ʽ��˻�������������
			}
			db.setAutoCommit(false);
			Object[] orderObj = { null, areaCode, orderID, phone, interfaceID,
					tradetype, Integer.parseInt(tradeFee),
					Integer.parseInt(facctFee), userFacct + "01", time, time,
					4, writeoff, checkAccount, termType, userNo, login,
					phoneType, phonePid, null };// 20
			String acct1 = Tools.getAccountSerial(time, userNo);
			Object[] acctObj = { null, userFacct + "01", acct1, phone, time,
					Integer.parseInt(tradeFee), Integer.parseInt(facctFee),
					acctType, "ȫ�����Ź̻���ֵ", 0, time,
					Integer.parseInt(code) - Integer.parseInt(facctFee),
					orderID, userFacct + "01", 1 };
			db.update("update wht_childfacct set accountleft=accountleft-"
					+ Integer.parseInt(facctFee) + " where childfacct='"
					+ userFacct + "01'");
			String tableName = "wht_acctbill_" + time.substring(2, 6);
			db.logData(20, orderObj, "wht_orderform_" + time.substring(2, 6));
			db.logData(15, acctObj, tableName);
			String acct2 = null;
			if (!"1".equals(isline)) {
				db.update("update wht_childfacct set accountleft=accountleft+"
						+ Integer.parseInt(parentFee) + " where childfacct='"
						+ parentFacct + "02'");
				acct2 = Tools.getAccountSerial(time, userNo);
				Object[] acctObj1 = { null, parentFacct + "02", acct2, phone,
						time, Integer.parseInt(parentFee),
						Integer.parseInt(parentFee), 15, "�¼����׷�Ӷ", 0, time,
						parentLeft + Integer.parseInt(parentFee), orderID,
						parentFacct + "02", 2 };
				db.logData(15, acctObj1, tableName);
			}
			db.commit();
			String rs="";
			// ���ó�ֵ

//			rs =getRandomDigit(); 
			rs=scdx.sjytczFile(orderID, time, tradeFee, phone.replace("-",""),content.get("gh")+"".trim(),phoneType,"0|")+"";//+phone.split("-")[0]
			
			if(!"0".equals(rs)&&!"01000".equals(tradeNo)&&null==content.get("gh"))
			{
				returnDeal(time,"-1", orderID, userNo, acct1, userFacct, isline,
						acct2, parentFacct, facctFee, parentFee, acctType);
			}else if("01000".equals(tradeNo)||null!=content.get("gh")){
				returnDeal(time,rs, orderID, userNo, acct1, userFacct, isline,
						acct2, parentFacct, facctFee, parentFee, acctType);
			}

			if (rs.equals("0")) {
				return 0;
			} else if (rs.equals("-1")) {
				return -1;
			} else {
				return -2;
			}
		} catch (Exception e) {
			db.rollback();
			e.printStackTrace();
			return 3;// ϵͳ��æ
		} finally {
			if (null != db) {
				db.close();
			}
		}
	}
	
	/**
	 * һ�����ڲ�ҵ����
	 * 
	 * @param msg
	 * @return
	 */
	public int yiKaHuiService(TradeMsg reveMess, String ip, String orderID,String tradeNo,String seqNo1) {
		int n = 0;
		String seqNo = null;
		String areaCode = null;
		String phoneType = null;
		String phone = null;
		String time = null;
		String facctFee = null;
		String parentFee = null;
		String userNo = null;
		String parentID = null;
		String userPid = null;
		String phonePid = null;
		String serialNo = null;
		String interfaceID = null;
		String tradeFee = null;
		String termType = null;
		String login = null;
		String tradetype = null;
		String productId=null;
		String realPrice=null;
		String aCTIVITYID=null;
		String miaoshu="ȫ�����ѳ�ֵ";
		int acctType;
		Log.info("��ʼ����һ����ҵ��...");
		seqNo = reveMess.getSeqNo();
		Map content = reveMess.getContent();
		if(null==content){
			return -1;
		}
		areaCode = (String) content.get("areaCode");
		phoneType = (String) content.get("phoneType");
		phone = (String) content.get("phoneNum");
		time = (String) content.get("time");
		facctFee = (String) content.get("facctFee");
		parentFee = (String) content.get("parentFee");
		userNo = (String) content.get("userNo");
		parentID = (String) content.get("parentID");
		String isline = (String) content.get("isline");
		userPid = (String) content.get("userPid");
		phonePid = (String) content.get("phonePid");
		serialNo = (String) content.get("serialNo");
		interfaceID = (String) content.get("interfaceID");
		tradeFee = (String) content.get("tradeFee");
		termType = (String) content.get("termType");
		login = (String) content.get("login");
		
		String writeoff =orderID;
		String checkAccount = seqNo + "#" + serialNo + "#" + userNo + "#"
				+ phone + "#" + tradeFee;
		// �ж����˻��Ϸ�
		com.wlt.webm.scpdb.DBService db = null;
		try {
			db = new com.wlt.webm.scpdb.DBService();
			FundAcctDao fd = new FundAcctDao(db);
			if (fd.checkSeq(seqNo1)) {
				return 6;// ��ˮ���ظ�
			}
			if (phoneType.equals("0")) {
				tradetype = "0001";
				acctType = 3;
			} else if (phoneType.equals("1")) {
				tradetype = "0002";
				acctType = 4;
			} else {
				tradetype = "0003";
				acctType = 5;
			}
			if("10001".equals(tradeNo)){//���������ֵ
				tradetype = "0009";
				acctType = 27;
				productId=(String) content.get("productId");
				realPrice=(String) content.get("realPrice");
				aCTIVITYID=(String) content.get("aCTIVITYID");
				String gp=(String) content.get("groups"); 
				String name=(String) content.get("name"); 
				writeoff=name.indexOf("mb")>0?name:name+"mb";
				miaoshu="��ͨ����";
				String cm="0";
//				int num=db.getInt("SELECT PRICE_NUM FROM wht_flowproduct where product_id='"+productId+"'");
//				if(!realPrice.equals(num*100+"")){
//					return 8;//������
//				}
				tradeFee=db.getString("select price*1000 from wht_flowprice where type=2 and name='"+name+"'");
				if(null==tradeFee){
					return 8;//����������
				}
				if("1".equals(isline)){//1ֱӪ,�ӿ���
					float value=db.getFloat("select value from sys_tpemploy_Flow where type=2 and groups='"+gp+"' and cm_addr="+cm);
					facctFee=FloatArith.mul(Double.parseDouble(tradeFee),1-FloatArith.div(value, 100))+"";
				}else{//�����
					facctFee=""+Double.parseDouble(tradeFee)*Double.parseDouble(DBConfig.getInstance().getLiantong_flow());
				}
			}
			if("10005".equals(tradeNo)){//����������ֵ
				tradetype = "0009";
				acctType = 27;
				productId=(String) content.get("name");
				realPrice=(String) content.get("realPrice");
				aCTIVITYID=(String) content.get("aCTIVITYID");
				String gp=(String) content.get("groups");
				String name=(String) content.get("name"); 
				writeoff=name.indexOf("mb")>0?name:name+"mb";
				miaoshu="������ͨ����";
				String cm="0";
				tradeFee=db.getString("select price*1000 from wht_flowprice where type=2 and name='"+name+"'");
				if(null==tradeFee){
					return 8;//����������
				}
				if("1".equals(isline)){//1ֱӪ,�ӿ���
					float value=db.getFloat("select value from sys_tpemploy_Flow where type=2 and groups='"+gp+"' and cm_addr="+cm);
					facctFee=FloatArith.mul(Double.parseDouble(tradeFee),1-FloatArith.div(value, 100))+"";
				}else{//�����
					facctFee=""+Double.parseDouble(tradeFee)*Double.parseDouble(DBConfig.getInstance().getLiantong_flow());
				}
			}
			//˼������ || ����ͨ���� || �������� || �������� ||���� ||����
			if("10006".equals(tradeNo) || "10008".equals(tradeNo) || "10009".equals(tradeNo) || "10010".equals(tradeNo)||"10013".equals(tradeNo)|| "10014".equals(tradeNo)|| "10015".equals(tradeNo)){
				String cm="0";//0 ȫ��   1 ʡ��
				//phoneType 0����  1�ƶ� 2��ͨ
				switch (Integer.parseInt(phoneType)) {
				case 0://����
					tradetype = "0011";
					acctType = 29;
					miaoshu="��������";
					break;
				case 1://�ƶ�
					cm=phonePid;
					tradetype = "0010";
					acctType = 28;
					miaoshu="�ƶ�����";
					break;
				case 2://��ͨ
					tradetype = "0009";
					acctType = 27;
					miaoshu="��ͨ����";
					break;
				default:
					break;
				}
				String gp=(String) content.get("groups"); //�ӿ���ħ����
				String name=(String) content.get("name"); 
				writeoff=name.indexOf("mb")>0?name:name+"mb";
				tradeFee=db.getString("select price*1000 from wht_flowprice where type="+phoneType+" and name='"+name+"'");
				if("1".equals(isline)){//1ֱӪ,�ӿ���
					float value=db.getFloat("select value from sys_tpemploy_Flow where type="+phoneType+" and groups='"+gp+"' and cm_addr="+cm);
					facctFee=FloatArith.mul(Double.parseDouble(tradeFee),1-FloatArith.div(value, 100))+"";
				}else{//�����
					facctFee=""+Double.parseDouble(tradeFee)*Double.parseDouble(DBConfig.getInstance().getLiantong_flow());
				}
			}
			if("10007".equals(tradeNo)){//�����ƶ�����
				tradetype = "0010";
				acctType = 28;
				productId=(String) content.get("name");
				realPrice=(String) content.get("realPrice");
				aCTIVITYID=(String) content.get("aCTIVITYID");
				String gp=(String) content.get("groups");
				String name=(String) content.get("name"); 
				writeoff=name.indexOf("mb")>0?name:name+"mb";
				miaoshu="�����ƶ�����";
				String cm=phonePid;
				tradeFee=db.getString("select price*1000 from wht_flowprice where type=1 and name='"+name+"'");
				if(null==tradeFee){
					return 8;//����������
				}
				if("1".equals(isline)){//1ֱӪ,�ӿ���
					float value=db.getFloat("select value from sys_tpemploy_Flow where type=1 and groups='"+gp+"' and cm_addr="+cm);
					facctFee=FloatArith.mul(Double.parseDouble(tradeFee),1-FloatArith.div(value, 100))+"";
				}else{//�����
					facctFee=""+Double.parseDouble(tradeFee)*Double.parseDouble(DBConfig.getInstance().getLiantong_flow());
				}
			}
			if("10011".equals(tradeNo)){//����������ֵ�ӿ�
				tradetype = "0010";
				acctType = 28;
				productId=(String) content.get("name");
				realPrice=(String) content.get("realPrice");
				aCTIVITYID=(String) content.get("aCTIVITYID");
				String gp=(String) content.get("groups");
				String name=(String) content.get("name"); 
				writeoff=name.indexOf("mb")>0?name:name+"mb";
				miaoshu="�����ƶ�����";
				String cm=phonePid;
				tradeFee=db.getString("select price*1000 from wht_flowprice where type=1 and name='"+name+"'");
				if(null==tradeFee){
					return 8;//����������
				}
				if("1".equals(isline)){//1ֱӪ,�ӿ���
					float value=db.getFloat("select value from sys_tpemploy_Flow where type=1 and groups='"+gp+"' and cm_addr="+cm);
					facctFee=FloatArith.mul(Double.parseDouble(tradeFee),1-FloatArith.div(value, 100))+"";
				}else{//�����
					facctFee=""+Double.parseDouble(tradeFee)*Double.parseDouble(DBConfig.getInstance().getLiantong_flow());
				}
			}
			if("10012".equals(tradeNo)){//���ɳ��ƶ�������ֵ
				checkAccount=GDFreeTrafficCharge.getTransIDO();//��ˮ��
				tradetype = "0010";
				acctType = 28;
				productId=(String) content.get("name");
				realPrice=(String) content.get("realPrice");
				aCTIVITYID=(String) content.get("aCTIVITYID");
				String gp=(String) content.get("groups");
				String name=(String) content.get("name"); 
				writeoff=name.indexOf("mb")>0?name:name+"mb";
				miaoshu="�����ƶ�����";
				String cm=phonePid;
				tradeFee=db.getString("select price*1000 from wht_flowprice where type=1 and name='"+name+"'");
				if(null==tradeFee){
					return 8;//����������
				}
				if("1".equals(isline)){//1ֱӪ,�ӿ���
					float value=db.getFloat("select value from sys_tpemploy_Flow where type=1 and groups='"+gp+"' and cm_addr="+cm);
					facctFee=FloatArith.mul(Double.parseDouble(tradeFee),1-FloatArith.div(value, 100))+"";
				}else{//�����
					facctFee=""+Double.parseDouble(tradeFee)*Double.parseDouble(DBConfig.getInstance().getLiantong_flow());
				}
			}
			/**
			if("10013".equals(tradeNo)){// �㶫������
				tradetype = "0009";
				acctType = 27;
				productId=(String) content.get("name");
				realPrice=(String) content.get("realPrice");
				aCTIVITYID=(String) content.get("aCTIVITYID");
				String gp=(String) content.get("groups");
				String name=(String) content.get("name"); 
				writeoff=name.indexOf("mb")>0?name:name+"mb";
				miaoshu="�㶫������";
				String cm="0";
				tradeFee=db.getString("select price*1000 from wht_flowprice where type=2 and name='"+name+"'");
				if(null==tradeFee){
					return 8;//����������
				}
				if("1".equals(isline)){//1ֱӪ,�ӿ���
					float value=db.getFloat("select value from sys_tpemploy_Flow where type=2 and groups='"+gp+"' and cm_addr="+cm);
					facctFee=FloatArith.mul(Double.parseDouble(tradeFee),1-FloatArith.div(value, 100))+"";
				}else{//�����
					facctFee=""+Double.parseDouble(tradeFee)*Double.parseDouble(DBConfig.getInstance().getLiantong_flow());
				}
			}*/
			String userFacct = fd.getFacctByUserno(userNo.trim()).trim();
			String parentFacct = "";
			int parentLeft = 0;
			if (!"1".equals(isline)&&!tradetype.equals("0009")&&!tradetype.equals("0011")&&!tradetype.equals("0010")) {
				parentFacct = fd.getFacctByUserID(Integer.parseInt(parentID));
				parentLeft = fd.getChildFacctsLeft(parentFacct, "02");
			}
			String code = fd.checkFacct(userFacct, userFacct + "01", Double.parseDouble(facctFee));
			if (RespCode.RC_FACCT_NOLEFT.equals(code)
					|| RespCode.RC_FACCT_DROP.equals(code)) {
				return -5;// �ʽ��˻�������������
			}
			db.setAutoCommit(false);
			Object[] orderObj = { null, areaCode, seqNo1, phone, interfaceID,
					tradetype, Double.parseDouble(tradeFee),
					Double.parseDouble(facctFee), userFacct + "01", time, time,
					4, writeoff, checkAccount, termType, userNo, login,
					phoneType, phonePid, null };// 20
			String acct1 = Tools.getAccountSerial(time, userNo);
			Object[] acctObj = { null, userFacct + "01", acct1, phone, time,
					Double.parseDouble(tradeFee), Double.parseDouble(facctFee),
					acctType, miaoshu, 0, time,
					Double.parseDouble(code) - Double.parseDouble(facctFee),
					seqNo1, userFacct + "01", 1 };
			db.update("update wht_childfacct set accountleft=accountleft-"
					+ Double.parseDouble(facctFee) + " where childfacct='"
					+ userFacct + "01'");
			String tableName = "wht_acctbill_" + time.substring(2, 6);
			db.logData(20, orderObj, "wht_orderform_" + time.substring(2, 6));
			db.logData(15, acctObj, tableName);
			String acct2 = null;
			if (!"1".equals(isline)&&!tradetype.equals("0009")&&!tradetype.equals("0010")&&!tradetype.equals("0011")) {
				db.update("update wht_childfacct set accountleft=accountleft+"
						+ Double.parseDouble(parentFee) + " where childfacct='"
						+ parentFacct + "02'");
				acct2 = Tools.getAccountSerial(time, userNo);
				Object[] acctObj1 = { null, parentFacct + "02", acct2, phone,
						time, Double.parseDouble(parentFee),
						Double.parseDouble(parentFee), 15, "�¼����׷�Ӷ", 0, time,
						parentLeft + Double.parseDouble(parentFee), seqNo1,
						parentFacct + "02", 2 };
				db.logData(15, acctObj1, tableName);
			}
			db.commit();
			db.setAutoCommit(true);
			String rs="";
			// ���ó�ֵ
			if ("01000".equals(tradeNo)) {// һ���ݳ�ֵ
//				rs =getRandomDigit(); 
				rs=yikaHuiFill(orderID, time, tradeFee, phone, ip);
			}else if ("09014".equals(tradeNo)) {// ��Ѹ��ֵ
//				rs =getRandomDigit(); 
				YxBean yb=new YxBean();
				rs=yb.yxFill(phone, orderID, Integer.parseInt(tradeFee)/1000+"", getYXPhoneInfo(phone),content.get("gh")+"".trim())+"";
			}else if ("09013".equals(tradeNo)) {// ������ͨ �Ĵ���ֵ
//				rs =getRandomDigit();
				rs=scdx.sjytczFile(orderID, time, tradeFee, phone,content.get("gh")+"".trim(),phoneType,"2|")+"";
			}else if ("09020".equals(tradeNo)){//�ڿ�
//				rs =getRandomDigit();
				rs=NineteenRecharge.getOrderState(orderID,phone,(Integer.parseInt(tradeFee)/1000)+"".trim());
			}else if ("09025".equals(tradeNo)){//����
//				rs =getRandomDigit();
				rs=JingFengInter.RechargeFile(phone,tradeFee,orderID,phoneType,content.get("gh")+"".trim())+"".trim();
			}else if ("10001".equals(tradeNo)){//��ͨ������ֵ
//				rs =getRandomDigit();
				rs=HttpFillOP.fillProduct(orderID, productId, phone, Long.parseLong(realPrice), aCTIVITYID)+"";
			}else if("10005".equals(tradeNo)){//������ͨ����
//				rs =getRandomDigit();
				String sa=Flow.BeiJin_Flow(phone,productId);
				String[] strings=sa.split("#");
				if("0".equals(strings[0])){
					rs="0";
					//����Ӧ�� ������ ��¼���
					db.update("update wht_orderform_" + time.substring(2, 6)+" set writeoff='"+strings[1]+"' where tradeserial='"+seqNo1+"'");
				}else{
					rs=strings[0];
				}
			}else if("09034".equals(tradeNo)){//�ֳ��ƶ�
//				rs =getRandomDigit();
				rs=MobileRecharge.Recharge(seqNo1, phone, (Integer.parseInt(tradeFee)/1000)+"".trim())+"";
			}else if("10006".equals(tradeNo)){//˼��������ֵ  �ص�����
//				rs =getRandomDigit();
				rs=Flow_SiKong.Recharge(seqNo1, phone,content.get("name")+"M");
			}else if("09036".equals(tradeNo)){//��ʤ�ӿ�
//				rs =getRandomDigit();
				rs=Mobile_Interface.Recharge(seqNo1, phone, (Integer.parseInt(tradeFee)/1000)+"".trim(), phoneType);
			}else if("10007".equals(tradeNo)){//�����ƶ�����
//				rs =getRandomDigit();
				String wString=Webservices_Flow.flow_Result(seqNo1,phone,content.get("name")+"".trim());
				rs=wString.split("#")[0];
				if("0".equals(rs)){
					db.update("update wht_orderform_" + time.substring(2, 6)+" set writeoff='"+wString.split("#")[1]+"' where tradeserial='"+seqNo1+"'");
				}
			}else if("09037".equals(tradeNo)){//�Ͱ��ƶ�
//				rs = getRandomDigit();
				rs = HeBao.Order_Recharge(orderID, phone,Integer.parseInt(tradeFee)/10+"",content.get("gh")+"".trim());
			}else if("10008".equals(tradeNo)){//����ͨ����
//				rs = getRandomDigit();
				rs=FlowsService.CZ_Orders(phone, content.get("name")+"", seqNo1);
			}else if("10009".equals(tradeNo)){//��������
//				rs = getRandomDigit();
				rs=""+LeliuFlowCharge.leliuFill(seqNo1,phone,""+content.get("name"),phoneType);
			}else if("10010".equals(tradeNo)){//��������
//				rs = getRandomDigit();
				rs=LianlianFlows.CZ_Orders(phone, content.get("name")+"", seqNo1);
			}else if("09038".equals(tradeNo)){//����ѳ�ֵ
//				rs = "-1";//getRandomDigit();
				rs=Mobile_Zb_Inter.cz(phone,Integer.parseInt(tradeFee)/10+"",orderID,content.get("gh")+"".trim())+"".trim();
			}else if("10011".equals(tradeNo)){//��������
//				rs = getRandomDigit();
				rs=Mobile_Zb_Inter.cz_flow(phone, content.get("name")+"", seqNo1);
			}else if("10012".equals(tradeNo)){//���ɳ��ƶ�������ֵ
//				rs = getRandomDigit();
				rs=GDFreeTrafficCharge.testEC001(phone,checkAccount,content.get("name")+"")+"";
			}else if("10013".equals(tradeNo)){//�㶫������
//				rs = getRandomDigit();
				rs=LlFan.llFanFill(seqNo1,phone,content.get("name")+"","2","")+"";
			}else if("10014".equals(tradeNo)){//��������
//				rs = getRandomDigit();
				rs=dingxFlow.fillFlow(seqNo1, phone,content.get("name")+"","0","0","0",time);
			}else if("10015".equals(tradeNo)){//��������
//				rs = getRandomDigit();
				rs=ZhiXin.ZxPreOrders(phone,content.get("name")+"",seqNo1,time,"4");
			}
			
			//����  һ���ᣬ�ڿ�,�ֳ��ƶ�,��ʤ�ӿ�  ֱ�ӷ��ض������
			if(content.get("gh")!=null || "09020".equals(tradeNo) || "01000".equals(tradeNo) || "09034".equals(tradeNo) ||"09036".equals(tradeNo)){
				returnDeal(time,rs, seqNo1, userNo, acct1, userFacct, isline,
						acct2, parentFacct, facctFee, parentFee, acctType);
			}else{
				if("-1".equals(rs)){//�����ύʧ�ܣ���ֱ���˷�
					// �������������η�Ӷ
					if(tradetype.equals("0009")||tradetype.equals("0011")||tradetype.equals("0010")){
						isline="1";
					}
					returnDeal(time,"-1", seqNo1, userNo, acct1, userFacct, isline,
							acct2, parentFacct, facctFee, parentFee, acctType);
				}
			}
			if (rs.equals("0")) {
				//�����ֱ�������ӿڣ�ֱ���޸Ķ���״̬ 
				if("10001".equals(tradeNo)||"10005".equals(tradeNo) || "10012".equals(tradeNo)){
					db.update("update wht_orderform_" + time.substring(2, 6)+" set state=0 where tradeserial='"+seqNo1+"'");
				}
				return 0;
			} else if (rs.equals("-1")) {
				return -1;
			} else {
				return -2;
			}
		} catch (Exception e) {
			try {
				if((null!=db)&&!db.getConn().getAutoCommit()){
				db.rollback();
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			Log.error("����ȫ��ҵ��ʧ��:"+e.toString() );
			return 3;// ϵͳ��æ
		} finally {
			if (null != db) {
				db.close();
			}
		}
	}
	public String getRandomDigit() {
		Random r = new Random();
		int n = r.nextInt(100);
		String m;
		if (n < 70) {
			m = "0";
			return m;
		} else if (n < 90) {
			m = "-1";
			return m;
		}else {
			m = "-2";
			return m;
		}
		
	}

	/**
	 * һ���ݳ�ֵ
	 * 
	 * @param transaction_id
	 * @param currTime
	 * @param totalPrice
	 * @param in_acct
	 * @param ip
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String yikaHuiFill(String transaction_id, String currTime,
			String totalPrice, String in_acct, String ip) {
		List lists = new ArrayList();
		lists.add("userNo");
		lists.add(Constant.YKH_USERNO);
		lists.add("goodsId");
		lists.add("100004152");
		lists.add("ptOrderNo");
		lists.add(transaction_id);
		lists.add("ptPayTime");
		lists.add(currTime);
		lists.add("buyNum");
		lists.add("1");
		lists.add("unitPrice");
		double a = Integer.parseInt(totalPrice) / 1000.0;
		lists.add(a);
		lists.add("totalPrice");
		lists.add(a);
		lists.add("gameAccount");
		lists.add(in_acct);
		lists.add("notifyUrl");
		lists.add(Constant.RETURN_BAK_URL);
		lists.add("merchantIP");
		lists.add(ip);
		lists.add("sign");
		lists.add(MD5Util.MD5Encode(Constant.YKH_USERNO + "100004152"
				+ transaction_id + currTime + 1 + a + a + in_acct + ip
				+ Constant.YKH_SIGNKEY, "UTF-8"));
		YiKahuiTrade qb = new YiKahuiTrade();
		Map<String, String> rs = qb.YikahuiFill(lists);
		String code = rs.get("code");
		return code;
	}

	/**
	 * һ���ݷ����ڲ�����
	 * 
	 * @param time
	 * @param code
	 * @param seqNo
	 * @param userno
	 * @param acct1
	 * @param userfacct
	 * @param parentid
	 *            1��ʾ��ֱӪ 0��ʾ��ֱӪ
	 * @param acct2
	 * @param parentfacct
	 * @param facctFee
	 * @param parentFee
	 */
//	public int conSA=0;
	public void returnDeal(String time, String code, String seqNo,
			String userno, String acct1, String userfacct, String parentid,
			String acct2, String parentfacct, String facctFee,
			String parentFee, int acctType) {
		    Log.info("returnDeal :"+seqNo+" userNO:"+userno+" userfacct:"+userfacct+" time:"+time);
			String orderTableName = "wht_orderform_" + time.substring(2, 6);
			String cdName="wht_qbbreakrecord_" + time.substring(2, 6);
			String acctTbaleName = "wht_acctbill_" + time.substring(2, 6);
			DBService db = null;
			try {
				db = new DBService();
				String ss=db.getString(" select state from "+orderTableName+" where tradeserial='" + seqNo +"' and userno='" + userno + "' and (state=0 or state=1)");
				if(ss!=null && !"".equals(ss)){
					Log.info("�ص�ҵ����,tradeserial:"+seqNo+",userno:"+userno+",����״̬Ϊ�ռ�״̬,������,����״̬Ϊ,state:"+ss);
					return ;
				}
				db.setAutoCommit(false);
				if ("0".equals(code)) {// cg
					db.update("update " + orderTableName
							+ " set state=0,chgtime='"+Tools.getNow3()+"' where tradeserial='" + seqNo
							+ "' and userno='" + userno + "'");
					Log.info("�ص�ҵ����,tradeserial:"+seqNo+",userno:"+userno+",�ص����Ϊ �ɹ�,����״̬�޸�Ϊ,0");
				} else if ("-1".equals(code)) {// ʧ��1accountleft
					//�����Q�������жϲ𵥱�������޳ɹ���¼  1
					String pd=db.getString("SELECT state FROM "+cdName+" WHERE  buyid=16 AND oldorderid='"+seqNo+"'");
					if(null!=pd&&pd.equals("0")){
						db.update("update " + orderTableName
								+ " set state=4 where tradeserial='" + seqNo
								+ "' and userno='" + userno + "'");
						Log.info("�ص�ҵ����,tradeserial:"+seqNo+",userno:"+userno+",�ص����Ϊ ʧ��,�𵥱�����һ�ʳɹ���,�������˷�ҵ����,�޸�״̬Ϊ 4");
						return ;
					}
					//1
//					synchronized (lock1) {
//						conSA=(conSA+1);
//						System.out.println(conSA);
						// ��֧�ֿ���
						String newtime = Tools.getNow3();
						String tableName = "wht_acctbill_" + newtime.substring(2, 6);
						String sql1 = "select tradeobject,fee  from " + orderTableName
								+ "  where tradeserial='" + seqNo + "' and userno='"
								+ userno + "'";
						String[] str1 = db.getStringArray(sql1);
						String userleft0 = "select accountleft from wht_childfacct where childfacct='"
								+ userfacct + "01'";
						long userleft = db.getLong(userleft0);
						String fialAcct1 = Tools.getAccountSerial(newtime, "QDF"
								+ str1[0]);
		
						db.update("update " + orderTableName
								+ " set state=1,chgtime='"+Tools.getNow3()+"' where tradeserial='" + seqNo
								+ "' and userno='" + userno + "'");
						Object[] acctObj1 = { null, userfacct + "01", fialAcct1,
								str1[0], newtime, Double.parseDouble(str1[1]),
								Double.parseDouble(facctFee), acctType, "ʧ���˷�", 0,
								newtime, userleft + Double.parseDouble(facctFee), seqNo,
								userfacct + "01", 2 };
						db.logData(15, acctObj1, tableName);
						db.update("update wht_childfacct set accountleft=accountleft+"
								+ Double.parseDouble(facctFee) + " where childfacct='"
								+ userfacct + "01'");
						if (!"1".equals(parentid)) {
							String fialAcct2 = Tools.getAccountSerial(newtime, "QSF"
									+ str1[0]);
							String userleft2 = "select accountleft from wht_childfacct where childfacct='"
									+ parentfacct + "02'";
							long userleft3 = db.getLong(userleft2);
		
							Object[] acctObj2 = { null, parentfacct + "02", fialAcct2,
									str1[0], newtime, Double.parseDouble(parentFee),
									Integer.parseInt(parentFee), 15, "���׳���", 0,
									newtime, userleft3 - Double.parseDouble(parentFee),
									seqNo, parentfacct + "02", 1 };
							db.logData(15, acctObj2, tableName);
							db.update("update wht_childfacct set accountleft=accountleft-"
											+ Double.parseDouble(parentFee)
											+ " where childfacct='"
											+ parentfacct
											+ "02'");
						}
//					}
						Log.info("�ص�ҵ����,tradeserial:"+seqNo+",userno:"+userno+",�ص����Ϊ ʧ��,�����˷�ҵ����,�޸�״̬Ϊ 1");
					// ��֧�ֿ�����
				} else {
					db.update("update " + orderTableName
							+ " set state=4 where tradeserial='" + seqNo
							+ "' and userno='" + userno + "'");
					Log.info("�ص�ҵ����,tradeserial:"+seqNo+",userno:"+userno+",�ص���� δ֪,�޸�״̬Ϊ 4");
				}
				db.commit();
			} catch (Exception e) {
				db.rollback();
				e.printStackTrace();
				Log.error("һ���ݴ������쳣:" + seqNo + " " + e.toString());
			} finally {
				if (null != db) {
					db.close();
				}
			}
			return ;
	}

	/**
	 * ��û۸������Ϣ
	 * 
	 * @param type
	 */
	public List getHfkList(String userno) {
		DBService dbService = null;
		try {
			dbService = new DBService();
			String sql = "select phone,fundacct,hfkid,sfz,time from sys_hfk"
					+ " where resourceid='" + userno + "'";
			return dbService.getList(sql);
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		} finally {
			if(dbService!=null){
				dbService.close();
			}
		}
	}

	/**
	 * ��û۸������Ϣ
	 * 
	 * @param type
	 */
	public int awardsOP(String login, String facct, String mon, String persion,
			String ids) {
		DBService dbService = null;
		String time = Tools.getNow3();
		String tableName = "wht_acctbill_" + time.substring(2, 6);
		try {
			int money = (int) (Float.parseFloat(mon));
			int id = Integer.parseInt(ids);
			String serial = Tools.getAccountSerial(time, login);
			dbService = new DBService();
			int left = dbService
					.getInt("select accountleft from wht_childfacct where childfacct='"
							+ facct + "'");
			int left1 = left + money;
			dbService.setAutoCommit(false);
			String sql0 = "update wht_monthawards SET state=0,operate='" + time
					+ "',operatewho='" + persion + "' WHERE id=" + id
					+ " AND login='" + login + "'";
			String sql1 = "update wht_childfacct set accountleft=accountleft+"
					+ money + " where childfacct='" + facct + "'";
			Object[] params = { null, facct, serial, facct, time, money, money,
					18, "�¶�Ӷ����", 0, time, left1, serial, facct, 2 };
			dbService.update(sql0);
			dbService.update(sql1);
			dbService.logData(15, params, tableName);
			dbService.commit();
			return 0;
		} catch (Exception ex) {
			dbService.rollback();
			ex.printStackTrace();
			return 1;
		}finally {
			if(dbService!=null){
				dbService.close();
			}
		}
	}

	public static void main(String[] args) {
		int n = (int) FloatArith.mul(500000, FloatArith.div(Double
				.valueOf(0.25), 100));
		int n1 = (int) FloatArith.mul(500000, 1 - FloatArith.div(Double
				.valueOf(1.5), 100));
		System.out.println(n);
		System.out.println((int) (Float.parseFloat("12.558")));
		System.out.println(FloatArith.mul(Double.parseDouble(1000+""),1-FloatArith.div(4.00, 100)));
	}

	public String compareUser(String userid) {
		DBService dbService = null;
		try {
			dbService = new DBService();
			String sql = "SELECT a.sr_type FROM sys_roletype a LEFT JOIN "
					+ "sys_role b ON b.sr_type=a.sr_type LEFT JOIN sys_user c "
					+ "ON c.user_role=b.sr_id WHERE c.user_login='" + userid
					+ "'";
			return dbService.getString(sql);
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		} finally {
			if(dbService!=null){
				dbService.close();
			}
		}
	}

	public String findUserType(String username, String fcct) {
		DBService dbService = null;
		try {
			dbService = new DBService();
			String sql = "SELECT a.sr_type FROM sys_roletype a LEFT JOIN "
					+ "sys_role b ON b.sr_type=a.sr_type LEFT JOIN sys_user c "
					+ "ON c.user_role=b.sr_id WHERE c.user_login='"
					+ fcct
					+ "' AND c.user_pt=(SELECT user_id FROM sys_user WHERE user_login='"
					+ username + "')";
			return dbService.getString(sql);
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}finally {
			if(dbService!=null){
				dbService.close();
			}
		}
	}

	/**
	 * ��Ѹ
	 * ���ݺŶλ�ȡ����ʡ����Ӫ������
	 * 
	 * @param num
	 * @return �������� ʡ������
	 */
	public String[] getYXPhoneInfo(String phone) {
		DBService dbService = null;
		try {
			dbService = new DBService();
			String sql="SELECT (CASE WHEN a.phone_type=0 THEN 'DX' " +
					"WHEN a.phone_type=1 THEN 'YD' ELSE 'UN' END) " +
					"AS phone_type,(CASE WHEN b.sa_name='���ɹ�������' " +
					"THEN LEFT(b.sa_name,3) WHEN b.sa_name='������ʡ' " +
					"THEN LEFT(b.sa_name,3) ELSE LEFT(b.sa_name,2) END ) " +
					"AS province FROM sys_phone_area a,sys_area b " +
					"WHERE a.phone_num='"
					+ phone.substring(0, 7) + "' AND a.province_code=b.sa_id";
			return dbService.getStringArray(sql);
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}finally {
			if(dbService!=null){
				dbService.close();
			}
		}
	}
	
	public boolean deldate(String date) {
		DBService dbService = null;
		try {
			dbService = new DBService();
			StringBuffer sql1 = new StringBuffer();
			sql1.append("delete from wht_datelimit where date='" + date + "';");
			dbService.update(sql1.toString());
			return true;
		} catch (Exception ex) {
			System.out.println(ex.toString());
			return false;
		} finally {
			if(null!=dbService)
			dbService.close();
		}
	}
	
	public int adddate(String str) throws Exception {
		DBService dbService =null;
		try {
			dbService = new DBService();
			String sql = "insert into wht_datelimit(date) values('" + str + "')";
			dbService.update(sql);
			return 0;
		} catch (Exception ex) {
			ex.printStackTrace();
			return 1;
		} finally {
			if(null!=dbService)
			dbService.close();
		}
	}
	
	
	public List listlimitdate() throws Exception {
		DBService dbService = null;
		try {
			dbService = new DBService();
			String sql = "SELECT DATE ,DATE_FORMAT(DATE,'%Y-%m-%d') FROM wht_datelimit ORDER BY DATE";
			List list = dbService.getList(sql.toString());
			return list;
		} catch (Exception ex) {
			throw ex;
		} finally {
			if(null!=dbService)
			dbService.close();
		}
	}

	/**
	 * Q�� �����ύ�ɹ�  ��  �����ɹ��޸�״̬
	 * @param db
	 * @param tableName
	 * @param check
	 * @param seqNo
	 * @param userno
	 * @param interfaceType 1����(�������׳ɹ���״̬��Ϊ0)   2����(�����ύ�ɹ���״̬��Ϊ4)
	 */
	public void innerQQSuccessDeal(DBService db, String tableName, String check,
			String seqNo, String userno,String interfaceType) {
		try {
			if("2".equals(interfaceType)){//���������ύ�ɹ�
				db.update("update " + tableName + " set state=4,writecheck='"
						+ check + "' where tradeserial='" + seqNo
						+ "' and userno='" + userno + "'");
			}else if("1".equals(interfaceType)){//���Ŷ������׳ɹ�
				db.update("update " + tableName + " set state=0,writecheck='"
						+ check + "' where tradeserial='" + seqNo
						+ "' and userno='" + userno + "'");
			}else if("89".equals(interfaceType)){//ֱͨ��
				db.update("update " + tableName + " set state=0,writecheck='"
						+ check + "' where tradeserial='" + seqNo
						+ "' and userno='" + userno + "'");
			}else{
				Log.info("Q��δ֪�ӿ�����,���޸Ķ���״̬,,,");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	/**
	 * �װ��ײ�ѯ
	 * @param orderid
	 * @return
	 */
	public String queryYiBaiMi(String orderid,String phone,String tradetime){
		int interfaceID = -1;
		String tradeNo = "";
//		String serialNo="";
		String seqNo = com.wlt.webm.tool.Tools.getStreamSerial(phone);
		tradeNo = "09019"; 
//		serialNo = "yb09019" + Tools.getNow3().substring(6)
//		+ Tools.buildRandom(2) + Tools.buildRandom(3); //�װ����ƶ�
		TradeMsg msg = new TradeMsg();
		msg.setSeqNo(seqNo);
		msg.setFlag("0");// ������
		msg.setMsgFrom2("0103");
		msg.setServNo("00");
		msg.setTradeNo(tradeNo);
		Map<String, String> content = new HashMap<String, String>();
		content.put("inTime", tradetime);
		content.put("orderId", orderid);
		Log.info("��ѯ���趩����:" + orderid);
		msg.setContent(content);
			if (!PortalSend.getInstance().sendmsg(msg)) {
				return "4";// ������Ϣʧ��,��ֵʧ��
			}
			int k = 0;
			TradeMsg rsMsg = null;
			while (k < 180) {
				k++;
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				rsMsg = MemcacheConfig.getInstance().get(seqNo);
				if (null == rsMsg) {
					continue;
				} else {
					MemcacheConfig.getInstance().delete(seqNo);
					break;
				}
			}
			if (null == rsMsg && k >= 90) {
				return "4";
			}
			String code1 = rsMsg.getRsCode();
			if(code1.equals("000")){
				return "0";//��ѯ�ɹ�
			}
			else if(code1.equals("001")){
				return "1";//��ѯʧ��
			}
			else  if(code1.equals("008")){
				return "4";//��ѯ������
			}
			return "4";
		}
	
	
	/**
	 * �������ǵ�����ȡӶ�����
	 * @param flag
	 * @param userPid
	 * @param phonePid
	 * @return
	 */
	public String[] getJCEmploy(DBService db,String flag,String userPid,String phonePid,int payfee,String phonetype) {
        String[] str=null;
        String cm_id;
        if (payfee > 0 && payfee <= 20){
		try {
			cm_id = db.getString("select cm_id from sys_valueRange where min<="
					+ payfee + " and max>" + payfee);
				String sql = "select value,value1 from sys_employ5 where pid="
						+ phonePid + " and type=" + phonetype + " and cm_id='"
						+ cm_id + "'";
				return db.getStringArray(sql);
		} catch (SQLException e1) {
			e1.printStackTrace();
			System.out.println(e1.toString());
			return null;
		}
        }
		try {
			String sql = null;
			if ("1".equals(flag)||"2".equals(flag)) {// ֱӪ
			str=new String[]{DBConfig.getInstance().getJcydzy()};
			} else {// ��ֱӪ
				if (!userPid.equals(phonePid)) {
					str=new String[]{DBConfig.getInstance().getJcydzy()};
				} else {// ��ʡ
					str=new String[]{DBConfig.getInstance().getJcydfzy_s(),DBConfig.getInstance().getJcydfzy_d()};
				}
			}
			return str;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static boolean getdesc(String userno)
	{
		DBService dbService = null;
		try {
			dbService = new DBService();
			String sql = "SELECT sr_desc FROM sys_role WHERE sr_id=(SELECT user_role FROM sys_user WHERE user_no='"+userno+"')";
			String str=dbService.getString(sql);
			if("1".equals(str))
			{
				return true;
			}
			return false;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}finally {
			if(dbService!=null){
				dbService.close();
			}
		}
	}
	
	/**
	 * ��ӽ�����
	 * 
	 * @param bpForm
	 * @return ��¼��
	 * @throws Exception
	 */
	public int addSetup(String setupname,String cm_type,String cm) {
		DBService dbService =null;
		try {
			dbService = new DBService();
			String table = "sys_setupgroups";
			Object[] params = { null,cm_type,setupname,cm,Tools.getNow3(),null,null,null };
			dbService.logData(8, params, table);
			return 0;
		} catch (Exception ex) {
			ex.printStackTrace();
			return 1;
		} finally {
			if(dbService!=null){
				dbService.close();
			}
		}
	}
	
	/**
	 * ɾ��������
	 * @param id
	 * @return
	 */
	public boolean delSetupGp(String id) {
		DBService dbService = null;
		try {
			dbService = new DBService();
			String sql = "delete from sys_setupgroups where groupsID="+id;
			dbService.update(sql.toString());
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}finally {
			if(dbService!=null){
				dbService.close();
			}
		}
	}
	
	public List getOneAngent()
	{
		DBService dbService =null;
		try {
			dbService = new DBService();
			return dbService.getList("SELECT groupsID,groupname FROM sys_setupgroups",null);
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		} finally {
			if(dbService!=null){
				dbService.close();
			}
		}
	}
	
	public List getgroupname()
	{
		DBService dbService =null;
		try {
			dbService = new DBService();
			return dbService.getList("SELECT groupsId,groupname FROM sys_setupgroups",null);
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		} finally {
			if(dbService!=null){
				dbService.close();
			}
		}
	}
	public List getuserList()
	{
		DBService dbService =null;
		try {
			dbService = new DBService();
			return dbService.getList("SELECT u.user_no,CONCAT(u.user_login,'/',u.user_ename) FROM sys_user u,sys_role r WHERE u.user_role=r.sr_id  AND r.sr_type=1 AND r.sr_desc=1 AND u.user_no NOT IN (SELECT m.userno FROM sys_oneagentmaps m)",null);
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		} finally {
			if(dbService!=null){
				dbService.close();
			}
		}
	}
	
	public String[] getPlaceMoney(String tradeType,String userno)
	{
		String tableName="wht_orderform_"+Tools.getNowMonth();
		int zfb=10000;
		int qq=5000;
		String ss="";
		String[] strs=new String[3]; //0��� 1���콻�׽��  2ʣ����
		String sql="";
		String ordersql="";
		if("3".equals(tradeType))
		{
			//���ת��
			sql="SELECT reversalcount FROM sys_reversalcount WHERE user_no='"+userno+"' AND tradetype=3 AND reversalcount=0";//0 ��ʾ��Ȩ�� 1��ʾ��Ȩ��
			DBService dbService =null;
			try{
				dbService=new DBService();
				String str1=dbService.getString(sql,null);
				if("0".equals(str1))
				{
					strs[0]="true";
				}
				else
				{
					return null;
				}
				return strs;
			}catch(Exception ex){
				Log.error("���ת�ƻ�ȡȨ���쳣������"+ex);
			}finally{
				if(null!=dbService)
					dbService.close();
			}
		}
		if("1".equals(tradeType))
		{
			//qq
			 sql="SELECT reversalcount FROM sys_reversalcount WHERE user_no='"+userno+"' AND tradetype=1";
			 ordersql="SELECT SUM(fee)/1000 FROM "+tableName+" WHERE userno='"+userno+"' AND phone_type=3 AND DATE_FORMAT(tradetime,'%Y%m%d')=DATE_FORMAT(SYSDATE(),'%Y%m%d') AND state<>1 AND state<>5 AND state<>7 ";
			 ss=qq+"".trim();
		}
		if("2".equals(tradeType))
		{
			//֧����
			sql="SELECT reversalcount FROM sys_reversalcount WHERE user_no='"+userno+"' AND tradetype=2";
			ordersql="SELECT SUM(fee)/1000 FROM "+tableName+" WHERE userno='"+userno+"' AND phone_type=5 AND DATE_FORMAT(tradetime,'%Y%m%d')=DATE_FORMAT(SYSDATE(),'%Y%m%d') AND state<>1 AND state<>5 AND state<>7 ";
			ss=zfb+"".trim();
		}
		
		DBService dbService =null;
		try{
			dbService=new DBService();
			String str1=dbService.getString(sql,null);
			if(str1==null || "".equals(str1))
			{
				strs[0]=ss;
			}
			else
			{
				strs[0]=str1;
			}
			String orderstr1=dbService.getString(ordersql,null);
			strs[1]=orderstr1==null?"0":new DecimalFormat("##0.00").format(Float.parseFloat(orderstr1));
			strs[2]=(Float.parseFloat(strs[0])-Float.parseFloat(strs[1]))+"";
			return strs;
		}catch(Exception ex){
			Log.error("��ȡ�޶��쳣������"+ex);
		}finally{
			if(null!=dbService)
				dbService.close();
		}
		return null;
	}

	/**
	 * ����ϵͳ���  ��ȡ�˻����
	 * @param userno
	 * @return float
	 */
	public float getFundacctMoney(String userno){
		float returnMoney=0;
		DBService db=null;
		try{
			db=new DBService();
			returnMoney=db.getFloat("SELECT accountleft FROM wht_childfacct WHERE childfacct=(SELECT CONCAT(fundacct,'01') FROM wht_facct WHERE user_no='"+userno+"')");
		}catch(Exception ex){
			Log.error("��ȡ�˻�����쳣������ϵͳ���:"+userno);
			return -1;
		}finally{
			if(null!=db){
				db.close();
			}
		}
		return returnMoney;
	}
	
	/**
	 * ����tradeserial ��ȡ writeoff �ֶ�
	 * @param tradeserial
	 * @return
	 */
	public static String getWriteOff(String tradeserial){
		DBService db=null;
		try{
			db=new DBService();
			String strs=db.getString("SELECT writeoff FROM wht_orderform_"+Tools.getNow4().substring(2,6)+" WHERE tradeserial='"+tradeserial+"'");
			return strs;
		}catch(Exception ex){
			Log.error("���������ж�������ˮ�ţ�tradeserial="+tradeserial);
		}finally{
			if(null!=db){
				db.close();
			}
		}
		return null;
	}
	
	public static String[] AppUserInfos(int url_id){
		DBService db=null;
		try{
			db=new DBService();
			String[] strs=db.getStringArray("SELECT NAME,company,POSITION,phone,telphone,address,qq,msn,email,website,note FROM wht_app_user WHERE url_id="+url_id+" LIMIT 0,1 ");
			return strs;
		}catch(Exception ex){
			Log.error("app ��ȡ�û���Ϣչʾ��Ϣerror,,ex"+ex);
		}finally{
			if(null!=db){
				db.close();
				db=null;
			}
		}
		return null;
	}
	
	/**
	 * �Ḷ��T+0�ӿ�
	 * @param userno  �û�ϵͳ���
	 * @param id      id
	 * @param time
	 * @return
	 */
	public int hfkAddMoney(String userno,String id,String time) {
		DBService dbService = null;
		try {
			dbService = new DBService();
			String sql = "SELECT facct,fee,ournum FROM sys_hfk_record WHERE state=0 AND userno='"+userno
			+"' and id="+id+" and CONCAT(SUBSTRING(tradetime,1,8),'230000')>'"+time+"'";
            String[] rs=dbService.getStringArray(sql);
            if(null==rs){
            	return 4;//δ��ѯ���������
            }else if(Integer.parseInt(rs[1])<=2000){
            	return 5;//�����Ѳ��㲻�ܼӿ�
            }else{
            	long fee=Long.parseLong(rs[1]);
            	long facctfee=fee-2000;
            	String tableName = "wht_acctbill_" + time.substring(2,6);
            	long left=dbService.getLong("select accountleft from wht_childfacct where childfacct='"+rs[0]+"01'");
                Object[] obj={null,rs[0]+"01",com.wlt.webm.tool.Tools.getStreamSerial(userno),rs[0]+"01",time,fee,
                		facctfee,17,"�۸���T+0",0,time,left+facctfee,
                		rs[2],rs[0],2};
                dbService.setAutoCommit(false);
				dbService.logData(15, obj, tableName);
				String sql0="update sys_hfk_record set state=1,descript='T+0',feetime='"+time+"' where id="+Integer.parseInt(id);
				
				String sql1="update wht_facct set accountleft=accountleft+"+facctfee+
				      ",awardleft=awardleft+"+facctfee+" where fundacct='"+rs[0]+"'";
				
				String sql2="update wht_childfacct set accountleft=accountleft+"+facctfee+
			      ",awardleft=awardleft+"+facctfee+" where childfacct='"+rs[0]+"01'";
				dbService.update(sql0);
				dbService.update(sql1);
				dbService.update(sql2);
				dbService.commit();
				return 0;
            }
            
		} catch (Exception ex) {
			dbService.rollback();
			Log.error("�۸���ӿ�ʧ��id="+id+"  "+ex.toString());
			return 5;//�쳣
		} finally {
			if(dbService!=null){
				dbService.close();
			}
		}
	}

	/**
	 * �����ӿ������б�
	 * @return list
	 */
	public List<String[]> flow_config(){
		DBService db=null;
		try {
			db=new DBService();
			return db.getList("SELECT f.id,CASE f.operator_type WHEN 0 THEN '��������' WHEN 1 THEN '�ƶ�����' WHEN 2 THEN '��ͨ����' END AS a,s.name,f.flow_interId,f.exp1,f.operator_type FROM sys_Flow f LEFT JOIN sys_interface s ON f.flow_interId=s.id order by f.operator_type,f.exp1+0 asc");
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(db!=null){
				db.close();
				db=null;
			}
		}
		return null;
	}
	/**
	 * �������е����� �ӿڶ�Ӧ�����
	 * @return list
	 */
	public List<String[]> flow_cinfig_interList(){
		DBService db=null;
		try {
			db=new DBService();
			return db.getList("SELECT interfaceID,flowface FROM sys_interface2face ORDER BY interfaceID ASC");
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(db!=null){
				db.close();
				db=null;
			}
		}
		return null;
	}
	
	/**
	 * ���� �޸� ���������б�
	 * @param inter �����ӿ�id
	 * @param flowId ��ǰ��¼id 
	 * @param val Ǯ
	 * @param sertypes ҵ������  0����  1�ƶ�  2��ͨ
	 * @param flag  0����  1�޸�
	 * @return boolean
	 */
	public boolean update_Flow(String inter,String flowId,String val,String sertypes,String flag){
		DBService db=null;
		try {
			db=new DBService();
			StringBuffer buf=new StringBuffer("SELECT 1 FROM sys_Flow WHERE operator_type="+sertypes+" AND exp1="+val);
			if(flowId!=null && !"".equals(flowId)){
				buf.append(" AND id<>"+flowId);
			}
			String isBool=db.getString(buf.toString());
			if(!"".equals(isBool) && isBool!=null){
				return false;
			}
			if("1".equals(flag)){//�޸�
				return db.update("UPDATE sys_Flow SET flow_interId="+inter+",exp1="+val+",operator_type="+sertypes+" WHERE id="+flowId)>0?true:false;
			}
			if("0".equals(flag)){//����
				return db.update("INSERT INTO sys_Flow(operator_type,flow_interId,exp1) VALUES("+sertypes+","+inter+","+val+")")>0?true:false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(db!=null){
				db.close();
				db=null;
			}
		}
		return false;
	}
	/**
	 * ������Ӫ�����ͺ�����ȡ�����ӿ�
	 * @param n
	 * @return 21 ��ͨ��һ���ӿ� 23 ��ͨ�ڶ����ӿ�
	 */
	public static int getFlowInterface(int n,int price){
		DBService db=null;
		try {
			db=new DBService();
			return db.getInt("SELECT flow_interId FROM sys_Flow WHERE operator_type="+n
					+" and exp1="+price);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(db!=null){
				db.close();
				db=null;
			}
		}
		return 0;
	}
	
	/**
	 * ������Ӫ�����ͻ�ȡ�����ӿ�  �� ��Ʒ����Ӧ�۸�
	 * @param n
	 * @return  
	 */
	public static String  getInfo(){
		DBService db=null;
		try {
			db=new DBService();
			String b=db.getString("SELECT GROUP_CONCAT(NAME,'|',price,'|',TYPE) FROM wht_flowprice");
			return b;
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(db!=null){
				db.close();
				db=null;
			}
		}
		return null;
	}
	
	/**
	 * APP��ѯ�������
	 * @param username
	 * @return
	 */
	public static ArrayList<String[]> getorders(String username){
		DBService db=null;
		try {
			db=new DBService();
			String tablename="wht_orderform_"+Tools.getNowMonth();
			return (ArrayList<String[]>) db.getList("SELECT tradeobject,fee/1000,tradefee/1000,tradetime,CASE WHEN state=0 THEN '�ɹ�' WHEN state=1 THEN 'ʧ��' ELSE  '������' END FROM "+tablename+" WHERE service='0009' AND user_name='"+username+"'and tradetime>='"+com.wlt.webm.tool.Tools.getOtherTime(-3)+"'");
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(db!=null){
				db.close();
				db=null;
			}
		}
		return null;
	}
	
	/**
	 * ������Ӫ�����ͻ�ȡ���ͽӿ�
	 * @param type ��Ӫ������  ���� �ƶ� ��ͨ
	 * @param areaId  ʡ�ݵ���
	 * @return
	 */
	public static List getFaceBytype(String type,String areaId){
		DBService db=null;
		try {
			db=new DBService();
			return db.getList("SELECT flow.flow_interId,flow.exp1 FROM sys_Flow flow,(SELECT map.interid FROM sys_flowinterfaceMaping map WHERE map.type='"+type+"' AND pid='"+areaId+"' LIMIT 0,1) AS ac WHERE flow.operator_type='"+type+"' AND flow.flow_interId=ac.interid");
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(db!=null){
				db.close();
				db=null;
			}
		}
		return null;
	}
	
	
	/**
	 * ������Ӫ������ ʡ�ݱ����ȡ�ӿ���Ϣ
	 * @param phonetype
	 * @param areaid
	 * @return
	 */
	public static int getFlowInterface(String phonetype,String areaid){
		DBService db=null;
		try {
			db=new DBService();
			return db.getInt("SELECT interid FROM sys_flowinterfaceMaping WHERE type="+phonetype+
					" AND pid="+areaid);
		} catch (Exception e) {
			e.printStackTrace();
			Log.error("������Ӫ������:"+phonetype+" ʡ��:"+areaid+"��ȡ�����ӿ���Ϣʧ��");
		}finally{
			if(db!=null){
				db.close();
				db=null;
			}
		}
		return 0;
	}
	
	/**
	 * ��ȡ���������ӿ�
	 * @param phonetype
	 * @param areaid
	 * @return
	 */
	public static List getFlowInterfaces(){
		DBService db=null;
		try {
			db=new DBService();
			return db.getList("SELECT id,NAME FROM sys_interface WHERE flag=2");
		} catch (Exception e) {
			e.printStackTrace();
			Log.error("��ȡ�����ӿ���Ϣʧ��");
		}finally{
			if(db!=null){
				db.close();
				db=null;
			}
		}
		return null;
	}
	
	/**
	 * ��ȡƽ̨�ӿ�����Ϣ
	 * @return
	 */
	public static List getAgents(){
		DBService db=null;
		try {
			db=new DBService();
			return db.getList("SELECT user_ename, user_no FROM sys_user a WHERE EXISTS(SELECT sr_id FROM sys_role b WHERE sr_type=4 AND a.user_role=b.sr_id)");
		} catch (Exception e) {
			e.printStackTrace();
			Log.error("��ȡ�ӿ�����Ϣʧ��");
		}finally{
			if(db!=null){
				db.close();
				db=null;
			}
		}
		return null;
	}
}
