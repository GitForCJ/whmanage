package com.wlt.webm.business.action;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.apache.struts.upload.FormFile;

import com.commsoft.epay.util.logging.Log;
import com.wlt.webm.business.bean.AcctBillBean;
import com.wlt.webm.business.bean.BiProd;
import com.wlt.webm.business.bean.unicom3gquery.FillProductDetail;
import com.wlt.webm.business.bean.unicom3gquery.FillProductInfo;
import com.wlt.webm.business.bean.unicom3gquery.HttpFillOP;
import com.wlt.webm.business.form.BatchRechargeForm;
import com.wlt.webm.business.form.TPForm;
import com.wlt.webm.db.DBService;
import com.wlt.webm.message.MemcacheConfig;
import com.wlt.webm.message.YDMessage;
import com.wlt.webm.rights.form.SysUserForm;
import com.wlt.webm.tool.FloatArith;
import com.wlt.webm.tool.Tools;

/**
 * ������ֵ
 * 
 * @author 1989
 */
public class BatchRecharge extends DispatchAction {

	/**
	 * �������ѳ�ֵ
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward rechage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String mess = "ϵͳ�쳣";
		SysUserForm userForm = (SysUserForm) request.getSession().getAttribute(
				"userSession");
		if (userForm == null) {
			request.setAttribute("mess", "�����µ�¼");
			return new ActionForward("/rights/wltlogin.jsp");
		}

		String ip = com.wlt.webm.util.Tools.getIpAddr(request);
		BatchRechargeForm info = (BatchRechargeForm) form;
		if (null == info.getTradepass() || null == info.getBatchfile()) {
			mess = "������Ϣ������";
		} else {
			DBService db = new DBService();
			String password = db
					.getString("SELECT trade_pass FROM sys_user WHERE user_no='"
							+ userForm.getUserno() + "'");
			if (password.equals(com.wlt.webm.ten.service.MD5Util.MD5Encode(info
					.getTradepass(), "GBK"))) {
				FormFile file = info.getBatchfile(); // ��ȡ��ǰ���ļ�
				String filename = file.getFileName();
				int filesize = file.getFileSize();
				if (!filename.endsWith(".txt") || filesize > (1024 * 1024)
						|| filesize <= 0) {
					mess = "�������ı��ļ���С��1MB,����0KB";
				} else {
					ArrayList<String[]> infos = new ArrayList<String[]>();
					String path = this.getServlet().getServletContext()
							.getRealPath("/")
							+ "batchpath\\";
					File fd = new File(path);
					if (!fd.exists()) {
						fd.mkdir();
					}
					String localfilename = userForm.getUserno() + "_"
							+ Tools.getNow3() + "_"
							+ (int) (Math.random() * 10000) + ".txt";
					File outfile = new File(path + localfilename);
					BufferedWriter write = new BufferedWriter(
							new OutputStreamWriter(
									new FileOutputStream(outfile)));
					BufferedReader reader = new BufferedReader(
							new InputStreamReader(file.getInputStream()));
					String phones = reader.readLine();
					while (null != phones && phones.length() > 11) {
						infos.add(phones.split("#"));
						write.write(phones + "\r\n");
						phones = reader.readLine();
					}
					reader.close();
					write.close();
					if (infos == null || infos.size() <= 0) {
						mess = "�ļ����ݴ���!";
					} else {
						// ���ó�ֵ����
						FatureCharge charge = new FatureCharge(infos, db, ip,
								userForm);
						charge.start();
						mess = "������ֵ���ύ�ɹ�";
					}
				}

			} else {
				mess = "�����������";
			}
		}
		request.setAttribute("mess", mess);
		return new ActionForward("/business/batchrecharge.jsp");
	}

	/**
	 * 
	 * @author 1989
	 * 
	 */
	class FatureCharge extends Thread {
		ArrayList<String[]> infos = null;
		DBService db = null;
		String ip = null;
		SysUserForm userForm;

		public FatureCharge(ArrayList<String[]> infos, DBService db, String ip,
				SysUserForm userForm) {
			this.infos = infos;
			this.db = db;
			this.ip = ip;
			this.userForm = userForm;
		}

		@Override
		public void run() {
			charge();
		}

		public void charge() {
			for (String[] phone : infos) {
				if(phone[0]==null || "".equals(phone[0]) || phone[0].length()!=11 || !isInteger(phone[1])){
					Log.info("������ֵ,����������,,,�û�ϵͳ���:"+userForm.getUserno()+",��½�˺�:"+userForm.getUsername()+",,,��������:"+Arrays.toString(phone));
					continue;
				}
				String[] phoneinfo=null;
				try {
					if(MemcacheConfig.getInstance().getMposUUID("phone_area").containsKey(phone[0].substring(0, 7))){
						phoneinfo=new String[]{"35",MemcacheConfig.getInstance().getMposUUID("phone_area").get(phone[0].substring(0,7))};
					}else{
						phoneinfo = db.getStringArray("SELECT province_code,phone_type FROM sys_phone_area WHERE phone_num=" + phone[0].substring(0, 7));
					}
					
					if (null != phoneinfo && phoneinfo.length == 2) {
						// ��ֵ
						String flag = "0";
						String payfee = phone[1];
						double fee = FloatArith.yun2li(payfee);
						if (AcctBillBean.getStatus(userForm.getParent_id())) {// ֱӪ
							flag = "1";
						}
						BiProd bp = new BiProd();
						String[] result = null;
						if (com.wlt.webm.util.Tools.isJC(phone[0])) {
							result = bp.getJCEmploy(db, flag, userForm
									.getUsersite(), phoneinfo[0], Integer
									.parseInt(payfee), phoneinfo[1]);
						} else {
							result = bp.getEmploy(db, phoneinfo[1],
									userForm.getUsersite(), phoneinfo[0],
									userForm.getUserno(), flag, Integer
											.parseInt(payfee), Integer
											.parseInt(userForm
													.getParent_id()), 0);
						}
						if (null == result) {
							continue;
						}
						String seqNo = Tools.getStreamSerial(phone[0]);
						int k = bp.nationFill(userForm.getParent_id(),
								userForm.getUserno(), phoneinfo[0],
								userForm.getUsersite(), phoneinfo[1],
								phone[0], seqNo, fee, result, userForm
										.getSa_zone(), db, userForm
										.getUsername(), ip, null, flag);
						Log.info("������:" + seqNo + " ��ֵ���=" + k);
						// ��ֵ����
					}
				} catch (Exception e) {
					e.printStackTrace();
					Log.error(phone[0] + "������ֵ:" + phone[1] + "\r\n"
							+ e.toString());
				}
			}
		}// charge()����
	}// �����

	public static boolean isInteger(String value) {
		try {
			Integer.parseInt(value);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
	
	
	/**
	 * ����������ֵ
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return null
	 * @throws Exception
	 */
	public ActionForward Flow_Rechage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		SysUserForm userForm = (SysUserForm) request.getSession().getAttribute("userSession");
		if (userForm == null) {
			request.setAttribute("mess", "�����µ�¼,������ֵ����ʧ��");
			return new ActionForward("/rights/wltlogin.jsp");
		}
		TPForm tp=MemcacheConfig.getInstance().getTP(userForm.getUserno());
		if(tp==null){
			request.setAttribute("mess","�������书��ֻ�ṩ���ӿ���,������ֵ����ʧ��");
			return new ActionForward("/business/BatchreFlowsCharge.jsp");
		}
		String ip = com.wlt.webm.util.Tools.getIpAddr(request);
		BatchRechargeForm info = (BatchRechargeForm) form;
		if (null == info.getTradepass() || null == info.getBatchfile()) {
			request.setAttribute("mess", "������Ϣ������,������ֵ����ʧ��");
			return new ActionForward("/business/BatchreFlowsCharge.jsp");
		}
		
		DBService db=null;
		try {
			db = new DBService();
			String password = db.getString("SELECT trade_pass FROM sys_user WHERE user_no='"+ userForm.getUserno() + "'");
			if (!password.equals(com.wlt.webm.ten.service.MD5Util.MD5Encode(info.getTradepass(), "GBK"))) {
				request.setAttribute("mess", "�����������,������ֵ����ʧ��");
				return new ActionForward("/business/BatchreFlowsCharge.jsp");
			}
			FormFile file = info.getBatchfile(); // ��ȡ��ǰ���ļ�
			String filename = file.getFileName();
			int filesize = file.getFileSize();
			if (!filename.endsWith(".txt") || filesize > (1024 * 1024)|| filesize <= 0) {
				request.setAttribute("mess", "�������ı��ļ���С��1MB,����0KB,������ֵ����ʧ��");
				return new ActionForward("/business/BatchreFlowsCharge.jsp");
			} 
			ArrayList<String[]> infos = new ArrayList<String[]>();
			String path = this.getServlet().getServletContext().getRealPath("/")+ "batchpath\\";
			File fd = new File(path);
			if (!fd.exists()) {
				fd.mkdir();
			}
			String localfilename = userForm.getUserno() + "_flow_"+ Tools.getNow3() + "_"+ (int) (Math.random() * 10000) + ".txt";
			File outfile = new File(path + localfilename);
			BufferedWriter write = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outfile)));
			BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()));
			String phones = reader.readLine();
			while (null != phones && phones.length() > 11) {
				infos.add(phones.split("#"));
				write.write(phones + "\r\n");
				phones = reader.readLine();
			}
			reader.close();
			write.close();
			if (infos == null || infos.size() <= 0) {
				request.setAttribute("mess", "�ļ����ݴ���,������ֵ����ʧ��");
				return new ActionForward("/business/BatchreFlowsCharge.jsp");
			} 
			for (String[] phone : infos) {
				if(phone[0]==null || "".equals(phone[0].trim()) || phone[0].length()!=11 || !isNumeric(phone[0]) || 
						phone[1]==null || phone[1].indexOf("mb")==-1 || !isNumeric(phone[1].trim().replace("mb",""))){
					request.setAttribute("mess", "������ֵʧ��,�������ֵ��ʽ����,�������ݣ�"+Arrays.toString(phone));
					return new ActionForward("/business/BatchreFlowsCharge.jsp");
				}
			}
			// ���ó�ֵ����
			new ThreadChargeFlows(infos,ip,tp).start();
			request.setAttribute("mess", "������ֵ���ύ�ɹ�!");
			return new ActionForward("/business/BatchreFlowsCharge.jsp");
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("mess", "������ֵ����ϵͳ�쳣,������ֵ����ʧ��");
			return new ActionForward("/business/BatchreFlowsCharge.jsp");
		}finally{
			if(db!=null){
				db.close();
				db=null;
			}
		}
	}
	
	
	class ThreadChargeFlows extends Thread{
		ArrayList<String[]> infos = null;
		String ip = null;
		TPForm tp =null;
		
		public ThreadChargeFlows(ArrayList<String[]> infos,String ip,TPForm tp){
			this.infos = infos;
			this.ip = ip;
			this.tp = tp;
		}
		
		public void run() {
			for (String[] phone : infos) {
				DBService db=null;
				String[] phoneinfo=null;
				String orderid=null;
				try {
					String phoneNum=phone[0];//��ֵ����
					String mz=phone[1];//��ֵ��ֵ  100mb
					String productId="";//��Ʒid 
					String realPrice="";//��Ʒ�۸�
					
					if(MemcacheConfig.getInstance().getMposUUID("phone_area").containsKey(phoneNum.substring(0, 7))){
						phoneinfo=new String[]{"35",MemcacheConfig.getInstance().getMposUUID("phone_area").get(phoneNum.substring(0,7))};
					}else{
						db=new DBService();
						phoneinfo = db.getStringArray("SELECT province_code,phone_type FROM sys_phone_area WHERE phone_num=" + phoneNum.substring(0, 7));
					}
					if(phoneinfo==null || phoneinfo.length<=0){
						Log.info("������ֵ,����������,,,ϵͳ���:"+tp.getUser_no()+",,,δ֪�Ŷ�,,,��������:"+Arrays.toString(phone));
						continue;
					}
					
					String phoneType=phoneinfo[1];//��Ӫ������
					String areaId=HttpFillOP.phoneAreas1.get(phoneinfo[0]);//��Ӫ��ʡ��
					orderid=Tools.getNow3()+"PC"+(int)(Math.random()*10000+10000)+((char)(new Random().nextInt(26) + (int)'A'))+((char)(new Random().nextInt(26) + (int)'a'));
					
					FillProductInfo obj=(FillProductInfo)HttpFillOP.filterProductJSON(phoneNum,areaId,phoneType,"bean",tp.getFlowgroups());
					if(obj==null || obj.getData()==null || obj.getData().getList()==null || obj.getData().getList().size()<=0){
						Log.info("������ֵ,����������,,,ϵͳ���:"+tp.getUser_no()+",,,δ��ͬ��������Ʒ,,,��������:"+Arrays.toString(phone));
						continue;
					}
					List<FillProductDetail> arry=obj.getData().getList();
					boolean bool=false;
					for(FillProductDetail f:arry){
						if(f.getProduct_name().trim().toLowerCase().equals((mz).toLowerCase())){
							bool=true;
							productId=f.getProduct_id();
							realPrice=(Float.parseFloat(f.getProduct_price())*100)+"";
							break;
						}
					}
					if(!bool){
						Log.info("������ֵ,����������,,,ϵͳ���:"+tp.getUser_no()+",,,û��ƥ�����ֵ,,,��������:"+Arrays.toString(phone));
						continue;
					}
					Log.info("������ֵ,����������,,,ϵͳ���:"+tp.getUser_no()+",,,������ֵ,�����ţ�"+orderid+",��������:"+Arrays.toString(phone));
					String jsonString=new Flow3g().Flows_Order_Inter(phoneType, areaId, orderid, productId, phoneNum, realPrice, mz, tp);
					Log.info("������ֵ,����������,,,ϵͳ���:"+tp.getUser_no()+",,,������ֵ,�����ţ�"+orderid+",��������:"+Arrays.toString(phone)+",,������Ӧ����:"+jsonString);
				} catch (Exception e) {
					e.printStackTrace();
					Log.error("������ֵ����,ϵͳ�쳣,�����ţ���"+orderid+",,��ֵ���룺"+phone[0] + ",,��ֵ��ֵ��" + phone[1] + ",,,ex:" + e.toString());
				}finally{
					if(db!=null){
						db.close();
						db=null;
					}
				}
			}
		}
	}
	/**
	 * �ж��Ƿ�Ϊ����
	 * @param str
	 * @return
	 */
	public boolean isNumeric(String str){
	    Pattern pattern = Pattern.compile("[0-9]*");
	    return pattern.matcher(str).matches();   
	}
	
	
	/**
	 * �������� �����ֶ������·�
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return null
	 */
	public ActionForward SendMessageAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		SysUserForm userForm = (SysUserForm) request.getSession().getAttribute("userSession");
		if (userForm == null) {
			request.setAttribute("mess", "�����µ�¼,�����ֶ������·�ʧ��");
			return new ActionForward("/rights/wltlogin.jsp");
		}
		if(!"0".equals(userForm.getRoleType())){
			request.setAttribute("mess","��û�ж����ֶ������·�Ȩ��,�����ֶ������·�ʧ��");
			return new ActionForward("/business/SendMessageInfos.jsp");
		}
		BatchRechargeForm info = (BatchRechargeForm) form;
		if (null == info.getTradepass() || null == info.getBatchfile()) {
			request.setAttribute("mess", "������Ϣ������,�����ֶ������·�ʧ��");
			return new ActionForward("/business/SendMessageInfos.jsp");
		}
		
		DBService db=null;
		try {
			db = new DBService();
			String password = db.getString("SELECT trade_pass FROM sys_user WHERE user_no='"+ userForm.getUserno() + "'");
			if (!password.equals(com.wlt.webm.ten.service.MD5Util.MD5Encode(info.getTradepass(), "GBK"))) {
				request.setAttribute("mess", "�����������,�����ֶ������·�ʧ��");
				return new ActionForward("/business/SendMessageInfos.jsp");
			}
			FormFile file = info.getBatchfile(); // ��ȡ��ǰ���ļ�
			String filename = file.getFileName();
			int filesize = file.getFileSize();
			if (!filename.endsWith(".txt") || filesize > (1024 * 1024)|| filesize <= 0) {
				request.setAttribute("mess", "�������ı��ļ���С��1MB,����0KB,�����ֶ������·�ʧ��");
				return new ActionForward("/business/SendMessageInfos.jsp");
			} 
			ArrayList<String[]> infos = new ArrayList<String[]>();
			String path = this.getServlet().getServletContext().getRealPath("/")+ "batchpath\\";
			File fd = new File(path);
			if (!fd.exists()) {
				fd.mkdir();
			}
			String localfilename = userForm.getUserno() + "_sendMsg_"+ Tools.getNow3() + "_"+ (int) (Math.random() * 10000) + ".txt";
			File outfile = new File(path + localfilename);
			BufferedWriter write = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outfile)));
			BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()));
			String phones = reader.readLine();
			while (null != phones && phones.length() > 11) {
				infos.add(phones.split("#"));
				write.write(phones + "\r\n");
				phones = reader.readLine();
			}
			reader.close();
			write.close();
			if (infos == null || infos.size() <= 0) {
				request.setAttribute("mess", "�ļ����ݴ���,�����ֶ������·�ʧ��");
				return new ActionForward("/business/SendMessageInfos.jsp");
			} 
			for (String[] phone : infos) {
				if(phone[0]==null || "".equals(phone[0].trim()) || phone[0].length()!=11 || !isNumeric(phone[0]) || 
						phone[1]==null || phone[1].indexOf("mb")==-1 || !isNumeric(phone[1].trim().replace("mb",""))){
					request.setAttribute("mess", "���������·�ʧ��,�������ֵ��ʽ����,�������ݣ�"+Arrays.toString(phone));
					return new ActionForward("/business/SendMessageInfos.jsp");
				}
			}
			// ���ó�ֵ����
			new ThreadSendMessage(infos).start();
			request.setAttribute("mess", "���������·����ύ�ɹ�!");
			return new ActionForward("/business/SendMessageInfos.jsp");
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("mess", "���������·�ϵͳ�쳣,�����ֶ������·�ʧ��");
			return new ActionForward("/business/SendMessageInfos.jsp");
		}finally{
			if(db!=null){
				db.close();
				db=null;
			}
		}
	}
	class ThreadSendMessage extends Thread{
		ArrayList<String[]> infos = null;
		public ThreadSendMessage(ArrayList<String[]> infos){
			this.infos = infos;
		}
		public void run() {
			for (String[] phone : infos) {
				//���÷����Žӿ�
				YDMessage.HeandMsg(phone[0],phone[1].replace("mb",""));
			}
		}
	}
}
