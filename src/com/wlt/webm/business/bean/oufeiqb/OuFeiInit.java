package com.wlt.webm.business.bean.oufeiqb;

import java.util.ArrayList;
import java.util.Timer;

import oufei.OUConstat;

import com.commsoft.epay.util.logging.Log;
import com.wlt.webm.db.DBService;

public class OuFeiInit {
	
	/**
	 * ŷ�� Q�Ҷ�ʱȡ������
	 */
	public static void init(){
		DBService db = null;
		ArrayList<String[]> result = null;
		try {
			db = new DBService();
			String sql = "SELECT userno,userpid,areacode,login,parentid,oufeiID,oufeimoban,oufeisign FROM sys_oufei_qb WHERE  state =0 and isstart=0 AND oufeitype=1";
			result = (ArrayList<String[]>) db.getList(sql);
			if (null != result&&result.size()>0) {
				System.out.println("����ŷ��qb����");
				for (String[] str : result) {
					OuFeiThread qbtask = new OuFeiThread(str);
					new Timer().scheduleAtFixedRate(qbtask, 0, 1 * 30 * 1000);
					OUConstat.Qbtask.put(str[4]+"_qb", qbtask);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			Log.error("��ѯ���ڿ���״̬��Ź��Q�ҹ����̣���������붨ʱ�������:" + e.toString());
			init();
		} finally {
			if (null != db) {
				db.close();
			}
		}
	}
}
