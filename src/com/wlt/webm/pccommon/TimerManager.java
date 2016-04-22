package com.wlt.webm.pccommon;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;

import com.wlt.webm.business.bean.FlowProportionCount;

/**
 * 任务管理
 * 
 * @author caiSJs
 */
public class TimerManager {

	// 时间间隔(一天)
	private static final long PERIOD_DAY = 24 * 60 * 60 * 1000;

	public TimerManager() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, 05);
		calendar.set(Calendar.MINUTE, 05);
		calendar.set(Calendar.SECOND, 0);
		Date date = calendar.getTime();
		if (date.before(new Date())) {
			date = this.addDay(date, 1);
		}
		new Timer().schedule(new FlowProportionCount(), date, PERIOD_DAY);
	}

	// 增加或减少天数
	public Date addDay(Date date, int num) {
		Calendar startDT = Calendar.getInstance();
		startDT.setTime(date);
		startDT.add(Calendar.DAY_OF_MONTH, num);
		return startDT.getTime();
	}

}
