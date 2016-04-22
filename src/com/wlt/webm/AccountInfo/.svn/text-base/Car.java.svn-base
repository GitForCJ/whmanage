package com.wlt.webm.AccountInfo;

import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.SimpleHttpConnectionManager;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import bsh.This;

import com.alibaba.fastjson.JSON;
import com.commsoft.epay.util.logging.Log;
import com.wlt.webm.business.bean.baibang.Md5AndBase64;
import com.wlt.webm.business.bean.baibang.XmlHead;
import com.wlt.webm.business.bean.beijingFlow.Result;
import com.wlt.webm.db.DBService;
import com.wlt.webm.scputil.MD5Util;
import com.wlt.webm.tool.Tools;
import com.wlt.webm.util.MD5;

public class Car extends DispatchAction {
	/**交通罚款 百事帮 业务回调
	 * @param maping
	 * @param form
	 * @param request
	 * @param response
	 * @return null
	 */
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Log.info("百事帮交通罚款，回调，，，");
		String Command=request.getParameter("Command");
		String UserId=request.getParameter("UserId");
		String CftMerId=request.getParameter("CftMerId");
		String MerchantId=request.getParameter("MerchantId");
		String Version=request.getParameter("Version");
		String ResFormat=request.getParameter("ResFormat");
		String Attach=request.getParameter("Attach");
		String Sign=request.getParameter("Sign");
		String CftTransId=request.getParameter("CftTransId");
		String SpTransId=request.getParameter("SpTransId");
		String ViolationId=request.getParameter("ViolationId");
		String DealStatus=request.getParameter("DealStatus");
		String DealFileUrl=request.getParameter("DealFileUrl");
		Log.info("百事帮交通罚款，回调，，，参数，" +
			"Command="+Command+"," +
			"UserId="+UserId+"," +
			"CftMerId="+CftMerId+"," +
			"MerchantId="+MerchantId+"," +
			"Version="+Version+"," +
			"ResFormat="+ResFormat+"," +
			"Attach="+Attach+"," +
			"Sign="+Sign+"," +
			"CftTransId="+CftTransId+"," +
			"SpTransId="+SpTransId+"," +
			"ViolationId="+ViolationId+"," +
			"DealStatus="+DealStatus+",DealFileUrl="+DealFileUrl);

		if(Command==null || "".equals(Command) ||
				CftMerId==null || "".equals(CftMerId) ||
				MerchantId==null || "".equals(MerchantId) ||
				Version==null || "".equals(Version) ||
				ResFormat==null || "".equals(ResFormat) ||
				Attach==null || "".equals(Attach) ||
				Sign==null || "".equals(Sign) ||
				CftTransId==null || "".equals(CftTransId) ||
				ViolationId==null || "".equals(ViolationId) ||
				DealStatus==null || "".equals(DealStatus)){
			Log.info("百事帮交通罚款，回调，，，参数不足");
			return null;
		}
		//违章办理进度：
		//	0，	已经接受，正在处理
		//	1，	办理成功
		//	2，	办理失败，已退款
		//状态 为1或2是，处理业务
		if("1".equals(DealStatus) || "2".equals(DealStatus)){
			Log.info("百事帮交通罚款，回调，，，状态DealStatus="+DealStatus);
			//查看订单状态  如果订单状态为 办理中，才允许修改状态
			String sql="SELECT s.woState FROM wht_breakrules s,wht_bruleorder r WHERE s.gdid=r.id AND s.id='"+ViolationId+"' AND r.workerNo='"+CftTransId+"' AND r.dealserial='"+CftTransId+"'";
			DBService db=null;
			PrintWriter out = null;
			try{
				db=new DBService();
				String str=db.getString(sql);
				if(str==null || "".equals(str)){
					Log.info("百事帮交通罚款，回调，，，没有此订单：CftTransId="+CftTransId);
					return null;
				}
				if(!"3".equals(str)){//订单状态修改过，重复回调
					Log.info("百事帮交通罚款，回调，，，重复回调，不处理,订单号：CftTransId="+CftTransId);
					return null;
				}
				//改为临时状态，防止重复回调
				db.update("UPDATE wht_breakrules SET woState=2 WHERE id="+ViolationId+" AND gdid=(SELECT id FROM wht_bruleorder  WHERE  workerNo='"+CftTransId+"'  AND dealserial='"+CftTransId+"')");
				//成功 或 失败，业务处理，，，，
				Car n=new Car();
				n.new carGoBackManage(CftTransId,ViolationId,DealStatus).start();
				
				//回调下游接口处理
				String[] ss=db.getStringArray("SELECT er.dealserial,es.Exp2,er.carnum,es.exp5,er.Exp4,si.keyvalue FROM wht_breakrules es, wht_bruleorder er,sys_agentsign si WHERE er.id=es.gdid AND er.userno=si.userno AND er.dealserial='"+CftTransId+"' AND er.workerNo='"+CftTransId+"'");
				if(ss!=null && ss.length>0){
					if(ss[4]!=null && !"".equals(ss[4])){
						Log.info("调用回调接口,订单号:"+CftTransId+",违章id:"+ViolationId+",回调信息:"+Arrays.toString(ss));
						//启动回调线程
						CarBack back=new CarBack(ss,DealStatus);
						back.start();
					}
				}
				//输出
				out=response.getWriter();
			}catch(Exception ex){
				Log.info("百事帮交通罚款，回调处理异常，，订单号："+CftTransId+",ex:"+ex);
			}finally{
				if(null!=db){
					db.close();
					db=null;
				}
			}
			Map<String, String> map =getMap(CftTransId,SpTransId,ViolationId);
			String resultStr=getXML(map);
			Log.info("百事帮交通罚款，回调，，，输出resultStr:"+resultStr);
			out.write(resultStr);
			out.flush();
			out.close();
			return null;
			
		}else if("0".equals(DealStatus)){//处理中 状态
			Log.info("百事帮交通罚款，回调，，，处理中状态DealStatus="+DealStatus);
		}else{
			Log.info("百事帮交通罚款，回调，，，未知状态DealStatus="+DealStatus);
		}
		return null;
	}
	/**百事帮 响应组装信息
	 * @param CftTransId
	 * @param SpTransId
	 * @param ViolationId
	 * @return map
	 */
	public static Map<String, String> getMap(String CftTransId,String SpTransId,String ViolationId){
		Map<String, String> map=new HashMap<String, String>();
		
		XmlHead head=new XmlHead("","");
		StringBuffer s2=new StringBuffer();
		s2.append("Attach="+head.getAttach());
		s2.append("&CftMerId="+head.getCftMerId());
		s2.append("&Command=1");
		s2.append("&CftRetCode=ok");
		s2.append("&CftRetInfo=成功");
		s2.append("&CftTransId="+CftTransId);
		s2.append("&MerchantId="+head.getMerchantId());
		s2.append("&ResFormat="+head.getResFormat());
		s2.append("&SpTransId="+SpTransId);
		s2.append("&UserId="+head.getUserId());
		s2.append("&Version="+head.getVersion());
		s2.append("&ViolationId="+ViolationId);
		s2.append("&Md5Key="+Md5AndBase64.KEY);
		String sign=MD5.encode(s2.toString()).toUpperCase();
		
		map.put("Attach",head.getAttach());
		map.put("CftMerId",head.getCftMerId());
		map.put("Command","1");
		map.put("CftRetCode","ok");
		map.put("CftRetInfo","成功");
		map.put("CftTransId",CftTransId);
		map.put("MerchantId",head.getMerchantId());
		map.put("ResFormat",head.getResFormat());
		map.put("SpTransId",SpTransId);
		map.put("UserId",head.getUserId());
		map.put("Version",head.getVersion());
		map.put("ViolationId",ViolationId);
		map.put("Sign",sign);
		return map;
	}
	/**组装xml 
	 * @param map
	 * @return string
	 */
	public static String getXML(Map map){
		StringBuffer str=new StringBuffer("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		str.append("<root>");
		for (Object key :map.keySet()) {
//			System.out.println("key= "+ key + " and value= " + map.get(key));
			str.append("<"+key+">"+map.get(key)+"</"+key+">");
		}
		str.append("</root>");
		return str.toString();
	}

	class carGoBackManage extends Thread{
		private String dealserial="";
		private String state="";
		private String ViolationId="";
		public carGoBackManage(String dealserial,String ViolationId,String state){
			this.dealserial=dealserial;
			this.state=state;
			this.ViolationId=ViolationId;
		}
		public void run() {
			DBService db=null;
			try{
				db=new DBService();
				db.setAutoCommit(false);
					if("1".equals(this.state)){//成功
						//改为成功状态
						db.update("UPDATE wht_breakrules SET woState=4,updateDate='"+Tools.getNewDate()+"' WHERE id="+this.ViolationId+" AND gdid=(SELECT id FROM wht_bruleorder WHERE workerNo='"+this.dealserial+"' AND dealserial='"+this.dealserial+"')");
						//修改预查询表，违章状态
						db.update("UPDATE wht_yquery_wz SET woState=4 WHERE carId="+this.ViolationId);
						Log.info("百事帮交通罚款，回调，，，修改订单状态，，工单ID:"+dealserial+",违章id:"+ViolationId+",,订单成功，程序处理成功，");
					}else{//失败
						Log.info("百事帮交通罚款，回调，，，修改订单状态，，工单ID:"+dealserial+",违章id:"+ViolationId+",,订单失败，开始退费业务");
						//退费
						String tableName="wht_acctbill_"+Tools.getNow3().substring(2,6);
						//下单 ：tradeaccount 用户登录账号，，，，childfacct 交易账号
						String[] acc=db.getStringArray(" SELECT tradeaccount,childfacct,infacctfee FROM "+tableName+" WHERE tradeserial='"+dealserial+"' AND dealserial='"+dealserial+"'");
						//根据白世邦 下单记录账户的  用户账号，查找此用户的账户，余额
						String getSql="SELECT childfacct,accountleft FROM wht_childfacct WHERE childfacct=(SELECT CONCAT(fundacct,'01') FROM wht_facct WHERE user_no='"+acc[0]+"')";
						String[] funs=db.getStringArray(getSql);
						if(!acc[1].equals(funs[0])){
							Log.info("百事帮交通罚款，回调，，，修改订单状态，，工单ID:"+dealserial+",违章id:"+ViolationId+",,订单失败,账户不匹配");
							return ;
						}
						String orderFee=db.getString("SELECT Exp4 FROM wht_breakrules WHERE  id="+this.ViolationId+" AND gdid=(SELECT id FROM wht_bruleorder WHERE workerNo='"+this.dealserial+"' AND dealserial='"+this.dealserial+"')");
						//单笔违章退费金额
						int orderMoney=Integer.parseInt(orderFee);
						int accountMoney=Integer.parseInt(funs[1])+orderMoney;
						//退费账务 流水号
						String serial="resBSB"+Tools.getNow3()+((int)(Math.random()*1000)+1000)+""+((char)(new Random().nextInt(26) + (int)'A'))+""+((int)(Math.random()*100)+100);
						//账务信息录入
						String acctSql="INSERT INTO "+tableName+"(childfacct,dealserial," +
								"tradeaccount,tradetime,tradefee,infacctfee,tradetype,expl,state,distime," +
								"accountleft,tradeserial,other_childfacct,pay_type) VALUES(" +
								"'"+acc[1]+"'," +
								"'"+serial+"'," +
								"'"+acc[0]+"'," +
								"'"+Tools.getNow3()+"'," +
								""+orderMoney+"," +
								""+orderMoney+"," +
								"8," +
								"'交通罚款(失败退费)'," +
								"0," +
								Tools.getNow3()+","+accountMoney+"," +
								"'"+dealserial+"'," +
								"'"+acc[1]+"'," +
								"2)";
						//修改下单状态
						db.update("UPDATE wht_breakrules SET woState=101,updateDate='"+Tools.getNewDate()+"' WHERE id="+this.ViolationId+" AND gdid=(SELECT id FROM wht_bruleorder  WHERE workerNo='"+dealserial+"'  AND dealserial='"+dealserial+"')");
						//修改预查询表，违章状态
						db.update("UPDATE wht_yquery_wz SET woState=101 WHERE carId="+this.ViolationId);
						//修改账户余额
						String updatesql="UPDATE wht_childfacct SET accountleft="+accountMoney+" WHERE childfacct='"+acc[1]+"'";
						db.update(acctSql);
						db.update(updatesql);
					}
				List arry=db.getList("SELECT woState FROM wht_breakrules WHERE gdid = (SELECT id FROM wht_bruleorder  WHERE workerNo='"+dealserial+"'  AND dealserial='"+dealserial+"')");
				if("1".equals(this.state)){//成功
					boolean bool=false;
					for(int i=0;i<arry.size();i++){
						Object[] obj=(Object[])arry.get(i);
						if("4".equals(obj[0]+"".trim())){
							bool=true;
							continue;
						}else{
							bool=false;
							break;
						}
					}
					if(bool){
						db.update("UPDATE wht_bruleorder SET woState='4' WHERE workerNo='"+dealserial+"'  AND dealserial='"+dealserial+"'");
						Log.info("百事帮交通罚款，回调，，，修改订单状态，，工单ID:"+dealserial+",违章id:"+ViolationId+",,工单状态修改为成功");
					}
				}else{
					boolean bool=false;
					for(int i=0;i<arry.size();i++){
						Object[] obj=(Object[])arry.get(i);
						if("101".equals(obj[0]+"".trim())){
							bool=true;
							continue;
						}else{
							bool=false;
							break;
						}
					}
					if(bool){
						db.update("UPDATE wht_bruleorder SET woState='101' WHERE workerNo='"+dealserial+"'  AND dealserial='"+dealserial+"'");
						Log.info("百事帮交通罚款，回调，，，修改订单状态，，工单ID:"+dealserial+",违章id:"+ViolationId+",工单状态修改为退费,修改成功");
					}
				}
				db.commit();
				return ;
			}catch(Exception e){
				db.rollback();
				Log.info("百事帮交通罚款，回调，，，修改订单状态，，工单ID:"+dealserial+",违章id:"+ViolationId+",,系统异常，，，，db.rellback()回滚"+e);
				return ;
			}finally{
				if(db!=null){
					db.close();
					db=null;
				}
			}
		}
		
	}

	/**
	 * 回调下游接口
	 * @author 施建桥
	 */
	class CarBack extends Thread{
		private String[] strs=null;
		private String backState=null;
		
		public CarBack(String[] s,String state){
			this.strs=s;
			this.backState=state;
		}
		
		public void run() {
			String backURL=strs[4];
			if(backURL.indexOf("?")==-1){
				backURL=backURL+"?";
			}else{
				backURL=backURL+"&";
			}
			
			for(int i=1;i<=3;i++){
				if(i!=1){
					try {
						Thread.sleep(10*1000);
					} catch (Exception e) {}
				}
				
				HttpClient client = null;
				GetMethod get = null;
				try{
					String sign=MD5Util.MD5Encode(strs[0]+strs[1]+strs[3]+backState+strs[5],"utf-8");
					StringBuffer buf=new StringBuffer(backURL);
					buf.append("ptOrderNo="+strs[0]);
					buf.append("&whid="+strs[1]);
					buf.append("&car_number="+URLEncoder.encode(strs[2],"utf-8"));
					buf.append("&car_fee="+strs[3]);
					buf.append("&orderState="+backState);
					buf.append("&sign="+sign);
					
					Log.info("交通罚款回调,第:"+i+"次回调,回调url:"+buf.toString());
					
					client = new HttpClient();
					get = new GetMethod(buf.toString());
					// 链接时间 30秒，单位是毫秒
					client.getHttpConnectionManager().getParams().setConnectionTimeout(60 * 1000);
					// 读取时间 10秒，单位是毫秒
					client.getHttpConnectionManager().getParams().setSoTimeout(60 * 1000);
					int status = client.executeMethod(get);
					Log.info("交通罚款回调,第:"+i+"次回调,http状态:"+status+",回调信息:"+Arrays.toString(strs));
					if (status == 200) {
						String result = get.getResponseBodyAsString();
						Log.info("交通罚款回调,第:"+i+"次回调,接收到下游响应:"+result+",回调信息:"+Arrays.toString(strs));
						if(result!=null && "0000".equals(result)){
							break;
						}
					}
				} catch (Exception e) {
					Log.info("交通罚款回调,第:"+i+"次回调,系统异常:"+e+",回调信息:"+Arrays.toString(strs));
				} finally {
					get.releaseConnection();
					((SimpleHttpConnectionManager) client.getHttpConnectionManager()).shutdown();
					if (null != client) {
						client = null;
					}
				}
			}
			return ;
		}
	}
	
//	public static void main(String[] arg){
//		String[] ss=new String[]{"151110061139000000026588885678","109","粤SG922R","170000.0","http://121.15.0.228/index.php/home/AccountBind/jiaotongtest","123456789456"};
//		CarBack back=new Car().new CarBack(ss,"1");
//		back.start();
//	}
}
