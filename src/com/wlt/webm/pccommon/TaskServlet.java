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
     * ��ʼ������
     * @throws ServletException
     */
    public void init() throws ServletException
    {	//��������SOCKET��ط���
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
	//��������SOCKET��Ϣ�����߳�
	TZSocketHandle sockHandle = new TZSocketHandle();
	Thread dealT = new Thread(sockHandle);
	sockHandle.setThreadPool(threadPool);
	dealT.start();
    }
}
