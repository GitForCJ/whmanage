package com.wlt.webm.business.bean.trafficfines;

import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;

import com.commsoft.epay.util.logging.Log;
import com.wlt.webm.db.DBService;
import com.wlt.webm.util.Tools;

/**
 * 自动同步交通罚款数据
 * 
 * @author 1989
 */
public class GetBreakRuleResult extends TimerTask {

	@Override
	public void run() {
		Log.info("开始同步交通罚款数据:" + Tools.getNow());
		SynchronousDatas syn=new SynchronousDatas();
		ArrayList<String[]> datas = syn.getDates();
		if (null == datas || datas.size() == 0) {
			Log.info("无需要同步的数据,任务结束:" + Tools.getNow());
			return;
		}
		for(String[] data:datas){
		BreakRulesInfoResp resp=syn.synchronousByCarPh(data[3],data[2]);
		List<PeccancyWOModel> model=resp.getOrders();
		for(PeccancyWOModel md:model){
			if(md.getId()==Long.parseLong(data[0])){
				syn.dealOrder(md);
			}
		}
		}
	}

}
