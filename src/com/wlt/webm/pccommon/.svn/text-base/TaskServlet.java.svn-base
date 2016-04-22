package com.wlt.webm.pccommon;

import java.io.IOException;
import java.net.UnknownHostException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import com.commsoft.epay.util.logging.Log;
import com.commsoft.epay.util.tp.ThreadPool;
import com.commsoft.epay.util.tp.ThreadProvider;
import com.wlt.webm.business.TZ.service.TZSocketHandle;
import com.wlt.webm.socket.TianZuoASynSocketClient;
import com.wlt.webm.uictool.Constant;

public class TaskServlet extends HttpServlet{
	
	private static ThreadPool threadPool = ThreadProvider.getInstance().getThreadPool();
	 /**
     * 初始化方法
     * @throws ServletException
     */
    public void init() throws ServletException
    {	//启动天作SOCKET监控服务
		try {
			new TianZuoASynSocketClient(Constant.TZIP, Constant.TZPORT);
	} catch (UnknownHostException e1) {
		e1.printStackTrace();
	} catch (InterruptedException e) {
		Log.error("UicMainTZ"+e);
	}
	catch (IOException e1) {
		Log.error("UicMainTZ"+e1);
	}
	//启动天作SOCKET消息处理线程
	TZSocketHandle sockHandle = new TZSocketHandle();
	Thread dealT = new Thread(sockHandle);
	sockHandle.setThreadPool(threadPool);
	dealT.start();
    }
}
