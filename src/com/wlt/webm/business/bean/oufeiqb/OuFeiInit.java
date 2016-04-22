package com.wlt.webm.business.bean.oufeiqb;

import java.util.ArrayList;
import java.util.Timer;

import oufei.OUConstat;

import com.commsoft.epay.util.logging.Log;
import com.wlt.webm.db.DBService;

public class OuFeiInit {
	
	/**
	 * 欧飞 Q币定时取单任务
	 */
	public static void init(){
		DBService db = null;
		ArrayList<String[]> result = null;
		try {
			db = new DBService();
			String sql = "SELECT userno,userpid,areacode,login,parentid,oufeiID,oufeimoban,oufeisign FROM sys_oufei_qb WHERE  state =0 and isstart=0 AND oufeitype=1";
			result = (ArrayList<String[]>) db.getList(sql);
			if (null != result&&result.size()>0) {
				System.out.println("启动欧飞qb供货");
				for (String[] str : result) {
					OuFeiThread qbtask = new OuFeiThread(str);
					new Timer().scheduleAtFixedRate(qbtask, 0, 1 * 30 * 1000);
					OUConstat.Qbtask.put(str[4]+"_qb", qbtask);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			Log.error("查询处于开启状态的殴飞Q币供货商，并将其加入定时任务出错:" + e.toString());
			init();
		} finally {
			if (null != db) {
				db.close();
			}
		}
	}
}
