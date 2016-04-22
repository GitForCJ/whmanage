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
 * 面额管理
 */
public class BiProd {
	/** 同步锁 **/
	private static Object lock = new Object();
//	private static Object lock1 = new Object();

	/**
	 * 获取面额信息列表
	 * 
	 * @return 面额信息列表
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
						temp[1] = "移动";
					} else if ("T".equals(temp[1])) {
						temp[1] = "电信";
					} else if ("U".equals(temp[1])) {
						temp[1] = "联通";
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
	 * 获取面额区间信息列表
	 * 
	 * @return 面额区间信息列表
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
	 * 获取代办点绑定的拉卡拉列表信息
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
			Log.error("获取 代办点拉卡拉信息列表异常，，，"+ex);
		} finally {
			if(dbService!=null){
				dbService.close();
			}
		}
		return null;
	}
	/**
	 * 获取指定 拉卡拉 刷卡记录
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
		//判断时间
		
		
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
			Log.error("获取指定 拉卡拉 刷卡记录，，，"+ex);
		} finally {
			if(dbService!=null){
				dbService.close();
			}
		}
		return null;
	}
	
	/**
	 * 获取指定 慧付款 刷卡记录
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
		//判断时间
		DBService dbService =null;
		try {
			dbService= new DBService();
			String sql = "SELECT phone,tradefee,fee,DATE_FORMAT(tradefirst,'%Y-%m-%d'),DATE_FORMAT(feetime,'%Y-%m-%d %k:%i:%s'),cardtype,state,descript,id FROM sys_hfk_record " +
					" WHERE phone='"+tid+"' " +str+" ORDER BY tradetime DESC";
			List list = dbService.getList(sql.toString(),null);
			return list;
		} catch (Exception ex) {
			Log.error("获取指定 慧付款 刷卡记录，，，"+ex);
		} finally {
			if(dbService!=null){
				dbService.close();
			}
		}
		return null;
	}
	/**
	 * 获取面额区间信息列表
	 * 
	 * @return 面额区间信息列表
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
	 * 获取接口商名称
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
	 * 获取月度奖励规则信息列表
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
	 * 获取剩余冲正次数
	 * 
	 * @return 面额区间信息列表
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
	 * 添加面额信息
	 * 
	 * @param bpForm
	 * @return 记录数
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
	 * 添加奖励信息
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
	 * 删除面额信息
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
	 * 删除月度奖励规则
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
	 * 获取面额信息(单个)
	 * 
	 * @return 面额信息
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
	 * 更新面额
	 * 
	 * @param prodForm
	 *            面额信息
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
	 * 获取区域列表
	 * 
	 * @return 区域列表
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
	 * 获取区域列表面额
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
	 * 获取分配接口商名称
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
	 * 获取面额信息(单个)
	 * 
	 * @return 面额信息
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
	 * 获取第三方接口绑定信息列表
	 * 
	 * @return 信息列表
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
						temp[1] = "移动";
					} else if ("1".equals(temp[1])) {
						temp[1] = "联通";
					} else if ("2".equals(temp[1])) {
						temp[1] = "电信";
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
	 * 删除第三方绑定信息
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
	 * 更新第三方绑定信息
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
	 * 获取接口列表
	 * 
	 * @return 区域列表
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
	 * 获取当前用户mac验证状态
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
	 * 获取当前用户mac验证状态
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
	 * 添加第三方接口绑定信息
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
	 * 获取佣金
	 * 
	 * @return 面额信息列表
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
						temp[2] = "移动";
					} else if ("0".equals(temp[2])) {
						temp[2] = "电信";
					} else if ("1".equals(temp[2])) {
						temp[2] = "联通";
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
	 * 修改 佣金信息
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
	 * 修改 佣金信息
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
	 * 修改 小额佣金信息
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
	 * 获取面额
	 * 
	 * @return 区域列表
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
	 * 添加佣金明细
	 * 
	 * @param bpForm
	 * @return 记录数
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
	 * 添加接口商佣金明细
	 * 
	 * @param bpForm
	 * @return 记录数
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
	 * 添加小额佣金明细
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
	 * 获取业务类型
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
	 * 删除佣金信息
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
	 * 添加综合业务
	 * 
	 * @param bpForm
	 * @return 记录数
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
	 * 获取业务类型
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
	 * 获取充值面额
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
	 * 获取接口表
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
	 * 添加接口对应表
	 * @param pid 
	 * @param moneyValues 
	 * @return 记录数
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
			Log.error("添加接口对应表 异常："+ex);
			return 1;
		} finally {
			if(dbService!=null)
				dbService.close();	
		}
	}

	/**
	 * 获取接口详情
	 * 
	 * @param type
	 * @return 编号 省份编号 省份名称 运营商类型 接口id 接口名字
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
	 * 获取接口详情
	 * 
	 * @param type
	 * @param cm_one 
	 * @return 编号 省份编号 省份名称 运营商类型 接口id 接口名字
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
	 * 修改接口对应关系表
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
	 * 获取本省佣金详情
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
	 * 获取非直营综合业务佣金详情
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
	 * 分配佣金
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
	 * 代理商查看佣金明细
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
	 * 代理商获取非直营综合业务佣金详情
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
	 * 根据号段获取号码省份运营商类型
	 * 
	 * @param num
	 * @return 省份编号 运营商类型 号码详情
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
	 * 根据号码获取用户姓名
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
	 * 根据号段获取号码省份运营商类型
	 * 
	 * @param num
	 * @param dbService
	 * @return 省份编号 运营商类型
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
	 * 殴飞供货充值
	 * 
	 * @param phone
	 *            电话号码
	 * @param orderID
	 *            订单号
	 * @param userno
	 *            代办点系统编号
	 * @param userPid
	 *            用户所属省份编号
	 * @param fee
	 *            交易金额
	 * @param areacode
	 *            区号
	 * @param login
	 *            代办点登陆账号
	 * @param parentid
	 *            父节点id
	 * @return 充值状态
	 */
	public int ghFill(String phone, String orderID, String userno,
			String userPid, String fee, String areacode, String login,
			int parentid) {
		DBService dbService = null;
		try {
			dbService = new DBService();
			double mon = FloatArith.yun2li(fee);
			String[] str = getPhoneInfo(phone.substring(0, 7), dbService);
			if (null == str) {// 号码所属省份编码 运营商类型
				return -1;
			}
			String[] result = getEmploy(dbService, str[1], userPid, str[0],
					userno, "0", (int) (mon / 1000.0), parentid,0);
			if (null == result) {
				return -1;
			}
			String flag = "0";// 考虑供货是代理商下级
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
	 * 根据省份运营商获取相应接口
	 * 
	 * @param pid
	 * @param phonetype
	 * @param dbService
	 * @return 接口id
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
	 * 获取佣金比例
	 * 
	 * @param db
	 * @param phonetype
	 *            号码类型
	 * @param userPid
	 *            用户所属省份
	 * @param phonePid
	 *            号码所属省份
	 * @param userno
	 *            用户系统编号
	 * @param flag
	 *            1 直营 0非直营
	 * @param payfee
	 *            金额 元
	 * @param parentid
	 *            父节点id
	 * @param groups 对外接口佣金组             
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
			if ("1".equals(flag)) {// 直营
				sql = "select value from sys_employ2 where pid=" + phonePid
						+ " and type=" + phonetype + " and cm_id='" + cm_id
						+ "'";
			} else if ("2".equals(flag)) {// 接口商
				sql = "select value from sys_tpemploy where pid=" + phonePid
						+ " and type=" + phonetype + " and cm_id='" + cm_id
						+ "' and groups="+groups;
			} else {// 非直营
				if (!userPid.equals(phonePid)) {
					sql = "select value from sys_employ1 where pid=" + phonePid
							+ " and type=" + phonetype + " and cm_id='" + cm_id
							+ "'";
				} else {// 本省
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
	 * 中石化组装消息发送business进行电信业务处理
	 * 
	 * @param parentID
	 *            父节点id
	 * @param userno
	 *            用户系统编号
	 * @param phonePid
	 *            号码归属省份
	 * @param userPid
	 *            用户归属省份
	 * @param phonetype
	 *            号码类型
	 * @param phone
	 *            号码
	 * @param seqNo
	 *            流水号
	 * @param fee
	 *            交易金额
	 * @param str
	 *            佣金数组
	 * @param areaCode
	 *            区号
	 * @return 0 成功 1 充值消息发送失败 -1 系统繁忙 -2 无对应充值接口 2 处理中 3失败
	 * @throws Exception
	 */
	public int zshDxFill(String parentID, String userno, String phonePid,
			String userPid, String phonetype, String phone, String seqNo,
			double fee, String areaCode, String login, String numType,
			String serialNo, DBService db) throws Exception {
		//一分钟内不允许重复交易
		synchronized (lock) {
			if(MemcacheConfig.getInstance().get1(phone+fee+"repeat")){
				Log.info(phone+"-"+fee+" 一分钟内存在该交易");
				return 10;
			}else{
				MemcacheConfig.getInstance().add(phone+fee+"repeat", phone);
			}
		}
		String tradeNo = "";
		int interfaceID = -1;
		DBService dbService =null;
		//根据面额，广东省，电信，获取接口id
		try {
			dbService=new DBService();
			String sql="SELECT interid FROM sys_gddxinterfacemaping ORDER BY id ASC LIMIT 0,1";
			interfaceID = dbService.getInt(sql);
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;//系统繁忙
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
			tradeNo = "09015"; // 广东迅源电信
			break;
		case 19:
			tradeNo = "09028";// 广东君宝电信
			serialNo = Tools.getNow3()+((char)(new Random().nextInt(26) + (int)'A'))+((int)(Math.random()*10000)+10000);
			break;
		}
		
		int facctfee = 0;
		int parentfee = 0;
		String userRebat = DBConfig.getInstance().getGdUserRebate();
		String parentRebat = DBConfig.getInstance().getGdParentRebat();
		TradeMsg msg = new TradeMsg();
		msg.setSeqNo(seqNo);
		msg.setFlag("0");// 有内容
		msg.setMsgFrom2("0103");
		msg.setServNo("00");
		msg.setTradeNo(tradeNo);
		Map<String, String> content = new HashMap<String, String>();
		content.put("phone", phone);
		content.put("phoneNum", phone);
		content.put("userNo", userno);
		content.put("parentID", parentID);
		content.put("isline", "1");// 是否直营标示 0标示非直营 1标示直营
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
			return 1;// 发送消息失败,充值失败
		}
		saveBarcodeAndOrder(1, seqNo, db); // 保存条码及订单
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
			return 2;// 处理中
		}
		String code = rsMsg.getRsCode();
		if ("103".equals(code) || "002".equals(code)
				|| "045".equals(code)) {
			// 删除条形码编号
			delBarcodeAndOrder(seqNo, db);
		}
		if ("000".equals(code)) {
			return 0;// 成功
		}else if("102".equals(code)){
			return -5;//余额不足
		} else if("101".equals(code)){
			return 6;//订单重复
		}else if("002".equals(code) || "001".equals(code)){// 失败
			return 1;//失败
		}else {
			return 2;//处理中 103系统繁忙 003超时，008卡单 009异常  105异常 
		}
	}

	/**
	 * 组装消息发送business进行业务处理
	 * 
	 * @param parentID
	 *            父节点id
	 * @param userno
	 *            用户系统编号
	 * @param phonePid
	 *            号码归属省份
	 * @param userPid
	 *            用户归属省份
	 * @param phonetype
	 *            号码类型
	 * @param phone
	 *            号码
	 * @param seqNo
	 *            流水号
	 * @param fee
	 *            交易金额
	 * @param str
	 *            佣金数组
	 * @param areaCode
	 *            区号
	 * @return 0 成功 1 充值消息发送失败 -1 系统繁忙 -2 无对应充值接口 2 处理中 3失败
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
		if ("1".equals(flag)) {// 直营
			facctfee = (int) FloatArith.mul(fee, 1 - FloatArith.div(Double
					.valueOf(userRebat), 100));
		} else {// 非直营
			facctfee = (int) FloatArith.mul(fee, 1 - FloatArith.div(Double
					.valueOf(userRebat), 100));
			parentfee = (int) FloatArith.mul(fee, FloatArith.div(Double
					.valueOf(parentRebat), 100));
		}
		TradeMsg msg = new TradeMsg();
		msg.setSeqNo(seqNo);
		msg.setFlag("0");// 有内容
		msg.setMsgFrom2("0103");
		msg.setServNo("00");
		msg.setTradeNo(tradeNo);
		Map<String, String> content = new HashMap<String, String>();
		content.put("phone", phone);
		content.put("userNo", userno);
		content.put("parentID", parentID);
		content.put("isline", flag);// 是否直营标示 0标示非直营 1标示直营
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
			return 1;// 发送消息失败,充值失败
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
			return 2;// 处理中
		}
		String code = rsMsg.getRsCode();
		if ("000".equals(code)) {
			return 0;// 成功
		} else {
			return 3;// 失败
		}
	}
//	/**移动充值
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
//		String userRebat= DBConfig.getInstance().getGdUserRebate();//佣金配置;
//		String parentRebat = DBConfig.getInstance().getGdParentRebat();//父节点佣金
//		
//		String tradeNo = "09014";
//		int facctfee = 0;
//		int parentfee = 0;
//		
//		String flag = AcctBillBean.getStatus(parentID + "") == true ? "1" : "0";
//		if ("1".equals(flag)) {// 直营
//			facctfee = (int) FloatArith.mul(fee, 1 - FloatArith.div(Double
//					.valueOf(userRebat), 100));
//		} else {// 非直营
//			facctfee = (int) FloatArith.mul(fee, 1 - FloatArith.div(Double
//					.valueOf(userRebat), 100));
//			parentfee = (int) FloatArith.mul(fee, FloatArith.div(Double
//					.valueOf(parentRebat), 100));
//		}
////		if ("09014".equals(tradeNo)) {// 易迅充值
////			String orderID = com.wlt.webm.tool.Tools.getStreamDate()
////			+ com.wlt.webm.tool.Tools.buildRandom(2);
////			return yiKaHuiService(msg, ip, orderID,tradeNo,seqNo);
//		return -1;
//	}
	/** 省联通 tcp 充值
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
		String userRebat= DBConfig.getInstance().getGdltUserRebate();//佣金配置;
		String parentRebat = DBConfig.getInstance().getGdltParentRebat();//父节点佣金
		
		int facctfee = 0;
		int parentfee = 0;
		
		String flag = AcctBillBean.getStatus(parentID + "") == true ? "1" : "0";
		if ("1".equals(flag)) {// 直营
			facctfee = (int) FloatArith.mul(fee, 1 - FloatArith.div(Double
					.valueOf(userRebat), 100));
		} else {// 非直营
			facctfee = (int) FloatArith.mul(fee, 1 - FloatArith.div(Double
					.valueOf(userRebat), 100));
			parentfee = (int) FloatArith.mul(fee, FloatArith.div(Double
					.valueOf(parentRebat), 100));
		}
		TradeMsg msg = new TradeMsg();
		msg.setSeqNo(seqNo);
		msg.setFlag("0");// 有内容
		msg.setMsgFrom2("0103");
		msg.setServNo("00");
		msg.setTradeNo("09022");
		Map<String, String> content = new HashMap<String, String>();
		content.put("phoneNum", phone);
		content.put("userNo", userno);
		content.put("parentID", parentID);
		content.put("isline", flag);// 是否直营标示 0标示非直营 1标示直营
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
			return 1;// 发送消息失败,充值失败
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
			return 2;// 处理中
		}
		String code = rsMsg.getRsCode();
		if ("000".equals(code)) {
			return 0;// 成功
		}else if("102".equals(code)){
			return 5;//余额不足
		}else if("001".equals(code) || "002".equals(code) || "101".equals(code)) {
			return 1;// 失败
		}else if("103".equals(code) || "009".equals(code) || "105".equals(code)){
			return 3;//异常
		}else if("045".equals(code)){
			return 4;//三分钟重复
		}else{ //008
			return 2;//处理中
		}
	}
	/**
	 * 组装消息发送business进行业务处理
	 * 
	 * @param parentID
	 *            父节点id
	 * @param userno
	 *            用户系统编号
	 * @param phonePid
	 *            号码归属省份
	 * @param userPid
	 *            用户归属省份
	 * @param phonetype
	 *            号码类型
	 * @param phone
	 *            号码
	 * @param seqNo
	 *            流水号
	 * @param fee
	 *            交易金额
	 * @param str
	 *            佣金数组
	 * @param areaCode
	 *            区号
	 * @param login 
	 * @param login 
	 * @param serialNo 
	 * @return 0 成功 1 充值消息发送失败 -1 系统繁忙 -2 无对应充值接口 2 处理中 3失败
	 * @throws Exception
	 */
	public int XYdxFill(String parentID, String userno, String phonePid,
			String userPid, String phonetype, String phone, String seqNo,
			double fee, String areaCode, String login, String numType,
			String serialNo,String gh) throws Exception {
		if("01".equals(numType))//单一
		{
			numType="000001";
		}
		if("02".equals(numType))//宽带
		{
			numType="000003";
		}
		if("03".equals(numType)){//综合
			numType="000004";
		}
		
		String tradeNo = "";
		int facctfee = 0;
		int parentfee = 0;
		int interfaceID = -1;
		DBService dbService =null;
		String[] employFee =null;
		String flag = AcctBillBean.getStatus(parentID + "") == true ? "1" : "0";
		//根据面额，广东省，电信，获取接口id
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
			return -1;//系统繁忙
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
			tradeNo = "09015"; // 广东迅源电信
			break;
		case 19:
			tradeNo = "09028";// 广东君宝电信
			serialNo = Tools.getNow3()+((char)(new Random().nextInt(26) + (int)'A'))+((int)(Math.random()*10000)+10000);
			break;
		}
		
		/**以前根据配置文件计算佣金 
		String userRebat= DBConfig.getInstance().getGdUserRebate();//佣金配置;
		String parentRebat = DBConfig.getInstance().getGdParentRebat();//父节点佣金
		if ("1".equals(flag)) {// 直营
			facctfee = (int) FloatArith.mul(fee, 1 - FloatArith.div(Double
					.valueOf(userRebat), 100));
		} else {// 非直营
			facctfee = (int) FloatArith.mul(fee, 1 - FloatArith.div(Double
					.valueOf(userRebat), 100));
			parentfee = (int) FloatArith.mul(fee, FloatArith.div(Double
					.valueOf(parentRebat), 100));
		}
		*/
		//新的计算佣金方法
		if ("1".equals(flag)) {
			facctfee = (int) FloatArith.mul(fee, 1 - FloatArith.div(
					Double.valueOf(employFee[0]), 100));
		} else {
			double a = FloatArith.div(Double.valueOf(employFee[0]), 100);// 总佣金比
			double b = FloatArith.mul(fee, a);// 总佣金
			double c = FloatArith.mul(b, FloatArith.div(Double
					.valueOf(employFee[1]), 100));// 代办点应得佣金
			facctfee = (int) FloatArith.sub(fee, c);// 应扣金额
			parentfee = (int) FloatArith.sub(b, c);// 上级节点应得佣金
		}
		//新的获取佣金方法结束
		TradeMsg msg = new TradeMsg();
		msg.setSeqNo(seqNo);
		msg.setFlag("0");// 有内容
		msg.setMsgFrom2("0103");
		msg.setServNo("00");
		msg.setTradeNo(tradeNo);
		Map<String, String> content = new HashMap<String, String>();
		content.put("phoneNum", phone);
		content.put("userNo", userno);
		content.put("parentID", parentID);
		content.put("isline", flag);// 是否直营标示 0标示非直营 1标示直营
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
			return 1;// 发送消息失败,充值失败
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
			return 2;// 处理中
		}
		String code = rsMsg.getRsCode();
		if ("000".equals(code)) {
			return 0;// 成功
		}else if("102".equals(code)){
			return -5;//余额不足
		} else if("101".equals(code)){
			return 6;//订单重复
		}else if("002".equals(code) || "001".equals(code)){// 失败
			return 3;//失败
		}else {
			return 2;//处理中 103 系统繁忙
		}
	}


	/**
	 * 接口商 调用 备用方法
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
		if("01".equals(numType))//单一
		{
			numType="000001";
		}
		if("02".equals(numType))//宽带
		{
			numType="000003";
		}
		if("03".equals(numType)){//综合
			numType="000004";
		}
		
		String userRebat= DBConfig.getInstance().getGdUserRebate();//佣金配置;
		String parentRebat = DBConfig.getInstance().getGdParentRebat();//父节点佣金
		String tradeNo = "";
		int facctfee = 0;
		int parentfee = 0;
		
		int interfaceID = -1;
		DBService dbService =null;
		//根据面额，广东省，电信，获取接口id
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
			return -1;//系统繁忙
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
			tradeNo = "09015"; // 广东迅源电信
			break;
		case 19:
			tradeNo = "09028";// 广东君宝电信
			serialNo = Tools.getNow3()+((char)(new Random().nextInt(26) + (int)'A'))+((int)(Math.random()*10000)+10000);
			break;
		}
		
		String flag = AcctBillBean.getStatus(parentID + "") == true ? "1" : "0";
		if ("1".equals(flag)) {// 直营
			facctfee = (int) FloatArith.mul(fee, 1 - FloatArith.div(Double
					.valueOf(userRebat), 100));
		} else {// 非直营
			facctfee = (int) FloatArith.mul(fee, 1 - FloatArith.div(Double
					.valueOf(userRebat), 100));
			parentfee = (int) FloatArith.mul(fee, FloatArith.div(Double
					.valueOf(parentRebat), 100));
		}
		TradeMsg msg = new TradeMsg();
		msg.setSeqNo(seqNo);
		msg.setFlag("0");// 有内容
		msg.setMsgFrom2("0103");
		msg.setServNo("00");
		msg.setTradeNo(tradeNo);
		Map<String, String> content = new HashMap<String, String>();
		content.put("phoneNum", phone);
		content.put("userNo", userno);
		content.put("parentID", parentID);
		content.put("isline", flag);// 是否直营标示 0标示非直营 1标示直营
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
			return 1;// 发送消息失败,充值失败
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
			return 2;// 处理中
		}
		String code = rsMsg.getRsCode();
		if ("000".equals(code)) {
			return 0;// 成功
		}else if("102".equals(code)){
			return -5;//余额不足
		} else if("101".equals(code)){
			return 6;//订单重复
		}else if("002".equals(code) || "001".equals(code)){// 失败
			return 3;//失败
		}else {
			return 2;//处理中 103 系统繁忙
		}
	}
	
	/**
	 * 全国电信固话充值
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
		//一分钟内不允许重复交易
		synchronized (lock) {
			if(MemcacheConfig.getInstance().get1(phone+fee+"repeat")){
				Log.info(phone+"-"+fee+" 一分钟内存在该交易");
				return 10;
			}else{
				MemcacheConfig.getInstance().add(phone+fee+"repeat", phone);
			}
		}
		int interfaceID = 6;
		String tradeNo = "09013";// 世纪银通 四川
		int facctfee = 0;
		int parentfee = 0;
		String serialNo = seqNo;
		
		if (fee > 0 && fee < 30000) {
			if ("1".equals(flag)) {// 直营
				facctfee = Integer.parseInt(str[0]);
			} else {
				facctfee = Integer.parseInt(str[0]);
				parentfee = Integer.parseInt(str[1]);
			}
		} else {
			if ("1".equals(flag)) {// 直营
				facctfee = (int) FloatArith.mul(fee, 1 - FloatArith.div(Double
						.valueOf(str[0]), 100));
			} else if (!userPid.equals(phonePid)) {// 非直营 非本省
				TenpayXmlPath tenpayxmlpath = new TenpayXmlPath();
				facctfee = (int) FloatArith.mul(fee, 1 - FloatArith.div(Double
						.valueOf(str[0]), 100));
				parentfee = (int) FloatArith
						.mul(fee, FloatArith.div(Double.valueOf(DBConfig
								.getInstance().getOtherRebate()), 100));
			} else {// 非直营 本省
				double a = FloatArith.div(Double.valueOf(str[0]), 100);// 总佣金比
				double b = FloatArith.mul(fee, a);// 总佣金
				double c = FloatArith.mul(b, FloatArith.div(Double
						.valueOf(str[1]), 100));// 代办点应得佣金
				facctfee = (int) FloatArith.sub(fee, c);// 应扣金额
				parentfee = (int) FloatArith.sub(b, c);// 上级节点应得佣金
			}
		}
		TradeMsg msg = new TradeMsg();
		msg.setSeqNo(seqNo);
		msg.setFlag("0");// 有内容
		msg.setMsgFrom2("0103");
		msg.setServNo("00");
		msg.setTradeNo(tradeNo);
		Map<String, String> content = new HashMap<String, String>();
		content.put("phoneNum", phone);
		content.put("userNo", userno);
		content.put("parentID", parentID);
		content.put("isline", flag);// 是否直营标示 0标示非直营 1标示直营
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
		
		Log.info("全国电信固话返销所需订单号:" + serialNo);
		content.put("serialNo", serialNo);
		content.put("termType", "0");
		msg.setContent(content);
		
		// 世纪银通 四川充值
		String orderID = com.wlt.webm.tool.Tools.getStreamDate()
		+ com.wlt.webm.tool.Tools.getCMPayNO()
		+ com.wlt.webm.tool.Tools.buildRandom(2);
		return qgdxghcz(msg, ip, orderID,tradeNo);
		
	}
	
	/**
	 * 组装消息发送business进行业务处理
	 * 
	 * @param parentID
	 *            父节点id
	 * @param userno
	 *            用户系统编号
	 * @param phonePid
	 *            号码归属省份
	 * @param userPid
	 *            用户归属省份
	 * @param phonetype
	 *            号码类型
	 * @param phone
	 *            号码
	 * @param seqNo
	 *            流水号
	 * @param fee
	 *            交易金额
	 * @param str
	 *            佣金数组
	 * @param areaCode
	 *            区号
	 * @param dbService
	 *            数据库服务
	 * @param login
	 *            登陆账号
	 * @param ip
	 * @param gh
	 * @param flag
	 *            是否直营
	 * @return 0 成功 1 充值消息发送失败 -1 系统繁忙 -2 无对应充值接口 2 处理中 3失败
	 */
	public int nationFill(String parentID, String userno, String phonePid,
			String userPid, String phonetype, String phone, String seqNo,
			double fee, String[] str, String areaCode, DBService dbService,
			String login, String ip, String gh, String flag) {
		//一分钟内不允许重复交易
		if(!"OF".equals(gh) && gh==null)
		{
			synchronized (lock) {
				if(MemcacheConfig.getInstance().get1(phone+fee+"repeat")){
					Log.info(phone+"-"+fee+" 一分钟内存在该交易");
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
			tradeNo = "09001"; // 广东移动
			serialNo = "XY0315012880537" + Tools.getNow3().substring(2)
					+ Tools.buildRandom(2) + Tools.buildRandom(3);
			break;
		case 4:
			tradeNo = "01013";// 广东联通
			serialNo = "XY0418682022602" + Tools.getNow3().substring(2)
					+ Tools.buildRandom(2) + Tools.buildRandom(3); // 广东联通
			break;
		case 5:
			tradeNo = "01001"; // 华瑞达电信
			break;
		case 1:
			tradeNo = "01000";// 全国
			break;
		case 6:
			tradeNo = "09013";// 世纪银通 四川
			break;
		case 7:
			tradeNo="09012";//华鸿
			serialNo = "wh09012" + Tools.getNow3().substring(6)
			+ Tools.buildRandom(2) + Tools.buildRandom(3); //华鸿移动
			break;
		case 8:
			tradeNo = "09014";// 北京易迅
			break;
		case 11:
			tradeNo="09016";//正和
			serialNo = "zh09016" + Tools.getNow3().substring(6)
			+ Tools.buildRandom(2) + Tools.buildRandom(3); //浙江正和移动
			break;
		case 12:
			tradeNo="09018";//易百米
//			serialNo = "yb09018" + Tools.getNow3().substring(6)
//			+ Tools.buildRandom(2) + Tools.buildRandom(3); //易百米移动
			break;
		case 13:
			tradeNo = "09020";// 亿快
			break;
		case 14://恒通 tcp  移动
			tradeNo="09021";
			serialNo = ((int)(Math.random()*1000)+1000)+""+((char)(new Random().nextInt(26) + (int)'A'))+""+((int)(Math.random()*100)+100);
			break;
		case 15://省联通  tcp 
			tradeNo="09022";
			serialNo = Tools.getNow3()+((char)(new Random().nextInt(26) + (int)'A'))+((int)(Math.random()*10000)+10000);
			break;
		case 17://劲峰接口  http
			tradeNo = "09025";
			break;
		case 18://君宝移动 tcp
			tradeNo="09026";
			serialNo = Tools.getNow3()+((char)(new Random().nextInt(26) + (int)'A'))+((int)(Math.random()*10000)+10000);
			break;
		case 22://银盛 移动
			tradeNo="09032";
			serialNo = "0000"+((char)(new Random().nextInt(26) + (int)'a'))+""+((int)(Math.random()*1000)+1000)+""+((char)(new Random().nextInt(26) + (int)'A'))+""+((int)(Math.random()*10000)+10000)+((char)(new Random().nextInt(26) + (int)'A'));
			break;
		case 24://乐充移动
			tradeNo = "09034";
			break;
		case 28://连胜接口
			tradeNo="09036";
			break;
		case 30://和包移动接口
			tradeNo="09037";
			break;
		case 40://正邦充值接口
			tradeNo="09038";
			break;
		default:
			Log.info("充值号码:"+phone+",未能匹配到接口,,,,");
			return 1; // 失败
		}
		if (fee > 0 && fee < 30000) {
			if ("1".equals(flag)) {// 直营
				facctfee = Integer.parseInt(str[0]);
			} else {
				facctfee = Integer.parseInt(str[0]);
				parentfee = Integer.parseInt(str[1]);
			}
		} else {
			if ("1".equals(flag)) {// 直营
				facctfee = (int) FloatArith.mul(fee, 1 - FloatArith.div(Double
						.valueOf(str[0]), 100));
			} else if (!userPid.equals(phonePid)) {// 非直营 非本省
				TenpayXmlPath tenpayxmlpath = new TenpayXmlPath();
				facctfee = (int) FloatArith.mul(fee, 1 - FloatArith.div(Double
						.valueOf(str[0]), 100));
				parentfee = (int) FloatArith
						.mul(fee, FloatArith.div(Double.valueOf(DBConfig
								.getInstance().getOtherRebate()), 100));
			} else {// 非直营 本省
				double a = FloatArith.div(Double.valueOf(str[0]), 100);// 总佣金比
				double b = FloatArith.mul(fee, a);// 总佣金
				double c = FloatArith.mul(b, FloatArith.div(Double
						.valueOf(str[1]), 100));// 代办点应得佣金
				facctfee = (int) FloatArith.sub(fee, c);// 应扣金额
				parentfee = (int) FloatArith.sub(b, c);// 上级节点应得佣金
			}
		}
		TradeMsg msg = new TradeMsg();
		msg.setSeqNo(seqNo);
		msg.setFlag("0");// 有内容
		msg.setMsgFrom2("0103");
		msg.setServNo("00");
		msg.setTradeNo(tradeNo);
		Map<String, String> content = new HashMap<String, String>();
		content.put("phoneNum", phone);
		content.put("userNo", userno);
		content.put("parentID", parentID);
		content.put("isline", flag);// 是否直营标示 0标示非直营 1标示直营
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
			content.put("numType","1");//省联通
		}
		if (null != gh && "OF".equals(gh)) {
			content.put("gh", gh);
		}
		Log.info("返销所需订单号:" + seqNo);
		if(tradeNo.equals("09018")){
			content.put("serialNo", seqNo);
		}else{
			content.put("serialNo", serialNo);
		}
		content.put("termType", "0");
		msg.setContent(content);
		if ("01000".equals(tradeNo)) {// 一卡惠充值
			String orderID = com.wlt.webm.tool.Tools.getStreamDate()
					+ com.wlt.webm.tool.Tools.getCMPayNO()
					+ com.wlt.webm.tool.Tools.buildRandom(2);
			return yiKaHuiService(msg, ip, orderID,tradeNo,seqNo);
		}else if ("09014".equals(tradeNo)) {// 易迅充值
			String orderID = com.wlt.webm.tool.Tools.getStreamDate()
			+ com.wlt.webm.tool.Tools.buildRandom(2);
			return yiKaHuiService(msg, ip, orderID,tradeNo,seqNo);
		} else if ("09013".equals(tradeNo)) {// 世纪银通 四川充值
			String orderID = com.wlt.webm.tool.Tools.getStreamDate()
			+ com.wlt.webm.tool.Tools.getCMPayNO()
			+ com.wlt.webm.tool.Tools.buildRandom(2);
			return yiKaHuiService(msg, ip, orderID,tradeNo,seqNo);
		} else if ("09020".equals(tradeNo)) {//亿快
			String orderID = "yikuai"+com.wlt.webm.tool.Tools.getNow3()+com.wlt.webm.tool.Tools.buildRandom(6);
			return yiKaHuiService(msg, ip, orderID,tradeNo,seqNo);
		} else if ("09025".equals(tradeNo)) {//劲峰
			String orderID ="09025"+Tools.getNow3()+((int)(Math.random()*1000)+1000)+""+((char)(new Random().nextInt(26) + (int)'A'))+""+((int)(Math.random()*100)+100);
			return yiKaHuiService(msg, ip, orderID,tradeNo,seqNo);
		} else if ("09034".equals(tradeNo)) {//乐充
			return yiKaHuiService(msg, ip,"",tradeNo,seqNo);
		} else if ("09036".equals(tradeNo)) {//连胜接口
			return yiKaHuiService(msg, ip,"",tradeNo,seqNo);
		} else if ("09037".equals(tradeNo)) {//和包移动接口
			String orderID = Tools.getNow3()+((int)(Math.random()*1000)+1000)+((char)(new Random().nextInt(26) + (int)'a'))+((char)(new Random().nextInt(26) + (int)'A'));
			return yiKaHuiService(msg, ip,orderID,tradeNo,seqNo);
		} else if ("09038".equals(tradeNo)) {//正邦充值接口
			String orderID = Tools.getNow3()+((int)(Math.random()*1000)+1000)+((char)(new Random().nextInt(26) + (int)'a'))+((char)(new Random().nextInt(26) + (int)'A'))+"zb";
			return yiKaHuiService(msg, ip,orderID,tradeNo,seqNo);
		}else {
			if (!PortalSend.getInstance().sendmsg(msg)) {
				return 1;// 发送消息失败,充值失败
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
				return 2;// 处理中
			}
			String code1 = rsMsg.getRsCode();
			if ("000".equals(code1)) {
				if (interfaceID == 3 || interfaceID == 14 || interfaceID==18) {
					return Integer.parseInt(rsMsg.getContent().get("mon"));
				} else {
					return 0;// 成功
				}
			} else if ("102".equals(code1)){
				return -5;//余额不足
			}else if ("002".equals(code1)|| "045".equals(code1) || "001".equals(code1)) {
				return 1; // 失败
			} else if ("008".equals(code1)) {
				return 3;// 处理中
			}else if("101".equals(code1)){
				return 6;//订单号重复
			} else {
				return 5;// 异常
			}
		}
	}
	
	/**
	 *  佛山移动 接口
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
		msg.setFlag("0");// 有内容
		msg.setMsgFrom2("0103");
		msg.setServNo("00");
		msg.setTradeNo(tradeNo);
		Map<String, String> content = new HashMap<String, String>();
		content.put("phoneNum", phone);
		content.put("userNo", userno);
		content.put("parentID", parentID);
		content.put("isline", flag);// 是否直营标示 0标示非直营 1标示直营
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
			return 1;// 发送消息失败,充值失败
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
			return 2;// 处理中
		}
		String code1 = rsMsg.getRsCode();
		if ("000".equals(code1)) {
			return 0;// 成功
		} else if ("102".equals(code1)){
			return 4;//资金账户销户或者余额不足
		}else if ("002".equals(code1) || "001".equals(code1)) {
			return 1; // 失败
		} else if ("008".equals(code1)) {
			return 2;// 处理中
		}else if("101".equals(code1)){
			return 3;//订单号重复
		} else {
			return 100;// 异常
		}
	}

	/**
	 * 组装中石化消息发送business进行业务处理
	 * 
	 * @param parentID
	 *            父节点id
	 * @param userno
	 *            用户系统编号
	 * @param phonePid
	 *            号码归属省份
	 * @param userPid
	 *            用户归属省份
	 * @param phonetype
	 *            号码类型
	 * @param phone
	 *            号码
	 * @param seqNo
	 *            流水号
	 * @param fee
	 *            交易金额
	 * @param areaCode
	 *            区号
	 * @param dbService
	 *            数据库服务
	 * @param login
	 *            登陆账号
	 * @param ip
	 * @param gh
	 * @return 条码编号 集合0 成功 1 充值消息发送失败 -1 系统繁忙 -2 无对应充值接口 2 处理中 3失败
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
		//一分钟内不允许重复交易
		synchronized (lock) {
			if(MemcacheConfig.getInstance().get1(phone+fee+"repeat")){
				Log.info(phone+"-"+fee+" 一分钟内存在该交易");
				map.put("state", 10);
				map.put("barcode",null);
				return map;
			}else{
				MemcacheConfig.getInstance().add(phone+fee+"repeat", phone);
			}
		}
	
		// 广东省联通、移动、全国电信
		if (Integer.parseInt(phonePid) == 35
				|| Integer.parseInt(phonetype) == 0) {
			map.put("barcode", 1);
		}
		// 全国移动
		else if (Integer.parseInt(phonePid) != 35
				&& Integer.parseInt(phonetype) == 1) {
			map.put("barcode", 2);
		}
		// 全国联通
		else if (Integer.parseInt(phonePid) != 35
				&& Integer.parseInt(phonetype) == 2) {
			map.put("barcode", 3);
		}
		// QQ充值
		else {
			map.put("barcode", 4);
		}
		try {
//			if(Tools.isJC(phone)){//揭阳 潮汕走年年接口
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
			tradeNo = "09001"; // 广东移动
			serialNo = "XY0315012880537" + Tools.getNow3().substring(2)
					+ Tools.buildRandom(2) + Tools.buildRandom(3);
			break;
		case 4:
			tradeNo = "01013";// 广东联通
			serialNo = "XY0418682022602" + Tools.getNow3().substring(2)
					+ Tools.buildRandom(2) + Tools.buildRandom(3); // 广东联通
			break;
		case 5:
			tradeNo = "01001"; // 广东电信
			break;
		case 6:
			tradeNo = "09013";// 世纪银通 四川
			break;
		case 1:
			tradeNo = "01000";// 全国
			break;
		case 7:
			tradeNo="09012";//华鸿
			serialNo = "wh09012" + Tools.getNow3().substring(6)
			+ Tools.buildRandom(2) + Tools.buildRandom(3); //华鸿移动
			break;
		case 8:
			tradeNo = "09014";// 北京易迅
			break;
//		case 9://讯源电信
//			tradeNo="09015";
//			serialNo = "wh09015" + Tools.getNow3().substring(6)
//			+ Tools.buildRandom(2) + Tools.buildRandom(3); 
//			break;
		case 11:
			tradeNo="09016";//正和
			serialNo = "zh09016" + Tools.getNow3().substring(6)
			+ Tools.buildRandom(2) + Tools.buildRandom(3); //浙江正和移动
			break;
		case 12:
			tradeNo="09018";//易百米
//			serialNo = "yb09018" + Tools.getNow3().substring(6)
//			+ Tools.buildRandom(2) + Tools.buildRandom(3); //浙江正和移动
			break;
		case 13:
			tradeNo = "09020";// 亿快
			break;
		case 14://恒通 tcp  移动
			tradeNo="09021";
			serialNo = ((int)(Math.random()*1000)+1000)+""+((char)(new Random().nextInt(26) + (int)'A'))+""+((int)(Math.random()*100)+100);
			break;
		case 15://省联通  tcp 
			tradeNo="09022";
			serialNo = Tools.getNow3()+((char)(new Random().nextInt(26) + (int)'A'))+((int)(Math.random()*10000)+10000);
			break;
		case 17://劲峰接口  http
			tradeNo = "09025";
			break;
//		case 18://君宝移动 tcp
//			tradeNo="09026";
//			serialNo = Tools.getNow3()+((char)(new Random().nextInt(26) + (int)'A'))+((int)(Math.random()*10000)+10000);
//			break;
		default:
			Log.info("zsh充值号码:"+phone+",未能匹配到接口,,,,");
			map.put("state", 1);//失败
		}
		TradeMsg msg = new TradeMsg();
		msg.setSeqNo(seqNo);
		msg.setFlag("0");// 有内容
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
			content.put("numType","1");//省联通
		}
		Log.info("返销所需订单号:" + seqNo);
		if(tradeNo.equals("09018")){
			content.put("serialNo", seqNo);
		}else{
			content.put("serialNo", serialNo);
		}
		content.put("termType", "0");
		msg.setContent(content);
		if ("01000".equals(tradeNo)) {// 一卡惠充值
			String orderID = com.wlt.webm.tool.Tools.getStreamDate()
			+ com.wlt.webm.tool.Tools.getCMPayNO()
			+ com.wlt.webm.tool.Tools.buildRandom(2);
			map.put("state", yiKaHuiService(msg, ip, orderID,tradeNo,seqNo));
			barSeqNo = orderID;
			saveBarcodeAndOrder(Integer.parseInt(map.get("barcode") + ""),
					barSeqNo, dbService); // 保存条码及订单
			return map;
		}else if ("09014".equals(tradeNo)) {// 易迅充值
			String orderID = com.wlt.webm.tool.Tools.getStreamDate()
			+ com.wlt.webm.tool.Tools.buildRandom(2)
			+com.wlt.webm.tool.Tools.buildRandom(2);
			map.put("state", yiKaHuiService(msg, ip, orderID,tradeNo,seqNo));
			barSeqNo = orderID;
			saveBarcodeAndOrder(Integer.parseInt(map.get("barcode") + ""),
					barSeqNo, dbService); // 保存条码及订单
			return map;
		} else if ("09013".equals(tradeNo)) {// 世纪银通 四川充值
			String orderID = com.wlt.webm.tool.Tools.getStreamDate()
			+ com.wlt.webm.tool.Tools.getCMPayNO()
			+ com.wlt.webm.tool.Tools.buildRandom(2);
			map.put("state", yiKaHuiService(msg, ip, orderID,tradeNo,seqNo));
			barSeqNo = orderID;
			saveBarcodeAndOrder(Integer.parseInt(map.get("barcode") + ""),
					barSeqNo, dbService); // 保存条码及订单
			return map;
		} else if ("09020".equals(tradeNo)) {// 亿快 
			String orderID = com.wlt.webm.tool.Tools.getStreamDate()
			+ com.wlt.webm.tool.Tools.getCMPayNO()
			+ com.wlt.webm.tool.Tools.buildRandom(2);
			map.put("state", yiKaHuiService(msg, ip, orderID,tradeNo,seqNo));
			barSeqNo = orderID;
			saveBarcodeAndOrder(Integer.parseInt(map.get("barcode") + ""),
					barSeqNo, dbService); // 保存条码及订单
			return map;
		} else if ("09025".equals(tradeNo)) {// 劲峰 全国三网
			String orderID = "09025"+Tools.getNow3()+((int)(Math.random()*1000)+1000)+""+((char)(new Random().nextInt(26) + (int)'A'))+""+((int)(Math.random()*100)+100);
			map.put("state", yiKaHuiService(msg, ip, orderID,tradeNo,seqNo));
			barSeqNo = orderID;
			saveBarcodeAndOrder(Integer.parseInt(map.get("barcode") + ""),
					barSeqNo, dbService); // 保存条码及订单
			return map;
		}else {
			barSeqNo = seqNo;
			saveBarcodeAndOrder(Integer.parseInt(map.get("barcode") + ""),
					barSeqNo, dbService); // 保存条码及订单
			if (!PortalSend.getInstance().sendmsg(msg)) {
				map.put("state", 1);// 发送消息失败,充值失败
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
				map.put("state", 2);// 处理中
				return map;
			}
			String code1 = rsMsg.getRsCode();
			if ("101".equals(code1) || "102".equals(code1)
					|| "103".equals(code1) || "002".equals(code1)
					|| "045".equals(code1)) {
				// 删除条形码编号
				delBarcodeAndOrder(barSeqNo, dbService);
			}
			if ("000".equals(code1)) {
				if (interfaceID == 3) {
					map.put("state", Integer.parseInt(rsMsg.getContent().get(
							"mon")));// 处理中
				} else {
					map.put("state", 0);// 成功
				}
			} else if ("102".equals(code1) || "002".equals(code1)
					|| "045".equals(code1) || "001".equals(code1)) {
				map.put("state", 1);
				 // 失败
			} else if ("008".equals(code1)) {
				map.put("state", 3);// 处理中
			} else {
				map.put("state", 5);// 异常
			}
			return map;
		}
	}

	/**
	 * 删除条形码
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
	 * 保存订单编号及条码编号
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
	 * 获取增值业务佣金比例
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
		if ("1".equals(flag)) {// 直营
			sql = "select value from sys_employ3 where code='" + code + "'"
					+ " and flag=1";
		} else {// 非直营
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
	 * 内部业务处理
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
	 * 内部业务处理中处理
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
	 * 内部业务成功处理
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
	 * 内部业务失败处理
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
			 * 收支未分开的 db.update("update " + orderTableName +
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
			// 收支分开后
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
					"Q币充值(失败退费)", 0, newtime, userleft + facctFee, seqNo,
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
						"Q币充值(交易撤销)", 0, newtime, userleft3 - parentFee, seqNo,
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
	 * 根据用户id查询 账号
	 * 
	 * @param userid
	 * @return 用户平台账户
	 */
	public String getFacctByUserID(DBService db, int userid) {
		String sql = "select fundacct from wht_facct where user_no=(select user_no from sys_user where user_id="
				+ userid + ")";
		try {
			return db.getString(sql);
		} catch (Exception ex) {
			Log.error("根据用户系统编号获取账户失败:", ex);
			return null;
		}
	}

	/**
	 * 全国电信固话充值
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
		Log.info("全国电信固话充值...");
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
		// 判断余额、账户合法
		com.wlt.webm.scpdb.DBService db = null;
		try {
			db = new com.wlt.webm.scpdb.DBService();
			FundAcctDao fd = new FundAcctDao(db);
			if (fd.checkSeq(orderID)) {
				return 1;// 流水号重复
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
				return -5;// 资金账户销户或者余额不足
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
					acctType, "全国电信固话充值", 0, time,
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
						Integer.parseInt(parentFee), 15, "下级交易返佣", 0, time,
						parentLeft + Integer.parseInt(parentFee), orderID,
						parentFacct + "02", 2 };
				db.logData(15, acctObj1, tableName);
			}
			db.commit();
			String rs="";
			// 调用充值

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
			return 3;// 系统繁忙
		} finally {
			if (null != db) {
				db.close();
			}
		}
	}
	
	/**
	 * 一卡惠内部业务处理
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
		String miaoshu="全国话费充值";
		int acctType;
		Log.info("开始处理一卡惠业务...");
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
		// 判断余额、账户合法
		com.wlt.webm.scpdb.DBService db = null;
		try {
			db = new com.wlt.webm.scpdb.DBService();
			FundAcctDao fd = new FundAcctDao(db);
			if (fd.checkSeq(seqNo1)) {
				return 6;// 流水号重复
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
			if("10001".equals(tradeNo)){//宽带流量充值
				tradetype = "0009";
				acctType = 27;
				productId=(String) content.get("productId");
				realPrice=(String) content.get("realPrice");
				aCTIVITYID=(String) content.get("aCTIVITYID");
				String gp=(String) content.get("groups"); 
				String name=(String) content.get("name"); 
				writeoff=name.indexOf("mb")>0?name:name+"mb";
				miaoshu="联通流量";
				String cm="0";
//				int num=db.getInt("SELECT PRICE_NUM FROM wht_flowproduct where product_id='"+productId+"'");
//				if(!realPrice.equals(num*100+"")){
//					return 8;//虚假面额
//				}
				tradeFee=db.getString("select price*1000 from wht_flowprice where type=2 and name='"+name+"'");
				if(null==tradeFee){
					return 8;//虚假流量面额
				}
				if("1".equals(isline)){//1直营,接口商
					float value=db.getFloat("select value from sys_tpemploy_Flow where type=2 and groups='"+gp+"' and cm_addr="+cm);
					facctFee=FloatArith.mul(Double.parseDouble(tradeFee),1-FloatArith.div(value, 100))+"";
				}else{//代理点
					facctFee=""+Double.parseDouble(tradeFee)*Double.parseDouble(DBConfig.getInstance().getLiantong_flow());
				}
			}
			if("10005".equals(tradeNo)){//北京流量充值
				tradetype = "0009";
				acctType = 27;
				productId=(String) content.get("name");
				realPrice=(String) content.get("realPrice");
				aCTIVITYID=(String) content.get("aCTIVITYID");
				String gp=(String) content.get("groups");
				String name=(String) content.get("name"); 
				writeoff=name.indexOf("mb")>0?name:name+"mb";
				miaoshu="北京联通流量";
				String cm="0";
				tradeFee=db.getString("select price*1000 from wht_flowprice where type=2 and name='"+name+"'");
				if(null==tradeFee){
					return 8;//虚假流量面额
				}
				if("1".equals(isline)){//1直营,接口商
					float value=db.getFloat("select value from sys_tpemploy_Flow where type=2 and groups='"+gp+"' and cm_addr="+cm);
					facctFee=FloatArith.mul(Double.parseDouble(tradeFee),1-FloatArith.div(value, 100))+"";
				}else{//代理点
					facctFee=""+Double.parseDouble(tradeFee)*Double.parseDouble(DBConfig.getInstance().getLiantong_flow());
				}
			}
			//思空流量 || 大汉三通流量 || 乐流流量 || 连连流量 ||鼎信 ||智信
			if("10006".equals(tradeNo) || "10008".equals(tradeNo) || "10009".equals(tradeNo) || "10010".equals(tradeNo)||"10013".equals(tradeNo)|| "10014".equals(tradeNo)|| "10015".equals(tradeNo)){
				String cm="0";//0 全国   1 省内
				//phoneType 0电信  1移动 2联通
				switch (Integer.parseInt(phoneType)) {
				case 0://电信
					tradetype = "0011";
					acctType = 29;
					miaoshu="电信流量";
					break;
				case 1://移动
					cm=phonePid;
					tradetype = "0010";
					acctType = 28;
					miaoshu="移动流量";
					break;
				case 2://联通
					tradetype = "0009";
					acctType = 27;
					miaoshu="联通流量";
					break;
				default:
					break;
				}
				String gp=(String) content.get("groups"); //接口商魔板编号
				String name=(String) content.get("name"); 
				writeoff=name.indexOf("mb")>0?name:name+"mb";
				tradeFee=db.getString("select price*1000 from wht_flowprice where type="+phoneType+" and name='"+name+"'");
				if("1".equals(isline)){//1直营,接口商
					float value=db.getFloat("select value from sys_tpemploy_Flow where type="+phoneType+" and groups='"+gp+"' and cm_addr="+cm);
					facctFee=FloatArith.mul(Double.parseDouble(tradeFee),1-FloatArith.div(value, 100))+"";
				}else{//代理点
					facctFee=""+Double.parseDouble(tradeFee)*Double.parseDouble(DBConfig.getInstance().getLiantong_flow());
				}
			}
			if("10007".equals(tradeNo)){//北京移动流量
				tradetype = "0010";
				acctType = 28;
				productId=(String) content.get("name");
				realPrice=(String) content.get("realPrice");
				aCTIVITYID=(String) content.get("aCTIVITYID");
				String gp=(String) content.get("groups");
				String name=(String) content.get("name"); 
				writeoff=name.indexOf("mb")>0?name:name+"mb";
				miaoshu="北京移动流量";
				String cm=phonePid;
				tradeFee=db.getString("select price*1000 from wht_flowprice where type=1 and name='"+name+"'");
				if(null==tradeFee){
					return 8;//虚假流量面额
				}
				if("1".equals(isline)){//1直营,接口商
					float value=db.getFloat("select value from sys_tpemploy_Flow where type=1 and groups='"+gp+"' and cm_addr="+cm);
					facctFee=FloatArith.mul(Double.parseDouble(tradeFee),1-FloatArith.div(value, 100))+"";
				}else{//代理点
					facctFee=""+Double.parseDouble(tradeFee)*Double.parseDouble(DBConfig.getInstance().getLiantong_flow());
				}
			}
			if("10011".equals(tradeNo)){//正邦流量充值接口
				tradetype = "0010";
				acctType = 28;
				productId=(String) content.get("name");
				realPrice=(String) content.get("realPrice");
				aCTIVITYID=(String) content.get("aCTIVITYID");
				String gp=(String) content.get("groups");
				String name=(String) content.get("name"); 
				writeoff=name.indexOf("mb")>0?name:name+"mb";
				miaoshu="正邦移动流量";
				String cm=phonePid;
				tradeFee=db.getString("select price*1000 from wht_flowprice where type=1 and name='"+name+"'");
				if(null==tradeFee){
					return 8;//虚假流量面额
				}
				if("1".equals(isline)){//1直营,接口商
					float value=db.getFloat("select value from sys_tpemploy_Flow where type=1 and groups='"+gp+"' and cm_addr="+cm);
					facctFee=FloatArith.mul(Double.parseDouble(tradeFee),1-FloatArith.div(value, 100))+"";
				}else{//代理点
					facctFee=""+Double.parseDouble(tradeFee)*Double.parseDouble(DBConfig.getInstance().getLiantong_flow());
				}
			}
			if("10012".equals(tradeNo)){//自由充移动流量充值
				checkAccount=GDFreeTrafficCharge.getTransIDO();//流水号
				tradetype = "0010";
				acctType = 28;
				productId=(String) content.get("name");
				realPrice=(String) content.get("realPrice");
				aCTIVITYID=(String) content.get("aCTIVITYID");
				String gp=(String) content.get("groups");
				String name=(String) content.get("name"); 
				writeoff=name.indexOf("mb")>0?name:name+"mb";
				miaoshu="自由移动流量";
				String cm=phonePid;
				tradeFee=db.getString("select price*1000 from wht_flowprice where type=1 and name='"+name+"'");
				if(null==tradeFee){
					return 8;//虚假流量面额
				}
				if("1".equals(isline)){//1直营,接口商
					float value=db.getFloat("select value from sys_tpemploy_Flow where type=1 and groups='"+gp+"' and cm_addr="+cm);
					facctFee=FloatArith.mul(Double.parseDouble(tradeFee),1-FloatArith.div(value, 100))+"";
				}else{//代理点
					facctFee=""+Double.parseDouble(tradeFee)*Double.parseDouble(DBConfig.getInstance().getLiantong_flow());
				}
			}
			/**
			if("10013".equals(tradeNo)){// 广东流量贩
				tradetype = "0009";
				acctType = 27;
				productId=(String) content.get("name");
				realPrice=(String) content.get("realPrice");
				aCTIVITYID=(String) content.get("aCTIVITYID");
				String gp=(String) content.get("groups");
				String name=(String) content.get("name"); 
				writeoff=name.indexOf("mb")>0?name:name+"mb";
				miaoshu="广东流量贩";
				String cm="0";
				tradeFee=db.getString("select price*1000 from wht_flowprice where type=2 and name='"+name+"'");
				if(null==tradeFee){
					return 8;//虚假流量面额
				}
				if("1".equals(isline)){//1直营,接口商
					float value=db.getFloat("select value from sys_tpemploy_Flow where type=2 and groups='"+gp+"' and cm_addr="+cm);
					facctFee=FloatArith.mul(Double.parseDouble(tradeFee),1-FloatArith.div(value, 100))+"";
				}else{//代理点
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
				return -5;// 资金账户销户或者余额不足
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
						Double.parseDouble(parentFee), 15, "下级交易返佣", 0, time,
						parentLeft + Double.parseDouble(parentFee), seqNo1,
						parentFacct + "02", 2 };
				db.logData(15, acctObj1, tableName);
			}
			db.commit();
			db.setAutoCommit(true);
			String rs="";
			// 调用充值
			if ("01000".equals(tradeNo)) {// 一卡惠充值
//				rs =getRandomDigit(); 
				rs=yikaHuiFill(orderID, time, tradeFee, phone, ip);
			}else if ("09014".equals(tradeNo)) {// 易迅充值
//				rs =getRandomDigit(); 
				YxBean yb=new YxBean();
				rs=yb.yxFill(phone, orderID, Integer.parseInt(tradeFee)/1000+"", getYXPhoneInfo(phone),content.get("gh")+"".trim())+"";
			}else if ("09013".equals(tradeNo)) {// 世纪银通 四川充值
//				rs =getRandomDigit();
				rs=scdx.sjytczFile(orderID, time, tradeFee, phone,content.get("gh")+"".trim(),phoneType,"2|")+"";
			}else if ("09020".equals(tradeNo)){//亿块
//				rs =getRandomDigit();
				rs=NineteenRecharge.getOrderState(orderID,phone,(Integer.parseInt(tradeFee)/1000)+"".trim());
			}else if ("09025".equals(tradeNo)){//劲峰
//				rs =getRandomDigit();
				rs=JingFengInter.RechargeFile(phone,tradeFee,orderID,phoneType,content.get("gh")+"".trim())+"".trim();
			}else if ("10001".equals(tradeNo)){//联通流量充值
//				rs =getRandomDigit();
				rs=HttpFillOP.fillProduct(orderID, productId, phone, Long.parseLong(realPrice), aCTIVITYID)+"";
			}else if("10005".equals(tradeNo)){//北京联通流量
//				rs =getRandomDigit();
				String sa=Flow.BeiJin_Flow(phone,productId);
				String[] strings=sa.split("#");
				if("0".equals(strings[0])){
					rs="0";
					//将相应的 订单号 记录入库
					db.update("update wht_orderform_" + time.substring(2, 6)+" set writeoff='"+strings[1]+"' where tradeserial='"+seqNo1+"'");
				}else{
					rs=strings[0];
				}
			}else if("09034".equals(tradeNo)){//乐充移动
//				rs =getRandomDigit();
				rs=MobileRecharge.Recharge(seqNo1, phone, (Integer.parseInt(tradeFee)/1000)+"".trim())+"";
			}else if("10006".equals(tradeNo)){//思空流量充值  回调机制
//				rs =getRandomDigit();
				rs=Flow_SiKong.Recharge(seqNo1, phone,content.get("name")+"M");
			}else if("09036".equals(tradeNo)){//连胜接口
//				rs =getRandomDigit();
				rs=Mobile_Interface.Recharge(seqNo1, phone, (Integer.parseInt(tradeFee)/1000)+"".trim(), phoneType);
			}else if("10007".equals(tradeNo)){//北京移动流量
//				rs =getRandomDigit();
				String wString=Webservices_Flow.flow_Result(seqNo1,phone,content.get("name")+"".trim());
				rs=wString.split("#")[0];
				if("0".equals(rs)){
					db.update("update wht_orderform_" + time.substring(2, 6)+" set writeoff='"+wString.split("#")[1]+"' where tradeserial='"+seqNo1+"'");
				}
			}else if("09037".equals(tradeNo)){//和包移动
//				rs = getRandomDigit();
				rs = HeBao.Order_Recharge(orderID, phone,Integer.parseInt(tradeFee)/10+"",content.get("gh")+"".trim());
			}else if("10008".equals(tradeNo)){//大汉三通流量
//				rs = getRandomDigit();
				rs=FlowsService.CZ_Orders(phone, content.get("name")+"", seqNo1);
			}else if("10009".equals(tradeNo)){//乐流流量
//				rs = getRandomDigit();
				rs=""+LeliuFlowCharge.leliuFill(seqNo1,phone,""+content.get("name"),phoneType);
			}else if("10010".equals(tradeNo)){//连连流量
//				rs = getRandomDigit();
				rs=LianlianFlows.CZ_Orders(phone, content.get("name")+"", seqNo1);
			}else if("09038".equals(tradeNo)){//正邦话费充值
//				rs = "-1";//getRandomDigit();
				rs=Mobile_Zb_Inter.cz(phone,Integer.parseInt(tradeFee)/10+"",orderID,content.get("gh")+"".trim())+"".trim();
			}else if("10011".equals(tradeNo)){//正邦流量
//				rs = getRandomDigit();
				rs=Mobile_Zb_Inter.cz_flow(phone, content.get("name")+"", seqNo1);
			}else if("10012".equals(tradeNo)){//自由充移动流量充值
//				rs = getRandomDigit();
				rs=GDFreeTrafficCharge.testEC001(phone,checkAccount,content.get("name")+"")+"";
			}else if("10013".equals(tradeNo)){//广东流量贩
//				rs = getRandomDigit();
				rs=LlFan.llFanFill(seqNo1,phone,content.get("name")+"","2","")+"";
			}else if("10014".equals(tradeNo)){//鼎信流量
//				rs = getRandomDigit();
				rs=dingxFlow.fillFlow(seqNo1, phone,content.get("name")+"","0","0","0",time);
			}else if("10015".equals(tradeNo)){//智信流量
//				rs = getRandomDigit();
				rs=ZhiXin.ZxPreOrders(phone,content.get("name")+"",seqNo1,time,"4");
			}
			
			//供货  一卡会，亿快,乐充移动,连胜接口  直接返回订单结果
			if(content.get("gh")!=null || "09020".equals(tradeNo) || "01000".equals(tradeNo) || "09034".equals(tradeNo) ||"09036".equals(tradeNo)){
				returnDeal(time,rs, seqNo1, userNo, acct1, userFacct, isline,
						acct2, parentFacct, facctFee, parentFee, acctType);
			}else{
				if("-1".equals(rs)){//订单提交失败，，直接退费
					// 流量，不给上游返佣
					if(tradetype.equals("0009")||tradetype.equals("0011")||tradetype.equals("0010")){
						isline="1";
					}
					returnDeal(time,"-1", seqNo1, userNo, acct1, userFacct, isline,
							acct2, parentFacct, facctFee, parentFee, acctType);
				}
			}
			if (rs.equals("0")) {
				//如果是直接流量接口，直接修改订单状态 
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
			Log.error("处理全国业务失败:"+e.toString() );
			return 3;// 系统繁忙
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
	 * 一卡惠充值
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
	 * 一卡惠返回内部处理
	 * 
	 * @param time
	 * @param code
	 * @param seqNo
	 * @param userno
	 * @param acct1
	 * @param userfacct
	 * @param parentid
	 *            1标示是直营 0标示非直营
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
					Log.info("回调业务处理,tradeserial:"+seqNo+",userno:"+userno+",订单状态为终极状态,不处理,订单状态为,state:"+ss);
					return ;
				}
				db.setAutoCommit(false);
				if ("0".equals(code)) {// cg
					db.update("update " + orderTableName
							+ " set state=0,chgtime='"+Tools.getNow3()+"' where tradeserial='" + seqNo
							+ "' and userno='" + userno + "'");
					Log.info("回调业务处理,tradeserial:"+seqNo+",userno:"+userno+",回调结果为 成功,订单状态修改为,0");
				} else if ("-1".equals(code)) {// 失败1accountleft
					//如果是Q币则先判断拆单表电信有无成功记录  1
					String pd=db.getString("SELECT state FROM "+cdName+" WHERE  buyid=16 AND oldorderid='"+seqNo+"'");
					if(null!=pd&&pd.equals("0")){
						db.update("update " + orderTableName
								+ " set state=4 where tradeserial='" + seqNo
								+ "' and userno='" + userno + "'");
						Log.info("回调业务处理,tradeserial:"+seqNo+",userno:"+userno+",回调结果为 失败,拆单表中有一笔成功的,不进行退费业务处理,修改状态为 4");
						return ;
					}
					//1
//					synchronized (lock1) {
//						conSA=(conSA+1);
//						System.out.println(conSA);
						// 收支分开的
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
								Double.parseDouble(facctFee), acctType, "失败退费", 0,
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
									Integer.parseInt(parentFee), 15, "交易撤销", 0,
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
						Log.info("回调业务处理,tradeserial:"+seqNo+",userno:"+userno+",回调结果为 失败,进行退费业务处理,修改状态为 1");
					// 收支分开结束
				} else {
					db.update("update " + orderTableName
							+ " set state=4 where tradeserial='" + seqNo
							+ "' and userno='" + userno + "'");
					Log.info("回调业务处理,tradeserial:"+seqNo+",userno:"+userno+",回调结果 未知,修改状态为 4");
				}
				db.commit();
			} catch (Exception e) {
				db.rollback();
				e.printStackTrace();
				Log.error("一卡惠处理返回异常:" + seqNo + " " + e.toString());
			} finally {
				if (null != db) {
					db.close();
				}
			}
			return ;
	}

	/**
	 * 获得慧付款绑定信息
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
	 * 获得慧付款绑定信息
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
					18, "月度佣金奖励", 0, time, left1, serial, facct, 2 };
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
	 * 易迅
	 * 根据号段获取号码省份运营商类型
	 * 
	 * @param num
	 * @return 号码类型 省份名称
	 */
	public String[] getYXPhoneInfo(String phone) {
		DBService dbService = null;
		try {
			dbService = new DBService();
			String sql="SELECT (CASE WHEN a.phone_type=0 THEN 'DX' " +
					"WHEN a.phone_type=1 THEN 'YD' ELSE 'UN' END) " +
					"AS phone_type,(CASE WHEN b.sa_name='内蒙古自治区' " +
					"THEN LEFT(b.sa_name,3) WHEN b.sa_name='黑龙江省' " +
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
	 * Q币 订单提交成功  或  订单成功修改状态
	 * @param db
	 * @param tableName
	 * @param check
	 * @param seqNo
	 * @param userno
	 * @param interfaceType 1广信(订单交易成功，状态改为0)   2冠名(订单提交成功，状态改为4)
	 */
	public void innerQQSuccessDeal(DBService db, String tableName, String check,
			String seqNo, String userno,String interfaceType) {
		try {
			if("2".equals(interfaceType)){//冠名订单提交成功
				db.update("update " + tableName + " set state=4,writecheck='"
						+ check + "' where tradeserial='" + seqNo
						+ "' and userno='" + userno + "'");
			}else if("1".equals(interfaceType)){//广信订单交易成功
				db.update("update " + tableName + " set state=0,writecheck='"
						+ check + "' where tradeserial='" + seqNo
						+ "' and userno='" + userno + "'");
			}else if("89".equals(interfaceType)){//直通车
				db.update("update " + tableName + " set state=0,writecheck='"
						+ check + "' where tradeserial='" + seqNo
						+ "' and userno='" + userno + "'");
			}else{
				Log.info("Q币未知接口类型,不修改订单状态,,,");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 易百米查询
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
//		+ Tools.buildRandom(2) + Tools.buildRandom(3); //易百米移动
		TradeMsg msg = new TradeMsg();
		msg.setSeqNo(seqNo);
		msg.setFlag("0");// 有内容
		msg.setMsgFrom2("0103");
		msg.setServNo("00");
		msg.setTradeNo(tradeNo);
		Map<String, String> content = new HashMap<String, String>();
		content.put("inTime", tradetime);
		content.put("orderId", orderid);
		Log.info("查询所需订单号:" + orderid);
		msg.setContent(content);
			if (!PortalSend.getInstance().sendmsg(msg)) {
				return "4";// 发送消息失败,充值失败
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
				return "0";//查询成功
			}
			else if(code1.equals("001")){
				return "1";//查询失败
			}
			else  if(code1.equals("008")){
				return "4";//查询处理中
			}
			return "4";
		}
	
	
	/**
	 * 揭阳潮汕地区获取佣金比例
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
			if ("1".equals(flag)||"2".equals(flag)) {// 直营
			str=new String[]{DBConfig.getInstance().getJcydzy()};
			} else {// 非直营
				if (!userPid.equals(phonePid)) {
					str=new String[]{DBConfig.getInstance().getJcydzy()};
				} else {// 本省
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
	 * 添加阶梯组
	 * 
	 * @param bpForm
	 * @return 记录数
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
	 * 删除阶梯组
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
		String[] strs=new String[3]; //0额度 1当天交易金额  2剩余金额
		String sql="";
		String ordersql="";
		if("3".equals(tradeType))
		{
			//额度转移
			sql="SELECT reversalcount FROM sys_reversalcount WHERE user_no='"+userno+"' AND tradetype=3 AND reversalcount=0";//0 标示无权限 1标示有权限
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
				Log.error("额度转移获取权限异常，，，"+ex);
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
			//支付宝
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
			Log.error("获取限额异常，，，"+ex);
		}finally{
			if(null!=dbService)
				dbService.close();
		}
		return null;
	}

	/**
	 * 根据系统编号  获取账户余额
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
			Log.error("获取账户余额异常，，，系统编号:"+userno);
			return -1;
		}finally{
			if(null!=db){
				db.close();
			}
		}
		return returnMoney;
	}
	
	/**
	 * 根据tradeserial 获取 writeoff 字段
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
			Log.error("君宝冲正判断上游流水号，tradeserial="+tradeserial);
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
			Log.error("app 获取用户信息展示信息error,,ex"+ex);
		}finally{
			if(null!=db){
				db.close();
				db=null;
			}
		}
		return null;
	}
	
	/**
	 * 会付款T+0加款
	 * @param userno  用户系统编号
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
            	return 4;//未查询到相关数据
            }else if(Integer.parseInt(rs[1])<=2000){
            	return 5;//手续费不足不能加款
            }else{
            	long fee=Long.parseLong(rs[1]);
            	long facctfee=fee-2000;
            	String tableName = "wht_acctbill_" + time.substring(2,6);
            	long left=dbService.getLong("select accountleft from wht_childfacct where childfacct='"+rs[0]+"01'");
                Object[] obj={null,rs[0]+"01",com.wlt.webm.tool.Tools.getStreamSerial(userno),rs[0]+"01",time,fee,
                		facctfee,17,"慧付款T+0",0,time,left+facctfee,
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
			Log.error("慧付款加款失败id="+id+"  "+ex.toString());
			return 5;//异常
		} finally {
			if(dbService!=null){
				dbService.close();
			}
		}
	}

	/**
	 * 流量接口配置列表
	 * @return list
	 */
	public List<String[]> flow_config(){
		DBService db=null;
		try {
			db=new DBService();
			return db.getList("SELECT f.id,CASE f.operator_type WHEN 0 THEN '电信流量' WHEN 1 THEN '移动流量' WHEN 2 THEN '联通流量' END AS a,s.name,f.flow_interId,f.exp1,f.operator_type FROM sys_Flow f LEFT JOIN sys_interface s ON f.flow_interId=s.id order by f.operator_type,f.exp1+0 asc");
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
	 * 加载所有的流量 接口对应的面额
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
	 * 增加 修改 流量配置列表
	 * @param inter 流量接口id
	 * @param flowId 当前记录id 
	 * @param val 钱
	 * @param sertypes 业务类型  0电信  1移动  2联通
	 * @param flag  0增加  1修改
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
			if("1".equals(flag)){//修改
				return db.update("UPDATE sys_Flow SET flow_interId="+inter+",exp1="+val+",operator_type="+sertypes+" WHERE id="+flowId)>0?true:false;
			}
			if("0".equals(flag)){//增加
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
	 * 根据运营商类型和面额获取流量接口
	 * @param n
	 * @return 21 联通第一个接口 23 联通第二个接口
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
	 * 根据运营商类型获取流量接口  和 产品面额对应价格
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
	 * APP查询最近订单
	 * @param username
	 * @return
	 */
	public static ArrayList<String[]> getorders(String username){
		DBService db=null;
		try {
			db=new DBService();
			String tablename="wht_orderform_"+Tools.getNowMonth();
			return (ArrayList<String[]>) db.getList("SELECT tradeobject,fee/1000,tradefee/1000,tradetime,CASE WHEN state=0 THEN '成功' WHEN state=1 THEN '失败' ELSE  '处理中' END FROM "+tablename+" WHERE service='0009' AND user_name='"+username+"'and tradetime>='"+com.wlt.webm.tool.Tools.getOtherTime(-3)+"'");
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
	 * 根据运营商类型获取面额和接口
	 * @param type 运营商类型  电信 移动 联通
	 * @param areaId  省份地市
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
	 * 根据运营商类型 省份编码获取接口信息
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
			Log.error("根据运营商类型:"+phonetype+" 省份:"+areaid+"获取流量接口信息失败");
		}finally{
			if(db!=null){
				db.close();
				db=null;
			}
		}
		return 0;
	}
	
	/**
	 * 获取所有流量接口
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
			Log.error("获取流量接口信息失败");
		}finally{
			if(db!=null){
				db.close();
				db=null;
			}
		}
		return null;
	}
	
	/**
	 * 获取平台接口商信息
	 * @return
	 */
	public static List getAgents(){
		DBService db=null;
		try {
			db=new DBService();
			return db.getList("SELECT user_ename, user_no FROM sys_user a WHERE EXISTS(SELECT sr_id FROM sys_role b WHERE sr_type=4 AND a.user_role=b.sr_id)");
		} catch (Exception e) {
			e.printStackTrace();
			Log.error("获取接口商信息失败");
		}finally{
			if(db!=null){
				db.close();
				db=null;
			}
		}
		return null;
	}
}
