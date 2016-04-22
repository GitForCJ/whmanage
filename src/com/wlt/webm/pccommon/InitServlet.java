package com.wlt.webm.pccommon;

import java.io.File;
import java.util.Timer;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import com.commsoft.epay.util.LoadConfig;
import com.commsoft.epay.util.logging.Log;
import com.wlt.webm.business.bean.oufeiqb.OuFeiInit;
import com.wlt.webm.message.PortalReceive;
import com.wlt.webm.ten.service.MD5Util;

/**
 * WEB服务器启动时加载资源的Servlet类<br>
 */
public class InitServlet extends HttpServlet {
	/**
	 * 初始化方法
	 * 
	 * @throws ServletException
	 */
	public void init() throws ServletException {
		super.init();
		ProjectContext context = ProjectContext.getInstance();
		ServletContext application = this.getServletContext();
		// 设置Application属性
		application.setAttribute("key", "ac3401b"
				+ MD5Util.MD5Encode("whtech_WH#TE@CH!0755", "UTF-8").substring(
						8) + "bcaedf");
		// =================================================

		// 设置项目工程的根路径
		context.setRootPath(getServletContext().getRealPath(File.separator));

		// 手工维护的所有配置文件的统一存放路径
		StringBuffer path = new StringBuffer();
		path.append(context.getRootPath()).append("WEB-INF").append(
				File.separator).append("project-config").append(File.separator);
		context.setConfigPath(path.toString());

		// =================================================

		// 设置项目工程配置文件路径
		context.setProjectConfigFile(context.getConfigPath()
				+ "project.properties");

		// 设置统一支付公共组件的配置文件路径
		context.setEpayUtilConfigFile(context.getConfigPath());
		LoadConfig.getConfig(context.getEpayUtilConfigFile());
		context.setInvoiceConfigFile(context.getConfigPath()
						+ "Invoice_PC.xml");
		Log.info("Invoice_PC.xml:路径：" + context.getInvoiceConfigFile());
//		PortalReceive rc = new PortalReceive();
//		OuFeighState.getSupplier();
//		OuFeiInit.init();
//		OuFeighState.setOne2UserTask();
//		new Timer().schedule(new BackToAgent(), 10*1000, 20*60*1000);
		TaskChecker.initAgentsign();// 加载接口商信息
//		//腾讯流量订单线程
////		new TencentThread().start();
//		//京东流量订单线程
////		new JDThread().start();
//		//流量抓单补充线程
////		new Grab_Thread().start();
//		//统计 流量充值 成功失败率
//		new TimerManager();
	}
}
