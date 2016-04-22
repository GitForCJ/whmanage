package oufei;

import java.util.TimerTask;

import org.apache.struts.action.ActionForward;

import com.commsoft.epay.util.logging.Log;
import com.wlt.webm.tool.Tools;

/**
 * 殴飞供货定时任务
 * @author Administrator
 *
 */
public class OufeiTask extends TimerTask {
	/**
	 * userno,userpid,areacode,login,parentid
	 */
	private String[] str=null;
	
	/**
	 * 
	 * @param str
	 */
	public OufeiTask(String[] str){
		this.str=str;
	}

	/* (non-Javadoc)
	 * @see java.util.TimerTask#run()
	 */
	/* (non-Javadoc)
	 * @see java.util.TimerTask#run()
	 */
	@Override
	public void run() {
		if(Tools.validateTime()){
		Log.info(str[4]+":"+str[3]+"  启动殴飞供货定时任务...");
		OuFeiBean ou=new OuFeiBean(str);
		try {
			ou.ouFeiBusiness();
		} catch (Exception e) {
			e.printStackTrace();
		}
		}else{
			Log.info("殴飞供货:23:50到00:10不允许交易");
		}
	}
}
