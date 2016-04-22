package com.wlt.webm.pccommon;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.wlt.webm.db.DBService;

/**
 * 殴飞供货处理类
 * @author Administrator
 *
 */
public class OufeiGhTask  {

	private OufeiGhTask(){
		Timer time=new Timer();
		time.scheduleAtFixedRate(new OufeiTask(), 10000, 1000*60*20);
	}
	
	class OufeiTask extends TimerTask{

		@Override
		public void run() {
			DBService db=null;
			List userList=null;
			try{
				db=new DBService();
				String sql="select userno,fundacct,oufeiID,oufeimoban,oufeisign,userpid " +
						"from sys_oufei where state=0 and isstaert=0";
				userList=db.getList(sql);
				
			}catch(Exception e){
				
			}
		}
		
	}
}
