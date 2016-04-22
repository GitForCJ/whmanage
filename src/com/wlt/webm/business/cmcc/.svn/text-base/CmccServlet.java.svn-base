package com.wlt.webm.business.cmcc;


import java.io.IOException;
import java.net.UnknownHostException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import com.commsoft.epay.util.logging.Log;
import com.commsoft.epay.util.tp.ThreadPool;
import com.commsoft.epay.util.tp.ThreadProvider;
import com.wlt.webm.business.unicom.ASynSocketClient;
import com.wlt.webm.business.unicom.SocketHandle;
import com.wlt.webm.tool.Constant;
/**
 * 
 */
public class CmccServlet extends HttpServlet
{
    /**
     * 初始化方法
     * @throws ServletException
     */
    public void init() throws ServletException
    {
        super.init();
		//启动广东移动缴费SOCKET监控服务
		try {
			 new CMPayASynSocketClient(Constant.CMPayIP,Constant.CMPayPORT);
		} catch (UnknownHostException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		//启动移动缴费SOCKET消息处理线程消息类型
		CMPaySocketHandle CMPaysockHandle = new CMPaySocketHandle();
		Thread dealT = new Thread(CMPaysockHandle);
		dealT.start();
		
		
		
		
		
//		
//		//启动广东联通SOCKET监控服务
		try {
			 new ASynSocketClient(Constant.UNIIP, Constant.UNIPORT);
		} catch (UnknownHostException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		//启动联通SOCKET消息处理线程
		SocketHandle sockHandle = new SocketHandle();
		Thread dealT2 = new Thread(sockHandle);
		dealT2.start();
    }
}
