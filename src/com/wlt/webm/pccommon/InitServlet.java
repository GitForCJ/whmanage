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
 * WEB����������ʱ������Դ��Servlet��<br>
 */
public class InitServlet extends HttpServlet {
	/**
	 * ��ʼ������
	 * 
	 * @throws ServletException
	 */
	public void init() throws ServletException {
		super.init();
		ProjectContext context = ProjectContext.getInstance();
		ServletContext application = this.getServletContext();
		// ����Application����
		application.setAttribute("key", "ac3401b"
				+ MD5Util.MD5Encode("whtech_WH#TE@CH!0755", "UTF-8").substring(
						8) + "bcaedf");
		// =================================================

		// ������Ŀ���̵ĸ�·��
		context.setRootPath(getServletContext().getRealPath(File.separator));

		// �ֹ�ά�������������ļ���ͳһ���·��
		StringBuffer path = new StringBuffer();
		path.append(context.getRootPath()).append("WEB-INF").append(
				File.separator).append("project-config").append(File.separator);
		context.setConfigPath(path.toString());

		// =================================================

		// ������Ŀ���������ļ�·��
		context.setProjectConfigFile(context.getConfigPath()
				+ "project.properties");

		// ����ͳһ֧����������������ļ�·��
		context.setEpayUtilConfigFile(context.getConfigPath());
		LoadConfig.getConfig(context.getEpayUtilConfigFile());
		context.setInvoiceConfigFile(context.getConfigPath()
						+ "Invoice_PC.xml");
		Log.info("Invoice_PC.xml:·����" + context.getInvoiceConfigFile());
//		PortalReceive rc = new PortalReceive();
//		OuFeighState.getSupplier();
//		OuFeiInit.init();
//		OuFeighState.setOne2UserTask();
//		new Timer().schedule(new BackToAgent(), 10*1000, 20*60*1000);
		TaskChecker.initAgentsign();// ���ؽӿ�����Ϣ
//		//��Ѷ���������߳�
////		new TencentThread().start();
//		//�������������߳�
////		new JDThread().start();
//		//����ץ�������߳�
////		new Grab_Thread().start();
//		//ͳ�� ������ֵ �ɹ�ʧ����
//		new TimerManager();
	}
}
