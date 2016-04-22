package com.wlt.webm.business.bean;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.wlt.webm.business.form.CheckAccountErrorForm;
import com.wlt.webm.db.DBService;
import com.wlt.webm.util.PageAttribute;
import com.wlt.webm.util.Tools;

/**
 * 对账管理
 */
public class CheckAccountErrorBean {
	/**
	 * 对账单列表
	 * @param tableName
	 * @param checkAcountErrorForm
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public List listCheckAccountError(String tableName,
			CheckAccountErrorForm checkAcountErrorForm, PageAttribute page) throws Exception {
		if (null != checkAcountErrorForm.getTradetime()
				&& !"".equals(checkAcountErrorForm.getTradetime())) {
			tableName = "wht_accountError_"
					+ (String) checkAcountErrorForm.getTradetime().replaceAll(
							"-", "").subSequence(2, 6);
		}
		DBService dbService = null;
		try {
			dbService = new DBService();
			StringBuffer paraBuffer = new StringBuffer();
			StringBuffer sql = new StringBuffer();
			sql.append("select a.tradeserial,a.externalserial,a.tradeobject," +
			"b.name,a.tradefee,DATE_FORMAT(a.tradetime,'%Y-%m-%d %k:%i:%s') AS `tradetime`," +
			"a.StateOne,a.StateTwo,a.Contrast_state,a.id");
			sql.append(" from " + tableName
					+ " a LEFT JOIN sys_interface b ON a.buyid=b.id ");
			sql.append(" where  1=1 ");
			if (null != checkAcountErrorForm.getBuyid()
					&& !"".equals(checkAcountErrorForm.getBuyid())) {
				sql.append(" and a.buyid = " + checkAcountErrorForm.getBuyid());
				paraBuffer.append("&buyid=" + checkAcountErrorForm.getBuyid());
			}
			if (null != checkAcountErrorForm.getTradetime()
					&& !"".equals(checkAcountErrorForm.getTradetime())) {
				sql.append(" and a.tradetime like '"
						+ checkAcountErrorForm.getTradetime().replaceAll("-", "")
						+ "%'");
				paraBuffer.append("&tradetime=" + checkAcountErrorForm.getTradetime());
			} else {
				sql.append(" and a.tradetime like '" + Tools.getYesterd()
						+ "%'");
				checkAcountErrorForm.setTradetime(new SimpleDateFormat("yyyy-MM-dd")
						.format(new Date()));
				paraBuffer.append("&tradetime=" + Tools.calDate());
			}
			sql.append(" order by a.tradetime desc");
			sql.append(" limit " + (page.getCurPage() - 1) * page.getPageSize()
					+ "," + page.getPageSize());
			List list = dbService.getList(sql.toString());
			checkAcountErrorForm.setParamUrl(paraBuffer.toString());
			for (Object tmp : list) {
				String[] temp = (String[]) tmp;
				if (null != temp[6] && !"".equals(temp[6])) {
					if ("0".equals(temp[6])) {
						temp[6] = "成功";
					} else if ("1".equals(temp[6])) {
						temp[6] = "失败";
					} else if ("4".equals(temp[6])) {
						temp[6] = "处理中";
					} else if ("5".equals(temp[6])) {
						temp[6] = "冲正";
					} else if ("6".equals(temp[6])) {
						temp[6] = "异常订单";
					}
				}
				if (null != temp[7] && !"".equals(temp[7])) {
					if ("0".equals(temp[7])) {
						temp[7] = "成功";
					} else if ("1".equals(temp[7])) {
						temp[7] = "失败";
					} else if ("2".equals(temp[7])) {
						temp[7] = "等待返回";
					} else if ("3".equals(temp[7])) {
						temp[7] = "订单存疑";
					}
				}
				if (null != temp[8] && !"".equals(temp[8])) {
					if ("0".equals(temp[8])) {
						temp[8] = "对比正常";
					} else if ("1".equals(temp[8])) {
						temp[8] = "状态不一致";
					} else if ("2".equals(temp[8])) {
						temp[8] = "对方无订单";
					} else if ("3".equals(temp[8])) {
						temp[8] = "我方无订单";
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
	 * 查询对账单条数
	 * @param tableName
	 * @param checkAcountErrorForm
	 * @return
	 */
	public String[] checkAccountErrorCount(String tableName,
			CheckAccountErrorForm checkAcountErrorForm) {
		if (null != checkAcountErrorForm.getTradetime()
				&& !"".equals(checkAcountErrorForm.getTradetime())) {
			tableName = "wht_accountError_"
					+ (String) checkAcountErrorForm.getTradetime().replaceAll(
							"-", "").subSequence(2, 6);
		}
		DBService dbService = null;
		try {
			dbService = new DBService();
			StringBuffer paraBuffer = new StringBuffer();
			StringBuffer sql = new StringBuffer();
			sql.append(" select sum(a.tradefee),count(*) ");
			sql.append(" from " + tableName
					+ " a LEFT JOIN sys_interface b ON a.buyid=b.id ");
			sql.append(" where  1=1 ");
			if (null != checkAcountErrorForm.getBuyid()
					&& !"".equals(checkAcountErrorForm.getBuyid())) {
				sql.append(" and a.buyid = " + checkAcountErrorForm.getBuyid());
				paraBuffer.append("&buyid=" + checkAcountErrorForm.getBuyid());
			}
			if (null != checkAcountErrorForm.getTradetime()
					&& !"".equals(checkAcountErrorForm.getTradetime())) {
				sql.append(" and a.tradetime like '"
						+ checkAcountErrorForm.getTradetime().replaceAll("-", "")
						+ "%'");
				paraBuffer.append("&tradetime=" + checkAcountErrorForm.getTradetime());
			} else {
				sql.append(" and a.tradetime like '" + Tools.getYesterd()
						+ "%'");
				checkAcountErrorForm.setTradetime(Tools.calDate());
				paraBuffer.append("&tradetime=" + Tools.calDate());
			}
			String[] str = dbService.getStringArray(sql.toString());
			checkAcountErrorForm.setParamUrl(paraBuffer.toString());
			if(null==str[0]||"".equals(str[0])){
				str=new String[]{"0","0","0"};
			}
			return str;
        } catch (Exception ex) {
            ex.printStackTrace();
            return new String[]{"0","0","0"};
        }finally {
			if(dbService!=null){
				dbService.close();
			}
		}
	}
	public List listCheckAccountError_EXCEL(String tableName,
			CheckAccountErrorForm checkAcountErrorForm, PageAttribute page) throws Exception {
		if (null != checkAcountErrorForm.getTradetime()
				&& !"".equals(checkAcountErrorForm.getTradetime())) {
			tableName = "wht_accountError_"
					+ (String) checkAcountErrorForm.getTradetime().replaceAll(
							"-", "").subSequence(2, 6);
		}
		DBService dbService = null;
		try {
			dbService = new DBService();
			StringBuffer paraBuffer = new StringBuffer();
			StringBuffer sql = new StringBuffer();
			sql.append("select a.tradeserial,a.externalserial,a.tradeobject," +
			"b.name,a.tradefee,DATE_FORMAT(a.tradetime,'%Y-%m-%d %k:%i:%s') AS `tradetime`," +
			"a.StateOne,a.StateTwo,a.Contrast_state");
			sql.append(" from " + tableName
					+ " a LEFT JOIN sys_interface b ON a.buyid=b.id ");
			sql.append(" where  1=1 ");
			if (null != checkAcountErrorForm.getBuyid()
					&& !"".equals(checkAcountErrorForm.getBuyid())) {
				sql.append(" and a.buyid = " + checkAcountErrorForm.getBuyid());
				paraBuffer.append("&buyid=" + checkAcountErrorForm.getBuyid());
			}
			if (null != checkAcountErrorForm.getTradetime()
					&& !"".equals(checkAcountErrorForm.getTradetime())) {
				sql.append(" and a.tradetime like '"
						+ checkAcountErrorForm.getTradetime().replaceAll("-", "")
						+ "%'");
				paraBuffer.append("&tradetime=" + checkAcountErrorForm.getTradetime());
			} else {
				sql.append(" and a.tradetime like '" + Tools.getYesterd()
						+ "%'");
				checkAcountErrorForm.setTradetime(Tools.calDate());
				paraBuffer.append("&tradetime=" + Tools.calDate());
			}
			sql.append(" order by a.tradetime desc");
			List list = dbService.getList(sql.toString());
			checkAcountErrorForm.setParamUrl(paraBuffer.toString());
			for (Object tmp : list) {
				String[] temp = (String[]) tmp;
				if (null != temp[6] && !"".equals(temp[6])) {
					if ("0".equals(temp[6])) {
						temp[6] = "成功";
					} else if ("1".equals(temp[6])) {
						temp[6] = "失败";
					} else if ("4".equals(temp[6])) {
						temp[6] = "处理中";
					} else if ("5".equals(temp[6])) {
						temp[6] = "冲正";
					} else if ("6".equals(temp[6])) {
						temp[6] = "异常订单";
					}
				}
				if (null != temp[7] && !"".equals(temp[7])) {
					if ("0".equals(temp[7])) {
						temp[7] = "成功";
					} else if ("1".equals(temp[7])) {
						temp[7] = "失败";
					} else if ("2".equals(temp[7])) {
						temp[7] = "等待返回";
					} else if ("3".equals(temp[7])) {
						temp[7] = "订单存疑";
					}
				}
				if (null != temp[8] && !"".equals(temp[8])) {
					if ("0".equals(temp[8])) {
						temp[8] = "对比正常";
					} else if ("1".equals(temp[8])) {
						temp[8] = "状态不一致";
					} else if ("2".equals(temp[8])) {
						temp[8] = "对方无订单";
					} else if ("3".equals(temp[8])) {
						temp[8] = "我方无订单";
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
	public List<String> getCheckAccError(String pid, String suffixName) throws Exception {
		DBService dbService = null;
		try {
			String tableName="wht_accountError_"+suffixName;
			dbService = new DBService();
			StringBuffer sql = new StringBuffer();
			sql.append("select a.tradeserial,a.externalserial,a.tradeobject," +
			"b.name,a.tradefee,DATE_FORMAT(a.tradetime,'%Y-%m-%d %k:%i:%s') AS `tradetime`," +
			"a.StateOne,a.StateTwo,a.Contrast_state");
			sql.append(" from " + tableName
					+ " a LEFT JOIN sys_interface b ON a.buyid=b.id ");
			sql.append(" where  1=1 and a.id="+pid);
			List list = dbService.getList(sql.toString());
			for (Object tmp : list) {
				String[] temp = (String[]) tmp;
				if (null != temp[6] && !"".equals(temp[6])) {
					if ("0".equals(temp[6])) {
						temp[6] = "成功";
					} else if ("1".equals(temp[6])) {
						temp[6] = "失败";
					} else if ("4".equals(temp[6])) {
						temp[6] = "处理中";
					} else if ("5".equals(temp[6])) {
						temp[6] = "冲正";
					} else if ("6".equals(temp[6])) {
						temp[6] = "异常订单";
					}
				}
				if (null != temp[7] && !"".equals(temp[7])) {
					if ("0".equals(temp[7])) {
						temp[7] = "成功";
					} else if ("1".equals(temp[7])) {
						temp[7] = "失败";
					} else if ("2".equals(temp[7])) {
						temp[7] = "等待返回";
					} else if ("3".equals(temp[7])) {
						temp[7] = "订单存疑";
					}
				}
				if (null != temp[8] && !"".equals(temp[8])) {
					if ("0".equals(temp[8])) {
						temp[8] = "对比正常";
					} else if ("1".equals(temp[8])) {
						temp[8] = "状态不一致";
					} else if ("2".equals(temp[8])) {
						temp[8] = "对方无订单";
					} else if ("3".equals(temp[8])) {
						temp[8] = "我方无订单";
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


}
