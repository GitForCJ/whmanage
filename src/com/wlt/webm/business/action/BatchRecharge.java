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
 * 批量充值
 * 
 * @author 1989
 */
public class BatchRecharge extends DispatchAction {

	/**
	 * 批量话费充值
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
		String mess = "系统异常";
		SysUserForm userForm = (SysUserForm) request.getSession().getAttribute(
				"userSession");
		if (userForm == null) {
			request.setAttribute("mess", "请重新登录");
			return new ActionForward("/rights/wltlogin.jsp");
		}

		String ip = com.wlt.webm.util.Tools.getIpAddr(request);
		BatchRechargeForm info = (BatchRechargeForm) form;
		if (null == info.getTradepass() || null == info.getBatchfile()) {
			mess = "所填信息不完整";
		} else {
			DBService db = new DBService();
			String password = db
					.getString("SELECT trade_pass FROM sys_user WHERE user_no='"
							+ userForm.getUserno() + "'");
			if (password.equals(com.wlt.webm.ten.service.MD5Util.MD5Encode(info
					.getTradepass(), "GBK"))) {
				FormFile file = info.getBatchfile(); // 获取当前的文件
				String filename = file.getFileName();
				int filesize = file.getFileSize();
				if (!filename.endsWith(".txt") || filesize > (1024 * 1024)
						|| filesize <= 0) {
					mess = "必须是文本文件且小于1MB,大于0KB";
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
						mess = "文件内容错误!";
					} else {
						// 调用充值方法
						FatureCharge charge = new FatureCharge(infos, db, ip,
								userForm);
						charge.start();
						mess = "批量充值已提交成功";
					}
				}

			} else {
				mess = "交易密码错误";
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
					Log.info("批量充值,此条不处理,,,用户系统编号:"+userForm.getUserno()+",登陆账号:"+userForm.getUsername()+",,,单条内容:"+Arrays.toString(phone));
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
						// 充值
						String flag = "0";
						String payfee = phone[1];
						double fee = FloatArith.yun2li(payfee);
						if (AcctBillBean.getStatus(userForm.getParent_id())) {// 直营
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
						Log.info("订单号:" + seqNo + " 充值结果=" + k);
						// 充值结束
					}
				} catch (Exception e) {
					e.printStackTrace();
					Log.error(phone[0] + "批量充值:" + phone[1] + "\r\n"
							+ e.toString());
				}
			}
		}// charge()结束
	}// 类结束

	public static boolean isInteger(String value) {
		try {
			Integer.parseInt(value);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
	
	
	/**
	 * 批量流量充值
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
			request.setAttribute("mess", "请重新登录,批量充值流量失败");
			return new ActionForward("/rights/wltlogin.jsp");
		}
		TPForm tp=MemcacheConfig.getInstance().getTP(userForm.getUserno());
		if(tp==null){
			request.setAttribute("mess","流量批充功能只提供给接口商,批量充值流量失败");
			return new ActionForward("/business/BatchreFlowsCharge.jsp");
		}
		String ip = com.wlt.webm.util.Tools.getIpAddr(request);
		BatchRechargeForm info = (BatchRechargeForm) form;
		if (null == info.getTradepass() || null == info.getBatchfile()) {
			request.setAttribute("mess", "所填信息不完整,批量充值流量失败");
			return new ActionForward("/business/BatchreFlowsCharge.jsp");
		}
		
		DBService db=null;
		try {
			db = new DBService();
			String password = db.getString("SELECT trade_pass FROM sys_user WHERE user_no='"+ userForm.getUserno() + "'");
			if (!password.equals(com.wlt.webm.ten.service.MD5Util.MD5Encode(info.getTradepass(), "GBK"))) {
				request.setAttribute("mess", "交易密码错误,批量充值流量失败");
				return new ActionForward("/business/BatchreFlowsCharge.jsp");
			}
			FormFile file = info.getBatchfile(); // 获取当前的文件
			String filename = file.getFileName();
			int filesize = file.getFileSize();
			if (!filename.endsWith(".txt") || filesize > (1024 * 1024)|| filesize <= 0) {
				request.setAttribute("mess", "必须是文本文件且小于1MB,大于0KB,批量充值流量失败");
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
				request.setAttribute("mess", "文件内容错误,批量充值流量失败");
				return new ActionForward("/business/BatchreFlowsCharge.jsp");
			} 
			for (String[] phone : infos) {
				if(phone[0]==null || "".equals(phone[0].trim()) || phone[0].length()!=11 || !isNumeric(phone[0]) || 
						phone[1]==null || phone[1].indexOf("mb")==-1 || !isNumeric(phone[1].trim().replace("mb",""))){
					request.setAttribute("mess", "批量充值失败,号码或面值格式错误,错误内容："+Arrays.toString(phone));
					return new ActionForward("/business/BatchreFlowsCharge.jsp");
				}
			}
			// 调用充值方法
			new ThreadChargeFlows(infos,ip,tp).start();
			request.setAttribute("mess", "批量充值已提交成功!");
			return new ActionForward("/business/BatchreFlowsCharge.jsp");
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("mess", "批量充值流量系统异常,批量充值流量失败");
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
					String phoneNum=phone[0];//充值号码
					String mz=phone[1];//充值面值  100mb
					String productId="";//产品id 
					String realPrice="";//产品价格
					
					if(MemcacheConfig.getInstance().getMposUUID("phone_area").containsKey(phoneNum.substring(0, 7))){
						phoneinfo=new String[]{"35",MemcacheConfig.getInstance().getMposUUID("phone_area").get(phoneNum.substring(0,7))};
					}else{
						db=new DBService();
						phoneinfo = db.getStringArray("SELECT province_code,phone_type FROM sys_phone_area WHERE phone_num=" + phoneNum.substring(0, 7));
					}
					if(phoneinfo==null || phoneinfo.length<=0){
						Log.info("批量充值,此条不处理,,,系统编号:"+tp.getUser_no()+",,,未知号段,,,单条内容:"+Arrays.toString(phone));
						continue;
					}
					
					String phoneType=phoneinfo[1];//运营商类型
					String areaId=HttpFillOP.phoneAreas1.get(phoneinfo[0]);//运营商省份
					orderid=Tools.getNow3()+"PC"+(int)(Math.random()*10000+10000)+((char)(new Random().nextInt(26) + (int)'A'))+((char)(new Random().nextInt(26) + (int)'a'));
					
					FillProductInfo obj=(FillProductInfo)HttpFillOP.filterProductJSON(phoneNum,areaId,phoneType,"bean",tp.getFlowgroups());
					if(obj==null || obj.getData()==null || obj.getData().getList()==null || obj.getData().getList().size()<=0){
						Log.info("批量充值,此条不处理,,,系统编号:"+tp.getUser_no()+",,,未能同步流量产品,,,单条内容:"+Arrays.toString(phone));
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
						Log.info("批量充值,此条不处理,,,系统编号:"+tp.getUser_no()+",,,没有匹配的面值,,,单条内容:"+Arrays.toString(phone));
						continue;
					}
					Log.info("批量充值,此条不处理,,,系统编号:"+tp.getUser_no()+",,,订单充值,订单号："+orderid+",单条内容:"+Arrays.toString(phone));
					String jsonString=new Flow3g().Flows_Order_Inter(phoneType, areaId, orderid, productId, phoneNum, realPrice, mz, tp);
					Log.info("批量充值,此条不处理,,,系统编号:"+tp.getUser_no()+",,,订单充值,订单号："+orderid+",单条内容:"+Arrays.toString(phone)+",,订单响应内容:"+jsonString);
				} catch (Exception e) {
					e.printStackTrace();
					Log.error("批量充值流量,系统异常,订单号：："+orderid+",,充值号码："+phone[0] + ",,充值面值：" + phone[1] + ",,,ex:" + e.toString());
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
	 * 判断是否为数字
	 * @param str
	 * @return
	 */
	public boolean isNumeric(String str){
	    Pattern pattern = Pattern.compile("[0-9]*");
	    return pattern.matcher(str).matches();   
	}
	
	
	/**
	 * 流量补充 短信手动批量下发
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
			request.setAttribute("mess", "请重新登录,短信手动批量下发失败");
			return new ActionForward("/rights/wltlogin.jsp");
		}
		if(!"0".equals(userForm.getRoleType())){
			request.setAttribute("mess","你没有短信手动批量下发权限,短信手动批量下发失败");
			return new ActionForward("/business/SendMessageInfos.jsp");
		}
		BatchRechargeForm info = (BatchRechargeForm) form;
		if (null == info.getTradepass() || null == info.getBatchfile()) {
			request.setAttribute("mess", "所填信息不完整,短信手动批量下发失败");
			return new ActionForward("/business/SendMessageInfos.jsp");
		}
		
		DBService db=null;
		try {
			db = new DBService();
			String password = db.getString("SELECT trade_pass FROM sys_user WHERE user_no='"+ userForm.getUserno() + "'");
			if (!password.equals(com.wlt.webm.ten.service.MD5Util.MD5Encode(info.getTradepass(), "GBK"))) {
				request.setAttribute("mess", "交易密码错误,短信手动批量下发失败");
				return new ActionForward("/business/SendMessageInfos.jsp");
			}
			FormFile file = info.getBatchfile(); // 获取当前的文件
			String filename = file.getFileName();
			int filesize = file.getFileSize();
			if (!filename.endsWith(".txt") || filesize > (1024 * 1024)|| filesize <= 0) {
				request.setAttribute("mess", "必须是文本文件且小于1MB,大于0KB,短信手动批量下发失败");
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
				request.setAttribute("mess", "文件内容错误,短信手动批量下发失败");
				return new ActionForward("/business/SendMessageInfos.jsp");
			} 
			for (String[] phone : infos) {
				if(phone[0]==null || "".equals(phone[0].trim()) || phone[0].length()!=11 || !isNumeric(phone[0]) || 
						phone[1]==null || phone[1].indexOf("mb")==-1 || !isNumeric(phone[1].trim().replace("mb",""))){
					request.setAttribute("mess", "短信批量下发失败,号码或面值格式错误,错误内容："+Arrays.toString(phone));
					return new ActionForward("/business/SendMessageInfos.jsp");
				}
			}
			// 调用充值方法
			new ThreadSendMessage(infos).start();
			request.setAttribute("mess", "短信批量下发已提交成功!");
			return new ActionForward("/business/SendMessageInfos.jsp");
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("mess", "短信批量下发系统异常,短信手动批量下发失败");
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
				//调用发短信接口
				YDMessage.HeandMsg(phone[0],phone[1].replace("mb",""));
			}
		}
	}
}
