package com.wlt.webm.business.bean;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.wlt.webm.business.form.CheckAccountErrorLogForm;
import com.wlt.webm.db.DBService;
import com.wlt.webm.util.PageAttribute;
import com.wlt.webm.util.Tools;

/**
 * 对账单日志管理
 */
public class CheckAccountErrorLogBean {
	public String[] checkAccountErrorLogCount(String tableName,
			CheckAccountErrorLogForm checkAcountErrorLogForm) {
		DBService dbService = null;
		try {
			dbService = new DBService();
			StringBuffer paraBuffer = new StringBuffer();
			StringBuffer sql = new StringBuffer();
			sql.append(" select count(*) ");
			sql.append(" from " + tableName);
			sql.append(" where  1=1 ");
			if (null != checkAcountErrorLogForm.getBuyid()
					&& !"".equals(checkAcountErrorLogForm.getBuyid())) {
				sql
						.append(" and buyid = "
								+ checkAcountErrorLogForm.getBuyid());
				paraBuffer.append("&buyid="
						+ checkAcountErrorLogForm.getBuyid());
			}
			if (null != checkAcountErrorLogForm.getChecktime()
					&& !"".equals(checkAcountErrorLogForm.getChecktime())) {
				sql.append(" and checktime like '"
						+ checkAcountErrorLogForm.getChecktime().replaceAll(
								"-", "") + "%'");
				paraBuffer.append("&checktime="
						+ checkAcountErrorLogForm.getChecktime());
			} else {
				sql.append(" and checktime like '" + Tools.getYesterd() + "%'");
				checkAcountErrorLogForm.setChecktime(Tools.calDate());
				paraBuffer.append("&checktime=" + Tools.calDate());
			}
			String[] str = dbService.getStringArray(sql.toString());
			checkAcountErrorLogForm.setParamUrl(paraBuffer.toString());
			if (null == str[0] || "".equals(str[0])) {
				str = new String[] { "0", "0", "0" };
			}
			return str;
		} catch (Exception ex) {
			ex.printStackTrace();
			return new String[] { "0", "0", "0" };
		} finally {
			if(dbService!=null){
				dbService.close();
			}
		}
	}

	public List listCheckAccountErrorLog(String tableName,
			CheckAccountErrorLogForm checkAcountErrorLogForm, PageAttribute page)
			throws Exception {
		DBService dbService = null;
		try {
			dbService = new DBService();
			StringBuffer paraBuffer = new StringBuffer();
			StringBuffer sql = new StringBuffer();
			sql.append("select b.name,DATE_FORMAT(a.checktime,'%Y-%m-%d') AS checktime,DATE_FORMAT(a.checkRunTime,'%Y-%m-%d') AS checkRunTime,a.State,a.id from "
					+ tableName);
			sql.append(" a left join sys_interface b on a.buyid=b.id ");
			sql.append(" where  1=1 ");
			if (null != checkAcountErrorLogForm.getBuyid()
					&& !"".equals(checkAcountErrorLogForm.getBuyid())) {
				sql
						.append(" and a.buyid = "
								+ checkAcountErrorLogForm.getBuyid());
				paraBuffer.append("&buyid="
						+ checkAcountErrorLogForm.getBuyid());
			}
			if (null != checkAcountErrorLogForm.getChecktime()
					&& !"".equals(checkAcountErrorLogForm.getChecktime())) {
				sql.append(" and a.checktime like '"
						+ checkAcountErrorLogForm.getChecktime().replaceAll(
								"-", "") + "%'");
				paraBuffer.append("&checktime="
						+ checkAcountErrorLogForm.getChecktime());
			} else {
				sql.append(" and a.checktime like '" + Tools.getYesterd() + "%'");
				checkAcountErrorLogForm.setChecktime(Tools.calDate());
				paraBuffer.append("&checktime=" + Tools.calDate());
			}
			sql.append(" order by a.checktime desc");
			sql.append(" limit " + (page.getCurPage() - 1) * page.getPageSize()
					+ "," + page.getPageSize());
			List list = dbService.getList(sql.toString());
			checkAcountErrorLogForm.setParamUrl(paraBuffer.toString());
			for (Object tmp : list) {
				String[] temp = (String[]) tmp;
				if (null != temp[3] && !"".equals(temp[3])) {
					if ("0".equals(temp[3])) {
						temp[3] = "已对账,账平";
					} else if ("1".equals(temp[3])) {
						temp[3] = "已对账,账不平";
					} else if ("2".equals(temp[3])) {
						temp[3] = "未对账";
					}else if ("3".equals(temp[3])) {
						temp[3] = "重对账中..";
					}
				}
			}
			return list;
		} catch (Exception ex) {
			throw ex;
		}finally {
			if(dbService!=null){
				dbService.close();
			}
		}
	}

	public List listCheckAccountErrorLog_EXCEL(String tableName,
			CheckAccountErrorLogForm checkAcountErrorLogForm, PageAttribute page) throws Exception {
		DBService dbService = null;
		try {
			dbService = new DBService();
			StringBuffer paraBuffer = new StringBuffer();
			StringBuffer sql = new StringBuffer();
			sql.append("select b.name,DATE_FORMAT(a.checktime,'%Y-%m-%d') AS checktime,DATE_FORMAT(a.checkRunTime,'%Y-%m-%d') AS checkRunTime,a.State,a.id from "
					+ tableName);
			sql.append(" a left join sys_interface b on a.buyid=b.id ");
			sql.append(" where  1=1 ");
			if (null != checkAcountErrorLogForm.getBuyid()
					&& !"".equals(checkAcountErrorLogForm.getBuyid())) {
				sql
						.append(" and a.buyid = "
								+ checkAcountErrorLogForm.getBuyid());
				paraBuffer.append("&buyid="
						+ checkAcountErrorLogForm.getBuyid());
			}
			if (null != checkAcountErrorLogForm.getChecktime()
					&& !"".equals(checkAcountErrorLogForm.getChecktime())) {
				sql.append(" and a.checktime like '"
						+ checkAcountErrorLogForm.getChecktime().replaceAll(
								"-", "") + "%'");
				paraBuffer.append("&checktime="
						+ checkAcountErrorLogForm.getChecktime());
			} else {
				sql.append(" and a.checktime like '" + Tools.getYesterd() + "%'");
				checkAcountErrorLogForm.setChecktime(Tools.calDate());
				paraBuffer.append("&checktime=" + Tools.calDate());
			}
			sql.append(" order by a.checktime desc");
			List list = dbService.getList(sql.toString());
			checkAcountErrorLogForm.setParamUrl(paraBuffer.toString());
			for (Object tmp : list) {
				String[] temp = (String[]) tmp;
				if (null != temp[3] && !"".equals(temp[3])) {
					if ("0".equals(temp[3])) {
						temp[3] = "已对账,账平";
					} else if ("1".equals(temp[3])) {
						temp[3] = "已对账,账不平";
					} else if ("2".equals(temp[3])) {
						temp[3] = "未对账";
					}else if ("3".equals(temp[3])) {
						temp[3] = "重对账中..";
					}
				}
			}
			return list;
		} catch (Exception ex) {
			throw ex;
		}finally {
			if(dbService!=null){
				dbService.close();
			}
		}
	}

	public void changeCheckAccountErrorLog(
			CheckAccountErrorLogForm checkAcountErrorLogForm) throws Exception {
		DBService dbService = null;
		try {
			dbService = new DBService();
			String sql="update wht_accountErrorLog set State=3 where id="+checkAcountErrorLogForm.getId();
			dbService.update(sql);
		} catch (Exception ex) {
			throw ex;
		}finally {
			if(dbService!=null){
				dbService.close();
			}
		}
	}

}
