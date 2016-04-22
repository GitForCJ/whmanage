package oufei;

import java.util.TimerTask;

import org.apache.struts.action.ActionForward;

import com.commsoft.epay.util.logging.Log;
import com.wlt.webm.tool.Tools;

/**
 * Ź�ɹ�����ʱ����
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
		Log.info(str[4]+":"+str[3]+"  ����Ź�ɹ�����ʱ����...");
		OuFeiBean ou=new OuFeiBean(str);
		try {
			ou.ouFeiBusiness();
		} catch (Exception e) {
			e.printStackTrace();
		}
		}else{
			Log.info("Ź�ɹ���:23:50��00:10��������");
		}
	}
}
