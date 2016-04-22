package com.wlt.webm.pccommon;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import oufei.OUConstat;
import oufei.OufeiTask;

import com.commsoft.epay.util.logging.Log;
import com.wlt.webm.db.DBService;
import com.wlt.webm.tool.Tools;

/**
 * 查询处于开启状态的，殴飞供货商，并将其加入定时任务
 * 
 * @author caiSJ
 * 
 */
public class OuFeighState {

	/**
	 * 查询处于开启状态的殴飞供货商,并将其加入定时任务
	 * 
	 * @return rsult
	 */
	@SuppressWarnings( { "unchecked", "unused" })
	public static void getSupplier() {
		DBService db = null;
		ArrayList<String[]> result = null;
		try {
			db = new DBService();
			String sql = "SELECT userno,userpid,areacode,login,parentid,oufeiID,oufeimoban,oufeisign FROM sys_oufei WHERE state =0 and isstart=0";
			result = (ArrayList<String[]>) db.getList(sql);
			if (null != result&&result.size()>0) {
				System.out.println("启动欧飞话费供货");
				for (String[] str : result) {
					OufeiTask task = new OufeiTask(str);
					new Timer().scheduleAtFixedRate(task, 0, 1 * 30 * 1000);
					OUConstat.tasks.put(str[4], task);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			Log.error("查询处于开启状态的殴飞供货商，并将其加入定时任务出错:" + e.toString());
			getSupplier();
		} finally {
			if (null != db) {
				db.close();
			}
		}
	}

	public static void setOne2UserTask() {
		Timer time = new Timer();
		OuFeighState o=new OuFeighState();
        time.scheduleAtFixedRate(o.new UserLimitTask(), 0, 30*60*1000);
	}

	/**
	 * @author Administrator
	 *将用户密码错误次数置为1
	 */
	class UserLimitTask extends TimerTask {
		@SuppressWarnings("unchecked")
		@Override
		public void run() {
			Log.info("启动 将用户密码错误次数置为1任务...");
			DBService db = null;
			try {
				db = new DBService();
				String sql = "SELECT userno FROM sys_userloginLimit WHERE errornum>1 and TIME<'"
						+ Tools.getNow() + "'";
				List<String> list = (List<String>) db.getStringList(sql);
				String update = "UPDATE sys_userloginLimit SET errornum=1,time=NULL WHERE userno=?";
				if (null != list && list.size() > 0) {
					PreparedStatement psmt1 = null;
					psmt1 = db.getConn().prepareStatement(update);
					int ps1 = 0;
					for (String str : list) {
						ps1++;
						psmt1.setString(1, str);
						psmt1.addBatch();
						if (ps1 % 50 == 0) {
							psmt1.executeBatch();
							psmt1.clearBatch();
						}
					}
					int[] n=psmt1.executeBatch();
				}
			} catch (Exception e) {
				e.printStackTrace();
				Log.error("set one出错:" + e.toString());
			} finally {
				if (null != db) {
					db.close();
				}
			}
		}

	}


}
